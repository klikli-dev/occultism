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
import com.github.klikli_dev.occultism.api.common.data.OtherworldBlockTier;
import com.github.klikli_dev.occultism.common.block.otherworld.IOtherworldBlock;
import com.github.klikli_dev.occultism.registry.OccultismEffects;
import com.github.klikli_dev.occultism.util.CuriosUtil;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.BlockPos;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.lwjgl.opengl.GL11;

import java.util.HashSet;
import java.util.Set;

public class ThirdEyeEffectRenderer {

    //region Fields
    public static final int MAX_THIRD_EYE_DISTANCE = 10;

    public static final ResourceLocation THIRD_EYE_SHADER = new ResourceLocation(Occultism.MODID,
            "shaders/post/third_eye.json");
    public static final ResourceLocation THIRD_EYE_OVERLAY = new ResourceLocation(Occultism.MODID,
            "textures/overlay/third_eye.png");

    public boolean thirdEyeActiveLastTick = false;
    public boolean gogglesActiveLastTick = false;

    public Set<BlockPos> uncoveredBlocks = new HashSet<>();
    //endregion Fields

    //region Static Methods
    private static void renderOverlay(RenderGameOverlayEvent.Post event, ResourceLocation texture) {

        Window window = Minecraft.getInstance().getWindow();
        event.getMatrixStack().pushPose();

        RenderSystem.disableDepthTest();
        RenderSystem.depthMask(false);

        Minecraft.getInstance().getTextureManager().bindForSetup(texture);

        Tesselator tessellator = Tesselator.getInstance();
        BufferBuilder buffer = tessellator.getBuilder();

        buffer.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
        buffer.vertex(0.0D, window.getGuiScaledHeight(), -90.0D).uv(0.0f, 1.0f).endVertex();
        buffer.vertex(window.getGuiScaledWidth(), window.getGuiScaledHeight(), -90.0D)
                .uv(1.0f, 1.0f).endVertex();
        buffer.vertex(window.getGuiScaledWidth(), 0.0D, -90.0D).uv(1.0f, 0.0f).endVertex();
        buffer.vertex(0.0D, 0.0D, -90.0D).uv(0.0f, 0.0f).endVertex();
        tessellator.end();

        RenderSystem.depthMask(true);
        RenderSystem.enableDepthTest();

        event.getMatrixStack().popPose();
    }
    //endregion Static Methods

