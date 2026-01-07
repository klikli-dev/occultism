package com.klikli_dev.occultism.datagen.book.summoning_rituals;

import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.EntryBackground;
import com.klikli_dev.modonomicon.api.datagen.EntryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookTextPageModel;
import com.klikli_dev.occultism.integration.modonomicon.pages.BookRitualRecipePageModel;
import com.klikli_dev.occultism.registry.OccultismItems;
import com.mojang.datafixers.util.Pair;

public class TraderGemsEntry extends EntryProvider {

    public static final String ENTRY_ID = "summon_gambler";

    public TraderGemsEntry(CategoryProvider parent) {
        super(parent);
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(OccultismItems.SPIRIT_ATTUNED_GEM);
    }

    @Override
    protected String entryName() {
        return "Summon Gem Gambler";
    }

    @Override
    protected String entryDescription() {
        return "Casino Spirit";
    }

    @Override
    protected void generatePages() {
        this.page("intro", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText()));
        this.pageTitle("Djinni Gambler");
        this.pageText("""
                    Betting is a type of trade, right? Maybe random exchanger.
                    \\
                    The Gambler spirit allows you to wager some gems to receive
                    another random gem or a nugget, including the rare {0}.
                    \\
                    Be careful not to leave the resulting gems on the ground.
                    Collect them quickly because he is cunning and never stops playing...
                    """,
                    this.itemLink(OccultismItems.IESNIUM_NUGGET)
        );

        this.page("ritual", () -> BookRitualRecipePageModel.create()
                .withRecipeId1(this.modLoc("ritual/summon_djinni_gambler")));
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
