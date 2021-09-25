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
import com.github.klikli_dev.occultism.client.model.entity.BlacksmithFamiliarModel;
import com.github.klikli_dev.occultism.common.entity.BlacksmithFamiliarEntity;
import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Quaternion;

public class BlacksmithFamiliarRenderer extends MobRenderer<BlacksmithFamiliarEntity, BlacksmithFamiliarModel> {

    private static final ResourceLocation TEXTURES = new ResourceLocation(Occultism.MODID,
            "textures/entity/blacksmith_familiar.png");

    public BlacksmithFamiliarRenderer(EntityRendererManager renderManagerIn) {
        super(renderManagerIn, new BlacksmithFamiliarModel(), 0.3f);
        this.addLayer(new IngotsLayer(this));
    }

    @Override
    public void render(BlacksmithFamiliarEntity entityIn, float entityYaw, float partialTicks,
            MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
        super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
    }

    @Override
    public ResourceLocation getTextureLocation(BlacksmithFamiliarEntity entity) {
        return TEXTURES;
    }

    private class IngotsLayer extends LayerRenderer<BlacksmithFamiliarEntity, BlacksmithFamiliarModel> {
        public IngotsLayer(IEntityRenderer<BlacksmithFamiliarEntity, BlacksmithFamiliarModel> renderer) {
            super(renderer);
        }

        @Override
        public void render(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn,
                BlacksmithFamiliarEntity entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks,
                float ageInTicks, float netHeadYaw, float headPitch) {
            matrixStackIn.pushPose();
            float scale = 0.5f;
            matrixStackIn.scale(scale, scale, scale);
            for (int i = 0; i < entitylivingbaseIn.getBars(); i++) {
                matrixStackIn.pushPose();
                matrixStackIn.translate(i % 2 == 0 ? -0.3 : 0.3, 2.03 - i / 2 * 0.03, -0.15);
                matrixStackIn.mulPose(new Quaternion(-90, 0, i, true));
                Minecraft.getInstance().getItemInHandRenderer().renderItem(entitylivingbaseIn,
                        new ItemStack(Items.IRON_INGOT), ItemCameraTransforms.TransformType.GROUND, false,
                        matrixStackIn, bufferIn, packedLightIn);
                matrixStackIn.popPose();
            }
            matrixStackIn.popPose();

        }
    }
}
