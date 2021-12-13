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

import java.util.Map;

import com.github.klikli_dev.occultism.Occultism;
import com.github.klikli_dev.occultism.client.model.entity.CthulhuFamiliarModel;
import com.github.klikli_dev.occultism.client.model.entity.HeadlessFamiliarModel;
import com.github.klikli_dev.occultism.common.entity.HeadlessFamiliarEntity;
import com.github.klikli_dev.occultism.registry.OccultismEntities;
import com.github.klikli_dev.occultism.util.FamiliarUtil;
import com.google.common.collect.ImmutableMap;
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
import net.minecraft.client.renderer.entity.model.GenericHeadModel;
import net.minecraft.client.renderer.entity.model.HumanoidHeadModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Quaternion;

public class HeadlessFamiliarRenderer extends MobRenderer<HeadlessFamiliarEntity, HeadlessFamiliarModel> {

    private static final ResourceLocation TEXTURE = new ResourceLocation(Occultism.MODID,
            "textures/entity/headless_familiar.png");

    public HeadlessFamiliarRenderer(EntityRendererManager renderManagerIn) {
        super(renderManagerIn, new HeadlessFamiliarModel(), 0.3f);
        this.addLayer(new HeadLayer(this));
        this.addLayer(new WeaponLayer(this));
        this.addLayer(new RebuiltLayer(this));
        this.addLayer(new PumpkinLayer(this));
    }

    @Override
    public ResourceLocation getTextureLocation(HeadlessFamiliarEntity entity) {
        return TEXTURE;
    }

    @Override
    public void render(HeadlessFamiliarEntity entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn,
            IRenderTypeBuffer bufferIn, int packedLightIn) {
        matrixStackIn.pushPose();
        if (entityIn.isSitting())
            matrixStackIn.translate(0, -0.12, 0);
        super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
        matrixStackIn.popPose();
    }

    private static class RebuiltLayer extends LayerRenderer<HeadlessFamiliarEntity, HeadlessFamiliarModel> {
        public RebuiltLayer(IEntityRenderer<HeadlessFamiliarEntity, HeadlessFamiliarModel> renderer) {
            super(renderer);
        }

        @Override
        public void render(MatrixStack matrix, IRenderTypeBuffer bufferIn, int packedLightIn,
                HeadlessFamiliarEntity headless, float limbSwing, float limbSwingAmount, float partialTicks,
                float ageInTicks, float netHeadYaw, float headPitch) {
            if (!headless.isHeadlessDead())
                return;

            FirstPersonRenderer renderer = Minecraft.getInstance().getItemInHandRenderer();

            matrix.pushPose();
            HeadlessFamiliarModel model = this.getParentModel();
            model.ratBody1.translateAndRotate(matrix);
            boolean partying = headless.isPartying();

            if (headless.isRebuilt(HeadlessFamiliarEntity.Rebuilt.RightLeg)) {
                matrix.pushPose();
                matrix.mulPose(new Quaternion(0, 130, 0, true));
                matrix.translate(0.3, -0.3, 0);
                this.renderItem(Items.WHEAT, matrix, bufferIn, packedLightIn, headless, renderer);
                matrix.popPose();
            }
            if (headless.isRebuilt(HeadlessFamiliarEntity.Rebuilt.LeftLeg)) {
                matrix.pushPose();
                matrix.mulPose(new Quaternion(0, 50, 0, true));
                matrix.translate(0.3, -0.3, 0);
                this.renderItem(Items.WHEAT, matrix, bufferIn, packedLightIn, headless, renderer);
                matrix.popPose();
            }
            if (headless.isRebuilt(HeadlessFamiliarEntity.Rebuilt.Body)) {
                matrix.pushPose();
                float size = 1.2f;
                matrix.scale(size, size, size);
                matrix.mulPose(new Quaternion(0, 0, 0, true));
                matrix.translate(0, -0.45, -0.05);
                this.renderItem(Items.HAY_BLOCK, matrix, bufferIn, packedLightIn, headless, renderer);
                matrix.translate(0, -0.25, 0);
                this.renderItem(Items.HAY_BLOCK, matrix, bufferIn, packedLightIn, headless, renderer);
                matrix.popPose();
            }
            if (headless.isRebuilt(HeadlessFamiliarEntity.Rebuilt.RightArm)) {
                matrix.pushPose();
                matrix.mulPose(new Quaternion(0, 180 + (partying ? MathHelper.sin(ageInTicks / 3) * 20 : 0), 0, true));
                matrix.translate(0.25, -0.6, 0.05);
                this.renderItem(Items.STICK, matrix, bufferIn, packedLightIn, headless, renderer);
                matrix.popPose();
            }
            if (headless.isRebuilt(HeadlessFamiliarEntity.Rebuilt.LeftArm)) {
                matrix.pushPose();
                matrix.mulPose(new Quaternion(0, partying ? MathHelper.sin(ageInTicks / 3) * 20 : 0, 0, true));
                matrix.translate(0.25, -0.6, -0.05);
                this.renderItem(Items.STICK, matrix, bufferIn, packedLightIn, headless, renderer);
                matrix.popPose();
            }
            if (headless.isRebuilt(HeadlessFamiliarEntity.Rebuilt.Head)) {
                matrix.pushPose();
                matrix.scale(-1, -1, 1);
                matrix.translate(0, 0.7, -0.06);
                matrix.mulPose(new Quaternion(0, partying ? ageInTicks * 8 : -netHeadYaw, 0, true));

                this.renderItem(Items.CARVED_PUMPKIN, matrix, bufferIn, packedLightIn, headless, renderer);
                matrix.popPose();
            }
            matrix.popPose();
        }

