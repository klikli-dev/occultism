package com.klikli_dev.occultism.datagen.book.summoning_rituals;

import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.EntryBackground;
import com.klikli_dev.modonomicon.api.datagen.EntryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookTextPageModel;
import com.klikli_dev.occultism.integration.modonomicon.pages.BookRitualRecipePageModel;
import com.klikli_dev.occultism.registry.OccultismItems;
import com.klikli_dev.modonomicon.client.gui.book.theme.GuiSprite;
import net.minecraft.ChatFormatting;

public class EssenceMaridEntry extends EntryProvider {

    public static final String ENTRY_ID = "marid_essence";

    public EssenceMaridEntry(CategoryProvider parent) {
        super(parent);
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(OccultismItems.MARID_ESSENCE);
    }

    @Override
    protected String entryName() {
        return "Marid Essence";
    }

    @Override
    protected String entryDescription() {
        return "Our battle will be legendary!";
    }

    @Override
    protected void generatePages() {
        this.page("intro", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText()));
        this.pageTitle("Marid Essence");
        this.pageText("""
                {0}  is required to safely control the most powerful spirits, commonly used
                 in the form of blue chalk. To obtain the essence, an {1} needs to be summoned
                 unbound into this plane, and killed. Be warned that this is no simple endeavour,
                 and unbound spirit presents great danger to all nearby.
                """,
                this.itemLink(OccultismItems.MARID_ESSENCE),
                this.color("Marid", ChatFormatting.DARK_PURPLE)
        );

        this.page("ritual_day", () -> BookRitualRecipePageModel.create()
                    .withRecipeId1(this.modLoc("ritual/summon_unbound_marid")));
        //no text

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
