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
import com.klikli_dev.modonomicon.api.datagen.AbstractModonomiconLanguageProvider;
import com.klikli_dev.modonomicon.api.datagen.BookContextHelper;
import com.klikli_dev.occultism.Occultism;
import com.klikli_dev.occultism.TranslationKeys;
import com.klikli_dev.occultism.common.ritual.RitualFactory;
import com.klikli_dev.occultism.datagen.OccultismAdvancementSubProvider;
import com.klikli_dev.occultism.integration.modonomicon.OccultismModonomiconConstants.I18n;
import com.klikli_dev.occultism.registry.*;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.data.PackOutput;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.registries.DeferredHolder;

public class RURUProvider extends AbstractModonomiconLanguageProvider {

    public static final String COLOR_PURPLE = "ad03fc";
    public static final String DEMONS_DREAM = "Блаженство демона";


    public RURUProvider(PackOutput gen) {
        super(gen, Occultism.MODID, "ru_ru");
    }

    public void addItemMessages() {

        //"item\.occultism\.(.*?)\.(.*)": "(.*)",
        // this.lang("ru_ru").add\(OccultismItems.\U\1\E.get\(\).getDescriptionId\(\)  + " \2", "\3"\);

        //book of callings use generic message base key, hence the manual string
        this.lang("ru_ru").add("item.occultism.book_of_calling" + ".message_target_uuid_no_match", "На данный момент дух не связан с этой книгой. Нажмите Shift + ПКМ по духу, чтобы связать его с этой книгой.");
        this.lang("ru_ru").add("item.occultism.book_of_calling" + ".message_target_linked", "Теперь дух связан с этой книгой..");
        this.lang("ru_ru").add("item.occultism.book_of_calling" + ".message_target_cannot_link", "Этот дух не может быть связан с этой книгой. Книга призыва должна соответствовать задаче духа!");
        this.lang("ru_ru").add("item.occultism.book_of_calling" + ".message_target_entity_no_inventory", "У этой сущности нет инвентаря, она не может быть установлена в качестве местоположения для вклада.");
        this.lang("ru_ru").add("item.occultism.book_of_calling" + ".message_spirit_not_found", "Дух, связанный с этой книгой не живёт в этой плоскости существования.");
        this.lang("ru_ru").add("item.occultism.book_of_calling" + ".message_set_deposit", "%s будет класть в %s со стороны: %s");
        this.lang("ru_ru").add("item.occultism.book_of_calling" + ".message_set_deposit_entity", "%s будет передавать предметы в: %s");
        this.lang("ru_ru").add("item.occultism.book_of_calling" + ".message_set_extract", "%s будет извлекать из %s со стороны: %s");
        this.lang("ru_ru").add("item.occultism.book_of_calling" + ".message_set_base", "База для %s установлена на %s");
        this.lang("ru_ru").add("item.occultism.book_of_calling" + ".message_set_storage_controller", "%s будет принимать рабочие задания из %s");
        this.lang("ru_ru").add("item.occultism.book_of_calling" + ".message_set_work_area_size", "%s будет отслеживать рабочее место %s");
        this.lang("ru_ru").add("item.occultism.book_of_calling" + ".message_set_managed_machine", "Настройки для машины %s обновлены.");
        this.lang("ru_ru").add("item.occultism.book_of_calling" + ".message_set_managed_machine_extract_location", "%s будет извлекать из %s со стороны: %s");
        this.lang("ru_ru").add("item.occultism.book_of_calling" + ".message_no_managed_machine", "Установите управляемую машину, прежде чем установить местоположение для извлечения %s");

        this.lang("ru_ru").add(OccultismItems.STABLE_WORMHOLE.get().getDescriptionId() + ".message.set_storage_controller", "Стабильная червоточина связана с Актуаторам хранилища.");
        this.lang("ru_ru").add(OccultismItems.STORAGE_REMOTE.get().getDescriptionId() + ".message.not_loaded", "Чанк для актуатора хранилища не загружен!");
        this.lang("ru_ru").add(OccultismItems.STORAGE_REMOTE.get().getDescriptionId() + ".message.linked", "Удалённое хранилище связано с актуатором.");
        this.lang("ru_ru").add(OccultismItems.DIVINATION_ROD.get().getDescriptionId() + ".message.no_linked_block", "Жезл прорицания не настроен на какой-либо материал.");
        this.lang("ru_ru").add(OccultismItems.DIVINATION_ROD.get().getDescriptionId() + ".message.linked_block", "Жезл прорицания настроен на %s");
        this.lang("ru_ru").add(OccultismItems.DIVINATION_ROD.get().getDescriptionId() + ".message.no_link_found", "С этим блоком нет резонанса.");
        this.lang("ru_ru").add(OccultismItems.SOUL_GEM_ITEM.get().getDescriptionId() + ".message.entity_type_denied", "Самоцвет душ не может хранить этот вид существа.");
    }

    public void addItemTooltips() {
        //"item\.occultism\.(.*?)\.(.*)": "(.*)",
        // this.lang("ru_ru").add\(OccultismItems.\U\1\E.get\(\).getDescriptionId\(\)  + " \2", "\3"\);
        this.lang("ru_ru").add(OccultismItems.BOOK_OF_BINDING_EMPTY.get().getDescriptionId() + ".tooltip", "Эта книга пока не определена к какому-либо духу.");
        this.lang("ru_ru").add(OccultismItems.BOOK_OF_BINDING_FOLIOT.get().getDescriptionId() + ".tooltip", "Эта книга пока не связана с Фолиотом.");
        this.lang("ru_ru").add(OccultismItems.BOOK_OF_BINDING_BOUND_FOLIOT.get().getDescriptionId() + ".tooltip", "Может использоваться для призыва Фолиота %s");
        this.lang("ru_ru").add(OccultismItems.BOOK_OF_BINDING_DJINNI.get().getDescriptionId() + ".tooltip", "Эта книга пока не связана с Джинни.");
        this.lang("ru_ru").add(OccultismItems.BOOK_OF_BINDING_BOUND_DJINNI.get().getDescriptionId() + ".tooltip", "Может использоваться для призыва Джинни %s");
        this.lang("ru_ru").add(OccultismItems.BOOK_OF_BINDING_AFRIT.get().getDescriptionId() + ".tooltip", "Эта книга пока не связана с Афритом.");
        this.lang("ru_ru").add(OccultismItems.BOOK_OF_BINDING_BOUND_AFRIT.get().getDescriptionId() + ".tooltip", "Может использоваться для призыва Африта %s");
        this.lang("ru_ru").add(OccultismItems.BOOK_OF_BINDING_MARID.get().getDescriptionId() + ".tooltip", "Эта книга пока не связана с Маридом.");
        this.lang("ru_ru").add(OccultismItems.BOOK_OF_BINDING_BOUND_MARID.get().getDescriptionId() + ".tooltip", "Может использоваться для призыва Марида %s");

        this.lang("ru_ru").add("item.occultism.book_of_calling_foliot" + ".tooltip", "Фолиот %s");
        this.lang("ru_ru").add("item.occultism.book_of_calling_foliot" + ".tooltip_dead", "%s покинул эту плоскость существования.");
        this.lang("ru_ru").add("item.occultism.book_of_calling_foliot" + ".tooltip.extract", "Извлекает из: %s.");
        this.lang("ru_ru").add("item.occultism.book_of_calling_foliot" + ".tooltip.deposit", "Кладёт в: %s.");
        this.lang("ru_ru").add("item.occultism.book_of_calling_foliot" + ".tooltip.deposit_entity", "Передаёт предметы в: %s.");
        this.lang("ru_ru").add("item.occultism.book_of_calling_djinni" + ".tooltip", "Джинни %s");
        this.lang("ru_ru").add("item.occultism.book_of_calling_djinni" + ".tooltip_dead", "%s покинул эту плоскость существования.");
        this.lang("ru_ru").add("item.occultism.book_of_calling_djinni" + ".tooltip.extract", "Извлекает из: %s.");
        this.lang("ru_ru").add("item.occultism.book_of_calling_djinni" + ".tooltip.deposit", "Вкладывает в: % s");
        this.lang("ru_ru").add(OccultismItems.FAMILIAR_RING.get().getDescriptionId() + ".tooltip", "Заселено фамильяром: %s\n%s");
        this.lang("ru_ru").add(OccultismItems.FAMILIAR_RING.get().getDescriptionId() + ".tooltip.familiar_type", "[Вид: %s]");
        this.lang("ru_ru").add(OccultismItems.FAMILIAR_RING.get().getDescriptionId() + ".tooltip.empty", "Не содержит какого-либо фамильяра.");

        this.lang("ru_ru").add("item.minecraft.diamond_sword.occultism_spirit_tooltip", "%s связан с этим мечом. Пусть враги дрожат перед его тщеславием.");

        this.lang("ru_ru").add(OccultismItems.STABLE_WORMHOLE.get().getDescriptionId() + ".tooltip.unlinked", "Не связана с Актуатором хранилища.");
        this.lang("ru_ru").add(OccultismItems.STABLE_WORMHOLE.get().getDescriptionId() + ".tooltip.linked", "Связана с Актуатором хранилища в %s.");
        this.lang("ru_ru").add(OccultismItems.STORAGE_REMOTE.get().getDescriptionId() + ".tooltip", "Получает доступ к сетевому хранилищу удалённо.");

        this.lang("ru_ru").add(OccultismItems.STORAGE_REMOTE.get().getDescriptionId() + ".tooltip.linked", "Связан с %s.");
        this.lang("ru_ru").add(OccultismItems.DIVINATION_ROD.get().getDescriptionId() + ".tooltip.no_linked_block", "Не настроен на какой-либо материал.");
        this.lang("ru_ru").add(OccultismItems.DIVINATION_ROD.get().getDescriptionId() + ".tooltip.linked_block", "Настроен к %s.");
        this.lang("ru_ru").add(OccultismItems.DIMENSIONAL_MATRIX.get().getDescriptionId() + ".tooltip", "%s связан с пространственной матрицей.");
        this.lang("ru_ru").add(OccultismItems.INFUSED_PICKAXE.get().getDescriptionId() + ".tooltip", "%s связан с этой киркой.");
        this.lang("ru_ru").add(OccultismItems.MINER_FOLIOT_UNSPECIALIZED.get().getDescriptionId() + ".tooltip", "%s добывает разные блоки в шахтёрском измерении.");
        this.lang("ru_ru").add(OccultismItems.MINER_DJINNI_ORES.get().getDescriptionId() + ".tooltip", "%s добывает разные руды в шахтёрском измерении.");
        this.lang("ru_ru").add(OccultismItems.MINER_DEBUG_UNSPECIALIZED.get().getDescriptionId() + ".tooltip", "Отладочный горняк добывает разные блоки в шахтёрском измерении.");
        this.lang("ru_ru").add(OccultismItems.MINER_AFRIT_DEEPS.get().getDescriptionId() + ".tooltip", "%s добывает разные руды в шахтёрском измерении и глубинносланцевые.");
        this.lang("ru_ru").add(OccultismItems.MINER_MARID_MASTER.get().getDescriptionId() + ".tooltip", "%s добывает разные, глубинносланцевые и редкие руды в шахтёрском измерении.");
        this.lang("ru_ru").add(OccultismItems.SOUL_GEM_ITEM.get().getDescriptionId() + ".tooltip_filled", "Содержит пойманного %s");
        this.lang("ru_ru").add(OccultismItems.SOUL_GEM_ITEM.get().getDescriptionId() + ".tooltip_empty", "Используйте по существу для поимки.");
        this.lang("ru_ru").add(OccultismItems.SATCHEL.get().getDescriptionId() + ".tooltip", "%s связан с этой сумкой.");

        this.lang("ru_ru").add(OccultismItems.SOUL_SHARD_ITEM.get().getDescriptionId() + ".tooltip_filled", "Содержит душу %s.\nМожет использоваться для воскресения.");
        this.lang("ru_ru").add(OccultismItems.SOUL_SHARD_ITEM.get().getDescriptionId() + ".tooltip_empty", "Выпадает с Фамильяра после его преждевременной смерти. Может использоваться для его воскресения.");
    }

    private void addItems() {
        //Notepad++ magic:
        //"item\.occultism\.(.*)": "(.*)"
        //this.addItem\(OccultismItems.\U\1\E, "\2"\);

        this.lang("ru_ru").add("itemGroup.occultism", "Occultism");

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
        this.addItem(OccultismItems.AWAKENED_FEATHER, "Awakened Feather");
        this.addItem(OccultismItems.TABOO_BOOK, "Taboo Book");
        this.addItem(OccultismItems.BOOK_OF_BINDING_EMPTY, "Book of Binding: Empty");
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
        this.addAutoTooltip(OccultismItems.DATURA_SEEDS.get(), "Plant to grow Demon's Dream Fruit.\nConsumption may allow to see beyond the veil ... it may also cause general un-wellness.");
        this.addItem(OccultismItems.DATURA, "Demon's Dream Fruit");
        this.addAutoTooltip(OccultismItems.DATURA.get(), "Consumption may allow to see beyond the veil ... it may also cause general un-wellness.");
        this.addItem(OccultismItems.DEMONS_DREAM_ESSENCE, "Demon's Dream Essence");
        this.addAutoTooltip(OccultismItems.DEMONS_DREAM_ESSENCE.get(), "Consumption allows to see beyond the veil ... and a whole lot of other effects.");
        this.addItem(OccultismItems.OTHERWORLD_ESSENCE, "Otherworld Essence");
        this.addAutoTooltip(OccultismItems.OTHERWORLD_ESSENCE.get(), "Purified Demon's Dream Essence, no longer provides any of the negative effects.");
        this.addItem(OccultismItems.SPIRIT_ATTUNED_GEM, "Spirit Attuned Gem");
        this.lang("ru_ru").add("item.occultism.otherworld_sapling", "Otherworld Sapling");
        this.lang("ru_ru").add("item.occultism.otherworld_sapling_natural", "Unstable Otherworld Sapling");
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
        this.lang("ru_ru").add(OccultismItems.SOUL_GEM_ITEM.get().getDescriptionId() + "_empty", "Empty Soul Gem");
        this.addItem(OccultismItems.SOUL_SHARD_ITEM, "Soul Shard");
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
        this.addItem(OccultismItems.SPAWN_EGG_POSSESSED_GHAST, "Possessed Ghast Spawn Egg");
        this.addItem(OccultismItems.SPAWN_EGG_POSSESSED_PHANTOM, "Possessed Phantom Spawn Egg");
        this.addItem(OccultismItems.SPAWN_EGG_POSSESSED_WEAK_SHULKER, "Possessed Weak Shulker Spawn Egg");
        this.addItem(OccultismItems.SPAWN_EGG_POSSESSED_SHULKER, "Possessed Shulker Spawn Egg");
        this.addItem(OccultismItems.SPAWN_EGG_POSSESSED_ELDER_GUARDIAN, "Possessed Elder Guardian Spawn Egg");
        this.addItem(OccultismItems.SPAWN_EGG_POSSESSED_WARDEN, "Possessed Warden Spawn Egg");
        this.addItem(OccultismItems.SPAWN_EGG_POSSESSED_HOGLIN, "Possessed Hoglin Spawn Egg");
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
        this.addItem(OccultismItems.SPAWN_EGG_PARROT_FAMILIAR, "Parrot Familiar Spawn Egg");
        this.addItem(OccultismItems.SPAWN_EGG_DEMONIC_WIFE, "Demonic Wife Spawn Egg");
        this.addItem(OccultismItems.SPAWN_EGG_DEMONIC_HUSBAND, "Demonic Husband Spawn Egg");
        this.addItem(OccultismItems.SPAWN_EGG_WILD_HORDE_HUSK, "Wild Horde Husk Spawn Egg");
        this.addItem(OccultismItems.SPAWN_EGG_WILD_HORDE_DROWNED, "Wild Horde Drowned Spawn Egg");
        this.addItem(OccultismItems.SPAWN_EGG_WILD_HORDE_CREEPER, "Wild Horde Creeper Spawn Egg");
        this.addItem(OccultismItems.SPAWN_EGG_WILD_HORDE_SILVERFISH, "Wild Horde Silverfish Spawn Egg");
        this.addItem(OccultismItems.SPAWN_EGG_POSSESSED_WEAK_BREEZE, "Possessed Weak Breeze Spawn Egg");
        this.addItem(OccultismItems.SPAWN_EGG_POSSESSED_BREEZE, "Possessed Breeze Spawn Egg");
        this.addItem(OccultismItems.SPAWN_EGG_POSSESSED_STRONG_BREEZE, "Possessed Strong Breeze Spawn Egg");
        this.addItem(OccultismItems.SPAWN_EGG_POSSESSED_EVOKER, "Possessed Evoker Spawn Egg");
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

        this.lang("ru_ru").add("block.occultism.otherworld_log", "Otherworld Wood");
        this.lang("ru_ru").add("block.occultism.otherworld_sapling", "Otherworld Sapling");
        this.lang("ru_ru").add("block.occultism.otherworld_leaves", "Otherworld Leaves");

        this.addBlock(OccultismBlocks.SPIRIT_FIRE, "Spiritfire");
        this.addBlock(OccultismBlocks.SPIRIT_ATTUNED_CRYSTAL, "Spirit Attuned Crystal");
        this.addBlock(OccultismBlocks.CANDLE_WHITE, "White Candle");
        this.addBlock(OccultismBlocks.SILVER_ORE, "Silver Ore");
        this.addBlock(OccultismBlocks.SILVER_ORE_DEEPSLATE, "Deepslate Silver Ore");
        this.addBlock(OccultismBlocks.IESNIUM_ORE, "Iesnium Ore");
        this.addBlock(OccultismBlocks.SILVER_BLOCK, "Block of Silver");
        this.addBlock(OccultismBlocks.IESNIUM_BLOCK, "Block of Iesnium");
        this.addBlock(OccultismBlocks.RAW_SILVER_BLOCK, "Block of Raw Silver");
        this.addBlock(OccultismBlocks.RAW_IESNIUM_BLOCK, "Block of Raw Iesnium");
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
        this.addEntityType(OccultismEntities.POSSESSED_PHANTOM, "Possessed Phantom");
        this.addEntityType(OccultismEntities.POSSESSED_WEAK_SHULKER, "Possessed Weak Shulker");
        this.addEntityType(OccultismEntities.POSSESSED_SHULKER, "Possessed Shulker");
        this.addEntityType(OccultismEntities.POSSESSED_ELDER_GUARDIAN, "Possessed Elder Guardian");
        this.addEntityType(OccultismEntities.POSSESSED_WARDEN, "Possessed Warden");
        this.addEntityType(OccultismEntities.POSSESSED_HOGLIN, "Possessed Hoglin");
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
        this.addEntityType(OccultismEntities.DEMONIC_WIFE, "Demonic Wife");
        this.addEntityType(OccultismEntities.DEMONIC_HUSBAND, "Demonic Husband");
        this.addEntityType(OccultismEntities.WILD_HORDE_HUSK, "Wild Horde Husk");
        this.addEntityType(OccultismEntities.WILD_HORDE_DROWNED, "Wild Horde Drowned");
        this.addEntityType(OccultismEntities.WILD_HORDE_CREEPER, "Wild Horde Creeper");
        this.addEntityType(OccultismEntities.WILD_HORDE_SILVERFISH, "Wild Horde Silverfish");
        this.addEntityType(OccultismEntities.POSSESSED_WEAK_BREEZE, "Possessed Weak Breeze");
        this.addEntityType(OccultismEntities.POSSESSED_BREEZE, "Possessed Breeze");
        this.addEntityType(OccultismEntities.POSSESSED_STRONG_BREEZE, "Possessed Strong Breeze");
        this.addEntityType(OccultismEntities.WILD_ZOMBIE, "Wild Zombie");
        this.addEntityType(OccultismEntities.WILD_SKELETON, "Wild Skeleton");
        this.addEntityType(OccultismEntities.WILD_SILVERFISH, "Wild Silverfish");
        this.addEntityType(OccultismEntities.WILD_SPIDER, "Wild Spider");
        this.addEntityType(OccultismEntities.WILD_BOGGED, "Wild Bogged");
        this.addEntityType(OccultismEntities.WILD_SLIME, "Wild Slime");
        this.addEntityType(OccultismEntities.WILD_HUSK, "Wild Husk");
        this.addEntityType(OccultismEntities.WILD_STRAY, "Wild Stray");
        this.addEntityType(OccultismEntities.WILD_CAVE_SPIDER, "Wild Cave Spider");
        this.addEntityType(OccultismEntities.POSSESSED_EVOKER, "Possessed Evoker");
    }

