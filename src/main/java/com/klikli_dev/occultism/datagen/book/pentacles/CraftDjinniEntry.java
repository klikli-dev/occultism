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

public class CraftDjinniEntry extends EntryProvider {

    public static final String ENTRY_ID = "craft_djinni";


    public CraftDjinniEntry(CategoryProvider parent) {
        super(parent);
    }

    @Override
    protected void generatePages() {

        this.page("intro", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText())
        );
        this.pageTitle("Strigeors Higher Binding");
        this.pageText("""
                **Purpose:** Bind {0}\\
                \\
                **Strigeors Higher Binding** is a pentacle for binding {1} into objects, should not be attempted by the
                 novice summoner. Supported by skeleton skulls and stabilized by candles it is highly suitable for
                 permanent infusions of objects with spirits.
                """,
                this.color("Djinni", ChatFormatting.DARK_PURPLE),
                this.color("Djinni", ChatFormatting.DARK_PURPLE)
        );

        this.page("multiblock", () -> BookMultiblockPageModel.create().withMultiblockId(this.modLoc(ENTRY_ID)));

        this.page("uses", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText())
        );
        this.pageTitle("Uses");
        this.pageText("""
                - [Infused Pickaxe](entry://crafting_rituals/craft_infused_pickaxe)
                - [Soul Gem](entry://crafting_rituals/craft_soul_gem)
                - [Familiar Ring](entry://crafting_rituals/craft_familiar_ring)
                - [Dimensional Matrix](entry://crafting_rituals/craft_dimensional_matrix)
                - [Storage Accessor](entry://crafting_rituals/craft_storage_remote)
                - [Storage Stabilizer Tier 2](entry://crafting_rituals/craft_stabilizer_tier2)
                - [Dimensional Mineshaft](entry://crafting_rituals/craft_dimensional_mineshaft)
                - [Djinni Ore Miner](entry://crafting_rituals/craft_djinni_miner)
                - [Repair Chalks](entry://crafting_rituals/repair)
                - [Gray Paste](entry://pentacles/gray_chalk)
                """
        );

    }

    @Override
    protected String entryName() {
        return "Strigeor's Higher Binding";
    }

    @Override
    protected String entryDescription() {
        return "Djinni Infusion";
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
