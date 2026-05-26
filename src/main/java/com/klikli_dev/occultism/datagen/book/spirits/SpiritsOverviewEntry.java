package com.klikli_dev.occultism.datagen.book.spirits;

import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.EntryBackground;
import com.klikli_dev.modonomicon.api.datagen.EntryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookTextPageModel;
import com.klikli_dev.modonomicon.client.gui.book.theme.GuiSprite;

public class SpiritsOverviewEntry extends EntryProvider {

    public static final String ENTRY_ID = "overview";

    public SpiritsOverviewEntry(CategoryProvider parent) {
        super(parent);
    }

    @Override
    protected void generatePages() {
        this.page("intro", () -> BookTextPageModel.create().withTitle(this.context().pageTitle()).withText(this.context().pageText()));
        this.pageTitle("On Spirits");
        this.pageText("""
                [#]({0})Spirit[#](), commonly referred to also as [#]({0})Demon[#](), is a general term for a variety of supernatural entities usually residing in [#]({0})The Other Place[#](), a plane of existence entirely separate from our own.
                """, "ad03fc");

        this.page("shapes", () -> BookTextPageModel.create().withTitle(this.context().pageTitle()).withText(this.context().pageText()));
        this.pageTitle("Shapes");
        this.pageText("""
                When in our world Spirits can take a variety of forms, by morphing their essence into [#]({0})Chosen Forms[#](). Alternatively, they can inhabit objects or even living beings.
                """, "ad03fc");

        this.page("tiers", () -> BookTextPageModel.create().withTitle(this.context().pageTitle()).withText(this.context().pageText()));
        this.pageTitle("Types of Spirits");
        this.pageText("""
                There are four major "ranks" of spirits identified by researchers, but there are a myriad spirits below and in between these ranks, and some great entities of terrible power, referred to only as [#]({0})Greater Spirits[#](), that are beyond classification.
                """, "ad03fc");

        this.page("foliot", () -> BookTextPageModel.create().withTitle(this.context().pageTitle()).withText(this.context().pageText()));
        this.pageTitle("Foliot");
        this.pageText("""
                The lowest identified class of spirit. Equipped with some intelligence and a modicum of power they are most often used for manual labor or minor artifacts.
                """);

        this.page("djinni", () -> BookTextPageModel.create().withTitle(this.context().pageTitle()).withText(this.context().pageText()));
        this.pageTitle("Djinni");
        this.pageText("""
                The most commonly summoned class. There is a great variety of Djinni, differing both in intelligence and power. Djinni can be used for a variety of task, ranging from higher artifacts over possession of living beings to carrying out tasks in their Chosen Form.
                """);

        this.page("afrit", () -> BookTextPageModel.create().withTitle(this.context().pageTitle()).withText(this.context().pageText()));
        this.pageTitle("Afrit");
        this.pageText("""
                Even more powerful than Djinni, Afrit are used for the creation of major artifacts and the possession of powerful beings.
                """);

        this.page("marid", () -> BookTextPageModel.create().withTitle(this.context().pageTitle()).withText(this.context().pageText()));
        this.pageTitle("Marid");
        this.pageText("""
                The strongest identified class of spirits. Due to their power and vast intellect attempting a summoning is extremely dangerous and usually only carried out by the most experienced summoners, and even then usually in groups.
                """);

        this.page("greater_spirits", () -> BookTextPageModel.create().withTitle(this.context().pageTitle()).withText(this.context().pageText()));
        this.pageTitle("Greater Spirits");
        this.pageText("""
                Spirits of power so great it is beyond measure. No summons have been attempted in living memory, and records of summonings in ancient times are mostly considered apocryphal.
                """);
    }

    @Override
    protected String entryName() {
        return "On Spirits";
    }

    @Override
    protected String entryDescription() {
        return "An overview of the supernatural";
    }

    @Override
    protected GuiSprite entryBackground() {
        return EntryBackground.STAR_GOLD;
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(this.modLoc("textures/gui/book/spirits.png"));
    }

    @Override
    protected String entryId() {
        return ENTRY_ID;
    }
}
