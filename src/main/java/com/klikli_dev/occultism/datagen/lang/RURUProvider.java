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
import com.klikli_dev.occultism.datagen.OccultismAdvancementProvider;
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
import net.minecraft.world.level.dimension.BuiltinDimensionTypes;

public class RURUProvider extends AbstractModonomiconLanguageProvider {

    public static final String COLOR_PURPLE = "ad03fc";
    public static final String DEMONS_DREAM = "Видение демона";


    public RURUProvider(PackOutput gen) {
        super(gen, Occultism.MODID, "ru_ru");
    }

    public AbstractModonomiconLanguageProvider lang(String lang) {
        return this;
    }

    public void addItemMessages() {

        //"item\.occultism\.(.*?)\.(.*)": "(.*)",
        // this.add\(OccultismItems.\U\1\E.get\(\).getDescriptionId\(\)  + " \2", "\3"\);

        //book of callings use generic message base key, hence the manual string
        this.lang("ru_ru").add("item.occultism.book_of_calling" + ".message_target_uuid_no_match", "Этот дух на данный момент не связан с книгой. Нажатие Shift + ПКМ по духу — связать.");
        this.lang("ru_ru").add("item.occultism.book_of_calling" + ".message_target_linked", "Теперь этот дух связан с книгой.");
        this.lang("ru_ru").add("item.occultism.book_of_calling" + ".message_target_cannot_link", "Этот дух не может быть связан с этой книгой. Книга вызова должна соответствовать задаче духа!");
        this.lang("ru_ru").add("item.occultism.book_of_calling" + ".message_target_entity_no_inventory", "У этой сущности нет инвентаря, она не может быть установлена в качестве место ввода.");
        this.lang("ru_ru").add("item.occultism.book_of_calling" + ".message_spirit_not_found", "Дух, связанный с этой книгой не обитает в этой плоскости существования.");
        this.lang("ru_ru").add("item.occultism.book_of_calling" + ".message_set_deposit", "%s будет вводить в %s со стороны: %s");
        this.lang("ru_ru").add("item.occultism.book_of_calling" + ".message_set_deposit_entity", "%s будет передавать предметы в: %s");
        this.lang("ru_ru").add("item.occultism.book_of_calling" + ".message_set_extract", "%s будет извлекать из %s со стороны: %s");
        this.lang("ru_ru").add("item.occultism.book_of_calling" + ".message_set_base", "База для %s установлена на %s");
        this.lang("ru_ru").add("item.occultism.book_of_calling" + ".message_set_storage_controller", "%s теперь будет принимать заказы на изготовление из %s");
        this.lang("ru_ru").add("item.occultism.book_of_calling" + ".message_set_work_area_size", "%s теперь будет отслеживать рабочее место %s");
        this.lang("ru_ru").add("item.occultism.book_of_calling" + ".message_set_managed_machine", "Настройки для машины %s обновлены.");
        this.lang("ru_ru").add("item.occultism.book_of_calling" + ".message_set_managed_machine_extract_location", "%s теперь будет извлекать из %s со стороны: %s");
        this.lang("ru_ru").add("item.occultism.book_of_calling" + ".message_no_managed_machine", "Установите машину, прежде чем установить место извлечения %s");

        this.lang("ru_ru").add(OccultismItems.STABLE_WORMHOLE.get().getDescriptionId() + ".message.set_storage_controller", "Стабильная червоточина связана с Актуаторам хранилища.");
        this.lang("ru_ru").add(OccultismItems.STORAGE_REMOTE.get().getDescriptionId() + ".message.not_loaded", "Чанк для актуатора хранилища не загружен!");
        this.lang("ru_ru").add(OccultismItems.STORAGE_REMOTE.get().getDescriptionId() + ".message.linked", "Удалённое хранилище связано с актуатором.");
        this.lang("ru_ru").add(OccultismItems.DIVINATION_ROD.get().getDescriptionId() + ".message.no_linked_block", "Жезл прорицания не настроен на какой-либо материал.");
        this.lang("ru_ru").add(OccultismItems.DIVINATION_ROD.get().getDescriptionId() + ".message.linked_block", "Жезл прорицания настроен на %s");
        this.lang("ru_ru").add(OccultismItems.DIVINATION_ROD.get().getDescriptionId() + ".message.no_link_found", "Нет резонанса с этим блоком.");
        this.lang("ru_ru").add(OccultismItems.SOUL_GEM_ITEM.get().getDescriptionId() + ".message.entity_type_denied", "Камень души не может удерживать этот тип существа.");
    }

    public void addItemTooltips() {
        //"item\.occultism\.(.*?)\.(.*)": "(.*)",
        // this.add\(OccultismItems.\U\1\E.get\(\).getDescriptionId\(\)  + " \2", "\3"\);
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
        this.lang("ru_ru").add("item.occultism.book_of_calling_foliot" + ".tooltip.deposit", "Вводит в: %s.");
        this.lang("ru_ru").add("item.occultism.book_of_calling_foliot" + ".tooltip.deposit_entity", "Передаёт предметы в: %s.");
        this.lang("ru_ru").add("item.occultism.book_of_calling_djinni" + ".tooltip", "Джинн %s");
        this.lang("ru_ru").add("item.occultism.book_of_calling_djinni" + ".tooltip_dead", "%s покинул эту плоскость существования.");
        this.lang("ru_ru").add("item.occultism.book_of_calling_djinni" + ".tooltip.extract", "Извлекает из: %s.");
        this.lang("ru_ru").add("item.occultism.book_of_calling_djinni" + ".tooltip.deposit", "Вводит в: % s");
        this.lang("ru_ru").add(OccultismItems.FAMILIAR_RING.get().getDescriptionId() + ".tooltip", "Заключён с фамильяром: %s\n%s");
        this.lang("ru_ru").add(OccultismItems.FAMILIAR_RING.get().getDescriptionId() + ".tooltip.familiar_type", "[Тип: %s]");
        this.lang("ru_ru").add(OccultismItems.FAMILIAR_RING.get().getDescriptionId() + ".tooltip.empty", "Не содержит какого-либо фамильяра.");

        this.lang("ru_ru").add("item.minecraft.diamond_sword.occultism_spirit_tooltip", "%s связан с этим мечом. Пусть враги трепещут перед его тщеславием.");

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
        this.lang("ru_ru").add(OccultismItems.MINER_DEBUG_UNSPECIALIZED.get().getDescriptionId() + ".tooltip", "Отладочный рудокоп добывает разные блоки в шахтёрском измерении.");
        this.lang("ru_ru").add(OccultismItems.MINER_AFRIT_DEEPS.get().getDescriptionId() + ".tooltip", "%s добывает разные и глубинносланцевые руды в шахтёрском измерении.");
        this.lang("ru_ru").add(OccultismItems.MINER_MARID_MASTER.get().getDescriptionId() + ".tooltip", "%s добывает разные, глубинносланцевые и редкие руды в шахтёрском измерении.");
        this.lang("ru_ru").add(OccultismItems.SOUL_GEM_ITEM.get().getDescriptionId() + ".tooltip_filled", "Содержит пойманного %s.");
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

        this.addItem(OccultismItems.DEBUG_WAND, "Жезл отладки");
        this.addItem(OccultismItems.DEBUG_FOLIOT_LUMBERJACK, "Вызов отладочного Дровосека-Фолиота");
        this.addItem(OccultismItems.DEBUG_FOLIOT_TRANSPORT_ITEMS, "Вызов отладочного Транспортировщика-Фолиота");
        this.addItem(OccultismItems.DEBUG_FOLIOT_CLEANER, "Вызов отладочного Дворника-Фолиота");
        this.addItem(OccultismItems.DEBUG_FOLIOT_TRADER_ITEM, "Вызов отладочного Фолиота-Торговца");
        this.addItem(OccultismItems.DEBUG_DJINNI_MANAGE_MACHINE, "Вызов отладочного Машиниста-Джинна");
        this.addItem(OccultismItems.DEBUG_DJINNI_TEST, "Вызов отладочного тестового Джинна");

        this.addItem(OccultismItems.CHALK_GOLD, "Жёлтый мел");
        this.addItem(OccultismItems.CHALK_PURPLE, "Пурпурный мел");
        this.addItem(OccultismItems.CHALK_RED, "Красный мел");
        this.addItem(OccultismItems.CHALK_WHITE, "Белый мел");
        this.addItem(OccultismItems.CHALK_GOLD_IMPURE, "Осквернённый жёлтый мел");
        this.addItem(OccultismItems.CHALK_PURPLE_IMPURE, "Осквернённый пурпурный мел");
        this.addItem(OccultismItems.CHALK_RED_IMPURE, "Осквернённый красный мел");
        this.addItem(OccultismItems.CHALK_WHITE_IMPURE, "Осквернённый белый мел");
        this.addItem(OccultismItems.BRUSH, "Щётка для мела");
        this.addItem(OccultismItems.AFRIT_ESSENCE, "Сущность Африта");
        this.addItem(OccultismItems.PURIFIED_INK, "Очищенные чернила");
        this.addItem(OccultismItems.AWAKENED_FEATHER, "Пробуждённое перо");
        this.addItem(OccultismItems.TABOO_BOOK, "Книга табу");
        this.addItem(OccultismItems.BOOK_OF_BINDING_EMPTY, "Книга привязки: Пустая");
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
        this.addItem(OccultismItems.BOOK_OF_CALLING_DJINNI_MANAGE_MACHINE, "Книга вызова: Машинист-Джинна");
        this.addItem(OccultismItems.STORAGE_REMOTE, "Средство доступа хранилища");
        this.addItem(OccultismItems.STORAGE_REMOTE_INERT, "Инертное средство доступа хранилища");
        this.addItem(OccultismItems.DIMENSIONAL_MATRIX, "Кристалл пространственной матрицы");
        this.addItem(OccultismItems.DIVINATION_ROD, "Жезл прорицания");
        this.addItem(OccultismItems.DATURA_SEEDS, "Семена «Видение демона»");
        this.addAutoTooltip(OccultismItems.DATURA_SEEDS.get(), "Посадите, чтобы вырастить Плод «Видение демона».\nУпотребление обеспечивает возможность видеть за гробовой чертой... Возможно, в свою очередь, вызовет плохое самочувствие.");
        this.addItem(OccultismItems.DATURA, "Плод «Видение демона»");
        this.addAutoTooltip(OccultismItems.DATURA.get(), "Употребление обеспечивает возможность видеть за гробовой чертой... Возможно, в свою очередь, вызовет плохое самочувствие.");
        this.addItem(OccultismItems.DEMONS_DREAM_ESSENCE, "Эссенция «Видение демона»");
        this.addAutoTooltip(OccultismItems.DEMONS_DREAM_ESSENCE.get(), "Употребление позволяет видеть за гробовой чертой ... И полный набор других эффектов.");
        this.addItem(OccultismItems.OTHERWORLD_ESSENCE, "Потусторонняя эссенция");
        this.addAutoTooltip(OccultismItems.OTHERWORLD_ESSENCE.get(), "Очищенная эссенция «Видение демона». Больше не оказывает пагубных эффектов.");
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
        this.addItem(OccultismItems.LENSES, "Линзы");
        this.addItem(OccultismItems.INFUSED_LENSES, "Наполненные линзы");
        this.addItem(OccultismItems.LENS_FRAME, "Оправа для очков");
        this.addItem(OccultismItems.OTHERWORLD_GOGGLES, "Потусторонние очки");
        this.addItem(OccultismItems.INFUSED_PICKAXE, "Наполненная кирка");
        this.addItem(OccultismItems.SPIRIT_ATTUNED_PICKAXE_HEAD, "Головка кирки из самоцвета, настроенного на духа");
        this.addItem(OccultismItems.IESNIUM_PICKAXE, "Кирка из айзния");
        this.addItem(OccultismItems.MAGIC_LAMP_EMPTY, "Пустая магическая лампа");
        this.addItem(OccultismItems.MINER_FOLIOT_UNSPECIALIZED, "Рудокоп-Фолиот");
        this.addItem(OccultismItems.MINER_DJINNI_ORES, "Рудный Рудокоп-Джинн");
        this.addItem(OccultismItems.MINER_DEBUG_UNSPECIALIZED, "Отладочный рудокоп");
        this.addItem(OccultismItems.MINER_AFRIT_DEEPS, "Рудокоп-Африт для глубинносланцевой руды");
        this.addItem(OccultismItems.MINER_MARID_MASTER, "Мастер Рудокоп-Марид");
        this.addItem(OccultismItems.SOUL_GEM_ITEM, "Камень души");
        this.lang("ru_ru").add(OccultismItems.SOUL_GEM_ITEM.get().getDescriptionId() + "_empty", "Пустой камень души");
        this.addItem(OccultismItems.SOUL_SHARD_ITEM, "Осколок души");
        this.addItem(OccultismItems.SATCHEL, "Необычайно большая сумка");
        this.addItem(OccultismItems.FAMILIAR_RING, "Кольцо для фамильяра");
        this.addItem(OccultismItems.SPAWN_EGG_FOLIOT, "Яйцо призыва Фолиота");
        this.addItem(OccultismItems.SPAWN_EGG_DJINNI, "Яйцо призыва Джинна");
        this.addItem(OccultismItems.SPAWN_EGG_AFRIT, "Яйцо призыва Африта");
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
        this.addItem(OccultismItems.SPAWN_EGG_GREEDY_FAMILIAR, "Яйцо призыва Алчного фамильяра");
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
        this.addItem(OccultismItems.SPAWN_EGG_BEHOLDER_FAMILIAR, "Яйцо призыва Созерцателя-фамильяра");
        this.addItem(OccultismItems.SPAWN_EGG_FAIRY_FAMILIAR, "Яйцо призыва Феи-фамильяра");
        this.addItem(OccultismItems.SPAWN_EGG_MUMMY_FAMILIAR, "Яйцо призыва Мумии-фамильяра");
        this.addItem(OccultismItems.SPAWN_EGG_BEAVER_FAMILIAR, "Яйцо призыва Бобра-фамильяра");
        this.addItem(OccultismItems.SPAWN_EGG_PARROT_FAMILIAR, "Яйцо призыва Попугая-фамильяра");
        this.addItem(OccultismItems.SPAWN_EGG_DEMONIC_WIFE, "Яйцо призыва Демонической жены");
        this.addItem(OccultismItems.SPAWN_EGG_DEMONIC_HUSBAND, "Яйцо призыва Демонического мужа");
    }

    private void addBlocks() {
        //"block\.occultism\.(.*?)": "(.*)",
        //this.addBlock\(OccultismItems.\U\1\E, "\2"\);

        this.addBlock(OccultismBlocks.OTHERSTONE, "Потусторонний камень");
        this.addBlock(OccultismBlocks.OTHERSTONE_SLAB, "Плита из потустороннего камня");
        this.addBlock(OccultismBlocks.OTHERSTONE_PEDESTAL, "Потусторонний пьедестал");
        this.addBlock(OccultismBlocks.SACRIFICIAL_BOWL, "Жертвенная миска");
        this.addBlock(OccultismBlocks.GOLDEN_SACRIFICIAL_BOWL, "Золотая жертвенная миска");
        this.addBlock(OccultismBlocks.CHALK_GLYPH_WHITE, "Белый меловой глиф");
        this.addBlock(OccultismBlocks.CHALK_GLYPH_PURPLE, "Пурпурный меловой глиф");
        this.addBlock(OccultismBlocks.CHALK_GLYPH_RED, "Красный меловой глиф");
        this.addBlock(OccultismBlocks.STORAGE_CONTROLLER, "Актуатор пространственного хранилища");
        this.addBlock(OccultismBlocks.STORAGE_CONTROLLER_BASE, "Основа актуатора хранилища");
        this.addBlock(OccultismBlocks.STORAGE_STABILIZER_TIER1, "Стабилизатор пространственного хранилища 1-го уровня");
        this.addBlock(OccultismBlocks.STORAGE_STABILIZER_TIER2, "Стабилизатор пространственного хранилища 2-го уровня");
        this.addBlock(OccultismBlocks.STORAGE_STABILIZER_TIER3, "Стабилизатор пространственного хранилища 3-го уровня");
        this.addBlock(OccultismBlocks.STORAGE_STABILIZER_TIER4, "Стабилизатор пространственного хранилища 4-го уровня");
        this.addBlock(OccultismBlocks.STABLE_WORMHOLE, "Стабильная червоточина");
        this.addBlock(OccultismBlocks.DATURA, "Видение демона");

        this.lang("ru_ru").add("block.occultism.otherworld_sapling", "Потусторонний саженец");
        this.lang("ru_ru").add("block.occultism.otherworld_leaves", "Потусторонняя листья");
		this.lang("ru_ru").add("block.occultism.otherworld_log", "Потустороннее бревно");
        this.lang("ru_ru").add("block.occultism.otherworld_wood", "Потусторонняя древесина");
        this.lang("ru_ru").add("block.occultism.stripped_otherworld_log", "Обтёсанное потустороннее бревно");
        this.lang("ru_ru").add("block.occultism.stripped_otherworld_wood", "Обтёсанная потусторонняя древесина");
        this.lang("ru_ru").add("block.occultism.otherplanks", "Потусторонние доски");
        this.lang("ru_ru").add("block.occultism.otherplanks_stairs", "Ступеньки из потусторонних досок");
        this.lang("ru_ru").add("block.occultism.otherplanks_slab", "Плита из потусторонних досок");
        this.lang("ru_ru").add("block.occultism.otherplanks_fence", "Забор из потусторонних досок");
        this.lang("ru_ru").add("block.occultism.otherplanks_fence_gate", "Калитка из потусторонних досок");
        this.lang("ru_ru").add("block.occultism.otherplanks_door", "Дверь из потусторонних досок");
        this.lang("ru_ru").add("block.occultism.otherplanks_trapdoor", "Люк из потусторонних досок");
        this.lang("ru_ru").add("block.occultism.otherplanks_pressure_plate", "Нажимная плита из потусторонних досок");
        this.lang("ru_ru").add("block.occultism.otherplanks_button", "Кнопка из потусторонних досок");
        this.lang("ru_ru").add("block.occultism.otherplanks_sign", "Табличка из потусторонних досок");
        this.lang("ru_ru").add("block.occultism.otherplanks_hanging_sign", "Навесная табличка из потусторонних досок");

        this.addBlock(OccultismBlocks.SPIRIT_FIRE, "Духовный огонь");
        this.addBlock(OccultismBlocks.SPIRIT_ATTUNED_CRYSTAL, "Кристалл, настроенный на духа");
        this.addBlock(OccultismBlocks.SILVER_ORE, "Серебряная руда");
        this.addBlock(OccultismBlocks.SILVER_ORE_DEEPSLATE, "Сереброносная глубинносланцевая руда");
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
        this.addBlock(OccultismBlocks.SPIRIT_TORCH, "Духовный факел"); //Настенный духовный факел автоматически использует такой же перевод.
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
    }

    private void addMiscTranslations() {

        //"(.*?)": "(.*)",
        //this.lang("ru_ru").add\("\1", "\2"\);

        this.lang("ru_ru").add(TranslationKeys.MESSAGE_CONTAINER_ALREADY_OPEN, "Этот контейнер уже открыт другим игроком, ждите пока он его не закроет.");

        //Занятия
        this.lang("ru_ru").add("job.occultism.lumberjack", "Дровосек");
        this.lang("ru_ru").add("job.occultism.crush_tier1", "Медленный дробильщик");
        this.lang("ru_ru").add("job.occultism.crush_tier2", "Дробильщик");
        this.lang("ru_ru").add("job.occultism.crush_tier3", "Быстрый дробильщик");
        this.lang("ru_ru").add("job.occultism.crush_tier4", "Очень быстрый дробильщик");
        this.lang("ru_ru").add("job.occultism.manage_machine", "Машинист");
        this.lang("ru_ru").add("job.occultism.transport_items", "Транспортировщик");
        this.lang("ru_ru").add("job.occultism.cleaner", "Дворник");
        this.lang("ru_ru").add("job.occultism.trade_otherstone_t1", "Торговец потусторонним камнем");
        this.lang("ru_ru").add("job.occultism.trade_otherworld_saplings_t1", "Торговец потусторонними саженцами");
        this.lang("ru_ru").add("job.occultism.clear_weather", "Дух ясной погоды");
        this.lang("ru_ru").add("job.occultism.rain_weather", "Дух дождливой погоды");
        this.lang("ru_ru").add("job.occultism.thunder_weather", "Дух грозы");
        this.lang("ru_ru").add("job.occultism.day_time", "Дух рассвета");
        this.lang("ru_ru").add("job.occultism.night_time", "Дух сумерок");

        //Перечисление
        this.lang("ru_ru").add("enum.occultism.facing.up", "Верх");
        this.lang("ru_ru").add("enum.occultism.facing.down", "Низ");
        this.lang("ru_ru").add("enum.occultism.facing.north", "Север");
        this.lang("ru_ru").add("enum.occultism.facing.south", "Юг");
        this.lang("ru_ru").add("enum.occultism.facing.west", "Запад");
        this.lang("ru_ru").add("enum.occultism.facing.east", "Восток");
        this.lang("ru_ru").add("enum.occultism.book_of_calling.item_mode.set_deposit", "Установить ввод");
        this.lang("ru_ru").add("enum.occultism.book_of_calling.item_mode.set_extract", "Установить извлечение");
        this.lang("ru_ru").add("enum.occultism.book_of_calling.item_mode.set_base", "Установить место базы");
        this.lang("ru_ru").add("enum.occultism.book_of_calling.item_mode.set_storage_controller", "Установить актуатор хранилища");
        this.lang("ru_ru").add("enum.occultism.book_of_calling.item_mode.set_managed_machine", "Установить машину");
        this.lang("ru_ru").add("enum.occultism.work_area_size.small", "16х16");
        this.lang("ru_ru").add("enum.occultism.work_area_size.medium", "32х32");
        this.lang("ru_ru").add("enum.occultism.work_area_size.large", "64х64");

        //Сообщения отладки
        this.lang("ru_ru").add("debug.occultism.debug_wand.printed_glyphs", "Глифы отпечатаны.");
        this.lang("ru_ru").add("debug.occultism.debug_wand.glyphs_verified", "Глифы проверены.");
        this.lang("ru_ru").add("debug.occultism.debug_wand.glyphs_not_verified", "Глифы не проверены.");
        this.lang("ru_ru").add("debug.occultism.debug_wand.spirit_selected", "Выбран дух с идентификатором %s.");
        this.lang("ru_ru").add("debug.occultism.debug_wand.spirit_tamed", "Приручен дух с идентификатором %s.");
        this.lang("ru_ru").add("debug.occultism.debug_wand.deposit_selected", "Установить блок %s для ввода, сторона «%s»");
        this.lang("ru_ru").add("debug.occultism.debug_wand.no_spirit_selected", "Дух не выбран.");

        //Жертвы для ритуала
        this.lang("ru_ru").add("ritual.occultism.sacrifice.cows", "Корова");
        this.lang("ru_ru").add("ritual.occultism.sacrifice.bats", "Летучая мышь");
        this.lang("ru_ru").add("ritual.occultism.sacrifice.zombies", "Зомби");
        this.lang("ru_ru").add("ritual.occultism.sacrifice.parrots", "Попугай");
        this.lang("ru_ru").add("ritual.occultism.sacrifice.chicken", "Курица");
        this.lang("ru_ru").add("ritual.occultism.sacrifice.pigs", "Свиньи");
        this.lang("ru_ru").add("ritual.occultism.sacrifice.humans", "Крестьянин или игрок");
        this.lang("ru_ru").add("ritual.occultism.sacrifice.squid", "Спрут");
        this.lang("ru_ru").add("ritual.occultism.sacrifice.horses", "Лошадь");
        this.lang("ru_ru").add("ritual.occultism.sacrifice.sheep", "Овца");
        this.lang("ru_ru").add("ritual.occultism.sacrifice.llamas", "Лама");
        this.lang("ru_ru").add("ritual.occultism.sacrifice.snow_golem", "Снежный голем");
        this.lang("ru_ru").add("ritual.occultism.sacrifice.iron_golem", "Железный голем");
        this.lang("ru_ru").add("ritual.occultism.sacrifice.spiders", "Паук");
        this.lang("ru_ru").add("ritual.occultism.sacrifice.flying_passive", "Тихоня, летучая мышь, пчела или попугай");
        this.lang("ru_ru").add("ritual.occultism.sacrifice.cubemob", "Слизень или магмовый куб");
        this.lang("ru_ru").add("ritual.occultism.sacrifice.fish", "Любая рыба");
        this.lang("ru_ru").add("ritual.occultism.sacrifice.axolotls", "Аксолотль");
        this.lang("ru_ru").add("ritual.occultism.sacrifice.camel", "Верблюд");
        this.lang("ru_ru").add("ritual.occultism.sacrifice.dolphin", "Дельфин");
        this.lang("ru_ru").add("ritual.occultism.sacrifice.wolfs", "Волк");
        this.lang("ru_ru").add("ritual.occultism.sacrifice.ocelot", "Оцелот");
		this.lang("ru_ru").add("ritual.occultism.sacrifice.cats", "Кошка");
        this.lang("ru_ru").add("ritual.occultism.sacrifice.vex", "Вредина");
		this.lang("ru_ru").add("ritual.occultism.sacrifice.tadpoles", "Головастик");
		this.lang("ru_ru").add("ritual.occultism.sacrifice.allay", "Тихоня");
        this.lang("ru_ru").add("ritual.occultism.sacrifice.warden", "Хранитель");

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
        this.lang("ru_ru").add("occultism.subtitle.chalk", "Начерчение мелом");
        this.lang("ru_ru").add("occultism.subtitle.brush", "Чистка щёткой");
        this.lang("ru_ru").add("occultism.subtitle.start_ritual", "Начало ритуала");
        this.lang("ru_ru").add("occultism.subtitle.tuning_fork", "Звук камертона");
        this.lang("ru_ru").add("occultism.subtitle.crunching", "Размельчение");
        this.lang("ru_ru").add("occultism.subtitle.poof", "Вжух!");

        //Типы измерений

        this.add(Util.makeDescriptionId("dimension_type", BuiltinDimensionTypes.OVERWORLD.location()), "Надземный мир");
        this.add(Util.makeDescriptionId("dimension_type", BuiltinDimensionTypes.NETHER.location()), "Незер");
        this.add(Util.makeDescriptionId("dimension_type", BuiltinDimensionTypes.END.location()), "Энд");
    }

