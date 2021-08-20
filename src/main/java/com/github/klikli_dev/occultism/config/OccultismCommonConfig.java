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

import com.github.klikli_dev.occultism.config.value.CachedBoolean;
import com.github.klikli_dev.occultism.config.value.CachedFloat;
import com.github.klikli_dev.occultism.config.value.CachedInt;
import com.github.klikli_dev.occultism.config.value.CachedObject;
import com.github.klikli_dev.occultism.registry.OccultismTags;
import net.minecraft.world.level.block.Block;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.ForgeConfigSpec;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class OccultismCommonConfig extends ConfigBase {
    //region Fields
    public final WorldGenSettings worldGen;
    public final ForgeConfigSpec spec;
    //endregion Fields

    //region Initialization
    public OccultismCommonConfig() {
        ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();
        this.worldGen = new WorldGenSettings(this, builder);
        this.spec = builder.build();
    }
    //endregion Initialization

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
                        new OreSettings("copperOre",
                                BlockTags.BASE_STONE_OVERWORLD, 9,
                                10, 20, 0, 64, this, builder);
                this.silverOre =
                        new OreSettings("silverOre",
                                BlockTags.BASE_STONE_OVERWORLD, 7,
                                3, 5, 0, 30, this, builder);
                this.iesniumOre =
                        new OreSettings("iesniumOre",
                                OccultismTags.NETHERRACK, 3, 10,
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
                public final CachedInt maximum;
                //endregion Fields

                //region Initialization
                public OreSettings(String oreName, Tag<Block> fillerBlockTag,
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
                                            BlockTags.getAllTags()
                                                    .getId(fillerBlockTag).toString()));
                    this.size = CachedInt.cache(this,
                            builder.comment("The size of veins for this ore.")
                                    .defineInRange("size", size, 0, Byte.MAX_VALUE));
                    this.count = CachedInt.cache(this,
                            builder.comment("The count value for the decorator for this ore.")
                                    .defineInRange("count", count, 0, Byte.MAX_VALUE));
                    this.bottomOffset = CachedInt.cache(this,
                            builder.comment("Range configuration min height.")
                                    .define("bottomOffset", bottomOffset));
                    this.maximum = CachedInt.cache(this,
                            builder.comment("Range configuration max height. A negative max height is interpreted as offset from the top of the world (relevant for nether)")
                                    .define("maximum", maximum));
                    builder.pop();
                }
                //endregion Initialization

                //region Getter / Setter
                public Tag<Block> getFillerBlockTag() {
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
