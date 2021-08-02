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
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SaplingBlock;
import net.minecraft.block.trees.Tree;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.core.BlockPos;

import javax.annotation.Nullable;

public class OtherworldSaplingNaturalBlock extends SaplingBlock implements IOtherworldBlock {

    //region Initialization
    public OtherworldSaplingNaturalBlock(Tree tree, Properties properties) {
        super(tree, properties);
        this.createBlockStateDefinition(this.getStateContainer().getBaseState().with(UNCOVERED, false));
    }
    //endregion Initialization

    //region Overrides
    @Override
    public Block getUncoveredBlock() {
        return this;
    }

    @Override
    public Block getCoveredBlock() {
        return Blocks.OAK_SAPLING;
    }

    @Override
    public OtherworldBlockTier getTier() {
        return OtherworldBlockTier.ONE;
    }

    @Override
    public void playerDestroy(Level worldIn, Player player, BlockPos pos, BlockState state,
                             @Nullable BlockEntity te, ItemStack stack) {
        super.playerDestroy(worldIn, player, pos, IOtherworldBlock.super.getHarvestState(player, state, stack), te,
                stack);
    }

    @Override
    public String getDescriptionId() {
        return "block.minecraft.oak_sapling";
    }

    @Override
    public ItemStack getCloneItemStack(BlockGetter worldIn, BlockPos pos, BlockState state) {
        return IOtherworldBlock.super.getItem(worldIn, pos, state);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(UNCOVERED);
        super.createBlockStateDefinition(builder);
    }
    //endregion Overrides

}
