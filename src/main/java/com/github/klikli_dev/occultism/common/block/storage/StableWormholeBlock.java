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

package com.github.klikli_dev.occultism.common.block.storage;

import com.github.klikli_dev.occultism.common.tile.StableWormholeBlockEntity;
import com.github.klikli_dev.occultism.registry.OccultismTiles;
import com.github.klikli_dev.occultism.util.BlockEntityUtil;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.ServerPlayer;
import net.minecraft.inventory.container.MenuProvider;
import net.minecraft.item.BlockPlaceContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.Property;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.core.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.CollisionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.Shapes;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.stream.Stream;

public class StableWormholeBlock extends Block {

    //region Fields
    public static final Property<Boolean> LINKED = BooleanProperty.create("linked");
    private static final Map<Direction, VoxelShape> SHAPES = Maps.newEnumMap(
            ImmutableMap.<Direction, VoxelShape>builder()
                    .put(Direction.EAST, Stream.of(
                            Block.makeCuboidShape(0, 4, 4, 1, 12, 12),
                            Block.makeCuboidShape(0, 4, 1, 1, 12, 4),
                            Block.makeCuboidShape(0, 4, 12, 1, 12, 15),
                            Block.makeCuboidShape(0, 6, 0, 1, 10, 1),
                            Block.makeCuboidShape(0, 6, 15, 1, 10, 16),
                            Block.makeCuboidShape(0, 2, 12, 1, 4, 13),
                            Block.makeCuboidShape(0, 12, 12, 1, 14, 13),
                            Block.makeCuboidShape(0, 2, 3, 1, 4, 4),
                            Block.makeCuboidShape(0, 3, 2, 1, 4, 3),
                            Block.makeCuboidShape(0, 12, 2, 1, 13, 3),
                            Block.makeCuboidShape(0, 3, 13, 1, 4, 14),
                            Block.makeCuboidShape(0, 12, 13, 1, 13, 14),
                            Block.makeCuboidShape(0, 12, 3, 1, 14, 4),
                            Block.makeCuboidShape(0, 1, 4, 1, 4, 12),
                            Block.makeCuboidShape(0, 12, 4, 1, 15, 12),
                            Block.makeCuboidShape(0, 0, 6, 1, 1, 10),
                            Block.makeCuboidShape(0, 15, 6, 1, 16, 10)
                    ).reduce((v1, v2) -> {return Shapes.combineAndSimplify(v1, v2, IBooleanFunction.OR);}).get())
                    .put(Direction.WEST, Stream.of(
                            Block.makeCuboidShape(15, 4, 4, 16, 12, 12),
                            Block.makeCuboidShape(15, 4, 1, 16, 12, 4),
                            Block.makeCuboidShape(15, 4, 12, 16, 12, 15),
                            Block.makeCuboidShape(15, 6, 0, 16, 10, 1),
                            Block.makeCuboidShape(15, 6, 15, 16, 10, 16),
                            Block.makeCuboidShape(15, 2, 12, 16, 4, 13),
                            Block.makeCuboidShape(15, 12, 12, 16, 14, 13),
                            Block.makeCuboidShape(15, 2, 3, 16, 4, 4),
                            Block.makeCuboidShape(15, 3, 2, 16, 4, 3),
                            Block.makeCuboidShape(15, 12, 2, 16, 13, 3),
                            Block.makeCuboidShape(15, 3, 13, 16, 4, 14),
                            Block.makeCuboidShape(15, 12, 13, 16, 13, 14),
                            Block.makeCuboidShape(15, 12, 3, 16, 14, 4),
                            Block.makeCuboidShape(15, 1, 4, 16, 4, 12),
                            Block.makeCuboidShape(15, 12, 4, 16, 15, 12),
                            Block.makeCuboidShape(15, 0, 6, 16, 1, 10),
                            Block.makeCuboidShape(15, 15, 6, 16, 16, 10)
                    ).reduce((v1, v2) -> {return Shapes.combineAndSimplify(v1, v2, IBooleanFunction.OR);}).get())
                    .put(Direction.NORTH, Stream.of(
                            Block.makeCuboidShape(4, 4, 15, 12, 12, 16),
                            Block.makeCuboidShape(1, 4, 15, 4, 12, 16),
                            Block.makeCuboidShape(12, 4, 15, 15, 12, 16),
                            Block.makeCuboidShape(0, 6, 15, 1, 10, 16),
                            Block.makeCuboidShape(15, 6, 15, 16, 10, 16),
                            Block.makeCuboidShape(12, 2, 15, 13, 4, 16),
                            Block.makeCuboidShape(12, 12, 15, 13, 14, 16),
                            Block.makeCuboidShape(3, 2, 15, 4, 4, 16),
                            Block.makeCuboidShape(2, 3, 15, 3, 4, 16),
                            Block.makeCuboidShape(2, 12, 15, 3, 13, 16),
                            Block.makeCuboidShape(13, 3, 15, 14, 4, 16),
                            Block.makeCuboidShape(13, 12, 15, 14, 13, 16),
                            Block.makeCuboidShape(3, 12, 15, 4, 14, 16),
                            Block.makeCuboidShape(4, 1, 15, 12, 4, 16),
                            Block.makeCuboidShape(4, 12, 15, 12, 15, 16),
                            Block.makeCuboidShape(6, 0, 15, 10, 1, 16),
                            Block.makeCuboidShape(6, 15, 15, 10, 16, 16)
                    ).reduce((v1, v2) -> {return Shapes.combineAndSimplify(v1, v2, IBooleanFunction.OR);}).get())
                    .put(Direction.SOUTH, Stream.of(
                            Block.makeCuboidShape(4, 4, 0, 12, 12, 1),
                            Block.makeCuboidShape(1, 4, 0, 4, 12, 1),
                            Block.makeCuboidShape(12, 4, 0, 15, 12, 1),
                            Block.makeCuboidShape(0, 6, 0, 1, 10, 1),
                            Block.makeCuboidShape(15, 6, 0, 16, 10, 1),
                            Block.makeCuboidShape(12, 2, 0, 13, 4, 1),
                            Block.makeCuboidShape(12, 12, 0, 13, 14, 1),
                            Block.makeCuboidShape(3, 2, 0, 4, 4, 1),
                            Block.makeCuboidShape(2, 3, 0, 3, 4, 1),
                            Block.makeCuboidShape(2, 12, 0, 3, 13, 1),
                            Block.makeCuboidShape(13, 3, 0, 14, 4, 1),
                            Block.makeCuboidShape(13, 12, 0, 14, 13, 1),
                            Block.makeCuboidShape(3, 12, 0, 4, 14, 1),
                            Block.makeCuboidShape(4, 1, 0, 12, 4, 1),
                            Block.makeCuboidShape(4, 12, 0, 12, 15, 1),
                            Block.makeCuboidShape(6, 0, 0, 10, 1, 1),
                            Block.makeCuboidShape(6, 15, 0, 10, 16, 1)
                    ).reduce((v1, v2) -> {return Shapes.combineAndSimplify(v1, v2, IBooleanFunction.OR);}).get())
                    .put(Direction.UP, Stream.of(
                            Block.makeCuboidShape(4, 0, 4, 12, 1, 12),
                            Block.makeCuboidShape(1, 0, 4, 4, 1, 12),
                            Block.makeCuboidShape(12, 0, 4, 15, 1, 12),
                            Block.makeCuboidShape(0, 0, 6, 1, 1, 10),
                            Block.makeCuboidShape(15, 0, 6, 16, 1, 10),
                            Block.makeCuboidShape(12, 0, 12, 13, 1, 14),
                            Block.makeCuboidShape(12, 0, 2, 13, 1, 4),
                            Block.makeCuboidShape(3, 0, 12, 4, 1, 14),
                            Block.makeCuboidShape(2, 0, 12, 3, 1, 13),
                            Block.makeCuboidShape(2, 0, 3, 3, 1, 4),
                            Block.makeCuboidShape(13, 0, 12, 14, 1, 13),
                            Block.makeCuboidShape(13, 0, 3, 14, 1, 4),
                            Block.makeCuboidShape(3, 0, 2, 4, 1, 4),
                            Block.makeCuboidShape(4, 0, 12, 12, 1, 15),
                            Block.makeCuboidShape(4, 0, 1, 12, 1, 4),
                            Block.makeCuboidShape(6, 0, 15, 10, 1, 16),
                            Block.makeCuboidShape(6, 0, 0, 10, 1, 1)
                    ).reduce((v1, v2) -> {return Shapes.combineAndSimplify(v1, v2, IBooleanFunction.OR);}).get())
                    .put(Direction.DOWN, Stream.of(
                            Block.makeCuboidShape(4, 15, 4, 12, 16, 12),
                            Block.makeCuboidShape(1, 15, 4, 4, 16, 12),
                            Block.makeCuboidShape(12, 15, 4, 15, 16, 12),
                            Block.makeCuboidShape(0, 15, 6, 1, 16, 10),
                            Block.makeCuboidShape(15, 15, 6, 16, 16, 10),
                            Block.makeCuboidShape(12, 15, 12, 13, 16, 14),
                            Block.makeCuboidShape(12, 15, 2, 13, 16, 4),
                            Block.makeCuboidShape(3, 15, 12, 4, 16, 14),
                            Block.makeCuboidShape(2, 15, 12, 3, 16, 13),
                            Block.makeCuboidShape(2, 15, 3, 3, 16, 4),
                            Block.makeCuboidShape(13, 15, 12, 14, 16, 13),
                            Block.makeCuboidShape(13, 15, 3, 14, 16, 4),
                            Block.makeCuboidShape(3, 15, 2, 4, 16, 4),
                            Block.makeCuboidShape(4, 15, 12, 12, 16, 15),
                            Block.makeCuboidShape(4, 15, 1, 12, 16, 4),
                            Block.makeCuboidShape(6, 15, 15, 10, 16, 16),
                            Block.makeCuboidShape(6, 15, 0, 10, 16, 1)
                    ).reduce((v1, v2) -> {return Shapes.combineAndSimplify(v1, v2, IBooleanFunction.OR);}).get()).build());

