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

import com.google.common.collect.ImmutableMap.Builder;
import com.klikli_dev.occultism.Occultism;
import com.klikli_dev.occultism.client.model.entity.CthulhuFamiliarModel;
import com.klikli_dev.occultism.client.model.entity.HeadlessFamiliarModel;
import com.klikli_dev.occultism.client.render.entity.state.HeadlessFamiliarRenderState;
import com.klikli_dev.occultism.common.entity.familiar.HeadlessFamiliarEntity;
import com.klikli_dev.occultism.common.entity.familiar.HeadlessFamiliarEntity.Rebuilt;
import com.klikli_dev.occultism.registry.OccultismEntities;
import com.klikli_dev.occultism.registry.OccultismModelLayers;
import com.klikli_dev.occultism.util.FamiliarUtil;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.object.skull.SkullModel;
import net.minecraft.client.model.object.skull.SkullModelBase;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.joml.Quaternionf;

import java.util.Map;

public class HeadlessFamiliarRenderer extends MobRenderer<HeadlessFamiliarEntity, HeadlessFamiliarRenderState, HeadlessFamiliarModel> {

    private static final Identifier TEXTURE = Identifier.fromNamespaceAndPath(Occultism.MODID,
            "textures/entity/headless_familiar.png");

    private final ItemModelResolver itemModelResolver;

    public HeadlessFamiliarRenderer(Context context) {
        super(context, new HeadlessFamiliarModel(context.bakeLayer(OccultismModelLayers.FAMILIAR_HEADLESS)), 0.3f);
        this.itemModelResolver = context.getItemModelResolver();
        this.addLayer(new HeadLayer(this));
        this.addLayer(new WeaponLayer(this, this.itemModelResolver));
        this.addLayer(new RebuiltLayer(this, this.itemModelResolver));
        this.addLayer(new PumpkinLayer(this));
    }

    @Override
    public void extractRenderState(HeadlessFamiliarEntity entity, HeadlessFamiliarRenderState reusedState, float partialTick) {
        super.extractRenderState(entity, reusedState, partialTick);
        reusedState.isSitting = entity.isSitting();
        reusedState.isHeadlessDead = entity.isHeadlessDead();
        reusedState.isPartying = entity.isPartying();
        reusedState.hasBlacksmithUpgrade = entity.hasBlacksmithUpgrade();
        reusedState.isHairy = entity.isHairy();
        reusedState.rebuiltRightLeg = entity.isRebuilt(Rebuilt.RightLeg);
        reusedState.rebuiltLeftLeg = entity.isRebuilt(Rebuilt.LeftLeg);
        reusedState.rebuiltBody = entity.isRebuilt(Rebuilt.Body);
        reusedState.rebuiltRightArm = entity.isRebuilt(Rebuilt.RightArm);
        reusedState.rebuiltLeftArm = entity.isRebuilt(Rebuilt.LeftArm);
        reusedState.rebuiltHead = entity.isRebuilt(Rebuilt.Head);
        reusedState.hasHead = entity.hasHead();
        reusedState.hasGlasses = entity.hasGlasses();
        reusedState.headType = entity.getHeadType();
        reusedState.weaponItem = entity.getWeaponItem().copy();
        reusedState.yHeadRot = entity.yHeadRot;
        reusedState.limbSwing = entity.walkAnimation.position();
        reusedState.limbSwingAmount = entity.walkAnimation.speed(partialTick);
        reusedState.attackTime = entity.getAttackAnim(partialTick);
    }

    @Override
    public HeadlessFamiliarRenderState createRenderState() {
        return new HeadlessFamiliarRenderState();
    }

    @Override
    public void submit(HeadlessFamiliarRenderState state, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, CameraRenderState camera) {
        poseStack.pushPose();
        if (state.isSitting)
            poseStack.translate(0, -0.12, 0);
        super.submit(state, poseStack, submitNodeCollector, camera);
        poseStack.popPose();
    }

    @Override
    public Identifier getTextureLocation(HeadlessFamiliarRenderState state) {
        return TEXTURE;
    }

    // -------------------------------------------------------------------------
    // RebuiltLayer
    // -------------------------------------------------------------------------

