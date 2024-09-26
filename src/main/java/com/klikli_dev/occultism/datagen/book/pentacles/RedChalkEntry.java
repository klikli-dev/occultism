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

public class RedChalkEntry extends EntryProvider {

    public static final String ENTRY_ID = "red_chalk";


    public RedChalkEntry(CategoryProvider parent) {
        super(parent);
    }

    @Override
    protected void generatePages() {

        this.page("lore", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText())
        );
        this.pageTitle("Under Control");
        this.pageText("""
                        The red chalk is made from the very essence of Afrit, making its glyphs directly
                         connected to these spirits. Additionally, it elevates the pentagrams
                          capacity to the most demonic levels.
                        """
        );

        this.page("spotlight", () -> BookSpotlightPageModel.create()
                .withItem(Ingredient.of(OccultismItems.CHALK_RED.get()))
                .withText(this.context().pageText()));
        this.pageText("""
                        These properties allow for an incredible achievement that has been sought
                         for ages before being attained: the summoning of an Afrit bound to the occultist.
                        """
        );

        this.page("essence", () -> BookSpotlightPageModel.create()
                .withItem(Ingredient.of(OccultismItems.AFRIT_ESSENCE.get()))
                .withText(this.context().pageText()));
        this.pageText("""
                        To obtain the essence of an {0} for [](item://occultism:chalk_red) you need to
                         [summon and kill an Unbound Afrit](entry://summoning_rituals/afrit_essence).
                        """,
                this.color("Afrit", ChatFormatting.DARK_PURPLE)
        );

        this.page("recipe_impure", () -> BookCraftingRecipePageModel.create()
                .withRecipeId1(this.modLoc("crafting/chalk_red_impure"))
        );

        this.page("recipe", () -> BookSpiritFireRecipePageModel.create()
                .withRecipeId1(this.modLoc("spirit_fire/chalk_red"))
        );

    }

    @Override
    protected String entryName() {
        return "The Afrit Control Chalk";
    }

    @Override
    protected String entryDescription() {
        return "Red Chalk";
    }

    @Override
    protected Pair<Integer, Integer> entryBackground() {
        return EntryBackground.DEFAULT;
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(OccultismItems.CHALK_RED.get());
    }

    @Override
    protected String entryId() {
        return ENTRY_ID;
    }
}
