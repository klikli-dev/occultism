package com.klikli_dev.occultism.datagen.book.summoning_rituals;

import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.EntryBackground;
import com.klikli_dev.modonomicon.api.datagen.EntryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookCraftingRecipePageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookTextPageModel;
import com.klikli_dev.modonomicon.client.gui.book.theme.GuiSprite;
import com.klikli_dev.occultism.integration.modonomicon.pages.BookRitualRecipePageModel;
import net.minecraft.ChatFormatting;
import net.minecraft.world.item.Items;

public class ManageMachineEntry extends EntryProvider {

    public static final String ENTRY_ID = "summon_manage_machine";

    public ManageMachineEntry(CategoryProvider parent) {
        super(parent);
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(Items.LEVER);
    }

    @Override
    protected String entryName() {
        return "Summon Djinni Machine Operator";
    }

    @Override
    protected String entryDescription() {
        return "Processing on-demand";
    }

    @Override
    protected void generatePages() {
        this.page("intro", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText()));
        this.pageTitle("Djinni Machine Operator");
        this.pageText("""
                The machine operator transfers items specified in the dimensional
                 storage actuator GUI, to it's managed machine, and returns crafting
                 results to the storage system. It can also be used to automatically
                 empty a chest into the storage actuator.
                 \\
                 Basically, on-demand crafting!
                """
        );

        this.page("tutorial", () -> BookTextPageModel.create()
                .withText(this.context().pageText()));
        this.pageText("""
                 To use the machine operator use the book of calling to link a Storage
                  Actuator, the machine and optionally a separate extract location
                  (the face you click on will be extracted from!). For the machine
                  you can additionally set a custom name and the insert/extract facings.
                """
        );

        this.page("tutorial2", () -> BookTextPageModel.create()
                .withText(this.context().pageText()));
        this.pageText("""
                Please note that setting a new machine (or configuring it with
                 the book of calling) will reset the extraction settings.
                 \\
                 \\
                 For an easy start, make sure to view the short
                 [Tutorial Video](https://gyazo.com/237227ba3775e143463b31bdb1b06f50)!
                """
        );

        this.page("ritual", () -> BookRitualRecipePageModel.create()
                .withRecipeId1(this.modLoc("ritual/summon_djinni_manage_machine")));
        //no text

        this.page("book_of_calling", () -> BookCraftingRecipePageModel.create()
                .withRecipeId1(this.modLoc("crafting/book_of_calling_djinni_manage_machine"))
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
                         Use the book of calling to set the actuator, managed machine and extract locations of the machine operator.
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
