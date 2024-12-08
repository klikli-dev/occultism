package com.klikli_dev.occultism.datagen.book.pentacles;

import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.EntryBackground;
import com.klikli_dev.modonomicon.api.datagen.EntryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookMultiblockPageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookTextPageModel;
import com.klikli_dev.occultism.registry.OccultismItems;
import com.mojang.datafixers.util.Pair;
import net.minecraft.ChatFormatting;

public class ResurrectSpiritEntry extends EntryProvider {

    public static final String ENTRY_ID = "resurrect_spirit";


    public ResurrectSpiritEntry(CategoryProvider parent) {
        super(parent);
    }

    @Override
    protected void generatePages() {

        this.page("intro", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText())
        );
        this.pageTitle("Susjes Simple Circle");
        this.pageText("""
                **Purpose:** Resurrect a {0}\\
                \\
                **Susjes Simple Circle** is a simple pentacle that returns the physical form of a spirit that has
                 been slaughtered, not needing much to do so.
                """,
                this.color("Familiar", ChatFormatting.DARK_PURPLE)
        );

        this.page("multiblock", () -> BookMultiblockPageModel.create().withMultiblockId(this.modLoc(ENTRY_ID)));

        this.page("uses", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText())
        );
        this.pageTitle("Uses");
        this.pageText("""
                - [Resurrect Familiar](entry://familiar_rituals/resurrection)
                - [Purify Vex to Allay](entry://familiar_rituals/resurrect_allay)
                """
        );

    }

    @Override
    protected String entryName() {
        return "Susje's Simple Circle";
    }

    @Override
    protected String entryDescription() {
        return "Spirit Resurrection";
    }

    @Override
    protected Pair<Integer, Integer> entryBackground() {
        return EntryBackground.DEFAULT;
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(OccultismItems.PENTACLE_MISC.get());
    }

    @Override
    protected String entryId() {
        return ENTRY_ID;
    }
}
