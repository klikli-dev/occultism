package com.klikli_dev.occultism.datagen.book.rituals;

import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.EntryBackground;
import com.klikli_dev.modonomicon.api.datagen.EntryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookTextPageModel;
import com.klikli_dev.modonomicon.client.gui.book.theme.GuiSprite;

public class RitualOverviewEntry extends EntryProvider {
    public static final String ENTRY_ID = "overview";
    public RitualOverviewEntry(CategoryProvider parent) { super(parent); }
    @Override protected BookIconModel entryIcon() { return BookIconModel.create(this.modLoc("textures/gui/book/robe.png")); }
    @Override protected String entryName() { return "Rituals"; }
    @Override protected String entryDescription() { return ""; }
    @Override protected void generatePages() {
        this.page("intro", () -> BookTextPageModel.create().withTitle(this.context().pageTitle()).withText(this.context().pageText()));
        this.pageTitle("Rituals");
        this.pageText("Rituals allow to summon spirits into our plane of existence, or bind them into objects or living beings. Every ritual consists of a [#]({0})Pentacle[#](), [#]({0})Ritual Ingredients[#]() provided via sacrificial bowls, a [#]({0})Starting Item[#]() and optionally the [#]({0})Sacrifice[#]() of living beings. A purple particle effect will show that the ritual is successful and in progress.", "ad03fc");
        this.page("steps", () -> BookTextPageModel.create().withTitle(this.context().pageTitle()).withText(this.context().pageText()));
        this.pageTitle("Performing a Ritual");
        this.pageText("Rituals always follow the same steps:\n- Draw the pentacle.\n- Place a golden ritual bowl.\n- Place sacrificial bowls.\n- Put ingredients in bowls.\n- [#]({0})Right-click[#]()the golden bowl with the activation item.\n- *Optional: Perform a sacrifice close to the center of the pentacle.*", "ad03fc");
        this.page("additional_requirements", () -> BookTextPageModel.create().withTitle(this.context().pageTitle()).withText(this.context().pageText()));
        this.pageTitle("Additional Requirements");
        this.pageText("If a ritual shows grey particles above the golden ritual bowl, then additional requirements as described in the ritual's page need to be fulfilled. Once all requirements are fulfilled, the ritual will show purple particles and start to consume the items in the sacrificial bowls.");
    }
    @Override protected GuiSprite entryBackground() { return EntryBackground.STAR_GOLD; }
    @Override protected String entryId() { return ENTRY_ID; }
}
