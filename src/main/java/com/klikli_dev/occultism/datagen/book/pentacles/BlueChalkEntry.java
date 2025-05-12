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

public class BlueChalkEntry extends EntryProvider {

    public static final String ENTRY_ID = "blue_chalk";


    public BlueChalkEntry(CategoryProvider parent) {
        super(parent);
    }

    @Override
    protected void generatePages() {

        this.page("lore", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText())
        );
        this.pageTitle("Marid Lure");
        this.pageText("""
                        Just as the red chalk is made from the essence of Afrit, the blue chalk is made
                         from the essence of Marid, allowing for control over these powerful spirits.
                        """
        );

        this.page("spotlight", () -> BookSpotlightPageModel.create()
                .withItem(Ingredient.of(OccultismItems.CHALK_BLUE.get()))
                .withText(this.context().pageText()));
        this.pageText("""
                        The purpose of the blue chalk is to overcome the willpower of a Marid; its sometimes
                         excessive use serves as a guarantee that any Marid will be controlled.
                         Should the control fail, it would generate extreme fury in the invoked Marid.
                        """
        );

        this.page("essence", () -> BookSpotlightPageModel.create()
                .withItem(Ingredient.of(OccultismItems.MARID_ESSENCE.get()))
                .withText(this.context().pageText()));
        this.pageText("""
                        To obtain the essence of a {0} for [](item://occultism:chalk_blue) you need to
                         [summon and kill an Unbound Marid](entry://summoning_rituals/marid_essence).
                        """,
                this.color("Marid", ChatFormatting.DARK_PURPLE)
        );

        this.page("recipe_impure", () -> BookCraftingRecipePageModel.create()
                .withRecipeId1(this.modLoc("crafting/chalk_blue_impure"))
        );

        this.page("recipe", () -> BookSpiritFireRecipePageModel.create()
                .withRecipeId1(this.modLoc("spirit_fire/chalk_blue"))
        );

    }

    @Override
    protected String entryName() {
        return "The Chalk of The Seven Seas";
    }

    @Override
    protected String entryDescription() {
        return "Blue Chalk";
    }

    @Override
    protected Pair<Integer, Integer> entryBackground() {
        return EntryBackground.DEFAULT;
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(OccultismItems.CHALK_BLUE.get());
    }

    @Override
    protected String entryId() {
        return ENTRY_ID;
    }
}
