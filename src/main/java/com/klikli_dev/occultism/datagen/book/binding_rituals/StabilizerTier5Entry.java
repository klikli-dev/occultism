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

public class StabilizerTier5Entry extends EntryProvider {

    public static final String ENTRY_ID = "stabilizer_tier5";

    public StabilizerTier5Entry(CategoryProvider parent) {
        super(parent);
    }

    @Override
    protected void generatePages() {
        this.page("spotlight", () -> BookSpotlightPageModel.create()
                .withItem(Ingredient.of(OccultismBlocks.STORAGE_STABILIZER_TIER5))
                .withText(this.context().pageText()));
        this.pageText("""
                        This extremely advanced stabilizer maybe is inhabited by a {0} that supports
                         the dimensional matrix in keeping the storage dimension stable, thus allowing to store even more items.
                        \\
                        \\
                        By default each Tier 5 Stabilizer adds **1024** item types and 8196000 items storage capacity.
                                                
                        """,
                this.color("Ancient Spirit", ChatFormatting.DARK_PURPLE)
        );

        this.page("ritual", () -> BookRitualRecipePageModel.create()
                .withRecipeId1(this.modLoc("ritual/misc_stabilizer_tier5"))
        );
        //no text
        this.page("ritual_dark", () -> BookRitualRecipePageModel.create()
                .withRecipeId1(this.modLoc("ritual/misc_stabilizer_tier5_dark"))
        );
        //no text
    }

    @Override
    protected String entryName() {
        return "Storage Stabilizer Tier 5";
    }

    @Override
    protected String entryDescription() {
        return "Tons of items";
    }

    @Override
    protected Pair<Integer, Integer> entryBackground() {
        return EntryBackground.DEFAULT;
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(OccultismBlocks.STORAGE_STABILIZER_TIER5);
    }

    @Override
    protected String entryId() {
        return ENTRY_ID;
    }
}
