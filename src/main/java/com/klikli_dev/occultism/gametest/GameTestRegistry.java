// SPDX-FileCopyrightText: 2026 klikli-dev
//
// SPDX-License-Identifier: MIT

package com.klikli_dev.occultism.gametest;

import com.klikli_dev.occultism.Occultism;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.gametest.framework.FunctionGameTestInstance;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.gametest.framework.TestData;
import net.minecraft.gametest.framework.TestEnvironmentDefinition;
import net.minecraft.resources.Identifier;
import net.neoforged.neoforge.event.RegisterGameTestsEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Consumer;

public class GameTestRegistry {

    public static final DeferredRegister<Consumer<GameTestHelper>> TEST_FUNCTIONS =
            DeferredRegister.create(BuiltInRegistries.TEST_FUNCTION, Occultism.MODID);

    public static final DeferredHolder<Consumer<GameTestHelper>, Consumer<GameTestHelper>> MAP_ITEM_RESOURCE_HANDLER_ABORTED_PROBES =
            TEST_FUNCTIONS.register("map_item_resource_handler_aborted_probes", () -> MapItemResourceHandlerGameTests::abortedProbesDoNotChangeState);

    public static final DeferredHolder<Consumer<GameTestHelper>, Consumer<GameTestHelper>> MAP_ITEM_RESOURCE_HANDLER_COMMITTED_EXTRACTION =
            TEST_FUNCTIONS.register("map_item_resource_handler_committed_extraction", () -> MapItemResourceHandlerGameTests::committedExtractionPersists);

    public static final DeferredHolder<Consumer<GameTestHelper>, Consumer<GameTestHelper>> MAP_ITEM_RESOURCE_HANDLER_NESTED_ABORT =
            TEST_FUNCTIONS.register("map_item_resource_handler_nested_abort", () -> MapItemResourceHandlerGameTests::nestedCommitThenRootAbortRollsBack);

    public static final DeferredHolder<Consumer<GameTestHelper>, Consumer<GameTestHelper>> MAP_ITEM_RESOURCE_HANDLER_NESTED_COMMIT =
            TEST_FUNCTIONS.register("map_item_resource_handler_nested_commit", () -> MapItemResourceHandlerGameTests::nestedCommitThenRootCommitPersists);

    public static final DeferredHolder<Consumer<GameTestHelper>, Consumer<GameTestHelper>> MAP_ITEM_RESOURCE_HANDLER_SLOT_REUSE_ROLLBACK =
            TEST_FUNCTIONS.register("map_item_resource_handler_slot_reuse_rollback", () -> MapItemResourceHandlerGameTests::abortedSlotReuseRestoresSlotAssignments);

    public static final DeferredHolder<Consumer<GameTestHelper>, Consumer<GameTestHelper>> MAP_ITEM_RESOURCE_HANDLER_UNDO_JOURNAL_DRAINS =
            TEST_FUNCTIONS.register("map_item_resource_handler_undo_journal_drains", () -> MapItemResourceHandlerGameTests::undoJournalDrainsAfterClosedTransactions);

    public static final DeferredHolder<Consumer<GameTestHelper>, Consumer<GameTestHelper>> MAP_ITEM_RESOURCE_HANDLER_RECURSIVE_COMMIT_NOTIFICATION =
            TEST_FUNCTIONS.register("map_item_resource_handler_recursive_commit_notification", () -> MapItemResourceHandlerGameTests::commitNotificationsCoverChangesTriggeredByNotifications);

    public static final DeferredHolder<Consumer<GameTestHelper>, Consumer<GameTestHelper>> CURIOS_UTIL_STORAGE_REMOTE_FIRST_SLOT =
            TEST_FUNCTIONS.register("curios_util_storage_remote_first_slot", () -> CuriosUtilGameTests::storageRemoteInFirstInventorySlotIsFound);

    public static final DeferredHolder<Consumer<GameTestHelper>, Consumer<GameTestHelper>> OTHERWORLD_BLOCK_DROPS_COVERED =
            TEST_FUNCTIONS.register("otherworld_block_drops_covered", () -> OtherworldBlockGameTests::naturalBlockDropsCoveredWithoutSight);

    public static final DeferredHolder<Consumer<GameTestHelper>, Consumer<GameTestHelper>> OTHERWORLD_BLOCK_DROPS_UNCOVERED =
            TEST_FUNCTIONS.register("otherworld_block_drops_uncovered", () -> OtherworldBlockGameTests::naturalBlockDropsUncoveredWithThirdEye);

    public static final DeferredHolder<Consumer<GameTestHelper>, Consumer<GameTestHelper>> OTHERWORLD_BLOCK_DROPS_STAFF =
            TEST_FUNCTIONS.register("otherworld_block_drops_staff", () -> OtherworldBlockGameTests::naturalBlockDropsUncoveredWithStaff);

    public static final DeferredHolder<Consumer<GameTestHelper>, Consumer<GameTestHelper>> IESNIUM_DROPS_STAFF =
            TEST_FUNCTIONS.register("iesnium_drops_staff", () -> OtherworldBlockGameTests::iesniumDropsUncoveredWithStaff);

