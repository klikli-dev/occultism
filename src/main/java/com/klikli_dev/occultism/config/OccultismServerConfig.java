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

package com.klikli_dev.occultism.config;

import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.neoforge.common.ModConfigSpec.*;

public class OccultismServerConfig {

    public final StorageSettings storage;
    public final SpiritJobSettings spiritJobs;

    public final RitualSettings rituals;
    public final DimensionalMineshaftSettings dimensionalMineshaft;
    public final ItemSettings itemSettings;
    public final ModConfigSpec spec;

    public OccultismServerConfig() {
        ModConfigSpec.Builder builder = new ModConfigSpec.Builder();
        this.storage = new StorageSettings(builder);
        this.spiritJobs = new SpiritJobSettings(builder);
        this.rituals = new RitualSettings(builder);
        this.dimensionalMineshaft = new DimensionalMineshaftSettings(builder);
        this.itemSettings = new ItemSettings(builder);
        this.spec = builder.build();
    }

    public static class ItemSettings {
        public final BooleanValue anyOreDivinationRod;
        public ItemSettings(ModConfigSpec.Builder builder) {
            builder.comment("Item Settings").push("items");
            this.anyOreDivinationRod =
                    builder.comment(
                            "Allow the Divining Rod to attune to any ore"
                    )
                            .define("anyOreDivinationRod", false);
            builder.pop();
        }
    }

    public static class SpiritJobSettings {
        public final DoubleValue tier1CrusherTimeMultiplier;
        public final DoubleValue tier2CrusherTimeMultiplier;
        public final DoubleValue tier3CrusherTimeMultiplier;
        public final DoubleValue tier4CrusherTimeMultiplier;
        public final DoubleValue tier1CrusherOutputMultiplier;
        public final DoubleValue tier2CrusherOutputMultiplier;
        public final DoubleValue tier3CrusherOutputMultiplier;
        public final DoubleValue tier4CrusherOutputMultiplier;
        public final IntValue drikwingFamiliarSlowFallingSeconds;
        public final IntValue crusherResultPickupDelay;
        public final IntValue blacksmithFamiliarUpgradeCost;
        public final IntValue blacksmithFamiliarUpgradeCooldown;
        public final DoubleValue blacksmithFamiliarRepairChance;

