package com.klikli_dev.occultism.datagen.book.familiar_rituals;

import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.EntryBackground;
import com.klikli_dev.modonomicon.api.datagen.EntryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookEntityPageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookTextPageModel;
import com.klikli_dev.modonomicon.client.gui.book.theme.GuiSprite;
import com.klikli_dev.occultism.integration.modonomicon.pages.BookRitualRecipePageModel;

public class FamiliarDeerEntry extends EntryProvider {

    public static final String ENTRY_ID = "familiar_deer";

    public FamiliarDeerEntry(CategoryProvider parent) {
        super(parent);
    }

    @Override
    protected void generatePages() {
        this.page("entity", () -> BookEntityPageModel.create()
                .withEntityId("occultism:deer_familiar")
                .withText(this.context().pageText())
                .withScale(0.7f)
                .withOffset(0.3f));
        this.pageText("**Provides**: [#](ad03fc)Speed and Jump Boost, Step assist[#]()\n");

        this.page("ritual", () -> BookRitualRecipePageModel.create()
                .withRecipeId1(this.modLoc("ritual/familiar_deer")));

        this.page("description", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText()));
        this.pageTitle("Description");
        this.pageText("**Upgrade Behaviour**\\\nWhen upgraded by a blacksmith familiar, will increase the step assist and it will attack nearby enemies with a hammer. Yep, a **hammer**.\n");
    }

    @Override
    protected String entryName() {
        return "Deer Familiar";
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
        return BookIconModel.create(this.modLoc("textures/gui/book/familiar_deer.png"));
    }

    @Override
    protected String entryId() {
        return ENTRY_ID;
    }
}
