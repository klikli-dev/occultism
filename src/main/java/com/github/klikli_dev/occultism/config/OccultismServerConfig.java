/*
 * MIT License
 *
 * Copyright 2020 klikli-dev
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
 * associated documentation files (the "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
 * of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following
 * conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial
 * portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR
 * PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT
 * OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
 * OTHER DEALINGS IN THE SOFTWARE.
 */

package com.github.klikli_dev.occultism.config;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.BooleanValue;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;
import net.minecraftforge.common.ForgeConfigSpec.DoubleValue;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class OccultismServerConfig {

    public final StorageSettings storage;
    public final SpiritJobSettings spiritJobs;

    public final RitualSettings rituals;
    public final DimensionalMineshaftSettings dimensionalMineshaft;
    public final ItemSettings itemSettings;
    public final ForgeConfigSpec spec;

    public OccultismServerConfig() {
        ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();
        this.storage = new StorageSettings(builder);
        this.spiritJobs = new SpiritJobSettings(builder);
        this.rituals = new RitualSettings(builder);
        this.dimensionalMineshaft = new DimensionalMineshaftSettings(builder);
        this.itemSettings = new ItemSettings(builder);
        this.spec = builder.build();
    }

    public static class ItemSettings {
        public final ConfigValue<List<String>> soulgemEntityTypeDenyList;

        public ItemSettings(ForgeConfigSpec.Builder builder) {
            builder.comment("Item Settings").push("items");

            List<String> defaultSoulgemEntityDenyList =
                    Stream.of("minecraft:wither")
                            .collect(Collectors.toList());
            this.soulgemEntityTypeDenyList =
                    builder.comment("Entity types that cannot be captured in a soul gem. Specify by their full id, e.g \"minecraft:zombie\"")
                            .define("soulgemEntityDenyList", defaultSoulgemEntityDenyList);

            builder.pop();
        }
    }

    public static class SpiritJobSettings {
        public final ConfigValue<Double> tier1CrusherTimeMultiplier;
        public final ConfigValue<Double> tier2CrusherTimeMultiplier;
        public final ConfigValue<Double> tier3CrusherTimeMultiplier;
        public final ConfigValue<Double> tier4CrusherTimeMultiplier;
        public final ConfigValue<Double> tier1CrusherOutputMultiplier;
        public final ConfigValue<Double> tier2CrusherOutputMultiplier;
        public final ConfigValue<Double> tier3CrusherOutputMultiplier;
        public final ConfigValue<Double> tier4CrusherOutputMultiplier;
        public final ConfigValue<Integer> drikwingFamiliarSlowFallingSeconds;
        public final ConfigValue<Integer> crusherResultPickupDelay;
        public final ConfigValue<Integer> blacksmithFamiliarUpgradeCost;
        public final ConfigValue<Integer> blacksmithFamiliarUpgradeCooldown;
        public final ConfigValue<Double> blacksmithFamiliarRepairChance;

        public SpiritJobSettings(ForgeConfigSpec.Builder builder) {
            builder.comment("Spirit Job Settings").push("spirit_job");
            this.drikwingFamiliarSlowFallingSeconds =
                    builder.comment(
                                    "The duration for the slow falling effect applied by a drikwing.")
                            .define("drikwingFamiliarSlowFallingSeconds", 15);

            this.tier1CrusherTimeMultiplier =
                    builder.comment(
                                    "The multiplier to each crushing recipe's crushing_time for Tier 1 (Foliot) Crusher Spirits.")
                            .define("tier1CrusherTimeMultiplier", 2.0);
            this.tier2CrusherTimeMultiplier =
                    builder.comment(
                                    "The multiplier to each crushing recipe's crushing_time for Tier 2 (Djinni) Crusher Spirits.")
                            .define("tier2CrusherTimeMultiplier", 1.0);
            this.tier3CrusherTimeMultiplier =
                    builder.comment(
                                    "Currently unused. The multiplier to each crushing recipe's crushing_time for Tier 3 (Afrit) Crusher Spirits.")
                            .define("tier3CrusherTimeMultiplier", 0.5);
            this.tier4CrusherTimeMultiplier =
                    builder.comment(
                                    "Currently unused. The multiplier to each crushing recipe's crushing_time for Tier 4 (Marid) Crusher Spirits.")
                            .define("tier4CrusherTimeMultiplier", 0.2);

            this.tier1CrusherOutputMultiplier =
                    builder.comment(
                                    "The multiplier to each crushing recipe's output count for Tier 1 (Foliot) Crusher Spirits.")
                            .define("tier1CrusherOutputMultiplier", 1.0);
            this.tier2CrusherOutputMultiplier =
                    builder.comment(
                                    "The multiplier to each crushing recipe's output count for Tier 2 (Djinni) Crusher Spirits.")
                            .define("tier2CrusherOutputMultiplier", 1.5);
            this.tier3CrusherOutputMultiplier =
                    builder.comment(
                                    "The multiplier to each crushing recipe's output count for Tier 3 (Afrit) Crusher Spirits.")
                            .define("tier3CrusherOutputMultiplier", 2.0);
            this.tier4CrusherOutputMultiplier =
                    builder.comment(
                                    "The multiplier to each crushing recipe's output count for Tier 4 (Marid) Crusher Spirits.")
                            .define("tier4CrusherOutputMultiplier", 3.0);

            this.crusherResultPickupDelay =
                    builder.comment(
                                    "The minimum ticks before a crusher can pick up an item it dropped. Default is 3 Seconds = 3 * 20 Ticks.")
                            .define("crusherResultPickupDelay", 20 * 3);

            this.blacksmithFamiliarRepairChance =
                    builder.comment(
                                    "The chance for a blacksmith familiar to repair an item (by 2 durability) whenever stone is picked up. 1.0 = 100%, 0.0 = 0%.")
                            .define("blacksmithFamiliarRepairChance", 0.05);
            this.blacksmithFamiliarUpgradeCost =
                    builder.comment(
                                    "The amount of iron required for a blacksmith familiar to upgrade another familiar.")
                            .define("blacksmithFamiliarUpgradeCost", 18);
            this.blacksmithFamiliarUpgradeCooldown =
                    builder.comment(
                                    "The cooldown for a blacksmith familiar to upgrade another familiar.")
                            .define("blacksmithFamiliarUpgradeCooldown", 20 * 20);


            builder.pop();
        }
    }

    public static class DimensionalMineshaftSettings {
        public final MinerSpiritSettings minerFoliotUnspecialized;
        public final MinerSpiritSettings minerDjinniOres;

        public DimensionalMineshaftSettings(ForgeConfigSpec.Builder builder) {
            builder.comment("Dimensional Mineshaft Settings").push("dimensional_mineshaft");

            this.minerFoliotUnspecialized =
                    new MinerSpiritSettings("miner_foliot_unspecialized", builder, 400, 1, 1000);

            this.minerDjinniOres =
                    new MinerSpiritSettings("miner_djinni_ores", builder, 300, 1, 400);

            builder.pop();
        }

        public static class MinerSpiritSettings {
            public final ConfigValue<Integer> maxMiningTime;
            public final ConfigValue<Integer> rollsPerOperation;
            public final ConfigValue<Integer> durability;

            public MinerSpiritSettings(String oreName, ForgeConfigSpec.Builder builder,
                                       int maxMiningTime, int rollsPerOperation, int durability) {
                builder.comment("Miner Spirit Settings").push(oreName);

                this.maxMiningTime =
                        builder.comment("The amount of time it takes the spirit to perform one mining operation.")
                                .define("maxMiningTime", maxMiningTime);
                this.rollsPerOperation =
                        builder.comment("The amount of blocks the spirit will obtain per mining operation")
                                .define("rollsPerOperation", rollsPerOperation);
                this.durability =
                        builder.comment("The amount of mining operations the spirit can perform before breaking.")
                                .define("durability", durability);

                builder.pop();
            }
        }

    }

    public static class RitualSettings {
        public final BooleanValue enableClearWeatherRitual;
        public final BooleanValue enableRainWeatherRitual;
        public final BooleanValue enableThunderWeatherRitual;
        public final BooleanValue enableDayTimeRitual;
        public final BooleanValue enableNightTimeRitual;
        public final BooleanValue enableRemainingIngredientCountMatching;
        public final DoubleValue ritualDurationMultiplier;

        public final ConfigValue<List<String>> possibleSpiritNames;

        public RitualSettings(ForgeConfigSpec.Builder builder) {
            builder.comment("Ritual Settings").push("rituals");

            this.enableClearWeatherRitual =
                    builder.comment("Enables the ritual to clear rainy weather.")
                            .define("enableClearWeatherRitual", true);
            this.enableRainWeatherRitual =
                    builder.comment("Enables the ritual to start rainy weather.")
                            .define("enableRainWeatherRitual", true);
            this.enableThunderWeatherRitual =
                    builder.comment("Enables the ritual to start a thunderstorm.")
                            .define("enableThunderWeatherRitual", true);
            this.enableDayTimeRitual =
                    builder.comment("Enables the ritual to set time to day.")
                            .define("enableDayTimeRitual", true);
            this.enableNightTimeRitual =
                    builder.comment("Enables the ritual to set time to night.")
                            .define("enableNightTimeRitual", true);
            this.enableRemainingIngredientCountMatching =
                    builder.comment(
                                    "If enabled, rituals are interrupted if *more* ingredients are present than needed. " +
                                            "This should usually be disabled, but can improve performance if " +
                                            "(very very) many rituals are running.")
                            .define("enableRemainingIngredientCountMatching", false);
            this.ritualDurationMultiplier =
                    builder.comment("Set a value below 1.0 to speed up rituals.")
                            .defineInRange("ritualDurationMultiplier", 1.0, 0.05, Double.MAX_VALUE);

            this.possibleSpiritNames =
                    builder.comment("By default spirit names are generated at random from lists of possible syllables. " +
                                    "If you instead want to specify the possible spirit names directly, configure a list of values here.")
                            .define("possibleSpiritNames", new ArrayList<String>());

            builder.pop();
        }
    }

    public static class StorageSettings {
        public final ConfigValue<Integer> stabilizerTier1Slots;
        public final ConfigValue<Integer> stabilizerTier2Slots;
        public final ConfigValue<Integer> stabilizerTier3Slots;
        public final ConfigValue<Integer> stabilizerTier4Slots;
        public final ConfigValue<Integer> controllerBaseSlots;
        public final ConfigValue<Integer> controllerStackSize;
        public final BooleanValue overrideItemStackSizes;

        public StorageSettings(ForgeConfigSpec.Builder builder) {
            builder.comment("Storage Settings").push("storage");
            this.stabilizerTier1Slots =
                    builder.comment("The amount of slots the storage stabilizer tier 1 provides.")
                            .define("stabilizerTier1Slots", 256);
            this.stabilizerTier2Slots =
                    builder.comment("The amount of slots the storage stabilizer tier 2 provides.")
                            .define("stabilizerTier2Slots", 512);
            this.stabilizerTier3Slots =
                    builder.comment("The amount of slots the storage stabilizer tier 3 provides.")
                            .define("stabilizerTier3Slots", 1024);
            this.stabilizerTier4Slots =
                    builder.comment("The amount of slots the storage stabilizer tier 4 provides.")
                            .define("stabilizerTier4Slots", 2048);
            this.controllerBaseSlots =
                    builder.comment("The amount of slots the storage actuator provides.")
                            .define("controllerBaseSlots", 128);
            this.controllerStackSize =
                    builder.comment("The stack size the storage actuator uses.")
                            .define("controllerStackSize", 1024);
            this.overrideItemStackSizes =
                    builder.comment(
                                    "True to use the configured controllerStackSize for all items, instead of the stack sizes provided by " +
                                            "item type (such as 16 for ender pearls, 64 for iron ingot). WARNING: Setting this to " +
                                            "false may have a negative impact on performance.")
                            .define("overrideItemStackSizes", true);
            builder.pop();
        }
    }
}
