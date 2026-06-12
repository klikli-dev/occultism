package com.klikli_dev.occultism.datagen.book.getting_started;

import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.EntryBackground;
import com.klikli_dev.modonomicon.api.datagen.EntryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookCraftingRecipePageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookSmeltingRecipePageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookSpotlightPageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookTextPageModel;
import com.klikli_dev.modonomicon.client.gui.book.theme.GuiSprite;
import com.klikli_dev.occultism.integration.modonomicon.pages.BookSpiritFireRecipePageModel;
import com.klikli_dev.occultism.registry.OccultismItems;
import net.minecraft.world.item.crafting.Ingredient;

public class RitualPrepChalkEntry extends EntryProvider {

    public static final String ENTRY_ID = "ritual_prep_chalk";

    public RitualPrepChalkEntry(CategoryProvider parent) {
        super(parent);
    }

    @Override
    protected void generatePages() {
        this.page("intro", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText()));
        this.pageTitle("Ritual Preparations: Chalks");
        this.pageText("To summon spirits from the [#](ad03fc)Other Place[#]() in *relative* safety,\nyou need to draw a fitting pentacle using chalk to contain their powers.\n");

        this.page("white_chalk", () -> BookSpotlightPageModel.create()
                .withItem(Ingredient.of(OccultismItems.CHALK_WHITE.get()))
                .withText(this.context().pageText()));
        this.pageText("White chalk is used to draw the most basic pentacles, such as for our first ritual.\n\\\n\\\nMore powerful summonings require appropriate more advanced chalk, see [Chalks](entry://occultism:dictionary_of_spirits/getting_started/chalks) for more information.\n");

        this.page("burnt_otherstone_recipe", () -> BookSmeltingRecipePageModel.create()
                .withRecipeId1(this.modLoc("smelting/burnt_otherstone")));

        this.page("otherworld_ashes_recipe", () -> BookSpiritFireRecipePageModel.create()
                .withRecipeId1(this.modLoc("spirit_fire/otherworld_ashes")));

        this.page("impure_white_chalk_recipe", () -> BookCraftingRecipePageModel.create()
                .withRecipeId1(this.modLoc("crafting/chalk_white_impure")));

        this.page("white_chalk_recipe", () -> BookSpiritFireRecipePageModel.create()
                .withRecipeId1(this.modLoc("spirit_fire/chalk_white")));

        this.page("usage", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText()));
        this.pageTitle("Usage");
        this.pageText("Right-click on a block with the chalk to draw a single glyph. For decorative purposes you can repeatedly click a block to cycle through glyphs. The shown glyph does not matter for the ritual, only the color.\n");
    }

    @Override
    protected String entryName() {
        return "Ritual Preparations: Chalks";
    }

    @Override
    protected String entryDescription() {
        return "Signs to find them, Signs to bring them all, and in the darkness bind them.";
    }

    @Override
    protected GuiSprite entryBackground() {
        return EntryBackground.DEFAULT;
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(OccultismItems.CHALK_WHITE.get());
    }

    @Override
    protected String entryId() {
        return ENTRY_ID;
    }
}
