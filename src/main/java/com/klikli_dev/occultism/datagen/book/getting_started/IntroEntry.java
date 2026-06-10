package com.klikli_dev.occultism.datagen.book.getting_started;

import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.EntryBackground;
import com.klikli_dev.modonomicon.api.datagen.EntryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookTextPageModel;
import com.klikli_dev.modonomicon.client.gui.book.theme.GuiSprite;
import com.klikli_dev.occultism.registry.OccultismItems;

public class IntroEntry extends EntryProvider {

    public static final String ENTRY_ID = "intro";

    public IntroEntry(CategoryProvider parent) {
        super(parent);
    }

    @Override
    protected void generatePages() {
        this.page("intro", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText()));
        this.pageTitle("About");
        this.pageText("This book aims to introduce the novice reader to the most common summoning rituals and equip them with a list of spirit names to summon.\nThe authors advise caution in the summoning of the listed entities and does not take responsibility for any harm caused.\n");

        this.page("help", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText()));
        this.pageTitle("Getting Help");
        this.pageText("If you run into any trouble while playing Occultism, please join our Discord server and ask for help.\n\\\n\\\n[Join us at https://discord.gg/trE4SHRXvb](https://discord.gg/trE4SHRXvb)\n");
    }

    @Override
    protected String entryName() {
        return "About";
    }

    @Override
    protected String entryDescription() {
        return "About using the Dictionary of Spirits";
    }

    @Override
    protected GuiSprite entryBackground() {
        return EntryBackground.SQUARE_GRAY;
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(OccultismItems.DICTIONARY_OF_SPIRITS_ICON.get());
    }

    @Override
    protected String entryId() {
        return ENTRY_ID;
    }
}
