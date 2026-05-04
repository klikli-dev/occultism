package com.klikli_dev.occultism.datagen.book.summoning_rituals;

import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.EntryBackground;
import com.klikli_dev.modonomicon.api.datagen.EntryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookCraftingRecipePageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookTextPageModel;
import com.klikli_dev.modonomicon.client.gui.book.theme.GuiSprite;
import com.klikli_dev.occultism.integration.modonomicon.pages.BookRitualRecipePageModel;
import com.klikli_dev.occultism.registry.OccultismItems;
import net.minecraft.ChatFormatting;
import net.minecraft.world.item.Items;

public class TransporterEntry extends EntryProvider {

    public static final String ENTRY_ID = "summon_transport_items";

    public TransporterEntry(CategoryProvider parent) {
        super(parent);
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(Items.CHEST_MINECART);
    }

    @Override
    protected String entryName() {
        return "Summon Foliot Transporter";
    }

    @Override
    protected String entryDescription() {
        return "Moving around";
    }

    @Override
    protected void generatePages() {
        this.page("intro", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText()));
        this.pageTitle("Foliot Transporter");
        this.pageText("""
                The transporter is useful in that you don't need a train of hoppers
                transporting stuff, and can use any inventory to take from and deposit.
                \\
                \\
                To make it take from an inventory simply sneak and interact
                with it's book of calling on the inventory you want.
                """
        );

        this.page("intro2", () -> BookTextPageModel.create()
                .withText(this.context().pageText()));
        this.pageText("""
                You can also dictate which inventory it deposits to in the same way.
                \\
                The transporter will move all items it can access from one inventory to another,
                 including machines. It can also deposit into the inventories of other spirits.
                 By setting the extract and insert side they can be used to automate various transport tasks.
                """
        );

        this.page("spirit_inventories", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText()));
        this.pageTitle("Spirit Inventories");
        this.pageText("""
                        The Transporter can also interact with the inventories of other spirits.
                        This is especially useful to automatically supply a {0} with items to crush,
                        a {1} with items to smelt or a {2} with items to crystallize.
                        """,
                this.entryLink("Crusher spirit", "summoning_rituals", "summon_crusher_t1"),
                this.entryLink("Smelter spirit", "summoning_rituals", "summon_smelter_t1"),
                this.entryLink("Crystallizer spirit", "summoning_rituals", "summon_crystallizer_t1")
        );

        this.page("item_filters", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText()));
        this.pageTitle("Item Filters");
        this.pageText("""
                Shift-click the transporter to open the config UI.
                The filter slot accepts a {0} or {1}, which control what the transporter extracts or inserts.
                \
                \
                A transporter without a filter will move any items it can reach. See {2} for details and recipes.
                """
                ,
                this.itemLink(OccultismItems.LIST_FILTER),
                this.itemLink(OccultismItems.ATTRIBUTE_FILTER),
                this.entryLink("Spirit Filters", "summoning_rituals", TransporterFiltersEntry.ENTRY_ID)
        );

        this.page("ritual", () -> BookRitualRecipePageModel.create()
                .withRecipeId1(this.modLoc("ritual/summon_foliot_transport_items")));
        //no text

        this.page("book_of_calling", () -> BookCraftingRecipePageModel.create()
                .withRecipeId1(this.modLoc("crafting/book_of_calling_foliot_transport_items"))
                .withText(this.context().pageText()));
        this.pageText("""
                        If you lose the book of calling, you can craft a new one.
                        {0} the spirit with the crafted book to assign it.
                        """,
                this.color("Shift-right-click", ChatFormatting.GREEN)
        );

        this.page("usage", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText()));
        this.pageTitle("Usage");
        this.pageText("""
                         Use the book of calling to set the extract and insert location of the transporter.
                         \\
                         \\
                         See {0} for more information.
                        """,
                this.entryLink("Books of Calling", "getting_started", "books_of_calling")
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
