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
import com.github.klikli_dev.occultism.common.item.ChalkItem;
import com.github.klikli_dev.occultism.common.item.DebugWandItem;
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

    //Tools
    public static final RegistryObject<Item> CHALK_WHITE = ITEMS.register("chalk_white",
            () -> new ChalkItem(defaultProperties().setNoRepair().maxDamage(128),
                    OccultismBlocks.CHALK_GLYPH_WHITE));
    public static final RegistryObject<Item> CHALK_GOLD = ITEMS.register("chalk_gold",
            () -> new ChalkItem(defaultProperties().setNoRepair().maxDamage(128),
                    OccultismBlocks.CHALK_GLYPH_GOLD));
    public static final RegistryObject<Item> CHALK_PURPLE = ITEMS.register("chalk_purple",
            () -> new ChalkItem(defaultProperties().setNoRepair().maxDamage(128),
                    OccultismBlocks.CHALK_GLYPH_PURPLE));
    public static final RegistryObject<Item> CHALK_RED = ITEMS.register("chalk_red",
            () -> new ChalkItem(defaultProperties().setNoRepair().maxDamage(128),
                    OccultismBlocks.CHALK_GLYPH_RED));
    //endregion Fields

    //region Static Methods
    public static Item.Properties defaultProperties() {
        return new Item.Properties().group(Occultism.ITEM_GROUP);
    }
    //endregion Static Methods
}
