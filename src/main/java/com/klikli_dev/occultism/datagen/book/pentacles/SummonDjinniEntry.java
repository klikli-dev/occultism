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

public class SummonDjinniEntry extends EntryProvider {

    public static final String ENTRY_ID = "summon_djinni";


    public SummonDjinniEntry(CategoryProvider parent) {
        super(parent);
    }

    @Override
    protected void generatePages() {

        this.page("intro", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText())
        );
        this.pageTitle("Ophyx Calling");
        this.pageText("""
                        **Purpose:** Summon a {0}\\
                        \\
                        Developed by {1} during the Third Era, the {2} is the go-to pentacle for {3} summonings ever since.
                         Skeleton skulls ({5})
                         and {4} provide the calling power required to force Djinni into appearance and candles stabilize the ritual.
                        
                        """, this.color("Djinni", ChatFormatting.DARK_PURPLE),
                this.color("Ophyx", ChatFormatting.LIGHT_PURPLE),
                this.color("Calling", ChatFormatting.LIGHT_PURPLE),
                this.color("Djinni", ChatFormatting.DARK_PURPLE),
                this.color("Lime Chalk", ChatFormatting.DARK_PURPLE),
                this.entryLink("Obtain here", "possession_rituals", "possess_skeleton"));

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
                """, this.entryLink("Djinni Crusher", "summoning_rituals", "summon_crusher_t2"), this.entryLink("Djinni Smelter", "summoning_rituals", "summon_smelter_t2"), this.entryLink("Djinni Crystallizer", "summoning_rituals", "summon_crystallizer_t2"), this.entryLink("Djinni Machine Operator", "summoning_rituals", "summon_manage_machine"), this.entryLink("Gem Gambler", "summoning_rituals", "summon_gambler"), this.entryLink("Wondering Trader", "summoning_rituals", "summon_wondering"), this.entryLink("Clear Weather", "summoning_rituals", "weather_magic@clear"), this.entryLink("Time Magic", "summoning_rituals", "time_magic"), this.entryLink("Demonic Partner", "familiar_rituals", "demonic_partner"));

    }

    @Override
    protected String entryName() {
        return "Ophyx' Calling";
    }

    @Override
    protected String entryDescription() {
        return "Djinni Summoning";
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
