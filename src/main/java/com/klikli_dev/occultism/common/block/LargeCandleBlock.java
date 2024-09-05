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

package com.klikli_dev.occultism.common.block;

import com.google.common.collect.ImmutableList;
import com.klikli_dev.occultism.registry.OccultismItems;
import com.klikli_dev.occultism.registry.OccultismParticles;
import com.mojang.serialization.MapCodec;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMaps;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.AbstractCandleBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.List;
import java.util.function.ToIntFunction;
import java.util.stream.Stream;

public class LargeCandleBlock extends AbstractCandleBlock implements SimpleWaterloggedBlock {
    public static final MapCodec<LargeCandleBlock> CODEC = simpleCodec(LargeCandleBlock::new);
    public static final IntegerProperty CANDLES = BlockStateProperties.CANDLES; //This is defining the fire type since I can create a new property without errors
    public static final BooleanProperty LIT = BlockStateProperties.LIT;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final ToIntFunction<BlockState> LIGHT_EMISSION = p_152848_ -> p_152848_.getValue(LIT) ? 15 : 0;

    private static final Int2ObjectMap<List<Vec3>> PARTICLE_OFFSETS = Util.make(
            () -> {
                Int2ObjectMap<List<Vec3>> int2objectmap = new Int2ObjectOpenHashMap<>();
                int2objectmap.defaultReturnValue(ImmutableList.of(new Vec3(0.5, 0.1, 0.5)));
                return Int2ObjectMaps.unmodifiable(int2objectmap);
            }
    ); //Hide Vanilla fire particles

    private static final VoxelShape SHAPE = Stream.of(
            Block.box(7, 0, 10, 9, 4, 11),
            Block.box(10, 0, 8, 11, 3, 9),
            Block.box(10, 0, 6, 11, 1, 7),
            Block.box(11, 0, 7, 12, 1, 8),
            Block.box(7, 0, 11, 8, 1, 12),
            Block.box(6, 0, 10, 7, 1, 11),
            Block.box(4, 0, 7, 5, 1, 8),
            Block.box(5, 0, 6, 6, 1, 7),
            Block.box(7, 0, 5, 8, 2, 6),
            Block.box(10, 0, 7, 11, 5, 8),
            Block.box(8, 0, 5, 9, 6, 6),
            Block.box(5, 0, 7, 6, 3, 9),
            Block.box(6, 0, 6, 10, 9, 10),
            Block.box(7.75, 8, 7.75, 8.25, 10, 8.25)
    ).reduce((v1, v2) -> {
        return Shapes.join(v1, v2, BooleanOp.OR);
    }).get();

    @Override
    public MapCodec<LargeCandleBlock> codec() {
        return CODEC;
    }

