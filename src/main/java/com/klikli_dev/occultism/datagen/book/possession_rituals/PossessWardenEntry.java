package com.klikli_dev.occultism.datagen.book.possession_rituals;

import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import net.minecraft.world.item.Items;

public class PossessWardenEntry extends AbstractPossessionEntry {

    public static final String ENTRY_ID = "possess_warden";

    public PossessWardenEntry(CategoryProvider parent) {
        super(parent);
    }

    @Override
    protected void generatePages() {
        this.entityPage("entity", "occultism:possessed_warden", 1f);
        this.pageText("""
                **Drops**: 6-9x [](item://minecraft:echo_shard)
                and items related to ancient city;
                """);

        this.ritualPage("ritual", "ritual/possess_warden");

        this.textPageNoTitle("description");
        this.pageText("""
                In this ritual a [#]({0})Warden[#]() is spawned using the life energy of a [#]({0})Axolotl[#]() and immediately possessed by the summoned [#]({0})Afrit[#](). The [#]({0})Possessed Warden[#]() will always drop at least six [](item://minecraft:echo_shard) when killed and as a chance to drop [](item://minecraft:disc_fragment_5), [](item://minecraft:music_disc_otherside), [](item://minecraft:silence_armor_trim_smithing_template), [](item://minecraft:ward_armor_trim_smithing_template). If you try to escape, this possessed Warden will go to the floor like a normal warden.
                """, COLOR_PURPLE);
    }

    @Override
    protected String entryName() {
        return "Possessed Warden";
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(Items.ECHO_SHARD);
    }

    @Override
    protected String entryId() {
        return ENTRY_ID;
    }
}
