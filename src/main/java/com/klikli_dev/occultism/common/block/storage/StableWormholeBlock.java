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
import com.klikli_dev.occultism.Occultism;
import com.klikli_dev.occultism.common.blockentity.StableWormholeBlockEntity;
import com.klikli_dev.occultism.common.container.storage.StorageControllerContainerBase;
import com.klikli_dev.occultism.registry.OccultismBlockEntities;
import com.klikli_dev.occultism.registry.OccultismDataComponents;
import com.klikli_dev.occultism.util.BlockEntityUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.component.DataComponents;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import javax.annotation.Nullable;
import java.util.Map;
import java.util.stream.Stream;

public class StableWormholeBlock extends Block implements EntityBlock {

    public static final Property<Boolean> LINKED = BooleanProperty.create("linked");
    private static final Map<Direction, VoxelShape> SHAPES = Maps.newEnumMap(
            ImmutableMap.<Direction, VoxelShape>builder()
                    .put(Direction.EAST, Stream.of(
                            Block.box(0, 4, 4, 1, 12, 12),
                            Block.box(0, 4, 1, 1, 12, 4),
                            Block.box(0, 4, 12, 1, 12, 15),
                            Block.box(0, 6, 0, 1, 10, 1),
                            Block.box(0, 6, 15, 1, 10, 16),
                            Block.box(0, 2, 12, 1, 4, 13),
                            Block.box(0, 12, 12, 1, 14, 13),
                            Block.box(0, 2, 3, 1, 4, 4),
                            Block.box(0, 3, 2, 1, 4, 3),
                            Block.box(0, 12, 2, 1, 13, 3),
                            Block.box(0, 3, 13, 1, 4, 14),
                            Block.box(0, 12, 13, 1, 13, 14),
                            Block.box(0, 12, 3, 1, 14, 4),
                            Block.box(0, 1, 4, 1, 4, 12),
                            Block.box(0, 12, 4, 1, 15, 12),
                            Block.box(0, 0, 6, 1, 1, 10),
                            Block.box(0, 15, 6, 1, 16, 10)
                    ).reduce((v1, v2) -> {
                        return Shapes.join(v1, v2, BooleanOp.OR);
                    }).get())
                    .put(Direction.WEST, Stream.of(
                            Block.box(15, 4, 4, 16, 12, 12),
                            Block.box(15, 4, 1, 16, 12, 4),
                            Block.box(15, 4, 12, 16, 12, 15),
                            Block.box(15, 6, 0, 16, 10, 1),
                            Block.box(15, 6, 15, 16, 10, 16),
                            Block.box(15, 2, 12, 16, 4, 13),
                            Block.box(15, 12, 12, 16, 14, 13),
                            Block.box(15, 2, 3, 16, 4, 4),
                            Block.box(15, 3, 2, 16, 4, 3),
                            Block.box(15, 12, 2, 16, 13, 3),
                            Block.box(15, 3, 13, 16, 4, 14),
                            Block.box(15, 12, 13, 16, 13, 14),
                            Block.box(15, 12, 3, 16, 14, 4),
                            Block.box(15, 1, 4, 16, 4, 12),
                            Block.box(15, 12, 4, 16, 15, 12),
                            Block.box(15, 0, 6, 16, 1, 10),
                            Block.box(15, 15, 6, 16, 16, 10)
                    ).reduce((v1, v2) -> {
                        return Shapes.join(v1, v2, BooleanOp.OR);
                    }).get())
                    .put(Direction.NORTH, Stream.of(
                            Block.box(4, 4, 15, 12, 12, 16),
                            Block.box(1, 4, 15, 4, 12, 16),
                            Block.box(12, 4, 15, 15, 12, 16),
                            Block.box(0, 6, 15, 1, 10, 16),
                            Block.box(15, 6, 15, 16, 10, 16),
                            Block.box(12, 2, 15, 13, 4, 16),
                            Block.box(12, 12, 15, 13, 14, 16),
                            Block.box(3, 2, 15, 4, 4, 16),
                            Block.box(2, 3, 15, 3, 4, 16),
                            Block.box(2, 12, 15, 3, 13, 16),
                            Block.box(13, 3, 15, 14, 4, 16),
                            Block.box(13, 12, 15, 14, 13, 16),
                            Block.box(3, 12, 15, 4, 14, 16),
                            Block.box(4, 1, 15, 12, 4, 16),
                            Block.box(4, 12, 15, 12, 15, 16),
                            Block.box(6, 0, 15, 10, 1, 16),
                            Block.box(6, 15, 15, 10, 16, 16)
                    ).reduce((v1, v2) -> {
                        return Shapes.join(v1, v2, BooleanOp.OR);
                    }).get())
                    .put(Direction.SOUTH, Stream.of(
                            Block.box(4, 4, 0, 12, 12, 1),
                            Block.box(1, 4, 0, 4, 12, 1),
                            Block.box(12, 4, 0, 15, 12, 1),
                            Block.box(0, 6, 0, 1, 10, 1),
                            Block.box(15, 6, 0, 16, 10, 1),
                            Block.box(12, 2, 0, 13, 4, 1),
                            Block.box(12, 12, 0, 13, 14, 1),
                            Block.box(3, 2, 0, 4, 4, 1),
                            Block.box(2, 3, 0, 3, 4, 1),
                            Block.box(2, 12, 0, 3, 13, 1),
                            Block.box(13, 3, 0, 14, 4, 1),
                            Block.box(13, 12, 0, 14, 13, 1),
                            Block.box(3, 12, 0, 4, 14, 1),
                            Block.box(4, 1, 0, 12, 4, 1),
                            Block.box(4, 12, 0, 12, 15, 1),
                            Block.box(6, 0, 0, 10, 1, 1),
                            Block.box(6, 15, 0, 10, 16, 1)
                    ).reduce((v1, v2) -> {
                        return Shapes.join(v1, v2, BooleanOp.OR);
                    }).get())
                    .put(Direction.UP, Stream.of(
                            Block.box(4, 0, 4, 12, 1, 12),
                            Block.box(1, 0, 4, 4, 1, 12),
                            Block.box(12, 0, 4, 15, 1, 12),
                            Block.box(0, 0, 6, 1, 1, 10),
                            Block.box(15, 0, 6, 16, 1, 10),
                            Block.box(12, 0, 12, 13, 1, 14),
                            Block.box(12, 0, 2, 13, 1, 4),
                            Block.box(3, 0, 12, 4, 1, 14),
                            Block.box(2, 0, 12, 3, 1, 13),
                            Block.box(2, 0, 3, 3, 1, 4),
                            Block.box(13, 0, 12, 14, 1, 13),
                            Block.box(13, 0, 3, 14, 1, 4),
                            Block.box(3, 0, 2, 4, 1, 4),
                            Block.box(4, 0, 12, 12, 1, 15),
                            Block.box(4, 0, 1, 12, 1, 4),
                            Block.box(6, 0, 15, 10, 1, 16),
                            Block.box(6, 0, 0, 10, 1, 1)
                    ).reduce((v1, v2) -> {
                        return Shapes.join(v1, v2, BooleanOp.OR);
                    }).get())
                    .put(Direction.DOWN, Stream.of(
                            Block.box(4, 15, 4, 12, 16, 12),
                            Block.box(1, 15, 4, 4, 16, 12),
                            Block.box(12, 15, 4, 15, 16, 12),
                            Block.box(0, 15, 6, 1, 16, 10),
                            Block.box(15, 15, 6, 16, 16, 10),
                            Block.box(12, 15, 12, 13, 16, 14),
                            Block.box(12, 15, 2, 13, 16, 4),
                            Block.box(3, 15, 12, 4, 16, 14),
                            Block.box(2, 15, 12, 3, 16, 13),
                            Block.box(2, 15, 3, 3, 16, 4),
                            Block.box(13, 15, 12, 14, 16, 13),
                            Block.box(13, 15, 3, 14, 16, 4),
                            Block.box(3, 15, 2, 4, 16, 4),
                            Block.box(4, 15, 12, 12, 16, 15),
                            Block.box(4, 15, 1, 12, 16, 4),
                            Block.box(6, 15, 15, 10, 16, 16),
                            Block.box(6, 15, 0, 10, 16, 1)
                    ).reduce((v1, v2) -> {
                        return Shapes.join(v1, v2, BooleanOp.OR);
                    }).get()).build());

