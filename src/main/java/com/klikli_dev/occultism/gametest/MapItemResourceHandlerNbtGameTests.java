// SPDX-FileCopyrightText: 2026 klikli-dev
//
// SPDX-License-Identifier: MIT

package com.klikli_dev.occultism.gametest;

import com.klikli_dev.occultism.common.misc.MapItemResourceHandler;
import net.minecraft.core.component.DataComponents;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.CustomData;
import net.neoforged.neoforge.transfer.item.ItemResource;
import net.neoforged.neoforge.transfer.transaction.Transaction;

/**
 * Tests for the NBT save format of {@link MapItemResourceHandler}, covering both the current format and the legacy
 * format that does not store a "slot" entry inside "keyToCountMap".
 */
public class MapItemResourceHandlerNbtGameTests {

    private static MapItemResourceHandler createHandlerWithSlotReuse(GameTestHelper helper) {
        var handler = new MapItemResourceHandler();
        var stone = ItemResource.of(new ItemStack(Items.STONE));
        var dirt = ItemResource.of(new ItemStack(Items.DIRT));
        var cobble = ItemResource.of(new ItemStack(Items.COBBLESTONE));
        var gold = ItemResource.of(new ItemStack(Items.GOLD_INGOT));

        try (var tx = Transaction.openRoot()) {
            handler.insert(stone, 4, tx);
            handler.insert(dirt, 8, tx);
            handler.insert(cobble, 16, tx);
            tx.commit();
        }

        //free slot 1 and reuse it, so the slot assignments are not simply insertion order
        try (var tx = Transaction.openRoot()) {
            helper.assertTrue(handler.extract(dirt, Integer.MAX_VALUE, tx) == 8, "Failed to drain dirt");
            tx.commit();
        }
        try (var tx = Transaction.openRoot()) {
            handler.insert(gold, 3, tx);
            tx.commit();
        }
        return handler;
    }

    private static ItemResource damagedSword(int damage) {
        var stack = new ItemStack(Items.IRON_SWORD);
        stack.set(DataComponents.DAMAGE, damage);
        return ItemResource.of(stack);
    }

    private static ItemResource enchantedSword(int variant) {
        var stack = new ItemStack(Items.IRON_SWORD);
        stack.set(DataComponents.CUSTOM_DATA, CustomData.EMPTY.update(tag -> tag.putInt("variant", variant)));
        return ItemResource.of(stack);
    }

    /**
     * Removes the "slot" entry from every "keyToCountMap" entry, turning a current save into the legacy format.
     */
    private static CompoundTag toLegacyFormat(CompoundTag nbt) {
        var legacy = nbt.copy();
        var countMap = legacy.getListOrEmpty("keyToCountMap");
        var stripped = new ListTag();
        countMap.forEach(tag -> {
            var entry = ((CompoundTag) tag).copy();
            entry.remove("slot");
            stripped.add(entry);
        });
        legacy.put("keyToCountMap", stripped);
        return legacy;
    }

    private static void assertSlotIdentity(GameTestHelper helper, MapItemResourceHandler handler, String when) {
        for (var entry : handler.resourceToCountMap().object2IntEntrySet()) {
            var key = entry.getKey();
            int slot = handler.resourceToSlot().getInt(key);
            helper.assertTrue(slot != -1, "Resource " + key + " has a count but no slot " + when);
            helper.assertTrue(handler.slotToResource().get(slot) == key,
                    "Slot " + slot + " does not hold the same ItemResource instance as the count map " + when);
        }
    }

    private static void assertSameContents(GameTestHelper helper, MapItemResourceHandler expected, MapItemResourceHandler actual) {
        helper.assertTrue(expected.totalItemCount() == actual.totalItemCount(),
                "Total count mismatch: " + expected.totalItemCount() + " vs " + actual.totalItemCount());
        helper.assertTrue(expected.resourceToCountMap().size() == actual.resourceToCountMap().size(),
                "Resource count mismatch: " + expected.resourceToCountMap().size() + " vs " + actual.resourceToCountMap().size());
        for (var entry : expected.resourceToCountMap().object2IntEntrySet()) {
            var key = entry.getKey();
            helper.assertTrue(actual.get(key) == entry.getIntValue(),
                    "Count for " + key + " is " + actual.get(key) + " but expected " + entry.getIntValue());
            helper.assertTrue(actual.resourceToSlot().getInt(key) == expected.resourceToSlot().getInt(key),
                    "Slot for " + key + " is " + actual.resourceToSlot().getInt(key) + " but expected " + expected.resourceToSlot().getInt(key));
        }
    }

