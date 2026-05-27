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

public class OtherworldWoodEntry extends EntryProvider {

    public static final String ENTRY_ID = "otherworld_wood";

    public OtherworldWoodEntry(CategoryProvider parent) {
        super(parent);
    }

    @Override
    protected void generatePages() {
        this.page("spotlight_sapling", () -> BookSpotlightPageModel.create()
                .withItem(Ingredient.of(OccultismBlocks.OTHERWORLD_SAPLING.get()))
                .withText("Otherworld saplings can be found in underground groves and traded by Otherworld Sapling Trader spirits. Plant them to grow otherworld trees.\n"));

        this.page("spotlight_log", () -> BookSpotlightPageModel.create()
                .withItem(Ingredient.of(OccultismBlocks.OTHERWORLD_LOG.get()))
                .withText("Otherworld logs can be stripped and crafted into otherplanks, the base material for the full otherworld wood set.\n"));

        this.page("wood_set", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText()));
        this.pageTitle("Wood Set");
        this.pageText("The otherworld wood set includes: otherplanks, stairs, slabs, fences, fence gates, doors, trapdoors, buttons, pressure plates, signs, wall signs, hanging signs, wall hanging signs.\n");

        this.page("natural_variants", () -> BookSpotlightPageModel.create()
                .withItem(Ingredient.of(OccultismBlocks.OTHERWORLD_LOG_NATURAL.get()))
                .withText("Natural variants of otherworld wood can be found in underground groves, already placed as part of the generation.\n"));
    }

    @Override
    protected String entryName() {
        return "Otherworld Wood";
    }

    @Override
    protected String entryDescription() {
        return "Wood from the otherworld dimension";
    }

    @Override
    protected GuiSprite entryBackground() {
        return EntryBackground.DEFAULT;
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(OccultismBlocks.OTHERPLANKS.get());
    }

    @Override
    protected String entryId() {
        return ENTRY_ID;
    }
}
