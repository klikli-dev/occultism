package com.klikli_dev.occultism.datagen.book.getting_started;

import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.EntryBackground;
import com.klikli_dev.modonomicon.api.datagen.EntryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookTextPageModel;
import com.klikli_dev.modonomicon.client.gui.book.theme.GuiSprite;
import net.minecraft.world.item.Items;

public class BooksOfBindingAutomationEntry extends EntryProvider {

    public static final String ENTRY_ID = "books_of_binding_automation";

    public BooksOfBindingAutomationEntry(CategoryProvider parent) {
        super(parent);
    }

    @Override
    protected void generatePages() {
        this.page("intro", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText()));
        this.pageTitle("The Problem");
        this.pageText("Bound Books of Binding are generated with a random spirit name. This tricks many automated crafting processes into no longer recognizing the item as the requested crafting result, because it does not expect NBT/Data Components on the item.\n\\\n\\\nThis leads to stuck crafting processes.\n");

        this.page("solution", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText()));
        this.pageTitle("The Solution");
        this.pageText("1. Put a dictionary of spirits into an anvil and give it a name. This will be the name of all spirits summoned in the future.\n2. Use this dictionary to configure crafting patterns (if your automation mod requires it).\n3. Use this dictionary to craft the Bound Books of Binding in the automation system. As usual, the dictionary will not be used up.\n4. All crafted books will now have the same name and will be recognized by your automation system.\n");
    }

    @Override
    protected String entryName() {
        return "Books of Binding in Automation";
    }

    @Override
    protected String entryDescription() {
        return "Tips for using books of binding in Crafting Automation such as AE2 or RS";
    }

    @Override
    protected GuiSprite entryBackground() {
        return EntryBackground.DEFAULT;
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(Items.CRAFTER);
    }

    @Override
    protected String entryId() {
        return ENTRY_ID;
    }
}
