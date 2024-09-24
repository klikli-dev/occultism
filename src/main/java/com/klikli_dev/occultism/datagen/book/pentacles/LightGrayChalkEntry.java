package com.klikli_dev.occultism.datagen.book.pentacles;

import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.EntryBackground;
import com.klikli_dev.modonomicon.api.datagen.EntryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookCraftingRecipePageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookSpotlightPageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookTextPageModel;
import com.klikli_dev.occultism.integration.modonomicon.pages.BookSpiritFireRecipePageModel;
import com.klikli_dev.occultism.registry.OccultismItems;
import com.mojang.datafixers.util.Pair;
import net.minecraft.world.item.crafting.Ingredient;

public class LightGrayChalkEntry extends EntryProvider {

    public static final String ENTRY_ID = "light_gray_chalk";


    public LightGrayChalkEntry(CategoryProvider parent) {
        super(parent);
    }

    @Override
    protected void generatePages() {

        this.page("lore", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText())
        );
        this.pageTitle("Most Common Foundation");
        this.pageText("""
                        The light gray chalk uses mineral compounds to enhance the foundation of a pentagram.
                         The foundation is an important property, often considered the stabilization of the "core."
                        """
        );

        this.page("spotlight", () -> BookSpotlightPageModel.create()
                .withItem(Ingredient.of(OccultismItems.CHALK_LIGHT_GRAY.get()))
                .withText(this.context().pageText()));
        this.pageText("""
                        Due to its relatively low cost and inherent magical properties, it is the most common
                         foundation option among practitioners of occultism, capable of replacing white chalk
                         in almost all pentagrams.
                        """
        );

        this.page("recipe_impure", () -> BookCraftingRecipePageModel.create()
                .withRecipeId1(this.modLoc("crafting/chalk_light_gray_impure"))
        );

        this.page("recipe", () -> BookSpiritFireRecipePageModel.create()
                .withRecipeId1(this.modLoc("spirit_fire/chalk_light_gray"))
        );

    }

    @Override
    protected String entryName() {
        return "Decent Foundation Chalk";
    }

    @Override
    protected String entryDescription() {
        return "Light Gray Chalk";
    }

    @Override
    protected Pair<Integer, Integer> entryBackground() {
        return EntryBackground.DEFAULT;
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(OccultismItems.CHALK_LIGHT_GRAY.get());
    }

    @Override
    protected String entryId() {
        return ENTRY_ID;
    }
}
