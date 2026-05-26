package com.klikli_dev.occultism.datagen.book.possession_rituals;

import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.EntryBackground;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.client.gui.book.theme.GuiSprite;

public class PossessionOverviewEntry extends AbstractPossessionEntry {

    public static final String ENTRY_ID = "overview";

    public PossessionOverviewEntry(CategoryProvider parent) {
        super(parent);
    }

    @Override
    protected void generatePages() {
        this.textPage("intro");
        this.pageTitle("Possession Rituals");
        this.pageText("""
                Possession rituals bind spirits into living beings, giving the summoner a degree of control over the possessed being.
                \\
                \\
                As such these rituals are used to obtain rare items without having to venture into dangerous places.
                \\
                \\
                Possessed Mobs count as their vanilla counterparts for the ritual sacrifices purposes.
                """);
    }

    @Override
    protected String entryName() {
        return "Possession Rituals";
    }

    @Override
    protected GuiSprite entryBackground() {
        return EntryBackground.STAR_GOLD;
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
