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

package com.github.klikli_dev.occultism.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.player.Player;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.level.IBlockReader;
import net.minecraft.level.IWorldReader;
import net.minecraft.level.Level;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;
import java.util.function.Supplier;

public class ChalkGlyphBlock extends Block {
    //region Fields
    /**
     * The glyph sign (the typeface)
     */
    public static final IntegerProperty SIGN = IntegerProperty.create("sign", 0, 12);

    private static final VoxelShape SHAPE = Block.makeCuboidShape(0, 0, 0, 15, 0.04, 15);

    protected Supplier<Item> chalk;
    protected int color;
    //endregion Fields

    //region Initialization
    public ChalkGlyphBlock(Properties properties, int color, Supplier<Item>chalk) {
        super(properties);
        this.color = color;
        this.chalk = chalk;
    }
    //endregion Initialization

    //region Getter / Setter
    public int getColor() {
        return this.color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public Item getChalk() {
        return this.chalk.get();
    }

    //endregion Getter / Setter

    //region Overrides

    @Override
    public boolean isReplaceable(BlockState state, BlockItemUseContext useContext) {
        return true;
    }

    @Override
    public boolean isReplaceable(BlockState state, Fluid fluid) {
        return true;
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return SHAPE;
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, IBlockReader worldIn, BlockPos pos,
                                        ISelectionContext context) {
        return VoxelShapes.empty();
    }

    @Override
    public void neighborChanged(BlockState state, Level worldIn, BlockPos pos, Block blockIn, BlockPos fromPos,
                                boolean isMoving) {
        if (!this.isValidPosition(state, worldIn, pos)) {
            worldIn.removeBlock(pos, false);
        }
    }

    @Override
    public boolean isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos) {
        BlockPos down = pos.down();
        BlockState downState = worldIn.getBlockState(down);
        return downState.isSolidSide(worldIn, down, Direction.UP) && state.getMaterial().isReplaceable();
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        BlockPos pos = context.getPos();
        int sign = Math.abs(pos.getX() + pos.getZ() * 2) % 13;
        return this.getDefaultState().with(SIGN, sign)
                       .with(BlockStateProperties.HORIZONTAL_FACING,
                               context.getPlacementHorizontalFacing().getOpposite());
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(SIGN, BlockStateProperties.HORIZONTAL_FACING);
        super.fillStateContainer(builder);
    }

    @Override
    public ItemStack getPickBlock(BlockState state, RayTraceResult target, IBlockReader level, BlockPos pos,
                                  Player player) {
        if (ForgeRegistries.ITEMS.containsKey(
                this.getChalk().getRegistryName()))//fix for startup crash related to patchouli getting pick block too early
            return new ItemStack(this.getChalk());
        return ItemStack.EMPTY;
    }

    @Nullable
    @Override
    public PathNodeType getAiPathNodeType(BlockState state, IBlockReader level, BlockPos pos,
                                          @Nullable MobEntity entity) {
        return PathNodeType.OPEN;
    }
    //endregion Overrides
}
