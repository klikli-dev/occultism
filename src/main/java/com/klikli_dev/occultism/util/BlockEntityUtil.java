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

package com.klikli_dev.occultism.util;

import com.klikli_dev.occultism.Occultism;
import com.klikli_dev.occultism.api.common.data.GlobalBlockPos;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.LevelChunk;
import net.neoforged.neoforge.capabilities.BlockCapability;
import net.neoforged.neoforge.server.ServerLifecycleHooks;

public class BlockEntityUtil {
    public static boolean isLoaded(Level level, GlobalBlockPos pos) {
        if (pos == null)
            return false;

        if (level.dimension() == pos.getDimensionKey()) {
            return level.isLoaded(pos.getPos());
        }
        if (level.isClientSide) //can only access other dimensions on the server.
            return false;

        Level dimensionWorld = ServerLifecycleHooks.getCurrentServer().getLevel(pos.getDimensionKey());
        if (dimensionWorld != null)
            return dimensionWorld.isLoaded(pos.getPos());

        return false;
    }

    /**
     * Gets the block entity from the given global position.
     *
     * @param level the level. Returns null if the dimension is unloaded.
     * @param pos   the global position.
     * @return the block entity or null.
     */
    public static BlockEntity get(Level level, GlobalBlockPos pos) {
        if (pos == null)
            return null;

        if (level.dimension() == pos.getDimensionKey()) {
            return getWorldTileEntityUnchecked(level, pos.getPos());
        }
        if (level.isClientSide) //can only access other dimensions on the server.
            return null;

        Level dimensionWorld = ServerLifecycleHooks.getCurrentServer().getLevel(pos.getDimensionKey());
        if (dimensionWorld != null)
            return getWorldTileEntityUnchecked(dimensionWorld, pos.getPos());

        return null;
    }

    static BlockEntity getWorldTileEntityUnchecked(Level level, BlockPos pos) {
        if (!level.isLoaded(pos)) {
            return null;
        } else {
            return level.getChunkAt(pos).getBlockEntity(pos, LevelChunk.EntityCreationType.IMMEDIATE);
        }
    }


    /**
     * Updates the block entity at the given position (mark dirty & send updates)
     *
     * @param level the level to update
     * @param pos   the position to update
     */
    public static void updateTile(Level level, BlockPos pos) {
        if (level == null || level.isClientSide || !level.isLoaded(pos)) {
            return;
        }

        BlockState state = level.getBlockState(pos);
        level.sendBlockUpdated(pos, state, state, 2);
        level.blockEntityChanged(pos);
    }

    /**
     * Checks all faces of a block entity for the given capability.
     *
     * @param blockEntity the block entity to check.
     * @param capability  the capability to check for.
     * @return true if the capability is found on any face.
     */
    public static <T> boolean hasCapabilityOnAnySide(BlockEntity blockEntity, BlockCapability<T, Direction> capability) {
        for (Direction face : Direction.values()) {
            if (blockEntity.getLevel().getCapability(capability, blockEntity.getBlockPos(), blockEntity.getBlockState(), blockEntity, face) != null)
                return true;
        }
        return false;
    }

    /**
     * Handles the common use case of giving self as item with block entity nbt.
     *
     * @param block the current block.
     * @param level the level
     * @param pos   the position.
     */
    public static ItemStack getItemWithNbt(Block block, LevelReader level, BlockPos pos) {
        ItemStack itemStack = new ItemStack(block);
        BlockEntity blockEntity = level.getBlockEntity(pos);

        if (blockEntity != null) {

            blockEntity.saveToItem(itemStack, level.registryAccess());
        } else {
            Occultism.LOGGER.warn("BlockEntity is null for block {} at pos {}, cannot get ItemStack with Components", block, pos);
        }

        return itemStack;
    }
}
