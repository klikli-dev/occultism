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

public class SmelterFoliotEntry extends EntryProvider {

    public static final String ENTRY_ID = "summon_smelter_t1";

    public SmelterFoliotEntry(CategoryProvider parent) {
        super(parent);
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(Items.COPPER_INGOT);
    }

    @Override
    protected String entryName() {
        return "Summon Foliot Smelter";
    }

    @Override
    protected String entryDescription() {
        return "BURN";
    }

    @Override
    protected void generatePages() {
        this.page("about_smelters", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText()));
        this.pageTitle("Smelter Spirits");
        this.pageText("""
                Smelter spirits are summoned to do furnace, blast furnace, smoker and campfire process,
                without using fuel. They will pick up appropriate items and drop the resulting into the world.
                A fire particle effect and a flame sound indicate the smelter is at work.
                """
        );

        this.page("automation", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText()));
        this.pageTitle("Automation");
        this.pageText("""
                        To ease automation, try summoning a {0} to place items from chests
                         in the smelter''s inventory, and a {1} to collect the processed items.
                        """,
                this.entryLink("Transporter Spirit", "summoning_rituals", "summon_transport_items"),
                this.entryLink("Janitor Spirit", "summoning_rituals", "summon_cleaner")
        );

        this.page("intro", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText()));
        this.pageTitle("Foliot Smelter");
        this.pageText("""
                The foliot smelter is the most basic smelter spirit.
                \\
                \\
                It will smelt an item at the same speed as the furnace.
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
                .withRecipeId1(this.modLoc("ritual/summon_foliot_smelter")));
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
