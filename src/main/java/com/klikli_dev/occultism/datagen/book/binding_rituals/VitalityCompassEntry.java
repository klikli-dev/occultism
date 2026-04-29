package com.klikli_dev.occultism.datagen.book.binding_rituals;

import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.EntryBackground;
import com.klikli_dev.modonomicon.api.datagen.EntryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookSpotlightPageModel;
import com.klikli_dev.occultism.integration.modonomicon.pages.BookRitualRecipePageModel;
import com.klikli_dev.occultism.registry.OccultismItems;
import com.klikli_dev.modonomicon.client.gui.book.theme.GuiSprite;
import net.minecraft.ChatFormatting;
import net.minecraft.world.item.crafting.Ingredient;

public class VitalityCompassEntry extends EntryProvider {

    public static final String ENTRY_ID = "vitality_compass";


    public VitalityCompassEntry(CategoryProvider parent) {
        super(parent);
    }

    @Override
    protected void generatePages() {
        this.page("spotlight", () -> BookSpotlightPageModel.create()
                .withItem(Ingredient.of(OccultismItems.VITALITY_COMPASS))
                .withText(this.context().pageText()));
        this.pageText("""
                       The [](item://occultism:vitality_compass) is a mystical tool infused with a {0}
                        that allows it to be attuned to the essence of living beings.
                        By right-clicking on a creature, you bind its life force to the compass,
                        allowing the needle to always point toward its location no matter the distance (while loaded).
                        """,
                this.color("Foliot", ChatFormatting.DARK_PURPLE)
        );

        this.page("ritual", () -> BookRitualRecipePageModel.create()
                .withRecipeId1(this.modLoc("ritual/craft_vitality_compass"))
        );
        //no text
    }

    @Override
    protected String entryName() {
        return "Vitality Compass";
    }

    @Override
    protected String entryDescription() {
        return "Where are my friends?";
    }

    @Override
    protected GuiSprite entryBackground() {
        return EntryBackground.DEFAULT;
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(OccultismItems.VITALITY_COMPASS);
    }

    @Override
    protected String entryId() {
        return ENTRY_ID;
    }
}
