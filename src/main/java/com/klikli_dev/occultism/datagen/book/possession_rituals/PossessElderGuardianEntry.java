package com.klikli_dev.occultism.datagen.book.possession_rituals;

import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import net.minecraft.world.item.Items;

public class PossessElderGuardianEntry extends AbstractPossessionEntry {

    public static final String ENTRY_ID = "possess_elder_guardian";

    public PossessElderGuardianEntry(CategoryProvider parent) {
        super(parent);
    }

    @Override
    protected void generatePages() {
        this.entityPage("entity", "occultism:possessed_elder_guardian", 0.7f);
        this.pageText("""
                **Drops**: 2-4x [](item://minecraft:nautilus_shell)
                and as 40%% to drop a [](item://minecraft:heart_of_the_sea)
                Also common Elder Guardian loot;
                """);

        this.ritualPage("ritual", "ritual/possess_elder_guardian");

        this.textPageNoTitle("description");
        this.pageText("""
                In this ritual a [#]({0})Elder Guardian[#]() is spawned using the life energy of a [#]({0})Fish[#]() and immediately possessed by the summoned [#]({0})Afrit[#](). The [#]({0})Possessed Elder Guardian[#]() will always drop at least one [](item://minecraft:nautilus_shell), having a chance to drop [](item://minecraft:heart_of_the_sea) and a lot of things that normal Elder Guardian drops.
                """, COLOR_PURPLE);
    }

    @Override
    protected String entryName() {
        return "Possessed Elder Guardian";
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(Items.HEART_OF_THE_SEA);
    }

    @Override
    protected String entryId() {
        return ENTRY_ID;
    }
}
