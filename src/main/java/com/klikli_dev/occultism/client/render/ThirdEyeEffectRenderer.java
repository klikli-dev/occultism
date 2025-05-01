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
import com.klikli_dev.occultism.api.common.data.OtherworldBlockTier;
import com.klikli_dev.occultism.common.block.otherworld.IOtherworldBlock;
import com.klikli_dev.occultism.registry.OccultismEffects;
import com.klikli_dev.occultism.registry.OccultismItems;
import com.klikli_dev.occultism.util.CuriosUtil;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

import java.util.HashSet;
import java.util.Set;

public class ThirdEyeEffectRenderer {

    public static final int MAX_THIRD_EYE_DISTANCE = 10;
    public static final ResourceLocation THIRD_EYE_SHADER = ResourceLocation.fromNamespaceAndPath(Occultism.MODID,
            "shaders/post/third_eye.json");
    public static final ResourceLocation THIRD_EYE_TEXTURE = ResourceLocation.fromNamespaceAndPath(Occultism.MODID,
            "textures/overlay/third_eye.png");
    public boolean thirdEyeActiveLastTick = false;
    public boolean gogglesActiveLastTick = false;

    public Set<BlockPos> uncoveredBlocks = new HashSet<>();

    @SubscribeEvent
    public void onPlayerTick(PlayerTickEvent.Post event) {
        if (event.getEntity().level().isClientSide && event.getEntity() == Minecraft.getInstance().player) {
            this.onThirdEyeTick(event);
            this.onGogglesTick(event);
            this.onStaffTick(event);
        }
    }

    public void renderOverlay(PoseStack pose) {
        RenderSystem.setShaderTexture(0, ThirdEyeEffectRenderer.THIRD_EYE_TEXTURE);
        RenderSystem.enableBlend();
        RenderSystem.blendFuncSeparate(
                GlStateManager.SourceFactor.SRC_ALPHA,
                GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA,
                GlStateManager.SourceFactor.ONE,
                GlStateManager.DestFactor.ZERO);

        RenderSystem.clearColor(1, 1, 1, 1);

        Window window = Minecraft.getInstance().getWindow();
        pose.pushPose();

        RenderSystem.disableDepthTest();
        RenderSystem.depthMask(false);

        BufferBuilder buffer = Tesselator.getInstance().begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
        buffer.addVertex(0.0f, window.getGuiScaledHeight(), -90.0f).setUv(0.0f, 1.0f);
        buffer.addVertex(window.getGuiScaledWidth(), window.getGuiScaledHeight(), -90.0f).setUv(1.0f, 1.0f);
        buffer.addVertex(window.getGuiScaledWidth(), 0.0f, -90.0f).setUv(1.0f, 0.0f);
        buffer.addVertex(0.0f, 0.0f, -90.0f).setUv(0.0f, 0.0f);
        BufferUploader.drawWithShader(buffer.buildOrThrow());

        RenderSystem.depthMask(true);
        RenderSystem.enableDepthTest();

        pose.popPose();

        RenderSystem.clearColor(1, 1, 1, 1);
        RenderSystem.disableBlend();
    }

    /**
     * Resets the currently uncovered blocks
     *
     * @param level the level.
     * @param clear true to delete the list of uncovered blocks.
     */
    public void resetUncoveredBlocks(Level level, boolean clear) {
        for (BlockPos pos : this.uncoveredBlocks) {
            BlockState state = level.getBlockState(pos);
            if (state.getBlock() instanceof IOtherworldBlock) //handle replaced or removed blocks gracefully
                level.setBlock(pos, state.setValue(IOtherworldBlock.UNCOVERED, false), 1);
        }
        if (clear)
            this.uncoveredBlocks.clear();
    }

