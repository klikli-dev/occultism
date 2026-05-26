package com.klikli_dev.occultism.datagen.book.possession_rituals;

import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import net.minecraft.world.item.Items;

public class PossessEndermanEntry extends AbstractPossessionEntry {

    public static final String ENTRY_ID = "possess_enderman";

    public PossessEndermanEntry(CategoryProvider parent) {
        super(parent);
    }

    @Override
    protected void generatePages() {
        this.entityPage("entity", "occultism:possessed_enderman");
        this.pageText("""
                **Drops**: 1-3x [](item://minecraft:ender_pearl)
                and as 10%% chance to drop a [](item://minecraft:eye_armor_trim_smithing_template)
                """);

        this.ritualPage("ritual", "ritual/possess_enderman");

        this.textPage("description");
        this.pageText("""
                In this ritual an [#]({0})Enderman[#]() is spawned using the life energy of a [#]({0})Pig[#]() and immediately possessed by the summoned [#]({0})Djinni[#](). The [#]({0})Possessed Enderman[#]() will always drop at least one [](item://minecraft:ender_pearl) when killed.
                """, COLOR_PURPLE);
    }

    @Override
    protected String entryName() {
        return "Possessed Enderman";
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(Items.ENDER_PEARL);
    }

    @Override
    protected String entryId() {
        return ENTRY_ID;
    }
}
