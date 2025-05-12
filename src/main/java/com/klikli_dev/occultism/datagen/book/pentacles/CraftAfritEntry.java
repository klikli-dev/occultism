package com.klikli_dev.occultism.datagen.book.pentacles;

import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.EntryBackground;
import com.klikli_dev.modonomicon.api.datagen.EntryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookMultiblockPageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookTextPageModel;
import com.klikli_dev.occultism.registry.OccultismItems;
import com.mojang.datafixers.util.Pair;
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
                - [Iesnium Sacrificial Bowl](entry://crafting_rituals/craft_iesnium_sacrificial_bowl)
                - [Storage Stabilizer Tier 3](entry://crafting_rituals/craft_stabilizer_tier3)
                - [Afrit Deep Ore Miner](entry://crafting_rituals/craft_afrit_miner)
                - [Artisanal Ritual Satchel](entry://crafting_rituals/artisanal_ritual_satchel)
                - [Repair Items](entry://crafting_rituals/repair)
                - [Whiterite Dust](entry://pentacles/black_chalk)
                """
        );

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
    protected Pair<Integer, Integer> entryBackground() {
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
