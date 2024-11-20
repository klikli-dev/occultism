package com.klikli_dev.occultism.datagen.book.binding_rituals;

import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.EntryBackground;
import com.klikli_dev.modonomicon.api.datagen.EntryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookSpotlightPageModel;
import com.klikli_dev.occultism.integration.modonomicon.pages.BookRitualRecipePageModel;
import com.klikli_dev.occultism.registry.OccultismBlocks;
import com.mojang.datafixers.util.Pair;
import net.minecraft.ChatFormatting;
import net.minecraft.world.item.crafting.Ingredient;

public class IesniumSacrificialBowlEntry extends EntryProvider {

    public static final String ENTRY_ID = "craft_iesnium_sacrificial_bowl";


    public IesniumSacrificialBowlEntry(CategoryProvider parent) {
        super(parent);
    }

    @Override
    protected void generatePages() {
        this.page("spotlight", () -> BookSpotlightPageModel.create()
                .withItem(Ingredient.of(OccultismBlocks.IESNIUM_SACRIFICIAL_BOWL.asItem()))
                .withText(this.context().pageText()));
        this.pageText("""
                        The [](item://occultism:iesnium_sacrificial_bowl) is an {0} infusion 
                         that helps expert occultists save time,
                         performing any ritual in only a quarter of the normal time.
                         All other things will works like the Golden Sacrificial Bowl.
                        """,
                this.color("Afrit", ChatFormatting.DARK_PURPLE)
        );

        this.page("ritual", () -> BookRitualRecipePageModel.create()
                .withRecipeId1(this.modLoc("ritual/craft_iesnium_sacrificial_bowl"))
        );
        //no text
    }

    @Override
    protected String entryName() {
        return "Iesnium Sacrificial Bowl";
    }

    @Override
    protected String entryDescription() {
        return "Faster Rituals";
    }

    @Override
    protected Pair<Integer, Integer> entryBackground() {
        return EntryBackground.DEFAULT;
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(OccultismBlocks.IESNIUM_SACRIFICIAL_BOWL.asItem());
    }

    @Override
    protected String entryId() {
        return ENTRY_ID;
    }
}
