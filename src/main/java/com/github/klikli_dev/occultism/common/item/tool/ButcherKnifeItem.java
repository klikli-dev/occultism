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

package com.github.klikli_dev.occultism.common.item.tool;

import com.github.klikli_dev.occultism.api.OccultismAPI;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.item.IItemTier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.item.SwordItem;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class ButcherKnifeItem extends SwordItem {
    //region Initialization

    public ButcherKnifeItem(IItemTier tier, int attackDamageIn, float attackSpeedIn, Properties builder) {
        super(tier, attackDamageIn, attackSpeedIn, builder);
    }
    //endregion Initialization

    //region Static Methods
    public static List<ItemStack> getLoot(LivingEntity entity, ItemStack knife, LivingEntity trueDamageSource) {
        List<ItemStack> loot = new ArrayList<>();
        for (Predicate<LivingEntity> predicate : OccultismAPI.BUTCHER_KNIFE_LOOT.keySet())
            if (predicate.test(entity))
                loot.addAll(OccultismAPI.BUTCHER_KNIFE_LOOT.get(predicate).getLoot(entity, knife, trueDamageSource));
        return loot;
    }
    //endregion Static Methods

    //region Methods

    //endregion Methods
}
