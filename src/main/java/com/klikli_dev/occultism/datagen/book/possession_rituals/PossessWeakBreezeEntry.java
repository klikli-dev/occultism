package com.klikli_dev.occultism.datagen.book.possession_rituals;

import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import net.minecraft.world.item.Items;

public class PossessWeakBreezeEntry extends AbstractPossessionEntry {

    public static final String ENTRY_ID = "possess_weak_breeze";

    public PossessWeakBreezeEntry(CategoryProvider parent) {
        super(parent);
    }

    @Override
    protected void generatePages() {
        this.entityPage("entity", "occultism:possessed_weak_breeze", 0.5f);
        this.pageText("""
                  **Drops**: 1x [](item://minecraft:trial_key) and can drop other things (See next page);
                """);

        this.ritualPage("ritual", "ritual/wild_weak_breeze");

        this.textPageNoTitle("description");
        this.pageText("""
                [](item://minecraft:breeze_rod) cannot be obtained from Wild Weak Breeze rods due to their fragile nature, but this version of Breeze hides some treasures and has a chance to drop: [](item://minecraft:guster_pottery_sherd), [](item://minecraft:scrape_pottery_sherd), [](item://minecraft:music_disc_creator_music_box) and [](item://minecraft:ominous_bottle).
                """);
    }

    @Override
    protected String entryName() {
        return "The first key";
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(Items.TRIAL_KEY);
    }

    @Override
    protected String entryId() {
        return ENTRY_ID;
    }
}
