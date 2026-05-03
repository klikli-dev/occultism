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
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;

public class OccultismTags {
    public static final TagKey<Biome> ALLOWS_SHUB_NIGGURRATH_TRANSFORMATION = makeBiomeTag(Identifier.fromNamespaceAndPath(Occultism.MODID, "allows_shub_niggurath_transformation"));

    public static TagKey<Item> makeItemTag(String id) {
        return makeItemTag(Identifier.parse(id));
    }

    public static TagKey<Item> makeItemTag(Identifier id) {
        return TagKey.create(Registries.ITEM, id);
    }


    //Biome Tags

    public static TagKey<Block> makeBlockTag(String id) {
        return makeBlockTag(Identifier.parse(id));
    }

    public static TagKey<Block> makeBlockTag(Identifier id) {
        return TagKey.create(Registries.BLOCK, id);
    }

    public static TagKey<EntityType<?>> makeEntityTypeTag(String id) {
        return makeEntityTypeTag(Identifier.parse(id));
    }

    public static TagKey<EntityType<?>> makeEntityTypeTag(Identifier id) {
        return TagKey.create(Registries.ENTITY_TYPE, id);
    }

    public static TagKey<Biome> makeBiomeTag(String id) {
        return makeBiomeTag(Identifier.parse(id));
    }

    public static TagKey<Biome> makeBiomeTag(Identifier id) {
        return TagKey.create(Registries.BIOME, id);
    }

    public static class Blocks {

        public static final TagKey<Block> PENTACLE_MATERIALS = makeBlockTag(Identifier.fromNamespaceAndPath(Occultism.MODID, "pentacle_materials"));
        public static final TagKey<Block> OTHERWORLD_COLLECTS = makeBlockTag(Identifier.fromNamespaceAndPath(Occultism.MODID, "otherworld_collects"));
        // Block Tags
        public static final TagKey<Block> TREE_SOIL = makeBlockTag(Identifier.fromNamespaceAndPath(Occultism.MODID, "tree_soil"));
        public static final TagKey<Block> CAVE_WALL_BLOCKS = makeBlockTag(Identifier.fromNamespaceAndPath(Occultism.MODID, "cave_wall_blocks"));
        public static final TagKey<Block> WORLDGEN_BLACKLIST = makeBlockTag(Identifier.fromNamespaceAndPath(Occultism.MODID, "worldgen_blacklist"));

        public static final TagKey<Block> NETHERRACK = makeBlockTag(Identifier.fromNamespaceAndPath(Occultism.MODID, "netherrack"));
        public static final TagKey<Block> CANDLES = makeBlockTag(Identifier.fromNamespaceAndPath("minecraft", "candles"));
        public static final TagKey<Block> CENTER_SACRIFICIAL_BOWL = makeBlockTag(Identifier.fromNamespaceAndPath(Occultism.MODID, "center_sacrificial_bowl"));
        public static final TagKey<Block> FOUNDATION_GLYPHS_ANY = makeBlockTag(Identifier.fromNamespaceAndPath(Occultism.MODID, "foundation_glyphs_any"));
        public static final TagKey<Block> FOUNDATION_GLYPHS_NO_WHITE = makeBlockTag(Identifier.fromNamespaceAndPath(Occultism.MODID, "foundation_glyphs_no_white"));
        public static final TagKey<Block> FOUNDATION_GLYPHS_DARK = makeBlockTag(Identifier.fromNamespaceAndPath(Occultism.MODID, "foundation_glyphs_dark"));
        public static final TagKey<Block> GLYPHS_BLACK = makeBlockTag(Identifier.fromNamespaceAndPath(Occultism.MODID, "glyphs_black"));
        public static final TagKey<Block> GLYPHS_RED = makeBlockTag(Identifier.fromNamespaceAndPath(Occultism.MODID, "glyphs_red"));

        public static final TagKey<Block> GLYPHS_BROWN = makeBlockTag(Identifier.fromNamespaceAndPath(Occultism.MODID, "glyphs_brown"));

        public static final TagKey<Block> GLYPHS_ORANGE = makeBlockTag(Identifier.fromNamespaceAndPath(Occultism.MODID, "glyphs_orange"));

        public static final TagKey<Block> GLYPHS_YELLOW = makeBlockTag(Identifier.fromNamespaceAndPath(Occultism.MODID, "glyphs_yellow"));

        public static final TagKey<Block> GLYPHS_LIME = makeBlockTag(Identifier.fromNamespaceAndPath(Occultism.MODID, "glyphs_lime"));