    /**
     * Legacy saves have no "slot" in keyToCountMap. Each entry must still resolve to the same ItemResource instance that
     * keyToSlot uses, so that slot lookups and count lookups agree.
     */
    public static void legacyFormatSharesResourceInstances(GameTestHelper helper) {
        var source = createHandlerWithSlotReuse(helper);
        var legacy = toLegacyFormat(source.serializeNBT(helper.getLevel().registryAccess()));

        var loaded = new MapItemResourceHandler();
        loaded.deserializeNBT(helper.getLevel().registryAccess(), legacy);

        assertSlotIdentity(helper, loaded, "after loading the legacy format");
        assertSameContents(helper, source, loaded);
        helper.succeed();
    }

    /**
     * Legacy saves with damaged and custom-data variants: each variant must keep its own slot and must not be merged
     * into another variant when the count map and slot map are decoded separately.
     */
    public static void legacyFormatKeepsVariantsSeparate(GameTestHelper helper) {
        var handler = new MapItemResourceHandler();
        var damaged1 = damagedSword(1);
        var damaged2 = damagedSword(2);
        var variant = enchantedSword(7);

        try (var tx = Transaction.openRoot()) {
            handler.insert(damaged1, 1, tx);
            handler.insert(damaged2, 1, tx);
            handler.insert(variant, 1, tx);
            tx.commit();
        }

        var legacy = toLegacyFormat(handler.serializeNBT(helper.getLevel().registryAccess()));
        var loaded = new MapItemResourceHandler();
        loaded.deserializeNBT(helper.getLevel().registryAccess(), legacy);

        helper.assertTrue(loaded.resourceToCountMap().size() == 3,
                "Variants were merged, only " + loaded.resourceToCountMap().size() + " distinct resources remain");
        helper.assertTrue(loaded.totalItemCount() == 3, "Total count changed to " + loaded.totalItemCount());
        assertSlotIdentity(helper, loaded, "for damaged and custom-data variants");

        for (var resource : new ItemResource[]{damaged1, damaged2, variant}) {
            helper.assertTrue(loaded.get(resource) == 1, "Variant " + resource + " lost its count");
            int slot = loaded.resourceToSlot().getInt(resource);
            helper.assertTrue(slot == handler.resourceToSlot().getInt(resource),
                    "Variant " + resource + " moved from slot " + handler.resourceToSlot().getInt(resource) + " to " + slot);
        }
        helper.succeed();
    }

    /**
     * Current-format round trip (serializeNBT -> deserializeNBT) must preserve exact slot indices, counts and empty
     * slot bookkeeping, so that slot based extraction still hits the same resource.
     */
    public static void roundTripPreservesSlotsAndCounts(GameTestHelper helper) {
        var source = createHandlerWithSlotReuse(helper);
        var nbt = source.serializeNBT(helper.getLevel().registryAccess());

        var loaded = new MapItemResourceHandler();
        loaded.deserializeNBT(helper.getLevel().registryAccess(), nbt);

        assertSlotIdentity(helper, loaded, "after a current-format round trip");
        assertSameContents(helper, source, loaded);
        helper.assertTrue(loaded.getSlots() == source.getSlots(),
                "Slot count changed from " + source.getSlots() + " to " + loaded.getSlots());
        for (int slot = 0; slot < source.getSlots(); slot++) {
            helper.assertTrue(loaded.getResource(slot).equals(source.getResource(slot)),
                    "Slot " + slot + " holds " + loaded.getResource(slot) + " but expected " + source.getResource(slot));
            helper.assertTrue(loaded.getAmountAsLong(slot) == source.getAmountAsLong(slot),
                    "Slot " + slot + " amount is " + loaded.getAmountAsLong(slot) + " but expected " + source.getAmountAsLong(slot));
        }

        var goldBefore = loaded.getResource(1);
        var extracted = loaded.extractItem(1, 2, false);
        helper.assertTrue(extracted.is(Items.GOLD_INGOT) && extracted.getCount() == 2,
                "Slot 1 extraction after round trip returned " + extracted);
        helper.assertTrue(loaded.get(goldBefore) == 1, "Slot 1 count after extraction is wrong");
        helper.succeed();
    }

    /**
     * Round-trip a legacy save, then save again in the current format. The second load must give the same slots.
     */
    public static void legacySaveUpgradesToCurrentFormat(GameTestHelper helper) {
        var source = createHandlerWithSlotReuse(helper);
        var legacy = toLegacyFormat(source.serializeNBT(helper.getLevel().registryAccess()));

        var loaded = new MapItemResourceHandler();
        loaded.deserializeNBT(helper.getLevel().registryAccess(), legacy);

        var upgraded = loaded.serializeNBT(helper.getLevel().registryAccess());
        var reloaded = new MapItemResourceHandler();
        reloaded.deserializeNBT(helper.getLevel().registryAccess(), upgraded);

        assertSlotIdentity(helper, reloaded, "after upgrading a legacy save");
        assertSameContents(helper, source, reloaded);
        helper.succeed();
    }
}
