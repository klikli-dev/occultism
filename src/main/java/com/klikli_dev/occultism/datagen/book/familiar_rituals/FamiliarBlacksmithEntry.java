package com.klikli_dev.occultism.datagen.book.familiar_rituals;

import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.EntryBackground;
import com.klikli_dev.modonomicon.api.datagen.EntryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookEntityPageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookTextPageModel;
import com.klikli_dev.modonomicon.client.gui.book.theme.GuiSprite;
import com.klikli_dev.occultism.integration.modonomicon.pages.BookRitualRecipePageModel;

public class FamiliarBlacksmithEntry extends EntryProvider {

    public static final String ENTRY_ID = "familiar_blacksmith";

    public FamiliarBlacksmithEntry(CategoryProvider parent) {
        super(parent);
    }

    @Override
    protected void generatePages() {
        this.page("entity", () -> BookEntityPageModel.create()
                .withEntityId("occultism:blacksmith_familiar")
                .withText(this.context().pageText()));
        this.pageText("**Provides**: [#](ad03fc)Repairs Equipment while Mining[#](), [#](ad03fc)Upgrades other familiars[#]()\n");

        this.page("ritual", () -> BookRitualRecipePageModel.create()
                .withRecipeId1(this.modLoc("ritual/familiar_blacksmith")));

        this.page("description", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText()));
        this.pageTitle("Description");
        this.pageText("Whenever the player picks up stone, there is a chance for the blacksmith familiar to repair their equipment a little bit.\n\\\n\\\n**Upgrade Behaviour**: \\\nCannot be upgraded, but upgrades other Familiars.\n");

        this.page("description2", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText()));
        this.pageTitle("Upgrading Familiars");
        this.pageText("To upgrade other familiars the blacksmith needs to be given iron ingots or blocks by [#](ad03fc)right-clicking[#]() it.\n\\\nWhen the blacksmith upgrades a familiar, a message appears in the action bar, an anvil sound is emitted, and a star appears at the end of the familiar's name.\n\\\nUpgraded familiars provide additional effects.\n");
    }

    @Override
    protected String entryName() {
        return "Blacksmith Familiar";
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
        return BookIconModel.create(this.modLoc("textures/gui/book/familiar_blacksmith.png"));
    }

    @Override
    protected String entryId() {
        return ENTRY_ID;
    }
}
