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

package com.klikli_dev.occultism.client.render;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.renderer.RenderType;

import java.util.OptionalDouble;

public class OccultismRenderType extends RenderType {
    public static final RenderType OVERLAY_LINES_ALTERNATIVE = create("overlay_lines_alternative", DefaultVertexFormat.POSITION_COLOR_NORMAL, VertexFormat.Mode.LINES,
            256, false, false, RenderType.CompositeState.builder()
                    .setShaderState(RENDERTYPE_LINES_SHADER)
                    .setLineState(new LineStateShard(OptionalDouble.empty()))
                    .setLayeringState(VIEW_OFFSET_Z_LAYERING)
                    .setTransparencyState(TRANSLUCENT_TRANSPARENCY)
                    .setDepthTestState(NO_DEPTH_TEST)
                    .setOutputState(OutputStateShard.ITEM_ENTITY_TARGET)
                    .setCullState(NO_CULL)
                    .setWriteMaskState(COLOR_DEPTH_WRITE)
                    .createCompositeState(false));
    private static final LineStateShard THICK_LINES = new LineStateShard(OptionalDouble.of(4.0D));
    private static final RenderType OVERLAY_LINES = create("overlay_lines",
            DefaultVertexFormat.POSITION_COLOR, VertexFormat.Mode.LINES, 256, false, false,
            CompositeState.builder().setLineState(THICK_LINES)
                    .setLayeringState(VIEW_OFFSET_Z_LAYERING)
                    .setShaderState(RENDERTYPE_LINES_SHADER)
                    .setTransparencyState(TRANSLUCENT_TRANSPARENCY)
                    .setTextureState(NO_TEXTURE)
                    .setDepthTestState(NO_DEPTH_TEST)
                    .setCullState(NO_CULL)
                    .setLightmapState(NO_LIGHTMAP)
                    .setWriteMaskState(COLOR_WRITE)
                    .setOutputState(PARTICLES_TARGET)
                    .createCompositeState(false));


    public OccultismRenderType(String name, VertexFormat vertexFormat, VertexFormat.Mode drawMode, int bufferSize,
                               boolean useDelegate, boolean needsSorting, Runnable setupTaskIn,
                               Runnable clearTaskIn) {
        super(name, vertexFormat, drawMode, bufferSize, useDelegate, needsSorting, setupTaskIn, clearTaskIn);
    }

    public static RenderType overlayLines() {
        return OVERLAY_LINES;
    }

    public static RenderType overlayLinesAlternative() {
        return OVERLAY_LINES_ALTERNATIVE;
    }
}