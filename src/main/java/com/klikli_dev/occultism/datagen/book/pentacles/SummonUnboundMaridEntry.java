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

public class SummonUnboundMaridEntry extends EntryProvider {

    public static final String ENTRY_ID = "summon_unbound_marid";


    public SummonUnboundMaridEntry(CategoryProvider parent) {
        super(parent);
    }

    @Override
    protected void generatePages() {

        this.page("intro", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText())
        );
        this.pageTitle("Abras Fortified Conjure");
        this.pageText("""
                **Purpose:** Summon an {0}\\
                \\
                **Abras Fortified Conjure** is a improved version of {1}, allowing to summon {2},
                 however without any control of the occultist. {3} summoned by this wat is neutral
                 and will only attack you if you initiate combat.
                """,
                this.color("Unbound Marid", ChatFormatting.DARK_PURPLE),
                this.color("Abras Conjure", ChatFormatting.LIGHT_PURPLE),
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
                - [Marid Essence](entry://summoning_rituals/afrit_essence)
                """
        );

    }

    @Override
    protected String entryName() {
        return "Abras' Fortified Conjure";
    }

    @Override
    protected String entryDescription() {
        return "Unbound Marid Summoning";
    }

    @Override
    protected Pair<Integer, Integer> entryBackground() {
        return EntryBackground.DEFAULT;
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(OccultismItems.PENTACLE_SUMMON.get());
    }

    @Override
    protected String entryId() {
        return ENTRY_ID;
    }
}
