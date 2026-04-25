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
import com.klikli_dev.occultism.client.model.entity.CthulhuFamiliarModel;
import com.klikli_dev.occultism.client.render.entity.state.CthulhuFamiliarRenderState;
import com.klikli_dev.occultism.common.entity.familiar.CthulhuFamiliarEntity;
import com.klikli_dev.occultism.registry.OccultismModelLayers;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.joml.Quaternionf;

public class CthulhuFamiliarRenderer extends MobRenderer<CthulhuFamiliarEntity, CthulhuFamiliarRenderState, CthulhuFamiliarModel> {

    private static final Identifier TEXTURES = Identifier.fromNamespaceAndPath(Occultism.MODID,
            "textures/entity/cthulhu_familiar.png");

    private final ItemModelResolver itemModelResolver;

    public CthulhuFamiliarRenderer(Context context) {
        super(context, new CthulhuFamiliarModel(context.bakeLayer(OccultismModelLayers.FAMILIAR_CTHULHU)), 0.3f);
        this.itemModelResolver = context.getItemModelResolver();
        this.addLayer(new HeldItemLayer(this, this.itemModelResolver));
    }

    @Override
    public void extractRenderState(CthulhuFamiliarEntity entity, CthulhuFamiliarRenderState reusedState, float partialTick) {
        super.extractRenderState(entity, reusedState, partialTick);
        reusedState.isPartying = entity.isPartying();
        reusedState.isSitting = entity.isSitting();
        reusedState.animationHeight = entity.getAnimationHeight(partialTick);
        reusedState.ageInTicks = entity.tickCount + partialTick;
        reusedState.isGiving = entity.isGiving();
        reusedState.hasTrunk = entity.hasTrunk();
        reusedState.hasHat = entity.hasHat();
        reusedState.hasBlacksmithUpgrade = entity.hasBlacksmithUpgrade();
        reusedState.isAngry = entity.isAngry();
        reusedState.isVehicle = entity.isVehicle();
        reusedState.isInWater = entity.isInWater();
        reusedState.walkAnimationPos = entity.walkAnimation.position();
        reusedState.walkAnimationSpeed = entity.walkAnimation.speed(partialTick);
        reusedState.yRot = entity.getYRot();
        reusedState.xRot = entity.getXRot();
    }

    @Override
    public CthulhuFamiliarRenderState createRenderState() {
        return new CthulhuFamiliarRenderState();
    }

    @Override
    public void submit(CthulhuFamiliarRenderState state, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, CameraRenderState camera) {
        poseStack.pushPose();

        if (state.isPartying) {
            state.bodyRot = -180;
            poseStack.translate(0, 1.55, 0);
            poseStack.mulPose(new Quaternionf().rotateXYZ(state.ageInTicks * 3 * ((float) Math.PI / 180F), 0, 0));
            poseStack.translate(0, 0.5, 0);
        } else {
            double offsetY = state.isSitting ? -0.35 : state.animationHeight * 0.08;
            poseStack.translate(0, offsetY, 0);
        }

        super.submit(state, poseStack, submitNodeCollector, camera);
        poseStack.popPose();
    }

    @Override
    public Identifier getTextureLocation(CthulhuFamiliarRenderState state) {
        return TEXTURES;
    }

    public static class HeldItemLayer extends RenderLayer<CthulhuFamiliarRenderState, CthulhuFamiliarModel> {

        private final ItemModelResolver itemModelResolver;

        public HeldItemLayer(RenderLayerParent<CthulhuFamiliarRenderState, CthulhuFamiliarModel> parent, ItemModelResolver itemModelResolver) {
            super(parent);
            this.itemModelResolver = itemModelResolver;
        }

        @Override
        public void submit(PoseStack matrixStack, SubmitNodeCollector submitNodeCollector, int lightCoords, CthulhuFamiliarRenderState state, float yRot, float xRot) {
            if (!state.isGiving)
                return;

            matrixStack.pushPose();
            matrixStack.scale(1.25f, -1.25f, 1.25f);
            matrixStack.translate(0, -0.75, -0.35);
            matrixStack.mulPose(new Quaternionf().rotateXYZ(-65 * ((float) Math.PI / 180F), 0, 0));

            ItemStack poppyStack = new ItemStack(Items.POPPY);
            ItemStackRenderState stackState = new ItemStackRenderState();
            this.itemModelResolver.updateForTopItem(stackState, poppyStack, ItemDisplayContext.GROUND, null, null, 0);
            stackState.submit(matrixStack, submitNodeCollector, lightCoords, OverlayTexture.NO_OVERLAY, state.outlineColor);

            matrixStack.popPose();
        }
    }
}
