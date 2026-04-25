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
import com.klikli_dev.occultism.client.model.entity.GreedyFamiliarModel;
import com.klikli_dev.occultism.client.render.entity.state.GreedyFamiliarRenderState;
import com.klikli_dev.occultism.common.entity.familiar.GreedyFamiliarEntity;
import com.klikli_dev.occultism.registry.OccultismModelLayers;
import com.klikli_dev.occultism.util.FamiliarUtil;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.joml.Quaternionf;

public class GreedyFamiliarRenderer extends MobRenderer<GreedyFamiliarEntity, GreedyFamiliarRenderState, GreedyFamiliarModel> {

    private static final Identifier TEXTURES = Identifier.fromNamespaceAndPath(Occultism.MODID,
            "textures/entity/greedy_familiar.png");

    private final ItemModelResolver itemModelResolver;

    public GreedyFamiliarRenderer(Context context) {
        super(context, new GreedyFamiliarModel(context.bakeLayer(OccultismModelLayers.FAMILIAR_GREEDY)), 0.3f);
        this.itemModelResolver = context.getItemModelResolver();
        this.addLayer(new ItemLayer(this, this.itemModelResolver));
        this.addLayer(new GreedyFamiliarChest(this));
    }

    @Override
    public GreedyFamiliarRenderState createRenderState() {
        return new GreedyFamiliarRenderState();
    }

    @Override
    public void extractRenderState(GreedyFamiliarEntity entity, GreedyFamiliarRenderState reusedState, float partialTick) {
        super.extractRenderState(entity, reusedState, partialTick);
        reusedState.isSitting = entity.isSitting();
        reusedState.isPartying = entity.isPartying();
        reusedState.isVehicle = entity.getVehicle() != null;
        reusedState.hasBlacksmithUpgrade = entity.hasBlacksmithUpgrade();
        reusedState.hasTargetBlock = entity.getTargetBlock().isPresent();
        reusedState.lidRot = entity.getLidRot(partialTick);
        reusedState.monsterRot = entity.getMonsterRot(partialTick);
        reusedState.earRotZ = entity.getEarRotZ(partialTick);
        reusedState.earRotX = entity.getEarRotX(partialTick);
        reusedState.offhandItem = entity.getOffhandItem().copy();
    }

    @Override
    public Identifier getTextureLocation(GreedyFamiliarRenderState state) {
        return TEXTURES;
    }

    @Override
    public void submit(GreedyFamiliarRenderState state, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, CameraRenderState camera) {
        poseStack.pushPose();
        if (state.isSitting && !state.isPartying)
            poseStack.translate(0, -0.25, 0);
        super.submit(state, poseStack, submitNodeCollector, camera);
        poseStack.popPose();
    }

    private static class GreedyFamiliarChest extends RenderLayer<GreedyFamiliarRenderState, GreedyFamiliarModel> {
        private static final Identifier CHEST = Identifier.fromNamespaceAndPath(Occultism.MODID,
                "textures/entity/greedy_familiar_chest.png");
        private static final Identifier CHRISTMAS = Identifier.fromNamespaceAndPath(Occultism.MODID,
                "textures/entity/greedy_familiar_christmas.png");

        public GreedyFamiliarChest(RenderLayerParent<GreedyFamiliarRenderState, GreedyFamiliarModel> parent) {
            super(parent);
        }

        @Override
        public void submit(PoseStack pMatrixStack, SubmitNodeCollector submitNodeCollector, int lightCoords, GreedyFamiliarRenderState state, float yRot, float xRot) {
            if (state.isInvisible)
                return;
            GreedyFamiliarModel model = this.getParentModel();
            Identifier tex = FamiliarUtil.isChristmas() ? CHRISTMAS : CHEST;
            RenderLayer.renderColoredCutoutModel(model, tex, pMatrixStack, submitNodeCollector, lightCoords, state, -1, 0);
        }
    }

    private static class ItemLayer extends RenderLayer<GreedyFamiliarRenderState, GreedyFamiliarModel> {

        private final ItemModelResolver itemModelResolver;

        public ItemLayer(RenderLayerParent<GreedyFamiliarRenderState, GreedyFamiliarModel> parent, ItemModelResolver itemModelResolver) {
            super(parent);
            this.itemModelResolver = itemModelResolver;
        }

        @Override
        public void submit(PoseStack pMatrixStack, SubmitNodeCollector submitNodeCollector, int lightCoords, GreedyFamiliarRenderState state, float yRot, float xRot) {
            ItemStack offhand = state.offhandItem;
            if (!state.hasBlacksmithUpgrade && (offhand == null || offhand.isEmpty()))
                return;

            GreedyFamiliarModel model = this.getParentModel();

            if (state.hasBlacksmithUpgrade) {
                pMatrixStack.pushPose();
                model.body.translateAndRotate(pMatrixStack);
                model.rightArm.translateAndRotate(pMatrixStack);

                pMatrixStack.translate(-0.06, 0.2, -0.1);
                pMatrixStack.mulPose(new Quaternionf().rotateXYZ(0, 90 * ((float) Math.PI / 180F), -45 * ((float) Math.PI / 180F)));

                ItemStackRenderState stackState = new ItemStackRenderState();
                this.itemModelResolver.updateForTopItem(stackState, new ItemStack(Items.IRON_PICKAXE), ItemDisplayContext.GROUND, null, null, 0);
                stackState.submit(pMatrixStack, submitNodeCollector, lightCoords, OverlayTexture.NO_OVERLAY, state.outlineColor);
                pMatrixStack.popPose();
            }

            if (offhand != null && !offhand.isEmpty()) {
                pMatrixStack.pushPose();
                model.body.translateAndRotate(pMatrixStack);
                model.leftArm.translateAndRotate(pMatrixStack);

                pMatrixStack.translate(0.06, 0.2, -0.17);
                pMatrixStack.mulPose(new Quaternionf().rotateXYZ(0, 45 * ((float) Math.PI / 180F), 0));
                float size = 0.75f;
                pMatrixStack.scale(size, size, size);

                ItemStackRenderState stackState = new ItemStackRenderState();
                this.itemModelResolver.updateForTopItem(stackState, offhand, ItemDisplayContext.GROUND, null, null, 0);
                stackState.submit(pMatrixStack, submitNodeCollector, lightCoords, OverlayTexture.NO_OVERLAY, state.outlineColor);
                pMatrixStack.popPose();
            }
        }
    }
}