        private void renderItem(Item item, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn,
                HeadlessFamiliarEntity entitylivingbaseIn, FirstPersonRenderer renderer) {
            renderer.renderItem(entitylivingbaseIn, new ItemStack(item), ItemCameraTransforms.TransformType.GROUND,
                    false, matrixStackIn, bufferIn, packedLightIn);
        }

    }

    private static class WeaponLayer extends LayerRenderer<HeadlessFamiliarEntity, HeadlessFamiliarModel> {
        public WeaponLayer(IEntityRenderer<HeadlessFamiliarEntity, HeadlessFamiliarModel> renderer) {
            super(renderer);
        }

        @Override
        public void render(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn,
                HeadlessFamiliarEntity entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks,
                float ageInTicks, float netHeadYaw, float headPitch) {
            if (entitylivingbaseIn.isHeadlessDead())
                return;

            matrixStackIn.pushPose();
            HeadlessFamiliarModel model = this.getParentModel();

            model.ratBody1.translateAndRotate(matrixStackIn);
            model.body.translateAndRotate(matrixStackIn);
            model.rightArm.translateAndRotate(matrixStackIn);

            matrixStackIn.translate(-0.05f, 0.16, -0.08);

            matrixStackIn.mulPose(new Quaternion(0, 90, -50, true));

            Minecraft.getInstance().getItemInHandRenderer().renderItem(entitylivingbaseIn,
                    entitylivingbaseIn.getWeaponItem(), ItemCameraTransforms.TransformType.GROUND, false, matrixStackIn,
                    bufferIn, packedLightIn);
            matrixStackIn.popPose();
        }
    }

    private static class PumpkinLayer extends LayerRenderer<HeadlessFamiliarEntity, HeadlessFamiliarModel> {
        private static final ResourceLocation PUMPKIN = new ResourceLocation(Occultism.MODID,
                "textures/entity/headless_familiar_pumpkin.png");
        private static final ResourceLocation CHRISTMAS = new ResourceLocation(Occultism.MODID,
                "textures/entity/headless_familiar_christmas.png");

        public PumpkinLayer(IEntityRenderer<HeadlessFamiliarEntity, HeadlessFamiliarModel> renderer) {
            super(renderer);
        }

        public void render(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn,
                HeadlessFamiliarEntity entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks,
                float ageInTicks, float netHeadYaw, float headPitch) {
            if (entitylivingbaseIn.isInvisible())
                return;

            boolean isChristmas = FamiliarUtil.isChristmas();
            boolean hasPumpkin = !entitylivingbaseIn.hasHead();
            IVertexBuilder ivertexbuilder = bufferIn
                    .getBuffer(RenderType.entityTranslucent(isChristmas ? CHRISTMAS : PUMPKIN));
            HeadlessFamiliarModel model = this.getParentModel();
            model.pumpkin1.visible = hasPumpkin;
            model.snowmanHat1.visible = isChristmas;
            model.snowmanHat2.visible = isChristmas;
            model.snowmanLeftEye.visible = isChristmas;
            model.snowmanRightEye.visible = isChristmas;
            model.snowmanMouth1.visible = isChristmas;
            model.snowmanMouth2.visible = isChristmas;
            model.snowmanMouth3.visible = isChristmas;
            model.snowmanNose.visible = isChristmas;
            model.pumpkin2.visible = !isChristmas;
            model.pumpkin3.visible = !isChristmas;
            model.pumpkin4.visible = !isChristmas;
            model.renderToBuffer(matrixStackIn, ivertexbuilder, packedLightIn,
                    LivingRenderer.getOverlayCoords(entitylivingbaseIn, 0), 1, 1, 1, 1);
        }
    }