    public static final DeferredHolder<Consumer<GameTestHelper>, Consumer<GameTestHelper>> IESNIUM_DROPS_OCCUPIED_INFUSED_PICK =
            TEST_FUNCTIONS.register("iesnium_drops_occupied_infused_pick", () -> OtherworldBlockGameTests::iesniumDropsUncoveredWithOccupiedInfusedPick);

    public static final DeferredHolder<Consumer<GameTestHelper>, Consumer<GameTestHelper>> IESNIUM_DROPS_UNOCCUPIED_INFUSED_PICK =
            TEST_FUNCTIONS.register("iesnium_drops_unoccupied_infused_pick", () -> OtherworldBlockGameTests::iesniumDropsCoveredWithUnoccupiedInfusedPick);

    public static final DeferredHolder<Consumer<GameTestHelper>, Consumer<GameTestHelper>> FAMILIAR_RING_CAPTURES_FAMILIAR =
            TEST_FUNCTIONS.register("familiar_ring_captures_familiar", () -> FamiliarRingGameTests::ringCapturesFamiliar);

    public static final DeferredHolder<Consumer<GameTestHelper>, Consumer<GameTestHelper>> FAMILIAR_RING_RELEASES_FAMILIAR =
            TEST_FUNCTIONS.register("familiar_ring_releases_familiar", () -> FamiliarRingGameTests::ringReleasesFamiliar);

    public static void onRegisterGameTests(RegisterGameTestsEvent event) {
        var environment = event.registerEnvironment(Identifier.fromNamespaceAndPath(Occultism.MODID, "map_item_resource_handler"));
        var curiosEnvironment = event.registerEnvironment(Identifier.fromNamespaceAndPath(Occultism.MODID, "curios_util"));
        var otherworldEnvironment = event.registerEnvironment(Identifier.fromNamespaceAndPath(Occultism.MODID, "otherworld_blocks"));
        var structure = Identifier.fromNamespaceAndPath(Occultism.MODID, "map_item_resource_handler_test");

        registerTest(event, MAP_ITEM_RESOURCE_HANDLER_ABORTED_PROBES, environment, structure, 40, 0);
        registerTest(event, MAP_ITEM_RESOURCE_HANDLER_COMMITTED_EXTRACTION, environment, structure, 40, 0);
        registerTest(event, MAP_ITEM_RESOURCE_HANDLER_NESTED_ABORT, environment, structure, 40, 0);
        registerTest(event, MAP_ITEM_RESOURCE_HANDLER_NESTED_COMMIT, environment, structure, 40, 0);
        registerTest(event, MAP_ITEM_RESOURCE_HANDLER_SLOT_REUSE_ROLLBACK, environment, structure, 40, 0);
        registerTest(event, MAP_ITEM_RESOURCE_HANDLER_UNDO_JOURNAL_DRAINS, environment, structure, 40, 0);
        registerTest(event, MAP_ITEM_RESOURCE_HANDLER_RECURSIVE_COMMIT_NOTIFICATION, environment, structure, 40, 0);
        registerTest(event, CURIOS_UTIL_STORAGE_REMOTE_FIRST_SLOT, curiosEnvironment, structure, 40, 0);
        registerTest(event, OTHERWORLD_BLOCK_DROPS_COVERED, otherworldEnvironment, structure, 40, 0);
        registerTest(event, OTHERWORLD_BLOCK_DROPS_UNCOVERED, otherworldEnvironment, structure, 40, 0);
        registerTest(event, OTHERWORLD_BLOCK_DROPS_STAFF, otherworldEnvironment, structure, 40, 0);
        registerTest(event, IESNIUM_DROPS_STAFF, otherworldEnvironment, structure, 40, 0);
        registerTest(event, IESNIUM_DROPS_OCCUPIED_INFUSED_PICK, otherworldEnvironment, structure, 40, 0);
        registerTest(event, IESNIUM_DROPS_UNOCCUPIED_INFUSED_PICK, otherworldEnvironment, structure, 40, 0);

        var familiarEnvironment = event.registerEnvironment(Identifier.fromNamespaceAndPath(Occultism.MODID, "familiar_ring"));
        registerTest(event, FAMILIAR_RING_CAPTURES_FAMILIAR, familiarEnvironment, structure, 40, 0);
        registerTest(event, FAMILIAR_RING_RELEASES_FAMILIAR, familiarEnvironment, structure, 40, 0);
    }

    private static void registerTest(
            RegisterGameTestsEvent event,
            DeferredHolder<Consumer<GameTestHelper>, Consumer<GameTestHelper>> test,
            Holder<TestEnvironmentDefinition<?>> environment,
            Identifier structure,
            int maxTicks,
            int setupTicks
    ) {
        var testData = new TestData<>(environment, structure, maxTicks, setupTicks, true);
        event.registerTest(test.getId(), new FunctionGameTestInstance(test.key(), testData));
    }
}
