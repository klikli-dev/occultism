package com.klikli_dev.occultism.datagen.book.possession_rituals;

import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.occultism.registry.OccultismItems;

public class MercyGoatEntry extends AbstractPossessionEntry {

    public static final String ENTRY_ID = "possess_goat";

    public MercyGoatEntry(CategoryProvider parent) {
        super(parent);
    }

    @Override
    protected void generatePages() {
        this.entityPage("entity", "occultism:mercy_goat", 0.7f);
        this.pageText("""
                  **Drops**: [](item://occultism:cruelty_essence);
                """);

        this.ritualPage("ritual", "ritual/possess_goat");

        this.textPageNoTitle("description");
        this.pageText("""
                In this ritual, a [#]({0})Goat of Mercy[#]() is summoned to be sacrificed. This is the only way to obtain the [](item://occultism:cruelty_essence).
                 Be sure of your actions, because they will forever mark your history.
                """, COLOR_PURPLE);
    }

    @Override
    protected String entryName() {
        return "Goat of Mercy";
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(OccultismItems.CRUELTY_ESSENCE);
    }

    @Override
    protected String entryId() {
        return ENTRY_ID;
    }
}
