package com.klikli_dev.occultism.datagen.book.binding_rituals;

import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.EntryBackground;
import com.klikli_dev.modonomicon.api.datagen.EntryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookSpotlightPageModel;
import com.klikli_dev.modonomicon.client.gui.book.theme.GuiSprite;
import com.klikli_dev.occultism.integration.modonomicon.pages.BookRitualRecipePageModel;
import com.klikli_dev.occultism.registry.OccultismBlocks;
import com.klikli_dev.occultism.registry.OccultismItems;
import net.minecraft.ChatFormatting;
import net.minecraft.world.item.crafting.Ingredient;

public class StorageRemoteEntry extends EntryProvider {

    public static final String ENTRY_ID = "craft_storage_remote";

    public StorageRemoteEntry(CategoryProvider parent) {
        super(parent);
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(OccultismItems.STORAGE_REMOTE);
    }

    @Override
    protected String entryName() {
        return "Remote Storage Accessor";
    }

    @Override
    protected String entryDescription() {
        return "Everything in your hands";
    }

    @Override
    protected void generatePages() {
        this.page("spotlight", () -> BookSpotlightPageModel.create()
                .withItem(Ingredient.of(OccultismItems.STORAGE_REMOTE))
                .withText(this.context().pageText()));
        this.pageText("""
                        The {0} can be linked to a {1} by shift-clicking.
                         The {2} bound to the accessor will then be able to
                         access items from the actuator even from across dimensions.
                        """,
                this.itemLink(OccultismItems.STORAGE_REMOTE),
                this.itemLink(OccultismBlocks.STORAGE_CONTROLLER),
                this.color("Djinni", ChatFormatting.DARK_PURPLE)
        );

        this.page("ritual", () -> BookRitualRecipePageModel.create()
                .withRecipeId1(this.modLoc("ritual/craft_storage_remote"))
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
