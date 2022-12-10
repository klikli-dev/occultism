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
 * PURvertexE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT
 * OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
 * OTHER DEALINGS IN THE SOFTWARE.
 */

package com.github.klikli_dev.occultism.util;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;
import org.joml.Matrix4f;

import javax.annotation.Nullable;

public class RenderUtil {

    //region Static Methods

    public static void buildBlockOutline(VertexConsumer buffer, Matrix4f positionMatrix, float mx, float my, float mz, float r, float g, float b, float a) {
        buffer.vertex(positionMatrix, mx, my, mz).color(r, g, b, a).endVertex();
        buffer.vertex(positionMatrix, mx + 1, my, mz).color(r, g, b, a).endVertex();
        buffer.vertex(positionMatrix, mx, my, mz).color(r, g, b, a).endVertex();
        buffer.vertex(positionMatrix, mx, my + 1, mz).color(r, g, b, a).endVertex();
        buffer.vertex(positionMatrix, mx, my, mz).color(r, g, b, a).endVertex();
        buffer.vertex(positionMatrix, mx, my, mz + 1).color(r, g, b, a).endVertex();
        buffer.vertex(positionMatrix, mx + 1, my + 1, mz + 1).color(r, g, b, a).endVertex();
        buffer.vertex(positionMatrix, mx, my + 1, mz + 1).color(r, g, b, a).endVertex();
        buffer.vertex(positionMatrix, mx + 1, my + 1, mz + 1).color(r, g, b, a).endVertex();
        buffer.vertex(positionMatrix, mx + 1, my, mz + 1).color(r, g, b, a).endVertex();
        buffer.vertex(positionMatrix, mx + 1, my + 1, mz + 1).color(r, g, b, a).endVertex();
        buffer.vertex(positionMatrix, mx + 1, my + 1, mz).color(r, g, b, a).endVertex();

        buffer.vertex(positionMatrix, mx, my + 1, mz).color(r, g, b, a).endVertex();
        buffer.vertex(positionMatrix, mx, my + 1, mz + 1).color(r, g, b, a).endVertex();
        buffer.vertex(positionMatrix, mx, my + 1, mz).color(r, g, b, a).endVertex();
        buffer.vertex(positionMatrix, mx + 1, my + 1, mz).color(r, g, b, a).endVertex();

        buffer.vertex(positionMatrix, mx + 1, my, mz).color(r, g, b, a).endVertex();
        buffer.vertex(positionMatrix, mx + 1, my, mz + 1).color(r, g, b, a).endVertex();
        buffer.vertex(positionMatrix, mx + 1, my, mz).color(r, g, b, a).endVertex();
        buffer.vertex(positionMatrix, mx + 1, my + 1, mz).color(r, g, b, a).endVertex();

        buffer.vertex(positionMatrix, mx, my, mz + 1).color(r, g, b, a).endVertex();
        buffer.vertex(positionMatrix, mx + 1, my, mz + 1).color(r, g, b, a).endVertex();
        buffer.vertex(positionMatrix, mx, my, mz + 1).color(r, g, b, a).endVertex();
        buffer.vertex(positionMatrix, mx, my + 1, mz + 1).color(r, g, b, a).endVertex();
    }

    /**
     * copied from ItemRenderer.renderGuiItemDecorations but allows to scale
     */
    public static void renderGuiItemDecorationsWithPose(ItemRenderer renderer, Font pFr, PoseStack posestack, ItemStack pStack, int pXPosition, int pYPosition, @Nullable String pText) {
        if (!pStack.isEmpty()) {
            if (pStack.getCount() != 1 || pText != null) {
                String s = pText == null ? String.valueOf(pStack.getCount()) : pText;
                posestack.translate(0.0D, 0.0D, renderer.blitOffset + 200.0F);
                MultiBufferSource.BufferSource multibuffersource$buffersource = MultiBufferSource.immediate(Tesselator.getInstance().getBuilder());
                pFr.drawInBatch(s, (float) (pXPosition + 19 - 2 - pFr.width(s)), (float) (pYPosition + 6 + 3), 16777215, true, posestack.last().pose(), multibuffersource$buffersource, false, 0, 15728880);
                multibuffersource$buffersource.endBatch();
            }

            if (pStack.getItem().isBarVisible(pStack)) {
                RenderSystem.disableDepthTest();
                RenderSystem.disableTexture();
                RenderSystem.disableBlend();
                Tesselator tesselator = Tesselator.getInstance();
                BufferBuilder bufferbuilder = tesselator.getBuilder();
                double health = pStack.getItem().getBarWidth(pStack);
                int i = Math.round(13.0F - (float) health * 13.0F);
                int j = pStack.getItem().getBarColor(pStack);
                renderer.fillRect(bufferbuilder, pXPosition + 2, pYPosition + 13, 13, 2, 0, 0, 0, 255);
                renderer.fillRect(bufferbuilder, pXPosition + 2, pYPosition + 13, i, 1, j >> 16 & 255, j >> 8 & 255, j & 255, 255);
                RenderSystem.enableBlend();
                RenderSystem.enableTexture();
                RenderSystem.enableDepthTest();
            }

            LocalPlayer localplayer = Minecraft.getInstance().player;
            float f = localplayer == null ? 0.0F : localplayer.getCooldowns().getCooldownPercent(pStack.getItem(), Minecraft.getInstance().getFrameTime());
            if (f > 0.0F) {
                RenderSystem.disableDepthTest();
                RenderSystem.disableTexture();
                RenderSystem.enableBlend();
                RenderSystem.defaultBlendFunc();
                Tesselator tesselator1 = Tesselator.getInstance();
                BufferBuilder bufferbuilder1 = tesselator1.getBuilder();
                renderer.fillRect(bufferbuilder1, pXPosition, pYPosition + Mth.floor(16.0F * (1.0F - f)), 16, Mth.ceil(16.0F * f), 255, 255, 255, 127);
                RenderSystem.enableTexture();
                RenderSystem.enableDepthTest();
            }

        }
    }
    //endregion Static Methods
}
