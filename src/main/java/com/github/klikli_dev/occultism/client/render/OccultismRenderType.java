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

package com.github.klikli_dev.occultism.client.render;

import net.minecraft.client.renderer.RenderState;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.vertex.VertexFormat;

import java.util.OptionalDouble;
//region Initialization
//endregion Initialization
//endregion Initialization

import net.minecraft.client.renderer.RenderState.LineState;
import net.minecraft.client.renderer.RenderType.State;

public class OccultismRenderType extends RenderType {
    //region Fields
    private static final LineState BLOCK_SELECTION_LINE_STATE = new LineState(OptionalDouble.of(4.0D));
    public static final RenderType BLOCK_SELECTION = create("overlay_lines", DefaultVertexFormats.POSITION_COLOR, 1,
            256, State.builder().setLineState(BLOCK_SELECTION_LINE_STATE).setLayeringState(RenderState.POLYGON_OFFSET_LAYERING)
                         //TODO: Figure out if this is the right replacement for PROJECTION_LAYERING
                         .setTransparencyState(TRANSLUCENT_TRANSPARENCY).setTextureState(NO_TEXTURE).setDepthTestState(NO_DEPTH_TEST)
                         .setCullState(NO_CULL).setLightmapState(NO_LIGHTMAP).setWriteMaskState(COLOR_WRITE).createCompositeState(false));
    //endregion Fields

    //region Initialization
    public OccultismRenderType(String name, VertexFormat vertexFormat, int drawMode, int bufferSize,
                               boolean useDelegate, boolean needsSorting, Runnable setupTaskIn,
                               Runnable clearTaskIn) {
        super(name, vertexFormat, drawMode, bufferSize, useDelegate, needsSorting, setupTaskIn, clearTaskIn);
    }
}