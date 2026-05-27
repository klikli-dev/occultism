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

public class DragonystDustEntry extends EntryProvider {

    public static final String ENTRY_ID = "craft_dragonyst_dust";

    public DragonystDustEntry(CategoryProvider parent) {
        super(parent);
    }

    @Override
    protected void generatePages() {
        this.page("spotlight", () -> BookSpotlightPageModel.create()
                .withItem(Ingredient.of(OccultismItems.DRAGONYST_DUST))
                .withText(this.context().pageText()));
        this.pageText("""
                        Dragonyst dust is the rarest and most powerful dust, used for
                         the highest tier eldritch crafting. It is crafted using the
                         essence of the {0} and requires extremely rare materials
                         from the End.
                        """,
                this.color("Marid", ChatFormatting.DARK_PURPLE)
        );

        this.page("ritual", () -> BookRitualRecipePageModel.create()
                .withRecipeId1(this.modLoc("ritual/craft_dragonyst_dust"))
        );
        //no text
    }

    @Override
    protected String entryName() {
        return "Dragonyst Dust";
    }

    @Override
    protected String entryDescription() {
        return "The rarest of dusts";
    }

    @Override
    protected GuiSprite entryBackground() {
        return EntryBackground.DEFAULT;
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(OccultismItems.DRAGONYST_DUST);
    }

    @Override
    protected String entryId() {
        return ENTRY_ID;
    }
}
