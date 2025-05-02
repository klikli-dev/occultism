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

import com.google.common.collect.ImmutableMap;
import com.klikli_dev.occultism.Occultism;
import com.klikli_dev.occultism.client.model.entity.CthulhuFamiliarModel;
import com.klikli_dev.occultism.client.model.entity.HeadlessFamiliarModel;
import com.klikli_dev.occultism.common.entity.familiar.HeadlessFamiliarEntity;
import com.klikli_dev.occultism.registry.OccultismEntities;
import com.klikli_dev.occultism.registry.OccultismModelLayers;
import com.klikli_dev.occultism.util.FamiliarUtil;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.SkullModel;
import net.minecraft.client.model.SkullModelBase;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
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
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.joml.Quaternionf;

import java.util.Map;

public class HeadlessFamiliarRenderer extends MobRenderer<HeadlessFamiliarEntity, HeadlessFamiliarModel> {

    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(Occultism.MODID,
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

            ItemInHandRenderer renderer = Minecraft.getInstance().getEntityRenderDispatcher().getItemInHandRenderer();

            matrix.pushPose();
            HeadlessFamiliarModel model = this.getParentModel();
            model.ratBody1.translateAndRotate(matrix);
            boolean partying = headless.isPartying();

            if (headless.isRebuilt(HeadlessFamiliarEntity.Rebuilt.RightLeg)) {
                matrix.pushPose();
                matrix.mulPose(new Quaternionf().rotateXYZ(0, 130 * ((float) Math.PI / 180F), 0));
                matrix.translate(0.3, -0.3, 0);
                this.renderItem(Items.WHEAT, matrix, bufferIn, packedLightIn, headless, renderer);
                matrix.popPose();
            }
            if (headless.isRebuilt(HeadlessFamiliarEntity.Rebuilt.LeftLeg)) {
                matrix.pushPose();
                matrix.mulPose(new Quaternionf().rotateXYZ(0, 50 * ((float) Math.PI / 180F), 0));
                matrix.translate(0.3, -0.3, 0);
                this.renderItem(Items.WHEAT, matrix, bufferIn, packedLightIn, headless, renderer);
                matrix.popPose();
            }
            if (headless.isRebuilt(HeadlessFamiliarEntity.Rebuilt.Body)) {
                matrix.pushPose();
                float size = 1.2f;
                matrix.scale(size, size, size);
                matrix.mulPose(new Quaternionf().rotateXYZ(0, 0, 0));
                matrix.translate(0, -0.45, -0.05);
                this.renderItem(Items.HAY_BLOCK, matrix, bufferIn, packedLightIn, headless, renderer);
                matrix.translate(0, -0.25, 0);
                this.renderItem(Items.HAY_BLOCK, matrix, bufferIn, packedLightIn, headless, renderer);
                matrix.popPose();
            }
            if (headless.isRebuilt(HeadlessFamiliarEntity.Rebuilt.RightArm)) {
                matrix.pushPose();
                matrix.mulPose(new Quaternionf().rotateXYZ(0, (180 + (partying ? Mth.sin(ageInTicks / 3) * 20 : 0)) * ((float) Math.PI / 180F), 0));
                matrix.translate(0.25, -0.6, 0.05);
                this.renderItem(Items.STICK, matrix, bufferIn, packedLightIn, headless, renderer);
                matrix.popPose();
            }
            if (headless.isRebuilt(HeadlessFamiliarEntity.Rebuilt.LeftArm)) {
                matrix.pushPose();
                matrix.mulPose(new Quaternionf().rotateXYZ(0, (partying ? Mth.sin(ageInTicks / 3) * 20 : 0) * ((float) Math.PI / 180F), 0));
                matrix.translate(0.25, -0.6, -0.05);
                this.renderItem(Items.STICK, matrix, bufferIn, packedLightIn, headless, renderer);
                matrix.popPose();
            }
            if (headless.isRebuilt(HeadlessFamiliarEntity.Rebuilt.Head)) {
                matrix.pushPose();
                matrix.scale(-1, -1, 1);
                matrix.translate(0, 0.7, -0.06);
                matrix.mulPose(new Quaternionf().rotateXYZ(0, (partying ? ageInTicks * 8 : -netHeadYaw) * ((float) Math.PI / 180F), 0));

                this.renderItem(Items.CARVED_PUMPKIN, matrix, bufferIn, packedLightIn, headless, renderer);
                matrix.popPose();
            }
            matrix.popPose();
        }


