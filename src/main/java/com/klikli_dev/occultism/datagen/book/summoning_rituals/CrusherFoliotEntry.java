package com.klikli_dev.occultism.datagen.book.summoning_rituals;

import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.EntryBackground;
import com.klikli_dev.modonomicon.api.datagen.EntryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookTextPageModel;
import com.klikli_dev.modonomicon.client.gui.book.theme.GuiSprite;
import com.klikli_dev.occultism.integration.modonomicon.pages.BookRitualRecipePageModel;
import com.klikli_dev.occultism.registry.OccultismItems;

public class CrusherFoliotEntry extends EntryProvider {

    public static final String ENTRY_ID = "summon_crusher_t1";

    public CrusherFoliotEntry(CategoryProvider parent) {
        super(parent);
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(OccultismItems.COPPER_DUST);
    }

    @Override
    protected String entryName() {
        return "Summon Foliot Crusher";
    }

    @Override
    protected String entryDescription() {
        return "x2";
    }

    @Override
    protected void generatePages() {
        this.page("about_crushers", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText()));
        this.pageTitle("Crusher Spirits");
        this.pageText("""
                  Crusher spirits are summoned to crush ores into dusts, effectively multiplying the metal output.
                   They will pick up appropriate ores and drop the resulting dusts into the world.
                   A spark particle effect and a crushing sound indicate the crusher is at work.
                """
        );

        this.page("automation", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText()));
        this.pageTitle("Automation");
        this.pageText("""
                        To ease automation, try summoning a {0} to place items from chests
                         in the crusher''s inventory, and a {1} to collect the processed items.
                        """,
                this.entryLink("Transporter Spirit", "summoning_rituals", "summon_transport_items"),
                this.entryLink("Janitor Spirit", "summoning_rituals", "summon_cleaner")
        );

        this.page("intro", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText()));
        this.pageTitle("Foliot Crusher");
        this.pageText("""
                The foliot crusher is the most basic crusher spirit.
                 \\
                 \\
                 It will crush **one** ore into **two** corresponding dusts.
                """
        );

        this.page("ritual", () -> BookRitualRecipePageModel.create()
                .withRecipeId1(this.modLoc("ritual/summon_foliot_crusher")));
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
