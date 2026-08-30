package com.klikli_dev.occultism.datagen.book.getting_started;

import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.EntryBackground;
import com.klikli_dev.modonomicon.api.datagen.EntryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookCraftingRecipePageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookSpotlightPageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookTextPageModel;
import com.klikli_dev.modonomicon.client.gui.book.theme.GuiSprite;
import com.klikli_dev.occultism.integration.modonomicon.pages.BookSpiritFireRecipePageModel;
import com.klikli_dev.occultism.registry.OccultismItems;
import net.minecraft.world.item.crafting.Ingredient;

public class InfusedPickaxeEntry extends EntryProvider {

    public static final String ENTRY_ID = "infused_pickaxe";

    public InfusedPickaxeEntry(CategoryProvider parent) {
        super(parent);
    }

    @Override
    protected void generatePages() {
        this.page("spotlight", () -> BookSpotlightPageModel.create()
                .withItem(Ingredient.of(OccultismItems.INFUSED_PICKAXE.get()))
                .withText(this.context().pageText()));
        this.pageText("Beyond [](item://occultism:otherworld_log) and [](item://occultism:otherstone) there are also otherworld materials that require special tools to harvest.\n\\\n\\\nThis pickaxe is rather brittle, but it will do the job.\n");

        this.page("gem_recipe", () -> BookSpiritFireRecipePageModel.create()
                .withRecipeId1(this.modLoc("spirit_fire/spirit_attuned_gem"))
                .withText(this.context().pageText()));
        this.pageText("These gems, when infused with a spirit, can be used to interact with Otherword materials and are the key to crafting the pickaxe.\n");

        this.page("head_recipe", () -> BookCraftingRecipePageModel.create()
                .withRecipeId1(this.modLoc("crafting/spirit_attuned_pickaxe_head")));

        this.page("crafting", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText()));
        this.pageTitle("Crafting");
        this.pageText("After preparing the raw materials, the pickaxe needs to be infused with a spirit.\n\\\n\\\nFollow the instructions at [Craft Infuse Pickaxe](entry://occultism:dictionary_of_spirits/crafting_rituals/craft_infused_tools)\n");
    }

    @Override
    protected String entryName() {
        return "Infused Pickaxe";
    }

    @Override
    protected String entryDescription() {
        return "Tackling Otherworld Ores";
    }

    @Override
    protected GuiSprite entryBackground() {
        return EntryBackground.DEFAULT;
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(OccultismItems.INFUSED_PICKAXE.get());
    }

    @Override
    protected String entryId() {
        return ENTRY_ID;
    }
}
