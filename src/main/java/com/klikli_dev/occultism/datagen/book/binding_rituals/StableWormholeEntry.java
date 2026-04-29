package com.klikli_dev.occultism.datagen.book.binding_rituals;

import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.EntryBackground;
import com.klikli_dev.modonomicon.api.datagen.EntryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookSpotlightPageModel;
import com.klikli_dev.occultism.integration.modonomicon.pages.BookRitualRecipePageModel;
import com.klikli_dev.occultism.registry.OccultismBlocks;
import com.klikli_dev.modonomicon.client.gui.book.theme.GuiSprite;
import net.minecraft.world.item.crafting.Ingredient;

public class StableWormholeEntry extends EntryProvider {

    public static final String ENTRY_ID = "craft_stable_wormhole";

    public StableWormholeEntry(CategoryProvider parent) {
        super(parent);
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(OccultismBlocks.STABLE_WORMHOLE);
    }

    @Override
    protected String entryName() {
        return "Stable Wormhole";
    }

    @Override
    protected String entryDescription() {
        return "Extra access points";
    }

    @Override
    protected void generatePages() {
        this.page("spotlight", () -> BookSpotlightPageModel.create()
                .withItem(Ingredient.of(OccultismBlocks.STABLE_WORMHOLE))
                .withText(this.context().pageText()));
        this.pageText("""
                        The stable wormhole allows access to a dimensional matrix from a remote destination.
                        \\
                        \\
                        Shift-click a {0} to link it, then place the wormhole in the world to use it as a remote access point.
                        """,
                this.itemLink(OccultismBlocks.STORAGE_CONTROLLER)
        );

        this.page("ritual", () -> BookRitualRecipePageModel.create()
                .withRecipeId1(this.modLoc("ritual/craft_stable_wormhole"))
        );
        //no text

        this.page("ritual_dark", () -> BookRitualRecipePageModel.create()
                .withRecipeId1(this.modLoc("ritual/craft_stable_wormhole_dark"))
        );
        //no text
    }

    @Override
    protected GuiSprite entryBackground() {
        return EntryBackground.DEFAULT;
    }

    @Override
    protected String entryId() {
        return ENTRY_ID;
    }
}
