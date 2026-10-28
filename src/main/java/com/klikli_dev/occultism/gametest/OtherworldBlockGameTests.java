// SPDX-FileCopyrightText: 2026 klikli-dev
//
// SPDX-License-Identifier: MIT

package com.klikli_dev.occultism.gametest;

import com.klikli_dev.occultism.registry.OccultismBlocks;
import com.klikli_dev.occultism.registry.OccultismDataComponents;
import com.klikli_dev.occultism.registry.OccultismEffects;
import com.klikli_dev.occultism.registry.OccultismItems;
import net.minecraft.core.BlockPos;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.block.Blocks;

public class OtherworldBlockGameTests {

    private static final BlockPos TEST_POS = new BlockPos(1, 2, 1);

    public static void naturalBlockDropsCoveredWithoutSight(GameTestHelper helper) {
        helper.setBlock(TEST_POS, OccultismBlocks.OTHERSTONE_NATURAL.get());
        helper.runAfterDelay(2, () -> {
            var player = (ServerPlayer) helper.makeMockServerPlayer(GameType.SURVIVAL);
            player.gameMode.destroyBlock(helper.absolutePos(TEST_POS));
            helper.assertTrue(helper.getBlockState(TEST_POS).isAir(), "otherstone natural should be broken");
        });
        helper.runAfterDelay(10, () -> {
            helper.assertItemEntityCountIs(Blocks.ANDESITE.asItem(), TEST_POS, 3.0, 1);
            helper.succeed();
        });
    }

    public static void naturalBlockDropsUncoveredWithThirdEye(GameTestHelper helper) {
        helper.setBlock(TEST_POS, OccultismBlocks.OTHERSTONE_NATURAL.get());
        helper.runAfterDelay(2, () -> {
            //needs a connected player, effects are synced to the client
            var player = MockPlayerHelper.makeConnectedMockServerPlayer(helper, GameType.SURVIVAL);
            player.addEffect(new MobEffectInstance(OccultismEffects.THIRD_EYE, 200, 0));
            player.gameMode.destroyBlock(helper.absolutePos(TEST_POS));
            helper.assertTrue(helper.getBlockState(TEST_POS).isAir(), "otherstone natural should be broken");
        });
        helper.runAfterDelay(10, () -> {
            helper.assertItemEntityCountIs(OccultismBlocks.OTHERSTONE.get().asItem(), TEST_POS, 3.0, 1);
            helper.succeed();
        });
    }

    public static void naturalBlockDropsUncoveredWithStaff(GameTestHelper helper) {
        helper.setBlock(TEST_POS, OccultismBlocks.OTHERSTONE_NATURAL.get());
        helper.runAfterDelay(2, () -> {
            var player = MockPlayerHelper.makeConnectedMockServerPlayer(helper, GameType.SURVIVAL);
            player.setItemInHand(InteractionHand.OFF_HAND, new ItemStack(OccultismItems.TRUE_SIGHT_STAFF.get()));
            player.setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(Items.DIAMOND_PICKAXE));
            player.gameMode.destroyBlock(helper.absolutePos(TEST_POS));
            helper.assertTrue(helper.getBlockState(TEST_POS).isAir(), "otherstone natural should be broken");
        });
        helper.runAfterDelay(10, () -> {
            helper.assertItemEntityCountIs(OccultismBlocks.OTHERSTONE.get().asItem(), TEST_POS, 3.0, 1);
            helper.succeed();
        });
    }

    public static void iesniumDropsUncoveredWithStaff(GameTestHelper helper) {
        helper.setBlock(TEST_POS, OccultismBlocks.IESNIUM_ORE_NATURAL.get());
        helper.runAfterDelay(2, () -> {
            var player = MockPlayerHelper.makeConnectedMockServerPlayer(helper, GameType.SURVIVAL);
            player.setItemInHand(InteractionHand.OFF_HAND, new ItemStack(OccultismItems.TRUE_SIGHT_STAFF.get()));
            player.setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(Items.DIAMOND_PICKAXE));
            player.gameMode.destroyBlock(helper.absolutePos(TEST_POS));
            helper.assertTrue(helper.getBlockState(TEST_POS).isAir(), "iesnium ore natural should be broken");
        });
        helper.runAfterDelay(10, () -> {
            helper.assertItemEntityCountIs(OccultismItems.RAW_IESNIUM.get(), TEST_POS, 3.0, 1);
            helper.succeed();
        });
    }

    public static void iesniumDropsUncoveredWithOccupiedInfusedPick(GameTestHelper helper) {
        helper.setBlock(TEST_POS, OccultismBlocks.IESNIUM_ORE_NATURAL.get());
        helper.runAfterDelay(2, () -> {
            var player = MockPlayerHelper.makeConnectedMockServerPlayer(helper, GameType.SURVIVAL);
            var pick = new ItemStack(OccultismItems.INFUSED_PICKAXE.get());
            pick.set(OccultismDataComponents.OCCUPIED, true);
            player.setItemInHand(InteractionHand.MAIN_HAND, pick);
            player.gameMode.destroyBlock(helper.absolutePos(TEST_POS));
            helper.assertTrue(helper.getBlockState(TEST_POS).isAir(), "iesnium ore natural should be broken");
        });
        helper.runAfterDelay(10, () -> {
            helper.assertItemEntityCountIs(OccultismItems.RAW_IESNIUM.get(), TEST_POS, 3.0, 1);
            helper.succeed();
        });
    }

    public static void iesniumDropsCoveredWithUnoccupiedInfusedPick(GameTestHelper helper) {
        helper.setBlock(TEST_POS, OccultismBlocks.IESNIUM_ORE_NATURAL.get());
        helper.runAfterDelay(2, () -> {
            var player = MockPlayerHelper.makeConnectedMockServerPlayer(helper, GameType.SURVIVAL);
            player.setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(OccultismItems.INFUSED_PICKAXE.get()));
            player.gameMode.destroyBlock(helper.absolutePos(TEST_POS));
            helper.assertTrue(helper.getBlockState(TEST_POS).isAir(), "iesnium ore natural should be broken");
        });
        helper.runAfterDelay(10, () -> {
            helper.assertItemEntityCountIs(Blocks.NETHERRACK.asItem(), TEST_POS, 3.0, 1);
            helper.succeed();
        });
    }
}
