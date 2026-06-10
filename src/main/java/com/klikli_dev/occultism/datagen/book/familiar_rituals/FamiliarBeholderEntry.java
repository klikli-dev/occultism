package com.klikli_dev.occultism.datagen.book.familiar_rituals;

import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.EntryBackground;
import com.klikli_dev.modonomicon.api.datagen.EntryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookEntityPageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookTextPageModel;
import com.klikli_dev.modonomicon.client.gui.book.theme.GuiSprite;
import com.klikli_dev.occultism.integration.modonomicon.pages.BookRitualRecipePageModel;

public class FamiliarBeholderEntry extends EntryProvider {

    public static final String ENTRY_ID = "familiar_beholder";

    public FamiliarBeholderEntry(CategoryProvider parent) {
        super(parent);
    }

    @Override
    protected void generatePages() {
        this.page("entity", () -> BookEntityPageModel.create()
                .withEntityId("occultism:beholder_familiar")
                .withText(this.context().pageText())
                .withScale(0.7f)
                .withOffset(0.3f));
        this.pageText("**Provides**: [#](ad03fc)Highlights enemies[#](), [#](ad03fc)Shoots **FREAKING LAZORS**[#]()\n");

        this.page("ritual", () -> BookRitualRecipePageModel.create()
                .withRecipeId1(this.modLoc("ritual/familiar_beholder")));

        this.page("description", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText()));
        this.pageTitle("Description");
        this.pageText("The Beholder familiar highlights nearby entities with a glow effect, and shoots laser rays at enemies. It **eats** (poor) **Shub Niggurath babies** to gain temporary damage and speed.\n\\\n\\\n**Upgrade Behaviour**\\\nWhen upgraded by a blacksmith familiar, it give it's master immunity to blindness, and after highlighting a Warden, the immunity extends to darkness.\n");
    }

    @Override
    protected String entryName() {
        return "Beholder Familiar";
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
        return BookIconModel.create(this.modLoc("textures/gui/book/familiar_beholder.png"));
    }

    @Override
    protected String entryId() {
        return ENTRY_ID;
    }
}
