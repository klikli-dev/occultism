package com.klikli_dev.occultism.client.render.entity;

import com.klikli_dev.occultism.client.model.entity.DragonFamiliarModel;
import com.klikli_dev.occultism.common.entity.familiar.DragonFamiliarEntity;
import com.klikli_dev.occultism.common.entity.familiar.ThrownSwordEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.client.renderer.entity.state.ThrownItemRenderState;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.joml.Quaternionf;

public class DragonRendering {

    public static class StickLayer extends RenderLayer<LivingEntityRenderState, DragonFamiliarModel> {
        public StickLayer(RenderLayerParent<LivingEntityRenderState, DragonFamiliarModel> parent) {
            super(parent);
        }

        @Override
        public void submit(PoseStack pMatrixStack, SubmitNodeCollector submitNodeCollector, int lightCoords, LivingEntityRenderState state, float yRot, float xRot) {
            DragonFamiliarEntity dragon = state.getRenderData(DragonFamiliarRenderer.DRAGON_KEY);
            if (dragon == null || !dragon.hasStick())
                return;

            pMatrixStack.pushPose();
            DragonFamiliarModel model = this.getParentModel();
            model.body.translateAndRotate(pMatrixStack);
            model.neck1.translateAndRotate(pMatrixStack);
            model.neck2.translateAndRotate(pMatrixStack);
            model.head.translateAndRotate(pMatrixStack);
            model.jaw.translateAndRotate(pMatrixStack);

            pMatrixStack.translate(-0.08, -0.07, -0.15);
            pMatrixStack.mulPose(new Quaternionf().rotateXYZ(0, 0, -45 * ((float) Math.PI / 180F)));

            // Render stick item via ItemModelResolver stored in render state
            ItemModelResolver resolver = state.getRenderData(DragonFamiliarRenderer.ITEM_MODEL_RESOLVER_KEY);
            if (resolver != null) {
                ItemStack stick = new ItemStack(Items.STICK);
                ItemStackRenderState stackState = new ItemStackRenderState();
                resolver.updateForTopItem(stackState, stick, ItemDisplayContext.GROUND, null, null, 0);
                stackState.submit(pMatrixStack, submitNodeCollector, lightCoords, OverlayTexture.NO_OVERLAY, state.outlineColor);
            }
            pMatrixStack.popPose();
        }
    }

    public static class SwordLayer extends RenderLayer<LivingEntityRenderState, DragonFamiliarModel> {
        public SwordLayer(RenderLayerParent<LivingEntityRenderState, DragonFamiliarModel> parent) {
            super(parent);
        }

        @Override
        public void submit(PoseStack pMatrixStack, SubmitNodeCollector submitNodeCollector, int lightCoords, LivingEntityRenderState state, float yRot, float xRot) {
            DragonFamiliarEntity dragon = state.getRenderData(DragonFamiliarRenderer.DRAGON_KEY);
            if (dragon == null || !dragon.hasSword())
                return;

            pMatrixStack.pushPose();
            DragonFamiliarModel model = this.getParentModel();
            float ageInTicks = state.ageInTicks;
            model.body.translateAndRotate(pMatrixStack);
            model.tail1.translateAndRotate(pMatrixStack);
            model.tail2.translateAndRotate(pMatrixStack);
            model.tail3.translateAndRotate(pMatrixStack);
            pMatrixStack.translate(0, 0.24, 0.32);
            pMatrixStack.translate(0, -0.23, -0.12);
            pMatrixStack.mulPose(new Quaternionf().rotateXYZ(
                    (Mth.sin(ageInTicks / 20) * 20 + 130) * ((float) Math.PI / 180F),
                    (90 + Mth.cos(ageInTicks / 20) * 20) * ((float) Math.PI / 180F),
                    0));
            pMatrixStack.translate(0.23, 0.12, 0.0);

            ItemStack sword = new ItemStack(Items.IRON_SWORD);
            ItemStackRenderState stackState = new ItemStackRenderState();
            ItemModelResolver resolver = state.getRenderData(DragonFamiliarRenderer.ITEM_MODEL_RESOLVER_KEY);
            if (resolver != null) {
                resolver.updateForTopItem(stackState, sword, ItemDisplayContext.GROUND, null, null, 0);
                stackState.submit(pMatrixStack, submitNodeCollector, lightCoords, OverlayTexture.NO_OVERLAY, state.outlineColor);
            }
            pMatrixStack.popPose();
        }
    }

    public static class ThrownSwordRenderer extends ThrownItemRenderer<ThrownSwordEntity> {

        public ThrownSwordRenderer(EntityRendererProvider.Context context) {
            super(context);
        }

        @Override
        public void submit(ThrownItemRenderState state, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, CameraRenderState camera) {
            float ageInTicks = state.ageInTicks;
            poseStack.pushPose();
            poseStack.mulPose(new Quaternionf().rotateXYZ(0, 0, ageInTicks * 20 * ((float) Math.PI / 180F)));
            super.submit(state, poseStack, submitNodeCollector, camera);
            poseStack.popPose();
        }
    }
}
