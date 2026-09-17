// SPDX-FileCopyrightText: 2026 klikli-dev
//
// SPDX-License-Identifier: MIT

package com.klikli_dev.occultism.gametest;

import com.klikli_dev.occultism.registry.OccultismItems;
import com.klikli_dev.occultism.util.CuriosUtil;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameType;

public class CuriosUtilGameTests {

    public static void storageRemoteInFirstInventorySlotIsFound(GameTestHelper helper) {
        var player = helper.makeMockPlayer(GameType.SURVIVAL);
        player.getInventory().setSelectedSlot(1);
        player.getInventory().setItem(0, new ItemStack(OccultismItems.STORAGE_REMOTE.get()));

        var selected = CuriosUtil.getStorageRemote(player);

        helper.assertTrue(selected != null, "Storage remote in the first inventory slot was not found");
        helper.assertTrue(selected.selectedSlot == 0, "Expected the storage remote in slot 0 but got slot " + selected.selectedSlot);
        helper.succeed();
    }
}
