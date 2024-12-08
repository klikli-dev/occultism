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
import com.klikli_dev.occultism.common.item.spirit.*;
import com.klikli_dev.occultism.common.item.storage.*;
import com.klikli_dev.occultism.common.item.tool.BrushItem;
import com.klikli_dev.occultism.common.item.tool.*;
import com.klikli_dev.occultism.common.item.tool.ritual_satchel.MultiBlockRitualSatchelItem;
import com.klikli_dev.occultism.common.item.tool.ritual_satchel.SingleBlockRitualSatchelItem;
import com.klikli_dev.occultism.util.TextUtil;
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

    //Dictionary
    public static final DeferredItem<GuideBookItem> DICTIONARY_OF_SPIRITS = ITEMS.register("dictionary_of_spirits",
            () -> new GuideBookItem(defaultProperties().stacksTo(1)));

    //Tools and equipable
    public static final DeferredItem<DivinationRodItem> DIVINATION_ROD = ITEMS.register("divination_rod",
            () -> new DivinationRodItem(defaultProperties().stacksTo(1)));
    public static final DeferredItem<SwordItem> BUTCHER_KNIFE = ITEMS.register("butcher_knife",
            () -> new SwordItem(Tiers.IRON, defaultProperties().attributes(SwordItem.createAttributes(Tiers.IRON, 3, -2.4F))));
    public static final DeferredItem<InfusedPickaxeItem> INFUSED_PICKAXE = ITEMS.register("infused_pickaxe",
            () -> new InfusedPickaxeItem(OccultismTiers.SPIRIT_ATTUNED, defaultProperties()
                    .component(OccultismDataComponents.SPIRIT_NAME, "(Not yet known)")
                    .attributes(PickaxeItem.createAttributes(Tiers.DIAMOND, 1.0F, -2.8F))));
    public static final DeferredItem<OtherworldPickaxeItem> IESNIUM_PICKAXE = ITEMS.register("iesnium_pickaxe",
            () -> new OtherworldPickaxeItem(OccultismTiers.IESNIUM, defaultProperties().attributes(PickaxeItem.createAttributes(Tiers.DIAMOND, 1.0F, -2.8F))));
    public static final DeferredItem<OtherworldGogglesItem> OTHERWORLD_GOGGLES = ITEMS.register("otherworld_goggles",
            () -> new OtherworldGogglesItem(ArmorMaterials.IRON,
                    ArmorItem.Type.HELMET, defaultProperties().stacksTo(1)));
    public static final DeferredItem<Item> SATCHEL = ITEMS.register("satchel",
            () -> new SatchelItem(defaultProperties().stacksTo(1).rarity(Rarity.RARE)
                    .component(DataComponents.CONTAINER, ItemContainerContents.EMPTY)
                    .component(OccultismDataComponents.SPIRIT_NAME, "(Not yet known)")
            ));
    public static final DeferredItem<SingleBlockRitualSatchelItem> RITUAL_SATCHEL_T1 = ITEMS.register("ritual_satchel_t1",
            () -> new SingleBlockRitualSatchelItem(defaultProperties()
                    .stacksTo(1).rarity(Rarity.RARE)
                    .component(DataComponents.CONTAINER, ItemContainerContents.EMPTY)
                    .component(OccultismDataComponents.SPIRIT_NAME, "(Not yet known)")
            ));
    public static final DeferredItem<MultiBlockRitualSatchelItem> RITUAL_SATCHEL_T2 = ITEMS.register("ritual_satchel_t2",
            () -> new MultiBlockRitualSatchelItem(defaultProperties()
                    .stacksTo(1).rarity(Rarity.RARE)
                    .component(DataComponents.CONTAINER, ItemContainerContents.EMPTY)
                    .component(OccultismDataComponents.SPIRIT_NAME, "(Not yet known)")
            ));
    public static final DeferredItem<StorageRemoteItem> STORAGE_REMOTE = ITEMS.register("storage_remote",
            () -> new StorageRemoteItem(defaultProperties().stacksTo(1)));
    public static final DeferredItem<SoulGemItem> SOUL_GEM_ITEM = ITEMS.register("soul_gem",
            () -> new SoulGemItem(defaultProperties().stacksTo(1)));
    public static final DeferredItem<SoulGemItem> TRINITY_GEM_ITEM = ITEMS.register("trinity_gem",
            () -> new SoulGemItem(defaultProperties().stacksTo(1)));
    public static final DeferredItem<Item> FAMILIAR_RING = ITEMS.register("familiar_ring",
            () -> new FamiliarRingItem(defaultProperties().stacksTo(1).component(OccultismDataComponents.SPIRIT_NAME, "(Not yet known)")));

    //Books of Binding
    public static final DeferredItem<BookOfBindingItem> BOOK_OF_BINDING_EMPTY = ITEMS.register(
            "book_of_binding_empty", () -> new BookOfBindingItem(defaultProperties().stacksTo(16)));
    public static final DeferredItem<BookOfBindingItem> BOOK_OF_BINDING_FOLIOT = ITEMS.register(
            "book_of_binding_foliot", () -> new BookOfBindingItem(defaultProperties().stacksTo(16)));
    public static final DeferredItem<BookOfBindingBoundItem> BOOK_OF_BINDING_BOUND_FOLIOT = ITEMS.register(
            "book_of_binding_bound_foliot", () -> new BookOfBindingBoundItem(defaultProperties().stacksTo(1).component(OccultismDataComponents.SPIRIT_NAME, TextUtil.SPIRIT_NAME_NOT_YET_KNOWN)));
    public static final DeferredItem<BookOfBindingItem> BOOK_OF_BINDING_DJINNI = ITEMS.register(
            "book_of_binding_djinni", () -> new BookOfBindingItem(defaultProperties().stacksTo(16)));
    public static final DeferredItem<BookOfBindingBoundItem> BOOK_OF_BINDING_BOUND_DJINNI = ITEMS.register(
            "book_of_binding_bound_djinni", () -> new BookOfBindingBoundItem(defaultProperties().stacksTo(1).component(OccultismDataComponents.SPIRIT_NAME, TextUtil.SPIRIT_NAME_NOT_YET_KNOWN)));
    public static final DeferredItem<BookOfBindingItem> BOOK_OF_BINDING_AFRIT = ITEMS.register(
            "book_of_binding_afrit", () -> new BookOfBindingItem(defaultProperties().stacksTo(16)));
    public static final DeferredItem<BookOfBindingBoundItem> BOOK_OF_BINDING_BOUND_AFRIT = ITEMS.register(
            "book_of_binding_bound_afrit", () -> new BookOfBindingBoundItem(defaultProperties().stacksTo(1).component(OccultismDataComponents.SPIRIT_NAME, TextUtil.SPIRIT_NAME_NOT_YET_KNOWN)));
    public static final DeferredItem<BookOfBindingItem> BOOK_OF_BINDING_MARID = ITEMS.register(
            "book_of_binding_marid", () -> new BookOfBindingItem(defaultProperties().stacksTo(16)));
    public static final DeferredItem<BookOfBindingBoundItem> BOOK_OF_BINDING_BOUND_MARID = ITEMS.register(
            "book_of_binding_bound_marid", () -> new BookOfBindingBoundItem(defaultProperties().stacksTo(1).component(OccultismDataComponents.SPIRIT_NAME, TextUtil.SPIRIT_NAME_NOT_YET_KNOWN)));
    //Books of Calling
    //Foliot
    public static final DeferredItem<BookOfCallingLumberjackItem> BOOK_OF_CALLING_FOLIOT_LUMBERJACK =
            ITEMS.register("book_of_calling_foliot_lumberjack",
                    () -> new BookOfCallingLumberjackItem(defaultProperties().stacksTo(1).component(OccultismDataComponents.SPIRIT_NAME, TextUtil.SPIRIT_NAME_NOT_YET_KNOWN),
                            TranslationKeys.BOOK_OF_CALLING_GENERIC + "_foliot"));
    public static final DeferredItem<BookOfCallingTransportItems> BOOK_OF_CALLING_FOLIOT_TRANSPORT_ITEMS =
            ITEMS.register("book_of_calling_foliot_transport_items",
                    () -> new BookOfCallingTransportItems(defaultProperties().stacksTo(1).component(OccultismDataComponents.SPIRIT_NAME, TextUtil.SPIRIT_NAME_NOT_YET_KNOWN),
                            TranslationKeys.BOOK_OF_CALLING_GENERIC + "_foliot"));
    public static final DeferredItem<BookOfCallingCleanerItem> BOOK_OF_CALLING_FOLIOT_CLEANER =
            ITEMS.register("book_of_calling_foliot_cleaner",
                    () -> new BookOfCallingCleanerItem(defaultProperties().stacksTo(1).component(OccultismDataComponents.SPIRIT_NAME, TextUtil.SPIRIT_NAME_NOT_YET_KNOWN),
                            TranslationKeys.BOOK_OF_CALLING_GENERIC + "_foliot"));
    //Djinn
    public static final DeferredItem<BookOfCallingManageMachineItem> BOOK_OF_CALLING_DJINNI_MANAGE_MACHINE =
            ITEMS.register("book_of_calling_djinni_manage_machine",
                    () -> new BookOfCallingManageMachineItem(defaultProperties().stacksTo(1).component(OccultismDataComponents.SPIRIT_NAME, TextUtil.SPIRIT_NAME_NOT_YET_KNOWN),
                            TranslationKeys.BOOK_OF_CALLING_GENERIC + "_djinni"));

    //Brush. Chalks and Impure Chalks
    public static final DeferredItem<BrushItem> BRUSH = ITEMS.register("brush",
            () -> new BrushItem(defaultProperties().stacksTo(1)));
    public static final DeferredItem<Item> CHALK_WHITE = ITEMS.register("chalk_white",
            () -> new ChalkItem(defaultProperties().setNoRepair().durability(256),
                    OccultismBlocks.CHALK_GLYPH_WHITE));
    public static final DeferredItem<Item> CHALK_LIGHT_GRAY = ITEMS.register("chalk_light_gray",
            () -> new ChalkItem(defaultProperties().setNoRepair().durability(256),
                    OccultismBlocks.CHALK_GLYPH_LIGHT_GRAY));
    public static final DeferredItem<Item> CHALK_GRAY = ITEMS.register("chalk_gray",
            () -> new ChalkItem(defaultProperties().setNoRepair().durability(256),
                    OccultismBlocks.CHALK_GLYPH_GRAY));
    public static final DeferredItem<Item> CHALK_BLACK = ITEMS.register("chalk_black",
            () -> new ChalkItem(defaultProperties().setNoRepair().durability(256),
                    OccultismBlocks.CHALK_GLYPH_BLACK));
    public static final DeferredItem<Item> CHALK_BROWN = ITEMS.register("chalk_brown",
            () -> new ChalkItem(defaultProperties().setNoRepair().durability(256),
                    OccultismBlocks.CHALK_GLYPH_BROWN));
    public static final DeferredItem<Item> CHALK_RED = ITEMS.register("chalk_red",
            () -> new ChalkItem(defaultProperties().setNoRepair().durability(256),
                    OccultismBlocks.CHALK_GLYPH_RED));
    public static final DeferredItem<Item> CHALK_ORANGE = ITEMS.register("chalk_orange",
            () -> new ChalkItem(defaultProperties().setNoRepair().durability(256),
                    OccultismBlocks.CHALK_GLYPH_ORANGE));
    public static final DeferredItem<Item> CHALK_YELLOW = ITEMS.register("chalk_gold",
            () -> new ChalkItem(defaultProperties().setNoRepair().durability(256),
                    OccultismBlocks.CHALK_GLYPH_YELLOW));
    public static final DeferredItem<Item> CHALK_LIME = ITEMS.register("chalk_lime",
            () -> new ChalkItem(defaultProperties().setNoRepair().durability(256),
                    OccultismBlocks.CHALK_GLYPH_LIME));
    public static final DeferredItem<Item> CHALK_GREEN = ITEMS.register("chalk_green",
            () -> new ChalkItem(defaultProperties().setNoRepair().durability(256),
                    OccultismBlocks.CHALK_GLYPH_GREEN));
    public static final DeferredItem<Item> CHALK_CYAN = ITEMS.register("chalk_cyan",
            () -> new ChalkItem(defaultProperties().setNoRepair().durability(256),
                    OccultismBlocks.CHALK_GLYPH_CYAN));
    public static final DeferredItem<Item> CHALK_LIGHT_BLUE = ITEMS.register("chalk_light_blue",
            () -> new ChalkItem(defaultProperties().setNoRepair().durability(256),
                    OccultismBlocks.CHALK_GLYPH_LIGHT_BLUE));
    public static final DeferredItem<Item> CHALK_BLUE = ITEMS.register("chalk_blue",
            () -> new ChalkItem(defaultProperties().setNoRepair().durability(256),
                    OccultismBlocks.CHALK_GLYPH_BLUE));
    public static final DeferredItem<Item> CHALK_PURPLE = ITEMS.register("chalk_purple",
            () -> new ChalkItem(defaultProperties().setNoRepair().durability(256),
                    OccultismBlocks.CHALK_GLYPH_PURPLE));
    public static final DeferredItem<Item> CHALK_MAGENTA = ITEMS.register("chalk_magenta",
            () -> new ChalkItem(defaultProperties().setNoRepair().durability(256),
                    OccultismBlocks.CHALK_GLYPH_MAGENTA));
    public static final DeferredItem<Item> CHALK_PINK = ITEMS.register("chalk_pink",
            () -> new ChalkItem(defaultProperties().setNoRepair().durability(256),
                    OccultismBlocks.CHALK_GLYPH_PINK));

    public static final DeferredItem<Item> CHALK_RAINBOW = ITEMS.register("chalk_rainbow",
            () -> new RainbowChalkItem(defaultProperties().setNoRepair().durability(4096),
                    OccultismBlocks.CHALK_GLYPH_RAINBOW));
    public static final DeferredItem<Item> CHALK_VOID = ITEMS.register("chalk_void",
            () -> new RainbowChalkItem(defaultProperties().setNoRepair().durability(4096),
                    OccultismBlocks.CHALK_GLYPH_VOID));

    public static final DeferredItem<Item> CHALK_WHITE_IMPURE = ITEMS.register("chalk_white_impure",
            () -> new Item(defaultProperties()));
    public static final DeferredItem<Item> CHALK_LIGHT_GRAY_IMPURE = ITEMS.register("chalk_light_gray_impure",
            () -> new Item(defaultProperties()));
    public static final DeferredItem<Item> CHALK_GRAY_IMPURE = ITEMS.register("chalk_gray_impure",
            () -> new Item(defaultProperties()));
    public static final DeferredItem<Item> CHALK_BLACK_IMPURE = ITEMS.register("chalk_black_impure",
            () -> new Item(defaultProperties()));
    public static final DeferredItem<Item> CHALK_BROWN_IMPURE = ITEMS.register("chalk_brown_impure",
            () -> new Item(defaultProperties()));
    public static final DeferredItem<Item> CHALK_RED_IMPURE = ITEMS.register("chalk_red_impure",
            () -> new Item(defaultProperties()));
    public static final DeferredItem<Item> CHALK_ORANGE_IMPURE = ITEMS.register("chalk_orange_impure",
            () -> new Item(defaultProperties()));
    public static final DeferredItem<Item> CHALK_YELLOW_IMPURE = ITEMS.register("chalk_yellow_impure",
            () -> new Item(defaultProperties()));
    public static final DeferredItem<Item> CHALK_LIME_IMPURE = ITEMS.register("chalk_lime_impure",
            () -> new Item(defaultProperties()));
    public static final DeferredItem<Item> CHALK_GREEN_IMPURE = ITEMS.register("chalk_green_impure",
            () -> new Item(defaultProperties()));
    public static final DeferredItem<Item> CHALK_CYAN_IMPURE = ITEMS.register("chalk_cyan_impure",
            () -> new Item(defaultProperties()));
    public static final DeferredItem<Item> CHALK_LIGHT_BLUE_IMPURE = ITEMS.register("chalk_light_blue_impure",
            () -> new Item(defaultProperties()));
    public static final DeferredItem<Item> CHALK_BLUE_IMPURE = ITEMS.register("chalk_blue_impure",
            () -> new Item(defaultProperties()));
    public static final DeferredItem<Item> CHALK_PURPLE_IMPURE = ITEMS.register("chalk_purple_impure",
            () -> new Item(defaultProperties()));
    public static final DeferredItem<Item> CHALK_MAGENTA_IMPURE = ITEMS.register("chalk_magenta_impure",
            () -> new Item(defaultProperties()));
    public static final DeferredItem<Item> CHALK_PINK_IMPURE = ITEMS.register("chalk_pink_impure",
            () -> new Item(defaultProperties()));


    //Miner Spirits
    public static final DeferredItem<Item> MAGIC_LAMP_EMPTY = ITEMS.register("magic_lamp_empty",
            () -> new Item(defaultProperties()));

    public static final DeferredItem<MinerSpiritItem> MINER_FOLIOT_UNSPECIALIZED = ITEMS.register("miner_foliot_unspecialized",
            () -> new MinerSpiritItem(defaultProperties()
                    .component(OccultismDataComponents.SPIRIT_NAME, TextUtil.SPIRIT_NAME_NOT_YET_KNOWN)
                    .durability(1000),
                    Occultism.STARTUP_CONFIG.dimensionalMineshaft.minerFoliotUnspecialized.maxMiningTime,
                    Occultism.STARTUP_CONFIG.dimensionalMineshaft.minerFoliotUnspecialized.rollsPerOperation,
                    Occultism.STARTUP_CONFIG.dimensionalMineshaft.minerFoliotUnspecialized.durability));
    public static final DeferredItem<MinerSpiritItem> MINER_DJINNI_ORES = ITEMS.register("miner_djinni_ores",
            () -> new MinerSpiritItem(defaultProperties().durability(400)
                    .component(OccultismDataComponents.SPIRIT_NAME, TextUtil.SPIRIT_NAME_NOT_YET_KNOWN),
                    Occultism.STARTUP_CONFIG.dimensionalMineshaft.minerDjinniOres.maxMiningTime,
                    Occultism.STARTUP_CONFIG.dimensionalMineshaft.minerDjinniOres.rollsPerOperation,
                    Occultism.STARTUP_CONFIG.dimensionalMineshaft.minerDjinniOres.durability));
    public static final DeferredItem<MinerSpiritItem> MINER_AFRIT_DEEPS = ITEMS.register("miner_afrit_deeps",
            () -> new MinerSpiritItem(defaultProperties().durability(800)
                    .component(OccultismDataComponents.SPIRIT_NAME, TextUtil.SPIRIT_NAME_NOT_YET_KNOWN),
                    Occultism.STARTUP_CONFIG.dimensionalMineshaft.minerAfritDeeps.maxMiningTime,
                    Occultism.STARTUP_CONFIG.dimensionalMineshaft.minerAfritDeeps.rollsPerOperation,
                    Occultism.STARTUP_CONFIG.dimensionalMineshaft.minerAfritDeeps.durability));
    public static final DeferredItem<MinerSpiritItem> MINER_MARID_MASTER = ITEMS.register("miner_marid_master",
            () -> new MinerSpiritItem(defaultProperties().durability(1600)
                    .component(OccultismDataComponents.SPIRIT_NAME, TextUtil.SPIRIT_NAME_NOT_YET_KNOWN),
                    Occultism.STARTUP_CONFIG.dimensionalMineshaft.minerMaridMaster.maxMiningTime,
                    Occultism.STARTUP_CONFIG.dimensionalMineshaft.minerMaridMaster.rollsPerOperation,
                    Occultism.STARTUP_CONFIG.dimensionalMineshaft.minerMaridMaster.durability));

    public static final DeferredItem<MinerSpiritItem> MINER_ANCIENT_ELDRITCH = ITEMS.register("miner_ancient_eldritch",
            () -> new MinerSpiritItem(defaultProperties().durability(3200)
                    .component(OccultismDataComponents.SPIRIT_NAME, TextUtil.SPIRIT_NAME_NOT_YET_KNOWN),
                    Occultism.STARTUP_CONFIG.dimensionalMineshaft.minerAncientEldritch.maxMiningTime,
                    Occultism.STARTUP_CONFIG.dimensionalMineshaft.minerAncientEldritch.rollsPerOperation,
                    Occultism.STARTUP_CONFIG.dimensionalMineshaft.minerAncientEldritch.durability));

    //Crops and food
    public static final DeferredItem<Item> DATURA_SEEDS =
            ITEMS.register("datura_seeds", () -> new ItemNameBlockItem(OccultismBlocks.DATURA.get(), defaultProperties()));
    public static final DeferredItem<Item> DATURA = ITEMS.register("datura",
            () -> new SpiritHealingItem(defaultProperties().food(OccultismFoods.DATURA.get())));
    public static final DeferredItem<Item> DEMONS_DREAM_ESSENCE = ITEMS.register("demons_dream_essence",
            () -> new SpiritHealingItem(defaultProperties().food(OccultismFoods.DEMONS_DREAM_ESSENCE.get())));

    public static final DeferredItem<Item> OTHERWORLD_ESSENCE = ITEMS.register("otherworld_essence",
            () -> new SpiritHealingItem(defaultProperties().food(OccultismFoods.OTHERWORLD_ESSENCE.get())));
    public static final DeferredItem<Item> BEAVER_NUGGET = ITEMS.register("beaver_nugget",
            () -> new Item(defaultProperties().food(OccultismFoods.BEAVER_NUGGET.get())));
    public static final DeferredItem<Item> CURSED_HONEY = ITEMS.register("cursed_honey",
            () -> new Item(defaultProperties().food(OccultismFoods.CURSED_HONEY.get())));
    public static final DeferredItem<Item> DEMONIC_MEAT = ITEMS.register("demonic_meat",
            () -> new Item(defaultProperties().food(OccultismFoods.DEMONIC_MEAT.get())));

    //Resources and materials
    public static final DeferredItem<Item> TALLOW = ITEMS.register("tallow",
            () -> new Item(defaultProperties()));
    public static final DeferredItem<Item> AFRIT_ESSENCE = ITEMS.register("afrit_essence",
            () -> new Item(defaultProperties()));
    public static final DeferredItem<Item> MARID_ESSENCE = ITEMS.register("marid_essence",
            () -> new Item(defaultProperties()));
    public static final DeferredItem<Item> CRUELTY_ESSENCE = ITEMS.register("cruelty_essence",
            () -> new Item(defaultProperties()));
    public static final DeferredItem<Item> SPIRIT_ATTUNED_GEM = ITEMS.register("spirit_attuned_gem",
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
    //Dusts
    public static final DeferredItem<Item> SILVER_DUST = ITEMS.register("silver_dust",
            () -> new Item(defaultProperties()));
    public static final DeferredItem<Item> IESNIUM_DUST = ITEMS.register("iesnium_dust",
            () -> new Item(defaultProperties()));
    public static final DeferredItem<Item> COPPER_DUST = ITEMS.register("copper_dust",
            () -> new Item(defaultProperties()));
    public static final DeferredItem<Item> IRON_DUST = ITEMS.register("iron_dust",
            () -> new Item(defaultProperties()));
    public static final DeferredItem<Item> GOLD_DUST = ITEMS.register("gold_dust",
            () -> new Item(defaultProperties()));
    public static final DeferredItem<Item> NETHERITE_SCRAP_DUST = ITEMS.register("netherite_scrap_dust",
            () -> new Item(defaultProperties()));
    public static final DeferredItem<Item> NETHERITE_DUST = ITEMS.register("netherite_dust",
            () -> new Item(defaultProperties()));
    public static final DeferredItem<Item> OBSIDIAN_DUST = ITEMS.register("obsidian_dust",
            () -> new Item(defaultProperties()));
    public static final DeferredItem<Item> LAPIS_DUST = ITEMS.register("lapis_dust",
            () -> new Item(defaultProperties()));
    public static final DeferredItem<Item> AMETHYST_DUST = ITEMS.register("amethyst_dust",
            () -> new Item(defaultProperties()));
    public static final DeferredItem<Item> EMERALD_DUST = ITEMS.register("emerald_dust",
            () -> new Item(defaultProperties()));
    public static final DeferredItem<Item> ECHO_DUST = ITEMS.register("echo_dust",
            () -> new Item(defaultProperties()));
    public static final DeferredItem<Item> RESEARCH_FRAGMENT_DUST = ITEMS.register("research_fragment_dust",
            () -> new Item(defaultProperties()));
    public static final DeferredItem<Item> WITHERITE_DUST = ITEMS.register("witherite_dust",
            () -> new Item(defaultProperties()));
    public static final DeferredItem<Item> DRAGONYST_DUST = ITEMS.register("dragonyst_dust",
            () -> new Item(defaultProperties()));
    public static final DeferredItem<Item> CRUSHED_END_STONE = ITEMS.register("crushed_end_stone",
            () -> new Item(defaultProperties()));
    public static final DeferredItem<Item> CRUSHED_CALCITE = ITEMS.register("crushed_calcite",
            () -> new Item(defaultProperties()));
    public static final DeferredItem<Item> CRUSHED_BLACKSTONE = ITEMS.register("crushed_blackstone",
            () -> new Item(defaultProperties()));
    public static final DeferredItem<Item> CRUSHED_ICE = ITEMS.register("crushed_ice",
            () -> new Item(defaultProperties()));
    public static final DeferredItem<Item> CRUSHED_PACKED_ICE = ITEMS.register("crushed_packed_ice",
            () -> new Item(defaultProperties()));
    public static final DeferredItem<Item> CRUSHED_BLUE_ICE = ITEMS.register("crushed_blue_ice",
            () -> new Item(defaultProperties()));
    public static final DeferredItem<Item> BURNT_OTHERSTONE = ITEMS.register("burnt_otherstone",
            () -> new Item(defaultProperties()));
    public static final DeferredItem<Item> OTHERWORLD_ASHES = ITEMS.register("otherworld_ashes",
            () -> new Item(defaultProperties()));
    public static final DeferredItem<Item> GRAY_PASTE = ITEMS.register("gray_paste",
            () -> new Item(defaultProperties()));
    public static final DeferredItem<Item> NATURE_PASTE = ITEMS.register("nature_paste",
            () -> new Item(defaultProperties()));

    //Components
    public static final DeferredItem<Item> PURIFIED_INK = ITEMS.register("purified_ink",
            () -> new Item(defaultProperties()));
    public static final DeferredItem<Item> AWAKENED_FEATHER = ITEMS.register("awakened_feather",
            () -> new Item(defaultProperties()));
    public static final DeferredItem<Item> TABOO_BOOK = ITEMS.register("taboo_book",
            () -> new Item(defaultProperties()));
    public static final DeferredItem<Item> SPIRIT_ATTUNED_PICKAXE_HEAD = ITEMS.register("spirit_attuned_pickaxe_head",
            () -> new Item(defaultProperties()));
    public static final DeferredItem<Item> LENSES = ITEMS.register("lenses",
            () -> new Item(defaultProperties()));
    public static final DeferredItem<Item> INFUSED_LENSES = ITEMS.register("infused_lenses",
            () -> new Item(defaultProperties()));
    public static final DeferredItem<Item> LENS_FRAME = ITEMS.register("lens_frame",
            () -> new Item(defaultProperties()));
    public static final DeferredItem<Item> OTHERSTONE_FRAME = ITEMS.register("otherstone_frame",
            () -> new Item(defaultProperties()));
    public static final DeferredItem<Item> WORMHOLE_FRAME = ITEMS.register("wormhole_frame",
            () -> new Item(defaultProperties()));
    public static final DeferredItem<Item> OTHERSTONE_TABLET = ITEMS.register("otherstone_tablet",
            () -> new Item(defaultProperties()));
    public static final DeferredItem<Item> STORAGE_REMOTE_INERT = ITEMS.register("storage_remote_inert",
            () -> new Item(defaultProperties()));
    public static final DeferredItem<DimensionalMatrixItem> DIMENSIONAL_MATRIX = ITEMS.register("dimensional_matrix",
            () -> new DimensionalMatrixItem(defaultProperties().component(OccultismDataComponents.SPIRIT_NAME, "(Not yet known)")));
    public static final DeferredItem<Item> MINING_DIMENSION_CORE_PIECE = ITEMS.register("mining_dim_core",
            () -> new Item(defaultProperties()));

    //Others
    public static final DeferredItem<SoulShardItem> SOUL_SHARD_ITEM = ITEMS.register("soul_shard",
            () -> new SoulShardItem(defaultProperties().stacksTo(1)));

    //Machines
    public static final DeferredItem<BlockItem> SPIRIT_FIRE =
            ITEMS.register("spirit_fire", () -> new BlockItem(OccultismBlocks.SPIRIT_FIRE.get(), defaultProperties()));
    public static final DeferredItem<StableWormholeBlockItem> STABLE_WORMHOLE = ITEMS.register("stable_wormhole",
            () -> new StableWormholeBlockItem(OccultismBlocks.STABLE_WORMHOLE.get(), defaultProperties())); //not work if auto-gen

    //Deco Block Items
    public static final DeferredItem<Item> SPIRIT_TORCH = ITEMS.register("spirit_torch",
            () -> new StandingAndWallBlockItem(OccultismBlocks.SPIRIT_TORCH.get(), OccultismBlocks.SPIRIT_WALL_TORCH.get(),
                    defaultProperties(), Direction.DOWN));
    public static final DeferredItem<Item> OTHERPLANKS_SIGN = ITEMS.register("otherplanks_sign",
            () -> new SignItem(new Item.Properties().stacksTo(16), OccultismBlocks.OTHERPLANKS_SIGN.get(), OccultismBlocks.OTHERPLANKS_WALL_SIGN.get()));
    public static final DeferredItem<Item> OTHERPLANKS_HANGING_SIGN = ITEMS.register("otherplanks_hanging_sign",
            () -> new HangingSignItem(OccultismBlocks.OTHERPLANKS_HANGING_SIGN.get(), OccultismBlocks.OTHERPLANKS_WALL_HANGING_SIGN.get(), new Item.Properties().stacksTo(16)));

    //Spawn Eggs
    public static final DeferredItem<Item> SPAWN_EGG_FOLIOT = ITEMS.register("spawn_egg/foliot",
            () -> new DeferredSpawnEggItem(OccultismEntities.FOLIOT_TYPE, 0x8d5454, 0x1f1f1f, defaultProperties()));
    public static final DeferredItem<Item> SPAWN_EGG_DJINNI = ITEMS.register("spawn_egg/djinni",
            () -> new DeferredSpawnEggItem(OccultismEntities.DJINNI_TYPE, 0x073f7c, 0xc9d631, defaultProperties()));
    public static final DeferredItem<Item> SPAWN_EGG_AFRIT = ITEMS.register("spawn_egg/afrit",
            () -> new DeferredSpawnEggItem(OccultismEntities.AFRIT_TYPE, 0x5d241a, 0x946510, defaultProperties()));
    public static final DeferredItem<Item> SPAWN_EGG_AFRIT_UNBOUND = ITEMS.register("spawn_egg/afrit_unbound",
            () -> new DeferredSpawnEggItem(OccultismEntities.AFRIT_WILD_TYPE, 0x4d140a, 0x744500, defaultProperties()));
    public static final DeferredItem<Item> SPAWN_EGG_MARID = ITEMS.register("spawn_egg/marid",
            () -> new DeferredSpawnEggItem(OccultismEntities.MARID_TYPE, 0x396265, 0x57c786, defaultProperties()));
    public static final DeferredItem<Item> SPAWN_EGG_MARID_UNBOUND = ITEMS.register("spawn_egg/marid_unbound",
            () -> new DeferredSpawnEggItem(OccultismEntities.MARID_UNBOUND_TYPE, 0x394245, 0x57a766, defaultProperties()));
    public static final DeferredItem<Item> SPAWN_EGG_POSSESSED_ENDERMITE = ITEMS.register("spawn_egg/possessed_endermite",
            () -> new DeferredSpawnEggItem(OccultismEntities.POSSESSED_ENDERMITE_TYPE, 0x161616, 0x6E6E6E, defaultProperties()));
    public static final DeferredItem<Item> SPAWN_EGG_POSSESSED_SKELETON = ITEMS.register("spawn_egg/possessed_skeleton",
            () -> new DeferredSpawnEggItem(OccultismEntities.POSSESSED_SKELETON_TYPE, 0xC1C1C1, 0x494949, defaultProperties()));
    public static final DeferredItem<Item> SPAWN_EGG_POSSESSED_ENDERMAN = ITEMS.register("spawn_egg/possessed_enderman",
            () -> new DeferredSpawnEggItem(OccultismEntities.POSSESSED_ENDERMAN_TYPE, 0x161616, 0x0, defaultProperties()));
    public static final DeferredItem<Item> SPAWN_EGG_POSSESSED_GHAST = ITEMS.register("spawn_egg/possessed_ghast",
            () -> new DeferredSpawnEggItem(OccultismEntities.POSSESSED_GHAST_TYPE, 0xe2e2e2, 0xC1C1C1, defaultProperties()));
    public static final DeferredItem<Item> SPAWN_EGG_POSSESSED_PHANTOM = ITEMS.register("spawn_egg/possessed_phantom",
            () -> new DeferredSpawnEggItem(OccultismEntities.POSSESSED_PHANTOM_TYPE, 0x3f4c81, 0x6ccc00, defaultProperties()));
    public static final DeferredItem<Item> SPAWN_EGG_POSSESSED_WEAK_SHULKER = ITEMS.register("spawn_egg/possessed_weak_shulker",
            () -> new DeferredSpawnEggItem(OccultismEntities.POSSESSED_WEAK_SHULKER_TYPE, 0x8c628c, 0x342638, defaultProperties()));
    public static final DeferredItem<Item> SPAWN_EGG_POSSESSED_SHULKER = ITEMS.register("spawn_egg/possessed_shulker",
            () -> new DeferredSpawnEggItem(OccultismEntities.POSSESSED_SHULKER_TYPE, 0x8c628c, 0x342638, defaultProperties()));
    public static final DeferredItem<Item> SPAWN_EGG_POSSESSED_ELDER_GUARDIAN = ITEMS.register("spawn_egg/possessed_elder_guardian",
            () -> new DeferredSpawnEggItem(OccultismEntities.POSSESSED_ELDER_GUARDIAN_TYPE, 0xb5b3a3, 0x4b4d60, defaultProperties()));
    public static final DeferredItem<Item> SPAWN_EGG_POSSESSED_WITCH = ITEMS.register("spawn_egg/possessed_witch",
            () -> new DeferredSpawnEggItem(OccultismEntities.POSSESSED_WITCH_TYPE, 0x280000, 0x346828, defaultProperties()));
    public static final DeferredItem<Item> SPAWN_EGG_POSSESSED_ZOMBIE_PIGLIN = ITEMS.register("spawn_egg/possessed_zombie_piglin",
            () -> new DeferredSpawnEggItem(OccultismEntities.POSSESSED_ZOMBIE_PIGLIN_TYPE, 0xdb8a8a, 0x6a8c46, defaultProperties()));
    public static final DeferredItem<Item> SPAWN_EGG_POSSESSED_BEE = ITEMS.register("spawn_egg/possessed_bee",
            () -> new DeferredSpawnEggItem(OccultismEntities.POSSESSED_BEE_TYPE, 0xd6b03c, 0x060606, defaultProperties()));
    public static final DeferredItem<Item> SPAWN_EGG_GOAT_OF_MERCY = ITEMS.register("spawn_egg/possessed_goat",
            () -> new DeferredSpawnEggItem(OccultismEntities.GOAT_OF_MERCY_TYPE, 0xa0a0a0, 0x835432, defaultProperties()));
    public static final DeferredItem<Item> SPAWN_EGG_WILD_HUNT_SKELETON = ITEMS.register("spawn_egg/wild_hunt_skeleton",
            () -> new DeferredSpawnEggItem(OccultismEntities.WILD_HUNT_SKELETON_TYPE, 12698049, 4802889, defaultProperties()));
    public static final DeferredItem<Item> SPAWN_EGG_WILD_HUNT_WITHER_SKELETON = ITEMS.register("spawn_egg/wild_hunt_wither_skeleton",
            () -> new DeferredSpawnEggItem(OccultismEntities.WILD_HUNT_WITHER_SKELETON_TYPE, 1315860, 4672845, defaultProperties()));
    public static final DeferredItem<Item> SPAWN_EGG_POSSESSED_WARDEN = ITEMS.register("spawn_egg/possessed_warden",
            () -> new DeferredSpawnEggItem(OccultismEntities.POSSESSED_WARDEN_TYPE, 0x0f4649, 0x39d6e0, defaultProperties()));
    public static final DeferredItem<Item> SPAWN_EGG_POSSESSED_HOGLIN = ITEMS.register("spawn_egg/possessed_hoglin",
            () -> new DeferredSpawnEggItem(OccultismEntities.POSSESSED_HOGLIN_TYPE, 0x592a10, 0xf9f3a4, defaultProperties()));
    public static final DeferredItem<Item> SPAWN_EGG_WILD_HORDE_HUSK = ITEMS.register("spawn_egg/wild_horde_husk",
            () -> new DeferredSpawnEggItem(OccultismEntities.WILD_HORDE_HUSK_TYPE, 0x5f584c, 0x92815e, defaultProperties()));
    public static final DeferredItem<Item> SPAWN_EGG_WILD_HORDE_DROWNED = ITEMS.register("spawn_egg/wild_horde_drowned",
            () -> new DeferredSpawnEggItem(OccultismEntities.WILD_HORDE_DROWNED_TYPE, 0x7bcfb9, 0x577148, defaultProperties()));
    public static final DeferredItem<Item> SPAWN_EGG_WILD_HORDE_CREEPER = ITEMS.register("spawn_egg/wild_horde_creeper",
            () -> new DeferredSpawnEggItem(OccultismEntities.WILD_HORDE_CREEPER_TYPE, 0x577148, 0x111111, defaultProperties()));
    public static final DeferredItem<Item> SPAWN_EGG_WILD_HORDE_SILVERFISH = ITEMS.register("spawn_egg/wild_horde_silverfish",
            () -> new DeferredSpawnEggItem(OccultismEntities.WILD_HORDE_SILVERFISH_TYPE, 0x666666, 0x262626, defaultProperties()));
    public static final DeferredItem<Item> SPAWN_EGG_WILD_WEAK_BREEZE = ITEMS.register("spawn_egg/wild_weak_breeze",
            () -> new DeferredSpawnEggItem(OccultismEntities.POSSESSED_WEAK_BREEZE_TYPE, 0xa289cf, 0x5d428f, defaultProperties()));
    public static final DeferredItem<Item> SPAWN_EGG_WILD_BREEZE = ITEMS.register("spawn_egg/wild_breeze",
            () -> new DeferredSpawnEggItem(OccultismEntities.POSSESSED_BREEZE_TYPE, 0x9279bf, 0x4d327f, defaultProperties()));
    public static final DeferredItem<Item> SPAWN_EGG_WILD_STRONG_BREEZE = ITEMS.register("spawn_egg/wild_strong_breeze",
            () -> new DeferredSpawnEggItem(OccultismEntities.POSSESSED_STRONG_BREEZE_TYPE, 0x8269af, 0x3d226f, defaultProperties()));
    public static final DeferredItem<Item> SPAWN_EGG_WILD_EVOKER = ITEMS.register("spawn_egg/wild_evoker",
            () -> new DeferredSpawnEggItem(OccultismEntities.POSSESSED_EVOKER_TYPE, 0x8e9494, 0xcbc786, defaultProperties()));
    public static final DeferredItem<Item> SPAWN_EGG_OTHERWORLD_BIRD = ITEMS.register("spawn_egg/otherworld_bird",
            () -> new DeferredSpawnEggItem(OccultismEntities.OTHERWORLD_BIRD_TYPE, 0x221269, 0x6b56c4, defaultProperties()));
    public static final DeferredItem<Item> SPAWN_EGG_GREEDY_FAMILIAR = ITEMS.register("spawn_egg/familiar_greedy",
            () -> new DeferredSpawnEggItem(OccultismEntities.GREEDY_FAMILIAR_TYPE, 0x54990f, 0x725025, defaultProperties()));
    public static final DeferredItem<Item> SPAWN_EGG_BAT_FAMILIAR = ITEMS.register("spawn_egg/familiar_bat",
            () -> new DeferredSpawnEggItem(OccultismEntities.BAT_FAMILIAR_TYPE, 0x434343, 0xda95de, defaultProperties()));
    public static final DeferredItem<Item> SPAWN_EGG_DEER_FAMILIAR = ITEMS.register("spawn_egg/familiar_deer",
            () -> new DeferredSpawnEggItem(OccultismEntities.DEER_FAMILIAR_TYPE, 0xc9833e, 0xfffdf2, defaultProperties()));
    public static final DeferredItem<Item> SPAWN_EGG_CTHULHU_FAMILIAR = ITEMS.register("spawn_egg/familiar_cthulhu",
            () -> new DeferredSpawnEggItem(OccultismEntities.CTHULHU_FAMILIAR_TYPE, 0x00cdc2, 0x4ae7c0, defaultProperties()));
    public static final DeferredItem<Item> SPAWN_EGG_DEVIL_FAMILIAR = ITEMS.register("spawn_egg/familiar_devil",
            () -> new DeferredSpawnEggItem(OccultismEntities.DEVIL_FAMILIAR_TYPE, 0xf2f0d7, 0xa01d1d, defaultProperties()));
    public static final DeferredItem<Item> SPAWN_EGG_DRAGON_FAMILIAR = ITEMS.register("spawn_egg/familiar_dragon",
            () -> new DeferredSpawnEggItem(OccultismEntities.DRAGON_FAMILIAR_TYPE, 0x18780f, 0x76c47b, defaultProperties()));
    public static final DeferredItem<Item> SPAWN_EGG_BLACKSMITH_FAMILIAR = ITEMS.register("spawn_egg/familiar_blacksmith",
            () -> new DeferredSpawnEggItem(OccultismEntities.BLACKSMITH_FAMILIAR_TYPE, 0x06bc64, 0x2b2b2b, defaultProperties()));
    public static final DeferredItem<Item> SPAWN_EGG_GUARDIAN_FAMILIAR = ITEMS.register("spawn_egg/familiar_guardian",
            () -> new DeferredSpawnEggItem(OccultismEntities.GUARDIAN_FAMILIAR_TYPE, 0x787878, 0x515151, defaultProperties()));
    public static final DeferredItem<Item> SPAWN_EGG_HEADLESS_FAMILIAR = ITEMS.register("spawn_egg/familiar_headless",
            () -> new DeferredSpawnEggItem(OccultismEntities.HEADLESS_FAMILIAR_TYPE, 0x0c0606, 0xde7900, defaultProperties()));
    public static final DeferredItem<Item> SPAWN_EGG_CHIMERA_FAMILIAR = ITEMS.register("spawn_egg/familiar_chimera",
            () -> new DeferredSpawnEggItem(OccultismEntities.CHIMERA_FAMILIAR_TYPE, 0xcf8441, 0x3e7922, defaultProperties()));
    public static final DeferredItem<Item> SPAWN_EGG_GOAT_FAMILIAR = ITEMS.register("spawn_egg/familiar_goat",
            () -> new DeferredSpawnEggItem(OccultismEntities.GOAT_FAMILIAR_TYPE, 0xe2e2e2, 0x0f0f0e, defaultProperties()));
    public static final DeferredItem<Item> SPAWN_EGG_SHUB_NIGGURATH_FAMILIAR = ITEMS.register("spawn_egg/familiar_shub_niggurath",
            () -> new DeferredSpawnEggItem(OccultismEntities.SHUB_NIGGURATH_FAMILIAR_TYPE, 0x362836, 0x594a3a, defaultProperties()));
    public static final DeferredItem<Item> SPAWN_EGG_BEHOLDER_FAMILIAR = ITEMS.register("spawn_egg/familiar_beholder",
            () -> new DeferredSpawnEggItem(OccultismEntities.BEHOLDER_FAMILIAR_TYPE, 0x340a09, 0xfffbff, defaultProperties()));
    public static final DeferredItem<Item> SPAWN_EGG_FAIRY_FAMILIAR = ITEMS.register("spawn_egg/familiar_fairy",
            () -> new DeferredSpawnEggItem(OccultismEntities.FAIRY_FAMILIAR_TYPE, 0xbd674c, 0xcca896, defaultProperties()));
    public static final DeferredItem<Item> SPAWN_EGG_MUMMY_FAMILIAR = ITEMS.register("spawn_egg/familiar_mummy",
            () -> new DeferredSpawnEggItem(OccultismEntities.MUMMY_FAMILIAR_TYPE, 0xcbb76a, 0xe0d4a3, defaultProperties()));
    public static final DeferredItem<Item> SPAWN_EGG_BEAVER_FAMILIAR = ITEMS.register("spawn_egg/familiar_beaver",
            () -> new DeferredSpawnEggItem(OccultismEntities.BEAVER_FAMILIAR_TYPE, 0x824a2b, 0xdd9973, defaultProperties()));
    public static final DeferredItem<Item> SPAWN_EGG_PARROT_FAMILIAR = ITEMS.register("spawn_egg/familiar_parrot",
            () -> new DeferredSpawnEggItem(() -> EntityType.PARROT, 894731, 16711680, defaultProperties()));
    public static final DeferredItem<Item> SPAWN_EGG_DEMONIC_WIFE = ITEMS.register("spawn_egg/demonic_wife",
            () -> new DeferredSpawnEggItem(OccultismEntities.DEMONIC_WIFE, 0xf2f0d7, 0xa01d1d, defaultProperties()));
    public static final DeferredItem<Item> SPAWN_EGG_DEMONIC_HUSBAND = ITEMS.register("spawn_egg/demonic_husband",
            () -> new DeferredSpawnEggItem(OccultismEntities.DEMONIC_HUSBAND, 0xf2f0d7, 0xa01d1d, defaultProperties()));
    public static final DeferredItem<Item> SPAWN_EGG_IESNIUM_GOLEM = ITEMS.register("spawn_egg/iesnium_golem",
            () -> new DeferredSpawnEggItem(OccultismEntities.IESNIUM_GOLEM_TYPE, 0x94d4db, 0x345f7c, defaultProperties()));
    
    //Ritual Dummy Items
        //SUMMON
            //Crusher
    public static final DeferredItem<Item> RITUAL_DUMMY_SUMMON_FOLIOT_CRUSHER = ITEMS.register("ritual_dummy/summon_foliot_crusher", () -> new DummyTooltipItem(defaultProperties()));
    public static final DeferredItem<Item> RITUAL_DUMMY_SUMMON_DJINNI_CRUSHER = ITEMS.register("ritual_dummy/summon_djinni_crusher", () -> new DummyTooltipItem(defaultProperties()));
    public static final DeferredItem<Item> RITUAL_DUMMY_SUMMON_AFRIT_CRUSHER = ITEMS.register("ritual_dummy/summon_afrit_crusher", () -> new DummyTooltipItem(defaultProperties()));
    public static final DeferredItem<Item> RITUAL_DUMMY_SUMMON_MARID_CRUSHER = ITEMS.register("ritual_dummy/summon_marid_crusher", () -> new DummyTooltipItem(defaultProperties()));
            //Smelter
    public static final DeferredItem<Item> RITUAL_DUMMY_SUMMON_FOLIOT_SMELTER = ITEMS.register("ritual_dummy/summon_foliot_smelter", () -> new DummyTooltipItem(defaultProperties()));
    public static final DeferredItem<Item> RITUAL_DUMMY_SUMMON_DJINNI_SMELTER = ITEMS.register("ritual_dummy/summon_djinni_smelter", () -> new DummyTooltipItem(defaultProperties()));
    public static final DeferredItem<Item> RITUAL_DUMMY_SUMMON_AFRIT_SMELTER = ITEMS.register("ritual_dummy/summon_afrit_smelter", () -> new DummyTooltipItem(defaultProperties()));
    public static final DeferredItem<Item> RITUAL_DUMMY_SUMMON_MARID_SMELTER = ITEMS.register("ritual_dummy/summon_marid_smelter", () -> new DummyTooltipItem(defaultProperties()));
            //Partner
    public static final DeferredItem<Item> RITUAL_DUMMY_SUMMON_DEMONIC_WIFE = ITEMS.register("ritual_dummy/summon_demonic_wife", () -> new DummyTooltipItem(defaultProperties()));
    public static final DeferredItem<Item> RITUAL_DUMMY_SUMMON_DEMONIC_HUSBAND = ITEMS.register("ritual_dummy/summon_demonic_husband", () -> new DummyTooltipItem(defaultProperties()));
            //One tier worker
    public static final DeferredItem<Item> RITUAL_DUMMY_SUMMON_FOLIOT_LUMBERJACK = ITEMS.register("ritual_dummy/summon_foliot_lumberjack", () -> new DummyTooltipItem(defaultProperties()));
    public static final DeferredItem<Item> RITUAL_DUMMY_SUMMON_FOLIOT_OTHERSTONE_TRADER = ITEMS.register("ritual_dummy/summon_foliot_otherstone_trader", () -> new DummyTooltipItem(defaultProperties()));
    public static final DeferredItem<Item> RITUAL_DUMMY_SUMMON_FOLIOT_SAPLING_TRADER = ITEMS.register("ritual_dummy/summon_foliot_sapling_trader", () -> new DummyTooltipItem(defaultProperties()));
    public static final DeferredItem<Item> RITUAL_DUMMY_SUMMON_FOLIOT_TRANSPORT_ITEMS = ITEMS.register("ritual_dummy/summon_foliot_transport_items", () -> new DummyTooltipItem(defaultProperties()));
    public static final DeferredItem<Item> RITUAL_DUMMY_SUMMON_FOLIOT_CLEANER = ITEMS.register("ritual_dummy/summon_foliot_cleaner", () -> new DummyTooltipItem(defaultProperties()));
    public static final DeferredItem<Item> RITUAL_DUMMY_SUMMON_DJINNI_MANAGE_MACHINE = ITEMS.register("ritual_dummy/summon_djinni_manage_machine", () -> new DummyTooltipItem(defaultProperties()));
    public static final DeferredItem<Item> RITUAL_DUMMY_SUMMON_DJINNI_DAY_TIME = ITEMS.register("ritual_dummy/summon_djinni_day_time", () -> new DummyTooltipItem(defaultProperties()));
    public static final DeferredItem<Item> RITUAL_DUMMY_SUMMON_DJINNI_NIGHT_TIME = ITEMS.register("ritual_dummy/summon_djinni_night_time", () -> new DummyTooltipItem(defaultProperties()));
    public static final DeferredItem<Item> RITUAL_DUMMY_SUMMON_DJINNI_CLEAR_WEATHER = ITEMS.register("ritual_dummy/summon_djinni_clear_weather", () -> new DummyTooltipItem(defaultProperties()));
    public static final DeferredItem<Item> RITUAL_DUMMY_SUMMON_AFRIT_RAIN_WEATHER = ITEMS.register("ritual_dummy/summon_afrit_rain_weather", () -> new DummyTooltipItem(defaultProperties()));
    public static final DeferredItem<Item> RITUAL_DUMMY_SUMMON_AFRIT_THUNDER_WEATHER = ITEMS.register("ritual_dummy/summon_afrit_thunder_weather", () -> new DummyTooltipItem(defaultProperties()));
            //Unbound
    public static final DeferredItem<Item> RITUAL_DUMMY_SUMMON_UNBOUND_AFRIT = ITEMS.register("ritual_dummy/summon_unbound_afrit", () -> new DummyTooltipItem(defaultProperties()));
    public static final DeferredItem<Item> RITUAL_DUMMY_SUMMON_UNBOUND_MARID = ITEMS.register("ritual_dummy/summon_unbound_marid", () -> new DummyTooltipItem(defaultProperties()));
        //POSSESS
    static {
            //Familiar
        ITEMS.register("ritual_dummy/familiar_beaver", () -> new DummyTooltipItem(defaultProperties()));
        ITEMS.register("ritual_dummy/familiar_blacksmith", () -> new DummyTooltipItem(defaultProperties()));
        ITEMS.register("ritual_dummy/familiar_deer", () -> new DummyTooltipItem(defaultProperties()));
        ITEMS.register("ritual_dummy/familiar_greedy", () -> new DummyTooltipItem(defaultProperties()));
        ITEMS.register("ritual_dummy/familiar_parrot", () -> new DummyTooltipItem(defaultProperties()));
        ITEMS.register("ritual_dummy/possess_unbound_parrot", () -> new DummyTooltipItem(defaultProperties()));
        ITEMS.register("ritual_dummy/familiar_bat", () -> new DummyTooltipItem(defaultProperties()));
        ITEMS.register("ritual_dummy/familiar_beholder", () -> new DummyTooltipItem(defaultProperties()));
        ITEMS.register("ritual_dummy/familiar_chimera", () -> new DummyTooltipItem(defaultProperties()));
        ITEMS.register("ritual_dummy/familiar_cthulhu", () -> new DummyTooltipItem(defaultProperties()));
        ITEMS.register("ritual_dummy/familiar_devil", () -> new DummyTooltipItem(defaultProperties()));
        ITEMS.register("ritual_dummy/familiar_dragon", () -> new DummyTooltipItem(defaultProperties()));
        ITEMS.register("ritual_dummy/familiar_fairy", () -> new DummyTooltipItem(defaultProperties()));
        ITEMS.register("ritual_dummy/familiar_headless", () -> new DummyTooltipItem(defaultProperties()));
        ITEMS.register("ritual_dummy/familiar_mummy", () -> new DummyTooltipItem(defaultProperties()));
        ITEMS.register("ritual_dummy/familiar_otherworld_bird", () -> new DummyTooltipItem(defaultProperties()));
        ITEMS.register("ritual_dummy/possess_unbound_otherworld_bird", () -> new DummyTooltipItem(defaultProperties()));
        ITEMS.register("ritual_dummy/familiar_guardian", () -> new DummyTooltipItem(defaultProperties()));
            //Possessed
        ITEMS.register("ritual_dummy/possess_endermite", () -> new DummyTooltipItem(defaultProperties()));
        ITEMS.register("ritual_dummy/possess_skeleton", () -> new DummyTooltipItem(defaultProperties()));
        ITEMS.register("ritual_dummy/possess_phantom", () -> new DummyTooltipItem(defaultProperties()));
        ITEMS.register("ritual_dummy/possess_enderman", () -> new DummyTooltipItem(defaultProperties()));
        ITEMS.register("ritual_dummy/possess_ghast", () -> new DummyTooltipItem(defaultProperties()));
        ITEMS.register("ritual_dummy/possess_warden", () -> new DummyTooltipItem(defaultProperties()));
        ITEMS.register("ritual_dummy/possess_weak_shulker", () -> new DummyTooltipItem(defaultProperties()));
        ITEMS.register("ritual_dummy/possess_elder_guardian", () -> new DummyTooltipItem(defaultProperties()));
        ITEMS.register("ritual_dummy/possess_hoglin", () -> new DummyTooltipItem(defaultProperties()));
        ITEMS.register("ritual_dummy/possess_shulker", () -> new DummyTooltipItem(defaultProperties()));
        ITEMS.register("ritual_dummy/possess_witch", () -> new DummyTooltipItem(defaultProperties()));
        ITEMS.register("ritual_dummy/possess_zombie_piglin", () -> new DummyTooltipItem(defaultProperties()));
        ITEMS.register("ritual_dummy/possess_bee", () -> new DummyTooltipItem(defaultProperties()));
        ITEMS.register("ritual_dummy/possess_goat", () -> new DummyTooltipItem(defaultProperties()));
    }

    public static final DeferredItem<Item> RITUAL_DUMMY_POSSESS_RANDOM_ANIMAL_COMMON = ITEMS.register("ritual_dummy/possess_random_animal_common", () -> new DummyTooltipItem(defaultProperties()));
    public static final DeferredItem<Item> RITUAL_DUMMY_POSSESS_RANDOM_ANIMAL_WATER = ITEMS.register("ritual_dummy/possess_random_animal_water", () -> new DummyTooltipItem(defaultProperties()));
    public static final DeferredItem<Item> RITUAL_DUMMY_POSSESS_RANDOM_ANIMAL_SMALL = ITEMS.register("ritual_dummy/possess_random_animal_small", () -> new DummyTooltipItem(defaultProperties()));
    public static final DeferredItem<Item> RITUAL_DUMMY_POSSESS_RANDOM_ANIMAL_RIDEABLE = ITEMS.register("ritual_dummy/possess_random_animal_rideable", () -> new DummyTooltipItem(defaultProperties()));
    public static final DeferredItem<Item> RITUAL_DUMMY_POSSESS_RANDOM_ANIMAL_SPECIAL = ITEMS.register("ritual_dummy/possess_random_animal_special", () -> new DummyTooltipItem(defaultProperties()));
    public static final DeferredItem<Item> RITUAL_DUMMY_POSSESS_VILLAGER = ITEMS.register("ritual_dummy/possess_villager", () -> new DummyTooltipItem(defaultProperties()));
    public static final DeferredItem<Item> RITUAL_DUMMY_POSSESS_IESNIUM_GOLEM = ITEMS.register("ritual_dummy/possess_iesnium_golem", () -> new DummyTooltipItem(defaultProperties()));

        // CRAFT
    static {
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
        ITEMS.register("ritual_dummy/craft_nature_paste", () -> new DummyTooltipItem(defaultProperties()));
        ITEMS.register("ritual_dummy/craft_gray_paste", () -> new DummyTooltipItem(defaultProperties()));
        ITEMS.register("ritual_dummy/craft_research_fragment_dust", () -> new DummyTooltipItem(defaultProperties()));
        ITEMS.register("ritual_dummy/craft_witherite_dust", () -> new DummyTooltipItem(defaultProperties()));
        ITEMS.register("ritual_dummy/craft_dragonyst_dust", () -> new DummyTooltipItem(defaultProperties()));
        ITEMS.register("ritual_dummy/repair_chalks", () -> new DummyTooltipItem(defaultProperties()));
        ITEMS.register("ritual_dummy/repair_tools", () -> new DummyTooltipItem(defaultProperties()));
        ITEMS.register("ritual_dummy/repair_armors", () -> new DummyTooltipItem(defaultProperties()));
        ITEMS.register("ritual_dummy/repair_miners", () -> new DummyTooltipItem(defaultProperties()));
    }
    public static final DeferredItem<Item> RITUAL_DUMMY_CRAFT_RITUAL_SATCHEL_T1 = ITEMS.register("ritual_dummy/craft_ritual_satchel_t1", () -> new DummyTooltipItem(defaultProperties()));
    public static final DeferredItem<Item> RITUAL_DUMMY_CRAFT_RITUAL_SATCHEL_T2 = ITEMS.register("ritual_dummy/craft_ritual_satchel_t2", () -> new DummyTooltipItem(defaultProperties()));
    public static final DeferredItem<Item> RITUAL_DUMMY_CRAFT_IESNIUM_SACRIFICIAL_BOWL = ITEMS.register("ritual_dummy/craft_iesnium_sacrificial_bowl", () -> new DummyTooltipItem(defaultProperties()));
    public static final DeferredItem<Item> RITUAL_DUMMY_CRAFT_IESNIUM_ANVIL = ITEMS.register("ritual_dummy/craft_iesnium_anvil", () -> new DummyTooltipItem(defaultProperties()));

    //MISC
        //Resurrect
    public static final DeferredItem<Item> RITUAL_DUMMY_RESURRECT_FAMILIAR = ITEMS.register("ritual_dummy/resurrect_familiar", () -> new DummyTooltipItem(defaultProperties()));
    public static final DeferredItem<Item> RITUAL_DUMMY_RESURRECT_ALLAY = ITEMS.register("ritual_dummy/resurrect_allay", () -> new DummyTooltipItem(defaultProperties()));
        //Wild (group possess)
    public static final DeferredItem<Item> RITUAL_DUMMY_WILD_HUNT = ITEMS.register("ritual_dummy/wild_hunt", () -> new DummyTooltipItem(defaultProperties()));
    public static final DeferredItem<Item> RITUAL_DUMMY_WILD_HUSK = ITEMS.register("ritual_dummy/wild_husk", () -> new DummyTooltipItem(defaultProperties()));
    public static final DeferredItem<Item> RITUAL_DUMMY_WILD_DROWNED = ITEMS.register("ritual_dummy/wild_drowned", () -> new DummyTooltipItem(defaultProperties()));
    public static final DeferredItem<Item> RITUAL_DUMMY_WILD_CREEPER = ITEMS.register("ritual_dummy/wild_creeper", () -> new DummyTooltipItem(defaultProperties()));
    public static final DeferredItem<Item> RITUAL_DUMMY_WILD_SILVERFISH = ITEMS.register("ritual_dummy/wild_silverfish", () -> new DummyTooltipItem(defaultProperties()));
    public static final DeferredItem<Item> RITUAL_DUMMY_WILD_WEAK_BREEZE = ITEMS.register("ritual_dummy/wild_weak_breeze", () -> new DummyTooltipItem(defaultProperties()));
    public static final DeferredItem<Item> RITUAL_DUMMY_WILD_BREEZE = ITEMS.register("ritual_dummy/wild_breeze", () -> new DummyTooltipItem(defaultProperties()));
    public static final DeferredItem<Item> RITUAL_DUMMY_WILD_STRONG_BREEZE = ITEMS.register("ritual_dummy/wild_strong_breeze", () -> new DummyTooltipItem(defaultProperties()));
    public static final DeferredItem<Item> RITUAL_DUMMY_WILD_ILLAGER = ITEMS.register("ritual_dummy/wild_horde_illager", () -> new DummyTooltipItem(defaultProperties()));
    public static final DeferredItem<Item> RITUAL_DUMMY_WILD_RANDOM_ANIMAL_COMMON = ITEMS.register("ritual_dummy/wild_random_animal_common", () -> new DummyTooltipItem(defaultProperties()));
    public static final DeferredItem<Item> RITUAL_DUMMY_WILD_RANDOM_ANIMAL_WATER = ITEMS.register("ritual_dummy/wild_random_animal_water", () -> new DummyTooltipItem(defaultProperties()));
    public static final DeferredItem<Item> RITUAL_DUMMY_WILD_RANDOM_ANIMAL_SMALL = ITEMS.register("ritual_dummy/wild_random_animal_small", () -> new DummyTooltipItem(defaultProperties()));
    public static final DeferredItem<Item> RITUAL_DUMMY_WILD_RANDOM_ANIMAL_RIDEABLE = ITEMS.register("ritual_dummy/wild_random_animal_rideable", () -> new DummyTooltipItem(defaultProperties()));
    public static final DeferredItem<Item> RITUAL_DUMMY_WILD_RANDOM_ANIMAL_SPECIAL = ITEMS.register("ritual_dummy/wild_random_animal_special", () -> new DummyTooltipItem(defaultProperties()));
    public static final DeferredItem<Item> RITUAL_DUMMY_WILD_VILLAGER = ITEMS.register("ritual_dummy/wild_villager", () -> new DummyTooltipItem(defaultProperties()));
        //Forge (cursed craft) misc in ID
    public static final DeferredItem<Item> RITUAL_DUMMY_FORGE_WILD_TRIM = ITEMS.register("ritual_dummy/misc_wild_trim", () -> new DummyTooltipItem(defaultProperties()));
    public static final DeferredItem<Item> RITUAL_DUMMY_FORGE_BUDDING_AMETHYST = ITEMS.register("ritual_dummy/misc_budding_amethyst", () -> new DummyTooltipItem(defaultProperties()));
    public static final DeferredItem<Item> RITUAL_DUMMY_FORGE_REINFORCED_DEEPSLATE = ITEMS.register("ritual_dummy/misc_reinforced_deepslate", () -> new DummyTooltipItem(defaultProperties()));
    public static final DeferredItem<Item> RITUAL_DUMMY_FORGE_BEE_NEST = ITEMS.register("ritual_dummy/misc_bee_nest", () -> new DummyTooltipItem(defaultProperties()));
    public static final DeferredItem<Item> RITUAL_DUMMY_FORGE_BELL = ITEMS.register("ritual_dummy/misc_bell", () -> new DummyTooltipItem(defaultProperties()));
    public static final DeferredItem<Item> RITUAL_DUMMY_FORGE_MINER_ANCIENT_ELDRITCH = ITEMS.register("ritual_dummy/misc_miner_ancient_eldritch", () -> new DummyTooltipItem(defaultProperties()));
    public static final DeferredItem<Item> RITUAL_DUMMY_FORGE_ELDRITCH_CHALICE = ITEMS.register("ritual_dummy/misc_eldritch_chalice", () -> new DummyTooltipItem(defaultProperties()));
    public static final DeferredItem<Item> RITUAL_DUMMY_FORGE_STABILIZED_STORAGE = ITEMS.register("ritual_dummy/misc_stabilized_storage", () -> new DummyTooltipItem(defaultProperties()));
    public static final DeferredItem<Item> RITUAL_DUMMY_FORGE_CHALK_RAINBOW = ITEMS.register("ritual_dummy/misc_chalk_rainbow", () -> new DummyTooltipItem(defaultProperties()));
    public static final DeferredItem<Item> RITUAL_DUMMY_FORGE_CHALK_VOID = ITEMS.register("ritual_dummy/misc_chalk_void", () -> new DummyTooltipItem(defaultProperties()));
    public static final DeferredItem<Item> RITUAL_DUMMY_FORGE_TRINITY_GEM = ITEMS.register("ritual_dummy/misc_trinity_gem", () -> new DummyTooltipItem(defaultProperties()));

    // CUSTOM
    public static final DeferredItem<Item> RITUAL_DUMMY_CUSTOM_SUMMON = ITEMS.register("ritual_dummy/custom_ritual_summon", () -> new DummyTooltipItem(defaultProperties()));
    public static final DeferredItem<Item> RITUAL_DUMMY_CUSTOM_POSSESS = ITEMS.register("ritual_dummy/custom_ritual_possess", () -> new DummyTooltipItem(defaultProperties()));
    public static final DeferredItem<Item> RITUAL_DUMMY_CUSTOM_CRAFT = ITEMS.register("ritual_dummy/custom_ritual_craft", () -> new DummyTooltipItem(defaultProperties()));
    public static final DeferredItem<Item> RITUAL_DUMMY_CUSTOM_MISC = ITEMS.register("ritual_dummy/custom_ritual_misc", () -> new DummyTooltipItem(defaultProperties()));

    //DEBUG
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
    public static final DeferredItem<MinerSpiritItem> MINER_DEBUG_UNSPECIALIZED =
            ITEMS.register("miner_debug_unspecialized",
                    () -> new MinerSpiritItem(defaultProperties()
                            .component(OccultismDataComponents.SPIRIT_NAME, TextUtil.SPIRIT_NAME_NOT_YET_KNOWN)
                            .durability(10000), () -> 100, () -> 10, () -> 10000));

    //Placeholders
    public static final DeferredItem<Item> DICTIONARY_OF_SPIRITS_ICON =
            ITEMS.register("dictionary_of_spirits_icon", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> PENTACLE_SUMMON = ITEMS.register("pentacle_summon",
            () -> new Item(defaultProperties()));
    public static final DeferredItem<Item> PENTACLE_POSSESS = ITEMS.register("pentacle_possess",
            () -> new Item(defaultProperties()));
    public static final DeferredItem<Item> PENTACLE_CRAFT = ITEMS.register("pentacle_craft",
            () -> new Item(defaultProperties()));
    public static final DeferredItem<Item> PENTACLE_MISC = ITEMS.register("pentacle_misc",
            () -> new Item(defaultProperties()));
    public static final DeferredItem<Item> ADVANCEMENT_ICON =
            ITEMS.register("advancement_icon", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> REPAIR_ICON =
            ITEMS.register("repair_icon", () -> new Item(defaultProperties()));
    public static final DeferredItem<Item> RESURRECT_ICON =
            ITEMS.register("resurrect_icon", () -> new Item(defaultProperties()));
    public static final DeferredItem<Item> MYSTERIOUS_EGG_ICON =
            ITEMS.register("mysterious_egg_icon", () -> new Item(defaultProperties()));
    //JEI Dummy Items
    public static final DeferredItem<DummyTooltipItem> JEI_DUMMY_NONE = ITEMS.register(
            "jei_dummy/none", () -> new DummyTooltipItem(new Item.Properties()));
    public static final DeferredItem<DummyTooltipItem> JEI_DUMMY_REQUIRE_SACRIFICE = ITEMS.register(
            "jei_dummy/require_sacrifice", () -> new DummyTooltipItem(new Item.Properties()));
    public static final DeferredItem<DummyTooltipItem> JEI_DUMMY_REQUIRE_ITEM_USE = ITEMS.register(
            "jei_dummy/require_item_use", () -> new DummyTooltipItem(new Item.Properties()));

    public static Item.Properties defaultProperties() {
        //historically used to add to occultism tab
        return new Item.Properties();
    }

    public static boolean shouldSkipCreativeModTab(Item item) {
        if (item == PENTACLE_SUMMON.get()
                || item == PENTACLE_POSSESS.get()
                || item == PENTACLE_CRAFT.get()
                || item == PENTACLE_MISC.get()
                || item == DICTIONARY_OF_SPIRITS_ICON.get()
                || item == ADVANCEMENT_ICON.get()
                || item == JEI_DUMMY_NONE.get()
                || item == JEI_DUMMY_REQUIRE_SACRIFICE.get()
                || item == JEI_DUMMY_REQUIRE_ITEM_USE.get()
                || item == REPAIR_ICON.get()
                || item == RESURRECT_ICON.get()
                || item == MYSTERIOUS_EGG_ICON.get()
        )
            return true;

        if (item instanceof BlockItem blockItem) {
            return blockItem.getBlock() == OccultismBlocks.LIGHTED_AIR.get();
        }
        return false;
    }

    public static boolean laterCreativeModTab(Item item) {
        return item.toString().contains("ritual_dummy")
                || item.toString().contains("debug")
                || item.toString().contains("spawn_egg");
    }

    public static boolean shouldPregenerateSpiritName(Item item) {
        return item == BOOK_OF_BINDING_BOUND_FOLIOT.get()
                || item == BOOK_OF_BINDING_BOUND_DJINNI.get()
                || item == BOOK_OF_BINDING_BOUND_AFRIT.get()
                || item == BOOK_OF_BINDING_BOUND_MARID.get()
                || item == BOOK_OF_CALLING_FOLIOT_LUMBERJACK.get()
                || item == BOOK_OF_CALLING_FOLIOT_TRANSPORT_ITEMS.get()
                || item == BOOK_OF_CALLING_FOLIOT_CLEANER.get()
                || item == BOOK_OF_CALLING_DJINNI_MANAGE_MACHINE.get()
                || item == DIMENSIONAL_MATRIX.get()
                || item == SATCHEL.get()
                || item == RITUAL_SATCHEL_T1.get()
                || item == RITUAL_SATCHEL_T2.get()
                || item == FAMILIAR_RING.get()
                || item == INFUSED_PICKAXE.get()
                || item == MINER_FOLIOT_UNSPECIALIZED.get()
                || item == MINER_DJINNI_ORES.get()
                || item == MINER_AFRIT_DEEPS.get()
                || item == MINER_MARID_MASTER.get()
                || item == MINER_ANCIENT_ELDRITCH.get()
                || item == MINER_DEBUG_UNSPECIALIZED.get();
    }
}
