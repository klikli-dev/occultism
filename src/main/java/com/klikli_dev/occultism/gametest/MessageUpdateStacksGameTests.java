// SPDX-FileCopyrightText: 2026 klikli-dev
//
// SPDX-License-Identifier: MIT

package com.klikli_dev.occultism.gametest;

import com.klikli_dev.occultism.network.messages.MessageUpdateStacks;
import io.netty.buffer.Unpooled;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import net.minecraft.core.component.DataComponents;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.CustomData;

import java.lang.reflect.Field;
import java.util.List;

/**
 * Round-trip tests for {@link MessageUpdateStacks}: the stack counts and slot indices must survive compression,
 * encoding, decoding and decompression.
 */
public class MessageUpdateStacksGameTests {

    private static List<ItemStack> sampleStacks() {
        var damagedSword = new ItemStack(Items.IRON_SWORD);
        damagedSword.set(DataComponents.DAMAGE, 10);
        var taggedSword = new ItemStack(Items.IRON_SWORD);
        taggedSword.set(DataComponents.CUSTOM_DATA, CustomData.EMPTY.update(tag -> tag.putInt("variant", 3)));
        return List.of(
                damagedSword,
                new ItemStack(Items.STONE, 64),
                taggedSword,
                new ItemStack(Items.GOLD_INGOT, 1000)
        );
    }

    private static IntList sampleSlots() {
        return IntArrayList.of(0, 5, 2, 17);
    }

    @SuppressWarnings("unchecked")
    private static <T> T readField(MessageUpdateStacks message, String name) throws ReflectiveOperationException {
        Field field = MessageUpdateStacks.class.getDeclaredField(name);
        field.setAccessible(true);
        return (T) field.get(message);
    }

    /**
     * Encodes a message, decodes it into a new instance, decompresses it and checks every stack count and slot index.
     */
    public static void roundTripPreservesCountsAndSlots(GameTestHelper helper) {
        var registries = helper.getLevel().registryAccess();
        var stacks = sampleStacks();
        var slots = sampleSlots();

        var original = new MessageUpdateStacks(stacks, slots, 128, stacks.size(), 2000L, 2000L, registries);

        var buf = new RegistryFriendlyByteBuf(Unpooled.buffer(), registries);
        original.encode(buf);

        var decoded = new MessageUpdateStacks(buf);
        decoded.uncompress(registries);

        try {
            List<ItemStack> decodedStacks = readField(decoded, "stacks");
            IntList decodedSlots = readField(decoded, "slots");

            helper.assertTrue(decodedStacks.size() == stacks.size(),
                    "Expected " + stacks.size() + " stacks but got " + decodedStacks.size());
            helper.assertTrue(decodedSlots.size() == slots.size(),
                    "Expected " + slots.size() + " slots but got " + decodedSlots.size());

            for (int i = 0; i < stacks.size(); i++) {
                var expected = stacks.get(i);
                var actual = decodedStacks.get(i);
                helper.assertTrue(ItemStack.isSameItemSameComponents(expected, actual),
                        "Stack " + i + " changed: expected " + expected + " but got " + actual);
                helper.assertTrue(actual.getCount() == expected.getCount(),
                        "Stack " + i + " count changed: expected " + expected.getCount() + " but got " + actual.getCount());
                helper.assertTrue(decodedSlots.getInt(i) == slots.getInt(i),
                        "Stack " + i + " slot changed: expected " + slots.getInt(i) + " but got " + decodedSlots.getInt(i));
            }
        } catch (ReflectiveOperationException e) {
            helper.fail("Could not read decoded message fields: " + e.getMessage());
        }
        helper.succeed();
    }

    /**
     * A message built without explicit slots must still round-trip with the unknown slot marker (-1) for every stack.
     */
    public static void roundTripWithoutSlotsUsesUnknownMarker(GameTestHelper helper) {
        var registries = helper.getLevel().registryAccess();
        var stacks = sampleStacks();

        var original = new MessageUpdateStacks(stacks, 128, stacks.size(), 2000L, 2000L, registries);

        var buf = new RegistryFriendlyByteBuf(Unpooled.buffer(), registries);
        original.encode(buf);

        var decoded = new MessageUpdateStacks(buf);
        decoded.uncompress(registries);

        try {
            IntList decodedSlots = readField(decoded, "slots");
            for (int i = 0; i < stacks.size(); i++) {
                helper.assertTrue(decodedSlots.getInt(i) == -1,
                        "Expected unknown slot -1 for stack " + i + " but got " + decodedSlots.getInt(i));
            }
        } catch (ReflectiveOperationException e) {
            helper.fail("Could not read decoded message fields: " + e.getMessage());
        }
        helper.succeed();
    }
}
