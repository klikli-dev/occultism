package com.klikli_dev.occultism.datagen.book.possession_rituals;

import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.occultism.registry.OccultismItems;

public class WildRandomAnimalEntry extends AbstractPossessionEntry {

    public static final String ENTRY_ID = "wild_random_animal";

    public WildRandomAnimalEntry(CategoryProvider parent) {
        super(parent);
    }

    @Override
    protected void generatePages() {
        this.textPage("description");
        this.pageTitle("Summon Group of Random Animal");
        this.pageText("""
                You have learned how to attract groups of random animals by changing
                 the pentacle to [#]({0})Osorin's Wild Calling[#]() and using a
                 [](item://occultism:spirit_attuned_gem) instead of a bound book of binding.
                """, COLOR_PURPLE);

        this.ritualPage("ritual_common", "ritual/wild_random_animal_common");
        this.ritualPage("ritual_water", "ritual/wild_random_animal_water");
        this.ritualPage("ritual_small", "ritual/wild_random_animal_small");
        this.ritualPage("ritual_rideable", "ritual/wild_random_animal_rideable");
        this.ritualPage("ritual_villager", "ritual/wild_villager");
        this.ritualPage("ritual_special", "ritual/wild_random_animal_special");
    }

    @Override
    protected String entryName() {
        return "Group of Random Animal";
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(OccultismItems.MYSTERIOUS_EGG_ICON);
    }

    @Override
    protected String entryId() {
        return ENTRY_ID;
    }
}