    private static class RebuiltLayer extends RenderLayer<HeadlessFamiliarRenderState, HeadlessFamiliarModel> {
        private final ItemModelResolver itemModelResolver;

        public RebuiltLayer(RenderLayerParent<HeadlessFamiliarRenderState, HeadlessFamiliarModel> parent, ItemModelResolver itemModelResolver) {
            super(parent);
            this.itemModelResolver = itemModelResolver;
        }

        @Override
        public void submit(PoseStack matrix, SubmitNodeCollector collector, int lightCoords, HeadlessFamiliarRenderState state, float yRot, float xRot) {
            if (!state.isHeadlessDead)
                return;

            boolean partying = state.isPartying;
            float ageInTicks = state.ageInTicks;
            float netHeadYaw = state.yHeadRot;

            matrix.pushPose();
            HeadlessFamiliarModel model = this.getParentModel();
            model.ratBody1.translateAndRotate(matrix);

            if (state.rebuiltRightLeg) {
                matrix.pushPose();
                matrix.mulPose(new Quaternionf().rotateXYZ(0, 130 * ((float) Math.PI / 180F), 0));
                matrix.translate(0.3, -0.3, 0);
                this.renderItem(new ItemStack(Items.WHEAT), matrix, collector, lightCoords);
                matrix.popPose();
            }
            if (state.rebuiltLeftLeg) {
                matrix.pushPose();
                matrix.mulPose(new Quaternionf().rotateXYZ(0, 50 * ((float) Math.PI / 180F), 0));
                matrix.translate(0.3, -0.3, 0);
                this.renderItem(new ItemStack(Items.WHEAT), matrix, collector, lightCoords);
                matrix.popPose();
            }
            if (state.rebuiltBody) {
                matrix.pushPose();
                float size = 1.2f;
                matrix.scale(size, size, size);
                matrix.mulPose(new Quaternionf().rotateXYZ(0, 0, 0));
                matrix.translate(0, -0.45, -0.05);
                this.renderItem(new ItemStack(Items.HAY_BLOCK), matrix, collector, lightCoords);
                matrix.translate(0, -0.25, 0);
                this.renderItem(new ItemStack(Items.HAY_BLOCK), matrix, collector, lightCoords);
                matrix.popPose();
            }
            if (state.rebuiltRightArm) {
                matrix.pushPose();
                matrix.mulPose(new Quaternionf().rotateXYZ(0, (180 + (partying ? Mth.sin(ageInTicks / 3) * 20 : 0)) * ((float) Math.PI / 180F), 0));
                matrix.translate(0.25, -0.6, 0.05);
                this.renderItem(new ItemStack(Items.STICK), matrix, collector, lightCoords);
                matrix.popPose();
            }
            if (state.rebuiltLeftArm) {
                matrix.pushPose();
                matrix.mulPose(new Quaternionf().rotateXYZ(0, (partying ? Mth.sin(ageInTicks / 3) * 20 : 0) * ((float) Math.PI / 180F), 0));
                matrix.translate(0.25, -0.6, -0.05);
                this.renderItem(new ItemStack(Items.STICK), matrix, collector, lightCoords);
                matrix.popPose();
            }
            if (state.rebuiltHead) {
                matrix.pushPose();
                matrix.scale(-1, -1, 1);
                matrix.translate(0, 0.7, -0.06);
                matrix.mulPose(new Quaternionf().rotateXYZ(0, (partying ? ageInTicks * 8 : -netHeadYaw) * ((float) Math.PI / 180F), 0));
                this.renderItem(new ItemStack(Items.CARVED_PUMPKIN), matrix, collector, lightCoords);
                matrix.popPose();
            }
            matrix.popPose();
        }

        private void renderItem(ItemStack stack, PoseStack poseStack, SubmitNodeCollector collector, int lightCoords) {
            ItemStackRenderState stackState = new ItemStackRenderState();
            this.itemModelResolver.updateForTopItem(stackState, stack, ItemDisplayContext.GROUND, null, null, 0);
            stackState.submit(poseStack, collector, lightCoords, OverlayTexture.NO_OVERLAY, 0xFFFFFFFF);
        }
    }

