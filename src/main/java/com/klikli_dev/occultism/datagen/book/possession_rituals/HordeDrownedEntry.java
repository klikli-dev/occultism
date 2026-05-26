package com.klikli_dev.occultism.datagen.book.possession_rituals;

import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import net.minecraft.world.item.Items;

public class HordeDrownedEntry extends AbstractPossessionEntry {

    public static final String ENTRY_ID = "horde_drowned";

    public HordeDrownedEntry(CategoryProvider parent) {
        super(parent);
    }

    @Override
    protected void generatePages() {
        this.entityPage("entity", "occultism:wild_horde_drowned", 1f);
        this.pageText("""
                **Drops**: Items related to ocean trials (See next page);
                """);

        this.ritualPage("ritual", "ritual/wild_drowned");

        this.textPageNoTitle("description");
        this.pageText("""
                Drowned summoned by this way can drop: [](item://minecraft:sniffer_egg), [](item://minecraft:turtle_egg), [](item://minecraft:trident), [](item://minecraft:angler_pottery_sherd), [](item://minecraft:shelter_pottery_sherd), [](item://minecraft:snort_pottery_sherd), [](item://minecraft:blade_pottery_sherd), [](item://minecraft:explorer_pottery_sherd), [](item://minecraft:mourner_pottery_sherd), [](item://minecraft:plenty_pottery_sherd).
                """);
    }

    @Override
    protected String entryName() {
        return "Wild Horde Drowned";
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(Items.SNIFFER_EGG);
    }

    @Override
    protected String entryId() {
        return ENTRY_ID;
    }
}
