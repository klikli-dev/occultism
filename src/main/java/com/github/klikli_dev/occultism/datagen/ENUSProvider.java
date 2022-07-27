/*
 * MIT License
 *
 * Copyright 2021 vemerion
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

package com.github.klikli_dev.occultism.datagen;

import com.github.klikli_dev.occultism.Occultism;
import com.github.klikli_dev.occultism.registry.OccultismItems;
import com.klikli_dev.modonomicon.Modonomicon;
import com.klikli_dev.modonomicon.api.ModonomiconAPI;
import net.minecraft.data.DataGenerator;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.minecraftforge.common.data.LanguageProvider;

public class ENUSProvider extends LanguageProvider {

    public ENUSProvider(DataGenerator gen) {
        super(gen, Occultism.MODID, "en_us");
    }

    @Override
    protected void addTranslations() {
        this.addAdvancements();
        this.addItems();
        this.addBook();

        //TODO: pentacle names Util.makeDescriptionId("multiblock", pentacle.getId()))
    }

    private void addItems(){
        //Notepad++ magic:
        //"item.occultism.(.*)": "(.*)"
        //this.addItem\(OccultismItems.\U\1\E, "\2"\);
        this.addItem(OccultismItems.PENTACLE, "Pentacle");
        this.addItem(OccultismItems.DEBUG_WAND, "Debug Wand");
        this.addItem(OccultismItems.DEBUG_FOLIOT_LUMBERJACK, "Summon Debug Foliot Lumberjack");
        this.addItem(OccultismItems.DEBUG_FOLIOT_TRANSPORT_ITEMS, "Summon Debug Foliot Transporter");
        this.addItem(OccultismItems.DEBUG_FOLIOT_CLEANER, "Summon Debug Foliot Janitor");
        this.addItem(OccultismItems.DEBUG_FOLIOT_TRADER_ITEM, "Summon Debug Foliot Trader");
        this.addItem(OccultismItems.DEBUG_DJINNI_MANAGE_MACHINE, "Summon Debug Djinni Manage Machine");
        this.addItem(OccultismItems.DEBUG_DJINNI_TEST, "Summon Debug Djinni Test");

        this.addItem(OccultismItems.CHALK_GOLD, "Golden Chalk");
        this.addItem(OccultismItems.CHALK_PURPLE, "Purple Chalk");
        this.addItem(OccultismItems.CHALK_RED, "Red Chalk");
        this.addItem(OccultismItems.CHALK_WHITE, "White Chalk");
        this.addItem(OccultismItems.CHALK_GOLD_IMPURE, "Impure Golden Chalk");
        this.addItem(OccultismItems.CHALK_PURPLE_IMPURE, "Impure Purple Chalk");
        this.addItem(OccultismItems.CHALK_RED_IMPURE, "Impure Red Chalk");
        this.addItem(OccultismItems.CHALK_WHITE_IMPURE, "Impure White Chalk");
        this.addItem(OccultismItems.BRUSH, "Chalk Brush");
        this.addItem(OccultismItems.AFRIT_ESSENCE, "Afrit Essence");
        this.addItem(OccultismItems.PURIFIED_INK, "Purified Ink");
        this.addItem(OccultismItems.BOOK_OF_BINDING_FOLIOT, "Book of Binding: Foliot");
        this.addItem(OccultismItems.BOOK_OF_BINDING_BOUND_FOLIOT, "Book of Binding: Foliot (Bound)");
        this.addItem(OccultismItems.BOOK_OF_BINDING_DJINNI, "Book of Binding: Djinni");
        this.addItem(OccultismItems.BOOK_OF_BINDING_BOUND_DJINNI, "Book of Binding: Djinni (Bound)");
        this.addItem(OccultismItems.BOOK_OF_BINDING_AFRIT, "Book of Binding: Afrit");
        this.addItem(OccultismItems.BOOK_OF_BINDING_BOUND_AFRIT, "Book of Binding: Afrit (Bound)");
        this.addItem(OccultismItems.BOOK_OF_BINDING_MARID, "Book of Binding: Marid");
        this.addItem(OccultismItems.BOOK_OF_BINDING_BOUND_MARID, "Book of Binding: Marid (Bound)");
        this.addItem(OccultismItems.BOOK_OF_CALLING_FOLIOT_LUMBERJACK, "Book of Calling: Foliot Lumberjack");
        this.addItem(OccultismItems.BOOK_OF_CALLING_FOLIOT_TRANSPORT_ITEMS, "Book of Calling: Foliot Transporter");
        this.addItem(OccultismItems.BOOK_OF_CALLING_FOLIOT_CLEANER, "Book of Calling: Foliot Janitor");
        this.addItem(OccultismItems.BOOK_OF_CALLING_DJINNI_MANAGE_MACHINE, "Book of Calling: Djinni Machine Operator");
        this.addItem(OccultismItems.STORAGE_REMOTE, "Storage Accessor");
        this.addItem(OccultismItems.STORAGE_REMOTE_INERT, "Inert Storage Accessor");
        this.addItem(OccultismItems.DIMENSIONAL_MATRIX, "Dimensional Crystal Matrix");
        this.addItem(OccultismItems.DIVINATION_ROD, "Divination Rod");
        this.addItem(OccultismItems.DATURA_SEEDS, "Demon's Dream Seeds");
        this.addItem(OccultismItems.DATURA, "Demon's Dream Fruit");
        this.addItem(OccultismItems.SPIRIT_ATTUNED_GEM, "Spirit Attuned Gem");
        this.addItem(OccultismItems.OTHERWORLD_SAPLING_NATURAL, "Unstable Otherworld Sapling");
        this.addItem(OccultismItems.OTHERWORLD_ASHES, "Otherworld Ashes");
        this.addItem(OccultismItems.BURNT_OTHERSTONE, "Burnt Otherstone");
        this.addItem(OccultismItems.SPIRIT_FIRE, "Spiritfire");
        this.addItem(OccultismItems.BUTCHER_KNIFE, "Butcher Knife");
        this.addItem(OccultismItems.TALLOW, "Tallow");
        this.addItem(OccultismItems.OTHERSTONE_FRAME, "Otherstone Frame");
        this.addItem(OccultismItems.OTHERSTONE_TABLET, "Otherstone Tablet");
        this.addItem(OccultismItems.WORMHOLE_FRAME, "Wormhole Frame");
        this.addItem(OccultismItems.IRON_DUST, "Iron Dust");
        this.addItem(OccultismItems.OBSIDIAN_DUST, "Obsidian Dust");
        this.addItem(OccultismItems.CRUSHED_END_STONE, "Crushed End Stone");
        this.addItem(OccultismItems.GOLD_DUST, "Gold Dust");
        this.addItem(OccultismItems.COPPER_DUST, "Copper Dust");
        this.addItem(OccultismItems.SILVER_DUST, "Silver Dust");
        this.addItem(OccultismItems.IESNIUM_DUST, "Iesnium Dust");
        this.addItem(OccultismItems.RAW_SILVER, "Raw Silver");
        this.addItem(OccultismItems.RAW_IESNIUM, "Raw Iesnium");
        this.addItem(OccultismItems.SILVER_INGOT, "Silver Ingot");
        this.addItem(OccultismItems.IESNIUM_INGOT, "Iesnium Ingot");
        this.addItem(OccultismItems.SILVER_NUGGET, "Silver Nugget");
        this.addItem(OccultismItems.IESNIUM_NUGGET, "Iesnium Nugget");
        this.addItem(OccultismItems.LENSES, "Glass Lenses");
        this.addItem(OccultismItems.INFUSED_LENSES, "Infused Lenses");
        this.addItem(OccultismItems.LENS_FRAME, "Lens Frame");
        this.addItem(OccultismItems.OTHERWORLD_GOGGLES, "Otherworld Goggles");
        this.addItem(OccultismItems.INFUSED_PICKAXE, "Infused Pickaxe");
        this.addItem(OccultismItems.SPIRIT_ATTUNED_PICKAXE_HEAD, "Spirit Attuned Pickaxe Head");
        this.addItem(OccultismItems.IESNIUM_PICKAXE, "Iesnium Pickaxe");
        this.addItem(OccultismItems.MAGIC_LAMP_EMPTY, "Empty Magic Lamp");
        this.addItem(OccultismItems.MINER_FOLIOT_UNSPECIALIZED, "Miner Foliot");
        this.addItem(OccultismItems.MINER_DJINNI_ORES, "Ore Miner Djinni");
        this.addItem(OccultismItems.MINER_DEBUG_UNSPECIALIZED, "Debug Miner");
        this.addItem(OccultismItems.SOUL_GEM_ITEM, "Soul Gem");
        this.addItem(OccultismItems.SATCHEL, "Surprisingly Substantial Satchel");
        this.addItem(OccultismItems.FAMILIAR_RING, "Familiar Ring");
        this.addItem(OccultismItems.SPAWN_EGG_FOLIOT, "Foliot Spawn Egg");
        this.addItem(OccultismItems.SPAWN_EGG_DJINNI, "Djinni Spawn Egg");
        this.addItem(OccultismItems.SPAWN_EGG_AFRIT, "Afrit Spawn Egg");
        this.addItem(OccultismItems.SPAWN_EGG_AFRIT_WILD, "Unbound Afrit Spawn Egg");
        this.addItem(OccultismItems.SPAWN_EGG_MARID, "Marid Spawn Egg");
        this.addItem(OccultismItems.SPAWN_EGG_POSSESSED_ENDERMITE, "Possessed Endermite Spawn Egg");
        this.addItem(OccultismItems.SPAWN_EGG_POSSESSED_SKELETON, "Possessed Skeleton Spawn Egg");
        this.addItem(OccultismItems.SPAWN_EGG_POSSESSED_ENDERMAN, "Possessed Enderman Spawn Egg");
        this.addItem(OccultismItems.SPAWN_EGG_WILD_HUNT_SKELETON, "Wild Hunt Skeleton Spawn Egg");
        this.addItem(OccultismItems.SPAWN_EGG_WILD_HUNT_WITHER_SKELETON, "Wild Hunt Wither Skeleton Spawn Egg");
        this.addItem(OccultismItems.SPAWN_EGG_OTHERWORLD_BIRD, "Drikwing Spawn Egg");
        this.addItem(OccultismItems.SPAWN_EGG_GREEDY_FAMILIAR, "Greedy Familiar Spawn Egg");
        this.addItem(OccultismItems.SPAWN_EGG_BAT_FAMILIAR, "Bat Familiar Spawn Egg");
        this.addItem(OccultismItems.SPAWN_EGG_DEER_FAMILIAR, "Deer Familiar Spawn Egg");
        this.addItem(OccultismItems.SPAWN_EGG_CTHULHU_FAMILIAR, "Cthulhu Familiar Spawn Egg");
        this.addItem(OccultismItems.SPAWN_EGG_DEVIL_FAMILIAR, "Devil Familiar Spawn Egg");
        this.addItem(OccultismItems.SPAWN_EGG_DRAGON_FAMILIAR, "Dragon Familiar Spawn Egg");
        this.addItem(OccultismItems.SPAWN_EGG_BLACKSMITH_FAMILIAR, "Blacksmith Familiar Spawn Egg");
        this.addItem(OccultismItems.SPAWN_EGG_GUARDIAN_FAMILIAR, "Guardian Familiar Spawn Egg");
        this.addItem(OccultismItems.SPAWN_EGG_HEADLESS_FAMILIAR, "Headless Ratman Familiar Spawn Egg");
        this.addItem(OccultismItems.SPAWN_EGG_CHIMERA_FAMILIAR, "Chimera Familiar Spawn Egg");
        this.addItem(OccultismItems.SPAWN_EGG_GOAT_FAMILIAR, "Goat Familiar Spawn Egg");
        this.addItem(OccultismItems.SPAWN_EGG_SHUB_NIGGURATH_FAMILIAR, "Shub Niggurath Familiar Spawn Egg");
        this.addItem(OccultismItems.SPAWN_EGG_BEHOLDER_FAMILIAR, "Beholder Familiar Spawn Egg");
        this.addItem(OccultismItems.SPAWN_EGG_FAIRY_FAMILIAR, "Fairy Familiar Spawn Egg");
        this.addItem(OccultismItems.SPAWN_EGG_MUMMY_FAMILIAR, "Mummy Familiar Spawn Egg");
        this.addItem(OccultismItems.SPAWN_EGG_BEAVER_FAMILIAR, "Beaver Familiar Spawn Egg");
        //TODO: that one block hidden among the items
        //TODO: item messages and tooltips
        //TODO: ritual dummies
    }

    private void addBook(){
        var helper = ModonomiconAPI.get().getLangHelper(Modonomicon.MODID);
        helper.book("dictionary_of_spirits");

//        this.addDemoBookFeaturesCategory(helper);
//        this.addDemoBookFormattingCategory(helper);

        this.add(helper.bookName(), "Dictionary of Spirits");
        this.add(helper.bookTooltip(), """
                This book aims to introduce the novice reader to the most common summoning rituals and equip them with a list of spirits and their names. 
                The authors advise caution in the summoning of the listed entities. 
                For help or to give feedback please join us in Discord https://invite.gg/klikli.
                """);
    }

    private void addAdvancements() {
        this.advancementTitle("root", "Occultism");
        this.advancementDescr("root", "Get spiritual");

        this.advancementTitle("familiars", "Occultism: Friends");
        this.advancementDescr("familiars", "Use a ritual to summon a familiar");

        this.familiarAdvancementTitle("deer", "Demonic Poop");
        this.familiarAdvancementDescr("deer", "Observe when your deer familiar poops demon seed");

        this.familiarAdvancementTitle("cthulhu", "You Monster!");
        this.familiarAdvancementDescr("cthulhu", "Make your cthulhu familiar sad");

        this.familiarAdvancementTitle("bat", "Cannibalism");
        this.familiarAdvancementDescr("bat", "Lure a normal bat near your bat familiar");

        this.familiarAdvancementTitle("devil", "Hellfire");
        this.familiarAdvancementDescr("devil", "Command your devil familiar to breath fire");

        this.familiarAdvancementTitle("greedy", "Errand Boy");
        this.familiarAdvancementDescr("greedy", "Let your greedy familiar pick something up for you");

        this.familiarAdvancementTitle("rare", "Rare Friend");
        this.familiarAdvancementDescr("rare", "Obtain a rare familiar variant");

        this.familiarAdvancementTitle("party", "Dance!");
        this.familiarAdvancementDescr("party", "Get your familiar to dance");

        this.familiarAdvancementTitle("capture", "Catch them all!");
        this.familiarAdvancementDescr("capture", "Trap your familiar in a familiar ring");

        this.familiarAdvancementTitle("dragon_nugget", "Deal!");
        this.familiarAdvancementDescr("dragon_nugget", "Give a gold nugget to your dragon familiar");

        this.familiarAdvancementTitle("dragon_ride", "Working together");
        this.familiarAdvancementDescr("dragon_ride", "Let your greedy familiar pick something up while riding a dragon familiar");
    }

    private void familiarAdvancementTitle(String name, String s) {
        this.add(((TranslatableContents)OccultismAdvancementProvider.familiarTitle(name).getContents()).getKey(), s);
    }

    private void familiarAdvancementDescr(String name, String s) {
        this.add(((TranslatableContents)OccultismAdvancementProvider.familiarDescr(name).getContents()).getKey(), s);
    }

    private void advancementTitle(String name, String s) {
        this.add(((TranslatableContents)OccultismAdvancementProvider.title(name).getContents()).getKey(), s);
    }

    private void advancementDescr(String name, String s) {
        this.add(((TranslatableContents)OccultismAdvancementProvider.descr(name).getContents()).getKey(), s);
    }
}
