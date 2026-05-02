package com.klikli_dev.occultism.datagen.book.binding_rituals;

import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.EntryBackground;
import com.klikli_dev.modonomicon.api.datagen.EntryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookSpotlightPageModel;
import com.klikli_dev.modonomicon.client.gui.book.theme.GuiSprite;
import com.klikli_dev.occultism.integration.modonomicon.pages.BookRitualRecipePageModel;
import com.klikli_dev.occultism.registry.OccultismBlocks;
import net.minecraft.ChatFormatting;
import net.minecraft.world.item.crafting.Ingredient;

public class StorageControllerBaseEntry extends EntryProvider {

    public static final String ENTRY_ID = "craft_storage_controller_base";

    public StorageControllerBaseEntry(CategoryProvider parent) {
        super(parent);
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(OccultismBlocks.STORAGE_CONTROLLER_BASE.get());
    }

    @Override
    protected String entryName() {
        return "Storage Actuator Base";
    }

    @Override
    protected String entryDescription() {
        return "Storage Body";
    }

    @Override
    protected void generatePages() {
        this.page("spotlight", () -> BookSpotlightPageModel.create()
                .withItem(Ingredient.of(OccultismBlocks.STORAGE_CONTROLLER_BASE.get()))
                .withText(this.context().pageText()));
        this.pageText("""
                        The storage actuator base imprisons a {0} responsible for
                         interacting with items in a dimensional storage matrix.
                        """,
                this.color("Foliot", ChatFormatting.DARK_PURPLE)
        );

        this.page("ritual", () -> BookRitualRecipePageModel.create()
                .withRecipeId1(this.modLoc("ritual/craft_storage_controller_base"))
        );
        //no text

        this.page("spotlight_dark", () -> BookSpotlightPageModel.create()
                .withItem(Ingredient.of(OccultismBlocks.STORAGE_CONTROLLER_BASE_DARK.get()))
                .withText(this.context().pageText()));
        this.pageText("""
                        All inventory system blocks have a dark version,
                         they function exactly like their counterpart.
                        """,
                this.color("", ChatFormatting.DARK_PURPLE)
        );

        this.page("ritual_dark", () -> BookRitualRecipePageModel.create()
                .withRecipeId1(this.modLoc("ritual/craft_storage_controller_base_dark"))
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
