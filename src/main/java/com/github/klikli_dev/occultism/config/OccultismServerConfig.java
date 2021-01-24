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
import com.github.klikli_dev.occultism.config.value.CachedObject;
import com.github.klikli_dev.occultism.registry.OccultismTags;
import net.minecraft.block.Block;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ITag;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.ForgeConfigSpec;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class OccultismServerConfig extends ConfigBase {

    //region Fields
    public final StorageSettings storage;
    public final SpiritJobSettings spiritJobs;
    public final WorldGenSettings worldGen;
    public final RitualSettings rituals;
    public final DimensionalMineshaftSettings dimensionalMineshaft;
    public final ForgeConfigSpec spec;
    //endregion Fields

    //region Initialization
    public OccultismServerConfig() {
        ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();
        this.storage = new StorageSettings(this, builder);
        this.spiritJobs = new SpiritJobSettings(this, builder);
        this.worldGen = new WorldGenSettings(this, builder);
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
        public final CachedInt drikwingFamiliarSlowFallingSeconds;
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
                            "The multiplier to each crushing recipe's crushing_time for Tier 1 Crusher Spirits.")
                            .define("tier1CrusherTimeMultiplier", 2.0));
            this.tier2CrusherTimeMultiplier = CachedFloat.cache(this,
                    builder.comment(
                            "Currently unused. The multiplier to each crushing recipe's crushing_time for Tier 2 Crusher Spirits.")
                            .define("tier2CrusherTimeMultiplier", 1.0));
            this.tier3CrusherTimeMultiplier = CachedFloat.cache(this,
                    builder.comment(
                            "Currently unused. The multiplier to each crushing recipe's crushing_time for Tier 3 Crusher Spirits.")
                            .define("tier3CrusherTimeMultiplier", 0.5));
            this.tier4CrusherTimeMultiplier = CachedFloat.cache(this,
                    builder.comment(
                            "Currently unused. The multiplier to each crushing recipe's crushing_time for Tier 4 Crusher Spirits.")
                            .define("tier4CrusherTimeMultiplier", 0.2));
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
            builder.pop();
        }
        //endregion Initialization
    }

    public class WorldGenSettings extends ConfigCategoryBase {
        //region Fields
        public final OreGenSettings oreGen;
        public final UndergroundGroveGenSettings undergroundGroveGen;
        //endregion Fields

        //region Initialization
        public WorldGenSettings(IConfigCache parent, ForgeConfigSpec.Builder builder) {
            super(parent, builder);
            builder.comment("WorldGen Settings").push("worldgen");
            this.oreGen = new OreGenSettings(this, builder);
            this.undergroundGroveGen = new UndergroundGroveGenSettings(this, builder);
            builder.pop();
        }
        //endregion Initialization

        public class OreGenSettings extends ConfigCategoryBase {
            //region Fields

            public final OreSettings copperOre;
            public final OreSettings silverOre;
            public final OreSettings iesniumOre;

            //endregion Fields

            //region Initialization
            public OreGenSettings(IConfigCache parent, ForgeConfigSpec.Builder builder) {
                super(parent, builder);
                builder.comment("Ore Gen Settings").push("oregen");

                this.copperOre =
                        new OreSettings("copperOre", BlockTags.BASE_STONE_OVERWORLD, 9,
                                10, 20, 0, 64, this, builder);
                this.silverOre =
                        new OreSettings("silverOre", BlockTags.BASE_STONE_OVERWORLD, 7,
                                3, 5, 0, 30, this, builder);
                this.iesniumOre =
                        new OreSettings("iesniumOre", OccultismTags.NETHERRACK, 3, 10,
                                10, 10, 128, this, builder);
                builder.pop();
            }
            //endregion Initialization

            public class OreSettings extends ConfigCategoryBase {
                //region Fields
                public final CachedBoolean generateOre;
                public final CachedObject<String> fillerBlockTag;
                public final CachedInt size;
                public final CachedInt count;
                public final CachedInt bottomOffset;
                public final CachedInt topOffset;
                public final CachedInt maximum;
                //endregion Fields

                //region Initialization
                public OreSettings(String oreName, ITag<Block> fillerBlockTag,
                                   int size, int count, int bottomOffset, int topOffset, int maximum,
                                   IConfigCache parent, ForgeConfigSpec.Builder builder) {
                    super(parent, builder);
                    builder.comment("Ore Settings").push(oreName);

                    this.generateOre = CachedBoolean.cache(this,
                            builder.comment("True to generate this ore.")
                                    .define("generateOre", true));
                    this.fillerBlockTag = CachedObject.cache(this,
                            builder.comment("The tag for the blocks this ore will spawn in.")
                                    .define("fillerBlockTag",
                                            BlockTags.getCollection()
                                                    .getDirectIdFromTag(fillerBlockTag).toString()));
                    this.size = CachedInt.cache(this,
                            builder.comment("The size of veins for this ore.")
                                    .defineInRange("size", size, 0, Byte.MAX_VALUE));
                    this.count = CachedInt.cache(this,
                            builder.comment("The count value for the decorator for this ore.")
                                    .defineInRange("count", count, 0, Byte.MAX_VALUE));
                    this.bottomOffset = CachedInt.cache(this,
                            builder.comment("Range configuration bottom offset.")
                                    .define("bottomOffset", bottomOffset));
                    this.topOffset = CachedInt.cache(this,
                            builder.comment("Range configuration top offset.")
                                    .define("topOffset", topOffset));
                    this.maximum = CachedInt.cache(this,
                            builder.comment("Range configuration maximum.")
                                    .define("maximum", maximum));
                    builder.pop();
                }
                //endregion Initialization

                //region Getter / Setter
                public ITag<Block> getFillerBlockTag() {
                    return BlockTags.createOptional(new ResourceLocation(this.fillerBlockTag.get()));
                }
                //endregion Getter / Setter
            }
        }


        public class UndergroundGroveGenSettings extends ConfigCategoryBase {
            //region Fields
            public final CachedBoolean generateUndergroundGroves;
            public final CachedInt groveSpawnChance;
            public final CachedInt groveSpawnMin;
            public final CachedInt groveSpawnMax;
            public final CachedFloat grassChance;
            public final CachedFloat treeChance;
            public final CachedFloat vineChance;
            public final CachedFloat ceilingLightChance;
            public final CachedObject<List<String>> biomeTypeBlacklist;
            //endregion Fields

            //region Initialization
            public UndergroundGroveGenSettings(IConfigCache parent, ForgeConfigSpec.Builder builder) {
                super(parent, builder);
                builder.comment("Underground Grove Settings").push("underground_grove");
                this.generateUndergroundGroves = CachedBoolean.cache(this,
                        builder.comment("True to generate underground groves. Should not be changed in most scenarios.")
                                .define("generateUndergroundGroves", true));

                this.groveSpawnChance = CachedInt.cache(this,
                        builder.comment(
                                "The chance for a grove to spawn in a chunk (generates 1/groveSpawnChance chunks on average).")
                                .define("groveSpawnChance", 400));
                this.groveSpawnMin = CachedInt.cache(this,
                        builder.comment(
                                "The min height for a grove to spawn (applied to the center of the grove, not the floor).")
                                .define("groveSpawnMin", 25));
                this.groveSpawnMax = CachedInt.cache(this,
                        builder.comment(
                                "The max height for a grove to spawn (applied to the center of the grove, not the ceiling).")
                                .define("groveSpawnMax", 60));


                this.grassChance = CachedFloat.cache(this,
                        builder.comment("The chance grass will spawn in the underground grove.")
                                .define("grassChance", 0.6));
                this.treeChance = CachedFloat.cache(this,
                        builder.comment("The chance small trees will spawn in the underground grove.")
                                .define("treeChance", 0.1));
                this.vineChance = CachedFloat.cache(this,
                        builder.comment("The chance vines will spawn in the underground grove.")
                                .define("vineChance", 0.3));
                this.ceilingLightChance = CachedFloat.cache(this,
                        builder.comment("The chance glowstone will spawn in the ceiling of the underground grove.")
                                .define("ceilingLightChance", 0.1));

                List<String> defaultBiomeTypeBlacklist =
                        Stream.of(BiomeDictionary.Type.NETHER, BiomeDictionary.Type.END)
                                .map(BiomeDictionary.Type::getName)
                                .collect(Collectors.toList());
                this.biomeTypeBlacklist = CachedObject.cache(this,
                        builder.comment("The biome types the underground grove cannot spawn in.")
                                .define("biomeTypeBlacklist", defaultBiomeTypeBlacklist));

                builder.pop();
            }
            //endregion Initialization
        }
    }
}