    public StableWormholeBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.getStateDefinition().any().setValue(LINKED, false));
    }

    @Override
    @SuppressWarnings("deprecation")
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        return SHAPES.get(state.getValue(BlockStateProperties.FACING));
    }

    @Override
    @SuppressWarnings("deprecation")
    public VoxelShape getCollisionShape(BlockState state, BlockGetter worldIn, BlockPos pos,
                                        CollisionContext context) {
        return Shapes.empty();
    }

    @Override
    @SuppressWarnings("deprecation")
    public void onRemove(BlockState state, Level worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
        if(Occultism.SERVER_CONFIG.storage.unlinkWormholeOnBreak.get()){
            BlockEntity blockEntity = worldIn.getBlockEntity(pos);
            if (blockEntity instanceof StableWormholeBlockEntity wormhole) {
                if (wormhole.getLinkedStorageController() != null) {
                    wormhole.setLinkedStorageControllerPosition(null);
                }
            }
        }
        super.onRemove(state, worldIn, pos, newState, isMoving);
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack pStack, BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHitResult) {
        if (!pLevel.isClientSide) {
            BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
            if (blockEntity instanceof StableWormholeBlockEntity wormhole && StorageControllerContainerBase.canOpen(pPlayer, pPos)) {
                if (wormhole.getLinkedStorageController() != null && pPlayer instanceof ServerPlayer serverPlayer) {
                    serverPlayer.openMenu(wormhole, pPos);
                    StorageControllerContainerBase.reserve(pPlayer, pPos);
                } else {
                    pLevel.setBlock(pPos, pState.setValue(LINKED, false), 2);
                }
            }
        }
        return ItemInteractionResult.SUCCESS;
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockState state = this.defaultBlockState().setValue(BlockStateProperties.FACING, context.getClickedFace());
        if (context.getItemInHand().has(OccultismDataComponents.LINKED_STORAGE_CONTROLLER)) {
            state = state.setValue(LINKED, true);
        }
        return state;
    }

    @Override
    @SuppressWarnings("deprecation")
    public ItemStack getCloneItemStack(LevelReader worldIn, BlockPos pos, BlockState state) {
        return BlockEntityUtil.getItemWithNbt(this, worldIn, pos);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(LINKED, BlockStateProperties.FACING);
        super.createBlockStateDefinition(builder);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return OccultismBlockEntities.STABLE_WORMHOLE.get().create(blockPos, blockState);
    }
}


