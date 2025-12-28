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
import net.neoforged.neoforge.common.ModConfigSpec.BooleanValue;
import net.neoforged.neoforge.common.ModConfigSpec.DoubleValue;
import net.neoforged.neoforge.common.ModConfigSpec.IntValue;
import net.neoforged.neoforge.common.ModConfigSpec.LongValue;

public class OccultismServerConfig {

    public final StorageSettings storage;
    public final SpiritJobSettings spiritJobs;
    public final FamiliarSettings familiar;

    public final RitualSettings rituals;
    public final ItemSettings itemSettings;
    public final ModConfigSpec spec;

    public OccultismServerConfig() {
        ModConfigSpec.Builder builder = new ModConfigSpec.Builder();
        this.storage = new StorageSettings(builder);
        this.spiritJobs = new SpiritJobSettings(builder);
        this.familiar = new FamiliarSettings(builder);
        this.rituals = new RitualSettings(builder);
        this.itemSettings = new ItemSettings(builder);
        this.spec = builder.build();
    }

    public static class ItemSettings {
        public final BooleanValue anyOreDivinationRod;
        public final BooleanValue minerOutputBeforeBreak;
        public final BooleanValue minerEfficiency;
        public final BooleanValue minerFortune;
        public final BooleanValue minerSilk;
        public final BooleanValue unbreakableChalks;
        public final IntValue maxDistanceRTP;
        public final IntValue maxTryRTP;

        public ItemSettings(ModConfigSpec.Builder builder) {
            builder.comment("Item Settings").push("items");

            this.anyOreDivinationRod =
                    builder.comment(
                                    "Allow the Divining Rod to attune to any ore"
                            )
                            .define("anyOreDivinationRod", false);
            this.minerOutputBeforeBreak =
                    builder.comment(
                                    "Miners head to the output in the Dimensional Mineshaft before it breaks"
                            )
                            .define("minerOutputBeforeBreak", false);
            this.minerEfficiency =
                    builder.comment(
                                    "Allow miners enchanted with efficiency mine faster"
                            )
                            .define("minerEfficiency", true);
            this.minerFortune =
                    builder.comment(
                                    "Allow miners enchanted with fortune to has a chance of mine extra results each operation"
                            )
                            .define("minerFortune", true);
            this.minerSilk =
                    builder.comment(
                                    "Allow miners enchanted with silk touch to has a chance of multiply results each operation"
                            )
                            .define("minerSilk", true);
            this.unbreakableChalks =
                    builder.comment(
                                    "Don't damage chalks on use"
                            )
                            .define("unbreakableChalks", false);
            this.maxDistanceRTP =
                    builder.comment(
                                    "Maximum distance the Entity Wormhole random teleport (RTP) feature can reach."
                            )
                            .defineInRange("maxDistanceRTP", 4096, 0, Integer.MAX_VALUE);
            this.maxTryRTP =
                    builder.comment(
                                    "Maximum number of attempts to find a safe place for the RTP."
                            )
                            .defineInRange("maxTryRTP", 99, 0, Integer.MAX_VALUE);

            builder.pop();
        }

    }

    public static class SpiritJobSettings {
        public final TierSpiritSettings crusherFoliot;
        public final TierSpiritSettings crusherDjinni;
        public final TierSpiritSettings crusherAfrit;
        public final TierSpiritSettings crusherMarid;
        public final IntValue crusherResultPickupDelay;

        public final TierSpiritSettings crystallizerFoliot;
        public final TierSpiritSettings crystallizerDjinni;
        public final TierSpiritSettings crystallizerAfrit;
        public final TierSpiritSettings crystallizerMarid;
        public final IntValue crystallizerResultPickupDelay;


        public final SimpleWorkerSpiritSettings smelterFoliot;
        public final SimpleWorkerSpiritSettings smelterDjinni;
        public final SimpleWorkerSpiritSettings smelterAfrit;
        public final SimpleWorkerSpiritSettings smelterMarid;
        public final IntValue smelterResultPickupDelay;

        public final TraderSpiritSettings traderSapling;
        public final TraderSpiritSettings traderOtherstone;
        public final TraderSpiritSettings traderOtherrock;
        public final TraderSpiritSettings traderGem;
        public final IntValue traderResultPickupDelay;