        public static final TagKey<Block> GLYPHS_GREEN = makeBlockTag(Identifier.fromNamespaceAndPath(Occultism.MODID, "glyphs_green"));

        public static final TagKey<Block> GLYPHS_CYAN = makeBlockTag(Identifier.fromNamespaceAndPath(Occultism.MODID, "glyphs_cyan"));

        public static final TagKey<Block> GLYPHS_BLUE = makeBlockTag(Identifier.fromNamespaceAndPath(Occultism.MODID, "glyphs_blue"));

        public static final TagKey<Block> GLYPHS_LIGHT_BLUE = makeBlockTag(Identifier.fromNamespaceAndPath(Occultism.MODID, "glyphs_light_blue"));

        public static final TagKey<Block> GLYPHS_PINK = makeBlockTag(Identifier.fromNamespaceAndPath(Occultism.MODID, "glyphs_pink"));
        public static final TagKey<Block> GLYPHS_MAGENTA = makeBlockTag(Identifier.fromNamespaceAndPath(Occultism.MODID, "glyphs_magenta"));
        public static final TagKey<Block> GLYPHS_PURPLE = makeBlockTag(Identifier.fromNamespaceAndPath(Occultism.MODID, "glyphs_purple"));
        public static final TagKey<Block> CHALK_GLYPHS = makeBlockTag(Identifier.fromNamespaceAndPath(Occultism.MODID, "chalk_glyphs"));
        public static final TagKey<Block> IESNIUM_ORE = makeBlockTag(Identifier.fromNamespaceAndPath("c", "ores/iesnium"));
        public static final TagKey<Block> SILVER_ORE = makeBlockTag(Identifier.fromNamespaceAndPath("c", "ores/silver"));
        // Storage Bock Tags
        public static final TagKey<Block> STORAGE_BLOCKS_IESNIUM = makeBlockTag(Identifier.fromNamespaceAndPath("c", "storage_blocks/iesnium"));
        public static final TagKey<Block> STORAGE_BLOCKS_SILVER = makeBlockTag(Identifier.fromNamespaceAndPath("c", "storage_blocks/silver"));
        public static final TagKey<Block> STORAGE_BLOCKS_RAW_IESNIUM = makeBlockTag(Identifier.fromNamespaceAndPath("c", "storage_blocks/raw_iesnium"));
        public static final TagKey<Block> STORAGE_BLOCKS_RAW_SILVER = makeBlockTag(Identifier.fromNamespaceAndPath("c", "storage_blocks/raw_silver"));

        public static final TagKey<Block> OTHERWORLD_SAPLINGS = makeBlockTag(Identifier.fromNamespaceAndPath(Occultism.MODID, "saplings/otherworld"));
        public static final TagKey<Block> OTHERWORLD_SAPLINGS_NATURAL = makeBlockTag(Identifier.fromNamespaceAndPath(Occultism.MODID, "saplings/otherworld_natural"));
        public static final TagKey<Block> OCCULTISM_CANDLES = makeBlockTag(Identifier.fromNamespaceAndPath(Occultism.MODID, "candles"));
        public static final TagKey<Block> STORAGE_STABILIZER = makeBlockTag(Identifier.fromNamespaceAndPath(Occultism.MODID, "storage_stabilizer"));
        public static final TagKey<Block> OTHERWORLD_LOGS = makeBlockTag(Identifier.fromNamespaceAndPath(Occultism.MODID, "otherworld_logs"));
        public static final TagKey<Block> MUSHROOM_BLOCKS = makeBlockTag(Identifier.fromNamespaceAndPath("c", "mushroom_blocks"));
        public static final TagKey<Block> LIGHTNING_RODS = makeBlockTag(Identifier.fromNamespaceAndPath("c", "lightning_rods"));
        public static final TagKey<Block> ENCHANTING_TABLES = makeBlockTag(Identifier.fromNamespaceAndPath("c", "enchanting_tables"));
        public static final TagKey<Block> IRON_BARS = makeBlockTag(Identifier.fromNamespaceAndPath("c", "iron_bars"));
        public static final TagKey<Block> BLOCKED_PASTE = makeBlockTag(Identifier.fromNamespaceAndPath("c", "blocked_for_paste_replicate"));
    }

