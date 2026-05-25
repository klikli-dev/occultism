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

public class MineshaftEntry extends EntryProvider {

    public static final String ENTRY_ID = "mineshaft";

    public MineshaftEntry(CategoryProvider parent) {
        super(parent);
    }

    @Override
    protected void generatePages() {
        this.page("spotlight", () -> BookSpotlightPageModel.create()
                .withItem(Ingredient.of(OccultismBlocks.DIMENSIONAL_MINESHAFT.get()))
                .withText(this.context().pageText()));
        this.pageText("This block acts as a portal, for spirits only, to the [#](ad03fc)Mining Dimension[#](). Place a Magic Lamp with a Miner Spirit in it, to make it mine for you.\n");

        this.page("crafting", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText()));
        this.pageTitle("Crafting");
        this.pageText("See [Dimensional Mineshaft](entry://occultism:dictionary_of_spirits/crafting_rituals/craft_dimensional_mineshaft) in the [Binding Rituals](category://occultism:dictionary_of_spirits/crafting_rituals) Category.\n");
    }

    @Override
    protected String entryName() {
        return "Dimensional Mineshaft";
    }

    @Override
    protected String entryDescription() {
        return "Ethically questionable, but very profitable";
    }

    @Override
    protected GuiSprite entryBackground() {
        return EntryBackground.DEFAULT;
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(OccultismBlocks.DIMENSIONAL_MINESHAFT.get());
    }

    @Override
    protected String entryId() {
        return ENTRY_ID;
    }
}
