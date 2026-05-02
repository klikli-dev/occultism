package com.klikli_dev.occultism.datagen.book.getting_started;

import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.EntryBackground;
import com.klikli_dev.modonomicon.api.datagen.EntryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookTextPageModel;
import com.klikli_dev.modonomicon.client.gui.book.theme.GuiSprite;
import net.minecraft.ChatFormatting;
import net.minecraft.world.item.Items;

public class BookshelfBindingEntry extends EntryProvider {

    public static final String ENTRY_ID = "bookshelf_binding";


    public BookshelfBindingEntry(CategoryProvider parent) {
        super(parent);
    }

    @Override
    protected void generatePages() {

        this.page("info", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText()));
        this.pageTitle("Five Star Bound Method");
        this.pageText("""
                        Crafting the bound books of binding is so boring? \\
                        Use the Bookshelf Binding! 
                        Make up to **SIX** bindings at the same time, with in-world interaction, no more the common shapeless recipe. \\
                        \\
                        Put the books in a [](item://minecraft:chiseled_bookshelf) and {0} with your {1}.
                        """,
                this.color("Shift + Right Click", ChatFormatting.DARK_PURPLE),
                this.color("Dictionary of Spirits", ChatFormatting.DARK_GREEN)

        );

        this.page("more", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText()));
        this.pageTitle("Dyeing the empties");
        this.pageText("""
                You can also bind [](item://occultism:book_of_binding_empty) directly, just hold 4 dyes for each in your off-hand. \\
                \\
                The spirit to be bound depends on the dye held:\\
                Blue    ->  Foliot\\
                Purple  ->  Djinni\\
                Yellow  ->  Afrit\\
                Green   ->  Marid
                """
        );

        this.page("automation", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText()));
        this.pageTitle("Automation");
        this.pageText("""
                        You can automate this process placing a sacrificial bowl with {0} above the [](item://minecraft:chiseled_bookshelf).
                        When the bowl receive a redstone signal, the books inside will be bounded.\\
                        \\
                        Note: Also work with copper and silver versions of the sacrificial bowl.
                        """,
                this.color("Dictionary of Spirits", ChatFormatting.DARK_GREEN)
        );

    }

    @Override
    protected String entryName() {
        return "Bookshelf Binding";
    }

    @Override
    protected String entryDescription() {
        return "Bound your books with chiseled bookshelf";
    }

    @Override
    protected GuiSprite entryBackground() {
        return EntryBackground.DEFAULT;
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(Items.CHISELED_BOOKSHELF);
    }

    @Override
    protected String entryId() {
        return ENTRY_ID;
    }
}
