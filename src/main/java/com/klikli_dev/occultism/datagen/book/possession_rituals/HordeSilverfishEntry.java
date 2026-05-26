package com.klikli_dev.occultism.datagen.book.possession_rituals;

import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import net.minecraft.world.item.Items;

public class HordeSilverfishEntry extends AbstractPossessionEntry {

    public static final String ENTRY_ID = "horde_silverfish";

    public HordeSilverfishEntry(CategoryProvider parent) {
        super(parent);
    }

    @Override
    protected void generatePages() {
        this.entityPage("entity", "occultism:wild_horde_silverfish", 1f);
        this.pageText("""
                **Drops**: Items related to ruins trials (See next page);
                """);

        this.ritualPage("ritual", "ritual/wild_silverfish");

        this.textPageNoTitle("description");
        this.pageText("""
                Silverfish summoned by this way can drop: [](item://minecraft:music_disc_relic), [](item://minecraft:host_armor_trim_smithing_template), [](item://minecraft:raiser_armor_trim_smithing_template), [](item://minecraft:shaper_armor_trim_smithing_template), [](item://minecraft:wayfinder_armor_trim_smithing_template), [](item://minecraft:burn_pottery_sherd), [](item://minecraft:danger_pottery_sherd), [](item://minecraft:friend_pottery_sherd), [](item://minecraft:heart_pottery_sherd), [](item://minecraft:heartbreak_pottery_sherd), [](item://minecraft:howl_pottery_sherd), [](item://minecraft:sheaf_pottery_sherd).
                """);
    }

    @Override
    protected String entryName() {
        return "Wild Horde Silverfish";
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(Items.MUSIC_DISC_RELIC);
    }

    @Override
    protected String entryId() {
        return ENTRY_ID;
    }
}
