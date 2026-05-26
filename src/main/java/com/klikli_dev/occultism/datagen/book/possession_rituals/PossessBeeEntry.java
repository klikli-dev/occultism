package com.klikli_dev.occultism.datagen.book.possession_rituals;

import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.occultism.registry.OccultismItems;

public class PossessBeeEntry extends AbstractPossessionEntry {

    public static final String ENTRY_ID = "possess_bee";

    public PossessBeeEntry(CategoryProvider parent) {
        super(parent);
    }

    @Override
    protected void generatePages() {
        this.entityPage("entity", "occultism:possessed_bee", 1.0f);
        this.pageText("""
                  **Drops**: [](item://occultism:cursed_honey);
                """);

        this.ritualPage("ritual", "ritual/possess_bee");

        this.textPageNoTitle("description");
        this.pageText("""
                In this ritual an [#]({0})djinni[#]() will possess an [#]({0})Bee[#](), Be careful,
                 a bee invoked by this way does not lose its stinger, always poison the target,
                 attacks faster and can summon other bees when it takes damage.
                 This is the only known method to obtain [](item://occultism:cursed_honey), eating will grants
                 a short regeneration buff.\\
                 \\
                 If this bee enters a hive, the djinni will return to [#]({0})The Other Place[#]().
                """, COLOR_PURPLE);
    }

    @Override
    protected String entryName() {
        return "Possessed Bee";
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(OccultismItems.CURSED_HONEY);
    }

    @Override
    protected String entryId() {
        return ENTRY_ID;
    }
}