        private void renderItem(Item item, PoseStack pMatrixStack, MultiBufferSource bufferIn, int packedLightIn,
                                HeadlessFamiliarEntity pLivingEntity, ItemInHandRenderer renderer) {
            renderer.renderItem(pLivingEntity, new ItemStack(item), ItemDisplayContext.GROUND,
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

            pMatrixStack.mulPose(new Quaternionf().rotateXYZ(0, 90 * ((float) Math.PI / 180F), -50 * ((float) Math.PI / 180F)));


            Minecraft.getInstance().getEntityRenderDispatcher().getItemInHandRenderer().renderItem(pLivingEntity,
                    pLivingEntity.getWeaponItem(), ItemDisplayContext.GROUND, false, pMatrixStack,
                    pBuffer, pPackedLight);
            pMatrixStack.popPose();
        }
    }

    private static class PumpkinLayer extends RenderLayer<HeadlessFamiliarEntity, HeadlessFamiliarModel> {
        private static final ResourceLocation PUMPKIN = ResourceLocation.fromNamespaceAndPath(Occultism.MODID,
                "textures/entity/headless_familiar_pumpkin.png");
        private static final ResourceLocation CHRISTMAS = ResourceLocation.fromNamespaceAndPath(Occultism.MODID,
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
                    LivingEntityRenderer.getOverlayCoords(entitylivingbaseIn, 0));
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
                builder.put(EntityType.PLAYER, DefaultPlayerSkin.getDefaultTexture());
                builder.put(EntityType.SKELETON, ResourceLocation.parse("textures/entity/skeleton/skeleton.png"));
                builder.put(EntityType.WITHER_SKELETON, ResourceLocation.parse("textures/entity/skeleton/wither_skeleton.png"));
                builder.put(EntityType.STRAY, ResourceLocation.parse("textures/entity/skeleton/stray.png"));
                builder.put(EntityType.BOGGED, ResourceLocation.parse("textures/entity/skeleton/bogged_overlay.png"));
                builder.put(EntityType.ZOMBIE, ResourceLocation.parse("textures/entity/zombie/zombie.png"));
                builder.put(EntityType.HUSK, ResourceLocation.parse("textures/entity/zombie/husk.png"));
                builder.put(EntityType.DROWNED, ResourceLocation.parse("textures/entity/zombie/drowned_outer_layer.png"));
                builder.put(EntityType.CREEPER, ResourceLocation.parse("textures/entity/creeper/creeper.png"));
                builder.put(EntityType.SPIDER, ResourceLocation.parse("textures/entity/spider/spider.png"));
                builder.put(EntityType.CAVE_SPIDER, ResourceLocation.parse("textures/entity/spider/cave_spider.png"));
                builder.put(EntityType.PIGLIN, ResourceLocation.parse("textures/entity/piglin/piglin.png"));
                builder.put(EntityType.PIGLIN_BRUTE, ResourceLocation.parse("textures/entity/piglin/piglin_brute.png"));
                builder.put(EntityType.ZOMBIFIED_PIGLIN, ResourceLocation.parse("textures/entity/piglin/zombified_piglin.png"));
                builder.put(EntityType.BLAZE, ResourceLocation.parse("textures/entity/blaze.png"));
                builder.put(EntityType.BREEZE, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "textures/entity/breeze_blaze_size.png"));
                builder.put(EntityType.ENDERMAN, ResourceLocation.parse("textures/entity/enderman/enderman.png"));
                builder.put(EntityType.ENDER_DRAGON, ResourceLocation.parse("textures/entity/enderdragon/dragon.png"));
                builder.put(OccultismEntities.CTHULHU_FAMILIAR.get(),
                        ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "textures/entity/cthulhu_familiar.png"));
                builder.put(EntityType.VILLAGER, ResourceLocation.parse("textures/entity/villager/villager.png"));
                builder.put(EntityType.WANDERING_TRADER, ResourceLocation.parse("textures/entity/wandering_trader.png"));
                builder.put(EntityType.ZOMBIE_VILLAGER, ResourceLocation.parse("textures/entity/zombie_villager/zombie_villager.png"));
                builder.put(EntityType.WITCH, ResourceLocation.parse("textures/entity/witch.png"));
                builder.put(EntityType.PILLAGER, ResourceLocation.parse("textures/entity/illager/pillager.png"));
                builder.put(EntityType.VINDICATOR, ResourceLocation.parse("textures/entity/illager/vindicator.png"));
                builder.put(EntityType.EVOKER, ResourceLocation.parse("textures/entity/illager/evoker.png"));
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
                builder.put(EntityType.STRAY, new SkullModel(entityModels.bakeLayer(ModelLayers.SKELETON_SKULL)));
                builder.put(EntityType.BOGGED, new OnlyHeadModel(entityModels.bakeLayer(ModelLayers.BOGGED)));
                builder.put(EntityType.PLAYER, new SkullModel(entityModels.bakeLayer(ModelLayers.PLAYER_HEAD)));
                builder.put(EntityType.ZOMBIE, new SkullModel(entityModels.bakeLayer(ModelLayers.ZOMBIE_HEAD)));
                builder.put(EntityType.HUSK, new SkullModel(entityModels.bakeLayer(ModelLayers.ZOMBIE_HEAD)));
                builder.put(EntityType.DROWNED, new OnlyHeadModel(entityModels.bakeLayer(ModelLayers.DROWNED)));
                builder.put(EntityType.CREEPER, new SkullModel(entityModels.bakeLayer(ModelLayers.CREEPER_HEAD)));
                builder.put(EntityType.SPIDER, new SpiderHeadModel(entityModels.bakeLayer(ModelLayers.SPIDER)));
                builder.put(EntityType.CAVE_SPIDER, new SpiderHeadModel(entityModels.bakeLayer(ModelLayers.CAVE_SPIDER)));
                builder.put(EntityType.PIGLIN, new SkullModel(entityModels.bakeLayer(ModelLayers.PIGLIN_HEAD)));
                builder.put(EntityType.PIGLIN_BRUTE, new SkullModel(entityModels.bakeLayer(ModelLayers.PIGLIN_HEAD)));
                builder.put(EntityType.ZOMBIFIED_PIGLIN, new SkullModel(entityModels.bakeLayer(ModelLayers.PIGLIN_HEAD)));
                builder.put(EntityType.BLAZE, new OnlyHeadModel(entityModels.bakeLayer(ModelLayers.BLAZE)));
                builder.put(EntityType.BREEZE, new OnlyHeadModel(entityModels.bakeLayer(ModelLayers.BLAZE))); //breeze model crash
                builder.put(EntityType.ENDERMAN, new EndermanHeadModel(entityModels.bakeLayer(ModelLayers.ENDERMAN)));
                builder.put(EntityType.ENDER_DRAGON, new SkullModel(entityModels.bakeLayer(ModelLayers.DRAGON_SKULL)));
                builder.put(OccultismEntities.CTHULHU_FAMILIAR.get(), new CthulhuHeadModel(entityModels.bakeLayer(OccultismModelLayers.FAMILIAR_CTHULHU)));
                builder.put(EntityType.VILLAGER, new OnlyHeadModel(entityModels.bakeLayer(ModelLayers.VILLAGER)));
                builder.put(EntityType.WANDERING_TRADER, new OnlyHeadModel(entityModels.bakeLayer(ModelLayers.WANDERING_TRADER)));
                builder.put(EntityType.ZOMBIE_VILLAGER, new OnlyHeadModel(entityModels.bakeLayer(ModelLayers.ZOMBIE_VILLAGER)));
                builder.put(EntityType.WITCH, new OnlyHeadModel(entityModels.bakeLayer(ModelLayers.WITCH)));
                builder.put(EntityType.PILLAGER, new OnlyHeadModel(entityModels.bakeLayer(ModelLayers.PILLAGER)));
                builder.put(EntityType.VINDICATOR, new OnlyHeadModel(entityModels.bakeLayer(ModelLayers.VINDICATOR)));
                builder.put(EntityType.EVOKER, new OnlyHeadModel(entityModels.bakeLayer(ModelLayers.EVOKER)));
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
            pMatrixStack.mulPose(new Quaternionf().rotateXYZ(90 * ((float) Math.PI / 180F), 0, 0));

            ResourceLocation texture = getTexture(headType);

            if (texture != null) {
                VertexConsumer builder = pBuffer.getBuffer(RenderType.entityCutoutNoCull(texture));
                head.renderToBuffer(pMatrixStack, builder, pPackedLight, OverlayTexture.NO_OVERLAY);
            }

            pMatrixStack.popPose();
        }
    }

    private static class CthulhuHeadModel extends SkullModelBase {
        protected final ModelPart head;
        private final CthulhuFamiliarModel model;

        public CthulhuHeadModel(ModelPart part) {
            this.model = new CthulhuFamiliarModel(part);
            this.head = part.getChild("body").getChild("head");
        }

        @Override
        public void renderToBuffer(PoseStack pMatrixStack, VertexConsumer pBuffer, int pPackedLight, int pPackedOverlay, int pColor) {
            this.model.trunk1.visible = false;
            this.model.trunk2.visible = false;
            this.model.trunk3.visible = false;
            this.model.hat1.visible = false;
            pMatrixStack.pushPose();
            pMatrixStack.scale(1.5f, 1.5f, 1.5f);
            pMatrixStack.translate(0, 0.35, 0.07);
            pMatrixStack.mulPose(new Quaternionf().rotateXYZ(10 * ((float) Math.PI / 180F), 0, 0));
            this.model.head.render(pMatrixStack, pBuffer, pPackedLight, pPackedOverlay);
            pMatrixStack.popPose();
        }

        public void setupAnim(float p_103811_, float p_103812_, float p_103813_) {
            this.head.yRot = p_103812_ * ((float) Math.PI / 180F);
            this.head.xRot = p_103813_ * ((float) Math.PI / 180F);
        }
    }

    private static class OnlyHeadModel extends SkullModelBase {
        protected final ModelPart head;

        public OnlyHeadModel(ModelPart root) {
            this.head = root.getChild("head");
        }

        public void setupAnim(float p_103811_, float p_103812_, float p_103813_) {
            this.head.yRot = p_103812_ * ((float) Math.PI / 180F);
            this.head.xRot = p_103813_ * ((float) Math.PI / 180F);
        }

        @Override
        public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, int color) {
            this.head.render(poseStack, buffer, packedLight, packedOverlay, color);
        }
    }

    private static class EndermanHeadModel extends SkullModelBase {
        protected final ModelPart head;

        public EndermanHeadModel(ModelPart root) {
            this.head = root.getChild("head");
        }

        public void setupAnim(float p_103811_, float p_103812_, float p_103813_) {
            this.head.yRot = p_103812_ * ((float) Math.PI / 180F);
            this.head.xRot = p_103813_ * ((float) Math.PI / 180F);
        }

        @Override
        public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, int color) {
            poseStack.translate(0,0.9,0);
            this.head.render(poseStack, buffer, packedLight, packedOverlay, color);
        }
    }

    private static class SpiderHeadModel extends SkullModelBase {
        protected final ModelPart head;

        public SpiderHeadModel(ModelPart root) {
            this.head = root.getChild("head");
        }

        public void setupAnim(float p_103811_, float p_103812_, float p_103813_) {
            this.head.yRot = p_103812_ * ((float) Math.PI / 180F);
            this.head.xRot = p_103813_ * ((float) Math.PI / 180F);
        }

        @Override
        public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, int color) {
            poseStack.translate(0,-1.1,0.3);
            this.head.render(poseStack, buffer, packedLight, packedOverlay, color);
        }
    }
}