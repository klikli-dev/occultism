// SPDX-FileCopyrightText: 2026 klikli-dev
//
// SPDX-License-Identifier: MIT

package com.klikli_dev.occultism.gametest;

import com.klikli_dev.occultism.Occultism;
import com.klikli_dev.occultism.common.level.ConfigEnabledFilter;
import com.klikli_dev.occultism.common.level.ConfigEnabledFilter.Setting;
import com.klikli_dev.occultism.datagen.worldgen.PlacedFeatures;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementContext;
import net.neoforged.neoforge.common.ModConfigSpec.BooleanValue;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Tests for the world generation toggles in the server config.
 * These do not generate terrain, they check that each placed feature is gated by its config switch and that the
 * switch actually stops the feature from being placed.
 */
public class WorldGenConfigGameTests {

    private static Map<Setting, ResourceKey<PlacedFeature>> gatedFeatures() {
        return Map.of(
                Setting.SILVER_ORE, PlacedFeatures.ORE_SILVER,
                Setting.DEEPSLATE_SILVER_ORE, PlacedFeatures.ORE_SILVER_DEEPSLATE,
                Setting.IESNIUM_ORE, PlacedFeatures.ORE_IESNIUM,
                Setting.UNDERGROUND_GROVE, PlacedFeatures.GROVE_UNDERGROUND
        );
    }

    /**
     * Every occultism placed feature must start with the config filter for its setting, otherwise the toggle does nothing.
     */
    public static void placedFeaturesAreGatedByConfig(GameTestHelper helper) {
        var placedFeatures = helper.getLevel().registryAccess().lookupOrThrow(Registries.PLACED_FEATURE);

        var expected = new HashMap<>(Map.of(
                PlacedFeatures.TREE_OTHERWORLD, Setting.OTHERWORLD_TREES,
                PlacedFeatures.TREE_OTHERWORLD_NATURAL, Setting.OTHERWORLD_TREES
        ));
        gatedFeatures().forEach((setting, key) -> expected.put(key, setting));

        expected.forEach((key, setting) -> {
            var placement = placedFeatures.getOrThrow(key).value().placement();
            helper.assertTrue(!placement.isEmpty() && placement.getFirst() instanceof ConfigEnabledFilter filter && filter.getSetting() == setting,
                    "Placed feature " + key.identifier() + " must start with the config filter for " + setting.getSerializedName() + ", but has " + placement);
        });

        helper.succeed();
    }

    /**
     * Turning a setting off must stop placement, turning it on must allow it again.
     */
    public static void configFilterRespectsSettings(GameTestHelper helper) {
        var level = helper.getLevel();
        var context = new PlacementContext(level, level.getChunkSource().getGenerator(), Optional.empty());
        var random = RandomSource.create(0);
        var pos = helper.absolutePos(BlockPos.ZERO);

        Map<Setting, Boolean> originalValues = new EnumMap<>(Setting.class);
        for (Setting setting : Setting.values())
            originalValues.put(setting, setting.getValue(Occultism.SERVER_CONFIG.worldGen).get());

        try {
            for (Setting setting : Setting.values()) {
                BooleanValue value = setting.getValue(Occultism.SERVER_CONFIG.worldGen);
                var filter = ConfigEnabledFilter.of(setting);

                value.set(false);
                helper.assertTrue(filter.getPositions(context, random, pos).findAny().isEmpty(),
                        setting.getSerializedName() + " is disabled, but the filter still allowed placement");

                value.set(true);
                helper.assertTrue(filter.getPositions(context, random, pos).findAny().isPresent(),
                        setting.getSerializedName() + " is enabled, but the filter prevented placement");
            }
        } finally {
            originalValues.forEach((setting, original) -> setting.getValue(Occultism.SERVER_CONFIG.worldGen).set(original));
        }

        helper.succeed();
    }
}
