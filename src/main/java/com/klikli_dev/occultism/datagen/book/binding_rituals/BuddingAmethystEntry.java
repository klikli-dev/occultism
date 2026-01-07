package com.klikli_dev.occultism.datagen.book.binding_rituals;

import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.EntryBackground;
import com.klikli_dev.modonomicon.api.datagen.EntryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookSpotlightPageModel;
import com.klikli_dev.occultism.integration.modonomicon.pages.BookRitualRecipePageModel;
import com.mojang.datafixers.util.Pair;
import net.minecraft.ChatFormatting;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

public class BuddingAmethystEntry extends EntryProvider {

    public static final String ENTRY_ID = "craft_budding_amethyst";

    public BuddingAmethystEntry(CategoryProvider parent) {
        super(parent);
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(Items.BUDDING_AMETHYST);
    }

    @Override
    protected String entryName() {
        return "Forge Budding Amethyst";
    }

    @Override
    protected String entryDescription() {
        return "So shiny";
    }

    @Override
    protected void generatePages() {
        this.page("spotlight", () -> BookSpotlightPageModel.create()
                .withItem(Ingredient.of(Items.BUDDING_AMETHYST))
                .withText(this.context().pageText()));
        this.pageText("""
                        Unlike other rituals, creating a {0} is a service provided by {1} and not bound any spirit to the
                         final object. You sacrifice the items and the Wild Spirits uses his power to forge that item for you.
                        """,
                this.itemLink(Items.BUDDING_AMETHYST),
                this.color("Wild Spirits", ChatFormatting.DARK_PURPLE)
        );

        this.page("ritual", () -> BookRitualRecipePageModel.create()
                .withRecipeId1(this.modLoc("ritual/misc_budding_amethyst"))
        );
        //no text
    }

    @Override
    protected Pair<Integer, Integer> entryBackground() {
        return EntryBackground.DEFAULT;
    }

    @Override
    protected String entryId() {
        return ENTRY_ID;
    }
}
