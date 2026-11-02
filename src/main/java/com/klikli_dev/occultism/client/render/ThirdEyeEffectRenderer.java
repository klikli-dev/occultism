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
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.gui.GuiLayer;
import net.neoforged.neoforge.event.tick.PlayerTickEvent.Post;

import java.util.HashSet;
import java.util.Set;

public class ThirdEyeEffectRenderer implements GuiLayer {

    public static final int MAX_THIRD_EYE_DISTANCE = 10;
    /**
     * Even if nothing changed, rescan periodically to pick up newly placed/loaded blocks or block states reset by the server.
     */
    public static final int RESCAN_INTERVAL_TICKS = 10;
    public static final Identifier THIRD_EYE_TEXTURE = Identifier.fromNamespaceAndPath(Occultism.MODID,
            "textures/overlay/third_eye.png");
    public boolean thirdEyeActiveLastTick = false;
    public boolean gogglesActiveLastTick = false;
    public boolean staffActiveLastTick = false;

    public Set<BlockPos> uncoveredBlocks = new HashSet<>();

    protected Level lastLevel;
    protected BlockPos lastOrigin;
    protected int lastStaffRange = -1;
    protected int ticksSinceScan = 0;

    @SubscribeEvent
    public void onPlayerTick(Post event) {
        if (event.getEntity().level().isClientSide() && event.getEntity() == Minecraft.getInstance().player) {
            this.onClientPlayerTick(event.getEntity());
        }
    }

    @Override
    public void render(GuiGraphicsExtractor guiGraphics, DeltaTracker deltaTracker) {
        var minecraft = Minecraft.getInstance();
        if (minecraft.player == null || minecraft.options.hideGui) {
            return;
        }

        if (this.gogglesActiveLastTick || this.thirdEyeActiveLastTick) {
            this.renderOverlay(guiGraphics);
        }
    }

    public void renderOverlay(GuiGraphicsExtractor guiGraphics) {
        guiGraphics.blit(RenderPipelines.GUI_TEXTURED, THIRD_EYE_TEXTURE, 0, 0, 0.0f, 0.0f,
                guiGraphics.guiWidth(), guiGraphics.guiHeight(), 256, 256);
    }

    /**
     * Determines all active sources of otherworld sight (third eye effect, goggles, divination staff),
     * and updates the uncovered blocks in a single pass.
     * Only rescans if the player moved to another block, a source changed, or every {@link #RESCAN_INTERVAL_TICKS}.
     */
    public void onClientPlayerTick(Player player) {
        Level level = player.level();

        //on level change the old positions are meaningless (and the client level was replaced anyway)
        if (level != this.lastLevel) {
            this.uncoveredBlocks.clear();
            this.lastLevel = level;
            this.lastOrigin = null;
        }

        boolean hasGoggles = CuriosUtil.hasGoggles(player);
        boolean hasStaff = CuriosUtil.hasStaff(player);
        var effect = player.getEffect(OccultismEffects.THIRD_EYE);
        boolean hasThirdEye = effect != null && effect.getDuration() > 1;
        int staffRange = hasStaff ? Occultism.CLIENT_CONFIG.visuals.trueSightStaffRange.getAsInt() : -1;

        boolean sourcesChanged = hasGoggles != this.gogglesActiveLastTick
                || hasThirdEye != this.thirdEyeActiveLastTick
                || hasStaff != this.staffActiveLastTick
                || staffRange != this.lastStaffRange;

        this.gogglesActiveLastTick = hasGoggles;
        this.thirdEyeActiveLastTick = hasThirdEye;
        this.staffActiveLastTick = hasStaff;
        this.lastStaffRange = staffRange;

        boolean anySource = hasGoggles || hasThirdEye || hasStaff;
        if (!anySource && this.uncoveredBlocks.isEmpty()) {
            this.ticksSinceScan = 0;
            this.lastOrigin = null;
            return;
        }

        BlockPos origin = player.blockPosition();
        this.ticksSinceScan++;
        if (!sourcesChanged && origin.equals(this.lastOrigin) && this.ticksSinceScan < RESCAN_INTERVAL_TICKS) {
            return;
        }
        this.ticksSinceScan = 0;
        this.lastOrigin = origin;

        Set<BlockPos> visible = new HashSet<>();
        if (anySource) {
            int tierOneDistance = 0;
            int tierTwoDistance = 0;
            if (hasThirdEye)
                tierOneDistance = MAX_THIRD_EYE_DISTANCE;
            if (hasGoggles)
                tierTwoDistance = MAX_THIRD_EYE_DISTANCE;
            if (hasStaff)
                tierTwoDistance = Math.max(tierTwoDistance, staffRange);
            //anything visible to tier two is also visible to tier one
            tierOneDistance = Math.max(tierOneDistance, tierTwoDistance);

            this.collectVisibleBlocks(level, origin, tierOneDistance, tierTwoDistance, visible);
        }

        //cover blocks that are no longer visible
        for (BlockPos pos : this.uncoveredBlocks) {
            if (!visible.contains(pos)) {
                this.setUncovered(level, pos, false);
            }
        }

        //uncover newly visible blocks (or blocks whose state was reset, e.g. by a server update)
        for (BlockPos pos : visible) {
            this.setUncovered(level, pos, true);
        }

        this.uncoveredBlocks = visible;
    }

    /**
     * Collects all otherworld blocks that should be uncovered.
     *
     * @param tierOneDistance max distance (cube radius) for tier one blocks, or 0 if none.
     * @param tierTwoDistance max distance (cube radius) for tier two blocks, or 0 if none.
     */
    protected void collectVisibleBlocks(Level level, BlockPos origin, int tierOneDistance, int tierTwoDistance, Set<BlockPos> result) {
        int distance = Math.max(tierOneDistance, tierTwoDistance);
        if (distance <= 0)
            return;

        BlockPos.betweenClosed(origin.offset(-distance, -distance, -distance),
                origin.offset(distance, distance, distance)).forEach(pos -> {
            BlockState state = level.getBlockState(pos);
            if (state.getBlock() instanceof IOtherworldBlock block) {
                int tierLevel = block.getTier().getLevel();
                int maxDistance = 0;
                if (tierLevel <= OtherworldBlockTier.ONE.getLevel())
                    maxDistance = tierOneDistance;
                else if (tierLevel <= OtherworldBlockTier.TWO.getLevel())
                    maxDistance = tierTwoDistance;

                if (maxDistance > 0 && chebyshevDistance(origin, pos) <= maxDistance) {
                    result.add(pos.immutable());
                }
            }
        });
    }

    protected static int chebyshevDistance(BlockPos a, BlockPos b) {
        return Math.max(Math.abs(a.getX() - b.getX()), Math.max(Math.abs(a.getY() - b.getY()), Math.abs(a.getZ() - b.getZ())));
    }

    /**
     * Sets the uncovered state of the block at the given position, if it is an otherworld block and the state differs.
     */
    protected void setUncovered(Level level, BlockPos pos, boolean uncovered) {
        BlockState state = level.getBlockState(pos);
        //handle replaced or removed blocks gracefully
        if (state.getBlock() instanceof IOtherworldBlock && state.getValue(IOtherworldBlock.UNCOVERED) != uncovered) {
            level.setBlock(pos, state.setValue(IOtherworldBlock.UNCOVERED, uncovered),
                    uncovered ? Block.UPDATE_IMMEDIATE : Block.UPDATE_NEIGHBORS);
        }
    }
}