    // -------------------------------------------------------------------------
    // WeaponLayer
    // -------------------------------------------------------------------------

    private static class WeaponLayer extends RenderLayer<HeadlessFamiliarRenderState, HeadlessFamiliarModel> {
        private final ItemModelResolver itemModelResolver;

        public WeaponLayer(RenderLayerParent<HeadlessFamiliarRenderState, HeadlessFamiliarModel> parent, ItemModelResolver itemModelResolver) {
            super(parent);
            this.itemModelResolver = itemModelResolver;
        }

        @Override
        public void submit(PoseStack poseStack, SubmitNodeCollector collector, int lightCoords, HeadlessFamiliarRenderState state, float yRot, float xRot) {
            if (state.isHeadlessDead)
                return;

            ItemStack weaponItem = state.weaponItem;
            if (weaponItem == null || weaponItem.isEmpty())
                return;

            poseStack.pushPose();
            HeadlessFamiliarModel model = this.getParentModel();

            model.ratBody1.translateAndRotate(poseStack);
            model.body.translateAndRotate(poseStack);
            model.rightArm.translateAndRotate(poseStack);

            poseStack.translate(-0.05f, 0.16, -0.08);
            poseStack.mulPose(new Quaternionf().rotateXYZ(0, 90 * ((float) Math.PI / 180F), -50 * ((float) Math.PI / 180F)));

            ItemStackRenderState stackState = new ItemStackRenderState();
            this.itemModelResolver.updateForTopItem(stackState, weaponItem, ItemDisplayContext.GROUND, null, null, 0);
            stackState.submit(poseStack, collector, lightCoords, OverlayTexture.NO_OVERLAY, state.outlineColor);

            poseStack.popPose();
        }
    }

    // -------------------------------------------------------------------------
    // PumpkinLayer
    // -------------------------------------------------------------------------

    private static class PumpkinLayer extends RenderLayer<HeadlessFamiliarRenderState, HeadlessFamiliarModel> {
        private static final Identifier PUMPKIN = Identifier.fromNamespaceAndPath(Occultism.MODID,
                "textures/entity/headless_familiar_pumpkin.png");
        private static final Identifier CHRISTMAS = Identifier.fromNamespaceAndPath(Occultism.MODID,
                "textures/entity/headless_familiar_christmas.png");

        public PumpkinLayer(RenderLayerParent<HeadlessFamiliarRenderState, HeadlessFamiliarModel> renderer) {
            super(renderer);
        }

        @Override
        public void submit(PoseStack poseStack, SubmitNodeCollector collector, int lightCoords, HeadlessFamiliarRenderState state, float yRot, float xRot) {
            if (state.isInvisible)
                return;

            boolean isChristmas = FamiliarUtil.isChristmas();
            boolean hasPumpkin = !state.hasHead;

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

            Identifier texture = isChristmas ? CHRISTMAS : PUMPKIN;
            RenderLayer.renderColoredCutoutModel(model, texture, poseStack, collector, lightCoords, state, -1, 0);

            // Reset to defaults
            model.pumpkin1.visible = true;
            model.snowmanHat1.visible = false;
            model.snowmanHat2.visible = false;
            model.snowmanLeftEye.visible = false;
            model.snowmanRightEye.visible = false;
            model.snowmanMouth1.visible = false;
            model.snowmanMouth2.visible = false;
            model.snowmanMouth3.visible = false;
            model.snowmanNose.visible = false;
            model.pumpkin2.visible = true;
            model.pumpkin3.visible = true;
            model.pumpkin4.visible = true;
        }
    }

    // -------------------------------------------------------------------------
    // HeadLayer
    // -------------------------------------------------------------------------

    public static class HeadLayer extends RenderLayer<HeadlessFamiliarRenderState, HeadlessFamiliarModel> {

        private static Map<EntityType<?>, Identifier> textures;
        private static Map<EntityType<?>, SkullModelBase> skulls;

        public HeadLayer(RenderLayerParent<HeadlessFamiliarRenderState, HeadlessFamiliarModel> parent) {
            super(parent);
        }

