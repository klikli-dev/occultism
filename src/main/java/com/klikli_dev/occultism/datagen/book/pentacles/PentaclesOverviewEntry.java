package com.klikli_dev.occultism.datagen.book.pentacles;

import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.EntryBackground;
import com.klikli_dev.modonomicon.api.datagen.EntryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookSpotlightPageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookTextPageModel;
import com.klikli_dev.occultism.datagen.book.BindingRitualsCategory;
import com.klikli_dev.occultism.datagen.book.binding_rituals.ApprenticeRitualSatchelEntry;
import com.klikli_dev.occultism.registry.OccultismBlocks;
import com.klikli_dev.occultism.registry.OccultismItems;
import com.mojang.datafixers.util.Pair;
import net.minecraft.world.item.crafting.Ingredient;

public class PentaclesOverviewEntry extends EntryProvider {

    public static final String ENTRY_ID = "pentacle_overview";


    public PentaclesOverviewEntry(CategoryProvider parent) {
        super(parent);
    }

    @Override
    protected void generatePages() {

        this.page("intro", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText()));
        this.pageTitle("Overview");
        this.pageText("""
                        Basic text.
                        """
        );

    }

    @Override
    protected String entryName() {
        return "Lets Draw";
    }

    @Override
    protected String entryDescription() {
        return "Pentacles";
    }

    @Override
    protected Pair<Integer, Integer> entryBackground() {
        return EntryBackground.DEFAULT;
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(OccultismBlocks.SPIRIT_ATTUNED_CRYSTAL.get());
    }

    @Override
    protected String entryId() {
        return ENTRY_ID;
    }
}
