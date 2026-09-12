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

    /**
     * Rolling back an insert that reused a freed slot must restore the exact slot assignments and the empty slot
     * list, so later inserts land in the same slots they would have occupied without the aborted transaction.
     */
    public static void abortedSlotReuseRestoresSlotAssignments(GameTestHelper helper) {
        var handler = new MapItemResourceHandler();
        var a = ItemResource.of(new ItemStack(Items.STONE));
        var b = ItemResource.of(new ItemStack(Items.DIRT));
        var c = ItemResource.of(new ItemStack(Items.COBBLESTONE));
        var d = ItemResource.of(new ItemStack(Items.GOLD_INGOT));
        var e = ItemResource.of(new ItemStack(Items.IRON_INGOT));
        var f = ItemResource.of(new ItemStack(Items.DIAMOND));

        try (var tx = Transaction.openRoot()) {
            handler.insert(a, 4, tx);
            handler.insert(b, 4, tx);
            handler.insert(c, 4, tx);
            tx.commit();
        }

        try (var tx = Transaction.openRoot()) {
            helper.assertTrue(handler.extract(b, Integer.MAX_VALUE, tx) == 4,
                    "Expected to fully extract the middle resource");
            tx.commit();
        }

        helper.assertTrue(handler.getSlots() == 4, "Expected slots 0-3 after three inserts");
        helper.assertTrue(handler.getResource(1).isEmpty(), "Slot 1 should be free after the committed extraction");

        try (var tx = Transaction.openRoot()) {
            handler.insert(d, 7, tx);
        }

        helper.assertTrue(handler.get(d) == 0, "Aborted insert was not rolled back");
        helper.assertTrue(handler.getResource(1).isEmpty(),
                "The slot reused by the aborted insert was not freed again");

        try (var tx = Transaction.openRoot()) {
            handler.insert(e, 9, tx);
            tx.commit();
        }

        helper.assertTrue(handler.getResource(1).equals(e),
                "Expected the freed slot to be reused by the next insert, but slot 1 holds " + handler.getResource(1));

        try (var tx = Transaction.openRoot()) {
            handler.extract(a, Integer.MAX_VALUE, tx);
        }

        helper.assertTrue(handler.get(a) == 4, "Aborted drain changed the count");

        try (var tx = Transaction.openRoot()) {
            handler.insert(f, 1, tx);
            tx.commit();
        }

        helper.assertTrue(handler.getResource(0).equals(a),
                "Slot 0 must still hold the drained resource after the aborted drain, but holds " + handler.getResource(0));
        helper.assertTrue(handler.getResource(3).equals(f),
                "Expected the new resource in the next free slot 3, but slot 3 holds " + handler.getResource(3));
        helper.succeed();
    }

    /**
     * The undo journal must not accumulate entries across closed transactions, and the active snapshot count must
     * return to zero. Leaked entries would grow memory indefinitely and corrupt future rollbacks.
     */
    public static void undoJournalDrainsAfterClosedTransactions(GameTestHelper helper) {
        var handler = new InstrumentedHandler();
        var resources = testResources();

        for (int round = 0; round < 4; round++) {
            for (var resource : resources) {
                try (var tx = Transaction.openRoot()) {
                    handler.insert(resource, STACK_SIZE, tx);
                    tx.commit();
                }
                assertJournalDrained(helper, handler, "after a committed insert");

                try (var tx = Transaction.openRoot()) {
                    handler.extract(resource, Integer.MAX_VALUE, tx);
                }
                assertJournalDrained(helper, handler, "after an aborted drain");

                try (var tx = Transaction.openRoot()) {
                    handler.insert(resource, 2, tx);
                    try (var nested = Transaction.open(tx)) {
                        handler.extract(resource, 1, nested);
                        nested.commit();
                    }
                    tx.commit();
                }
                assertJournalDrained(helper, handler, "after a committed nested transaction");

                try (var tx = Transaction.openRoot()) {
                    handler.insert(resource, 5, tx);
                    try (var nested = Transaction.open(tx)) {
                        handler.extract(resource, 1, nested);
                        nested.commit();
                    }
                }
                assertJournalDrained(helper, handler, "after an aborted root with committed nested transaction");
            }
        }

        helper.succeed();
    }

    private static void assertJournalDrained(GameTestHelper helper, InstrumentedHandler handler, String when) {
        helper.assertTrue(handler.undoLogSize() == 0,
                "Undo journal still holds " + handler.undoLogSize() + " entries " + when);
        helper.assertTrue(handler.activeSnapshotCount() == 0,
                "Active snapshot count is " + handler.activeSnapshotCount() + " instead of 0 " + when);
    }

    private static class InstrumentedHandler extends MapItemResourceHandler {
        int undoLogSize() {
            return this.undoLog.size();
        }

        int activeSnapshotCount() {
            return this.activeSnapshotCount;
        }
    }
}
