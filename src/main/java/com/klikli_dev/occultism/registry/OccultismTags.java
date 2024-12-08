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

        public static final TagKey<Block> PENTACLE_MATERIALS = makeBlockTag(ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "pentacle_materials"));

        // Block Tags
        public static final TagKey<Block> TREE_SOIL = makeBlockTag(ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "tree_soil"));
        public static final TagKey<Block> CAVE_WALL_BLOCKS = makeBlockTag(ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "cave_wall_blocks"));
        public static final TagKey<Block> WORLDGEN_BLACKLIST = makeBlockTag(ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "worldgen_blacklist"));

        public static final TagKey<Block> NETHERRACK = makeBlockTag(ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "netherrack"));
        public static final TagKey<Block> CANDLES = makeBlockTag(ResourceLocation.fromNamespaceAndPath("minecraft", "candles"));
        public static final TagKey<Block> CENTER_SACRIFICIAL_BOWL = makeBlockTag(ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "center_sacrificial_bowl"));
        public static final TagKey<Block> FOUNDATION_GLYPHS_ANY = makeBlockTag(ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "foundation_glyphs_any"));
        public static final TagKey<Block> FOUNDATION_GLYPHS_NO_WHITE = makeBlockTag(ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "foundation_glyphs_no_white"));
        public static final TagKey<Block> FOUNDATION_GLYPHS_DARK = makeBlockTag(ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "foundation_glyphs_dark"));
        public static final TagKey<Block> GLYPHS_BLACK = makeBlockTag(ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "glyphs_black"));
        public static final TagKey<Block> GLYPHS_RED = makeBlockTag(ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "glyphs_red"));

        public static final TagKey<Block> GLYPHS_BROWN = makeBlockTag(ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "glyphs_brown"));

        public static final TagKey<Block> GLYPHS_ORANGE = makeBlockTag(ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "glyphs_orange"));

        public static final TagKey<Block> GLYPHS_YELLOW = makeBlockTag(ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "glyphs_yellow"));

        public static final TagKey<Block> GLYPHS_LIME = makeBlockTag(ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "glyphs_lime"));

        public static final TagKey<Block> GLYPHS_GREEN = makeBlockTag(ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "glyphs_green"));

        public static final TagKey<Block> GLYPHS_CYAN = makeBlockTag(ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "glyphs_cyan"));

        public static final TagKey<Block> GLYPHS_BLUE = makeBlockTag(ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "glyphs_blue"));

        public static final TagKey<Block> GLYPHS_LIGHT_BLUE = makeBlockTag(ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "glyphs_light_blue"));

        public static final TagKey<Block> GLYPHS_PINK = makeBlockTag(ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "glyphs_pink"));
        public static final TagKey<Block> GLYPHS_MAGENTA = makeBlockTag(ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "glyphs_magenta"));
        public static final TagKey<Block> GLYPHS_PURPLE = makeBlockTag(ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "glyphs_purple"));
        public static final TagKey<Block> CHALK_GLYPHS = makeBlockTag(ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "chalk_glyphs"));
        public static final TagKey<Block> IESNIUM_ORE = makeBlockTag(ResourceLocation.fromNamespaceAndPath("c", "ores/iesnium"));
        public static final TagKey<Block> SILVER_ORE = makeBlockTag(ResourceLocation.fromNamespaceAndPath("c", "ores/silver"));
        // Storage Bock Tags
        public static final TagKey<Block> STORAGE_BLOCKS_IESNIUM = makeBlockTag(ResourceLocation.fromNamespaceAndPath("c", "storage_blocks/iesnium"));
        public static final TagKey<Block> STORAGE_BLOCKS_SILVER = makeBlockTag(ResourceLocation.fromNamespaceAndPath("c", "storage_blocks/silver"));
        public static final TagKey<Block> STORAGE_BLOCKS_RAW_IESNIUM = makeBlockTag(ResourceLocation.fromNamespaceAndPath("c", "storage_blocks/raw_iesnium"));
        public static final TagKey<Block> STORAGE_BLOCKS_RAW_SILVER = makeBlockTag(ResourceLocation.fromNamespaceAndPath("c", "storage_blocks/raw_silver"));

        public static final TagKey<Block> OTHERWORLD_SAPLINGS = makeBlockTag(ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "saplings/otherworld"));
        public static final TagKey<Block> OTHERWORLD_SAPLINGS_NATURAL = makeBlockTag(ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "saplings/otherworld_natural"));
        public static final TagKey<Block> OCCULTISM_CANDLES = makeBlockTag(ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "candles"));
        public static final TagKey<Block> STORAGE_STABILIZER = makeBlockTag(ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "storage_stabilizer"));
        public static final TagKey<Block> OTHERWORLD_LOGS = makeBlockTag(ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "otherworld_logs"));
        public static final TagKey<Block> MUSHROOM_BLOCKS = makeBlockTag(ResourceLocation.fromNamespaceAndPath("c", "mushroom_blocks"));
    }

    public static class Items {

        public static final TagKey<Item> PENTACLE_MATERIALS = makeItemTag(ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "pentacle_materials"));
        public static final TagKey<Item> SKULLS = makeItemTag(ResourceLocation.fromNamespaceAndPath("c", "skulls"));
        public static final TagKey<Item> OCCULTISM_CANDLES = makeItemTag(ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "candles"));
        public static final TagKey<Item> SCUTESHELL = makeItemTag(ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "armored_items"));

        public static final TagKey<Item> DEMONIC_PARTNER_FOOD = makeItemTag(ResourceLocation.fromNamespaceAndPath("occultism", "demonic_partner_food"));

        public static final TagKey<Item> IESNIUM_ORE = makeItemTag(ResourceLocation.fromNamespaceAndPath("c", "ores/iesnium"));
        public static final TagKey<Item> SILVER_ORE = makeItemTag(ResourceLocation.fromNamespaceAndPath("c", "ores/silver"));
        public static final TagKey<Item> RAW_IESNIUM = makeItemTag(ResourceLocation.fromNamespaceAndPath("c", "raw_materials/iesnium"));
        public static final TagKey<Item> RAW_SILVER = makeItemTag(ResourceLocation.fromNamespaceAndPath("c", "raw_materials/silver"));
        public static final TagKey<Item> STORAGE_BLOCK_IESNIUM = makeItemTag(ResourceLocation.fromNamespaceAndPath("c", "storage_blocks/iesnium"));
        public static final TagKey<Item> STORAGE_BLOCK_SILVER = makeItemTag(ResourceLocation.fromNamespaceAndPath("c", "storage_blocks/silver"));
        public static final TagKey<Item> STORAGE_BLOCK_RAW_IESNIUM = makeItemTag(ResourceLocation.fromNamespaceAndPath("c", "storage_blocks/raw_iesnium"));
        public static final TagKey<Item> STORAGE_BLOCK_RAW_SILVER = makeItemTag(ResourceLocation.fromNamespaceAndPath("c", "storage_blocks/raw_silver"));

        public static final TagKey<Item> MUSHROOM_BLOCKS = makeItemTag(ResourceLocation.fromNamespaceAndPath("c", "mushroom_blocks"));
        // Dusts
        public static final TagKey<Item> COPPER_DUST=makeItemTag(ResourceLocation.fromNamespaceAndPath("c", "dusts/copper"));
        public static final TagKey<Item> END_STONE_DUST=makeItemTag(ResourceLocation.fromNamespaceAndPath("c", "dusts/end_stone"));
        public static final TagKey<Item> GOLD_DUST=makeItemTag(ResourceLocation.fromNamespaceAndPath("c", "dusts/gold"));
        public static final TagKey<Item> IRON_DUST=makeItemTag(ResourceLocation.fromNamespaceAndPath("c", "dusts/iron"));
        public static final TagKey<Item> IESNIUM_DUST=makeItemTag(ResourceLocation.fromNamespaceAndPath("c", "dusts/iesnium"));
        public static final TagKey<Item> SILVER_DUST=makeItemTag(ResourceLocation.fromNamespaceAndPath("c", "dusts/silver"));
        public static final TagKey<Item> RAW_MATERIALS_SILVER =makeItemTag(ResourceLocation.fromNamespaceAndPath("c", "raw_materials/silver"));
        public static final TagKey<Item> OBSIDIAN_DUST=makeItemTag(ResourceLocation.fromNamespaceAndPath("c", "dusts/obsidian"));
        public static final TagKey<Item> BLAZE_DUST = makeItemTag(ResourceLocation.fromNamespaceAndPath("c", "dusts/blaze"));

        public static final TagKey<Item> AMETHYST_DUST = makeItemTag(ResourceLocation.fromNamespaceAndPath("c","dusts/amethyst"));
        public static final TagKey<Item> BLACKSTONE_DUST = makeItemTag(ResourceLocation.fromNamespaceAndPath("c","dusts/blackstone"));
        public static final TagKey<Item> BLUE_ICE_DUST = makeItemTag(ResourceLocation.fromNamespaceAndPath("c","dusts/blue_ice"));
        public static final TagKey<Item> CALCITE_DUST = makeItemTag(ResourceLocation.fromNamespaceAndPath("c","dusts/calcite"));
        public static final TagKey<Item> ICE_DUST = makeItemTag(ResourceLocation.fromNamespaceAndPath("c","dusts/ice"));
        public static final TagKey<Item> PACKED_ICE_DUST = makeItemTag(ResourceLocation.fromNamespaceAndPath("c","dusts/packed_ice"));
        public static final TagKey<Item> DRAGONYST_DUST = makeItemTag(ResourceLocation.fromNamespaceAndPath("c","dusts/dragonyst"));
        public static final TagKey<Item> ECHO_DUST = makeItemTag(ResourceLocation.fromNamespaceAndPath("c","dusts/echo"));
        public static final TagKey<Item> EMERALD_DUST = makeItemTag(ResourceLocation.fromNamespaceAndPath("c","dusts/emerald"));
        public static final TagKey<Item> LAPIS_DUST = makeItemTag(ResourceLocation.fromNamespaceAndPath("c","dusts/lapis"));
        public static final TagKey<Item> NETHERITE_DUST = makeItemTag(ResourceLocation.fromNamespaceAndPath("c","dusts/netherite"));
        public static final TagKey<Item> NETHERITE_SCRAP_DUST = makeItemTag(ResourceLocation.fromNamespaceAndPath("c","dusts/netherite_scrap"));
        public static final TagKey<Item> RESEARCH_DUST = makeItemTag(ResourceLocation.fromNamespaceAndPath("c","dusts/research"));
        public static final TagKey<Item> WITHERITE_DUST = makeItemTag(ResourceLocation.fromNamespaceAndPath("c","dusts/witherite"));
        public static final TagKey<Item> OTHERSTONE_DUST = makeItemTag(ResourceLocation.fromNamespaceAndPath("c","dusts/otherstone"));
        public static final TagKey<Item> OTHERWORLD_WOOD_DUST = makeItemTag(ResourceLocation.fromNamespaceAndPath("c","dusts/otherworld_wood"));

        public static final TagKey<Item> DATURA_CROP = makeItemTag(ResourceLocation.fromNamespaceAndPath("c", "crops/datura"));

        // Ingots
        public static final TagKey<Item> IESNIUM_INGOT = makeItemTag(ResourceLocation.fromNamespaceAndPath("c", "ingots/iesnium"));
        public static final TagKey<Item> SILVER_INGOT = makeItemTag(ResourceLocation.fromNamespaceAndPath("c", "ingots/silver"));

        // Nuggets
        public static final TagKey<Item> IESNIUM_NUGGET = makeItemTag(ResourceLocation.fromNamespaceAndPath("c", "nuggets/iesnium"));
        public static final TagKey<Item> SILVER_NUGGET = makeItemTag(ResourceLocation.fromNamespaceAndPath("c", "nuggets/silver"));

        // Seeds
        public static final TagKey<Item> DATURA_SEEDS = makeItemTag(ResourceLocation.fromNamespaceAndPath("c", "seeds/datura"));

        //Item Tags
        public static final TagKey<Item> TOOLS_KNIFE = makeItemTag(ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "tools/knife"));

        public static final TagKey<Item> OTHERWORLD_GOGGLES = makeItemTag(ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "otherworld_goggles"));
        public static final TagKey<Item> ELYTRA = makeItemTag(ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "elytra"));
        public static final TagKey<Item> BOOKS = makeItemTag(ResourceLocation.fromNamespaceAndPath("c", "books"));
        public static final TagKey<Item> FRUITS = makeItemTag(ResourceLocation.fromNamespaceAndPath("c", "fruits"));
        public static final TagKey<Item> TALLOW = makeItemTag(ResourceLocation.fromNamespaceAndPath("c", "tallow"));
        public static final TagKey<Item> BOOK_OF_CALLING_FOLIOT = makeItemTag(ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "books/book_of_calling_foliot"));
        public static final TagKey<Item> BOOK_OF_CALLING_DJINNI = makeItemTag(ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "books/book_of_calling_djinni"));
        public static final TagKey<Item> BOOKS_OF_BINDING = makeItemTag(ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "books/books_of_binding"));


        public static final TagKey<Item> MAGMA = makeItemTag(ResourceLocation.fromNamespaceAndPath("c", "magma"));

        public static final TagKey<Item> MANUALS = makeItemTag(ResourceLocation.fromNamespaceAndPath("c", "manuals"));

        public static final TagKey<Item> METAL_AXES = makeItemTag(ResourceLocation.fromNamespaceAndPath("c", "tools/metal/axes"));
        public static final TagKey<Item> OTHERWORLD_SAPLINGS = makeItemTag(ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "saplings/otherworld"));
        public static final TagKey<Item> OTHERWORLD_SAPLINGS_NATURAL = makeItemTag(ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "saplings/otherworld_natural"));

        public static class Miners {
            public static final TagKey<Item> MINERS = makeItemTag(ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "miners"));

            public static final TagKey<Item> BASIC_RESOURCES = makeItemTag(ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "miners/basic_resources"));
            public static final TagKey<Item> DEEPS = makeItemTag(ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "miners/deeps"));
            public static final TagKey<Item> MASTER = makeItemTag(ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "miners/master"));
            public static final TagKey<Item> ORES = makeItemTag(ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "miners/ores"));
            public static final TagKey<Item> ELDRITCH = makeItemTag(ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "miners/eldritch"));
        }

        public static final TagKey<Item> TOOLS_CHALK = makeItemTag(ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "tools/chalk"));
        public static final TagKey<Item> OTHERWORLD_LOGS = makeItemTag(ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "otherworld_logs"));
        public static final TagKey<Item> OTHERSTONE = makeItemTag(ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "otherstone"));
        public static final TagKey<Item> OTHERCOBBLESTONE = makeItemTag(ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "othercobblestone"));
    }

    public static class Entities {
        public static final TagKey<EntityType<?>> AXOLOTL = makeEntityTypeTag(ResourceLocation.fromNamespaceAndPath("c", "axolotls"));
        public static final TagKey<EntityType<?>> CHICKEN = makeEntityTypeTag(ResourceLocation.fromNamespaceAndPath("c", "chickens"));
        public static final TagKey<EntityType<?>> PARROTS = makeEntityTypeTag(ResourceLocation.fromNamespaceAndPath("c", "parrots"));
        public static final TagKey<EntityType<?>> PIGS = makeEntityTypeTag(ResourceLocation.fromNamespaceAndPath("c", "pigs"));
        public static final TagKey<EntityType<?>> COWS = makeEntityTypeTag(ResourceLocation.fromNamespaceAndPath("c", "cows"));
        public static final TagKey<EntityType<?>> VILLAGERS = makeEntityTypeTag(ResourceLocation.fromNamespaceAndPath("c", "villagers"));
        public static final TagKey<EntityType<?>> ZOMBIES = makeEntityTypeTag(ResourceLocation.fromNamespaceAndPath("c", "zombies"));
        public static final TagKey<EntityType<?>> BATS = makeEntityTypeTag(ResourceLocation.fromNamespaceAndPath("c", "bats"));
        public static final TagKey<EntityType<?>> BEES = makeEntityTypeTag(ResourceLocation.fromNamespaceAndPath("c", "bees"));
        public static final TagKey<EntityType<?>> SNOW_GOLEM=makeEntityTypeTag(ResourceLocation.fromNamespaceAndPath("c", "golems/snow"));
        public static final TagKey<EntityType<?>> IRON_GOLEM=makeEntityTypeTag(ResourceLocation.fromNamespaceAndPath("c", "golems/iron"));
        public static final TagKey<EntityType<?>> DONKEYS = makeEntityTypeTag(ResourceLocation.fromNamespaceAndPath("c", "donkeys"));
        public static final TagKey<EntityType<?>> FISH = makeEntityTypeTag(ResourceLocation.fromNamespaceAndPath("c", "fish"));
        public static final TagKey<EntityType<?>> GOATS = makeEntityTypeTag(ResourceLocation.fromNamespaceAndPath("c", "goats"));
        public static final TagKey<EntityType<?>> HOGLINS = makeEntityTypeTag(ResourceLocation.fromNamespaceAndPath("c", "hoglins"));
        public static final TagKey<EntityType<?>> HORSES = makeEntityTypeTag(ResourceLocation.fromNamespaceAndPath("c", "horses"));
        public static final TagKey<EntityType<?>> LLAMAS = makeEntityTypeTag(ResourceLocation.fromNamespaceAndPath("c", "llamas"));
        public static final TagKey<EntityType<?>> MULES = makeEntityTypeTag(ResourceLocation.fromNamespaceAndPath("c", "mules"));
        public static final TagKey<EntityType<?>> PANDAS = makeEntityTypeTag(ResourceLocation.fromNamespaceAndPath("c", "pandas"));
        public static final TagKey<EntityType<?>> SHEEP = makeEntityTypeTag(ResourceLocation.fromNamespaceAndPath("c", "sheep"));
        public static final TagKey<EntityType<?>> SPIDERS = makeEntityTypeTag(ResourceLocation.fromNamespaceAndPath("c", "spiders"));
        public static final TagKey<EntityType<?>> SQUID = makeEntityTypeTag(ResourceLocation.fromNamespaceAndPath("c", "squids"));
        public static final TagKey<EntityType<?>> TADPOLES = makeEntityTypeTag(ResourceLocation.fromNamespaceAndPath("c", "tadpoles"));
        public static final TagKey<EntityType<?>> CAMEL = makeEntityTypeTag(ResourceLocation.fromNamespaceAndPath("c", "camels"));
        public static final TagKey<EntityType<?>> DOLPHIN = makeEntityTypeTag(ResourceLocation.fromNamespaceAndPath("c", "dolphins"));
        public static final TagKey<EntityType<?>> WOLFS = makeEntityTypeTag(ResourceLocation.fromNamespaceAndPath("c", "wolfs"));
        public static final TagKey<EntityType<?>> OCELOT = makeEntityTypeTag(ResourceLocation.fromNamespaceAndPath("c", "ocelots"));
        public static final TagKey<EntityType<?>> CATS = makeEntityTypeTag(ResourceLocation.fromNamespaceAndPath("c", "cats"));
        public static final TagKey<EntityType<?>> WARDEN = makeEntityTypeTag(ResourceLocation.fromNamespaceAndPath("c", "wardens"));
        public static final TagKey<EntityType<?>> RAVAGER = makeEntityTypeTag(ResourceLocation.fromNamespaceAndPath("c", "ravagers"));
        public static final TagKey<EntityType<?>> VEX = makeEntityTypeTag(ResourceLocation.fromNamespaceAndPath("c", "vex"));
        public static final TagKey<EntityType<?>> ALLAY = makeEntityTypeTag(ResourceLocation.fromNamespaceAndPath("c", "allay"));
        //Entity Tags
        public static final TagKey<EntityType<?>> SOUL_GEM_DENY_LIST = makeEntityTypeTag(ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "soul_gem_deny_list"));
        public static final TagKey<EntityType<?>> TRINITY_GEM_DENY_LIST = makeEntityTypeTag(ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "trinity_gem_deny_list"));
        public static final TagKey<EntityType<?>> AFRIT_ALLIES = makeEntityTypeTag(ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "afrit_allies"));
        public static final TagKey<EntityType<?>> WILD_HUNT = makeEntityTypeTag(ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "wild_hunt"));
        public static final TagKey<EntityType<?>> WILD_TRIAL = makeEntityTypeTag(ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "wild_trial"));
        public static final TagKey<EntityType<?>> HEALED_BY_DEMONS_DREAM_FRUIT = makeEntityTypeTag(ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "healed_by_demons_dream_fruit"));
        public static final TagKey<EntityType<?>> CUBEMOB = makeEntityTypeTag(ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "cubemob"));
        public static final TagKey<EntityType<?>> FLYING_PASSIVE = makeEntityTypeTag(ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "flying_passive"));
        public static final TagKey<EntityType<?>> HUMANS = makeEntityTypeTag(ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "humans"));

        public static final TagKey<EntityType<?>> RANDOM_ANIMALS_COMMON = makeEntityTypeTag(ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "random_animals_common"));
        public static final TagKey<EntityType<?>> RANDOM_ANIMALS_WATER = makeEntityTypeTag(ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "random_animals_water"));
        public static final TagKey<EntityType<?>> RANDOM_ANIMALS_SMALL = makeEntityTypeTag(ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "random_animals_small"));
        public static final TagKey<EntityType<?>> RANDOM_ANIMALS_SPECIAL = makeEntityTypeTag(ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "random_animals_special"));
        public static final TagKey<EntityType<?>> RANDOM_ANIMALS_RIDEABLE = makeEntityTypeTag(ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "random_animals_rideable"));
    }


    //Biome Tags

    public static final TagKey<Biome> ALLOWS_SHUB_NIGGURRATH_TRANSFORMATION = makeBiomeTag(ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "allows_shub_niggurath_transformation"));


    public static TagKey<Item> makeItemTag(String id) {
        return makeItemTag(ResourceLocation.parse(id));
    }

    public static TagKey<Item> makeItemTag(ResourceLocation id) {
        return TagKey.create(Registries.ITEM, id);
    }

    public static TagKey<Block> makeBlockTag(String id) {
        return makeBlockTag(ResourceLocation.parse(id));
    }

    public static TagKey<Block> makeBlockTag(ResourceLocation id) {
        return TagKey.create(Registries.BLOCK, id);
    }

    public static TagKey<EntityType<?>> makeEntityTypeTag(String id) {
        return makeEntityTypeTag(ResourceLocation.parse(id));
    }

    public static TagKey<EntityType<?>> makeEntityTypeTag(ResourceLocation id) {
        return TagKey.create(Registries.ENTITY_TYPE, id);
    }

    public static TagKey<Biome> makeBiomeTag(String id) {
        return makeBiomeTag(ResourceLocation.parse(id));
    }

    public static TagKey<Biome> makeBiomeTag(ResourceLocation id) {
        return TagKey.create(Registries.BIOME, id);
    }
}
