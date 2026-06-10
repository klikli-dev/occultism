package com.klikli_dev.occultism.datagen.book.binding_rituals;

import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.EntryBackground;
import com.klikli_dev.modonomicon.api.datagen.EntryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookSpotlightPageModel;
import com.klikli_dev.modonomicon.client.gui.book.theme.GuiSprite;
import com.klikli_dev.occultism.integration.modonomicon.pages.BookRitualRecipePageModel;
import com.klikli_dev.occultism.registry.OccultismItems;
import net.minecraft.ChatFormatting;
import net.minecraft.world.item.crafting.Ingredient;

public class UnbreakableEntry extends EntryProvider {

    public static final String ENTRY_ID = "unbreakable";

    public UnbreakableEntry(CategoryProvider parent) {
        super(parent);
    }

    @Override
    protected void generatePages() {
        this.page("spotlight_scrap", () -> BookSpotlightPageModel.create()
                .withItem(Ingredient.of(OccultismItems.BEDROCK_SCRAP))
                .withText(this.context().pageText()));
        this.pageText("""
                        During eldritch mining, some {0} are found. Through an ancient ritual,
                         powerful spirits can inlay jewels to forge a {1}.
                         This is a legendary item for turning ordinary items into unbreakable ones.
                        """,
                this.itemLink(OccultismItems.BEDROCK_SCRAP),
                this.itemLink(OccultismItems.BEDROCK_GEM_CLUSTER)
        );

        this.page("ritual_gem", () -> BookRitualRecipePageModel.create()
                .withRecipeId1(this.modLoc("ritual/misc_bedrock_gem_cluster"))
        );

        this.page("spotlight_cluster", () -> BookSpotlightPageModel.create()
                .withItem(Ingredient.of(OccultismItems.BEDROCK_GEM_CLUSTER))
                .withText(this.context().pageText()));
        this.pageText("""
                        By creating, seeing, and finally holding this item, you feel for the first time in
                         your occultism journey a brief connection with {0} forces, perhaps wonderful things can happen.
                        """,
                this.color("Celestial", ChatFormatting.DARK_PURPLE)
        );

        this.page("ritual", () -> BookRitualRecipePageModel.create()
                .withRecipeId1(this.modLoc("ritual/misc_unbreakable"))
        );
        //no text
    }

    @Override
    protected String entryName() {
        return "Bedrocking items";
    }

    @Override
    protected String entryDescription() {
        return "Remove durability";
    }

    @Override
    protected GuiSprite entryBackground() {
        return EntryBackground.DEFAULT;
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(OccultismItems.BEDROCK_GEM_CLUSTER);
    }

    @Override
    protected String entryId() {
        return ENTRY_ID;
    }
}
