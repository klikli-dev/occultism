package com.klikli_dev.occultism.datagen.book.binding_rituals;

import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.EntryBackground;
import com.klikli_dev.modonomicon.api.datagen.EntryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookSpotlightPageModel;
import com.klikli_dev.occultism.integration.modonomicon.pages.BookRitualRecipePageModel;
import com.klikli_dev.occultism.registry.OccultismBlocks;
import com.mojang.datafixers.util.Pair;
import net.minecraft.ChatFormatting;
import net.minecraft.world.item.crafting.Ingredient;

public class EldritchChaliceEntry extends EntryProvider {

    public static final String ENTRY_ID = "craft_eldritch_chalice";


    public EldritchChaliceEntry(CategoryProvider parent) {
        super(parent);
    }

    @Override
    protected void generatePages() {
        this.page("spotlight", () -> BookSpotlightPageModel.create()
                .withItem(Ingredient.of(OccultismBlocks.ELDRITCH_CHALICE.asItem()))
                .withText(this.context().pageText()));
        this.pageText("""
                        Forging an [](item://occultism:eldritch_chalice) is one service provide by {0}, this block will
                         helps occult masters twist time, performing any ritual instantly.\\
                         All other things will works like the Golden or Iesnium Sacrificial Bowl.
                        """,
                this.color("Eldritch Spirits", ChatFormatting.DARK_PURPLE)
        );

        this.page("ritual", () -> BookRitualRecipePageModel.create()
                .withRecipeId1(this.modLoc("ritual/misc_eldritch_chalice"))
        );
        //no text
    }

    @Override
    protected String entryName() {
        return "Eldritch Chalice";
    }

    @Override
    protected String entryDescription() {
        return "Is it fast enough now?";
    }

    @Override
    protected Pair<Integer, Integer> entryBackground() {
        return EntryBackground.DEFAULT;
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(OccultismBlocks.ELDRITCH_CHALICE.asItem());
    }

    @Override
    protected String entryId() {
        return ENTRY_ID;
    }
}
