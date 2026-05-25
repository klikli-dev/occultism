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

public class SummonFoliotEntry extends EntryProvider {

    public static final String ENTRY_ID = "summon_foliot";


    public SummonFoliotEntry(CategoryProvider parent) {
        super(parent);
    }

    @Override
    protected void generatePages() {

        this.page("intro", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText())
        );
        this.pageTitle("Aviars Circle");
        this.pageText("""
                        **Purpose:** Summon a {0}\\
                        \\
                        Considered by most to be the simplest pentacle, {1} is easy to set up,
                         but provides only a minimum of binding power and protection for the summoner.\\
                        \\
                        Only the weakest {2} can be summoned in rituals using this pentacle.
                        """,
                this.color("Foliot", ChatFormatting.DARK_PURPLE),
                this.color("Aviars Circle", ChatFormatting.LIGHT_PURPLE),
                this.color("Foliot", ChatFormatting.DARK_PURPLE)
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
                - {2}
                - {3}
                - {4}
                - {5}
                - {6}
                - {7}
                - {8}
                - {9}
                """, this.entryLink("Foliot Crusher", "summoning_rituals", "summon_crusher_t1"), this.entryLink("Foliot Smelter", "summoning_rituals", "summon_smelter_t1"), this.entryLink("Foliot Crystallizer", "summoning_rituals", "summon_crystallizer_t1"), this.entryLink("Foliot Lumberjack", "summoning_rituals", "summon_lumberjack"), this.entryLink("Foliot Farmer", "summoning_rituals", "summon_farmer"), this.entryLink("Foliot Transporter", "summoning_rituals", "summon_transport_items"), this.entryLink("Foliot Janitor", "summoning_rituals", "summon_cleaner"), this.entryLink("Otherstone Trader", "summoning_rituals", "summon_otherstone_trader"), this.entryLink("Otherrock Trader", "summoning_rituals", "summon_otherrock_trader"), this.entryLink("Otherworld Sapling Trader", "summoning_rituals", "summon_otherworld_sapling_trader"));

    }

    @Override
    protected String entryName() {
        return "Aviar's Circle";
    }

    @Override
    protected String entryDescription() {
        return "Foliot Summoning";
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
