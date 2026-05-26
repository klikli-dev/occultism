package com.klikli_dev.occultism.datagen.book.spirits;

import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.EntryBackground;
import com.klikli_dev.modonomicon.api.datagen.EntryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookTextPageModel;
import com.klikli_dev.modonomicon.client.gui.book.theme.GuiSprite;
import net.minecraft.world.item.Items;

public class TrueNamesEntry extends EntryProvider {

    public static final String ENTRY_ID = "true_names";

    public TrueNamesEntry(CategoryProvider parent) {
        super(parent);
    }

    @Override
    protected void generatePages() {
        this.page("intro", () -> BookTextPageModel.create().withTitle(this.context().pageTitle()).withText(this.context().pageText()));
        this.pageTitle("True Names");
        this.pageText("""
                To summon a spirit the magician needs to know their [#]({0})True Name[#](). By calling the true naming during the summoning ritual the Spirit is drawn forth from [#]({0})The Other Place[#]() and forced to do the summoners bidding.

                *It should be noted, that it does not matter which spirit name is used in summoning, only the spirit tier is relevant.*
                """, "ad03fc");

        this.page("finding_names", () -> BookTextPageModel.create().withTitle(this.context().pageTitle()).withText(this.context().pageText()));
        this.pageTitle("Finding Names");
        this.pageText("""
                In ancient summoners had to research and experiment to find [#]({0})True Names[#](). Some spirits can be convinced to share their knowledge of true names of other demons, either by promising a swift return to [#]({0})The Other Place[#](), or by more ... *persuasive* measures.
                """, "ad03fc");

        this.page("using_names", () -> BookTextPageModel.create().withTitle(this.context().pageTitle()).withText(this.context().pageText()));
        this.pageTitle("Using Names to Summon a Spirit");
        this.pageText("""
                For your convenience, in this work you will find the known names of spirits of all 4 ranks, as well as some beyond that. To summon a spirit, copy their name from this book into the appropriate book of binding, then use this bound book of binding to activate a ritual.
                """);
    }

    @Override
    protected String entryName() {
        return "True Names";
    }

    @Override
    protected String entryDescription() {
        return "How to call spirits.";
    }

    @Override
    protected GuiSprite entryBackground() {
        return EntryBackground.DEFAULT;
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(Items.WRITABLE_BOOK);
    }

    @Override
    protected String entryId() {
        return ENTRY_ID;
    }
}
