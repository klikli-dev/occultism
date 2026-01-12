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
import com.klikli_dev.occultism.registry.OccultismBlocks;
import com.klikli_dev.occultism.registry.OccultismEntities;
import com.klikli_dev.occultism.registry.OccultismItems;
import com.klikli_dev.occultism.registry.OccultismTags;
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
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;

public class RURUProvider extends AbstractModonomiconLanguageProvider {

    public static final String COLOR_PURPLE = "ad03fc";
    public static final String DEMONS_DREAM = "Видение демона";


    public RURUProvider(PackOutput gen) {
        super(gen, Occultism.MODID, "ru_ru");
    }

    public RURUProvider lang(String lang) {
        return this;
    }

    public void addItemMessages() {

        //"item\.occultism\.(.*?)\.(.*)": "(.*)",
        // this.add\(OccultismItems.\U\1\E.get\(\).getDescriptionId\(\)  + " \2", "\3"\);

        //book of callings use generic message base key, hence the manual string
        this.lang("ru_ru").add("item.occultism.book_of_calling" + ".message_target_uuid_no_match", "Дух на данный момент не связан с этой книгой. Нажмите Shift + ПКМ на духе, чтобы связать.");
        this.lang("ru_ru").add("item.occultism.book_of_calling" + ".message_target_linked", "Дух связан с книгой.");
        this.lang("ru_ru").add("item.occultism.book_of_calling" + ".message_target_cannot_link", "Духа невозможно связать с этой книгой. Книга призыва должна соответствовать задаче духа!");
        this.lang("ru_ru").add("item.occultism.book_of_calling" + ".message_target_entity_no_inventory", "У сущности нет инвентаря: её невозможно установить в качестве место хранения.");
        this.lang("ru_ru").add("item.occultism.book_of_calling" + ".message_spirit_not_found", "Дух, связанный с этой книгой не обитает в этой параллельной реальности.");
        this.lang("ru_ru").add("item.occultism.book_of_calling" + ".message_set_deposit", "%s будет вносить в [%s] со стороны [%s]");
        this.lang("ru_ru").add("item.occultism.book_of_calling" + ".message_set_deposit_entity", "%s будет передавать предметы в [%s]");
        this.lang("ru_ru").add("item.occultism.book_of_calling" + ".message_set_extract", "%s будет извлекать из [%s] со стороны [%s]");
        this.lang("ru_ru").add("item.occultism.book_of_calling" + ".message_set_base", "База для [%s] установлена на [%s]");
        this.lang("ru_ru").add("item.occultism.book_of_calling" + ".message_set_storage_controller", "%s теперь будет принимать заказы на создание из [%s]");
        this.lang("ru_ru").add("item.occultism.book_of_calling" + ".message_set_work_area_size", "%s теперь будет отслеживать рабочее место [%s]");
        this.lang("ru_ru").add("item.occultism.book_of_calling" + ".message_set_managed_machine", "Настройки для устройства [%s] обновлены.");
        this.lang("ru_ru").add("item.occultism.book_of_calling" + ".message_set_managed_machine_extract_location", "%s теперь будет извлекать из [%s] со стороны [%s]");
        this.lang("ru_ru").add("item.occultism.book_of_calling" + ".message_no_managed_machine", "Установите устройство, прежде чем установить место извлечения [%s]");

        this.lang("ru_ru").add(OccultismItems.STABLE_WORMHOLE.get().getDescriptionId() + ".message.set_storage_controller", "Стабильная червоточина связана с регулятором хранилища.");
		this.lang("ru_ru").add(OccultismItems.STABLE_WORMHOLE.get().getDescriptionId() + ".message.not_loaded", "Чанк для актуатора хранилища не загружен!");
		this.lang("ru_ru").add(OccultismItems.STABLE_WORMHOLE_DARK.get().getDescriptionId() + ".message.set_storage_controller", "Тёмная стабильная червоточина связана с регулятором хранилища.");
		this.lang("ru_ru").add(OccultismItems.STABLE_WORMHOLE_DARK.get().getDescriptionId() + ".message.not_loaded", "Чанк для актуатора хранилища не загружен!");
        this.lang("ru_ru").add(OccultismItems.STORAGE_REMOTE.get().getDescriptionId() + ".message.not_loaded", "Чанк для актуатора хранилища не загружен!");
        this.lang("ru_ru").add(OccultismItems.STORAGE_REMOTE.get().getDescriptionId() + ".message.linked", "Удалённое хранилище связано с актуатором.");
        this.lang("ru_ru").add(OccultismItems.DIVINATION_ROD.get().getDescriptionId() + ".message.no_linked_block", "Жезл истинного зрения не настроен на какой-либо материал.");
        this.lang("ru_ru").add(OccultismItems.DIVINATION_ROD.get().getDescriptionId() + ".message.linked_block", "Жезл прорицания настроен на [%s].");
        this.lang("ru_ru").add(OccultismItems.DIVINATION_ROD.get().getDescriptionId() + ".message.no_link_found", "Нет резонанса с этим блоком.");
        this.lang("ru_ru").add(OccultismItems.TRUE_SIGHT_STAFF.get().getDescriptionId() + ".message.no_linked_block", "Посох истинного зрения не настроен на материал.");
        this.lang("ru_ru").add(OccultismItems.TRUE_SIGHT_STAFF.get().getDescriptionId() + ".message.linked_block", "Посох истинного зрения настроен на [%s].");
        this.lang("ru_ru").add(OccultismItems.TRUE_SIGHT_STAFF.get().getDescriptionId() + ".message.no_link_found", "Нет резонанса с этим блоком.");
		this.lang("ru_ru").add(OccultismItems.FRAGILE_SOUL_GEM_ITEM.get().getDescriptionId() + ".message.entity_type_denied", "Хрупкие камни души не могут заточать этот тип существа.");
        this.lang("ru_ru").add(OccultismItems.SOUL_GEM_ITEM.get().getDescriptionId() + ".message.entity_type_denied", "Камни души не могут удерживать этот тип существа.");
        this.lang("ru_ru").add(OccultismItems.TRINITY_GEM_ITEM.get().getDescriptionId() + ".message.entity_type_denied", "Камни Троицы не способны удерживать этот тип существа.");
		this.lang("ru_ru").add(OccultismItems.VITALITY_COMPASS.get().getDescriptionId() + ".message.target_linked", "Компас жизни связан с [%s].");
        this.lang("ru_ru").add(OccultismItems.VITALITY_COMPASS.get().getDescriptionId() + ".message.target_blocked", "Это существо нельзя связать с компасом жизни.");
    }

    public void addItemTooltips() {
        //"item\.occultism\.(.*?)\.(.*)": "(.*)",
        // this.add\(OccultismItems.\U\1\E.get\(\).getDescriptionId\(\)  + " \2", "\3"\);
        this.lang("ru_ru").add(OccultismItems.BOOK_OF_BINDING_EMPTY.get().getDescriptionId() + ".tooltip", "Эта книга ещё не определена к какому-либо духу.");
        this.lang("ru_ru").add(OccultismItems.BOOK_OF_BINDING_FOLIOT.get().getDescriptionId() + ".tooltip", "Эта книга ещё не связана с Фолиотом.");
        this.lang("ru_ru").add(OccultismItems.BOOK_OF_BINDING_BOUND_FOLIOT.get().getDescriptionId() + ".tooltip", "Применяется для призыва Фолиота [%s].");
        this.lang("ru_ru").add(OccultismItems.BOOK_OF_BINDING_DJINNI.get().getDescriptionId() + ".tooltip", "Эта книга ещё не связана с Джинном.");
        this.lang("ru_ru").add(OccultismItems.BOOK_OF_BINDING_BOUND_DJINNI.get().getDescriptionId() + ".tooltip", "Применяется для призыва Джинна [%s].");
        this.lang("ru_ru").add(OccultismItems.BOOK_OF_BINDING_AFRIT.get().getDescriptionId() + ".tooltip", "Эта книга ещё не связана с Афритом.");
        this.lang("ru_ru").add(OccultismItems.BOOK_OF_BINDING_BOUND_AFRIT.get().getDescriptionId() + ".tooltip", "Применяется для призыва Африта [%s].");
        this.lang("ru_ru").add(OccultismItems.BOOK_OF_BINDING_MARID.get().getDescriptionId() + ".tooltip", "Эта книга ещё не связана с Маридом.");
        this.lang("ru_ru").add(OccultismItems.BOOK_OF_BINDING_BOUND_MARID.get().getDescriptionId() + ".tooltip", "Применяется для призыва Марида [%s].");
		this.lang("ru_ru").addTooltip(OccultismItems.FLAME_AUTOMATION.get(), "%s");
        this.lang("ru_ru").addAutoTooltip(OccultismItems.FLAME_AUTOMATION.get(), "Приобретается при завершении ритуала без получаемого предмета, если над центральной ритуальной чашей есть перевёрнутая жертвенная чаша в пределах трёх блоков.");

        this.lang("ru_ru").add("item.occultism.book_of_calling_foliot" + ".tooltip", "Фолиот [%s]");
        this.lang("ru_ru").add("item.occultism.book_of_calling_foliot" + ".tooltip_dead", "[%s] покинул эту параллельную реальность..");
        this.lang("ru_ru").add("item.occultism.book_of_calling_foliot" + ".tooltip.extract", "Извлекает из [%s]");
        this.lang("ru_ru").add("item.occultism.book_of_calling_foliot" + ".tooltip.deposit", "Вносит в [%s]");
        this.lang("ru_ru").add("item.occultism.book_of_calling_foliot" + ".tooltip.deposit_entity", "Передаёт предметы в [%s]");
        this.lang("ru_ru").add("item.occultism.book_of_calling_djinni" + ".tooltip", "Джинн [%s]");
        this.lang("ru_ru").add("item.occultism.book_of_calling_djinni" + ".tooltip_dead", "[%s] покинул эту параллельную реальность.");
        this.lang("ru_ru").add("item.occultism.book_of_calling_djinni" + ".tooltip.extract", "Извлекает из [%s]");
        this.lang("ru_ru").add("item.occultism.book_of_calling_djinni" + ".tooltip.deposit", "Вносит в [%s]");
        this.lang("ru_ru").add(OccultismItems.FAMILIAR_RING.get().getDescriptionId() + ".tooltip", "Занят фамильяром %s\n%s");
        this.lang("ru_ru").add(OccultismItems.FAMILIAR_RING.get().getDescriptionId() + ".tooltip.familiar_type", "[Тип: %s]");
        this.lang("ru_ru").add(OccultismItems.FAMILIAR_RING.get().getDescriptionId() + ".tooltip.empty", "Не содержит фамильяра.");
		this.lang("ru_ru").add(OccultismItems.VITALITY_COMPASS.get().getDescriptionId() + ".tooltip", "Поиск [%s]");

        this.lang("ru_ru").add("item.minecraft.diamond_sword.occultism_spirit_tooltip", "%s заключён в этом мече. Пусть враги трепещут перед его тщеславием.");

        this.lang("ru_ru").add(OccultismItems.STABLE_WORMHOLE.get().getDescriptionId() + ".tooltip.unlinked", "Не связана с регулятором хранилища.");
        this.lang("ru_ru").add(OccultismItems.STABLE_WORMHOLE.get().getDescriptionId() + ".tooltip.linked", "Связана с регулятором хранилища на [%s]");
		this.lang("ru_ru").add(OccultismItems.STABLE_WORMHOLE_DARK.get().getDescriptionId() + ".tooltip.unlinked", "Связь с актуатором хранилища не установлена.");
        this.lang("ru_ru").add(OccultismItems.STABLE_WORMHOLE_DARK.get().getDescriptionId() + ".tooltip.linked", "Связана с регулятором хранилища на %s.");
        this.lang("ru_ru").add(OccultismItems.STORAGE_REMOTE.get().getDescriptionId() + ".tooltip", "Удалённо получает доступ к сетевому хранилищу.");
		this.lang("ru_ru").add(OccultismItems.STORAGE_REMOTE.get().getDescriptionId() + ".tooltip.spirit", "[%s] заключён в средстве доступа.");
		this.lang("ru_ru").add(OccultismItems.STORAGE_REMOTE.get().getDescriptionId() + ".tooltip.linked", "Связано с [%s]");
        this.lang("ru_ru").add(OccultismItems.STORAGE_REMOTE.get().getDescriptionId() + ".tooltip.unlinked", "Не связан с актуатором хранилища.");
        this.lang("ru_ru").add("block.occultism.otherglass.auto_tooltip", "Наденьте потусторонние очки, чтобы видеть стекло.");

        this.lang("ru_ru").add(OccultismItems.DIVINATION_ROD.get().getDescriptionId() + ".tooltip.no_linked_block", "Не настроен на какой-либо материал.");
        this.lang("ru_ru").add(OccultismItems.DIVINATION_ROD.get().getDescriptionId() + ".tooltip.linked_block", "Настроен на %s.");
        this.lang("ru_ru").add(OccultismItems.TRUE_SIGHT_STAFF.get().getDescriptionId() + ".tooltip.no_linked_block", "Не настроен на какой-либо материал.");
        this.lang("ru_ru").add(OccultismItems.TRUE_SIGHT_STAFF.get().getDescriptionId() + ".tooltip.linked_block", "Настроен на [%s]");
        this.lang("ru_ru").add(OccultismItems.DIMENSIONAL_MATRIX.get().getDescriptionId() + ".tooltip", "[%s] связан с этой пространственной матрицей.");
        this.lang("ru_ru").add(OccultismItems.INFUSED_PICKAXE.get().getDescriptionId() + ".tooltip", "[%s] заключён в этой кирке.");
        this.lang("ru_ru").add(OccultismItems.MINER_FOLIOT_UNSPECIALIZED.get().getDescriptionId() + ".tooltip", "[%s] будет добывать разные и глубинносланцевые руды в шахтёрском измерении.");
        this.lang("ru_ru").add(OccultismItems.MINER_DJINNI_ORES.get().getDescriptionId() + ".tooltip", "[%s] будет добывать разные руды в шахтёрском измерении.");
        this.lang("ru_ru").add(OccultismItems.MINER_DEBUG_UNSPECIALIZED.get().getDescriptionId() + ".tooltip", "Отладочный рудокоп будет добывать разные блоки в шахтёрском измерении.");
        this.lang("ru_ru").add(OccultismItems.MINER_AFRIT_DEEPS.get().getDescriptionId() + ".tooltip", "[%s] будет добывать и глубинносланцевые руды в шахтёрском измерении.");
        this.lang("ru_ru").add(OccultismItems.MINER_MARID_MASTER.get().getDescriptionId() + ".tooltip", "[%s] будет добывать: разные, глубинносланцевые и редкие руды в шахтёрском измерении.");
        this.lang("ru_ru").add(OccultismItems.MINER_ANCIENT_ELDRITCH.get().getDescriptionId() + ".tooltip", "Нечто будет добывать: разные рудные блоки, редкие руды и самоцветные блоки в шахтёрском измерении.");
		this.lang("ru_ru").add(OccultismItems.MAGIC_LAMP_EMPTY.get().getDescriptionId() + ".tooltip_filled", "[%s] заключён в этой лампе.\n %s: %s");
        this.lang("ru_ru").add(OccultismItems.MAGIC_LAMP_EMPTY.get().getDescriptionId() + ".tooltip_empty", "Используйте на духе-работнике, чтобы поймать его.");
        this.lang("ru_ru").add(OccultismItems.MAGIC_LAMP_EMPTY.get().getDescriptionId() + ".spirit_message_0", "<%s> Выпусти меня немедленно!");
        this.lang("ru_ru").add(OccultismItems.MAGIC_LAMP_EMPTY.get().getDescriptionId() + ".spirit_message_1", "<%s> Здесь так тесно.");
        this.lang("ru_ru").add(OccultismItems.MAGIC_LAMP_EMPTY.get().getDescriptionId() + ".spirit_message_2", "<%s> Я выберусь отсюда.");
        this.lang("ru_ru").add(OccultismItems.MAGIC_LAMP_EMPTY.get().getDescriptionId() + ".spirit_message_3", "<%s> Ты должен проявить больше сочувствия.");
        this.lang("ru_ru").add(OccultismItems.MAGIC_LAMP_EMPTY.get().getDescriptionId() + ".spirit_message_4", "<%s> Где мы находимся?");
        this.lang("ru_ru").add(OccultismItems.MAGIC_LAMP_EMPTY.get().getDescriptionId() + ".spirit_message_5", "<%s> Все духи важны!");
        this.lang("ru_ru").add(OccultismItems.MAGIC_LAMP_EMPTY.get().getDescriptionId() + ".spirit_message_6", "<%s> Я осваиваюсь в этой темнице.");
        this.lang("ru_ru").add(OccultismItems.MAGIC_LAMP_EMPTY.get().getDescriptionId() + ".spirit_message_7", "<%s> Ты можешь мне дать ещё потусторонней эссенции?");
        this.lang("ru_ru").add(OccultismItems.MAGIC_LAMP_EMPTY.get().getDescriptionId() + ".spirit_message_8", "<%s> Ты пожалеешь, если не выпустишь меня!");
        this.lang("ru_ru").add(OccultismItems.MAGIC_LAMP_EMPTY.get().getDescriptionId() + ".spirit_message_9", "<%s> Какое твоё выдающееся желание?");
		this.lang("ru_ru").add(OccultismItems.FRAGILE_SOUL_GEM_ITEM.get().getDescriptionId() + ".tooltip_filled", "Содержит пойманного [%s]\n" + ChatFormatting.RED + "Разрушится после освобождения существа!");
        this.lang("ru_ru").add(OccultismItems.FRAGILE_SOUL_GEM_ITEM.get().getDescriptionId() + ".tooltip_empty", "Используйте на существе для его поимки.\n" + ChatFormatting.RED + "Разрушается после однократного использования.");
        this.lang("ru_ru").add(OccultismItems.SOUL_GEM_ITEM.get().getDescriptionId() + ".tooltip_filled", "Содержит пойманного [%s]");
        this.lang("ru_ru").add(OccultismItems.SOUL_GEM_ITEM.get().getDescriptionId() + ".tooltip_empty", "Используйте на существе для его поимки.");
        this.lang("ru_ru").add(OccultismItems.TRINITY_GEM_ITEM.get().getDescriptionId() + ".tooltip_filled", "Содержит пойманного [%s]");
        this.lang("ru_ru").add(OccultismItems.TRINITY_GEM_ITEM.get().getDescriptionId() + ".tooltip_empty", "Используйте на существе для его поимки.\n" + ChatFormatting.GRAY + "Можно ловить боссов.");
        this.lang("ru_ru").add(OccultismItems.SATCHEL.get().getDescriptionId() + ".tooltip", "[%s] заключён в этой наплечной сумке.");
		this.lang("ru_ru").add(OccultismItems.ENDER_SATCHEL.get().getDescriptionId() + ".tooltip", "[%s] заключён в этой наплечной сумке.");
        this.lang("ru_ru").add(OccultismItems.ENDER_SATCHEL.get().getDescriptionId() + ".tooltip_linked", "Привязан к игроку: %s");
        this.lang("ru_ru").add(OccultismItems.ENDER_SATCHEL.get().getDescriptionId() + ".chest_menu", "Эндер-наплечная сумка [%s]");
        this.lang("ru_ru").add(OccultismItems.RITUAL_SATCHEL_T1.get().getDescriptionId() + ".tooltip", "[%s] заключён в этой наплечной сумке.");
        this.lang("ru_ru").add(OccultismItems.RITUAL_SATCHEL_T2.get().getDescriptionId() + ".tooltip", "[%s] заключён в этой наплечной сумке.");
		this.lang("ru_ru").add(OccultismItems.KNOWLEDGE_TABLET.get().getDescriptionId() + ".tooltip", "[%s] связан с этой скрижалью.\n Накоплено опыта: %s");

        this.lang("ru_ru").add(OccultismItems.SOUL_SHARD_ITEM.get().getDescriptionId() + ".tooltip_filled", "Содержит душу [%s].\n Можно использовать для воскресения.");
        this.lang("ru_ru").add(OccultismItems.SOUL_SHARD_ITEM.get().getDescriptionId() + ".tooltip_empty", "Выпадает с фамильяра после его преждевременной смерти.\n Можно использовать для его воскресения.");
    }

