package com.klikli_dev.occultism.datagen.book.familiar_rituals;

import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.EntryBackground;
import com.klikli_dev.modonomicon.api.datagen.EntryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookEntityPageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookTextPageModel;
import com.klikli_dev.modonomicon.client.gui.book.theme.GuiSprite;
import com.klikli_dev.occultism.integration.modonomicon.pages.BookRitualRecipePageModel;

public class FamiliarDevilEntry extends EntryProvider {

    public static final String ENTRY_ID = "familiar_devil";

    public FamiliarDevilEntry(CategoryProvider parent) {
        super(parent);
    }

    @Override
    protected void generatePages() {
        this.page("entity", () -> BookEntityPageModel.create()
                .withEntityId("occultism:devil_familiar")
                .withText(this.context().pageText())
                .withScale(0.5f)
                .withOffset(0.3f));
        this.pageText("**Provides**: [#](ad03fc)Fire Resistance[#](), [#](ad03fc)Attacks Enemies[#]()\n");

        this.page("ritual", () -> BookRitualRecipePageModel.create()
                .withRecipeId1(this.modLoc("ritual/familiar_devil")));

        this.page("description", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText()));
        this.pageTitle("Description");
        this.pageText("**Upgrade Behaviour**\\\nEnchants a Golden Apple or a Golden Dragon's Dream Fruit when right-click, but has large time interval.\n");
    }

    @Override
    protected String entryName() {
        return "Devil Familiar";
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
        return BookIconModel.create(this.modLoc("textures/gui/book/familiar_devil.png"));
    }

    @Override
    protected String entryId() {
        return ENTRY_ID;
    }
}
