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
import com.mojang.math.Axis;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.resources.Identifier;
import net.minecraft.util.ARGB;
import com.geckolib.model.GeoModel;
import com.geckolib.renderer.GeoBlockRenderer;
import com.geckolib.renderer.base.RenderPassInfo;
import net.minecraft.client.renderer.rendertype.RenderTypes;

public class StorageControllerGeoRenderer extends GeoBlockRenderer<StorageControllerBlockEntity, OccultismGeoBlockEntityRenderState> {

    public StorageControllerGeoRenderer(BlockEntityRendererProvider.Context rendererDispatcherIn) {
        this(rendererDispatcherIn, new DimensionalMatrixModel());
    }

    public StorageControllerGeoRenderer(BlockEntityRendererProvider.Context rendererDispatcherIn, GeoModel<StorageControllerBlockEntity> modelProvider) {
        super(rendererDispatcherIn, modelProvider);
    }

    @Override
    public OccultismGeoBlockEntityRenderState createRenderState() {
        return new OccultismGeoBlockEntityRenderState();
    }

    @Override
    public void adjustRenderPose(RenderPassInfo<OccultismGeoBlockEntityRenderState> renderPassInfo) {
        super.adjustRenderPose(renderPassInfo);

        // move above block
        renderPassInfo.poseStack().translate(0.0, 1.25, 0.0);

        // rotate item slowly around y axis
        long systemTime = System.currentTimeMillis();
        float angle = (systemTime / 16) % 360;
        renderPassInfo.poseStack().mulPose(Axis.YP.rotationDegrees(angle));
    }

    @Override
    public RenderType getRenderType(OccultismGeoBlockEntityRenderState renderState, Identifier texture) {
        return RenderTypes.entityTranslucent(texture);
    }


    @Override
    public int getRenderColor(StorageControllerBlockEntity animatable, Void relatedObject, float partialTick) {
        long systemTime = System.currentTimeMillis();
        double systemTimeRadSin8 = Math.sin(Math.toRadians((float) systemTime / 8));
        //get colors from hue over time
        long colorScale = 100L - Math.abs(systemTime / 16 / 2 % 160L - 80L);
        //make saturation smoothly go from 0.0-1.0
        float saturation = (float) systemTimeRadSin8 * 0.5f + 0.5f;
        // Convert HSB to RGB int
        int rgb = java.awt.Color.HSBtoRGB(0.01F * (float) colorScale, saturation, 0.01F * (float) colorScale);
        return ARGB.color(255, ARGB.red(rgb), ARGB.green(rgb), ARGB.blue(rgb));
    }
}
