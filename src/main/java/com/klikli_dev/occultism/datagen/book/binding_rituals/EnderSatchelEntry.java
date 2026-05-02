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

public class EnderSatchelEntry extends EntryProvider {

    public static final String ENTRY_ID = "ender_satchel";

    public EnderSatchelEntry(CategoryProvider parent) {
        super(parent);
    }

    @Override
    protected void generatePages() {
        this.page("spotlight", () -> BookSpotlightPageModel.create()
                .withItem(Ingredient.of(OccultismItems.ENDER_SATCHEL))
                .withText(this.context().pageText()));
        this.pageText("""
                        A {0} is bound to the ender satchel, tasked with **slightly** warping the space.
                         This allows open your ender chest from anywhere, making it a practical traveller''s companion.
                        
                        """,
                this.color("Djinni", ChatFormatting.DARK_PURPLE)
        );

        this.page("ritual", () -> BookRitualRecipePageModel.create()
                .withRecipeId1(this.modLoc("ritual/craft_ender_satchel"))
        );
        //no text

        this.page("link", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText()));
        this.pageTitle("Inventory Sharing");
        this.pageText("""
                        Additionally, using {0} will bind the satchel to you,
                         allowing to share your ender chest with any other player,
                         making this a great way to send items over long distances to friends.
                        """,
                this.color("Shift + Right-Click", ChatFormatting.DARK_PURPLE)
        );

    }

    @Override
    protected String entryName() {
        return "Ender Satchel";
    }

    @Override
    protected String entryDescription() {
        return "Portable ender chest";
    }

    @Override
    protected GuiSprite entryBackground() {
        return EntryBackground.DEFAULT;
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(OccultismItems.ENDER_SATCHEL);
    }

    @Override
    protected String entryId() {
        return ENTRY_ID;
    }
}