        public SpiritJobSettings(ModConfigSpec.Builder builder) {
            builder.comment("Spirit Job Settings").push("spirit_job");
            this.drikwingFamiliarSlowFallingSeconds =
                    builder.comment(
                                    "The duration for the slow falling effect applied by a drikwing.")
                            .defineInRange("drikwingFamiliarSlowFallingSeconds", 15, 0, Integer.MAX_VALUE);

            this.tier1CrusherTimeMultiplier =
                    builder.comment(
                                    "The multiplier to each crushing recipe's crushing_time for Tier 1 (Foliot) Crusher Spirits.")
                            .defineInRange("tier1CrusherTimeMultiplier", 2.0, 0.0, Double.MAX_VALUE);
            this.tier2CrusherTimeMultiplier =
                    builder.comment(
                                    "The multiplier to each crushing recipe's crushing_time for Tier 2 (Djinni) Crusher Spirits.")
                            .defineInRange("tier2CrusherTimeMultiplier", 1.0, 0.0, Double.MAX_VALUE);
            this.tier3CrusherTimeMultiplier =
                    builder.comment(
                                    "Currently unused. The multiplier to each crushing recipe's crushing_time for Tier 3 (Afrit) Crusher Spirits.")
                            .defineInRange("tier3CrusherTimeMultiplier", 0.5, 0.0, Double.MAX_VALUE);
            this.tier4CrusherTimeMultiplier =
                    builder.comment(
                                    "Currently unused. The multiplier to each crushing recipe's crushing_time for Tier 4 (Marid) Crusher Spirits.")
                            .defineInRange("tier4CrusherTimeMultiplier", 0.2, 0.0, Double.MAX_VALUE);

            this.tier1CrusherOutputMultiplier =
                    builder.comment(
                                    "The multiplier to each crushing recipe's output count for Tier 1 (Foliot) Crusher Spirits.")
                            .defineInRange("tier1CrusherOutputMultiplier", 1.0, 0.0, Double.MAX_VALUE);
            this.tier2CrusherOutputMultiplier =
                    builder.comment(
                                    "The multiplier to each crushing recipe's output count for Tier 2 (Djinni) Crusher Spirits.")
                            .defineInRange("tier2CrusherOutputMultiplier", 1.5, 0.0, Double.MAX_VALUE);
            this.tier3CrusherOutputMultiplier =
                    builder.comment(
                                    "The multiplier to each crushing recipe's output count for Tier 3 (Afrit) Crusher Spirits.")
                            .defineInRange("tier3CrusherOutputMultiplier", 2.0, 0.0, Double.MAX_VALUE);
            this.tier4CrusherOutputMultiplier =
                    builder.comment(
                                    "The multiplier to each crushing recipe's output count for Tier 4 (Marid) Crusher Spirits.")
                            .defineInRange("tier4CrusherOutputMultiplier", 3.0, 0.0, Double.MAX_VALUE);

            this.crusherResultPickupDelay =
                    builder.comment(
                                    "The minimum ticks before a crusher can pick up an item it dropped. Default is 3 Seconds = 3 * 20 Ticks.")
                            .defineInRange("crusherResultPickupDelay", 20 * 3, 0, Integer.MAX_VALUE);

            this.blacksmithFamiliarRepairChance =
                    builder.comment(
                                    "The chance for a blacksmith familiar to repair an item (by 2 durability) whenever stone is picked up. 1.0 = 100%, 0.0 = 0%.")
                            .defineInRange("blacksmithFamiliarRepairChance", 0.05, 0.0, Double.MAX_VALUE);
            this.blacksmithFamiliarUpgradeCost =
                    builder.comment(
                                    "The amount of iron required for a blacksmith familiar to upgrade another familiar.")
                            .defineInRange("blacksmithFamiliarUpgradeCost", 18, 0, Integer.MAX_VALUE);
            this.blacksmithFamiliarUpgradeCooldown =
                    builder.comment(
                                    "The cooldown for a blacksmith familiar to upgrade another familiar.")
                            .defineInRange("blacksmithFamiliarUpgradeCooldown", 20 * 20, 0, Integer.MAX_VALUE);


            builder.pop();
        }
    }

    public static class DimensionalMineshaftSettings {
        public final MinerSpiritSettings minerFoliotUnspecialized;
        public final MinerSpiritSettings minerDjinniOres;
        public final MinerSpiritSettings minerAfritDeeps;
        public final MinerSpiritSettings minerMaridMaster;

        public DimensionalMineshaftSettings(ModConfigSpec.Builder builder) {
            builder.comment("Dimensional Mineshaft Settings").push("dimensional_mineshaft");

            this.minerFoliotUnspecialized =
                    new MinerSpiritSettings("miner_foliot_unspecialized", builder, 400, 1, 1000);

            this.minerDjinniOres =
                    new MinerSpiritSettings("miner_djinni_ores", builder, 300, 1, 400);

            this.minerAfritDeeps =
                    new MinerSpiritSettings("miner_afrit_deeps", builder, 200, 1, 800);

            this.minerMaridMaster =
                    new MinerSpiritSettings("miner_marid_master", builder, 100, 1, 1600);

            builder.pop();
        }

        public static class MinerSpiritSettings {
            public final IntValue maxMiningTime;
            public final IntValue rollsPerOperation;
            public final IntValue durability;

            public MinerSpiritSettings(String oreName, ModConfigSpec.Builder builder,
                                       int maxMiningTime, int rollsPerOperation, int durability) {
                builder.comment("Miner Spirit Settings").push(oreName);

                this.maxMiningTime =
                        builder.comment("The amount of time it takes the spirit to perform one mining operation.")
                                .defineInRange("maxMiningTime", maxMiningTime, 0, Integer.MAX_VALUE);
                this.rollsPerOperation =
                        builder.comment("The amount of blocks the spirit will obtain per mining operation")
                                .defineInRange("rollsPerOperation", rollsPerOperation, 0, Integer.MAX_VALUE);
                this.durability =
                        builder.comment("The amount of mining operations the spirit can perform before breaking.")
                                .defineInRange("durability", durability, 0, Integer.MAX_VALUE);

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


        public RitualSettings(ModConfigSpec.Builder builder) {
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

            builder.pop();
        }
    }

