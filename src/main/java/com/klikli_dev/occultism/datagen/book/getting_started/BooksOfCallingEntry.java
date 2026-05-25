package com.klikli_dev.occultism.datagen.book.getting_started;

import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.EntryBackground;
import com.klikli_dev.modonomicon.api.datagen.EntryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookEntryModel;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.*;
import com.klikli_dev.modonomicon.client.gui.book.theme.GuiSprite;
import com.klikli_dev.occultism.integration.modonomicon.pages.BookBindingCraftingRecipePageModel;
import com.klikli_dev.occultism.integration.modonomicon.pages.BookRitualRecipePageModel;
import com.klikli_dev.occultism.integration.modonomicon.pages.BookSpiritFireRecipePageModel;
import com.klikli_dev.occultism.registry.OccultismBlocks;
import com.klikli_dev.occultism.registry.OccultismItems;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

public class BooksOfCallingEntry extends EntryProvider {

    public static final String ENTRY_ID = "books_of_calling";

    public BooksOfCallingEntry(CategoryProvider parent) {
        super(parent);
    }

    @Override
    protected void generatePages() {
        this.page("intro", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText()));
        this.pageTitle("Books of Calling");
        this.pageText("Books of Calling allow to control a summoned spirit, and to store it to prevent essence decay or move it more easily.\n\\\n\\\nOnly spirits that require precise instructions - such as a work area or drop-off storage - come with a book of calling.\n");

        this.page("usage", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText()));
        this.pageTitle("Usage");
        this.pageText("- [#](ad03fc)Right-click[#]() air to open the configuration screen\n- [#](ad03fc)Shift-right-click[#]() a block to apply the action selected in the configuration screen\n- [#](ad03fc)Shift-right-click[#]() a spirit to capture it (must be of the same type)\n- [#](ad03fc)Right-click[#]() with a book with a captured spirit to release it\n");

        this.page("obtaining", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText()));
        this.pageTitle("How to obtain Books of Calling");
        this.pageText("If a summoned spirit supports the use of a Book of Calling, the summoning ritual will automatically spawn a book in the world alongside the spirit.\n\\\n\\\nIf you **lose the book**, there are also crafting recipes that just provide the book (without summoning a spirit).\n");

        this.page("obtaining2", () -> BookTextPageModel.create()
                .withText(this.context().pageText()));
        this.pageText("The recipes can be found in this book or via JEI.\n\\\n\\\n[#](ad03fc)Shift-right-click[#]() the spirit with the crafted book to assign it.\n");

        this.page("storage", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText()));
        this.pageTitle("Storing Spirits");
        this.pageText("To store spirits that do not have a fitting book of calling, you can use a [Soul Gem](entry://occultism:dictionary_of_spirits/crafting_rituals/craft_soul_gem).\nSoul gems are much more versatile and allow to store almost all types of entities even animals and monsters, but not players or bosses.\n");
    }

    @Override
    protected String entryName() {
        return "Books of Calling";
    }

    @Override
    protected String entryDescription() {
        return "Telling your spirits what to do";
    }

    @Override
    protected GuiSprite entryBackground() {
        return EntryBackground.DEFAULT;
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(OccultismItems.BOOK_OF_CALLING_DJINNI_MANAGE_MACHINE.get());
    }

    @Override
    protected String entryId() {
        return ENTRY_ID;
    }
}
