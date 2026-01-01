package com.klikli_dev.occultism.datagen.book.storage_system;

import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.EntryBackground;
import com.klikli_dev.modonomicon.api.datagen.EntryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookTextPageModel;
import com.mojang.datafixers.util.Pair;
import net.minecraft.world.item.Items;

public class AutomationEntry extends EntryProvider {

    public static final String ENTRY_ID = "storage_system_automation";

    public AutomationEntry(CategoryProvider parent) {
        super(parent);
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(Items.HOPPER);
    }

    @Override
    protected String entryName() {
        return "Storage Logistics";
    }

    @Override
    protected String entryDescription() {
        return "Inserting and extracting items from the Storage Actuator";
    }

    @Override
    protected void generatePages() {
        this.page("intro", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText()));
        this.pageTitle("Storage Logistics");
        this.pageText("""
                    The Storage Actuator behaves much like a big chest or shulker box.
                     That means, hoppers and pipes can insert and extract items.
                    """
        );

        this.page("performance", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText()));
        this.pageTitle("Performance");
        this.pageText("""
                   Due to the potentially huge amount of items in the storage system,
                    it is good to consider some performance aspects, otherwise your
                    system might slow down your game or even a server you are playing on.
                    """
        );

        this.page("extraction", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText()));
        this.pageTitle("Extracting Items");
        this.pageText("""
                    Extracting items can lead to performance issues, especially when pipes with Item Filters
                     are used, because then the entire huge storage is searched for these items one by one.
                     \\
                     \\
                     To *massively* improve performance, use Transporter Spirits to extract from the
                     Storage Actuator or Stable Wormhole. Even if the Spirit deposits into a chest
                     right next to the storage system, and a pipe extracts from that chest, the
                     performance is **much** better than if a pipe extracts directly.
                    """
        );

        this.page("insertion", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText()));
        this.pageTitle("Inserting Items");
        this.pageText("""
                    When inserting items, you do not need to do anything, the Storage Actuator will
                     maximize performance automatically for you. It is safe to insert high amounts
                     of items at high frequencies without negative impact on game performance.
                    """
        );
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
