package com.klikli_dev.occultism.datagen.book.familiar_rituals;

import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.EntryBackground;
import com.klikli_dev.modonomicon.api.datagen.EntryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookEntityPageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookTextPageModel;
import com.klikli_dev.modonomicon.client.gui.book.theme.GuiSprite;
import com.klikli_dev.occultism.integration.modonomicon.pages.BookRitualRecipePageModel;

public class FamiliarParrotEntry extends EntryProvider {

    public static final String ENTRY_ID = "familiar_parrot";

    public FamiliarParrotEntry(CategoryProvider parent) {
        super(parent);
    }

    @Override
    protected void generatePages() {
        this.page("entity", () -> BookEntityPageModel.create()
                .withEntityId("minecraft:parrot")
                .withText(this.context().pageText())
                .withScale(1.2f)
                .withOffset(-0.4f));
        this.pageText("It's a bird!");

        this.page("ritual", () -> BookRitualRecipePageModel.create()
                .withRecipeId1(this.modLoc("ritual/familiar_parrot")));

        this.page("description", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText()));
        this.pageTitle("Description");
        this.pageText("In this ritual a [#](ad03fc)Foliot[#]() is summoned **as a familiar**, the slaughter of a [#](ad03fc)Chicken[#]() and the offering of dyes are intended to entice the [#](ad03fc)Foliot[#]() to take the shape of a parrot.\\\nAs [#](ad03fc)Foliot[#]() are not among the smartest spirits, they sometimes misunderstand the instructions...\n");

        this.page("description2", () -> BookTextPageModel.create()
                .withText(this.context().pageText()));
        this.pageText("*This means, if a [#](ad03fc)Chicken[#]() is spawned, that's not a bug, just bad luck!*");
    }

    @Override
    protected String entryName() {
        return "Parrot Familiar";
    }

    @Override
    protected String entryDescription() {
        return "Don't feed it cookies";
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
