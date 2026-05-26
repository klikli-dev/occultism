package com.klikli_dev.occultism.datagen.book.possession_rituals;

import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.EntryBackground;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.client.gui.book.theme.GuiSprite;

public class ReturnToRitualsEntry extends AbstractPossessionEntry {

    public static final String ENTRY_ID = "return_to_rituals";

    public ReturnToRitualsEntry(CategoryProvider parent) {
        super(parent);
    }

    @Override
    protected void generatePages() {
    }

    @Override
    protected String entryName() {
        return "Return to Rituals Category";
    }

    @Override
    protected GuiSprite entryBackground() {
        return EntryBackground.CIRCLE_GRAY;
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(this.modLoc("textures/gui/book/robe.png"));
    }

    @Override
    protected String entryId() {
        return ENTRY_ID;
    }
}
