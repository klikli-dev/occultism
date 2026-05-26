package com.klikli_dev.occultism.datagen.book.possession_rituals;

import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import net.minecraft.world.item.Items;

public class PossessShulkerEntry extends AbstractPossessionEntry {

    public static final String ENTRY_ID = "possess_shulker";

    public PossessShulkerEntry(CategoryProvider parent) {
        super(parent);
    }

    @Override
    protected void generatePages() {
        this.entityPage("entity", "occultism:possessed_shulker", 0.5f);
        this.pageText("""
                **Drops**: 1-2x [](item://minecraft:shulker_shell) and
                as 10%% chance to drop a [](item://minecraft:chorus_flower)
                 or [](item://minecraft:spire_armor_trim_smithing_template);
                """);

        this.ritualPage("ritual", "ritual/possess_shulker");

        this.textPageNoTitle("description");
        this.pageText("""
                In this ritual a [#]({0})Shulker[#]() is spawned using the life energy of a [#]({0})Cube Mob[#]() and immediately possessed by the summoned [#]({0})Afrit[#](). The [#]({0})Possessed Shulker[#]() will always drop at least one [](item://minecraft:shulker_shell) when killed. You can use vanilla shulker multiplication to get normal shulkers but their have less chance to drop shells.
                """, COLOR_PURPLE);
    }

    @Override
    protected String entryName() {
        return "Possessed Shulker";
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(Items.SHULKER_SHELL);
    }

    @Override
    protected String entryId() {
        return ENTRY_ID;
    }
}
