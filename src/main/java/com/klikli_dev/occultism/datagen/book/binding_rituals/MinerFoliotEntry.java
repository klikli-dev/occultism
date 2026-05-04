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
import com.klikli_dev.occultism.registry.OccultismBlocks;
import com.klikli_dev.occultism.registry.OccultismItems;
import net.minecraft.ChatFormatting;
import net.minecraft.world.item.crafting.Ingredient;

public class MinerFoliotEntry extends EntryProvider {

    public static final String ENTRY_ID = "craft_foliot_miner";

    public MinerFoliotEntry(CategoryProvider parent) {
        super(parent);
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(OccultismItems.MINER_FOLIOT_UNSPECIALIZED);
    }

    @Override
    protected String entryName() {
        return "Foliot Miner";
    }

    @Override
    protected String entryDescription() {
        return "Stone, stone and ores";
    }

    @Override
    protected void generatePages() {
        this.page("intro", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText()));
        this.pageTitle("Foliot Miner");
        this.pageText("""
                        Miner spirits use {0} to acquire resources from other dimensions.
                         They are summoned and bound into magic lamps, which they can leave only through the mineshaft.
                         The magic lamp degrades over time, once it breaks the spirit is released back to {1}.
                        """,
                this.itemLink(OccultismBlocks.DIMENSIONAL_MINESHAFT),
                this.color("The Other Place", ChatFormatting.DARK_PURPLE)
        );

        this.page("magic_lamp", () -> BookCraftingRecipePageModel.create()
                .withRecipeId1(this.modLoc("crafting/magic_lamp_empty"))
                .withText(this.context().pageText()));
        this.pageText("""
                        To summon miner spirits, you first need to craft a {0} to hold them.
                         The key ingredient for that is {1}.
                        """,
                this.entryLink("Magic Lamp", "getting_started", "magic_lamps"),
                this.itemLink(OccultismItems.SPIRIT_ATTUNED_GEM)
        );

        this.page("spotlight", () -> BookSpotlightPageModel.create()
                .withItem(Ingredient.of(OccultismItems.MINER_FOLIOT_UNSPECIALIZED))
                .withText(this.context().pageText()));
        this.pageText("""
                        The {0} miner harvests only resources considered more basic and less precious.
                         The mining process is quite slow, due to this the Foliot expends only minor
                         amounts of energy, damaging the lamp it is housed in slowly over time.
                        """,
                this.color("Foliot", ChatFormatting.DARK_PURPLE)
        );

        this.page("ritual", () -> BookRitualRecipePageModel.create()
                .withRecipeId1(this.modLoc("ritual/craft_miner_foliot_unspecialized"))
        );
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
