/*
 * MIT License
 *
 * Copyright 2021 vemerion, klikli-dev, AL7X
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
import com.github.klikli_dev.occultism.registry.OccultismBlocks;
import com.github.klikli_dev.occultism.registry.OccultismEntities;
import com.github.klikli_dev.occultism.registry.OccultismItems;
import com.klikli_dev.modonomicon.api.ModonomiconAPI;
import com.klikli_dev.modonomicon.api.datagen.BookLangHelper;
import net.minecraft.Util;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.data.LanguageProvider;

public class FRFRProvider extends LanguageProvider {

    public static final String COLOR_PURPLE = "ad03fc";
    public static final String DEMONS_DREAM = "Demon's Dream";


    public FRFRProvider(PackOutput gen) {
        super(gen, Occultism.MODID, "fr_fr");
    }

    public void addItemMessages() {

        //"item\.occultism\.(.*?)\.(.*)": "(.*)",
        // this.add\(OccultismItems.\U\1\E.get\(\).getDescriptionId\(\)  + " \2", "\3"\);

        //book of callings use generic message base key, hence the manual string
        this.add("item.occultism.book_of_calling" + ".message_target_uuid_no_match", "Cet esprit n'est pas actuellement lié à ce livre. Shift-Cliquez sur l'esprit pour le lier à ce livre.");
        this.add("item.occultism.book_of_calling" + ".message_target_linked", "Cet esprit est maintenant lié à ce livre.");
        this.add("item.occultism.book_of_calling" + ".message_target_cannot_link", "Cet esprit ne peut être lié à ce livre - le livre d'appel doit correspondre à la tâche de l'esprit !");
        this.add("item.occultism.book_of_calling" + ".message_target_entity_no_inventory", "Cette entité n'a pas d'inventaire, elle ne peut pas être définie comme lieu de dépôt.");
        this.add("item.occultism.book_of_calling" + ".message_spirit_not_found", "L'esprit lié à ce livre ne réside pas sur ce plan d'existence.");
        this.add("item.occultism.book_of_calling" + ".message_set_deposit", "%s sera désormais déposé dans %s depuis le côté: %s");
        this.add("item.occultism.book_of_calling" + ".message_set_deposit_entity", "%s va maintenant remettre des objets à : %s");
        this.add("item.occultism.book_of_calling" + ".message_set_extract", "%s va maintenant extraire de %s du côté: %s");
        this.add("item.occultism.book_of_calling" + ".message_set_base", "Définir la base pour %s à %s");
        this.add("item.occultism.book_of_calling" + ".message_set_storage_controller", "%s acceptera désormais les bons de travail de %s");
        this.add("item.occultism.book_of_calling" + ".message_set_work_area_size", "%s va maintenant surveiller une zone de travail de %s");
        this.add("item.occultism.book_of_calling" + ".message_set_managed_machine", "Mise à jour des paramètres de la machine pour %s");
        this.add("item.occultism.book_of_calling" + ".message_set_managed_machine_extract_location", "%s va maintenant extraire de %s du côté: %s");
        this.add("item.occultism.book_of_calling" + ".message_no_managed_machine", "Définir une machine gérée avant de définir un emplacement d'extraction %s");

        this.add(OccultismItems.STABLE_WORMHOLE.get().getDescriptionId() + ".message.set_storage_controller", "J'ai lié le vortex stable à cet actionneur de stockage..");
        this.add(OccultismItems.STORAGE_REMOTE.get().getDescriptionId() + ".message.not_loaded", "Chunk pour actionneur de stockage non chargé !");
        this.add(OccultismItems.STORAGE_REMOTE.get().getDescriptionId() + ".message.linked", "Stockage lié à distance à l'actionneur.");
        this.add(OccultismItems.DIVINATION_ROD.get().getDescriptionId() + ".message.no_linked_block", "La bâton de divination ne s'accorde avec aucun matériau.");
        this.add(OccultismItems.DIVINATION_ROD.get().getDescriptionId() + ".message.linked_block", "Le bâton de divination est maintenant accordé à %s.");
        this.add(OccultismItems.DIVINATION_ROD.get().getDescriptionId() + ".message.no_link_found", "Il n'y a pas de résonance avec ce bloc.");
        this.add(OccultismItems.SOUL_GEM_ITEM.get().getDescriptionId() + ".message.entity_type_denied", "Les gemmes de l'âme ne peuvent pas contenir ce type d'être.");
    }

    public void addItemTooltips() {
        //"item\.occultism\.(.*?)\.(.*)": "(.*)",
        // this.add\(OccultismItems.\U\1\E.get\(\).getDescriptionId\(\)  + " \2", "\3"\);

        this.add(OccultismItems.BOOK_OF_BINDING_FOLIOT.get().getDescriptionId() + ".tooltip", "Ce livre n'a pas encore été relié à un foliot.");
        this.add(OccultismItems.BOOK_OF_BINDING_BOUND_FOLIOT.get().getDescriptionId() + ".tooltip", "Peut être utilisé pour invoquer le foliot. %s");
        this.add(OccultismItems.BOOK_OF_BINDING_DJINNI.get().getDescriptionId() + ".tooltip", "Ce livre n'a pas encore été lié à un djinni.");
        this.add(OccultismItems.BOOK_OF_BINDING_BOUND_DJINNI.get().getDescriptionId() + ".tooltip", "Peut être utilisé pour invoquer le djinni. %s");
        this.add(OccultismItems.BOOK_OF_BINDING_AFRIT.get().getDescriptionId() + ".tooltip", "Ce livre n'a pas encore été relié à un afrit.");
        this.add(OccultismItems.BOOK_OF_BINDING_BOUND_AFRIT.get().getDescriptionId() + ".tooltip", "Peut être utilisé pour invoquer l'afrit %s");
        this.add(OccultismItems.BOOK_OF_BINDING_MARID.get().getDescriptionId() + ".tooltip", "Ce livre n'a pas encore été relié à un marid.");
        this.add(OccultismItems.BOOK_OF_BINDING_BOUND_MARID.get().getDescriptionId() + ".tooltip", "Peut être utilisé pour invoquer le marid %s");

        this.add("item.occultism.book_of_calling_foliot" + ".tooltip", "Foliot %s");
        this.add("item.occultism.book_of_calling_foliot" + ".tooltip_dead", "%s a quitté ce plan d'existence.");
        this.add("item.occultism.book_of_calling_foliot" + ".tooltip.extract", "Extraits de: %s.");
        this.add("item.occultism.book_of_calling_foliot" + ".tooltip.deposit", "Dépôts à : %s.");
        this.add("item.occultism.book_of_calling_foliot" + ".tooltip.deposit_entity", "Remettre les articles à : %s.");
        this.add("item.occultism.book_of_calling_djinni" + ".tooltip", "Djinni %s");
        this.add("item.occultism.book_of_calling_djinni" + ".tooltip_dead", "%s a quitté ce plan d'existence.");
        this.add("item.occultism.book_of_calling_djinni" + ".tooltip.extract", "Extraits de : %s.");
        this.add("item.occultism.book_of_calling_djinni" + ".tooltip.deposit", "Dépôts à : % s");
        this.add(OccultismItems.FAMILIAR_RING.get().getDescriptionId() + ".tooltip", "Occupé par le familier %s");

        this.add("item.minecraft.diamond_sword.occultism_spirit_tooltip", "%s est lié à cette épée. Que vos ennemis tremblent devant sa gloire.");

        this.add(OccultismItems.STABLE_WORMHOLE.get().getDescriptionId() + ".tooltip.unlinked", "Non lié à un actionneur de stockage.");
        this.add(OccultismItems.STABLE_WORMHOLE.get().getDescriptionId() + ".tooltip.linked", "Lié à l'actionneur de stockage à %s.");
        this.add(OccultismItems.STORAGE_REMOTE.get().getDescriptionId() + ".tooltip", "Accéder à un réseau de stockage à distance.");

        this.add(OccultismItems.STORAGE_REMOTE.get().getDescriptionId() + ".tooltip.linked", "Lié à %s.");
        this.add(OccultismItems.DIVINATION_ROD.get().getDescriptionId() + ".tooltip.no_linked_block", "Je ne m'accorde avec aucun matériau.");
        this.add(OccultismItems.DIVINATION_ROD.get().getDescriptionId() + ".tooltip.linked_block", "En accord avec %s.");
        this.add(OccultismItems.DIMENSIONAL_MATRIX.get().getDescriptionId() + ".tooltip", "%s est lié à cette matrice dimensionnelle.");
        this.add(OccultismItems.INFUSED_PICKAXE.get().getDescriptionId() + ".tooltip", "%s est lié à cette pioche.");
        this.add(OccultismItems.MINER_FOLIOT_UNSPECIALIZED.get().getDescriptionId() + ".tooltip", "%s va extraire des blocs aléatoires dans la dimension minière.");
        this.add(OccultismItems.MINER_DJINNI_ORES.get().getDescriptionId() + ".tooltip", "%s va extraire des minerais aléatoires dans la dimension minière.");
        this.add(OccultismItems.MINER_DEBUG_UNSPECIALIZED.get().getDescriptionId() + ".tooltip", "Debug Miner va extraire des blocs aléatoires dans la dimension minière.");
        this.add(OccultismItems.SOUL_GEM_ITEM.get().getDescriptionId() + ".tooltip_filled", "Contient une capture %s.");
        this.add(OccultismItems.SOUL_GEM_ITEM.get().getDescriptionId() + ".tooltip_empty", "A utiliser sur une créature pour la capturer.");
        this.add(OccultismItems.SATCHEL.get().getDescriptionId() + ".tooltip", "%s est lié à cette sacoche.");
    }

    private void addItems() {
        //Notepad++ magic:
        //"item\.occultism\.(.*)": "(.*)"
        //this.addItem\(OccultismItems.\U\1\E, "\2"\);

        this.add("itemGroup.occultism", "Occultism");

        this.addItem(OccultismItems.PENTACLE, "Pentacle");
        this.addItem(OccultismItems.DEBUG_WAND, "Baguette de débogage");
        this.addItem(OccultismItems.DEBUG_FOLIOT_LUMBERJACK, "Invocation du bûcheron Debug Foliot");
        this.addItem(OccultismItems.DEBUG_FOLIOT_TRANSPORT_ITEMS, "Invocation d'un transporteur de foliots de débogage");
        this.addItem(OccultismItems.DEBUG_FOLIOT_CLEANER, "Invocation du concierge de Debug Foliot");
        this.addItem(OccultismItems.DEBUG_FOLIOT_TRADER_ITEM, "Invocation de Debug Foliot Trader");
        this.addItem(OccultismItems.DEBUG_DJINNI_MANAGE_MACHINE, "Summon Debug Djinni Manage Machine");
        this.addItem(OccultismItems.DEBUG_DJINNI_TEST, "Invocation Debug Djinni Test");

        this.addItem(OccultismItems.CHALK_GOLD, "Craie dorée");
        this.addItem(OccultismItems.CHALK_PURPLE, "Craie violette");
        this.addItem(OccultismItems.CHALK_RED, "Craie rouge");
        this.addItem(OccultismItems.CHALK_WHITE, "Craie blanche");
        this.addItem(OccultismItems.CHALK_GOLD_IMPURE, "Craie dorée impure");
        this.addItem(OccultismItems.CHALK_PURPLE_IMPURE, "Craie violette impure");
        this.addItem(OccultismItems.CHALK_RED_IMPURE, "Craie rouge impure");
        this.addItem(OccultismItems.CHALK_WHITE_IMPURE, "Craie blanche impure");
        this.addItem(OccultismItems.BRUSH, "Pinceau à craie");
        this.addItem(OccultismItems.AFRIT_ESSENCE, "Afrit Essence");
        this.addItem(OccultismItems.PURIFIED_INK, "Encre purifiée");
        this.addItem(OccultismItems.BOOK_OF_BINDING_FOLIOT, "Livre de reliure : Foliot");
        this.addItem(OccultismItems.BOOK_OF_BINDING_BOUND_FOLIOT, "Livre de la Reliure : Foliot (Relié)");
        this.addItem(OccultismItems.BOOK_OF_BINDING_DJINNI, "Livre de reliure : Djinni");
        this.addItem(OccultismItems.BOOK_OF_BINDING_BOUND_DJINNI, "Livre de la Reliure : Djinni (Lié)");
        this.addItem(OccultismItems.BOOK_OF_BINDING_AFRIT, "Livre de reliure : Afrit");
        this.addItem(OccultismItems.BOOK_OF_BINDING_BOUND_AFRIT, "Livre de la Reliure : Afrit (Relié)");
        this.addItem(OccultismItems.BOOK_OF_BINDING_MARID, "Livre de reliure : Marid");
        this.addItem(OccultismItems.BOOK_OF_BINDING_BOUND_MARID, "Livre de la Reliure : Marid (Relié)");
        this.addItem(OccultismItems.BOOK_OF_CALLING_FOLIOT_LUMBERJACK, "Livre d'appel : Foliot Lumberjack");
        this.addItem(OccultismItems.BOOK_OF_CALLING_FOLIOT_TRANSPORT_ITEMS, "Livre d'appel : Transporteur de folioles");
        this.addItem(OccultismItems.BOOK_OF_CALLING_FOLIOT_CLEANER, "Livre d'appel : Concierge foliot");
        this.addItem(OccultismItems.BOOK_OF_CALLING_DJINNI_MANAGE_MACHINE, "Livre d'appel : Opérateur de machine Djinni");
        this.addItem(OccultismItems.STORAGE_REMOTE, "Accessoire de stockage");
        this.addItem(OccultismItems.STORAGE_REMOTE_INERT, "Accessoire de stockage inerte");
        this.addItem(OccultismItems.DIMENSIONAL_MATRIX, "Matrice cristalline dimensionnelle");
        this.addItem(OccultismItems.DIVINATION_ROD, "Bâton de divination");
        this.addItem(OccultismItems.DATURA_SEEDS, "Graines de rêve du démon");
        this.addItem(OccultismItems.DATURA, "Fruit du rêve du démon");
        this.addItem(OccultismItems.SPIRIT_ATTUNED_GEM, "Gemme accordée à l'esprit");
        this.addItem(OccultismItems.OTHERWORLD_SAPLING_NATURAL, "Jeune pousse instable de l'Autre Monde");
        this.addItem(OccultismItems.OTHERWORLD_ASHES, "Cendres de l'autre monde");
        this.addItem(OccultismItems.BURNT_OTHERSTONE, "Pierres étrangères brûlées");
        this.addItem(OccultismItems.BUTCHER_KNIFE, "Couteau de boucher");
        this.addItem(OccultismItems.TALLOW, "Suif");
        this.addItem(OccultismItems.OTHERSTONE_FRAME, "Cadre de pierres étranges");
        this.addItem(OccultismItems.OTHERSTONE_TABLET, "Tablette de pierres étranges");
        this.addItem(OccultismItems.WORMHOLE_FRAME, "Cadre de vortex");
        this.addItem(OccultismItems.IRON_DUST, "Poussière de fer");
        this.addItem(OccultismItems.OBSIDIAN_DUST, "Poussière d'obsidienne");
        this.addItem(OccultismItems.CRUSHED_END_STONE, "Pierre d'extrémité concassée");
        this.addItem(OccultismItems.GOLD_DUST, "Poussière d'or");
        this.addItem(OccultismItems.COPPER_DUST, "Poussière de cuivre");
        this.addItem(OccultismItems.SILVER_DUST, "Poussière d'argent");
        this.addItem(OccultismItems.IESNIUM_DUST, "Poussière d'IESNIUM");
        this.addItem(OccultismItems.RAW_SILVER, "Argent brut");
        this.addItem(OccultismItems.RAW_IESNIUM, "Brut Iesnium");
        this.addItem(OccultismItems.SILVER_INGOT, "Lingot d'argent");
        this.addItem(OccultismItems.IESNIUM_INGOT, "L'ingot d'Iesnium ");
        this.addItem(OccultismItems.SILVER_NUGGET, "Pépite d'argent");
        this.addItem(OccultismItems.IESNIUM_NUGGET, "Pépite d'Iesnium");
        this.addItem(OccultismItems.LENSES, "Lentilles en verre");
        this.addItem(OccultismItems.INFUSED_LENSES, "Lentilles infusées");
        this.addItem(OccultismItems.LENS_FRAME, "Cadre de l'objectif");
        this.addItem(OccultismItems.OTHERWORLD_GOGGLES, "Lunettes de protection de l'autre monde");
        this.addItem(OccultismItems.INFUSED_PICKAXE, "Pioche infusée");
        this.addItem(OccultismItems.SPIRIT_ATTUNED_PICKAXE_HEAD, "Tête de pioche accordée à l'esprit");
        this.addItem(OccultismItems.IESNIUM_PICKAXE, "Pioche en Iesnium");
        this.addItem(OccultismItems.MAGIC_LAMP_EMPTY, "Lampe magique vide");
        this.addItem(OccultismItems.MINER_FOLIOT_UNSPECIALIZED, "Mineur Foliot");
        this.addItem(OccultismItems.MINER_DJINNI_ORES, "Ore Miner Djinni");
        this.addItem(OccultismItems.MINER_DEBUG_UNSPECIALIZED, "Débogueur mineur");
        this.addItem(OccultismItems.SOUL_GEM_ITEM, "Gemme de l'âme");
        this.add(OccultismItems.SOUL_GEM_ITEM.get().getDescriptionId() + "_empty", "Gemme de l'âme vide");
        this.addItem(OccultismItems.SATCHEL, "Sacoche étonnamment substantielle");
        this.addItem(OccultismItems.FAMILIAR_RING, "Anneau familial");
        this.addItem(OccultismItems.SPAWN_EGG_FOLIOT, "Œuf de ponte de foliot");
        this.addItem(OccultismItems.SPAWN_EGG_DJINNI, "Œuf de ponte de Djinni");
        this.addItem(OccultismItems.SPAWN_EGG_AFRIT, "Œuf de ponte Afrit");
        this.addItem(OccultismItems.SPAWN_EGG_AFRIT_WILD, "Œuf de ponte Afrit non lié");
        this.addItem(OccultismItems.SPAWN_EGG_MARID, "Œuf de ponte maride");
        this.addItem(OccultismItems.SPAWN_EGG_POSSESSED_ENDERMITE, "Œuf de ponte d'Endermite possédé.");
        this.addItem(OccultismItems.SPAWN_EGG_POSSESSED_SKELETON, "Œuf de ponte de squelette possédé");
        this.addItem(OccultismItems.SPAWN_EGG_POSSESSED_ENDERMAN, "L'œuf de l'Enderman possédé");
        this.addItem(OccultismItems.SPAWN_EGG_WILD_HUNT_SKELETON, "Œuf de Squelette de la Chasse Sauvage");
        this.addItem(OccultismItems.SPAWN_EGG_WILD_HUNT_WITHER_SKELETON, "Chasse sauvage Squelette Wither Spawn Egg");
        this.addItem(OccultismItems.SPAWN_EGG_OTHERWORLD_BIRD, "Œuf de ponte de Drikwing");
        this.addItem(OccultismItems.SPAWN_EGG_GREEDY_FAMILIAR, "Œuf de ponte du familier avide");
        this.addItem(OccultismItems.SPAWN_EGG_BAT_FAMILIAR, "Œuf de ponte de chauve-souris familière");
        this.addItem(OccultismItems.SPAWN_EGG_DEER_FAMILIAR, "Œuf de ponte d'un cerf familier");
        this.addItem(OccultismItems.SPAWN_EGG_CTHULHU_FAMILIAR, "Œuf de ponte de Cthulhu Familier");
        this.addItem(OccultismItems.SPAWN_EGG_DEVIL_FAMILIAR, "Œuf de ponte du diable Familier");
        this.addItem(OccultismItems.SPAWN_EGG_DRAGON_FAMILIAR, "Œuf de ponte de Dragon Familier");
        this.addItem(OccultismItems.SPAWN_EGG_BLACKSMITH_FAMILIAR, "Œuf de reproduction du forgeron familier");
        this.addItem(OccultismItems.SPAWN_EGG_GUARDIAN_FAMILIAR, "Œuf de ponte du Gardien familier");
        this.addItem(OccultismItems.SPAWN_EGG_HEADLESS_FAMILIAR, "Œuf de reproduction de l'homme-rat sans tête.");
        this.addItem(OccultismItems.SPAWN_EGG_CHIMERA_FAMILIAR, "Œuf de ponte de Chimère familier");
        this.addItem(OccultismItems.SPAWN_EGG_GOAT_FAMILIAR, "Œuf de ponte de la chèvre familière");
        this.addItem(OccultismItems.SPAWN_EGG_SHUB_NIGGURATH_FAMILIAR, "Œuf de ponte de Shub Niggurath Familier");
        this.addItem(OccultismItems.SPAWN_EGG_BEHOLDER_FAMILIAR, "Œuf de ponte du Beholder familier");
        this.addItem(OccultismItems.SPAWN_EGG_FAIRY_FAMILIAR, "Œuf de ponte du familier des fées");
        this.addItem(OccultismItems.SPAWN_EGG_MUMMY_FAMILIAR, "Œuf de ponte de la momie familière");
        this.addItem(OccultismItems.SPAWN_EGG_BEAVER_FAMILIAR, "Œuf de ponte d'un castor familier");
    }

    private void addBlocks() {
        //"block\.occultism\.(.*?)": "(.*)",
        //this.addBlock\(OccultismItems.\U\1\E, "\2"\);

        this.addBlock(OccultismBlocks.OTHERSTONE, "Pierre étrange");
        this.addBlock(OccultismBlocks.OTHERSTONE_SLAB, "Dalle de pierre étrange");
        this.addBlock(OccultismBlocks.OTHERSTONE_PEDESTAL, "Piédestal de pierre étrange");
        this.addBlock(OccultismBlocks.SACRIFICIAL_BOWL, "Bol sacrificiel");
        this.addBlock(OccultismBlocks.GOLDEN_SACRIFICIAL_BOWL, "Bol sacrificiel en or");
        this.addBlock(OccultismBlocks.CHALK_GLYPH_WHITE, "Glyphe à la craie blanche");
        this.addBlock(OccultismBlocks.CHALK_GLYPH_GOLD, "Glyphe à la craie d'or");
        this.addBlock(OccultismBlocks.CHALK_GLYPH_PURPLE, "Glyphe de la craie violette");
        this.addBlock(OccultismBlocks.CHALK_GLYPH_RED, "Glyphe à la craie rouge");
        this.addBlock(OccultismBlocks.STORAGE_CONTROLLER, "Stockage dimensionnel Actionneur");
        this.addBlock(OccultismBlocks.STORAGE_CONTROLLER_BASE, "Base de l'actionneur de stockage");
        this.addBlock(OccultismBlocks.STORAGE_STABILIZER_TIER1, "Tier 1 Stabilisateur de stockage dimensionnel");
        this.addBlock(OccultismBlocks.STORAGE_STABILIZER_TIER2, "Tier 2 Stabilisateur de stockage dimensionnel");
        this.addBlock(OccultismBlocks.STORAGE_STABILIZER_TIER3, "Tier 3 Stabilisateur de stockage dimensionnel");
        this.addBlock(OccultismBlocks.STORAGE_STABILIZER_TIER4, "Tier 4 Stabilisateur de stockage dimensionnel");
        this.addBlock(OccultismBlocks.STABLE_WORMHOLE, "Vortex stable");
        this.addBlock(OccultismBlocks.DATURA, "Le rêve du démon");
        this.addBlock(OccultismBlocks.OTHERWORLD_LOG, "Bois de l'Autre Monde");
        this.addBlock(OccultismBlocks.OTHERWORLD_SAPLING, "Jeune pousse de l'Autre Monde");
        this.addBlock(OccultismBlocks.OTHERWORLD_LEAVES, "Feuilles d'autres mondes");
        this.addBlock(OccultismBlocks.SPIRIT_FIRE, "Feu de l'esprit");
        this.addBlock(OccultismBlocks.SPIRIT_ATTUNED_CRYSTAL, "Cristal accordé à l'esprit");
        this.addBlock(OccultismBlocks.CANDLE_WHITE, "Bougie blanche");
        this.addBlock(OccultismBlocks.SILVER_ORE, "Minerai d'argent");
        this.addBlock(OccultismBlocks.SILVER_ORE_DEEPSLATE, "Minerai d'argent de Deepslate");
        this.addBlock(OccultismBlocks.IESNIUM_ORE, "Minerai d'Iesnium");
        this.addBlock(OccultismBlocks.SILVER_BLOCK, "Bloc d'argent");
        this.addBlock(OccultismBlocks.IESNIUM_BLOCK, "Bloc de Iesnium");
        this.addBlock(OccultismBlocks.DIMENSIONAL_MINESHAFT, "Mineshaft dimensionnelles");
        this.addBlock(OccultismBlocks.SKELETON_SKULL_DUMMY, "Crâne de Squelette");
        this.addBlock(OccultismBlocks.WITHER_SKELETON_SKULL_DUMMY, "Crâne de wither squelette");
        this.addBlock(OccultismBlocks.LIGHTED_AIR, "Air lumineux");
        this.addBlock(OccultismBlocks.SPIRIT_LANTERN, "Lanterne d'esprit");
        this.addBlock(OccultismBlocks.SPIRIT_CAMPFIRE, "Feu de camp de l'esprit");
        this.addBlock(OccultismBlocks.SPIRIT_TORCH, "Torche spirituelle"); //spirit wall torch automatically uses the same translation
    }

    private void addEntities() {
        //"entity\.occultism\.(.*?)": "(.*)",
        //this.addEntityType\(OccultismEntities.\U\1\E, "\2"\);

        this.addEntityType(OccultismEntities.FOLIOT, "Foliot");
        this.addEntityType(OccultismEntities.DJINNI, "Djinni");
        this.addEntityType(OccultismEntities.AFRIT, "Afrit");
        this.addEntityType(OccultismEntities.AFRIT_WILD, "Afrit Délié");
        this.addEntityType(OccultismEntities.MARID, "Marid");
        this.addEntityType(OccultismEntities.POSSESSED_ENDERMITE, "Endermite possedée");
        this.addEntityType(OccultismEntities.POSSESSED_SKELETON, "Skeleton possedé");
        this.addEntityType(OccultismEntities.POSSESSED_ENDERMAN, "Enderman possedé");
        this.addEntityType(OccultismEntities.POSSESSED_GHAST, "Ghast possedé");
        this.addEntityType(OccultismEntities.WILD_HUNT_SKELETON, "Squelette de chasse sauvage");
        this.addEntityType(OccultismEntities.WILD_HUNT_WITHER_SKELETON, "Chasse sauvage Wither Squelette");
        this.addEntityType(OccultismEntities.OTHERWORLD_BIRD, "Drikwing");
        this.addEntityType(OccultismEntities.GREEDY_FAMILIAR, "Familier gourmand");
        this.addEntityType(OccultismEntities.BAT_FAMILIAR, "chauve-souris Familière");
        this.addEntityType(OccultismEntities.DEER_FAMILIAR, "cerf familier");
        this.addEntityType(OccultismEntities.CTHULHU_FAMILIAR, "Cthulhu familier");
        this.addEntityType(OccultismEntities.DEVIL_FAMILIAR, "Diable familier");
        this.addEntityType(OccultismEntities.DRAGON_FAMILIAR, "Dragon Familier");
        this.addEntityType(OccultismEntities.BLACKSMITH_FAMILIAR, "Forgeron Familier");
        this.addEntityType(OccultismEntities.GUARDIAN_FAMILIAR, "Guardian Familier");
        this.addEntityType(OccultismEntities.HEADLESS_FAMILIAR, "Headless Familier");
        this.addEntityType(OccultismEntities.CHIMERA_FAMILIAR, "chimère Familier");
        this.addEntityType(OccultismEntities.GOAT_FAMILIAR, "Chèbre Familière");
        this.addEntityType(OccultismEntities.SHUB_NIGGURATH_FAMILIAR, "Shub Niggurath Familier");
        this.addEntityType(OccultismEntities.BEHOLDER_FAMILIAR, "Familier du détenteur");
        this.addEntityType(OccultismEntities.FAIRY_FAMILIAR, "Fées familières");
        this.addEntityType(OccultismEntities.MUMMY_FAMILIAR, "Momy familière ");
        this.addEntityType(OccultismEntities.BEAVER_FAMILIAR, "Castor Familier");
        this.addEntityType(OccultismEntities.SHUB_NIGGURATH_SPAWN, "Shub Niggurath Spawn");
        this.addEntityType(OccultismEntities.THROWN_SWORD, "Thrown Sword");
    }

    private void addMiscTranslations() {

        //"(.*?)": "(.*)",
        //this.add\("\1", "\2"\);

        //Jobs
        this.add("job.occultism.lumberjack", "Bûcheron");
        this.add("job.occultism.crush_tier1", "Broyeur lent");
        this.add("job.occultism.crush_tier2", "Concasseur");
        this.add("job.occultism.crush_tier3", "Broyeur rapide");
        this.add("job.occultism.crush_tier4", "Broyeur très rapide");
        this.add("job.occultism.manage_machine", "Opérateur de machine");
        this.add("job.occultism.transport_items", "Transporteur");
        this.add("job.occultism.cleaner", "Concierge");
        this.add("job.occultism.trade_otherstone_t1", "Négociant de pierres étranges");
        this.add("job.occultism.trade_otherworld_saplings_t1", "Négociant en pousses de l'Autre Monde");
        this.add("job.occultism.clear_weather", "L'esprit du soleil");
        this.add("job.occultism.rain_weather", "L'esprit du temps pluvieux");
        this.add("job.occultism.thunder_weather", "Esprit de l'Ouragan");
        this.add("job.occultism.day_time", "Esprit de l'aube");
        this.add("job.occultism.night_time", "Esprit du crépuscule");

        //Enums
        this.add("enum.occultism.facing.up", "Haut");
        this.add("enum.occultism.facing.down", "Bas");
        this.add("enum.occultism.facing.north", "Nord");
        this.add("enum.occultism.facing.south", "Sud");
        this.add("enum.occultism.facing.west", "Ouest");
        this.add("enum.occultism.facing.east", "Est");
        this.add("enum.occultism.book_of_calling.item_mode.set_deposit", "Fixer le dépôt");
        this.add("enum.occultism.book_of_calling.item_mode.set_extract", "Fixer l'extraction");
        this.add("enum.occultism.book_of_calling.item_mode.set_base", "Définir l'emplacement de la base");
        this.add("enum.occultism.book_of_calling.item_mode.set_storage_controller", "Régler l'actionneur de stockage");
        this.add("enum.occultism.book_of_calling.item_mode.set_managed_machine", "Définir la machine gérée");
        this.add("enum.occultism.work_area_size.small", "16x16");
        this.add("enum.occultism.work_area_size.medium", "32x32");
        this.add("enum.occultism.work_area_size.large", "64x64");

        //Debug messages
        this.add("debug.occultism.debug_wand.printed_glyphs", "Glyphes imprimés");
        this.add("debug.occultism.debug_wand.glyphs_verified", "Glyphes vérifiés");
        this.add("debug.occultism.debug_wand.glyphs_not_verified", "Glyphes non vérifiés");
        this.add("debug.occultism.debug_wand.spirit_selected", "Esprit sélectionné avec id %s");
        this.add("debug.occultism.debug_wand.spirit_tamed", "Esprit apprivoisé avec id %s");
        this.add("debug.occultism.debug_wand.deposit_selected", "Blocage du dépôt %s, en face de %s");
        this.add("debug.occultism.debug_wand.no_spirit_selected", "Aucun esprit sélectionné.");

        //Ritual Sacrifices
        this.add("ritual.occultism.sacrifice.cows", "Vache");
        this.add("ritual.occultism.sacrifice.bats", "Chauve-Souris");
        this.add("ritual.occultism.sacrifice.zombies", "Zombie");
        this.add("ritual.occultism.sacrifice.parrots", "Perroquet");
        this.add("ritual.occultism.sacrifice.chicken", "Poulet");
        this.add("ritual.occultism.sacrifice.pigs", "Cochons");
        this.add("ritual.occultism.sacrifice.humans", "Villageois ou joueur");
        this.add("ritual.occultism.sacrifice.squid", "Calamar");
        this.add("ritual.occultism.sacrifice.horses", "Cheval");
        this.add("ritual.occultism.sacrifice.sheep", "Moutons");
        this.add("ritual.occultism.sacrifice.llamas", "Llama");
        this.add("ritual.occultism.sacrifice.snow_golem", "Bonhomme de neige");
        this.add("ritual.occultism.sacrifice.spiders", "Araignée");

        //Network Message
        this.add("network.messages.occultism.request_order.order_received", "Commande reçue!");

        //Effects
        this.add("effect.occultism.third_eye", "Troisième œil");
        this.add("effect.occultism.double_jump", "Sauts multiples");
        this.add("effect.occultism.dragon_greed", "Dragon's Greed");
        this.add("effect.occultism.mummy_dodge", "Dodge");
        this.add("effect.occultism.bat_lifesteal", "Lifesteal");
        this.add("effect.occultism.beaver_harvest", "Récolte des castors");

        //Sounds
        this.add("occultism.subtitle.chalk", "Craie");
        this.add("occultism.subtitle.brush", "Pinceau");
        this.add("occultism.subtitle.start_ritual", "Démarrer le rituel");
        this.add("occultism.subtitle.tuning_fork", "Diapason");
        this.add("occultism.subtitle.crunching", "Crunching");
        this.add("occultism.subtitle.poof", "Poof!");

    }

    private void addGuiTranslations() {
        this.add("gui.occultism.book_of_calling.mode", "Mode");
        this.add("gui.occultism.book_of_calling.work_area", "Zone de travail");
        this.add("gui.occultism.book_of_calling.manage_machine.insert", "Face d'insertion");
        this.add("gui.occultism.book_of_calling.manage_machine.extract", "Face d'extraction");
        this.add("gui.occultism.book_of_calling.manage_machine.custom_name", "Nom personnalisé");

        // Spirit GUI
        this.add("gui.occultism.spirit.age", "Décomposition de l'essence: %d%%");
        this.add("gui.occultism.spirit.job", "%s");

        // Spirit Transporter GUI
        this.add("gui.occultism.spirit.transporter.filter_mode", "Mode de filtrage");
        this.add("gui.occultism.spirit.transporter.filter_mode.blacklist", "Blacklist");
        this.add("gui.occultism.spirit.transporter.filter_mode.whitelist", "Whitelist");
        this.add("gui.occultism.spirit.transporter.tag_filter", "Saisissez les balises à filtrer en les séparant par \";\".\nE.g.: \"forge:ores;*logs*\".\nUse \"*\" pour correspondre à n'importe quel caractère, e.g. \"*ore*\" pour correspondre aux balises de minerai de n'importe quel mod. Pour filtrer les éléments, faites précéder l'identifiant de l'élément de la mention \"item:\", E.g.: \"item:minecraft:chest\".");

        // Storage Controller GUI
        this.add("gui.occultism.storage_controller.space_info_label", "%d/%d");
        this.add("gui.occultism.storage_controller.shift", "Maintenez la touche pour plus d'informations.");
        this.add("gui.occultism.storage_controller.search.tooltip@", "Prefix @: Recherche de l'id du mod.");
        this.add("gui.occultism.storage_controller.search.tooltip#", "Prefix #: Recherche dans l'info-bulle de l'élément.");
        this.add("gui.occultism.storage_controller.search.tooltip$", "Prefix $: Recherche de tags.");
        this.add("gui.occultism.storage_controller.search.tooltip_rightclick", "Effacez le texte avec un clic droit.");
        this.add("gui.occultism.storage_controller.search.tooltip_clear", "Clear search.");
        this.add("gui.occultism.storage_controller.search.tooltip_jei_on", "Recherche synchronisée avec la JEI.");
        this.add("gui.occultism.storage_controller.search.tooltip_jei_off", "Ne pas synchroniser la recherche avec la JEI.");
        this.add("gui.occultism.storage_controller.search.tooltip_sort_type_amount", "Trier par montant.");
        this.add("gui.occultism.storage_controller.search.tooltip_sort_type_name", "Trier par nom d'élément.");
        this.add("gui.occultism.storage_controller.search.tooltip_sort_type_mod", "Trier par nom de mod.");
        this.add("gui.occultism.storage_controller.search.tooltip_sort_direction_down", "Trier en ordre croissant.");
        this.add("gui.occultism.storage_controller.search.tooltip_sort_direction_up", "Trier en ordre décroissant.");
        this.add("gui.occultism.storage_controller.search.machines.tooltip@", "Prefix @: Recherche de l'identifiant du mod.");
        this.add("gui.occultism.storage_controller.search.machines.tooltip_sort_type_amount", "Trier par distance.");
        this.add("gui.occultism.storage_controller.search.machines.tooltip_sort_type_name", "Trier par nom de machine.");
        this.add("gui.occultism.storage_controller.search.machines.tooltip_sort_type_mod", "Trier par nom de mod.");

    }

    private void addRitualMessages() {
        this.add("ritual.occultism.pentacle_help", "\u00a7lInvalid pentacle!\u00a7r\nEssayez-vous de créer un pentacle : %s? Manquant:\n%s");
        this.add("ritual.occultism.pentacle_help_at_glue", " à la position ");
        this.add("ritual.occultism.ritual_help", "\u00a7lInvalid ritual!\u00a7r\nEssais-tu d'accomplir un rituel: \"%s\"? Items manquants:\n%s");
        this.add("ritual.occultism.disabled", "Ce rituel est désactivé sur ce serveur.");
        this.add("ritual.occultism.does_not_exist", "\u00a7lUnknown ritual\u00a7r. Assurez-vous que le pentacle et les ingrédients sont correctement configurés. Si vous n'avez toujours pas réussi, rejoignez notre discord avec le lien suivant: https://invite.gg/klikli");
        this.add("ritual.occultism.book_not_bound", "\u00a7lUnbound Book of Calling\u00a7r. Vous devez fabriquer ce livre avec le Dictionnaire des esprits pour vous lier à un esprit avant de commencer un rituel.");
        this.add("ritual.occultism.debug.conditions", "Toutes les conditions requises pour ce rituel ne sont pas remplies.");
        this.add("ritual.occultism.debug.started", "Rituel commencé.");
        this.add("ritual.occultism.debug.finished", "Rituel terminé avec succès.");
        this.add("ritual.occultism.debug.interrupted", "Rituel interrompu.");
        this.add("ritual.occultism.summon_foliot_lumberjack.conditions", "Toutes les conditions requises pour ce rituel ne sont pas remplies.");
        this.add("ritual.occultism.summon_foliot_lumberjack.started", "J'ai commencé à invoquer le bûcheron Foliot.");
        this.add("ritual.occultism.summon_foliot_lumberjack.finished", "Invocation réussie du bûcheron foliot.");
        this.add("ritual.occultism.summon_foliot_lumberjack.interrupted", "Invocation du bûcheron foliot interrompue.");
        this.add("ritual.occultism.summon_foliot_transport_items.conditions", "Toutes les conditions requises pour ce rituel ne sont pas remplies.");
        this.add("ritual.occultism.summon_foliot_transport_items.started", "J'ai commencé à invoquer le transporteur de foliot.");
        this.add("ritual.occultism.summon_foliot_transport_items.finished", "Invocation du transporteur foliot avec succès.");
        this.add("ritual.occultism.summon_foliot_transport_items.interrupted", "L'invocation du transporteur de foliot est interrompue..");
        this.add("ritual.occultism.summon_foliot_cleaner.conditions", "Toutes les conditions requises pour ce rituel ne sont pas remplies.");
        this.add("ritual.occultism.summon_foliot_cleaner.started", "J'ai commencé à invoquer le concierge de Foliot.");
        this.add("ritual.occultism.summon_foliot_cleaner.finished", "Invocation réussie du concierge foliot.");
        this.add("ritual.occultism.summon_foliot_cleaner.interrupted", "Invocation du transporteur de janitor interrompue");
        this.add("ritual.occultism.summon_foliot_crusher.conditions", "Toutes les conditions requises pour ce rituel ne sont pas remplies.");
        this.add("ritual.occultism.summon_foliot_crusher.started", "Début de l'invocation foliot concasseur de minerais.");
        this.add("ritual.occultism.summon_foliot_crusher.finished", "Invocation de foliot concasseur de minerais avec succès.");
        this.add("ritual.occultism.summon_foliot_crusher.interrupted", "Invocation de foliot concasseur de minerais interrompu.");
        this.add("ritual.occultism.summon_djinni_crusher.conditions", "Toutes les conditions requises pour ce rituel ne sont pas remplies.");
        this.add("ritual.occultism.summon_djinni_crusher.started", "Début de l'invocation djinni concasseur de minerais.");
        this.add("ritual.occultism.summon_djinni_crusher.finished", "Invocation de djinni concasseur de minerais avec succès.");
        this.add("ritual.occultism.summon_djinni_crusher.interrupted", "Invocation de djinni concasseur de minerais interrompu.");
        this.add("ritual.occultism.summon_afrit_crusher.conditions", "Toutes les conditions requises pour ce rituel ne sont pas remplies.");
        this.add("ritual.occultism.summon_afrit_crusher.started", "Début de l'invocation afrit concasseur de minerais.");
        this.add("ritual.occultism.summon_afrit_crusher.finished", "Invocation de afrit concasseur de minerais avec succès.");
        this.add("ritual.occultism.summon_afrit_crusher.interrupted", "Invocation de afrit concasseur de minerais interrompu.");
        this.add("ritual.occultism.summon_marid_crusher.conditions", "Toutes les conditions requises pour ce rituel ne sont pas remplies.");
        this.add("ritual.occultism.summon_marid_crusher.started", "Début de l'invocation marid concasseur de minerais.");
        this.add("ritual.occultism.summon_marid_crusher.finished", "Invocation de marid concasseur de minerais avec succès.");
        this.add("ritual.occultism.summon_marid_crusher.interrupted", "Invocation de marid concasseur de minerais interrompu.");
        this.add("ritual.occultism.summon_foliot_sapling_trader.conditions", "Toutes les conditions requises pour ce rituel ne sont pas remplies.");
        this.add("ritual.occultism.summon_foliot_sapling_trader.started", "Début de l'invocation foliot négociant de pousses d'autres mondes.");
        this.add("ritual.occultism.summon_foliot_sapling_trader.finished", "Invocation de foliot pousse d'autre monde avec succès.");
        this.add("ritual.occultism.summon_foliot_sapling_trader.interrupted", "Invocation de foliot négociant de pousses d'autres mondes interrompu.");
        this.add("ritual.occultism.summon_foliot_otherstone_trader.conditions", "Toutes les conditions requises pour ce rituel ne sont pas remplies.");
        this.add("ritual.occultism.summon_foliot_otherstone_trader.started", "Début de l'invocation foliot commerçant de pierres étranges.");
        this.add("ritual.occultism.summon_foliot_otherstone_trader.finished", "Invocation de foliot commerçant de pierres étranges avec succès.");
        this.add("ritual.occultism.summon_foliot_otherstone_trader.interrupted", "Invocation de foliot commerçant de pierres étranges interrompu.");
        this.add("ritual.occultism.summon_djinni_manage_machine.conditions", "Toutes les conditions requises pour ce rituel ne sont pas remplies.");
        this.add("ritual.occultism.summon_djinni_manage_machine.started", "Début de l'invocation djinni machine operator.");
        this.add("ritual.occultism.summon_djinni_manage_machine.finished", "Invocation de djinni machine operator avec succès.");
        this.add("ritual.occultism.summon_djinni_manage_machine.interrupted", "Invocation de djinni machine operator interrompu.");
        this.add("ritual.occultism.summon_djinni_clear_weather.conditions", "Toutes les conditions requises pour ce rituel ne sont pas remplies.");
        this.add("ritual.occultism.summon_djinni_clear_weather.started", "Début de l'invocation djinni pour dégager le temps.");
        this.add("ritual.occultism.summon_djinni_clear_weather.finished", "Invocation de djinni avec succès.");
        this.add("ritual.occultism.summon_djinni_clear_weather.interrupted", "Invocation de djinni interrompu.");
        this.add("ritual.occultism.summon_djinni_day_time.conditions", "Toutes les conditions requises pour ce rituel ne sont pas remplies.");
        this.add("ritual.occultism.summon_djinni_day_time.started", "Début de l'invocation djinni pour régler l'heure sur le jour.");
        this.add("ritual.occultism.summon_djinni_day_time.finished", "Invocation de djinni avec succès.");
        this.add("ritual.occultism.summon_djinni_day_time.interrupted", "Invocation de djinni interrompu.");
        this.add("ritual.occultism.summon_djinni_night_time.conditions", "Toutes les conditions requises pour ce rituel ne sont pas remplies.");
        this.add("ritual.occultism.summon_djinni_night_time.started", "Début de l'invocation djinni pour régler l'heure de la nuit.");
        this.add("ritual.occultism.summon_djinni_night_time.finished", "Invocation de djinni avec succès.");
        this.add("ritual.occultism.summon_djinni_night_time.interrupted", "Invocation de djinni interrompu.");
        this.add("ritual.occultism.summon_afrit_rain_weather.conditions", "Toutes les conditions requises pour ce rituel ne sont pas remplies.");
        this.add("ritual.occultism.summon_afrit_rain_weather.started", "Début de l'invocation afrit pour un temps pluvieux.");
        this.add("ritual.occultism.summon_afrit_rain_weather.finished", "Invocation de afrit avec succès.");
        this.add("ritual.occultism.summon_afrit_rain_weather.interrupted", "Invocation de afrit interrompu.");
        this.add("ritual.occultism.summon_afrit_thunder_weather.conditions", "Toutes les conditions requises pour ce rituel ne sont pas remplies.");
        this.add("ritual.occultism.summon_afrit_thunder_weather.started", "Début de l'invocation afrit pour un orage.");
        this.add("ritual.occultism.summon_afrit_thunder_weather.finished", "Invocation de afrit avec succès.");
        this.add("ritual.occultism.summon_afrit_thunder_weather.interrupted", "Invocation de afrit interrompu.");
        this.add("ritual.occultism.summon_wild_afrit.conditions", "Toutes les conditions requises pour ce rituel ne sont pas remplies.");
        this.add("ritual.occultism.summon_wild_afrit.started", "Début de l'invocation afrit non consolidé.");
        this.add("ritual.occultism.summon_wild_afrit.finished", "Invocation de afrit non consolidé  avec succès.");
        this.add("ritual.occultism.summon_wild_afrit.interrupted", "Invocation de afrit non consolidé interrompu.");
        this.add("ritual.occultism.summon_wild_hunt.conditions", "Toutes les conditions requises pour ce rituel ne sont pas remplies.");
        this.add("ritual.occultism.summon_wild_hunt.started", "Début de l'invocation la chasse sauvage.");
        this.add("ritual.occultism.summon_wild_hunt.finished", "Invocation de la chasse sauvage avec succès.");
        this.add("ritual.occultism.summon_wild_hunt.interrupted", "Invocation de la chasse sauvage interrompu.");
        this.add("ritual.occultism.craft_dimensional_matrix.conditions", "Toutes les conditions requises pour ce rituel ne sont pas remplies.");
        this.add("ritual.occultism.craft_dimensional_matrix.started", "J'ai commencé à lier les djinni dans une matrice dimensionnelle.");
        this.add("ritual.occultism.craft_dimensional_matrix.finished", "J'ai réussi à lier le djinni à une matrice dimensionnelle.");
        this.add("ritual.occultism.craft_dimensional_matrix.interrupted", "Liaison de djinni interrompu.");
        this.add("ritual.occultism.craft_dimensional_mineshaft.conditions", "Toutes les conditions requises pour ce rituel ne sont pas remplies.");
        this.add("ritual.occultism.craft_dimensional_mineshaft.started", "J'ai commencé à lier djinni dans un mineshaft dimensionnel");
        this.add("ritual.occultism.craft_dimensional_mineshaft.finished", "J'ai réussi à lier un djinni dans un mineshaft dimensionnel.");
        this.add("ritual.occultism.craft_dimensional_mineshaft.interrupted", "Liaison de djinni interrompu.");
        this.add("ritual.occultism.craft_storage_controller_base.conditions", "Toutes les conditions requises pour ce rituel ne sont pas remplies.");
        this.add("ritual.occultism.craft_storage_controller_base.started", "J'ai commencé à lier foliot dans la base de l'actionneur de stockage.");
        this.add("ritual.occultism.craft_storage_controller_base.finished", "Liaison avec succès de foliot dans la base de l'actionneur de stockage.");
        this.add("ritual.occultism.craft_storage_controller_base.interrupted", "Liaison de foliot interrompu.");
        this.add("ritual.occultism.craft_stabilizer_tier1.conditions", "Toutes les conditions requises pour ce rituel ne sont pas remplies.");
        this.add("ritual.occultism.craft_stabilizer_tier1.started", "J'ai commencé à lier foliot dans stabilisateur de stockage.");
        this.add("ritual.occultism.craft_stabilizer_tier1.finished", "J'ai réussi à lier foliot dans stabilisateur de stockage.");
        this.add("ritual.occultism.craft_stabilizer_tier1.interrupted", "Liaison de foliot interrompu.");
        this.add("ritual.occultism.craft_stabilizer_tier2.conditions", "Toutes les conditions requises pour ce rituel ne sont pas remplies.");
        this.add("ritual.occultism.craft_stabilizer_tier2.started", "J'ai commencé à lier djinni dans stabilisateur de stockage.");
        this.add("ritual.occultism.craft_stabilizer_tier2.finished", "J'ai réussi à lier djinni dans stabilisateur de stockage.");
        this.add("ritual.occultism.craft_stabilizer_tier2.interrupted", "Liaison de djinni interrompu.");
        this.add("ritual.occultism.craft_stabilizer_tier3.conditions", "Toutes les conditions requises pour ce rituel ne sont pas remplies.");
        this.add("ritual.occultism.craft_stabilizer_tier3.started", "J'ai commencé à lier afrit dans stabilisateur de stockage.");
        this.add("ritual.occultism.craft_stabilizer_tier3.finished", "J'ai réussi à lier l'afrit au stabilisateur de stockage.");
        this.add("ritual.occultism.craft_stabilizer_tier3.interrupted", "Liaison de l'afrit interrompu.");
        this.add("ritual.occultism.craft_stabilizer_tier4.conditions", "Toutes les conditions requises pour ce rituel ne sont pas remplies.");
        this.add("ritual.occultism.craft_stabilizer_tier4.started", "J'ai commencé à lier marid dans stabilisateur de stockage.");
        this.add("ritual.occultism.craft_stabilizer_tier4.finished", "J'ai réussi à lier marid dans stabilisateur de stockage.");
        this.add("ritual.occultism.craft_stabilizer_tier4.interrupted", "Liaison de marid interrompu.");
        this.add("ritual.occultism.craft_stable_wormhole.conditions", "Toutes les conditions requises pour ce rituel ne sont pas remplies.");
        this.add("ritual.occultism.craft_stable_wormhole.started", "J'ai commencé à lier foliot dans le cadre du vortex.");
        this.add("ritual.occultism.craft_stable_wormhole.finished", "J'ai réussi à lier foliot dans le cadre du vortex.");
        this.add("ritual.occultism.craft_stable_wormhole.interrupted", "Liaison de foliot interrompu.");
        this.add("ritual.occultism.craft_storage_remote.conditions", "Toutes les conditions requises pour ce rituel ne sont pas remplies.");
        this.add("ritual.occultism.craft_storage_remote.started", "J'ai commencé à lier djinni dans le stockage à distance.");
        this.add("ritual.occultism.craft_storage_remote.finished", "J'ai réussi à lier djinni dans le stockage à distance.");
        this.add("ritual.occultism.craft_storage_remote.interrupted", "Liaison de djinni interrompu.");
        this.add("ritual.occultism.craft_infused_lenses.conditions", "Toutes les conditions requises pour ce rituel ne sont pas remplies.");
        this.add("ritual.occultism.craft_infused_lenses.started", "J'ai commencé à lier foliot en lentilles.");
        this.add("ritual.occultism.craft_infused_lenses.finished", "J'ai réussi à lier foliot en lentilles.");
        this.add("ritual.occultism.craft_infused_lenses.interrupted", "Liaison de foliot interrompu.");
        this.add("ritual.occultism.craft_infused_pickaxe.conditions", "Toutes les conditions requises pour ce rituel ne sont pas remplies.");
        this.add("ritual.occultism.craft_infused_pickaxe.started", "J'ai commencé à lier djinni en pioche.");
        this.add("ritual.occultism.craft_infused_pickaxe.finished", "J'ai réussi à lier djinni en pioche.");
        this.add("ritual.occultism.craft_infused_pickaxe.interrupted", "Liaison de djinni interrompu.");
        this.add("ritual.occultism.craft_miner_foliot_unspecialized.conditions", "Toutes les conditions requises pour ce rituel ne sont pas remplies.");
        this.add("ritual.occultism.craft_miner_foliot_unspecialized.started", "Début de l'invocation foliot dans la lampe magique.");
        this.add("ritual.occultism.craft_miner_foliot_unspecialized.finished", "Succès de l'invocation de foliot dans la lampe magique.");
        this.add("ritual.occultism.craft_miner_foliot_unspecialized.interrupted", "Invocation de foliot interrompu.");
        this.add("ritual.occultism.craft_miner_djinni_ores.conditions", "Toutes les conditions requises pour ce rituel ne sont pas remplies.");
        this.add("ritual.occultism.craft_miner_djinni_ores.started", "Début de l'invocation djinni dans la lampe magique.");
        this.add("ritual.occultism.craft_miner_djinni_ores.finished", "Succès de l'invocation de djinni dans la lampe magique.");
        this.add("ritual.occultism.craft_miner_djinni_ores.interrupted", "Invocation de djinni interrompu.");
        this.add("ritual.occultism.craft_satchel.conditions", "Toutes les conditions requises pour ce rituel ne sont pas remplies.");
        this.add("ritual.occultism.craft_satchel.started", "J'ai commencé à lier foliot dans la sacoche.");
        this.add("ritual.occultism.craft_satchel.finished", "J'ai réussi à lier foliot dans la sacoche.");
        this.add("ritual.occultism.craft_satchel.interrupted", "Liaison de foliot interrompu.");
        this.add("ritual.occultism.craft_soul_gem.conditions", "Toutes les conditions requises pour ce rituel ne sont pas remplies.");
        this.add("ritual.occultism.craft_soul_gem.started", "J'ai commencé à lier djinni dans le joyau de l'âme.");
        this.add("ritual.occultism.craft_soul_gem.finished", "J'ai réussi à lier djinni dans le joyau de l'âme.");
        this.add("ritual.occultism.craft_soul_gem.interrupted", "Liaison de djinni interrompu.");
        this.add("ritual.occultism.craft_familiar_ring.conditions", "Toutes les conditions requises pour ce rituel ne sont pas remplies.");
        this.add("ritual.occultism.craft_familiar_ring.started", "J'ai commencé à lier djinni dans l'anneau familier.");
        this.add("ritual.occultism.craft_familiar_ring.finished", "J'ai réussi à lier djinni dans l'anneau familier.");
        this.add("ritual.occultism.craft_familiar_ring.interrupted", "Liaison de djinni interrompu.");
        this.add("ritual.occultism.possess_endermite.conditions", "Toutes les conditions requises pour ce rituel ne sont pas remplies.");
        this.add("ritual.occultism.possess_endermite.started", "Début de l'invocation de l'endermite possedée.");
        this.add("ritual.occultism.possess_endermite.finished", "Invocation de l'endermite possedée avec succès.");
        this.add("ritual.occultism.possess_endermite.interrupted", "Invocation de l'endermite possedée interrompu.");
        this.add("ritual.occultism.possess_skeleton.conditions", "Toutes les conditions requises pour ce rituel ne sont pas remplies.");
        this.add("ritual.occultism.possess_skeleton.started", "Début de l'invocation squelette possédé.");
        this.add("ritual.occultism.possess_skeleton.finished", "Invocation du squelette possédé avec succès.");
        this.add("ritual.occultism.possess_skeleton.interrupted", "Invocation du squelette possédé interrompu.");
        this.add("ritual.occultism.possess_enderman.conditions", "Toutes les conditions requises pour ce rituel ne sont pas remplies.");
        this.add("ritual.occultism.possess_enderman.started", "Début de l'invocation de l'enderman possedé.");
        this.add("ritual.occultism.possess_enderman.finished", "Invocation de l'enderman possedé avec succès.");
        this.add("ritual.occultism.possess_enderman.interrupted", "Invocation de l'enderman possedé interrompu.");
        this.add("ritual.occultism.possess_ghast.conditions", "Toutes les conditions requises pour ce rituel ne sont pas remplies.");
        this.add("ritual.occultism.possess_ghast.started", "Début de l'invocation du ghast possedé.");
        this.add("ritual.occultism.possess_ghast.finished", "Invocation du ghast possedé avec succès.");
        this.add("ritual.occultism.possess_ghast.interrupted", "Invocation du ghast possedé interrompu.");
        this.add("ritual.occultism.familiar_otherworld_bird.conditions", "Toutes les conditions requises pour ce rituel ne sont pas remplies.");
        this.add("ritual.occultism.familiar_otherworld_bird.started", "Début de l'invocation drikwing familier.");
        this.add("ritual.occultism.familiar_otherworld_bird.finished", "Invocation de drikwing familier avec succès.");
        this.add("ritual.occultism.familiar_otherworld_bird.interrupted", "Invocation de drikwing familier interrompu.");
        this.add("ritual.occultism.familiar_cthulhu.conditions", "Toutes les conditions requises pour ce rituel ne sont pas remplies.");
        this.add("ritual.occultism.familiar_cthulhu.started", "Début de l'invocation cthulhu familier.");
        this.add("ritual.occultism.familiar_cthulhu.finished", "Invocation de cthulhu familier avec succès.");
        this.add("ritual.occultism.familiar_cthulhu.interrupted", "Invocation de cthulhu familier interrompu.");
        this.add("ritual.occultism.familiar_devil.conditions", "Toutes les conditions requises pour ce rituel ne sont pas remplies.");
        this.add("ritual.occultism.familiar_devil.started", "Début de l'invocation diable familier.");
        this.add("ritual.occultism.familiar_devil.finished", "Invocation de diable familier avec succès.");
        this.add("ritual.occultism.familiar_devil.interrupted", "Invocation de diable familier interrompu.");
        this.add("ritual.occultism.familiar_dragon.conditions", "Toutes les conditions requises pour ce rituel ne sont pas remplies.");
        this.add("ritual.occultism.familiar_dragon.started", "Début de l'invocation dragon familier.");
        this.add("ritual.occultism.familiar_dragon.finished", "Invocation de dragon familier avec succès.");
        this.add("ritual.occultism.familiar_dragon.interrupted", "Invocation de dragon familier interrompu.");
        this.add("ritual.occultism.familiar_blacksmith.conditions", "Toutes les conditions requises pour ce rituel ne sont pas remplies.");
        this.add("ritual.occultism.familiar_blacksmith.started", "Début de l'invocation forgeron familier.");
        this.add("ritual.occultism.familiar_blacksmith.finished", "Invocation de forgeron familier avec succès.");
        this.add("ritual.occultism.familiar_blacksmith.interrupted", "Invocation de forgeron familier interrompu.");
        this.add("ritual.occultism.familiar_guardian.conditions", "Toutes les conditions requises pour ce rituel ne sont pas remplies.");
        this.add("ritual.occultism.familiar_guardian.started", "Début de l'invocation guardian familier.");
        this.add("ritual.occultism.familiar_guardian.finished", "Invocation de guardian familier avec succès.");
        this.add("ritual.occultism.familiar_guardian.interrupted", "Invocation de guardian familier interrompu.");
        this.add("ritual.occultism.summon_wild_otherworld_bird.conditions", "Toutes les conditions requises pour ce rituel ne sont pas remplies.");
        this.add("ritual.occultism.summon_wild_otherworld_bird.started", "Début de l'invocation drikwing sauvage.");
        this.add("ritual.occultism.summon_wild_otherworld_bird.finished", "Invocation de drikwing sauvage avec succès.");
        this.add("ritual.occultism.summon_wild_otherworld_bird.interrupted", "Invocation de drikwing sauvage interrompu.");
        this.add("ritual.occultism.summon_wild_parrot.conditions", "Toutes les conditions requises pour ce rituel ne sont pas remplies.");
        this.add("ritual.occultism.summon_wild_parrot.started", "Début de l'invocation perroquet sauvage.");
        this.add("ritual.occultism.summon_wild_parrot.finished", "Invocation de perroquet sauvage avec succès.");
        this.add("ritual.occultism.summon_wild_parrot.interrupted", "Invocation de perroquet sauvage interrompu.");
        this.add("ritual.occultism.familiar_parrot.conditions", "Toutes les conditions requises pour ce rituel ne sont pas remplies.");
        this.add("ritual.occultism.familiar_parrot.started", "Début de l'invocation perroquet familier.");
        this.add("ritual.occultism.familiar_parrot.finished", "Invocation de perroquet familier avec succès.");
        this.add("ritual.occultism.familiar_parrot.interrupted", "Invocation de perroquet familier interrompu.");
        this.add("ritual.occultism.familiar_greedy.conditions", "Toutes les conditions requises pour ce rituel ne sont pas remplies.");
        this.add("ritual.occultism.familiar_greedy.started", "Début de l'invocation greedy familier.");
        this.add("ritual.occultism.familiar_greedy.finished", "Invocation de greedy familier avec succès.");
        this.add("ritual.occultism.familiar_greedy.interrupted", "Invocation de greedy familier interrompu.");
        this.add("ritual.occultism.familiar_bat.conditions", "Toutes les conditions requises pour ce rituel ne sont pas remplies.");
        this.add("ritual.occultism.familiar_bat.started", "Début de l'invocation chauve-souris familière.");
        this.add("ritual.occultism.familiar_bat.finished", "Invocation de chauve-souris familière avec succès.");
        this.add("ritual.occultism.familiar_bat.interrupted", "Invocation de chauve-souris familière interrompu.");
        this.add("ritual.occultism.familiar_deer.conditions", "Toutes les conditions requises pour ce rituel ne sont pas remplies.");
        this.add("ritual.occultism.familiar_deer.started", "Début de l'invocation cerf familier.");
        this.add("ritual.occultism.familiar_deer.finished", "Invocation de cerf familier avec succès.");
        this.add("ritual.occultism.familiar_deer.interrupted", "Invocation de cerf familier interrompu.");
        this.add("ritual.occultism.familiar_headless.conditions", "Toutes les conditions requises pour ce rituel ne sont pas remplies.");
        this.add("ritual.occultism.familiar_headless.started", "Début de l'invocation l'homme-rat sans tête familier.");
        this.add("ritual.occultism.familiar_headless.finished", "Invocation de l'homme-rat sans tête familier avec succès.");
        this.add("ritual.occultism.familiar_headless.interrupted", "Invocation de l'homme-rat sans tête familier interrompu.");
        this.add("ritual.occultism.familiar_chimera.conditions", "Toutes les conditions requises pour ce rituel ne sont pas remplies.");
        this.add("ritual.occultism.familiar_chimera.started", "Début de l'invocation chimère familier.");
        this.add("ritual.occultism.familiar_chimera.finished", "Invocation de chimère familier avec succès.");
        this.add("ritual.occultism.familiar_chimera.interrupted", "Invocation de chimère familier interrompu.");
        this.add("ritual.occultism.familiar_beholder.conditions", "Toutes les conditions requises pour ce rituel ne sont pas remplies.");
        this.add("ritual.occultism.familiar_beholder.started", "Début de l'invocation conteneur familier.");
        this.add("ritual.occultism.familiar_beholder.finished", "Invocation de conteneur familier avec succès.");
        this.add("ritual.occultism.familiar_beholder.interrupted", "Invocation de conteneur familier interrompu.");
        this.add("ritual.occultism.familiar_fairy.conditions", "Toutes les conditions requises pour ce rituel ne sont pas remplies.");
        this.add("ritual.occultism.familiar_fairy.started", "Début de l'invocation fées familier.");
        this.add("ritual.occultism.familiar_fairy.finished", "Invocation de fées familier avec succès.");
        this.add("ritual.occultism.familiar_fairy.interrupted", "Invocation de fées familier interrompu.");
        this.add("ritual.occultism.familiar_mummy.conditions", "Toutes les conditions requises pour ce rituel ne sont pas remplies.");
        this.add("ritual.occultism.familiar_mummy.started", "Début de l'invocation mummy familier.");
        this.add("ritual.occultism.familiar_mummy.finished", "Invocation de mummy familier avec succès.");
        this.add("ritual.occultism.familiar_mummy.interrupted", "Invocation de mummy familier interrompu.");
        this.add("ritual.occultism.familiar_beaver.conditions", "Toutes les conditions requises pour ce rituel ne sont pas remplies.");
        this.add("ritual.occultism.familiar_beaver.started", "Début de l'invocation castor familier.");
        this.add("ritual.occultism.familiar_beaver.finished", "Invocation de castor familier avec succès.");
        this.add("ritual.occultism.familiar_beaver.interrupted", "Invocation de castor familier interrompu.");
    }

    private void addBook() {
        var helper = ModonomiconAPI.get().getLangHelper(Occultism.MODID);
        helper.book("dictionary_of_spirits");
        this.add(helper.bookName(), "Dictionnaire des Esprits");
        this.add(helper.bookTooltip(), """
                Ce livre a pour but de présenter au lecteur novice les rituels d'invocation les plus courants et de le doter d'une liste d'esprits et de leurs noms.
                Les auteurs conseillent la prudence dans l'invocation des entités listées.
                Pour obtenir de l'aide ou faire part de vos commentaires, veuillez nous rejoindre sur Discord. https://invite.gg/klikli.
                """);

        this.addGettingStartedCategory(helper);
        this.addRitualsCategory(helper);
    }

    private void addGettingStartedCategory(BookLangHelper helper) {
        helper.category("getting_started");
        this.add(helper.categoryName(), "Pour commencer");

        helper.entry("demons_dream");
        this.add(helper.entryName(), "Lever le voile");
        this.add(helper.entryDescription(), "Découvrez l'autre monde et le troisième œil.");

        helper.page("intro");
        this.add(helper.pageTitle(), "L'autre monde");
        this.add(helper.pageText(),
                """
                        Caché aux yeux des humains, il existe un autre plan d'existence, une autre *dimension* si vous voulez, le [#](%1$s)Otherworld[#]().
                        Ce monde est peuplé d'entités souvent appelées [#](%1$s)Demons[#]().
                        """.formatted(COLOR_PURPLE));

        helper.page("intro2");
        this.add(helper.pageText(),
                """
                        Ces Démons possèdent une grande variété de pouvoirs et de compétences utiles, et depuis des siècles, les magiciens ont cherché à les invoquer pour leur propre bénéfice. La première étape du voyage pour réussir à invoquer une telle Entité est d'apprendre à interagir avec l'Autre Monde.
                        """);

        helper.page("spotlight");
        this.add(helper.pageText(),
                """
                        %1$s est une herbe qui donne aux humains [#](%2$s)Third Eye[#](),
                        leur permettant de voir où le [#](%2$s)Otherworld[#]() se croise avec la nôtre.
                        On peut trouver des semences **en cassant des graines**.
                        **Consommer** le fruit cultivé active la capacité.
                        """.formatted(DEMONS_DREAM, COLOR_PURPLE));

        helper.page("harvest_effect");
        this.add(helper.pageText(),
                """
                        Un effet secondaire supplémentaire de %1$s, est **la possibilité d'interagir avec [#](%2$s)Otherworld[#]() matériaux**.
                        Ceci est unique à %1$s, d'autres façons d'obtenir [#](%2$s)Troisième œil[#]() ne donnent pas cette capacité.
                        Sous l'effet de %1$s vous êtes en mesure de **récolter** des pierres de l'autre côté ainsi que des arbres de l'autre monde..
                         """.formatted(DEMONS_DREAM, COLOR_PURPLE));


        helper.page("datura_screenshot"); //no text


        helper.entry("spirit_fire");
        this.add(helper.entryName(), "Ça brûle !");
        this.add(helper.entryDescription(), "Ou pas ?");

        helper.page("spotlight");
        this.add(helper.pageText(),
                """
                        [#](%1$s)Spiritfire[#]() est un type spécial de feu qui existe surtout dans les pays suivants [#](%1$s)L'autre lieu[#]()
                        et ne nuit pas aux êtres vivants. Ses propriétés particulières permettent de l'utiliser pour purifier et convertir certains matériaux en les brûlant, sans les consommer.
                        """.formatted(COLOR_PURPLE));


        helper.page("spirit_fire_screenshot");
        this.add(helper.pageText(),
                """
                        Lancer [](item://occultism:datura) au sol et y mettre le feu avec [](item://minecraft:flint_and_steel).
                         """);


        helper.page("main_uses");
        this.add(helper.pageText(),
                """
                        Les principales utilisations de [](item://occultism:spirit_fire) sont à convertir [](item://minecraft:diamond) en [](item://occultism:spirit_attuned_gem),
                        pour obtenir des ingrédients de base tels que [](item://occultism:otherstone) et [Otherworld Saplings](item://occultism:otherworld_sapling_natural),
                        et pour purifier les craies impures.
                         """);

        helper.page("otherstone_recipe");
        this.add(helper.pageText(),
                """
                        Un moyen plus facile d'obtenir [](item://occultism:otherstone) que par la divination.
                              """);


        helper.page("otherworld_sapling_natural_recipe");
        this.add(helper.pageText(),
                """
                        Un moyen plus facile d'obtenir [Otherworld Saplings](item://occultism:otherworld_sapling_natural) que par la divination.
                              """);

        helper.entry("third_eye");
        this.add(helper.entryName(), "Le troisième œil");
        this.add(helper.entryDescription(), "Vous voyez maintenant ?");

        helper.page("about");
        this.add(helper.pageTitle(), "Troisième œil");
        this.add(helper.pageText(),
                """
                        La capacité de voir au-delà du monde physique est appelée [#](%1$s)Third Eye[#]().
                        Les humains ne possèdent pas cette capacité à voir. [#](%1$s)beyond the veil[#](),
                        Cependant, avec certaines substances et certains appareils, l'invocateur averti peut contourner cette limitation.
                         """.formatted(COLOR_PURPLE));

        helper.page("how_to_obtain");
        this.add(helper.pageText(),
                """
                        La façon la plus confortable, et la plus *coûteuse*, d'obtenir cette capacité, est de porter des lunettes infusées avec des esprits, qui *prêtent* leur vue à celui qui les porte.
                        Une alternative légèrement plus nauséabonde, mais **très abordable** est la consommation de certaines herbes,
                        [%1$s](entry://occultism:dictionary_of_spirits/getting_started/demons_dream) le plus important d'entre eux.
                         """.formatted(DEMONS_DREAM));

        helper.page("otherworld_goggles");
        this.add(helper.pageText(),
                """
                        [Ces lunettes de protection](entry://occultism:dictionary_of_spirits/rituals/craft_otherworld_goggles) permettent de voir encore plus de blocs cachés de l'Autre Monde, mais ils ne permettent pas de récolter ces matériaux.
                        Les matériaux de bas niveau peuvent être récoltés en consommant [%1$s](entry://occultism:dictionary_of_spirits/getting_started/demons_dream),
                        mais les matériaux plus précieux nécessitent des outils spéciaux.
                                """.formatted(DEMONS_DREAM));


        helper.entry("divination_rod");
        this.add(helper.entryName(), "Bâton de divination");
        this.add(helper.entryDescription(), "Obtenir des matériaux d'un autre monde");

        helper.page("intro");
        this.add(helper.pageTitle(), "Divination");
        this.add(helper.pageText(),
                """
                        Pour faciliter la prise en main, les matériaux obtenus par divination sont désormais accompagnés de recettes d'artisanat.
                        **Si vous voulez profiter pleinement de l'expérience, passez la page de la recette suivante et passez à la page de la
                        [divination instructions](entry://occultism:dictionary_of_spirits/getting_started/divination_rod@divination_instructions).**
                                """);


        helper.page("otherstone_recipe");
        //no text

        helper.page("otherworld_sapling_natural_recipe");
        this.add(helper.pageText(),
                """
                        **Méfiez-vous de**: l'arbre qui poussera à partir du jeune arbre ressemblera à un chêne normal.
                        Vous devez activer le [Troisième oeil](entry://occultism:dictionary_of_spirits/getting_started/demons_dream)
                        pour récolter les bûches et les feuilles de l'Autre Monde.
                                """);

        helper.page("divination_rod");
        this.add(helper.pageText(),
                """
                        Les matériaux de l'autre monde jouent un rôle important dans l'interaction avec les esprits.
                        Comme ils sont rares et non visibles à l'œil nu, leur recherche nécessite des outils spéciaux.
                        La baguette de divination permet de trouver les matériaux de l'Autre Monde en se basant sur leurs similitudes avec les matériaux communs de notre monde.
                                 """);

        helper.page("about_divination_rod");
        this.add(helper.pageText(),
                """
                        La baguette de divination utilise une gemme accordée à un esprit et attachée à une tige de bois.
                        La gemme résonne avec le matériau choisi, et ce mouvement est amplifié par la tige en bois, permettant de détecter les matériaux de l'Autre Monde à proximité.   \s
                           \s
                           \s
                        La baguette fonctionne en détectant la résonance entre les matériaux du monde réel et ceux de l'Autre Monde.
                        Si vous accordez la baguette à un matériau du monde réel, elle trouvera le bloc correspondant dans l'autre monde.
                                 """);

        helper.page("how_to_use");
        this.add(helper.pageTitle(), "Utilisation du bâton");
        this.add(helper.pageText(),
                """
                        Shift-clic droit sur un bloc pour accorder la baguette au bloc correspondant de l'Autre Monde.
                        - [](item://minecraft:andesite): [](item://occultism:otherstone)
                        - [](item://minecraft:oak_wood):  [](item://occultism:otherworld_log)
                        - [](item://minecraft:oak_leaves): [](item://occultism:otherworld_leaves)
                        - [](item://minecraft:netherrack): [](item://occultism:iesnium_ore)

                        Ensuite, faites un clic droit et maintenez-le enfoncé jusqu'à ce que l'animation de la tige se termine.""");

        helper.page("how_to_use2");
        this.add(helper.pageText(),
                """
                        Une fois l'animation terminée, le plus proche **Le bloc trouvé sera mis en évidence par des lignes blanches et pourra être vu à travers les autres blocs.**.
                        En outre, vous pouvez observer les cristaux pour obtenir des indices : un cristal blanc indique qu'aucun bloc cible n'a été trouvé, un bloc entièrement violet signifie que le bloc trouvé est proche. Un mélange de blanc et de violet indique que la cible est assez éloignée.""");

        helper.page("divination_rod_screenshots");
        this.add(helper.pageText(),
                """
                        Le blanc signifie que rien n'a été trouvé. Plus vous voyez de violet, plus vous êtes proche.
                        """);

        helper.page("otherworld_groves");
        this.add(helper.pageTitle(), "Bosquets de l'Autre Monde");
        this.add(helper.pageText(),
                """
                        Les bosquets de l'Autre Monde sont des grottes luxuriantes, envahies par la végétation, avec... [#](%1$s)Otherworld Trees[#](),
                        et les murs[](item://occultism:otherstone), Pour les trouver, accordez votre baguette de divination sur les feuilles ou les bûches de l'Autre Monde, car contrairement à la pierre de l'Autre, elles n'apparaissent que dans ces bosquets.
                         """.formatted(COLOR_PURPLE));

        helper.page("otherworld_groves_2");
        this.add(helper.pageText(),
                """
                        **Indice:** Dans l'Overworld, regardez **en bas**.
                          """);

        helper.page("otherworld_trees");
        this.add(helper.pageTitle(), "Arbres d'un autre monde");
        this.add(helper.pageText(),
                """
                        Les arbres de l'Autre Monde poussent naturellement dans les bosquets de l'Autre Monde. À l'œil nu, ils ressemblent à des chênes, mais pour le troisième œil, ils révèlent leur véritable nature.   \s
                        **Important:** Les jeunes arbres de l'Autre Monde ne peuvent être obtenus qu'en cassant les feuilles manuellement, naturellement seuls les jeunes arbres de chêne tombent.
                         """);

        helper.page("otherworld_trees_2");
        this.add(helper.pageText(),
                """
                        Les arbres cultivés à partir de plants stables de l'autre monde, obtenus auprès de marchands d'esprits, ne sont pas soumis à cette limitation.
                         """);


        helper.entry("ritual_prep");
        this.add(helper.entryName(), "Préparations rituelles");
        this.add(helper.entryDescription(), "Les choses à faire avant votre premier rituel.");

        helper.page("intro");
        this.add(helper.pageTitle(), "Préparations rituelles");
        this.add(helper.pageText(),
                """
                        Pour invoquer les esprits de la [#](%1$s)Other Place[#]() dans une sécurité relative, vous devez dessiner un pentacle adéquat à l'aide de craie pour contenir leurs pouvoirs. De plus, vous devez [Sacrificial Bowls](item://occultism:sacrificial_bowl)
                        de sacrifier des objets appropriés pour attirer l'esprit.
                         """.formatted(COLOR_PURPLE));


        helper.page("white_chalk");
        this.add(helper.pageText(),
                """
                        Pour les pentacles, seule la couleur de la craie est pertinente, pas le glyph/sign.
                        À des fins décoratives, vous pouvez cliquer plusieurs fois sur un bloc pour faire défiler les glyphes.
                        Pour d'autres craies, voir [Chalks](entry://occultism:dictionary_of_spirits/advanced/chalks)
                            """);

        helper.page("burnt_otherstone_recipe");
        //no text

        helper.page("otherworld_ashes_recipe");
        //no text

        helper.page("impure_white_chalk_recipe");
        //no text

        helper.page("white_chalk_recipe");
        //no text

        helper.page("brush_recipe");
        this.add(helper.pageText(), "La brosse peut être utilisée pour enlever facilement les glyphes de craie du sol..");

        helper.page("white_candle");
        this.add(helper.pageText(),
                """
                        Les bougies apportent de la stabilité aux rituels et sont un élément important de presque tous les pentacles.
                        **Les bougies agissent également comme des étagères pour l'enchantement.**
                            """);

        helper.page("tallow");
        this.add(helper.pageText(),
                """
                        Ingrédient pour les bougies. Tuez les gros animaux comme les porcs, les vaches ou les moutons avec un [#](item://occultism:butcher_knife)
                        pour récolter[#](item://occultism:tallow).
                            """);


        helper.page("white_candle_recipe");
        //no text

        helper.page("sacrificial_bowl");
        this.add(helper.pageText(),
                """
                        Ces bols sont utilisés pour sacrifier des objets dans le cadre d'un rituel et vous aurez besoin d'une poignée d'entre eux.
                        Remarque : leur emplacement exact dans le rituel n'a pas d'importance - il suffit de les garder à moins de 8 blocs du centre du pentacle !
                             """);

        helper.page("sacrificial_bowl_recipe");
        //no text

        helper.page("golden_sacrificial_bowl");
        this.add(helper.pageText(),
                """
                        Ce bol sacrificiel spécial est utilisé pour activer le rituel en faisant un clic droit dessus avec l'objet d'activation, généralement un livre de liaison, une fois que tout a été mis en place et que vous êtes prêt à commencer.
                             """);


        helper.page("golden_sacrificial_bowl_recipe");
        //no text
    }

    private void addRitualsCategory(BookLangHelper helper) {
        helper.category("rituels");
        this.add(helper.categoryName(), "Rituels");

        helper.entry("craft_otherworld_goggles");
        this.add(helper.entryName(), "Fabriquer des lunettes de protection pour l'autre monde");
    }

    private void addAdvancements() {
        //"advancements\.occultism\.(.*?)\.title": "(.*)",
        //this.advancementTitle\("\1", "\2"\);
        this.advancementTitle("root", "Occultism");
        this.advancementDescr("root", "Devenez spirituel!");
        this.advancementTitle("summon_foliot_crusher", "Doublement du minerai");
        this.advancementDescr("summon_foliot_crusher", "Crunch ! Crunch ! Crunch!");
        this.advancementTitle("familiars", "Occultism: Amis");
        this.advancementDescr("familiars", "Utiliser un rituel pour invoquer un familier");
        this.advancementDescr("familiar.bat", "Attirer une chauve-souris normale près de votre familier de chauve-souris");
        this.advancementTitle("familiar.bat", "Cannibalisme");
        this.advancementDescr("familiar.capture", "Piéger votre familier dans un anneau familier");
        this.advancementTitle("familiar.capture", "Attrapez-les tous !");
        this.advancementDescr("familiar.cthulhu", "Rendez votre familier cthulhu triste");
        this.advancementTitle("familiar.cthulhu", "Espèce de monstre !");
        this.advancementDescr("familiar.deer", "Observez quand votre cerf familier fait ses besoins en graines de démon.");
        this.advancementTitle("familiar.deer", "Caca démoniaque");
        this.advancementDescr("familiar.devil", "Ordonnez à votre familier démoniaque de cracher du feu");
        this.advancementTitle("familiar.devil", "Hellfire");
        this.advancementDescr("familiar.dragon_nugget", "Donnez une pépite d'or à votre dragon familier");
        this.advancementTitle("familiar.dragon_nugget", "Offre spéciale!");
        this.advancementDescr("familiar.dragon_ride", "Laissez votre greedy familier ramasser quelque chose en chevauchant un familier dragon.");
        this.advancementTitle("familiar.dragon_ride", "Travailler ensemble");
        this.advancementDescr("familiar.greedy", "Laissez votre familier gourmand choisir quelque chose pour vous.");
        this.advancementTitle("familiar.greedy", "Errand Boy");
        this.advancementDescr("familiar.party", "Faites danser vos proches");
        this.advancementTitle("familiar.party", "Dance !");
        this.advancementDescr("familiar.rare", "Obtenir une variante rare de familier");
        this.advancementTitle("familiar.rare", "Ami rare");
        this.advancementDescr("familiar.root", "Utiliser un rituel pour invoquer un familier");
        this.advancementTitle("familiar.root", "Occultism: Amis");
        this.advancementDescr("familiar.mans_best_friend", "Caressez votre dragon familier et jouez à la balle avec lui.");
        this.advancementTitle("familiar.mans_best_friend", "Le meilleur ami de l'homme");
        this.advancementTitle("familiar.blacksmith_upgrade", "Entièrement équipé !");
        this.advancementDescr("familiar.blacksmith_upgrade", "Laissez votre familier forgeron améliorer l'un de vos autres familiers.");
        this.advancementTitle("familiar.guardian_ultimate_sacrifice", "L'Ultime Sacrifice");
        this.advancementDescr("familiar.guardian_ultimate_sacrifice", "Laissez mourir votre familier gardien pour vous sauver.");
        this.advancementTitle("familiar.headless_cthulhu_head", "L'horreur!");
        this.advancementDescr("familiar.headless_cthulhu_head", "Tuez Cthulhu près de votre familier Ratman sans tête.");
        this.advancementTitle("familiar.headless_rebuilt", "Nous pouvons le reconstruire");
        this.advancementDescr("familiar.headless_rebuilt", "\"Rebuild\" votre familier Ratman sans tête après sa mort");
        this.advancementTitle("familiar.chimera_ride", "Monter!");
        this.advancementDescr("familiar.chimera_ride", "Chevauchez votre chimère familière quand vous l'avez suffisamment nourrie.");
        this.advancementTitle("familiar.goat_detach", "Disassemble");
        this.advancementDescr("familiar.goat_detach", "Donnez à votre familier Chimera une pomme d'or");
        this.advancementTitle("familiar.shub_niggurath_summon", "La chèvre noire des bois");
        this.advancementDescr("familiar.shub_niggurath_summon", "Transformez votre chèvre familière en quelque chose de terrible");
        this.advancementTitle("familiar.shub_cthulhu_friends", "L'amour eldritch");
        this.advancementDescr("familiar.shub_cthulhu_friends", "Regardez Shub Niggurath et Cthulhu se tenir la main.");
        this.advancementTitle("familiar.shub_niggurath_spawn", "Pensez aux enfants!");
        this.advancementDescr("familiar.shub_niggurath_spawn", "Faites en sorte qu'un spécimen de Shub Niggurath endommage un ennemi en explosant.");
        this.advancementTitle("familiar.beholder_ray", "Rayon de la mort");
        this.advancementDescr("familiar.beholder_ray", "Laissez votre familier Beholder attaquer un ennemi");
        this.advancementTitle("familiar.beholder_eat", "Faim");
        this.advancementDescr("familiar.beholder_eat", "Regardez votre familier Beholder manger une progéniture de Shub Niggurath.");
        this.advancementTitle("familiar.fairy_save", "Ange gardien");
        this.advancementDescr("familiar.fairy_save", "Laissez votre fée familière sauver un de vos autres familiers d'une mort certaine.");
        this.advancementTitle("familiar.mummy_dodge", "Ninja!");
        this.advancementDescr("familiar.mummy_dodge", "Esquiver une attaque avec l'effet d'esquive familier de la momie.");
        this.advancementTitle("familiar.beaver_woodchop", "Woodchopper");
        this.advancementDescr("familiar.beaver_woodchop", "Laissez votre Castor familier abattre un arbre");
    }

    private void addKeybinds() {
        this.add("key.occultism.category", "Occultism");
        this.add("key.occultism.backpack", "Sac à dos ouvert");
        this.add("key.occultism.storage_remote", "Accessoire de stockage ouvert");
        this.add("key.occultism.familiar.otherworld_bird", "Effet de bague : Drikwing");
        this.add("key.occultism.familiar.greedy_familiar", "Effet d'anneau : greedy");
        this.add("key.occultism.familiar.bat_familiar", "Effet d'anneau: Chauve-souris");
        this.add("key.occultism.familiar.deer_familiar", "Effet d'anneau: Cerf");
        this.add("key.occultism.familiar.cthulhu_familiar", "Effet d'anneau: Cthulhu");
        this.add("key.occultism.familiar.devil_familiar", "Effet d'anneau: Diable");
        this.add("key.occultism.familiar.dragon_familiar", "Effet d'anneau: Dragon");
        this.add("key.occultism.familiar.blacksmith_familiar", "Effet d'anneau: Blacksmith");
        this.add("key.occultism.familiar.guardian_familiar", "Effet d'anneau: Guardian");
        this.add("key.occultism.familiar.headless_familiar", "Effet d'anneau: Ratman sans tête");
        this.add("key.occultism.familiar.chimera_familiar", "Effet d'anneau: chimère");
        this.add("key.occultism.familiar.goat_familiar", "Effet d'anneau: Goat");
        this.add("key.occultism.familiar.shub_niggurath_familiar", "Effet d'anneau: Shub Niggurath");
        this.add("key.occultism.familiar.beholder_familiar", "Effet d'anneau: Beholder");
        this.add("key.occultism.familiar.fairy_familiar", "Effet d'anneau: Fée");
        this.add("key.occultism.familiar.mummy_familiar", "Effet d'anneau: Momie");
        this.add("key.occultism.familiar.beaver_familiar", "Effet d'anneau: Castor");
    }

    private void addJeiTranslations() {
        this.add("occultism.jei.spirit_fire", "Feu d'esprit");
        this.add("occultism.jei.crushing", "Esprit de concasseur");
        this.add("occultism.jei.miner", "Mineshaft Dimensionnel");
        this.add("occultism.jei.miner.chance", "Chance: %d%%");
        this.add("occultism.jei.ritual", "Occult Rituel");
        this.add("occultism.jei.pentacle", "Pentacle");
        this.add("jei.occultism.ingredient.tallow.description", "Tuer des animaux, comme \u00a72pigs\u00a7r, \u00a72cows\u00a7r, \u00a72sheep\u00a7r, \u00a72horses\u00a7r et \u00a72lamas\u00a7r avec le couteau de boucher pour obtenir du suif.");
        this.add("jei.occultism.ingredient.otherstone.description", "Se trouve principalement dans les bosquets de l'Autre Monde. Seulement visible lorsque le statut \u00a76Troisième œil\u00a7r est actif. Voir \u00a76Dictionary des esprits\u00a7r pour plus d'informations.");
        this.add("jei.occultism.ingredient.otherworld_log.description", "Se trouve principalement dans les bosquets de l'Autre Monde. Seulement visible lorsque le statut \u00a76Troisième œil\u00a7r est actif. Voir \u00a76Dictionary of Spirits\u00a7r pour plus d'informations.");
        this.add("jei.occultism.ingredient.otherworld_sapling.description", "On le trouve principalement dans les bosquets de l'Autre Monde. Visible uniquement lorsque le statut \u00a76Troisième œil\u00a7r est actif. Voir \u00a76Dictionnaire des Esprits\u00a7r pour plus d'informations.");
        this.add("jei.occultism.ingredient.otherworld_sapling_natural.description", "On le trouve principalement dans les bosquets de l'Autre Monde. Il n'est visible que lorsque le statut \u00a76Troisième œil\u00a7r est actif. Voir \u00a76Dictionnaire des Esprits\u00a7r pour plus d'informations.");
        this.add("jei.occultism.ingredient.otherworld_leaves.description", "Se trouve principalement dans les bosquets de l'Autre Monde. Seulement visible lorsque le statut \u00a76Troisième œil\u00a7r est actif. Voir \u00a76Dictionary of Spirits\u00a7r pour plus d'informations.");
        this.add("jei.occultism.ingredient.iesnium_ore.description", "Trouvé dans le néant. Seulement visible lorsque le statut \u00a76Third\u00a7r \u00a76Eye\u00a7r est actif. Voir \u00a76Dictionary\u00a7r \u00a76of\u00a7r \u00a76Spirits\u00a7r pour plus d'informations.");
        this.add("jei.occultism.ingredient.spirit_fire.description", "Lancer \u00a76Fruit du rêve du démon\u00a7r sur le sol et y mettre le feu. Voir \u00a76Dictionnaire des Esprits\u00a7r pour plus d'informations.");
        this.add("jei.occultism.ingredient.datura.description", "Peut être utilisé pour guérir tous les esprits et familiers invoqués par les rituels d'occultisme. Il suffit de faire un clic droit sur l'entité pour la guérir d'un cœur.");
        this.add("jei.occultism.sacrifice", "Sacrifice: %s");
        this.add("jei.occultism.summon", "Invoquer: %s");
        this.add("jei.occultism.job", "Emploi: %s");
        this.add("jei.occultism.item_to_use", "Item à utiliser:");
        this.add("jei.occultism.error.missing_id", "Impossible d'identifier la recette.");
        this.add("jei.occultism.error.invalid_type", "Type de recette non valide.");
        this.add("jei.occultism.error.recipe_too_large", "Recette plus grande que 3x3.");
        this.add("jei.occultism.error.pentacle_not_loaded", "Le pentacle n'a pas pu être chargé.");
        this.add("item.occultism.jei_dummy.require_sacrifice", "Requiert un sacrifice !");
        this.add("item.occultism.jei_dummy.require_sacrifice.tooltip", "Ce rituel nécessite un sacrifice pour commencer. Veuillez vous référer au Dictionnaire des Esprits pour des instructions détaillées.");
        this.add("item.occultism.jei_dummy.require_item_use", "Nécessite l'utilisation de l'article !");
        this.add("item.occultism.jei_dummy.require_item_use.tooltip", "Ce rituel nécessite d'utiliser un objet spécifique pour commencer. Veuillez vous référer au Dictionnaire des Esprits pour des instructions détaillées..");
        this.add("item.occultism.jei_dummy.none", "Résultat du rituel sans objet");
        this.add("item.occultism.jei_dummy.none.tooltip", "Ce rituel ne crée pas d'objets.");
    }

    private void addFamiliarSettingsMessages() {
        this.add("message.occultism.familiar.otherworld_bird.enabled", "Effet de l'anneau - Drikwing : Activé");
        this.add("message.occultism.familiar.otherworld_bird.disabled", "Effet de l'anneau - Drikwing: Disabled");
        this.add("message.occultism.familiar.greedy_familiar.enabled", "Effet de l'anneau - Greedy: Enabled");
        this.add("message.occultism.familiar.greedy_familiar.disabled", "Effet de l'anneau - Greedy: Disabled");
        this.add("message.occultism.familiar.bat_familiar.enabled", "Effet de l'anneau - Bat: Enabled");
        this.add("message.occultism.familiar.bat_familiar.disabled", "Effet de l'anneau - Bat: Disabled");
        this.add("message.occultism.familiar.deer_familiar.enabled", "Effet de l'anneau - Cerf: Enabled");
        this.add("message.occultism.familiar.deer_familiar.disabled", "Effet de l'anneau - Cerf: Disabled");
        this.add("message.occultism.familiar.cthulhu_familiar.enabled", "Effet de l'anneau - Cthulhu: Enabled");
        this.add("message.occultism.familiar.cthulhu_familiar.disabled", "Effet de l'anneau - Cthulhu: Disabled");
        this.add("message.occultism.familiar.devil_familiar.enabled", "Effet de l'anneau - Diable: Enabled");
        this.add("message.occultism.familiar.devil_familiar.disabled", "Effet de l'anneau - Diable: Disabled");
        this.add("message.occultism.familiar.dragon_familiar.enabled", "Effet de l'anneau - Dragon: Enabled");
        this.add("message.occultism.familiar.dragon_familiar.disabled", "Effet de l'anneau - Dragon: Disabled");
        this.add("message.occultism.familiar.blacksmith_familiar.enabled", "Effet de l'anneau - Forgeron: Enabled");
        this.add("message.occultism.familiar.blacksmith_familiar.disabled", "Effet de l'anneau - Forgeron: Disabled");
        this.add("message.occultism.familiar.guardian_familiar.enabled", "Effet de l'anneau - Guardian: Enabled");
        this.add("message.occultism.familiar.guardian_familiar.disabled", "Effet de l'anneau - Guardian: Disabled");
        this.add("message.occultism.familiar.headless_familiar.enabled", "Effet de l'anneau - Ratman sans tête: Enabled");
        this.add("message.occultism.familiar.headless_familiar.disabled", "Effet de l'anneau - Ratman sans tête: Disabled");
        this.add("message.occultism.familiar.chimera_familiar.enabled", "Effet de l'anneau - chimère: Enabled");
        this.add("message.occultism.familiar.chimera_familiar.disabled", "Effet de l'anneau - chimère: Disabled");
        this.add("message.occultism.familiar.shub_niggurath_familiar.enabled", "Effet de l'anneau - Shub Niggurath: Enabled");
        this.add("message.occultism.familiar.shub_niggurath_familiar.disabled", "Effet de l'anneau - Shub Niggurath: Disabled");
        this.add("message.occultism.familiar.beholder_familiar.enabled", "Effet de l'anneau - Beholder: Enabled");
        this.add("message.occultism.familiar.beholder_familiar.disabled", "Effet de l'anneau - Beholder: Disabled");
        this.add("message.occultism.familiar.fairy_familiar.enabled", "Effet de l'anneau - Fée: Enabled");
        this.add("message.occultism.familiar.fairy_familiar.disabled", "Effet de l'anneau - Fée: Disabled");
        this.add("message.occultism.familiar.mummy_familiar.enabled", "Effet de l'anneau - Momie: Enabled");
        this.add("message.occultism.familiar.mummy_familiar.disabled", "Effet de l'anneau - Momie: Disabled");
        this.add("message.occultism.familiar.beaver_familiar.enabled", "Effet de l'anneau - Castor: Enabled");
        this.add("message.occultism.familiar.beaver_familiar.disabled", "Effet de l'anneau - Castor: Disabled");
    }

    private void addPentacles() {
        this.addPentacle("otherworld_bird", "Oiseau d'un autre monde");
        this.addPentacle("craft_afrit", "L'enfermement permanent de Sevira");
        this.addPentacle("craft_djinni", "La liaison supérieure de Strigeor");
        this.addPentacle("craft_foliot", "Compulsion spectrale d'Eziveus");
        this.addPentacle("craft_marid", "Tour inversée Uphyx");
        this.addPentacle("possess_afrit", "Conjuration de commandement d'Abras");
        this.addPentacle("possess_djinni", "Enthousiasme d'Ihagan");
        this.addPentacle("possess_foliot", "Le leurre d'Hedyrin");
        this.addPentacle("summon_afrit", "Conjuration d'Abras");
        this.addPentacle("summon_djinni", "L'appel d'Ophyx");
        this.addPentacle("summon_foliot", "Le cercle d'Aviar");
        this.addPentacle("summon_wild_afrit", "Conjuration ouverte d'Abras");
        this.addPentacle("summon_marid", "L'attraction incitative de Fatma");
        this.addPentacle("summon_wild_greater_spirit", "L'appel délié d'Osorin");
    }

    private void addPentacle(String id, String name) {
        this.add(Util.makeDescriptionId("multiblock", new ResourceLocation(Occultism.MODID, id)), name);
    }

    private void addRitualDummies() {
        this.add("item.occultism.ritual_dummy.custom_ritual", "Mannequin de rituel personnalisé");
        this.add("item.occultism.ritual_dummy.custom_ritual.tooltip", "Utilisé pour les modpacks comme solution de repli pour les rituels personnalisés qui ne disposent pas de leur propre objet rituel.");
        this.add("item.occultism.ritual_dummy.craft_dimensional_matrix", "Rituel : Fabrication d'une matrice dimensionnelle");
        this.add("item.occultism.ritual_dummy.craft_dimensional_matrix.tooltip", " La matrice dimensionnelle est le point d'entrée d'une petite dimension utilisée pour stocker des éléments.");
        this.add("item.occultism.ritual_dummy.craft_dimensional_mineshaft", "Rituel: Craft Dimensionel Mineshaft");
        this.add("item.occultism.ritual_dummy.craft_dimensional_mineshaft.tooltip", "Permet aux esprits mineurs d'entrer dans la dimension minière et de rapporter des ressources.");
        this.add("item.occultism.ritual_dummy.craft_infused_lenses", "Rituel : Fabrication de lentilles infusées");
        this.add("item.occultism.ritual_dummy.craft_infused_lenses.tooltip", "Ces lentilles sont utilisées pour fabriquer des lunettes qui permettent de voir au-delà du monde physique.");
        this.add("item.occultism.ritual_dummy.craft_infused_pickaxe", "Rituel : Fabrication d'une pioche infusée");
        this.add("item.occultism.ritual_dummy.craft_infused_pickaxe.tooltip", "Infusez une pioche.");
        this.add("item.occultism.ritual_dummy.craft_miner_djinni_ores", "Rituel : Invocation du mineur de minerai Djinni");
        this.add("item.occultism.ritual_dummy.craft_miner_djinni_ores.tooltip", "Invoquez le mineur de minerai Djinni dans une lampe magique.");
        this.add("item.occultism.ritual_dummy.craft_miner_foliot_unspecialized", "Rituel : Invoquer Foliot Mineur");
        this.add("item.occultism.ritual_dummy.craft_miner_foliot_unspecialized.tooltip", "Invoquez le Mineur Foliot dans une lampe magique.");
        this.add("item.occultism.ritual_dummy.craft_satchel", "Rituel : Fabriquer une sacoche étonnamment substantielle");
        this.add("item.occultism.ritual_dummy.craft_satchel.tooltip", "Cette sacoche permet de stocker plus d'objets que sa taille ne l'indique, ce qui en fait un compagnon de voyage pratique.");
        this.add("item.occultism.ritual_dummy.craft_soul_gem", "Rituel : Fabrication d'une gemme d'âme");
        this.add("item.occultism.ritual_dummy.craft_soul_gem.tooltip", "La gemme de l'âme permet de stocker temporairement des êtres vivants. ");
        this.add("item.occultism.ritual_dummy.craft_familiar_ring", "Rituel : Anneau d'artisanat familial");
        this.add("item.occultism.ritual_dummy.craft_familiar_ring.tooltip", "L'anneau familier permet de stocker des familiers. L'anneau appliquera l'effet du familier à son porteur.");
        this.add("item.occultism.ritual_dummy.craft_stabilizer_tier1", "Rituel : Craft un stabilisateur de rangement Tier 1");
        this.add("item.occultism.ritual_dummy.craft_stabilizer_tier1.tooltip", "Le stabilisateur de stockage permet de stocker plus d'articles dans l'accesseur de stockage dimensionnel.");
        this.add("item.occultism.ritual_dummy.craft_stabilizer_tier2", "Rituel : Craft un stabilisateur de rangement Tier 2");
        this.add("item.occultism.ritual_dummy.craft_stabilizer_tier2.tooltip", "Le stabilisateur de stockage permet de stocker plus d'articles dans l'accesseur de stockage dimensionnel.");
        this.add("item.occultism.ritual_dummy.craft_stabilizer_tier3", "Rituel : Craft un stabilisateur de rangement Tier 3");
        this.add("item.occultism.ritual_dummy.craft_stabilizer_tier3.tooltip", "Le stabilisateur de stockage permet de stocker plus d'articles dans l'accesseur de stockage dimensionnel.");
        this.add("item.occultism.ritual_dummy.craft_stabilizer_tier4", "Rituel : Craft un stabilisateur de rangement Tier 4");
        this.add("item.occultism.ritual_dummy.craft_stabilizer_tier4.tooltip", "Le stabilisateur de stockage permet de stocker plus d'articles dans l'accesseur de stockage dimensionnel.");
        this.add("item.occultism.ritual_dummy.craft_stable_wormhole", "Rituel : Fabrication d'un vortex stable");
        this.add("item.occultism.ritual_dummy.craft_stable_wormhole.tooltip", "Le vortex stable permet d'accéder à une matrice dimensionnelle à partir d'une destination éloignée.");
        this.add("item.occultism.ritual_dummy.craft_storage_controller_base", "Rituel : Base de l'actionneur de stockage de l'artisanat");
        this.add("item.occultism.ritual_dummy.craft_storage_controller_base.tooltip", "La base de l'actionneur de stockage emprisonne un Foliot chargé d'interagir avec les objets dans une matrice de stockage dimensionnelle.");
        this.add("item.occultism.ritual_dummy.craft_storage_remote", "Rituel : Craft Accessoire de stockage ");
        this.add("item.occultism.ritual_dummy.craft_storage_remote.tooltip", "L'accès au stockage peut être lié à un actionneur de stockage pour accéder à distance aux articles.");
        this.add("item.occultism.ritual_dummy.familiar_otherworld_bird", "Rituel : Invocation d'un familier Drikwing");
        this.add("item.occultism.ritual_dummy.familiar_otherworld_bird.tooltip", "Les Drikwings offrent à leur propriétaire des capacités de vol limitées lorsqu'ils sont à proximité.");
        this.add("item.occultism.ritual_dummy.familiar_parrot", "Rituel : invocation de la famille du perroquet");
        this.add("item.occultism.ritual_dummy.familiar_parrot.tooltip", "Les familiers perroquets se comportent exactement comme des perroquets apprivoisés.");
        this.add("item.occultism.ritual_dummy.familiar_greedy", "Rituel : Invocation d'un greedy familier ");
        this.add("item.occultism.ritual_dummy.familiar_greedy.tooltip", "Les greedy familiers ramassent des objets pour leur maître. Lorsqu'ils sont stockés dans un anneau familier, ils augmentent la portée de ramassage (comme un aimant à objets).");
        this.add("item.occultism.ritual_dummy.familiar_bat", "Rituel : Invocation d'un familier chauve-souris");
        this.add("item.occultism.ritual_dummy.familiar_bat.tooltip", "Les chauves-souris familières fournissent une vision nocturne à leur maître.");
        this.add("item.occultism.ritual_dummy.familiar_deer", "Rituel : Appeler le cerf familier");
        this.add("item.occultism.ritual_dummy.familiar_deer.tooltip", "Les familiers du cerf donnent une impulsion de saut à leur maître.");
        this.add("item.occultism.ritual_dummy.familiar_cthulhu", "Rituel : invocation de Cthulhu familier");
        this.add("item.occultism.ritual_dummy.familiar_cthulhu.tooltip", "Les cthulhu familiers fournissent une respiration aquatique à leur maître.");
        this.add("item.occultism.ritual_dummy.familiar_devil", "Ritual: Summon Diable familier");
        this.add("item.occultism.ritual_dummy.familiar_devil.tooltip", "Le diable familier offrent une résistance au feu à leur maître.");
        this.add("item.occultism.ritual_dummy.familiar_dragon", "Rituel : Invocation d'un dragon familier");
        this.add("item.occultism.ritual_dummy.familiar_dragon.tooltip", "Les dragons familiers offrent un gain d'expérience accru à leur maître.");
        this.add("item.occultism.ritual_dummy.familiar_blacksmith", "Rituel : Invocation d'un forgeron familier");
        this.add("item.occultism.ritual_dummy.familiar_blacksmith.tooltip", "Les forgerons familiers prennent la pierre que leur maître extrait et l'utilisent pour réparer les équipements..");
        this.add("item.occultism.ritual_dummy.familiar_guardian", "Rituel : Invocation d'un gardien familier ");
        this.add("item.occultism.ritual_dummy.familiar_guardian.tooltip", "Les gardiens familiers empêchent la mort violente de leur maître.");
        this.add("item.occultism.ritual_dummy.familiar_headless", "Rituel : Invocation du familier Ratman sans tête");
        this.add("item.occultism.ritual_dummy.familiar_headless.tooltip", "Les ratman sans tête familiers augmentent les dégâts d'attaque de leur maître contre les ennemis du type auquel il a volé la tête.");
        this.add("item.occultism.ritual_dummy.familiar_chimera", "Rituel: Summon chimère Familière");
        this.add("item.occultism.ritual_dummy.familiar_chimera.tooltip", "Les familiers chimères peuvent être nourris pour grandir en taille et gagner en vitesse d'attaque et en dégâts. Une fois suffisamment grands, les joueurs peuvent les chevaucher.");
        this.add("item.occultism.ritual_dummy.familiar_beholder", "Rituel : Invocation du Beholder Familier");
        this.add("item.occultism.ritual_dummy.familiar_beholder.tooltip", "Les familiers beholder mettent en évidence les entités proches par un effet lumineux et tirent des rayons laser sur les ennemis.");
        this.add("item.occultism.ritual_dummy.familiar_fairy", "Rituel : Invocation d'une fée familière");
        this.add("item.occultism.ritual_dummy.familiar_fairy.tooltip", "Le familier des fées empêche les autres familiers de mourir, draine la force vitale des ennemis et soigne son maître et ses familiers.");
        this.add("item.occultism.ritual_dummy.familiar_mummy", "Rituel : Invocation d'une momie familière");
        this.add("item.occultism.ritual_dummy.familiar_mummy.tooltip", "Le familier de la momie est un expert en arts martiaux et se bat pour protéger son maître.");
        this.add("item.occultism.ritual_dummy.familiar_beaver", "Rituel : Appeler le castor familier");
        this.add("item.occultism.ritual_dummy.familiar_beaver.tooltip", "Le familier Castor offre à son maître une vitesse accrue pour la coupe du bois et récolte les arbres proches lorsqu'ils poussent à partir d'une pousse d'arbre.");
        this.add("item.occultism.ritual_dummy.possess_enderman", "Rituel : Invocation d'un Enderman possédé");
        this.add("item.occultism.ritual_dummy.possess_enderman.tooltip", "L'Enderman possédé laissera toujours tomber au moins une perle Ender lorsqu'il sera tué.");
        this.add("item.occultism.ritual_dummy.possess_endermite", "Rituel : invocation d'une Endermite possédée.");
        this.add("item.occultism.ritual_dummy.possess_endermite.tooltip", "L'Endermite possédée laisse tomber la pierre d'extrémité.");
        this.add("item.occultism.ritual_dummy.possess_skeleton", "Rituel : Invocation d'un squelette possédé");
        this.add("item.occultism.ritual_dummy.possess_skeleton.tooltip", " Le squelette possédé est immunisé contre la lumière du jour et laisse toujours tomber au moins un crâne de squelette lorsqu'il est tué.");
        this.add("item.occultism.ritual_dummy.possess_ghast", "Rituel : Invocation d'un Ghast possédé");
        this.add("item.occultism.ritual_dummy.possess_ghast.tooltip", "Le Ghast possédé laissera toujours tomber au moins une larme de ghast lorsqu'il est tué.");
        this.add("item.occultism.ritual_dummy.summon_afrit_rain_weather", "Rituel : Temps pluvieux");
        this.add("item.occultism.ritual_dummy.summon_afrit_rain_weather.tooltip", "Invoque un Afrit lié qui crée de la pluie.");
        this.add("item.occultism.ritual_dummy.summon_afrit_thunder_weather", "Rituel : Orage");
        this.add("item.occultism.ritual_dummy.summon_afrit_thunder_weather.tooltip", "Invoque un Afrit lié qui crée un orage.");
        this.add("item.occultism.ritual_dummy.summon_djinni_clear_weather", "Rituel : Temps clair");
        this.add("item.occultism.ritual_dummy.summon_djinni_clear_weather.tooltip", "Invoque un Djinni qui dégage le temps.");
        this.add("item.occultism.ritual_dummy.summon_djinni_day_time", "Rituel : Invocation de l'aube");
        this.add("item.occultism.ritual_dummy.summon_djinni_day_time.tooltip", "Invoque un Djinni qui met l'heure à midi.");
        this.add("item.occultism.ritual_dummy.summon_djinni_manage_machine", "Rituel : Invocation d'un opérateur de machine Djinni");
        this.add("item.occultism.ritual_dummy.summon_djinni_manage_machine.tooltip", "L'opérateur de la machine transfère automatiquement les articles entre les systèmes de stockage dimensionnel et les inventaires et machines connectés.");
        this.add("item.occultism.ritual_dummy.summon_djinni_night_time", "Rituel : Invocation du Crépuscule");
        this.add("item.occultism.ritual_dummy.summon_djinni_night_time.tooltip", "Invoque un Djinni qui met l'heure à minuit.");
        this.add("item.occultism.ritual_dummy.summon_foliot_crusher", "Rituel : invocation du concasseur");
        this.add("item.occultism.ritual_dummy.summon_foliot_crusher.tooltip", "Le concasseur est un esprit invoqué pour réduire les minerais en poussières, doublant ainsi la production de métal.");
        this.add("item.occultism.ritual_dummy.summon_djinni_crusher", "Rituel : invocation du concasseur de Djinni");
        this.add("item.occultism.ritual_dummy.summon_djinni_crusher.tooltip", "Le concasseur est un esprit invoqué pour réduire les minerais en poussières, ce qui permet de (plus que) doubler la production de métal. Ce concasseur se désintègre (beaucoup) plus lentement que les concasseurs de niveau inférieur.");
        this.add("item.occultism.ritual_dummy.summon_afrit_crusher", "Rituel : invocation du concasseur d'Afrit");
        this.add("item.occultism.ritual_dummy.summon_afrit_crusher.tooltip", "Le concasseur est un esprit invoqué pour broyer les minerais en poussières, ce qui permet effectivement de (plus que) doubler la production de métal. Ce concasseur se désintègre (beaucoup) plus lentement que les concasseurs de niveau inférieur. ");
        this.add("item.occultism.ritual_dummy.summon_marid_crusher", "Rituel : invocation de l'écraseur de Marid.");
        this.add("item.occultism.ritual_dummy.summon_marid_crusher.tooltip", " Le concasseur est un esprit invoqué pour broyer les minerais en poussières, ce qui permet effectivement de (plus que) doubler la production de métal. Ce concasseur se désintègre (beaucoup) plus lentement que les concasseurs de niveau inférieur.");
        this.add("item.occultism.ritual_dummy.summon_foliot_lumberjack", "Rituel : Invocation d'un bûcheron foliot");
        this.add("item.occultism.ritual_dummy.summon_foliot_lumberjack.tooltip", "Le bûcheron récoltera les arbres dans sa zone de travail et déposera les objets tombés dans le coffre spécifié.");
        this.add("item.occultism.ritual_dummy.summon_foliot_otherstone_trader", "Rituel : invocation d'un commerçant d'une pierre étrange");
        this.add("item.occultism.ritual_dummy.summon_foliot_otherstone_trader.tooltip", "Le marchand de pierres étranges échange des pierres normales contre des pierres étranges.");
        this.add("item.occultism.ritual_dummy.summon_foliot_sapling_trader", "Rituel : invocation d'un marchand de pousses d'arbres de l'autre monde");
        this.add("item.occultism.ritual_dummy.summon_foliot_sapling_trader.tooltip", "Le négociant en pousses d'arbres de l'autre monde échange des pousses d'arbres naturels de l'autre monde contre des arbres stables, qui peuvent être récoltés sans le troisième œil.");
        this.add("item.occultism.ritual_dummy.summon_foliot_transport_items", "Rituel : invocation du transporteur Foliot");
        this.add("item.occultism.ritual_dummy.summon_foliot_transport_items.tooltip", "Le transporteur déplace tous les objets auxquels il a accès d'un inventaire à un autre, y compris les machines.");
        this.add("item.occultism.ritual_dummy.summon_foliot_cleaner", "Rituel : invocation du concierge de Foliot");
        this.add("item.occultism.ritual_dummy.summon_foliot_cleaner.tooltip", "Le concierge ramasse les objets tombés et les dépose dans un inventaire cible.");
        this.add("item.occultism.ritual_dummy.summon_wild_afrit", "Rituel : Invocation d'Afrit déliée");
        this.add("item.occultism.ritual_dummy.summon_wild_afrit.tooltip", "Invoque un Afrit non lié qui peut être tué pour obtenir de l'essence d'Afrit.");
        this.add("item.occultism.ritual_dummy.summon_wild_hunt", "Rituel: invoque la chasse sauvage");
        this.add("item.occultism.ritual_dummy.summon_wild_hunt.tooltip", "la chasse sauvage se compose de squelettes Wither qui sont garantis de laisser tomber des crânes de squelettes Wither, et leurs serviteurs.");
        this.add("item.occultism.ritual_dummy.summon_wild_otherworld_bird", "Rituel : Invocation d'un Drikwing sauvage");
        this.add("item.occultism.ritual_dummy.summon_wild_otherworld_bird.tooltip", "Invoque un Drikwing familier qui peut être apprivoisé par n'importe qui, pas seulement par l'invocateur.");
        this.add("item.occultism.ritual_dummy.summon_wild_parrot", "Rituel : Invocation de perroquet sauvage");
        this.add("item.occultism.ritual_dummy.summon_wild_parrot.tooltip", "Invoque un perroquet qui peut être apprivoisé par n'importe qui, pas seulement par l'invocateur.");
    }

    private void addDialogs() {
        this.add("dialog.occultism.dragon.pet", "purrr");
        this.add("dialog.occultism.mummy.kapow", "KAPOW!");
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
    }
}