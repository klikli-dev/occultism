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
import com.github.klikli_dev.occultism.client.model.entity.CthulhuFamiliarModel;
import com.github.klikli_dev.occultism.common.entity.familiar.CthulhuFamiliarEntity;
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

public class CthulhuFamiliarRenderer extends MobRenderer<CthulhuFamiliarEntity, CthulhuFamiliarModel> {

    private static final ResourceLocation TEXTURES = new ResourceLocation(Occultism.MODID,
            "textures/entity/cthulhu_familiar.png");

    public CthulhuFamiliarRenderer(EntityRendererProvider.Context context) {
        super(context, new CthulhuFamiliarModel(context.bakeLayer(OccultismModelLayers.FAMILIAR_CTHULHU)), 0.3f);
        this.addLayer(new HeldItemLayer(this));
    }

    @Override
    public void render(CthulhuFamiliarEntity pEntity, float pEntityYaw, float pPartialTicks, PoseStack pMatrixStack, MultiBufferSource pBuffer, int pPackedLight) {

        pMatrixStack.pushPose();

        if (pEntity.isPartying()) {
            float ageInTicks = pEntity.tickCount + pPartialTicks;
            pMatrixStack.translate(0, 1.55, 0);
            pMatrixStack.mulPose(new Quaternion(ageInTicks * 3, 0, 0, true));
            pMatrixStack.translate(0, 0.5, 0);
            pEntity.yBodyRotO = -180;
            pEntity.yBodyRot = -180;
        } else
            pMatrixStack.translate(0, pEntity.isSitting() ? -0.35 : pEntity.getAnimationHeight(pPartialTicks) * 0.08, 0);

        super.render(pEntity, pEntityYaw, pPartialTicks, pMatrixStack, pBuffer, pPackedLight);
        pMatrixStack.popPose();
    }

    @Override
    public ResourceLocation getTextureLocation(CthulhuFamiliarEntity entity) {
        return TEXTURES;
    }

    public class HeldItemLayer extends RenderLayer<CthulhuFamiliarEntity, CthulhuFamiliarModel> {
        public HeldItemLayer(RenderLayerParent<CthulhuFamiliarEntity, CthulhuFamiliarModel> parent) {
            super(parent);
        }

        @Override
        public void render(PoseStack matrixStack, MultiBufferSource pBuffer, int pPackedLight, CthulhuFamiliarEntity pLivingEntity, float pLimbSwing, float pLimbSwingAmount, float pPartialTicks, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch) {
            if (pLivingEntity.isGiving()) {
                matrixStack.pushPose();
                matrixStack.scale(1.25f, -1.25f, 1.25f);
                matrixStack.translate(0, -0.75, -0.35);
                matrixStack.mulPose(new Quaternion(-65, 0, 0, true));
                Minecraft.getInstance().getEntityRenderDispatcher().getItemInHandRenderer().renderItem(pLivingEntity, new ItemStack(Items.POPPY),
                        ItemTransforms.TransformType.GROUND, false, matrixStack, pBuffer,
                        pPackedLight);
                matrixStack.popPose();
            }

        }
    }

}
