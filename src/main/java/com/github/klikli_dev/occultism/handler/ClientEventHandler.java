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
import com.github.klikli_dev.occultism.registry.PotionRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.List;

public class ClientEventHandler {
    //region Fields
    public static final float MAX_THIRD_EYE_DISTANCE = 15.0f;
    public static final float MAX_THIRD_EYE_DISTANCE_SQUARED = (float) Math.pow(MAX_THIRD_EYE_DISTANCE, 2);
    public static final ResourceLocation THIRD_EYE_SHADER = new ResourceLocation(Occultism.MODID,
            "shaders/post/third_eye.json");
    public static final ResourceLocation THIRD_EYE_OVERLAY = new ResourceLocation(Occultism.MODID,
            "textures/overlay/third_eye.png");
    public boolean thirdEyeActiveLastTick = false;
    public int postEffectTicks = 0;

    public List<BlockPos> thirdEyeBlocks = new ArrayList<>();
    //endregion Fields

    //region Static Methods
    private static void renderOverlay(RenderGameOverlayEvent.Post event, ResourceLocation texture) {

        GlStateManager.pushMatrix();

        GlStateManager.disableDepth();
        GlStateManager.depthMask(false);

        Minecraft.getMinecraft().renderEngine.bindTexture(texture);

        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBuffer();

        buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
        buffer.pos(0.0D, event.getResolution().getScaledHeight(), -90.0D).tex(0.0D, 1.0D).endVertex();
        buffer.pos(event.getResolution().getScaledWidth(), event.getResolution().getScaledHeight(), -90.0D)
                .tex(1.0D, 1.0D).endVertex();
        buffer.pos(event.getResolution().getScaledWidth(), 0.0D, -90.0D).tex(1.0D, 0.0D).endVertex();
        buffer.pos(0.0D, 0.0D, -90.0D).tex(0.0D, 0.0D).endVertex();
        tessellator.draw();

        GlStateManager.depthMask(true);
        GlStateManager.enableDepth();

        GlStateManager.popMatrix();
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

                GlStateManager.enableBlend();
                OpenGlHelper.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, GL11.GL_ONE, GL11.GL_ZERO);
                //This blend function ignores alpha making it a lot darker
                //                GlStateManager
                //                        .tryBlendFuncSeparate(GlStateManager.SourceFactor.ZERO, GlStateManager.DestFactor.SRC_COLOR,
                //                                GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_COLOR);
                GlStateManager.color(1, 1, 1, 1);

                renderOverlay(event, THIRD_EYE_OVERLAY);

                GlStateManager.color(1, 1, 1, 1);
            }
        }
    }

    public boolean shouldRenderThirdEye(BlockState state, BlockPos pos) {
        return this.thirdEyeActiveLastTick &&
               Minecraft.getMinecraft().player.getDistanceSq(pos) < MAX_THIRD_EYE_DISTANCE_SQUARED;
    }

    public void triggerThirdEyeUpdate(World world, BlockPos pos, float distance) {
        BlockPos min = pos.add(-distance, -distance, -distance);
        BlockPos max = pos.add(distance, distance, distance);
        world.markBlockRangeForRenderUpdate(min, max);
    }

    public void cleanupThirdEyeBlocks(World world, boolean delete) {
        for (BlockPos pos : this.thirdEyeBlocks) {
            world.markBlockRangeForRenderUpdate(pos, pos);
        }
        if (delete)
            this.thirdEyeBlocks.clear();
    }

    public void onThirdEyeTick(TickEvent.PlayerTickEvent event) {
        if (event.player.isPotionActive(PotionRegistry.THIRD_EYE)) {
            if (!this.thirdEyeActiveLastTick) {
                this.thirdEyeActiveLastTick = true;
                this.postEffectTicks = 0;

                //initial block render update after triggering third eye
                this.triggerThirdEyeUpdate(event.player.world, event.player.getPosition(), MAX_THIRD_EYE_DISTANCE);
                //load shader
                Minecraft.getMinecraft()
                        .addScheduledTask(() -> Minecraft.getMinecraft().entityRenderer.loadShader(THIRD_EYE_SHADER));

            }
            else if (event.player.world.getTotalWorldTime() % (20 * 2) == 0) {
                this.triggerThirdEyeUpdate(event.player.world, event.player.getPosition(), MAX_THIRD_EYE_DISTANCE);
            }
        }
        else {
            if (this.thirdEyeActiveLastTick) {
                this.thirdEyeActiveLastTick = false;
                //clean up block render

                //this.cleanupThirdEyeBlocks(event.player.world);
                this.cleanupThirdEyeBlocks(event.player.world, false);
                //unload shader
                Minecraft.getMinecraft()
                        .addScheduledTask(() -> Minecraft.getMinecraft().entityRenderer.stopUseShader());
            }
            //not sure why, but a few updates in short intervals are required to reliably reset the blocks.
            if (event.player.world.getTotalWorldTime() % (20 * 1) == 0) {
                if (this.postEffectTicks < 3) {
                    this.postEffectTicks++;
                    this.cleanupThirdEyeBlocks(event.player.world, false);
                }
                if (this.postEffectTicks == 3) {
                    this.postEffectTicks++;
                    this.cleanupThirdEyeBlocks(event.player.world, true);
                }
            }
        }
    }
    //endregion Methods
}