    public static class Items {
        public static final TagKey<Item> START_SPIRIT_FIRE = makeItemTag(Identifier.fromNamespaceAndPath(Occultism.MODID, "start_spiritfire"));
        public static final TagKey<Item> PENTACLE_MATERIALS = makeItemTag(Identifier.fromNamespaceAndPath(Occultism.MODID, "pentacle_materials"));
        public static final TagKey<Item> SKULLS = makeItemTag(Identifier.fromNamespaceAndPath("c", "skulls"));
        public static final TagKey<Item> OCCULTISM_CANDLES = makeItemTag(Identifier.fromNamespaceAndPath(Occultism.MODID, "candles"));
        public static final TagKey<Item> SCUTESHELL = makeItemTag(Identifier.fromNamespaceAndPath(Occultism.MODID, "armored_items"));

        public static final TagKey<Item> DEMONIC_PARTNER_FOOD = makeItemTag(Identifier.fromNamespaceAndPath("occultism", "demonic_partner_food"));

        public static final TagKey<Item> IESNIUM_ORE = makeItemTag(Identifier.fromNamespaceAndPath("c", "ores/iesnium"));
        public static final TagKey<Item> SILVER_ORE = makeItemTag(Identifier.fromNamespaceAndPath("c", "ores/silver"));
        public static final TagKey<Item> RAW_IESNIUM = makeItemTag(Identifier.fromNamespaceAndPath("c", "raw_materials/iesnium"));
        public static final TagKey<Item> RAW_SILVER = makeItemTag(Identifier.fromNamespaceAndPath("c", "raw_materials/silver"));
        public static final TagKey<Item> INGOTS_SILVER = makeItemTag(Identifier.fromNamespaceAndPath("c", "ingots/silver"));
        public static final TagKey<Item> STORAGE_BLOCK_IESNIUM = makeItemTag(Identifier.fromNamespaceAndPath("c", "storage_blocks/iesnium"));
        public static final TagKey<Item> STORAGE_BLOCK_SILVER = makeItemTag(Identifier.fromNamespaceAndPath("c", "storage_blocks/silver"));
        public static final TagKey<Item> STORAGE_BLOCK_RAW_IESNIUM = makeItemTag(Identifier.fromNamespaceAndPath("c", "storage_blocks/raw_iesnium"));
        public static final TagKey<Item> STORAGE_BLOCK_RAW_SILVER = makeItemTag(Identifier.fromNamespaceAndPath("c", "storage_blocks/raw_silver"));

        public static final TagKey<Item> MUSHROOM_BLOCKS = makeItemTag(Identifier.fromNamespaceAndPath("c", "mushroom_blocks"));
        public static final TagKey<Item> LIGHTNING_RODS = makeItemTag(Identifier.fromNamespaceAndPath("c", "lightning_rods"));
        public static final TagKey<Item> ENCHANTING_TABLES = makeItemTag(Identifier.fromNamespaceAndPath("c", "enchanting_tables"));
        public static final TagKey<Item> IRON_BARS = makeItemTag(Identifier.fromNamespaceAndPath("c", "iron_bars"));
        public static final TagKey<Item> TUBE_CORALS = makeItemTag(Identifier.fromNamespaceAndPath("c", "tube_corals"));
        // Dusts
        public static final TagKey<Item> COPPER_DUST = makeItemTag(Identifier.fromNamespaceAndPath("c", "dusts/copper"));
        public static final TagKey<Item> END_STONE_DUST = makeItemTag(Identifier.fromNamespaceAndPath("c", "dusts/end_stone"));
        public static final TagKey<Item> GOLD_DUST = makeItemTag(Identifier.fromNamespaceAndPath("c", "dusts/gold"));
        public static final TagKey<Item> IRON_DUST = makeItemTag(Identifier.fromNamespaceAndPath("c", "dusts/iron"));
        public static final TagKey<Item> IESNIUM_DUST = makeItemTag(Identifier.fromNamespaceAndPath("c", "dusts/iesnium"));
        public static final TagKey<Item> SILVER_DUST = makeItemTag(Identifier.fromNamespaceAndPath("c", "dusts/silver"));
        public static final TagKey<Item> RAW_MATERIALS_SILVER = makeItemTag(Identifier.fromNamespaceAndPath("c", "raw_materials/silver"));
        public static final TagKey<Item> OBSIDIAN_DUST = makeItemTag(Identifier.fromNamespaceAndPath("c", "dusts/obsidian"));
        public static final TagKey<Item> BLAZE_DUST = makeItemTag(Identifier.fromNamespaceAndPath("c", "dusts/blaze"));

