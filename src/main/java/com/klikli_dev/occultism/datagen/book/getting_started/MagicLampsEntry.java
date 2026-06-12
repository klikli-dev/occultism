package com.klikli_dev.occultism.datagen.book.getting_started;

import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.EntryBackground;
import com.klikli_dev.modonomicon.api.datagen.EntryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookCraftingRecipePageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookSpotlightPageModel;
import com.klikli_dev.modonomicon.client.gui.book.theme.GuiSprite;
import com.klikli_dev.occultism.registry.OccultismItems;
import net.minecraft.world.item.crafting.Ingredient;

public class MagicLampsEntry extends EntryProvider {

    public static final String ENTRY_ID = "magic_lamps";

    public MagicLampsEntry(CategoryProvider parent) {
        super(parent);
    }

    @Override
    protected void generatePages() {
        this.page("spotlight", () -> BookSpotlightPageModel.create()
                .withItem(Ingredient.of(OccultismItems.MAGIC_LAMP_EMPTY.get()))
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText()));
        this.pageTitle("Magic Lamps");
        this.pageText("Magic Lamps can be used to keep spirits safe from [#](ad03fc)Essence Decay[#]() (if the spirit has decay), while still having access to some of their powers. Right-Click on one of your workers to store and transport it as desired.\n");

        this.page("crafting", () -> BookCraftingRecipePageModel.create()
                .withRecipeId1(this.modLoc("crafting/magic_lamp_empty")));
    }

    @Override
    protected String entryName() {
        return "Magic Lamps";
    }

    @Override
    protected String entryDescription() {
        return "Three wishes? Close, but not quite..";
    }

    @Override
    protected GuiSprite entryBackground() {
        return EntryBackground.DEFAULT;
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(OccultismItems.MAGIC_LAMP_EMPTY.get());
    }

    @Override
    protected String entryId() {
        return ENTRY_ID;
    }
}
