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

package com.klikli_dev.occultism.datagen.lang;

import com.klikli_dev.modonomicon.api.ModonomiconAPI;
import com.klikli_dev.modonomicon.api.datagen.BookLangHelper;
import com.klikli_dev.occultism.Occultism;
import com.klikli_dev.occultism.datagen.OccultismAdvancementProvider;
import com.klikli_dev.occultism.integration.modonomicon.OccultismModonomiconConstants;
import com.klikli_dev.occultism.registry.OccultismBlocks;
import com.klikli_dev.occultism.registry.OccultismEntities;
import com.klikli_dev.occultism.registry.OccultismItems;
import net.minecraft.Util;
import net.minecraft.data.PackOutput;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.data.LanguageProvider;

public class PTBRProvider extends LanguageProvider {

    public static final String COLOR_PURPLE = "ad03fc";
    public static final String DEMONS_DREAM = "Demon's Dream";


    public PTBRProvider(PackOutput gen) {
        super(gen, Occultism.MODID, "pt_br");
    }

    public void addItemMessages() {

        //"item\.occultism\.(.*?)\.(.*)": "(.*)",
        // this.add\(OccultismItems.\U\1\E.get\(\).getDescriptionId\(\)  + " \2", "\3"\);

        //book of callings use generic message base key, hence the manual string   
        this.add("item.occultism.book_of_calling" + ".message_target_uuid_no_match", "Esse espírito não está atualmente vinculado a esse livro. Shift clique no espírito para vinculá-lo a esse livro.");
        this.add("item.occultism.book_of_calling" + ".message_target_linked", "Esse espírito não está vinculado a esse livro.");
        this.add("item.occultism.book_of_calling" + ".message_target_cannot_link", "Esse espírito não pode ser vinculado a esse livro; o livro de chamado precisa corresponder à tarefa do espírito!");
        this.add("item.occultism.book_of_calling" + ".message_target_entity_no_inventory", "Essa entidade não tem inventário, ela não pode ser definida como local de depósito.");
        this.add("item.occultism.book_of_calling" + ".message_spirit_not_found", "O espírito vinculado a esse livro não está habitando esse plano de existência.");
        this.add("item.occultism.book_of_calling" + ".message_set_deposit", "%s agora irá depositar em %s pelo lado: %s");
        this.add("item.occultism.book_of_calling" + ".message_set_deposit_entity", "%s agora irá entregar itens para: %s");
        this.add("item.occultism.book_of_calling" + ".message_set_extract", "%s agora irá extrair de %s pelo lado: %s");
        this.add("item.occultism.book_of_calling" + ".message_set_base", "Definiu a base de %s a %s");
        this.add("item.occultism.book_of_calling" + ".message_set_storage_controller", "%s agora irá aceitar ordens de trabalho de %s");
        this.add("item.occultism.book_of_calling" + ".message_set_work_area_size", "%s agora irá monitorar a área de trabalho de %s");
        this.add("item.occultism.book_of_calling" + ".message_set_managed_machine", "Configurações da máquina atualizadas para %s");
        this.add("item.occultism.book_of_calling" + ".message_set_managed_machine_extract_location", "%s agora irá extrair de %s pelo lado: %s");
        this.add("item.occultism.book_of_calling" + ".message_no_managed_machine", "Defina uma máquina gerenciada antes de definir um local de extração %s");

        this.add(OccultismItems.STABLE_WORMHOLE.get().getDescriptionId() + ".message.set_storage_controller", "Ligou o buraco de minhoca estável a este atuador de armazenamento.");
        this.add(OccultismItems.STORAGE_REMOTE.get().getDescriptionId() + ".message.not_loaded", "Chunk para o atuador de armazenamento não carregado!");
        this.add(OccultismItems.STORAGE_REMOTE.get().getDescriptionId() + ".message.linked", "Ligou controle de armazenamento ao atuador.");
        this.add(OccultismItems.DIVINATION_ROD.get().getDescriptionId() + ".message.no_linked_block", "A vara de adivinhação não está sintonizada a nenhum material.");
        this.add(OccultismItems.DIVINATION_ROD.get().getDescriptionId() + ".message.linked_block", "A vara de adivinhação agora está sintonizada para %s.");
        this.add(OccultismItems.DIVINATION_ROD.get().getDescriptionId() + ".message.no_link_found", "Não tem ressonância com este bloco.");
        this.add(OccultismItems.SOUL_GEM_ITEM.get().getDescriptionId() + ".message.entity_type_denied", "Gemas da alma não podem conter este tipo de ser.");
    }

    public void addItemTooltips() {
        //"item\.occultism\.(.*?)\.(.*)": "(.*)",
        // this.add\(OccultismItems.\U\1\E.get\(\).getDescriptionId\(\)  + " \2", "\3"\);

        this.add(OccultismItems.BOOK_OF_BINDING_FOLIOT.get().getDescriptionId() + ".tooltip", "Este livro ainda não foi vinculado a um foliot.");
        this.add(OccultismItems.BOOK_OF_BINDING_BOUND_FOLIOT.get().getDescriptionId() + ".tooltip", "Pode ser usado para invocar o foliot %s");
        this.add(OccultismItems.BOOK_OF_BINDING_DJINNI.get().getDescriptionId() + ".tooltip", "Este livro ainda não foi vinculado a um djinni.");
        this.add(OccultismItems.BOOK_OF_BINDING_BOUND_DJINNI.get().getDescriptionId() + ".tooltip", "Pode ser usado para invocar o djinni %s");
        this.add(OccultismItems.BOOK_OF_BINDING_AFRIT.get().getDescriptionId() + ".tooltip", "Este livro ainda não foi vinculado a um afrit.");
        this.add(OccultismItems.BOOK_OF_BINDING_BOUND_AFRIT.get().getDescriptionId() + ".tooltip", "Pode ser usado para invocar o afrit %s");
        this.add(OccultismItems.BOOK_OF_BINDING_MARID.get().getDescriptionId() + ".tooltip", "Este livro ainda não foi vinculado a um marid.");
        this.add(OccultismItems.BOOK_OF_BINDING_BOUND_MARID.get().getDescriptionId() + ".tooltip", "Pode ser usado para invocar o marid %s");

        this.add("item.occultism.book_of_calling_foliot" + ".tooltip", "Foliot %s");
        this.add("item.occultism.book_of_calling_foliot" + ".tooltip_dead", "%s deixou este plano de existência.");
        this.add("item.occultism.book_of_calling_foliot" + ".tooltip.extract", "Extrai de: %s.");
        this.add("item.occultism.book_of_calling_foliot" + ".tooltip.deposit", "Deposita para: %s.");
        this.add("item.occultism.book_of_calling_foliot" + ".tooltip.deposit_entity", "Entrega itens para: %s.");
        this.add("item.occultism.book_of_calling_djinni" + ".tooltip", "Djinni %s");
        this.add("item.occultism.book_of_calling_djinni" + ".tooltip_dead", "%s deixou este plano de existência.");
        this.add("item.occultism.book_of_calling_djinni" + ".tooltip.extract", "Extrai de: %s.");
        this.add("item.occultism.book_of_calling_djinni" + ".tooltip.deposit", "Deposita para: % s");
        this.add(OccultismItems.FAMILIAR_RING.get().getDescriptionId() + ".tooltip", "Ocupado pelo familiar %s");

        this.add("item.minecraft.diamond_sword.occultism_spirit_tooltip", "%s está ligado a esta espada. Que seus inimigos estremeçam diante de sua glória.");

        this.add(OccultismItems.STABLE_WORMHOLE.get().getDescriptionId() + ".tooltip.unlinked", "Não vinculado a um atuador de armazenamento.");
        this.add(OccultismItems.STABLE_WORMHOLE.get().getDescriptionId() + ".tooltip.linked", "Vinculado ao atuador de armazenamento em %s.");
        this.add(OccultismItems.STORAGE_REMOTE.get().getDescriptionId() + ".tooltip", "Acesse uma rede de armazenamento remotamente.");

        this.add(OccultismItems.STORAGE_REMOTE.get().getDescriptionId() + ".tooltip.linked", "Vinculado a %s.");
        this.add(OccultismItems.DIVINATION_ROD.get().getDescriptionId() + ".tooltip.no_linked_block", "Não sintonizado com nenhum material.");
        this.add(OccultismItems.DIVINATION_ROD.get().getDescriptionId() + ".tooltip.linked_block", "Sintonizado a %s.");
        this.add(OccultismItems.DIMENSIONAL_MATRIX.get().getDescriptionId() + ".tooltip", "%s está vinculado a esta matriz dimensional.");
        this.add(OccultismItems.INFUSED_PICKAXE.get().getDescriptionId() + ".tooltip", "%s está vinculado a esta picareta.");
        this.add(OccultismItems.MINER_FOLIOT_UNSPECIALIZED.get().getDescriptionId() + ".tooltip", "%s irá minerar blocos aleatórios na dimensão de mineração.");
        this.add(OccultismItems.MINER_DJINNI_ORES.get().getDescriptionId() + ".tooltip", "%s irá minerar minérios aleatórios na dimensão de mineração.");
        this.add(OccultismItems.MINER_DEBUG_UNSPECIALIZED.get().getDescriptionId() + ".tooltip", "Debug Miner irá minerar blocos aleatórios na dimensão de mineração.");
        this.add(OccultismItems.SOUL_GEM_ITEM.get().getDescriptionId() + ".tooltip_filled", "Contém um(a) %s capturado.");
        this.add(OccultismItems.SOUL_GEM_ITEM.get().getDescriptionId() + ".tooltip_empty", "Use em uma criatura para capturá-la.");
        this.add(OccultismItems.SATCHEL.get().getDescriptionId() + ".tooltip", "%s está vinculado a esta bolsa.");
    }

