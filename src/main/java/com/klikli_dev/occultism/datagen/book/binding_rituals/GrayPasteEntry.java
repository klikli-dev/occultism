package com.klikli_dev.occultism.datagen.book.binding_rituals;

import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.EntryBackground;
import com.klikli_dev.modonomicon.api.datagen.EntryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookSpotlightPageModel;
import com.klikli_dev.modonomicon.client.gui.book.theme.GuiSprite;
import com.klikli_dev.occultism.integration.modonomicon.pages.BookRitualRecipePageModel;
import com.klikli_dev.occultism.registry.OccultismItems;
import net.minecraft.ChatFormatting;
import net.minecraft.world.item.crafting.Ingredient;

public class GrayPasteEntry extends EntryProvider {

    public static final String ENTRY_ID = "craft_gray_paste";


    public GrayPasteEntry(CategoryProvider parent) {
        super(parent);
    }

    @Override
    protected void generatePages() {
        this.page("spotlight", () -> BookSpotlightPageModel.create()
                .withItem(Ingredient.of(OccultismItems.GRAY_PASTE))
                .withText(this.context().pageText()));
        this.pageText("""
                        Gray Paste is a versatile crafting paste created with the help of {0}. \\
                        It can be used to convert various dusts back into their original items, \\
                        effectively acting as a reverse crusher.
                        """,
                this.color("Djinni", ChatFormatting.DARK_PURPLE)
        );

        this.page("ritual", () -> BookRitualRecipePageModel.create()
                .withRecipeId1(this.modLoc("ritual/craft_gray_paste"))
        );
        //no text
    }

    @Override
    protected String entryName() {
        return "Gray Paste";
    }

    @Override
    protected String entryDescription() {
        return "A versatile crafting paste";
    }

    @Override
    protected GuiSprite entryBackground() {
        return EntryBackground.DEFAULT;
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(OccultismItems.GRAY_PASTE);
    }

    @Override
    protected String entryId() {
        return ENTRY_ID;
    }
}
