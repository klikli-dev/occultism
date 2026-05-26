package com.klikli_dev.occultism.datagen.book.possession_rituals;

import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import net.minecraft.world.item.Items;

public class PossessWeakShulkerEntry extends AbstractPossessionEntry {

    public static final String ENTRY_ID = "possess_weak_shulker";

    public PossessWeakShulkerEntry(CategoryProvider parent) {
        super(parent);
    }

    @Override
    protected void generatePages() {
        this.entityPage("entity", "occultism:possessed_weak_shulker", 0.5f);
        this.pageText("""
                **Drops**: 1-3x [](item://minecraft:chorus_fruit)
                and as 10%% to drop a [](item://minecraft:shulker_shell);
                """);

        this.ritualPage("ritual", "ritual/possess_weak_shulker");

        this.textPageNoTitle("description");
        this.pageText("""
                In this ritual a [#]({0})Shulker[#]() is spawned using the life energy of a [#]({0})Cube Mob[#]() and immediately possessed by the summoned [#]({0})Djinni[#](). The [#]({0})Possessed Weak Shulker[#]() will always drop at least one [](item://minecraft:chorus_fruit) when killed and as a chance to drop [](item://minecraft:shulker_shell). You can use vanilla shulker multiplication to get normal shulkers with more chance to drop their shells.
                """, COLOR_PURPLE);
    }

    @Override
    protected String entryName() {
        return "Possessed Weak Shulker";
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(Items.CHORUS_FRUIT);
    }

    @Override
    protected String entryId() {
        return ENTRY_ID;
    }
}
