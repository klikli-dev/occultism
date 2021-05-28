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

import com.github.klikli_dev.occultism.config.value.CachedBoolean;
import com.github.klikli_dev.occultism.config.value.CachedFloat;
import com.github.klikli_dev.occultism.config.value.CachedInt;
import net.minecraftforge.common.ForgeConfigSpec;

public class OccultismServerConfig extends ConfigBase {

    //region Fields
    public final StorageSettings storage;
    public final SpiritJobSettings spiritJobs;
    public final RitualSettings rituals;
    public final DimensionalMineshaftSettings dimensionalMineshaft;
    public final ForgeConfigSpec spec;
    //endregion Fields

    //region Initialization
    public OccultismServerConfig() {
        ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();
        this.storage = new StorageSettings(this, builder);
        this.spiritJobs = new SpiritJobSettings(this, builder);
        this.rituals = new RitualSettings(this, builder);
        this.dimensionalMineshaft = new DimensionalMineshaftSettings(this, builder);
        this.spec = builder.build();
    }
    //endregion Initialization

    public class SpiritJobSettings extends ConfigCategoryBase {
        //region Fields
        public final CachedFloat tier1CrusherTimeMultiplier;
        public final CachedFloat tier2CrusherTimeMultiplier;
        public final CachedFloat tier3CrusherTimeMultiplier;
        public final CachedFloat tier4CrusherTimeMultiplier;
        public final CachedFloat tier1CrusherOutputMultiplier;
        public final CachedFloat tier2CrusherOutputMultiplier;
        public final CachedFloat tier3CrusherOutputMultiplier;
        public final CachedFloat tier4CrusherOutputMultiplier;
        public final CachedInt drikwingFamiliarSlowFallingSeconds;
        public final CachedInt tier1CrusherMaxAgeSeconds;
        public final CachedInt tier2CrusherMaxAgeSeconds;
        public final CachedInt tier3CrusherMaxAgeSeconds;
        public final CachedInt tier4CrusherMaxAgeSeconds;
        //endregion Fields

        //region Initialization
        public SpiritJobSettings(IConfigCache parent, ForgeConfigSpec.Builder builder) {
            super(parent, builder);
            builder.comment("Spirit Job Settings").push("spirit_job");
            this.drikwingFamiliarSlowFallingSeconds = CachedInt.cache(this,
                    builder.comment(
                            "The duration for the slow falling effect applied by a drikwing.")
                            .define("drikwingFamiliarSlowFallingSeconds", 15));
            this.tier1CrusherTimeMultiplier = CachedFloat.cache(this,
                    builder.comment(
                            "The multiplier to each crushing recipe's crushing_time for Tier 1 (Foliot) Crusher Spirits.")
                            .define("tier1CrusherTimeMultiplier", 2.0));
            this.tier2CrusherTimeMultiplier = CachedFloat.cache(this,
                    builder.comment(
                            "The multiplier to each crushing recipe's crushing_time for Tier 2 (Djinni) Crusher Spirits.")
                            .define("tier2CrusherTimeMultiplier", 1.0));
            this.tier3CrusherTimeMultiplier = CachedFloat.cache(this,
                    builder.comment(
                            "Currently unused. The multiplier to each crushing recipe's crushing_time for Tier 3 (Afrit) Crusher Spirits.")
                            .define("tier3CrusherTimeMultiplier", 0.5));
            this.tier4CrusherTimeMultiplier = CachedFloat.cache(this,
                    builder.comment(
                            "Currently unused. The multiplier to each crushing recipe's crushing_time for Tier 4 (Marid) Crusher Spirits.")
                            .define("tier4CrusherTimeMultiplier", 0.2));

            this.tier1CrusherOutputMultiplier = CachedFloat.cache(this,
                    builder.comment(
                            "The multiplier to each crushing recipe's output count for Tier 1 (Foliot) Crusher Spirits.")
                            .define("tier1CrusherOutputMultiplier", 1.0));
            this.tier2CrusherOutputMultiplier = CachedFloat.cache(this,
                    builder.comment(
                            "The multiplier to each crushing recipe's output count for Tier 2 (Djinni) Crusher Spirits.")
                            .define("tier2CrusherOutputMultiplier", 1.5));
            this.tier3CrusherOutputMultiplier = CachedFloat.cache(this,
                    builder.comment(
                            "The multiplier to each crushing recipe's output count for Tier 3 (Afrit) Crusher Spirits.")
                            .define("tier3CrusherOutputMultiplier", 2.0));
            this.tier4CrusherOutputMultiplier = CachedFloat.cache(this,
                    builder.comment(
                            "The multiplier to each crushing recipe's output count for Tier 4 (Marid) Crusher Spirits.")
                            .define("tier4CrusherOutputMultiplier", 3.0));

            this.tier1CrusherMaxAgeSeconds = CachedInt.cache(this,
                    builder.comment(
                            "The max seconds before a spirit despawns. -1 to disable despawn.")
                            .define("tier1CrusherMaxAgeSeconds", 60 * 60 * 9));
            this.tier2CrusherMaxAgeSeconds = CachedInt.cache(this,
                    builder.comment(
                            "The max seconds before a spirit despawns. -1 to disable despawn.")
                            .define("tier2CrusherMaxAgeSeconds", -1));
            this.tier3CrusherMaxAgeSeconds = CachedInt.cache(this,
                    builder.comment(
                            "The max seconds before a spirit despawns. -1 to disable despawn.")
                            .define("tier3CrusherMaxAgeSeconds", -1));
            this.tier4CrusherMaxAgeSeconds = CachedInt.cache(this,
                    builder.comment(
                            "The max seconds before a spirit despawns. -1 to disable despawn.")
                            .define("tier4CrusherMaxAgeSeconds", -1));
            builder.pop();
        }
        //endregion Initialization
    }

