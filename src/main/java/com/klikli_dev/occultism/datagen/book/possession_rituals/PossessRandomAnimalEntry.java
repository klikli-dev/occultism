package com.klikli_dev.occultism.datagen.book.possession_rituals;

import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.occultism.registry.OccultismItems;

public class PossessRandomAnimalEntry extends AbstractPossessionEntry {

    public static final String ENTRY_ID = "possess_random_animal";

    public PossessRandomAnimalEntry(CategoryProvider parent) {
        super(parent);
    }

    @Override
    protected void generatePages() {
        this.textPage("description");
        this.pageTitle("Summon Random Animal");
        this.pageText("""
                In this type of ritual, a [#]({0})Spirit[#]() is summoned **as an untamed creature** to take the shape of a random animal.
                Different rituals can be performed, each with their own respective animals, check in the ritual dummy or in the recipe output the possibilities.
                \\
                The animal can be interacted with as it's natural counterpart, including taming, breeding and loot.
                """, COLOR_PURPLE);

        this.ritualPage("ritual_common", "ritual/possess_random_animal_common");
        this.ritualPage("ritual_water", "ritual/possess_random_animal_water");
        this.ritualPage("ritual_small", "ritual/possess_random_animal_small");
        this.ritualPage("ritual_rideable", "ritual/possess_random_animal_rideable");
        this.ritualPage("ritual_villager", "ritual/possess_villager");
        this.ritualPage("ritual_special", "ritual/possess_random_animal_special");
    }

    @Override
    protected String entryName() {
        return "Possessed Random Animal";
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
