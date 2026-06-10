package com.klikli_dev.occultism.datagen.book.getting_started;

import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.EntryBackground;
import com.klikli_dev.modonomicon.api.datagen.EntryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookCraftingRecipePageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookSpotlightPageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookTextPageModel;
import com.klikli_dev.modonomicon.client.gui.book.theme.GuiSprite;
import com.klikli_dev.theurgy.registry.ItemRegistry;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

public class TheurgyDivinationRodsEntry extends EntryProvider {

    public static final String ENTRY_ID = "theurgy_divination_rod";

    public TheurgyDivinationRodsEntry(CategoryProvider parent) {
        super(parent);
    }

    @Override
    protected void generatePages() {
        this.page("intro", () -> BookSpotlightPageModel.create()
                .withItem(Ingredient.of(ItemRegistry.DIVINATION_ROD_T1.get()))
                .withText(this.context().pageText()));
        this.pageText("While the [](item://occultism:divination_rod) is a great tool for finding [#](ad03fc)Otherworld Materials[#](), it would be useful to have a way to find *all other* ores and resources as well.\n\\\n\\\nThis is where the Theurgy Divination Rod comes in.\n");

        this.page("recipe_rod", () -> BookCraftingRecipePageModel.create()
                .withRecipeId1("theurgy:crafting/shaped/divination_rod_t1"));

        this.page("more_info", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText()));
        this.pageTitle("More Information");
        this.pageText("To find out more about the Theurgy Divination Rod, check out *\"The Hermetica\"*, the Guidebook for Theurgy.\n[This Entry](entry://theurgy:the_hermetica/getting_started/about_divination_rods) has more information about the Theurgy Divination Rod.\n");

        this.page("recipe_hermetica", () -> BookCraftingRecipePageModel.create()
                .withRecipeId1("theurgy:crafting/shapeless/the_hermetica"));
    }

    @Override
    protected String entryName() {
        return "More Divination Rods";
    }

    @Override
    protected String entryDescription() {
        return "Finding other ores and resources.";
    }

    @Override
    protected GuiSprite entryBackground() {
        return EntryBackground.DEFAULT;
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(Items.STICK);
    }

    @Override
    protected String entryId() {
        return ENTRY_ID;
    }
}