    public class DimensionalMineshaftSettings extends ConfigCategoryBase {
        //region Fields
        public final MinerSpiritSettings minerFoliotUnspecialized;
        public final MinerSpiritSettings minerDjinniOres;
        //endregion Fields

        //region Initialization
        public DimensionalMineshaftSettings(IConfigCache parent, ForgeConfigSpec.Builder builder) {
            super(parent, builder);
            builder.comment("Dimensional Mineshaft Settings").push("dimensional_mineshaft");

            this.minerFoliotUnspecialized =
                    new MinerSpiritSettings("miner_foliot_unspecialized", parent, builder, 400, 1, 1000);

            this.minerDjinniOres =
                    new MinerSpiritSettings("miner_djinni_ores", parent, builder, 400, 1, 100);

            builder.pop();
        }

        //endregion Initialization
        public class MinerSpiritSettings extends ConfigCategoryBase {
            //region Fields
            public final CachedInt maxMiningTime;
            public final CachedInt rollsPerOperation;
            public final CachedInt durability;
            //endregion Fields

            //region Initialization
            public MinerSpiritSettings(String oreName, IConfigCache parent, ForgeConfigSpec.Builder builder,
                                       int maxMiningTime, int rollsPerOperation, int durability) {
                super(parent, builder);
                builder.comment("Miner Spirit Settings").push(oreName);

                this.maxMiningTime = CachedInt.cache(this,
                        builder.comment("The amount of time it takes the spirit to perform one mining operation.")
                                .define("maxMiningTime", maxMiningTime));
                this.rollsPerOperation = CachedInt.cache(this,
                        builder.comment("The amount of blocks the spirit will obtain per mining operation")
                                .define("rollsPerOperation", rollsPerOperation));
                this.durability = CachedInt.cache(this,
                        builder.comment("The amount of mining operations the spirit can perform before breaking.")
                                .define("durability", durability));

                builder.pop();
            }
            //endregion Initialization
        }

    }