    //region Methods
    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if(event.player.level.isClientSide && event.player == Minecraft.getInstance().player){
            this.onThirdEyeTick(event);
            this.onGogglesTick(event);
        }
    }

    @SubscribeEvent
    public void onPreRenderOverlay(RenderGameOverlayEvent.Pre event) {
        //TODO: Remove this hack once MC fixes shader rendering on their own
        //      Based on: https://discordapp.com/channels/313125603924639766/725850371834118214/784883909694980167
        RenderSystem.enableTexture();
    }

    @SubscribeEvent
    public void onRenderOverlay(RenderGameOverlayEvent.Post event) {
        //Elementtype was HELMET in 1.16
        if (event.getType() == RenderGameOverlayEvent.ElementType.BOSSINFO) {
            if (this.thirdEyeActiveLastTick || this.gogglesActiveLastTick) {
                RenderSystem.enableBlend();
                RenderSystem.blendFuncSeparate(
                        GlStateManager.SourceFactor.SRC_ALPHA,
                        GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA,
                        GlStateManager.SourceFactor.ONE,
                        GlStateManager.DestFactor.ZERO);

                RenderSystem.clearColor(1, 1, 1, 1);

                renderOverlay(event, THIRD_EYE_OVERLAY);

                RenderSystem.clearColor(1, 1, 1, 1);
                RenderSystem.disableBlend();
            }
        }

    }

    /**
     * Resets the currently uncovered blocks
     * @param level the level.
     * @param clear true to delete the list of uncovered blocks.
     */
    public void resetUncoveredBlocks(Level level, boolean clear){
        for(BlockPos pos : this.uncoveredBlocks) {
            BlockState state = level.getBlockState(pos);
            if(state.getBlock() instanceof IOtherworldBlock) //handle replaced or removed blocks gracefully
                level.setBlock(pos, state.setValue(IOtherworldBlock.UNCOVERED, false), 1);
        }
        if(clear)
            this.uncoveredBlocks.clear();
    }

    /**
     * Uncovers the otherworld blocks within MAX_THIRD_EYE_DISTANCE of the player.
     *
     * @param player the player.
     * @param level the level.
     */
    public void uncoverBlocks(Player player, Level level, OtherworldBlockTier tier){
        BlockPos origin = player.blockPosition();
        BlockPos.betweenClosed(origin.offset(-MAX_THIRD_EYE_DISTANCE, -MAX_THIRD_EYE_DISTANCE, -MAX_THIRD_EYE_DISTANCE),
                origin.offset(MAX_THIRD_EYE_DISTANCE,MAX_THIRD_EYE_DISTANCE,MAX_THIRD_EYE_DISTANCE)).forEach(pos -> {
            BlockState state = level.getBlockState(pos);
            if(state.getBlock() instanceof IOtherworldBlock){
                IOtherworldBlock block = (IOtherworldBlock) state.getBlock();
                if(block.getTier().getLevel()  <= tier.getLevel()){
                    if(!state.getValue(IOtherworldBlock.UNCOVERED)){
                        level.setBlock(pos, state.setValue(IOtherworldBlock.UNCOVERED, true), 1);
                    }
                    this.uncoveredBlocks.add(pos.immutable());
                }
            }
        });
    }

    public void onThirdEyeTick(TickEvent.PlayerTickEvent event) {
        boolean hasGoggles = CuriosUtil.hasGoggles(event.player);
        if (hasGoggles)
            return;

        MobEffectInstance effect = event.player.getEffect(OccultismEffects.THIRD_EYE.get());
        int duration = effect == null ? 0 : effect.getDuration();
        if (duration > 1) {
            if (!this.thirdEyeActiveLastTick) {
                this.thirdEyeActiveLastTick = true;

                //load shader, but only if we are on the natural effects
                if(!Occultism.CLIENT_CONFIG.visuals.disableDemonsDreamShaders.get()){
                    Minecraft.getInstance().tell(() -> Minecraft.getInstance().gameRenderer.loadEffect(THIRD_EYE_SHADER));
                }
            }
            //also handle goggles in one if we have them
            this.uncoverBlocks(event.player, event.player.level, OtherworldBlockTier.ONE);
        }
        else {
            //if we don't have goggles, cover blocks
            //Try twice, but on the last effect tick, clear the list.
            this.resetUncoveredBlocks(event.player.level, duration == 0);

            if (this.thirdEyeActiveLastTick) {
                this.thirdEyeActiveLastTick = false;

                if (!Occultism.CLIENT_CONFIG.visuals.disableDemonsDreamShaders.get()) {
                    //unload shader
                    Minecraft.getInstance().tell(() -> Minecraft.getInstance().gameRenderer.shutdownEffect());
                }
            }
        }
    }

    public void onGogglesTick(TickEvent.PlayerTickEvent event){
        boolean hasGoggles = CuriosUtil.hasGoggles(event.player);
        if(hasGoggles){
            if(!this.gogglesActiveLastTick){
                this.gogglesActiveLastTick = true;
            }
            this.uncoverBlocks(event.player, event.player.level, OtherworldBlockTier.TWO);
        }
        else {
            if (this.gogglesActiveLastTick) {
                this.gogglesActiveLastTick = false;

                //only cover blocks if third eye is not active and still needs them visible.
                this.resetUncoveredBlocks(event.player.level, true);
                if (this.thirdEyeActiveLastTick) {
                    //this uncovers tier 1 blocks that we still can see under normal third eye
                    this.uncoverBlocks(event.player, event.player.level, OtherworldBlockTier.ONE);
                }
            }
        }
    }
}
