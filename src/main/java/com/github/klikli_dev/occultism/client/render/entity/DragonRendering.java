package com.github.klikli_dev.occultism.client.render.entity;

import com.github.klikli_dev.occultism.client.model.entity.DragonFamiliarModel;
import com.github.klikli_dev.occultism.common.entity.DragonFamiliarEntity;
import com.github.klikli_dev.occultism.common.entity.ThrownSwordEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Quaternion;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class DragonRendering {
    public static class StickLayer extends RenderLayer<DragonFamiliarEntity, DragonFamiliarModel> {
        public StickLayer(RenderLayerParent<DragonFamiliarEntity, DragonFamiliarModel> parent) {
            super(parent);
        }

        @Override
        public void render(PoseStack pMatrixStack, MultiBufferSource pBuffer, int pPackedLight, DragonFamiliarEntity pLivingEntity, float pLimbSwing, float pLimbSwingAmount, float pPartialTicks, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch) {
            if (!pLivingEntity.hasStick())
                return;
            pMatrixStack.pushPose();
            DragonFamiliarModel model = this.getParentModel();
            model.body.translateAndRotate(pMatrixStack);
            model.neck1.translateAndRotate(pMatrixStack);
            model.neck2.translateAndRotate(pMatrixStack);
            model.head.translateAndRotate(pMatrixStack);
            model.jaw.translateAndRotate(pMatrixStack);

            pMatrixStack.translate(-0.08, -0.07, -0.15);
            pMatrixStack.mulPose(new Quaternion(0, 0, -45, true));

            Minecraft.getInstance().getEntityRenderDispatcher().getItemInHandRenderer().renderItem(pLivingEntity, new ItemStack(Items.STICK),
                    ItemTransforms.TransformType.GROUND, false, pMatrixStack, pBuffer, pPackedLight);
            pMatrixStack.popPose();
        }
    }

    public static class SwordLayer extends RenderLayer<DragonFamiliarEntity, DragonFamiliarModel> {
        public SwordLayer(RenderLayerParent<DragonFamiliarEntity, DragonFamiliarModel> parent) {
            super(parent);
        }

        @Override
        public void render(PoseStack pMatrixStack, MultiBufferSource pBuffer, int pPackedLight, DragonFamiliarEntity pLivingEntity, float pLimbSwing, float pLimbSwingAmount, float pPartialTicks, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch) {
            if (!pLivingEntity.hasSword())
                return;
            pMatrixStack.pushPose();

            DragonFamiliarModel model = this.getParentModel();
            model.body.translateAndRotate(pMatrixStack);
            model.tail1.translateAndRotate(pMatrixStack);
            model.tail2.translateAndRotate(pMatrixStack);
            model.tail3.translateAndRotate(pMatrixStack);
            pMatrixStack.translate(0, 0.24, 0.32);
            pMatrixStack.translate(0, -0.23, -0.12);
            pMatrixStack.mulPose(new Quaternion(Mth.sin(pAgeInTicks / 20) * 20 + 130, 90 + Mth.cos(pAgeInTicks / 20) * 20, 0, true));
            pMatrixStack.translate(0.23, 0.12, 0.0);

            Minecraft.getInstance().getEntityRenderDispatcher().getItemInHandRenderer().renderItem(pLivingEntity,
                    new ItemStack(Items.IRON_SWORD), ItemTransforms.TransformType.GROUND, false, pMatrixStack,
                    pBuffer, pPackedLight);
            pMatrixStack.popPose();
        }
    }

    public static class ThrownSwordRenderer extends ThrownItemRenderer<ThrownSwordEntity> {

        public ThrownSwordRenderer(EntityRendererProvider.Context context) {
            super(context);
        }

        @Override
        public void render(ThrownSwordEntity pEntity, float pEntityYaw, float pPartialTicks, PoseStack pMatrixStack, MultiBufferSource pBuffer, int pPackedLight) {
            float ageInTicks = pEntity.tickCount + pPartialTicks;
            pMatrixStack.pushPose();
            pMatrixStack.mulPose(new Quaternion(0, 0, ageInTicks * 20, true));
            super.render(pEntity, pEntityYaw, pPartialTicks, pMatrixStack, pBuffer, pPackedLight);
            pMatrixStack.popPose();

        }
    }
}