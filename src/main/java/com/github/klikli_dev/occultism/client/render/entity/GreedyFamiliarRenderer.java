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
import com.github.klikli_dev.occultism.common.entity.GreedyFamiliarEntity;
import com.github.klikli_dev.occultism.util.FamiliarUtil;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.FirstPersonRenderer;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Quaternion;

public class GreedyFamiliarRenderer extends MobRenderer<GreedyFamiliarEntity, GreedyFamiliarModel> {

    private static final ResourceLocation TEXTURES = new ResourceLocation(Occultism.MODID,
            "textures/entity/greedy_familiar.png");

    public GreedyFamiliarRenderer(EntityRendererManager renderManagerIn) {
        super(renderManagerIn, new GreedyFamiliarModel(), 0.3f);
        this.addLayer(new ItemLayer(this));
        this.addLayer(new GreedyFamiliarChest(this));
    }

    @Override
    public ResourceLocation getTextureLocation(GreedyFamiliarEntity entity) {
        return TEXTURES;
    }

    @Override
    public void render(GreedyFamiliarEntity entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn,
            IRenderTypeBuffer bufferIn, int packedLightIn) {
        matrixStackIn.pushPose();
        if (entityIn.isSitting() && !entityIn.isPartying())
            matrixStackIn.translate(0, -0.25, 0);
        super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
        matrixStackIn.popPose();
    }

    private static class GreedyFamiliarChest extends LayerRenderer<GreedyFamiliarEntity, GreedyFamiliarModel> {
        private static final ResourceLocation CHEST = new ResourceLocation(Occultism.MODID,
                "textures/entity/greedy_familiar_chest.png");
        private static final ResourceLocation CHRISTMAS = new ResourceLocation(Occultism.MODID,
                "textures/entity/greedy_familiar_christmas.png");

        public GreedyFamiliarChest(IEntityRenderer<GreedyFamiliarEntity, GreedyFamiliarModel> renderer) {
            super(renderer);
        }

        public void render(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn,
                GreedyFamiliarEntity entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks,
                float ageInTicks, float netHeadYaw, float headPitch) {
            if (entitylivingbaseIn.isInvisible())
                return;

            IVertexBuilder ivertexbuilder = bufferIn
                    .getBuffer(RenderType.entityTranslucent(FamiliarUtil.isChristmas() ? CHRISTMAS : CHEST));
            GreedyFamiliarModel model = this.getParentModel();
            model.renderToBuffer(matrixStackIn, ivertexbuilder, packedLightIn,
                    LivingRenderer.getOverlayCoords(entitylivingbaseIn, 0), 1, 1, 1, 1);
        }
    }

    private static class ItemLayer extends LayerRenderer<GreedyFamiliarEntity, GreedyFamiliarModel> {
        public ItemLayer(IEntityRenderer<GreedyFamiliarEntity, GreedyFamiliarModel> renderer) {
            super(renderer);
        }

        @Override
        public void render(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn,
                GreedyFamiliarEntity entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks,
                float ageInTicks, float netHeadYaw, float headPitch) {
            boolean hasBlacksmithUpgrade = entitylivingbaseIn.hasBlacksmithUpgrade();
            ItemStack offhand = entitylivingbaseIn.getOffhandItem();
            if (!hasBlacksmithUpgrade && offhand.isEmpty())
                return;

            GreedyFamiliarModel model = this.getParentModel();
            FirstPersonRenderer renderer = Minecraft.getInstance().getItemInHandRenderer();
            matrixStackIn.pushPose();

            if (hasBlacksmithUpgrade) {
                model.body.translateAndRotate(matrixStackIn);
                model.rightArm.translateAndRotate(matrixStackIn);

                matrixStackIn.translate(-0.06, 0.2, -0.1);
                matrixStackIn.mulPose(new Quaternion(0, 90, -45, true));

                renderer.renderItem(entitylivingbaseIn, new ItemStack(Items.IRON_PICKAXE),
                        ItemCameraTransforms.TransformType.GROUND, false, matrixStackIn, bufferIn, packedLightIn);
                matrixStackIn.popPose();
            }

            if (!offhand.isEmpty()) {
                matrixStackIn.pushPose();
                model.body.translateAndRotate(matrixStackIn);
                model.leftArm.translateAndRotate(matrixStackIn);

                matrixStackIn.translate(0.06, 0.2, -0.17);
                matrixStackIn.mulPose(new Quaternion(0, 45, 0, true));
                float size = 0.75f;
                matrixStackIn.scale(size, size, size);

                renderer.renderItem(entitylivingbaseIn, offhand, ItemCameraTransforms.TransformType.GROUND, false,
                        matrixStackIn, bufferIn, packedLightIn);
                matrixStackIn.popPose();
            }
        }
    }
}