    private void addItems() {
        //Notepad++ magic:
        //"item\.occultism\.(.*)": "(.*)"
        //this.addItem\(OccultismItems.\U\1\E, "\2"\);

        this.add("itemGroup.occultism", "Occultism");

        this.lang("ru_ru").addItem(OccultismItems.PENTACLE_SUMMON, "Пентакль для призыва");
        this.lang("ru_ru").addItem(OccultismItems.PENTACLE_POSSESS, "Пентакль для завладения");
        this.lang("ru_ru").addItem(OccultismItems.PENTACLE_CRAFT, "Создание пентакля");
        this.lang("ru_ru").addItem(OccultismItems.PENTACLE_MISC, "Пентакль для разного");
        this.lang("ru_ru").addItem(OccultismItems.REPAIR_ICON, "Иконка починки");
        this.lang("ru_ru").addItem(OccultismItems.RESURRECT_ICON, "Иконка воскресения");
        this.lang("ru_ru").addItem(OccultismItems.MYSTERIOUS_EGG_ICON, "Иконка таинственного яйца");
        this.lang("ru_ru").addItem(OccultismItems.DEBUG_WAND, "Жезл отладки");
        this.lang("ru_ru").addItem(OccultismItems.DEBUG_FOLIOT_LUMBERJACK, "Призыв отладочного Фолиота-дровосека");
		this.lang("ru_ru").addItem(OccultismItems.DEBUG_FOLIOT_FARMER, "Призыв отладочного Фолиота-фермера");
        this.lang("ru_ru").addItem(OccultismItems.DEBUG_FOLIOT_TRANSPORT_ITEMS, "Призыв отладочного Фолиота-транспортировщика");
        this.lang("ru_ru").addItem(OccultismItems.DEBUG_FOLIOT_CLEANER, "Призыв отладочного Фолиота-уборщика");
        this.lang("ru_ru").addItem(OccultismItems.DEBUG_FOLIOT_TRADER_ITEM, "Призыв отладочного Фолиота-торговца");
        this.lang("ru_ru").addItem(OccultismItems.DEBUG_DJINNI_MANAGE_MACHINE, "Призыв отладочного Джинна-станочника");
        this.lang("ru_ru").addItem(OccultismItems.DEBUG_DJINNI_TEST, "Призыв отладочного тестового Джинна");
        this.lang("ru_ru").addAutoTooltip(OccultismItems.DIVINATION_ROD.get(),
                """
                        Ничего не видите?
                        В лексиконе духов ознакомьтесь со страницей «Решение проблем»!
                        Найдите иконку стержня прорицания во вкладке "Знакомство".
                        """
        );
        this.lang("ru_ru").addItem(OccultismItems.RITUAL_SATCHEL_T1, "Ритуальная наплечная сумка подмастерья");
        this.lang("ru_ru").addAutoTooltip(OccultismItems.RITUAL_SATCHEL_T1.get(),
                """
                        Обычная ритуальная наплечная сумка может помещать ритуальные круги поблочно.
                        Нажмите ПКМ на предосмотренном блоке, чтобы поставить его из наплечной сумки.
                        Нажмите Shift + ПКМ, чтобы открыть наплечную сумку и добавить ингредиенты для ритуала.
                        Предмет с прочностью будет использоваться до тех пор, пока не останется 1 единица прочности, при этом эффект мерцания прекратится.
                        """
        );
        this.lang("ru_ru").addItem(OccultismItems.RITUAL_SATCHEL_T2, "Ритуальная наплечная сумка ручной работы");
        this.lang("ru_ru").addAutoTooltip(OccultismItems.RITUAL_SATCHEL_T2.get(),
                """
                        Улучшенная ритуальная наплечная сумка может помещать целые ритуальные круги сразу.
                        Нажмите ПКМ на каком-либо предосмотренном блоке, чтобы поместить все блоки из наплечной сумки.
                        Нажмите Shift + ПКМ, чтобы открыть наплечную сумку и добавить ингредиенты для ритуала.
                        Нажмите ПКМ на золотую чашу, чтобы убрать ритуальный круг и собрать ингредиенты.
                        Предмет с прочностью будет использоваться до тех пор, пока не останется 1 единица прочности, при этом эффект мерцания прекратится.
                        """
        );

        this.lang("ru_ru").add(TranslationKeys.RITUAL_SATCHEL_NO_PREVIEW_IN_WORLD, "Вам нужно активировать предосмотр пентакля с помощью лексикона духов.");
        this.lang("ru_ru").add(TranslationKeys.RITUAL_SATCHEL_NO_PREVIEW_BLOCK_TARGETED, "Вам нужно нацелиться ритуальной наплечной сумкой на предосмотренном блоке.");
        this.lang("ru_ru").add(TranslationKeys.RITUAL_SATCHEL_NO_VALID_ITEM_IN_SATCHEL, "Нет допустимого предмета в наплечной сумке для этого предосмотренного блока.");
        this.lang("ru_ru").add(TranslationKeys.RITUAL_SATCHEL_BLOCK_ABOVE_NOT_AIR, "Блок над нажатой позиции не пустой.");
        this.lang("ru_ru").add(TranslationKeys.RITUAL_SATCHEL_BLOCK_AT_POSITION_NOT_AIR, "Блок на нажатой позиции не пустой.");
        this.lang("ru_ru").add(TranslationKeys.RITUAL_SATCHEL_INVALID_MATCHER, "Невозможно поставить блок на месте КАКОГО-ЛИБО или DISPLAY_ONLY многоблока-согласоветеля.");
		this.lang("ru_ru").add(TranslationKeys.RITUAL_SATCHEL_GLYPH_CANNOT_SURVIVE, "Сюда нельзя поставить глиф.");
		this.lang("ru_ru").add(TranslationKeys.RITUAL_SATCHEL_WILL_BREAK_ITEM, "Какой-то предмет сломан. Почините его!");

		this.addItem(OccultismItems.KNOWLEDGE_TABLET, "Скрижаль знаний");
        this.addAutoTooltip(OccultismItems.KNOWLEDGE_TABLET.get(),
                """
                        Нажатие ПКМ, чтобы сохранить весь ваш опыт.
                        Нажатие Shift + ПКМ, чтобы вернуть весь накопленный опыт.
                        На основании числительной оценки (очень близкой суммы), может наложиться небольшой "налог".
                        """
        );

        this.lang("ru_ru").addItem(OccultismItems.CHALK_YELLOW, "Жёлтый мелок");
        this.lang("ru_ru").addItem(OccultismItems.CHALK_PURPLE, "Фиолетовый мелок");
        this.lang("ru_ru").addItem(OccultismItems.CHALK_RED, "Красный мелок");
        this.lang("ru_ru").addItem(OccultismItems.CHALK_WHITE, "Белый мелок");
        this.lang("ru_ru").addItem(OccultismItems.CHALK_LIGHT_GRAY, "Светло-серый мелок");
        this.lang("ru_ru").addItem(OccultismItems.CHALK_GRAY, "Серый мелок");
        this.lang("ru_ru").addItem(OccultismItems.CHALK_BLACK, "Чёрный мелок");
        this.lang("ru_ru").addItem(OccultismItems.CHALK_BROWN, "Коричневый мелок");
        this.lang("ru_ru").addItem(OccultismItems.CHALK_ORANGE, "Оранжевый мелок");
        this.lang("ru_ru").addItem(OccultismItems.CHALK_LIME, "Лаймовый мелок");
        this.lang("ru_ru").addItem(OccultismItems.CHALK_GREEN, "Зелёный мелок");
        this.lang("ru_ru").addItem(OccultismItems.CHALK_CYAN, "Бирюзовый мелок");
        this.lang("ru_ru").addItem(OccultismItems.CHALK_LIGHT_BLUE, "Голубой мелок");
        this.lang("ru_ru").addItem(OccultismItems.CHALK_BLUE, "Синий мелок");
        this.lang("ru_ru").addItem(OccultismItems.CHALK_MAGENTA, "Пурпурный мелок");
        this.lang("ru_ru").addItem(OccultismItems.CHALK_PINK, "Розовый мелок");
        this.lang("ru_ru").addItem(OccultismItems.CHALK_RAINBOW, "Радужный мелок");
		this.lang("ru_ru").addAutoTooltip(OccultismItems.CHALK_RAINBOW, "Нажатие Shift + ПКМ на глифе, чтобы стереть.");
        this.lang("ru_ru").addItem(OccultismItems.CHALK_VOID, "Мелок пустоты");
		this.lang("ru_ru").addAutoTooltip(OccultismItems.CHALK_VOID, "Нажатие Shift + ПКМ на глифе, чтобы стереть.");
        this.lang("ru_ru").addItem(OccultismItems.CHALK_YELLOW_IMPURE, "Осквернённый жёлтый мелок");
        this.lang("ru_ru").addItem(OccultismItems.CHALK_PURPLE_IMPURE, "Осквернённый фиолетовый мелок");
        this.lang("ru_ru").addItem(OccultismItems.CHALK_RED_IMPURE, "Осквернённый красный мелок");
        this.lang("ru_ru").addItem(OccultismItems.CHALK_WHITE_IMPURE, "Осквернённый белый мелок");
        this.lang("ru_ru").addItem(OccultismItems.CHALK_LIGHT_GRAY_IMPURE, "Осквернённый светло-серый мелок");
        this.lang("ru_ru").addItem(OccultismItems.CHALK_GRAY_IMPURE, "Осквернённый серый мелок");
        this.lang("ru_ru").addItem(OccultismItems.CHALK_BLACK_IMPURE, "Осквернённый чёрный мелок");
        this.lang("ru_ru").addItem(OccultismItems.CHALK_BROWN_IMPURE, "Осквернённый коричневый мелок");
        this.lang("ru_ru").addItem(OccultismItems.CHALK_ORANGE_IMPURE, "Осквернённый оранжевый мелок");
        this.lang("ru_ru").addItem(OccultismItems.CHALK_LIME_IMPURE, "Осквернённый лаймовый мелок");
        this.lang("ru_ru").addItem(OccultismItems.CHALK_GREEN_IMPURE, "Осквернённый зелёный мелок");
        this.lang("ru_ru").addItem(OccultismItems.CHALK_CYAN_IMPURE, "Осквернённый бирюзовый мелок");
        this.lang("ru_ru").addItem(OccultismItems.CHALK_LIGHT_BLUE_IMPURE, "Осквернённый голубой мелок");
        this.lang("ru_ru").addItem(OccultismItems.CHALK_BLUE_IMPURE, "Осквернённый синий мелок");
        this.lang("ru_ru").addItem(OccultismItems.CHALK_MAGENTA_IMPURE, "Осквернённый пурпурный мелок");
        this.lang("ru_ru").addItem(OccultismItems.CHALK_PINK_IMPURE, "Осквернённый розовый мелок");
        this.lang("ru_ru").addItem(OccultismItems.BRUSH, "Щётка от мелков");
        this.lang("ru_ru").addItem(OccultismItems.AFRIT_ESSENCE, "Сущность Африта");
        this.lang("ru_ru").addItem(OccultismItems.PURIFIED_INK, "Очищенный чернила");
        this.lang("ru_ru").addItem(OccultismItems.AWAKENED_FEATHER, "Пробуждённое перо");
        this.lang("ru_ru").addItem(OccultismItems.TABOO_BOOK, "Книга табу");
        this.lang("ru_ru").addItem(OccultismItems.BOOK_OF_BINDING_EMPTY, "Пустая книга привязки");
        this.lang("ru_ru").addItem(OccultismItems.BOOK_OF_BINDING_FOLIOT, "Книга привязки: Фолиот");
        this.lang("ru_ru").addItem(OccultismItems.BOOK_OF_BINDING_BOUND_FOLIOT, "Книга привязки: Фолиот (связанная)");
        this.lang("ru_ru").addItem(OccultismItems.BOOK_OF_BINDING_DJINNI, "Книга привязки: Джинн");
        this.lang("ru_ru").addItem(OccultismItems.BOOK_OF_BINDING_BOUND_DJINNI, "Книга привязки: Джинн (связанная)");
        this.lang("ru_ru").addItem(OccultismItems.BOOK_OF_BINDING_AFRIT, "Книга привязки: Африт");
        this.lang("ru_ru").addItem(OccultismItems.BOOK_OF_BINDING_BOUND_AFRIT, "Книга привязки: Африт (связанная)");
        this.lang("ru_ru").addItem(OccultismItems.BOOK_OF_BINDING_MARID, "Книга привязки: Марид");
        this.lang("ru_ru").addItem(OccultismItems.BOOK_OF_BINDING_BOUND_MARID, "Книга привязки: Марид (связанная)");
        this.lang("ru_ru").addItem(OccultismItems.BOOK_OF_CALLING_FOLIOT_LUMBERJACK, "Книга призыва: Фолиот-дровосек");
		this.lang("ru_ru").addItem(OccultismItems.BOOK_OF_CALLING_FOLIOT_FARMER, "Книга призыва: Фолиот-фермер");
        this.lang("ru_ru").addItem(OccultismItems.BOOK_OF_CALLING_FOLIOT_TRANSPORT_ITEMS, "Книга призыва: Фолиот-транспортировщик");
        this.lang("ru_ru").addItem(OccultismItems.BOOK_OF_CALLING_FOLIOT_CLEANER, "Книга призыва: Фолиот-уборщик");
        this.lang("ru_ru").addItem(OccultismItems.BOOK_OF_CALLING_DJINNI_MANAGE_MACHINE, "Книга призыва: Джинн-станочник");
		this.lang("ru_ru").addItem(OccultismItems.FLAME_AUTOMATION, "Полымя автоматизации");
        this.lang("ru_ru").addItem(OccultismItems.STORAGE_REMOTE, "Средство доступа к хранилищу");
        this.lang("ru_ru").addItem(OccultismItems.STORAGE_REMOTE_INERT, "Инертное средство доступа к хранилищу");
        this.lang("ru_ru").addItem(OccultismItems.DIMENSIONAL_MATRIX, "Кристальная пространственная матрица");
        this.lang("ru_ru").addItem(OccultismItems.DIVINATION_ROD, "Жезл прорицания");
        this.lang("ru_ru").addItem(OccultismItems.TRUE_SIGHT_STAFF, "Посох истинного зрения");
        this.lang("ru_ru").addItem(OccultismItems.DATURA_SEEDS, "Семена видения демона");
        this.lang("ru_ru").addAutoTooltip(OccultismItems.DATURA_SEEDS.get(), "Посадите, чтобы вырастить «Плод видения демона».\nУпотребление позволяет видеть за гробовой чертой... также вызвать плохое самочувствие. (Даёт «Третий глаз»).");
        this.lang("ru_ru").addItem(OccultismItems.DATURA, "Плод видения демона");
        this.lang("ru_ru").addAutoTooltip(OccultismItems.DATURA.get(), "Употребление позволяет видеть за гробовой чертой... может также вызвать плохое самочувствие. (Даёт «Третий глаз»).");
        this.lang("ru_ru").addItem(OccultismItems.DEMONS_DREAM_ESSENCE, "Эссенция видения демона");
        this.lang("ru_ru").addAutoTooltip(OccultismItems.DEMONS_DREAM_ESSENCE.get(), "Употребление позволяет видеть за гробовой чертой... и полный набор других эффектов. (Употребление даёт «Третий глаз»).");
        this.lang("ru_ru").addItem(OccultismItems.OTHERWORLD_ESSENCE, "Потусторонняя эссенция");
        this.lang("ru_ru").addAutoTooltip(OccultismItems.OTHERWORLD_ESSENCE.get(), "Очищенная эссенция видения демона больше не оказывает пагубные эффекты. (Употребление даёт «Третий глаз»).");
        this.lang("ru_ru").addItem(OccultismItems.BEAVER_NUGGET, "Лакомство «Beaver nuggets»");
        this.lang("ru_ru").addItem(OccultismItems.SPIRIT_ATTUNED_GEM, "Самоцвет, настроенный на духа");
        this.lang("ru_ru").add("item.occultism.otherworld_sapling", "Потусторонний саженец");
        this.lang("ru_ru").add("item.occultism.otherworld_sapling_natural", "Нестабильный потусторонний саженец");
        this.lang("ru_ru").addItem(OccultismItems.OTHERWORLD_ASHES, "Потусторонняя зола");
        this.lang("ru_ru").addItem(OccultismItems.BURNT_OTHERSTONE, "Гарь из потустороннего камня");
        this.lang("ru_ru").addItem(OccultismItems.BURNT_OTHERROCK, "Гарь из потусторонней породы");
        this.lang("ru_ru").addItem(OccultismItems.BUTCHER_KNIFE, "Нож мясника");
        this.lang("ru_ru").addItem(OccultismItems.TALLOW, "Жир");
        this.lang("ru_ru").addItem(OccultismItems.OTHERSTONE_FRAME, "Потусторонняя рама");
		this.lang("ru_ru").addItem(OccultismItems.OTHERROCK_FRAME, "Рама из потусторонней породы");
        this.lang("ru_ru").addItem(OccultismItems.OTHERSTONE_TABLET, "Сверхъестественная скрижаль");
        this.lang("ru_ru").addItem(OccultismItems.IRON_DUST, "Железная пыль");
        this.lang("ru_ru").addItem(OccultismItems.OBSIDIAN_DUST, "Обсидиановая пыль");
        this.lang("ru_ru").addItem(OccultismItems.CRUSHED_END_STONE, "Измельчённый эндерняк");
        this.lang("ru_ru").addItem(OccultismItems.GOLD_DUST, "Золотая пыль");
        this.lang("ru_ru").addItem(OccultismItems.COPPER_DUST, "Медная пыль");
        this.lang("ru_ru").addItem(OccultismItems.SILVER_DUST, "Серебряная пыль");
        this.lang("ru_ru").addItem(OccultismItems.IESNIUM_DUST, "Айзниевая пыль");
        this.lang("ru_ru").addItem(OccultismItems.RAW_SILVER, "Рудное серебро");
        this.lang("ru_ru").addItem(OccultismItems.RAW_IESNIUM, "Рудный айзний");
        this.lang("ru_ru").addItem(OccultismItems.SILVER_INGOT, "Серебряный слиток");
        this.lang("ru_ru").addItem(OccultismItems.IESNIUM_INGOT, "Айзниевый слиток");
        this.lang("ru_ru").addItem(OccultismItems.SILVER_NUGGET, "Кусочек серебра");
        this.lang("ru_ru").addItem(OccultismItems.IESNIUM_NUGGET, "Кусочек айзния");
        this.lang("ru_ru").addItem(OccultismItems.LENSES, "Линзы");
        this.lang("ru_ru").addItem(OccultismItems.INFUSED_LENSES, "Наполненные линзы");
        this.lang("ru_ru").addItem(OccultismItems.LENS_FRAME, "Оправа для очков");
        this.lang("ru_ru").addItem(OccultismItems.OTHERWORLD_GOGGLES, "Потусторонние очки");
        this.lang("ru_ru").addItem(OccultismItems.INFUSED_PICKAXE, "Наполненная кирка");
        this.lang("ru_ru").addItem(OccultismItems.SPIRIT_ATTUNED_PICKAXE_HEAD, "Головка кирки из самоцвета, настроенного на духа");
        this.lang("ru_ru").addItem(OccultismItems.IESNIUM_PICKAXE, "Айзниевая кирка");
		this.lang("ru_ru").add(OccultismItems.MAGIC_LAMP_EMPTY.get().getDescriptionId().replace("empty","filled"), "Волшебная лампа");
        this.lang("ru_ru").addItem(OccultismItems.MAGIC_LAMP_EMPTY, "Пустая волшебная лампа");
        this.lang("ru_ru").addItem(OccultismItems.MINER_FOLIOT_UNSPECIALIZED, "Фолиот-рудокоп");
        this.lang("ru_ru").addItem(OccultismItems.MINER_DJINNI_ORES, "Рудный Джинн-рудокоп");
        this.lang("ru_ru").addItem(OccultismItems.MINER_DEBUG_UNSPECIALIZED, "Отладочный рудокоп");
        this.lang("ru_ru").addItem(OccultismItems.MINER_AFRIT_DEEPS, "Африт-рудокоп для глубинносланцевой руды");
        this.lang("ru_ru").addItem(OccultismItems.MINER_MARID_MASTER, "Мастер Марид-рудокоп");
        this.lang("ru_ru").addItem(OccultismItems.MINER_ANCIENT_ELDRITCH, "Сверхъестественный древний рудокоп");
        this.lang("ru_ru").addItem(OccultismItems.MINING_DIMENSION_CORE_PIECE, "Часть ядра шахтёрского измерения");
		this.lang("ru_ru").addAutoTooltip(OccultismItems.MINING_DIMENSION_CORE_PIECE, "Очень долговечное топливо.");
		this.lang("ru_ru").addItem(OccultismItems.FRAGILE_SOUL_GEM_ITEM, "Хрупкий камень души");
        this.lang("ru_ru").add(OccultismItems.FRAGILE_SOUL_GEM_ITEM.get().getDescriptionId() + "_empty", "Пустой хрупкий камень души");
        this.lang("ru_ru").addItem(OccultismItems.SOUL_GEM_ITEM, "Камень души");
        this.lang("ru_ru").add(OccultismItems.SOUL_GEM_ITEM.get().getDescriptionId() + "_empty", "Пустой камень души");
        this.lang("ru_ru").addItem(OccultismItems.TRINITY_GEM_ITEM, "Камень Троицы");
        this.lang("ru_ru").add(OccultismItems.TRINITY_GEM_ITEM.get().getDescriptionId() + "_empty", "Пустой камень Троицы");
        this.lang("ru_ru").addItem(OccultismItems.SOUL_SHARD_ITEM, "Осколок души");
        this.lang("ru_ru").addItem(OccultismItems.SATCHEL, "Необычайно большая наплечная сумка");
		this.lang("ru_ru").addAutoTooltip(OccultismItems.SATCHEL, "Некоторые называют её рюкзаком.");
		this.lang("ru_ru").addItem(OccultismItems.ENDER_SATCHEL, "Эндер-наплечная сумка");
        this.lang("ru_ru").addItem(OccultismItems.FAMILIAR_RING, "Перстень для фамильяра");
		this.lang("ru_ru").addItem(OccultismItems.VITALITY_COMPASS, "Компас жизни");
        this.lang("ru_ru").addItem(OccultismItems.SPAWN_EGG_FOLIOT, "Яйцо призыва Фолиота");
        this.lang("ru_ru").addItem(OccultismItems.SPAWN_EGG_DJINNI, "Яйцо призыва Джинна");
        this.lang("ru_ru").addItem(OccultismItems.SPAWN_EGG_AFRIT, "Яйцо призыва Африта");
        this.lang("ru_ru").addItem(OccultismItems.SPAWN_EGG_AFRIT_UNBOUND, "Яйцо призыва незаключённого Африта");
        this.lang("ru_ru").addItem(OccultismItems.SPAWN_EGG_MARID, "Яйцо призыва Марида");
        this.lang("ru_ru").addItem(OccultismItems.SPAWN_EGG_MARID_UNBOUND, "Яйцо призыва незаключённого Марида");
		this.lang("ru_ru").addItem(OccultismItems.SPAWN_EGG_WONDERING_TRADER, "Яйцо призыва странствующего торговца");
        this.lang("ru_ru").addItem(OccultismItems.SPAWN_EGG_POSSESSED_ENDERMITE, "Яйцо призыва одержимого эндермита");
        this.lang("ru_ru").addItem(OccultismItems.SPAWN_EGG_POSSESSED_SKELETON, "Яйцо призыва одержимого скелета");
        this.lang("ru_ru").addItem(OccultismItems.SPAWN_EGG_POSSESSED_ENDERMAN, "Яйцо призыва одержимого эндермена");
        this.lang("ru_ru").addItem(OccultismItems.SPAWN_EGG_POSSESSED_GHAST, "Яйцо призыва одержимого гаста");
        this.lang("ru_ru").addItem(OccultismItems.SPAWN_EGG_POSSESSED_PHANTOM, "Яйцо призыва одержимого фантома");
        this.lang("ru_ru").addItem(OccultismItems.SPAWN_EGG_POSSESSED_WEAK_SHULKER, "Яйцо призыва одержимого слабого шалкера");
        this.lang("ru_ru").addItem(OccultismItems.SPAWN_EGG_POSSESSED_SHULKER, "Яйцо призыва одержимого шалкера");
        this.lang("ru_ru").addItem(OccultismItems.SPAWN_EGG_POSSESSED_ELDER_GUARDIAN, "Яйцо призыва одержимого древнего стража");
        this.lang("ru_ru").addItem(OccultismItems.SPAWN_EGG_POSSESSED_WARDEN, "Яйцо призыва одержимого хранителя");
        this.lang("ru_ru").addItem(OccultismItems.SPAWN_EGG_POSSESSED_HOGLIN, "Яйцо призыва одержимого хоглина");
        this.lang("ru_ru").addItem(OccultismItems.SPAWN_EGG_POSSESSED_WITCH, "Яйцо призыва одержимой ведьмы");
		this.lang("ru_ru").addItem(OccultismItems.SPAWN_EGG_POSSESSED_BLAZE, "Яйцо призыва одержимого всполоха");
        this.lang("ru_ru").addItem(OccultismItems.SPAWN_EGG_POSSESSED_ZOMBIE_PIGLIN, "Яйцо призыва одержимого зомбифицированного пиглина");
		this.lang("ru_ru").addItem(OccultismItems.SPAWN_EGG_POSSESSED_GUARDIAN, "Яйцо призыва одержимого стража");
        this.lang("ru_ru").addItem(OccultismItems.SPAWN_EGG_POSSESSED_BEE, "Яйцо призыва одержимой пчелы");
        this.lang("ru_ru").addItem(OccultismItems.SPAWN_EGG_GOAT_OF_MERCY, "Яйцо призыва козла милосердия");
        this.lang("ru_ru").addItem(OccultismItems.SPAWN_EGG_WILD_HUNT_SKELETON, "Яйцо призыва скелета дикой охоты");
        this.lang("ru_ru").addItem(OccultismItems.SPAWN_EGG_WILD_HUNT_WITHER_SKELETON, "Яйцо призыва визер-скелета дикой охоты");
        this.lang("ru_ru").addItem(OccultismItems.SPAWN_EGG_OTHERWORLD_BIRD, "Яйцо призыва дрикрыла");
        this.lang("ru_ru").addItem(OccultismItems.SPAWN_EGG_GREEDY_FAMILIAR, "Яйцо призыва алчного фамильяра");
        this.lang("ru_ru").addItem(OccultismItems.SPAWN_EGG_BAT_FAMILIAR, "Яйцо призыва фамильяра-летучая мышь");
        this.lang("ru_ru").addItem(OccultismItems.SPAWN_EGG_DEER_FAMILIAR, "Яйцо призыва фамильяра-оленя");
        this.lang("ru_ru").addItem(OccultismItems.SPAWN_EGG_CTHULHU_FAMILIAR, "Яйцо призыва фамильяра-Ктулху");
        this.lang("ru_ru").addItem(OccultismItems.SPAWN_EGG_DEVIL_FAMILIAR, "Яйцо призыва фамильяра-дьявола");
        this.lang("ru_ru").addItem(OccultismItems.SPAWN_EGG_DRAGON_FAMILIAR, "Яйцо призыва фамильяра-дракона");
        this.lang("ru_ru").addItem(OccultismItems.SPAWN_EGG_BLACKSMITH_FAMILIAR, "Яйцо призыва фамильяра-кузнеца");
        this.lang("ru_ru").addItem(OccultismItems.SPAWN_EGG_GUARDIAN_FAMILIAR, "Яйцо призыва фамильяра-стража");
        this.lang("ru_ru").addItem(OccultismItems.SPAWN_EGG_HEADLESS_FAMILIAR, "Яйцо призыва фамильяра-безголового человека на крысе");
        this.lang("ru_ru").addItem(OccultismItems.SPAWN_EGG_CHIMERA_FAMILIAR, "Яйцо призыва фамильяра-химеры");
        this.lang("ru_ru").addItem(OccultismItems.SPAWN_EGG_GOAT_FAMILIAR, "Яйцо призыва фамильяра-козы");
        this.lang("ru_ru").addItem(OccultismItems.SPAWN_EGG_SHUB_NIGGURATH_FAMILIAR, "Яйцо призыва фамильяра-Шаб-Ниггурата");
        this.lang("ru_ru").addItem(OccultismItems.SPAWN_EGG_BEHOLDER_FAMILIAR, "Яйцо призыва фамильяра-созерцателя");
        this.lang("ru_ru").addItem(OccultismItems.SPAWN_EGG_FAIRY_FAMILIAR, "Яйцо призыва фамильяра-феи");
        this.lang("ru_ru").addItem(OccultismItems.SPAWN_EGG_MUMMY_FAMILIAR, "Яйцо призыва фамильяра-мумии");
        this.lang("ru_ru").addItem(OccultismItems.SPAWN_EGG_BEAVER_FAMILIAR, "Яйцо призыва фамильяра-бобра");
        this.lang("ru_ru").addItem(OccultismItems.SPAWN_EGG_DEMONIC_WIFE, "Яйцо призыва демонической жены");
        this.lang("ru_ru").addItem(OccultismItems.SPAWN_EGG_DEMONIC_HUSBAND, "Яйцо призыва демонического мужа");
        this.lang("ru_ru").addItem(OccultismItems.SPAWN_EGG_IESNIUM_GOLEM, "Яйцо призыва айзниевого голема");
        this.lang("ru_ru").addItem(OccultismItems.SPAWN_EGG_WILD_HORDE_HUSK, "Яйцо призыва орды диких кадавров");
        this.lang("ru_ru").addItem(OccultismItems.SPAWN_EGG_WILD_HORDE_DROWNED, "Яйцо призыва орды диких утопленников");
        this.lang("ru_ru").addItem(OccultismItems.SPAWN_EGG_WILD_HORDE_CREEPER, "Яйцо призыва орды диких криперов");
        this.lang("ru_ru").addItem(OccultismItems.SPAWN_EGG_WILD_HORDE_SILVERFISH, "Яйцо призыва орды диких чешуйниц");
        this.lang("ru_ru").addItem(OccultismItems.SPAWN_EGG_WILD_WEAK_BREEZE, "Яйцо призыва дикого слабого вихря");
        this.lang("ru_ru").addItem(OccultismItems.SPAWN_EGG_WILD_BREEZE, "Яйцо призыва дикого вихря");
        this.lang("ru_ru").addItem(OccultismItems.SPAWN_EGG_WILD_STRONG_BREEZE, "Яйцо призыва дикого сильного вихря");
        this.lang("ru_ru").addItem(OccultismItems.SPAWN_EGG_WILD_EVOKER, "Яйцо призыва дикого заклинателя");
        //Pentacle Rework Update
        this.lang("ru_ru").addItem(OccultismItems.AMETHYST_DUST, "Аметистовая пыль");
        this.lang("ru_ru").addItem(OccultismItems.CRUELTY_ESSENCE, "Сущность бессердечия");
        this.lang("ru_ru").addItem(OccultismItems.CRUSHED_BLACKSTONE, "Измельчённый чернит");
        this.lang("ru_ru").addItem(OccultismItems.CRUSHED_BLUE_ICE, "Измельчённый синий лёд");
        this.lang("ru_ru").addItem(OccultismItems.CRUSHED_CALCITE, "Измельчённый кальцит");
        this.lang("ru_ru").addItem(OccultismItems.CRUSHED_ICE, "Измельчённый лёд");
        this.lang("ru_ru").addItem(OccultismItems.CRUSHED_PACKED_ICE, "Измельчённый плотный лёд");
        this.lang("ru_ru").addItem(OccultismItems.CURSED_HONEY, "Проклятый мёд");
		this.lang("ru_ru").addAutoTooltip(OccultismItems.CURSED_HONEY, "Употребление даёт «Регенерация».");
		this.lang("ru_ru").addItem(OccultismItems.SWEET_HONEY_HEART,"§5Сладкое медовое сердце");
        this.lang("ru_ru").addAutoTooltip(OccultismItems.SWEET_HONEY_HEART, ChatFormatting.WHITE + "Сделано с любовью, сахаром и злонамеренностью.\n" + ChatFormatting.GRAY + "Даёт большое «Поглощение» при употреблении.\n" + ChatFormatting.DARK_PURPLE + "§5Дайте партнёру «Проклятый мёд» для получения сердца.");
        this.lang("ru_ru").addItem(OccultismItems.DEMONIC_MEAT, "Демоническое мясо");
		this.lang("ru_ru").addAutoTooltip(OccultismItems.DEMONIC_MEAT, "Употребление даёт «Огнестойкость».");
        this.lang("ru_ru").addItem(OccultismItems.DRAGONYST_DUST, "Драконистовая пыль");
		this.lang("ru_ru").addItem(OccultismItems.ECHO_DUST, "Пыль эхо");
        this.lang("ru_ru").addItem(OccultismItems.EMERALD_DUST, "Изумрудная пыль");
        this.lang("ru_ru").addItem(OccultismItems.GRAY_PASTE, "Серая паста");
		this.lang("ru_ru").addAutoTooltip(OccultismItems.GRAY_PASTE, "Вступает в реакцию с некоторой пылью, возвращая пасту в её прежнюю форму.");
        this.lang("ru_ru").addItem(OccultismItems.LAPIS_DUST, "Лазуритовая пыль");
        this.lang("ru_ru").addItem(OccultismItems.MARID_ESSENCE, "Сущность Марида");
        this.lang("ru_ru").addItem(OccultismItems.NATURE_PASTE, "Природная паста");
		this.lang("ru_ru").addAutoTooltip(OccultismItems.NATURE_PASTE, "Плодотворная и многоразовая костная мука: мгновенно выращивает и затрагивает больше растений.");
        this.lang("ru_ru").addItem(OccultismItems.NETHERITE_DUST, "Незеритовая пыль");
        this.lang("ru_ru").addItem(OccultismItems.NETHERITE_SCRAP_DUST, "Пыль из незеритового лома");
        this.lang("ru_ru").addItem(OccultismItems.RESEARCH_FRAGMENT_DUST, "Пыль фрагмента исследования");
        this.lang("ru_ru").addItem(OccultismItems.WITHERITE_DUST, "Визеритовая пыль");
    }

