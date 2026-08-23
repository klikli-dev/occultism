package com.klikli_dev.occultism.datagen.book.binding_rituals;

import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.EntryBackground;
import com.klikli_dev.modonomicon.api.datagen.EntryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookSpotlightPageModel;
import com.klikli_dev.modonomicon.client.gui.book.theme.GuiSprite;
import com.klikli_dev.occultism.datagen.OccultismResearch;
import com.klikli_dev.occultism.datagen.book.PentaclesCategory;
import com.klikli_dev.occultism.datagen.book.pentacles.SummonAfritEntry;
import com.klikli_dev.occultism.integration.modonomicon.pages.BookRitualRecipePageModel;
import com.klikli_dev.occultism.registry.OccultismItems;
import net.minecraft.ChatFormatting;
import net.minecraft.world.item.Items;

public class RepairEntry extends EntryProvider {

    public static final String ENTRY_ID = "repair";

    public RepairEntry(CategoryProvider parent) {
        super(parent);
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(OccultismItems.REPAIR_ICON);
    }

    @Override
    protected String entryName() {
        return "Repair Rituals";
    }

    @Override
    protected String entryDescription() {
        return "Hammer time";
    }

    @Override
    protected void generatePages() {
        this.page("spotlight", () -> BookSpotlightPageModel.create()
                .withItem(Items.ANVIL)
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText()));
        this.pageTitle("Repairing");
        this.pageText("""
                        With simple materials, a {0} can repair any chalk for you.
                         By evolving in the occult path, an {1} can repair miners, tools and armors.
                         Any item repaired in this way retains its properties.
                        """,
                this.color("Djinni", ChatFormatting.DARK_PURPLE),
                this.color("Afrit", ChatFormatting.DARK_PURPLE)
        );

        this.page("ritual_chalks", () -> BookRitualRecipePageModel.create()
                .withRecipeId1(this.modLoc("ritual/repair_chalks"))
        );
        //no text
        this.page("ritual_miners", () -> BookRitualRecipePageModel.create()
                .withRecipeId1(this.modLoc("ritual/repair_miners"))
                .withCondition(this.condition().researchNodeEntryViewedOnce(OccultismResearch.PENTACLES_SUMMON_AFRIT, OccultismResearch.entryModel(PentaclesCategory.CATEGORY_ID, SummonAfritEntry.ENTRY_ID)))
        );
        //no text
        this.page("ritual_tools", () -> BookRitualRecipePageModel.create()
                .withRecipeId1(this.modLoc("ritual/repair_tools"))
                .withCondition(this.condition().researchNodeEntryViewedOnce(OccultismResearch.PENTACLES_SUMMON_AFRIT, OccultismResearch.entryModel(PentaclesCategory.CATEGORY_ID, SummonAfritEntry.ENTRY_ID)))
        );
        //no text
        this.page("ritual_armors", () -> BookRitualRecipePageModel.create()
                .withRecipeId1(this.modLoc("ritual/repair_armors"))
                .withCondition(this.condition().researchNodeEntryViewedOnce(OccultismResearch.PENTACLES_SUMMON_AFRIT, OccultismResearch.entryModel(PentaclesCategory.CATEGORY_ID, SummonAfritEntry.ENTRY_ID)))
        );
        //no text
    }

    @Override
    protected GuiSprite entryBackground() {
        return EntryBackground.DEFAULT;
    }

    @Override
    protected String entryId() {
        return ENTRY_ID;
    }
}
