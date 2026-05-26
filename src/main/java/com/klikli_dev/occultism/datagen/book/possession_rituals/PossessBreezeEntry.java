package com.klikli_dev.occultism.datagen.book.possession_rituals;

import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import net.minecraft.world.item.Items;

public class PossessBreezeEntry extends AbstractPossessionEntry {

    public static final String ENTRY_ID = "possess_breeze";

    public PossessBreezeEntry(CategoryProvider parent) {
        super(parent);
    }

    @Override
    protected void generatePages() {
        this.entityPage("entity", "occultism:possessed_breeze", 1.0f);
        this.pageText("""
                  **Drops**: 1x [](item://minecraft:ominous_trial_key) and can drop other things (See next page);
                """);

        this.ritualPage("ritual", "ritual/wild_breeze");

        this.textPageNoTitle("description");
        this.pageText("""
                The Wild Breeze has intrinsic Ominous Essence causing a drop of [](item://minecraft:ominous_trial_key). The [](item://minecraft:breeze_rod) from this enemy can survive after the battle and the extra loot is: [](item://minecraft:bolt_armor_trim_smithing_template), [](item://minecraft:guster_banner_pattern) and [](item://minecraft:music_disc_precipice).
                """);
    }

    @Override
    protected String entryName() {
        return "In the chamber";
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(Items.OMINOUS_TRIAL_KEY);
    }

    @Override
    protected String entryId() {
        return ENTRY_ID;
    }
}
