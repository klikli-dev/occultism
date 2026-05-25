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

public class CraftAfritEntry extends EntryProvider {

    public static final String ENTRY_ID = "craft_afrit";


    public CraftAfritEntry(CategoryProvider parent) {
        super(parent);
    }

    @Override
    protected void generatePages() {

        this.page("intro", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText())
        );
        this.pageTitle("Seviras Permanent Confinement");
        this.pageText("""
                        **Purpose:** Bind {0}\\
                        \\
                        First discovered by Grandmistress Sevira of Emberwoods, **Seviras Permanent Confinement** is used for
                         binding {1} into objects. Due to the power of the spirits involved, this should be performed only by advanced summoners.
                        
                        """,
                this.color("Afrit", ChatFormatting.DARK_PURPLE),
                this.color("Afrit", ChatFormatting.DARK_PURPLE)
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
                - {6}
                - {7}
                """, this.entryLink("Dimensional Battlefield", "crafting_rituals", "dimensional_battlefield"), this.entryLink("Iesnium Ritual Bowl", "crafting_rituals", "craft_iesnium_sacrificial_bowl"), this.entryLink("Iesnium Butcher Knife", "crafting_rituals", "iesnium_butcher_knife"), this.entryLink("Storage Stabilizer Tier 3", "crafting_rituals", "craft_stabilizer_tier3"), this.entryLink("Afrit Deep Ore Miner", "crafting_rituals", "craft_afrit_miner"), this.entryLink("Artisanal Ritual Satchel", "crafting_rituals", "artisanal_ritual_satchel"), this.entryLink("Repair Items", "crafting_rituals", "repair"), this.entryLink("Whiterite Dust", "pentacles", "black_chalk"));

    }

    @Override
    protected String entryName() {
        return "Sevira's Permanent Confinement";
    }

    @Override
    protected String entryDescription() {
        return "Afrit Infusion";
    }

    @Override
    protected GuiSprite entryBackground() {
        return EntryBackground.DEFAULT;
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(OccultismItems.PENTACLE_CRAFT.get());
    }

    @Override
    protected String entryId() {
        return ENTRY_ID;
    }
}
