package com.klikli_dev.occultism.datagen.book.pentacles;

import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.EntryBackground;
import com.klikli_dev.modonomicon.api.datagen.EntryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookCraftingRecipePageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookSpotlightPageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookTextPageModel;
import com.klikli_dev.occultism.integration.modonomicon.pages.BookRitualRecipePageModel;
import com.klikli_dev.occultism.integration.modonomicon.pages.BookSpiritFireRecipePageModel;
import com.klikli_dev.occultism.registry.OccultismItems;
import com.mojang.datafixers.util.Pair;
import net.minecraft.world.item.crafting.Ingredient;

public class LimeChalkEntry extends EntryProvider {

    public static final String ENTRY_ID = "lime_chalk";


    public LimeChalkEntry(CategoryProvider parent) {
        super(parent);
    }

    @Override
    protected void generatePages() {


        this.page("lore", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText())
        );
        this.pageTitle("Getting Experience");
        this.pageText("""
                        The lime chalk attracts greater spirits than Foliots.
                         Anyone wishing to elevate the level of their rituals will need this chalk.
                        """
        );

        this.page("spotlight", () -> BookSpotlightPageModel.create()
                .withItem(Ingredient.of(OccultismItems.CHALK_LIME.get()))
                .withText(this.context().pageText()));
        this.pageText("""
                        Made with valuable gems infused with experiences, lime glyphs become especially
                         interesting to demonstrate that yours skills have surpassed the basic level.
                        """
        );

        this.page("ritual", () -> BookRitualRecipePageModel.create()
                .withRecipeId1(this.modLoc("ritual/craft_research_fragment_dust"))
        );

        this.page("recipe_impure", () -> BookCraftingRecipePageModel.create()
                .withRecipeId1(this.modLoc("crafting/chalk_lime_impure"))
        );

        this.page("recipe", () -> BookSpiritFireRecipePageModel.create()
                .withRecipeId1(this.modLoc("spirit_fire/chalk_lime"))
        );
    }

    @Override
    protected String entryName() {
        return "The Knowledge Chalk";
    }

    @Override
    protected String entryDescription() {
        return "Lime Chalk";
    }

    @Override
    protected Pair<Integer, Integer> entryBackground() {
        return EntryBackground.DEFAULT;
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(OccultismItems.CHALK_LIME.get());
    }

    @Override
    protected String entryId() {
        return ENTRY_ID;
    }
}
