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

public class SpiritFireEntry extends EntryProvider {

    public static final String ENTRY_ID = "spirit_fire";

    public SpiritFireEntry(CategoryProvider parent) {
        super(parent);
    }

    @Override
    protected void generatePages() {
        this.page("spotlight", () -> BookSpotlightPageModel.create()
                .withItem(Ingredient.of(OccultismItems.SPIRIT_FIRE.get()))
                .withText(this.context().pageText()));
        this.pageText("[#](ad03fc)Spiritfire[#]() is a special type of fire that exists mostly in [#](ad03fc)The Other Place[#]()\nand does not harm living beings. Its special properties allow to use it to purify and convert\ncertain materials by burning them, without consuming them.\n");

        this.page("spirit_fire_screenshot", () -> BookImagePageModel.create()
                .withImages(this.modLoc("textures/gui/book/spiritfire_instructions.png"))
                .withText(this.context().pageText()));
        this.pageText("Throw [](item://occultism:datura) to the ground and light it on fire with [](item://minecraft:flint_and_steel).\n");

        this.page("main_uses", () -> BookTextPageModel.create()
                .withText(this.context().pageText()));
        this.pageText("The main uses of [](item://occultism:spirit_fire) are to convert [](item://minecraft:diamond) into [](item://occultism:spirit_attuned_gem),\nto get basic ingredients such as [](item://occultism:otherstone) and [Otherworld Saplings](item://occultism:otherworld_sapling_natural),\nand to purify impure chalks.\n");

        this.page("otherstone_recipe", () -> BookSpiritFireRecipePageModel.create()
                .withRecipeId1(this.modLoc("spirit_fire/otherstone"))
                .withText(this.context().pageText()));
        this.pageText("An easier way to obtain [](item://occultism:otherstone) than via divination.\n");

        this.page("otherrock_recipe", () -> BookSpiritFireRecipePageModel.create()
                .withRecipeId1(this.modLoc("spirit_fire/otherrock"))
                .withText(this.context().pageText()));
        this.pageText("Otherrock is a variation of [](item://occultism:otherstone),\n you can use it for decoration and making sacrificial bowls,\n  but it does not work as a base for chalks or dimensional storage.\n");

        this.page("otherworld_sapling_natural_recipe", () -> BookSpiritFireRecipePageModel.create()
                .withRecipeId1(this.modLoc("spirit_fire/otherworld_sapling_natural"))
                .withText(this.context().pageText()));
        this.pageText("An easier way to obtain [Otherworld Saplings](item://occultism:otherworld_sapling_natural) than via divination.\n");

        this.page("otherworld_ashes_recipe", () -> BookSpiritFireRecipePageModel.create()
                .withRecipeId1(this.modLoc("spirit_fire/otherworld_ashes")));

        this.page("gem_recipe", () -> BookSpiritFireRecipePageModel.create()
                .withRecipeId1(this.modLoc("spirit_fire/spirit_attuned_gem")));

        this.page("otherflower_recipe", () -> BookSpiritFireRecipePageModel.create()
                .withRecipeId1(this.modLoc("spirit_fire/otherflower"))
                .withText(this.context().pageText()));
        this.pageText("An easier way to clone any dye, mix this flower and the target color. You can also make ~~suspicious~~ delicious stews.\n");
    }

    @Override
    protected String entryName() {
        return "It burns!";
    }

    @Override
    protected String entryDescription() {
        return "Or does it?";
    }

    @Override
    protected GuiSprite entryBackground() {
        return EntryBackground.DEFAULT;
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(OccultismItems.SPIRIT_FIRE.get());
    }

    @Override
    protected String entryId() {
        return ENTRY_ID;
    }
}
