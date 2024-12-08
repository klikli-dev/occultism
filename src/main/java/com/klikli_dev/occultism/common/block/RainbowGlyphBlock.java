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
import com.klikli_dev.occultism.registry.OccultismBlocks;
import com.klikli_dev.occultism.registry.OccultismItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.neoforge.common.Tags;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

public class RainbowGlyphBlock extends Block {
    /**
     * The glyph sign (the typeface)
     */
    public static final IntegerProperty SIGN = IntegerProperty.create("sign", 0, 12);
    public static final IntegerProperty COLOR = IntegerProperty.create("color", 0, 15);
    public static final BooleanProperty CYCLE = BooleanProperty.create("cycle");
    public static final int MAX_SIGN = 12;

    private static final VoxelShape SHAPE = Block.box(0, 0, 0, 15, 0.04, 15);

    protected Supplier<Item> chalk;
    protected Supplier<Integer> color;
    protected Boolean cycle;

    public RainbowGlyphBlock(Properties properties, Boolean cycle, Supplier<Item> chalk) {
        super(properties);
        this.chalk = chalk;
        this.cycle = cycle;
        this.registerDefaultState(
                this.stateDefinition
                        .any()
                        .setValue(CYCLE, cycle)
        );
    }

    public int getColor(BlockState state) {
        return switch (state.getValue(COLOR)) {
            case 1 -> Occultism.CLIENT_CONFIG.visuals.lightGrayChalkGlyphColor.get();
            case 2 -> Occultism.CLIENT_CONFIG.visuals.grayChalkGlyphColor.get();
            case 3 -> Occultism.CLIENT_CONFIG.visuals.blackChalkGlyphColor.get();
            case 4 -> Occultism.CLIENT_CONFIG.visuals.redChalkGlyphColor.get();
            case 5 -> Occultism.CLIENT_CONFIG.visuals.brownChalkGlyphColor.get();
            case 6 -> Occultism.CLIENT_CONFIG.visuals.orangeChalkGlyphColor.get();
            case 7 -> Occultism.CLIENT_CONFIG.visuals.yellowChalkGlyphColor.get();
            case 8 -> Occultism.CLIENT_CONFIG.visuals.limeChalkGlyphColor.get();
            case 9 -> Occultism.CLIENT_CONFIG.visuals.greenChalkGlyphColor.get();
            case 10 -> Occultism.CLIENT_CONFIG.visuals.cyanChalkGlyphColor.get();
            case 11 -> Occultism.CLIENT_CONFIG.visuals.blueChalkGlyphColor.get();
            case 12 -> Occultism.CLIENT_CONFIG.visuals.lightBlueChalkGlyphColor.get();
            case 13 -> Occultism.CLIENT_CONFIG.visuals.pinkChalkGlyphColor.get();
            case 14 -> Occultism.CLIENT_CONFIG.visuals.magentaChalkGlyphColor.get();
            case 15 -> Occultism.CLIENT_CONFIG.visuals.purpleChalkGlyphColor.get();
            default -> Occultism.CLIENT_CONFIG.visuals.whiteChalkGlyphColor.get();
        };
    }

    public Item getChalk() {
        return this.chalk.get();
    }

    @Override
    @SuppressWarnings("deprecation")
    public void neighborChanged(BlockState state, Level worldIn, BlockPos pos, Block blockIn, BlockPos fromPos,
                                boolean isMoving) {
        if (!this.canSurvive(state, worldIn, pos)) {
            worldIn.removeBlock(pos, false);
        }
    }

    @Override
    @SuppressWarnings("deprecation")
    public boolean canBeReplaced(BlockState state, BlockPlaceContext context) {
        return true;
    }

    @Override
    @SuppressWarnings("deprecation")
    public boolean canBeReplaced(BlockState state, Fluid fluid) {
        return true;
    }

    @Override
    @SuppressWarnings("deprecation")
    public boolean canSurvive(BlockState state, LevelReader worldIn, BlockPos pos) {
        BlockPos down = pos.below();
        BlockState downState = worldIn.getBlockState(down);
        return downState.isFaceSturdy(worldIn, down, Direction.UP) && state.canBeReplaced();
    }

    @Override
    @SuppressWarnings("deprecation")
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    @SuppressWarnings("deprecation")
    public VoxelShape getCollisionShape(BlockState state, BlockGetter worldIn, BlockPos pos,
                                        CollisionContext context) {
        return Shapes.empty();
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockPos pos = context.getClickedPos();
        int sign = context.getLevel().getRandom().nextInt(MAX_SIGN + 1);
        int cor = 0;
        boolean cc = this.cycle;
        BlockState current = context.getLevel().getBlockState(pos);
        if (this.getChalk().asItem().equals(OccultismItems.CHALK_RAINBOW.get())){
            cor = 4;
        }
        if (current.getBlock() == this) {
            sign = (current.getValue(SIGN) + 1) % (MAX_SIGN + 1);
            cor = current.getValue(COLOR);
            cc = current.getValue(CYCLE);
        }
        return this.defaultBlockState().setValue(COLOR, cor).setValue(CYCLE, cc).setValue(SIGN, sign)
                .setValue(BlockStateProperties.HORIZONTAL_FACING,
                        context.getHorizontalDirection().getOpposite());
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(COLOR, CYCLE, SIGN, BlockStateProperties.HORIZONTAL_FACING);
        super.createBlockStateDefinition(builder);
    }

    @Override
    public ItemStack getCloneItemStack(LevelReader pLevel, BlockPos pPos, BlockState pState) {
        if (BuiltInRegistries.ITEM.containsValue(this.getChalk()))//fix for startup crash related to patchouli getting pick block too early
            return new ItemStack(this.getChalk());
        return ItemStack.EMPTY;
    }

