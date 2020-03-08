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

import com.github.klikli_dev.occultism.Occultism;
import com.github.klikli_dev.occultism.registry.OccultismEffects;
import com.github.klikli_dev.occultism.util.Math3DUtil;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.block.BlockState;
import net.minecraft.client.MainWindow;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.lwjgl.opengl.GL11;

public class ThirdEyeEffectRenderer {

    //region Fields
    public static final float MAX_THIRD_EYE_DISTANCE = 15.0f;
    public static final float MAX_THIRD_EYE_DISTANCE_SQUARED = (float) Math.pow(MAX_THIRD_EYE_DISTANCE, 2);
    public static final ResourceLocation THIRD_EYE_SHADER = new ResourceLocation(Occultism.MODID,
            "shaders/post/third_eye.json");
    public static final ResourceLocation THIRD_EYE_OVERLAY = new ResourceLocation(Occultism.MODID,
            "textures/overlay/third_eye.png");

    public boolean thirdEyeActiveLastTick = false;
    //endregion Fields

    //region Static Methods
    private static void renderOverlay(RenderGameOverlayEvent.Post event, ResourceLocation texture) {

        MainWindow window = Minecraft.getInstance().getMainWindow();
        RenderSystem.pushMatrix();

        RenderSystem.disableDepthTest();
        RenderSystem.depthMask(false);

        Minecraft.getInstance().getTextureManager().bindTexture(texture);

        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBuffer();

        buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
        buffer.pos(0.0D, window.getScaledHeight(), -90.0D).tex(0.0f, 1.0f).endVertex();
        buffer.pos(window.getScaledWidth(), window.getScaledHeight(), -90.0D)
                .tex(1.0f, 1.0f).endVertex();
        buffer.pos(window.getScaledWidth(), 0.0D, -90.0D).tex(1.0f, 0.0f).endVertex();
        buffer.pos(0.0D, 0.0D, -90.0D).tex(0.0f, 0.0f).endVertex();
        tessellator.draw();

        RenderSystem.depthMask(true);
        RenderSystem.enableDepthTest();

        RenderSystem.popMatrix();
    }
    //endregion Static Methods

    //region Methods
    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent event) {
        this.onThirdEyeTick(event);
    }

    @SubscribeEvent
    public void onRenderOverlay(RenderGameOverlayEvent.Post event) {
        if (event.getType() == RenderGameOverlayEvent.ElementType.HELMET) {
            if (this.thirdEyeActiveLastTick) {

                RenderSystem.enableBlend();
                RenderSystem.blendFuncSeparate(
                        GlStateManager.SourceFactor.SRC_ALPHA,
                        GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA,
                        GlStateManager.SourceFactor.ONE,
                        GlStateManager.DestFactor.ZERO);

                RenderSystem.color4f(1, 1, 1, 1);

                renderOverlay(event, THIRD_EYE_OVERLAY);

                RenderSystem.color4f(1, 1, 1, 1);
            }
        }
    }

    public boolean shouldRenderThirdEye(BlockState state, BlockPos pos) {
        return this.thirdEyeActiveLastTick &&
               Minecraft.getInstance().player.getDistanceSq(Math3DUtil.center(pos)) < MAX_THIRD_EYE_DISTANCE_SQUARED;
    }

    public void onThirdEyeTick(TickEvent.PlayerTickEvent event) {
        if (event.player.isPotionActive(OccultismEffects.THIRD_EYE.get())) {
            if (!this.thirdEyeActiveLastTick) {
                this.thirdEyeActiveLastTick = true;

                //load shader
                Minecraft.getInstance()
                        .enqueue(() -> Minecraft.getInstance().gameRenderer.loadShader(THIRD_EYE_SHADER));

            }
            else if (event.player.world.getGameTime() % (20 * 2) == 0) {
            }
        }
        else {
            if (this.thirdEyeActiveLastTick) {
                this.thirdEyeActiveLastTick = false;
                //clean up block render

                //unload shader
                Minecraft.getInstance().enqueue(() -> Minecraft.getInstance().gameRenderer.stopUseShader());

            }

        }
    }
}
