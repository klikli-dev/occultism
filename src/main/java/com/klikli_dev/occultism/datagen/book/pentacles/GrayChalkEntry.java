package com.klikli_dev.occultism.datagen.book.pentacles;

import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.EntryBackground;
import com.klikli_dev.modonomicon.api.datagen.EntryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookCraftingRecipePageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookSpotlightPageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookTextPageModel;
import com.klikli_dev.occultism.integration.modonomicon.pages.BookRitualRecipePageModel;
import com.klikli_dev.occultism.integration.modonomicon.pages.BookSpiritFireRecipePageModel;
import com.klikli_dev.occultism.registry.OccultismItems;
import com.mojang.datafixers.util.Pair;
import net.minecraft.world.item.crafting.Ingredient;

public class GrayChalkEntry extends EntryProvider {

    public static final String ENTRY_ID = "gray_chalk";


    public GrayChalkEntry(CategoryProvider parent) {
        super(parent);
    }

    @Override
    protected void generatePages() {

        this.page("lore", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText())
        );
        this.pageTitle("Getting Power");
        this.pageText("""
                        The gray chalk is made with one of the magical pastes, making it an intermediate-level
                         foundation chalk. Its magical properties can react in curious ways with the world around it.
                        """
        );

        this.page("spotlight", () -> BookSpotlightPageModel.create()
                .withItem(Ingredient.of(OccultismItems.CHALK_GRAY.get()))
                .withText(this.context().pageText()));
        this.pageText("""
                        As an enhanced foundation chalk, it further strengthens the core of the pentacle and
                         (almost always) can replace white glyphs or light gray glyphs.
                        """
        );

        this.page("ritual", () -> BookRitualRecipePageModel.create()
                .withRecipeId1(this.modLoc("ritual/craft_gray_paste"))
        );

        this.page("paste", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText())
        );
        this.pageTitle("Gray Paste");
        this.pageText("""
                        This strange amorphous paste is mainly used to improve the foundation of your pentacles.
                         But its secondary use can be very useful, some dusts react with portion of this matter
                         and return to their original shape as before being crushed.
                        """
        );

        this.page("recipe_impure", () -> BookCraftingRecipePageModel.create()
                .withRecipeId1(this.modLoc("crafting/chalk_gray_impure"))
        );

        this.page("recipe", () -> BookSpiritFireRecipePageModel.create()
                .withRecipeId1(this.modLoc("spirit_fire/chalk_gray"))
        );

    }

    @Override
    protected String entryName() {
        return "Improved Foundation Chalk";
    }

    @Override
    protected String entryDescription() {
        return "Gray Chalk";
    }

    @Override
    protected Pair<Integer, Integer> entryBackground() {
        return EntryBackground.DEFAULT;
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(OccultismItems.CHALK_GRAY.get());
    }

    @Override
    protected String entryId() {
        return ENTRY_ID;
    }
}
