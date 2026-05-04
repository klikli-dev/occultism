/*
 * SPDX-FileCopyrightText: 2026 klikli-dev
 *
 * SPDX-License-Identifier: MIT
 */

package com.klikli_dev.occultism.datagen.book.summoning_rituals;

import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.EntryBackground;
import com.klikli_dev.modonomicon.api.datagen.EntryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookCraftingRecipePageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookTextPageModel;
import com.klikli_dev.modonomicon.client.gui.book.theme.GuiSprite;
import com.klikli_dev.occultism.registry.OccultismItems;

public class TransporterFiltersEntry extends EntryProvider {

    public static final String ENTRY_ID = "transporter_filters";

    public TransporterFiltersEntry(CategoryProvider parent) {
        super(parent);
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(OccultismItems.LIST_FILTER);
    }

    @Override
    protected String entryName() {
        return "Spirit Filters";
    }

    @Override
    protected String entryDescription() {
        return "List and attribute filters";
    }

    @Override
    protected void generatePages() {
        this.page("intro", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText()));
        this.pageTitle("Spirit Filters");
        this.pageText("""
                Spirits that pick up or extract items now use a filter item instead of an internal allow/block list.
                Put either a {0} or {1} into the filter slot in the spirit UI, then configure that filter.
                \
                \
                If the filter slot is empty, transporters will move anything they can reach. Janitors still wait for a filter before picking up items.
                """,
                this.itemLink(OccultismItems.LIST_FILTER),
                this.itemLink(OccultismItems.ATTRIBUTE_FILTER)
        );

        this.page("list_filter", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText()));
        this.pageTitle("List Filters");
        this.pageText("""
                A {0} matches specific items you place into it.
                Use it when you want an exact allow/block list, and switch its mode to decide whether matching items are allowed or blocked.
                \
                \
                This is the best choice when you know the exact stacks a spirit should move.
                """,
                this.itemLink(OccultismItems.LIST_FILTER)
        );

        this.page("attribute_filter", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText()));
        this.pageTitle("Attribute Filters");
        this.pageText("""
                A {0} matches by properties instead of exact items.
                Use it for broader rules such as tags or other shared item attributes when one list would be too long.
                \
                \
                This is useful for groups like all ores, all logs, or all outputs from one mod.
                """,
                this.itemLink(OccultismItems.ATTRIBUTE_FILTER)
        );

        this.page("recipes", () -> BookCraftingRecipePageModel.create()
                .withRecipeId1(this.modLoc("crafting/list_filter"))
                .withRecipeId2(this.modLoc("crafting/attribute_filter"))
                .withText(this.context().pageText()));
        this.pageText("""
                Both filters are crafted from paper, sticks and Demon's Dream fruit.
                Keep a few around when setting up spirit automation.
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
