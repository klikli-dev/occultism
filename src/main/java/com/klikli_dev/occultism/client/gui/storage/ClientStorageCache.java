/*
 * MIT License
 *
 * Copyright 2024 klikli-dev
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

package com.klikli_dev.occultism.client.gui.storage;

import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ClientStorageCache {
    private final Int2ObjectOpenHashMap<List<ItemStack>> entriesByItemId = new Int2ObjectOpenHashMap<>();
    private List<ItemStack> stacks = new ArrayList<>();
    private boolean entriesByItemIdNeedsUpdate = true;

    public List<ItemStack> stacks() {
        return this.stacks;
    }

    public void update(List<ItemStack> stacks) {
        this.stacks = stacks;

        this.entriesByItemIdNeedsUpdate = true;
    }

    public List<ItemStack> getByIngredient(Ingredient ingredient) {
        var entries = new ArrayList<ItemStack>();
        for (int i = 0; i < ingredient.getStackingIds().size(); i++) {
            var itemId = ingredient.getStackingIds().getInt(i);
            for (var entry : this.getByItemId(itemId)) {
                if (ingredient.test(entry)) {
                    entries.add(entry);
                }
            }
        }
        return entries;
    }

    private Collection<ItemStack> getByItemId(int itemId) {
        if (this.entriesByItemIdNeedsUpdate) {
            this.rebuildItemIdToEntries();
            this.entriesByItemIdNeedsUpdate = false;
        }
        return this.entriesByItemId.getOrDefault(itemId, List.of());
    }

    private void rebuildItemIdToEntries() {
        this.entriesByItemId.clear();
        for (var entry : this.stacks()) {
            var itemId = BuiltInRegistries.ITEM.getId(entry.getItem());
            var currentList = this.entriesByItemId.get(itemId);
            if (currentList == null) {
                // For many items without NBT, this list will only ever have one entry
                this.entriesByItemId.put(itemId, List.of(entry));
            } else if (currentList.size() == 1) {
                // Convert the list from an immutable single-entry list to a mutable normal arraylist
                var mutableList = new ArrayList<ItemStack>(10);
                mutableList.addAll(currentList);
                mutableList.add(entry);
                this.entriesByItemId.put(itemId, mutableList);
            } else {
                // If it had more than 1 item, it must have been mutable already
                currentList.add(entry);
            }
        }
    }
}
