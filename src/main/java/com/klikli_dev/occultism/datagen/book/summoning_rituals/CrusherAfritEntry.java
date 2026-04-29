package com.klikli_dev.occultism.datagen.book.summoning_rituals;

import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.EntryBackground;
import com.klikli_dev.modonomicon.api.datagen.EntryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookTextPageModel;
import com.klikli_dev.occultism.integration.modonomicon.pages.BookRitualRecipePageModel;
import com.klikli_dev.occultism.registry.OccultismItems;
import com.klikli_dev.modonomicon.client.gui.book.theme.GuiSprite;

public class CrusherAfritEntry extends EntryProvider {

    public static final String ENTRY_ID = "summon_crusher_t3";

    public CrusherAfritEntry(CategoryProvider parent) {
        super(parent);
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(OccultismItems.GOLD_DUST);
    }

    @Override
    protected String entryName() {
        return "Summon Afrit Crusher";
    }

    @Override
    protected String entryDescription() {
        return "x4";
    }

    @Override
    protected void generatePages() {
        this.page("intro", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText()));
        this.pageTitle("Afrit Crusher");
        this.pageText("""
                The afrit crusher is faster and more efficient than the djinni crusher.
                \\
                \\
                It will crush **one** ore into **four** corresponding dusts.
                """
        );

        this.page("ritual", () -> BookRitualRecipePageModel.create()
                .withRecipeId1(this.modLoc("ritual/summon_afrit_crusher")));
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
