package com.klikli_dev.occultism.datagen.book.summoning_rituals;

import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.EntryBackground;
import com.klikli_dev.modonomicon.api.datagen.EntryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookTextPageModel;
import com.klikli_dev.occultism.integration.modonomicon.pages.BookRitualRecipePageModel;
import com.klikli_dev.occultism.registry.OccultismItems;
import com.mojang.datafixers.util.Pair;
import net.minecraft.world.item.Items;

public class MagicTimeEntry extends EntryProvider {

    public static final String ENTRY_ID = "time_magic";

    public MagicTimeEntry(CategoryProvider parent) {
        super(parent);
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(Items.CLOCK);
    }

    @Override
    protected String entryName() {
        return "Time Magic";
    }

    @Override
    protected String entryDescription() {
        return "Sadly, it's not time in a bottle, a temporal pouch, or any other such device.";
    }

    @Override
    protected void generatePages() {
        this.page("intro", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText()));
        this.pageTitle("Time Magic");
        this.pageText("""
                        Time magic is limited in scope, it cannot send the magician back
                         or forth in time, but rather allows to change the time of day.
                         This is especially useful for rituals or other tasks requiring day
                         or nighttime specifically.
                        \\
                        \\
                        Time spirits will only modify the time once and then vanish.
                """
        );

        this.page("ritual_day", () -> BookRitualRecipePageModel.create()
                    .withRecipeId1(this.modLoc("ritual/summon_djinni_day_time"))
                    .withAnchor("day"));
        //no text

        this.page("ritual_night", () -> BookRitualRecipePageModel.create()
                    .withRecipeId1(this.modLoc("ritual/summon_djinni_night_time"))
                    .withAnchor("night"));
        //no text

    }

    @Override
    protected Pair<Integer, Integer> entryBackground() {
        return EntryBackground.DEFAULT;
    }

    @Override
    protected String entryId() {
        return ENTRY_ID;
    }
}
