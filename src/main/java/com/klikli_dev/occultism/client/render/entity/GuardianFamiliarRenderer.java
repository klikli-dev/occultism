/*
 * MIT License
 *
 * Copyright 2021 vemerion
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
 * associated documentation files (the "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
 * of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following
 * conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial
 * portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR
 * PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT
 * OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
 * OTHER DEALINGS IN THE SOFTWARE.
 */

package com.klikli_dev.occultism.client.render.entity;

import com.klikli_dev.occultism.Occultism;
import com.klikli_dev.occultism.client.model.entity.GuardianFamiliarModel;
import com.klikli_dev.occultism.client.render.entity.state.GuardianFamiliarRenderState;
import com.klikli_dev.occultism.common.entity.familiar.GuardianFamiliarEntity;
import com.klikli_dev.occultism.registry.OccultismModelLayers;
import com.klikli_dev.occultism.util.FamiliarUtil;
import net.minecraft.client.Minecraft;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.client.renderer.MultiBufferSource;
import org.joml.Quaternionf;


public class GuardianFamiliarRenderer extends MobRenderer<GuardianFamiliarEntity, GuardianFamiliarRenderState, GuardianFamiliarModel> {

    private static final Identifier TEXTURES = Identifier.fromNamespaceAndPath(Occultism.MODID,
            "textures/entity/guardian_familiar.png");

    private final ItemModelResolver itemModelResolver;

    public GuardianFamiliarRenderer(Context context) {
        super(context, new GuardianFamiliarModel(context.bakeLayer(OccultismModelLayers.FAMILIAR_GUARDIAN)), 0.3f);
        this.itemModelResolver = context.getItemModelResolver();
        this.addLayer(new GuardianFamiliarOverlay(this));
        this.addLayer(new ToolsLayer(this, this.itemModelResolver));
        this.addLayer(new GuardianFamiliarTree(this));
        this.addLayer(new BirdLayer(this));
    }

    @Override
    public void extractRenderState(GuardianFamiliarEntity entity, GuardianFamiliarRenderState reusedState, float partialTick) {
        super.extractRenderState(entity, reusedState, partialTick);
        reusedState.isSitting = entity.isSitting();
        reusedState.isPartying = entity.isPartying();
        reusedState.lives = entity.getLives();
        reusedState.animHeight = entity.getAnimationHeight(partialTick);
        reusedState.hasBird = entity.hasBird();
        reusedState.hasTools = entity.hasTools();
        reusedState.hasTree = entity.hasTree();
        reusedState.red = entity.getRed();
        reusedState.green = entity.getGreen();
        reusedState.blue = entity.getBlue();
    }

    @Override
    public GuardianFamiliarRenderState createRenderState() {
        return new GuardianFamiliarRenderState();
    }

    @Override
    public void submit(GuardianFamiliarRenderState state, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, CameraRenderState camera) {
        poseStack.pushPose();
        boolean isSitting = state.isSitting;
        boolean noLegs = state.lives <= GuardianFamiliarEntity.FLOATING;
        float height = state.animHeight;

        poseStack.translate(0,
                isSitting ? (noLegs ? -0.5 : -0.36) : height * 0.08, 0);
        super.submit(state, poseStack, submitNodeCollector, camera);
        poseStack.popPose();
    }

    @Override
    public Identifier getTextureLocation(GuardianFamiliarRenderState state) {
        return TEXTURES;
    }

    private static class GuardianFamiliarOverlay extends RenderLayer<GuardianFamiliarRenderState, GuardianFamiliarModel> {
        private static final Identifier OVERLAY = Identifier.fromNamespaceAndPath(Occultism.MODID,
                "textures/entity/guardian_familiar_overlay.png");

        public GuardianFamiliarOverlay(RenderLayerParent<GuardianFamiliarRenderState, GuardianFamiliarModel> parent) {
            super(parent);
        }

        @Override
        public void submit(PoseStack poseStack, SubmitNodeCollector submitNodeCollector, int lightCoords, GuardianFamiliarRenderState state, float yRot, float xRot) {
            if (state.isInvisible)
                return;

            float r = state.red;
            float g = state.green;
            float b = state.blue;

            // Compute alpha from ageInTicks
            float a = (Mth.cos(state.ageInTicks / 20) + 1) * 0.3f + 0.4f;
            int color = ((int) (a * 255) << 24)
                    | ((int) (r * 255) << 16)
                    | ((int) (g * 255) << 8)
                    | (int) (b * 255);

            GuardianFamiliarModel model = this.getParentModel();
            RenderLayer.renderColoredCutoutModel(model, OVERLAY, poseStack, submitNodeCollector, lightCoords, state, color, 0);
        }
    }

    private static class ToolsLayer extends RenderLayer<GuardianFamiliarRenderState, GuardianFamiliarModel> {
        private final ItemModelResolver itemModelResolver;

