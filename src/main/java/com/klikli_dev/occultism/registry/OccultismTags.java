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

package com.klikli_dev.occultism.registry;

import com.klikli_dev.occultism.Occultism;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;

public class OccultismTags {
    public static class Blocks {

        // Block Tags
        public static final TagKey<Block> TREE_SOIL = makeBlockTag(new ResourceLocation(Occultism.MODID, "tree_soil"));
        public static final TagKey<Block> CAVE_WALL_BLOCKS = makeBlockTag(new ResourceLocation(Occultism.MODID, "cave_wall_blocks"));
        public static final TagKey<Block> WORLDGEN_BLACKLIST = makeBlockTag(new ResourceLocation(Occultism.MODID, "worldgen_blacklist"));

        public static final TagKey<Block> NETHERRACK = makeBlockTag(new ResourceLocation(Occultism.MODID, "netherrack"));
        public static final TagKey<Block> CANDLES = makeBlockTag(new ResourceLocation("minecraft", "candles"));
        public static final TagKey<Block> IESNIUM_ORE = makeBlockTag(new ResourceLocation("c", "ores/iesnium"));
        public static final TagKey<Block> SILVER_ORE = makeBlockTag(new ResourceLocation("c", "ores/silver"));
        // Storage Bock Tags
        public static final TagKey<Block> STORAGE_BLOCKS_IESNIUM = makeBlockTag(new ResourceLocation("c", "storage_blocks/iesnium"));
        public static final TagKey<Block> STORAGE_BLOCKS_SILVER = makeBlockTag(new ResourceLocation("c", "storage_blocks/silver"));
        public static final TagKey<Block> STORAGE_BLOCKS_RAW_IESNIUM = makeBlockTag(new ResourceLocation("c", "storage_blocks/raw_iesnium"));
        public static final TagKey<Block> STORAGE_BLOCKS_RAW_SILVER = makeBlockTag(new ResourceLocation("c", "storage_blocks/raw_silver"));

        public static final TagKey<Block> OTHERWORLD_SAPLINGS = makeBlockTag(new ResourceLocation(Occultism.MODID, "saplings/otherworld"));
        public static final TagKey<Block> OCCULTISM_CANDLES = makeBlockTag(new ResourceLocation(Occultism.MODID, "candles"));
        public static final TagKey<Block> STORAGE_STABILIZER = makeBlockTag(new ResourceLocation(Occultism.MODID, "storage_stabilizer"));
    }


    public static class Items {

        public static final TagKey<Item> DEMONIC_PARTNER_FOOD = makeItemTag(new ResourceLocation("occultism", "demonic_partner_food"));

        public static final TagKey<Item> IESNIUM_ORE = makeItemTag(new ResourceLocation("c", "ores/iesnium"));
        public static final TagKey<Item> SILVER_ORE = makeItemTag(new ResourceLocation("c", "ores/silver"));
        public static final TagKey<Item> RAW_IESNIUM = makeItemTag(new ResourceLocation("c", "raw_materials/iesnium"));
        public static final TagKey<Item> RAW_SILVER = makeItemTag(new ResourceLocation("c", "raw_materials/silver"));
        public static final TagKey<Item> STORAGE_BLOCK_IESNIUM = makeItemTag(new ResourceLocation("c", "storage_blocks/iesnium"));
        public static final TagKey<Item> STORAGE_BLOCK_SILVER = makeItemTag(new ResourceLocation("c", "storage_blocks/silver"));
        public static final TagKey<Item> STORAGE_BLOCK_RAW_IESNIUM = makeItemTag(new ResourceLocation("c", "storage_blocks/raw_iesnium"));
        public static final TagKey<Item> STORAGE_BLOCK_RAW_SILVER = makeItemTag(new ResourceLocation("c", "storage_blocks/raw_silver"));

        // Dusts
        public static final TagKey<Item> COPPER_DUST=makeItemTag(new ResourceLocation("c","dusts/copper"));
        public static final TagKey<Item> END_STONE_DUST=makeItemTag(new ResourceLocation("c","dusts/end_stone"));
        public static final TagKey<Item> GOLD_DUST=makeItemTag(new ResourceLocation("c","dusts/gold"));
        public static final TagKey<Item> IRON_DUST=makeItemTag(new ResourceLocation("c","dusts/iron"));
        public static final TagKey<Item> IESNIUM_DUST=makeItemTag(new ResourceLocation("c","dusts/iesnium"));
        public static final TagKey<Item> SILVER_DUST=makeItemTag(new ResourceLocation("c","dusts/silver"));
        public static final TagKey<Item> OBSIDIAN_DUST=makeItemTag(new ResourceLocation("c","dusts/obsidian"));

