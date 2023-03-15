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

package com.github.klikli_dev.occultism.client.render.entity;

import com.github.klikli_dev.occultism.Occultism;
import com.github.klikli_dev.occultism.client.model.entity.GreedyFamiliarModel;
import com.github.klikli_dev.occultism.common.entity.familiar.GreedyFamiliarEntity;
import com.github.klikli_dev.occultism.registry.OccultismModelLayers;
import com.github.klikli_dev.occultism.util.FamiliarUtil;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.joml.Quaternionf;

public class GreedyFamiliarRenderer extends MobRenderer<GreedyFamiliarEntity, GreedyFamiliarModel> {

    private static final ResourceLocation TEXTURES = new ResourceLocation(Occultism.MODID,
            "textures/entity/greedy_familiar.png");

    public GreedyFamiliarRenderer(EntityRendererProvider.Context context) {
        super(context, new GreedyFamiliarModel(context.bakeLayer(OccultismModelLayers.FAMILIAR_GREEDY)), 0.3f);
        this.addLayer(new ItemLayer(this));
        this.addLayer(new GreedyFamiliarChest(this));
    }

    @Override
    public ResourceLocation getTextureLocation(GreedyFamiliarEntity entity) {
        return TEXTURES;
    }

    @Override
    public void render(GreedyFamiliarEntity entityIn, float entityYaw, float partialTicks, PoseStack pMatrixStack,
                       MultiBufferSource bufferIn, int packedLightIn) {
        pMatrixStack.pushPose();
        if (entityIn.isSitting() && !entityIn.isPartying())
            pMatrixStack.translate(0, -0.25, 0);
        super.render(entityIn, entityYaw, partialTicks, pMatrixStack, bufferIn, packedLightIn);
        pMatrixStack.popPose();
    }

    private static class GreedyFamiliarChest extends RenderLayer<GreedyFamiliarEntity, GreedyFamiliarModel> {
        private static final ResourceLocation CHEST = new ResourceLocation(Occultism.MODID,
                "textures/entity/greedy_familiar_chest.png");
        private static final ResourceLocation CHRISTMAS = new ResourceLocation(Occultism.MODID,
                "textures/entity/greedy_familiar_christmas.png");

        public GreedyFamiliarChest(RenderLayerParent<GreedyFamiliarEntity, GreedyFamiliarModel> parent) {
            super(parent);
        }

        @Override
        public void render(PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn,
                           GreedyFamiliarEntity entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks,
                           float ageInTicks, float netHeadYaw, float headPitch) {
            if (entitylivingbaseIn.isInvisible())
                return;

            VertexConsumer ivertexbuilder = bufferIn
                    .getBuffer(RenderType.entityTranslucent(FamiliarUtil.isChristmas() ? CHRISTMAS : CHEST));
            GreedyFamiliarModel model = this.getParentModel();
            model.renderToBuffer(matrixStackIn, ivertexbuilder, packedLightIn,
                    LivingEntityRenderer.getOverlayCoords(entitylivingbaseIn, 0), 1, 1, 1, 1);
        }
    }

    private static class ItemLayer extends RenderLayer<GreedyFamiliarEntity, GreedyFamiliarModel> {
        public ItemLayer(RenderLayerParent<GreedyFamiliarEntity, GreedyFamiliarModel> parent) {
            super(parent);
        }

        @Override
        public void render(PoseStack pMatrixStack, MultiBufferSource pBuffer, int pPackedLight, GreedyFamiliarEntity pLivingEntity, float pLimbSwing, float pLimbSwingAmount, float pPartialTicks, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch) {
            boolean hasBlacksmithUpgrade = pLivingEntity.hasBlacksmithUpgrade();
            ItemStack offhand = pLivingEntity.getOffhandItem();
            if (!hasBlacksmithUpgrade && offhand.isEmpty())
                return;

            GreedyFamiliarModel model = this.getParentModel();
            ItemInHandRenderer renderer = Minecraft.getInstance().getEntityRenderDispatcher().getItemInHandRenderer();
            pMatrixStack.pushPose();


            if (hasBlacksmithUpgrade) {
                model.body.translateAndRotate(pMatrixStack);
                model.rightArm.translateAndRotate(pMatrixStack);

                pMatrixStack.translate(-0.06, 0.2, -0.1);
                pMatrixStack.mulPose(new Quaternionf().rotateXYZ(0, 90 * ((float) Math.PI / 180F), -45 * ((float) Math.PI / 180F)));

                renderer.renderItem(pLivingEntity,
                        new ItemStack(Items.IRON_PICKAXE), ItemDisplayContext.GROUND, false,
                        pMatrixStack, pBuffer, pPackedLight);
                pMatrixStack.popPose();
            }

            if (!offhand.isEmpty()) {
                pMatrixStack.pushPose();
                model.body.translateAndRotate(pMatrixStack);
                model.leftArm.translateAndRotate(pMatrixStack);

                pMatrixStack.translate(0.06, 0.2, -0.17);
                pMatrixStack.mulPose(new Quaternionf().rotateXYZ(0, 45 * ((float) Math.PI / 180F), 0));
                float size = 0.75f;
                pMatrixStack.scale(size, size, size);

                renderer.renderItem(pLivingEntity, offhand,
                        ItemDisplayContext.GROUND, false, pMatrixStack, pBuffer, pPackedLight);
                pMatrixStack.popPose();
            }
        }
    }
}