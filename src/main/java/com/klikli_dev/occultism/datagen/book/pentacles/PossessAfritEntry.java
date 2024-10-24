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

public class PossessAfritEntry extends EntryProvider {

    public static final String ENTRY_ID = "possess_afrit";


    public PossessAfritEntry(CategoryProvider parent) {
        super(parent);
    }

    @Override
    protected void generatePages() {

        this.page("intro", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText())
        );
        this.pageTitle("Abras Commanding Conjure");
        this.pageText("""
                **Purpose:** {0} Possession\\
                \\
                **Abras Commanding Conjure** is a modified version in the possession geometry of {1} that allows
                 possessing entities, and thus summoning familiars.
                """,
                this.color("Afrit", ChatFormatting.DARK_PURPLE),
                this.color("Abras Conjure", ChatFormatting.LIGHT_PURPLE)
        );

        this.page("multiblock", () -> BookMultiblockPageModel.create().withMultiblockId(this.modLoc(ENTRY_ID)));

        this.page("uses", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText())
        );
        this.pageTitle("Uses");
        this.pageText("""
                - [Guardian Familiar](entry://familiar_rituals/familiar_guardian)
                - [Possessed Shulker](entry://possession_rituals/possess_shulker)
                - [Possessed Elder Guardian](entry://possession_rituals/possess_elder_guardian)
                - [Possessed Hoglin](entry://possession_rituals/possess_hoglin)
                """
        );

    }

    @Override
    protected String entryName() {
        return "Abras' Commanding Conjure";
    }

    @Override
    protected String entryDescription() {
        return "Possessing Afrit";
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
