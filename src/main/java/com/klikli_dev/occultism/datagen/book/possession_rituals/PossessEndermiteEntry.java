package com.klikli_dev.occultism.datagen.book.possession_rituals;

import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import net.minecraft.world.level.block.Blocks;

public class PossessEndermiteEntry extends AbstractPossessionEntry {

    public static final String ENTRY_ID = "possess_endermite";

    public PossessEndermiteEntry(CategoryProvider parent) {
        super(parent);
    }

    @Override
    protected void generatePages() {
        this.entityPage("entity", "occultism:possessed_endermite");
        this.pageText("""
                **Drops**: 1-2x [](item://minecraft:end_stone)
                and as 25%% chance to drop an Eye
                """);

        this.ritualPage("ritual", "ritual/possess_endermite");

        this.textPage("description");
        this.pageText("""
                In this ritual an [#]({0})Endermite[#]() is tricked into spawning. The stone and dirt represent the surroundings, then an egg is thrown to simulate the use of an ender pearl. When the mite spawns, the summoned [#]({0})Foliot[#]() immediately possesses it, visits [#]({0})The End[#](), and returns. The [#]({0})Possessed Endermite[#]() will always drop at least one [](item://minecraft:end_stone) when killed.
                """, COLOR_PURPLE);
    }

    @Override
    protected String entryName() {
        return "Possessed Endermite";
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(Blocks.END_STONE);
    }

    @Override
    protected String entryId() {
        return ENTRY_ID;
    }
}