    private void addItems() {
        //Notepad++ magic:
        //"item\.occultism\.(.*)": "(.*)"
        //this.addItem\(OccultismItems.\U\1\E, "\2"\);

        this.add("itemGroup.occultism", "Occultism");

        this.addItem(OccultismItems.PENTACLE, "Pentáculo");
        this.addItem(OccultismItems.DEBUG_WAND, "Varinha de debug");
        this.addItem(OccultismItems.DEBUG_FOLIOT_LUMBERJACK, "Invocar debug de foliot lenhador");
        this.addItem(OccultismItems.DEBUG_FOLIOT_TRANSPORT_ITEMS, "Invocar debug de foliot transportador");
        this.addItem(OccultismItems.DEBUG_FOLIOT_CLEANER, "Invocar debug de foliot zelador");
        this.addItem(OccultismItems.DEBUG_FOLIOT_TRADER_ITEM, "Invocar debug de foliot mercador");
        this.addItem(OccultismItems.DEBUG_DJINNI_MANAGE_MACHINE, "Invocar debug de djinni gerenciador de máquina");
        this.addItem(OccultismItems.DEBUG_DJINNI_TEST, "Invocar debug de djinni teste");

        this.addItem(OccultismItems.CHALK_GOLD, "Giz dourado");
        this.addItem(OccultismItems.CHALK_PURPLE, "Giz roxo");
        this.addItem(OccultismItems.CHALK_RED, "Giz vermelho");
        this.addItem(OccultismItems.CHALK_WHITE, "Giz branco");
        this.addItem(OccultismItems.CHALK_GOLD_IMPURE, "Giz dourado impuro");
        this.addItem(OccultismItems.CHALK_PURPLE_IMPURE, "Giz roxo impuro");
        this.addItem(OccultismItems.CHALK_RED_IMPURE, "Giz vermelho impuro");
        this.addItem(OccultismItems.CHALK_WHITE_IMPURE, "Giz branco impuro");
        this.addItem(OccultismItems.BRUSH, "Apagador");
        this.addItem(OccultismItems.AFRIT_ESSENCE, "Essência de Afrit");
        this.addItem(OccultismItems.PURIFIED_INK, "Tinta purificada");
        this.addItem(OccultismItems.BOOK_OF_BINDING_FOLIOT, "Livro de Vínculo: Foliot");
        this.addItem(OccultismItems.BOOK_OF_BINDING_BOUND_FOLIOT, "Livro de Vínculo: Foliot (Vinculado)");
        this.addItem(OccultismItems.BOOK_OF_BINDING_DJINNI, "Livro de Vínculo: Djinni");
        this.addItem(OccultismItems.BOOK_OF_BINDING_BOUND_DJINNI, "Livro de Vínculo: Djinni (Vinculado)");
        this.addItem(OccultismItems.BOOK_OF_BINDING_AFRIT, "Livro de Vínculo: Afrit");
        this.addItem(OccultismItems.BOOK_OF_BINDING_BOUND_AFRIT, "Livro de Vínculo: Afrit (Vinculado)");
        this.addItem(OccultismItems.BOOK_OF_BINDING_MARID, "Livro de Vínculo: Marid");
        this.addItem(OccultismItems.BOOK_OF_BINDING_BOUND_MARID, "Livro de Vínculo: Marid (Vinculado)");
        this.addItem(OccultismItems.BOOK_OF_CALLING_FOLIOT_LUMBERJACK, "Livro de Chamado: Foliot Lenhador");
        this.addItem(OccultismItems.BOOK_OF_CALLING_FOLIOT_TRANSPORT_ITEMS, "Livro de Chamado: Foliot Transportador");
        this.addItem(OccultismItems.BOOK_OF_CALLING_FOLIOT_CLEANER, "Livro de Chamado: Foliot Zelador");
        this.addItem(OccultismItems.BOOK_OF_CALLING_DJINNI_MANAGE_MACHINE, "Livro de Chamado: Djinni Maquinista");
        this.addItem(OccultismItems.STORAGE_REMOTE, "Conector de armazenamento");
        this.addItem(OccultismItems.STORAGE_REMOTE_INERT, "Conector de armazenamento inerte");
        this.addItem(OccultismItems.DIMENSIONAL_MATRIX, "Matriz de cristal dimensional");
        this.addItem(OccultismItems.DIVINATION_ROD, "Vara da adivinhação");
        this.addItem(OccultismItems.DATURA_SEEDS, "Sementes de sonho demoníaco");
        this.addItem(OccultismItems.DATURA, "Fruta de sonho demoníaco");
        this.addItem(OccultismItems.SPIRIT_ATTUNED_GEM, "Gema mística sintonizada");
        this.add("item.occultism.otherworld_sapling", "Muda do Outro Mundo");
        this.add("item.occultism.otherworld_sapling_natural", "Muda do Outro Mundo instável");
        this.addItem(OccultismItems.OTHERWORLD_ASHES, "Cinzas do Outro Mundo");
        this.addItem(OccultismItems.BURNT_OTHERSTONE, "Outra pedra queimada");
        this.addItem(OccultismItems.BUTCHER_KNIFE, "Cutelo");
        this.addItem(OccultismItems.TALLOW, "Sebo");
        this.addItem(OccultismItems.OTHERSTONE_FRAME, "Moldura de outra pedra");
        this.addItem(OccultismItems.OTHERSTONE_TABLET, "Tabuleta de outra pedra	");
        this.addItem(OccultismItems.WORMHOLE_FRAME, "Moldura do buraco de minhoca");
        this.addItem(OccultismItems.IRON_DUST, "Pó de ferro");
        this.addItem(OccultismItems.OBSIDIAN_DUST, "Pó de obsidiana");
        this.addItem(OccultismItems.CRUSHED_END_STONE, "Pedra do fim esmagada");
        this.addItem(OccultismItems.GOLD_DUST, "Pó de ouro");
        this.addItem(OccultismItems.COPPER_DUST, "Pó de cobre");
        this.addItem(OccultismItems.SILVER_DUST, "Pó de prata");
        this.addItem(OccultismItems.IESNIUM_DUST, "Pó de Iésnio");
        this.addItem(OccultismItems.RAW_SILVER, "Prata bruto");
        this.addItem(OccultismItems.RAW_IESNIUM, "Iésnio bruto");
        this.addItem(OccultismItems.SILVER_INGOT, "Barra de prata");
        this.addItem(OccultismItems.IESNIUM_INGOT, "Barra de Iésnio");
        this.addItem(OccultismItems.SILVER_NUGGET, "Pepita de prata");
        this.addItem(OccultismItems.IESNIUM_NUGGET, "Pepita de Iésnio");
        this.addItem(OccultismItems.LENSES, "Lentes de vidro");
        this.addItem(OccultismItems.INFUSED_LENSES, "Lentes infundidas");
        this.addItem(OccultismItems.LENS_FRAME, "Armação das lentes");
        this.addItem(OccultismItems.OTHERWORLD_GOGGLES, "Óculos do Outro Mundo");
        this.addItem(OccultismItems.INFUSED_PICKAXE, "Picareta infundida");
        this.addItem(OccultismItems.SPIRIT_ATTUNED_PICKAXE_HEAD, "Cabeça de picareta mítisca sintonizada");
        this.addItem(OccultismItems.IESNIUM_PICKAXE, "Picareta de Iésnio");
        this.addItem(OccultismItems.MAGIC_LAMP_EMPTY, "Lâmpada mágica vazia");
        this.addItem(OccultismItems.MINER_FOLIOT_UNSPECIALIZED, "Mineiro foliot");
        this.addItem(OccultismItems.MINER_DJINNI_ORES, "Mineiro Djinni");
        this.addItem(OccultismItems.MINER_DEBUG_UNSPECIALIZED, "Mineiro de debug");
        this.addItem(OccultismItems.SOUL_GEM_ITEM, "Gema da alma");
        this.add(OccultismItems.SOUL_GEM_ITEM.get().getDescriptionId() + "_empty", "Gema da alma vazia");
        this.addItem(OccultismItems.SATCHEL, "Bolsa Surpreendentemente Substancial");
        this.addItem(OccultismItems.FAMILIAR_RING, "Anel familiar");
        this.addItem(OccultismItems.SPAWN_EGG_FOLIOT, "Ovo gerador de Foliot");
        this.addItem(OccultismItems.SPAWN_EGG_DJINNI, "Ovo gerador de Djinni");
        this.addItem(OccultismItems.SPAWN_EGG_AFRIT, "Ovo gerador de Afrit");
        this.addItem(OccultismItems.SPAWN_EGG_AFRIT_WILD, "Ovo gerador de Afrit desvinculado");
        this.addItem(OccultismItems.SPAWN_EGG_MARID, "Ovo gerador de Marid");
        this.addItem(OccultismItems.SPAWN_EGG_POSSESSED_ENDERMITE, "Ovo gerador de endermite possuído");
        this.addItem(OccultismItems.SPAWN_EGG_POSSESSED_SKELETON, "Ovo gerador de esqueleto possuído");
        this.addItem(OccultismItems.SPAWN_EGG_POSSESSED_ENDERMAN, "Ovo gerador de enderman possuído");
        this.addItem(OccultismItems.SPAWN_EGG_WILD_HUNT_SKELETON, "Ovo gerador de esqueleto da Caça Selvagem");
        this.addItem(OccultismItems.SPAWN_EGG_WILD_HUNT_WITHER_SKELETON, "Ovo gerador de esqueleto wither da Caça Selvagem");
        this.addItem(OccultismItems.SPAWN_EGG_OTHERWORLD_BIRD, "Ovo gerador de Drikwing");
        this.addItem(OccultismItems.SPAWN_EGG_GREEDY_FAMILIAR, "Ovo gerador de avarento familiar");
        this.addItem(OccultismItems.SPAWN_EGG_BAT_FAMILIAR, "Ovo gerador de morcego familiar");
        this.addItem(OccultismItems.SPAWN_EGG_DEER_FAMILIAR, "Ovo gerador de veado familiar");
        this.addItem(OccultismItems.SPAWN_EGG_CTHULHU_FAMILIAR, "Ovo gerador de Cthulhu familiar");
        this.addItem(OccultismItems.SPAWN_EGG_DEVIL_FAMILIAR, "Ovo gerador de diabo familiar");
        this.addItem(OccultismItems.SPAWN_EGG_DRAGON_FAMILIAR, "Ovo gerador de dragão familiar");
        this.addItem(OccultismItems.SPAWN_EGG_BLACKSMITH_FAMILIAR, "Ovo gerador de ferreiro familiar");
        this.addItem(OccultismItems.SPAWN_EGG_GUARDIAN_FAMILIAR, "Ovo gerador de guardião familiar");
        this.addItem(OccultismItems.SPAWN_EGG_HEADLESS_FAMILIAR, "Ovo gerador de homem-rato sem cabeça familiar");
        this.addItem(OccultismItems.SPAWN_EGG_CHIMERA_FAMILIAR, "Ovo gerador de quimera familiar");
        this.addItem(OccultismItems.SPAWN_EGG_GOAT_FAMILIAR, "Ovo gerador de bode familair");
        this.addItem(OccultismItems.SPAWN_EGG_SHUB_NIGGURATH_FAMILIAR, "Ovo gerador de Shub Niggurath familiar");
        this.addItem(OccultismItems.SPAWN_EGG_BEHOLDER_FAMILIAR, "Ovo gerador de Beholder familiar");
        this.addItem(OccultismItems.SPAWN_EGG_FAIRY_FAMILIAR, "Ovo gerador de fada familiar");
        this.addItem(OccultismItems.SPAWN_EGG_MUMMY_FAMILIAR, "Ovo gerador de múmia familiar");
        this.addItem(OccultismItems.SPAWN_EGG_BEAVER_FAMILIAR, "Ovo gerador de castor familiar");
    }

    private void addBlocks() {
        //"block\.occultism\.(.*?)": "(.*)",
        //this.addBlock\(OccultismItems.\U\1\E, "\2"\);

        this.addBlock(OccultismBlocks.OTHERSTONE, "Outra pedra");
        this.addBlock(OccultismBlocks.OTHERSTONE_SLAB, "Laje de outra pedra");
        this.addBlock(OccultismBlocks.OTHERSTONE_PEDESTAL, "Pedestal de outra pedra");
        this.addBlock(OccultismBlocks.SACRIFICIAL_BOWL, "Tijela sacrificial");
        this.addBlock(OccultismBlocks.GOLDEN_SACRIFICIAL_BOWL, "Tijela sacrificial dourada");
        this.addBlock(OccultismBlocks.CHALK_GLYPH_WHITE, "Glifo de giz branco");
        this.addBlock(OccultismBlocks.CHALK_GLYPH_GOLD, "Glifo de giz dourado");
        this.addBlock(OccultismBlocks.CHALK_GLYPH_PURPLE, "Glifo de giz roxo");
        this.addBlock(OccultismBlocks.CHALK_GLYPH_RED, "Glifo de giz vermelho");
        this.addBlock(OccultismBlocks.STORAGE_CONTROLLER, "Atuador de armazenamento dimensional");
        this.addBlock(OccultismBlocks.STORAGE_CONTROLLER_BASE, "Base do atuador de armazenamento");
        this.addBlock(OccultismBlocks.STORAGE_STABILIZER_TIER1, "Estabilizador do armazenamento dimensional Tier 1");
        this.addBlock(OccultismBlocks.STORAGE_STABILIZER_TIER2, "Estabilizador do armazenamento dimensional Tier 2");
        this.addBlock(OccultismBlocks.STORAGE_STABILIZER_TIER3, "Estabilizador do armazenamento dimensional Tier 3");
        this.addBlock(OccultismBlocks.STORAGE_STABILIZER_TIER4, "Estabilizador do armazenamento dimensional Tier 4");
        this.addBlock(OccultismBlocks.STABLE_WORMHOLE, "Buraco de minhoca estável");
        this.addBlock(OccultismBlocks.DATURA, "Sonho demoníaco");

        this.add("block.occultism.otherworld_log", "Tronco do Outro Mundo");
        this.add("block.occultism.otherworld_sapling", "Muda do Outro Mundo");
        this.add("block.occultism.otherworld_leaves", "Folhas do Outro Mundo");

        this.addBlock(OccultismBlocks.SPIRIT_FIRE, "Fogo místico");
        this.addBlock(OccultismBlocks.SPIRIT_ATTUNED_CRYSTAL, "Cristal mítisco sintonizado");
        this.addBlock(OccultismBlocks.CANDLE_WHITE, "Vela branca");
        this.addBlock(OccultismBlocks.SILVER_ORE, "Minério de prata");
        this.addBlock(OccultismBlocks.SILVER_ORE_DEEPSLATE, "Minério de prata de ardósia");
        this.addBlock(OccultismBlocks.IESNIUM_ORE, "Minério de Iésnio");
        this.addBlock(OccultismBlocks.SILVER_BLOCK, "Bloco de prata");
        this.addBlock(OccultismBlocks.IESNIUM_BLOCK, "Bloco de Iésnio");
        this.addBlock(OccultismBlocks.DIMENSIONAL_MINESHAFT, "Mina dimensional");
        this.addBlock(OccultismBlocks.SKELETON_SKULL_DUMMY, "Crânio de esqueleto");
        this.addBlock(OccultismBlocks.WITHER_SKELETON_SKULL_DUMMY, "Crânio de esqueleto wither");
        this.addBlock(OccultismBlocks.LIGHTED_AIR, "Ar brilhante");
        this.addBlock(OccultismBlocks.SPIRIT_LANTERN, "Lanterna mística");
        this.addBlock(OccultismBlocks.SPIRIT_CAMPFIRE, "Fogueira mística");
        this.addBlock(OccultismBlocks.SPIRIT_TORCH, "Tocha mística"); //spirit wall torch automatically uses the same translation
    }

    private void addEntities() {
        //"entity\.occultism\.(.*?)": "(.*)",
        //this.addEntityType\(OccultismEntities.\U\1\E, "\2"\);

        this.addEntityType(OccultismEntities.FOLIOT, "Foliot");
        this.addEntityType(OccultismEntities.DJINNI, "Djinni");
        this.addEntityType(OccultismEntities.AFRIT, "Afrit");
        this.addEntityType(OccultismEntities.AFRIT_WILD, "Afrit desvinculado");
        this.addEntityType(OccultismEntities.MARID, "Marid");
        this.addEntityType(OccultismEntities.POSSESSED_ENDERMITE, "Endermite possuído");
        this.addEntityType(OccultismEntities.POSSESSED_SKELETON, "Esqueleto possuído");
        this.addEntityType(OccultismEntities.POSSESSED_ENDERMAN, "Enderman possuído");
        this.addEntityType(OccultismEntities.POSSESSED_GHAST, "Ghast possuído");
        this.addEntityType(OccultismEntities.WILD_HUNT_SKELETON, "Esqueleto da Caça Selvagem");
        this.addEntityType(OccultismEntities.WILD_HUNT_WITHER_SKELETON, "Esqueleto wither da Caça Selvagem");
        this.addEntityType(OccultismEntities.OTHERWORLD_BIRD, "Drikwing");
        this.addEntityType(OccultismEntities.GREEDY_FAMILIAR, "Avarento familiar");
        this.addEntityType(OccultismEntities.BAT_FAMILIAR, "Morcego familiar");
        this.addEntityType(OccultismEntities.DEER_FAMILIAR, "Veado familiar");
        this.addEntityType(OccultismEntities.CTHULHU_FAMILIAR, "Cthulhu familiar");
        this.addEntityType(OccultismEntities.DEVIL_FAMILIAR, "Diabo familiar");
        this.addEntityType(OccultismEntities.DRAGON_FAMILIAR, "Dragão familiar");
        this.addEntityType(OccultismEntities.BLACKSMITH_FAMILIAR, "Ferreiro familiar");
        this.addEntityType(OccultismEntities.GUARDIAN_FAMILIAR, "Guardião familiar");
        this.addEntityType(OccultismEntities.HEADLESS_FAMILIAR, "Sem cabeça familiar");
        this.addEntityType(OccultismEntities.CHIMERA_FAMILIAR, "Quimera familiar");
        this.addEntityType(OccultismEntities.GOAT_FAMILIAR, "Bode familiar");
        this.addEntityType(OccultismEntities.SHUB_NIGGURATH_FAMILIAR, "Shub Niggurath familiar");
        this.addEntityType(OccultismEntities.BEHOLDER_FAMILIAR, "Beholder familiar");
        this.addEntityType(OccultismEntities.FAIRY_FAMILIAR, "Fada familiar");
        this.addEntityType(OccultismEntities.MUMMY_FAMILIAR, "Múmia familiar");
        this.addEntityType(OccultismEntities.BEAVER_FAMILIAR, "Castor familiar");
        this.addEntityType(OccultismEntities.SHUB_NIGGURATH_SPAWN, "Prole de Shub Niggurath");
        this.addEntityType(OccultismEntities.THROWN_SWORD, "Espada jogada");
    }

