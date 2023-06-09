/*
 * MIT License
 *
 * Copyright 2020 klikli-dev
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

package com.klikli_dev.occultism.client.render.blockentity;

import com.klikli_dev.occultism.client.model.tile.DimensionalMatrixModel;
import com.klikli_dev.occultism.common.blockentity.StorageControllerBlockEntity;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.object.Color;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

public class StorageControllerGeoRenderer extends GeoBlockRenderer<StorageControllerBlockEntity> {

    private final GeoModel<StorageControllerBlockEntity> modelProvider;

    public StorageControllerGeoRenderer(BlockEntityRendererProvider.Context rendererDispatcherIn) {
        this(rendererDispatcherIn, new DimensionalMatrixModel());
    }

    public StorageControllerGeoRenderer(BlockEntityRendererProvider.Context rendererDispatcherIn, GeoModel<StorageControllerBlockEntity> modelProvider) {
        super(modelProvider);
        this.modelProvider = modelProvider;
    }

    @Override
    public void actuallyRender(PoseStack poseStack, StorageControllerBlockEntity animatable, BakedGeoModel model, RenderType renderType, MultiBufferSource bufferSource, VertexConsumer bufferIn, boolean isReRender, float partialTicks, int packedLightIn, int packedOverlay, float red, float green, float blue, float alpha) {

        poseStack.pushPose();

        //poseStack.translate(0, 0.01f, 0); //we don't need this
        //move above block
        poseStack.translate(0.5, 1.25, 0.5);

        //this.rotateBlock(this.getFacing(tile), poseStack); //our block does not use directions

        //rotate item slowly around y axis
        long systemTime = System.currentTimeMillis();

        //do not use system time rad, as rotationDegrees converts for us and we want to clamp it to 360° first
        float angle = (systemTime / 16) % 360;
        poseStack.mulPose(Axis.YP.rotationDegrees(angle));


        if (!isReRender) {
            var animationState = new AnimationState<>(animatable, 0, 0, partialTicks, false);
            long instanceId = this.getInstanceId(animatable);

            animationState.setData(DataTickets.TICK, animatable.getTick(animatable));
            animationState.setData(DataTickets.BLOCK_ENTITY, animatable);
            this.model.addAdditionalStateData(animatable, instanceId, animationState::setData);
//            poseStack.translate(0, 0.01f, 0);
//            poseStack.translate(0.5, 0, 0.5);
//           rotateBlock(getFacing(animatable), poseStack);
            this.model.handleAnimations(animatable, instanceId, animationState);
        }

        RenderSystem.setShaderTexture(0, this.getTextureLocation(animatable));

        for (GeoBone group : model.topLevelBones()) {
            this.renderRecursively(poseStack, animatable, group, renderType, bufferSource, bufferIn, isReRender, partialTicks, packedLightIn,
                    packedOverlay, red, green, blue, alpha);
        }

        poseStack.popPose();
    }

    @Override
    public RenderType getRenderType(StorageControllerBlockEntity animatable, ResourceLocation texture, @org.jetbrains.annotations.Nullable MultiBufferSource bufferSource, float partialTick) {
        return RenderType.entityTranslucentCull(this.modelProvider.getTextureResource(animatable));
    }

    @Override
    public Color getRenderColor(StorageControllerBlockEntity animatable, float partialTick, int packedLight) {
        long systemTime = System.currentTimeMillis();
        double systemTimeRadSin8 = Math.sin(Math.toRadians((float) systemTime / 8));
        //get colors from hue over time
        long colorScale = 100L - Math.abs(systemTime / 16 / 2 % 160L - 80L);
        //make saturation smoothly go from 0.0-1.0
        float saturation = (float) systemTimeRadSin8 * 0.5f + 0.5f;
        return Color.ofHSB(0.01F * (float) colorScale, saturation, 0.01F * (float) colorScale);
    }
}