    private void addGuiTranslations() {
        this.lang("ru_ru").add("gui.occultism.book_of_calling.mode", "Режим");
        this.lang("ru_ru").add("gui.occultism.book_of_calling.work_area", "Рабочее место");
        this.lang("ru_ru").add("gui.occultism.book_of_calling.manage_machine.insert", "Сторона ввода");
        this.lang("ru_ru").add("gui.occultism.book_of_calling.manage_machine.extract", "Сторона извлечения");
        this.lang("ru_ru").add("gui.occultism.book_of_calling.manage_machine.custom_name", "Своё название");

        // Spirit GUI
        this.lang("ru_ru").add("gui.occultism.spirit.age", "Распад сущности: %d%%");
        this.lang("ru_ru").add("gui.occultism.spirit.job", "%s");

        // Spirit Transporter GUI
        this.lang("ru_ru").add("gui.occultism.spirit.transporter.filter_mode", "Режим фильтра");
        this.lang("ru_ru").add("gui.occultism.spirit.transporter.filter_mode.blacklist", "Чёрный список");
        this.lang("ru_ru").add("gui.occultism.spirit.transporter.filter_mode.whitelist", "Белый список");
        this.lang("ru_ru").add("gui.occultism.spirit.transporter.tag_filter", "Введите теги для фильтрации по символам разделения \";\".\nНапр.: \"forge:ores;*брёвна*\".\nИспользуйте \"*\" для соответствия любого символа, напр.: \"*руда*\" для соответствия тегов руд из любого мода. Для фильтрации предметов, префикс с идентификатором предмета \"item:\", напр.: \"item:minecraft:chest\".");

        // Storage Controller GUI
        this.lang("ru_ru").add("gui.occultism.storage_controller.space_info_label", "%d/%d");
        this.lang("ru_ru").add("gui.occultism.storage_controller.space_info_label_new", "Заполнено: %s%%");
        this.lang("ru_ru").add("gui.occultism.storage_controller.space_info_label_types", "Типов: %s%%");
        this.lang("ru_ru").add("gui.occultism.storage_controller.shift", "Зажмите Shift для получения дополнительной информации.");
        this.lang("ru_ru").add("gui.occultism.storage_controller.search.tooltip@", "Префикс @: поиск по идентификатору мода.");
        this.lang("ru_ru").add("gui.occultism.storage_controller.search.tooltip#", "Префикс #: поиск по подсказке предмета.");
        this.lang("ru_ru").add("gui.occultism.storage_controller.search.tooltip$", "Префикс $: поиск по тегу.");
        this.lang("ru_ru").add("gui.occultism.storage_controller.search.tooltip_rightclick", "Очистка текста с помощью ПКМ.");
        this.lang("ru_ru").add("gui.occultism.storage_controller.search.tooltip_clear", "Очистка поиска.");
        this.lang("ru_ru").add("gui.occultism.storage_controller.search.tooltip_jei_on", "Синхронизировать с JEI.");
        this.lang("ru_ru").add("gui.occultism.storage_controller.search.tooltip_jei_off", "Не синхронизировать с JEI.");
        this.lang("ru_ru").add("gui.occultism.storage_controller.search.tooltip_sort_type_amount", "Сортировать по количеству.");
        this.lang("ru_ru").add("gui.occultism.storage_controller.search.tooltip_sort_type_name", "Сортировать по названию предмета.");
        this.lang("ru_ru").add("gui.occultism.storage_controller.search.tooltip_sort_type_mod", "Сортировать по названию мода.");
        this.lang("ru_ru").add("gui.occultism.storage_controller.search.tooltip_sort_direction_down", "Сортировать по возрастанию.");
        this.lang("ru_ru").add("gui.occultism.storage_controller.search.tooltip_sort_direction_up", "Сортировать по убыванию.");
        this.lang("ru_ru").add("gui.occultism.storage_controller.search.machines.tooltip@", "Префикс @: поиск по идентификатору мода.");
        this.lang("ru_ru").add("gui.occultism.storage_controller.search.machines.tooltip_sort_type_amount", "Сортировать по расстоянию.");
        this.lang("ru_ru").add("gui.occultism.storage_controller.search.machines.tooltip_sort_type_name", "Сортировать по названию машины.");
        this.lang("ru_ru").add("gui.occultism.storage_controller.search.machines.tooltip_sort_type_mod", "Сортировать по названию мода.");
    }

