package com.klikli_dev.occultism.datagen.book.familiar_rituals;

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

public class GreatResurrectionEntry extends EntryProvider {

    public static final String ENTRY_ID = "great_resurrection";

    public GreatResurrectionEntry(CategoryProvider parent) {
        super(parent);
    }

    @Override
    protected void generatePages() {

        this.page("soul_shattered", () -> BookSpotlightPageModel.create()
                .withItem(Ingredient.of(OccultismItems.SOUL_SHATTERED_ITEM.get()))
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText()));
        this.pageTitle("Shattered Soul Shards");
        this.pageText("""
                Occultists have discovered a way to capture part of the essence
                 of any fallen enemy through an enchantment known as {0}.
                \\
                \\
                With a variation of the ritual to resurrect familiars,
                 it is possible for a {1} to create a completely revived mob,
                 forming its new body and trapping within it the shard''s remaining soul.
                """,
                this.color("Fracture Soul", ChatFormatting.GREEN),
                this.itemLink(OccultismItems.SOUL_SHATTERED_ITEM.get()));

        this.page("ritual", () -> BookRitualRecipePageModel.create()
                .withRecipeId1(this.modLoc("ritual/resurrect_mob"))
        );
        //no text
    }

    @Override
    protected String entryName() {
        return "Resurrecting any Mob";
    }

    @Override
    protected String entryDescription() {
        return "How to bring back a stranger from the dead.";
    }

    @Override
    protected GuiSprite entryBackground() {
        return EntryBackground.DEFAULT;
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(OccultismItems.SOUL_SHATTERED_ITEM.get());
    }

    @Override
    protected String entryId() {
        return ENTRY_ID;
    }
}
