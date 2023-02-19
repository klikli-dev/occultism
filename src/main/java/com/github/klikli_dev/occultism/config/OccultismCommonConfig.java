/*
 * MIT License
 *
 * Copyright 2021 klikli-dev
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

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.BooleanValue;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;
import net.minecraftforge.common.ForgeConfigSpec.DoubleValue;
import net.minecraftforge.common.ForgeConfigSpec.IntValue;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class OccultismCommonConfig {
    public final WorldGenSettings worldGen;
    public final ForgeConfigSpec spec;

    //region Initialization
    public OccultismCommonConfig() {
        ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();
        this.worldGen = new WorldGenSettings(builder);
        this.spec = builder.build();
    }

    public static class WorldGenSettings {
        public final OreGenSettings oreGen;
        public final UndergroundGroveGenSettings undergroundGroveGen;

        public WorldGenSettings(ForgeConfigSpec.Builder builder) {
            builder.comment("WorldGen Settings").push("worldgen");
            this.oreGen = new OreGenSettings(builder);
            this.undergroundGroveGen = new UndergroundGroveGenSettings(builder);
            builder.pop();
        }

        public static class OreGenSettings {
            public final OreSettings silverOre;
            public final OreSettings silverOreDeepslate;
            public final OreSettings iesniumOre;

            public OreGenSettings(ForgeConfigSpec.Builder builder) {
                builder.comment("Ore Gen Settings").push("oregen");
                
                this.silverOre =
                        new OreSettings("silverOre",
                                BlockTags.STONE_ORE_REPLACEABLES, 5,
                                7, 50, 0, 200, builder);
                this.silverOreDeepslate =
                        new OreSettings("silverOreDeepslate",
                                BlockTags.DEEPSLATE_ORE_REPLACEABLES, 10,
                                10, -64, 0, 50, builder);
                this.iesniumOre =
                        new OreSettings("iesniumOre",
                                BlockTags.BASE_STONE_NETHER, 3, 10,
                                10, 10, 128, builder);
                builder.pop();
            }

            public static class OreSettings {
                public final BooleanValue generateOre;
                public final ConfigValue<String> fillerBlockTag;
                public final IntValue size;
                public final IntValue count;
                public final IntValue minimum;
                public final IntValue maximum;

                public OreSettings(String oreName, TagKey<Block> fillerBlockTag,
                                   int size, int count, int minimum, int topOffset, int maximum,
                                   ForgeConfigSpec.Builder builder) {
                    builder.comment("Ore Settings").push(oreName);

                    this.generateOre =
                            builder.comment("True to generate this ore.")
                                    .define("generateOre", true);
                    this.fillerBlockTag =
                            builder.comment("The tag for the blocks this ore will spawn in.")
                                    .define("fillerBlockTag", fillerBlockTag.location().toString());
                    this.size =
                            builder.comment("The size of veins for this ore.")
                                    .defineInRange("size", size, 0, Byte.MAX_VALUE);
                    this.count =
                            builder.comment("The count value for the decorator for this ore.")
                                    .defineInRange("count", count, 0, Byte.MAX_VALUE);
                    this.minimum = builder.comment("Range configuration min height.")
                            .defineInRange("bottomOffset", minimum, Integer.MIN_VALUE, Integer.MAX_VALUE);
                    this.maximum = builder.comment("Range configuration max height. A negative max height is interpreted as offset from the top of the world (relevant for nether)")
                            .defineInRange("maximum", maximum, Integer.MIN_VALUE, Integer.MAX_VALUE);
                    builder.pop();
                }

                public TagKey<Block> getFillerBlockTag() {
                    return TagKey.create(Registry.BLOCK_REGISTRY, new ResourceLocation(this.fillerBlockTag.get()));
                }
            }
        }


        public static class UndergroundGroveGenSettings {
            public final BooleanValue generateUndergroundGroves;
            public final IntValue groveSpawnChance;
            public final IntValue groveSpawnMin;
            public final IntValue groveSpawnMax;
            public final DoubleValue grassChance;
            public final DoubleValue treeChance;
            public final DoubleValue vineChance;
            public final DoubleValue ceilingLightChance;
            public final ConfigValue<List<String>> biomeTypeBlacklist;

            public UndergroundGroveGenSettings(ForgeConfigSpec.Builder builder) {
                builder.comment("Underground Grove Settings").push("underground_grove");
                this.generateUndergroundGroves =
                        builder.comment("True to generate underground groves. Should not be changed in most scenarios.")
                                .define("generateUndergroundGroves", true);

                this.groveSpawnChance =
                        builder.comment(
                                        "The chance for a grove to spawn in a chunk (generates 1/groveSpawnChance chunks on average).")
                                .defineInRange("groveSpawnChance", 400, 0, Integer.MAX_VALUE);
                this.groveSpawnMin =
                        builder.comment(
                                        "The min height for a grove to spawn (applied to the center of the grove, not the floor).")
                                .defineInRange("groveSpawnMin", 25, 0, 512);
                this.groveSpawnMax =
                        builder.comment(
                                        "The max height for a grove to spawn (applied to the center of the grove, not the ceiling).")
                                .defineInRange("groveSpawnMax", 60, 0, 512);


                this.grassChance =
                        builder.comment("The chance grass will spawn in the underground grove.")
                                .defineInRange("grassChance", 0.6, 0.0f, 1.0f);
                this.treeChance =
                        builder.comment("The chance small trees will spawn in the underground grove.")
                                .defineInRange("treeChance", 0.1, 0.0f, 1.0f);
                this.vineChance =
                        builder.comment("The chance vines will spawn in the underground grove.")
                                .defineInRange("vineChance", 0.3, 0.0f, 1.0f);
                this.ceilingLightChance =
                        builder.comment("The chance glowstone will spawn in the ceiling of the underground grove.")
                                .defineInRange("ceilingLightChance", 0.1, 0.0f, 1.0f);

                List<String> defaultBiomeTypeBlacklist =
                        Stream.of(BiomeDictionary.Type.NETHER, BiomeDictionary.Type.END)
                                .map(BiomeDictionary.Type::getName)
                                .collect(Collectors.toList());
                this.biomeTypeBlacklist =
                        builder.comment("The biome types the underground grove cannot spawn in.")
                                .define("biomeTypeBlacklist", defaultBiomeTypeBlacklist);

                builder.pop();
            }
        }
    }
}
