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

package com.klikli_dev.occultism.common.item.spirit;

import com.klikli_dev.occultism.common.entity.job.ManageMachineJob;
import net.minecraft.world.item.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class BookOfCallingManageMachineItem extends BookOfCallingItem {

    public BookOfCallingManageMachineItem(Properties properties, String translationKeyBase) {
        super(properties, translationKeyBase, spirit -> spirit.getJob().orElse(null) instanceof ManageMachineJob);
    }

    @Override
    public IItemModeSubset<?> getItemModeSubset(ItemStack stack) {
        ItemModeSubset subset = ItemModeSubset.get(ItemMode.get(this.getItemMode(stack)));
        return subset != null ? subset : ItemModeSubset.SET_STORAGE_CONTROLLER;
    }

    public enum ItemModeSubset implements IItemModeSubset<ItemModeSubset> {
        SET_MANAGED_MACHINE(ItemMode.SET_MANAGED_MACHINE),
        SET_EXTRACT(ItemMode.SET_EXTRACT),
        SET_STORAGE_CONTROLLER(ItemMode.SET_STORAGE_CONTROLLER);

        private static final Map<ItemMode, ItemModeSubset> lookup = new HashMap<>();

        static {
            for (ItemModeSubset subset : ItemModeSubset.values()) {
                lookup.put(subset.getItemMode(), subset);
            }
        }

        private final ItemMode itemMode;

        ItemModeSubset(ItemMode itemMode) {
            this.itemMode = itemMode;
        }

        //region Static Methods
        public static ItemModeSubset get(ItemMode value) {
            return lookup.get(value);
        }

        @Override
        public ItemMode getItemMode() {
            return this.itemMode;
        }

        @Override
        public ItemModeSubset next() {
            return values()[(this.ordinal() + 1) % values().length];
        }
        //endregion Static Methods
    }
}