    private void addMiscTranslations() {

        //"(.*?)": "(.*)",
        //this.add\("\1", "\2"\);

        //Jobs
        this.add("job.occultism.lumberjack", "Lenhador");
        this.add("job.occultism.crush_tier1", "Triturador lento");
        this.add("job.occultism.crush_tier2", "Triturador");
        this.add("job.occultism.crush_tier3", "Triturador veloz");
        this.add("job.occultism.crush_tier4", "Triturador muito veloz");
        this.add("job.occultism.manage_machine", "Maquinista");
        this.add("job.occultism.transport_items", "Transportador");
        this.add("job.occultism.cleaner", "Zelador");
        this.add("job.occultism.trade_otherstone_t1", "Mercador de outra pedra");
        this.add("job.occultism.trade_otherworld_saplings_t1", "Mercador de muda do Outro Mundo");
        this.add("job.occultism.clear_weather", "Espírito da luz do sol");
        this.add("job.occultism.rain_weather", "Espírito do clima chuvoso");
        this.add("job.occultism.thunder_weather", "Espírito da tempestade");
        this.add("job.occultism.day_time", "Espírito do alvorecer");
        this.add("job.occultism.night_time", "Espírito do crepúsculo");

        //Enums
        this.add("enum.occultism.facing.up", "Cima");
        this.add("enum.occultism.facing.down", "Baixo");
        this.add("enum.occultism.facing.north", "Norte");
        this.add("enum.occultism.facing.south", "Sul");
        this.add("enum.occultism.facing.west", "Oeste");
        this.add("enum.occultism.facing.east", "Leste");
        this.add("enum.occultism.book_of_calling.item_mode.set_deposit", "Definir depósito");
        this.add("enum.occultism.book_of_calling.item_mode.set_extract", "Definir extração");
        this.add("enum.occultism.book_of_calling.item_mode.set_base", "Definir local de base");
        this.add("enum.occultism.book_of_calling.item_mode.set_storage_controller", "Definir atuador de armazenamento");
        this.add("enum.occultism.book_of_calling.item_mode.set_managed_machine", "Definir máquina gerenciada");
        this.add("enum.occultism.work_area_size.small", "16x16");
        this.add("enum.occultism.work_area_size.medium", "32x32");
        this.add("enum.occultism.work_area_size.large", "64x64");

        //Debug messages
        this.add("debug.occultism.debug_wand.printed_glyphs", "Glifos escritos");
        this.add("debug.occultism.debug_wand.glyphs_verified", "Glifos verificados");
        this.add("debug.occultism.debug_wand.glyphs_not_verified", "Glifos não verificados");
        this.add("debug.occultism.debug_wand.spirit_selected", "Selecionado espírito com id %s");
        this.add("debug.occultism.debug_wand.spirit_tamed", "Domado espírito com id %s");
        this.add("debug.occultism.debug_wand.deposit_selected", "Definido bloco de depósito %s, virado para %s");
        this.add("debug.occultism.debug_wand.no_spirit_selected", "Nenhum espírito selecionado.");

        //Ritual Sacrifices
        this.add("ritual.occultism.sacrifice.cows", "Vaca");
        this.add("ritual.occultism.sacrifice.bats", "Morcego");
        this.add("ritual.occultism.sacrifice.zombies", "Zumbi");
        this.add("ritual.occultism.sacrifice.parrots", "Papagaio");
        this.add("ritual.occultism.sacrifice.chicken", "Galinha");
        this.add("ritual.occultism.sacrifice.pigs", "Porcos");
        this.add("ritual.occultism.sacrifice.humans", "Aldeão ou Jogador");
        this.add("ritual.occultism.sacrifice.squid", "Lula");
        this.add("ritual.occultism.sacrifice.horses", "Cavalo");
        this.add("ritual.occultism.sacrifice.sheep", "Ovelha");
        this.add("ritual.occultism.sacrifice.llamas", "Lhama");
        this.add("ritual.occultism.sacrifice.snow_golem", "Golem de neve");
        this.add("ritual.occultism.sacrifice.spiders", "Aranha");

        //Network Message
        this.add("network.messages.occultism.request_order.order_received", "Ordem recebida!");

        //Effects
        this.add("effect.occultism.third_eye", "Terceiro olho");
        this.add("effect.occultism.double_jump", "Multi pulo");
        this.add("effect.occultism.dragon_greed", "Ganância do dragão");
        this.add("effect.occultism.mummy_dodge", "Esquiva");
        this.add("effect.occultism.bat_lifesteal", "Roubo vital");
        this.add("effect.occultism.beaver_harvest", "Safra do castor");

        //Sounds
        this.add("occultism.subtitle.chalk", "Giz");
        this.add("occultism.subtitle.brush", "Apaga");
        this.add("occultism.subtitle.start_ritual", "Ritual inicia");
        this.add("occultism.subtitle.tuning_fork", "Diapasão");
        this.add("occultism.subtitle.crunching", "Triturando");
        this.add("occultism.subtitle.poof", "Puff!");

    }

    private void addGuiTranslations() {
        this.add("gui.occultism.book_of_calling.mode", "Modo");
        this.add("gui.occultism.book_of_calling.work_area", "Área de trabalho");
        this.add("gui.occultism.book_of_calling.manage_machine.insert", "Lado de inserção");
        this.add("gui.occultism.book_of_calling.manage_machine.extract", "Lado de extração");
        this.add("gui.occultism.book_of_calling.manage_machine.custom_name", "Nome customizado");

        // Spirit GUI
        this.add("gui.occultism.spirit.age", "Decadência de essência: %d%%");
        this.add("gui.occultism.spirit.job", "%s");

        // Spirit Transporter GUI
        this.add("gui.occultism.spirit.transporter.filter_mode", "Modo filtro");
        this.add("gui.occultism.spirit.transporter.filter_mode.blacklist", "Lista negra");
        this.add("gui.occultism.spirit.transporter.filter_mode.whitelist", "Lista branca");
        this.add("gui.occultism.spirit.transporter.tag_filter", "Insira tags para filtrar separadamente \";\".\nE.g.: \"forge:ores;*logs*\".\nUse \"*\" para corresponder a qualquer caractere, e.g. \"*ore*\" para corresponder a tags de minério em qualquer mod. Para filtrar por itens, prefixe o id do item com \"item:\", E.g.: \"item:minecraft:chest\".");

        // Storage Controller GUI
        this.add("gui.occultism.storage_controller.space_info_label", "%d/%d");
        this.add("gui.occultism.storage_controller.shift", "Segure shift para mais informações.");
        this.add("gui.occultism.storage_controller.search.tooltip@", "Prefixo @: Busca id de mod.");
        this.add("gui.occultism.storage_controller.search.tooltip#", "Prefixo #: Busca no tooltip do item.");
        this.add("gui.occultism.storage_controller.search.tooltip$", "Prefixo $: Busca por tag.");
        this.add("gui.occultism.storage_controller.search.tooltip_rightclick", "Apague o texto com o botão direito.");
        this.add("gui.occultism.storage_controller.search.tooltip_clear", "Apagar busca.");
        this.add("gui.occultism.storage_controller.search.tooltip_jei_on", "Sincronizar busca com JEI.");
        this.add("gui.occultism.storage_controller.search.tooltip_jei_off", "Não sincronizar busca com JEI.");
        this.add("gui.occultism.storage_controller.search.tooltip_sort_type_amount", "Ordenar por quantidade.");
        this.add("gui.occultism.storage_controller.search.tooltip_sort_type_name", "Ordenar por nome do item.");
        this.add("gui.occultism.storage_controller.search.tooltip_sort_type_mod", "Ordenar por nome do mod.");
        this.add("gui.occultism.storage_controller.search.tooltip_sort_direction_down", "Ordenar ascendente.");
        this.add("gui.occultism.storage_controller.search.tooltip_sort_direction_up", "Ordenar descendente.");
        this.add("gui.occultism.storage_controller.search.machines.tooltip@", "Prefixo @: Busca id de mod.");
        this.add("gui.occultism.storage_controller.search.machines.tooltip_sort_type_amount", "Ordenar por distância.");
        this.add("gui.occultism.storage_controller.search.machines.tooltip_sort_type_name", "Ordenar por nome da máquina.");
        this.add("gui.occultism.storage_controller.search.machines.tooltip_sort_type_mod", "Ordenar por nome do mod.");

    }

