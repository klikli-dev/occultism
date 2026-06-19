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
        this.pageTitle("Odus' Open Convocation");
        this.pageText("""
                        **Purpose:** {0} Possession\\
                        \\
                        **Odus Open Convocation** is a simplified version of {1}, allowing you to forces {2} to possess a nearby Creature without red chalk.
                         Due to the very reduced power of the Pentacle it's limited in use.
                        """,
                this.color("Unbound Afrit", ChatFormatting.DARK_PURPLE),
                this.color("Posuc' Convocation", ChatFormatting.DARK_PURPLE),
                this.color("Afrit", ChatFormatting.DARK_PURPLE)
        );

        this.page("multiblock", () -> BookMultiblockPageModel.create().withMultiblockId(this.modLoc(ENTRY_ID)));

        this.page("uses", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText())
        );
        this.pageTitle("Uses");
        this.pageText("""
                - {0}
                - {1}
                """, this.entryLink("Possessed Zombified Piglin", "possession_rituals", "possess_zombified_piglin"), this.entryLink("Possessed Guardian", "possession_rituals", "possess_guardian"));

    }

    @Override
    protected String entryName() {
        return "Odus' Open Convocation";
    }

    @Override
    protected String entryDescription() {
        return "Unbound Afrit Possession";
    }

    @Override
    protected GuiSprite entryBackground() {
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
