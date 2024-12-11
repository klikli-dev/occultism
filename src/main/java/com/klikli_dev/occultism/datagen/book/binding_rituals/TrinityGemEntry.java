package com.klikli_dev.occultism.datagen.book.binding_rituals;

import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.EntryBackground;
import com.klikli_dev.modonomicon.api.datagen.EntryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookSpotlightPageModel;
import com.klikli_dev.occultism.integration.modonomicon.pages.BookRitualRecipePageModel;
import com.klikli_dev.occultism.registry.OccultismBlocks;
import com.klikli_dev.occultism.registry.OccultismItems;
import com.mojang.datafixers.util.Pair;
import net.minecraft.ChatFormatting;
import net.minecraft.world.item.crafting.Ingredient;

public class TrinityGemEntry extends EntryProvider {

    public static final String ENTRY_ID = "trinity_gem";


    public TrinityGemEntry(CategoryProvider parent) {
        super(parent);
    }

    @Override
    protected void generatePages() {
        this.page("spotlight", () -> BookSpotlightPageModel.create()
                .withItem(Ingredient.of(OccultismItems.TRINITY_GEM_ITEM))
                .withText(this.context().pageText()));
        this.pageText("""
                        Forging the [](item://occultism:trinity_gem) is a service provided by an {0}.
                        This gem is upgraded version of [](item://occultism:soul_gem) created with
                        3 distinct essences, 3 powerful dusts and 3 iesnium dusts.
                        The trinity gem has a smaller entity blacklist (none by default), but some mobs might still be
                        incompatible like the ender dragon.
                        """,
                this.color("Eldritch Spirit", ChatFormatting.DARK_PURPLE)
        );

        this.page("ritual", () -> BookRitualRecipePageModel.create()
                .withRecipeId1(this.modLoc("ritual/misc_trinity_gem"))
        );
        //no text
    }

    @Override
    protected String entryName() {
        return "Trinity Gem";
    }

    @Override
    protected String entryDescription() {
        return "Really catch all!";
    }

    @Override
    protected Pair<Integer, Integer> entryBackground() {
        return EntryBackground.DEFAULT;
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(OccultismItems.TRINITY_GEM_ITEM);
    }

    @Override
    protected String entryId() {
        return ENTRY_ID;
    }
}