    private void addRitualMessages() {
        this.lang("ru_ru").add("ritual.occultism.pentacle_help", "\u00a7lНедопустимый пентакль!\u00a7r\nВы было пытаетесь создать пентакль: %s? Отсутствуют:\n%s");
        this.lang("ru_ru").add("ritual.occultism.pentacle_help_at_glue", " на позиции ");
        this.lang("ru_ru").add("ritual.occultism.pentacle_help.no_pentacle", "\u00a7lПентакль не найден!\u00a7r\nКажется, Вы не начертили пентакль, или в Вашем пентакле отсутствуют важные элементы. Обратитесь в раздел \"Ритуалы\" справочника душ, необходимый пентакль будет отображён как гиперссылка над рецептом ритуала в странице ритуала.");
        this.lang("ru_ru").add("ritual.occultism.ritual_help", "\u00a7lНедопустимый ритуал!\u00a7r\nВы было пытались выполнить ритуал:: \"%s\"? Отсутствуют предметы:\n%s");
        this.lang("ru_ru").add("ritual.occultism.disabled", "Ритуал недоступен на этом сервере.");
        this.lang("ru_ru").add("ritual.occultism.does_not_exist", "\u00a7lНеизвестный ритуал\u00a7r. Убедитесь, что пентакли и ингредиенты расположены правильно. Если Вы до сих пор не достигли желаемого результата, присоединяйтесь к нашему Discord-серверу —> (https://discord.gg/trE4SHRXvb)");
        this.lang("ru_ru").add("ritual.occultism.book_not_bound", "\u00a7lНесвязанная книга вызова\u00a7r. Перед началом ритуала, Вы должны создать эту книгу с помощью Справочника душ, для связки её с духом.");

        this.lang("ru_ru").add("ritual.occultism.unknown.conditions", "Удовлетворены не все требования для этого ритуала.");
        this.lang("ru_ru").add("ritual.occultism.unknown.started", "Ритуал запущен.");
        this.lang("ru_ru").add("ritual.occultism.unknown.finished", "Ритуал полностью завершился.");
        this.lang("ru_ru").add("ritual.occultism.unknown.interrupted", "Ритуал прерван.");

        this.add("ritual.occultism.debug.conditions", "Удовлетворены не все требования для этого ритуала.");
        this.add("ritual.occultism.debug.started", "Ритуал запущен.");
        this.add("ritual.occultism.debug.finished", "Ритуал полностью завершился.");
        this.add("ritual.occultism.debug.interrupted", "Ритуал прерван.");
        this.lang("ru_ru").add("ritual.occultism.summon_foliot_lumberjack.conditions", "Удовлетворены не все требования для этого ритуала.");
        this.lang("ru_ru").add("ritual.occultism.summon_foliot_lumberjack.started", "Начался вызов Дровосека-Фолиота.");
        this.lang("ru_ru").add("ritual.occultism.summon_foliot_lumberjack.finished", "Дровосек-Фолиот успешно вызван.");
        this.lang("ru_ru").add("ritual.occultism.summon_foliot_lumberjack.interrupted", "Вызов Дровосека-Фолиота прерван.");
        this.lang("ru_ru").add("ritual.occultism.summon_foliot_transport_items.conditions", "Удовлетворены не все требования для этого ритуала.");
        this.lang("ru_ru").add("ritual.occultism.summon_foliot_transport_items.started", "Начался вызов Транспортировщика-Фолиота.");
        this.lang("ru_ru").add("ritual.occultism.summon_foliot_transport_items.finished", "Транспортировщик-Фолиот успешно вызван.");
        this.lang("ru_ru").add("ritual.occultism.summon_foliot_transport_items.interrupted", "Вызов Транспортировщика-Фолиота прерван.");
        this.lang("ru_ru").add("ritual.occultism.summon_foliot_cleaner.conditions", "Удовлетворены не все требования для этого ритуала.");
        this.lang("ru_ru").add("ritual.occultism.summon_foliot_cleaner.started", "Начался вызов Дворника-Фолиота.");
        this.lang("ru_ru").add("ritual.occultism.summon_foliot_cleaner.finished", "Дворник-Фолиот успешно вызван.");
        this.lang("ru_ru").add("ritual.occultism.summon_foliot_cleaner.interrupted", "Вызов Дворника-Фолиота прерван.");
        this.lang("ru_ru").add("ritual.occultism.summon_foliot_crusher.conditions", "Удовлетворены не все требования для этого ритуала.");
        this.lang("ru_ru").add("ritual.occultism.summon_foliot_crusher.started", "Начался вызов Фолиота-Дробильщика руды.");
        this.lang("ru_ru").add("ritual.occultism.summon_foliot_crusher.finished", "Фолиот-Дробильщик руды успешно вызван.");
        this.lang("ru_ru").add("ritual.occultism.summon_foliot_crusher.interrupted", "Вызов Фолиота-Дробильщика руды прерван.");
        this.lang("ru_ru").add("ritual.occultism.summon_djinni_crusher.conditions", "Удовлетворены не все требования для этого ритуала.");
        this.lang("ru_ru").add("ritual.occultism.summon_djinni_crusher.started", "Начался вызов Джинна-Дробильщика руды.");
        this.lang("ru_ru").add("ritual.occultism.summon_djinni_crusher.finished", "Джинн-Дробильщик руды успешно вызван.");
        this.lang("ru_ru").add("ritual.occultism.summon_djinni_crusher.interrupted", "Вызов Джинна-Дробильщика руды прерван.");
        this.lang("ru_ru").add("ritual.occultism.summon_afrit_crusher.conditions", "Удовлетворены не все требования для этого ритуала.");
        this.lang("ru_ru").add("ritual.occultism.summon_afrit_crusher.started", "Начался вызов Африта-Дробильщика руды.");
        this.lang("ru_ru").add("ritual.occultism.summon_afrit_crusher.finished", "Африт-Дробильщика руды успешно вызван.");
        this.lang("ru_ru").add("ritual.occultism.summon_afrit_crusher.interrupted", "Вызов Африта-Дробильщика руды прерван.");
        this.lang("ru_ru").add("ritual.occultism.summon_marid_crusher.conditions", "Удовлетворены не все требования для этого ритуала.");
        this.lang("ru_ru").add("ritual.occultism.summon_marid_crusher.started", "Начался вызов Марида-Дробильщика руды.");
        this.lang("ru_ru").add("ritual.occultism.summon_marid_crusher.finished", "Марида-Дробильщика руды успешно вызван.");
        this.lang("ru_ru").add("ritual.occultism.summon_marid_crusher.interrupted", "Вызов Марида-Дробильщика руды прерван.");
        this.lang("ru_ru").add("ritual.occultism.summon_foliot_sapling_trader.conditions", "Удовлетворены не все требования для этого ритуала.");
        this.lang("ru_ru").add("ritual.occultism.summon_foliot_sapling_trader.started", "Начался вызов Торговца-Фолиота потусторонними саженцами.");
        this.lang("ru_ru").add("ritual.occultism.summon_foliot_sapling_trader.finished", "Торговец-Фолиот потусторонними саженцами успешно вызван.");
        this.lang("ru_ru").add("ritual.occultism.summon_foliot_sapling_trader.interrupted", "Вызов Торговца-Фолиота потусторонними саженцами прерван.");
        this.lang("ru_ru").add("ritual.occultism.summon_foliot_otherstone_trader.conditions", "Удовлетворены не все требования для этого ритуала.");
        this.lang("ru_ru").add("ritual.occultism.summon_foliot_otherstone_trader.started", "Начался вызов Торговца-Фолиота потустороннем камнем.");
        this.lang("ru_ru").add("ritual.occultism.summon_foliot_otherstone_trader.finished", "Торговец-Фолиот потустороннем камнем успешно вызван.");
        this.lang("ru_ru").add("ritual.occultism.summon_foliot_otherstone_trader.interrupted", "Вызов Торговца-Фолиота потустороннем камнем прерван.");
        this.lang("ru_ru").add("ritual.occultism.summon_djinni_manage_machine.conditions", "Удовлетворены не все требования для этого ритуала.");
        this.lang("ru_ru").add("ritual.occultism.summon_djinni_manage_machine.started", "Начался вызов Машиниста-Джинна.");
        this.lang("ru_ru").add("ritual.occultism.summon_djinni_manage_machine.finished", "Машинист-Джинн успешно вызван.");
        this.lang("ru_ru").add("ritual.occultism.summon_djinni_manage_machine.interrupted", "Вызов Машиниста-Джинна прерван.");
        this.lang("ru_ru").add("ritual.occultism.summon_djinni_clear_weather.conditions", "Удовлетворены не все требования для этого ритуала.");
        this.lang("ru_ru").add("ritual.occultism.summon_djinni_clear_weather.started", "Начался вызов Джинна для ясной погоды.");
        this.lang("ru_ru").add("ritual.occultism.summon_djinni_clear_weather.finished", "Джинн успешно вызван.");
        this.lang("ru_ru").add("ritual.occultism.summon_djinni_clear_weather.interrupted", "Вызов Джинна прерван.");
        this.lang("ru_ru").add("ritual.occultism.summon_djinni_day_time.conditions", "Удовлетворены не все требования для этого ритуала.");
        this.lang("ru_ru").add("ritual.occultism.summon_djinni_day_time.started", "Начался вызов Джинна с целью установки времени на день.");
        this.lang("ru_ru").add("ritual.occultism.summon_djinni_day_time.finished", "Джинн успешно вызван.");
        this.lang("ru_ru").add("ritual.occultism.summon_djinni_day_time.interrupted", "Вызов джинна прерван.");
        this.lang("ru_ru").add("ritual.occultism.summon_djinni_night_time.conditions", "Удовлетворены не все требования для этого ритуала.");
        this.lang("ru_ru").add("ritual.occultism.summon_djinni_night_time.started", "Начался вызов Джинна с целью установки времени на ночь.");
        this.lang("ru_ru").add("ritual.occultism.summon_djinni_night_time.finished", "Джинн успешно вызван.");
        this.lang("ru_ru").add("ritual.occultism.summon_djinni_night_time.interrupted", "Вызов джинна прерван.");
        this.lang("ru_ru").add("ritual.occultism.summon_afrit_rain_weather.conditions", "Удовлетворены не все требования для этого ритуала.");
        this.lang("ru_ru").add("ritual.occultism.summon_afrit_rain_weather.started", "Начался вызов Африта для дождливой погоды.");
        this.lang("ru_ru").add("ritual.occultism.summon_afrit_rain_weather.finished", "Джинн успешно вызван.");
        this.lang("ru_ru").add("ritual.occultism.summon_afrit_rain_weather.interrupted", "Вызов Джинна прерван.");
        this.lang("ru_ru").add("ritual.occultism.summon_afrit_thunder_weather.conditions", "Удовлетворены не все требования для этого ритуала.");
        this.lang("ru_ru").add("ritual.occultism.summon_afrit_thunder_weather.started", "Начался вызов Африта для грозы.");
        this.lang("ru_ru").add("ritual.occultism.summon_afrit_thunder_weather.finished", "Африт успешно вызван.");
        this.lang("ru_ru").add("ritual.occultism.summon_afrit_thunder_weather.interrupted", "Вызов Африта прерван.");
        this.lang("ru_ru").add("ritual.occultism.summon_unbound_afrit.conditions", "Удовлетворены не все требования для этого ритуала.");
        this.lang("ru_ru").add("ritual.occultism.summon_unbound_afrit.started", "Начался вызов несвязанного Африта.");
        this.lang("ru_ru").add("ritual.occultism.summon_unbound_afrit.finished", "Несвязанный Африт успешно вызван.");
        this.lang("ru_ru").add("ritual.occultism.summon_unbound_afrit.interrupted", "Вызов несвязанного Африта прерван.");
		this.lang("ru_ru").add("ritual.occultism.summon_unbound_marid.conditions", "Удовлетворены не все требования для этого ритуала.");
        this.lang("ru_ru").add("ritual.occultism.summon_unbound_marid.started", "Начался вызов несвязанного Марида.");
        this.lang("ru_ru").add("ritual.occultism.summon_unbound_marid.finished", "Несвязанный Марид успешно вызван.");
        this.lang("ru_ru").add("ritual.occultism.summon_unbound_marid.interrupted", "Вызов несвязанного Марида прерван.");
        this.lang("ru_ru").add("ritual.occultism.summon_wild_hunt.conditions", "Удовлетворены не все требования для этого ритуала.");
        this.lang("ru_ru").add("ritual.occultism.summon_wild_hunt.started", "Начался вызов Дикой Охоты.");
        this.lang("ru_ru").add("ritual.occultism.summon_wild_hunt.finished", "Дикая Охота успешно вызвана.");
        this.lang("ru_ru").add("ritual.occultism.summon_wild_hunt.interrupted", "Вызов Дикой Охоты прерван.");
        this.lang("ru_ru").add("ritual.occultism.craft_dimensional_matrix.conditions", "Удовлетворены не все требования для этого ритуала.");
        this.lang("ru_ru").add("ritual.occultism.craft_dimensional_matrix.started", "Началось заточение Джинна в пространственную матрицу.");
        this.lang("ru_ru").add("ritual.occultism.craft_dimensional_matrix.finished", "Успешно заточил Джинна в пространственную матрицу.");
        this.lang("ru_ru").add("ritual.occultism.craft_dimensional_matrix.interrupted", "Заточение Джинна прервано.");
        this.lang("ru_ru").add("ritual.occultism.craft_dimensional_mineshaft.conditions", "Удовлетворены не все требования для этого ритуала.");
        this.lang("ru_ru").add("ritual.occultism.craft_dimensional_mineshaft.started", "Началось заточение Джинна в пространственную шахту.");
        this.lang("ru_ru").add("ritual.occultism.craft_dimensional_mineshaft.finished", "Успешно заточил Джинна в пространственную шахту.");
        this.lang("ru_ru").add("ritual.occultism.craft_dimensional_mineshaft.interrupted", "Заточение Джинна прервано.");
        this.lang("ru_ru").add("ritual.occultism.craft_storage_controller_base.conditions", "Удовлетворены не все требования для этого ритуала.");
        this.lang("ru_ru").add("ritual.occultism.craft_storage_controller_base.started", "Началось заточение Фолиота в основу актуатора хранилища.");
        this.lang("ru_ru").add("ritual.occultism.craft_storage_controller_base.finished", "Успешно заточил Фолиота в основу актуатора хранилища.");
        this.lang("ru_ru").add("ritual.occultism.craft_storage_controller_base.interrupted", "Заточение Фолиота прервано.");
        this.lang("ru_ru").add("ritual.occultism.craft_stabilizer_tier1.conditions", "Удовлетворены не все требования для этого ритуала.");
        this.lang("ru_ru").add("ritual.occultism.craft_stabilizer_tier1.started", "Началось заточение Фолиота в стабилизатор хранилища.");
        this.lang("ru_ru").add("ritual.occultism.craft_stabilizer_tier1.finished", "Успешно заточил Фолиота в стабилизатор хранилища.");
        this.lang("ru_ru").add("ritual.occultism.craft_stabilizer_tier1.interrupted", "Заточение Фолиота прервано.");
        this.lang("ru_ru").add("ritual.occultism.craft_stabilizer_tier2.conditions", "Удовлетворены не все требования для этого ритуала.");
        this.lang("ru_ru").add("ritual.occultism.craft_stabilizer_tier2.started", "Началось заточение Джинна в стабилизатор хранилища.");
        this.lang("ru_ru").add("ritual.occultism.craft_stabilizer_tier2.finished", "Успешно заточил Джинна в стабилизатор хранилища.");
        this.lang("ru_ru").add("ritual.occultism.craft_stabilizer_tier2.interrupted", "Заточение Джинна прервано.");
        this.lang("ru_ru").add("ritual.occultism.craft_stabilizer_tier3.conditions", "Удовлетворены не все требования для этого ритуала.");
        this.lang("ru_ru").add("ritual.occultism.craft_stabilizer_tier3.started", "Началось заточение Африта в стабилизатор хранилища.");
        this.lang("ru_ru").add("ritual.occultism.craft_stabilizer_tier3.finished", "Успешно заточил Африта в стабилизатор хранилища.");
        this.lang("ru_ru").add("ritual.occultism.craft_stabilizer_tier3.interrupted", "Заточение Африта прервано.");
        this.lang("ru_ru").add("ritual.occultism.craft_stabilizer_tier4.conditions", "Удовлетворены не все требования для этого ритуала.");
        this.lang("ru_ru").add("ritual.occultism.craft_stabilizer_tier4.started", "Началось заточение Марида в стабилизатор хранилища.");
        this.lang("ru_ru").add("ritual.occultism.craft_stabilizer_tier4.finished", "Успешно заточил Марида в стабилизатор хранилища.");
        this.lang("ru_ru").add("ritual.occultism.craft_stabilizer_tier4.interrupted", "Заточение Марида прервано.");
        this.lang("ru_ru").add("ritual.occultism.craft_stable_wormhole.conditions", "Удовлетворены не все требования для этого ритуала.");
        this.lang("ru_ru").add("ritual.occultism.craft_stable_wormhole.started", "Началось заточение Фолиота в каркас червоточины.");
        this.lang("ru_ru").add("ritual.occultism.craft_stable_wormhole.finished", "Успешно заточил Фолиота в каркас червоточины.");
        this.lang("ru_ru").add("ritual.occultism.craft_stable_wormhole.interrupted", "Заточение Фолиота прервано.");
        this.lang("ru_ru").add("ritual.occultism.craft_storage_remote.conditions", "Удовлетворены не все требования для этого ритуала.");
        this.lang("ru_ru").add("ritual.occultism.craft_storage_remote.started", "Началось заточение Джинна в удалённое хранилище.");
        this.lang("ru_ru").add("ritual.occultism.craft_storage_remote.finished", "Успешно заточил Джинна в удалённое хранилище.");
        this.lang("ru_ru").add("ritual.occultism.craft_storage_remote.interrupted", "Заточение Джинна прервано.");
        this.lang("ru_ru").add("ritual.occultism.craft_infused_lenses.conditions", "Удовлетворены не все требования для этого ритуала.");
        this.lang("ru_ru").add("ritual.occultism.craft_infused_lenses.started", "Началось заточение Фолиота в линзы.");
        this.lang("ru_ru").add("ritual.occultism.craft_infused_lenses.finished", "Успешно заточил Фолиота в линзы.");
        this.lang("ru_ru").add("ritual.occultism.craft_infused_lenses.interrupted", "Заточение Фолиота прервано.");
        this.lang("ru_ru").add("ritual.occultism.craft_infused_pickaxe.conditions", "Удовлетворены не все требования для этого ритуала.");
        this.lang("ru_ru").add("ritual.occultism.craft_infused_pickaxe.started", "Началось заточение Джинна в кирку.");
        this.lang("ru_ru").add("ritual.occultism.craft_infused_pickaxe.finished", "Успешно заточил Джинна в кирку.");
        this.lang("ru_ru").add("ritual.occultism.craft_infused_pickaxe.interrupted", "Заточение Джинна прервано.");

        this.lang("ru_ru").add("ritual.occultism.craft_miner_foliot_unspecialized.conditions", "Удовлетворены не все требования для этого ритуала.");
        this.lang("ru_ru").add("ritual.occultism.craft_miner_foliot_unspecialized.started", "Начался вызов Фолиота в магическую лампу");
        this.lang("ru_ru").add("ritual.occultism.craft_miner_foliot_unspecialized.finished", "Фолиот успешно вызван в магическую лампу.");
        this.lang("ru_ru").add("ritual.occultism.craft_miner_foliot_unspecialized.interrupted", "Вызов Фолиота прерван.");

        this.lang("ru_ru").add("ritual.occultism.craft_miner_djinni_ores.conditions", "Удовлетворены не все требования для этого ритуала.");
        this.lang("ru_ru").add("ritual.occultism.craft_miner_djinni_ores.started", "Начался вызов Джинна в магическую лампу");
        this.lang("ru_ru").add("ritual.occultism.craft_miner_djinni_ores.finished", "Джинн успешно вызван в магическую лампу.");
        this.lang("ru_ru").add("ritual.occultism.craft_miner_djinni_ores.interrupted", "Вызов Джинна прерван.");

        this.lang("ru_ru").add("ritual.occultism.craft_miner_afrit_deeps.conditions", "Удовлетворены не все требования для этого ритуала.");
        this.lang("ru_ru").add("ritual.occultism.craft_miner_afrit_deeps.started", "Начался вызов Африта в магическую лампу");
        this.lang("ru_ru").add("ritual.occultism.craft_miner_afrit_deeps.finished", "Африт успешно вызван в магическую лампу");
        this.lang("ru_ru").add("ritual.occultism.craft_miner_afrit_deeps.interrupted", "Вызов Африта прерван.");

        this.lang("ru_ru").add("ritual.occultism.craft_miner_marid_master.conditions", "Удовлетворены не все требования для этого ритуала.");
        this.lang("ru_ru").add("ritual.occultism.craft_miner_marid_master.started", "Начался вызов Марида в магическую лампу");
        this.lang("ru_ru").add("ritual.occultism.craft_miner_marid_master.finished", "Марид успешно вызван в магическую лампу.");
        this.lang("ru_ru").add("ritual.occultism.craft_miner_marid_master.interrupted", "Вызов Марида прерван.");
		
		this.lang("ru_ru").add("ritual.occultism.craft_miner_ancient_eldritch.conditions", "Удовлетворены не все требования для этого ритуала.");
        this.lang("ru_ru").add("ritual.occultism.craft_miner_ancient_eldritch.started", "Успешно вызвано нечто в магическую лампу.");
        this.lang("ru_ru").add("ritual.occultism.craft_miner_ancient_eldritch.finished", "Успешно вызвано нечто в магическую лампу.");
        this.lang("ru_ru").add("ritual.occultism.craft_miner_ancient_eldritch.interrupted", "Вызов нечто прерван.");

        this.lang("ru_ru").add("ritual.occultism.craft_satchel.conditions", "Удовлетворены не все требования для этого ритуала.");
        this.lang("ru_ru").add("ritual.occultism.craft_satchel.started", "Началось заточение Фолиота в сумку.");
        this.lang("ru_ru").add("ritual.occultism.craft_satchel.finished", "Успешно заточил Фолиота в сумку.");
        this.lang("ru_ru").add("ritual.occultism.craft_satchel.interrupted", "Заточение Фолиота прервано.");
        this.lang("ru_ru").add("ritual.occultism.craft_soul_gem.conditions", "Удовлетворены не все требования для этого ритуала.");
        this.lang("ru_ru").add("ritual.occultism.craft_soul_gem.started", "Началось заточение Джинна в камень души.");
        this.lang("ru_ru").add("ritual.occultism.craft_soul_gem.finished", "Успешно заточил Джинна в камень души.");
        this.lang("ru_ru").add("ritual.occultism.craft_soul_gem.interrupted", "Заточение Джинна прервано.");
        this.lang("ru_ru").add("ritual.occultism.craft_familiar_ring.conditions", "Удовлетворены не все требования для этого ритуала.");
        this.lang("ru_ru").add("ritual.occultism.craft_familiar_ring.started", "Началось заточение Джинна в Кольцо для фамильяра.");
        this.lang("ru_ru").add("ritual.occultism.craft_familiar_ring.finished", "Успешно заточил Джинна в Кольцо для фамильяра.");
        this.lang("ru_ru").add("ritual.occultism.craft_familiar_ring.interrupted", "Заключение Джинна прервано.");
        this.lang("ru_ru").add("ritual.occultism.craft_wild_trim.conditions", "Удовлетворены не все требования для этого ритуала.");
        this.lang("ru_ru").add("ritual.occultism.craft_wild_trim.started", "Дикие духи начали ковать Кузнечный шаблон.");
        this.lang("ru_ru").add("ritual.occultism.craft_wild_trim.finished", "Успешно выковал Кузнечный шаблон.");
        this.lang("ru_ru").add("ritual.occultism.craft_wild_trim.interrupted", "Заточение Джинна прервано.");
        this.lang("ru_ru").add("ritual.occultism.possess_endermite.conditions", "Удовлетворены не все требования для этого ритуала.");
        this.lang("ru_ru").add("ritual.occultism.possess_endermite.started", "Начался вызов одержимого эндермита.");
        this.lang("ru_ru").add("ritual.occultism.possess_endermite.finished", "Одержимый эндермита успешно вызван.");
        this.lang("ru_ru").add("ritual.occultism.possess_endermite.interrupted", "Вызов одержимого эндермита прерван.");
        this.lang("ru_ru").add("ritual.occultism.possess_skeleton.conditions", "Удовлетворены не все требования для этого ритуала.");
        this.lang("ru_ru").add("ritual.occultism.possess_skeleton.started", "Начался вызов одержимого скелета.");
        this.lang("ru_ru").add("ritual.occultism.possess_skeleton.finished", "Одержимый скелет успешно вызван.");
        this.lang("ru_ru").add("ritual.occultism.possess_skeleton.interrupted", "Вызов одержимого скелета прерван.");
        this.lang("ru_ru").add("ritual.occultism.possess_enderman.conditions", "Удовлетворены не все требования для этого ритуала.");
        this.lang("ru_ru").add("ritual.occultism.possess_enderman.started", "Начался вызов одержимого эндермена.");
        this.lang("ru_ru").add("ritual.occultism.possess_enderman.finished", "Одержимый эндермен успешно вызван.");
        this.lang("ru_ru").add("ritual.occultism.possess_enderman.interrupted", "Вызов одержимого эндермена прерван.");
        this.lang("ru_ru").add("ritual.occultism.possess_ghast.conditions", "Удовлетворены не все требования для этого ритуала.");
        this.lang("ru_ru").add("ritual.occultism.possess_ghast.started", "Начался вызов одержимого гаста.");
        this.lang("ru_ru").add("ritual.occultism.possess_ghast.finished", "Одержимый гаст успешно вызван.");
        this.lang("ru_ru").add("ritual.occultism.possess_ghast.interrupted", "Вызов одержимого гаста прерван.");
        this.lang("ru_ru").add("ritual.occultism.possess_phantom.conditions", "Удовлетворены не все требования для этого ритуала.");
        this.lang("ru_ru").add("ritual.occultism.possess_phantom.started", "Начался вызов одержимого фантома.");
        this.lang("ru_ru").add("ritual.occultism.possess_phantom.finished", "Одержимый фантом успешно вызван.");
        this.lang("ru_ru").add("ritual.occultism.possess_phantom.interrupted", "Вызов одержимого фантома прерван.");
        this.lang("ru_ru").add("ritual.occultism.possess_weak_shulker.conditions", "Удовлетворены не все требования для этого ритуала.");
        this.lang("ru_ru").add("ritual.occultism.possess_weak_shulker.started", "Начался вызов одержимого слабого шалкера.");
        this.lang("ru_ru").add("ritual.occultism.possess_weak_shulker.finished", "Одержимый слабый шалкер успешно вызван.");
        this.lang("ru_ru").add("ritual.occultism.possess_weak_shulker.interrupted", "Вызов одержимого слабого шалкера прерван.");
        this.lang("ru_ru").add("ritual.occultism.possess_shulker.conditions", "Удовлетворены не все требования для этого ритуала.");
        this.lang("ru_ru").add("ritual.occultism.possess_shulker.started", "Начался вызов одержимого шалкера.");
        this.lang("ru_ru").add("ritual.occultism.possess_shulker.finished", "Одержимый шалкер успешно вызван.");
        this.lang("ru_ru").add("ritual.occultism.possess_shulker.interrupted", "Вызов одержимого шалкера прерван.");
        this.lang("ru_ru").add("ritual.occultism.possess_elder_guardian.conditions", "Удовлетворены не все требования для этого ритуала.");
        this.lang("ru_ru").add("ritual.occultism.possess_elder_guardian.started", "Начался вызов одержимого древнего стража.");
        this.lang("ru_ru").add("ritual.occultism.possess_elder_guardian.finished", "Одержимый древний страж успешно вызван.");
        this.lang("ru_ru").add("ritual.occultism.possess_elder_guardian.interrupted", "Вызов одержимого древнего стража прерван.");
        this.lang("ru_ru").add("ritual.occultism.possess_warden.conditions", "Удовлетворены не все требования для этого ритуала.");
        this.lang("ru_ru").add("ritual.occultism.possess_warden.started", "Начался вызов одержимого хранителя.");
        this.lang("ru_ru").add("ritual.occultism.possess_warden.finished", "Одержимый хранитель успешно вызван.");
        this.lang("ru_ru").add("ritual.occultism.possess_warden.interrupted", "Вызов одержимого хранителя прерван.");
        this.lang("ru_ru").add("ritual.occultism.possess_hoglin.conditions", "Удовлетворены не все требования для этого ритуала.");
        this.lang("ru_ru").add("ritual.occultism.possess_hoglin.started", "Начался вызов одержимого хоглина.");
        this.lang("ru_ru").add("ritual.occultism.possess_hoglin.finished", "Одержимый хоглин успешно вызван.");
        this.lang("ru_ru").add("ritual.occultism.possess_hoglin.interrupted", "Вызов одержимого хоглина прерван.");
		this.lang("ru_ru").add("ritual.occultism.possess_witch.conditions", "Удовлетворены не все требования для этого ритуала.");
        this.lang("ru_ru").add("ritual.occultism.possess_witch.started", "Начался вызон одержимой ведьмы.");
        this.lang("ru_ru").add("ritual.occultism.possess_witch.finished", "Одержимая ведьма успешно вызвана.");
        this.lang("ru_ru").add("ritual.occultism.possess_witch.interrupted", "Вызов одержимой ведьмы прерван.");
        this.lang("ru_ru").add("ritual.occultism.possess_zombie_piglin.conditions", "Удовлетворены не все требования для этого ритуала.");
        this.lang("ru_ru").add("ritual.occultism.possess_zombie_piglin.started", "Начался вызов одержимого зомби-пиглина.");
        this.lang("ru_ru").add("ritual.occultism.possess_zombie_piglin.finished", "Одержимый зомби-пиглин успешно вызван.");
        this.lang("ru_ru").add("ritual.occultism.possess_zombie_piglin.interrupted", "Вызов одержимого зомби-пиглина прерван.");
        this.lang("ru_ru").add("ritual.occultism.possess_bee.conditions", "Удовлетворены не все требования для этого ритуала.");
        this.lang("ru_ru").add("ritual.occultism.possess_bee.started", "Начался вызов одержимой пчелы.");
        this.lang("ru_ru").add("ritual.occultism.possess_bee.finished", "Одержимая пчела успешно вызвана.");
        this.lang("ru_ru").add("ritual.occultism.possess_bee.interrupted", "Вызов одержимой пчелы прерван.");
        this.lang("ru_ru").add("ritual.occultism.possess_goat.conditions", "Удовлетворены не все требования для этого ритуала.");
        this.lang("ru_ru").add("ritual.occultism.possess_goat.started", "Начался вызов козла милосердияmercy.");
        this.lang("ru_ru").add("ritual.occultism.possess_goat.finished", "Козёл милосердия успешно вызван.");
        this.lang("ru_ru").add("ritual.occultism.possess_goat.interrupted", "Вызов козы милосердия прерван.");
        this.lang("ru_ru").add("ritual.occultism.familiar_otherworld_bird.conditions", "Удовлетворены не все требования для этого ритуала.");
        this.lang("ru_ru").add("ritual.occultism.familiar_otherworld_bird.started", "Начался вызов дрикрыла-фамильяр.");
        this.lang("ru_ru").add("ritual.occultism.familiar_otherworld_bird.finished", "Дрикрыл-фамильяр успешно вызван.");
        this.lang("ru_ru").add("ritual.occultism.familiar_otherworld_bird.interrupted", "Вызов дриклыра-фамильяра прерван.");
        this.lang("ru_ru").add("ritual.occultism.familiar_cthulhu.conditions", "Удовлетворены не все требования для этого ритуала.");
        this.lang("ru_ru").add("ritual.occultism.familiar_cthulhu.started", "Начался вызов ктулху-фамильяра.");
        this.lang("ru_ru").add("ritual.occultism.familiar_cthulhu.finished", "Ктулху-фамильяр успешно вызван.");
        this.lang("ru_ru").add("ritual.occultism.familiar_cthulhu.interrupted", "Вызов ктулху-фамильяра прерван.");
        this.lang("ru_ru").add("ritual.occultism.familiar_devil.conditions", "Удовлетворены не все требования для этого ритуала.");
        this.lang("ru_ru").add("ritual.occultism.familiar_devil.started", "Начался вызов дьявола-фамильяра.");
        this.lang("ru_ru").add("ritual.occultism.familiar_devil.finished", "Дьявол-фамильяр успешно вызван.");
        this.lang("ru_ru").add("ritual.occultism.familiar_devil.interrupted", "Вызов дьявола-фамильяра прерван.");
        this.lang("ru_ru").add("ritual.occultism.familiar_dragon.conditions", "Удовлетворены не все требования для этого ритуала.");
        this.lang("ru_ru").add("ritual.occultism.familiar_dragon.started", "Начался вызов дракона-фамильяра.");
        this.lang("ru_ru").add("ritual.occultism.familiar_dragon.finished", "Дракон-фамильяр успешно вызван.");
        this.lang("ru_ru").add("ritual.occultism.familiar_dragon.interrupted", "Вызов дракона-фамильяра прерван.");
        this.lang("ru_ru").add("ritual.occultism.familiar_blacksmith.conditions", "Удовлетворены не все требования для этого ритуала.");
        this.lang("ru_ru").add("ritual.occultism.familiar_blacksmith.started", "Начался вызов кузнеца-фамильяра.");
        this.lang("ru_ru").add("ritual.occultism.familiar_blacksmith.finished", "Кузнец-фамильяр успешно вызван.");
        this.lang("ru_ru").add("ritual.occultism.familiar_blacksmith.interrupted", "Вызов кузнеца-фамильяра прерван.");
        this.lang("ru_ru").add("ritual.occultism.familiar_guardian.conditions", "Удовлетворены не все требования для этого ритуала.");
        this.lang("ru_ru").add("ritual.occultism.familiar_guardian.started", "Начался вызов стража-фамильяр.");
        this.lang("ru_ru").add("ritual.occultism.familiar_guardian.finished", "Страж-фамильяр успешно вызван.");
        this.lang("ru_ru").add("ritual.occultism.familiar_guardian.interrupted", "Вызов стража-фамильяра прерван.");
        this.lang("ru_ru").add("ritual.occultism.possess_unbound_otherworld_bird.conditions", "Удовлетворены не все требования для этого ритуала.");
        this.lang("ru_ru").add("ritual.occultism.possess_unbound_otherworld_bird.started", "Начался вызов дикого дрикрыла.");
        this.lang("ru_ru").add("ritual.occultism.possess_unbound_otherworld_bird.finished", "Дикий дрикрыл успешно вызван.");
        this.lang("ru_ru").add("ritual.occultism.possess_unbound_otherworld_bird.interrupted", "Вызов дикого дрикрыла прерван.");
        this.lang("ru_ru").add("ritual.occultism.possess_unbound_parrot.conditions", "Удовлетворены не все требования для этого ритуала.");
        this.lang("ru_ru").add("ritual.occultism.possess_unbound_parrot.started", "Начался вызов дикого попугая.");
        this.lang("ru_ru").add("ritual.occultism.possess_unbound_parrot.finished", "Дикий попугай успешно вызван.");
        this.lang("ru_ru").add("ritual.occultism.possess_unbound_parrot.interrupted", "Вызов дикого попугая прерван.");

        this.add("ritual.occultism.summon_random_animal.conditions", "Удовлетворены не все требования для этого ритуала.");
        this.add("ritual.occultism.summon_random_animal.started", "Начался вызов случайного животного.");
        this.add("ritual.occultism.summon_random_animal.finished", "Успешно вызван.");
        this.add("ritual.occultism.summon_random_animal.interrupted", "Вызов случайного животного прерван.");

        this.lang("ru_ru").add("ritual.occultism.familiar_parrot.conditions", "Удовлетворены не все требования для этого ритуала.");
        this.lang("ru_ru").add("ritual.occultism.familiar_parrot.started", "Начался вызов попугая-фамильяра.");
        this.lang("ru_ru").add("ritual.occultism.familiar_parrot.finished", "Попугай-фамильяр успешно вызван.");
        this.lang("ru_ru").add("ritual.occultism.familiar_parrot.interrupted", "Вызов попугая-фамильяра прерван.");
        this.lang("ru_ru").add("ritual.occultism.resurrect_allay.conditions", "Удовлетворены не все требования для этого ритуала.");
        this.lang("ru_ru").add("ritual.occultism.resurrect_allay.started", "Началось очищение Вредины до Тихони.");
        this.lang("ru_ru").add("ritual.occultism.resurrect_allay.finished", "Вредина успешно очищена до Тихони.");
        this.lang("ru_ru").add("ritual.occultism.resurrect_allay.interrupted", "Очищение Вредины до Тихони прервано.");
        this.lang("ru_ru").add("ritual.occultism.familiar_greedy.conditions", "Удовлетворены не все требования для этого ритуала.");
        this.lang("ru_ru").add("ritual.occultism.familiar_greedy.started", "Начался вызов алчного фамильяра.");
        this.lang("ru_ru").add("ritual.occultism.familiar_greedy.finished", "Алчный фамильяр успешно вызван.");
        this.lang("ru_ru").add("ritual.occultism.familiar_greedy.interrupted", "Вызов алчного фамильяра прерван.");
        this.lang("ru_ru").add("ritual.occultism.familiar_bat.conditions", "Удовлетворены не все требования для этого ритуала.");
        this.lang("ru_ru").add("ritual.occultism.familiar_bat.started", "Начался вызов летучей мыши-фамильяра.");
        this.lang("ru_ru").add("ritual.occultism.familiar_bat.finished", "Летучая мышь-фамильяр успешно вызвана.");
        this.lang("ru_ru").add("ritual.occultism.familiar_bat.interrupted", "Вызов летучей мыши-фамильяра прерван.");
        this.lang("ru_ru").add("ritual.occultism.familiar_deer.conditions", "Удовлетворены не все требования для этого ритуала.");
        this.lang("ru_ru").add("ritual.occultism.familiar_deer.started", "Начался вызов оленя-фамильяра.");
        this.lang("ru_ru").add("ritual.occultism.familiar_deer.finished", "Олень-фамильяр успешно вызван.");
        this.lang("ru_ru").add("ritual.occultism.familiar_deer.interrupted", "Вызов оленя-фамильяра прерван.");
        this.lang("ru_ru").add("ritual.occultism.familiar_headless.conditions", "Удовлетворены не все требования для этого ритуала.");
        this.lang("ru_ru").add("ritual.occultism.familiar_headless.started", "Начался вызов безголового человека-крысы-фамильяра.");
        this.lang("ru_ru").add("ritual.occultism.familiar_headless.finished", "Безголовый человек-крыса-фамильяр успешно вызван.");
        this.lang("ru_ru").add("ritual.occultism.familiar_headless.interrupted", "Вызов безголового человека-крысы-фамильяра прерван.");
        this.lang("ru_ru").add("ritual.occultism.familiar_chimera.conditions", "Удовлетворены не все требования для этого ритуала.");
        this.lang("ru_ru").add("ritual.occultism.familiar_chimera.started", "Начался вызов химеры-фамильяра.");
        this.lang("ru_ru").add("ritual.occultism.familiar_chimera.finished", "Химера-фамильяр успешно вызвана.");
        this.lang("ru_ru").add("ritual.occultism.familiar_chimera.interrupted", "Вызов химеры-фамильяра прерван.");
        this.lang("ru_ru").add("ritual.occultism.familiar_beholder.conditions", "Удовлетворены не все требования для этого ритуала.");
        this.lang("ru_ru").add("ritual.occultism.familiar_beholder.started", "Начался вызов созерцателя-фамильяра.");
        this.lang("ru_ru").add("ritual.occultism.familiar_beholder.finished", "Созерцатель-фамильяр успешно вызван.");
        this.lang("ru_ru").add("ritual.occultism.familiar_beholder.interrupted", "Вызов созерцателя-фамильяра прерван.");
        this.lang("ru_ru").add("ritual.occultism.familiar_fairy.conditions", "Удовлетворены не все требования для этого ритуала.");
        this.lang("ru_ru").add("ritual.occultism.familiar_fairy.started", "Начался вызов феи-фамильяра.");
        this.lang("ru_ru").add("ritual.occultism.familiar_fairy.finished", "Фея-фамильяр успешно вызвана.");
        this.lang("ru_ru").add("ritual.occultism.familiar_fairy.interrupted", "Вызов феи-фамильяра прерван.");
        this.lang("ru_ru").add("ritual.occultism.familiar_mummy.conditions", "Удовлетворены не все требования для этого ритуала.");
        this.lang("ru_ru").add("ritual.occultism.familiar_mummy.started", "Начался вызов мумии-фамильяр.");
        this.lang("ru_ru").add("ritual.occultism.familiar_mummy.finished", "Мумия-фамильяр успешно вызвана.");
        this.lang("ru_ru").add("ritual.occultism.familiar_mummy.interrupted", "Вызов мумии-фамильяра прерван.");
        this.lang("ru_ru").add("ritual.occultism.familiar_beaver.conditions", "Удовлетворены не все требования для этого ритуала.");
        this.lang("ru_ru").add("ritual.occultism.familiar_beaver.started", "Начался вызов бобра-фамильяра.");
        this.lang("ru_ru").add("ritual.occultism.familiar_beaver.finished", "Бобёр-фамильяр успешно вызван.");
        this.lang("ru_ru").add("ritual.occultism.familiar_beaver.interrupted", "Вызов бобра-фамильяра прерван.");

        this.lang("ru_ru").add("ritual.occultism.summon_demonic_wife.conditions", "Удовлетворены не все требования для этого ритуала.");
        this.lang("ru_ru").add("ritual.occultism.summon_demonic_wife.started", "Начался вызов.");
        this.lang("ru_ru").add("ritual.occultism.summon_demonic_wife.finished", "Успешно вызвана.");
        this.lang("ru_ru").add("ritual.occultism.summon_demonic_wife.interrupted", "Вызов прерван.");
        this.lang("ru_ru").add("ritual.occultism.summon_demonic_husband.conditions", "Удовлетворены не все требования для этого ритуала.");
        this.lang("ru_ru").add("ritual.occultism.summon_demonic_husband.started", "Начался вызов.");
        this.lang("ru_ru").add("ritual.occultism.summon_demonic_husband.finished", "Успешно вызван.");
        this.lang("ru_ru").add("ritual.occultism.summon_demonic_husband.interrupted", "Вызов прерван.");

        this.lang("ru_ru").add("ritual.occultism.wild_husk.conditions", "Удовлетворены не все требования для этого ритуала.");
        this.lang("ru_ru").add("ritual.occultism.wild_husk.started", "Начался вызов орды диких кадавров.");
        this.lang("ru_ru").add("ritual.occultism.wild_husk.finished", "Орда диких кадавров успешна вызвана.");
        this.lang("ru_ru").add("ritual.occultism.wild_husk.interrupted", "Вызов орды диких кадавров прерван.");
        this.lang("ru_ru").add("ritual.occultism.wild_drowned.conditions", "Удовлетворены не все требования для этого ритуала.");
        this.lang("ru_ru").add("ritual.occultism.wild_drowned.started", "Начался вызов орды диких утопленников.");
        this.lang("ru_ru").add("ritual.occultism.wild_drowned.finished", "Орда диких утопленников успешна вызвана.");
        this.lang("ru_ru").add("ritual.occultism.wild_drowned.interrupted", "Вызов орды диких утопленников прерван.");
        this.lang("ru_ru").add("ritual.occultism.wild_creeper.conditions", "Удовлетворены не все требования для этого ритуала.");
        this.lang("ru_ru").add("ritual.occultism.wild_creeper.started", "Начался вызов орды диких криперов.");
        this.lang("ru_ru").add("ritual.occultism.wild_creeper.finished", "Орда диких криперов успешна вызвана.");
        this.lang("ru_ru").add("ritual.occultism.wild_creeper.interrupted", "Вызов орды диких криперов прерван.");
        this.lang("ru_ru").add("ritual.occultism.wild_silverfish.conditions", "Удовлетворены не все требования для этого ритуала.");
        this.lang("ru_ru").add("ritual.occultism.wild_silverfish.started", "Начался вызов орды диких чешуйниц.");
        this.lang("ru_ru").add("ritual.occultism.wild_silverfish.finished", "Орда диких чешуйниц успешно вызвана.");
        this.lang("ru_ru").add("ritual.occultism.wild_silverfish.interrupted", "Вызов орды диких чешуйниц прерван.");
        this.lang("ru_ru").add("ritual.occultism.wild_weak_breeze.conditions", "Удовлетворены не все требования для этого ритуала.");
        this.lang("ru_ru").add("ritual.occultism.wild_weak_breeze.started", "Начался вызов одержимого слабого вихря.");
        this.lang("ru_ru").add("ritual.occultism.wild_weak_breeze.finished", "Одержимый слабый вихрь успешно вызван.");
        this.lang("ru_ru").add("ritual.occultism.wild_weak_breeze.interrupted", "Вызов одержимого слабого вихря прерван.");
        this.lang("ru_ru").add("ritual.occultism.wild_breeze.conditions", "Удовлетворены не все требования для этого ритуала.");
        this.lang("ru_ru").add("ritual.occultism.wild_breeze.started", "Начался вызов одержимого вихря.");
        this.lang("ru_ru").add("ritual.occultism.wild_breeze.finished", "Одержимый вихрь успешно вызван.");
        this.lang("ru_ru").add("ritual.occultism.wild_breeze.interrupted", "Вызов одержимого вихря прерван.");
        this.lang("ru_ru").add("ritual.occultism.wild_strong_breeze.conditions", "Удовлетворены не все требования для этого ритуала.");
        this.lang("ru_ru").add("ritual.occultism.wild_strong_breeze.started", "Начался вызов одержимого сильного вихря.");
        this.lang("ru_ru").add("ritual.occultism.wild_strong_breeze.finished", "Одержимый сильный вихрь успешно вызван.");
        this.lang("ru_ru").add("ritual.occultism.wild_strong_breeze.interrupted", "Вызов одержимого сильного вихря прерван.");
        this.lang("ru_ru").add("ritual.occultism.summon_horde_illager.conditions", "Удовлетворены не все требования для этого ритуала.");
        this.lang("ru_ru").add("ritual.occultism.summon_horde_illager.started", "Начался вызов небольшого нашествия обитателей.");
        this.lang("ru_ru").add("ritual.occultism.summon_horde_illager.finished", "Небольшое нашествие обитателей успешно вызвано.");
        this.lang("ru_ru").add("ritual.occultism.summon_horde_illager.interrupted", "Вызов небольшого нашествия обитателей прервано.");

        this.lang("ru_ru").add("ritual.occultism.craft_nature_paste.conditions", "Удовлетворены не все требования для этого ритуала.");
        this.lang("ru_ru").add("ritual.occultism.craft_nature_paste.started", "Началось наполнение природной пасты.");
        this.lang("ru_ru").add("ritual.occultism.craft_nature_paste.finished", "Успешно наполнил природную пасту.");
        this.lang("ru_ru").add("ritual.occultism.craft_nature_paste.interrupted", "Создание природной пасты прервано.");
        this.lang("ru_ru").add("ritual.occultism.craft_gray_paste.conditions", "Удовлетворены не все требования для этого ритуала.");
        this.lang("ru_ru").add("ritual.occultism.craft_gray_paste.started", "Началось наполнение серой пасты.");
        this.lang("ru_ru").add("ritual.occultism.craft_gray_paste.finished", "Успешно наполнил серую пасту.");
        this.lang("ru_ru").add("ritual.occultism.craft_gray_paste.interrupted", "Создание серой пасты прервано.");
        this.lang("ru_ru").add("ritual.occultism.craft_research_fragment_dust.conditions", "Удовлетворены не все требования для этого ритуала.");
        this.lang("ru_ru").add("ritual.occultism.craft_research_fragment_dust.started", "Началось наполнение пыли фрагмента исследования.");
        this.lang("ru_ru").add("ritual.occultism.craft_research_fragment_dust.finished", "Успешно наполнил пыль фрагмента исследования.");
        this.lang("ru_ru").add("ritual.occultism.craft_research_fragment_dust.interrupted", "Создание пыли фрагмента исследования прервано.");
        this.lang("ru_ru").add("ritual.occultism.craft_witherite_dust.conditions", "Удовлетворены не все требования для этого ритуала.");
        this.lang("ru_ru").add("ritual.occultism.craft_witherite_dust.started", "Началось наполнение визерита.");
        this.lang("ru_ru").add("ritual.occultism.craft_witherite_dust.finished", "Успешно наполнил визерит.");
        this.lang("ru_ru").add("ritual.occultism.craft_witherite_dust.interrupted", "Создание визерита прервано.");
	    this.lang("ru_ru").add("ritual.occultism.repair_chalks.conditions", "Удовлетворены не все требования для этого ритуала.");
        this.lang("ru_ru").add("ritual.occultism.repair_chalks.started", "Началась починка мела.");
        this.lang("ru_ru").add("ritual.occultism.repair_chalks.finished", "Мел успешно починен.");
        this.lang("ru_ru").add("ritual.occultism.repair_chalks.interrupted", "Починка мела прервана.");
        this.lang("ru_ru").add("ritual.occultism.repair_tools.conditions", "Удовлетворены не все требования для этого ритуала.");
        this.lang("ru_ru").add("ritual.occultism.repair_tools.started", "Началась починка инструмента.");
        this.lang("ru_ru").add("ritual.occultism.repair_tools.finished", "Инструмент успешно починен.");
        this.lang("ru_ru").add("ritual.occultism.repair_tools.interrupted", "Починка инструмента прервана.");
        this.lang("ru_ru").add("ritual.occultism.repair_armors.conditions", "Удовлетворены не все требования для этого ритуала.");
        this.lang("ru_ru").add("ritual.occultism.repair_armors.started", "Началась починка брони.");
        this.lang("ru_ru").add("ritual.occultism.repair_armors.finished", "Броня успешно починена.");
        this.lang("ru_ru").add("ritual.occultism.repair_armors.interrupted", "Починка брони прервана.");
        this.lang("ru_ru").add("ritual.occultism.repair_miners.conditions", "Удовлетворены не все требования для этого ритуала.");
        this.lang("ru_ru").add("ritual.occultism.repair_miners.started", "Началось восстановление рудокопа.");
        this.lang("ru_ru").add("ritual.occultism.repair_miners.finished", "Рудокоп успешно восстановлен.");
        this.lang("ru_ru").add("ritual.occultism.repair_miners.interrupted", "Восстановление рудокопа прервано.");

    }

