/*
 * SPDX-FileCopyrightText: 2026 klikli-dev
 *
 * SPDX-License-Identifier: MIT
 */

package com.klikli_dev.occultism.common.entity.job;

import com.klikli_dev.occultism.common.container.spirit.SpiritTransporterContainer;
import com.klikli_dev.occultism.common.entity.spirit.SpiritEntity;
import com.klikli_dev.occultism.common.item.filter.EntityItemFilter;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

import javax.annotation.Nullable;
import java.util.List;

public abstract class FilterableProcessingSpiritJob extends SpiritJob implements MenuProvider {

    protected FilterableProcessingSpiritJob(SpiritEntity entity) {
        super(entity);
    }

    @Override
    public Component getDisplayName() {
        return this.entity.getDisplayName();
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int id, Inventory playerInventory, Player player) {
        return new SpiritTransporterContainer(id, playerInventory, this.entity);
    }

    protected boolean matchesPickupItem(ItemStack stack, List<Ingredient> recipeIngredients) {
        return !stack.isEmpty()
                && recipeIngredients.stream().anyMatch(ingredient -> ingredient.test(stack))
                && EntityItemFilter.matches(this.entity.level(), this.entity.getFilterItem(), stack, true);
    }

    protected boolean matchesPickupItem(ItemEntity entity, List<Ingredient> recipeIngredients) {
        return this.matchesPickupItem(entity.getItem(), recipeIngredients);
    }
}
