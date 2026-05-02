package com.klikli_dev.occultism.datagen.book.storage_system;

import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.EntryBackground;
import com.klikli_dev.modonomicon.api.datagen.EntryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookTextPageModel;
import com.klikli_dev.modonomicon.client.gui.book.theme.GuiSprite;
import net.minecraft.world.item.Items;

public class StorageOverviewEntry extends EntryProvider {

    public static final String ENTRY_ID = "overview";

    public StorageOverviewEntry(CategoryProvider parent) {
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
        return "Storage System";
    }

    @Override
    protected void generatePages() {
        this.page("intro", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText()));
        this.pageTitle("Magic Storage");
        this.pageText("""
                Every summoner knows the problem: There are just too many occult paraphernalia lying around.
                 The solution is simple, yet elegant: Magic Storage!
                \\
                \\
                Using Spirits able to access storage dimensions it is possible to create almost unlimited storage space.
                """
        );

        this.page("intro2", () -> BookTextPageModel.create()
                .withText(this.context().pageText()));
        this.pageText("""
                        Follow the steps shown in this category to get your own storage system!
                         The steps related to storage in {0} show only the rituals,
                         while here **all required steps** including crafting are shown.
                        """,
                this.categoryLink("Binding Rituals", "crafting_rituals")
        );
    }

    @Override
    protected GuiSprite entryBackground() {
        return EntryBackground.CATEGORY_START;
    }

    @Override
    protected String entryId() {
        return ENTRY_ID;
    }
}