        public ToolsLayer(RenderLayerParent<GuardianFamiliarRenderState, GuardianFamiliarModel> parent, ItemModelResolver itemModelResolver) {
            super(parent);
            this.itemModelResolver = itemModelResolver;
        }

        @Override
        public void submit(PoseStack poseStack, SubmitNodeCollector submitNodeCollector, int lightCoords, GuardianFamiliarRenderState state, float yRot, float xRot) {
            if (state.isInvisible)
                return;
            if (!state.hasTools)
                return;

            GuardianFamiliarModel model = this.getParentModel();

            poseStack.pushPose();
            model.body.translateAndRotate(poseStack);
            poseStack.translate(-0.15, -0.25, -0.25);
            poseStack.mulPose(new Quaternionf().rotateXYZ(0, -60 * ((float) Math.PI / 180F), 0));
            this.renderItem(new ItemStack(Items.STONE_SWORD), poseStack, submitNodeCollector, lightCoords);
            poseStack.popPose();

            poseStack.pushPose();
            model.body.translateAndRotate(poseStack);
            poseStack.translate(-0.15, 0.1, 0.37);
            poseStack.mulPose(new Quaternionf().rotateXYZ(0, 60 * ((float) Math.PI / 180F), -110 * ((float) Math.PI / 180F)));
            this.renderItem(new ItemStack(Items.STONE_AXE), poseStack, submitNodeCollector, lightCoords);
            poseStack.popPose();

            if (model.leftArm1.visible) {
                poseStack.pushPose();
                model.body.translateAndRotate(poseStack);
                model.leftArm1.translateAndRotate(poseStack);
                poseStack.translate(0.21, 0.2, 0);
                poseStack.mulPose(new Quaternionf().rotateXYZ(0, 0, 210 * ((float) Math.PI / 180F)));
                this.renderItem(new ItemStack(Items.STONE_PICKAXE), poseStack, submitNodeCollector, lightCoords);
                poseStack.popPose();
            }
        }

        private void renderItem(ItemStack stack, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, int lightCoords) {
            ItemStackRenderState stackState = new ItemStackRenderState();
            this.itemModelResolver.updateForTopItem(stackState, stack, ItemDisplayContext.GROUND, null, null, 0);
            stackState.submit(poseStack, submitNodeCollector, lightCoords, OverlayTexture.NO_OVERLAY, 0xFFFFFFFF);
        }
    }

    private static class GuardianFamiliarTree extends RenderLayer<GuardianFamiliarRenderState, GuardianFamiliarModel> {
        private static final Identifier TREE = Identifier.fromNamespaceAndPath(Occultism.MODID,
                "textures/entity/guardian_familiar_tree.png");
        private static final Identifier CHRISTMAS = Identifier.fromNamespaceAndPath(Occultism.MODID,
                "textures/entity/guardian_familiar_christmas.png");

        public GuardianFamiliarTree(RenderLayerParent<GuardianFamiliarRenderState, GuardianFamiliarModel> renderer) {
            super(renderer);
        }

        @Override
        public void submit(PoseStack poseStack, SubmitNodeCollector submitNodeCollector, int lightCoords, GuardianFamiliarRenderState state, float yRot, float xRot) {
            if (state.isInvisible)
                return;

            boolean isChristmas = FamiliarUtil.isChristmas();
            boolean showTree = isChristmas || state.hasTree;

            GuardianFamiliarModel model = this.getParentModel();
            model.tree1.visible = showTree;
            model.tree2.visible = showTree;

            Identifier treeTexture = isChristmas ? CHRISTMAS : TREE;
            RenderLayer.renderColoredCutoutModel(model, treeTexture, poseStack, submitNodeCollector, lightCoords, state, -1, 0);

            // Reset visibility
            model.tree1.visible = false;
            model.tree2.visible = false;
        }
    }

    private static class BirdLayer extends RenderLayer<GuardianFamiliarRenderState, GuardianFamiliarModel> {
        public BirdLayer(RenderLayerParent<GuardianFamiliarRenderState, GuardianFamiliarModel> parent) {
            super(parent);
        }

        @Override
        public void submit(PoseStack poseStack, SubmitNodeCollector submitNodeCollector, int lightCoords, GuardianFamiliarRenderState state, float yRot, float xRot) {
            GuardianFamiliarModel model = this.getParentModel();
            if (!state.hasBird || model.leftArm1.visible || state.isInvisible) {
                return;
            }

            MultiBufferSource.BufferSource bufferSource = Minecraft.getInstance().renderBuffers().bufferSource();
            VertexConsumer vertexConsumer = bufferSource.getBuffer(model.renderType(TEXTURES));
            poseStack.pushPose();
            poseStack.translate(model.body.x / 16d, model.body.y / 16d, model.body.z / 16d);
            poseStack.translate(0.35, -0.2, 0);
            model.birdBody.render(poseStack, vertexConsumer, lightCoords, OverlayTexture.NO_OVERLAY, 0xFFFFFFFF);
            poseStack.popPose();
            bufferSource.endBatch(model.renderType(TEXTURES));
        }
    }
}