    private void addBlocks() {
        //"block\.occultism\.(.*?)": "(.*)",
        //this.addBlock\(OccultismItems.\U\1\E, "\2"\);
        this.lang("ru_ru").addBlock(OccultismBlocks.OTHERGLASS, "Потустороннее стекло");
        this.lang("ru_ru").addBlock(OccultismBlocks.OTHERSTONE, "Потусторонний камень");
        this.lang("ru_ru").addBlock(OccultismBlocks.OTHERSTONE_STAIRS, "Потусторонние ступеньки");
        this.lang("ru_ru").addBlock(OccultismBlocks.OTHERSTONE_SLAB, "Потусторонняя плита");
        this.lang("ru_ru").addBlock(OccultismBlocks.OTHERSTONE_PRESSURE_PLATE, "Потусторонняя нажимная плита");
        this.lang("ru_ru").addBlock(OccultismBlocks.OTHERSTONE_BUTTON, "Потусторонняя кнопка");
        this.lang("ru_ru").addBlock(OccultismBlocks.OTHERSTONE_WALL, "Потусторонняя ограда");
        this.lang("ru_ru").addBlock(OccultismBlocks.OTHERCOBBLESTONE, "Потусторонний булыжник");
        this.lang("ru_ru").addBlock(OccultismBlocks.OTHERCOBBLESTONE_STAIRS, "Булыжные потусторонние ступеньки");
        this.lang("ru_ru").addBlock(OccultismBlocks.OTHERCOBBLESTONE_SLAB, "Булыжная потусторонняя плита");
        this.lang("ru_ru").addBlock(OccultismBlocks.OTHERCOBBLESTONE_WALL, "Булыжная потусторонняя ограда");
        this.lang("ru_ru").addBlock(OccultismBlocks.POLISHED_OTHERSTONE, "Полированный потусторонний камень");
        this.lang("ru_ru").addBlock(OccultismBlocks.POLISHED_OTHERSTONE_STAIRS, "Потусторонние полированные ступеньки");
        this.lang("ru_ru").addBlock(OccultismBlocks.POLISHED_OTHERSTONE_SLAB, "Потусторонняя полированная плита");
        this.lang("ru_ru").addBlock(OccultismBlocks.POLISHED_OTHERSTONE_WALL, "Потусторонняя полированная ограда");
        this.lang("ru_ru").addBlock(OccultismBlocks.OTHERSTONE_BRICKS, "Потусторонние кирпичи");
        this.lang("ru_ru").addBlock(OccultismBlocks.OTHERSTONE_BRICKS_STAIRS, "Потусторонние кирпичные ступеньки");
        this.lang("ru_ru").addBlock(OccultismBlocks.OTHERSTONE_BRICKS_SLAB, "Потусторонняя кирпичная плита");
        this.lang("ru_ru").addBlock(OccultismBlocks.OTHERSTONE_BRICKS_WALL, "Потусторонняя кирпичная ограда");
        this.lang("ru_ru").addBlock(OccultismBlocks.CHISELED_OTHERSTONE_BRICKS, "Резные кирпичи из потустороннего камня");
        this.lang("ru_ru").addBlock(OccultismBlocks.CRACKED_OTHERSTONE_BRICKS, "Потрескавшиеся потусторонние кирпичи");
        this.lang("ru_ru").addBlock(OccultismBlocks.OTHERSTONE_PEDESTAL, "Потусторонний пьедестал");
        this.lang("ru_ru").addBlock(OccultismBlocks.OTHERROCK_PEDESTAL, "Пьедестал из потусторонней породы");
        this.lang("ru_ru").addBlock(OccultismBlocks.OTHERROCK, "Потусторонняя порода");
        this.lang("ru_ru").addBlock(OccultismBlocks.OTHERROCK_STAIRS, "Ступеньки из потусторонней породы");
        this.lang("ru_ru").addBlock(OccultismBlocks.OTHERROCK_SLAB, "Плита из потусторонней породы");
        this.lang("ru_ru").addBlock(OccultismBlocks.OTHERROCK_PRESSURE_PLATE, "Нажимная пластина из потусторонней породы");
        this.lang("ru_ru").addBlock(OccultismBlocks.OTHERROCK_BUTTON, "Кнопка из потусторонней породы");
        this.lang("ru_ru").addBlock(OccultismBlocks.OTHERROCK_WALL, "Ограда из потусторонней породы");
        this.lang("ru_ru").addBlock(OccultismBlocks.OTHERCOBBLEROCK, "Потусторонняя булыжная порода");
        this.lang("ru_ru").addBlock(OccultismBlocks.OTHERCOBBLEROCK_STAIRS, "Ступеньки из потусторонней булыжной породы");
        this.lang("ru_ru").addBlock(OccultismBlocks.OTHERCOBBLEROCK_SLAB, "Плита из потусторонней булыжной породы");
        this.lang("ru_ru").addBlock(OccultismBlocks.OTHERCOBBLEROCK_WALL, "Ограда из потусторонней булыжной породы");
        this.lang("ru_ru").addBlock(OccultismBlocks.POLISHED_OTHERROCK, "Резная потусторонняя порода");
        this.lang("ru_ru").addBlock(OccultismBlocks.POLISHED_OTHERROCK_STAIRS, "Резные ступеньки из потусторонней породы");
        this.lang("ru_ru").addBlock(OccultismBlocks.POLISHED_OTHERROCK_SLAB, "Резные кирпичи из потусторонней породы");
        this.lang("ru_ru").addBlock(OccultismBlocks.POLISHED_OTHERROCK_WALL, "Резная ограда из потусторонней породы");
        this.lang("ru_ru").addBlock(OccultismBlocks.OTHERROCK_BRICKS, "Кирпичи из потусторонней породы");
        this.lang("ru_ru").addBlock(OccultismBlocks.OTHERROCK_BRICKS_STAIRS, "Кирпичные ступеньки из потусторонней породы");
        this.lang("ru_ru").addBlock(OccultismBlocks.OTHERROCK_BRICKS_SLAB, "Кирпичная плита из потусторонней породы");
        this.lang("ru_ru").addBlock(OccultismBlocks.OTHERROCK_BRICKS_WALL, "Кирпичная ограда из потусторонней породы");
        this.lang("ru_ru").addBlock(OccultismBlocks.CHISELED_OTHERROCK_BRICKS, "Резные кирпичи из потусторонней породы");
        this.lang("ru_ru").addBlock(OccultismBlocks.CRACKED_OTHERROCK_BRICKS, "Потрескавшиеся кирпичи из потусторонней породы");
        this.lang("ru_ru").addBlock(OccultismBlocks.SACRIFICIAL_BOWL, "Жертвенная чаша");
        this.lang("ru_ru").addBlock(OccultismBlocks.COPPER_SACRIFICIAL_BOWL, "Медная жертвенная чаша");
        this.lang("ru_ru").addBlock(OccultismBlocks.SILVER_SACRIFICIAL_BOWL, "Серебряная жертвенная чаша");
		this.lang("ru_ru").addBlock(OccultismBlocks.GOLDEN_SACRIFICIAL_BOWL, "Золотая ритуальная чаша");
        this.lang("ru_ru").addAutoTooltip(OccultismBlocks.GOLDEN_SACRIFICIAL_BOWL.asItem(), ChatFormatting.RED + "Является центральной чашей. Используйте только одну в пентакле.");
        this.lang("ru_ru").addBlock(OccultismBlocks.IESNIUM_SACRIFICIAL_BOWL, "Айзниевая ритуальная чаша");
        this.lang("ru_ru").addAutoTooltip(OccultismBlocks.IESNIUM_SACRIFICIAL_BOWL.asItem(), ChatFormatting.RED + "Является центральной чашей. Используйте только одну в пентакле.");
        this.lang("ru_ru").addBlock(OccultismBlocks.DARK_SACRIFICIAL_BOWL, "Тёмная жертвенная чаша");
        this.lang("ru_ru").addBlock(OccultismBlocks.DARK_COPPER_SACRIFICIAL_BOWL, "Тёмная медная жертвенная чаша");
        this.lang("ru_ru").addBlock(OccultismBlocks.DARK_SILVER_SACRIFICIAL_BOWL, "Тёмная серебряная жертвенная чаша");
        this.lang("ru_ru").addBlock(OccultismBlocks.DARK_GOLDEN_SACRIFICIAL_BOWL, "Тёмная золотая ритуальная чаша");
        this.lang("ru_ru").addAutoTooltip(OccultismBlocks.DARK_GOLDEN_SACRIFICIAL_BOWL.asItem(), ChatFormatting.RED + "Является центральной чашей. Используйте только одну в пентакле.");
        this.lang("ru_ru").addBlock(OccultismBlocks.DARK_IESNIUM_SACRIFICIAL_BOWL, "Тёмная ритуальная чаша из айзния");
        this.lang("ru_ru").addAutoTooltip(OccultismBlocks.DARK_IESNIUM_SACRIFICIAL_BOWL.asItem(), ChatFormatting.RED + "Является центральной чашей. Используйте только одну в пентакле.");
        this.lang("ru_ru").addBlock(OccultismBlocks.CHALK_GLYPH_WHITE, "Белый глиф");
        this.lang("ru_ru").addBlock(OccultismBlocks.CHALK_GLYPH_YELLOW, "Жёлтый глиф");
        this.lang("ru_ru").addBlock(OccultismBlocks.CHALK_GLYPH_PURPLE, "Фиолетовый глиф");
        this.lang("ru_ru").addBlock(OccultismBlocks.CHALK_GLYPH_RED, "Красный глиф");
        this.lang("ru_ru").addBlock(OccultismBlocks.CHALK_GLYPH_LIGHT_GRAY, "Светло-серый глиф");
        this.lang("ru_ru").addBlock(OccultismBlocks.CHALK_GLYPH_GRAY, "Серый глиф");
        this.lang("ru_ru").addBlock(OccultismBlocks.CHALK_GLYPH_BLACK, "Чёрный глиф");
        this.lang("ru_ru").addBlock(OccultismBlocks.CHALK_GLYPH_BROWN, "Коричневый глиф");
        this.lang("ru_ru").addBlock(OccultismBlocks.CHALK_GLYPH_ORANGE, "Оранжевый глиф");
        this.lang("ru_ru").addBlock(OccultismBlocks.CHALK_GLYPH_LIME, "Лаймовый глиф");
        this.lang("ru_ru").addBlock(OccultismBlocks.CHALK_GLYPH_GREEN, "Зелёный глиф");
        this.lang("ru_ru").addBlock(OccultismBlocks.CHALK_GLYPH_CYAN, "Бирюзовый глиф");
        this.lang("ru_ru").addBlock(OccultismBlocks.CHALK_GLYPH_LIGHT_BLUE, "Голубой глиф");
        this.lang("ru_ru").addBlock(OccultismBlocks.CHALK_GLYPH_BLUE, "Синий глиф");
        this.lang("ru_ru").addBlock(OccultismBlocks.CHALK_GLYPH_MAGENTA, "Пурпурный глиф");
        this.lang("ru_ru").addBlock(OccultismBlocks.CHALK_GLYPH_PINK, "Розовый глиф");
        this.lang("ru_ru").addBlock(OccultismBlocks.CHALK_GLYPH_RAINBOW, "Радужный глиф");
        this.lang("ru_ru").addBlock(OccultismBlocks.CHALK_GLYPH_VOID, "Глиф пустоты");
        this.lang("ru_ru").addBlock(OccultismBlocks.STORAGE_CONTROLLER, "Регулятор пространственного хранилища");
        this.lang("ru_ru").addBlock(OccultismBlocks.STORAGE_CONTROLLER_STABILIZED, "Стабильный регулятор для пространственного хранилища");
        this.lang("ru_ru").addBlock(OccultismBlocks.STORAGE_CONTROLLER_BASE, "Основание регулятора хранилища");
        this.lang("ru_ru").addBlock(OccultismBlocks.STORAGE_STABILIZER_TIER0, "Основание стабилизатора пространственного хранилища");
        this.lang("ru_ru").addBlock(OccultismBlocks.STORAGE_STABILIZER_TIER1, "Стабилизатор пространственного хранилища [1 уровень]");
        this.lang("ru_ru").addBlock(OccultismBlocks.STORAGE_STABILIZER_TIER2, "Стабилизатор пространственного хранилища [2 уровень]");
        this.lang("ru_ru").addBlock(OccultismBlocks.STORAGE_STABILIZER_TIER3, "Стабилизатор пространственного хранилища [3 уровень]");
        this.lang("ru_ru").addBlock(OccultismBlocks.STORAGE_STABILIZER_TIER4, "Стабилизатор пространственного хранилища [4 уровень]");
		this.lang("ru_ru").addBlock(OccultismBlocks.STORAGE_STABILIZER_TIER5, "Стабилизатор пространственного хранилища [5 уровень]");
        this.lang("ru_ru").addBlock(OccultismBlocks.STABLE_WORMHOLE, "Стабильная червоточина");
		this.lang("ru_ru").addBlock(OccultismBlocks.ENTITY_WORMHOLE, "Червоточина существ");
		this.lang("ru_ru").addBlock(OccultismBlocks.STORAGE_CONTROLLER_DARK, "Тёмный регулятор для стабилизатора пространственного хранилища");
        this.lang("ru_ru").addBlock(OccultismBlocks.STORAGE_CONTROLLER_STABILIZED_DARK, "Тёмный стабильный регулятор для пространственного хранилища");
        this.lang("ru_ru").addBlock(OccultismBlocks.STORAGE_CONTROLLER_BASE_DARK, "Тёмное основание регулятора хранилища");
        this.lang("ru_ru").addBlock(OccultismBlocks.STORAGE_STABILIZER_TIER0_DARK, "Тёмное основание стабилизатора пространственного хранилища");
        this.lang("ru_ru").addBlock(OccultismBlocks.STORAGE_STABILIZER_TIER1_DARK, "Тёмный стабилизатор пространственного хранилища [1 уровень]");
        this.lang("ru_ru").addBlock(OccultismBlocks.STORAGE_STABILIZER_TIER2_DARK, "Тёмный стабилизатор пространственного хранилища [2 уровень]");
        this.lang("ru_ru").addBlock(OccultismBlocks.STORAGE_STABILIZER_TIER3_DARK, "Тёмный стабилизатор пространственного хранилища [3 уровень]");
        this.lang("ru_ru").addBlock(OccultismBlocks.STORAGE_STABILIZER_TIER4_DARK, "Тёмный стабилизатор пространственного хранилища [4 уровень]");
        this.lang("ru_ru").addBlock(OccultismBlocks.STORAGE_STABILIZER_TIER5_DARK, "Тёмный стабилизатор пространственного хранилища [5 уровень]");
        this.lang("ru_ru").addBlock(OccultismBlocks.STABLE_WORMHOLE_DARK, "Тёмная стабильная червоточина");
        this.lang("ru_ru").addBlock(OccultismBlocks.ENTITY_WORMHOLE_DARK, "Тёмная червоточина сущностей");
        this.lang("ru_ru").addBlock(OccultismBlocks.DATURA, "Видение демона");
        this.lang("ru_ru").addBlock(OccultismBlocks.OTHERFLOWER, "Потусторонний цветок");
        this.lang("ru_ru").addBlock(OccultismBlocks.OTHERWORLD_SAPLING, "Потусторонний саженец");
        this.lang("ru_ru").addBlock(OccultismBlocks.OTHERWORLD_LEAVES, "Потусторонние листья");
        this.lang("ru_ru").addBlock(OccultismBlocks.OTHERWORLD_LOG, "Потустороннее бревно");
        this.lang("ru_ru").addBlock(OccultismBlocks.OTHERWORLD_WOOD, "Потусторонняя древесина");
        this.lang("ru_ru").addBlock(OccultismBlocks.STRIPPED_OTHERWORLD_LOG, "Обтёсанное потустороннее бревно");
        this.lang("ru_ru").addBlock(OccultismBlocks.STRIPPED_OTHERWORLD_WOOD, "Обтёсанная потусторонняя древесина");
        this.lang("ru_ru").addBlock(OccultismBlocks.OTHERPLANKS, "Потусторонние доски");
        this.lang("ru_ru").addBlock(OccultismBlocks.OTHERPLANKS_STAIRS, "Ступеньки из потусторонних досок");
        this.lang("ru_ru").addBlock(OccultismBlocks.OTHERPLANKS_SLAB, "Плита из потусторонних досок");
        this.lang("ru_ru").addBlock(OccultismBlocks.OTHERPLANKS_FENCE, "Забор из потусторонних досок");
        this.lang("ru_ru").addBlock(OccultismBlocks.OTHERPLANKS_FENCE_GATE, "Калитка из потусторонних досок");
        this.lang("ru_ru").addBlock(OccultismBlocks.OTHERPLANKS_DOOR, "Дверь из потусторонних досок");
        this.lang("ru_ru").addBlock(OccultismBlocks.OTHERPLANKS_TRAPDOOR, "Люк из потусторонних досок");
        this.lang("ru_ru").addBlock(OccultismBlocks.OTHERPLANKS_PRESSURE_PLATE, "Нажимная плита из потусторонних досок");
        this.lang("ru_ru").addBlock(OccultismBlocks.OTHERPLANKS_BUTTON, "Кнопка из потусторонних досок");
        this.lang("ru_ru").addBlock(OccultismBlocks.OTHERPLANKS_SIGN, "Табличка из потусторонних досок");
        this.lang("ru_ru").addBlock(OccultismBlocks.OTHERPLANKS_HANGING_SIGN, "Подвесная табличка из потусторонних досок");
        this.lang("ru_ru").addBlock(OccultismBlocks.TALLOW_BLOCK, "Блок жира");
        this.lang("ru_ru").addBlock(OccultismBlocks.SPIRIT_FIRE, "Огонь духов");
        this.lang("ru_ru").addBlock(OccultismBlocks.SPIRIT_ATTUNED_CRYSTAL, "Кристалл, настроенный на духа");
        this.lang("ru_ru").addBlock(OccultismBlocks.LARGE_CANDLE, "Большая свеча");
        this.lang("ru_ru").addBlock(OccultismBlocks.LARGE_CANDLE_WHITE, "Большая белая свеча");
        this.lang("ru_ru").addBlock(OccultismBlocks.LARGE_CANDLE_LIGHT_GRAY, "Большая светло-серая свеча");
        this.lang("ru_ru").addBlock(OccultismBlocks.LARGE_CANDLE_GRAY, "Большая серая свеча");
        this.lang("ru_ru").addBlock(OccultismBlocks.LARGE_CANDLE_BLACK, "Большая чёрная свеча");
        this.lang("ru_ru").addBlock(OccultismBlocks.LARGE_CANDLE_BROWN, "Большая коричневая свеча");
        this.lang("ru_ru").addBlock(OccultismBlocks.LARGE_CANDLE_RED, "Большая красная свеча");
        this.lang("ru_ru").addBlock(OccultismBlocks.LARGE_CANDLE_ORANGE, "Большая оранжевая свеча");
        this.lang("ru_ru").addBlock(OccultismBlocks.LARGE_CANDLE_YELLOW, "Большая жёлтая свеча");
        this.lang("ru_ru").addBlock(OccultismBlocks.LARGE_CANDLE_LIME, "Большая лаймовая свеча");
        this.lang("ru_ru").addBlock(OccultismBlocks.LARGE_CANDLE_GREEN, "Большая зелёная свеча");
        this.lang("ru_ru").addBlock(OccultismBlocks.LARGE_CANDLE_CYAN, "Большая бирюзовая свеча");
        this.lang("ru_ru").addBlock(OccultismBlocks.LARGE_CANDLE_BLUE, "Большая синяя свеча");
        this.lang("ru_ru").addBlock(OccultismBlocks.LARGE_CANDLE_LIGHT_BLUE, "Большая голубая свеча");
        this.lang("ru_ru").addBlock(OccultismBlocks.LARGE_CANDLE_PINK, "Большая розовая свеча");
        this.lang("ru_ru").addBlock(OccultismBlocks.LARGE_CANDLE_MAGENTA, "Большая пурпурная свеча");
        this.lang("ru_ru").addBlock(OccultismBlocks.LARGE_CANDLE_PURPLE, "Большая фиолетовая свеча");
        this.lang("ru_ru").addBlock(OccultismBlocks.SILVER_ORE, "Серебряная руда");
        this.lang("ru_ru").addBlock(OccultismBlocks.SILVER_ORE_DEEPSLATE, "Серебреносная глубинносланцевая руда");
        this.lang("ru_ru").addBlock(OccultismBlocks.IESNIUM_ANVIL, "Айзниевая наковальня");
        this.lang("ru_ru").addBlock(OccultismBlocks.IESNIUM_ORE, "Руда айзния");
        this.lang("ru_ru").addBlock(OccultismBlocks.SILVER_BLOCK, "Серебряный блок");
        this.lang("ru_ru").addBlock(OccultismBlocks.IESNIUM_BLOCK, "Блок айзния");
        this.lang("ru_ru").addBlock(OccultismBlocks.RAW_SILVER_BLOCK, "Блок рудного серебра");
        this.lang("ru_ru").addBlock(OccultismBlocks.RAW_IESNIUM_BLOCK, "Блок рудного айзния");
        this.lang("ru_ru").addBlock(OccultismBlocks.DIMENSIONAL_MINESHAFT, "Пространственная шахта");
        this.lang("ru_ru").addBlock(OccultismBlocks.SKELETON_SKULL_DUMMY, "Череп скелета");
        this.lang("ru_ru").addBlock(OccultismBlocks.WITHER_SKELETON_SKULL_DUMMY, "Череп визер-скелета");
        this.lang("ru_ru").addBlock(OccultismBlocks.LIGHTED_AIR, "Подсвеченный воздух");
        this.lang("ru_ru").addBlock(OccultismBlocks.SPIRIT_LANTERN, "Фонарь духов");
        this.lang("ru_ru").addBlock(OccultismBlocks.SPIRIT_CAMPFIRE, "Костёр духов");
        this.lang("ru_ru").addBlock(OccultismBlocks.SPIRIT_TORCH, "Факел духов"); //настенный факел духов автоматически использует такой же перевод.
        this.lang("ru_ru").addBlock(OccultismBlocks.ELDRITCH_CHALICE, "Сверхъестественный потир");
		this.addAutoTooltip(OccultismBlocks.ELDRITCH_CHALICE.asItem(), ChatFormatting.RED + "Является центральной чашей. Используйте только одну в пентакле.");
		this.lang("ru_ru").addBlock(OccultismBlocks.CELESTIAL_CHALICE, "Небесный потир");
		this.lang("ru_ru").addAutoTooltip(OccultismBlocks.CELESTIAL_CHALICE.asItem(), ChatFormatting.RED + "Является центральной чашей. Используйте только одну в пентакле.");
    }

    private void addEntities() {
        //"entity\.occultism\.(.*?)": "(.*)",
        //this.addEntityType\(OccultismEntities.\U\1\E, "\2"\);

        this.lang("ru_ru").addEntityType(OccultismEntities.FOLIOT, "Фолиот");
        this.lang("ru_ru").addEntityType(OccultismEntities.DJINNI, "Джинн");
        this.lang("ru_ru").addEntityType(OccultismEntities.AFRIT, "Африт");
        this.lang("ru_ru").addEntityType(OccultismEntities.AFRIT_WILD, "Незаключённый Африт");
        this.lang("ru_ru").addEntityType(OccultismEntities.MARID, "Марид");
        this.lang("ru_ru").addEntityType(OccultismEntities.MARID_UNBOUND, "Незаключённый Марид");
		this.lang("ru_ru").addEntityType(OccultismEntities.WONDERING_TRADER, "Странствующий торговец");
        this.lang("ru_ru").addEntityType(OccultismEntities.POSSESSED_ENDERMITE, "Одержимый эндермит");
        this.lang("ru_ru").addEntityType(OccultismEntities.POSSESSED_SKELETON, "Одержимый скелет");
        this.lang("ru_ru").addEntityType(OccultismEntities.POSSESSED_ENDERMAN, "Одержимый эндермен");
        this.lang("ru_ru").addEntityType(OccultismEntities.POSSESSED_GHAST, "Одержимый гаст");
        this.lang("ru_ru").addEntityType(OccultismEntities.POSSESSED_PHANTOM, "Одержимый фантом");
        this.lang("ru_ru").addEntityType(OccultismEntities.POSSESSED_WEAK_SHULKER, "Одержимый слабый шалкер");
        this.lang("ru_ru").addEntityType(OccultismEntities.POSSESSED_SHULKER, "Одержимый шалкер");
        this.lang("ru_ru").addEntityType(OccultismEntities.POSSESSED_ELDER_GUARDIAN, "Одержимый древний страж");
        this.lang("ru_ru").addEntityType(OccultismEntities.POSSESSED_WARDEN, "Одержимый хранитель");
        this.lang("ru_ru").addEntityType(OccultismEntities.POSSESSED_HOGLIN, "Одержимый хоглин");
        this.lang("ru_ru").addEntityType(OccultismEntities.POSSESSED_WITCH, "Одержимая ведьма");
		this.lang("ru_ru").addEntityType(OccultismEntities.POSSESSED_BLAZE, "Одержимый всполох");
        this.lang("ru_ru").addEntityType(OccultismEntities.POSSESSED_ZOMBIE_PIGLIN, "Одержимый зомбифицированный пиглин");
		this.lang("ru_ru").addEntityType(OccultismEntities.POSSESSED_GUARDIAN, "Одержимый страж");
        this.lang("ru_ru").addEntityType(OccultismEntities.POSSESSED_BEE, "Одержимая пчела");
        this.lang("ru_ru").addEntityType(OccultismEntities.GOAT_OF_MERCY, "Козёл милосердия");
        this.lang("ru_ru").addEntityType(OccultismEntities.WILD_HUNT_SKELETON, "Скелет дикой охоты");
        this.lang("ru_ru").addEntityType(OccultismEntities.WILD_HUNT_WITHER_SKELETON, "Визер-скелет дикой охоты");
        this.lang("ru_ru").addEntityType(OccultismEntities.OTHERWORLD_BIRD, "Дрикрыл");
        this.lang("ru_ru").addEntityType(OccultismEntities.GREEDY_FAMILIAR, "Алчный фамильяр");
        this.lang("ru_ru").addEntityType(OccultismEntities.BAT_FAMILIAR, "Фамильяр-летучая мышь");
        this.lang("ru_ru").addEntityType(OccultismEntities.DEER_FAMILIAR, "Фамильяр-олень");
        this.lang("ru_ru").addEntityType(OccultismEntities.CTHULHU_FAMILIAR, "Фамильяр-Ктулху");
        this.lang("ru_ru").addEntityType(OccultismEntities.DEVIL_FAMILIAR, "Фамильяр-дьявол");
        this.lang("ru_ru").addEntityType(OccultismEntities.DRAGON_FAMILIAR, "Фамильяр-дракон");
        this.lang("ru_ru").addEntityType(OccultismEntities.BLACKSMITH_FAMILIAR, "Фамильяр-кузнец");
        this.lang("ru_ru").addEntityType(OccultismEntities.GUARDIAN_FAMILIAR, "Фамильяр-страж");
        this.lang("ru_ru").addEntityType(OccultismEntities.HEADLESS_FAMILIAR, "Фамильяр-безголовый человек");
        this.lang("ru_ru").addEntityType(OccultismEntities.CHIMERA_FAMILIAR, "Фамильяр-химера");
        this.lang("ru_ru").addEntityType(OccultismEntities.GOAT_FAMILIAR, "Фамильяр-коза");
        this.lang("ru_ru").addEntityType(OccultismEntities.SHUB_NIGGURATH_FAMILIAR, "Фамильяр Шаб-Ниггурат");
        this.lang("ru_ru").addEntityType(OccultismEntities.BEHOLDER_FAMILIAR, "Фамильяр созерцатель");
        this.lang("ru_ru").addEntityType(OccultismEntities.FAIRY_FAMILIAR, "Фамильяр фея");
        this.lang("ru_ru").addEntityType(OccultismEntities.MUMMY_FAMILIAR, "Фамильяр мумия");
        this.lang("ru_ru").addEntityType(OccultismEntities.BEAVER_FAMILIAR, "Фамильяр бобёр");
        this.lang("ru_ru").addEntityType(OccultismEntities.SHUB_NIGGURATH_SPAWN, "Потомок Шаб-Ниггурата");
        this.lang("ru_ru").addEntityType(OccultismEntities.THROWN_SWORD, "Брошенный меч");
        this.lang("ru_ru").addEntityType(OccultismEntities.DEMONIC_WIFE, "Демоническая жена");
        this.lang("ru_ru").addEntityType(OccultismEntities.DEMONIC_HUSBAND, "Демонический муж");
        this.lang("ru_ru").addEntityType(OccultismEntities.IESNIUM_GOLEM, "Айзниевый голем");
        this.lang("ru_ru").addEntityType(OccultismEntities.WILD_HORDE_HUSK, "Орда диких кадавров");
        this.lang("ru_ru").addEntityType(OccultismEntities.WILD_HORDE_DROWNED, "Орда диких утопленников");
        this.lang("ru_ru").addEntityType(OccultismEntities.WILD_HORDE_CREEPER, "Орда диких криперов");
        this.lang("ru_ru").addEntityType(OccultismEntities.WILD_HORDE_SILVERFISH, "Орда диких чешуйниц");
        this.lang("ru_ru").addEntityType(OccultismEntities.POSSESSED_WEAK_BREEZE, "Слабый дикий вихрь");
        this.lang("ru_ru").addEntityType(OccultismEntities.POSSESSED_BREEZE, "Дикий вихрь");
        this.lang("ru_ru").addEntityType(OccultismEntities.POSSESSED_STRONG_BREEZE, "Сильный дикий вихрь");
        this.lang("ru_ru").addEntityType(OccultismEntities.WILD_ZOMBIE, "Дикий зомби");
        this.lang("ru_ru").addEntityType(OccultismEntities.WILD_SKELETON, "Дикий скелет");
        this.lang("ru_ru").addEntityType(OccultismEntities.WILD_SILVERFISH, "Дикая чешуйница");
        this.lang("ru_ru").addEntityType(OccultismEntities.WILD_SPIDER, "Дикий паук");
        this.lang("ru_ru").addEntityType(OccultismEntities.WILD_BOGGED, "Дикий болотник");
        this.lang("ru_ru").addEntityType(OccultismEntities.WILD_SLIME, "Дикий слизень");
        this.lang("ru_ru").addEntityType(OccultismEntities.WILD_HUSK, "Дикий кадавр");
        this.lang("ru_ru").addEntityType(OccultismEntities.WILD_STRAY, "Дикий зимогор");
        this.lang("ru_ru").addEntityType(OccultismEntities.WILD_CAVE_SPIDER, "Дикий пещерный паук");
        this.lang("ru_ru").addEntityType(OccultismEntities.POSSESSED_EVOKER, "Дикий заклинатель");
    }

    private void addMiscTranslations() {

        //"(.*?)": "(.*)",
        //this.add\("\1", "\2"\);

        this.lang("ru_ru").add(TranslationKeys.HUD_NO_PENTACLE_FOUND, "Допустимый пентакль не найден.");
        this.lang("ru_ru").add(TranslationKeys.HUD_PENTACLE_FOUND, "Текущий пентакль: %s");

        this.lang("ru_ru").add(TranslationKeys.MESSAGE_CONTAINER_ALREADY_OPEN, "Этот контейнер уже открыт другим игроком, ждите пока он его не запрёт.");

        //Должности
        this.lang("ru_ru").add("job.occultism.lumberjack", "Дровосек");
		this.lang("ru_ru").add("job.occultism.farmer", "Фермер");
        this.lang("ru_ru").add("job.occultism.crush_tier1", "Медленный дробильщик");
        this.lang("ru_ru").add("job.occultism.crush_tier2", "Дробильщик");
        this.lang("ru_ru").add("job.occultism.crush_tier3", "Быстрый дробильщик");
        this.lang("ru_ru").add("job.occultism.crush_tier4", "Очень быстрый дробильщик");
        this.lang("ru_ru").add("job.occultism.crystal_tier1", "Медленный кристаллизовщик");
        this.lang("ru_ru").add("job.occultism.crystal_tier2", "Кристаллизовщик");
        this.lang("ru_ru").add("job.occultism.crystal_tier3", "Быстрый кристаллизовщик");
        this.lang("ru_ru").add("job.occultism.crystal_tier4", "Очень быстрый кристаллизовщик");
        this.lang("ru_ru").add("job.occultism.smelt_tier1", "Медленный литейщик");
        this.lang("ru_ru").add("job.occultism.smelt_tier2", "Литейщик");
        this.lang("ru_ru").add("job.occultism.smelt_tier3", "Быстрый литейщик");
        this.lang("ru_ru").add("job.occultism.smelt_tier4", "Очень быстрый литейщик");
        this.lang("ru_ru").add("job.occultism.manage_machine", "Станочник");
        this.lang("ru_ru").add("job.occultism.transport_items", "Транспортировщик");
        this.lang("ru_ru").add("job.occultism.cleaner", "Уборщик");
        this.lang("ru_ru").add("job.occultism.trade_otherstone", "Торговец потусторонним камнем");
		this.lang("ru_ru").add("job.occultism.trader_otherrock", "Торговец потусторонней породой");
        this.lang("ru_ru").add("job.occultism.trade_otherworld_saplings", "Торговец потусторонними саженцами");
		this.lang("ru_ru").add("job.occultism.gambler", "Спекулянт");
        this.lang("ru_ru").add("job.occultism.clear_weather", "Дух ясной погоды");
        this.lang("ru_ru").add("job.occultism.rain_weather", "Дух дождливой погоды");
        this.lang("ru_ru").add("job.occultism.thunder_weather", "Дух грозы");
        this.lang("ru_ru").add("job.occultism.day_time", "Дух рассвета");
        this.lang("ru_ru").add("job.occultism.night_time", "Дух сумерек");

        //Enums
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
        this.lang("ru_ru").add("enum.occultism.book_of_calling.item_mode.set_managed_machine", "Установить управляемое устройство");
        this.lang("ru_ru").add("enum.occultism.work_area_size.small", "16х16");
        this.lang("ru_ru").add("enum.occultism.work_area_size.medium", "32х32");
        this.lang("ru_ru").add("enum.occultism.work_area_size.large", "64х64");

        //Debug messages
        this.lang("ru_ru").add("debug.occultism.debug_wand.printed_glyphs", "Глифы записаны.");
        this.lang("ru_ru").add("debug.occultism.debug_wand.glyphs_verified", "Глифы проверены.");
        this.lang("ru_ru").add("debug.occultism.debug_wand.glyphs_not_verified", "Глифы не проверены.");
        this.lang("ru_ru").add("debug.occultism.debug_wand.spirit_selected", "Дух с идентификатором %s выбран.");
        this.lang("ru_ru").add("debug.occultism.debug_wand.spirit_tamed", "Дух с идентификатором %s приручен.");
        this.lang("ru_ru").add("debug.occultism.debug_wand.deposit_selected", "Установить блок для ввода %s: сторона %s.");
        this.lang("ru_ru").add("debug.occultism.debug_wand.no_spirit_selected", "Дух не выбран.");

        //Ritual Sacrifices
        this.lang("ru_ru").add("ritual.occultism.sacrifice.cows", "Корова");
        this.lang("ru_ru").add("ritual.occultism.sacrifice.bats", "Летучая мышь");
        this.lang("ru_ru").add("ritual.occultism.sacrifice.bees", "Пчела");
        this.lang("ru_ru").add("ritual.occultism.sacrifice.zombies", "Зомби");
        this.lang("ru_ru").add("ritual.occultism.sacrifice.parrots", "Попугай");
        this.lang("ru_ru").add("ritual.occultism.sacrifice.chicken", "Курица");
        this.lang("ru_ru").add("ritual.occultism.sacrifice.pigs", "Свиньи");
        this.lang("ru_ru").add("ritual.occultism.sacrifice.humans", "Крестьянин или игрок");
        this.lang("ru_ru").add("ritual.occultism.sacrifice.squid", "Спрут");
        this.lang("ru_ru").add("ritual.occultism.sacrifice.horses", "Лошадь");
        this.lang("ru_ru").add("ritual.occultism.sacrifice.sheep", "Овца");
        this.lang("ru_ru").add("ritual.occultism.sacrifice.llamas", "Лама");
        this.lang("ru_ru").add("ritual.occultism.sacrifice.goats", "Коза");
        this.lang("ru_ru").add("ritual.occultism.sacrifice.snow_golem", "Снежный голем");
        this.lang("ru_ru").add("ritual.occultism.sacrifice.iron_golem", "Железный голем");
        this.lang("ru_ru").add("ritual.occultism.sacrifice.spiders", "Паук");
        this.lang("ru_ru").add("ritual.occultism.sacrifice.flying_passive", "Тихоня, летучая мышь, пчела или попугай");
        this.lang("ru_ru").add("ritual.occultism.sacrifice.cubemob", "Слизень или магмовый куб");
        this.lang("ru_ru").add("ritual.occultism.sacrifice.fish", "Какая-либо рыба");
        this.lang("ru_ru").add("ritual.occultism.sacrifice.axolotls", "Аксолотль");
        this.lang("ru_ru").add("ritual.occultism.sacrifice.camel", "Верблюд");
        this.lang("ru_ru").add("ritual.occultism.sacrifice.dolphin", "Дельфин");
        this.lang("ru_ru").add("ritual.occultism.sacrifice.wolfs", "Волк");
        this.lang("ru_ru").add("ritual.occultism.sacrifice.ocelot", "Оцелот");
        this.lang("ru_ru").add("ritual.occultism.sacrifice.cats", "Кошка");
        this.lang("ru_ru").add("ritual.occultism.sacrifice.vex", "Вредина");
        this.lang("ru_ru").add("ritual.occultism.sacrifice.tadpoles", "Головастик");
        this.lang("ru_ru").add("ritual.occultism.sacrifice.allay", "Тихоня");
		this.lang("ru_ru").add("ritual.occultism.sacrifice.armadillos", "Броненосец");
        this.lang("ru_ru").add("ritual.occultism.sacrifice.warden", "Хранитель");
        this.lang("ru_ru").add("ritual.occultism.sacrifice.ravager", "Разоритель");
		this.lang("ru_ru").add("ritual.occultism.sacrifice.endermen", "Эндермен");
        this.lang("ru_ru").add("ritual.occultism.sacrifice.shulker", "Шалкер");

        //Network Message
        this.lang("ru_ru").add("network.messages.occultism.request_order.order_received", "Заказ получен!");

        //Effects
        this.lang("ru_ru").add("effect.occultism.third_eye", "Третий глаз");
        this.lang("ru_ru").add("effect.occultism.double_jump", "Мульти-прыжок");
        this.lang("ru_ru").add("effect.occultism.dragon_greed", "Алчность дракона");
        this.lang("ru_ru").add("effect.occultism.mummy_dodge", "Уклонение");
        this.lang("ru_ru").add("effect.occultism.bat_lifesteal", "Похищение жизни");
        this.lang("ru_ru").add("effect.occultism.beaver_harvest", "Лесоруб");
        this.lang("ru_ru").add("effect.occultism.step_height", "Высокий шаг");
		this.lang("ru_ru").add("effect.occultism.step_blocked", "Остановка шага");
		this.lang("ru_ru").add("effect.occultism.pumpkin_head", "Тыквенная голова");

        //Potions
        this.lang("ru_ru").add("item.minecraft.potion.effect.third_eye_potion", "Зелье третьего глаза");
        this.lang("ru_ru").add("item.minecraft.potion.effect.long_third_eye_potion", "Зелье третьего глаза");
        this.lang("ru_ru").add("item.minecraft.splash_potion.effect.third_eye_potion", "Взрывное зелье третьего глаза");
        this.lang("ru_ru").add("item.minecraft.splash_potion.effect.long_third_eye_potion", "Взрывное зелье третьего глаза");
        this.lang("ru_ru").add("item.minecraft.lingering_potion.effect.third_eye_potion", "Туманное зелье третьего глаза");
        this.lang("ru_ru").add("item.minecraft.lingering_potion.effect.long_third_eye_potion", "Туманное зелье третьего глаза");
		this.lang("ru_ru").add("item.minecraft.tipped_arrow.effect.third_eye_potion", "Стрела третьего глаза");
        this.lang("ru_ru").add("item.minecraft.tipped_arrow.effect.long_third_eye_potion", "Стрела третьего глаза");

        //Sounds
        this.lang("ru_ru").add("occultism.subtitle.chalk", "Черчение мелком");
        this.lang("ru_ru").add("occultism.subtitle.brush", "Очистка щёткой");
        this.lang("ru_ru").add("occultism.subtitle.start_ritual", "Запуск ритуала");
        this.lang("ru_ru").add("occultism.subtitle.tuning_fork", "Звук камертона");
        this.lang("ru_ru").add("occultism.subtitle.crunching", "Измельчение");
        this.lang("ru_ru").add("occultism.subtitle.poof", "Вжух!");

        //Dimension types

        this.lang("ru_ru").add(Util.makeDescriptionId("dimension_type", BuiltinDimensionTypes.OVERWORLD.location()), "Обычный мир");
        this.lang("ru_ru").add(Util.makeDescriptionId("dimension_type", BuiltinDimensionTypes.NETHER.location()), "Незер");
        this.lang("ru_ru").add(Util.makeDescriptionId("dimension_type", BuiltinDimensionTypes.END.location()), "Энд");
    }

