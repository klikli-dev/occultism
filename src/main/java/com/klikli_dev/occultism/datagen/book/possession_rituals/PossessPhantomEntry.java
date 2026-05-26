package com.klikli_dev.occultism.datagen.book.possession_rituals;

import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import net.minecraft.world.item.Items;

public class PossessPhantomEntry extends AbstractPossessionEntry {

    public static final String ENTRY_ID = "possess_phantom";

    public PossessPhantomEntry(CategoryProvider parent) {
        super(parent);
    }

    @Override
    protected void generatePages() {
        this.entityPage("entity", "occultism:possessed_phantom", 0.5f);
        this.pageText("""
                **Drops**: 1-4x [](item://minecraft:phantom_membrane)
                and has 5%% chance to drop a [](item://minecraft:wind_charge)
                """);

        this.ritualPage("ritual", "ritual/possess_phantom");

        this.textPageNoTitle("description");
        this.pageText("""
                In this ritual a [#]({0})Phantom[#]() is spawned using the life energy of a [#]({0})Flying Passive Mob[#]() and immediately possessed by the summoned [#]({0})Foliot[#](). The [#]({0})Possessed Phantom[#]() will always drop at least one [](item://minecraft:phantom_membrane) when killed. Using this ritual is easy to trap the phantom and you can has comfy sleep.
                """, COLOR_PURPLE);
    }

    @Override
    protected String entryName() {
        return "Possessed Phantom";
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(Items.PHANTOM_MEMBRANE);
    }

    @Override
    protected String entryId() {
        return ENTRY_ID;
    }
}
