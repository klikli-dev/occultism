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
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.state.StateContainer;
import net.minecraft.core.BlockPos;

import javax.annotation.Nullable;

public class IesniumOreNaturalBlock extends Block implements IOtherworldBlock {
    //region Initialization
    public IesniumOreNaturalBlock(Properties properties) {
        super(properties);
        this.setDefaultState(this.getStateContainer().getBaseState().with(UNCOVERED, false));
    }
    //endregion Initialization

    //region Overrides
    @Override
    public Block getUncoveredBlock() {
        return OccultismBlocks.IESNIUM_ORE.get();
    }

    @Override
    public Block getCoveredBlock() {
        return Blocks.NETHERRACK;
    }

    @Override
    public OtherworldBlockTier getTier() {
        return OtherworldBlockTier.TWO;
    }

    @Override
    public void harvestBlock(Level worldIn, Player player, BlockPos pos, BlockState state,
                             @Nullable BlockEntity te, ItemStack stack) {
        super.harvestBlock(worldIn, player, pos, IOtherworldBlock.super.getHarvestState(player, state, stack), te,
                stack);
    }

    @Override
    public String getDescriptionId() {
        return "block.minecraft.netherrack";
    }

    @Override
    public ItemStack getItem(BlockGetter worldIn, BlockPos pos, BlockState state) {
        return IOtherworldBlock.super.getItem(worldIn, pos, state);
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(UNCOVERED);
        super.fillStateContainer(builder);
    }
    //endregion Overrides
}
