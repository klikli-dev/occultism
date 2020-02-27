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

import net.minecraft.block.CropsBlock;
import net.minecraft.block.BlockState;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeEventFactory;

public interface IReplantable {

    //region Methods
    default void onHarvest(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        if (state.getValue(CropsBlock.AGE) >= 7) {
            NonNullList<ItemStack> drops = NonNullList.create();
            state.getBlock().getDrops(drops, world, pos, state, 0);
            ForgeEventFactory.fireBlockHarvesting(drops, world, pos, state, 0, 1.0F, false, player);

            if (!world.isRemote) {
                //reset crop
                world.setBlockState(pos, state.withProperty(CropsBlock.AGE, 0));
                for (ItemStack stack : drops) {
                    ItemEntity entityItem = new ItemEntity(world, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5,
                            stack);
                    entityItem.setPickupDelay(10);
                    world.spawnEntity(entityItem);
                }
            }
        }
    }
    //endregion Methods
}