    private void addBook() {
        var helper = ModonomiconAPI.get().getContextHelper(Occultism.MODID);
        helper.book("dictionary_of_spirits");

        this.addRitualsCategory(helper);
        this.addSummoningRitualsCategory(helper);
        this.addCraftingRitualsCategory(helper);
        this.addPossessionRitualsCategory(helper);
        this.addFamiliarRitualsCategory(helper);
        this.addStorageCategory(helper);
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
                        Ритуалы позволяют вызывать духов в нашу плоскость существования или связывать их в предметы или живые существа. В состав каждого ритуала входит: [#](%1$s)Пентакль[#](), [#](%1$s)ингредиенты для ритуала[#](), что предоставляются через жертвенные миски, [#](%1$s)запускающий предмет[#](), а в ряде случаев — [#](%1$s)Жертвоприношение[#]() живых существ. Пурпурные частицы покажут, что ритуал удался и выполняется.
                        """.formatted(COLOR_PURPLE));

        helper.page("steps");
        this.lang("ru_ru").add(helper.pageTitle(), "Выполнение ритуала");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        Ритуалы всегда выполняются по неизменным этапам:
                        - Начертить пентакль;
                        - Поместить золотую миску;
                        - Поместить жертвенные миски;
                        - Положить ингредиенты в миски;
                        - Нажать [#](%1$s)ПКМ[#]() по золотой миске при помощи активационного предмета.
                        - *При желании: совершить жертвоприношение в центре пентакля.*
                        """.formatted(COLOR_PURPLE));

        helper.page("additional_requirements");
        this.lang("ru_ru").add(helper.pageTitle(), "Дополнительные требования");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        Если ритуал показывает серые частицы над золотой жертвенной миской, то следует осуществить дополнительные требования, описанные на странице ритуала. После осуществления всех требований, ритуал покажет пурпурные частицы и начнёт тратить предметы в жертвенных мисках.
                        """);

        helper.entry("item_use");
        this.lang("ru_ru").add(helper.entryName(), "Использование предмета");

        helper.page("intro");
        this.lang("ru_ru").add(helper.pageTitle(), "Использование предмета");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        Для выполнения некоторых ритуалов требуется использовать определённые предметы. Чтобы приступить к проведению ритуала, используйте предмет в пределах 16 блоков от [](item://occultism:golden_sacrificial_bowl), что описан на странице ритуала.
                        \\
                        \\
                        **Важная информация**: перед тем как использовать предмет, начните ритуал. Серые частицы означают, что ритуал готов использовать предмет.
                        """);

        helper.entry("sacrifice");
        this.lang("ru_ru").add(helper.entryName(), "Жертвы");