        public static final TagKey<Item> DATURA_CROP = makeItemTag(new ResourceLocation("c", "crops/datura"));

        // Ingots
        public static final TagKey<Item> IESNIUM_INGOT = makeItemTag(new ResourceLocation("c", "ingots/iesnium"));
        public static final TagKey<Item> SILVER_INGOT = makeItemTag(new ResourceLocation("c", "ingots/silver"));

        // Nuggets
        public static final TagKey<Item> IESNIUM_NUGGET = makeItemTag(new ResourceLocation("c", "nuggets/iesnium"));
        public static final TagKey<Item> SILVER_NUGGET = makeItemTag(new ResourceLocation("c", "nuggets/silver"));

        // Seeds
        public static final TagKey<Item> DATURA_SEEDS = makeItemTag(new ResourceLocation("c", "seeds/datura"));

        //Item Tags
        public static final TagKey<Item> TOOL_KNIVES = makeItemTag(new ResourceLocation(Occultism.MODID, "tools/knives"));
        public static final TagKey<Item> OTHERWORLD_GOGGLES = makeItemTag(new ResourceLocation(Occultism.MODID, "otherworld_goggles"));
        public static final TagKey<Item> ELYTRA = makeItemTag(new ResourceLocation(Occultism.MODID, "elytra"));
        public static final TagKey<Item> BOOKS = makeItemTag(new ResourceLocation("c", "books"));
        public static final TagKey<Item> FRUITS = makeItemTag(new ResourceLocation("c", "fruits"));
        public static final TagKey<Item> TALLOW = makeItemTag(new ResourceLocation("c", "tallow"));
        public static final TagKey<Item> BOOK_OF_CALLING_FOLIOT = makeItemTag(new ResourceLocation(Occultism.MODID, "books/book_of_calling_foliot"));
        public static final TagKey<Item> BOOK_OF_CALLING_DJINNI = makeItemTag(new ResourceLocation(Occultism.MODID, "books/book_of_calling_djinni"));


        public static final TagKey<Item> MAGMA = makeItemTag(new ResourceLocation("c", "magma"));

        public static final TagKey<Item> MANUALS = makeItemTag(new ResourceLocation("c", "manuals"));

        public static final TagKey<Item> METAL_AXES = makeItemTag(new ResourceLocation("c", "tools/metal/axes"));
        public static final TagKey<Item> OTHERWORLD_SAPLINGS = makeItemTag(new ResourceLocation(Occultism.MODID, "saplings/otherworld"));

        public static class Miners {
            public static final TagKey<Item> BASIC_RESOURCES = makeItemTag(new ResourceLocation(Occultism.MODID, "miners/basic_resources"));
            public static final TagKey<Item> DEEPS = makeItemTag(new ResourceLocation(Occultism.MODID, "miners/deeps"));
            public static final TagKey<Item> MASTER = makeItemTag(new ResourceLocation(Occultism.MODID, "miners/master"));
            public static final TagKey<Item> ORES = makeItemTag(new ResourceLocation(Occultism.MODID,"miners/ores"));
        }
    }