    private void addMiscTranslations() {

        //"(.*?)": "(.*)",
        //this.lang("ru_ru").add\("\1", "\2"\);

        this.lang("ru_ru").add(TranslationKeys.HUD_NO_PENTACLE_FOUND, "No valid pentacle found.");
        this.lang("ru_ru").add(TranslationKeys.HUD_PENTACLE_FOUND, "Current Pentacle: %s");

        this.lang("ru_ru").add(TranslationKeys.MESSAGE_CONTAINER_ALREADY_OPEN, "This container is already opened by another player, wait until they close it.");

        //Занятия
        this.lang("ru_ru").add("job.occultism.lumberjack", "Дровосек");
        this.lang("ru_ru").add("job.occultism.crush_tier1", "Медленный дробильщик");
        this.lang("ru_ru").add("job.occultism.crush_tier2", "Дробильщик");
        this.lang("ru_ru").add("job.occultism.crush_tier3", "Быстрый дробильщик");
        this.lang("ru_ru").add("job.occultism.crush_tier4", "Очень быстрый дробильщик");
        this.lang("ru_ru").add("job.occultism.manage_machine", "Станочник");
        this.lang("ru_ru").add("job.occultism.transport_items", "Транспортировщик");
        this.lang("ru_ru").add("job.occultism.cleaner", "Дворник");
        this.lang("ru_ru").add("job.occultism.trade_otherstone_t1", "Торговец потусторонним камнем");
        this.lang("ru_ru").add("job.occultism.trade_otherworld_saplings_t1", "Торговец потусторонними саженцами");
        this.lang("ru_ru").add("job.occultism.clear_weather", "Дух солнечной погоды");
        this.lang("ru_ru").add("job.occultism.rain_weather", "Дух дождливой погоды");
        this.lang("ru_ru").add("job.occultism.thunder_weather", "Дух грозы");
        this.lang("ru_ru").add("job.occultism.day_time", "Дух рассвета");
        this.lang("ru_ru").add("job.occultism.night_time", "Дух сумерок");

        //Подсчёт
        this.lang("ru_ru").add("enum.occultism.facing.up", "Верх");
        this.lang("ru_ru").add("enum.occultism.facing.down", "Низ");
        this.lang("ru_ru").add("enum.occultism.facing.north", "Север");
        this.lang("ru_ru").add("enum.occultism.facing.south", "Юг");
        this.lang("ru_ru").add("enum.occultism.facing.west", "Запад");
        this.lang("ru_ru").add("enum.occultism.facing.east", "Восток");
        this.lang("ru_ru").add("enum.occultism.book_of_calling.item_mode.set_deposit", "Установить вклад");
        this.lang("ru_ru").add("enum.occultism.book_of_calling.item_mode.set_extract", "Установить извлечение");
        this.lang("ru_ru").add("enum.occultism.book_of_calling.item_mode.set_base", "Установить место базы");
        this.lang("ru_ru").add("enum.occultism.book_of_calling.item_mode.set_storage_controller", "Установить актуатор хранилища");
        this.lang("ru_ru").add("enum.occultism.book_of_calling.item_mode.set_managed_machine", "Установить управляемое устройство");
        this.lang("ru_ru").add("enum.occultism.work_area_size.small", "16х16");
        this.lang("ru_ru").add("enum.occultism.work_area_size.medium", "32х32");
        this.lang("ru_ru").add("enum.occultism.work_area_size.large", "64х64");

        //Сообщения отладки
        this.lang("ru_ru").add("debug.occultism.debug_wand.printed_glyphs", "Глифы отпечатаны.");
        this.lang("ru_ru").add("debug.occultism.debug_wand.glyphs_verified", "Глифы проверены.");
        this.lang("ru_ru").add("debug.occultism.debug_wand.glyphs_not_verified", "Глифы не проверены.");
        this.lang("ru_ru").add("debug.occultism.debug_wand.spirit_selected", "Дух с идентификатором %s выбран.");
        this.lang("ru_ru").add("debug.occultism.debug_wand.spirit_tamed", "Дух с идентификатором %s приручен.");
        this.lang("ru_ru").add("debug.occultism.debug_wand.deposit_selected", "Установить блок %s для вклада, сторона «%s»");
        this.lang("ru_ru").add("debug.occultism.debug_wand.no_spirit_selected", "Дух не выбран.");

        //Ритуальные жертвоприношения
        this.lang("ru_ru").add("ritual.occultism.sacrifice.cows", "Корова");
        this.lang("ru_ru").add("ritual.occultism.sacrifice.bats", "Летучая мышь");
        this.lang("ru_ru").add("ritual.occultism.sacrifice.zombies", "Зомби");
        this.lang("ru_ru").add("ritual.occultism.sacrifice.parrots", "Попугай");
        this.lang("ru_ru").add("ritual.occultism.sacrifice.chicken", "Курица");
        this.lang("ru_ru").add("ritual.occultism.sacrifice.pigs", "Свиньи");
        this.lang("ru_ru").add("ritual.occultism.sacrifice.humans", "Крестьянин и Игрок");
        this.lang("ru_ru").add("ritual.occultism.sacrifice.squid", "Спрут");
        this.lang("ru_ru").add("ritual.occultism.sacrifice.horses", "Лошадь");
        this.lang("ru_ru").add("ritual.occultism.sacrifice.sheep", "Овца");
        this.lang("ru_ru").add("ritual.occultism.sacrifice.llamas", "Лама");
        this.lang("ru_ru").add("ritual.occultism.sacrifice.snow_golem", "Снежный голем");
        this.lang("ru_ru").add("ritual.occultism.sacrifice.iron_golem", "Железный голем");
        this.lang("ru_ru").add("ritual.occultism.sacrifice.spiders", "Паук");
        this.lang("ru_ru").add("ritual.occultism.sacrifice.flying_passive", "Тихоня, летучая мышь, пчела или попугай");
        this.lang("ru_ru").add("ritual.occultism.sacrifice.cubemob", "Слизень или Магмовый куб");
        this.lang("ru_ru").add("ritual.occultism.sacrifice.fish", "Любаяр рыба");
        this.lang("ru_ru").add("ritual.occultism.sacrifice.axolotls", "Аксолотль");
        this.lang("ru_ru").add("ritual.occultism.sacrifice.camel", "Верблюд");
        this.lang("ru_ru").add("ritual.occultism.sacrifice.dolphin", "Дельфин");
        this.lang("ru_ru").add("ritual.occultism.sacrifice.wolfs", "Волк");
        this.lang("ru_ru").add("ritual.occultism.sacrifice.ocelot", "Оцелот");
        this.lang("ru_ru").add("ritual.occultism.sacrifice.vex", "Вредина");

        //Сообщение сети
        this.lang("ru_ru").add("network.messages.occultism.request_order.order_received", "Заказ получен!");

        //Эффекты
        this.lang("ru_ru").add("effect.occultism.third_eye", "Третий глаз");
        this.lang("ru_ru").add("effect.occultism.double_jump", "Мульти-прыжок");
        this.lang("ru_ru").add("effect.occultism.dragon_greed", "Алчность дракона");
        this.lang("ru_ru").add("effect.occultism.mummy_dodge", "Уклонение");
        this.lang("ru_ru").add("effect.occultism.bat_lifesteal", "Похищение жизни");
        this.lang("ru_ru").add("effect.occultism.beaver_harvest", "Бобровая жатва");
        this.lang("ru_ru").add("effect.occultism.step_height", "Высокая поступь");

        //Звуки
        this.lang("ru_ru").add("occultism.subtitle.chalk", "Мел");
        this.lang("ru_ru").add("occultism.subtitle.brush", "Щётка для мела");
        this.lang("ru_ru").add("occultism.subtitle.start_ritual", "Начать ритуал");
        this.lang("ru_ru").add("occultism.subtitle.tuning_fork", "Звук камертона");
        this.lang("ru_ru").add("occultism.subtitle.crunching", "Размельчение");
        this.lang("ru_ru").add("occultism.subtitle.poof", "Вжух!");
    }

    private void addGuiTranslations() {
        this.lang("ru_ru").add("gui.occultism.book_of_calling.mode", "Режим");
        this.lang("ru_ru").add("gui.occultism.book_of_calling.work_area", "Рабочее место");
        this.lang("ru_ru").add("gui.occultism.book_of_calling.manage_machine.insert", "Сторона вклада");
        this.lang("ru_ru").add("gui.occultism.book_of_calling.manage_machine.extract", "Сторона извлечения");
        this.lang("ru_ru").add("gui.occultism.book_of_calling.manage_machine.custom_name", "Польз. название");

        // Spirit GUI
        this.lang("ru_ru").add("gui.occultism.spirit.age", "Распад эссенции: %d%%");
        this.lang("ru_ru").add("gui.occultism.spirit.job", "%s");

        // Spirit Transporter GUI
        this.lang("ru_ru").add("gui.occultism.spirit.transporter.filter_mode", "Режим фильтра");
        this.lang("ru_ru").add("gui.occultism.spirit.transporter.filter_mode.blacklist", "Чёрный список");
        this.lang("ru_ru").add("gui.occultism.spirit.transporter.filter_mode.whitelist", "Белый список");
        this.lang("ru_ru").add("gui.occultism.spirit.transporter.tag_filter", "Введите теги для фильтрации по разделённым символам \";\".\nНапр.: \"forge:ores;*брёвна*\".\nИспользуйте \"*\" для соответствия любого символа, напр.: \"*руда*\" для соответствия тегов руд из любого мода. Для фильтрации предметов, префикс с идентификатором предмета \"item:\", напр.: \"item:minecraft:chest\".");

        // Storage Controller GUI
        this.lang("ru_ru").add("gui.occultism.storage_controller.space_info_label", "%d/%d");
        this.lang("ru_ru").add("gui.occultism.storage_controller.space_info_label_new", "%s%% заполнено.");
        this.lang("ru_ru").add("gui.occultism.storage_controller.space_info_label_types", "%s%% типов.");
        this.lang("ru_ru").add("gui.occultism.storage_controller.shift", "Удерживайте Shift для подробностей.");
        this.lang("ru_ru").add("gui.occultism.storage_controller.search.tooltip@", "Префикс @: поиск по идентификатору мода.");
        this.lang("ru_ru").add("gui.occultism.storage_controller.search.tooltip#", "Префикс #: поиск по подсказке предмета.");
        this.lang("ru_ru").add("gui.occultism.storage_controller.search.tooltip$", "Префикс $: поиск по тегу.");
        this.lang("ru_ru").add("gui.occultism.storage_controller.search.tooltip_rightclick", "Очистка текста при помощи ПКМ.");
        this.lang("ru_ru").add("gui.occultism.storage_controller.search.tooltip_clear", "Очистить поиск.");
        this.lang("ru_ru").add("gui.occultism.storage_controller.search.tooltip_jei_on", "Синхронизировать с JEI.");
        this.lang("ru_ru").add("gui.occultism.storage_controller.search.tooltip_jei_off", "Не синхронизировать с JEI.");
        this.lang("ru_ru").add("gui.occultism.storage_controller.search.tooltip_sort_type_amount", "Сортировать по количеству.");
        this.lang("ru_ru").add("gui.occultism.storage_controller.search.tooltip_sort_type_name", "Сортировать по названию предмета.");
        this.lang("ru_ru").add("gui.occultism.storage_controller.search.tooltip_sort_type_mod", "Сортировать по названию мода.");
        this.lang("ru_ru").add("gui.occultism.storage_controller.search.tooltip_sort_direction_down", "Сортировать по возрастанию.");
        this.lang("ru_ru").add("gui.occultism.storage_controller.search.tooltip_sort_direction_up", "Сортировать по убыванию.");
        this.lang("ru_ru").add("gui.occultism.storage_controller.search.machines.tooltip@", "Префикс @: поиск по идентификатору мода.");
        this.lang("ru_ru").add("gui.occultism.storage_controller.search.machines.tooltip_sort_type_amount", "Сортировать по расстоянию.");
        this.lang("ru_ru").add("gui.occultism.storage_controller.search.machines.tooltip_sort_type_name", "Сортировать по названии устройства.");
        this.lang("ru_ru").add("gui.occultism.storage_controller.search.machines.tooltip_sort_type_mod", "Сортировать по названию мода.");
    }

