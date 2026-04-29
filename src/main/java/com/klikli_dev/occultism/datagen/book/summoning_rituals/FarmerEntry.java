package com.klikli_dev.occultism.datagen.book.summoning_rituals;

import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.EntryBackground;
import com.klikli_dev.modonomicon.api.datagen.EntryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookCraftingRecipePageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookTextPageModel;
import com.klikli_dev.occultism.integration.modonomicon.pages.BookRitualRecipePageModel;
import com.klikli_dev.modonomicon.client.gui.book.theme.GuiSprite;
import net.minecraft.ChatFormatting;
import net.minecraft.world.item.Items;

public class FarmerEntry extends EntryProvider {

    public static final String ENTRY_ID = "summon_farmer";

    public FarmerEntry(CategoryProvider parent) {
        super(parent);
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(Items.IRON_HOE);
    }

    @Override
    protected String entryName() {
        return "Summon Foliot Farmer";
    }

    @Override
    protected String entryDescription() {
        return "Grandpa will return at the dawn of the 3rd year.";
    }

    @Override
    protected void generatePages() {
        this.page("intro", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText()));
        this.pageTitle("Foliot Farmer");
        this.pageText("""
                    The farmer will harvest and re-plant crops in it's working area.
                     If a deposit location is set it will collect the dropped items into the specified chest.
                    """
        );

        this.page("ritual", () -> BookRitualRecipePageModel.create()
                .withRecipeId1(this.modLoc("ritual/summon_foliot_farmer")));
        //no text

        this.page("book_of_calling", () -> BookCraftingRecipePageModel.create()
                .withRecipeId1(this.modLoc("crafting/book_of_calling_foliot_farmer"))
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
                        Use the book of calling to set the work area and deposit location of the farmer.
                        \\
                        \\
                        See {0} for more information.
                       """,
                this.entryLink("Books of Calling", "getting_started", "books_of_calling")
        );

        this.page("usage2", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText()));
        this.pageTitle("Lazy Farmer?");
        this.pageText("""
                        The spirit might pause for a few minutes after clearing his work area,
                         even if crops have mature since. This is a performance-saving
                         measure and not a bug, he will continue on his own.
                        \\
                        \\
                        Set the work area again to make him continue work immediately.
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
