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

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;

import java.util.OptionalDouble;

public class OccultismRenderType extends RenderType {
    private static final LineStateShard BLOCK_SELECTION_LINE_STATE = new LineStateShard(OptionalDouble.of(4.0D));
    public static final RenderType BLOCK_SELECTION = create("overlay_lines", DefaultVertexFormat.POSITION_COLOR, VertexFormat.Mode.LINES,
            256, false, false, RenderType.CompositeState.builder().setLineState(BLOCK_SELECTION_LINE_STATE).setLayeringState(RenderStateShard.POLYGON_OFFSET_LAYERING)
                    //TODO: Figure out if this is the right replacement for PROJECTION_LAYERING
                    .setTransparencyState(TRANSLUCENT_TRANSPARENCY).setTextureState(NO_TEXTURE).setDepthTestState(NO_DEPTH_TEST)
                    .setCullState(NO_CULL).setLightmapState(NO_LIGHTMAP).setWriteMaskState(COLOR_WRITE).createCompositeState(false));

    public OccultismRenderType(String name, VertexFormat vertexFormat, VertexFormat.Mode drawMode, int bufferSize,
                               boolean useDelegate, boolean needsSorting, Runnable setupTaskIn,
                               Runnable clearTaskIn) {
        super(name, vertexFormat, drawMode, bufferSize, useDelegate, needsSorting, setupTaskIn, clearTaskIn);
    }
}