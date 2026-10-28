// SPDX-FileCopyrightText: 2026 klikli-dev
//
// SPDX-License-Identifier: MIT

package com.klikli_dev.occultism.gametest;

import com.klikli_dev.occultism.registry.OccultismEntities;
import com.klikli_dev.occultism.registry.OccultismItems;
import com.klikli_dev.occultism.util.ItemNBTUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;

public class FamiliarRingGameTests {

    private static final BlockPos FAMILIAR_POS = new BlockPos(1, 2, 1);

    public static void ringCapturesFamiliar(GameTestHelper helper) {
        var familiar = helper.spawn(OccultismEntities.GREEDY_FAMILIAR.get(), FAMILIAR_POS);
        var player = MockPlayerHelper.makeConnectedMockServerPlayer(helper, GameType.SURVIVAL);
        var ring = new ItemStack(OccultismItems.FAMILIAR_RING.get());

        var result = ring.getItem().interactLivingEntity(ring, player, familiar, InteractionHand.MAIN_HAND);

        helper.assertTrue(result.consumesAction(), "capture should succeed, got " + result);
        helper.assertTrue(ItemNBTUtil.getOccupied(ring), "ring should be occupied after capture");
        helper.assertTrue(familiar.isRemoved(), "captured familiar should be removed from the level");
        helper.succeed();
    }

    public static void ringReleasesFamiliar(GameTestHelper helper) {
        var player = MockPlayerHelper.makeConnectedMockServerPlayer(helper, GameType.SURVIVAL);
        var vec = Vec3.atBottomCenterOf(helper.absolutePos(FAMILIAR_POS));
        player.setPos(vec.x(), vec.y(), vec.z());
        var ring = new ItemStack(OccultismItems.FAMILIAR_RING.get());

        //capture first so we have something to release
        var familiar = helper.spawn(OccultismEntities.GREEDY_FAMILIAR.get(), FAMILIAR_POS);
        var capture = ring.getItem().interactLivingEntity(ring, player, familiar, InteractionHand.MAIN_HAND);
        helper.assertTrue(capture.consumesAction(), "setup capture should succeed");

        //release via block use with the ring in hand
        player.setItemInHand(InteractionHand.MAIN_HAND, ring);
        helper.setBlock(new BlockPos(1, 1, 1), Blocks.STONE);
        helper.useBlock(new BlockPos(1, 1, 1), player);

        helper.assertTrue(!ItemNBTUtil.getOccupied(ring), "ring should no longer be occupied after release");
        helper.assertTrue(helper.findEntities(OccultismEntities.GREEDY_FAMILIAR.get(), 1, 2, 1, 5.0).size() == 1,
                "released familiar should be back in the level");
        helper.succeed();
    }
}