    private void addGuiTranslations() {
        this.lang("ru_ru").add("gui.occultism.book_of_calling.mode", "Режим");
        this.lang("ru_ru").add("gui.occultism.book_of_calling.work_area", "Рабочее место");
        this.lang("ru_ru").add("gui.occultism.book_of_calling.manage_machine.insert", "Сторона ввода");
        this.lang("ru_ru").add("gui.occultism.book_of_calling.manage_machine.extract", "Сторона извлечения");
        this.lang("ru_ru").add("gui.occultism.book_of_calling.manage_machine.custom_name", "Пользовательское название");

        // Spirit GUI
        this.lang("ru_ru").add("gui.occultism.spirit.age", "Распад сущности: %d%%");
        this.lang("ru_ru").add("gui.occultism.spirit.job", "%s");

        // Spirit Transporter GUI
        this.lang("ru_ru").add("gui.occultism.spirit.transporter.filter_mode", "Режим фильтрации");
        this.lang("ru_ru").add("gui.occultism.spirit.transporter.filter_mode.blacklist", "Чёрный список");
        this.lang("ru_ru").add("gui.occultism.spirit.transporter.filter_mode.whitelist", "Белый список");
        this.lang("ru_ru").add("gui.occultism.spirit.transporter.tag_filter", "Введите теги для фильтрации по символам разделения \";\".\nНапример, \"c:ores;*бревна*\".\nИспользуйте знак \"*\" для соответствия с любым символом, например, \"*руда*\" для соответствия с тегами руд из любого мода. Для фильтрации предметов, введите префикс с идентификатором предмета [\"item:\"], например,\"item:minecraft:chest\".");

        // Storage Controller GUI
		this.lang("ru_ru").add("gui.occultism.storage_controller.display.rows", "Изменить количество рядов.");
        this.lang("ru_ru").add("gui.occultism.storage_controller.space_info_label", "%d/%d");
        this.lang("ru_ru").add("gui.occultism.storage_controller.space_info_label_new", "%s%% занято.");
        this.lang("ru_ru").add("gui.occultism.storage_controller.space_info_label_types", "%s%% типов.");
        this.lang("ru_ru").add("gui.occultism.storage_controller.shift", "Нажмите Shift, чтобы узнать подробнее.");
        this.lang("ru_ru").add("gui.occultism.storage_controller.search.tooltip@", "Префикс @: поиск по идентификатору мода.");
        this.lang("ru_ru").add("gui.occultism.storage_controller.search.tooltip#", "Префикс #: поиск по подсказке предмета.");
        this.lang("ru_ru").add("gui.occultism.storage_controller.search.tooltip$", "Префикс $: поиск по тегу.");
        this.lang("ru_ru").add("gui.occultism.storage_controller.search.tooltip_rightclick", "Очистка текста через ПКМ.");
        this.lang("ru_ru").add("gui.occultism.storage_controller.search.tooltip_clear", "Очистить поиск.");
        this.lang("ru_ru").add("gui.occultism.storage_controller.search.tooltip_jei_on", "Синхронизировать поиск с JEI.");
        this.lang("ru_ru").add("gui.occultism.storage_controller.search.tooltip_jei_off", "Не синхронизировать поиск с JEI.");
        this.lang("ru_ru").add("gui.occultism.storage_controller.search.tooltip_sort_type_amount", "Сортировать по количеству.");
        this.lang("ru_ru").add("gui.occultism.storage_controller.search.tooltip_sort_type_name", "Сортировать по названию предмета.");
        this.lang("ru_ru").add("gui.occultism.storage_controller.search.tooltip_sort_type_mod", "Сортировать по названию мода.");
        this.lang("ru_ru").add("gui.occultism.storage_controller.search.tooltip_sort_direction_down", "Сортировать по возрастанию.");
        this.lang("ru_ru").add("gui.occultism.storage_controller.search.tooltip_sort_direction_up", "Сортировать по убыванию.");
        this.lang("ru_ru").add("gui.occultism.storage_controller.search.machines.tooltip@", "Префикс @: поиск по идентификатору мода.");
        this.lang("ru_ru").add("gui.occultism.storage_controller.search.machines.tooltip_sort_type_amount", "Сортировать по расстоянию.");
        this.lang("ru_ru").add("gui.occultism.storage_controller.search.machines.tooltip_sort_type_name", "Сортировать по названию устройства.");
        this.lang("ru_ru").add("gui.occultism.storage_controller.search.machines.tooltip_sort_type_mod", "Сортировать по названию мода.");

    }

    private void addRitualMessages() {
        this.lang("ru_ru").add("ritual.occultism.pentacle_help", "\u00a7lНедействительный пентакль!\u00a7r\nВы, было, пытаетесь создать пентакль: \"%s\"? Отсутствует:\n%s.");
        this.lang("ru_ru").add("ritual.occultism.pentacle_help_at_glue", " на позиции ");
        this.lang("ru_ru").add("ritual.occultism.pentacle_help.no_pentacle", "\u00a7lПентакль не найден!\u00a7r\nВы, кажется, не начертили пентакль, или в пентакле отсутствуют важные элементы. Ознакомьтесь с разделом \"Ритуалы\" в лексиконе духов: требуемый пентакль будет отображён на странице ритуала в качестве гиперссылки над рецептом ритуала.");
        this.lang("ru_ru").add("ritual.occultism.ritual_help", "\u00a7lНедопустимый ритуал!\u00a7r\nВы, было, пытались выполнить ритуал: \"%s\"? Отсутствуют предметы:\n%s");
        this.lang("ru_ru").add("ritual.occultism.disabled", "Ритуал отключён на сервере.");
        this.lang("ru_ru").add("ritual.occultism.does_not_exist", "\u00a7lНеизвестный ритуал\u00a7r. Убедитесь, что пентакли и ингредиенты расположены правильно. Если вы до сих пор не достигли желаемого результата, присоединяйтесь к нашему Discord-серверу по ссылке https://discord.gg/trE4SHRXvb . ВПН в помощь!");
        this.lang("ru_ru").add("ritual.occultism.book_not_bound", "\u00a7lНесвязанная книга призыва\u00a7r.\nПеред началом ритуала, вы должны создать эту книгу в верстаке вместе с лексиконом духов, чтобы связать её с духом.");
		this.lang("ru_ru").add("ritual.occultism.wrong_activation_item", "\u00a7lНеприемлемый предмет для активации\u00a7r.\nВы пытались начать ритуал неприемлемым предметом, попробуйте:");
        this.lang("ru_ru").add("ritual.occultism.wrong_pentacle", "\u00a7lНеправильный пентакль\u00a7r.\nВы проводите ритуал на несоответствующем пентакле, правильный:");
		this.lang("ru_ru").add("ritual.occultism.no_bowls", "\u00a7lЖертвенные чаши не найдены.\u00a7r\nПредварительно поставьте жертвенные чаши рядом с пентаклем — чёрные точки укажут возможные места. Принимает потусторонние и варианты из потусторонней породы в вариациях: простая, медная и серебряная.");
		this.lang("ru_ru").add("ritual.occultism.empty_bowls", "\u00a7lБлизлежайшие жертвенные чаши пусты.\u00a7r\nРасположите все ингредиенты в жертвенные чаши до предмета для активации: центральный предмет в рецепте является последним и помещается в эту ритуальную чашу.");
		this.lang("ru_ru").add("ritual.occultism.put_in_satchel", "Хранит пентакли в наплечной сумке.");
        this.lang("ru_ru").add("ritual.occultism.sacrifice", "" + ChatFormatting.WHITE + ChatFormatting.BOLD + "Выполните жертвоприношение:");
        this.lang("ru_ru").add("ritual.occultism.use_item", "" + ChatFormatting.WHITE + ChatFormatting.BOLD + "Используйте предмет:");

        this.lang("ru_ru").add("ritual.occultism.unknown.conditions", "Для этого ритуала удовлетворены не все требования.");
        this.lang("ru_ru").add("ritual.occultism.unknown.started", "Ритуал запущен.");
        this.lang("ru_ru").add("ritual.occultism.unknown.finished", "Ритуал полностью завершился.");
        this.lang("ru_ru").add("ritual.occultism.unknown.interrupted", "Ритуал прерван.");

        this.lang("ru_ru").add("ritual.occultism.debug.conditions", "Для этого ритуала удовлетворены не все требования.");
        this.lang("ru_ru").add("ritual.occultism.debug.started", "Ритуал запущен.");
        this.lang("ru_ru").add("ritual.occultism.debug.finished", "Ритуал полностью завершился.");
        this.lang("ru_ru").add("ritual.occultism.debug.interrupted", "Ритуал прерван.");
    }

    public void addRitualMessage(DeferredHolder<RitualFactory, RitualFactory> ritual, String key, String message) {
        this.add("ritual.%s.%s".formatted(ritual.getId().getNamespace(), ritual.getId().getPath()) + "." + key, message);
    }


    public void addRitualMessage(DeferredItem<Item> ritualDummy, String key, String message) {
        var ritualName = ritualDummy.getId().getPath().replace("ritual_dummy/", "");
        this.add("ritual.%s.%s".formatted(ritualDummy.getId().getNamespace(), ritualName) + "." + key, message);
    }

    private void addBook() {
        var helper = ModonomiconAPI.get().getContextHelper(Occultism.MODID);
        helper.book("dictionary_of_spirits");

        this.addRitualsCategory(helper);
        this.addPossessionRitualsCategory(helper);
        this.addFamiliarRitualsCategory(helper);
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
                        Ритуалы позволяют призывать духов в нашу параллельную реальность или заключать их в предметы, либо живые существа. Каждый ритуал включает [#](%1$s)пентакль[#](), [#](%1$s)ингредиенты для ритуала[#](), снабжаемые через жертвенные чаши, [#](%1$s)запускающий предмет[#](), а в некоторых случаях — [#](%1$s)жертвоприношение[#]() живых существ. Эффект фиолетовых частиц покажет, что ритуал удался и выполняется.
                        """.formatted(COLOR_PURPLE));

        helper.page("steps");
        this.lang("ru_ru").add(helper.pageTitle(), "Исполнение ритуала");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        Ритуалы всегда выполняются по неизменным этапам:
                        - Начертить пентакль;
                        - Поставить золотую ритуальную чашу;
                        - Поставить жертвенные чаши;
                        - Положить ингредиенты в чаши;
                        - Нажать [#](%1$s)ПКМ[#]() предметом для активации на золотую чашу;
                        - *Необязательно: совершить жертвоприношение почти в центре пентакля.*
                        """.formatted(COLOR_PURPLE));

        helper.page("additional_requirements");
        this.lang("ru_ru").add(helper.pageTitle(), "Дополнительные требования");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        Если ритуал показывает серые частицы над золотой ритуальной чашей, значит, нужно удовлетворить дополнительные требования, согласно страницы ритуала. После удовлетворения всех требований, ритуал покажет фиолетовые частицы и начнёт тратить предметы в жертвенных чашах.
                        """);

        helper.entry("item_use");
        this.lang("ru_ru").add(helper.entryName(), "Использование предмета");

        helper.page("intro");
        this.lang("ru_ru").add(helper.pageTitle(), "Использование предмета");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        Для выполнения некоторых ритуалов требуется использовать определённые предметы. Чтобы провести ритуал, используйте предмет, изображённый на странице ритуала на расстоянии **16 блоков** от [](item://occultism:golden_sacrificial_bowl).
                        \\
                        \\
                        **Важно**: запустите ритуал перед использованием предмета. Серые частицы указывают на то, что ритуал готов к использованию предмета.
                        """);

        helper.entry("sacrifice");
        this.lang("ru_ru").add(helper.entryName(), "Жертвы");

