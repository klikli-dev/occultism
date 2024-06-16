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
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.joml.Quaternionf;
import software.bernie.geckolib.util.Color;

public class GuardianFamiliarRenderer extends MobRenderer<GuardianFamiliarEntity, GuardianFamiliarModel> {

    private static final ResourceLocation TEXTURES = ResourceLocation.fromNamespaceAndPath(Occultism.MODID,
            "textures/entity/guardian_familiar.png");

    public GuardianFamiliarRenderer(EntityRendererProvider.Context context) {
        super(context, new GuardianFamiliarModel(context.bakeLayer(OccultismModelLayers.FAMILIAR_GUARDIAN)), 0.3f);
        this.addLayer(new GuardianFamiliarOverlay(this, context));
        this.addLayer(new ToolsLayer(this));
        this.addLayer(new GuardianFamiliarTree(this));
    }

    @Override
    public void render(GuardianFamiliarEntity pEntity, float pEntityYaw, float pPartialTicks, PoseStack ppMatrixStackStack, MultiBufferSource pBuffer, int pPackedLight) {
        ppMatrixStackStack.pushPose();
        boolean noLegs = pEntity.getLives() <= GuardianFamiliarEntity.FLOATING;
        ppMatrixStackStack.translate(0,
                pEntity.isSitting() ? (noLegs ? -0.5 : -0.36) : pEntity.getAnimationHeight(pPartialTicks) * 0.08, 0);
        super.render(pEntity, pEntityYaw, pPartialTicks, ppMatrixStackStack, pBuffer, pPackedLight);
        ppMatrixStackStack.popPose();

    }

    @Override
    public ResourceLocation getTextureLocation(GuardianFamiliarEntity entity) {
        return TEXTURES;
    }

    private static class GuardianFamiliarOverlay extends RenderLayer<GuardianFamiliarEntity, GuardianFamiliarModel> {
        private static final ResourceLocation OVERLAY = ResourceLocation.fromNamespaceAndPath(Occultism.MODID,
                "textures/entity/guardian_familiar_overlay.png");

        private final GuardianFamiliarModel model;

        public GuardianFamiliarOverlay(RenderLayerParent<GuardianFamiliarEntity, GuardianFamiliarModel> parent, EntityRendererProvider.Context context) {
            super(parent);
            this.model = new GuardianFamiliarModel(context.bakeLayer(OccultismModelLayers.FAMILIAR_GUARDIAN));
        }

        @Override
        public void render(PoseStack ppMatrixStackStack, MultiBufferSource pBuffer, int pPackedLight, GuardianFamiliarEntity pLivingEntity, float pLimbSwing, float pLimbSwingAmount, float pPartialTicks, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch) {
            if (!pLivingEntity.isInvisible()) {
                this.getParentModel().copyPropertiesTo(this.model);
                this.model.prepareMobModel(pLivingEntity, pLimbSwing, pLimbSwingAmount, pPartialTicks);
                this.model.setupAnim(pLivingEntity, pLimbSwing, pLimbSwingAmount, pAgeInTicks, pNetHeadYaw, pHeadPitch);
                VertexConsumer ivertexbuilder = pBuffer.getBuffer(RenderType.entityTranslucent(OVERLAY));
                this.model.renderToBuffer(ppMatrixStackStack, ivertexbuilder, pPackedLight,
                        LivingEntityRenderer.getOverlayCoords(pLivingEntity, 0.0F),
                        Color.ofRGBA(
                        pLivingEntity.getRed(),
                        pLivingEntity.getGreen(), pLivingEntity.getBlue(),
                        (Mth.cos(pAgeInTicks / 20) + 1) * 0.3f + 0.4f).getColor());
            }
        }
    }

    private static class ToolsLayer extends RenderLayer<GuardianFamiliarEntity, GuardianFamiliarModel> {

        public ToolsLayer(RenderLayerParent<GuardianFamiliarEntity, GuardianFamiliarModel> parent) {
            super(parent);
        }

