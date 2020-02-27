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

package com.github.klikli_dev.occultism.common.item;

import com.github.klikli_dev.occultism.registry.ItemRegistry;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemBlockOtherworldSapling extends ItemBlock {
    //region Fields
    protected String translationKey;
    //endregion Fields

    //region Initialization
    public ItemBlockOtherworldSapling(Block block) {
        super(block);
        ItemRegistry.registerItem(this, block.getRegistryName().getPath(), "treeSapling");
    }
    //endregion Initialization

    //region Overrides
    @Override
    public Item setTranslationKey(String key) {
        this.translationKey = key;
        return super.setTranslationKey(key);
    }

    @Override
    public int getItemBurnTime(ItemStack itemStack) {
        return 100;
    }

    @Override
    public String getTranslationKey(ItemStack stack) {
        return "item." + this.translationKey;
    }

    @Override
    public String getTranslationKey() {
        return "item." + this.translationKey;
    }
    //endregion Overrides
}
