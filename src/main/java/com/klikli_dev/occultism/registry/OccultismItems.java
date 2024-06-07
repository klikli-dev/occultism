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
import com.klikli_dev.occultism.TranslationKeys;
import com.klikli_dev.occultism.common.item.DummyTooltipItem;
import com.klikli_dev.occultism.common.item.armor.OtherworldGogglesItem;
import com.klikli_dev.occultism.common.item.debug.*;
import com.klikli_dev.occultism.common.item.otherworld.OtherworldBlockItem;
import com.klikli_dev.occultism.common.item.spirit.*;
import com.klikli_dev.occultism.common.item.storage.*;
import com.klikli_dev.occultism.common.item.tool.BrushItem;
import com.klikli_dev.occultism.common.item.tool.*;
import net.minecraft.core.Direction;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.ItemContainerContents;
import net.neoforged.neoforge.common.DeferredSpawnEggItem;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class OccultismItems {

    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(Occultism.MODID);

    //Debug and placeholder items
    public static final DeferredItem<Item> DICTIONARY_OF_SPIRITS_ICON =
            ITEMS.register("dictionary_of_spirits_icon", () -> new Item(new Item.Properties()));

    public static final DeferredItem<GuideBookItem> DICTIONARY_OF_SPIRITS = ITEMS.register("dictionary_of_spirits",
            () -> new GuideBookItem(defaultProperties().stacksTo(1)));

    public static final DeferredItem<Item> PENTACLE = ITEMS.register("pentacle",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> DEBUG_WAND = ITEMS.register("debug_wand",
            () -> new DebugWandItem(defaultProperties().stacksTo(1)));
    public static final DeferredItem<Item> DEBUG_FOLIOT_LUMBERJACK = ITEMS.register("debug_foliot_lumberjack",
            () -> new SummonFoliotLumberjackItem(defaultProperties().stacksTo(1)));
    public static final DeferredItem<Item> DEBUG_FOLIOT_TRANSPORT_ITEMS =
            ITEMS.register("debug_foliot_transport_items",
                    () -> new SummonFoliotTransportItemsItem(defaultProperties().stacksTo(1)));
    public static final DeferredItem<Item> DEBUG_FOLIOT_CLEANER =
            ITEMS.register("debug_foliot_cleaner",
                    () -> new SummonFoliotCleanerItem(defaultProperties().stacksTo(1)));
    public static final DeferredItem<Item> DEBUG_FOLIOT_TRADER_ITEM = ITEMS.register("debug_foliot_trader",
            () -> new SummonFoliotTraderItem(defaultProperties().stacksTo(1)));
    public static final DeferredItem<Item> DEBUG_DJINNI_MANAGE_MACHINE = ITEMS.register("debug_djinni_manage_machine",
            () -> new SummonDjinniManageMachineItem(defaultProperties().stacksTo(1)));
    public static final DeferredItem<Item> DEBUG_DJINNI_TEST = ITEMS.register("debug_djinni_test",
            () -> new SummonDjinniTest(defaultProperties().stacksTo(1)));

    public static final DeferredItem<BlockItem> SPIRIT_FIRE =
            ITEMS.register("spirit_fire", () -> new BlockItem(OccultismBlocks.SPIRIT_FIRE.get(), defaultProperties()));

    public static final DeferredItem<Item> ADVANCEMENT_ICON =
            ITEMS.register("advancement_icon", () -> new Item(new Item.Properties()));

    //Resources
    public static final DeferredItem<OtherworldBlockItem> OTHERWORLD_SAPLING_NATURAL =
            ITEMS.register("otherworld_sapling_natural",
                    () -> new OtherworldBlockItem(OccultismBlocks.OTHERWORLD_SAPLING_NATURAL.get(),
                            defaultProperties()));
    public static final DeferredItem<Item> TALLOW = ITEMS.register("tallow",
            () -> new Item(defaultProperties()));
    public static final DeferredItem<Item> AFRIT_ESSENCE = ITEMS.register("afrit_essence",
            () -> new Item(defaultProperties()));

    //Components
    public static final DeferredItem<DimensionalMatrixItem> DIMENSIONAL_MATRIX = ITEMS.register("dimensional_matrix",
            () -> new DimensionalMatrixItem(defaultProperties()));
    public static final DeferredItem<Item> SPIRIT_ATTUNED_GEM = ITEMS.register("spirit_attuned_gem",
            () -> new Item(defaultProperties()));
    public static final DeferredItem<Item> OTHERWORLD_ASHES = ITEMS.register("otherworld_ashes",
            () -> new Item(defaultProperties()));
    public static final DeferredItem<Item> BURNT_OTHERSTONE = ITEMS.register("burnt_otherstone",
            () -> new Item(defaultProperties()));

    public static final DeferredItem<Item> OTHERSTONE_FRAME = ITEMS.register("otherstone_frame",
            () -> new Item(defaultProperties()));
    public static final DeferredItem<Item> WORMHOLE_FRAME = ITEMS.register("wormhole_frame",
            () -> new Item(defaultProperties()));
    public static final DeferredItem<Item> OTHERSTONE_TABLET = ITEMS.register("otherstone_tablet",
            () -> new Item(defaultProperties()));
    public static final DeferredItem<Item> STORAGE_REMOTE_INERT = ITEMS.register("storage_remote_inert",
            () -> new Item(defaultProperties()));

    public static final DeferredItem<Item> CHALK_WHITE_IMPURE = ITEMS.register("chalk_white_impure",
            () -> new Item(defaultProperties()));
    public static final DeferredItem<Item> CHALK_RED_IMPURE = ITEMS.register("chalk_red_impure",
            () -> new Item(defaultProperties()));
    public static final DeferredItem<Item> CHALK_GOLD_IMPURE = ITEMS.register("chalk_gold_impure",
            () -> new Item(defaultProperties()));
    public static final DeferredItem<Item> CHALK_PURPLE_IMPURE = ITEMS.register("chalk_purple_impure",
            () -> new Item(defaultProperties()));

    public static final DeferredItem<Item> RAW_SILVER = ITEMS.register("raw_silver",
            () -> new Item(defaultProperties()));
    public static final DeferredItem<Item> RAW_IESNIUM = ITEMS.register("raw_iesnium",
            () -> new Item(defaultProperties()));

    public static final DeferredItem<Item> SILVER_INGOT = ITEMS.register("silver_ingot",
            () -> new Item(defaultProperties()));
    public static final DeferredItem<Item> IESNIUM_INGOT = ITEMS.register("iesnium_ingot",
            () -> new Item(defaultProperties()));

    public static final DeferredItem<Item> SILVER_NUGGET = ITEMS.register("silver_nugget",
            () -> new Item(defaultProperties()));
    public static final DeferredItem<Item> IESNIUM_NUGGET = ITEMS.register("iesnium_nugget",
            () -> new Item(defaultProperties()));

    public static final DeferredItem<Item> OBSIDIAN_DUST = ITEMS.register("obsidian_dust",
            () -> new Item(defaultProperties()));
    public static final DeferredItem<Item> IRON_DUST = ITEMS.register("iron_dust",
            () -> new Item(defaultProperties()));
    public static final DeferredItem<Item> GOLD_DUST = ITEMS.register("gold_dust",
            () -> new Item(defaultProperties()));
    public static final DeferredItem<Item> COPPER_DUST = ITEMS.register("copper_dust",
            () -> new Item(defaultProperties()));
    public static final DeferredItem<Item> SILVER_DUST = ITEMS.register("silver_dust",
            () -> new Item(defaultProperties()));
    public static final DeferredItem<Item> IESNIUM_DUST = ITEMS.register("iesnium_dust",
            () -> new Item(defaultProperties()));

    public static final DeferredItem<Item> CRUSHED_END_STONE = ITEMS.register("crushed_end_stone",
            () -> new Item(defaultProperties()));

    public static final DeferredItem<Item> LENSES = ITEMS.register("lenses",
            () -> new Item(defaultProperties()));
    public static final DeferredItem<Item> INFUSED_LENSES = ITEMS.register("infused_lenses",
            () -> new Item(defaultProperties()));
    public static final DeferredItem<Item> LENS_FRAME = ITEMS.register("lens_frame",
            () -> new Item(defaultProperties()));

    public static final DeferredItem<StorageRemoteItem> STORAGE_REMOTE = ITEMS.register("storage_remote",
            () -> new StorageRemoteItem(defaultProperties().stacksTo(1)));

    public static final DeferredItem<Item> CHALK_WHITE = ITEMS.register("chalk_white",
            () -> new ChalkItem(defaultProperties().setNoRepair().durability(128),
                    () -> OccultismBlocks.CHALK_GLYPH_WHITE.get()));
    public static final DeferredItem<Item> CHALK_GOLD = ITEMS.register("chalk_gold",
            () -> new ChalkItem(defaultProperties().setNoRepair().durability(128),
                    () -> OccultismBlocks.CHALK_GLYPH_GOLD.get()));
    public static final DeferredItem<Item> CHALK_PURPLE = ITEMS.register("chalk_purple",
            () -> new ChalkItem(defaultProperties().setNoRepair().durability(128),
                    () -> OccultismBlocks.CHALK_GLYPH_PURPLE.get()));
    public static final DeferredItem<Item> CHALK_RED = ITEMS.register("chalk_red",
            () -> new ChalkItem(defaultProperties().setNoRepair().durability(128),
                    () -> OccultismBlocks.CHALK_GLYPH_RED.get()));

    public static final DeferredItem<DivinationRodItem> DIVINATION_ROD = ITEMS.register("divination_rod",
            () -> new DivinationRodItem(defaultProperties().stacksTo(1)));
    public static final DeferredItem<BrushItem> BRUSH = ITEMS.register("brush",
            () -> new BrushItem(defaultProperties().stacksTo(1)));
    public static final DeferredItem<SwordItem> BUTCHER_KNIFE = ITEMS.register("butcher_knife",
            () -> new SwordItem(Tiers.IRON, defaultProperties().attributes(SwordItem.createAttributes(Tiers.IRON, 3, -2.4F))));

    public static final DeferredItem<Item> SPIRIT_ATTUNED_PICKAXE_HEAD = ITEMS.register("spirit_attuned_pickaxe_head",
            () -> new Item(defaultProperties()));
    public static final DeferredItem<InfusedPickaxeItem> INFUSED_PICKAXE = ITEMS.register("infused_pickaxe",
            () -> new InfusedPickaxeItem(Tiers.DIAMOND, defaultProperties().attributes(PickaxeItem.createAttributes(Tiers.DIAMOND, 1.0F, -2.8F))));
    public static final DeferredItem<OtherworldPickaxeItem> IESNIUM_PICKAXE = ITEMS.register("iesnium_pickaxe",
            () -> new OtherworldPickaxeItem(Tiers.DIAMOND, defaultProperties().attributes(PickaxeItem.createAttributes(Tiers.DIAMOND, 1.0F, -2.8F))));

    public static final DeferredItem<SoulGemItem> SOUL_GEM_ITEM = ITEMS.register("soul_gem",
            () -> new SoulGemItem(defaultProperties().stacksTo(1)));

    public static final DeferredItem<SoulShardItem> SOUL_SHARD_ITEM = ITEMS.register("soul_shard",
            () -> new SoulShardItem(defaultProperties().stacksTo(1)));

    public static final DeferredItem<Item> SATCHEL = ITEMS.register("satchel",
            () -> new SatchelItem(defaultProperties().stacksTo(1).rarity(Rarity.RARE).component(DataComponents.CONTAINER, ItemContainerContents.EMPTY)));

    public static final DeferredItem<Item> FAMILIAR_RING = ITEMS.register("familiar_ring",
            () -> new FamiliarRingItem(defaultProperties().stacksTo(1)));

    public static final DeferredItem<Item> PURIFIED_INK = ITEMS.register("purified_ink",
            () -> new Item(defaultProperties()));
    public static final DeferredItem<Item> AWAKENED_FEATHER = ITEMS.register("awakened_feather",
            () -> new Item(defaultProperties()));
    public static final DeferredItem<Item> TABOO_BOOK = ITEMS.register("taboo_book",
            () -> new Item(defaultProperties()));
    //Books of Binding
    public static final DeferredItem<BookOfBindingItem> BOOK_OF_BINDING_EMPTY = ITEMS.register(
            "book_of_binding_empty", () -> new BookOfBindingItem(defaultProperties().stacksTo(1)));
    public static final DeferredItem<BookOfBindingItem> BOOK_OF_BINDING_FOLIOT = ITEMS.register(
            "book_of_binding_foliot", () -> new BookOfBindingItem(defaultProperties().stacksTo(1)));
    public static final DeferredItem<BookOfBindingBoundItem> BOOK_OF_BINDING_BOUND_FOLIOT = ITEMS.register(
            "book_of_binding_bound_foliot", () -> new BookOfBindingBoundItem(defaultProperties().stacksTo(1)));
    public static final DeferredItem<BookOfBindingItem> BOOK_OF_BINDING_DJINNI = ITEMS.register(
            "book_of_binding_djinni", () -> new BookOfBindingItem(defaultProperties().stacksTo(1)));
    public static final DeferredItem<BookOfBindingBoundItem> BOOK_OF_BINDING_BOUND_DJINNI = ITEMS.register(
            "book_of_binding_bound_djinni", () -> new BookOfBindingBoundItem(defaultProperties().stacksTo(1)));
    public static final DeferredItem<BookOfBindingItem> BOOK_OF_BINDING_AFRIT = ITEMS.register(
            "book_of_binding_afrit", () -> new BookOfBindingItem(defaultProperties().stacksTo(1)));
    public static final DeferredItem<BookOfBindingBoundItem> BOOK_OF_BINDING_BOUND_AFRIT = ITEMS.register(
            "book_of_binding_bound_afrit", () -> new BookOfBindingBoundItem(defaultProperties().stacksTo(1)));
    public static final DeferredItem<BookOfBindingItem> BOOK_OF_BINDING_MARID = ITEMS.register(
            "book_of_binding_marid", () -> new BookOfBindingItem(defaultProperties().stacksTo(1)));
    public static final DeferredItem<BookOfBindingBoundItem> BOOK_OF_BINDING_BOUND_MARID = ITEMS.register(
            "book_of_binding_bound_marid", () -> new BookOfBindingBoundItem(defaultProperties().stacksTo(1)));
    //Books of Calling
    //Foliot
    public static final DeferredItem<BookOfCallingLumberjackItem> BOOK_OF_CALLING_FOLIOT_LUMBERJACK =
            ITEMS.register("book_of_calling_foliot_lumberjack",
                    () -> new BookOfCallingLumberjackItem(defaultProperties().stacksTo(1),
                            TranslationKeys.BOOK_OF_CALLING_GENERIC + "_foliot"));
    public static final DeferredItem<BookOfCallingTransportItems> BOOK_OF_CALLING_FOLIOT_TRANSPORT_ITEMS =
            ITEMS.register("book_of_calling_foliot_transport_items",
                    () -> new BookOfCallingTransportItems(defaultProperties().stacksTo(1),
                            TranslationKeys.BOOK_OF_CALLING_GENERIC + "_foliot"));
    public static final DeferredItem<BookOfCallingCleanerItem> BOOK_OF_CALLING_FOLIOT_CLEANER =
            ITEMS.register("book_of_calling_foliot_cleaner",
                    () -> new BookOfCallingCleanerItem(defaultProperties().stacksTo(1),
                            TranslationKeys.BOOK_OF_CALLING_GENERIC + "_foliot"));
    //Djinn
    public static final DeferredItem<BookOfCallingManageMachineItem> BOOK_OF_CALLING_DJINNI_MANAGE_MACHINE =
            ITEMS.register("book_of_calling_djinni_manage_machine",
                    () -> new BookOfCallingManageMachineItem(defaultProperties().stacksTo(1),
                            TranslationKeys.BOOK_OF_CALLING_GENERIC + "_djinni"));

    //Armor
    public static final DeferredItem<OtherworldGogglesItem> OTHERWORLD_GOGGLES = ITEMS.register("otherworld_goggles",
            () -> new OtherworldGogglesItem(ArmorMaterials.IRON,
                    ArmorItem.Type.HELMET, defaultProperties().stacksTo(1)));

    //Machines
    public static final DeferredItem<StableWormholeBlockItem> STABLE_WORMHOLE = ITEMS.register("stable_wormhole",
            () -> new StableWormholeBlockItem(OccultismBlocks.STABLE_WORMHOLE.get(), defaultProperties()));
    public static final DeferredItem<StorageControllerBlockItem> STORAGE_CONTROLLER = ITEMS.register("storage_controller",
            () -> new StorageControllerBlockItem(OccultismBlocks.STORAGE_CONTROLLER.get(), defaultProperties()));


    //Crops
    public static final DeferredItem<Item> DATURA_SEEDS =
            ITEMS.register("datura_seeds", () -> new ItemNameBlockItem(OccultismBlocks.DATURA.get(), defaultProperties().food(OccultismFoods.DATURA.get())));

    //Foods
    public static final DeferredItem<Item> DATURA = ITEMS.register("datura",
            () -> new SpiritHealingItem(defaultProperties().food(OccultismFoods.DATURA.get())));

    public static final DeferredItem<Item> DEMONS_DREAM_ESSENCE = ITEMS.register("demons_dream_essence",
            () -> new SpiritHealingItem(defaultProperties().food(OccultismFoods.DEMONS_DREAM_ESSENCE.get())));

    public static final DeferredItem<Item> OTHERWORLD_ESSENCE = ITEMS.register("otherworld_essence",
            () -> new SpiritHealingItem(defaultProperties().food(OccultismFoods.OTHERWORLD_ESSENCE.get())));

    //Miner Spirits
    public static final DeferredItem<Item> MAGIC_LAMP_EMPTY = ITEMS.register("magic_lamp_empty",
            () -> new Item(defaultProperties()));
    public static final DeferredItem<MinerSpiritItem> MINER_DEBUG_UNSPECIALIZED =
            ITEMS.register("miner_debug_unspecialized",
                    () -> new MinerSpiritItem(defaultProperties().durability(10000), () -> 100, () -> 10, () -> 10000));
    public static final DeferredItem<MinerSpiritItem> MINER_FOLIOT_UNSPECIALIZED = ITEMS.register("miner_foliot_unspecialized",
            () -> new MinerSpiritItem(defaultProperties().durability(1000),
                    Occultism.SERVER_CONFIG.dimensionalMineshaft.minerFoliotUnspecialized.maxMiningTime,
                    Occultism.SERVER_CONFIG.dimensionalMineshaft.minerFoliotUnspecialized.rollsPerOperation,
                    Occultism.SERVER_CONFIG.dimensionalMineshaft.minerFoliotUnspecialized.durability));
    public static final DeferredItem<MinerSpiritItem> MINER_DJINNI_ORES = ITEMS.register("miner_djinni_ores",
            () -> new MinerSpiritItem(defaultProperties().durability(400),
                    Occultism.SERVER_CONFIG.dimensionalMineshaft.minerDjinniOres.maxMiningTime,
                    Occultism.SERVER_CONFIG.dimensionalMineshaft.minerDjinniOres.rollsPerOperation,
                    Occultism.SERVER_CONFIG.dimensionalMineshaft.minerDjinniOres.durability));
    public static final DeferredItem<MinerSpiritItem> MINER_AFRIT_DEEPS = ITEMS.register("miner_afrit_deeps",
            () -> new MinerSpiritItem(defaultProperties().durability(800),
                    Occultism.SERVER_CONFIG.dimensionalMineshaft.minerAfritDeeps.maxMiningTime,
                    Occultism.SERVER_CONFIG.dimensionalMineshaft.minerAfritDeeps.rollsPerOperation,
                    Occultism.SERVER_CONFIG.dimensionalMineshaft.minerAfritDeeps.durability));
    public static final DeferredItem<MinerSpiritItem> MINER_MARID_MASTER = ITEMS.register("miner_marid_master",
            () -> new MinerSpiritItem(defaultProperties().durability(1600),
                    Occultism.SERVER_CONFIG.dimensionalMineshaft.minerMaridMaster.maxMiningTime,
                    Occultism.SERVER_CONFIG.dimensionalMineshaft.minerMaridMaster.rollsPerOperation,
                    Occultism.SERVER_CONFIG.dimensionalMineshaft.minerMaridMaster.durability));

    //JEI Dummy Items
    public static final DeferredItem<DummyTooltipItem> JEI_DUMMY_NONE = ITEMS.register(
            "jei_dummy/none", () -> new DummyTooltipItem(new Item.Properties()));
    public static final DeferredItem<DummyTooltipItem> JEI_DUMMY_REQUIRE_SACRIFICE = ITEMS.register(
            "jei_dummy/require_sacrifice", () -> new DummyTooltipItem(new Item.Properties()));
    public static final DeferredItem<DummyTooltipItem> JEI_DUMMY_REQUIRE_ITEM_USE = ITEMS.register(
            "jei_dummy/require_item_use", () -> new DummyTooltipItem(new Item.Properties()));

    //Deco Block Items
    public static final DeferredItem<Item> SPIRIT_TORCH = ITEMS.register("spirit_torch",
            () -> new StandingAndWallBlockItem(OccultismBlocks.SPIRIT_TORCH.get(), OccultismBlocks.SPIRIT_WALL_TORCH.get(),
                    defaultProperties(), Direction.DOWN));

    public static final DeferredItem<Item> SPAWN_EGG_FOLIOT = ITEMS.register("spawn_egg/foliot",
            () -> new DeferredSpawnEggItem(OccultismEntities.FOLIOT_TYPE::get, 0xaa728d, 0x37222c, defaultProperties()));
    public static final DeferredItem<Item> SPAWN_EGG_DJINNI = ITEMS.register("spawn_egg/djinni",
            () -> new DeferredSpawnEggItem(OccultismEntities.DJINNI_TYPE::get, 0xaa728d, 0x37222c, defaultProperties()));
    public static final DeferredItem<Item> SPAWN_EGG_AFRIT = ITEMS.register("spawn_egg/afrit",
            () -> new DeferredSpawnEggItem(OccultismEntities.AFRIT_TYPE::get, 0xaa728d, 0x37222c, defaultProperties()));
    public static final DeferredItem<Item> SPAWN_EGG_AFRIT_WILD = ITEMS.register("spawn_egg/afrit_wild",
            () -> new DeferredSpawnEggItem(OccultismEntities.AFRIT_WILD_TYPE::get, 0xaa728d, 0x37222c, defaultProperties()));
    public static final DeferredItem<Item> SPAWN_EGG_MARID = ITEMS.register("spawn_egg/marid",
            () -> new DeferredSpawnEggItem(OccultismEntities.MARID_TYPE::get, 0xaa728d, 0x37222c, defaultProperties()));

    public static final DeferredItem<Item> SPAWN_EGG_POSSESSED_ENDERMITE = ITEMS.register("spawn_egg/possessed_endermite",
            () -> new DeferredSpawnEggItem(OccultismEntities.POSSESSED_ENDERMITE_TYPE::get, 0x161616, 0x6E6E6E, defaultProperties()));
    public static final DeferredItem<Item> SPAWN_EGG_POSSESSED_SKELETON = ITEMS.register("spawn_egg/possessed_skeleton",
            () -> new DeferredSpawnEggItem(OccultismEntities.POSSESSED_SKELETON_TYPE::get, 0xC1C1C1, 0x494949, defaultProperties()));
    public static final DeferredItem<Item> SPAWN_EGG_POSSESSED_ENDERMAN = ITEMS.register("spawn_egg/possessed_enderman",
            () -> new DeferredSpawnEggItem(OccultismEntities.POSSESSED_ENDERMAN_TYPE::get, 0x161616, 0x0, defaultProperties()));
    public static final DeferredItem<Item> SPAWN_EGG_POSSESSED_GHAST = ITEMS.register("spawn_egg/possessed_ghast",
            () -> new DeferredSpawnEggItem(OccultismEntities.POSSESSED_GHAST_TYPE::get, 0xe2e2e2, 0xC1C1C1, defaultProperties()));
    public static final DeferredItem<Item> SPAWN_EGG_POSSESSED_PHANTOM = ITEMS.register("spawn_egg/possessed_phantom",
            () -> new DeferredSpawnEggItem(OccultismEntities.POSSESSED_PHANTOM_TYPE::get, 0x3f4c81, 0x6ccc00, defaultProperties()));
    public static final DeferredItem<Item> SPAWN_EGG_POSSESSED_WEAK_SHULKER = ITEMS.register("spawn_egg/possessed_weak_shulker",
            () -> new DeferredSpawnEggItem(OccultismEntities.POSSESSED_WEAK_SHULKER_TYPE::get, 0x8c628c, 0x342638, defaultProperties()));
    public static final DeferredItem<Item> SPAWN_EGG_POSSESSED_SHULKER = ITEMS.register("spawn_egg/possessed_shulker",
            () -> new DeferredSpawnEggItem(OccultismEntities.POSSESSED_SHULKER_TYPE::get, 0x8c628c, 0x342638, defaultProperties()));
    public static final DeferredItem<Item> SPAWN_EGG_POSSESSED_ELDER_GUARDIAN = ITEMS.register("spawn_egg/possessed_elder_guardian",
            () -> new DeferredSpawnEggItem(OccultismEntities.POSSESSED_ELDER_GUARDIAN_TYPE::get, 0xb5b3a3, 0x4b4d60, defaultProperties()));
    public static final DeferredItem<Item> SPAWN_EGG_WILD_HUNT_SKELETON = ITEMS.register("spawn_egg/wild_hunt_skeleton",
            () -> new DeferredSpawnEggItem(OccultismEntities.WILD_HUNT_SKELETON_TYPE::get, 12698049, 4802889, defaultProperties()));
    public static final DeferredItem<Item> SPAWN_EGG_WILD_HUNT_WITHER_SKELETON = ITEMS.register("spawn_egg/wild_hunt_wither_skeleton",
            () -> new DeferredSpawnEggItem(OccultismEntities.WILD_HUNT_WITHER_SKELETON_TYPE::get, 1315860, 4672845, defaultProperties()));
    public static final DeferredItem<Item> SPAWN_EGG_POSSESSED_WARDEN = ITEMS.register("spawn_egg/possessed_warden",
            () -> new DeferredSpawnEggItem(OccultismEntities.POSSESSED_WARDEN_TYPE::get, 0x0f4649, 0x39d6e0, defaultProperties()));

    public static final DeferredItem<Item> SPAWN_EGG_POSSESSED_HOGLIN = ITEMS.register("spawn_egg/possessed_hoglin",
            () -> new DeferredSpawnEggItem(OccultismEntities.POSSESSED_HOGLIN_TYPE::get, 0x592a10, 0xf9f3a4, defaultProperties()));

    public static final DeferredItem<Item> SPAWN_EGG_OTHERWORLD_BIRD = ITEMS.register("spawn_egg/otherworld_bird",
            () -> new DeferredSpawnEggItem(OccultismEntities.OTHERWORLD_BIRD_TYPE::get, 0x221269, 0x6b56c4, defaultProperties()));

    public static final DeferredItem<Item> SPAWN_EGG_GREEDY_FAMILIAR = ITEMS.register("spawn_egg/familiar_greedy",
            () -> new DeferredSpawnEggItem(OccultismEntities.GREEDY_FAMILIAR_TYPE::get, 0x54990f, 0x725025, defaultProperties()));
    public static final DeferredItem<Item> SPAWN_EGG_BAT_FAMILIAR = ITEMS.register("spawn_egg/familiar_bat",
            () -> new DeferredSpawnEggItem(OccultismEntities.BAT_FAMILIAR_TYPE::get, 0x434343, 0xda95de, defaultProperties()));

    public static final DeferredItem<Item> SPAWN_EGG_DEER_FAMILIAR = ITEMS.register("spawn_egg/familiar_deer",
            () -> new DeferredSpawnEggItem(OccultismEntities.DEER_FAMILIAR_TYPE::get, 0xc9833e, 0xfffdf2, defaultProperties()));
    public static final DeferredItem<Item> SPAWN_EGG_CTHULHU_FAMILIAR = ITEMS.register("spawn_egg/familiar_cthulhu",
            () -> new DeferredSpawnEggItem(OccultismEntities.CTHULHU_FAMILIAR_TYPE::get, 0x00cdc2, 0x4ae7c0, defaultProperties()));
    public static final DeferredItem<Item> SPAWN_EGG_DEVIL_FAMILIAR = ITEMS.register("spawn_egg/familiar_devil",
            () -> new DeferredSpawnEggItem(OccultismEntities.DEVIL_FAMILIAR_TYPE::get, 0xf2f0d7, 0xa01d1d, defaultProperties()));
    public static final DeferredItem<Item> SPAWN_EGG_DRAGON_FAMILIAR = ITEMS.register("spawn_egg/familiar_dragon",
            () -> new DeferredSpawnEggItem(OccultismEntities.DRAGON_FAMILIAR_TYPE::get, 0x18780f, 0x76c47b, defaultProperties()));
    public static final DeferredItem<Item> SPAWN_EGG_BLACKSMITH_FAMILIAR = ITEMS.register("spawn_egg/familiar_blacksmith",
            () -> new DeferredSpawnEggItem(OccultismEntities.BLACKSMITH_FAMILIAR_TYPE::get, 0x06bc64, 0x2b2b2b, defaultProperties()));
    public static final DeferredItem<Item> SPAWN_EGG_GUARDIAN_FAMILIAR = ITEMS.register("spawn_egg/familiar_guardian",
            () -> new DeferredSpawnEggItem(OccultismEntities.GUARDIAN_FAMILIAR_TYPE::get, 0x787878, 0x515151, defaultProperties()));
    public static final DeferredItem<Item> SPAWN_EGG_HEADLESS_FAMILIAR = ITEMS.register("spawn_egg/familiar_headless",
            () -> new DeferredSpawnEggItem(OccultismEntities.HEADLESS_FAMILIAR_TYPE::get, 0x0c0606, 0xde7900, defaultProperties()));
    public static final DeferredItem<Item> SPAWN_EGG_CHIMERA_FAMILIAR = ITEMS.register("spawn_egg/familiar_chimera",
            () -> new DeferredSpawnEggItem(OccultismEntities.CHIMERA_FAMILIAR_TYPE::get, 0xcf8441, 0x3e7922, defaultProperties()));
    public static final DeferredItem<Item> SPAWN_EGG_GOAT_FAMILIAR = ITEMS.register("spawn_egg/familiar_goat",
            () -> new DeferredSpawnEggItem(OccultismEntities.GOAT_FAMILIAR_TYPE::get, 0xe2e2e2, 0x0f0f0e, defaultProperties()));
    public static final DeferredItem<Item> SPAWN_EGG_SHUB_NIGGURATH_FAMILIAR = ITEMS.register("spawn_egg/familiar_shub_niggurath",
            () -> new DeferredSpawnEggItem(OccultismEntities.SHUB_NIGGURATH_FAMILIAR_TYPE::get, 0x362836, 0x594a3a, defaultProperties()));
    public static final DeferredItem<Item> SPAWN_EGG_BEHOLDER_FAMILIAR = ITEMS.register("spawn_egg/familiar_beholder",
            () -> new DeferredSpawnEggItem(OccultismEntities.BEHOLDER_FAMILIAR_TYPE::get, 0x340a09, 0xfffbff, defaultProperties()));
    public static final DeferredItem<Item> SPAWN_EGG_FAIRY_FAMILIAR = ITEMS.register("spawn_egg/familiar_fairy",
            () -> new DeferredSpawnEggItem(OccultismEntities.FAIRY_FAMILIAR_TYPE::get, 0xbd674c, 0xcca896, defaultProperties()));
    public static final DeferredItem<Item> SPAWN_EGG_MUMMY_FAMILIAR = ITEMS.register("spawn_egg/familiar_mummy",
            () -> new DeferredSpawnEggItem(OccultismEntities.MUMMY_FAMILIAR_TYPE::get, 0xcbb76a, 0xe0d4a3, defaultProperties()));
    public static final DeferredItem<Item> SPAWN_EGG_BEAVER_FAMILIAR = ITEMS.register("spawn_egg/familiar_beaver",
            () -> new DeferredSpawnEggItem(OccultismEntities.BEAVER_FAMILIAR_TYPE::get, 0x824a2b, 0xdd9973, defaultProperties()));
    public static final DeferredItem<Item> SPAWN_EGG_PARROT_FAMILIAR = ITEMS.register("spawn_egg/familiar_parrot",
            () -> new DeferredSpawnEggItem(() -> EntityType.PARROT, 894731, 16711680, defaultProperties()));

    public static final DeferredItem<Item> SPAWN_EGG_DEMONIC_WIFE = ITEMS.register("spawn_egg/demonic_wife", () -> new DeferredSpawnEggItem(OccultismEntities.DEMONIC_WIFE::get, 0xf2f0d7, 0xa01d1d, defaultProperties()));
    public static final DeferredItem<Item> SPAWN_EGG_DEMONIC_HUSBAND = ITEMS.register("spawn_egg/demonic_husband", () -> new DeferredSpawnEggItem(OccultismEntities.DEMONIC_HUSBAND::get, 0xf2f0d7, 0xa01d1d, defaultProperties()));


    public static final DeferredItem<Item> RITUAL_DUMMY_SUMMON_DEMONIC_WIFE = ITEMS.register("ritual_dummy/summon_demonic_wife", () -> new DummyTooltipItem(defaultProperties()));
    public static final DeferredItem<Item> RITUAL_DUMMY_SUMMON_DEMONIC_HUSBAND = ITEMS.register("ritual_dummy/summon_demonic_husband", () -> new DummyTooltipItem(defaultProperties()));

    public static final DeferredItem<Item> RITUAL_RESURRECT_FAMILIAR = ITEMS.register("ritual_dummy/resurrect_familiar", () -> new DummyTooltipItem(defaultProperties()));

    //Ritual Dummy Items
    static {
        ITEMS.register("ritual_dummy/custom_ritual", () -> new DummyTooltipItem(defaultProperties()));
        ITEMS.register("ritual_dummy/craft_dimensional_matrix", () -> new DummyTooltipItem(defaultProperties()));
        ITEMS.register("ritual_dummy/craft_dimensional_mineshaft", () -> new DummyTooltipItem(defaultProperties()));
        ITEMS.register("ritual_dummy/craft_infused_lenses", () -> new DummyTooltipItem(defaultProperties()));
        ITEMS.register("ritual_dummy/craft_infused_pickaxe", () -> new DummyTooltipItem(defaultProperties()));
        ITEMS.register("ritual_dummy/craft_miner_afrit_deeps", () -> new DummyTooltipItem(defaultProperties()));
        ITEMS.register("ritual_dummy/craft_miner_djinni_ores", () -> new DummyTooltipItem(defaultProperties()));
        ITEMS.register("ritual_dummy/craft_miner_foliot_unspecialized", () -> new DummyTooltipItem(defaultProperties()));
        ITEMS.register("ritual_dummy/craft_miner_marid_master", () -> new DummyTooltipItem(defaultProperties()));
        ITEMS.register("ritual_dummy/craft_satchel", () -> new DummyTooltipItem(defaultProperties()));
        ITEMS.register("ritual_dummy/craft_soul_gem", () -> new DummyTooltipItem(defaultProperties()));
        ITEMS.register("ritual_dummy/craft_familiar_ring", () -> new DummyTooltipItem(defaultProperties()));
        ITEMS.register("ritual_dummy/craft_stabilizer_tier1", () -> new DummyTooltipItem(defaultProperties()));
        ITEMS.register("ritual_dummy/craft_stabilizer_tier2", () -> new DummyTooltipItem(defaultProperties()));
        ITEMS.register("ritual_dummy/craft_stabilizer_tier3", () -> new DummyTooltipItem(defaultProperties()));
        ITEMS.register("ritual_dummy/craft_stabilizer_tier4", () -> new DummyTooltipItem(defaultProperties()));
        ITEMS.register("ritual_dummy/craft_stable_wormhole", () -> new DummyTooltipItem(defaultProperties()));
        ITEMS.register("ritual_dummy/craft_storage_controller_base", () -> new DummyTooltipItem(defaultProperties()));
        ITEMS.register("ritual_dummy/craft_storage_remote", () -> new DummyTooltipItem(defaultProperties()));
        ITEMS.register("ritual_dummy/familiar_otherworld_bird", () -> new DummyTooltipItem(defaultProperties()));
        ITEMS.register("ritual_dummy/familiar_parrot", () -> new DummyTooltipItem(defaultProperties()));
        ITEMS.register("ritual_dummy/familiar_greedy", () -> new DummyTooltipItem(defaultProperties()));
        ITEMS.register("ritual_dummy/familiar_bat", () -> new DummyTooltipItem(defaultProperties()));
        ITEMS.register("ritual_dummy/familiar_deer", () -> new DummyTooltipItem(defaultProperties()));
        ITEMS.register("ritual_dummy/familiar_cthulhu", () -> new DummyTooltipItem(defaultProperties()));
        ITEMS.register("ritual_dummy/familiar_devil", () -> new DummyTooltipItem(defaultProperties()));
        ITEMS.register("ritual_dummy/familiar_dragon", () -> new DummyTooltipItem(defaultProperties()));
        ITEMS.register("ritual_dummy/familiar_blacksmith", () -> new DummyTooltipItem(defaultProperties()));
        ITEMS.register("ritual_dummy/familiar_guardian", () -> new DummyTooltipItem(defaultProperties()));
        ITEMS.register("ritual_dummy/familiar_headless", () -> new DummyTooltipItem(defaultProperties()));
        ITEMS.register("ritual_dummy/familiar_chimera", () -> new DummyTooltipItem(defaultProperties()));
        ITEMS.register("ritual_dummy/familiar_beholder", () -> new DummyTooltipItem(defaultProperties()));
        ITEMS.register("ritual_dummy/familiar_fairy", () -> new DummyTooltipItem(defaultProperties()));
        ITEMS.register("ritual_dummy/familiar_mummy", () -> new DummyTooltipItem(defaultProperties()));
        ITEMS.register("ritual_dummy/familiar_beaver", () -> new DummyTooltipItem(defaultProperties()));
        ITEMS.register("ritual_dummy/possess_enderman", () -> new DummyTooltipItem(defaultProperties()));
        ITEMS.register("ritual_dummy/possess_endermite", () -> new DummyTooltipItem(defaultProperties()));
        ITEMS.register("ritual_dummy/possess_skeleton", () -> new DummyTooltipItem(defaultProperties()));
        ITEMS.register("ritual_dummy/possess_ghast", () -> new DummyTooltipItem(defaultProperties()));
        ITEMS.register("ritual_dummy/possess_phantom", () -> new DummyTooltipItem(defaultProperties()));
        ITEMS.register("ritual_dummy/possess_weak_shulker", () -> new DummyTooltipItem(defaultProperties()));
        ITEMS.register("ritual_dummy/possess_shulker", () -> new DummyTooltipItem(defaultProperties()));
        ITEMS.register("ritual_dummy/possess_elder_guardian", () -> new DummyTooltipItem(defaultProperties()));
        ITEMS.register("ritual_dummy/possess_warden", () -> new DummyTooltipItem(defaultProperties()));
        ITEMS.register("ritual_dummy/possess_hoglin", () -> new DummyTooltipItem(defaultProperties()));
        ITEMS.register("ritual_dummy/summon_afrit_rain_weather", () -> new DummyTooltipItem(defaultProperties()));
        ITEMS.register("ritual_dummy/summon_afrit_thunder_weather", () -> new DummyTooltipItem(defaultProperties()));
        ITEMS.register("ritual_dummy/summon_djinni_clear_weather", () -> new DummyTooltipItem(defaultProperties()));
        ITEMS.register("ritual_dummy/summon_djinni_day_time", () -> new DummyTooltipItem(defaultProperties()));
        ITEMS.register("ritual_dummy/summon_djinni_manage_machine", () -> new DummyTooltipItem(defaultProperties()));
        ITEMS.register("ritual_dummy/summon_djinni_night_time", () -> new DummyTooltipItem(defaultProperties()));
        ITEMS.register("ritual_dummy/summon_foliot_lumberjack", () -> new DummyTooltipItem(defaultProperties()));
        ITEMS.register("ritual_dummy/summon_foliot_otherstone_trader", () -> new DummyTooltipItem(defaultProperties()));
        ITEMS.register("ritual_dummy/summon_foliot_sapling_trader", () -> new DummyTooltipItem(defaultProperties()));
        ITEMS.register("ritual_dummy/summon_foliot_transport_items", () -> new DummyTooltipItem(defaultProperties()));
        ITEMS.register("ritual_dummy/summon_foliot_cleaner", () -> new DummyTooltipItem(defaultProperties()));
        ITEMS.register("ritual_dummy/summon_wild_afrit", () -> new DummyTooltipItem(defaultProperties()));
        ITEMS.register("ritual_dummy/summon_wild_hunt", () -> new DummyTooltipItem(defaultProperties()));
        ITEMS.register("ritual_dummy/summon_wild_otherworld_bird", () -> new DummyTooltipItem(defaultProperties()));
        ITEMS.register("ritual_dummy/summon_wild_parrot", () -> new DummyTooltipItem(defaultProperties()));
        ITEMS.register("ritual_dummy/summon_foliot_crusher", () -> new DummyTooltipItem(defaultProperties()));
        ITEMS.register("ritual_dummy/summon_djinni_crusher", () -> new DummyTooltipItem(defaultProperties()));
        ITEMS.register("ritual_dummy/summon_afrit_crusher", () -> new DummyTooltipItem(defaultProperties()));
        ITEMS.register("ritual_dummy/summon_marid_crusher", () -> new DummyTooltipItem(defaultProperties()));
    }

    public static Item.Properties defaultProperties() {
        //historically used to add to occultism tab
        return new Item.Properties();
    }
    
    public static boolean shouldSkipCreativeModTab(Item item) {
        if (item == PENTACLE.get()
                || item == DICTIONARY_OF_SPIRITS_ICON.get()
                || item == DICTIONARY_OF_SPIRITS.get()
                || item == ADVANCEMENT_ICON.get()
                || item == JEI_DUMMY_NONE.get()
                || item == JEI_DUMMY_REQUIRE_SACRIFICE.get()
                || item == JEI_DUMMY_REQUIRE_ITEM_USE.get())
            return true;

        if (item instanceof BlockItem blockItem) {
            return blockItem.getBlock() == OccultismBlocks.LIGHTED_AIR.get();
        }
        return false;
    }
}