    public static class StorageSettings {
        public final IntValue stabilizerTier1AdditionalMaxItemTypes;
        public final LongValue stabilizerTier1AdditionalMaxTotalItemCount;
        public final IntValue stabilizerTier2AdditionalMaxItemTypes;

        public final LongValue stabilizerTier2AdditionalMaxTotalItemCount;
        public final IntValue stabilizerTier3AdditionalMaxItemTypes;

        public final LongValue stabilizerTier3AdditionalMaxTotalItemCount;
        public final IntValue stabilizerTier4AdditionalMaxItemTypes;

        public final LongValue stabilizerTier4AdditionalMaxTotalItemCount;
        public final IntValue controllerMaxItemTypes;
        public final LongValue controllerMaxTotalItemCount;
        public final BooleanValue unlinkWormholeOnBreak;

        public StorageSettings(ModConfigSpec.Builder builder) {
            builder.comment("Storage Settings").push("storage");
            this.stabilizerTier1AdditionalMaxItemTypes =
                    builder.comment("The amount of additional slots the storage stabilizer tier 1 provides. 1 Slot holds one item type.")
                            .defineInRange("stabilizerTier1AdditionalMaxItemTypes", 64, 0, Integer.MAX_VALUE);
            this.stabilizerTier1AdditionalMaxTotalItemCount =
                    builder.comment("The amount by which the stabilizer increases the maximum total item count the controller can hold. This is not per slot but the total amount of all items combined.")
                            .defineInRange("stabilizerTier1AdditionalMaxTotalItemCount", 512 * 1000L, 0, Integer.MAX_VALUE);
            this.stabilizerTier2AdditionalMaxItemTypes =
                    builder.comment("The amount of slots the storage stabilizer tier 2 provides.")
                            .defineInRange("stabilizerTier2AdditionalMaxItemTypes", 128, 0, Integer.MAX_VALUE);
            this.stabilizerTier2AdditionalMaxTotalItemCount =
                    builder.comment("The amount by which the stabilizer increases the maximum total item count the controller can hold. This is not per slot but the total amount of all items combined.")
                            .defineInRange("stabilizerTier2AdditionalMaxTotalItemCount", 1024 * 1000L, 0, Integer.MAX_VALUE);
            this.stabilizerTier3AdditionalMaxItemTypes =
                    builder.comment("The amount of slots the storage stabilizer tier 3 provides.")
                            .defineInRange("stabilizerTier3AdditionalMaxItemTypes", 256, 0, Integer.MAX_VALUE);
            this.stabilizerTier3AdditionalMaxTotalItemCount =
                    builder.comment("The amount by which the stabilizer increases the maximum total item count the controller can hold. This is not per slot but the total amount of all items combined.")
                            .defineInRange("stabilizerTier3AdditionalMaxTotalItemCount", 2048 * 1000L, 0, Integer.MAX_VALUE);
            this.stabilizerTier4AdditionalMaxItemTypes =
                    builder.comment("The amount of slots the storage stabilizer tier 4 provides.")
                            .defineInRange("stabilizerTier4AdditionalMaxItemTypes", 512, 0, Integer.MAX_VALUE);
            this.stabilizerTier4AdditionalMaxTotalItemCount =
                    builder.comment("The amount by which the stabilizer increases the maximum total item count the controller can hold. This is not per slot but the total amount of all items combined.")
                            .defineInRange("stabilizerTier4AdditionalMaxTotalItemCount", 4096 * 1000L, 0, Long.MAX_VALUE);
            this.controllerMaxItemTypes =
                    builder.comment("The amount of slots the storage actuator provides.")
                            .defineInRange("controllerMaxItemTypes", 128, 0, Integer.MAX_VALUE);
            this.controllerMaxTotalItemCount =
                    builder.comment("The stack size the storage actuator uses.")
                            .defineInRange("controllerMaxTotalItemCount", 256 * 1000L, 0, Long.MAX_VALUE);

            this.unlinkWormholeOnBreak =
                    builder.comment(
                                    "True to use the configured controllerStackSize for all items, instead of the stack sizes provided by " +
                                            "item type (such as 16 for ender pearls, 64 for iron ingot). WARNING: Setting this to " +
                                            "false may have a negative impact on performance.")
                            .define("unlinkWormholeOnBreak", false);
            builder.pop();
        }
    }
}
