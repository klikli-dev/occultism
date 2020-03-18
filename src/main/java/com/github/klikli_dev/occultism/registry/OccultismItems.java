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
import com.github.klikli_dev.occultism.common.item.debug.DebugWandItem;
import com.github.klikli_dev.occultism.common.item.debug.SummonFoliotLumberjackItem;
import com.github.klikli_dev.occultism.common.item.debug.SummonDjinniManageMachineItem;
import com.github.klikli_dev.occultism.common.item.debug.SummonFoliotTraderItem;
import com.github.klikli_dev.occultism.common.item.otherworld.OtherworldBlockItem;
import com.github.klikli_dev.occultism.common.item.spirit.*;
import com.github.klikli_dev.occultism.common.item.storage.DimensionalMatrixItem;
import com.github.klikli_dev.occultism.common.item.storage.StableWormholeBlockItem;
import com.github.klikli_dev.occultism.common.item.storage.StorageRemoteItem;
import com.github.klikli_dev.occultism.common.item.tool.BrushItem;
import com.github.klikli_dev.occultism.common.item.tool.ButcherKnifeItem;
import com.github.klikli_dev.occultism.common.item.tool.ChalkItem;
import com.github.klikli_dev.occultism.common.item.tool.DivinationRodItem;
import net.minecraft.item.BlockItem;
import net.minecraft.item.BlockNamedItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemTier;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class OccultismItems {

    //region Fields
    public static final DeferredRegister<Item> ITEMS = new DeferredRegister<>(ForgeRegistries.ITEMS, Occultism.MODID);

    //Debug and placeholder items
    public static final RegistryObject<Item> PENTACLE = ITEMS.register("pentacle",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> DEBUG_WAND = ITEMS.register("debug_wand",
            () -> new DebugWandItem(defaultProperties().maxStackSize(1)));
    public static final RegistryObject<Item> DEBUG_FOLIOT_LUMBERJACK = ITEMS.register("debug_foliot_lumberjack",
            () -> new SummonFoliotLumberjackItem(defaultProperties().maxStackSize(1)));
    public static final RegistryObject<Item> DEBUG_FOLIOT_MANAGE_MACHINE = ITEMS.register("debug_foliot_manage_machine",
            () -> new SummonDjinniManageMachineItem(defaultProperties().maxStackSize(1)));
    public static final RegistryObject<Item> DEBUG_FOLIOT_TRADER_ITEM = ITEMS.register("debug_foliot_trader",
            () -> new SummonFoliotTraderItem(defaultProperties().maxStackSize(1)));

    public static final RegistryObject<BlockItem> SPIRIT_FIRE = ITEMS.register("spirit_fire", () -> new BlockItem(OccultismBlocks.SPIRIT_FIRE.get(), defaultProperties()));

    //Resources
    public static final RegistryObject<OtherworldBlockItem> OTHERWORLD_SAPLING_NATURAL = ITEMS.register("otherworld_sapling_natural",
            () -> new OtherworldBlockItem(OccultismBlocks.OTHERWORLD_SAPLING_NATURAL.get(), defaultProperties()));

    //Components
    public static final RegistryObject<DimensionalMatrixItem> DIMENSIONAL_MATRIX = ITEMS.register("dimensional_matrix",
            () -> new DimensionalMatrixItem(defaultProperties()));
    public static final RegistryObject<Item> SPIRIT_ATTUNED_GEM = ITEMS.register("spirit_attuned_gem",
            () -> new Item(defaultProperties()));
    public static final RegistryObject<Item> OTHERWORLD_ASHES = ITEMS.register("otherworld_ashes",
            () -> new Item(defaultProperties()));
    public static final RegistryObject<Item> BURNT_OTHERSTONE = ITEMS.register("burnt_otherstone",
            () -> new Item(defaultProperties()));
    public static final RegistryObject<Item> TALLOW = ITEMS.register("tallow",
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
    public static final RegistryObject<Item> PLATINUM_INGOT = ITEMS.register("platinum_ingot",
            () -> new Item(defaultProperties()));

    public static final RegistryObject<Item> COPPER_NUGGET = ITEMS.register("copper_nugget",
            () -> new Item(defaultProperties()));
    public static final RegistryObject<Item> SILVER_NUGGET = ITEMS.register("silver_nugget",
            () -> new Item(defaultProperties()));
    public static final RegistryObject<Item> PLATINUM_NUGGET = ITEMS.register("platinum_nugget",
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
    public static final RegistryObject<Item> PLATINUM_DUST = ITEMS.register("platinum_dust",
            () -> new Item(defaultProperties()));

    public static final RegistryObject<Item> CRUSHED_END_STONE = ITEMS.register("crushed_end_stone",
            () -> new Item(defaultProperties()));

    //Tools
    public static final RegistryObject<Item> STORAGE_REMOTE = ITEMS.register("storage_remote",
            () -> new StorageRemoteItem(defaultProperties().maxStackSize(1)));

    public static final RegistryObject<Item> CHALK_WHITE = ITEMS.register("chalk_white",
            () -> new ChalkItem(defaultProperties().setNoRepair().maxDamage(128),
                    () -> OccultismBlocks.CHALK_GLYPH_WHITE.get()));
    public static final RegistryObject<Item> CHALK_GOLD = ITEMS.register("chalk_gold",
            () -> new ChalkItem(defaultProperties().setNoRepair().maxDamage(128),
                    () -> OccultismBlocks.CHALK_GLYPH_GOLD.get()));
    public static final RegistryObject<Item> CHALK_PURPLE = ITEMS.register("chalk_purple",
            () -> new ChalkItem(defaultProperties().setNoRepair().maxDamage(128),
                    () -> OccultismBlocks.CHALK_GLYPH_PURPLE.get()));
    public static final RegistryObject<Item> CHALK_RED = ITEMS.register("chalk_red",
            () -> new ChalkItem(defaultProperties().setNoRepair().maxDamage(128),
                    () -> OccultismBlocks.CHALK_GLYPH_RED.get()));

    public static final RegistryObject<DivinationRodItem> DIVINATION_ROD = ITEMS.register("divination_rod",
            () -> new DivinationRodItem(defaultProperties().maxStackSize(1)));
    public static final RegistryObject<BrushItem> BRUSH = ITEMS.register("brush",
            () -> new BrushItem(defaultProperties().maxStackSize(1)));
    public static final RegistryObject<ButcherKnifeItem> BUTCHER_KNIFE = ITEMS.register("butcher_knife",
            () -> new ButcherKnifeItem(ItemTier.IRON, 3, -2.4F, defaultProperties()));

    //Books of Binding
    public static final RegistryObject<BookOfBindingItem> BOOK_OF_BINDING_FOLIOT = ITEMS.register(
            "book_of_binding_foliot", () -> new BookOfBindingItem(defaultProperties().maxStackSize(1)));
    public static final RegistryObject<BookOfBindingBoundItem> BOOK_OF_BINDING_BOUND_FOLIOT = ITEMS.register(
            "book_of_binding_bound_foliot", () -> new BookOfBindingBoundItem(defaultProperties().maxStackSize(1)));
    public static final RegistryObject<BookOfBindingItem> BOOK_OF_BINDING_DJINNI = ITEMS.register(
            "book_of_binding_djinni", () -> new BookOfBindingItem(defaultProperties().maxStackSize(1)));
    public static final RegistryObject<BookOfBindingBoundItem> BOOK_OF_BINDING_BOUND_DJINNI = ITEMS.register(
            "book_of_binding_bound_djinni", () -> new BookOfBindingBoundItem(defaultProperties().maxStackSize(1)));
    public static final RegistryObject<BookOfBindingItem> BOOK_OF_BINDING_AFRIT = ITEMS.register(
            "book_of_binding_afrit", () -> new BookOfBindingItem(defaultProperties().maxStackSize(1)));
    public static final RegistryObject<BookOfBindingBoundItem> BOOK_OF_BINDING_BOUND_AFRIT = ITEMS.register(
            "book_of_binding_bound_afrit", () -> new BookOfBindingBoundItem(defaultProperties().maxStackSize(1)));
    public static final RegistryObject<BookOfBindingItem> BOOK_OF_BINDING_MARID= ITEMS.register(
            "book_of_binding_marid", () -> new BookOfBindingItem(defaultProperties().maxStackSize(1)));
    public static final RegistryObject<BookOfBindingBoundItem> BOOK_OF_BINDING_BOUND_MARID = ITEMS.register(
            "book_of_binding_bound_marid", () -> new BookOfBindingBoundItem(defaultProperties().maxStackSize(1)));
    //Books of Calling
    //Foliot
    public static final RegistryObject<BookOfCallingLumberjackItem> BOOK_OF_CALLING_FOLIOT_LUMBERJACK = ITEMS.register(
            "book_of_calling_foliot_lumberjack",
            () -> new BookOfCallingLumberjackItem(defaultProperties().maxStackSize(1),
                    TranslationKeys.BOOK_OF_CALLING_GENERIC + "_foliot"));
    public static final RegistryObject<BookOfCallingManageMachineItem> BOOK_OF_CALLING_DJINNI_MANAGE_MACHINE = ITEMS.register(
            "book_of_calling_djinni_manage_machine",
            () -> new BookOfCallingManageMachineItem(defaultProperties().maxStackSize(1),
                    TranslationKeys.BOOK_OF_CALLING_GENERIC + "_djinni"));
    //Djinn

    //Machines
    public static final RegistryObject<StableWormholeBlockItem> STABLE_WORMHOLE = ITEMS.register("stable_wormhole",
            () -> new StableWormholeBlockItem(OccultismBlocks.STABLE_WORMHOLE.get(), defaultProperties()));

    //Crops
    public static final RegistryObject<Item> DATURA_SEEDS =
            ITEMS.register("datura_seeds", () -> new BlockNamedItem(OccultismBlocks.DATURA.get(), defaultProperties()));

    //Foods
    public static final RegistryObject<Item> DATURA = ITEMS.register("datura",
            () -> new Item(defaultProperties().food(OccultismFoods.DATURA.get())));

    //endregion Fields

    //region Static Methods
    public static Item.Properties defaultProperties() {
        return new Item.Properties().group(Occultism.ITEM_GROUP);
    }
    //endregion Static Methods
}
