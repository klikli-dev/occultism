package com.klikli_dev.occultism.datagen.book.binding_rituals;

import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.EntryBackground;
import com.klikli_dev.modonomicon.api.datagen.EntryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookSpotlightPageModel;
import com.klikli_dev.modonomicon.client.gui.book.theme.GuiSprite;
import com.klikli_dev.occultism.integration.modonomicon.pages.BookRitualRecipePageModel;
import com.klikli_dev.occultism.registry.OccultismItems;
import net.minecraft.ChatFormatting;
import net.minecraft.world.item.crafting.Ingredient;

public class IesniumButcherKnifeEntry extends EntryProvider {

    public static final String ENTRY_ID = "iesnium_butcher_knife";

    public IesniumButcherKnifeEntry(CategoryProvider parent) {
        super(parent);
    }

    @Override
    protected void generatePages() {
        this.page("spotlight", () -> BookSpotlightPageModel.create()
                .withItem(Ingredient.of(OccultismItems.IESNIUM_BUTCHER_KNIFE))
                .withText(this.context().pageText()));
        this.pageText("""
                        This knife is an {0} infusion that enhances the butcher knife with iesnium,
                         increasing its damage while preserving the tallow drop property.
                         \\
                         Additionally, certain mobs may drop their heads, and attacks against spirits deal triple damage.
                        """,
                this.color("Afrit", ChatFormatting.DARK_PURPLE)
        );

        this.page("ritual", () -> BookRitualRecipePageModel.create()
                .withRecipeId1(this.modLoc("ritual/craft_iesnium_butcher_knife"))
        );
        //no text
    }

    @Override
    protected String entryName() {
        return "Iesnium Butcher Knife";
    }

    @Override
    protected String entryDescription() {
        return "Off with his head!";
    }

    @Override
    protected GuiSprite entryBackground() {
        return EntryBackground.DEFAULT;
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(OccultismItems.IESNIUM_BUTCHER_KNIFE);
    }

    @Override
    protected String entryId() {
        return ENTRY_ID;
    }
}
