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

import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.klikli_dev.occultism.Occultism;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.renderer.rendertype.RenderSetup;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.resources.Identifier;

import java.util.Optional;

public class OccultismRenderType {
    private static final Identifier BLANK_TEXTURE = Identifier.fromNamespaceAndPath(Occultism.MODID, "textures/gui/empty_transparent.png");

    public static final RenderPipeline OVERLAY_LINES_NO_DEPTH_PIPELINE = RenderPipelines.ENTITY_SOLID.toBuilder()
            .withLocation(Identifier.fromNamespaceAndPath(Occultism.MODID, "overlay_frame_no_depth"))
            .withCull(false)
            .withDepthStencilState(Optional.empty())
            .build();

    public static final RenderPipeline OVERLAY_LINES_ALTERNATIVE_NO_DEPTH_PIPELINE = RenderPipelines.ENTITY_SOLID.toBuilder()
            .withLocation(Identifier.fromNamespaceAndPath(Occultism.MODID, "overlay_frame_alternative_no_depth"))
            .withCull(false)
            .withDepthStencilState(Optional.empty())
            .build();

    private static final RenderType OVERLAY_FRAME_NO_DEPTH = RenderType.create(
            "occultism_overlay_frame_no_depth",
            RenderSetup.builder(OVERLAY_LINES_NO_DEPTH_PIPELINE)
                    .withTexture("Sampler0", BLANK_TEXTURE)
                    .useLightmap()
                    .useOverlay()
                    .createRenderSetup()
    );

    private static final RenderType OVERLAY_FRAME_ALTERNATIVE_NO_DEPTH = RenderType.create(
            "occultism_overlay_frame_alternative_no_depth",
            RenderSetup.builder(OVERLAY_LINES_ALTERNATIVE_NO_DEPTH_PIPELINE)
                    .withTexture("Sampler0", BLANK_TEXTURE)
                    .useLightmap()
                    .useOverlay()
                    .createRenderSetup()
    );

    public static RenderType overlayLines() {
        return OVERLAY_FRAME_NO_DEPTH;
    }

    public static RenderType overlayLinesAlternative() {
        return OVERLAY_FRAME_ALTERNATIVE_NO_DEPTH;
    }
}
