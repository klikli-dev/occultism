package com.klikli_dev.occultism.datagen.book.binding_rituals;

import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.EntryBackground;
import com.klikli_dev.modonomicon.api.datagen.EntryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookSpotlightPageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookTextPageModel;
import com.klikli_dev.occultism.integration.modonomicon.pages.BookRitualRecipePageModel;
import com.klikli_dev.occultism.registry.OccultismBlocks;
import com.klikli_dev.occultism.registry.OccultismItems;
import com.mojang.datafixers.util.Pair;
import net.minecraft.ChatFormatting;
import net.minecraft.world.item.crafting.Ingredient;

public class ArtisanalRitualSatchelEntry extends EntryProvider {

    public static final String ENTRY_ID = "artisanal_ritual_satchel";


    public ArtisanalRitualSatchelEntry(CategoryProvider parent) {
        super(parent);
    }

    @Override
    protected void generatePages() {
        this.page("spotlight", () -> BookSpotlightPageModel.create()
                .withItem(Ingredient.of(OccultismItems.RITUAL_SATCHEL_T2.get()))
                .withText(this.context().pageText()));
        this.pageText("""
                         The artisanal satchel employs an {0} to assist the summoner in drawing pentacles for their rituals. Unlike {1} in the apprentice satchel, the bound {0} can set up an entire pentacle within a moment's notice.
                         \\
                         Moreover, the spirit can also clean up the pentacle after the ritual is complete, leaving no trace of the ritual behind.
                        """,
                this.color("Afrit", ChatFormatting.DARK_PURPLE),
                this.color("Djinni", ChatFormatting.DARK_PURPLE)
        );

        this.page("about", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText()));
        this.pageTitle("About");
        this.pageText("""
                A ritual satchel can hold items needed to create pentacles for rituals,
                 if an item inside has less than 40 percent of durability the glint effect will stop.
                 Other items cannot be placed in the satchel.
                \\
                The satchel sets up an entire pentacle at once, if all necessary items are present in the satchel.\\
                It can also remove (intact) pentacles by {0} the {1} and store all blocks back in the satchel.
                """,
                this.color("Right-Clicking", ChatFormatting.GREEN),
                this.itemLink(OccultismBlocks.GOLDEN_SACRIFICIAL_BOWL)
        );

        this.page("crafting", () -> BookRitualRecipePageModel.create()
                .withRecipeId1(this.modLoc("ritual/craft_ritual_satchel_t2"))
        );
        //no text

        this.page("usage_drawing", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText()));
        this.pageTitle("Usage (Drawing)");
        this.pageText("""
                        1. {0} to open the satchel and place chalks, candles, crystals, skulls and other items needed for rituals.
                        2. Using this book, use the "eye" icon to preview the pentacle you want to set up in the world.
                        3. {1} with this book to anchor the preview pentacle in the spot where you want to set it up.
                        4. {2} with the satchel on a previewed chalk mark or block to automatically place it.
                        5. Repeat until the pentacle is complete.
                        """,
                this.color("Shift-Right-Click", ChatFormatting.GREEN),
                this.color("Right-Click", ChatFormatting.GREEN)
        );

        this.page("usage_cleaning", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText()));
        this.pageTitle("Usage (Cleaning)");
        this.pageText("""
                        1. Find a pentacle you want to remove. The pentacle needs to be intact (ready for a ritual).
                        2. {0} the central {1} with the satchel.
                        2. The satchel will remove all chalk marks, and pick up all paraphernalia blocks, such as candles or skulls, used in the pentacle.
                        """,
                this.color("Right-Click", ChatFormatting.GREEN),
                this.itemLink(OccultismBlocks.GOLDEN_SACRIFICIAL_BOWL)
        );
    }

    @Override
    protected String entryName() {
        return "Artisanal Ritual Satchel";
    }

    @Override
    protected String entryDescription() {
        return "The Apprentice Ritual Satchel is still too slow? Let's get help from an Afrit!";
    }

    @Override
    protected Pair<Integer, Integer> entryBackground() {
        return EntryBackground.DEFAULT;
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(OccultismItems.RITUAL_SATCHEL_T2.get());
    }

    @Override
    protected String entryId() {
        return ENTRY_ID;
    }
}
