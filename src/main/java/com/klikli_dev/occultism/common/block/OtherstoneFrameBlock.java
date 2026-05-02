package com.klikli_dev.occultism.common.block;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.stream.Stream;

public class OtherstoneFrameBlock extends Block {

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

    public OtherstoneFrameBlock(Properties properties) {
        super(properties);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        return SHAPES.get(state.getValue(BlockStateProperties.FACING));
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockState state = this.defaultBlockState().setValue(BlockStateProperties.FACING, context.getClickedFace());
        return state;
    }

    @Override
    protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
        builder.add(BlockStateProperties.FACING);
        super.createBlockStateDefinition(builder);
    }
}
