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
import com.github.klikli_dev.occultism.client.model.entity.HeadlessFamiliarModel;
import com.github.klikli_dev.occultism.common.entity.familiar.HeadlessFamiliarEntity;
import com.github.klikli_dev.occultism.registry.OccultismEntities;
import com.github.klikli_dev.occultism.registry.OccultismModelLayers;
import com.github.klikli_dev.occultism.util.FamiliarUtil;
import com.google.common.collect.ImmutableMap;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Quaternion;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.SkullModel;
import net.minecraft.client.model.SkullModelBase;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.Map;

public class HeadlessFamiliarRenderer extends MobRenderer<HeadlessFamiliarEntity, HeadlessFamiliarModel> {

    private static final ResourceLocation TEXTURE = new ResourceLocation(Occultism.MODID,
            "textures/entity/headless_familiar.png");

    public HeadlessFamiliarRenderer(EntityRendererProvider.Context context) {
        super(context, new HeadlessFamiliarModel(context.bakeLayer(OccultismModelLayers.FAMILIAR_HEADLESS)), 0.3f);
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
    public void render(HeadlessFamiliarEntity pEntity, float pEntityYaw, float pPartialTicks, PoseStack pMatrixStack, MultiBufferSource pBuffer, int pPackedLight) {
        pMatrixStack.pushPose();
        if (pEntity.isSitting())
            pMatrixStack.translate(0, -0.12, 0);
        super.render(pEntity, pEntityYaw, pPartialTicks, pMatrixStack, pBuffer, pPackedLight);
        pMatrixStack.popPose();

    }

    private static class RebuiltLayer extends RenderLayer<HeadlessFamiliarEntity, HeadlessFamiliarModel> {
        public RebuiltLayer(RenderLayerParent<HeadlessFamiliarEntity, HeadlessFamiliarModel> parent) {
            super(parent);
        }

        @Override
        public void render(PoseStack matrix, MultiBufferSource bufferIn, int packedLightIn, HeadlessFamiliarEntity headless, float pLimbSwing, float pLimbSwingAmount, float pPartialTicks, float ageInTicks, float netHeadYaw, float pHeadPitch) {
            if (!headless.isHeadlessDead())
                return;

            ItemInHandRenderer renderer = Minecraft.getInstance().getItemInHandRenderer();

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
                matrix.mulPose(new Quaternion(0, 180 + (partying ? Mth.sin(ageInTicks / 3) * 20 : 0), 0, true));
                matrix.translate(0.25, -0.6, 0.05);
                this.renderItem(Items.STICK, matrix, bufferIn, packedLightIn, headless, renderer);
                matrix.popPose();
            }
            if (headless.isRebuilt(HeadlessFamiliarEntity.Rebuilt.LeftArm)) {
                matrix.pushPose();
                matrix.mulPose(new Quaternion(0, partying ? Mth.sin(ageInTicks / 3) * 20 : 0, 0, true));
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


        private void renderItem(Item item, PoseStack pMatrixStack, MultiBufferSource bufferIn, int packedLightIn,
                                HeadlessFamiliarEntity pLivingEntity, ItemInHandRenderer renderer) {
            renderer.renderItem(pLivingEntity, new ItemStack(item), ItemTransforms.TransformType.GROUND,
                    false, pMatrixStack, bufferIn, packedLightIn);
        }

    }

    private static class WeaponLayer extends RenderLayer<HeadlessFamiliarEntity, HeadlessFamiliarModel> {
        public WeaponLayer(RenderLayerParent<HeadlessFamiliarEntity, HeadlessFamiliarModel> parent) {
            super(parent);
        }

        @Override
        public void render(PoseStack pMatrixStack, MultiBufferSource pBuffer, int pPackedLight, HeadlessFamiliarEntity pLivingEntity, float pLimbSwing, float pLimbSwingAmount, float pPartialTicks, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch) {
            if (pLivingEntity.isHeadlessDead())
                return;

            pMatrixStack.pushPose();
            HeadlessFamiliarModel model = this.getParentModel();

            model.ratBody1.translateAndRotate(pMatrixStack);
            model.body.translateAndRotate(pMatrixStack);
            model.rightArm.translateAndRotate(pMatrixStack);

            pMatrixStack.translate(-0.05f, 0.16, -0.08);

            pMatrixStack.mulPose(new Quaternion(0, 90, -50, true));


            Minecraft.getInstance().getItemInHandRenderer().renderItem(pLivingEntity,
                    pLivingEntity.getWeaponItem(), ItemTransforms.TransformType.GROUND, false, pMatrixStack,
                    pBuffer, pPackedLight);
            pMatrixStack.popPose();
        }
    }

    private static class PumpkinLayer extends RenderLayer<HeadlessFamiliarEntity, HeadlessFamiliarModel> {
        private static final ResourceLocation PUMPKIN = new ResourceLocation(Occultism.MODID,
                "textures/entity/headless_familiar_pumpkin.png");
        private static final ResourceLocation CHRISTMAS = new ResourceLocation(Occultism.MODID,
                "textures/entity/headless_familiar_christmas.png");

        public PumpkinLayer(RenderLayerParent<HeadlessFamiliarEntity, HeadlessFamiliarModel> renderer) {
            super(renderer);
        }

        @Override
        public void render(PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn,
                           HeadlessFamiliarEntity entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks,
                           float ageInTicks, float netHeadYaw, float headPitch) {
            if (entitylivingbaseIn.isInvisible())
                return;

            boolean isChristmas = FamiliarUtil.isChristmas();
            boolean hasPumpkin = !entitylivingbaseIn.hasHead();
            VertexConsumer ivertexbuilder = bufferIn
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
                    LivingEntityRenderer.getOverlayCoords(entitylivingbaseIn, 0), 1, 1, 1, 1);
        }
    }

    public static class HeadLayer extends RenderLayer<HeadlessFamiliarEntity, HeadlessFamiliarModel> {

        private static Map<EntityType<?>, ResourceLocation> textures;
        private static Map<EntityType<?>, SkullModelBase> skulls;

        public HeadLayer(RenderLayerParent<HeadlessFamiliarEntity, HeadlessFamiliarModel> parent) {
            super(parent);
        }

        private static ResourceLocation getTexture(EntityType<?> type) {
            if (textures == null) {
                ImmutableMap.Builder<EntityType<?>, ResourceLocation> builder = new ImmutableMap.Builder<>();
                builder.put(EntityType.PLAYER, DefaultPlayerSkin.getDefaultSkin());
                builder.put(EntityType.SKELETON, new ResourceLocation("textures/entity/skeleton/skeleton.png"));
                builder.put(EntityType.WITHER_SKELETON, new ResourceLocation("textures/entity/skeleton/wither_skeleton.png"));
                builder.put(EntityType.ZOMBIE, new ResourceLocation("textures/entity/zombie/zombie.png"));
                builder.put(EntityType.CREEPER, new ResourceLocation("textures/entity/creeper/creeper.png"));
                builder.put(EntityType.SPIDER, new ResourceLocation("textures/entity/spider/spider.png"));
                builder.put(OccultismEntities.CTHULHU_FAMILIAR.get(),
                        new ResourceLocation(Occultism.MODID, "textures/entity/cthulhu_familiar.png"));
                textures = builder.build();
            }
            return textures.get(type);
        }

        private static SkullModelBase getHeadModel(EntityType<?> type) {
            if (skulls == null) {
                EntityModelSet entityModels = Minecraft.getInstance().getEntityModels();
                ImmutableMap.Builder<EntityType<?>, SkullModelBase> builder = new ImmutableMap.Builder<>();
                builder.put(EntityType.SKELETON, new SkullModel(entityModels.bakeLayer(ModelLayers.SKELETON_SKULL)));
                builder.put(EntityType.WITHER_SKELETON, new SkullModel(entityModels.bakeLayer(ModelLayers.WITHER_SKELETON_SKULL)));
                builder.put(EntityType.PLAYER, new SkullModel(entityModels.bakeLayer(ModelLayers.PLAYER_HEAD)));
                builder.put(EntityType.ZOMBIE, new SkullModel(entityModels.bakeLayer(ModelLayers.ZOMBIE_HEAD)));
                builder.put(EntityType.CREEPER, new SkullModel(entityModels.bakeLayer(ModelLayers.CREEPER_HEAD)));
                builder.put(EntityType.SPIDER, new SkullModel(entityModels.bakeLayer(ModelLayers.SPIDER)));
                builder.put(OccultismEntities.CTHULHU_FAMILIAR.get(), new CthulhuHeadModel(entityModels.bakeLayer(OccultismModelLayers.FAMILIAR_CTHULHU)));
                skulls = builder.build();
            }
            return skulls.get(type);
        }

        @Override
        public void render(PoseStack pMatrixStack, MultiBufferSource pBuffer, int pPackedLight, HeadlessFamiliarEntity pLivingEntity, float pLimbSwing, float pLimbSwingAmount, float pPartialTicks, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch) {
            if (pLivingEntity.isHeadlessDead())
                return;

            EntityType<?> headType = pLivingEntity.getHeadType();
            if (headType == null)
                return;

            SkullModelBase head = getHeadModel(headType);
            if (head == null)
                return;

            pMatrixStack.pushPose();
            HeadlessFamiliarModel model = this.getParentModel();
            model.ratBody1.translateAndRotate(pMatrixStack);
            model.body.translateAndRotate(pMatrixStack);
            model.leftArm.translateAndRotate(pMatrixStack);

            float size = 0.5f;
            pMatrixStack.scale(size, size, size);
            pMatrixStack.translate(0.15, 0.5, -0.12);
            pMatrixStack.mulPose(new Quaternion(90, 0, 0, true));

            ResourceLocation texture = getTexture(headType);

            if (texture != null) {
                VertexConsumer builder = pBuffer.getBuffer(RenderType.entityCutoutNoCull(texture));
                head.renderToBuffer(pMatrixStack, builder, pPackedLight, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1);
            }

            pMatrixStack.popPose();
        }
    }

    private static class CthulhuHeadModel extends SkullModelBase {
        private final CthulhuFamiliarModel model;
        protected final ModelPart head;

        public CthulhuHeadModel(ModelPart part) {
            this.model = new CthulhuFamiliarModel(part);
            this.head = part.getChild("body").getChild("head");
        }

        @Override
        public void renderToBuffer(PoseStack pMatrixStack, VertexConsumer pBuffer, int pPackedLight, int pPackedOverlay, float pRed, float pGreen, float pBlue, float pAlpha) {
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

        public void setupAnim(float p_103811_, float p_103812_, float p_103813_) {
            this.head.yRot = p_103812_ * ((float)Math.PI / 180F);
            this.head.xRot = p_103813_ * ((float)Math.PI / 180F);
        }
    }
}