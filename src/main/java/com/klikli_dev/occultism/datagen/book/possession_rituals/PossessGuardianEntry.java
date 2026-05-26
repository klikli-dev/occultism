package com.klikli_dev.occultism.datagen.book.possession_rituals;

import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import net.minecraft.world.item.Items;

public class PossessGuardianEntry extends AbstractPossessionEntry {

    public static final String ENTRY_ID = "possess_guardian";

    public PossessGuardianEntry(CategoryProvider parent) {
        super(parent);
    }

    @Override
    protected void generatePages() {
        this.entityPage("entity", "occultism:possessed_guardian", 0.6f);
        this.pageText("""
                  **Drops**: Every coral, prismarine and some sea plants;
                """);

        this.ritualPage("ritual", "ritual/possess_guardian");

        this.textPageNoTitle("description");
        this.pageText("""
                In this ritual an [#]({0})afrit[#]() will possess a [#]({0})Guardian[#](), 
                harvesting energy from warm seas, corals are infused into the guardian's internal structure.\\ 
                \\
                 Drops:
                  + [](item://minecraft:sea_pickle) or [](item://minecraft:kelp);
                  + [](item://minecraft:tube_coral), [](item://minecraft:brain_coral), [](item://minecraft:bubble_coral),
                  [](item://minecraft:fire_coral), [](item://minecraft:horn_coral) (all also in block and fan version)
                  + [](item://minecraft:prismarine_shard), [](item://minecraft:prismarine_crystals);
                """, COLOR_PURPLE);
    }

    @Override
    protected String entryName() {
        return "Possessed Guardian";
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(Items.TUBE_CORAL);
    }

    @Override
    protected String entryId() {
        return ENTRY_ID;
    }
}