        public static final TagKey<Item> AMETHYST_DUST = makeItemTag(Identifier.fromNamespaceAndPath("c", "dusts/amethyst"));
        public static final TagKey<Item> BLACKSTONE_DUST = makeItemTag(Identifier.fromNamespaceAndPath("c", "dusts/blackstone"));
        public static final TagKey<Item> BLUE_ICE_DUST = makeItemTag(Identifier.fromNamespaceAndPath("c", "dusts/blue_ice"));
        public static final TagKey<Item> CALCITE_DUST = makeItemTag(Identifier.fromNamespaceAndPath("c", "dusts/calcite"));
        public static final TagKey<Item> ICE_DUST = makeItemTag(Identifier.fromNamespaceAndPath("c", "dusts/ice"));
        public static final TagKey<Item> PACKED_ICE_DUST = makeItemTag(Identifier.fromNamespaceAndPath("c", "dusts/packed_ice"));
        public static final TagKey<Item> DRAGONYST_DUST = makeItemTag(Identifier.fromNamespaceAndPath("c", "dusts/dragonyst"));
        public static final TagKey<Item> ECHO_DUST = makeItemTag(Identifier.fromNamespaceAndPath("c", "dusts/echo"));
        public static final TagKey<Item> EMERALD_DUST = makeItemTag(Identifier.fromNamespaceAndPath("c", "dusts/emerald"));
        public static final TagKey<Item> LAPIS_DUST = makeItemTag(Identifier.fromNamespaceAndPath("c", "dusts/lapis"));
        public static final TagKey<Item> NETHERITE_DUST = makeItemTag(Identifier.fromNamespaceAndPath("c", "dusts/netherite"));
        public static final TagKey<Item> NETHERITE_SCRAP_DUST = makeItemTag(Identifier.fromNamespaceAndPath("c", "dusts/netherite_scrap"));
        public static final TagKey<Item> RESEARCH_DUST = makeItemTag(Identifier.fromNamespaceAndPath("c", "dusts/research"));
        public static final TagKey<Item> WITHERITE_DUST = makeItemTag(Identifier.fromNamespaceAndPath("c", "dusts/witherite"));
        public static final TagKey<Item> OTHERSTONE_DUST = makeItemTag(Identifier.fromNamespaceAndPath("c", "dusts/otherstone"));
        public static final TagKey<Item> OTHERROCK_DUST = makeItemTag(Identifier.fromNamespaceAndPath("c", "dusts/otherrock"));
        public static final TagKey<Item> CHALK_BASE_DUST = makeItemTag(Identifier.fromNamespaceAndPath(Occultism.MODID, "dusts/chalk_base"));
        public static final TagKey<Item> OTHERWORLD_WOOD_DUST = makeItemTag(Identifier.fromNamespaceAndPath("c", "dusts/otherworld_wood"));

        public static final TagKey<Item> DATURA_CROP = makeItemTag(Identifier.fromNamespaceAndPath("c", "crops/datura"));

        // Ingots
        public static final TagKey<Item> SPIRIT_ATTUNED_GEM_MATERIALS = makeItemTag(Identifier.fromNamespaceAndPath(Occultism.MODID, "spirit_attuned_gem_materials"));
        public static final TagKey<Item> IESNIUM_INGOT = makeItemTag(Identifier.fromNamespaceAndPath("c", "ingots/iesnium"));
        public static final TagKey<Item> SILVER_INGOT = makeItemTag(Identifier.fromNamespaceAndPath("c", "ingots/silver"));

        // Nuggets
        public static final TagKey<Item> IESNIUM_NUGGET = makeItemTag(Identifier.fromNamespaceAndPath("c", "nuggets/iesnium"));
        public static final TagKey<Item> SILVER_NUGGET = makeItemTag(Identifier.fromNamespaceAndPath("c", "nuggets/silver"));

        // Seeds
        public static final TagKey<Item> DATURA_SEEDS = makeItemTag(Identifier.fromNamespaceAndPath("c", "seeds/datura"));

        //Item Tags
        public static final TagKey<Item> TOOLS_KNIFE = makeItemTag(Identifier.fromNamespaceAndPath(Occultism.MODID, "tools/knife"));
        public static final TagKey<Item> TOOLS_KNIFE_IESNIUM = makeItemTag(Identifier.fromNamespaceAndPath(Occultism.MODID, "tools/knife/iesnium"));