    private void addRitualMessages() {
        this.lang("ru_ru").add("ritual.occultism.pentacle_help", "\u00a7lНеверный пентакль!\u00a7r\nВы было пытаетесь создать пентакль: %s? Отсутствует:\n%s");
        this.lang("ru_ru").add("ritual.occultism.pentacle_help_at_glue", " на позиции ");
        this.lang("ru_ru").add("ritual.occultism.pentacle_help.no_pentacle", "\u00a7lПентакль не найден!\u00a7r\nКажется, Вы не начертили пентакль, или в вашем пентакле отсутствуют важные элементы. Обратитесь в Справочник душ, раздел \"Ритуалы\", необходимый пентакль будет отображён как гиперссылка над рецептом ритуала в странице ритуала.");
        this.lang("ru_ru").add("ritual.occultism.ritual_help", "\u00a7lНеправильный ритуал!\u00a7r\nВы было пытались выполнить ритуал:: \"%s\"? Отсутствуют предметы:\n%s");
        this.lang("ru_ru").add("ritual.occultism.disabled", "Этот ритуал недоступен на сервере.");
        this.lang("ru_ru").add("ritual.occultism.does_not_exist", "\u00a7lНеизвестный ритуал\u00a7r. Убедитесь, что пентакли и ингредиенты расположены правильно. Если Вы до сих пор не достигли желаемого результата, присоединяйтесь к нашему Discord-серверу —> https://invite.gg/klikli");
        this.lang("ru_ru").add("ritual.occultism.book_not_bound", "\u00a7lКнига призыва (несвязанная)\u00a7r. Вы должны создать эту книгу, используя «Справочник душ», чтобы связать её с духом перед началом ритуала..");

        this.lang("ru_ru").add("ritual.occultism.debug.conditions", "Не все требования удовлетворены для этого ритуала.");
        this.lang("ru_ru").add("ritual.occultism.debug.started", "Ритуал запущен.");
        this.lang("ru_ru").add("ritual.occultism.debug.finished", "Ритуал полностью завершился.");
        this.lang("ru_ru").add("ritual.occultism.debug.interrupted", "Ритуал прерван.");

        this.lang("ru_ru").add("ritual.occultism.summon_foliot_lumberjack.conditions", "Не все требования удовлетворены для этого ритуала.");
        this.lang("ru_ru").add("ritual.occultism.summon_foliot_lumberjack.started", "Начался призыв foliot lumberjack.");
        this.lang("ru_ru").add("ritual.occultism.summon_foliot_lumberjack.finished", "Summoned foliot lumberjack successfully.");
        this.lang("ru_ru").add("ritual.occultism.summon_foliot_lumberjack.interrupted", "Summoning of foliot lumberjack interrupted.");
        this.lang("ru_ru").add("ritual.occultism.summon_foliot_transport_items.conditions", "Не все требования удовлетворены для этого ритуала.");
        this.lang("ru_ru").add("ritual.occultism.summon_foliot_transport_items.started", "Начался призыв foliot transporter.");
        this.lang("ru_ru").add("ritual.occultism.summon_foliot_transport_items.finished", "Summoned foliot transporter successfully.");
        this.lang("ru_ru").add("ritual.occultism.summon_foliot_transport_items.interrupted", "Summoning of foliot transporter interrupted.");
        this.lang("ru_ru").add("ritual.occultism.summon_foliot_cleaner.conditions", "Не все требования удовлетворены для этого ритуала.");
        this.lang("ru_ru").add("ritual.occultism.summon_foliot_cleaner.started", "Начался призыв foliot janitor.");
        this.lang("ru_ru").add("ritual.occultism.summon_foliot_cleaner.finished", "Summoned foliot janitor successfully.");
        this.lang("ru_ru").add("ritual.occultism.summon_foliot_cleaner.interrupted", "Summoning of janitor transporter interrupted.");
        this.lang("ru_ru").add("ritual.occultism.summon_foliot_crusher.conditions", "Не все требования удовлетворены для этого ритуала.");
        this.lang("ru_ru").add("ritual.occultism.summon_foliot_crusher.started", "Начался призыв foliot ore crusher.");
        this.lang("ru_ru").add("ritual.occultism.summon_foliot_crusher.finished", "Summoned foliot ore crusher successfully.");
        this.lang("ru_ru").add("ritual.occultism.summon_foliot_crusher.interrupted", "Summoning of foliot ore crusher interrupted.");
        this.lang("ru_ru").add("ritual.occultism.summon_djinni_crusher.conditions", "Не все требования удовлетворены для этого ритуала.");
        this.lang("ru_ru").add("ritual.occultism.summon_djinni_crusher.started", "Начался призыв djinni ore crusher.");
        this.lang("ru_ru").add("ritual.occultism.summon_djinni_crusher.finished", "Summoned djinni ore crusher successfully.");
        this.lang("ru_ru").add("ritual.occultism.summon_djinni_crusher.interrupted", "Summoning of djinni ore crusher interrupted.");
        this.lang("ru_ru").add("ritual.occultism.summon_afrit_crusher.conditions", "Не все требования удовлетворены для этого ритуала.");
        this.lang("ru_ru").add("ritual.occultism.summon_afrit_crusher.started", "Начался призыв afrit ore crusher.");
        this.lang("ru_ru").add("ritual.occultism.summon_afrit_crusher.finished", "Summoned afrit ore crusher successfully.");
        this.lang("ru_ru").add("ritual.occultism.summon_afrit_crusher.interrupted", "Summoning of afrit ore crusher interrupted.");
        this.lang("ru_ru").add("ritual.occultism.summon_marid_crusher.conditions", "Не все требования удовлетворены для этого ритуала.");
        this.lang("ru_ru").add("ritual.occultism.summon_marid_crusher.started", "Начался призыв marid ore crusher.");
        this.lang("ru_ru").add("ritual.occultism.summon_marid_crusher.finished", "Summoned marid ore crusher successfully.");
        this.lang("ru_ru").add("ritual.occultism.summon_marid_crusher.interrupted", "Summoning of marid ore crusher interrupted.");
        this.lang("ru_ru").add("ritual.occultism.summon_foliot_sapling_trader.conditions", "Не все требования удовлетворены для этого ритуала.");
        this.lang("ru_ru").add("ritual.occultism.summon_foliot_sapling_trader.started", "Начался призыв foliot otherworld sapling trader.");
        this.lang("ru_ru").add("ritual.occultism.summon_foliot_sapling_trader.finished", "Summoned foliot otherworld sapling successfully.");
        this.lang("ru_ru").add("ritual.occultism.summon_foliot_sapling_trader.interrupted", "Summoning of foliot otherworld sapling trader interrupted.");
        this.lang("ru_ru").add("ritual.occultism.summon_foliot_otherstone_trader.conditions", "Не все требования удовлетворены для этого ритуала.");
        this.lang("ru_ru").add("ritual.occultism.summon_foliot_otherstone_trader.started", "Начался призыв foliot otherstone trader.");
        this.lang("ru_ru").add("ritual.occultism.summon_foliot_otherstone_trader.finished", "Summoned foliot otherstone trader successfully.");
        this.lang("ru_ru").add("ritual.occultism.summon_foliot_otherstone_trader.interrupted", "Summoning of foliot otherstone trader interrupted.");
        this.lang("ru_ru").add("ritual.occultism.summon_djinni_manage_machine.conditions", "Не все требования удовлетворены для этого ритуала.");
        this.lang("ru_ru").add("ritual.occultism.summon_djinni_manage_machine.started", "Начался призыв djinni machine operator.");
        this.lang("ru_ru").add("ritual.occultism.summon_djinni_manage_machine.finished", "Summoned djinni machine operator successfully.");
        this.lang("ru_ru").add("ritual.occultism.summon_djinni_manage_machine.interrupted", "Summoning of djinni machine operator interrupted.");
        this.lang("ru_ru").add("ritual.occultism.summon_djinni_clear_weather.conditions", "Не все требования удовлетворены для этого ритуала.");
        this.lang("ru_ru").add("ritual.occultism.summon_djinni_clear_weather.started", "Начался призыв djinni to clear weather.");
        this.lang("ru_ru").add("ritual.occultism.summon_djinni_clear_weather.finished", "Summoned djinni successfully.");
        this.lang("ru_ru").add("ritual.occultism.summon_djinni_clear_weather.interrupted", "Summoning of djinni interrupted.");
        this.lang("ru_ru").add("ritual.occultism.summon_djinni_day_time.conditions", "Не все требования удовлетворены для этого ритуала.");
        this.lang("ru_ru").add("ritual.occultism.summon_djinni_day_time.started", "Начался призыв djinni to set time to day.");
        this.lang("ru_ru").add("ritual.occultism.summon_djinni_day_time.finished", "Summoned djinni successfully.");
        this.lang("ru_ru").add("ritual.occultism.summon_djinni_day_time.interrupted", "Summoning of djinni interrupted.");
        this.lang("ru_ru").add("ritual.occultism.summon_djinni_night_time.conditions", "Не все требования удовлетворены для этого ритуала.");
        this.lang("ru_ru").add("ritual.occultism.summon_djinni_night_time.started", "Начался призыв djinni to set time to night.");
        this.lang("ru_ru").add("ritual.occultism.summon_djinni_night_time.finished", "Summoned djinni successfully.");
        this.lang("ru_ru").add("ritual.occultism.summon_djinni_night_time.interrupted", "Summoning of djinni interrupted.");
        this.lang("ru_ru").add("ritual.occultism.summon_afrit_rain_weather.conditions", "Не все требования удовлетворены для этого ритуала.");
        this.lang("ru_ru").add("ritual.occultism.summon_afrit_rain_weather.started", "Начался призыв afrit for rainy weather.");
        this.lang("ru_ru").add("ritual.occultism.summon_afrit_rain_weather.finished", "Summoned afrit successfully.");
        this.lang("ru_ru").add("ritual.occultism.summon_afrit_rain_weather.interrupted", "Summoning of afrit interrupted.");
        this.lang("ru_ru").add("ritual.occultism.summon_afrit_thunder_weather.conditions", "Не все требования удовлетворены для этого ритуала.");
        this.lang("ru_ru").add("ritual.occultism.summon_afrit_thunder_weather.started", "Начался призыв Африта для грозы.");
        this.lang("ru_ru").add("ritual.occultism.summon_afrit_thunder_weather.finished", "Африт успешно призван.");
        this.lang("ru_ru").add("ritual.occultism.summon_afrit_thunder_weather.interrupted", "Призыв Африта прерван.");
        this.lang("ru_ru").add("ritual.occultism.summon_wild_afrit.conditions", "Не все требования удовлетворены для этого ритуала.");
        this.lang("ru_ru").add("ritual.occultism.summon_wild_afrit.started", "Начался призыв несвязанного африта.");
        this.lang("ru_ru").add("ritual.occultism.summon_wild_afrit.finished", "Несвязанный Африт успешно призван.");
        this.lang("ru_ru").add("ritual.occultism.summon_wild_afrit.interrupted", "Призыв несвязанного Африта прерван.");
        this.lang("ru_ru").add("ritual.occultism.summon_wild_hunt.conditions", "Не все требования удовлетворены для этого ритуала.");
        this.lang("ru_ru").add("ritual.occultism.summon_wild_hunt.started", "Начался призыв Дикой Охоты.");
        this.lang("ru_ru").add("ritual.occultism.summon_wild_hunt.finished", "Дикая Охота успешно призвана.");
        this.lang("ru_ru").add("ritual.occultism.summon_wild_hunt.interrupted", "Summoning of the wild hunt interrupted.");
        this.lang("ru_ru").add("ritual.occultism.craft_dimensional_matrix.conditions", "Не все требования удовлетворены для этого ритуала.");
        this.lang("ru_ru").add("ritual.occultism.craft_dimensional_matrix.started", "Началось связывание Джинни в пространственную матрицу.");
        this.lang("ru_ru").add("ritual.occultism.craft_dimensional_matrix.finished", "Успешно заточил Джинни в пространственную матрицу.");
        this.lang("ru_ru").add("ritual.occultism.craft_dimensional_matrix.interrupted", "Binding of djinni interrupted.");
        this.lang("ru_ru").add("ritual.occultism.craft_dimensional_mineshaft.conditions", "Не все требования удовлетворены для этого ритуала.");
        this.lang("ru_ru").add("ritual.occultism.craft_dimensional_mineshaft.started", "Started binding djinni into dimensional mineshaft.");
        this.lang("ru_ru").add("ritual.occultism.craft_dimensional_mineshaft.finished", "Successfully bound djinni into dimensional mineshaft.");
        this.lang("ru_ru").add("ritual.occultism.craft_dimensional_mineshaft.interrupted", "Binding of djinni interrupted.");
        this.lang("ru_ru").add("ritual.occultism.craft_storage_controller_base.conditions", "Не все требования удовлетворены для этого ритуала.");
        this.lang("ru_ru").add("ritual.occultism.craft_storage_controller_base.started", "Started binding foliot into storage actuator base.");
        this.lang("ru_ru").add("ritual.occultism.craft_storage_controller_base.finished", "Successfully bound foliot into storage actuator base.");
        this.lang("ru_ru").add("ritual.occultism.craft_storage_controller_base.interrupted", "Binding of foliot interrupted.");
        this.lang("ru_ru").add("ritual.occultism.craft_stabilizer_tier1.conditions", "Не все требования удовлетворены для этого ритуала.");
        this.lang("ru_ru").add("ritual.occultism.craft_stabilizer_tier1.started", "Started binding foliot into storage stabilizer.");
        this.lang("ru_ru").add("ritual.occultism.craft_stabilizer_tier1.finished", "Successfully bound foliot into storage stabilizer.");
        this.lang("ru_ru").add("ritual.occultism.craft_stabilizer_tier1.interrupted", "Binding of foliot interrupted.");
        this.lang("ru_ru").add("ritual.occultism.craft_stabilizer_tier2.conditions", "Не все требования удовлетворены для этого ритуала.");
        this.lang("ru_ru").add("ritual.occultism.craft_stabilizer_tier2.started", "Started binding djinni into storage stabilizer.");
        this.lang("ru_ru").add("ritual.occultism.craft_stabilizer_tier2.finished", "Successfully bound djinni into storage stabilizer.");
        this.lang("ru_ru").add("ritual.occultism.craft_stabilizer_tier2.interrupted", "Binding of djinni interrupted.");
        this.lang("ru_ru").add("ritual.occultism.craft_stabilizer_tier3.conditions", "Не все требования удовлетворены для этого ритуала.");
        this.lang("ru_ru").add("ritual.occultism.craft_stabilizer_tier3.started", "Started binding afrit into storage stabilizer.");
        this.lang("ru_ru").add("ritual.occultism.craft_stabilizer_tier3.finished", "Successfully bound afrit into storage stabilizer.");
        this.lang("ru_ru").add("ritual.occultism.craft_stabilizer_tier3.interrupted", "Binding of afrit interrupted.");
        this.lang("ru_ru").add("ritual.occultism.craft_stabilizer_tier4.conditions", "Не все требования удовлетворены для этого ритуала.");
        this.lang("ru_ru").add("ritual.occultism.craft_stabilizer_tier4.started", "Started binding marid into storage stabilizer.");
        this.lang("ru_ru").add("ritual.occultism.craft_stabilizer_tier4.finished", "Successfully bound marid into storage stabilizer.");
        this.lang("ru_ru").add("ritual.occultism.craft_stabilizer_tier4.interrupted", "Binding of marid interrupted.");
        this.lang("ru_ru").add("ritual.occultism.craft_stable_wormhole.conditions", "Не все требования удовлетворены для этого ритуала.");
        this.lang("ru_ru").add("ritual.occultism.craft_stable_wormhole.started", "Started binding foliot into wormhole frame.");
        this.lang("ru_ru").add("ritual.occultism.craft_stable_wormhole.finished", "Successfully bound foliot into wormhole frame.");
        this.lang("ru_ru").add("ritual.occultism.craft_stable_wormhole.interrupted", "Binding of foliot interrupted.");
        this.lang("ru_ru").add("ritual.occultism.craft_storage_remote.conditions", "Не все требования удовлетворены для этого ритуала.");
        this.lang("ru_ru").add("ritual.occultism.craft_storage_remote.started", "Started binding djinni into storage remote.");
        this.lang("ru_ru").add("ritual.occultism.craft_storage_remote.finished", "Successfully bound djinni into storage remote.");
        this.lang("ru_ru").add("ritual.occultism.craft_storage_remote.interrupted", "Binding of djinni interrupted.");
        this.lang("ru_ru").add("ritual.occultism.craft_infused_lenses.conditions", "Не все требования удовлетворены для этого ритуала.");
        this.lang("ru_ru").add("ritual.occultism.craft_infused_lenses.started", "Started binding foliot into lenses.");
        this.lang("ru_ru").add("ritual.occultism.craft_infused_lenses.finished", "Successfully bound foliot into lenses.");
        this.lang("ru_ru").add("ritual.occultism.craft_infused_lenses.interrupted", "Binding of foliot interrupted.");
        this.lang("ru_ru").add("ritual.occultism.craft_infused_pickaxe.conditions", "Не все требования удовлетворены для этого ритуала.");
        this.lang("ru_ru").add("ritual.occultism.craft_infused_pickaxe.started", "Started binding djinni into pickaxe.");
        this.lang("ru_ru").add("ritual.occultism.craft_infused_pickaxe.finished", "Successfully bound djinni into pickaxe.");
        this.lang("ru_ru").add("ritual.occultism.craft_infused_pickaxe.interrupted", "Binding of djinni interrupted.");

        this.lang("ru_ru").add("ritual.occultism.craft_miner_foliot_unspecialized.conditions", "Не все требования удовлетворены для этого ритуала.");
        this.lang("ru_ru").add("ritual.occultism.craft_miner_foliot_unspecialized.started", "Начался призыв foliot в магическую лампу");
        this.lang("ru_ru").add("ritual.occultism.craft_miner_foliot_unspecialized.finished", "Successfully summoned foliot в магическую лампу");
        this.lang("ru_ru").add("ritual.occultism.craft_miner_foliot_unspecialized.interrupted", "Summoning of foliot interrupted.");

        this.lang("ru_ru").add("ritual.occultism.craft_miner_djinni_ores.conditions", "Не все требования удовлетворены для этого ритуала.");
        this.lang("ru_ru").add("ritual.occultism.craft_miner_djinni_ores.started", "Начался призыв djinni в магическую лампу");
        this.lang("ru_ru").add("ritual.occultism.craft_miner_djinni_ores.finished", "Successfully summoned djinni в магическую лампу");
        this.lang("ru_ru").add("ritual.occultism.craft_miner_djinni_ores.interrupted", "Summoning of djinni interrupted.");

        this.lang("ru_ru").add("ritual.occultism.craft_miner_afrit_deeps.conditions", "Не все требования удовлетворены для этого ритуала.");
        this.lang("ru_ru").add("ritual.occultism.craft_miner_afrit_deeps.started", "Начался призыв afrit в магическую лампу");
        this.lang("ru_ru").add("ritual.occultism.craft_miner_afrit_deeps.finished", "Successfully summoned afrit в магическую лампу");
        this.lang("ru_ru").add("ritual.occultism.craft_miner_afrit_deeps.interrupted", "Summoning of afrit interrupted.");

        this.lang("ru_ru").add("ritual.occultism.craft_miner_marid_master.conditions", "Не все требования удовлетворены для этого ритуала.");
        this.lang("ru_ru").add("ritual.occultism.craft_miner_marid_master.started", "Начался призыв marid в магическую лампу");
        this.lang("ru_ru").add("ritual.occultism.craft_miner_marid_master.finished", "Successfully summoned marid в магическую лампу");
        this.lang("ru_ru").add("ritual.occultism.craft_miner_marid_master.interrupted", "Призыв Марида прерван.");

        this.lang("ru_ru").add("ritual.occultism.craft_satchel.conditions", "Не все требования удовлетворены для этого ритуала.");
        this.lang("ru_ru").add("ritual.occultism.craft_satchel.started", "Started binding foliot into satchel.");
        this.lang("ru_ru").add("ritual.occultism.craft_satchel.finished", "Successfully bound foliot into satchel.");
        this.lang("ru_ru").add("ritual.occultism.craft_satchel.interrupted", "Binding of foliot interrupted.");
        this.lang("ru_ru").add("ritual.occultism.craft_soul_gem.conditions", "Не все требования удовлетворены для этого ритуала.");
        this.lang("ru_ru").add("ritual.occultism.craft_soul_gem.started", "Started binding djinni into soul gem.");
        this.lang("ru_ru").add("ritual.occultism.craft_soul_gem.finished", "Successfully bound djinni into soul gem.");
        this.lang("ru_ru").add("ritual.occultism.craft_soul_gem.interrupted", "Binding of djinni interrupted.");
        this.lang("ru_ru").add("ritual.occultism.craft_familiar_ring.conditions", "Не все требования удовлетворены для этого ритуала.");
        this.lang("ru_ru").add("ritual.occultism.craft_familiar_ring.started", "Started binding djinni into familiar ring.");
        this.lang("ru_ru").add("ritual.occultism.craft_familiar_ring.finished", "Successfully bound djinni into familiar ring.");
        this.lang("ru_ru").add("ritual.occultism.craft_familiar_ring.interrupted", "Binding of djinni interrupted.");
        this.lang("ru_ru").add("ritual.occultism.craft_wild_trim.conditions", "Не все требования удовлетворены для этого ритуала.");
        this.lang("ru_ru").add("ritual.occultism.craft_wild_trim.started", "Marid has started to forge the Wild Armor Trim Smithing Template.");
        this.lang("ru_ru").add("ritual.occultism.craft_wild_trim.finished", "Successfully forged the Wild Armor Trim Smithing Template.");
        this.lang("ru_ru").add("ritual.occultism.craft_wild_trim.interrupted", "Binding of djinni interrupted.");
        this.lang("ru_ru").add("ritual.occultism.possess_endermite.conditions", "Не все требования удовлетворены для этого ритуала.");
        this.lang("ru_ru").add("ritual.occultism.possess_endermite.started", "Начался призыв possessed endermite.");
        this.lang("ru_ru").add("ritual.occultism.possess_endermite.finished", "Summoned possessed endermite successfully.");
        this.lang("ru_ru").add("ritual.occultism.possess_endermite.interrupted", "Summoning of possessed endermite interrupted.");
        this.lang("ru_ru").add("ritual.occultism.possess_skeleton.conditions", "Не все требования удовлетворены для этого ритуала.");
        this.lang("ru_ru").add("ritual.occultism.possess_skeleton.started", "Начался призыв possessed skeleton.");
        this.lang("ru_ru").add("ritual.occultism.possess_skeleton.finished", "Summoned possessed skeleton successfully.");
        this.lang("ru_ru").add("ritual.occultism.possess_skeleton.interrupted", "Summoning of possessed skeleton interrupted.");
        this.lang("ru_ru").add("ritual.occultism.possess_enderman.conditions", "Не все требования удовлетворены для этого ритуала.");
        this.lang("ru_ru").add("ritual.occultism.possess_enderman.started", "Начался призыв possessed enderman.");
        this.lang("ru_ru").add("ritual.occultism.possess_enderman.finished", "Summoned possessed enderman successfully.");
        this.lang("ru_ru").add("ritual.occultism.possess_enderman.interrupted", "Summoning of possessed enderman interrupted.");
        this.lang("ru_ru").add("ritual.occultism.possess_ghast.conditions", "Не все требования удовлетворены для этого ритуала.");
        this.lang("ru_ru").add("ritual.occultism.possess_ghast.started", "Начался призыв possessed ghast.");
        this.lang("ru_ru").add("ritual.occultism.possess_ghast.finished", "Summoned possessed ghast successfully.");
        this.lang("ru_ru").add("ritual.occultism.possess_ghast.interrupted", "Summoning of possessed ghast interrupted.");
        this.lang("ru_ru").add("ritual.occultism.possess_phantom.conditions", "Не все требования удовлетворены для этого ритуала.");
        this.lang("ru_ru").add("ritual.occultism.possess_phantom.started", "Начался призыв possessed phantom.");
        this.lang("ru_ru").add("ritual.occultism.possess_phantom.finished", "Summoned possessed phantom successfully.");
        this.lang("ru_ru").add("ritual.occultism.possess_phantom.interrupted", "Summoning of possessed phantom interrupted.");
        this.lang("ru_ru").add("ritual.occultism.possess_weak_shulker.conditions", "Не все требования удовлетворены для этого ритуала.");
        this.lang("ru_ru").add("ritual.occultism.possess_weak_shulker.started", "Начался призыв possessed weak shulker.");
        this.lang("ru_ru").add("ritual.occultism.possess_weak_shulker.finished", "Summoned possessed weak shulker successfully.");
        this.lang("ru_ru").add("ritual.occultism.possess_weak_shulker.interrupted", "Summoning of possessed weak shulker interrupted.");
        this.lang("ru_ru").add("ritual.occultism.possess_shulker.conditions", "Не все требования удовлетворены для этого ритуала.");
        this.lang("ru_ru").add("ritual.occultism.possess_shulker.started", "Начался призыв possessed shulker.");
        this.lang("ru_ru").add("ritual.occultism.possess_shulker.finished", "Summoned possessed shulker successfully.");
        this.lang("ru_ru").add("ritual.occultism.possess_shulker.interrupted", "Summoning of possessed shulker interrupted.");
        this.lang("ru_ru").add("ritual.occultism.possess_elder_guardian.conditions", "Не все требования удовлетворены для этого ритуала.");
        this.lang("ru_ru").add("ritual.occultism.possess_elder_guardian.started", "Начался призыв possessed elder_guardian.");
        this.lang("ru_ru").add("ritual.occultism.possess_elder_guardian.finished", "Summoned possessed elder_guardian successfully.");
        this.lang("ru_ru").add("ritual.occultism.possess_elder_guardian.interrupted", "Summoning of possessed elder_guardian interrupted.");
        this.lang("ru_ru").add("ritual.occultism.possess_warden.conditions", "Не все требования удовлетворены для этого ритуала.");
        this.lang("ru_ru").add("ritual.occultism.possess_warden.started", "Начался призыв possessed warden.");
        this.lang("ru_ru").add("ritual.occultism.possess_warden.finished", "Summoned possessed warden successfully.");
        this.lang("ru_ru").add("ritual.occultism.possess_warden.interrupted", "Summoning of possessed warden interrupted.");
        this.lang("ru_ru").add("ritual.occultism.possess_hoglin.conditions", "Не все требования удовлетворены для этого ритуала.");
        this.lang("ru_ru").add("ritual.occultism.possess_hoglin.started", "Начался призыв possessed hoglin.");
        this.lang("ru_ru").add("ritual.occultism.possess_hoglin.finished", "Summoned possessed hoglin successfully.");
        this.lang("ru_ru").add("ritual.occultism.possess_hoglin.interrupted", "Summoning of possessed hoglin interrupted.");
        this.lang("ru_ru").add("ritual.occultism.familiar_otherworld_bird.conditions", "Не все требования удовлетворены для этого ритуала.");
        this.lang("ru_ru").add("ritual.occultism.familiar_otherworld_bird.started", "Начался призыв drikwing familiar.");
        this.lang("ru_ru").add("ritual.occultism.familiar_otherworld_bird.finished", "Summoned drikwing familiar successfully.");
        this.lang("ru_ru").add("ritual.occultism.familiar_otherworld_bird.interrupted", "Summoning of drikwing familiar interrupted.");
        this.lang("ru_ru").add("ritual.occultism.familiar_cthulhu.conditions", "Не все требования удовлетворены для этого ритуала.");
        this.lang("ru_ru").add("ritual.occultism.familiar_cthulhu.started", "Начался призыв cthulhu familiar.");
        this.lang("ru_ru").add("ritual.occultism.familiar_cthulhu.finished", "Summoned cthulhu familiar successfully.");
        this.lang("ru_ru").add("ritual.occultism.familiar_cthulhu.interrupted", "Summoning of cthulhu familiar interrupted.");
        this.lang("ru_ru").add("ritual.occultism.familiar_devil.conditions", "Не все требования удовлетворены для этого ритуала.");
        this.lang("ru_ru").add("ritual.occultism.familiar_devil.started", "Начался призыв devil familiar.");
        this.lang("ru_ru").add("ritual.occultism.familiar_devil.finished", "Summoned devil familiar successfully.");
        this.lang("ru_ru").add("ritual.occultism.familiar_devil.interrupted", "Summoning of devil familiar interrupted.");
        this.lang("ru_ru").add("ritual.occultism.familiar_dragon.conditions", "Не все требования удовлетворены для этого ритуала.");
        this.lang("ru_ru").add("ritual.occultism.familiar_dragon.started", "Начался призыв dragon familiar.");
        this.lang("ru_ru").add("ritual.occultism.familiar_dragon.finished", "Summoned dragon familiar successfully.");
        this.lang("ru_ru").add("ritual.occultism.familiar_dragon.interrupted", "Summoning of dragon familiar interrupted.");
        this.lang("ru_ru").add("ritual.occultism.familiar_blacksmith.conditions", "Не все требования удовлетворены для этого ритуала.");
        this.lang("ru_ru").add("ritual.occultism.familiar_blacksmith.started", "Начался призыв blacksmith familiar.");
        this.lang("ru_ru").add("ritual.occultism.familiar_blacksmith.finished", "Summoned blacksmith familiar successfully.");
        this.lang("ru_ru").add("ritual.occultism.familiar_blacksmith.interrupted", "Summoning of blacksmith familiar interrupted.");
        this.lang("ru_ru").add("ritual.occultism.familiar_guardian.conditions", "Не все требования удовлетворены для этого ритуала.");
        this.lang("ru_ru").add("ritual.occultism.familiar_guardian.started", "Начался призыв guardian familiar.");
        this.lang("ru_ru").add("ritual.occultism.familiar_guardian.finished", "Summoned guardian familiar successfully.");
        this.lang("ru_ru").add("ritual.occultism.familiar_guardian.interrupted", "Summoning of guardian familiar interrupted.");
        this.lang("ru_ru").add("ritual.occultism.summon_wild_otherworld_bird.conditions", "Не все требования удовлетворены для этого ритуала.");
        this.lang("ru_ru").add("ritual.occultism.summon_wild_otherworld_bird.started", "Начался призыв wild drikwing.");
        this.lang("ru_ru").add("ritual.occultism.summon_wild_otherworld_bird.finished", "Summoned wild drikwing successfully.");
        this.lang("ru_ru").add("ritual.occultism.summon_wild_otherworld_bird.interrupted", "Summoning of wild drikwing interrupted.");
        this.lang("ru_ru").add("ritual.occultism.summon_wild_parrot.conditions", "Не все требования удовлетворены для этого ритуала.");
        this.lang("ru_ru").add("ritual.occultism.summon_wild_parrot.started", "Начался призыв wild parrot.");
        this.lang("ru_ru").add("ritual.occultism.summon_wild_parrot.finished", "Summoned wild parrot successfully.");
        this.lang("ru_ru").add("ritual.occultism.summon_wild_parrot.interrupted", "Summoning of wild parrot interrupted.");
        this.lang("ru_ru").add("ritual.occultism.familiar_parrot.conditions", "Не все требования удовлетворены для этого ритуала.");
        this.lang("ru_ru").add("ritual.occultism.familiar_parrot.started", "Начался призыв parrot familiar.");
        this.lang("ru_ru").add("ritual.occultism.familiar_parrot.finished", "Summoned parrot familiar successfully.");
        this.lang("ru_ru").add("ritual.occultism.familiar_parrot.interrupted", "Summoning of parrot familiar interrupted.");
        this.lang("ru_ru").add("ritual.occultism.summon_allay.conditions", "Не все требования удовлетворены для этого ритуала.");
        this.lang("ru_ru").add("ritual.occultism.summon_allay.started", "Started purifying Vex to Allay.");
        this.lang("ru_ru").add("ritual.occultism.summon_allay.finished", "Purified Vex to Allay successfully.");
        this.lang("ru_ru").add("ritual.occultism.summon_allay.interrupted", "Purifying Vex to allay interrupted.");
        this.lang("ru_ru").add("ritual.occultism.familiar_greedy.conditions", "Не все требования удовлетворены для этого ритуала.");
        this.lang("ru_ru").add("ritual.occultism.familiar_greedy.started", "Начался призыв greedy familiar.");
        this.lang("ru_ru").add("ritual.occultism.familiar_greedy.finished", "Summoned v familiar successfully.");
        this.lang("ru_ru").add("ritual.occultism.familiar_greedy.interrupted", "Summoning of greedy familiar interrupted.");
        this.lang("ru_ru").add("ritual.occultism.familiar_bat.conditions", "Не все требования удовлетворены для этого ритуала.");
        this.lang("ru_ru").add("ritual.occultism.familiar_bat.started", "Начался призыв bat familiar.");
        this.lang("ru_ru").add("ritual.occultism.familiar_bat.finished", "Summoned bat familiar successfully.");
        this.lang("ru_ru").add("ritual.occultism.familiar_bat.interrupted", "Summoning of bat familiar interrupted.");
        this.lang("ru_ru").add("ritual.occultism.familiar_deer.conditions", "Не все требования удовлетворены для этого ритуала.");
        this.lang("ru_ru").add("ritual.occultism.familiar_deer.started", "Начался призыв deer familiar.");
        this.lang("ru_ru").add("ritual.occultism.familiar_deer.finished", "Summoned deer familiar successfully.");
        this.lang("ru_ru").add("ritual.occultism.familiar_deer.interrupted", "Summoning of deer familiar interrupted.");
        this.lang("ru_ru").add("ritual.occultism.familiar_headless.conditions", "Не все требования удовлетворены для этого ритуала.");
        this.lang("ru_ru").add("ritual.occultism.familiar_headless.started", "Начался призыв headless ratman familiar.");
        this.lang("ru_ru").add("ritual.occultism.familiar_headless.finished", "Summoned headless ratman familiar successfully.");
        this.lang("ru_ru").add("ritual.occultism.familiar_headless.interrupted", "Summoning of headless ratman familiar interrupted.");
        this.lang("ru_ru").add("ritual.occultism.familiar_chimera.conditions", "Не все требования удовлетворены для этого ритуала.");
        this.lang("ru_ru").add("ritual.occultism.familiar_chimera.started", "Начался призыв chimera familiar.");
        this.lang("ru_ru").add("ritual.occultism.familiar_chimera.finished", "Summoned chimera familiar successfully.");
        this.lang("ru_ru").add("ritual.occultism.familiar_chimera.interrupted", "Summoning of chimera familiar interrupted.");
        this.lang("ru_ru").add("ritual.occultism.familiar_beholder.conditions", "Не все требования удовлетворены для этого ритуала.");
        this.lang("ru_ru").add("ritual.occultism.familiar_beholder.started", "Начался призыв beholder familiar.");
        this.lang("ru_ru").add("ritual.occultism.familiar_beholder.finished", "Summoned beholder familiar successfully.");
        this.lang("ru_ru").add("ritual.occultism.familiar_beholder.interrupted", "Summoning of beholder familiar interrupted.");
        this.lang("ru_ru").add("ritual.occultism.familiar_fairy.conditions", "Не все требования удовлетворены для этого ритуала.");
        this.lang("ru_ru").add("ritual.occultism.familiar_fairy.started", "Начался призыв fairy familiar.");
        this.lang("ru_ru").add("ritual.occultism.familiar_fairy.finished", "Summoned fairy familiar successfully.");
        this.lang("ru_ru").add("ritual.occultism.familiar_fairy.interrupted", "Summoning of fairy familiar interrupted.");
        this.lang("ru_ru").add("ritual.occultism.familiar_mummy.conditions", "Не все требования удовлетворены для этого ритуала.");
        this.lang("ru_ru").add("ritual.occultism.familiar_mummy.started", "Начался призыв mummy familiar.");
        this.lang("ru_ru").add("ritual.occultism.familiar_mummy.finished", "Summoned mummy familiar successfully.");
        this.lang("ru_ru").add("ritual.occultism.familiar_mummy.interrupted", "Summoning of mummy familiar interrupted.");
        this.lang("ru_ru").add("ritual.occultism.familiar_beaver.conditions", "Не все требования удовлетворены для этого ритуала.");
        this.lang("ru_ru").add("ritual.occultism.familiar_beaver.started", "Начался призыв beaver familiar.");
        this.lang("ru_ru").add("ritual.occultism.familiar_beaver.finished", "Summoned beaver familiar successfully.");
        this.lang("ru_ru").add("ritual.occultism.familiar_beaver.interrupted", "Summoning of beaver familiar interrupted.");

        this.lang("ru_ru").add("ritual.occultism.summon_demonic_wife.conditions", "Не все требования удовлетворены для этого ритуала.");
        this.lang("ru_ru").add("ritual.occultism.summon_demonic_wife.started", "Начался призыв.");
        this.lang("ru_ru").add("ritual.occultism.summon_demonic_wife.finished", "Summoned successfully.");
        this.lang("ru_ru").add("ritual.occultism.summon_demonic_wife.interrupted", "Summoning interrupted.");
        this.lang("ru_ru").add("ritual.occultism.summon_demonic_husband.conditions", "Не все требования удовлетворены для этого ритуала.");
        this.lang("ru_ru").add("ritual.occultism.summon_demonic_husband.started", "Начался призыв.");
        this.lang("ru_ru").add("ritual.occultism.summon_demonic_husband.finished", "Summoned successfully.");
        this.lang("ru_ru").add("ritual.occultism.summon_demonic_husband.interrupted", "Summoning interrupted.");

        this.lang("ru_ru").add("ritual.occultism.summon_wild_husk.conditions", "Не все требования удовлетворены для этого ритуала.");
        this.lang("ru_ru").add("ritual.occultism.summon_wild_husk.started", "Начался призыв the wild horde husk.");
        this.lang("ru_ru").add("ritual.occultism.summon_wild_husk.finished", "Summoned the wild horde husk successfully.");
        this.lang("ru_ru").add("ritual.occultism.summon_wild_husk.interrupted", "Summoning of the wild horde husk interrupted.");
        this.lang("ru_ru").add("ritual.occultism.summon_wild_drowned.conditions", "Не все требования удовлетворены для этого ритуала.");
        this.lang("ru_ru").add("ritual.occultism.summon_wild_drowned.started", "Начался призыв the wild horde drowned.");
        this.lang("ru_ru").add("ritual.occultism.summon_wild_drowned.finished", "Summoned the wild horde drowned successfully.");
        this.lang("ru_ru").add("ritual.occultism.summon_wild_drowned.interrupted", "Summoning of the wild horde drowned interrupted.");
        this.lang("ru_ru").add("ritual.occultism.summon_wild_creeper.conditions", "Не все требования удовлетворены для этого ритуала.");
        this.lang("ru_ru").add("ritual.occultism.summon_wild_creeper.started", "Начался призыв the wild horde creeper.");
        this.lang("ru_ru").add("ritual.occultism.summon_wild_creeper.finished", "Summoned the wild horde creeper successfully.");
        this.lang("ru_ru").add("ritual.occultism.summon_wild_creeper.interrupted", "Summoning of the wild horde creeper interrupted.");
        this.lang("ru_ru").add("ritual.occultism.summon_wild_silverfish.conditions", "Не все требования удовлетворены для этого ритуала.");
        this.lang("ru_ru").add("ritual.occultism.summon_wild_silverfish.started", "Начался призыв the wild horde silverfish.");
        this.lang("ru_ru").add("ritual.occultism.summon_wild_silverfish.finished", "Summoned the wild horde silverfish successfully.");
        this.lang("ru_ru").add("ritual.occultism.summon_wild_silverfish.interrupted", "Summoning of the wild horde silverfish interrupted.");
        this.lang("ru_ru").add("ritual.occultism.possess_weak_breeze.conditions", "Не все требования удовлетворены для этого ритуала.");
        this.lang("ru_ru").add("ritual.occultism.possess_weak_breeze.started", "Начался призыв possessed weak breeze.");
        this.lang("ru_ru").add("ritual.occultism.possess_weak_breeze.finished", "Summoned possessed weak breeze successfully.");
        this.lang("ru_ru").add("ritual.occultism.possess_weak_breeze.interrupted", "Summoning of possessed weak breeze interrupted.");
        this.lang("ru_ru").add("ritual.occultism.possess_breeze.conditions", "Не все требования удовлетворены для этого ритуала.");
        this.lang("ru_ru").add("ritual.occultism.possess_breeze.started", "Начался призыв possessed breeze.");
        this.lang("ru_ru").add("ritual.occultism.possess_breeze.finished", "Summoned possessed breeze successfully.");
        this.lang("ru_ru").add("ritual.occultism.possess_breeze.interrupted", "Summoning of possessed breeze interrupted.");
        this.lang("ru_ru").add("ritual.occultism.possess_strong_breeze.conditions", "Не все требования удовлетворены для этого ритуала.");
        this.lang("ru_ru").add("ritual.occultism.possess_strong_breeze.started", "Начался призыв possessed strong breeze.");
        this.lang("ru_ru").add("ritual.occultism.possess_strong_breeze.finished", "Summoned possessed strong breeze successfully.");
        this.lang("ru_ru").add("ritual.occultism.possess_strong_breeze.interrupted", "Summoning of possessed strong breeze interrupted.");
        this.lang("ru_ru").add("ritual.occultism.summon_horde_illager.conditions", "Не все требования удовлетворены для этого ритуала.");
        this.lang("ru_ru").add("ritual.occultism.summon_horde_illager.started", "Начался призыв the small illager invasion.");
        this.lang("ru_ru").add("ritual.occultism.summon_horde_illager.finished", "Summoned the small illager invasion successfully.");
        this.lang("ru_ru").add("ritual.occultism.summon_horde_illager.interrupted", "Summoning of the small illager invasion interrupted.");

        this.addRitualMessage(OccultismRituals.RESURRECT_FAMILIAR, "conditions", "Не все требования удовлетворены для этого ритуала.");
        this.addRitualMessage(OccultismRituals.RESURRECT_FAMILIAR, "started", "Началось воскресение фамильяра.");
        this.addRitualMessage(OccultismRituals.RESURRECT_FAMILIAR, "finished", "Фамильяр успешно воскрешён.");
        this.addRitualMessage(OccultismRituals.RESURRECT_FAMILIAR, "interrupted", "Воскресение прервано.");
    }