        public final IntValue dayTimeToCast;
        public final IntValue nightTimeToCast;
        public final IntValue rainTimeToCast;
        public final IntValue thunderTimeToCast;
        public final IntValue clearWeatherTimeToCast;

        public SpiritJobSettings(ModConfigSpec.Builder builder) {
            builder.comment("Spirit Job Settings").push("spirit_job");

            this.crusherFoliot = new TierSpiritSettings(builder, "Foliot Crusher", "crusher_tier1",
                    1, 2.0, 1.0, 1);
            this.crusherDjinni = new TierSpiritSettings(builder, "Djinni Crusher", "crusher_tier2",
                    2, 1.0, 1.5, 1);
            this.crusherAfrit = new TierSpiritSettings(builder, "Afrit Crusher", "crusher_tier3",
                    3, 0.5, 2.0, 1);
            this.crusherMarid = new TierSpiritSettings(builder, "Marid Crusher", "crusher_tier4",
                    4, 0.3, 3.0, 1);
            this.crusherResultPickupDelay = builder.comment(
                    "The minimum ticks before a crusher can pick up an item it dropped. Default is 3 Seconds = 3 * 20 Ticks.")
                    .defineInRange("crusherResultPickupDelay", 20 * 3, 0, Integer.MAX_VALUE);

            this.crystallizerFoliot = new TierSpiritSettings(builder, "Foliot Crystallizer", "crystal_tier1",
                    1, 1.0, 1.0, 1);
            this.crystallizerDjinni = new TierSpiritSettings(builder, "Djinni Crystallizer", "crystal_tier2",
                    2, 0.5, 1.5, 1);
            this.crystallizerAfrit = new TierSpiritSettings(builder, "Afrit Crystallizer", "crystal_tier3",
                    3, 0.3, 2.0, 1);
            this.crystallizerMarid = new TierSpiritSettings(builder, "Marid Crystallizer", "crystal_tier4",
                    4, 0.1, 3.0, 1);
            this.crystallizerResultPickupDelay = builder.comment(
                    "The minimum ticks before a crystallizer can pick up an item it dropped. Default is 3 Seconds = 3 * 20 Ticks.")
                    .defineInRange("crystallizerResultPickupDelay", 20 * 3, 0, Integer.MAX_VALUE);

            this.smelterFoliot = new SimpleWorkerSpiritSettings(builder, "Foliot Smelter",
                    "smelter_tier1", 1.0, 1);
            this.smelterDjinni = new SimpleWorkerSpiritSettings(builder, "Djinni Smelter",
                    "smelter_tier2", 0.5, 1);
            this.smelterAfrit = new SimpleWorkerSpiritSettings(builder, "Afrit Smelter",
                    "smelter_tier3", 0.1, 1);
            this.smelterMarid = new SimpleWorkerSpiritSettings(builder, "Marid Smelter",
                    "smelter_tier4", 0.01, 1);
            this.smelterResultPickupDelay = builder.comment(
                    "The minimum ticks before a smelter can pick up an item it dropped. Default is 3 Seconds = 3 * 20 Ticks.")
                    .defineInRange("smelterResultPickupDelay", 20 * 3, 0, Integer.MAX_VALUE);

            this.traderSapling = new TraderSpiritSettings(builder, "Otherworld Sapling Trader",
                    "trader_sapling", 20, 1);
            this.traderOtherstone = new TraderSpiritSettings(builder, "Otherstone Trader",
                    "trader_otherstone", 10, 4);
            this.traderOtherrock = new TraderSpiritSettings(builder, "Otherrock Trader",
                    "trader_otherrock", 10, 4);
            this.traderGem = new TraderSpiritSettings(builder, "Gambler",
                    "trader_gem", 200, 16);
            this.traderResultPickupDelay = builder.comment(
                    "The minimum ticks before a trader can pick up an item it dropped. Default is 3 Seconds = 3 * 20 Ticks.")
                    .defineInRange("traderResultPickupDelay", 20 * 3, 0, Integer.MAX_VALUE);

            this.dayTimeToCast = builder.comment(
                    "The time in ticks it takes to cast the day time ritual.")
                    .defineInRange("dayTimeToCast", 20 * 5, 0, Integer.MAX_VALUE);
            this.nightTimeToCast = builder.comment(
                    "The time in ticks it takes to cast the night time ritual.")
                    .defineInRange("nightTimeToCast", 20 * 5, 0, Integer.MAX_VALUE);
            this.rainTimeToCast = builder.comment(
                    "The time in ticks it takes to cast the rain ritual.")
                    .defineInRange("rainTimeToCast", 20 * 10, 0, Integer.MAX_VALUE);
            this.thunderTimeToCast = builder.comment(
                    "The time in ticks it takes to cast the thunder ritual.")
                    .defineInRange("thunderTimeToCast", 20 * 15, 0, Integer.MAX_VALUE);
            this.clearWeatherTimeToCast = builder.comment(
                    "The time in ticks it takes to cast the clear weather ritual.")
                    .defineInRange("clearWeatherTimeToCast", 20 * 5, 0, Integer.MAX_VALUE);

            builder.pop();
        }

