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
                - {0}
                - {1}
                - {2}
                - {3}
                - {4}
                - {5}
                - {6}
                """, this.entryLink("Infused Pickaxe", "crafting_rituals", "craft_infused_pickaxe"), this.entryLink("Soul Gem", "crafting_rituals", "craft_soul_gem"), this.entryLink("Familiar Ring", "crafting_rituals", "craft_familiar_ring"), this.entryLink("Entity Wormhole", "crafting_rituals", "entity_wormhole"), this.entryLink("Dimensional Extractor", "crafting_rituals", "dimensional_extractor"), this.entryLink("Dimensional Mineshaft", "crafting_rituals", "craft_dimensional_mineshaft"), this.entryLink("Djinni Ore Miner", "crafting_rituals", "craft_djinni_miner"));

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
                - {5}
                - {6}
                """, this.entryLink("Ender Satchel", "crafting_rituals", "ender_satchel"), this.entryLink("Dimensional Matrix", "crafting_rituals", "craft_dimensional_matrix"), this.entryLink("Storage Accessor", "crafting_rituals", "craft_storage_remote"), this.entryLink("Storage Stabilizer Tier 2", "crafting_rituals", "craft_stabilizer_tier2"), this.entryLink("Spirit Grindstone", "crafting_rituals", "spirit_grindstone"), this.entryLink("Repair Chalks", "crafting_rituals", "repair"), this.entryLink("Gray Paste", "pentacles", "gray_chalk"));

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
