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

package com.github.klikli_dev.occultism.api.common.block;

import com.github.klikli_dev.occultism.Occultism;
import com.github.klikli_dev.occultism.handler.ClientEventHandler;
import com.github.klikli_dev.occultism.registry.PotionRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;

import java.util.Random;

public interface IOtherOre {
    //region Fields
    IProperty<Boolean> UNCOVERED = PropertyBool.create("uncovered");
    //endregion Fields

    //region Getter / Setter
    Block getUncoveredBlock();

    Block getCoveredBlock();

    //endregion Getter / Setter

    //region Methods
    int damageDropped(BlockState state);

    default BlockState getActualState(BlockState state, IBlockAccess worldIn, BlockPos pos) {
        if (FMLCommonHandler.instance().getEffectiveSide().isClient()) {
            ClientEventHandler clientEventHandler = Occultism.proxy.getClientEventHandler();
            if (clientEventHandler.shouldRenderThirdEye(state, pos)) {
                clientEventHandler.thirdEyeBlocks.add(pos);
                return state.withProperty(UNCOVERED, true);
            }
        }
        return state.withProperty(UNCOVERED, false);
    }

    default BlockState getHarvestState(PlayerEntity player, BlockState state) {
        return player.isPotionActive(PotionRegistry.THIRD_EYE) ? state.withProperty(UNCOVERED, true) : state;
    }

    default ItemStack getItem(World worldIn, BlockPos pos, BlockState state) {
        Item item = state.getValue(UNCOVERED) ? Item.getItemFromBlock(this.getUncoveredBlock()) : Item.getItemFromBlock(
                this.getCoveredBlock());
        return new ItemStack(item, 1, this.damageDropped(state));
    }

    default Item getItemDropped(BlockState state, Random rand, int fortune) {
        return state.getValue(UNCOVERED) ? Item.getItemFromBlock(this.getUncoveredBlock()) : Item.getItemFromBlock(
                this.getCoveredBlock());
    }

    default ItemStack getSilkTouchDrop(BlockState state) {
        return state.getValue(UNCOVERED) ? new ItemStack(this.getUncoveredBlock(), 1) : new ItemStack(
                this.getCoveredBlock(), 1);
    }

    //endregion Methods
}
