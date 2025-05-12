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

public class PinkChalkEntry extends EntryProvider {

    public static final String ENTRY_ID = "pink_chalk";


    public PinkChalkEntry(CategoryProvider parent) {
        super(parent);
    }

    @Override
    protected void generatePages() {

        this.page("lore", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText())
        );
        this.pageTitle("It's Alive");
        this.pageText("""
                        Made from the flesh of a pig possessed by an Afrit, this chalk possesses
                         both animalistic properties and part of an Afrit's power.
                        """
        );

        this.page("spotlight", () -> BookSpotlightPageModel.create()
                .withItem(Ingredient.of(OccultismItems.CHALK_PINK.get()))
                .withText(this.context().pageText()));
        this.pageText("""
                        Some occultists have reported that the demonic flesh keeps the chalk alive,
                         resulting in strange movements. However, this has never been officially
                         documented or proven, remaining merely rumors that may or may not be true.
                        """
        );

        this.page("meat", () -> BookSpotlightPageModel.create()
                .withItem(Ingredient.of(OccultismItems.DEMONIC_MEAT.get()))
                .withText(this.context().pageText()));
        this.pageText("""
                        To obtain the [](item://occultism:demonic_meat) for [](item://occultism:chalk_pink) you need to
                         [summon and kill a Possessed Zombified Piglin](entry://possession_rituals/possess_zombie_piglin)
                        """
        );

        this.page("recipe_impure", () -> BookCraftingRecipePageModel.create()
                .withRecipeId1(this.modLoc("crafting/chalk_pink_impure"))
        );

        this.page("recipe", () -> BookSpiritFireRecipePageModel.create()
                .withRecipeId1(this.modLoc("spirit_fire/chalk_pink"))
        );

    }

    @Override
    protected String entryName() {
        return "The Meat Chalk";
    }

    @Override
    protected String entryDescription() {
        return "Pink Chalk";
    }

    @Override
    protected Pair<Integer, Integer> entryBackground() {
        return EntryBackground.DEFAULT;
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(OccultismItems.CHALK_PINK.get());
    }

    @Override
    protected String entryId() {
        return ENTRY_ID;
    }
}
