package com.klikli_dev.occultism.datagen.book.getting_started;

import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.EntryBackground;
import com.klikli_dev.modonomicon.api.datagen.EntryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookCraftingRecipePageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookTextPageModel;
import com.klikli_dev.modonomicon.client.gui.book.theme.GuiSprite;
import com.klikli_dev.occultism.integration.modonomicon.pages.BookBindingCraftingRecipePageModel;
import com.klikli_dev.occultism.integration.modonomicon.pages.BookSpiritFireRecipePageModel;
import com.klikli_dev.occultism.registry.OccultismItems;
import net.minecraft.world.item.ItemStackTemplate;

public class BooksOfBindingEntry extends EntryProvider {

    public static final String ENTRY_ID = "books_of_binding";

    public BooksOfBindingEntry(CategoryProvider parent) {
        super(parent);
    }

    @Override
    protected void generatePages() {
        this.page("intro", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText()));
        this.pageTitle("Books of Binding");
        this.pageText("To call forth a spirit, a [#](ad03fc)Book of Binding[#]() must be used in the ritual.\nThere is a type of book corresponding to each type (or tier) of spirit.\nTo identify a spirit to summon, it's name must be written in the [#](ad03fc)Book of Binding[#](), resulting in a [#](ad03fc)Bound Book of Binding[#]() that can be used in the ritual.\n");

        this.page("intro2", () -> BookTextPageModel.create()
                .withText(this.context().pageText()));
        this.pageText("**Note:** *The spirit names are eye candy only*, that means they are not relevant for the recipe. As long as you have the right spirit type in your book of binding it can be used.\n");

        this.page("purified_ink_recipe", () -> BookSpiritFireRecipePageModel.create()
                .withRecipeId1(this.modLoc("spirit_fire/purified_ink"))
                .withText(this.context().pageText()));
        this.pageText("In order to craft [#](ad03fc)Books of Binding[#]() to summon spirits, you need purified ink. Simply drop any black dye into [](item://occultism:spirit_fire) to purify it.\n");

        this.page("awakened_feather_recipe", () -> BookSpiritFireRecipePageModel.create()
                .withRecipeId1(this.modLoc("spirit_fire/awakened_feather"))
                .withText(this.context().pageText()));
        this.pageText("In order to craft [#](ad03fc)Books of Binding[#]() to summon spirits, you also need awakened feather. Simply drop any feather into [](item://occultism:spirit_fire) to awakened it.\n");

        this.page("taboo_book_recipe", () -> BookSpiritFireRecipePageModel.create()
                .withRecipeId1(this.modLoc("spirit_fire/taboo_book"))
                .withText(this.context().pageText()));
        this.pageText("Lastly you need taboo book to craft [#](ad03fc)Books of Binding[#]() to summon spirits. Simply drop a book into [](item://occultism:spirit_fire) to get it.\n");

        this.page("book_of_binding_foliot_recipe", () -> BookCraftingRecipePageModel.create()
                .withRecipeId1(this.modLoc("crafting/book_of_binding_foliot"))
                .withText(this.context().pageText()));
        this.pageText("Craft a book of binding that will be used to call forth a [#](ad03fc)Foliot[#]() spirit.\n");

        this.page("book_of_binding_bound_foliot_recipe", () -> BookBindingCraftingRecipePageModel.create()
                .withRecipeId1()
                .withUnboundBook(new ItemStackTemplate(OccultismItems.BOOK_OF_BINDING_FOLIOT.get()))
                .withText(this.context().pageText()));
        this.pageText("Add the name of the spirit to summon to your book of binding by crafting it with the Dictionary of Spirits. The Dictionary will not be used up.\n");

        this.page("book_of_binding_djinni_recipe", () -> BookCraftingRecipePageModel.create()
                .withRecipeId1(this.modLoc("crafting/book_of_binding_djinni")));

        this.page("book_of_binding_bound_djinni_recipe", () -> BookBindingCraftingRecipePageModel.create()
                .withRecipeId1()
                .withUnboundBook(new ItemStackTemplate(OccultismItems.BOOK_OF_BINDING_DJINNI.get())));

        this.page("book_of_binding_afrit_recipe", () -> BookCraftingRecipePageModel.create()
                .withRecipeId1(this.modLoc("crafting/book_of_binding_afrit")));

        this.page("book_of_binding_bound_afrit_recipe", () -> BookBindingCraftingRecipePageModel.create()
                .withRecipeId1()
                .withUnboundBook(new ItemStackTemplate(OccultismItems.BOOK_OF_BINDING_AFRIT.get())));

        this.page("book_of_binding_marid_recipe", () -> BookCraftingRecipePageModel.create()
                .withRecipeId1(this.modLoc("crafting/book_of_binding_marid")));

        this.page("book_of_binding_bound_marid_recipe", () -> BookBindingCraftingRecipePageModel.create()
                .withRecipeId1()
                .withUnboundBook(new ItemStackTemplate(OccultismItems.BOOK_OF_BINDING_MARID.get())));

        this.page("book_of_binding_empty", () -> BookSpiritFireRecipePageModel.create()
                .withRecipeId1(this.modLoc("spirit_fire/book_of_binding_empty"))
                .withText(this.context().pageText()));
        this.pageText(" Alternatively, you can directly use the Binding Book: Empty instead of the previous three items. There are two ways to obtain this book. Place this book in the center of dyes to get specific book of binding.\n");

        this.page("book_of_binding_empty_recipe", () -> BookCraftingRecipePageModel.create()
                .withRecipeId2(this.modLoc("crafting/book_of_binding_empty")));
    }

    @Override
    protected String entryName() {
        return "Books of Binding";
    }

    @Override
    protected String entryDescription() {
        return "Or how to identify your spirit";
    }

    @Override
    protected GuiSprite entryBackground() {
        return EntryBackground.DEFAULT;
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(OccultismItems.BOOK_OF_BINDING_BOUND_FOLIOT.get());
    }

    @Override
    protected String entryId() {
        return ENTRY_ID;
    }
}
