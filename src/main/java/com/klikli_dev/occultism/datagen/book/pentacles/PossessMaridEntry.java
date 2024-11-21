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

public class PossessMaridEntry extends EntryProvider {

    public static final String ENTRY_ID = "possess_marid";


    public PossessMaridEntry(CategoryProvider parent) {
        super(parent);
    }

    @Override
    protected void generatePages() {

        this.page("intro", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText())
        );
        this.pageTitle("Xeovrenth Adjure");
        this.pageText("""
                **Purpose:** {0} Possession\\
                \\
                **Xeovrenth Adjure** is a powerful pentacle, allowing to possessing {1} in extremely dangerous rituals.
                """,
                this.color("Marid", ChatFormatting.DARK_PURPLE),
                this.color("Marid", ChatFormatting.DARK_PURPLE)
        );

        this.page("multiblock", () -> BookMultiblockPageModel.create().withMultiblockId(this.modLoc(ENTRY_ID)));

        this.page("uses", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText())
        );
        this.pageTitle("Uses");
        this.pageText("""
                - [Iesnium Golem](entry://familiar_rituals/iesnium_golem)
                - [Cruelty Essence](entry://possession_rituals/possess_goat)
                """
        );

    }

    @Override
    protected String entryName() {
        return "Xeovrenth Adjure";
    }

    @Override
    protected String entryDescription() {
        return "Marid Possession";
    }

    @Override
    protected Pair<Integer, Integer> entryBackground() {
        return EntryBackground.DEFAULT;
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(OccultismItems.PENTACLE_POSSESS.get());
    }

    @Override
    protected String entryId() {
        return ENTRY_ID;
    }
}
