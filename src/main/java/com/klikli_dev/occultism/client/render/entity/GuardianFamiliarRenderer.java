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
import com.klikli_dev.occultism.common.entity.familiar.GuardianFamiliarEntity;
import com.klikli_dev.occultism.registry.OccultismModelLayers;
import com.klikli_dev.occultism.util.FamiliarUtil;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import net.minecraft.util.context.ContextKey;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.joml.Quaternionf;


public class GuardianFamiliarRenderer extends MobRenderer<GuardianFamiliarEntity, LivingEntityRenderState, GuardianFamiliarModel> {

    private static final Identifier TEXTURES = Identifier.fromNamespaceAndPath(Occultism.MODID,
            "textures/entity/guardian_familiar.png");

    private static final ContextKey<Boolean> IS_SITTING = new ContextKey<>(Identifier.fromNamespaceAndPath(Occultism.MODID, "guardian_is_sitting"));
    private static final ContextKey<Byte> LIVES = new ContextKey<>(Identifier.fromNamespaceAndPath(Occultism.MODID, "guardian_lives"));
    private static final ContextKey<Float> ANIM_HEIGHT = new ContextKey<>(Identifier.fromNamespaceAndPath(Occultism.MODID, "guardian_anim_height"));
    private static final ContextKey<Boolean> HAS_TOOLS = new ContextKey<>(Identifier.fromNamespaceAndPath(Occultism.MODID, "guardian_has_tools"));
    private static final ContextKey<Boolean> HAS_TREE = new ContextKey<>(Identifier.fromNamespaceAndPath(Occultism.MODID, "guardian_has_tree"));
    private static final ContextKey<Float> RED = new ContextKey<>(Identifier.fromNamespaceAndPath(Occultism.MODID, "guardian_red"));
    private static final ContextKey<Float> GREEN = new ContextKey<>(Identifier.fromNamespaceAndPath(Occultism.MODID, "guardian_green"));
    private static final ContextKey<Float> BLUE = new ContextKey<>(Identifier.fromNamespaceAndPath(Occultism.MODID, "guardian_blue"));

    private final ItemModelResolver itemModelResolver;

    public GuardianFamiliarRenderer(EntityRendererProvider.Context context) {
        super(context, new GuardianFamiliarModel(context.bakeLayer(OccultismModelLayers.FAMILIAR_GUARDIAN)), 0.3f);
        this.itemModelResolver = context.getItemModelResolver();
        this.addLayer(new GuardianFamiliarOverlay(this));
        this.addLayer(new ToolsLayer(this, this.itemModelResolver));
        this.addLayer(new GuardianFamiliarTree(this));
    }

    @Override
    public void extractRenderState(GuardianFamiliarEntity entity, LivingEntityRenderState reusedState, float partialTick) {
        super.extractRenderState(entity, reusedState, partialTick);
        reusedState.setRenderData(IS_SITTING, entity.isSitting());
        reusedState.setRenderData(LIVES, entity.getLives());
        reusedState.setRenderData(ANIM_HEIGHT, entity.getAnimationHeight(partialTick));
        reusedState.setRenderData(HAS_TOOLS, entity.hasTools());
        reusedState.setRenderData(HAS_TREE, entity.hasTree());
        reusedState.setRenderData(RED, entity.getRed());
        reusedState.setRenderData(GREEN, entity.getGreen());
        reusedState.setRenderData(BLUE, entity.getBlue());
    }

    @Override
    public LivingEntityRenderState createRenderState() {
        return new LivingEntityRenderState();
    }

    @Override
    public void submit(LivingEntityRenderState state, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, CameraRenderState camera) {
        poseStack.pushPose();
        Boolean sitting = state.getRenderData(IS_SITTING);
        Byte lives = state.getRenderData(LIVES);
        Float animHeight = state.getRenderData(ANIM_HEIGHT);

        boolean isSitting = sitting != null && sitting;
        boolean noLegs = lives != null && lives <= GuardianFamiliarEntity.FLOATING;
        float height = animHeight != null ? animHeight : 0f;

        poseStack.translate(0,
                isSitting ? (noLegs ? -0.5 : -0.36) : height * 0.08, 0);
        super.submit(state, poseStack, submitNodeCollector, camera);
        poseStack.popPose();
    }

    @Override
    public Identifier getTextureLocation(LivingEntityRenderState state) {
        return TEXTURES;
    }

    private static class GuardianFamiliarOverlay extends RenderLayer<LivingEntityRenderState, GuardianFamiliarModel> {
        private static final Identifier OVERLAY = Identifier.fromNamespaceAndPath(Occultism.MODID,
                "textures/entity/guardian_familiar_overlay.png");

