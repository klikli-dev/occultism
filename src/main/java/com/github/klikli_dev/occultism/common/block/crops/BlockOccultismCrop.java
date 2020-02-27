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

import com.github.klikli_dev.occultism.registry.BlockRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

public class BlockOccultismCrop extends BlockCrops implements IReplantable {
    //region Fields
    private Item seed;
    private Item crop;
    //endregion Fields

    //region Initialization
    public BlockOccultismCrop(String name) {
        super();
        BlockRegistry.registerBlock(this, name, Material.PLANTS, SoundType.PLANT, 0, 0);
    }
    //endregion Initialization

    //region Overrides
    @Override
    @Nonnull
    public Item getSeed() {
        return this.seed;
    }

    @Override
    @Nonnull
    public Item getCrop() {
        return this.crop;
    }

    public void setCrop(Item crop) {
        this.crop = crop;
    }

    public void setSeed(Item seed) {
        this.seed = seed;
    }

    @Override
    public void neighborChanged(IBlockState state, World world, BlockPos to, Block block, BlockPos from) {
        if (!this.canBlockStay(world, to, state)) {
            this.dropBlockAsItem(world, to, state, 0);
            world.removeTileEntity(to);
            world.setBlockToAir(to);
        }
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand,
                                    EnumFacing facing, float hitX, float hitY, float hitZ) {
        IReplantable.super.onHarvest(world, pos, state, player);
        return super.onBlockActivated(world, pos, state, player, hand, facing, hitX, hitY, hitZ);
    }

    //endregion Overrides
}
