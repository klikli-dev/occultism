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

import com.klikli_dev.occultism.Occultism;
import com.klikli_dev.occultism.registry.OccultismItems;
import com.klikli_dev.occultism.util.EnumUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.neoforged.neoforge.common.Tags.Items;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class RainbowGlyphBlock extends ChalkGlyphBlock {
    public static final IntegerProperty COLOR = IntegerProperty.create("color", 4, 15);
    public static final BooleanProperty CYCLE = BooleanProperty.create("cycle");

    protected Supplier<Item> chalk;
    protected Supplier<Integer> color;
    protected Boolean cycle;

    public RainbowGlyphBlock(Properties properties, Boolean cycle, Supplier<Item> chalk) {
        super(properties, Occultism.CLIENT_CONFIG.visuals.whiteChalkGlyphColor, chalk);
        this.chalk = chalk;
        this.cycle = cycle;
        this.registerDefaultState(
                this.stateDefinition
                        .any()
                        .setValue(CYCLE, cycle)
        );
    }

    public int getColor(BlockState state) {
        return EnumUtil.getConfiguredColor(state.getValue(COLOR));
    }

    public Item getChalk() {
        return this.chalk.get();
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockPos pos = context.getClickedPos();
        int sign = context.getLevel().getRandom().nextInt(MAX_SIGN + 1);
        int cor = RandomSource.create().nextIntBetweenInclusive(4, 15);
        boolean cc = this.cycle;
        BlockState current = context.getLevel().getBlockState(pos);
        if (current.getBlock() == this) {
            sign = (current.getValue(SIGN) + 1) % (MAX_SIGN + 1);
            cor = current.getValue(COLOR);
            cc = current.getValue(CYCLE);
        }
        Player player = context.getPlayer();
        if (player != null) {
            ItemStack stack = context.getHand().equals(InteractionHand.MAIN_HAND) ?
                    player.getItemInHand(InteractionHand.OFF_HAND) :
                    player.getItemInHand(InteractionHand.MAIN_HAND);
            if (stack.is(Items.DYES_BROWN)) {
                cor = 4;
                cc = false;
            } else if (stack.is(Items.DYES_RED)) {
                cor = 5;
                cc = false;
            } else if (stack.is(Items.DYES_ORANGE)) {
                cor = 6;
                cc = false;
            } else if (stack.is(Items.DYES_YELLOW)) {
                cor = 7;
                cc = false;
            } else if (stack.is(Items.DYES_LIME)) {
                cor = 8;
                cc = false;
            } else if (stack.is(Items.DYES_GREEN)) {
                cor = 9;
                cc = false;
            } else if (stack.is(Items.DYES_CYAN)) {
                cor = 10;
                cc = false;
            } else if (stack.is(Items.DYES_LIGHT_BLUE)) {
                cor = 11;
                cc = false;
            } else if (stack.is(Items.DYES_BLUE)) {
                cor = 12;
                cc = false;
            } else if (stack.is(Items.DYES_PURPLE)) {
                cor = 13;
                cc = false;
            } else if (stack.is(Items.DYES_MAGENTA)) {
                cor = 14;
                cc = false;
            } else if (stack.is(Items.DYES_PINK)) {
                cor = 15;
                cc = false;
            } else if (stack.is(OccultismItems.SPIRIT_ATTUNED_GEM)) {
                cc = false;
            }
        }
        return this.defaultBlockState().setValue(COLOR, cor).setValue(CYCLE, cc).setValue(SIGN, sign)
                .setValue(BlockStateProperties.HORIZONTAL_FACING,
                        context.getHorizontalDirection().getOpposite());
    }

    protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
        builder.add(COLOR, CYCLE);
        super.createBlockStateDefinition(builder);
    }

    @Override
    protected @NotNull InteractionResult useItemOn(
            @NotNull ItemStack stack, @NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos,
            Player player, @NotNull InteractionHand hand, @NotNull BlockHitResult hitResult) {
        if (player.getAbilities().mayBuild) {
            if (stack.getItem().equals(OccultismItems.SPIRIT_ATTUNED_GEM.get())) {
                if (state.getValue(CYCLE)) {
                    level.setBlockAndUpdate(pos, state.setValue(CYCLE, false));
                } else {
                    level.setBlockAndUpdate(pos, state.setValue(CYCLE, true));
                }
                return InteractionResult.SUCCESS;
            } else if (stack.is(Items.DYES_BROWN)) {
                level.setBlockAndUpdate(pos, state.setValue(COLOR, 4));
                return InteractionResult.SUCCESS;
            } else if (stack.is(Items.DYES_RED)) {
                level.setBlockAndUpdate(pos, state.setValue(COLOR, 5));
                return InteractionResult.SUCCESS;
            } else if (stack.is(Items.DYES_ORANGE)) {
                level.setBlockAndUpdate(pos, state.setValue(COLOR, 6));
                return InteractionResult.SUCCESS;
            } else if (stack.is(Items.DYES_YELLOW)) {
                level.setBlockAndUpdate(pos, state.setValue(COLOR, 7));
                return InteractionResult.SUCCESS;
            } else if (stack.is(Items.DYES_LIME)) {
                level.setBlockAndUpdate(pos, state.setValue(COLOR, 8));
                return InteractionResult.SUCCESS;
            } else if (stack.is(Items.DYES_GREEN)) {
                level.setBlockAndUpdate(pos, state.setValue(COLOR, 9));
                return InteractionResult.SUCCESS;
            } else if (stack.is(Items.DYES_CYAN)) {
                level.setBlockAndUpdate(pos, state.setValue(COLOR, 10));
                return InteractionResult.SUCCESS;
            } else if (stack.is(Items.DYES_LIGHT_BLUE)) {
                level.setBlockAndUpdate(pos, state.setValue(COLOR, 11));
                return InteractionResult.SUCCESS;
            } else if (stack.is(Items.DYES_BLUE)) {
                level.setBlockAndUpdate(pos, state.setValue(COLOR, 12));
                return InteractionResult.SUCCESS;
            } else if (stack.is(Items.DYES_PURPLE)) {
                level.setBlockAndUpdate(pos, state.setValue(COLOR, 13));
                return InteractionResult.SUCCESS;
            } else if (stack.is(Items.DYES_MAGENTA)) {
                level.setBlockAndUpdate(pos, state.setValue(COLOR, 14));
                return InteractionResult.SUCCESS;
            } else if (stack.is(Items.DYES_PINK)) {
                level.setBlockAndUpdate(pos, state.setValue(COLOR, 15));
                return InteractionResult.SUCCESS;
            }
        }
        return super.useItemOn(stack, state, level, pos, player, hand, hitResult);
    }

    public void animateTick(BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull RandomSource rand) {
        if (state.getValue(CYCLE)) {
            int nextColor = state.getValue(COLOR) == 15 ? 4 : state.getValue(COLOR) + 1;
            level.setBlockAndUpdate(pos, state.setValue(COLOR, nextColor));
        }
    }

}
