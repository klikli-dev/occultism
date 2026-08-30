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

public class InfusedToolsEntry extends EntryProvider {

    public static final String ENTRY_ID = "craft_infused_tools";

    public InfusedToolsEntry(CategoryProvider parent) {
        super(parent);
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(OccultismItems.INFUSED_PICKAXE.get());
    }

    @Override
    protected String entryName() {
        return "Infused Pickaxe";
    }

    @Override
    protected String entryDescription() {
        return "When the Third Eye isn't enough";
    }

    @Override
    protected void generatePages() {
        this.page("description", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText()));
        this.pageTitle("Infused Tools");
        this.pageText("""
                        Otherworld resource can usually only be mined with under the {0} effect.
                        \\
                        Brittle {1} can be a solution to this fruit addiction problem.
                         Tools made of these material can collect basic Otherworld resources even when they are hidden from view.
                         \\
                         \\
                        They also function as {2} when held.
                        """,
                this.entryLink("Third Eye", "getting_started", "effects@third_eye"),
                this.itemLink(OccultismItems.SPIRIT_ATTUNED_GEM),
                this.entryLink("Familiar Ring", "crafting_rituals", "craft_familiar_ring")
        );

        this.page("spotlight", () -> BookSpotlightPageModel.create()
                .withItem(Ingredient.of(OccultismItems.INFUSED_PICKAXE.get()))
                .withText(this.context().pageText()));
        this.pageText("""
                        Advanced Otherworld Ores usually can only be mined with Otherworld metal tools.
                         The {0} is a makeshift solution to this Chicken-and-Egg problem.
                         While housing a {1} that allows harvesting the,
                         but the durability is low. A more durable version is the {2}.
                        """,
                this.itemLink(OccultismItems.INFUSED_PICKAXE.get()),
                this.color("Familiar", ChatFormatting.DARK_PURPLE),
                this.entryLink("Iesnium Pickaxe", "getting_started", "iesnium_pickaxe")
        );

        this.page("ritual_pickaxe", () -> BookRitualRecipePageModel.create()
                .withRecipeId1(this.modLoc("ritual/craft_infused_pickaxe"))
        );

        this.page("ritual_axe", () -> BookRitualRecipePageModel.create()
                .withRecipeId1(this.modLoc("ritual/craft_infused_axe"))
        );

        this.page("ritual_shovel", () -> BookRitualRecipePageModel.create()
                .withRecipeId1(this.modLoc("ritual/craft_infused_shovel"))
        );

        this.page("ritual_hoe", () -> BookRitualRecipePageModel.create()
                .withRecipeId1(this.modLoc("ritual/craft_infused_hoe"))
        );

        this.page("ritual_sword", () -> BookRitualRecipePageModel.create()
                .withRecipeId1(this.modLoc("ritual/craft_infused_sword"))
        );

        this.page("ritual_spear", () -> BookRitualRecipePageModel.create()
                .withRecipeId1(this.modLoc("ritual/craft_infused_spear"))
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
