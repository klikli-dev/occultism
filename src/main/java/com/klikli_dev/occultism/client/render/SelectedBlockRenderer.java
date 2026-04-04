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

import com.klikli_dev.occultism.Occultism;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent;

import java.awt.*;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class SelectedBlockRenderer {

    protected Set<SelectionInfo> selectedBlocks = new HashSet<>();

    /**
     * Highlights the given block position until the given system time (not game time)
     *
     * @param pos        the position to highlight.
     * @param expireTime the time when it expires. Absolute system time, not interval!
     */
    public void selectBlock(BlockPos pos, long expireTime) {
        this.selectBlock(pos, expireTime, new Color(1.0f, 1.0f, 1.0f, 0.8f));
    }

    /**
     * Highlights the given block position until the given system time (not game time)
     *
     * @param pos        the position to highlight.
     * @param expireTime the time when it expires. Absolute system time, not interval!
     * @param color      the color to render the block in.
     */
    public void selectBlock(BlockPos pos, long expireTime, Color color) {
        SelectionInfo info = new SelectionInfo(pos, expireTime, color);
        this.selectedBlocks.remove(info);
        this.selectedBlocks.add(info);
    }

    /**
     * Unselects the given block position.
     *
     * @param pos the position.
     */
    public void unselectBlock(BlockPos pos) {
        this.selectedBlocks.removeIf(info -> info.selectedBlock.equals(pos));
    }

    @SubscribeEvent
    public void RenderLevelLastEvent(RenderLevelStageEvent.AfterLevel event) {
        this.renderSelectedBlocks(event);
    }

    protected void renderSelectedBlocks(RenderLevelStageEvent.AfterLevel event) {
        var useAltRenderer = Occultism.CLIENT_CONFIG.visuals.useAlternativeDivinationRodRenderer.get();

        if (!this.selectedBlocks.isEmpty()) {
            long time = System.currentTimeMillis();

            PoseStack matrixStack = event.getPoseStack();
            MultiBufferSource.BufferSource buffer = Minecraft.getInstance().renderBuffers().bufferSource();
            var renderType = useAltRenderer ? OccultismRenderType.overlayLinesAlternative() : OccultismRenderType.overlayLines();
            VertexConsumer builder = buffer.getBuffer(renderType);
            matrixStack.pushPose();

            var camera = Minecraft.getInstance().gameRenderer.getMainCamera();
            Vec3 cameraPosition = camera.position();
            matrixStack.translate(-cameraPosition.x, -cameraPosition.y, -cameraPosition.z);

            for (Iterator<SelectionInfo> it = this.selectedBlocks.iterator(); it.hasNext(); ) {
                SelectionInfo info = it.next();

                if (time > info.selectionExpireTime || info.selectedBlock == null) {
                    //remove expired or invalid selections
                    it.remove();
                } else {
                    // Draw the 12 edges of the AABB box manually as line segments
                    float x0 = info.selectedBlock.getX();
                    float y0 = info.selectedBlock.getY();
                    float z0 = info.selectedBlock.getZ();
                    float x1 = x0 + 1;
                    float y1 = y0 + 1;
                    float z1 = z0 + 1;
                    float r = info.color.getRed() / 255.0f;
                    float g = info.color.getGreen() / 255.0f;
                    float b = info.color.getBlue() / 255.0f;
                    float a = info.color.getAlpha() / 255.0f;
                    float lineWidth = useAltRenderer ? 4.0f : 2.0f;
                    var last = matrixStack.last();

                    // Bottom face edges
                    builder.addVertex(last, x0, y0, z0).setColor(r, g, b, a).setNormal(last, 1, 0, 0).setLineWidth(lineWidth);
                    builder.addVertex(last, x1, y0, z0).setColor(r, g, b, a).setNormal(last, 1, 0, 0).setLineWidth(lineWidth);
                    builder.addVertex(last, x1, y0, z0).setColor(r, g, b, a).setNormal(last, 0, 0, 1).setLineWidth(lineWidth);
                    builder.addVertex(last, x1, y0, z1).setColor(r, g, b, a).setNormal(last, 0, 0, 1).setLineWidth(lineWidth);
                    builder.addVertex(last, x1, y0, z1).setColor(r, g, b, a).setNormal(last, -1, 0, 0).setLineWidth(lineWidth);
                    builder.addVertex(last, x0, y0, z1).setColor(r, g, b, a).setNormal(last, -1, 0, 0).setLineWidth(lineWidth);
                    builder.addVertex(last, x0, y0, z1).setColor(r, g, b, a).setNormal(last, 0, 0, -1).setLineWidth(lineWidth);
                    builder.addVertex(last, x0, y0, z0).setColor(r, g, b, a).setNormal(last, 0, 0, -1).setLineWidth(lineWidth);

                    // Top face edges
                    builder.addVertex(last, x0, y1, z0).setColor(r, g, b, a).setNormal(last, 1, 0, 0).setLineWidth(lineWidth);
                    builder.addVertex(last, x1, y1, z0).setColor(r, g, b, a).setNormal(last, 1, 0, 0).setLineWidth(lineWidth);
                    builder.addVertex(last, x1, y1, z0).setColor(r, g, b, a).setNormal(last, 0, 0, 1).setLineWidth(lineWidth);
                    builder.addVertex(last, x1, y1, z1).setColor(r, g, b, a).setNormal(last, 0, 0, 1).setLineWidth(lineWidth);
                    builder.addVertex(last, x1, y1, z1).setColor(r, g, b, a).setNormal(last, -1, 0, 0).setLineWidth(lineWidth);
                    builder.addVertex(last, x0, y1, z1).setColor(r, g, b, a).setNormal(last, -1, 0, 0).setLineWidth(lineWidth);
                    builder.addVertex(last, x0, y1, z1).setColor(r, g, b, a).setNormal(last, 0, 0, -1).setLineWidth(lineWidth);
                    builder.addVertex(last, x0, y1, z0).setColor(r, g, b, a).setNormal(last, 0, 0, -1).setLineWidth(lineWidth);

                    // Vertical edges
                    builder.addVertex(last, x0, y0, z0).setColor(r, g, b, a).setNormal(last, 0, 1, 0).setLineWidth(lineWidth);
                    builder.addVertex(last, x0, y1, z0).setColor(r, g, b, a).setNormal(last, 0, 1, 0).setLineWidth(lineWidth);
                    builder.addVertex(last, x1, y0, z0).setColor(r, g, b, a).setNormal(last, 0, 1, 0).setLineWidth(lineWidth);
                    builder.addVertex(last, x1, y1, z0).setColor(r, g, b, a).setNormal(last, 0, 1, 0).setLineWidth(lineWidth);
                    builder.addVertex(last, x1, y0, z1).setColor(r, g, b, a).setNormal(last, 0, 1, 0).setLineWidth(lineWidth);
                    builder.addVertex(last, x1, y1, z1).setColor(r, g, b, a).setNormal(last, 0, 1, 0).setLineWidth(lineWidth);
                    builder.addVertex(last, x0, y0, z1).setColor(r, g, b, a).setNormal(last, 0, 1, 0).setLineWidth(lineWidth);
                    builder.addVertex(last, x0, y1, z1).setColor(r, g, b, a).setNormal(last, 0, 1, 0).setLineWidth(lineWidth);
                }
            }

            matrixStack.popPose();
            buffer.endBatch(); //call this instead of the rendertype specific end batch to fix wobbling
        }
    }

    public class SelectionInfo {

        public BlockPos selectedBlock;
        public long selectionExpireTime;
        public Color color;

        public SelectionInfo(BlockPos selectedBlock, long selectionExpireTime, Color color) {
            this.selectedBlock = selectedBlock;
            this.selectionExpireTime = selectionExpireTime;
            this.color = color;
        }

        @Override
        public int hashCode() {
            return this.selectedBlock.hashCode();
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this)
                return true;

            SelectionInfo other = (SelectionInfo) obj;
            if (other == null)
                return false;

            return other.selectedBlock.equals(this.selectedBlock);
        }

    }
}
