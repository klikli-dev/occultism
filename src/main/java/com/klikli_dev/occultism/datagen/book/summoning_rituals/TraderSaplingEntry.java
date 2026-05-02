package com.klikli_dev.occultism.datagen.book.summoning_rituals;

import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.EntryBackground;
import com.klikli_dev.modonomicon.api.datagen.EntryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookTextPageModel;
import com.klikli_dev.modonomicon.client.gui.book.theme.GuiSprite;
import com.klikli_dev.occultism.integration.modonomicon.pages.BookRitualRecipePageModel;
import com.klikli_dev.occultism.integration.modonomicon.pages.BookSpiritTradeRecipePageModel;
import com.klikli_dev.occultism.registry.OccultismBlocks;
import net.minecraft.ChatFormatting;

public class TraderSaplingEntry extends EntryProvider {

    public static final String ENTRY_ID = "summon_otherworld_sapling_trader";

    public TraderSaplingEntry(CategoryProvider parent) {
        super(parent);
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(OccultismBlocks.OTHERWORLD_SAPLING);
    }

    @Override
    protected String entryName() {
        return "Summon Otherworld Sapling Trader";
    }

    @Override
    protected String entryDescription() {
        return "You don't need to break the unstable tree with a pick";
    }

    @Override
    protected void generatePages() {
        this.page("intro", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText()));
        this.pageTitle("Otherworld Sapling Trader");
        this.pageText("""
                        Otherworld Trees grown from natural Otherworld Saplings can only be harvested
                        when under the effect of {0}. To make life easier, the Otherworld Sapling Trader
                        will exchange such natural saplings for a stable variant that can be harvested
                        by anyone, and will drop the same stable saplings when harvested.
                        """,
                this.color("Third Eye", ChatFormatting.DARK_PURPLE)
        );

        this.page("trade", () -> BookSpiritTradeRecipePageModel.create()
                .withRecipeId1(this.modLoc("spirit_trade/otherworld_sapling"))
                .withText(this.context().pageText()));
        this.pageText("""
                To trade, drop your offered item next to the trader,
                he will pick it up and drop the exchanged item.
                """
        );

        this.page("ritual", () -> BookRitualRecipePageModel.create()
                .withRecipeId1(this.modLoc("ritual/summon_foliot_sapling_trader")));
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
