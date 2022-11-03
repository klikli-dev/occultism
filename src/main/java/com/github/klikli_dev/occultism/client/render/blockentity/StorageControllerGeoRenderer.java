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

package com.github.klikli_dev.occultism.client.render.blockentity;

import com.github.klikli_dev.occultism.client.model.tile.DimensionalMatrixModel;
import com.github.klikli_dev.occultism.common.blockentity.StorageControllerBlockEntity;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.core.util.Color;
import software.bernie.geckolib3.geo.render.built.GeoModel;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.renderers.geo.GeoBlockRenderer;

import javax.annotation.Nullable;

public class StorageControllerGeoRenderer extends GeoBlockRenderer<StorageControllerBlockEntity> {

    private final AnimatedGeoModel<StorageControllerBlockEntity> modelProvider;

    public StorageControllerGeoRenderer(BlockEntityRendererProvider.Context rendererDispatcherIn) {
        this(rendererDispatcherIn, new DimensionalMatrixModel());
    }

    public StorageControllerGeoRenderer(BlockEntityRendererProvider.Context rendererDispatcherIn, AnimatedGeoModel<StorageControllerBlockEntity> modelProvider) {
        super(rendererDispatcherIn, modelProvider);
        this.modelProvider = modelProvider;
    }

    public void render(StorageControllerBlockEntity tile, float partialTicks, PoseStack stack, MultiBufferSource bufferIn, int packedLightIn) {
        //copied from super + modified

        GeoModel model = this.modelProvider.getModel(this.modelProvider.getModelLocation(tile));
        this.modelProvider.setCustomAnimations(tile, this.getInstanceId(tile));
        stack.pushPose();
        //stack.translate(0, 0.01f, 0); //we don't need this
        //move above block
        stack.translate(0.5, 1.25, 0.5);

        //this.rotateBlock(this.getFacing(tile), stack); //our block does not use directions

        //rotate item slowly around y axis
        long systemTime = System.currentTimeMillis();

        //do not use system time rad, as rotationDegrees converts for us and we want to clamp it to 360° first
        float angle = (systemTime / 16) % 360;
        stack.mulPose(Vector3f.YP.rotationDegrees(angle));

        RenderSystem.setShaderTexture(0, this.getTextureLocation(tile));
        Color renderColor = this.getRenderColor(tile, partialTicks, stack, bufferIn, null, packedLightIn);
        RenderType renderType = this.getRenderType(tile, partialTicks, stack, bufferIn, null, packedLightIn,
                this.getTextureLocation(tile));
        this.render(model, tile, partialTicks, renderType, stack, bufferIn, null, packedLightIn, OverlayTexture.NO_OVERLAY,
                (float) renderColor.getRed() / 255f, (float) renderColor.getGreen() / 255f,
                (float) renderColor.getBlue() / 255f, (float) renderColor.getAlpha() / 255);
        stack.popPose();
    }

    @Override
    public RenderType getRenderType(StorageControllerBlockEntity animatable, float partialTicks, PoseStack stack, @Nullable MultiBufferSource renderTypeBuffer, @Nullable VertexConsumer vertexBuilder, int packedLightIn, ResourceLocation textureLocation) {
        return RenderType.entityTranslucentCull(this.modelProvider.getTextureLocation(animatable));
    }

    @Override
    public Color getRenderColor(StorageControllerBlockEntity animatable, float partialTicks, PoseStack stack, @Nullable MultiBufferSource renderTypeBuffer, @Nullable VertexConsumer vertexBuilder, int packedLightIn) {
        long systemTime = System.currentTimeMillis();
        double systemTimeRadSin8 = Math.sin(Math.toRadians((float) systemTime / 8));
        //get colors from hue over time
        long colorScale = 100L - Math.abs(systemTime / 16 / 2 % 160L - 80L);
        //make saturation smoothly go from 0.0-1.0
        float saturation = (float) systemTimeRadSin8 * 0.5f + 0.5f;
        return Color.ofHSB(0.01F * (float) colorScale, saturation, 0.01F * (float) colorScale);
    }
}
