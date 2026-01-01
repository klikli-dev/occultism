package com.klikli_dev.occultism.datagen.book.storage_system;

import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.EntryBackground;
import com.klikli_dev.modonomicon.api.datagen.EntryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookCraftingRecipePageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookMultiblockPageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookSpotlightPageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookTextPageModel;
import com.klikli_dev.occultism.registry.OccultismBlocks;
import com.mojang.datafixers.util.Pair;

public class StabilizerEntry extends EntryProvider {

    public static final String ENTRY_ID = "storage_stabilizer";

    public StabilizerEntry(CategoryProvider parent) {
        super(parent);
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(OccultismBlocks.STORAGE_STABILIZER_TIER0);
    }

    @Override
    protected String entryName() {
        return "Extending Storage";
    }

    @Override
    protected String entryDescription() {
        return "External disk";
    }

    @Override
    protected void generatePages() {
        this.page("spotlight", () -> BookSpotlightPageModel.create()
                .withItem(OccultismBlocks.STORAGE_STABILIZER_TIER0)
                .withText(this.context().pageText()));
        this.pageText("""
                   Storage Stabilizers increase the storage space in the storage dimension of the storage actuator.
                    The higher the tier of the stabilizer, the more additional storage it provides.
                    The following entries will show you how to craft each tier.
                    """
        );

        this.page("crafting", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText()));
        this.pageTitle("Crafting");
        this.pageText("""
                    The stabilizer system works by tier, starting from 0 (which does not add space) up to 5.
                    \\
                    \\
                    To get started, see the recipes and instructions on the following pages.
                    """
        );

        this.page("recipe", () -> BookCraftingRecipePageModel.create()
                .withRecipeId1(this.modLoc("crafting/otherstone_pedestal"))
                .withRecipeId2(this.modLoc("crafting/storage_stabilizer_tier0"))
                );
        //no text
        this.page("recipe_dark", () -> BookCraftingRecipePageModel.create()
                .withRecipeId1(this.modLoc("crafting/otherrock_pedestal"))
                .withRecipeId2(this.modLoc("crafting/storage_stabilizer_tier0_dark"))
        );
        //no text

        this.page("upgrade", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText()));
        this.pageTitle("Upgrading");
        this.pageText("""
                   It is **safe to destroy a storage stabilizer** to upgrade it. The items
                    in the {0} will not be lost or dropped - you simply cannot add new items
                    until you add enough storage stabilizers to have free slots again.
                    """,
                this.entryLink("Storage Actuator", "storage", "storage_controller")
        );

        this.page("build_instructions", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText()));
        this.pageTitle("Build Instructions");
        this.pageText("""
                    Storage controllers need to point at the {0}, that means **one block above the {1}**.
                     \\
                     \\
                     They can be **up to 5 blocks away** from the Dimensional Matrix, and need to be in
                     a straight line of sight. See the next page for a possible very simple setup.
                    """,
                this.entryLink("Dimensional Matrix", "crafting_rituals", "craft_dimensional_matrix"),
                this.entryLink("Storage Actuator", "storage", "storage_controller")
        );

        this.page("demo", () -> BookMultiblockPageModel.create()
                .withMultiblockId(this.modLoc("storage_stabilizer_demo"))
                .withMultiblockName("Storage Stabilizer Setup")
                .withText(this.context().pageText()));
        this.pageText("""
                   **Note:** You do not need all 4 stabilizers, even one will increase your storage.
                    In addition, the up and down directions also work.
                    """
        );
    }

    @Override
    protected Pair<Integer, Integer> entryBackground() {
        return EntryBackground.DEFAULT;
    }

    @Override
    protected String entryId() {
        return ENTRY_ID;
    }
}
