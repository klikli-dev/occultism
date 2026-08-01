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

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.PoseStack.Pose;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.SubmitCustomGeometryEvent;

import java.awt.*;
import java.util.HashSet;
import java.util.Set;

public class SelectedBlockRenderer {
    private static final float EDGE_THICKNESS = 1.0f / 16.0f;
    private static final float EDGE_OFFSET = 0.001f;

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
    public void onRenderLevelStage(SubmitCustomGeometryEvent event) {
        if (this.selectedBlocks.isEmpty())
            return;

        long time = System.currentTimeMillis();
        this.selectedBlocks.removeIf(info -> time > info.selectionExpireTime || info.selectedBlock == null);
        if (this.selectedBlocks.isEmpty())
            return;

        Vec3 camera = event.getLevelRenderState().cameraRenderState.pos;
        PoseStack poseStack = event.getPoseStack();
        var collector = event.getSubmitNodeCollector();

        poseStack.pushPose();
        var renderType = OccultismRenderType.overlayLines();
        poseStack.translate(-camera.x, -camera.y, -camera.z);
        collector.submitCustomGeometry(poseStack, renderType, (pose, consumer) -> this.renderSelectedBlocks(pose, consumer));
        poseStack.popPose();
    }

    protected void renderSelectedBlocks(PoseStack.Pose pose, VertexConsumer builder) {
        for (SelectionInfo info : this.selectedBlocks) {
            if (info.selectedBlock == null)
                continue;

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

            this.renderFrameEdge(builder, pose, x0 - EDGE_OFFSET, y0 - EDGE_OFFSET, z0 - EDGE_OFFSET, x1 + EDGE_OFFSET, y0 - EDGE_OFFSET + EDGE_THICKNESS, z0 - EDGE_OFFSET + EDGE_THICKNESS, r, g, b, a);
            this.renderFrameEdge(builder, pose, x0 - EDGE_OFFSET, y0 - EDGE_OFFSET, z1 + EDGE_OFFSET - EDGE_THICKNESS, x1 + EDGE_OFFSET, y0 - EDGE_OFFSET + EDGE_THICKNESS, z1 + EDGE_OFFSET, r, g, b, a);
            this.renderFrameEdge(builder, pose, x0 - EDGE_OFFSET, y1 + EDGE_OFFSET - EDGE_THICKNESS, z0 - EDGE_OFFSET, x1 + EDGE_OFFSET, y1 + EDGE_OFFSET, z0 - EDGE_OFFSET + EDGE_THICKNESS, r, g, b, a);
            this.renderFrameEdge(builder, pose, x0 - EDGE_OFFSET, y1 + EDGE_OFFSET - EDGE_THICKNESS, z1 + EDGE_OFFSET - EDGE_THICKNESS, x1 + EDGE_OFFSET, y1 + EDGE_OFFSET, z1 + EDGE_OFFSET, r, g, b, a);

            this.renderFrameEdge(builder, pose, x0 - EDGE_OFFSET, y0 - EDGE_OFFSET, z0 - EDGE_OFFSET, x0 - EDGE_OFFSET + EDGE_THICKNESS, y0 - EDGE_OFFSET + EDGE_THICKNESS, z1 + EDGE_OFFSET, r, g, b, a);
            this.renderFrameEdge(builder, pose, x1 + EDGE_OFFSET - EDGE_THICKNESS, y0 - EDGE_OFFSET, z0 - EDGE_OFFSET, x1 + EDGE_OFFSET, y0 - EDGE_OFFSET + EDGE_THICKNESS, z1 + EDGE_OFFSET, r, g, b, a);
            this.renderFrameEdge(builder, pose, x0 - EDGE_OFFSET, y1 + EDGE_OFFSET - EDGE_THICKNESS, z0 - EDGE_OFFSET, x0 - EDGE_OFFSET + EDGE_THICKNESS, y1 + EDGE_OFFSET, z1 + EDGE_OFFSET, r, g, b, a);
            this.renderFrameEdge(builder, pose, x1 + EDGE_OFFSET - EDGE_THICKNESS, y1 + EDGE_OFFSET - EDGE_THICKNESS, z0 - EDGE_OFFSET, x1 + EDGE_OFFSET, y1 + EDGE_OFFSET, z1 + EDGE_OFFSET, r, g, b, a);

            this.renderFrameEdge(builder, pose, x0 - EDGE_OFFSET, y0 - EDGE_OFFSET, z0 - EDGE_OFFSET, x0 - EDGE_OFFSET + EDGE_THICKNESS, y1 + EDGE_OFFSET, z0 - EDGE_OFFSET + EDGE_THICKNESS, r, g, b, a);
            this.renderFrameEdge(builder, pose, x1 + EDGE_OFFSET - EDGE_THICKNESS, y0 - EDGE_OFFSET, z0 - EDGE_OFFSET, x1 + EDGE_OFFSET, y1 + EDGE_OFFSET, z0 - EDGE_OFFSET + EDGE_THICKNESS, r, g, b, a);
            this.renderFrameEdge(builder, pose, x0 - EDGE_OFFSET, y0 - EDGE_OFFSET, z1 + EDGE_OFFSET - EDGE_THICKNESS, x0 - EDGE_OFFSET + EDGE_THICKNESS, y1 + EDGE_OFFSET, z1 + EDGE_OFFSET, r, g, b, a);
            this.renderFrameEdge(builder, pose, x1 + EDGE_OFFSET - EDGE_THICKNESS, y0 - EDGE_OFFSET, z1 + EDGE_OFFSET - EDGE_THICKNESS, x1 + EDGE_OFFSET, y1 + EDGE_OFFSET, z1 + EDGE_OFFSET, r, g, b, a);
        }
    }

    protected void renderFrameEdge(VertexConsumer builder, Pose pose, float x0, float y0, float z0, float x1, float y1,
                                   float z1, float r, float g, float b, float a) {
        this.renderQuad(builder, pose, x0, y0, z0, x1, y0, z0, x1, y1, z0, x0, y1, z0, r, g, b, a);
        this.renderQuad(builder, pose, x0, y0, z1, x0, y1, z1, x1, y1, z1, x1, y0, z1, r, g, b, a);
        this.renderQuad(builder, pose, x0, y0, z0, x0, y0, z1, x1, y0, z1, x1, y0, z0, r, g, b, a);
        this.renderQuad(builder, pose, x0, y1, z0, x1, y1, z0, x1, y1, z1, x0, y1, z1, r, g, b, a);
        this.renderQuad(builder, pose, x0, y0, z0, x0, y1, z0, x0, y1, z1, x0, y0, z1, r, g, b, a);
        this.renderQuad(builder, pose, x1, y0, z0, x1, y0, z1, x1, y1, z1, x1, y1, z0, r, g, b, a);
    }

    protected void renderQuad(VertexConsumer builder, Pose pose, float ax, float ay, float az, float bx, float by, float bz,
                              float cx, float cy, float cz, float dx, float dy, float dz, float r, float g, float b, float a) {
        builder.addVertex(pose, ax, ay, az).setColor(r, g, b, a);
        builder.addVertex(pose, bx, by, bz).setColor(r, g, b, a);
        builder.addVertex(pose, cx, cy, cz).setColor(r, g, b, a);
        builder.addVertex(pose, dx, dy, dz).setColor(r, g, b, a);
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
