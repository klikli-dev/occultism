package com.klikli_dev.occultism.datagen.book.summoning_rituals;

import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.EntryBackground;
import com.klikli_dev.modonomicon.api.datagen.EntryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookTextPageModel;
import com.klikli_dev.occultism.integration.modonomicon.pages.BookRitualRecipePageModel;
import com.klikli_dev.occultism.integration.modonomicon.pages.BookSpiritTradeRecipePageModel;
import com.klikli_dev.occultism.registry.OccultismBlocks;
import com.klikli_dev.occultism.registry.OccultismItems;
import com.klikli_dev.modonomicon.client.gui.book.theme.GuiSprite;

public class TraderOtherstoneEntry extends EntryProvider {

    public static final String ENTRY_ID = "summon_otherstone_trader";

    public TraderOtherstoneEntry(CategoryProvider parent) {
        super(parent);
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(OccultismBlocks.OTHERSTONE);
    }

    @Override
    protected String entryName() {
        return "Summon Otherstone Trader";
    }

    @Override
    protected String entryDescription() {
        return "Don't confuse with Otherrock trader";
    }

    @Override
    protected void generatePages() {
        this.page("intro", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText()));
        this.pageTitle("Otherstone Trader");
        this.pageText("""
                    The Otherstone Trader spirit allows to get more {0} than using {1}.
                    Thus it is especially efficient if you want to use Otherstone as a building material.
                    """,
                    this.itemLink(OccultismBlocks.OTHERSTONE),
                    this.itemLink(OccultismItems.SPIRIT_FIRE)
        );

        this.page("trade", () -> BookSpiritTradeRecipePageModel.create()
                .withRecipeId1(this.modLoc("spirit_trade/stone_to_otherstone"))
                .withText(this.context().pageText()));
        this.pageText("""
                    To trade, drop your offered item next to the trader,
                    he will pick it up and drop the exchanged item.
                    """
        );

        this.page("ritual", () -> BookRitualRecipePageModel.create()
                .withRecipeId1(this.modLoc("ritual/summon_foliot_otherstone_trader")));
        //no text

    }

    @Override
    protected GuiSprite entryBackground() {
        return EntryBackground.DEFAULT;
    }

    @Override
    protected String entryId() {
        return ENTRY_ID;
    }
}
