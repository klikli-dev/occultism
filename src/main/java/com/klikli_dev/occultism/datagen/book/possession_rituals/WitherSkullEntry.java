package com.klikli_dev.occultism.datagen.book.possession_rituals;

import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import net.minecraft.world.item.Items;

public class WitherSkullEntry extends AbstractPossessionEntry {

    public static final String ENTRY_ID = "wither_skull";

    public WitherSkullEntry(CategoryProvider parent) {
        super(parent);
    }

    @Override
    protected void generatePages() {
        this.textPage("intro");
        this.pageTitle("Wither Skeleton Skull");
        this.pageText("""
                Besides venturing into nether dungeons, there is one more way to get these skulls. The legendary [#]({0})Wild Hunt[#]() consists of [#]({0})Greater Spirits[#]() taking the form of wither skeletons. While summoning the Wild Hunt is incredibly dangerous, it is the fastest way to get wither skeleton skulls.
                """, COLOR_PURPLE);

        this.ritualPage("ritual", "ritual/wild_hunt");
    }

    @Override
    protected String entryName() {
        return "Wild Hunt";
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(Items.WITHER_SKELETON_SKULL);
    }

    @Override
    protected String entryId() {
        return ENTRY_ID;
    }
}
