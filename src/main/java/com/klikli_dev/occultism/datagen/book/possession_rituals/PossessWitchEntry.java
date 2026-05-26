package com.klikli_dev.occultism.datagen.book.possession_rituals;

import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import net.minecraft.world.item.Items;

public class PossessWitchEntry extends AbstractPossessionEntry {

    public static final String ENTRY_ID = "possess_witch";

    public PossessWitchEntry(CategoryProvider parent) {
        super(parent);
    }

    @Override
    protected void generatePages() {
        this.entityPage("entity", "occultism:possessed_witch", 0.4f, 0.8f);
        this.pageText("""
                  **Drops**: Can drop: [](item://minecraft:experience_bottle) or other bottles (See next page);
                """);

        this.ritualPage("ritual", "ritual/possess_witch");

        this.textPageNoTitle("description");
        this.pageText("""
                In this ritual a [#]({0})Witch[#]() is spawned using the rage energy from the [#]({0})Cat[#]() death. The [#]({0})Possessed Witch[#]() can drop a [](item://minecraft:experience_bottle), [](item://minecraft:honey_bottle), [](item://minecraft:ominous_bottle) or a simple water bottle.
                """, COLOR_PURPLE);
    }

    @Override
    protected String entryName() {
        return "Possessed Witch";
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(Items.EXPERIENCE_BOTTLE);
    }

    @Override
    protected String entryId() {
        return ENTRY_ID;
    }
}
