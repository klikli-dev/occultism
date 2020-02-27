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

import com.github.klikli_dev.occultism.api.common.block.IOtherOre;
import com.github.klikli_dev.occultism.registry.BlockRegistry;
import net.minecraft.block.*;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.LogBlock;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.Random;

public class BlockOtherworldLogNatural extends LogBlock implements IOtherOre {

    //region Initialization

    public BlockOtherworldLogNatural() {
        BlockRegistry.registerBlock(this, "otherworld_log_natural", Material.WOOD, SoundType.WOOD, 2.0f, 0);
        this.setTranslationKey("log.oak");
        this.setDefaultState(this.getBlockState().getBaseState().withProperty(LOG_AXIS, LogBlock.EnumAxis.Y)
                                     .withProperty(UNCOVERED, false));
    }

    //endregion Initialization

    //region Overrides
    @Override
    public Block getUncoveredBlock() {
        return BlockRegistry.OTHERWORLD_LOG;
    }

    @Override
    public Block getCoveredBlock() {
        return Blocks.LOG;
    }

    @Override
    public BlockState getStateFromMeta(int meta) {
        BlockState iblockstate = this.getDefaultState();
        switch (meta & 12) {
            case 0:
                iblockstate = iblockstate.withProperty(LOG_AXIS, EnumAxis.Y);
                break;
            case 4:
                iblockstate = iblockstate.withProperty(LOG_AXIS, EnumAxis.X);
                break;
            case 8:
                iblockstate = iblockstate.withProperty(LOG_AXIS, EnumAxis.Z);
                break;
            default:
                iblockstate = iblockstate.withProperty(LOG_AXIS, EnumAxis.NONE);
        }

        return iblockstate;
    }

    @Override
    public int getMetaFromState(BlockState state) {
        int i = 0;
        switch (state.getValue(LOG_AXIS)) {
            case X:
                i |= 4;
                break;
            case Z:
                i |= 8;
                break;
            case NONE:
                i |= 12;
        }

        return i;
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, LogBlock.LOG_AXIS, UNCOVERED);
    }

    @Override
    public ItemStack getSilkTouchDrop(BlockState state) {
        return IOtherOre.super.getSilkTouchDrop(state);
    }

    @Override
    public MaterialColor getMapColor(BlockState state, IBlockAccess worldIn, BlockPos pos) {
        return MaterialColor.BROWN;
    }

    @Override
    public BlockState getActualState(BlockState state, IBlockAccess worldIn, BlockPos pos) {
        return IOtherOre.super.getActualState(state, worldIn, pos);
    }

    @Override
    public Item getItemDropped(BlockState state, Random rand, int fortune) {
        return IOtherOre.super.getItemDropped(state, rand, fortune);
    }

    @Override
    public int damageDropped(BlockState state) {
        return state.getValue(UNCOVERED) ? 0 : BlockPlanks.EnumType.OAK.getMetadata();
    }

    @Override
    public void harvestBlock(World worldIn, PlayerEntity player, BlockPos pos, BlockState state,
                             @Nullable TileEntity te, ItemStack stack) {
        super.harvestBlock(worldIn, player, pos, IOtherOre.super.getHarvestState(player, state), te, stack);
    }

    @Override
    public ItemStack getItem(World worldIn, BlockPos pos, BlockState state) {
        return IOtherOre.super.getItem(worldIn, pos, state);
    }
    //endregion Overrides
}