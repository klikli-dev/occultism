package com.klikli_dev.occultism.datagen.book.binding_rituals;

import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.EntryBackground;
import com.klikli_dev.modonomicon.api.datagen.EntryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.condition.BookEntryReadConditionModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookSpotlightPageModel;
import com.klikli_dev.modonomicon.client.gui.book.theme.GuiSprite;
import com.klikli_dev.occultism.datagen.book.PentaclesCategory;
import com.klikli_dev.occultism.datagen.book.pentacles.CraftDjinniEntry;
import com.klikli_dev.occultism.registry.OccultismBlocks;
import com.klikli_dev.occultism.registry.OccultismItems;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

public class StorageSystemEntry extends EntryProvider {

    public static final String ENTRY_ID = "craft_storage_system";

    public StorageSystemEntry(CategoryProvider parent) {
        super(parent);
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(Items.CHEST);
    }

    @Override
    protected String entryName() {
        return "Magic Storage";
    }

    @Override
    protected String entryDescription() {
        return "Hold my items";
    }

    @Override
    protected void generatePages() {
        this.page("satchels", () -> BookSpotlightPageModel.create()
                .withItem(Ingredient.of(OccultismItems.SATCHEL.get()))
                .withText(this.context().pageText()));
        this.pageText("""
                Occultism offers two storage solutions: the first is based on satchels, portable inventories with different functions depending on the type.
                 Some are already obtainable, while others will require evolve in the mod first.
                """);

        this.page("spotlight", () -> BookSpotlightPageModel.create()
                        .withItem(Ingredient.of(OccultismBlocks.STORAGE_CONTROLLER.get()))
                        .withText(this.context().pageText()))
                .withCondition(BookEntryReadConditionModel.create().withEntry(
                        this.modId() + ":" + PentaclesCategory.CATEGORY_ID + "/" + CraftDjinniEntry.ENTRY_ID));
        this.pageText("""
                        The second option is to follow the entries below that show the rituals related to the Magic Storage system.
                         For full step-by-step instructions on building the storage system, see the {0} category.
                        """,
                this.categoryLink("Magic Storage", "storage")
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
