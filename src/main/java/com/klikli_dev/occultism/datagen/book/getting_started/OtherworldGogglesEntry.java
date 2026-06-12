package com.klikli_dev.occultism.datagen.book.getting_started;

import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.EntryBackground;
import com.klikli_dev.modonomicon.api.datagen.EntryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookSpotlightPageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookTextPageModel;
import com.klikli_dev.modonomicon.client.gui.book.theme.GuiSprite;
import com.klikli_dev.occultism.registry.OccultismItems;
import net.minecraft.world.item.crafting.Ingredient;

public class OtherworldGogglesEntry extends EntryProvider {

    public static final String ENTRY_ID = "otherworld_goggles";

    public OtherworldGogglesEntry(CategoryProvider parent) {
        super(parent);
    }

    @Override
    protected void generatePages() {
        this.page("spotlight", () -> BookSpotlightPageModel.create()
                .withItem(Ingredient.of(OccultismItems.OTHERWORLD_GOGGLES.get()))
                .withText(this.context().pageText()));
        this.pageText("The [](item://occultism:otherworld_goggles) are what advanced summoners use to see the [#](ad03fc)Otherworld[#](), to avoid the negative side effects of [](entry://occultism:dictionary_of_spirits/getting_started/demons_dream).\n\\\n\\\nMaking your first pair of these is seen by many as a rite of passage.\n");

        this.page("crafting", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText()));
        this.pageTitle("Crafting Goggles");
        this.pageText("Crafting these goggles is a multi-step process described in detail in the Entry about [Crafting Otherworld Goggles](entry://occultism:dictionary_of_spirits/crafting_rituals/craft_otherworld_goggles).\n");
    }

    @Override
    protected String entryName() {
        return "Otherworld Goggles";
    }

    @Override
    protected String entryDescription() {
        return "Say no to drugs!";
    }

    @Override
    protected GuiSprite entryBackground() {
        return EntryBackground.DEFAULT;
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(OccultismItems.OTHERWORLD_GOGGLES.get());
    }

    @Override
    protected String entryId() {
        return ENTRY_ID;
    }
}
