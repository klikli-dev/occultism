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

package com.github.klikli_dev.occultism.integration.hwyla;

import com.github.klikli_dev.occultism.Occultism;
import com.github.klikli_dev.occultism.api.common.block.IOtherOre;
import mcp.mobius.waila.api.*;
import net.minecraft.block.BlockStone;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;

@WailaPlugin(Occultism.MODID)
public class HwylaPlugin implements IWailaPlugin, IWailaDataProvider {

    //region Overrides
    @Override
    public void register(IWailaRegistrar registrar) {
        //registrar.registerStackProvider(this, BlockOtherstoneOre.class);
    }

    @Nonnull
    @Override
    public ItemStack getWailaStack(IWailaDataAccessor accessor, IWailaConfigHandler config) {
        //TODO: currently unused because we use IOtherOre.getItem, but could be used to override icon render and mod id of waila results
        if (accessor.getBlock() instanceof IOtherOre)
            return new ItemStack(Item.getItemFromBlock(Blocks.STONE), 1, BlockStone.EnumType.ANDESITE.getMetadata());
        else
            return accessor.getStack();
    }
    //endregion Overrides
}