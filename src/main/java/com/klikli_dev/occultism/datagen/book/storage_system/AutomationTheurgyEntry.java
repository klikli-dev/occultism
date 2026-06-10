package com.klikli_dev.occultism.datagen.book.storage_system;

import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.EntryBackground;
import com.klikli_dev.modonomicon.api.datagen.EntryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookTextPageModel;
import com.klikli_dev.modonomicon.client.gui.book.theme.GuiSprite;
import com.klikli_dev.theurgy.registry.ItemRegistry;

public class AutomationTheurgyEntry extends EntryProvider {

    public static final String ENTRY_ID = "storage_system_automation_theurgy";

    public AutomationTheurgyEntry(CategoryProvider parent) {
        super(parent);
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(ItemRegistry.LIST_FILTER.get()); // placeholder for ItemRegistry.LIST_FILTER.get()
    }

    @Override
    protected String entryName() {
        return "Theurgy Storage Integration";
    }

    @Override
    protected String entryDescription() {
        return "Inserting and extracting items from the Storage Actuator using Theurgy Logistics";
    }

    @Override
    protected void generatePages() {
        this.page("intro", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText()));
        this.pageTitle("Theurgy Storage Integration");
        this.pageText("""
                Much like transporter Spirits, Theurgy Mercurial Logistics systems
                 are optimized to work with the Storage Actuator and Stable Wormholes.
                """
        );

        this.page("extraction", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText()));
        this.pageTitle("Extracting Items");
        this.pageText("""
                Once again, item extraction is the critical issue for performance.
                \\
                \\
                To make use of the performance optimization, use a [](item://theurgy:logistics_item_extractor)
                 and apply a [](item://theurgy:list_filter) to extract the desired items.
                \\
                \\
                The Theurgy Guidebook "The Hermetica" has a chapter on Theurgy
                 mercurial logistics and how to use them to insert and extract items.
                """
        );
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
