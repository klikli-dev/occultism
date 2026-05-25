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

public class SpiritMinersEntry extends EntryProvider {

    public static final String ENTRY_ID = "spirit_miners";

    public SpiritMinersEntry(CategoryProvider parent) {
        super(parent);
    }

    @Override
    protected void generatePages() {
        this.page("spotlight", () -> BookSpotlightPageModel.create()
                .withItem(Ingredient.of(OccultismItems.MINER_FOLIOT_UNSPECIALIZED.get()))
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText()));
        this.pageTitle("Spirit Miners");
        this.pageText("Lamps are commonly used to access a [#](ad03fc)Mining Dimension[#]() and act as (*lag free*) [#](ad03fc)Void Miners[#]().\n This is a great way to get resources without having to go mining in the overworld (or other dimesions) yourself.\n");

        this.page("crafting", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText()));
        this.pageTitle("Crafting");
        this.pageText("By summoning a spirit into a Magic Lamp and placing it in a [Dimensional Mineshaft (see next step)](entry://occultism:dictionary_of_spirits/getting_started/mineshaft) it can be made to mine for you in a [#](ad03fc)Mining Dimension[#]().\nSee [Foliot Miner](entry://occultism:dictionary_of_spirits/crafting_rituals/craft_foliot_miner) and the subsequent entries for information on how to craft spirit miners.\n");
    }

    @Override
    protected String entryName() {
        return "Spirit Miners";
    }

    @Override
    protected String entryDescription() {
        return "It's Free Real Estate (-> Resources)";
    }

    @Override
    protected GuiSprite entryBackground() {
        return EntryBackground.DEFAULT;
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(OccultismItems.MINER_FOLIOT_UNSPECIALIZED.get());
    }

    @Override
    protected String entryId() {
        return ENTRY_ID;
    }
}
