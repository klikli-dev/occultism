package com.klikli_dev.occultism.datagen.book.binding_rituals;

import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.EntryBackground;
import com.klikli_dev.modonomicon.api.datagen.EntryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookSpotlightPageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookTextPageModel;
import com.klikli_dev.occultism.integration.modonomicon.pages.BookRitualRecipePageModel;
import com.klikli_dev.occultism.registry.OccultismItems;
import com.mojang.datafixers.util.Pair;
import net.minecraft.ChatFormatting;
import net.minecraft.world.item.crafting.Ingredient;

public class FragileSoulGemEntry extends EntryProvider {

    public static final String ENTRY_ID = "fragile_soul_gem";


    public FragileSoulGemEntry(CategoryProvider parent) {
        super(parent);
    }

    @Override
    protected void generatePages() {
        this.page("spotlight", () -> BookSpotlightPageModel.create()
                .withItem(Ingredient.of(OccultismItems.FRAGILE_SOUL_GEM_ITEM))
                .withText(this.context().pageText()));
        this.pageText("""
                        Fragile Soul gems are eggs infused by a {0}. The spirit creates a small dimension
                         that allows the temporary entrapment of living beings.
                         Beings of great power or size cannot be stored, however. \\
                         Be careful, this item will break after transporting a creature.
                        """,
                this.color("Foliot", ChatFormatting.DARK_PURPLE)
        );

        this.page("ritual", () -> BookRitualRecipePageModel.create()
                .withRecipeId1(this.modLoc("ritual/craft_fragile_soul_gem"))
        );
        //no text

        this.page("use", () -> BookTextPageModel.create()
                .withTitle(this.context.pageTitle())
                .withText(this.context().pageText()));
        this.pageTitle("Usage");
        this.pageText("""
                        To capture an entity, {0} it with the soul gem. \\
                        {1} again to release the entity.
                        \\
                        \\
                        Bosses cannot be captured.
                        """,
                this.color("right-click", ChatFormatting.DARK_PURPLE),
                this.color("Right-click", ChatFormatting.DARK_PURPLE)
        );
    }

    @Override
    protected String entryName() {
        return "Fragile Soul Gem";
    }

    @Override
    protected String entryDescription() {
        return "Who needs boats?";
    }

    @Override
    protected Pair<Integer, Integer> entryBackground() {
        return EntryBackground.DEFAULT;
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(OccultismItems.FRAGILE_SOUL_GEM_ITEM);
    }

    @Override
    protected String entryId() {
        return ENTRY_ID;
    }
}
