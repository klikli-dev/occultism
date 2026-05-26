package com.klikli_dev.occultism.datagen.book.possession_rituals;

import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import net.minecraft.world.item.Items;

public class PossessStrongBreezeEntry extends AbstractPossessionEntry {

    public static final String ENTRY_ID = "possess_strong_breeze";

    public PossessStrongBreezeEntry(CategoryProvider parent) {
        super(parent);
    }

    @Override
    protected void generatePages() {
        this.entityPage("entity", "occultism:possessed_strong_breeze", 1.0f);
        this.pageText("""
                  **Drops**: 1x [](item://minecraft:heavy_core) and can drop other things (See next page);
                """);

        this.ritualPage("ritual", "ritual/wild_strong_breeze");

        this.textPageNoTitle("description");
        this.pageText("""
                The Wild Strong Breeze is 'Flow-Forged', granting a powerful version of the regular Breeze. This is the final target to obtain a [](item://minecraft:heavy_core) and as a bonus, you can get: [](item://minecraft:flow_armor_trim_smithing_template), [](item://minecraft:flow_banner_pattern), [](item://minecraft:flow_pottery_sherd) and [](item://minecraft:music_disc_creator).
                """);
    }

    @Override
    protected String entryName() {
        return "Glorious Vault";
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(Items.HEAVY_CORE);
    }

    @Override
    protected String entryId() {
        return ENTRY_ID;
    }
}