        helper.page("intro");
        this.lang("ru_ru").add(helper.pageTitle(), "Жертвы");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        Некоторые ритуалы требуют жертвоприношение живым существом для обеспечения необходимой энергией для вызова духа. Жертвы описаны на странице ритуала в подразделе "Жертвоприношение". Для совершения жертвоприношения, убейте животное в пределах 8 блоков от золотой жертвенной миски. Жертвоприношение считается только убийством, совершённым игроком!
                         """);

        helper.entry("summoning_rituals");
        this.lang("ru_ru").add(helper.entryName(), "Ритуалы вызова");

        helper.entry("possession_rituals");
        this.lang("ru_ru").add(helper.entryName(), "Ритуалы одержимости");

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
                        Ритуалы вызова вынуждают духов входить в этот мир в своём выбранном облике, что приводит к небольшим ограничениям на их силе; но подвергает их распаду сущности. Вызванные духи классифицируются, начиная с Духов-Торговцев, торгующих и преобразующих предметы, заканчивая рабами-помощниками для физического труда.
                         """);

        helper.entry("return_to_rituals");
        this.lang("ru_ru").add(helper.entryName(), "Вернуться в категорию ритуалов");

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
                        Он дробит **1** руду на **3** соответствующие пыли.
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
                        Он дробит **1** руду на **4** соответствующие пыли.
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
                        Он дробит **1** руду на **6** соответствующие пыли.
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
        this.lang("ru_ru").add(helper.entryName(), "Вызов Машиниста-Джинна");

        helper.page("intro");
        this.lang("ru_ru").add(helper.pageTitle(), "Машинист-Джинн");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        Машинист перемещает предметы в свою управляющее устройство, что указаны в интерфейсе Актуатора пространственного хранилища, и возвращает результаты создания в систему хранения. Кроме того, он может использоваться для автоматического опустошения сундука в Актуатор хранилища.
                        \\
                        По сути, это создание по заказу!
                          """);

        helper.page("ritual");
        //текст отсутствует

        helper.page("tutorial");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        Чтобы использовать машиниста, используйте книгу вызова, чтобы связать Актуатор хранилища, машину, и по желанию, отдельное место извлечения (лицевая сторона, из которой будут извлекаться предметы (по нажатию!)). Для машины можете дополнительно задать своё название и лицевые стороны ввода/извлечения.
                          """);

        helper.page("tutorial2");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        Обратите внимание, что установка новой машины (либо её настройка с помощью книги вызова), будут сброшены настройки извлечения.
                        \\
                        \\
                        Для лёгкого начала, убедитесь, что просмотрели короткую [Видеоинструкцию](https://gyazo.com/237227ba3775e143463b31bdb1b06f50)!
                          """);

        helper.page("book_of_calling");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        В случае потери Книги вызова, Вы сможете создать новую.
                        Нажатие [#](%1$s)Shift + ПКМ[#]() по духу созданной книге, определит её.
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
                        Потусторонние деревья, что выращены из естественных Потусторонних саженцев, могут быть собраны только во время действия [#](%1$s)Третьего глаза[#](). Для упрощения жизни, Торговец потусторонними саженцами будет обменивать столь естественные саженцы на стабильную разновидность, которая может быть собрана кем-угодно, а при сборе сбрасывать такие же стабильные саженцы.
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
                        Торговцы потусторонним камнем позволяют получить больше [](item://occultism:otherstone), нежели использовать [](item://occultism:spirit_fire). В первую очередь это выгодно, если хотите использовать Потусторонний камень в качестве строительного материала.
                           """);

        helper.page("trade");
        //текст отсутствует

        helper.page("ritual");
        //текст отсутствует

        helper.entry("invoke_unbound_parrot");
        this.lang("ru_ru").add(helper.entryName(), "Вызов несвязанного попугая");

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
                        Убийство [#](%1$s)Курицы[#]() и пожертвование красителей предназначается для того, чтобы склонить Фолиота принять облик попугая. Хотя [#](%1$s)Фолиот[#]() не находится среди умнейших духов, порой он дурно понимает указания ...
                          """.formatted(COLOR_PURPLE));

        helper.page("description2");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        *Следовательно, если появится [#](%1$s)курица[#](), это не ошибка, просто не повезло!*
                           """.formatted(COLOR_PURPLE));

        helper.entry("possess_unbound_otherworld_bird");
        this.lang("ru_ru").add(helper.entryName(), "Овладение несвязанным дрикрылом");

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
                        Для получения дополнительной информации, ознакомьтесь с записью [Дрикрыл-фамильяр](entry://familiar_rituals/familiar_otherworld_bird).
                          """);

        helper.entry("weather_magic");
        this.lang("ru_ru").add(helper.entryName(), "Магия погоды");

        helper.page("intro");
        this.lang("ru_ru").add(helper.pageTitle(), "Магия погоды");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        Магия погоды особенно полезна для фермеров и других людей, в зависимости от конкретной погоды. Вызывайте духов ради изменения погоды. Для разных видов изменения погоды требуются определённые духи.
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
                        Магия времени ограничена возможностями. Она не может перемещать заклинателя во времени, однако скорее, позволяет изменять время суток. Это особенно полезно для ритуалов или других задач, требующих сугубо дня и ночи.
                        \\
                        \\
                        Духи времени изменяют лишь только время, а затем исчезают.
                           """);

        helper.page("ritual_day");
        //текст отсутствует

        helper.page("ritual_night");
        //текст отсутствует

        helper.entry("afrit_essence");
        this.add(helper.entryName(), "Сущность Африта");

        helper.page("intro");
        this.lang("ru_ru").add(helper.pageTitle(), "Сущность Африта");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        [](item://occultism:afrit_essence) — широко используется при формировании красного мела, требуемого для безопасного вызова более могущественных духов. Для получения сущности [#](%1$s)Африта[#]() в этой плоскости нужно вызвать несвязанного Африта и убить. Учтите, что это не простая затея. Несвязанный дух представляет большую угрозу всех поблизости.
                           """.formatted(COLOR_PURPLE));

        helper.page("ritual");
        //текст отсутствует

        helper.entry("marid_essence");
        this.add(helper.entryName(), "Сущность Марида");

        helper.page("intro");
        this.lang("ru_ru").add(helper.pageTitle(), "Сущность Марида");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        [](item://occultism:marid_essence) — широко используемая при формировании синего мела, требуемого для безопасного контролирования самых могущественных духов. Для получения сущности [#](%1$s)Марида[#]() в этой плоскости нужно вызвать несвязанного Марида и убить. Учтите, что это не простая затея. Несвязанный дух представляет большую угрозу всех поблизости.
                           """.formatted(COLOR_PURPLE));

        helper.page("ritual");
        //текст отсутствует
    }

    private void addPossessionRitualsCategory(BookContextHelper helper) {
        helper.category("possession_rituals");
        this.lang("ru_ru").add(helper.categoryName(), "Ритуалы одержимости");

        helper.entry("return_to_rituals");
        this.lang("ru_ru").add(helper.entryName(), "Вернуться в категорию ритуалов");

        helper.entry("overview");
        this.lang("ru_ru").add(helper.entryName(), "Ритуалы одержимости");

        helper.page("intro");
        this.lang("ru_ru").add(helper.pageTitle(), "Ритуалы одержимости");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        Ритуалы одержимости связывают духов с живыми существами, что в значительной степени наделяет вызывателя контролем над одержимым существом.
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
                        В этом ритуале появляется [#](%1$s)Эндермен[#](), при использовании жизненной энергии [#](%1$s)Свиньи[#]() и почти сразу овладевается вызванным [#](%1$s)Джинном[#](). При убийстве с [#](%1$s)Одержимого эндермена[#]() всегда выпадает минимум 1 [](item://minecraft:ender_pearl)
                                """.formatted(COLOR_PURPLE));

        helper.entry("wither_skull");
        this.add(helper.entryName(), "Череп визер-скелета");

        helper.page("intro");
        this.add(helper.pageTitle(), "Череп визер-скелета");
        this.add(helper.pageText(),
                """
                        Помимо опасного путешествия в Незер, существует ещё один способ получения этих черепов. Легендарная [#](%1$s)Дикая Охота[#]() состоит из [#](%1$s)Могущественных духов[#](), принявших облик Визер-скелетов. Хотя вызов Дикой Охоты чрезвычайно опасен, — это наибыстрейший способ получения черепов визер-скелетов.
                           """.formatted(COLOR_PURPLE));

        helper.page("ritual");
        //no text

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
                        В этом ритуале появляется [#](%1$s)Эндермит[#]() при обмане. Камни и земля символизируют окружающий мир, затем бросается яйцо для имитации использования эндер-жемчуга. При появлении эндермита, вызванный [#](%1$s)Фолиот[#]() почти сразу овладевает им, посещает [#](%1$s)Энд[#]() и возвращается обратно. При убийстве с [#](%1$s)Одержимого эндермита[#]() всегда выпадает минимум 1 [](item://minecraft:end_stone).
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
                        В этом ритуале появляется [#](%1$s)Скелет[#](), при использовании жизненной энергии [#](%1$s)Курицы[#]() и почти сразу овладевается вызванным [#](%1$s)Фолиотом[#](). При убийстве [#](%1$s)Одержимый скелет[#]() будет устойчивым к дневному свету, и всегда выдаёт минимум 1 [](item://minecraft:skeleton_skull).
                                """.formatted(COLOR_PURPLE));
    }

    private void addCraftingRitualsCategory(BookContextHelper helper) {
        helper.category("crafting_rituals");
        this.lang("ru_ru").add(helper.categoryName(), "Ритуалы связывания"); //done via the category provider for the rituals category

        helper.entry("return_to_rituals");
        this.lang("ru_ru").add(helper.entryName(), "Вернуться в категорию ритуалов");

        helper.entry("overview");
        this.lang("ru_ru").add(helper.entryName(), "Ритуалы связывания");

        helper.page("intro");
        this.lang("ru_ru").add(helper.pageTitle(), "Ритуалы связывания");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        Ритуалы связывания наполняют духов в предметы, в которых их силы используются для достижения определённой цели. Созданные предметы могут действовать как простые чары, наделяющие силой, или выполнять сложные задачи, чтобы помочь вызывателю.
                           """);

        helper.entry("craft_storage_system");
        this.lang("ru_ru").add(helper.entryName(), "Магическое хранилище");

        helper.page("spotlight");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        Следующие записи показывают только ритуалы, связанные с системой Магического хранения. Подробную пошаговую инструкцию по созданию системы хранения смотрите в категории [Магическое хранилище](category://storage).
                           """.formatted(COLOR_PURPLE));

        helper.entry("craft_dimensional_matrix");
        this.lang("ru_ru").add(helper.entryName(), "Пространственная матрица");

        helper.page("spotlight");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        Пространственная матрица — мостик в небольшое измерение, используемое для хранения предметов. [#](%1$s)Джинн[#](), связанный с матрицей — поддерживает стабильность измерения, как правило, поддерживается другими духами внутри стабилизаторов хранилища для повышения размера пространства.
                           """.formatted(COLOR_PURPLE));

        helper.page("ritual");
        //текст отсутствует

        helper.entry("craft_dimensional_mineshaft");
        this.lang("ru_ru").add(helper.entryName(), "Пространственная шахта");

        helper.page("spotlight");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        Пространственная шахта заселяет [#](%1$s)Джинна[#](), который открывает доступ к стабильному соединению в незаселённом измерении, идеально подходящем для добычи ресурсов. Хотя портал слишком мал для переноса людей, другие духи могут использовать его для входа в шахтёрское измерение и выносить оттуда ресурсы.
                           """.formatted(COLOR_PURPLE));

        helper.page("ritual");
        //текст отсутствует

        helper.page("description");
        this.lang("ru_ru").add(helper.pageTitle(), "Функционирование");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        Пространственная шахта будет отбрасывать любые предметы, неспособные к хранению, в связи с этим важно регулярно опустошать шахту либо вручную при помощи воронок, либо при помощи Духа-Транспортировщика. Духи в лампе могут быть **размещены** сверху, все прочие стороны можно использовать для **извлечения** предметов.
                           """.formatted(COLOR_PURPLE));


        helper.entry("craft_infused_pickaxe");
        this.lang("ru_ru").add(helper.entryName(), "Наполненная кирка");

        helper.page("spotlight");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        Потусторонние руды, как правило, можно добыть только с помощью инструментов из потустороннего металла [](item://occultism:infused_pickaxe) является временным решением этой классической дилеммы. Хрупкий кристалл, настроенный на духа, содержит [#](%1$s)Джинна[#](), который позволяет собирать руды, однако прочность кирки крайне низкая. Наиболее прочной является [кирка из айзния](entry://getting_started/iesnium_pickaxe).
                           """.formatted(COLOR_PURPLE));

        helper.page("ritual");
        //текст отсутствует

        helper.entry("craft_otherworld_goggles");
        this.lang("ru_ru").add(helper.entryName(), "Создание потусторонних очков");

        helper.page("goggles_spotlight");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        [](item://occultism:otherworld_goggles) наделяют владельца постоянным [#](%1$s)Третьим глазом[#](), позволяя видеть даже блоки, что сокрыты от тех, кто отведал [Видение демона](entry://occultism:dictionary_of_spirits/getting_started/demons_dream).
                        \\
                        \\
                        Это изящно решает общую проблему вызывателей, находящихся под наркотическим помутнением, что может привести к всяческим расстройствам.
                        """.formatted(COLOR_PURPLE));

        helper.page("goggles_more");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        Тем не менее, очки не наделяют способностью добывать материалы из Иного Места. Иными словами, при ношении очков следует использовать [наполненную кирку](entry://getting_started/infused_pickaxe), или ещё лучше — [кирку из айзния](entry://getting_started/iesnium_pickaxe), для того, чтобы разрушать блоки и получать с них Потусторонние разновидности.
                        """.formatted(COLOR_PURPLE));

        helper.page("lenses_spotlight");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        Потусторонние очки располагают [#](%1$s)Фолиота[#](), связанного с линзами. Фолиот передаёт владельцу способность видеть более высокие плоскости, благодаря этому давая возможность видеть Потусторонние материалы.
                         """.formatted(COLOR_PURPLE));

        helper.page("lenses_more");
        this.lang("ru_ru").add(helper.pageTitle(), "Создание линз");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        Вызов духа в линзы, используемый для создания очков, обычно является одним из первых сложных ритуалов для начинающих вызывателей. Это говорит о том, что их навыки усовершенствуются за рамки основных знаний.
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
                        Основа актуатора хранилища заточает [#](%1$s)Фолиота[#](), отвечающего за взаимодействие с предметами в матрице пространственного хранилища.
                        """.formatted(COLOR_PURPLE));

        helper.page("ritual");
        //текст отсутствует

        helper.entry("craft_stabilizer_tier1");
        this.lang("ru_ru").add(helper.entryName(), "Стабилизатор хранилища 1-го уровня");

        helper.page("spotlight");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        Этот простой стабилизатор хранилища, заселённый [#](%1$s)Фолиотом[#](), который поддерживает стойкость Пространственной матрицы во время стабильного состояния пространственного хранилища, что таким образом позволяет хранить больше предметов.
                        \\
                        \\
                        По умолчанию каждый Стабилизатор 1-го уровня добавляет **64** типа предметов и 512.000 ед. вместимости.
                        """.formatted(COLOR_PURPLE));

        helper.page("ritual");
        //текст отсутствует

        helper.entry("craft_stabilizer_tier2");
        this.lang("ru_ru").add(helper.entryName(), "Стабилизатор хранилища 2-го уровня");

        helper.page("spotlight");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        Этот улучшенный стабилизатор, заселённый [#](%1$s)Джинном[#](), который поддерживает стойкость Пространственной матрицы во время стабильного состояния пространственного хранилища, что таким образом позволяет хранить ещё больше предметов.
                        \\
                        \\
                        По умолчанию каждый Стабилизатор 2-го уровня добавляет **128** типов предметов и 1.024.000 ед. вместимости.
                        """.formatted(COLOR_PURPLE));

        helper.page("ritual");
        //текст отсутствует

        helper.entry("craft_stabilizer_tier3");
        this.lang("ru_ru").add(helper.entryName(), "Стабилизатор хранилища 3-го уровня");

        helper.page("spotlight");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        Этот продвинутый стабилизатор, заселённый [#](%1$s)Афритом[#](), который поддерживает стойкость Пространственной матрицы во время стабильного состояния пространственного хранилища, что таким образом позволяет хранить ещё больше предметов.
                        \\
                        \\
                        По умолчанию каждый Стабилизатор 3-го уровня добавляет **256** типов предметов и 2.048.000 ед. вместимости.
                        """.formatted(COLOR_PURPLE));

        helper.page("ritual");
        //текст отсутствует

        helper.entry("craft_stabilizer_tier4");
        this.lang("ru_ru").add(helper.entryName(), "Стабилизатор хранилища 4-го уровня");

        helper.page("spotlight");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        Этот весьма продвинутый стабилизатор, заселённый [#](%1$s)Маридом[#](), который поддерживает стойкость Пространственной матрицы во время стабильного состояния пространственного хранилища, что таким образом позволяет хранить ещё больше предметов.
                        \\
                        \\
                        По умолчанию каждый Стабилизатор 4-го уровня добавляет **512** типа предметов и 4.098.000 ед. вместимости.
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
                        Нажмите Shift + ПКМ по [](item://occultism:storage_controller), чтобы связать её, затем поставьте червоточину в мир, чтобы использовать её в качестве удалённой точки доступа.
                         """.formatted(COLOR_PURPLE));

        helper.page("ritual");
        //текст отсутствует

        helper.entry("craft_storage_remote");
        this.lang("ru_ru").add(helper.entryName(), "Средство доступа удалённого хранилища");

        helper.page("spotlight");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        [](item://occultism:storage_remote) может быть связано с [](item://occultism:storage_controller) при нажатии Shift + ПКМ. [#](%1$s)Джинн[#](), связанный со средством доступа в дальнейшем сможет получить доступ к предметам из Актуатора даже из других измерений.
                        """.formatted(COLOR_PURPLE));

        helper.page("ritual");
        //текст отсутствует

        helper.entry("craft_foliot_miner");
        this.lang("ru_ru").add(helper.entryName(), "Рудокоп-Фолиот");

        helper.page("intro");
        this.lang("ru_ru").add(helper.pageTitle(), "Рудокоп-Фолиот");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        Духи-Рудокопы используют [](item://occultism:dimensional_mineshaft) для получения ресурсов из других измерений. Они вызываются и связываются в магические лампы, из которых могут выходить только через шахты. Магическая лампа постепенно разрушается, а когда она разрушится, дух освободится обратно в [#](%1$s)Иное Место[#]().
                        """.formatted(COLOR_PURPLE));

        helper.page("magic_lamp");
        this.lang("ru_ru").add(helper.pageTitle(), "Магическая лампа");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        Для вызова Духов-Рудокопов сперва Вам нужно создать [Магическую лампу](entry://getting_started/magic_lamps), чтобы владеть ими. Главным ингредиентом является [Айзний](entry://getting_started/iesnium).
                        """.formatted(COLOR_PURPLE));

        helper.page("magic_lamp_recipe");
        //текст отсутствует

        helper.page("spotlight");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        Рудокоп-[#](%1$s)Фолиот[#]() добывает блоки без особой цели и возвращает всё, что находит. Процесс добычи ресурсов довольно медленный, по этой причине Фолиот тратит лишь незначительное количество энергии, постепенно вредя лампе, в которой он содержится.
                        """.formatted(COLOR_PURPLE));

        helper.page("ritual");
        //текст отсутствует

        helper.entry("craft_djinni_miner");
        this.lang("ru_ru").add(helper.entryName(), "Рудокоп-Джинн");

        helper.page("spotlight");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        Рудокоп-[#](%1$s)Джинн[#]() добывает именно руду. Исключая другие блоки — способен быстрее и эффективнее добывать руды. Чем больше сила Джинна, тем быстрее вредит магической лампе.
                        """.formatted(COLOR_PURPLE));

        helper.page("ritual");
        //текст отсутствует

        helper.entry("craft_afrit_miner");
        this.lang("ru_ru").add(helper.entryName(), "Рудокоп-Африт");

        helper.page("spotlight");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        Рудокоп-[#](%1$s)Африт[#]() добывает руду, как и рудокопы-джинны и к тому же глубинносланцевые руды. Этот рудокоп быстрее и эффективнее джинна, в связи с этим ещё медленнее вредит магической лампе.
                        """.formatted(COLOR_PURPLE));

        helper.page("ritual");
        //текст отсутствует

        helper.entry("craft_marid_miner");
        this.lang("ru_ru").add(helper.entryName(), "Рудокоп-Марид");

        helper.page("spotlight");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        Рудокоп-[#](%1$s)Марид[#]() является самым могущественным духом-рудокопом. У него невероятная скорость добычи, и лучшая сохранность магической лампы. В отличие от других духов-рудокопов, он также может добывать редчайшие руды, напр.: [](item://minecraft:ancient_debris) и [](item://occultism:iesnium_ore).
                        """.formatted(COLOR_PURPLE));

        helper.page("ritual");
        //текст отсутствует

        helper.entry("craft_ancient_miner");
        this.add(helper.entryName(), "Древний рудокоп");

        helper.page("spotlight");
        this.add(helper.pageText(),
                """
                        Сжав ДАМ Вы получите сверхмогущественного рудокопа, однако нечто начнёт наблюдать за Вами. [](item://occultism:mining_dim_core) крайне редко добывается Маридом.
                        """.formatted(COLOR_PURPLE));

        helper.page("ritual");
        //no text

        helper.entry("craft_satchel");
        this.lang("ru_ru").add(helper.entryName(), "Необычайно большая сумка");

        helper.page("spotlight");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        На [#](%1$s)Фолиота[#](), связанного с сумкой, возлагается обязанность **незначительно** искажать реальность. Это позволяет хранить в ней больше предметов, чем указывают её размеры, что делает её практичным спутником путешественника.
                        """.formatted(COLOR_PURPLE));

        helper.page("ritual");
        //текст отсутствует

        helper.entry("craft_soul_gem");
        this.lang("ru_ru").add(helper.entryName(), "Камень души");

        helper.page("spotlight");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        Камень души — алмаз, что устанавливается в драгоценные металлы, а потом заселяется [#](%1$s)Джинном[#](). Дух создаёт небольшое измерение, что позволяет временно захватывать живых существ. Тем не менее существ огромной силы нельзя захватывать.
                        """.formatted(COLOR_PURPLE));

        helper.page("usage");
        this.lang("ru_ru").add(helper.pageTitle(), "Использование");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        Для поимки существа, нажмите по нему [#](%1$s)ПКМ[#]() с помощью камня души.
                        Снова нажмите [#](%1$s)ПКМ[#](), чтобы выпустить существо.
                        \\
                        \\
                        Босса невозможно поймать.
                               """.formatted(COLOR_PURPLE));


        helper.page("ritual");
        //текст отсутствует

        helper.entry("craft_familiar_ring");
        this.lang("ru_ru").add(helper.entryName(), "Кольцо для фамильяра");

        helper.page("spotlight");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        Кольцо для фамильяра состоит из [](item://occultism:soul_gem), содержащего в себе [#](%1$s)Джинна[#](), заточённого в кольцо. [#](%1$s)Джинн[#]() в кольце позволяет фамильяру, заключённому в камне души — наделять владельца эффектами."
                        """.formatted(COLOR_PURPLE));

        helper.page("usage");
        this.lang("ru_ru").add(helper.pageTitle(), "Использование");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        Чтобы воспользоваться [](item://occultism:familiar_ring), поймайте вызванного (и приручённого) фамильяра, нажав по нему [#](%1$s)ПКМ[#](), а затем наденьте кольцо в качестве [#](%1$s)Аксессуара[#](), чтобы использовать эффекты, оказываемые фамильяром.
                        \\
                        \\
                        При освобождении фамильяра из кольца, дух распознает человека, освободившего его, как своего нового хозяина.
                        """.formatted(COLOR_PURPLE));

        helper.page("ritual");
        //текст отсутствует
        helper.entry("craft_wild_trim");
        this.lang("ru_ru").add(helper.entryName(), "Отделка «Дебри»");

        helper.page("spotlight");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        В отличие от других ритуалов, создание [](item://minecraft:wild_armor_trim_smithing_template) — услуга, оказываемая Дикими духами и не связывает какого-либо духа с последним объектом. Вы жертвуете предметами, а Дикие духи используют свои силы, чтобы сковать предмет для Вас.
                        """.formatted(COLOR_PURPLE));

        helper.page("ritual");
		        //no text

        helper.entry("craft_budding_amethyst");
        this.add(helper.entryName(), "Ковка цветущего аметиста");

        helper.page("spotlight");
        this.add(helper.pageText(),
                """
                        В оличие от других ритуалов, создание [](item://minecraft:budding_amethyst) — услуга, оказываемая Дикими духами и не связывает какого-либо духа с последним объектом. Вы жертвуете предметами, а Дикие духи используют свои силы, чтобы сковать предмет для Вас.
                        """.formatted(COLOR_PURPLE));

        helper.page("ritual");
        //no text

        helper.entry("craft_reinforced_deepslate");
        this.add(helper.entryName(), "Ковка укреплённого глубинного сланца");

        helper.page("spotlight");
        this.add(helper.pageText(),
                """
                        В отличие от других ритуалов, создание [](item://minecraft:reinforced deepslate) — услуга, оказываемая Дикими духами и не связывает какого-либо духа с последним объектом. Вы жертвуете предметами, а Дикие духи используют свои силы, чтобы сковать предмет для Вас.
                        """.formatted(COLOR_PURPLE));

        helper.page("ritual");
        //no text

        helper.entry("repair");
        this.add(helper.entryName(), "Ритуалы починки");

        helper.page("spotlight");
        this.add(helper.pageTitle(), "Починка");
        this.add(helper.pageText(),
                """
                        При использовании простых материалов, Джинн сможет починить любой мел для Вас. Развив оккультный путь, Африт сможет восстанавливать рудокопов, чинить инструменты и доспехи. Благодаря этому любой починенный предмет сохранит свои свойства.
                        """.formatted(COLOR_PURPLE));

        helper.page("ritual_chalks");
        //Текст отсутствует
        helper.page("ritual_miners");
        //Текст отсутствует
        helper.page("ritual_tools");
        //Текст отсутствует
        helper.page("ritual_armors");
        //Текст отсутствует
    }

    private void addFamiliarRitualsCategory(BookContextHelper helper) {
        helper.category("familiar_rituals");

        helper.entry("return_to_rituals");
        this.lang("ru_ru").add(helper.entryName(), "Вернуться в категорию ритуалов");

        helper.entry("overview");
        this.lang("ru_ru").add(helper.entryName(), "Ритуалы для фамильяров");

        helper.page("intro");
        this.lang("ru_ru").add(helper.pageTitle(), "Ритуалы для фамильяров");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        Ритуалы для фамильяров вызывают духов для непосредственной помощи вызывателю. Духи обычно обитают в теле животного, что позволяет им защищать сущность от распада. Фамильяры наделяют усилениями, но могут также активно защищать вызывателя.
                                """.formatted(COLOR_PURPLE));

        helper.page("ring");
        this.lang("ru_ru").add(helper.pageTitle(), "Экипировка фамильярами");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        Находчивые вызыватели нашли способ связать фамильяров в драгоценные камни, что пассивно накладывают свои усиления. [Кольцо для фамильяра](entry://crafting_rituals/craft_familiar_ring).
                                """.formatted(COLOR_PURPLE));

        helper.page("trading");
        this.lang("ru_ru").add(helper.pageTitle(), "Экипировка фамильярами");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        Фамильярами можно свободно сменять при нахождении в [Кольце для фамильяра](entry://crafting_rituals/craft_familiar_ring).
                        \\
                        \\
                        Дух при выпускании признает человека, что выпустил его, как своего нового хозяина.
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
                        **Улучшение поведения**:\\
                        Будучи улучшена кузнецом-фамильяром, летучая мышь-фамильяр будет наделять своего владельца эффектом похищения жизни.
                           """.formatted(COLOR_PURPLE));

        helper.entry("familiar_beaver");
        this.lang("ru_ru").add(helper.entryName(), "Бобёр-фамильяр");

        helper.page("entity");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        **Предоставляет**: [#](%1$s)Повышенную скорость рубки дерева[#]().
                               """.formatted(COLOR_PURPLE));

        helper.page("ritual");
        //текст отсутствует

        helper.page("description");
        this.lang("ru_ru").add(helper.pageTitle(), "Описание");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        Бобёр-фамильяр будет срубать близлежащие деревья, как только они вырастут. Он справляется только с небольшими деревьями.
                        \\
                        \\
                        **Улучшение поведения**:\\
                        Даёт бесплатные лакомства при нажатии по нему пустой рукой.
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
                        Созерцатель-фамильяр подсвечивает близлежащих существ с помощью эффекта свечения, да и стреляет лазерными лучами по врагам. **Питается** (слабыми) **маленькими Шуб-Ниггуратами** для получения временного урона и скорости.
                        \\
                        \\
                        **Улучшение поведения**:\\
                        Будучи улучшен кузнецом-фамильяром, — наделяет своего владельца устойчивостью к слепоте.
                           """.formatted(COLOR_PURPLE));

        helper.entry("familiar_blacksmith");
        this.lang("ru_ru").add(helper.entryName(), "Кузнец-фамильяр");

        helper.page("entity");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        **Предоставляет**: [#](%1$s)Починку снаряжения во время добычи ресурсов[#](), [#](%1$s)улучшает других фамильяров[#]().
                               """.formatted(COLOR_PURPLE));

        helper.page("ritual");
        //текст отсутствует

        helper.page("description");
        this.lang("ru_ru").add(helper.pageTitle(), "Описание");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        Каждый раз, когда игрок подбирает камень, существует вероятность, что кузнец-фамильяр немного починит его снаряжение.
                        \\
                        \\
                        **Улучшение поведения**: \\
                        Не может быть улучшен, но улучшает других фамильяров.
                           """.formatted(COLOR_PURPLE));

        helper.page("description2");
        this.lang("ru_ru").add(helper.pageTitle(), "Улучшение фамильяров");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        Чтобы улучшать других фамильяров, кузнецу нужно дать железные слитки или блоки, нажав по нему [#](%1$s)ПКМ[#]().
                        \\
                        \\
                        Улучшенные фамильяры наделяют дополнительными эффектами.
                           """.formatted(COLOR_PURPLE));

        helper.entry("familiar_chimera");
        this.lang("ru_ru").add(helper.entryName(), "Химера-фамильяр");

        helper.page("entity");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        **Предоставляет**: [#](%1$s)Ездового, верхового животного[#]().
                               """.formatted(COLOR_PURPLE));

        helper.page("ritual");
        //текст отсутствует

        helper.page("description");
        this.lang("ru_ru").add(helper.pageTitle(), "Описание");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        "Чтобы вырастить химеру её нужно кормить (любым) мясом, при выращивании она будет наделять Вас уроном и скоростью. Как только она станет достаточно большой, игроки смогут её оседлать. Если накормить её [](item://minecraft:golden_apple), [#](%1$s)коза[#]() отчленится и станет отдельным фамильяром.
                        \\
                        \\
                        Отцеплённую Козу-фамильяра можно использовать для получения [Шуб-Ниггурата-фамильяра](entry://familiar_rituals/familiar_shub_niggurath).
                           """.formatted(COLOR_PURPLE));

        helper.page("description2");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        **Улучшение поведения**:\\
                        Будучи улучшена кузнецом-фамильяром, коза получит сигнальный колокол. При атаке фамильяра зазвенит колокол и **привлечёт врагов** в пределах большого радиуса.
                           """.formatted(COLOR_PURPLE));

        helper.entry("familiar_cthulhu");
        this.lang("ru_ru").add(helper.entryName(), "Ктулху-фамильяр");

        helper.page("entity");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        **Предоставляет**: [#](%1$s)Водное дыхание[#](), [#](%1$s)хладнокровие[#]().
                               """.formatted(COLOR_PURPLE));

        helper.page("ritual");
        //текст отсутствует

        helper.page("description");
        this.lang("ru_ru").add(helper.pageTitle(), "Описание");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        **Улучшение поведения**:\\
                        Будучи улучшен кузнецом-фамильяром, он будет служить передвижным источником света.
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
                        [#](%1$s)Шуб-Ниггурат[#]() не вызывается напрямую. Сперва вызовите [Химеру-фамильяра](entry://familiar_rituals/familiar_chimera) и накормите её [](item://minecraft:golden_apple), чтобы отчленить [#](%1$s)Козу[#](). Приведите козу в [#](%1$s)Лесной биом[#](). Затем нажмите по козе с помощью [любого Чёрного красителя](item://minecraft:black_dye), [](item://minecraft:flint) и [](item://minecraft:ender_eye), чтобы вызвать [#](%1$s)Шуб-Ниггурата[#]().
                           """.formatted(COLOR_PURPLE));

        helper.page("description");
        this.lang("ru_ru").add(helper.pageTitle(), "Описание");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        **Улучшение поведения**:\\
                        Будучи улучшен кузнецом-фамильяром, коза получит сигнальный колокол. При атаке фамильяра зазвенит колокол и **привлечёт врагов** в пределах большого радиуса.
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
                        **Улучшение поведения**:\\
                        Будучи улучшен кузнецом-фамильяром, он будет атаковать близлежащих врагов молотом. Ага, **молотом**.
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
                        **Улучшение поведения**:\\
                        Не может быть улучшен кузнецом-фамильяром.
                           """.formatted(COLOR_PURPLE));

        helper.entry("familiar_dragon");
        this.lang("ru_ru").add(helper.entryName(), "Дракон-фамильяр");

        helper.page("entity");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        **Предоставляет**: [#](%1$s)Повышенное получение опыта[#](), любит палочки.
                                   """.formatted(COLOR_PURPLE));

        helper.page("ritual");
        //текст отсутствует

        helper.page("description");
        this.lang("ru_ru").add(helper.pageTitle(), "Описание");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        Алчные фамильяры могут оседлать дракона-фамильяра вдобавок наделять дракона эффектами алчности.
                        \\
                        \\
                        **Улучшение поведения**:\\
                        Будучи улучшен кузнецом-фамильяром, он будет кидать мечи во врагов поблизости.
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
                        Фея-фамильяр **оберегает от смерти других фамильяров** (с перезарядкой), выручает других фамильяров **благоприятными эффектами** и **истощает жизненную силу врагов** для помощи своего хозяина.
                        \\
                        \\
                        **Улучшение поведения**:\\
                        Не может быть улучшена кузнецом-фамильяром.
                           """.formatted(COLOR_PURPLE));

        helper.entry("familiar_greedy");
        this.lang("ru_ru").add(helper.entryName(), "Алчный-фамильяр");

        helper.page("entity");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        **Предоставляет**: [#](%1$s)повышенную дальность подбора[#](), [#](%1$s)подбирает предметы[#]().
                                   """.formatted(COLOR_PURPLE));
        helper.page("ritual");
        //текст отсутствует

        helper.page("description");
        this.lang("ru_ru").add(helper.pageTitle(), "Описание");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        Алчный фамильяр является Фолиотом, подбирающим близлежащие предметы в угоду своего хозяина. Если фамильяра поймать в кольцо, он повысит дальность подбирания своего хозяина.
                        \\
                        \\
                        **Улучшение поведения**:\\
                        Будучи улучшен кузнецом-фамильяром, он сможет находить блоки своему хозяину. Нажатие по нему [#](%1$s)ПКМ[#]() блоком, чтобы указать, что искать.
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
                        Страж-фамильяр жертвует своей конечностью всякий раз, когда его хозяин близок к смерти, и благодаря этому **предотвращает смерть**. Как только страж умирает, игрок больше не будет защищён. Будучи вызван, страж появится со случайным количеством конечностей: нет гарантии, что вызовется полноценный страж.
                           """.formatted(COLOR_PURPLE));

        helper.page("description2");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        **Улучшение поведения**:\\
                        Будучи улучшен кузнецом-фамильяром, он вновь приобретёт конечность (можно выполнить только 1 раз).
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
                        Безголовый человек-крыса-фамильяр похищает головы существ возле себя при убийстве монстров. Затем он наделяет своего хозяина усилением урона против того типа существа. Если здоровье человека-крысы падает **ниже 50%**, он погибает. Однако позже может быть воссоздан своим же хозяином, предоставив ему: [](item://minecraft:wheat), [](item://minecraft:stick), [](item://minecraft:hay_block) и [](item://minecraft:pumpkin).
                           """.formatted(COLOR_PURPLE));

        helper.page("description2");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        **Улучшение поведения**:\\
                        Будучи улучшен кузнецом-фамильяром, он будет вызывать слабость близлежащих монстров того же типа, чьи головы он украл.
                           """.formatted(COLOR_PURPLE));


        helper.entry("familiar_mummy");
        this.lang("ru_ru").add(helper.entryName(), "Мумия-фамильяр");

        helper.page("entity");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        **Предоставляет**: [#](%1$s)Сражается с Вашими врагами[#](), [#](%1$s)эффект уклонения[#]().
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
                        **Улучшение поведения**:\\
                        Будучи улучшен кузнецом-фамильяром, он будет наносить ещё больше урона.
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
                        [#](%1$s)Дрикрылы$[#]() — подкласс [#](%1$s)Джинна[#](), известные своей дружелюбностью по отношению к людям. Как правило, они принимают облик тёмно-синего и пурпурного цвета попугая. Дрикрылы будут наделять своего владельца ограниченными возможностями полёта, будучи рядом.
                        \\
                        \\
                        **Улучшение поведения**:\\
                        Не могут быть улучшены кузнецом-фамильяром.
                            """.formatted(COLOR_PURPLE));

        helper.page("description2");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        Для получения попугая или попугая-фамильяра на предмет жертвоприношения, попробуйте вызвать их при помощи: [Ритуал: Дикий попугай](entry://possession_rituals/possess_unbound_parrot) или [Ритуал: Попугай-фамильяр](entry://familiar_rituals/familiar_parrot).
                        \\
                        \\
                        **Совет:** Если используете моды, защищающие питомцев от смерти, используйте Ритуал: Дикий попугай!
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


		//
        helper.page("description");
        this.lang("ru_ru").add(helper.pageTitle(), "Описание");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        Во время этого ритуала [#](%1$s)Фолиот[#]() вызывается **в качестве фамильяра**. Убийство [#](%1$s)Курицы[#]() и пожертвование красителей предназначается для того, чтобы склонить [#](%1$s)Фолиота[#]() принять облик попугая.\\
                        Хотя [#](%1$s)Фолиот[#]() не находится среди умнейших духов, порой он дурно понимает указания ...
                            """.formatted(COLOR_PURPLE));

        helper.page("description2");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        *Следовательно, если появится [#](%1$s)курица[#](), это не ошибка, просто не повезло!*
                        \\
                        \\
                        **Улучшение поведения**:\\
                        Не может быть улучшены кузнецом-фамильяром.
                           """.formatted(COLOR_PURPLE));

        //текст отсутствует
        helper.entry("resurrect_allay");
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
                         Очищение Вредины до Тихони, во время процесса воскресения, раскроет её истинное имя.
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
                        Проблема, известная каждому вызывателю: существует слишком много оккультной атрибутики, лежащих без дела. Простое решение проблемы, но всё же лучшее: Магическое хранилище!
                        \\
                        \\
                        С помощью духов можно получить доступ к пространственным хранилищам, способным создавать почти неограниченное место для хранения.
                        """.formatted(COLOR_PURPLE));

        helper.page("intro2");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        Выполните следующие действия, отображённые в этой категории, для получения собственной системы хранения!
                        Действия, относящиеся к хранению в [Ритуалах связывания](category://crafting_rituals/) отображают только ритуалы, тогда как здесь отображены **все требуемые действия**, включая создание.
                        """.formatted(COLOR_PURPLE));

        helper.entry("storage_controller");
        this.lang("ru_ru").add(helper.entryName(), "Актуатор хранилища");

        helper.page("intro");
        this.lang("ru_ru").add(helper.pageTitle(), "Актуатор хранилища");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        В состав [](item://occultism:storage_controller) входит [Пространственная матрица](entry://crafting_rituals/craft_dimensional_matrix), заселённая [#](%1$s)Джинном[#](), что создаёт и управляет пространственным хранилищем, и [Основание](entry://crafting_rituals/craft_storage_controller_base), вселённое [#](%1$s)Фолиотом[#](), который перемещает предметы из пространственого хранилища туда и обратно.
                        """.formatted(COLOR_PURPLE));

        helper.page("usage");
        this.lang("ru_ru").add(helper.pageTitle(), "Использование");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        После создания [](item://occultism:storage_controller) (ознакомьтесь со следующей страницей), поставьте его в мире и нажмите по нему [#](%1$s)ПКМ[#]() пустой рукой. Это откроет его графический интерфейс, и с этого момента он будет работать как очень большой шалкеровый ящик.
                        """.formatted(COLOR_PURPLE));

        helper.page("safety");
        this.lang("ru_ru").add(helper.pageTitle(), "Безопасность прежде всего!");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        Разрушение регулятора хранилища сохранит все предметы, содержащиеся внутри, в качестве выброшенного предмета. Вы ничего не потеряете.
                        То же самое относится к разрушению или замене Стабилизаторов хранилища (об этом узнаете позже).
                        \\
                        \\
                        Аналогично шалкеровому ящику, Ваши предметы в безопасности!
                        """.formatted(COLOR_PURPLE));


        helper.page("size");
        this.lang("ru_ru").add(helper.pageTitle(), "Такое огромное хранилище!");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        Регулятор хранилища хранит до **128** различных типов предметов (_Позже узнаете, как увеличить размер_). Кроме того, размер в целом ограничивается до 256.000 ед. предметов. Неважно, 256.000 ли у Вас различных предметов или 256.000 ед. одного предмета, или солянка.
                        """.formatted(COLOR_PURPLE));

        helper.page("unique_items");
        this.lang("ru_ru").add(helper.pageTitle(), "Отличительные предметы");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        Предметы с уникальными свойствами ("NBT-данные"), напр.: повреждённое или зачарованное снаряжение, займёт 1 тип предмета за каждое различие. Напр.: 2 деревянных меча с двумя разным повреждениями занимают 2 типа предмета. 2 деревянных меча с одинаковым повреждением (или без), занимают 1 тип предмета.
                        """.formatted(COLOR_PURPLE));

        helper.page("config");
        this.lang("ru_ru").add(helper.pageTitle(), "Конфигурируемость");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        Количество типа предмета и ёмкость хранилища можно настроить в файле конфигурации "[#](%1$s)occultism-server.toml[#]()" в папке сохранения мира.
                        """.formatted(COLOR_PURPLE));

        helper.page("mods");
        this.lang("ru_ru").add(helper.pageTitle(), "Взаимодействие с модами");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        Для других модов регулятор хранилища работает как шалкеровый ящик: всё, что может взаимодействовать с ванильными сундуками и шалкеровыми ящиками, могут взаимодействовать с контроллером хранилища.
                        Машины, что считаются хранилищем, могут столкнуться с проблемами размеров стека.
                        """.formatted(COLOR_PURPLE));


        helper.page("matrix_ritual");
        //текст отсутствует

        helper.page("base_ritual");
        //текст отсутствует

        helper.page("recipe");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        Это действующий блок, который работает как хранилище: обязательно создайте его!
                        Размещение [](item://occultism:storage_controller_base) на землю из предыдущего шага — не сработает.
                        """.formatted(COLOR_PURPLE));
        //текст отсутствует


        helper.entry("storage_stabilizer");
        this.lang("ru_ru").add(helper.entryName(), "Расширение хранилища");

        helper.page("spotlight");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        Стабилизаторы хранилища увеличивают ёмкость хранилища в пространственном хранилище Актуатора хранилища. Чем выше уровень стабилизатора, тем больше получит дополнительного хранилища. Следующие записи покажут, как создать каждый уровень.
                        \\
                        \\
                        """.formatted(COLOR_PURPLE));

        helper.page("upgrade");
        this.lang("ru_ru").add(helper.pageTitle(), "Улучшение");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        Он **безопасно уничтожит стабилизатор хранилища**, чтобы его улучшить. Предметы в [Актуаторе хранилища](entry://storage/storage_controller) не будут утеряны или выброшены, — Вы попросту не сможете добавлять новые предметы, пока не добавите достаточно стабилизаторов хранилища, чтобы снова иметь свободные слоты.
                         """.formatted(COLOR_PURPLE));

        helper.page("build_instructions");
        this.lang("ru_ru").add(helper.pageTitle(), "Инструкция по установке");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        Регуляторы хранилища необходимо направить на [Пространственную матрицу](entry://crafting_rituals/craft_dimensional_matrix), иными словами **1 блок над [Актуатором хранилища](entry://storage/storage_controller)**.
                        \\
                        \\
                        Их может быть **до 5 блоков** вдали от Пространственной матрицы, и должны быть по прямой линии в зоне видимости. Ознакомьтесь со следующей страницей, касательно предполагаемой, очень простой установки.
                        """.formatted(COLOR_PURPLE));


        helper.page("demo");
        this.lang("ru_ru").add(helper.pageTitle(), "Установка стабилизаторов хранилища");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        **Примечание:** Вам не нужны все 4 стабилизатора, даже 1 увеличит размер Вашего хранилища.
                        """.formatted(COLOR_PURPLE));
    }

    private void addAdvancements() {
        //"advancements\.occultism\.(.*?)\.title": "(.*)",
        //this.advancementTitle\("\1", "\2"\);
        this.advancementTitle("root", "Occultism");
        this.advancementDescr("root", "Принять духовность!");
        this.advancementTitle("summon_foliot_crusher", "Размельчение руды");
        this.advancementDescr("summon_foliot_crusher", "Хрусть! Хрусть! Хрусть!");
        this.advancementTitle("familiars", "Occultism: Друзья");
        this.advancementDescr("familiars", "Воспользуйтесь ритуалом, чтобы вызвать фамильяра.");
        this.advancementDescr("familiar.bat", "Заманите обычную летучую мышь близко к летучей мыши-фамильяру.");
        this.advancementTitle("familiar.bat", "Каннибализм");
        this.advancementDescr("familiar.capture", "Заточите фамильяра в кольцо для фамильяра.");
        this.advancementTitle("familiar.capture", "Заточить каждого!");
        this.advancementDescr("familiar.cthulhu", "Омрачите ктулху-фамильяра.");
        this.advancementTitle("familiar.cthulhu", "Вы изверг!");
        this.advancementDescr("familiar.deer", "Понаблюдайте за тем, как бобёр-фамильяр опорожняется семенами демона.");
        this.advancementTitle("familiar.deer", "Демоническая какашка");
        this.advancementDescr("familiar.devil", "Прикажите демону-фамильяру изрыгнуть пламенем.");
        this.advancementTitle("familiar.devil", "Пламя Преисподней");
        this.advancementDescr("familiar.dragon_nugget", "Дайте кусочек золота дракону-фамильяру.");
        this.advancementTitle("familiar.dragon_nugget", "Договорились!");
        this.advancementDescr("familiar.dragon_ride", "Позвольте алчному фамильяру подобрать что-нибудь во время езды на драконе-фамильяре.");
        this.advancementTitle("familiar.dragon_ride", "В тесной взаимосвязи");
        this.advancementDescr("familiar.greedy", "Позвольте алчному фамильяру подобрать что-нибудь для Вас.");
        this.advancementTitle("familiar.greedy", "Посыльный");
        this.advancementDescr("familiar.party", "Потанцуйте с фамильяром.");
        this.advancementTitle("familiar.party", "Потанцуем!");
        this.advancementDescr("familiar.rare", "Заполучите редкий вид фамильяра.");
        this.advancementTitle("familiar.rare", "Редкий друг");
        this.advancementDescr("familiar.root", "Воспользуйтесь ритуалом, чтобы вызвать фамильяра.");
        this.advancementTitle("familiar.root", "Occultism: Друзья");
        this.advancementDescr("familiar.mans_best_friend", "Погладьте дракона-фамильяра, да и поиграйте с ним в игру «Принеси мяч».");
        this.advancementTitle("familiar.mans_best_friend", "Лучший друг человека");
        this.advancementTitle("familiar.blacksmith_upgrade", "В полной боевой экипировке!");
        this.advancementDescr("familiar.blacksmith_upgrade", "Позвольте кузнецу-фамильяру улучшить одного из Ваших фамильяров.");
        this.advancementTitle("familiar.guardian_ultimate_sacrifice", "Бескомпромиссное жертвоприношение");
        this.advancementDescr("familiar.guardian_ultimate_sacrifice", "Позвольте стражу-фамильяру умереть, чтобы спасти самого Вас.");
        this.advancementTitle("familiar.headless_cthulhu_head", "Какой ужас!");
        this.advancementDescr("familiar.headless_cthulhu_head", "Убейте Ктулху рядом с безголовым человеком-крысой-фамильяра.");
        this.advancementTitle("familiar.headless_rebuilt", "Мы можем воссоздать его");
        this.advancementDescr("familiar.headless_rebuilt", "\"Воссоздайте\" безголового человека-красу-фамильяра после его смерти.");
        this.advancementTitle("familiar.chimera_ride", "По коням!");
        this.advancementDescr("familiar.chimera_ride", "Оседлайте химеру-фамильяра в момент её полного насыщения.");
        this.advancementTitle("familiar.goat_detach", "Демонтаж");
        this.advancementDescr("familiar.goat_detach", "Дайте химере-фамильяру золотое яблоко.");
        this.advancementTitle("familiar.shub_niggurath_summon", "Чёрный козёл лесов");
        this.advancementDescr("familiar.shub_niggurath_summon", "Перевоплотите козу-фамильяра во что-нибудь ужасающее.");
        this.advancementTitle("familiar.shub_cthulhu_friends", "Страсть к сверхъестественному");
        this.advancementDescr("familiar.shub_cthulhu_friends", "Понаблюдайте за тем, как Шуб-Ниггурат и Ктулху держатся за руки.");
        this.advancementTitle("familiar.shub_niggurath_spawn", "Подумайте о детях!");
        this.advancementDescr("familiar.shub_niggurath_spawn", "Позвольте потомку Шуба-Ниггурата нанести урон по врагу взрывом.");
        this.advancementTitle("familiar.beholder_ray", "Смертельный луч");
        this.advancementDescr("familiar.beholder_ray", "Позвольте созерцателю-фамильяру напасть на врага.");
        this.advancementTitle("familiar.beholder_eat", "Голод");
        this.advancementDescr("familiar.beholder_eat", "Понаблюдайте за тем, как созерцатель-фамильяр сжирает потомка Шуб-Ниггурата.");
        this.advancementTitle("familiar.fairy_save", "Ангел-хранитель");
        this.advancementDescr("familiar.fairy_save", "Позвольте феи-фамильяру спасти от неминуемой смерти одного из Ваших фамильяров.");
        this.advancementTitle("familiar.mummy_dodge", "Ниндзя!");
        this.advancementDescr("familiar.mummy_dodge", "Уклонитесь от удара за счёт эффекта уклонения мумии-фамильяра.");
        this.advancementTitle("familiar.beaver_woodchop", "Дровосек");
        this.advancementDescr("familiar.beaver_woodchop", "Позвольте бобёру-фамильяру срубить дерево.");
		this.advancementTitle("chalks.root", "Occultism: Мелки");
        this.advancementDescr("chalks.root", "Яркий.");
        this.advancementTitle("chalks.white", "Белый мел");
        this.advancementDescr("chalks.white", "Для первого основания пентакля.");
        this.advancementTitle("chalks.light_gray", "Светло-серый мел");
        this.advancementDescr("chalks.light_gray", "Для второго основания пентакля.");
        this.advancementTitle("chalks.gray", "Серый мел");
        this.advancementDescr("chalks.gray", "Для третьего основания пентакля.");
        this.advancementTitle("chalks.black", "Чёрный мел");
        this.advancementDescr("chalks.black", "Для четвёртого основания пентакля.");
        this.advancementTitle("chalks.brown", "Коричневый мел");
        this.advancementDescr("chalks.brown", "На кого приманка?");
        this.advancementTitle("chalks.red", "Красный мел");
        this.advancementDescr("chalks.red", "Третий уровень!");
        this.advancementTitle("chalks.orange", "Оранжевый мел");
        this.advancementDescr("chalks.orange", "Третий уровень?");
        this.advancementTitle("chalks.yellow", "Жёлтый мел");
        this.advancementDescr("chalks.yellow", "Одержимость");
        this.advancementTitle("chalks.lime", "Лаймовый мел");
        this.advancementDescr("chalks.lime", "Второй уровень.");
        this.advancementTitle("chalks.green", "Зелёный мел");
        this.advancementDescr("chalks.green", "Привлечение дикой природы.");
        this.advancementTitle("chalks.cyan", "Cyan мел");
        this.advancementDescr("chalks.cyan", "Древние знания.");
        this.advancementTitle("chalks.light_blue", "Голубой мел");
        this.advancementDescr("chalks.light_blue", "Стабилизатор дикой природы.");
        this.advancementTitle("chalks.blue", "Синий мел");
        this.advancementDescr("chalks.blue", "Четвёртый уровень");
        this.advancementTitle("chalks.purple", "Пурпурный мел");
        this.advancementDescr("chalks.purple", "Наполнение");
        this.advancementTitle("chalks.magenta", "Фиолетовый мел");
        this.advancementDescr("chalks.magenta", "Мощь дракона");
        this.advancementTitle("chalks.pink", "Розовый мел");
        this.advancementDescr("chalks.pink", "Сила дикой природы.");
    }

    private void addKeybinds() {
        this.lang("ru_ru").add("key.occultism.category", "Occultism");
        this.lang("ru_ru").add("key.occultism.backpack", "Открыть сумку");
        this.lang("ru_ru").add("key.occultism.storage_remote", "Открыть средство доступа хранилища");
        this.lang("ru_ru").add("key.occultism.familiar.otherworld_bird", "Вкл./выкл. эффект кольца: Дрикрыл");
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
        this.lang("ru_ru").add("occultism.jei.pentacle", "Пентакль");

        this.lang("ru_ru").add(TranslationKeys.JEI_CRUSHING_RECIPE_MIN_TIER, "Мин. уровень дробильщика: %d");
        this.lang("ru_ru").add(TranslationKeys.JEI_CRUSHING_RECIPE_MAX_TIER, "Макс. уровень дробильщика:: %d");
        this.lang("ru_ru").add("jei.occultism.ingredient.tallow.description", "Для получения жира убивайте таких животных, как: \u00a72свиньи\u00a7r, \u00a72коровы\u00a7r, \u00a72овцы\u00a7r, \u00a72лошади\u00a7r и \u00a72ламы\u00a7r с помощью ножа мясника.");
        this.lang("ru_ru").add("jei.occultism.ingredient.otherstone.description", "Преимущественно встречается в Потусторонних Рощах. Виден только во время активного состояния \u00a76Третьего глаза\u00a7r. Обратитесь в \u00a76Справочник душ\u00a7r для получения дополнительной информации.");
        this.lang("ru_ru").add("jei.occultism.ingredient.otherworld_log.description", "Преимущественно встречается в Потусторонних Рощах. Виден только во время \u00a76Третьего глаза\u00a7r. Обратитесь в \u00a76Справочник душ\u00a7r для получения дополнительной информации.");
        this.lang("ru_ru").add("jei.occultism.ingredient.otherworld_sapling.description", "Можно получить у Торговца потусторонними саженцами. Можно увидеть и собрать без \u00a76Третьего глаза\u00a7r. Обратитесь в \u00a76Справочник душ\u00a7r для получения дополнительной информации касательно вызова Торговца.");
        this.lang("ru_ru").add("jei.occultism.ingredient.otherworld_sapling_natural.description", "Преимущественно встречается в Потусторонних Рощах. Виден только во время активного состояния \u00a76Третьего глаза\u00a7r. Обратитесь в \u00a76Справочник душ\u00a7r для получения дополнительной информации.");
        this.lang("ru_ru").add("jei.occultism.ingredient.otherworld_leaves.description", "Преимущественно встречается в Потусторонних Рощах. Виден только во время активного состояния \u00a76Третьего глаза\u00a7r. Обратитесь в \u00a76Справочник душ\u00a7r для получения дополнительной информации.");
        this.lang("ru_ru").add("jei.occultism.ingredient.iesnium_ore.description", "Встречается в Незере. Виден только во время активного состояния \u00a76Третьего глаза\u00a7r. Обратитесь в \u00a76Справочник душ\u00a7r для получения дополнительной информации.");
        this.lang("ru_ru").add("jei.occultism.ingredient.spirit_fire.description", "Бросьте \u00a76Плод «Видение демона»\u00a7r на землю и подожгите. Обратитесь в \u00a76Справочник душ\u00a7r для получения дополнительной информации.");
        this.lang("ru_ru").add("jei.occultism.ingredient.datura.description", "Может использоваться для исцеления всех вызванных духов и фамильяров, вызванные через ритуалы Occultism. Нажатие ПКМ по существу — исцелить на 1 сердце.");

        this.lang("ru_ru").add("jei.occultism.ingredient.spawn_egg.familiar_goat.description", "Накормите Химеру-фамильяра золотым яблоком для получения Козы-фамильяра. Обратитесь в \u00a76Справочник душ\u00a7r для получения дополнительной информации.");
        this.lang("ru_ru").add("jei.occultism.ingredient.spawn_egg.familiar_shub_niggurath.description", "Приведите Козу-фамильяра в лесной биом и щёлкните по ней сперва Чёрным красителем, затем Кремнем, а затем Оком Эндера для получения Шуба-Ниггурата-фамильяра. Обратитесь в \u00a76Справочник душ\u00a7r для получения дополнительной информации.");

        this.lang("ru_ru").add("jei.occultism.sacrifice", "Жертва: %s");
        this.lang("ru_ru").add("jei.occultism.summon", "Вызов: %s");
        this.lang("ru_ru").add("jei.occultism.job", "Должность: %s");
        this.lang("ru_ru").add("jei.occultism.item_to_use", "Предмет использования:");
        this.lang("ru_ru").add("jei.occultism.error.missing_id", "Не удалось определить рецепт.");
        this.lang("ru_ru").add("jei.occultism.error.invalid_type", "Недействительный тип рецепта.");
        this.lang("ru_ru").add("jei.occultism.error.recipe_too_large", "Рецепт больше 3х3.");
        this.lang("ru_ru").add("jei.occultism.error.recipe_items_missing", "Отсутствующие предметы будут игнорироваться.");
        this.lang("ru_ru").add("jei.occultism.error.recipe_no_items", "Совместимые предметы для рецепта не найдены.");
        this.lang("ru_ru").add("jei.occultism.error.recipe_move_items", "Переместить предметы");
        this.lang("ru_ru").add("jei.occultism.error.pentacle_not_loaded", "Пентакль не может быть загружен.");
        this.lang("ru_ru").add("item.occultism.jei_dummy.require_sacrifice", "Нужна жертва!");
        this.lang("ru_ru").add("item.occultism.jei_dummy.require_sacrifice.tooltip", "Для запуска этого ритуала требуется жертва. Обратитесь к Справочнику душ за подробными инструкциями.");
        this.lang("ru_ru").add("item.occultism.jei_dummy.require_item_use", "Требуется использовать предмет!");
        this.lang("ru_ru").add("item.occultism.jei_dummy.require_item_use.tooltip", "Для запуска этого ритуала требуется использовать специальный предмет. Обратитесь к Справочнику душ за подробными инструкциями.");
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
		this.addPentacle("summon_foliot", "Круг Авиара");
		this.addPentacle("summon_djinni", "Зов Офикса");
		this.addPentacle("summon_unbound_afrit", "Вызов свободного Абраса");
		this.addPentacle("summon_afrit", "Вызов Абраса");
		this.addPentacle("summon_unbound_marid", "Вызов усиленного Абраса");
		this.addPentacle("summon_marid", "Поощряемое привлечение Фатмы");
		this.addPentacle("possess_foliot", "Приманка Гидирина");
		this.addPentacle("possess_djinni", "Порабощение Айгана");
		this.addPentacle("possess_unbound_afrit", "Повелительный вызов свободного Абраса");
		this.addPentacle("possess_afrit", "Заклятие повиновения Абраса");
		this.addPentacle("possess_marid", "Присяга Ксеоврента");
		this.addPentacle("craft_foliot", "Спектральное вынуждение Изива");
        this.addPentacle("craft_djinni", "Связывание высшего Стригора");
		this.addPentacle("craft_afrit", "Пожизненное заточение Севиры");
        this.addPentacle("craft_marid", "Перевёрнутая башня Юфиксеса");
		this.addPentacle("resurrect_spirit", "Простой круг Саджи");
        this.addPentacle("contact_wild_spirit", "Вызов дикого Осорина");
        this.addPentacle("contact_eldritch_spirit", "Связь Роназа");
    }

    private void addPentacle(String id, String name) {
        this.add(Util.makeDescriptionId("multiblock", new ResourceLocation(Occultism.MODID, id)), name);
    }

    private void addRitualDummies() {
       this.lang("ru_ru").add("item.occultism.ritual_dummy.custom_ritual_summon", "Пользовательский макет ритуала");
       this.lang("ru_ru").add("item.occultism.ritual_dummy.custom_ritual_summon.tooltip", "Используется для сборок в качестве запасного варианта для пользовательских ритуалов, у которых нет своего предмета для ритуала.");
       this.lang("ru_ru").add("item.occultism.ritual_dummy.custom_ritual_possess", "Пользовательский макет ритуала");
       this.lang("ru_ru").add("item.occultism.ritual_dummy.custom_ritual_possess.tooltip", "Используется для сборок в качестве запасного варианта для пользовательских ритуалов, не имеющих собственного предмета для ритуала.");
       this.lang("ru_ru").add("item.occultism.ritual_dummy.custom_ritual_craft", "Пользовательский макет ритуала");
       this.lang("ru_ru").add("item.occultism.ritual_dummy.custom_ritual_craft.tooltip", "Используется для сборок в качестве запасного варианта для пользовательских ритуалов, не имеющих собственного предмета для ритуала.");
       this.lang("ru_ru").add("item.occultism.ritual_dummy.custom_ritual_misc", "Пользовательский макет ритуала");
       this.lang("ru_ru").add("item.occultism.ritual_dummy.custom_ritual_misc.tooltip", "Используется для сборок в качестве запасного варианта для пользовательских ритуалов, не имеющих собственного предмета для ритуала.");
	   this.lang("ru_ru").add("item.occultism.ritual_dummy.craft_wild_trim", "Ритуал: Кузнечный шаблон");
       this.lang("ru_ru").add("item.occultism.ritual_dummy.craft_wild_trim.tooltip", "Дикие духи скуют Кузнечный шаблон.");
       this.lang("ru_ru").add("item.occultism.ritual_dummy.craft_dimensional_matrix", "Ритуал: Создать пространственную матрицу");
       this.lang("ru_ru").add("item.occultism.ritual_dummy.craft_dimensional_matrix.tooltip", "Пространственная матрица — мостик в небольшое измерение, используемое для хранения предметов.");
       this.lang("ru_ru").add("item.occultism.ritual_dummy.craft_dimensional_mineshaft", "Ритуал: Создать пространственную шахту");
       this.lang("ru_ru").add("item.occultism.ritual_dummy.craft_dimensional_mineshaft.tooltip", "Позволяет духам-шахтёрам входить в шахтёрское измерение и выносить оттуда ресурсы.");
       this.lang("ru_ru").add("item.occultism.ritual_dummy.craft_infused_lenses", "Ритуал: Создать наполненные линзы");
       this.lang("ru_ru").add("item.occultism.ritual_dummy.craft_infused_lenses.tooltip", "Эти линзы используются для создания очков, дающие тебе возможность видеть за пределами физического мира.");
       this.lang("ru_ru").add("item.occultism.ritual_dummy.craft_infused_pickaxe", "Ритуал: Создать наполненную кирку");
       this.lang("ru_ru").add("item.occultism.ritual_dummy.craft_infused_pickaxe.tooltip", "Наполнение кирки.");

        this.lang("ru_ru").add("item.occultism.ritual_dummy.craft_miner_djinni_ores", "Ритуал: Вызов рудного Рудокопа-Джинна");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.craft_miner_djinni_ores.tooltip", "Вызывайте рудного Рудокопа-Джинна в магическую лампу.");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.craft_miner_foliot_unspecialized", "Ритуал: Вызов Рудокопа-Фолиота");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.craft_miner_foliot_unspecialized.tooltip", "Вызывайте Рудокопа-Фолиота в магическую лампу.");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.craft_miner_afrit_deeps", "Ритуал: Вызов Рудокопа-Африта для глубинносланцевой руды");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.craft_miner_afrit_deeps.tooltip", "Вызывайте Рудокопа-Африта для глубинносланцевой руды в магическую лампу.");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.craft_miner_marid_master", "Ритуал: Вызов мастера Рудокопа-Марида");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.craft_miner_marid_master.tooltip", "Вызывайте мастера Рудокопа-Марида в магическую лампу.");
		this.lang("ru_ru").add("item.occultism.ritual_dummy.craft_miner_ancient_eldritch", "Ритуал: Вызов сверхъестественного древнего рудокопа");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.craft_miner_ancient_eldritch.tooltip", "Вызывайте сверхъестественного древнего рудокопа в магическую лампу.");

        this.lang("ru_ru").add("item.occultism.ritual_dummy.craft_satchel", "Ритуал: Создать необычайно большую сумку");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.craft_satchel.tooltip", "Эта сумка позволяет хранить больше предметов, чем указывают её размеры, что делает её практичным спутником путешественника.");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.craft_soul_gem", "Ритуал: Создать камень души");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.craft_soul_gem.tooltip", "Камень души позволяет временно хранить живых существ.");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.craft_familiar_ring", "Ритуал: Создать кольцо для фамильяра");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.craft_familiar_ring.tooltip", "Кольцо для фамильяра позволяет заточить фамильяров. Кольцо будет накладывать эффект фамильяра на владельца.");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.craft_stabilizer_tier1", "Ритуал: Создать стабилизатор хранилища 1-го уровня");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.craft_stabilizer_tier1.tooltip", "Стабилизатор хранилища позволяет хранить больше предметов в средстве доступа пространственного хранилища.");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.craft_stabilizer_tier2", "Ритуал: Создать стабилизатор хранилища 2-го уровня");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.craft_stabilizer_tier2.tooltip", "Стабилизатор хранилища позволяет хранить больше предметов в средстве доступа пространственного хранилища.");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.craft_stabilizer_tier3", "Ритуал: Создать стабилизатор хранилища 3-го уровня");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.craft_stabilizer_tier3.tooltip", "Стабилизатор хранилища позволяет хранить больше предметов в средстве доступа пространственного хранилища.");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.craft_stabilizer_tier4", "Ритуал: Создать стабилизатор хранилища 4-го уровня");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.craft_stabilizer_tier4.tooltip", "Стабилизатор хранилища позволяет хранить больше предметов в средстве доступа пространственного хранилища.");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.craft_stable_wormhole", "Ритуал: Создать стабильную червоточину");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.craft_stable_wormhole.tooltip", "Стабильная червоточина позволяет получить доступ к пространственной матрице из удалённого место назначения.");
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
        this.lang("ru_ru").add("item.occultism.ritual_dummy.familiar_bat.tooltip", "Летучая мышь-фамильяр наделяет ночным зрением своего хозяина.");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.familiar_deer", "Ритуал: Вызов оленя-фамильяра");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.familiar_deer.tooltip", "Олень-фамильяр наделяет прыгучестью своего хозяина.");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.familiar_cthulhu", "Ритуал: Вызов ктулху-фамильяра");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.familiar_cthulhu.tooltip", "Ктулху-фамильяр наделяет водным дыханием своего хозяина.");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.familiar_devil", "Ритуал: Вызов дьявола-фамильяра");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.familiar_devil.tooltip", "Дьявол-фамильяр наделяет огнестойкостью своего хозяина.");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.familiar_dragon", "Ритуал: Вызов дракона-фамильяра");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.familiar_dragon.tooltip", "Дракон-фамильяр наделяет повышенным получением опыта своего хозяина.");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.familiar_blacksmith", "Ритуал: Вызов кузнеца-фамильяра");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.familiar_blacksmith.tooltip", "Кузнец-фамильяр берёт камень, добытый хозяином и использует его для починки снаряжения.");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.familiar_guardian", "Ритуал: Вызов стража-фамильяра");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.familiar_guardian.tooltip", "Страж-фамильяр оберегает от жестокой смерти своего хозяина.");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.familiar_headless", "Ритуал: Вызов безголового человека-крысы-фамильяра");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.familiar_headless.tooltip", "Безголовый человек-крыса-фамильяр увеличивает скорость атаки против врагов своего хозяина, чью голову он украл.");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.familiar_chimera", "Ритуал: Вызов химеры-фамильяра");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.familiar_chimera.tooltip", "Химеру-фамильяра можно кормить до полного роста для получения скорости атаки и урона. Как только она вырастит, игроки смогут её оседлать.");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.familiar_beholder", "Ритуал: Вызов созерцателя-фамильяра");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.familiar_beholder.tooltip", "Созерцатель-фамильяр подсвечивает близлежащих существ эффектом свечения и стреляет лазерными лучами во врагов.");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.familiar_fairy", "Ритуал: Вызов феи-фамильяра");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.familiar_fairy.tooltip", "Фея-фамильяр оберегает от смерти других фамильяров, истощает жизненную силу своих врагов и исцеляет своего хозяина и его фамильяров.");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.familiar_mummy", "Ритуал: Вызов мумии-фамильяра");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.familiar_mummy.tooltip", "Мумия-фамильяр — мастер боевых искусств и сражается с целью защитить своего хозяина.");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.familiar_beaver", "Ритуал: Вызов бобра-фамильяра");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.familiar_beaver.tooltip", "Бобёр-фамильяр занимается заготовкой древесины, когда они вырастают из саженца и наделяет своего хозяина повышенной скоростью рубки.");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.possess_enderman", "Ритуал: Вызов одержимого эндермена");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.possess_enderman.tooltip", "При убийстве Одержимый эндермен всегда выдаёт минимум 1 эндер-жемчуг.");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.possess_endermite", "Ритуал: Вызов одержимого эндермита");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.possess_endermite.tooltip", "Одержимый эндермит сбрасывает Эндерняк.");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.possess_skeleton", "Ритуал: Вызов одержимого скелета");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.possess_skeleton.tooltip", " При убийстве Одержимый скелет становится устойчивым к дневному свету и всегда сбрасывает минимум 1 череп скелета.");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.possess_ghast", "Ритуал: Вызов одержимого гаста");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.possess_ghast.tooltip", "При убийстве Одержимый гаст всегда сбрасывает минимум 1 слезу гаста.");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.possess_phantom", "Ритуал: Вызов одержимого фантома");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.possess_phantom.tooltip", "При убийстве Одержимый фантом всегда сбрасывает минимум 1 мембрану фантома, но его легко поймать в ловушку.");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.possess_weak_shulker", "Ритуал: Вызов одержимого слабого шалкера");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.possess_weak_shulker.tooltip", "При убийстве Одержимый слабый шалкер сбрасывает минимум 1 плод хоруса, и может сбросить панцирь шалкера.");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.possess_shulker", "Ритуал: Вызов одержимого шалкера");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.possess_shulker.tooltip", "При убийстве Одержимый шалкер всегда сбрасывает минимум 1 панцирь шалкера.");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.possess_elder_guardian", "Ритуал: Вызов одержимого древнего стража");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.possess_elder_guardian.tooltip", "При убийстве Одержимый древний страж сбрасывает минимум 1 раковину наутилуса, кроме того, может сбросить сердце моря и обычную добычу.");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.possess_warden", "Ритуал: Вызов одержимого хранителя");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.possess_warden.tooltip", "При убийстве Одержимый хранитель всегда сбрасывает минимум 6 осколков эха, и может сбросить другие древние вещи (кузнечные шаблоны и пластинки).");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.possess_hoglin", "Ритуал: Вызов одержимого хоглина");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.possess_hoglin.tooltip", "При убийстве у Одержимого хоглина есть шанс сбросить Незеритовое улучшение.");
		this.lang("ru_ru").add("item.occultism.ritual_dummy.possess_witch", "Ритуал: Вызов одержимой ведьмы");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.possess_witch.tooltip", "Одержимая ведьма будет сбрасывать наполненную бутылочку");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.possess_zombie_piglin", "Ритуал: Вызов одержимого зомби-пиглина");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.possess_zombie_piglin.tooltip", "Одержимый зомби-пиглин будет сбрасывать демоническое мясо.");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.possess_bee", "Ритуал: Вызов одержимой пчелы");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.possess_bee.tooltip", "Одержимая пчела будет сбрасывать проклятый мёд.");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.possess_goat", "Ритуал: Вызов козла милосердия");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.possess_goat.tooltip", "Козёл милосердия будет сбрасывать Сущность бессердечия.");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.summon_afrit_rain_weather", "Ритуал: Дождливая погода");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.summon_afrit_rain_weather.tooltip", "Вызывает связанного Африта, вызывающего дождь.");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.summon_afrit_thunder_weather", "Ритуал: Гроза");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.summon_afrit_thunder_weather.tooltip", "Вызывает связанного Африта, вызывающего грозу.");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.summon_djinni_clear_weather", "Ритуал: Ясная погода");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.summon_djinni_clear_weather.tooltip", "Вызывает Джинна, устраняющего погоду.");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.summon_djinni_day_time", "Ритуал: Вызов рассвета");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.summon_djinni_day_time.tooltip", "Вызывает Джинна, устанавливающего время в полдень.");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.summon_djinni_manage_machine", "Ритуал: Вызов Машиниста-Джинна");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.summon_djinni_manage_machine.tooltip", "Машинист автоматически перемещает предметы между системой пространственного хранилища и присоединёнными инвентарями, а также машинами.");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.summon_djinni_night_time", "Ритуал: Вызов сумерек");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.summon_djinni_night_time.tooltip", "Вызывает Джинна, устанавливающего время в сумерки.");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.summon_foliot_crusher", "Ритуал: Вызов Дробильщика-Фолиота");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.summon_foliot_crusher.tooltip", "Дробильщик — дух, вызываемый с целью размельчения руды в пыль, эффективно удваивая металлопродукцию.\n" + ChatFormatting.GRAY + ChatFormatting.ITALIC + "Примечание: некоторые рецепты могут требовать высокий уровень дробильщиков или низкий.");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.summon_djinni_crusher", "Ритуал: Вызов Дробильщика-Джинна");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.summon_djinni_crusher.tooltip", "Дробильщик — дух, вызываемый с целью размельчения руды в пыль, эффективно (гораздо), удваивая металлопродукцию. Этот дробильщик распадается (гораздо) медленнее низкоуровневых дробильщиков.\n" + ChatFormatting.GRAY + ChatFormatting.ITALIC + "Примечание: некоторые рецепты могут требовать высокий уровень дробильщиков или низкий.");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.summon_afrit_crusher", "Ритуал: Вызов Дробильщика-Африта");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.summon_afrit_crusher.tooltip", "Дробильщик — дух, вызываемый с целью размельчения руды в пыль, эффективно (гораздо), удваивая металлопродукцию. Этот дробильщик распадается (гораздо) медленнее низкоуровневых дробильщиков.\n" + ChatFormatting.GRAY + ChatFormatting.ITALIC + "Примечание: некоторые рецепты могут требовать высокий уровень дробильщиков или низкий.");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.summon_marid_crusher", "Ритуал: Вызов Дробильщика-Марида");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.summon_marid_crusher.tooltip", "Дробильщик — дух, вызываемый с целью размельчения руды в пыль, эффективно (гораздо), удваивая металлопродукцию. Этот дробильщик распадается (гораздо) медленнее низкоуровневых дробильщиков.\n" + ChatFormatting.GRAY + ChatFormatting.ITALIC + "Примечание: некоторые рецепты могут требовать высокий уровень дробильщиков или низкий.");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.summon_foliot_lumberjack", "Ритуал: Вызов Дровосека-Фолиота");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.summon_foliot_lumberjack.tooltip", "Дровосек занимается заготовкой древесины на своём рабочем месте и кладёт выпавшие предметы в указанный сундук.");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.summon_foliot_otherstone_trader", "Ритуал: Вызов потустороннего торговца");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.summon_foliot_otherstone_trader.tooltip", "Торговец потусторонним камнем обменивает обычный камень на потусторонний.");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.summon_foliot_sapling_trader", "Ритуал: Вызов торговца потусторонними саженцами");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.summon_foliot_sapling_trader.tooltip", "Торговец потусторонними саженцами обменивает естественные потусторонние саженцы на стабильные, что могут быть собраны без Третьего глаза.");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.summon_foliot_transport_items", "Ритуал: Вызов Транспортировщика-Фолиота");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.summon_foliot_transport_items.tooltip", "Транспортировщик перемещает все предметы из одного инвентаря в другой, к которым он получает доступ, включая машины.");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.summon_foliot_cleaner", "Ритуал: Вызов Дворника-Фолиота");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.summon_foliot_cleaner.tooltip", "Дворник подбирает выпавшие предметы и кладёт их в указанный инвентарь.");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.summon_unbound_afrit", "Ритуал: Вызов несвязанного Африта");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.summon_unbound_afrit.tooltip", "Вызывает несвязанного Африта, которого можно убить для получения Сущности Африта.");
		this.lang("ru_ru").add("item.occultism.ritual_dummy.summon_unbound_marid", "Ритуал: Вызов несвязанного Марида");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.summon_unbound_marid.tooltip", "Вызывает несвязанного Марида, которого можно убить для получения Сущности Марида.");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.wild_hunt", "Ритуал: Вызов Дикой Охоты");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.wild_hunt.tooltip", "В состав Дикой Охоты входит Визер-скелеты и их прислужники, а в качестве большого шанса с прислужников выпадут черепа визер-скелетов.");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.possess_unbound_otherworld_bird", "Ритуал: Овладение несвязанным дрикрылом");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.possess_unbound_otherworld_bird.tooltip", "Овладевает Дрикрылом-фамильяром, который может быть приручен кем-угодно, не только вызывателем.");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.possess_unbound_parrot", "Ритуал: Овладение несвязанным попугаем");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.possess_unbound_parrot.tooltip", "Овладевает попугаем, который может быть приручен кем-угодно, не только вызывателем.");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.resurrect_allay", "Ритуал: Очищение Вредины до Тихони");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.resurrect_allay.tooltip", "Очищение Вредины до Тихони в ходе процесса воскресения.");

        this.lang("ru_ru").add("item.occultism.ritual_dummy.wild_husk", "Ритуал: Вызов орды диких кадавров");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.wild_husk.tooltip", "В состав орды диких кадавров входит несколько Кадавров, с которых выпадают предметы, что связаны с испытаниями пустыни.");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.wild_drowned", "Ритуал: Вызов орды диких утопленников");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.wild_drowned.tooltip", "В состав орды диких утопленников входят несколько Утопленников, с которых выпадают предметы, что связаны с испытаниями океана.");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.wild_creeper", "Ритуал: Вызов орды диких криперов");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.wild_creeper.tooltip", "В состав орды диких криперов входят несколько заряженных Криперов, с которых выпадают много пластинок.");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.wild_silverfish", "Ритуал: Вызов орды диких чешуйниц");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.wild_silverfish.tooltip", "В состав орды диких чешуйниц входит несколько чешуйниц, с которых выпадают предметы, связанные с испытаниями руин.");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.weak_breeze", "Ритуал: Вызов одержимого слабого вихря");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.weak_breeze.tooltip", "С Одержимого слабого вихря выпадает Ключ испытаний и предметы, что связаны с камерой испытаний.");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.breeze", "Ритуал: Вызов одержимого вихря");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.breeze.tooltip", "С одержимого вихря выпадает Зловещий ключ испытаний и предметы, что связаны с камерой испытаний.");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.strong_breeze", "Ритуал: Вызов одержимого сильного вихря");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.strong_breeze.tooltip", "С Одержимого сильного вихря выпадает Навершие булавы и предметы, что связаны с камерой испытаний.");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.horde_illager", "Ритуал: Вызов одержимого заклинателя");
        this.lang("ru_ru").add("item.occultism.ritual_dummy.horde_illager.tooltip", "Вызывает одержимого Заклинателя и его приспешника.");

        this.add("item.occultism.ritual_dummy.craft_nature_paste", "Ритуал: Создание природной пасты");
        this.add("item.occultism.ritual_dummy.craft_nature_paste.tooltip", "Смешав ингредиенты, Фолиот создаст природную пасту.");
        this.add("item.occultism.ritual_dummy.craft_gray_paste", "Ритуал: Создание серой пасты");
        this.add("item.occultism.ritual_dummy.craft_gray_paste.tooltip", "Смешав ингредиенты, Джинн создаст серую пасту.");
        this.add("item.occultism.ritual_dummy.craft_research_fragment_dust", "Ритуал: Создать пыль фрагмента исследования");
        this.add("item.occultism.ritual_dummy.craft_research_fragment_dust.tooltip", "Фолиот наполнит опыт в изумрудную пыль.");
        this.add("item.occultism.ritual_dummy.craft_witherite_dust", "Ритуал: Создать визеритовую пыль");
        this.add("item.occultism.ritual_dummy.craft_witherite_dust.tooltip", "Африт наполнит Сущность визера в незеритовую пыль.");
        this.add("item.occultism.ritual_dummy.craft_dragonyst_dust", "Ритуал: Создать драконистовую пыль");
        this.add("item.occultism.ritual_dummy.craft_dragonyst_dust.tooltip", "Марид наполнит Сущность Эндер-Дракона в аметистовую пыль.");

        this.add("item.occultism.ritual_dummy.repair_chalks", "Ритуал: Починка мела");
        this.add("item.occultism.ritual_dummy.repair_chalks.tooltip", "Джинн починит Ваш мел.");
        this.add("item.occultism.ritual_dummy.repair_tools", "Ритуал: Починка инструмента");
        this.add("item.occultism.ritual_dummy.repair_tools.tooltip", "Африт починит Ваш инструмент.");
        this.add("item.occultism.ritual_dummy.repair_armors", "Ритуал: Починка брони");
        this.add("item.occultism.ritual_dummy.repair_armors.tooltip", "Африт починит Вашу броню.");
        this.add("item.occultism.ritual_dummy.repair_miners", "Ритуал: Восстановление рудокопа");
        this.add("item.occultism.ritual_dummy.repair_miners.tooltip", "Африт продлит срок Вашего договора с рудокопом.");

        this.lang("ru_ru").add(OccultismItems.RITUAL_DUMMY_SUMMON_DEMONIC_WIFE.get(), "Ритуал: Вызов демонической жены");

        this.lang("ru_ru").add(OccultismItems.RITUAL_DUMMY_SUMMON_DEMONIC_HUSBAND.get(), "Ритуал: Вызов демонического мужа");

        this.lang("ru_ru").add(OccultismItems.RITUAL_RESURRECT_FAMILIAR.get(), "Ритуал: Воскресение фамильяра");
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
        this.lang("ru_ru").add("dialog.occultism.beaver.snack_on_cooldown", "Эй, не жадничай!");
        this.lang("ru_ru").add("dialog.occultism.beaver.no_upgrade", "Кузнецу-фамильяру нужно улучшить Созерцателя, прежде чем он будет разбрасываться лакомствами!");
    }

    private void addModonomiconIntegration() {
        this.lang("ru_ru").add(I18n.RITUAL_RECIPE_ITEM_USE, "Использование предмета:");
        this.lang("ru_ru").add(I18n.RITUAL_RECIPE_SUMMON, "Вызов: %s");
        this.lang("ru_ru").add(I18n.RITUAL_RECIPE_JOB, "Должность: %s");
        this.lang("ru_ru").add(I18n.RITUAL_RECIPE_SACRIFICE, "Жертва: %s");
        this.lang("ru_ru").add(I18n.RITUAL_RECIPE_GO_TO_PENTACLE, "Открыть страницу пентакля: %s");
    }

    private void advancementTitle(String name, String s) {
        this.add(((TranslatableContents) OccultismAdvancementProvider.title(name).getContents()).getKey(), s);
    }

    private void advancementDescr(String name, String s) {
        this.add(((TranslatableContents) OccultismAdvancementProvider.descr(name).getContents()).getKey(), s);
    }

    private void addTags() {
        // Теги блока
        this.addBlockTag(OccultismTags.Blocks.OTHERWORLD_SAPLINGS, "Потусторонние саженцы");
        this.addBlockTag(OccultismTags.Blocks.CANDLES, "Свечи");
        this.addBlockTag(OccultismTags.Blocks.CAVE_WALL_BLOCKS, "Пещерная ограда");
        this.addBlockTag(OccultismTags.Blocks.NETHERRACK, "Незерак");
        this.addBlockTag(OccultismTags.Blocks.STORAGE_STABILIZER, "Стабилизатор хранилища");
        this.addBlockTag(OccultismTags.Blocks.TREE_SOIL, "Почва для дерева");
        this.addBlockTag(OccultismTags.Blocks.WORLDGEN_BLACKLIST, "Блоки генерации мира в чёрном списке");
        this.addBlockTag(OccultismTags.Blocks.IESNIUM_ORE, "Руда айзния");
        this.addBlockTag(OccultismTags.Blocks.SILVER_ORE, "Серебряная руда");
        this.addBlockTag(OccultismTags.Blocks.STORAGE_BLOCKS_IESNIUM, "Хранилище блоков айзния");
        this.addBlockTag(OccultismTags.Blocks.STORAGE_BLOCKS_SILVER, "Хранилище серебряный блоков");
        this.addBlockTag(OccultismTags.Blocks.STORAGE_BLOCKS_RAW_IESNIUM, "Хранилище рудных блоков айзния");
        this.addBlockTag(OccultismTags.Blocks.STORAGE_BLOCKS_RAW_SILVER, "Хранилище рудных блоков серебра");


        // Теги предмета
        this.addItemTag(OccultismTags.Items.OTHERWORLD_SAPLINGS, "Потусторонние саженцы");
        this.addItemTag(OccultismTags.Items.BOOK_OF_CALLING_DJINNI, "Книга вызова Джинна");
        this.addItemTag(OccultismTags.Items.BOOK_OF_CALLING_FOLIOT, "Книга вызова Фолиота");
		this.addItemTag(OccultismTags.Items.BOOKS_OF_BINDING, "Книги привязки");
        this.addItemTag(OccultismTags.Items.Miners.BASIC_RESOURCES, "Рудокопы базовых ресурсов");
        this.addItemTag(OccultismTags.Items.Miners.DEEPS, "Рудокопы глубинносланца");
        this.addItemTag(OccultismTags.Items.Miners.MASTER, "Рудокопы редких ресурсов");
        this.addItemTag(OccultismTags.Items.Miners.ORES, "Основные рудокопы");
        this.addItemTag(OccultismTags.Items.ELYTRA, "Элитры");
        this.addItemTag(OccultismTags.Items.OTHERWORLD_GOGGLES, "Потусторонние очки");
        this.addItemTag(OccultismTags.Items.DATURA_SEEDS, "Семена «Видение демона»");
        this.addItemTag(OccultismTags.Items.DATURA_CROP, "Видение демона");
        this.addItemTag(OccultismTags.Items.COPPER_DUST, "Медная пыль");
        this.addItemTag(OccultismTags.Items.GOLD_DUST, "Золотая пыль");
        this.addItemTag(OccultismTags.Items.IESNIUM_DUST, "Пыль айзния");
        this.addItemTag(OccultismTags.Items.IRON_DUST, "Железная пыль");
        this.addItemTag(OccultismTags.Items.SILVER_DUST, "Серебряная пыль");
        this.addItemTag(OccultismTags.Items.END_STONE_DUST, "Измельчённый эндерняк");
        this.addItemTag(OccultismTags.Items.OBSIDIAN_DUST, "Измельчённый обсидиан");
        this.addItemTag(OccultismTags.Items.IESNIUM_INGOT, "Слиток айзния");
        this.addItemTag(OccultismTags.Items.SILVER_INGOT, "Серебряный слиток");
        this.addItemTag(OccultismTags.Items.IESNIUM_NUGGET, "Кусочек айзния");
        this.addItemTag(OccultismTags.Items.SILVER_NUGGET, "Кусочек серебра");
        this.addItemTag(OccultismTags.Items.IESNIUM_ORE, "Руда айзния");
        this.addItemTag(OccultismTags.Items.SILVER_ORE, "Серебряная руда");
        this.addItemTag(OccultismTags.Items.RAW_IESNIUM, "Рудный айзний");
        this.addItemTag(OccultismTags.Items.RAW_SILVER, "Рудное серебро");
        this.addItemTag(OccultismTags.Items.STORAGE_BLOCK_IESNIUM, "Хранилище блоков айзния");
        this.addItemTag(OccultismTags.Items.STORAGE_BLOCK_SILVER, "Хранилище серебряный блоков");
        this.addItemTag(OccultismTags.Items.STORAGE_BLOCK_RAW_IESNIUM, "Хранилище блоков рудного айзния");
        this.addItemTag(OccultismTags.Items.STORAGE_BLOCK_RAW_SILVER, "Хранилище блоков серебра");
        this.addItemTag(OccultismTags.Items.TALLOW, "Жир");
        this.addItemTag(OccultismTags.Items.METAL_AXES, "Металлические топоры");
        this.addItemTag(OccultismTags.Items.MAGMA, "Магма");
        this.addItemTag(OccultismTags.Items.BOOKS, "Книги");
        this.addItemTag(OccultismTags.Items.FRUITS, "Фрукты");
		this.addItemTag(OccultismTags.Items.BLAZE_DUST, "Пылающая пыль");
        this.addItemTag(OccultismTags.Items.MANUALS, "Руководства");
    }

    private void addItemTag(ResourceLocation resourceLocation, String string) {
        this.add("tag.item." + resourceLocation.getNamespace() + "." + resourceLocation.getPath().replace("/", "."), string);
    }

    private void addBlockTag(TagKey<Block> block, String string) {
        this.addBlockTag(block.location(), string);
    }

    private void addItemTag(TagKey<Item> item, String string) {
        this.addItemTag(item.location(), string);
    }

    private void addBlockTag(ResourceLocation resourceLocation, String string) {
        this.add("tag.block." + resourceLocation.getNamespace() + "." + resourceLocation.getPath().replace("/", "."), string);
    }

    private void addEmiTranslations() {
        this.lang("ru_ru").add("emi.category.occultism.spirit_fire", "Духовный огонь");
        this.lang("ru_ru").add("emi.category.occultism.crushing", "Дробление");
        this.lang("ru_ru").add("emi.category.occultism.miner", "Пространственная шахта");
        this.lang("ru_ru").add("emi.category.occultism.ritual", "Ритуалы");
        this.lang("ru_ru").add("emi.occultism.item_to_use", "Предмет использования: %s");
    }

	private void addConditionMessages() {
     
    }

    private void addConfigurationTranslations() {
        this.addConfig("visual", "Визуальные настройки");
        this.addConfig("showItemTagsInTooltip", "Показывать теги предмета в подсказках");
        this.addConfig("disableDemonsDreamShaders", "Отключить шейдеры для Видения демона");
        this.addConfig("disableHolidayTheming", "Отключить шейдеры для Потусторонних очков");
        this.addConfig("useAlternativeDivinationRodRenderer", "Использовать альтернативный отрисовщик для Стержня прорицания");
        this.addConfig("whiteChalkGlyphColor", "Цвет белого глифового мела");
        this.addConfig("yellowChalkGlyphColor", "Цвет жёлтого глифового мела");
        this.addConfig("purpleChalkGlyphColor", "Цвет пурпурного глифового мела");
        this.addConfig("redChalkGlyphColor", "Цвет красного глифового мела");
		this.addConfig("lightGrayChalkGlyphColor", "Цвет светло-серого мелового глифа");
        this.addConfig("grayChalkGlyphColor", "Цвет серого мелового глифа");
        this.addConfig("blackChalkGlyphColor", "Цвет чёрного мелового глифа");
        this.addConfig("brownChalkGlyphColor", "Цвет коричневого мелового глифа");
        this.addConfig("orangeChalkGlyphColor", "Цвет оранжевого мелового глифа");
        this.addConfig("limeChalkGlyphColor", "Цвет лаймового мелового глифа");
        this.addConfig("greenChalkGlyphColor", "Цвет зелёного мелового глифа");
        this.addConfig("cyanChalkGlyphColor", "Цвет бирюзового мелового глифа");
        this.addConfig("lightBlueChalkGlyphColor", "Цвет голубого мелового глифа");
        this.addConfig("blueChalkGlyphColor", "Цвет синего мелового глифа");
        this.addConfig("magentaChalkGlyphColor", "Цвет фиолетового мелового глифа");
        this.addConfig("pinkChalkGlyphColor", "Цвет розового мелового глифа");

        this.addConfig("misc", "Прочие настройки");
        this.addConfig("syncJeiSearch", "Синхронизировать поиск с JEI");
        this.addConfig("divinationRodHighlightAllResults", "Подсвечивать все результаты Стержня прорицания");
        this.addConfig("divinationRodScanRange", "Радиус сканирования Жезла прорицания");
        this.addConfig("disableSpiritFireSuccessSound", "Отключить звук успешности Духовного огня");

        this.addConfig("storage", "Настройки хранилища");
        this.addConfig("stabilizerTier1AdditionalMaxItemTypes", "Макс. доп. типов предметов в стабилизаторе 1-го уровня");
        this.addConfig("stabilizerTier1AdditionalMaxTotalItemCount", "Макс. доп. общего количества предметов в стабилизаторе 1-го уровня");
        this.addConfig("stabilizerTier2AdditionalMaxItemTypes", "Макс. доп. типов предметов в стабилизаторе 2-го уровня");
        this.addConfig("stabilizerTier2AdditionalMaxTotalItemCount", "Макс. доп. общего количества предметов в стабилизаторе 2-го уровня");
        this.addConfig("stabilizerTier3AdditionalMaxItemTypes", "Макс. доп. типов предметов в стабилизаторе 3-го уровня");
        this.addConfig("stabilizerTier3AdditionalMaxTotalItemCount", "Макс. доп. общего количества предметов в стабилизаторе 3-го уровня");
        this.addConfig("stabilizerTier4AdditionalMaxItemTypes", "Макс. доп. типов предметов в стабилизаторе 4-го уровня");
        this.addConfig("stabilizerTier4AdditionalMaxTotalItemCount", "Макс. доп. общего количества предметов в стабилизаторе 4-го уровня");
        this.addConfig("controllerMaxItemTypes", "Макс. типов предметов в регуляторе");
        this.addConfig("controllerMaxTotalItemCount", "Макс. общее количество предмета в регуляторе");
        this.addConfig("unlinkWormholeOnBreak", "Отвязывать Червоточину при разрушении");

        this.addConfig("spirit_job", "Настройки занятия духа");
        this.addConfig("drikwingFamiliarSlowFallingSeconds", "Продолжительность эффекта плавного падения в сек., полученное благодаря Дрикрылу-фамильяру.");
        this.addConfig("tier1CrusherTimeMultiplier", "Коэффициент времени для дробильщика 1-го уровня.");
        this.addConfig("tier2CrusherTimeMultiplier", "Коэффициент времени для дробильщика 2-го уровня.");
        this.addConfig("tier3CrusherTimeMultiplier", "Коэффициент времени для дробильщика 3-го уровня.");
        this.addConfig("tier4CrusherTimeMultiplier", "Коэффициент времени для дробильщика 4-го уровня.");
        this.addConfig("tier1CrusherOutputMultiplier", "Коэффициент продукции для дробильщика 1-го уровня.");
        this.addConfig("tier2CrusherOutputMultiplier", "Коэффициент продукции для дробильщика 2-го уровня.");
        this.addConfig("tier3CrusherOutputMultiplier", "Коэффициент продукции для дробильщика 3-го уровня.");
        this.addConfig("tier4CrusherOutputMultiplier", "Коэффициент продукции для дробильщика 4-го уровня.");
        this.addConfig("crusherResultPickupDelay", "Задержка, прежде чем могут быть подобраны предметы из дробильщика.");
        this.addConfig("blacksmithFamiliarRepairChance", "Шанс, что Кузнец-фамильяр каждый такт будет чинить предмет.");
        this.addConfig("blacksmithFamiliarUpgradeCost", "Стоимость обновления предметов Кузнецом-фамильяром в уровнях опыта.");
        this.addConfig("blacksmithFamiliarUpgradeCooldown", "Перезарядка/в тактах, прежде чем Кузнец-фамильяр сможет снова обновить предметы.");

        this.addConfig("rituals", "Настройки ритуалов");
        this.addConfig("enableClearWeatherRitual", "Включить условия ритуала ясной погоды.");
        this.addConfig("enableRainWeatherRitual", "Включить условия ритуалу для вызова дождливой погоды.");
        this.addConfig("enableThunderWeatherRitual", "Включить условия ритуала для вызова грозы.");
        this.addConfig("enableDayTimeRitual", "Позволить ритуалу изменять время на день.");
        this.addConfig("enableNightTimeRitual", "Позволить ритуалу изменять время в ночь.");
        this.addConfig("enableRemainingIngredientCountMatching", "Включить соответствия оставшихся ингредиентов в рецептах ритуала.");
        this.addConfig("ritualDurationMultiplier", "Коэффициент для регулирования продолжительности всех ритуалов.");
        this.addConfig("possibleSpiritNames", "Возможные имена духов");

        this.addConfig("dimensional_mineshaft", "Настройки пространственной шахты");
        this.addConfig("miner_foliot_unspecialized", "Неспециализированный Рудокоп-Фолиот");
        this.addConfig("miner_djinni_ores", "Рудный Рудокоп-Джинн");
        this.addConfig("miner_afrit_deeps", "Рудокоп-Африт для глубинносланцевой руды");
        this.addConfig("miner_marid_master", "Мастер Рудокоп-Марид");
		this.addConfig("miner_ancient_eldritch", "Сверхъестественный древний рудокоп");

        this.addConfig("maxMiningTime", "Макс. время добычи");
        this.addConfig("rollsPerOperation", "Rolls Per Operation");
        this.addConfig("durability", "Прочность");
		
		this.addConfig("items", "Предметы");
        this.addConfig("anyOreDivinationRod", "Прорицание c:ores");
		this.addConfig("minerOutputBeforeBreak", "Сохранить рудокопов до разрушения");
    }

    private void addConfig(String key, String name) {
        this.add(Occultism.MODID + ".configuration." + key, name);
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
        this.addConditionMessages();
    }
}
