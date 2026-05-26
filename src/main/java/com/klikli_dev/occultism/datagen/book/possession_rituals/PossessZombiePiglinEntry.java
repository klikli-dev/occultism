package com.klikli_dev.occultism.datagen.book.possession_rituals;

import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.occultism.registry.OccultismItems;

public class PossessZombiePiglinEntry extends AbstractPossessionEntry {

    public static final String ENTRY_ID = "possess_zombie_piglin";

    public PossessZombiePiglinEntry(CategoryProvider parent) {
        super(parent);
    }

    @Override
    protected void generatePages() {
        this.entityPage("entity", "occultism:possessed_zombie_piglin", 0.7f);
        this.pageText("""
                  **Drops**: 1-4x [](item://occultism:demonic_meat) and
                  other body parts;
                """);

        this.ritualPage("ritual", "ritual/possess_zombified_piglin");

        this.textPageNoTitle("description");
        this.pageText("""
                In this ritual an [#]({0})afrit[#]() will possess an [#]({0})Old Zombified Piglin[#](),
                 unifying the energies of the [#]({0})nether[#](), the power of the [#]({0})afrit[#](),
                  the material [#]({0})pork[#]() and the concept of the color [#]({0})pink[#]().
                 This is the only known method to obtain [](item://occultism:demonic_meat), its properties
                  prevent cooking but grant fire resistance to whoever consumes it.
                """, COLOR_PURPLE);
    }

    @Override
    protected String entryName() {
        return "Possessed Zombified Piglin";
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(OccultismItems.DEMONIC_MEAT);
    }

    @Override
    protected String entryId() {
        return ENTRY_ID;
    }
}
