package com.klikli_dev.occultism.datagen.book.getting_started;

import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.EntryBackground;
import com.klikli_dev.modonomicon.api.datagen.EntryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookSpotlightPageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookTextPageModel;
import com.klikli_dev.modonomicon.client.gui.book.theme.GuiSprite;
import com.klikli_dev.occultism.registry.OccultismBlocks;
import net.minecraft.world.item.crafting.Ingredient;

public class OtherstoneBlocksEntry extends EntryProvider {

    public static final String ENTRY_ID = "otherstone_blocks";

    public OtherstoneBlocksEntry(CategoryProvider parent) {
        super(parent);
    }

    @Override
    protected void generatePages() {
        this.page("spotlight_otherstone", () -> BookSpotlightPageModel.create()
                .withItem(Ingredient.of(OccultismBlocks.OTHERSTONE.get()))
                .withText("Otherstone is mined from the overworld mining dimension using Dimensional Mineshafts and miner spirits. It is the primary building material of the mod.\n"));

        this.page("otherstone_variants", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText()));
        this.pageTitle("Otherstone Blocks");
        this.pageText("The otherstone block set includes:\n" +
                "- Otherstone\n" +
                "- Polished Otherstone\n" +
                "- Otherstone Bricks\n" +
                "- Chiseled Otherstone Bricks\n" +
                "- Cracked Otherstone Bricks\n" +
                "- Othercobblestone\n" +
                "- Stairs, Slabs, Walls, Buttons, and Pressure Plates for each variant.\n");

        this.page("spotlight_otherrock", () -> BookSpotlightPageModel.create()
                .withItem(Ingredient.of(OccultismBlocks.OTHERROCK.get()))
                .withText("Otherrock is the nether variant, mined from the nether mining dimension. It has the same block variants as otherstone.\n"));

        this.page("otherrock_variants", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText()));
        this.pageTitle("Otherrock Blocks");
        this.pageText("The otherrock block set includes:\n" +
                "- Otherrock\n" +
                "- Polished Otherrock\n" +
                "- Otherrock Bricks\n" +
                "- Chiseled Otherrock Bricks\n" +
                "- Cracked Otherrock Bricks\n" +
                "- Othercobblerock\n" +
                "- Stairs, Slabs, Walls, Buttons, and Pressure Plates for each variant.\n");

        this.page("obtaining", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText()));
        this.pageTitle("How to obtain");
        this.pageText("Otherstone and otherrock are primarily obtained through dimensional mining using miner spirits in the Dimensional Mineshaft. They can also be produced by Crusher and Smelter spirits from stone and netherrack.\n");
    }

    @Override
    protected String entryName() {
        return "Otherstone and Otherrock";
    }

    @Override
    protected String entryDescription() {
        return "Building blocks from dimensional mining";
    }

    @Override
    protected GuiSprite entryBackground() {
        return EntryBackground.DEFAULT;
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(OccultismBlocks.OTHERSTONE.get());
    }

    @Override
    protected String entryId() {
        return ENTRY_ID;
    }
}
