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

public class CleanerEntry extends EntryProvider {

    public static final String ENTRY_ID = "summon_cleaner";

    public CleanerEntry(CategoryProvider parent) {
        super(parent);
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(OccultismItems.BRUSH);
    }

    @Override
    protected String entryName() {
        return "Summon Foliot Janitor";
    }

    @Override
    protected String entryDescription() {
        return "Clearing around";
    }

    @Override
    protected void generatePages() {
        this.page("intro", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText()));
        this.pageTitle("Foliot Janitor");
        this.pageText("""
                The janitor will pick up dropped items and deposit them into a target
                inventory. You can configure an allow/block list to specify which
                items to pick up or ignore. **Warning**: By default it is set to
                "allow" mode, so it will only pick up items you specify in the
                allow list. You can use tags to handle whole groups of items.
                """
        );

        this.page("intro2", () -> BookTextPageModel.create()
                .withText(this.context().pageText()));
        this.pageText("""
                To bind the janitor to an inventory simply sneak and interact
                with the janitor book of calling on that inventory. You can
                also interact with a block while holding the janitor book of
                calling to have it deposit items there. You can also have it
                wander around a select area by pulling up that interface.
                To configure an allow/block list sneak and interact with the janitor.
                """
        );

        this.page("tip", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText()));
        this.pageTitle("Pro tip");
        this.pageText("""
                        The Janitor will pick up crushed items from a {0}, {1}, {2}
                        spirits and deposit them into a chest.
                        \\
                        \\
                        Combine that with a {3} to automate the whole process.
                        """,
                this.entryLink("Crusher", "summoning_rituals", "summon_crusher_t1"),
                this.entryLink("Smelter", "summoning_rituals", "summon_smelter_t1"),
                this.entryLink("Crystallizer", "summoning_rituals", "summon_crystallizer_t1"),
                this.entryLink("Transporter Spirit", "summoning_rituals", "summon_transport_items")
        );

        this.page("ritual", () -> BookRitualRecipePageModel.create()
                .withRecipeId1(this.modLoc("ritual/summon_foliot_cleaner")));
        //no text

        this.page("book_of_calling", () -> BookCraftingRecipePageModel.create()
                .withRecipeId1(this.modLoc("crafting/book_of_calling_foliot_cleaner"))
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
                         Use the book of calling to set the work area and deposit location of the janitor.
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
