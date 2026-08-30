package com.klikli_dev.occultism.datagen.book.familiar_rituals;

import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.EntryBackground;
import com.klikli_dev.modonomicon.api.datagen.EntryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookCraftingRecipePageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookTextPageModel;
import com.klikli_dev.modonomicon.client.gui.book.theme.GuiSprite;
import com.klikli_dev.occultism.registry.OccultismItems;

public class TabletConfigFamiliarEntry extends EntryProvider {

    public static final String ENTRY_ID = "tablet";


    public TabletConfigFamiliarEntry(CategoryProvider parent) {
        super(parent);
    }

    @Override
    protected void generatePages() {
        this.page("recipe", () -> BookCraftingRecipePageModel.create()
                .withRecipeId1(this.modLoc("crafting/familiar_tablet"))
                .withText(this.context().pageText()));
        this.pageText("""
                        When using this device, you can enable or disable familiar abilities and set the level of the applied effects.
                        \\
                        \\
                        On servers, you need to click the confirm button to save your changes.
                        """
        );

        this.page("description", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText()));
        this.pageTitle("Effects");
        this.pageText("""
                        Change the effect being configured by clicking or scrolling the mouse wheel.
                        \\
                        The bar's color indicates the familiar level required.
                         The higher of the selected and available levels will be applied.
                         - Gray -> Default
                         - Green -> Upgraded
                         - Blue -> Iesnium
                         - Red indicates that it is disabled.
                        """
        );
    }

    @Override
    protected String entryName() {
        return "Familiar Tablet";
    }

    @Override
    protected String entryDescription() {
        return "Choice your powers.";
    }

    @Override
    protected GuiSprite entryBackground() {
        return EntryBackground.DEFAULT;
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(OccultismItems.FAMILIAR_TABLET.get());
    }

    @Override
    protected String entryId() {
        return ENTRY_ID;
    }
}