        helper.page("intro");
        this.lang("ru_ru").add(helper.pageTitle(), "Жертвы");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        Некоторые ритуалы требуют жертвоприношение живых существ, чтобы обеспечить необходимую энергию для призыва духа. Жертвы изображены на странице ритуала в подразделе "Жертвоприношение". Чтобы выполнить жертвоприношение: убейте животное в пределах 8 блоков от золотой ритуальной чаши: жертвоприношение считается только убийством, совершённым игроком!
                         """);

        helper.entry("summoning_rituals");
        this.lang("ru_ru").add(helper.entryName(), "Ритуалы для призыва");

        helper.entry("possession_rituals");
        this.lang("ru_ru").add(helper.entryName(), "Ритуалы для завладения");

        helper.entry("crafting_rituals");
        this.lang("ru_ru").add(helper.entryName(), "Ритуалы для заключения");

        helper.entry("familiar_rituals");
        this.lang("ru_ru").add(helper.entryName(), "Ритуалы для фамильяров");
    }

    private void addPossessionRitualsCategory(BookContextHelper helper) {
        helper.category("possession_rituals");
        this.lang("ru_ru").add(helper.categoryName(), "Ритуалы для завладения");

        helper.entry("return_to_rituals");
        this.lang("ru_ru").add(helper.entryName(), "Вернуться в категорию «Ритуалы»");

        helper.entry("overview");
        this.lang("ru_ru").add(helper.entryName(), "Ритуалы для завладения");

        helper.page("intro");
        this.lang("ru_ru").add(helper.pageTitle(), "Ритуалы для завладения");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        Ритуалы для завладения заключают духов в живых существ, до некоторой степени давая призывателю контроль над одержимым существом.
                        \\
                        \\
                        По сути, эти ритуалы применяются для получения редких предметов, не вторгаясь в опасные места.
						\\
                        \\
                        Одержимые существа считаются к их собратьям в целях жертвоприношения в ритуалах.
                           """);

        helper.entry("possess_enderman");
        this.lang("ru_ru").add(helper.entryName(), "Одержимый эндермер");

        helper.page("entity");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        **Добыча**: [](item://minecraft:ender_pearl) 1-3 шт.,
						а также 10%% шанс выпадания [](item://minecraft:eye_armor_trim_smithing_template).
                                """);

        helper.page("ritual");
        //no text

        helper.page("description");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        При проведении ритуала [#](%1$s)эндермен[#]() появляется за счёт жизненной энергии [#](%1$s)свиньи[#](), но сразу одержим призванным [#](%1$s)Джинном[#](). При убийстве с [#](%1$s)одержимого эндермена[#]() всегда будет выпадать не более одного [](item://minecraft:ender_pearl).
                                """.formatted(COLOR_PURPLE));

        helper.entry("wither_skull");
        this.lang("ru_ru").add(helper.entryName(), "Дикая охота (Охота Каина)");

        helper.page("intro");
        this.lang("ru_ru").add(helper.pageTitle(), "Череп визер-скелета");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        Есть альтернативный способ получить эти черепа помимо опасного путешествия в Незер. Легендарная [#](%1$s)дикая охота[#]() (Охота Каина) состоит из [#](%1$s)могущественных духов[#](), принявших облик визер-скелетов. Хотя призыв дикой охоты невероятно опасен — это наибыстрейший способ получить черепа визер-скелетов.
                           """.formatted(COLOR_PURPLE));

        helper.page("ritual");
        //no text

        helper.entry("possess_endermite");
        this.lang("ru_ru").add(helper.entryName(), "Одержимый эндермит");

        helper.page("entity");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        **Добыча**: [](item://minecraft:end_stone) 1-2 шт.,
						а также 25%% шанс выпадения глаза.
                                """);

        helper.page("ritual");
        //no text

        helper.page("description");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        При проведении ритуал обманом заставляет [#](%1$s)эндермита[#]() появиться. Камни и земля символизируют окружающий мир, после этого бросается яйцо для имитации использования эндер-жемчуга. Когда появляется эндермит, призванный [#](%1$s)Фолиот[#]() сразу завладевает им, посещает [#](%1$s)Энд[#]() и возвращается обратно. С [#](%1$s)одержимого эндермита[#]() всегда будет выпадать не более одного [](item://minecraft:end_stone) при убийстве.
                                """.formatted(COLOR_PURPLE));

        helper.entry("possess_ghast");
        //moved to OccultismBookProvider#makePossessGhastEntry

        helper.entry("possess_skeleton");
        this.lang("ru_ru").add(helper.entryName(), "Одержимый скелет");

        helper.page("entity");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        **Добыча**: [](item://minecraft:skeleton_skull) 1 шт.
                                """);

        helper.page("ritual");
        //no text

        helper.page("description");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        При проведении ритуала появляется [#](%1$s)скелет[#]() за счёт жизненной энергии [#](%1$s)курицы[#](), но сразу одержим призванным [#](%1$s)Фолиотом[#](). [#](ad03fc)Одержимый скелет[#]() будет устойчивым к дневному свету, но с него всегда будет выпадать не более одного [](item://minecraft:skeleton_skull) при убийстве.
                                """.formatted(COLOR_PURPLE));

        helper.entry("possess_unbound_parrot");
        this.lang("ru_ru").add(helper.entryName(), "Несвязанный попугай");

        helper.page("entity");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        **Предоставляет**: приручаемый попугай.
                          """);

        helper.page("ritual");
        //no text

        helper.page("description");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        При проведении ритуала призывается [#](%1$s)Фолиот[#]() **в виде дикого духа**.
                        \\
                        \\
                        Убийство [#](%1$s)курицы[#]() и подношение красителей предназначается для того, чтобы склонить Фолиота принять облик попугая. Хотя [#](%1$s)Фолиот[#]() не находится среди умнейших духов — в ряде случаях он дурно понимает указания...
                          """.formatted(COLOR_PURPLE));

        helper.page("description2");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        *Если появится [#](%1$s)курица[#]() — это не ошибка; это значит, что вам не повезло!*
                           """.formatted(COLOR_PURPLE));

        helper.entry("possess_unbound_otherworld_bird");
        this.lang("ru_ru").add(helper.entryName(), "Несвязанный дрикрыл");

        helper.page("entity");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        **Предоставляет**: приручаемый дрикрыл.
                          """);

        helper.page("ritual");
        //no text

        helper.page("description");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        Чтобы узнать больше посмотрите [Фамильяр-дрикрыл](entry://familiar_rituals/familiar_otherworld_bird).
                          """);
    }

    private void addFamiliarRitualsCategory(BookContextHelper helper) {
        helper.category("familiar_rituals");

        helper.entry("return_to_rituals");
        this.lang("ru_ru").add(helper.entryName(), "Вернуться в категорию «Ритуалы»");

        helper.entry("overview");
        this.lang("ru_ru").add(helper.entryName(), "Ритуалы для фамильяров");

        helper.page("intro");
        this.lang("ru_ru").add(helper.pageTitle(), "Ритуалы для фамильяров");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        Ритуалы для фамильяров призывают духов для непосредственной помощи призывателю. Духи, как правило, обитают в теле животного, позволяя им защищать сущность от распада. Фамильяры активно защищют призывателя и наделяют усилениями.
                                """.formatted(COLOR_PURPLE));

        helper.page("ring");
        this.lang("ru_ru").add(helper.pageTitle(), "Оснащение фамильярами");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        Находчивые призыватели нашли способ заключать фамильяров в драгоценные камни, которые пассивно накладывают свои усиления — [Перстень для фамильяра](entry://crafting_rituals/craft_familiar_ring).
                                """.formatted(COLOR_PURPLE));

        helper.page("trading");
        this.lang("ru_ru").add(helper.pageTitle(), "Оснащение фамильярами");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        Фамильярами можно свободно обмениваться при нахождении в [Перстне для фамильяра](entry://crafting_rituals/craft_familiar_ring).
                        \\
                        \\
                        При освобождении духа из перстня, тот признает своим новым хозяином того, кто его вызволил.
                                 """.formatted(COLOR_PURPLE));

        helper.entry("familiar_bat");
        this.lang("ru_ru").add(helper.entryName(), "Фамильяр-летучая мышь");

        helper.page("entity");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        **Наделяет**: [#](%1$s)«ночное зрение»[#]().
                               """.formatted(COLOR_PURPLE));

        helper.page("ritual");
        //no text

        helper.page("description");
        this.lang("ru_ru").add(helper.pageTitle(), "Описание");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        **Обновление поведения**:\\
                        При обновлении фамильяром-кузнецом, фамильяр-летучая мышь наделяет хозяина эффектом «Похищение жизни».
                           """.formatted(COLOR_PURPLE));

        helper.entry("familiar_beaver");
        this.lang("ru_ru").add(helper.entryName(), "Фамильяр-бобёр");

        helper.page("entity");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        **Предоставляет**: [#](%1$s)увеличение скорости рубки дерева[#]().
                               """.formatted(COLOR_PURPLE));

        helper.page("ritual");
        //no text

        helper.page("description");
        this.lang("ru_ru").add(helper.pageTitle(), "Описание");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        Фамильяр-бобёр будет срубать близлежащие деревья, когда они вырастут с обычного саженца в дерево; справляется лишь с малыми деревьями.
                        \\
                        \\
                        **Обновление поведения**:\\
                        При нажатии на нём ПКМ пустой рукой даёт бесплатные лакомства.
                           """.formatted(COLOR_PURPLE));

        helper.entry("familiar_beholder");
        this.lang("ru_ru").add(helper.entryName(), "Фамильяр-созерцатель");

        helper.page("entity");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        **Предоставляет**: [#](%1$s)«подсветка врагов»[#](), [#](%1$s)стрельба **ДОЛБАНЫМИ ЛАЗЕРНЫМИ ЛУЧАМИ**[#]().
                               """.formatted(COLOR_PURPLE));

        helper.page("ritual");
        //no text

        helper.page("description");
        this.lang("ru_ru").add(helper.pageTitle(), "Описание");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        Фамильяр-созерцатель подсвечивает эффектом свечения близлежащих существ и стреляет лазерными лучами по врагам. Для получения временного урона и скорости — **поедает** (слабых) малышей **Шаб-Ниггуратов**.
                        \\
                        \\
                        **Обновление поведения**:\\
                        При обновлении фамильяром-кузнецом, наделяет хозяина устойчивостью к слепоте, а после подсветки хранителя, устойчивость распространяется и на темноту.
                           """.formatted(COLOR_PURPLE));

        helper.entry("familiar_blacksmith");
        this.lang("ru_ru").add(helper.entryName(), "Фамильяр-кузнец");

        helper.page("entity");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        **Осуществляет**: [#](%1$s)починка снаряжения при добыче ресурсов[#](), [#](%1$s)обновляет других фамильяров[#]().
                               """.formatted(COLOR_PURPLE));

        helper.page("ritual");
        //no text

        helper.page("description");
        this.lang("ru_ru").add(helper.pageTitle(), "Описание");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        Всякий раз, как игрок подбирает камень, есть шанс, что фамильяр-кузнец немножко починит снаряжение игрока.
                        \\
                        \\
                        **Обновление поведения**: \\
                        Улучшает других фамильяров, однако улучшить самого невозможно.
                           """.formatted(COLOR_PURPLE));

        helper.page("description2");
        this.lang("ru_ru").add(helper.pageTitle(), "Улучшение фамильяров");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        Чтобы улучшать других фамильяров, кузнецу необходимо дать железные слитки или блоки, нажав [#](%1$s)ПКМ[#]() на него.
						\\
						Когда кузнец улучшает другого фамильяра, появляется сообщение на панели действий (над горячей панелью), прозвучит звук наковальни, а в конце имени фамильяра отобразится звезда.
                        \\
                        Улучшенные фамильяры дают дополнительные эффекты.
                           """.formatted(COLOR_PURPLE));

        helper.entry("familiar_chimera");
        this.lang("ru_ru").add(helper.entryName(), "Фамильяр-химера");

        helper.page("entity");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        **Предоставляет**: [#](%1$s)ездовое, верховое животное[#]().
                               """.formatted(COLOR_PURPLE));

        helper.page("ritual");
        //no text

        helper.page("description");
        this.lang("ru_ru").add(helper.pageTitle(), "Описание");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        Для роста фамильяры-химеры нужно кормить её мясом (любым). При росте она будет получать урон и скорость. Как только вырастит, игроки смогут оседлать её. При кормлении [](item://minecraft:golden_apple), [#](%1$s)коза[#]() отчленится и станет отдельным фамильяром.
                        \\
                        \\
                        Отцеплённую фамильяра-козу можно использовать для получения [Шаб-Ниггурата](entry://familiar_rituals/familiar_shub_niggurath).
                           """.formatted(COLOR_PURPLE));

        helper.page("description2");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        **Обновление поведения**:\\
                        Как только фамильяр-коза обновлена фамильяром-кузнецом, она приобретёт сигнальный колокольчик; во время атаки на неё она зазвонит из колокольчика и **привлечёт врагов** в пределах большого радиуса.
                           """.formatted(COLOR_PURPLE));

        helper.entry("familiar_cthulhu");
        this.lang("ru_ru").add(helper.entryName(), "Фамильяр-Ктулху");

        helper.page("entity");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        **Наделяет**: [#](%1$s)«водное дыхание»[#](), [#](%1$s)невозмутимость[#]() и [#](%1$s)превращение призмарина[#]().
                               """.formatted(COLOR_PURPLE));

        helper.page("ritual");
        //no text

        helper.page("description");
        this.lang("ru_ru").add(helper.pageTitle(), "Описание");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        Дайте [](item://minecraft:lapis_lazuli), чтобы превратить в [](item://minecraft:prismarine_shard).\\
                        \\
                        **Обновление поведения**\\
                        При обновлении фамильяром-кузнецом, он будет служить передвижным источником света..\\
                        Вы будете получать больше призмарина за каждый лазурит.
                           """.formatted(COLOR_PURPLE));

        helper.entry("familiar_shub_niggurath");
        this.lang("ru_ru").add(helper.entryName(), "Фамильяр-Шаб-Ниггурат");

        helper.page("entity");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        **Предоставляет**: [#](%1$s)порождения малых версий самого себя для вашей защиты[#]().
                                   """.formatted(COLOR_PURPLE));

        helper.page("ritual");
        this.lang("ru_ru").add(helper.pageTitle(), "Ритуал");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        Фамильяр-[#](%1$s)Шаб-Ниггурат[#]() не призывается напрямую. Для начала призовите [Фамильяра-химеру](entry://familiar_rituals/familiar_chimera) и скормите ей [](item://minecraft:golden_apple), чтобы отчленить [#](%1$s)козу[#](). Приведите козу в [#](%1$s)лесной биом[#](), затем нажмите на неё каким-либо [чёрным красителем](item://minecraft:black_dye), [](item://minecraft:flint) и [](item://minecraft:ender_eye), чтобы призвать [#](%1$s)Шаб-Ниггурата[#]().
                           """.formatted(COLOR_PURPLE));

        helper.page("description");
        this.lang("ru_ru").add(helper.pageTitle(), "Описание");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        **Обновление поведения**:\\
                        При обновлении фамильяром-кузнецом, он приобретёт сигнальный колокольчик; во время атаки на него он зазвонит из колокольчика и **привлечёт врагов** в пределах большого радиуса.
                           """.formatted(COLOR_PURPLE));

        helper.entry("familiar_deer");
        this.lang("ru_ru").add(helper.entryName(), "Фамильяр-олень");

        helper.page("entity");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        **Наделяет**: [#](%1$s)«скорость», «прыгучесть» и «высокий шаг»[#]().
                                   """.formatted(COLOR_PURPLE));

        helper.page("ritual");
        //no text

        helper.page("description");
        this.lang("ru_ru").add(helper.pageTitle(), "Описание");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        **Обновление поведения**:\\
                        При обновлении фамильяром-кузнецом, он улучшает эффект «Высокий шаг» и атакует близлежащих врагов булавой. Ага, **булавой**.
                           """.formatted(COLOR_PURPLE));

        helper.entry("familiar_devil");
        this.lang("ru_ru").add(helper.entryName(), "Фамильяр-дьявол");

        helper.page("entity");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        **Наделяет**: [#](%1$s)«огнестойкость»[#](), [#](%1$s)атакует врагов[#]().
                                   """.formatted(COLOR_PURPLE));

        helper.page("ritual");
        //no text

        helper.page("description");
        this.lang("ru_ru").add(helper.pageTitle(), "Описание");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        **Обновление поведения**:\\
                        Зачаровывает золотое яблоко при нажатии ПКМ на нём, однако у действия большой период времени.
                           """.formatted(COLOR_PURPLE));

        helper.entry("familiar_dragon");
        this.lang("ru_ru").add(helper.entryName(), "Фамильяр-дракон");

        helper.page("entity");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        **Наделяет**: [#](%1$s)повышенное получение опыта[#](), любит палочки.
                                   """.formatted(COLOR_PURPLE));

        helper.page("ritual");
        //no text

        helper.page("description");
        this.lang("ru_ru").add(helper.pageTitle(), "Описание");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        Алчные фамильяры способны оседлать фамильяра-дракона, дополнительно наделяя его эффектом алчности.
                        \\
                        \\
                        **Обновление поведения**:\\
                        При обновлении фамильяром-кузнецом, он будет метать мечи на близлежащих врагов.
                           """.formatted(COLOR_PURPLE));


        helper.entry("familiar_fairy");
        this.lang("ru_ru").add(helper.entryName(), "Фамильяр-фея");

        helper.page("entity");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        **Осуществляет**: [#](%1$s)помогает фамильярам[#](), [#](%1$s)предотвращает смерть фамильяра[#](), [#](%1$s)истощает жизненную силу врага[#]().
                                   """.formatted(COLOR_PURPLE));

        helper.page("ritual");
        //no text

        helper.page("description");
        this.lang("ru_ru").add(helper.pageTitle(), "Описание");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        Фамильяр-фея **оберегает от смерти других фамильяров** (с перезарядкой), благодаря **благоприятными эффектами** выручает других фамильяров и **истощает жизненную силу врагов** для помощи хозяину.
                        \\
                        \\
                        **Обновление поведения**:\\
                        При нажатии ПКМ с помощью бутылочки позволяет собирать драконье дыхание.
                           """.formatted(COLOR_PURPLE));

        helper.entry("familiar_greedy");
        this.lang("ru_ru").add(helper.entryName(), "Алчный фамильяр");

        helper.page("entity");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        **Осуществляет**: [#](%1$s)увеличение дальности подбирания[#](), [#](%1$s)подбирает предметы[#]().
                                   """.formatted(COLOR_PURPLE));
        helper.page("ritual");
        //no text

        helper.page("description");
        this.lang("ru_ru").add(helper.pageTitle(), "Описание");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        Алчный фамильяр, подбирающий близлежащие предметы хозяину является Фолиотом. В случае ловли фамильяра в перстень его радиус подбора увеличивается.
                        \\
                        \\
                        **Обновление поведения**:\\
                        При обновлении фамильяром-кузнецом, он сможет находить блоки хозяину. Нажмите [#](%1$s)ПКМ[#]() на него блоком, чтобы указать, что именно ему искать.
                           """.formatted(COLOR_PURPLE));

        helper.entry("familiar_guardian");
        this.lang("ru_ru").add(helper.entryName(), "Фамильяр-страж");

        helper.page("entity");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        **Осуществляет**: [#](%1$s)предотвращает смерть игрока при жизни[#]().
                                   """.formatted(COLOR_PURPLE));

        helper.page("ritual");
        //no text

        helper.page("description");
        this.lang("ru_ru").add(helper.pageTitle(), "Описание");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        Всякий раз, как хозяин близок к смерти, фамильяр-страж жертвует своей конечностью хозяину, вследствие чего **предотвращает смерть**. Игрок перестаёт находиться под защитой при смерти стража. Страж, будучи призванным, появляется со **случайным количеством конечностей**; нет гарантии, что призовётся полноценный страж.
                           """.formatted(COLOR_PURPLE));

        helper.page("description2");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        **Обновление поведения**:\\
                        При обновлении фамильяром-кузнецом вновь приобретает конечность.
                           """.formatted(COLOR_PURPLE));

        helper.entry("familiar_headless");
        this.lang("ru_ru").add(helper.entryName(), "Фамильяр-безголовый человек на крысе");

        helper.page("entity");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        **Наделяет**: [#](%1$s)усиление условного урона[#]().
                                   """.formatted(COLOR_PURPLE));

        helper.page("ritual");
        //no text

        helper.page("description");
        this.lang("ru_ru").add(helper.pageTitle(), "Описание");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        Фамильяр-безголовый человек на крысе похищает головы существ возле себя при убийстве монстров. Затем наделяет своего хозяина эффектом «Усиление урона» против того типа существа. В случае падения здоровья **ниже 50%%** безголовый человек на крысе погибает, но хозяин, в свою очередь, сможет его воссоздать, давая крысе: [](item://minecraft:wheat), [](item://minecraft:stick), [](item://minecraft:hay_block) и [](item://minecraft:carved_pumpkin).
                           """);

        helper.page("description2");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        **Обновление поведения**:\\
                        При обновлении фамильяром-кузнецом, он наделяет слабостью близлежащих монстров того же типа, чью голову украл, а если владелец смотрит в глаза Эндермена, тот не будет злиться на него.
                           """.formatted(COLOR_PURPLE));

        helper.entry("familiar_mummy");
        this.lang("ru_ru").add(helper.entryName(), "Фамильяр-мумия");

        helper.page("entity");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        **Наделяет**: [#](%1$s)эффект «уклонение»[#](), [#](%1$s)сражается с вашими врагами[#]().
                                   """.formatted(COLOR_PURPLE));

        helper.page("ritual");
        //no text

        helper.page("description");
        this.lang("ru_ru").add(helper.pageTitle(), "Описание");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        Фамильяр-мумия является мастером боевых искусств, сражающаяся, чтобы защитить своего хозяина.
                        \\
                        \\
                        **Обновление поведения**:\\
                        При обновлении фамильяром-кузнецом, он будет наносить ещё больше урона и удваивать шанс уклонения.
                            """.formatted(COLOR_PURPLE));

        helper.entry("familiar_otherworld_bird");
        this.lang("ru_ru").add(helper.entryName(), "Фамильяр-дрикрыл");

        helper.page("entity");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        **Наделяет**: [#](%1$s)«мульти-прыжок»[#](), [#](%1$s)«прыгучесть»[#](), [#](%1$s)«плавное падение»[#]().
                                   """.formatted(COLOR_PURPLE));

        helper.page("ritual");
        //no text

        helper.page("description");
        this.lang("ru_ru").add(helper.pageTitle(), "Описание");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        [#](%1$s)Дрикрылы[#]() — это подкласс [#](%1$s)Джинна[#](), которые заведомо дружелюбны к людям. Как правило, они принимают облик попугая с тёмно-синим и фиолетовым окрасом. Будучи рядом, дрикрылы наделяют своего хозяина ограниченными возможностями полёта.
                        \\
                        \\
                        **Обновление поведения**:\\
                        увеличивает количество прыжков и меняет «Плавное падение» на «Устойчивость к урону от падения».
                            """.formatted(COLOR_PURPLE));

        helper.page("description2");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        Для получения попугая или фамильяра-попугая с целью жертвоприношения, попробуйте призвать их либо используя [Ритуал: дикий попугай](entry://possession_rituals/possess_unbound_parrot), или [Ритуал: фамильяр-попугай](entry://familiar_rituals/familiar_parrot).
                        \\
                        \\
                        (**Совет:** в случае использования вами моды, защищающие питомцев от смерти, используйте Ритуал: дикий попугай!)
                            """.formatted(COLOR_PURPLE));

        helper.entry("familiar_parrot");
        this.lang("ru_ru").add(helper.entryName(), "Фамильяр-попугай");

        helper.page("entity");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        **Предусматривает**: [#](%1$s)собеседника[#]().
                                   """.formatted(COLOR_PURPLE));

        helper.page("ritual");
        //no text

        helper.page("description");
        this.lang("ru_ru").add(helper.pageTitle(), "Описание");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        При проведении этого ритуала [#](%1$s)Фолиот[#]() призывается **в роли фамильяра**; убийство [#](%1$s)курицы[#]() и подношение красителей предназначается для того, чтобы склонить [#](%1$s)Фолиота[#]() принять облик попугая.
						\\
                        Хотя [#](%1$s)Фолиот[#]() не находится среди умнейших духов — в ряде случаях он дурно понимает указания...
                            """.formatted(COLOR_PURPLE));

        helper.page("description2");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        *Если появится [#](%1$s)курица[#]() — это не ошибка; это значит, что вам не повезло!*
                        \\
                        \\
                        **Обновление поведения**:\\
                        Не обновляется фамильяром кузнецом.
                           """.formatted(COLOR_PURPLE));
        //no text

        helper.entry("resurrect_allay");
        this.lang("ru_ru").add(helper.entryName(), "Очистка вредины в тихоню");

        helper.page("entity");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        **Даёт**: Тихоню.
                          """);

        helper.page("ritual");

        helper.page("description");
        this.lang("ru_ru").add(helper.pageTitle(), "Описание");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        Очистка вредины в тихоню в процессе воскресения — раскрывает её истинное имя.
                          """.formatted(COLOR_PURPLE));
    }

    private void addAdvancements() {
        //"advancements\.occultism\.(.*?)\.title": "(.*)",
        //this.advancementTitle\("\1", "\2"\);
        this.lang("ru_ru").advancementTitle("root", "Occultism");
        this.lang("ru_ru").advancementDescr("root", "Принять духовность!");
        this.lang("ru_ru").advancementTitle("summon_foliot_crusher", "Удвоение руд");
        this.lang("ru_ru").advancementDescr("summon_foliot_crusher", "Хрусть! Хрусть! Хрусть!");
        this.lang("ru_ru").advancementTitle("familiars", "Occultism: друзья");
        this.lang("ru_ru").advancementDescr("familiars", "Воспользуйтесь ритуалом, чтобы призвать фамильяра.");
        this.lang("ru_ru").advancementDescr("familiar.bat", "Заманите обычную летучую мышь близко к фамильяру-летучей мыши.");
        this.lang("ru_ru").advancementTitle("familiar.bat", "Каннибализм");
        this.lang("ru_ru").advancementDescr("familiar.capture", "Поймайте фамильяра в «Перстень для фамильяра».");
        this.lang("ru_ru").advancementTitle("familiar.capture", "Поймайте каждого!");
        this.lang("ru_ru").advancementDescr("familiar.cthulhu", "Опечальте фамильяра-Ктулху.");
        this.lang("ru_ru").advancementTitle("familiar.cthulhu", "Вы изверг!");
        this.lang("ru_ru").advancementDescr("familiar.deer", "Понаблюдайте, как фамильяр-олень опорожняется семенами демона.");
        this.lang("ru_ru").advancementTitle("familiar.deer", "Демонические экскременты");
        this.lang("ru_ru").advancementDescr("familiar.devil", "Прикажите фамильяру-демону изрыгнуть пламенем.");
        this.lang("ru_ru").advancementTitle("familiar.devil", "Пламя Преисподней");
        this.lang("ru_ru").advancementDescr("familiar.dragon_nugget", "Дайте фамильяру-дракону кусочек золота.");
        this.lang("ru_ru").advancementTitle("familiar.dragon_nugget", "Договорились!");
        this.lang("ru_ru").advancementDescr("familiar.dragon_ride", "Позвольте алчному фамильяру подобрать что-нибудь во время езды на фамильяре-драконе.");
        this.lang("ru_ru").advancementTitle("familiar.dragon_ride", "В тесной взаимосвязи");
        this.lang("ru_ru").advancementDescr("familiar.greedy", "Позвольте алчному фамильяру подобрать что-нибудь для вас.");
        this.lang("ru_ru").advancementTitle("familiar.greedy", "Посыльный");
        this.lang("ru_ru").advancementDescr("familiar.party", "Потанцуйте с фамильяром.");
        this.lang("ru_ru").advancementTitle("familiar.party", "Потанцуем!");
        this.lang("ru_ru").advancementDescr("familiar.rare", "Раздобудьте редкий вид фамильяра.");
        this.lang("ru_ru").advancementTitle("familiar.rare", "Редкий друг");
        this.lang("ru_ru").advancementDescr("familiar.root", "Воспользуйтесь ритуалом для призыва фамильяра.");
        this.lang("ru_ru").advancementTitle("familiar.root", "Occultism: друзья");
        this.lang("ru_ru").advancementDescr("familiar.mans_best_friend", "Погладьте фамильяра-дракона и поиграйте с ним в игру «Принеси мяч».");
        this.lang("ru_ru").advancementTitle("familiar.mans_best_friend", "Лучший друг человека");
        this.lang("ru_ru").advancementTitle("familiar.blacksmith_upgrade", "В полной боевой экипировке!");
        this.lang("ru_ru").advancementDescr("familiar.blacksmith_upgrade", "Позвольте фамильяру-кузнецу улучшить какого-нибудь из ваших фамильяров.");
        this.lang("ru_ru").advancementTitle("familiar.guardian_ultimate_sacrifice", "Бескомпромиссное жертвоприношение");
        this.lang("ru_ru").advancementDescr("familiar.guardian_ultimate_sacrifice", "Позвольте фамильяру-стражу умереть ради вас.");
        this.lang("ru_ru").advancementTitle("familiar.headless_cthulhu_head", "Какой ужас!");
        this.lang("ru_ru").advancementDescr("familiar.headless_cthulhu_head", "Убейте Ктулху рядом с фамильяром-безголовым человеком на крысе.");
        this.lang("ru_ru").advancementTitle("familiar.headless_rebuilt", "Мы можем восстановить его");
        this.lang("ru_ru").advancementDescr("familiar.headless_rebuilt", "\"Воссоздайте\" фамильяра-безголового человека на крысе после его смерти.");
        this.lang("ru_ru").advancementTitle("familiar.chimera_ride", "По коням!");
        this.lang("ru_ru").advancementDescr("familiar.chimera_ride", "Оседлайте фамильяра-химеру в момент полного её насыщения.");
        this.lang("ru_ru").advancementTitle("familiar.goat_detach", "Демонтаж");
        this.lang("ru_ru").advancementDescr("familiar.goat_detach", "Дайте фамильяру-химере золотое яблоко.");
        this.lang("ru_ru").advancementTitle("familiar.shub_niggurath_summon", "Чёрный козёл лесов");
        this.lang("ru_ru").advancementDescr("familiar.shub_niggurath_summon", "Превратите фамильяра-козу в нечто мерзкое.");
        this.lang("ru_ru").advancementTitle("familiar.shub_cthulhu_friends", "Страсть к сверхъестественному");
        this.lang("ru_ru").advancementDescr("familiar.shub_cthulhu_friends", "Увидьте, как Шаб-Ниггурат и Ктулху держатся за руки.");
        this.lang("ru_ru").advancementTitle("familiar.shub_niggurath_spawn", "Подумайте о детях!");
        this.lang("ru_ru").advancementDescr("familiar.shub_niggurath_spawn", "Позвольте потомку Шаб-Ниггурата нанести урон врагу взрывом.");
        this.lang("ru_ru").advancementTitle("familiar.beholder_ray", "Смертельный луч");
        this.lang("ru_ru").advancementDescr("familiar.beholder_ray", "Позвольте фамильяру-созерцателю атаковать врага.");
        this.lang("ru_ru").advancementTitle("familiar.beholder_eat", "Голод");
        this.lang("ru_ru").advancementDescr("familiar.beholder_eat", "Увидьте, как фамильяр-созерцатель пожирает потомка Шаб-Ниггурата.");
        this.lang("ru_ru").advancementTitle("familiar.fairy_save", "Ангел-хранитель");
        this.lang("ru_ru").advancementDescr("familiar.fairy_save", "Позвольте фамильяру-фее спасти какого-нибудь из ваших фамильяров от неминуемой смерти.");
        this.lang("ru_ru").advancementTitle("familiar.mummy_dodge", "Ниндзя!");
        this.lang("ru_ru").advancementDescr("familiar.mummy_dodge", "Уклонитесь от удара с помощью эффекта уклонения фамильяра-мумии.");
        this.lang("ru_ru").advancementTitle("familiar.beaver_woodchop", "Дровосек");
        this.lang("ru_ru").advancementDescr("familiar.beaver_woodchop", "Позвольте фамильяру-бобру срубить дерево.");
        this.lang("ru_ru").advancementTitle("chalks.root", "Occultism: мелки");
        this.lang("ru_ru").advancementDescr("chalks.root", "Разноцветные.");
        this.lang("ru_ru").advancementTitle("chalks.white", "Применение белого мелка");
        this.lang("ru_ru").advancementDescr("chalks.white", "Для первого уровня основания пентакля.");
        this.lang("ru_ru").advancementTitle("chalks.light_gray", "Применение светло-серого мелка");
        this.lang("ru_ru").advancementDescr("chalks.light_gray", "Для второго уровня основания пентакля.");
        this.lang("ru_ru").advancementTitle("chalks.gray", "Применение серого мелка");
        this.lang("ru_ru").advancementDescr("chalks.gray", "Для третьего уровня основания пентакля.");
        this.lang("ru_ru").advancementTitle("chalks.black", "Применение чёрного мелка");
        this.lang("ru_ru").advancementDescr("chalks.black", "Для четвёртого уровня основания пентакля.");
        this.lang("ru_ru").advancementTitle("chalks.brown", "Применение коричневого мелка");
        this.lang("ru_ru").advancementDescr("chalks.brown", "Кого приманиваем?");
        this.lang("ru_ru").advancementTitle("chalks.red", "Применение красного мелка");
        this.lang("ru_ru").advancementDescr("chalks.red", "Третий уровень!");
        this.lang("ru_ru").advancementTitle("chalks.orange", "Применение оранжевого мелка");
        this.lang("ru_ru").advancementDescr("chalks.orange", "Третий уровень?");
        this.lang("ru_ru").advancementTitle("chalks.yellow", "Применение жёлтого мелка");
        this.lang("ru_ru").advancementDescr("chalks.yellow", "Завладение.");
        this.lang("ru_ru").advancementTitle("chalks.lime", "Применение лаймового мелка");
        this.lang("ru_ru").advancementDescr("chalks.lime", "Второй уровень.");
        this.lang("ru_ru").advancementTitle("chalks.green", "Применение зелёного мелка");
        this.lang("ru_ru").advancementDescr("chalks.green", "Привлечение дебрей");
        this.lang("ru_ru").advancementTitle("chalks.cyan", "Применение бирюзового мелка");
        this.lang("ru_ru").advancementDescr("chalks.cyan", "Древние знания.");
        this.lang("ru_ru").advancementTitle("chalks.light_blue", "Применение голубого мелка");
        this.lang("ru_ru").advancementDescr("chalks.light_blue", "Стабилизатор дебрей.");
        this.lang("ru_ru").advancementTitle("chalks.blue", "Применение синего мелка");
        this.lang("ru_ru").advancementDescr("chalks.blue", "Четвёртый уровень.");
        this.lang("ru_ru").advancementTitle("chalks.purple", "Применение фиолетового мелка");
        this.lang("ru_ru").advancementDescr("chalks.purple", "Наполнение.");
        this.lang("ru_ru").advancementTitle("chalks.magenta", "Применение пурпурного мелка");
        this.lang("ru_ru").advancementDescr("chalks.magenta", "Мощь дракона.");
        this.lang("ru_ru").advancementTitle("chalks.pink", "Применение розового мелка");
        this.lang("ru_ru").advancementDescr("chalks.pink", "Сила дикой природы.");
        this.lang("ru_ru").advancementTitle("chalks.rainbow", "Применение радужного мелка");
        this.lang("ru_ru").advancementDescr("chalks.rainbow", "Зачем вам столько мелков?");
        this.lang("ru_ru").advancementTitle("chalks.void", "Применение пустотного мелаChalk");
        this.lang("ru_ru").advancementDescr("chalks.void", "...");
    }

    private void addKeybinds() {
        this.lang("ru_ru").add("key.occultism.category", "Occultism");
        this.lang("ru_ru").add("key.occultism.backpack", "Открыть наплечную сумку");
		this.lang("ru_ru").add("key.occultism.ender_bag", "Открыть эндер-наплечную сумку");
        this.lang("ru_ru").add("key.occultism.storage_remote", "Открыть средство доступа к хранилищу");
        this.lang("ru_ru").add("key.occultism.familiar.otherworld_bird", "Переключить эффект от перстня: дрикрыл");
        this.lang("ru_ru").add("key.occultism.familiar.greedy_familiar", "Переключить эффект от перстня: алчный");
        this.lang("ru_ru").add("key.occultism.familiar.bat_familiar", "Переключить эффект от перстня: летучая мышь");
        this.lang("ru_ru").add("key.occultism.familiar.deer_familiar", "Переключить эффект от перстня: олень");
        this.lang("ru_ru").add("key.occultism.familiar.cthulhu_familiar", "Переключить эффект от перстня: Ктулху");
        this.lang("ru_ru").add("key.occultism.familiar.devil_familiar", "Переключить эффект от перстня: дьявол");
        this.lang("ru_ru").add("key.occultism.familiar.dragon_familiar", "Переключить эффект от перстня: дракон");
        this.lang("ru_ru").add("key.occultism.familiar.blacksmith_familiar", "Переключить эффект от перстня: кузнец");
        this.lang("ru_ru").add("key.occultism.familiar.guardian_familiar", "Переключить эффект от перстня: страж");
        this.lang("ru_ru").add("key.occultism.familiar.headless_familiar", "Переключить эффект от перстня: безголовый человек на крысе");
        this.lang("ru_ru").add("key.occultism.familiar.chimera_familiar", "Переключить эффект от перстня: химера");
        this.lang("ru_ru").add("key.occultism.familiar.goat_familiar", "Переключить эффект от перстня: коза");
        this.lang("ru_ru").add("key.occultism.familiar.shub_niggurath_familiar", "Переключить эффект от перстня: Шаб-Ниггурат");
        this.lang("ru_ru").add("key.occultism.familiar.beholder_familiar", "Переключить эффект от перстня: созерцатель");
        this.lang("ru_ru").add("key.occultism.familiar.fairy_familiar", "Переключить эффект от перстня: фея");
        this.lang("ru_ru").add("key.occultism.familiar.mummy_familiar", "Переключить эффект от перстня: мумия");
        this.lang("ru_ru").add("key.occultism.familiar.beaver_familiar", "Переключить эффект от перстня: бобёр");
    }

    private void addJeiTranslations() {
        this.lang("ru_ru").add("occultism.jei.spirit_fire", "Огонь духов");
		this.lang("ru_ru").add("occultism.jei.spirit_trader", "Дух торговец");
        this.lang("ru_ru").add("occultism.jei.spirit_trader.chance", "Шанс: %s%%");
        this.lang("ru_ru").add("occultism.jei.crushing", "Дух-дробильщик");
		this.lang("ru_ru").add("occultism.jei.crystallize", "Дух-кристаллизовщик");
        this.lang("ru_ru").add("occultism.jei.miner", "Пространственная шахта");
        this.lang("ru_ru").add("occultism.jei.miner.chance", "Коэффиц.: %d");
        this.lang("ru_ru").add("occultism.jei.ritual", "Оккультный ритуал");
        this.lang("ru_ru").add("occultism.jei.pentacle", "Пентакль");

        this.lang("ru_ru").add(TranslationKeys.JEI_CRUSHING_RECIPE_MIN_TIER, "Минимальный уровень дробильщика: %d");
        this.lang("ru_ru").add(TranslationKeys.JEI_CRUSHING_RECIPE_MAX_TIER, "Максимальный уровень дробильщика: %d");
        this.lang("ru_ru").add("jei.occultism.crushing.multiply_output", "Увеличение продукции зависит от уровня дробильщика.");
        this.lang("ru_ru").add(TranslationKeys.JEI_CRYSTALLIZE_RECIPE_MIN_TIER, "Минимальный уровень кристаллизовщика: %d");
        this.lang("ru_ru").add(TranslationKeys.JEI_CRYSTALLIZE_RECIPE_MAX_TIER, "Максимальный уровень кристаллизовщика: %d");
        this.lang("ru_ru").add("jei.occultism.crystallize.multiply_output", "Увеличение продукции зависит от уровня кристаллизатора.");
        this.lang("ru_ru").add("jei.occultism.ingredient.tallow.description", "Чтобы получить [](item://occultism:tallow), убивайте крупных животных, таких как \u00a72свиньи\u00a7r, \u00a72коровы\u00a7r или \u00a72овцы\u00a7r, \u00a72лошади\u00a7r и \u00a72ламы\u00a7r, используя [](item://occultism:butcher_knife).");
        this.lang("ru_ru").add("jei.occultism.ingredient.otherstone.description", "Преимущественно встречается в потусторонних рощах. Виден только при активном эффекте «\u00a76Третий глаз\u00a7r». Чтобы узнать больше посмотрите в \u00a76лексиконе\u00a7r \u00a76духов\u00a7r.");
        this.lang("ru_ru").add("jei.occultism.ingredient.otherworld_log.description", "Преимущественно встречается в потусторонних рощах. Виден только при активном эффекте «\u00a76Третий глаз\u00a7r». Чтобы узнать больше посмотрите в \u00a76лексиконе\u00a7r \u00a76духов\u00a7r.");
        this.lang("ru_ru").add("jei.occultism.ingredient.otherworld_sapling.description", "Можно получить от торговца потусторонними саженцами. Можно увидеть и собрать без «\u00a76Третьего глаза\u00a7r». Чтобы узнать больше о том, как призвать торговца смотрите в «\u00a76лексиконе духов\u00a7r».");
        this.lang("ru_ru").add("jei.occultism.ingredient.otherworld_sapling_natural.description", "Преимущественно встречается в потусторонних рощах. Виден только при активном эффекте «\u00a76Третий глаз\u00a7r». Чтобы узнать больше посмотрите в в \u00a76лексиконе\u00a7r \u00a76духов\u00a7r.");
        this.lang("ru_ru").add("jei.occultism.ingredient.otherworld_leaves.description", "Преимущественно встречаются в потусторонних рощах. Видны только при активном эффекте «\u00a76Третий глаз\u00a7r». Чтобы узнать больше посмотрите в \u00a76лексиконе\u00a7r \u00a76духов\u00a7r.");
        this.lang("ru_ru").add("jei.occultism.ingredient.iesnium_ore.description", "Встречается в Незере. Видна только при активном эффекте «\u00a76Третий глаз\u00a7r». Чтобы узнать больше посмотрите в \u00a76лексиконе\u00a7r \u00a76духов\u00a7r.");
        this.lang("ru_ru").add("jei.occultism.ingredient.spirit_fire.description", "Бросьте \u00a76Плод видения демона\u00a7r на землю и подожгите. Чтобы узнать больше посмотрите в \u00a76лексиконе\u00a7r \u00a76духов\u00a7r.");
        this.lang("ru_ru").add("jei.occultism.ingredient.datura.description", "Используется для исцеления всех духов и фамильяров, которые призваны благодаря ритуалам из Occultism. Нажмите ПКМ на существе, чтобы исцелить его на одно сердце.");

        this.lang("ru_ru").add("jei.occultism.ingredient.spawn_egg.familiar_goat.description", "Фамильяру-козу можно получить, накормив фамильяру-химеру золотым яблоком. Чтобы узнать больше посмотрите в \u00a76лексиконе\u00a7r \u00a76духов\u00a7r.");
        this.lang("ru_ru").add("jei.occultism.ingredient.spawn_egg.familiar_shub_niggurath.description", "Шаб-Ниггурата можно получить, приводя фамильяра козу в лесной биом, и нажатием на неё сперва чёрным красителем, а затем кремнем и оком эндера. Чтобы узнать больше посмотрите в \u00a76лексиконе\u00a7r \u00a76духов\u00a7r.");

        this.lang("ru_ru").add("jei.occultism.sacrifice", "Жертва: %s");
        this.lang("ru_ru").add("jei.occultism.summon", "Призыв: %s");
        this.lang("ru_ru").add("jei.occultism.job", "Должность: %s");
        this.lang("ru_ru").add("jei.occultism.item_to_use", "Предмет использования:");
        this.lang("ru_ru").add("jei.occultism.error.missing_id", "Не удалось определить рецепт.");
        this.lang("ru_ru").add("jei.occultism.error.invalid_type", "Недопустимый тип рецепта.");
        this.lang("ru_ru").add("jei.occultism.error.recipe_too_large", "Рецепт больше 3х3.");
        this.lang("ru_ru").add("jei.occultism.error.recipe_items_missing", "Отсутствующие предметы будут игнорироваться.");
        this.lang("ru_ru").add("jei.occultism.error.recipe_no_items", "Совместимые предметы для рецепта не найдены.");
        this.lang("ru_ru").add("jei.occultism.error.recipe_move_items", "Переместите предметы");
        this.lang("ru_ru").add("jei.occultism.error.pentacle_not_loaded", "Пентакль не может быть загружен.");
        this.lang("ru_ru").add("item.occultism.jei_dummy.require_sacrifice", "Требуется жертва!");
        this.lang("ru_ru").add("item.occultism.jei_dummy.require_sacrifice.tooltip", "Для запуска ритуала требуется жертва. Подробности смотрите в лексиконе духов.");
        this.lang("ru_ru").add("item.occultism.jei_dummy.require_item_use", "Требуется использовать предмет!");
        this.lang("ru_ru").add("item.occultism.jei_dummy.require_item_use.tooltip", "Для запуска ритуала необходимо использовать специальный предмет. Подробности смотрите в лексиконе духов.");
        this.lang("ru_ru").add("item.occultism.jei_dummy.none", "Результат ритуала без предмета.");
        this.lang("ru_ru").add("item.occultism.jei_dummy.none.tooltip", "Этот ритуал не создаёт предметы.");
    }

    private void addFamiliarSettingsMessages() {
		this.lang("ru_ru").add("message.occultism.familiar.upgraded", "[%s] получил обновление!");
        this.lang("ru_ru").add("message.occultism.familiar.otherworld_bird.enabled", "Эффект от перстня — дрикрыл: ВКЛ.");
        this.lang("ru_ru").add("message.occultism.familiar.otherworld_bird.disabled", "Эффект от перстня — дрикрыл: ВЫКЛ.");
        this.lang("ru_ru").add("message.occultism.familiar.greedy_familiar.enabled", "Эффект от перстня — алчный: ВКЛ.");
        this.lang("ru_ru").add("message.occultism.familiar.greedy_familiar.disabled", "Эффект от перстня — алчный: ВЫКЛ.");
        this.lang("ru_ru").add("message.occultism.familiar.bat_familiar.enabled", "Эффект от перстня — летучая мышь: ВКЛ.");
        this.lang("ru_ru").add("message.occultism.familiar.bat_familiar.disabled", "Эффект от перстня — летучая мышь: ВЫКЛ.");
        this.lang("ru_ru").add("message.occultism.familiar.deer_familiar.enabled", "Эффект от перстня — олень: ВКЛ.");
        this.lang("ru_ru").add("message.occultism.familiar.deer_familiar.disabled", "Эффект от перстня — олень: ВЫКЛ.");
        this.lang("ru_ru").add("message.occultism.familiar.cthulhu_familiar.enabled", "Эффект от перстня — Ктулху: ВКЛ.");
        this.lang("ru_ru").add("message.occultism.familiar.cthulhu_familiar.disabled", "Эффект от перстня — Ктулху: ВЫКЛ.");
        this.lang("ru_ru").add("message.occultism.familiar.devil_familiar.enabled", "Эффект от перстня — дьявол: ВКЛ.");
        this.lang("ru_ru").add("message.occultism.familiar.devil_familiar.disabled", "Эффект от перстня — дьявол: ВЫКЛ.");
        this.lang("ru_ru").add("message.occultism.familiar.dragon_familiar.enabled", "Эффект от перстня — дракон: ВКЛ.");
        this.lang("ru_ru").add("message.occultism.familiar.dragon_familiar.disabled", "Эффект от перстня — дракон: ВЫКЛ.");
        this.lang("ru_ru").add("message.occultism.familiar.blacksmith_familiar.enabled", "Эффект от перстня — кузнец: ВКЛ.");
        this.lang("ru_ru").add("message.occultism.familiar.blacksmith_familiar.disabled", "Эффект от перстня — кузнец: ВЫКЛ.");
        this.lang("ru_ru").add("message.occultism.familiar.guardian_familiar.enabled", "Эффект от перстня — страж: ВКЛ.");
        this.lang("ru_ru").add("message.occultism.familiar.guardian_familiar.disabled", "Эффект от перстня — страж: ВЫКЛ.");
        this.lang("ru_ru").add("message.occultism.familiar.headless_familiar.enabled", "Эффект от перстня — безголовый человек на крысе: ВКЛ.");
        this.lang("ru_ru").add("message.occultism.familiar.headless_familiar.disabled", "Эффект от перстня — безголовый человек на крысе: ВЫКЛ.");
        this.lang("ru_ru").add("message.occultism.familiar.chimera_familiar.enabled", "Эффект от перстня — химера: ВКЛ.");
        this.lang("ru_ru").add("message.occultism.familiar.chimera_familiar.disabled", "Эффект от перстня — химера: ВЫКЛ.");
        this.lang("ru_ru").add("message.occultism.familiar.shub_niggurath_familiar.enabled", "Эффект от перстня — Шаб-Ниггурат: ВКЛ.");
        this.lang("ru_ru").add("message.occultism.familiar.shub_niggurath_familiar.disabled", "Эффект от перстня — Шаб-Ниггурат: ВЫКЛ.");
        this.lang("ru_ru").add("message.occultism.familiar.beholder_familiar.enabled", "Эффект от перстня — созерцатель: ВКЛ.");
        this.lang("ru_ru").add("message.occultism.familiar.beholder_familiar.disabled", "Эффект от перстня — созерцатель: ВЫКЛ.");
        this.lang("ru_ru").add("message.occultism.familiar.fairy_familiar.enabled", "Эффект от перстня — фея: ВКЛ.");
        this.lang("ru_ru").add("message.occultism.familiar.fairy_familiar.disabled", "Эффект от перстня — фея: ВЫКЛ.");
        this.lang("ru_ru").add("message.occultism.familiar.mummy_familiar.enabled", "Эффект от перстня — мумия: ВКЛ.");
        this.lang("ru_ru").add("message.occultism.familiar.mummy_familiar.disabled", "Эффект от перстня — мумия: ВЫКЛ.");
        this.lang("ru_ru").add("message.occultism.familiar.beaver_familiar.enabled", "Эффект от перстня — бобёр: ВКЛ.");
        this.lang("ru_ru").add("message.occultism.familiar.beaver_familiar.disabled", "Эффект от перстня — бобёр: ВЫКЛ.");
    }

    private void addPentacles() {
        this.lang("ru_ru").addPentacle("otherworld_bird", "Потусторонняя птица");
        this.lang("ru_ru").addPentacle("summon_foliot", "Круг Авиара");
        this.lang("ru_ru").addPentacle("summon_djinni", "Зов Офикса");
        this.lang("ru_ru").addPentacle("summon_unbound_afrit", "Призыв свободного Кандара");
        this.lang("ru_ru").addPentacle("summon_afrit", "Призыв Абраса");
        this.lang("ru_ru").addPentacle("summon_unbound_marid", "Привлечение Тибайрана");
        this.lang("ru_ru").addPentacle("summon_marid", "Поощряемое привлечение Фатмы");
        this.lang("ru_ru").addPentacle("possess_foliot", "Приманка Гидирина");
        this.lang("ru_ru").addPentacle("possess_djinni", "Порабощение Айгана");
        this.lang("ru_ru").addPentacle("possess_unbound_afrit", "Созыв свободного Одуса");
        this.lang("ru_ru").addPentacle("possess_afrit", "Созыв Покуса");
        this.lang("ru_ru").addPentacle("possess_marid", "Присяга Ксеоврента");
        this.lang("ru_ru").addPentacle("craft_foliot", "Вынуждение призрачного Изива");
        this.lang("ru_ru").addPentacle("craft_djinni", "Заключение высшего Стригора");
        this.lang("ru_ru").addPentacle("craft_afrit", "Пожизненное заключение Севиры");
        this.lang("ru_ru").addPentacle("craft_marid", "Перевёрнутая башня Афиксеса");
        this.lang("ru_ru").addPentacle("resurrect_spirit", "Простой круг Сасджейса");
        this.lang("ru_ru").addPentacle("contact_wild_spirit", "Призыв свободного Осорина");
        this.lang("ru_ru").addPentacle("contact_eldritch_spirit", "Связь с Роназом");
    }

    private void addPentacle(String id, String name) {
        this.add(Util.makeDescriptionId("multiblock", ResourceLocation.fromNamespaceAndPath(Occultism.MODID, id)), name);
    }

    private void addRitualDummies() {
        //Custom dummy
        this.lang("ru_ru").add(OccultismItems.RITUAL_DUMMY_CUSTOM_SUMMON.get(), "Пользовательский макет ритуала");
        this.lang("ru_ru").addTooltip(OccultismItems.RITUAL_DUMMY_CUSTOM_SUMMON.get(), "Используется для сборок в качестве альтернативы для пользовательских ритуалов, не имеющих собственного предмета для ритуала.");
        this.lang("ru_ru").add(OccultismItems.RITUAL_DUMMY_CUSTOM_POSSESS.get(), "Пользовательский макет ритуала");
        this.lang("ru_ru").addTooltip(OccultismItems.RITUAL_DUMMY_CUSTOM_POSSESS.get(), "Используется для сборок в качестве альтернативы для пользовательских ритуалов, не имеющих собственного предмета для ритуала.");
        this.lang("ru_ru").add(OccultismItems.RITUAL_DUMMY_CUSTOM_CRAFT.get(), "Пользовательский макет ритуала");
        this.lang("ru_ru").addTooltip(OccultismItems.RITUAL_DUMMY_CUSTOM_CRAFT.get(), "Используется для сборок в качестве альтернативы для пользовательских ритуалов, не имеющих собственного предмета для ритуала.");
        this.lang("ru_ru").add(OccultismItems.RITUAL_DUMMY_CUSTOM_MISC.get(), "Пользовательский макет ритуала");
        this.lang("ru_ru").addTooltip(OccultismItems.RITUAL_DUMMY_CUSTOM_MISC.get(), "Используется для сборок в качестве альтернативы для пользовательских ритуалов, не имеющих собственного предмета для ритуала.");

        //SUMMON
			//Crusher
        this.lang("ru_ru").autoDummyFactory(OccultismItems.RITUAL_DUMMY_SUMMON_FOLIOT_CRUSHER, "призыв Фолиота-дробильщика", "Фолиот", "Дробильщик — это дух, который призывается для размельчения руды в пыль, эффективно удваивая металлопродукцию.\n" + ChatFormatting.GRAY + ChatFormatting.ITALIC + "Примечание: некоторые рецепты могут потребовать высокий или низкий уровень дробильщиков.");
        this.lang("ru_ru").autoDummyFactory(OccultismItems.RITUAL_DUMMY_SUMMON_DJINNI_CRUSHER, "призыв Джинна-дробильщика", "Джинн", "Дробильщик — это дух, который призывается для измельчения руды в пыль, эффективно (гораздо), удваивая металлопродукцию.\n" + ChatFormatting.GRAY + ChatFormatting.ITALIC + "Примечание: некоторые рецепты могут потребовать высокий или низкий уровень дробильщиков.");
        this.lang("ru_ru").autoDummyFactory(OccultismItems.RITUAL_DUMMY_SUMMON_AFRIT_CRUSHER, "призыв Африта-дробильщика", "Африт", "Дробильщик — это дух, который призывается для измельчения руды в пыль, эффективно (гораздо), удваивая металлопродукцию.\n" + ChatFormatting.GRAY + ChatFormatting.ITALIC + "Примечание: некоторые рецепты могут потребовать высокий или низкий уровень дробильщиков.");
        this.lang("ru_ru").autoDummyFactory(OccultismItems.RITUAL_DUMMY_SUMMON_MARID_CRUSHER, "призыв Марида-дробильщика", "Марид", "Дробильщик — это дух, который призывается для измельчения руды в пыль, эффективно (гораздо), удваивая металлопродукцию.\n" + ChatFormatting.GRAY + ChatFormatting.ITALIC + "Примечание: некоторые рецепты могут потребовать высокий или низкий уровень дробильщиков.");
			//Smelter
        this.lang("ru_ru").autoDummyFactory(OccultismItems.RITUAL_DUMMY_SUMMON_FOLIOT_SMELTER, "призыв Фолиота-литейщика", "Фолиот", "Литейщик — это дух, который призывается для создания рецептов печи, плавильной печи, коптильни и костра — без использования топлива, к тому же более быстрый в зависимости от духа.");
        this.lang("ru_ru").autoDummyFactory(OccultismItems.RITUAL_DUMMY_SUMMON_DJINNI_SMELTER, "призыв Джинна-литейщика", "Джинн", "Литейщик — это дух, который призывается для создания рецептов печи, плавильной печи, коптильни и костра — без использования топлива, к тому же более быстрый в зависимости от духа.");
        this.lang("ru_ru").autoDummyFactory(OccultismItems.RITUAL_DUMMY_SUMMON_AFRIT_SMELTER, "призыв Африта-литейщика", "Африт", "Литейщик — это дух, который призывается для создания рецептов печи, плавильной печи, коптильни и костра — без использования топлива, к тому же более быстрый в зависимости от духа.");
        this.lang("ru_ru").autoDummyFactory(OccultismItems.RITUAL_DUMMY_SUMMON_MARID_SMELTER, "призыв Марида-литейщика", "Марид", "Литейщик — это дух, который призывается для создания рецептов печи, плавильной печи, коптильни и костра — без использования топлива, к тому же более быстрый в зависимости от духа.");
			//Crystallize
        this.lang("ru_ru").autoDummyFactory(OccultismItems.RITUAL_DUMMY_SUMMON_FOLIOT_CRYSTALLIZER, "призыв Фолиота-кристаллизовщика", "Фолиот", "Кристаллизовщик — это дух, который призывается для превращения пыли от самоцветов обратно в самоцветы. Также может извлекать самоцветы из руд.\n" + ChatFormatting.GRAY + ChatFormatting.ITALIC + "Примечание: некоторые рецепты могут потребовать высокий или низкий уровень кристаллизовщиков.");
        this.lang("ru_ru").autoDummyFactory(OccultismItems.RITUAL_DUMMY_SUMMON_DJINNI_CRYSTALLIZER, "призыв Джинна-кристаллизовщика", "Джинн", "Кристаллизовщик — это дух, который призывается для превращения пыли от самоцветов обратно в самоцветы. Также может извлекать самоцветы из руд.\n" + ChatFormatting.GRAY + ChatFormatting.ITALIC + "Примечание: некоторые рецепты могут потребовать высокий или низкий уровень кристаллизовщиков.");
        this.lang("ru_ru").autoDummyFactory(OccultismItems.RITUAL_DUMMY_SUMMON_AFRIT_CRYSTALLIZER, "призыв Африта-кристаллизовщика", "Африт", "Кристаллизовщик — это дух, который призывается для превращения пыли от самоцветов обратно в самоцветы. Также может извлекать самоцветы из руд.\n" + ChatFormatting.GRAY + ChatFormatting.ITALIC + "Примечание: некоторые рецепты могут потребовать высокий или низкий уровень кристаллизовщиков.");
        this.lang("ru_ru").autoDummyFactory(OccultismItems.RITUAL_DUMMY_SUMMON_MARID_CRYSTALLIZER, "призыв Марида-кристаллизовщика", "Марид", "Кристаллизовщик — это дух, который призывается для превращения пыли от самоцветов обратно в самоцветы. Также может извлекать самоцветы из руд." + ChatFormatting.GRAY + ChatFormatting.ITALIC + "Примечание: некоторые рецепты могут потребовать высокий или низкий уровень кристаллизовщиков.");
			//Partner
        this.lang("ru_ru").autoDummyFactory(OccultismItems.RITUAL_DUMMY_SUMMON_DEMONIC_WIFE, "призыв демонической жены", "Джинн", "Призывает демоническую жену для поддержки. Жена будет защищать вас, помогать с готовкой и продлевать срок зелья.");
        this.lang("ru_ru").autoDummyFactory(OccultismItems.RITUAL_DUMMY_SUMMON_DEMONIC_HUSBAND, "призыв демонического мужа", "Джинн", "Призывает демонического мужа для поддержки. Муж будет защищать вас, помогать с готовкой и продлевать срок зелья.");
			//One tier worker
        this.lang("ru_ru").autoDummyFactory(OccultismItems.RITUAL_DUMMY_SUMMON_FOLIOT_LUMBERJACK, "призыв Фолиота-дровосека", "Фолиот", "Дровосек будет рубить близлежащие деревья на своём рабочем месте и класть их в указанный сундук.");
		this.lang("ru_ru").autoDummyFactory(OccultismItems.RITUAL_DUMMY_SUMMON_FOLIOT_FARMER, "призыв Фолиота-фермера", "Фолиот", "Фермер будет собирать и пересаживать растения на своём рабочем месте. В случае установки места хранения, он будет собирать выпавшие предметы в указанный сундук.");
        this.lang("ru_ru").autoDummyFactory(OccultismItems.RITUAL_DUMMY_SUMMON_FOLIOT_OTHERSTONE_TRADER, "призыв торговца потусторонним камнем", "Фолиот", "Торговец потусторонним камнем обменивает обычный камень на потусторонний вариант.");
		this.lang("ru_ru").autoDummyFactory(OccultismItems.RITUAL_DUMMY_SUMMON_FOLIOT_OTHERROCK_TRADER, "призыв торговца потусторонней породой", "Фолиот", "Торговец потусторонней породой обменивает обычный камень на потустороннюю породу.");
        this.lang("ru_ru").autoDummyFactory(OccultismItems.RITUAL_DUMMY_SUMMON_FOLIOT_SAPLING_TRADER, "призыв торговца потусторонними саженцами", "Фолиот", "Торговец потусторонними саженцами обменивает природные потусторонние саженцы на стабильные, собираемые без «Третьего глаза».");
        this.lang("ru_ru").autoDummyFactory(OccultismItems.RITUAL_DUMMY_SUMMON_FOLIOT_TRANSPORT_ITEMS, "призыв Фолиота-транспортировщика", "Фолиот", "Транспортировщик будет перемещать все предметы из одного инвентаря в другой (к которому получает доступ), включая устройства.");
        this.lang("ru_ru").autoDummyFactory(OccultismItems.RITUAL_DUMMY_SUMMON_FOLIOT_CLEANER, "призыв Фолиота-уборщика", "Фолиот", "Уборщик будет подбирать выпавшие предметы и класть их в указанный инвентарь.");
        this.lang("ru_ru").autoDummyFactory(OccultismItems.RITUAL_DUMMY_SUMMON_DJINNI_MANAGE_MACHINE, "призыв Джинна-станочника", "Джинн", "Станочник автоматически перемещает предметы между системой пространственного хранилища и присоединёнными инвентарями, а также устройствами.");
		this.lang("ru_ru").autoDummyFactory(OccultismItems.RITUAL_DUMMY_SUMMON_DJINNI_GAMBLER, "призыв джинна-спекулянта", "Джинн", "Спекулянт обменивает самоцветы и кусочки на другие (несколько штук); торговец с чуточку произвольности.");
        this.lang("ru_ru").autoDummyFactory(OccultismItems.RITUAL_DUMMY_SUMMON_WONDERING_TRADER, "призыв странствующего торговца", "Джинн", "Призывает странствующего торговца, предлагающего специальные оккультные предметы, когда вы видите «Иной мир».");
        this.lang("ru_ru").autoDummyFactory(OccultismItems.RITUAL_DUMMY_SUMMON_DJINNI_DAY_TIME, "вызов рассвета", "Джинн", "Призывает Джинна, устанавливающего время на полдень.");
        this.lang("ru_ru").autoDummyFactory(OccultismItems.RITUAL_DUMMY_SUMMON_DJINNI_NIGHT_TIME, "вызов сумерек", "Джинн", "Призывает Джинна, устанавливающего время на сумерки.");
        this.lang("ru_ru").autoDummyFactory(OccultismItems.RITUAL_DUMMY_SUMMON_DJINNI_CLEAR_WEATHER, "вызов безоблачного неба", "Джинн", "Призывает Джинна, устраняющего погоду.");
        this.lang("ru_ru").autoDummyFactory(OccultismItems.RITUAL_DUMMY_SUMMON_AFRIT_RAIN_WEATHER, "вызов дождя", "Африт", "Призывает Африта, вызывающего дождь.");
        this.lang("ru_ru").autoDummyFactory(OccultismItems.RITUAL_DUMMY_SUMMON_AFRIT_THUNDER_WEATHER, "вызов грозы", "Африт", "Призывает Африта, вызывающего грозу.");
			//Unbound
        this.lang("ru_ru").autoDummyFactory(OccultismItems.RITUAL_DUMMY_SUMMON_UNBOUND_AFRIT, "призыв незаключённого Африта", "Африт (несвязанный)", "Призывает незаключённого Африта, которого можно убить для получения сущности Африта.");
        this.lang("ru_ru").autoDummyFactory(OccultismItems.RITUAL_DUMMY_SUMMON_UNBOUND_MARID, "призыв незаключённого Марида", "Марид (несвязанный)", "Призывает незаключённого Марида, которого можно убить для получения сущности Марида.");
        //POSSESS
			//Familiar
        this.lang("ru_ru").autoDummyFactory(OccultismItems.RITUAL_DUMMY_FAMILIAR_BEAVER, "призыв фамильяра-бобра", "Фолиот", "Фамильяр-бобёр даёт повышенную скорость рубки своему хозяину, и добывает близлежащие деревья, когда они вырастут из саженца.");
        this.lang("ru_ru").autoDummyFactory(OccultismItems.RITUAL_DUMMY_FAMILIAR_BLACKSMITH, "призыв фамильяра-кузнеца", "Фолиот", "Фамильяры-кузнецы берут камни, добытые хозяином и используют их для починки снаряжения.");
        this.lang("ru_ru").autoDummyFactory(OccultismItems.RITUAL_DUMMY_FAMILIAR_DEER, "призыв фамильяра-оленя", "Фолиот", "Фамильяры-олени дают «Прыгучесть» своему хозяину.");
        this.lang("ru_ru").autoDummyFactory(OccultismItems.RITUAL_DUMMY_FAMILIAR_GREEDY, "призыв алчного фамильяра", "Фолиот", "Алчные фамильяры подбирают предметы хозяину. Находясь в заключении перстня для фамильяра, они увеличивают дальность сбора предметов (словно «Магнит предметов» из мода Cyclic).");
        this.lang("ru_ru").autoDummyFactory(OccultismItems.RITUAL_DUMMY_FAMILIAR_PARROT, "призыв фамильяра-попугая", "Фолиот", "Фамильяр-попугай ведёт себя точь-в-точь как прирученные попугаи.");
        this.lang("ru_ru").autoDummyFactory(OccultismItems.RITUAL_DUMMY_POSSESS_UNBOUND_PARROT, "Завладение несвязанным попугаем", "Фолиот", "Завладевает попугаем, которого можно приручить кем угодно, не только призывателем.");
        this.lang("ru_ru").autoDummyFactory(OccultismItems.RITUAL_DUMMY_FAMILIAR_BAT, "призыв фамильяра-летучая мышь", "Джинн", "Фамильяры-летучие мыши дают «Ночное зрение» хозяину.");
        this.lang("ru_ru").autoDummyFactory(OccultismItems.RITUAL_DUMMY_FAMILIAR_BEHOLDER, "призыв фамильяра-созерцателя", "Джинн", "Фамильяры-созерцатели подсвечивают близлежащих существ эффектом свечения и стреляют лазерными лучами во врагов.");
        this.lang("ru_ru").autoDummyFactory(OccultismItems.RITUAL_DUMMY_FAMILIAR_CHIMERA, "призыв фамильяра-химеры", "Джинн", "Фамильяров-химер можно накормить до полного роста для получения скорости атаки и урона. Как только вырастут, игроки могут их оседлать.");
        this.lang("ru_ru").autoDummyFactory(OccultismItems.RITUAL_DUMMY_FAMILIAR_CTHULHU, "призыв фамильяра-Ктулху", "Джинн", "Фамильяры-Ктулхи дают «Водное дыхание» своему хозяину.");
        this.lang("ru_ru").autoDummyFactory(OccultismItems.RITUAL_DUMMY_FAMILIAR_DEVIL, "призыв фамильяра-дьявола", "Джинн", "Фамильяры-дьяволы дают «Огнестойкость» своему хозяину.");
        this.lang("ru_ru").autoDummyFactory(OccultismItems.RITUAL_DUMMY_FAMILIAR_DRAGON, "призыв фамильяра-дракона", "Джинн", "Фамильяры-драконы дают повышенное получение опыта своему хозяину.");
        this.lang("ru_ru").autoDummyFactory(OccultismItems.RITUAL_DUMMY_FAMILIAR_FAIRY, "призыв фамильяра-феи", "Джинн", "Фамильяр-фея оберегает от смерти других фамильяров, истощает жизненную силу своих врагов и исцеляет своего хозяина, а также и его фамильяров.");
        this.lang("ru_ru").autoDummyFactory(OccultismItems.RITUAL_DUMMY_FAMILIAR_HEADLESS, "призыв фамильяра-безголового человека на крысе", "Джинн", "Фамильяры-безголовые люди на крысе увеличивают скорость атаки против врагов хозяина, чьи головы они украли.");
        this.lang("ru_ru").autoDummyFactory(OccultismItems.RITUAL_DUMMY_FAMILIAR_MUMMY, "призыв фамильяра-мумии", "Джинн", "Фамильяр-мумия является мастером боевых искусств, сражающаяся, чтобы защитить своего хозяина.");
        this.lang("ru_ru").autoDummyFactory(OccultismItems.RITUAL_DUMMY_FAMILIAR_OTHERWORLD_BIRD, "призыв фамильяра-дрикрыла", "Джинн", "Дрикрылы дают владельцу ограниченные возможности полёта, будучи рядом.");
        this.lang("ru_ru").autoDummyFactory(OccultismItems.RITUAL_DUMMY_POSSESS_UNBOUND_OTHERWORLD_BIRD, "Завладение несвязанным дрикрылом", "Джинн", "Овладевает фамильяром-дрикрылом, которого можно приручить кем угодно, не только призывателем.");
        this.lang("ru_ru").autoDummyFactory(OccultismItems.RITUAL_DUMMY_FAMILIAR_GUARDIAN, "призыв фамильяра-стража", "Африт", "Фамильяры-стражи оберегают хозяина от жестокой кончины.");
        this.lang("ru_ru").autoDummyFactory(OccultismItems.RITUAL_DUMMY_POSSESS_IESNIUM_GOLEM, "призыв айзниевого голема", "Марид", "Призывает сильного и неуязвимого айзниевого голема для защиты территории.");
			//Possessed
        this.lang("ru_ru").autoDummyFactory(OccultismItems.RITUAL_DUMMY_POSSESS_ENDERMITE, "призыв одержимого эндермита", "Фолиот", "Одержимый эндермит сбрасывает эндерняк.");
        this.lang("ru_ru").autoDummyFactory(OccultismItems.RITUAL_DUMMY_POSSESS_PHANTOM, "призыв одержимого фантома", "Фолиот", "При убийстве одержимый фантом всегда будет сбрасывать не более одной мембраны фантома, и его легко поймать в ловушку.");
        this.lang("ru_ru").autoDummyFactory(OccultismItems.RITUAL_DUMMY_POSSESS_SKELETON, "призыв одержимого скелета", "Фолиот", "При убийстве одержимый скелет становится устойчивым к дневному свету и всегда сбрасывает не более одного черепа скелета.");
        this.lang("ru_ru").autoDummyFactory(OccultismItems.RITUAL_DUMMY_POSSESS_WITCH, "призыв одержимой ведьмы", "Фолиот", "Одержимая ведьма будет сбрасывать особую наполненную бутылочку.");
        this.lang("ru_ru").autoDummyFactory(OccultismItems.RITUAL_DUMMY_POSSESS_ENDERMAN, "призыв одержимого эндермена", "Джинн", "При убийстве одержимый эндермен всегда будет сбрасывать не более одного эндер-жемчуга.");
        this.lang("ru_ru").autoDummyFactory(OccultismItems.RITUAL_DUMMY_POSSESS_BEE, "призыв одержимой пчелы", "Джинн", "Одержимая пчела будет сбрасывать проклятый мёд.");
        this.lang("ru_ru").autoDummyFactory(OccultismItems.RITUAL_DUMMY_POSSESS_GHAST, "призыв одержимого гаста", "Джинн", "При убийстве одержимый гаст всегда будет сбрасывать не более одной слезы гаста.");
        this.lang("ru_ru").autoDummyFactory(OccultismItems.RITUAL_DUMMY_POSSESS_WEAK_SHULKER, "призыв одержимого слабого шалкера", "Джинн", "При убийстве одержимый слабый шалкер будет сбрасывать не более одного плода хоруса, а также сбрасывать панцирь шалкера.");
		this.lang("ru_ru").autoDummyFactory(OccultismItems.RITUAL_DUMMY_POSSESS_BLAZE, "призыв одержимого всполоха", "Джинн", "Одержимый всполох будет сбрасывать не более двух огненных стержней и ряд предметов, связанных с Незером, включая также блоки, растения и, весьма редко древние обломки.");
        this.lang("ru_ru").autoDummyFactory(OccultismItems.RITUAL_DUMMY_POSSESS_ZOMBIE_PIGLIN, "призыв одержимого зомбифицированного пиглина", "Африт (несвязанный)", "Одержимый зомбифицированный пиглин будет сбрасывать демоническое мясо.");
		this.lang("ru_ru").autoDummyFactory(OccultismItems.RITUAL_DUMMY_POSSESS_GUARDIAN, "призыв одержимого стража", "Африт (несвязанный)", "Одержимый страж будет сбрасывать вещь из кораллового рифа.");
        this.lang("ru_ru").autoDummyFactory(OccultismItems.RITUAL_DUMMY_POSSESS_WARDEN, "призыв одержимого хранителя", "Африт", "При убийстве одержимый хранитель всегда будет сбрасывать не более шести осколков эха, а также сбрасывать и другие древние предметы: кузнечные шаблоны и пластинки.");
        this.lang("ru_ru").autoDummyFactory(OccultismItems.RITUAL_DUMMY_POSSESS_ELDER_GUARDIAN, "призыв одержимого древнего стража", "Африт", "При убийстве одержимый древний страж будет сбрасывать не более одной раковины наутилуса, а также сбрасывать сердце моря и обычную добычу.");
        this.lang("ru_ru").autoDummyFactory(OccultismItems.RITUAL_DUMMY_POSSESS_HOGLIN, "призыв одержимого хоглина", "Африт", "При убийстве  одержимого хоглина есть шанс получить незеритового улучшения.");
        this.lang("ru_ru").autoDummyFactory(OccultismItems.RITUAL_DUMMY_POSSESS_SHULKER, "призыв одержимого шалкера", "Африт", "При убийстве одержимый шалкер всегда будет сбрасывать не более одного панциря шалкера.");
        this.lang("ru_ru").autoDummyFactory(OccultismItems.RITUAL_DUMMY_POSSESS_GOAT, "призыв козла милосердия", "Марид", "Козёл милосердия будет сбрасывать сущность бессердечия.");
			//Random
        this.lang("ru_ru").autoDummyFactory(OccultismItems.RITUAL_DUMMY_POSSESS_RANDOM_ANIMAL_COMMON, "призыв случайного обычного животного", "Фолиот", "Призывает случайное пассивное обычное животное. (Все варианты: курица, корова, свинья, овца, спрут и волк).");
        this.lang("ru_ru").autoDummyFactory(OccultismItems.RITUAL_DUMMY_POSSESS_RANDOM_ANIMAL_WATER, "призыв случайного водного животного", "Фолиот", "Призывает случайное пассивное водное животное. (Все варианты: аксолотль, лягушка, дельфин, треска, лосось, тропическая рыба, иглобрюх, спрут, светящийся спрут, головастик, черепаха и снежный голем).");
        this.lang("ru_ru").autoDummyFactory(OccultismItems.RITUAL_DUMMY_POSSESS_RANDOM_ANIMAL_SMALL, "призыв случайного малого животного", "Фолиот", "Призывает случайное пассивное малое животное. (Все варианты: тихоня, летучая мышь, пчела, попугай, кошка, оцелот, лиса и кролик).");
        this.lang("ru_ru").autoDummyFactory(OccultismItems.RITUAL_DUMMY_POSSESS_RANDOM_ANIMAL_SPECIAL, "призыв случайного специального животного", "Джинн", "Призывает случайное пассивное специальное животное. (Все варианты: броненосец, муушрум, панда, белый медведь, коза, железный голем и нюхач).");
        this.lang("ru_ru").autoDummyFactory(OccultismItems.RITUAL_DUMMY_POSSESS_RANDOM_ANIMAL_RIDEABLE, "призыв случайного ездового животного", "Джинн", "Призывает случайное пассивное ездовое животное. (Все варианты: свинья, верблюд, осёл, лошадь, лошадь-скелет, зомби-лошадь, лама, лама торговца, мул и лавомерка).");
        this.lang("ru_ru").autoDummyFactory(OccultismItems.RITUAL_DUMMY_POSSESS_VILLAGER, "призыв крестьянина", "Джинн", "Призывает крестьянина или странствующего торговца.");
        //CRAFT
			//Tools
        this.lang("ru_ru").autoDummyFactory(OccultismItems.RITUAL_DUMMY_CRAFT_INFUSED_LENSES, "создание наполненных линз", "Фолиот", "Эти линзы используются для создания очков, которые дают вам способность видеть за пределами физического мира.");
        this.lang("ru_ru").autoDummyFactory(OccultismItems.RITUAL_DUMMY_CRAFT_INFUSED_PICKAXE, "создание наполненной кирки", "Джинн", "Наполняйте кирку, чтобы добывать потусторонние руды.");
        this.lang("ru_ru").autoDummyFactory(OccultismItems.RITUAL_DUMMY_CRAFT_SATCHEL, "создание необычайно большой наплечной сумки", "Фолиот", "Эта наплечная сумка позволяет хранить куча предметов, чем позволяют её физические размеры, что делает её полезным спутником для путешественника.");
        this.lang("ru_ru").autoDummyFactory(OccultismItems.RITUAL_DUMMY_CRAFT_RITUAL_SATCHEL_T1, "создание ритуальной наплечной сумки подмастерья", "Фолиот", "Заключает Фолиота в наплечную сумку для пошаговой постройки пентаклей в пользу призывателя.");
        this.lang("ru_ru").autoDummyFactory(OccultismItems.RITUAL_DUMMY_CRAFT_RITUAL_SATCHEL_T2, "создание ритуальной наплечной сумки ручной работы", "Африт", "Заключает Африта в наплечную сумку для мгновенной постройки пентаклей в пользу призывателя.");
		this.lang("ru_ru").autoDummyFactory(OccultismItems.RITUAL_DUMMY_CRAFT_UPGRADE_RITUAL_SATCHEL, "создание ритуальной наплечной сумки ручной работы", "Африт", "Африт улучшит ритуальную наплечную сумку подмастерья для немедленной постройки пентаклей в пользу призывателя. Этот рецепт сохранит в ней предметы.");
		this.lang("ru_ru").autoDummyFactory(OccultismItems.RITUAL_DUMMY_CRAFT_KNOWLEDGE_TABLET, "создание скрижали знаний", "Фолиот", "Заключает Фолиота в скрижаль для хранения опыта.");
		this.lang("ru_ru").autoDummyFactory(OccultismItems.RITUAL_DUMMY_CRAFT_VITALITY_COMPASS, "создание компаса жизни", "Фолиот", "Создайте компас, который можно связывать с живыми существами, чтобы их обнаруживать.");
        this.lang("ru_ru").autoDummyFactory(OccultismItems.RITUAL_DUMMY_CRAFT_FRAGILE_SOUL_GEM, "создание хрупкого камня души", "Фолиот", "Хрупкий камень души позволяет временно заключать живых существ. Можно использовать лишь раз.");
        this.lang("ru_ru").autoDummyFactory(OccultismItems.RITUAL_DUMMY_CRAFT_SOUL_GEM, "создание камня души", "Джинн", "Камень души позволяет временно хранить живых существ.");
        this.lang("ru_ru").autoDummyFactory(OccultismItems.RITUAL_DUMMY_CRAFT_FAMILIAR_RING, "создание перстня для фамильяра", "Джинн", "Перстень для фамильяра позволяет заключать фамильяров. Перстень будет накладывать эффект фамильяра на владельца.");
        this.lang("ru_ru").autoDummyFactory(OccultismItems.RITUAL_DUMMY_CRAFT_TRUE_SIGHT_STAFF, "создание посоха истинного зрения", "Марид", "Посох истинного зрения позволяет находить, видеть и взаимодействовать с потусторонним миром.");
			//Miners
        this.lang("ru_ru").autoDummyFactory(OccultismItems.RITUAL_DUMMY_CRAFT_DIMENSIONAL_MINESHAFT, "создание пространственной шахты", "Джинн", "Позволяет духам-рудокопам входить в шахтёрское измерение и выносить из неё ресурсы.");
        this.lang("ru_ru").autoDummyFactory(OccultismItems.RITUAL_DUMMY_CRAFT_MINER_FOLIOT_UNSPECIALIZED, "вселение Фолиота-рудокопа", "Фолиот", "Призывайте Фолиота-рудокопа в волшебную лампу.");
        this.lang("ru_ru").autoDummyFactory(OccultismItems.RITUAL_DUMMY_CRAFT_MINER_DJINNI_ORES, "вселение рудного Джинна-рудокопа.", "Джинн", "Призывайте рудного Джинна-рудокопа в волшебную лампу.");
        this.lang("ru_ru").autoDummyFactory(OccultismItems.RITUAL_DUMMY_CRAFT_MINER_AFRIT_DEEPS, "вселение Африта-рудокопа для глубинносланцевой руды", "Африт", "Призывайте Африта-рудокопа для глубинносланцевой руды в волшебную лампу.");
        this.lang("ru_ru").autoDummyFactory(OccultismItems.RITUAL_DUMMY_CRAFT_MINER_MARID_MASTER, "вселение мастера Марида-рудокопа", "Марид", "Призывайте мастера Марида-рудокопа в волшебную лампу.");
			//Storage
        this.lang("ru_ru").autoDummyFactory(OccultismItems.RITUAL_DUMMY_CRAFT_STORAGE_CONTROLLER_BASE, "создание основы актуатора хранилища", "Фолиот", "Основание актуатора хранилища заключает Фолиота в матрице пространственного хранилища, отвечающего за взаимодействие с предметами.");
		this.lang("ru_ru").autoDummyFactory(OccultismItems.RITUAL_DUMMY_CRAFT_STORAGE_CONTROLLER_BASE_DARK, "создание тёмного основания актуатора хранилища", "Фолиот", "Тёмное основание актуатора хранилища заключает Фолиота в матрице пространственного хранилища, отвечающего за взаимодействие с предметами.");
        this.lang("ru_ru").autoDummyFactory(OccultismItems.RITUAL_DUMMY_CRAFT_DIMENSIONAL_MATRIX, "создание пространственной матрицы", "Джинн", "Пространственная матрица — это мостик в малое пространство для хранения предметов.");
        this.lang("ru_ru").autoDummyFactory(OccultismItems.RITUAL_DUMMY_CRAFT_STABILIZER_TIER1, "создание стабилизатора хранилища [1 уровень]", "Фолиот", "Стабилизатор хранилища позволяет хранить больше предметов в средстве доступа пространственного хранилища.");
        this.lang("ru_ru").autoDummyFactory(OccultismItems.RITUAL_DUMMY_CRAFT_STABILIZER_TIER2, "создание стабилизатора хранилища [2 уровень]", "Джинн", "Стабилизатор хранилища позволяет хранить больше предметов в средстве доступа пространственного хранилища.");
        this.lang("ru_ru").autoDummyFactory(OccultismItems.RITUAL_DUMMY_CRAFT_STABILIZER_TIER3, "создание стабилизатора хранилища [3 уровень]", "Африт", "Стабилизатор хранилища позволяет хранить больше предметов в средстве доступа пространственного хранилища.");
        this.lang("ru_ru").autoDummyFactory(OccultismItems.RITUAL_DUMMY_CRAFT_STABILIZER_TIER4, "создание стабилизатора хранилища [4 уровень]", "Марид", "Стабилизатор хранилища позволяет хранить больше предметов в средстве доступа пространственного хранилища.");
		this.lang("ru_ru").autoDummyFactory(OccultismItems.RITUAL_DUMMY_CRAFT_STABILIZER_TIER1_DARK, "создание тёмного стабилизатора хранилища [1 уровень]", "Фолиот", "Тёмный стабилизатор хранилища позволяет хранить больше предметов в средстве доступа пространственного хранилища.");
        this.lang("ru_ru").autoDummyFactory(OccultismItems.RITUAL_DUMMY_CRAFT_STABILIZER_TIER2_DARK, "создание тёмного стабилизатора хранилища [2 уровень]", "Джинн", "Тёмный стабилизатор хранилища позволяет хранить больше предметов в средстве доступа пространственного хранилища.");
        this.lang("ru_ru").autoDummyFactory(OccultismItems.RITUAL_DUMMY_CRAFT_STABILIZER_TIER3_DARK, "создание тёмного стабилизатора хранилища [3 уровень]", "Африт", "Тёмный стабилизатор хранилища позволяет хранить больше предметов в средстве доступа пространственного хранилища.");
        this.lang("ru_ru").autoDummyFactory(OccultismItems.RITUAL_DUMMY_CRAFT_STABILIZER_TIER4_DARK, "создание тёмного стабилизатора хранилища [4 уровень]", "Марид", "Тёмный стабилизатор хранилища позволяет хранить больше предметов в средстве доступа пространственного хранилища.");
        this.lang("ru_ru").autoDummyFactory(OccultismItems.RITUAL_DUMMY_CRAFT_STABLE_WORMHOLE, "создание стабильной червоточины", "Фолиот", "Стабильная червоточина позволяет получить доступ к пространственной матрице из удалённого место назначения.");
		this.lang("ru_ru").autoDummyFactory(OccultismItems.RITUAL_DUMMY_CRAFT_STABLE_WORMHOLE_DARK, "создание тёмной стабильной червоточины", "Фолиот", "Тёмная стабильная червоточина позволяет получить доступ к пространственной матрице из удалённого место назначения.");
        this.lang("ru_ru").autoDummyFactory(OccultismItems.RITUAL_DUMMY_CRAFT_STORAGE_REMOTE, "создание средства доступа к хранилищу", "Джинн", "Средство доступа к хранилищу можно связать с актуатором хранилища для получения удалённого доступа к предметам.");
			//Materials
        this.lang("ru_ru").autoDummyFactory(OccultismItems.RITUAL_DUMMY_CRAFT_RESEARCH_FRAGMENT_DUST, "создание пыли фрагмента исследования", "Фолиот", "Фолиот наполнит опыт в изумрудную пыль.");
        this.lang("ru_ru").autoDummyFactory(OccultismItems.RITUAL_DUMMY_CRAFT_NATURE_PASTE, "создание природной пасты", "Фолиот", "Фолиот создаст природную пасту, смешав ингредиенты.");
        this.lang("ru_ru").autoDummyFactory(OccultismItems.RITUAL_DUMMY_CRAFT_GRAY_PASTE, "создание серой пасты", "Джинн", "Джинн создаст серую пасту, смешав ингредиенты.");
        this.lang("ru_ru").autoDummyFactory(OccultismItems.RITUAL_DUMMY_CRAFT_WITHERITE_DUST, "создание визеритовой пыли", "Африт", "Африт наполнит незеритовую пыль в сущность визера.");
        this.lang("ru_ru").autoDummyFactory(OccultismItems.RITUAL_DUMMY_CRAFT_DRAGONYST_DUST, "создание драконистовой пыли", "Марид", "Марид наполнит сущность Эндер-дракона в аметистовую пыль.");
			//Blocks
        this.lang("ru_ru").autoDummyFactory(OccultismItems.RITUAL_DUMMY_CRAFT_ENTITY_WORMHOLE, "создание червоточины сущностей", "Джинн", "Червоточина сущностей представляет собой базовое устройство для телепортации. Свяжите с компасом, чтобы телепортировать игрока, существ или предметы при соприкосновении с малым порталом.");
		this.lang("ru_ru").autoDummyFactory(OccultismItems.RITUAL_DUMMY_CRAFT_ENTITY_WORMHOLE_DARK, "создание тёмной червоточины сущностей", "Джинн", "Тёмная червоточина сущностей представляет собой базовое устройство для телепортации. Свяжите с компасом, чтобы телепортировать игрока, существ или предметы при соприкосновении с малым порталом.");
        this.lang("ru_ru").autoDummyFactory(OccultismItems.RITUAL_DUMMY_CRAFT_IESNIUM_SACRIFICIAL_BOWL, "создание айзниевой ритуальной чаши", "Африт", "Айзниевая ритуальная чаша выполняет любой ритуал всего лишь за четверть обычного времени. Остальные атрибуты функционируют аналогично золотой ритуальной чаши.");
		this.lang("ru_ru").autoDummyFactory(OccultismItems.RITUAL_DUMMY_CRAFT_DARK_IESNIUM_SACRIFICIAL_BOWL, "создание ритуальной чаши из тёмного айзния", "Африт", "Ритуальная чаша из тёмного айзния выполняет любой ритуал всего лишь за четверть основного времени. Остальные атрибуты функционируют аналогично ритуальной чаши из тёмного золота.");
        this.lang("ru_ru").autoDummyFactory(OccultismItems.RITUAL_DUMMY_CRAFT_IESNIUM_ANVIL, "создание айзниевой наковальни", "Марид", "Айзниевая наковальня является достижением по сравнению с обычной наковальней. Все её преимущества смотрите в лексиконе духов.");
			//Repair
        this.lang("ru_ru").autoDummyFactory(OccultismItems.RITUAL_DUMMY_REPAIR_CHALKS, "Починка мелка", "Джинн", "Полностью починит мелок, вселив в него Джинна.");
        this.lang("ru_ru").autoDummyFactory(OccultismItems.RITUAL_DUMMY_REPAIR_TOOLS, "Починка инструмента", "Африт", "Полностью починит инструмент, вселив в него Африта.");
        this.lang("ru_ru").autoDummyFactory(OccultismItems.RITUAL_DUMMY_REPAIR_ARMORS, "Починка брони", "Африт", "Полностью починит броню, вселив в неё Африта.");
        this.lang("ru_ru").autoDummyFactory(OccultismItems.RITUAL_DUMMY_REPAIR_MINERS, "Восстановление рудокопа", "Африт", "Продлит шахтёрский договор, заключив сделку с Афритом.");
        //MISC
			//Resurrect
        this.lang("ru_ru").autoDummyFactory(OccultismItems.RITUAL_DUMMY_RESURRECT_FAMILIAR, "Воскресение фамильяра", "Фамильяр", "Воскрешает фамильяра из осколка души.");
        this.lang("ru_ru").autoDummyFactory(OccultismItems.RITUAL_DUMMY_RESURRECT_ALLAY, "Очистка вредины в тихоню", "Фамильяр", "Очищает вредину в тихоню с помощью воскресения.");
			//Wild
        this.lang("ru_ru").autoDummyFactory(OccultismItems.RITUAL_DUMMY_WILD_HUNT, "призыв Дикой охоты", "дикая природа", "Дикая охота (Охота Каина) состоит из визер-скелетов и их прислужников, с которых большой шанс получить черепа визер-скелетов, что и с прислужников.");
        this.lang("ru_ru").autoDummyFactory(OccultismItems.RITUAL_DUMMY_WILD_CREEPER, "призыв орды криперов", "дикая природа", "Орда криперов состоит из несколько заряженных криперов, с которых выпадает много пластинок.");
        this.lang("ru_ru").autoDummyFactory(OccultismItems.RITUAL_DUMMY_WILD_DROWNED, "призыв орды утопленников", "дикая природа", "Орда утопленников состоит из несколько утопленников, с которых выпадают предметы, связанные с испытаниями океана.");
        this.lang("ru_ru").autoDummyFactory(OccultismItems.RITUAL_DUMMY_WILD_HUSK, "призыв орды кадавров", "дикая природа", "Орда кадавров состоит из несколько кадавров, с которых выпадают предметы, связанные с испытаниями пустыни.");
        this.lang("ru_ru").autoDummyFactory(OccultismItems.RITUAL_DUMMY_WILD_SILVERFISH, "призыв орды диких чешуйниц", "дикая природа", "Орда диких чешуйниц состоит из несколько чешуйниц, с которых выпадают предметы, связанные с испытаниями руин.");
        this.lang("ru_ru").autoDummyFactory(OccultismItems.RITUAL_DUMMY_WILD_WEAK_BREEZE, "призыв дикого слабого вихря", "дикая природа", "Дикий слабый вихрь будет сбрасывать ключ испытаний и предметы, связанные с камерой испытаний.");
        this.lang("ru_ru").autoDummyFactory(OccultismItems.RITUAL_DUMMY_WILD_BREEZE, "призыв дикого вихря", "дикая природа", "Дикий вихрь будет сбрасывать «Зловещий ключ испытаний» и предметы, связанные с камерой испытаний.");
        this.lang("ru_ru").autoDummyFactory(OccultismItems.RITUAL_DUMMY_WILD_STRONG_BREEZE, "призыв дикого сильного вихря", "дикая природа", "Дикий сильный вихрь будет сбрасывать навершие булавы и предметы, связанные с камерой испытаний.");
        this.lang("ru_ru").autoDummyFactory(OccultismItems.RITUAL_DUMMY_WILD_ILLAGER, "призыв диких разбойников", "дикая природа", "Призывает дикого заклинателя и его приспешника.");
        this.lang("ru_ru").autoDummyFactory(OccultismItems.RITUAL_DUMMY_WILD_RANDOM_ANIMAL_COMMON, "призыв группы случайных обычных животных", "дикая природа", "Призывает группу случайных пассивных обычных животных. (Все варианты: курица, корова, свинья, овца, спрут и волк).");
        this.lang("ru_ru").autoDummyFactory(OccultismItems.RITUAL_DUMMY_WILD_RANDOM_ANIMAL_WATER, "призыв группы случайных водных животных", "дикая природа", "Призывает группу случайных пассивных водных животных. (Все варианты: аксолотль, лягушка, дельфин, треска, лосось, тропическая рыба, иглобрюх, спрут, светящийся спрут, головастик, черепаха и снежный голем).");
        this.lang("ru_ru").autoDummyFactory(OccultismItems.RITUAL_DUMMY_WILD_RANDOM_ANIMAL_SMALL, "призыв группы случайных малых животных", "дикая природа", "Призывает группу случайных пассивных малых животных. (Все варианты: тихоня, летучая мышь, пчела, попугай, кошка, оцелот, лиса и кролик).");
        this.lang("ru_ru").autoDummyFactory(OccultismItems.RITUAL_DUMMY_WILD_RANDOM_ANIMAL_SPECIAL, "призыв группы случайных специальных животных", "дикая природа", "Призывает группу случайных пассивных специальных животных. (Все варианты: свинья, верблюд, осёл, лошадь, лошадь-скелет, лошадь-зомби, лама, лама торговца, мул и лавомерка).");
        this.lang("ru_ru").autoDummyFactory(OccultismItems.RITUAL_DUMMY_WILD_RANDOM_ANIMAL_RIDEABLE, "призыв группы случайных ездовых животных", "дикая природа", "Призывает группу случайных пассивных специальных животных. (Все варианты: броненосец, муушрум, панда, белый медведь, коза, железный голем и нюхач).");
        this.lang("ru_ru").autoDummyFactory(OccultismItems.RITUAL_DUMMY_WILD_VILLAGER, "призыв группы крестьян", "дикая природа", "Призывает группу крестьян и одного странствующего торговца.");
			//Forge
        this.lang("ru_ru").autoDummyFactory(OccultismItems.RITUAL_DUMMY_FORGE_BEE_NEST, "создание пчелиного гнезда", "дикая природа", "Дикие духи создадут пчелиное гнездо, более эстетичнее пчелиного улья.");
        this.lang("ru_ru").autoDummyFactory(OccultismItems.RITUAL_DUMMY_FORGE_BELL, "создание колокола", "дикая природа", "Дикие духи создадут колокол.");
        this.lang("ru_ru").autoDummyFactory(OccultismItems.RITUAL_DUMMY_FORGE_BUDDING_AMETHYST, "создание цветущего аметиста", "дикая природа", "Дикие духи создадут цветущий аметист.");
        this.lang("ru_ru").autoDummyFactory(OccultismItems.RITUAL_DUMMY_FORGE_WILD_TRIM, "Кузнечный шаблон", "дикая природа", "Дикие духи создадут кузнечный шаблон.");
		this.lang("ru_ru").autoDummyFactory(OccultismItems.RITUAL_DUMMY_FORGE_IRON_HORSE_ARMOR, "создание железной конской брони", "дикая природа", "Дикие духи создадут железную конскую броню.");
        this.lang("ru_ru").autoDummyFactory(OccultismItems.RITUAL_DUMMY_FORGE_GOLDEN_HORSE_ARMOR, "создание золотой конской брони", "дикая природа", "Дикие духи создадут золотую конскую броню.");
        this.lang("ru_ru").autoDummyFactory(OccultismItems.RITUAL_DUMMY_FORGE_DIAMOND_HORSE_ARMOR, "создание алмазной конской брони", "дикая природа", "Дикие духи создадут алмазную конскую броню.");
        this.lang("ru_ru").autoDummyFactory(OccultismItems.RITUAL_DUMMY_FORGE_REINFORCED_DEEPSLATE, "создание укреплённого глубинного сланца", "дикая природа", "Дикие духи создадут укреплённый глубинный сланец.");
		this.lang("ru_ru").autoDummyFactory(OccultismItems.RITUAL_DUMMY_FORGE_CELESTIAL_CHALICE, "создание небесного потира", "сверхъестественный", "Сверхъестественные духи создадут небесный потир, выполняющий ритуалы на раз. Вот ваш трофей!");
        this.lang("ru_ru").autoDummyFactory(OccultismItems.RITUAL_DUMMY_FORGE_ELDRITCH_CHALICE, "создание сверхъестественного потира", "сверхъестественный", "Сверхъестественные духи создадут сверхъестественный потир, выполняющий ритуалы на раз. Вот ваш трофей!");
		this.lang("ru_ru").autoDummyFactory(OccultismItems.RITUAL_DUMMY_FORGE_CHALK_RAINBOW, "создание радужного мелка", "сверхъестественный", "Сверхъестественные духи создадут радужный мелок, заменяющий любой мелок.");
        this.lang("ru_ru").autoDummyFactory(OccultismItems.RITUAL_DUMMY_FORGE_CHALK_VOID, "создание пустотного мелка", "сверхъестественный", "Сверхъестественные духи создадут мелок пустоты, заменяющий любой мелок.");
        this.lang("ru_ru").autoDummyFactory(OccultismItems.RITUAL_DUMMY_FORGE_TRINITY_GEM, "создание камня Троицы", "сверхъестественный", "Сверхъестественные духи создадут камень Троицы, усовершенствуя камень души.");
        this.lang("ru_ru").autoDummyFactory(OccultismItems.RITUAL_DUMMY_FORGE_STABILIZED_STORAGE, "создание стабилизированного актуатора пространственного хранилища", "сверхъестественный", "Сверхъестественные духи создадут стабилизированный актуатор пространственного хранилища. Работает как актуатор с максимальным количеством стабилизаторов, занимая всего лишь 1 блок. Этот рецепт сохраняет предметы внутри актуатора.");
        this.lang("ru_ru").autoDummyFactory(OccultismItems.RITUAL_DUMMY_FORGE_STABILIZED_STORAGE_DARK, "создание тёмного стабилизированного актуатора пространственного хранилища", "сверхъестественный", "Сверхъестественные духи создадут тёмный стабилизированный актуатор пространственного хранилища. Работает как актуатор с максимальным количеством стабилизаторов, занимая всего лишь 1 блок. Этот рецепт сохраняет предметы внутри актуатора.");
		this.lang("ru_ru").autoDummyFactory(OccultismItems.RITUAL_DUMMY_FORGE_MINER_ANCIENT_ELDRITCH, "призыв сверхъестественного древнего рудокопа", "Сверхъестественный", "Призывайте сверхъестественного древнего рудокопа в волшебную лампу.");
		this.lang("ru_ru").autoDummyFactory(OccultismItems.RITUAL_DUMMY_CRAFT_STABILIZER_TIER5, "создание стабилизатора хранилища [уровень 5]", "Eldritch", "Стабилизатор хранилища позволяет хранить много предметов в средстве доступа пространственного хранилища.");
        this.lang("ru_ru").autoDummyFactory(OccultismItems.RITUAL_DUMMY_CRAFT_STABILIZER_TIER5_DARK, "создание тёмного стабилизатора хранилища [уровень 5]", "Сверхъестественный", "Тёмный стабилизатор хранилища позволяет хранить много предметов в средстве доступа пространственного хранилища.");
	}

    public void autoDummyFactory(DeferredItem<Item> dummy, String name, String tier, String description) {
        this.add(dummy.get(), "Ритуал: " + name);
        this.addTooltip(dummy.get(), description);
        this.addAutoTooltip(dummy.get(), "Уровень: " + tier);
        this.addRitualMessage(dummy, "conditions", "Для этого ритуала удовлетворены не все требования.");
        this.addRitualMessage(dummy, "started", "Запуск ритуала: " + name +".");
        this.addRitualMessage(dummy, "finished", "Ритуал успешно выполнен: " + name +".");
        this.addRitualMessage(dummy, "interrupted", "Нарушение ритуала: " + name +".");
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
        this.lang("ru_ru").add("dialog.occultism.beaver.no_upgrade", "Прежде чем фамильяр-созерцатель будет разбрасываться лакомствами — фамильяр-кузнец должен улучшить его!");
        this.lang("ru_ru").add("dialog.occultism.fairy.breath_on_cooldown", "Эй, прислушайся, жди!");
        this.lang("ru_ru").add("dialog.occultism.fairy.no_upgrade", "Фамильяр-кузнец должен улучшить фамильяра-фею, прежде чем изрыгать пламенем, как дракон!");
        this.lang("ru_ru").add("dialog.occultism.devil.sin_on_cooldown", "Ещё один грех будет доступен по истечении: %s тактов!");
        this.lang("ru_ru").add("dialog.occultism.devil.no_upgrade", "Фамильяр-кузнец должен улучшить фамильяра-дьявола, прежде чем совершить грех!");
		this.lang("ru_ru").add("dialog.occultism.cthulhu.prismarine_on_cooldown", "Ожидание большой волны... Сила океана — это заряд!");
        this.lang("ru_ru").add("dialog.occultism.partner.heart_on_cooldown", "О, дорогой(-ая), мне нужно побольше времени, чтобы сделать это снова. (Следующий раз через: %s тактов).");
    }

    private void addModonomiconIntegration() {
        this.lang("ru_ru").add(I18n.RITUAL_RECIPE_ITEM_USE, "Предмет использования: ");
        this.lang("ru_ru").add(I18n.RITUAL_RECIPE_SUMMON, "Призыв: %s");
        this.lang("ru_ru").add(I18n.RITUAL_RECIPE_JOB, "Должность: %s");
        this.lang("ru_ru").add(I18n.RITUAL_RECIPE_SACRIFICE, "Жертва: %s");
        this.lang("ru_ru").add(I18n.RITUAL_RECIPE_GO_TO_PENTACLE, "Открыть страницу пентакля: %s");
    }

    private void advancementTitle(String name, String s) {
        this.add(((TranslatableContents) OccultismAdvancementSubProvider.title(name).getContents()).getKey(), s);
    }

    private void advancementDescr(String name, String s) {
        this.add(((TranslatableContents) OccultismAdvancementSubProvider.descr(name).getContents()).getKey(), s);
    }

    private void addTags() {
        // Block tags
        this.lang("ru_ru").addBlockTag(OccultismTags.Blocks.OTHERWORLD_SAPLINGS, "Потусторонние саженцы");
        this.lang("ru_ru").addBlockTag(OccultismTags.Blocks.OTHERWORLD_SAPLINGS_NATURAL, "Потусторонние саженцы_NATURAL");
        this.lang("ru_ru").addBlockTag(OccultismTags.Blocks.CANDLES, "Свечи");
        this.lang("ru_ru").addBlockTag(OccultismTags.Blocks.CAVE_WALL_BLOCKS, "Пещерная ограда");
        this.lang("ru_ru").addBlockTag(OccultismTags.Blocks.NETHERRACK, "Незерак");
        this.lang("ru_ru").addBlockTag(OccultismTags.Blocks.STORAGE_STABILIZER, "Стабилизатор хранилища");
        this.lang("ru_ru").addBlockTag(OccultismTags.Blocks.TREE_SOIL, "Почва для дерева");
        this.lang("ru_ru").addBlockTag(OccultismTags.Blocks.WORLDGEN_BLACKLIST, "Чёрный список блоков генерации мира");
        this.lang("ru_ru").addBlockTag(OccultismTags.Blocks.IESNIUM_ORE, "Руда айзния");
        this.lang("ru_ru").addBlockTag(OccultismTags.Blocks.SILVER_ORE, "Серебряная руда");
        this.lang("ru_ru").addBlockTag(OccultismTags.Blocks.STORAGE_BLOCKS_IESNIUM, "Хранилище айзниевых блоков");
        this.lang("ru_ru").addBlockTag(OccultismTags.Blocks.STORAGE_BLOCKS_SILVER, "Хранилище серебряных блоков");
        this.lang("ru_ru").addBlockTag(OccultismTags.Blocks.STORAGE_BLOCKS_RAW_IESNIUM, "Хранилище рудного айзния");
        this.lang("ru_ru").addBlockTag(OccultismTags.Blocks.STORAGE_BLOCKS_RAW_SILVER, "Хранилище рудного серебра");
        this.lang("ru_ru").addBlockTag(OccultismTags.Blocks.OTHERWORLD_COLLECTS, "Потустороннее, которое можно собрать.");

        // Item tags
		this.lang("ru_ru").addItemTag(OccultismTags.Items.START_SPIRIT_FIRE, "Способен создать огонь духов");
        this.lang("ru_ru").addItemTag(OccultismTags.Items.OTHERWORLD_SAPLINGS, "Потусторонние саженцы");
        this.lang("ru_ru").addItemTag(OccultismTags.Items.OTHERWORLD_SAPLINGS_NATURAL, "Природные потусторонние саженцы");
        this.lang("ru_ru").addItemTag(OccultismTags.Items.BOOK_OF_CALLING_DJINNI, "Книга призыва Джинна");
        this.lang("ru_ru").addItemTag(OccultismTags.Items.BOOK_OF_CALLING_FOLIOT, "Книга призыва Фолиота");
        this.lang("ru_ru").addItemTag(OccultismTags.Items.BOOKS_OF_BINDING, "Книги привязки");
		this.lang("ru_ru").addItemTag(OccultismTags.Items.BOOKS_FOR_EMPTY, "Книги для пустых книг привязки");
        this.lang("ru_ru").addItemTag(OccultismTags.Items.Miners.BASIC_RESOURCES, "Рудокопы базовых ресурсов");
        this.lang("ru_ru").addItemTag(OccultismTags.Items.Miners.DEEPS, "Рудокопы глубинносланца");
        this.lang("ru_ru").addItemTag(OccultismTags.Items.Miners.MASTER, "Рудокопы редких ресурсов");
        this.lang("ru_ru").addItemTag(OccultismTags.Items.Miners.ELDRITCH, "Сверхъестественные рудокопы");
        this.lang("ru_ru").addItemTag(OccultismTags.Items.Miners.ORES, "Основные рудокопы");
        this.lang("ru_ru").addItemTag(OccultismTags.Items.ELYTRA, "Элитры");
        this.lang("ru_ru").addItemTag(OccultismTags.Items.OTHERWORLD_GOGGLES, "Потусторонние очки");
        this.lang("ru_ru").addItemTag(OccultismTags.Items.DATURA_SEEDS, "Семена видения демона");
        this.lang("ru_ru").addItemTag(OccultismTags.Items.DATURA_CROP, "Видение демона");
        this.lang("ru_ru").addItemTag(OccultismTags.Items.COPPER_DUST, "Медная пыль");
        this.lang("ru_ru").addItemTag(OccultismTags.Items.GOLD_DUST, "Золотая пыль");
        this.lang("ru_ru").addItemTag(OccultismTags.Items.IESNIUM_DUST, "Айзниевая пыль");
        this.lang("ru_ru").addItemTag(OccultismTags.Items.IRON_DUST, "Железная пыль");
        this.lang("ru_ru").addItemTag(OccultismTags.Items.SILVER_DUST, "Серебряная пыль");
        this.lang("ru_ru").addItemTag(OccultismTags.Items.END_STONE_DUST, "Измельчённый эндерняк");
        this.lang("ru_ru").addItemTag(OccultismTags.Items.OBSIDIAN_DUST, "Измельчённый обсидиан");
        this.lang("ru_ru").addItemTag(OccultismTags.Items.IESNIUM_INGOT, "Айзниевый слиток");
        this.lang("ru_ru").addItemTag(OccultismTags.Items.SILVER_INGOT, "Серебряный слиток");
        this.lang("ru_ru").addItemTag(OccultismTags.Items.IESNIUM_NUGGET, "Азниевый самородок");
        this.lang("ru_ru").addItemTag(OccultismTags.Items.SILVER_NUGGET, "Серебряный самородок");
        this.lang("ru_ru").addItemTag(OccultismTags.Items.IESNIUM_ORE, "Руда айзния");
        this.lang("ru_ru").addItemTag(OccultismTags.Items.SILVER_ORE, "Серебряная руда");
        this.lang("ru_ru").addItemTag(OccultismTags.Items.RAW_IESNIUM, "Рудный айзний");
        this.lang("ru_ru").addItemTag(OccultismTags.Items.RAW_SILVER, "Рудное серебро");
        this.lang("ru_ru").addItemTag(OccultismTags.Items.STORAGE_BLOCK_IESNIUM, "Хранилище айзниевых блоков");
        this.lang("ru_ru").addItemTag(OccultismTags.Items.STORAGE_BLOCK_SILVER, "Хранилище серебряный блоков");
        this.lang("ru_ru").addItemTag(OccultismTags.Items.STORAGE_BLOCK_RAW_IESNIUM, "Хранилище блоков рудного айзния");
        this.lang("ru_ru").addItemTag(OccultismTags.Items.STORAGE_BLOCK_RAW_SILVER, "Хранилище блоков рудного серебра");
        this.lang("ru_ru").addItemTag(OccultismTags.Items.MUSHROOM_BLOCKS, "Грибные блоки");
		this.lang("ru_ru").addItemTag(OccultismTags.Items.TUBE_CORALS, "Трубчатый коралл");
		this.lang("ru_ru").addItemTag(OccultismTags.Items.LIGHTNING_RODS, "Громоотводы");
		this.lang("ru_ru").addItemTag(OccultismTags.Items.ENCHANTING_TABLES, "Чародейские столы");
		this.lang("ru_ru").addItemTag(OccultismTags.Items.IRON_BARS, "Железные слитки");
        this.lang("ru_ru").addItemTag(OccultismTags.Items.TALLOW, "Жир");
        this.lang("ru_ru").addItemTag(OccultismTags.Items.METAL_AXES, "Металлические топоры");
        this.lang("ru_ru").addItemTag(OccultismTags.Items.MAGMA, "Магма");
        this.lang("ru_ru").addItemTag(OccultismTags.Items.BOOKS, "Книги");
        this.lang("ru_ru").addItemTag(OccultismTags.Items.FRUITS, "Фрукты");
        this.lang("ru_ru").addItemTag(OccultismTags.Items.AMETHYST_DUST, "Аметистовая пыль");
        this.lang("ru_ru").addItemTag(OccultismTags.Items.BLACKSTONE_DUST, "Чернитная пыль");
        this.lang("ru_ru").addItemTag(OccultismTags.Items.BLUE_ICE_DUST, "Пыль из синего льда");
        this.lang("ru_ru").addItemTag(OccultismTags.Items.CALCITE_DUST, "Кальцитная пыль");
        this.lang("ru_ru").addItemTag(OccultismTags.Items.ICE_DUST, "Пыль изо льда");
        this.lang("ru_ru").addItemTag(OccultismTags.Items.PACKED_ICE_DUST, "Пыль из плотного льда");
        this.lang("ru_ru").addItemTag(OccultismTags.Items.DRAGONYST_DUST, "Драконистовая пыль");
        this.lang("ru_ru").addItemTag(OccultismTags.Items.ECHO_DUST, "Пыль эхо");
        this.lang("ru_ru").addItemTag(OccultismTags.Items.EMERALD_DUST, "Изумрудная пыль");
        this.lang("ru_ru").addItemTag(OccultismTags.Items.LAPIS_DUST, "Лазуритовая пыль");
        this.lang("ru_ru").addItemTag(OccultismTags.Items.NETHERITE_DUST, "Незеритовая пыль");
        this.lang("ru_ru").addItemTag(OccultismTags.Items.NETHERITE_SCRAP_DUST, "Пыль из незеритового обломка");
        this.lang("ru_ru").addItemTag(OccultismTags.Items.RESEARCH_DUST, "Пыль исследования");
        this.lang("ru_ru").addItemTag(OccultismTags.Items.WITHERITE_DUST, "Визеритовая пыль");
        this.lang("ru_ru").addItemTag(OccultismTags.Items.OTHERSTONE_DUST, "Пыль из потустороннего камня");
		this.lang("ru_ru").addItemTag(OccultismTags.Items.OTHERROCK_DUST, "Пыль из потусторонней породы");
        this.lang("ru_ru").addItemTag(OccultismTags.Items.CHALK_BASE_DUST, "Chalk Base Dust");
		this.lang("ru_ru").addItemTag(OccultismTags.Items.OTHERWORLD_WOOD_DUST, "Пыль из потусторонней древесины");
        this.lang("ru_ru").addItemTag(OccultismTags.Items.OCCULTISM_CANDLES, "Свечи из Occultism");
        this.lang("ru_ru").addItemTag(OccultismTags.Items.Miners.MINERS, "Пространственные рудокопы");
        this.lang("ru_ru").addItemTag(OccultismTags.Items.SCUTESHELL, "Щиток или панцирь");
        this.lang("ru_ru").addItemTag(OccultismTags.Items.BLAZE_DUST, "Пылающая пыль");
        this.lang("ru_ru").addItemTag(OccultismTags.Items.MANUALS, "Руководства");
        this.lang("ru_ru").addItemTag(OccultismTags.Items.TOOLS_KNIFE, "Ножи");
		this.lang("ru_ru").addItemTag(ResourceLocation.fromNamespaceAndPath("c", "tools/knife"), "Ножи");
        this.lang("ru_ru").addItemTag(ResourceLocation.fromNamespaceAndPath("curios", "belt"), "Пояса");
        this.lang("ru_ru").addItemTag(ResourceLocation.fromNamespaceAndPath("curios", "hands"), "Руки");
        this.lang("ru_ru").addItemTag(ResourceLocation.fromNamespaceAndPath("curios", "head"), "Голова");
        this.lang("ru_ru").addItemTag(ResourceLocation.fromNamespaceAndPath("curios", "ring"), "Перстень");
        this.lang("ru_ru").addItemTag(OccultismTags.Items.DEMONIC_PARTNER_FOOD, "Пища для демонического партнёра");
        this.lang("ru_ru").addItemTag(OccultismTags.Items.OTHERCOBBLESTONE, "Потусторонний булыжник");
        this.lang("ru_ru").addItemTag(OccultismTags.Items.OTHERSTONE, "Потусторонний камень");
        this.lang("ru_ru").addItemTag(OccultismTags.Items.OTHERWORLD_LOGS, "Потусторонние брёвна");
        this.lang("ru_ru").addItemTag(OccultismTags.Items.PENTACLE_MATERIALS, "Материалы для пентакля");
        this.lang("ru_ru").addItemTag(OccultismTags.Items.TOOLS_CHALK, "Мелки");
		this.lang("ru_ru").addItemTag(OccultismTags.Items.CLAY, "Глина");

		this.lang("ru_ru").addItemTag(OccultismTags.Items.DROPS_POSSESSED_BLAZE, "Выпадает с одержимого всполоха");
        this.lang("ru_ru").addItemTag(OccultismTags.Items.DROPS_POSSESSED_BREEZE, "Выпадает с одержимого вихря");
		this.lang("ru_ru").addItemTag(OccultismTags.Items.DROPS_POSSESSED_ELDER_GUARDIAN, "Выпадает с одержимого древнего стража");
        this.lang("ru_ru").addItemTag(OccultismTags.Items.DROPS_POSSESSED_ENDERMAN, "Выпадает с одержимого эндермена");
		this.lang("ru_ru").addItemTag(OccultismTags.Items.DROPS_POSSESSED_ENDERMITE, "Выпадает с одержимого эндермита");
        this.lang("ru_ru").addItemTag(OccultismTags.Items.DROPS_POSSESSED_EVOKER, "Выпадает с одержимого заклинателя");
        this.lang("ru_ru").addItemTag(OccultismTags.Items.DROPS_POSSESSED_GHAST, "Выпадает с одержимого гаста");
        this.lang("ru_ru").addItemTag(OccultismTags.Items.DROPS_POSSESSED_HOGLIN, "Выпадает с одержимого хоглина");
		this.lang("ru_ru").addItemTag(OccultismTags.Items.DROPS_POSSESSED_PHANTOM, "Выпадает с одержимого фантома");
        this.lang("ru_ru").addItemTag(OccultismTags.Items.DROPS_POSSESSED_SHULKER, "Выпадает с одержимого шалкера");
		this.lang("ru_ru").addItemTag(OccultismTags.Items.DROPS_POSSESSED_SKELETON, "Выпадает с одержимого скелета");
		this.lang("ru_ru").addItemTag(OccultismTags.Items.DROPS_POSSESSED_STRONG_BREEZE, "Выпадает с одержимого сильного вихря");
        this.lang("ru_ru").addItemTag(OccultismTags.Items.DROPS_POSSESSED_WARDEN, "Выпадает с одержимого хранителя");
        this.lang("ru_ru").addItemTag(OccultismTags.Items.DROPS_POSSESSED_WEAK_BREEZE, "Выпадает с одержимого слабого вихря");
		this.lang("ru_ru").addItemTag(OccultismTags.Items.DROPS_POSSESSED_WEAK_SHULKER, "Выпадает с одержимого слабого шалкера");
        this.lang("ru_ru").addItemTag(OccultismTags.Items.DROPS_POSSESSED_WITCH, "Выпадает с одержимой ведьмы");
		this.lang("ru_ru").addItemTag(OccultismTags.Items.DROPS_POSSESSED_ZOMBIE_PIGLIN, "Выпадает с одержимого зомбифицированного пиглина");
		this.lang("ru_ru").addItemTag(OccultismTags.Items.DROPS_POSSESSED_GUARDIAN, "Выпадает с одержимого стража");
        this.lang("ru_ru").addItemTag(OccultismTags.Items.DROPS_WILD_HUNT, "Выпадает с дикой охоты");
        this.lang("ru_ru").addItemTag(OccultismTags.Items.DROPS_WILD_HORDE_CREEPER, "Выпадает с дикой орды криперов");
        this.lang("ru_ru").addItemTag(OccultismTags.Items.DROPS_WILD_HORDE_DROWNED, "Выпадает с дикой орды утопленников");
        this.lang("ru_ru").addItemTag(OccultismTags.Items.DROPS_WILD_HORDE_HUSK, "Выпадает с дикой орды кадавров");
        this.lang("ru_ru").addItemTag(OccultismTags.Items.DROPS_WILD_HORDE_SILVERFISH, "Выпадает с дикой орды чушейниц");
		this.lang("ru_ru").addItemTag(OccultismTags.Items.RANDOM_SPAWN_COMMON, "Может появиться в виде случайного обычного животного");
        this.lang("ru_ru").addItemTag(OccultismTags.Items.RANDOM_SPAWN_RIDEABLE, "Может появиться в виде случайного ездового животного");
        this.lang("ru_ru").addItemTag(OccultismTags.Items.RANDOM_SPAWN_SMALL, "Может появиться в виде случайного малого животного");
        this.lang("ru_ru").addItemTag(OccultismTags.Items.RANDOM_SPAWN_SPECIAL, "Может появиться в виде случайного специального животного");
        this.lang("ru_ru").addItemTag(OccultismTags.Items.RANDOM_SPAWN_WATER, "Может появиться в виде случайного водного животного");
        this.lang("ru_ru").addItemTag(OccultismTags.Items.RANDOM_SPAWN_VILLAGER, "Может появиться в виде случайного крестьянина");
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
        this.lang("ru_ru").add("emi.category.occultism.spirit_fire", "Огонь духов");
		this.lang("ru_ru").add("emi.category.occultism.spirit_trader", "Дух торговец");
        this.lang("ru_ru").add("emi.category.occultism.crushing", "Измельчение");
		this.lang("ru_ru").add("emi.category.occultism.crystallize", "Кристаллизовать");
        this.lang("ru_ru").add("emi.category.occultism.miner", "Пространственная шахта");
        this.lang("ru_ru").add("emi.category.occultism.ritual", "Ритуалы");
        this.lang("ru_ru").add("emi.occultism.item_to_use", "Используйте предмет после запуска ритуала.");
		this.add("emi.occultism.ritual_duration", "%s секунд.");
    }

    private void addConditionMessages() {
        this.lang("ru_ru").add(TranslationKeys.Condition.Ritual.IS_IN_DIMENSION_TYPE_NOT_FULFILLED, "Выполните ритуал в измерении [%s]! Он был выполнен в [%s].");
        this.lang("ru_ru").add(TranslationKeys.Condition.Ritual.IS_IN_DIMENSION_TYPE_DESCRIPTION, "Необходимо выполнить в измерении [%s].");

		this.lang("ru_ru").add(TranslationKeys.Condition.Ritual.IS_IN_DIMENSION_NOT_FULFILLED, "Выполните ритуал в измерении [%s]! Он был выполнен в [%s].");
        this.lang("ru_ru").add(TranslationKeys.Condition.Ritual.IS_IN_DIMENSION_DESCRIPTION, "Необходимо выполнить в измерении [%s].");

        this.lang("ru_ru").add(TranslationKeys.Condition.Ritual.IS_IN_BIOME_NOT_FULFILLED, "Выполните ритуал в биоме [%s]! Он был выполнен в [%s].");
        this.lang("ru_ru").add(TranslationKeys.Condition.Ritual.IS_IN_BIOME_DESCRIPTION, "Необходимо выполнить в биоме [%s].");

        this.lang("ru_ru").add(TranslationKeys.Condition.Ritual.IS_IN_BIOME_WITH_TAG_NOT_FULFILLED, "Выполните ритуал в биоме с тегом [%s]! Он был выполнен в биоме [%s], у которого отсутствует тег.");
        this.lang("ru_ru").add(TranslationKeys.Condition.Ritual.IS_IN_BIOME_WITH_TAG_DESCRIPTION, "Необходимо выполнить в биоме с тегом [%s].");

        this.lang("ru_ru").add(TranslationKeys.Condition.Ritual.AND_NOT_FULFILLED, "Один или несколько из необходимых требований не были выполнены (должно быть всё выполнено): [%s]");
        this.lang("ru_ru").add(TranslationKeys.Condition.Ritual.AND_DESCRIPTION, "Необходимо выполнить все нижеследующие требования: %s");

        this.lang("ru_ru").add(TranslationKeys.Condition.Ritual.OR_NOT_FULFILLED, "Ни один из требуемых требований не были выполнены (должно быть выполнено минимум одно): %s");
        this.lang("ru_ru").add(TranslationKeys.Condition.Ritual.OR_DESCRIPTION, "Необходимо выполнить минимум один из следующих требований: [%s]");

        this.lang("ru_ru").add(TranslationKeys.Condition.Ritual.TRUE_NOT_FULFILLED, "Постоянное выполняемое требование неким образом не выполняется. Это никогда не должно происходить.");
        this.lang("ru_ru").add(TranslationKeys.Condition.Ritual.TRUE_DESCRIPTION, "Это условие всегда выполняется.");

        this.lang("ru_ru").add(TranslationKeys.Condition.Ritual.FALSE_NOT_FULFILLED, "Это требование никогда не выполнялось. Используйте другое требование в рецепте, чтобы заставить ритуал заработать.");
        this.lang("ru_ru").add(TranslationKeys.Condition.Ritual.FALSE_DESCRIPTION, "Это требование никогда не выполнялось.");

        this.lang("ru_ru").add(TranslationKeys.Condition.Ritual.NOT_NOT_FULFILLED, "Требование было выполнено, но не должно выполняться: [%s]");
        this.lang("ru_ru").add(TranslationKeys.Condition.Ritual.NOT_DESCRIPTION, "Следующее условие не должно удовлетворяться: [%s]");

        this.lang("ru_ru").add(TranslationKeys.Condition.Ritual.ITEM_EXISTS_NOT_FULFILLED, "Предмет, как %s не существует.");
        this.lang("ru_ru").add(TranslationKeys.Condition.Ritual.ITEM_EXISTS_DESCRIPTION, "Предмет «%s» должен существовать.");


        this.lang("ru_ru").add(TranslationKeys.Condition.Ritual.MOD_LOADED_NOT_FULFILLED, "Мод «%s» не установлен.");
        this.lang("ru_ru").add(TranslationKeys.Condition.Ritual.MOD_LOADED_DESCRIPTION, "Мод «%s» должен быть установлен.");

        this.lang("ru_ru").add(TranslationKeys.Condition.Ritual.TAG_EMPTY_NOT_FULFILLED, "Тег [%s] не пустой.");
        this.lang("ru_ru").add(TranslationKeys.Condition.Ritual.TAG_EMPTY_DESCRIPTION, "Тег [%s] должен быть пустым.");

	}

    private void addConfigurationTranslations() {

        this.lang("ru_ru").addConfig("visual", "Визуальные настройки");
        this.lang("ru_ru").addConfig("showItemTagsInTooltip", "Показывать теги предмета в подсказках");
        this.lang("ru_ru").addConfig("disableDemonsDreamShaders", "Отключить шейдеры для видения демона");
        this.lang("ru_ru").addConfig("disableHolidayTheming", "Отключить шейдеры для потусторонних очков");
        this.lang("ru_ru").addConfig("useAlternativeDivinationRodRenderer", "Использовать альтернативный отрисовщик для стержня прорицания");
        this.lang("ru_ru").addConfig("whiteChalkGlyphColor", "Цвет белого глифа");
        this.lang("ru_ru").addConfig("yellowChalkGlyphColor", "Цвет жёлтого глифа");
        this.lang("ru_ru").addConfig("purpleChalkGlyphColor", "Цвет фиолетового глифа");
        this.lang("ru_ru").addConfig("redChalkGlyphColor", "Цвет красного глифа");
        this.lang("ru_ru").addConfig("lightGrayChalkGlyphColor", "Цвет светло-серого глифа");
        this.lang("ru_ru").addConfig("grayChalkGlyphColor", "Цвет серого глифа");
        this.lang("ru_ru").addConfig("blackChalkGlyphColor", "Цвет чёрного глифа");
        this.lang("ru_ru").addConfig("brownChalkGlyphColor", "Цвет коричневого глифа");
        this.lang("ru_ru").addConfig("orangeChalkGlyphColor", "Цвет оранжевого глифа");
        this.lang("ru_ru").addConfig("limeChalkGlyphColor", "Цвет лаймового глифа");
        this.lang("ru_ru").addConfig("greenChalkGlyphColor", "Цвет зелёного глифа");
        this.lang("ru_ru").addConfig("cyanChalkGlyphColor", "Цвет бирюзового глифа");
        this.lang("ru_ru").addConfig("lightBlueChalkGlyphColor", "Цвет голубого глифа");
        this.lang("ru_ru").addConfig("blueChalkGlyphColor", "Цвет синего глифа");
        this.lang("ru_ru").addConfig("magentaChalkGlyphColor", "Цвет пурпурного глифа");
        this.lang("ru_ru").addConfig("pinkChalkGlyphColor", "Цвет розового глифа");

        this.lang("ru_ru").addConfig("misc", "Дополнительные настройки");
        this.lang("ru_ru").addConfig("syncJeiSearch", "Синхронизировать поиск с JEI");
		this.lang("ru_ru").addConfig("enableEMISync", "Синхронизировать поиск с EMI");
		this.lang("ru_ru").addConfig("storageRows", "Количество рядов в системе хранения");
        this.lang("ru_ru").addConfig("divinationRodHighlightAllResults", "Подсвечивать все результаты стержнем прорицания");
        this.lang("ru_ru").addConfig("divinationRodScanRange", "Радиус сканирования жезла прорицания");
        this.lang("ru_ru").addConfig("disableSpiritFireSuccessSound", "Отключить звук успешности для духовного огня");
		this.lang("ru_ru").addConfig("pentagramInBowlInfoCount", "Максимум названий пентаклей в каждой странице");
        this.lang("ru_ru").addConfig("pentagramInBowlInfoTicks", "Кол-во тактов для изменения текущих пентаклей");

        this.lang("ru_ru").addConfig("storage", "Настройки хранилища");
        this.lang("ru_ru").addConfig("stabilizerTier1AdditionalMaxItemTypes", "Макс. кол-во доп. типов предметов в стабилизаторе [1 уровень].");
        this.lang("ru_ru").addConfig("stabilizerTier1AdditionalMaxTotalItemCount", "Макс. кол-во доп. предметов в общем кол-ве предметов в стабилизаторе [1 уровень].");
        this.lang("ru_ru").addConfig("stabilizerTier2AdditionalMaxItemTypes", "Макс. кол-во доп. типов предметов в стабилизаторе [2 уровень].");
        this.lang("ru_ru").addConfig("stabilizerTier2AdditionalMaxTotalItemCount", "Макс. кол-во доп. предметов в общем кол-ве предметов в стабилизаторе [2 уровень].");
        this.lang("ru_ru").addConfig("stabilizerTier3AdditionalMaxItemTypes", "Макс. кол-во доп. типов предметов в стабилизаторе [3 уровень].");
        this.lang("ru_ru").addConfig("stabilizerTier3AdditionalMaxTotalItemCount", "Макс. кол-во доп. предметов в общем кол-ве предметов в стабилизаторе [3 уровень].");
        this.lang("ru_ru").addConfig("stabilizerTier4AdditionalMaxItemTypes", "Макс. кол-во доп. типов предметов в стабилизаторе [4 уровень].");
        this.lang("ru_ru").addConfig("stabilizerTier4AdditionalMaxTotalItemCount", "Макс. кол-во доп. предметов в общем кол-ве предметов в стабилизаторе [4 уровень].");
		this.lang("ru_ru").addConfig("stabilizerTier5AdditionalMaxItemTypes", "Макс. кол-во доп. типов предметов в стабилизаторе [5 уровень].");
        this.lang("ru_ru").addConfig("stabilizerTier5AdditionalMaxTotalItemCount", "Макс. кол-во доп. предметов в общем кол-ве предметов в стабилизаторе [5 уровень].");
        this.lang("ru_ru").addConfig("controllerMaxItemTypes", "Максимальное количество типов предметов в регуляторе");
        this.lang("ru_ru").addConfig("controllerMaxTotalItemCount", "Максимальное общее количество предметов в регуляторе");
        this.lang("ru_ru").addConfig("stabilizedControllerStabilizers", "Встроенное количество стабилизаторов в стабилизированном регуляторе");
        this.lang("ru_ru").addConfig("unlinkWormholeOnBreak", "Отвязывать червоточину при разрушении");

        this.lang("ru_ru").addConfig("spirit_job", "Настройки должности духов");
		this.lang("ru_ru").addConfig("tier", "Уровень");
        this.lang("ru_ru").addConfig("timeMultiplier", "Множитель времени");
        this.lang("ru_ru").addConfig("outputMultiplier", "Множитель продукции");
        this.lang("ru_ru").addConfig("operationCount", "Готовность рецептов за операции");
        this.lang("ru_ru").addConfig("operationTimer", "Время для каждой операции");
        this.lang("ru_ru").addConfig("crusher_tier1", "Фолиот-дробильщик");
        this.lang("ru_ru").addConfig("crusher_tier2", "Джинн-дробильщик");
        this.lang("ru_ru").addConfig("crusher_tier3", "Африт-дробильщик");
        this.lang("ru_ru").addConfig("crusher_tier4", "Марид-дробильщик");
        this.lang("ru_ru").addConfig("crusherResultPickupDelay", "Задержка сбора предметов для дробильщика");
        this.lang("ru_ru").addConfig("crystal_tier1", "Фолиот-кристаллизовщик");
        this.lang("ru_ru").addConfig("crystal_tier2", "Джинн-кристаллизовщик");
        this.lang("ru_ru").addConfig("crystal_tier3", "Африт-кристаллизовщик");
        this.lang("ru_ru").addConfig("crystal_tier4", "Марид-кристаллизовщик");
        this.lang("ru_ru").addConfig("crystallizerResultPickupDelay", "Задержка сбора предметов для кристаллизовщика");
        this.lang("ru_ru").addConfig("smelter_tier1", "Фолиот-литейщик");
        this.lang("ru_ru").addConfig("smelter_tier2", "Джинн-литейщик");
        this.lang("ru_ru").addConfig("smelter_tier3", "Африт-литейщик");
        this.lang("ru_ru").addConfig("smelter_tier4", "Марид-литейщик");
        this.lang("ru_ru").addConfig("smelterResultPickupDelay", "Задержка сбора предметов для плавильщика");
        this.lang("ru_ru").addConfig("trader_sapling", "Торговец потусторонними саженцами");
        this.lang("ru_ru").addConfig("trader_otherstone", "Торговец потусторонним камнем");
        this.lang("ru_ru").addConfig("trader_otherrock", "Торговец потусторонней породой");
        this.lang("ru_ru").addConfig("trader_gem", "Спекулянт");
        this.lang("ru_ru").addConfig("traderResultPickupDelay", "Задержка сбора предметов для торговца");
        this.lang("ru_ru").addConfig("traderWonderingChance", "Шанс странствующего торговца");
		this.lang("ru_ru").addConfig("dayTimeToCast", "Пора вызывать: день");
        this.lang("ru_ru").addConfig("nightTimeToCast", "Пора вызывать: ночь");
        this.lang("ru_ru").addConfig("rainTimeToCast", "Пора вызывать: дождь");
        this.lang("ru_ru").addConfig("thunderTimeToCast", "Пора вызывать: гроза");
        this.lang("ru_ru").addConfig("clearWeatherTimeToCast", "Пора вызывать: ясная погода");

        this.lang("ru_ru").addConfig("familiar", "Настройки фамильяра");
        this.lang("ru_ru").addConfig("drikwingFamiliarSlowFallingSeconds", "Продолжительность медленного падения для дрикрыла.");
        this.lang("ru_ru").addConfig("blacksmithFamiliarRepairChance", "Шанс починки кузнеца.");
        this.lang("ru_ru").addConfig("blacksmithFamiliarUpgradeCost", "Цена обновления кузнецом железом.");
        this.lang("ru_ru").addConfig("blacksmithFamiliarUpgradeCooldown", "Перезарядка обновления кузнеца.");
        this.lang("ru_ru").addConfig("greedySearchRange", "Дальность поиска алчного фамильяра по горизонтали.");
        this.lang("ru_ru").addConfig("greedyVerticalSearchRange", "Дальность поиска алчного фамильяра по вертикали.");

        this.lang("ru_ru").addConfig("rituals", "Настройки ритуала");
        this.lang("ru_ru").addConfig("enableClearWeatherRitual", "Включить требования ритуалу для ясной погоды.");
        this.lang("ru_ru").addConfig("enableRainWeatherRitual", "Включить требования ритуалу для вызова дождливой погоды.");
        this.lang("ru_ru").addConfig("enableThunderWeatherRitual", "Включить требования ритуалу для вызова грозы.");
        this.lang("ru_ru").addConfig("enableDayTimeRitual", "Разрешить ритуалу изменять время на день.");
        this.lang("ru_ru").addConfig("enableNightTimeRitual", "Разрешить ритуалу изменять время на ночь.");
        this.lang("ru_ru").addConfig("enableRemainingIngredientCountMatching", "Включить соответствия оставшихся ингредиентов в рецептах ритуала.");
        this.lang("ru_ru").addConfig("ritualDurationMultiplier", "Коэффициент регулирования продолжительности всех ритуалов.");
        this.lang("ru_ru").addConfig("possibleSpiritNames", "Возможные имена духов");
		this.lang("ru_ru").addConfig("usePossibleSpiritNamesChance", "Шанс выбора возможных имён духов списком");

        this.lang("ru_ru").addConfig("dimensional_mineshaft", "Настройки пространственной шахты");
        this.lang("ru_ru").addConfig("miner_foliot_unspecialized", "Неспециализированный Фолиот-рудокоп");
        this.lang("ru_ru").addConfig("miner_djinni_ores", "Рудный Джинн-рудокоп");
        this.lang("ru_ru").addConfig("miner_afrit_deeps", "Африт-рудокоп для глубинносланцевой руды");
        this.lang("ru_ru").addConfig("miner_marid_master", "Мастер Марид-рудокоп");
        this.lang("ru_ru").addConfig("miner_ancient_eldritch", "Сверхъестественный древний рудокоп");

        this.lang("ru_ru").addConfig("maxMiningTime", "Максимальное время добычи");
        this.lang("ru_ru").addConfig("rollsPerOperation", "Циклы за операцию");
        this.lang("ru_ru").addConfig("durability", "Прочность");

        this.lang("ru_ru").addConfig("items", "Предметы");
        this.lang("ru_ru").addConfig("anyOreDivinationRod", "Прорицание c:ores");
        this.lang("ru_ru").addConfig("minerOutputBeforeBreak", "Сохранить рудокопов до разрушения");
		this.lang("ru_ru").addConfig("minerEfficiency", "Эффективность рудокопов");
        this.lang("ru_ru").addConfig("minerFortune", "Удача рудокопов");
		this.lang("ru_ru").addConfig("minerSilk", "Рудокопы с шёлковым касанием");
        this.lang("ru_ru").addConfig("unbreakableChalks", "Неломкость мелков");
		this.lang("ru_ru").addConfig("maxDistanceRTP", "Макс. расстояние RTP (случайного телепорта)");
        this.lang("ru_ru").addConfig("maxTryRTP", "Макс. попыток для RTP");
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
        this.addWaila();
    }

    private void addWaila() {
        this.lang("ru_ru").add("occultism.waila.current_ritual","Текущий ритуал: [%s].");
        this.lang("ru_ru").add("occultism.waila.no_current_ritual","Отсутствует текущий ритуал.");
        this.lang("ru_ru").add("occultism.waila.no_item_use","Требуемый предмет не использовался.");
        this.lang("ru_ru").add("occultism.waila.no_sacrifice","Не выполнено требуемое жертвоприношения.");
        this.lang("ru_ru").add("occultism.waila.foliot","Фолиот");
        this.lang("ru_ru").add("occultism.waila.foliot_age","Фолиот: осталось %s секунд.");
        this.lang("ru_ru").add("occultism.waila.djinni","Джинн");
        this.lang("ru_ru").add("occultism.waila.djinni_age","Джинн: осталось %s секунд.");
        this.lang("ru_ru").add("occultism.waila.afrit","Африт");
        this.lang("ru_ru").add("occultism.waila.afrit_age","Африт: осталось %s секунд.");
        this.lang("ru_ru").add("occultism.waila.marid","Марид");
        this.lang("ru_ru").add("occultism.waila.marid_age","Марид: осталось %s секунд.");
		this.lang("ru_ru").add("config.jade.plugin_occultism.foliot","Информация о духах");
        this.lang("ru_ru").add("config.jade.plugin_occultism.sacrificial","Информация о ритуальной чаше");
    }
}
