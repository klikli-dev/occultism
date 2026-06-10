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

public class CandleEntry extends EntryProvider {

    public static final String ENTRY_ID = "candle";

    public CandleEntry(CategoryProvider parent) {
        super(parent);
    }

    @Override
    protected void generatePages() {
        this.page("intro", () -> BookSpotlightPageModel.create()
                .withItem(Ingredient.of(OccultismBlocks.LARGE_CANDLE.get()))
                .withText(this.context().pageText()));
        this.pageText("Candles provide stability to rituals and are an important part of almost all pentacles.\n**Large Candles also act like bookshelves for enchantment purposes.**\n\\\n\\\nCandles from Minecraft and other Mods may be used in place of Occultism candles.\n");

        this.page("tallow", () -> BookSpotlightPageModel.create()
                .withItem(Ingredient.of(OccultismItems.TALLOW.get()))
                .withText(this.context().pageText()));
        this.pageText("Key ingredient for large candles. Kill large animals like pigs, cows or sheep with a [](item://occultism:butcher_knife)\nto harvest [](item://occultism:tallow).\n");

        this.page("cleaver_recipe", () -> BookCraftingRecipePageModel.create()
                .withRecipeId1(this.modLoc("crafting/butcher_knife")));

        this.page("candle_recipe", () -> BookCraftingRecipePageModel.create()
                .withRecipeId1(this.modLoc("crafting/large_candle")));

        this.page("color_candle", () -> BookTextPageModel.create()
                .withText(this.context().pageText()));
        this.pageText("You can use a dye and the [](item://occultism:large_candle) to mix then in shapeless craft process to get a colored large candle.\n\\\nAvailable in all the 16 minecraft dyes.\n");

        this.page("lit_candle", () -> BookTextPageModel.create()
                .withText(this.context().pageText()));
        this.pageText("Just like the candles from Minecraft, [](item://occultism:large_candle) and colored versions can be lit, turning in a great light source.\n\\\nIn addition, you can use a [](item://minecraft:torch), [](item://minecraft:soul_torch), [](item://minecraft:copper_torch), [](item://minecraft:redstone_torch) or [](item://occultism:spirit_torch) to change the type of fire.\n\\\nAlso can be waterlogged.\n");
    }

    @Override
    protected String entryName() {
        return "Candles";
    }

    @Override
    protected String entryDescription() {
        return "Let there be light!";
    }

    @Override
    protected GuiSprite entryBackground() {
        return EntryBackground.DEFAULT;
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(OccultismBlocks.LARGE_CANDLE.get());
    }

    @Override
    protected String entryId() {
        return ENTRY_ID;
    }
}
