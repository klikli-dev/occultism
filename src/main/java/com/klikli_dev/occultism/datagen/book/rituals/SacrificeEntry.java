package com.klikli_dev.occultism.datagen.book.rituals;

import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.EntryBackground;
import com.klikli_dev.modonomicon.api.datagen.EntryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookTextPageModel;
import com.klikli_dev.modonomicon.client.gui.book.theme.GuiSprite;
import net.minecraft.world.item.Items;

public class SacrificeEntry extends EntryProvider {
    public static final String ENTRY_ID = "sacrifice";
    public SacrificeEntry(CategoryProvider parent) { super(parent); }
    @Override protected BookIconModel entryIcon() { return BookIconModel.create(Items.IRON_SWORD); }
    @Override protected String entryName() { return "Sacrifices"; }
    @Override protected String entryDescription() { return ""; }
    @Override protected void generatePages() {
        this.page("intro", () -> BookTextPageModel.create().withTitle(this.context().pageTitle()).withText(this.context().pageText()));
        this.pageTitle("Sacrifices");
        this.pageText("Some rituals require the sacrifice of a living being to provide the necessary energy to summon the spirit. Sacrifices are described on the ritual's page under the \"Sacrifice\" subheading. To perform a sacrifice, kill an animal within 8 blocks of the golden ritual bowl. Only kills by players count as sacrifice!");
    }
    @Override protected GuiSprite entryBackground() { return EntryBackground.DEFAULT; }
    @Override protected String entryId() { return ENTRY_ID; }
}
