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
import com.github.klikli_dev.occultism.common.item.crops.ItemDatura;
import com.github.klikli_dev.occultism.common.item.crops.ItemOccultismSeed;
import com.github.klikli_dev.occultism.common.item.tool.ItemButcherKnife;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.util.ResourceLocation;

import java.util.HashMap;
import java.util.Map;

public class ItemRegistry {
    //region Fields
    public static final Map<Item, String[]> ORE_DICTIONARY = new HashMap<>();

    //Debug and placeholder items
    public static final Item DEBUG_WAND = new ItemDebugWand();
    public static final Item PENTACLE = new ItemGenericComponent("pentacle");
    public static final BlockItem SPIRIT_FIRE = new ItemGenericBlock(BlockRegistry.SPIRIT_FIRE, "spirit_fire_item");

    //Resources
    public static final BlockItem OTHERWORLD_LOG_NATURAL = new ItemBlockOtherworldLog(
            BlockRegistry.OTHERWORLD_LOG_NATURAL);
    public static final BlockItem OTHERWORLD_LOG = new ItemBlockOtherworldLog(BlockRegistry.OTHERWORLD_LOG);
    public static final BlockItem OTHERWORLD_SAPLING_NATURAL = (BlockItem) new ItemBlockOtherworldSapling(
            BlockRegistry.OTHERWORLD_SAPLING_NATURAL).setTranslationKey(
            Occultism.MODID + ".otherworld_sapling_natural");
    public static final BlockItem OTHERWORLD_SAPLING = new ItemBlockOtherworldSapling(BlockRegistry.OTHERWORLD_SAPLING);

    //Components
    public static final BlockItem BLOCK_STABLE_WORMHOLE = new ItemBlockStableWormhole(BlockRegistry.STABLE_WORMHOLE);
    public static final Item DIMENSIONAL_MATRIX = new ItemGenericComponent("dimensional_matrix");
    public static final Item SPIRIT_ATTUNED_GEM = new ItemGenericComponent("spirit_attuned_gem", "gemSpiritAttuned");
    public static final Item OTHERWORLD_ASHES = new ItemGenericComponent("otherworld_ashes", "dustAsh");
    public static final Item BURNT_OTHERSTONE = new ItemGenericComponent("burnt_otherstone", "dustOtherstone");
    public static final Item TALLOW = new ItemGenericComponent("tallow", "tallow");
    public static final Item CHALK_WHITE_IMPURE = new ItemGenericComponent("chalk_white_impure");
    public static final Item CHALK_RED_IMPURE = new ItemGenericComponent("chalk_red_impure");
    public static final Item CHALK_GOLD_IMPURE = new ItemGenericComponent("chalk_gold_impure");
    public static final Item CHALK_PURPLE_IMPURE = new ItemGenericComponent("chalk_purple_impure");

    //Seeds
    public static final ItemOccultismSeed DATURA_SEEDS = new ItemOccultismSeed("datura_seeds",
            BlockRegistry.CROP_DATURA, Blocks.FARMLAND);

    //Crops
    public static final ItemFood DATURA = new ItemDatura();

    //Tools
    public static final Item DIVINATION_ROD = new ItemDivinationRod();
    public static final Item BRUSH = new ItemBrush();
    public static final Item STORAGE_REMOTE = new ItemStorageRemote();
    public static final Item BUTCHER_KNIFE = new ItemButcherKnife();
    public static final Item CHALK_WHITE = new ItemChalk("chalk_white");
    public static final Item CHALK_RED = new ItemChalk("chalk_red");
    public static final Item CHALK_GOLD = new ItemChalk("chalk_gold");
    public static final Item CHALK_PURPLE = new ItemChalk("chalk_purple");

    //Spirit Books
    //Foliot
    public static final Item BOOK_OF_BINDING_FOLIOT = new ItemBookOfBinding("book_of_binding_foliot");
    public static final Item BOOK_OF_BINDING_ACTIVE_FOLIOT = new ItemBookOfBindingActive(
            "book_of_binding_active_foliot");

    public static final Item BOOK_OF_CALLING_LUMBERJACK_ACTIVE_FOLIOT = new ItemBookOfCallingActive(
            "book_of_calling_lumberjack_active_foliot", "bookOfCallingActiveFoliot");
    public static final Item BOOK_OF_CALLING_MANAGE_MACHINE_ACTIVE_FOLIOT = new ItemBookOfCallingActive(
            "book_of_calling_manage_machine_active_foliot", "bookOfCallingActiveFoliot");
    //Djinn
    public static final Item BOOK_OF_BINDING_DJINNI = new ItemBookOfBinding("book_of_binding_djinni");
    public static final Item BOOK_OF_BINDING_ACTIVE_DJINNI = new ItemBookOfBindingActive(
            "book_of_binding_active_djinni");

    //endregion Fields

    //region Static Methods

    /**
     * Registers the item with Forge.
     * Automatically sets the unlocalized name and creative tab.
     *
     * @param item               the item to register.
     * @param name               the name to register the item under.
     * @param oreDictionaryNames the list of ore dictionary names.
     * @return the registered item.
     */
    public static <T extends Item> T registerItem(T item, String name, String... oreDictionaryNames) {
        ResourceLocation location = new ResourceLocation(Occultism.MODID, name);
        item.setRegistryName(location);
        item.setTranslationKey(location.toString().replace(":", "."));
        item.setCreativeTab(Occultism.CREATIVE_TAB);

        //prepare ore dictionary. Registration is after item registration event.
        ORE_DICTIONARY.put(item, oreDictionaryNames);

        return item;
    }
    //endregion Static Methods
}
