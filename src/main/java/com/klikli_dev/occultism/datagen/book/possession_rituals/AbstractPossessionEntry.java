package com.klikli_dev.occultism.datagen.book.possession_rituals;

import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.EntryBackground;
import com.klikli_dev.modonomicon.api.datagen.EntryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookEntityPageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookTextPageModel;
import com.klikli_dev.modonomicon.client.gui.book.theme.GuiSprite;
import com.klikli_dev.occultism.datagen.OccultismBookProvider;
import com.klikli_dev.occultism.integration.modonomicon.pages.BookRitualRecipePageModel;

public abstract class AbstractPossessionEntry extends EntryProvider {

    protected static final String COLOR_PURPLE = OccultismBookProvider.COLOR_PURPLE;

    protected AbstractPossessionEntry(CategoryProvider parent) {
        super(parent);
    }

    protected BookTextPageModel textPage(String id) {
        return this.page(id, () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText()));
    }

    protected BookTextPageModel textPageNoTitle(String id) {
        return this.page(id, () -> BookTextPageModel.create()
                .withText(this.context().pageText()));
    }

    protected BookEntityPageModel entityPage(String id, String entityId) {
        return this.page(id, () -> BookEntityPageModel.create()
                .withEntityId(entityId)
                .withText(this.context().pageText()));
    }

    protected BookEntityPageModel entityPage(String id, String entityId, float scale) {
        return this.page(id, () -> BookEntityPageModel.create()
                .withEntityId(entityId)
                .withScale(scale)
                .withText(this.context().pageText()));
    }

    protected BookEntityPageModel entityPage(String id, String entityId, float scale, float offset) {
        return this.page(id, () -> BookEntityPageModel.create()
                .withEntityId(entityId)
                .withScale(scale)
                .withOffset(offset)
                .withText(this.context().pageText()));
    }

    protected BookRitualRecipePageModel ritualPage(String id, String recipeId) {
        return this.page(id, () -> BookRitualRecipePageModel.create()
                .withRecipeId1(this.modLoc(recipeId)));
    }

    @Override
    protected String entryDescription() {
        return "";
    }

    @Override
    protected GuiSprite entryBackground() {
        return EntryBackground.DEFAULT;
    }
}