    public class RitualSettings extends ConfigCategoryBase {
        //region Fields
        public final CachedBoolean enableClearWeatherRitual;
        public final CachedBoolean enableRainWeatherRitual;
        public final CachedBoolean enableThunderWeatherRitual;
        public final CachedBoolean enableDayTimeRitual;
        public final CachedBoolean enableNightTimeRitual;
        public final CachedBoolean enableRemainingIngredientCountMatching;
        //endregion Fields

        //region Initialization
        public RitualSettings(IConfigCache parent, ForgeConfigSpec.Builder builder) {
            super(parent, builder);
            builder.comment("Ritual Settings").push("rituals");

            this.enableClearWeatherRitual = CachedBoolean.cache(this,
                    builder.comment("Enables the ritual to clear rainy weather.")
                            .define("enableClearWeatherRitual", true));
            this.enableRainWeatherRitual = CachedBoolean.cache(this,
                    builder.comment("Enables the ritual to start rainy weather.")
                            .define("enableRainWeatherRitual", true));
            this.enableThunderWeatherRitual = CachedBoolean.cache(this,
                    builder.comment("Enables the ritual to start a thunderstorm.")
                            .define("enableThunderWeatherRitual", true));
            this.enableDayTimeRitual = CachedBoolean.cache(this,
                    builder.comment("Enables the ritual to set time to day.")
                            .define("enableDayTimeRitual", true));
            this.enableNightTimeRitual = CachedBoolean.cache(this,
                    builder.comment("Enables the ritual to set time to night.")
                            .define("enableNightTimeRitual", true));
            this.enableRemainingIngredientCountMatching = CachedBoolean.cache(this,
                    builder.comment(
                            "If enabled, rituals are interrupted if *more* ingredients are present than needed. " +
                            "This should usually be disabled, but can improve performance if " +
                            "(very very) many rituals are running.")
                            .define("enableRemainingIngredientCountMatching", false));
            builder.pop();
        }
        //endregion Initialization
    }

    public class StorageSettings extends ConfigCategoryBase {
        //region Fields
        public final CachedInt stabilizerTier1Slots;
        public final CachedInt stabilizerTier2Slots;
        public final CachedInt stabilizerTier3Slots;
        public final CachedInt stabilizerTier4Slots;
        public final CachedInt controllerBaseSlots;
        public final CachedInt controllerStackSize;
        public final CachedBoolean overrideItemStackSizes;
        //endregion Fields

        //region Initialization
        public StorageSettings(IConfigCache parent, ForgeConfigSpec.Builder builder) {
            super(parent, builder);
            builder.comment("Storage Settings").push("storage");
            this.stabilizerTier1Slots = CachedInt.cache(this,
                    builder.comment("The amount of slots the storage stabilizer tier 1 provides.")
                            .define("stabilizerTier1Slots", 128));
            this.stabilizerTier2Slots = CachedInt.cache(this,
                    builder.comment("The amount of slots the storage stabilizer tier 2 provides.")
                            .define("stabilizerTier2Slots", 256));
            this.stabilizerTier3Slots = CachedInt.cache(this,
                    builder.comment("The amount of slots the storage stabilizer tier 3 provides.")
                            .define("stabilizerTier3Slots", 512));
            this.stabilizerTier4Slots = CachedInt.cache(this,
                    builder.comment("The amount of slots the storage stabilizer tier 4 provides.")
                            .define("stabilizerTier4Slots", 1024));
            this.controllerBaseSlots = CachedInt.cache(this,
                    builder.comment("The amount of slots the storage actuator provides.")
                            .define("controllerBaseSlots", 128));
            this.controllerStackSize = CachedInt.cache(this,
                    builder.comment("The stack size the storage actuator uses.")
                            .define("controllerStackSize", 1024));
            this.overrideItemStackSizes = CachedBoolean.cache(this,
                    builder.comment(
                            "True to use the configured controllerStackSize for all items, instead of the stack sizes provided by " +
                            "item type (such as 16 for ender pearls, 64 for iron ingot). WARNING: Setting this to " +
                            "false may have a negative impact on performance.")
                            .define("overrideItemStackSizes", true));
            builder.pop();
        }
        //endregion Initialization
    }
}
