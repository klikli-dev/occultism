/*
 * SPDX-FileCopyrightText: 2026 klikli-dev
 *
 * SPDX-License-Identifier: MIT
 */

package com.klikli_dev.occultism.common.item.filter;

import com.klikli_dev.codedefinedgui.premade.filter.attribute.AttributeFilterDefinition;
import com.klikli_dev.codedefinedgui.premade.filter.core.FilterMatchContext;
import com.klikli_dev.codedefinedgui.premade.filter.list.ListFilterDefinition;
import com.klikli_dev.codedefinedgui.premade.filter.list.ListFilterMode;
import com.klikli_dev.codedefinedgui.premade.filter.list.ListFilterState;
import com.klikli_dev.codedefinedgui.premade.filter.list.ListFilterStateAccessor;
import com.klikli_dev.occultism.common.entity.IFilterConfigurable;
import com.klikli_dev.occultism.common.misc.MapItemResourceHandler;
import com.klikli_dev.occultism.registry.OccultismItems;
import com.klikli_dev.occultism.util.ItemTransferUtil;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.transfer.ResourceHandler;
import net.neoforged.neoforge.transfer.item.ItemResource;

public final class EntityItemFilter {
    private EntityItemFilter() {
    }

    public static boolean isFilterItem(ItemStack stack) {
        return stack.is(OccultismItems.LIST_FILTER.get()) || stack.is(OccultismItems.ATTRIBUTE_FILTER.get());
    }

    public static boolean matches(Level level, IFilterConfigurable configurable, ItemStack candidate) {
        return matches(level, configurable.getFilterItem(), candidate, configurable.matchesWhenFilterEmpty());
    }

    public static boolean matches(Level level, ItemStack filterStack, ItemStack candidate, boolean emptyResult) {
        if (filterStack.isEmpty()) {
            return emptyResult;
        }

        FilterMatchContext context = new FilterMatchContext(level);
        if (filterStack.is(OccultismItems.LIST_FILTER.get())) {
            return ListFilterDefinition.INSTANCE.matches(filterStack, candidate, context);
        }

        if (filterStack.is(OccultismItems.ATTRIBUTE_FILTER.get())) {
            return AttributeFilterDefinition.INSTANCE.matches(filterStack, candidate, context);
        }

        return emptyResult;
    }

    public static int getFirstMatchingSlot(Level level, ResourceHandler<ItemResource> handler, IFilterConfigurable configurable) {
        ItemStack filterStack = configurable.getFilterItem();
        if (filterStack.isEmpty() && !configurable.matchesWhenFilterEmpty()) {
            return -1;
        }

        for (int i = 0; i < handler.size(); i++) {
            var resource = handler.getResource(i);
            if (resource.isEmpty()) {
                continue;
            }

            ItemStack stack = resource.toStack((int) handler.getAmountAsLong(i));
            if (matches(level, filterStack, stack, configurable.matchesWhenFilterEmpty())) {
                return i;
            }
        }

        return -1;
    }

    public static boolean tryPerformStorageActuatorExtraction(Level level, ResourceHandler<ItemResource> blockEntityHandler,
                                                              ResourceHandler<ItemResource> entityHandler, ItemStack filterStack) {
        if (!(blockEntityHandler instanceof MapItemResourceHandler mapItemStackHandler)) {
            return false;
        }

        if (!filterStack.is(OccultismItems.LIST_FILTER.get())) {
            return false;
        }

        ListFilterState state = ListFilterStateAccessor.INSTANCE.read(filterStack);
        if (state.mode() != ListFilterMode.ALLOW) {
            return false;
        }

        NonNullList<ItemStack> entries = NonNullList.withSize(state.entries().getSlots(), ItemStack.EMPTY);
        state.entries().copyInto(entries);

        boolean hasEntries = false;
        for (ItemStack entry : entries) {
            if (entry.isEmpty()) {
                continue;
            }

            hasEntries = true;
            ItemStack extracted = state.respectDataComponents()
                    ? mapItemStackHandler.extractItem(entry, Integer.MAX_VALUE, true)
                    : mapItemStackHandler.extractItemIgnoreComponents(entry, Integer.MAX_VALUE, true);
            if (extracted.isEmpty()) {
                continue;
            }

            ItemStack inserted = ItemTransferUtil.insertItemStacked(entityHandler, extracted, true);
            if (inserted.getCount() == extracted.getCount()) {
                continue;
            }

            ItemStack remaining = ItemTransferUtil.insertItemStacked(entityHandler, extracted, false);
            mapItemStackHandler.extractItem(extracted, extracted.getCount() - remaining.getCount(), false);
            return true;
        }

        return hasEntries;
    }
}
