package com.klikli_dev.occultism.datagen.book.binding_rituals;

import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.EntryBackground;
import com.klikli_dev.modonomicon.api.datagen.EntryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookSpotlightPageModel;
import com.klikli_dev.occultism.integration.modonomicon.pages.BookRitualRecipePageModel;
import com.klikli_dev.occultism.registry.OccultismItems;
import com.mojang.datafixers.util.Pair;
import net.minecraft.ChatFormatting;
import net.minecraft.world.item.crafting.Ingredient;

public class MasterChalksEntry extends EntryProvider {

    public static final String ENTRY_ID = "craft_master_chalks";


    public MasterChalksEntry(CategoryProvider parent) {
        super(parent);
    }

    @Override
    protected void generatePages() {
        this.page("spotlight", () -> BookSpotlightPageModel.create()
                .withItem(Ingredient.of(OccultismItems.CHALK_RAINBOW))
                .withText(this.context().pageText()));
        this.pageText("""
                        Forging the [](item://occultism:chalk_rainbow) is a service provided by an {0}.
                        This chalk can replace any non-foundation chalk, with extra features.
                        1. Use a [](item://occultism:spirit_attuned_gem) to toggle the random color changes or
                        use any dye to set the color of glyph, items will not be consumed.
                        2. Use this chalk in a glyph while crouched will erase the glyph, acting as a [](item://occultism:brush).
                        """,
                this.color("Eldritch Spirit", ChatFormatting.DARK_PURPLE)
        );

        this.page("ritual", () -> BookRitualRecipePageModel.create()
                .withRecipeId1(this.modLoc("ritual/misc_chalk_rainbow"))
        );
        //no text
        this.page("spotlight2", () -> BookSpotlightPageModel.create()
                .withItem(Ingredient.of(OccultismItems.CHALK_VOID))
                .withText(this.context().pageText()));
        this.pageText("""
                        Forging the [](item://occultism:chalk_void) is a service provided by an {0}.
                        This chalk can replace any chalk and has the same abilities as the [](item://occultism:chalk_rainbow).
                        """,
                this.color("Eldritch Spirit", ChatFormatting.DARK_PURPLE)
        );

        this.page("ritual2", () -> BookRitualRecipePageModel.create()
                .withRecipeId1(this.modLoc("ritual/misc_chalk_void"))
        );
        //no text
    }

    @Override
    protected String entryName() {
        return "Mastery Chalks";
    }

    @Override
    protected String entryDescription() {
        return "Choose your colors";
    }

    @Override
    protected Pair<Integer, Integer> entryBackground() {
        return EntryBackground.DEFAULT;
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(OccultismItems.CHALK_RAINBOW);
    }

    @Override
    protected String entryId() {
        return ENTRY_ID;
    }
}
