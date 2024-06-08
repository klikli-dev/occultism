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

package com.klikli_dev.occultism.common.block.storage;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.klikli_dev.occultism.common.blockentity.StorageControllerBlockEntity;
import com.klikli_dev.occultism.util.Math3DUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DirectionalBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;

public class StorageStabilizerBlock extends Block {

    private static final Map<Direction, VoxelShape> SHAPES = Maps.newEnumMap(
            ImmutableMap.<Direction, VoxelShape>builder()
                    .put(Direction.EAST, Shapes.create(new AABB(0, 0, 0, 0.3, 1, 1)))
                    .put(Direction.WEST, Shapes.create(new AABB(0.7, 0, 0, 1, 1, 1)))
                    .put(Direction.NORTH, Shapes.create(new AABB(0, 0, 0.7, 1, 1, 1)))
                    .put(Direction.SOUTH, Shapes.create(new AABB(0, 0, 0, 1, 1, 0.3)))
                    .put(Direction.UP, Shapes.create(new AABB(0, 0, 0, 1, 0.3, 1)))
                    .put(Direction.DOWN, Shapes.create(new AABB(0, 0.7, 0, 1, 1, 1))).build());

    public StorageStabilizerBlock(Properties properties) {
        super(properties);

    }

    @Override
    protected boolean isPathfindable(BlockState pState, PathComputationType pPathComputationType) {
        return false;
    }

    @Override
    @SuppressWarnings("deprecation")
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving) {
        if (state != newState) {
            this.notifyStorageControllers(level, pos, state);
        }
        super.onRemove(state, level, pos, newState, isMoving);
    }

    @Override
    @SuppressWarnings("deprecation")
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        return SHAPES.get(state.getValue(BlockStateProperties.FACING));
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(BlockStateProperties.FACING, context.getClickedFace());
    }

    @Override
    public void playerDestroy(Level worldIn, Player player, BlockPos pos, BlockState state,
                              @Nullable BlockEntity te, ItemStack stack) {
        super.playerDestroy(worldIn, player, pos, state, te, stack);
    }

    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, LivingEntity placer,
                            ItemStack stack) {
        this.notifyStorageControllers(level, pos, state);

        super.setPlacedBy(level, pos, state, placer, stack);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(BlockStateProperties.FACING);
        super.createBlockStateDefinition(builder);
    }

    public void notifyStorageControllers(Level level, BlockPos pos, BlockState state) {
        Direction facing = state.getValue(DirectionalBlock.FACING);

        //storage controller actually wants stabilizers to point at one block above it, so unless we are on y axis we trace one below
        BlockPos min = facing != Direction.DOWN && facing != Direction.UP ? pos.below() : pos;
        //trace a straight line for the possible controller positions
        List<BlockPos> blocks = Math3DUtil.simpleTrace(min, facing,
                StorageControllerBlockEntity.MAX_STABILIZER_DISTANCE);

        //we are also using the fake trace here, because players may have placed a block on this trace line.
        //The trace line is actually below the line the controller uses to check for valid stabilizers.
        //This is due to the stabilizers aiming for one block above the controller.
        for (BlockPos block : blocks) {
            BlockEntity blockEntity = level.getBlockEntity(block);
            if (blockEntity instanceof StorageControllerBlockEntity controller) {
                controller.updateStabilizers(); //force controller to re-check available stabilizers.
            }
        }
    }
}


