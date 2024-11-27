package com.klikli_dev.occultism.datagen.book.binding_rituals;

import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.EntryBackground;
import com.klikli_dev.modonomicon.api.datagen.EntryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookSpotlightPageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookTextPageModel;
import com.klikli_dev.occultism.integration.modonomicon.pages.BookRitualRecipePageModel;
import com.klikli_dev.occultism.registry.OccultismItems;
import com.mojang.datafixers.util.Pair;
import net.minecraft.ChatFormatting;
import net.minecraft.world.item.crafting.Ingredient;

public class ApprenticeRitualSatchelEntry extends EntryProvider {

    public static final String ENTRY_ID = "apprentice_ritual_satchel";


    public ApprenticeRitualSatchelEntry(CategoryProvider parent) {
        super(parent);
    }

    @Override
    protected void generatePages() {

        this.page("spotlight", () -> BookSpotlightPageModel.create()
                .withItem(Ingredient.of(OccultismItems.RITUAL_SATCHEL_T1.get()))
                .withText(this.context().pageText()));
        this.pageText("""
                         At first glance the satchel appears to be a normal bag, of moderate size and storage capacity. However, closer inspection reveals that a {0} is bound to the satchel, tasked with assisting the summoner in drawing pentacles for their rituals.
                        """,
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
                The satchel allows to speed up setting pentacles by automatically choosing the right items from the satchel, instead of manually selecting chalks, candles and other items to place them.
                """);

        this.page("crafting", () -> BookRitualRecipePageModel.create()
                .withRecipeId1(this.modLoc("ritual/craft_ritual_satchel_t1"))
        );
        //no text

        this.page("usage", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText()));
        this.pageTitle("Usage");
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
    }

    @Override
    protected String entryName() {
        return "Apprentice Ritual Satchel";
    }

    @Override
    protected String entryDescription() {
        return "Tired of so many chalks and paraphernalia? The Ritual Satchel is the solution!";
    }

    @Override
    protected Pair<Integer, Integer> entryBackground() {
        return EntryBackground.DEFAULT;
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(OccultismItems.RITUAL_SATCHEL_T1.get());
    }

    @Override
    protected String entryId() {
        return ENTRY_ID;
    }
}
