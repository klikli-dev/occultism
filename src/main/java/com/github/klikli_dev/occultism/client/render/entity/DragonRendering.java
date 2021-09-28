package com.github.klikli_dev.occultism.client.render.entity;

import com.github.klikli_dev.occultism.client.model.entity.DragonFamiliarModel;
import com.github.klikli_dev.occultism.common.entity.DragonFamiliarEntity;
import com.github.klikli_dev.occultism.common.entity.ThrownSwordEntity;
import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.SpriteRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Quaternion;

public class DragonRendering {
    public static class StickLayer extends LayerRenderer<DragonFamiliarEntity, DragonFamiliarModel> {
        public StickLayer(IEntityRenderer<DragonFamiliarEntity, DragonFamiliarModel> renderer) {
            super(renderer);
        }

        @Override
        public void render(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn,
                DragonFamiliarEntity entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks,
                float ageInTicks, float netHeadYaw, float headPitch) {
            if (!entitylivingbaseIn.hasStick())
                return;
            matrixStackIn.pushPose();
            DragonFamiliarModel model = this.getParentModel();
            model.body.translateAndRotate(matrixStackIn);
            model.neck1.translateAndRotate(matrixStackIn);
            model.neck2.translateAndRotate(matrixStackIn);
            model.head.translateAndRotate(matrixStackIn);
            model.jaw.translateAndRotate(matrixStackIn);

            matrixStackIn.translate(-0.08, -0.07, -0.15);
            matrixStackIn.mulPose(new Quaternion(0, 0, -45, true));

            Minecraft.getInstance().getItemInHandRenderer().renderItem(entitylivingbaseIn, new ItemStack(Items.STICK),
                    ItemCameraTransforms.TransformType.GROUND, false, matrixStackIn, bufferIn, packedLightIn);
            matrixStackIn.popPose();
        }
    }

    public static class SwordLayer extends LayerRenderer<DragonFamiliarEntity, DragonFamiliarModel> {
        public SwordLayer(IEntityRenderer<DragonFamiliarEntity, DragonFamiliarModel> renderer) {
            super(renderer);
        }

        @Override
        public void render(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn,
                DragonFamiliarEntity entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks,
                float ageInTicks, float netHeadYaw, float headPitch) {
            if (!entitylivingbaseIn.hasSword())
                return;
            matrixStackIn.pushPose();

            DragonFamiliarModel model = this.getParentModel();
            model.body.translateAndRotate(matrixStackIn);
            model.tail1.translateAndRotate(matrixStackIn);
            model.tail2.translateAndRotate(matrixStackIn);
            model.tail3.translateAndRotate(matrixStackIn);
            matrixStackIn.translate(0, 0.24, 0.32);
            matrixStackIn.translate(0, -0.23, -0.12);
            matrixStackIn.mulPose(new Quaternion(MathHelper.sin(ageInTicks / 20) * 20 + 130, 90 + MathHelper.cos(ageInTicks / 20) * 20, 0, true));
            matrixStackIn.translate(0.23, 0.12, 0.0);

            Minecraft.getInstance().getItemInHandRenderer().renderItem(entitylivingbaseIn,
                    new ItemStack(Items.IRON_SWORD), ItemCameraTransforms.TransformType.GROUND, false, matrixStackIn,
                    bufferIn, packedLightIn);
            matrixStackIn.popPose();
        }
    }
    
    public static class ThrownSwordRenderer extends SpriteRenderer<ThrownSwordEntity> {

        public ThrownSwordRenderer(EntityRendererManager manager, ItemRenderer itemRenderer) {
            super(manager, itemRenderer);
        }
        
        @Override
        public void render(ThrownSwordEntity pEntity, float pEntityYaw, float pPartialTicks, MatrixStack pMatrixStack,
                IRenderTypeBuffer pBuffer, int pPackedLight) {
            float ageInTicks = pEntity.tickCount + pPartialTicks;
            pMatrixStack.pushPose();
            pMatrixStack.mulPose(new Quaternion(0, 0, ageInTicks * 20, true));
            super.render(pEntity, pEntityYaw, pPartialTicks, pMatrixStack, pBuffer, pPackedLight);
            pMatrixStack.popPose();
        }
        
    }
}