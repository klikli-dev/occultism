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

public class CraftFoliotEntry extends EntryProvider {

    public static final String ENTRY_ID = "craft_foliot";


    public CraftFoliotEntry(CategoryProvider parent) {
        super(parent);
    }

    @Override
    protected void generatePages() {

        this.page("intro", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText())
        );
        this.pageTitle("Eziveus Spectral Compulsion");
        this.pageText("""
                **Purpose:** Bind {0}\\
                \\
                As a simple binding pentacle, **Eziveus Spectral Compulsion** is a common starting point for object
                 infusion with lower spirits. The enchantment is made permanent by stabilizing candles.
                """,
                this.color("Foliot", ChatFormatting.DARK_PURPLE),
                this.color("Marid", ChatFormatting.DARK_PURPLE),
                this.color("Marid", ChatFormatting.DARK_PURPLE)
        );

        this.page("multiblock", () -> BookMultiblockPageModel.create().withMultiblockId(this.modLoc(ENTRY_ID)));

        this.page("uses", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText())
        );
        this.pageTitle("Uses");
        this.pageText("""
                - [Infused Lenses](entry://crafting_rituals/craft_otherworld_goggles)
                - [Surprisingsly Substantial Satchel](entry://crafting_rituals/craft_satchel)
                - [Storage Actuator Base](entry://crafting_rituals/craft_storage_controller_base)
                - [Stable Wormhole](entry://crafting_rituals/craft_stable_wormhole)
                - [Storage Stabilizer Tier 1](entry://crafting_rituals/craft_stabilizer_tier1)
                - [Foliot Miner](entry://crafting_rituals/craft_foliot_miner)
                - [Apprentice Ritual Satchel](entry://crafting_rituals/apprentice_ritual_satchel)
                - [Fragile Soul Gem](entry://crafting_rituals/fragile_soul_gem)
                - [Research Fragment Dust](entry://pentacles/lime_chalk)
                - [Nature Paste](entry://pentacles/green_chalk)
                """
        );

    }

    @Override
    protected String entryName() {
        return "Eziveus' Spectral Compulsion";
    }

    @Override
    protected String entryDescription() {
        return "Foliot Infusion";
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
