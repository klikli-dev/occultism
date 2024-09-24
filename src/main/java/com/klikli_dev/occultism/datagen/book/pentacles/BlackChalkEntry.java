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

public class BlackChalkEntry extends EntryProvider {

    public static final String ENTRY_ID = "black_chalk";


    public BlackChalkEntry(CategoryProvider parent) {
        super(parent);
    }

    @Override
    protected void generatePages() {

        this.page("lore", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText())
        );
        this.pageTitle("Powerful");
        this.pageText("""
                        The black chalk has a composition as rigid as it is supernatural. Mixing the essence of
                         Wither with netherite turns this chalk into an extremely valuable tool.
                        """
        );

        this.page("spotlight", () -> BookSpotlightPageModel.create()
                .withItem(Ingredient.of(OccultismItems.CHALK_BLACK.get()))
                .withText(this.context().pageText()));
        this.pageText("""
                        Perfect for the foundation of any pentagram, the power of the black chalk is sufficient
                         to replace any other "colorless" chalk.
                        """
        );

        this.page("recipe_impure", () -> BookCraftingRecipePageModel.create()
                .withRecipeId1(this.modLoc("crafting/chalk_black_impure"))
        );

        this.page("recipe", () -> BookSpiritFireRecipePageModel.create()
                .withRecipeId1(this.modLoc("spirit_fire/chalk_black"))
        );

    }

    @Override
    protected String entryName() {
        return "Perfect Foundation Chalk";
    }

    @Override
    protected String entryDescription() {
        return "Black Chalk";
    }

    @Override
    protected Pair<Integer, Integer> entryBackground() {
        return EntryBackground.DEFAULT;
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(OccultismItems.CHALK_BLACK.get());
    }

    @Override
    protected String entryId() {
        return ENTRY_ID;
    }
}
