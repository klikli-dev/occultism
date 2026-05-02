package com.klikli_dev.occultism.datagen.book.summoning_rituals;

import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.EntryBackground;
import com.klikli_dev.modonomicon.api.datagen.EntryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookTextPageModel;
import com.klikli_dev.modonomicon.client.gui.book.theme.GuiSprite;

public class SummoningOverviewEntry extends EntryProvider {

    public static final String ENTRY_ID = "overview";

    public SummoningOverviewEntry(CategoryProvider parent) {
        super(parent);
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(this.modLoc("textures/gui/book/summoning.png"));
    }

    @Override
    protected String entryName() {
        return "Summoning Rituals";
    }

    @Override
    protected String entryDescription() {
        return "Workers of the world, unite!";
    }

    @Override
    protected void generatePages() {
        this.page("intro", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText()));
        this.pageTitle("Summoning Rituals");
        this.pageText("""
                Summon rituals force spirits to enter this world in their chosen shape, leading
                 to little restrictions on their power. Summoned spirits range from trade spirits
                 that trade and convert items, to slave-like helpers for manual labour.
                """
        );
    }

    @Override
    protected GuiSprite entryBackground() {
        return EntryBackground.CATEGORY_START;
    }

    @Override
    protected String entryId() {
        return ENTRY_ID;
    }
}
