package com.klikli_dev.occultism.datagen.book.rituals;

import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.EntryBackground;
import com.klikli_dev.modonomicon.api.datagen.EntryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookTextPageModel;
import com.klikli_dev.modonomicon.client.gui.book.theme.GuiSprite;
import net.minecraft.world.item.Items;

public class ItemUseEntry extends EntryProvider {
    public static final String ENTRY_ID = "item_use";
    public ItemUseEntry(CategoryProvider parent) { super(parent); }
    @Override protected BookIconModel entryIcon() { return BookIconModel.create(Items.FLINT_AND_STEEL); }
    @Override protected String entryName() { return "Item Use"; }
    @Override protected String entryDescription() { return ""; }
    @Override protected void generatePages() {
        this.page("intro", () -> BookTextPageModel.create().withTitle(this.context().pageTitle()).withText(this.context().pageText()));
        this.pageTitle("Item Use");
        this.pageText("Some rituals require the use of certain items to be performed. Use the item described on the ritual's page within **16 blocks** of the [](item://occultism:golden_sacrificial_bowl) to proceed with the ritual.\n\\\n\\\n**Important:** Before using the item, start the ritual. Grey particles indicate that the ritual is ready for the item use.");
    }
    @Override protected GuiSprite entryBackground() { return EntryBackground.DEFAULT; }
    @Override protected String entryId() { return ENTRY_ID; }
}
