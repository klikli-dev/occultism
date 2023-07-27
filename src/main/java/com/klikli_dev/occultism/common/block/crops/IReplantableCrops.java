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

package com.klikli_dev.occultism.common.block.crops;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;

public interface IReplantableCrops {
    //region Fields
    float EXHAUSTION_PER_HARVEST = 0.005f;
    //endregion Fields

    //region Getter / Setter
    ItemLike getCropsItem();

    ItemLike getSeedsItem();
    //endregion Getter / Setter

    //region Overrides

    //endregion Getter / Setter
    //region Methods
    default InteractionResult onHarvest(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand) {
        if (!level.isClientSide) {
            if (state.getValue(CropBlock.AGE) >= 7) {
                List<ItemStack> drops = Block.getDrops(state, (ServerLevel) level, pos, null, player,
                        player.getItemInHand(hand));

                // From 1.15 -> does not exist any more and I guess we don't need it.
                //                ForgeEventFactory.fireBlockHarvesting(
                //                        NonNullList.from(ItemStack.EMPTY, drops.toArray(new ItemStack[0])), level, pos, state,
                //                        0, 1.0F, false, player);

                //reset crop
                level.setBlockAndUpdate(pos, state.setValue(CropBlock.AGE, 0));
                for (ItemStack stack : drops) {
                    Containers.dropItemStack(level, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, stack);
                }

                player.swing(hand);
                player.causeFoodExhaustion(EXHAUSTION_PER_HARVEST);

                return InteractionResult.SUCCESS;
            }
        }
        return InteractionResult.PASS;
    }
    //endregion Methods
}