    public void addRitualMessage(DeferredHolder<RitualFactory, RitualFactory> ritual, String key, String message) {
        this.lang("ru_ru").add("ritual.%s.%s".formatted(ritual.getId().getNamespace(), ritual.getId().getPath()) + "." + key, message);
    }

    private void addBook() {
        var helper = ModonomiconAPI.get().getContextHelper(Occultism.MODID);
        helper.book("dictionary_of_spirits");

        this.addPentaclesCategory(helper);
        this.addRitualsCategory(helper);
        this.addSummoningRitualsCategory(helper);
        this.addCraftingRitualsCategory(helper);
        this.addPossessionRitualsCategory(helper);
        this.addFamiliarRitualsCategory(helper);
        this.addStorageCategory(helper);
    }

    private void addPentaclesCategory(BookContextHelper helper) {
        helper.category("pentacles");
        this.lang("ru_ru").add(helper.categoryName(), "Пентакли");

        helper.entry("pentacles_overview");
        this.lang("ru_ru").add(helper.entryName(), "Касательно пентаклей");

        helper.page("intro1");
        this.lang("ru_ru").add(helper.pageTitle(), "Касательно пентаклей");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        Название [#](ad03fc)Пентакль[#]() в таком контексте относится к ритуальным рисункам любой формы, не только пятиконечным звёздам. \\
                        \\
                        Пентакли используются для призыва и связывания духов из [#](%1$s)Мира духов[#]().
                        """.formatted(COLOR_PURPLE));

        helper.page("intro2");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        They act both as a device to call on the entity, an amplifier for the summoner's commanding power
                        and as a protecting circle preventing attacks from within against the summoner.
                        """.formatted(COLOR_PURPLE));

        helper.page("intro3");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        Each pentacle consists of a central golden sacrificial bowl, surrounding runes of various colors and occult paraphernalia that improve the intended effect in various ways.
                        """.formatted(COLOR_PURPLE));


        helper.page("intro4");
        this.lang("ru_ru").add(helper.pageText(),
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
        this.lang("ru_ru").add(helper.pageText(),
                """
                        [Sacrificial Bowls](item://occultism:sacrificial_bowl) must be placed **anywhere** within 8 blocks horizontally of the central [](item://occultism:golden_sacrificial_bowl). The exact location does not matter.
                        \\
                        \\
                        Now it is time to place the ingredients you see on the next page in the (regular, not golden) sacrificial bowls.
                          """);

        helper.page("summoning_pentacles");
        this.lang("ru_ru").add(helper.pageTitle(), "Summoning Pentacles");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        The purpose of this type of pentacle is to summon spirits in their chosen form into the world. Spirits summoned thus suffer from strong essence decay, and only very powerful spirits can remain for extended periods of time.
                        """);

        helper.page("infusion_pentacles");
        this.lang("ru_ru").add(helper.pageTitle(), "Infusion Pentacles");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        Infusion pentacles allow the binding of spirits into objects. While the spirits suffer from essence decay in some cases, this can often be averted with the right pentacle setup, and by embedding crystals and precious metals into the object to support the spirit.
                        """);

        helper.page("possession_pentacles");
        this.lang("ru_ru").add(helper.pageTitle(), "Possession Pentacles");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        These pentacles force spirits to possess a living being, which, depending on the ritual context, gives the summoner control over various aspects of that being, ranging from it's strength to it what it drops when killed, and in some cases even allows total control.
                        """);

        helper.entry("paraphernalia");
        this.lang("ru_ru").add(helper.entryName(), "Occult Paraphernalia");

        helper.page("intro");
        this.lang("ru_ru").add(helper.pageTitle(), "Occult Paraphernalia");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        In addition to runes various occult paraphernalia are used to improve the intended effect of the pentacle.
                        """);

        helper.page("candle");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        Candles increase the stability of the pentacle, thus allowing a slowed essence decay of the summoned spirit, leading to a longer lifetime of the spirit, or possessed object or being.
                        """);