        public static final TagKey<Item> OTHERWORLD_GOGGLES = makeItemTag(Identifier.fromNamespaceAndPath(Occultism.MODID, "otherworld_goggles"));
        public static final TagKey<Item> ELYTRA = makeItemTag(Identifier.fromNamespaceAndPath(Occultism.MODID, "elytra"));
        public static final TagKey<Item> BOOKS = makeItemTag(Identifier.fromNamespaceAndPath("c", "books"));
        public static final TagKey<Item> FRUITS = makeItemTag(Identifier.fromNamespaceAndPath("c", "fruits"));
        public static final TagKey<Item> TALLOW = makeItemTag(Identifier.fromNamespaceAndPath("c", "tallow"));
        public static final TagKey<Item> BOOK_OF_CALLING_FOLIOT = makeItemTag(Identifier.fromNamespaceAndPath(Occultism.MODID, "books/book_of_calling_foliot"));
        public static final TagKey<Item> BOOK_OF_CALLING_DJINNI = makeItemTag(Identifier.fromNamespaceAndPath(Occultism.MODID, "books/book_of_calling_djinni"));
        public static final TagKey<Item> BOOKS_OF_BINDING = makeItemTag(Identifier.fromNamespaceAndPath(Occultism.MODID, "books/books_of_binding"));
        public static final TagKey<Item> BOOKS_FOR_EMPTY = makeItemTag(Identifier.fromNamespaceAndPath(Occultism.MODID, "books/books_for_empty"));
        public static final TagKey<Item> MAGMA = makeItemTag(Identifier.fromNamespaceAndPath("c", "magma"));
        public static final TagKey<Item> CLAY = makeItemTag(Identifier.fromNamespaceAndPath("c", "clay"));

        public static final TagKey<Item> MANUALS = makeItemTag(Identifier.fromNamespaceAndPath("c", "manuals"));

