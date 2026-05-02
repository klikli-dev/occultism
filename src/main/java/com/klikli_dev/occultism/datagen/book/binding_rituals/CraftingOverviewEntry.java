package com.klikli_dev.occultism.datagen.book.binding_rituals;

import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.EntryBackground;
import com.klikli_dev.modonomicon.api.datagen.EntryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookTextPageModel;
import com.klikli_dev.modonomicon.client.gui.book.theme.GuiSprite;

public class CraftingOverviewEntry extends EntryProvider {

    public static final String ENTRY_ID = "overview";

    public CraftingOverviewEntry(CategoryProvider parent) {
        super(parent);
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(this.modLoc("textures/gui/book/infusion.png"));
    }

    @Override
    protected String entryName() {
        return "Binding Rituals";
    }

    @Override
    protected String entryDescription() {
        return "The Crafting";
    }

    @Override
    protected void generatePages() {
        this.page("intro", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText()));
        this.pageTitle("Binding Rituals");
        this.pageText("""
                Binding rituals infuse spirits into items, where their powers are used for one specific purpose.
                 The created items can act like simple empowering enchantments, or fulfill complex tasks to aid the summoner.
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
