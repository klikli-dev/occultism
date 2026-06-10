package com.klikli_dev.occultism.datagen.book.getting_started;

import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.EntryBackground;
import com.klikli_dev.modonomicon.api.datagen.EntryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.client.gui.book.theme.GuiSprite;

public class SpiritsEntry extends EntryProvider {

    public static final String ENTRY_ID = "spirits";

    public SpiritsEntry(CategoryProvider parent) {
        super(parent);
    }

    @Override
    protected void generatePages() {
    }

    @Override
    protected String entryName() {
        return "About Spirits";
    }

    @Override
    protected String entryDescription() {
        return "Learn more about Spirits.";
    }

    @Override
    protected GuiSprite entryBackground() {
        return EntryBackground.STAR_GRAY;
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(this.modLoc("textures/gui/book/spirits.png"));
    }

    @Override
    protected String entryId() {
        return ENTRY_ID;
    }
}