    /**
     * Uncovers the otherworld blocks within MAX_THIRD_EYE_DISTANCE of the player.
     *
     * @param player the player.
     * @param level  the level.
     */
    public void uncoverBlocks(Player player, Level level, OtherworldBlockTier tier) {
        BlockPos origin = player.blockPosition();
        BlockPos.betweenClosed(origin.offset(-MAX_THIRD_EYE_DISTANCE, -MAX_THIRD_EYE_DISTANCE, -MAX_THIRD_EYE_DISTANCE),
                origin.offset(MAX_THIRD_EYE_DISTANCE, MAX_THIRD_EYE_DISTANCE, MAX_THIRD_EYE_DISTANCE)).forEach(pos -> {
            BlockState state = level.getBlockState(pos);
            if (state.getBlock() instanceof IOtherworldBlock block) {
                if (block.getTier().getLevel() <= tier.getLevel()) {
                    if (!state.getValue(IOtherworldBlock.UNCOVERED)) {
                        level.setBlock(pos, state.setValue(IOtherworldBlock.UNCOVERED, true), Block.UPDATE_IMMEDIATE);
                    }
                    this.uncoveredBlocks.add(pos.immutable());
                }
            }
        });
    }

    public void onThirdEyeTick(PlayerTickEvent.Post event) {
        boolean hasGoggles = CuriosUtil.hasGoggles(event.getEntity());
        if (hasGoggles)
            return;

        var effect = event.getEntity().getEffect(OccultismEffects.THIRD_EYE);
        int duration = effect == null ? 0 : effect.getDuration();
        if (duration > 1) {
            if (!this.thirdEyeActiveLastTick) {
                this.thirdEyeActiveLastTick = true;

                //load shader, but only if we are on the natural effects
                if (!Occultism.CLIENT_CONFIG.visuals.disableDemonsDreamShaders.get()) {
                    Minecraft.getInstance().tell(() -> Minecraft.getInstance().gameRenderer.loadEffect(THIRD_EYE_SHADER));
                }
            }
            //also handle goggles in one if we have them
            this.uncoverBlocks(event.getEntity(), event.getEntity().level(), OtherworldBlockTier.ONE);
        } else {
            //if we don't have goggles, cover blocks
            //Try twice, but on the last effect tick, clear the list.
            this.resetUncoveredBlocks(event.getEntity().level(), duration == 0);

            if (this.thirdEyeActiveLastTick) {
                this.thirdEyeActiveLastTick = false;

                if (!Occultism.CLIENT_CONFIG.visuals.disableDemonsDreamShaders.get()) {
                    //unload shader
                    Minecraft.getInstance().tell(() -> Minecraft.getInstance().gameRenderer.shutdownEffect());
                }
            }
        }
    }

    public void onGogglesTick(PlayerTickEvent.Post event) {
        boolean hasGoggles = CuriosUtil.hasGoggles(event.getEntity());
        if (hasGoggles) {
            if (!this.gogglesActiveLastTick) {
                this.gogglesActiveLastTick = true;
            }
            this.uncoverBlocks(event.getEntity(), event.getEntity().level(), OtherworldBlockTier.TWO);
        } else {
            if (this.gogglesActiveLastTick) {
                this.gogglesActiveLastTick = false;

                //only cover blocks if third eye is not active and still needs them visible.
                this.resetUncoveredBlocks(event.getEntity().level(), true);
                if (this.thirdEyeActiveLastTick) {
                    //this uncovers tier 1 blocks that we still can see under normal third eye
                    this.uncoverBlocks(event.getEntity(), event.getEntity().level(), OtherworldBlockTier.ONE);
                }
            }
        }
    }

    public void onStaffTick(PlayerTickEvent.Post event) {
        boolean hasStaff = event.getEntity().getOffhandItem().is(OccultismItems.TRUE_SIGHT_STAFF) || CuriosUtil.hasStaff(event.getEntity());
        if (hasStaff) {
            this.uncoverBlocks(event.getEntity(), event.getEntity().level(), OtherworldBlockTier.TWO);
        } else {
            //only cover blocks if third eye is not active and still needs them visible.
            if (!gogglesActiveLastTick)
                this.resetUncoveredBlocks(event.getEntity().level(), true);
            if (this.thirdEyeActiveLastTick) {
                //this uncovers tier 1 blocks that we still can see under normal third eye
                this.uncoverBlocks(event.getEntity(), event.getEntity().level(), OtherworldBlockTier.ONE);
            }
        }
    }
}
