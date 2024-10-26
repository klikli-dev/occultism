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

public class PurpleChalkEntry extends EntryProvider {

    public static final String ENTRY_ID = "purple_chalk";


    public PurpleChalkEntry(CategoryProvider parent) {
        super(parent);
    }

    @Override
    protected void generatePages() {


        this.page("lore", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText())
        );
        this.pageTitle("Infusing");
        this.pageText("""
                        The purple chalk is extremely important for those wishing to perform infusions,
                         serving as the main glyph in this type of ritual.
                        """
        );

        this.page("spotlight", () -> BookSpotlightPageModel.create()
                .withItem(Ingredient.of(OccultismItems.CHALK_PURPLE.get()))
                .withText(this.context().pageText()));
        this.pageText("""
                        Infusions are an extremely different type of ritual, as while summoning and possession
                         bring living creatures into the world, infusion creates objects bound to spirits.
                        """
        );

        this.page("recipe_impure", () -> BookCraftingRecipePageModel.create()
                .withRecipeId1(this.modLoc("crafting/chalk_purple_impure"))
                .withText(this.context.pageText()));
        this.pageText("""
                You do not need to visit the {0} to obtain Endstone. You can summon a
                 [Possessed Endermite](entry://possession_rituals/possess_endermite) which has a high chance to drop it.
                """,
        this.color("The End", ChatFormatting.LIGHT_PURPLE));

        this.page("recipe", () -> BookSpiritFireRecipePageModel.create()
                .withRecipeId1(this.modLoc("spirit_fire/chalk_purple"))
        );

    }

    @Override
    protected String entryName() {
        return "The Chalk of Infusion";
    }

    @Override
    protected String entryDescription() {
        return "Purple Chalk";
    }

    @Override
    protected Pair<Integer, Integer> entryBackground() {
        return EntryBackground.DEFAULT;
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(OccultismItems.CHALK_PURPLE.get());
    }

    @Override
    protected String entryId() {
        return ENTRY_ID;
    }
}
