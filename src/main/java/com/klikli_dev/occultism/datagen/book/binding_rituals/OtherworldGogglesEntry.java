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
import net.minecraft.world.item.crafting.Ingredient;

public class OtherworldGogglesEntry extends EntryProvider {

    public static final String ENTRY_ID = "craft_otherworld_goggles";

    public OtherworldGogglesEntry(CategoryProvider parent) {
        super(parent);
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(OccultismItems.OTHERWORLD_GOGGLES);
    }

    @Override
    protected String entryName() {
        return "Craft Otherworld Goggles";
    }

    @Override
    protected String entryDescription() {
        return "Drugs, No That!";
    }

    @Override
    protected void generatePages() {
        this.page("goggles_spotlight", () -> BookSpotlightPageModel.create()
                .withItem(Ingredient.of(OccultismItems.OTHERWORLD_GOGGLES))
                .withText(this.context().pageText()));
        this.pageText("""
                        The {0} give the wearer permanent {1}, allowing to view even blocks hidden from those partaking of {2}.
                        \\
                        \\
                        This elegantly solves the general issue of summoners being in a drugged haze, causing all sorts of havoc.
                        """,
                this.itemLink(OccultismItems.OTHERWORLD_GOGGLES),
                this.color("Third Eye", ChatFormatting.DARK_PURPLE),
                this.entryLink("Demon's Dream", "getting_started", "demons_dream"));

        this.page("goggles_more", () -> BookTextPageModel.create()
                .withText(this.context().pageText()));
        this.pageText("""
                        The Goggles will, however, not give the ability to harvest otherworld materials.
                         That means when wearing goggles, an {0}, or even better, an {1} needs to be
                         used to break blocks in order to obtain their Otherworld variants.
                        """,
                this.entryLink("Infused Pick", "getting_started", "infused_pickaxe"),
                this.entryLink("Iesnium Pick", "getting_started", "iesnium_pickaxe"));

        this.page("lenses_spotlight", () -> BookSpotlightPageModel.create()
                .withItem(Ingredient.of(OccultismItems.LENSES))
                .withText(this.context().pageText()));
        this.pageText("""
                        Otherworld Goggles make use of a {0} bound into the lenses.
                         The Foliot shares it's ability to view higher planes with the wearer,
                         thus allowing them to see Otherworld materials.
                        """,
                this.color("Foliot", ChatFormatting.DARK_PURPLE));

        this.page("lenses_more", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText()));
        this.pageTitle("Crafting Lenses");
        this.pageText("""
                Summoning a spirit into the lenses used to craft goggles is one of the
                 first of the more complex rituals apprentice summoners usually attempt,
                 showing that their skills are progressing beyond the basics.
                """);

        //no text bellow
        this.page("lenses_recipe", () -> BookCraftingRecipePageModel.create()
                .withRecipeId1(this.modLoc("crafting/lenses")));

        this.page("ritual", () -> BookRitualRecipePageModel.create()
                .withRecipeId1(this.modLoc("ritual/craft_infused_lenses")));

        this.page("frame_recipe", () -> BookCraftingRecipePageModel.create()
                .withRecipeId1(this.modLoc("crafting/lens_frame"))
                .withRecipeId2(this.modLoc("crafting/lens_frame_alt")));

        this.page("goggles_recipe", () -> BookCraftingRecipePageModel.create()
                .withRecipeId1(this.modLoc("crafting/goggles")));
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