    private void addRitualMessages() {
        this.add("ritual.occultism.pentacle_help", "\u00a7lPentáculo inválido!\u00a7r\nEstava tentando criar o pentáculo: %s? Faltando:\n%s");
        this.add("ritual.occultism.pentacle_help_at_glue", " na posição ");
        this.add("ritual.occultism.ritual_help", "\u00a7lRitual inválido!\u00a7r\nEstava tentando realizar o ritual: \"%s\"? Itens faltando:\n%s");
        this.add("ritual.occultism.disabled", "Este ritual está desabilitado neste servidor..");
        this.add("ritual.occultism.does_not_exist", "\u00a7lRitual desconhecido\u00a7r. Garanta que o pentáculo e os ingredientes estejam configurados corretamente. Se você ainda não tiver sucesso, junte-se ao nosso discord https://invite.gg/klikli");
        this.add("ritual.occultism.book_not_bound", "\u00a7lLivro de Chamado desvinculado\u00a7r. Você deve juntar este livro com o Dicionário de Espíritos para ligá-lo a um espírito antes de iniciar um ritual.");
        this.add("ritual.occultism.debug.conditions", "Nem todos os requisitos para este ritual foram atendidos.");
        this.add("ritual.occultism.debug.started", "Ritual iniciado.");
        this.add("ritual.occultism.debug.finished", "Ritual completado com sucesso.");
        this.add("ritual.occultism.debug.interrupted", "Ritual interrompido.");
        this.add("ritual.occultism.summon_foliot_lumberjack.conditions", "Nem todos os requisitos para este ritual foram atendidos.");
        this.add("ritual.occultism.summon_foliot_lumberjack.started", "Começou a invocar foliot lenhador.");
        this.add("ritual.occultism.summon_foliot_lumberjack.finished", "Foliot lenhador invocado com sucesso.");
        this.add("ritual.occultism.summon_foliot_lumberjack.interrupted", "Invocação de foliot lenhador interrompida.");
        this.add("ritual.occultism.summon_foliot_transport_items.conditions", "Nem todos os requisitos para este ritual foram atendidos.");
        this.add("ritual.occultism.summon_foliot_transport_items.started", "Começou a invocar foliot transportador.");
        this.add("ritual.occultism.summon_foliot_transport_items.finished", "Foliot transportador invocado com sucesso");
        this.add("ritual.occultism.summon_foliot_transport_items.interrupted", "Invocação de Foliot transportador interrompida.");
        this.add("ritual.occultism.summon_foliot_cleaner.conditions", "Nem todos os requisitos para este ritual foram atendidos.");
        this.add("ritual.occultism.summon_foliot_cleaner.started", "Começou a invocar foliot zelador.");
        this.add("ritual.occultism.summon_foliot_cleaner.finished", "Foliot zelador invocado com sucesso");
        this.add("ritual.occultism.summon_foliot_cleaner.interrupted", "Invocação de foliot zelador interrompida.");
        this.add("ritual.occultism.summon_foliot_crusher.conditions", "Nem todos os requisitos para este ritual foram atendidos.");
        this.add("ritual.occultism.summon_foliot_crusher.started", "Começou a invocar foliot triturador.");
        this.add("ritual.occultism.summon_foliot_crusher.finished", "Foliot triturador invocado com sucesso");
        this.add("ritual.occultism.summon_foliot_crusher.interrupted", "Invocação de foliot triturador interrompida.");
        this.add("ritual.occultism.summon_djinni_crusher.conditions", "Nem todos os requisitos para este ritual foram atendidos.");
        this.add("ritual.occultism.summon_djinni_crusher.started", "Começou a invocar djinni triturador.");
        this.add("ritual.occultism.summon_djinni_crusher.finished", "Djinni triturador invocado com sucesso");
        this.add("ritual.occultism.summon_djinni_crusher.interrupted", "Invocação de djinni triturador interrompida.");
        this.add("ritual.occultism.summon_afrit_crusher.conditions", "Nem todos os requisitos para este ritual foram atendidos.");
        this.add("ritual.occultism.summon_afrit_crusher.started", "Começou a invocar afrit triturador.");
        this.add("ritual.occultism.summon_afrit_crusher.finished", "Afrit triturador invocado com sucesso");
        this.add("ritual.occultism.summon_afrit_crusher.interrupted", "Invocação de afrit triturador interrompida.");
        this.add("ritual.occultism.summon_marid_crusher.conditions", "Nem todos os requisitos para este ritual foram atendidos.");
        this.add("ritual.occultism.summon_marid_crusher.started", "Começou a invocar marid triturador.");
        this.add("ritual.occultism.summon_marid_crusher.finished", "Marid triturador invocado com sucesso");
        this.add("ritual.occultism.summon_marid_crusher.interrupted", "Invocação de marid triturador interrompida.");
        this.add("ritual.occultism.summon_foliot_sapling_trader.conditions", "Nem todos os requisitos para este ritual foram atendidos.");
        this.add("ritual.occultism.summon_foliot_sapling_trader.started", "Começou a invocar foliot mercador de muda do Outro Mundo.");
        this.add("ritual.occultism.summon_foliot_sapling_trader.finished", "Foliot mercador de muda do Outro Mundo invocado com sucesso.");
        this.add("ritual.occultism.summon_foliot_sapling_trader.interrupted", "Invocação de foliot mercador de muda do Outro Mundo interrompida.");
        this.add("ritual.occultism.summon_foliot_otherstone_trader.conditions", "Nem todos os requisitos para este ritual foram atendidos.");
        this.add("ritual.occultism.summon_foliot_otherstone_trader.started", "Começou a invocar foliot mercador de outra pedra.");
        this.add("ritual.occultism.summon_foliot_otherstone_trader.finished", "Foliot mercador de outra pedra invocado com sucesso.");
        this.add("ritual.occultism.summon_foliot_otherstone_trader.interrupted", "Invocação de foliot mercador de outra pedra interrompida.");
        this.add("ritual.occultism.summon_djinni_manage_machine.conditions", "Nem todos os requisitos para este ritual foram atendidos.");
        this.add("ritual.occultism.summon_djinni_manage_machine.started", "Começou a invocar djinni maquinista.");
        this.add("ritual.occultism.summon_djinni_manage_machine.finished", "Djinni maquinista invocado com sucesso.");
        this.add("ritual.occultism.summon_djinni_manage_machine.interrupted", "Invocação de djinni maquinista interrompida.");
        this.add("ritual.occultism.summon_djinni_clear_weather.conditions", "Nem todos os requisitos para este ritual foram atendidos.");
        this.add("ritual.occultism.summon_djinni_clear_weather.started", "Começou a invocar djinni para limpar o tempo.");
        this.add("ritual.occultism.summon_djinni_clear_weather.finished", "Djinni invocado com sucesso.");
        this.add("ritual.occultism.summon_djinni_clear_weather.interrupted", "Invocação de djinni interrompida.");
        this.add("ritual.occultism.summon_djinni_day_time.conditions", "Nem todos os requisitos para este ritual foram atendidos.");
        this.add("ritual.occultism.summon_djinni_day_time.started", "Começou a invocar djinni para deixar de dia.");
        this.add("ritual.occultism.summon_djinni_day_time.finished", "Djinni invocado com sucesso.");
        this.add("ritual.occultism.summon_djinni_day_time.interrupted", "Invocação de djinni interrompida.");
        this.add("ritual.occultism.summon_djinni_night_time.conditions", "Nem todos os requisitos para este ritual foram atendidos.");
        this.add("ritual.occultism.summon_djinni_night_time.started", "Começou a invocar djinni para deixar de noite.");
        this.add("ritual.occultism.summon_djinni_night_time.finished", "Djinni invocado com sucesso.");
        this.add("ritual.occultism.summon_djinni_night_time.interrupted", "Invocação de djinni interrompida.");
        this.add("ritual.occultism.summon_afrit_rain_weather.conditions", "Nem todos os requisitos para este ritual foram atendidos.");
        this.add("ritual.occultism.summon_afrit_rain_weather.started", "Começou a invocar afrit para tempo chuvoso.");
        this.add("ritual.occultism.summon_afrit_rain_weather.finished", "Afrit invocado com sucesso.");
        this.add("ritual.occultism.summon_afrit_rain_weather.interrupted", "Invocação de afrit interrompida.");
        this.add("ritual.occultism.summon_afrit_thunder_weather.conditions", "Nem todos os requisitos para este ritual foram atendidos.");
        this.add("ritual.occultism.summon_afrit_thunder_weather.started", "Começou a invocar afrit para uma tempestade.");
        this.add("ritual.occultism.summon_afrit_thunder_weather.finished", "Afrit invocado com sucesso.");
        this.add("ritual.occultism.summon_afrit_thunder_weather.interrupted", "Invocação de afrit interrompida.");
        this.add("ritual.occultism.summon_wild_afrit.conditions", "Nem todos os requisitos para este ritual foram atendidos.");
        this.add("ritual.occultism.summon_wild_afrit.started", "Começou a invocar afrit desvinculado.");
        this.add("ritual.occultism.summon_wild_afrit.finished", "Afrit desvinculado invocado com sucesso.");
        this.add("ritual.occultism.summon_wild_afrit.interrupted", "Invocação de afrit desvinculado interrompida.");
        this.add("ritual.occultism.summon_wild_hunt.conditions", "Nem todos os requisitos para este ritual foram atendidos.");
        this.add("ritual.occultism.summon_wild_hunt.started", "Começou a invocar a Caça Selvagem.");
        this.add("ritual.occultism.summon_wild_hunt.finished", "A Caça Selvagem foi invocada com sucesso.");
        this.add("ritual.occultism.summon_wild_hunt.interrupted", "Invocação da Caça Selvagem interrompida.");
        this.add("ritual.occultism.craft_dimensional_matrix.conditions", "Nem todos os requisitos para este ritual foram atendidos.");
        this.add("ritual.occultism.craft_dimensional_matrix.started", "Começou a vincular djinni à matriz dimensional.");
        this.add("ritual.occultism.craft_dimensional_matrix.finished", "Djinni vinculado à matriz dimensional com sucesso.");
        this.add("ritual.occultism.craft_dimensional_matrix.interrupted", "Vinculação de djinni interrompida.");
        this.add("ritual.occultism.craft_dimensional_mineshaft.conditions", "Nem todos os requisitos para este ritual foram atendidos.");
        this.add("ritual.occultism.craft_dimensional_mineshaft.started", "Começou a vincular djinni à mina dimensional.");
        this.add("ritual.occultism.craft_dimensional_mineshaft.finished", "Djinni vinculado à mina dimensional com sucesso.");
        this.add("ritual.occultism.craft_dimensional_mineshaft.interrupted", "Vinculação de djinni interrompida.");
        this.add("ritual.occultism.craft_storage_controller_base.conditions", "Nem todos os requisitos para este ritual foram atendidos.");
        this.add("ritual.occultism.craft_storage_controller_base.started", "Começou a vincular foliot à base do atuador de armazenamento.");
        this.add("ritual.occultism.craft_storage_controller_base.finished", "Foliot vinculado à base do atuador de armazenamento com sucesso.");
        this.add("ritual.occultism.craft_storage_controller_base.interrupted", "Vinculação de foliot interrompida.");
        this.add("ritual.occultism.craft_stabilizer_tier1.conditions", "Nem todos os requisitos para este ritual foram atendidos.");
        this.add("ritual.occultism.craft_stabilizer_tier1.started", "Começou a vincular foliot ao estabilizador de armazenamento.");
        this.add("ritual.occultism.craft_stabilizer_tier1.finished", "Foliot vinculado ao estabilizador de armazenamento com sucesso.");
        this.add("ritual.occultism.craft_stabilizer_tier1.interrupted", "Vinculação de foliot interrompida.");
        this.add("ritual.occultism.craft_stabilizer_tier2.conditions", "Nem todos os requisitos para este ritual foram atendidos.");
        this.add("ritual.occultism.craft_stabilizer_tier2.started", "Começou a vincular djinni ao estabilizador de armazenamento.");
        this.add("ritual.occultism.craft_stabilizer_tier2.finished", "Djinni vinculado ao estabilizador de armazenamento com sucesso.");
        this.add("ritual.occultism.craft_stabilizer_tier2.interrupted", "Vinculação de djinni interrompida.");
        this.add("ritual.occultism.craft_stabilizer_tier3.conditions", "Nem todos os requisitos para este ritual foram atendidos.");
        this.add("ritual.occultism.craft_stabilizer_tier3.started", "Começou a vincular afrit ao estabilizador de armazenamento.");
        this.add("ritual.occultism.craft_stabilizer_tier3.finished", "Afrit vinculado ao estabilizador de armazenamento com sucesso.");
        this.add("ritual.occultism.craft_stabilizer_tier3.interrupted", "Vinculação de afrit interrompida.");
        this.add("ritual.occultism.craft_stabilizer_tier4.conditions", "Nem todos os requisitos para este ritual foram atendidos.");
        this.add("ritual.occultism.craft_stabilizer_tier4.started", "Começou a vincular marid ao estabilizador de armazenamento.");
        this.add("ritual.occultism.craft_stabilizer_tier4.finished", "Marid vinculado ao estabilizador de armazenamento com sucesso.");
        this.add("ritual.occultism.craft_stabilizer_tier4.interrupted", "Vinculação de marid interrompida.");
        this.add("ritual.occultism.craft_stable_wormhole.conditions", "Nem todos os requisitos para este ritual foram atendidos.");
        this.add("ritual.occultism.craft_stable_wormhole.started", "Começou a vincular foliot à moldura do buraco de minhoca.");
        this.add("ritual.occultism.craft_stable_wormhole.finished", "Foliot vinculado à moldura do buraco de minhoca com sucesso.");
        this.add("ritual.occultism.craft_stable_wormhole.interrupted", "Vinculação de foliot interrompida.");
        this.add("ritual.occultism.craft_storage_remote.conditions", "Nem todos os requisitos para este ritual foram atendidos.");
        this.add("ritual.occultism.craft_storage_remote.started", "Começou a vincular djinni ao controle de armazenamento.");
        this.add("ritual.occultism.craft_storage_remote.finished", "Djinni vinculado ao controle de armazenamento com sucesso.");
        this.add("ritual.occultism.craft_storage_remote.interrupted", "Vinculação de djinni interrompida.");
        this.add("ritual.occultism.craft_infused_lenses.conditions", "Nem todos os requisitos para este ritual foram atendidos.");
        this.add("ritual.occultism.craft_infused_lenses.started", "Começou a vincular foliot às lentes.");
        this.add("ritual.occultism.craft_infused_lenses.finished", "Foliot vinculado às lentes com sucesso.");
        this.add("ritual.occultism.craft_infused_lenses.interrupted", "Vinculação de foliot interrompida.");
        this.add("ritual.occultism.craft_infused_pickaxe.conditions", "Nem todos os requisitos para este ritual foram atendidos.");
        this.add("ritual.occultism.craft_infused_pickaxe.started", "Começou a vincular djinni à picareta.");
        this.add("ritual.occultism.craft_infused_pickaxe.finished", "Djinni vinculado à picareta com sucesso.");
        this.add("ritual.occultism.craft_infused_pickaxe.interrupted", "Vinculação de djinni interrompida.");
        this.add("ritual.occultism.craft_miner_foliot_unspecialized.conditions", "Nem todos os requisitos para este ritual foram atendidos.");
        this.add("ritual.occultism.craft_miner_foliot_unspecialized.started", "Começou a invocar foliot na lâmpada mágica.");
        this.add("ritual.occultism.craft_miner_foliot_unspecialized.finished", "Foliot invocado na lâmpada mágica com sucesso.");
        this.add("ritual.occultism.craft_miner_foliot_unspecialized.interrupted", "Invocação de foliot interrompida.");
        this.add("ritual.occultism.craft_miner_djinni_ores.conditions", "Nem todos os requisitos para este ritual foram atendidos.");
        this.add("ritual.occultism.craft_miner_djinni_ores.started", "Começou a invocar djinni na lâmpada mágica.");
        this.add("ritual.occultism.craft_miner_djinni_ores.finished", "Djinni invocado na lâmpada mágica com sucesso.");
        this.add("ritual.occultism.craft_miner_djinni_ores.interrupted", "Invocação de djinni interrompida.");
        this.add("ritual.occultism.craft_satchel.conditions", "Nem todos os requisitos para este ritual foram atendidos.");
        this.add("ritual.occultism.craft_satchel.started", "Começou a vincular foliot à bolsa.");
        this.add("ritual.occultism.craft_satchel.finished", "Foliot vinculado à bolsa com sucesso.");
        this.add("ritual.occultism.craft_satchel.interrupted", "Vinculação de foliot interrompida.");
        this.add("ritual.occultism.craft_soul_gem.conditions", "Nem todos os requisitos para este ritual foram atendidos.");
        this.add("ritual.occultism.craft_soul_gem.started", "Começou a vincular djinni à gema da alma.");
        this.add("ritual.occultism.craft_soul_gem.finished", "Djinni vinculado à gema da alma com sucesso.");
        this.add("ritual.occultism.craft_soul_gem.interrupted", "Vinculação de djinni interrompida.");
        this.add("ritual.occultism.craft_familiar_ring.conditions", "Nem todos os requisitos para este ritual foram atendidos.");
        this.add("ritual.occultism.craft_familiar_ring.started", "Começou a vincular djinni ao anel familiar.");
        this.add("ritual.occultism.craft_familiar_ring.finished", "Djinni vinculado ao anel familiar com sucesso.");
        this.add("ritual.occultism.craft_familiar_ring.interrupted", "Vinculação de djinni interrompida.");
        this.add("ritual.occultism.possess_endermite.conditions", "Nem todos os requisitos para este ritual foram atendidos.");
        this.add("ritual.occultism.possess_endermite.started", "Começou a invocar endermite possuído.");
        this.add("ritual.occultism.possess_endermite.finished", "Endermite possuído invocado com sucesso.");
        this.add("ritual.occultism.possess_endermite.interrupted", "Invocação de endermite possuído interrompida.");
        this.add("ritual.occultism.possess_skeleton.conditions", "Nem todos os requisitos para este ritual foram atendidos.");
        this.add("ritual.occultism.possess_skeleton.started", "Começou a invocar esqueleto possuído.");
        this.add("ritual.occultism.possess_skeleton.finished", "Esqueleto possuído invocado com sucesso.");
        this.add("ritual.occultism.possess_skeleton.interrupted", "Invocação de esqueleto possuído interrompida.");
        this.add("ritual.occultism.possess_enderman.conditions", "Nem todos os requisitos para este ritual foram atendidos.");
        this.add("ritual.occultism.possess_enderman.started", "Começou a invocar enderman possuído.");
        this.add("ritual.occultism.possess_enderman.finished", "Enderman possuído invocado com sucesso.");
        this.add("ritual.occultism.possess_enderman.interrupted", "Invocação de enderman possuído interrompida.");
        this.add("ritual.occultism.possess_ghast.conditions", "Nem todos os requisitos para este ritual foram atendidos.");
        this.add("ritual.occultism.possess_ghast.started", "Começou a invocar ghast possuído.");
        this.add("ritual.occultism.possess_ghast.finished", "Ghast possuído invocado com successo.");
        this.add("ritual.occultism.possess_ghast.interrupted", "Invocação de ghast possuído interrompida.");
        this.add("ritual.occultism.familiar_otherworld_bird.conditions", "Nem todos os requisitos para este ritual foram atendidos.");
        this.add("ritual.occultism.familiar_otherworld_bird.started", "Começou a invocar drikwing familiar.");
        this.add("ritual.occultism.familiar_otherworld_bird.finished", "Drikwing familiar invocado com sucesso.");
        this.add("ritual.occultism.familiar_otherworld_bird.interrupted", "Invocação de drikwing familiar interrompida.");
        this.add("ritual.occultism.familiar_cthulhu.conditions", "Nem todos os requisitos para este ritual foram atendidos.");
        this.add("ritual.occultism.familiar_cthulhu.started", "Começou a invocar Cthulhu familiar.");
        this.add("ritual.occultism.familiar_cthulhu.finished", "Cthulhu familiar invocado com sucesso.");
        this.add("ritual.occultism.familiar_cthulhu.interrupted", "Invocação de cthulhu familiar interrompida.");
        this.add("ritual.occultism.familiar_devil.conditions", "Nem todos os requisitos para este ritual foram atendidos.");
        this.add("ritual.occultism.familiar_devil.started", "Começou a invocar diabo familiar.");
        this.add("ritual.occultism.familiar_devil.finished", "Diabo familiar invocado com sucesso.");
        this.add("ritual.occultism.familiar_devil.interrupted", "Invocação de diabo familiar interrompida.");
        this.add("ritual.occultism.familiar_dragon.conditions", "Nem todos os requisitos para este ritual foram atendidos.");
        this.add("ritual.occultism.familiar_dragon.started", "Começou a invocar dragão familiar.");
        this.add("ritual.occultism.familiar_dragon.finished", "Dragão familiar invocado com sucesso.");
        this.add("ritual.occultism.familiar_dragon.interrupted", "Invocação de dragão familiar interrompida.");
        this.add("ritual.occultism.familiar_blacksmith.conditions", "Nem todos os requisitos para este ritual foram atendidos.");
        this.add("ritual.occultism.familiar_blacksmith.started", "Começou a invocar ferreiro familiar.");
        this.add("ritual.occultism.familiar_blacksmith.finished", "Ferreiro familiar invocado com sucesso.");
        this.add("ritual.occultism.familiar_blacksmith.interrupted", "Invocação de ferreiro familiar interrompida.");
        this.add("ritual.occultism.familiar_guardian.conditions", "Nem todos os requisitos para este ritual foram atendidos.");
        this.add("ritual.occultism.familiar_guardian.started", "Começou a invocar guardião familiar.");
        this.add("ritual.occultism.familiar_guardian.finished", "Guardião familiar invocado com sucesso.");
        this.add("ritual.occultism.familiar_guardian.interrupted", "Invocação de guardião familiar interrompida.");
        this.add("ritual.occultism.summon_wild_otherworld_bird.conditions", "Nem todos os requisitos para este ritual foram atendidos.");
        this.add("ritual.occultism.summon_wild_otherworld_bird.started", "Começou a invocar drikwing selvagem.");
        this.add("ritual.occultism.summon_wild_otherworld_bird.finished", "Drikwing selvagem invocado com sucesso.");
        this.add("ritual.occultism.summon_wild_otherworld_bird.interrupted", "Invocação de drikwing selvagem interrompida.");
        this.add("ritual.occultism.summon_wild_parrot.conditions", "Nem todos os requisitos para este ritual foram atendidos.");
        this.add("ritual.occultism.summon_wild_parrot.started", "Começou a invocar papagaio selvagem.");
        this.add("ritual.occultism.summon_wild_parrot.finished", "Papagaio selvagem invocado com sucesso.");
        this.add("ritual.occultism.summon_wild_parrot.interrupted", "Invocação de papagaio selvagem interrompida.");
        this.add("ritual.occultism.familiar_parrot.conditions", "Nem todos os requisitos para este ritual foram atendidos.");
        this.add("ritual.occultism.familiar_parrot.started", "Começou a invocar papagaio familiar.");
        this.add("ritual.occultism.familiar_parrot.finished", "Papagaio familiar invocado com sucesso.");
        this.add("ritual.occultism.familiar_parrot.interrupted", "Invocação de papagaio familiar interrompida.");
        this.add("ritual.occultism.familiar_greedy.conditions", "Nem todos os requisitos para este ritual foram atendidos.");
        this.add("ritual.occultism.familiar_greedy.started", "Começou a invocar avarento familiar.");
        this.add("ritual.occultism.familiar_greedy.finished", "Avarento familiar invocado com sucesso.");
        this.add("ritual.occultism.familiar_greedy.interrupted", "Invocação de avarento familiar interrompida.");
        this.add("ritual.occultism.familiar_bat.conditions", "Nem todos os requisitos para este ritual foram atendidos.");
        this.add("ritual.occultism.familiar_bat.started", "Começou a invocar morcego familiar.");
        this.add("ritual.occultism.familiar_bat.finished", "Morcego familiar invocado com sucesso.");
        this.add("ritual.occultism.familiar_bat.interrupted", "Invocação de morcego familiar interrompida.");
        this.add("ritual.occultism.familiar_deer.conditions", "Nem todos os requisitos para este ritual foram atendidos.");
        this.add("ritual.occultism.familiar_deer.started", "Começou a invocar veado familiar.");
        this.add("ritual.occultism.familiar_deer.finished", "Veado familiar invocado com sucesso.");
        this.add("ritual.occultism.familiar_deer.interrupted", "Invocação de veado familiar interrompida.");
        this.add("ritual.occultism.familiar_headless.conditions", "Nem todos os requisitos para este ritual foram atendidos.");
        this.add("ritual.occultism.familiar_headless.started", "Começou a invocar homem-rato sem cabeça familiar.");
        this.add("ritual.occultism.familiar_headless.finished", "Homem-rato sem cabeça familiar invocado com sucesso.");
        this.add("ritual.occultism.familiar_headless.interrupted", "Invocação de homem-rato sem cabeça familiar interrompida.");
        this.add("ritual.occultism.familiar_chimera.conditions", "Nem todos os requisitos para este ritual foram atendidos.");
        this.add("ritual.occultism.familiar_chimera.started", "Começou a invocar quimera familiar.");
        this.add("ritual.occultism.familiar_chimera.finished", "Quimera familiar invocada com sucesso.");
        this.add("ritual.occultism.familiar_chimera.interrupted", "Invocação de quimera familiar interrompida.");
        this.add("ritual.occultism.familiar_beholder.conditions", "Nem todos os requisitos para este ritual foram atendidos.");
        this.add("ritual.occultism.familiar_beholder.started", "Começou a invocar beholder familiar.");
        this.add("ritual.occultism.familiar_beholder.finished", "Beholder familiar invocado com sucesso.");
        this.add("ritual.occultism.familiar_beholder.interrupted", "Invocação de beholder familiar interrompida.");
        this.add("ritual.occultism.familiar_fairy.conditions", "Nem todos os requisitos para este ritual foram atendidos.");
        this.add("ritual.occultism.familiar_fairy.started", "Começou a invocar fada familiar.");
        this.add("ritual.occultism.familiar_fairy.finished", "Fada familiar invocada com sucesso.");
        this.add("ritual.occultism.familiar_fairy.interrupted", "Invocação de fada familiar interrompida.");
        this.add("ritual.occultism.familiar_mummy.conditions", "Nem todos os requisitos para este ritual foram atendidos.");
        this.add("ritual.occultism.familiar_mummy.started", "Começou a invocar múmia familiar.");
        this.add("ritual.occultism.familiar_mummy.finished", "Múmia familiar invocada com sucesso.");
        this.add("ritual.occultism.familiar_mummy.interrupted", "Invocação de múmia familiar interrompida.");
        this.add("ritual.occultism.familiar_beaver.conditions", "Nem todos os requisitos para este ritual foram atendidos.");
        this.add("ritual.occultism.familiar_beaver.started", "Começou a invocar castor familiar.");
        this.add("ritual.occultism.familiar_beaver.finished", "Castor familiar invocado com sucesso.");
        this.add("ritual.occultism.familiar_beaver.interrupted", "Invocação de castor familiar interrompida.");
    }