    //endregion Fields
    //region Initialization
    public StableWormholeBlock(Properties properties) {
        super(properties);
        this.setDefaultState(this.getDefaultState().with(LINKED, false));
    }
    //endregion Initialization

    //region Overrides
    @Override
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        return SHAPES.get(state.get(BlockStateProperties.FACING));
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockGetter worldIn, BlockPos pos,
                                        CollisionContext context) {
        return Shapes.empty();
    }

    @Override
    public void onReplaced(BlockState state, Level worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
        BlockEntityUtil.onBlockChangeDropWithNbt(this, state, worldIn, pos, newState);
        super.onReplaced(state, worldIn, pos, newState, isMoving);
    }

    @Override
    public InteractionResult onBlockActivated(BlockState state, Level level, BlockPos pos, Player player,
                                             InteractionHand handIn, BlockRayTraceResult rayTraceResult) {
        if (!level.isClientSide) {
            BlockEntity blockEntity = level.getBlockEntity(pos);
            if (BlockEntity instanceof StableWormholeBlockEntity) {
                StableWormholeBlockEntity wormhole = (StableWormholeBlockEntity) BlockEntity;
                if(wormhole.getLinkedStorageController() != null)
                    NetworkHooks.openGui((ServerPlayer) player, (MenuProvider) BlockEntity, pos);
                else{
                    level.setBlockState(pos, state.with(LINKED, false), 2);
                }
            }
        }
        return InteractionResult.SUCCESS;
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockState state = this.getDefaultState().with(BlockStateProperties.FACING, context.getClickedFace());
        if (context.getItem().getOrCreateTag().getCompound("BlockEntityTag")
                    .contains("linkedStorageControllerPosition")) {
            state = state.with(LINKED, true);
        }
        return state;
    }

    @Override
    public ItemStack getItem(BlockGetter worldIn, BlockPos pos, BlockState state) {
        return BlockEntityUtil.getItemWithNbt(this, worldIn, pos);
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(LINKED, BlockStateProperties.FACING);
        super.fillStateContainer(builder);
    }

    @Override
    public boolean hasBlockEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockState state, BlockGetter level) {
        return OccultismTiles.STABLE_WORMHOLE.get().create();
    }

    //endregion Overrides
}