        private static Identifier getTexture(EntityType<?> type) {
            if (textures == null) {
                Builder<EntityType<?>, Identifier> builder = new Builder<>();
                builder.put(EntityType.PLAYER, DefaultPlayerSkin.getDefaultTexture());
                builder.put(EntityType.SKELETON, Identifier.parse("textures/entity/skeleton/skeleton.png"));
                builder.put(EntityType.WITHER_SKELETON, Identifier.parse("textures/entity/skeleton/wither_skeleton.png"));
                builder.put(EntityType.STRAY, Identifier.parse("textures/entity/skeleton/stray.png"));
                builder.put(EntityType.BOGGED, Identifier.parse("textures/entity/skeleton/bogged_overlay.png"));
                builder.put(EntityType.ZOMBIE, Identifier.parse("textures/entity/zombie/zombie.png"));
                builder.put(EntityType.HUSK, Identifier.parse("textures/entity/zombie/husk.png"));
                builder.put(EntityType.DROWNED, Identifier.parse("textures/entity/zombie/drowned_outer_layer.png"));
                builder.put(EntityType.CREEPER, Identifier.parse("textures/entity/creeper/creeper.png"));
                builder.put(EntityType.SPIDER, Identifier.parse("textures/entity/spider/spider.png"));
                builder.put(EntityType.CAVE_SPIDER, Identifier.parse("textures/entity/spider/cave_spider.png"));
                builder.put(EntityType.PIGLIN, Identifier.parse("textures/entity/piglin/piglin.png"));
                builder.put(EntityType.PIGLIN_BRUTE, Identifier.parse("textures/entity/piglin/piglin_brute.png"));
                builder.put(EntityType.ZOMBIFIED_PIGLIN, Identifier.parse("textures/entity/piglin/zombified_piglin.png"));
                builder.put(EntityType.BLAZE, Identifier.parse("textures/entity/blaze.png"));
                builder.put(EntityType.BREEZE, Identifier.fromNamespaceAndPath(Occultism.MODID, "textures/entity/breeze_blaze_size.png"));
                builder.put(EntityType.ENDERMAN, Identifier.parse("textures/entity/enderman/enderman.png"));
                builder.put(EntityType.ENDER_DRAGON, Identifier.parse("textures/entity/enderdragon/dragon.png"));
                builder.put(OccultismEntities.CTHULHU_FAMILIAR.get(),
                        Identifier.fromNamespaceAndPath(Occultism.MODID, "textures/entity/cthulhu_familiar.png"));
                builder.put(EntityType.VILLAGER, Identifier.parse("textures/entity/villager/villager.png"));
                builder.put(EntityType.WANDERING_TRADER, Identifier.parse("textures/entity/wandering_trader.png"));
                builder.put(EntityType.ZOMBIE_VILLAGER, Identifier.parse("textures/entity/zombie_villager/zombie_villager.png"));
                builder.put(EntityType.WITCH, Identifier.parse("textures/entity/witch.png"));
                builder.put(EntityType.PILLAGER, Identifier.parse("textures/entity/illager/pillager.png"));
                builder.put(EntityType.VINDICATOR, Identifier.parse("textures/entity/illager/vindicator.png"));
                builder.put(EntityType.EVOKER, Identifier.parse("textures/entity/illager/evoker.png"));
                textures = builder.build();
            }
            return textures.get(type);
        }

