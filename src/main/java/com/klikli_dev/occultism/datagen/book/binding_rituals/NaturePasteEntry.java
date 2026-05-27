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

public class NaturePasteEntry extends EntryProvider {

    public static final String ENTRY_ID = "craft_nature_paste";


    public NaturePasteEntry(CategoryProvider parent) {
        super(parent);
    }

    @Override
    protected void generatePages() {
        this.page("spotlight", () -> BookSpotlightPageModel.create()
                .withItem(Ingredient.of(OccultismItems.NATURE_PASTE))
                .withText(this.context().pageText()));
        this.pageText("""
                        Nature Paste is an organic crafting paste created with the help of {0}. \\
                        Made from leaves, saplings, and seeds, it serves as a versatile \\
                        natural crafting material.
                        """,
                this.color("Foliot", ChatFormatting.DARK_PURPLE)
        );

        this.page("ritual", () -> BookRitualRecipePageModel.create()
                .withRecipeId1(this.modLoc("ritual/craft_nature_paste"))
        );
        //no text
    }

    @Override
    protected String entryName() {
        return "Nature Paste";
    }

    @Override
    protected String entryDescription() {
        return "Organic crafting material";
    }

    @Override
    protected GuiSprite entryBackground() {
        return EntryBackground.DEFAULT;
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(OccultismItems.NATURE_PASTE);
    }

    @Override
    protected String entryId() {
        return ENTRY_ID;
    }
}
