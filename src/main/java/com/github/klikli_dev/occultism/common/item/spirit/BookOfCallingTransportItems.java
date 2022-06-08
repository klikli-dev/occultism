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

import com.github.klikli_dev.occultism.common.job.TransportItemsJob;
import com.github.klikli_dev.occultism.util.ItemNBTUtil;
import com.github.klikli_dev.occultism.util.TextUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BookOfCallingTransportItems extends BookOfCallingItem {
    //region Initialization
    public BookOfCallingTransportItems(Properties properties, String translationKeyBase) {
        super(properties, translationKeyBase, spirit -> spirit.getJob().orElse(null) instanceof TransportItemsJob);
    }
    //endregion Initialization

    //region Overrides
    @Override
    public IItemModeSubset<?> getItemModeSubset(ItemStack stack) {
        ItemModeSubset subset = ItemModeSubset.get(ItemMode.get(this.getItemMode(stack)));
        return subset != null ? subset : ItemModeSubset.SET_DEPOSIT;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip,
                                TooltipFlag flagIn) {
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
        BlockPos extract = ItemNBTUtil.getExtractPosition(stack);
        if (extract != null) {
            tooltip.add(Component.translatable(this.getTranslationKeyBase() + ".tooltip.extract", extract.toString()));
        }

        BlockPos deposit = ItemNBTUtil.getDepositPosition(stack);
        String depositName = ItemNBTUtil.getDepositEntityName(stack);

        if (deposit != null) {
            tooltip.add(Component.translatable(this.getTranslationKeyBase() + ".tooltip.deposit", deposit.toString()));
        } else if (depositName != null) {
            tooltip.add(Component.translatable(this.getTranslationKeyBase() + ".tooltip.deposit_entity", TextUtil.formatDemonName(depositName)));
        }
    }
    //endregion Overrides

    public enum ItemModeSubset implements IItemModeSubset<ItemModeSubset> {
        SET_EXTRACT(ItemMode.SET_EXTRACT),
        SET_DEPOSIT(ItemMode.SET_DEPOSIT);

        //region Fields
        private static final Map<ItemMode, ItemModeSubset> lookup = new HashMap<>();

        static {
            for (ItemModeSubset subset : ItemModeSubset.values()) {
                lookup.put(subset.getItemMode(), subset);
            }
        }

        private final ItemMode itemMode;
        //endregion Fields

        //region Initialization
        ItemModeSubset(ItemMode itemMode) {
            this.itemMode = itemMode;
        }
        //endregion Initialization

        //region Static Methods
        public static ItemModeSubset get(ItemMode value) {
            return lookup.get(value);
        }

        //region Overrides
        @Override
        public ItemMode getItemMode() {
            return this.itemMode;
        }
        //endregion Overrides

        @Override
        public ItemModeSubset next() {
            return values()[(this.ordinal() + 1) % values().length];
        }
        //endregion Static Methods
    }
}
