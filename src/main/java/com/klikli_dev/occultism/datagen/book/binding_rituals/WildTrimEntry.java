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

public class WildTrimEntry extends EntryProvider {

    public static final String ENTRY_ID = "craft_wild_trim";

    public WildTrimEntry(CategoryProvider parent) {
        super(parent);
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(Items.WILD_ARMOR_TRIM_SMITHING_TEMPLATE);
    }

    @Override
    protected String entryName() {
        return "Forge Wild Trim";
    }

    @Override
    protected String entryDescription() {
        return "Welcome to the jungle";
    }

    @Override
    protected void generatePages() {
        this.page("spotlight", () -> BookSpotlightPageModel.create()
                .withItem(Ingredient.of(Items.WILD_ARMOR_TRIM_SMITHING_TEMPLATE))
                .withText(this.context().pageText()));
        this.pageText("""
                        Unlike other rituals, creating a {0} is a service provided by {1} and not bound any spirit to the
                         final object. You sacrifice the items and the Wild Spirits uses his power to forge that item for you.
                         \\
                         Other connected items also follow this same operating principle.
                        """,
                this.itemLink(Items.WILD_ARMOR_TRIM_SMITHING_TEMPLATE),
                this.color("Wild Spirits", ChatFormatting.DARK_PURPLE)
        );

        this.page("ritual", () -> BookRitualRecipePageModel.create()
                .withRecipeId1(this.modLoc("ritual/misc_wild_trim"))
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
