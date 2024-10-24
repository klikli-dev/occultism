package com.klikli_dev.occultism.datagen.book.pentacles;

import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.EntryBackground;
import com.klikli_dev.modonomicon.api.datagen.EntryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookCraftingRecipePageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookSpotlightPageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookTextPageModel;
import com.klikli_dev.occultism.integration.modonomicon.pages.BookSpiritFireRecipePageModel;
import com.klikli_dev.occultism.registry.OccultismBlocks;
import com.mojang.datafixers.util.Pair;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Blocks;

public class ParaphernaliaEntry extends EntryProvider {

    public static final String ENTRY_ID = "paraphernalia";


    public ParaphernaliaEntry(CategoryProvider parent) {
        super(parent);
    }

    @Override
    protected void generatePages() {

        this.page("intro", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText()));
        this.pageTitle("Paraphernalia");
        this.pageText("""
                In addition to runes various occult paraphernalia are used to improve the intended effect of the pentacle.
                """
        );

        this.page("candle", () -> BookSpotlightPageModel.create()
                .withItem(Ingredient.of(OccultismBlocks.LARGE_CANDLE.asItem()))
                .withText(this.context().pageText()));
        this.pageText("""
                Candles increase the stability of the pentacle, thus allowing a slowed essence decay of the summoned
                 spirit, leading to a longer lifetime of the spirit, or possessed object or being.
                """
        );

        this.page("skull", () -> BookSpotlightPageModel.create()
                .withItem(Ingredient.of(Items.SKELETON_SKULL))
                .withText(this.context().pageText()));
        this.pageText("""
                Skulls increase the calling power of the pentacle, allowing to summon more dangerous spirits.\\
                \\
                Occultists can find an easy way to obtain these skulls using basic possession rituals.
                """
        );

        this.page("crystal", () -> BookSpotlightPageModel.create()
                .withItem(Ingredient.of(OccultismBlocks.SPIRIT_ATTUNED_CRYSTAL.asItem()))
                .withText(this.context().pageText()));
        this.pageText("""
                Crystals increase the stability of the pentacle, at levels that candles would not reach, allowing a performing more unstable rituals.\\
                \\
                Check the recipes in next page.
                """
        );

        this.page("recipe_gem", () -> BookSpiritFireRecipePageModel.create()
                .withRecipeId1(this.modLoc("spirit_fire/spirit_attuned_gem"))
        );

        this.page("recipe_crystal", () -> BookCraftingRecipePageModel.create()
                .withRecipeId1(this.modLoc("crafting/spirit_attuned_crystal"))
        );

        this.page("wither_skull", () -> BookSpotlightPageModel.create()
                .withItem(Items.WITHER_SKELETON_SKULL)
                .withText(this.context().pageText()));
        this.pageText("""
                Whiter Skulls are stronger than regular Skull, and increase a lot the calling power of the pentacle,
                 allowing to summon more powerful class of spirits.\\
                \\
                Occultists can find an easy way to obtain these skulls using wild rituals.
                """
        );

    }

    @Override
    protected String entryName() {
        return "Occult Paraphernalia";
    }

    @Override
    protected String entryDescription() {
        return "Stability and Power";
    }

    @Override
    protected Pair<Integer, Integer> entryBackground() {
        return EntryBackground.DEFAULT;
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(Blocks.SKELETON_SKULL);
    }

    @Override
    protected String entryId() {
        return ENTRY_ID;
    }
}
