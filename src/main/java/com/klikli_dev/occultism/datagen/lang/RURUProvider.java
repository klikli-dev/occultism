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
        this.lang("ru_ru").add("item.occultism.book_of_calling" + ".message_target_uuid_no_match", "Этот дух на данный момент не связан с книгой. Нажмите Shift + ПКМ на духе, чтобы связать.");
        this.lang("ru_ru").add("item.occultism.book_of_calling" + ".message_target_linked", "Дух связан с книгой.");
        this.lang("ru_ru").add("item.occultism.book_of_calling" + ".message_target_cannot_link", "Дух не может быть связан с этой книгой. Книга вызова должна соответствовать задаче духа!");
        this.lang("ru_ru").add("item.occultism.book_of_calling" + ".message_target_entity_no_inventory", "У этой сущности нет инвентаря: она не может быть установлена в качестве место ввода.");
        this.lang("ru_ru").add("item.occultism.book_of_calling" + ".message_spirit_not_found", "Дух, связанный с этой книгой не обитает в этой параллельной реальности.");
        this.lang("ru_ru").add("item.occultism.book_of_calling" + ".message_set_deposit", "%s будет вводить в %s со стороны: %s");
        this.lang("ru_ru").add("item.occultism.book_of_calling" + ".message_set_deposit_entity", "%s будет передавать предметы в: %s");
        this.lang("ru_ru").add("item.occultism.book_of_calling" + ".message_set_extract", "%s будет извлекать из %s со стороны: %s");
        this.lang("ru_ru").add("item.occultism.book_of_calling" + ".message_set_base", "База для %s установлена на %s");
        this.lang("ru_ru").add("item.occultism.book_of_calling" + ".message_set_storage_controller", "%s теперь будет принимать заказы на изготовление из %s");
        this.lang("ru_ru").add("item.occultism.book_of_calling" + ".message_set_work_area_size", "%s теперь будет отслеживать рабочую зону %s");
        this.lang("ru_ru").add("item.occultism.book_of_calling" + ".message_set_managed_machine", "Настройки для машины %s обновлены.");
        this.lang("ru_ru").add("item.occultism.book_of_calling" + ".message_set_managed_machine_extract_location", "%s теперь будет извлекать из %s со стороны: %s");
        this.lang("ru_ru").add("item.occultism.book_of_calling" + ".message_no_managed_machine", "Установите машину, прежде чем установить место извлечения %s");

        this.lang("ru_ru").add(OccultismItems.STABLE_WORMHOLE.get().getDescriptionId() + ".message.set_storage_controller", "Стабильная червоточина связана с регулятором хранилища.");
        this.lang("ru_ru").add(OccultismItems.STORAGE_REMOTE.get().getDescriptionId() + ".message.not_loaded", "Чанк для актуатора хранилища не загружен!");
        this.lang("ru_ru").add(OccultismItems.STORAGE_REMOTE.get().getDescriptionId() + ".message.linked", "Удалённое хранилище связано с актуатором.");
        this.lang("ru_ru").add(OccultismItems.DIVINATION_ROD.get().getDescriptionId() + ".message.no_linked_block", "Жезл истинного зрения не настроен на какой-либо материал.");
        this.lang("ru_ru").add(OccultismItems.DIVINATION_ROD.get().getDescriptionId() + ".message.linked_block", "Жезл прорицания настроен на %s.");
        this.lang("ru_ru").add(OccultismItems.DIVINATION_ROD.get().getDescriptionId() + ".message.no_link_found", "Нет резонанса с этим блоком.");
        this.lang("ru_ru").add(OccultismItems.TRUE_SIGHT_STAFF.get().getDescriptionId() + ".message.no_linked_block", "Посох истинного зрения не настроен на какой-либо материал.");
        this.lang("ru_ru").add(OccultismItems.TRUE_SIGHT_STAFF.get().getDescriptionId() + ".message.linked_block", "Посох истинного зрения настроен на %s.");
        this.lang("ru_ru").add(OccultismItems.TRUE_SIGHT_STAFF.get().getDescriptionId() + ".message.no_link_found", "Нет резонанса с этим блоком.");
        this.lang("ru_ru").add(OccultismItems.FRAGILE_SOUL_GEM_ITEM.get().getDescriptionId() + ".message.entity_type_denied", "Хрупкие камни души не могут содержать этот вид существа.");
        this.lang("ru_ru").add(OccultismItems.SOUL_GEM_ITEM.get().getDescriptionId() + ".message.entity_type_denied", "Камни душ не могут удерживать этот тип существа.");
        this.lang("ru_ru").add(OccultismItems.TRINITY_GEM_ITEM.get().getDescriptionId() + ".message.entity_type_denied", "Камни Троицы не могут содержать этот тип существа.");
    }

    public void addItemTooltips() {
        //"item\.occultism\.(.*?)\.(.*)": "(.*)",
        // this.add\(OccultismItems.\U\1\E.get\(\).getDescriptionId\(\)  + " \2", "\3"\);
        this.lang("ru_ru").add(OccultismItems.BOOK_OF_BINDING_EMPTY.get().getDescriptionId() + ".tooltip", "Эта книга пока не определена к какому-либо духу.");
        this.lang("ru_ru").add(OccultismItems.BOOK_OF_BINDING_FOLIOT.get().getDescriptionId() + ".tooltip", "Эта книга пока не связана с Фолиотом.");
        this.lang("ru_ru").add(OccultismItems.BOOK_OF_BINDING_BOUND_FOLIOT.get().getDescriptionId() + ".tooltip", "Может быть использована для вызова Фолиота %s");
        this.lang("ru_ru").add(OccultismItems.BOOK_OF_BINDING_DJINNI.get().getDescriptionId() + ".tooltip", "Эта книга пока не связана с Джинном.");
        this.lang("ru_ru").add(OccultismItems.BOOK_OF_BINDING_BOUND_DJINNI.get().getDescriptionId() + ".tooltip", "Может быть использована для вызова Джинна %s");
        this.lang("ru_ru").add(OccultismItems.BOOK_OF_BINDING_AFRIT.get().getDescriptionId() + ".tooltip", "Эта книга пока не связана с Афритом.");
        this.lang("ru_ru").add(OccultismItems.BOOK_OF_BINDING_BOUND_AFRIT.get().getDescriptionId() + ".tooltip", "Может быть использована для вызова Африта %s");
        this.lang("ru_ru").add(OccultismItems.BOOK_OF_BINDING_MARID.get().getDescriptionId() + ".tooltip", "Эта книга пока не связана с Маридом.");
        this.lang("ru_ru").add(OccultismItems.BOOK_OF_BINDING_BOUND_MARID.get().getDescriptionId() + ".tooltip", "Может быть использована для вызова Марида %s");

        this.lang("ru_ru").add("item.occultism.book_of_calling_foliot" + ".tooltip", "Фолиот %s");
        this.lang("ru_ru").add("item.occultism.book_of_calling_foliot" + ".tooltip_dead", "%s оставил эту параллельную реальность.");
        this.lang("ru_ru").add("item.occultism.book_of_calling_foliot" + ".tooltip.extract", "Извлекает из: %s.");
        this.lang("ru_ru").add("item.occultism.book_of_calling_foliot" + ".tooltip.deposit", "Вносит в: %s.");
        this.lang("ru_ru").add("item.occultism.book_of_calling_foliot" + ".tooltip.deposit_entity", "Передаёт предметы в: %s.");
        this.lang("ru_ru").add("item.occultism.book_of_calling_djinni" + ".tooltip", "Джинн %s");
        this.lang("ru_ru").add("item.occultism.book_of_calling_djinni" + ".tooltip_dead", "%s покинул эту параллельную реальность.");
        this.lang("ru_ru").add("item.occultism.book_of_calling_djinni" + ".tooltip.extract", "Извлекает из: %s.");
        this.lang("ru_ru").add("item.occultism.book_of_calling_djinni" + ".tooltip.deposit", "Вносит в: % s");
        this.lang("ru_ru").add(OccultismItems.FAMILIAR_RING.get().getDescriptionId() + ".tooltip", "Занят фамильяром %s\n%s");
        this.lang("ru_ru").add(OccultismItems.FAMILIAR_RING.get().getDescriptionId() + ".tooltip.familiar_type", "[Вид: %s]");
        this.lang("ru_ru").add(OccultismItems.FAMILIAR_RING.get().getDescriptionId() + ".tooltip.empty", "Не содержит какого-либо фамильяра.");

        this.lang("ru_ru").add("item.minecraft.diamond_sword.occultism_spirit_tooltip", "%s заточён в этом мече. Пусть враги трепещут перед его тщеславием.");

        this.lang("ru_ru").add(OccultismItems.STABLE_WORMHOLE.get().getDescriptionId() + ".tooltip.unlinked", "Не связана с регулятором хранилища.");
        this.lang("ru_ru").add(OccultismItems.STABLE_WORMHOLE.get().getDescriptionId() + ".tooltip.linked", "Связана с регулятором хранилища в %s.");
        this.lang("ru_ru").add(OccultismItems.STORAGE_REMOTE.get().getDescriptionId() + ".tooltip", "Удалённо получает доступ к сетевому хранилищу.");
        this.lang("ru_ru").add("block.occultism.otherglass.auto_tooltip", "Наденьте потусторонние очки, чтобы видеть стекло.");

        this.lang("ru_ru").add(OccultismItems.STORAGE_REMOTE.get().getDescriptionId() + ".tooltip.linked", "Связано с %s.");
        this.lang("ru_ru").add(OccultismItems.DIVINATION_ROD.get().getDescriptionId() + ".tooltip.no_linked_block", "Не настроен на какой-либо материал.");
        this.lang("ru_ru").add(OccultismItems.DIVINATION_ROD.get().getDescriptionId() + ".tooltip.linked_block", "Настроен на %s.");
        this.lang("ru_ru").add(OccultismItems.TRUE_SIGHT_STAFF.get().getDescriptionId() + ".tooltip.no_linked_block", "Не настроен на какой-либо материал.");
        this.lang("ru_ru").add(OccultismItems.TRUE_SIGHT_STAFF.get().getDescriptionId() + ".tooltip.linked_block", "Настроен на %s.");
        this.lang("ru_ru").add(OccultismItems.DIMENSIONAL_MATRIX.get().getDescriptionId() + ".tooltip", "%s связан с пространственной матрицей.");
        this.lang("ru_ru").add(OccultismItems.INFUSED_PICKAXE.get().getDescriptionId() + ".tooltip", "%s заточён в этой кирке.");
        this.lang("ru_ru").add(OccultismItems.MINER_FOLIOT_UNSPECIALIZED.get().getDescriptionId() + ".tooltip", "%s will mine random blocks in the mining dimension.");
        this.lang("ru_ru").add(OccultismItems.MINER_DJINNI_ORES.get().getDescriptionId() + ".tooltip", "%s will mine random ores in the mining dimension.");
        this.lang("ru_ru").add(OccultismItems.MINER_DEBUG_UNSPECIALIZED.get().getDescriptionId() + ".tooltip", "Debug Miner will mine random blocks in the mining dimension.");
        this.lang("ru_ru").add(OccultismItems.MINER_AFRIT_DEEPS.get().getDescriptionId() + ".tooltip", "%s will mine random ores and deepslate ores in the mining dimension.");
        this.lang("ru_ru").add(OccultismItems.MINER_MARID_MASTER.get().getDescriptionId() + ".tooltip", "%s will mine random ores, deepslate ores and rare ores in the mining dimension.");
        this.lang("ru_ru").add(OccultismItems.MINER_ANCIENT_ELDRITCH.get().getDescriptionId() + ".tooltip", "Something will mine random raw ores blocks, gems blocks and rare ores in the mining dimension.");
        this.lang("ru_ru").add(OccultismItems.FRAGILE_SOUL_GEM_ITEM.get().getDescriptionId() + ".tooltip_filled", "Содержит пойманного %s.\n" + ChatFormatting.RED + "Will break when release the creature!");
        this.lang("ru_ru").add(OccultismItems.FRAGILE_SOUL_GEM_ITEM.get().getDescriptionId() + ".tooltip_empty", "Используйте на существе для его поимки.\n" + ChatFormatting.RED + "Разрушается после однократного использования.");
        this.lang("ru_ru").add(OccultismItems.SOUL_GEM_ITEM.get().getDescriptionId() + ".tooltip_filled", "Содержит пойманного %s.");
        this.lang("ru_ru").add(OccultismItems.SOUL_GEM_ITEM.get().getDescriptionId() + ".tooltip_empty", "Используйте на существе для его поимки.");
        this.lang("ru_ru").add(OccultismItems.TRINITY_GEM_ITEM.get().getDescriptionId() + ".tooltip_filled", "Содержит пойманного %s.");
        this.lang("ru_ru").add(OccultismItems.TRINITY_GEM_ITEM.get().getDescriptionId() + ".tooltip_empty", "Используйте на существе для его поимки.\n" + ChatFormatting.GRAY + "Ловит боссов.");
        this.lang("ru_ru").add(OccultismItems.SATCHEL.get().getDescriptionId() + ".tooltip", "%s is bound to this satchel.");
        this.lang("ru_ru").add(OccultismItems.RITUAL_SATCHEL_T1.get().getDescriptionId() + ".tooltip", "%s is bound to this satchel.");
        this.lang("ru_ru").add(OccultismItems.RITUAL_SATCHEL_T2.get().getDescriptionId() + ".tooltip", "%s is bound to this satchel.");

        this.lang("ru_ru").add(OccultismItems.SOUL_SHARD_ITEM.get().getDescriptionId() + ".tooltip_filled", "Contains the soul of a %s.\nCan be used to resurrect it.");
        this.lang("ru_ru").add(OccultismItems.SOUL_SHARD_ITEM.get().getDescriptionId() + ".tooltip_empty", "Dropped by a Familiar after their untimely death. Can be used to resurrect it.");
    }

    private void addItems() {
        //Notepad++ magic:
        //"item\.occultism\.(.*)": "(.*)"
        //this.addItem\(OccultismItems.\U\1\E, "\2"\);

        this.add("itemGroup.occultism", "Occultism");

        this.lang("ru_ru").addItem(OccultismItems.PENTACLE_SUMMON, "Pentacle Summon");
        this.lang("ru_ru").addItem(OccultismItems.PENTACLE_POSSESS, "Pentacle Possess");
        this.lang("ru_ru").addItem(OccultismItems.PENTACLE_CRAFT, "Pentacle Craft");
        this.lang("ru_ru").addItem(OccultismItems.PENTACLE_MISC, "Pentacle Misc");
        this.lang("ru_ru").addItem(OccultismItems.REPAIR_ICON, "Repair Icon");
        this.lang("ru_ru").addItem(OccultismItems.RESURRECT_ICON, "Resurrect Icon");
        this.lang("ru_ru").addItem(OccultismItems.MYSTERIOUS_EGG_ICON, "Mysterious Egg Icon");
        this.lang("ru_ru").addItem(OccultismItems.DEBUG_WAND, "Debug Wand");
        this.lang("ru_ru").addItem(OccultismItems.DEBUG_FOLIOT_LUMBERJACK, "Summon Debug Foliot Lumberjack");
        this.lang("ru_ru").addItem(OccultismItems.DEBUG_FOLIOT_TRANSPORT_ITEMS, "Summon Debug Foliot Transporter");
        this.lang("ru_ru").addItem(OccultismItems.DEBUG_FOLIOT_CLEANER, "Summon Debug Foliot Janitor");
        this.lang("ru_ru").addItem(OccultismItems.DEBUG_FOLIOT_TRADER_ITEM, "Summon Debug Foliot Trader");
        this.lang("ru_ru").addItem(OccultismItems.DEBUG_DJINNI_MANAGE_MACHINE, "Summon Debug Djinni Manage Machine");
        this.lang("ru_ru").addItem(OccultismItems.DEBUG_DJINNI_TEST, "Summon Debug Djinni Test");
        this.lang("ru_ru").addAutoTooltip(OccultismItems.DIVINATION_ROD.get(),
                """
                        Don't see anything?
                        Check the Troubleshooting page in the Dictionary of Spirits!
                        In the "Getting Started" tab find the Divination Rod item.
                        """
        );
        this.lang("ru_ru").addItem(OccultismItems.RITUAL_SATCHEL_T1, "Apprentice Ritual Satchel");
        this.lang("ru_ru").addAutoTooltip(OccultismItems.RITUAL_SATCHEL_T1.get(),
                """
                        A basic ritual satchel that can place ritual circles block by block.
                        Right-Click a preview block to place it out of the satchel.
                        Shift-Right-Click to open the satchel and add ritual ingredients.
                        If an item inside has less than 40% of durability the glint effect will stop.
                        """
        );
        this.lang("ru_ru").addItem(OccultismItems.RITUAL_SATCHEL_T2, "Artisanal Ritual Satchel");
        this.lang("ru_ru").addAutoTooltip(OccultismItems.RITUAL_SATCHEL_T2.get(),
                """
                        An improved ritual satchel that can place an entire ritual circle at once.
                        Right-Click any preview block to place all preview blocks out of the satchel.
                        Shift-Right-Click to open the satchel and add ritual ingredients.
                        Right-Click a Golden Bowl to remove the ritual circle and collect the ingredients.
                        If an item inside has less than 40% of durability the glint effect will stop.
                        """
        );

        this.lang("ru_ru").add(TranslationKeys.RITUAL_SATCHEL_NO_PREVIEW_IN_WORLD, " You need to preview a pentacle using the Dictionary of Spirits.");
        this.lang("ru_ru").add(TranslationKeys.RITUAL_SATCHEL_NO_PREVIEW_BLOCK_TARGETED, "You need to aim the ritual satchel at a preview block.");
        this.lang("ru_ru").add(TranslationKeys.RITUAL_SATCHEL_NO_VALID_ITEM_IN_SATCHEL, "There is no valid item in the satchel for this previewed block.");
        this.lang("ru_ru").add(TranslationKeys.RITUAL_SATCHEL_BLOCK_ABOVE_NOT_AIR, "The block above the clicked position is not empty.");
        this.lang("ru_ru").add(TranslationKeys.RITUAL_SATCHEL_BLOCK_AT_POSITION_NOT_AIR, "The block at the clicked position is not empty.");
        this.lang("ru_ru").add(TranslationKeys.RITUAL_SATCHEL_INVALID_MATCHER, "Cannot place a block for an ANY or DISPLAY_ONLY multiblock matcher");

        this.lang("ru_ru").addItem(OccultismItems.CHALK_YELLOW, "Yellow Chalk");
        this.lang("ru_ru").addItem(OccultismItems.CHALK_PURPLE, "Purple Chalk");
        this.lang("ru_ru").addItem(OccultismItems.CHALK_RED, "Red Chalk");
        this.lang("ru_ru").addItem(OccultismItems.CHALK_WHITE, "White Chalk");
        this.lang("ru_ru").addItem(OccultismItems.CHALK_LIGHT_GRAY, "Light Gray Chalk");
        this.lang("ru_ru").addItem(OccultismItems.CHALK_GRAY, "Gray Chalk");
        this.lang("ru_ru").addItem(OccultismItems.CHALK_BLACK, "Black Chalk");
        this.lang("ru_ru").addItem(OccultismItems.CHALK_BROWN, "Brown Chalk");
        this.lang("ru_ru").addItem(OccultismItems.CHALK_ORANGE, "Orange Chalk");
        this.lang("ru_ru").addItem(OccultismItems.CHALK_LIME, "Lime Chalk");
        this.lang("ru_ru").addItem(OccultismItems.CHALK_GREEN, "Green Chalk");
        this.lang("ru_ru").addItem(OccultismItems.CHALK_CYAN, "Cyan Chalk");
        this.lang("ru_ru").addItem(OccultismItems.CHALK_LIGHT_BLUE, "Light Blue Chalk");
        this.lang("ru_ru").addItem(OccultismItems.CHALK_BLUE, "Blue Chalk");
        this.lang("ru_ru").addItem(OccultismItems.CHALK_MAGENTA, "Magenta Chalk");
        this.lang("ru_ru").addItem(OccultismItems.CHALK_PINK, "Pink Chalk");
        this.lang("ru_ru").addItem(OccultismItems.CHALK_RAINBOW, "Rainbow Chalk");
        this.lang("ru_ru").addItem(OccultismItems.CHALK_VOID, "Void Chalk");
        this.lang("ru_ru").addItem(OccultismItems.CHALK_YELLOW_IMPURE, "Impure Yellow Chalk");
        this.lang("ru_ru").addItem(OccultismItems.CHALK_PURPLE_IMPURE, "Impure Purple Chalk");
        this.lang("ru_ru").addItem(OccultismItems.CHALK_RED_IMPURE, "Impure Red Chalk");
        this.lang("ru_ru").addItem(OccultismItems.CHALK_WHITE_IMPURE, "Impure White Chalk");
        this.lang("ru_ru").addItem(OccultismItems.CHALK_LIGHT_GRAY_IMPURE, "Impure Light Gray Chalk");
        this.lang("ru_ru").addItem(OccultismItems.CHALK_GRAY_IMPURE, "Impure Gray Chalk");
        this.lang("ru_ru").addItem(OccultismItems.CHALK_BLACK_IMPURE, "Impure Black Chalk");
        this.lang("ru_ru").addItem(OccultismItems.CHALK_BROWN_IMPURE, "Impure Brown Chalk");
        this.lang("ru_ru").addItem(OccultismItems.CHALK_ORANGE_IMPURE, "Impure Orange Chalk");
        this.lang("ru_ru").addItem(OccultismItems.CHALK_LIME_IMPURE, "Impure Lime Chalk");
        this.lang("ru_ru").addItem(OccultismItems.CHALK_GREEN_IMPURE, "Impure Green Chalk");
        this.lang("ru_ru").addItem(OccultismItems.CHALK_CYAN_IMPURE, "Impure Cyan Chalk");
        this.lang("ru_ru").addItem(OccultismItems.CHALK_LIGHT_BLUE_IMPURE, "Impure Light Blue Chalk");
        this.lang("ru_ru").addItem(OccultismItems.CHALK_BLUE_IMPURE, "Impure Blue Chalk");
        this.lang("ru_ru").addItem(OccultismItems.CHALK_MAGENTA_IMPURE, "Impure Magenta Chalk");
        this.lang("ru_ru").addItem(OccultismItems.CHALK_PINK_IMPURE, "Impure Pink Chalk");
        this.lang("ru_ru").addItem(OccultismItems.BRUSH, "Chalk Brush");
        this.lang("ru_ru").addItem(OccultismItems.AFRIT_ESSENCE, "Afrit Essence");
        this.lang("ru_ru").addItem(OccultismItems.PURIFIED_INK, "Purified Ink");
        this.lang("ru_ru").addItem(OccultismItems.AWAKENED_FEATHER, "Awakened Feather");
        this.lang("ru_ru").addItem(OccultismItems.TABOO_BOOK, "Taboo Book");
        this.lang("ru_ru").addItem(OccultismItems.BOOK_OF_BINDING_EMPTY, "Book of Binding: Empty");
        this.lang("ru_ru").addItem(OccultismItems.BOOK_OF_BINDING_FOLIOT, "Book of Binding: Foliot");
        this.lang("ru_ru").addItem(OccultismItems.BOOK_OF_BINDING_BOUND_FOLIOT, "Book of Binding: Foliot (Bound)");
        this.lang("ru_ru").addItem(OccultismItems.BOOK_OF_BINDING_DJINNI, "Book of Binding: Djinni");
        this.lang("ru_ru").addItem(OccultismItems.BOOK_OF_BINDING_BOUND_DJINNI, "Book of Binding: Djinni (Bound)");
        this.lang("ru_ru").addItem(OccultismItems.BOOK_OF_BINDING_AFRIT, "Book of Binding: Afrit");
        this.lang("ru_ru").addItem(OccultismItems.BOOK_OF_BINDING_BOUND_AFRIT, "Book of Binding: Afrit (Bound)");
        this.lang("ru_ru").addItem(OccultismItems.BOOK_OF_BINDING_MARID, "Book of Binding: Marid");
        this.lang("ru_ru").addItem(OccultismItems.BOOK_OF_BINDING_BOUND_MARID, "Book of Binding: Marid (Bound)");
        this.lang("ru_ru").addItem(OccultismItems.BOOK_OF_CALLING_FOLIOT_LUMBERJACK, "Book of Calling: Foliot Lumberjack");
        this.lang("ru_ru").addItem(OccultismItems.BOOK_OF_CALLING_FOLIOT_TRANSPORT_ITEMS, "Book of Calling: Foliot Transporter");
        this.lang("ru_ru").addItem(OccultismItems.BOOK_OF_CALLING_FOLIOT_CLEANER, "Book of Calling: Foliot Janitor");
        this.lang("ru_ru").addItem(OccultismItems.BOOK_OF_CALLING_DJINNI_MANAGE_MACHINE, "Book of Calling: Djinni Machine Operator");
        this.lang("ru_ru").addItem(OccultismItems.STORAGE_REMOTE, "Storage Accessor");
        this.lang("ru_ru").addItem(OccultismItems.STORAGE_REMOTE_INERT, "Inert Storage Accessor");
        this.lang("ru_ru").addItem(OccultismItems.DIMENSIONAL_MATRIX, "Dimensional Crystal Matrix");
        this.lang("ru_ru").addItem(OccultismItems.DIVINATION_ROD, "Divination Rod");
        this.lang("ru_ru").addItem(OccultismItems.TRUE_SIGHT_STAFF, "True Sight Staff");
        this.lang("ru_ru").addItem(OccultismItems.DATURA_SEEDS, "Demon's Dream Seeds");
        this.lang("ru_ru").addAutoTooltip(OccultismItems.DATURA_SEEDS.get(), "Plant to grow Demon's Dream Fruit.\nConsumption may allow to see beyond the veil ... it may also cause general un-wellness.");
        this.lang("ru_ru").addItem(OccultismItems.DATURA, "Demon's Dream Fruit");
        this.lang("ru_ru").addAutoTooltip(OccultismItems.DATURA.get(), "Consumption may allow to see beyond the veil ... it may also cause general un-wellness.");
        this.lang("ru_ru").addItem(OccultismItems.DEMONS_DREAM_ESSENCE, "Demon's Dream Essence");
        this.lang("ru_ru").addAutoTooltip(OccultismItems.DEMONS_DREAM_ESSENCE.get(), "Consumption allows to see beyond the veil ... and a whole lot of other effects.");
        this.lang("ru_ru").addItem(OccultismItems.OTHERWORLD_ESSENCE, "Otherworld Essence");
        this.lang("ru_ru").addAutoTooltip(OccultismItems.OTHERWORLD_ESSENCE.get(), "Purified Demon's Dream Essence, no longer provides any of the negative effects.");
        this.lang("ru_ru").addItem(OccultismItems.BEAVER_NUGGET, "Beaver Nugget");
        this.lang("ru_ru").addItem(OccultismItems.SPIRIT_ATTUNED_GEM, "Spirit Attuned Gem");
        this.lang("ru_ru").add("item.occultism.otherworld_sapling", "Otherworld Sapling");
        this.lang("ru_ru").add("item.occultism.otherworld_sapling_natural", "Unstable Otherworld Sapling");
        this.lang("ru_ru").addItem(OccultismItems.OTHERWORLD_ASHES, "Otherworld Ashes");
        this.lang("ru_ru").addItem(OccultismItems.BURNT_OTHERSTONE, "Burnt Otherstone");
        this.lang("ru_ru").addItem(OccultismItems.BUTCHER_KNIFE, "Butcher Knife");
        this.lang("ru_ru").addItem(OccultismItems.TALLOW, "Tallow");
        this.lang("ru_ru").addItem(OccultismItems.OTHERSTONE_FRAME, "Otherstone Frame");
        this.lang("ru_ru").addItem(OccultismItems.OTHERSTONE_TABLET, "Otherstone Tablet");
        this.lang("ru_ru").addItem(OccultismItems.WORMHOLE_FRAME, "Wormhole Frame");
        this.lang("ru_ru").addItem(OccultismItems.IRON_DUST, "Iron Dust");
        this.lang("ru_ru").addItem(OccultismItems.OBSIDIAN_DUST, "Obsidian Dust");
        this.lang("ru_ru").addItem(OccultismItems.CRUSHED_END_STONE, "Crushed End Stone");
        this.lang("ru_ru").addItem(OccultismItems.GOLD_DUST, "Gold Dust");
        this.lang("ru_ru").addItem(OccultismItems.COPPER_DUST, "Copper Dust");
        this.lang("ru_ru").addItem(OccultismItems.SILVER_DUST, "Silver Dust");
        this.lang("ru_ru").addItem(OccultismItems.IESNIUM_DUST, "Iesnium Dust");
        this.lang("ru_ru").addItem(OccultismItems.RAW_SILVER, "Raw Silver");
        this.lang("ru_ru").addItem(OccultismItems.RAW_IESNIUM, "Raw Iesnium");
        this.lang("ru_ru").addItem(OccultismItems.SILVER_INGOT, "Silver Ingot");
        this.lang("ru_ru").addItem(OccultismItems.IESNIUM_INGOT, "Iesnium Ingot");
        this.lang("ru_ru").addItem(OccultismItems.SILVER_NUGGET, "Silver Nugget");
        this.lang("ru_ru").addItem(OccultismItems.IESNIUM_NUGGET, "Iesnium Nugget");
        this.lang("ru_ru").addItem(OccultismItems.LENSES, "Glass Lenses");
        this.lang("ru_ru").addItem(OccultismItems.INFUSED_LENSES, "Infused Lenses");
        this.lang("ru_ru").addItem(OccultismItems.LENS_FRAME, "Lens Frame");
        this.lang("ru_ru").addItem(OccultismItems.OTHERWORLD_GOGGLES, "Otherworld Goggles");
        this.lang("ru_ru").addItem(OccultismItems.INFUSED_PICKAXE, "Infused Pickaxe");
        this.lang("ru_ru").addItem(OccultismItems.SPIRIT_ATTUNED_PICKAXE_HEAD, "Spirit Attuned Pickaxe Head");
        this.lang("ru_ru").addItem(OccultismItems.IESNIUM_PICKAXE, "Iesnium Pickaxe");
        this.lang("ru_ru").addItem(OccultismItems.MAGIC_LAMP_EMPTY, "Empty Magic Lamp");
        this.lang("ru_ru").addItem(OccultismItems.MINER_FOLIOT_UNSPECIALIZED, "Miner Foliot");
        this.lang("ru_ru").addItem(OccultismItems.MINER_DJINNI_ORES, "Ore Miner Djinni");
        this.lang("ru_ru").addItem(OccultismItems.MINER_DEBUG_UNSPECIALIZED, "Debug Miner");
        this.lang("ru_ru").addItem(OccultismItems.MINER_AFRIT_DEEPS, "Deep Ore Miner Afrit");
        this.lang("ru_ru").addItem(OccultismItems.MINER_MARID_MASTER, "Master Miner Marid");
        this.lang("ru_ru").addItem(OccultismItems.MINER_ANCIENT_ELDRITCH, "Eldritch Ancient Miner");
        this.lang("ru_ru").addItem(OccultismItems.MINING_DIMENSION_CORE_PIECE, "Mining Dimension Core Piece");
        this.lang("ru_ru").addItem(OccultismItems.FRAGILE_SOUL_GEM_ITEM, "Fragile Soul Gem");
        this.lang("ru_ru").add(OccultismItems.FRAGILE_SOUL_GEM_ITEM.get().getDescriptionId() + "_empty", "Хрупкий камень души (пустой)");
        this.lang("ru_ru").addItem(OccultismItems.SOUL_GEM_ITEM, "Камень души");
        this.lang("ru_ru").add(OccultismItems.SOUL_GEM_ITEM.get().getDescriptionId() + "_empty", "Пустой камень души");
        this.lang("ru_ru").addItem(OccultismItems.TRINITY_GEM_ITEM, "Trinity Gem");
        this.lang("ru_ru").add(OccultismItems.TRINITY_GEM_ITEM.get().getDescriptionId() + "_empty", "Пустой камень Троицы");
        this.lang("ru_ru").addItem(OccultismItems.SOUL_SHARD_ITEM, "Soul Shard");
        this.lang("ru_ru").addItem(OccultismItems.SATCHEL, "Surprisingly Substantial Satchel");
        this.lang("ru_ru").addItem(OccultismItems.FAMILIAR_RING, "Familiar Ring");
        this.lang("ru_ru").addItem(OccultismItems.SPAWN_EGG_FOLIOT, "Foliot Spawn Egg");
        this.lang("ru_ru").addItem(OccultismItems.SPAWN_EGG_DJINNI, "Djinni Spawn Egg");
        this.lang("ru_ru").addItem(OccultismItems.SPAWN_EGG_AFRIT, "Afrit Spawn Egg");
        this.lang("ru_ru").addItem(OccultismItems.SPAWN_EGG_AFRIT_UNBOUND, "Unbound Afrit Spawn Egg");
        this.lang("ru_ru").addItem(OccultismItems.SPAWN_EGG_MARID, "Marid Spawn Egg");
        this.lang("ru_ru").addItem(OccultismItems.SPAWN_EGG_MARID_UNBOUND, "Unbound Marid Spawn Egg");
        this.lang("ru_ru").addItem(OccultismItems.SPAWN_EGG_POSSESSED_ENDERMITE, "Possessed Endermite Spawn Egg");
        this.lang("ru_ru").addItem(OccultismItems.SPAWN_EGG_POSSESSED_SKELETON, "Possessed Skeleton Spawn Egg");
        this.lang("ru_ru").addItem(OccultismItems.SPAWN_EGG_POSSESSED_ENDERMAN, "Possessed Enderman Spawn Egg");
        this.lang("ru_ru").addItem(OccultismItems.SPAWN_EGG_POSSESSED_GHAST, "Possessed Ghast Spawn Egg");
        this.lang("ru_ru").addItem(OccultismItems.SPAWN_EGG_POSSESSED_PHANTOM, "Possessed Phantom Spawn Egg");
        this.lang("ru_ru").addItem(OccultismItems.SPAWN_EGG_POSSESSED_WEAK_SHULKER, "Possessed Weak Shulker Spawn Egg");
        this.lang("ru_ru").addItem(OccultismItems.SPAWN_EGG_POSSESSED_SHULKER, "Possessed Shulker Spawn Egg");
        this.lang("ru_ru").addItem(OccultismItems.SPAWN_EGG_POSSESSED_ELDER_GUARDIAN, "Possessed Elder Guardian Spawn Egg");
        this.lang("ru_ru").addItem(OccultismItems.SPAWN_EGG_POSSESSED_WARDEN, "Possessed Warden Spawn Egg");
        this.lang("ru_ru").addItem(OccultismItems.SPAWN_EGG_POSSESSED_HOGLIN, "Possessed Hoglin Spawn Egg");
        this.lang("ru_ru").addItem(OccultismItems.SPAWN_EGG_POSSESSED_WITCH, "Possessed Witch Spawn Egg");
        this.lang("ru_ru").addItem(OccultismItems.SPAWN_EGG_POSSESSED_ZOMBIE_PIGLIN, "Possessed Zombified Piglin Spawn Egg");
        this.lang("ru_ru").addItem(OccultismItems.SPAWN_EGG_POSSESSED_BEE, "Possessed Bee Spawn Egg");
        this.lang("ru_ru").addItem(OccultismItems.SPAWN_EGG_GOAT_OF_MERCY, "Goat of Mercy Spawn Egg");
        this.lang("ru_ru").addItem(OccultismItems.SPAWN_EGG_WILD_HUNT_SKELETON, "Wild Hunt Skeleton Spawn Egg");
        this.lang("ru_ru").addItem(OccultismItems.SPAWN_EGG_WILD_HUNT_WITHER_SKELETON, "Wild Hunt Wither Skeleton Spawn Egg");
        this.lang("ru_ru").addItem(OccultismItems.SPAWN_EGG_OTHERWORLD_BIRD, "Drikwing Spawn Egg");
        this.lang("ru_ru").addItem(OccultismItems.SPAWN_EGG_GREEDY_FAMILIAR, "Greedy Familiar Spawn Egg");
        this.lang("ru_ru").addItem(OccultismItems.SPAWN_EGG_BAT_FAMILIAR, "Bat Familiar Spawn Egg");
        this.lang("ru_ru").addItem(OccultismItems.SPAWN_EGG_DEER_FAMILIAR, "Deer Familiar Spawn Egg");
        this.lang("ru_ru").addItem(OccultismItems.SPAWN_EGG_CTHULHU_FAMILIAR, "Cthulhu Familiar Spawn Egg");
        this.lang("ru_ru").addItem(OccultismItems.SPAWN_EGG_DEVIL_FAMILIAR, "Devil Familiar Spawn Egg");
        this.lang("ru_ru").addItem(OccultismItems.SPAWN_EGG_DRAGON_FAMILIAR, "Dragon Familiar Spawn Egg");
        this.lang("ru_ru").addItem(OccultismItems.SPAWN_EGG_BLACKSMITH_FAMILIAR, "Blacksmith Familiar Spawn Egg");
        this.lang("ru_ru").addItem(OccultismItems.SPAWN_EGG_GUARDIAN_FAMILIAR, "Guardian Familiar Spawn Egg");
        this.lang("ru_ru").addItem(OccultismItems.SPAWN_EGG_HEADLESS_FAMILIAR, "Headless Ratman Familiar Spawn Egg");
        this.lang("ru_ru").addItem(OccultismItems.SPAWN_EGG_CHIMERA_FAMILIAR, "Chimera Familiar Spawn Egg");
        this.lang("ru_ru").addItem(OccultismItems.SPAWN_EGG_GOAT_FAMILIAR, "Goat Familiar Spawn Egg");
        this.lang("ru_ru").addItem(OccultismItems.SPAWN_EGG_SHUB_NIGGURATH_FAMILIAR, "Shub Niggurath Familiar Spawn Egg");
        this.lang("ru_ru").addItem(OccultismItems.SPAWN_EGG_BEHOLDER_FAMILIAR, "Beholder Familiar Spawn Egg");
        this.lang("ru_ru").addItem(OccultismItems.SPAWN_EGG_FAIRY_FAMILIAR, "Fairy Familiar Spawn Egg");
        this.lang("ru_ru").addItem(OccultismItems.SPAWN_EGG_MUMMY_FAMILIAR, "Mummy Familiar Spawn Egg");
        this.lang("ru_ru").addItem(OccultismItems.SPAWN_EGG_BEAVER_FAMILIAR, "Beaver Familiar Spawn Egg");
        this.lang("ru_ru").addItem(OccultismItems.SPAWN_EGG_PARROT_FAMILIAR, "Parrot Familiar Spawn Egg");
        this.lang("ru_ru").addItem(OccultismItems.SPAWN_EGG_DEMONIC_WIFE, "Demonic Wife Spawn Egg");
        this.lang("ru_ru").addItem(OccultismItems.SPAWN_EGG_DEMONIC_HUSBAND, "Demonic Husband Spawn Egg");
        this.lang("ru_ru").addItem(OccultismItems.SPAWN_EGG_IESNIUM_GOLEM, "Iesnium Golem Spawn Egg");
        this.lang("ru_ru").addItem(OccultismItems.SPAWN_EGG_WILD_HORDE_HUSK, "Wild Horde Husk Spawn Egg");
        this.lang("ru_ru").addItem(OccultismItems.SPAWN_EGG_WILD_HORDE_DROWNED, "Wild Horde Drowned Spawn Egg");
        this.lang("ru_ru").addItem(OccultismItems.SPAWN_EGG_WILD_HORDE_CREEPER, "Wild Horde Creeper Spawn Egg");
        this.lang("ru_ru").addItem(OccultismItems.SPAWN_EGG_WILD_HORDE_SILVERFISH, "Wild Horde Silverfish Spawn Egg");
        this.lang("ru_ru").addItem(OccultismItems.SPAWN_EGG_WILD_WEAK_BREEZE, "Wild Weak Breeze Spawn Egg");
        this.lang("ru_ru").addItem(OccultismItems.SPAWN_EGG_WILD_BREEZE, "Wild Breeze Spawn Egg");
        this.lang("ru_ru").addItem(OccultismItems.SPAWN_EGG_WILD_STRONG_BREEZE, "Wild Strong Breeze Spawn Egg");
        this.lang("ru_ru").addItem(OccultismItems.SPAWN_EGG_WILD_EVOKER, "Wild Evoker Spawn Egg");
        //Pentacle Rework Update
        this.lang("ru_ru").addItem(OccultismItems.AMETHYST_DUST, "Amethyst Dust");
        this.lang("ru_ru").addItem(OccultismItems.CRUELTY_ESSENCE, "Cruelty Essence");
        this.lang("ru_ru").addItem(OccultismItems.CRUSHED_BLACKSTONE, "Crushed Blackstone");
        this.lang("ru_ru").addItem(OccultismItems.CRUSHED_BLUE_ICE, "Crushed Blue Ice");
        this.lang("ru_ru").addItem(OccultismItems.CRUSHED_CALCITE, "Crushed Calcite");
        this.lang("ru_ru").addItem(OccultismItems.CRUSHED_ICE, "Crushed Ice");
        this.lang("ru_ru").addItem(OccultismItems.CRUSHED_PACKED_ICE, "Crushed Packed Ice");
        this.lang("ru_ru").addItem(OccultismItems.CURSED_HONEY, "Cursed Honey");
        this.lang("ru_ru").addItem(OccultismItems.DEMONIC_MEAT, "Demonic Meat");
        this.lang("ru_ru").addItem(OccultismItems.DRAGONYST_DUST, "Dragonyst Dust");
        this.lang("ru_ru").addItem(OccultismItems.ECHO_DUST, "Echo Dust");
        this.lang("ru_ru").addItem(OccultismItems.EMERALD_DUST, "Emerald Dust");
        this.lang("ru_ru").addItem(OccultismItems.GRAY_PASTE, "Gray Paste");
        this.lang("ru_ru").addItem(OccultismItems.LAPIS_DUST, "Lapis Dust");
        this.lang("ru_ru").addItem(OccultismItems.MARID_ESSENCE, "Marid Essence");
        this.lang("ru_ru").addItem(OccultismItems.NATURE_PASTE, "Nature Paste");
        this.lang("ru_ru").addItem(OccultismItems.NETHERITE_DUST, "Netherite Dust");
        this.lang("ru_ru").addItem(OccultismItems.NETHERITE_SCRAP_DUST, "Netherite Scrap Dust");
        this.lang("ru_ru").addItem(OccultismItems.RESEARCH_FRAGMENT_DUST, "Research Fragment Dust");
        this.lang("ru_ru").addItem(OccultismItems.WITHERITE_DUST, "Witherite Dust");
    }

    private void addBlocks() {
        //"block\.occultism\.(.*?)": "(.*)",
        //this.addBlock\(OccultismItems.\U\1\E, "\2"\);
        this.lang("ru_ru").addBlock(OccultismBlocks.OTHERGLASS, "Otherglass");
        this.lang("ru_ru").addBlock(OccultismBlocks.OTHERSTONE, "Otherstone");
        this.lang("ru_ru").addBlock(OccultismBlocks.OTHERSTONE_STAIRS, "Otherstone Stairs");
        this.lang("ru_ru").addBlock(OccultismBlocks.OTHERSTONE_SLAB, "Otherstone Slab");
        this.lang("ru_ru").addBlock(OccultismBlocks.OTHERSTONE_PRESSURE_PLATE, "Otherstone Pressure Plate");
        this.lang("ru_ru").addBlock(OccultismBlocks.OTHERSTONE_BUTTON, "Otherstone Button");
        this.lang("ru_ru").addBlock(OccultismBlocks.OTHERSTONE_WALL, "Otherstone Wall");
        this.lang("ru_ru").addBlock(OccultismBlocks.OTHERCOBBLESTONE, "Othercobblestone");
        this.lang("ru_ru").addBlock(OccultismBlocks.OTHERCOBBLESTONE_STAIRS, "Othercobblestone Stairs");
        this.lang("ru_ru").addBlock(OccultismBlocks.OTHERCOBBLESTONE_SLAB, "Othercobblestone Slab");
        this.lang("ru_ru").addBlock(OccultismBlocks.OTHERCOBBLESTONE_WALL, "Othercobblestone Wall");
        this.lang("ru_ru").addBlock(OccultismBlocks.POLISHED_OTHERSTONE, "Polished Otherstone");
        this.lang("ru_ru").addBlock(OccultismBlocks.POLISHED_OTHERSTONE_STAIRS, "Polished Otherstone Stairs");
        this.lang("ru_ru").addBlock(OccultismBlocks.POLISHED_OTHERSTONE_SLAB, "Polished Otherstone Slab");
        this.lang("ru_ru").addBlock(OccultismBlocks.POLISHED_OTHERSTONE_WALL, "Polished Otherstone Wall");
        this.lang("ru_ru").addBlock(OccultismBlocks.OTHERSTONE_BRICKS, "Otherstone Bricks");
        this.lang("ru_ru").addBlock(OccultismBlocks.OTHERSTONE_BRICKS_STAIRS, "Otherstone Bricks Stairs");
        this.lang("ru_ru").addBlock(OccultismBlocks.OTHERSTONE_BRICKS_SLAB, "Otherstone Bricks Slab");
        this.lang("ru_ru").addBlock(OccultismBlocks.OTHERSTONE_BRICKS_WALL, "Otherstone Bricks Wall");
        this.lang("ru_ru").addBlock(OccultismBlocks.CHISELED_OTHERSTONE_BRICKS, "Chiseled Otherstone Bricks");
        this.lang("ru_ru").addBlock(OccultismBlocks.CRACKED_OTHERSTONE_BRICKS, "Cracked Otherstone Bricks");
        this.lang("ru_ru").addBlock(OccultismBlocks.OTHERSTONE_PEDESTAL, "Otherstone Pedestal");
        this.lang("ru_ru").addBlock(OccultismBlocks.OTHERSTONE_PEDESTAL_SILVER, "Silver Otherstone Pedestal");
        this.lang("ru_ru").addBlock(OccultismBlocks.SACRIFICIAL_BOWL, "Sacrificial Bowl");
        this.lang("ru_ru").addBlock(OccultismBlocks.COPPER_SACRIFICIAL_BOWL, "Copper Sacrificial Bowl");
        this.lang("ru_ru").addBlock(OccultismBlocks.SILVER_SACRIFICIAL_BOWL, "Silver Sacrificial Bowl");
        this.lang("ru_ru").addBlock(OccultismBlocks.GOLDEN_SACRIFICIAL_BOWL, "Golden Sacrificial Bowl");
        this.lang("ru_ru").addBlock(OccultismBlocks.CHALK_GLYPH_WHITE, "White Chalk Glyph");
        this.lang("ru_ru").addBlock(OccultismBlocks.CHALK_GLYPH_YELLOW, "Yellow Chalk Glyph");
        this.lang("ru_ru").addBlock(OccultismBlocks.CHALK_GLYPH_PURPLE, "Purple Chalk Glyph");
        this.lang("ru_ru").addBlock(OccultismBlocks.CHALK_GLYPH_RED, "Red Chalk Glyph");
        this.lang("ru_ru").addBlock(OccultismBlocks.CHALK_GLYPH_LIGHT_GRAY, "Light Gray Chalk Glyph");
        this.lang("ru_ru").addBlock(OccultismBlocks.CHALK_GLYPH_GRAY, "Gray Chalk Glyph");
        this.lang("ru_ru").addBlock(OccultismBlocks.CHALK_GLYPH_BLACK, "Black Chalk Glyph");
        this.lang("ru_ru").addBlock(OccultismBlocks.CHALK_GLYPH_BROWN, "Brown Chalk Glyph");
        this.lang("ru_ru").addBlock(OccultismBlocks.CHALK_GLYPH_ORANGE, "Orange Chalk Glyph");
        this.lang("ru_ru").addBlock(OccultismBlocks.CHALK_GLYPH_LIME, "Lime Chalk Glyph");
        this.lang("ru_ru").addBlock(OccultismBlocks.CHALK_GLYPH_GREEN, "Green Chalk Glyph");
        this.lang("ru_ru").addBlock(OccultismBlocks.CHALK_GLYPH_CYAN, "Cyan Chalk Glyph");
        this.lang("ru_ru").addBlock(OccultismBlocks.CHALK_GLYPH_LIGHT_BLUE, "Light Blue Chalk Glyph");
        this.lang("ru_ru").addBlock(OccultismBlocks.CHALK_GLYPH_BLUE, "Blue Chalk Glyph");
        this.lang("ru_ru").addBlock(OccultismBlocks.CHALK_GLYPH_MAGENTA, "Magenta Chalk Glyph");
        this.lang("ru_ru").addBlock(OccultismBlocks.CHALK_GLYPH_PINK, "Pink Chalk Glyph");
        this.lang("ru_ru").addBlock(OccultismBlocks.CHALK_GLYPH_RAINBOW, "Rainbow Chalk Glyph");
        this.lang("ru_ru").addBlock(OccultismBlocks.CHALK_GLYPH_VOID, "Void Chalk Glyph");
        this.lang("ru_ru").addBlock(OccultismBlocks.STORAGE_CONTROLLER, "Dimensional Storage Actuator");
        this.lang("ru_ru").addBlock(OccultismBlocks.STORAGE_CONTROLLER_STABILIZED, "Stabilized Dimensional Storage Actuator");
        this.lang("ru_ru").addBlock(OccultismBlocks.STORAGE_CONTROLLER_BASE, "Storage Actuator Base");
        this.lang("ru_ru").addBlock(OccultismBlocks.STORAGE_STABILIZER_TIER0, "Dimensional Storage Stabilizer Base");
        this.lang("ru_ru").addBlock(OccultismBlocks.STORAGE_STABILIZER_TIER1, "Tier 1 Dimensional Storage Stabilizer");
        this.lang("ru_ru").addBlock(OccultismBlocks.STORAGE_STABILIZER_TIER2, "Tier 2 Dimensional Storage Stabilizer");
        this.lang("ru_ru").addBlock(OccultismBlocks.STORAGE_STABILIZER_TIER3, "Tier 3 Dimensional Storage Stabilizer");
        this.lang("ru_ru").addBlock(OccultismBlocks.STORAGE_STABILIZER_TIER4, "Tier 4 Dimensional Storage Stabilizer");
        this.lang("ru_ru").addBlock(OccultismBlocks.STABLE_WORMHOLE, "Stable Wormhole");
        this.lang("ru_ru").addBlock(OccultismBlocks.DATURA, "Demon's Dream");
        this.lang("ru_ru").addBlock(OccultismBlocks.OTHERFLOWER, "Otherflower");
        this.lang("ru_ru").addBlock(OccultismBlocks.OTHERWORLD_SAPLING, "Otherworld Sapling");
        this.lang("ru_ru").addBlock(OccultismBlocks.OTHERWORLD_LEAVES, "Otherworld Leaves");
        this.lang("ru_ru").addBlock(OccultismBlocks.OTHERWORLD_LOG, "Otherworld Log");
        this.lang("ru_ru").addBlock(OccultismBlocks.OTHERWORLD_WOOD, "Otherworld Wood");
        this.lang("ru_ru").addBlock(OccultismBlocks.STRIPPED_OTHERWORLD_LOG, "Stripped Otherworld Log");
        this.lang("ru_ru").addBlock(OccultismBlocks.STRIPPED_OTHERWORLD_WOOD, "Stripped Otherworld Wood");
        this.lang("ru_ru").addBlock(OccultismBlocks.OTHERPLANKS, "Otherplanks");
        this.lang("ru_ru").addBlock(OccultismBlocks.OTHERPLANKS_STAIRS, "Otherplanks Stairs");
        this.lang("ru_ru").addBlock(OccultismBlocks.OTHERPLANKS_SLAB, "Otherplanks Slab");
        this.lang("ru_ru").addBlock(OccultismBlocks.OTHERPLANKS_FENCE, "Otherplanks Fence");
        this.lang("ru_ru").addBlock(OccultismBlocks.OTHERPLANKS_FENCE_GATE, "Otherplanks Fence Gate");
        this.lang("ru_ru").addBlock(OccultismBlocks.OTHERPLANKS_DOOR, "Otherplanks Door");
        this.lang("ru_ru").addBlock(OccultismBlocks.OTHERPLANKS_TRAPDOOR, "Otherplanks Trapdoor");
        this.lang("ru_ru").addBlock(OccultismBlocks.OTHERPLANKS_PRESSURE_PLATE, "Otherplanks Pressure Plate");
        this.lang("ru_ru").addBlock(OccultismBlocks.OTHERPLANKS_BUTTON, "Otherplanks Button");
        this.lang("ru_ru").addBlock(OccultismBlocks.OTHERPLANKS_SIGN, "Otherplanks Sign");
        this.lang("ru_ru").addBlock(OccultismBlocks.OTHERPLANKS_HANGING_SIGN, "Otherplanks Hanging Sign");
        this.lang("ru_ru").addBlock(OccultismBlocks.TALLOW_BLOCK, "Tallow Block");
        this.lang("ru_ru").addBlock(OccultismBlocks.SPIRIT_FIRE, "Spiritfire");
        this.lang("ru_ru").addBlock(OccultismBlocks.SPIRIT_ATTUNED_CRYSTAL, "Spirit Attuned Crystal");
        this.lang("ru_ru").addBlock(OccultismBlocks.LARGE_CANDLE, "Large Candle");
        this.lang("ru_ru").addBlock(OccultismBlocks.LARGE_CANDLE_WHITE, "Large White Candle");
        this.lang("ru_ru").addBlock(OccultismBlocks.LARGE_CANDLE_LIGHT_GRAY, "Large Light Gray Candle");
        this.lang("ru_ru").addBlock(OccultismBlocks.LARGE_CANDLE_GRAY, "Large Gray Candle");
        this.lang("ru_ru").addBlock(OccultismBlocks.LARGE_CANDLE_BLACK, "Large Black Candle");
        this.lang("ru_ru").addBlock(OccultismBlocks.LARGE_CANDLE_BROWN, "Large Brown Candle");
        this.lang("ru_ru").addBlock(OccultismBlocks.LARGE_CANDLE_RED, "Large Red Candle");
        this.lang("ru_ru").addBlock(OccultismBlocks.LARGE_CANDLE_ORANGE, "Large Orange Candle");
        this.lang("ru_ru").addBlock(OccultismBlocks.LARGE_CANDLE_YELLOW, "Large Yellow Candle");
        this.lang("ru_ru").addBlock(OccultismBlocks.LARGE_CANDLE_LIME, "Large Lime Candle");
        this.lang("ru_ru").addBlock(OccultismBlocks.LARGE_CANDLE_GREEN, "Large Green Candle");
        this.lang("ru_ru").addBlock(OccultismBlocks.LARGE_CANDLE_CYAN, "Large Cyan Candle");
        this.lang("ru_ru").addBlock(OccultismBlocks.LARGE_CANDLE_BLUE, "Large Blue Candle");
        this.lang("ru_ru").addBlock(OccultismBlocks.LARGE_CANDLE_LIGHT_BLUE, "Large Light Blue Candle");
        this.lang("ru_ru").addBlock(OccultismBlocks.LARGE_CANDLE_PINK, "Large Pink Candle");
        this.lang("ru_ru").addBlock(OccultismBlocks.LARGE_CANDLE_MAGENTA, "Large Magenta Candle");
        this.lang("ru_ru").addBlock(OccultismBlocks.LARGE_CANDLE_PURPLE, "Large Purple Candle");
        this.lang("ru_ru").addBlock(OccultismBlocks.SILVER_ORE, "Silver Ore");
        this.lang("ru_ru").addBlock(OccultismBlocks.SILVER_ORE_DEEPSLATE, "Deepslate Silver Ore");
        this.lang("ru_ru").addBlock(OccultismBlocks.IESNIUM_ANVIL, "Iesnium Anvil");
        this.lang("ru_ru").addBlock(OccultismBlocks.IESNIUM_ORE, "Iesnium Ore");
        this.lang("ru_ru").addBlock(OccultismBlocks.SILVER_BLOCK, "Block of Silver");
        this.lang("ru_ru").addBlock(OccultismBlocks.IESNIUM_BLOCK, "Block of Iesnium");
        this.lang("ru_ru").addBlock(OccultismBlocks.IESNIUM_SACRIFICIAL_BOWL, "Iesnium Sacrificial Bowl");
        this.lang("ru_ru").addBlock(OccultismBlocks.RAW_SILVER_BLOCK, "Block of Raw Silver");
        this.lang("ru_ru").addBlock(OccultismBlocks.RAW_IESNIUM_BLOCK, "Block of Raw Iesnium");
        this.lang("ru_ru").addBlock(OccultismBlocks.DIMENSIONAL_MINESHAFT, "Dimensional Mineshaft");
        this.lang("ru_ru").addBlock(OccultismBlocks.SKELETON_SKULL_DUMMY, "Skeleton Skull");
        this.lang("ru_ru").addBlock(OccultismBlocks.WITHER_SKELETON_SKULL_DUMMY, "Wither Skeleton Skull");
        this.lang("ru_ru").addBlock(OccultismBlocks.LIGHTED_AIR, "Lighted Air");
        this.lang("ru_ru").addBlock(OccultismBlocks.SPIRIT_LANTERN, "Spirit Lantern");
        this.lang("ru_ru").addBlock(OccultismBlocks.SPIRIT_CAMPFIRE, "Spirit Campfire");
        this.lang("ru_ru").addBlock(OccultismBlocks.SPIRIT_TORCH, "Spirit Torch"); //spirit wall torch automatically uses the same translation
        this.lang("ru_ru").addBlock(OccultismBlocks.ELDRITCH_CHALICE, "Eldritch Chalice");
    }

    private void addEntities() {
        //"entity\.occultism\.(.*?)": "(.*)",
        //this.addEntityType\(OccultismEntities.\U\1\E, "\2"\);

        this.lang("ru_ru").addEntityType(OccultismEntities.FOLIOT, "Foliot");
        this.lang("ru_ru").addEntityType(OccultismEntities.DJINNI, "Djinni");
        this.lang("ru_ru").addEntityType(OccultismEntities.AFRIT, "Afrit");
        this.lang("ru_ru").addEntityType(OccultismEntities.AFRIT_WILD, "Unbound Afrit");
        this.lang("ru_ru").addEntityType(OccultismEntities.MARID, "Marid");
        this.lang("ru_ru").addEntityType(OccultismEntities.MARID_UNBOUND, "Unbound Marid");
        this.lang("ru_ru").addEntityType(OccultismEntities.POSSESSED_ENDERMITE, "Possessed Endermite");
        this.lang("ru_ru").addEntityType(OccultismEntities.POSSESSED_SKELETON, "Possessed Skeleton");
        this.lang("ru_ru").addEntityType(OccultismEntities.POSSESSED_ENDERMAN, "Possessed Enderman");
        this.lang("ru_ru").addEntityType(OccultismEntities.POSSESSED_GHAST, "Possessed Ghast");
        this.lang("ru_ru").addEntityType(OccultismEntities.POSSESSED_PHANTOM, "Possessed Phantom");
        this.lang("ru_ru").addEntityType(OccultismEntities.POSSESSED_WEAK_SHULKER, "Possessed Weak Shulker");
        this.lang("ru_ru").addEntityType(OccultismEntities.POSSESSED_SHULKER, "Possessed Shulker");
        this.lang("ru_ru").addEntityType(OccultismEntities.POSSESSED_ELDER_GUARDIAN, "Possessed Elder Guardian");
        this.lang("ru_ru").addEntityType(OccultismEntities.POSSESSED_WARDEN, "Possessed Warden");
        this.lang("ru_ru").addEntityType(OccultismEntities.POSSESSED_HOGLIN, "Possessed Hoglin");
        this.lang("ru_ru").addEntityType(OccultismEntities.POSSESSED_WITCH, "Possessed Witch");
        this.lang("ru_ru").addEntityType(OccultismEntities.POSSESSED_ZOMBIE_PIGLIN, "Possessed Zombified Piglin");
        this.lang("ru_ru").addEntityType(OccultismEntities.POSSESSED_BEE, "Possessed Bee");
        this.lang("ru_ru").addEntityType(OccultismEntities.GOAT_OF_MERCY, "Goat of Mercy");
        this.lang("ru_ru").addEntityType(OccultismEntities.WILD_HUNT_SKELETON, "Wild Hunt Skeleton");
        this.lang("ru_ru").addEntityType(OccultismEntities.WILD_HUNT_WITHER_SKELETON, "Wild Hunt Wither Skeleton");
        this.lang("ru_ru").addEntityType(OccultismEntities.OTHERWORLD_BIRD, "Drikwing");
        this.lang("ru_ru").addEntityType(OccultismEntities.GREEDY_FAMILIAR, "Greedy Familiar");
        this.lang("ru_ru").addEntityType(OccultismEntities.BAT_FAMILIAR, "Bat Familiar");
        this.lang("ru_ru").addEntityType(OccultismEntities.DEER_FAMILIAR, "Deer Familiar");
        this.lang("ru_ru").addEntityType(OccultismEntities.CTHULHU_FAMILIAR, "Cthulhu Familiar");
        this.lang("ru_ru").addEntityType(OccultismEntities.DEVIL_FAMILIAR, "Devil Familiar");
        this.lang("ru_ru").addEntityType(OccultismEntities.DRAGON_FAMILIAR, "Dragon Familiar");
        this.lang("ru_ru").addEntityType(OccultismEntities.BLACKSMITH_FAMILIAR, "Blacksmith Familiar");
        this.lang("ru_ru").addEntityType(OccultismEntities.GUARDIAN_FAMILIAR, "Guardian Familiar");
        this.lang("ru_ru").addEntityType(OccultismEntities.HEADLESS_FAMILIAR, "Headless Familiar");
        this.lang("ru_ru").addEntityType(OccultismEntities.CHIMERA_FAMILIAR, "Chimera Familiar");
        this.lang("ru_ru").addEntityType(OccultismEntities.GOAT_FAMILIAR, "Goat Familiar");
        this.lang("ru_ru").addEntityType(OccultismEntities.SHUB_NIGGURATH_FAMILIAR, "Shub Niggurath Familiar");
        this.lang("ru_ru").addEntityType(OccultismEntities.BEHOLDER_FAMILIAR, "Beholder Familiar");
        this.lang("ru_ru").addEntityType(OccultismEntities.FAIRY_FAMILIAR, "Fairy Familiar");
        this.lang("ru_ru").addEntityType(OccultismEntities.MUMMY_FAMILIAR, "Mummy Familiar");
        this.lang("ru_ru").addEntityType(OccultismEntities.BEAVER_FAMILIAR, "Beaver Familiar");
        this.lang("ru_ru").addEntityType(OccultismEntities.SHUB_NIGGURATH_SPAWN, "Shub Niggurath Spawn");
        this.lang("ru_ru").addEntityType(OccultismEntities.THROWN_SWORD, "Thrown Sword");
        this.lang("ru_ru").addEntityType(OccultismEntities.DEMONIC_WIFE, "Demonic Wife");
        this.lang("ru_ru").addEntityType(OccultismEntities.DEMONIC_HUSBAND, "Demonic Husband");
        this.lang("ru_ru").addEntityType(OccultismEntities.IESNIUM_GOLEM, "Iesnium Golem");
        this.lang("ru_ru").addEntityType(OccultismEntities.WILD_HORDE_HUSK, "Wild Horde Husk");
        this.lang("ru_ru").addEntityType(OccultismEntities.WILD_HORDE_DROWNED, "Wild Horde Drowned");
        this.lang("ru_ru").addEntityType(OccultismEntities.WILD_HORDE_CREEPER, "Wild Horde Creeper");
        this.lang("ru_ru").addEntityType(OccultismEntities.WILD_HORDE_SILVERFISH, "Wild Horde Silverfish");
        this.lang("ru_ru").addEntityType(OccultismEntities.POSSESSED_WEAK_BREEZE, "Wild Weak Breeze");
        this.lang("ru_ru").addEntityType(OccultismEntities.POSSESSED_BREEZE, "Wild Breeze");
        this.lang("ru_ru").addEntityType(OccultismEntities.POSSESSED_STRONG_BREEZE, "Wild Strong Breeze");
        this.lang("ru_ru").addEntityType(OccultismEntities.WILD_ZOMBIE, "Wild Zombie");
        this.lang("ru_ru").addEntityType(OccultismEntities.WILD_SKELETON, "Wild Skeleton");
        this.lang("ru_ru").addEntityType(OccultismEntities.WILD_SILVERFISH, "Wild Silverfish");
        this.lang("ru_ru").addEntityType(OccultismEntities.WILD_SPIDER, "Wild Spider");
        this.lang("ru_ru").addEntityType(OccultismEntities.WILD_BOGGED, "Wild Bogged");
        this.lang("ru_ru").addEntityType(OccultismEntities.WILD_SLIME, "Wild Slime");
        this.lang("ru_ru").addEntityType(OccultismEntities.WILD_HUSK, "Wild Husk");
        this.lang("ru_ru").addEntityType(OccultismEntities.WILD_STRAY, "Wild Stray");
        this.lang("ru_ru").addEntityType(OccultismEntities.WILD_CAVE_SPIDER, "Wild Cave Spider");
        this.lang("ru_ru").addEntityType(OccultismEntities.POSSESSED_EVOKER, "Wild Evoker");
    }

    private void addMiscTranslations() {

        //"(.*?)": "(.*)",
        //this.add\("\1", "\2"\);

        this.lang("ru_ru").add(TranslationKeys.HUD_NO_PENTACLE_FOUND, "No valid pentacle found.");
        this.lang("ru_ru").add(TranslationKeys.HUD_PENTACLE_FOUND, "Current Pentacle: %s");

        this.lang("ru_ru").add(TranslationKeys.MESSAGE_CONTAINER_ALREADY_OPEN, "This container is already opened by another player, wait until they close it.");

        //Jobs
        this.lang("ru_ru").add("job.occultism.lumberjack", "Lumberjack");
        this.lang("ru_ru").add("job.occultism.crush_tier1", "Slow Crusher");
        this.lang("ru_ru").add("job.occultism.crush_tier2", "Crusher");
        this.lang("ru_ru").add("job.occultism.crush_tier3", "Fast Crusher");
        this.lang("ru_ru").add("job.occultism.crush_tier4", "Very Fast Crusher");
        this.lang("ru_ru").add("job.occultism.smelt_tier1", "Slow Smelter");
        this.lang("ru_ru").add("job.occultism.smelt_tier2", "Smelter");
        this.lang("ru_ru").add("job.occultism.smelt_tier3", "Fast Smelter");
        this.lang("ru_ru").add("job.occultism.smelt_tier4", "Very Fast Smelter");
        this.lang("ru_ru").add("job.occultism.manage_machine", "Machine Operator");
        this.lang("ru_ru").add("job.occultism.transport_items", "Transporter");
        this.lang("ru_ru").add("job.occultism.cleaner", "Janitor");
        this.lang("ru_ru").add("job.occultism.trade_otherstone_t1", "Otherstone Trader");
        this.lang("ru_ru").add("job.occultism.trade_otherworld_saplings_t1", "Otherworld Sapling Trader");
        this.lang("ru_ru").add("job.occultism.clear_weather", "Sunshine Spirit");
        this.lang("ru_ru").add("job.occultism.rain_weather", "Rainy Weather Spirit");
        this.lang("ru_ru").add("job.occultism.thunder_weather", "Thunderstorm Spirit");
        this.lang("ru_ru").add("job.occultism.day_time", "Dawn Spirit");
        this.lang("ru_ru").add("job.occultism.night_time", "Dusk Spirit");

        //Enums
        this.lang("ru_ru").add("enum.occultism.facing.up", "Up");
        this.lang("ru_ru").add("enum.occultism.facing.down", "Down");
        this.lang("ru_ru").add("enum.occultism.facing.north", "North");
        this.lang("ru_ru").add("enum.occultism.facing.south", "South");
        this.lang("ru_ru").add("enum.occultism.facing.west", "West");
        this.lang("ru_ru").add("enum.occultism.facing.east", "East");
        this.lang("ru_ru").add("enum.occultism.book_of_calling.item_mode.set_deposit", "Set Deposit");
        this.lang("ru_ru").add("enum.occultism.book_of_calling.item_mode.set_extract", "Set Extract");
        this.lang("ru_ru").add("enum.occultism.book_of_calling.item_mode.set_base", "Set Base Location");
        this.lang("ru_ru").add("enum.occultism.book_of_calling.item_mode.set_storage_controller", "Set Storage Actuator");
        this.lang("ru_ru").add("enum.occultism.book_of_calling.item_mode.set_managed_machine", "Set Managed Machine");
        this.lang("ru_ru").add("enum.occultism.work_area_size.small", "16x16");
        this.lang("ru_ru").add("enum.occultism.work_area_size.medium", "32x32");
        this.lang("ru_ru").add("enum.occultism.work_area_size.large", "64x64");

        //Debug messages
        this.lang("ru_ru").add("debug.occultism.debug_wand.printed_glyphs", "Printed glyphs");
        this.lang("ru_ru").add("debug.occultism.debug_wand.glyphs_verified", "Glyphs verified");
        this.lang("ru_ru").add("debug.occultism.debug_wand.glyphs_not_verified", "Glyphs not verified");
        this.lang("ru_ru").add("debug.occultism.debug_wand.spirit_selected", "Selected spirit with id %s");
        this.lang("ru_ru").add("debug.occultism.debug_wand.spirit_tamed", "Tamed spirit with id %s");
        this.lang("ru_ru").add("debug.occultism.debug_wand.deposit_selected", "Set deposit block %s, facing %s");
        this.lang("ru_ru").add("debug.occultism.debug_wand.no_spirit_selected", "No spirit selected.");

        //Ritual Sacrifices
        this.lang("ru_ru").add("ritual.occultism.sacrifice.cows", "Cow");
        this.lang("ru_ru").add("ritual.occultism.sacrifice.bats", "Bat");
        this.lang("ru_ru").add("ritual.occultism.sacrifice.bees", "Bee");
        this.lang("ru_ru").add("ritual.occultism.sacrifice.zombies", "Zombie");
        this.lang("ru_ru").add("ritual.occultism.sacrifice.parrots", "Parrot");
        this.lang("ru_ru").add("ritual.occultism.sacrifice.chicken", "Chicken");
        this.lang("ru_ru").add("ritual.occultism.sacrifice.pigs", "Pigs");
        this.lang("ru_ru").add("ritual.occultism.sacrifice.humans", "Villager or Player");
        this.lang("ru_ru").add("ritual.occultism.sacrifice.squid", "Squid");
        this.lang("ru_ru").add("ritual.occultism.sacrifice.horses", "Horse");
        this.lang("ru_ru").add("ritual.occultism.sacrifice.sheep", "Sheep");
        this.lang("ru_ru").add("ritual.occultism.sacrifice.llamas", "Llama");
        this.lang("ru_ru").add("ritual.occultism.sacrifice.goats", "Goat");
        this.lang("ru_ru").add("ritual.occultism.sacrifice.snow_golem", "Snow Golem");
        this.lang("ru_ru").add("ritual.occultism.sacrifice.iron_golem", "Iron Golem");
        this.lang("ru_ru").add("ritual.occultism.sacrifice.spiders", "Spider");
        this.lang("ru_ru").add("ritual.occultism.sacrifice.flying_passive", "Allay, Bat, Bee or Parrot");
        this.lang("ru_ru").add("ritual.occultism.sacrifice.cubemob", "Slime or Magma Cube");
        this.lang("ru_ru").add("ritual.occultism.sacrifice.fish", "Any Fish");
        this.lang("ru_ru").add("ritual.occultism.sacrifice.axolotls", "Axolotl");
        this.lang("ru_ru").add("ritual.occultism.sacrifice.camel", "Camel");
        this.lang("ru_ru").add("ritual.occultism.sacrifice.dolphin", "Dolphin");
        this.lang("ru_ru").add("ritual.occultism.sacrifice.wolfs", "Wolf");
        this.lang("ru_ru").add("ritual.occultism.sacrifice.ocelot", "Ocelot");
        this.lang("ru_ru").add("ritual.occultism.sacrifice.cats", "Cat");
        this.lang("ru_ru").add("ritual.occultism.sacrifice.vex", "Vex");
        this.lang("ru_ru").add("ritual.occultism.sacrifice.tadpoles", "Tadpole");
        this.lang("ru_ru").add("ritual.occultism.sacrifice.allay", "Allay");
        this.lang("ru_ru").add("ritual.occultism.sacrifice.warden", "Warden");
        this.lang("ru_ru").add("ritual.occultism.sacrifice.ravager", "Ravager");

        //Network Message
        this.lang("ru_ru").add("network.messages.occultism.request_order.order_received", "Order received!");

        //Effects
        this.lang("ru_ru").add("effect.occultism.third_eye", "Third Eye");
        this.lang("ru_ru").add("effect.occultism.double_jump", "Multi Jump");
        this.lang("ru_ru").add("effect.occultism.dragon_greed", "Dragon's Greed");
        this.lang("ru_ru").add("effect.occultism.mummy_dodge", "Dodge");
        this.lang("ru_ru").add("effect.occultism.bat_lifesteal", "Lifesteal");
        this.lang("ru_ru").add("effect.occultism.beaver_harvest", "Beaver Harvest");
        this.lang("ru_ru").add("effect.occultism.step_height", "Step Height");

        //Sounds
        this.lang("ru_ru").add("occultism.subtitle.chalk", "Chalk");
        this.lang("ru_ru").add("occultism.subtitle.brush", "Brush");
        this.lang("ru_ru").add("occultism.subtitle.start_ritual", "Start Ritual");
        this.lang("ru_ru").add("occultism.subtitle.tuning_fork", "Tuning Fork");
        this.lang("ru_ru").add("occultism.subtitle.crunching", "Crunching");
        this.lang("ru_ru").add("occultism.subtitle.poof", "Poof!");

        //Dimension types
        this.lang("ru_ru").add(Util.makeDescriptionId("dimension_type", BuiltinDimensionTypes.OVERWORLD.location()), "Overworld");
        this.lang("ru_ru").add(Util.makeDescriptionId("dimension_type", BuiltinDimensionTypes.NETHER.location()), "Nether");
        this.lang("ru_ru").add(Util.makeDescriptionId("dimension_type", BuiltinDimensionTypes.END.location()), "The End");
    }

    private void addGuiTranslations() {
        this.lang("ru_ru").add("gui.occultism.book_of_calling.mode", "Mode");
        this.lang("ru_ru").add("gui.occultism.book_of_calling.work_area", "Work Area");
        this.lang("ru_ru").add("gui.occultism.book_of_calling.manage_machine.insert", "Insert Facing");
        this.lang("ru_ru").add("gui.occultism.book_of_calling.manage_machine.extract", "Extract Facing");
        this.lang("ru_ru").add("gui.occultism.book_of_calling.manage_machine.custom_name", "Custom Name");

        // Spirit GUI
        this.lang("ru_ru").add("gui.occultism.spirit.age", "Essence Decay: %d%%");
        this.lang("ru_ru").add("gui.occultism.spirit.job", "%s");

        // Spirit Transporter GUI
        this.lang("ru_ru").add("gui.occultism.spirit.transporter.filter_mode", "Filter Mode");
        this.lang("ru_ru").add("gui.occultism.spirit.transporter.filter_mode.blacklist", "Blacklist");
        this.lang("ru_ru").add("gui.occultism.spirit.transporter.filter_mode.whitelist", "Whitelist");
        this.lang("ru_ru").add("gui.occultism.spirit.transporter.tag_filter", "Enter the tags to filter for separated by \";\".\nE.g.: \"c:ores;*logs*\".\nUse \"*\" to match any character, e.g. \"*ore*\" to match ore tags from any mod. To filter for items, prefix the item id with \"item:\", E.g.: \"item:minecraft:chest\".");

        // Storage Controller GUI
        this.lang("ru_ru").add("gui.occultism.storage_controller.space_info_label", "%d/%d");
        this.lang("ru_ru").add("gui.occultism.storage_controller.space_info_label_new", "%s%% filled");
        this.lang("ru_ru").add("gui.occultism.storage_controller.space_info_label_types", "%s%% of types");
        this.lang("ru_ru").add("gui.occultism.storage_controller.shift", "Hold shift for more information.");
        this.lang("ru_ru").add("gui.occultism.storage_controller.search.tooltip@", "Prefix @: Search mod id.");
        this.lang("ru_ru").add("gui.occultism.storage_controller.search.tooltip#", "Prefix #: Search in item tooltip.");
        this.lang("ru_ru").add("gui.occultism.storage_controller.search.tooltip$", "Prefix $: Search for Tag.");
        this.lang("ru_ru").add("gui.occultism.storage_controller.search.tooltip_rightclick", "Clear the text with a right-click.");
        this.lang("ru_ru").add("gui.occultism.storage_controller.search.tooltip_clear", "Clear search.");
        this.lang("ru_ru").add("gui.occultism.storage_controller.search.tooltip_jei_on", "Sync search with JEI.");
        this.lang("ru_ru").add("gui.occultism.storage_controller.search.tooltip_jei_off", "Do not sync search with JEI.");
        this.lang("ru_ru").add("gui.occultism.storage_controller.search.tooltip_sort_type_amount", "Sort by amount.");
        this.lang("ru_ru").add("gui.occultism.storage_controller.search.tooltip_sort_type_name", "Sort by item name.");
        this.lang("ru_ru").add("gui.occultism.storage_controller.search.tooltip_sort_type_mod", "Sort by mod name.");
        this.lang("ru_ru").add("gui.occultism.storage_controller.search.tooltip_sort_direction_down", "Sort ascending.");
        this.lang("ru_ru").add("gui.occultism.storage_controller.search.tooltip_sort_direction_up", "Sort descending.");
        this.lang("ru_ru").add("gui.occultism.storage_controller.search.machines.tooltip@", "Prefix @: Search mod id.");
        this.lang("ru_ru").add("gui.occultism.storage_controller.search.machines.tooltip_sort_type_amount", "Sort by distance.");
        this.lang("ru_ru").add("gui.occultism.storage_controller.search.machines.tooltip_sort_type_name", "Sort by machine name.");
        this.lang("ru_ru").add("gui.occultism.storage_controller.search.machines.tooltip_sort_type_mod", "Sort by mod name.");
    }

    private void addRitualMessages() {
        this.add("ritual.occultism.pentacle_help", "\u00a7lInvalid pentacle!\u00a7r\nWere you trying to create pentacle: %s? Missing:\n%s");
        this.add("ritual.occultism.pentacle_help_at_glue", " at position ");
        this.add("ritual.occultism.pentacle_help.no_pentacle", "\u00a7lNo pentacle found!\u00a7r\nIt seems you did not draw a pentacle, or your pentacle is missing large parts. See the \"Rituals\" section of the Dictionary of Spirits, the required Pentacle will be a clickable blue link above the ritual recipe on the ritual's page.");
        this.add("ritual.occultism.ritual_help", "\u00a7lInvalid ritual!\u00a7r\nWere you trying to perform ritual: \"%s\"? Missing items:\n%s");
        this.add("ritual.occultism.disabled", "This ritual is disabled on this server.");
        this.add("ritual.occultism.does_not_exist", "\u00a7lUnknown ritual\u00a7r. Make sure the pentacle & ingredients are set up correctly. If you are still unsuccessful join our discord at https://discord.gg/trE4SHRXvb");
        this.add("ritual.occultism.book_not_bound", "\u00a7lUnbound Book of Calling\u00a7r. You must craft this book with Dictionary of Spirits to bind to a spirit before starting a ritual.");
        this.add("ritual.occultism.sacrifice", "" + ChatFormatting.WHITE + ChatFormatting.BOLD + "Perform the Sacrifice of:");
        this.add("ritual.occultism.use_item", "" + ChatFormatting.WHITE + ChatFormatting.BOLD + "Use the item:");

        this.add("ritual.occultism.unknown.conditions", "Not all requirements for this ritual are met.");
        this.add("ritual.occultism.unknown.started", "Ritual started.");
        this.add("ritual.occultism.unknown.finished", "Ritual completed successfully.");
        this.add("ritual.occultism.unknown.interrupted", "Ritual interrupted.");

        this.add("ritual.occultism.debug.conditions", "Not all requirements for this ritual are met.");
        this.add("ritual.occultism.debug.started", "Ritual started.");
        this.add("ritual.occultism.debug.finished", "Ritual completed successfully.");
        this.add("ritual.occultism.debug.interrupted", "Ritual interrupted.");
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
                        Ритуалы позволяют вызывать духов в нашу параллельную реальность или заточать их в предметы или живые существа. Каждый ритуал состоит из [#](%1$s)пентакля[#](), [#](%1$s)ингредиентов для ритуала[#](), снабжаемые через жертвенные миски, [#](%1$s)запускающего предмета[#](), а в некоторых случаях — [#](%1$s)жертвоприношение[#]() живых существ. Фиолетовые частицы покажут, что ритуал удался и выполняется.
                        """.formatted(COLOR_PURPLE));

        helper.page("steps");
        this.lang("ru_ru").add(helper.pageTitle(), "Выполнение ритуала");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        Ритуалы всегда выполняются по неизменным этапам:
                        - Начертить пентакль;
                        - Поставить золотую миску;
                        - Поставить жертвенные миски;
                        - Положить ингредиенты в миски;
                        - Нажать [#](%1$s)ПКМ[#]() на золотую миску с помощью активационного предмета.
                        - *Необязательно: совершить жертвоприношение в центре пентакля.*
                        """.formatted(COLOR_PURPLE));

        helper.page("additional_requirements");
        this.lang("ru_ru").add(helper.pageTitle(), "Дополнительные требования");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        Если ритуал показывает серые частицы над золотой жертвенной миской, нужно удовлетворить дополнительные требования в порядке, предусмотренном на странице ритуала. После удовлетворения всех требований: ритуал покажет фиолетовые частицы и начнёт тратить предметы в жертвенных мисках.
                        """);

        helper.entry("item_use");
        this.lang("ru_ru").add(helper.entryName(), "Использование предмета");

        helper.page("intro");
        this.lang("ru_ru").add(helper.pageTitle(), "Использование предмета");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        Для выполнения некоторых ритуалов требуется использовать определённые предметы. Чтобы провести ритуал, используйте предмет, отмеченный на странице ритуала в радиусе 16 блоков от [](item://occultism:golden_sacrificial_bowl) для проведения ритуала.
                        \\
                        \\
                        **Примечание**: начните ритуал перед использованием предмета. Серые частицы указывают, что ритуал готов использовать предмет.
                        """);

        helper.entry("sacrifice");
        this.lang("ru_ru").add(helper.entryName(), "Жертвы");

        helper.page("intro");
        this.lang("ru_ru").add(helper.pageTitle(), "Жертвы");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        Некоторые ритуалы требуют жертвоприношение живых существ, чтобы обеспечить необходимую энергию для вызова духа. Жертвы отмечены на странице ритуала в подразделе "Жертвоприношение". Чтобы выполнить жертвоприношение: убейте животное в пределах 8 блоков от золотой жертвенной миски: жертвоприношение считается только убийством, совершённым игроком!
                         """);

        helper.entry("summoning_rituals");
        this.lang("ru_ru").add(helper.entryName(), "Ритуалы для вызова");

        helper.entry("possession_rituals");
        this.lang("ru_ru").add(helper.entryName(), "Ритуалы для одержимости");

        helper.entry("crafting_rituals");
        this.lang("ru_ru").add(helper.entryName(), "Ритуалы для заточения");

        helper.entry("familiar_rituals");
        this.lang("ru_ru").add(helper.entryName(), "Ритуалы для фамильяров");
    }

    private void addSummoningRitualsCategory(BookContextHelper helper) {
        helper.category("summoning_rituals");
        this.lang("ru_ru").add(helper.categoryName(), "Ритуалы для вызова");

        helper.entry("overview");
        this.lang("ru_ru").add(helper.entryName(), "Ритуалы для вызова");

        helper.page("intro");
        this.lang("ru_ru").add(helper.pageTitle(), "Ритуалы для вызова");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        Ритуалы для вызова заставляют духов входить в этот мир в своём избранном облике, что приводит к небольшим ограничениям на их силе. Вызванные духи классифицируются от духов-торговцев, которые торгуют и преобразуют предметы, до рабов-помощников для физического труда.
                         """);

        helper.entry("return_to_rituals");
        this.lang("ru_ru").add(helper.entryName(), "Вернуться к категории ритуалов");

        helper.entry("summon_crusher_t1");
        //Moved to OccultismBookProvider#makeSummonCrusherT1Entry

        helper.entry("summon_crusher_t2");
        this.lang("ru_ru").add(helper.entryName(), "Вызов Джинна-дробильщика");

        helper.page("intro");
        this.lang("ru_ru").add(helper.pageTitle(), "Джинн-дробильщик");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        Джинн-дробильщик быстрее, эффективнее и искуснее Фолиота-дробильщика,
                        что позволяет ему дробить лёд, не растапливая.
                        \\
                        Он дробит **одну** руду на **три** аналогичные пыли.
                         """);

        helper.page("ritual");
        //no text

        helper.entry("summon_crusher_t3");
        this.lang("ru_ru").add(helper.entryName(), "Вызов Африта-дробильщика");

        helper.page("intro");
        this.lang("ru_ru").add(helper.pageTitle(), "Африт-дробильщик");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        Африт-дробильщик быстрее и эффективнее Джинна-дробильщика.
                        \\
                        \\
                        Он дробит **одну** руду на **четыре** аналогичных пыли.
                          """);

        helper.page("ritual");
        //no text

        helper.entry("summon_crusher_t4");
        this.lang("ru_ru").add(helper.entryName(), "Вызов Марида-дробильщика");

        helper.page("intro");
        this.lang("ru_ru").add(helper.pageTitle(), "Марид-дробильщик");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        Марид-дробильщик быстрее, эффективнее и искуснее Африта-дробильщика,
                        что позволяет ему дробить осколок эха с сохранением его свойств.
                        \\
                        Он дробит **одну** руду на **шесть** аналогичных пылей.
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
        this.lang("ru_ru").add(helper.entryName(), "Вызов Джинна-станочника");

        helper.page("intro");
        this.lang("ru_ru").add(helper.pageTitle(), "Джинн-станочник");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        Станочник перемещает предметы, указанные в графическом интерфейсе актуатора пространственного хранилища, в свою выбранную машину и возвращает результаты создания в систему хранения. Он также может использоваться для автоматического опустошения сундука в актуатор хранилища.
                        \\
                        По сути, — это создание по заказу!
                          """);

        helper.page("ritual");
        //no text

        helper.page("tutorial");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        Чтобы использовать станочника, используйте книгу вызова для связи актуатора хранилища, машины и, по желанию, отдельного места извлечения: лицевой стороны, откуда будут извлекаться предметы (по нажатию!). Для машины вы можете дополнительно задать пользовательское название и лицевые стороны ввода/извлечения.
                          """);

        helper.page("tutorial2");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        Обратите внимание, что установка новой машины либо её настройка с помощью книги вызова сбросит настройки извлечения.
                        \\
                        \\
                        Для лёгкого старта убедитесь, что посмотрели короткую [видео-инструкцию](https://gyazo.com/237227ba3775e143463b31bdb1b06f50)!
                          """);

        helper.page("book_of_calling");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        Если потеряете книгу вызова, сможете создать новую.
                        Нажмите [#](%1$s)Shift + ПКМ[#]() на духе с помощью созданной книги, чтобы определить её.
                        """.formatted(COLOR_PURPLE));

        helper.entry("trade_spirits");
        this.lang("ru_ru").add(helper.entryName(), "Духи-торговцы");

        helper.page("intro");
        this.lang("ru_ru").add(helper.pageTitle(), "Духи-торговцы");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        Духи-торговцы подбирают необходимые предметы и кидают результаты обмена на землю. Дух активно меняет предметы лишь в случаях, когда вокруг него появляются фиолетовые частицы.
                        \\
                        \\
                        **Если не видите частиц**, убедитесь, что дали надлежащий предмет и количество.
                           """);

        helper.page("intro2");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        Большинство духов-торговцев испытывают чрезмерный распад сущности и немедленно возвращаются в [#](%1$s)Иное Место[#]().
                           """.formatted(COLOR_PURPLE));

        helper.entry("summon_otherworld_sapling_trader");
        this.lang("ru_ru").add(helper.entryName(), "Вызов торговца потусторонними саженцами");

        helper.page("intro");
        this.lang("ru_ru").add(helper.pageTitle(), "Торговец потусторонними саженцами");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        Потусторонние деревья, выращенные из потусторонних саженцев, могут быть добыты только во время действия эффекта [#](%1$s)Третий глах[#](). Для упрощения жизни торговец потусторонними саженцами будет обменивать столь естественные саженцы на стабильную разновидность, добываемую кем угодно, а при добыче выдавать такие же стабильные саженцы.
                           """.formatted(COLOR_PURPLE));

        helper.page("trade");
        //no text

        helper.page("ritual");
        //no text

        helper.entry("summon_otherstone_trader");
        this.lang("ru_ru").add(helper.entryName(), "Вызов торговца потусторонним камнем");

        helper.page("intro");
        this.lang("ru_ru").add(helper.pageTitle(), "Торговец потусторонним камнем");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        Торговец потусторонним камнем позволяет получить больше [](item://occultism:otherstone) вместо того, чтобы использовать [](item://occultism:spirit_fire). Получается, что это вдвойне выгодно, если нужно использовать потусторонний камень в качестве строительного материала.
                           """);

        helper.page("trade");
        //no text

        helper.page("ritual");
        //no text

        helper.entry("weather_magic");
        this.lang("ru_ru").add(helper.entryName(), "Магия погоды");

        helper.page("intro");
        this.lang("ru_ru").add(helper.pageTitle(), "Магия погоды");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        Магия погоды весьма полезна для фермеров и других людей, в зависимости от конкретной погоды. Вызывайте духов для изменения погоды. Разным типам изменения погоды требуются определённые духи.
                        \\
                        \\
                        Духи погоды изменяют только погоду, а затем исчезают.
                           """);

        helper.page("ritual_clear");
        //no text

        helper.page("ritual_rain");
        //no text

        helper.page("ritual_thunder");
        //no text

        helper.entry("time_magic");
        this.lang("ru_ru").add(helper.entryName(), "Магия времени");

        helper.page("intro");
        this.lang("ru_ru").add(helper.pageTitle(), "Магия времени");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        Магия времени ограничена возможностями: она не может перемещать заклинателя во времени; однако скорее позволяет изменять время дня. Это особенно полезно для ритуалов или других задач, требующих сугубо дня и ночи.
                        \\
                        \\
                        Духи времени изменяют только время, а затем исчезают.
                           """);

        helper.page("ritual_day");
        //no text

        helper.page("ritual_night");
        //no text

        helper.entry("afrit_essence");
        this.lang("ru_ru").add(helper.entryName(), "Сущность Африта");

        helper.page("intro");
        this.lang("ru_ru").add(helper.pageTitle(), "Сущность Африта");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        [](item://occultism:afrit_essence) широко используется для создания красного мела, необходимого для безопасного вызова более могущественных духов. Чтобы получить сущность [#](%1$s)Африта[#]() в этой реальности, нужно вызвать незаточённого Африта и убить. Имейте ввиду, что это непростая затея: незаточённый дух представляет большую угрозу для всех поблизости.
                           """.formatted(COLOR_PURPLE));

        helper.page("ritual");
        //no text

        helper.entry("marid_essence");
        this.lang("ru_ru").add(helper.entryName(), "Сущность Марида");

        helper.page("intro");
        this.lang("ru_ru").add(helper.pageTitle(), "Сущность Марида");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        [](item://occultism:marid_essence) широко используется для создания синего мела, необходимого для безопасного контроля самых могущественных духов. Чтобы получить сущность [#](%1$s)Марида[#]() в этой реальности, нужно вызвать незаточённого Марида и убить. Имейте ввиду, что это непростая затея: незаточённый дух представляет большую угрозу для всех поблизости.
                           """.formatted(COLOR_PURPLE));

        helper.page("ritual");
        //no text
    }

    private void addPossessionRitualsCategory(BookContextHelper helper) {
        helper.category("possession_rituals");
        this.lang("ru_ru").add(helper.categoryName(), "Ритуалы для одержимости");

        helper.entry("return_to_rituals");
        this.lang("ru_ru").add(helper.entryName(), "Вернуться к категории ритуалов");

        helper.entry("overview");
        this.lang("ru_ru").add(helper.entryName(), "Ритуалы для одержимости");

        helper.page("intro");
        this.lang("ru_ru").add(helper.pageTitle(), "Ритуалы для одержимости");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        Ритуалы для одержимости заточают духов в живых существ, что в значительной степени дают вызывателю контроль над одержимым существом.
                        \\
                        \\
                        По сути, эти ритуалы используются для получения редких предметов, избегая опасных мест.
                           """);

        helper.entry("possess_enderman");
        this.lang("ru_ru").add(helper.entryName(), "Одержимый эндермер");

        helper.page("entity");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        **Добыча**: 1-3х [](item://minecraft:ender_pearl).
                                """);

        helper.page("ritual");
        //no text

        helper.page("description");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        При проведении этого ритуала (используя жизненную энергию [#](%1$s)свиньи[#]()) появляется [#](%1$s)эндермен[#](), и почти сразу же завладевается вызванным [#](%1$s)Джинном[#](). При убийстве с [#](%1$s)одержимого эндермена[#]() всегда выпадает минимум один [](item://minecraft:ender_pearl).
                                """.formatted(COLOR_PURPLE));

        helper.entry("wither_skull");
        this.lang("ru_ru").add(helper.entryName(), "Дикая охота");

        helper.page("intro");
        this.lang("ru_ru").add(helper.pageTitle(), "Череп визер-скелета");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        Помимо опасного путешествия в Незер, существует ещё другой способ получить эти черепа. Легендарная [#](%1$s)Дикая охота[#]() состоит из [#](%1$s)могущественных духов[#](), принявших облик визер-скелетов. Хотя вызов Дикой охоты невероятно опасен — это наибыстрейший способ получения черепов визер-скелетов.
                           """.formatted(COLOR_PURPLE));

        helper.page("ritual");
        //no text

        helper.entry("possess_endermite");
        this.lang("ru_ru").add(helper.entryName(), "Одержимый эндермит");

        helper.page("entity");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        **Добыча**: 1-2х [](item://minecraft:end_stone).
                                """);

        helper.page("ritual");
        //no text

        helper.page("description");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        При проведении этого ритуала [#](%1$s)эндермит[#]() появляется при обмане. Камни и земля символизируют окружающий мир, затем бросается яйцо для имитации использования эндер-жемчуга. При появлении эндермита, вызванный [#](%1$s)Фолиот[#]() сразу же завладевает им, посещает [#](%1$s)Энд[#]() и возвращается обратно. При убийстве с [#](%1$s)одержимого эндермита[#]() всегда выпадает минимум один [](item://minecraft:end_stone).
                                """.formatted(COLOR_PURPLE));

        helper.entry("possess_ghast");
        //moved to OccultismBookProvider#makePossessGhastEntry

        helper.entry("possess_skeleton");
        this.lang("ru_ru").add(helper.entryName(), "Одержимый скелет");

        helper.page("entity");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        **Добыча**: 1х [](item://minecraft:skeleton_skull).
                                """);

        helper.page("ritual");
        //no text

        helper.page("description");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        При проведении этого ритуала (используя жизненную энергию [#](%1$s)курицы[#]()) появляется [#](%1$s)скелет[#](), и сразу же завладевается вызванным [#](%1$s)Фолиотом[#](). [#](ad03fc)одержимый скелет[#]() будет устойчивым к дневному свету, но при убийстве всегда выпадает минимум один [](item://minecraft:skeleton_skull).
                                """.formatted(COLOR_PURPLE));

        helper.entry("possess_unbound_parrot");
        this.lang("ru_ru").add(helper.entryName(), "Несвязанный попугай");

        helper.page("entity");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        **Предоставляет**: приручаемого попугая
                          """);

        helper.page("ritual");
        //no text

        helper.page("description");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        При проведении этого ритуала вызывается [#](%1$s)Фолиот[#]() **в качестве дикого духа**.
                        \\
                        \\
                        Убийство [#](%1$s)курицы[#]() и подношение красителей предназначается для того, чтобы склонить Фолиота принять облик попугая. Хотя [#](%1$s)Фолиот[#]() не находится среди умнейших духов — в ряде случаях он дурно понимает указания...
                          """.formatted(COLOR_PURPLE));

        helper.page("description2");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        *Следовательно, если появится [#](%1$s)курица[#](), это не ошибка, просто не повезло!*
                           """.formatted(COLOR_PURPLE));

        helper.entry("possess_unbound_otherworld_bird");
        this.lang("ru_ru").add(helper.entryName(), "Несвязанный дрикрыл");

        helper.page("entity");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        **Предоставляет**: приручаемого дрикрыла
                          """);

        helper.page("ritual");
        //no text

        helper.page("description");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        Дополнительные сведения см. в записи [Фамильяр-дрикрыл](entry://familiar_rituals/familiar_otherworld_bird).
                          """);
    }

    private void addCraftingRitualsCategory(BookContextHelper helper) {
        helper.category("crafting_rituals");
//        this.lang("ru_ru").add(helper.categoryName(), "Binding Rituals"); //done via the category provider for the rituals category

        helper.entry("return_to_rituals");
        this.lang("ru_ru").add(helper.entryName(), "Вернуться к категории ритуалов");

        helper.entry("overview");
        this.lang("ru_ru").add(helper.entryName(), "Ритуалы для заточения");

        helper.page("intro");
        this.lang("ru_ru").add(helper.pageTitle(), "Ритуалы для заточения");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        Ритуалы для заточения вселяют духов в предметы, где их силы используются для конкретной цели. Созданные предметы действуют в качестве простых чар, наделяющие силой или, наоборот, выполняют сложные задачи, чтобы помочь вызывателю.
                           """);

        helper.entry("craft_storage_system");
        this.lang("ru_ru").add(helper.entryName(), "Магическое хранилище");

        helper.page("spotlight");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        Следующие записи показывают только ритуалы, связанные с системой магического хранения. За подробной пошаговой инструкцией по созданию системы хранения, смотрите в категории [Магическое хранилище](category://storage).
                           """.formatted(COLOR_PURPLE));

        helper.entry("craft_dimensional_matrix");
        this.lang("ru_ru").add(helper.entryName(), "Пространственная матрица");

        helper.page("spotlight");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        Пространственная матрица — это мостик в малое пространство для хранения предметов. [#](%1$s)Джинн[#](), заключённый в матрице, поддерживает стабильность пространства, а другие духи в стабилизаторах хранилища, как правило, помогают увеличить размер пространства.
                           """.formatted(COLOR_PURPLE));

        helper.page("ritual");
        //no text

        helper.entry("craft_infused_pickaxe");
        this.lang("ru_ru").add(helper.entryName(), "Наполненная кирка");

        helper.page("spotlight");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        Обычно потусторонние руды могут быть добыты только с помощью инструментов из потустороннего металла. [](item://occultism:infused_pickaxe) служит временным решением классической дилеммы. Хрупкий кристалл, настроенный на духа, заселённый [#](%1$s)Джинном[#](), что позволяет собирать руды: обладает очень низкой прочностью. Однако к более прочному варианту относится [Айзниевая кирка](entry://getting_started/iesnium_pickaxe).
                           """.formatted(COLOR_PURPLE));

        helper.page("ritual");
        //no text

        helper.entry("craft_otherworld_goggles");
        this.lang("ru_ru").add(helper.entryName(), "Создание потусторонних очков");

        helper.page("goggles_spotlight");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        [](item://occultism:otherworld_goggles) дают владельцу постоянный эффект [#](%1$s)Третий глаз[#](), что позволяет видеть даже те блоки, скрытые от тех, кто съел [Видение демона](entry://occultism:dictionary_of_spirits/getting_started/demons_dream).
                        \\
                        \\
                        Это изящно решает общераспространённую проблему вызывателей, находящихся под воздействием наркотического помутнения, что служит причиной всяческих расстройств.
                        """.formatted(COLOR_PURPLE));

        helper.page("goggles_more");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        Однако очки не дают возможность добывать потусторонние материалы, т. е. для разрушения блоков и получения потусторонних разновидностей нужно не только носить очки, а также использовать [Наполненную кирку](entry://getting_started/infused_pickaxe), а ещё лучше [Айзниевую кирку](entry://getting_started/iesnium_pickaxe).
                        """.formatted(COLOR_PURPLE));

        helper.page("lenses_spotlight");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        Потусторонние очки используют [#](%1$s)Фолиота[#](), заточённого в линзы. Фолиот передаёт владельцу способность видеть более высокие плоскости существования, тем самым позволяя видеть потусторонние материалы.
                         """.formatted(COLOR_PURPLE));

        helper.page("lenses_more");
        this.lang("ru_ru").add(helper.pageTitle(), "Создание линз");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        Ритуал для вызова духа в линзы, необходимый для создания очков, как правило, считается одним из первых сложных ритуалов для начинающих вызывателей, указывая на то, что их навыки выходят за пределы базовых знаний, достигая совершенства.
                        """.formatted(COLOR_PURPLE));

        helper.page("lenses_recipe");
        //no text

        helper.page("ritual");
        //no text

        helper.page("goggles_recipe");
        //no text

        helper.entry("craft_storage_controller_base");
        this.lang("ru_ru").add(helper.entryName(), "Основание актуатора хранилища");

        helper.page("spotlight");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        Основание актуатора хранилища заточает [#](%1$s)Фолиота[#](), отвечающего за взаимодействие с предметами в матрице пространственного хранилища.
                        """.formatted(COLOR_PURPLE));

        helper.page("ritual");
        //no text

        helper.entry("craft_stabilizer_tier1");
        this.lang("ru_ru").add(helper.entryName(), "Стабилизатор хранилища 1-го уровня");

        helper.page("spotlight");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        Этот простой стабилизатор хранилища, заселённый [#](%1$s)Фолиотом[#](): поддерживает стойкость пространственной матрицы во время стабильного состояния пространственного хранилища, таким образом позволяя хранить много предметов.
                        \\
                        \\
                        По умолчанию каждый стабилизатор 1-го уровня добавляет **64** типа предметов и 512.000 ед. вместимости.
                        """.formatted(COLOR_PURPLE));

        helper.page("ritual");
        //no text

        helper.entry("craft_stabilizer_tier2");
        this.lang("ru_ru").add(helper.entryName(), "Стабилизатор хранилища 2-го уровня");

        helper.page("spotlight");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        Этот улучшенный стабилизатор, заселённый [#](%1$s)Джинном[#](): поддерживает стойкость пространственной матрицы во время стабильного состояния пространственного хранилища, таким образом позволяя хранить ещё больше предметов.
                        \\
                        \\
                        По умолчанию каждый стабилизатор 2-го уровня добавляет **128** типов предметов и 1.024.000 ед. вместимости.
                        """.formatted(COLOR_PURPLE));

        helper.page("ritual");
        //no text

        helper.entry("craft_stabilizer_tier3");
        this.lang("ru_ru").add(helper.entryName(), "Стабилизатор хранилища 3-го уровня");

        helper.page("spotlight");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        Этот продвинутый стабилизатор, заселённый [#](%1$s)Афритом[#](): поддерживает стойкость пространственной матрицы во время стабильного состояния пространственного хранилища, таким образом позволяя хранить ещё больше предметов.
                        \\
                        \\
                        По умолчанию каждый стабилизатор 3-го уровня добавляет **256** типов предметов и 2.048.000 ед. вместимости.
                        """.formatted(COLOR_PURPLE));

        helper.page("ritual");
        //no text

        helper.entry("craft_stabilizer_tier4");
        this.lang("ru_ru").add(helper.entryName(), "Стабилизатор хранилища 4-го уровня");

        helper.page("spotlight");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        Этот весьма продвинутый стабилизатор, заселённый [#](%1$s)Маридом[#](): поддерживает стойкость пространственной матрицы во время стабильного состояния пространственного хранилища, таким образом позволяя хранить ещё больше предметов.
                        \\
                        \\
                        По умолчанию каждый стабилизатор 4-го уровня добавляет **512** типа предметов и 4.098.000 ед. вместимости.
                        """.formatted(COLOR_PURPLE));

        helper.page("ritual");
        //no text

        helper.entry("craft_stable_wormhole");
        this.lang("ru_ru").add(helper.entryName(), "Стабильная червоточина");

        helper.page("spotlight");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        Стабильная червоточина позволяет получит доступ к пространственной матрице из удалённого места назначения.
                        \\
                        \\
                        Нажмите Shift + ПКМ на [](item://occultism:storage_controller) для её связки, затем поставьте червоточину в мир для использования в качестве удалённой точки доступа.
                         """.formatted(COLOR_PURPLE));

        helper.page("ritual");
        //no text

        helper.entry("craft_storage_remote");
        this.lang("ru_ru").add(helper.entryName(), "Средство доступа к удалённому хранилищу");

        helper.page("spotlight");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        [](item://occultism:storage_remote) может быть связано с [](item://occultism:storage_controller) при нажатии Shift + ПКМ. [#](%1$s)Джинн[#](), заточённый в средстве доступа, в дальнейшем сможет получить доступ к предметам из актуатора даже из других измерений.
                        """.formatted(COLOR_PURPLE));

        helper.page("ritual");
        //no text

        helper.entry("craft_foliot_miner");
        this.lang("ru_ru").add(helper.entryName(), "Фолиот-рудокоп");

        helper.page("intro");
        this.lang("ru_ru").add(helper.pageTitle(), "Фолиот-рудокоп");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        Духи-рудокопы используют [](item://occultism:dimensional_mineshaft) для получения ресурсов из других измерений. Они вызываются и заключаются в волшебные лампы, из которых могут выходить только через шахты. Волшебная лампа разрушается с ходом времени, и как только разрушится, дух освободится обратно в [#](%1$s)Иное Место[#]().
                        """.formatted(COLOR_PURPLE));

        helper.page("magic_lamp");
        this.lang("ru_ru").add(helper.pageTitle(), "Волшебная лампа");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        Для вызова и владения духами-рудокопами сперва нужно создать [Волшебную лампу](entry://getting_started/magic_lamps). Ключевой ингредиент для этого [Айзний](entry://getting_started/iesnium).
                        """.formatted(COLOR_PURPLE));

        helper.page("magic_lamp_recipe");
        //no text

        helper.page("spotlight");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        [#](%1$s)Фолиот[#]()-рудокоп добывает блоки без особой цели и возвращает всё, что находит. Процесс добычи ресурсов довольно медленный, поэтому Фолиот потребляет небольшое количество энергии, которое со временем наносит вред лампе, где находится.
                        """.formatted(COLOR_PURPLE));

        helper.page("ritual");
        //no text

        helper.entry("craft_djinni_miner");
        this.lang("ru_ru").add(helper.entryName(), "Джинн-рудокоп");

        helper.page("spotlight");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        [#](%1$s)Джинн[#]()-рудокоп добывает только руду. Отсеивая другие блоки, способен быстрее и эффективнее добывать руды. Чем больше сила Джинна, тем сравнительно быстро вредит волшебной лампе.
                        """.formatted(COLOR_PURPLE));

        helper.page("ritual");
        //no text

        helper.entry("craft_afrit_miner");
        this.lang("ru_ru").add(helper.entryName(), "Африт-рудокоп");

        helper.page("spotlight");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        [#](%1$s)Африт[#]()-рудокоп добывает руду, как и Джинны-рудокопы, а также глубинносланцевые руды. Этот рудокоп быстрее и эффективнее Джинна, тем самым ещё медленнее вредя волшебной лампе.
                        """.formatted(COLOR_PURPLE));

        helper.page("ritual");
        //no text

        helper.entry("craft_marid_miner");
        this.lang("ru_ru").add(helper.entryName(), "Марид-рудокоп");

        helper.page("spotlight");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        [#](%1$s)Марид[#]()-рудокоп является самым могущественным духом-рудокопом. У него невероятная скорость добычи и лучшая сохранность волшебной лампы. В отличие от других духов-рудокопов, также способен добывать редчайшие руды, например, [](item://minecraft:ancient_debris) и [](item://occultism:iesnium_ore).
                        """.formatted(COLOR_PURPLE));

        helper.page("ritual");
        //no text

        helper.entry("craft_ancient_miner");
        this.lang("ru_ru").add(helper.entryName(), "Древний рудокоп");

        helper.page("spotlight");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        Сжав «МММ» вы получите сверхмогущественного рудокопа. Однако нечто начнёт за вами наблюдать. [](item://occultism:mining_dim_core) крайне редко добывается Маридом.
                        """.formatted(COLOR_PURPLE));

        helper.page("ritual");
        //no text

        helper.entry("craft_satchel");
        this.lang("ru_ru").add(helper.entryName(), "Необычайно большая наплечная сумка");

        helper.page("spotlight");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        На [#](%1$s)Фолиота[#](), заточённого в сумке, возлагается обязанность **едва** искажать реальность, позволяя хранить в сумке куча предметов, чем позволяют её физические размеры. Это делает её полезным спутником для путешественника.
                        """.formatted(COLOR_PURPLE));

        helper.page("ritual");
        //no text

        helper.entry("craft_soul_gem");
        this.lang("ru_ru").add(helper.entryName(), "Камень души");

        helper.page("spotlight");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        Камни души представляют собой алмаз, установленный в драгоценные металлы и затем вселённым [#](%1$s)Джинном[#](). Дух создаёт малое пространство, позволяя временно удерживать живых существ. Однако существ великой силы нельзя хранить.
                        """.formatted(COLOR_PURPLE));

        helper.page("usage");
        this.lang("ru_ru").add(helper.pageTitle(), "Использование");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        Для поимки существа нажмите [#](%1$s)ПКМ[#]() на него с помощью камня души. \\
                        Нажмите [#](%1$s)ПКМ[#]() ещё раз, чтобы выпустить его.
                        \\
                        \\
                        Босса поймать не представляется возможным.
                               """.formatted(COLOR_PURPLE));

        helper.page("ritual");
        //no text

        helper.entry("craft_familiar_ring");
        this.lang("ru_ru").add(helper.entryName(), "Перстень для фамильяра");

        helper.page("spotlight");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        Перстень для фамильяра сделан из [](item://occultism:soul_gem), сдерживающего [#](%1$s)Джинна[#](), заточённого в перстне. [#](%1$s)Джинн[#]() в перстне позволяет фамильяру, заключённому в камне душ накладывать эффекты на владельца."
                        """.formatted(COLOR_PURPLE));

        helper.page("usage");
        this.lang("ru_ru").add(helper.pageTitle(), "Использование");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        Чтобы использовать [](item://occultism:familiar_ring), поймайте вызванного и приручённого фамильяра, нажав [#](%1$s)ПКМ[#]() на него, а затем наденьте перстень в качестве [#](%1$s)аксессуара[#](), чтобы использовать эффекты, предоставляемые фамильяром.
                        \\
                        \\
                        Когда фамильяр освобождается из перстня, он признаёт (освободившего его человека) своим новым хозяином.
                        """.formatted(COLOR_PURPLE));

        helper.page("ritual");
        //no text

        helper.entry("craft_wild_trim");
        this.lang("ru_ru").add(helper.entryName(), "Создание отделки «Дебри»");

        helper.page("spotlight");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        В отличие от других ритуалов, создание [](item://minecraft:wild_armor_trim_smithing_template) представляет собой услугу, оказываемую дикими духами, не связывая духа к готовому предмету. Жертвуйте предметами, а дикие духи используют свои силы, чтобы создать вам предмет.
                        """.formatted(COLOR_PURPLE));

        helper.page("ritual");
        //no text

        helper.entry("craft_budding_amethyst");
        this.lang("ru_ru").add(helper.entryName(), "Создание цветущего аметиста");

        helper.page("spotlight");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        В отличие от других ритуалов, создание [](item://minecraft:reinforced_deepslate) представляет собой услугу, оказываемую дикими духами, не связывая духа к готовому предмету. Жертвуйте предметами, а дикие духи используют свои силы, чтобы создать вам предмет.
                        """.formatted(COLOR_PURPLE));

        helper.page("ritual");
        //no text

        helper.entry("craft_reinforced_deepslate");
        this.lang("ru_ru").add(helper.entryName(), "Создание укреплённого глубинного сланца");

        helper.page("spotlight");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        В отличие от других ритуалов, создание [](item://minecraft:reinforced_deepslate) представляет собой услугу, оказываемую дикими духами, не связывая духа к готовому предмету. Жертвуйте предметами, а дикие духи используют свои силы, чтобы создать вам предмет. \\
                        \\
                        Эти блоки могут быть добыты с помощью айзниевой или наполненной кирки.
                        """.formatted(COLOR_PURPLE));

        helper.page("ritual");
        //no text

        helper.entry("repair");
        this.lang("ru_ru").add(helper.entryName(), "Ритуалы для починки");

        helper.page("spotlight");
        this.lang("ru_ru").add(helper.pageTitle(), "Починка");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        Джинн с помощью простых материалов чинит вам мел. Развив оккультный путь, Африт может восстановить рудокопов, починить инструменты и доспехи. Следовательно, какой-любой починенный предмет сохранит свои свойства.
                        """.formatted(COLOR_PURPLE));

        helper.page("ritual_chalks");
        //no text
        helper.page("ritual_miners");
        //no text
        helper.page("ritual_tools");
        //no text
        helper.page("ritual_armors");
        //no text
    }

    private void addFamiliarRitualsCategory(BookContextHelper helper) {
        helper.category("familiar_rituals");

        helper.entry("return_to_rituals");
        this.lang("ru_ru").add(helper.entryName(), "Вернуться к категории ритуалов");

        helper.entry("overview");
        this.lang("ru_ru").add(helper.entryName(), "Ритуалы для фамильяров");

        helper.page("intro");
        this.lang("ru_ru").add(helper.pageTitle(), "Ритуалы для фамильяров");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        Ритуалы для фамильяров вызывают духов для непосредственной помощи вызывателю. Как правило, духи обитают в теле животного. Это позволяет им защищать сущность от распада. Фамильяры предоставляют усиления, но и могут активно защищать вызывателя.
                                """.formatted(COLOR_PURPLE));

        helper.page("ring");
        this.lang("ru_ru").add(helper.pageTitle(), "Экипировка фамильярами");
        this.lang("ru_ru").add(helper.pageText(),
                """
                                          Находчивые вызыватели нашли способ заточать фамильяров в драгоценные камни, которые пассивно накладывают свои усиления. \\
                        [Перстень для фамильяра](entry://crafting_rituals/craft_familiar_ring).
                                                  """.formatted(COLOR_PURPLE));

        helper.page("trading");
        this.lang("ru_ru").add(helper.pageTitle(), "Экипировка фамильярами");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        Фамильяры могут быть свободно торговаться при нахождении в [Перстне для фамильяра](entry://crafting_rituals/craft_familiar_ring).
                        \\
                        \\
                        При освобождении дух признаёт того, кто его выпустил, своим новым хозяином.
                                 """.formatted(COLOR_PURPLE));

        helper.entry("familiar_bat");
        this.lang("ru_ru").add(helper.entryName(), "Фамильяр-летучая мышь");

        helper.page("entity");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        **Предоставляет**: [#](%1$s)Ночное зрение[#]().
                               """.formatted(COLOR_PURPLE));

        helper.page("ritual");
        //no text

        helper.page("description");
        this.lang("ru_ru").add(helper.pageTitle(), "Описание");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        **Улучшение поведения**:\\
                        При улучшении фамильяром-кузнецом, фамильяр-летучая мышь даёт хозяину эффект «Похищение жизни».
                           """.formatted(COLOR_PURPLE));

        helper.entry("familiar_beaver");
        this.lang("ru_ru").add(helper.entryName(), "Фамильяр-бобёр");

        helper.page("entity");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        **Предоставляет**: [#](%1$s)повышенную скорость рубки дерева[#]()
                               """.formatted(COLOR_PURPLE));

        helper.page("ritual");
        //no text

        helper.page("description");
        this.lang("ru_ru").add(helper.pageTitle(), "Описание");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        Фамильяр-бобёр срубает близлежащие деревья, когда они вырастут из обычного саженца в дерево. Он справляется только с небольшими деревьями.
                        \\
                        \\
                        **Улучшение поведения**:\\
                        Даёт бесплатные лакомства при нажатии ПКМ на него пустой рукой.
                           """.formatted(COLOR_PURPLE));

        helper.entry("familiar_beholder");
        this.lang("ru_ru").add(helper.entryName(), "Фамильяр-созерцатель");

        helper.page("entity");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        **Предоставляет**: [#](%1$s)подсвечивание врагов[#](), [#](%1$s)стреляет **ДОЛБАНЫМИ ЛАЗЕРАМИ**[#]().
                               """.formatted(COLOR_PURPLE));

        helper.page("ritual");
        //no text

        helper.page("description");
        this.lang("ru_ru").add(helper.pageTitle(), "Описание");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        Фамильяр-созерцатель подсвечивает эффектом свечения близлежащих существ и стреляет лазерными лучами по врагам. **Поедает** (слабых) **маленьких Шаб-Ниггуратов** для получения временного урона и скорости.
                        \\
                        \\
                        **Улучшение поведения**:\\
                        При улучшении фамильяром-кузнецом, он наделяет своего хозяина устойчивостью к слепоте. Затем, подсвечивание хранителя к тому же, устойчивость распространяется на темноту.
                           """.formatted(COLOR_PURPLE));

        helper.entry("familiar_blacksmith");
        this.lang("ru_ru").add(helper.entryName(), "Фамильяр-кузнец");

        helper.page("entity");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        **Предоставляет**: [#](%1$s)чинит снаряжения при добыче ресурсов[#](), [#](%1$s)улучшает других фамильяров[#]().
                               """.formatted(COLOR_PURPLE));

        helper.page("ritual");
        //no text

        helper.page("description");
        this.lang("ru_ru").add(helper.pageTitle(), "Описание");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        Всякий раз, когда игрок подбирает камень, существует вероятность, что фамильяр-кузнец немножко починит снаряжение игрока.
                        \\
                        \\
                        **Улучшение поведения**: \\
                        Улучшает других фамильяров, но сам не может быть улучшен.
                           """.formatted(COLOR_PURPLE));

        helper.page("description2");
        this.lang("ru_ru").add(helper.pageTitle(), "Улучшение фамильяров");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        Чтобы улучшать других фамильяров, кузнецу необходимо дать железные слитки или блоки, нажав [#](%1$s)ПКМ[#]() на него.
                        \\
                        \\
                        Улучшенные фамильяры предоставляют дополнительные эффекты.
                           """.formatted(COLOR_PURPLE));

        helper.entry("familiar_chimera");
        this.lang("ru_ru").add(helper.entryName(), "Фамильяр-химера");

        helper.page("entity");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        **Предоставляет**: [#](%1$s)ездового, верхового животного[#]().
                               """.formatted(COLOR_PURPLE));

        helper.page("ritual");
        //no text

        helper.page("description");
        this.lang("ru_ru").add(helper.pageTitle(), "Описание");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        Чтобы фамильяр-химера росла, она должна быть накормлена каким-либо мясом. При росте она будет получать бонус к урону и скорости. Как только вырастит, игроки смогут оседлать её. Если кормить её [](item://minecraft:golden_apple), [#](%1$s)коза[#]() отчленится и станет отдельным фамильяром.
                        \\
                        \\
                        Отцеплённая фамильяр-коза может быть использована для получения [Шаб-Ниггурата](entry://familiar_rituals/familiar_shub_niggurath).
                           """.formatted(COLOR_PURPLE));

        helper.page("description2");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        **Улучшение поведения**:\\
                        При улучшении фамильяром-кузнецом, фамильяр-коза получит сигнальный колокольчик. При атаке фамильяра она позвонит в него и **привлечёт врагов** в пределах большого радиуса.
                           """.formatted(COLOR_PURPLE));

        helper.entry("familiar_cthulhu");
        this.lang("ru_ru").add(helper.entryName(), "Фамильяр-Ктулху");

        helper.page("entity");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        **Предоставляет**: [#](%1$s)водное дыхание[#](), [#](%1$s)невозмутимость[#]().
                               """.formatted(COLOR_PURPLE));

        helper.page("ritual");
        //no text

        helper.page("description");
        this.lang("ru_ru").add(helper.pageTitle(), "Описание");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        **Улучшение поведения**:\\
                        При улучшении фамильяром-кузнецом, он служит передвижным источником света.
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
                        Фамильяр-[#](%1$s)Шаб-Ниггурат[#]() напрямую не вызывается. Сперва вызовите [Фамильяра-химеру](entry://familiar_rituals/familiar_chimera) и накормите её [](item://minecraft:golden_apple), чтобы отчленить [#](%1$s)козу[#](). Приведите её в [#](%1$s)лесной биом[#](), затем нажмите на неё [каким-либо чёрным красителем](item://minecraft:black_dye), [](item://minecraft:flint) и [](item://minecraft:ender_eye), чтобы вызвать [#](%1$s)Шаб-Ниггурата[#]().
                           """.formatted(COLOR_PURPLE));

        helper.page("description");
        this.lang("ru_ru").add(helper.pageTitle(), "Описание");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        **Улучшение поведения**:\\
                        При улучшении фамильяром-кузнецом, он получит сигнальный колокольчик. При атаке фамильяра он позвонит в него и **привлечёт врагов** в пределах большого радиуса.
                           """.formatted(COLOR_PURPLE));

        helper.entry("familiar_deer");
        this.lang("ru_ru").add(helper.entryName(), "Фамильяр-олень");

        helper.page("entity");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        **Предоставляет**: [#](%1$s)скорость, прыгучесть и помощь шага[#]()
                                   """.formatted(COLOR_PURPLE));

        helper.page("ritual");
        //no text

        helper.page("description");
        this.lang("ru_ru").add(helper.pageTitle(), "Описание");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        **Улучшение поведения**:\\
                        При улучшении фамильяром-кузнецом, он улучшает эффект «Помощь шага» и атакует близлежащих врагов молотом. Ага, **молотом**.
                           """.formatted(COLOR_PURPLE));

        helper.entry("familiar_devil");
        this.lang("ru_ru").add(helper.entryName(), "Фамильяр-дьявол");

        helper.page("entity");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        **Предоставляет**: [#](%1$s)огнестойкость[#](), [#](%1$s)атакует врагов#]().
                                   """.formatted(COLOR_PURPLE));

        helper.page("ritual");
        //no text

        helper.page("description");
        this.lang("ru_ru").add(helper.pageTitle(), "Описание");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        **Улучшение поведения**:\\
                        При улучшении фамильяром-кузнецом, он кидает мечи на близлежащих врагов.
                           """.formatted(COLOR_PURPLE));

        helper.entry("familiar_dragon");
        this.lang("ru_ru").add(helper.entryName(), "Фамильяр-дракон");

        helper.page("entity");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        **Предоставляет**: [#](%1$s)повышенное получение опыта[#](), любит палочки.
                                   """.formatted(COLOR_PURPLE));

        helper.page("ritual");
        //no text

        helper.page("description");
        this.lang("ru_ru").add(helper.pageTitle(), "Описание");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        Алчные фамильяры могут оседлать фамильяра-дракона, вдобавок наделять его эффектами алчности.
                        \\
                        \\
                        **Улучшение поведения**:\\
                        При улучшении фамильяром-кузнецом, он кидает мечи на близлежащих врагов.
                           """.formatted(COLOR_PURPLE));

        helper.entry("familiar_fairy");
        this.lang("ru_ru").add(helper.entryName(), "Фамильяр-фея");

        helper.page("entity");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        **Предоставляет**: [#](%1$s)помогает фамильярам[#](), [#](%1$s)предотвращает смерть фамильяра[#](), [#](%1$s)истощает жизненную силу врага[#]().
                                   """.formatted(COLOR_PURPLE));

        helper.page("ritual");
        //no text

        helper.page("description");
        this.lang("ru_ru").add(helper.pageTitle(), "Описание");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        Фамильяр-фея **оберегает от смерти других фамильяров** (с перезарядкой), выручает других фамильяров **благоприятными эффектами** и **истощает жизненную силу врагов** для помощи своему хозяину.
                        \\
                        \\
                        **Улучшение поведения**:\\
                        Позволяет собирать драконье дыхание при нажатии ПКМ с помощью бутылочки.
                           """.formatted(COLOR_PURPLE));

        helper.entry("familiar_greedy");
        this.lang("ru_ru").add(helper.entryName(), "Алчный фамильяр");

        helper.page("entity");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        **Предоставляет**: [#](%1$s)увеличенную дальность подбирания[#](), [#](%1$s)подбирает предметы[#]().
                                   """.formatted(COLOR_PURPLE));
        helper.page("ritual");
        //no text

        helper.page("description");
        this.lang("ru_ru").add(helper.pageTitle(), "Описание");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        Алчный фамильяр является Фолиотом, подбирающим ближайшие предметы для хозяина. Если фамильяра поймать в перстень, он увеличит дальность подбирания владельцу.
                        \\
                        \\
                        **Улучшение поведения**:\\
                        фамильяром-кузнецом, он сможет находить блоки для хозяина. Нажмите [#](%1$s)ПКМ[#]() на него блоком, чтобы указать, что именно искать.
                           """.formatted(COLOR_PURPLE));

        helper.entry("familiar_guardian");
        this.lang("ru_ru").add(helper.entryName(), "Фамильяр-страж");

        helper.page("entity");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        **Предоставляет**: [#](%1$s)предотвращает смерть игрока, пока ещё живой[#]().
                                   """.formatted(COLOR_PURPLE));

        helper.page("ritual");
        //no text

        helper.page("description");
        this.lang("ru_ru").add(helper.pageTitle(), "Описание");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        Фамильяр-страж жертвует своей конечностью всякий раз, когда его хозяин близок к смерти, и благодаря этому **предотвращает смерть**. После смерти стража, игрок перестаёт быть защищённым. Будучи вызван, страж появляется со **случайным количеством конечностей**, однако нет гарантии, что вызовется целый страж.
                           """.formatted(COLOR_PURPLE));

        helper.page("description2");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        **Улучшение поведения**:\\
                        При улучшении фамильяром-кузнецом, он вновь приобретёт конечность.
                           """.formatted(COLOR_PURPLE));

        helper.entry("familiar_headless");
        this.lang("ru_ru").add(helper.entryName(), "Фамильяр-безголовый человек на крысе");

        helper.page("entity");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        **Предоставляет**: [#](%1$s)усиление условного урона[#]().
                                   """.formatted(COLOR_PURPLE));

        helper.page("ritual");
        //no text

        helper.page("description");
        this.lang("ru_ru").add(helper.pageTitle(), "Описание");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        Фамильяр-безголовый человек на крысе похищает головы существ возле себя при убийстве монстров. Затем наделяет своему хозяину усиление урона против того типа существа. Если здоровье безголового человека на крысе падает **ниже 50%%**, он погибает. Однако, предоставив ему [](item://minecraft:wheat), [](item://minecraft:stick), [](item://minecraft:hay_block) и [](item://minecraft:pumpkin), может быть воссоздан своим хозяином.
                           """);

        helper.page("description2");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        **Улучшение поведения**:\\
                        При улучшении фамильяром-кузнецом, он наделяет слабостью близлежащих монстров того же типа, чьи головы он украл, к тому же при просмотре в глаза Эндермена владельцем, тот не будет злиться.
                           """.formatted(COLOR_PURPLE));

        helper.entry("familiar_mummy");
        this.lang("ru_ru").add(helper.entryName(), "Фамильяр-мумия");

        helper.page("entity");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        **Предоставляет**: [#](%1$s)эффект уклонения[#](), [#](%1$s)сражается с вашими врагами[#]().
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
                        **Улучшение поведения**:\\
                        При улучшении фамильяром-кузнецом, он будет наносить ещё больше урона и удваивать шанс уклонения.
                            """.formatted(COLOR_PURPLE));

        helper.entry("familiar_otherworld_bird");
        this.lang("ru_ru").add(helper.entryName(), "Фамильяр-дрикрыл");

        helper.page("entity");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        **Предоставляет**: [#](%1$s)мульти-прыжок[#](), [#](%1$s)прыгучесть[#](), [#](%1$s)плавное падение[#]().
                                   """.formatted(COLOR_PURPLE));

        helper.page("ritual");
        //no text

        helper.page("description");
        this.lang("ru_ru").add(helper.pageTitle(), "Описание");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        [#](%1$s)Дрикрылы[#]() — это подкласс [#](%1$s)Джинна[#](), заведомо дружелюбны к людям. Как правило, они принимают облик попугая с тёмно-синим и фиолетовым окрасом. Дрикрылы наделяют своему владельцу ограниченные возможности полёта, будучи рядом.
                        \\
                        \\
                        **Улучшение поведения**:\\
                        Не может быть улучшен фамильяром кузнецом.
                            """.formatted(COLOR_PURPLE));

        helper.page("description2");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        Чтобы получить попугая или фамильяра-попугая для жертвоприношения, попробуйте вызвать их либо с помощью [Ритуал: дикий попугай](entry://possession_rituals/possess_unbound_parrot), или [Ритуал: фамильяр-попугай](entry://familiar_rituals/familiar_parrot).
                        \\
                        \\
                        (**Совет:** Если вы используете моды, защищающие питомцев от смерти, используйте ритуал: дикий попугай!)
                            """.formatted(COLOR_PURPLE));

        helper.entry("familiar_parrot");
        this.lang("ru_ru").add(helper.entryName(), "Фамильяр-попугай");

        helper.page("entity");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        **Предоставляет**: [#](%1$s)собеседника[#]()
                                   """.formatted(COLOR_PURPLE));

        helper.page("ritual");
        //no text

        helper.page("description");
        this.lang("ru_ru").add(helper.pageTitle(), "Описание");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        При проведении этого ритуала [#](%1$s)Фолиот[#]() вызывается **в качестве фамильяра**. Убийство [#](%1$s)курицы[#]() и подношение красителей предназначается для того, чтобы склонить [#](%1$s)Фолиота[#]() принять облик попугая.\\
                        Хотя [#](%1$s)Фолиот[#]() не находится среди умнейших духов — в ряде случаях он дурно понимает указания...
                            """.formatted(COLOR_PURPLE));

        helper.page("description2");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        *Следовательно, если появится [#](ad03fc)курица[#](), это не ошибка, просто не повезло!*
                        \\
                        \\
                        **Улучшение поведения**:\\
                        Не может быть улучшен фамильяром кузнецом.
                           """.formatted(COLOR_PURPLE));
        //no text

        helper.entry("resurrect_allay");
        this.lang("ru_ru").add(helper.entryName(), "Очистка вредины в тихоню");

        helper.page("entity");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        **Предоставляет**: Тихоню
                          """);

        helper.page("ritual");

        helper.page("description");
        this.lang("ru_ru").add(helper.pageTitle(), "Описание");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        Очистка вредины в тихоню в процессе воскресения раскроет её истинное имя.
                          """.formatted(COLOR_PURPLE));
    }

    private void addStorageCategory(BookContextHelper helper) {
        helper.category("storage");
        this.lang("ru_ru").add(helper.categoryName(), "Магическое хранение");

        helper.entry("overview");
        this.lang("ru_ru").add(helper.entryName(), "Магическое хранение");

        helper.page("intro");
        this.lang("ru_ru").add(helper.pageTitle(), "Магическое хранение");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        Проблема известная каждому вызывателю. Существует слишком много оккультной атрибутики, лежащей без дела. Простое решение проблемы, и всё же лучшее — это магическое хранилище!
                        \\
                        \\
                        С помощью духов можно получить доступ к пространственным хранилищам, способным создавать почти неограниченное место для хранения.
                        """.formatted(COLOR_PURPLE));

        helper.page("intro2");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        Выполните следующие действия, обозначенные в этой категории, чтобы получить собственную систему хранения!
                        Действия, связанные с хранением в [Ритуалах для заточения](category://crafting_rituals/) отображают только ритуалы — тогда как здесь отображены **все требуемые действия**, включая создание.
                        """.formatted(COLOR_PURPLE));

        helper.entry("storage_controller");
        this.lang("ru_ru").add(helper.entryName(), "Актуатор хранилища");

        helper.page("intro");
        this.lang("ru_ru").add(helper.pageTitle(), "Актуатор хранилища");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        [](item://occultism:storage_controller) включает в себя [Пространственную матрицу](entry://crafting_rituals/craft_dimensional_matrix), заселённую [#](%1$s)Джинном[#](), что создаёт и управляет пространственным хранилищем и [Основа](entry://crafting_rituals/craft_storage_controller_base), вселённой [#](%1$s)Фолиотом[#](), что перемещает предметы из пространственного хранилища — туда и обратно.
                        """.formatted(COLOR_PURPLE));

        helper.page("usage");
        this.lang("ru_ru").add(helper.pageTitle(), "Использование");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        После создания [](item://occultism:storage_controller) (см. на следующей странице), поставьте его на землю и нажмите [#](%1$s)ПКМ[#]() на него пустой рукой: откроется графический интерфейс — после чего он будет работать подобно очень большому шалкеровому ящику.
                        """.formatted(COLOR_PURPLE));

        helper.page("safety");
        this.lang("ru_ru").add(helper.pageTitle(), "Безопасность прежде всего!");
        this.lang("ru_ru").add(helper.pageText(),
                """
                                          Разрушение регулятора хранилища сохранит все предметы при выпадении предмета: вы ничего не потеряете.
                        То же самое касается разрушения или замены стабилизаторов хранилища (вы узнаете о них позже).
                                          \\
                                          \\
                                          Подобно шалкеровому ящику, ваши предметы в безопасности!
                                          """.formatted(COLOR_PURPLE));

        helper.page("size");
        this.lang("ru_ru").add(helper.pageTitle(), "Такое огромное хранилище!");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        Регулятор хранилища хранит до **128** различных типов предметов (_как увеличить размер узнаете позже_). Помимо этого, размер суммарно ограничивается до 256.000 ед. предметов. Неважно, 256.000 ли у вас различных предметов или 256.000 ед. одного предмета, или солянка.
                        """.formatted(COLOR_PURPLE));

        helper.page("unique_items");
        this.lang("ru_ru").add(helper.pageTitle(), "Уникальные предметы");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        Предметы с уникальными свойствами ("NBT-данные"), например, повреждённое или зачарованное снаряжение, занимает один тип предмета за каждое различие. Например, два деревянных меча с разными уровнями повреждений занимают два типа предмета, а два деревянных меча с одинаковым повреждением (или без) — один тип предмета.
                        """.formatted(COLOR_PURPLE));

        helper.page("config");
        this.lang("ru_ru").add(helper.pageTitle(), "Изменение конфигурации");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        Количество типа предмета и размер хранилища может быть настроено в файле конфигурации"[#](%1$s)occultism-server.toml[#]()" в папке сохранения мира.
                        """.formatted(COLOR_PURPLE));

        helper.page("mods");
        this.lang("ru_ru").add(helper.pageTitle(), "Взаимодействие с модами");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        Для других модов регулятор хранилища функционирует как шалкеровый ящик: всё, что может взаимодействовать с ванильными сундуками и шалкеровыми ящиками — может взаимодействовать и с регулятором хранилища.
                        Машины, которые подсчитывают содержимое хранилища, могут столкнуться с проблемами размеров стека.
                        """.formatted(COLOR_PURPLE));

        helper.page("matrix_ritual");
        //no text

        helper.page("base_ritual");
        //no text

        helper.page("recipe");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        Это блок, который действительно функционирует как хранилище: обязательно создайте его!
                        Установка только [](item://occultism:storage_controller_base) на землю из предыдущего шага не сработает.
                        """.formatted(COLOR_PURPLE));
        //no text

        helper.entry("storage_stabilizer");
        this.lang("ru_ru").add(helper.entryName(), "Расширение хранилища");

        helper.page("spotlight");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        Стабилизаторы хранилища увеличивают размер хранилища: в пространственном хранилище актуатора хранилища. Чем выше уровень стабилизатора, тем больше получит дополнительного хранилища. Следующие записи покажут, как создать каждый уровень.
                        \\
                        \\
                        """.formatted(COLOR_PURPLE));

        helper.page("upgrade");
        this.lang("ru_ru").add(helper.pageTitle(), "Улучшение");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        Он **безопасно уничтожит стабилизатор хранилища**, чтобы улучшить его. Предметы в [Актуаторе хранилища](entry://storage/storage_controller) не будут утеряны или выброшены — вы попросту не сможете добавлять новые предметы, пока не добавите достаточно стабилизаторов хранилища, чтобы снова иметь свободные слоты.
                         """.formatted(COLOR_PURPLE));

        helper.page("build_instructions");
        this.lang("ru_ru").add(helper.pageTitle(), "Инструкция по сборке");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        Регуляторы хранилища необходимо направить на [Пространственную матрицу](entry://crafting_rituals/craft_dimensional_matrix): **один блок над [Актуатором хранилища](entry://storage/storage_controller)**
                        \\
                        \\
                        Их может быть **до 5 блоков** в отдалении от пространственной матрицы (должны быть по прямой линии, в зоне видимости). См. следующую страницу на предмет возможной — очень простой установки.
                        """.formatted(COLOR_PURPLE));

        helper.page("demo");
        this.lang("ru_ru").add(helper.pageTitle(), "Установка стабилизатора хранилища");
        this.lang("ru_ru").add(helper.pageText(),
                """
                        **Примечание:** вам не нужны все 4 стабилизатора, даже один увеличит размер хранилища.
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
        this.lang("ru_ru").advancementDescr("familiars", "Воспользуйтесь ритуалом для вызова фамильяра.");
        this.lang("ru_ru").advancementDescr("familiar.bat", "Заманите ванильную летучую мышь близко к фамильяру-летучей мыши.");
        this.lang("ru_ru").advancementTitle("familiar.bat", "Каннибализм");
        this.lang("ru_ru").advancementDescr("familiar.capture", "Поймайте фамильяра в перстень для фамильяра.");
        this.lang("ru_ru").advancementTitle("familiar.capture", "Поймайте каждого!");
        this.lang("ru_ru").advancementDescr("familiar.cthulhu", "Опечальте фамильяра-Ктулху.");
        this.lang("ru_ru").advancementTitle("familiar.cthulhu", "Вы изверг!");
        this.lang("ru_ru").advancementDescr("familiar.deer", "Понаблюдайте, как фамильяр-олень опорожняется семенами демона.");
        this.lang("ru_ru").advancementTitle("familiar.deer", "Демонические экскременты");
        this.lang("ru_ru").advancementDescr("familiar.devil", "Прикажите фамильяру-демону изрыгнуть пламенем.");
        this.lang("ru_ru").advancementTitle("familiar.devil", "Пламя Преисподней");
        this.lang("ru_ru").advancementDescr("familiar.dragon_nugget", "Дайте фамильяру-дракону кусочек золота.");
        this.lang("ru_ru").advancementTitle("familiar.dragon_nugget", "Договорились!");
        this.lang("ru_ru").advancementDescr("familiar.dragon_ride", "Позвольте алчному фамильяру что-нибудь подобрать во время езды на фамильяре-драконе.");
        this.lang("ru_ru").advancementTitle("familiar.dragon_ride", "В тесной взаимосвязи");
        this.lang("ru_ru").advancementDescr("familiar.greedy", "Позвольте алчному фамильяру подобрать что-нибудь для вас.");
        this.lang("ru_ru").advancementTitle("familiar.greedy", "Посыльный");
        this.lang("ru_ru").advancementDescr("familiar.party", "Потанцуйте с фамильяром.");
        this.lang("ru_ru").advancementTitle("familiar.party", "Потанцуем!");
        this.lang("ru_ru").advancementDescr("familiar.rare", "Заполучите редкий вид фамильяра.");
        this.lang("ru_ru").advancementTitle("familiar.rare", "Редкий друг");
        this.lang("ru_ru").advancementDescr("familiar.root", "Воспользуйтесь ритуалом для вызова фамильяра.");
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
        this.lang("ru_ru").advancementDescr("familiar.chimera_ride", "Оседлайте фамильяра-химеру в момент её полного насыщения.");
        this.lang("ru_ru").advancementTitle("familiar.goat_detach", "Демонтаж");
        this.lang("ru_ru").advancementDescr("familiar.goat_detach", "Дайте фамильяру-химере золотое яблоко.");
        this.lang("ru_ru").advancementTitle("familiar.shub_niggurath_summon", "Чёрный козёл лесов");
        this.lang("ru_ru").advancementDescr("familiar.shub_niggurath_summon", "Превратите фамильяра-козу в нечто отвратительное.");
        this.lang("ru_ru").advancementTitle("familiar.shub_cthulhu_friends", "Страсть к сверхъестественному");
        this.lang("ru_ru").advancementDescr("familiar.shub_cthulhu_friends", "Увидьте, как Шаб-Ниггурат и Ктулху держатся за руки.");
        this.lang("ru_ru").advancementTitle("familiar.shub_niggurath_spawn", "Подумайте о детях!");
        this.lang("ru_ru").advancementDescr("familiar.shub_niggurath_spawn", "Позвольте потомку Шаб-Ниггурата нанести урон по врагу взрывом.");
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
        this.lang("ru_ru").advancementTitle("chalks.root", "Occultism: мелы");
        this.lang("ru_ru").advancementDescr("chalks.root", "Разноцветные.");
        this.lang("ru_ru").advancementTitle("chalks.white", "Применение белого мела");
        this.lang("ru_ru").advancementDescr("chalks.white", "Для первого уровня основы пентакля.");
        this.lang("ru_ru").advancementTitle("chalks.light_gray", "Применение светло-серого мела");
        this.lang("ru_ru").advancementDescr("chalks.light_gray", "Для второго уровня основы пентакля.");
        this.lang("ru_ru").advancementTitle("chalks.gray", "Применение серого мела");
        this.lang("ru_ru").advancementDescr("chalks.gray", "Для третьего уровня основы пентакля.");
        this.lang("ru_ru").advancementTitle("chalks.black", "Применение чёрного мела");
        this.lang("ru_ru").advancementDescr("chalks.black", "Для четвёртого уровня основы пентакля.");
        this.lang("ru_ru").advancementTitle("chalks.brown", "Применение коричневого мела");
        this.lang("ru_ru").advancementDescr("chalks.brown", "Кого приманиваем?");
        this.lang("ru_ru").advancementTitle("chalks.red", "Применение красного мела");
        this.lang("ru_ru").advancementDescr("chalks.red", "Третий уровень!");
        this.lang("ru_ru").advancementTitle("chalks.orange", "Применение оранжевого мела");
        this.lang("ru_ru").advancementDescr("chalks.orange", "Третий уровень?");
        this.lang("ru_ru").advancementTitle("chalks.yellow", "Применение жёлтого мела");
        this.lang("ru_ru").advancementDescr("chalks.yellow", "Одержимость");
        this.lang("ru_ru").advancementTitle("chalks.lime", "Применение лаймового мела");
        this.lang("ru_ru").advancementDescr("chalks.lime", "Второй уровень.");
        this.lang("ru_ru").advancementTitle("chalks.green", "Применение зелёного мела");
        this.lang("ru_ru").advancementDescr("chalks.green", "Привлечение дебрей");
        this.lang("ru_ru").advancementTitle("chalks.cyan", "Применение бирюзового мела");
        this.lang("ru_ru").advancementDescr("chalks.cyan", "Древние знания.");
        this.lang("ru_ru").advancementTitle("chalks.light_blue", "Применение голубого мела");
        this.lang("ru_ru").advancementDescr("chalks.light_blue", "Стабилизатор дебрей.");
        this.lang("ru_ru").advancementTitle("chalks.blue", "Применение синего мела");
        this.lang("ru_ru").advancementDescr("chalks.blue", "Четвёртый уровень.");
        this.lang("ru_ru").advancementTitle("chalks.purple", "Применение фиолетового мела");
        this.lang("ru_ru").advancementDescr("chalks.purple", "Наполнение.");
        this.lang("ru_ru").advancementTitle("chalks.magenta", "Применение пурпурного мела");
        this.lang("ru_ru").advancementDescr("chalks.magenta", "Мощь дракона.");
        this.lang("ru_ru").advancementTitle("chalks.pink", "Применение розового мела");
        this.lang("ru_ru").advancementDescr("chalks.pink", "Сила дикой природы.");
        this.lang("ru_ru").advancementTitle("chalks.rainbow", "Применение радужного мела");
        this.lang("ru_ru").advancementDescr("chalks.rainbow", "Зачем столько мелов?");
        this.lang("ru_ru").advancementTitle("chalks.void", "Применение пустотного мелаChalk");
        this.lang("ru_ru").advancementDescr("chalks.void", "...");
    }

    private void addKeybinds() {
        this.lang("ru_ru").add("key.occultism.category", "Occultism");
        this.lang("ru_ru").add("key.occultism.backpack", "Открыть наплечную сумку");
        this.lang("ru_ru").add("key.occultism.storage_remote", "Открыть средство доступа к хранилищу");
        this.lang("ru_ru").add("key.occultism.familiar.otherworld_bird", "Переключение эффекта перстня: дрикрыл");
        this.lang("ru_ru").add("key.occultism.familiar.greedy_familiar", "Переключение эффекта перстня: алчный");
        this.lang("ru_ru").add("key.occultism.familiar.bat_familiar", "Переключение эффекта перстня: летучая мышь");
        this.lang("ru_ru").add("key.occultism.familiar.deer_familiar", "Переключение эффекта перстня: олень");
        this.lang("ru_ru").add("key.occultism.familiar.cthulhu_familiar", "Переключение эффекта перстня: Крулху");
        this.lang("ru_ru").add("key.occultism.familiar.devil_familiar", "Переключение эффекта перстня: дьявол");
        this.lang("ru_ru").add("key.occultism.familiar.dragon_familiar", "Переключение эффекта перстня: дракон");
        this.lang("ru_ru").add("key.occultism.familiar.blacksmith_familiar", "Переключение эффекта перстня: кузнец");
        this.lang("ru_ru").add("key.occultism.familiar.guardian_familiar", "Переключение эффекта перстня: страж");
        this.lang("ru_ru").add("key.occultism.familiar.headless_familiar", "Переключение эффекта перстня: безголовый человек на крысе");
        this.lang("ru_ru").add("key.occultism.familiar.chimera_familiar", "Переключение эффекта перстня: химера");
        this.lang("ru_ru").add("key.occultism.familiar.goat_familiar", "Переключение эффекта перстня: коза");
        this.lang("ru_ru").add("key.occultism.familiar.shub_niggurath_familiar", "Переключение эффекта перстня: Шаб-Ниггурат");
        this.lang("ru_ru").add("key.occultism.familiar.beholder_familiar", "Переключение эффекта перстня: созерцатель");
        this.lang("ru_ru").add("key.occultism.familiar.fairy_familiar", "Переключение эффекта перстня: фея");
        this.lang("ru_ru").add("key.occultism.familiar.mummy_familiar", "Переключение эффекта перстня: мумия");
        this.lang("ru_ru").add("key.occultism.familiar.beaver_familiar", "Переключение эффекта перстня: бобёр");
    }

    private void addJeiTranslations() {
        this.lang("ru_ru").add("occultism.jei.spirit_fire", "Духовный огонь");
        this.lang("ru_ru").add("occultism.jei.crushing", "Дух-дробильщик");
        this.lang("ru_ru").add("occultism.jei.miner", "Пространственная шахта");
        this.lang("ru_ru").add("occultism.jei.miner.chance", "Коэффициент: %d");
        this.lang("ru_ru").add("occultism.jei.ritual", "Оккультный ритуал");
        this.lang("ru_ru").add("occultism.jei.pentacle", "Пентакль");

        this.lang("ru_ru").add(TranslationKeys.JEI_CRUSHING_RECIPE_MIN_TIER, "Мин. уровень дробильщика: %d");
        this.lang("ru_ru").add(TranslationKeys.JEI_CRUSHING_RECIPE_MAX_TIER, "Макс. уровень дробильщика: %d");
        this.lang("ru_ru").add("jei.occultism.ingredient.tallow.description", "Для получения жира убивайте животных, таких как \u00a72свиньи\u00a7r, \u00a72коровы\u00a7r, \u00a72овцы\u00a7r, \u00a72лошади\u00a7r и \u00a72ламы\u00a7r с помощью ножа мясника.");
        this.lang("ru_ru").add("jei.occultism.ingredient.otherstone.description", "Преимущественно находится в потусторонних рощах. Виден только тогда, когда активен эффект «\u00a76Третий глаз\u00a7r». Дополнительные сведения см. в \u00a76справочнике\u00a7r \u00a76of\u00a7r \u00a76душ\u00a7r.");
        this.lang("ru_ru").add("jei.occultism.ingredient.otherworld_log.description", "Преимущественно находится в потусторонних рощах. Видно только тогда, когда активен эффект «\u00a76Третий глаз\u00a7r». Дополнительные сведения см. в \u00a76справочнике\u00a7r \u00a76of\u00a7r \u00a76душ\u00a7r.");
        this.lang("ru_ru").add("jei.occultism.ingredient.otherworld_sapling.description", "Может быть получен от торговца потусторонними саженцами. Может быть увидено и собрано без эффекта «\u00a76Третий глаз\u00a7r». Дополнительные сведения о том, как вызвать торговца см. в «\u00a76справочнике душ\u00a7r».");
        this.lang("ru_ru").add("jei.occultism.ingredient.otherworld_sapling_natural.description", "Преимущественно находится в потусторонних рощах. Виден только тогда, когда активен эффект «\u00a76Третий глаз\u00a7r». Дополнительные сведения см. в \u00a76справочнике\u00a7r \u00a76of\u00a7r \u00a76душ\u00a7r.");
        this.lang("ru_ru").add("jei.occultism.ingredient.otherworld_leaves.description", "Преимущественно находится в потусторонних рощах. Виден только тогда, когда активен эффект «\u00a76Третий глаз\u00a7r». Дополнительные сведения см. в \u00a76справочнике\u00a7r \u00a76of\u00a7r \u00a76душ\u00a7r.");
        this.lang("ru_ru").add("jei.occultism.ingredient.iesnium_ore.description", "Находится в Незере. Видна только тогда, когда активен эффект «\u00a76Третий глаз\u00a7r». Дополнительные сведения см. в \u00a76справочнике\u00a7r \u00a76of\u00a7r \u00a76душ\u00a7r.");
        this.lang("ru_ru").add("jei.occultism.ingredient.spirit_fire.description", "Бросьте \u00a76плод видения демона\u00a7r на землю и подожгите. Дополнительные сведения см. в \u00a76справочнике\u00a7r \u00a76of\u00a7r \u00a76душ\u00a7r.");
        this.lang("ru_ru").add("jei.occultism.ingredient.datura.description", "Может быть использована для исцеления всех духов и фамильяров, вызванные с помощью ритуалов из Occultism. Нажмите ПКМ на существе, чтобы исцелить на одно сердце.");

        this.lang("ru_ru").add("jei.occultism.ingredient.spawn_egg.familiar_goat.description", "Фамильяр-коза может быть получена благодаря кормлению фамильяра-химеры золотым яблоком. Дополнительные сведения см. в \u00a76справочнике\u00a7r \u00a76of\u00a7r \u00a76дущ\u00a7r.");
        this.lang("ru_ru").add("jei.occultism.ingredient.spawn_egg.familiar_shub_niggurath.description", "Шаб-Ниггурат может быть получен благодаря приводу фамильяра козы в лесной биом, и нажатием на неё сперва чёрным красителем, а затем кремнем и оком эндера. Дополнительные сведения см. в \u00a76справочнике\u00a7r \u00a76of\u00a7r \u00a76душ\u00a7r.");

        this.lang("ru_ru").add("jei.occultism.sacrifice", "Жертва: %s");
        this.lang("ru_ru").add("jei.occultism.summon", "Вызов: %s");
        this.lang("ru_ru").add("jei.occultism.job", "Должность: %s");
        this.lang("ru_ru").add("jei.occultism.item_to_use", "Предмет использования::");
        this.lang("ru_ru").add("jei.occultism.error.missing_id", "Не удалось определить рецепт..");
        this.lang("ru_ru").add("jei.occultism.error.invalid_type", "Недопустимый тип рецепта.");
        this.lang("ru_ru").add("jei.occultism.error.recipe_too_large", "Рецепт больше 3х3.");
        this.lang("ru_ru").add("jei.occultism.error.recipe_items_missing", "Отсутствующие предметы будут игнорироваться..");
        this.lang("ru_ru").add("jei.occultism.error.recipe_no_items", "Совместимые предметы для рецепта не найдены.");
        this.lang("ru_ru").add("jei.occultism.error.recipe_move_items", "Переместите предметы");
        this.lang("ru_ru").add("jei.occultism.error.pentacle_not_loaded", "Пентакль не может быть загружен.");
        this.lang("ru_ru").add("item.occultism.jei_dummy.require_sacrifice", "Требуется жертва!");
        this.lang("ru_ru").add("item.occultism.jei_dummy.require_sacrifice.tooltip", "Для запуска этого ритуала требуется жертва. Обратитесь к справочнику душ за подробными инструкциями.");
        this.lang("ru_ru").add("item.occultism.jei_dummy.require_item_use", "Требуется использовать предмет!");
        this.lang("ru_ru").add("item.occultism.jei_dummy.require_item_use.tooltip", "Для запуска этого ритуала необходимо использовать специальный предмет. Обратитесь к справочнику душ за подробными инструкциями.");
        this.lang("ru_ru").add("item.occultism.jei_dummy.none", "Результат ритуала без предмета");
        this.lang("ru_ru").add("item.occultism.jei_dummy.none.tooltip", "Этот ритуал не создаёт какие-либо предметы.");
    }

    private void addFamiliarSettingsMessages() {
        this.lang("ru_ru").add("message.occultism.familiar.otherworld_bird.enabled", "Эффект перстня — дрикрыл: ВКЛ.");
        this.lang("ru_ru").add("message.occultism.familiar.otherworld_bird.disabled", "Эффект перстня — дрикрыл: ВЫКЛ.");
        this.lang("ru_ru").add("message.occultism.familiar.greedy_familiar.enabled", "Эффект перстня — алчный: ВКЛ.");
        this.lang("ru_ru").add("message.occultism.familiar.greedy_familiar.disabled", "Эффект перстня — алчный: ВЫКЛ.");
        this.lang("ru_ru").add("message.occultism.familiar.bat_familiar.enabled", "Эффект перстня — летучая мышь: ВКЛ.");
        this.lang("ru_ru").add("message.occultism.familiar.bat_familiar.disabled", "Эффект перстня — летучая мышь: ВЫКЛ.");
        this.lang("ru_ru").add("message.occultism.familiar.deer_familiar.enabled", "Эффект перстня — олень: ВКЛ.");
        this.lang("ru_ru").add("message.occultism.familiar.deer_familiar.disabled", "Эффект перстня — олень: ВЫКЛ.");
        this.lang("ru_ru").add("message.occultism.familiar.cthulhu_familiar.enabled", "Эффект перстня — Ктулху: ВКЛ.");
        this.lang("ru_ru").add("message.occultism.familiar.cthulhu_familiar.disabled", "Эффект перстня — Ктулху: ВЫКЛ.");
        this.lang("ru_ru").add("message.occultism.familiar.devil_familiar.enabled", "Эффект перстня — дьявол: ВКЛ.");
        this.lang("ru_ru").add("message.occultism.familiar.devil_familiar.disabled", "Эффект перстня — дьявол: ВЫКЛ.");
        this.lang("ru_ru").add("message.occultism.familiar.dragon_familiar.enabled", "Эффект перстня — дракон: ВКЛ.");
        this.lang("ru_ru").add("message.occultism.familiar.dragon_familiar.disabled", "Эффект перстня — дракон: ВЫКЛ.");
        this.lang("ru_ru").add("message.occultism.familiar.blacksmith_familiar.enabled", "Эффект перстня — кузнец: ВКЛ.");
        this.lang("ru_ru").add("message.occultism.familiar.blacksmith_familiar.disabled", "Эффект перстня — кузнец: ВЫКЛ.");
        this.lang("ru_ru").add("message.occultism.familiar.guardian_familiar.enabled", "Эффект перстня — страж: ВКЛ.");
        this.lang("ru_ru").add("message.occultism.familiar.guardian_familiar.disabled", "Эффект перстня — страж: ВЫКЛ.");
        this.lang("ru_ru").add("message.occultism.familiar.headless_familiar.enabled", "Эффект перстня — безголовый человек на крысе: ВКЛ.");
        this.lang("ru_ru").add("message.occultism.familiar.headless_familiar.disabled", "Эффект перстня — безголовый человек на крысе: ВЫКЛ.");
        this.lang("ru_ru").add("message.occultism.familiar.chimera_familiar.enabled", "Эффект перстня — химера: ВКЛ.");
        this.lang("ru_ru").add("message.occultism.familiar.chimera_familiar.disabled", "Эффект перстня — химера: ВЫКЛ.");
        this.lang("ru_ru").add("message.occultism.familiar.shub_niggurath_familiar.enabled", "Эффект перстня — Шаб-Ниггурат: ВКЛ.");
        this.lang("ru_ru").add("message.occultism.familiar.shub_niggurath_familiar.disabled", "Эффект перстня — Шаб-Ниггурат: ВЫКЛ.");
        this.lang("ru_ru").add("message.occultism.familiar.beholder_familiar.enabled", "Эффект перстня — созерцатель: ВКЛ.");
        this.lang("ru_ru").add("message.occultism.familiar.beholder_familiar.disabled", "Эффект перстня — созерцатель: ВЫКЛ.");
        this.lang("ru_ru").add("message.occultism.familiar.fairy_familiar.enabled", "Эффект перстня — фея: ВКЛ.");
        this.lang("ru_ru").add("message.occultism.familiar.fairy_familiar.disabled", "Эффект перстня — фея: ВЫКЛ.");
        this.lang("ru_ru").add("message.occultism.familiar.mummy_familiar.enabled", "Эффект перстня — мумия: ВКЛ.");
        this.lang("ru_ru").add("message.occultism.familiar.mummy_familiar.disabled", "Эффект перстня — мумия: ВЫКЛ.");
        this.lang("ru_ru").add("message.occultism.familiar.beaver_familiar.enabled", "Эффект перстня — бобёр: ВКЛ.");
        this.lang("ru_ru").add("message.occultism.familiar.beaver_familiar.disabled", "Эффект перстня — бобёр: ВЫКЛ.");
    }

    private void addPentacles() {
        this.lang("ru_ru").addPentacle("otherworld_bird", "Потусторонняя птица");
        this.lang("ru_ru").addPentacle("summon_foliot", "Круг Авиара");
        this.lang("ru_ru").addPentacle("summon_djinni", "Зов Офикса");
        this.lang("ru_ru").addPentacle("summon_unbound_afrit", "Вызов свободного Абраса");
        this.lang("ru_ru").addPentacle("summon_afrit", "Вызов Абраса");
        this.lang("ru_ru").addPentacle("summon_unbound_marid", "Вызов усиленного Абраса");
        this.lang("ru_ru").addPentacle("summon_marid", "Поощряемое привлечение Фатмы");
        this.lang("ru_ru").addPentacle("possess_foliot", "Приманка Гидирина");
        this.lang("ru_ru").addPentacle("possess_djinni", "Порабощение Айгана");
        this.lang("ru_ru").addPentacle("possess_unbound_afrit", "Повелительный вызов свободного Абраса");
        this.lang("ru_ru").addPentacle("possess_afrit", "Повелительный вызов Абраса");
        this.lang("ru_ru").addPentacle("possess_marid", "Присяга Ксеоврента");
        this.lang("ru_ru").addPentacle("craft_foliot", "Вынуждение призрачного Изива");
        this.lang("ru_ru").addPentacle("craft_djinni", "Заточение высшего Стригора");
        this.lang("ru_ru").addPentacle("craft_afrit", "Пожизненное заточение Севиры");
        this.lang("ru_ru").addPentacle("craft_marid", "Перевёрнутая башня Афиксеса");
        this.lang("ru_ru").addPentacle("resurrect_spirit", "Простой круг Сасджейса");
        this.lang("ru_ru").addPentacle("contact_wild_spirit", "Вызов свободного Осорина");
        this.lang("ru_ru").addPentacle("contact_eldritch_spirit", "Связь с Роназом");
    }

    private void addPentacle(String id, String name) {
        this.add(Util.makeDescriptionId("multiblock", ResourceLocation.fromNamespaceAndPath(Occultism.MODID, id)), name);
    }

    private void addRitualDummies() {
        //Custom dummy
        this.lang("ru_ru").add(OccultismItems.RITUAL_DUMMY_CUSTOM_SUMMON.get(), "Пользовательский макет ритуала");
        this.lang("ru_ru").addTooltip(OccultismItems.RITUAL_DUMMY_CUSTOM_SUMMON.get(), "Используется для сборок в качестве запасного варианта для пользовательских ритуалов, не имеющих собственного предмета для ритуала.");
        this.lang("ru_ru").add(OccultismItems.RITUAL_DUMMY_CUSTOM_POSSESS.get(), "Пользовательский макет ритуала");
        this.lang("ru_ru").addTooltip(OccultismItems.RITUAL_DUMMY_CUSTOM_POSSESS.get(), "Используется для сборок в качестве запасного варианта для пользовательских ритуалов, не имеющих собственного предмета для ритуала.");
        this.lang("ru_ru").add(OccultismItems.RITUAL_DUMMY_CUSTOM_CRAFT.get(), "Пользовательский макет ритуала");
        this.lang("ru_ru").addTooltip(OccultismItems.RITUAL_DUMMY_CUSTOM_CRAFT.get(), "Используется для сборок в качестве запасного варианта для пользовательских ритуалов, не имеющих собственного предмета для ритуала.");
        this.lang("ru_ru").add(OccultismItems.RITUAL_DUMMY_CUSTOM_MISC.get(), "Пользовательский макет ритуала");
        this.lang("ru_ru").addTooltip(OccultismItems.RITUAL_DUMMY_CUSTOM_MISC.get(), "Используется для сборок в качестве запасного варианта для пользовательских ритуалов, не имеющих собственного предмета для ритуала.");

        //SUMMON
        //Crusher
        this.autoDummyFactory(OccultismItems.RITUAL_DUMMY_SUMMON_FOLIOT_CRUSHER, "Вызов Фолиота-дробильщика", "Фолиот", "Дробильщик — это дух, вызываемый для размельчения руды в пыль, эффективно удваивая металлопродукцию.\n" + ChatFormatting.GRAY + ChatFormatting.ITALIC + "Примечание: некоторые рецепты требуют высокий или низкий уровень дробильщиков.");
        this.autoDummyFactory(OccultismItems.RITUAL_DUMMY_SUMMON_DJINNI_CRUSHER, "Вызов Джинна-дробильщика", "Джинн", "Дробильщик — это дух, вызываемый для измельчения руды в пыль, эффективно (гораздо), удваивая металлопродукцию.\n" + ChatFormatting.GRAY + ChatFormatting.ITALIC + "Примечание: некоторые рецепты требуют высокий или низкий уровень дробильщиков.");
        this.autoDummyFactory(OccultismItems.RITUAL_DUMMY_SUMMON_AFRIT_CRUSHER, "Вызов Африта-дробильщика", "Африт", "Дробильщик — это дух, вызываемый для измельчения руды в пыль, эффективно (гораздо), удваивая металлопродукцию.\n" + ChatFormatting.GRAY + ChatFormatting.ITALIC + "Примечание: некоторые рецепты требуют высокий или низкий уровень дробильщиков.");
        this.autoDummyFactory(OccultismItems.RITUAL_DUMMY_SUMMON_MARID_CRUSHER, "Вызов Марида-дробильщика", "Марид", "Дробильщик — это дух, вызываемый для измельчения руды в пыль, эффективно (гораздо), удваивая металлопродукцию.\n" + ChatFormatting.GRAY + ChatFormatting.ITALIC + "Примечание: некоторые рецепты требуют высокий или низкий уровень дробильщиков.");
        //Smelter
        this.autoDummyFactory(OccultismItems.RITUAL_DUMMY_SUMMON_FOLIOT_SMELTER, "Вызов Фолиота-литейщика", "Фолиот", "Литейщик — это дух, вызываемый для создания рецептов печи, плавильной печи, коптильни и костра — без использования топлива, и более быстрый в зависимости от духа.");
        this.autoDummyFactory(OccultismItems.RITUAL_DUMMY_SUMMON_DJINNI_SMELTER, "Вызов Джинна-литейщика", "Джинн", "Литейщик — это дух, вызываемый для создания рецептов печи, плавильной печи, коптильни и костра — без использования топлива, и более быстрый в зависимости от духа.");
        this.autoDummyFactory(OccultismItems.RITUAL_DUMMY_SUMMON_AFRIT_SMELTER, "Вызов Африта-литейщика", "Африт", "Литейщик — это дух, вызываемый для создания рецептов печи, плавильной печи, коптильни и костра — без использования топлива, и более быстрый в зависимости от духа.");
        this.autoDummyFactory(OccultismItems.RITUAL_DUMMY_SUMMON_MARID_SMELTER, "Вызов Марида-литейщика", "Марид", "Литейщик — это дух, вызываемый для создания рецептов печи, плавильной печи, коптильни и костра — без использования топлива, и более быстрый в зависимости от духа.");
        //Partner
        this.autoDummyFactory(OccultismItems.RITUAL_DUMMY_SUMMON_DEMONIC_WIFE, "Вызов демонической жены", "Джинн", "Вызывает демоническую жену для поддержки: она будет защищать вас, помогать с готовкой и продлевать продолжительность зелья.");
        this.autoDummyFactory(OccultismItems.RITUAL_DUMMY_SUMMON_DEMONIC_HUSBAND, "Вызов демонического мужа", "Джинн", "Вызывает демонического мужа для поддержки: он будет защищать вас, помогать с готовкой и продлевать продолжительность зелья.");
        //One tier worker
        this.autoDummyFactory(OccultismItems.RITUAL_DUMMY_SUMMON_FOLIOT_LUMBERJACK, "Вызов Фолиота-дровосека", "Фолиот", "Дровосек рубит близлежащие деревья на своём рабочем месте и кладёт выпавшие предметы в указанный сундук.");
        this.autoDummyFactory(OccultismItems.RITUAL_DUMMY_SUMMON_FOLIOT_OTHERSTONE_TRADER, "Вызов торговца потусторонним камнем", "Фолиот", "Торговец потусторонним камнем обменивает обычный камень на потусторонний камень.");
        this.autoDummyFactory(OccultismItems.RITUAL_DUMMY_SUMMON_FOLIOT_SAPLING_TRADER, "Вызов торговца потусторонними саженцами", "Фолиот", "Торговец потусторонними саженцами обменивает природные потусторонние саженцы на стабильные, которые можно собрать без эффекта «Третий глаз».");
        this.autoDummyFactory(OccultismItems.RITUAL_DUMMY_SUMMON_FOLIOT_TRANSPORT_ITEMS, "Вызов Фолиота-транспортировщика", "Фолиот", "Транспортировщик будет перемещать все предметы из одного инвентаря в другой, к которому получает доступ, включая машины.");
        this.autoDummyFactory(OccultismItems.RITUAL_DUMMY_SUMMON_FOLIOT_CLEANER, "Вызов Фолиота-дворника", "Фолиот", "Дворник подбирает выпавшие предметы и кладёт их в указанный инвентарь.");
        this.autoDummyFactory(OccultismItems.RITUAL_DUMMY_SUMMON_DJINNI_MANAGE_MACHINE, "Вызов Джинна-станочника", "Джинн", "Станочник автоматически перемещает предметы между системой пространственного хранилища и присоединёнными инвентарями, а также машинами.");
        this.autoDummyFactory(OccultismItems.RITUAL_DUMMY_SUMMON_DJINNI_DAY_TIME, "Вызов рассвета", "Джинн", "Вызывает Джинна, устанавливающего время на полдень.");
        this.autoDummyFactory(OccultismItems.RITUAL_DUMMY_SUMMON_DJINNI_NIGHT_TIME, "Вызов сумерек", "Джинн", "Вызывает Джинна, устанавливающего время на сумерки.");
        this.autoDummyFactory(OccultismItems.RITUAL_DUMMY_SUMMON_DJINNI_CLEAR_WEATHER, "Вызов безоблачного неба", "Джинн", "Вызывает Джинна, устраняющего погоду.");
        this.autoDummyFactory(OccultismItems.RITUAL_DUMMY_SUMMON_AFRIT_RAIN_WEATHER, "Вызов дождя", "Африт", "Вызывает Африта, вызывающего дождь.");
        this.autoDummyFactory(OccultismItems.RITUAL_DUMMY_SUMMON_AFRIT_THUNDER_WEATHER, "Вызов грозы", "Африт", "Вызывает Африта, вызывающего грозу.");
        //Unbound
        this.autoDummyFactory(OccultismItems.RITUAL_DUMMY_SUMMON_UNBOUND_AFRIT, "Вызов незаточённого Африта", "Африт", "Вызывает незаточённого Африта, который может быть убит для получения сущности Африта.");
        this.autoDummyFactory(OccultismItems.RITUAL_DUMMY_SUMMON_UNBOUND_MARID, "Вызов незаточённого Марида", "Марид", "Вызывает незаточённого Марида, который может быть убит для получения сущности Марида.");
        //POSSESS
        //Familiar
        this.autoDummyFactory(OccultismItems.RITUAL_DUMMY_FAMILIAR_BEAVER, "Вызов фамильяра-бобра", "Фолиот", "Фамильяр-бобёр даёт повышенную скорость рубки хозяину, и добывает близлежащие деревья, когда вырастут из саженца.");
        this.autoDummyFactory(OccultismItems.RITUAL_DUMMY_FAMILIAR_BLACKSMITH, "Вызов фамильяра-кузнеца", "Фолиот", "Фамильяры-кузнецы берут камни, добытые хозяином и используют их для починки снаряжения.");
        this.autoDummyFactory(OccultismItems.RITUAL_DUMMY_FAMILIAR_DEER, "Вызов фамильяра-оленя", "Фолиот", "Фамильяры-олени дают эффект «Прыгучесть» хозяину.");
        this.autoDummyFactory(OccultismItems.RITUAL_DUMMY_FAMILIAR_GREEDY, "Вызов алчного фамильяра", "Фолиот", "Алчные фамильяры подбирают предметы для хозяина. Находясь в заключении перстня для фамильяра, — увеличивает дальность подбирания предметов (словно «Магнит предметов» из мода Cyclic).");
        this.autoDummyFactory(OccultismItems.RITUAL_DUMMY_FAMILIAR_PARROT, "Вызов фамильяра-попугая", "Фолиот", "Фамильяр-попугай ведёт себя точь-в-точь как прирученные попугаи.");
        this.autoDummyFactory(OccultismItems.RITUAL_DUMMY_POSSESS_UNBOUND_PARROT, "Овладение несвязанного попугая", "Фолиот", "Завладевает попугаем, который может быть приручен кем угодно, не только вызывателем.");
        this.autoDummyFactory(OccultismItems.RITUAL_DUMMY_FAMILIAR_BAT, "Вызов фамильяра-летучая мышь", "Джинн", "Фамильяры-летучие мыши дают эффект «Ночное зрение» хозяину.");
        this.autoDummyFactory(OccultismItems.RITUAL_DUMMY_FAMILIAR_BEHOLDER, "Вызов фамильяра-созерцателя", "Джинн", "Фамильяры-созерцатели подсвечивают эффектом свечения близлежащих существ и стреляют лазерными лучами во врагов.");
        this.autoDummyFactory(OccultismItems.RITUAL_DUMMY_FAMILIAR_CHIMERA, "Вызов фамильяра-химеру", "Джинн", "Фамильяры-химеры могут быть накормлены до полного роста для получения скорости атаки и урона. Как только вырастят, игроки смогут оседлать их.");
        this.autoDummyFactory(OccultismItems.RITUAL_DUMMY_FAMILIAR_CTHULHU, "Вызов фамильяра-Ктулху", "Джинн", "Фамильяры-Ктулхи дают эффект «Водное дыхание» хозяину.");
        this.autoDummyFactory(OccultismItems.RITUAL_DUMMY_FAMILIAR_DEVIL, "Вызов фамильяра-дьявола", "Джинн", "Фамильяры-дьяволы дают эффект «Огнестойкость» хозяину.");
        this.autoDummyFactory(OccultismItems.RITUAL_DUMMY_FAMILIAR_DRAGON, "Вызов фамильяра-дракона", "Джинн", "Фамильяры-драконы дают повышенное получение опыта хозяину.");
        this.autoDummyFactory(OccultismItems.RITUAL_DUMMY_FAMILIAR_FAIRY, "Вызов фамильяра-феи", "Джинн", "Фамильяр-фея оберегает от смерти других фамильяров, истощает жизненную силу своих врагов и исцеляет своего хозяина, а также его фамильяров.");
        this.autoDummyFactory(OccultismItems.RITUAL_DUMMY_FAMILIAR_HEADLESS, "Вызов фамильяра-безголового человека на крысе", "Джинн", "Фамильяры-безголовые человеки на крысе увеличивают скорость атаки против врагов хозяина, чьи головы они украли.");
        this.autoDummyFactory(OccultismItems.RITUAL_DUMMY_FAMILIAR_MUMMY, "Вызов фамильяра-мумии", "Джинн", "Фамильяр-мумия является мастером боевых искусств, сражающаяся, чтобы защитить своего хозяина.");
        this.autoDummyFactory(OccultismItems.RITUAL_DUMMY_FAMILIAR_OTHERWORLD_BIRD, "Вызов фамильяра-дрикрыла", "Джинн", "Дрикрылы дают владельцу ограниченные возможности полёта, будучи рядом.");
        this.autoDummyFactory(OccultismItems.RITUAL_DUMMY_POSSESS_UNBOUND_OTHERWORLD_BIRD, "Овладение несвязанного дрикрыла", "Джинн", "Завладевает фамильяром-дрикрылом, который может быть приручен кем угодно, не только вызывателем.");
        this.autoDummyFactory(OccultismItems.RITUAL_DUMMY_FAMILIAR_GUARDIAN, "Вызов фамильяра-стража", "Африт", "Фамильяры-стражи оберегают хозяина от жестокой кончины.");
        this.autoDummyFactory(OccultismItems.RITUAL_DUMMY_POSSESS_IESNIUM_GOLEM, "Вызов айзниевого голема", "Марид", "Вызывает сильного и неуязвимого айзниевого голема для защиты территории.");
        //Possessed
        this.autoDummyFactory(OccultismItems.RITUAL_DUMMY_POSSESS_ENDERMITE, "Вызов одержимого эндермита", "Фолиот", "Одержимый эндермит сбрасывает эндерняк.");
        this.autoDummyFactory(OccultismItems.RITUAL_DUMMY_POSSESS_PHANTOM, "Вызов одержимого фантома", "Фолиот", "При убийстве одержимый фантом всегда сбрасывает минимум одну мембрану фантома, но его легко поймать в ловушку.");
        this.autoDummyFactory(OccultismItems.RITUAL_DUMMY_POSSESS_SKELETON, "Вызов одержимого скелета", "Фолиот", "При убийстве одержимый скелет становится устойчивым к дневному свету и всегда сбрасывает минимум один череп скелета.");
        this.autoDummyFactory(OccultismItems.RITUAL_DUMMY_POSSESS_WITCH, "Вызов одержимой ведьмы", "Фолиот", "Одержимая ведьма будет сбрасывать наполненную бутылочку.");
        this.autoDummyFactory(OccultismItems.RITUAL_DUMMY_POSSESS_ENDERMAN, "Вызов одержимого эндермена", "Джинн", "При убийстве одержимый эндермен всегда сбрасывает минимум один эндер-жемчуг.");
        this.autoDummyFactory(OccultismItems.RITUAL_DUMMY_POSSESS_BEE, "Вызов одержимой пчелы", "Джинн", "Одержимая пчела будет сбрасывать проклятый мёд.");
        this.autoDummyFactory(OccultismItems.RITUAL_DUMMY_POSSESS_GHAST, "Вызов одержимого гаста", "Джинн", "При убийстве одержимый гаст всегда сбрасывает минимум одну слезу гаста.");
        this.autoDummyFactory(OccultismItems.RITUAL_DUMMY_POSSESS_WEAK_SHULKER, "Вызов одержимого слабого шалкера", "Джинн", "При убийстве одержимый слабый шалкер сбрасывает минимум один плод хоруса, кроме того, может сбросить панцирь шалкера.");
        this.autoDummyFactory(OccultismItems.RITUAL_DUMMY_POSSESS_ZOMBIE_PIGLIN, "Вызов одержимого зомбифицированного пиглина", "Африт", "Одержимый зомбифицированный пиглин будет сбрасывать демоническое мясо.");
        this.autoDummyFactory(OccultismItems.RITUAL_DUMMY_POSSESS_WARDEN, "Вызов одержимого хранителя", "Африт", "При убийстве одержимый хранитель всегда сбрасывает минимум шесть осколков эха, кроме того, может сбросить другие древние вещи (кузнечные шаблоны и пластинки).");
        this.autoDummyFactory(OccultismItems.RITUAL_DUMMY_POSSESS_ELDER_GUARDIAN, "Вызов одержимого древнего стража", "Африт", "При убийстве одержимый древний страж сбрасывает минимум одну раковину наутилуса, кроме того, может сбросить сердце моря и обычную добычу.");
        this.autoDummyFactory(OccultismItems.RITUAL_DUMMY_POSSESS_HOGLIN, "Вызов одержимого хоглина", "Африт", "При убийстве у одержимого хоглина есть шанс сбросить незеритовое улучшение.");
        this.autoDummyFactory(OccultismItems.RITUAL_DUMMY_POSSESS_SHULKER, "Вызов одержимого шалкера", "Африт", "При убийстве одержимый шалкер всегда сбрасывает минимум один панцирь шалкера.");
        this.autoDummyFactory(OccultismItems.RITUAL_DUMMY_POSSESS_GOAT, "Вызов козла милосердия", "Марид", "Козёл милосердия будет сбрасывать сущность бессердечия.");
        //Random
        this.autoDummyFactory(OccultismItems.RITUAL_DUMMY_POSSESS_RANDOM_ANIMAL_COMMON, "Вызов случайного обычного животного", "Фолиот", "Вызывает случайное пассивное обычное животное. (Все варианты: курица, корова, свинья, овца, спрут и волк).");
        this.autoDummyFactory(OccultismItems.RITUAL_DUMMY_POSSESS_RANDOM_ANIMAL_WATER, "Вызов случайного водного животного", "Фолиот", "Вызывает случайное пассивное водное животное. (Все варианты: аксолотль, лягушка, дельфин, треска, лосось, тропическая рыба, иглобрюх, спрут, светящийся спрут, головастик, черепаха и снежный голем).");
        this.autoDummyFactory(OccultismItems.RITUAL_DUMMY_POSSESS_RANDOM_ANIMAL_SMALL, "Вызов случайного малого животного", "Фолиот", "Вызывает случайное пассивное малое животное. (Все варианты: тихоня, летучая мышь, пчела, попугай, кошка, оцелот, лиса и кролик).");
        this.autoDummyFactory(OccultismItems.RITUAL_DUMMY_POSSESS_RANDOM_ANIMAL_SPECIAL, "Вызов случайного специального животного", "Джинн", "Вызывает случайное пассивное специальное животное. (Все варианты: броненосец, муушрум, панда, белый медведь, коза, железный голем и нюхач).");
        this.autoDummyFactory(OccultismItems.RITUAL_DUMMY_POSSESS_RANDOM_ANIMAL_RIDEABLE, "Вызов случайного ездового животного", "Джинн", "Вызывает случайное пассивное ездовое животное. (Все варианты: свинья, верблюд, осёл, лошадь, скелет-лошадь, зомби-лошадь, лама, лама торговца, мул и лавомерка).");
        this.autoDummyFactory(OccultismItems.RITUAL_DUMMY_POSSESS_VILLAGER, "Вызов крестьянина", "Джинн", "Вызывает крестьянина или странствующего торговца.");
        //CRAFT
        //Tools
        this.autoDummyFactory(OccultismItems.RITUAL_DUMMY_CRAFT_INFUSED_LENSES, "Создание наполненных линз", "Фолиот", "Эти линзы используются для создания очков, которые дают способность видеть за пределами физического мира.");
        this.autoDummyFactory(OccultismItems.RITUAL_DUMMY_CRAFT_INFUSED_PICKAXE, "Создание наполненной кирки", "Джинн", "Наполняйте кирку для добычи потусторонних руд.");
        this.autoDummyFactory(OccultismItems.RITUAL_DUMMY_CRAFT_SATCHEL, "Создание необычайно большой наплечной сумки", "Фолиот", "Эта наплечная сумка позволяет хранить в себе куча предметов, чем позволяют её физические размеры. Это делает её полезным спутником для путешественника.");
        this.autoDummyFactory(OccultismItems.RITUAL_DUMMY_CRAFT_RITUAL_SATCHEL_T1, "Создание ритуальной наплечной сумки подмастерья", "Фолиот", "Заточает Фолиота в наплечную сумку для пошаговой постройки пентаклей в пользу вызывателя.");
        this.autoDummyFactory(OccultismItems.RITUAL_DUMMY_CRAFT_RITUAL_SATCHEL_T2, "Создание ремесленной ритуальной наплечной сумки", "Африт", "Заточает Африта в наплечную сумку для мгновенной постройки пентаклей в пользу вызывателя.");
        this.autoDummyFactory(OccultismItems.RITUAL_DUMMY_CRAFT_FRAGILE_SOUL_GEM, "Создание хрупкого камня души", "Фолиот", "Хрупкий камень души позволяет временно хранить живых существ. Может быть использован только один раз.");
        this.autoDummyFactory(OccultismItems.RITUAL_DUMMY_CRAFT_SOUL_GEM, "Создание камня души", "Джинн", "Камень души позволяет временно хранить живых существ.");
        this.autoDummyFactory(OccultismItems.RITUAL_DUMMY_CRAFT_FAMILIAR_RING, "Создание перстня для фамильяра", "Джинн", "Перстень для фамильяра позволяет заточать фамильяров. Перстень будет накладывать эффект фамильяра на владельца.");
        this.autoDummyFactory(OccultismItems.RITUAL_DUMMY_CRAFT_TRUE_SIGHT_STAFF, "Создание посоха истинного зрения", "Марид", "Посох истинного зрения позволяет находить, видеть и взаимодействовать с потусторонним миром.");
        //Miners
        this.autoDummyFactory(OccultismItems.RITUAL_DUMMY_CRAFT_DIMENSIONAL_MINESHAFT, "Создание пространственной шахты", "Джинн", "Позволяет духам-рудокопам входить в шахтёрское измерение и выносить обратно ресурсы.");
        this.autoDummyFactory(OccultismItems.RITUAL_DUMMY_CRAFT_MINER_FOLIOT_UNSPECIALIZED, "Вселение Фолиота-рудокопа", "Фолиот", "Вызывайте Фолиота-рудокопа в волшебную лампу.");
        this.autoDummyFactory(OccultismItems.RITUAL_DUMMY_CRAFT_MINER_DJINNI_ORES, "Вселение рудного Джинна-рудокопа.", "Джинн", "Вызывайте рудного Джинна-рудокопа в волшебную лампу.");
        this.autoDummyFactory(OccultismItems.RITUAL_DUMMY_CRAFT_MINER_AFRIT_DEEPS, "Вселение Африта-рудокопа для глубинносланцевой руды", "Африт", "Вызывайте Африта-рудокопа для глубинносланцевой руды в волшебную лампу.");
        this.autoDummyFactory(OccultismItems.RITUAL_DUMMY_CRAFT_MINER_MARID_MASTER, "Вселение мастера Марида-рудокопа", "Марид", "Вызывайте мастера Марида-рудокопа в волшебную лампу.");
        //Storage
        this.autoDummyFactory(OccultismItems.RITUAL_DUMMY_CRAFT_STORAGE_CONTROLLER_BASE, "Создание основы актуатора хранилища", "Фолиот", "Основа актуатора хранилища заключает Фолиота в матрице пространственного хранилища, отвечающего за взаимодействие с предметами.");
        this.autoDummyFactory(OccultismItems.RITUAL_DUMMY_CRAFT_DIMENSIONAL_MATRIX, "Создание пространственной матрицы", "Джинн", "Пространственная матрица — это мостик в малое пространство для хранения предметов.");
        this.autoDummyFactory(OccultismItems.RITUAL_DUMMY_CRAFT_STABILIZER_TIER1, "Создание стабилизатора хранилища 1-го уровня", "Фолиот", "Стабилизатор хранилища позволяет хранить больше предметов в средстве доступа пространственного хранилища.");
        this.autoDummyFactory(OccultismItems.RITUAL_DUMMY_CRAFT_STABILIZER_TIER2, "Создание стабилизатора хранилища 2-го уровня", "Джинн", "Стабилизатор хранилища позволяет хранить больше предметов в средстве доступа пространственного хранилища.");
        this.autoDummyFactory(OccultismItems.RITUAL_DUMMY_CRAFT_STABILIZER_TIER3, "Создание стабилизатора хранилища 3-го уровня", "Африт", "Стабилизатор хранилища позволяет хранить больше предметов в средстве доступа пространственного хранилища.");
        this.autoDummyFactory(OccultismItems.RITUAL_DUMMY_CRAFT_STABILIZER_TIER4, "Создание стабилизатора хранилища 4-го уровня", "Марид", "Стабилизатор хранилища позволяет хранить больше предметов в средстве доступа пространственного хранилища.");
        this.autoDummyFactory(OccultismItems.RITUAL_DUMMY_CRAFT_STABLE_WORMHOLE, "Создание стабильной червоточины", "Фолиот", "Стабильная червоточина позволяет получить доступ к пространственной матрице из удалённого место назначения.");
        this.autoDummyFactory(OccultismItems.RITUAL_DUMMY_CRAFT_STORAGE_REMOTE, "Создание средства доступа к хранилищу", "Джинн", "Средство доступа к хранилищу может быть связано с актуатором хранилища для получения удалённого доступа к предметам.");
        //Materials
        this.autoDummyFactory(OccultismItems.RITUAL_DUMMY_CRAFT_RESEARCH_FRAGMENT_DUST, "Создание пыли фрагмента исследования", "Фолиот", "Фолиот наполнит опыт в изумрудную пыль.");
        this.autoDummyFactory(OccultismItems.RITUAL_DUMMY_CRAFT_NATURE_PASTE, "Создание природной пасты", "Фолиот", "Смешав ингредиенты, Фолиот создаст природную пасту.");
        this.autoDummyFactory(OccultismItems.RITUAL_DUMMY_CRAFT_GRAY_PASTE, "Создание серой пасты", "Джинн", "Смешав ингредиенты, Джинн создаст серую пасту.");
        this.autoDummyFactory(OccultismItems.RITUAL_DUMMY_CRAFT_WITHERITE_DUST, "Создание визеритовой пыли", "Африт", "Африт наполнит незеритовую пыль в сущность визера.");
        this.autoDummyFactory(OccultismItems.RITUAL_DUMMY_CRAFT_DRAGONYST_DUST, "Создание драконистовой пыли", "Марид", "Марид наполнит сущность Эндер-дракона в аметистовую пыль.");
        //Extras
        this.autoDummyFactory(OccultismItems.RITUAL_DUMMY_CRAFT_IESNIUM_SACRIFICIAL_BOWL, "Создание айзниевой жертвенной миски", "Африт", "Айзниевая жертвенная миска выполняет любой ритуал за четверть расчётного времени. Остальные особенности действуют аналогично золотой жертвенной миски.");
        this.autoDummyFactory(OccultismItems.RITUAL_DUMMY_CRAFT_IESNIUM_ANVIL, "Создание айзниевой наковальни", "Марид", "Айзниевая наковальня является достижением по сравнению с обычной наковальней. Все её преимущества см. в справочнике душ.");
        //Repair
        this.autoDummyFactory(OccultismItems.RITUAL_DUMMY_REPAIR_CHALKS, "Починка мела", "Джинн", "Полностью починит мел, вселив в него Джинна.");
        this.autoDummyFactory(OccultismItems.RITUAL_DUMMY_REPAIR_TOOLS, "Починка инструмента", "Африт", "Полностью починит инструмент, вселив в него Африта.");
        this.autoDummyFactory(OccultismItems.RITUAL_DUMMY_REPAIR_ARMORS, "Починка брони", "Африт", "Полностью починит броню, вселив в неё Африта.");
        this.autoDummyFactory(OccultismItems.RITUAL_DUMMY_REPAIR_MINERS, "Восстановление рудокопа", "Африт", "Продлит договор, заключив сделку с Афритом.");
        //MISC
        //Resurrect
        this.autoDummyFactory(OccultismItems.RITUAL_DUMMY_RESURRECT_FAMILIAR, "Воскресение фамильяра", "Фамильяр", "Воскрешает фамильяра из осколка души.");
        this.autoDummyFactory(OccultismItems.RITUAL_DUMMY_RESURRECT_ALLAY, "Очистка вредины в тихоню", "Фамильяр", "Очищает вредину на тихоню с помощью воскресения.");
        //Wild
        this.autoDummyFactory(OccultismItems.RITUAL_DUMMY_WILD_HUNT, "Вызов Дикой охоты", "Дикая природа", "Дикая охота состоит из визер-скелетов и их прислужников, с которых большой шанс получить черепа визер-скелетов, что и с прислужников.");
        this.autoDummyFactory(OccultismItems.RITUAL_DUMMY_WILD_CREEPER, "Вызов орды криперов", "Дикая природа", "Орда криперов состоит из несколько заряженных криперов, с которых выпадает много пластинок.");
        this.autoDummyFactory(OccultismItems.RITUAL_DUMMY_WILD_DROWNED, "Вызов орды утопленников", "Дикая природа", "Орда утопленников состоит из несколько утопленников, с которых выпадают предметы, связанные с испытаниями океана.");
        this.autoDummyFactory(OccultismItems.RITUAL_DUMMY_WILD_HUSK, "Вызов орды кадавров", "Дикая природа", "Орда кадавров состоит из несколько кадавров, с которых выпадают предметы, связанные с испытаниями пустыни.");
        this.autoDummyFactory(OccultismItems.RITUAL_DUMMY_WILD_SILVERFISH, "Вызов орды диких чешуйниц", "Дикая природа", "Орда диких чешуйниц состоит из несколько чешуйниц, с которых выпадают предметы, связанные с испытаниями руин.");
        this.autoDummyFactory(OccultismItems.RITUAL_DUMMY_WILD_WEAK_BREEZE, "Вызов дикого слабого вихря", "Дикая природа", "Дикий слабый вихрь будет сбрасывать ключ испытаний и предметы, связанные с камерой испытаний.");
        this.autoDummyFactory(OccultismItems.RITUAL_DUMMY_WILD_BREEZE, "Вызов дикого вихря", "Дикая природа", "Дикий вихрь будет сбрасывать зловещий ключ испытаний и предметы, связанные с камерой испытаний.");
        this.autoDummyFactory(OccultismItems.RITUAL_DUMMY_WILD_STRONG_BREEZE, "Вызов дикого сильного вихря", "Дикая природа", "Дикий сильный вихрь будет сбрасывать навершие булавы и предметы, связанные с камерой испытаний.");
        this.autoDummyFactory(OccultismItems.RITUAL_DUMMY_WILD_ILLAGER, "Вызов диких разбойников", "Дикая природа", "Вызывает дикого заклинателя и его приспешника.");
        this.autoDummyFactory(OccultismItems.RITUAL_DUMMY_WILD_RANDOM_ANIMAL_COMMON, "Вызов группы случайных обычных животных", "Дикая природа", "Вызывает группу случайных пассивных обычных животных. (Все варианты: курица, корова, свинья, овца, спрут и волк).");
        this.autoDummyFactory(OccultismItems.RITUAL_DUMMY_WILD_RANDOM_ANIMAL_WATER, "Вызов группы случайных водных животных", "Дикая природа", "Вызывает группу случайных пассивных водных животных. (Все варианты: аксолотль, лягушка, дельфин, треска, лосось, тропическая рыба, иглобрюх, спрут, светящийся спрут, головастик, черепаха и снежный голем).");
        this.autoDummyFactory(OccultismItems.RITUAL_DUMMY_WILD_RANDOM_ANIMAL_SMALL, "Вызов группы случайных малых животных", "Дикая природа", "Вызывает группу случайных пассивных малых животных. (Все варианты: тихоня, летучая мышь, пчела, попугай, кошка, оцелот, лиса и кролик).");
        this.autoDummyFactory(OccultismItems.RITUAL_DUMMY_WILD_RANDOM_ANIMAL_RIDEABLE, "Вызов группы случайных специальных животных", "Дикая природа", "Вызывает группу случайных пассивных специальных животных. (Все варианты: свинья, верблюд, осёл, лошадь, лошадь-скелет, зомби-лошадь, лама, лама торговца, мул и лавомерка).");
        this.autoDummyFactory(OccultismItems.RITUAL_DUMMY_WILD_RANDOM_ANIMAL_SPECIAL, "Вызов группы случайных ездовых животных", "Дикая природа", "Вызывает группу случайных пассивных специальных животных. (Все варианты: броненосец, муушрум, панда, белый медведь, коза, железный голем и нюхач).");
        this.autoDummyFactory(OccultismItems.RITUAL_DUMMY_WILD_VILLAGER, "Вызов группы крестьян", "Дикая природа", "Вызывает группу крестьян и одного странствующего торговца.");
        //Forge
        this.autoDummyFactory(OccultismItems.RITUAL_DUMMY_FORGE_BEE_NEST, "Создание пчелиного гнезда", "Дикая природа", "Дикие духи создадут пчелиное гнездо, более эстетичнее пчелиного улья.");
        this.autoDummyFactory(OccultismItems.RITUAL_DUMMY_FORGE_BELL, "Создание колокольчика", "Дикая природа", "Дикие духи создадут колокольчик.");
        this.autoDummyFactory(OccultismItems.RITUAL_DUMMY_FORGE_BUDDING_AMETHYST, "Создание цветущего аметиста", "Дикая природа", "Дикие духи создадут цветущий аметист.");
        this.autoDummyFactory(OccultismItems.RITUAL_DUMMY_FORGE_WILD_TRIM, "Кузнечный шаблон", "Дикая природа", "Дикие духи создадут кузнечный шаблон.");
        this.autoDummyFactory(OccultismItems.RITUAL_DUMMY_FORGE_REINFORCED_DEEPSLATE, "Создание укреплённого глубинного сланца", "Дикая природа", "Дикие духи создадут укреплённый глубинный сланец.");
        this.autoDummyFactory(OccultismItems.RITUAL_DUMMY_FORGE_ELDRITCH_CHALICE, "Создание сверхъестественного потира", "Сверхъестественный", "Сверхъестественные духи создадут сверхъестественный потир, мгновенно выполняющий любые ритуалы, — вот ваш трофей!");
        this.autoDummyFactory(OccultismItems.RITUAL_DUMMY_FORGE_CHALK_RAINBOW, "Создание радужного мела", "Сверхъестественный", "Сверхъестественные духи создадут радужный мел, используемый вместо любого цветного мела.");
        this.autoDummyFactory(OccultismItems.RITUAL_DUMMY_FORGE_CHALK_VOID, "Создание пустотного мела", "Сверхъестественный", "Сверхъестественные духи создадут радужный мел, применяемый вместо любого мела.");
        this.autoDummyFactory(OccultismItems.RITUAL_DUMMY_FORGE_TRINITY_GEM, "Создание камня Троицы", "Сверхъестественный", "Сверхъестественные духи создадут камень Троицы, усовершенствуя камень души.");
        this.autoDummyFactory(OccultismItems.RITUAL_DUMMY_FORGE_STABILIZED_STORAGE, "Создание актуатора для стабилизатора пространственного хранилища", "Сверхъестественный", "Сверхъестественные духи создадут стабилизированный актуатор пространственного хранилища. Работает как актуатор с максимальным количеством стабилизаторов, занимая всего один блок.");
        this.autoDummyFactory(OccultismItems.RITUAL_DUMMY_FORGE_MINER_ANCIENT_ELDRITCH, "Вызов сверхъестественного древнего рудокопа", "Сверхъестественный", "Вызывайте сверхъестественного древнего рудокопа в волшебную лампу.");
    }

    public void autoDummyFactory(DeferredItem<Item> dummy, String name, String tier, String description) {
        this.add(dummy.get(), "Ритуал: " + name);
        this.addTooltip(dummy.get(), description);
        this.addAutoTooltip(dummy.get(), "Уровень: " + tier);
        this.addRitualMessage(dummy, "conditions", "Для этого ритуала удовлетворены не все требования.");
        this.addRitualMessage(dummy, "started", "Начало ритуала: " + name + ".");
        this.addRitualMessage(dummy, "finished", "Ритуал успешно завершён: " + name + ".");
        this.addRitualMessage(dummy, "interrupted", "Нарушение ритуала: " + name + ".");
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
        this.lang("ru_ru").add("dialog.occultism.beaver.no_upgrade", "Фамильяр-кузнец должен улучшить фамильяра-созерцателя, прежде чем он будет разбрасываться лакомствами!");
        this.lang("ru_ru").add("dialog.occultism.fairy.breath_on_cooldown", "Эй, прислушайся, жди!");
        this.lang("ru_ru").add("dialog.occultism.fairy.no_upgrade", "Фамильяр-кузнец должен улучшить фамильяра-фею, прежде чем изрыгать пламенем, словно дракон!");
        this.lang("ru_ru").add("dialog.occultism.devil.sin_on_cooldown", "Ещё один грех будет доступен по истечении: %s тактов!");
        this.lang("ru_ru").add("dialog.occultism.devil.no_upgrade", "Фамильяр-кузнец должен улучшить фамильяра-дьявола, прежде чем совершить грех!");
    }

    private void addModonomiconIntegration() {
        this.lang("ru_ru").add(I18n.RITUAL_RECIPE_ITEM_USE, "Предмет использования: ");
        this.lang("ru_ru").add(I18n.RITUAL_RECIPE_SUMMON, "Вызов: %s");
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
        this.lang("ru_ru").addItemTag(OccultismTags.Items.OTHERWORLD_SAPLINGS, "Потусторонние саженцы");
        this.lang("ru_ru").addItemTag(OccultismTags.Items.OTHERWORLD_SAPLINGS_NATURAL, "Природные потусторонние саженцы");
        this.lang("ru_ru").addItemTag(OccultismTags.Items.BOOK_OF_CALLING_DJINNI, "Книга вызова Джинна");
        this.lang("ru_ru").addItemTag(OccultismTags.Items.BOOK_OF_CALLING_FOLIOT, "Книга вызова Фолиота");
        this.lang("ru_ru").addItemTag(OccultismTags.Items.BOOKS_OF_BINDING, "Книги привязки");
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
        this.lang("ru_ru").addItemTag(OccultismTags.Items.OTHERWORLD_WOOD_DUST, "Пыль из потусторонней древесины");
        this.lang("ru_ru").addItemTag(OccultismTags.Items.OCCULTISM_CANDLES, "Свечи из Occultism");
        this.lang("ru_ru").addItemTag(OccultismTags.Items.Miners.MINERS, "Пространственные рудокопы");
        this.lang("ru_ru").addItemTag(OccultismTags.Items.SCUTESHELL, "Щиток или панцирь");
        this.lang("ru_ru").addItemTag(OccultismTags.Items.BLAZE_DUST, "Пылающая пыль");
        this.lang("ru_ru").addItemTag(OccultismTags.Items.MANUALS, "Руководства");
        this.lang("ru_ru").addItemTag(OccultismTags.Items.TOOLS_KNIFE, "Ножи");
        this.lang("ru_ru").addItemTag(ResourceLocation.fromNamespaceAndPath("curios", "belt"), "Пояса");
        this.lang("ru_ru").addItemTag(ResourceLocation.fromNamespaceAndPath("curios", "hands"), "Руки");
        this.lang("ru_ru").addItemTag(ResourceLocation.fromNamespaceAndPath("curios", "heads"), "Головы");
        this.lang("ru_ru").addItemTag(ResourceLocation.fromNamespaceAndPath("curios", "ring"), "Кольцо");
        this.lang("ru_ru").addItemTag(OccultismTags.Items.DEMONIC_PARTNER_FOOD, "Пища для демонического партнёра");
        this.lang("ru_ru").addItemTag(OccultismTags.Items.OTHERCOBBLESTONE, "Потусторонний булыжник");
        this.lang("ru_ru").addItemTag(OccultismTags.Items.OTHERSTONE, "Потусторонний камень");
        this.lang("ru_ru").addItemTag(OccultismTags.Items.OTHERWORLD_LOGS, "Потусторонние брёвна");
        this.lang("ru_ru").addItemTag(OccultismTags.Items.PENTACLE_MATERIALS, "Материалы для пентакля");
        this.lang("ru_ru").addItemTag(OccultismTags.Items.TOOLS_CHALK, "Мелы");
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
        this.lang("ru_ru").add("emi.category.occultism.crushing", "Измельчение");
        this.lang("ru_ru").add("emi.category.occultism.miner", "Пространственная шахта");
        this.lang("ru_ru").add("emi.category.occultism.ritual", "Ритуалы");
        this.lang("ru_ru").add("emi.occultism.item_to_use", "Предмет использования: %s");
        this.add("emi.occultism.ritual_duration", "%s секунд.");
    }

    private void addConditionMessages() {
        this.lang("ru_ru").add(TranslationKeys.Condition.Ritual.IS_IN_DIMENSION_TYPE_NOT_FULFILLED, "Выполните ритуал в измерении «%s»! Он был выполнен в %s.");
        this.lang("ru_ru").add(TranslationKeys.Condition.Ritual.IS_IN_DIMENSION_TYPE_DESCRIPTION, "Нужно выполнить в измерении «%s».");
        this.lang("ru_ru").add(TranslationKeys.Condition.Ritual.IS_IN_DIMENSION_NOT_FULFILLED, "Выполните ритуал в измерении «%s»! Он был выполнен в «%s».");
        this.lang("ru_ru").add(TranslationKeys.Condition.Ritual.IS_IN_DIMENSION_DESCRIPTION, "Нужно выполнить в измерении «%s».");
        this.lang("ru_ru").add(TranslationKeys.Condition.Ritual.IS_IN_BIOME_NOT_FULFILLED, "Выполните ритуал в биоме «%s»! Он был выполнен в «%s».");
        this.lang("ru_ru").add(TranslationKeys.Condition.Ritual.IS_IN_BIOME_DESCRIPTION, "Нужно выполнить в биоме «%s».");
        this.lang("ru_ru").add(TranslationKeys.Condition.Ritual.IS_IN_BIOME_WITH_TAG_NOT_FULFILLED, "Выполните ритуал в биоме с тегом «%s»! Он был выполнен в биоме «%s», у которого отсутствует тег.");
        this.lang("ru_ru").add(TranslationKeys.Condition.Ritual.IS_IN_BIOME_WITH_TAG_DESCRIPTION, "Нужно выполнить в биоме с тегом «%s».");
        this.lang("ru_ru").add(TranslationKeys.Condition.Ritual.AND_NOT_FULFILLED, "Один или несколько из требуемых условий не были удовлетворены (всё должно быть удовлетворено): %s");
        this.lang("ru_ru").add(TranslationKeys.Condition.Ritual.AND_DESCRIPTION, "Нужно удовлетворить все нижеследующие условия: %s");
        this.lang("ru_ru").add(TranslationKeys.Condition.Ritual.OR_NOT_FULFILLED, "Ни один из требуемых условий не были удовлетворены (должно быть удовлетворено минимум одно): %s");
        this.lang("ru_ru").add(TranslationKeys.Condition.Ritual.OR_DESCRIPTION, "Нужно выполнить минимум один из следующих условий: %s");
        this.lang("ru_ru").add(TranslationKeys.Condition.Ritual.TRUE_NOT_FULFILLED, "Постоянное выполняемое условие по тем или иным причинам не выполняется. Это никогда не должно было происходить.");
        this.lang("ru_ru").add(TranslationKeys.Condition.Ritual.TRUE_DESCRIPTION, "Это условие всегда выполняется.");
        this.lang("ru_ru").add(TranslationKeys.Condition.Ritual.FALSE_NOT_FULFILLED, "Это условие никогда не выполняется. Используйте другое условие в рецепте, чтобы заставить ритуал заработать.");
        this.lang("ru_ru").add(TranslationKeys.Condition.Ritual.FALSE_DESCRIPTION, "Никогда не выполнять это условие.");
        this.lang("ru_ru").add(TranslationKeys.Condition.Ritual.NOT_NOT_FULFILLED, "Условие было удовлетворено, но не должно удовлетворяться: %s");
        this.lang("ru_ru").add(TranslationKeys.Condition.Ritual.NOT_DESCRIPTION, "Следующее условие не должно удовлетворяться: %s");
        this.lang("ru_ru").add(TranslationKeys.Condition.Ritual.ITEM_EXISTS_NOT_FULFILLED, "Предмет %s не существует.");
        this.lang("ru_ru").add(TranslationKeys.Condition.Ritual.ITEM_EXISTS_DESCRIPTION, "Предмет %s должен существовать.");
        this.lang("ru_ru").add(TranslationKeys.Condition.Ritual.MOD_LOADED_NOT_FULFILLED, "Мод «%s» не загружен.");
        this.lang("ru_ru").add(TranslationKeys.Condition.Ritual.MOD_LOADED_DESCRIPTION, "Мод «%s» должен быть загружен.");
        this.lang("ru_ru").add(TranslationKeys.Condition.Ritual.TAG_EMPTY_NOT_FULFILLED, "Тег «%s» не пустой.");
        this.lang("ru_ru").add(TranslationKeys.Condition.Ritual.TAG_EMPTY_DESCRIPTION, "Тег «%s» должен быть пустым.");
    }

    private void addConfigurationTranslations() {
        this.lang("ru_ru").addConfig("visual", "Визуальные настройки");
        this.lang("ru_ru").addConfig("showItemTagsInTooltip", "Показывать теги предмета в подсказках");
        this.lang("ru_ru").addConfig("disableDemonsDreamShaders", "Отключить шейдеры видению демона");
        this.lang("ru_ru").addConfig("disableHolidayTheming", "Отключить шейдеры потусторонним очкам");
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
        this.lang("ru_ru").addConfig("divinationRodHighlightAllResults", "Подсвечивать все результаты стержнем прорицания");
        this.lang("ru_ru").addConfig("divinationRodScanRange", "Радиус сканирования жезла прорицания");
        this.lang("ru_ru").addConfig("disableSpiritFireSuccessSound", "Отключить звук успешности для духовного огня");

        this.lang("ru_ru").addConfig("storage", "Настройки хранилища");
        this.lang("ru_ru").addConfig("stabilizerTier1AdditionalMaxItemTypes", "Максимальное количество дополнительных типов предметов в стабилизаторе 1-го уровня");
        this.lang("ru_ru").addConfig("stabilizerTier1AdditionalMaxTotalItemCount", "Максимальное количество дополнительных предметов в общем количестве предметов в стабилизаторе 1-го уровня");
        this.lang("ru_ru").addConfig("stabilizerTier2AdditionalMaxItemTypes", "Максимальное количество дополнительных типов предметов в стабилизаторе 2-го уровня");
        this.lang("ru_ru").addConfig("stabilizerTier2AdditionalMaxTotalItemCount", "Максимальное количество дополнительных предметов в общем количестве предметов в стабилизаторе 2-го уровня");
        this.lang("ru_ru").addConfig("stabilizerTier3AdditionalMaxItemTypes", "Максимальное количество дополнительных типов предметов в стабилизаторе 3-го уровня");
        this.lang("ru_ru").addConfig("stabilizerTier3AdditionalMaxTotalItemCount", "Максимальное количество дополнительных предметов в общем количестве предметов в стабилизаторе 3-го уровня");
        this.lang("ru_ru").addConfig("stabilizerTier4AdditionalMaxItemTypes", "Максимальное количество дополнительных типов предметов в стабилизаторе 4-го уровня");
        this.lang("ru_ru").addConfig("stabilizerTier4AdditionalMaxTotalItemCount", "Максимальное количество дополнительных предметов в общем количестве предметов в стабилизаторе 4-го уровня");
        this.lang("ru_ru").addConfig("controllerMaxItemTypes", "Максимальное количество типов предметов в регуляторе");
        this.lang("ru_ru").addConfig("controllerMaxTotalItemCount", "Максимальное общее количество предметов в регуляторе");
        this.lang("ru_ru").addConfig("stabilizedControllerStabilizers", "Stabilized Controller Built-in Stabilizers");
        this.lang("ru_ru").addConfig("unlinkWormholeOnBreak", "Отвязывать червоточину при разрушении");

        this.lang("ru_ru").addConfig("spirit_job", "Настройки должности духов");
        this.lang("ru_ru").addConfig("drikwingFamiliarSlowFallingSeconds", "Продолжительность (в секундах) плавного падения, полученное благодаря дрикрылу.");
        this.lang("ru_ru").addConfig("tier1CrusherTimeMultiplier", "Коэффициент времени для операций дробильщика 1-го уровня..");
        this.lang("ru_ru").addConfig("tier2CrusherTimeMultiplier", "Коэффициент времени для операций дробильщика 2-го уровня..");
        this.lang("ru_ru").addConfig("tier3CrusherTimeMultiplier", "Коэффициент времени для операций дробильщика 3-го уровня..");
        this.lang("ru_ru").addConfig("tier4CrusherTimeMultiplier", "Коэффициент времени для операций дробильщика 4-го уровня..");
        this.lang("ru_ru").addConfig("tier1CrusherOutputMultiplier", "Коэффициент продукции для операций дробильщика 1-го уровня..");
        this.lang("ru_ru").addConfig("tier2CrusherOutputMultiplier", "Коэффициент продукции для операций дробильщика 2-го уровня..");
        this.lang("ru_ru").addConfig("tier3CrusherOutputMultiplier", "Коэффициент продукции для операций дробильщика 3-го уровня..");
        this.lang("ru_ru").addConfig("tier4CrusherOutputMultiplier", "Коэффициент продукции для операций дробильщика 4-го уровня..");
        this.lang("ru_ru").addConfig("crusherResultPickupDelay", "Задержка, прежде чем могут быть подобраны предметы из дробильщика.");
        this.lang("ru_ru").addConfig("tier1SmelterTimeMultiplier", "Временной коэффициент для операций литейщика 1-го уровня..");
        this.lang("ru_ru").addConfig("tier2SmelterTimeMultiplier", "Временной коэффициент для операций литейщика 2-го уровня..");
        this.lang("ru_ru").addConfig("tier3SmelterTimeMultiplier", "Временной коэффициент для операций литейщика 3-го уровня..");
        this.lang("ru_ru").addConfig("tier4SmelterTimeMultiplier", "Временной коэффициент для операций литейщика 4-го уровня..");
        this.lang("ru_ru").addConfig("smelterResultPickupDelay", "Задержка, прежде чем могут быть подобраны предметы из литейщика.");
        this.lang("ru_ru").addConfig("blacksmithFamiliarRepairChance", "Шанс фамильяру кузнецу починить предмет каждый такт.");
        this.lang("ru_ru").addConfig("blacksmithFamiliarUpgradeCost", "Стоимость (в уровнях опыта) обновления предметов фамильяром кузнецом.");
        this.lang("ru_ru").addConfig("blacksmithFamiliarUpgradeCooldown", "Перезарядка (в тактах), прежде чем фамильяр кузнец снова сможет улучшать предметы.");
        this.lang("ru_ru").addConfig("greedySearchRange", "Дальность поиска по горизонтали алчного фамильяра обновлён.");
        this.lang("ru_ru").addConfig("greedyVerticalSearchRange", "Дальность поиска по вертикали алчного фамильяра обновлён.");

        this.lang("ru_ru").addConfig("rituals", "Настройки ритуала");
        this.lang("ru_ru").addConfig("enableClearWeatherRitual", "Включить условия ритуалу для ясной погоды.");
        this.lang("ru_ru").addConfig("enableRainWeatherRitual", "Включить условия ритуалу для вызова дождливой погоды.");
        this.lang("ru_ru").addConfig("enableThunderWeatherRitual", "Включить условия ритуалу для вызова грозы.");
        this.lang("ru_ru").addConfig("enableDayTimeRitual", "Разрешить ритуалу изменять время на день.");
        this.lang("ru_ru").addConfig("enableNightTimeRitual", "Разрешить ритуалу изменять время на ночь.");
        this.lang("ru_ru").addConfig("enableRemainingIngredientCountMatching", "Включить соответствия оставшихся ингредиентов в рецептах ритуала.");
        this.lang("ru_ru").addConfig("ritualDurationMultiplier", "Коэффициент регулирования продолжительности всех ритуалов.");
        this.lang("ru_ru").addConfig("possibleSpiritNames", "Возможные имена духов");

        this.lang("ru_ru").addConfig("dimensional_mineshaft", "Настройки пространственной шахты");
        this.lang("ru_ru").addConfig("miner_foliot_unspecialized", "Неспециализированный Фолиот-рудокоп");
        this.lang("ru_ru").addConfig("miner_djinni_ores", "Рудный Джинн-рудокоп");
        this.lang("ru_ru").addConfig("miner_afrit_deeps", "Африт-рудокоп для глубинносланцевой руды");
        this.lang("ru_ru").addConfig("miner_marid_master", "Мастер Марид-рудокоп");
        this.lang("ru_ru").addConfig("miner_ancient_eldritch", "Сверхъестественный древний рудокоп");

        this.lang("ru_ru").addConfig("maxMiningTime", "Максимальное время добычи");
        this.lang("ru_ru").addConfig("rollsPerOperation", "Циклов за операцию");
        this.lang("ru_ru").addConfig("durability", "Прочность");

        this.lang("ru_ru").addConfig("items", "Предметы");
        this.lang("ru_ru").addConfig("anyOreDivinationRod", "Прорицание c:ores");
        this.lang("ru_ru").addConfig("minerOutputBeforeBreak", "Сохранить рудокопов до разрушения");
        this.lang("ru_ru").addConfig("unbreakableChalks", "Неломкость мелов");
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
        this.lang("ru_ru").add("occultism.waila.current_ritual", "Текущий ритуал: %s");
        this.lang("ru_ru").add("occultism.waila.no_current_ritual", "Отсутствует текущий ритуал.");
        this.lang("ru_ru").add("occultism.waila.no_item_use", "Требуемый предмет не использовался");
        this.lang("ru_ru").add("occultism.waila.no_sacrifice", "Не выполнено требуемое жертвоприношения.");
        this.lang("ru_ru").add("occultism.waila.foliot", "Фолиот");
        this.lang("ru_ru").add("occultism.waila.foliot_age", "Foliot: осталось %s секунд.");
        this.lang("ru_ru").add("occultism.waila.djinni", "Джинн");
        this.lang("ru_ru").add("occultism.waila.djinni_age", "Djinni: осталось %s секунд.");
        this.lang("ru_ru").add("occultism.waila.afrit", "Африт");
        this.lang("ru_ru").add("occultism.waila.afrit_age", "Afrit: осталось %s секунд.");
        this.lang("ru_ru").add("occultism.waila.marid", "Марид");
        this.lang("ru_ru").add("occultism.waila.marid_age", "Marid: осталось %s секунд.");
    }
}
