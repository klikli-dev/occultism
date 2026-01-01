package com.klikli_dev.occultism.datagen.book.binding_rituals;

import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.EntryBackground;
import com.klikli_dev.modonomicon.api.datagen.EntryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookSpotlightPageModel;
import com.klikli_dev.occultism.integration.modonomicon.pages.BookRitualRecipePageModel;
import com.klikli_dev.occultism.registry.OccultismBlocks;
import com.klikli_dev.occultism.registry.OccultismItems;
import com.mojang.datafixers.util.Pair;
import net.minecraft.ChatFormatting;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

public class MinerMaridEntry extends EntryProvider {

    public static final String ENTRY_ID = "craft_marid_miner";

    public MinerMaridEntry(CategoryProvider parent) {
        super(parent);
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(OccultismItems.MINER_MARID_MASTER);
    }

    @Override
    protected String entryName() {
        return "Marid Miner";
    }

    @Override
    protected String entryDescription() {
        return "Rare resources miner";
    }

    @Override
    protected void generatePages() {
        this.page("spotlight", () -> BookSpotlightPageModel.create()
                .withItem(Ingredient.of(OccultismItems.MINER_MARID_MASTER))
                .withText(this.context().pageText()));
        this.pageText("""
                        The {0} miner is the most powerful miner spirit, it has the fasted mining speed and best magic lamp
                         preservation. Unlike other miner spirits they also can mine the rarest ores, such as {1} and {2}.
                        """,
                this.color("Marid", ChatFormatting.DARK_PURPLE),
                this.itemLink(Items.ANCIENT_DEBRIS),
                this.itemLink(OccultismBlocks.IESNIUM_ORE)
        );

        this.page("ritual", () -> BookRitualRecipePageModel.create()
                .withRecipeId1(this.modLoc("ritual/craft_miner_marid_master"))
        );
        //no text
    }

    @Override
    protected Pair<Integer, Integer> entryBackground() {
        return EntryBackground.DEFAULT;
    }

    @Override
    protected String entryId() {
        return ENTRY_ID;
    }
}