    private void addBook() {
        var helper = ModonomiconAPI.get().getLangHelper(Occultism.MODID);
        helper.book("dictionary_of_spirits");
        this.add(helper.bookName(), "Dicionário de espíritos");
        this.add(helper.bookTooltip(), """
                Este livro tem como objetivo apresentar ao leitor iniciante os rituais de invocação mais comuns e equipá-los com uma lista de espíritos e seus nomes.
                      Os autores aconselham cautela na convocação das entidades listadas.
                      Para obter ajuda ou dar feedback, junte-se a nós no Discord https://invite.gg/klikli.
                      """);

        this.addGettingStartedCategory(helper);
        this.addPentaclesCategory(helper);
        this.addRitualsCategory(helper);
        this.addAdvancedCategory(helper);
    }

    private void addGettingStartedCategory(BookLangHelper helper) {
        helper.category("getting_started");
        this.add(helper.categoryName(), "Começando");

        helper.entry("intro");
        this.add(helper.entryName(), "Aviso!");
        this.add(helper.entryDescription(), "Sobre o uso do Dicionário de Espíritos");

        helper.page("intro");
        this.add(helper.pageTitle(), "Informação importante");
        this.add(helper.pageText(),
                """
                        Occultism está em transição do Patchouli para o Modonomicon como forma de documentação do jogo.\\
                              \\
                              Atualmente, apenas a seção "Começando" está disponível no Modonomicon,
                              para todos os outros conteúdos, você precisa consultar o livro do Patchouli
                              intitulado "Dicionário de Espíritos (Edição Antiga)". 
                                    
                                    """);

        helper.page("intro2");
        this.add(helper.pageText(),
                """
                        Por enquanto, para procurar receitas, formas de pentagramas, informações sobre rituais e basicamente tudo depois de "começando",
                              abra a Edição Antiga.\\
                              \\
                              Com o tempo, cada vez mais conteúdo estará disponível diretamente aqui na versão Modonomicon.
                                    """);

        helper.page("recipe");
        this.add(helper.pageText(),
                """
                        Para obter a edição antiga, basta colocar este livro sozinho em uma grade de fabricação. (A nova edição do livro não será destruída.)
                                    """);

        helper.entry("demons_dream");
        this.add(helper.entryName(), "Levantando o Véu");
        this.add(helper.entryDescription(), "Aprenda sobre o Outro Mundo e o terceiro olho.");

        helper.page("intro");
        this.add(helper.pageTitle(), "O Outro Mundo");
        this.add(helper.pageText(),
                """
                        Escondido de meros olhos humanos existe outro plano de existência, outra *dimensão* como dizem, o [#](%1$s)Outro Mundo[#]().
                        Este mundo é preenchido com entidades geralmente chamadas de [#](%1$s)Demônios[#]().
                                    """.formatted(COLOR_PURPLE));

        helper.page("intro2");
        this.add(helper.pageText(),
                """
                        Esses Demônios possuem uma grande variedade de poderes e habilidades úteis, e por séculos os magos buscaram invocá-los ao seu favor.
                        O primeiro passo na jornada para invocar tal Entidade com sucesso é aprender a interagir com o Outro Mundo.
                                    """);

        helper.page("spotlight");
        this.add(helper.pageText(),
                """
                        %1$s é uma erva que dá aos humanos o [#](%2$s)Terceiro Olho[#](),
                              permitindo que eles vejam onde o [#](%2$s)Outro Mundo[#]() se cruza com o nosso.
                              As sementes podem ser encontradas **quebrando grama**.
                              **Consumir** a fruta ativa a habilidade.
                                    """.formatted(DEMONS_DREAM, COLOR_PURPLE));

        helper.page("harvest_effect");
        this.add(helper.pageText(),
                """
                        Um efeito colateral adicional de %1$s é **a capacidade de interagir com materiais do [#](%2$s)Outro Mundo[#]()**.
                              Isso é exclusivo de %1$s, outras formas de obter [#](%2$s)Terceiro Olho[#]() não produzem essa habilidade.
                              Enquanto estiver sob o efeito de %1$s, você pode **colher** outra pedra, bem como árvores do Outro Mundo.
                                     """.formatted(DEMONS_DREAM, COLOR_PURPLE));


        helper.page("datura_screenshot"); //no text


        helper.entry("spirit_fire");
        this.add(helper.entryName(), "Isso queima!");
        this.add(helper.entryDescription(), "Ou não?");

        helper.page("spotlight");
        this.add(helper.pageText(),
                """
                        [#](%1$s)Fogo místico[#]() é um tipo especial de fogo que existe principalmente no [#](%1$s)Outro Lugar[#]()
                              e não prejudica os seres vivos. Suas propriedades especiais permitem usá-lo para purificar e converter
                              certos materiais queimando-os, sem consumi-los.
                                    """.formatted(COLOR_PURPLE));


        helper.page("spirit_fire_screenshot");
        this.add(helper.pageText(),
                """
                        Largue [](item://occultism:datura) no chão e o incendeie com [](item://minecraft:flint_and_steel).
                         """);


        helper.page("main_uses");
        this.add(helper.pageText(),
                """
                        Os principais usos de [](item://occultism:spirit_fire) é converter [](item://minecraft:diamond) em [](item://occultism:spirit_attuned_gem),
                                 para obter ingredientes básicos como [](item://occultism:otherstone) e [Mudas do Outro Mundo](item://occultism:otherworld_sapling_natural),
                                 e purificar giz impuro.
                                     """);

        helper.page("otherstone_recipe");
        this.add(helper.pageText(),
                """
                        Uma maneira mais fácil de obter [](item://occultism:otherstone) do que via adivinhação.
                                          """);


        helper.page("otherworld_sapling_natural_recipe");
        this.add(helper.pageText(),
                """
                        Uma maneira mais fácil de obter [Mudas do Outro Mundo](item://occultism:otherworld_sapling_natural) do que via adivinhação.
                              """);

        helper.entry("third_eye");
        this.add(helper.entryName(), "O Terceiro Olho");
        this.add(helper.entryDescription(), "Você vê agora?");

        helper.page("about");
        this.add(helper.pageTitle(), "Terceiro Olho");
        this.add(helper.pageText(),
                """
                        A capacidade de ver além do mundo físico é chamada de [#](%1$s)Terceiro Olho[#]().
                              Os humanos não possuem essa capacidade de ver [#](%1$s)além do véu[#](),
                              no entanto, com certas substâncias e engenhocas, um invocador experiente pode contornar essa limitação.
                                     """.formatted(COLOR_PURPLE));

        helper.page("how_to_obtain");
        this.add(helper.pageText(),
                """
                        A maneira mais confortável e mais *cara* de obter essa habilidade é usar um óculos
                              infundido com espíritos, que *emprestam* sua visão ao usuário.
                              Uma alternativa um pouco mais nauseante, mas **muito acessível** é o consumo de certas ervas, sendo
                              [%1$s](entry://occultism:dictionary_of_spirits/getting_started/demons_dream) a mais proeminente entre elas.
                                     """.formatted(DEMONS_DREAM));

        helper.page("otherworld_goggles");
        this.add(helper.pageText(),
                """
                        [Esses óculos](entry://crafting_rituals/craft_otherworld_goggles) permitem ver ainda mais blocos ocultos do Outro Mundo,
                                 porém não permitem a colheita desses materiais.
                                 Materiais de nível baixo podem ser colhidos consumindo [%1$s](entry://occultism:dictionary_of_spirits/getting_started/demons_dream),
                                 mas materiais mais valiosos requerem ferramentas especiais.
                                            """.formatted(DEMONS_DREAM));


        helper.entry("divination_rod");
        this.add(helper.entryName(), "Vara de Adivinhação");
        this.add(helper.entryDescription(), "Obtendo materiais do Outro Mundo");

        helper.page("intro");
        this.add(helper.pageTitle(), "Adivinhação");
        this.add(helper.pageText(),
                """
                        Para facilitar o início, os materiais obtidos por adivinhação agora também têm receitas de fabricação.
                              **Se você quiser a experiência completa, pule a página de receitas a seguir e vá para a
                              [instruções de adivinhação](entry://occultism:dictionary_of_spirits/getting_started/divination_rod@divination_instructions).**
                                            """);


        helper.page("otherstone_recipe");
        //no text

        helper.page("otherworld_sapling_natural_recipe");
        this.add(helper.pageText(),
                """
                        **Aviso**: a árvore que cresce da muda parecerá um carvalho normal.
                              Você precisa ativar o [Terceiro Olho](entry://occultism:dictionary_of_spirits/getting_started/demons_dream)
                              para colher os Troncos e Folhas do Outro Mundo.
                                            """);

        helper.page("divination_rod");
        this.add(helper.pageText(),
                """
                        Os materiais do Outro Mundo desempenham um papel importante na interação com os espíritos.
                              Como são raros e invisíveis a olho nu, encontrá-los requer ferramentas especiais.
                              A vara de adivinhação permite encontrar materiais do Outro Mundo com base em suas semelhanças com materiais comuns ao nosso mundo.
                                             """);

        helper.page("about_divination_rod");
        this.add(helper.pageText(),
                """
                        A vara de adivinhação usa uma gema mística sintonizada presa a uma vara de madeira.
                              A gema ressoa com o material escolhido, e esse movimento é amplificado pela haste de madeira,
                              permitindo detectar materiais do Outro Mundo próximos. \s
                                 \s
                                 \s
                              A haste detecta a ressonância entre os materiais do mundo real e do Outro Mundo.
                              Sintonize o bastão com um material do mundo real e ele encontrará o bloco do Outro Mundo correspondente.
                                             """);

        helper.page("how_to_use");
        this.add(helper.pageTitle(), "Uso da Vara");
        this.add(helper.pageText(),
                """
                        Shift+clique direito em um bloco para sintonizar a vara com o bloco do Outro Mundo correspondente.
                        - [](item://minecraft:andesite): [](item://occultism:otherstone)
                        - [](item://minecraft:oak_wood):  [](item://occultism:otherworld_log)
                        - [](item://minecraft:oak_leaves): [](item://occultism:otherworld_leaves)
                        - [](item://minecraft:netherrack): [](item://occultism:iesnium_ore)

                        Então clique direito e segure até que a animação da vara termine.""");

        helper.page("how_to_use2");
        this.add(helper.pageText(),
                """
                        Depois que a animação terminar, o bloco **mais próximo encontrado será destacado
                              com linhas brancas e pode ser visto através de outros blocos**.
                              Além disso, você pode observar o cristal para obter dicas: um cristal branco indica que nenhum bloco de destino foi encontrado,
                              um bloco totalmente roxo significa que o bloco encontrado está próximo. Misturas entre branco e roxo mostram
                              que o alvo está longe.""");

        helper.page("divination_rod_screenshots");
        this.add(helper.pageText(),
                """
                        Branco significa que nada foi encontrado.
                        Quanto mais roxo você vê, mais perto está.
                        """);

        helper.page("otherworld_groves");
        this.add(helper.pageTitle(), "Bosques do Outro Mundo");
        this.add(helper.pageText(),
                """
                        Bosques do Outro Mundo são cavernas exuberantes e cobertas de vegetação, com [#](%1$s)Árvores do Outro Mundo[#](),
                              e paredes de [](item://occultism:otherstone), e representam a maneira mais rápida de obter tudo que
                              alguém precisa para começar como um invocador.
                              Para encontrá-los, sintonize sua vara de adivinhação com as folhas do Outro Mundo
                              ou troncos, pois ao contrário da outra pedra, elas só aparecem nesses bosques.
                                     """.formatted(COLOR_PURPLE));

        helper.page("otherworld_groves_2");
        this.add(helper.pageText(),
                """
                        **Dica:** No Mundo Superior, olhe **para baixo**.
                          """);

        helper.page("otherworld_trees");
        this.add(helper.pageTitle(), "Árvores do Outro Mundo");
        this.add(helper.pageText(),
                """
                        Árvores do Outro Mundo crescem naturalmente em Bosques do Outro Mundo. A olho nu parecem carvalhos,
                              mas ao Terceiro Olho elas revelam sua verdadeira natureza. \s
                              **Importante:** Mudas do Outro Mundo só podem ser obtidas quebrando as folhas manualmente, naturalmente só caem as mudas de carvalho.
                                     """);

        helper.page("otherworld_trees_2");
        this.add(helper.pageText(),
                """
                        Árvores cultivadas a partir de Mudas Estáveis ​​do Outro Mundo obtidas de comerciantes espirituais não têm essa limitação.
                                     """);

        helper.entry("candle");
        this.add(helper.entryName(), "Velas");
        this.add(helper.entryDescription(), "Que haja luz!");

        helper.page("white_candle");
        this.add(helper.pageText(),
                """
                        As velas dão estabilidade aos rituais e são uma parte importante de quase todos os pentagramas.
                              **As velas também funcionam como estantes de livros para fins de encantamento.**
                              \\
                              \\
                              Velas de Minecraft e outros Mods podem ser usadas no lugar de velas do Occultism.
                                        """);

        helper.page("tallow");
        this.add(helper.pageText(),
                """
                        Ingrediente chave para velas. Mate animais grandes como porcos, vacas ou ovelhas com um [](item://occultism:butcher_knife)
                        para colher [](item://occultism:tallow).
                                        """);


        helper.page("white_candle_recipe");
        //no text


        helper.entry("ritual_prep");
        this.add(helper.entryName(), "Preparando Rituais");
        this.add(helper.entryDescription(), "Coisas para fazer antes de seu primeiro ritual.");

        helper.page("intro");
        this.add(helper.pageTitle(), "Preparando Rituais");
        this.add(helper.pageText(),
                """
                        Para invocar espíritos do [#](%1$s)Outro Lugar[#]() em relativa segurança,
                              você precisa desenhar um pentáculo apropriado usando giz para conter seus poderes.
                              Além disso, você precisa de [Tijelas sacrificiais](item://occultism:sacrificial_bowl)
                              para sacrificar itens adequados a atrair o espírito.
                                     """.formatted(COLOR_PURPLE));


        helper.page("white_chalk");
        this.add(helper.pageText(),
                """
                        Para pentáculos, apenas a cor do giz é relevante, não o glifo/sinal.
                              Para fins decorativos, você pode clicar repetidamente em um bloco para mudar os glifos.
                              Para outros gizes veja [Giz](entry://occultism:dictionary_of_spirits/getting_started/chalks).
                                        """);

        helper.page("burnt_otherstone_recipe");
        //no text

        helper.page("otherworld_ashes_recipe");
        //no text

        helper.page("impure_white_chalk_recipe");
        //no text

        helper.page("white_chalk_recipe");
        //no text

        helper.page("sacrificial_bowl");
        this.add(helper.pageText(),
                """
                        Essas tigelas são usadas para sacrificar itens como parte de um ritual e você precisará de um monte delas.
                              Nota: Sua colocação exata no ritual não importa - apenas mantenha-as dentro de 8 blocos do centro do pentáculo!
                                         """);

        helper.page("sacrificial_bowl_recipe");
        //no text

        helper.page("golden_sacrificial_bowl");
        this.add(helper.pageText(),
                """
                        Esta tigela de sacrifício especial é usada para ativar o ritual clicando com o botão direito do mouse com o item de ativação,
                              geralmente um livro de vínculo, uma vez que tudo foi arrumado e você está pronto para começar.
                                         """);


        helper.page("golden_sacrificial_bowl_recipe");
        //no text


        helper.entry("ritual_book");
        this.add(helper.entryName(), "Livros de Vínculo");
        this.add(helper.entryDescription(), "Ou como identificar seu espírito");

        helper.page("intro");
        this.add(helper.pageTitle(), "Livros de Vínculo");
        this.add(helper.pageText(),
                """
                        Para invocar um espírito, um [#](%1$s)Livro de Vínculo[#]() deve ser usado no ritual.
                              Há um tipo de livro correspondente a cada tipo (ou nível) de espírito.
                              Para identificar um espírito a ser invocado, seu nome deve ser escrito no [#](%1$s)Livro de Vínculo[#](),
                              resultando em um [#](%1$s)Livro de Vínculo[#]() que pode ser usado no ritual. 
                                     """.formatted(COLOR_PURPLE));

        helper.page("purified_ink_recipe");
        this.add(helper.pageText(),
                """
                        Para criar [#](%1$s)Livros de Vínculo[#]() para invocar espíritos, você precisa de tinta purificada.
                              Simplesmente coloque qualquer corante preto em [](item://occultism:spirit_fire) para purificá-lo.
                                        """.formatted(COLOR_PURPLE));

        helper.page("book_of_binding_foliot_recipe");
        this.add(helper.pageText(),
                """
                        Fabrique um livro de vínculo que será usado para invocar um espírito [#](%1$s)Foliot[#]().
                                       """.formatted(COLOR_PURPLE));

        helper.page("book_of_binding_bound_foliot_recipe");
        this.add(helper.pageText(),
                """
                        Adicione o nome do espírito a ser invocado ao seu livro de vínculo juntando-o com o Dicionário de Espíritos. O Dicionário não será usado.
                                       """);

        helper.page("book_of_binding_djinni_recipe");
        //no text

        helper.page("book_of_binding_afrit_recipe");
        //no text

        helper.page("book_of_binding_marid_recipe");
        //no text

        helper.entry("first_ritual");
        this.add(helper.entryName(), "Primeiro Ritual");
        this.add(helper.entryDescription(), "Agora sim estamos começando!");

        helper.page("intro");
        this.add(helper.pageTitle(), "O Ritual (tm)");
        this.add(helper.pageText(),
                """
                        Estas páginas guiarão o caro leitor pelo processo do primeiro ritual passo a passo.
                        Começamos colocando a tigela sacrificial dourada e desenhando o pentáculo apropriado,
                        [#](%1$s)Círculo de Aviar[#]().
                        \\
                        \\
                        Depois, coloque quatro tijelas sacrificiais perto do pentáculo.
                                     """.formatted(COLOR_PURPLE));


        helper.page("bowl_placement");
        //no text

        helper.page("bowl_text");
        this.add(helper.pageText(),
                """
                        [Tijelas Sacrificiais](item://occultism:sacrificial_bowl) devem ser colocadas **em qualquer lugar** 
                        dentro de 8 blocos da [](item://occultism:golden_sacrificial_bowl) central.
                        The exact location does not matter.
                        \\
                        \\
                        Agora é hora de colocar os ingredientes que você vê na próxima página nas tigelas sacrificiais (normais, não douradas).
                          """.formatted(COLOR_PURPLE));

        helper.page("ritual_recipe");
        this.add(helper.pageText(),
                """
                        Esta página mostrará a receita do ritual no futuro.
                        Por enquanto, consulte a Edição Antiga para procurar a 
                                 receita do ritual "Invoca Foliot Triturador".
                                   """.formatted(COLOR_PURPLE));
        //no text

        helper.page("start_ritual");
        this.add(helper.pageText(),
                """
                        Por fim, clique com o botão direito do mouse na tigela sacrificial dourada com o livro de vínculo **vinculado**
                              que você criou e espere até que o triturador apareça.
                              \\
                              \\
                              Agora só falta jogar os minérios apropriados perto do triturador e esperar que ele transforme-os em pó.
                                     """.formatted(COLOR_PURPLE));


        helper.entry("brush");
        this.add(helper.entryName(), "Apagador");
        this.add(helper.entryDescription(), "Limpando!");

        helper.page("intro");
        this.add(helper.pageTitle(), "Próximos Passos");
        this.add(helper.pageText(),
                """
                        Giz é chato de limpar, mas clicando com o botão direito do mouse com um apagador você pode removê-lo do mundo com muito mais facilidade. 
                                     """);

        helper.page("brushRecipe");
        //no text

        helper.entry("next_steps");
        this.add(helper.entryName(), "Próximos Passos");
        this.add(helper.entryDescription(), "Ao infinito e além!");

        helper.page("text");
        this.add(helper.pageTitle(), "Próximos Passos");
        this.add(helper.pageText(),
                """
                        Por enquanto, consulte a seção Começando da Edição Antiga para saber mais sobre as próximas etapas.
                              \\
                              \\
                              Veja também a [Entrada de Aviso](entry://occultism:dictionary_of_spirits/getting_started/intro).
                                     """);
    }

