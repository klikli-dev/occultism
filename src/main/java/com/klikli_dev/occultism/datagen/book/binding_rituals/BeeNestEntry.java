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

public class BeeNestEntry extends EntryProvider {

    public static final String ENTRY_ID = "bee_nest";


    public BeeNestEntry(CategoryProvider parent) {
        super(parent);
    }

    @Override
    protected void generatePages() {
        this.page("spotlight", () -> BookSpotlightPageModel.create()
                .withItem(Ingredient.of(Items.BEE_NEST))
                .withText(this.context().pageText()));
        this.pageText("""
                        Unlike other rituals, creating a [](item://minecraft:bee_nest) is a service provided by {0}
                        and not bound any spirit to the final object. You sacrifice the items and the Wild Spirits
                         uses his power to forge that item for you.
                                                
                        """,
                this.color("Wild Spirits", ChatFormatting.DARK_PURPLE)
        );

        this.page("ritual", () -> BookRitualRecipePageModel.create()
                .withRecipeId1(this.modLoc("ritual/misc_bee_nest"))
        );
        //no text
    }

    @Override
    protected String entryName() {
        return "Forge Bee nest";
    }

    @Override
    protected String entryDescription() {
        return "Not the bees...";
    }

    @Override
    protected Pair<Integer, Integer> entryBackground() {
        return EntryBackground.DEFAULT;
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(Items.BEE_NEST);
    }

    @Override
    protected String entryId() {
        return ENTRY_ID;
    }
}
