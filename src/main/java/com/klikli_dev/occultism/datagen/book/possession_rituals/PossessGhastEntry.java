package com.klikli_dev.occultism.datagen.book.possession_rituals;

import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import net.minecraft.world.item.Items;

public class PossessGhastEntry extends AbstractPossessionEntry {

    public static final String ENTRY_ID = "possess_ghast";

    public PossessGhastEntry(CategoryProvider parent) {
        super(parent);
    }

    @Override
    protected void generatePages() {
        this.entityPage("entity", "occultism:possessed_ghast", 0.5f);
        this.pageText("""
                **Drops**: 1-3x [](item://minecraft:ghast_tear) and
                1-4x [](item://minecraft:gunpowder)
                """);

        this.ritualPage("ritual", "ritual/possess_ghast");

        this.textPage("description");
        this.pageText("""
                In this ritual a [#]({0})Ghast[#]() is spawned using the life energy of a [#]({0})Cow[#]() and immediately possessed by the summoned [#]({0})Djinni[#](). The [#]({0})Possessed Ghast[#]() will always drop at least one [](item://minecraft:ghast_tear) when killed.
                """, COLOR_PURPLE);
    }

    @Override
    protected String entryName() {
        return "Possessed Ghast";
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(Items.GHAST_TEAR);
    }

    @Override
    protected String entryId() {
        return ENTRY_ID;
    }
}
