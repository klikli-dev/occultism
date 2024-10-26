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

public class LightBlueChalkEntry extends EntryProvider {

    public static final String ENTRY_ID = "light_blue_chalk";


    public LightBlueChalkEntry(CategoryProvider parent) {
        super(parent);
    }

    @Override
    protected void generatePages() {

        this.page("lore", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText())
        );
        this.pageTitle("Never Melts");
        this.pageText("""
                        The light blue chalk is made with such icy materials that its glyphs
                         are inert and impart a natural stability to the pentacles.
                        """
        );

        this.page("spotlight", () -> BookSpotlightPageModel.create()
                .withItem(Ingredient.of(OccultismItems.CHALK_LIGHT_BLUE.get()))
                .withText(this.context().pageText()));
        this.pageText("""
                        Although it is basically made of ice, the magic present prevents it from melting.
                        """
        );

        this.page("recipe_impure", () -> BookCraftingRecipePageModel.create()
                .withRecipeId1(this.modLoc("crafting/chalk_light_blue_impure"))
                .withText(this.context().pageText()));
        this.pageText("""
                Crushing ice without melting it is a job a Foliot can't do, so you'll need a better crusher.
                """);

        this.page("recipe", () -> BookSpiritFireRecipePageModel.create()
                .withRecipeId1(this.modLoc("spirit_fire/chalk_light_blue"))
        );

    }

    @Override
    protected String entryName() {
        return "The Glacial Chalk";
    }

    @Override
    protected String entryDescription() {
        return "Light Blue Chalk";
    }

    @Override
    protected Pair<Integer, Integer> entryBackground() {
        return EntryBackground.DEFAULT;
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(OccultismItems.CHALK_LIGHT_BLUE.get());
    }

    @Override
    protected String entryId() {
        return ENTRY_ID;
    }
}