    public LargeCandleBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(
                this.stateDefinition
                        .any()
                        .setValue(LIT, Boolean.valueOf(false))
                        .setValue(CANDLES, 1)
                        .setValue(WATERLOGGED, Boolean.valueOf(false))
        );
    }

    @Override
    protected ItemInteractionResult useItemOn(
            ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult
    ) {
        if (player.getAbilities().mayBuild && state.getValue(LIT)) {
            if (stack.isEmpty()){
                extinguish(player, state, level, pos);
                return ItemInteractionResult.sidedSuccess(level.isClientSide);
            } else if (stack.getItem() == Items.TORCH.asItem()) {
                level.setBlock(pos, state.setValue(CANDLES, 1), 11);
                return ItemInteractionResult.sidedSuccess(level.isClientSide);
            } else if (stack.getItem() == Items.SOUL_TORCH.asItem()) {
                level.setBlock(pos, state.setValue(CANDLES, 2), 11);
                return ItemInteractionResult.sidedSuccess(level.isClientSide);
            } else if (stack.getItem() == Items.REDSTONE_TORCH.asItem()) {
                level.setBlock(pos, state.setValue(CANDLES, 3), 11);
                return ItemInteractionResult.sidedSuccess(level.isClientSide);
            } else if (stack.getItem() == OccultismItems.SPIRIT_TORCH.asItem()) {
                level.setBlock(pos, state.setValue(CANDLES, 4), 11);
                return ItemInteractionResult.sidedSuccess(level.isClientSide);
            } else {
                return super.useItemOn(stack, state, level, pos, player, hand, hitResult);
            }
        } else {
            return super.useItemOn(stack, state, level, pos, player, hand, hitResult);
        }
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        FluidState fluidstate = context.getLevel().getFluidState(context.getClickedPos());
        boolean flag = fluidstate.getType() == Fluids.WATER;
        return super.getStateForPlacement(context).setValue(WATERLOGGED, Boolean.valueOf(flag));
    }

    @Override
    protected BlockState updateShape(
            BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos pos, BlockPos neighborPos
    ) {
        if (state.getValue(WATERLOGGED)) {
            level.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
        }

        return super.updateShape(state, direction, neighborState, level, pos, neighborPos);
    }

    @Override
    protected FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(LIT, WATERLOGGED, CANDLES);
        super.createBlockStateDefinition(builder);
    }

    @Override
    public boolean placeLiquid(LevelAccessor level, BlockPos pos, BlockState state, FluidState fluidState) {
        if (!state.getValue(WATERLOGGED) && fluidState.getType() == Fluids.WATER) {
            BlockState blockstate = state.setValue(WATERLOGGED, Boolean.valueOf(true));
            if (state.getValue(LIT)) {
                extinguish(null, blockstate, level, pos);
            } else {
                level.setBlock(pos, blockstate, 3);
            }

            level.scheduleTick(pos, fluidState.getType(), fluidState.getType().getTickDelay(level));
            return true;
        } else {
            return false;
        }
    }

    public static boolean canLight(BlockState state) {
        return state.is(BlockTags.CANDLES, p_152810_ -> p_152810_.hasProperty(LIT) && p_152810_.hasProperty(WATERLOGGED))
                && !state.getValue(LIT)
                && !state.getValue(WATERLOGGED);
    }

    @Override
    protected Iterable<Vec3> getParticleOffsets(BlockState state) {
        return PARTICLE_OFFSETS.get(1);
    }

    @Override
    protected boolean canBeLit(BlockState state) {
        return !state.getValue(WATERLOGGED) && super.canBeLit(state);
    }

    @Override
    @SuppressWarnings("deprecation")
    public void neighborChanged(BlockState state, Level worldIn, BlockPos pos, Block blockIn, BlockPos fromPos,
                                boolean isMoving) {
        if (!this.canSurvive(state, worldIn, pos)) {
            dropResources(state, worldIn, pos);
            worldIn.removeBlock(pos, false);
        }
    }
    @Override
    @SuppressWarnings("deprecation")
    public boolean canSurvive(BlockState state, LevelReader worldIn, BlockPos pos) {
        BlockPos down = pos.below();
        BlockState downState = worldIn.getBlockState(down);
        return downState.isFaceSturdy(worldIn, down, Direction.UP);
    }

    @Override
    public float getEnchantPowerBonus(BlockState state, LevelReader level, BlockPos pos) {
        return 1;
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos blockPos, RandomSource rand) {
        if (state.getValue(LIT)){
            double d0 = (double) blockPos.getX() + 0.5D;
            double d1 = (double) blockPos.getY() + 0.7D;
            double d2 = (double) blockPos.getZ() + 0.5D;
            float f = rand.nextFloat();
            if (f < 0.9F) {
                switch (state.getValue(CANDLES)) {
                    case 1:
                        level.addParticle(ParticleTypes.FLAME, d0, d1, d2, 0.0D, 0.0D, 0.0D);
                        if (f < 0.24F) {
                            level.addParticle(ParticleTypes.SMOKE, d0, d1, d2, 0.0D, 0.0D, 0.0D);
                        }
                        break;
                    case 2:
                        level.addParticle(ParticleTypes.SOUL_FIRE_FLAME, d0, d1, d2, 0.0D, 0.0D, 0.0D);
                        if (f < 0.24F) {
                            level.addParticle(ParticleTypes.SOUL, d0, d1, d2, 0.0D, 0.09D, 0.0D);
                        }
                        break;
                    case 3:
                        level.addParticle(OccultismParticles.RED_FIRE_FLAME.get(), d0, d1, d2, 0.0D, 0.0D, 0.0D);
                        if (f < 0.24F) {
                            level.addParticle(ParticleTypes.TRIAL_SPAWNER_DETECTED_PLAYER, d0, d1, d2, 0.0D, -0.03D, 0.0D);
                        }
                        break;
                    case 4:
                        level.addParticle(OccultismParticles.SPIRIT_FIRE_FLAME.get(), d0, d1, d2, 0.0D, 0.0D, 0.0D);
                        if (f < 0.24F) {
                            level.addParticle(ParticleTypes.DRAGON_BREATH, d0, d1, d2, 0.0D, 0.02D, 0.0D);
                        }
                        break;
                }
            }

        }
        super.animateTick(state, level, blockPos, rand);
    }

}