        public static class TierSpiritSettings {
            public final ModConfigSpec.IntValue tier;
            public final ModConfigSpec.DoubleValue timeMultiplier;
            public final ModConfigSpec.DoubleValue outputMultiplier;
            public final ModConfigSpec.IntValue operationCount;

            public TierSpiritSettings(ModConfigSpec.Builder builder, String spirit, String spiritJobName,
                                       int tier, double timeMultiplier, double outputMultiplier, int operationCount) {
                builder.comment("Worker Spirit Settings").push(spiritJobName);

                this.tier =
                        builder.comment("The tier of " + spirit)
                                .defineInRange("tier", tier, 0, Integer.MAX_VALUE);
                this.timeMultiplier =
                        builder.comment("The multiplier to each recipe's time for " + spirit)
                                .defineInRange("timeMultiplier", timeMultiplier, 0.0, Integer.MAX_VALUE);
                this.outputMultiplier =
                        builder.comment("The multiplier to each recipe's output count for " + spirit)
                                .defineInRange("outputMultiplier", outputMultiplier, 0.0, Integer.MAX_VALUE);
                this.operationCount =
                        builder.comment("Max number of recipes that " + spirit + " make per operation.")
                                .defineInRange("operationCount", operationCount, 0, 64);

                builder.pop();
            }
        }
        public static class SimpleWorkerSpiritSettings {
            public final ModConfigSpec.DoubleValue timeMultiplier;
            public final ModConfigSpec.IntValue operationCount;

            public SimpleWorkerSpiritSettings(ModConfigSpec.Builder builder, String spirit, String spiritJobName,
                                      double timeMultiplier, int operationCount) {
                builder.comment("Worker Spirit Settings").push(spiritJobName);

                this.timeMultiplier =
                        builder.comment("The multiplier to each recipe's time for " + spirit)
                                .defineInRange("timeMultiplier", timeMultiplier, 0.0, Integer.MAX_VALUE);
                this.operationCount =
                        builder.comment("Max number of recipes that " + spirit + " make per operation.")
                                .defineInRange("operationCount", operationCount, 0, 64);

                builder.pop();
            }
        }
        public static class TraderSpiritSettings {
            public final ModConfigSpec.IntValue operationTimer;
            public final ModConfigSpec.IntValue operationCount;

            public TraderSpiritSettings(ModConfigSpec.Builder builder, String spirit, String spiritJobName,
                                              int operationTimer, int operationCount) {
                builder.comment("Trader Spirit Settings").push(spiritJobName);

                this.operationTimer =
                        builder.comment("The time to each operation for " + spirit)
                                .defineInRange("operationTimer", operationTimer, 0, Integer.MAX_VALUE);
                this.operationCount =
                        builder.comment("Max number of recipes that" + spirit + "make per operation.")
                                .defineInRange("operationCount", operationCount, 0, 64);

                builder.pop();
            }
        }
    }

    public static class FamiliarSettings {
        public final IntValue drikwingFamiliarSlowFallingSeconds;
        public final DoubleValue blacksmithFamiliarRepairChance;
        public final IntValue blacksmithFamiliarUpgradeCost;
        public final IntValue blacksmithFamiliarUpgradeCooldown;
        public final IntValue greedySearchRange;
        public final IntValue greedyVerticalSearchRange;

