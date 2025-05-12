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

public class OrangeChalkEntry extends EntryProvider {

    public static final String ENTRY_ID = "orange_chalk";


    public OrangeChalkEntry(CategoryProvider parent) {
        super(parent);
    }

    @Override
    protected void generatePages() {


        this.page("lore", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText())
        );
        this.pageTitle("Afrit Attractive");
        this.pageText("""
                        The orange chalk is a perfect bait for spirits of the Afrit class, who,
                         although impressed by the lime chalk, can resist its call.
                        """
        );

        this.page("spotlight", () -> BookSpotlightPageModel.create()
                .withItem(Ingredient.of(OccultismItems.CHALK_ORANGE.get()))
                .withText(this.context().pageText()));
        this.pageText("""
                        Being a sweet and slightly warm chalk, it does not guarantee control over the invoked Afrit.
                         Commanding an Afrit requires power directly related to its class of spirits and
                         extreme stability in the pentacle.
                        """
        );

        this.page("honey", () -> BookSpotlightPageModel.create()
                .withItem(Ingredient.of(OccultismItems.CURSED_HONEY.get()))
                .withText(this.context().pageText()));
        this.pageText("""
                        To obtain the [](item://occultism:cursed_honey) for [](item://occultism:chalk_orange) you need to
                         [summon and kill a Possessed Bee](entry://possession_rituals/possess_bee)
                        """
        );

        this.page("recipe_impure", () -> BookCraftingRecipePageModel.create()
                .withRecipeId1(this.modLoc("crafting/chalk_orange_impure"))
        );

        this.page("recipe", () -> BookSpiritFireRecipePageModel.create()
                .withRecipeId1(this.modLoc("spirit_fire/chalk_orange"))
        );

    }

    @Override
    protected String entryName() {
        return "The Tangy Chalk";
    }

    @Override
    protected String entryDescription() {
        return "Orange Chalk";
    }

    @Override
    protected Pair<Integer, Integer> entryBackground() {
        return EntryBackground.DEFAULT;
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(OccultismItems.CHALK_ORANGE.get());
    }

    @Override
    protected String entryId() {
        return ENTRY_ID;
    }
}
