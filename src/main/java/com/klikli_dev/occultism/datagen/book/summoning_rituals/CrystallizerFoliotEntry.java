package com.klikli_dev.occultism.datagen.book.summoning_rituals;

import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.EntryBackground;
import com.klikli_dev.modonomicon.api.datagen.EntryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookTextPageModel;
import com.klikli_dev.modonomicon.client.gui.book.theme.GuiSprite;
import com.klikli_dev.occultism.integration.modonomicon.pages.BookRitualRecipePageModel;
import com.klikli_dev.occultism.registry.OccultismItems;
import net.minecraft.world.item.Items;

public class CrystallizerFoliotEntry extends EntryProvider {

    public static final String ENTRY_ID = "summon_crystallizer_t1";

    public CrystallizerFoliotEntry(CategoryProvider parent) {
        super(parent);
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(Items.LAPIS_LAZULI);
    }

    @Override
    protected String entryName() {
        return "Summon Foliot Crystallizer";
    }

    @Override
    protected String entryDescription() {
        return "Glassy";
    }

    @Override
    protected void generatePages() {
        this.page("about_crystallizers", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText()));
        this.pageTitle("Crystallizer Spirits");
        this.pageText("""
                Crystallizer spirits are summoned to regenerate gem from their dusts and directly multiply
                the output of breaking gem ores. They will pick up appropriate items and drop the resulting into
                the world. A magical particle effect and a amethyst sound indicate the crystallizer is at work.
                """
        );

        this.page("automation", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText()));
        this.pageTitle("Automation");
        this.pageText("""
                        To ease automation, try summoning a {0} to place items from chests in the
                         crystallizer''s inventory, and a {1} to collect the processed items.
                        """,
                this.entryLink("Transporter Spirit", "summoning_rituals", "summon_transport_items"),
                this.entryLink("Janitor Spirit", "summoning_rituals", "summon_cleaner")
        );

        this.page("intro", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText()));
        this.pageTitle("Foliot Crystallizer");
        this.pageText("""
                The foliot crystallizer is the most basic crystallizer spirit.
                \\
                \\
                It will crystallize in a very low speed.
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
                .withRecipeId1(this.modLoc("ritual/summon_foliot_crystallizer")));
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
