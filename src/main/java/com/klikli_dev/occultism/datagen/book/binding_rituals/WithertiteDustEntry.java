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

public class WithertiteDustEntry extends EntryProvider {

    public static final String ENTRY_ID = "craft_witherite_dust";

    public WithertiteDustEntry(CategoryProvider parent) {
        super(parent);
    }

    @Override
    protected void generatePages() {
        this.page("spotlight", () -> BookSpotlightPageModel.create()
                .withItem(Ingredient.of(OccultismItems.WITHERITE_DUST))
                .withText(this.context().pageText()));
        this.pageText("""
                        Withertite dust is a powerful ingredient for dark rituals.
                         It is crafted using the essence of the {0} and
                         requires rare materials from the Nether.
                        """,
                this.color("Afrit", ChatFormatting.DARK_PURPLE)
        );

        this.page("ritual", () -> BookRitualRecipePageModel.create()
                .withRecipeId1(this.modLoc("ritual/craft_witherite_dust"))
        );
        //no text
    }

    @Override
    protected String entryName() {
        return "Withertite Dust";
    }

    @Override
    protected String entryDescription() {
        return "A powerful dust for dark rituals";
    }

    @Override
    protected GuiSprite entryBackground() {
        return EntryBackground.DEFAULT;
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(OccultismItems.WITHERITE_DUST);
    }

    @Override
    protected String entryId() {
        return ENTRY_ID;
    }
}
