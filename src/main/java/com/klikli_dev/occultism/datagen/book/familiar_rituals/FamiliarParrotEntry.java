package com.klikli_dev.occultism.datagen.book.familiar_rituals;

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

public class FamiliarParrotEntry extends EntryProvider {

    public static final String ENTRY_ID = "familiar_parrot";

    public FamiliarParrotEntry(CategoryProvider parent) {
        super(parent);
    }

    @Override
    protected void generatePages() {
        this.page("entity", () -> BookEntityPageModel.create()
                .withEntityId("minecraft:parrot")
                .withText(this.context().pageText()));
        this.pageText("**Provides**: [#](ad03fc)Company[#]()\n");

        this.page("ritual", () -> BookRitualRecipePageModel.create()
                .withRecipeId1(this.modLoc("ritual/familiar_parrot")));

        this.page("description", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText()));
        this.pageTitle("Description");
        this.pageText("In this ritual a [#](ad03fc)Foliot[#]() is summoned **as a familiar**, the slaughter of a [#](ad03fc)Chicken[#]() and the offering of dyes are intended to entice the [#](ad03fc)Foliot[#]() to take the shape of a parrot.\\\nAs [#](ad03fc)Foliot[#]() are not among the smartest spirits, they sometimes misunderstand the instructions ...\n");

        this.page("description2", () -> BookTextPageModel.create()
                .withText(this.context().pageText()));
        this.pageText("*This means, if a [#](ad03fc)Chicken[#]() is spawned, that's not a bug, just bad luck!*\n\\\n\\\n**Upgrade Behaviour**\\\nCannot be upgraded by the blacksmith familiar.\n");
    }

    @Override
    protected String entryName() {
        return "Parrot Familiar";
    }

    @Override
    protected String entryDescription() {
        return "";
    }

    @Override
    protected GuiSprite entryBackground() {
        return EntryBackground.DEFAULT;
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(this.modLoc("textures/gui/book/parrot.png"));
    }

    @Override
    protected String entryId() {
        return ENTRY_ID;
    }
}
