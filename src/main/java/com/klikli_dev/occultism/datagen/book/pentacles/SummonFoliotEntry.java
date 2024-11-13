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
                - [Foliot Crusher](entry://summoning_rituals/summon_crusher_t1)
                - [Foliot Smelter](entry://summoning_rituals/summon_smelter_t1)
                - [Foliot Lumberjack](entry://summoning_rituals/summon_lumberjack)
                - [Foliot Transporter](entry://summoning_rituals/summon_transport_items)
                - [Foliot Janitor](entry://summoning_rituals/summon_cleaner)
                - [Otherstone Trader](entry://summoning_rituals/summon_otherstone_trader)
                - [Otherworld Sapling Trader](entry://summoning_rituals/summon_otherworld_sapling_trader)
                """
        );

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
