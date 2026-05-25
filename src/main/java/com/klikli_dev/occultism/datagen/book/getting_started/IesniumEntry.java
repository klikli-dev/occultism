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

public class IesniumEntry extends EntryProvider {

    public static final String ENTRY_ID = "iesnium";

    public IesniumEntry(CategoryProvider parent) {
        super(parent);
    }

    @Override
    protected void generatePages() {
        this.page("spotlight", () -> BookSpotlightPageModel.create()
                .withItem(Ingredient.of(OccultismBlocks.IESNIUM_ORE.get()))
                .withText(this.context().pageText()));
        this.pageText("This is a rare metal that, to the naked eye, looks like [](item://minecraft:netherrack) and cannot be mined with a regular pickaxe.\n\\\n\\\nWhen mined with the correct tools, it can be used to craft powerful items (you will learn more about that later).\n");

        this.page("where", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText()));
        this.pageTitle("Where to find it");
        this.pageText("Like Netherrack, Iesnium can be found in the Nether. In order to **see** it, you need to wear [Otherworld Goggles](entry://occultism:dictionary_of_spirits/getting_started/otherworld_goggles).\n\\\n\\\nTo make searching for it simpler, attune a [Divination Rod](entry://occultism:dictionary_of_spirits/getting_started/divination_rod) to it and righ-click and hold in the nether until it highlights a nearby block, which will hold the ore.\n");

        this.page("how", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText()));
        this.pageTitle("How to mine it");
        this.pageText("Iesnium can only be mined with the [Infused Pickaxe](entry://occultism:dictionary_of_spirits/getting_started/infused_pickaxe) or an [](item://occultism:iesnium_pickaxe) (about which you will learn later).\n\\\n\\\nAfter identifying a block that holds Iesnium, you can mine it with the pickaxe you created in the previous step.\n");

        this.page("processing", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText()));
        this.pageTitle("Processing");
        this.pageText("Iesnium ore, when mined, will drop [](item://occultism:raw_iesnium) that can be smelted directly into ingots.\nLike common ores, this is affected by Fortune and Silk Touch. If mined with silk, it will drop\n a stabilized version of Iesnium Ore, which can be mined with any pickaxe when placed back on the ground.\n");

        this.page("uses", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText()));
        this.pageTitle("Uses");
        this.pageText("Iesnium can be used to craft an improved pickaxe, spirit lamps, and other powerful items. Follow the progress in this book to learn more about it.\n");

        this.page("otherglass", () -> BookCraftingRecipePageModel.create()
                .withRecipeId1(this.modLoc("crafting/otherglass"))
                .withText(this.context().pageText()));
        this.pageText("One of the uses of iesnium is the creation of Otherglass, this block hides from common eyes and is revealed only to those who see the other world. To collect this you need an infused or iesnium pickaxe.\n");
    }

    @Override
    protected String entryName() {
        return "Iesnium Ore";
    }

    @Override
    protected String entryDescription() {
        return "Myterious metals ...";
    }

    @Override
    protected GuiSprite entryBackground() {
        return EntryBackground.DEFAULT;
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(OccultismBlocks.IESNIUM_ORE.get());
    }

    @Override
    protected String entryId() {
        return ENTRY_ID;
    }
}
