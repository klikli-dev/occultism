package com.klikli_dev.occultism.datagen.book.pentacles;

import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.EntryBackground;
import com.klikli_dev.modonomicon.api.datagen.EntryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookTextPageModel;
import com.klikli_dev.occultism.registry.OccultismItems;
import com.mojang.datafixers.util.Pair;

public class DidacticsEntry extends EntryProvider {

    public static final String ENTRY_ID = "didactics";


    public DidacticsEntry(CategoryProvider parent) {
        super(parent);
    }

    @Override
    protected void generatePages() {

        this.page("intro", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText()));
        this.pageTitle("About");
        this.pageText("""
                        This category is a roadmap that guides you step by step, follow the unlocked entries.\\
                        \\
                       **Tip:** By default, to set up all pentacles, you need four 19x19 areas.
                        """
        );

        this.page("table", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText()));
        this.pageTitle("Types and Tier");
        this.pageText("""
                        This category is organized as a table.\\
                         Each line refers to a type of ritual.\\
                         And each column represents a tier.\\
                         By following the line you can just upgrade the pentacle you drew before,
                         working like the previous and the new one.
                        """
        );

    }

    @Override
    protected String entryName() {
        return "Reading this Section";
    }

    @Override
    protected String entryDescription() {
        return "Basic learning";
    }

    @Override
    protected Pair<Integer, Integer> entryBackground() {
        return EntryBackground.DEFAULT;
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(OccultismItems.BRUSH.get());
    }

    @Override
    protected String entryId() {
        return ENTRY_ID;
    }
}
