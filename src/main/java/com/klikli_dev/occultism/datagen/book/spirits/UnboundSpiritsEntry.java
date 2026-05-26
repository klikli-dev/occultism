package com.klikli_dev.occultism.datagen.book.spirits;

import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.EntryBackground;
import com.klikli_dev.modonomicon.api.datagen.EntryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookTextPageModel;
import com.klikli_dev.modonomicon.client.gui.book.theme.GuiSprite;

public class UnboundSpiritsEntry extends EntryProvider {

    public static final String ENTRY_ID = "unbound_spirits";

    public UnboundSpiritsEntry(CategoryProvider parent) {
        super(parent);
    }

    @Override
    protected void generatePages() {
        this.page("intro", () -> BookTextPageModel.create().withTitle(this.context().pageTitle()).withText(this.context().pageText()));
        this.pageTitle("Unbound Spirits");
        this.pageText("""
                Generally spirits are summoned [#]({0})bound[#](), which refers to any condition that keeps them under control of the summoner. A side effect of binding spells is that part of the spirit remains in [#]({0})The Other Place[#](), robbing them of large portions of the power, but at the same time also protecting their essence from foreign access in this world.
                """, "ad03fc");

        this.page("unbound", () -> BookTextPageModel.create().withTitle(this.context().pageTitle()).withText(this.context().pageText()));
        this.pageTitle("Forego the Leash");
        this.pageText("""
                In order to access a spirit's essence, or unleash it's full destructive power, it needs to be summoned [#]({0})unbound[#](). Unbound summonings use pentacles that are intentionally incomplete or unstable, allowing to call on the spirit, but not putting any constraints on it.
                """, "ad03fc");

        this.page("unbound2", () -> BookTextPageModel.create().withTitle(this.context().pageTitle()).withText(this.context().pageText()));
        this.pageTitle("Beware!");
        this.pageText("""
                The lack of restraints when summoning spirits unbound makes these rituals incredibly dangerous, but you may find that the rewards are worth the risk - and often there is no way around them to achieve certain results.
                """);

        this.page("essence", () -> BookTextPageModel.create().withTitle(this.context().pageTitle()).withText(this.context().pageText()));
        this.pageTitle("Spirit Essence");
        this.pageText("""
                Unbound summonings are the only way to obtain [Afrit Essence](entry://summoning_rituals/afrit_essence), a powerful substance required for crafting [](item://occultism:chalk_red) which is used for the most powerful binding pentacles.
                """);
    }

    @Override
    protected String entryName() {
        return "Unbound Spirits";
    }

    @Override
    protected String entryDescription() {
        return "Try not to lose your spirits!";
    }

    @Override
    protected GuiSprite entryBackground() {
        return EntryBackground.DEFAULT;
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(this.modLoc("textures/gui/book/unbound_spirits.png"));
    }

    @Override
    protected String entryId() {
        return ENTRY_ID;
    }
}
