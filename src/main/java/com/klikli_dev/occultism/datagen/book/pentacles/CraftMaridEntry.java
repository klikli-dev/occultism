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

public class CraftMaridEntry extends EntryProvider {

    public static final String ENTRY_ID = "craft_marid";


    public CraftMaridEntry(CategoryProvider parent) {
        super(parent);
    }

    @Override
    protected void generatePages() {

        this.page("intro", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText())
        );
        this.pageTitle("Uphyxes Inverted Tower");
        this.pageText("""
                **Purpose:** Bind {0}\\
                \\
                **Uphyxes Inverted Tower** is one of the few pentacles capable of binding {1} into objects.
                 Any rituals involving {2} should be performed only by the most experienced summoners.
                """,
                this.color("Marid", ChatFormatting.DARK_PURPLE),
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
                - [Storage Stabilizer Tier 4](entry://crafting_rituals/craft_stabilizer_tier4)
                - [Marid Master Miner](entry://crafting_rituals/craft_marid_miner)
                - [Iesnium Anvil](entry://crafting_rituals/craft_iesnium_anvil)
                - [True Sight Staff](entry://crafting_rituals/true_sight_staff)
                - [Dragonyst Dust](entry://pentacles/magenta_chalk)
                """
        );

    }

    @Override
    protected String entryName() {
        return "Uphyxes Inverted Tower";
    }

    @Override
    protected String entryDescription() {
        return "Marid Infusion";
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
