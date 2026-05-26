package com.klikli_dev.occultism.datagen.book.possession_rituals;

import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import net.minecraft.world.item.Items;

public class HordeIllagerEntry extends AbstractPossessionEntry {

    public static final String ENTRY_ID = "horde_illager";

    public HordeIllagerEntry(CategoryProvider parent) {
        super(parent);
    }

    @Override
    protected void generatePages() {
        this.entityPage("entity", "occultism:possessed_evoker", 0.7f);
        this.pageText("""
                  **Drops**: [](item://minecraft:totem_of_undying)
                """);

        this.ritualPage("ritual", "ritual/wild_horde_illager");

        this.textPageNoTitle("description");
        this.pageText("""
                Summon a Wild Evoker and his henchmen to get [](item://minecraft:totem_of_undying), [](item://minecraft:vex_armor_trim_smithing_template) and [](item://minecraft:sentry_armor_trim_smithing_template).
                """);
    }

    @Override
    protected String entryName() {
        return "Wild Illager Invasion";
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(Items.TOTEM_OF_UNDYING);
    }

    @Override
    protected String entryId() {
        return ENTRY_ID;
    }
}
