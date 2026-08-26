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
                - {0}
                - {1}
                - {2}
                - {3}
                - {4}
                - {5}
                - {6}
                """,
                this.entryLink("Research Fragment Dust", "crafting_rituals", "craft_research_dust"),
                this.entryLink("Nature Paste", "crafting_rituals", "craft_nature_paste"),
                this.entryLink("Infused Lenses", "crafting_rituals", "craft_otherworld_goggles"),
                this.entryLink("Fragile Soul Gem", "crafting_rituals", "fragile_soul_gem"),
                this.entryLink("Vitality Compass", "crafting_rituals", "vitality_compass"),
                this.entryLink("Knowledge Tablet", "crafting_rituals", "knowledge_tablet"),
                this.entryLink("Foliot Miner", "crafting_rituals", "craft_foliot_miner"));

        this.page("uses2", () -> BookTextPageModel.create()
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
                """, this.entryLink("Surprisingsly Substantial Satchel", "crafting_rituals", "craft_satchel"), this.entryLink("Apprentice Ritual Satchel", "crafting_rituals", "apprentice_ritual_satchel"), this.entryLink("Storage Actuator Base", "crafting_rituals", "craft_storage_controller_base"), this.entryLink("Stable Wormhole", "crafting_rituals", "craft_stable_wormhole"), this.entryLink("Storage Stabilizer Tier 1", "crafting_rituals", "craft_stabilizer_tier1"));

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
