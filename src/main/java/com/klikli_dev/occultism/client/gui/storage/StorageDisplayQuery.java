/*
 * SPDX-FileCopyrightText: 2026 klikli-dev
 *
 * SPDX-License-Identifier: MIT
 */

package com.klikli_dev.occultism.client.gui.storage;

import com.klikli_dev.occultism.api.common.data.MachineReference;
import com.klikli_dev.occultism.api.common.data.SortDirection;
import com.klikli_dev.occultism.api.common.data.SortType;
import com.klikli_dev.occultism.util.TextUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

public class StorageDisplayQuery {
    public List<ItemStack> filterItems(List<ItemStack> stacks, String searchText, Predicate<ItemStack> matchesSearch) {
        if (searchText.isEmpty()) {
            return new ArrayList<>(stacks);
        }

        List<ItemStack> filtered = new ArrayList<>();
        for (ItemStack stack : stacks) {
            if (matchesSearch.test(stack)) {
                filtered.add(stack);
            }
        }

        return filtered;
    }

    public List<MachineReference> filterMachines(List<MachineReference> machines, String searchText,
                                                 Predicate<MachineReference> matchesSearch) {
        if (searchText.isEmpty()) {
            return new ArrayList<>(machines);
        }

        List<MachineReference> filtered = new ArrayList<>();
        for (MachineReference machine : machines) {
            if (matchesSearch.test(machine)) {
                filtered.add(machine);
            }
        }

        return filtered;
    }

    public void sortItems(List<ItemStack> stacksToDisplay, SortDirection sortDirection, SortType sortType) {
        int direction = sortDirection.isDown() ? -1 : 1;
        stacksToDisplay.sort(new Comparator<>() {
            @Override
            public int compare(ItemStack a, ItemStack b) {
                return switch (sortType) {
                    case AMOUNT -> Integer.compare(b.getCount(), a.getCount()) * direction;
                    case NAME -> a.getDisplayName().getString().compareToIgnoreCase(b.getDisplayName().getString()) * direction;
                    case MOD -> TextUtil.getModNameForGameObject(a.getItem())
                            .compareToIgnoreCase(TextUtil.getModNameForGameObject(b.getItem())) * direction;
                };
            }
        });
    }

    public void sortMachines(List<MachineReference> machinesToDisplay, SortDirection sortDirection, SortType sortType,
                             BlockPos actionPosition, ResourceKey<Level> dimensionKey) {
        int direction = sortDirection.isDown() ? -1 : 1;
        machinesToDisplay.sort(new Comparator<>() {
            @Override
            public int compare(MachineReference a, MachineReference b) {
                return switch (sortType) {
                    case AMOUNT -> Double.compare(distanceFor(b), distanceFor(a)) * direction;
                    case NAME -> a.getInsertItemStack().getDisplayName().getString()
                            .compareToIgnoreCase(b.getInsertItemStack().getDisplayName().getString()) * direction;
                    case MOD -> TextUtil.getModNameForGameObject(a.getInsertItem())
                            .compareToIgnoreCase(TextUtil.getModNameForGameObject(b.getInsertItem())) * direction;
                };
            }

            private double distanceFor(MachineReference machine) {
                return machine.insertGlobalPos.getDimensionKey() == dimensionKey
                        ? machine.insertGlobalPos.getPos().distSqr(actionPosition)
                        : Double.MAX_VALUE;
            }
        });
    }

    public int maxFirstVisibleRow(int entryCount, int columns, int visibleRows) {
        int totalRows = entryCount / columns;
        if (entryCount % columns != 0) {
            totalRows++;
        }

        int maxFirstVisibleRow = totalRows - (visibleRows - 1);
        return Math.max(1, maxFirstVisibleRow);
    }

    public int firstVisibleIndex(int firstVisibleRow, int columns) {
        return (firstVisibleRow - 1) * columns;
    }
}