        @Override
        public void render(PoseStack pMatrixStack, MultiBufferSource pBuffer, int pPackedLight, GuardianFamiliarEntity pLivingEntity, float pLimbSwing, float pLimbSwingAmount, float pPartialTicks, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch) {
            if (pLivingEntity.isInvisible() || !pLivingEntity.hasTools())
                return;

            ItemInHandRenderer itemRenderer = Minecraft.getInstance().getEntityRenderDispatcher().getItemInHandRenderer();
            GuardianFamiliarModel model = this.getParentModel();

            pMatrixStack.pushPose();
            model.body.translateAndRotate(pMatrixStack);

            pMatrixStack.translate(-0.15, -0.25, -0.25);
            pMatrixStack.mulPose(new Quaternionf().rotateXYZ(0, -60 * ((float) Math.PI / 180F), 0));

            itemRenderer.renderItem(pLivingEntity, new ItemStack(Items.STONE_SWORD), ItemDisplayContext.GROUND, false, pMatrixStack, pBuffer, pPackedLight);
            pMatrixStack.popPose();

            pMatrixStack.pushPose();
            model.body.translateAndRotate(pMatrixStack);

            pMatrixStack.translate(-0.15, 0.1, 0.37);
            pMatrixStack.mulPose(new Quaternionf().rotateXYZ(0, 60 * ((float) Math.PI / 180F), -110 * ((float) Math.PI / 180F)));

            itemRenderer.renderItem(pLivingEntity, new ItemStack(Items.STONE_AXE), ItemDisplayContext.GROUND, false, pMatrixStack, pBuffer, pPackedLight);
            pMatrixStack.popPose();

            if (model.leftArm1.visible) {
                pMatrixStack.pushPose();
                model.body.translateAndRotate(pMatrixStack);
                model.leftArm1.translateAndRotate(pMatrixStack);

                pMatrixStack.translate(0.21, 0.2, 0);
                pMatrixStack.mulPose(new Quaternionf().rotateXYZ(0, 0, 210 * ((float) Math.PI / 180F)));

                itemRenderer.renderItem(pLivingEntity, new ItemStack(Items.STONE_PICKAXE), ItemDisplayContext.GROUND, false, pMatrixStack, pBuffer,
                        pPackedLight);
                pMatrixStack.popPose();
            }
        }
    }

    private static class GuardianFamiliarTree extends RenderLayer<GuardianFamiliarEntity, GuardianFamiliarModel> {
        private static final ResourceLocation TREE = ResourceLocation.fromNamespaceAndPath(Occultism.MODID,
                "textures/entity/guardian_familiar_tree.png");
        private static final ResourceLocation CHRISTMAS = ResourceLocation.fromNamespaceAndPath(Occultism.MODID,
                "textures/entity/guardian_familiar_christmas.png");

        public GuardianFamiliarTree(RenderLayerParent<GuardianFamiliarEntity, GuardianFamiliarModel> renderer) {
            super(renderer);
        }

        @Override
        public void render(PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn,
                           GuardianFamiliarEntity entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks,
                           float ageInTicks, float netHeadYaw, float headPitch) {
            if (entitylivingbaseIn.isInvisible())
                return;

            boolean isChristmas = FamiliarUtil.isChristmas();
            boolean hasTree = entitylivingbaseIn.hasTree();
            VertexConsumer ivertexbuilder = bufferIn
                    .getBuffer(RenderType.entityTranslucent(isChristmas ? CHRISTMAS : TREE));
            GuardianFamiliarModel model = this.getParentModel();
            model.tree1.visible = isChristmas || hasTree;
            model.tree2.visible = isChristmas || hasTree;
            model.renderToBuffer(matrixStackIn, ivertexbuilder, packedLightIn,
                    LivingEntityRenderer.getOverlayCoords(entitylivingbaseIn, 0));
        }
    }

}
