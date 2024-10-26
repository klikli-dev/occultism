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
import net.minecraft.ChatFormatting;
import net.minecraft.world.item.crafting.Ingredient;

public class CyanChalkEntry extends EntryProvider {

    public static final String ENTRY_ID = "cyan_chalk";


    public CyanChalkEntry(CategoryProvider parent) {
        super(parent);
    }

    @Override
    protected void generatePages() {

        this.page("lore", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText())
        );
        this.pageTitle("In the Past");
        this.pageText("""
                        Almost lost to time, the cyan chalk brings ancient knowledge, often even forbidden.
                         But who cares? After all, knowledge is knowledge, and the more, the better, right?
                        """
        );

        this.page("spotlight", () -> BookSpotlightPageModel.create()
                .withItem(Ingredient.of(OccultismItems.CHALK_CYAN.get()))
                .withText(this.context().pageText()));
        this.pageText("""
                        Despite the complexity of its manufacture, this chalk is dated as one of the oldest,
                         perhaps even the oldest chalk ever recorded. And even after all this time,
                         its uses are little known, but you can see just how stable it is.
                        """
        );

        this.page("recipe_impure", () -> BookCraftingRecipePageModel.create()
                .withRecipeId1(this.modLoc("crafting/chalk_cyan_impure"))
                .withText(this.context().pageText()));
        this.pageText("""
                "Pulverizing an {0} while retaining its properties is a job that only the best crushers can do.
                """,
                this.color("Echo Shard", ChatFormatting.LIGHT_PURPLE));

        this.page("recipe", () -> BookSpiritFireRecipePageModel.create()
                .withRecipeId1(this.modLoc("spirit_fire/chalk_cyan"))
        );

    }

    @Override
    protected String entryName() {
        return "The Chalk From Ancients";
    }

    @Override
    protected String entryDescription() {
        return "Cyan Chalk";
    }

    @Override
    protected Pair<Integer, Integer> entryBackground() {
        return EntryBackground.DEFAULT;
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(OccultismItems.CHALK_CYAN.get());
    }

    @Override
    protected String entryId() {
        return ENTRY_ID;
    }
}