        private static SkullModelBase getHeadModel(EntityType<?> type) {
            if (skulls == null) {
                EntityModelSet entityModels = Minecraft.getInstance().getEntityModels();
                Builder<EntityType<?>, SkullModelBase> builder = new Builder<>();
                builder.put(EntityType.SKELETON, new VanillaSkullModel(entityModels.bakeLayer(ModelLayers.SKELETON_SKULL)));
                builder.put(EntityType.WITHER_SKELETON, new VanillaSkullModel(entityModels.bakeLayer(ModelLayers.WITHER_SKELETON_SKULL)));
                builder.put(EntityType.STRAY, new VanillaSkullModel(entityModels.bakeLayer(ModelLayers.SKELETON_SKULL)));
                builder.put(EntityType.BOGGED, new OnlyHeadModel(entityModels.bakeLayer(ModelLayers.BOGGED)));
                builder.put(EntityType.PLAYER, new VanillaSkullModel(entityModels.bakeLayer(ModelLayers.PLAYER_HEAD)));
                builder.put(EntityType.ZOMBIE, new VanillaSkullModel(entityModels.bakeLayer(ModelLayers.ZOMBIE_HEAD)));
                builder.put(EntityType.HUSK, new VanillaSkullModel(entityModels.bakeLayer(ModelLayers.ZOMBIE_HEAD)));
                builder.put(EntityType.DROWNED, new OnlyHeadModel(entityModels.bakeLayer(ModelLayers.DROWNED)));
                builder.put(EntityType.CREEPER, new VanillaSkullModel(entityModels.bakeLayer(ModelLayers.CREEPER_HEAD)));
                builder.put(EntityType.SPIDER, new SpiderHeadModel(entityModels.bakeLayer(ModelLayers.SPIDER)));
                builder.put(EntityType.CAVE_SPIDER, new SpiderHeadModel(entityModels.bakeLayer(ModelLayers.CAVE_SPIDER)));
                builder.put(EntityType.PIGLIN, new VanillaSkullModel(entityModels.bakeLayer(ModelLayers.PIGLIN_HEAD)));
                builder.put(EntityType.PIGLIN_BRUTE, new VanillaSkullModel(entityModels.bakeLayer(ModelLayers.PIGLIN_HEAD)));
                builder.put(EntityType.ZOMBIFIED_PIGLIN, new VanillaSkullModel(entityModels.bakeLayer(ModelLayers.PIGLIN_HEAD)));
                builder.put(EntityType.BLAZE, new OnlyHeadModel(entityModels.bakeLayer(ModelLayers.BLAZE)));
                builder.put(EntityType.BREEZE, new OnlyHeadModel(entityModels.bakeLayer(ModelLayers.BLAZE))); // breeze model crash
                builder.put(EntityType.ENDERMAN, new EndermanHeadModel(entityModels.bakeLayer(ModelLayers.ENDERMAN)));
                builder.put(EntityType.ENDER_DRAGON, new VanillaSkullModel(entityModels.bakeLayer(ModelLayers.DRAGON_SKULL)));
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
        public void submit(PoseStack poseStack, SubmitNodeCollector collector, int lightCoords, HeadlessFamiliarRenderState state, float yRot, float xRot) {
            if (state.isHeadlessDead)
                return;

            EntityType<?> headType = state.headType;
            if (headType == null)
                return;

            SkullModelBase headModel = getHeadModel(headType);
            if (headModel == null)
                return;

            Identifier texture = getTexture(headType);
            if (texture == null)
                return;

            poseStack.pushPose();
            HeadlessFamiliarModel model = this.getParentModel();
            model.ratBody1.translateAndRotate(poseStack);
            model.body.translateAndRotate(poseStack);
            model.leftArm.translateAndRotate(poseStack);

            float size = 0.5f;
            poseStack.scale(size, size, size);
            poseStack.translate(0.15, 0.5, -0.12);
            poseStack.mulPose(new Quaternionf().rotateXYZ(90 * ((float) Math.PI / 180F), 0, 0));

            VertexConsumer buffer = Minecraft.getInstance().renderBuffers().bufferSource()
                    .getBuffer(RenderTypes.entityCutout(texture));

            // Dispatch to per-model render method
            if (headModel instanceof CthulhuHeadModel cthulhu) {
                cthulhu.renderHead(poseStack, buffer, lightCoords, OverlayTexture.NO_OVERLAY);
            } else if (headModel instanceof OnlyHeadModel only) {
                only.renderHead(poseStack, buffer, lightCoords, OverlayTexture.NO_OVERLAY);
            } else if (headModel instanceof EndermanHeadModel enderman) {
                enderman.renderHead(poseStack, buffer, lightCoords, OverlayTexture.NO_OVERLAY);
            } else if (headModel instanceof SpiderHeadModel spider) {
                spider.renderHead(poseStack, buffer, lightCoords, OverlayTexture.NO_OVERLAY);
            } else if (headModel instanceof VanillaSkullModel vanilla) {
                vanilla.renderHead(poseStack, buffer, lightCoords, OverlayTexture.NO_OVERLAY);
            }

            poseStack.popPose();
        }
    }

    // -------------------------------------------------------------------------
    // Custom SkullModelBase subclasses — no renderToBuffer override (final in Model)
    // Use renderHead() to render the head part directly
    // -------------------------------------------------------------------------

    private static class CthulhuHeadModel extends SkullModelBase {
        protected final ModelPart head;
        private final CthulhuFamiliarModel model;

        public CthulhuHeadModel(ModelPart part) {
            super(part);
            this.model = new CthulhuFamiliarModel(part);
            this.head = part.getChild("body").getChild("head");
        }

        public void renderHead(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay) {
            this.model.trunk1.visible = false;
            this.model.trunk2.visible = false;
            this.model.trunk3.visible = false;
            this.model.hat1.visible = false;
            poseStack.pushPose();
            poseStack.scale(1.5f, 1.5f, 1.5f);
            poseStack.translate(0, 0.35, 0.07);
            poseStack.mulPose(new Quaternionf().rotateXYZ(10 * ((float) Math.PI / 180F), 0, 0));
            this.model.head.render(poseStack, buffer, packedLight, packedOverlay);
            poseStack.popPose();
            // Reset visibility
            this.model.trunk1.visible = true;
            this.model.trunk2.visible = true;
            this.model.trunk3.visible = true;
            this.model.hat1.visible = true;
        }

        @Override
        public void setupAnim(State state) {
            super.setupAnim(state);
            this.head.yRot = state.yRot * ((float) Math.PI / 180F);
            this.head.xRot = state.xRot * ((float) Math.PI / 180F);
        }
    }

    private static class OnlyHeadModel extends SkullModelBase {
        protected final ModelPart head;

        public OnlyHeadModel(ModelPart root) {
            super(root);
            this.head = root.getChild("head");
        }

        public void renderHead(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay) {
            this.head.render(poseStack, buffer, packedLight, packedOverlay);
        }

        @Override
        public void setupAnim(State state) {
            super.setupAnim(state);
            this.head.yRot = state.yRot * ((float) Math.PI / 180F);
            this.head.xRot = state.xRot * ((float) Math.PI / 180F);
        }
    }

    private static class EndermanHeadModel extends SkullModelBase {
        protected final ModelPart head;

        public EndermanHeadModel(ModelPart root) {
            super(root);
            this.head = root.getChild("head");
        }

        public void renderHead(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay) {
            poseStack.translate(0, 0.9, 0);
            this.head.render(poseStack, buffer, packedLight, packedOverlay);
        }

        @Override
        public void setupAnim(State state) {
            super.setupAnim(state);
            this.head.yRot = state.yRot * ((float) Math.PI / 180F);
            this.head.xRot = state.xRot * ((float) Math.PI / 180F);
        }
    }

    private static class SpiderHeadModel extends SkullModelBase {
        protected final ModelPart head;

        public SpiderHeadModel(ModelPart root) {
            super(root);
            this.head = root.getChild("head");
        }

        public void renderHead(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay) {
            poseStack.translate(0, -1.1, 0.3);
            this.head.render(poseStack, buffer, packedLight, packedOverlay);
        }

        @Override
        public void setupAnim(State state) {
            super.setupAnim(state);
            this.head.yRot = state.yRot * ((float) Math.PI / 180F);
            this.head.xRot = state.xRot * ((float) Math.PI / 180F);
        }
    }

    /**
     * Wrapper around vanilla SkullModel that exposes a public renderHead method.
     * This is needed because SkullModel.head is protected and cannot be accessed
     * from outside subclasses.
     */
    private static class VanillaSkullModel extends SkullModel {
        public VanillaSkullModel(ModelPart root) {
            super(root);
        }

        public void renderHead(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay) {
            this.head.render(poseStack, buffer, packedLight, packedOverlay);
        }
    }
}