    @Override
    public @Nullable PathType getBlockPathType(BlockState state, BlockGetter level, BlockPos pos, @Nullable Mob mob) {
        return PathType.OPEN;
    }
    @Override
    protected ItemInteractionResult useItemOn(
            ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult
    ) {
        if (player.getAbilities().mayBuild) {
            if (stack.getItem().equals(OccultismItems.SPIRIT_ATTUNED_GEM.get())) {
                if (state.getValue(CYCLE)) {
                    level.setBlockAndUpdate(pos, state.setValue(CYCLE, false));
                } else {
                    level.setBlockAndUpdate(pos, state.setValue(CYCLE, true));
                }
                return ItemInteractionResult.sidedSuccess(level.isClientSide);
            } else if (stack.getTags().toList().contains(Tags.Items.DYES_RED)) {
                level.setBlockAndUpdate(pos, state.setValue(COLOR, 4));
                return ItemInteractionResult.sidedSuccess(level.isClientSide);
            } else if (stack.getTags().toList().contains(Tags.Items.DYES_BROWN)) {
                level.setBlockAndUpdate(pos, state.setValue(COLOR, 5));
                return ItemInteractionResult.sidedSuccess(level.isClientSide);
            } else if (stack.getTags().toList().contains(Tags.Items.DYES_ORANGE)) {
                level.setBlockAndUpdate(pos, state.setValue(COLOR, 6));
                return ItemInteractionResult.sidedSuccess(level.isClientSide);
            } else if (stack.getTags().toList().contains(Tags.Items.DYES_YELLOW)) {
                level.setBlockAndUpdate(pos, state.setValue(COLOR, 7));
                return ItemInteractionResult.sidedSuccess(level.isClientSide);
            } else if (stack.getTags().toList().contains(Tags.Items.DYES_LIME)) {
                level.setBlockAndUpdate(pos, state.setValue(COLOR, 8));
                return ItemInteractionResult.sidedSuccess(level.isClientSide);
            } else if (stack.getTags().toList().contains(Tags.Items.DYES_GREEN)) {
                level.setBlockAndUpdate(pos, state.setValue(COLOR, 9));
                return ItemInteractionResult.sidedSuccess(level.isClientSide);
            } else if (stack.getTags().toList().contains(Tags.Items.DYES_CYAN)) {
                level.setBlockAndUpdate(pos, state.setValue(COLOR, 10));
                return ItemInteractionResult.sidedSuccess(level.isClientSide);
            } else if (stack.getTags().toList().contains(Tags.Items.DYES_BLUE)) {
                level.setBlockAndUpdate(pos, state.setValue(COLOR, 11));
                return ItemInteractionResult.sidedSuccess(level.isClientSide);
            } else if (stack.getTags().toList().contains(Tags.Items.DYES_LIGHT_BLUE)) {
                level.setBlockAndUpdate(pos, state.setValue(COLOR, 12));
                return ItemInteractionResult.sidedSuccess(level.isClientSide);
            } else if (stack.getTags().toList().contains(Tags.Items.DYES_PINK)) {
                level.setBlockAndUpdate(pos, state.setValue(COLOR, 13));
                return ItemInteractionResult.sidedSuccess(level.isClientSide);
            } else if (stack.getTags().toList().contains(Tags.Items.DYES_MAGENTA)) {
                level.setBlockAndUpdate(pos, state.setValue(COLOR, 14));
                return ItemInteractionResult.sidedSuccess(level.isClientSide);
            } else if (stack.getTags().toList().contains(Tags.Items.DYES_PURPLE)) {
                level.setBlockAndUpdate(pos, state.setValue(COLOR, 15));
                return ItemInteractionResult.sidedSuccess(level.isClientSide);
            } else if (state.getBlock().equals(OccultismBlocks.CHALK_GLYPH_VOID.get())) {
                if (stack.getTags().toList().contains(Tags.Items.DYES_WHITE)) {
                    level.setBlockAndUpdate(pos, state.setValue(COLOR, 0));
                    return ItemInteractionResult.sidedSuccess(level.isClientSide);
                } else if (stack.getTags().toList().contains(Tags.Items.DYES_LIGHT_GRAY)) {
                    level.setBlockAndUpdate(pos, state.setValue(COLOR, 1));
                    return ItemInteractionResult.sidedSuccess(level.isClientSide);
                } else if (stack.getTags().toList().contains(Tags.Items.DYES_GRAY)) {
                    level.setBlockAndUpdate(pos, state.setValue(COLOR, 2));
                    return ItemInteractionResult.sidedSuccess(level.isClientSide);
                } else if (stack.getTags().toList().contains(Tags.Items.DYES_BLACK)) {
                    level.setBlockAndUpdate(pos, state.setValue(COLOR, 3));
                    return ItemInteractionResult.sidedSuccess(level.isClientSide);
                }
            }
        }
        return super.useItemOn(stack, state, level, pos, player, hand, hitResult);
    }

    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource rand) {
        if (state.getValue(CYCLE)) {
            Integer nextColor = state.getValue(COLOR) + 1;
            if (nextColor == 16 && state.getBlock().equals(OccultismBlocks.CHALK_GLYPH_VOID.get())){nextColor = 0;} //return to white
            if (nextColor == 16 && state.getBlock().equals(OccultismBlocks.CHALK_GLYPH_RAINBOW.get())){nextColor = 4;} //return to red
            level.setBlockAndUpdate(pos, state.setValue(COLOR, nextColor));
        }
    }

}
