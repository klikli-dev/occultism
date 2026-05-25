package com.klikli_dev.occultism.datagen.book.getting_started;

import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.EntryBackground;
import com.klikli_dev.modonomicon.api.datagen.EntryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookEntryModel;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.*;
import com.klikli_dev.modonomicon.client.gui.book.theme.GuiSprite;
import com.klikli_dev.occultism.integration.modonomicon.pages.BookBindingCraftingRecipePageModel;
import com.klikli_dev.occultism.integration.modonomicon.pages.BookRitualRecipePageModel;
import com.klikli_dev.occultism.integration.modonomicon.pages.BookSpiritFireRecipePageModel;
import com.klikli_dev.occultism.registry.OccultismBlocks;
import com.klikli_dev.occultism.registry.OccultismItems;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

public class RitualPrepBowlEntry extends EntryProvider {

    public static final String ENTRY_ID = "ritual_prep_bowl";

    public RitualPrepBowlEntry(CategoryProvider parent) {
        super(parent);
    }

    @Override
    protected void generatePages() {
        this.page("sacrificial_bowl", () -> BookSpotlightPageModel.create()
                .withItem(Ingredient.of(OccultismBlocks.SACRIFICIAL_BOWL.get()))
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText()));
        this.pageTitle("Ritual Preparations: Sacrificial Bowls");
        this.pageText("These bowls are used to place the items we will sacrifice as part of a ritual and you will need a handful of them.\nNote: Their exact placement in the ritual does not matter - just keep them within 8 blocks horizontally of the pentacle center!\n");

        this.page("sacrificial_bowl_recipe", () -> BookCraftingRecipePageModel.create()
                .withRecipeId1(this.modLoc("crafting/sacrificial_bowl"))
                .withText(this.context().pageText()));
        this.pageText("You can mix a sacrificial bowl with a copper or silver ingot to create variations with the same functionality.\n");

        this.page("decorative_sacrificial_bowl_recipe", () -> BookCraftingRecipePageModel.create()
                .withRecipeId1(this.modLoc("crafting/copper_sacrificial_bowl"))
                .withRecipeId2(this.modLoc("crafting/silver_sacrificial_bowl")));

        this.page("spirit_bowl", () -> BookSpotlightPageModel.create()
                .withItem(Ingredient.of(OccultismBlocks.SPIRIT_FIRE.get()))
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText()));
        this.pageTitle("Bowls and Spirit Fire");
        this.pageText("If you place a sacrificial bowl above a Spirit Fire or Spirit Campfire,\nany item inserted will instantly transform if it has a recipe in the spirit fire.\\\nAlso work with copper or silver version of sacrificial bowl.\n");

        this.page("golden_sacrificial_bowl", () -> BookSpotlightPageModel.create()
                .withItem(Ingredient.of(OccultismBlocks.GOLDEN_SACRIFICIAL_BOWL.get()))
                .withText(this.context().pageText()));
        this.pageText("Once everything has been set up and you are ready to start, this special ritual bowl is used to activate the ritual by [#](ad03fc)right-clicking[#]() it with the activation item,\nusually a [Book of Binding](entry://occultism:dictionary_of_spirits/getting_started/books_of_binding).\n");

        this.page("golden_sacrificial_bowl_recipe", () -> BookCraftingRecipePageModel.create()
                .withRecipeId1(this.modLoc("crafting/golden_sacrificial_bowl")));
    }

    @Override
    protected String entryName() {
        return "Ritual Preparations: Sacrificial Bowls";
    }

    @Override
    protected String entryDescription() {
        return "There is no power without sacrifice.";
    }

    @Override
    protected GuiSprite entryBackground() {
        return EntryBackground.DEFAULT;
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(OccultismBlocks.GOLDEN_SACRIFICIAL_BOWL.get());
    }

    @Override
    protected String entryId() {
        return ENTRY_ID;
    }
}