        public FamiliarSettings(ModConfigSpec.Builder builder) {
            builder.comment("Familiar Settings").push("familiar");

            this.drikwingFamiliarSlowFallingSeconds = builder.comment(
                            "The duration of slow falling effect given by Drikwing Familiar in seconds.")
                    .defineInRange("drikwingFamiliarSlowFallingSeconds", 15, 0, Integer.MAX_VALUE);
            this.blacksmithFamiliarRepairChance = builder.comment(
                            "The chance for a blacksmith familiar to repair an item (by 2 durability) whenever stone is picked up. 1.0 = 100%, 0.0 = 0%.")
                    .defineInRange("blacksmithFamiliarRepairChance", 0.33, 0.0, Double.MAX_VALUE);
            this.blacksmithFamiliarUpgradeCost = builder.comment(
                            "The amount of iron required for a blacksmith familiar to upgrade another familiar.")
                    .defineInRange("blacksmithFamiliarUpgradeCost", 18, 0, Integer.MAX_VALUE);
            this.blacksmithFamiliarUpgradeCooldown = builder.comment(
                            "The cooldown for a blacksmith familiar to upgrade another familiar.")
                    .defineInRange("blacksmithFamiliarUpgradeCooldown", 20 * 20, 0, Integer.MAX_VALUE);

            this.greedySearchRange = builder.comment(
                            "The horizontal value that the upgraded greedy familiar will seek blocks. (Large distances can cause delays in finding)")
                    .defineInRange("greedySearchRange", 32, 0, Integer.MAX_VALUE);

            this.greedyVerticalSearchRange = builder.comment(
                            "The vertical value that the upgraded greedy familiar will seek blocks. (Large distances can cause delays in finding)")
                    .defineInRange("greedyVerticalSearchRange", 16, 0, Integer.MAX_VALUE);

            builder.pop();
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
                    builder.comment("Set a value below 1.0 to speed up rituals. Please ensure to use a preceding 0 for values below 1.0, e.g. '0.05' instead of '.05', otherwise Nightconfig (NeoForge's config sytem) will reset the value to 1.0.")
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
        public final IntValue stabilizerTier5AdditionalMaxItemTypes;
        public final LongValue stabilizerTier5AdditionalMaxTotalItemCount;
        public final IntValue controllerMaxItemTypes;
        public final LongValue controllerMaxTotalItemCount;
        public final IntValue stabilizedControllerStabilizers;
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
            this.stabilizerTier5AdditionalMaxItemTypes =
                    builder.comment("The amount of slots the storage stabilizer tier 5 provides.")
                            .defineInRange("stabilizerTier5AdditionalMaxItemTypes", 1024, 0, Integer.MAX_VALUE);
            this.stabilizerTier5AdditionalMaxTotalItemCount =
                    builder.comment("The amount by which the stabilizer increases the maximum total item count the controller can hold. This is not per slot but the total amount of all items combined.")
                            .defineInRange("stabilizerTier5AdditionalMaxTotalItemCount", 8192 * 1000L, 0, Long.MAX_VALUE);
            this.controllerMaxItemTypes =
                    builder.comment("The amount of slots the storage actuator provides.")
                            .defineInRange("controllerMaxItemTypes", 128, 0, Integer.MAX_VALUE);
            this.controllerMaxTotalItemCount =
                    builder.comment("The stack size the storage actuator uses.")
                            .defineInRange("controllerMaxTotalItemCount", 256 * 1000L, 0, Long.MAX_VALUE);
            this.stabilizedControllerStabilizers =
                    builder.comment("The amount of stabilizers tier 5 in the stabilized storage actuator. (Don't auto change the recipe)")
                            .defineInRange("stabilizedControllerStabilizers", 8, 0, Integer.MAX_VALUE);
            this.unlinkWormholeOnBreak =
                    builder.comment(
                                    "True to unlink the wormhole when break (so it doesn't function as a cheaper Storage Accessor).")
                            .define("unlinkWormholeOnBreak", false);
            builder.pop();
        }
    }
}
