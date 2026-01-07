package com.klikli_dev.occultism.datagen.book.summoning_rituals;

import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.EntryBackground;
import com.klikli_dev.modonomicon.api.datagen.EntryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookTextPageModel;
import com.mojang.datafixers.util.Pair;
import net.minecraft.ChatFormatting;

public class TraderSpiritsEntry extends EntryProvider {

    public static final String ENTRY_ID = "trade_spirits";

    public TraderSpiritsEntry(CategoryProvider parent) {
        super(parent);
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(this.modLoc("textures/gui/book/cash.png"));
    }

    @Override
    protected String entryName() {
        return "Trade Spirits";
    }

    @Override
    protected String entryDescription() {
        return "Time for a bargain";
    }

    @Override
    protected void generatePages() {
        this.page("intro", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText()));
        this.pageTitle("Trade Spirits");
        this.pageText("""
                    Trade spirits pick up appropriate items and throw trade results on the ground.
                    The spirit is only actively exchanging items if purple particles spawn around it.
                    \\
                    \\
                    **If you do not see any particles**, ensure that you gave the proper item and amount.
                    """
        );

        this.page("intro2", () -> BookTextPageModel.create()
                .withText(this.context().pageText()));
        this.pageText("""
                    Most trade spirits experience extreme essence decay and will return to {0} quickly.
                    """,
                    this.color("The Other Place", ChatFormatting.DARK_PURPLE)
        );
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
