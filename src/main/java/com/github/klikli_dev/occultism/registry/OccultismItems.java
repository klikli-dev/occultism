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

package com.github.klikli_dev.occultism.registry;

import com.github.klikli_dev.occultism.Occultism;
import com.github.klikli_dev.occultism.TranslationKeys;
import com.github.klikli_dev.occultism.api.common.misc.OccultismItemTier;
import com.github.klikli_dev.occultism.common.item.DummyTooltipItem;
import com.github.klikli_dev.occultism.common.item.armor.OtherworldGogglesItem;
import com.github.klikli_dev.occultism.common.item.debug.*;
import com.github.klikli_dev.occultism.common.item.otherworld.OtherworldBlockItem;
import com.github.klikli_dev.occultism.common.item.spirit.*;
import com.github.klikli_dev.occultism.common.item.storage.DimensionalMatrixItem;
import com.github.klikli_dev.occultism.common.item.storage.SatchelItem;
import com.github.klikli_dev.occultism.common.item.storage.StableWormholeBlockItem;
import com.github.klikli_dev.occultism.common.item.storage.StorageRemoteItem;
import com.github.klikli_dev.occultism.common.item.tool.*;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.*;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class OccultismItems {

    //region Fields
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Occultism.MODID);

    //Debug and placeholder items
    public static final RegistryObject<Item> PENTACLE = ITEMS.register("pentacle",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> DEBUG_WAND = ITEMS.register("debug_wand",
            () -> new DebugWandItem(defaultProperties().stacksTo(1)));
    public static final RegistryObject<Item> DEBUG_FOLIOT_LUMBERJACK = ITEMS.register("debug_foliot_lumberjack",
            () -> new SummonFoliotLumberjackItem(defaultProperties().stacksTo(1)));
    public static final RegistryObject<Item> DEBUG_FOLIOT_TRANSPORT_ITEMS =
            ITEMS.register("debug_foliot_transport_items",
                    () -> new SummonFoliotTransportItemsItem(defaultProperties().stacksTo(1)));
    public static final RegistryObject<Item> DEBUG_FOLIOT_CLEANER =
            ITEMS.register("debug_foliot_cleaner",
                    () -> new SummonFoliotCleanerItem(defaultProperties().stacksTo(1)));
    public static final RegistryObject<Item> DEBUG_FOLIOT_TRADER_ITEM = ITEMS.register("debug_foliot_trader",
            () -> new SummonFoliotTraderItem(defaultProperties().stacksTo(1)));
    public static final RegistryObject<Item> DEBUG_DJINNI_MANAGE_MACHINE = ITEMS.register("debug_djinni_manage_machine",
            () -> new SummonDjinniManageMachineItem(defaultProperties().stacksTo(1)));
    public static final RegistryObject<Item> DEBUG_DJINNI_TEST = ITEMS.register("debug_djinni_test",
            () -> new SummonDjinniTest(defaultProperties().stacksTo(1)));

    public static final RegistryObject<BlockItem> SPIRIT_FIRE =
            ITEMS.register("spirit_fire", () -> new BlockItem(OccultismBlocks.SPIRIT_FIRE.get(), defaultProperties()));

    //Resources
    public static final RegistryObject<OtherworldBlockItem> OTHERWORLD_SAPLING_NATURAL =
            ITEMS.register("otherworld_sapling_natural",
                    () -> new OtherworldBlockItem(OccultismBlocks.OTHERWORLD_SAPLING_NATURAL.get(),
                            defaultProperties()));
    public static final RegistryObject<Item> TALLOW = ITEMS.register("tallow",
            () -> new Item(defaultProperties()));
    public static final RegistryObject<Item> AFRIT_ESSENCE = ITEMS.register("afrit_essence",
            () -> new Item(defaultProperties()));

    //Components
    public static final RegistryObject<DimensionalMatrixItem> DIMENSIONAL_MATRIX = ITEMS.register("dimensional_matrix",
            () -> new DimensionalMatrixItem(defaultProperties()));
    public static final RegistryObject<Item> SPIRIT_ATTUNED_GEM = ITEMS.register("spirit_attuned_gem",
            () -> new Item(defaultProperties()));
    public static final RegistryObject<Item> OTHERWORLD_ASHES = ITEMS.register("otherworld_ashes",
            () -> new Item(defaultProperties()));
    public static final RegistryObject<Item> BURNT_OTHERSTONE = ITEMS.register("burnt_otherstone",
            () -> new Item(defaultProperties()));

    public static final RegistryObject<Item> OTHERSTONE_FRAME = ITEMS.register("otherstone_frame",
            () -> new Item(defaultProperties()));
    public static final RegistryObject<Item> WORMHOLE_FRAME = ITEMS.register("wormhole_frame",
            () -> new Item(defaultProperties()));
    public static final RegistryObject<Item> OTHERSTONE_TABLET = ITEMS.register("otherstone_tablet",
            () -> new Item(defaultProperties()));
    public static final RegistryObject<Item> STORAGE_REMOTE_INERT = ITEMS.register("storage_remote_inert",
            () -> new Item(defaultProperties()));

    public static final RegistryObject<Item> CHALK_WHITE_IMPURE = ITEMS.register("chalk_white_impure",
            () -> new Item(defaultProperties()));
    public static final RegistryObject<Item> CHALK_RED_IMPURE = ITEMS.register("chalk_red_impure",
            () -> new Item(defaultProperties()));
    public static final RegistryObject<Item> CHALK_GOLD_IMPURE = ITEMS.register("chalk_gold_impure",
            () -> new Item(defaultProperties()));
    public static final RegistryObject<Item> CHALK_PURPLE_IMPURE = ITEMS.register("chalk_purple_impure",
            () -> new Item(defaultProperties()));

    public static final RegistryObject<Item> COPPER_INGOT = ITEMS.register("copper_ingot",
            () -> new Item(defaultProperties()));
    public static final RegistryObject<Item> SILVER_INGOT = ITEMS.register("silver_ingot",
            () -> new Item(defaultProperties()));
    public static final RegistryObject<Item> IESNIUM_INGOT = ITEMS.register("iesnium_ingot",
            () -> new Item(defaultProperties()));

    public static final RegistryObject<Item> COPPER_NUGGET = ITEMS.register("copper_nugget",
            () -> new Item(defaultProperties()));
    public static final RegistryObject<Item> SILVER_NUGGET = ITEMS.register("silver_nugget",
            () -> new Item(defaultProperties()));
    public static final RegistryObject<Item> IESNIUM_NUGGET = ITEMS.register("iesnium_nugget",
            () -> new Item(defaultProperties()));

    public static final RegistryObject<Item> OBSIDIAN_DUST = ITEMS.register("obsidian_dust",
            () -> new Item(defaultProperties()));
    public static final RegistryObject<Item> IRON_DUST = ITEMS.register("iron_dust",
            () -> new Item(defaultProperties()));
    public static final RegistryObject<Item> GOLD_DUST = ITEMS.register("gold_dust",
            () -> new Item(defaultProperties()));
    public static final RegistryObject<Item> COPPER_DUST = ITEMS.register("copper_dust",
            () -> new Item(defaultProperties()));
    public static final RegistryObject<Item> SILVER_DUST = ITEMS.register("silver_dust",
            () -> new Item(defaultProperties()));
    public static final RegistryObject<Item> IESNIUM_DUST = ITEMS.register("iesnium_dust",
            () -> new Item(defaultProperties()));

    public static final RegistryObject<Item> CRUSHED_END_STONE = ITEMS.register("crushed_end_stone",
            () -> new Item(defaultProperties()));

    public static final RegistryObject<Item> LENSES = ITEMS.register("lenses",
            () -> new Item(defaultProperties()));
    public static final RegistryObject<Item> INFUSED_LENSES = ITEMS.register("infused_lenses",
            () -> new Item(defaultProperties()));
    public static final RegistryObject<Item> LENS_FRAME = ITEMS.register("lens_frame",
            () -> new Item(defaultProperties()));
    public static final RegistryObject<Item> PURIFIED_INK = ITEMS.register("purified_ink",
            () -> new Item(defaultProperties()));

    public static final RegistryObject<Item> SPIRIT_ATTUNED_PICKAXE_HEAD = ITEMS.register("spirit_attuned_pickaxe_head",
            () -> new Item(defaultProperties()));

    public static final RegistryObject<Item> MAGIC_LAMP_EMPTY = ITEMS.register("magic_lamp_empty",
            () -> new Item(defaultProperties()));
    //Tools
    public static final RegistryObject<GuideBookItem> GUIDE_BOOK = ITEMS.register("dictionary_of_spirits",
            () -> new GuideBookItem(defaultProperties().stacksTo(1)));

    public static final RegistryObject<StorageRemoteItem> STORAGE_REMOTE = ITEMS.register("storage_remote",
            () -> new StorageRemoteItem(defaultProperties().stacksTo(1)));

    public static final RegistryObject<Item> CHALK_WHITE = ITEMS.register("chalk_white",
            () -> new ChalkItem(defaultProperties().setNoRepair().durability(128),
                    () -> OccultismBlocks.CHALK_GLYPH_WHITE.get()));
    public static final RegistryObject<Item> CHALK_GOLD = ITEMS.register("chalk_gold",
            () -> new ChalkItem(defaultProperties().setNoRepair().durability(128),
                    () -> OccultismBlocks.CHALK_GLYPH_GOLD.get()));
    public static final RegistryObject<Item> CHALK_PURPLE = ITEMS.register("chalk_purple",
            () -> new ChalkItem(defaultProperties().setNoRepair().durability(128),
                    () -> OccultismBlocks.CHALK_GLYPH_PURPLE.get()));
    public static final RegistryObject<Item> CHALK_RED = ITEMS.register("chalk_red",
            () -> new ChalkItem(defaultProperties().setNoRepair().durability(128),
                    () -> OccultismBlocks.CHALK_GLYPH_RED.get()));

    public static final RegistryObject<DivinationRodItem> DIVINATION_ROD = ITEMS.register("divination_rod",
            () -> new DivinationRodItem(defaultProperties().stacksTo(1)));
    public static final RegistryObject<BrushItem> BRUSH = ITEMS.register("brush",
            () -> new BrushItem(defaultProperties().stacksTo(1)));
    public static final RegistryObject<ButcherKnifeItem> BUTCHER_KNIFE = ITEMS.register("butcher_knife",
            () -> new ButcherKnifeItem(Tiers.IRON, 3, -2.4F, defaultProperties()));

    public static final RegistryObject<InfusedPickaxeItem> INFUSED_PICKAXE = ITEMS.register("infused_pickaxe",
            () -> new InfusedPickaxeItem(OccultismItemTier.SPIRIT_ATTUNED_GEM, 1, -2.8F, defaultProperties()));
    public static final RegistryObject<OtherworldPickaxeItem> IESNIUM_PICKAXE = ITEMS.register("iesnium_pickaxe",
            () -> new OtherworldPickaxeItem(Tiers.DIAMOND, 1, -2.8F, defaultProperties()));

    public static final RegistryObject<SoulGemItem> SOUL_GEM_ITEM = ITEMS.register("soul_gem",
            () -> new SoulGemItem(defaultProperties().stacksTo(1)));

    public static final RegistryObject<Item> SATCHEL = ITEMS.register("satchel",
            () -> new SatchelItem(defaultProperties().stacksTo(1).rarity(Rarity.RARE)));

    public static final RegistryObject<Item> FAMILIAR_RING = ITEMS.register("familiar_ring",
            () -> new FamiliarRingItem(defaultProperties().stacksTo(1)));

    //Books of Binding
    public static final RegistryObject<BookOfBindingItem> BOOK_OF_BINDING_FOLIOT = ITEMS.register(
            "book_of_binding_foliot", () -> new BookOfBindingItem(defaultProperties().stacksTo(1)));
    public static final RegistryObject<BookOfBindingBoundItem> BOOK_OF_BINDING_BOUND_FOLIOT = ITEMS.register(
            "book_of_binding_bound_foliot", () -> new BookOfBindingBoundItem(defaultProperties().stacksTo(1)));
    public static final RegistryObject<BookOfBindingItem> BOOK_OF_BINDING_DJINNI = ITEMS.register(
            "book_of_binding_djinni", () -> new BookOfBindingItem(defaultProperties().stacksTo(1)));
    public static final RegistryObject<BookOfBindingBoundItem> BOOK_OF_BINDING_BOUND_DJINNI = ITEMS.register(
            "book_of_binding_bound_djinni", () -> new BookOfBindingBoundItem(defaultProperties().stacksTo(1)));
    public static final RegistryObject<BookOfBindingItem> BOOK_OF_BINDING_AFRIT = ITEMS.register(
            "book_of_binding_afrit", () -> new BookOfBindingItem(defaultProperties().stacksTo(1)));
    public static final RegistryObject<BookOfBindingBoundItem> BOOK_OF_BINDING_BOUND_AFRIT = ITEMS.register(
            "book_of_binding_bound_afrit", () -> new BookOfBindingBoundItem(defaultProperties().stacksTo(1)));
    public static final RegistryObject<BookOfBindingItem> BOOK_OF_BINDING_MARID = ITEMS.register(
            "book_of_binding_marid", () -> new BookOfBindingItem(defaultProperties().stacksTo(1)));
    public static final RegistryObject<BookOfBindingBoundItem> BOOK_OF_BINDING_BOUND_MARID = ITEMS.register(
            "book_of_binding_bound_marid", () -> new BookOfBindingBoundItem(defaultProperties().stacksTo(1)));
    //Books of Calling
    //Foliot
    public static final RegistryObject<BookOfCallingLumberjackItem> BOOK_OF_CALLING_FOLIOT_LUMBERJACK =
            ITEMS.register("book_of_calling_foliot_lumberjack",
                    () -> new BookOfCallingLumberjackItem(defaultProperties().stacksTo(1),
                            TranslationKeys.BOOK_OF_CALLING_GENERIC + "_foliot"));
    public static final RegistryObject<BookOfCallingTransportItems> BOOK_OF_CALLING_FOLIOT_TRANSPORT_ITEMS =
            ITEMS.register("book_of_calling_foliot_transport_items",
                    () -> new BookOfCallingTransportItems(defaultProperties().stacksTo(1),
                            TranslationKeys.BOOK_OF_CALLING_GENERIC + "_foliot"));
    public static final RegistryObject<BookOfCallingCleanerItem> BOOK_OF_CALLING_FOLIOT_CLEANER =
            ITEMS.register("book_of_calling_foliot_cleaner",
                    () -> new BookOfCallingCleanerItem(defaultProperties().stacksTo(1),
                            TranslationKeys.BOOK_OF_CALLING_GENERIC + "_foliot"));
    //Djinn
    public static final RegistryObject<BookOfCallingManageMachineItem> BOOK_OF_CALLING_DJINNI_MANAGE_MACHINE =
            ITEMS.register("book_of_calling_djinni_manage_machine",
                    () -> new BookOfCallingManageMachineItem(defaultProperties().stacksTo(1),
                            TranslationKeys.BOOK_OF_CALLING_GENERIC + "_djinni"));

    //Armor
    public static final RegistryObject<OtherworldGogglesItem> OTHERWORLD_GOGGLES = ITEMS.register("otherworld_goggles",
            () -> new OtherworldGogglesItem(ArmorMaterials.IRON,
                    EquipmentSlot.HEAD, defaultProperties().stacksTo(1)));

    //Machines
    public static final RegistryObject<StableWormholeBlockItem> STABLE_WORMHOLE = ITEMS.register("stable_wormhole",
            () -> new StableWormholeBlockItem(OccultismBlocks.STABLE_WORMHOLE.get(), defaultProperties()));

    //Crops
    public static final RegistryObject<Item> DATURA_SEEDS =
            ITEMS.register("datura_seeds", () -> new ItemNameBlockItem(OccultismBlocks.DATURA.get(), defaultProperties()));

    //Foods
    public static final RegistryObject<Item> DATURA = ITEMS.register("datura",
            () -> new Item(defaultProperties().food(OccultismFoods.DATURA.get())));

    //Miner Spirits
    public static final RegistryObject<MinerSpiritItem> MINER_DEBUG_UNSPECIALIZED =
            ITEMS.register("miner_debug_unspecialized",
                    () -> new MinerSpiritItem(defaultProperties().durability(10000), () -> 100, () -> 10, () -> 10000));
    public static final RegistryObject<MinerSpiritItem> MINER_FOLIOT_UNSPECIALIZED =
            ITEMS.register("miner_foliot_unspecialized",
                    () -> new MinerSpiritItem(defaultProperties()
                            .durability(
                                    Occultism.SERVER_CONFIG.dimensionalMineshaft.minerFoliotUnspecialized.durability
                                            .get()),
                            Occultism.SERVER_CONFIG.dimensionalMineshaft.minerFoliotUnspecialized.maxMiningTime::get,
                            Occultism.SERVER_CONFIG.dimensionalMineshaft.minerFoliotUnspecialized.rollsPerOperation::get,
                            Occultism.SERVER_CONFIG.dimensionalMineshaft.minerFoliotUnspecialized.durability::get));
    public static final RegistryObject<MinerSpiritItem> MINER_DJINNI_ORES = ITEMS.register("miner_djinni_ores",
            () -> new MinerSpiritItem(defaultProperties().durability(
                    Occultism.SERVER_CONFIG.dimensionalMineshaft.minerDjinniOres.durability.get())
                    , Occultism.SERVER_CONFIG.dimensionalMineshaft.minerDjinniOres.maxMiningTime::get,
                    Occultism.SERVER_CONFIG.dimensionalMineshaft.minerDjinniOres.rollsPerOperation::get,
                    Occultism.SERVER_CONFIG.dimensionalMineshaft.minerDjinniOres.durability::get));


    //JEI Dummy Items
    public static final RegistryObject<DummyTooltipItem> JEI_DUMMY_NONE = ITEMS.register(
            "jei_dummy/none", () -> new DummyTooltipItem(new Item.Properties()));
    public static final RegistryObject<DummyTooltipItem> JEI_DUMMY_REQUIRE_SACRIFICE = ITEMS.register(
            "jei_dummy/require_sacrifice", () -> new DummyTooltipItem(new Item.Properties()));
    public static final RegistryObject<DummyTooltipItem> JEI_DUMMY_REQUIRE_ITEM_USE = ITEMS.register(
            "jei_dummy/require_item_use", () -> new DummyTooltipItem(new Item.Properties()));

    //Ritual Dummy Items
    static {
        ITEMS.register("ritual_dummy/craft_dimensional_matrix", () -> new DummyTooltipItem(defaultProperties(),
                LazyOptional.of(OccultismRituals.CRAFT_DIMENSIONAL_MATRIX_RITUAL::get)));
        ITEMS.register("ritual_dummy/craft_dimensional_mineshaft", () -> new DummyTooltipItem(defaultProperties(),
                LazyOptional.of(OccultismRituals.CRAFT_DIMENSIONAL_MINESHAFT_RITUAL::get)));
        ITEMS.register("ritual_dummy/craft_infused_lenses", () -> new DummyTooltipItem(defaultProperties(),
                LazyOptional.of(OccultismRituals.CRAFT_INFUSED_LENSES_RITUAL::get)));

        ITEMS.register("ritual_dummy/craft_infused_pickaxe", () -> new DummyTooltipItem(defaultProperties(),
                LazyOptional.of(OccultismRituals.CRAFT_INFUSED_PICKAXE_RITUAL::get)));
        ITEMS.register("ritual_dummy/craft_miner_djinni_ores", () -> new DummyTooltipItem(defaultProperties(),
                LazyOptional.of(OccultismRituals.CRAFT_MINER_DJINNI_ORES_RITUAL::get)));
        ITEMS.register("ritual_dummy/craft_miner_foliot_unspecialized", () -> new DummyTooltipItem(defaultProperties(),
                LazyOptional.of(OccultismRituals.CRAFT_MINER_FOLIOT_UNSPECIALIZED_RITUAL::get)));
        ITEMS.register("ritual_dummy/craft_satchel", () -> new DummyTooltipItem(defaultProperties(),
                LazyOptional.of(OccultismRituals.CRAFT_SATCHEL_RITUAL::get)));
        ITEMS.register("ritual_dummy/craft_soul_gem", () -> new DummyTooltipItem(defaultProperties(),
                LazyOptional.of(OccultismRituals.CRAFT_SOUL_GEM_RITUAL::get)));
        ITEMS.register("ritual_dummy/craft_familiar_ring", () -> new DummyTooltipItem(defaultProperties(),
                LazyOptional.of(OccultismRituals.CRAFT_FAMILIAR_RING::get)));
        ITEMS.register("ritual_dummy/craft_stabilizer_tier1", () -> new DummyTooltipItem(defaultProperties(),
                LazyOptional.of(OccultismRituals.CRAFT_STABILIZER_TIER1_RITUAL::get)));
        ITEMS.register("ritual_dummy/craft_stabilizer_tier2", () -> new DummyTooltipItem(defaultProperties(),
                LazyOptional.of(OccultismRituals.CRAFT_STABILIZER_TIER2_RITUAL::get)));
        ITEMS.register("ritual_dummy/craft_stabilizer_tier3", () -> new DummyTooltipItem(defaultProperties(),
                LazyOptional.of(OccultismRituals.CRAFT_STABILIZER_TIER3_RITUAL::get)));
        ITEMS.register("ritual_dummy/craft_stabilizer_tier4", () -> new DummyTooltipItem(defaultProperties(),
                LazyOptional.of(OccultismRituals.CRAFT_STABILIZER_TIER4_RITUAL::get)));
        ITEMS.register("ritual_dummy/craft_stable_wormhole", () -> new DummyTooltipItem(defaultProperties(),
                LazyOptional.of(OccultismRituals.CRAFT_STABLE_WORMHOLE_RITUAL::get)));

        ITEMS.register("ritual_dummy/craft_storage_controller_base", () -> new DummyTooltipItem(defaultProperties(),
                LazyOptional.of(OccultismRituals.CRAFT_STORAGE_CONTROLLER_BASE_RITUAL::get)));
        ITEMS.register("ritual_dummy/craft_storage_remote", () -> new DummyTooltipItem(defaultProperties(),
                LazyOptional.of(OccultismRituals.CRAFT_STORAGE_REMOTE_RITUAL::get)));
        ITEMS.register("ritual_dummy/familiar_otherworld_bird", () -> new DummyTooltipItem(defaultProperties(),
                LazyOptional.of(OccultismRituals.FAMILIAR_OTHERWORLD_BIRD::get)));
        ITEMS.register("ritual_dummy/familiar_parrot", () -> new DummyTooltipItem(defaultProperties(),
                LazyOptional.of(OccultismRituals.FAMILIAR_PARROT_RITUAL::get)));
        ITEMS.register("ritual_dummy/familiar_greedy", () -> new DummyTooltipItem(defaultProperties(),
                LazyOptional.of(OccultismRituals.FAMILIAR_GREEDY_RITUAL::get)));
        ITEMS.register("ritual_dummy/familiar_bat",
                () -> new DummyTooltipItem(defaultProperties(), LazyOptional.of(OccultismRituals.FAMILIAR_BAT::get)));
        ITEMS.register("ritual_dummy/familiar_deer",
                () -> new DummyTooltipItem(defaultProperties(), LazyOptional.of(OccultismRituals.FAMILIAR_DEER::get)));
        ITEMS.register("ritual_dummy/possess_enderman", () -> new DummyTooltipItem(defaultProperties(),
                LazyOptional.of(OccultismRituals.POSSESS_ENDERMAN_RITUAL::get)));
        ITEMS.register("ritual_dummy/possess_endermite", () -> new DummyTooltipItem(defaultProperties(),
                LazyOptional.of(OccultismRituals.POSSESS_ENDERMITE_RITUAL::get)));
        ITEMS.register("ritual_dummy/possess_skeleton", () -> new DummyTooltipItem(defaultProperties(),
                LazyOptional.of(OccultismRituals.POSSESS_SKELETON_RITUAL::get)));
        ITEMS.register("ritual_dummy/summon_afrit_rain_weather", () -> new DummyTooltipItem(defaultProperties(),
                LazyOptional.of(OccultismRituals.SUMMON_AFRIT_RAIN_WEATHER_RITUAL::get)));
        ITEMS.register("ritual_dummy/summon_afrit_thunder_weather", () -> new DummyTooltipItem(defaultProperties(),
                LazyOptional.of(OccultismRituals.SUMMON_AFRIT_THUNDER_WEATHER_RITUAL::get)));
        ITEMS.register("ritual_dummy/summon_djinni_clear_weather", () -> new DummyTooltipItem(defaultProperties(),
                LazyOptional.of(OccultismRituals.SUMMON_DJINNI_CLEAR_WEATHER_RITUAL::get)));
        ITEMS.register("ritual_dummy/summon_djinni_day_time", () -> new DummyTooltipItem(defaultProperties(),
                LazyOptional.of(OccultismRituals.SUMMON_DJINNI_DAY_TIME_RITUAL::get)));

        ITEMS.register("ritual_dummy/summon_djinni_manage_machine", () -> new DummyTooltipItem(defaultProperties(),
                LazyOptional.of(OccultismRituals.SUMMON_DJINNI_MANAGE_MACHINE_RITUAL::get)));
        ITEMS.register("ritual_dummy/summon_djinni_night_time", () -> new DummyTooltipItem(defaultProperties(),
                LazyOptional.of(OccultismRituals.SUMMON_DJINNI_NIGHT_TIME_RITUAL::get)));
        ITEMS.register("ritual_dummy/summon_foliot_lumberjack", () -> new DummyTooltipItem(defaultProperties(),
                LazyOptional.of(OccultismRituals.SUMMON_FOLIOT_LUMBERJACK_RITUAL::get)));
        ITEMS.register("ritual_dummy/summon_foliot_otherstone_trader", () -> new DummyTooltipItem(defaultProperties(),
                LazyOptional.of(OccultismRituals.SUMMON_FOLIOT_OTHERSTONE_TRADER_RITUAL::get)));
        ITEMS.register("ritual_dummy/summon_foliot_sapling_trader", () -> new DummyTooltipItem(defaultProperties(),
                LazyOptional.of(OccultismRituals.SUMMON_FOLIOT_SAPLING_TRADER_RITUAL::get)));
        ITEMS.register("ritual_dummy/summon_foliot_transport_items", () -> new DummyTooltipItem(defaultProperties(),
                LazyOptional.of(OccultismRituals.SUMMON_FOLIOT_TRANSPORT_ITEMS_RITUAL::get)));
        ITEMS.register("ritual_dummy/summon_foliot_cleaner", () -> new DummyTooltipItem(defaultProperties(),
                LazyOptional.of(OccultismRituals.SUMMON_FOLIOT_CLEANER_RITUAL::get)));
        ITEMS.register("ritual_dummy/summon_wild_afrit", () -> new DummyTooltipItem(defaultProperties(),
                LazyOptional.of(OccultismRituals.SUMMON_WILD_AFRIT_RITUAL::get)));
        ITEMS.register("ritual_dummy/summon_wild_hunt", () -> new DummyTooltipItem(defaultProperties(),
                LazyOptional.of(OccultismRituals.SUMMON_WILD_HUNT_RITUAL::get)));
        ITEMS.register("ritual_dummy/summon_wild_otherworld_bird", () -> new DummyTooltipItem(defaultProperties(),
                LazyOptional.of(OccultismRituals.SUMMON_WILD_OTHERWORLD_BIRD_RITUAL::get)));
        ITEMS.register("ritual_dummy/summon_wild_parrot", () -> new DummyTooltipItem(defaultProperties(),
                LazyOptional.of(OccultismRituals.SUMMON_WILD_PARROT_RITUAL::get)));

        ITEMS.register("ritual_dummy/summon_foliot_crusher", () -> new DummyTooltipItem(defaultProperties(),
                LazyOptional.of(OccultismRituals.SUMMON_FOLIOT_CRUSHER_RITUAL::get)));
        ITEMS.register("ritual_dummy/summon_djinni_crusher", () -> new DummyTooltipItem(defaultProperties(),
                LazyOptional.of(OccultismRituals.SUMMON_DJINNI_CRUSHER_RITUAL::get)));
        ITEMS.register("ritual_dummy/summon_afrit_crusher", () -> new DummyTooltipItem(defaultProperties(),
                LazyOptional.of(OccultismRituals.SUMMON_AFRIT_CRUSHER_RITUAL::get)));
        ITEMS.register("ritual_dummy/summon_marid_crusher", () -> new DummyTooltipItem(defaultProperties(), LazyOptional.of(OccultismRituals.SUMMON_MARID_CRUSHER_RITUAL::get)));

    }

    //endregion Fields

    //region Static Methods
    public static Item.Properties defaultProperties() {
        return new Item.Properties().tab(Occultism.ITEM_GROUP);
    }
    //endregion Static Methods
}
