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

public class BrownChalkEntry extends EntryProvider {

    public static final String ENTRY_ID = "brown_chalk";


    public BrownChalkEntry(CategoryProvider parent) {
        super(parent);
    }

    @Override
    protected void generatePages() {

        this.page("lore", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText())
        );
        this.pageTitle("Very Evil");
        this.pageText("""
                        The brown chalk is made with the essence of cruelty, and obtaining it certainly lives up
                         to its name. Do the ends justify the means? Does morality truly exist? What is your morality?
                        """
        );

        this.page("spotlight", () -> BookSpotlightPageModel.create()
                .withItem(Ingredient.of(OccultismItems.CHALK_BROWN.get()))
                .withText(this.context().pageText()));
        this.pageText("""
                        This chalk is known to be part of an "Alignment Test." Anyone who possesses it is
                         automatically classified as "Evil." What kind of spirits will these glyphs attract?
                        """
        );

        this.page("essence", () -> BookSpotlightPageModel.create()
                .withItem(Ingredient.of(OccultismItems.CRUELTY_ESSENCE.get()))
                .withText(this.context().pageText()));
        this.pageText("""
                        To obtain the [](item://occultism:cruelty_essence) for [](item://occultism:chalk_brown) you need to
                         [summon and kill a Mercy Goat](entry://possession_rituals/possess_goat)
                        """
        );

        this.page("recipe_impure", () -> BookCraftingRecipePageModel.create()
                .withRecipeId1(this.modLoc("crafting/chalk_brown_impure"))
        );

        this.page("recipe", () -> BookSpiritFireRecipePageModel.create()
                .withRecipeId1(this.modLoc("spirit_fire/chalk_brown"))
        );

    }

    @Override
    protected String entryName() {
        return "The Cruelty Chalk";
    }

    @Override
    protected String entryDescription() {
        return "Brown Chalk";
    }

    @Override
    protected Pair<Integer, Integer> entryBackground() {
        return EntryBackground.DEFAULT;
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(OccultismItems.CHALK_BROWN.get());
    }

    @Override
    protected String entryId() {
        return ENTRY_ID;
    }
}
