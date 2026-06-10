package com.klikli_dev.occultism.datagen.book.getting_started;

import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.EntryBackground;
import com.klikli_dev.modonomicon.api.datagen.EntryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookCraftingRecipePageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookSpotlightPageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookTextPageModel;
import com.klikli_dev.modonomicon.client.gui.book.theme.GuiSprite;
import com.klikli_dev.occultism.registry.OccultismBlocks;
import com.klikli_dev.occultism.registry.OccultismItems;
import net.minecraft.world.item.crafting.Ingredient;

public class DaturaEntry extends EntryProvider {

    public static final String ENTRY_ID = "datura";

    public DaturaEntry(CategoryProvider parent) {
        super(parent);
    }

    @Override
    protected void generatePages() {
        this.page("spotlight_seeds", () -> BookSpotlightPageModel.create()
                .withItem(Ingredient.of(OccultismItems.DATURA_SEEDS.get()))
                .withText(this.context().pageText()));
        this.pageText("Datura is a crop that can be grown like wheat. It has multiple uses in the mod.\n");

        this.page("spotlight_crop", () -> BookSpotlightPageModel.create()
                .withItem(Ingredient.of(OccultismItems.DATURA.get()))
                .withText(this.context().pageText()));
        this.pageText("When the Datura crop reaches maturity, the full-grown plant drops datura. Harvest it before it dies to collect.\n");

        this.page("healing", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText()));
        this.pageTitle("Healing Spirits");
        this.pageText("Right-click a spirit with datura to heal it. Works on Familiars, Summoned Spirits, and Possessed Mobs.\n");

        this.page("spirit_fire", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText()));
        this.pageTitle("Spirit Fire Fuel");
        this.pageText("Throw datura to the ground and light it on fire with flint and steel to create a spirit fire.\n");

        this.page("crafting", () -> BookCraftingRecipePageModel.create()
                .withRecipeId1(this.modLoc("crafting/datura_seeds")));

        this.page("crushing", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText()));
        this.pageTitle("Crushing");
        this.pageText("Datura can be crushed by a Crusher spirit to produce seeds for replanting.\n");

        this.page("deco_lighting", () -> BookSpotlightPageModel.create()
                .withItem(Ingredient.of(OccultismBlocks.SPIRIT_LANTERN.get()))
                .withText(this.context().pageText()));
        this.pageText("Light up your space with [](block://occultism:spirit_lantern), [](block://occultism:spirit_campfire), and [](block://occultism:spirit_torch) -- decorative lighting made from [](item://occultism:datura) crop.\n");

        this.page("deco_lighting_recipes", () -> BookCraftingRecipePageModel.create()
                .withRecipeId1(this.modLoc("crafting/spirit_lantern"))
                .withRecipeId2(this.modLoc("crafting/spirit_campfire")));

        this.page("deco_lighting_recipe_torch", () -> BookCraftingRecipePageModel.create()
                .withRecipeId1(this.modLoc("crafting/spirit_torch")));
    }

    @Override
    protected String entryName() {
        return "Datura";
    }

    @Override
    protected String entryDescription() {
        return "Healing crop for spirits and rituals";
    }

    @Override
    protected GuiSprite entryBackground() {
        return EntryBackground.DEFAULT;
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(OccultismItems.DATURA_SEEDS.get());
    }

    @Override
    protected String entryId() {
        return ENTRY_ID;
    }
}
