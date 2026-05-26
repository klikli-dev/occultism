package com.klikli_dev.occultism.datagen.book.rituals;

import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.EntryBackground;
import com.klikli_dev.modonomicon.api.datagen.EntryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.client.gui.book.theme.GuiSprite;

public class CraftingRitualsSubcategoryEntry extends EntryProvider {
    public static final String ENTRY_ID = "crafting_rituals";
    public CraftingRitualsSubcategoryEntry(CategoryProvider parent) { super(parent); }
    @Override protected BookIconModel entryIcon() { return BookIconModel.create(this.modLoc("textures/gui/book/infusion.png")); }
    @Override protected String entryName() { return "Crafting Rituals"; }
    @Override protected String entryDescription() { return ""; }
    @Override protected void generatePages() {}
    @Override protected GuiSprite entryBackground() { return EntryBackground.STAR_GRAY; }
    @Override protected String entryId() { return ENTRY_ID; }
}
