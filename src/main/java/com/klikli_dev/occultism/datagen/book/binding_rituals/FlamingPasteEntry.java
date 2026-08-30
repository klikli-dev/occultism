package com.klikli_dev.occultism.datagen.book.binding_rituals;

import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.EntryBackground;
import com.klikli_dev.modonomicon.api.datagen.EntryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookCraftingRecipePageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookSpotlightPageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookTextPageModel;
import com.klikli_dev.modonomicon.client.gui.book.theme.GuiSprite;
import com.klikli_dev.occultism.integration.modonomicon.pages.BookRitualRecipePageModel;
import com.klikli_dev.occultism.registry.OccultismItems;
import net.minecraft.ChatFormatting;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

public class FlamingPasteEntry extends EntryProvider {

    public static final String ENTRY_ID = "craft_flaming_paste";


    public FlamingPasteEntry(CategoryProvider parent) {
        super(parent);
    }

    @Override
    protected void generatePages() {
        this.page("spotlight", () -> BookSpotlightPageModel.create()
                .withItem(Ingredient.of(OccultismItems.FLAMING_PASTE))
                .withText(this.context().pageText()));
        this.pageText("""
                        Flaming Paste is a versatile flammable paste created with
                         the help of {0}, capable of manipulating fire and lava.
                         It can be used to set creatures on fire and transmute certain items.
                        \\
                        \\
                        With a {1}, it can be used to create fire and perform ignition
                         actions compatible with {2}. Additionally, {3} makes it function as a {4}.
                        """,
                this.color("Afrit", ChatFormatting.DARK_PURPLE),
                this.color("Right-Click", ChatFormatting.DARK_PURPLE),
                this.itemLink(Items.FLINT_AND_STEEL),
                this.color("Shift-Right-Click", ChatFormatting.DARK_PURPLE),
                this.itemLink(Items.LAVA_BUCKET)
        );

        this.page("ritual", () -> BookRitualRecipePageModel.create()
                .withRecipeId1(this.modLoc("ritual/craft_flaming_paste"))
        );
        //no text

        this.page("crafting1", () -> BookCraftingRecipePageModel.create()
                .withRecipeId1(this.modLoc("crafting/flaming_paste/blaze_rod"))
                .withRecipeId2(this.modLoc("crafting/flaming_paste/fire_charge"))
        );

        this.page("crafting2", () -> BookCraftingRecipePageModel.create()
                .withRecipeId1(this.modLoc("crafting/flaming_paste/glowstone_dust"))
                .withRecipeId2(this.modLoc("crafting/flaming_paste/magma_cream"))
        );

        this.page("fireball", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText()));
        this.pageTitle("I cast FIREBALL!");
        this.pageText("""
                        When combined with {0} in the off-hand, it allows you to
                         launch small fireballs in the direction the player is looking.
                        \\
                        \\
                        When dispensed by a Dispenser, it launches a large fireball.
                        """,
                this.entryLink("Gray Paste", "crafting_rituals", "craft_gray_paste")
        );
    }

    @Override
    protected String entryName() {
        return "Flaming Paste";
    }

    @Override
    protected String entryDescription() {
        return "This is pure magma";
    }

    @Override
    protected GuiSprite entryBackground() {
        return EntryBackground.DEFAULT;
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(OccultismItems.FLAMING_PASTE);
    }

    @Override
    protected String entryId() {
        return ENTRY_ID;
    }
}