    private void addPentaclesCategory(BookLangHelper helper) {
        helper.category("pentacles");
        this.add(helper.categoryName(), "Pentáculos");

        helper.entry("summon_foliot");
        this.add(helper.entryName(), "Círculo de Aviar");

        helper.page("intro");
        this.add(helper.pageTitle(), "Óculos do Outro Mundo");
        this.add(helper.pageText(),
                """
                        **Propósito:** Invocar um [#](%1$s)Foliot[#]()
                              \\
                              \\
                              Considerado pela maioria como o pentáculo mais simples, o [#](%1$s)Círculo de Aviar[#]() é fácil de configurar,
                              mas fornece apenas um mínimo de poder de vinculação e proteção para o invocador.
                              \\
                              \\
                              Apenas os [#](%1$s)Foliot[#]() mais fracos podem ser invocados em rituais usando este pentáculo.
                                 """.formatted(COLOR_PURPLE));

        helper.page("multiblock");
        //no text

        helper.page("uses");
        this.add(helper.pageTitle(), "Usos");
        this.add(helper.pageText(),
                """
                        - [Foliot Triturador]()
                        - //TODO: Add remaining uses
                        """.formatted(COLOR_PURPLE));
    }

    private void addRitualsCategory(BookLangHelper helper) {
        helper.category("rituals");
        this.add(helper.categoryName(), "Rituais");

        helper.entry("craft_otherworld_goggles");
        this.add(helper.entryName(), "Fabrique Óculos do Outro Mundo");

        helper.page("intro");
        this.add(helper.pageTitle(), "Óculos do Outro Mundo");
        this.add(helper.pageText(),
                """
                        //TODO: Entry not yet implemented
                        """);
    }

    private void addAdvancedCategory(BookLangHelper helper) {
        helper.category("advanced");
        this.add(helper.categoryName(), "Avançado");

        helper.entry("chalks");
        this.add(helper.entryName(), "Gizes");
        this.add(helper.entryDescription(), "Gizes melhores para rituais melhores!");

        helper.page("intro");
        this.add(helper.pageTitle(), "Mais Gizes");
        this.add(helper.pageText(),
                """
                        Para rituais mais avançados, o simples [Giz Branco](entry://occultism:dictionary_of_spirits/getting_started/ritual_prep@white_chalk)
                                 não é suficiente. Em vez disso, são necessários gizes feitos de materiais mais arcanos.
                                 """);

        helper.page("gold_chalk_recipe");
        //no text

        helper.page("purple_chalk_recipe");
        //no text

        helper.page("red_chalk_recipe");
        //no text

        helper.page("afrit_essence");
        this.add(helper.pageText(),
                """
                        //TODO: Entry not yet implemented
                        """);
    }


    private void addAdvancements() {
        //"advancements\.occultism\.(.*?)\.title": "(.*)",
        //this.advancementTitle\("\1", "\2"\);
        this.advancementTitle("root", "Ocultismo");
        this.advancementDescr("root", "Espiritualize!");
        this.advancementTitle("summon_foliot_crusher", "Dobrando Minério");
        this.advancementDescr("summon_foliot_crusher", "Nhoc! Nhoc! Nhoc!");
        this.advancementTitle("familiars", "Ocultismo: Amigos");
        this.advancementDescr("familiars", "Use um ritual para invocar um familiar");
        this.advancementDescr("familiar.bat", "Atraia um morcego normal até o seu morcego familiar");
        this.advancementTitle("familiar.bat", "Canibalismo");
        this.advancementDescr("familiar.capture", "Prenda seu familiar em um anel familiar");
        this.advancementTitle("familiar.capture", "Temos que pegar!");
        this.advancementDescr("familiar.cthulhu", "Deixe seu Cthulhu familiar triste");
        this.advancementTitle("familiar.cthulhu", "Seu Monstro!");
        this.advancementDescr("familiar.deer", "Observe quando seu veado familiar defecar semente demoníaca");
        this.advancementTitle("familiar.deer", "Cocô Demoníaco");
        this.advancementDescr("familiar.devil", "Comande seu diabo familiar a soprar fogo");
        this.advancementTitle("familiar.devil", "Fogo do inferno");
        this.advancementDescr("familiar.dragon_nugget", "Dê uma pepita de ouro para seu dragão familiar");
        this.advancementTitle("familiar.dragon_nugget", "Negócio Fechado!");
        this.advancementDescr("familiar.dragon_ride", "Deixe seu avarento familiar pegar algo enquanto monta um dragão familiar");
        this.advancementTitle("familiar.dragon_ride", "Trabalhando juntos");
        this.advancementDescr("familiar.greedy", "Deixe seu avarento familiar pegar algo para você");
        this.advancementTitle("familiar.greedy", "Garoto de Entregas");
        this.advancementDescr("familiar.party", "Faça seu familiar dançar");
        this.advancementTitle("familiar.party", "Dance!");
        this.advancementDescr("familiar.rare", "Obtenha uma variante rara de familiar");
        this.advancementTitle("familiar.rare", "Amigo Raro");
        this.advancementDescr("familiar.root", "Use um ritual para invocar um familiar");
        this.advancementTitle("familiar.root", "Ocultismo: Amigos");
        this.advancementDescr("familiar.mans_best_friend", "Afague seu dragão familiar; e brinque de jogar o graveto com ele");
        this.advancementTitle("familiar.mans_best_friend", "Melhor Amigo do Homem");
        this.advancementTitle("familiar.blacksmith_upgrade", "Totalmente Equipado!");
        this.advancementDescr("familiar.blacksmith_upgrade", "Deixe seu ferreiro familiar melhorar um de seus outros familiares");
        this.advancementTitle("familiar.guardian_ultimate_sacrifice", "O Sacrifício Definitivo");
        this.advancementDescr("familiar.guardian_ultimate_sacrifice", "Deixe seu Guardião familiar morrer para você se salvar");
        this.advancementTitle("familiar.headless_cthulhu_head", "O Horror!");
        this.advancementDescr("familiar.headless_cthulhu_head", "Mate Cthulhu perto de seu Homem-rato Sem Cabeça familiar");
        this.advancementTitle("familiar.headless_rebuilt", "Podemos reconstruí-lo");
        this.advancementDescr("familiar.headless_rebuilt", "\"Reconstrua\" seu Homem-rato Sem Cabeça familiar depois que ele morreu");
        this.advancementTitle("familiar.chimera_ride", "Monte!");
        this.advancementDescr("familiar.chimera_ride", "Monte em sua Quimera familiar quando tiver a alimentado o suficiente");
        this.advancementTitle("familiar.goat_detach", "Desmontar");
        this.advancementDescr("familiar.goat_detach", "Dê uma maçã dourada à sua Quimera familiar");
        this.advancementTitle("familiar.shub_niggurath_summon", "A Cabra Negra da Floresta");
        this.advancementDescr("familiar.shub_niggurath_summon", "Transforme seu bode familiar em algo terrível");
        this.advancementTitle("familiar.shub_cthulhu_friends", "Amor Sobrenatural");
        this.advancementDescr("familiar.shub_cthulhu_friends", "Veja Shub Niggurath e Cthulhu darem as mãos");
        this.advancementTitle("familiar.shub_niggurath_spawn", "Pense nas Crianças!");
        this.advancementDescr("familiar.shub_niggurath_spawn", "Deixe uma prole de Shub Niggurath causar dano a um inimigo ao explodir");
        this.advancementTitle("familiar.beholder_ray", "Raio da Morte");
        this.advancementDescr("familiar.beholder_ray", "Deixe seu Beholder familiar atacar um inimigo");
        this.advancementTitle("familiar.beholder_eat", "Fome");
        this.advancementDescr("familiar.beholder_eat", "Veja seu Beholder familiar comer uma prole de Shub Niggurath");
        this.advancementTitle("familiar.fairy_save", "Anjo da Guarda");
        this.advancementDescr("familiar.fairy_save", "Deixe sua Fada familiar salvar um de seus outros familiares da morte certa");
        this.advancementTitle("familiar.mummy_dodge", "Ninja!");
        this.advancementDescr("familiar.mummy_dodge", "Desvie de um ataque com o efeito de esquiva da Múmia familiar");
        this.advancementTitle("familiar.beaver_woodchop", "Lenhador");
        this.advancementDescr("familiar.beaver_woodchop", "Deixe seu Castor familiar derrubar uma árvore");
    }

