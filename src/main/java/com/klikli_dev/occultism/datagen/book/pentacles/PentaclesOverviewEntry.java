package com.klikli_dev.occultism.datagen.book.pentacles;

import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.EntryBackground;
import com.klikli_dev.modonomicon.api.datagen.EntryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookTextPageModel;
import com.klikli_dev.occultism.registry.OccultismBlocks;
import com.mojang.datafixers.util.Pair;
import net.minecraft.ChatFormatting;

public class PentaclesOverviewEntry extends EntryProvider {

    public static final String ENTRY_ID = "pentacle_overview";


    public PentaclesOverviewEntry(CategoryProvider parent) {
        super(parent);
    }

    @Override
    protected void generatePages() {

        this.page("intro1", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText()));
        this.pageTitle("Overview");
        this.pageText("""
                        The name {0} in this context refers to ritual drawings of any shape, not just five-pointed stars. \\
                        \\
                        Pentacles are used to summon and bind spirits from {1}.
                        """,
                this.color("Pentacle", ChatFormatting.DARK_PURPLE),
                this.color("The Other Place", ChatFormatting.DARK_PURPLE)
        );

        this.page("intro2", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText()));
        this.pageTitle("");
        this.pageText("""
                        Each pentacle consists of a central golden sacrificial bowl, surrounding runes of various colors
                         and occult paraphernalia that improve the intended effect in various ways.\\
                        \\
                        They act both as a device to call on the entity, an amplifier for the summoner's commanding power
                         and as a protecting circle preventing attacks from within against the summoner.
                        """
        );

        this.page("intro3", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText()));
        this.pageTitle("");
        this.pageText("""
                        The combination of chosen runes and supporting items as well as their exact spatial positioning
                         determines the use and effectiveness of the pentacle.
                        """
        );

        this.page("intro4", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText()));
        this.pageTitle("");
        this.pageText("""
                        Ingredients are placed in [Sacrificial Bowls](item://occultism:sacrificial_bowl)
                         near the pentacle. Specifically, must be placed **anywhere** within
                         8 blocks horizontally of the central [](item://occultism:golden_sacrificial_bowl).
                         The exact location does not matter.
                        """
        );

    }

    @Override
    protected String entryName() {
        return "On Pentacles";
    }

    @Override
    protected String entryDescription() {
        return "Lets Draw";
    }

    @Override
    protected Pair<Integer, Integer> entryBackground() {
        return EntryBackground.CATEGORY_START;
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(this.modLoc("textures/gui/book/pentacle.png"));
    }

    @Override
    protected String entryId() {
        return ENTRY_ID;
    }
}
