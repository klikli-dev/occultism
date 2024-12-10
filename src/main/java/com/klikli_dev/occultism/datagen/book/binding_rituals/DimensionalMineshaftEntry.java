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

public class DimensionalMineshaftEntry extends EntryProvider {

    public static final String ENTRY_ID = "craft_dimensional_mineshaft";


    public DimensionalMineshaftEntry(CategoryProvider parent) {
        super(parent);
    }

    @Override
    protected void generatePages() {
        this.page("spotlight", () -> BookSpotlightPageModel.create()
                .withItem(Ingredient.of(OccultismBlocks.DIMENSIONAL_MINESHAFT.asItem()))
                .withText(this.context().pageText()));
        this.pageText("""
                         The dimensional mineshaft houses a {0} which opens up a stable connection into an
                          uninhabited dimension, perfectly suited for mining. While the portal is too small
                           to transfer humans, other spirits can use it to enter the mining dimension and bring back resources.

                        """,
                this.color("Djinni", ChatFormatting.DARK_PURPLE)
        );

        this.page("ritual", () -> BookRitualRecipePageModel.create()
                .withRecipeId1(this.modLoc("ritual/craft_dimensional_mineshaft"))
        );
        //no text

        this.page("description", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText()));
        this.pageTitle("Operation");
        this.pageText("""
                        The dimensional mineshaft will discard any items it cannot store, so it is important
                        to regularly empty the mineshaft, either manually, with hoppers or using a transporter spirit.
                         Spirits in lamps can be **inserted** from the top, all other sides can be used to **extract** items.
                        """
        );

        this.page("redstone", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText()));
        this.pageTitle("Redstone");
        this.pageText("""
                        The dimensional mineshaft has two interactions with redstone:
                        1. The spirit will stop working when receives a redstone signal;
                        2. A comparator can be used to extract a signal based on occupied slots and lamp durability.
                         Tip, if the comparator sends a power of 10, it is better to stop the operations.
                       """
        );

        this.page("config", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText()));
        this.pageTitle("Config");
        this.pageText("""
                       If you want to save your miners before they break, check "Server Configuration > Items".
                        By setting the "Save miners before breaking" option to "on", a miner will go to the output 
                        of the dimensional mineshaft when it reaches 1 durability.
                       """
        );
    }

    @Override
    protected String entryName() {
        return "Dimensional Mineshaft";
    }

    @Override
    protected String entryDescription() {
        return "Void mining";
    }

    @Override
    protected Pair<Integer, Integer> entryBackground() {
        return EntryBackground.DEFAULT;
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(OccultismBlocks.DIMENSIONAL_MINESHAFT.asItem());
    }

    @Override
    protected String entryId() {
        return ENTRY_ID;
    }
}