    public static class HeadLayer extends LayerRenderer<HeadlessFamiliarEntity, HeadlessFamiliarModel> {

        private static Map<EntityType<?>, ResourceLocation> textures;

        HumanoidHeadModel humanoidHead = new HumanoidHeadModel();
        GenericHeadModel genericHead = new GenericHeadModel(0, 0, 64, 32);
        GenericHeadModel spiderHead = new GenericHeadModel(32, 4, 64, 32);
        GenericHeadModel cthulhuHead = new CthulhuHeadModel();

        public HeadLayer(IEntityRenderer<HeadlessFamiliarEntity, HeadlessFamiliarModel> renderer) {
            super(renderer);
        }

        private static ResourceLocation getTexture(EntityType<?> type) {
            if (textures == null) {
                ImmutableMap.Builder<EntityType<?>, ResourceLocation> builder = new ImmutableMap.Builder<>();
                builder.put(EntityType.PLAYER, DefaultPlayerSkin.getDefaultSkin());
                builder.put(EntityType.ZOMBIE, new ResourceLocation("textures/entity/zombie/zombie.png"));
                builder.put(EntityType.SKELETON, new ResourceLocation("textures/entity/skeleton/skeleton.png"));
                builder.put(EntityType.WITHER_SKELETON,
                        new ResourceLocation("textures/entity/skeleton/wither_skeleton.png"));
                builder.put(EntityType.CREEPER, new ResourceLocation("textures/entity/creeper/creeper.png"));
                builder.put(EntityType.SPIDER, new ResourceLocation("textures/entity/spider/spider.png"));
                builder.put(OccultismEntities.CTHULHU_FAMILIAR.get(),
                        new ResourceLocation(Occultism.MODID, "textures/entity/cthulhu_familiar.png"));
                textures = builder.build();
            }
            return textures.get(type);
        }

        private GenericHeadModel getHeadModel(EntityType<?> type) {
            if (type == EntityType.SKELETON || type == EntityType.WITHER_SKELETON || type == EntityType.CREEPER)
                return this.genericHead;
            else if (type == EntityType.PLAYER || type == EntityType.ZOMBIE)
                return this.humanoidHead;
            else if (type == EntityType.SPIDER)
                return this.spiderHead;
            else if (type == OccultismEntities.CTHULHU_FAMILIAR.get())
                return this.cthulhuHead;

            return null;
        }

        @Override
        public void render(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn,
                HeadlessFamiliarEntity entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks,
                float ageInTicks, float netHeadYaw, float headPitch) {

            if (entitylivingbaseIn.isHeadlessDead())
                return;

            EntityType<?> headType = entitylivingbaseIn.getHeadType();
            if (headType == null)
                return;

            GenericHeadModel head = this.getHeadModel(headType);
            if (head == null)
                return;

            matrixStackIn.pushPose();
            HeadlessFamiliarModel model = this.getParentModel();
            model.ratBody1.translateAndRotate(matrixStackIn);
            model.body.translateAndRotate(matrixStackIn);
            model.leftArm.translateAndRotate(matrixStackIn);

            float size = 0.5f;
            matrixStackIn.scale(size, size, size);
            matrixStackIn.translate(0.15, 0.5, -0.12);
            matrixStackIn.mulPose(new Quaternion(90, 0, 0, true));

            ResourceLocation texture = getTexture(headType);

            if (texture != null) {
                IVertexBuilder builder = bufferIn.getBuffer(RenderType.entityCutoutNoCull(texture));
                head.renderToBuffer(matrixStackIn, builder, packedLightIn, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1);
            }

            matrixStackIn.popPose();
        }
    }

    private static class CthulhuHeadModel extends GenericHeadModel {
        private final CthulhuFamiliarModel model = new CthulhuFamiliarModel();

        @Override
        public void renderToBuffer(MatrixStack pMatrixStack, IVertexBuilder pBuffer, int pPackedLight,
                int pPackedOverlay, float pRed, float pGreen, float pBlue, float pAlpha) {
            this.model.trunk1.visible = false;
            this.model.trunk2.visible = false;
            this.model.trunk3.visible = false;
            this.model.hat1.visible = false;
            pMatrixStack.pushPose();
            pMatrixStack.scale(1.5f, 1.5f, 1.5f);
            pMatrixStack.translate(0, 0.35, 0.07);
            pMatrixStack.mulPose(new Quaternion(10, 0, 0, true));
            this.model.head.render(pMatrixStack, pBuffer, pPackedLight, pPackedOverlay);
            pMatrixStack.popPose();
        }
    }
}