        public static final TagKey<Item> METAL_AXES = makeItemTag(Identifier.fromNamespaceAndPath("c", "tools/metal/axes"));
        public static final TagKey<Item> OTHERWORLD_SAPLINGS = makeItemTag(Identifier.fromNamespaceAndPath(Occultism.MODID, "saplings/otherworld"));
        public static final TagKey<Item> OTHERWORLD_SAPLINGS_NATURAL = makeItemTag(Identifier.fromNamespaceAndPath(Occultism.MODID, "saplings/otherworld_natural"));
        public static final TagKey<Item> TOOLS_CHALK = makeItemTag(Identifier.fromNamespaceAndPath(Occultism.MODID, "tools/chalk"));
        public static final TagKey<Item> OTHERWORLD_LOGS = makeItemTag(Identifier.fromNamespaceAndPath(Occultism.MODID, "otherworld_logs"));
        public static final TagKey<Item> OTHERSTONE = makeItemTag(Identifier.fromNamespaceAndPath(Occultism.MODID, "otherstone"));
        public static final TagKey<Item> OTHERCOBBLESTONE = makeItemTag(Identifier.fromNamespaceAndPath(Occultism.MODID, "othercobblestone"));
        public static final TagKey<Item> DROPS_POSSESSED_BLAZE = makeItemTag(Identifier.fromNamespaceAndPath(Occultism.MODID, "drop_from/possessed_blaze"));
        public static final TagKey<Item> DROPS_POSSESSED_BREEZE = makeItemTag(Identifier.fromNamespaceAndPath(Occultism.MODID, "drop_from/possessed_breeze"));
        public static final TagKey<Item> DROPS_POSSESSED_ELDER_GUARDIAN = makeItemTag(Identifier.fromNamespaceAndPath(Occultism.MODID, "drop_from/possessed_elder_guardian"));
        public static final TagKey<Item> DROPS_POSSESSED_ENDERMAN = makeItemTag(Identifier.fromNamespaceAndPath(Occultism.MODID, "drop_from/possessed_enderman"));
        public static final TagKey<Item> DROPS_POSSESSED_ENDERMITE = makeItemTag(Identifier.fromNamespaceAndPath(Occultism.MODID, "drop_from/possessed_endermite"));
        public static final TagKey<Item> DROPS_POSSESSED_EVOKER = makeItemTag(Identifier.fromNamespaceAndPath(Occultism.MODID, "drop_from/possessed_evoker"));
        public static final TagKey<Item> DROPS_POSSESSED_GHAST = makeItemTag(Identifier.fromNamespaceAndPath(Occultism.MODID, "drop_from/possessed_ghast"));
        public static final TagKey<Item> DROPS_POSSESSED_HOGLIN = makeItemTag(Identifier.fromNamespaceAndPath(Occultism.MODID, "drop_from/possessed_hoglin"));
        public static final TagKey<Item> DROPS_POSSESSED_PHANTOM = makeItemTag(Identifier.fromNamespaceAndPath(Occultism.MODID, "drop_from/possessed_phantom"));
        public static final TagKey<Item> DROPS_POSSESSED_SHULKER = makeItemTag(Identifier.fromNamespaceAndPath(Occultism.MODID, "drop_from/possessed_shulker"));
        public static final TagKey<Item> DROPS_POSSESSED_SKELETON = makeItemTag(Identifier.fromNamespaceAndPath(Occultism.MODID, "drop_from/possessed_skeleton"));
        public static final TagKey<Item> DROPS_POSSESSED_STRONG_BREEZE = makeItemTag(Identifier.fromNamespaceAndPath(Occultism.MODID, "drop_from/possessed_strong_breeze"));
        public static final TagKey<Item> DROPS_POSSESSED_WARDEN = makeItemTag(Identifier.fromNamespaceAndPath(Occultism.MODID, "drop_from/possessed_warden"));
        public static final TagKey<Item> DROPS_POSSESSED_WEAK_BREEZE = makeItemTag(Identifier.fromNamespaceAndPath(Occultism.MODID, "drop_from/possessed_weak_breeze"));
        public static final TagKey<Item> DROPS_POSSESSED_WEAK_SHULKER = makeItemTag(Identifier.fromNamespaceAndPath(Occultism.MODID, "drop_from/possessed_weak_shulker"));
        public static final TagKey<Item> DROPS_POSSESSED_WITCH = makeItemTag(Identifier.fromNamespaceAndPath(Occultism.MODID, "drop_from/possessed_witch"));
        public static final TagKey<Item> DROPS_POSSESSED_ZOMBIE_PIGLIN = makeItemTag(Identifier.fromNamespaceAndPath(Occultism.MODID, "drop_from/possessed_zombie_piglin"));
        public static final TagKey<Item> DROPS_POSSESSED_GUARDIAN = makeItemTag(Identifier.fromNamespaceAndPath(Occultism.MODID, "drop_from/possessed_guardian"));
        public static final TagKey<Item> DROPS_WILD_HUNT = makeItemTag(Identifier.fromNamespaceAndPath(Occultism.MODID, "drop_from/wild_hunt_wither_skeleton"));
        public static final TagKey<Item> DROPS_WILD_HORDE_CREEPER = makeItemTag(Identifier.fromNamespaceAndPath(Occultism.MODID, "drop_from/wild_horde_creeper"));
        public static final TagKey<Item> DROPS_WILD_HORDE_DROWNED = makeItemTag(Identifier.fromNamespaceAndPath(Occultism.MODID, "drop_from/wild_horde_drowned"));
        public static final TagKey<Item> DROPS_WILD_HORDE_HUSK = makeItemTag(Identifier.fromNamespaceAndPath(Occultism.MODID, "drop_from/wild_horde_husk"));
        public static final TagKey<Item> DROPS_WILD_HORDE_SILVERFISH = makeItemTag(Identifier.fromNamespaceAndPath(Occultism.MODID, "drop_from/wild_horde_silverfish"));
        public static final TagKey<Item> RANDOM_SPAWN_COMMON = makeItemTag(Identifier.fromNamespaceAndPath(Occultism.MODID, "random_spawn_from/common"));
        public static final TagKey<Item> RANDOM_SPAWN_RIDEABLE = makeItemTag(Identifier.fromNamespaceAndPath(Occultism.MODID, "random_spawn_from/rideable"));
        public static final TagKey<Item> RANDOM_SPAWN_SMALL = makeItemTag(Identifier.fromNamespaceAndPath(Occultism.MODID, "random_spawn_from/small"));
        public static final TagKey<Item> RANDOM_SPAWN_SPECIAL = makeItemTag(Identifier.fromNamespaceAndPath(Occultism.MODID, "random_spawn_from/special"));
        public static final TagKey<Item> RANDOM_SPAWN_WATER = makeItemTag(Identifier.fromNamespaceAndPath(Occultism.MODID, "random_spawn_from/water"));
        public static final TagKey<Item> RANDOM_SPAWN_VILLAGER = makeItemTag(Identifier.fromNamespaceAndPath(Occultism.MODID, "random_spawn_from/villagers"));

        public static class Miners {
            public static final TagKey<Item> MINERS = makeItemTag(Identifier.fromNamespaceAndPath(Occultism.MODID, "miners"));

            public static final TagKey<Item> BASIC = makeItemTag(Identifier.fromNamespaceAndPath(Occultism.MODID, "miners/basic"));
            public static final TagKey<Item> IRON = makeItemTag(Identifier.fromNamespaceAndPath(Occultism.MODID, "miners/iron"));
            public static final TagKey<Item> DIAMOND = makeItemTag(Identifier.fromNamespaceAndPath(Occultism.MODID, "miners/diamond"));
            public static final TagKey<Item> NETHERITE = makeItemTag(Identifier.fromNamespaceAndPath(Occultism.MODID, "miners/netherite"));
            public static final TagKey<Item> ELDRITCH = makeItemTag(Identifier.fromNamespaceAndPath(Occultism.MODID, "miners/eldritch"));
        }
    }

