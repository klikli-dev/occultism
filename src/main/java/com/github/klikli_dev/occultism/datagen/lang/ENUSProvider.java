/*
 * MIT License
 *
 * Copyright 2021 vemerion, klikli-dev
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

package com.github.klikli_dev.occultism.datagen.lang;

import com.github.klikli_dev.occultism.Occultism;
import com.github.klikli_dev.occultism.datagen.OccultismAdvancementProvider;
import com.github.klikli_dev.occultism.integration.modonomicon.OccultismModonomiconConstants;
import com.github.klikli_dev.occultism.integration.modonomicon.OccultismModonomiconConstants.I18n;
import com.github.klikli_dev.occultism.integration.modonomicon.pages.BookSpiritFireRecipePageModel;
import com.github.klikli_dev.occultism.registry.OccultismBlocks;
import com.github.klikli_dev.occultism.registry.OccultismEntities;
import com.github.klikli_dev.occultism.registry.OccultismItems;
import com.klikli_dev.modonomicon.api.ModonomiconAPI;
import com.klikli_dev.modonomicon.api.datagen.BookLangHelper;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookCraftingRecipePageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookSpotlightPageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookTextPageModel;
import net.minecraft.Util;
import net.minecraft.data.DataGenerator;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.data.LanguageProvider;

public class ENUSProvider extends LanguageProvider {

    public static final String COLOR_PURPLE = "ad03fc";
    public static final String DEMONS_DREAM = "Demon's Dream";


    public ENUSProvider(DataGenerator gen) {
        super(gen, Occultism.MODID, "en_us");
    }

    public void addItemMessages() {

        //"item\.occultism\.(.*?)\.(.*)": "(.*)",
        // this.add\(OccultismItems.\U\1\E.get\(\).getDescriptionId\(\)  + " \2", "\3"\);

        //book of callings use generic message base key, hence the manual string
        this.add("item.occultism.book_of_calling" + ".message_target_uuid_no_match", "This spirit is not currently bound to this book. Shift-Click the spirit to bind it to this book.");
        this.add("item.occultism.book_of_calling" + ".message_target_linked", "This spirit is now bound to this book.");
        this.add("item.occultism.book_of_calling" + ".message_target_cannot_link", "This spirit cannot be bound to this book - the book of calling needs to match the spirit's task!");
        this.add("item.occultism.book_of_calling" + ".message_target_entity_no_inventory", "This entity has no inventory, it cannot be set as deposit location.");
        this.add("item.occultism.book_of_calling" + ".message_spirit_not_found", "The spirit bound to this book is not dwelling on this plane of existence.");
        this.add("item.occultism.book_of_calling" + ".message_set_deposit", "%s will now deposit into %s from the side: %s");
        this.add("item.occultism.book_of_calling" + ".message_set_deposit_entity", "%s will now hand over items to: %s");
        this.add("item.occultism.book_of_calling" + ".message_set_extract", "%s will now extract from %s from the side: %s");
        this.add("item.occultism.book_of_calling" + ".message_set_base", "Set base for %s to %s");
        this.add("item.occultism.book_of_calling" + ".message_set_storage_controller", "%s will now accept work orders from %s");
        this.add("item.occultism.book_of_calling" + ".message_set_work_area_size", "%s will now monitor a work area of %s");
        this.add("item.occultism.book_of_calling" + ".message_set_managed_machine", "Updated machine settings for %s");
        this.add("item.occultism.book_of_calling" + ".message_set_managed_machine_extract_location", "%s will now extract from %s from the side: %s");
        this.add("item.occultism.book_of_calling" + ".message_no_managed_machine", "Set a managed machine before setting an extract location %s");

        this.add(OccultismItems.STABLE_WORMHOLE.get().getDescriptionId() + ".message.set_storage_controller", "Linked the stable wormhole to this storage actuator.");
        this.add(OccultismItems.STORAGE_REMOTE.get().getDescriptionId() + ".message.not_loaded", "Chunk for storage actuator not loaded!");
        this.add(OccultismItems.STORAGE_REMOTE.get().getDescriptionId() + ".message.linked", "Linked storage remote to actuator.");
        this.add(OccultismItems.DIVINATION_ROD.get().getDescriptionId() + ".message.no_linked_block", "The divination rod is not attuned to any material.");
        this.add(OccultismItems.DIVINATION_ROD.get().getDescriptionId() + ".message.linked_block", "The divination rod is now attuned to %s.");
        this.add(OccultismItems.DIVINATION_ROD.get().getDescriptionId() + ".message.no_link_found", "There is no resonance with this block.");
        this.add(OccultismItems.SOUL_GEM_ITEM.get().getDescriptionId() + ".message.entity_type_denied", "Soul gems cannot contain this type of being.");
    }

    public void addItemTooltips() {
        //"item\.occultism\.(.*?)\.(.*)": "(.*)",
        // this.add\(OccultismItems.\U\1\E.get\(\).getDescriptionId\(\)  + " \2", "\3"\);

        this.add(OccultismItems.BOOK_OF_BINDING_FOLIOT.get().getDescriptionId() + ".tooltip", "This book has not been bound to a foliot yet.");
        this.add(OccultismItems.BOOK_OF_BINDING_BOUND_FOLIOT.get().getDescriptionId() + ".tooltip", "Can be used to summon the foliot %s");
        this.add(OccultismItems.BOOK_OF_BINDING_DJINNI.get().getDescriptionId() + ".tooltip", "This book has not been bound to a djinni yet.");
        this.add(OccultismItems.BOOK_OF_BINDING_BOUND_DJINNI.get().getDescriptionId() + ".tooltip", "Can be used to summon the djinni %s");
        this.add(OccultismItems.BOOK_OF_BINDING_AFRIT.get().getDescriptionId() + ".tooltip", "This book has not been bound to an afrit yet.");
        this.add(OccultismItems.BOOK_OF_BINDING_BOUND_AFRIT.get().getDescriptionId() + ".tooltip", "Can be used to summon the afrit %s");
        this.add(OccultismItems.BOOK_OF_BINDING_MARID.get().getDescriptionId() + ".tooltip", "This book has not been bound to a marid yet.");
        this.add(OccultismItems.BOOK_OF_BINDING_BOUND_MARID.get().getDescriptionId() + ".tooltip", "Can be used to summon the marid %s");

        this.add("item.occultism.book_of_calling_foliot" + ".tooltip", "Foliot %s");
        this.add("item.occultism.book_of_calling_foliot" + ".tooltip_dead", "%s has left this plane of existence.");
        this.add("item.occultism.book_of_calling_foliot" + ".tooltip.extract", "Extracts from: %s.");
        this.add("item.occultism.book_of_calling_foliot" + ".tooltip.deposit", "Deposits to: %s.");
        this.add("item.occultism.book_of_calling_foliot" + ".tooltip.deposit_entity", "Hands items over to: %s.");
        this.add("item.occultism.book_of_calling_djinni" + ".tooltip", "Djinni %s");
        this.add("item.occultism.book_of_calling_djinni" + ".tooltip_dead", "%s has left this plane of existence.");
        this.add("item.occultism.book_of_calling_djinni" + ".tooltip.extract", "Extracts from: %s.");
        this.add("item.occultism.book_of_calling_djinni" + ".tooltip.deposit", "Deposits to: % s");
        this.add(OccultismItems.FAMILIAR_RING.get().getDescriptionId() + ".tooltip", "Occupied by the familiar %s");

        this.add("item.minecraft.diamond_sword.occultism_spirit_tooltip", "%s is bound to this sword. May your foes tremor before its glory.");

        this.add(OccultismItems.STABLE_WORMHOLE.get().getDescriptionId() + ".tooltip.unlinked", "Not linked to a storage actuator.");
        this.add(OccultismItems.STABLE_WORMHOLE.get().getDescriptionId() + ".tooltip.linked", "Linked to storage actuator at %s.");
        this.add(OccultismItems.STORAGE_REMOTE.get().getDescriptionId() + ".tooltip", "Access a storage network remotely.");

        this.add(OccultismItems.STORAGE_REMOTE.get().getDescriptionId() + ".tooltip.linked", "Bound to %s.");
        this.add(OccultismItems.DIVINATION_ROD.get().getDescriptionId() + ".tooltip.no_linked_block", "Not attuned to any material.");
        this.add(OccultismItems.DIVINATION_ROD.get().getDescriptionId() + ".tooltip.linked_block", "Attuned to %s.");
        this.add(OccultismItems.DIMENSIONAL_MATRIX.get().getDescriptionId() + ".tooltip", "%s is bound to this dimensional matrix.");
        this.add(OccultismItems.INFUSED_PICKAXE.get().getDescriptionId() + ".tooltip", "%s is bound to this pickaxe.");
        this.add(OccultismItems.MINER_FOLIOT_UNSPECIALIZED.get().getDescriptionId() + ".tooltip", "%s will mine random blocks in the mining dimension.");
        this.add(OccultismItems.MINER_DJINNI_ORES.get().getDescriptionId() + ".tooltip", "%s will mine random ores in the mining dimension.");
        this.add(OccultismItems.MINER_DEBUG_UNSPECIALIZED.get().getDescriptionId() + ".tooltip", "Debug Miner will mine random blocks in the mining dimension.");
        this.add(OccultismItems.MINER_AFRIT_DEEPS.get().getDescriptionId() + ".tooltip", "%s will mine random ores and deepslate ores in the mining dimension.");
        this.add(OccultismItems.MINER_MARID_MASTER.get().getDescriptionId() + ".tooltip", "%s will mine random ores, deepslate ores and rare ores in the mining dimension.");
        this.add(OccultismItems.SOUL_GEM_ITEM.get().getDescriptionId() + ".tooltip_filled", "Contains a captured %s.");
        this.add(OccultismItems.SOUL_GEM_ITEM.get().getDescriptionId() + ".tooltip_empty", "Use on a creature to capture it.");
        this.add(OccultismItems.SATCHEL.get().getDescriptionId() + ".tooltip", "%s is bound to this satchel.");
    }

    private void addItems() {
        //Notepad++ magic:
        //"item\.occultism\.(.*)": "(.*)"
        //this.addItem\(OccultismItems.\U\1\E, "\2"\);

        this.add("itemGroup.occultism", "Occultism");

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
        this.add("item.occultism.otherworld_sapling", "Otherworld Sapling");
        this.add("item.occultism.otherworld_sapling_natural", "Unstable Otherworld Sapling");
        this.addItem(OccultismItems.OTHERWORLD_ASHES, "Otherworld Ashes");
        this.addItem(OccultismItems.BURNT_OTHERSTONE, "Burnt Otherstone");
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
        this.addItem(OccultismItems.MINER_AFRIT_DEEPS, "Deep Ore Miner Afrit");
        this.addItem(OccultismItems.MINER_MARID_MASTER, "Master Miner Marid");
        this.addItem(OccultismItems.SOUL_GEM_ITEM, "Soul Gem");
        this.add(OccultismItems.SOUL_GEM_ITEM.get().getDescriptionId() + "_empty", "Empty Soul Gem");
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
    }

    private void addBlocks() {
        //"block\.occultism\.(.*?)": "(.*)",
        //this.addBlock\(OccultismItems.\U\1\E, "\2"\);

        this.addBlock(OccultismBlocks.OTHERSTONE, "Otherstone");
        this.addBlock(OccultismBlocks.OTHERSTONE_SLAB, "Otherstone Slab");
        this.addBlock(OccultismBlocks.OTHERSTONE_PEDESTAL, "Otherstone Pedestal");
        this.addBlock(OccultismBlocks.SACRIFICIAL_BOWL, "Sacrificial Bowl");
        this.addBlock(OccultismBlocks.GOLDEN_SACRIFICIAL_BOWL, "Golden Sacrificial Bowl");
        this.addBlock(OccultismBlocks.CHALK_GLYPH_WHITE, "White Chalk Glyph");
        this.addBlock(OccultismBlocks.CHALK_GLYPH_GOLD, "Gold Chalk Glyph");
        this.addBlock(OccultismBlocks.CHALK_GLYPH_PURPLE, "Purple Chalk Glyph");
        this.addBlock(OccultismBlocks.CHALK_GLYPH_RED, "Red Chalk Glyph");
        this.addBlock(OccultismBlocks.STORAGE_CONTROLLER, "Dimensional Storage Actuator");
        this.addBlock(OccultismBlocks.STORAGE_CONTROLLER_BASE, "Storage Actuator Base");
        this.addBlock(OccultismBlocks.STORAGE_STABILIZER_TIER1, "Tier 1 Dimensional Storage Stabilizer");
        this.addBlock(OccultismBlocks.STORAGE_STABILIZER_TIER2, "Tier 2 Dimensional Storage Stabilizer");
        this.addBlock(OccultismBlocks.STORAGE_STABILIZER_TIER3, "Tier 3 Dimensional Storage Stabilizer");
        this.addBlock(OccultismBlocks.STORAGE_STABILIZER_TIER4, "Tier 4 Dimensional Storage Stabilizer");
        this.addBlock(OccultismBlocks.STABLE_WORMHOLE, "Stable Wormhole");
        this.addBlock(OccultismBlocks.DATURA, "Demon's Dream");

        this.add("block.occultism.otherworld_log", "Otherworld Wood");
        this.add("block.occultism.otherworld_sapling", "Otherworld Sapling");
        this.add("block.occultism.otherworld_leaves", "Otherworld Leaves");

        this.addBlock(OccultismBlocks.SPIRIT_FIRE, "Spiritfire");
        this.addBlock(OccultismBlocks.SPIRIT_ATTUNED_CRYSTAL, "Spirit Attuned Crystal");
        this.addBlock(OccultismBlocks.CANDLE_WHITE, "White Candle");
        this.addBlock(OccultismBlocks.SILVER_ORE, "Silver Ore");
        this.addBlock(OccultismBlocks.SILVER_ORE_DEEPSLATE, "Deepslate Silver Ore");
        this.addBlock(OccultismBlocks.IESNIUM_ORE, "Iesnium Ore");
        this.addBlock(OccultismBlocks.SILVER_BLOCK, "Block of Silver");
        this.addBlock(OccultismBlocks.IESNIUM_BLOCK, "Block of Iesnium");
        this.addBlock(OccultismBlocks.DIMENSIONAL_MINESHAFT, "Dimensional Mineshaft");
        this.addBlock(OccultismBlocks.SKELETON_SKULL_DUMMY, "Skeleton Skull");
        this.addBlock(OccultismBlocks.WITHER_SKELETON_SKULL_DUMMY, "Wither Skeleton Skull");
        this.addBlock(OccultismBlocks.LIGHTED_AIR, "Lighted Air");
        this.addBlock(OccultismBlocks.SPIRIT_LANTERN, "Spirit Lantern");
        this.addBlock(OccultismBlocks.SPIRIT_CAMPFIRE, "Spirit Campfire");
        this.addBlock(OccultismBlocks.SPIRIT_TORCH, "Spirit Torch"); //spirit wall torch automatically uses the same translation
    }

    private void addEntities() {
        //"entity\.occultism\.(.*?)": "(.*)",
        //this.addEntityType\(OccultismEntities.\U\1\E, "\2"\);

        this.addEntityType(OccultismEntities.FOLIOT, "Foliot");
        this.addEntityType(OccultismEntities.DJINNI, "Djinni");
        this.addEntityType(OccultismEntities.AFRIT, "Afrit");
        this.addEntityType(OccultismEntities.AFRIT_WILD, "Unbound Afrit");
        this.addEntityType(OccultismEntities.MARID, "Marid");
        this.addEntityType(OccultismEntities.POSSESSED_ENDERMITE, "Possessed Endermite");
        this.addEntityType(OccultismEntities.POSSESSED_SKELETON, "Possessed Skeleton");
        this.addEntityType(OccultismEntities.POSSESSED_ENDERMAN, "Possessed Enderman");
        this.addEntityType(OccultismEntities.POSSESSED_GHAST, "Possessed Ghast");
        this.addEntityType(OccultismEntities.WILD_HUNT_SKELETON, "Wild Hunt Skeleton");
        this.addEntityType(OccultismEntities.WILD_HUNT_WITHER_SKELETON, "Wild Hunt Wither Skeleton");
        this.addEntityType(OccultismEntities.OTHERWORLD_BIRD, "Drikwing");
        this.addEntityType(OccultismEntities.GREEDY_FAMILIAR, "Greedy Familiar");
        this.addEntityType(OccultismEntities.BAT_FAMILIAR, "Bat Familiar");
        this.addEntityType(OccultismEntities.DEER_FAMILIAR, "Deer Familiar");
        this.addEntityType(OccultismEntities.CTHULHU_FAMILIAR, "Cthulhu Familiar");
        this.addEntityType(OccultismEntities.DEVIL_FAMILIAR, "Devil Familiar");
        this.addEntityType(OccultismEntities.DRAGON_FAMILIAR, "Dragon Familiar");
        this.addEntityType(OccultismEntities.BLACKSMITH_FAMILIAR, "Blacksmith Familiar");
        this.addEntityType(OccultismEntities.GUARDIAN_FAMILIAR, "Guardian Familiar");
        this.addEntityType(OccultismEntities.HEADLESS_FAMILIAR, "Headless Familiar");
        this.addEntityType(OccultismEntities.CHIMERA_FAMILIAR, "Chimera Familiar");
        this.addEntityType(OccultismEntities.GOAT_FAMILIAR, "Goat Familiar");
        this.addEntityType(OccultismEntities.SHUB_NIGGURATH_FAMILIAR, "Shub Niggurath Familiar");
        this.addEntityType(OccultismEntities.BEHOLDER_FAMILIAR, "Beholder Familiar");
        this.addEntityType(OccultismEntities.FAIRY_FAMILIAR, "Fairy Familiar");
        this.addEntityType(OccultismEntities.MUMMY_FAMILIAR, "Mummy Familiar");
        this.addEntityType(OccultismEntities.BEAVER_FAMILIAR, "Beaver Familiar");
        this.addEntityType(OccultismEntities.SHUB_NIGGURATH_SPAWN, "Shub Niggurath Spawn");
        this.addEntityType(OccultismEntities.THROWN_SWORD, "Thrown Sword");
    }

    private void addMiscTranslations() {

        //"(.*?)": "(.*)",
        //this.add\("\1", "\2"\);

        //Jobs
        this.add("job.occultism.lumberjack", "Lumberjack");
        this.add("job.occultism.crush_tier1", "Slow Crusher");
        this.add("job.occultism.crush_tier2", "Crusher");
        this.add("job.occultism.crush_tier3", "Fast Crusher");
        this.add("job.occultism.crush_tier4", "Very Fast Crusher");
        this.add("job.occultism.manage_machine", "Machine Operator");
        this.add("job.occultism.transport_items", "Transporter");
        this.add("job.occultism.cleaner", "Janitor");
        this.add("job.occultism.trade_otherstone_t1", "Otherstone Trader");
        this.add("job.occultism.trade_otherworld_saplings_t1", "Otherworld Sapling Trader");
        this.add("job.occultism.clear_weather", "Sunshine Spirit");
        this.add("job.occultism.rain_weather", "Rainy Weather Spirit");
        this.add("job.occultism.thunder_weather", "Thunderstorm Spirit");
        this.add("job.occultism.day_time", "Dawn Spirit");
        this.add("job.occultism.night_time", "Dusk Spirit");

        //Enums
        this.add("enum.occultism.facing.up", "Up");
        this.add("enum.occultism.facing.down", "Down");
        this.add("enum.occultism.facing.north", "North");
        this.add("enum.occultism.facing.south", "South");
        this.add("enum.occultism.facing.west", "West");
        this.add("enum.occultism.facing.east", "East");
        this.add("enum.occultism.book_of_calling.item_mode.set_deposit", "Set Deposit");
        this.add("enum.occultism.book_of_calling.item_mode.set_extract", "Set Extract");
        this.add("enum.occultism.book_of_calling.item_mode.set_base", "Set Base Location");
        this.add("enum.occultism.book_of_calling.item_mode.set_storage_controller", "Set Storage Actuator");
        this.add("enum.occultism.book_of_calling.item_mode.set_managed_machine", "Set Managed Machine");
        this.add("enum.occultism.work_area_size.small", "16x16");
        this.add("enum.occultism.work_area_size.medium", "32x32");
        this.add("enum.occultism.work_area_size.large", "64x64");

        //Debug messages
        this.add("debug.occultism.debug_wand.printed_glyphs", "Printed glyphs");
        this.add("debug.occultism.debug_wand.glyphs_verified", "Glyphs verified");
        this.add("debug.occultism.debug_wand.glyphs_not_verified", "Glyphs not verified");
        this.add("debug.occultism.debug_wand.spirit_selected", "Selected spirit with id %s");
        this.add("debug.occultism.debug_wand.spirit_tamed", "Tamed spirit with id %s");
        this.add("debug.occultism.debug_wand.deposit_selected", "Set deposit block %s, facing %s");
        this.add("debug.occultism.debug_wand.no_spirit_selected", "No spirit selected.");

        //Ritual Sacrifices
        this.add("ritual.occultism.sacrifice.cows", "Cow");
        this.add("ritual.occultism.sacrifice.bats", "Bat");
        this.add("ritual.occultism.sacrifice.zombies", "Zombie");
        this.add("ritual.occultism.sacrifice.parrots", "Parrot");
        this.add("ritual.occultism.sacrifice.chicken", "Chicken");
        this.add("ritual.occultism.sacrifice.pigs", "Pigs");
        this.add("ritual.occultism.sacrifice.humans", "Villager or Player");
        this.add("ritual.occultism.sacrifice.squid", "Squid");
        this.add("ritual.occultism.sacrifice.horses", "Horse");
        this.add("ritual.occultism.sacrifice.sheep", "Sheep");
        this.add("ritual.occultism.sacrifice.llamas", "Llama");
        this.add("ritual.occultism.sacrifice.snow_golem", "Snow Golem");
        this.add("ritual.occultism.sacrifice.spiders", "Spider");

        //Network Message
        this.add("network.messages.occultism.request_order.order_received", "Order received!");

        //Effects
        this.add("effect.occultism.third_eye", "Third Eye");
        this.add("effect.occultism.double_jump", "Multi Jump");
        this.add("effect.occultism.dragon_greed", "Dragon's Greed");
        this.add("effect.occultism.mummy_dodge", "Dodge");
        this.add("effect.occultism.bat_lifesteal", "Lifesteal");
        this.add("effect.occultism.beaver_harvest", "Beaver Harvest");

        //Sounds
        this.add("occultism.subtitle.chalk", "Chalk");
        this.add("occultism.subtitle.brush", "Brush");
        this.add("occultism.subtitle.start_ritual", "Start Ritual");
        this.add("occultism.subtitle.tuning_fork", "Tuning Fork");
        this.add("occultism.subtitle.crunching", "Crunching");
        this.add("occultism.subtitle.poof", "Poof!");

    }

    private void addGuiTranslations() {
        this.add("gui.occultism.book_of_calling.mode", "Mode");
        this.add("gui.occultism.book_of_calling.work_area", "Work Area");
        this.add("gui.occultism.book_of_calling.manage_machine.insert", "Insert Facing");
        this.add("gui.occultism.book_of_calling.manage_machine.extract", "Extract Facing");
        this.add("gui.occultism.book_of_calling.manage_machine.custom_name", "Custom Name");

        // Spirit GUI
        this.add("gui.occultism.spirit.age", "Essence Decay: %d%%");
        this.add("gui.occultism.spirit.job", "%s");

        // Spirit Transporter GUI
        this.add("gui.occultism.spirit.transporter.filter_mode", "Filter Mode");
        this.add("gui.occultism.spirit.transporter.filter_mode.blacklist", "Blacklist");
        this.add("gui.occultism.spirit.transporter.filter_mode.whitelist", "Whitelist");
        this.add("gui.occultism.spirit.transporter.tag_filter", "Enter the tags to filter for separated by \";\".\nE.g.: \"forge:ores;*logs*\".\nUse \"*\" to match any character, e.g. \"*ore*\" to match ore tags from any mod. To filter for items, prefix the item id with \"item:\", E.g.: \"item:minecraft:chest\".");

        // Storage Controller GUI
        this.add("gui.occultism.storage_controller.space_info_label", "%d/%d");
        this.add("gui.occultism.storage_controller.shift", "Hold shift for more information.");
        this.add("gui.occultism.storage_controller.search.tooltip@", "Prefix @: Search mod id.");
        this.add("gui.occultism.storage_controller.search.tooltip#", "Prefix #: Search in item tooltip.");
        this.add("gui.occultism.storage_controller.search.tooltip$", "Prefix $: Search for Tag.");
        this.add("gui.occultism.storage_controller.search.tooltip_rightclick", "Clear the text with a right-click.");
        this.add("gui.occultism.storage_controller.search.tooltip_clear", "Clear search.");
        this.add("gui.occultism.storage_controller.search.tooltip_jei_on", "Sync search with JEI.");
        this.add("gui.occultism.storage_controller.search.tooltip_jei_off", "Do not sync search with JEI.");
        this.add("gui.occultism.storage_controller.search.tooltip_sort_type_amount", "Sort by amount.");
        this.add("gui.occultism.storage_controller.search.tooltip_sort_type_name", "Sort by item name.");
        this.add("gui.occultism.storage_controller.search.tooltip_sort_type_mod", "Sort by mod name.");
        this.add("gui.occultism.storage_controller.search.tooltip_sort_direction_down", "Sort ascending.");
        this.add("gui.occultism.storage_controller.search.tooltip_sort_direction_up", "Sort descending.");
        this.add("gui.occultism.storage_controller.search.machines.tooltip@", "Prefix @: Search mod id.");
        this.add("gui.occultism.storage_controller.search.machines.tooltip_sort_type_amount", "Sort by distance.");
        this.add("gui.occultism.storage_controller.search.machines.tooltip_sort_type_name", "Sort by machine name.");
        this.add("gui.occultism.storage_controller.search.machines.tooltip_sort_type_mod", "Sort by mod name.");

    }

    private void addRitualMessages() {
        this.add("ritual.occultism.pentacle_help", "\u00a7lInvalid pentacle!\u00a7r\nWere you trying to create pentacle: %s? Missing:\n%s");
        this.add("ritual.occultism.pentacle_help_at_glue", " at position ");
        this.add("ritual.occultism.ritual_help", "\u00a7lInvalid ritual!\u00a7r\nWere you trying to perform ritual: \"%s\"? Missing items:\n%s");
        this.add("ritual.occultism.disabled", "This ritual is disabled on this server.");
        this.add("ritual.occultism.does_not_exist", "\u00a7lUnknown ritual\u00a7r. Make sure the pentacle & ingredients are set up correctly. If you are still unsuccessful join our discord at https://invite.gg/klikli");
        this.add("ritual.occultism.book_not_bound", "\u00a7lUnbound Book of Calling\u00a7r. You must craft this book with Dictionary of Spirits to bind to a spirit before starting a ritual.");
        this.add("ritual.occultism.debug.conditions", "Not all requirements for this ritual are met.");
        this.add("ritual.occultism.debug.started", "Ritual started.");
        this.add("ritual.occultism.debug.finished", "Ritual completed successfully.");
        this.add("ritual.occultism.debug.interrupted", "Ritual interrupted.");
        this.add("ritual.occultism.summon_foliot_lumberjack.conditions", "Not all requirements for this ritual are met.");
        this.add("ritual.occultism.summon_foliot_lumberjack.started", "Started summoning foliot lumberjack.");
        this.add("ritual.occultism.summon_foliot_lumberjack.finished", "Summoned foliot lumberjack successfully.");
        this.add("ritual.occultism.summon_foliot_lumberjack.interrupted", "Summoning of foliot lumberjack interrupted.");
        this.add("ritual.occultism.summon_foliot_transport_items.conditions", "Not all requirements for this ritual are met.");
        this.add("ritual.occultism.summon_foliot_transport_items.started", "Started summoning foliot transporter.");
        this.add("ritual.occultism.summon_foliot_transport_items.finished", "Summoned foliot transporter successfully.");
        this.add("ritual.occultism.summon_foliot_transport_items.interrupted", "Summoning of foliot transporter interrupted.");
        this.add("ritual.occultism.summon_foliot_cleaner.conditions", "Not all requirements for this ritual are met.");
        this.add("ritual.occultism.summon_foliot_cleaner.started", "Started summoning foliot janitor.");
        this.add("ritual.occultism.summon_foliot_cleaner.finished", "Summoned foliot janitor successfully.");
        this.add("ritual.occultism.summon_foliot_cleaner.interrupted", "Summoning of janitor transporter interrupted.");
        this.add("ritual.occultism.summon_foliot_crusher.conditions", "Not all requirements for this ritual are met.");
        this.add("ritual.occultism.summon_foliot_crusher.started", "Started summoning foliot ore crusher.");
        this.add("ritual.occultism.summon_foliot_crusher.finished", "Summoned foliot ore crusher successfully.");
        this.add("ritual.occultism.summon_foliot_crusher.interrupted", "Summoning of foliot ore crusher interrupted.");
        this.add("ritual.occultism.summon_djinni_crusher.conditions", "Not all requirements for this ritual are met.");
        this.add("ritual.occultism.summon_djinni_crusher.started", "Started summoning djinni ore crusher.");
        this.add("ritual.occultism.summon_djinni_crusher.finished", "Summoned djinni ore crusher successfully.");
        this.add("ritual.occultism.summon_djinni_crusher.interrupted", "Summoning of djinni ore crusher interrupted.");
        this.add("ritual.occultism.summon_afrit_crusher.conditions", "Not all requirements for this ritual are met.");
        this.add("ritual.occultism.summon_afrit_crusher.started", "Started summoning afrit ore crusher.");
        this.add("ritual.occultism.summon_afrit_crusher.finished", "Summoned afrit ore crusher successfully.");
        this.add("ritual.occultism.summon_afrit_crusher.interrupted", "Summoning of afrit ore crusher interrupted.");
        this.add("ritual.occultism.summon_marid_crusher.conditions", "Not all requirements for this ritual are met.");
        this.add("ritual.occultism.summon_marid_crusher.started", "Started summoning marid ore crusher.");
        this.add("ritual.occultism.summon_marid_crusher.finished", "Summoned marid ore crusher successfully.");
        this.add("ritual.occultism.summon_marid_crusher.interrupted", "Summoning of marid ore crusher interrupted.");
        this.add("ritual.occultism.summon_foliot_sapling_trader.conditions", "Not all requirements for this ritual are met.");
        this.add("ritual.occultism.summon_foliot_sapling_trader.started", "Started summoning foliot otherworld sapling trader.");
        this.add("ritual.occultism.summon_foliot_sapling_trader.finished", "Summoned foliot otherworld sapling successfully.");
        this.add("ritual.occultism.summon_foliot_sapling_trader.interrupted", "Summoning of foliot otherworld sapling trader interrupted.");
        this.add("ritual.occultism.summon_foliot_otherstone_trader.conditions", "Not all requirements for this ritual are met.");
        this.add("ritual.occultism.summon_foliot_otherstone_trader.started", "Started summoning foliot otherstone trader.");
        this.add("ritual.occultism.summon_foliot_otherstone_trader.finished", "Summoned foliot otherstone trader successfully.");
        this.add("ritual.occultism.summon_foliot_otherstone_trader.interrupted", "Summoning of foliot otherstone trader interrupted.");
        this.add("ritual.occultism.summon_djinni_manage_machine.conditions", "Not all requirements for this ritual are met.");
        this.add("ritual.occultism.summon_djinni_manage_machine.started", "Started summoning djinni machine operator.");
        this.add("ritual.occultism.summon_djinni_manage_machine.finished", "Summoned djinni machine operator successfully.");
        this.add("ritual.occultism.summon_djinni_manage_machine.interrupted", "Summoning of djinni machine operator interrupted.");
        this.add("ritual.occultism.summon_djinni_clear_weather.conditions", "Not all requirements for this ritual are met.");
        this.add("ritual.occultism.summon_djinni_clear_weather.started", "Started summoning djinni to clear weather.");
        this.add("ritual.occultism.summon_djinni_clear_weather.finished", "Summoned djinni successfully.");
        this.add("ritual.occultism.summon_djinni_clear_weather.interrupted", "Summoning of djinni interrupted.");
        this.add("ritual.occultism.summon_djinni_day_time.conditions", "Not all requirements for this ritual are met.");
        this.add("ritual.occultism.summon_djinni_day_time.started", "Started summoning djinni to set time to day.");
        this.add("ritual.occultism.summon_djinni_day_time.finished", "Summoned djinni successfully.");
        this.add("ritual.occultism.summon_djinni_day_time.interrupted", "Summoning of djinni interrupted.");
        this.add("ritual.occultism.summon_djinni_night_time.conditions", "Not all requirements for this ritual are met.");
        this.add("ritual.occultism.summon_djinni_night_time.started", "Started summoning djinni to set time to night.");
        this.add("ritual.occultism.summon_djinni_night_time.finished", "Summoned djinni successfully.");
        this.add("ritual.occultism.summon_djinni_night_time.interrupted", "Summoning of djinni interrupted.");
        this.add("ritual.occultism.summon_afrit_rain_weather.conditions", "Not all requirements for this ritual are met.");
        this.add("ritual.occultism.summon_afrit_rain_weather.started", "Started summoning afrit for rainy weather.");
        this.add("ritual.occultism.summon_afrit_rain_weather.finished", "Summoned afrit successfully.");
        this.add("ritual.occultism.summon_afrit_rain_weather.interrupted", "Summoning of afrit interrupted.");
        this.add("ritual.occultism.summon_afrit_thunder_weather.conditions", "Not all requirements for this ritual are met.");
        this.add("ritual.occultism.summon_afrit_thunder_weather.started", "Started summoning afrit for a thunderstorm.");
        this.add("ritual.occultism.summon_afrit_thunder_weather.finished", "Summoned afrit successfully.");
        this.add("ritual.occultism.summon_afrit_thunder_weather.interrupted", "Summoning of afrit interrupted.");
        this.add("ritual.occultism.summon_wild_afrit.conditions", "Not all requirements for this ritual are met.");
        this.add("ritual.occultism.summon_wild_afrit.started", "Started summoning unbound afrit.");
        this.add("ritual.occultism.summon_wild_afrit.finished", "Summoned unbound afrit successfully.");
        this.add("ritual.occultism.summon_wild_afrit.interrupted", "Summoning of unbound afrit interrupted.");
        this.add("ritual.occultism.summon_wild_hunt.conditions", "Not all requirements for this ritual are met.");
        this.add("ritual.occultism.summon_wild_hunt.started", "Started summoning the wild hunt.");
        this.add("ritual.occultism.summon_wild_hunt.finished", "Summoned the wild hunt successfully.");
        this.add("ritual.occultism.summon_wild_hunt.interrupted", "Summoning of the wild hunt interrupted.");
        this.add("ritual.occultism.craft_dimensional_matrix.conditions", "Not all requirements for this ritual are met.");
        this.add("ritual.occultism.craft_dimensional_matrix.started", "Started binding djinni into dimensional matrix.");
        this.add("ritual.occultism.craft_dimensional_matrix.finished", "Successfully bound djinni into dimensional matrix.");
        this.add("ritual.occultism.craft_dimensional_matrix.interrupted", "Binding of djinni interrupted.");
        this.add("ritual.occultism.craft_dimensional_mineshaft.conditions", "Not all requirements for this ritual are met.");
        this.add("ritual.occultism.craft_dimensional_mineshaft.started", "Started binding djinni into dimensional mineshaft.");
        this.add("ritual.occultism.craft_dimensional_mineshaft.finished", "Successfully bound djinni into dimensional mineshaft.");
        this.add("ritual.occultism.craft_dimensional_mineshaft.interrupted", "Binding of djinni interrupted.");
        this.add("ritual.occultism.craft_storage_controller_base.conditions", "Not all requirements for this ritual are met.");
        this.add("ritual.occultism.craft_storage_controller_base.started", "Started binding foliot into storage actuator base.");
        this.add("ritual.occultism.craft_storage_controller_base.finished", "Successfully bound foliot into storage actuator base.");
        this.add("ritual.occultism.craft_storage_controller_base.interrupted", "Binding of foliot interrupted.");
        this.add("ritual.occultism.craft_stabilizer_tier1.conditions", "Not all requirements for this ritual are met.");
        this.add("ritual.occultism.craft_stabilizer_tier1.started", "Started binding foliot into storage stabilizer.");
        this.add("ritual.occultism.craft_stabilizer_tier1.finished", "Successfully bound foliot into storage stabilizer.");
        this.add("ritual.occultism.craft_stabilizer_tier1.interrupted", "Binding of foliot interrupted.");
        this.add("ritual.occultism.craft_stabilizer_tier2.conditions", "Not all requirements for this ritual are met.");
        this.add("ritual.occultism.craft_stabilizer_tier2.started", "Started binding djinni into storage stabilizer.");
        this.add("ritual.occultism.craft_stabilizer_tier2.finished", "Successfully bound djinni into storage stabilizer.");
        this.add("ritual.occultism.craft_stabilizer_tier2.interrupted", "Binding of djinni interrupted.");
        this.add("ritual.occultism.craft_stabilizer_tier3.conditions", "Not all requirements for this ritual are met.");
        this.add("ritual.occultism.craft_stabilizer_tier3.started", "Started binding afrit into storage stabilizer.");
        this.add("ritual.occultism.craft_stabilizer_tier3.finished", "Successfully bound afrit into storage stabilizer.");
        this.add("ritual.occultism.craft_stabilizer_tier3.interrupted", "Binding of afrit interrupted.");
        this.add("ritual.occultism.craft_stabilizer_tier4.conditions", "Not all requirements for this ritual are met.");
        this.add("ritual.occultism.craft_stabilizer_tier4.started", "Started binding marid into storage stabilizer.");
        this.add("ritual.occultism.craft_stabilizer_tier4.finished", "Successfully bound marid into storage stabilizer.");
        this.add("ritual.occultism.craft_stabilizer_tier4.interrupted", "Binding of marid interrupted.");
        this.add("ritual.occultism.craft_stable_wormhole.conditions", "Not all requirements for this ritual are met.");
        this.add("ritual.occultism.craft_stable_wormhole.started", "Started binding foliot into wormhole frame.");
        this.add("ritual.occultism.craft_stable_wormhole.finished", "Successfully bound foliot into wormhole frame.");
        this.add("ritual.occultism.craft_stable_wormhole.interrupted", "Binding of foliot interrupted.");
        this.add("ritual.occultism.craft_storage_remote.conditions", "Not all requirements for this ritual are met.");
        this.add("ritual.occultism.craft_storage_remote.started", "Started binding djinni into storage remote.");
        this.add("ritual.occultism.craft_storage_remote.finished", "Successfully bound djinni into storage remote.");
        this.add("ritual.occultism.craft_storage_remote.interrupted", "Binding of djinni interrupted.");
        this.add("ritual.occultism.craft_infused_lenses.conditions", "Not all requirements for this ritual are met.");
        this.add("ritual.occultism.craft_infused_lenses.started", "Started binding foliot into lenses.");
        this.add("ritual.occultism.craft_infused_lenses.finished", "Successfully bound foliot into lenses.");
        this.add("ritual.occultism.craft_infused_lenses.interrupted", "Binding of foliot interrupted.");
        this.add("ritual.occultism.craft_infused_pickaxe.conditions", "Not all requirements for this ritual are met.");
        this.add("ritual.occultism.craft_infused_pickaxe.started", "Started binding djinni into pickaxe.");
        this.add("ritual.occultism.craft_infused_pickaxe.finished", "Successfully bound djinni into pickaxe.");
        this.add("ritual.occultism.craft_infused_pickaxe.interrupted", "Binding of djinni interrupted.");

        this.add("ritual.occultism.craft_miner_foliot_unspecialized.conditions", "Not all requirements for this ritual are met.");
        this.add("ritual.occultism.craft_miner_foliot_unspecialized.started", "Started summoning foliot into magic lamp.");
        this.add("ritual.occultism.craft_miner_foliot_unspecialized.finished", "Successfully summoned foliot into magic lamp.");
        this.add("ritual.occultism.craft_miner_foliot_unspecialized.interrupted", "Summoning of foliot interrupted.");

        this.add("ritual.occultism.craft_miner_djinni_ores.conditions", "Not all requirements for this ritual are met.");
        this.add("ritual.occultism.craft_miner_djinni_ores.started", "Started summoning djinni into magic lamp.");
        this.add("ritual.occultism.craft_miner_djinni_ores.finished", "Successfully summoned djinni into magic lamp.");
        this.add("ritual.occultism.craft_miner_djinni_ores.interrupted", "Summoning of djinni interrupted.");

        this.add("ritual.occultism.craft_miner_afrit_deeps.conditions", "Not all requirements for this ritual are met.");
        this.add("ritual.occultism.craft_miner_afrit_deeps.started", "Started summoning afrit into magic lamp.");
        this.add("ritual.occultism.craft_miner_afrit_deeps.finished", "Successfully summoned afrit into magic lamp.");
        this.add("ritual.occultism.craft_miner_afrit_deeps.interrupted", "Summoning of afrit interrupted.");

        this.add("ritual.occultism.craft_miner_marid_master.conditions", "Not all requirements for this ritual are met.");
        this.add("ritual.occultism.craft_miner_marid_master.started", "Started summoning marid into magic lamp.");
        this.add("ritual.occultism.craft_miner_marid_master.finished", "Successfully summoned marid into magic lamp.");
        this.add("ritual.occultism.craft_miner_marid_master.interrupted", "Summoning of marid interrupted.");

        this.add("ritual.occultism.craft_satchel.conditions", "Not all requirements for this ritual are met.");
        this.add("ritual.occultism.craft_satchel.started", "Started binding foliot into satchel.");
        this.add("ritual.occultism.craft_satchel.finished", "Successfully bound foliot into satchel.");
        this.add("ritual.occultism.craft_satchel.interrupted", "Binding of foliot interrupted.");
        this.add("ritual.occultism.craft_soul_gem.conditions", "Not all requirements for this ritual are met.");
        this.add("ritual.occultism.craft_soul_gem.started", "Started binding djinni into soul gem.");
        this.add("ritual.occultism.craft_soul_gem.finished", "Successfully bound djinni into soul goum.");
        this.add("ritual.occultism.craft_soul_gem.interrupted", "Binding of djinni interrupted.");
        this.add("ritual.occultism.craft_familiar_ring.conditions", "Not all requirements for this ritual are met.");
        this.add("ritual.occultism.craft_familiar_ring.started", "Started binding djinni into familiar ring.");
        this.add("ritual.occultism.craft_familiar_ring.finished", "Successfully bound djinni into familiar ring.");
        this.add("ritual.occultism.craft_familiar_ring.interrupted", "Binding of djinni interrupted.");
        this.add("ritual.occultism.possess_endermite.conditions", "Not all requirements for this ritual are met.");
        this.add("ritual.occultism.possess_endermite.started", "Started summoning possessed endermite.");
        this.add("ritual.occultism.possess_endermite.finished", "Summoned possessed endermite successfully.");
        this.add("ritual.occultism.possess_endermite.interrupted", "Summoning of possessed endermite interrupted.");
        this.add("ritual.occultism.possess_skeleton.conditions", "Not all requirements for this ritual are met.");
        this.add("ritual.occultism.possess_skeleton.started", "Started summoning possessed skeleton.");
        this.add("ritual.occultism.possess_skeleton.finished", "Summoned possessed skeleton successfully.");
        this.add("ritual.occultism.possess_skeleton.interrupted", "Summoning of possessed skeleton interrupted.");
        this.add("ritual.occultism.possess_enderman.conditions", "Not all requirements for this ritual are met.");
        this.add("ritual.occultism.possess_enderman.started", "Started summoning possessed enderman.");
        this.add("ritual.occultism.possess_enderman.finished", "Summoned possessed enderman successfully.");
        this.add("ritual.occultism.possess_enderman.interrupted", "Summoning of possessed enderman interrupted.");
        this.add("ritual.occultism.possess_ghast.conditions", "Not all requirements for this ritual are met.");
        this.add("ritual.occultism.possess_ghast.started", "Started summoning possessed ghast.");
        this.add("ritual.occultism.possess_ghast.finished", "Summoned possessed ghast successfully.");
        this.add("ritual.occultism.possess_ghast.interrupted", "Summoning of possessed ghast interrupted.");
        this.add("ritual.occultism.familiar_otherworld_bird.conditions", "Not all requirements for this ritual are met.");
        this.add("ritual.occultism.familiar_otherworld_bird.started", "Started summoning drikwing familiar.");
        this.add("ritual.occultism.familiar_otherworld_bird.finished", "Summoned drikwing familiar successfully.");
        this.add("ritual.occultism.familiar_otherworld_bird.interrupted", "Summoning of drikwing familiar interrupted.");
        this.add("ritual.occultism.familiar_cthulhu.conditions", "Not all requirements for this ritual are met.");
        this.add("ritual.occultism.familiar_cthulhu.started", "Started summoning cthulhu familiar.");
        this.add("ritual.occultism.familiar_cthulhu.finished", "Summoned cthulhu familiar successfully.");
        this.add("ritual.occultism.familiar_cthulhu.interrupted", "Summoning of cthulhu familiar interrupted.");
        this.add("ritual.occultism.familiar_devil.conditions", "Not all requirements for this ritual are met.");
        this.add("ritual.occultism.familiar_devil.started", "Started summoning devil familiar.");
        this.add("ritual.occultism.familiar_devil.finished", "Summoned devil familiar successfully.");
        this.add("ritual.occultism.familiar_devil.interrupted", "Summoning of devil familiar interrupted.");
        this.add("ritual.occultism.familiar_dragon.conditions", "Not all requirements for this ritual are met.");
        this.add("ritual.occultism.familiar_dragon.started", "Started summoning dragon familiar.");
        this.add("ritual.occultism.familiar_dragon.finished", "Summoned dragon familiar successfully.");
        this.add("ritual.occultism.familiar_dragon.interrupted", "Summoning of dragon familiar interrupted.");
        this.add("ritual.occultism.familiar_blacksmith.conditions", "Not all requirements for this ritual are met.");
        this.add("ritual.occultism.familiar_blacksmith.started", "Started summoning blacksmith familiar.");
        this.add("ritual.occultism.familiar_blacksmith.finished", "Summoned blacksmith familiar successfully.");
        this.add("ritual.occultism.familiar_blacksmith.interrupted", "Summoning of blacksmith familiar interrupted.");
        this.add("ritual.occultism.familiar_guardian.conditions", "Not all requirements for this ritual are met.");
        this.add("ritual.occultism.familiar_guardian.started", "Started summoning guardian familiar.");
        this.add("ritual.occultism.familiar_guardian.finished", "Summoned guardian familiar successfully.");
        this.add("ritual.occultism.familiar_guardian.interrupted", "Summoning of guardian familiar interrupted.");
        this.add("ritual.occultism.summon_wild_otherworld_bird.conditions", "Not all requirements for this ritual are met.");
        this.add("ritual.occultism.summon_wild_otherworld_bird.started", "Started summoning wild drikwing.");
        this.add("ritual.occultism.summon_wild_otherworld_bird.finished", "Summoned wild drikwing successfully.");
        this.add("ritual.occultism.summon_wild_otherworld_bird.interrupted", "Summoning of wild drikwing interrupted.");
        this.add("ritual.occultism.summon_wild_parrot.conditions", "Not all requirements for this ritual are met.");
        this.add("ritual.occultism.summon_wild_parrot.started", "Started summoning wild parrot.");
        this.add("ritual.occultism.summon_wild_parrot.finished", "Summoned wild parrot successfully.");
        this.add("ritual.occultism.summon_wild_parrot.interrupted", "Summoning of wild parrot interrupted.");
        this.add("ritual.occultism.familiar_parrot.conditions", "Not all requirements for this ritual are met.");
        this.add("ritual.occultism.familiar_parrot.started", "Started summoning parrot familiar.");
        this.add("ritual.occultism.familiar_parrot.finished", "Summoned parrot familiar successfully.");
        this.add("ritual.occultism.familiar_parrot.interrupted", "Summoning of parrot familiar interrupted.");
        this.add("ritual.occultism.familiar_greedy.conditions", "Not all requirements for this ritual are met.");
        this.add("ritual.occultism.familiar_greedy.started", "Started summoning greedy familiar.");
        this.add("ritual.occultism.familiar_greedy.finished", "Summoned v familiar successfully.");
        this.add("ritual.occultism.familiar_greedy.interrupted", "Summoning of greedy familiar interrupted.");
        this.add("ritual.occultism.familiar_bat.conditions", "Not all requirements for this ritual are met.");
        this.add("ritual.occultism.familiar_bat.started", "Started summoning bat familiar.");
        this.add("ritual.occultism.familiar_bat.finished", "Summoned bat familiar successfully.");
        this.add("ritual.occultism.familiar_bat.interrupted", "Summoning of bat familiar interrupted.");
        this.add("ritual.occultism.familiar_deer.conditions", "Not all requirements for this ritual are met.");
        this.add("ritual.occultism.familiar_deer.started", "Started summoning deer familiar.");
        this.add("ritual.occultism.familiar_deer.finished", "Summoned deer familiar successfully.");
        this.add("ritual.occultism.familiar_deer.interrupted", "Summoning of deer familiar interrupted.");
        this.add("ritual.occultism.familiar_headless.conditions", "Not all requirements for this ritual are met.");
        this.add("ritual.occultism.familiar_headless.started", "Started summoning headless ratman familiar.");
        this.add("ritual.occultism.familiar_headless.finished", "Summoned headless ratman familiar successfully.");
        this.add("ritual.occultism.familiar_headless.interrupted", "Summoning of headless ratman familiar interrupted.");
        this.add("ritual.occultism.familiar_chimera.conditions", "Not all requirements for this ritual are met.");
        this.add("ritual.occultism.familiar_chimera.started", "Started summoning chimera familiar.");
        this.add("ritual.occultism.familiar_chimera.finished", "Summoned chimera familiar successfully.");
        this.add("ritual.occultism.familiar_chimera.interrupted", "Summoning of chimera familiar interrupted.");
        this.add("ritual.occultism.familiar_beholder.conditions", "Not all requirements for this ritual are met.");
        this.add("ritual.occultism.familiar_beholder.started", "Started summoning beholder familiar.");
        this.add("ritual.occultism.familiar_beholder.finished", "Summoned beholder familiar successfully.");
        this.add("ritual.occultism.familiar_beholder.interrupted", "Summoning of beholder familiar interrupted.");
        this.add("ritual.occultism.familiar_fairy.conditions", "Not all requirements for this ritual are met.");
        this.add("ritual.occultism.familiar_fairy.started", "Started summoning fairy familiar.");
        this.add("ritual.occultism.familiar_fairy.finished", "Summoned fairy familiar successfully.");
        this.add("ritual.occultism.familiar_fairy.interrupted", "Summoning of fairy familiar interrupted.");
        this.add("ritual.occultism.familiar_mummy.conditions", "Not all requirements for this ritual are met.");
        this.add("ritual.occultism.familiar_mummy.started", "Started summoning mummy familiar.");
        this.add("ritual.occultism.familiar_mummy.finished", "Summoned mummy familiar successfully.");
        this.add("ritual.occultism.familiar_mummy.interrupted", "Summoning of mummy familiar interrupted.");
        this.add("ritual.occultism.familiar_beaver.conditions", "Not all requirements for this ritual are met.");
        this.add("ritual.occultism.familiar_beaver.started", "Started summoning beaver familiar.");
        this.add("ritual.occultism.familiar_beaver.finished", "Summoned beaver familiar successfully.");
        this.add("ritual.occultism.familiar_beaver.interrupted", "Summoning of beaver familiar interrupted.");
    }

    private void addBook() {
        var helper = ModonomiconAPI.get().getLangHelper(Occultism.MODID);
        helper.book("dictionary_of_spirits");
        this.add(helper.bookName(), "Dictionary of Spirits");
        this.add(helper.bookTooltip(), """
                This book aims to introduce the novice reader to the most common summoning rituals and equip them with a list of spirits and their names.
                The authors advise caution in the summoning of the listed entities.
                For help or to give feedback please join us in Discord https://invite.gg/klikli.
                """);

        this.addGettingStartedCategory(helper);
        this.addGettingStartedCategoryPart2(helper);
        this.addSpiritsCategory(helper);
        this.addPentaclesCategory(helper);
        this.addRitualsCategory(helper);
        this.addSummoningRitualsCategory(helper);
        this.addCraftingRitualsCategory(helper);
        this.addPossessionRitualsCategory(helper);
        this.addFamiliarRitualsCategory(helper);
        this.addStorageCategory(helper);
    }

    private void addGettingStartedCategory(BookLangHelper helper) {
        helper.category("getting_started");
        this.add(helper.categoryName(), "Getting Started");

        helper.entry("intro");
        this.add(helper.entryName(), "Disclaimer!");
        this.add(helper.entryDescription(), "About using the Dictionary of Spirits");

        helper.page("intro");
        this.add(helper.pageTitle(), "Important Information");
        this.add(helper.pageText(),
                """
                        Occultism is transitioning from Patchouli to Modonomicon as in-game documentation.
                        \\
                        \\
                        Currently only the "getting started" section is available in Modonomicon, for all other content you need to refer back to the Patchouli book
                        titled "Dictionary of Spirits (Old Edition)".
                        """);

        helper.page("intro2");
        this.add(helper.pageText(),
                """
                        For now, to look up recipes, pentacle shapes, ritual information and basically everything after "getting started", please open the Old Edition.
                        \\
                        \\
                        Over time more and more content will be available directly here in the Modonomicon version.
                        """);

        helper.page("recipe");
        this.add(helper.pageText(),
                """
                        To obtain the old edition, simply craft this book with [](item://occultism:datura_seeds) in a crafting grid. (The book new edition will not be destroyed.)
                        """);

        helper.entry("demons_dream");
        this.add(helper.entryName(), "Lifting the Veil");
        this.add(helper.entryDescription(), "Learn about the Otherworld and the Third Eye.");

        helper.page("intro");
        this.add(helper.pageTitle(), "The Otherworld");
        this.add(helper.pageText(),
                """
                        Hidden from mere human eyes exists another plane of existence, another *dimension* if you will, the [#](%1$s)Otherworld[#]().
                        This world is populated with entities often referred to as [#](%1$s)Demons[#]().
                        """.formatted(COLOR_PURPLE));

        helper.page("intro2");
        this.add(helper.pageText(),
                """
                        These Demons possess a wide variety of powers and useful skills, and for centuries magicians have sought to summon them for their own gain.
                        The first step on the journey to successfully summoning such an Entity is to learn how to interact with the Otherworld.
                        """);

        helper.page("spotlight");
        this.add(helper.pageText(),
                """
                        %1$s is a herb that gives humans the [#](%2$s)Third Eye[#](),
                        allowing them to see where the [#](%2$s)Otherworld[#]() intersects with our own.
                        Seeds can be found **by breaking grass**.
                        **Consuming** the grown fruit activates the ability.
                        """.formatted(DEMONS_DREAM, COLOR_PURPLE));

        helper.page("harvest_effect");
        this.add(helper.pageText(),
                """
                        An additional side effect of %1$s, is **the ability to interact with [#](%2$s)Otherworld[#]() materials**.
                        This is unique to %1$s, other ways to obtain [#](%2$s)Third Eye[#]() do not yield this ability.
                        While under the effect of %1$s you are able to **harvest** Otherstone as well as Otherworld trees.
                         """.formatted(DEMONS_DREAM, COLOR_PURPLE));


        helper.page("datura_screenshot"); //no tex
        helper.page("note_on_spirit_fire");
        this.add(helper.pageText(),
                """
                        **Hint**: The otherworld materials you obtain by harvesting under the effects of[#](%2$s)Third Eye[#]() **can be obtained more easily using [](item://occultism:spirit_fire)**. Proceed with the next entry in this book to learn more about spirit fire.
                         """.formatted(DEMONS_DREAM, COLOR_PURPLE));

        helper.entry("spirit_fire");
        this.add(helper.entryName(), "It burns!");
        this.add(helper.entryDescription(), "Or does it?");

        helper.page("spotlight");
        this.add(helper.pageText(),
                """
                        [#](%1$s)Spiritfire[#]() is a special type of fire that exists mostly in [#](%1$s)The Other Place[#]()
                        and does not harm living beings. Its special properties allow to use it to purify and convert
                        certain materials by burning them, without consuming them.
                        """.formatted(COLOR_PURPLE));


        helper.page("spirit_fire_screenshot");
        this.add(helper.pageText(),
                """
                        Throw [](item://occultism:datura) to the ground and light it on fire with [](item://minecraft:flint_and_steel).
                         """);


        helper.page("main_uses");
        this.add(helper.pageText(),
                """
                        The main uses of [](item://occultism:spirit_fire) are to convert [](item://minecraft:diamond) into [](item://occultism:spirit_attuned_gem),
                        to get basic ingredients such as [](item://occultism:otherstone) and [Otherworld Saplings](item://occultism:otherworld_sapling_natural),
                        and to purify impure chalks.
                         """);

        helper.page("otherstone_recipe");
        this.add(helper.pageText(),
                """
                        An easier way to obtain [](item://occultism:otherstone) than via divination.
                              """);


        helper.page("otherworld_sapling_natural_recipe");
        this.add(helper.pageText(),
                """
                        An easier way to obtain [Otherworld Saplings](item://occultism:otherworld_sapling_natural) than via divination.
                              """);

        helper.entry("third_eye");
        this.add(helper.entryName(), "The Third Eye");
        this.add(helper.entryDescription(), "Do you see now?");

        helper.page("about");
        this.add(helper.pageTitle(), "Third Eye");
        this.add(helper.pageText(),
                """
                        The ability to see beyond the physical world is referred to as [#](%1$s)Third Eye[#]().
                        Humans do not possess such an ability to see [#](%1$s)beyond the veil[#](),
                        however with certain substances and contraptions the knowledgeable summoner can work around this limitation.
                         """.formatted(COLOR_PURPLE));

        helper.page("how_to_obtain");
        this.add(helper.pageText(),
                """
                        The most comfortable, and most *expensive*, way to obtain this ability, is to wear spectacles
                        infused with spirits, that *lend* their sight to the wearer.
                        A slightly more nauseating, but **very affordable** alternative is the consumption of certain herbs,
                        [%1$s](entry://occultism:dictionary_of_spirits/getting_started/demons_dream) most prominent among them.
                         """.formatted(DEMONS_DREAM));

        helper.page("otherworld_goggles");
        this.add(helper.pageText(),
                """
                        [These goggles](entry://occultism:dictionary_of_spirits/crafting_rituals/craft_otherworld_goggles) allow to see even more hidden Otherworld blocks,
                        however they do not allow harvesting those materials.
                        Low-tier materials can be harvested by consuming [%1$s](entry://occultism:dictionary_of_spirits/getting_started/demons_dream),
                        but more valuable materials require special tools.
                                """.formatted(DEMONS_DREAM));


        helper.entry("divination_rod");
        this.add(helper.entryName(), "Divination Rod");
        this.add(helper.entryDescription(), "Obtaining otherworld materials");

        helper.page("intro");
        this.add(helper.pageTitle(), "Divination");
        this.add(helper.pageText(),
                """
                        To make it easier to get started, the materials obtained by divination now also have crafting recipes.
                        **If you want the full experience, skip the following recipe page and move on to the
                        [divination instructions](entry://occultism:dictionary_of_spirits/getting_started/divination_rod@divination_instructions).**
                                """);


        helper.page("otherstone_recipe");
        //no text

        helper.page("otherworld_sapling_natural_recipe");
        this.add(helper.pageText(),
                """
                        **Beware**: the tree growing from the sapling will look like a normal oak tree.
                        You need to activate the [Third Eye](entry://occultism:dictionary_of_spirits/getting_started/demons_dream)
                        to harvest the Otherworld Logs and Leaves.
                                """);

        helper.page("divination_rod");
        this.add(helper.pageText(),
                """
                        Otherworld materials play an important role in interacting with spirits.
                        As they are rare and not visible to the naked eye, finding them requires special tools.
                        The divination rod allows to find Otherworld materials based on their similarities to materials common to our world.
                                 """);

        helper.page("about_divination_rod");
        this.add(helper.pageText(),
                """
                        The divination rod uses a spirit attuned gem attached to a wooden rod.
                        The gem resonates with the chosen material, and this movement is amplified by the wooden rod,
                        allowing to detect nearby Otherworld materials.   \s
                           \s
                           \s
                        The rod works by detecting resonance between real world and Otherworld materials.
                        Attuned the rod to a real world material, and it will find the corresponding Otherworld block.
                                 """);

        helper.page("how_to_use");
        this.add(helper.pageTitle(), "Use of the Rod");
        this.add(helper.pageText(),
                """
                        [#](%1$s)Shift-right-click[#]() a block to attune the rod to the corresponding Otherworld block.
                        - [](item://minecraft:andesite): [](item://occultism:otherstone)
                        - [](item://minecraft:oak_wood):  [](item://occultism:otherworld_log)
                        - [](item://minecraft:oak_leaves): [](item://occultism:otherworld_leaves)
                        - [](item://minecraft:netherrack): [](item://occultism:iesnium_ore)

                        Then [#](%1$s)right-click[#]() and hold until the rod animation finishes.""".formatted(COLOR_PURPLE));

        helper.page("how_to_use2");
        this.add(helper.pageText(),
                """
                        After the animation finishes, the closest **found block will be highlighted
                        with white lines and can be seen through other blocks**.
                        Additionally you can watch the crystals for hints: a white crystal indicates no target blocks found,
                        a fully purple block means the found block is nearby. Mixes between white and purple show
                        that the target is rather far away.""");

        helper.page("divination_rod_screenshots");
        this.add(helper.pageText(),
                """
                        White means nothing was found.
                        The more purple you see, the closer you are.
                        """);

        helper.page("otherworld_groves");
        this.add(helper.pageTitle(), "Otherworld Groves");
        this.add(helper.pageText(),
                """
                        Otherworld Groves are lush, overgrown caves, with [#](%1$s)Otherworld Trees[#](),
                        and walls of [](item://occultism:otherstone), and represent the fastest way to get everything one
                        needs to get set up as a summoner.
                        To find them, attune your divination rod to Otherworld leaves
                        or logs, as unlike Otherstone, they only spawn in these groves.
                         """.formatted(COLOR_PURPLE));

        helper.page("otherworld_groves_2");
        this.add(helper.pageText(),
                """
                        **Hint:** In the Overworld, look **down**.
                          """);

        helper.page("otherworld_trees");
        this.add(helper.pageTitle(), "Otherworld Trees");
        this.add(helper.pageText(),
                """
                        Otherworld trees grow naturally in Otherworld Groves. To the naked eye they appear as oak trees,
                        but to the Third Eye they reveal their true nature.   \s
                        **Important:** Otherworld Saplings can only be obtained by breaking the leaves manually, naturally only oak saplings drop.
                         """);

        helper.page("otherworld_trees_2");
        this.add(helper.pageText(),
                """
                        Trees grown from Stable Otherworld Saplings as obtained from spirit traders do not have that limitation.
                         """);

        helper.entry("candle");
        this.add(helper.entryName(), "Candles");
        this.add(helper.entryDescription(), "Let there be light!");

        helper.page("white_candle");
        this.add(helper.pageText(),
                """
                        Candles provide stability to rituals and are an important part of almost all pentacles.
                        **Candles also act like bookshelves for enchantment purposes.**
                        \\
                        \\
                        Candles from Minecraft and other Mods may be used in place of Occultism candles.
                            """);

        helper.page("tallow");
        this.add(helper.pageText(),
                """
                        Key ingredient for candles. Kill large animals like pigs, cows or sheep with a [](item://occultism:butcher_knife)
                        to harvest [](item://occultism:tallow).
                            """);


        helper.page("white_candle_recipe");
        //no text

        helper.entry("ritual_prep_chalk");
        this.add(helper.entryName(), "Ritual Preparations: Chalks");
        this.add(helper.entryDescription(), "Signs to find them, Signs to bring them all, and in the darkness bind them.");

        helper.page("intro");
        this.add(helper.pageTitle(), "Ritual Preparations: Chalks");
        this.add(helper.pageText(),
                """
                        To summon spirits from the [#](%1$s)Other Place[#]() in *relative* safety,
                        you need to draw a fitting pentacle using chalk to contain their powers.
                         """.formatted(COLOR_PURPLE));

        helper.page("white_chalk");
        this.add(helper.pageText(),
                """
                        White chalk is used to draw the most basic pentacles, such as for our first ritual.
                        \\
                        \\
                        More powerful summonings require appropriate more advanced chalk, see [Chalks](entry://occultism:dictionary_of_spirits/getting_started/chalks) for more information.
                            """);

        helper.page("burnt_otherstone_recipe");
        //no text

        helper.page("otherworld_ashes_recipe");
        //no text

        helper.page("impure_white_chalk_recipe");
        //no text

        helper.page("white_chalk_recipe");
        //no text

        helper.page("usage");
        this.add(helper.pageTitle(), "Usage");
        this.add(helper.pageText(),
                """
                        Right-click on a block with the chalk to draw a single glyph. For decorative purposes you can repeatedly click a block to cycle through glyphs. The shown glyph does not matter for the ritual, only the color.
                         """.formatted(COLOR_PURPLE));

        helper.entry("ritual_prep_bowl");
        this.add(helper.entryName(), "Ritual Preparations: Sacrificial Bowls");
        this.add(helper.entryDescription(), "There is no power without sacrifice.");

        helper.page("sacrificial_bowl");
        this.add(helper.pageTitle(), "Ritual Preparations: Sacrificial Bowls");
        this.add(helper.pageText(),
                """
                        These bowls are used to place the items we will sacrifice as part of a ritual and you will need a handful of them.
                        Note: Their exact placement in the ritual does not matter - just keep them within 8 blocks of the pentacle center!
                             """);

        helper.page("sacrificial_bowl_recipe");
        //no text

        helper.page("golden_sacrificial_bowl");
        this.add(helper.pageText(),
                """
                        Once everything has been set up and you are ready to start, this special sacrificial bowl is used to activate the ritual by [#](%1$s)right-clicking[#]() it with the activation item,
                        usually a [Book of Binding](entry://getting_started/books_of_binding).
                             """.formatted(COLOR_PURPLE));


        helper.page("golden_sacrificial_bowl_recipe");
        //no text

        helper.entry("books_of_binding");
        this.add(helper.entryName(), "Books of Binding");
        this.add(helper.entryDescription(), "Or how to identify your spirit");

        helper.page("intro");
        this.add(helper.pageTitle(), "Books of Binding");
        this.add(helper.pageText(),
                """
                        To call forth a spirit, a [#](%1$s)Book of Binding[#]() must be used in the ritual.
                        There is a type of book corresponding to each type (or tier) of spirit.
                        To identify a spirit to summon, it's name must be written in the [#](%1$s)Book of Binding[#](), resulting in a [#](%1$s)Bound Book of Binding[#]() that can be used in the ritual.
                         """.formatted(COLOR_PURPLE));

        helper.page("intro2");
        this.add(helper.pageText(),
                """
                        **Note:** *The spirit names are eye candy only*, that means they are not relevant for the recipe. As long as you have the right spirit type in your book of binding it can be used.
                         """.formatted(COLOR_PURPLE));

        helper.page("purified_ink_recipe");
        this.add(helper.pageText(),
                """
                        In order to craft [#](%1$s)Books of Binding[#]() to summon spirits, you need purified ink. Simply drop any black dye into [](item://occultism:spirit_fire) to purify it.
                            """.formatted(COLOR_PURPLE));

        helper.page("book_of_binding_foliot_recipe");
        this.add(helper.pageText(),
                """
                        Craft a book of binding that will be used to call forth a [#](%1$s)Foliot[#]() spirit.
                           """.formatted(COLOR_PURPLE));

        helper.page("book_of_binding_bound_foliot_recipe");
        this.add(helper.pageText(),
                """
                        Add the name of the spirit to summon to your book of binding by crafting it with the Dictionary of Spirits. The Dictionary will not be used up.
                           """);

        helper.page("book_of_binding_djinni_recipe");
        //no text

        helper.page("book_of_binding_afrit_recipe");
        //no text

        helper.page("book_of_binding_marid_recipe");
        //no text

        helper.entry("first_ritual");
        this.add(helper.entryName(), "First Ritual");
        this.add(helper.entryDescription(), "We're actually getting started now!");

        helper.page("intro");
        this.add(helper.pageTitle(), "The Ritual (tm)");
        this.add(helper.pageText(),
                """
                        These pages will walk the gentle reader through the process of the [first ritual](entry://summoning_rituals/summon_crusher_t1) step by step.
                        \\
                        We **start** by placing the [](item://occultism:golden_sacrificial_bowl) and drawing the appropriate pentacle, [Aviar's Circle](entry://pentacles/summon_foliot) as seen on the left around it.
                          """.formatted(COLOR_PURPLE));

        helper.page("multiblock");
        this.add(helper.pageText(),
                """
                        Only the color and location of the chalk marks is relevant, not the glyph/sign.
                          """.formatted(COLOR_PURPLE));

        helper.page("bowl_text");
        this.add(helper.pageTitle(), "Sacrificial Bowls");
        this.add(helper.pageText(),
                """
                        Next, place *at least* 4 [Sacrificial Bowls](item://occultism:sacrificial_bowl) close to the pentacle.
                        \\
                        \\
                        They must be placed **anywhere** within 8 blocks of the central [](item://occultism:golden_sacrificial_bowl). **The exact location does not matter.**
                          """.formatted(COLOR_PURPLE));


        helper.page("bowl_placement");
        this.add(helper.pageText(),
                """
                        Possible locations for the sacrificial bowls.
                          """.formatted(COLOR_PURPLE));


        helper.page("ritual_text");
        this.add(helper.pageTitle(), "Placing Ingredients");
        this.add(helper.pageText(),
                """
                        Now it is time to place the ingredients you see on the next page in the (regular, not golden) sacrificial bowls. The ingredients will be consumed from the bowls as the ritual progresses.
                          """.formatted(COLOR_PURPLE));

        helper.page("ritual_recipe");
        //no text

        helper.page("start_ritual");
        this.add(helper.pageTitle(), "Let there be ... spirits!");
        this.add(helper.pageText(),
                """
                        Finally, [#](%1$s)right-click[#]() the [](item://occultism:golden_sacrificial_bowl) with the **bound** book of binding you created before and wait until the crusher spawns.
                        \\
                        \\
                        Now all that remains is to drop appropriate ores near the crusher and wait for it to turn it into dust.
                          """.formatted(COLOR_PURPLE));

        helper.entry("brush");
        this.add(helper.entryName(), "Brush");
        this.add(helper.entryDescription(), "Cleaning up!");

        helper.page("intro");
        this.add(helper.pageTitle(), "Next Steps");
        this.add(helper.pageText(),
                """
                        Chalk is a pain to clean up, by [#](%1$s)right-clicking[#]() with a brush you can remove it from the world much more easily.
                          """.formatted(COLOR_PURPLE));

        helper.page("brushRecipe");
        //no text

        helper.entry("more_rituals");
        this.add(helper.entryName(), "More Rituals");
        this.add(helper.entryDescription(), "Ready for new challenges?");

        helper.entry("grey_particles");
        this.add(helper.entryName(), "Grey particles?");
        this.add(helper.entryDescription(), "What to do when a ritual seems stuck!");

        helper.page("text");
        this.add(helper.pageTitle(), "Ritual stuck?");
        this.add(helper.pageText(),
                """
                        If a ritual appears stuck - no items being consumed - you should see grey particles around the [](item://occultism:golden_sacrificial_bowl). If this is the case the ritual requires you to either [use a specific item](entry://rituals/item_use) or [sacrifice a specific mob](entry://rituals/sacrifice).
                        \\
                        \\
                        Find the ritual in the [Rituals](category://rituals) category and check for instructions.
                          """);

        helper.entry("books_of_calling");
        this.add(helper.entryName(), "Books of Calling");
        this.add(helper.entryDescription(), "Telling your spirits what to do");

        helper.page("intro");
        this.add(helper.pageTitle(), "Books of Calling");
        this.add(helper.pageText(),
                """
                        Books of Calling allow to control a summoned spirit, and to store it to prevent essence decay or move it more easily. 
                        \\
                        \\
                        Only spirits that require precise instructions - such as a work area or drop-off storage - come with a book of calling.
                        """);

        helper.page("usage");
        this.add(helper.pageTitle(), "Usage");
        this.add(helper.pageText(),
                """
                        - [#](%1$s)Right-click[#]() air to open the configuration screen
                        - [#](%1$s)Shift-right-click[#]() a block to apply the action selected in the configuration screen
                        - [#](%1$s)Shift-right-click[#]() a spirit to capture it (must be of the same type)
                        - [#](%1$s)Right-click[#]() with a book with a captured spirit to release it
                        """.formatted(COLOR_PURPLE));

        helper.page("obtaining");
        this.add(helper.pageTitle(), "How to obtain Books of Calling");
        this.add(helper.pageText(),
                """
                        If a summoned spirit supports the use of a Book of Calling, the summoning ritual will automatically spawn a book in the world alongside the spirit.
                        \\
                        \\
                        If you **lose the book**, there are also crafting recipes that just provide the book (without summoning a spirit).
                        """.formatted(COLOR_PURPLE));

        helper.page("obtaining2");
        this.add(helper.pageText(),
                """
                        The recipes can be found in this book or via JEI.
                        \\
                        \\
                        [#](%1$s)Shift-right-click[#]() the spirit with the crafted book to assign it.
                        """.formatted(COLOR_PURPLE));

        helper.page("storage");
        this.add(helper.pageTitle(), "Storing Spirits");
        this.add(helper.pageText(),
                """
                        To store spirits that do not have a fitting book of calling, you can use a [Soul Gem](entry://crafting_rituals/craft_soul_gem).
                        Soul gems are much more versatile and allow to store almost all types of entities even animals and monsters, but not players or bosses.
                        """);

        helper.entry("spirits");
        this.add(helper.entryName(), "About Spirits");
        this.add(helper.entryDescription(), "Learn more about Spirits.");

        helper.entry("infused_pickaxe");
        this.add(helper.entryName(), "Infused Pickaxe");
        this.add(helper.entryDescription(), "Tackling Otherworld Ores");

        helper.page("spotlight");
        this.add(helper.pageText(),
                """
                        Beyond [](item://occultism:otherworld_log) and [](item://occultism:otherstone) there are also otherworld materials that require special tools to harvest. 
                        \\
                        \\
                        This pickaxe is rather brittle, but it will do the job.
                        """);

        helper.page("gem_recipe");
        this.add(helper.pageText(),
                """
                        These gems, when infused with a spirit, can be used to interact with Otherword materials and are the key to crafting the pickaxe.
                        """);

        helper.page("head_recipe");
        //no text

        helper.page("crafting");
        this.add(helper.pageTitle(), "Crafting");
        this.add(helper.pageText(),
                """
                        After preparing the raw materials, the pickaxe needs to be infused with a spirit.
                        \\
                        \\
                        Follow the instructions at [Craft Infuse Pickaxe](entry://crafting_rituals/craft_infused_pickaxe)
                        """.formatted(COLOR_PURPLE));

        helper.entry("iesnium");
        this.add(helper.entryName(), "Iesnium Ore");
        this.add(helper.entryDescription(), "Myterious metals ...");

        helper.page("spotlight");
        this.add(helper.pageText(),
                """
                   This is a rare metal that, to the naked eye, looks like [](item://minecraft:netherrack) and cannot be mined with a regular pickaxe.
                   \\
                   \\
                   When mined with the correct tools, it can be used to craft powerful items (you will learn more about that later).
                        """.formatted(COLOR_PURPLE));

        helper.page("where");
        this.add(helper.pageTitle(), "Where to find it");
        this.add(helper.pageText(),
                """
                   Like Netherrack, Iesnium can be found in the Nether. In order to **see** it, you need to wear [Otherworld Goggles](entry://getting_started/otherworld_goggles).
                   \\
                   \\
                   To make searching for it simpler, attune a [Divination Rod](entry://getting_started/divination_rod) to it and righ-click and hold in the nether until it highlights a nearby block, which will hold the ore.
                        """.formatted(COLOR_PURPLE));

        helper.page("how");
        this.add(helper.pageTitle(), "How to mine it");
        this.add(helper.pageText(),
                """
                   Iesnium can only be mined with the [Infused Pickaxe](entry://getting_started/infused_pickaxe) or an [](item://occultism:iesnium_pickaxe) (about which you will learn later).
                   \\
                   \\
                   After identifying a block that holds Iesnium, you can mine it with the pickaxe you created in the previous step.
                        """.formatted(COLOR_PURPLE));

        helper.page("processing");
        this.add(helper.pageTitle(), "Processing");
        this.add(helper.pageText(),
                """
                   Iesnium Ore, after mining, can be smelted directly into ingots, or placed down. When placed, it will not turn back into it's netherrack form. Consequently it can also be mined with any pickaxe then. This visible form of the Ore, when mined, will drop [](item://occultism:raw_iesnium).
                        """.formatted(COLOR_PURPLE));

        helper.page("uses");
        this.add(helper.pageTitle(), "Uses");
        this.add(helper.pageText(),
                """
                   Iesnium can be used to craft an improved pickaxe, spirit lamps, and other powerful items. Follow the progress in this book to learn more about it.
                        """.formatted(COLOR_PURPLE));

        helper.entry("iesnium_pickaxe");
        this.add(helper.entryName(), "Iesnium Pickaxe");
        this.add(helper.entryDescription(), "A more durable otherworld-appropriate pickaxe");

        helper.page("spotlight");
        this.add(helper.pageText(),
                """
                   Like the [Infused Pickaxe](entry://getting_started/infused_pickaxe), this pickaxe can be used to mine Tier 2 Otherworld Materials such as [](item://occultism:iesnium_ore). As it is made from metal, instead of brittle [](item://occultism:spirit_attuned_gem), it is very durable and can be used for a long time.
                        """.formatted(COLOR_PURPLE));

        helper.page("crafting");
        //no text

        helper.entry("magic_lamps");
        this.add(helper.entryName(), "Magic Lamps");
        this.add(helper.entryDescription(), "Three wishes? Close, but not quite..");

        helper.page("spotlight");
        this.add(helper.pageTitle(), "Magic Lamps");
        this.add(helper.pageText(),
                """
                   Magic Lamps can be used to keep spirits safe from [#](%1$s)Essence Decay[#](), while still having access to some of their powers. Most commonly they are used to access a [#](%1$s)Mining Dimension[#]() and act as (*lag free*) [#](%1$s)Void Miners[#]().
                        """.formatted(COLOR_PURPLE));

        helper.page("crafting");
        //no text

        helper.entry("spirit_miners");
        this.add(helper.entryName(), "Spirit Miners");
        this.add(helper.entryDescription(), "It's Free Real Estate (-> Resources)");

        helper.page("spotlight");
        this.add(helper.pageTitle(), "Spirit Miners");
        this.add(helper.pageText(),
                """
                   By summoning a spirit into a Magic Lamp and placing it in a [Dimensional Mineshaft (see next step)](entry://getting_started/mineshaft) it can be made to mine for you in a [#](%1$s)Mining Dimension[#](). This is a great way to get resources without having to go mining in the overworld (or other dimesions) yourself.
                        """.formatted(COLOR_PURPLE));

        helper.page("crafting");
        this.add(helper.pageTitle(), "Crafting");
        this.add(helper.pageText(),
                """
                   See [Foliot Miner](entry://crafting_rituals/craft_foliot_miner) and the subsequent entries for information on how to craft spirit miners.
                        """.formatted(COLOR_PURPLE));

        helper.entry("mineshaft");
        this.add(helper.entryName(), "Dimensional Mineshaft");
        this.add(helper.entryDescription(), "Ethically questionable, but very profitable");

        helper.page("spotlight");
        this.add(helper.pageText(),
                """
                   This block acts as a portal, for spirits only, to the [#](%1$s)Mining Dimension[#](). Place a Magic Lamp with a Miner Spirit in it, to make it mine for you.
                        """.formatted(COLOR_PURPLE));

        helper.page("crafting");
        this.add(helper.pageTitle(), "Crafting");
        this.add(helper.pageText(),
                """
                   See [Dimensional Mineshaft](entry://crafting_rituals/craft_dimensional_mineshaft) in the [Binding Rituals](category://crafting_rituals) Category.
                        """.formatted(COLOR_PURPLE));

        helper.entry("storage");
        this.add(helper.entryName(), "Magic Storage");
        this.add(helper.entryDescription(), "Looking for much much much more storage? Look no further!");

        helper.entry("possession_rituals");
        this.add(helper.entryName(), "Possession Rituals");
        this.add(helper.entryDescription(), "A different way to get rare drops ...");

        helper.page("intro");
        this.add(helper.pageTitle(), "Possession Rituals");
        this.add(helper.pageText(),
                """
                   Possessed mobs are controlled by spirits, allowing the summoner to determine some of their properties. They usually have **high drop rates** for rare drops, but are generally harder to kill.
                   \\
                   \\
                   You probably will want to start by summoning a [Possessed Endermite](entry://possession_rituals/possess_endermite) to get [](item://minecraft:end_stone) to craft [Advanced Chalks](entry://getting_started/chalks).
                        """.formatted(COLOR_PURPLE));

        helper.page("more");
        this.add(helper.pageTitle(), "More Information");
        this.add(helper.pageText(),
                """
                   To find out more about Possession Rituals, see the [Possession Rituals](category://possession_rituals) Category.
                        """.formatted(COLOR_PURPLE));

        helper.entry("familiar_rituals");
        this.add(helper.entryName(), "Familiar Rituals");
        this.add(helper.entryDescription(), "Personal helpers that provide buffs or fight for you");

        helper.page("intro");
        this.add(helper.pageTitle(), "Familiar Rituals");
        this.add(helper.pageText(),
                """
                   Familiars provide a variety of bonus effects, such as feather falling, water breathing, jump boosts and more, and may also assist you in combat.
                   \\
                   \\
                   Store them in a [Familiar Ring](entry://crafting_rituals/craft_familiar_ring) to equip them as a curio.
                        """.formatted(COLOR_PURPLE));

        helper.page("more");
        this.add(helper.pageTitle(), "More Information");
        this.add(helper.pageText(),
                """
                   To find more about Familiars, see the [Familiar Rituals](category://familiar_rituals) Category.
                        """.formatted(COLOR_PURPLE));

        helper.entry("summoning_rituals");
        this.add(helper.entryName(), "Summoning Rituals");
        this.add(helper.entryDescription(), "Spirit helpers for your daily work life");

        helper.page("intro");
        this.add(helper.pageTitle(), "Summoning Rituals");
        this.add(helper.pageText(),
                """
                   Summoning Rituals allow you to summon spirits to work for you. Unlike familiars, they are not personally bound to you, meaning they will not follow you around, but they will perform various work tasks for you. In fact the first ritual you performed, the [Foliot Crusher](entry://getting_started/first_ritual), was a summoning ritual.
                        """.formatted(COLOR_PURPLE));


        helper.page("more");
        this.add(helper.pageTitle(), "More Information");
        this.add(helper.pageText(),
                """
                   To find more about Summoning Rituals, see the [Summoning Rituals](category://summoning_rituals) Category.
                        """.formatted(COLOR_PURPLE));

        helper.entry("crafting_rituals");
        this.add(helper.entryName(), "Infusion Rituals");
        this.add(helper.entryDescription(), "Infuse spirits into items to create powerful tools");

        helper.page("intro");
        this.add(helper.pageTitle(), "Infusion Rituals");
        this.add(helper.pageText(),
                """
                   Infusion rituals are all about crafting powerful items, by binding ("infusing") spirits into objects.The spirits will provide special functionality to the items.
                        """.formatted(COLOR_PURPLE));

        helper.page("more");
        this.add(helper.pageTitle(), "More Information");
        this.add(helper.pageText(),
                """
                   To find more about Infusing items, see the [Infusion Rituals](category://crafting_rituals) Category.
                        """.formatted(COLOR_PURPLE));
    }

    private void addSpiritsCategory(BookLangHelper helper) {
        helper.category("spirits");
        this.add(helper.categoryName(), "Pentacles");

        helper.entry("return_to_getting_started");
        this.add(helper.entryName(), "Return to getting started");

        helper.entry("overview");
        this.add(helper.entryName(), "On Spirits");

        helper.page("intro");
        this.add(helper.pageTitle(), "On Spirits");
        this.add(helper.pageText(),
                """
                        [#](%1$s)Spirit[#](), commonly referred to also as [#](%1$s)Demon[#](), is a general term for a variety of supernatural entities usually residing in [#](%1$s)The Other Place[#](), a plane of existence entirely separate from our own.
                        """.formatted(COLOR_PURPLE));

        helper.page("shapes");
        this.add(helper.pageTitle(), "Shapes");
        this.add(helper.pageText(),
                """
                        When in our world Spirits can take a variety of forms, by morphing their essence into [#](%1$s)Chosen Forms[#](). Alternatively, they can inhabit objects or even living beings.
                          """.formatted(COLOR_PURPLE));

        helper.page("tiers");
        this.add(helper.pageTitle(), "Types of Spirits");
        this.add(helper.pageText(),
                """
                        There are four major "ranks" of spirits identified by researchers, but there are a myriad spirits below and in between these ranks, and some great entities of terrible power, referred to only as [#](%1$s)Greater Spirits[#](), that are beyond classification.
                         """.formatted(COLOR_PURPLE));

        helper.page("foliot");
        this.add(helper.pageTitle(), "Foliot");
        this.add(helper.pageText(),
                """
                        The lowest identified class of spirit. Equipped with some intelligence and a modicum of power they are most often used for manual labor or minor artifacts.
                         """.formatted(COLOR_PURPLE));

        helper.page("djinni");
        this.add(helper.pageTitle(), "Djinni");
        this.add(helper.pageText(),
                """
                        The most commonly summoned class. There is a great variety of Djinni, differing both in intelligence and power. Djinni can be used for a variety of task, ranging from higher artifacts over possession of living beings to carrying out tasks in their Chosen Form.
                         """.formatted(COLOR_PURPLE));

        helper.page("afrit");
        this.add(helper.pageTitle(), "Afrit");
        this.add(helper.pageText(),
                """
                        Even more powerful than Djinni, Afrit are used for the creation of major artifacts and the possession of powerful beings.
                         """.formatted(COLOR_PURPLE));

        helper.page("marid");
        this.add(helper.pageTitle(), "Marid");
        this.add(helper.pageText(),
                """
                        The strongest identified class of spirits. Due to their power and vast intellect attempting a summoning is extremely dangerous and usually only carried out by the most experienced summoners, and even then usually in groups.
                         """.formatted(COLOR_PURPLE));

        helper.page("greater_spirits");
        this.add(helper.pageTitle(), "Greater Spirits");
        this.add(helper.pageText(),
                """
                        Spirits of power so great it is beyond measure. No summons have been attempted in living memory, and records of summonings in ancient times are mostly considered apocryphal.
                         """.formatted(COLOR_PURPLE));

        helper.entry("true_names");
        this.add(helper.entryName(), "True Names");
        this.add(helper.entryDescription(), "How to call spirits.");

        helper.page("intro");
        this.add(helper.pageTitle(), "True Names");
        this.add(helper.pageText(),
                """
                        To summon a spirit the magician needs to know their [#](%1$s)True Name[#](). By calling the true naming during the summoning ritual the Spirit is drawn forth from [#](%1$s)The Other Place[#]() and forced to do the summoners bidding.
                        \\
                        \\
                        *It should be noted, that it does not matter which spirit name is used in summoning, only the spirit tier is relevant.*
                         """.formatted(COLOR_PURPLE));

        helper.page("finding_names");
        this.add(helper.pageTitle(), "Finding Names");
        this.add(helper.pageText(),
                """
                        In ancient summoners had to research and experiment to find [#](%1$s)True Names[#](). Some spirits can be convinced to share their knowledge of true names of other demons, either by promising a swift return to [#](%1$s)The Other Place[#](), or by more ... *persuasive* measures.
                        """.formatted(COLOR_PURPLE));


        helper.page("using_names");
        this.add(helper.pageTitle(), "Using Names to Summon a Spirit");
        this.add(helper.pageText(),
                """
                        For your convenience, in this work you will find the known names of spirits of all 4 ranks, as well as some beyond that. To summon a spirit, copy their name from this book into the appropriate book of binding, then use this bound book of binding to activate a ritual.
                         """.formatted(COLOR_PURPLE));

        helper.entry("essence_decay");
        this.add(helper.entryName(), "Essence Decay");
        this.add(helper.entryDescription(), "Even the immortal are not immune to time.");

        helper.page("intro");
        this.add(helper.pageTitle(), "Essence Decay");
        this.add(helper.pageText(),
                """
                        When residing in our plane of existence, spirits experience [#](%1$s)Essence Decay[#](), the slow rot of their "body". The more powerful the spirit, the slower the decay, but only the most powerful can stop it entirely. Once fully decayed they are returned to [#](%1$s)The Other Place[#]() and can only be re-summoned once fully recovered.
                        """.formatted(COLOR_PURPLE));

        helper.page("countermeasures");
        this.add(helper.pageTitle(), "Countermeasures");
        this.add(helper.pageText(),
                """
                        The summoner can slow or even stop essence decay by binding the spirit into an object, or summoning it into a living being. Additionally the pentacle used can influence the effects of essence decay to a degree.
                        """.formatted(COLOR_PURPLE));


        helper.page("affected_spirits");
        this.add(helper.pageTitle(), "Affected Spirits");
        this.add(helper.pageText(),
                """
                        Only tier 1 spirits are affected by essence decay, by default. All higher tiers are immune and will not despawn. Modpacks may modify this behaviour.
                              """.formatted(COLOR_PURPLE));

        helper.entry("unbound_spirits");
        this.add(helper.entryName(), "Unbound Spirits");
        this.add(helper.entryDescription(), "Try not to lose your spirits!");

        helper.page("intro");
        this.add(helper.pageTitle(), "Unbound Spirits");
        this.add(helper.pageText(),
                """
                        Generally spirits are summoned [#](%1$s)bound[#](), which refers to any condition that keeps them under control of the summoner. A side effect of binding spells is that part of the spirit remains in [#](%1$s)The Other Place[#](), robbing them of large portions of the power, but at the same time also protecting their essence from foreign access in this world.
                        """.formatted(COLOR_PURPLE));

        helper.page("unbound");
        this.add(helper.pageTitle(), "Forego the Leash");
        this.add(helper.pageText(),
                """
                        In order to access a spirit's essence, or unleash it's full destructive power, it needs to be summoned [#](%1$s)unbound[#](). Unbound summonings use pentacles that are intentionally incomplete or unstable, allowing to call on the spirit, but not putting any constraints on it.
                        """.formatted(COLOR_PURPLE));

        helper.page("unbound2");
        this.add(helper.pageTitle(), "Beware!");
        this.add(helper.pageText(),
                """
                        The lack of restraints when summoning spirits unbound makes these rituals incredibly dangerous, but you may find that the rewards are worth the risk - and often there is no way around them to achieve certain results.
                        """.formatted(COLOR_PURPLE));

        helper.page("essence");
        this.add(helper.pageTitle(), "Spirit Essence");
        this.add(helper.pageText(),
                """
                        Unbound summonings are the only way to obtain [Afrit Essence](entry://summoning_rituals/afrit_essence), a powerful substance required for crafting [](item://occultism:chalk_red) which is used for the most powerful binding pentacles.
                        """.formatted(COLOR_PURPLE));

        helper.entry("wild_hunt");
        this.add(helper.entryName(), "The Wild Hunt");
        this.add(helper.entryDescription(), "You better watch out, you better not cry ...");

        helper.page("intro");
        this.add(helper.pageTitle(), "The Wild Hunt");
        this.add(helper.pageText(),
                """
                        A group of legendary Greater Spirits, usually appearing in the form of wither skeletons, with their skeleton minions. The Greater Spirits are bound to their minions in such fashion that they are virtually invulnerable until their minions have been sent back to [#](%1$s)The Other Place[#]().
                        """.formatted(COLOR_PURPLE));

        helper.page("wither_skull");
        this.add(helper.pageTitle(), "Wither Skeleton Skulls");
        this.add(helper.pageText(),
                """
                        While it is incredibly dangerous to call on the Wild Hunt, some summoners have been known to do so for quick access to the rare wither skeleton skulls they are known to leave behind. Summoning the wild hunt is described in detail on the page on obtaining [Wither Skeleton Skulls](entry://summoning_rituals/wither_skull).
                        """.formatted(COLOR_PURPLE));

    }

    private void addPentaclesCategory(BookLangHelper helper) {
        helper.category("pentacles");
        this.add(helper.categoryName(), "Pentacles");

        helper.entry("pentacles_overview");
        this.add(helper.entryName(), "On Pentacles");

        helper.page("intro1");
        this.add(helper.pageTitle(), "On Pentacles");
        this.add(helper.pageText(),
                """
                        The name [#](%1$s)Pentacle[#]() in this context refers to ritual drawings of any shape, not just five-pointed stars. \\
                        \\
                        Pentacles are used to summon and bind spirits from [#](%1$s)The Other Place[#]().
                        """.formatted(COLOR_PURPLE));

        helper.page("intro2");
        this.add(helper.pageText(),
                """
                        They act both as a device to call on the entity, an amplifier for the summoner's commanding power
                        and as a protecting circle preventing attacks from within against the summoner.
                        """.formatted(COLOR_PURPLE));

        helper.page("intro3");
        this.add(helper.pageText(),
                """
                        Each pentacle consists of a central golden sacrificial bowl, surrounding runes of various colors and occult paraphernalia that improve the intended effect in various ways.
                        """.formatted(COLOR_PURPLE));


        helper.page("intro4");
        this.add(helper.pageText(),
                """
                        The combination of chosen runes and supporting items as well as their exact spatial positioning determines the use and effectiveness of the pentacle.
                        \\
                        \\
                        Ingredients are placed in [#](%1$s)Sacrifical Bowls[#]() near the pentacle.
                         """.formatted(COLOR_PURPLE));

        //exact copy found in first ritual entry
        helper.page("bowl_placement");
        //no text

        //exact copy found in first ritual entry
        helper.page("bowl_text");
        this.add(helper.pageText(),
                """
                        [Sacrificial Bowls](item://occultism:sacrificial_bowl) must be placed **anywhere** within 8 blocks of the central [](item://occultism:golden_sacrificial_bowl). The exact location does not matter.
                        \\
                        \\
                        Now it is time to place the ingredients you see on the next page in the (regular, not golden) sacrificial bowls.
                          """);

        helper.page("summoning_pentacles");
        this.add(helper.pageTitle(), "Summoning Pentacles");
        this.add(helper.pageText(),
                """
                        The purpose of this type of pentacle is to summon spirits in their chosen form into the world. Spirits summoned thus suffer from strong essence decay, and only very powerful spirits can remain for extended periods of time.
                        """);

        helper.page("infusion_pentacles");
        this.add(helper.pageTitle(), "Infusion Pentacles");
        this.add(helper.pageText(),
                """
                        Infusion pentacles allow the binding of spirits into objects. While the spirits suffer from essence decay in some cases, this can often be averted with the right pentacle setup, and by embedding crystals and precious metals into the object to support the spirit.
                        """);

        helper.page("possession_pentacles");
        this.add(helper.pageTitle(), "Possession Pentacles");
        this.add(helper.pageText(),
                """
                        These pentacles force spirits to possess a living being, which, depending on the ritual context, gives the summoner control over various aspects of that being, ranging from it's strength to it what it drops when killed, and in some cases even allows total control.
                        """);

        helper.entry("paraphernalia");
        this.add(helper.entryName(), "Occult Paraphernalia");

        helper.page("intro");
        this.add(helper.pageTitle(), "Occult Paraphernalia");
        this.add(helper.pageText(),
                """
                        In addition to runes various occult paraphernalia are used to improve the intended effect of the pentacle.
                        """);

        helper.page("candle");
        this.add(helper.pageText(),
                """
                        Candles increase the stability of the pentacle, thus allowing a slowed essence decay of the summoned spirit, leading to a longer lifetime of the spirit, or possessed object or being.
                        """);

        helper.page("crystal");
        this.add(helper.pageText(),
                """
                        Crystals increase the binding power of the pentacle, allowing a permanent binding of the spirit into an item or living being.
                        """);

        helper.page("gem_recipe");
        //no text

        helper.page("crystal_recipe");
        //no text

        helper.page("skeleton_skull");
        this.add(helper.pageText(),
                """
                        Skulls increase the calling power of the pentacle, allowing to summon more dangerous spirits.
                        """);

        helper.entry("chalk_uses");
        this.add(helper.entryName(), "Chalk Types");

        helper.page("intro");
        this.add(helper.pageTitle(), "Chalk Types");
        this.add(helper.pageText(),
                """
                        Chalk is used to draw pentacle runes and define the pentacle shape. Different types of chalk are used for different purposes, as outlined on the next pages.
                        \\
                        \\
                        The different runes are purely decorative.
                        """);

        helper.page("intro2");
        this.add(helper.pageText(),
                """
                        **Repeatedly** use chalk on a block to switch to a different rune.
                        \\
                        \\
                        Using a [](item://occultism:brush) is the easiest way to **remove** chalk runes that have been incorrectly placed.
                        """);

        helper.page("white_chalk");
        this.add(helper.pageText(),
                """
                        White chalk is the most basic type of ritual chalk and is found in most pentacles. It has no special power beyond defining the shape of the pentacle.
                           """);


        helper.page("white_chalk_uses");
        this.add(helper.pageTitle(), "White Chalk Uses");
        this.add(helper.pageText(),
                """
                        - [Aviar's Circle](entry://occultism:dictionary_of_spirits/pentacles/summon_foliot)
                        - [Eziveus' Spectral Compulsion](entry://occultism:dictionary_of_spirits/pentacles/craft_foliot)
                        - [Hedyrin's Lure](entry://occultism:dictionary_of_spirits/pentacles/possess_foliot)
                        - [Ophyx' Calling](entry://occultism:dictionary_of_spirits/pentacles/summon_djinni)
                        - [Strigeor's Higher Binding](entry://occultism:dictionary_of_spirits/pentacles/craft_djinni)
                        - [Ihagan's Enthrallment](entry://occultism:dictionary_of_spirits/pentacles/possess_djinni)
                        - [Abras' Conjure](entry://occultism:dictionary_of_spirits/pentacles/summon_afrit)
                        - [Sevira's Permanent Confinement](entry://occultism:dictionary_of_spirits/pentacles/craft_afrit)
                        - [Abras' Open Conjure](entry://occultism:dictionary_of_spirits/pentacles/summon_wild_afrit)
                         """);

        helper.page("white_chalk_uses2");
        this.add(helper.pageTitle(), "White Chalk Uses");
        this.add(helper.pageText(),
                """
                        - [Uphyxes Inverted Tower](entry://occultism:dictionary_of_spirits/pentacles/craft_marid)
                        - [Osorin' Unbound Calling](entry://occultism:dictionary_of_spirits/pentacles/summon_wild_greater_spirit)
                         """);

        helper.page("golden_chalk");
        this.add(helper.pageText(),
                """
                        Golden chalk is used for binding runes, which allow to infuse a spirit into an item, or make it possess a living being.
                               """);


        helper.page("golden_chalk_uses");
        this.add(helper.pageTitle(), "Golden Chalk Uses");
        this.add(helper.pageText(),
                """
                        - [Eziveus' Spectral Compulsion](entry://occultism:dictionary_of_spirits/pentacles/craft_foliot)
                        - [Hedyrin's Lure](entry://occultism:dictionary_of_spirits/pentacles/possess_foliot)
                        - [Strigeor's Higher Binding](entry://occultism:dictionary_of_spirits/pentacles/craft_djinni)
                        - [Ihagan's Enthrallment](entry://occultism:dictionary_of_spirits/pentacles/possess_djinni)
                        - [Sevira's Permanent Confinement](entry://occultism:dictionary_of_spirits/pentacles/craft_afrit)
                        - [Uphyxes Inverted Tower](entry://occultism:dictionary_of_spirits/pentacles/craft_marid)
                           """);

        helper.page("purple_chalk");
        this.add(helper.pageText(),
                """
                        Purple chalk is generally used to call on higher beings such as [#](%1$s)Djinn[#]() or [#](%1$s)Afrit[#](), but also slows essence decay of summoned spirits.
                               """.formatted(COLOR_PURPLE));

        helper.page("purple_chalk_uses");
        this.add(helper.pageTitle(), "Purple Chalk Uses");
        this.add(helper.pageText(),
                """
                        - [Ophyx' Calling](entry://occultism:dictionary_of_spirits/pentacles/summon_djinni)
                        - [Strigeor's Higher Binding](entry://occultism:dictionary_of_spirits/pentacles/craft_djinni)
                        - [Ihagan's Enthrallment](entry://occultism:dictionary_of_spirits/pentacles/possess_djinni)
                        - [Abras' Conjure](entry://occultism:dictionary_of_spirits/pentacles/summon_afrit)
                        - [Sevira's Permanent Confinement](entry://occultism:dictionary_of_spirits/pentacles/craft_afrit)
                        - [Abras' Open Conjure](entry://occultism:dictionary_of_spirits/pentacles/summon_wild_afrit)
                        - [Osorin' Unbound Calling](entry://occultism:dictionary_of_spirits/pentacles/summon_wild_greater_spirit)
                           """);

        helper.page("red_chalk");
        this.add(helper.pageText(),
                """
                        Red chalk is used to call on the most powerful and dangerous beings, such as [#](%1$s)Marid[#]().
                        \\
                        \\
                        [Afrit Essence](entry://occultism:dictionary_of_spirits/summoning_rituals/afrit_essence) is required to craft red chalk.
                                """.formatted(COLOR_PURPLE));

        helper.page("red_chalk_uses");
        this.add(helper.pageTitle(), "Red Chalk Uses");
        this.add(helper.pageText(),
                """
                        - [Abras' Conjure](entry://occultism:dictionary_of_spirits/pentacles/summon_afrit)
                        - [Uphyxes Inverted Tower](entry://occultism:dictionary_of_spirits/pentacles/craft_marid)
                           """);


        helper.entry("summon_foliot");
        this.add(helper.entryName(), "Aviar's Circle");

        helper.page("intro");
        this.add(helper.pageTitle(), "Aviar's Circle");
        this.add(helper.pageText(),
                """
                        **Purpose:** Summon a [#](%1$s)Foliot[#]()
                        \\
                        \\
                        Considered by most to be the simplest pentacle, [#](%1$s)Aviar's Circle[#]() is easy to set up, but provides only a minimum of binding power and protection for the summoner.
                        \\
                        \\
                        Only the weakest [#](%1$s)Foliot[#]() can be summoned in rituals using this pentacle.
                        """.formatted(COLOR_PURPLE));

        helper.page("multiblock");
        //no text

        helper.page("uses");
        this.add(helper.pageTitle(), "Uses");
        this.add(helper.pageText(),
                """
                        - [Foliot Crusher](entry://summoning_rituals/summon_crusher_t1)
                        - [Foliot Lumberjack](entry://summoning_rituals/summon_lumberjack)
                        - [Foliot Transporter](entry://summoning_rituals/summon_transport_items)
                        - [Foliot Janitor](entry://summoning_rituals/summon_cleaner)
                        - [Otherstone Trader](entry://summoning_rituals/summon_otherstone_trader)
                        - [Otherworld Sapling Trader](entry://summoning_rituals/summon_otherworld_sapling_trader)
                        """);

        helper.entry("summon_djinni");
        this.add(helper.entryName(), "Ophyx' Calling");

        helper.page("intro");
        this.add(helper.pageTitle(), "Ophyx' Calling");
        //Add link rituals/possession/possess_skeleton instead of [Obtain here]
        this.add(helper.pageText(),
                """
                        **Purpose:** Summon a [#](%1$s)Djinni[#]()
                        \\
                        \\
                        Developed by [#](%1$s)Ophyx[#]() during the Third Era, the [#](%1$s)Calling[#]() is the go-to pentacle for [#](%1$s)Djinni[#]() summonings ever since. Skeleton skulls ([Obtain here](entry://possession_rituals/possess_skeleton)) and [#](%1$s)Purple Chalk[#]() provide the calling power required to force Djinni into appearance and candles stabilize the ritual.
                         """.formatted(COLOR_PURPLE));

        helper.page("multiblock");
        //no text

        helper.page("uses");
        this.add(helper.pageTitle(), "Uses");
        this.add(helper.pageText(),
                """
                        - [Djinni Machine Operator](entry://summoning_rituals/summon_manage_machine)
                        - [Djinni Crusher](entry://summoning_rituals/summon_crusher_t2)
                        - [Clear Weather](entry://summoning_rituals/weather_magic@clear)
                        - [Time Magic](entry://summoning_rituals/time_magic)
                        """.formatted(COLOR_PURPLE));

        helper.entry("summon_afrit");
        this.add(helper.entryName(), "Abras' Conjure");
        helper.page("intro");
        this.add(helper.pageTitle(), "Abras' Conjure");
        this.add(helper.pageText(),
                """
                        **Purpose:** Summon an [#](%1$s)Afrit[#]()
                        \\
                        \\
                        **Abras' Conjure** is one of the few pentacles capable of (mostly) safely summoning an [#](%1$s)Afrit[#](). While the requirement of a wither skeleton skull makes it comparatively expensive, the additional calling potential is required to reach these high-power spirits.
                         """.formatted(COLOR_PURPLE));

        helper.page("multiblock");
        //no text

        helper.page("uses");
        this.add(helper.pageTitle(), "Uses");
        this.add(helper.pageText(),
                """
                        - [Thunderstorm](entry://summoning_rituals/weather_magic@thunder)
                        - [Rainy Weather](entry://summoning_rituals/weather_magic@rain)
                        - [Afrit Crusher](entry://summoning_rituals/summon_crusher_t3)
                        """.formatted(COLOR_PURPLE));

        helper.entry("summon_marid");
        this.add(helper.entryName(), "Fatma's Incentivized Attraction");
        helper.page("intro");
        this.add(helper.pageTitle(), "Fatma's Incentivized Attraction");
        this.add(helper.pageText(),
                """
                        **Purpose:** Summon a [#](%1$s)Marid[#]()
                        \\
                        \\
                        **Fatma's Incentivized Attraction** is a powerful pentacle, allowing to summon [#](%1$s)Marid[#]() and bind them to the summoner's will.
                         """.formatted(COLOR_PURPLE));

        helper.page("multiblock");
        //no text

        helper.page("uses");
        this.add(helper.pageTitle(), "Uses");
        this.add(helper.pageText(),
                """
                        - [Marid Crusher](entry://summoning_rituals/summon_crusher_t4)
                        """.formatted(COLOR_PURPLE));

        helper.entry("summon_wild_afrit");
        this.add(helper.entryName(), "Abras' Open Conjure");
        helper.page("intro");
        this.add(helper.pageTitle(), "Abras' Open Conjure");
        this.add(helper.pageText(),
                """
                        **Purpose:** Summon an unbound [#](%1$s)Afrit[#]()
                        \\
                        \\
                        **Abras' Open Conjure** is a simplified version of [#](%1$s)Abras' Conjure[#](), allowing to summon [#](%1$s)Afrit[#]() without red chalk. Due to the much reduced power of the pentacle, it cannot be used to control [#](%1$s)Afrit[#](), and it thus can only be used to fight and kill [#](%1$s)Afrit[#]().
                         """.formatted(COLOR_PURPLE));

        helper.page("multiblock");
        //no text

        helper.page("uses");
        this.add(helper.pageTitle(), "Uses");
        this.add(helper.pageText(),
                """
                        - [Afrit Essence](entry://summoning_rituals/afrit_essence)
                        """.formatted(COLOR_PURPLE));

        helper.entry("summon_wild_greater_spirit");
        this.add(helper.entryName(), "Osorin's Unbound Calling");
        helper.page("intro");
        this.add(helper.pageTitle(), "Osorin's Unbound Calling");
        this.add(helper.pageText(),
                """
                        **Purpose:** Summon an unbound [#](%1$s)Greater Spirit[#]()
                        \\
                        \\
                        **Osorin's Unbound Calling** is based on [#](%1$s)Abras' Open Conjure[#](), but features none of the stabilizing paraphernalia. The pentacle offers no protection whatsoever to the summoner, but acts as an irresistible call to [#](%1$s)Greater Spirits[#]().
                        """.formatted(COLOR_PURPLE));

        helper.page("multiblock");
        //no text

        helper.page("uses");
        this.add(helper.pageTitle(), "Uses");
        this.add(helper.pageText(),
                """
                        - [Wither Skeleton Skull](entry://summoning_rituals/wither_skull)
                        """.formatted(COLOR_PURPLE));

        helper.entry("possess_foliot");
        this.add(helper.entryName(), "Hedyrin's Lure");
        helper.page("intro");
        this.add(helper.pageTitle(), "Hedyrin's Lure");
        this.add(helper.pageText(),
                """
                        **Purpose:** Foliot Possession
                        \\
                        \\
                        **Hedyrin's Lure** attracts [#](%1$s)Foliot[#]() and forces them to possess a nearby creature. This pentacle does not lead to permanent imprisonment, the spirit and possessed creature will perish within a short period of time.
                         """.formatted(COLOR_PURPLE));

        helper.page("multiblock");
        //no text

        helper.page("uses");
        this.add(helper.pageTitle(), "Uses");
        this.add(helper.pageText(),
                """
                        - [Possessed Endermite](entry://possession_rituals/possess_endermite)
                        - [Possessed Skeleton](entry://possession_rituals/possess_skeleton)
                        - [Parrot Familiar](entry://familiar_rituals/familiar_parrot)
                        - [Greedy Familiar](entry://familiar_rituals/familiar_greedy)
                        - [Deer Familiar](entry://familiar_rituals/familiar_deer)
                        - [Blacksmith Familiar](entry://familiar_rituals/familiar_blacksmith)
                        - [Beaver Familiar](entry://familiar_rituals/familiar_beaver)
                        """.formatted(COLOR_PURPLE));

        helper.entry("possess_djinni");
        this.add(helper.entryName(), "Ihagan's Enthrallment");
        helper.page("intro");
        this.add(helper.pageTitle(), "Ihagan's Enthrallment");
        this.add(helper.pageText(),
                """
                        **Purpose:** Djinni Possession
                        \\
                        \\
                        **Ihagan's Enthrallment** forces [#](%1$s)Djinn[#]() to possess a nearby creature. This pentacle does not lead to permanent imprisonment, the spirit and possessed creature will perish within a short period of time.
                         """.formatted(COLOR_PURPLE));

        helper.page("multiblock");
        //no text

        helper.page("uses");
        this.add(helper.pageTitle(), "Uses");
        this.add(helper.pageText(),
                """
                        - [Possessed Enderman](entry://possession_rituals/possess_enderman)
                        - [Possessed Ghast](entry://possession_rituals/possess_ghast)
                        - [Drikwing Familiar](entry://familiar_rituals/familiar_otherworld_bird)
                        - [Bat Familiar](entry://familiar_rituals/familiar_bat)
                        - [Cthulhu Familiar](entry://familiar_rituals/familiar_cthulhu)
                        - [Devil Familiar](entry://familiar_rituals/familiar_devil)
                        - [Dragon Familiar](entry://familiar_rituals/familiar_dragon)
                        - [Headless Ratman Familiar](entry://familiar_rituals/familiar_headless)
                        - [Chimera Familiar](entry://familiar_rituals/familiar_chimera)
                        - [Beholder Familiar](entry://familiar_rituals/familiar_beholder)
                        """.formatted(COLOR_PURPLE));

        helper.page("uses2");
        this.add(helper.pageTitle(), "Uses");
        this.add(helper.pageText(),
                """
                        - [Fairy Familiar](entry://familiar_rituals/familiar_fairy)
                        """.formatted(COLOR_PURPLE));

        helper.entry("possess_afrit");
        this.add(helper.entryName(), "Abras' Commanding Conjure");
        helper.page("intro");
        this.add(helper.pageTitle(), "Abras' Commanding Conjure");
        this.add(helper.pageText(),
                """
                        **Purpose:** Afrit Possession
                        \\
                        \\
                        **Abras' Commanding Conjure** is a modified version of [#](%1$s)Abras' Conjure[#]() that allows possessing entities, and thus summoning familiars.
                         """.formatted(COLOR_PURPLE));

        helper.page("multiblock");
        //no text

        helper.page("uses");
        this.add(helper.pageTitle(), "Uses");
        this.add(helper.pageText(),
                """
                        - [Guardian Familiar](entry://familiar_rituals/familiar_guardian)
                        """.formatted(COLOR_PURPLE));

        helper.entry("craft_foliot");
        this.add(helper.entryName(), "Eziveus' Spectral Compulsion");
        helper.page("intro");
        this.add(helper.pageTitle(), "Eziveus' Spectral Compulsion");
        this.add(helper.pageText(),
                """
                        **Purpose:** Bind Foliot
                        \\
                        \\
                        As a simple binding pentacle, **Eziveus' Spectral Compulsion** is a common starting point for object infusion with lower spirits. The enchantment is made permanent by stabilizing candles and spirit attuned crystals.
                         """.formatted(COLOR_PURPLE));

        helper.page("multiblock");
        //no text

        helper.page("uses");
        this.add(helper.pageTitle(), "Uses");
        this.add(helper.pageText(),
                """
                        - [Infused Lenses](entry://crafting_rituals/craft_otherworld_goggles)
                        - [Surprisingsly Substantial Satchel](entry://crafting_rituals/craft_satchel)
                        - [Storage Actuator Base](entry://crafting_rituals/craft_storage_controller_base)
                        - [Stable Wormhole](entry://crafting_rituals/craft_stable_wormhole)
                        - [Storage Stabilizer Tier 1](entry://crafting_rituals/craft_stabilizer_tier1)
                        - [Foliot Miner](entry://crafting_rituals/craft_foliot_miner)
                        """.formatted(COLOR_PURPLE));

        helper.entry("craft_djinni");
        this.add(helper.entryName(), "Strigeor's Higher Binding");
        helper.page("intro");
        this.add(helper.pageTitle(), "Strigeor's Higher Binding");
        this.add(helper.pageText(),
                """
                        **Purpose:** Bind Djinni
                        \\
                        \\
                        **Strigeor's Higher Binding** is a pentacle for binding [#](%1$s)Djinn[#]() into objects, should not be attempted by the novice summoner. Supported by spirit attuned crystals and stabilized by candles it is highly suitable for permanent infusions of objects with spirits.
                         """.formatted(COLOR_PURPLE));

        helper.page("multiblock");
        //no text

        helper.page("uses");
        this.add(helper.pageTitle(), "Uses");
        this.add(helper.pageText(),
                """
                        - [Infused Pickaxe](entry://crafting_rituals/craft_infused_pickaxe)
                        - [Soul Gem](entry://crafting_rituals/craft_soul_gem)
                        - [Familiar Ring](entry://crafting_rituals/craft_familiar_ring)
                        - [Dimensional Matrix](entry://crafting_rituals/craft_dimensional_matrix)
                        - [Storage Accessor](entry://crafting_rituals/craft_storage_remote)
                        - [Storage Stabilizer Tier 2](entry://crafting_rituals/craft_stabilizer_tier2)
                        - [Dimensional Mineshaft](entry://crafting_rituals/craft_dimensional_mineshaft)
                        - [Djinni Ore Miner](entry://crafting_rituals/craft_djinni_miner)
                        """.formatted(COLOR_PURPLE));

        helper.entry("craft_afrit");
        this.add(helper.entryName(), "Sevira's Permanent Confinement");
        helper.page("intro");
        this.add(helper.pageTitle(), "Sevira's Permanent Confinement");
        this.add(helper.pageText(),
                """
                        **Purpose:** Bind Afrit
                        \\
                        \\
                        First discovered by Grandmistress Sevira of Emberwoods, **Sevira's Permanent Confinement** is used for binding [#](%1$s)Afrit[#]() into objects. Due to the power of the spirits involved, this should be performed only by advanced summoners.
                         """.formatted(COLOR_PURPLE));

        helper.page("multiblock");
        //no text

        helper.page("uses");
        this.add(helper.pageTitle(), "Uses");
        this.add(helper.pageText(),
                """
                        - [Storage Stabilizer Tier 3](entry://crafting_rituals/craft_stabilizer_tier3)
                        - [Afrit Deep Ore Miner](entry://crafting_rituals/craft_afrit_miner)
                        """.formatted(COLOR_PURPLE));

        helper.entry("craft_marid");
        this.add(helper.entryName(), "Uphyxes Inverted Tower");
        helper.page("intro");
        this.add(helper.pageTitle(), "Uphyxes Inverted Tower");
        this.add(helper.pageText(),
                """
                        **Purpose:** Bind Marid
                        \\
                        \\
                        **Uphyxes Inverted Tower** is one of the few pentacles capable of binding [#](%1$s)Marid[#]() into objects. Any rituals involving [#](%1$s)Marid[#]() should be performed only by the most experienced summoners.
                         """.formatted(COLOR_PURPLE));

        helper.page("multiblock");
        //no text

        helper.page("uses");
        this.add(helper.pageTitle(), "Uses");
        this.add(helper.pageText(),
                """
                        - [Storage Stabilizer Tier 4](entry://crafting_rituals/craft_stabilizer_tier4)
                        - [Marid Master Miner](entry://crafting_rituals/craft_marid_miner)
                        """.formatted(COLOR_PURPLE));
    }

    private void addGettingStartedCategoryPart2(BookLangHelper helper) {
        //no call to helper.category, because we're still in getting started
        helper.entry("chalks");
        this.add(helper.entryName(), "More Chalks");
        this.add(helper.entryDescription(), "Better chalks for better rituals!");

        helper.page("intro");
        this.add(helper.pageTitle(), "More Chalks");
        this.add(helper.pageText(),
                """
                        For more advanced rituals the basic [White Chalk](entry://occultism:dictionary_of_spirits/getting_started/ritual_prep_chalk) is not sufficient. Instead chalks made from more arcane materials are required.
                        """);


        helper.page("gold_chalk_recipe");
        //no text

        helper.page("impure_purple_chalk_recipe");
        this.add(helper.pageText(),
                """
                   You do not need to visit the [#](%1$s)The End[#]() to obtain Endstone. You can summon a [Possessed Endermite](entry://possession_rituals/possess_endermite) which has a high chance to drop it.
                        """.formatted(COLOR_PURPLE));
        helper.page("purple_chalk_recipe");
        //no text

        helper.page("impure_red_chalk_recipe");
        //no text

        helper.page("red_chalk_recipe");
        //no text

        helper.page("afrit_essence");
        this.add(helper.pageText(),
                """
                        To obtain the essence of an [#](%1$s)Afrit[#]() for [](item://occultism:chalk_red) you need to [summon and kill an Unbound Afrit](entry://summoning_rituals/afrit_essence).
                        """.formatted(COLOR_PURPLE));

        helper.entry("otherworld_goggles");
        this.add(helper.entryName(), "Otherworld Goggles");
        this.add(helper.entryDescription(), "Say no to drugs!");

        helper.page("spotlight");
        this.add(helper.pageText(),
                """
                        The [](item://occultism:otherworld_goggles) are what advanced summoners use to see the [#](%1$s)Otherworld[#](), to avoid the negative side effects of [](entry://occultism:dictionary_of_spirits/getting_started/demons_dream).
                        \\
                        \\
                        Making your first pair of these is seen by many as a rite of passage.
                        """.formatted(COLOR_PURPLE));

        helper.page("crafting");
        this.add(helper.pageTitle(), "Crafting Goggles");
        this.add(helper.pageText(),
                """
                        Crafting these goggles is a multi-step process described in detail in the Entry about [Crafting Otherworld Goggles](entry://crafting_rituals/craft_otherworld_goggles).
                        """.formatted(COLOR_PURPLE));
    }

    private void addRitualsCategory(BookLangHelper helper) {
        helper.category("rituals");
        this.add(helper.categoryName(), "Rituals");

        helper.entry("overview");
        this.add(helper.entryName(), "Rituals");

        helper.page("intro");
        this.add(helper.pageTitle(), "Rituals");
        this.add(helper.pageText(),
                """
                        Rituals allow to summon spirits into our plane of existence, or bind them into objects or living beings. Every ritual consists of a [#](%1$s)Pentacle[#](), [#](%1$s)Ritual Ingredients[#]() provided via sacrificial bowls, a [#](%1$s)Starting Item[#]() and optionally the [#](%1$s)Sacrifice[#]() of living beings. A purple particle effect will show that the ritual is successful and in progress.
                        """.formatted(COLOR_PURPLE));

        helper.page("steps");
        this.add(helper.pageTitle(), "Performing a Ritual");
        this.add(helper.pageText(),
                """
                        Rituals always follow the same steps:
                        - Draw the pentacle.
                        - Place a golden bowl.
                        - Place sacrificial bowls.
                        - Put ingredients in bowls.
                        - [#](%1$s)Right-click[#]()the golden bowl with the activation item.
                        - *Optional: Perform a sacrifice close to the center of the pentacle.*
                        """.formatted(COLOR_PURPLE));

        helper.page("additional_requirements");
        this.add(helper.pageTitle(), "Additional Requirements");
        this.add(helper.pageText(),
                """
                        If a ritual shows grey particles above the golden sacrificial bowl, then additional requirements as described in the ritual's page need to be fulfilled. Once all requirements are fulfilled, the ritual will show purple particles and start to consume the items in the sacrificial bowls.
                        """);

        helper.entry("item_use");
        this.add(helper.entryName(), "Item Use");

        helper.page("intro");
        this.add(helper.pageTitle(), "Item Use");
        this.add(helper.pageText(),
                """
                        Some rituals require the use of certain items to be performed. Use the item described on the ritual's page within **16 blocks** of the [](item://occultism:golden_sacrificial_bowl) to proceed with the ritual.
                        \\
                        \\
                        **Important:** Before using the item, start the ritual. Grey particles indicate that the ritual is ready for the item use.
                        """);

        helper.entry("sacrifice");
        this.add(helper.entryName(), "Sacrifices");

        helper.page("intro");
        this.add(helper.pageTitle(), "Sacrifices");
        this.add(helper.pageText(),
                """
                        Some rituals require the sacrifice of a living being to provide the necessary energy to summon the spirit. Sacrifices are described on the ritual's page under the "Sacrifice" subheading. To perform a sacrifice, kill an animal within 8 blocks of the golden sacrificial bowl. Only kills by players count as sacrifice!
                         """);

        helper.entry("summoning_rituals");
        this.add(helper.entryName(), "Summoning Rituals");

        helper.entry("possession_rituals");
        this.add(helper.entryName(), "Possession Rituals");

        helper.entry("crafting_rituals");
        this.add(helper.entryName(), "Binding Rituals");

        helper.entry("familiar_rituals");
        this.add(helper.entryName(), "Familiar Rituals");
    }

    private void addSummoningRitualsCategory(BookLangHelper helper) {
        helper.category("summoning_rituals");
        this.add(helper.categoryName(), "Summoning Rituals");

        helper.entry("overview");
        this.add(helper.entryName(), "Summoning Rituals");

        helper.page("intro");
        this.add(helper.pageTitle(), "Summoning Rituals");
        this.add(helper.pageText(),
                """
                        Summon rituals force spirits to enter this world in their chosen shape, leading to little restrictions on their power, but expose them to essence decay. Summoned spirits range from trade spirits that trade and convert items, to slave-like helpers for manual labour.
                         """);

        helper.entry("return_to_rituals");
        this.add(helper.entryName(), "Return to Rituals Category");

        helper.entry("summon_crusher_t1");
        this.add(helper.entryName(), "Summon Foliot Crusher");

        helper.page("about_crushers");
        this.add(helper.pageTitle(), "Crusher Spirits");
        this.add(helper.pageText(),
                """
                        Crusher spirits are summoned to crush ores into dusts, effectively multiplying the metal output. They will pick up appropriate ores and drop the resulting dusts into the world. A purple particle effect and a crushing sound indicate the crusher is at work.
                          """);

        helper.page("automation");
        this.add(helper.pageText(),
                """
                        To ease automation, try summoning a [Transporter Spirit](entry://occultism:dictionary_of_spirits/summoning_rituals/summon_transport_items)
                        to place items from chests in the crusher's inventory, and a [Janitor Spirit](entry://occultism:dictionary_of_spirits/summoning_rituals/summon_cleaner) to collect the processed items.
                         """);

        helper.page("intro");
        this.add(helper.pageTitle(), "Foliot Crusher");
        this.add(helper.pageText(),
                """
                        The foliot crusher is the most basic crusher spirit.
                        \\
                        \\
                        It will crush **one** ore into **two** corresponding dusts.
                         """);

        helper.page("ritual");
        //no text

        helper.entry("summon_crusher_t2");
        this.add(helper.entryName(), "Summon Djinni Crusher");

        helper.page("intro");
        this.add(helper.pageTitle(), "Djinni Crusher");
        this.add(helper.pageText(),
                """
                        The djinni crusher is resistant essence decay and faster and more efficient than the foliot crusher.
                        \\
                        \\
                        It will crush **one** ore into **three** corresponding dusts.
                         """);

        helper.page("ritual");
        //no text

        helper.entry("summon_crusher_t3");
        this.add(helper.entryName(), "Summon Afrit Crusher");

        helper.page("intro");
        this.add(helper.pageTitle(), "Afrit Crusher");
        this.add(helper.pageText(),
                """
                        The afrit crusher is resistant to essence decay and faster and more efficient than the djinni crusher.
                        \\
                        \\
                        It will crush **one** ore into **four** corresponding dusts.
                          """);

        helper.page("ritual");
        //no text

        helper.entry("summon_crusher_t4");
        this.add(helper.entryName(), "Summon Marid Crusher");

        helper.page("intro");
        this.add(helper.pageTitle(), "Marid Crusher");
        this.add(helper.pageText(),
                """
                        The afrit crusher is resistant to essence decay and faster and more efficient than the afrit crusher.
                        \\
                        \\
                        It will crush **one** ore into **six** corresponding dusts.
                          """);

        helper.page("ritual");
        //no text


        helper.entry("summon_lumberjack");
        this.add(helper.entryName(), "Summon Foliot Lumberjack");

        helper.page("intro");
        this.add(helper.pageTitle(), "Foliot Lumberjack");
        this.add(helper.pageText(),
                """
                        The lumberjack will harvest trees in it's working area and deposit the dropped items into the specified chest. **Note**: The lumberjack is using the old vanilla AI, not villager AI. That means he is ... *wonky*, and wont to get stuck. Consider him more of a semi-automatic helper, than a fully automated tree farm :)
                          """);

        helper.page("ritual");
        //no text

        helper.page("book_of_calling");
        this.add(helper.pageText(),
                """
                        If you lose the book of calling, you can craft a new one.
                        [#](%1$s)Shift-right-click[#]() the spirit with the crafted book to assign it.
                        """.formatted(COLOR_PURPLE));

        helper.entry("summon_transport_items");
        this.add(helper.entryName(), "Summon Foliot Transporter");

        helper.page("intro");
        this.add(helper.pageTitle(), "Foliot Transporter");
        this.add(helper.pageText(),
                """
                        The transporter is useful in that you don't need a train of hoppers transporting stuff, and can use any inventory to take from and deposit.
                        \\
                        \\
                        To make it take from an inventory simply sneak and interact with it's book of calling on the inventory you want.
                               """);

        helper.page("intro2");
        this.add(helper.pageText(),
                """
                        You can also dictate which inventory it deposits to in the same way.
                        \\
                        The transporter will move all items it can access from one inventory to another, including machines. It can also deposit into the inventories of other spirits. By setting the extract and insert side they can be used to automate various transport tasks.
                           """);

        helper.page("ritual");
        //no text

        helper.page("book_of_calling");
        this.add(helper.pageText(),
                """
                        If you lose the book of calling, you can craft a new one.
                        [#](%1$s)Shift-right-click[#]() the spirit with the crafted book to assign it.
                        """.formatted(COLOR_PURPLE));

        helper.entry("summon_cleaner");
        this.add(helper.entryName(), "Summon Foliot Janitor");

        helper.page("intro");
        this.add(helper.pageTitle(), "Foliot Janitor");
        this.add(helper.pageText(),
                """
                        The janitor will pick up dropped items and deposit them into a target inventory. You can configure an allow/block list to specify which items to pick up or ignore. **Warning**: By default it is set to "allow" mode, so it will only pick up items you specify in the allow list.
                        You can use tags to handle whole groups of items.
                          """);

        helper.page("intro2");
        this.add(helper.pageText(),
                """
                        To bind the janitor to an inventory simply sneak and interact with the janitor book of calling on that inventory. You can also interact with a block while holding the janitor book of calling to have it deposit items there. You can also have it wander around a select area by pulling up that interface. To configure an allow/block list sneak and interact with the janitor.
                          """);


        helper.page("ritual");
        //no text

        helper.page("book_of_calling");
        this.add(helper.pageText(),
                """
                        If you lose the book of calling, you can craft a new one.
                        [#](%1$s)Shift-right-click[#]() the spirit with the crafted book to assign it.
                        """.formatted(COLOR_PURPLE));

        helper.entry("summon_manage_machine");
        this.add(helper.entryName(), "Summon Djinni Machine Operator");

        helper.page("intro");
        this.add(helper.pageTitle(), "Djinni Machine Operator");
        this.add(helper.pageText(),
                """
                        The machine operator transfers items specified in the dimensional storage actuator GUI, to it's managed machine, and returns crafting results to the storage system. It can also be used to automatically empty a chest into the storage actuator.
                        \\
                        Basically, on-demand crafting!
                          """);

        helper.page("ritual");
        //no text

        helper.page("tutorial");
        this.add(helper.pageText(),
                """
                        To use the machine operator use the book of calling to link a Storage Actuator, the machine and optionally a separate extract location (the face you click on will be extracted from!). For the machine you can additionally set a custom name and the insert/extract facings.
                          """);

        helper.page("tutorial2");
        this.add(helper.pageText(),
                """
                        Please note that setting a new machine (or configuring it with the book of calling) will reset the extraction settings.
                        \\
                        \\
                        For an easy start, make sure to view the short [Tutorial Video](https://gyazo.com/237227ba3775e143463b31bdb1b06f50)!
                          """);

        helper.page("book_of_calling");
        this.add(helper.pageText(),
                """
                        If you lose the book of calling, you can craft a new one.
                        [#](%1$s)Shift-right-click[#]() the spirit with the crafted book to assign it.
                        """.formatted(COLOR_PURPLE));

        helper.entry("trade_spirits");
        this.add(helper.entryName(), "Trade Spirits");

        helper.page("intro");
        this.add(helper.pageTitle(), "Trade Spirits");
        this.add(helper.pageText(),
                """
                        Trade spirits pick up appropriate items and throw trade results on the ground. The spirit is only actively exchanging items if purple particles spawn around it.
                        \\
                        \\
                        **If you do not see any particles**, ensure that you gave the proper item and amount.
                           """);

        helper.page("intro2");
        this.add(helper.pageText(),
                """
                        Most trade spirits experience extreme essence decay and will return to [#](%1$s)The Other Place[#]() quickly.
                           """.formatted(COLOR_PURPLE));

        helper.entry("summon_otherworld_sapling_trader");
        this.add(helper.entryName(), "Summon Otherworld Sapling Trader");

        helper.page("intro");
        this.add(helper.pageTitle(), "Otherworld Sapling Trader");
        this.add(helper.pageText(),
                """
                        Otherworld Trees grown from natural Otherworld Saplings can only be harvested when under the effect of [#](%1$s)Third Eye[#](). To make life easier, the Otherworld Sapling Trader will exchange such natural saplings for a stable variant that can be harvested by anyone, and will drop the same stable saplings when harvested.
                           """.formatted(COLOR_PURPLE));

        helper.page("trade");
        //no text

        helper.page("ritual");
        //no text

        helper.entry("summon_otherstone_trader");
        this.add(helper.entryName(), "Summon Otherstone Trader");

        helper.page("intro");
        this.add(helper.pageTitle(), "Otherstone Trader");
        this.add(helper.pageText(),
                """
                        The Otherstone Trader spirit allows to get more [](item://occultism:otherstone) than using [](item://occultism:spirit_fire). Thus it is especially efficient if you want to use Otherstone as a building material.
                           """);

        helper.page("trade");
        //no text

        helper.page("ritual");
        //no text

        helper.entry("summon_wild_parrot");
        this.add(helper.entryName(), "Summon Wild Parrot");

        helper.page("entity");
        this.add(helper.pageText(),
                """
                        **Provides**: A tameable Parrot
                          """);

        helper.page("ritual");
        //no text

        helper.page("description");
        this.add(helper.pageText(),
                """
                        In this ritual a [#](%1$s)Foliot[#]() is summoned **as an untamed spirit**.
                        \\
                        \\
                        The slaughter of a [#](%1$s)Chicken[#]() and the offering of dyes are intended to entice the Foliot to take the shape of a parrot. As [#](%1$s)Foliot[#]() are not among the smartest spirits, they sometimes misunderstand the instructions ...
                          """.formatted(COLOR_PURPLE));

        helper.page("description2");
        this.add(helper.pageText(),
                """
                        *This means, if a [#](%1$s)Chicken[#]() is spawned, that's not a bug, just bad luck!*
                           """.formatted(COLOR_PURPLE));

        helper.entry("summon_wild_otherworld_bird");
        this.add(helper.entryName(), "Summon Wild Drikwing");

        helper.page("entity");
        this.add(helper.pageText(),
                """
                        **Provides**: A tameable Drikwing
                          """);

        helper.page("ritual");
        //no text

        helper.page("description");
        this.add(helper.pageText(),
                """
                        See [Drikwing Familiar](entry://familiar_rituals/familiar_otherworld_bird) for more information.
                          """);

        helper.entry("weather_magic");
        this.add(helper.entryName(), "Weather Magic");

        helper.page("intro");
        this.add(helper.pageTitle(), "Weather Magic");
        this.add(helper.pageText(),
                """
                        Weather magic is especially useful for farmers and others depending on specific weather. Summons spirits to modify the weather. Different types of weather modification require different spirits.
                        \\
                        \\
                        Weather spirits will only modify the weather once and then vanish.
                           """);

        helper.page("ritual_clear");
        //no text

        helper.page("ritual_rain");
        //no text

        helper.page("ritual_thunder");
        //no text

        helper.entry("time_magic");
        this.add(helper.entryName(), "Time Magic");

        helper.page("intro");
        this.add(helper.pageTitle(), "Time Magic");
        this.add(helper.pageText(),
                """
                        Time magic is limited in scope, it cannot send the magician back or forth in time, but rather allows to change time time of day. This is especially useful for rituals or other tasks requiring day- or nighttime specifically.
                        \\
                        \\
                        Time spirits will only modify the weather once and then vanish.
                           """);

        helper.page("ritual_day");
        //no text

        helper.page("ritual_night");
        //no text

        helper.entry("wither_skull");
        this.add(helper.entryName(), "Wither Skeleton Skull");

        helper.page("intro");
        this.add(helper.pageTitle(), "Wither Skeleton Skull");
        this.add(helper.pageText(),
                """
                        Besides venturing into nether dungeons, there is one more way to get these skulls. The legendary [#](%1$s)Wild Hunt[#]() consists of [#](%1$s)Greater Spirits[#]() taking the form of wither skeletons. While summoning the Wild Hunt is incredibly dangerous, it is the fastest way to get wither skeleton skulls.
                           """.formatted(COLOR_PURPLE));

        helper.page("ritual");
        //no text

        helper.entry("afrit_essence");
        this.add(helper.entryName(), "Afrit Essence");

        helper.page("intro");
        this.add(helper.pageTitle(), "Afrit Essence");
        this.add(helper.pageText(),
                """
                        [](item://occultism:afrit_essence) is required to safely call on the more powerful spirits, commonly used in the form of red chalk. To obtain the essence, an [#](%1$s)Afrit[#]() needs to be summoned unbound into this plane, and killed. Be warned that this is no simple endeavour, and unbound spirit presents great danger to all nearby.
                           """.formatted(COLOR_PURPLE));

        helper.page("ritual");
        //no text
    }

    private void addPossessionRitualsCategory(BookLangHelper helper) {
        helper.category("possession_rituals");
        this.add(helper.categoryName(), "Possession Rituals");

        helper.entry("return_to_rituals");
        this.add(helper.entryName(), "Return to Rituals Category");

        helper.entry("overview");
        this.add(helper.entryName(), "Possession Rituals");

        helper.page("intro");
        this.add(helper.pageTitle(), "Possession Rituals");
        this.add(helper.pageText(),
                """
                        Possession rituals bind spirits into living beings, giving the summoner a degree of control over the possessed being.
                        \\
                        \\
                        As such these rituals are used to obtain rare items without having to venture into dangerous places.
                           """);

        helper.entry("possess_enderman");
        this.add(helper.entryName(), "Possessed Enderman");

        helper.page("entity");
        this.add(helper.pageText(),
                """
                        **Drops**: 1-3x [](item://minecraft:ender_pearl)
                                """);

        helper.page("ritual");
        //no text

        helper.page("description");
        this.add(helper.pageText(),
                """
                        In this ritual an [#](%1$s)Enderman[#]() is spawned using the life energy of a [#](%1$s)Pig[#]() and immediately possessed by the summoned [#](%1$s)Djinni[#](). The [#](%1$s)Possessed Enderman[#]() will always drop at least one [](item://minecraft:ender_pearl) when killed.
                                """.formatted(COLOR_PURPLE));

        helper.entry("possess_endermite");
        this.add(helper.entryName(), "Possessed Endermite");

        helper.page("entity");
        this.add(helper.pageText(),
                """
                        **Drops**: 1-2x [](item://minecraft:end_stone)
                                """);

        helper.page("ritual");
        //no text

        helper.page("description");
        this.add(helper.pageText(),
                """
                        In this ritual an [#](%1$s)Endermite[#]() is tricked into spawning. The stone and dirt represent the surroundings, then an egg is thrown to simulate the use of an ender pearl. When the mite spawns, the summoned [#](%1$s)Foliot[#]() immediately possesses it, visits [#](%1$s)The End[#](), and returns. The [#](%1$s)Possessed Endermite[#]() will always drop at least one [](item://minecraft:end_stone) when killed.
                                """.formatted(COLOR_PURPLE));

        helper.entry("possess_ghast");
        this.add(helper.entryName(), "Possessed Ghast");

        helper.page("entity");
        this.add(helper.pageText(),
                """
                        **Drops**: 1-3x [](item://minecraft:ghast_tear)
                                """);

        helper.page("ritual");
        //no text

        helper.page("description");
        this.add(helper.pageText(),
                """
                        In this ritual a [#](%1$s)Ghast[#]() is spawned using the life energy of a [#](%1$s)Sheep[#]() and immediately possessed by the summoned [#](%1$s)Djinni[#](). The [#](%1$s)Possessed Ghast[#]() will always drop at least one [](item://minecraft:ghast_tear) when killed.
                        """.formatted(COLOR_PURPLE));

        helper.entry("possess_skeleton");
        this.add(helper.entryName(), "Possessed Skeleton");

        helper.page("entity");
        this.add(helper.pageText(),
                """
                        **Drops**: 1x [](item://minecraft:skeleton_skull)
                                """);

        helper.page("ritual");
        //no text

        helper.page("description");
        this.add(helper.pageText(),
                """
                        In this ritual an [#](%1$s)Skeleton[#]() is spawned using the life energy of a [#](%1$s)Chicken[#]() and possessed by a [#](%1$s)Foliot[#](). The [#](%1$s)Possessed Skeleton[#]() will be immune to daylight and always drop at least one [](item://minecraft:skeleton_skull) when killed.
                                """.formatted(COLOR_PURPLE));
    }

    private void addCraftingRitualsCategory(BookLangHelper helper) {
        helper.category("crafting_rituals");
        this.add(helper.categoryName(), "Binding Rituals");

        helper.entry("return_to_rituals");
        this.add(helper.entryName(), "Return to Rituals Category");

        helper.entry("overview");
        this.add(helper.entryName(), "Binding Rituals");

        helper.page("intro");
        this.add(helper.pageTitle(), "Binding Rituals");
        this.add(helper.pageText(),
                """
                        Binding rituals infuse spirits into items, where their powers are used for one specific purpose. The created items can act like simple empowering enchantments, or fulfill complex tasks to aid the summoner.
                           """);

        helper.entry("craft_storage_system");
        this.add(helper.entryName(), "Magic Storage");

        helper.page("spotlight");
        this.add(helper.pageText(),
                """
                        The following entries show only the rituals related to the Magic Storage system. For full step-by-step instructions on building the storage system, see the [Magic Storage](category://storage) category.
                           """.formatted(COLOR_PURPLE));

        helper.entry("craft_dimensional_matrix");
        this.add(helper.entryName(), "Dimensional Matrix");

        helper.page("spotlight");
        this.add(helper.pageText(),
                """
                        The dimensional matrix is the entry point to a small dimension used for storing items. A [#](%1$s)Djinni[#]() bound to the matrix keeps the dimension stable, often supported by additional spirits in storage stabilizers, to increase the dimension size.
                           """.formatted(COLOR_PURPLE));

        helper.page("ritual");
        //no text

        helper.entry("craft_dimensional_mineshaft");
        this.add(helper.entryName(), "Dimensional Mineshaft");

        helper.page("spotlight");
        this.add(helper.pageText(),
                """
                        The dimensional mineshaft houses a [#](%1$s)Djinni[#]() which opens up a stable connection into an uninhabited dimension, perfectly suited for mining. While the portal is too small to transfer humans, other spirits can use it to enter the mining dimension and bring back resources.
                           """.formatted(COLOR_PURPLE));

        helper.page("ritual");
        //no text

        helper.page("description");
        this.add(helper.pageTitle(), "Operation");
        this.add(helper.pageText(),
                """
                        The dimensional mineshaft will discard any items it cannot store, so it is important to regularly empty the mineshaft, either manually, with hoppers or using a transporter spirit. Spirits in lamps can be **inserted** from the top, all other sides can be used to **extract** items.
                           """.formatted(COLOR_PURPLE));


        helper.entry("craft_infused_pickaxe");
        this.add(helper.entryName(), "Infused Pickaxe");

        helper.page("spotlight");
        this.add(helper.pageText(),
                """
                        Otherworld ores usually can only be mined with Otherworld metal tools. The [](item://occultism:infused_pickaxe) is a makeshift solution to this Chicken-and-Egg problem. Brittle spirit attuned gems house a [#](%1$s)Djinni[#]() that allows harvesting the ores, but the durability is extremely low.
                           """.formatted(COLOR_PURPLE));

        //TODO: link to page about iesnium pick

        helper.page("ritual");
        //no text

        helper.entry("craft_otherworld_goggles");
        this.add(helper.entryName(), "Craft Otherworld Goggles");

        helper.page("goggles_spotlight");
        this.add(helper.pageText(),
                """
                        The [](item://occultism:otherworld_goggles) give the wearer permanent [#](%1$s)Third Eye[#](), allowing to view even blocks hidden from those partaking of [Demon's Dream](entry://occultism:dictionary_of_spirits/getting_started/demons_dream).
                        \\
                        \\
                        This elegantly solves the general issue of summoners being in a drugged haze, causing all sorts of havoc.
                        """.formatted(COLOR_PURPLE));

        helper.page("goggles_more");
        this.add(helper.pageText(),
                """
                        The Goggles will, however, not give the ability to harvest otherworld materials. That means when wearing goggles, an [Infused Pick](), or even better, an [Iesnium Pick]() needs to be used to break blocks in order to obtain their Otherworld variants.
                        """.formatted(COLOR_PURPLE));

        //TODO: Link to infused and iesnium pick

        helper.page("lenses_spotlight");
        this.add(helper.pageText(),
                """
                        Otherworld Goggles make use of a [#](%1$s)Foliot[#]() bound into the lenses. The Foliot shares it's ability to view higher planes with the wearer, thus allowing them to see Otherworld materials.
                         """.formatted(COLOR_PURPLE));

        helper.page("lenses_more");
        this.add(helper.pageTitle(), "Crafting Lenses");
        this.add(helper.pageText(),
                """
                        Summoning a spirit into the lenses used to craft goggles is one of the first of the more complex rituals apprentice summoners usually attempt, showing that their skills are progressing beyond the basics.
                        """.formatted(COLOR_PURPLE));

        helper.page("lenses_recipe");
        //no text

        helper.page("ritual");
        //no text

        helper.page("goggles_recipe");
        //no text

        helper.entry("craft_storage_controller_base");
        this.add(helper.entryName(), "Storage Actuator Base");

        helper.page("spotlight");
        this.add(helper.pageText(),
                """
                        The storage actuator base imprisons a [#](%1$s)Foliot[#]() responsible for interacting with items in a dimensional storage matrix.
                        """.formatted(COLOR_PURPLE));

        helper.page("ritual");
        //no text

        helper.entry("craft_stabilizer_tier1");
        this.add(helper.entryName(), "Storage Stabilizer Tier 1");

        helper.page("spotlight");
        this.add(helper.pageText(),
                """
                        This simple storage stabilizer is inhabited by a [#](%1$s)Foliot[#]() that supports the dimensional matrix in keeping the storage dimension stable, thus allowing to store more items.
                        \\
                        \\
                        By default each Tier 1 Stabilizer adds **256** slots.
                        """.formatted(COLOR_PURPLE));

        helper.page("ritual");
        //no text

        helper.entry("craft_stabilizer_tier2");
        this.add(helper.entryName(), "Storage Stabilizer Tier 2");

        helper.page("spotlight");
        this.add(helper.pageText(),
                """
                        This improved stabilizer is inhabited by a [#](%1$s)Djinni[#]() that supports the dimensional matrix in keeping the storage dimension stable, thus allowing to store even more items.
                        \\
                        \\
                        By default each Tier 2 Stabilizer adds **512** slots.
                        """.formatted(COLOR_PURPLE));

        helper.page("ritual");
        //no text

        helper.entry("craft_stabilizer_tier3");
        this.add(helper.entryName(), "Storage Stabilizer Tier 3");

        helper.page("spotlight");
        this.add(helper.pageText(),
                """
                        This advanced stabilizer is inhabited by an [#](%1$s)Afrit[#]() that supports the dimensional matrix in keeping the storage dimension stable, thus allowing to store even more items.
                        \\
                        \\
                        By default each Tier 3 Stabilizer adds **1024** slots.
                        """.formatted(COLOR_PURPLE));

        helper.page("ritual");
        //no text

        helper.entry("craft_stabilizer_tier4");
        this.add(helper.entryName(), "Storage Stabilizer Tier 4");

        helper.page("spotlight");
        this.add(helper.pageText(),
                """
                        This highly advanced stabilizer is inhabited by a [#](%1$s)Marid[#]() that supports the dimensional matrix in keeping the storage dimension stable, thus allowing to store even more items.
                        \\
                        \\
                        By default each Tier 4 Stabilizer adds **2048** slots.
                        """.formatted(COLOR_PURPLE));

        helper.page("ritual");
        //no text

        helper.entry("craft_stable_wormhole");
        this.add(helper.entryName(), "Stable Wormhole");

        helper.page("spotlight");
        this.add(helper.pageText(),
                """
                        The stable wormhole allows access to a dimensional matrix from a remote destination.
                        \\
                        \\
                        Shift-click a [](item://occultism:storage_controller) to link it, then place the wormhole in the world to use it as a remote access point.
                         """.formatted(COLOR_PURPLE));

        helper.page("ritual");
        //no text

        helper.entry("craft_storage_remote");
        this.add(helper.entryName(), "Remote Storage Accessor");

        helper.page("spotlight");
        this.add(helper.pageText(),
                """
                        The [](item://occultism:storage_remote) can be linked to a [](item://occultism:storage_controller) by shift-clicking. The [#](%1$s)Djinni[#]() bound to the accessor will then be able to access items from the actuator even from across dimensions.
                        """.formatted(COLOR_PURPLE));

        helper.page("ritual");
        //no text

        helper.entry("craft_foliot_miner");
        this.add(helper.entryName(), "Foliot Miner");

        helper.page("intro");
        this.add(helper.pageTitle(), "Foliot Miner");
        this.add(helper.pageText(),
                """
                        Miner spirits use [](item://occultism:dimensional_mineshaft) to acquire resources from other dimensions. They are summoned and bound into magic lamps, which they can leave only through the mineshaft. The magic lamp degrades over time, once it breaks the spirit is released back to [#](%1$s)The Other Place[#]().
                        """.formatted(COLOR_PURPLE));

        helper.page("magic_lamp");
        this.add(helper.pageTitle(), "Magic Lamp");
        this.add(helper.pageText(),
                """
                        To summon miner spirits, you first need to craft a [](item://occultism:magic_lamp_empty) to hold them. The key ingredient for that is [Iesnium]().
                        """.formatted(COLOR_PURPLE));

        //TODO: link to [Iesnium](entry://getting_started/iesnium) or appropriate starting entry

        helper.page("magic_lamp_recipe");
        //no text

        helper.page("spotlight");
        this.add(helper.pageText(),
                """
                        The [#](%1$s)Foliot[#]() miner harvests block without much aim and returns anything it finds. The mining process is quite slow, due to this the Foliot expends only minor amounts of energy, damaging the lamp it is housed in slowly over time.
                        """.formatted(COLOR_PURPLE));

        helper.page("ritual");
        //no text

        helper.entry("craft_djinni_miner");
        this.add(helper.entryName(), "Djinni Miner");

        helper.page("spotlight");
        this.add(helper.pageText(),
                """
                        The [#](%1$s)Djinni[#]() miner harvests ores specifically. By discarding other blocks it is able to mine faster and more efficiently. The greater power of the djinni it damages the magic lamp relatively quickly.
                        """.formatted(COLOR_PURPLE));

        helper.page("ritual");
        //no text

        helper.entry("craft_afrit_miner");
        this.add(helper.entryName(), "Afrit Miner");

        helper.page("spotlight");
        this.add(helper.pageText(),
                """
                        The [#](%1$s)Afrit[#]() miner harvests ores, like djinni miners, and additionally mines deepslate ores. This miner is faster and more efficient than the djinnis, thus damaging the magic lamp even more slowly.
                        """.formatted(COLOR_PURPLE));

        helper.page("ritual");
        //no text

        helper.entry("craft_marid_miner");
        this.add(helper.entryName(), "Marid Miner");

        helper.page("spotlight");
        this.add(helper.pageText(),
                """
                        The [#](%1$s)Marid[#]() miner is the most powerful miner spirit, it has the fasted mining speed and best magic lamp preservation. Unlike other miner spirits they also can mine the rarest ores, such as [](item://minecraft:ancient_debris) and [](item://occultism:iesnium_ore).
                        """.formatted(COLOR_PURPLE));

        helper.page("ritual");
        //no text

        helper.entry("craft_satchel");
        this.add(helper.entryName(), "Surprisingly Substantial Satchel");

        helper.page("spotlight");
        this.add(helper.pageText(),
                """
                        A [#](%1$s)Foliot[#]() is bound to the satchel, tasked with **slightly** warping reality. This allows to store more items in the satchel than it's size would indicate, making it a practical traveller's companion.
                        """.formatted(COLOR_PURPLE));

        helper.page("ritual");
        //no text

        helper.entry("craft_soul_gem");
        this.add(helper.entryName(), "Soul Gem");

        helper.page("spotlight");
        this.add(helper.pageText(),
                """
                        Soul gems are diamonds set in precious metals, which are then infused with a [#](%1$s)Djinni[#](). The spirit creates a small dimension that allows the temporary entrapment of living beings. Beings of great power or size cannot be stored, however.
                        """.formatted(COLOR_PURPLE));

        helper.page("usage");
        this.add(helper.pageTitle(), "Usage");
        this.add(helper.pageText(),
                """
                        To capture an entity, [#](%1$s)right-click[#]() it with the soul gem. \\
                        [#](%1$s)Right-click[#]() again to release the entity.
                        \\
                        \\
                        Bosses cannot be captured.
                               """.formatted(COLOR_PURPLE));


        helper.page("ritual");
        //no text

        helper.entry("craft_familiar_ring");
        this.add(helper.entryName(), "Familiar Ring");

        helper.page("spotlight");
        this.add(helper.pageText(),
                """
                        Familiar Rings consist of a [](item://occultism:soul_gem), that contains a [#](%1$s)Djinni[#](), mounted on a ring. The [#](%1$s)Djinni[#]() in the ring allows the familiar captured in the soul gem to apply effects to the wearer."
                        """.formatted(COLOR_PURPLE));

        helper.page("usage");
        this.add(helper.pageTitle(), "Usage");
        this.add(helper.pageText(),
                """
                        To use a [](item://occultism:familiar_ring), simply capture a summoned (and tamed) familiar by [#](%1$s)right-clicking[#]() it, and then wear the ring as [#](%1$s)Curio[#]() to make use of the effects the familiar provides.
                        \\
                        \\
                        When released from a familiar ring, the spirit will recognize the person releasing them as their new master.
                        """.formatted(COLOR_PURPLE));

        helper.page("ritual");
        //no text
    }

    private void addFamiliarRitualsCategory(BookLangHelper helper) {
        helper.category("familiar_rituals");
        this.add(helper.categoryName(), "Familiar Rituals");

        helper.entry("return_to_rituals");
        this.add(helper.entryName(), "Return to Rituals Category");

        helper.entry("overview");
        this.add(helper.entryName(), "Familiar Rituals");

        helper.page("intro");
        this.add(helper.pageTitle(), "Familiar Rituals");
        this.add(helper.pageText(),
                """
                        Familiar rituals summon spirits to aid the summoner directly. The spirits usually inhabit an animal's body, allowing them to resist essence decay. Familiars provide buffs, but may also actively protect the summoner.
                                """.formatted(COLOR_PURPLE));

        helper.page("ring");
        this.add(helper.pageTitle(), "Equipping Familiars");
        this.add(helper.pageText(),
                """
                        Enterprising summoners have found a way to bind familiars into jewelry that passively applies their buff, the [Familiar Ring](entry://crafting_rituals/craft_familiar_ring).
                                """.formatted(COLOR_PURPLE));

        helper.page("trading");
        this.add(helper.pageTitle(), "Equipping Familiars");
        this.add(helper.pageText(),
                """
                        "Familiars can be easily traded when in a [Familiar Ring](entry://crafting_rituals/craft_familiar_ring).
                        \\
                        \\
                        When released, the spirit will recognize the person releasing them as their new master.
                                 """.formatted(COLOR_PURPLE));

        helper.entry("familiar_bat");
        this.add(helper.entryName(), "Bat Familiar");

        helper.page("entity");
        this.add(helper.pageText(),
                """
                        **Provides**: [#](%1$s)Night Vision[#]()
                               """.formatted(COLOR_PURPLE));

        helper.page("ritual");
        //no text

        helper.page("description");
        this.add(helper.pageTitle(), "Description");
        this.add(helper.pageText(),
                """
                        **Upgrade Behaviour**\\
                        When upgraded by a blacksmith familiar, the bat familiar will give a life steal effect to it's master.
                           """.formatted(COLOR_PURPLE));

        helper.entry("familiar_beaver");
        this.add(helper.entryName(), "Beaver Familiar");

        helper.page("entity");
        this.add(helper.pageText(),
                """
                        **Provides**: [#](%1$s)Increased wood break speed[#]()
                               """.formatted(COLOR_PURPLE));

        helper.page("ritual");
        //no text

        helper.page("description");
        this.add(helper.pageTitle(), "Description");
        this.add(helper.pageText(),
                """
                        The Beaver familiar will chop down nearby trees when they grow from a sapling into a tree. It can only handle small trees.
                        \\
                        \\
                        **Upgrade Behaviour**\\
                        Cannot be upgraded by the blacksmith familiar.
                           """.formatted(COLOR_PURPLE));

        helper.entry("familiar_beholder");
        this.add(helper.entryName(), "Beholder Familiar");

        helper.page("entity");
        this.add(helper.pageText(),
                """
                        **Provides**: [#](%1$s)Highlights enemies[#](), [#](%1$s)Shoots **FREAKING LAZORS**[#]()
                               """.formatted(COLOR_PURPLE));

        helper.page("ritual");
        //no text

        helper.page("description");
        this.add(helper.pageTitle(), "Description");
        this.add(helper.pageText(),
                """
                        The Beholder familiar highlights nearby entities with a glow effect, and shoots laser rays at enemies. It **eats** (poor) **Shub Niggurath babies** to gain temporary damage and speed.
                        \\
                        \\
                        **Upgrade Behaviour**\\
                        When upgraded by a blacksmith familiar, it give it's master immunity to blindness.
                           """.formatted(COLOR_PURPLE));

        helper.entry("familiar_blacksmith");
        this.add(helper.entryName(), "Blacksmith Familiar");

        helper.page("entity");
        this.add(helper.pageText(),
                """
                        **Provides**: [#](%1$s)Repairs Equipment while Mining[#](), [#](%1$s)Upgrades other familiars[#]()
                               """.formatted(COLOR_PURPLE));

        helper.page("ritual");
        //no text

        helper.page("description");
        this.add(helper.pageTitle(), "Description");
        this.add(helper.pageText(),
                """
                        Whenever the player picks up stone, there is a chance for the blacksmith familiar to repair their equipment a little bit.
                        \\
                        \\
                        **Upgrade Behaviour**: \\
                        Cannot be upgraded, but upgrades other Familiars.
                           """.formatted(COLOR_PURPLE));

        helper.page("description2");
        this.add(helper.pageTitle(), "Upgrading Familiars");
        this.add(helper.pageText(),
                """
                        To upgrade other familiars the blacksmith needs to be given iron ingots or blocks by [#](%1$s)right-clicking[#]() it.
                        \\
                        \\
                        Upgraded familiars provide additional effects.
                           """.formatted(COLOR_PURPLE));

        helper.entry("familiar_chimera");
        this.add(helper.entryName(), "Chimera Familiar");

        helper.page("entity");
        this.add(helper.pageText(),
                """
                        **Provides**: [#](%1$s)Rideable Mount[#]()
                               """.formatted(COLOR_PURPLE));

        helper.page("ritual");
        //no text

        helper.page("description");
        this.add(helper.pageTitle(), "Description");
        this.add(helper.pageText(),
                """
                        The chimera familiar can be fed (any) meat to grow, when growing it will gain damage and speed. Once it has grown big enough, players can ride it. When feeding it a [](item://minecraft:golden_apple) the [#](%1$s)Goat[#]() will detach and become a separate familiar.
                        \\
                        \\
                        The detached goat familiar can be used to obtain the [Shub Niggurath](entry://familiar_rituals/familiar_shub_niggurath) familiar.
                           """.formatted(COLOR_PURPLE));

        helper.page("description2");
        this.add(helper.pageText(),
                """
                        **Upgrade Behaviour**\\
                        When upgraded by a blacksmith familiar, the goat familiar will get a warning bell. When you hit the familiar it will ring the bell and attract enemies in a large radius.
                           """.formatted(COLOR_PURPLE));

        helper.entry("familiar_cthulhu");
        this.add(helper.entryName(), "Cthulhu Familiar");

        helper.page("entity");
        this.add(helper.pageText(),
                """
                        **Provides**: [#](%1$s)Water Breathing[#](), [#](%1$s)General Coolness[#]()
                               """.formatted(COLOR_PURPLE));

        helper.page("ritual");
        //no text

        helper.page("description");
        this.add(helper.pageTitle(), "Description");
        this.add(helper.pageText(),
                """
                        **Upgrade Behaviour**\\
                        When upgraded by a blacksmith familiar, it will act as a mobile light source.
                           """.formatted(COLOR_PURPLE));

        helper.entry("familiar_shub_niggurath");
        this.add(helper.entryName(), "Shub Niggurath Familiar");

        helper.page("entity");
        this.add(helper.pageText(),
                """
                        **Provides**: [#](%1$s)Spawns small versions of itself to fight for you.[#]()
                                   """.formatted(COLOR_PURPLE));

        helper.page("ritual");
        this.add(helper.pageTitle(), "Ritual");
        this.add(helper.pageText(),
                """
                        The [#](%1$s)Shub Niggurath[#]() is not summoned directly. First, summon a [Chimera Familiar](entry://familiar_rituals/familiar_chimera) and feed it a [](item://minecraft:golden_apple) to detach the [#](%1$s)Goat[#](). Bring the goat to a [#](%1$s)Forest Biome[#](). Then click the goat with [any Black Dye](item://minecraft:black_dye), [](item://minecraft:flint) and [](item://minecraft:ender_eye) to summon the [#](%1$s)Shub Niggurath[#]().
                           """.formatted(COLOR_PURPLE));

        helper.page("description");
        this.add(helper.pageTitle(), "Description");
        this.add(helper.pageText(),
                """
                        **Upgrade Behaviour**\\
                        When upgraded by a blacksmith familiar, it will get a warning bell. When you hit the familiar it will ring the bell and **attract enemies** in a large radius.
                           """.formatted(COLOR_PURPLE));

        helper.entry("familiar_deer");
        this.add(helper.entryName(), "Deer Familiar");

        helper.page("entity");
        this.add(helper.pageText(),
                """
                        **Provides**: [#](%1$s)Jump Boost[#]()
                                   """.formatted(COLOR_PURPLE));

        helper.page("ritual");
        //no text

        helper.page("description");
        this.add(helper.pageTitle(), "Description");
        this.add(helper.pageText(),
                """
                        **Upgrade Behaviour**\\
                        When upgraded by a blacksmith familiar, it will attack nearby enemies with a hammer. Yep, a **hammer**.
                           """.formatted(COLOR_PURPLE));

        helper.entry("familiar_devil");
        this.add(helper.entryName(), "Devil Familiar");

        helper.page("entity");
        this.add(helper.pageText(),
                """
                        **Provides**: [#](%1$s)Fire Resistance[#](), [#](%1$s)Attacks Enemies[#]()
                                   """.formatted(COLOR_PURPLE));

        helper.page("ritual");
        //no text

        helper.page("description");
        this.add(helper.pageTitle(), "Description");
        this.add(helper.pageText(),
                """
                        **Upgrade Behaviour**\\
                        Cannot be upgraded by the blacksmith familiar.
                           """.formatted(COLOR_PURPLE));

        helper.entry("familiar_dragon");
        this.add(helper.entryName(), "Dragon Familiar");

        helper.page("entity");
        this.add(helper.pageText(),
                """
                        **Provides**: [#](%1$s)Increased XP[#](), Loves Sticks
                                   """.formatted(COLOR_PURPLE));

        helper.page("ritual");
        //no text

        helper.page("description");
        this.add(helper.pageTitle(), "Description");
        this.add(helper.pageText(),
                """
                        Greedy familiars can ride on dragon familiars, giving the dragon the greedy effects additionally.
                        \\
                        \\
                        **Upgrade Behaviour**\\
                        When upgraded by a blacksmith familiar, it will throw swords at nearby enemies.
                           """.formatted(COLOR_PURPLE));


        helper.entry("familiar_fairy");
        this.add(helper.entryName(), "Fairy Familiar");

        helper.page("entity");
        this.add(helper.pageText(),
                """
                        **Provides**: [#](%1$s)Assists Familiars[#](), [#](%1$s)Prevents Familiar Deaths[#](), [#](%1$s)Drains Enemy Life Force[#]()
                                   """.formatted(COLOR_PURPLE));

        helper.page("ritual");
        //no text

        helper.page("description");
        this.add(helper.pageTitle(), "Description");
        this.add(helper.pageText(),
                """
                        The Fairy familiar **keeps other familiars from dying** (with cooldown), helps out other familiars with **beneficial effects** and **drains the life force of enemies** to assist their master.
                        \\
                        \\
                        **Upgrade Behaviour**\\
                        Cannot be upgraded by the blacksmith familiar.
                           """.formatted(COLOR_PURPLE));

        helper.entry("familiar_greedy");
        this.add(helper.entryName(), "Greedy Familiar");

        helper.page("entity");
        this.add(helper.pageText(),
                """
                        **Provides**: [#](%1$s)Picks up Items[#](), [#](%1$s)Increased Pick-up Range[#]()
                                   """.formatted(COLOR_PURPLE));
        helper.page("ritual");
        //no text

        helper.page("description");
        this.add(helper.pageTitle(), "Description");
        this.add(helper.pageText(),
                """
                        The greedy familiar is a Foliot that will pick up nearby items for it's master. When captured in a familiar ring it increased the pick-up range of the wearer.
                        \\
                        \\
                        **Upgrade Behaviour**\\
                        When upgraded by a blacksmith familiar, it can find blocks for its master. [#](%1$s)Right-click[#]() it with a block to tell it what to look for.
                           """.formatted(COLOR_PURPLE));

        helper.entry("familiar_guardian");
        this.add(helper.entryName(), "Guardian Familiar");

        helper.page("entity");
        this.add(helper.pageText(),
                """
                        **Provides**: [#](%1$s)Prevents player death while alive[#]()
                                   """.formatted(COLOR_PURPLE));

        helper.page("ritual");
        //no text

        helper.page("description");
        this.add(helper.pageTitle(), "Description");
        this.add(helper.pageText(),
                """
                        The guardian familiar sacrifices a limb everytime it's master is about to die and thus **prevents the death**. Once the guardian dies, the player is no longer protected. When summoned, the guardian spawns with a **random amount of limbs**, there is no guarantee that a complete guardian is summoned.
                           """.formatted(COLOR_PURPLE));

        helper.page("description2");
        this.add(helper.pageText(),
                """
                        **Upgrade Behaviour**\\
                        When upgraded by a blacksmith familiar, it regains a limb (can only be done once).
                           """.formatted(COLOR_PURPLE));

        helper.entry("familiar_headless");
        this.add(helper.entryName(), "Headless Ratman Familiar");

        helper.page("entity");
        this.add(helper.pageText(),
                """
                        **Provides**: [#](%1$s)Conditional Damage Buff[#]()
                                   """.formatted(COLOR_PURPLE));

        helper.page("ritual");
        //no text

        helper.page("description");
        this.add(helper.pageTitle(), "Description");
        this.add(helper.pageText(),
                """
                        The headless ratman familiar steals heads of mobs near the ratman when they are killed. It then provides a damage buff against that type of mob to their master. If the ratman drops **below 50%% health** it dies, but can then be rebuilt by their master by giving them [](item://minecraft:wheat), [](item://minecraft:stick), [](item://minecraft:hay_block) and a [](item://minecraft:pumpkin).
                           """.formatted(COLOR_PURPLE));

        helper.page("description2");
        this.add(helper.pageText(),
                """
                        **Upgrade Behaviour**\\
                        When upgraded by a blacksmith familiar, it will give weakness to nearby mobs of the type it stole the head from.
                           """.formatted(COLOR_PURPLE));


        helper.entry("familiar_mummy");
        this.add(helper.entryName(), "Mummy Familiar");

        helper.page("entity");
        this.add(helper.pageText(),
                """
                        **Provides**: [#](%1$s)Fights your enemies[#](), [#](%1$s)Dodge Effect[#]()
                                   """.formatted(COLOR_PURPLE));

        helper.page("ritual");
        //no text

        helper.page("description");
        this.add(helper.pageTitle(), "Description");
        this.add(helper.pageText(),
                """
                        The Mummy familiar is a martial arts expert and fights to protect their master.
                        \\
                        \\
                        **Upgrade Behaviour**\\
                        When upgraded by a blacksmith familiar, it the familiar will deal even more damage.
                            """.formatted(COLOR_PURPLE));

        helper.entry("familiar_otherworld_bird");
        this.add(helper.entryName(), "Drikwing Familiar");

        helper.page("entity");
        this.add(helper.pageText(),
                """
                        **Provides**: [#](%1$s)Multi-Jump[#](), [#](%1$s)Jump Boost[#](), [#](%1$s)Slow Falling[#]()
                                   """.formatted(COLOR_PURPLE));

        helper.page("ritual");
        //no text

        helper.page("description");
        this.add(helper.pageTitle(), "Description");
        this.add(helper.pageText(),
                """
                        [#](%1$s)Drikwings$[#]() are a subclass of [#](%1$s)Djinni[#]() that are known to be amicable towards humans. They usually take the shape of a dark blue and purple parrot. Drikwings will provide their owner with limited flight abilities when nearby.
                        \\
                        \\
                        **Upgrade Behaviour**\\
                        Cannot be upgraded by the blacksmith familiar.
                            """.formatted(COLOR_PURPLE));

        helper.page("description2");
        this.add(helper.pageText(),
                """
                        To obtain the parrot or parrot familiar for the sacrifice, consider summoning them using either the [Wild Parrot Ritual](entry://summoning_rituals/summon_wild_parrot) or [Parrot Familiar Ritual](entry://familiar_rituals/familiar_parrot)
                        \\
                        \\
                        **Hint:** If you use mods that protect pets from death, use the wild parrot ritual!
                            """.formatted(COLOR_PURPLE));

        helper.entry("familiar_parrot");
        this.add(helper.entryName(), "Parrot Familiar");

        helper.page("entity");
        this.add(helper.pageText(),
                """
                        **Provides**: [#](%1$s)Company[#]()
                                   """.formatted(COLOR_PURPLE));

        helper.page("ritual");
        //no text


        //
        helper.page("description");
        this.add(helper.pageTitle(), "Description");
        this.add(helper.pageText(),
                """
                        In this ritual a [#](%1$s)Foliot[#]() is summoned **as a familiar**, the slaughter of a [#](%1$s)Chicken[#]() and the offering of dyes are intended to entice the [#](%1$s)Foliot[#]() to take the shape of a parrot.\\
                        As [#](%1$s)Foliot[#]() are not among the smartest spirits, they sometimes misunderstand the instructions ...
                            """.formatted(COLOR_PURPLE));

        helper.page("description2");
        this.add(helper.pageText(),
                """
                        *This means, if a [#](%1$s)Chicken[#]() is spawned, that's not a bug, just bad luck!*
                        \\
                        \\
                        **Upgrade Behaviour**\\
                        Cannot be upgraded by the blacksmith familiar.
                           """.formatted(COLOR_PURPLE));

    }

    private void addStorageCategory(BookLangHelper helper) {
        helper.category("storage");
        this.add(helper.categoryName(), "Magic Storage");

        helper.entry("overview");
        this.add(helper.entryName(), "Magic Storage");

        helper.page("intro");
        this.add(helper.pageTitle(), "Magic Storage");
        this.add(helper.pageText(),
                """
                        Every summoner knows the problem: There are just too many occult paraphernalia lying around. The solution is simple, yet elegant: Magic Storage!
                        \\
                        \\
                        Using Spirits able to access storage dimensions it is possible to create almost unlimited storage space.
                        """.formatted(COLOR_PURPLE));

        helper.page("intro2");
        this.add(helper.pageText(),
                """
                        Follow the steps shown in this category to get your own storage system!
                        The steps related to storage in [Binding Rituals](category://crafting_rituals/) show only the rituals, while here **all required steps** including crafting are shown.
                        """.formatted(COLOR_PURPLE));

        helper.entry("storage_controller");
        this.add(helper.entryName(), "Storage Actuator");

        helper.page("intro");
        this.add(helper.pageTitle(), "Storage Actuator");
        this.add(helper.pageText(),
                """
                        The [](item://occultism:storage_controller) consists of a [Dimensional Matrix](entry://crafting_rituals/craft_dimensional_matrix) inhabited by a [#](%1$s)Djinni[#]() that creates and manages a storage dimension, and a [Base](entry://crafting_rituals/craft_storage_controller_base) infused with a [#](%1$s)Foliot[#]() that moves items in and out of the storage dimension.
                        """.formatted(COLOR_PURPLE));

        helper.page("usage");
        this.add(helper.pageTitle(), "Usage");
        this.add(helper.pageText(),
                """
                        After crafting the [](item://occultism:storage_controller) (see following pages), place it in the world and [#](%1$s)right-click[#]() it with an empty hand. This will open the GUI of the storage controller, from there on it will work much like a very big shulker box.
                        """.formatted(COLOR_PURPLE));

        helper.page("safety");
        this.add(helper.pageTitle(), "Safety first!");
        this.add(helper.pageText(),
                """
                        Breaking the storage controller will store all contained items in the dropped item, you will not lose anything.
                        The same applies to breaking or replacing Storage Stabilizers (you will learn about these later). 
                        \\
                        \\
                        Like in a shulker box, your items are safe!
                        """.formatted(COLOR_PURPLE));


        helper.page("size");
        this.add(helper.pageTitle(), "So much storage!");
        this.add(helper.pageText(),
                """
                        The storage controller by default provides **128** slots (_You will learn later how to increase that_). Each slot can hold up to **1024** items, even items that usually have smaller stack sizes or are not stackable at all.
                        """.formatted(COLOR_PURPLE));

        helper.page("unique_items");
        this.add(helper.pageTitle(), "Unique Items");
        this.add(helper.pageText(),
                """
                        The only exception to the increased stack size are **items with unique properties** ("NBT data"), such as damaged equipment, which cannot stack at all and will take up a full slot. For optimal storage results you should limit the amount of these items in your system.
                        """.formatted(COLOR_PURPLE));

        helper.page("config");
        this.add(helper.pageTitle(), "Configurablity");
        this.add(helper.pageText(),
                """
                        Slot amount and slot size can be configured in the "[#](%1$s)occultism-server.toml[#]()" config file in the save directory of your world.
                        \\
                        \\
                        Increasing slot size does not impact performance, increasing slot amount (by a lot) can have a negative impact on performance.
                        """.formatted(COLOR_PURPLE));

        helper.page("mods");
        this.add(helper.pageTitle(), "Interaction with Mods");
        this.add(helper.pageText(),
                """
                        For other mods the storage controller behaves like a shulker box, anything that can interact with vanilla chests and shulker boxes can interact with the storage controller.
                        Devices that count storage contents may have trouble with the stack sizes, if you run into this issue have your server admin set [this option](https://github.com/klikli-dev/occultism/issues/221#issuecomment-944904459).
                        """.formatted(COLOR_PURPLE));


        helper.page("matrix_ritual");
        //no text

        helper.page("base_ritual");
        //no text

        helper.page("recipe");
        this.add(helper.pageText(),
                """
                        This is the actual block that works as a storage, make sure to craft it!
                        Placing just the [](item://occultism:storage_controller_base) from the previous step won't work.
                        """.formatted(COLOR_PURPLE));
        //no text


        helper.entry("storage_stabilizer");
        this.add(helper.entryName(), "Extending Storage");

        helper.page("spotlight");
        this.add(helper.pageText(),
                """
                        Storage Stabilizers increase the storage space in the storage dimension of the storage actuator. The higher the tier of the stabilizer, the more additional storage slots it provides. The following entries will show you how to craft each tier.
                        \\
                        \\
                        """.formatted(COLOR_PURPLE));

        helper.page("upgrade");
        this.add(helper.pageTitle(), "Upgrading");
        this.add(helper.pageText(),
                """
                        It is **safe to destroy a storage stabilizer** to upgrade it. The items in the [Storage Actuator](entry://storage/storage_controller) will not be lost or dropped - you simply cannot add new items until you add enough storage stabilizers to have free slots again.
                         """.formatted(COLOR_PURPLE));

        helper.page("build_instructions");
        this.add(helper.pageTitle(), "Build Instructions");
        this.add(helper.pageText(),
                """
                        Storage controllers need to point at the [Dimensional Matrix](entry://crafting_rituals/craft_dimensional_matrix), that means **one block above the [Storage Actuator](entry://storage/storage_controller)**.
                        \\
                        \\
                        They can be **up to 5 blocks away** from the Dimensional Matrix, and need to be in a straight line of sight. See the next page for a possible very simple setup.
                        """.formatted(COLOR_PURPLE));


        helper.page("demo");
        this.add(helper.pageTitle(), "Storage Stabilizer Setup");
        this.add(helper.pageText(),
                """
                        **Note:** You do not need all 4 stabilizers, even one will increase your storage.
                        """.formatted(COLOR_PURPLE));
    }

    private void addAdvancements() {
        //"advancements\.occultism\.(.*?)\.title": "(.*)",
        //this.advancementTitle\("\1", "\2"\);
        this.advancementTitle("root", "Occultism");
        this.advancementDescr("root", "Get Spiritual!");
        this.advancementTitle("summon_foliot_crusher", "Ore Doubling");
        this.advancementDescr("summon_foliot_crusher", "Crunch! Crunch! Crunch!");
        this.advancementTitle("familiars", "Occultism: Friends");
        this.advancementDescr("familiars", "Use a ritual to summon a familiar");
        this.advancementDescr("familiar.bat", "Lure a normal bat near your bat familiar");
        this.advancementTitle("familiar.bat", "Cannibalism");
        this.advancementDescr("familiar.capture", "Trap your familiar in a familiar ring");
        this.advancementTitle("familiar.capture", "Catch them all!");
        this.advancementDescr("familiar.cthulhu", "Make your cthulhu familiar sad");
        this.advancementTitle("familiar.cthulhu", "You Monster!");
        this.advancementDescr("familiar.deer", "Observe when your deer familiar poops demon seed");
        this.advancementTitle("familiar.deer", "Demonic Poop");
        this.advancementDescr("familiar.devil", "Command your devil familiar to breath fire");
        this.advancementTitle("familiar.devil", "Hellfire");
        this.advancementDescr("familiar.dragon_nugget", "Give a gold nugget to your dragon familiar");
        this.advancementTitle("familiar.dragon_nugget", "Deal!");
        this.advancementDescr("familiar.dragon_ride", "Let your greedy familiar pick something up while riding a dragon familiar");
        this.advancementTitle("familiar.dragon_ride", "Working together");
        this.advancementDescr("familiar.greedy", "Let your greedy familiar pick something up for you");
        this.advancementTitle("familiar.greedy", "Errand Boy");
        this.advancementDescr("familiar.party", "Get your familiar to dance");
        this.advancementTitle("familiar.party", "Dance!");
        this.advancementDescr("familiar.rare", "Obtain a rare familiar variant");
        this.advancementTitle("familiar.rare", "Rare Friend");
        this.advancementDescr("familiar.root", "Use a ritual to summon a familiar");
        this.advancementTitle("familiar.root", "Occultism: Friends");
        this.advancementDescr("familiar.mans_best_friend", "Pet your dragon familiar; and play fetch with it");
        this.advancementTitle("familiar.mans_best_friend", "Man's Best Friend");
        this.advancementTitle("familiar.blacksmith_upgrade", "Fully Equipped!");
        this.advancementDescr("familiar.blacksmith_upgrade", "Let your blacksmith familiar upgrade one of your other familiars");
        this.advancementTitle("familiar.guardian_ultimate_sacrifice", "The Ultimate Sacrifice");
        this.advancementDescr("familiar.guardian_ultimate_sacrifice", "Let your Guardian Familiar die to save yourself");
        this.advancementTitle("familiar.headless_cthulhu_head", "The Horror!");
        this.advancementDescr("familiar.headless_cthulhu_head", "Kill Cthulhu near your Headless Ratman familiar");
        this.advancementTitle("familiar.headless_rebuilt", "We can rebuild him");
        this.advancementDescr("familiar.headless_rebuilt", "\"Rebuild\" your Headless Ratman familiar after he has died");
        this.advancementTitle("familiar.chimera_ride", "Mount up!");
        this.advancementDescr("familiar.chimera_ride", "Ride on your Chimera familiar when you have fed it enough");
        this.advancementTitle("familiar.goat_detach", "Disassemble");
        this.advancementDescr("familiar.goat_detach", "Give your Chimera familiar a golden apple");
        this.advancementTitle("familiar.shub_niggurath_summon", "The Black Goat of the Woods");
        this.advancementDescr("familiar.shub_niggurath_summon", "Transform your goat familiar into something terrible");
        this.advancementTitle("familiar.shub_cthulhu_friends", "Eldritch Love");
        this.advancementDescr("familiar.shub_cthulhu_friends", "Watch Shub Niggurath and Cthulhu hold hands");
        this.advancementTitle("familiar.shub_niggurath_spawn", "Think of the Children!");
        this.advancementDescr("familiar.shub_niggurath_spawn", "Let a spawn of Shub Niggurath damage an enemy by exploding");
        this.advancementTitle("familiar.beholder_ray", "Death Ray");
        this.advancementDescr("familiar.beholder_ray", "Let your Beholder familiar attack an enemy");
        this.advancementTitle("familiar.beholder_eat", "Hunger");
        this.advancementDescr("familiar.beholder_eat", "Watch your Beholder familiar eat a spawn of Shub Niggurath");
        this.advancementTitle("familiar.fairy_save", "Guardian Angel");
        this.advancementDescr("familiar.fairy_save", "Let your Fairy familiar save one of your other familiars from certain death");
        this.advancementTitle("familiar.mummy_dodge", "Ninja!");
        this.advancementDescr("familiar.mummy_dodge", "Dodge an attack with the Mummy familiar dodge effect");
        this.advancementTitle("familiar.beaver_woodchop", "Woodchopper");
        this.advancementDescr("familiar.beaver_woodchop", "Let your Beaver familiar chop down a tree");
    }

    private void addKeybinds() {
        this.add("key.occultism.category", "Occultism");
        this.add("key.occultism.backpack", "Open Satchel");
        this.add("key.occultism.storage_remote", "Open Storage Accessor");
        this.add("key.occultism.familiar.otherworld_bird", "Toggle Ring Effect: Drikwing");
        this.add("key.occultism.familiar.greedy_familiar", "Toggle Ring Effect: Greedy");
        this.add("key.occultism.familiar.bat_familiar", "Toggle Ring Effect: Bat");
        this.add("key.occultism.familiar.deer_familiar", "Toggle Ring Effect: Deer");
        this.add("key.occultism.familiar.cthulhu_familiar", "Toggle Ring Effect: Cthulhu");
        this.add("key.occultism.familiar.devil_familiar", "Toggle Ring Effect: Devil");
        this.add("key.occultism.familiar.dragon_familiar", "Toggle Ring Effect: Dragon");
        this.add("key.occultism.familiar.blacksmith_familiar", "Toggle Ring Effect: Blacksmith");
        this.add("key.occultism.familiar.guardian_familiar", "Toggle Ring Effect: Guardian");
        this.add("key.occultism.familiar.headless_familiar", "Toggle Ring Effect: Headless Ratman");
        this.add("key.occultism.familiar.chimera_familiar", "Toggle Ring Effect: Chimera");
        this.add("key.occultism.familiar.goat_familiar", "Toggle Ring Effect: Goat");
        this.add("key.occultism.familiar.shub_niggurath_familiar", "Toggle Ring Effect: Shub Niggurath");
        this.add("key.occultism.familiar.beholder_familiar", "Toggle Ring Effect: Beholder");
        this.add("key.occultism.familiar.fairy_familiar", "Toggle Ring Effect: Fairy");
        this.add("key.occultism.familiar.mummy_familiar", "Toggle Ring Effect: Mummy");
        this.add("key.occultism.familiar.beaver_familiar", "Toggle Ring Effect: Beaver");
    }

    private void addJeiTranslations() {
        this.add("occultism.jei.spirit_fire", "Spiritfire");
        this.add("occultism.jei.crushing", "Crusher Spirit");
        this.add("occultism.jei.miner", "Dimensional Mineshaft");
        this.add("occultism.jei.miner.chance", "Chance: %d%%");
        this.add("occultism.jei.ritual", "Occult Ritual");
        this.add("occultism.jei.pentacle", "Pentacle");
        this.add("jei.occultism.ingredient.tallow.description", "Kill animals, such as \u00a72pigs\u00a7r, \u00a72cows\u00a7r, \u00a72sheep\u00a7r, \u00a72horses\u00a7r and \u00a72lamas\u00a7r with the Butcher Knife to obtain tallow.");
        this.add("jei.occultism.ingredient.otherstone.description", "Primarily found in Otherworld Groves. Only visible while the status \u00a76Third Eye\u00a7r is active. See \u00a76Dictionary of Spirits\u00a7r for more information.");
        this.add("jei.occultism.ingredient.otherworld_log.description", "Primarily found in Otherworld Groves. Only visible while the status \u00a76Third Eye\u00a7r is active. See \u00a76Dictionary of Spirits\u00a7r for more information.");
        this.add("jei.occultism.ingredient.otherworld_sapling.description", "Primarily found in Otherworld Groves. Only visible while the status \u00a76Third Eye\u00a7r is active. See \u00a76Dictionary of Spirits\u00a7r for more information.");
        this.add("jei.occultism.ingredient.otherworld_sapling_natural.description", "Primarily found in Otherworld Groves. Only visible while the status \u00a76Third Eye\u00a7r is active. See \u00a76Dictionary of Spirits\u00a7r for more information.");
        this.add("jei.occultism.ingredient.otherworld_leaves.description", "Primarily found in Otherworld Groves. Only visible while the status \u00a76Third Eye\u00a7r is active. See \u00a76Dictionary of Spirits\u00a7r for more information.");
        this.add("jei.occultism.ingredient.iesnium_ore.description", "Found in the nether. Only visible while the status \u00a76Third\u00a7r \u00a76Eye\u00a7r is active. See \u00a76Dictionary\u00a7r \u00a76of\u00a7r \u00a76Spirits\u00a7r for more information.");
        this.add("jei.occultism.ingredient.spirit_fire.description", "Throw \u00a76Demon's Dream  Fruit\u00a7r to the ground and light it on fire. See \u00a76Dictionary of Spirits\u00a7r for more information.");
        this.add("jei.occultism.ingredient.datura.description", "Can be used to heal all spirits and familiars summoned by Occultism Rituals. Simply right-click the entity to heal it by one heart");
        this.add("jei.occultism.sacrifice", "Sacrifice: %s");
        this.add("jei.occultism.summon", "Summon: %s");
        this.add("jei.occultism.job", "Job: %s");
        this.add("jei.occultism.item_to_use", "Item to use:");
        this.add("jei.occultism.error.missing_id", "Cannot identify recipe.");
        this.add("jei.occultism.error.invalid_type", "Invalid recipe type.");
        this.add("jei.occultism.error.recipe_too_large", "Recipe larger than 3x3.");
        this.add("jei.occultism.error.pentacle_not_loaded", "The pentacle could not be loaded.");
        this.add("item.occultism.jei_dummy.require_sacrifice", "Requires Sacrifice!");
        this.add("item.occultism.jei_dummy.require_sacrifice.tooltip", "This ritual requires a sacrifice to start. Please refer to the Dictionary of Spirits for detailed instructions.");
        this.add("item.occultism.jei_dummy.require_item_use", "Requires Item Use!");
        this.add("item.occultism.jei_dummy.require_item_use.tooltip", "This ritual requires to use a specific item to start. Please refer to the Dictionary of Spirits for detailed instructions.");
        this.add("item.occultism.jei_dummy.none", "Non-Item Ritual Result");
        this.add("item.occultism.jei_dummy.none.tooltip", "This ritual does not create any items.");
    }

    private void addFamiliarSettingsMessages() {
        this.add("message.occultism.familiar.otherworld_bird.enabled", "Ring Effect - Drikwing: Enabled");
        this.add("message.occultism.familiar.otherworld_bird.disabled", "Ring Effect - Drikwing: Disabled");
        this.add("message.occultism.familiar.greedy_familiar.enabled", "Ring Effect - Greedy: Enabled");
        this.add("message.occultism.familiar.greedy_familiar.disabled", "Ring Effect - Greedy: Disabled");
        this.add("message.occultism.familiar.bat_familiar.enabled", "Ring Effect - Bat: Enabled");
        this.add("message.occultism.familiar.bat_familiar.disabled", "Ring Effect - Bat: Disabled");
        this.add("message.occultism.familiar.deer_familiar.enabled", "Ring Effect - Deer: Enabled");
        this.add("message.occultism.familiar.deer_familiar.disabled", "Ring Effect - Deer: Disabled");
        this.add("message.occultism.familiar.cthulhu_familiar.enabled", "Ring Effect - Cthulhu: Enabled");
        this.add("message.occultism.familiar.cthulhu_familiar.disabled", "Ring Effect - Cthulhu: Disabled");
        this.add("message.occultism.familiar.devil_familiar.enabled", "Ring Effect - Devil: Enabled");
        this.add("message.occultism.familiar.devil_familiar.disabled", "Ring Effect - Devil: Disabled");
        this.add("message.occultism.familiar.dragon_familiar.enabled", "Ring Effect - Dragon: Enabled");
        this.add("message.occultism.familiar.dragon_familiar.disabled", "Ring Effect - Dragon: Disabled");
        this.add("message.occultism.familiar.blacksmith_familiar.enabled", "Ring Effect - Blacksmith: Enabled");
        this.add("message.occultism.familiar.blacksmith_familiar.disabled", "Ring Effect - Blacksmith: Disabled");
        this.add("message.occultism.familiar.guardian_familiar.enabled", "Ring Effect - Guardian: Enabled");
        this.add("message.occultism.familiar.guardian_familiar.disabled", "Ring Effect - Guardian: Disabled");
        this.add("message.occultism.familiar.headless_familiar.enabled", "Ring Effect - Headless Ratman: Enabled");
        this.add("message.occultism.familiar.headless_familiar.disabled", "Ring Effect - Headless Ratman: Disabled");
        this.add("message.occultism.familiar.chimera_familiar.enabled", "Ring Effect - Chimera: Enabled");
        this.add("message.occultism.familiar.chimera_familiar.disabled", "Ring Effect - Chimera: Disabled");
        this.add("message.occultism.familiar.shub_niggurath_familiar.enabled", "Ring Effect - Shub Niggurath: Enabled");
        this.add("message.occultism.familiar.shub_niggurath_familiar.disabled", "Ring Effect - Shub Niggurath: Disabled");
        this.add("message.occultism.familiar.beholder_familiar.enabled", "Ring Effect - Beholder: Enabled");
        this.add("message.occultism.familiar.beholder_familiar.disabled", "Ring Effect - Beholder: Disabled");
        this.add("message.occultism.familiar.fairy_familiar.enabled", "Ring Effect - Fairy: Enabled");
        this.add("message.occultism.familiar.fairy_familiar.disabled", "Ring Effect - Fairy: Disabled");
        this.add("message.occultism.familiar.mummy_familiar.enabled", "Ring Effect - Mummy: Enabled");
        this.add("message.occultism.familiar.mummy_familiar.disabled", "Ring Effect - Mummy: Disabled");
        this.add("message.occultism.familiar.beaver_familiar.enabled", "Ring Effect - Beaver: Enabled");
        this.add("message.occultism.familiar.beaver_familiar.disabled", "Ring Effect - Beaver: Disabled");
    }

    private void addPentacles() {
        this.addPentacle("otherworld_bird", "Otherworld Bird");
        this.addPentacle("craft_afrit", "Sevira's Permanent Confinement");
        this.addPentacle("craft_djinni", "Strigeor's Higher Binding");
        this.addPentacle("craft_foliot", "Eziveus' Spectral Compulsion");
        this.addPentacle("craft_marid", "Uphyxes Inverted Tower");
        this.addPentacle("possess_afrit", "Abras' Commanding Conjure");
        this.addPentacle("possess_djinni", "Ihagan's Enthrallment");
        this.addPentacle("possess_foliot", "Hedyrin's Lure");
        this.addPentacle("summon_afrit", "Abras' Conjure");
        this.addPentacle("summon_djinni", "Ophyx' Calling");
        this.addPentacle("summon_foliot", "Aviar's Circle");
        this.addPentacle("summon_wild_afrit", "Abras' Open Conjure");
        this.addPentacle("summon_marid", "Fatma's Incentivized Attraction");
        this.addPentacle("summon_wild_greater_spirit", "Osorin's Unbound Calling");
    }

    private void addPentacle(String id, String name) {
        this.add(Util.makeDescriptionId("multiblock", new ResourceLocation(Occultism.MODID, id)), name);
    }

    private void addRitualDummies() {
        this.add("item.occultism.ritual_dummy.custom_ritual", "Custom Ritual Dummy");
        this.add("item.occultism.ritual_dummy.custom_ritual.tooltip", "Used for modpacks as a fallback for custom rituals that do not have their own ritual item.");
        this.add("item.occultism.ritual_dummy.craft_dimensional_matrix", "Ritual: Craft Dimensional Matrix");
        this.add("item.occultism.ritual_dummy.craft_dimensional_matrix.tooltip", "The dimensional matrix is the entry point to a small dimension used for storing items.");
        this.add("item.occultism.ritual_dummy.craft_dimensional_mineshaft", "Ritual: Craft Dimensional Mineshaft");
        this.add("item.occultism.ritual_dummy.craft_dimensional_mineshaft.tooltip", "Allows miner spirits to enter the mining dimension and bring back resources.");
        this.add("item.occultism.ritual_dummy.craft_infused_lenses", "Ritual: Craft Infused Lenses");
        this.add("item.occultism.ritual_dummy.craft_infused_lenses.tooltip", "These lenses are used to craft spectacles that give thee ability to see beyond the physical world.");
        this.add("item.occultism.ritual_dummy.craft_infused_pickaxe", "Ritual: Craft Infused Pickaxe");
        this.add("item.occultism.ritual_dummy.craft_infused_pickaxe.tooltip", "Infuse a Pickaxe.");

        this.add("item.occultism.ritual_dummy.craft_miner_djinni_ores", "Ritual: Summon Djinni Ore Miner");
        this.add("item.occultism.ritual_dummy.craft_miner_djinni_ores.tooltip", "Summon Djinni Ore Miner into a magic lamp.");
        this.add("item.occultism.ritual_dummy.craft_miner_foliot_unspecialized", "Ritual: Summon Foliot Miner");
        this.add("item.occultism.ritual_dummy.craft_miner_foliot_unspecialized.tooltip", "Summon Foliot Miner into a magic lamp.");
        this.add("item.occultism.ritual_dummy.craft_miner_afrit_deeps", "Ritual: Summon Afrit Deep Ore Miner");
        this.add("item.occultism.ritual_dummy.craft_miner_afrit_deeps.tooltip", "Summon Afrit Deep Ore Miner into a magic lamp.");
        this.add("item.occultism.ritual_dummy.craft_miner_marid_master", "Ritual: Summon Marid Master Miner");
        this.add("item.occultism.ritual_dummy.craft_miner_marid_master.tooltip", "Summon Marid Master Miner into a magic lamp.");

        this.add("item.occultism.ritual_dummy.craft_satchel", "Ritual: Craft Surprisingly Substantial Satchel");
        this.add("item.occultism.ritual_dummy.craft_satchel.tooltip", "This satchels allows to store more items than it's size would indicate, making it a practical traveller's companion.");
        this.add("item.occultism.ritual_dummy.craft_soul_gem", "Ritual: Craft Soul Gem");
        this.add("item.occultism.ritual_dummy.craft_soul_gem.tooltip", "The soul gem allows the temporary storage of living beings. ");
        this.add("item.occultism.ritual_dummy.craft_familiar_ring", "Ritual: Craft Familiar Ring");
        this.add("item.occultism.ritual_dummy.craft_familiar_ring.tooltip", "The familiar ring allows to store familiars. The ring will apply the familiar effect to the wearer.");
        this.add("item.occultism.ritual_dummy.craft_stabilizer_tier1", "Ritual: Craft Storage Stabilizer Tier 1");
        this.add("item.occultism.ritual_dummy.craft_stabilizer_tier1.tooltip", "The storage stabilizer allows to store more items in the dimensional storage accessor.");
        this.add("item.occultism.ritual_dummy.craft_stabilizer_tier2", "Ritual: Craft Storage Stabilizer Tier 2");
        this.add("item.occultism.ritual_dummy.craft_stabilizer_tier2.tooltip", "The storage stabilizer allows to store more items in the dimensional storage accessor.");
        this.add("item.occultism.ritual_dummy.craft_stabilizer_tier3", "Ritual: Craft Storage Stabilizer Tier 3");
        this.add("item.occultism.ritual_dummy.craft_stabilizer_tier3.tooltip", "The storage stabilizer allows to store more items in the dimensional storage accessor.");
        this.add("item.occultism.ritual_dummy.craft_stabilizer_tier4", "Ritual: Craft Storage Stabilizer Tier 4");
        this.add("item.occultism.ritual_dummy.craft_stabilizer_tier4.tooltip", "The storage stabilizer allows to store more items in the dimensional storage accessor.");
        this.add("item.occultism.ritual_dummy.craft_stable_wormhole", "Ritual: Craft Stable Wormhole");
        this.add("item.occultism.ritual_dummy.craft_stable_wormhole.tooltip", "The stable wormhole allows access to a dimensional matrix from a remote destination.");
        this.add("item.occultism.ritual_dummy.craft_storage_controller_base", "Ritual: Craft Storage Actuator Base");
        this.add("item.occultism.ritual_dummy.craft_storage_controller_base.tooltip", "The storage actuator base imprisons a Foliot responsible for interacting with items in a dimensional storage matrix.");
        this.add("item.occultism.ritual_dummy.craft_storage_remote", "Ritual: Craft Storage Accessor");
        this.add("item.occultism.ritual_dummy.craft_storage_remote.tooltip", "The Storage Accessor can be linked to a Storage Actuator to remotely access items.");
        this.add("item.occultism.ritual_dummy.familiar_otherworld_bird", "Ritual: Summon Drikwing Familiar");
        this.add("item.occultism.ritual_dummy.familiar_otherworld_bird.tooltip", "Drikwings will provide their owner with limited flight abilities when nearby.");
        this.add("item.occultism.ritual_dummy.familiar_parrot", "Ritual: Summon Parrot Familiar");
        this.add("item.occultism.ritual_dummy.familiar_parrot.tooltip", "Parrot familiars behave exactly like tamed parrots.");
        this.add("item.occultism.ritual_dummy.familiar_greedy", "Ritual: Summon Greedy Familiar");
        this.add("item.occultism.ritual_dummy.familiar_greedy.tooltip", "Greedy familiars pick up items for their master. When stored in a familiar ring, they increase the pickup range (like an item magnet).");
        this.add("item.occultism.ritual_dummy.familiar_bat", "Ritual: Summon Bat Familiar");
        this.add("item.occultism.ritual_dummy.familiar_bat.tooltip", "Bat familiars provide night vision to their master.");
        this.add("item.occultism.ritual_dummy.familiar_deer", "Ritual: Summon Deer Familiar");
        this.add("item.occultism.ritual_dummy.familiar_deer.tooltip", "Deer familiars provide jump boost to their master.");
        this.add("item.occultism.ritual_dummy.familiar_cthulhu", "Ritual: Summon Cthulhu Familiar");
        this.add("item.occultism.ritual_dummy.familiar_cthulhu.tooltip", "The cthulhu familiars provide water breathing to their master.");
        this.add("item.occultism.ritual_dummy.familiar_devil", "Ritual: Summon Devil Familiar");
        this.add("item.occultism.ritual_dummy.familiar_devil.tooltip", "The devil familiars provide fire resistance to their master.");
        this.add("item.occultism.ritual_dummy.familiar_dragon", "Ritual: Summon Dragon Familiar");
        this.add("item.occultism.ritual_dummy.familiar_dragon.tooltip", "The dragon familiars provide increased experience gain to their master.");
        this.add("item.occultism.ritual_dummy.familiar_blacksmith", "Ritual: Summon Blacksmith Familiar");
        this.add("item.occultism.ritual_dummy.familiar_blacksmith.tooltip", "The blacksmith familiars take stone their master mines and uses it to repair equipment.");
        this.add("item.occultism.ritual_dummy.familiar_guardian", "Ritual: Summon Guardian Familiar");
        this.add("item.occultism.ritual_dummy.familiar_guardian.tooltip", "The guardian familiars prevent their master's violent demise.");
        this.add("item.occultism.ritual_dummy.familiar_headless", "Ritual: Summon Headless Ratman Familiar");
        this.add("item.occultism.ritual_dummy.familiar_headless.tooltip", "The headless ratman familiars increase their master's attack damage against enemies of the kind it stole the head from.");
        this.add("item.occultism.ritual_dummy.familiar_chimera", "Ritual: Summon Chimera Familiar");
        this.add("item.occultism.ritual_dummy.familiar_chimera.tooltip", "The chimera familiars can be fed to grow in size and gain attack speed and damage. Once big enough, playeres can ride them.");
        this.add("item.occultism.ritual_dummy.familiar_beholder", "Ritual: Summon Beholder Familiar");
        this.add("item.occultism.ritual_dummy.familiar_beholder.tooltip", "The beholder familiars highlight nearby entities with a glow effect and shoot laser rays at enemies.");
        this.add("item.occultism.ritual_dummy.familiar_fairy", "Ritual: Summon Fairy Familiar");
        this.add("item.occultism.ritual_dummy.familiar_fairy.tooltip", "The fairy familiar keeps other familiars from dying, drains enemies of their life force and heals its master and their familiars.");
        this.add("item.occultism.ritual_dummy.familiar_mummy", "Ritual: Summon Mummy Familiar");
        this.add("item.occultism.ritual_dummy.familiar_mummy.tooltip", "The Mummy familiar is a martial arts expert and fights to protect their master.");
        this.add("item.occultism.ritual_dummy.familiar_beaver", "Ritual: Summon Beaver Familiar");
        this.add("item.occultism.ritual_dummy.familiar_beaver.tooltip", "The Beaver familiar provides increased woodcutting speed to their masters and harvests nearby trees when they grow from a sapling.");
        this.add("item.occultism.ritual_dummy.possess_enderman", "Ritual: Summon Possessed Enderman");
        this.add("item.occultism.ritual_dummy.possess_enderman.tooltip", "The possessed Enderman will always drop at least one ender pearl when killed.");
        this.add("item.occultism.ritual_dummy.possess_endermite", "Ritual: Summon Possessed Endermite");
        this.add("item.occultism.ritual_dummy.possess_endermite.tooltip", "The possessed Endermite drops End Stone.");
        this.add("item.occultism.ritual_dummy.possess_skeleton", "Ritual: Summon Possessed Skeleton");
        this.add("item.occultism.ritual_dummy.possess_skeleton.tooltip", " The possessed Skeleton is immune to daylight and always drop at least one Skeleton Skull when killed.");
        this.add("item.occultism.ritual_dummy.possess_ghast", "Ritual: Summon Possessed Ghast");
        this.add("item.occultism.ritual_dummy.possess_ghast.tooltip", "The possessed Ghast will always drop at least one ghast tear when killed.");
        this.add("item.occultism.ritual_dummy.summon_afrit_rain_weather", "Ritual: Rainy Weather");
        this.add("item.occultism.ritual_dummy.summon_afrit_rain_weather.tooltip", "Summons an bound Afrit that creates rain.");
        this.add("item.occultism.ritual_dummy.summon_afrit_thunder_weather", "Ritual: Thunderstorm");
        this.add("item.occultism.ritual_dummy.summon_afrit_thunder_weather.tooltip", "Summons an bound Afrit that creates a thunderstorm.");
        this.add("item.occultism.ritual_dummy.summon_djinni_clear_weather", "Ritual: Clear Weather");
        this.add("item.occultism.ritual_dummy.summon_djinni_clear_weather.tooltip", "Summons a Djinni that clears the weather.");
        this.add("item.occultism.ritual_dummy.summon_djinni_day_time", "Ritual: Summoning of Dawn");
        this.add("item.occultism.ritual_dummy.summon_djinni_day_time.tooltip", "Summons a Djinni that sets the time to high noon.");
        this.add("item.occultism.ritual_dummy.summon_djinni_manage_machine", "Ritual: Summon Djinni Machine Operator");
        this.add("item.occultism.ritual_dummy.summon_djinni_manage_machine.tooltip", "The machine operator automatically transfers items between Dimensional Storage Systems and connected Inventories and Machines.");
        this.add("item.occultism.ritual_dummy.summon_djinni_night_time", "Ritual: Summoning of Dusk");
        this.add("item.occultism.ritual_dummy.summon_djinni_night_time.tooltip", "Summons a Djinni that sets the time to midnight.");
        this.add("item.occultism.ritual_dummy.summon_foliot_crusher", "Ritual: Summon Foliot Crusher");
        this.add("item.occultism.ritual_dummy.summon_foliot_crusher.tooltip", "The crusher is a spirit summoned to crush ores into dusts, effectively doubling the metal output.");
        this.add("item.occultism.ritual_dummy.summon_djinni_crusher", "Ritual: Summon Djinni Crusher");
        this.add("item.occultism.ritual_dummy.summon_djinni_crusher.tooltip", "The crusher is a spirit summoned to crush ores into dusts, effectively (more than) doubling the metal output. This crusher decays (much) slower than lower tier crushers.");
        this.add("item.occultism.ritual_dummy.summon_afrit_crusher", "Ritual: Summon Afrit Crusher");
        this.add("item.occultism.ritual_dummy.summon_afrit_crusher.tooltip", "The crusher is a spirit summoned to crush ores into dusts, effectively (more than) doubling the metal output. This crusher decays (much) slower than lower tier crushers.");
        this.add("item.occultism.ritual_dummy.summon_marid_crusher", "Ritual: Summon Marid Crusher");
        this.add("item.occultism.ritual_dummy.summon_marid_crusher.tooltip", "The crusher is a spirit summoned to crush ores into dusts, effectively (more than) doubling the metal output. This crusher decays (much) slower than lower tier crushers.");
        this.add("item.occultism.ritual_dummy.summon_foliot_lumberjack", "Ritual: Summon Foliot Lumberjack");
        this.add("item.occultism.ritual_dummy.summon_foliot_lumberjack.tooltip", "The lumberjack will harvest trees in it's working area and deposit the dropped items into the specified chest.");
        this.add("item.occultism.ritual_dummy.summon_foliot_otherstone_trader", "Ritual: Summon Otherstone Trader");
        this.add("item.occultism.ritual_dummy.summon_foliot_otherstone_trader.tooltip", "The otherstone trader trades normal stone for otherstone.");
        this.add("item.occultism.ritual_dummy.summon_foliot_sapling_trader", "Ritual: Summon Otherworld Sapling Trader");
        this.add("item.occultism.ritual_dummy.summon_foliot_sapling_trader.tooltip", "he otherworld sapling trader trades natural otherworld saplings for stable ones, that can be harvested without the third eye.");
        this.add("item.occultism.ritual_dummy.summon_foliot_transport_items", "Ritual: Summon Foliot Transporter");
        this.add("item.occultism.ritual_dummy.summon_foliot_transport_items.tooltip", "The transporter will move all items it can access from one inventory to another, including machines.");
        this.add("item.occultism.ritual_dummy.summon_foliot_cleaner", "Ritual: Summon Foliot Janitor");
        this.add("item.occultism.ritual_dummy.summon_foliot_cleaner.tooltip", "The janitor will pick up dropped items and deposit them into a target inventory.");
        this.add("item.occultism.ritual_dummy.summon_wild_afrit", "Ritual: Summon Unbound Afrit");
        this.add("item.occultism.ritual_dummy.summon_wild_afrit.tooltip", "Summons an unbound Afrit that can be killed to obtain Afrit Essence");
        this.add("item.occultism.ritual_dummy.summon_wild_hunt", "Ritual: Summon The Wild Hunt");
        this.add("item.occultism.ritual_dummy.summon_wild_hunt.tooltip", "The Wild Hunt consists of Wither Skeletons that are guaranteed to drop Wither Skeleton Skulls, and their minions.");
        this.add("item.occultism.ritual_dummy.summon_wild_otherworld_bird", "Ritual: Summon Wild Drikwing");
        this.add("item.occultism.ritual_dummy.summon_wild_otherworld_bird.tooltip", "Summons a Drikwing Familiar that can be tamed by anyone, not just the summoner.");
        this.add("item.occultism.ritual_dummy.summon_wild_parrot", "Ritual: Summon Wild Parrot");
        this.add("item.occultism.ritual_dummy.summon_wild_parrot.tooltip", "Summons a Parrot that can be tamed by anyone, not just the summoner.");
    }

    private void addDialogs() {
        this.add("dialog.occultism.dragon.pet", "purrr");
        this.add("dialog.occultism.mummy.kapow", "KAPOW!");
    }

    private void addPatchouli() {
        this.add("book.occultism.name", "Dictionary of Spirits (Old Edition)");
        this.add("pentacle.occultism.craft_djinni", "Strigeor's Higher Binding");
        this.add("pentacle.occultism.craft_foliot", "Eziveus' Spectral Compulsion");
    }

    private void addModonomiconIntegration() {
        this.add(OccultismModonomiconConstants.I18n.RITUAL_RECIPE_ITEM_USE, "Item to use:");
        this.add(OccultismModonomiconConstants.I18n.RITUAL_RECIPE_SUMMON, "Summon: %s");
        this.add(OccultismModonomiconConstants.I18n.RITUAL_RECIPE_JOB, "Job: %s");
        this.add(OccultismModonomiconConstants.I18n.RITUAL_RECIPE_SACRIFICE, "Sacrifice: %s");
        this.add(I18n.RITUAL_RECIPE_GO_TO_PENTACLE, "Open Pentacle Page: %s");
    }

    private void advancementTitle(String name, String s) {
        this.add(((TranslatableContents) OccultismAdvancementProvider.title(name).getContents()).getKey(), s);
    }

    private void advancementDescr(String name, String s) {
        this.add(((TranslatableContents) OccultismAdvancementProvider.descr(name).getContents()).getKey(), s);
    }

    @Override
    protected void addTranslations() {
        this.addAdvancements();
        this.addItems();
        this.addItemMessages();
        this.addItemTooltips();
        this.addBlocks();
        this.addBook();
        this.addEntities();
        this.addMiscTranslations();
        this.addRitualMessages();
        this.addGuiTranslations();
        this.addKeybinds();
        this.addJeiTranslations();
        this.addFamiliarSettingsMessages();
        this.addRitualDummies();
        this.addDialogs();
        this.addPentacles();
        this.addPatchouli(); //TODO: remove once no longer needed
        this.addModonomiconIntegration();
    }
}
