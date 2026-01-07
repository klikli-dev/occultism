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

public class MinerAfritEntry extends EntryProvider {

    public static final String ENTRY_ID = "craft_afrit_miner";

    public MinerAfritEntry(CategoryProvider parent) {
        super(parent);
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(OccultismItems.MINER_AFRIT_DEEPS);
    }

    @Override
    protected String entryName() {
        return "Afrit Miner";
    }

    @Override
    protected String entryDescription() {
        return "Deep mining";
    }

    @Override
    protected void generatePages() {
        this.page("spotlight", () -> BookSpotlightPageModel.create()
                .withItem(Ingredient.of(OccultismItems.MINER_AFRIT_DEEPS))
                .withText(this.context().pageText()));
        this.pageText("""
                        The {0} miner harvests ores, like djinni miners, and additionally
                         mines some deepslate ores. This miner is faster and more efficient
                         than the djinnis, thus damaging the magic lamp even more slowly.
                        """,
                this.color("Afrit", ChatFormatting.DARK_PURPLE)
        );

        this.page("ritual", () -> BookRitualRecipePageModel.create()
                .withRecipeId1(this.modLoc("ritual/craft_miner_afrit_deeps"))
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
