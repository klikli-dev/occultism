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
import com.github.klikli_dev.occultism.common.item.*;
import com.github.klikli_dev.occultism.common.item.spirit.BookOfBindingBoundItem;
import com.github.klikli_dev.occultism.common.item.spirit.BookOfBindingItem;
import com.github.klikli_dev.occultism.common.item.spirit.BookOfCallingItem;
import com.github.klikli_dev.occultism.common.item.storage.StableWormholeBlockItem;
import com.github.klikli_dev.occultism.common.item.storage.StorageRemoteItem;
import net.minecraft.item.Item;
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
            () -> new SummonFoliotManageMachineItem(defaultProperties().maxStackSize(1)));
    public static final RegistryObject<Item> DEBUG_FOLIOT_TRADER_ITEM = ITEMS.register("debug_foliot_trader",
            () -> new SummonFoliotTraderItem(defaultProperties().maxStackSize(1)));

    //Resources
    //Components
    public static final RegistryObject<Item> DIMENSIONAL_MATRIX = ITEMS.register("dimensional_matrix",
            () -> new Item(new Item.Properties()));

    //Tools
    public static final RegistryObject<Item> STORAGE_REMOTE = ITEMS.register("storage_remote",
            () -> new StorageRemoteItem(defaultProperties().maxStackSize(1)));

    public static final RegistryObject<Item> CHALK_WHITE = ITEMS.register("chalk_white",
            () -> new ChalkItem(defaultProperties().setNoRepair().maxDamage(128), OccultismBlocks.CHALK_GLYPH_WHITE));
    public static final RegistryObject<Item> CHALK_GOLD = ITEMS.register("chalk_gold",
            () -> new ChalkItem(defaultProperties().setNoRepair().maxDamage(128), OccultismBlocks.CHALK_GLYPH_GOLD));
    public static final RegistryObject<Item> CHALK_PURPLE = ITEMS.register("chalk_purple",
            () -> new ChalkItem(defaultProperties().setNoRepair().maxDamage(128), OccultismBlocks.CHALK_GLYPH_PURPLE));
    public static final RegistryObject<Item> CHALK_RED = ITEMS.register("chalk_red",
            () -> new ChalkItem(defaultProperties().setNoRepair().maxDamage(128), OccultismBlocks.CHALK_GLYPH_RED));

    //Books of Binding
    public static final RegistryObject<BookOfBindingItem> BOOK_OF_BINDING_FOLIOT = ITEMS.register(
            "book_of_binding_foliot", () -> new BookOfBindingItem(defaultProperties().maxStackSize(1)));
    public static final RegistryObject<BookOfBindingBoundItem> BOOK_OF_BINDING_BOUND_FOLIOT = ITEMS.register(
            "book_of_binding_bound_foliot", () -> new BookOfBindingBoundItem(defaultProperties().maxStackSize(1)));
    public static final RegistryObject<BookOfBindingItem> BOOK_OF_BINDING_DJINNI = ITEMS.register(
            "book_of_binding_djinni", () -> new BookOfBindingItem(defaultProperties().maxStackSize(1)));
    public static final RegistryObject<BookOfBindingBoundItem> BOOK_OF_BINDING_BOUND_DJINNI = ITEMS.register(
            "book_of_binding_bound_djinni", () -> new BookOfBindingBoundItem(defaultProperties().maxStackSize(1)));
    //Books of Calling
    //Foliot
    public static final RegistryObject<BookOfCallingItem> BOOK_OF_CALLING_FOLIOT_LUMBERJACK = ITEMS.register(
            "book_of_calling_foliot_lumberjack",
            () -> new BookOfCallingItem(defaultProperties().maxStackSize(1), "book_of_calling_foliot"));
    public static final RegistryObject<BookOfCallingItem> BOOK_OF_CALLING_FOLIOT_MANAGE_MACHINE = ITEMS.register(
            "book_of_calling_foliot_manage_machine",
            () -> new BookOfCallingItem(defaultProperties().maxStackSize(1), "book_of_calling_foliot"));
    //Djinn

    //Machines
    public static final RegistryObject<StableWormholeBlockItem> STABLE_WORMHOLE = ITEMS.register("stable_wormhole",
            () -> new StableWormholeBlockItem(OccultismBlocks.STABLE_WORMHOLE.get(), defaultProperties()));


    //endregion Fields

    //region Static Methods
    public static Item.Properties defaultProperties() {
        return new Item.Properties().group(Occultism.ITEM_GROUP);
    }
    //endregion Static Methods
}
