package com.klikli_dev.occultism.datagen.book.pentacles;

import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.EntryBackground;
import com.klikli_dev.modonomicon.api.datagen.EntryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookCraftingRecipePageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookSpotlightPageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookTextPageModel;
import com.klikli_dev.occultism.integration.modonomicon.pages.BookSpiritFireRecipePageModel;
import com.klikli_dev.occultism.registry.OccultismItems;
import com.mojang.datafixers.util.Pair;
import net.minecraft.world.item.crafting.Ingredient;

public class YellowChalkEntry extends EntryProvider {

    public static final String ENTRY_ID = "yellow_chalk";


    public YellowChalkEntry(CategoryProvider parent) {
        super(parent);
    }

    @Override
    protected void generatePages() {

        this.page("lore", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText())
        );
        this.pageTitle("Possessing");
        this.pageText("""
                        The yellow chalk, also known as golden chalk, brings a sparkle to the eyes of those who
                         engage in possessions, serving as the main glyph in this type of ritual.
                        """
        );

        this.page("spotlight", () -> BookSpotlightPageModel.create()
                .withItem(Ingredient.of(OccultismItems.CHALK_YELLOW.get()))
                .withText(this.context().pageText()));
        this.pageText("""
                        Possessions are a variation of summoning, caused by a transformation in geometry and
                         the addition of yellow glyphs, allowing spirits to manifest in bodies (material prisons)
                          different from their usual ones.
                        """
        );

        this.page("recipe_impure", () -> BookCraftingRecipePageModel.create()
                .withRecipeId1(this.modLoc("crafting/chalk_yellow_impure"))
        );

        this.page("recipe", () -> BookSpiritFireRecipePageModel.create()
                .withRecipeId1(this.modLoc("spirit_fire/chalk_yellow"))
        );
    }

    @Override
    protected String entryName() {
        return "The Chalk of Possession";
    }

    @Override
    protected String entryDescription() {
        return "Yellow Chalk";
    }

    @Override
    protected Pair<Integer, Integer> entryBackground() {
        return EntryBackground.DEFAULT;
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(OccultismItems.CHALK_YELLOW.get());
    }

    @Override
    protected String entryId() {
        return ENTRY_ID;
    }
}
