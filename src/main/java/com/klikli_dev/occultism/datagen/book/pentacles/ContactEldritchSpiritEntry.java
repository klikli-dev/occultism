package com.klikli_dev.occultism.datagen.book.pentacles;

import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.EntryBackground;
import com.klikli_dev.modonomicon.api.datagen.EntryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookMultiblockPageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookTextPageModel;
import com.klikli_dev.modonomicon.client.gui.book.theme.GuiSprite;
import com.klikli_dev.occultism.registry.OccultismItems;
import net.minecraft.ChatFormatting;

public class ContactEldritchSpiritEntry extends EntryProvider {

    public static final String ENTRY_ID = "contact_eldritch_spirit";

    public ContactEldritchSpiritEntry(CategoryProvider parent) {
        super(parent);
    }

    @Override
    protected void generatePages() {

        this.page("intro", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText())
        );
        this.pageTitle("Ronazas Contact");
        this.pageText("""
                        **Purpose:** Contact {0}\\
                        \\
                        This strange pentacle uses forbidden knowledge, bringing together paraphernalia from ancient traditions.\\
                        The level of power that can be achieved almost cheats the common reality.
                        """,
                this.color("Eldritch", ChatFormatting.DARK_PURPLE)
        );

        this.page("multiblock", () -> BookMultiblockPageModel.create().withMultiblockId(this.modLoc(ENTRY_ID)));

        this.page("uses", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText())
        );
        this.pageTitle("Uses");
        this.pageText("""
                - {0}
                - {1}
                - {2}
                - {3}
                - {4}
                - {5}
                """, this.entryLink("Eldritch Ancient Miner", "crafting_rituals", "craft_ancient_miner"), this.entryLink("Storage Stabilizer Tier 5", "crafting_rituals", "stabilizer_tier5"), this.entryLink("Stabilized Storage", "crafting_rituals", "craft_stabilized_storage"), this.entryLink("Eldritch Chalice", "crafting_rituals", "craft_eldritch_chalice"), this.entryLink("Mastery Chalks", "crafting_rituals", "craft_master_chalks"), this.entryLink("Trinity Gem", "crafting_rituals", "trinity_gem"));

    }

    @Override
    protected String entryName() {
        return "Ronaza's Contact";
    }

    @Override
    protected String entryDescription() {
        return "Contact Eldritch";
    }

    @Override
    protected GuiSprite entryBackground() {
        return EntryBackground.DEFAULT;
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(OccultismItems.PENTACLE_MISC.get());
    }

    @Override
    protected String entryId() {
        return ENTRY_ID;
    }
}
