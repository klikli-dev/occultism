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

import com.klikli_dev.occultism.api.common.data.ColorBlockState;
import com.klikli_dev.occultism.registry.OccultismRecipes;
import com.klikli_dev.occultism.registry.OccultismSounds;
import com.klikli_dev.occultism.registry.OccultismTags;
import com.klikli_dev.occultism.util.EnumUtil;
import com.klikli_dev.occultism.util.Math3DUtil;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.entity.InsideBlockEffectApplier;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

import java.util.function.Supplier;

public class SpiritFireBlock extends BaseFireBlock {
    public static final EnumProperty<ColorBlockState> COLOR = EnumProperty.create("color", ColorBlockState.class);

    public static final MapCodec<SpiritFireBlock> CODEC = simpleCodec(SpiritFireBlock::new);
    protected Supplier<Integer> color;

    public SpiritFireBlock(Properties properties) {
        super(properties, 0);
        this.registerDefaultState(
                this.stateDefinition
                        .any()
                        .setValue(COLOR, ColorBlockState.WHITE)
        );
    }

    // Remove Theurgy dependency
    private static int mixColors(int color1, int color2, float w) {
        int a1 = (color1 >> 24);
        int r1 = (color1 >> 16) & 0xFF;
        int g1 = (color1 >> 8) & 0xFF;
        int b1 = color1 & 0xFF;
        int a2 = (color2 >> 24);
        int r2 = (color2 >> 16) & 0xFF;
        int g2 = (color2 >> 8) & 0xFF;
        int b2 = color2 & 0xFF;

        return
                ((int) (a1 + (a2 - a1) * w) << 24) +
                        ((int) (r1 + (r2 - r1) * w) << 16) +
                        ((int) (g1 + (g2 - g1) * w) << 8) +
                        ((int) (b1 + (b2 - b1) * w) << 0);
    }

    @Override
    protected MapCodec<? extends BaseFireBlock> codec() {
        return CODEC;
    }

    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        return this.defaultBlockState();
    }

    @Override
    protected boolean canBurn(BlockState pState) {
        return true;
    }

    @Override
    public void entityInside(BlockState pState, Level pLevel, BlockPos pPos, Entity pEntity, InsideBlockEffectApplier effectApplier, boolean isPrecise) {
        if (pLevel instanceof ServerLevel serverLevel && pEntity instanceof ItemEntity item) {
            var recipeInput =
                    new SingleRecipeInput(item.getItem());
            var recipe =
                    serverLevel.recipeAccess().getRecipeFor(OccultismRecipes.SPIRIT_FIRE_TYPE.get(), recipeInput, serverLevel);

            if (recipe.isPresent() && !item.isRemoved()) {
                item.remove(RemovalReason.DISCARDED);

                ItemStack result = recipe.get().value().assemble(recipeInput);
                Vec3 center = Math3DUtil.center(pPos);
                Containers.dropItemStack(pLevel, center.x, center.y + 0.5, center.z, result);

                pLevel.playSound(null, pPos, OccultismSounds.START_RITUAL.get(), SoundSource.BLOCKS, 1, 1);
            }
        }
    }

    protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
        builder.add(COLOR);
        super.createBlockStateDefinition(builder);
    }

    public int getColor(BlockState state, int i) {
        return i == 1 ? mixColors(this.getColor(state, 0), 0xFFFFFF, 0.6F) :
                EnumUtil.getConfiguredColor((byte) state.getValue(COLOR).ordinal());
    }

    @Override
    protected InteractionResult useItemOn(
            ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult
    ) {
        if (player.getAbilities().mayBuild) {
            if (stack.is(OccultismTags.Items.TOOLS_CHALK)) {
                level.setBlockAndUpdate(pos, state.setValue(COLOR, ColorBlockState.fromChalk(stack.getItem())));
                return InteractionResult.SUCCESS;
            }
        }
        return super.useItemOn(stack, state, level, pos, player, hand, hitResult);
    }

    @Override
    public BlockState updateShape(BlockState pState, LevelReader pLevel, ScheduledTickAccess scheduledTickAccess, BlockPos pCurrentPos, Direction pFacing, BlockPos pFacingPos, BlockState pFacingState, RandomSource random) {
        return this.canSurvive(pState, pLevel, pCurrentPos) ? pState : Blocks.AIR.defaultBlockState();
    }

    @Override
    @SuppressWarnings("deprecation")
    public boolean canSurvive(BlockState state, LevelReader worldIn, BlockPos pos) {
        BlockPos below = pos.below();
        return worldIn.getBlockState(below).isFaceSturdy(worldIn, pos, Direction.UP);
    }
}
