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

package com.github.klikli_dev.occultism.common.block.otherworld;

import com.github.klikli_dev.occultism.api.common.data.OtherworldBlockTier;
import com.github.klikli_dev.occultism.registry.OccultismBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.RotatedPillarBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class OtherworldLogNaturalBlock extends RotatedPillarBlock implements IOtherworldBlock {

    //region Initialization
    public OtherworldLogNaturalBlock(Properties properties) {
        super(properties);
        this.setDefaultState(this.getDefaultState().with(UNCOVERED, false));
    }
    //endregion Initialization

    //region Overrides
    @Override
    public Block getUncoveredBlock() {
        return OccultismBlocks.OTHERWORLD_LOG.get();
    }

    @Override
    public Block getCoveredBlock() {
        return Blocks.OAK_LOG;
    }

    @Override
    public OtherworldBlockTier getTier() {
        return OtherworldBlockTier.ONE;
    }


    @Override
    public void harvestBlock(World worldIn, PlayerEntity player, BlockPos pos, BlockState state,
                             @Nullable TileEntity te, ItemStack stack) {
        super.harvestBlock(worldIn, player, pos, IOtherworldBlock.super.getHarvestState(player, state, stack), te,
                stack);
    }

    @Override
    public String getTranslationKey() {
        return "block.minecraft.oak_log";
    }

    @Override
    public ItemStack getItem(IBlockReader worldIn, BlockPos pos, BlockState state) {
        return IOtherworldBlock.super.getItem(worldIn, pos, state);
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(UNCOVERED);
        super.fillStateContainer(builder);
    }
    //endregion Overrides
}