        helper.page("crystal");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        Crystals increase the binding power of the pentacle, allowing a permanent binding of the spirit into an item or living being.
                        """);

        helper.page("gem_recipe");
        //no text

        helper.page("crystal_recipe");
        //no text

        helper.page("skeleton_skull");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        Skulls increase the calling power of the pentacle, allowing to summon more dangerous spirits.
                        """);

        helper.entry("chalk_uses");
        this.lang("ru_ru").add(helper.entryName(), "Chalk Types");

        helper.page("intro");
        this.lang("ru_ru").add(helper.pageTitle(), "Chalk Types");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        Chalk is used to draw pentacle runes and define the pentacle shape. Different types of chalk are used for different purposes, as outlined on the next pages.
                        \\
                        \\
                        The different runes are purely decorative.
                        """);

        helper.page("intro2");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        **Repeatedly** use chalk on a block to switch to a different rune.
                        \\
                        \\
                        Using a [](item://occultism:brush) is the easiest way to **remove** chalk runes that have been incorrectly placed.
                        """);

        helper.page("white_chalk");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        White chalk is the most basic type of ritual chalk and is found in most pentacles. It has no special power beyond defining the shape of the pentacle.
                           """);


        helper.page("white_chalk_uses");
        this.lang("ru_ru").add(helper.pageTitle(), "White Chalk Uses");
        this.lang("ru_ru").add(helper.pageText(),
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
        this.lang("ru_ru").add(helper.pageTitle(), "White Chalk Uses");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        - [Uphyxes Inverted Tower](entry://occultism:dictionary_of_spirits/pentacles/craft_marid)
                        - [Osorin' Unbound Calling](entry://occultism:dictionary_of_spirits/pentacles/summon_wild_greater_spirit)
                         """);

        helper.page("golden_chalk");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        Golden chalk is used for binding runes, which allow to infuse a spirit into an item, or make it possess a living being.
                               """);


        helper.page("golden_chalk_uses");
        this.lang("ru_ru").add(helper.pageTitle(), "Golden Chalk Uses");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        - [Eziveus' Spectral Compulsion](entry://occultism:dictionary_of_spirits/pentacles/craft_foliot)
                        - [Hedyrin's Lure](entry://occultism:dictionary_of_spirits/pentacles/possess_foliot)
                        - [Strigeor's Higher Binding](entry://occultism:dictionary_of_spirits/pentacles/craft_djinni)
                        - [Ihagan's Enthrallment](entry://occultism:dictionary_of_spirits/pentacles/possess_djinni)
                        - [Sevira's Permanent Confinement](entry://occultism:dictionary_of_spirits/pentacles/craft_afrit)
                        - [Uphyxes Inverted Tower](entry://occultism:dictionary_of_spirits/pentacles/craft_marid)
                           """);

        helper.page("purple_chalk");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        Purple chalk is generally used to call on higher beings such as [#](%1$s)Djinn[#]() or [#](%1$s)Afrit[#](), but also slows essence decay of summoned spirits.
                               """.formatted(COLOR_PURPLE));

        helper.page("purple_chalk_uses");
        this.lang("ru_ru").add(helper.pageTitle(), "Purple Chalk Uses");
        this.lang("ru_ru").add(helper.pageText(),
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
        this.lang("ru_ru").add(helper.pageText(),
                """
                        Red chalk is used to call on the most powerful and dangerous beings, such as [#](%1$s)Marid[#]().
                        \\
                        \\
                        [Afrit Essence](entry://occultism:dictionary_of_spirits/summoning_rituals/afrit_essence) is required to craft red chalk.
                                """.formatted(COLOR_PURPLE));

        helper.page("red_chalk_uses");
        this.lang("ru_ru").add(helper.pageTitle(), "Red Chalk Uses");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        - [Abras' Conjure](entry://occultism:dictionary_of_spirits/pentacles/summon_afrit)
                        - [Uphyxes Inverted Tower](entry://occultism:dictionary_of_spirits/pentacles/craft_marid)
                           """);


        helper.entry("summon_foliot");
        this.lang("ru_ru").add(helper.entryName(), "Aviar's Circle");

        helper.page("intro");
        this.lang("ru_ru").add(helper.pageTitle(), "Aviar's Circle");
        this.lang("ru_ru").add(helper.pageText(),
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
        this.lang("ru_ru").add(helper.pageTitle(), "Uses");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        - [Foliot Crusher](entry://summoning_rituals/summon_crusher_t1)
                        - [Foliot Lumberjack](entry://summoning_rituals/summon_lumberjack)
                        - [Foliot Transporter](entry://summoning_rituals/summon_transport_items)
                        - [Foliot Janitor](entry://summoning_rituals/summon_cleaner)
                        - [Otherstone Trader](entry://summoning_rituals/summon_otherstone_trader)
                        - [Otherworld Sapling Trader](entry://summoning_rituals/summon_otherworld_sapling_trader)
                        - [Resurrect Familiar](entry://familiar_rituals/resurrection)
                        - [Purify Vex to Allay](entry://familiar_rituals/summon_allay)
                        """);


        helper.entry("summon_djinni");
        this.lang("ru_ru").add(helper.entryName(), "Ophyx' Calling");

        helper.page("intro");
        this.lang("ru_ru").add(helper.pageTitle(), "Ophyx' Calling");
        //Add link rituals/possession/possess_skeleton instead of [Obtain here]
        this.lang("ru_ru").add(helper.pageText(),
                """
                        **Purpose:** Summon a [#](%1$s)Djinni[#]()
                        \\
                        \\
                        Developed by [#](%1$s)Ophyx[#]() during the Third Era, the [#](%1$s)Calling[#]() is the go-to pentacle for [#](%1$s)Djinni[#]() summonings ever since. Skeleton skulls ([Obtain here](entry://possession_rituals/possess_skeleton)) and [#](%1$s)Purple Chalk[#]() provide the calling power required to force Djinni into appearance and candles stabilize the ritual.
                         """.formatted(COLOR_PURPLE));

        helper.page("multiblock");
        //no text

        helper.page("uses");
        this.lang("ru_ru").add(helper.pageTitle(), "Uses");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        - [Djinni Machine Operator](entry://summoning_rituals/summon_manage_machine)
                        - [Djinni Crusher](entry://summoning_rituals/summon_crusher_t2)
                        - [Clear Weather](entry://summoning_rituals/weather_magic@clear)
                        - [Time Magic](entry://summoning_rituals/time_magic)
                        """.formatted(COLOR_PURPLE));

        helper.entry("summon_afrit");
        this.lang("ru_ru").add(helper.entryName(), "Abras' Conjure");
        helper.page("intro");
        this.lang("ru_ru").add(helper.pageTitle(), "Abras' Conjure");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        **Purpose:** Summon an [#](%1$s)Afrit[#]()
                        \\
                        \\
                        **Abras' Conjure** is one of the few pentacles capable of (mostly) safely summoning an [#](%1$s)Afrit[#](). While the requirement of a wither skeleton skull makes it comparatively expensive, the additional calling potential is required to reach these high-power spirits.
                         """.formatted(COLOR_PURPLE));

        helper.page("multiblock");
        //no text

        helper.page("uses");
        this.lang("ru_ru").add(helper.pageTitle(), "Uses");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        - [Thunderstorm](entry://summoning_rituals/weather_magic@thunder)
                        - [Rainy Weather](entry://summoning_rituals/weather_magic@rain)
                        - [Afrit Crusher](entry://summoning_rituals/summon_crusher_t3)
                        """.formatted(COLOR_PURPLE));

        helper.entry("summon_marid");
        this.lang("ru_ru").add(helper.entryName(), "Fatma's Incentivized Attraction");
        helper.page("intro");
        this.lang("ru_ru").add(helper.pageTitle(), "Fatma's Incentivized Attraction");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        **Purpose:** Summon a [#](%1$s)Marid[#]()
                        \\
                        \\
                        **Fatma's Incentivized Attraction** is a powerful pentacle, allowing to summon [#](%1$s)Marid[#]() and bind them to the summoner's will.
                         """.formatted(COLOR_PURPLE));

        helper.page("multiblock");
        //no text

        helper.page("uses");
        this.lang("ru_ru").add(helper.pageTitle(), "Uses");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        - [Marid Crusher](entry://summoning_rituals/summon_crusher_t4)
                        """.formatted(COLOR_PURPLE));

        helper.entry("summon_wild_afrit");
        this.lang("ru_ru").add(helper.entryName(), "Abras' Open Conjure");
        helper.page("intro");
        this.lang("ru_ru").add(helper.pageTitle(), "Abras' Open Conjure");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        **Purpose:** Summon an unbound [#](%1$s)Afrit[#]()
                        \\
                        \\
                        **Abras' Open Conjure** is a simplified version of [#](%1$s)Abras' Conjure[#](), allowing to summon [#](%1$s)Afrit[#]() without red chalk. Due to the much reduced power of the pentacle, it cannot be used to control [#](%1$s)Afrit[#](), and it thus can only be used to fight and kill [#](%1$s)Afrit[#]().
                         """.formatted(COLOR_PURPLE));

        helper.page("multiblock");
        //no text

        helper.page("uses");
        this.lang("ru_ru").add(helper.pageTitle(), "Uses");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        - [Afrit Essence](entry://summoning_rituals/afrit_essence)
                        """.formatted(COLOR_PURPLE));

        helper.entry("summon_wild_greater_spirit");
        this.lang("ru_ru").add(helper.entryName(), "Osorin's Unbound Calling");
        helper.page("intro");
        this.lang("ru_ru").add(helper.pageTitle(), "Osorin's Unbound Calling");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        **Purpose:** Summon an unbound [#](%1$s)Greater Spirit[#]()
                        \\
                        \\
                        **Osorin's Unbound Calling** is based on [#](%1$s)Abras' Open Conjure[#](), but features none of the stabilizing paraphernalia. The pentacle offers no protection whatsoever to the summoner, but acts as an irresistible call to [#](%1$s)Greater Spirits[#]().
                        """.formatted(COLOR_PURPLE));

        helper.page("multiblock");
        //no text

        helper.page("uses");
        this.lang("ru_ru").add(helper.pageTitle(), "Uses");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        - [Wither Skeleton Skull](entry://summoning_rituals/wither_skull)
                        - [Horde Husk](entry://possession_rituals/horde_husk)
                        - [Horde Drowned](entry://possession_rituals/horde_drowned)
                        - [Horde Creeper](entry://possession_rituals/horde_creeper)
                        - [Horde Silverfish](entry://possession_rituals/horde_silverfish)
                        - [Trial Key](entry://possession_rituals/possess_weak_breeze)
                        - [Ominous Trial Key](entry://possession_rituals/possess_breeze)
                        - [Heavy Core](entry://possession_rituals/possess_strong_breeze)
                        - [Small Illager Invasion](entry://possession_rituals/horde_illager)
                        """.formatted(COLOR_PURPLE));

        helper.entry("possess_foliot");
        this.lang("ru_ru").add(helper.entryName(), "Hedyrin's Lure");
        helper.page("intro");
        this.lang("ru_ru").add(helper.pageTitle(), "Hedyrin's Lure");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        **Purpose:** Foliot Possession
                        \\
                        \\
                        **Hedyrin's Lure** attracts [#](%1$s)Foliot[#]() and forces them to possess a nearby creature. This pentacle does not lead to permanent imprisonment, the spirit and possessed creature will perish within a short period of time.
                         """.formatted(COLOR_PURPLE));

        helper.page("multiblock");
        //no text

        helper.page("uses");
        this.lang("ru_ru").add(helper.pageTitle(), "Uses");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        - [Possessed Endermite](entry://possession_rituals/possess_endermite)
                        - [Possessed Skeleton](entry://possession_rituals/possess_skeleton)
                        - [Possessed Phantom](entry://possession_rituals/possess_phantom)
                        - [Parrot Familiar](entry://familiar_rituals/familiar_parrot)
                        - [Greedy Familiar](entry://familiar_rituals/familiar_greedy)
                        - [Deer Familiar](entry://familiar_rituals/familiar_deer)
                        - [Blacksmith Familiar](entry://familiar_rituals/familiar_blacksmith)
                        - [Beaver Familiar](entry://familiar_rituals/familiar_beaver)
                        """.formatted(COLOR_PURPLE));

        helper.entry("possess_djinni");
        this.lang("ru_ru").add(helper.entryName(), "Ihagan's Enthrallment");
        helper.page("intro");
        this.lang("ru_ru").add(helper.pageTitle(), "Ihagan's Enthrallment");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        **Purpose:** Djinni Possession
                        \\
                        \\
                        **Ihagan's Enthrallment** forces [#](%1$s)Djinn[#]() to possess a nearby creature. This pentacle does not lead to permanent imprisonment, the spirit and possessed creature will perish within a short period of time.
                         """.formatted(COLOR_PURPLE));

        helper.page("multiblock");
        //no text

        helper.page("uses");
        this.lang("ru_ru").add(helper.pageTitle(), "Uses");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        - [Possessed Enderman](entry://possession_rituals/possess_enderman)
                        - [Possessed Ghast](entry://possession_rituals/possess_ghast)
                        - [Possessed Weak Shulker](entry://possession_rituals/possess_weak_shulker)
                        - [Possessed Warden](entry://possession_rituals/possess_warden)
                        - [Drikwing Familiar](entry://familiar_rituals/familiar_otherworld_bird)
                        - [Bat Familiar](entry://familiar_rituals/familiar_bat)
                        - [Cthulhu Familiar](entry://familiar_rituals/familiar_cthulhu)
                        - [Devil Familiar](entry://familiar_rituals/familiar_devil)
                        - [Dragon Familiar](entry://familiar_rituals/familiar_dragon)
                        - [Headless Ratman Familiar](entry://familiar_rituals/familiar_headless)
                        - [Chimera Familiar](entry://familiar_rituals/familiar_chimera)
                        """.formatted(COLOR_PURPLE));

        helper.page("uses2");
        this.lang("ru_ru").add(helper.pageTitle(), "Uses");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        - [Beholder Familiar](entry://familiar_rituals/familiar_beholder)                        
                        - [Fairy Familiar](entry://familiar_rituals/familiar_fairy)
                        """.formatted(COLOR_PURPLE));

        helper.entry("possess_afrit");
        this.lang("ru_ru").add(helper.entryName(), "Abras' Commanding Conjure");
        helper.page("intro");
        this.lang("ru_ru").add(helper.pageTitle(), "Abras' Commanding Conjure");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        **Purpose:** Afrit Possession
                        \\
                        \\
                        **Abras' Commanding Conjure** is a modified version of [#](%1$s)Abras' Conjure[#]() that allows possessing entities, and thus summoning familiars.
                         """.formatted(COLOR_PURPLE));

        helper.page("multiblock");
        //no text

        helper.page("uses");
        this.lang("ru_ru").add(helper.pageTitle(), "Uses");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        - [Guardian Familiar](entry://familiar_rituals/familiar_guardian)
                        - [Possessed Shulker](entry://possession_rituals/possess_shulker)
                        - [Possessed Elder Guardian](entry://possession_rituals/possess_elder_guardian)
                        - [Possessed Hoglin](entry://possession_rituals/possess_hoglin)
                        """.formatted(COLOR_PURPLE));

        helper.entry("craft_foliot");
        this.lang("ru_ru").add(helper.entryName(), "Eziveus' Spectral Compulsion");
        helper.page("intro");
        this.lang("ru_ru").add(helper.pageTitle(), "Eziveus' Spectral Compulsion");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        **Purpose:** Bind Foliot
                        \\
                        \\
                        As a simple binding pentacle, **Eziveus' Spectral Compulsion** is a common starting point for object infusion with lower spirits. The enchantment is made permanent by stabilizing candles and spirit attuned crystals.
                         """.formatted(COLOR_PURPLE));

        helper.page("multiblock");
        //no text

        helper.page("uses");
        this.lang("ru_ru").add(helper.pageTitle(), "Uses");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        - [Infused Lenses](entry://crafting_rituals/craft_otherworld_goggles)
                        - [Surprisingsly Substantial Satchel](entry://crafting_rituals/craft_satchel)
                        - [Storage Actuator Base](entry://crafting_rituals/craft_storage_controller_base)
                        - [Stable Wormhole](entry://crafting_rituals/craft_stable_wormhole)
                        - [Storage Stabilizer Tier 1](entry://crafting_rituals/craft_stabilizer_tier1)
                        - [Foliot Miner](entry://crafting_rituals/craft_foliot_miner)
                        """.formatted(COLOR_PURPLE));

        helper.entry("craft_djinni");
        this.lang("ru_ru").add(helper.entryName(), "Strigeor's Higher Binding");
        helper.page("intro");
        this.lang("ru_ru").add(helper.pageTitle(), "Strigeor's Higher Binding");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        **Purpose:** Bind Djinni
                        \\
                        \\
                        **Strigeor's Higher Binding** is a pentacle for binding [#](%1$s)Djinn[#]() into objects, should not be attempted by the novice summoner. Supported by spirit attuned crystals and stabilized by candles it is highly suitable for permanent infusions of objects with spirits.
                         """.formatted(COLOR_PURPLE));

        helper.page("multiblock");
        //no text

        helper.page("uses");
        this.lang("ru_ru").add(helper.pageTitle(), "Uses");
        this.lang("ru_ru").add(helper.pageText(),
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
        this.lang("ru_ru").add(helper.entryName(), "Sevira's Permanent Confinement");
        helper.page("intro");
        this.lang("ru_ru").add(helper.pageTitle(), "Sevira's Permanent Confinement");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        **Purpose:** Bind Afrit
                        \\
                        \\
                        First discovered by Grandmistress Sevira of Emberwoods, **Sevira's Permanent Confinement** is used for binding [#](%1$s)Afrit[#]() into objects. Due to the power of the spirits involved, this should be performed only by advanced summoners.
                         """.formatted(COLOR_PURPLE));

        helper.page("multiblock");
        //no text

        helper.page("uses");
        this.lang("ru_ru").add(helper.pageTitle(), "Uses");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        - [Storage Stabilizer Tier 3](entry://crafting_rituals/craft_stabilizer_tier3)
                        - [Afrit Deep Ore Miner](entry://crafting_rituals/craft_afrit_miner)
                        """.formatted(COLOR_PURPLE));

        helper.entry("craft_marid");
        this.lang("ru_ru").add(helper.entryName(), "Uphyxes Inverted Tower");
        helper.page("intro");
        this.lang("ru_ru").add(helper.pageTitle(), "Uphyxes Inverted Tower");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        **Purpose:** Bind Marid
                        \\
                        \\
                        **Uphyxes Inverted Tower** is one of the few pentacles capable of binding [#](%1$s)Marid[#]() into objects. Any rituals involving [#](%1$s)Marid[#]() should be performed only by the most experienced summoners.
                         """.formatted(COLOR_PURPLE));

        helper.page("multiblock");
        //no text

        helper.page("uses");
        this.lang("ru_ru").add(helper.pageTitle(), "Uses");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        - [Storage Stabilizer Tier 4](entry://crafting_rituals/craft_stabilizer_tier4)
                        - [Marid Master Miner](entry://crafting_rituals/craft_marid_miner)
                        - [Wild Armor Trim Smithing Template](entry://crafting_rituals/craft_wild_trim)
                        """.formatted(COLOR_PURPLE));
    }

    private void addRitualsCategory(BookContextHelper helper) {
        helper.category("rituals");
        this.lang("ru_ru").add(helper.categoryName(), "Rituals");

        helper.entry("overview");
        this.lang("ru_ru").add(helper.entryName(), "Rituals");

        helper.page("intro");
        this.lang("ru_ru").add(helper.pageTitle(), "Rituals");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        Rituals allow to summon spirits into our plane of existence, or bind them into objects or living beings. Every ritual consists of a [#](%1$s)Pentacle[#](), [#](%1$s)Ritual Ingredients[#]() provided via sacrificial bowls, a [#](%1$s)Starting Item[#]() and optionally the [#](%1$s)Sacrifice[#]() of living beings. A purple particle effect will show that the ritual is successful and in progress.
                        """.formatted(COLOR_PURPLE));

        helper.page("steps");
        this.lang("ru_ru").add(helper.pageTitle(), "Performing a Ritual");
        this.lang("ru_ru").add(helper.pageText(),
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
        this.lang("ru_ru").add(helper.pageTitle(), "Additional Requirements");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        If a ritual shows grey particles above the golden sacrificial bowl, then additional requirements as described in the ritual's page need to be fulfilled. Once all requirements are fulfilled, the ritual will show purple particles and start to consume the items in the sacrificial bowls.
                        """);

        helper.entry("item_use");
        this.lang("ru_ru").add(helper.entryName(), "Item Use");

        helper.page("intro");
        this.lang("ru_ru").add(helper.pageTitle(), "Item Use");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        Some rituals require the use of certain items to be performed. Use the item described on the ritual's page within **16 blocks** of the [](item://occultism:golden_sacrificial_bowl) to proceed with the ritual.
                        \\
                        \\
                        **Important:** Before using the item, start the ritual. Grey particles indicate that the ritual is ready for the item use.
                        """);

        helper.entry("sacrifice");
        this.lang("ru_ru").add(helper.entryName(), "Sacrifices");

        helper.page("intro");
        this.lang("ru_ru").add(helper.pageTitle(), "Sacrifices");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        Some rituals require the sacrifice of a living being to provide the necessary energy to summon the spirit. Sacrifices are described on the ritual's page under the "Sacrifice" subheading. To perform a sacrifice, kill an animal within 8 blocks of the golden sacrificial bowl. Only kills by players count as sacrifice!
                         """);

        helper.entry("summoning_rituals");
        this.lang("ru_ru").add(helper.entryName(), "Summoning Rituals");

        helper.entry("possession_rituals");
        this.lang("ru_ru").add(helper.entryName(), "Possession Rituals");

        helper.entry("crafting_rituals");
        this.lang("ru_ru").add(helper.entryName(), "Binding Rituals");

        helper.entry("familiar_rituals");
        this.lang("ru_ru").add(helper.entryName(), "Familiar Rituals");
    }

    private void addSummoningRitualsCategory(BookContextHelper helper) {
        helper.category("summoning_rituals");
        this.lang("ru_ru").add(helper.categoryName(), "Summoning Rituals");

        helper.entry("overview");
        this.lang("ru_ru").add(helper.entryName(), "Summoning Rituals");

        helper.page("intro");
        this.lang("ru_ru").add(helper.pageTitle(), "Summoning Rituals");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        Summon rituals force spirits to enter this world in their chosen shape, leading to little restrictions on their power, but expose them to essence decay. Summoned spirits range from trade spirits that trade and convert items, to slave-like helpers for manual labour.
                         """);

        helper.entry("return_to_rituals");
        this.lang("ru_ru").add(helper.entryName(), "Return to Rituals Category");

        helper.entry("summon_crusher_t1");
        //Moved to OccultismBookProvider#makeSummonCrusherT1Entry

        helper.entry("summon_crusher_t2");
        this.lang("ru_ru").add(helper.entryName(), "Summon Djinni Crusher");

        helper.page("intro");
        this.lang("ru_ru").add(helper.pageTitle(), "Djinni Crusher");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        The djinni crusher is resistant essence decay and faster and more efficient than the foliot crusher.
                        \\
                        \\
                        It will crush **one** ore into **three** corresponding dusts.
                         """);

        helper.page("ritual");
        //no text

        helper.entry("summon_crusher_t3");
        this.lang("ru_ru").add(helper.entryName(), "Summon Afrit Crusher");

        helper.page("intro");
        this.lang("ru_ru").add(helper.pageTitle(), "Afrit Crusher");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        The afrit crusher is resistant to essence decay and faster and more efficient than the djinni crusher.
                        \\
                        \\
                        It will crush **one** ore into **four** corresponding dusts.
                          """);

        helper.page("ritual");
        //no text

        helper.entry("summon_crusher_t4");
        this.lang("ru_ru").add(helper.entryName(), "Summon Marid Crusher");

        helper.page("intro");
        this.lang("ru_ru").add(helper.pageTitle(), "Marid Crusher");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        The marid crusher is resistant to essence decay and faster and more efficient than the afrit crusher.
                        \\
                        \\
                        It will crush **one** ore into **six** corresponding dusts.
                          """);

        helper.page("ritual");
        //no text


        helper.entry("summon_lumberjack");
        //Moved to OccultismBookProvider#makeSummonLumberjackEntry

        helper.entry("summon_transport_items");
        //Moved to OccultismBookProvider#makeSummonTransportItemsEntry


        helper.entry("summon_cleaner");
        //Moved to OccultismBookProvider#makeSummonCleanerEntry

        helper.entry("summon_manage_machine");
        this.lang("ru_ru").add(helper.entryName(), "Summon Djinni Machine Operator");

        helper.page("intro");
        this.lang("ru_ru").add(helper.pageTitle(), "Djinni Machine Operator");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        The machine operator transfers items specified in the dimensional storage actuator GUI, to it's managed machine, and returns crafting results to the storage system. It can also be used to automatically empty a chest into the storage actuator.
                        \\
                        Basically, on-demand crafting!
                          """);

        helper.page("ritual");
        //no text

        helper.page("tutorial");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        To use the machine operator use the book of calling to link a Storage Actuator, the machine and optionally a separate extract location (the face you click on will be extracted from!). For the machine you can additionally set a custom name and the insert/extract facings.
                          """);

        helper.page("tutorial2");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        Please note that setting a new machine (or configuring it with the book of calling) will reset the extraction settings.
                        \\
                        \\
                        For an easy start, make sure to view the short [Tutorial Video](https://gyazo.com/237227ba3775e143463b31bdb1b06f50)!
                          """);

        helper.page("book_of_calling");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        If you lose the book of calling, you can craft a new one.
                        [#](%1$s)Shift-right-click[#]() the spirit with the crafted book to assign it.
                        """.formatted(COLOR_PURPLE));

        helper.entry("trade_spirits");
        this.lang("ru_ru").add(helper.entryName(), "Trade Spirits");

        helper.page("intro");
        this.lang("ru_ru").add(helper.pageTitle(), "Trade Spirits");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        Trade spirits pick up appropriate items and throw trade results on the ground. The spirit is only actively exchanging items if purple particles spawn around it.
                        \\
                        \\
                        **If you do not see any particles**, ensure that you gave the proper item and amount.
                           """);

        helper.page("intro2");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        Most trade spirits experience extreme essence decay and will return to [#](%1$s)The Other Place[#]() quickly.
                           """.formatted(COLOR_PURPLE));

        helper.entry("summon_otherworld_sapling_trader");
        this.lang("ru_ru").add(helper.entryName(), "Summon Otherworld Sapling Trader");

        helper.page("intro");
        this.lang("ru_ru").add(helper.pageTitle(), "Otherworld Sapling Trader");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        Otherworld Trees grown from natural Otherworld Saplings can only be harvested when under the effect of [#](%1$s)Third Eye[#](). To make life easier, the Otherworld Sapling Trader will exchange such natural saplings for a stable variant that can be harvested by anyone, and will drop the same stable saplings when harvested.
                           """.formatted(COLOR_PURPLE));

        helper.page("trade");
        //no text

        helper.page("ritual");
        //no text

        helper.entry("summon_otherstone_trader");
        this.lang("ru_ru").add(helper.entryName(), "Summon Otherstone Trader");

        helper.page("intro");
        this.lang("ru_ru").add(helper.pageTitle(), "Otherstone Trader");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        The Otherstone Trader spirit allows to get more [](item://occultism:otherstone) than using [](item://occultism:spirit_fire). Thus it is especially efficient if you want to use Otherstone as a building material.
                           """);

        helper.page("trade");
        //no text

        helper.page("ritual");
        //no text

        helper.entry("summon_wild_parrot");
        this.lang("ru_ru").add(helper.entryName(), "Summon Wild Parrot");

        helper.page("entity");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        **Provides**: A tameable Parrot
                          """);

        helper.page("ritual");
        //no text

        helper.page("description");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        In this ritual a [#](%1$s)Foliot[#]() is summoned **as an untamed spirit**.
                        \\
                        \\
                        The slaughter of a [#](%1$s)Chicken[#]() and the offering of dyes are intended to entice the Foliot to take the shape of a parrot. As [#](%1$s)Foliot[#]() are not among the smartest spirits, they sometimes misunderstand the instructions ...
                          """.formatted(COLOR_PURPLE));

        helper.page("description2");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        *This means, if a [#](%1$s)Chicken[#]() is spawned, that's not a bug, just bad luck!*
                           """.formatted(COLOR_PURPLE));

        helper.entry("summon_wild_otherworld_bird");
        this.lang("ru_ru").add(helper.entryName(), "Summon Wild Drikwing");

        helper.page("entity");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        **Provides**: A tameable Drikwing
                          """);

        helper.page("ritual");
        //no text

        helper.page("description");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        See [Drikwing Familiar](entry://familiar_rituals/familiar_otherworld_bird) for more information.
                          """);

        helper.entry("weather_magic");
        this.lang("ru_ru").add(helper.entryName(), "Weather Magic");

        helper.page("intro");
        this.lang("ru_ru").add(helper.pageTitle(), "Weather Magic");
        this.lang("ru_ru").add(helper.pageText(),
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
        this.lang("ru_ru").add(helper.entryName(), "Time Magic");

        helper.page("intro");
        this.lang("ru_ru").add(helper.pageTitle(), "Time Magic");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        Time magic is limited in scope, it cannot send the magician back or forth in time, but rather allows to change time time of day. This is especially useful for rituals or other tasks requiring day- or nighttime specifically.
                        \\
                        \\
                        Time spirits will only modify the time once and then vanish.
                           """);

        helper.page("ritual_day");
        //no text

        helper.page("ritual_night");
        //no text

        helper.entry("wither_skull");
        this.lang("ru_ru").add(helper.entryName(), "Wither Skeleton Skull");

        helper.page("intro");
        this.lang("ru_ru").add(helper.pageTitle(), "Wither Skeleton Skull");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        Besides venturing into nether dungeons, there is one more way to get these skulls. The legendary [#](%1$s)Wild Hunt[#]() consists of [#](%1$s)Greater Spirits[#]() taking the form of wither skeletons. While summoning the Wild Hunt is incredibly dangerous, it is the fastest way to get wither skeleton skulls.
                           """.formatted(COLOR_PURPLE));

        helper.page("ritual");
        //no text

        helper.entry("afrit_essence");
        this.lang("ru_ru").add(helper.entryName(), "Afrit Essence");

        helper.page("intro");
        this.lang("ru_ru").add(helper.pageTitle(), "Afrit Essence");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        [](item://occultism:afrit_essence) is required to safely call on the more powerful spirits, commonly used in the form of red chalk. To obtain the essence, an [#](%1$s)Afrit[#]() needs to be summoned unbound into this plane, and killed. Be warned that this is no simple endeavour, and unbound spirit presents great danger to all nearby.
                           """.formatted(COLOR_PURPLE));

        helper.page("ritual");
        //no text
    }

    private void addPossessionRitualsCategory(BookContextHelper helper) {
        helper.category("possession_rituals");
        this.lang("ru_ru").add(helper.categoryName(), "Possession Rituals");

        helper.entry("return_to_rituals");
        this.lang("ru_ru").add(helper.entryName(), "Return to Rituals Category");

        helper.entry("overview");
        this.lang("ru_ru").add(helper.entryName(), "Possession Rituals");

        helper.page("intro");
        this.lang("ru_ru").add(helper.pageTitle(), "Possession Rituals");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        Possession rituals bind spirits into living beings, giving the summoner a degree of control over the possessed being.
                        \\
                        \\
                        As such these rituals are used to obtain rare items without having to venture into dangerous places.
                           """);

        helper.entry("possess_enderman");
        this.lang("ru_ru").add(helper.entryName(), "Possessed Enderman");

        helper.page("entity");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        **Drops**: 1-3x [](item://minecraft:ender_pearl)
                                """);

        helper.page("ritual");
        //no text

        helper.page("description");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        In this ritual an [#](%1$s)Enderman[#]() is spawned using the life energy of a [#](%1$s)Pig[#]() and immediately possessed by the summoned [#](%1$s)Djinni[#](). The [#](%1$s)Possessed Enderman[#]() will always drop at least one [](item://minecraft:ender_pearl) when killed.
                                """.formatted(COLOR_PURPLE));

        helper.entry("possess_endermite");
        this.lang("ru_ru").add(helper.entryName(), "Possessed Endermite");

        helper.page("entity");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        **Drops**: 1-2x [](item://minecraft:end_stone)
                                """);

        helper.page("ritual");
        //no text

        helper.page("description");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        In this ritual an [#](%1$s)Endermite[#]() is tricked into spawning. The stone and dirt represent the surroundings, then an egg is thrown to simulate the use of an ender pearl. When the mite spawns, the summoned [#](%1$s)Foliot[#]() immediately possesses it, visits [#](%1$s)The End[#](), and returns. The [#](%1$s)Possessed Endermite[#]() will always drop at least one [](item://minecraft:end_stone) when killed.
                                """.formatted(COLOR_PURPLE));

        helper.entry("possess_ghast");
        //moved to OccultismBookProvider#makePossessGhastEntry

        helper.entry("possess_skeleton");
        this.lang("ru_ru").add(helper.entryName(), "Possessed Skeleton");

        helper.page("entity");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        **Drops**: 1x [](item://minecraft:skeleton_skull)
                                """);

        helper.page("ritual");
        //no text

        helper.page("description");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        In this ritual an [#](%1$s)Skeleton[#]() is spawned using the life energy of a [#](%1$s)Chicken[#]() and possessed by a [#](%1$s)Foliot[#](). The [#](%1$s)Possessed Skeleton[#]() will be immune to daylight and always drop at least one [](item://minecraft:skeleton_skull) when killed.
                                """.formatted(COLOR_PURPLE));
    }

    private void addCraftingRitualsCategory(BookContextHelper helper) {
        helper.category("crafting_rituals");
        this.lang("ru_ru").add(helper.categoryName(), "Binding Rituals");

        helper.entry("return_to_rituals");
        this.lang("ru_ru").add(helper.entryName(), "Return to Rituals Category");

        helper.entry("overview");
        this.lang("ru_ru").add(helper.entryName(), "Binding Rituals");

        helper.page("intro");
        this.lang("ru_ru").add(helper.pageTitle(), "Binding Rituals");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        Binding rituals infuse spirits into items, where their powers are used for one specific purpose. The created items can act like simple empowering enchantments, or fulfill complex tasks to aid the summoner.
                           """);

        helper.entry("craft_storage_system");
        this.lang("ru_ru").add(helper.entryName(), "Magic Storage");

        helper.page("spotlight");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        The following entries show only the rituals related to the Magic Storage system. For full step-by-step instructions on building the storage system, see the [Magic Storage](category://storage) category.
                           """.formatted(COLOR_PURPLE));

        helper.entry("craft_dimensional_matrix");
        this.lang("ru_ru").add(helper.entryName(), "Dimensional Matrix");

        helper.page("spotlight");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        The dimensional matrix is the entry point to a small dimension used for storing items. A [#](%1$s)Djinni[#]() bound to the matrix keeps the dimension stable, often supported by additional spirits in storage stabilizers, to increase the dimension size.
                           """.formatted(COLOR_PURPLE));

        helper.page("ritual");
        //no text

        helper.entry("craft_dimensional_mineshaft");
        this.lang("ru_ru").add(helper.entryName(), "Dimensional Mineshaft");

        helper.page("spotlight");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        The dimensional mineshaft houses a [#](%1$s)Djinni[#]() which opens up a stable connection into an uninhabited dimension, perfectly suited for mining. While the portal is too small to transfer humans, other spirits can use it to enter the mining dimension and bring back resources.
                           """.formatted(COLOR_PURPLE));

        helper.page("ritual");
        //no text

        helper.page("description");
        this.lang("ru_ru").add(helper.pageTitle(), "Operation");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        The dimensional mineshaft will discard any items it cannot store, so it is important to regularly empty the mineshaft, either manually, with hoppers or using a transporter spirit. Spirits in lamps can be **inserted** from the top, all other sides can be used to **extract** items.
                           """.formatted(COLOR_PURPLE));


        helper.entry("craft_infused_pickaxe");
        this.lang("ru_ru").add(helper.entryName(), "Infused Pickaxe");

        helper.page("spotlight");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        Otherworld ores usually can only be mined with Otherworld metal tools. The [](item://occultism:infused_pickaxe) is a makeshift solution to this Chicken-and-Egg problem. Brittle spirit attuned gems house a [#](%1$s)Djinni[#]() that allows harvesting the ores, but the durability is extremely low. A more durable version is the [Iesnium Pickaxe](entry://getting_started/iesnium_pickaxe).
                           """.formatted(COLOR_PURPLE));

        helper.page("ritual");
        //no text

        helper.entry("craft_otherworld_goggles");
        this.lang("ru_ru").add(helper.entryName(), "Craft Otherworld Goggles");

        helper.page("goggles_spotlight");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        The [](item://occultism:otherworld_goggles) give the wearer permanent [#](%1$s)Third Eye[#](), allowing to view even blocks hidden from those partaking of [Demon's Dream](entry://occultism:dictionary_of_spirits/getting_started/demons_dream).
                        \\
                        \\
                        This elegantly solves the general issue of summoners being in a drugged haze, causing all sorts of havoc.
                        """.formatted(COLOR_PURPLE));

        helper.page("goggles_more");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        The Goggles will, however, not give the ability to harvest otherworld materials. That means when wearing goggles, an [Infused Pick](entry://getting_started/infused_pickaxe), or even better, an [Iesnium Pick](entry://getting_started/iesnium_pickaxe) needs to be used to break blocks in order to obtain their Otherworld variants.
                        """.formatted(COLOR_PURPLE));

        helper.page("lenses_spotlight");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        Otherworld Goggles make use of a [#](%1$s)Foliot[#]() bound into the lenses. The Foliot shares it's ability to view higher planes with the wearer, thus allowing them to see Otherworld materials.
                         """.formatted(COLOR_PURPLE));

        helper.page("lenses_more");
        this.lang("ru_ru").add(helper.pageTitle(), "Crafting Lenses");
        this.lang("ru_ru").add(helper.pageText(),
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
        this.lang("ru_ru").add(helper.entryName(), "Storage Actuator Base");

        helper.page("spotlight");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        The storage actuator base imprisons a [#](%1$s)Foliot[#]() responsible for interacting with items in a dimensional storage matrix.
                        """.formatted(COLOR_PURPLE));

        helper.page("ritual");
        //no text

        helper.entry("craft_stabilizer_tier1");
        this.lang("ru_ru").add(helper.entryName(), "Storage Stabilizer Tier 1");

        helper.page("spotlight");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        This simple storage stabilizer is inhabited by a [#](%1$s)Foliot[#]() that supports the dimensional matrix in keeping the storage dimension stable, thus allowing to store more items.
                        \\
                        \\
                        By default each Tier 1 Stabilizer adds **64** item types and 512000 items storage capacity.
                        """.formatted(COLOR_PURPLE));

        helper.page("ritual");
        //no text

        helper.entry("craft_stabilizer_tier2");
        this.lang("ru_ru").add(helper.entryName(), "Storage Stabilizer Tier 2");

        helper.page("spotlight");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        This improved stabilizer is inhabited by a [#](%1$s)Djinni[#]() that supports the dimensional matrix in keeping the storage dimension stable, thus allowing to store even more items.
                        \\
                        \\
                        By default each Tier 2 Stabilizer adds **128** item types and 1024000 items storage capacity.
                        """.formatted(COLOR_PURPLE));

        helper.page("ritual");
        //no text

        helper.entry("craft_stabilizer_tier3");
        this.lang("ru_ru").add(helper.entryName(), "Storage Stabilizer Tier 3");

        helper.page("spotlight");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        This advanced stabilizer is inhabited by an [#](%1$s)Afrit[#]() that supports the dimensional matrix in keeping the storage dimension stable, thus allowing to store even more items.
                        \\
                        \\
                        By default each Tier 3 Stabilizer adds **256** item types and 2048000 items storage capacity.
                        """.formatted(COLOR_PURPLE));

        helper.page("ritual");
        //no text

        helper.entry("craft_stabilizer_tier4");
        this.lang("ru_ru").add(helper.entryName(), "Storage Stabilizer Tier 4");

        helper.page("spotlight");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        This highly advanced stabilizer is inhabited by a [#](%1$s)Marid[#]() that supports the dimensional matrix in keeping the storage dimension stable, thus allowing to store even more items.
                        \\
                        \\
                        By default each Tier 4 Stabilizer adds **512** item types and 4098000 items storage capacity.
                        """.formatted(COLOR_PURPLE));

        helper.page("ritual");
        //no text

        helper.entry("craft_stable_wormhole");
        this.lang("ru_ru").add(helper.entryName(), "Stable Wormhole");

        helper.page("spotlight");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        The stable wormhole allows access to a dimensional matrix from a remote destination.
                        \\
                        \\
                        Shift-click a [](item://occultism:storage_controller) to link it, then place the wormhole in the world to use it as a remote access point.
                         """.formatted(COLOR_PURPLE));

        helper.page("ritual");
        //no text

        helper.entry("craft_storage_remote");
        this.lang("ru_ru").add(helper.entryName(), "Remote Storage Accessor");

        helper.page("spotlight");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        The [](item://occultism:storage_remote) can be linked to a [](item://occultism:storage_controller) by shift-clicking. The [#](%1$s)Djinni[#]() bound to the accessor will then be able to access items from the actuator even from across dimensions.
                        """.formatted(COLOR_PURPLE));

        helper.page("ritual");
        //no text

        helper.entry("craft_foliot_miner");
        this.lang("ru_ru").add(helper.entryName(), "Foliot Miner");

        helper.page("intro");
        this.lang("ru_ru").add(helper.pageTitle(), "Foliot Miner");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        Miner spirits use [](item://occultism:dimensional_mineshaft) to acquire resources from other dimensions. They are summoned and bound into magic lamps, which they can leave only through the mineshaft. The magic lamp degrades over time, once it breaks the spirit is released back to [#](%1$s)The Other Place[#]().
                        """.formatted(COLOR_PURPLE));

        helper.page("magic_lamp");
        this.lang("ru_ru").add(helper.pageTitle(), "Magic Lamp");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        To summon miner spirits, you first need to craft a [Magic Lamp](entry://getting_started/magic_lamps) to hold them. The key ingredient for that is [Iesnium](entry://getting_started/iesnium).
                        """.formatted(COLOR_PURPLE));

        helper.page("magic_lamp_recipe");
        //no text

        helper.page("spotlight");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        The [#](%1$s)Foliot[#]() miner harvests block without much aim and returns anything it finds. The mining process is quite slow, due to this the Foliot expends only minor amounts of energy, damaging the lamp it is housed in slowly over time.
                        """.formatted(COLOR_PURPLE));

        helper.page("ritual");
        //no text

        helper.entry("craft_djinni_miner");
        this.lang("ru_ru").add(helper.entryName(), "Djinni Miner");

        helper.page("spotlight");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        The [#](%1$s)Djinni[#]() miner harvests ores specifically. By discarding other blocks it is able to mine faster and more efficiently. The greater power of the djinni it damages the magic lamp relatively quickly.
                        """.formatted(COLOR_PURPLE));

        helper.page("ritual");
        //no text

        helper.entry("craft_afrit_miner");
        this.lang("ru_ru").add(helper.entryName(), "Afrit Miner");

        helper.page("spotlight");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        The [#](%1$s)Afrit[#]() miner harvests ores, like djinni miners, and additionally mines deepslate ores. This miner is faster and more efficient than the djinnis, thus damaging the magic lamp even more slowly.
                        """.formatted(COLOR_PURPLE));

        helper.page("ritual");
        //no text

        helper.entry("craft_marid_miner");
        this.lang("ru_ru").add(helper.entryName(), "Marid Miner");

        helper.page("spotlight");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        The [#](%1$s)Marid[#]() miner is the most powerful miner spirit, it has the fasted mining speed and best magic lamp preservation. Unlike other miner spirits they also can mine the rarest ores, such as [](item://minecraft:ancient_debris) and [](item://occultism:iesnium_ore).
                        """.formatted(COLOR_PURPLE));

        helper.page("ritual");
        //no text

        helper.entry("craft_satchel");
        this.lang("ru_ru").add(helper.entryName(), "Surprisingly Substantial Satchel");

        helper.page("spotlight");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        A [#](%1$s)Foliot[#]() is bound to the satchel, tasked with **slightly** warping reality. This allows to store more items in the satchel than it's size would indicate, making it a practical traveller's companion.
                        """.formatted(COLOR_PURPLE));

        helper.page("ritual");
        //no text

        helper.entry("craft_soul_gem");
        this.lang("ru_ru").add(helper.entryName(), "Soul Gem");

        helper.page("spotlight");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        Soul gems are diamonds set in precious metals, which are then infused with a [#](%1$s)Djinni[#](). The spirit creates a small dimension that allows the temporary entrapment of living beings. Beings of great power or size cannot be stored, however.
                        """.formatted(COLOR_PURPLE));

        helper.page("usage");
        this.lang("ru_ru").add(helper.pageTitle(), "Usage");
        this.lang("ru_ru").add(helper.pageText(),
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
        this.lang("ru_ru").add(helper.entryName(), "Familiar Ring");

        helper.page("spotlight");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        Familiar Rings consist of a [](item://occultism:soul_gem), that contains a [#](%1$s)Djinni[#](), mounted on a ring. The [#](%1$s)Djinni[#]() in the ring allows the familiar captured in the soul gem to apply effects to the wearer."
                        """.formatted(COLOR_PURPLE));

        helper.page("usage");
        this.lang("ru_ru").add(helper.pageTitle(), "Usage");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        To use a [](item://occultism:familiar_ring), simply capture a summoned (and tamed) familiar by [#](%1$s)right-clicking[#]() it, and then wear the ring as [#](%1$s)Curio[#]() to make use of the effects the familiar provides.
                        \\
                        \\
                        When released from a familiar ring, the spirit will recognize the person releasing them as their new master.
                        """.formatted(COLOR_PURPLE));

        helper.page("ritual");
        //no text
        helper.entry("craft_wild_trim");
        this.lang("ru_ru").add(helper.entryName(), "Forge Wild Trim");

        helper.page("spotlight");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        Unlike other rituals, creating a [](item://minecraft:wild_armor_trim_smithing_template) is a service provided by a Marid that is not bound to the final object. You sacrifice the items and the Marid uses his power to forge that item for you.
                        """.formatted(COLOR_PURPLE));

        helper.page("ritual");
    }

    private void addFamiliarRitualsCategory(BookContextHelper helper) {
        helper.category("familiar_rituals");

        helper.entry("return_to_rituals");
        this.lang("ru_ru").add(helper.entryName(), "Return to Rituals Category");

        helper.entry("overview");
        this.lang("ru_ru").add(helper.entryName(), "Familiar Rituals");

        helper.page("intro");
        this.lang("ru_ru").add(helper.pageTitle(), "Familiar Rituals");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        Familiar rituals summon spirits to aid the summoner directly. The spirits usually inhabit an animal's body, allowing them to resist essence decay. Familiars provide buffs, but may also actively protect the summoner.
                                """.formatted(COLOR_PURPLE));

        helper.page("ring");
        this.lang("ru_ru").add(helper.pageTitle(), "Equipping Familiars");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        Enterprising summoners have found a way to bind familiars into jewelry that passively applies their buff, the [Familiar Ring](entry://crafting_rituals/craft_familiar_ring).
                                """.formatted(COLOR_PURPLE));

        helper.page("trading");
        this.lang("ru_ru").add(helper.pageTitle(), "Equipping Familiars");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        "Familiars can be easily traded when in a [Familiar Ring](entry://crafting_rituals/craft_familiar_ring).
                        \\
                        \\
                        When released, the spirit will recognize the person releasing them as their new master.
                                 """.formatted(COLOR_PURPLE));

        helper.entry("familiar_bat");
        this.lang("ru_ru").add(helper.entryName(), "Bat Familiar");

        helper.page("entity");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        **Provides**: [#](%1$s)Night Vision[#]()
                               """.formatted(COLOR_PURPLE));

        helper.page("ritual");
        //no text

        helper.page("description");
        this.lang("ru_ru").add(helper.pageTitle(), "Description");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        **Upgrade Behaviour**\\
                        When upgraded by a blacksmith familiar, the bat familiar will give a life steal effect to it's master.
                           """.formatted(COLOR_PURPLE));

        helper.entry("familiar_beaver");
        this.lang("ru_ru").add(helper.entryName(), "Beaver Familiar");

        helper.page("entity");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        **Provides**: [#](%1$s)Increased wood break speed[#]()
                               """.formatted(COLOR_PURPLE));

        helper.page("ritual");
        //no text

        helper.page("description");
        this.lang("ru_ru").add(helper.pageTitle(), "Description");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        The Beaver familiar will chop down nearby trees when they grow from a sapling into a tree. It can only handle small trees.
                        \\
                        \\
                        **Upgrade Behaviour**\\
                        Cannot be upgraded by the blacksmith familiar.
                           """.formatted(COLOR_PURPLE));

        helper.entry("familiar_beholder");
        this.lang("ru_ru").add(helper.entryName(), "Beholder Familiar");

        helper.page("entity");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        **Provides**: [#](%1$s)Highlights enemies[#](), [#](%1$s)Shoots **FREAKING LAZORS**[#]()
                               """.formatted(COLOR_PURPLE));

        helper.page("ritual");
        //no text

        helper.page("description");
        this.lang("ru_ru").add(helper.pageTitle(), "Description");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        The Beholder familiar highlights nearby entities with a glow effect, and shoots laser rays at enemies. It **eats** (poor) **Shub Niggurath babies** to gain temporary damage and speed.
                        \\
                        \\
                        **Upgrade Behaviour**\\
                        When upgraded by a blacksmith familiar, it give it's master immunity to blindness.
                           """.formatted(COLOR_PURPLE));

        helper.entry("familiar_blacksmith");
        this.lang("ru_ru").add(helper.entryName(), "Blacksmith Familiar");

        helper.page("entity");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        **Provides**: [#](%1$s)Repairs Equipment while Mining[#](), [#](%1$s)Upgrades other familiars[#]()
                               """.formatted(COLOR_PURPLE));

        helper.page("ritual");
        //no text

        helper.page("description");
        this.lang("ru_ru").add(helper.pageTitle(), "Description");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        Whenever the player picks up stone, there is a chance for the blacksmith familiar to repair their equipment a little bit.
                        \\
                        \\
                        **Upgrade Behaviour**: \\
                        Cannot be upgraded, but upgrades other Familiars.
                           """.formatted(COLOR_PURPLE));

        helper.page("description2");
        this.lang("ru_ru").add(helper.pageTitle(), "Upgrading Familiars");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        To upgrade other familiars the blacksmith needs to be given iron ingots or blocks by [#](%1$s)right-clicking[#]() it.
                        \\
                        \\
                        Upgraded familiars provide additional effects.
                           """.formatted(COLOR_PURPLE));

        helper.entry("familiar_chimera");
        this.lang("ru_ru").add(helper.entryName(), "Chimera Familiar");

        helper.page("entity");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        **Provides**: [#](%1$s)Rideable Mount[#]()
                               """.formatted(COLOR_PURPLE));

        helper.page("ritual");
        //no text

        helper.page("description");
        this.lang("ru_ru").add(helper.pageTitle(), "Description");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        The chimera familiar can be fed (any) meat to grow, when growing it will gain damage and speed. Once it has grown big enough, players can ride it. When feeding it a [](item://minecraft:golden_apple) the [#](%1$s)Goat[#]() will detach and become a separate familiar.
                        \\
                        \\
                        The detached goat familiar can be used to obtain the [Shub Niggurath](entry://familiar_rituals/familiar_shub_niggurath) familiar.
                           """.formatted(COLOR_PURPLE));

        helper.page("description2");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        **Upgrade Behaviour**\\
                        When upgraded by a blacksmith familiar, the goat familiar will get a warning bell. When you hit the familiar it will ring the bell and attract enemies in a large radius.
                           """.formatted(COLOR_PURPLE));

        helper.entry("familiar_cthulhu");
        this.lang("ru_ru").add(helper.entryName(), "Cthulhu Familiar");

        helper.page("entity");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        **Provides**: [#](%1$s)Water Breathing[#](), [#](%1$s)General Coolness[#]()
                               """.formatted(COLOR_PURPLE));

        helper.page("ritual");
        //no text

        helper.page("description");
        this.lang("ru_ru").add(helper.pageTitle(), "Description");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        **Upgrade Behaviour**\\
                        When upgraded by a blacksmith familiar, it will act as a mobile light source.
                           """.formatted(COLOR_PURPLE));

        helper.entry("familiar_shub_niggurath");
        this.lang("ru_ru").add(helper.entryName(), "Shub Niggurath Familiar");

        helper.page("entity");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        **Provides**: [#](%1$s)Spawns small versions of itself to fight for you.[#]()
                                   """.formatted(COLOR_PURPLE));

        helper.page("ritual");
        this.lang("ru_ru").add(helper.pageTitle(), "Ritual");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        The [#](%1$s)Shub Niggurath[#]() is not summoned directly. First, summon a [Chimera Familiar](entry://familiar_rituals/familiar_chimera) and feed it a [](item://minecraft:golden_apple) to detach the [#](%1$s)Goat[#](). Bring the goat to a [#](%1$s)Forest Biome[#](). Then click the goat with [any Black Dye](item://minecraft:black_dye), [](item://minecraft:flint) and [](item://minecraft:ender_eye) to summon the [#](%1$s)Shub Niggurath[#]().
                           """.formatted(COLOR_PURPLE));

        helper.page("description");
        this.lang("ru_ru").add(helper.pageTitle(), "Description");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        **Upgrade Behaviour**\\
                        When upgraded by a blacksmith familiar, it will get a warning bell. When you hit the familiar it will ring the bell and **attract enemies** in a large radius.
                           """.formatted(COLOR_PURPLE));

        helper.entry("familiar_deer");
        this.lang("ru_ru").add(helper.entryName(), "Deer Familiar");

        helper.page("entity");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        **Provides**: [#](%1$s)Jump Boost[#]()
                                   """.formatted(COLOR_PURPLE));

        helper.page("ritual");
        //no text

        helper.page("description");
        this.lang("ru_ru").add(helper.pageTitle(), "Description");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        **Upgrade Behaviour**\\
                        When upgraded by a blacksmith familiar, it will attack nearby enemies with a hammer. Yep, a **hammer**.
                           """.formatted(COLOR_PURPLE));

        helper.entry("familiar_devil");
        this.lang("ru_ru").add(helper.entryName(), "Devil Familiar");

        helper.page("entity");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        **Provides**: [#](%1$s)Fire Resistance[#](), [#](%1$s)Attacks Enemies[#]()
                                   """.formatted(COLOR_PURPLE));

        helper.page("ritual");
        //no text

        helper.page("description");
        this.lang("ru_ru").add(helper.pageTitle(), "Description");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        **Upgrade Behaviour**\\
                        Cannot be upgraded by the blacksmith familiar.
                           """.formatted(COLOR_PURPLE));

        helper.entry("familiar_dragon");
        this.lang("ru_ru").add(helper.entryName(), "Dragon Familiar");

        helper.page("entity");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        **Provides**: [#](%1$s)Increased XP[#](), Loves Sticks
                                   """.formatted(COLOR_PURPLE));

        helper.page("ritual");
        //no text

        helper.page("description");
        this.lang("ru_ru").add(helper.pageTitle(), "Description");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        Greedy familiars can ride on dragon familiars, giving the dragon the greedy effects additionally.
                        \\
                        \\
                        **Upgrade Behaviour**\\
                        When upgraded by a blacksmith familiar, it will throw swords at nearby enemies.
                           """.formatted(COLOR_PURPLE));


        helper.entry("familiar_fairy");
        this.lang("ru_ru").add(helper.entryName(), "Fairy Familiar");

        helper.page("entity");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        **Provides**: [#](%1$s)Assists Familiars[#](), [#](%1$s)Prevents Familiar Deaths[#](), [#](%1$s)Drains Enemy Life Force[#]()
                                   """.formatted(COLOR_PURPLE));

        helper.page("ritual");
        //no text

        helper.page("description");
        this.lang("ru_ru").add(helper.pageTitle(), "Description");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        The Fairy familiar **keeps other familiars from dying** (with cooldown), helps out other familiars with **beneficial effects** and **drains the life force of enemies** to assist their master.
                        \\
                        \\
                        **Upgrade Behaviour**\\
                        Cannot be upgraded by the blacksmith familiar.
                           """.formatted(COLOR_PURPLE));

        helper.entry("familiar_greedy");
        this.lang("ru_ru").add(helper.entryName(), "Greedy Familiar");

        helper.page("entity");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        **Provides**: [#](%1$s)Picks up Items[#](), [#](%1$s)Increased Pick-up Range[#]()
                                   """.formatted(COLOR_PURPLE));
        helper.page("ritual");
        //no text

        helper.page("description");
        this.lang("ru_ru").add(helper.pageTitle(), "Description");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        The greedy familiar is a Foliot that will pick up nearby items for it's master. When captured in a familiar ring it increased the pick-up range of the wearer.
                        \\
                        \\
                        **Upgrade Behaviour**\\
                        When upgraded by a blacksmith familiar, it can find blocks for its master. [#](%1$s)Right-click[#]() it with a block to tell it what to look for.
                           """.formatted(COLOR_PURPLE));

        helper.entry("familiar_guardian");
        this.lang("ru_ru").add(helper.entryName(), "Guardian Familiar");

        helper.page("entity");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        **Provides**: [#](%1$s)Prevents player death while alive[#]()
                                   """.formatted(COLOR_PURPLE));

        helper.page("ritual");
        //no text

        helper.page("description");
        this.lang("ru_ru").add(helper.pageTitle(), "Description");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        The guardian familiar sacrifices a limb everytime it's master is about to die and thus **prevents the death**. Once the guardian dies, the player is no longer protected. When summoned, the guardian spawns with a **random amount of limbs**, there is no guarantee that a complete guardian is summoned.
                           """.formatted(COLOR_PURPLE));

        helper.page("description2");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        **Upgrade Behaviour**\\
                        When upgraded by a blacksmith familiar, it regains a limb (can only be done once).
                           """.formatted(COLOR_PURPLE));

        helper.entry("familiar_headless");
        this.lang("ru_ru").add(helper.entryName(), "Headless Ratman Familiar");

        helper.page("entity");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        **Provides**: [#](%1$s)Conditional Damage Buff[#]()
                                   """.formatted(COLOR_PURPLE));

        helper.page("ritual");
        //no text

        helper.page("description");
        this.lang("ru_ru").add(helper.pageTitle(), "Description");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        The headless ratman familiar steals heads of mobs near the ratman when they are killed. It then provides a damage buff against that type of mob to their master. If the ratman drops **below 50%% health** it dies, but can then be rebuilt by their master by giving them [](item://minecraft:wheat), [](item://minecraft:stick), [](item://minecraft:hay_block) and a [](item://minecraft:carved_pumpkin).
                           """.formatted(COLOR_PURPLE));

        helper.page("description2");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        **Upgrade Behaviour**\\
                        When upgraded by a blacksmith familiar, it will give weakness to nearby mobs of the type it stole the head from.
                           """.formatted(COLOR_PURPLE));


        helper.entry("familiar_mummy");
        this.lang("ru_ru").add(helper.entryName(), "Mummy Familiar");

        helper.page("entity");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        **Provides**: [#](%1$s)Fights your enemies[#](), [#](%1$s)Dodge Effect[#]()
                                   """.formatted(COLOR_PURPLE));

        helper.page("ritual");
        //no text

        helper.page("description");
        this.lang("ru_ru").add(helper.pageTitle(), "Description");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        The Mummy familiar is a martial arts expert and fights to protect their master.
                        \\
                        \\
                        **Upgrade Behaviour**\\
                        When upgraded by a blacksmith familiar, it the familiar will deal even more damage.
                            """.formatted(COLOR_PURPLE));

        helper.entry("familiar_otherworld_bird");
        this.lang("ru_ru").add(helper.entryName(), "Drikwing Familiar");

        helper.page("entity");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        **Provides**: [#](%1$s)Multi-Jump[#](), [#](%1$s)Jump Boost[#](), [#](%1$s)Slow Falling[#]()
                                   """.formatted(COLOR_PURPLE));

        helper.page("ritual");
        //no text

        helper.page("description");
        this.lang("ru_ru").add(helper.pageTitle(), "Description");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        [#](%1$s)Drikwings$[#]() are a subclass of [#](%1$s)Djinni[#]() that are known to be amicable towards humans. They usually take the shape of a dark blue and purple parrot. Drikwings will provide their owner with limited flight abilities when nearby.
                        \\
                        \\
                        **Upgrade Behaviour**\\
                        Cannot be upgraded by the blacksmith familiar.
                            """.formatted(COLOR_PURPLE));

        helper.page("description2");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        To obtain the parrot or parrot familiar for the sacrifice, consider summoning them using either the [Wild Parrot Ritual](entry://summoning_rituals/summon_wild_parrot) or [Parrot Familiar Ritual](entry://familiar_rituals/familiar_parrot)
                        \\
                        \\
                        **Hint:** If you use mods that protect pets from death, use the wild parrot ritual!
                            """.formatted(COLOR_PURPLE));

        helper.entry("familiar_parrot");
        this.lang("ru_ru").add(helper.entryName(), "Parrot Familiar");

        helper.page("entity");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        **Provides**: [#](%1$s)Company[#]()
                                   """.formatted(COLOR_PURPLE));

        helper.page("ritual");
        //no text


        //
        helper.page("description");
        this.lang("ru_ru").add(helper.pageTitle(), "Description");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        In this ritual a [#](%1$s)Foliot[#]() is summoned **as a familiar**, the slaughter of a [#](%1$s)Chicken[#]() and the offering of dyes are intended to entice the [#](%1$s)Foliot[#]() to take the shape of a parrot.\\
                        As [#](%1$s)Foliot[#]() are not among the smartest spirits, they sometimes misunderstand the instructions ...
                            """.formatted(COLOR_PURPLE));

        helper.page("description2");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        *This means, if a [#](%1$s)Chicken[#]() is spawned, that's not a bug, just bad luck!*
                        \\
                        \\
                        **Upgrade Behaviour**\\
                        Cannot be upgraded by the blacksmith familiar.
                           """.formatted(COLOR_PURPLE));
        //no text

        helper.entry("summon_allay");
        this.lang("ru_ru").add(helper.entryName(), "Purify Vex to Allay");

        helper.page("entity");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        **Provides**: Allay
                          """);

        helper.page("ritual");

        helper.page("description");
        this.lang("ru_ru").add(helper.pageTitle(), "Description");
        this.lang("ru_ru").add(helper.pageText(),
                """
                       Purify a Vex to an Allay on a resurrection process that reveals its true name.
                         """.formatted(COLOR_PURPLE));

    }

    private void addStorageCategory(BookContextHelper helper) {
        helper.category("storage");
        this.lang("ru_ru").add(helper.categoryName(), "Magic Storage");

        helper.entry("overview");
        this.lang("ru_ru").add(helper.entryName(), "Magic Storage");

        helper.page("intro");
        this.lang("ru_ru").add(helper.pageTitle(), "Magic Storage");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        Every summoner knows the problem: There are just too many occult paraphernalia lying around. The solution is simple, yet elegant: Magic Storage!
                        \\
                        \\
                        Using Spirits able to access storage dimensions it is possible to create almost unlimited storage space.
                        """.formatted(COLOR_PURPLE));

        helper.page("intro2");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        Follow the steps shown in this category to get your own storage system!
                        The steps related to storage in [Binding Rituals](category://crafting_rituals/) show only the rituals, while here **all required steps** including crafting are shown.
                        """.formatted(COLOR_PURPLE));

        helper.entry("storage_controller");
        this.lang("ru_ru").add(helper.entryName(), "Storage Actuator");

        helper.page("intro");
        this.lang("ru_ru").add(helper.pageTitle(), "Storage Actuator");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        The [](item://occultism:storage_controller) consists of a [Dimensional Matrix](entry://crafting_rituals/craft_dimensional_matrix) inhabited by a [#](%1$s)Djinni[#]() that creates and manages a storage dimension, and a [Base](entry://crafting_rituals/craft_storage_controller_base) infused with a [#](%1$s)Foliot[#]() that moves items in and out of the storage dimension.
                        """.formatted(COLOR_PURPLE));

        helper.page("usage");
        this.lang("ru_ru").add(helper.pageTitle(), "Usage");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        After crafting the [](item://occultism:storage_controller) (see following pages), place it in the world and [#](%1$s)right-click[#]() it with an empty hand. This will open the GUI of the storage controller, from there on it will work much like a very big shulker box.
                        """.formatted(COLOR_PURPLE));

        helper.page("safety");
        this.lang("ru_ru").add(helper.pageTitle(), "Safety first!");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        Breaking the storage controller will store all contained items in the dropped item, you will not lose anything.
                        The same applies to breaking or replacing Storage Stabilizers (you will learn about these later). 
                        \\
                        \\
                        Like in a shulker box, your items are safe!
                        """.formatted(COLOR_PURPLE));


        helper.page("size");
        this.lang("ru_ru").add(helper.pageTitle(), "So much storage!");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        The storage controller holds up to **128** different types of items (_You will learn later how to increase that_). Additionally it is limited to 256000 items in total. It does not matter if you have 256000 different items or 256000 of one item, or any mix.
                        """.formatted(COLOR_PURPLE));

        helper.page("unique_items");
        this.lang("ru_ru").add(helper.pageTitle(), "Unique Items");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        Items with unique properties ("NBT data"), such as damaged or enchanted equipment will take up one item type for each variation. For example two wooden swords with two different damage values take up two item types. Two wooden swords with the same (or no) damage take up one item type.
                        """.formatted(COLOR_PURPLE));

        helper.page("config");
        this.lang("ru_ru").add(helper.pageTitle(), "Configurablity");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        The item type amount and storage size can be configured in the "[#](%1$s)occultism-server.toml[#]()" config file in the save directory of your world.
                        """.formatted(COLOR_PURPLE));

        helper.page("mods");
        this.lang("ru_ru").add(helper.pageTitle(), "Interaction with Mods");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        For other mods the storage controller behaves like a shulker box, anything that can interact with vanilla chests and shulker boxes can interact with the storage controller.
                        Devices that count storage contents may have trouble with the stack sizes.
                        """.formatted(COLOR_PURPLE));


        helper.page("matrix_ritual");
        //no text

        helper.page("base_ritual");
        //no text

        helper.page("recipe");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        This is the actual block that works as a storage, make sure to craft it!
                        Placing just the [](item://occultism:storage_controller_base) from the previous step won't work.
                        """.formatted(COLOR_PURPLE));
        //no text


        helper.entry("storage_stabilizer");
        this.lang("ru_ru").add(helper.entryName(), "Extending Storage");

        helper.page("spotlight");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        Storage Stabilizers increase the storage space in the storage dimension of the storage actuator. The higher the tier of the stabilizer, the more additional storage it provides. The following entries will show you how to craft each tier.
                        \\
                        \\
                        """.formatted(COLOR_PURPLE));

        helper.page("upgrade");
        this.lang("ru_ru").add(helper.pageTitle(), "Upgrading");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        It is **safe to destroy a storage stabilizer** to upgrade it. The items in the [Storage Actuator](entry://storage/storage_controller) will not be lost or dropped - you simply cannot add new items until you add enough storage stabilizers to have free slots again.
                         """.formatted(COLOR_PURPLE));

        helper.page("build_instructions");
        this.lang("ru_ru").add(helper.pageTitle(), "Build Instructions");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        Storage controllers need to point at the [Dimensional Matrix](entry://crafting_rituals/craft_dimensional_matrix), that means **one block above the [Storage Actuator](entry://storage/storage_controller)**.
                        \\
                        \\
                        They can be **up to 5 blocks away** from the Dimensional Matrix, and need to be in a straight line of sight. See the next page for a possible very simple setup.
                        """.formatted(COLOR_PURPLE));


        helper.page("demo");
        this.lang("ru_ru").add(helper.pageTitle(), "Storage Stabilizer Setup");
        this.lang("ru_ru").add(helper.pageText(),
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
        this.lang("ru_ru").add("key.occultism.category", "Occultism");
        this.lang("ru_ru").add("key.occultism.backpack", "Open Satchel");
        this.lang("ru_ru").add("key.occultism.storage_remote", "Open Storage Accessor");
        this.lang("ru_ru").add("key.occultism.familiar.otherworld_bird", "Toggle Ring Effect: Drikwing");
        this.lang("ru_ru").add("key.occultism.familiar.greedy_familiar", "Toggle Ring Effect: Greedy");
        this.lang("ru_ru").add("key.occultism.familiar.bat_familiar", "Toggle Ring Effect: Bat");
        this.lang("ru_ru").add("key.occultism.familiar.deer_familiar", "Toggle Ring Effect: Deer");
        this.lang("ru_ru").add("key.occultism.familiar.cthulhu_familiar", "Toggle Ring Effect: Cthulhu");
        this.lang("ru_ru").add("key.occultism.familiar.devil_familiar", "Toggle Ring Effect: Devil");
        this.lang("ru_ru").add("key.occultism.familiar.dragon_familiar", "Toggle Ring Effect: Dragon");
        this.lang("ru_ru").add("key.occultism.familiar.blacksmith_familiar", "Toggle Ring Effect: Blacksmith");
        this.lang("ru_ru").add("key.occultism.familiar.guardian_familiar", "Toggle Ring Effect: Guardian");
        this.lang("ru_ru").add("key.occultism.familiar.headless_familiar", "Toggle Ring Effect: Headless Ratman");
        this.lang("ru_ru").add("key.occultism.familiar.chimera_familiar", "Toggle Ring Effect: Chimera");
        this.lang("ru_ru").add("key.occultism.familiar.goat_familiar", "Toggle Ring Effect: Goat");
        this.lang("ru_ru").add("key.occultism.familiar.shub_niggurath_familiar", "Toggle Ring Effect: Shub Niggurath");
        this.lang("ru_ru").add("key.occultism.familiar.beholder_familiar", "Toggle Ring Effect: Beholder");
        this.lang("ru_ru").add("key.occultism.familiar.fairy_familiar", "Toggle Ring Effect: Fairy");
        this.lang("ru_ru").add("key.occultism.familiar.mummy_familiar", "Toggle Ring Effect: Mummy");
        this.lang("ru_ru").add("key.occultism.familiar.beaver_familiar", "Toggle Ring Effect: Beaver");
    }

    private void addJeiTranslations() {
        this.lang("ru_ru").add("occultism.jei.spirit_fire", "Spiritfire");
        this.lang("ru_ru").add("occultism.jei.crushing", "Crusher Spirit");
        this.lang("ru_ru").add("occultism.jei.miner", "Dimensional Mineshaft");
        this.lang("ru_ru").add("occultism.jei.miner.chance", "Weight: %d");
        this.lang("ru_ru").add("occultism.jei.ritual", "Occult Ritual");
        this.lang("ru_ru").add("occultism.jei.pentacle", "Pentacle");

        this.lang("ru_ru").add(TranslationKeys.JEI_CRUSHING_RECIPE_MIN_TIER, "Min Crusher Tier: %d");
        this.lang("ru_ru").add(TranslationKeys.JEI_CRUSHING_RECIPE_MAX_TIER, "Max Crusher Tier: %d");
        this.lang("ru_ru").add("jei.occultism.ingredient.tallow.description", "Kill animals, such as \u00a72pigs\u00a7r, \u00a72cows\u00a7r, \u00a72sheep\u00a7r, \u00a72horses\u00a7r and \u00a72lamas\u00a7r with the Butcher Knife to obtain tallow.");
        this.lang("ru_ru").add("jei.occultism.ingredient.otherstone.description", "Primarily found in Otherworld Groves. Only visible while the status \u00a76Third Eye\u00a7r is active. See \u00a76Dictionary of Spirits\u00a7r for more information.");
        this.lang("ru_ru").add("jei.occultism.ingredient.otherworld_log.description", "Primarily found in Otherworld Groves. Only visible while the status \u00a76Third Eye\u00a7r is active. See \u00a76Dictionary of Spirits\u00a7r for more information.");
        this.lang("ru_ru").add("jei.occultism.ingredient.otherworld_sapling.description", "Can be obtained from a Otherworld Sapling Trader. Can be seen and harvested without \u00a76Third Eye\u00a7r. See \u00a76Dictionary of Spirits\u00a7r for information on how to summon the trader.");
        this.lang("ru_ru").add("jei.occultism.ingredient.otherworld_sapling_natural.description", "Primarily found in Otherworld Groves. Only visible while the status \u00a76Third Eye\u00a7r is active. See \u00a76Dictionary of Spirits\u00a7r for more information.");
        this.lang("ru_ru").add("jei.occultism.ingredient.otherworld_leaves.description", "Primarily found in Otherworld Groves. Only visible while the status \u00a76Third Eye\u00a7r is active. See \u00a76Dictionary of Spirits\u00a7r for more information.");
        this.lang("ru_ru").add("jei.occultism.ingredient.iesnium_ore.description", "Found in the nether. Only visible while the status \u00a76Third\u00a7r \u00a76Eye\u00a7r is active. See \u00a76Dictionary\u00a7r \u00a76of\u00a7r \u00a76Spirits\u00a7r for more information.");
        this.lang("ru_ru").add("jei.occultism.ingredient.spirit_fire.description", "Throw \u00a76Demon's Dream  Fruit\u00a7r to the ground and light it on fire. See \u00a76Dictionary of Spirits\u00a7r for more information.");
        this.lang("ru_ru").add("jei.occultism.ingredient.datura.description", "Can be used to heal all spirits and familiars summoned by Occultism Rituals. Simply right-click the entity to heal it by one heart");

        this.lang("ru_ru").add("jei.occultism.ingredient.spawn_egg.familiar_goat.description", "The Goat Familiar can be obtained by feeding a Golden Apple to a Chimera Familiar. See \u00a76Dictionary\u00a7r \u00a76of\u00a7r \u00a76Spirits\u00a7r for more information.");
        this.lang("ru_ru").add("jei.occultism.ingredient.spawn_egg.familiar_shub_niggurath.description", "The Shub Niggurath Familiar can be obtained by bringing a Goat Familiar to a Forest Biome and clicking the Goat first with any Black Dye, then Flint and then an Eye of Ender. See \u00a76Dictionary\u00a7r \u00a76of\u00a7r \u00a76Spirits\u00a7r for more information.");

        this.lang("ru_ru").add("jei.occultism.sacrifice", "Sacrifice: %s");
        this.lang("ru_ru").add("jei.occultism.summon", "Summon: %s");
        this.lang("ru_ru").add("jei.occultism.job", "Job: %s");
        this.lang("ru_ru").add("jei.occultism.item_to_use", "Item to use:");
        this.lang("ru_ru").add("jei.occultism.error.missing_id", "Cannot identify recipe.");
        this.lang("ru_ru").add("jei.occultism.error.invalid_type", "Invalid recipe type.");
        this.lang("ru_ru").add("jei.occultism.error.recipe_too_large", "Recipe larger than 3x3.");
        this.lang("ru_ru").add("jei.occultism.error.pentacle_not_loaded", "The pentacle could not be loaded.");
        this.lang("ru_ru").add("item.occultism.jei_dummy.require_sacrifice", "Requires Sacrifice!");
        this.lang("ru_ru").add("item.occultism.jei_dummy.require_sacrifice.tooltip", "This ritual requires a sacrifice to start. Please refer to the Dictionary of Spirits for detailed instructions.");
        this.lang("ru_ru").add("item.occultism.jei_dummy.require_item_use", "Requires Item Use!");
        this.lang("ru_ru").add("item.occultism.jei_dummy.require_item_use.tooltip", "This ritual requires to use a specific item to start. Please refer to the Dictionary of Spirits for detailed instructions.");
        this.lang("ru_ru").add("item.occultism.jei_dummy.none", "Non-Item Ritual Result");
        this.lang("ru_ru").add("item.occultism.jei_dummy.none.tooltip", "This ritual does not create any items.");
    }

    private void addFamiliarSettingsMessages() {
        this.lang("ru_ru").add("message.occultism.familiar.otherworld_bird.enabled", "Ring Effect - Drikwing: Enabled");
        this.lang("ru_ru").add("message.occultism.familiar.otherworld_bird.disabled", "Ring Effect - Drikwing: Disabled");
        this.lang("ru_ru").add("message.occultism.familiar.greedy_familiar.enabled", "Ring Effect - Greedy: Enabled");
        this.lang("ru_ru").add("message.occultism.familiar.greedy_familiar.disabled", "Ring Effect - Greedy: Disabled");
        this.lang("ru_ru").add("message.occultism.familiar.bat_familiar.enabled", "Ring Effect - Bat: Enabled");
        this.lang("ru_ru").add("message.occultism.familiar.bat_familiar.disabled", "Ring Effect - Bat: Disabled");
        this.lang("ru_ru").add("message.occultism.familiar.deer_familiar.enabled", "Ring Effect - Deer: Enabled");
        this.lang("ru_ru").add("message.occultism.familiar.deer_familiar.disabled", "Ring Effect - Deer: Disabled");
        this.lang("ru_ru").add("message.occultism.familiar.cthulhu_familiar.enabled", "Ring Effect - Cthulhu: Enabled");
        this.lang("ru_ru").add("message.occultism.familiar.cthulhu_familiar.disabled", "Ring Effect - Cthulhu: Disabled");
        this.lang("ru_ru").add("message.occultism.familiar.devil_familiar.enabled", "Ring Effect - Devil: Enabled");
        this.lang("ru_ru").add("message.occultism.familiar.devil_familiar.disabled", "Ring Effect - Devil: Disabled");
        this.lang("ru_ru").add("message.occultism.familiar.dragon_familiar.enabled", "Ring Effect - Dragon: Enabled");
        this.lang("ru_ru").add("message.occultism.familiar.dragon_familiar.disabled", "Ring Effect - Dragon: Disabled");
        this.lang("ru_ru").add("message.occultism.familiar.blacksmith_familiar.enabled", "Ring Effect - Blacksmith: Enabled");
        this.lang("ru_ru").add("message.occultism.familiar.blacksmith_familiar.disabled", "Ring Effect - Blacksmith: Disabled");
        this.lang("ru_ru").add("message.occultism.familiar.guardian_familiar.enabled", "Ring Effect - Guardian: Enabled");
        this.lang("ru_ru").add("message.occultism.familiar.guardian_familiar.disabled", "Ring Effect - Guardian: Disabled");
        this.lang("ru_ru").add("message.occultism.familiar.headless_familiar.enabled", "Ring Effect - Headless Ratman: Enabled");
        this.lang("ru_ru").add("message.occultism.familiar.headless_familiar.disabled", "Ring Effect - Headless Ratman: Disabled");
        this.lang("ru_ru").add("message.occultism.familiar.chimera_familiar.enabled", "Ring Effect - Chimera: Enabled");
        this.lang("ru_ru").add("message.occultism.familiar.chimera_familiar.disabled", "Ring Effect - Chimera: Disabled");
        this.lang("ru_ru").add("message.occultism.familiar.shub_niggurath_familiar.enabled", "Ring Effect - Shub Niggurath: Enabled");
        this.lang("ru_ru").add("message.occultism.familiar.shub_niggurath_familiar.disabled", "Ring Effect - Shub Niggurath: Disabled");
        this.lang("ru_ru").add("message.occultism.familiar.beholder_familiar.enabled", "Ring Effect - Beholder: Enabled");
        this.lang("ru_ru").add("message.occultism.familiar.beholder_familiar.disabled", "Ring Effect - Beholder: Disabled");
        this.lang("ru_ru").add("message.occultism.familiar.fairy_familiar.enabled", "Ring Effect - Fairy: Enabled");
        this.lang("ru_ru").add("message.occultism.familiar.fairy_familiar.disabled", "Ring Effect - Fairy: Disabled");
        this.lang("ru_ru").add("message.occultism.familiar.mummy_familiar.enabled", "Ring Effect - Mummy: Enabled");
        this.lang("ru_ru").add("message.occultism.familiar.mummy_familiar.disabled", "Ring Effect - Mummy: Disabled");
        this.lang("ru_ru").add("message.occultism.familiar.beaver_familiar.enabled", "Ring Effect - Beaver: Enabled");
        this.lang("ru_ru").add("message.occultism.familiar.beaver_familiar.disabled", "Ring Effect - Beaver: Disabled");
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
        this.lang("ru_ru").add(Util.makeDescriptionId("multiblock", ResourceLocation.fromNamespaceAndPath(Occultism.MODID, id)), name);
    }

    private void addRitualDummies() {
        this.lang("ru_ru").add("item.occultism.ritual_dummy.custom_ritual", "Custom Ritual Dummy");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.custom_ritual.tooltip", "Used for modpacks as a fallback for custom rituals that do not have their own ritual item.");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.craft_wild_trim", "Ritual: Forge Wild Armor Trim Smithing Template");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.craft_wild_trim.tooltip", "Marid will forge a Wild Armor Trim Smithing Template.");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.craft_dimensional_matrix", "Ritual: Craft Dimensional Matrix");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.craft_dimensional_matrix.tooltip", "The dimensional matrix is the entry point to a small dimension used for storing items.");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.craft_dimensional_mineshaft", "Ritual: Craft Dimensional Mineshaft");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.craft_dimensional_mineshaft.tooltip", "Allows miner spirits to enter the mining dimension and bring back resources.");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.craft_infused_lenses", "Ritual: Craft Infused Lenses");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.craft_infused_lenses.tooltip", "These lenses are used to craft spectacles that give thee ability to see beyond the physical world.");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.craft_infused_pickaxe", "Ritual: Craft Infused Pickaxe");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.craft_infused_pickaxe.tooltip", "Infuse a Pickaxe.");

        this.lang("ru_ru").add("item.occultism.ritual_dummy.craft_miner_djinni_ores", "Ritual: Summon Djinni Ore Miner");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.craft_miner_djinni_ores.tooltip", "Summon Djinni Ore Miner into a magic lamp.");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.craft_miner_foliot_unspecialized", "Ritual: Summon Foliot Miner");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.craft_miner_foliot_unspecialized.tooltip", "Summon Foliot Miner into a magic lamp.");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.craft_miner_afrit_deeps", "Ritual: Summon Afrit Deep Ore Miner");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.craft_miner_afrit_deeps.tooltip", "Summon Afrit Deep Ore Miner into a magic lamp.");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.craft_miner_marid_master", "Ritual: Summon Marid Master Miner");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.craft_miner_marid_master.tooltip", "Summon Marid Master Miner into a magic lamp.");

        this.lang("ru_ru").add("item.occultism.ritual_dummy.craft_satchel", "Ritual: Craft Surprisingly Substantial Satchel");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.craft_satchel.tooltip", "This satchels allows to store more items than it's size would indicate, making it a practical traveller's companion.");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.craft_soul_gem", "Ritual: Craft Soul Gem");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.craft_soul_gem.tooltip", "The soul gem allows the temporary storage of living beings. ");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.craft_familiar_ring", "Ritual: Craft Familiar Ring");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.craft_familiar_ring.tooltip", "The familiar ring allows to store familiars. The ring will apply the familiar effect to the wearer.");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.craft_stabilizer_tier1", "Ritual: Craft Storage Stabilizer Tier 1");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.craft_stabilizer_tier1.tooltip", "The storage stabilizer allows to store more items in the dimensional storage accessor.");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.craft_stabilizer_tier2", "Ritual: Craft Storage Stabilizer Tier 2");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.craft_stabilizer_tier2.tooltip", "The storage stabilizer allows to store more items in the dimensional storage accessor.");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.craft_stabilizer_tier3", "Ritual: Craft Storage Stabilizer Tier 3");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.craft_stabilizer_tier3.tooltip", "The storage stabilizer allows to store more items in the dimensional storage accessor.");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.craft_stabilizer_tier4", "Ritual: Craft Storage Stabilizer Tier 4");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.craft_stabilizer_tier4.tooltip", "The storage stabilizer allows to store more items in the dimensional storage accessor.");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.craft_stable_wormhole", "Ritual: Craft Stable Wormhole");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.craft_stable_wormhole.tooltip", "The stable wormhole allows access to a dimensional matrix from a remote destination.");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.craft_storage_controller_base", "Ritual: Craft Storage Actuator Base");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.craft_storage_controller_base.tooltip", "The storage actuator base imprisons a Foliot responsible for interacting with items in a dimensional storage matrix.");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.craft_storage_remote", "Ritual: Craft Storage Accessor");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.craft_storage_remote.tooltip", "The Storage Accessor can be linked to a Storage Actuator to remotely access items.");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.familiar_otherworld_bird", "Ritual: Summon Drikwing Familiar");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.familiar_otherworld_bird.tooltip", "Drikwings will provide their owner with limited flight abilities when nearby.");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.familiar_parrot", "Ritual: Summon Parrot Familiar");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.familiar_parrot.tooltip", "Parrot familiars behave exactly like tamed parrots.");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.familiar_greedy", "Ritual: Summon Greedy Familiar");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.familiar_greedy.tooltip", "Greedy familiars pick up items for their master. When stored in a familiar ring, they increase the pickup range (like an item magnet).");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.familiar_bat", "Ritual: Summon Bat Familiar");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.familiar_bat.tooltip", "Bat familiars provide night vision to their master.");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.familiar_deer", "Ritual: Summon Deer Familiar");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.familiar_deer.tooltip", "Deer familiars provide jump boost to their master.");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.familiar_cthulhu", "Ritual: Summon Cthulhu Familiar");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.familiar_cthulhu.tooltip", "The cthulhu familiars provide water breathing to their master.");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.familiar_devil", "Ritual: Summon Devil Familiar");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.familiar_devil.tooltip", "The devil familiars provide fire resistance to their master.");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.familiar_dragon", "Ritual: Summon Dragon Familiar");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.familiar_dragon.tooltip", "The dragon familiars provide increased experience gain to their master.");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.familiar_blacksmith", "Ritual: Summon Blacksmith Familiar");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.familiar_blacksmith.tooltip", "The blacksmith familiars take stone their master mines and uses it to repair equipment.");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.familiar_guardian", "Ritual: Summon Guardian Familiar");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.familiar_guardian.tooltip", "The guardian familiars prevent their master's violent demise.");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.familiar_headless", "Ritual: Summon Headless Ratman Familiar");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.familiar_headless.tooltip", "The headless ratman familiars increase their master's attack damage against enemies of the kind it stole the head from.");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.familiar_chimera", "Ritual: Summon Chimera Familiar");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.familiar_chimera.tooltip", "The chimera familiars can be fed to grow in size and gain attack speed and damage. Once big enough, playeres can ride them.");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.familiar_beholder", "Ritual: Summon Beholder Familiar");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.familiar_beholder.tooltip", "The beholder familiars highlight nearby entities with a glow effect and shoot laser rays at enemies.");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.familiar_fairy", "Ritual: Summon Fairy Familiar");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.familiar_fairy.tooltip", "The fairy familiar keeps other familiars from dying, drains enemies of their life force and heals its master and their familiars.");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.familiar_mummy", "Ritual: Summon Mummy Familiar");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.familiar_mummy.tooltip", "The Mummy familiar is a martial arts expert and fights to protect their master.");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.familiar_beaver", "Ritual: Summon Beaver Familiar");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.familiar_beaver.tooltip", "The Beaver familiar provides increased woodcutting speed to their masters and harvests nearby trees when they grow from a sapling.");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.possess_enderman", "Ritual: Summon Possessed Enderman");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.possess_enderman.tooltip", "The possessed Enderman will always drop at least one ender pearl when killed.");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.possess_endermite", "Ritual: Summon Possessed Endermite");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.possess_endermite.tooltip", "The possessed Endermite drops End Stone.");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.possess_skeleton", "Ritual: Summon Possessed Skeleton");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.possess_skeleton.tooltip", " The possessed Skeleton is immune to daylight and always drop at least one Skeleton Skull when killed.");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.possess_ghast", "Ritual: Summon Possessed Ghast");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.possess_ghast.tooltip", "The possessed Ghast will always drop at least one ghast tear when killed.");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.possess_phantom", "Ritual: Summon Possessed Phantom");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.possess_phantom.tooltip", "The possessed Phantom will always drop at least one phantom membrane when killed and is easy to trap.");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.possess_weak_shulker", "Ritual: Summon Possessed Weak Shulker");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.possess_weak_shulker.tooltip", "The possessed Weak Shulker will drop at least one chorus fruit when killed and can drop shulker shell.");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.possess_shulker", "Ritual: Summon Possessed Shulker");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.possess_shulker.tooltip", "The possessed Shulker will always drop at least one shulker shell when killed.");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.possess_elder_guardian", "Ritual: Summon Possessed Elder Guardian");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.possess_elder_guardian.tooltip", "The possessed elder guardian will drop at least one nautilus shell when killed, also can drop heart of the sea and the commom drops.");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.possess_warden", "Ritual: Summon Possessed Warden");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.possess_warden.tooltip", "The possessed Warden will always drop a echo shard and can drop anothers ancient stuff (smithing templates and discs) when killed.");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.possess_hoglin", "Ritual: Summon Possessed Hoglin");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.possess_hoglin.tooltip", "The possessed Hoglin has a chance to drop smithing template of netherite upgrade when killed.");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.summon_afrit_rain_weather", "Ritual: Rainy Weather");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.summon_afrit_rain_weather.tooltip", "Summons an bound Afrit that creates rain.");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.summon_afrit_thunder_weather", "Ritual: Thunderstorm");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.summon_afrit_thunder_weather.tooltip", "Summons an bound Afrit that creates a thunderstorm.");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.summon_djinni_clear_weather", "Ritual: Clear Weather");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.summon_djinni_clear_weather.tooltip", "Summons a Djinni that clears the weather.");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.summon_djinni_day_time", "Ritual: Summoning of Dawn");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.summon_djinni_day_time.tooltip", "Summons a Djinni that sets the time to high noon.");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.summon_djinni_manage_machine", "Ritual: Summon Djinni Machine Operator");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.summon_djinni_manage_machine.tooltip", "The machine operator automatically transfers items between Dimensional Storage Systems and connected Inventories and Machines.");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.summon_djinni_night_time", "Ritual: Summoning of Dusk");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.summon_djinni_night_time.tooltip", "Summons a Djinni that sets the time to midnight.");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.summon_foliot_crusher", "Ritual: Summon Foliot Crusher");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.summon_foliot_crusher.tooltip", "The crusher is a spirit summoned to crush ores into dusts, effectively doubling the metal output.\n" + ChatFormatting.GRAY + ChatFormatting.ITALIC + "Note: Some recipes may require higher or lower tier crushers.");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.summon_djinni_crusher", "Ritual: Summon Djinni Crusher");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.summon_djinni_crusher.tooltip", "The crusher is a spirit summoned to crush ores into dusts, effectively (more than) doubling the metal output. This crusher decays (much) slower than lower tier crushers.\n" + ChatFormatting.GRAY + ChatFormatting.ITALIC + "Note: Some recipes may require higher or lower tier crushers.");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.summon_afrit_crusher", "Ritual: Summon Afrit Crusher");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.summon_afrit_crusher.tooltip", "The crusher is a spirit summoned to crush ores into dusts, effectively (more than) doubling the metal output. This crusher decays (much) slower than lower tier crushers.\n" + ChatFormatting.GRAY + ChatFormatting.ITALIC + "Note: Some recipes may require higher or lower tier crushers.");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.summon_marid_crusher", "Ritual: Summon Marid Crusher");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.summon_marid_crusher.tooltip", "The crusher is a spirit summoned to crush ores into dusts, effectively (more than) doubling the metal output. This crusher decays (much) slower than lower tier crushers.\n" + ChatFormatting.GRAY + ChatFormatting.ITALIC + "Note: Some recipes may require higher or lower tier crushers.");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.summon_foliot_lumberjack", "Ritual: Summon Foliot Lumberjack");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.summon_foliot_lumberjack.tooltip", "The lumberjack will harvest trees in it's working area and deposit the dropped items into the specified chest.");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.summon_foliot_otherstone_trader", "Ritual: Summon Otherstone Trader");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.summon_foliot_otherstone_trader.tooltip", "The otherstone trader trades normal stone for otherstone.");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.summon_foliot_sapling_trader", "Ritual: Summon Otherworld Sapling Trader");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.summon_foliot_sapling_trader.tooltip", "he otherworld sapling trader trades natural otherworld saplings for stable ones, that can be harvested without the third eye.");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.summon_foliot_transport_items", "Ritual: Summon Foliot Transporter");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.summon_foliot_transport_items.tooltip", "The transporter will move all items it can access from one inventory to another, including machines.");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.summon_foliot_cleaner", "Ritual: Summon Foliot Janitor");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.summon_foliot_cleaner.tooltip", "The janitor will pick up dropped items and deposit them into a target inventory.");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.summon_wild_afrit", "Ritual: Summon Unbound Afrit");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.summon_wild_afrit.tooltip", "Summons an unbound Afrit that can be killed to obtain Afrit Essence");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.summon_wild_hunt", "Ritual: Summon The Wild Hunt");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.summon_wild_hunt.tooltip", "The Wild Hunt consists of Wither Skeletons that as a big chance to drop Wither Skeleton Skulls, and their minions.");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.summon_wild_otherworld_bird", "Ritual: Summon Wild Drikwing");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.summon_wild_otherworld_bird.tooltip", "Summons a Drikwing Familiar that can be tamed by anyone, not just the summoner.");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.summon_wild_parrot", "Ritual: Summon Wild Parrot");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.summon_wild_parrot.tooltip", "Summons a Parrot that can be tamed by anyone, not just the summoner.");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.summon_allay", "Ritual: Purify Vex to Allay");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.summon_allay.tooltip", "Purify a Vex to a Allay on a resurrection process.");

        this.lang("ru_ru").add("item.occultism.ritual_dummy.summon_wild_husk", "Ritual: Summon The Wild Horde Husk");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.summon_wild_husk.tooltip", "The Wild Horde Husk consists of a few Husks that drop items related to desert trails.");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.summon_wild_drowned", "Ritual: Summon The Wild Horde Drowned");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.summon_wild_drowned.tooltip", "The Wild Horde Drowned consists of a few Drowneds that drop items related to ocean trails.");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.summon_wild_creeper", "Ritual: Summon The Wild Horde Creeper");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.summon_wild_creeper.tooltip", "The Wild Horde Creeper consists of a few charged Creepers that drop many disks.");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.summon_wild_silverfish", "Ritual: Summon The Wild Horde Silverfish");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.summon_wild_silverfish.tooltip", "The Wild Horde Silverfish consists of a few Silverfishs that drop items related to ruins trails.");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.possess_weak_breeze", "Ritual: Summon Possessed Weak Breeze");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.possess_weak_breeze.tooltip", "The possessed Weak Breeze will drop a Trial Key and trial chamber related items.");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.possess_breeze", "Ritual: Summon Possessed Breeze");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.possess_breeze.tooltip", "The possessed Breeze will drop a Ominous Trial Key and trial chamber related items.");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.possess_strong_breeze", "Ritual: Summon Possessed Strong Breeze");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.possess_strong_breeze.tooltip", "The possessed Strong Breeze will drop a Heavy Core and trial chamber related items.");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.summon_horde_illager", "Ritual: Summon Possessed Evoker");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.summon_horde_illager.tooltip", "Summon a possessed Evoker and his henchmen.");

        this.lang("ru_ru").add(OccultismItems.RITUAL_DUMMY_SUMMON_DEMONIC_WIFE.get(), "Ritual: Summon Demonic Wife");
        this.addTooltip(OccultismItems.RITUAL_DUMMY_SUMMON_DEMONIC_WIFE.get(), "Summons a Demonic Wife to support you: She will fight for you, help with cooking, and extend potion durations.");

        this.lang("ru_ru").add(OccultismItems.RITUAL_DUMMY_SUMMON_DEMONIC_HUSBAND.get(), "Ritual: Summon Demonic Husband");
        this.addTooltip(OccultismItems.RITUAL_DUMMY_SUMMON_DEMONIC_HUSBAND.get(), "Summons a Demonic Husband to support you: He will fight for you, help with cooking, and extend potion durations.");


        this.lang("ru_ru").add(OccultismItems.RITUAL_RESURRECT_FAMILIAR.get(), "Ritual: Resurrect Familiar");
        this.addTooltip(OccultismItems.RITUAL_RESURRECT_FAMILIAR.get(), "Resurrects a Familiar from a Soul Shard.");
    }

    public void addTooltip(ItemLike key, String value) {
        this.lang("ru_ru").add(key.asItem().getDescriptionId() + ".tooltip", value);
    }

    public void addAutoTooltip(ItemLike key, String value) {
        this.lang("ru_ru").add(key.asItem().getDescriptionId() + ".auto_tooltip", value);
    }

    private void addDialogs() {
        this.lang("ru_ru").add("dialog.occultism.dragon.pet", "purrr");
        this.lang("ru_ru").add("dialog.occultism.mummy.kapow", "KAPOW!");
    }

    private void addModonomiconIntegration() {
        this.lang("ru_ru").add(I18n.RITUAL_RECIPE_ITEM_USE, "Item to use:");
        this.lang("ru_ru").add(I18n.RITUAL_RECIPE_SUMMON, "Summon: %s");
        this.lang("ru_ru").add(I18n.RITUAL_RECIPE_JOB, "Job: %s");
        this.lang("ru_ru").add(I18n.RITUAL_RECIPE_SACRIFICE, "Sacrifice: %s");
        this.lang("ru_ru").add(I18n.RITUAL_RECIPE_GO_TO_PENTACLE, "Open Pentacle Page: %s");
    }

    private void advancementTitle(String name, String s) {
        this.lang("ru_ru").add(((TranslatableContents) OccultismAdvancementSubProvider.title(name).getContents()).getKey(), s);
    }

    private void advancementDescr(String name, String s) {
        this.lang("ru_ru").add(((TranslatableContents) OccultismAdvancementSubProvider.descr(name).getContents()).getKey(), s);
    }

    private void addTags() {
        // Block tags
        this.addBlockTag(OccultismTags.Blocks.OTHERWORLD_SAPLINGS,"Otherworld Saplings");
        this.addBlockTag(OccultismTags.Blocks.CANDLES,"Candles");
        this.addBlockTag(OccultismTags.Blocks.CAVE_WALL_BLOCKS,"Cave Wall Blocks");
        this.addBlockTag(OccultismTags.Blocks.NETHERRACK,"Netherrack");
        this.addBlockTag(OccultismTags.Blocks.STORAGE_STABILIZER,"Storage Stabilizer Blocks");
        this.addBlockTag(OccultismTags.Blocks.TREE_SOIL,"Tree Soil Blocks");
        this.addBlockTag(OccultismTags.Blocks.WORLDGEN_BLACKLIST,"Worldgen Blacklisted Blocks");
        this.addBlockTag(OccultismTags.Blocks.IESNIUM_ORE,"Iesnium Ore");
        this.addBlockTag(OccultismTags.Blocks.SILVER_ORE,"Silver Ore");
        this.addBlockTag(OccultismTags.Blocks.STORAGE_BLOCKS_IESNIUM,"Iesnium Storage Blocks");
        this.addBlockTag(OccultismTags.Blocks.STORAGE_BLOCKS_SILVER,"Silver Storage Blocks");
        this.addBlockTag(OccultismTags.Blocks.STORAGE_BLOCKS_RAW_IESNIUM,"Raw Iesnium Storage Blocks");
        this.addBlockTag(OccultismTags.Blocks.STORAGE_BLOCKS_RAW_SILVER,"Raw Silver Storage Blocks");


        // Item tags
        this.addItemTag(OccultismTags.Items.OTHERWORLD_SAPLINGS,"Otherworld Saplings");
        this.addItemTag(OccultismTags.Items.BOOK_OF_CALLING_DJINNI,"Book of Calling Djinni");
        this.addItemTag(OccultismTags.Items.BOOK_OF_CALLING_FOLIOT,"Book of Calling Foliot");
        this.addItemTag(OccultismTags.Items.Miners.BASIC_RESOURCES,"Basic Resource Miners");
        this.addItemTag(OccultismTags.Items.Miners.DEEPS,"Deepslate Miners");
        this.addItemTag(OccultismTags.Items.Miners.MASTER,"Rare Resource Miners");
        this.addItemTag(OccultismTags.Items.Miners.ORES,"General Miners");
        this.addItemTag(OccultismTags.Items.TOOL_KNIVES,"Knives");
        this.addItemTag(OccultismTags.Items.ELYTRA,"Elytras");
        this.addItemTag(OccultismTags.Items.OTHERWORLD_GOGGLES,"Otherworld Goggles");
        this.addItemTag(OccultismTags.Items.DATURA_SEEDS,"Demon's Dream Seeds");
        this.addItemTag(OccultismTags.Items.DATURA_CROP,"Demon's Dream");
        this.addItemTag(OccultismTags.Items.COPPER_DUST,"Copper Dust");
        this.addItemTag(OccultismTags.Items.GOLD_DUST,"Gold Dust");
        this.addItemTag(OccultismTags.Items.IESNIUM_DUST,"Iesnium Dust");
        this.addItemTag(OccultismTags.Items.IRON_DUST,"Iron Dust");
        this.addItemTag(OccultismTags.Items.SILVER_DUST,"Silver Dust");
        this.addItemTag(OccultismTags.Items.END_STONE_DUST,"Crushed End Stone");
        this.addItemTag(OccultismTags.Items.OBSIDIAN_DUST,"Crushed Obsidian");
        this.addItemTag(OccultismTags.Items.IESNIUM_INGOT,"Iesnium Ingot");
        this.addItemTag(OccultismTags.Items.SILVER_INGOT,"Silver Ingot");
        this.addItemTag(OccultismTags.Items.IESNIUM_NUGGET,"Iesnium Nugget");
        this.addItemTag(OccultismTags.Items.SILVER_NUGGET,"Silver Nugget");
        this.addItemTag(OccultismTags.Items.IESNIUM_ORE,"Iesnium Ore");
        this.addItemTag(OccultismTags.Items.SILVER_ORE,"Silver Ore");
        this.addItemTag(OccultismTags.Items.RAW_IESNIUM,"Raw Iesnium");
        this.addItemTag(OccultismTags.Items.RAW_SILVER,"Raw Silver");
        this.addItemTag(OccultismTags.Items.STORAGE_BLOCK_IESNIUM,"Iesnium Storage Blocks");
        this.addItemTag(OccultismTags.Items.STORAGE_BLOCK_SILVER,"Silver Storage Blocks");
        this.addItemTag(OccultismTags.Items.STORAGE_BLOCK_RAW_IESNIUM,"Raw Iesnium Storage Blocks");
        this.addItemTag(OccultismTags.Items.STORAGE_BLOCK_RAW_SILVER,"Raw Silver Storage Blocks");
        this.addItemTag(OccultismTags.Items.TALLOW,"Tallow");
        this.addItemTag(OccultismTags.Items.METAL_AXES,"Metal Axes");
        this.addItemTag(OccultismTags.Items.MAGMA,"Magma");
        this.addItemTag(OccultismTags.Items.BOOKS,"Books");
        this.addItemTag(OccultismTags.Items.FRUITS,"Fruits");
    }

    private void addItemTag(ResourceLocation resourceLocation, String string) {
        this.lang("ru_ru").add("tag.item."+resourceLocation.getNamespace()+"."+resourceLocation.getPath().replace("/","."), string);
    }
    private void addBlockTag(TagKey<Block> block, String string) {
        this.addBlockTag(block.location(),string);
    }
    private void addItemTag(TagKey<Item> item, String string) {
        this.addItemTag(item.location(),string);
    }
    private void addBlockTag(ResourceLocation resourceLocation, String string) {
        this.lang("ru_ru").add("tag.block."+resourceLocation.getNamespace()+"."+resourceLocation.getPath().replace("/","."), string);
    }

    private void addEmiTranslations() {
        this.lang("ru_ru").add("emi.category.occultism.spirit_fire","Spirit Fire");
        this.lang("ru_ru").add("emi.category.occultism.crushing","Crushing");
        this.lang("ru_ru").add("emi.category.occultism.miner","Dimensional Mineshaft");
        this.lang("ru_ru").add("emi.category.occultism.ritual","Rituals");
        this.lang("ru_ru").add("emi.occultism.item_to_use", "Item to use: %s");
    }

    private void addConfigurationTranslations() {

        this.addConfig("visual", "Visual Settings");
        this.addConfig("showItemTagsInTooltip", "Show Item Tags in Tooltips");
        this.addConfig("disableDemonsDreamShaders", "Disable Demon's Dream Shaders");
        this.addConfig("disableHolidayTheming", "Disable Otherworld Goggles Shaders");
        this.addConfig("useAlternativeDivinationRodRenderer", "Use Alternative Divination Rod Renderer");
        this.addConfig("whiteChalkGlyphColor", "White Chalk Glyph Color");
        this.addConfig("goldenChalkGlyphColor", "Golden Chalk Glyph Color");
        this.addConfig("purpleChalkGlyphColor", "Purple Chalk Glyph Color");
        this.addConfig("redChalkGlyphColor", "Red Chalk Glyph Color");

        this.addConfig("misc", "Misc Settings");
        this.addConfig("syncJeiSearch", "Sync JEI Search");
        this.addConfig("divinationRodHighlightAllResults", "Divination Rod Highlight All Results");
        this.addConfig("divinationRodScanRange", "Divination Rod Scan Range");
        this.addConfig("disableSpiritFireSuccessSound", "Disable Spirit Fire Success Sound");

        this.addConfig("storage", "Storage Settings");
        this.addConfig("stabilizerTier1AdditionalMaxItemTypes", "Stabilizer Tier 1 Additional Max Item Types");
        this.addConfig("stabilizerTier1AdditionalMaxTotalItemCount", "Stabilizer Tier 1 Additional Max Total Item Count");
        this.addConfig("stabilizerTier2AdditionalMaxItemTypes", "Stabilizer Tier 2 Additional Max Item Types");
        this.addConfig("stabilizerTier2AdditionalMaxTotalItemCount", "Stabilizer Tier 2 Additional Max Total Item Count");
        this.addConfig("stabilizerTier3AdditionalMaxItemTypes", "Stabilizer Tier 3 Additional Max Item Types");
        this.addConfig("stabilizerTier3AdditionalMaxTotalItemCount", "Stabilizer Tier 3 Additional Max Total Item Count");
        this.addConfig("stabilizerTier4AdditionalMaxItemTypes", "Stabilizer Tier 4 Additional Max Item Types");
        this.addConfig("stabilizerTier4AdditionalMaxTotalItemCount", "Stabilizer Tier 4 Additional Max Total Item Count");
        this.addConfig("controllerMaxItemTypes", "Controller Max Item Types");
        this.addConfig("controllerMaxTotalItemCount", "Controller Max Total Item Count");
        this.addConfig("unlinkWormholeOnBreak", "Unlink Wormhole on Break");

        this.addConfig("spirit_job", "Spirit Job Settings");
        this.addConfig("drikwingFamiliarSlowFallingSeconds", "Duration of slow falling effect given by Drikwing Familiar in seconds.");
        this.addConfig("tier1CrusherTimeMultiplier", "Time multiplier for Tier 1 Crusher operations.");
        this.addConfig("tier2CrusherTimeMultiplier", "Time multiplier for Tier 2 Crusher operations.");
        this.addConfig("tier3CrusherTimeMultiplier", "Time multiplier for Tier 3 Crusher operations.");
        this.addConfig("tier4CrusherTimeMultiplier", "Time multiplier for Tier 4 Crusher operations.");
        this.addConfig("tier1CrusherOutputMultiplier", "Output multiplier for Tier 1 Crusher operations.");
        this.addConfig("tier2CrusherOutputMultiplier", "Output multiplier for Tier 2 Crusher operations.");
        this.addConfig("tier3CrusherOutputMultiplier", "Output multiplier for Tier 3 Crusher operations.");
        this.addConfig("tier4CrusherOutputMultiplier", "Output multiplier for Tier 4 Crusher operations.");
        this.addConfig("crusherResultPickupDelay", "Delay before items from crusher operations can be picked up.");
        this.addConfig("blacksmithFamiliarRepairChance", "Chance for Blacksmith Familiar to repair an item each tick.");
        this.addConfig("blacksmithFamiliarUpgradeCost", "Cost in experience levels for upgrading items with Blacksmith Familiar.");
        this.addConfig("blacksmithFamiliarUpgradeCooldown", "Cooldown in ticks before Blacksmith Familiar can upgrade items again.");

        this.addConfig("rituals", "Rituals Settings");
        this.addConfig("enableClearWeatherRitual", "Enable the ritual to clear weather conditions.");
        this.addConfig("enableRainWeatherRitual", "Enable the ritual to cause rain weather conditions.");
        this.addConfig("enableThunderWeatherRitual", "Enable the ritual to cause thunderstorm weather conditions.");
        this.addConfig("enableDayTimeRitual", "Enable the ritual to change the time to day.");
        this.addConfig("enableNightTimeRitual", "Enable the ritual to change the time to night.");
        this.addConfig("enableRemainingIngredientCountMatching", "Enable matching of remaining ingredients in ritual recipes.");
        this.addConfig("ritualDurationMultiplier", "Multiplier to adjust the duration of all rituals.");
        this.addConfig("possibleSpiritNames", "Possible Spirit Names");

        this.addConfig("dimensional_mineshaft", "Dimensional Mineshaft Settings");
        this.addConfig("miner_foliot_unspecialized", "Foliot Miner Unspectialized");
        this.addConfig("miner_djinni_ores", "Djinni Ore Miner");
        this.addConfig("miner_afrit_deeps", "Afrit Deep Ore Miner");
        this.addConfig("miner_marid_master", "Marid Master Miner");

        this.addConfig("maxMiningTime", "Max Mining Time");
        this.addConfig("rollsPerOperation", "Rolls Per Operation");
        this.addConfig("durability", "Durability");
    }

    private void addConfig(String key, String name){
        this.lang("ru_ru").add(Occultism.MODID + ".configuration." + key, name);
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
        this.addEmiTranslations();
        this.addConfigurationTranslations();
        this.addTags();
    }
}
