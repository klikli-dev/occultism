package com.klikli_dev.occultism.datagen.book.getting_started;

import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.EntryBackground;
import com.klikli_dev.modonomicon.api.datagen.EntryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.client.gui.book.theme.GuiSprite;
import com.klikli_dev.occultism.registry.OccultismBlocks;

public class StorageEntry extends EntryProvider {

    public static final String ENTRY_ID = "storage";

    public StorageEntry(CategoryProvider parent) {
        super(parent);
    }

    @Override
    protected void generatePages() {
    }

    @Override
    protected String entryName() {
        return "Magic Storage";
    }

    @Override
    protected String entryDescription() {
        return "Looking for much much much more storage? Look no further!";
    }

    @Override
    protected GuiSprite entryBackground() {
        return EntryBackground.STAR_GRAY;
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(OccultismBlocks.STORAGE_CONTROLLER.get());
    }

    @Override
    protected String entryId() {
        return ENTRY_ID;
    }
}
