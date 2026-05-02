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
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Camera;
import net.minecraft.client.renderer.MultiBufferSource.BufferSource;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Brightness;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.ExtractBlockOutlineRenderStateEvent;
import org.joml.Matrix3f;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector4f;

import java.awt.*;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class SelectedBlockRenderer {
    private static final float EDGE_THICKNESS = 1.0f / 16.0f;
    private static final float ALTERNATIVE_EDGE_THICKNESS = 2.0f / 16.0f;
    private static final float EDGE_OFFSET = 0.001f;
    private static final int FULL_BRIGHT = Brightness.FULL_BRIGHT.pack();

    private final Vector3f minPosTemp = new Vector3f();
    private final Vector3f maxPosTemp = new Vector3f();
    private final Vector4f posTransformTemp = new Vector4f();
    private final Vector3f normalTransformTemp = new Vector3f();

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
    public void onExtractBlockOutlineRenderState(ExtractBlockOutlineRenderStateEvent event) {
        if (this.selectedBlocks.isEmpty())
            return;

        long time = System.currentTimeMillis();
        this.selectedBlocks.removeIf(info -> time > info.selectionExpireTime || info.selectedBlock == null);
        if (this.selectedBlocks.isEmpty())
            return;

        Camera camera = event.getCamera();
        boolean translucentPass = event.isInTranslucentPass();
        event.addCustomRenderer((renderState, buffer, poseStack, currentPass, levelRenderState) -> {
            if (currentPass != translucentPass) {
                return false;
            }

            this.renderSelectedBlocks(poseStack, buffer, camera);
            return false;
        });
    }

    protected void renderSelectedBlocks(PoseStack matrixStack, BufferSource buffer, Camera camera) {
        var useAltRenderer = Occultism.CLIENT_CONFIG.visuals.useAlternativeDivinationRodRenderer.get();
        float edgeThickness = useAltRenderer ? ALTERNATIVE_EDGE_THICKNESS : EDGE_THICKNESS;

        if (!this.selectedBlocks.isEmpty()) {
            var renderType = useAltRenderer ? OccultismRenderType.overlayLinesAlternative() : OccultismRenderType.overlayLines();
            VertexConsumer builder = buffer.getBuffer(renderType);
            matrixStack.pushPose();

            Vec3 cameraPosition = camera.position();
            matrixStack.translate(-cameraPosition.x, -cameraPosition.y, -cameraPosition.z);
            var last = matrixStack.last();

            for (Iterator<SelectionInfo> it = this.selectedBlocks.iterator(); it.hasNext(); ) {
                SelectionInfo info = it.next();

                if (info.selectedBlock != null) {
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

                    this.renderFrameEdge(builder, last, x0 - EDGE_OFFSET, y0 - EDGE_OFFSET, z0 - EDGE_OFFSET, x1 + EDGE_OFFSET, y0 - EDGE_OFFSET + edgeThickness, z0 - EDGE_OFFSET + edgeThickness, r, g, b, a);
                    this.renderFrameEdge(builder, last, x0 - EDGE_OFFSET, y0 - EDGE_OFFSET, z1 + EDGE_OFFSET - edgeThickness, x1 + EDGE_OFFSET, y0 - EDGE_OFFSET + edgeThickness, z1 + EDGE_OFFSET, r, g, b, a);
                    this.renderFrameEdge(builder, last, x0 - EDGE_OFFSET, y1 + EDGE_OFFSET - edgeThickness, z0 - EDGE_OFFSET, x1 + EDGE_OFFSET, y1 + EDGE_OFFSET, z0 - EDGE_OFFSET + edgeThickness, r, g, b, a);
                    this.renderFrameEdge(builder, last, x0 - EDGE_OFFSET, y1 + EDGE_OFFSET - edgeThickness, z1 + EDGE_OFFSET - edgeThickness, x1 + EDGE_OFFSET, y1 + EDGE_OFFSET, z1 + EDGE_OFFSET, r, g, b, a);

                    this.renderFrameEdge(builder, last, x0 - EDGE_OFFSET, y0 - EDGE_OFFSET, z0 - EDGE_OFFSET, x0 - EDGE_OFFSET + edgeThickness, y0 - EDGE_OFFSET + edgeThickness, z1 + EDGE_OFFSET, r, g, b, a);
                    this.renderFrameEdge(builder, last, x1 + EDGE_OFFSET - edgeThickness, y0 - EDGE_OFFSET, z0 - EDGE_OFFSET, x1 + EDGE_OFFSET, y0 - EDGE_OFFSET + edgeThickness, z1 + EDGE_OFFSET, r, g, b, a);
                    this.renderFrameEdge(builder, last, x0 - EDGE_OFFSET, y1 + EDGE_OFFSET - edgeThickness, z0 - EDGE_OFFSET, x0 - EDGE_OFFSET + edgeThickness, y1 + EDGE_OFFSET, z1 + EDGE_OFFSET, r, g, b, a);
                    this.renderFrameEdge(builder, last, x1 + EDGE_OFFSET - edgeThickness, y1 + EDGE_OFFSET - edgeThickness, z0 - EDGE_OFFSET, x1 + EDGE_OFFSET, y1 + EDGE_OFFSET, z1 + EDGE_OFFSET, r, g, b, a);

                    this.renderFrameEdge(builder, last, x0 - EDGE_OFFSET, y0 - EDGE_OFFSET, z0 - EDGE_OFFSET, x0 - EDGE_OFFSET + edgeThickness, y1 + EDGE_OFFSET, z0 - EDGE_OFFSET + edgeThickness, r, g, b, a);
                    this.renderFrameEdge(builder, last, x1 + EDGE_OFFSET - edgeThickness, y0 - EDGE_OFFSET, z0 - EDGE_OFFSET, x1 + EDGE_OFFSET, y1 + EDGE_OFFSET, z0 - EDGE_OFFSET + edgeThickness, r, g, b, a);
                    this.renderFrameEdge(builder, last, x0 - EDGE_OFFSET, y0 - EDGE_OFFSET, z1 + EDGE_OFFSET - edgeThickness, x0 - EDGE_OFFSET + edgeThickness, y1 + EDGE_OFFSET, z1 + EDGE_OFFSET, r, g, b, a);
                    this.renderFrameEdge(builder, last, x1 + EDGE_OFFSET - edgeThickness, y0 - EDGE_OFFSET, z1 + EDGE_OFFSET - edgeThickness, x1 + EDGE_OFFSET, y1 + EDGE_OFFSET, z1 + EDGE_OFFSET, r, g, b, a);
                }
            }

            matrixStack.popPose();
        }
    }

    protected void renderFrameEdge(VertexConsumer builder, PoseStack.Pose pose, float x0, float y0, float z0, float x1, float y1,
            float z1, float r, float g, float b, float a) {
        this.minPosTemp.set(x0, y0, z0);
        this.maxPosTemp.set(x1, y1, z1);
        this.renderCuboid(pose, builder, this.minPosTemp, this.maxPosTemp, r, g, b, a);
    }

    protected void renderCuboid(PoseStack.Pose pose, VertexConsumer builder, Vector3f minPos, Vector3f maxPos, float r, float g, float b,
            float a) {
        Matrix4f posMatrix = pose.pose();
        Matrix3f normalMatrix = pose.normal();
        Vector4f posTransform = this.posTransformTemp;
        Vector3f normalTransform = this.normalTransformTemp;

        float minX = minPos.x();
        float minY = minPos.y();
        float minZ = minPos.z();
        float maxX = maxPos.x();
        float maxY = maxPos.y();
        float maxZ = maxPos.z();

        posTransform.set(minX, minY, maxZ, 1.0f).mul(posMatrix);
        float x0 = posTransform.x();
        float y0 = posTransform.y();
        float z0 = posTransform.z();

        posTransform.set(minX, minY, minZ, 1.0f).mul(posMatrix);
        float x1 = posTransform.x();
        float y1 = posTransform.y();
        float z1 = posTransform.z();

        posTransform.set(maxX, minY, minZ, 1.0f).mul(posMatrix);
        float x2 = posTransform.x();
        float y2 = posTransform.y();
        float z2 = posTransform.z();

        posTransform.set(maxX, minY, maxZ, 1.0f).mul(posMatrix);
        float x3 = posTransform.x();
        float y3 = posTransform.y();
        float z3 = posTransform.z();

        posTransform.set(minX, maxY, minZ, 1.0f).mul(posMatrix);
        float x4 = posTransform.x();
        float y4 = posTransform.y();
        float z4 = posTransform.z();

        posTransform.set(minX, maxY, maxZ, 1.0f).mul(posMatrix);
        float x5 = posTransform.x();
        float y5 = posTransform.y();
        float z5 = posTransform.z();

        posTransform.set(maxX, maxY, maxZ, 1.0f).mul(posMatrix);
        float x6 = posTransform.x();
        float y6 = posTransform.y();
        float z6 = posTransform.z();

        posTransform.set(maxX, maxY, minZ, 1.0f).mul(posMatrix);
        float x7 = posTransform.x();
        float y7 = posTransform.y();
        float z7 = posTransform.z();

        this.putQuad(builder, normalTransform, normalMatrix, x0, y0, z0, x1, y1, z1, x2, y2, z2, x3, y3, z3, 0, -1, 0, r, g, b, a);
        this.putQuad(builder, normalTransform, normalMatrix, x4, y4, z4, x5, y5, z5, x6, y6, z6, x7, y7, z7, 0, 1, 0, r, g, b, a);
        this.putQuad(builder, normalTransform, normalMatrix, x7, y7, z7, x2, y2, z2, x1, y1, z1, x4, y4, z4, 0, 0, -1, r, g, b, a);
        this.putQuad(builder, normalTransform, normalMatrix, x5, y5, z5, x0, y0, z0, x3, y3, z3, x6, y6, z6, 0, 0, 1, r, g, b, a);
        this.putQuad(builder, normalTransform, normalMatrix, x4, y4, z4, x1, y1, z1, x0, y0, z0, x5, y5, z5, -1, 0, 0, r, g, b, a);
        this.putQuad(builder, normalTransform, normalMatrix, x6, y6, z6, x3, y3, z3, x2, y2, z2, x7, y7, z7, 1, 0, 0, r, g, b, a);
    }

    protected void putQuad(VertexConsumer builder, Vector3f normalTransform, Matrix3f normalMatrix, float ax, float ay, float az,
            float bx, float by, float bz, float cx, float cy, float cz, float dx, float dy, float dz, float nx, float ny, float nz,
            float r, float g, float b, float a) {
        normalTransform.set(nx, ny, nz).mul(normalMatrix);
        float tx = normalTransform.x();
        float ty = normalTransform.y();
        float tz = normalTransform.z();

        builder.addVertex(ax, ay, az).setColor(r, g, b, a).setUv(0, 0).setOverlay(OverlayTexture.NO_OVERLAY).setLight(FULL_BRIGHT).setNormal(tx, ty, tz);
        builder.addVertex(bx, by, bz).setColor(r, g, b, a).setUv(0, 1).setOverlay(OverlayTexture.NO_OVERLAY).setLight(FULL_BRIGHT).setNormal(tx, ty, tz);
        builder.addVertex(cx, cy, cz).setColor(r, g, b, a).setUv(1, 1).setOverlay(OverlayTexture.NO_OVERLAY).setLight(FULL_BRIGHT).setNormal(tx, ty, tz);
        builder.addVertex(dx, dy, dz).setColor(r, g, b, a).setUv(1, 0).setOverlay(OverlayTexture.NO_OVERLAY).setLight(FULL_BRIGHT).setNormal(tx, ty, tz);
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
