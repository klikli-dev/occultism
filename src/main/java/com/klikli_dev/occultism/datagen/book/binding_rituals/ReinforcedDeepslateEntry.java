package com.klikli_dev.occultism.datagen.book.binding_rituals;

import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.EntryBackground;
import com.klikli_dev.modonomicon.api.datagen.EntryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookSpotlightPageModel;
import com.klikli_dev.modonomicon.client.gui.book.theme.GuiSprite;
import com.klikli_dev.occultism.integration.modonomicon.pages.BookRitualRecipePageModel;
import net.minecraft.ChatFormatting;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

public class ReinforcedDeepslateEntry extends EntryProvider {

    public static final String ENTRY_ID = "craft_reinforced_deepslate";

    public ReinforcedDeepslateEntry(CategoryProvider parent) {
        super(parent);
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(Items.REINFORCED_DEEPSLATE);
    }

    @Override
    protected String entryName() {
        return "Forge Reinforced Deepslate";
    }

    @Override
    protected String entryDescription() {
        return "It's hard";
    }

    @Override
    protected void generatePages() {
        this.page("spotlight", () -> BookSpotlightPageModel.create()
                .withItem(Ingredient.of(Items.REINFORCED_DEEPSLATE))
                .withText(this.context().pageText()));
        this.pageText("""
                        Unlike other rituals, creating a {0} is a service provided by {1} and not bound any spirit to the
                         final object. You sacrifice the items and the Wild Spirits uses his power to forge that item for you.
                        """,
                this.itemLink(Items.REINFORCED_DEEPSLATE),
                this.color("Wild Spirits", ChatFormatting.DARK_PURPLE)
        );

        this.page("ritual", () -> BookRitualRecipePageModel.create()
                .withRecipeId1(this.modLoc("ritual/misc_reinforced_deepslate"))
        );
        //no text
    }

    @Override
    protected GuiSprite entryBackground() {
        return EntryBackground.DEFAULT;
    }

    @Override
    protected String entryId() {
        return ENTRY_ID;
    }
}