    public static class Entities {
        //Entity Tags
        public static final TagKey<EntityType<?>> AXOLOTL = makeEntityTypeTag(Identifier.fromNamespaceAndPath("c", "axolotls"));
        public static final TagKey<EntityType<?>> CHICKEN = makeEntityTypeTag(Identifier.fromNamespaceAndPath("c", "chickens"));
        public static final TagKey<EntityType<?>> PARROTS = makeEntityTypeTag(Identifier.fromNamespaceAndPath("c", "parrots"));
        public static final TagKey<EntityType<?>> PIGS = makeEntityTypeTag(Identifier.fromNamespaceAndPath("c", "pigs"));
        public static final TagKey<EntityType<?>> COWS = makeEntityTypeTag(Identifier.fromNamespaceAndPath("c", "cows"));
        public static final TagKey<EntityType<?>> VILLAGERS = makeEntityTypeTag(Identifier.fromNamespaceAndPath("c", "villagers"));
        public static final TagKey<EntityType<?>> BATS = makeEntityTypeTag(Identifier.fromNamespaceAndPath("c", "bats"));
        public static final TagKey<EntityType<?>> BEES = makeEntityTypeTag(Identifier.fromNamespaceAndPath("c", "bees"));
        public static final TagKey<EntityType<?>> SNOW_GOLEM = makeEntityTypeTag(Identifier.fromNamespaceAndPath("c", "golems/snow"));
        public static final TagKey<EntityType<?>> IRON_GOLEM = makeEntityTypeTag(Identifier.fromNamespaceAndPath("c", "golems/iron"));
        public static final TagKey<EntityType<?>> DONKEYS = makeEntityTypeTag(Identifier.fromNamespaceAndPath("c", "donkeys"));
        public static final TagKey<EntityType<?>> FISH = makeEntityTypeTag(Identifier.fromNamespaceAndPath("c", "fish"));
        public static final TagKey<EntityType<?>> GOATS = makeEntityTypeTag(Identifier.fromNamespaceAndPath("c", "goats"));
        public static final TagKey<EntityType<?>> HOGLINS = makeEntityTypeTag(Identifier.fromNamespaceAndPath("c", "hoglins"));
        public static final TagKey<EntityType<?>> HORSES = makeEntityTypeTag(Identifier.fromNamespaceAndPath("c", "horses"));
        public static final TagKey<EntityType<?>> LLAMAS = makeEntityTypeTag(Identifier.fromNamespaceAndPath("c", "llamas"));
        public static final TagKey<EntityType<?>> MULES = makeEntityTypeTag(Identifier.fromNamespaceAndPath("c", "mules"));
        public static final TagKey<EntityType<?>> PANDAS = makeEntityTypeTag(Identifier.fromNamespaceAndPath("c", "pandas"));
        public static final TagKey<EntityType<?>> SHEEP = makeEntityTypeTag(Identifier.fromNamespaceAndPath("c", "sheep"));
        public static final TagKey<EntityType<?>> SPIDERS = makeEntityTypeTag(Identifier.fromNamespaceAndPath("c", "spiders"));
        public static final TagKey<EntityType<?>> SQUID = makeEntityTypeTag(Identifier.fromNamespaceAndPath("c", "squids"));
        public static final TagKey<EntityType<?>> TADPOLES = makeEntityTypeTag(Identifier.fromNamespaceAndPath("c", "tadpoles"));
        public static final TagKey<EntityType<?>> CAMEL = makeEntityTypeTag(Identifier.fromNamespaceAndPath("c", "camels"));
        public static final TagKey<EntityType<?>> DOLPHIN = makeEntityTypeTag(Identifier.fromNamespaceAndPath("c", "dolphins"));
        public static final TagKey<EntityType<?>> WOLFS = makeEntityTypeTag(Identifier.fromNamespaceAndPath("c", "wolfs"));
        public static final TagKey<EntityType<?>> OCELOT = makeEntityTypeTag(Identifier.fromNamespaceAndPath("c", "ocelots"));
        public static final TagKey<EntityType<?>> CATS = makeEntityTypeTag(Identifier.fromNamespaceAndPath("c", "cats"));
        public static final TagKey<EntityType<?>> WARDEN = makeEntityTypeTag(Identifier.fromNamespaceAndPath("c", "wardens"));
        public static final TagKey<EntityType<?>> RAVAGER = makeEntityTypeTag(Identifier.fromNamespaceAndPath("c", "ravagers"));
        public static final TagKey<EntityType<?>> VEX = makeEntityTypeTag(Identifier.fromNamespaceAndPath("c", "vex"));
        public static final TagKey<EntityType<?>> ALLAY = makeEntityTypeTag(Identifier.fromNamespaceAndPath("c", "allay"));
        public static final TagKey<EntityType<?>> ARMADILLOS = makeEntityTypeTag(Identifier.fromNamespaceAndPath("c", "armadillos"));

