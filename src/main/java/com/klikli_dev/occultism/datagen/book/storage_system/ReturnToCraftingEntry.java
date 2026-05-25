package com.klikli_dev.occultism.datagen.book.storage_system;

import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.EntryBackground;
import com.klikli_dev.modonomicon.api.datagen.EntryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.client.gui.book.theme.GuiSprite;

public class ReturnToCraftingEntry extends EntryProvider {

    public static final String ENTRY_ID = "return_to_crafting";

    public ReturnToCraftingEntry(CategoryProvider parent) {
        super(parent);
    }

    @Override
    protected void generatePages() {
    }

    @Override
    protected String entryName() {
        return "Return to Binding Rituals Category";
    }

    @Override
    protected String entryDescription() {
        return "";
    }

    protected GuiSprite entryBackground() {
        return EntryBackground.CIRCLE_GRAY;
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(this.modLoc("textures/gui/book/infusion.png"));
    }

    @Override
    protected String entryId() {
        return ENTRY_ID;
    }
}
