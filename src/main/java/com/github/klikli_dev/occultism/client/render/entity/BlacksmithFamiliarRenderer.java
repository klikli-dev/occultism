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
import com.github.klikli_dev.occultism.registry.OccultismModelLayers;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Quaternion;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class BlacksmithFamiliarRenderer extends MobRenderer<BlacksmithFamiliarEntity, BlacksmithFamiliarModel> {

    private static final ResourceLocation TEXTURES = new ResourceLocation(Occultism.MODID,
            "textures/entity/blacksmith_familiar.png");

    public BlacksmithFamiliarRenderer(EntityRendererProvider.Context context) {
        super(context, new BlacksmithFamiliarModel(context.bakeLayer(OccultismModelLayers.FAMILIAR_BLACKSMITH)), 0.3f);
        this.addLayer(new IngotsLayer(this));
    }

    @Override
    public ResourceLocation getTextureLocation(BlacksmithFamiliarEntity entity) {
        return TEXTURES;
    }

    @Override
    protected void setupRotations(BlacksmithFamiliarEntity pEntityLiving, PoseStack pMatrixStack, float pAgeInTicks, float pRotationYaw, float pPartialTicks) {
        if (!pEntityLiving.isSitting())
            super.setupRotations(pEntityLiving, pMatrixStack, pAgeInTicks, pRotationYaw, pPartialTicks);
    }

    private class IngotsLayer extends RenderLayer<BlacksmithFamiliarEntity, BlacksmithFamiliarModel> {
        public IngotsLayer(RenderLayerParent<BlacksmithFamiliarEntity, BlacksmithFamiliarModel> parent) {
            super(parent);
        }

        @Override
        public void render(PoseStack pMatrixStack, MultiBufferSource pBuffer, int pPackedLight, BlacksmithFamiliarEntity pLivingEntity, float pLimbSwing, float pLimbSwingAmount, float pPartialTicks, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch) {
            pMatrixStack.pushPose();
            float scale = 0.5f;
            pMatrixStack.scale(scale, scale, scale);
            for (int i = 0; i < pLivingEntity.getBars(); i++) {
                pMatrixStack.pushPose();
                pMatrixStack.translate(i % 2 == 0 ? -0.3 : 0.3, 2.03 - i / 2 * 0.03, -0.15);
                pMatrixStack.mulPose(new Quaternion(-90, 0, i, true));
                Minecraft.getInstance().getEntityRenderDispatcher().getItemInHandRenderer().renderItem(pLivingEntity,
                        new ItemStack(Items.IRON_INGOT), ItemTransforms.TransformType.GROUND, false,
                        pMatrixStack, pBuffer, pPackedLight);
                pMatrixStack.popPose();
            }
            pMatrixStack.popPose();
        }
    }
}
