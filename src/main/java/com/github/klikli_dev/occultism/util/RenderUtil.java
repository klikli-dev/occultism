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

package com.github.klikli_dev.occultism.util;

import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.util.math.vector.Matrix4f;

public class RenderUtil {

    //region Static Methods

    /**
     * Builds a block outline into the given tesselator buffer.
     *
     * @param buffer the buffer to write into.
     * @param transform the transform matrix.
     * @param x      the block x position.
     * @param y      the block y position.
     * @param z      the block z position.
     * @param r      the color r.
     * @param g      the color g.
     * @param b      the color b.
     * @param a      the color a.
     */
    public static void buildBlockOutline(IVertexBuilder buffer, Matrix4f transform, float x, float y, float z, float r, float g, float b, float a) {
        buffer.vertex(transform, x, y, z).color(r, g, b, a).endVertex();
        buffer.vertex(transform, x + 1, y, z).color(r, g, b, a).endVertex();
        buffer.vertex(transform, x, y, z).color(r, g, b, a).endVertex();
        buffer.vertex(transform, x, y + 1, z).color(r, g, b, a).endVertex();
        buffer.vertex(transform, x, y, z).color(r, g, b, a).endVertex();
        buffer.vertex(transform, x, y, z + 1).color(r, g, b, a).endVertex();
        buffer.vertex(transform, x + 1, y + 1, z + 1).color(r, g, b, a).endVertex();
        buffer.vertex(transform, x, y + 1, z + 1).color(r, g, b, a).endVertex();
        buffer.vertex(transform, x + 1, y + 1, z + 1).color(r, g, b, a).endVertex();
        buffer.vertex(transform, x + 1, y, z + 1).color(r, g, b, a).endVertex();
        buffer.vertex(transform, x + 1, y + 1, z + 1).color(r, g, b, a).endVertex();
        buffer.vertex(transform, x + 1, y + 1, z).color(r, g, b, a).endVertex();

        buffer.vertex(transform, x, y + 1, z).color(r, g, b, a).endVertex();
        buffer.vertex(transform, x, y + 1, z + 1).color(r, g, b, a).endVertex();
        buffer.vertex(transform, x, y + 1, z).color(r, g, b, a).endVertex();
        buffer.vertex(transform, x + 1, y + 1, z).color(r, g, b, a).endVertex();

        buffer.vertex(transform, x + 1, y, z).color(r, g, b, a).endVertex();
        buffer.vertex(transform, x + 1, y, z + 1).color(r, g, b, a).endVertex();
        buffer.vertex(transform, x + 1, y, z).color(r, g, b, a).endVertex();
        buffer.vertex(transform, x + 1, y + 1, z).color(r, g, b, a).endVertex();

        buffer.vertex(transform, x, y, z + 1).color(r, g, b, a).endVertex();
        buffer.vertex(transform, x + 1, y, z + 1).color(r, g, b, a).endVertex();
        buffer.vertex(transform, x, y, z + 1).color(r, g, b, a).endVertex();
        buffer.vertex(transform, x, y + 1, z + 1).color(r, g, b, a).endVertex();
    }
    //endregion Static Methods
}
