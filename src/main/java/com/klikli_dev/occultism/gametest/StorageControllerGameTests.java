// SPDX-FileCopyrightText: 2026 klikli-dev
//
// SPDX-License-Identifier: MIT

package com.klikli_dev.occultism.gametest;

import com.klikli_dev.occultism.common.blockentity.StorageControllerBlockEntity;
import com.klikli_dev.occultism.common.misc.MapItemResourceHandler;
import com.klikli_dev.occultism.registry.OccultismBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.CustomData;
import net.neoforged.neoforge.transfer.item.ItemResource;
import net.neoforged.neoforge.transfer.transaction.Transaction;

/**
 * Tests for slot based access on the storage controller. Variants of the same item (different damage or custom data)
 * must stay distinct, and slot based extraction must return the exact variant stored in that slot.
 */
public class StorageControllerGameTests {

    private static final BlockPos CONTROLLER_POS = new BlockPos(1, 2, 1);

    private static StorageControllerBlockEntity placeController(GameTestHelper helper) {
        helper.setBlock(CONTROLLER_POS, OccultismBlocks.STORAGE_CONTROLLER.get());
        return (StorageControllerBlockEntity) helper.getLevel().getBlockEntity(helper.absolutePos(CONTROLLER_POS));
    }

    private static ItemResource sword(int damage) {
        var stack = new ItemStack(Items.IRON_SWORD);
        stack.set(DataComponents.DAMAGE, damage);
        return ItemResource.of(stack);
    }

    private static ItemResource taggedSword(int variant) {
        var stack = new ItemStack(Items.IRON_SWORD);
        stack.set(DataComponents.CUSTOM_DATA, CustomData.EMPTY.update(tag -> tag.putInt("variant", variant)));
        return ItemResource.of(stack);
    }

    private static int slotOf(MapItemResourceHandler handler, ItemResource resource) {
        return handler.resourceToSlot().getInt(resource);
    }

    /**
     * Two sword variants are stored in separate slots. Extracting by slot must return the exact requested variant,
     * even when the request is a plain sword without damage or custom data (as a stripped client stack would be).
     */
    public static void slotExtractionReturnsExactVariant(GameTestHelper helper) {
        var controller = placeController(helper);
        var handler = controller.itemStackHandler;
        var damaged = sword(10);
        var tagged = taggedSword(3);

        try (var tx = Transaction.openRoot()) {
            handler.insert(damaged, 2, tx);
            handler.insert(tagged, 5, tx);
            tx.commit();
        }

        int damagedSlot = slotOf(handler, damaged);
        int taggedSlot = slotOf(handler, tagged);
        helper.assertTrue(damagedSlot != -1 && taggedSlot != -1 && damagedSlot != taggedSlot,
                "Variants must occupy different slots, got " + damagedSlot + " and " + taggedSlot);

        var taggedInSlot = controller.getItemStackInSlot(taggedSlot, 1, true);
        helper.assertTrue(taggedInSlot.getCount() == 1, "Simulated slot extraction returned " + taggedInSlot);
        helper.assertTrue(taggedInSlot.getOrDefault(DataComponents.DAMAGE, 0) == 0,
                "Tagged variant should not carry damage, got " + taggedInSlot);
        helper.assertTrue(taggedInSlot.has(DataComponents.CUSTOM_DATA),
                "Tagged variant lost its custom data");

        var extractedDamaged = controller.getItemStackInSlot(damagedSlot, 2, false);
        helper.assertTrue(extractedDamaged.getCount() == 2, "Expected 2 damaged swords, got " + extractedDamaged);
        helper.assertTrue(extractedDamaged.is(Items.IRON_SWORD), "Wrong item returned " + extractedDamaged);
        helper.assertTrue(extractedDamaged.get(DataComponents.DAMAGE) == 10,
                "Slot " + damagedSlot + " returned the wrong variant: " + extractedDamaged);
        helper.assertTrue(extractedDamaged.get(DataComponents.CUSTOM_DATA) == null,
                "Damaged variant picked up custom data: " + extractedDamaged);

        helper.assertTrue(controller.getAvailableAmountInSlot(damagedSlot) == 0, "Damaged slot should be empty");
        helper.assertTrue(controller.getAvailableAmountInSlot(taggedSlot) == 5, "Tagged slot count changed");
        helper.succeed();
    }

    /**
     * Slot extraction must not fall back to a different variant when the requested slot is empty.
     */
    public static void emptySlotExtractionDoesNotBorrowOtherVariant(GameTestHelper helper) {
        var controller = placeController(helper);
        var handler = controller.itemStackHandler;
        var damaged = sword(4);

        try (var tx = Transaction.openRoot()) {
            handler.insert(damaged, 3, tx);
            tx.commit();
        }

        int damagedSlot = slotOf(handler, damaged);
        int emptySlot = damagedSlot == 0 ? 1 : 0;
        var extracted = controller.getItemStackInSlot(emptySlot, 1, false);
        helper.assertTrue(extracted.isEmpty(), "Empty slot " + emptySlot + " returned " + extracted);
        helper.assertTrue(handler.get(damaged) == 3, "Count of the other variant changed");
        helper.succeed();
    }

    /**
     * Save and load the controller contents: slot indices, counts and variants must match, and slot extraction must
     * still return the same variant from the loaded handler.
     */
    public static void serializedContentsKeepSlotsForExtraction(GameTestHelper helper) {
        var controller = placeController(helper);
        var handler = controller.itemStackHandler;
        var damaged = sword(10);
        var tagged = taggedSword(3);
        var other = ItemResource.of(new ItemStack(Items.STONE));

        try (var tx = Transaction.openRoot()) {
            handler.insert(other, 7, tx);
            handler.insert(damaged, 2, tx);
            handler.insert(tagged, 5, tx);
            tx.commit();
        }
        try (var tx = Transaction.openRoot()) {
            handler.extract(other, Integer.MAX_VALUE, tx);
            tx.commit();
        }
        try (var tx = Transaction.openRoot()) {
            handler.insert(ItemResource.of(new ItemStack(Items.DIAMOND)), 4, tx);
            tx.commit();
        }

        var nbt = handler.serializeNBT(helper.getLevel().registryAccess());
        var loaded = new MapItemResourceHandler();
        loaded.deserializeNBT(helper.getLevel().registryAccess(), nbt);

        helper.assertTrue(loaded.getSlots() == handler.getSlots(),
                "Slot count changed from " + handler.getSlots() + " to " + loaded.getSlots());
        for (var resource : new ItemResource[]{damaged, tagged}) {
            helper.assertTrue(slotOf(loaded, resource) == slotOf(handler, resource),
                    "Variant " + resource + " moved slots after load");
            helper.assertTrue(loaded.get(resource) == handler.get(resource),
                    "Count of " + resource + " changed after load");
        }
        helper.assertTrue(loaded.getResource(slotOf(handler, damaged)).equals(damaged),
                "Damaged variant is not in its original slot after load");

        var extracted = loaded.extractItem(slotOf(loaded, damaged), 1, false);
        helper.assertTrue(extracted.getCount() == 1 && extracted.get(DataComponents.DAMAGE) == 10,
                "Slot extraction after load returned the wrong variant: " + extracted);
        helper.assertTrue(loaded.get(damaged) == 1, "Slot extraction did not reduce the count after load");
        helper.succeed();
    }
}
