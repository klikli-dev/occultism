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

public class FamiliarRingEntry extends EntryProvider {

    public static final String ENTRY_ID = "craft_familiar_ring";

    public FamiliarRingEntry(CategoryProvider parent) {
        super(parent);
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(OccultismItems.FAMILIAR_RING);
    }

    @Override
    protected String entryName() {
        return "Familiar Ring";
    }

    @Override
    protected String entryDescription() {
        return "My precious";
    }

    @Override
    protected void generatePages() {
        this.page("spotlight", () -> BookSpotlightPageModel.create()
                .withItem(Ingredient.of(OccultismItems.FAMILIAR_RING))
                .withText(this.context().pageText()));
        this.pageText("""
                        Familiar Rings consist of a {0}, that contains a {1}, mounted on a ring.
                         The {1} in the ring allows the familiar captured in the soul gem to apply effects to the wearer.
                        """,
                this.itemLink(OccultismItems.SOUL_GEM_ITEM),
                this.color("Djinni", ChatFormatting.DARK_PURPLE)
        );

        this.page("ritual", () -> BookRitualRecipePageModel.create()
                .withRecipeId1(this.modLoc("ritual/craft_familiar_ring"))
        );
        //no text

        this.page("usage", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText()));
        this.pageTitle("Usage");
        this.pageText("""
                        To use a {0}, simply capture a summoned (and tamed) familiar by {1},
                         and then wear the ring as {2} to make use of the effects the familiar provides.
                        \\
                        \\
                        When released from a familiar ring, the spirit will recognize the person releasing them as their new master.
                        """,
                this.itemLink(OccultismItems.FAMILIAR_RING),
                this.color("right-clicking", ChatFormatting.DARK_PURPLE),
                this.color("Curio", ChatFormatting.DARK_PURPLE)
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