        public GuardianFamiliarOverlay(RenderLayerParent<LivingEntityRenderState, GuardianFamiliarModel> parent) {
            super(parent);
        }

        @Override
        public void submit(PoseStack poseStack, SubmitNodeCollector submitNodeCollector, int lightCoords, LivingEntityRenderState state, float yRot, float xRot) {
            if (state.isInvisible)
                return;

            Float red = state.getRenderData(GuardianFamiliarRenderer.RED);
            Float green = state.getRenderData(GuardianFamiliarRenderer.GREEN);
            Float blue = state.getRenderData(GuardianFamiliarRenderer.BLUE);

            float r = red != null ? red : 1f;
            float g = green != null ? green : 1f;
            float b = blue != null ? blue : 1f;

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

    private static class ToolsLayer extends RenderLayer<LivingEntityRenderState, GuardianFamiliarModel> {
        private final ItemModelResolver itemModelResolver;

        public ToolsLayer(RenderLayerParent<LivingEntityRenderState, GuardianFamiliarModel> parent, ItemModelResolver itemModelResolver) {
            super(parent);
            this.itemModelResolver = itemModelResolver;
        }

        @Override
        public void submit(PoseStack poseStack, SubmitNodeCollector submitNodeCollector, int lightCoords, LivingEntityRenderState state, float yRot, float xRot) {
            if (state.isInvisible)
                return;
            Boolean hasTools = state.getRenderData(GuardianFamiliarRenderer.HAS_TOOLS);
            if (hasTools == null || !hasTools)
                return;

            GuardianFamiliarModel model = this.getParentModel();

            poseStack.pushPose();
            model.body.translateAndRotate(poseStack);
            poseStack.translate(-0.15, -0.25, -0.25);
            poseStack.mulPose(new Quaternionf().rotateXYZ(0, -60 * ((float) Math.PI / 180F), 0));
            renderItem(new ItemStack(Items.STONE_SWORD), poseStack, submitNodeCollector, lightCoords);
            poseStack.popPose();

            poseStack.pushPose();
            model.body.translateAndRotate(poseStack);
            poseStack.translate(-0.15, 0.1, 0.37);
            poseStack.mulPose(new Quaternionf().rotateXYZ(0, 60 * ((float) Math.PI / 180F), -110 * ((float) Math.PI / 180F)));
            renderItem(new ItemStack(Items.STONE_AXE), poseStack, submitNodeCollector, lightCoords);
            poseStack.popPose();

            if (model.leftArm1.visible) {
                poseStack.pushPose();
                model.body.translateAndRotate(poseStack);
                model.leftArm1.translateAndRotate(poseStack);
                poseStack.translate(0.21, 0.2, 0);
                poseStack.mulPose(new Quaternionf().rotateXYZ(0, 0, 210 * ((float) Math.PI / 180F)));
                renderItem(new ItemStack(Items.STONE_PICKAXE), poseStack, submitNodeCollector, lightCoords);
                poseStack.popPose();
            }
        }

        private void renderItem(ItemStack stack, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, int lightCoords) {
            ItemStackRenderState stackState = new ItemStackRenderState();
            this.itemModelResolver.updateForTopItem(stackState, stack, ItemDisplayContext.GROUND, null, null, 0);
            stackState.submit(poseStack, submitNodeCollector, lightCoords, OverlayTexture.NO_OVERLAY, 0xFFFFFFFF);
        }
    }

    private static class GuardianFamiliarTree extends RenderLayer<LivingEntityRenderState, GuardianFamiliarModel> {
        private static final Identifier TREE = Identifier.fromNamespaceAndPath(Occultism.MODID,
                "textures/entity/guardian_familiar_tree.png");
        private static final Identifier CHRISTMAS = Identifier.fromNamespaceAndPath(Occultism.MODID,
                "textures/entity/guardian_familiar_christmas.png");

        public GuardianFamiliarTree(RenderLayerParent<LivingEntityRenderState, GuardianFamiliarModel> renderer) {
            super(renderer);
        }

        @Override
        public void submit(PoseStack poseStack, SubmitNodeCollector submitNodeCollector, int lightCoords, LivingEntityRenderState state, float yRot, float xRot) {
            if (state.isInvisible)
                return;

            boolean isChristmas = FamiliarUtil.isChristmas();
            Boolean hasTree = state.getRenderData(GuardianFamiliarRenderer.HAS_TREE);
            boolean showTree = isChristmas || (hasTree != null && hasTree);

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
}
