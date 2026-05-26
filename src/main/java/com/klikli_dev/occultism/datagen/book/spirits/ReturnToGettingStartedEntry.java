package com.klikli_dev.occultism.datagen.book.spirits;

import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.EntryBackground;
import com.klikli_dev.modonomicon.api.datagen.EntryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.client.gui.book.theme.GuiSprite;
import com.klikli_dev.occultism.registry.OccultismItems;

public class ReturnToGettingStartedEntry extends EntryProvider {
    public static final String ENTRY_ID = "return_to_getting_started";
    public ReturnToGettingStartedEntry(CategoryProvider parent){super(parent);} @Override protected void generatePages() {}
    @Override protected String entryName(){return "Return to getting started";}
    @Override protected String entryDescription(){return "";}
    @Override protected GuiSprite entryBackground(){return EntryBackground.CIRCLE_GRAY;}
    @Override protected BookIconModel entryIcon(){return BookIconModel.create(OccultismItems.DICTIONARY_OF_SPIRITS_ICON.get());}
    @Override protected String entryId(){return ENTRY_ID;}
}
