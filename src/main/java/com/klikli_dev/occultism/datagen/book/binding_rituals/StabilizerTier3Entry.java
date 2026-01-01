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

public class StabilizerTier3Entry extends EntryProvider {

    public static final String ENTRY_ID = "craft_stabilizer_tier3";

    public StabilizerTier3Entry(CategoryProvider parent) {
        super(parent);
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(OccultismBlocks.STORAGE_STABILIZER_TIER3);
    }

    @Override
    protected String entryName() {
        return "Storage Stabilizer Tier 3";
    }

    @Override
    protected String entryDescription() {
        return "Lots of items";
    }

    @Override
    protected void generatePages() {
        this.page("spotlight", () -> BookSpotlightPageModel.create()
                .withItem(Ingredient.of(OccultismBlocks.STORAGE_STABILIZER_TIER3))
                .withText(this.context().pageText()));
        this.pageText("""
                        This advanced stabilizer is inhabited by a {0} that supports the dimensional matrix
                         in keeping the storage dimension stable, thus allowing to store more items.
                        \\
                        \\
                        By default each Tier 3 Stabilizer adds **256** item types and 2048000 items storage capacity.
                        """,
                this.color("Afrit", ChatFormatting.DARK_PURPLE)
        );

        this.page("ritual", () -> BookRitualRecipePageModel.create()
                .withRecipeId1(this.modLoc("ritual/craft_stabilizer_tier3"))
        );
        //no text

        this.page("ritual_dark", () -> BookRitualRecipePageModel.create()
                .withRecipeId1(this.modLoc("ritual/craft_stabilizer_tier3_dark"))
        );
        //no text
    }

    @Override
    protected Pair<Integer, Integer> entryBackground() {
        return EntryBackground.DEFAULT;
    }

    @Override
    protected String entryId() {
        return ENTRY_ID;
    }
}
