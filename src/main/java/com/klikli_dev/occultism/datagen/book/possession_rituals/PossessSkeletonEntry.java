package com.klikli_dev.occultism.datagen.book.possession_rituals;

import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import net.minecraft.world.item.Items;

public class PossessSkeletonEntry extends AbstractPossessionEntry {

    public static final String ENTRY_ID = "possess_skeleton";

    public PossessSkeletonEntry(CategoryProvider parent) {
        super(parent);
    }

    @Override
    protected void generatePages() {
        this.entityPage("entity", "occultism:possessed_skeleton");
        this.pageText("""
                **Drops**: 1x [](item://minecraft:skeleton_skull)
                """);

        this.ritualPage("ritual", "ritual/possess_skeleton");

        this.textPageNoTitle("description");
        this.pageText("""
                In this ritual an [#]({0})Skeleton[#]() is spawned using the life energy of a [#]({0})Chicken[#]() and possessed by a [#]({0})Foliot[#](). The [#]({0})Possessed Skeleton[#]() will be immune to daylight and always drop at least one [](item://minecraft:skeleton_skull) when killed.
                """, COLOR_PURPLE);
    }

    @Override
    protected String entryName() {
        return "Possessed Skeleton";
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(Items.SKELETON_SKULL);
    }

    @Override
    protected String entryId() {
        return ENTRY_ID;
    }
}
