package com.klikli_dev.occultism.datagen.book.getting_started;

import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.EntryBackground;
import com.klikli_dev.modonomicon.api.datagen.EntryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookCraftingRecipePageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookSpotlightPageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookTextPageModel;
import com.klikli_dev.modonomicon.client.gui.book.theme.GuiSprite;
import com.klikli_dev.occultism.registry.OccultismItems;
import net.minecraft.world.item.crafting.Ingredient;

public class PitayaEntry extends EntryProvider {

    public static final String ENTRY_ID = "pitaya";

    public PitayaEntry(CategoryProvider parent) {
        super(parent);
    }

    @Override
    protected void generatePages() {
        this.page("spotlight", () -> BookSpotlightPageModel.create()
                .withItem(Ingredient.of(OccultismItems.PITAYA.get()))
                .withText(this.context().pageText()));
        this.pageText("Pitaya is a fruit found in the otherworld dimension and underground groves. It can be eaten for hunger restoration or crafted into better food.\n");

        this.page("golden", () -> BookCraftingRecipePageModel.create()
                .withRecipeId1(this.modLoc("crafting/golden_pitaya"))
                .withText(this.context().pageText()));
        this.pageText("Golden pitaya provides enhanced hunger and saturation restoration.\n");

        this.page("enchanted", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText()));
        this.pageTitle("Enchanted Golden Pitaya");
        this.pageText("Craft golden pitaya with enchanted golden apples to create enchanted golden pitaya, the highest-tier food in the mod. Provides absorption and regeneration effects.\n");
    }

    @Override
    protected String entryName() {
        return "Pitaya";
    }

    @Override
    protected String entryDescription() {
        return "Otherworld fruit and food";
    }

    @Override
    protected GuiSprite entryBackground() {
        return EntryBackground.DEFAULT;
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(OccultismItems.PITAYA.get());
    }

    @Override
    protected String entryId() {
        return ENTRY_ID;
    }
}
