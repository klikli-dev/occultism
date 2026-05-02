package com.klikli_dev.occultism.datagen.book.pentacles;

import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.EntryBackground;
import com.klikli_dev.modonomicon.api.datagen.EntryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookMultiblockPageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookTextPageModel;
import com.klikli_dev.modonomicon.client.gui.book.theme.GuiSprite;
import com.klikli_dev.occultism.registry.OccultismItems;
import net.minecraft.ChatFormatting;

public class SummonUnboundAfritEntry extends EntryProvider {

    public static final String ENTRY_ID = "summon_unbound_afrit";


    public SummonUnboundAfritEntry(CategoryProvider parent) {
        super(parent);
    }

    @Override
    protected void generatePages() {

        this.page("intro", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText())
        );
        this.pageTitle("Kandar's Open Conjure");
        this.pageText("""
                        **Purpose:** Summon an {0}\\
                        \\
                        **Kandars Open Conjure** is a simplified version of {1}, allowing to summon {2} without red chalk.
                         Due to the much reduced power of the pentacle, it cannot be used to control {3},
                         and it thus can only be used to fight and kill {4}.
                        """,
                this.color("Unbound Afrit", ChatFormatting.DARK_PURPLE),
                this.color("Abras Conjure", ChatFormatting.LIGHT_PURPLE),
                this.color("Afrit", ChatFormatting.DARK_PURPLE),
                this.color("Afrit", ChatFormatting.DARK_PURPLE),
                this.color("Afrit", ChatFormatting.DARK_PURPLE)
        );

        this.page("multiblock", () -> BookMultiblockPageModel.create().withMultiblockId(this.modLoc(ENTRY_ID)));

        this.page("uses", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText())
        );
        this.pageTitle("Uses");
        this.pageText("""
                - [Afrit Essence](entry://summoning_rituals/afrit_essence)
                """
        );

    }

    @Override
    protected String entryName() {
        return "Kandar's Open Conjure";
    }

    @Override
    protected String entryDescription() {
        return "Unbound Afrit Summoning";
    }

    @Override
    protected GuiSprite entryBackground() {
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
