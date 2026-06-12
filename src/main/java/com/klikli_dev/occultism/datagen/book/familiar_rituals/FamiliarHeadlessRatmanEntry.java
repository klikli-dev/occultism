package com.klikli_dev.occultism.datagen.book.familiar_rituals;

import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.EntryBackground;
import com.klikli_dev.modonomicon.api.datagen.EntryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookEntityPageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookTextPageModel;
import com.klikli_dev.modonomicon.client.gui.book.theme.GuiSprite;
import com.klikli_dev.occultism.integration.modonomicon.pages.BookRitualRecipePageModel;

public class FamiliarHeadlessRatmanEntry extends EntryProvider {

    public static final String ENTRY_ID = "familiar_headless";

    public FamiliarHeadlessRatmanEntry(CategoryProvider parent) {
        super(parent);
    }

    @Override
    protected void generatePages() {
        this.page("entity", () -> BookEntityPageModel.create()
                .withEntityId("occultism:headless_familiar")
                .withScale(0.7f)
                .withText(this.context().pageText()));
        this.pageText("**Provides**: [#](ad03fc)Conditional Damage Buff[#]()\n");

        this.page("ritual", () -> BookRitualRecipePageModel.create()
                .withRecipeId1(this.modLoc("ritual/familiar_headless")));

        this.page("description", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText()));
        this.pageTitle("Description");
        this.pageText("The headless ratman familiar steals heads of mobs near the ratman when they are killed. It then provides a damage buff against that type of mob to their master. If the ratman drops **below 50%% health** it dies, but can then be rebuilt by their master by giving them [](item://minecraft:wheat), [](item://minecraft:stick), [](item://minecraft:hay_block) and a [](item://minecraft:carved_pumpkin).\n");

        this.page("description2", () -> BookTextPageModel.create()
                .withText(this.context().pageText()));
        this.pageText("**Upgrade Behaviour**\\\nWhen upgraded by a blacksmith familiar, it will give weakness to nearby mobs of the type it stole the head from. And the owner will not make the Enderman angry by looking into his eyes.\n");
    }

    @Override
    protected String entryName() {
        return "Headless Ratman Familiar";
    }

    @Override
    protected String entryDescription() {
        return "";
    }

    @Override
    protected GuiSprite entryBackground() {
        return EntryBackground.DEFAULT;
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(this.modLoc("textures/gui/book/familiar_headless_ratman.png"));
    }

    @Override
    protected String entryId() {
        return ENTRY_ID;
    }
}
