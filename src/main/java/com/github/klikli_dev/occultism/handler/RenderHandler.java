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

package com.github.klikli_dev.occultism.handler;


import com.github.klikli_dev.occultism.Occultism;
import com.github.klikli_dev.occultism.client.ClientData;
import com.github.klikli_dev.occultism.util.RenderUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.opengl.GL11;

public class RenderHandler {

    //region Methods
    @SubscribeEvent
    public void renderWorldLastEvent(RenderWorldLastEvent event) {
        this.renderSelectedBlock(event);
    }

    protected void renderSelectedBlock(RenderWorldLastEvent event) {
        ClientData data = Occultism.proxy.getClientData();
        BlockPos pos = data.selectedBlock;
        if (pos == null) {
            return;
        }

        long time = System.currentTimeMillis();
        if (time > data.selectionExpireTime) {
            data.selectBlock(null, -1);
            return;
        }

        ClientPlayerEntity p = Minecraft.getMinecraft().player;
        double doubleX = p.lastTickPosX + (p.posX - p.lastTickPosX) * event.getPartialTicks();
        double doubleY = p.lastTickPosY + (p.posY - p.lastTickPosY) * event.getPartialTicks();
        double doubleZ = p.lastTickPosZ + (p.posZ - p.lastTickPosZ) * event.getPartialTicks();

        GlStateManager.pushMatrix();
        GlStateManager.color(1.0f, 0, 0);
        GlStateManager.glLineWidth(3);
        GlStateManager.translate(-doubleX, -doubleY, -doubleZ);

        GlStateManager.disableDepth();
        GlStateManager.disableTexture2D();

        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBuffer();
        buffer.begin(GL11.GL_LINES, DefaultVertexFormats.POSITION_COLOR);

        RenderUtil.buildBlockOutline(buffer, pos.getX(), pos.getY(), pos.getZ(), 1.0f, 1.0f, 1.0f, 0.8f);

        tessellator.draw();

        GlStateManager.enableTexture2D();
        GlStateManager.popMatrix();
    }
    //endregion Methods
}