        public static final TagKey<EntityType<?>> ENDERMEN = makeEntityTypeTag(Identifier.fromNamespaceAndPath("c", "endermen"));
        public static final TagKey<EntityType<?>> SHULKER = makeEntityTypeTag(Identifier.fromNamespaceAndPath("c", "shulkers"));
        public static final TagKey<EntityType<?>> FRAGILE_SOUL_GEM_DENY_LIST = makeEntityTypeTag(Identifier.fromNamespaceAndPath(Occultism.MODID, "fragile_soul_gem_deny_list"));
        public static final TagKey<EntityType<?>> SOUL_GEM_DENY_LIST = makeEntityTypeTag(Identifier.fromNamespaceAndPath(Occultism.MODID, "soul_gem_deny_list"));
        public static final TagKey<EntityType<?>> TRINITY_GEM_DENY_LIST = makeEntityTypeTag(Identifier.fromNamespaceAndPath(Occultism.MODID, "trinity_gem_deny_list"));
        public static final TagKey<EntityType<?>> SOUL_SHATTERED_DENY_LIST = makeEntityTypeTag(Identifier.fromNamespaceAndPath(Occultism.MODID, "soul_shattered_deny_list"));
        public static final TagKey<EntityType<?>> VITALITY_COMPASS_DENY_LIST = makeEntityTypeTag(Identifier.fromNamespaceAndPath(Occultism.MODID, "vitality_compass_deny_list"));
        public static final TagKey<EntityType<?>> AFRIT_ALLIES = makeEntityTypeTag(Identifier.fromNamespaceAndPath(Occultism.MODID, "afrit_allies"));
        public static final TagKey<EntityType<?>> WILD_HUNT = makeEntityTypeTag(Identifier.fromNamespaceAndPath(Occultism.MODID, "wild_hunt"));
        public static final TagKey<EntityType<?>> WILD_TRIAL = makeEntityTypeTag(Identifier.fromNamespaceAndPath(Occultism.MODID, "wild_trial"));
        public static final TagKey<EntityType<?>> HEALED_BY_DEMONS_DREAM_FRUIT = makeEntityTypeTag(Identifier.fromNamespaceAndPath(Occultism.MODID, "healed_by_demons_dream_fruit"));
        public static final TagKey<EntityType<?>> CUBEMOB = makeEntityTypeTag(Identifier.fromNamespaceAndPath(Occultism.MODID, "cubemob"));
        public static final TagKey<EntityType<?>> FLYING_PASSIVE = makeEntityTypeTag(Identifier.fromNamespaceAndPath(Occultism.MODID, "flying_passive"));
        public static final TagKey<EntityType<?>> HUMANS = makeEntityTypeTag(Identifier.fromNamespaceAndPath(Occultism.MODID, "humans"));

        public static final TagKey<EntityType<?>> RANDOM_ANIMALS_COMMON = makeEntityTypeTag(Identifier.fromNamespaceAndPath(Occultism.MODID, "random_animals_common"));
        public static final TagKey<EntityType<?>> RANDOM_ANIMALS_WATER = makeEntityTypeTag(Identifier.fromNamespaceAndPath(Occultism.MODID, "random_animals_water"));
        public static final TagKey<EntityType<?>> RANDOM_ANIMALS_SMALL = makeEntityTypeTag(Identifier.fromNamespaceAndPath(Occultism.MODID, "random_animals_small"));
        public static final TagKey<EntityType<?>> RANDOM_ANIMALS_SPECIAL = makeEntityTypeTag(Identifier.fromNamespaceAndPath(Occultism.MODID, "random_animals_special"));
        public static final TagKey<EntityType<?>> RANDOM_ANIMALS_RIDEABLE = makeEntityTypeTag(Identifier.fromNamespaceAndPath(Occultism.MODID, "random_animals_rideable"));

        public static final TagKey<EntityType<?>> FORCE_KILL_SIMULATION = makeEntityTypeTag(Identifier.fromNamespaceAndPath(Occultism.MODID, "force_kill_simulation"));
    }
}
