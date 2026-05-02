package com.klikli_dev.occultism.datagen.book.binding_rituals;

import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.EntryBackground;
import com.klikli_dev.modonomicon.api.datagen.EntryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookSpotlightPageModel;
import com.klikli_dev.modonomicon.client.gui.book.theme.GuiSprite;
import com.klikli_dev.occultism.integration.modonomicon.pages.BookRitualRecipePageModel;
import com.klikli_dev.occultism.registry.OccultismItems;
import net.minecraft.ChatFormatting;
import net.minecraft.world.item.crafting.Ingredient;

public class DimensionalMatrixEntry extends EntryProvider {

    public static final String ENTRY_ID = "craft_dimensional_matrix";

    public DimensionalMatrixEntry(CategoryProvider parent) {
        super(parent);
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(OccultismItems.DIMENSIONAL_MATRIX.get());
    }

    @Override
    protected String entryName() {
        return "Dimensional Matrix";
    }

    @Override
    protected String entryDescription() {
        return "Storage Brain";
    }

    @Override
    protected void generatePages() {
        this.page("spotlight", () -> BookSpotlightPageModel.create()
                .withItem(Ingredient.of(OccultismItems.DIMENSIONAL_MATRIX.get()))
                .withText(this.context().pageText()));
        this.pageText("""
                        The dimensional matrix is the entry point to a small dimension used for storing items.
                         A {0} bound to the matrix keeps the dimension stable, often supported by additional
                         spirits in storage stabilizers, to increase the dimension size.
                        """,
                this.color("Djinni", ChatFormatting.DARK_PURPLE)
        );

        this.page("ritual", () -> BookRitualRecipePageModel.create()
                .withRecipeId1(this.modLoc("ritual/craft_dimensional_matrix"))
        );
        //no text
    }

    @Override
    protected GuiSprite entryBackground() {
        return EntryBackground.DEFAULT;
    }

    @Override
    protected String entryId() {
        return ENTRY_ID;
    }
}
