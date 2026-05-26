package com.klikli_dev.occultism.datagen.book.possession_rituals;

import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import net.minecraft.world.item.Items;

public class PossessHoglinEntry extends AbstractPossessionEntry {

    public static final String ENTRY_ID = "possess_hoglin";

    public PossessHoglinEntry(CategoryProvider parent) {
        super(parent);
    }

    @Override
    protected void generatePages() {
        this.entityPage("entity", "occultism:possessed_hoglin", 0.7f);
        this.pageText("""
                  **Drops**: Can drop: [](item://minecraft:netherite_upgrade_smithing_template),
                  return back [](item://minecraft:netherite_scrap) or other things (See next page);
                """);

        this.ritualPage("ritual", "ritual/possess_hoglin");

        this.textPageNoTitle("description");
        this.pageText("""
                In this ritual a [#]({0})Hoglin[#]() is spawned using the life energy of a [#]({0})Pig[#]() and immediately possessed by the summoned [#]({0})Afrit[#](). The [#]({0})Possessed Hoglin[#]() can drop a [](item://minecraft:netherite_upgrade_smithing_template), [](item://minecraft:snout_armor_trim_smithing_template), [](item://minecraft:music_disc_pigstep), [](item://minecraft:piglin_banner_pattern), [](item://minecraft:nether_brick) or return back [](item://minecraft:netherite_scrap). You need to kill this mob before the transformation to a Zoglin if you don't want to perform the ritual in the nether.
                """, COLOR_PURPLE);
    }

    @Override
    protected String entryName() {
        return "Possessed Hoglin";
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE);
    }

    @Override
    protected String entryId() {
        return ENTRY_ID;
    }
}
