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

package com.github.klikli_dev.occultism.common.item.spirit;

import com.github.klikli_dev.occultism.common.job.CleanerJob;
import com.github.klikli_dev.occultism.common.job.TransportItemsJob;
import net.minecraft.item.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class BookOfCallingCleanerItem extends BookOfCallingItem {
    //region Initialization
    public BookOfCallingCleanerItem(Properties properties, String translationKeyBase) {
        super(properties, translationKeyBase, spirit -> spirit.getJob().orElse(null) instanceof CleanerJob);
    }
    //endregion Initialization

    //region Overrides
    @Override
    public IItemModeSubset<?> getItemModeSubset(ItemStack stack) {
        ItemModeSubset subset = ItemModeSubset.get(ItemMode.get(this.getItemMode(stack)));
        return subset != null ? subset : ItemModeSubset.SET_BASE;
    }
    //endregion Overrides

    public enum ItemModeSubset implements IItemModeSubset<ItemModeSubset> {
        SET_BASE(ItemMode.SET_BASE),
        SET_DEPOSIT(ItemMode.SET_DEPOSIT);

        //region Fields
        private static final Map<ItemMode, ItemModeSubset> lookup = new HashMap<>();

        static {
            for (ItemModeSubset subset : ItemModeSubset.values()) {
                lookup.put(subset.getItemMode(), subset);
            }
        }

        private ItemMode itemMode;
        //endregion Fields

        //region Initialization
        ItemModeSubset(ItemMode itemMode) {
            this.itemMode = itemMode;
        }
        //endregion Initialization

        //region Overrides
        @Override
        public ItemMode getItemMode() {
            return this.itemMode;
        }

        @Override
        public ItemModeSubset next() {
            return values()[(this.ordinal() + 1) % values().length];
        }
        //endregion Overrides

        //region Static Methods
        public static ItemModeSubset get(ItemMode value) {
            return lookup.get(value);
        }
        //endregion Static Methods
    }
}
