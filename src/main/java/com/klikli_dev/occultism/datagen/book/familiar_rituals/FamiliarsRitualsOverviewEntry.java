package com.klikli_dev.occultism.datagen.book.familiar_rituals;

import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.EntryBackground;
import com.klikli_dev.modonomicon.api.datagen.EntryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookTextPageModel;
import com.klikli_dev.modonomicon.client.gui.book.theme.GuiSprite;

public class FamiliarsRitualsOverviewEntry extends EntryProvider {

    public static final String ENTRY_ID = "overview";

    public FamiliarsRitualsOverviewEntry(CategoryProvider parent) {
        super(parent);
    }

    @Override
    protected void generatePages() {
        this.page("intro", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText()));
        this.pageTitle("Familiar Rituals");
        this.pageText("""
                        Familiar rituals summon spirits to aid the summoner directly.
                         The spirits usually inhabit an animal''s body, allowing them to resist essence decay.
                         Familiars provide buffs, but may also actively protect the summoner.
                         \\
                         \\
                         Effects and abilities can be configured using a {0}.
                        """,
                this.entryLink("Familiar Tablet", "familiar_rituals", "tablet")
        );

        this.page("ring", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText()));
        this.pageTitle("Equipping Familiars");
        this.pageText("""
                        Enterprising summoners have found a way to bind familiars
                         into jewelry that passively applies their buff and some abilities,
                         the {0}, Infused {1} and {2}.
                        """,
                this.entryLink("Familiar Ring", "familiar_rituals", "tablet"),
                this.entryLink("Armor", "crafting_rituals", "craft_infused_armor"),
                this.entryLink("Tools", "crafting_rituals", "craft_infused_tools")
        );

        this.page("trading", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText()));
        this.pageTitle("Trading Familiars");
        this.pageText("""
                        Familiars can be easily traded when in one of the equipments listed before.
                        \\
                        When released, the spirit will recognize the person releasing them as their new master.
                        """
        );
    }

    @Override
    protected String entryName() {
        return "Familiar Rituals";
    }

    @Override
    protected String entryDescription() {
        return "Occultist best friends";
    }

    @Override
    protected GuiSprite entryBackground() {
        return EntryBackground.SQUARE_GRAY;
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(this.modLoc("textures/gui/book/familiar.png"));
    }

    @Override
    protected String entryId() {
        return ENTRY_ID;
    }
}
