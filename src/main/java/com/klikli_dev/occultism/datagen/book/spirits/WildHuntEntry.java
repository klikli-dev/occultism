package com.klikli_dev.occultism.datagen.book.spirits;

import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.EntryBackground;
import com.klikli_dev.modonomicon.api.datagen.EntryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookTextPageModel;
import com.klikli_dev.modonomicon.client.gui.book.theme.GuiSprite;

public class WildHuntEntry extends EntryProvider {

    public static final String ENTRY_ID = "wild_hunt";

    public WildHuntEntry(CategoryProvider parent) {
        super(parent);
    }

    @Override
    protected void generatePages() {
        this.page("intro", () -> BookTextPageModel.create().withTitle(this.context().pageTitle()).withText(this.context().pageText()));
        this.pageTitle("The Wild Hunt");
        this.pageText("""
                A group of legendary Greater Spirits, usually appearing in the form of wither skeletons, with their skeleton minions. The Greater Spirits are bound to their minions in such fashion that they are virtually invulnerable until their minions have been sent back to [#]({0})The Other Place[#]().
                """, "ad03fc");

        this.page("wither_skull", () -> BookTextPageModel.create().withTitle(this.context().pageTitle()).withText(this.context().pageText()));
        this.pageTitle("Wither Skeleton Skulls");
        this.pageText("""
                While it is incredibly dangerous to call on the Wild Hunt, some summoners have been known to do so for quick access to the rare wither skeleton skulls they are known to leave behind. Summoning the wild hunt is described in detail on the page on obtaining [Wither Skeleton Skulls](entry://possession_rituals/wither_skull).
                """);
    }

    @Override
    protected String entryName() {
        return "The Wild Hunt";
    }

    @Override
    protected String entryDescription() {
        return "You better watch out, you better not cry ...";
    }

    @Override
    protected GuiSprite entryBackground() {
        return EntryBackground.DEFAULT;
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(this.modLoc("textures/gui/book/wild_hunt.png"));
    }

    @Override
    protected String entryId() {
        return ENTRY_ID;
    }
}
