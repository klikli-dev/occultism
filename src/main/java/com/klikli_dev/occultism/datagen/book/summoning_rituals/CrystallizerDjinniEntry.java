package com.klikli_dev.occultism.datagen.book.summoning_rituals;

import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.EntryBackground;
import com.klikli_dev.modonomicon.api.datagen.EntryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookTextPageModel;
import com.klikli_dev.occultism.integration.modonomicon.pages.BookRitualRecipePageModel;
import com.mojang.datafixers.util.Pair;
import net.minecraft.world.item.Items;

public class CrystallizerDjinniEntry extends EntryProvider {

    public static final String ENTRY_ID = "summon_crystallizer_t2";

    public CrystallizerDjinniEntry(CategoryProvider parent) {
        super(parent);
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(Items.AMETHYST_SHARD);
    }

    @Override
    protected String entryName() {
        return "Summon Djinni Crystallizer";
    }

    @Override
    protected String entryDescription() {
        return "Nice gems";
    }

    @Override
    protected void generatePages() {
        this.page("intro", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText()));
        this.pageTitle("Djinni Crystallizer");
        this.pageText("""
                    The djinni crystallizer is faster and more efficient, doubling the speed of processing and increasing the output amount from gem ores.
                    \\
                    \\
                    They also can deconstruct amethyst and quartz blocks.
                    """
        );

        this.page("ritual", () -> BookRitualRecipePageModel.create()
                .withRecipeId1(this.modLoc("ritual/summon_djinni_crystallizer")));
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
