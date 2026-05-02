package com.klikli_dev.occultism.datagen.book.binding_rituals;

import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.EntryBackground;
import com.klikli_dev.modonomicon.api.datagen.EntryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookSpotlightPageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookTextPageModel;
import com.klikli_dev.modonomicon.client.gui.book.theme.GuiSprite;
import com.klikli_dev.occultism.integration.modonomicon.pages.BookRitualRecipePageModel;
import com.klikli_dev.occultism.registry.OccultismItems;
import net.minecraft.ChatFormatting;
import net.minecraft.world.item.crafting.Ingredient;

public class SoulGemEntry extends EntryProvider {

    public static final String ENTRY_ID = "craft_soul_gem";

    public SoulGemEntry(CategoryProvider parent) {
        super(parent);
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(OccultismItems.SOUL_GEM_ITEM);
    }

    @Override
    protected String entryName() {
        return "Soul Gem";
    }

    @Override
    protected String entryDescription() {
        return "Magic mob imprisonment tool";
    }

    @Override
    protected void generatePages() {
        this.page("spotlight", () -> BookSpotlightPageModel.create()
                .withItem(Ingredient.of(OccultismItems.SOUL_GEM_ITEM))
                .withText(this.context().pageText()));
        this.pageText("""
                        Soul gems are diamonds set in precious metals, which are then infused with a {0}.
                         The spirit creates a small dimension that allows the temporary entrapment of living beings.
                         Beings of great power or size cannot be stored, however.
                        """,
                this.color("Djinni", ChatFormatting.DARK_PURPLE)
        );

        this.page("ritual", () -> BookRitualRecipePageModel.create()
                .withRecipeId1(this.modLoc("ritual/craft_soul_gem"))
        );
        //no text

        this.page("usage", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText()));
        this.pageTitle("Usage");
        this.pageText("""
                        To capture an entity, {0} it with the soul gem. \\
                        {0} again to release the entity.
                        \\
                        \\
                        Bosses cannot be captured.
                        """,
                this.color("right-click", ChatFormatting.DARK_PURPLE)
        );
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
