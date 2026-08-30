package com.klikli_dev.occultism.datagen.book.binding_rituals;

import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.EntryBackground;
import com.klikli_dev.modonomicon.api.datagen.EntryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookSpotlightPageModel;
import com.klikli_dev.modonomicon.client.gui.book.theme.GuiSprite;
import com.klikli_dev.occultism.integration.modonomicon.pages.BookRitualRecipePageModel;
import com.klikli_dev.occultism.registry.OccultismItems;
import net.minecraft.world.item.crafting.Ingredient;

public class FamiliarGloveEntry extends EntryProvider {

    public static final String ENTRY_ID = "craft_familiar_glove";

    public FamiliarGloveEntry(CategoryProvider parent) {
        super(parent);
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(OccultismItems.FAMILIAR_GLOVE);
    }

    @Override
    protected String entryName() {
        return "Familiar Glove";
    }

    @Override
    protected String entryDescription() {
        return "I got family";
    }

    @Override
    protected void generatePages() {
        this.page("spotlight", () -> BookSpotlightPageModel.create()
                .withItem(Ingredient.of(OccultismItems.FAMILIAR_GLOVE))
                .withText(this.context().pageText()));
        this.pageText("""
                        The {0} is an upgraded version of the {1}, capable of holding up to 6 familiars at once instead of just one.
                        """,
                this.itemLink(OccultismItems.FAMILIAR_GLOVE),
                this.entryLink("Familiar Ring", "crafting_rituals", "craft_familiar_ring")
        );

        this.page("ritual", () -> BookRitualRecipePageModel.create()
                .withRecipeId1(this.modLoc("ritual/misc_familiar_glove"))
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
