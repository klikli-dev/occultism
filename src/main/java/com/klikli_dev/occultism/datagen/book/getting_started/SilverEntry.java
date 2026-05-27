package com.klikli_dev.occultism.datagen.book.getting_started;

import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.EntryBackground;
import com.klikli_dev.modonomicon.api.datagen.EntryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookSpotlightPageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookTextPageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookSmeltingRecipePageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookCraftingRecipePageModel;
import com.klikli_dev.modonomicon.client.gui.book.theme.GuiSprite;
import com.klikli_dev.occultism.registry.OccultismItems;
import com.klikli_dev.occultism.registry.OccultismBlocks;
import net.minecraft.world.item.crafting.Ingredient;

public class SilverEntry extends EntryProvider {

    public static final String ENTRY_ID = "silver";

    public SilverEntry(CategoryProvider parent) {
        super(parent);
    }

    @Override
    protected void generatePages() {
        this.page("spotlight_ore", () -> BookSpotlightPageModel.create()
                .withItem(Ingredient.of(OccultismBlocks.SILVER_ORE.get()))
                .withText(this.context().pageText()));
        this.pageText("Silver ore is found underground and can be smelted into silver ingots. It is a key crafting material for many mod recipes.\n");

        this.page("spotlight_ingot", () -> BookSpotlightPageModel.create()
                .withItem(Ingredient.of(OccultismItems.SILVER_INGOT.get()))
                .withText(this.context().pageText()));
        this.pageText("Silver ingots are used in satchels, soul gems, infused pickaxes, and many other recipes.\n");

        this.page("smelting", () -> BookSmeltingRecipePageModel.create()
                .withRecipeId1(this.modLoc("smelting/silver_ingot")));

        this.page("block", () -> BookCraftingRecipePageModel.create()
                .withRecipeId1(this.modLoc("crafting/silver_block")));

        this.page("uses", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText()));
        this.pageTitle("Uses");
        this.pageText("Silver is used in many recipes throughout the mod, including:\n\\\n\\\n" +
                "- **Satchels** for storing and transporting items\n" +
                "- **Soul Gems** for capturing and storing souls\n" +
                "- **Infused Pickaxe** for mining special ores\n" +
                "- **Ritual Satchels** for automating rituals\n" +
                "- **Familiar Rings** for binding familiars\n" +
                "- And many more!\n\\\n\\\n" +
                "Silver can also be obtained from **crusher** spirit jobs.\n");
    }

    @Override
    protected String entryName() {
        return "Silver";
    }

    @Override
    protected String entryDescription() {
        return "Mod ore and crafting material";
    }

    @Override
    protected GuiSprite entryBackground() {
        return EntryBackground.DEFAULT;
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(OccultismItems.SILVER_INGOT.get());
    }

    @Override
    protected String entryId() {
        return ENTRY_ID;
    }
}
