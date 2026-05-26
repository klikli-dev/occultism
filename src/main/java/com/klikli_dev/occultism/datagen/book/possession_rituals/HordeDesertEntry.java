package com.klikli_dev.occultism.datagen.book.possession_rituals;

import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import net.minecraft.world.item.Items;

public class HordeDesertEntry extends AbstractPossessionEntry {

    public static final String ENTRY_ID = "horde_desert";

    public HordeDesertEntry(CategoryProvider parent) {
        super(parent);
    }

    @Override
    protected void generatePages() {
        this.entityPage("husk", "occultism:wild_horde_husk", 1f);
        this.pageText("""
                **Drops**: Items related to desert trials (See next page);
                """);

        this.ritualPage("ritual", "ritual/wild_husk");

        this.entityPage("parched", "occultism:wild_horde_parched", 1f);
        this.pageText("""
                **Drops**: Items related to desert trials (See next page);
                """);

        this.ritualPage("ritual2", "ritual/wild_parched");

        this.textPageNoTitle("description");
        this.pageText("""
                Husks and Parcheds summoned by any of this rituals can drop: [](item://minecraft:dune_armor_trim_smithing_template), [](item://minecraft:archer_pottery_sherd), [](item://minecraft:miner_pottery_sherd), [](item://minecraft:prize_pottery_sherd), [](item://minecraft:skull_pottery_sherd), [](item://minecraft:arms_up_pottery_sherd), [](item://minecraft:brewer_pottery_sherd).
                """);
    }

    @Override
    protected String entryName() {
        return "Wild Horde Desert";
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(Items.DUNE_ARMOR_TRIM_SMITHING_TEMPLATE);
    }

    @Override
    protected String entryId() {
        return ENTRY_ID;
    }
}
