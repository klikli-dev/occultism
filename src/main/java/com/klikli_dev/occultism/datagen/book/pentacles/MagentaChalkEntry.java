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

public class MagentaChalkEntry extends EntryProvider {

    public static final String ENTRY_ID = "magenta_chalk";


    public MagentaChalkEntry(CategoryProvider parent) {
        super(parent);
    }

    @Override
    protected void generatePages() {

        this.page("lore", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText())
        );
        this.pageTitle("Ender Energy");
        this.pageText("""
                        All the power of a dragon in a single chalk: this is the marvel of the purple chalk.
                         The relationship between spirits and dragons is unclear, but there may definitely be connections.
                        """
        );

        this.page("spotlight", () -> BookSpotlightPageModel.create()
                .withItem(Ingredient.of(OccultismItems.CHALK_MAGENTA.get()))
                .withText(this.context().pageText()));
        this.pageText("""
                        Ah, the energy of the end. But what end? Does an end truly exist?
                         What concludes at the end? Will the end ever come to an end?
                        """
        );

        this.page("recipe_impure", () -> BookCraftingRecipePageModel.create()
                .withRecipeId1(this.modLoc("crafting/chalk_magenta_impure"))
        );

        this.page("recipe", () -> BookSpiritFireRecipePageModel.create()
                .withRecipeId1(this.modLoc("spirit_fire/chalk_magenta"))
        );

    }

    @Override
    protected String entryName() {
        return "The Dragon Chalk";
    }

    @Override
    protected String entryDescription() {
        return "Magenta Chalk";
    }

    @Override
    protected Pair<Integer, Integer> entryBackground() {
        return EntryBackground.DEFAULT;
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(OccultismItems.CHALK_MAGENTA.get());
    }

    @Override
    protected String entryId() {
        return ENTRY_ID;
    }
}
