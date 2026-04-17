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
import com.klikli_dev.occultism.util.CuriosUtil;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent.Post;

import java.util.HashSet;
import java.util.Set;

public class ThirdEyeEffectRenderer {

    public static final int MAX_THIRD_EYE_DISTANCE = 10;
    public static final Identifier THIRD_EYE_TEXTURE = Identifier.fromNamespaceAndPath(Occultism.MODID,
            "textures/overlay/third_eye.png");
    public boolean thirdEyeActiveLastTick = false;
    public boolean gogglesActiveLastTick = false;

    public Set<BlockPos> uncoveredBlocks = new HashSet<>();

    @SubscribeEvent
    public void onPlayerTick(Post event) {
        if (event.getEntity().level().isClientSide() && event.getEntity() == Minecraft.getInstance().player) {
            this.onThirdEyeTick(event);
            this.onGogglesTick(event);
            this.onStaffTick(event);
        }
    }

    public void renderOverlay(PoseStack pose) {
        // RenderSystem overlay rendering removed in 26.1 - stubbed out
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

    public void onThirdEyeTick(Post event) {
        boolean hasGoggles = CuriosUtil.hasGoggles(event.getEntity());
        if (hasGoggles)
            return;

        var effect = event.getEntity().getEffect(OccultismEffects.THIRD_EYE);
        int duration = effect == null ? 0 : effect.getDuration();
        if (duration > 1) {
            if (!this.thirdEyeActiveLastTick) {
                this.thirdEyeActiveLastTick = true;
            }
            //also handle goggles in one if we have them
            this.uncoverBlocks(event.getEntity(), event.getEntity().level(), OtherworldBlockTier.ONE);
        } else {
            //if we don't have goggles, cover blocks
            //Try twice, but on the last effect tick, clear the list.
            this.resetUncoveredBlocks(event.getEntity().level(), duration == 0);

            if (this.thirdEyeActiveLastTick) {
                this.thirdEyeActiveLastTick = false;
            }
        }
    }

    public void onGogglesTick(Post event) {
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

    public void onStaffTick(Post event) {
        if (CuriosUtil.hasStaff(event.getEntity())) {
            this.uncoverBlocks(event.getEntity(), event.getEntity().level(), OtherworldBlockTier.TWO);
        } else {
            //only cover blocks if third eye is not active and still needs them visible.
            if (!this.gogglesActiveLastTick)
                this.resetUncoveredBlocks(event.getEntity().level(), true);
            if (this.thirdEyeActiveLastTick) {
                //this uncovers tier 1 blocks that we still can see under normal third eye
                this.uncoverBlocks(event.getEntity(), event.getEntity().level(), OtherworldBlockTier.ONE);
            }
        }
    }
}
