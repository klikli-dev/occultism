package com.klikli_dev.occultism.datagen.book.binding_rituals;

import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.EntryBackground;
import com.klikli_dev.modonomicon.api.datagen.EntryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookSpotlightPageModel;
import com.klikli_dev.occultism.integration.modonomicon.pages.BookRitualRecipePageModel;
import com.klikli_dev.occultism.registry.OccultismBlocks;
import com.mojang.datafixers.util.Pair;
import net.minecraft.ChatFormatting;
import net.minecraft.world.item.crafting.Ingredient;

public class StabilizedStorageEntry extends EntryProvider {

    public static final String ENTRY_ID = "craft_stabilized_storage";


    public StabilizedStorageEntry(CategoryProvider parent) {
        super(parent);
    }

    @Override
    protected void generatePages() {
        this.page("spotlight", () -> BookSpotlightPageModel.create()
                .withItem(Ingredient.of(OccultismBlocks.STORAGE_CONTROLLER_STABILIZED.asItem()))
                .withText(this.context().pageText()));
        this.pageText("""
                        Forging the [](item://occultism:storage_controller_stabilized) is one service provide by {0},
                         this block will helps occult masters twist space, placing the stabilizers in the same
                         position as the actuator in some extra-planar dimension invisible even to the best eyes.\\
                         By default this item receives one bonus stabilizer tier 4\\
                         Other external stabilizers do not affect this block.
                        """,
                this.color("Eldritch Spirits", ChatFormatting.DARK_PURPLE)
        );

        this.page("ritual", () -> BookRitualRecipePageModel.create()
                .withRecipeId1(this.modLoc("ritual/misc_stabilized_storage"))
        );
        //no text
    }

    @Override
    protected String entryName() {
        return "Stabilized Dimensional Storage Actuator";
    }

    @Override
    protected String entryDescription() {
        return "Compact";
    }

    @Override
    protected Pair<Integer, Integer> entryBackground() {
        return EntryBackground.DEFAULT;
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(OccultismBlocks.STORAGE_CONTROLLER_STABILIZED.asItem());
    }

    @Override
    protected String entryId() {
        return ENTRY_ID;
    }
}
