package com.klikli_dev.occultism.datagen.book.summoning_rituals;

import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.EntryBackground;
import com.klikli_dev.modonomicon.api.datagen.EntryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookTextPageModel;
import com.klikli_dev.modonomicon.client.gui.book.theme.GuiSprite;
import com.klikli_dev.occultism.integration.modonomicon.pages.BookRitualRecipePageModel;
import com.klikli_dev.occultism.registry.OccultismItems;

public class SmelterMaridEntry extends EntryProvider {

    public static final String ENTRY_ID = "summon_smelter_t4";

    public SmelterMaridEntry(CategoryProvider parent) {
        super(parent);
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(OccultismItems.IESNIUM_INGOT);
    }

    @Override
    protected String entryName() {
        return "Summon Marid Smelter";
    }

    @Override
    protected String entryDescription() {
        return "Overcooked";
    }

    @Override
    protected void generatePages() {
        this.page("intro", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText()));
        this.pageTitle("Marid Smelter");
        this.pageText("""
                The marid smelter is extremely faster, doing the process in one percent of the time.
                """
        );

        this.page("filters", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText()));
        this.pageTitle("Filters");
        this.pageText("""
                This spirit already only picks up items it has recipes for.
                Put a {0} or {1} into its filter slot to further restrict which valid inputs it will take.
                See {2} for filter details.
                """,
                this.itemLink(OccultismItems.LIST_FILTER),
                this.itemLink(OccultismItems.ATTRIBUTE_FILTER),
                this.entryLink("Spirit Filters", "summoning_rituals", TransporterFiltersEntry.ENTRY_ID)
        );

        this.page("ritual", () -> BookRitualRecipePageModel.create()
                .withRecipeId1(this.modLoc("ritual/summon_marid_smelter")));
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
