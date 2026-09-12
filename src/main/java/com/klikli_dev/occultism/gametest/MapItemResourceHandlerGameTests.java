// SPDX-FileCopyrightText: 2026 klikli-dev
//
// SPDX-License-Identifier: MIT

package com.klikli_dev.occultism.gametest;

import com.klikli_dev.occultism.common.misc.MapItemResourceHandler;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.transfer.item.ItemResource;
import net.neoforged.neoforge.transfer.transaction.Transaction;

import java.util.List;

/**
 * Regression tests for the transactional snapshot/rollback behavior of {@link MapItemResourceHandler}.
 * <p>
 * An external handler (for example an AE2 storage bus) discovers available stacks by running extractions in
 * transactions that are then aborted. These tests reproduce that probe pattern and verify that aborting leaves the
 * handler untouched, while committing persists the changes.
 */
public class MapItemResourceHandlerGameTests {

    private static final int STACK_SIZE = 16;

    private static List<ItemResource> testResources() {
        return List.of(
                ItemResource.of(new ItemStack(Items.STONE)),
                ItemResource.of(new ItemStack(Items.DIRT)),
                ItemResource.of(new ItemStack(Items.COBBLESTONE)),
                ItemResource.of(new ItemStack(Items.OAK_LOG))
        );
    }

    private static MapItemResourceHandler createFilledHandler() {
        var handler = new MapItemResourceHandler();
        for (var resource : testResources()) {
            try (var tx = Transaction.openRoot()) {
                handler.insert(resource, STACK_SIZE, tx);
                tx.commit();
            }
        }
        return handler;
    }

    private static void assertState(GameTestHelper helper, MapItemResourceHandler handler, List<ItemResource> resources, int[] expectedCounts) {
        long expectedTotal = 0;
        for (int i = 0; i < resources.size(); i++) {
            var resource = resources.get(i);
            expectedTotal += expectedCounts[i];
            helper.assertTrue(handler.get(resource) == expectedCounts[i],
                    "Count for " + resource + " changed: expected " + expectedCounts[i] + " but was " + handler.get(resource));
        }
        helper.assertTrue(handler.totalItemCount() == expectedTotal,
                "Total item count changed: expected " + expectedTotal + " but was " + handler.totalItemCount());
        helper.assertTrue(handler.getSlots() == resources.size() + 1,
                "Slot count changed: expected " + (resources.size() + 1) + " but was " + handler.getSlots());
    }

    /**
     * Reproduces the issue's hot path: repeatedly draining stored resources in transactions that are aborted. The
     * undo journal must restore counts, total count, slot mappings, empty slots and next slot index each time.
     */
    public static void abortedProbesDoNotChangeState(GameTestHelper helper) {
        var handler = createFilledHandler();
        var resources = testResources();
        var countsBefore = resources.stream().mapToInt(handler::get).toArray();

        for (int round = 0; round < 8; round++) {
            // One root transaction per resource, extracting everything one item at a time (recursive style).
            for (var resource : resources) {
                try (var tx = Transaction.openRoot()) {
                    int extracted;
                    do {
                        extracted = handler.extract(resource, 1, tx);
                    } while (extracted > 0);
                }
            }

            // And one transaction that drains every resource before being aborted.
            try (var tx = Transaction.openRoot()) {
                for (var resource : resources) {
                    handler.extract(resource, Integer.MAX_VALUE, tx);
                }
            }
        }

        assertState(helper, handler, resources, countsBefore);
        helper.succeed();
    }

    public static void committedExtractionPersists(GameTestHelper helper) {
        var handler = createFilledHandler();
        var resource = testResources().get(0);
        long totalBefore = handler.totalItemCount();

        try (var tx = Transaction.openRoot()) {
            int extracted = handler.extract(resource, 10, tx);
            helper.assertTrue(extracted == 10, "Expected to extract 10 but extracted " + extracted);
            tx.commit();
        }

        helper.assertTrue(handler.get(resource) == STACK_SIZE - 10,
                "Committed extraction was not persisted, count is " + handler.get(resource));
        helper.assertTrue(handler.totalItemCount() == totalBefore - 10,
                "Committed extraction did not reduce the total count");
        helper.succeed();
    }

    public static void nestedCommitThenRootAbortRollsBack(GameTestHelper helper) {
        var handler = createFilledHandler();
        var resources = testResources();
        var countsBefore = resources.stream().mapToInt(handler::get).toArray();
        var newResource = ItemResource.of(new ItemStack(Items.GOLD_INGOT));

        try (var root = Transaction.openRoot()) {
            handler.insert(newResource, 12, root);
            try (var nested = Transaction.open(root)) {
                handler.insert(newResource, 4, nested);
                handler.extract(resources.get(0), 6, nested);
                nested.commit();
            }
            // Root is closed without committing, so all of it must roll back.
        }

        helper.assertTrue(handler.get(newResource) == 0,
                "Changes of an aborted root transaction were not rolled back");
        assertState(helper, handler, resources, countsBefore);
        helper.succeed();
    }

    public static void nestedCommitThenRootCommitPersists(GameTestHelper helper) {
        var handler = createFilledHandler();
        var resources = testResources();
        var resource = resources.get(0);
        var newResource = ItemResource.of(new ItemStack(Items.GOLD_INGOT));
        long totalBefore = handler.totalItemCount();

        try (var root = Transaction.openRoot()) {
            handler.insert(newResource, 12, root);
            try (var nested = Transaction.open(root)) {
                handler.insert(newResource, 4, nested);
                handler.extract(resource, 6, nested);
                nested.commit();
            }
            root.commit();
        }

        helper.assertTrue(handler.get(newResource) == 16,
                "Committed nested insert was not persisted, count is " + handler.get(newResource));
        helper.assertTrue(handler.get(resource) == STACK_SIZE - 6,
                "Committed nested extraction was not persisted, count is " + handler.get(resource));
        helper.assertTrue(handler.totalItemCount() == totalBefore + 16 - 6,
                "Total item count does not reflect the committed changes");
        helper.succeed();
    }
}
