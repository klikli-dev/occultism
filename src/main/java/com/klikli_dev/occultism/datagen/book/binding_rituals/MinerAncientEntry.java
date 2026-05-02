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

public class MinerAncientEntry extends EntryProvider {

    public static final String ENTRY_ID = "craft_ancient_miner";

    public MinerAncientEntry(CategoryProvider parent) {
        super(parent);
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(OccultismItems.MINER_ANCIENT_ELDRITCH);
    }

    @Override
    protected String entryName() {
        return "Ancient Miner";
    }

    @Override
    protected String entryDescription() {
        return "Stoneless";
    }

    @Override
    protected void generatePages() {
        this.page("spotlight", () -> BookSpotlightPageModel.create()
                .withItem(Ingredient.of(OccultismItems.MINER_ANCIENT_ELDRITCH))
                .withText(this.context().pageText()));
        this.pageText("""
                        By compressing {0} you get an extremely powerful miner, but something starts watching you.
                         {1} are a extremely rarely mined by a Marid.
                        """,
                this.color("MMM", ChatFormatting.DARK_PURPLE),
                this.itemLink(OccultismItems.MINING_DIMENSION_CORE_PIECE)
        );

        this.page("ritual", () -> BookRitualRecipePageModel.create()
                .withRecipeId1(this.modLoc("ritual/misc_miner_ancient_eldritch"))
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
