package com.klikli_dev.occultism.datagen.book.getting_started;

import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.EntryBackground;
import com.klikli_dev.modonomicon.api.datagen.EntryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookTextPageModel;
import com.klikli_dev.modonomicon.client.gui.book.theme.GuiSprite;

public class SummoningRitualsEntry extends EntryProvider {

    public static final String ENTRY_ID = "summoning_rituals";

    public SummoningRitualsEntry(CategoryProvider parent) {
        super(parent);
    }

    @Override
    protected void generatePages() {
        this.page("intro", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText()));
        this.pageTitle("Summoning Rituals");
        this.pageText("Summoning Rituals allow you to summon spirits to work for you. Unlike familiars, they are not personally bound to you, meaning they will not follow you around, but they will perform various work tasks for you. In fact the first ritual you performed, the [Foliot Crusher](entry://occultism:dictionary_of_spirits/getting_started/first_ritual), was a summoning ritual.\n");

        this.page("more", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText()));
        this.pageTitle("More Information");
        this.pageText("To find more about Summoning Rituals, see the [Summoning Rituals](category://occultism:dictionary_of_spirits/summoning_rituals) Category.\n");
    }

    @Override
    protected String entryName() {
        return "Summoning Rituals";
    }

    @Override
    protected String entryDescription() {
        return "Spirit helpers for your daily work life";
    }

    @Override
    protected GuiSprite entryBackground() {
        return EntryBackground.STAR_GRAY;
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(this.modLoc("textures/gui/book/summoning.png"));
    }

    @Override
    protected String entryId() {
        return ENTRY_ID;
    }
}