    public static class Entities {
        public static final TagKey<EntityType<?>> AXOLOTL = makeEntityTypeTag(new ResourceLocation("c", "axolotls"));
        public static final TagKey<EntityType<?>> CHICKEN = makeEntityTypeTag(new ResourceLocation("c", "chicken"));
        public static final TagKey<EntityType<?>> PARROTS = makeEntityTypeTag(new ResourceLocation("c", "parrots"));
        public static final TagKey<EntityType<?>> PIGS = makeEntityTypeTag(new ResourceLocation("c", "pigs"));
        public static final TagKey<EntityType<?>> COWS = makeEntityTypeTag(new ResourceLocation("c", "cows"));
        public static final TagKey<EntityType<?>> VILLAGERS = makeEntityTypeTag(new ResourceLocation("c", "villagers"));
        public static final TagKey<EntityType<?>> ZOMBIES = makeEntityTypeTag(new ResourceLocation("c", "zombies"));
        public static final TagKey<EntityType<?>> BATS = makeEntityTypeTag(new ResourceLocation("c", "bats"));
        public static final TagKey<EntityType<?>> SNOW_GOLEM=makeEntityTypeTag(new ResourceLocation("c", "golems/snow"));
        public static final TagKey<EntityType<?>> DONKEYS = makeEntityTypeTag(new ResourceLocation("c", "donkeys"));
        public static final TagKey<EntityType<?>> FISH = makeEntityTypeTag(new ResourceLocation("c", "fish"));
        public static final TagKey<EntityType<?>> GOATS = makeEntityTypeTag(new ResourceLocation("c", "goats"));
        public static final TagKey<EntityType<?>> HOGLINS = makeEntityTypeTag(new ResourceLocation("c", "hoglins"));
        public static final TagKey<EntityType<?>> HORSES = makeEntityTypeTag(new ResourceLocation("c", "horses"));
        public static final TagKey<EntityType<?>> LLAMAS = makeEntityTypeTag(new ResourceLocation("c", "llamas"));
        public static final TagKey<EntityType<?>> MULES = makeEntityTypeTag(new ResourceLocation("c", "mules"));
        public static final TagKey<EntityType<?>> PANDAS = makeEntityTypeTag(new ResourceLocation("c", "pandas"));
        public static final TagKey<EntityType<?>> SHEEP = makeEntityTypeTag(new ResourceLocation("c", "sheep"));
        public static final TagKey<EntityType<?>> SPIDERS = makeEntityTypeTag(new ResourceLocation("c", "spiders"));
        public static final TagKey<EntityType<?>> SQUID = makeEntityTypeTag(new ResourceLocation("c", "squid"));
        //Entity Tags
        public static final TagKey<EntityType<?>> SOUL_GEM_DENY_LIST = makeEntityTypeTag(new ResourceLocation(Occultism.MODID, "soul_gem_deny_list"));
        public static final TagKey<EntityType<?>> AFRIT_ALLIES = makeEntityTypeTag(new ResourceLocation(Occultism.MODID, "afrit_allies"));
        public static final TagKey<EntityType<?>> WILD_HUNT = makeEntityTypeTag(new ResourceLocation(Occultism.MODID, "wild_hunt"));
        public static final TagKey<EntityType<?>> HEALED_BY_DEMONS_DREAM_FRUIT = makeEntityTypeTag(new ResourceLocation(Occultism.MODID, "healed_by_demons_dream_fruit"));
        public static final TagKey<EntityType<?>> CUBEMOB = makeEntityTypeTag(new ResourceLocation(Occultism.MODID, "cubemob"));
        public static final TagKey<EntityType<?>> FLYING_PASSIVE = makeEntityTypeTag(new ResourceLocation(Occultism.MODID, "flying_passive"));
        public static final TagKey<EntityType<?>> HUMANS = makeEntityTypeTag(new ResourceLocation(Occultism.MODID, "humans"));
    }


    //Biome Tags

    public static final TagKey<Biome> ALLOWS_SHUB_NIGGURRATH_TRANSFORMATION = makeBiomeTag(new ResourceLocation(Occultism.MODID, "allows_shub_niggurath_transformation"));


    public static TagKey<Item> makeItemTag(String id) {
        return makeItemTag(new ResourceLocation(id));
    }

    public static TagKey<Item> makeItemTag(ResourceLocation id) {
        return TagKey.create(Registries.ITEM, id);
    }

    public static TagKey<Block> makeBlockTag(String id) {
        return makeBlockTag(new ResourceLocation(id));
    }

    public static TagKey<Block> makeBlockTag(ResourceLocation id) {
        return TagKey.create(Registries.BLOCK, id);
    }

    public static TagKey<EntityType<?>> makeEntityTypeTag(String id) {
        return makeEntityTypeTag(new ResourceLocation(id));
    }

    public static TagKey<EntityType<?>> makeEntityTypeTag(ResourceLocation id) {
        return TagKey.create(Registries.ENTITY_TYPE, id);
    }

    public static TagKey<Biome> makeBiomeTag(String id) {
        return makeBiomeTag(new ResourceLocation(id));
    }

    public static TagKey<Biome> makeBiomeTag(ResourceLocation id) {
        return TagKey.create(Registries.BIOME, id);
    }
}
