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

public class SummonAfritEntry extends EntryProvider {

    public static final String ENTRY_ID = "summon_afrit";


    public SummonAfritEntry(CategoryProvider parent) {
        super(parent);
    }

    @Override
    protected void generatePages() {

        this.page("intro", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText())
        );
        this.pageTitle("Abras Conjure");
        this.pageText("""
                        **Purpose:** Summon a {0}\\
                        \\
                        **Abras Conjure** is one of the few pentacles capable of (mostly) safely summoning an {1}.
                         While the requirement of several {2} makes it comparatively expensive,
                         the additional calling potential is required to reach these high-power spirits.
                        """,
                this.color("Afrit", ChatFormatting.DARK_PURPLE),
                this.color("Afrit", ChatFormatting.DARK_PURPLE),
                this.color("Spirit Attuned Gems", ChatFormatting.LIGHT_PURPLE)
        );

        this.page("multiblock", () -> BookMultiblockPageModel.create().withMultiblockId(this.modLoc(ENTRY_ID)));

        this.page("uses", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText())
        );
        this.pageTitle("Uses");
        this.pageText("""
                - [Afrit Crusher](entry://summoning_rituals/summon_crusher_t3)
                - [Afrit Smelter](entry://summoning_rituals/summon_smelter_t3)
                - [Thunderstorm](entry://summoning_rituals/weather_magic@thunder)
                - [Rainy Weather](entry://summoning_rituals/weather_magic@rain)
                """
        );

    }

    @Override
    protected String entryName() {
        return "Abras' Conjure";
    }

    @Override
    protected String entryDescription() {
        return "Afrit Summoning";
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
