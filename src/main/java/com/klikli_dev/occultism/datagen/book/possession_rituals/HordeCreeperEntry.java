package com.klikli_dev.occultism.datagen.book.possession_rituals;

import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import net.minecraft.world.item.Items;

public class HordeCreeperEntry extends AbstractPossessionEntry {

    public static final String ENTRY_ID = "horde_creeper";

    public HordeCreeperEntry(CategoryProvider parent) {
        super(parent);
    }

    @Override
    protected void generatePages() {
        this.entityPage("entity", "occultism:wild_horde_creeper", 0.8f);
        this.pageText("""
                **Drops**: Discs that the normal creeper drops when killed by Skeleton (See next page);
                """);

        this.ritualPage("ritual", "ritual/wild_creeper");

        this.textPageNoTitle("description");
        this.pageText("""
                Creeper summoned in this ritual are CHARGED and will drop 1-3 of these discs: [](item://minecraft:music_disc_13), [](item://minecraft:music_disc_cat), [](item://minecraft:music_disc_blocks), [](item://minecraft:music_disc_chirp), [](item://minecraft:music_disc_far), [](item://minecraft:music_disc_mall), [](item://minecraft:music_disc_mellohi), [](item://minecraft:music_disc_stal), [](item://minecraft:music_disc_strad), [](item://minecraft:music_disc_ward), [](item://minecraft:music_disc_11), [](item://minecraft:music_disc_wait).
                """);
    }

    @Override
    protected String entryName() {
        return "Wild Horde Creeper";
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(Items.MUSIC_DISC_CAT);
    }

    @Override
    protected String entryId() {
        return ENTRY_ID;
    }
}
