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

package com.github.klikli_dev.occultism.client.render.tile;

import com.github.klikli_dev.occultism.client.model.tile.DimensionalMatrixModel;
import com.github.klikli_dev.occultism.common.tile.StorageControllerTileEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Atlases;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3f;
import software.bernie.geckolib3.geo.render.built.GeoModel;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.renderers.geo.GeoBlockRenderer;

import javax.annotation.Nullable;
import java.awt.*;

public class StorageControllerGeoRenderer extends GeoBlockRenderer<StorageControllerTileEntity> {

    private final AnimatedGeoModel<StorageControllerTileEntity> modelProvider;

    public StorageControllerGeoRenderer(TileEntityRendererDispatcher dispatcher) {
        this(dispatcher, new DimensionalMatrixModel());
    }

    public StorageControllerGeoRenderer(TileEntityRendererDispatcher dispatcher, AnimatedGeoModel<StorageControllerTileEntity> modelProvider) {
        super(dispatcher, modelProvider);
        this.modelProvider = modelProvider;
    }

    @Override
    public void render(StorageControllerTileEntity tile, float partialTicks, MatrixStack stack, IRenderTypeBuffer bufferIn, int packedLightIn) {
        //copied from super + modified

        GeoModel model = this.modelProvider.getModel(this.modelProvider.getModelLocation(tile));
        this.modelProvider.setLivingAnimations(tile, this.getUniqueID(tile));
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

        Minecraft.getInstance().textureManager.bind(this.getTextureLocation(tile));
        Color renderColor = this.getRenderColor(tile, partialTicks, stack, bufferIn, null, packedLightIn);
        RenderType renderType = this.getRenderType(tile, partialTicks, stack, bufferIn, null, packedLightIn,
                this.getTextureLocation(tile));
        this.render(model, tile, partialTicks, renderType, stack, bufferIn, null, packedLightIn, OverlayTexture.NO_OVERLAY,
                (float) renderColor.getRed() / 255f, (float) renderColor.getGreen() / 255f,
                (float) renderColor.getBlue() / 255f, (float) renderColor.getAlpha() / 255);
        stack.popPose();
    }

    @Override
    public RenderType getRenderType(StorageControllerTileEntity animatable, float partialTicks, MatrixStack stack, @Nullable IRenderTypeBuffer renderTypeBuffer, @Nullable IVertexBuilder vertexBuilder, int packedLightIn, ResourceLocation textureLocation) {
        return RenderType.entityTranslucentCull(this.modelProvider.getTextureLocation(animatable));
                //Atlases.translucentCullBlockSheet();
    }

    @Override
    public Color getRenderColor(StorageControllerTileEntity animatable, float partialTicks, MatrixStack stack, @Nullable IRenderTypeBuffer renderTypeBuffer, @Nullable IVertexBuilder vertexBuilder, int packedLightIn) {
        long systemTime = System.currentTimeMillis();
        double systemTimeRadSin8 = Math.sin(Math.toRadians((float) systemTime / 8));
        //get colors from hue over time
        long colorScale = 100L - Math.abs(systemTime / 16 / 2 % 160L - 80L);
        //make saturation smoothly go from 0.0-1.0
        float saturation = (float) systemTimeRadSin8 * 0.5f + 0.5f;

        return Color.getHSBColor(0.01F * (float) colorScale, saturation, 0.01F * (float) colorScale);
    }
}
