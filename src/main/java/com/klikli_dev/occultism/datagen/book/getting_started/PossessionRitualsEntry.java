package com.klikli_dev.occultism.datagen.book.getting_started;

import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.EntryBackground;
import com.klikli_dev.modonomicon.api.datagen.EntryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookTextPageModel;
import com.klikli_dev.modonomicon.client.gui.book.theme.GuiSprite;

public class PossessionRitualsEntry extends EntryProvider {

    public static final String ENTRY_ID = "possession_rituals";

    public PossessionRitualsEntry(CategoryProvider parent) {
        super(parent);
    }

    @Override
    protected void generatePages() {
        this.page("intro", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText()));
        this.pageTitle("Possession Rituals");
        this.pageText("Possessed mobs are controlled by spirits, allowing the summoner to determine some of their properties. They usually have **high drop rates** for rare drops, but are generally harder to kill.\n\\\n\\\nYou probably will want to start by summoning a [Possessed Endermite](entry://occultism:dictionary_of_spirits/possession_rituals/possess_endermite) to get [](item://minecraft:end_stone) to craft [Advanced Chalks](entry://occultism:dictionary_of_spirits/getting_started/chalks).\n");

        this.page("more", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText()));
        this.pageTitle("More Information");
        this.pageText("To find out more about Possession Rituals, see the [Possession Rituals](category://occultism:dictionary_of_spirits/possession_rituals) Category.\n");
    }

    @Override
    protected String entryName() {
        return "Possession Rituals";
    }

    @Override
    protected String entryDescription() {
        return "A different way to get rare drops ...";
    }

    @Override
    protected GuiSprite entryBackground() {
        return EntryBackground.STAR_GRAY;
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(this.modLoc("textures/gui/book/possession.png"));
    }

    @Override
    protected String entryId() {
        return ENTRY_ID;
    }
}
