package com.klikli_dev.occultism.datagen.book.summoning_rituals;

import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.EntryBackground;
import com.klikli_dev.modonomicon.api.datagen.EntryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookTextPageModel;
import com.klikli_dev.modonomicon.client.gui.book.theme.GuiSprite;
import com.klikli_dev.occultism.integration.modonomicon.pages.BookRitualRecipePageModel;
import com.klikli_dev.occultism.registry.OccultismItems;
import net.minecraft.ChatFormatting;

public class TraderWonderingEntry extends EntryProvider {

    public static final String ENTRY_ID = "summon_wondering";

    public TraderWonderingEntry(CategoryProvider parent) {
        super(parent);
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(OccultismItems.SPAWN_EGG_WONDERING_TRADER);
    }

    @Override
    protected String entryName() {
        return "Summon Wondering Trader";
    }

    @Override
    protected String entryDescription() {
        return "Otherworld Traveling Merchant";
    }

    @Override
    protected void generatePages() {
        this.page("intro", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText()));
        this.pageTitle("Wondering Trader");
        this.pageText("""
                        The true form of this merchant will only be revealed with {0} or {1},
                         otherwise, he is identical to a common Wandering Trader,
                         perhaps you have already encountered one without realizing it.
                         \\
                         \\
                         The first time you interact with him, mundane sales are always shown.
                         These vendors love trying to sell everything they have.
                        """,
                this.color("Third Eye", ChatFormatting.DARK_PURPLE),
                this.itemLink(OccultismItems.OTHERWORLD_GOGGLES)
        );

        this.page("ritual", () -> BookRitualRecipePageModel.create()
                .withRecipeId1(this.modLoc("ritual/summon_wondering_trader")));
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
