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
import com.klikli_dev.occultism.common.item.FlameAutomationItem;
import com.klikli_dev.occultism.common.item.armor.OtherworldGogglesItem;
import com.klikli_dev.occultism.common.item.debug.*;
import com.klikli_dev.occultism.common.item.spirit.*;
import com.klikli_dev.occultism.common.item.storage.*;
import com.klikli_dev.occultism.common.item.tool.*;
import com.klikli_dev.occultism.common.item.tool.BrushItem;
import com.klikli_dev.occultism.common.item.tool.ritual_satchel.MultiBlockRitualSatchelItem;
import com.klikli_dev.occultism.common.item.tool.ritual_satchel.SingleBlockRitualSatchelItem;
import com.klikli_dev.occultism.util.TextUtil;
import net.minecraft.core.Direction;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.*;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.component.ItemContainerContents;
import net.minecraft.world.item.equipment.EquipmentAsset;
import net.minecraft.world.item.equipment.EquipmentAssets;
import net.minecraft.world.item.equipment.Equippable;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class OccultismItems {

    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(Occultism.MODID);
    //Dictionary
    public static final DeferredItem<GuideBookItem> DICTIONARY_OF_SPIRITS = ITEMS.registerItem("dictionary_of_spirits",
            GuideBookItem::new, () -> new Properties().stacksTo(1));
    //Tools and equipable
    public static final DeferredItem<DivinationRodItem> DIVINATION_ROD = ITEMS.registerItem("divination_rod",
            DivinationRodItem::new, () -> new Properties().stacksTo(1));
    public static final DeferredItem<DivinationRodItem> TRUE_SIGHT_STAFF = ITEMS.registerItem("true_sight_staff",
            DivinationRodItem::new, () -> new Properties().stacksTo(1).rarity(Rarity.RARE).fireResistant());
    public static final DeferredItem<Item> BUTCHER_KNIFE = ITEMS.registerItem("butcher_knife",
            Item::new, () -> (ToolMaterial.IRON.applySwordProperties(new Properties(), 3, -2.4F)));
    public static final DeferredItem<Item> IESNIUM_BUTCHER_KNIFE = ITEMS.registerItem("iesnium_butcher_knife",
            Item::new, () -> OccultismTiers.IESNIUM.applySwordProperties(new Properties().rarity(Rarity.UNCOMMON)
                    .component(OccultismDataComponents.SPIRIT_NAME, TextUtil.SPIRIT_NAME_NOT_YET_KNOWN), 10, -1.8F));
    public static final DeferredItem<InfusedPickaxeItem> INFUSED_PICKAXE = ITEMS.registerItem("infused_pickaxe",
            properties -> new InfusedPickaxeItem(OccultismTiers.SPIRIT_ATTUNED.applyToolProperties(properties
                            .component(OccultismDataComponents.SPIRIT_NAME, TextUtil.SPIRIT_NAME_NOT_YET_KNOWN),
                    BlockTags.MINEABLE_WITH_PICKAXE, 1.0F, -2.8F, 0)));
    public static final DeferredItem<OtherworldPickaxeItem> IESNIUM_PICKAXE = ITEMS.registerItem("iesnium_pickaxe",
            properties -> new OtherworldPickaxeItem(OccultismTiers.IESNIUM.applyToolProperties(properties,
                    BlockTags.MINEABLE_WITH_PICKAXE, 1.0F, -2.8F, 0)));
    public static final DeferredItem<SatchelItem> SATCHEL = ITEMS.registerItem("satchel",
            SatchelItem::new, () -> new Properties().stacksTo(1)
                    .component(DataComponents.CONTAINER, ItemContainerContents.EMPTY)
                    .component(OccultismDataComponents.SPIRIT_NAME, TextUtil.SPIRIT_NAME_NOT_YET_KNOWN));
    public static final DeferredItem<EnderSatchelItem> ENDER_SATCHEL = ITEMS.registerItem("ender_satchel",
            EnderSatchelItem::new, () -> new Properties().stacksTo(1)
                    .component(OccultismDataComponents.SPIRIT_NAME, TextUtil.SPIRIT_NAME_NOT_YET_KNOWN));
    public static final DeferredItem<SingleBlockRitualSatchelItem> RITUAL_SATCHEL_T1 = ITEMS.registerItem("ritual_satchel_t1",
            SingleBlockRitualSatchelItem::new, () -> new Properties()
                    .stacksTo(1)
                    .component(DataComponents.CONTAINER, ItemContainerContents.EMPTY)
                    .component(OccultismDataComponents.SPIRIT_NAME, TextUtil.SPIRIT_NAME_NOT_YET_KNOWN));
    public static final DeferredItem<MultiBlockRitualSatchelItem> RITUAL_SATCHEL_T2 = ITEMS.registerItem("ritual_satchel_t2",
            MultiBlockRitualSatchelItem::new, () -> new Properties()
                    .stacksTo(1).rarity(Rarity.UNCOMMON).fireResistant()
                    .component(DataComponents.CONTAINER, ItemContainerContents.EMPTY)
                    .component(OccultismDataComponents.SPIRIT_NAME, TextUtil.SPIRIT_NAME_NOT_YET_KNOWN));
    public static final DeferredItem<KnowledgeTabletItem> KNOWLEDGE_TABLET = ITEMS.registerItem("knowledge_tablet",
            KnowledgeTabletItem::new, () -> new Properties().stacksTo(1)
                    .component(OccultismDataComponents.SPIRIT_NAME, TextUtil.SPIRIT_NAME_NOT_YET_KNOWN)
                    .component(OccultismDataComponents.STORED_XP, 0));
    public static final DeferredItem<StorageRemoteItem> STORAGE_REMOTE = ITEMS.registerItem("storage_remote",
            StorageRemoteItem::new, () -> new Properties().stacksTo(1)
                    .component(OccultismDataComponents.SPIRIT_NAME, TextUtil.SPIRIT_NAME_NOT_YET_KNOWN));
    public static final DeferredItem<SoulGemItem> FRAGILE_SOUL_GEM_ITEM = ITEMS.registerItem("fragile_soul_gem",
            SoulGemItem::new, () -> new Properties().stacksTo(1).component(OccultismDataComponents.FAIL_CHANCE, 0.8F));
    public static final DeferredItem<SoulGemItem> SOUL_GEM_ITEM = ITEMS.registerItem("soul_gem",
            SoulGemItem::new, () -> new Properties().stacksTo(1));
    public static final DeferredItem<SoulGemItem> TRINITY_GEM_ITEM = ITEMS.registerItem("trinity_gem",
            SoulGemItem::new, () -> new Properties().stacksTo(1).rarity(Rarity.EPIC).fireResistant()
                    .component(OccultismDataComponents.ROLLS_PER_OPERATION, 3));
    public static final DeferredItem<Item> FAMILIAR_RING = ITEMS.registerItem("familiar_ring",
            FamiliarRingItem::new, () -> new Properties().stacksTo(1).component(OccultismDataComponents.SPIRIT_NAME, TextUtil.SPIRIT_NAME_NOT_YET_KNOWN));
    public static final DeferredItem<Item> VITALITY_COMPASS = ITEMS.registerItem("vitality_compass",
            VitalityCompassItem::new, () -> new Properties().stacksTo(1)
                    .component(OccultismDataComponents.SPIRIT_NAME, TextUtil.SPIRIT_NAME_NOT_YET_KNOWN));
    //Books of Binding
    public static final DeferredItem<BookOfBindingItem> BOOK_OF_BINDING_EMPTY = ITEMS.registerItem(
            "book_of_binding_empty", BookOfBindingItem::new, () -> new Properties().stacksTo(16));
    public static final DeferredItem<BookOfBindingItem> BOOK_OF_BINDING_FOLIOT = ITEMS.registerItem(
            "book_of_binding_foliot", BookOfBindingItem::new, () -> new Properties().stacksTo(16));
    public static final DeferredItem<BookOfBindingBoundItem> BOOK_OF_BINDING_BOUND_FOLIOT = ITEMS.registerItem(
            "book_of_binding_bound_foliot", BookOfBindingBoundItem::new, () -> new Properties().stacksTo(1).component(OccultismDataComponents.SPIRIT_NAME, TextUtil.SPIRIT_NAME_NOT_YET_KNOWN));
    public static final DeferredItem<BookOfBindingItem> BOOK_OF_BINDING_DJINNI = ITEMS.registerItem(
            "book_of_binding_djinni", BookOfBindingItem::new, () -> new Properties().stacksTo(16));
    public static final DeferredItem<BookOfBindingBoundItem> BOOK_OF_BINDING_BOUND_DJINNI = ITEMS.registerItem(
            "book_of_binding_bound_djinni", BookOfBindingBoundItem::new, () -> new Properties().stacksTo(1).component(OccultismDataComponents.SPIRIT_NAME, TextUtil.SPIRIT_NAME_NOT_YET_KNOWN));
    public static final DeferredItem<BookOfBindingItem> BOOK_OF_BINDING_AFRIT = ITEMS.registerItem(
            "book_of_binding_afrit", BookOfBindingItem::new, () -> new Properties().stacksTo(16));
    public static final DeferredItem<BookOfBindingBoundItem> BOOK_OF_BINDING_BOUND_AFRIT = ITEMS.registerItem(
            "book_of_binding_bound_afrit", BookOfBindingBoundItem::new, () -> new Properties().stacksTo(1).rarity(Rarity.UNCOMMON).fireResistant().component(OccultismDataComponents.SPIRIT_NAME, TextUtil.SPIRIT_NAME_NOT_YET_KNOWN));
    public static final DeferredItem<BookOfBindingItem> BOOK_OF_BINDING_MARID = ITEMS.registerItem(
            "book_of_binding_marid", BookOfBindingItem::new, () -> new Properties().stacksTo(16));
    public static final DeferredItem<BookOfBindingBoundItem> BOOK_OF_BINDING_BOUND_MARID = ITEMS.registerItem(
            "book_of_binding_bound_marid", BookOfBindingBoundItem::new, () -> new Properties().stacksTo(1).rarity(Rarity.RARE).fireResistant().component(OccultismDataComponents.SPIRIT_NAME, TextUtil.SPIRIT_NAME_NOT_YET_KNOWN));
    //Books of Calling
    //Foliot
    public static final DeferredItem<BookOfCallingLumberjackItem> BOOK_OF_CALLING_FOLIOT_LUMBERJACK =
            ITEMS.registerItem("book_of_calling_foliot_lumberjack",
                    properties -> new BookOfCallingLumberjackItem(properties.stacksTo(1).component(OccultismDataComponents.SPIRIT_NAME, TextUtil.SPIRIT_NAME_NOT_YET_KNOWN),
                            TranslationKeys.BOOK_OF_CALLING_GENERIC + "_foliot"));
    public static final DeferredItem<BookOfCallingFarmerItem> BOOK_OF_CALLING_FOLIOT_FARMER =
            ITEMS.registerItem("book_of_calling_foliot_farmer",
                    properties -> new BookOfCallingFarmerItem(properties.stacksTo(1).component(OccultismDataComponents.SPIRIT_NAME, TextUtil.SPIRIT_NAME_NOT_YET_KNOWN),
                            TranslationKeys.BOOK_OF_CALLING_GENERIC + "_foliot"));
    public static final DeferredItem<BookOfCallingTransportItems> BOOK_OF_CALLING_FOLIOT_TRANSPORT_ITEMS =
            ITEMS.registerItem("book_of_calling_foliot_transport_items",
                    properties -> new BookOfCallingTransportItems(properties.stacksTo(1).component(OccultismDataComponents.SPIRIT_NAME, TextUtil.SPIRIT_NAME_NOT_YET_KNOWN),
                            TranslationKeys.BOOK_OF_CALLING_GENERIC + "_foliot"));
    public static final DeferredItem<BookOfCallingCleanerItem> BOOK_OF_CALLING_FOLIOT_CLEANER =
            ITEMS.registerItem("book_of_calling_foliot_cleaner",
                    properties -> new BookOfCallingCleanerItem(properties.stacksTo(1).component(OccultismDataComponents.SPIRIT_NAME, TextUtil.SPIRIT_NAME_NOT_YET_KNOWN),
                            TranslationKeys.BOOK_OF_CALLING_GENERIC + "_foliot"));
    //Djinn
    public static final DeferredItem<BookOfCallingManageMachineItem> BOOK_OF_CALLING_DJINNI_MANAGE_MACHINE =
            ITEMS.registerItem("book_of_calling_djinni_manage_machine",
                    properties -> new BookOfCallingManageMachineItem(properties.stacksTo(1).component(OccultismDataComponents.SPIRIT_NAME, TextUtil.SPIRIT_NAME_NOT_YET_KNOWN),
                            TranslationKeys.BOOK_OF_CALLING_GENERIC + "_djinni"));
    //Brush. Chalks and Impure Chalks
    public static final DeferredItem<BrushItem> BRUSH = ITEMS.registerItem("brush",
            BrushItem::new, () -> new Properties().stacksTo(1));
    public static final DeferredItem<Item> CHALK_WHITE = ITEMS.registerItem("chalk_white",
            properties -> new ChalkItem(properties.durability(256),
                    OccultismBlocks.CHALK_GLYPH_WHITE.get()));
    public static final DeferredItem<Item> CHALK_LIGHT_GRAY = ITEMS.registerItem("chalk_light_gray",
            properties -> new ChalkItem(properties.durability(256),
                    OccultismBlocks.CHALK_GLYPH_LIGHT_GRAY.get()));
    public static final DeferredItem<Item> CHALK_GRAY = ITEMS.registerItem("chalk_gray",
            properties -> new ChalkItem(properties.durability(256),
                    OccultismBlocks.CHALK_GLYPH_GRAY.get()));
    public static final DeferredItem<Item> CHALK_BLACK = ITEMS.registerItem("chalk_black",
            properties -> new ChalkItem(properties.durability(256).rarity(Rarity.UNCOMMON).fireResistant(),
                    OccultismBlocks.CHALK_GLYPH_BLACK.get()));
    public static final DeferredItem<Item> CHALK_BROWN = ITEMS.registerItem("chalk_brown",
            properties -> new ChalkItem(properties.durability(256).rarity(Rarity.RARE).fireResistant(),
                    OccultismBlocks.CHALK_GLYPH_BROWN.get()));
    public static final DeferredItem<Item> CHALK_RED = ITEMS.registerItem("chalk_red",
            properties -> new ChalkItem(properties.durability(256).rarity(Rarity.UNCOMMON).fireResistant(),
                    OccultismBlocks.CHALK_GLYPH_RED.get()));
    public static final DeferredItem<Item> CHALK_ORANGE = ITEMS.registerItem("chalk_orange",
            properties -> new ChalkItem(properties.durability(256),
                    OccultismBlocks.CHALK_GLYPH_ORANGE.get()));
    public static final DeferredItem<Item> CHALK_YELLOW = ITEMS.registerItem("chalk_yellow",
            properties -> new ChalkItem(properties.durability(256),
                    OccultismBlocks.CHALK_GLYPH_YELLOW.get()));
    public static final DeferredItem<Item> CHALK_LIME = ITEMS.registerItem("chalk_lime",
            properties -> new ChalkItem(properties.durability(256),
                    OccultismBlocks.CHALK_GLYPH_LIME.get()));
    public static final DeferredItem<Item> CHALK_GREEN = ITEMS.registerItem("chalk_green",
            properties -> new ChalkItem(properties.durability(256),
                    OccultismBlocks.CHALK_GLYPH_GREEN.get()));
    public static final DeferredItem<Item> CHALK_CYAN = ITEMS.registerItem("chalk_cyan",
            properties -> new ChalkItem(properties.durability(256).rarity(Rarity.RARE).fireResistant(),
                    OccultismBlocks.CHALK_GLYPH_CYAN.get()));
    public static final DeferredItem<Item> CHALK_LIGHT_BLUE = ITEMS.registerItem("chalk_light_blue",
            properties -> new ChalkItem(properties.durability(256),
                    OccultismBlocks.CHALK_GLYPH_LIGHT_BLUE.get()));
    public static final DeferredItem<Item> CHALK_BLUE = ITEMS.registerItem("chalk_blue",
            properties -> new ChalkItem(properties.durability(256).rarity(Rarity.RARE).fireResistant(),
                    OccultismBlocks.CHALK_GLYPH_BLUE.get()));
    public static final DeferredItem<Item> CHALK_PURPLE = ITEMS.registerItem("chalk_purple",
            properties -> new ChalkItem(properties.durability(256),
                    OccultismBlocks.CHALK_GLYPH_PURPLE.get()));
    public static final DeferredItem<Item> CHALK_MAGENTA = ITEMS.registerItem("chalk_magenta",
            properties -> new ChalkItem(properties.durability(256).rarity(Rarity.RARE).fireResistant(),
                    OccultismBlocks.CHALK_GLYPH_MAGENTA.get()));
    public static final DeferredItem<Item> CHALK_PINK = ITEMS.registerItem("chalk_pink",
            properties -> new ChalkItem(properties.durability(256).rarity(Rarity.UNCOMMON).fireResistant(),
                    OccultismBlocks.CHALK_GLYPH_PINK.get()));
    public static final DeferredItem<Item> CHALK_RAINBOW = ITEMS.registerItem("chalk_rainbow",
            properties -> new ChalkItem(properties.stacksTo(1).rarity(Rarity.EPIC).fireResistant(),
                    OccultismBlocks.CHALK_GLYPH_RAINBOW.get(), true));
    public static final DeferredItem<Item> CHALK_VOID = ITEMS.registerItem("chalk_void",
            properties -> new ChalkItem(properties.stacksTo(1).rarity(Rarity.EPIC).fireResistant(),
                    OccultismBlocks.CHALK_GLYPH_VOID.get(), true));
    public static final DeferredItem<Item> CHALK_WHITE_IMPURE = ITEMS.registerItem("chalk_white_impure",
            Item::new, () -> new Properties());
    public static final DeferredItem<Item> CHALK_LIGHT_GRAY_IMPURE = ITEMS.registerItem("chalk_light_gray_impure",
            Item::new, () -> new Properties());
    public static final DeferredItem<Item> CHALK_GRAY_IMPURE = ITEMS.registerItem("chalk_gray_impure",
            Item::new, () -> new Properties());
    public static final DeferredItem<Item> CHALK_BLACK_IMPURE = ITEMS.registerItem("chalk_black_impure",
            Item::new, () -> new Properties().rarity(Rarity.UNCOMMON).fireResistant());
    public static final DeferredItem<Item> CHALK_BROWN_IMPURE = ITEMS.registerItem("chalk_brown_impure",
            Item::new, () -> new Properties().rarity(Rarity.RARE).fireResistant());
    public static final DeferredItem<Item> CHALK_RED_IMPURE = ITEMS.registerItem("chalk_red_impure",
            Item::new, () -> new Properties().rarity(Rarity.UNCOMMON).fireResistant());
    public static final DeferredItem<Item> CHALK_ORANGE_IMPURE = ITEMS.registerItem("chalk_orange_impure",
            Item::new, () -> new Properties());
    public static final DeferredItem<Item> CHALK_YELLOW_IMPURE = ITEMS.registerItem("chalk_yellow_impure",
            Item::new, () -> new Properties());
    public static final DeferredItem<Item> CHALK_LIME_IMPURE = ITEMS.registerItem("chalk_lime_impure",
            Item::new, () -> new Properties());
    public static final DeferredItem<Item> CHALK_GREEN_IMPURE = ITEMS.registerItem("chalk_green_impure",
            Item::new, () -> new Properties());
    public static final DeferredItem<Item> CHALK_CYAN_IMPURE = ITEMS.registerItem("chalk_cyan_impure",
            Item::new, () -> new Properties().rarity(Rarity.RARE).fireResistant());
    public static final DeferredItem<Item> CHALK_LIGHT_BLUE_IMPURE = ITEMS.registerItem("chalk_light_blue_impure",
            Item::new, () -> new Properties());
    public static final DeferredItem<Item> CHALK_BLUE_IMPURE = ITEMS.registerItem("chalk_blue_impure",
            Item::new, () -> new Properties().rarity(Rarity.RARE).fireResistant());
    public static final DeferredItem<Item> CHALK_PURPLE_IMPURE = ITEMS.registerItem("chalk_purple_impure",
            Item::new, () -> new Properties());
    public static final DeferredItem<Item> CHALK_MAGENTA_IMPURE = ITEMS.registerItem("chalk_magenta_impure",
            Item::new, () -> new Properties().rarity(Rarity.RARE).fireResistant());
    public static final DeferredItem<Item> CHALK_PINK_IMPURE = ITEMS.registerItem("chalk_pink_impure",
            Item::new, () -> new Properties().rarity(Rarity.UNCOMMON).fireResistant());
    //Miner Spirits
    public static final DeferredItem<MagicLampItem> MAGIC_LAMP_EMPTY = ITEMS.registerItem("magic_lamp_empty",
            MagicLampItem::new, () -> new Properties().stacksTo(1).component(OccultismDataComponents.SPIRIT_NAME, TextUtil.SPIRIT_NAME_NOT_YET_KNOWN));
    public static final DeferredItem<MinerSpiritItem> MINER_FOLIOT_UNSPECIALIZED = ITEMS.registerItem("miner_foliot_unspecialized",
            properties -> new MinerSpiritItem(properties
                    .component(OccultismDataComponents.SPIRIT_NAME, TextUtil.SPIRIT_NAME_NOT_YET_KNOWN)
                    .durability(1000),
                    Occultism.STARTUP_CONFIG.dimensionalMineshaft.minerFoliotUnspecialized.maxMiningTime,
                    Occultism.STARTUP_CONFIG.dimensionalMineshaft.minerFoliotUnspecialized.rollsPerOperation,
                    Occultism.STARTUP_CONFIG.dimensionalMineshaft.minerFoliotUnspecialized.outputMultiplier,
                    Occultism.STARTUP_CONFIG.dimensionalMineshaft.minerFoliotUnspecialized.durability));
    public static final DeferredItem<MinerSpiritItem> MINER_DJINNI_ORES = ITEMS.registerItem("miner_djinni_ores",
            properties -> new MinerSpiritItem(properties.durability(400)
                    .component(OccultismDataComponents.SPIRIT_NAME, TextUtil.SPIRIT_NAME_NOT_YET_KNOWN),
                    Occultism.STARTUP_CONFIG.dimensionalMineshaft.minerDjinniOres.maxMiningTime,
                    Occultism.STARTUP_CONFIG.dimensionalMineshaft.minerDjinniOres.rollsPerOperation,
                    Occultism.STARTUP_CONFIG.dimensionalMineshaft.minerDjinniOres.outputMultiplier,
                    Occultism.STARTUP_CONFIG.dimensionalMineshaft.minerDjinniOres.durability));
    public static final DeferredItem<MinerSpiritItem> MINER_AFRIT_DEEPS = ITEMS.registerItem("miner_afrit_deeps",
            properties -> new MinerSpiritItem(properties.durability(800).rarity(Rarity.UNCOMMON).fireResistant()
                    .component(OccultismDataComponents.SPIRIT_NAME, TextUtil.SPIRIT_NAME_NOT_YET_KNOWN),
                    Occultism.STARTUP_CONFIG.dimensionalMineshaft.minerAfritDeeps.maxMiningTime,
                    Occultism.STARTUP_CONFIG.dimensionalMineshaft.minerAfritDeeps.rollsPerOperation,
                    Occultism.STARTUP_CONFIG.dimensionalMineshaft.minerAfritDeeps.outputMultiplier,
                    Occultism.STARTUP_CONFIG.dimensionalMineshaft.minerAfritDeeps.durability));
    public static final DeferredItem<MinerSpiritItem> MINER_MARID_MASTER = ITEMS.registerItem("miner_marid_master",
            properties -> new MinerSpiritItem(properties.durability(1600).rarity(Rarity.RARE).fireResistant()
                    .component(OccultismDataComponents.SPIRIT_NAME, TextUtil.SPIRIT_NAME_NOT_YET_KNOWN),
                    Occultism.STARTUP_CONFIG.dimensionalMineshaft.minerMaridMaster.maxMiningTime,
                    Occultism.STARTUP_CONFIG.dimensionalMineshaft.minerMaridMaster.rollsPerOperation,
                    Occultism.STARTUP_CONFIG.dimensionalMineshaft.minerMaridMaster.outputMultiplier,
                    Occultism.STARTUP_CONFIG.dimensionalMineshaft.minerMaridMaster.durability));
    public static final DeferredItem<MinerSpiritItem> MINER_ANCIENT_ELDRITCH = ITEMS.registerItem("miner_ancient_eldritch",
            properties -> new MinerSpiritItem(properties.durability(3200).rarity(Rarity.EPIC).fireResistant()
                    .component(OccultismDataComponents.SPIRIT_NAME, TextUtil.SPIRIT_NAME_NOT_YET_KNOWN),
                    Occultism.STARTUP_CONFIG.dimensionalMineshaft.minerAncientEldritch.maxMiningTime,
                    Occultism.STARTUP_CONFIG.dimensionalMineshaft.minerAncientEldritch.rollsPerOperation,
                    Occultism.STARTUP_CONFIG.dimensionalMineshaft.minerAncientEldritch.outputMultiplier,
                    Occultism.STARTUP_CONFIG.dimensionalMineshaft.minerAncientEldritch.durability));
    //Crops and food
    public static final DeferredItem<BlockItem> DATURA_SEEDS = ITEMS.registerItem("datura_seeds",
            properties -> new BlockItem(OccultismBlocks.DATURA.get(), properties.useBlockDescriptionPrefix()
                    .component(OccultismDataComponents.SOUL_VALUE, 1)));
    public static final DeferredItem<Item> DATURA = ITEMS.registerItem("datura",
            SpiritHealingItem::new, () -> new Properties().food(OccultismFoods.DATURA.get(), OccultismFoods.DATURA_CONSUMABLE)
                    .component(OccultismDataComponents.SOUL_VALUE, 2));
    public static final DeferredItem<Item> DEMONS_DREAM_ESSENCE = ITEMS.registerItem("demons_dream_essence",
            SpiritHealingItem::new, () -> new Properties().food(OccultismFoods.DEMONS_DREAM_ESSENCE.get(), OccultismFoods.DEMONS_DREAM_ESSENCE_CONSUMABLE)
                    .component(OccultismDataComponents.SOUL_VALUE, 20));
    public static final DeferredItem<Item> OTHERWORLD_ESSENCE = ITEMS.registerItem("otherworld_essence",
            SpiritHealingItem::new, () -> new Properties().food(OccultismFoods.OTHERWORLD_ESSENCE.get(), OccultismFoods.OTHERWORLD_ESSENCE_CONSUMABLE)
                    .component(OccultismDataComponents.SOUL_VALUE, 32).component(OccultismDataComponents.LUCK_VALUE, 2));
    public static final DeferredItem<Item> BEAVER_NUGGET = ITEMS.registerItem("beaver_nugget",
            Item::new, () -> new Properties().food(OccultismFoods.BEAVER_NUGGET.get(), OccultismFoods.BEAVER_NUGGET_CONSUMABLE));
    public static final DeferredItem<Item> CURSED_HONEY = ITEMS.registerItem("cursed_honey",
            Item::new, () -> new Properties().food(OccultismFoods.CURSED_HONEY.get(), OccultismFoods.CURSED_HONEY_CONSUMABLE));
    public static final DeferredItem<Item> SWEET_HONEY_HEART = ITEMS.registerItem("sweet_honey_heart",
            Item::new, () -> new Properties().food(OccultismFoods.SWEET_HONEY_HEART.get(), OccultismFoods.SWEET_HONEY_HEART_CONSUMABLE));
    public static final DeferredItem<Item> DEMONIC_MEAT = ITEMS.registerItem("demonic_meat",
            Item::new, () -> new Properties().food(OccultismFoods.DEMONIC_MEAT.get(), OccultismFoods.DEMONIC_MEAT_CONSUMABLE).rarity(Rarity.UNCOMMON).fireResistant());
    //Resources and materials
    public static final DeferredItem<Item> TALLOW = ITEMS.registerItem("tallow",
            Item::new, () -> new Properties());
    public static final DeferredItem<Item> AFRIT_ESSENCE = ITEMS.registerItem("afrit_essence",
            Item::new, () -> new Properties().rarity(Rarity.UNCOMMON).fireResistant());
    public static final DeferredItem<Item> MARID_ESSENCE = ITEMS.registerItem("marid_essence",
            Item::new, () -> new Properties().rarity(Rarity.RARE).fireResistant());
    public static final DeferredItem<Item> CRUELTY_ESSENCE = ITEMS.registerItem("cruelty_essence",
            Item::new, () -> new Properties().rarity(Rarity.RARE).fireResistant());
    public static final DeferredItem<Item> SPIRIT_ATTUNED_GEM = ITEMS.registerItem("spirit_attuned_gem",
            Item::new, () -> new Properties());
    public static final DeferredItem<Item> RAW_SILVER = ITEMS.registerItem("raw_silver",
            Item::new, () -> new Properties());
    public static final DeferredItem<Item> RAW_IESNIUM = ITEMS.registerItem("raw_iesnium",
            Item::new, () -> new Properties());
    public static final DeferredItem<Item> SILVER_INGOT = ITEMS.registerItem("silver_ingot",
            Item::new, () -> new Properties());
    public static final DeferredItem<Item> IESNIUM_INGOT = ITEMS.registerItem("iesnium_ingot",
            Item::new, () -> new Properties());
    public static final DeferredItem<Item> SILVER_NUGGET = ITEMS.registerItem("silver_nugget",
            Item::new, () -> new Properties());
    public static final DeferredItem<Item> IESNIUM_NUGGET = ITEMS.registerItem("iesnium_nugget",
            Item::new, () -> new Properties());
    //Dusts
    public static final DeferredItem<Item> SILVER_DUST = ITEMS.registerItem("silver_dust",
            Item::new, () -> new Properties());
    public static final DeferredItem<Item> IESNIUM_DUST = ITEMS.registerItem("iesnium_dust",
            Item::new, () -> new Properties());
    public static final DeferredItem<Item> COPPER_DUST = ITEMS.registerItem("copper_dust",
            Item::new, () -> new Properties());
    public static final DeferredItem<Item> IRON_DUST = ITEMS.registerItem("iron_dust",
            Item::new, () -> new Properties());
    public static final DeferredItem<Item> GOLD_DUST = ITEMS.registerItem("gold_dust",
            Item::new, () -> new Properties());
    public static final DeferredItem<Item> NETHERITE_SCRAP_DUST = ITEMS.registerItem("netherite_scrap_dust",
            Item::new, () -> new Properties());
    public static final DeferredItem<Item> NETHERITE_DUST = ITEMS.registerItem("netherite_dust",
            Item::new, () -> new Properties());
    public static final DeferredItem<Item> OBSIDIAN_DUST = ITEMS.registerItem("obsidian_dust",
            Item::new, () -> new Properties());
    public static final DeferredItem<Item> LAPIS_DUST = ITEMS.registerItem("lapis_dust",
            Item::new, () -> new Properties());
    public static final DeferredItem<Item> AMETHYST_DUST = ITEMS.registerItem("amethyst_dust",
            Item::new, () -> new Properties());
    public static final DeferredItem<Item> EMERALD_DUST = ITEMS.registerItem("emerald_dust",
            Item::new, () -> new Properties());
    public static final DeferredItem<Item> ECHO_DUST = ITEMS.registerItem("echo_dust",
            Item::new, () -> new Properties().rarity(Rarity.RARE).fireResistant());
    public static final DeferredItem<Item> RESEARCH_FRAGMENT_DUST = ITEMS.registerItem("research_fragment_dust",
            Item::new, () -> new Properties());
    public static final DeferredItem<Item> WITHERITE_DUST = ITEMS.registerItem("witherite_dust",
            Item::new, () -> new Properties().rarity(Rarity.UNCOMMON).fireResistant());
    public static final DeferredItem<Item> DRAGONYST_DUST = ITEMS.registerItem("dragonyst_dust",
            Item::new, () -> new Properties().rarity(Rarity.RARE).fireResistant());
    public static final DeferredItem<Item> CRUSHED_END_STONE = ITEMS.registerItem("crushed_end_stone",
            Item::new, () -> new Properties());
    public static final DeferredItem<Item> CRUSHED_CALCITE = ITEMS.registerItem("crushed_calcite",
            Item::new, () -> new Properties());
    public static final DeferredItem<Item> CRUSHED_BLACKSTONE = ITEMS.registerItem("crushed_blackstone",
            Item::new, () -> new Properties());
    public static final DeferredItem<Item> CRUSHED_ICE = ITEMS.registerItem("crushed_ice",
            Item::new, () -> new Properties());
    public static final DeferredItem<Item> CRUSHED_PACKED_ICE = ITEMS.registerItem("crushed_packed_ice",
            Item::new, () -> new Properties());
    public static final DeferredItem<Item> CRUSHED_BLUE_ICE = ITEMS.registerItem("crushed_blue_ice",
            Item::new, () -> new Properties());
    public static final DeferredItem<Item> BURNT_OTHERSTONE = ITEMS.registerItem("burnt_otherstone",
            Item::new, () -> new Properties());
    public static final DeferredItem<Item> BURNT_OTHERROCK = ITEMS.registerItem("burnt_otherrock",
            Item::new, () -> new Properties());
    public static final DeferredItem<Item> OTHERWORLD_ASHES = ITEMS.registerItem("otherworld_ashes",
            Item::new, () -> new Properties());
    public static final DeferredItem<Item> GRAY_PASTE = ITEMS.registerItem("gray_paste",
            DamageInCraftingItem::new, () -> new Properties().durability(64));
    public static final DeferredItem<Item> NATURE_PASTE = ITEMS.registerItem("nature_paste",
            NaturePasteItem::new, () -> new Properties().durability(64));
    //Components
    public static final DeferredItem<Item> PURIFIED_INK = ITEMS.registerItem("purified_ink",
            Item::new, () -> new Properties());
    public static final DeferredItem<Item> AWAKENED_FEATHER = ITEMS.registerItem("awakened_feather",
            Item::new, () -> new Properties());
    public static final DeferredItem<Item> TABOO_BOOK = ITEMS.registerItem("taboo_book",
            Item::new, () -> new Properties());
    public static final DeferredItem<Item> SPIRIT_ATTUNED_PICKAXE_HEAD = ITEMS.registerItem("spirit_attuned_pickaxe_head",
            Item::new, () -> new Properties());
    public static final DeferredItem<Item> LENSES = ITEMS.registerItem("lenses",
            Item::new, () -> new Properties());
    public static final DeferredItem<Item> INFUSED_LENSES = ITEMS.registerItem("infused_lenses",
            Item::new, () -> new Properties());
    public static final DeferredItem<Item> LENS_FRAME = ITEMS.registerItem("lens_frame",
            Item::new, () -> new Properties());
    public static final DeferredItem<Item> OTHERSTONE_FRAME = ITEMS.registerItem("otherstone_frame",
            Item::new, () -> new Properties());
    public static final DeferredItem<Item> OTHERROCK_FRAME = ITEMS.registerItem("otherrock_frame",
            Item::new, () -> new Properties());
    public static final DeferredItem<Item> OTHERWORLDLY_TABLET = ITEMS.registerItem("otherworldly_tablet",
            Item::new, () -> new Properties());
    public static final DeferredItem<Item> STORAGE_REMOTE_INERT = ITEMS.registerItem("storage_remote_inert",
            Item::new, () -> new Properties());
    public static final DeferredItem<DimensionalMatrixItem> DIMENSIONAL_MATRIX = ITEMS.registerItem("dimensional_matrix",
            DimensionalMatrixItem::new, () -> new Properties().component(OccultismDataComponents.SPIRIT_NAME, TextUtil.SPIRIT_NAME_NOT_YET_KNOWN));
    public static final DeferredItem<Item> MINING_DIMENSION_CORE_PIECE = ITEMS.registerItem("mining_dim_core",
            DamageInCraftingItem::new, () -> new Properties().rarity(Rarity.RARE).fireResistant().durability(Integer.MAX_VALUE)
                    .component(OccultismDataComponents.SPIRIT_NAME, "Something"));
    //Others
    public static final DeferredItem<SoulShardItem> SOUL_SHARD_ITEM = ITEMS.registerItem("soul_shard",
            SoulShardItem::new, () -> new Properties().stacksTo(1));
    public static final DeferredItem<SoulShardItem> SOUL_SHATTERED_ITEM = ITEMS.registerItem("soul_shattered",
            SoulShardItem::new, () -> new Properties().stacksTo(1)
                    .component(OccultismDataComponents.SOUL_VALUE, 1000000).component(OccultismDataComponents.CONSUME_CHANCE, 0.33F));
    //Machines
    public static final DeferredItem<BlockItem> SPIRIT_FIRE =
            ITEMS.registerItem("spirit_fire", properties -> new BlockItem(OccultismBlocks.SPIRIT_FIRE.get(), properties.useBlockDescriptionPrefix()));
    public static final DeferredItem<StableWormholeBlockItem> STABLE_WORMHOLE = ITEMS.registerItem("stable_wormhole",
            properties -> new StableWormholeBlockItem(OccultismBlocks.STABLE_WORMHOLE.get(), properties.useBlockDescriptionPrefix())); //not work if auto-gen
    public static final DeferredItem<StableWormholeBlockItem> STABLE_WORMHOLE_DARK = ITEMS.registerItem("stable_wormhole_dark",
            properties -> new StableWormholeBlockItem(OccultismBlocks.STABLE_WORMHOLE_DARK.get(), properties.useBlockDescriptionPrefix())); //not work if auto-gen
    //Deco Block Items
    public static final DeferredItem<BlockItem> SPIRIT_TORCH = ITEMS.registerItem("spirit_torch",
            properties -> new StandingAndWallBlockItem(OccultismBlocks.SPIRIT_TORCH.get(), OccultismBlocks.SPIRIT_WALL_TORCH.get(), Direction.UP,
                    properties.useBlockDescriptionPrefix()));
    public static final DeferredItem<BlockItem> OTHERPLANKS_SIGN = ITEMS.registerItem("otherplanks_sign",
            properties -> new SignItem(OccultismBlocks.OTHERPLANKS_SIGN.get(), OccultismBlocks.OTHERPLANKS_WALL_SIGN.get(), properties.stacksTo(16).useBlockDescriptionPrefix()));
    public static final DeferredItem<BlockItem> OTHERPLANKS_HANGING_SIGN = ITEMS.registerItem("otherplanks_hanging_sign",
            properties -> new HangingSignItem(OccultismBlocks.OTHERPLANKS_HANGING_SIGN.get(), OccultismBlocks.OTHERPLANKS_WALL_HANGING_SIGN.get(), properties.stacksTo(16).useBlockDescriptionPrefix()));
    //Spawn Eggs
    public static final DeferredItem<Item> SPAWN_EGG_FOLIOT = ITEMS.registerItem("spawn_egg/foliot",
            SpawnEggItem::new, () -> new Properties().spawnEgg(OccultismEntities.FOLIOT_TYPE.get()));
    public static final DeferredItem<Item> SPAWN_EGG_DJINNI = ITEMS.registerItem("spawn_egg/djinni",
            SpawnEggItem::new, () -> new Properties().spawnEgg(OccultismEntities.DJINNI_TYPE.get()));
    public static final DeferredItem<Item> SPAWN_EGG_AFRIT = ITEMS.registerItem("spawn_egg/afrit",
            SpawnEggItem::new, () -> new Properties().spawnEgg(OccultismEntities.AFRIT_TYPE.get()));
    public static final DeferredItem<Item> SPAWN_EGG_AFRIT_UNBOUND = ITEMS.registerItem("spawn_egg/afrit_unbound",
            SpawnEggItem::new, () -> new Properties().spawnEgg(OccultismEntities.AFRIT_WILD_TYPE.get()));
    public static final DeferredItem<Item> SPAWN_EGG_MARID = ITEMS.registerItem("spawn_egg/marid",
            SpawnEggItem::new, () -> new Properties().spawnEgg(OccultismEntities.MARID_TYPE.get()));
    public static final DeferredItem<Item> SPAWN_EGG_MARID_UNBOUND = ITEMS.registerItem("spawn_egg/marid_unbound",
            SpawnEggItem::new, () -> new Properties().spawnEgg(OccultismEntities.MARID_UNBOUND_TYPE.get()));
    public static final DeferredItem<Item> SPAWN_EGG_WONDERING_TRADER = ITEMS.registerItem("spawn_egg/wondering_trader",
            SpawnEggItem::new, () -> new Properties().spawnEgg(OccultismEntities.WONDERING_TRADER_TYPE.get()));
    public static final DeferredItem<Item> SPAWN_EGG_POSSESSED_ENDERMITE = ITEMS.registerItem("spawn_egg/possessed_endermite",
            SpawnEggItem::new, () -> new Properties().spawnEgg(OccultismEntities.POSSESSED_ENDERMITE_TYPE.get()));
    public static final DeferredItem<Item> SPAWN_EGG_POSSESSED_SKELETON = ITEMS.registerItem("spawn_egg/possessed_skeleton",
            SpawnEggItem::new, () -> new Properties().spawnEgg(OccultismEntities.POSSESSED_SKELETON_TYPE.get()));
    public static final DeferredItem<Item> SPAWN_EGG_POSSESSED_ENDERMAN = ITEMS.registerItem("spawn_egg/possessed_enderman",
            SpawnEggItem::new, () -> new Properties().spawnEgg(OccultismEntities.POSSESSED_ENDERMAN_TYPE.get()));
    public static final DeferredItem<Item> SPAWN_EGG_POSSESSED_GHAST = ITEMS.registerItem("spawn_egg/possessed_ghast",
            SpawnEggItem::new, () -> new Properties().spawnEgg(OccultismEntities.POSSESSED_GHAST_TYPE.get()));
    public static final DeferredItem<Item> SPAWN_EGG_POSSESSED_PHANTOM = ITEMS.registerItem("spawn_egg/possessed_phantom",
            SpawnEggItem::new, () -> new Properties().spawnEgg(OccultismEntities.POSSESSED_PHANTOM_TYPE.get()));
    public static final DeferredItem<Item> SPAWN_EGG_POSSESSED_WEAK_SHULKER = ITEMS.registerItem("spawn_egg/possessed_weak_shulker",
            SpawnEggItem::new, () -> new Properties().spawnEgg(OccultismEntities.POSSESSED_WEAK_SHULKER_TYPE.get()));
    public static final DeferredItem<Item> SPAWN_EGG_POSSESSED_SHULKER = ITEMS.registerItem("spawn_egg/possessed_shulker",
            SpawnEggItem::new, () -> new Properties().spawnEgg(OccultismEntities.POSSESSED_SHULKER_TYPE.get()));
    public static final DeferredItem<Item> SPAWN_EGG_POSSESSED_ELDER_GUARDIAN = ITEMS.registerItem("spawn_egg/possessed_elder_guardian",
            SpawnEggItem::new, () -> new Properties().spawnEgg(OccultismEntities.POSSESSED_ELDER_GUARDIAN_TYPE.get()));
    public static final DeferredItem<Item> SPAWN_EGG_POSSESSED_WITCH = ITEMS.registerItem("spawn_egg/possessed_witch",
            SpawnEggItem::new, () -> new Properties().spawnEgg(OccultismEntities.POSSESSED_WITCH_TYPE.get()));
    public static final DeferredItem<Item> SPAWN_EGG_POSSESSED_BLAZE = ITEMS.registerItem("spawn_egg/possessed_blaze",
            SpawnEggItem::new, () -> new Properties().spawnEgg(OccultismEntities.POSSESSED_BLAZE_TYPE.get()));
    public static final DeferredItem<Item> SPAWN_EGG_POSSESSED_ZOMBIE_PIGLIN = ITEMS.registerItem("spawn_egg/possessed_zombie_piglin",
            SpawnEggItem::new, () -> new Properties().spawnEgg(OccultismEntities.POSSESSED_ZOMBIE_PIGLIN_TYPE.get()));
    public static final DeferredItem<Item> SPAWN_EGG_POSSESSED_BEE = ITEMS.registerItem("spawn_egg/possessed_bee",
            SpawnEggItem::new, () -> new Properties().spawnEgg(OccultismEntities.POSSESSED_BEE_TYPE.get()));
    public static final DeferredItem<Item> SPAWN_EGG_POSSESSED_GUARDIAN = ITEMS.registerItem("spawn_egg/possessed_guardian",
            SpawnEggItem::new, () -> new Properties().spawnEgg(OccultismEntities.POSSESSED_GUARDIAN_TYPE.get()));
    public static final DeferredItem<Item> SPAWN_EGG_GOAT_OF_MERCY = ITEMS.registerItem("spawn_egg/possessed_goat",
            SpawnEggItem::new, () -> new Properties().spawnEgg(OccultismEntities.GOAT_OF_MERCY_TYPE.get()));
    public static final DeferredItem<Item> SPAWN_EGG_WILD_HUNT_SKELETON = ITEMS.registerItem("spawn_egg/wild_hunt_skeleton",
            SpawnEggItem::new, () -> new Properties().spawnEgg(OccultismEntities.WILD_HUNT_SKELETON_TYPE.get()));
    public static final DeferredItem<Item> SPAWN_EGG_WILD_HUNT_WITHER_SKELETON = ITEMS.registerItem("spawn_egg/wild_hunt_wither_skeleton",
            SpawnEggItem::new, () -> new Properties().spawnEgg(OccultismEntities.WILD_HUNT_WITHER_SKELETON_TYPE.get()));
    public static final DeferredItem<Item> SPAWN_EGG_POSSESSED_WARDEN = ITEMS.registerItem("spawn_egg/possessed_warden",
            SpawnEggItem::new, () -> new Properties().spawnEgg(OccultismEntities.POSSESSED_WARDEN_TYPE.get()));
    public static final DeferredItem<Item> SPAWN_EGG_POSSESSED_HOGLIN = ITEMS.registerItem("spawn_egg/possessed_hoglin",
            SpawnEggItem::new, () -> new Properties().spawnEgg(OccultismEntities.POSSESSED_HOGLIN_TYPE.get()));
    public static final DeferredItem<Item> SPAWN_EGG_WILD_HORDE_HUSK = ITEMS.registerItem("spawn_egg/wild_horde_husk",
            SpawnEggItem::new, () -> new Properties().spawnEgg(OccultismEntities.WILD_HORDE_HUSK_TYPE.get()));
    public static final DeferredItem<Item> SPAWN_EGG_WILD_HORDE_PARCHED = ITEMS.registerItem("spawn_egg/wild_horde_parched",
            SpawnEggItem::new, () -> new Properties().spawnEgg(OccultismEntities.WILD_HORDE_PARCHED_TYPE.get()));
    public static final DeferredItem<Item> SPAWN_EGG_WILD_HORDE_DROWNED = ITEMS.registerItem("spawn_egg/wild_horde_drowned",
            SpawnEggItem::new, () -> new Properties().spawnEgg(OccultismEntities.WILD_HORDE_DROWNED_TYPE.get()));
    public static final DeferredItem<Item> SPAWN_EGG_WILD_HORDE_CREEPER = ITEMS.registerItem("spawn_egg/wild_horde_creeper",
            SpawnEggItem::new, () -> new Properties().spawnEgg(OccultismEntities.WILD_HORDE_CREEPER_TYPE.get()));
    public static final DeferredItem<Item> SPAWN_EGG_WILD_HORDE_SILVERFISH = ITEMS.registerItem("spawn_egg/wild_horde_silverfish",
            SpawnEggItem::new, () -> new Properties().spawnEgg(OccultismEntities.WILD_HORDE_SILVERFISH_TYPE.get()));
    public static final DeferredItem<Item> SPAWN_EGG_WILD_WEAK_BREEZE = ITEMS.registerItem("spawn_egg/wild_weak_breeze",
            SpawnEggItem::new, () -> new Properties().spawnEgg(OccultismEntities.POSSESSED_WEAK_BREEZE_TYPE.get()));
    public static final DeferredItem<Item> SPAWN_EGG_WILD_BREEZE = ITEMS.registerItem("spawn_egg/wild_breeze",
            SpawnEggItem::new, () -> new Properties().spawnEgg(OccultismEntities.POSSESSED_BREEZE_TYPE.get()));
    public static final DeferredItem<Item> SPAWN_EGG_WILD_STRONG_BREEZE = ITEMS.registerItem("spawn_egg/wild_strong_breeze",
            SpawnEggItem::new, () -> new Properties().spawnEgg(OccultismEntities.POSSESSED_STRONG_BREEZE_TYPE.get()));
    public static final DeferredItem<Item> SPAWN_EGG_WILD_EVOKER = ITEMS.registerItem("spawn_egg/wild_evoker",
            SpawnEggItem::new, () -> new Properties().spawnEgg(OccultismEntities.POSSESSED_EVOKER_TYPE.get()));
    public static final DeferredItem<Item> SPAWN_EGG_OTHERWORLD_BIRD = ITEMS.registerItem("spawn_egg/otherworld_bird",
            SpawnEggItem::new, () -> new Properties().spawnEgg(OccultismEntities.OTHERWORLD_BIRD_TYPE.get()));
    public static final DeferredItem<Item> SPAWN_EGG_GREEDY_FAMILIAR = ITEMS.registerItem("spawn_egg/familiar_greedy",
            SpawnEggItem::new, () -> new Properties().spawnEgg(OccultismEntities.GREEDY_FAMILIAR_TYPE.get()));
    public static final DeferredItem<Item> SPAWN_EGG_BAT_FAMILIAR = ITEMS.registerItem("spawn_egg/familiar_bat",
            SpawnEggItem::new, () -> new Properties().spawnEgg(OccultismEntities.BAT_FAMILIAR_TYPE.get()));
    public static final DeferredItem<Item> SPAWN_EGG_DEER_FAMILIAR = ITEMS.registerItem("spawn_egg/familiar_deer",
            SpawnEggItem::new, () -> new Properties().spawnEgg(OccultismEntities.DEER_FAMILIAR_TYPE.get()));
    public static final DeferredItem<Item> SPAWN_EGG_CTHULHU_FAMILIAR = ITEMS.registerItem("spawn_egg/familiar_cthulhu",
            SpawnEggItem::new, () -> new Properties().spawnEgg(OccultismEntities.CTHULHU_FAMILIAR_TYPE.get()));
    public static final DeferredItem<Item> SPAWN_EGG_DEVIL_FAMILIAR = ITEMS.registerItem("spawn_egg/familiar_devil",
            SpawnEggItem::new, () -> new Properties().spawnEgg(OccultismEntities.DEVIL_FAMILIAR_TYPE.get()));
    public static final DeferredItem<Item> SPAWN_EGG_DRAGON_FAMILIAR = ITEMS.registerItem("spawn_egg/familiar_dragon",
            SpawnEggItem::new, () -> new Properties().spawnEgg(OccultismEntities.DRAGON_FAMILIAR_TYPE.get()));
    public static final DeferredItem<Item> SPAWN_EGG_BLACKSMITH_FAMILIAR = ITEMS.registerItem("spawn_egg/familiar_blacksmith",
            SpawnEggItem::new, () -> new Properties().spawnEgg(OccultismEntities.BLACKSMITH_FAMILIAR_TYPE.get()));
    public static final DeferredItem<Item> SPAWN_EGG_GUARDIAN_FAMILIAR = ITEMS.registerItem("spawn_egg/familiar_guardian",
            SpawnEggItem::new, () -> new Properties().spawnEgg(OccultismEntities.GUARDIAN_FAMILIAR_TYPE.get()));
    public static final DeferredItem<Item> SPAWN_EGG_HEADLESS_FAMILIAR = ITEMS.registerItem("spawn_egg/familiar_headless",
            SpawnEggItem::new, () -> new Properties().spawnEgg(OccultismEntities.HEADLESS_FAMILIAR_TYPE.get()));
    public static final DeferredItem<Item> SPAWN_EGG_CHIMERA_FAMILIAR = ITEMS.registerItem("spawn_egg/familiar_chimera",
            SpawnEggItem::new, () -> new Properties().spawnEgg(OccultismEntities.CHIMERA_FAMILIAR_TYPE.get()));
    public static final DeferredItem<Item> SPAWN_EGG_GOAT_FAMILIAR = ITEMS.registerItem("spawn_egg/familiar_goat",
            SpawnEggItem::new, () -> new Properties().spawnEgg(OccultismEntities.GOAT_FAMILIAR_TYPE.get()));
    public static final DeferredItem<Item> SPAWN_EGG_SHUB_NIGGURATH_FAMILIAR = ITEMS.registerItem("spawn_egg/familiar_shub_niggurath",
            SpawnEggItem::new, () -> new Properties().spawnEgg(OccultismEntities.SHUB_NIGGURATH_FAMILIAR_TYPE.get()));
    public static final DeferredItem<Item> SPAWN_EGG_BEHOLDER_FAMILIAR = ITEMS.registerItem("spawn_egg/familiar_beholder",
            SpawnEggItem::new, () -> new Properties().spawnEgg(OccultismEntities.BEHOLDER_FAMILIAR_TYPE.get()));
    public static final DeferredItem<Item> SPAWN_EGG_FAIRY_FAMILIAR = ITEMS.registerItem("spawn_egg/familiar_fairy",
            SpawnEggItem::new, () -> new Properties().spawnEgg(OccultismEntities.FAIRY_FAMILIAR_TYPE.get()));
    public static final DeferredItem<Item> SPAWN_EGG_MUMMY_FAMILIAR = ITEMS.registerItem("spawn_egg/familiar_mummy",
            SpawnEggItem::new, () -> new Properties().spawnEgg(OccultismEntities.MUMMY_FAMILIAR_TYPE.get()));
    public static final DeferredItem<Item> SPAWN_EGG_BEAVER_FAMILIAR = ITEMS.registerItem("spawn_egg/familiar_beaver",
            SpawnEggItem::new, () -> new Properties().spawnEgg(OccultismEntities.BEAVER_FAMILIAR_TYPE.get()));
    public static final DeferredItem<Item> SPAWN_EGG_DEMONIC_WIFE = ITEMS.registerItem("spawn_egg/demonic_wife",
            SpawnEggItem::new, () -> new Properties().spawnEgg(OccultismEntities.DEMONIC_WIFE.get()));
    public static final DeferredItem<Item> SPAWN_EGG_DEMONIC_HUSBAND = ITEMS.registerItem("spawn_egg/demonic_husband",
            SpawnEggItem::new, () -> new Properties().spawnEgg(OccultismEntities.DEMONIC_HUSBAND.get()));
    public static final DeferredItem<Item> SPAWN_EGG_IESNIUM_GOLEM = ITEMS.registerItem("spawn_egg/iesnium_golem",
            SpawnEggItem::new, () -> new Properties().spawnEgg(OccultismEntities.IESNIUM_GOLEM_TYPE.get()));
    //Ritual Dummy Items
    //SUMMON
    //Crusher
    public static final DeferredItem<Item> RITUAL_DUMMY_SUMMON_FOLIOT_CRUSHER = ITEMS.registerItem("ritual_dummy/summon_foliot_crusher", DummyTooltipItem::new, () -> new Properties());
    public static final DeferredItem<Item> RITUAL_DUMMY_SUMMON_DJINNI_CRUSHER = ITEMS.registerItem("ritual_dummy/summon_djinni_crusher", DummyTooltipItem::new, () -> new Properties());
    public static final DeferredItem<Item> RITUAL_DUMMY_SUMMON_AFRIT_CRUSHER = ITEMS.registerItem("ritual_dummy/summon_afrit_crusher", DummyTooltipItem::new, () -> new Properties());
    public static final DeferredItem<Item> RITUAL_DUMMY_SUMMON_MARID_CRUSHER = ITEMS.registerItem("ritual_dummy/summon_marid_crusher", DummyTooltipItem::new, () -> new Properties());
    //Smelter
    public static final DeferredItem<Item> RITUAL_DUMMY_SUMMON_FOLIOT_SMELTER = ITEMS.registerItem("ritual_dummy/summon_foliot_smelter", DummyTooltipItem::new, () -> new Properties());
    public static final DeferredItem<Item> RITUAL_DUMMY_SUMMON_DJINNI_SMELTER = ITEMS.registerItem("ritual_dummy/summon_djinni_smelter", DummyTooltipItem::new, () -> new Properties());
    public static final DeferredItem<Item> RITUAL_DUMMY_SUMMON_AFRIT_SMELTER = ITEMS.registerItem("ritual_dummy/summon_afrit_smelter", DummyTooltipItem::new, () -> new Properties());
    public static final DeferredItem<Item> RITUAL_DUMMY_SUMMON_MARID_SMELTER = ITEMS.registerItem("ritual_dummy/summon_marid_smelter", DummyTooltipItem::new, () -> new Properties());
    //Crystallizer
    public static final DeferredItem<Item> RITUAL_DUMMY_SUMMON_FOLIOT_CRYSTALLIZER = ITEMS.registerItem("ritual_dummy/summon_foliot_crystallizer", DummyTooltipItem::new, () -> new Properties());
    public static final DeferredItem<Item> RITUAL_DUMMY_SUMMON_DJINNI_CRYSTALLIZER = ITEMS.registerItem("ritual_dummy/summon_djinni_crystallizer", DummyTooltipItem::new, () -> new Properties());
    public static final DeferredItem<Item> RITUAL_DUMMY_SUMMON_AFRIT_CRYSTALLIZER = ITEMS.registerItem("ritual_dummy/summon_afrit_crystallizer", DummyTooltipItem::new, () -> new Properties());
    public static final DeferredItem<Item> RITUAL_DUMMY_SUMMON_MARID_CRYSTALLIZER = ITEMS.registerItem("ritual_dummy/summon_marid_crystallizer", DummyTooltipItem::new, () -> new Properties());
    //Partner
    public static final DeferredItem<Item> RITUAL_DUMMY_SUMMON_DEMONIC_WIFE = ITEMS.registerItem("ritual_dummy/summon_demonic_wife", DummyTooltipItem::new, () -> new Properties());
    public static final DeferredItem<Item> RITUAL_DUMMY_SUMMON_DEMONIC_HUSBAND = ITEMS.registerItem("ritual_dummy/summon_demonic_husband", DummyTooltipItem::new, () -> new Properties());
    //One tier worker
    public static final DeferredItem<Item> RITUAL_DUMMY_SUMMON_FOLIOT_LUMBERJACK = ITEMS.registerItem("ritual_dummy/summon_foliot_lumberjack", DummyTooltipItem::new, () -> new Properties());
    public static final DeferredItem<Item> RITUAL_DUMMY_SUMMON_FOLIOT_FARMER = ITEMS.registerItem("ritual_dummy/summon_foliot_farmer", DummyTooltipItem::new, () -> new Properties());
    public static final DeferredItem<Item> RITUAL_DUMMY_SUMMON_FOLIOT_OTHERSTONE_TRADER = ITEMS.registerItem("ritual_dummy/summon_foliot_otherstone_trader", DummyTooltipItem::new, () -> new Properties());
    public static final DeferredItem<Item> RITUAL_DUMMY_SUMMON_FOLIOT_OTHERROCK_TRADER = ITEMS.registerItem("ritual_dummy/summon_foliot_otherrock_trader", DummyTooltipItem::new, () -> new Properties());
    public static final DeferredItem<Item> RITUAL_DUMMY_SUMMON_FOLIOT_SAPLING_TRADER = ITEMS.registerItem("ritual_dummy/summon_foliot_sapling_trader", DummyTooltipItem::new, () -> new Properties());
    public static final DeferredItem<Item> RITUAL_DUMMY_SUMMON_FOLIOT_TRANSPORT_ITEMS = ITEMS.registerItem("ritual_dummy/summon_foliot_transport_items", DummyTooltipItem::new, () -> new Properties());
    public static final DeferredItem<Item> RITUAL_DUMMY_SUMMON_FOLIOT_CLEANER = ITEMS.registerItem("ritual_dummy/summon_foliot_cleaner", DummyTooltipItem::new, () -> new Properties());
    public static final DeferredItem<Item> RITUAL_DUMMY_SUMMON_DJINNI_MANAGE_MACHINE = ITEMS.registerItem("ritual_dummy/summon_djinni_manage_machine", DummyTooltipItem::new, () -> new Properties());
    public static final DeferredItem<Item> RITUAL_DUMMY_SUMMON_DJINNI_GAMBLER = ITEMS.registerItem("ritual_dummy/summon_djinni_gambler", DummyTooltipItem::new, () -> new Properties());
    public static final DeferredItem<Item> RITUAL_DUMMY_SUMMON_WONDERING_TRADER = ITEMS.registerItem("ritual_dummy/summon_wondering_trader", DummyTooltipItem::new, () -> new Properties());
    public static final DeferredItem<Item> RITUAL_DUMMY_SUMMON_DJINNI_DAY_TIME = ITEMS.registerItem("ritual_dummy/summon_djinni_day_time", DummyTooltipItem::new, () -> new Properties());
    public static final DeferredItem<Item> RITUAL_DUMMY_SUMMON_DJINNI_NIGHT_TIME = ITEMS.registerItem("ritual_dummy/summon_djinni_night_time", DummyTooltipItem::new, () -> new Properties());
    public static final DeferredItem<Item> RITUAL_DUMMY_SUMMON_DJINNI_CLEAR_WEATHER = ITEMS.registerItem("ritual_dummy/summon_djinni_clear_weather", DummyTooltipItem::new, () -> new Properties());
    public static final DeferredItem<Item> RITUAL_DUMMY_SUMMON_AFRIT_RAIN_WEATHER = ITEMS.registerItem("ritual_dummy/summon_afrit_rain_weather", DummyTooltipItem::new, () -> new Properties());
    public static final DeferredItem<Item> RITUAL_DUMMY_SUMMON_AFRIT_THUNDER_WEATHER = ITEMS.registerItem("ritual_dummy/summon_afrit_thunder_weather", DummyTooltipItem::new, () -> new Properties());
    //Unbound
    public static final DeferredItem<Item> RITUAL_DUMMY_SUMMON_UNBOUND_AFRIT = ITEMS.registerItem("ritual_dummy/summon_unbound_afrit", DummyTooltipItem::new, () -> new Properties());
    public static final DeferredItem<Item> RITUAL_DUMMY_SUMMON_UNBOUND_MARID = ITEMS.registerItem("ritual_dummy/summon_unbound_marid", DummyTooltipItem::new, () -> new Properties());
    //POSSESS
    //Familiar
    public static final DeferredItem<Item> RITUAL_DUMMY_FAMILIAR_BEAVER = ITEMS.registerItem("ritual_dummy/familiar_beaver", DummyTooltipItem::new, () -> new Properties());
    public static final DeferredItem<Item> RITUAL_DUMMY_FAMILIAR_BLACKSMITH = ITEMS.registerItem("ritual_dummy/familiar_blacksmith", DummyTooltipItem::new, () -> new Properties());
    public static final DeferredItem<Item> RITUAL_DUMMY_FAMILIAR_DEER = ITEMS.registerItem("ritual_dummy/familiar_deer", DummyTooltipItem::new, () -> new Properties());
    public static final DeferredItem<Item> RITUAL_DUMMY_FAMILIAR_GREEDY = ITEMS.registerItem("ritual_dummy/familiar_greedy", DummyTooltipItem::new, () -> new Properties());
    public static final DeferredItem<Item> RITUAL_DUMMY_FAMILIAR_PARROT = ITEMS.registerItem("ritual_dummy/familiar_parrot", DummyTooltipItem::new, () -> new Properties());
    public static final DeferredItem<Item> RITUAL_DUMMY_POSSESS_UNBOUND_PARROT = ITEMS.registerItem("ritual_dummy/possess_unbound_parrot", DummyTooltipItem::new, () -> new Properties());
    public static final DeferredItem<Item> RITUAL_DUMMY_FAMILIAR_BAT = ITEMS.registerItem("ritual_dummy/familiar_bat", DummyTooltipItem::new, () -> new Properties());
    public static final DeferredItem<Item> RITUAL_DUMMY_FAMILIAR_BEHOLDER = ITEMS.registerItem("ritual_dummy/familiar_beholder", DummyTooltipItem::new, () -> new Properties());
    public static final DeferredItem<Item> RITUAL_DUMMY_FAMILIAR_CHIMERA = ITEMS.registerItem("ritual_dummy/familiar_chimera", DummyTooltipItem::new, () -> new Properties());
    public static final DeferredItem<Item> RITUAL_DUMMY_FAMILIAR_CTHULHU = ITEMS.registerItem("ritual_dummy/familiar_cthulhu", DummyTooltipItem::new, () -> new Properties());
    public static final DeferredItem<Item> RITUAL_DUMMY_FAMILIAR_DEVIL = ITEMS.registerItem("ritual_dummy/familiar_devil", DummyTooltipItem::new, () -> new Properties());
    public static final DeferredItem<Item> RITUAL_DUMMY_FAMILIAR_DRAGON = ITEMS.registerItem("ritual_dummy/familiar_dragon", DummyTooltipItem::new, () -> new Properties());
    public static final DeferredItem<Item> RITUAL_DUMMY_FAMILIAR_FAIRY = ITEMS.registerItem("ritual_dummy/familiar_fairy", DummyTooltipItem::new, () -> new Properties());
    public static final DeferredItem<Item> RITUAL_DUMMY_FAMILIAR_HEADLESS = ITEMS.registerItem("ritual_dummy/familiar_headless", DummyTooltipItem::new, () -> new Properties());
    public static final DeferredItem<Item> RITUAL_DUMMY_FAMILIAR_MUMMY = ITEMS.registerItem("ritual_dummy/familiar_mummy", DummyTooltipItem::new, () -> new Properties());
    public static final DeferredItem<Item> RITUAL_DUMMY_FAMILIAR_OTHERWORLD_BIRD = ITEMS.registerItem("ritual_dummy/familiar_otherworld_bird", DummyTooltipItem::new, () -> new Properties());
    public static final DeferredItem<Item> RITUAL_DUMMY_POSSESS_UNBOUND_OTHERWORLD_BIRD = ITEMS.registerItem("ritual_dummy/possess_unbound_otherworld_bird", DummyTooltipItem::new, () -> new Properties());
    public static final DeferredItem<Item> RITUAL_DUMMY_FAMILIAR_GUARDIAN = ITEMS.registerItem("ritual_dummy/familiar_guardian", DummyTooltipItem::new, () -> new Properties());
    public static final DeferredItem<Item> RITUAL_DUMMY_POSSESS_IESNIUM_GOLEM = ITEMS.registerItem("ritual_dummy/possess_iesnium_golem", DummyTooltipItem::new, () -> new Properties());
    //Possessed
    public static final DeferredItem<Item> RITUAL_DUMMY_POSSESS_ENDERMITE = ITEMS.registerItem("ritual_dummy/possess_endermite", DummyTooltipItem::new, () -> new Properties());
    public static final DeferredItem<Item> RITUAL_DUMMY_POSSESS_PHANTOM = ITEMS.registerItem("ritual_dummy/possess_phantom", DummyTooltipItem::new, () -> new Properties());
    public static final DeferredItem<Item> RITUAL_DUMMY_POSSESS_SKELETON = ITEMS.registerItem("ritual_dummy/possess_skeleton", DummyTooltipItem::new, () -> new Properties());
    public static final DeferredItem<Item> RITUAL_DUMMY_POSSESS_WITCH = ITEMS.registerItem("ritual_dummy/possess_witch", DummyTooltipItem::new, () -> new Properties());
    public static final DeferredItem<Item> RITUAL_DUMMY_POSSESS_ENDERMAN = ITEMS.registerItem("ritual_dummy/possess_enderman", DummyTooltipItem::new, () -> new Properties());
    public static final DeferredItem<Item> RITUAL_DUMMY_POSSESS_BEE = ITEMS.registerItem("ritual_dummy/possess_bee", DummyTooltipItem::new, () -> new Properties());
    public static final DeferredItem<Item> RITUAL_DUMMY_POSSESS_GHAST = ITEMS.registerItem("ritual_dummy/possess_ghast", DummyTooltipItem::new, () -> new Properties());
    public static final DeferredItem<Item> RITUAL_DUMMY_POSSESS_WEAK_SHULKER = ITEMS.registerItem("ritual_dummy/possess_weak_shulker", DummyTooltipItem::new, () -> new Properties());
    public static final DeferredItem<Item> RITUAL_DUMMY_POSSESS_BLAZE = ITEMS.registerItem("ritual_dummy/possess_blaze", DummyTooltipItem::new, () -> new Properties());
    public static final DeferredItem<Item> RITUAL_DUMMY_POSSESS_ZOMBIE_PIGLIN = ITEMS.registerItem("ritual_dummy/possess_zombie_piglin", DummyTooltipItem::new, () -> new Properties());
    public static final DeferredItem<Item> RITUAL_DUMMY_POSSESS_GUARDIAN = ITEMS.registerItem("ritual_dummy/possess_guardian", DummyTooltipItem::new, () -> new Properties());
    public static final DeferredItem<Item> RITUAL_DUMMY_POSSESS_WARDEN = ITEMS.registerItem("ritual_dummy/possess_warden", DummyTooltipItem::new, () -> new Properties());
    public static final DeferredItem<Item> RITUAL_DUMMY_POSSESS_ELDER_GUARDIAN = ITEMS.registerItem("ritual_dummy/possess_elder_guardian", DummyTooltipItem::new, () -> new Properties());
    public static final DeferredItem<Item> RITUAL_DUMMY_POSSESS_HOGLIN = ITEMS.registerItem("ritual_dummy/possess_hoglin", DummyTooltipItem::new, () -> new Properties());
    public static final DeferredItem<Item> RITUAL_DUMMY_POSSESS_SHULKER = ITEMS.registerItem("ritual_dummy/possess_shulker", DummyTooltipItem::new, () -> new Properties());
    public static final DeferredItem<Item> RITUAL_DUMMY_POSSESS_GOAT = ITEMS.registerItem("ritual_dummy/possess_goat", DummyTooltipItem::new, () -> new Properties());
    //Random
    public static final DeferredItem<Item> RITUAL_DUMMY_POSSESS_RANDOM_ANIMAL_COMMON = ITEMS.registerItem("ritual_dummy/possess_random_animal_common", DummyTooltipItem::new, () -> new Properties());
    public static final DeferredItem<Item> RITUAL_DUMMY_POSSESS_RANDOM_ANIMAL_WATER = ITEMS.registerItem("ritual_dummy/possess_random_animal_water", DummyTooltipItem::new, () -> new Properties());
    public static final DeferredItem<Item> RITUAL_DUMMY_POSSESS_RANDOM_ANIMAL_SMALL = ITEMS.registerItem("ritual_dummy/possess_random_animal_small", DummyTooltipItem::new, () -> new Properties());
    public static final DeferredItem<Item> RITUAL_DUMMY_POSSESS_RANDOM_ANIMAL_RIDEABLE = ITEMS.registerItem("ritual_dummy/possess_random_animal_rideable", DummyTooltipItem::new, () -> new Properties());
    public static final DeferredItem<Item> RITUAL_DUMMY_POSSESS_RANDOM_ANIMAL_SPECIAL = ITEMS.registerItem("ritual_dummy/possess_random_animal_special", DummyTooltipItem::new, () -> new Properties());
    public static final DeferredItem<Item> RITUAL_DUMMY_POSSESS_VILLAGER = ITEMS.registerItem("ritual_dummy/possess_villager", DummyTooltipItem::new, () -> new Properties());
    // CRAFT
    //Tools
    public static final DeferredItem<Item> RITUAL_DUMMY_CRAFT_INFUSED_LENSES = ITEMS.registerItem("ritual_dummy/craft_infused_lenses", DummyTooltipItem::new, () -> new Properties());
    public static final DeferredItem<Item> RITUAL_DUMMY_CRAFT_INFUSED_PICKAXE = ITEMS.registerItem("ritual_dummy/craft_infused_pickaxe", DummyTooltipItem::new, () -> new Properties());
    public static final DeferredItem<Item> RITUAL_DUMMY_CRAFT_SATCHEL = ITEMS.registerItem("ritual_dummy/craft_satchel", DummyTooltipItem::new, () -> new Properties());
    public static final DeferredItem<Item> RITUAL_DUMMY_CRAFT_ENDER_SATCHEL = ITEMS.registerItem("ritual_dummy/craft_ender_satchel", DummyTooltipItem::new, () -> new Properties());
    public static final DeferredItem<Item> RITUAL_DUMMY_CRAFT_RITUAL_SATCHEL_T1 = ITEMS.registerItem("ritual_dummy/craft_ritual_satchel_t1", DummyTooltipItem::new, () -> new Properties());
    public static final DeferredItem<Item> RITUAL_DUMMY_CRAFT_RITUAL_SATCHEL_T2 = ITEMS.registerItem("ritual_dummy/craft_ritual_satchel_t2", DummyTooltipItem::new, () -> new Properties());
    public static final DeferredItem<Item> RITUAL_DUMMY_CRAFT_UPGRADE_RITUAL_SATCHEL = ITEMS.registerItem("ritual_dummy/craft_upgrade_ritual_satchel", DummyTooltipItem::new, () -> new Properties());
    public static final DeferredItem<Item> RITUAL_DUMMY_CRAFT_KNOWLEDGE_TABLET = ITEMS.registerItem("ritual_dummy/craft_knowledge_tablet", DummyTooltipItem::new, () -> new Properties());
    public static final DeferredItem<Item> RITUAL_DUMMY_CRAFT_VITALITY_COMPASS = ITEMS.registerItem("ritual_dummy/craft_vitality_compass", DummyTooltipItem::new, () -> new Properties());
    public static final DeferredItem<Item> RITUAL_DUMMY_CRAFT_FRAGILE_SOUL_GEM = ITEMS.registerItem("ritual_dummy/craft_fragile_soul_gem", DummyTooltipItem::new, () -> new Properties());
    public static final DeferredItem<Item> RITUAL_DUMMY_CRAFT_SOUL_GEM = ITEMS.registerItem("ritual_dummy/craft_soul_gem", DummyTooltipItem::new, () -> new Properties());
    public static final DeferredItem<Item> RITUAL_DUMMY_CRAFT_FAMILIAR_RING = ITEMS.registerItem("ritual_dummy/craft_familiar_ring", DummyTooltipItem::new, () -> new Properties());
    public static final DeferredItem<Item> RITUAL_DUMMY_CRAFT_IESNIUM_BUTCHER_KNIFE = ITEMS.registerItem("ritual_dummy/craft_iesnium_butcher_knife", DummyTooltipItem::new, () -> new Properties());
    public static final DeferredItem<Item> RITUAL_DUMMY_CRAFT_TRUE_SIGHT_STAFF = ITEMS.registerItem("ritual_dummy/craft_true_sight_staff", DummyTooltipItem::new, () -> new Properties());
    //Miners
    public static final DeferredItem<Item> RITUAL_DUMMY_CRAFT_DIMENSIONAL_EXTRACTOR = ITEMS.registerItem("ritual_dummy/craft_dimensional_extractor", DummyTooltipItem::new, () -> new Properties());
    public static final DeferredItem<Item> RITUAL_DUMMY_CRAFT_DIMENSIONAL_MINESHAFT = ITEMS.registerItem("ritual_dummy/craft_dimensional_mineshaft", DummyTooltipItem::new, () -> new Properties());
    public static final DeferredItem<Item> RITUAL_DUMMY_CRAFT_DIMENSIONAL_BATTLEFIELD = ITEMS.registerItem("ritual_dummy/craft_dimensional_battlefield", DummyTooltipItem::new, () -> new Properties());
    public static final DeferredItem<Item> RITUAL_DUMMY_CRAFT_MINER_FOLIOT_UNSPECIALIZED = ITEMS.registerItem("ritual_dummy/craft_miner_foliot_unspecialized", DummyTooltipItem::new, () -> new Properties());
    public static final DeferredItem<Item> RITUAL_DUMMY_CRAFT_MINER_DJINNI_ORES = ITEMS.registerItem("ritual_dummy/craft_miner_djinni_ores", DummyTooltipItem::new, () -> new Properties());
    public static final DeferredItem<Item> RITUAL_DUMMY_CRAFT_MINER_AFRIT_DEEPS = ITEMS.registerItem("ritual_dummy/craft_miner_afrit_deeps", DummyTooltipItem::new, () -> new Properties());
    public static final DeferredItem<Item> RITUAL_DUMMY_CRAFT_MINER_MARID_MASTER = ITEMS.registerItem("ritual_dummy/craft_miner_marid_master", DummyTooltipItem::new, () -> new Properties());
    //Storage
    public static final DeferredItem<Item> RITUAL_DUMMY_CRAFT_STORAGE_CONTROLLER_BASE = ITEMS.registerItem("ritual_dummy/craft_storage_controller_base", DummyTooltipItem::new, () -> new Properties());
    public static final DeferredItem<Item> RITUAL_DUMMY_CRAFT_STORAGE_CONTROLLER_BASE_DARK = ITEMS.registerItem("ritual_dummy/craft_storage_controller_base_dark", DummyTooltipItem::new, () -> new Properties());
    public static final DeferredItem<Item> RITUAL_DUMMY_CRAFT_DIMENSIONAL_MATRIX = ITEMS.registerItem("ritual_dummy/craft_dimensional_matrix", DummyTooltipItem::new, () -> new Properties());
    public static final DeferredItem<Item> RITUAL_DUMMY_CRAFT_STABILIZER_TIER1 = ITEMS.registerItem("ritual_dummy/craft_stabilizer_tier1", DummyTooltipItem::new, () -> new Properties());
    public static final DeferredItem<Item> RITUAL_DUMMY_CRAFT_STABILIZER_TIER2 = ITEMS.registerItem("ritual_dummy/craft_stabilizer_tier2", DummyTooltipItem::new, () -> new Properties());
    public static final DeferredItem<Item> RITUAL_DUMMY_CRAFT_STABILIZER_TIER3 = ITEMS.registerItem("ritual_dummy/craft_stabilizer_tier3", DummyTooltipItem::new, () -> new Properties());
    public static final DeferredItem<Item> RITUAL_DUMMY_CRAFT_STABILIZER_TIER4 = ITEMS.registerItem("ritual_dummy/craft_stabilizer_tier4", DummyTooltipItem::new, () -> new Properties());
    public static final DeferredItem<Item> RITUAL_DUMMY_CRAFT_STABILIZER_TIER1_DARK = ITEMS.registerItem("ritual_dummy/craft_stabilizer_tier1_dark", DummyTooltipItem::new, () -> new Properties());
    public static final DeferredItem<Item> RITUAL_DUMMY_CRAFT_STABILIZER_TIER2_DARK = ITEMS.registerItem("ritual_dummy/craft_stabilizer_tier2_dark", DummyTooltipItem::new, () -> new Properties());
    public static final DeferredItem<Item> RITUAL_DUMMY_CRAFT_STABILIZER_TIER3_DARK = ITEMS.registerItem("ritual_dummy/craft_stabilizer_tier3_dark", DummyTooltipItem::new, () -> new Properties());
    public static final DeferredItem<Item> RITUAL_DUMMY_CRAFT_STABILIZER_TIER4_DARK = ITEMS.registerItem("ritual_dummy/craft_stabilizer_tier4_dark", DummyTooltipItem::new, () -> new Properties());
    public static final DeferredItem<Item> RITUAL_DUMMY_CRAFT_STABLE_WORMHOLE = ITEMS.registerItem("ritual_dummy/craft_stable_wormhole", DummyTooltipItem::new, () -> new Properties());
    public static final DeferredItem<Item> RITUAL_DUMMY_CRAFT_STABLE_WORMHOLE_DARK = ITEMS.registerItem("ritual_dummy/craft_stable_wormhole_dark", DummyTooltipItem::new, () -> new Properties());
    public static final DeferredItem<Item> RITUAL_DUMMY_CRAFT_STORAGE_REMOTE = ITEMS.registerItem("ritual_dummy/craft_storage_remote", DummyTooltipItem::new, () -> new Properties());
    //Materials
    public static final DeferredItem<Item> RITUAL_DUMMY_CRAFT_RESEARCH_FRAGMENT_DUST = ITEMS.registerItem("ritual_dummy/craft_research_fragment_dust", DummyTooltipItem::new, () -> new Properties());
    public static final DeferredItem<Item> RITUAL_DUMMY_CRAFT_NATURE_PASTE = ITEMS.registerItem("ritual_dummy/craft_nature_paste", DummyTooltipItem::new, () -> new Properties());
    public static final DeferredItem<Item> RITUAL_DUMMY_CRAFT_GRAY_PASTE = ITEMS.registerItem("ritual_dummy/craft_gray_paste", DummyTooltipItem::new, () -> new Properties());
    public static final DeferredItem<Item> RITUAL_DUMMY_CRAFT_WITHERITE_DUST = ITEMS.registerItem("ritual_dummy/craft_witherite_dust", DummyTooltipItem::new, () -> new Properties());
    public static final DeferredItem<Item> RITUAL_DUMMY_CRAFT_DRAGONYST_DUST = ITEMS.registerItem("ritual_dummy/craft_dragonyst_dust", DummyTooltipItem::new, () -> new Properties());
    //Blocks
    public static final DeferredItem<Item> RITUAL_DUMMY_CRAFT_ENTITY_WORMHOLE = ITEMS.registerItem("ritual_dummy/craft_entity_wormhole", DummyTooltipItem::new, () -> new Properties());
    public static final DeferredItem<Item> RITUAL_DUMMY_CRAFT_ENTITY_WORMHOLE_DARK = ITEMS.registerItem("ritual_dummy/craft_entity_wormhole_dark", DummyTooltipItem::new, () -> new Properties());
    public static final DeferredItem<Item> RITUAL_DUMMY_CRAFT_SPIRIT_GRINDSTONE = ITEMS.registerItem("ritual_dummy/craft_spirit_grindstone", DummyTooltipItem::new, () -> new Properties());
    public static final DeferredItem<Item> RITUAL_DUMMY_CRAFT_IESNIUM_SACRIFICIAL_BOWL = ITEMS.registerItem("ritual_dummy/craft_iesnium_sacrificial_bowl", DummyTooltipItem::new, () -> new Properties());
    public static final DeferredItem<Item> RITUAL_DUMMY_CRAFT_DARK_IESNIUM_SACRIFICIAL_BOWL = ITEMS.registerItem("ritual_dummy/craft_dark_iesnium_sacrificial_bowl", DummyTooltipItem::new, () -> new Properties());
    public static final DeferredItem<Item> RITUAL_DUMMY_CRAFT_IESNIUM_ANVIL = ITEMS.registerItem("ritual_dummy/craft_iesnium_anvil", DummyTooltipItem::new, () -> new Properties());
    //Repair
    public static final DeferredItem<Item> RITUAL_DUMMY_REPAIR_CHALKS = ITEMS.registerItem("ritual_dummy/repair_chalks", DummyTooltipItem::new, () -> new Properties());
    public static final DeferredItem<Item> RITUAL_DUMMY_REPAIR_TOOLS = ITEMS.registerItem("ritual_dummy/repair_tools", DummyTooltipItem::new, () -> new Properties());
    public static final DeferredItem<Item> RITUAL_DUMMY_REPAIR_ARMORS = ITEMS.registerItem("ritual_dummy/repair_armors", DummyTooltipItem::new, () -> new Properties());
    public static final DeferredItem<Item> RITUAL_DUMMY_REPAIR_MINERS = ITEMS.registerItem("ritual_dummy/repair_miners", DummyTooltipItem::new, () -> new Properties());
    //MISC
    //Resurrect
    public static final DeferredItem<Item> RITUAL_DUMMY_RESURRECT_FAMILIAR = ITEMS.registerItem("ritual_dummy/resurrect_familiar", DummyTooltipItem::new, () -> new Properties());
    public static final DeferredItem<Item> RITUAL_DUMMY_RESURRECT_ALLAY = ITEMS.registerItem("ritual_dummy/resurrect_allay", DummyTooltipItem::new, () -> new Properties());
    public static final DeferredItem<Item> RITUAL_DUMMY_RESURRECT_MOB = ITEMS.registerItem("ritual_dummy/resurrect_mob", DummyTooltipItem::new, () -> new Properties());
    //Wild (group possess)
    public static final DeferredItem<Item> RITUAL_DUMMY_WILD_HUNT = ITEMS.registerItem("ritual_dummy/wild_hunt", DummyTooltipItem::new, () -> new Properties());
    public static final DeferredItem<Item> RITUAL_DUMMY_WILD_CREEPER = ITEMS.registerItem("ritual_dummy/wild_creeper", DummyTooltipItem::new, () -> new Properties());
    public static final DeferredItem<Item> RITUAL_DUMMY_WILD_DROWNED = ITEMS.registerItem("ritual_dummy/wild_drowned", DummyTooltipItem::new, () -> new Properties());
    public static final DeferredItem<Item> RITUAL_DUMMY_WILD_HUSK = ITEMS.registerItem("ritual_dummy/wild_husk", DummyTooltipItem::new, () -> new Properties());
    public static final DeferredItem<Item> RITUAL_DUMMY_WILD_PARCHED = ITEMS.registerItem("ritual_dummy/wild_parched", DummyTooltipItem::new, () -> new Properties());
    public static final DeferredItem<Item> RITUAL_DUMMY_WILD_SILVERFISH = ITEMS.registerItem("ritual_dummy/wild_silverfish", DummyTooltipItem::new, () -> new Properties());
    public static final DeferredItem<Item> RITUAL_DUMMY_WILD_WEAK_BREEZE = ITEMS.registerItem("ritual_dummy/wild_weak_breeze", DummyTooltipItem::new, () -> new Properties());
    public static final DeferredItem<Item> RITUAL_DUMMY_WILD_BREEZE = ITEMS.registerItem("ritual_dummy/wild_breeze", DummyTooltipItem::new, () -> new Properties());
    public static final DeferredItem<Item> RITUAL_DUMMY_WILD_STRONG_BREEZE = ITEMS.registerItem("ritual_dummy/wild_strong_breeze", DummyTooltipItem::new, () -> new Properties());
    public static final DeferredItem<Item> RITUAL_DUMMY_WILD_ILLAGER = ITEMS.registerItem("ritual_dummy/wild_horde_illager", DummyTooltipItem::new, () -> new Properties());
    public static final DeferredItem<Item> RITUAL_DUMMY_WILD_RANDOM_ANIMAL_COMMON = ITEMS.registerItem("ritual_dummy/wild_random_animal_common", DummyTooltipItem::new, () -> new Properties());
    public static final DeferredItem<Item> RITUAL_DUMMY_WILD_RANDOM_ANIMAL_WATER = ITEMS.registerItem("ritual_dummy/wild_random_animal_water", DummyTooltipItem::new, () -> new Properties());
    public static final DeferredItem<Item> RITUAL_DUMMY_WILD_RANDOM_ANIMAL_SMALL = ITEMS.registerItem("ritual_dummy/wild_random_animal_small", DummyTooltipItem::new, () -> new Properties());
    public static final DeferredItem<Item> RITUAL_DUMMY_WILD_RANDOM_ANIMAL_RIDEABLE = ITEMS.registerItem("ritual_dummy/wild_random_animal_rideable", DummyTooltipItem::new, () -> new Properties());
    public static final DeferredItem<Item> RITUAL_DUMMY_WILD_RANDOM_ANIMAL_SPECIAL = ITEMS.registerItem("ritual_dummy/wild_random_animal_special", DummyTooltipItem::new, () -> new Properties());
    public static final DeferredItem<Item> RITUAL_DUMMY_WILD_VILLAGER = ITEMS.registerItem("ritual_dummy/wild_villager", DummyTooltipItem::new, () -> new Properties());
    //Forge (cursed craft) misc in ID
    public static final DeferredItem<Item> RITUAL_DUMMY_FORGE_BEE_NEST = ITEMS.registerItem("ritual_dummy/misc_bee_nest", DummyTooltipItem::new, () -> new Properties());
    public static final DeferredItem<Item> RITUAL_DUMMY_FORGE_BELL = ITEMS.registerItem("ritual_dummy/misc_bell", DummyTooltipItem::new, () -> new Properties());
    public static final DeferredItem<Item> RITUAL_DUMMY_FORGE_BUDDING_AMETHYST = ITEMS.registerItem("ritual_dummy/misc_budding_amethyst", DummyTooltipItem::new, () -> new Properties());
    public static final DeferredItem<Item> RITUAL_DUMMY_FORGE_REINFORCED_DEEPSLATE = ITEMS.registerItem("ritual_dummy/misc_reinforced_deepslate", DummyTooltipItem::new, () -> new Properties());
    public static final DeferredItem<Item> RITUAL_DUMMY_FORGE_WILD_TRIM = ITEMS.registerItem("ritual_dummy/misc_wild_trim", DummyTooltipItem::new, () -> new Properties());
    public static final DeferredItem<Item> RITUAL_DUMMY_FORGE_COPPER_HORSE_ARMOR = ITEMS.registerItem("ritual_dummy/misc_copper_horse_armor", DummyTooltipItem::new, () -> new Properties());
    public static final DeferredItem<Item> RITUAL_DUMMY_FORGE_IRON_HORSE_ARMOR = ITEMS.registerItem("ritual_dummy/misc_iron_horse_armor", DummyTooltipItem::new, () -> new Properties());
    public static final DeferredItem<Item> RITUAL_DUMMY_FORGE_GOLDEN_HORSE_ARMOR = ITEMS.registerItem("ritual_dummy/misc_golden_horse_armor", DummyTooltipItem::new, () -> new Properties());
    public static final DeferredItem<Item> RITUAL_DUMMY_FORGE_DIAMOND_HORSE_ARMOR = ITEMS.registerItem("ritual_dummy/misc_diamond_horse_armor", DummyTooltipItem::new, () -> new Properties());
    public static final DeferredItem<Item> RITUAL_DUMMY_FORGE_COPPER_NAUTILUS_ARMOR = ITEMS.registerItem("ritual_dummy/misc_copper_nautilus_armor", DummyTooltipItem::new, () -> new Properties());
    public static final DeferredItem<Item> RITUAL_DUMMY_FORGE_IRON_NAUTILUS_ARMOR = ITEMS.registerItem("ritual_dummy/misc_iron_nautilus_armor", DummyTooltipItem::new, () -> new Properties());
    public static final DeferredItem<Item> RITUAL_DUMMY_FORGE_GOLDEN_NAUTILUS_ARMOR = ITEMS.registerItem("ritual_dummy/misc_golden_nautilus_armor", DummyTooltipItem::new, () -> new Properties());
    public static final DeferredItem<Item> RITUAL_DUMMY_FORGE_DIAMOND_NAUTILUS_ARMOR = ITEMS.registerItem("ritual_dummy/misc_diamond_nautilus_armor", DummyTooltipItem::new, () -> new Properties());
    public static final DeferredItem<Item> RITUAL_DUMMY_FORGE_ELDRITCH_CHALICE = ITEMS.registerItem("ritual_dummy/misc_eldritch_chalice", DummyTooltipItem::new, () -> new Properties());
    public static final DeferredItem<Item> RITUAL_DUMMY_FORGE_CELESTIAL_CHALICE = ITEMS.registerItem("ritual_dummy/misc_celestial_chalice", DummyTooltipItem::new, () -> new Properties());
    public static final DeferredItem<Item> RITUAL_DUMMY_FORGE_CHALK_RAINBOW = ITEMS.registerItem("ritual_dummy/misc_chalk_rainbow", DummyTooltipItem::new, () -> new Properties());
    public static final DeferredItem<Item> RITUAL_DUMMY_FORGE_CHALK_VOID = ITEMS.registerItem("ritual_dummy/misc_chalk_void", DummyTooltipItem::new, () -> new Properties());
    public static final DeferredItem<Item> RITUAL_DUMMY_FORGE_TRINITY_GEM = ITEMS.registerItem("ritual_dummy/misc_trinity_gem", DummyTooltipItem::new, () -> new Properties());
    public static final DeferredItem<Item> RITUAL_DUMMY_FORGE_STABILIZED_STORAGE = ITEMS.registerItem("ritual_dummy/misc_stabilized_storage", DummyTooltipItem::new, () -> new Properties());
    public static final DeferredItem<Item> RITUAL_DUMMY_FORGE_STABILIZED_STORAGE_DARK = ITEMS.registerItem("ritual_dummy/misc_stabilized_storage_dark", DummyTooltipItem::new, () -> new Properties());
    public static final DeferredItem<Item> RITUAL_DUMMY_FORGE_MINER_ANCIENT_ELDRITCH = ITEMS.registerItem("ritual_dummy/misc_miner_ancient_eldritch", DummyTooltipItem::new, () -> new Properties());
    public static final DeferredItem<Item> RITUAL_DUMMY_CRAFT_STABILIZER_TIER5 = ITEMS.registerItem("ritual_dummy/misc_stabilizer_tier5", DummyTooltipItem::new, () -> new Properties());
    public static final DeferredItem<Item> RITUAL_DUMMY_CRAFT_STABILIZER_TIER5_DARK = ITEMS.registerItem("ritual_dummy/misc_stabilizer_tier5_dark", DummyTooltipItem::new, () -> new Properties());
    // CUSTOM
    public static final DeferredItem<Item> RITUAL_DUMMY_CUSTOM_SUMMON = ITEMS.registerItem("ritual_dummy/custom_ritual_summon", DummyTooltipItem::new, () -> new Properties());
    public static final DeferredItem<Item> RITUAL_DUMMY_CUSTOM_POSSESS = ITEMS.registerItem("ritual_dummy/custom_ritual_possess", DummyTooltipItem::new, () -> new Properties());
    public static final DeferredItem<Item> RITUAL_DUMMY_CUSTOM_CRAFT = ITEMS.registerItem("ritual_dummy/custom_ritual_craft", DummyTooltipItem::new, () -> new Properties());
    public static final DeferredItem<Item> RITUAL_DUMMY_CUSTOM_MISC = ITEMS.registerItem("ritual_dummy/custom_ritual_misc", DummyTooltipItem::new, () -> new Properties());
    public static final DeferredItem<FlameAutomationItem> FLAME_AUTOMATION = ITEMS.registerItem(
            "flame_of_automation", FlameAutomationItem::new, () -> new Properties().component(OccultismDataComponents.SPIRIT_NAME, TextUtil.SPIRIT_NAME_NOT_YET_KNOWN));
    //DEBUG
    public static final DeferredItem<Item> DEBUG_WAND = ITEMS.registerItem("debug_wand",
            DebugWandItem::new, () -> new Properties().stacksTo(1));
    public static final DeferredItem<Item> DEBUG_FOLIOT_LUMBERJACK = ITEMS.registerItem("debug_foliot_lumberjack",
            SummonFoliotLumberjackItem::new, () -> new Properties().stacksTo(1));
    public static final DeferredItem<Item> DEBUG_FOLIOT_FARMER = ITEMS.registerItem("debug_foliot_farmer",
            SummonFoliotFarmerItem::new, () -> new Properties().stacksTo(1));
    public static final DeferredItem<Item> DEBUG_FOLIOT_TRANSPORT_ITEMS =
            ITEMS.registerItem("debug_foliot_transport_items",
                    SummonFoliotTransportItemsItem::new, () -> new Properties().stacksTo(1));
    public static final DeferredItem<Item> DEBUG_FOLIOT_CLEANER =
            ITEMS.registerItem("debug_foliot_cleaner",
                    SummonFoliotCleanerItem::new, () -> new Properties().stacksTo(1));
    public static final DeferredItem<Item> DEBUG_FOLIOT_TRADER_ITEM = ITEMS.registerItem("debug_foliot_trader",
            SummonFoliotTraderItem::new, () -> new Properties().stacksTo(1));
    public static final DeferredItem<Item> DEBUG_DJINNI_MANAGE_MACHINE = ITEMS.registerItem("debug_djinni_manage_machine",
            SummonDjinniManageMachineItem::new, () -> new Properties().stacksTo(1));
    public static final DeferredItem<Item> DEBUG_DJINNI_TEST = ITEMS.registerItem("debug_djinni_test",
            SummonDjinniTest::new, () -> new Properties().stacksTo(1));
    public static final DeferredItem<MinerSpiritItem> MINER_DEBUG_UNSPECIALIZED =
            ITEMS.registerItem("miner_debug_unspecialized",
                    properties -> new MinerSpiritItem(properties
                            .component(OccultismDataComponents.SPIRIT_NAME, TextUtil.SPIRIT_NAME_NOT_YET_KNOWN)
                            .durability(10000), () -> 100, () -> 10, () -> 1, () -> 10000));
    //Placeholders
    public static final DeferredItem<Item> DICTIONARY_OF_SPIRITS_ICON =
            ITEMS.registerItem("dictionary_of_spirits_icon", Item::new, () -> new Properties());
    public static final DeferredItem<Item> PENTACLE_SUMMON = ITEMS.registerItem("pentacle_summon",
            Item::new, () -> new Properties());
    public static final DeferredItem<Item> PENTACLE_POSSESS = ITEMS.registerItem("pentacle_possess",
            Item::new, () -> new Properties());
    public static final DeferredItem<Item> PENTACLE_CRAFT = ITEMS.registerItem("pentacle_craft",
            Item::new, () -> new Properties());
    public static final DeferredItem<Item> PENTACLE_MISC = ITEMS.registerItem("pentacle_misc",
            Item::new, () -> new Properties());
    public static final DeferredItem<Item> WORMHOLE_PORTAL = ITEMS.registerItem("entity_wormhole_portal",
            Item::new, () -> new Properties());
    public static final DeferredItem<Item> ADVANCEMENT_ICON =
            ITEMS.registerItem("advancement_icon", Item::new, () -> new Properties());
    public static final DeferredItem<Item> REPAIR_ICON =
            ITEMS.registerItem("repair_icon", Item::new, () -> new Properties());
    public static final DeferredItem<Item> RESURRECT_ICON =
            ITEMS.registerItem("resurrect_icon", Item::new, () -> new Properties());
    public static final DeferredItem<Item> MYSTERIOUS_EGG_ICON =
            ITEMS.registerItem("mysterious_egg_icon", Item::new, () -> new Properties());
    //JEI Dummy Items
    public static final DeferredItem<DummyTooltipItem> JEI_DUMMY_NONE = ITEMS.registerItem(
            "jei_dummy/none", DummyTooltipItem::new, () -> new Properties());
    public static final DeferredItem<DummyTooltipItem> JEI_DUMMY_REQUIRE_SACRIFICE = ITEMS.registerItem(
            "jei_dummy/require_sacrifice", DummyTooltipItem::new, () -> new Properties());
    public static final DeferredItem<DummyTooltipItem> JEI_DUMMY_REQUIRE_ITEM_USE = ITEMS.registerItem(
            "jei_dummy/require_item_use", DummyTooltipItem::new, () -> new Properties());
    private static final ResourceKey<EquipmentAsset> OTHERWORLD_GOGGLES_EQUIPMENT_ASSET = ResourceKey.create(
            EquipmentAssets.ROOT_ID, Identifier.fromNamespaceAndPath(Occultism.MODID, "otherworld_goggles"));
    public static final DeferredItem<OtherworldGogglesItem> OTHERWORLD_GOGGLES = ITEMS.registerItem("otherworld_goggles",
            OtherworldGogglesItem::new, () -> new Properties().stacksTo(1)
                    .component(DataComponents.EQUIPPABLE, Equippable.builder(EquipmentSlot.HEAD)
                            .setAsset(OTHERWORLD_GOGGLES_EQUIPMENT_ASSET)
                            .setEquipSound(SoundEvents.ARMOR_EQUIP_LEATHER)
                            .build()));

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
                || item == WORMHOLE_PORTAL.asItem()
        )
            return true;

        if (item instanceof BlockItem blockItem) {
            return blockItem.getBlock() == OccultismBlocks.LIGHTED_AIR.get();
        }
        return false;
    }

    public static boolean laterCreativeModTab(Item item) {
        return item.toString().contains("debug")
                || item instanceof DummyTooltipItem
                || item instanceof SpawnEggItem
                || item instanceof StableWormholeBlockItem
                || item == FLAME_AUTOMATION.get()
                || item == SPIRIT_TORCH.get()
                || item == OTHERPLANKS_SIGN.get()
                || item == OTHERPLANKS_HANGING_SIGN.get();
    }

    public static boolean shouldPregenerateSpiritName(Item item) {
        return item == BOOK_OF_BINDING_BOUND_FOLIOT.get()
                || item == BOOK_OF_BINDING_BOUND_DJINNI.get()
                || item == BOOK_OF_BINDING_BOUND_AFRIT.get()
                || item == BOOK_OF_BINDING_BOUND_MARID.get()
                || item == BOOK_OF_CALLING_FOLIOT_LUMBERJACK.get()
                || item == BOOK_OF_CALLING_FOLIOT_FARMER.get()
                || item == BOOK_OF_CALLING_FOLIOT_TRANSPORT_ITEMS.get()
                || item == BOOK_OF_CALLING_FOLIOT_CLEANER.get()
                || item == BOOK_OF_CALLING_DJINNI_MANAGE_MACHINE.get()
                || item == DIMENSIONAL_MATRIX.get()
                || item == ENDER_SATCHEL.get()
                || item == SATCHEL.get()
                || item == RITUAL_SATCHEL_T1.get()
                || item == RITUAL_SATCHEL_T2.get()
                || item == KNOWLEDGE_TABLET.get()
                || item == STORAGE_REMOTE.get()
                || item == FAMILIAR_RING.get()
                || item == VITALITY_COMPASS.get()
                || item == INFUSED_PICKAXE.get()
                || item == IESNIUM_BUTCHER_KNIFE.get()
                || item == MAGIC_LAMP_EMPTY.get()
                || item == MINER_FOLIOT_UNSPECIALIZED.get()
                || item == MINER_DJINNI_ORES.get()
                || item == MINER_AFRIT_DEEPS.get()
                || item == MINER_MARID_MASTER.get()
                || item == MINER_ANCIENT_ELDRITCH.get()
                || item == MINER_DEBUG_UNSPECIALIZED.get();
    }
}
