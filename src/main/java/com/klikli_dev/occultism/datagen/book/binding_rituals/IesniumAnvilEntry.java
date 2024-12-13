package com.klikli_dev.occultism.datagen.book.binding_rituals;

import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.EntryBackground;
import com.klikli_dev.modonomicon.api.datagen.EntryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookSpotlightPageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookTextPageModel;
import com.klikli_dev.occultism.integration.modonomicon.pages.BookRitualRecipePageModel;
import com.klikli_dev.occultism.registry.OccultismBlocks;
import com.mojang.datafixers.util.Pair;
import net.minecraft.ChatFormatting;
import net.minecraft.world.item.crafting.Ingredient;

public class IesniumAnvilEntry extends EntryProvider {

    public static final String ENTRY_ID = "craft_iesnium_anvil";


    public IesniumAnvilEntry(CategoryProvider parent) {
        super(parent);
    }

    @Override
    protected void generatePages() {
        this.page("spotlight", () -> BookSpotlightPageModel.create()
                .withItem(Ingredient.of(OccultismBlocks.IESNIUM_ANVIL.asItem()))
                .withText(this.context().pageText()));
        this.pageText("""
                        The [](item://occultism:iesnium_anvil) is a {0} infusion.
                        This anvil has some improvements:
                        1. Is unbreakable;
                        2. Can exceed the maximum level of enchantments by 1;
                        3. Marid will pay half of the showed level cost (round up);
                        4. The cost increase of working with the same item is reduced;
                        5. The maximum cost limit is increased;
                        """,
                this.color("Marid", ChatFormatting.DARK_PURPLE)
        );

        this.page("ritual", () -> BookRitualRecipePageModel.create()
                .withRecipeId1(this.modLoc("ritual/craft_iesnium_anvil"))
        );
        //no text

        this.page("apothic", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText()));
        this.pageTitle("Apotheosis Information");
        this.pageText("""
                        When using {0} you can get all enchantments at level 10 instead of one level higher than the maximum
                        """,
                this.color("Apothic Enchanting Mod", ChatFormatting.DARK_PURPLE)
        );
    }

    @Override
    protected String entryName() {
        return "Iesnium Anvil";
    }

    @Override
    protected String entryDescription() {
        return "Upgraded Anvil";
    }

    @Override
    protected Pair<Integer, Integer> entryBackground() {
        return EntryBackground.DEFAULT;
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(OccultismBlocks.IESNIUM_ANVIL.asItem());
    }

    @Override
    protected String entryId() {
        return ENTRY_ID;
    }
}