    private void addKeybinds() {
        this.add("key.occultism.category", "Occultism");
        this.add("key.occultism.backpack", "Abrir Bolsa");
        this.add("key.occultism.storage_remote", "Abrir Conector de Armazenamento");
        this.add("key.occultism.familiar.otherworld_bird", "Alternar Efeito do Anel: Drikwing");
        this.add("key.occultism.familiar.greedy_familiar", "Alternar Efeito do Anel: Avarento");
        this.add("key.occultism.familiar.bat_familiar", "Alternar Efeito do Anel: Morcego");
        this.add("key.occultism.familiar.deer_familiar", "Alternar Efeito do Anel: Veado");
        this.add("key.occultism.familiar.cthulhu_familiar", "Alternar Efeito do Anel: Cthulhu");
        this.add("key.occultism.familiar.devil_familiar", "Alternar Efeito do Anel: Diabo");
        this.add("key.occultism.familiar.dragon_familiar", "Alternar Efeito do Anel: Dragão");
        this.add("key.occultism.familiar.blacksmith_familiar", "Alternar Efeito do Anel: Ferreiro");
        this.add("key.occultism.familiar.guardian_familiar", "Alternar Efeito do Anel: Guardião");
        this.add("key.occultism.familiar.headless_familiar", "Alternar Efeito do Anel: Homem-rato Sem Cabeça");
        this.add("key.occultism.familiar.chimera_familiar", "Alternar Efeito do Anel: Quimera");
        this.add("key.occultism.familiar.goat_familiar", "Alternar Efeito do Anel: Bode");
        this.add("key.occultism.familiar.shub_niggurath_familiar", "Alternar Efeito do Anel: Shub Niggurath");
        this.add("key.occultism.familiar.beholder_familiar", "Alternar Efeito do Anel: Beholder");
        this.add("key.occultism.familiar.fairy_familiar", "Alternar Efeito do Anel: Fada");
        this.add("key.occultism.familiar.mummy_familiar", "Alternar Efeito do Anel: Múmia");
        this.add("key.occultism.familiar.beaver_familiar", "Alternar Efeito do Anel: Castor");
    }

    private void addJeiTranslations() {
        this.add("occultism.jei.spirit_fire", "Fogo místico");
        this.add("occultism.jei.crushing", "Espírito Triturador");
        this.add("occultism.jei.miner", "Mina dimensional");
        this.add("occultism.jei.miner.chance", "Chance: %d%%");
        this.add("occultism.jei.ritual", "Ritual Oculto");
        this.add("occultism.jei.pentacle", "Pentáculo");
        this.add("jei.occultism.ingredient.tallow.description", "Mate animais como \u00a72porcos\u00a7r, \u00a72vacas\u00a7r, \u00a72ovelhas\u00a7r, \u00a72cavalos\u00a7r e \u00a72lhamas\u00a7r com o Cutelo para obter sebo.");
        this.add("jei.occultism.ingredient.otherstone.description", "Encontrado principalmente em Bosques do Outro Mundo. Visível apenas enquanto o status \u00a76Terceiro Olho\u00a7r estiver ativo. Veja \u00a76Dicionário de Espíritos\u00a7r para mais informações.");
        this.add("jei.occultism.ingredient.otherworld_log.description", "Encontrado principalmente em Bosques do Outro Mundo. Visível apenas enquanto o status \u00a76Terceiro Olho\u00a7r estiver ativo. Veja \u00a76Dicionário de Espíritos\u00a7r para mais informações.");
        this.add("jei.occultism.ingredient.otherworld_sapling.description", "Encontrado principalmente em Bosques do Outro Mundo. Visível apenas enquanto o status \u00a76Terceiro Olho\u00a7r estiver ativo. Veja \u00a76Dicionário de Espíritos\u00a7r para mais informações.");
        this.add("jei.occultism.ingredient.otherworld_sapling_natural.description", "Encontrado principalmente em Bosques do Outro Mundo. Visível apenas enquanto o status \u00a76Terceiro Olho\u00a7r estiver ativo. Veja \u00a76Dicionário de Espíritos\u00a7r para mais informações.");
        this.add("jei.occultism.ingredient.otherworld_leaves.description", "Encontrado principalmente em Bosques do Outro Mundo. Visível apenas enquanto o status \u00a76Terceiro Olho\u00a7r estiver ativo. Veja \u00a76Dicionário de Espíritos\u00a7r para mais informações.");
        this.add("jei.occultism.ingredient.iesnium_ore.description", "Encontrado no Nether. Visível apenas enquanto o status \u00a76Terceiro\u00a7r \u00a76Olho\u00a7r estiver ativo. Veja \u00a76Dicionário\u00a7r \u00a76de\u00a7r \u00a76Espíritos\u00a7r para mais informações.");
        this.add("jei.occultism.ingredient.spirit_fire.description", "Jogue \u00a76Fruta do sonho demoníaco\u00a7r no chão e toque fogo nela. Veja \u00a76Dicionário de Espíritos\u00a7r para mais informações.");
        this.add("jei.occultism.ingredient.datura.description", "Pode ser usado para curar qualquer espírito e familiar convocado pelos Rituais de Occultism. Basta clicar com o botão direito do mouse na entidade para curá-la por um coração");
        this.add("jei.occultism.sacrifice", "Sacrifício: %s");
        this.add("jei.occultism.summon", "Invocação: %s");
        this.add("jei.occultism.job", "Trabalho: %s");
        this.add("jei.occultism.item_to_use", "Item a ser usado:");
        this.add("jei.occultism.error.missing_id", "Não foi possível identificar a receita.");
        this.add("jei.occultism.error.invalid_type", "Tipo de receita inválido.");
        this.add("jei.occultism.error.recipe_too_large", "Receita maior que 3x3.");
        this.add("jei.occultism.error.pentacle_not_loaded", "O pentáculo não pôde ser carregado.");
        this.add("item.occultism.jei_dummy.require_sacrifice", "Requer Sacrifício!");
        this.add("item.occultism.jei_dummy.require_sacrifice.tooltip", "Este ritual requer um sacrifício para começar. Consulte o Dicionário de Espíritos para obter instruções detalhadas.");
        this.add("item.occultism.jei_dummy.require_item_use", "Requer Uso de Item!");
        this.add("item.occultism.jei_dummy.require_item_use.tooltip", "Este ritual requer o uso de um item específico para começar. Consulte o Dicionário de Espíritos para obter instruções detalhadas.");
        this.add("item.occultism.jei_dummy.none", "Resultado do ritual sem itens");
        this.add("item.occultism.jei_dummy.none.tooltip", "Este ritual não cria nenhum item.");
    }

    private void addFamiliarSettingsMessages() {
        this.add("message.occultism.familiar.otherworld_bird.enabled", "Efeito do Anel - Drikwing: Ligado");
        this.add("message.occultism.familiar.otherworld_bird.disabled", "Efeito do Anel - Drikwing: Desligado");
        this.add("message.occultism.familiar.greedy_familiar.enabled", "Efeito do Anel - Avarento: Ligado");
        this.add("message.occultism.familiar.greedy_familiar.disabled", "Efeito do Anel - Avarento: Desligado");
        this.add("message.occultism.familiar.bat_familiar.enabled", "Efeito do Anel - Morcego: Ligado");
        this.add("message.occultism.familiar.bat_familiar.disabled", "Efeito do Anel - Morcego: Desligado");
        this.add("message.occultism.familiar.deer_familiar.enabled", "Efeito do Anel - Veado: Ligado");
        this.add("message.occultism.familiar.deer_familiar.disabled", "Efeito do Anel - Veado: Desligado");
        this.add("message.occultism.familiar.cthulhu_familiar.enabled", "Efeito do Anel - Cthulhu: Ligado");
        this.add("message.occultism.familiar.cthulhu_familiar.disabled", "Efeito do Anel - Cthulhu: Desligado");
        this.add("message.occultism.familiar.devil_familiar.enabled", "Efeito do Anel - Diabo: Ligado");
        this.add("message.occultism.familiar.devil_familiar.disabled", "Efeito do Anel - Diabp: Desligado");
        this.add("message.occultism.familiar.dragon_familiar.enabled", "Efeito do Anel - Dragão: Ligado");
        this.add("message.occultism.familiar.dragon_familiar.disabled", "Efeito do Anel - Dragão: Desligado");
        this.add("message.occultism.familiar.blacksmith_familiar.enabled", "Efeito do Anel - Ferreiro: Ligado");
        this.add("message.occultism.familiar.blacksmith_familiar.disabled", "Efeito do Anel - Ferreiro: Desligado");
        this.add("message.occultism.familiar.guardian_familiar.enabled", "Efeito do Anel - Guardião: Ligado");
        this.add("message.occultism.familiar.guardian_familiar.disabled", "Efeito do Anel - Guardião: Desligado");
        this.add("message.occultism.familiar.headless_familiar.enabled", "Efeito do Anel - Homem-rato Sem Cabeça: Ligado");
        this.add("message.occultism.familiar.headless_familiar.disabled", "Efeito do Anel - Homem-rato Sem Cabeça: Desligado");
        this.add("message.occultism.familiar.chimera_familiar.enabled", "Efeito do Anel - Quimera: Ligado");
        this.add("message.occultism.familiar.chimera_familiar.disabled", "Efeito do Anel - Quimera: Desligado");
        this.add("message.occultism.familiar.shub_niggurath_familiar.enabled", "Efeito do Anel - Shub Niggurath: Ligado");
        this.add("message.occultism.familiar.shub_niggurath_familiar.disabled", "Efeito do Anel - Shub Niggurath: Desligado");
        this.add("message.occultism.familiar.beholder_familiar.enabled", "Efeito do Anel - Beholder: Ligado");
        this.add("message.occultism.familiar.beholder_familiar.disabled", "Efeito do Anel - Beholder: Desligado");
        this.add("message.occultism.familiar.fairy_familiar.enabled", "Efeito do Anel - Fada: Ligado");
        this.add("message.occultism.familiar.fairy_familiar.disabled", "Efeito do Anel - Fada: Desligado");
        this.add("message.occultism.familiar.mummy_familiar.enabled", "Efeito do Anel - Múmia: Ligado");
        this.add("message.occultism.familiar.mummy_familiar.disabled", "Efeito do Anel - Múmia: Desligado");
        this.add("message.occultism.familiar.beaver_familiar.enabled", "Efeito do Anel - Castor: Ligado");
        this.add("message.occultism.familiar.beaver_familiar.disabled", "Efeito do Anel - Castor: Desligado");
    }

    private void addPentacles() {
        this.addPentacle("otherworld_bird", "Pássaro do Outro Mundo");
        this.addPentacle("craft_afrit", "Confinamento Permanente de Sevira");
        this.addPentacle("craft_djinni", "Vinculação Maior de Strigeor");
        this.addPentacle("craft_foliot", "Compulsão Espectral de Eziveus");
        this.addPentacle("craft_marid", "Torre Invertida Uphyxes");
        this.addPentacle("possess_afrit", "Conjuração Comandada de Abras");
        this.addPentacle("possess_djinni", "Fascínio de Ihagan");
        this.addPentacle("possess_foliot", "Engodo de Hedyrin");
        this.addPentacle("summon_afrit", "Conjuração de Abras");
        this.addPentacle("summon_djinni", "Chamado de Ophyx");
        this.addPentacle("summon_foliot", "Círculo de Aviar");
        this.addPentacle("summon_wild_afrit", "Conjuração Aberta de Abras");
        this.addPentacle("summon_marid", "Atração Incentivada de Fatma");
        this.addPentacle("summon_wild_greater_spirit", "Chamado Desvinculado de Osorin");
    }

    private void addPentacle(String id, String name) {
        this.add(Util.makeDescriptionId("multiblock", new ResourceLocation(Occultism.MODID, id)), name);
    }

