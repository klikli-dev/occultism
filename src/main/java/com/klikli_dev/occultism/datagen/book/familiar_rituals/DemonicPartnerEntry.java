package com.klikli_dev.occultism.datagen.book.familiar_rituals;

import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.EntryBackground;
import com.klikli_dev.modonomicon.api.datagen.EntryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookEntityPageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookTextPageModel;
import com.klikli_dev.occultism.integration.modonomicon.pages.BookRitualRecipePageModel;
import com.klikli_dev.occultism.registry.OccultismItems;
import com.mojang.datafixers.util.Pair;

public class DemonicPartnerEntry extends EntryProvider {

    public static final String ENTRY_ID = "demonic_partner";


    public DemonicPartnerEntry(CategoryProvider parent) {
        super(parent);
    }

    @Override
    protected void generatePages() {


        this.page("description", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText())
        );
        this.pageTitle("Demonic Partner");
        this.pageText("""
                Magicians practicing the occult are a diverse crowd, coming from all creeds and all corners of the world. However one thing unites them all - they are as lonely as any human without a partner.
                \\
                \\
                Of course being a magician, the dating pool is larger than for most people, meeting all kinds of otherworldly beings, besides humans.
                """);

        this.page("description2", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText())
        );
        this.pageTitle("Demonic Partner");
        this.pageText("""
                As beings of immense powers Demons can have it all ... even love.\\
                In rare cases a Demon is so impressed by a mortal that they stay in touch. And in even rarer cases, take them on a date. And in such cases, the most unlikely thing can happen - love between a Spirit and a mortal.
                """);

        this.page("description3", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText())
        );
        this.pageTitle("Demonic Partner");
        this.pageText("""
                Demons, of course, like to deal in contracts, and what better contract than a marriage contract?\\
                Beware however, Spirits are all about commitment, so this is a permanent bond.
                """);

        this.page("about", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText())
        );
        this.pageTitle("Demonic Partner");
        this.pageText("""
                The Demonic Partner - a Husband or Wife - can fight for you and split your household chores.
                \\
                \\
                Right-Click with any cookable food and they will use their magic to cook it.
                \\
                \\
                Right-Click with a potion to get the effect for a significantly longer time.
                """);

        this.page("familiar", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText())
        );
        this.pageTitle("Not a Familiar");
        this.pageText("""
                        The Demonic Partner Chapter is part of the Familiar Category of this book because of the similarities, however a Partner is obviously not a familiar.
                        \\
                        \\
                        As such, they also cannot be stored in a {0}. You can, however, use a {1} as for any other being.
                        """,
                this.itemLink(OccultismItems.FAMILIAR_RING.get()),
                this.itemLink(OccultismItems.SOUL_GEM_ITEM.get())
        );

        this.page("husband", () -> BookEntityPageModel.create()
                .withEntityId("occultism:demonic_husband")
                .withEntityName(this.context().pageTitle())
        );
        this.pageTitle("Demonic Husband");

        this.page("ritual1", () -> BookRitualRecipePageModel.create()
                .withRecipeId1(this.modLoc("ritual/summon_demonic_husband"))
        );

        this.page("wife", () -> BookEntityPageModel.create()
                .withEntityId("occultism:demonic_wife")
                .withEntityName(this.context().pageTitle())
        );
        this.pageTitle("Demonic Wife");

        this.page("ritual2", () -> BookRitualRecipePageModel.create()
                .withRecipeId1(this.modLoc("ritual/summon_demonic_wife"))
        );

    }

    @Override
    protected String entryName() {
        return "Demonic Partner";
    }

    @Override
    protected String entryDescription() {
        return "A demonic Household?";
    }

    @Override
    protected Pair<Integer, Integer> entryBackground() {
        return EntryBackground.DEFAULT;
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(this.modLoc("textures/gui/book/demonic_partner.png"));
    }

    @Override
    protected String entryId() {
        return ENTRY_ID;
    }
}
