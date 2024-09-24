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

public class WhiteChalkEntry extends EntryProvider {

    public static final String ENTRY_ID = "white_chalk";


    public WhiteChalkEntry(CategoryProvider parent) {
        super(parent);
    }

    @Override
    protected void generatePages() {

        this.page("lore", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText())
        );
        this.pageTitle("First Foundation");
        this.pageText("""
                        This is the first chalk in the journey of any occultist. Due to its simplicity, it serves as a
                         weak foundation. Pentagrams drawn only with this chalk attract only the weakest spirits, 
                         known as Folit.
                        """
        );

        this.page("spotlight", () -> BookSpotlightPageModel.create()
                .withItem(Ingredient.of(OccultismItems.CHALK_WHITE.get()))
                .withText(this.context().pageText()));
        this.pageText("""
                        In almost any pentagram where you see a white glyph, it can be replaced with another
                         "colorless" chalk (light gray, gray, or black).
                        """
        );

        this.page("recipe_impure", () -> BookCraftingRecipePageModel.create()
                .withRecipeId1(this.modLoc("crafting/chalk_white_impure"))
        );

        this.page("recipe", () -> BookSpiritFireRecipePageModel.create()
                .withRecipeId1(this.modLoc("spirit_fire/chalk_white"))
        );

    }

    @Override
    protected String entryName() {
        return "The Most Basic Chalk";
    }

    @Override
    protected String entryDescription() {
        return "White Chalk";
    }

    @Override
    protected Pair<Integer, Integer> entryBackground() {
        return EntryBackground.DEFAULT;
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(OccultismItems.CHALK_WHITE.get());
    }

    @Override
    protected String entryId() {
        return ENTRY_ID;
    }
}