    private void addRitualDummies() {
        this.add("item.occultism.ritual_dummy.custom_ritual", "Manequim de ritual customizado");
        this.add("item.occultism.ritual_dummy.custom_ritual.tooltip", "Usado para modpacks como substituto para rituais personalizados que não possuem seu próprio item de ritual.");
        this.add("item.occultism.ritual_dummy.craft_dimensional_matrix", "Ritual: Fabricar Matriz Dimensional");
        this.add("item.occultism.ritual_dummy.craft_dimensional_matrix.tooltip", "A matriz dimensional é o ponto de entrada para uma pequena dimensão usada para guardar itens.");
        this.add("item.occultism.ritual_dummy.craft_dimensional_mineshaft", "Ritual: Fabricar Mina Dimensional");
        this.add("item.occultism.ritual_dummy.craft_dimensional_mineshaft.tooltip", "Permite que espíritos mineiros entrem na dimensão de mineração e tragam recursos de volta.");
        this.add("item.occultism.ritual_dummy.craft_infused_lenses", "Ritual: Fabricar Lentes Infundidas");
        this.add("item.occultism.ritual_dummy.craft_infused_lenses.tooltip", "Essas lentes são usadas para criar óculos que te permitem ver além do mundo físico.");
        this.add("item.occultism.ritual_dummy.craft_infused_pickaxe", "Ritual: Fabricar Picareta Infundida");
        this.add("item.occultism.ritual_dummy.craft_infused_pickaxe.tooltip", "Infunda uma picareta.");
        this.add("item.occultism.ritual_dummy.craft_miner_djinni_ores", "Ritual: Invocar Djinni Mineiro");
        this.add("item.occultism.ritual_dummy.craft_miner_djinni_ores.tooltip", "Invoca Djinni Mineiro em uma lâmpada mágica.");
        this.add("item.occultism.ritual_dummy.craft_miner_foliot_unspecialized", "Ritual: Invocar Foliot Mineiro");
        this.add("item.occultism.ritual_dummy.craft_miner_foliot_unspecialized.tooltip", "Invoca Foliot Mineiro em uma lâmpada mágica.");
        this.add("item.occultism.ritual_dummy.craft_satchel", "Ritual: Fabricar Bolsa Surpreendentemente Substancial");
        this.add("item.occultism.ritual_dummy.craft_satchel.tooltip", "Esta bolsa permite guardar mais itens do que seu tamanho indicaria, tornando-a uma prática companheira de viagem.");
        this.add("item.occultism.ritual_dummy.craft_soul_gem", "Ritual: Fabricar Gema da Alma");
        this.add("item.occultism.ritual_dummy.craft_soul_gem.tooltip", "A gema da alma permite o armazenamento temporário de seres vivos. ");
        this.add("item.occultism.ritual_dummy.craft_familiar_ring", "Ritual: Fabricar Anel Familiar");
        this.add("item.occultism.ritual_dummy.craft_familiar_ring.tooltip", "O anel familiar permite armazenar familiares. O anel aplicará o efeito do familiar ao usuário.");
        this.add("item.occultism.ritual_dummy.craft_stabilizer_tier1", "Ritual: Fabricar Estabilizador de Armazenamento Tier 1");
        this.add("item.occultism.ritual_dummy.craft_stabilizer_tier1.tooltip", "O estabilizador de armazenamento permite armazenar mais itens no conector de armazenamento dimensional.");
        this.add("item.occultism.ritual_dummy.craft_stabilizer_tier2", "Ritual: Fabricar Estabilizador de Armazenamento Tier 2");
        this.add("item.occultism.ritual_dummy.craft_stabilizer_tier2.tooltip", "O estabilizador de armazenamento permite armazenar mais itens no conector de armazenamento dimensional.");
        this.add("item.occultism.ritual_dummy.craft_stabilizer_tier3", "Ritual: Fabricar Estabilizador de Armazenamento Tier 3");
        this.add("item.occultism.ritual_dummy.craft_stabilizer_tier3.tooltip", "O estabilizador de armazenamento permite armazenar mais itens no conector de armazenamento dimensional.");
        this.add("item.occultism.ritual_dummy.craft_stabilizer_tier4", "Ritual: Fabricar Estabilizador de Armazenamento Tier 4");
        this.add("item.occultism.ritual_dummy.craft_stabilizer_tier4.tooltip", "O estabilizador de armazenamento permite armazenar mais itens no conector de armazenamento dimensional.");
        this.add("item.occultism.ritual_dummy.craft_stable_wormhole", "Ritual: Fabricar Buraco de Minhoca Estável");
        this.add("item.occultism.ritual_dummy.craft_stable_wormhole.tooltip", "O buraco de minhoca estável permite acessar uma matriz dimensional remotamente.");
        this.add("item.occultism.ritual_dummy.craft_storage_controller_base", "Ritual: Fabricar Base do Atuador de Armazenamento");
        this.add("item.occultism.ritual_dummy.craft_storage_controller_base.tooltip", "A base do atuador de armazenamento aprisiona um Foliot responsável por interagir com os itens em uma matriz de armazenamento dimensional.");
        this.add("item.occultism.ritual_dummy.craft_storage_remote", "Ritual: Fabricar Conector de Armazenamento");
        this.add("item.occultism.ritual_dummy.craft_storage_remote.tooltip", "O conector de armazenamento pode ser vinculado a um atuador para acessar itens remotamente.");
        this.add("item.occultism.ritual_dummy.familiar_otherworld_bird", "Ritual: Invocar Drikwing Familiar");
        this.add("item.occultism.ritual_dummy.familiar_otherworld_bird.tooltip", "Drikwing fornecerá ao seu dono habilidades de voo limitadas quando estiver próximo.");
        this.add("item.occultism.ritual_dummy.familiar_parrot", "Ritual: Invocar Papagaio Familiar");
        this.add("item.occultism.ritual_dummy.familiar_parrot.tooltip", "Papagaios familiares se comportam exatamente como papagaios domesticados.");
        this.add("item.occultism.ritual_dummy.familiar_greedy", "Ritual: Invocar Avarento Familiar");
        this.add("item.occultism.ritual_dummy.familiar_greedy.tooltip", "Avarentos familiares pegam itens para seu mestre. Quando armazenados em um anel familiar, eles aumentam o alcance de coleta(como um ímã de item).");
        this.add("item.occultism.ritual_dummy.familiar_bat", "Ritual: Invocar Morcego Familiar");
        this.add("item.occultism.ritual_dummy.familiar_bat.tooltip", "Morcegos familiares fornecem visão noturna ao seu mestre.");
        this.add("item.occultism.ritual_dummy.familiar_deer", "Ritual: Invocar Veado Familiar");
        this.add("item.occultism.ritual_dummy.familiar_deer.tooltip", "Veados familiares fornecem um boost de salto para seu mestre.");
        this.add("item.occultism.ritual_dummy.familiar_cthulhu", "Ritual: Invocar Cthulhu Familiar");
        this.add("item.occultism.ritual_dummy.familiar_cthulhu.tooltip", "Os Cthulhu familiares fornecem respiração aquática para seu mestre.");
        this.add("item.occultism.ritual_dummy.familiar_devil", "Ritual: Invocar Diabo Familiar");
        this.add("item.occultism.ritual_dummy.familiar_devil.tooltip", "Os diabos familiares fornecem resistência a fogo ao seu mestre.");
        this.add("item.occultism.ritual_dummy.familiar_dragon", "Ritual: Invocar Dragão Familiar");
        this.add("item.occultism.ritual_dummy.familiar_dragon.tooltip", "Dragões familiares fornecem um ganho de experiência melhorado ao seu mestre.");
        this.add("item.occultism.ritual_dummy.familiar_blacksmith", "Ritual: Invocar Ferreiro Familiar");
        this.add("item.occultism.ritual_dummy.familiar_blacksmith.tooltip", "Os ferreiros familiares pegam pedras que seu mestre minera para reparar seu equipamento.");
        this.add("item.occultism.ritual_dummy.familiar_guardian", "Ritual: Invocar Guardião Familiar");
        this.add("item.occultism.ritual_dummy.familiar_guardian.tooltip", "Os guardiões familiares evitam a morte violenta de seu mestre.");
        this.add("item.occultism.ritual_dummy.familiar_headless", "Ritual: Invocar Homem-rato Sem Cabeça Familiar");
        this.add("item.occultism.ritual_dummy.familiar_headless.tooltip", "O homem-rato sem cabeça familiar aumenta o dano de ataque de seu mestre contra inimigos do tipo de quem ele roubou a cabeça.");
        this.add("item.occultism.ritual_dummy.familiar_chimera", "Ritual: Invocar Quimera Familiar");
        this.add("item.occultism.ritual_dummy.familiar_chimera.tooltip", "A quimera familiar pode ser alimentada para crescer e ganhar velocidade de ataque e dano. Quando grande o suficiente, os jogadores podem montá-la.");
        this.add("item.occultism.ritual_dummy.familiar_beholder", "Ritual: Invocar Beholder Familiar");
        this.add("item.occultism.ritual_dummy.familiar_beholder.tooltip", "O beholder familiar destaca entidades próximas com um efeito de brilho e dispara raios laser nos inimigos.");
        this.add("item.occultism.ritual_dummy.familiar_fairy", "Ritual: Invocar Fada Familiar");
        this.add("item.occultism.ritual_dummy.familiar_fairy.tooltip", "A fada familiar evita que outros familiares morram, drena os inimigos de sua força vital e cura seu mestre e seus familiares.");
        this.add("item.occultism.ritual_dummy.familiar_mummy", "Ritual: Invocar Múmia Familiar");
        this.add("item.occultism.ritual_dummy.familiar_mummy.tooltip", "A múmia familiar é uma especialista em artes marciais e luta para proteger seu mestre.");
        this.add("item.occultism.ritual_dummy.familiar_beaver", "Ritual: Invocar Castor Familiar");
        this.add("item.occultism.ritual_dummy.familiar_beaver.tooltip", "O castor familiar aumenta a velocidade com que seu mestre lenha e colhe árvores próximas quando elas crescem de uma muda.");
        this.add("item.occultism.ritual_dummy.possess_enderman", "Ritual: Invocar Enderman Possuído");
        this.add("item.occultism.ritual_dummy.possess_enderman.tooltip", "O Enderman possuído sempre deixará cair pelo menos uma pérola do ender quando morto.");
        this.add("item.occultism.ritual_dummy.possess_endermite", "Ritual: Invocar Endermite Possuído");
        this.add("item.occultism.ritual_dummy.possess_endermite.tooltip", "O endermite possuído larga pedra do End.");
        this.add("item.occultism.ritual_dummy.possess_skeleton", "Ritual: Invocar Esqueleto Possuído");
        this.add("item.occultism.ritual_dummy.possess_skeleton.tooltip", "O esqueleto possuído é imune à luz do dia e sempre deixa cair pelo menos um crânio de esqueleto quando morto.");
        this.add("item.occultism.ritual_dummy.possess_ghast", "Ritual: Invocar Ghast Possuído");
        this.add("item.occultism.ritual_dummy.possess_ghast.tooltip", "O ghast possuído sempre deixa cair pelo menos uma lágrima de ghast quando morto.");
        this.add("item.occultism.ritual_dummy.summon_afrit_rain_weather", "Ritual: Clima Chuvoso");
        this.add("item.occultism.ritual_dummy.summon_afrit_rain_weather.tooltip", "Invoca um Afrit vinculado que cria chuva.");
        this.add("item.occultism.ritual_dummy.summon_afrit_thunder_weather", "Ritual: Tempestade");
        this.add("item.occultism.ritual_dummy.summon_afrit_thunder_weather.tooltip", "Invoca um Afrit vinculado que cria tempestade.");
        this.add("item.occultism.ritual_dummy.summon_djinni_clear_weather", "Ritual: Clima Limpo");
        this.add("item.occultism.ritual_dummy.summon_djinni_clear_weather.tooltip", "Invoca um Djinni que limpa o clima.");
        this.add("item.occultism.ritual_dummy.summon_djinni_day_time", "Ritual: Invocação do Alvorecer");
        this.add("item.occultism.ritual_dummy.summon_djinni_day_time.tooltip", "Invoca um Djinni que define o horário para meio-dia.");
        this.add("item.occultism.ritual_dummy.summon_djinni_manage_machine", "Ritual: Invocar Djinni Maquinista");
        this.add("item.occultism.ritual_dummy.summon_djinni_manage_machine.tooltip", "O maquinista transfere automaticamente itens entre Sistemas de Armazenamento Dimensional e Inventários e Máquinas conectados.");
        this.add("item.occultism.ritual_dummy.summon_djinni_night_time", "Ritual: Invocação do Crepúsculo");
        this.add("item.occultism.ritual_dummy.summon_djinni_night_time.tooltip", "Invoca um Djinni que define o horário para meia-noite.");
        this.add("item.occultism.ritual_dummy.summon_foliot_crusher", "Ritual: Invocar Foliot Triturador");
        this.add("item.occultism.ritual_dummy.summon_foliot_crusher.tooltip", "O triturador é um espírito convocado para triturar minérios em pó, dobrando efetivamente a produção de metal.");
        this.add("item.occultism.ritual_dummy.summon_djinni_crusher", "Ritual: Invocar Djinni Triturador");
        this.add("item.occultism.ritual_dummy.summon_djinni_crusher.tooltip", "O triturador é um espírito convocado para triturar minérios em pó, (mais que) dobrando efetivamente a produção de metal. Esse triturador decai muito mais devagar que tiers menores.");
        this.add("item.occultism.ritual_dummy.summon_afrit_crusher", "Ritual: Invocar Afrit Triturador");
        this.add("item.occultism.ritual_dummy.summon_afrit_crusher.tooltip", "O triturador é um espírito convocado para triturar minérios em pó, (mais que) dobrando efetivamente a produção de metal. Esse triturador decai muito mais devagar que tiers menores.");
        this.add("item.occultism.ritual_dummy.summon_marid_crusher", "Ritual: Invocar Marid Triturador");
        this.add("item.occultism.ritual_dummy.summon_marid_crusher.tooltip", "O triturador é um espírito convocado para triturar minérios em pó, (mais que) dobrando efetivamente a produção de metal. Esse triturador decai muito mais devagar que tiers menores.");
        this.add("item.occultism.ritual_dummy.summon_foliot_lumberjack", "Ritual: Invocar Foliot Lenhador");
        this.add("item.occultism.ritual_dummy.summon_foliot_lumberjack.tooltip", "O lenhador colherá árvores em sua área de trabalho e depositará os itens caídos no baú especificado.");
        this.add("item.occultism.ritual_dummy.summon_foliot_otherstone_trader", "Ritual: Invocar Mercador de Outra Pedra");
        this.add("item.occultism.ritual_dummy.summon_foliot_otherstone_trader.tooltip", "O mercador de outra pedra troca pedra normal por outra pedra.");
        this.add("item.occultism.ritual_dummy.summon_foliot_sapling_trader", "Ritual: Invocar Mercador de Muda do Outro Mundo");
        this.add("item.occultism.ritual_dummy.summon_foliot_sapling_trader.tooltip", "O mercador de muda do outro mundo troca mudas naturais do outro mundo por mudas estáveis, que podem ser colhidas sem o terceiro olho.");
        this.add("item.occultism.ritual_dummy.summon_foliot_transport_items", "Ritual: Invocar Foliot Transportador");
        this.add("item.occultism.ritual_dummy.summon_foliot_transport_items.tooltip", "O transportador moverá todos os itens que puder acessar de um inventário para outro, incluindo máquinas.");
        this.add("item.occultism.ritual_dummy.summon_foliot_cleaner", "Ritual: Invocar Foliot Zelador");
        this.add("item.occultism.ritual_dummy.summon_foliot_cleaner.tooltip", "O zelador pegará itens caídos e os depositará em um inventário alvo.");
        this.add("item.occultism.ritual_dummy.summon_wild_afrit", "Ritual: Invocar Afrit Desvinculado");
        this.add("item.occultism.ritual_dummy.summon_wild_afrit.tooltip", "Invoca um afrit desvinculado que pode ser morto para obter essência de afrit.");
        this.add("item.occultism.ritual_dummy.summon_wild_hunt", "Ritual: Invocar A Caça Selvagem");
        this.add("item.occultism.ritual_dummy.summon_wild_hunt.tooltip", "A Caça Selvagem consiste em esqueletos wither que garantem o drop de crânios de esqueleto wither, e seus lacaios.");
        this.add("item.occultism.ritual_dummy.summon_wild_otherworld_bird", "Ritual: Invocar Drikwing Selvagem");
        this.add("item.occultism.ritual_dummy.summon_wild_otherworld_bird.tooltip", "Invoca um drikwing familiar que pode ser domado por qualquer um, não só o invocador.");
        this.add("item.occultism.ritual_dummy.summon_wild_parrot", "Ritual: Invocar Papagaio Selvagem");
        this.add("item.occultism.ritual_dummy.summon_wild_parrot.tooltip", "Invoca um papagaio que pode ser domado por qualquer um, não só o invocador.");
    }

    private void addDialogs() {
        this.add("dialog.occultism.dragon.pet", "ron-ron");
        this.add("dialog.occultism.mummy.kapow", "KAPOW!");
    }

    private void addPatchouli() {
        this.add("book.occultism.name", "Dicionário de Espíritos (Edição Antiga)");
        this.add("pentacle.occultism.craft_djinni", "Vinculação Maior de Strigeor");
        this.add("pentacle.occultism.craft_foliot", "Compulsão Espectral de Eziveus");
    }

    private void addModonomiconIntegration() {
        this.add(OccultismModonomiconConstants.I18n.RITUAL_RECIPE_ITEM_USE, "Item a se usar:");
        this.add(OccultismModonomiconConstants.I18n.RITUAL_RECIPE_SUMMON, "Invoca: %s");
        this.add(OccultismModonomiconConstants.I18n.RITUAL_RECIPE_JOB, "Trabalho: %s");
        this.add(OccultismModonomiconConstants.I18n.RITUAL_RECIPE_SACRIFICE, "Sacrifício: %s");
        this.add(OccultismModonomiconConstants.I18n.RITUAL_RECIPE_GO_TO_PENTACLE, "Página do Pentáculo: %s");
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
        this.addModonomiconIntegration();
    }
}