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

public class PossessUnboundAfritEntry extends EntryProvider {

    public static final String ENTRY_ID = "possess_unbound_afrit";


    public PossessUnboundAfritEntry(CategoryProvider parent) {
        super(parent);
    }

    @Override
    protected void generatePages() {

        this.page("intro", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText())
        );
        this.pageTitle("Abras Open Commanding Conjure");
        this.pageText("""
                **Purpose:** {0} Possession\\
                \\
                **Abras Open Commanding Conjure** is a simplified version of {1}, allowing you to forces {2} to possess a nearby Creature without red chalk.
                 Due to the very reduced power of the Pentacle it's limited in use.
                """,
                this.color("Unbound Afrit", ChatFormatting.DARK_PURPLE),
                this.color("Abras Commanding Conjure", ChatFormatting.DARK_PURPLE),
                this.color("Afrit", ChatFormatting.DARK_PURPLE)
        );

        this.page("multiblock", () -> BookMultiblockPageModel.create().withMultiblockId(this.modLoc(ENTRY_ID)));

        this.page("uses", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText())
        );
        this.pageTitle("Uses");
        this.pageText("""
                - [Possessed Zombified Piglin](entry://possession_rituals/possess_zombie_piglin)
                """
        );

    }

    @Override
    protected String entryName() {
        return "Abras' Open Commanding Conjure";
    }

    @Override
    protected String entryDescription() {
        return "Unbound Afrit Possession";
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
