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

package com.github.klikli_dev.occultism.common.block.crops;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.block.CropsBlock;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.util.IItemProvider;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.BlockHitResult;

import java.util.function.Supplier;

public class ReplantableCropsBlock extends CropsBlock implements IReplantableCrops {
    //region Fields
    protected Supplier<Item> seed;
    protected Supplier<Item> crops;
    //endregion Fields

    //region Initialization
    public ReplantableCropsBlock(Properties builder, Supplier<Item> seed, Supplier<Item> crops) {
        super(builder);
        this.seed = seed;
        this.crops = crops;
    }
    //endregion Initialization

    //region Getter / Setter
    public IItemProvider getCropsItem() {
        return this.crops.get();
    }
    //endregion Getter / Setter

    //region Overrides
    @Override
    public IItemProvider getSeedsItem() {
        return this.seed.get();
    }

    @Override
    public InteractionResult use(BlockState state, Level worldIn, BlockPos pos, Player player,
                                             InteractionHand handIn, BlockHitResult hit) {
        return IReplantableCrops.super.onHarvest(state, worldIn, pos, player, handIn);
    }

    //endregion Overrides
}
