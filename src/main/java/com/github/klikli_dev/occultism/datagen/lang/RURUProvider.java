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
        this.lang("ru_ru").add("item.occultism.book_of_calling" + ".message_target_uuid_no_match", "На данный момент дух не связан с этой книгой. Нажмите Shift + ПКМ по духу, чтобы его связать.");
        this.lang("ru_ru").add("item.occultism.book_of_calling" + ".message_target_linked", "Теперь дух связан с этой книгой..");
        this.lang("ru_ru").add("item.occultism.book_of_calling" + ".message_target_cannot_link", "Этот дух не может быть связан с этой книгой. Книга вызова должна соответствовать задаче духа!");
        this.lang("ru_ru").add("item.occultism.book_of_calling" + ".message_target_entity_no_inventory", "У этой сущности нет инвентаря, она не может быть установлена в качестве местоположения для вклада.");
        this.lang("ru_ru").add("item.occultism.book_of_calling" + ".message_spirit_not_found", "Дух, связанный с этой книгой не живёт в этой плоскости существования.");
        this.lang("ru_ru").add("item.occultism.book_of_calling" + ".message_set_deposit", "%s будет класть в %s со стороны: %s");
        this.lang("ru_ru").add("item.occultism.book_of_calling" + ".message_set_deposit_entity", "%s будет передавать предметы в: %s");
        this.lang("ru_ru").add("item.occultism.book_of_calling" + ".message_set_extract", "%s будет извлекать из %s со стороны: %s");
        this.lang("ru_ru").add("item.occultism.book_of_calling" + ".message_set_base", "База для %s установлена на %s");
        this.lang("ru_ru").add("item.occultism.book_of_calling" + ".message_set_storage_controller", "%s будет принимать рабочие задания из %s");
        this.lang("ru_ru").add("item.occultism.book_of_calling" + ".message_set_work_area_size", "%s будет отслеживать рабочее место %s");
        this.lang("ru_ru").add("item.occultism.book_of_calling" + ".message_set_managed_machine", "Настройки для устройства %s обновлены.");
        this.lang("ru_ru").add("item.occultism.book_of_calling" + ".message_set_managed_machine_extract_location", "%s будет извлекать из %s со стороны: %s");
        this.lang("ru_ru").add("item.occultism.book_of_calling" + ".message_no_managed_machine", "Установите управляемое устройство, прежде чем установить местоположение для извлечения %s");

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
        this.lang("ru_ru").add(OccultismItems.BOOK_OF_BINDING_BOUND_FOLIOT.get().getDescriptionId() + ".tooltip", "Может использоваться для вызова Фолиота %s");
        this.lang("ru_ru").add(OccultismItems.BOOK_OF_BINDING_DJINNI.get().getDescriptionId() + ".tooltip", "Эта книга пока не связана с Джинном.");
        this.lang("ru_ru").add(OccultismItems.BOOK_OF_BINDING_BOUND_DJINNI.get().getDescriptionId() + ".tooltip", "Может использоваться для вызова Джинна %s");
        this.lang("ru_ru").add(OccultismItems.BOOK_OF_BINDING_AFRIT.get().getDescriptionId() + ".tooltip", "Эта книга пока не связана с Афритом.");
        this.lang("ru_ru").add(OccultismItems.BOOK_OF_BINDING_BOUND_AFRIT.get().getDescriptionId() + ".tooltip", "Может использоваться для вызова Африта %s");
        this.lang("ru_ru").add(OccultismItems.BOOK_OF_BINDING_MARID.get().getDescriptionId() + ".tooltip", "Эта книга пока не связана с Маридом.");
        this.lang("ru_ru").add(OccultismItems.BOOK_OF_BINDING_BOUND_MARID.get().getDescriptionId() + ".tooltip", "Может использоваться для вызова Марида %s");

        this.lang("ru_ru").add("item.occultism.book_of_calling_foliot" + ".tooltip", "Фолиот %s");
        this.lang("ru_ru").add("item.occultism.book_of_calling_foliot" + ".tooltip_dead", "%s покинул эту плоскость существования.");
        this.lang("ru_ru").add("item.occultism.book_of_calling_foliot" + ".tooltip.extract", "Извлекает из: %s.");
        this.lang("ru_ru").add("item.occultism.book_of_calling_foliot" + ".tooltip.deposit", "Кладёт в: %s.");
        this.lang("ru_ru").add("item.occultism.book_of_calling_foliot" + ".tooltip.deposit_entity", "Передаёт предметы в: %s.");
        this.lang("ru_ru").add("item.occultism.book_of_calling_djinni" + ".tooltip", "Джинн %s");
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
        //Магия Notepad++:
        //"item\.occultism\.(.*)": "(.*)"
        //this.addItem\(OccultismItems.\U\1\E, "\2"\);

        this.lang("ru_ru").add("itemGroup.occultism", "Occultism");

        this.addItem(OccultismItems.PENTACLE, "Пентакля");
        this.addItem(OccultismItems.DEBUG_WAND, "Жезл отладки");
        this.addItem(OccultismItems.DEBUG_FOLIOT_LUMBERJACK, "Вызов отладочного Дровосека-Фолиота");
        this.addItem(OccultismItems.DEBUG_FOLIOT_TRANSPORT_ITEMS, "Вызов отладочного Транспортировщика-Фолиота");
        this.addItem(OccultismItems.DEBUG_FOLIOT_CLEANER, "Вызов отладочного Дворника-Фолиота");
        this.addItem(OccultismItems.DEBUG_FOLIOT_TRADER_ITEM, "Вызов отладочного Фолиота-Торговца");
        this.addItem(OccultismItems.DEBUG_DJINNI_MANAGE_MACHINE, "Вызов отладочного Станочника-Джинна");
        this.addItem(OccultismItems.DEBUG_DJINNI_TEST, "Вызов отладочного тестового Джинна");

        this.addItem(OccultismItems.CHALK_GOLD, "Золотой мел");
        this.addItem(OccultismItems.CHALK_PURPLE, "Пурпурный мел");
        this.addItem(OccultismItems.CHALK_RED, "Красный мел");
        this.addItem(OccultismItems.CHALK_WHITE, "Мел");
        this.addItem(OccultismItems.CHALK_GOLD_IMPURE, "Осквернённый золотой мел");
        this.addItem(OccultismItems.CHALK_PURPLE_IMPURE, "Осквернённый пурпурный мел");
        this.addItem(OccultismItems.CHALK_RED_IMPURE, "Осквернённый красный мел");
        this.addItem(OccultismItems.CHALK_WHITE_IMPURE, "Осквернённый мел");
        this.addItem(OccultismItems.BRUSH, "Щётка для мела");
        this.addItem(OccultismItems.AFRIT_ESSENCE, "Сущность Африта");
        this.addItem(OccultismItems.PURIFIED_INK, "Очищенные чернила");
        this.addItem(OccultismItems.AWAKENED_FEATHER, "Пробуждённое перо");
        this.addItem(OccultismItems.TABOO_BOOK, "Книга табу");
        this.addItem(OccultismItems.BOOK_OF_BINDING_EMPTY, "Пустая книга привязки");
        this.addItem(OccultismItems.BOOK_OF_BINDING_FOLIOT, "Книга привязки: Фолиот");
        this.addItem(OccultismItems.BOOK_OF_BINDING_BOUND_FOLIOT, "Книга привязки: Фолиот (Связанная)");
        this.addItem(OccultismItems.BOOK_OF_BINDING_DJINNI, "Книга привязки: Джинна");
        this.addItem(OccultismItems.BOOK_OF_BINDING_BOUND_DJINNI, "Книга привязки: Джинна (Связанная)");
        this.addItem(OccultismItems.BOOK_OF_BINDING_AFRIT, "Книга привязки: Африт");
        this.addItem(OccultismItems.BOOK_OF_BINDING_BOUND_AFRIT, "Книга привязки: Африт (Связанная)");
        this.addItem(OccultismItems.BOOK_OF_BINDING_MARID, "Книга привязки: Марид");
        this.addItem(OccultismItems.BOOK_OF_BINDING_BOUND_MARID, "Книга привязки: Марид (Связанная)");
        this.addItem(OccultismItems.BOOK_OF_CALLING_FOLIOT_LUMBERJACK, "Книга вызова: Дровосек-Фолиот");
        this.addItem(OccultismItems.BOOK_OF_CALLING_FOLIOT_TRANSPORT_ITEMS, "Книга вызова: Транспортировщик-Фолиот");
        this.addItem(OccultismItems.BOOK_OF_CALLING_FOLIOT_CLEANER, "Книга вызова: Дворник-Фолиот");
        this.addItem(OccultismItems.BOOK_OF_CALLING_DJINNI_MANAGE_MACHINE, "Книга вызова: Станочник-Джинна");
        this.addItem(OccultismItems.STORAGE_REMOTE, "Средство доступа хранилища");
        this.addItem(OccultismItems.STORAGE_REMOTE_INERT, "Инертное средство доступа хранилища");
        this.addItem(OccultismItems.DIMENSIONAL_MATRIX, "Кристалл пространственной матрицы");
        this.addItem(OccultismItems.DIVINATION_ROD, "Жезл прорицания");
        this.addItem(OccultismItems.DATURA_SEEDS, "Семена «Блаженство демона");
        this.addAutoTooltip(OccultismItems.DATURA_SEEDS.get(), "Посадите, чтобы вырастить Плод «Блаженство демона».\nУпотребление может позволить видеть за гробовой чертой... Возможно, вызовет плохое самочувствие.");
        this.addItem(OccultismItems.DATURA, "Плод «Блаженство демона»");
        this.addAutoTooltip(OccultismItems.DATURA.get(), "Употребление может позволить видеть за гробовой чертой... Возможно, вызовет плохое самочувствие.");
        this.addItem(OccultismItems.DEMONS_DREAM_ESSENCE, "Эссенция «Блаженство демона»");
        this.addAutoTooltip(OccultismItems.DEMONS_DREAM_ESSENCE.get(), "Употребление позволяет видеть за гробовой чертой ... И полный набор других эффектов.");
        this.addItem(OccultismItems.OTHERWORLD_ESSENCE, "Потусторонняя эссенция");
        this.addAutoTooltip(OccultismItems.OTHERWORLD_ESSENCE.get(), "Очищенная эссенция «Блаженство демона». Больше не оказывает пагубных эффектов.");
		this.addItem(OccultismItems.BEAVER_NUGGET, "Мех бобра");
        this.addItem(OccultismItems.SPIRIT_ATTUNED_GEM, "Самоцвет, настроенный на духа");
        this.lang("ru_ru").add("item.occultism.otherworld_sapling", "Потусторонний саженец");
        this.lang("ru_ru").add("item.occultism.otherworld_sapling_natural", "Нестабильный потусторонний саженец");
        this.addItem(OccultismItems.OTHERWORLD_ASHES, "Потусторонняя зола");
        this.addItem(OccultismItems.BURNT_OTHERSTONE, "Гарь из Потустороннего камня");
        this.addItem(OccultismItems.BUTCHER_KNIFE, "Нож мясника");
        this.addItem(OccultismItems.TALLOW, "Жир");
        this.addItem(OccultismItems.OTHERSTONE_FRAME, "Рама из Потустороннего камня");
        this.addItem(OccultismItems.OTHERSTONE_TABLET, "Дощечка из Потустороннего камня");
        this.addItem(OccultismItems.WORMHOLE_FRAME, "Рама червоточины");
        this.addItem(OccultismItems.IRON_DUST, "Железная пыль");
        this.addItem(OccultismItems.OBSIDIAN_DUST, "Обсидиановая пыль");
        this.addItem(OccultismItems.CRUSHED_END_STONE, "Измельчённый эндерняк");
        this.addItem(OccultismItems.GOLD_DUST, "Золотая пыль");
        this.addItem(OccultismItems.COPPER_DUST, "Медная пыль");
        this.addItem(OccultismItems.SILVER_DUST, "Серебряная пыль");
        this.addItem(OccultismItems.IESNIUM_DUST, "Пыль айзния");
        this.addItem(OccultismItems.RAW_SILVER, "Рудное серебро");
        this.addItem(OccultismItems.RAW_IESNIUM, "Рудный айзний");
        this.addItem(OccultismItems.SILVER_INGOT, "Серебряный слиток");
        this.addItem(OccultismItems.IESNIUM_INGOT, "Слиток айзния");
        this.addItem(OccultismItems.SILVER_NUGGET, "Кусочек серебра");
        this.addItem(OccultismItems.IESNIUM_NUGGET, "Кусочек айзния");
        this.addItem(OccultismItems.LENSES, "Стеклянные линзы");
        this.addItem(OccultismItems.INFUSED_LENSES, "Наполненные линзы");
        this.addItem(OccultismItems.LENS_FRAME, "Оправа для очков");
        this.addItem(OccultismItems.OTHERWORLD_GOGGLES, "Потусторонние очки");
        this.addItem(OccultismItems.INFUSED_PICKAXE, "Наполненная кирка");
        this.addItem(OccultismItems.SPIRIT_ATTUNED_PICKAXE_HEAD, "Головка кирки из самоцвета, настроенного на духа");
        this.addItem(OccultismItems.IESNIUM_PICKAXE, "Кирка из айзния");
        this.addItem(OccultismItems.MAGIC_LAMP_EMPTY, "Пустая магическая лампа");
        this.addItem(OccultismItems.MINER_FOLIOT_UNSPECIALIZED, "Горняк-Фолиот");
        this.addItem(OccultismItems.MINER_DJINNI_ORES, "Рудный Горняк-Джинн");
        this.addItem(OccultismItems.MINER_DEBUG_UNSPECIALIZED, "Отладочный горняк");
        this.addItem(OccultismItems.MINER_AFRIT_DEEPS, "Горняк-Африт для глубинносланцевой руды");
        this.addItem(OccultismItems.MINER_MARID_MASTER, "Мастер Горняк-Марид");
        this.addItem(OccultismItems.SOUL_GEM_ITEM, "Самоцвет душ");
        this.lang("ru_ru").add(OccultismItems.SOUL_GEM_ITEM.get().getDescriptionId() + "_empty", "Пустой самоцвет душ");
        this.addItem(OccultismItems.SOUL_SHARD_ITEM, "Осколок душ");
        this.addItem(OccultismItems.SATCHEL, "Изумительно большая сумка");
        this.addItem(OccultismItems.FAMILIAR_RING, "Кольцо фамильяра");
        this.addItem(OccultismItems.SPAWN_EGG_FOLIOT, "Яйцо призыва Фолиота");
        this.addItem(OccultismItems.SPAWN_EGG_DJINNI, "Яйцо призыва Джинна");
        this.addItem(OccultismItems.SPAWN_EGG_AFRIT, "Яйцо призыва Африта");
        this.addItem(OccultismItems.SPAWN_EGG_AFRIT_WILD, "Яйцо призыва несвязанного Африта");
        this.addItem(OccultismItems.SPAWN_EGG_MARID, "Яйцо призыва Марида");
        this.addItem(OccultismItems.SPAWN_EGG_POSSESSED_ENDERMITE, "Яйцо призыва одержимого эндермита");
        this.addItem(OccultismItems.SPAWN_EGG_POSSESSED_SKELETON, "Яйцо призыва одержимого скелета");
        this.addItem(OccultismItems.SPAWN_EGG_POSSESSED_ENDERMAN, "Яйцо призыва одержимого эндермена");
        this.addItem(OccultismItems.SPAWN_EGG_POSSESSED_GHAST, "Яйцо призыва одержимого гаста");
        this.addItem(OccultismItems.SPAWN_EGG_POSSESSED_PHANTOM, "Яйцо призыва одержимого фантома");
        this.addItem(OccultismItems.SPAWN_EGG_POSSESSED_WEAK_SHULKER, "Яйцо призыва одержимого слабого шалкера");
        this.addItem(OccultismItems.SPAWN_EGG_POSSESSED_SHULKER, "Яйцо призыва одержимого шалкера");
        this.addItem(OccultismItems.SPAWN_EGG_POSSESSED_ELDER_GUARDIAN, "Яйцо призыва одержимого древнего стража");
        this.addItem(OccultismItems.SPAWN_EGG_POSSESSED_WARDEN, "Яйцо призыва одержимого хранителя");
        this.addItem(OccultismItems.SPAWN_EGG_POSSESSED_HOGLIN, "Яйцо призыва одержимого хоглина");
        this.addItem(OccultismItems.SPAWN_EGG_WILD_HUNT_SKELETON, "Яйцо призыва скелета Дикой Охоты");
        this.addItem(OccultismItems.SPAWN_EGG_WILD_HUNT_WITHER_SKELETON, "Яйцо призыва визер скелета Дикой Охоты");
        this.addItem(OccultismItems.SPAWN_EGG_OTHERWORLD_BIRD, "Яйцо призыва Дрикрыла");
        this.addItem(OccultismItems.SPAWN_EGG_GREEDY_FAMILIAR, "Яйцо ризыва алчного фамильяра");
        this.addItem(OccultismItems.SPAWN_EGG_BAT_FAMILIAR, "Яйцо призыва Летучей мыши-фамильяра");
        this.addItem(OccultismItems.SPAWN_EGG_DEER_FAMILIAR, "Яйцо призыва Оленя-фамильяра");
        this.addItem(OccultismItems.SPAWN_EGG_CTHULHU_FAMILIAR, "Яйцо призыва Ктулху-фамильяра");
        this.addItem(OccultismItems.SPAWN_EGG_DEVIL_FAMILIAR, "Яйцо призыва Дьявола-фамильяра");
        this.addItem(OccultismItems.SPAWN_EGG_DRAGON_FAMILIAR, "Яйцо призыва Дракона-фамильяра");
        this.addItem(OccultismItems.SPAWN_EGG_BLACKSMITH_FAMILIAR, "Яйцо призыва Кузнеца-фамильяра");
        this.addItem(OccultismItems.SPAWN_EGG_GUARDIAN_FAMILIAR, "Яйцо призыва Стража-фамильяра");
        this.addItem(OccultismItems.SPAWN_EGG_HEADLESS_FAMILIAR, "Яйцо призыва Безголового человека-крысы-фамильяра");
        this.addItem(OccultismItems.SPAWN_EGG_CHIMERA_FAMILIAR, "Яйцо призыва Химеры-фамильяра");
        this.addItem(OccultismItems.SPAWN_EGG_GOAT_FAMILIAR, "Яйцо призыва Козы-фамильяра");
        this.addItem(OccultismItems.SPAWN_EGG_SHUB_NIGGURATH_FAMILIAR, "Яйцо призыва Шуб-Ниггурата-фамильяра");
        this.addItem(OccultismItems.SPAWN_EGG_BEHOLDER_FAMILIAR, "Яйцо призыва Созерцателя--фамильяра");
        this.addItem(OccultismItems.SPAWN_EGG_FAIRY_FAMILIAR, "Яйцо призыва Фем-фамильяра");
        this.addItem(OccultismItems.SPAWN_EGG_MUMMY_FAMILIAR, "Яйцо призыва Мумии-фамильяра");
        this.addItem(OccultismItems.SPAWN_EGG_BEAVER_FAMILIAR, "Яйцо призыва Бобра-фамильяра");
        this.addItem(OccultismItems.SPAWN_EGG_PARROT_FAMILIAR, "Яйцо призыва Попугая-фамильяра");
        this.addItem(OccultismItems.SPAWN_EGG_DEMONIC_WIFE, "Яйцо призыва Демонической жены");
        this.addItem(OccultismItems.SPAWN_EGG_DEMONIC_HUSBAND, "Яйцо призыва Демонического мужа");
        this.addItem(OccultismItems.SPAWN_EGG_WILD_HORDE_HUSK, "Яйцо призыва орды диких кадавров");
        this.addItem(OccultismItems.SPAWN_EGG_WILD_HORDE_DROWNED, "Яйцо призыва орды диких утопленников");
        this.addItem(OccultismItems.SPAWN_EGG_WILD_HORDE_CREEPER, "Яйцо призыва орды диких криперов");
        this.addItem(OccultismItems.SPAWN_EGG_WILD_HORDE_SILVERFISH, "Яйцо призыва орды диких чешуйниц");
        this.addItem(OccultismItems.SPAWN_EGG_POSSESSED_WEAK_BREEZE, "Яйцо призыва одержимого слабого вихря");
        this.addItem(OccultismItems.SPAWN_EGG_POSSESSED_BREEZE, "Яйцо призыва одержимого вихря");
        this.addItem(OccultismItems.SPAWN_EGG_POSSESSED_STRONG_BREEZE, "Яйцо призыва одержимого сильного вихря");
        this.addItem(OccultismItems.SPAWN_EGG_POSSESSED_EVOKER, "Яйцо призыва одержимого заклинателя");
    }

    private void addBlocks() {
        //"block\.occultism\.(.*?)": "(.*)",
        //this.addBlock\(OccultismItems.\U\1\E, "\2"\);

        this.addBlock(OccultismBlocks.OTHERSTONE, "Потусторонний камень");
        this.addBlock(OccultismBlocks.OTHERSTONE_SLAB, "Потусторонняя плита");
        this.addBlock(OccultismBlocks.OTHERSTONE_PEDESTAL, "Потусторонний пьедестал");
        this.addBlock(OccultismBlocks.SACRIFICIAL_BOWL, "Миска для жертвоприношений");
        this.addBlock(OccultismBlocks.GOLDEN_SACRIFICIAL_BOWL, "Золотая миска для жертвоприношений");
        this.addBlock(OccultismBlocks.CHALK_GLYPH_WHITE, "Меловой глиф");
        this.addBlock(OccultismBlocks.CHALK_GLYPH_GOLD, "Золотой меловой глиф");
        this.addBlock(OccultismBlocks.CHALK_GLYPH_PURPLE, "Пурпурный меловой глиф");
        this.addBlock(OccultismBlocks.CHALK_GLYPH_RED, "Красный меловой глиф");
        this.addBlock(OccultismBlocks.STORAGE_CONTROLLER, "Актуатор пространственного хранилища");
        this.addBlock(OccultismBlocks.STORAGE_CONTROLLER_BASE, "Основа актуатора хранилища");
        this.addBlock(OccultismBlocks.STORAGE_STABILIZER_TIER1, "Стабилизатор пространственного хранилища 1-го уровня");
        this.addBlock(OccultismBlocks.STORAGE_STABILIZER_TIER2, "Стабилизатор пространственного хранилища 2-го уровня");
        this.addBlock(OccultismBlocks.STORAGE_STABILIZER_TIER3, "Стабилизатор пространственного хранилища 3-го уровня");
        this.addBlock(OccultismBlocks.STORAGE_STABILIZER_TIER4, "Стабилизатор пространственного хранилища 4-го уровня");
        this.addBlock(OccultismBlocks.STABLE_WORMHOLE, "Стабильная червоточина");
        this.addBlock(OccultismBlocks.DATURA, "Блаженство демона");

        this.lang("ru_ru").add("block.occultism.otherworld_log", "Потусторонняя древесина");
        this.lang("ru_ru").add("block.occultism.otherworld_sapling", "Потусторонний саженец");
        this.lang("ru_ru").add("block.occultism.otherworld_leaves", "Потусторонняя листья");

        this.addBlock(OccultismBlocks.SPIRIT_FIRE, "Духовный огонь");
        this.addBlock(OccultismBlocks.SPIRIT_ATTUNED_CRYSTAL, "Кристалл, настроенный на духа");
        this.addBlock(OccultismBlocks.CANDLE_WHITE, "Свеча");
        this.addBlock(OccultismBlocks.SILVER_ORE, "Серебряная руда");
        this.addBlock(OccultismBlocks.SILVER_ORE_DEEPSLATE, "Серебреносная глубиносланцевая руда");
        this.addBlock(OccultismBlocks.IESNIUM_ORE, "Руда айзния");
        this.addBlock(OccultismBlocks.SILVER_BLOCK, "Серебряный блок");
        this.addBlock(OccultismBlocks.IESNIUM_BLOCK, "Блок айзния");
        this.addBlock(OccultismBlocks.RAW_SILVER_BLOCK, "Блок рудного серебра");
        this.addBlock(OccultismBlocks.RAW_IESNIUM_BLOCK, "Блок рудного айзния");
        this.addBlock(OccultismBlocks.DIMENSIONAL_MINESHAFT, "Пространственная шахта");
        this.addBlock(OccultismBlocks.SKELETON_SKULL_DUMMY, "Череп скелета");
        this.addBlock(OccultismBlocks.WITHER_SKELETON_SKULL_DUMMY, "Череп визер-скелета");
        this.addBlock(OccultismBlocks.LIGHTED_AIR, "Подсвеченный воздух");
        this.addBlock(OccultismBlocks.SPIRIT_LANTERN, "Духовный фонарь");
        this.addBlock(OccultismBlocks.SPIRIT_CAMPFIRE, "Духовный костёр");
        this.addBlock(OccultismBlocks.SPIRIT_TORCH, "Духовный факел"); //Настенный духовный факел автоматически использует аналогичный перевод.
    }

    private void addEntities() {
        //"entity\.occultism\.(.*?)": "(.*)",
        //this.addEntityType\(OccultismEntities.\U\1\E, "\2"\);

        this.addEntityType(OccultismEntities.FOLIOT, "Фолиот");
        this.addEntityType(OccultismEntities.DJINNI, "Джинн");
        this.addEntityType(OccultismEntities.AFRIT, "Африт");
        this.addEntityType(OccultismEntities.AFRIT_WILD, "Несвязанный Африт");
        this.addEntityType(OccultismEntities.MARID, "Марид");
        this.addEntityType(OccultismEntities.POSSESSED_ENDERMITE, "Одержимый эндермит");
        this.addEntityType(OccultismEntities.POSSESSED_SKELETON, "Одержимый скелет");
        this.addEntityType(OccultismEntities.POSSESSED_ENDERMAN, "Одержимый эндермен");
        this.addEntityType(OccultismEntities.POSSESSED_GHAST, "Одержимый гаст");
        this.addEntityType(OccultismEntities.POSSESSED_PHANTOM, "Одержимый фантом");
        this.addEntityType(OccultismEntities.POSSESSED_WEAK_SHULKER, "Одержимый слабый шалкер");
        this.addEntityType(OccultismEntities.POSSESSED_SHULKER, "Одержимый шалкер");
        this.addEntityType(OccultismEntities.POSSESSED_ELDER_GUARDIAN, "Одержимый древний страж");
        this.addEntityType(OccultismEntities.POSSESSED_WARDEN, "Одержимый хранитель");
        this.addEntityType(OccultismEntities.POSSESSED_HOGLIN, "Одержимый хоглин");
        this.addEntityType(OccultismEntities.WILD_HUNT_SKELETON, "Скелет Дикой Охоты");
        this.addEntityType(OccultismEntities.WILD_HUNT_WITHER_SKELETON, "Визер-скелет Дикой Охоты");
        this.addEntityType(OccultismEntities.OTHERWORLD_BIRD, "Дрикрыл");
        this.addEntityType(OccultismEntities.GREEDY_FAMILIAR, "Алчный фамильяр");
        this.addEntityType(OccultismEntities.BAT_FAMILIAR, "Летучая мышь-фамильяр");
        this.addEntityType(OccultismEntities.DEER_FAMILIAR, "Олень-фамильяр");
        this.addEntityType(OccultismEntities.CTHULHU_FAMILIAR, "Ктулху-фамильяр");
        this.addEntityType(OccultismEntities.DEVIL_FAMILIAR, "Дьявол-фамильяр");
        this.addEntityType(OccultismEntities.DRAGON_FAMILIAR, "Дракон-фамильяр");
        this.addEntityType(OccultismEntities.BLACKSMITH_FAMILIAR, "Кузнец-фамильяр");
        this.addEntityType(OccultismEntities.GUARDIAN_FAMILIAR, "Страж-фамильяр");
        this.addEntityType(OccultismEntities.HEADLESS_FAMILIAR, "Безголовый-фамильяр");
        this.addEntityType(OccultismEntities.CHIMERA_FAMILIAR, "Химера-фамильяр");
        this.addEntityType(OccultismEntities.GOAT_FAMILIAR, "Козёл-фамильяр");
        this.addEntityType(OccultismEntities.SHUB_NIGGURATH_FAMILIAR, "Шуб-Ниггурат-фамильяр");
        this.addEntityType(OccultismEntities.BEHOLDER_FAMILIAR, "Созерцатель-фамильяр");
        this.addEntityType(OccultismEntities.FAIRY_FAMILIAR, "Фея-фамильяр");
        this.addEntityType(OccultismEntities.MUMMY_FAMILIAR, "Мумия-фамильяр");
        this.addEntityType(OccultismEntities.BEAVER_FAMILIAR, "Бобёр-фамильяр");
        this.addEntityType(OccultismEntities.SHUB_NIGGURATH_SPAWN, "Потомок Шуб-Ниггурата");
        this.addEntityType(OccultismEntities.THROWN_SWORD, "Брошенный меч");
        this.addEntityType(OccultismEntities.DEMONIC_WIFE, "Демоническая жена");
        this.addEntityType(OccultismEntities.DEMONIC_HUSBAND, "Демонический муж");
        this.addEntityType(OccultismEntities.WILD_HORDE_HUSK, "Орда диких Husk");
        this.addEntityType(OccultismEntities.WILD_HORDE_DROWNED, "Орда диких утопленников");
        this.addEntityType(OccultismEntities.WILD_HORDE_CREEPER, "Орда диких криперов");
        this.addEntityType(OccultismEntities.WILD_HORDE_SILVERFISH, "Орда диких чешуйниц");
        this.addEntityType(OccultismEntities.POSSESSED_WEAK_BREEZE, "Одержимый слабого вихря");
        this.addEntityType(OccultismEntities.POSSESSED_BREEZE, "Одержимый вихря");
        this.addEntityType(OccultismEntities.POSSESSED_STRONG_BREEZE, "Одержимый сильного вихря");
        this.addEntityType(OccultismEntities.WILD_ZOMBIE, "Дикий зомби");
        this.addEntityType(OccultismEntities.WILD_SKELETON, "Дикий скелет");
        this.addEntityType(OccultismEntities.WILD_SILVERFISH, "Дикий чешуйница");
        this.addEntityType(OccultismEntities.WILD_SPIDER, "Дикий паук");
        this.addEntityType(OccultismEntities.WILD_BOGGED, "Дикий болотник");
        this.addEntityType(OccultismEntities.WILD_SLIME, "Дикий слизень");
        this.addEntityType(OccultismEntities.WILD_HUSK, "Дикий кадавр");
        this.addEntityType(OccultismEntities.WILD_STRAY, "Дикий зимогор");
        this.addEntityType(OccultismEntities.WILD_CAVE_SPIDER, "Дикий мещерный паук");
        this.addEntityType(OccultismEntities.POSSESSED_EVOKER, "Одержимый заклинатель");
    }

    private void addMiscTranslations() {

        //"(.*?)": "(.*)",
        //this.lang("ru_ru").add\("\1", "\2"\);

        this.lang("ru_ru").add(TranslationKeys.HUD_NO_PENTACLE_FOUND, "Не найдено допустимой пентакли.");
        this.lang("ru_ru").add(TranslationKeys.HUD_PENTACLE_FOUND, "Текущий пентакль: %s");

        this.lang("ru_ru").add(TranslationKeys.MESSAGE_CONTAINER_ALREADY_OPEN, "Этот контейнер уже открыт другим игроком, ждите пока он его не закроет.");

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
        this.lang("ru_ru").add("gui.occultism.spirit.age", "Распад сущности: %d%%");
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
        this.lang("ru_ru").add("ritual.occultism.book_not_bound", "\u00a7lКнига вызова (несвязанная)\u00a7r. Перед началом ритуала Вы должны создать эту книгу, используя «Справочник душ», чтобы связать её с духом.");

        this.lang("ru_ru").add("ritual.occultism.debug.conditions", "Не все требования удовлетворены для этого ритуала.");
        this.lang("ru_ru").add("ritual.occultism.debug.started", "Ритуал запущен.");
        this.lang("ru_ru").add("ritual.occultism.debug.finished", "Ритуал полностью завершился.");
        this.lang("ru_ru").add("ritual.occultism.debug.interrupted", "Ритуал прерван.");

        this.lang("ru_ru").add("ritual.occultism.summon_foliot_lumberjack.conditions", "Не все требования удовлетворены для этого ритуала.");
        this.lang("ru_ru").add("ritual.occultism.summon_foliot_lumberjack.started", "Начался вызов Дровосека-фолиота.");
        this.lang("ru_ru").add("ritual.occultism.summon_foliot_lumberjack.finished", "Дровосек-Фолиот успешно вызван.");
        this.lang("ru_ru").add("ritual.occultism.summon_foliot_lumberjack.interrupted", "Вызов Дровосека-Фолиота прерван.");
        this.lang("ru_ru").add("ritual.occultism.summon_foliot_transport_items.conditions", "Не все требования удовлетворены для этого ритуала.");
        this.lang("ru_ru").add("ritual.occultism.summon_foliot_transport_items.started", "Начался вызов Транспортировщика-Фолиота.");
        this.lang("ru_ru").add("ritual.occultism.summon_foliot_transport_items.finished", "Транспортировщик-Фолиот успешно вызван.");
        this.lang("ru_ru").add("ritual.occultism.summon_foliot_transport_items.interrupted", "Вызов Транспортировщика-Фолиота прерван.");
        this.lang("ru_ru").add("ritual.occultism.summon_foliot_cleaner.conditions", "Не все требования удовлетворены для этого ритуала.");
        this.lang("ru_ru").add("ritual.occultism.summon_foliot_cleaner.started", "Начался вызов Дворника-Фолиота.");
        this.lang("ru_ru").add("ritual.occultism.summon_foliot_cleaner.finished", "Дворник-Фолиот успешно вызван.");
        this.lang("ru_ru").add("ritual.occultism.summon_foliot_cleaner.interrupted", "Вызов Дворника-Фолиота прерван.");
        this.lang("ru_ru").add("ritual.occultism.summon_foliot_crusher.conditions", "Не все требования удовлетворены для этого ритуала.");
        this.lang("ru_ru").add("ritual.occultism.summon_foliot_crusher.started", "Начался вызов Дробильщика руды Фолиота.");
        this.lang("ru_ru").add("ritual.occultism.summon_foliot_crusher.finished", "Дробильщик руды Фолиот успешно вызван.");
        this.lang("ru_ru").add("ritual.occultism.summon_foliot_crusher.interrupted", "Вызов Дробильщика руды Фолиота прерван.");
        this.lang("ru_ru").add("ritual.occultism.summon_djinni_crusher.conditions", "Не все требования удовлетворены для этого ритуала.");
        this.lang("ru_ru").add("ritual.occultism.summon_djinni_crusher.started", "Начался вызов Дробильщика руды Джинна.");
        this.lang("ru_ru").add("ritual.occultism.summon_djinni_crusher.finished", "Дробильщик руды Джинна успешно вызван.");
        this.lang("ru_ru").add("ritual.occultism.summon_djinni_crusher.interrupted", "Вызов Дробильщика руды Джинна прерван.");
        this.lang("ru_ru").add("ritual.occultism.summon_afrit_crusher.conditions", "Не все требования удовлетворены для этого ритуала.");
        this.lang("ru_ru").add("ritual.occultism.summon_afrit_crusher.started", "Начался вызов Дробильщика руды Африта.");
        this.lang("ru_ru").add("ritual.occultism.summon_afrit_crusher.finished", "Дробильщик руды Африт успешно вызван.");
        this.lang("ru_ru").add("ritual.occultism.summon_afrit_crusher.interrupted", "Вызов Дробильщика руды Африта прерван.");
        this.lang("ru_ru").add("ritual.occultism.summon_marid_crusher.conditions", "Не все требования удовлетворены для этого ритуала.");
        this.lang("ru_ru").add("ritual.occultism.summon_marid_crusher.started", "Начался вызов Дробильщика руды Марида.");
        this.lang("ru_ru").add("ritual.occultism.summon_marid_crusher.finished", "Дробильщик руды Марид успешно вызван.");
        this.lang("ru_ru").add("ritual.occultism.summon_marid_crusher.interrupted", "Вызов Дробильщика руды Марида прерван.");
        this.lang("ru_ru").add("ritual.occultism.summon_foliot_sapling_trader.conditions", "Не все требования удовлетворены для этого ритуала.");
        this.lang("ru_ru").add("ritual.occultism.summon_foliot_sapling_trader.started", "Начался вызов Торговца-Фолиота потусторонними саженцами.");
        this.lang("ru_ru").add("ritual.occultism.summon_foliot_sapling_trader.finished", "Торговец-Фолиот потусторонними саженцами успешно вызван.");
        this.lang("ru_ru").add("ritual.occultism.summon_foliot_sapling_trader.interrupted", "Вызов Торговца-Фолиота потусторонними саженцами прерван.");
        this.lang("ru_ru").add("ritual.occultism.summon_foliot_otherstone_trader.conditions", "Не все требования удовлетворены для этого ритуала.");
        this.lang("ru_ru").add("ritual.occultism.summon_foliot_otherstone_trader.started", "Начался вызов Торговца-Фолиота потустороннем камнем.");
        this.lang("ru_ru").add("ritual.occultism.summon_foliot_otherstone_trader.finished", "Торговец-Фолиот потустороннем камнем успешно вызван.");
        this.lang("ru_ru").add("ritual.occultism.summon_foliot_otherstone_trader.interrupted", "Вызов Торговца-Фолиота потустороннем камнем прерван.");
        this.lang("ru_ru").add("ritual.occultism.summon_djinni_manage_machine.conditions", "Не все требования удовлетворены для этого ритуала.");
        this.lang("ru_ru").add("ritual.occultism.summon_djinni_manage_machine.started", "Начался вызов Станочника-Джинна.");
        this.lang("ru_ru").add("ritual.occultism.summon_djinni_manage_machine.finished", "Станочник-Джинн успешно вызван.");
        this.lang("ru_ru").add("ritual.occultism.summon_djinni_manage_machine.interrupted", "Вызов Станочника-Джинна прерван.");
        this.lang("ru_ru").add("ritual.occultism.summon_djinni_clear_weather.conditions", "Не все требования удовлетворены для этого ритуала.");
        this.lang("ru_ru").add("ritual.occultism.summon_djinni_clear_weather.started", "Начался вызов Джинна для ясной погоды.");
        this.lang("ru_ru").add("ritual.occultism.summon_djinni_clear_weather.finished", "Джинн успешно вызван.");
        this.lang("ru_ru").add("ritual.occultism.summon_djinni_clear_weather.interrupted", "Вызов Джинна прерван.");
        this.lang("ru_ru").add("ritual.occultism.summon_djinni_day_time.conditions", "Не все требования удовлетворены для этого ритуала.");
        this.lang("ru_ru").add("ritual.occultism.summon_djinni_day_time.started", "Начался вызов Джинна с целью установки времени на день.");
        this.lang("ru_ru").add("ritual.occultism.summon_djinni_day_time.finished", "Джинн успешно вызван.");
        this.lang("ru_ru").add("ritual.occultism.summon_djinni_day_time.interrupted", "Вызов джинна прерван.");
        this.lang("ru_ru").add("ritual.occultism.summon_djinni_night_time.conditions", "Не все требования удовлетворены для этого ритуала.");
        this.lang("ru_ru").add("ritual.occultism.summon_djinni_night_time.started", "Начался вызов Джинна с целью установки времени на ночь.");
        this.lang("ru_ru").add("ritual.occultism.summon_djinni_night_time.finished", "Джинн успешно вызван.");
        this.lang("ru_ru").add("ritual.occultism.summon_djinni_night_time.interrupted", "Вызов джинна прерван.");
        this.lang("ru_ru").add("ritual.occultism.summon_afrit_rain_weather.conditions", "Не все требования удовлетворены для этого ритуала.");
        this.lang("ru_ru").add("ritual.occultism.summon_afrit_rain_weather.started", "Начался вызов Африта для дождливой погоды.");
        this.lang("ru_ru").add("ritual.occultism.summon_afrit_rain_weather.finished", "Джинн успешно вызван.");
        this.lang("ru_ru").add("ritual.occultism.summon_afrit_rain_weather.interrupted", "Вызов джинна прерван.");
        this.lang("ru_ru").add("ritual.occultism.summon_afrit_thunder_weather.conditions", "Не все требования удовлетворены для этого ритуала.");
        this.lang("ru_ru").add("ritual.occultism.summon_afrit_thunder_weather.started", "Начался вызов Африта для грозы.");
        this.lang("ru_ru").add("ritual.occultism.summon_afrit_thunder_weather.finished", "Африт успешно вызван.");
        this.lang("ru_ru").add("ritual.occultism.summon_afrit_thunder_weather.interrupted", "Вызов Африта прерван.");
        this.lang("ru_ru").add("ritual.occultism.summon_wild_afrit.conditions", "Не все требования удовлетворены для этого ритуала.");
        this.lang("ru_ru").add("ritual.occultism.summon_wild_afrit.started", "Начался вызов несвязанного африта.");
        this.lang("ru_ru").add("ritual.occultism.summon_wild_afrit.finished", "Несвязанный Африт успешно вызван.");
        this.lang("ru_ru").add("ritual.occultism.summon_wild_afrit.interrupted", "Вызов несвязанного Африта прерван.");
        this.lang("ru_ru").add("ritual.occultism.summon_wild_hunt.conditions", "Не все требования удовлетворены для этого ритуала.");
        this.lang("ru_ru").add("ritual.occultism.summon_wild_hunt.started", "Начался вызов Дикой Охоты.");
        this.lang("ru_ru").add("ritual.occultism.summon_wild_hunt.finished", "Дикая Охота успешно вызвана.");
        this.lang("ru_ru").add("ritual.occultism.summon_wild_hunt.interrupted", "Вызов Дикой Охоты прерван.");
        this.lang("ru_ru").add("ritual.occultism.craft_dimensional_matrix.conditions", "Не все требования удовлетворены для этого ритуала.");
        this.lang("ru_ru").add("ritual.occultism.craft_dimensional_matrix.started", "Началось заточение джинна в пространственную матрицу.");
        this.lang("ru_ru").add("ritual.occultism.craft_dimensional_matrix.finished", "Успешно заточил джинна в пространственную матрицу.");
        this.lang("ru_ru").add("ritual.occultism.craft_dimensional_matrix.interrupted", "Заточение джинна прервано.");
        this.lang("ru_ru").add("ritual.occultism.craft_dimensional_mineshaft.conditions", "Не все требования удовлетворены для этого ритуала.");
        this.lang("ru_ru").add("ritual.occultism.craft_dimensional_mineshaft.started", "Началось заточение Джинна в пространственную шахту.");
        this.lang("ru_ru").add("ritual.occultism.craft_dimensional_mineshaft.finished", "Успешно заточил Джинна в пространственную шахту.");
        this.lang("ru_ru").add("ritual.occultism.craft_dimensional_mineshaft.interrupted", "Заточение джинна прервано.");
        this.lang("ru_ru").add("ritual.occultism.craft_storage_controller_base.conditions", "Не все требования удовлетворены для этого ритуала.");
        this.lang("ru_ru").add("ritual.occultism.craft_storage_controller_base.started", "Началось заточение Фолиота в основу актуатора хранилища.");
        this.lang("ru_ru").add("ritual.occultism.craft_storage_controller_base.finished", "Успешно заточил Фолиота в основу актуатора хранилища.");
        this.lang("ru_ru").add("ritual.occultism.craft_storage_controller_base.interrupted", "Заточение Фолиота прервано.");
        this.lang("ru_ru").add("ritual.occultism.craft_stabilizer_tier1.conditions", "Не все требования удовлетворены для этого ритуала.");
        this.lang("ru_ru").add("ritual.occultism.craft_stabilizer_tier1.started", "Началось заточение Фолиота в стабилизатор хранилища.");
        this.lang("ru_ru").add("ritual.occultism.craft_stabilizer_tier1.finished", "Успешно заточил Фолиота в стабилизатор хранилища.);
        this.lang("ru_ru").add("ritual.occultism.craft_stabilizer_tier1.interrupted", "Заточение Фолиота прервано.");
        this.lang("ru_ru").add("ritual.occultism.craft_stabilizer_tier2.conditions", "Не все требования удовлетворены для этого ритуала.");
        this.lang("ru_ru").add("ritual.occultism.craft_stabilizer_tier2.started", "Началось заточение Джинна в стабилизатор хранилища.");
        this.lang("ru_ru").add("ritual.occultism.craft_stabilizer_tier2.finished", "Успешно заточил Джинна в стабилизатор хранилища.");
        this.lang("ru_ru").add("ritual.occultism.craft_stabilizer_tier2.interrupted", "Заточение Джинна прервано.");
        this.lang("ru_ru").add("ritual.occultism.craft_stabilizer_tier3.conditions", "Не все требования удовлетворены для этого ритуала.");
        this.lang("ru_ru").add("ritual.occultism.craft_stabilizer_tier3.started", "Началось заточение Африта в стабилизатор хранилища.");
        this.lang("ru_ru").add("ritual.occultism.craft_stabilizer_tier3.finished", "Успешно заточил Африта в стабилизатор хранилища.");
        this.lang("ru_ru").add("ritual.occultism.craft_stabilizer_tier3.interrupted", "Заточение Африта прервано.");
        this.lang("ru_ru").add("ritual.occultism.craft_stabilizer_tier4.conditions", "Не все требования удовлетворены для этого ритуала.");
        this.lang("ru_ru").add("ritual.occultism.craft_stabilizer_tier4.started", "Началось заточение Марида в стабилизатор хранилища.");
        this.lang("ru_ru").add("ritual.occultism.craft_stabilizer_tier4.finished", "Успешно заточил Марида в стабилизатор хранилища.");
        this.lang("ru_ru").add("ritual.occultism.craft_stabilizer_tier4.interrupted", "Заточение Марида прервано.");
        this.lang("ru_ru").add("ritual.occultism.craft_stable_wormhole.conditions", "Не все требования удовлетворены для этого ритуала.");
        this.lang("ru_ru").add("ritual.occultism.craft_stable_wormhole.started", "Началось заточение Фолиота в каркас червоточины.");
        this.lang("ru_ru").add("ritual.occultism.craft_stable_wormhole.finished", "Успешно заточил Фолиота в каркас червоточины.");
        this.lang("ru_ru").add("ritual.occultism.craft_stable_wormhole.interrupted", "Заточение Фолиота прервано.");
        this.lang("ru_ru").add("ritual.occultism.craft_storage_remote.conditions", "Не все требования удовлетворены для этого ритуала.");
        this.lang("ru_ru").add("ritual.occultism.craft_storage_remote.started", "Началось заточение Джинна в удалённое хранилище.");
        this.lang("ru_ru").add("ritual.occultism.craft_storage_remote.finished", "Успешно заточил Джинна в удалённое хранилище.");
        this.lang("ru_ru").add("ritual.occultism.craft_storage_remote.interrupted", "Заточение Джинна прервано.");
        this.lang("ru_ru").add("ritual.occultism.craft_infused_lenses.conditions", "Не все требования удовлетворены для этого ритуала.");
        this.lang("ru_ru").add("ritual.occultism.craft_infused_lenses.started", "Началось заточение Фолиота в линзы.");
        this.lang("ru_ru").add("ritual.occultism.craft_infused_lenses.finished", "Успешно заточил Фолиота в линзы.");
        this.lang("ru_ru").add("ritual.occultism.craft_infused_lenses.interrupted", "Заточение Фолиота прервано.");
        this.lang("ru_ru").add("ritual.occultism.craft_infused_pickaxe.conditions", "Не все требования удовлетворены для этого ритуала.");
        this.lang("ru_ru").add("ritual.occultism.craft_infused_pickaxe.started", "Началось заточение Джинна в кирку.");
        this.lang("ru_ru").add("ritual.occultism.craft_infused_pickaxe.finished", "Успешно заточил Джинна в кирку.");
        this.lang("ru_ru").add("ritual.occultism.craft_infused_pickaxe.interrupted", "Заточение Джинна прервано.");

        this.lang("ru_ru").add("ritual.occultism.craft_miner_foliot_unspecialized.conditions", "Не все требования удовлетворены для этого ритуала.");
        this.lang("ru_ru").add("ritual.occultism.craft_miner_foliot_unspecialized.started", "Начался вызов Фолиота в магическую лампу");
        this.lang("ru_ru").add("ritual.occultism.craft_miner_foliot_unspecialized.finished", "Фолиот успешно вызван в магическую лампу.");
        this.lang("ru_ru").add("ritual.occultism.craft_miner_foliot_unspecialized.interrupted", "Вызов Фолиота прерван.");

        this.lang("ru_ru").add("ritual.occultism.craft_miner_djinni_ores.conditions", "Не все требования удовлетворены для этого ритуала.");
        this.lang("ru_ru").add("ritual.occultism.craft_miner_djinni_ores.started", "Начался вызов Джинна в магическую лампу");
        this.lang("ru_ru").add("ritual.occultism.craft_miner_djinni_ores.finished", "Джинн успешно вызван в магическую лампу.");
        this.lang("ru_ru").add("ritual.occultism.craft_miner_djinni_ores.interrupted", "Вызов Джинна прерван.");

        this.lang("ru_ru").add("ritual.occultism.craft_miner_afrit_deeps.conditions", "Не все требования удовлетворены для этого ритуала.");
        this.lang("ru_ru").add("ritual.occultism.craft_miner_afrit_deeps.started", "Начался вызов Африта в магическую лампу");
        this.lang("ru_ru").add("ritual.occultism.craft_miner_afrit_deeps.finished", "Африт успешно вызван в магическую лампу");
        this.lang("ru_ru").add("ritual.occultism.craft_miner_afrit_deeps.interrupted", "Вызов Африта прерван.");

        this.lang("ru_ru").add("ritual.occultism.craft_miner_marid_master.conditions", "Не все требования удовлетворены для этого ритуала.");
        this.lang("ru_ru").add("ritual.occultism.craft_miner_marid_master.started", "Начался вызов Марида в магическую лампу");
        this.lang("ru_ru").add("ritual.occultism.craft_miner_marid_master.finished", "Марид успешно вызван в магическую лампу.");
        this.lang("ru_ru").add("ritual.occultism.craft_miner_marid_master.interrupted", "Вызов Марида прерван.");

        this.lang("ru_ru").add("ritual.occultism.craft_satchel.conditions", "Не все требования удовлетворены для этого ритуала.");
        this.lang("ru_ru").add("ritual.occultism.craft_satchel.started", "Началось заточение Фолиота в сумку.");
        this.lang("ru_ru").add("ritual.occultism.craft_satchel.finished", "Успешно заточил Фолиота в сумку.");
        this.lang("ru_ru").add("ritual.occultism.craft_satchel.interrupted", "Заточение Фолиота прервано.");
        this.lang("ru_ru").add("ritual.occultism.craft_soul_gem.conditions", "Не все требования удовлетворены для этого ритуала.");
        this.lang("ru_ru").add("ritual.occultism.craft_soul_gem.started", "Началось заточение Джинна в камень душ.");
        this.lang("ru_ru").add("ritual.occultism.craft_soul_gem.finished", "Успешно заточил Джинна в камень душ.");
        this.lang("ru_ru").add("ritual.occultism.craft_soul_gem.interrupted", "Заточение Джинна прервано.");
        this.lang("ru_ru").add("ritual.occultism.craft_familiar_ring.conditions", "Не все требования удовлетворены для этого ритуала.");
        this.lang("ru_ru").add("ritual.occultism.craft_familiar_ring.started", "Началось заточение Джинна в Кольцо фамильяра.");
        this.lang("ru_ru").add("ritual.occultism.craft_familiar_ring.finished", "Успешно заточил Джинна в кольцо фамильяра.");
        this.lang("ru_ru").add("ritual.occultism.craft_familiar_ring.interrupted", "Заключение Джинна прервано.");
        this.lang("ru_ru").add("ritual.occultism.craft_wild_trim.conditions", "Не все требования удовлетворены для этого ритуала.");
        this.lang("ru_ru").add("ritual.occultism.craft_wild_trim.started", "Марид начал ковать Кузнечный шаблон.");
        this.lang("ru_ru").add("ritual.occultism.craft_wild_trim.finished", "Успешно выковал Кузнечный шаблон.");
        this.lang("ru_ru").add("ritual.occultism.craft_wild_trim.interrupted", "Заточение Джинна прервано.");
        this.lang("ru_ru").add("ritual.occultism.possess_endermite.conditions", "Не все требования удовлетворены для этого ритуала.");
        this.lang("ru_ru").add("ritual.occultism.possess_endermite.started", "Начался вызов одержимого эндермита.");
        this.lang("ru_ru").add("ritual.occultism.possess_endermite.finished", "Одержимый эндермита успешно вызван.");
        this.lang("ru_ru").add("ritual.occultism.possess_endermite.interrupted", "Вызов одержимого эндермита прерван.");
        this.lang("ru_ru").add("ritual.occultism.possess_skeleton.conditions", "Не все требования удовлетворены для этого ритуала.");
        this.lang("ru_ru").add("ritual.occultism.possess_skeleton.started", "Начался вызов одержимого скелета.");
        this.lang("ru_ru").add("ritual.occultism.possess_skeleton.finished", "Одержимый скелет успешно вызван.");
        this.lang("ru_ru").add("ritual.occultism.possess_skeleton.interrupted", "Вызов одержимого скелета прерван.");
        this.lang("ru_ru").add("ritual.occultism.possess_enderman.conditions", "Не все требования удовлетворены для этого ритуала.");
        this.lang("ru_ru").add("ritual.occultism.possess_enderman.started", "Начался вызов одержимого эндермена.");
        this.lang("ru_ru").add("ritual.occultism.possess_enderman.finished", "Одержимый эндермен успешно вызван.");
        this.lang("ru_ru").add("ritual.occultism.possess_enderman.interrupted", "Вызов одержимого эндермена прерван.");
        this.lang("ru_ru").add("ritual.occultism.possess_ghast.conditions", "Не все требования удовлетворены для этого ритуала.");
        this.lang("ru_ru").add("ritual.occultism.possess_ghast.started", "Начался вызов одержимого гаста.");
        this.lang("ru_ru").add("ritual.occultism.possess_ghast.finished", "Одержимый гаст успешно вызван.");
        this.lang("ru_ru").add("ritual.occultism.possess_ghast.interrupted", "Вызов одержимого гаста прерван.");
        this.lang("ru_ru").add("ritual.occultism.possess_phantom.conditions", "Не все требования удовлетворены для этого ритуала.");
        this.lang("ru_ru").add("ritual.occultism.possess_phantom.started", "Начался вызов одержимого фантома.");
        this.lang("ru_ru").add("ritual.occultism.possess_phantom.finished", "Одержимый фантом успешно вызван.");
        this.lang("ru_ru").add("ritual.occultism.possess_phantom.interrupted", "Вызов одержимого фантома прерван.");
        this.lang("ru_ru").add("ritual.occultism.possess_weak_shulker.conditions", "Не все требования удовлетворены для этого ритуала.");
        this.lang("ru_ru").add("ritual.occultism.possess_weak_shulker.started", "Начался вызов одержимого слабого шалкера.");
        this.lang("ru_ru").add("ritual.occultism.possess_weak_shulker.finished", "Одержимый слабый шалкер успешно вызван.");
        this.lang("ru_ru").add("ritual.occultism.possess_weak_shulker.interrupted", "Вызов одержимого слабого шалкера прерван.");
        this.lang("ru_ru").add("ritual.occultism.possess_shulker.conditions", "Не все требования удовлетворены для этого ритуала.");
        this.lang("ru_ru").add("ritual.occultism.possess_shulker.started", "Начался вызов одержимого шалкера.");
        this.lang("ru_ru").add("ritual.occultism.possess_shulker.finished", "Одержимый шалкер успешно вызван.");
        this.lang("ru_ru").add("ritual.occultism.possess_shulker.interrupted", "Вызов одержимого шалкера прерван.");
        this.lang("ru_ru").add("ritual.occultism.possess_elder_guardian.conditions", "Не все требования удовлетворены для этого ритуала.");
        this.lang("ru_ru").add("ritual.occultism.possess_elder_guardian.started", "Начался вызов одержимого древнего стража.");
        this.lang("ru_ru").add("ritual.occultism.possess_elder_guardian.finished", "Одержимый древний страж успешно вызван.");
        this.lang("ru_ru").add("ritual.occultism.possess_elder_guardian.interrupted", "Вызов одержимого древнего стража прерван.");
        this.lang("ru_ru").add("ritual.occultism.possess_warden.conditions", "Не все требования удовлетворены для этого ритуала.");
        this.lang("ru_ru").add("ritual.occultism.possess_warden.started", "Начался вызов одержимого хранителя.");
        this.lang("ru_ru").add("ritual.occultism.possess_warden.finished", "Одержимый хранитель успешно вызван.");
        this.lang("ru_ru").add("ritual.occultism.possess_warden.interrupted", "Вызов одержимого хранителя прерван.");
        this.lang("ru_ru").add("ritual.occultism.possess_hoglin.conditions", "Не все требования удовлетворены для этого ритуала.");
        this.lang("ru_ru").add("ritual.occultism.possess_hoglin.started", "Начался вызов одержимого хоглина.");
        this.lang("ru_ru").add("ritual.occultism.possess_hoglin.finished", "Одержимый хоглин успешно вызван.");
        this.lang("ru_ru").add("ritual.occultism.possess_hoglin.interrupted", "Вызов одержимого хоглина прерван.");
        this.lang("ru_ru").add("ritual.occultism.familiar_otherworld_bird.conditions", "Не все требования удовлетворены для этого ритуала.");
        this.lang("ru_ru").add("ritual.occultism.familiar_otherworld_bird.started", "Начался вызов дрикрыла-фамильяр.");
        this.lang("ru_ru").add("ritual.occultism.familiar_otherworld_bird.finished", "Дрикрыл-фамильяр успешно вызван.");
        this.lang("ru_ru").add("ritual.occultism.familiar_otherworld_bird.interrupted", "Вызов дриклыра-фамильяра прерван.");
        this.lang("ru_ru").add("ritual.occultism.familiar_cthulhu.conditions", "Не все требования удовлетворены для этого ритуала.");
        this.lang("ru_ru").add("ritual.occultism.familiar_cthulhu.started", "Начался вызов ктулху-фамильяра.");
        this.lang("ru_ru").add("ritual.occultism.familiar_cthulhu.finished", "Ктулху-фамильяр успешно вызван.");
        this.lang("ru_ru").add("ritual.occultism.familiar_cthulhu.interrupted", "Вызов ктулху-фамильяра прерван.");
        this.lang("ru_ru").add("ritual.occultism.familiar_devil.conditions", "Не все требования удовлетворены для этого ритуала.");
        this.lang("ru_ru").add("ritual.occultism.familiar_devil.started", "Начался вызов дьявола-фамильяра.");
        this.lang("ru_ru").add("ritual.occultism.familiar_devil.finished", "Дьявол-фамильяр успешно вызван.");
        this.lang("ru_ru").add("ritual.occultism.familiar_devil.interrupted", "Вызов дьявола-фамильяра прерван.");
        this.lang("ru_ru").add("ritual.occultism.familiar_dragon.conditions", "Не все требования удовлетворены для этого ритуала.");
        this.lang("ru_ru").add("ritual.occultism.familiar_dragon.started", "Начался вызов дракона-фамильяра.");
        this.lang("ru_ru").add("ritual.occultism.familiar_dragon.finished", "Дракон-фамильяр успешно вызван.");
        this.lang("ru_ru").add("ritual.occultism.familiar_dragon.interrupted", "Вызов дракона-фамильяра прерван.");
        this.lang("ru_ru").add("ritual.occultism.familiar_blacksmith.conditions", "Не все требования удовлетворены для этого ритуала.");
        this.lang("ru_ru").add("ritual.occultism.familiar_blacksmith.started", "Начался вызов кузнеца-фамильяра.");
        this.lang("ru_ru").add("ritual.occultism.familiar_blacksmith.finished", "Кузнец-фамильяр успешно вызван.");
        this.lang("ru_ru").add("ritual.occultism.familiar_blacksmith.interrupted", "Вызов кузнеца-фамильяра прерван.");
        this.lang("ru_ru").add("ritual.occultism.familiar_guardian.conditions", "Не все требования удовлетворены для этого ритуала.");
        this.lang("ru_ru").add("ritual.occultism.familiar_guardian.started", "Начался вызов стража-фамильяр.");
        this.lang("ru_ru").add("ritual.occultism.familiar_guardian.finished", "Страж-фамильяр успешно вызван.");
        this.lang("ru_ru").add("ritual.occultism.familiar_guardian.interrupted", "Вызов стража-фамильяра прерван.");
        this.lang("ru_ru").add("ritual.occultism.summon_wild_otherworld_bird.conditions", "Не все требования удовлетворены для этого ритуала.");
        this.lang("ru_ru").add("ritual.occultism.summon_wild_otherworld_bird.started", "Начался вызов дикого дрикрыла.");
        this.lang("ru_ru").add("ritual.occultism.summon_wild_otherworld_bird.finished", "Дикий дрикрыл успешно вызван.");
        this.lang("ru_ru").add("ritual.occultism.summon_wild_otherworld_bird.interrupted", "Вызов дикого дрикрыла прерван.");
        this.lang("ru_ru").add("ritual.occultism.summon_wild_parrot.conditions", "Не все требования удовлетворены для этого ритуала.");
        this.lang("ru_ru").add("ritual.occultism.summon_wild_parrot.started", "Начался вызов дикого попугая.");
        this.lang("ru_ru").add("ritual.occultism.summon_wild_parrot.finished", "Дикий попугай успешно вызван.");
        this.lang("ru_ru").add("ritual.occultism.summon_wild_parrot.interrupted", "Вызов дикого попугая прерван.");
        this.lang("ru_ru").add("ritual.occultism.familiar_parrot.conditions", "Не все требования удовлетворены для этого ритуала.");
        this.lang("ru_ru").add("ritual.occultism.familiar_parrot.started", "Начался вызов попугая-фамильяра.");
        this.lang("ru_ru").add("ritual.occultism.familiar_parrot.finished", "Попугай-фамильяр успешно вызван.");
        this.lang("ru_ru").add("ritual.occultism.familiar_parrot.interrupted", "Вызов попугая-фамильяра прерван.");
        this.lang("ru_ru").add("ritual.occultism.summon_allay.conditions", "Не все требования удовлетворены для этого ритуала.");
        this.lang("ru_ru").add("ritual.occultism.summon_allay.started", "Началось очищение Вредины до Тихони.");
        this.lang("ru_ru").add("ritual.occultism.summon_allay.finished", "Вредина успешно очищена до Тихони.");
        this.lang("ru_ru").add("ritual.occultism.summon_allay.interrupted", "Очищение Вредины до Тихони прервано.");
        this.lang("ru_ru").add("ritual.occultism.familiar_greedy.conditions", "Не все требования удовлетворены для этого ритуала.");
        this.lang("ru_ru").add("ritual.occultism.familiar_greedy.started", "Начался вызов алчного фамильяра.");
        this.lang("ru_ru").add("ritual.occultism.familiar_greedy.finished", "Алчный фамильяр успешно вызван.");
        this.lang("ru_ru").add("ritual.occultism.familiar_greedy.interrupted", "Вызов алчного фамильяра прерван.");
        this.lang("ru_ru").add("ritual.occultism.familiar_bat.conditions", "Не все требования удовлетворены для этого ритуала.");
        this.lang("ru_ru").add("ritual.occultism.familiar_bat.started", "Начался вызов летучей мыши-фамильяра.");
        this.lang("ru_ru").add("ritual.occultism.familiar_bat.finished", "Летучая мышь-фамильяр успешно вызван.");
        this.lang("ru_ru").add("ritual.occultism.familiar_bat.interrupted", "Вызов летучей мыши-фамильяра прерван.");
        this.lang("ru_ru").add("ritual.occultism.familiar_deer.conditions", "Не все требования удовлетворены для этого ритуала.");
        this.lang("ru_ru").add("ritual.occultism.familiar_deer.started", "Начался вызов оленя-фамильяра.");
        this.lang("ru_ru").add("ritual.occultism.familiar_deer.finished", "Олень-фамильяр успешно вызван.");
        this.lang("ru_ru").add("ritual.occultism.familiar_deer.interrupted", "Вызов оленя-фамильяра прерван.");
        this.lang("ru_ru").add("ritual.occultism.familiar_headless.conditions", "Не все требования удовлетворены для этого ритуала.");
        this.lang("ru_ru").add("ritual.occultism.familiar_headless.started", "Начался вызов безголового человека-красы-фамильяра.");
        this.lang("ru_ru").add("ritual.occultism.familiar_headless.finished", "Безголовый человек-краса-фамильяр успешно вызван.");
        this.lang("ru_ru").add("ritual.occultism.familiar_headless.interrupted", "Вызов безголового человека-красы-фамильяра прерван.");
        this.lang("ru_ru").add("ritual.occultism.familiar_chimera.conditions", "Не все требования удовлетворены для этого ритуала.");
        this.lang("ru_ru").add("ritual.occultism.familiar_chimera.started", "Начался вызов химеры-фамильяра.");
        this.lang("ru_ru").add("ritual.occultism.familiar_chimera.finished", "Химера-фамильяр успешно вызван.");
        this.lang("ru_ru").add("ritual.occultism.familiar_chimera.interrupted", "Вызов химеры-фамильяра прерван.");
        this.lang("ru_ru").add("ritual.occultism.familiar_beholder.conditions", "Не все требования удовлетворены для этого ритуала.");
        this.lang("ru_ru").add("ritual.occultism.familiar_beholder.started", "Начался вызов созерцателя-фамильяра.");
        this.lang("ru_ru").add("ritual.occultism.familiar_beholder.finished", "Созерцатель-фамильяр успешно вызван.");
        this.lang("ru_ru").add("ritual.occultism.familiar_beholder.interrupted", "Вызов созерцателя-фамильяра прерван.");
        this.lang("ru_ru").add("ritual.occultism.familiar_fairy.conditions", "Не все требования удовлетворены для этого ритуала.");
        this.lang("ru_ru").add("ritual.occultism.familiar_fairy.started", "Начался вызов феи-фамильяра.");
        this.lang("ru_ru").add("ritual.occultism.familiar_fairy.finished", "Фея-фамильяр успешно вызван.");
        this.lang("ru_ru").add("ritual.occultism.familiar_fairy.interrupted", "Вызов феи-фамильяра прерван.");
        this.lang("ru_ru").add("ritual.occultism.familiar_mummy.conditions", "Не все требования удовлетворены для этого ритуала.");
        this.lang("ru_ru").add("ritual.occultism.familiar_mummy.started", "Начался вызов мумии-фамильяр.");
        this.lang("ru_ru").add("ritual.occultism.familiar_mummy.finished", "Мумия-фамильяр успешно вызван.");
        this.lang("ru_ru").add("ritual.occultism.familiar_mummy.interrupted", "Вызов мумии-фамильяра прерван.");
        this.lang("ru_ru").add("ritual.occultism.familiar_beaver.conditions", "Не все требования удовлетворены для этого ритуала.");
        this.lang("ru_ru").add("ritual.occultism.familiar_beaver.started", "Начался вызов бобра-фамильяра.");
        this.lang("ru_ru").add("ritual.occultism.familiar_beaver.finished", "Бобёр-фамильяр успешно вызван.");
        this.lang("ru_ru").add("ritual.occultism.familiar_beaver.interrupted", "Вызов бобра-фамильяра прерван.");

        this.lang("ru_ru").add("ritual.occultism.summon_demonic_wife.conditions", "Не все требования удовлетворены для этого ритуала.");
        this.lang("ru_ru").add("ritual.occultism.summon_demonic_wife.started", "Начался вызов.");
        this.lang("ru_ru").add("ritual.occultism.summon_demonic_wife.finished", "Успешно вызван.");
        this.lang("ru_ru").add("ritual.occultism.summon_demonic_wife.interrupted", "Вызов прерван.");
        this.lang("ru_ru").add("ritual.occultism.summon_demonic_husband.conditions", "Не все требования удовлетворены для этого ритуала.");
        this.lang("ru_ru").add("ritual.occultism.summon_demonic_husband.started", "Начался вызов.");
        this.lang("ru_ru").add("ritual.occultism.summon_demonic_husband.finished", "Успешно вызван.");
        this.lang("ru_ru").add("ritual.occultism.summon_demonic_husband.interrupted", "Вызов прерван.");

        this.lang("ru_ru").add("ritual.occultism.summon_wild_husk.conditions", "Не все требования удовлетворены для этого ритуала.");
        this.lang("ru_ru").add("ritual.occultism.summon_wild_husk.started", "Начался вызов орды диких кадавров.");
        this.lang("ru_ru").add("ritual.occultism.summon_wild_husk.finished", "Орда диких кадавров успешна вызвана.");
        this.lang("ru_ru").add("ritual.occultism.summon_wild_husk.interrupted", "Вызов орды диких кадавров прерван.");
        this.lang("ru_ru").add("ritual.occultism.summon_wild_drowned.conditions", "Не все требования удовлетворены для этого ритуала.");
        this.lang("ru_ru").add("ritual.occultism.summon_wild_drowned.started", "Начался вызов орды диких утопленников.");
        this.lang("ru_ru").add("ritual.occultism.summon_wild_drowned.finished", "Орда диких утопленников успешна вызвана.");
        this.lang("ru_ru").add("ritual.occultism.summon_wild_drowned.interrupted", "Вызов орды диких утопленников прерван.");
        this.lang("ru_ru").add("ritual.occultism.summon_wild_creeper.conditions", "Не все требования удовлетворены для этого ритуала.");
        this.lang("ru_ru").add("ritual.occultism.summon_wild_creeper.started", "Начался вызов орды диких криперов.");
        this.lang("ru_ru").add("ritual.occultism.summon_wild_creeper.finished", "Орда диких криперов успешна вызвана.");
        this.lang("ru_ru").add("ritual.occultism.summon_wild_creeper.interrupted", "Вызов орды диких криперов прерван.");
        this.lang("ru_ru").add("ritual.occultism.summon_wild_silverfish.conditions", "Не все требования удовлетворены для этого ритуала.");
        this.lang("ru_ru").add("ritual.occultism.summon_wild_silverfish.started", "Начался вызов орды диких чешуйниц.");
        this.lang("ru_ru").add("ritual.occultism.summon_wild_silverfish.finished", "Орда диких чешуйниц успешно вызвана.");
        this.lang("ru_ru").add("ritual.occultism.summon_wild_silverfish.interrupted", "Вызов орды диких чешуйниц прерван.");
        this.lang("ru_ru").add("ritual.occultism.possess_weak_breeze.conditions", "Не все требования удовлетворены для этого ритуала.");
        this.lang("ru_ru").add("ritual.occultism.possess_weak_breeze.started", "Начался вызов одержимого слабого вихря.");
        this.lang("ru_ru").add("ritual.occultism.possess_weak_breeze.finished", "Одержимый слабый вихрь успешно вызван.");
        this.lang("ru_ru").add("ritual.occultism.possess_weak_breeze.interrupted", "Вызов одержимого слабого вихря прерван.");
        this.lang("ru_ru").add("ritual.occultism.possess_breeze.conditions", "Не все требования удовлетворены для этого ритуала.");
        this.lang("ru_ru").add("ritual.occultism.possess_breeze.started", "Начался вызов одержимого вихря.");
        this.lang("ru_ru").add("ritual.occultism.possess_breeze.finished", "Одержимый вихрь успешно вызван.");
        this.lang("ru_ru").add("ritual.occultism.possess_breeze.interrupted", "Вызов одержимого вихря прерван.");
        this.lang("ru_ru").add("ritual.occultism.possess_strong_breeze.conditions", "Не все требования удовлетворены для этого ритуала.");
        this.lang("ru_ru").add("ritual.occultism.possess_strong_breeze.started", "Начался вызов одержимого сильного вихря.");
        this.lang("ru_ru").add("ritual.occultism.possess_strong_breeze.finished", "Одержимый сильный вихрь успешно вызван.");
        this.lang("ru_ru").add("ritual.occultism.possess_strong_breeze.interrupted", "Вызов одержимого сильного вихря прерван.");
        this.lang("ru_ru").add("ritual.occultism.summon_horde_illager.conditions", "Не все требования удовлетворены для этого ритуала.");
        this.lang("ru_ru").add("ritual.occultism.summon_horde_illager.started", "Начался вызов небольшого нашествия обитателей.");
        this.lang("ru_ru").add("ritual.occultism.summon_horde_illager.finished", "Небольшое нашествие обитателей успешно вызвано.");
        this.lang("ru_ru").add("ritual.occultism.summon_horde_illager.interrupted", "Вызов небольшого нашествия обитателей прервано.");

        this.addRitualMessage(OccultismRituals.RESURRECT_FAMILIAR, "conditions", "Не все требования удовлетворены для этого ритуала.");
        this.addRitualMessage(OccultismRituals.RESURRECT_FAMILIAR, "started", "Началось воскресение фамильяра.");
        this.addRitualMessage(OccultismRituals.RESURRECT_FAMILIAR, "finished", "Фамильяр успешно воскрешён.");
        this.addRitualMessage(OccultismRituals.RESURRECT_FAMILIAR, "interrupted", "Воскресение прервано.");
    }

    public void addRitualMessage(DeferredHolder<RitualFactory, RitualFactory> ritual, String key, String message) {
        this.add("ritual.%s.%s".formatted(ritual.getId().getNamespace(), ritual.getId().getPath()) + "." + key, message);
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
                        Пентакли используются для вызова и связывания духов из [#](%1$s)Иного Места[#]().
                        """.formatted(COLOR_PURPLE));

        helper.page("intro2");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        Они оба действуют как устройство вызова существа, усилителя повелительной силы вызывателя,
                        так и защитного круга, предотвращающего внутренние атаки против вызывателя.
                        """.formatted(COLOR_PURPLE));

        helper.page("intro3");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        В состав каждого пентакля входит золотая миска для жертвоприношений по центру, окружающие руны различных цветов и оккультные принадлежности, что улучшают желаемый результат различными способами.
                        """.formatted(COLOR_PURPLE));


        helper.page("intro4");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        Сочетание выбранных рун и второстепенных предметов, в том числе их точное пространственное расположение, определяет использование и эффективность пентакля.
                        \\
                        \\
                        Ингредиенты размещаются в [#](%1$s)Миски для жертвоприношений[#]() рядом с пентаклем.
                         """.formatted(COLOR_PURPLE));

        //точная копия расположена в первой записи ритуала
        helper.page("bowl_placement");
        //текст отсутствует

        //точная копия расположена в первой записи ритуала
        helper.page("bowl_text");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        [Миски для жертвоприношений](item://occultism:sacrificial_bowl) должны быть размещёны **где угодно** в пределах 8 блоков от центральной [](item://occultism:golden_sacrificial_bowl). Точное расположение не имеет значения.
                        \\
                        \\
                        Теперь уже пора расположить видимые Вами ингредиенты на следующей странице в (обычные, не золотые) Миски для жертвоприношений.
                          """);

        helper.page("summoning_pentacles");
        this.lang("ru_ru").add(helper.pageTitle(), "Пентакли для вызова");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        Цель такого типа пентакля состоит в том, чтобы вызвать духов в мир в своём выбранном облике. По этой причине, вызванные духи страдают от сильного распада сущности, и лишь очень могущественные духи могут продержаться в выбранном облике в течение длительного периода времени.
                        """);

        helper.page("infusion_pentacles");
        this.lang("ru_ru").add(helper.pageTitle(), "Пентакли наполнения");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        Пентакли наполнения позволяют связывать духов в объекты. Хотя духи страдают от распада сущности в определённых случаях, как правило, это может быть предотвращено правильной конфигурацией Пентакля и внедрением кристаллов, а также драгоценных металлов в объект для сдерживания духа.
                        """);

        helper.page("possession_pentacles");
        this.lang("ru_ru").add(helper.pageTitle(), "Пентакли для завладения");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        Эти пентакли принуждают духов овладеть живым существом, которые в зависимости от связи ритуала, дают вызывателю контроль над различными чертами этого существа, начиная от его силы и заканчивая тем, что с него выпадает при убийстве, а в некоторых случаях даже допускают полный контроль.
                        """);

        helper.entry("paraphernalia");
        this.lang("ru_ru").add(helper.entryName(), "Оккультные принадлежности");

        helper.page("intro");
        this.lang("ru_ru").add(helper.pageTitle(), "Оккультные принадлежности");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        Не говоря о рунах, различные оккультные принадлежности используются для улучшения желаемого результата.
                        """);

        helper.page("candle");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        Свечи увеличивают стабильность пентакля, таким образом, позволяя замедлить распад сущности вызванного духа, что влечёт за собой более длительное время жизни духа, либо овладевшего объектом, либо существом.
                        """);

        helper.page("crystal");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        Кристаллы увеличивают связывающую силу пентакля, что позволяет постоянно связывать духа в объекты или живым существом.
                        """);

        helper.page("gem_recipe");
        //текст отсутствует

        helper.page("crystal_recipe");
        //текст отсутствует

        helper.page("skeleton_skull");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        Черепа увеличивают вызывную мощность пентакля, что позволяет вызывать опаснейших духов.
                        """);

        helper.entry("chalk_uses");
        this.lang("ru_ru").add(helper.entryName(), "Виды мела");

        helper.page("intro");
        this.lang("ru_ru").add(helper.pageTitle(), "Виды мела");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        Мел используется для черчения рун и определения формы пентакля. Для различных целей используются разные виды мелков, как уже указано на следующих страницах.
                        \\
                        \\
                        Разные руны сугубо декоративны.
                        """);

        helper.page("intro2");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        **Периодически** используйте мел по блоку, чтобы переключаться между разными рунами.
                        \\
                        \\
                        Применение [](item://occultism:brush) — простейший способ **удалить** неправильно размещённые меловые руны.
                        """);

        helper.page("white_chalk");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        Мел — самый базовый вид ритуального мела и встречается в большинстве пентаклей. У него отсутствуют особые силы, кроме определения формы пентакля.
                           """);


        helper.page("white_chalk_uses");
        this.lang("ru_ru").add(helper.pageTitle(), "Использование мела");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        - [Круг Авиара](entry://occultism:dictionary_of_spirits/pentacles/summon_foliot)
                        - [Спектральное вынуждение Изива](entry://occultism:dictionary_of_spirits/pentacles/craft_foliot)
                        - [Приманка Гидирина](entry://occultism:dictionary_of_spirits/pentacles/possess_foliot)
                        - [Зов Офикса](entry://occultism:dictionary_of_spirits/pentacles/summon_djinni)
                        - [Заточение высшего Стригора](entry://occultism:dictionary_of_spirits/pentacles/craft_djinni)
                        - [Порабощение Айгана](entry://occultism:dictionary_of_spirits/pentacles/possess_djinni)
                        - [Вызов Абраса](entry://occultism:dictionary_of_spirits/pentacles/summon_afrit)
                        - [Вызов свободного Абраса](entry://occultism:dictionary_of_spirits/pentacles/craft_afrit)
                        - [Вызов свободного Абраса](entry://occultism:dictionary_of_spirits/pentacles/summon_wild_afrit)
                         """);

        helper.page("white_chalk_uses2");
        this.lang("ru_ru").add(helper.pageTitle(), "Использование мела");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        - [Перевёрнутая башня Юфиксеса](entry://occultism:dictionary_of_spirits/pentacles/craft_marid)
                        - [Вызов несвязанного Осорина](entry://occultism:dictionary_of_spirits/pentacles/summon_wild_greater_spirit)
                         """);

        helper.page("golden_chalk");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        Золотой мел используется для связывания рун, позволяющий заключить душу в предмет или заставить его овладеть живым существом.
                               """);


        helper.page("golden_chalk_uses");
        this.lang("ru_ru").add(helper.pageTitle(), "Использование золотого мела");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        - [Спектральное вынуждение Изива](entry://occultism:dictionary_of_spirits/pentacles/craft_foliot)
                        - [Приманка Гидирина](entry://occultism:dictionary_of_spirits/pentacles/possess_foliot)
                        - [Заточение высшего Стригора](entry://occultism:dictionary_of_spirits/pentacles/craft_djinni)
                        - [Порабощение Айгана](entry://occultism:dictionary_of_spirits/pentacles/possess_djinni)
                        - [Вечное заточение Севиры](entry://occultism:dictionary_of_spirits/pentacles/craft_afrit)
                        - [Перевёрнутая башня Юфиксеса](entry://occultism:dictionary_of_spirits/pentacles/craft_marid)
                           """);

        helper.page("purple_chalk");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        Пурпурный мел, как правило, используется для вызова высших существ, таких как [#](%1$s)Джинн[#]() или [#](%1$s)Африт[#](), в том числе и для замедления распада сущности вызванных духов.
                               """.formatted(COLOR_PURPLE));

        helper.page("purple_chalk_uses");
        this.lang("ru_ru").add(helper.pageTitle(), "Использование пурпурного мела");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        - [Зов Офикса](entry://occultism:dictionary_of_spirits/pentacles/summon_djinni)
                        - [Заточение высшего Стригора](entry://occultism:dictionary_of_spirits/pentacles/craft_djinni)
                        - [Порабощение Айгана](entry://occultism:dictionary_of_spirits/pentacles/possess_djinni)
                        - [Вызов Абраса](entry://occultism:dictionary_of_spirits/pentacles/summon_afrit)
                        - [Вечное заточение Севиры](entry://occultism:dictionary_of_spirits/pentacles/craft_afrit)
                        - [Вызов свободного Абраса](entry://occultism:dictionary_of_spirits/pentacles/summon_wild_afrit)
                        - [Вызов несвязанного Осорина](entry://occultism:dictionary_of_spirits/pentacles/summon_wild_greater_spirit)
                           """);

        helper.page("red_chalk");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        Красный мел используется для вызова самых могущественных и опаснейших существ, таких как [#](%1$s)Марид[#]().
                        \\
                        \\
                        [Сущность Африта](entry://occultism:dictionary_of_spirits/summoning_rituals/afrit_essence) требуется для создания красного мела.
                                """.formatted(COLOR_PURPLE));

        helper.page("red_chalk_uses");
        this.lang("ru_ru").add(helper.pageTitle(), "Использование красного мела");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        - [Вызов Абраса](entry://occultism:dictionary_of_spirits/pentacles/summon_afrit)
                        - [Перевёрнутая башня Юфиксеса](entry://occultism:dictionary_of_spirits/pentacles/craft_marid)
                           """);


        helper.entry("summon_foliot");
        this.lang("ru_ru").add(helper.entryName(), "Круг Авиара");

        helper.page("intro");
        this.lang("ru_ru").add(helper.pageTitle(), "Круг Авиара");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        **Цель:** Вызов [#](%1$s)Фолиота[#]()
                        \\
                        \\
                        По общепринятому мнению, [#](%1$s)Круг Авиара[#]() — самый простейший, удобоустанавливаемый пентакль, но предоставляет всего лишь минимальную силу связывания и защиту вызывателя.
                        \\
                        \\
                        При помощи этого пентакля в ритуалах можно вызывать только слабейшего [#](%1$s)Фолиота[#]().
                        """.formatted(COLOR_PURPLE));

        helper.page("multiblock");
        //текст отсутствует

        helper.page("uses");
        this.lang("ru_ru").add(helper.pageTitle(), "Использование");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        - [Дробильщик-Фолиот](entry://summoning_rituals/summon_crusher_t1)
                        - [Дровосек-Фолиот](entry://summoning_rituals/summon_lumberjack)
                        - [Транспортировщик-Фолиот](entry://summoning_rituals/summon_transport_items)
                        - [Дворник-Фолиот](entry://summoning_rituals/summon_cleaner)
                        - [Торговец потусторонним камнем](entry://summoning_rituals/summon_otherstone_trader)
                        - [Торговец потусторонними саженцами](entry://summoning_rituals/summon_otherworld_sapling_trader)
                        - [Воскресение фамильяра](entry://familiar_rituals/resurrection)
                        - [Очищение Вредины до Тихони](entry://familiar_rituals/summon_allay)
                        """);


        helper.entry("summon_djinni");
        this.lang("ru_ru").add(helper.entryName(), "Зов Офикса");

        helper.page("intro");
        this.lang("ru_ru").add(helper.pageTitle(), "Зов Офикса");
        //Добавьте rituals/possession/possess_skeleton вместо [Приобретаются здесь]
        this.lang("ru_ru").add(helper.pageText(),
                """
                        **Цель:** Вызов [#](%1$s)Джинна[#]()
                        \\
                        \\
                        Разработанный [#](%1$s)Офиксом[#]() во время Третьей Эры. С тех пор [#](%1$s)Вызов[#]() является высококлассным пентаклем для вызова [#](%1$s)Джинна[#](). Черепа скелета ([Приобретаются здесь](entry://possession_rituals/possess_skeleton)), а [#](%1$s)Пурпурный мел[#]() обеспечивает необходимую силу, чтобы силой заставить Джинна появится в облике; а свечи стабилизируют ритуал.
                         """.formatted(COLOR_PURPLE));

        helper.page("multiblock");
        //текст отсутствует

        helper.page("uses");
        this.lang("ru_ru").add(helper.pageTitle(), "Использование");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        - [Станочник-Джинн](entry://summoning_rituals/summon_manage_machine)
                        - [Дробильщик-Джинн](entry://summoning_rituals/summon_crusher_t2)
                        - [Ясная погода](entry://summoning_rituals/weather_magic@clear)
                        - [Магия времени](entry://summoning_rituals/time_magic)
                        """.formatted(COLOR_PURPLE));

        helper.entry("summon_afrit");
        this.lang("ru_ru").add(helper.entryName(), "Вызов Абраса");
        helper.page("intro");
        this.lang("ru_ru").add(helper.pageTitle(), "Вызов Абраса");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        **Цель:** Вызов [#](%1$s)Африта[#]()
                        \\
                        \\
                        **Вызов Абраса** — один из нескольких пентаклей, способных (наиболее) безопасно вызывать [#](%1$s)Африта[#](). Хотя требование черепа Визер-скелета делает его относительно дорогостоящим, а чтобы достичь потенциально могущественных духов, потребуется выполнить дополнительный вызов.
                         """.formatted(COLOR_PURPLE));

        helper.page("multiblock");
        //текст отсутствует

        helper.page("uses");
        this.lang("ru_ru").add(helper.pageTitle(), "Использование");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        - [Гроза](entry://summoning_rituals/weather_magic@thunder)
                        - [Дождливая погода](entry://summoning_rituals/weather_magic@rain)
                        - [Дробильщик-Африт](entry://summoning_rituals/summon_crusher_t3)
                        """.formatted(COLOR_PURPLE));

        helper.entry("summon_marid");
        this.lang("ru_ru").add(helper.entryName(), "Поощряемое привлечение Фатмы");
        helper.page("intro");
        this.lang("ru_ru").add(helper.pageTitle(), "Поощряемое привлечение Фатмы");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        **Цель:** Вызов [#](%1$s)Марида[#]()
                        \\
                        \\
                        **Поощряемое привлечение Фатмы** — мощный пентакль, позволяющий вызывать [#](%1$s)Марида[#]() и связывать его волей вызывателя.
                         """.formatted(COLOR_PURPLE));

        helper.page("multiblock");
        //текст отсутствует

        helper.page("uses");
        this.lang("ru_ru").add(helper.pageTitle(), "Использование");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        - [Дробильщик-Марид](entry://summoning_rituals/summon_crusher_t4)
                        """.formatted(COLOR_PURPLE));

        helper.entry("summon_wild_afrit");
        this.lang("ru_ru").add(helper.entryName(), "Вызов свободного Абраса");
        helper.page("intro");
        this.lang("ru_ru").add(helper.pageTitle(), "Вызов свободного Абраса");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        **Цель:** Вызов несвязанного [#](%1$s)Африта[#]()
                        \\
                        \\
                        **Вызов свободного Абраса** — упрощённая версия [#](%1$s)Вызова Абраса[#](), позволяющая вызывать [#](%1$s)Африта[#]() без красного мела. Из-за очень низкой силы пентакля, его нельзя использовать с целью контроля над [#](%1$s)Афритом[#](), , а значит, его можно использовать только для сражения и убийства [#](%1$s)Африта[#]().
                         """.formatted(COLOR_PURPLE));

        helper.page("multiblock");
        //текст отсутствует

        helper.page("uses");
        this.lang("ru_ru").add(helper.pageTitle(), "Использование");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        - [Сущность Африта](entry://summoning_rituals/afrit_essence)
                        """.formatted(COLOR_PURPLE));

        helper.entry("summon_wild_greater_spirit");
        this.lang("ru_ru").add(helper.entryName(), "Вызов несвязанного Осорина");
        helper.page("intro");
        this.lang("ru_ru").add(helper.pageTitle(), "Вызов несвязанного Осорина");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        **Цель:** Вызов несвязанного [#](%1$s)Могущественного духа[#]()
                        \\
                        \\
                        **Вызов несвязанного Осорина** основан на [#](%1$s)Вызове свободного Абраса[#](), но совсем не использует стабилизирующие принадлежности. Пентакль совершенно не обеспечивает защиту вызывателя, но действует в качестве вызова неодолимых [#](%1$s)Могущественных духов[#]().
                        """.formatted(COLOR_PURPLE));

        helper.page("multiblock");
        //текст отсутствует

        helper.page("uses");
        this.lang("ru_ru").add(helper.pageTitle(), "Использование");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        - [Череп визер-скелета](entry://summoning_rituals/wither_skull)
                        - [Орда кадавров](entry://possession_rituals/horde_husk)
                        - [Орда утопленников](entry://possession_rituals/horde_drowned)
                        - [Орда криперов](entry://possession_rituals/horde_creeper)
                        - [Орда чешуйниц](entry://possession_rituals/horde_silverfish)
                        - [Ключ испытаний](entry://possession_rituals/possess_weak_breeze)
                        - [Зловещий ключ испытаний](entry://possession_rituals/possess_breeze)
                        - [Навершие булавы](entry://possession_rituals/possess_strong_breeze)
                        - [Небольшое нашествие обитателей](entry://possession_rituals/horde_illager)
                        """.formatted(COLOR_PURPLE));

        helper.entry("possess_foliot");
        this.lang("ru_ru").add(helper.entryName(), "Приманка Гидирина");
        helper.page("intro");
        this.lang("ru_ru").add(helper.pageTitle(), "Приманка Гидирина");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        **Цель:** Завладение Фолиота.
                        \\
                        \\
                        **Приманка Гидирина** заманивает [#](%1$s)Фолиота[#]() и принуждает его завладеть близлежащим существом. Пентакль не приводит к постоянному заключению. Дух и одержимое существо вскоре погибнут.
                         """.formatted(COLOR_PURPLE));

        helper.page("multiblock");
        //текст отсутствует

        helper.page("uses");
        this.lang("ru_ru").add(helper.pageTitle(), "Использование");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        - [Одержимый эндермит](entry://possession_rituals/possess_endermite)
                        - [Одержимый скелет](entry://possession_rituals/possess_skeleton)
                        - [Одержимый фантом](entry://possession_rituals/possess_phantom)
                        - [Попугай-фамильяр](entry://familiar_rituals/familiar_parrot)
                        - [Алчный фамильяр](entry://familiar_rituals/familiar_greedy)
                        - [Олень-фамильяр](entry://familiar_rituals/familiar_deer)
                        - [Кузнец-фамильяр](entry://familiar_rituals/familiar_blacksmith)
                        - [Бобёр-фамильяр](entry://familiar_rituals/familiar_beaver)
                        """.formatted(COLOR_PURPLE));

        helper.entry("possess_djinni");
        this.lang("ru_ru").add(helper.entryName(), "Порабощение Айгана");
        helper.page("intro");
        this.lang("ru_ru").add(helper.pageTitle(), "Порабощение Айгана");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        **Цель:** Завладение Джинном
                        \\
                        \\
                        **Порабощение Айгана** принуждает [#](%1$s)Джинна[#]() овладеть близлежащим существом. Пентакль не приводит к постоянному заключению. Дух и одержимое существо вскоре погибнут.
                         """.formatted(COLOR_PURPLE));

        helper.page("multiblock");
        //текст отсутствует

        helper.page("uses");
        this.lang("ru_ru").add(helper.pageTitle(), "Использование");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        - [Одержимый эндермен](entry://possession_rituals/possess_enderman)
                        - [Одержимый гаст](entry://possession_rituals/possess_ghast)
                        - [Одержимый слабый шалкер](entry://possession_rituals/possess_weak_shulker)
                        - [Одержимый хранитель](entry://possession_rituals/possess_warden)
                        - [Дрикрыл-фамильяр](entry://familiar_rituals/familiar_otherworld_bird)
                        - [Летучая мышь-фамильяр](entry://familiar_rituals/familiar_bat)
                        - [Ктулху-фамильяр](entry://familiar_rituals/familiar_cthulhu)
                        - [Дьявол-фамильяр](entry://familiar_rituals/familiar_devil)
                        - [Дракон-фамильяр](entry://familiar_rituals/familiar_dragon)
                        - [Безголовый человек-крыса-фамильяр](entry://familiar_rituals/familiar_headless)
                        - [Химера-фамильяр](entry://familiar_rituals/familiar_chimera)
                        """.formatted(COLOR_PURPLE));

        helper.page("uses2");
        this.lang("ru_ru").add(helper.pageTitle(), "Использование");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        - [Созерцатель-фамильяр](entry://familiar_rituals/familiar_beholder)                        
                        - [Фея-фамильяр](entry://familiar_rituals/familiar_fairy)
                        """.formatted(COLOR_PURPLE));

        helper.entry("possess_afrit");
        this.lang("ru_ru").add(helper.entryName(), "Заклятие повиновения Абраса");
        helper.page("intro");
        this.lang("ru_ru").add(helper.pageTitle(), "Заклятие повиновения Абраса");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        **Цель:** Завладение Африта
                        \\
                        \\
                        **Заклятие повиновения Абраса** — модифицированная версия [#](%1$s)Вызова Абраса[#](), позволяющая завладеть существами и благодаря этому вызвать фамильяров.
                         """.formatted(COLOR_PURPLE));

        helper.page("multiblock");
        //текст отсутствует

        helper.page("uses");
        this.lang("ru_ru").add(helper.pageTitle(), "Использование");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        - [Страж-фамильяр](entry://familiar_rituals/familiar_guardian)
                        - [Одержимый шалкер](entry://possession_rituals/possess_shulker)
                        - [Одержимый древний страж](entry://possession_rituals/possess_elder_guardian)
                        - [Одержимый хоглин](entry://possession_rituals/possess_hoglin)
                        """.formatted(COLOR_PURPLE));

        helper.entry("craft_foliot");
        this.lang("ru_ru").add(helper.entryName(), "Спектральное вынуждение Изива");
        helper.page("intro");
        this.lang("ru_ru").add(helper.pageTitle(), "Спектральное вынуждение Изива");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        **Цель:** Связывание Фолиота
                        \\
                        \\
                        **Спектральное вынуждение Изива** — простое начало наполнения объектов низшими духами в качестве простого пентакля связывания. При помощи свечей-стабилизаторов и кристаллов, настроенных на духа, чары становятся постоянными.
                         """.formatted(COLOR_PURPLE));

        helper.page("multiblock");
        //текст отсутствует

        helper.page("uses");
        this.lang("ru_ru").add(helper.pageTitle(), "Использование");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        - [Наполненные линзы](entry://crafting_rituals/craft_otherworld_goggles)
                        - [Изумительно большая сумка](entry://crafting_rituals/craft_satchel)
                        - [Основа актуатора хранилища](entry://crafting_rituals/craft_storage_controller_base)
                        - [Стабильная червоточина](entry://crafting_rituals/craft_stable_wormhole)
                        - [Стабилизатор хранилище 1-го уровня](entry://crafting_rituals/craft_stabilizer_tier1)
                        - [Горняк-Фолиот](entry://crafting_rituals/craft_foliot_miner)
                        """.formatted(COLOR_PURPLE));

        helper.entry("craft_djinni");
        this.lang("ru_ru").add(helper.entryName(), "Связывание высшего Стригора");
        helper.page("intro");
        this.lang("ru_ru").add(helper.pageTitle(), "Связывание высшего Стригора");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        **Цель:** Заточение Джинна
                        \\
                        \\
                        **Заточение высшего Стригора** — пентакль связывания [#](%1$s)Джинна[#]() в объекты. Не должен выполняться неопытными вызывателями. Пентакль поддерживается кристаллами, настроенными на духа и свечами-стабилизаторами, что отлично подходят для постоянного наполнения объектов духами.
                         """.formatted(COLOR_PURPLE));

        helper.page("multiblock");
        //текст отсутствует

        helper.page("uses");
        this.lang("ru_ru").add(helper.pageTitle(), "Использование");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        - [Наполненная кирка](entry://crafting_rituals/craft_infused_pickaxe)
                        - [Самоцвет душ](entry://crafting_rituals/craft_soul_gem)
                        - [Кольцо фамильяра](entry://crafting_rituals/craft_familiar_ring)
                        - [Пространственная матрица](entry://crafting_rituals/craft_dimensional_matrix)
                        - [Средство доступа хранилища](entry://crafting_rituals/craft_storage_remote)
                        - [Стабилизатор хранилища 2-го уровня](entry://crafting_rituals/craft_stabilizer_tier2)
                        - [Пространственная шахта](entry://crafting_rituals/craft_dimensional_mineshaft)
                        - [Рудный Горняк-Джинн](entry://crafting_rituals/craft_djinni_miner)
                        """.formatted(COLOR_PURPLE));

        helper.entry("craft_afrit");
        this.lang("ru_ru").add(helper.entryName(), "Вечное заточение Севиры");
        helper.page("intro");
        this.lang("ru_ru").add(helper.pageTitle(), "Вечное заточение Севиры");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        **Цель:** Связывание Африта
                        \\
                        \\
                        Первоначально открыт главной мастерицей из Тлеющих лесов Севирой. **Вечное заточение Севиры** используется для связывания [#](%1$s)Африта[#]() в объекты. Благодаря силе вовлечённых духов, такой способ должен выполняться только опытными вызывателями.
                         """.formatted(COLOR_PURPLE));

        helper.page("multiblock");
        //текст отсутствует

        helper.page("uses");
        this.lang("ru_ru").add(helper.pageTitle(), "Использование");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        - [Стабилизатор хранилища 3-го уровня](entry://crafting_rituals/craft_stabilizer_tier3)
                        - [Горняк-Африт для глубинносланцевой руды](entry://crafting_rituals/craft_afrit_miner)
                        """.formatted(COLOR_PURPLE));

        helper.entry("craft_marid");
        this.lang("ru_ru").add(helper.entryName(), "Перевёрнутая башня Юфиксеса");
        helper.page("intro");
        this.lang("ru_ru").add(helper.pageTitle(), "Перевёрнутая башня Юфиксеса");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        **Цель:** Связывание Марида
                        \\
                        \\
                        **Перевёрнутая башня Юфиксеса** — один из нескольких пентаклей, способных связать [#](%1$s)Марида[#]() в объекты. Любые ритуалы при участии [#](%1$s)Марида[#]() должны выполняться только самыми опытными вызывателями.
                         """.formatted(COLOR_PURPLE));

        helper.page("multiblock");
        //текст отсутствует

        helper.page("uses");
        this.lang("ru_ru").add(helper.pageTitle(), "Использование");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        - [Стабилизатор хранилища 4-го уровня](entry://crafting_rituals/craft_stabilizer_tier4)
                        - [Мастер Горняк-Марид](entry://crafting_rituals/craft_marid_miner)
                        - [Кузнечный шаблон](entry://crafting_rituals/craft_wild_trim)
                        """.formatted(COLOR_PURPLE));
    }

    private void addRitualsCategory(BookContextHelper helper) {
        helper.category("rituals");
        this.lang("ru_ru").add(helper.categoryName(), "Ритуалы");

        helper.entry("overview");
        this.lang("ru_ru").add(helper.entryName(), "Ритуалы");

        helper.page("intro");
        this.lang("ru_ru").add(helper.pageTitle(), "Ритуалы");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        Ритуалы позволяют вызывать духов в нашу плоскость существования, либо связывать их в предметы или живыми существами. В состав каждого ритуала входит: [#](%1$s)Пентакль[#](), [#](%1$s)Ритуальные ингредиенты[#](), предоставленные через Миски для жертвоприношений, [#](%1$s)запускающий предмет[#](), а в ряде случаев — [#](%1$s)Жертвование[#]() живыми существами. Пурпурные частицы отобразят, что ритуал удался и выполняется.
                        """.formatted(COLOR_PURPLE));

        helper.page("steps");
        this.lang("ru_ru").add(helper.pageTitle(), "Выполнение ритуала");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        Ритуалы всегда выполняются по неизменным этапам:
                        - Начертить пентакль.
                        - Поставить золотую миску.
                        - Поставить миски для жертвоприношений.
                        - Положить ингредиенты в миски.
                        - [#](%1$s)ПКМ[#]() по золотой миске при помощи активационного предмета.
                        - *При желании: выполнить жертвоприношение около центрального пентакля.*
                        """.formatted(COLOR_PURPLE));

        helper.page("additional_requirements");
        this.lang("ru_ru").add(helper.pageTitle(), "Дополнительные требования");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        Если ритуал показывает серые частицы над золотой миской для жертвоприношения, тогда нужно осуществить дополнительные требования, которые описаны на странице ритуала. После осуществления всех требований, ритуал покажет пурпурные частицы и начнёт тратить предметы в мисках для жертвоприношений.
                        """);

        helper.entry("item_use");
        this.lang("ru_ru").add(helper.entryName(), "Использование предмета");

        helper.page("intro");
        this.lang("ru_ru").add(helper.pageTitle(), "Использование предмета");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        Для выполнения некоторых ритуалов требуется использовать определённые предметы. Чтобы приступить к выполнению ритуала, нужно использовать предмет (описанный на странице ритуала), в пределах 16 блоков от [](item://occultism:golden_sacrificial_bowl).
                        \\
                        \\
                        **Важная информация**: Перед тем как использовать предмет, начните ритуал. Серые частицы означают, что ритуал готов использовать предмет.
                        """);

        helper.entry("sacrifice");
        this.lang("ru_ru").add(helper.entryName(), "Жертвоприношения");

        helper.page("intro");
        this.lang("ru_ru").add(helper.pageTitle(), "Жертвоприношения");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        Некоторые ритуалы требуют жертвоприношения живым существом, чтобы обеспечить необходимой энергией для вызова духа. Жертвоприношения описаны на странице ритуала в подразделе \"Жертвоприношение\". Для выполнения жертвоприношения, убейте животное в пределах 8 блоков от золотой Миски для жертвоприношений. Только убийства игроком считаются жертвоприношением!
                         """);

        helper.entry("summoning_rituals");
        this.lang("ru_ru").add(helper.entryName(), "Ритуалы вызова");

        helper.entry("possession_rituals");
        this.lang("ru_ru").add(helper.entryName(), "Ритуалы завладения");

        helper.entry("crafting_rituals");
        this.lang("ru_ru").add(helper.entryName(), "Ритуалы связывания");

        helper.entry("familiar_rituals");
        this.lang("ru_ru").add(helper.entryName(), "Ритуалы для фамильяров");
    }

    private void addSummoningRitualsCategory(BookContextHelper helper) {
        helper.category("summoning_rituals");
        this.lang("ru_ru").add(helper.categoryName(), "Ритуалы вызова");

        helper.entry("overview");
        this.lang("ru_ru").add(helper.entryName(), "Ритуалы вызова");

        helper.page("intro");
        this.lang("ru_ru").add(helper.pageTitle(), "Ритуалы вызова");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        Ритуалы вызова заставляют духов войти в этот мир в своём выбранном облике, что влечёт за собой небольшие ограничения на их силе; но подвергает их распаду сущности. Вызванные духи классифицируются, начиная с Духов-Торговцев, что торгуют и преобразуют предметы, заканчивая рабами-помощниками для физического труда.
                         """);

        helper.entry("return_to_rituals");
        this.lang("ru_ru").add(helper.entryName(), "Вернуться в категорию Ритуалы");

        helper.entry("summon_crusher_t1");
        //Перемещён в OccultismBookProvider#makeSummonCrusherT1Entry

        helper.entry("summon_crusher_t2");
        this.lang("ru_ru").add(helper.entryName(), "Вызов Дробильщика-Джинна");

        helper.page("intro");
        this.lang("ru_ru").add(helper.pageTitle(), "Дробильщик-Джинн");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        Дробильщик-Джинн устойчив к распаду сущности и быстрее, а также эффективнее Дробильщика-Фолиота.
                        \\
                        \\
                        Он дробит **1-у** руду в **3-и** соответствующие пыли.
                         """);

        helper.page("ritual");
        //текст отсутствует

        helper.entry("summon_crusher_t3");
        this.lang("ru_ru").add(helper.entryName(), "Вызов Дробильщика-Африта");

        helper.page("intro");
        this.lang("ru_ru").add(helper.pageTitle(), "Дробильщик-Африт");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        Дробильщик-Африт устойчив к распаду сущности и быстрее, а также эффективнее Дробильщика-Джинна.
                        \\
                        \\
                        Он дробит **1-у** руду в **4-е** соответствующие пыли.
                          """);

        helper.page("ritual");
        //текст отсутствует

        helper.entry("summon_crusher_t4");
        this.lang("ru_ru").add(helper.entryName(), "Вызов Дробильщика-Марида");

        helper.page("intro");
        this.lang("ru_ru").add(helper.pageTitle(), "Дробильщик-Марид");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        Дробильщик-Марид устойчив к распаду сущности и быстрее, а также эффективнее Дробильщика-Африта.
                        \\
                        \\
                        Он дробит **1-у** руду в **6** соответствующие пыли.
                          """);

        helper.page("ritual");
        //текст отсутствует


        helper.entry("summon_lumberjack");
        //Moved to OccultismBookProvider#makeSummonLumberjackEntry

        helper.entry("summon_transport_items");
        //Moved to OccultismBookProvider#makeSummonTransportItemsEntry


        helper.entry("summon_cleaner");
        //Moved to OccultismBookProvider#makeSummonCleanerEntry

        helper.entry("summon_manage_machine");
        this.lang("ru_ru").add(helper.entryName(), "Вызов Станочника-Джинна");

        helper.page("intro");
        this.lang("ru_ru").add(helper.pageTitle(), "Станочник-Джинн");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        Станочник перемещает предметы в свою управляющее устройство, что указаны в интерфейсе Актуатора пространственного хранилища, и возвращает результаты создания в систему хранения. Кроме того, он может использоваться для автоматического опустошения сундука в Актуатор хранилища.
                        \\
                        По сути, это создание по заказу!
                          """);

        helper.page("ritual");
        //текст отсутствует

        helper.page("tutorial");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        Чтобы использовать станочника, используйте Книгу вызова, чтобы связать Актуатор хранилища, устройство и, если захотите, разделите местоположение для извлечения (лицевая сторона, на которую Вы нажмёте, будет извлекаться!). Для устройства Вы можете дополнительно установить пользовательское название и лицевые стороны для вставки/извлечения.
                          """);

        helper.page("tutorial2");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        Обратите внимание, что установка нового устройства (либо её настройка Книгой вызова) будут сброшены настройки извлечения.
                        \\
                        \\
                        Для лёгкого начала убедитесь, что просмотрели короткую [Видеоинструкцию](https://gyazo.com/237227ba3775e143463b31bdb1b06f50)!
                          """);

        helper.page("book_of_calling");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        Вдруг потеряете Книгу вызова, сможете создать новую.
                        Нажмите [#](%1$s)Shift + ПКМ[#]() по духу при помощи созданной книги, чтобы её определить.
                        """.formatted(COLOR_PURPLE));

        helper.entry("trade_spirits");
        this.lang("ru_ru").add(helper.entryName(), "Духи-торговцы");

        helper.page("intro");
        this.lang("ru_ru").add(helper.pageTitle(), "Духи-торговцы");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        Духи-торговцы подбирают необходимые предметы и кидают результаты обмена на землю. Дух активно меняет предметы только в случае, если вокруг него появляются пурпурные частицы.
                        \\
                        \\
                        **Если Вы не видите частиц**, убедитесь, что дали должный предмет и количество.
                           """);

        helper.page("intro2");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        Большинство духов-торговцев испытывают чрезмерный распад сущности и немедленно уходят в [#](%1$s)Иное Место[#]().
                           """.formatted(COLOR_PURPLE));

        helper.entry("summon_otherworld_sapling_trader");
        this.lang("ru_ru").add(helper.entryName(), "Вызов торговца потусторонними саженцами");

        helper.page("intro");
        this.lang("ru_ru").add(helper.pageTitle(), "Торговец потусторонними саженцами");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        Потусторонние деревья, выращенные из естественных Потусторонних саженцев, могут быть собраны во время действия [#](%1$s)Третьего глаза[#](). Для облегчения жизни, Торговец потусторонними саженцами будет обменивать столь естественные саженцы на стабильную разновидность, которая может быть собрана кем-угодно, а при сборе сбрасывать такие же стабильные саженцы.
                           """.formatted(COLOR_PURPLE));

        helper.page("trade");
        //текст отсутствует

        helper.page("ritual");
        //текст отсутствует

        helper.entry("summon_otherstone_trader");
        this.lang("ru_ru").add(helper.entryName(), "Вызов торговца потусторонним камнем");

        helper.page("intro");
        this.lang("ru_ru").add(helper.pageTitle(), "Торговец потусторонним камнем");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        Торговцы потусторонним камнем позволяют получить больше [](item://occultism:otherstone) вместо того, чтобы использовать [](item://occultism:spirit_fire). В первую очередь это выгодно, если хотите использовать Потусторонний камень в качестве строительного материала.
                           """);

        helper.page("trade");
        //текст отсутствует

        helper.page("ritual");
        //текст отсутствует

        helper.entry("summon_wild_parrot");
        this.lang("ru_ru").add(helper.entryName(), "Вызов дикого попугая");

        helper.page("entity");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        **Предоставляет**: Приручаемого попугая
                          """);

        helper.page("ritual");
        //текст отсутствует

        helper.page("description");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        В этом ритуале [#](%1$s)Фолиот[#]() вызывается **в качестве неприручённого духа**.
                        \\
                        \\
                        Убийство [#](%1$s)Курицы[#]() и жертвование красителями предназначены для того, чтобы склонить Фолиота принять облик попугая. Хотя [#](%1$s)Фолиот[#]() не находится среди умнейших духов, порой он недопонимает указания ...
                          """.formatted(COLOR_PURPLE));

        helper.page("description2");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        *При таком подходе, если появится [#](%1$s)курица[#](), это не ошибка, просто Вам не повезло!*
                           """.formatted(COLOR_PURPLE));

        helper.entry("summon_wild_otherworld_bird");
        this.lang("ru_ru").add(helper.entryName(), "Вызов дикого дрикрыла");

        helper.page("entity");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        **Предоставляет**: Приручаемого дрикрыла
                          """);

        helper.page("ritual");
        //текст отсутствует

        helper.page("description");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        За дополнительными сведениями, обратитесь к записи [Дрикрыл-фамильяр](entry://familiar_rituals/familiar_otherworld_bird).
                          """);

        helper.entry("weather_magic");
        this.lang("ru_ru").add(helper.entryName(), "Магия погоды");

        helper.page("intro");
        this.lang("ru_ru").add(helper.pageTitle(), "Магия погоды");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        Магия погоды особенно полезна для фермеров и других людей, в зависимости от конкретной погоды. Вызывайте духов, чтобы изменять погоду. Для разных видов изменения погоды нужны определённые духи.
                        \\
                        \\
                        Духи погоды изменяют лишь только погоду, а затем исчезают.
                           """);

        helper.page("ritual_clear");
        //текст отсутствует

        helper.page("ritual_rain");
        //текст отсутствует

        helper.page("ritual_thunder");
        //текст отсутствует

        helper.entry("time_magic");
        this.lang("ru_ru").add(helper.entryName(), "Магия времени");

        helper.page("intro");
        this.lang("ru_ru").add(helper.pageTitle(), "Магия времени");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        Магия времени ограничена возможностями: она не может перемещать заклинателя во времени, скорее всего, лишь позволяет изменять время суток. Это особенно полезно для ритуалов или других задач, требующих сугубо дня и ночи.
                        \\
                        \\
                        Духи времени изменяют лишь только время, а затем исчезают.
                           """);

        helper.page("ritual_day");
        //текст отсутствует

        helper.page("ritual_night");
        //текст отсутствует

        helper.entry("wither_skull");
        this.lang("ru_ru").add(helper.entryName(), "Череп визер-скелета");

        helper.page("intro");
        this.lang("ru_ru").add(helper.pageTitle(), "Череп визер-скелета");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        Помимо опасного путешествия в Незер, существует ещё 1 способ получить эти черепа. Легендарная [#](%1$s)Дикая Охота[#]() состоит из [#](%1$s)Могущественных духов[#](), принявших облик Визер-скелетов. Хотя вызов Дикой Охоты чрезвычайно опасен, — это наибыстрейший способ получения черепов визер-скелетов.
                           """.formatted(COLOR_PURPLE));

        helper.page("ritual");
        //текст отсутствует

        helper.entry("afrit_essence");
        this.lang("ru_ru").add(helper.entryName(), "Сущность Африта");

        helper.page("intro");
        this.lang("ru_ru").add(helper.pageTitle(), "Сущность Африта");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        Широко используемая в виде красного мела [](item://occultism:afrit_essence), требуемая для безопасного вызова более могущественных духов. Для получения сущности [#](%1$s)Африта[#]() в этой плоскости нужно вызвать несвязанного Африта и убить. Учтите, что это не простая затея; а несвязанный дух представляет большую угрозу для каждого поблизости.
                           """.formatted(COLOR_PURPLE));

        helper.page("ritual");
        //текст отсутствует
    }

    private void addPossessionRitualsCategory(BookContextHelper helper) {
        helper.category("possession_rituals");
        this.lang("ru_ru").add(helper.categoryName(), "Ритуалы завладения");

        helper.entry("return_to_rituals");
        this.lang("ru_ru").add(helper.entryName(), "Вернуться в категорию Ритуалы");

        helper.entry("overview");
        this.lang("ru_ru").add(helper.entryName(), "Ритуалы завладения");

        helper.page("intro");
        this.lang("ru_ru").add(helper.pageTitle(), "Ритуалы завладения");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        Ритуалы завладения связывают духов с живыми существами, что в значительной степени наделяет вызывателя контролем над одержимым.
                        \\
                        \\
                        Фактически, эти ритуалы используются для получения редких предметов, избегая опасных мест.
                           """);

        helper.entry("possess_enderman");
        this.lang("ru_ru").add(helper.entryName(), "Одержимый эндермен");

        helper.page("entity");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        **Добыча**: 1-3х [](item://minecraft:ender_pearl)
                                """);

        helper.page("ritual");
        //текст отсутствует

        helper.page("description");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        В этом ритуале появляется [#](%1$s)Эндермен[#](), используя жизненную энергию [#](%1$s)Свиньи[#]() , и сразу овладевается путём вызова [#](%1$s)Джинна[#](). При убийстве [#](%1$s)Одержимый эндермен[#]() всегда выдаёт минимум 1 [](item://minecraft:ender_pearl)
                                """.formatted(COLOR_PURPLE));

        helper.entry("possess_endermite");
        this.lang("ru_ru").add(helper.entryName(), "Одержимый эндермит");

        helper.page("entity");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        **Добыча**: 1-2х [](item://minecraft:end_stone)
                                """);

        helper.page("ritual");
        //текст отсутствует

        helper.page("description");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        В этом ритуале появляется [#](%1$s)Эндермит[#]() при обмане. Камни и земля символизируют окружающую местность, затем бросается яйцо для имитации использования Эндер-жемчуга. Когда появляется эндермит вызванный [#](%1$s)Фолиот[#]() сразу им овладевает, посещает [#](%1$s)Энд[#]() и возвращается. При убийстве [#](%1$s)Одержимый эндермит[#]() всегда выдаёт минимум 1 [](item://minecraft:end_stone)
                                """.formatted(COLOR_PURPLE));

        helper.entry("possess_ghast");
        //moved to OccultismBookProvider#makePossessGhastEntry

        helper.entry("possess_skeleton");
        this.lang("ru_ru").add(helper.entryName(), "Одержимый скелет");

        helper.page("entity");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        **Добыча**: 1x [](item://minecraft:skeleton_skull)
                                """);

        helper.page("ritual");
        //текст отсутствует

        helper.page("description");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        В этом ритуале появляется [#](%1$s)Скелет[#](), используя жизненную энергию [#](%1$s)Курицы[#](), и сразу овладевается [#](%1$s)Фолиотом[#](). При убийстве [#](%1$s)Одержимый скелет[#]() будет устойчивым к дневному свету и всегда выдаёт минимум 1 [](item://minecraft:skeleton_skull).
                                """.formatted(COLOR_PURPLE));
    }

    private void addCraftingRitualsCategory(BookContextHelper helper) {
        helper.category("crafting_rituals");
        this.lang("ru_ru").add(helper.categoryName(), "Ритуалы связывания");

        helper.entry("return_to_rituals");
        this.lang("ru_ru").add(helper.entryName(), "Вернуться к категории Ритуалы");

        helper.entry("overview");
        this.lang("ru_ru").add(helper.entryName(), "Ритуалы связывания");

        helper.page("intro");
        this.lang("ru_ru").add(helper.pageTitle(), "Ритуалы связывания");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        Ритуалы связывания позволяют духам проникать в предметы, где их силы используются для достижения определённой цели. Созданные предметы могут действовать как простые чары, наделяющие силы, или выполнять сложные задачи для содействия вызывателю.
                           """);

        helper.entry("craft_storage_system");
        this.lang("ru_ru").add(helper.entryName(), "Магическое хранилище");

        helper.page("spotlight");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        Следующие записи отображают только ритуалы, относящиеся к системе магического хранения. Для получения полной пошаговой инструкции по созданию системы хранения, обратитесь к категории [Магическое хранилище](category://storage) category.
                           """.formatted(COLOR_PURPLE));

        helper.entry("craft_dimensional_matrix");
        this.lang("ru_ru").add(helper.entryName(), "Пространственная матрица");

        helper.page("spotlight");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        Пространственная матрица — мостик в небольшое измерение, что используется в качестве хранения предметов. [#](%1$s)Джинн[#](), связанный с матрицей — поддерживает стабильность измерения. Как правило, поддерживается другими духами внутри стабилизаторов хранилища для увеличения размера пространства.
                           """.formatted(COLOR_PURPLE));

        helper.page("ritual");
        //текст отсутствует

        helper.entry("craft_dimensional_mineshaft");
        this.lang("ru_ru").add(helper.entryName(), "Пространственная шахта");

        helper.page("spotlight");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        Пространственная шахта заселяет [#](%1$s)Джинна[#]()открывающего доступ к стабильному соединению в незаселённом измерении, идеально подходящем для добычи ресурсов. Хотя портал слишком мал для переноса людей, другие же духи могут использовать его для входа в измерение и выносить оттуда ресурсы.
                           """.formatted(COLOR_PURPLE));

        helper.page("ritual");
        //текст отсутствует

        helper.page("description");
        this.lang("ru_ru").add(helper.pageTitle(), "Функционирование");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        Пространственная шахта будет отбрасывать все предметы, неспособные к хранению, в связи с чем важно регулярно опустошать шахту либо вручную при помощи воронок, либо при помощи Духа-Транспортировщика. Духи в лампе могут быть **размещены** сверху, а другие стороны можно использовать для **извлечения** предметов.
                           """.formatted(COLOR_PURPLE));


        helper.entry("craft_infused_pickaxe");
        this.lang("ru_ru").add(helper.entryName(), "Наполненная кирка");

        helper.page("spotlight");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        Потусторонние руды, как правило, можно добыть только инструментами из потустороннего металла. [](item://occultism:infused_pickaxe) является временным решением этой классической дилеммы. Хрупкий кристалл, настроенный на духа содержит [#](%1$s)Джинна[#](), который позволяет собирать руды, хотя её прочность крайне низкая. Наиболее прочная версия является [Кирка из айзния](entry://getting_started/iesnium_pickaxe).
                           """.formatted(COLOR_PURPLE));

        helper.page("ritual");
        //текст отсутствует

        helper.entry("craft_otherworld_goggles");
        this.lang("ru_ru").add(helper.entryName(), "Создание потусторонних очков");

        helper.page("goggles_spotlight");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        [](item://occultism:otherworld_goggles) наделяют владельца постоянным [#](%1$s)Третьим глазом[#](), позволяя видеть даже блоки, сокрытые от тех, кто отведал [Блаженство демона](entry://occultism:dictionary_of_spirits/getting_started/demons_dream).
                        \\
                        \\
                        Это изящно решает общую проблему вызывателей, находящихся под наркотическим помутнением; а это может привести к всяческим расстройствам.
                        """.formatted(COLOR_PURPLE));

        helper.page("goggles_more");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        Впрочем, эти очки не наделяют способностью собирать материалы из Иного Места. Иными словами, при ношении этих очков следует использовать [Наполненную кирку](entry://getting_started/infused_pickaxe) или ещё лучше [Кирку айзния](entry://getting_started/iesnium_pickaxe), чтобы разрушать блоки и получать их разновидности потусторонних материалов.
                        """.formatted(COLOR_PURPLE));

        helper.page("lenses_spotlight");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        [#](%1$s)Фолиот[#]() использует Потусторонние очки, связанные с линзами. Фолиот передаёт владельцу способность видеть более высокие плоскости, благодаря этому давая возможность видеть Потусторонние материалы.
                         """.formatted(COLOR_PURPLE));

        helper.page("lenses_more");
        this.lang("ru_ru").add(helper.pageTitle(), "Создание линз");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        Вызов духа в линзы, используемый для создания очков, как правило, является одним из первых сложных ритуалов для начинающих вызывателей. Это говорит о том, что их навыки усовершенствуются за рамки основных знаний.
                        """.formatted(COLOR_PURPLE));

        helper.page("lenses_recipe");
        //текст отсутствует

        helper.page("ritual");
        //текст отсутствует

        helper.page("goggles_recipe");
        //текст отсутствует

        helper.entry("craft_storage_controller_base");
        this.lang("ru_ru").add(helper.entryName(), "Основа актуатора хранилища");

        helper.page("spotlight");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        Основа актуатора хранилища заключает [#](%1$s)Фолиота[#](), отвечающего за взаимодействие с предметами в матрице пространственного хранилища.
                        """.formatted(COLOR_PURPLE));

        helper.page("ritual");
        //текст отсутствует

        helper.entry("craft_stabilizer_tier1");
        this.lang("ru_ru").add(helper.entryName(), "Стабилизатор хранилища 1-го уровня");

        helper.page("spotlight");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        Этот простой стабилизатор хранилища, заселённый [#](%1$s)Фолиотом[#](), поддерживает пространственную матрицу в стабильном состоянии, таким образом, позволяя хранить больше предметов.
                        \\
                        \\
                        По умолчанию, каждый Стабилизатор 1-го уровня добавляет **64** типа предметов, а ёмкость хранилища до 512000 предметов.
                        """.formatted(COLOR_PURPLE));

        helper.page("ritual");
        //текст отсутствует

        helper.entry("craft_stabilizer_tier2");
        this.lang("ru_ru").add(helper.entryName(), "Стабилизатор хранилища 2-го уровня");

        helper.page("spotlight");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        Этот улучшенный стабилизатор, заселённый [#](%1$s)Джинном[#](), поддерживает пространственную матрицу в стабильном состоянии, таким образом, позволяя хранить ещё больше предметов.
                        \\
                        \\
                        По умолчанию, каждый Стабилизатор 2-го уровня добавляет **128** типа предметов, а ёмкость хранилища до 1024000 предметов.
                        """.formatted(COLOR_PURPLE));

        helper.page("ritual");
        //текст отсутствует

        helper.entry("craft_stabilizer_tier3");
        this.lang("ru_ru").add(helper.entryName(), "Стабилизатор хранилища 3-го уровня");

        helper.page("spotlight");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        Этот продвинутый стабилизатор, заселённый [#](%1$s)Афритом[#](), поддерживает пространственную матрицу в стабильном состоянии, таким образом позволяет хранить ещё больше предметов.
                        \\
                        \\
                        По умолчанию, каждый Стабилизатор 3-го уровня добавляет **256** типа предметов, а ёмкость хранилища до 2048000 предметов.
                        """.formatted(COLOR_PURPLE));

        helper.page("ritual");
        //текст отсутствует

        helper.entry("craft_stabilizer_tier4");
        this.lang("ru_ru").add(helper.entryName(), "Стабилизатор хранилища 4-го уровня");

        helper.page("spotlight");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        Этот высокоуровневый стабилизатор, заселённый [#](%1$s)Маридом[#](), поддерживает пространственную матрицу в стабильном состоянии, таким образом, позволяя хранить ещё больше предметов.
                        \\
                        \\
                        По умолчанию, каждый Стабилизатор 4-го уровня добавляет **512** типа предметов, а ёмкость хранилища до 4098000 предметов.
                        """.formatted(COLOR_PURPLE));

        helper.page("ritual");
        //текст отсутствует

        helper.entry("craft_stable_wormhole");
        this.lang("ru_ru").add(helper.entryName(), "Стабильная червоточина");

        helper.page("spotlight");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        Стабильная червоточина позволяет получит доступ к пространственной матрице из удалённого места назначения.
                        \\
                        \\
                        Нажмите Shift + ПКМ по [](item://occultism:storage_controller), чтобы её связать, затем поставьте червоточину в мир, чтобы использовать её в качестве удалённой точки доступа.
                         """.formatted(COLOR_PURPLE));

        helper.page("ritual");
        //текст отсутствует

        helper.entry("craft_storage_remote");
        this.lang("ru_ru").add(helper.entryName(), "Средство доступа удалённого хранилища");

        helper.page("spotlight");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        [](item://occultism:storage_remote) может быть связано с [](item://occultism:storage_controller) при нажатии Shift + ПКМ. [#](%1$s)Джинн[#](), связанный к средству доступа, таким образом сможет получить доступ к предметам из Актуатора даже из других измерений.
                        """.formatted(COLOR_PURPLE));

        helper.page("ritual");
        //текст отсутствует

        helper.entry("craft_foliot_miner");
        this.lang("ru_ru").add(helper.entryName(), "Горняк-Фолиот");

        helper.page("intro");
        this.lang("ru_ru").add(helper.pageTitle(), "Горняк-Фолиот");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        Духи-Горняки используют [](item://occultism:dimensional_mineshaft) для получения ресурсов из других измерений. Они вызываются и связываются в магические лампы, из которых могут выходить только через шахты. Магическая лампа постепенно разрушается, а когда она разрушится, дух освобождится обратно в [#](%1$s)Иное Место[#]().
                        """.formatted(COLOR_PURPLE));

        helper.page("magic_lamp");
        this.lang("ru_ru").add(helper.pageTitle(), "Магическая лампа");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        Для вызова Духов-Горняков сперва Вам нужно создать [Магическую лампу](entry://getting_started/magic_lamps), чтобы владеть ими. Главным ингредиентом является [Айзний](entry://getting_started/iesnium).
                        """.formatted(COLOR_PURPLE));

        helper.page("magic_lamp_recipe");
        //текст отсутствует

        helper.page("spotlight");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        Горняк-[#](%1$s)Фолиот[#]() добывает блоки без особой цели и возвращает всё, что находит. Процесс добычи довольно медленный, поскольку Фолиот тратит лишь небольшое количество энергии, постепенно вредя лампе, в которой он содержится.
                        """.formatted(COLOR_PURPLE));

        helper.page("ritual");
        //текст отсутствует

        helper.entry("craft_djinni_miner");
        this.lang("ru_ru").add(helper.entryName(), "Горняк-Джинн");

        helper.page("spotlight");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        Горняк-[#](%1$s)Джинн[#]() добывает именно руду. Отбраковывая другие блоки, он способен быстрее и эффективнее добывать руды. Чем больше сила Джинна, тем быстрее вредит магической лампе.
                        """.formatted(COLOR_PURPLE));

        helper.page("ritual");
        //текст отсутствует

        helper.entry("craft_afrit_miner");
        this.lang("ru_ru").add(helper.entryName(), "Горняк-Африт");

        helper.page("spotlight");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        Горняк-[#](%1$s)Африт[#]() добывает руду, как и Горняки-Джинны также добывает глубинносланцевую руду. Этот горняк быстрее и эффективнее Джинна, а значит ещё медленнее вредит магической лампе.
                        """.formatted(COLOR_PURPLE));

        helper.page("ritual");
        //текст отсутствует

        helper.entry("craft_marid_miner");
        this.lang("ru_ru").add(helper.entryName(), "Горняк-Марид");

        helper.page("spotlight");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        Горняк-[#](%1$s)Марид[#]()— самый могущественный Дух-Горняк. У него невероятная скорость добычи, и лучшее сохранение целостности магической лампы. В отличие от других Духов-Горняков он, в свою очередь, способен добывать редчайшие руды, такие как [](item://minecraft:ancient_debris) и [](item://occultism:iesnium_ore).
                        """.formatted(COLOR_PURPLE));

        helper.page("ritual");
        //текст отсутствует

        helper.entry("craft_satchel");
        this.lang("ru_ru").add(helper.entryName(), "Изумительно большая сумка");

        helper.page("spotlight");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        [#](%1$s)Фолиот[#](), связанный с сумкой, заботится о **незначительном** искажении реальности. Это позволяет хранить в ней больше предметов, чем указывают её размеры, и делает её практичным спутником путешественника.
                        """.formatted(COLOR_PURPLE));

        helper.page("ritual");
        //текст отсутствует

        helper.entry("craft_soul_gem");
        this.lang("ru_ru").add(helper.entryName(), "Самоцвет душ");

        helper.page("spotlight");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        Самоцветы душ — алмазы, что вделываются в драгоценные металлы, которые затем вселяются [#](%1$s)Джинном[#](). Дух создаёт небольшое измерение, позволяющее временно удерживать живых существ. Однако существа огромной силы или размера не могут удерживаться внутри самоцветов.
                        """.formatted(COLOR_PURPLE));

        helper.page("usage");
        this.lang("ru_ru").add(helper.pageTitle(), "Использование");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        Для поимки существа, нажмите по нему [#](%1$s)ПКМ[#]() при помощи самоцвета душ. \\
                        Снова нажмите [#](%1$s)ПКМ[#](), чтобы выпустить существо.
                        \\
                        \\
                        Босса невозможно поймать.
                               """.formatted(COLOR_PURPLE));


        helper.page("ritual");
        //текст отсутствует

        helper.entry("craft_familiar_ring");
        this.lang("ru_ru").add(helper.entryName(), "Кольцо фамильяра");

        helper.page("spotlight");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        Кольцо фамильяра состоит из [](item://occultism:soul_gem), содержащего в себе [#](%1$s)Джинна[#](), заключённого в кольцо. [#](%1$s)Джинн[#]() в кольце позволяет фамильяру, заключённому в самоцвет душ наделять эффектами владельца."
                        """.formatted(COLOR_PURPLE));

        helper.page("usage");
        this.lang("ru_ru").add(helper.pageTitle(), "Использование");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        Чтобы воспользоваться [](item://occultism:familiar_ring), поймайте вызванного (и приручённого) фамильяра, нажав по нему [#](%1$s)ПКМ[#](), а затем наденьте кольцо в качестве [#](%1$s)Аксессуара[#](), чтобы использовать эффекты, оказываемые фамильяром.
                        \\
                        \\
                        Когда фамильяр выпущен из кольца, дух распознает человека, освободившего его, как своего нового хозяина.
                        """.formatted(COLOR_PURPLE));

        helper.page("ritual");
        //текст отсутствует
        helper.entry("craft_wild_trim");
        this.lang("ru_ru").add(helper.entryName(), "Отделка «Дебри»");

        helper.page("spotlight");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        В отличие от ритуалов, создание [](item://minecraft:wild_armor_trim_smithing_template) является услугой, оказываемой Маридом, что не связан к последнему объекту. Вы жертвуете предметами, а Марид использует свои силы, чтобы сковать для Вас этот предмет.
                        """.formatted(COLOR_PURPLE));

        helper.page("ritual");
    }

    private void addFamiliarRitualsCategory(BookContextHelper helper) {
        helper.category("familiar_rituals");

        helper.entry("return_to_rituals");
        this.lang("ru_ru").add(helper.entryName(), "Вернуться к категории Ритуалы");

        helper.entry("overview");
        this.lang("ru_ru").add(helper.entryName(), "Ритуалы для фамильяров");

        helper.page("intro");
        this.lang("ru_ru").add(helper.pageTitle(), "Ритуалы для фамильяров");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        Ритуалы для фамильяров вызывают духов для непосредственной помощи вызывателю. Духи, как правило, обитают в теле животного, что позволяет им защищать сущность от распада. Фамильяры предоставляют усиления, но могут, в свою очередь, активно защищать вызывателя.
                                """.formatted(COLOR_PURPLE));

        helper.page("ring");
        this.lang("ru_ru").add(helper.pageTitle(), "Экипировка фамильярами");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        Предприимчивые вызыватели нашли способ связывать фамильяров в драгоценные камни, что пассивно накладывают свои усиления. [Кольцо фамильяра](entry://crafting_rituals/craft_familiar_ring).
                                """.formatted(COLOR_PURPLE));

        helper.page("trading");
        this.lang("ru_ru").add(helper.pageTitle(), "Экипировка фамильярами");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        "Фамильяры свободно меняются, когда находятся в [Кольце для фамильяра](entry://crafting_rituals/craft_familiar_ring).
                        \\
                        \\
                        Дух при выпускании признает человека, выпустившего его, как своего нового хозяина.
                                 """.formatted(COLOR_PURPLE));

        helper.entry("familiar_bat");
        this.lang("ru_ru").add(helper.entryName(), "Летучая мышь-фамильяр");

        helper.page("entity");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        **Предоставляет**: [#](%1$s)Ночное зрение[#]().
                               """.formatted(COLOR_PURPLE));

        helper.page("ritual");
        //текст отсутствует

        helper.page("description");
        this.lang("ru_ru").add(helper.pageTitle(), "Описание");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        **Обновление поведения**:\\
                        будучи улучшена Кузнецом-фамильяром, Летучая мышь-фамильяр будет наделять своего владельца эффектом похищения жизни.
                           """.formatted(COLOR_PURPLE));

        helper.entry("familiar_beaver");
        this.lang("ru_ru").add(helper.entryName(), "Бобёр-фамильяр");

        helper.page("entity");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        **Предоставляет**: [#](%1$s)Повышенная скорость рубки древесины[#]().
                               """.formatted(COLOR_PURPLE));

        helper.page("ritual");
        //текст отсутствует

        helper.page("description");
        this.lang("ru_ru").add(helper.pageTitle(), "Описание");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        Бобёр-фамильяр будет срубать близлежащие деревья, когда они вырастут из саженца в дерево. Он справляется только с небольшими деревьями.
                        \\
                        \\
                        **Обновление поведения**:\\
                        При нажатии по нему пустой рукой даёт бесплатные лакомства.
                           """.formatted(COLOR_PURPLE));

        helper.entry("familiar_beholder");
        this.lang("ru_ru").add(helper.entryName(), "Созерцатель-фамильяр");

        helper.page("entity");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        **Предоставляет**: [#](%1$s)Подсвечивание врагов[#](), [#](%1$s)Стреляет **ДОЛБАНЫМИ ЛАЗЕРАМИ**[#]().
                               """.formatted(COLOR_PURPLE));

        helper.page("ritual");
        //текст отсутствует

        helper.page("description");
        this.lang("ru_ru").add(helper.pageTitle(), "Описание");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        Созерцатель-фамильяр подсвечивает близлежащих существ эффектом свечения и стреляет лазерными лучами во врагов. **Питается** (слабыми) **маленькими Шуб-Ниггуратами** для получения временного урона и скорости.
                        \\
                        \\
                        **Обновление поведения**:\\
                        будучи улучшен Кузнецом-фамильяром, наделяет своего владельца устойчивостью к слепоте.
                           """.formatted(COLOR_PURPLE));

        helper.entry("familiar_blacksmith");
        this.lang("ru_ru").add(helper.entryName(), "Кузнец-фамильяр");

        helper.page("entity");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        **Предоставляет**: [#](%1$s)Починка снаряжения во время добычи[#](), [#](%1$s)улучшает других фамильяров[#]().
                               """.formatted(COLOR_PURPLE));

        helper.page("ritual");
        //текст отсутствует

        helper.page("description");
        this.lang("ru_ru").add(helper.pageTitle(), "Описание");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        Каждый раз, когда игрок подбирает камень, существует вероятность, что Кузнец-фамильяр немного починит его снаряжение.
                        \\
                        \\
                        **Обновление поведения**: \\
                        не может быть улучшен, но улучшает других фамильяров.
                           """.formatted(COLOR_PURPLE));

        helper.page("description2");
        this.lang("ru_ru").add(helper.pageTitle(), "Улучшение фамильяров");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        Чтобы улучшать других фамильяров, кузнецу нужно выдать железные слитки или блоки при нажатии по нему [#](%1$s)ПКМ[#]().
                        \\
                        \\
                        Улучшенные фамильяры наделяют дополнительными эффектами.
                           """.formatted(COLOR_PURPLE));

        helper.entry("familiar_chimera");
        this.lang("ru_ru").add(helper.entryName(), "Химера-фамильяр");

        helper.page("entity");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        **Предоставляет**: [#](%1$s)Ездового зверя[#]().
                               """.formatted(COLOR_PURPLE));

        helper.page("ritual");
        //текст отсутствует

        helper.page("description");
        this.lang("ru_ru").add(helper.pageTitle(), "Описание");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        Чтобы вырастить химеру, её надо кормить (любым) мясом. Когда она вырастет, она будет наносить больший урон и быстрее передвигаться. Когда она достаточно вырастит, игроки смогут её оседлать. Если её накормить [](item://minecraft:golden_apple), [#](%1$s)коза[#]() отцепится и станет отдельным фамильяром.
                        \\
                        \\
                        Отцеплённую Козу-фамильяра можно использовать для получения [Шуб-Ниггурата-фамильяра](entry://familiar_rituals/familiar_shub_niggurath).
                           """.formatted(COLOR_PURPLE));

        helper.page("description2");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        **Обновление поведения**:\\
                        будучи улучшена Кузнецом-фамильяром, коза получит сигнальный колокол. При атаке фамильяра зазвенит колокол и привлечёт врагов в пределах большого радиуса.
                           """.formatted(COLOR_PURPLE));

        helper.entry("familiar_cthulhu");
        this.lang("ru_ru").add(helper.entryName(), "Ктулху-фамильяр");

        helper.page("entity");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        **Предоставляет**: [#](%1$s)Водное дыхание[#](), [#](%1$s)общую свежесть[#]().
                               """.formatted(COLOR_PURPLE));

        helper.page("ritual");
        //текст отсутствует

        helper.page("description");
        this.lang("ru_ru").add(helper.pageTitle(), "Описание");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        **Обновление поведения**:\\
                        будучи улучшена Кузнецом-фамильяром, он будет служить передвижным источником света.
                           """.formatted(COLOR_PURPLE));

        helper.entry("familiar_shub_niggurath");
        this.lang("ru_ru").add(helper.entryName(), "Шуб-Ниггурат-фамильяр");

        helper.page("entity");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        **Предоставляет**: [#](%1$s)Порождения малых версий самого себя, чтобы защищать Вас[#]().
                                   """.formatted(COLOR_PURPLE));

        helper.page("ritual");
        this.lang("ru_ru").add(helper.pageTitle(), "Ритуал");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        [#](%1$s)Шуб-Ниггурат[#]() не вызывается напрямую. Сперва вызовите [Химеру-фамильяра](entry://familiar_rituals/familiar_chimera) и накормите её [](item://minecraft:golden_apple), чтобы отчленить [#](%1$s)Козу[#](). Приведите козу в [#](%1$s)Лесной биом[#](). Затем нажмите по козе [любым Чёрным красителем](item://minecraft:black_dye), [](item://minecraft:flint) и [](item://minecraft:ender_eye), чтобы вызвать [#](%1$s)Шуб-Ниггурата[#]().
                           """.formatted(COLOR_PURPLE));

        helper.page("description");
        this.lang("ru_ru").add(helper.pageTitle(), "Описание");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        **Обновление поведения**:\\
                        будучи улучшена Кузнецом-фамильяром, коза получит сигнальный колокол. При атаке фамильяра зазвенит колокол и **привлечёт врагов** в пределах большого радиуса.
                           """.formatted(COLOR_PURPLE));

        helper.entry("familiar_deer");
        this.lang("ru_ru").add(helper.entryName(), "Олень-фамильяр");

        helper.page("entity");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        **Предоставляет**: [#](%1$s)Прыгучесть[#]().
                                   """.formatted(COLOR_PURPLE));

        helper.page("ritual");
        //текст отсутствует

        helper.page("description");
        this.lang("ru_ru").add(helper.pageTitle(), "Описание");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        **Обновление поведения**:\\
                        будучи улучшен Кузнецом-фамильяром, он будет атаковать близлежащих врагов молотом. Да, **молотом**.
                           """.formatted(COLOR_PURPLE));

        helper.entry("familiar_devil");
        this.lang("ru_ru").add(helper.entryName(), "Дьявол-фамильяр");

        helper.page("entity");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        **Предоставляет**: [#](%1$s)Огнестойкость[#](), [#](%1$s)атакует врагов[#]().
                                   """.formatted(COLOR_PURPLE));

        helper.page("ritual");
        //текст отсутствует

        helper.page("description");
        this.lang("ru_ru").add(helper.pageTitle(), "Описание");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        **Обновление поведения**:\\
                        не может быть улучшен Кузнецом-фамильяром.
                           """.formatted(COLOR_PURPLE));

        helper.entry("familiar_dragon");
        this.lang("ru_ru").add(helper.entryName(), "Дракон-фамильяр");

        helper.page("entity");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        **Предоставляет**: [#](%1$s)Повышенный опыт[#](), любит палочки.
                                   """.formatted(COLOR_PURPLE));

        helper.page("ritual");
        //текст отсутствует

        helper.page("description");
        this.lang("ru_ru").add(helper.pageTitle(), "Описание");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        Алчные фамильяры могут оседлать Дракона-фамильяра, и помимо этого передавать ему эффекты своей алчности.
                        \\
                        \\
                        **Обновление поведения**:\\
                        будучи улучшен Кузнецом-фамильяром, он будет кидать мечи в близлежащих врагов.
                           """.formatted(COLOR_PURPLE));


        helper.entry("familiar_fairy");
        this.lang("ru_ru").add(helper.entryName(), "Фея-фамильяр");

        helper.page("entity");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        **Предоставляет**: [#](%1$s)Помогает фамильярам[#](), [#](%1$s)не даёт фамильяру умирать[#](), [#](%1$s)истощает жизненную силу врага[#]().
                                   """.formatted(COLOR_PURPLE));

        helper.page("ritual");
        //текст отсутствует

        helper.page("description");
        this.lang("ru_ru").add(helper.pageTitle(), "Описание");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        Фея-фамильяр **предотвращает смерть других фамильяров** (с восстановлением), выручает других фамильяров **благоприятными эффектами** и **истощает жизненную силу врагов** для помощи своего хозяина.
                        \\
                        \\
                        **Обновление поведения**:\\
                        не может быть улучшен Кузнецом-фамильяром.
                           """.formatted(COLOR_PURPLE));

        helper.entry("familiar_greedy");
        this.lang("ru_ru").add(helper.entryName(), "Алчный-фамильяр");

        helper.page("entity");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        **Предоставляет**: [#](%1$s)подбирает предметы[#](), [#](%1$s)повышанная дальность подбирания[#]().
                                   """.formatted(COLOR_PURPLE));
        helper.page("ritual");
        //текст отсутствует

        helper.page("description");
        this.lang("ru_ru").add(helper.pageTitle(), "Описание");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        Алчный фамильяр является Фолиотом, который подбирает близлежащие предметы своему хозяину. Если фамильяра поймать в кольцо, он повысит дальность подбирания своему хозяину.
                        \\
                        \\
                        **Обновление поведения**:\\
                        будучи улучшен Кузнецом-фамильяром, он сможет находить блоки для своего хозяина. Нажатие по нему [#](%1$s)ПКМ[#]() блоком, чтобы указать, какой именно нужно искать блок.
                           """.formatted(COLOR_PURPLE));

        helper.entry("familiar_guardian");
        this.lang("ru_ru").add(helper.entryName(), "Страж-фамильяр");

        helper.page("entity");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        **Предоставляет**: [#](%1$s)Предотвращает смерть игрока при жизни[#]().
                                   """.formatted(COLOR_PURPLE));

        helper.page("ritual");
        //текст отсутствует

        helper.page("description");
        this.lang("ru_ru").add(helper.pageTitle(), "Описание");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        Страж-фамильяр жертвует одной из своих конечностей всякий раз, когда его хозяин находится на волоске от смерти, и соответственно **предотвращает смерть**. Как только страж умирает, игрок больше не будет защищён. Будучи вызван, страж появится со случайным количеством конечностей, и не существует гарантии, что вызовется полноценный страж.
                           """.formatted(COLOR_PURPLE));

        helper.page("description2");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        **Обновление поведения**:\\
                        будучи улучшен Кузнецом-фамильяром, он вновь приобретёт конечность (можно сделать лишь раз).
                           """.formatted(COLOR_PURPLE));

        helper.entry("familiar_headless");
        this.lang("ru_ru").add(helper.entryName(), "Безголовый человек-крыса-фамильяр");

        helper.page("entity");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        **Предоставляет**: [#](%1$s)Усиление условного урона[#]().
                                   """.formatted(COLOR_PURPLE));

        helper.page("ritual");
        //текст отсутствует

        helper.page("description");
        this.lang("ru_ru").add(helper.pageTitle(), "Описание");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        Безголовый человек-крыса-фамильяр, при убийстве монстров, похищает их головы возле себя. Затем он наделяет своего хозяина усилением урона против этого типа монстра. Если здоровье человека-крысы падает **ниже 50%**, он погибает, но в то же время может быть перестроен своим же хозяином, предоставив ему [](item://minecraft:wheat), [](item://minecraft:stick), [](item://minecraft:hay_block) и [](item://minecraft:pumpkin).
                           """.formatted(COLOR_PURPLE));

        helper.page("description2");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        **Обновление поведения**:\\
                        будучи улучшен Кузнецом-фамильяром, он будет вызывать слабость у близлежащих монстров того же типа, чьи головы он украл.
                           """.formatted(COLOR_PURPLE));


        helper.entry("familiar_mummy");
        this.lang("ru_ru").add(helper.entryName(), "Мумия-фамильяр");

        helper.page("entity");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        **Предоставляет**: [#](%1$s)Сражается с Вашими врагами[#](), [#](%1$s)Эффект уклонения[#]().
                                   """.formatted(COLOR_PURPLE));

        helper.page("ritual");
        //текст отсутствует

        helper.page("description");
        this.lang("ru_ru").add(helper.pageTitle(), "Описание");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        Мумия-фамильяр — мастер боевых искусств и сражается с целью защитить своего хозяина.
                        \\
                        \\
                        **Обновление поведения**:\\
                        будучи улучшен Кузнецом-фамильяром, он будет наносить ещё больше урона.
                            """.formatted(COLOR_PURPLE));

        helper.entry("familiar_otherworld_bird");
        this.lang("ru_ru").add(helper.entryName(), "Дрикрыл-фамильяр");

        helper.page("entity");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        **Предоставляет**: [#](%1$s)Мульти-прыжок[#](), [#](%1$s)прыгучесть[#](), [#](%1$s)плавное падение[#]().
                                   """.formatted(COLOR_PURPLE));

        helper.page("ritual");
        //текст отсутствует

        helper.page("description");
        this.lang("ru_ru").add(helper.pageTitle(), "Описание");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        [#](%1$s)Дрикрылы$[#]() — подкласс [#](%1$s)Джинна[#](), известные своей дружелюбностью по отношению к людям. Они, как правило, принимают облик тёмно-синего и пурпурного цвета попугая. Дрикрылы будут снабжать своего владельца ограниченными возможностями полёта, будучи рядом.
                        \\
                        \\
                        **Обновление поведения**:\\
                        не могут быть улучшены Кузнецом-фамильяром.
                            """.formatted(COLOR_PURPLE));

        helper.page("description2");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        Для получения Попугая или Попугая-фамильяра на предмет жертвоприношения, попробуйте его вызвать при помощи [Ритуал: Дикий попугай](entry://summoning_rituals/summon_wild_parrot) или [Ритуал: Попугай-фамильяр](entry://familiar_rituals/familiar_parrot).
                        \\
                        \\
                        **Совет:** Если Вы используете моды, защищающие питомцев от смерти, используйте Ритуал: Дикий попугай!
                            """.formatted(COLOR_PURPLE));

        helper.entry("familiar_parrot");
        this.lang("ru_ru").add(helper.entryName(), "Попугай-фамильяр");

        helper.page("entity");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        **Предоставляет**: [#](%1$s)Собеседника[#]().
                                   """.formatted(COLOR_PURPLE));

        helper.page("ritual");
        //текст отсутствует


        helper.page("description");
        this.lang("ru_ru").add(helper.pageTitle(), "Описание");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        В этом ритуале [#](%1$s)Фолиот[#]() вызывается **как фамильяр**. Убийство [#](%1$s)Курицы[#]() и жертвование красителей предназначается для того, чтобы склонить [#](%1$s)Фолиота[#]() принять облик попугая.\\
                        Хотя [#](%1$s)Фолиот[#]() не находится среди умнейших духов, порой он недопонимает указания ...
                            """.formatted(COLOR_PURPLE));

        helper.page("description2");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        *При таком подходе, если появится [#](%1$s)курица[#](), это не ошибка, просто Вам не повезло!*
                        \\
                        \\
                        **Обновление поведения**:\\
                        не может быть улучшены Кузнецом-фамильяром.
                           """.formatted(COLOR_PURPLE));
        
		//текст отсутствует
        helper.entry("summon_allay");
        this.lang("ru_ru").add(helper.entryName(), "Очищение Вредины до Тихони");

        helper.page("entity");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        **Предоставляет**: Тихоню.
                          """);

        helper.page("ritual");

        helper.page("description");
        this.lang("ru_ru").add(helper.pageTitle(), "Описание");
        this.lang("ru_ru").add(helper.pageText(),
                """
                       Очистка Вредины до Тихони в ходе процесса воскресения, что раскрывает её истинное имя.
                         """.formatted(COLOR_PURPLE));

    }

    private void addStorageCategory(BookContextHelper helper) {
        helper.category("storage");
        this.lang("ru_ru").add(helper.categoryName(), "Магическое хранилище");

        helper.entry("overview");
        this.lang("ru_ru").add(helper.entryName(), "Магическое хранилище");

        helper.page("intro");
        this.lang("ru_ru").add(helper.pageTitle(), "Магическое хранилище");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        Проблема, известная каждому вызывателю: существует слишком много оккультных принадлежностей, лежащих без дела. Решение простое, но всё же лучшее: Магическое хранилище!
                        \\
                        \\
                        С помощью духов можно получить доступ к пространственным хранилищам, способным создавать почти неограниченное место для хранения.
                        """.formatted(COLOR_PURPLE));

        helper.page("intro2");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        Выполните следующие действия, отображённые в этой категории для получения собственной системы хранения!
                        Действия, относящиеся к хранению в [Ритуалах связывания](category://crafting_rituals/), отображают только ритуалы; при том здесь отображены все требуемые шаги, включая создание.
                        """.formatted(COLOR_PURPLE));

        helper.entry("storage_controller");
        this.lang("ru_ru").add(helper.entryName(), "Актуатор хранилища");

        helper.page("intro");
        this.lang("ru_ru").add(helper.pageTitle(), "Актуатор хранилища");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        В состав [](item://occultism:storage_controller) входят [Пространственная матрица](entry://crafting_rituals/craft_dimensional_matrix), заселённая [#](%1$s)Джинном[#](), что создаёт и управляет пространственным хранилищем и [Базовым компонентом](entry://crafting_rituals/craft_storage_controller_base), заселённым [#](%1$s)Фолиотом[#](), который перемещает предметы из пространственного хранилища туда и обратно.
                        """.formatted(COLOR_PURPLE));

        helper.page("usage");
        this.lang("ru_ru").add(helper.pageTitle(), "Использование");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        После создания [](item://occultism:storage_controller) (обратитесь к следующей странице), поставьте его и нажмите по нему [#](%1$s)ПКМ[#]() пустой рукой. Это откроет его графический интерфейс и с этого момента он будет работать как очень большой шалкеровый ящик.
                        """.formatted(COLOR_PURPLE));

        helper.page("safety");
        this.lang("ru_ru").add(helper.pageTitle(), "Безопасность прежде всего!");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        Разрушение контроллера хранилища сохранит всё содержимое в качестве выброшенного предмета, и Вы ничего не потеряете.
                        То же самое относится к разрушению или перестановке Стабилизаторов хранилища (об этом Вы узнаете позже).
                        \\
                        \\
                        Как и в случае с шалкеровым ящиком, Ваши предметы в безопасности!
                        """.formatted(COLOR_PURPLE));


        helper.page("size");
        this.lang("ru_ru").add(helper.pageTitle(), "Такое огромное хранилище!");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        Контроллер хранилища хранит до **128** различных типов предметов (_Позже узнаете, как увеличить размер_). Кроме того, ограничивается до 256000 предметов в целом. Неважно — 256000 у Вас разных предметов или 256000 одного предмета, или солянка.
                        """.formatted(COLOR_PURPLE));

        helper.page("unique_items");
        this.lang("ru_ru").add(helper.pageTitle(), "Уникальные предметы");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        Предметы с уникальными свойствами (\"NBT-данные\"), напр.: повреждённое или зачарованное снаряжение, займёт 1 тип предмета за каждое различие. Напр.: 2 деревянных меча с 2-мя разным повреждениями занимают 2 типа предмета. 2 деревянных меча с одинаковым уроном (или без), занимают 1 тип предмета.
                        """.formatted(COLOR_PURPLE));

        helper.page("config");
        this.lang("ru_ru").add(helper.pageTitle(), "Конфигурируемость");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        Количество типа предмета и размер хранилища можно настроить в файле конфигурации \"[#](%1$s)occultism-server.toml[#]()\" в папке сохранения мира.
                        """.formatted(COLOR_PURPLE));

        helper.page("mods");
        this.lang("ru_ru").add(helper.pageTitle(), "Взаимодействие с модами");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        Для разных модов контроллер хранилища ведет себя как шалкеровый ящик. Всё, что может взаимодействовать с ванильными сундуками и шалкеровыми ящиками, могут взаимодействовать и с контроллером хранилища.
                        Устройства, которые считаются хранилищем, могут столкнуться с проблемами размеров стека.
                        """.formatted(COLOR_PURPLE));


        helper.page("matrix_ritual");
        //текст отсутствует

        helper.page("base_ritual");
        //текст отсутствует

        helper.page("recipe");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        Это действующий блок, который работает как хранилище. На его создание приложите все силы!
                        Просто поставить [](item://occultism:storage_controller_base) на землю из предыдущего шага — не сработает.
                        """.formatted(COLOR_PURPLE));
        //текст отсутствует


        helper.entry("storage_stabilizer");
        this.lang("ru_ru").add(helper.entryName(), "Расширение хранилища");

        helper.page("spotlight");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        Стабилизаторы хранилища повышают размер хранилища в пространственном хранилище Актуатора хранилища. Чем выше уровень стабилизатора, тем больше получит дополнительного хранилища. Следующие записи покажут, как создать каждый уровень.
                        \\
                        \\
                        """.formatted(COLOR_PURPLE));

        helper.page("upgrade");
        this.lang("ru_ru").add(helper.pageTitle(), "Улучшение");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        Оно **Безопасно уничтожит стабилизатор хранилища**, чтобы его улучшить. Предметы в [Актуаторе хранилища](entry://storage/storage_controller) не будут утеряны или выброшены — Вы не можете добавлять новые предметы, пока не добавите достаточно стабилизаторов хранилища, чтобы снова иметь свободные слоты.
                         """.formatted(COLOR_PURPLE));

        helper.page("build_instructions");
        this.lang("ru_ru").add(helper.pageTitle(), "Инструкция по установке");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        Контроллеры хранилища необходимо направить на [Пространственную матрицу](entry://crafting_rituals/craft_dimensional_matrix), иными словами **1 блок над [Аактуатором хранилища](entry://storage/storage_controller)**.
                        \\
                        \\
                        Их может быть **до 5 блоков** вдали от Пространственной матрицы, и должны быть по прямой линии в зоне видимости. Для возможной очень простой установки, обратитесь к следующей странице.
                        """.formatted(COLOR_PURPLE));


        helper.page("demo");
        this.lang("ru_ru").add(helper.pageTitle(), "Установка стабилизаторов хранилища");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        **Примечание:** Вам не нужны все 4 стабилизатора, даже 1 повысит размер Вашего хранилища.
                        """.formatted(COLOR_PURPLE));
    }

    private void addAdvancements() {
        //"advancements\.occultism\.(.*?)\.title": "(.*)",
        //this.advancementTitle\("\1", "\2"\);
        this.advancementTitle("root", "Occultism");
        this.advancementDescr("root", "Принять духовность!");
        this.advancementTitle("summon_foliot_crusher", "Измельчение руды");
        this.advancementDescr("summon_foliot_crusher", "Хруп, хруп, хруп!");
        this.advancementTitle("familiars", "Occultism: Друзья");
        this.advancementDescr("familiars", "Используйте ритуал для вызова фамильяра.");
        this.advancementDescr("familiar.bat", "Заманите летучую мышь близко к вашей Летучей мыши-фамильяру.");
        this.advancementTitle("familiar.bat", "Каннибализм");
        this.advancementDescr("familiar.capture", "Заключите фамильяра в Кольцо фамильяра.");
        this.advancementTitle("familiar.capture", "Захватить каждого!");
        this.advancementDescr("familiar.cthulhu", "Расстройте Ктулху-фамильяра.");
        this.advancementTitle("familiar.cthulhu", "Вы изверг!");
        this.advancementDescr("familiar.deer", "Наблюдайте, как Бобёр-фамильяр опорожняется демоническими семенами.");
        this.advancementTitle("familiar.deer", "Демоническая какашка");
        this.advancementDescr("familiar.devil", "Прикажите Демону-фамильяру изрыгнуть пламенем.");
        this.advancementTitle("familiar.devil", "Пламя Преисподней");
        this.advancementDescr("familiar.dragon_nugget", "Дайте кусочек золота Дракону-фамильяру.");
        this.advancementTitle("familiar.dragon_nugget", "Договор!");
        this.advancementDescr("familiar.dragon_ride", "Пусть Алчный фамильяр подберёт что-нибудь во время езды на Драконе-фамильяре.");
        this.advancementTitle("familiar.dragon_ride", "В тесной взаимосвязи");
        this.advancementDescr("familiar.greedy", "Пусть Алчный фамильяр подберёт что-нибудь для Вас.");
        this.advancementTitle("familiar.greedy", "Мальчик на посылках");
        this.advancementDescr("familiar.party", "Потанцуйте с фамильяром.");
        this.advancementTitle("familiar.party", "Потанцуем!");
        this.advancementDescr("familiar.rare", "Заполучите редкий вид фамильяра.");
        this.advancementTitle("familiar.rare", "Уникальный друг");
        this.advancementDescr("familiar.root", "Используйте ритуал для вызова фамильяра.");
        this.advancementTitle("familiar.root", "Occultism: Друзья");
        this.advancementDescr("familiar.mans_best_friend", "Погладьте Дракона-фамильяр и поиграйте с ним в игру «Принеси мяч».");
        this.advancementTitle("familiar.mans_best_friend", "Лучший друг человека");
        this.advancementTitle("familiar.blacksmith_upgrade", "В полной боевой экипировке!");
        this.advancementDescr("familiar.blacksmith_upgrade", "Пусть Ваш Кузнец-фамильяр улучшит одного из Ваших фамильяров.");
        this.advancementTitle("familiar.guardian_ultimate_sacrifice", "Бескомпромиссное жертвоприношение");
        this.advancementDescr("familiar.guardian_ultimate_sacrifice", "Пусть Страж-фамильяр умрёт, чтобы спасти самого Вас.");
        this.advancementTitle("familiar.headless_cthulhu_head", "Какой ужас!!");
        this.advancementDescr("familiar.headless_cthulhu_head", "Убейте Ктулху рядом с Безголовым человеком-крысой-фамильяра.");
        this.advancementTitle("familiar.headless_rebuilt", "Сможем его отремонтировать");
        this.advancementDescr("familiar.headless_rebuilt", "\"Отремонтируйте\" своего Безголового человека-красу-фамильяра после его смерти.");
        this.advancementTitle("familiar.chimera_ride", "По коням!");
        this.advancementDescr("familiar.chimera_ride", "Оседлайте Химеру-фамильяра в момент её полного насыщения.");
        this.advancementTitle("familiar.goat_detach", "Демонтаж");
        this.advancementDescr("familiar.goat_detach", "Дайте золотое яблоко Химере-фамильяру.");
        this.advancementTitle("familiar.shub_niggurath_summon", "Чёрная коза лесов с тысячным потомством");
        this.advancementDescr("familiar.shub_niggurath_summon", "Превратите Козу-фамильяра во что-нибудь ужасающее.");
        this.advancementTitle("familiar.shub_cthulhu_friends", "Страсть к сверхъестественному");
        this.advancementDescr("familiar.shub_cthulhu_friends", "Посмотрите, как Шуб-Ниггурат и Ктулху держатся за руки.");
        this.advancementTitle("familiar.shub_niggurath_spawn", "Подумайте о детях!");
        this.advancementDescr("familiar.shub_niggurath_spawn", "Пусть потомок Шуб-Ниггурата нанесёт урон врагу взрывом.");
        this.advancementTitle("familiar.beholder_ray", "Смертельный луч");
        this.advancementDescr("familiar.beholder_ray", "Пусть ваш Созерцатель-фамильяр нападёт на врага.");
        this.advancementTitle("familiar.beholder_eat", "Голод");
        this.advancementDescr("familiar.beholder_eat", "Посмотрите, как Созерцатель-фамильяр сжирает потомка Шуб-Ниггурата.");
        this.advancementTitle("familiar.fairy_save", "Ангел-хранитель");
        this.advancementDescr("familiar.fairy_save", "Пусть Ваша Фея-фамильяр спасёт от неминуемой смерти одного из Ваших фамильяров.");
        this.advancementTitle("familiar.mummy_dodge", "Ниндзя!");
        this.advancementDescr("familiar.mummy_dodge", "Уклонитесь от удара за счёт эффекта уклонения Мумии-фамильяра.");
        this.advancementTitle("familiar.beaver_woodchop", "Дровосек");
        this.advancementDescr("familiar.beaver_woodchop", "Пусть Ваш Бобёр-фамильяр срубит дерево.");
    }

    private void addKeybinds() {
        this.lang("ru_ru").add("key.occultism.category", "Occultism");
        this.lang("ru_ru").add("key.occultism.backpack", "Открыть сумку");
        this.lang("ru_ru").add("key.occultism.storage_remote", "Открыть средство доступа хранилища");
        this.lang("ru_ru").add("key.occultism.familiar.otherworld_bird", "Вкл./выкл. эффект кольца: Дриклыр");
        this.lang("ru_ru").add("key.occultism.familiar.greedy_familiar", "Вкл./выкл. эффект кольца: Алчный");
        this.lang("ru_ru").add("key.occultism.familiar.bat_familiar", "Вкл./выкл. эффект кольца: Летучая-мышь");
        this.lang("ru_ru").add("key.occultism.familiar.deer_familiar", "Вкл./выкл. эффект кольца: Олень");
        this.lang("ru_ru").add("key.occultism.familiar.cthulhu_familiar", "Вкл./выкл. эффект кольца: Ктулху");
        this.lang("ru_ru").add("key.occultism.familiar.devil_familiar", "Вкл./выкл. эффект кольца: Дьявол");
        this.lang("ru_ru").add("key.occultism.familiar.dragon_familiar", "Вкл./выкл. эффект кольца: Дракон");
        this.lang("ru_ru").add("key.occultism.familiar.blacksmith_familiar", "Вкл./выкл. эффект кольца: Кузнец");
        this.lang("ru_ru").add("key.occultism.familiar.guardian_familiar", "Вкл./выкл. эффект кольца: Страж");
        this.lang("ru_ru").add("key.occultism.familiar.headless_familiar", "Вкл./выкл. эффект кольца: Безголовый человек-крыса");
        this.lang("ru_ru").add("key.occultism.familiar.chimera_familiar", "Вкл./выкл. эффект кольца: Химера");
        this.lang("ru_ru").add("key.occultism.familiar.goat_familiar", "Вкл./выкл. эффект кольца: Коза");
        this.lang("ru_ru").add("key.occultism.familiar.shub_niggurath_familiar", "Вкл./выкл. эффект кольца: Шуб-Ниггурат");
        this.lang("ru_ru").add("key.occultism.familiar.beholder_familiar", "Вкл./выкл. эффект кольца: Созерцатель");
        this.lang("ru_ru").add("key.occultism.familiar.fairy_familiar", "Вкл./выкл. эффект кольца: Фея");
        this.lang("ru_ru").add("key.occultism.familiar.mummy_familiar", "Вкл./выкл. эффект кольца: Мумия");
        this.lang("ru_ru").add("key.occultism.familiar.beaver_familiar", "Вкл./выкл. эффект кольца: Бобёр");
    }

    private void addJeiTranslations() {
        this.lang("ru_ru").add("occultism.jei.spirit_fire", "Духовный огонь");
        this.lang("ru_ru").add("occultism.jei.crushing", "Дух-Дробильщик");
        this.lang("ru_ru").add("occultism.jei.miner", "Пространственная шахта");
        this.lang("ru_ru").add("occultism.jei.miner.chance", "Коэффициент: %d");
        this.lang("ru_ru").add("occultism.jei.ritual", "Оккультный ритуал");
        this.lang("ru_ru").add("occultism.jei.pentacle", "Пентакля");

        this.lang("ru_ru").add(TranslationKeys.JEI_CRUSHING_RECIPE_MIN_TIER, "Мин. уровень дробильщика: %d");
        this.lang("ru_ru").add(TranslationKeys.JEI_CRUSHING_RECIPE_MAX_TIER, "Макс. уровень дробильщика:: %d");
        this.lang("ru_ru").add("jei.occultism.ingredient.tallow.description", "Для получения жира убивайте животных, таких как \u00a72свиньи\u00a7r, \u00a72коровы\u00a7r, \u00a72овцы\u00a7r, \u00a72лошади\u00a7r и \u00a72ламы\u00a7r при помощи ножа мясника.");
        this.lang("ru_ru").add("jei.occultism.ingredient.otherstone.description", "Преимущественно встречается в Рощах из Иного Места. Виден только во время активного состояния \u00a76Третьего глаза\u00a7r. За дополнительными сведениями, обратитесь в \u00a76Справочник душ\u00a7r.");
        this.lang("ru_ru").add("jei.occultism.ingredient.otherworld_log.description", "Преимущественно встречается в Рощах из Иного Места. Виден только во время \u00a76Третьего глаза\u00a7r. За дополнительными сведениями, обратитесь в \u00a76Справочник душ\u00a7r.");
        this.lang("ru_ru").add("jei.occultism.ingredient.otherworld_sapling.description", "Можно получить у Торговца потусторонними саженцами. Можно увидеть и собрать без \u00a76Третьего глаза\u00a7r. За дополнительными сведениями, обратитесь в \u00a76Справочник душ\u00a7r на предмет вызова Торговца.");
        this.lang("ru_ru").add("jei.occultism.ingredient.otherworld_sapling_natural.description", "Преимущественно встречается в Рощах из Иного Места. Виден только во время активного состояния \u00a76Третьего глаза\u00a7r. За дополнительными сведениями, обратитесь в \u00a76Справочник душ\u00a7r.");
        this.lang("ru_ru").add("jei.occultism.ingredient.otherworld_leaves.description", "Преимущественно встречается в Рощах из Иного Места. Виден только во время активного состояния \u00a76Третьего глаза\u00a7r. За дополнительными сведениями, обратитесь в \u00a76Справочник душ\u00a7r.");
        this.lang("ru_ru").add("jei.occultism.ingredient.iesnium_ore.description", "Встречается в Незере. Виден только во время активного состояния \u00a76Третьего глаза\u00a7r. За дополнительными сведениями, обратитесь в \u00a76Справочник душ\u00a7r.");
        this.lang("ru_ru").add("jei.occultism.ingredient.spirit_fire.description", "Бросьте на землю \u00a76Плод Блаженство демона\u00a7r и подожгите. За дополнительными сведениями, обратитесь в \u00a76Справочник душ\u00a7r.");
        this.lang("ru_ru").add("jei.occultism.ingredient.datura.description", "Может использоваться для исцеления любых вызванных духов и фамильяров при помощи ритуалов Occultism. Нажмите ПКМ по существу, чтобы исцелить его на 1 сердце.");

        this.lang("ru_ru").add("jei.occultism.ingredient.spawn_egg.familiar_goat.description", "Накормите Химеру-фамильяра золотым яблоком для получения Козы-фамильяра. За дополнительными сведениями, обратитесь в \u00a76Справочник душ\u00a7r.");
        this.lang("ru_ru").add("jei.occultism.ingredient.spawn_egg.familiar_shub_niggurath.description", "Приведите Козу-фамильяра в лесной биом и щёлкните по ней сначала: Чёрным красителем, затем Кремнием, а затем Оком Эндера для получения Шуба-Ниггурата-фамильяра. За дополнительными сведениями, обратитесь в \u00a76Справочник душ\u00a7r.");

        this.lang("ru_ru").add("jei.occultism.sacrifice", "Жертвоприношение: %s");
        this.lang("ru_ru").add("jei.occultism.summon", "Вызов: %s");
        this.lang("ru_ru").add("jei.occultism.job", "Занятие: %s");
        this.lang("ru_ru").add("jei.occultism.item_to_use", "Использование предмета:");
        this.lang("ru_ru").add("jei.occultism.error.missing_id", "Не удалось определить рецепт.");
        this.lang("ru_ru").add("jei.occultism.error.invalid_type", "Неверный тип рецепта..");
        this.lang("ru_ru").add("jei.occultism.error.recipe_too_large", "Рецепт больше 3х3.");
        this.lang("ru_ru").add("jei.occultism.error.pentacle_not_loaded", "Пентакль не может быть загружен.");
        this.lang("ru_ru").add("item.occultism.jei_dummy.require_sacrifice", "Требуется жертвоприношение!");
        this.lang("ru_ru").add("item.occultism.jei_dummy.require_sacrifice.tooltip", "Для запуска требуется жертвоприношение. Обратитесь в Справочник душ за подробными инструкциями.");
        this.lang("ru_ru").add("item.occultism.jei_dummy.require_item_use", "Требуется использовать предмет!");
        this.lang("ru_ru").add("item.occultism.jei_dummy.require_item_use.tooltip", "Для запуска требуется использовать определённый предмет. Обратитесь в Справочник душ за подробными инструкциями.");
        this.lang("ru_ru").add("item.occultism.jei_dummy.none", "Результат ритуала без предмета");
        this.lang("ru_ru").add("item.occultism.jei_dummy.none.tooltip", "Этот ритуал не создаёт предметы.");
    }

    private void addFamiliarSettingsMessages() {
        this.lang("ru_ru").add("message.occultism.familiar.otherworld_bird.enabled", "Эффект кольца — Дрикрыл: включен");
        this.lang("ru_ru").add("message.occultism.familiar.otherworld_bird.disabled", "Эффект кольца — Дрикрыл: выключен");
        this.lang("ru_ru").add("message.occultism.familiar.greedy_familiar.enabled", "Эффект кольца — Алчный: включен");
        this.lang("ru_ru").add("message.occultism.familiar.greedy_familiar.disabled", "Эффект кольца — Алчный: выключен");
        this.lang("ru_ru").add("message.occultism.familiar.bat_familiar.enabled", "Эффект кольца — Летучая-мышь: включен");
        this.lang("ru_ru").add("message.occultism.familiar.bat_familiar.disabled", "Эффект кольца — Летучая-мышь: выключен");
        this.lang("ru_ru").add("message.occultism.familiar.deer_familiar.enabled", "Эффект кольца — Олень: включен");
        this.lang("ru_ru").add("message.occultism.familiar.deer_familiar.disabled", "Эффект кольца — Олень: выключен");
        this.lang("ru_ru").add("message.occultism.familiar.cthulhu_familiar.enabled", "Эффект кольца — Ктулху: включен");
        this.lang("ru_ru").add("message.occultism.familiar.cthulhu_familiar.disabled", "Эффект кольца — Ктулху: выключен");
        this.lang("ru_ru").add("message.occultism.familiar.devil_familiar.enabled", "Эффект кольца — Дьявол: включен");
        this.lang("ru_ru").add("message.occultism.familiar.devil_familiar.disabled", "Эффект кольца — Дьявол: выключен");
        this.lang("ru_ru").add("message.occultism.familiar.dragon_familiar.enabled", "Эффект кольца — Дракон: включен");
        this.lang("ru_ru").add("message.occultism.familiar.dragon_familiar.disabled", "Эффект кольца — Дракон: выключен");
        this.lang("ru_ru").add("message.occultism.familiar.blacksmith_familiar.enabled", "Эффект кольца — Кузнец: включен");
        this.lang("ru_ru").add("message.occultism.familiar.blacksmith_familiar.disabled", "Эффект кольца — Кузнец: выключен");
        this.lang("ru_ru").add("message.occultism.familiar.guardian_familiar.enabled", "Эффект кольца — Страж: включен");
        this.lang("ru_ru").add("message.occultism.familiar.guardian_familiar.disabled", "Эффект кольца — Страж: выключен");
        this.lang("ru_ru").add("message.occultism.familiar.headless_familiar.enabled", "Эффект кольца — Безголовый человек-крыса: включен");
        this.lang("ru_ru").add("message.occultism.familiar.headless_familiar.disabled", "Эффект кольца — Безголовый человек-крыса: выключен");
        this.lang("ru_ru").add("message.occultism.familiar.chimera_familiar.enabled", "Эффект кольца — Химера: включен");
        this.lang("ru_ru").add("message.occultism.familiar.chimera_familiar.disabled", "Эффект кольца — Химера: выключен");
        this.lang("ru_ru").add("message.occultism.familiar.shub_niggurath_familiar.enabled", "Эффект кольца — Шуб-Ниггурат: включен");
        this.lang("ru_ru").add("message.occultism.familiar.shub_niggurath_familiar.disabled", "Эффект кольца — Шуб-Ниггурат: выключен");
        this.lang("ru_ru").add("message.occultism.familiar.beholder_familiar.enabled", "Эффект кольца — Созерцатель: включен");
        this.lang("ru_ru").add("message.occultism.familiar.beholder_familiar.disabled", "Эффект кольца — Созерцатель: выключен");
        this.lang("ru_ru").add("message.occultism.familiar.fairy_familiar.enabled", "Эффект кольца — Фея: включен");
        this.lang("ru_ru").add("message.occultism.familiar.fairy_familiar.disabled", "Эффект кольца — Фея: выключен");
        this.lang("ru_ru").add("message.occultism.familiar.mummy_familiar.enabled", "Эффект кольца — Мумия: включен");
        this.lang("ru_ru").add("message.occultism.familiar.mummy_familiar.disabled", "Эффект кольца — Мумия: выключен");
        this.lang("ru_ru").add("message.occultism.familiar.beaver_familiar.enabled", "Эффект кольца — Бобёр: включен");
        this.lang("ru_ru").add("message.occultism.familiar.beaver_familiar.disabled", "Эффект кольца — Бобёр: выключен");
    }

    private void addPentacles() {
        this.addPentacle("otherworld_bird", "Потусторонняя птица");
        this.addPentacle("craft_afrit", "Вечное заточение Севиры");
        this.addPentacle("craft_djinni", "Связывание высшего Стригора");
        this.addPentacle("craft_foliot", "Спектральное вынуждение Изива");
        this.addPentacle("craft_marid", "Перевёрнутая башня Юфиксеса");
        this.addPentacle("possess_afrit", "Заклятие повиновения Абраса");
        this.addPentacle("possess_djinni", "Порабощение Айгана");
        this.addPentacle("possess_foliot", "Приманка Гидирина");
        this.addPentacle("summon_afrit", "Вызов Абраса");
        this.addPentacle("summon_djinni", "Зов Офикса");
        this.addPentacle("summon_foliot", "Круг Авиара");
        this.addPentacle("summon_wild_afrit", "Вызов свободного Абраса");
        this.addPentacle("summon_marid", "Поощряемое привлечение Фатмы");
        this.addPentacle("summon_wild_greater_spirit", "Вызов несвязанного Осорина");
    }

    private void addPentacle(String id, String name) {
        this.add(Util.makeDescriptionId("multiblock", ResourceLocation.fromNamespaceAndPath(Occultism.MODID, id)), name);
    }

    private void addRitualDummies() {
        this.lang("ru_ru").add("item.occultism.ritual_dummy.custom_ritual", "Польз. макет ритуала");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.custom_ritual.tooltip", "Используется для сборок в качестве запасного варианта для пользовательских ритуалов, у которых нет своего предмета для ритуала.");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.craft_wild_trim", "Ритуал: Кузнечный шаблон");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.craft_wild_trim.tooltip", "Марид скуёт Кузнечный шаблон.");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.craft_dimensional_matrix", "Ритуал: Создать пространственную матрицу");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.craft_dimensional_matrix.tooltip", "Пространственная матрица — мостик в небольшое измерение, используемый в качестве хранения предметов.");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.craft_dimensional_mineshaft", "Ритуал: Создать пространственную шахту");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.craft_dimensional_mineshaft.tooltip", "Позволяет духам-шахтёрам входить в шахтёрское измерение и выносить оттуда ресурсы.");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.craft_infused_lenses", "Ритуал: Создать наполненные линзы");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.craft_infused_lenses.tooltip", "Эти линзы используются для создания очков, дающие тебе возможность видеть за пределами физического мира.");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.craft_infused_pickaxe", "Ритуал: Создать наполненную кирку");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.craft_infused_pickaxe.tooltip", "Наполнение кирки.");

        this.lang("ru_ru").add("item.occultism.ritual_dummy.craft_miner_djinni_ores", "Ритуал: Вызов рудного Горняка-Джинна");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.craft_miner_djinni_ores.tooltip", "Вызывайте рудного Горняка-Джинна в магическую лампу.");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.craft_miner_foliot_unspecialized", "Ритуал: Вызов Горняка-Фолиота");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.craft_miner_foliot_unspecialized.tooltip", "Вызывайте Горняка-Фолиота в магическую лампу.");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.craft_miner_afrit_deeps", "Ритуал: Вызов Горняка-Африта для глубинносланцевой руды");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.craft_miner_afrit_deeps.tooltip", "Вызов Горняка-Африта для глубинносланцевой руды в магическую лампу.");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.craft_miner_marid_master", "Ритуал: Вызов мастера Горняка-Марида");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.craft_miner_marid_master.tooltip", "Вызывайте мастера Горняка-Марида в магическую лампу");

        this.lang("ru_ru").add("item.occultism.ritual_dummy.craft_satchel", "Ритуал: Создать изумительной большой сумки");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.craft_satchel.tooltip", "Эта сумка позволяет хранить больше предметов, чем указывают её размеры. Это делает её практичным спутником путешественника.");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.craft_soul_gem", "Ритуал: Создать самоцвета душ");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.craft_soul_gem.tooltip", "Самоцвет душ позволяет временно хранить живых существ.");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.craft_familiar_ring", "Ритуал: Создать кольцо фамильяра");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.craft_familiar_ring.tooltip", "Кольцо фамильяра позволяет хранить фамильяров. Кольцо будет накладывать эффект фамильяра на владельца.");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.craft_stabilizer_tier1", "Ритуал: Создать стабилизатор хранилища 1-го уровня");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.craft_stabilizer_tier1.tooltip", "Стабилизатор хранилища позволяет хранить больше предметов в средстве доступа пространственного хранилища.");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.craft_stabilizer_tier2", "Ритуал: Создать стабилизатор хранилища 2-го уровня");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.craft_stabilizer_tier2.tooltip", "Стабилизатор хранилища позволяет хранить больше предметов в средстве доступа пространственного хранилища.");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.craft_stabilizer_tier3", "Ритуал: Создать стабилизатор хранилища 3-го уровня");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.craft_stabilizer_tier3.tooltip", "Стабилизатор хранилища позволяет хранить больше предметов в средстве доступа пространственного хранилища.");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.craft_stabilizer_tier4", "Ритуал: Создать стабилизатор хранилища 4-го уровня");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.craft_stabilizer_tier4.tooltip", "Стабилизатор хранилища позволяет хранить больше предметов в средстве доступа пространственного хранилища.");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.craft_stable_wormhole", "Ритуал: Создать стабильную червоточину");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.craft_stable_wormhole.tooltip", "Стабильная червоточина позволяет получить доступ к пространственной матрице из удалённого местоназначения.");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.craft_storage_controller_base", "Ритуал: Создать основу актуатора хранилища");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.craft_storage_controller_base.tooltip", "Основа актуатора хранилища заключает Фолиота в матрице пространственного хранилища, отвечающего за взаимодействие с предметами.");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.craft_storage_remote", "Ритуал: Создать средство доступа хранилища");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.craft_storage_remote.tooltip", "Средство доступа хранилища может быть связано с Актуатором хранилища для получения удалённого доступа к предметам.");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.familiar_otherworld_bird", "Ритуал: Вызов дрикрыла-фамильяра");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.familiar_otherworld_bird.tooltip", "Дрикрыл наделяет своего владельца ограниченными возможностями полёта, будучи рядом.");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.familiar_parrot", "Ритуал: Вызов попугая-фамильяра");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.familiar_parrot.tooltip", "Попугай-фамильяр ведёт себя точь-в-точь как прирученные попугаи.");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.familiar_greedy", "Ритуал: Вызов алчного фамильяра");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.familiar_greedy.tooltip", "Алчный фамильяр подбирает предметы для своего хозяина. Находясь в заключении кольца для фамильяра, он повышает дальность сбора предметов (как Магнит предметов [Cyclic])");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.familiar_bat", "Ритуал: Вызов летучей мыши-фамильяра");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.familiar_bat.tooltip", "Летучая мышь-фамильяр наделяет своего хозяина ночным зрением.");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.familiar_deer", "Ритуал: Вызов оленя-фамильяра");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.familiar_deer.tooltip", "Олень-фамильяр наделяет своего хозяина прыгучестью.");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.familiar_cthulhu", "Ритуал: Вызов ктулху-фамильяра");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.familiar_cthulhu.tooltip", "Ктулху-фамильяр наделяет своего хозяина водным дыханием.");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.familiar_devil", "Ритуал: Вызов дьявола-фамильяра");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.familiar_devil.tooltip", "Дьявол-фамильяр наделяет своего хозяина огнестойкостью.");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.familiar_dragon", "Ритуал: Вызов дракона-фамильяра");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.familiar_dragon.tooltip", "Дракон-фамильяр наделяет своего хозяина повышенным получением опыта.");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.familiar_blacksmith", "Ритуал: Вызов кузнеца-фамильяра");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.familiar_blacksmith.tooltip", "Кузнец-фамильяр берёт камень, добытый хозяином и использует его для починки снаряжения.");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.familiar_guardian", "Ритуал: Вызов стража-фамильяра");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.familiar_guardian.tooltip", "Страж-фамильяр оберегает своего хозяина от жестокой смерти.");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.familiar_headless", "Ритуал: Вызов безголового человека-крысы-фамильяра");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.familiar_headless.tooltip", "Безголовый человек-крыса-фамильяр повышает скорость атаки своего хозяина против врагов, чью голову он украл.");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.familiar_chimera", "Ритуал: Вызов химеры-фамильяра");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.familiar_chimera.tooltip", "Химеру-фамильяра можно кормить до полного роста для получения скорости атаки и урона. Как только она вырастит, игроки смогут её оседлать.");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.familiar_beholder", "Ритуал: Вызов созерцателя-фамильяра");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.familiar_beholder.tooltip", "Созерцатель-фамильяр подсвечивает близлежащих существ эффектом свечения и стреляет лазерными лучами во врагов.");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.familiar_fairy", "Ритуал: Вызов феи-фамильяра");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.familiar_fairy.tooltip", "Фея-фамильяр предотвращает смерть других фамильяров, истощает жизненную силу своих врагов и исцеляет своего хозяина и его фамильяров.");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.familiar_mummy", "Ритуал: Вызов мумии-фамильяра");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.familiar_mummy.tooltip", "Мумия-фамильяр — мастер боевых искусств и сражается с целью защитить своего хозяина.");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.familiar_beaver", "Ритуал: Вызов бобра-фамильяра");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.familiar_beaver.tooltip", "Бобёр-фамильяр занимается заготовкой древесины, когда они вырастают из саженца и наделяет своего хозяина повышенной скоростью рубки.");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.possess_enderman", "Ритуал: Вызов одержимого эндермена");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.possess_enderman.tooltip", "При убийстве Одержимый эндермен всегда выдаёт минимум 1 эндер-жемчуг.");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.possess_endermite", "Ритуал: Вызов одержимого эндермита");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.possess_endermite.tooltip", "Одержимый эндермит выдаёт эндерняк.");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.possess_skeleton", "Ритуал: Вызов одержимого скелета");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.possess_skeleton.tooltip", " При убийстве Одержимый скелет становится устойчивым к дневному свету и всегда выдаёт минимум 1 череп скелета.");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.possess_ghast", "Ритуал: Вызов одержимого гаста");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.possess_ghast.tooltip", "При убийстве Одержимый гаст всегда выдаёт минимум 1-у слезу гаста.");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.possess_phantom", "Ритуал: Вызов одержимого фантома");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.possess_phantom.tooltip", "При убийстве Одержимый фантом всегда выдаёт минимум 1-у мембрану фантома, но его легко поймать в ловушку.");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.possess_weak_shulker", "Ритуал: Вызов одержимого слабого шалкера");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.possess_weak_shulker.tooltip", "При убийстве Одержимый слабый шалкер может выдать минимум 1 плод хоруса и панцирь шалкера.");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.possess_shulker", "Ритуал: Вызов одержимого шалкера");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.possess_shulker.tooltip", "При убийстве Одержимый шалкер всегда выдаёт минимум 1 панцирь шалкера.");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.possess_elder_guardian", "Ритуал: Вызов одержимого древнего стража");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.possess_elder_guardian.tooltip", "При убийстве Одержимый древний страж выдаёт минимум 1-у раковину наутилуса. Кроме того, может сбросить сердце моря и обычную добычу.");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.possess_warden", "Ритуал: Вызов одержимого хранителя");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.possess_warden.tooltip", "При убийстве Одержимый хранитель всегда может выдать Осколок эха и другие древние вещи (кузнечные шаблоны и пластинки).");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.possess_hoglin", "Ритуал: Вызов одержимого хоглина");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.possess_hoglin.tooltip", "При убийстве у Одержимого хоглина есть вероятность выдать Кузнечный шаблон незеритового улучшения.");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.summon_afrit_rain_weather", "Ритуал: Дождливая погода");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.summon_afrit_rain_weather.tooltip", "Вызывает связанного Африта, вызывающего дождь.");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.summon_afrit_thunder_weather", "Ритуал: Гроза");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.summon_afrit_thunder_weather.tooltip", "Вызывает связанного Африта, вызывающего грозу.");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.summon_djinni_clear_weather", "Ритуал: Ясная погода");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.summon_djinni_clear_weather.tooltip", "Вызывает Джинна, устраняющего погоду.");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.summon_djinni_day_time", "Ритуал: Вызов рассвета");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.summon_djinni_day_time.tooltip", "Вызывает Джинна, устанавливающего время в полдень.");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.summon_djinni_manage_machine", "Ритуал: Вызов Станочника-Джинна");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.summon_djinni_manage_machine.tooltip", "Станочник автоматически перемещает предметы между системы пространственного хранилища и присоединённых инвентарей, а также устройств.");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.summon_djinni_night_time", "Ритуал: Вызов сумерек");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.summon_djinni_night_time.tooltip", "Вызывает Джинна, устанавливающего время в сумерки.");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.summon_foliot_crusher", "Ритуал: Вызов Дробильщика-Фолиота");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.summon_foliot_crusher.tooltip", "Дробильщик — дух, вызываемый с целью размельчения руд в пыль, эффективно удваивая металлопродукцию.\n" + ChatFormatting.GRAY + ChatFormatting.ITALIC + "Примечание: некоторые рецепты могут потребовать высокий либо низкий уровень дробильщиков.");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.summon_djinni_crusher", "Ритуал: Вызов Дробильщика-Джинна");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.summon_djinni_crusher.tooltip", "Дробильщик — дух, вызываемый с целью размельчения руд в пыль, эффективно (гораздо) удваивая металлопродукцию. Этот дробильщик распадается (гораздо) медленнее низкоуровневых дробильщиков.\n" + ChatFormatting.GRAY + ChatFormatting.ITALIC + "Примечание: некоторые рецепты могут потребовать высокий либо низкий уровень дробильщиков.");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.summon_afrit_crusher", "Ритуал: Вызов Дробильщика-Африта");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.summon_afrit_crusher.tooltip", "Дробильщик — дух, вызываемый с целью размельчения руд в пыль, эффективно (гораздо) удваивая металлопродукцию. Этот дробильщик распадается (гораздо) медленнее низкоуровневых дробильщиков.\n" + ChatFormatting.GRAY + ChatFormatting.ITALIC + "Примечание: некоторые рецепты могут потребовать высокий либо низкий уровень дробильщиков.");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.summon_marid_crusher", "Ритуал: Вызов Дробильщика-Марида");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.summon_marid_crusher.tooltip", "Дробильщик — дух, вызываемый с целью размельчения руд в пыль, эффективно (гораздо) удваивая металлопродукцию. Этот дробильщик распадается (гораздо) медленнее низкоуровневых дробильщиков.\n" + ChatFormatting.GRAY + ChatFormatting.ITALIC + "Примечание: некоторые рецепты могут потребовать высокий либо низкий уровень дробильщиков.");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.summon_foliot_lumberjack", "Ритуал: Вызов Дровосека-Фолиота");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.summon_foliot_lumberjack.tooltip", "Дровосек занимается заготовкой древесины на своём рабочем месте и кладёт выпавшие предметы в указанный сундук.");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.summon_foliot_otherstone_trader", "Ритуал: Вызов потустороннего торговца");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.summon_foliot_otherstone_trader.tooltip", "Торговец потусторонним камнем обменивает обычный камень на потусторонний.");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.summon_foliot_sapling_trader", "Ритуал: Вызов торговца потусторонними саженцами");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.summon_foliot_sapling_trader.tooltip", "Торговец потусторонними саженцами обменивает естественные потусторонние саженцы на стабильные, что могут быть собраны без Третьего глаза.");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.summon_foliot_transport_items", "Ритуал: Вызов Транспортировщика-Фолиота");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.summon_foliot_transport_items.tooltip", "Транспортировщик перемещает все предметы из одного инвентаря к другому, к которым он получает доступ, включая устройства.");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.summon_foliot_cleaner", "Ритуал: Вызов Дворника-Фолиота");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.summon_foliot_cleaner.tooltip", "Дворник подбирает выпавшие предметы и кладёт их в указанный инвентарь.");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.summon_wild_afrit", "Ритуал: Вызов несвязанного Африта");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.summon_wild_afrit.tooltip", "Вызывает несвязанного Африта, которого можно убить для получения Сущности Африта.");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.summon_wild_hunt", "Ритуал: Вызов Дикой Охоты");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.summon_wild_hunt.tooltip", "В состав Дикой Охоты входит Визер-скелеты и их прислужники, а в качестве большого шанса с прислужников выпадут черепа визер-скелетов.");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.summon_wild_otherworld_bird", "Ритуал: Вызов дикого дрикрыла");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.summon_wild_otherworld_bird.tooltip", "Вызывает дрикрыла-фамильяра, который может быть приручен кем угодно, не только вызывателем.");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.summon_wild_parrot", "Ритуал: Вызов дикого попугая");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.summon_wild_parrot.tooltip", "Вызывает попугая, который может быть приручен кем угодно, не только вызывателем.");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.summon_allay", "Ритуал: Очищение Вредины до Тихони");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.summon_allay.tooltip", "Очистка Вредины до Тихони в ходе процесса воскресения.");

        this.lang("ru_ru").add("item.occultism.ritual_dummy.summon_wild_husk", "Ритуал: Вызов орды диких кадавров");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.summon_wild_husk.tooltip", "В состав орды диких кадавров входит несколько Кадавров, с которых выпадут предметы, связанные  с испытаниями пустыни.");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.summon_wild_drowned", "Ритуал: Вызов орды диких утопленников");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.summon_wild_drowned.tooltip", "В состав орды диких утопленников входят несколько Утопленников, с которых выпадают предметы, связанные с испытаниями океана.");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.summon_wild_creeper", "Ритуал: Вызов орды диких криперов");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.summon_wild_creeper.tooltip", "В состав орды диких криперов входят несколько заряженных Криперов, с которых выпадают множество пластинок.");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.summon_wild_silverfish", "Ритуал: Вызов орды диких чешуйниц");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.summon_wild_silverfish.tooltip", "В состав орды диких чешуйниц входит несколько чешуйниц, с которых выпадают предметы, связанные с испытаниями руин.");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.possess_weak_breeze", "Ритуал: Вызов одержимого слабого вихря");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.possess_weak_breeze.tooltip", "Одержимый слабый вихрь выдаёт Ключ испытаний и предметы, что связаны с камерой испытаний.");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.possess_breeze", "Ритуал: Вызов одержимого вихря");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.possess_breeze.tooltip", "Одержимый вихрь выдаёт Зловещий ключ испытаний и предметы, что связаны с камерой испытаний.");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.possess_strong_breeze", "Ритуал: Вызов одержимого сильного вихря");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.possess_strong_breeze.tooltip", "Одержимый сильный вихрь выдаёт Навершие булавы и предметы, что связаны с камерой испытаний.");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.summon_horde_illager", "Ритуал: Вызов одержимого заклинателя");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.summon_horde_illager.tooltip", "Вызывает одержимого Заклинателя и его приспешников.");

        this.lang("ru_ru").add(OccultismItems.RITUAL_DUMMY_SUMMON_DEMONIC_WIFE.get(), "Ритуал: Вызов демонической жены");
        this.addTooltip(OccultismItems.RITUAL_DUMMY_SUMMON_DEMONIC_WIFE.get(), "Вызывает демоническую жену для поддержки: она будет Вас защищать, помогать с готовкой, и увеличит продолжительность зелья.");

        this.lang("ru_ru").add(OccultismItems.RITUAL_DUMMY_SUMMON_DEMONIC_HUSBAND.get(), "Ритуал: Вызов демонического мужа");
        this.addTooltip(OccultismItems.RITUAL_DUMMY_SUMMON_DEMONIC_HUSBAND.get(), "Вызывает демонического мужа для поддержки: он будет Вас защищать, помогать с готовкой, и увеличит продолжительность зелья.");


        this.lang("ru_ru").add(OccultismItems.RITUAL_RESURRECT_FAMILIAR.get(), "Ритуал: Воскресение фамильяра");
        this.addTooltip(OccultismItems.RITUAL_RESURRECT_FAMILIAR.get(), "Воскрешает фамильяра из Осколка души.");
    }

    public void addTooltip(ItemLike key, String value) {
        this.add(key.asItem().getDescriptionId() + ".tooltip", value);
    }

    public void addAutoTooltip(ItemLike key, String value) {
        this.add(key.asItem().getDescriptionId() + ".auto_tooltip", value);
    }

    private void addDialogs() {
        this.lang("ru_ru").add("dialog.occultism.dragon.pet", "Мурчанье");
        this.lang("ru_ru").add("dialog.occultism.mummy.kapow", "БУМ!");
        this.lang("ru_ru").add("dialog.occultism.beaver.snack_on_cooldown", "Эй, не будь алчным!");
        this.lang("ru_ru").add("dialog.occultism.beaver.no_upgrade", "Кузнецу-фамильяру нужно улучшить Созерцателя, прежде чем он будет разбрасываться лакомствами!");
    }

    private void addModonomiconIntegration() {
        this.lang("ru_ru").add(I18n.RITUAL_RECIPE_ITEM_USE, "Использование предмета:");
        this.lang("ru_ru").add(I18n.RITUAL_RECIPE_SUMMON, "Вызов: %s");
        this.lang("ru_ru").add(I18n.RITUAL_RECIPE_JOB, "Занятие: %s");
        this.lang("ru_ru").add(I18n.RITUAL_RECIPE_SACRIFICE, "Жертвоприношение: %s");
        this.lang("ru_ru").add(I18n.RITUAL_RECIPE_GO_TO_PENTACLE, "Открыть страницу пентакля: %s");
    }

    private void advancementTitle(String name, String s) {
        this.add(((TranslatableContents) OccultismAdvancementSubProvider.title(name).getContents()).getKey(), s);
    }

    private void advancementDescr(String name, String s) {
        this.add(((TranslatableContents) OccultismAdvancementSubProvider.descr(name).getContents()).getKey(), s);
    }

    private void addTags() {
        // Теги блока
        this.addBlockTag(OccultismTags.Blocks.OTHERWORLD_SAPLINGS,"Потусторонние саженцы");
        this.addBlockTag(OccultismTags.Blocks.CANDLES,"Свечи");
        this.addBlockTag(OccultismTags.Blocks.CAVE_WALL_BLOCKS,"Пещерная ограда");
        this.addBlockTag(OccultismTags.Blocks.NETHERRACK,"Незерак");
        this.addBlockTag(OccultismTags.Blocks.STORAGE_STABILIZER,"Стабилизатор хранилища");
        this.addBlockTag(OccultismTags.Blocks.TREE_SOIL,"Почва для дерева");
        this.addBlockTag(OccultismTags.Blocks.WORLDGEN_BLACKLIST,"Блоки генерации в чёрном списке");
        this.addBlockTag(OccultismTags.Blocks.IESNIUM_ORE,"Руда айзния");
        this.addBlockTag(OccultismTags.Blocks.SILVER_ORE,"Серебряная руда");
        this.addBlockTag(OccultismTags.Blocks.STORAGE_BLOCKS_IESNIUM,"Хранилище блоков айзния");
        this.addBlockTag(OccultismTags.Blocks.STORAGE_BLOCKS_SILVER,"Хранилище серебряный блоков");
        this.addBlockTag(OccultismTags.Blocks.STORAGE_BLOCKS_RAW_IESNIUM,"Хранилище рудных блоков айзния");
        this.addBlockTag(OccultismTags.Blocks.STORAGE_BLOCKS_RAW_SILVER,"Хранилище рудных блоков серебра");


        // Теги предмета
        this.addItemTag(OccultismTags.Items.OTHERWORLD_SAPLINGS,"Потусторонние саженцы");
        this.addItemTag(OccultismTags.Items.BOOK_OF_CALLING_DJINNI,"Книга вызова Джинна");
        this.addItemTag(OccultismTags.Items.BOOK_OF_CALLING_FOLIOT,"Книга вызова Фолиота");
        this.addItemTag(OccultismTags.Items.Miners.BASIC_RESOURCES,"Горняки базовых ресурсов");
        this.addItemTag(OccultismTags.Items.Miners.DEEPS,"Горняки глубинносланца");
        this.addItemTag(OccultismTags.Items.Miners.MASTER,"Горняки редких ресурсов");
        this.addItemTag(OccultismTags.Items.Miners.ORES,"Основные горняки");
        this.addItemTag(OccultismTags.Items.TOOL_KNIVES,"Ножи");
        this.addItemTag(OccultismTags.Items.ELYTRA,"Элитры");
        this.addItemTag(OccultismTags.Items.OTHERWORLD_GOGGLES,"Потусторонние очки");
        this.addItemTag(OccultismTags.Items.DATURA_SEEDS,"Семена «Блаженство демона»");
        this.addItemTag(OccultismTags.Items.DATURA_CROP,"Блаженство демона");
        this.addItemTag(OccultismTags.Items.COPPER_DUST,"Медная пыль");
        this.addItemTag(OccultismTags.Items.GOLD_DUST,"Золотая пыль");
        this.addItemTag(OccultismTags.Items.IESNIUM_DUST,"Пыль айзния");
        this.addItemTag(OccultismTags.Items.IRON_DUST,"Железная пыль");
        this.addItemTag(OccultismTags.Items.SILVER_DUST,"Серебряная пыль");
        this.addItemTag(OccultismTags.Items.END_STONE_DUST,"Измельчённый эндерняк");
        this.addItemTag(OccultismTags.Items.OBSIDIAN_DUST,"Измельчённый обсидиан");
        this.addItemTag(OccultismTags.Items.IESNIUM_INGOT,"Слиток айзния");
        this.addItemTag(OccultismTags.Items.SILVER_INGOT,"Серебряный слиток");
        this.addItemTag(OccultismTags.Items.IESNIUM_NUGGET,"Кусочек айзния");
        this.addItemTag(OccultismTags.Items.SILVER_NUGGET,"Кусочек серебра");
        this.addItemTag(OccultismTags.Items.IESNIUM_ORE,"Руда айзния");
        this.addItemTag(OccultismTags.Items.SILVER_ORE,"Серебряная руда");
        this.addItemTag(OccultismTags.Items.RAW_IESNIUM,"Рудный айзний");
        this.addItemTag(OccultismTags.Items.RAW_SILVER,"Рудное серебро");
        this.addItemTag(OccultismTags.Items.STORAGE_BLOCK_IESNIUM,"Хранилище блоков айзния");
        this.addItemTag(OccultismTags.Items.STORAGE_BLOCK_SILVER,"Хранилище серебряный блоков");
        this.addItemTag(OccultismTags.Items.STORAGE_BLOCK_RAW_IESNIUM,"Хранилище рудных блоков айзния");
        this.addItemTag(OccultismTags.Items.STORAGE_BLOCK_RAW_SILVER,"Хранилище рудных блоков серебра");
        this.addItemTag(OccultismTags.Items.TALLOW,"Жир");
        this.addItemTag(OccultismTags.Items.METAL_AXES,"Металлические топоры");
        this.addItemTag(OccultismTags.Items.MAGMA,"Магма");
        this.addItemTag(OccultismTags.Items.BOOKS,"Книги");
        this.addItemTag(OccultismTags.Items.FRUITS,"Фрукты");
    }

    private void addItemTag(ResourceLocation resourceLocation, String string) {
        this.add("tag.item."+resourceLocation.getNamespace()+"."+resourceLocation.getPath().replace("/","."), string);
    }
    private void addBlockTag(TagKey<Block> block, String string) {
        this.addBlockTag(block.location(),string);
    }
    private void addItemTag(TagKey<Item> item, String string) {
        this.addItemTag(item.location(),string);
    }
    private void addBlockTag(ResourceLocation resourceLocation, String string) {
        this.add("tag.block."+resourceLocation.getNamespace()+"."+resourceLocation.getPath().replace("/","."), string);
    }

    private void addEmiTranslations() {
        this.lang("ru_ru").add("emi.category.occultism.spirit_fire","Духовный огонь");
        this.lang("ru_ru").add("emi.category.occultism.crushing","Дробление");
        this.lang("ru_ru").add("emi.category.occultism.miner","Пространственная шахта");
        this.lang("ru_ru").add("emi.category.occultism.ritual","Ритуалы");
        this.lang("ru_ru").add("emi.occultism.item_to_use", "Использование предмета: %s");
    }

    private void addConfigurationTranslations() {

        this.addConfig("visual", "Визуальные настройки");
        this.addConfig("showItemTagsInTooltip", "Показывать теги предмета в подсказках");
        this.addConfig("disableDemonsDreamShaders", "Отключить шейдеры Блаженство демона");
        this.addConfig("disableHolidayTheming", "Отключить шейдеры Потусторонних очков");
        this.addConfig("useAlternativeDivinationRodRenderer", "Использовать альтернативный отрисовщик Стержня прорицания");
        this.addConfig("whiteChalkGlyphColor", "Цвет глифового мела");
        this.addConfig("goldenChalkGlyphColor", "Цвет золотого глифового мела");
        this.addConfig("purpleChalkGlyphColor", "Цвет пурпурного глифового мела");
        this.addConfig("redChalkGlyphColor", "Цвет красного глифового мела");

        this.addConfig("misc", "Прочие настройки");
        this.addConfig("syncJeiSearch", "Синхронизировать поиск с JEI");
        this.addConfig("divinationRodHighlightAllResults", "Подсвечивать все результаты Стержня прорицания");
        this.addConfig("divinationRodScanRange", "Радиус сканирования Жезла прорицания");
        this.addConfig("disableSpiritFireSuccessSound", "Отключить звук успешности Духовного огня");

        this.addConfig("storage", "Настройки хранилища");
        this.addConfig("stabilizerTier1AdditionalMaxItemTypes", "Максимум дополнительных типов предметов в стабилизаторе 1-го уровня");
        this.addConfig("stabilizerTier1AdditionalMaxTotalItemCount", "Максимум дополнительного общего количества предметов стабилизатора 1-го уровня");
        this.addConfig("stabilizerTier2AdditionalMaxItemTypes", "Максимум дополнительных типов предметов в стабилизаторе 2-го уровня");
        this.addConfig("stabilizerTier2AdditionalMaxTotalItemCount", "Максимум дополнительного общего количества предметов стабилизатора 2-го уровня");
        this.addConfig("stabilizerTier3AdditionalMaxItemTypes", "Максимум дополнительных типов предметов в стабилизаторе 3-го уровня");
        this.addConfig("stabilizerTier3AdditionalMaxTotalItemCount", "Максимум дополнительного общего количества предметов стабилизатора 3-го уровня");
        this.addConfig("stabilizerTier4AdditionalMaxItemTypes", "Максимум дополнительных типов предметов в стабилизаторе 4-го уровня");
        this.addConfig("stabilizerTier4AdditionalMaxTotalItemCount", "Максимум дополнительного общего количества предметов стабилизатора 4-го уровня");
        this.addConfig("controllerMaxItemTypes", "Максимум типов предметов в контроллере");
        this.addConfig("controllerMaxTotalItemCount", "Максимальное общее количество предмета в контроллера");
        this.addConfig("unlinkWormholeOnBreak", "Отвязывать Червоточину при разрушении");

        this.addConfig("spirit_job", "Настройки занятия духа");
        this.addConfig("drikwingFamiliarSlowFallingSeconds", "Продолжительность эффекта плавного падения в сек., полученное благодаря Дрикрылу-фамильяру.");
        this.addConfig("tier1CrusherTimeMultiplier", "Коэффициент времени для дробильщика 1-го уровня..");
        this.addConfig("tier2CrusherTimeMultiplier", "Коэффициент времени для дробильщика 2-го уровня..");
        this.addConfig("tier3CrusherTimeMultiplier", "Коэффициент времени для дробильщика 3-го уровня..");
        this.addConfig("tier4CrusherTimeMultiplier", "Коэффициент времени для дробильщика 4-го уровня..");
        this.addConfig("tier1CrusherOutputMultiplier", "Коэффициент продукции для дробильщика 1-го уровня..");
        this.addConfig("tier2CrusherOutputMultiplier", "Коэффициент продукции для дробильщика 2-го уровня..");
        this.addConfig("tier3CrusherOutputMultiplier", "Коэффициент продукции для дробильщика 3-го уровня..");
        this.addConfig("tier4CrusherOutputMultiplier", "Коэффициент продукции для дробильщика 4-го уровня..");
        this.addConfig("crusherResultPickupDelay", "Задержка, прежде чем предметы из дробильщика могут быть подобраны.");
        this.addConfig("blacksmithFamiliarRepairChance", "Вероятность, что Кузнец-фамильяр будет чинить предмет каждый такт.");
        this.addConfig("blacksmithFamiliarUpgradeCost", "Стоимость обновления предметов в уровнях опыта при помощи Кузнеца-фамильяра.");
        this.addConfig("blacksmithFamiliarUpgradeCooldown", "Перезарядка/в тактах, прежде чем Кузнец-фамильяр сможет снова обновить предметы.");

        this.addConfig("rituals", "Настройки ритуалов");
        this.addConfig("enableClearWeatherRitual", "Включить требования для ритуала ясной погоды.");
        this.addConfig("enableRainWeatherRitual", "Включить требования ритуалу для вызова дождливой погоды.");
        this.addConfig("enableThunderWeatherRitual", "Включить требования ритуала для вызова грозы.");
        this.addConfig("enableDayTimeRitual", "Позволить ритуалу изменять время на день.");
        this.addConfig("enableNightTimeRitual", "Позволить ритуалу изменять время в ночь.");
        this.addConfig("enableRemainingIngredientCountMatching", "Включить соответствия оставшихся ингредиентов в рецептах ритуала.");
        this.addConfig("ritualDurationMultiplier", "Регулировка коэффициента продолжительности всех ритуалов.");
        this.addConfig("possibleSpiritNames", "Возможные имена духов");

        this.addConfig("dimensional_mineshaft", "Настройки пространственной шахты");
        this.addConfig("miner_foliot_unspecialized", "Неспециализированный Горняк-Фолиот");
        this.addConfig("miner_djinni_ores", "Рудный Горняк-Джинн");
        this.addConfig("miner_afrit_deeps", "Горняк-Африт для глубинносланцевой руды");
        this.addConfig("miner_marid_master", "Мастер Горняк-Марид");

        this.addConfig("maxMiningTime", "Максимальное время добычи");
        this.addConfig("rollsPerOperation", "Rolls Per Operation");
        this.addConfig("durability", "Прочность");
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
