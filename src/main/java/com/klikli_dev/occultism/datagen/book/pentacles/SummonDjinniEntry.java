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
                         Skeleton skulls ([Obtain here](entry://possession_rituals/possess_skeleton))
                         and {4} provide the calling power required to force Djinni into appearance and candles stabilize the ritual.
                                               
                        """,
                this.color("Djinni", ChatFormatting.DARK_PURPLE),
                this.color("Ophyx", ChatFormatting.LIGHT_PURPLE),
                this.color("Calling", ChatFormatting.LIGHT_PURPLE),
                this.color("Djinni", ChatFormatting.DARK_PURPLE),
                this.color("Lime Chalk", ChatFormatting.DARK_PURPLE)
        );

        this.page("multiblock", () -> BookMultiblockPageModel.create().withMultiblockId(this.modLoc(ENTRY_ID)));

        this.page("uses", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText())
        );
        this.pageTitle("Uses");
        this.pageText("""
                - [Djinni Crusher](entry://summoning_rituals/summon_crusher_t2)
                - [Djinni Smelter](entry://summoning_rituals/summon_smelter_t2)
                - [Djinni Machine Operator](entry://summoning_rituals/summon_manage_machine)
                - [Clear Weather](entry://summoning_rituals/weather_magic@clear)
                - [Time Magic](entry://summoning_rituals/time_magic)
                - [Demonic Partner](entry://familiar_rituals/demonic_partner)
                """
        );

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
