package com.klikli_dev.occultism.datagen.book.familiar_rituals;

import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.EntryBackground;
import com.klikli_dev.modonomicon.api.datagen.EntryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookEntityPageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookTextPageModel;
import com.klikli_dev.modonomicon.client.gui.book.theme.GuiSprite;
import com.klikli_dev.occultism.integration.modonomicon.pages.BookRitualRecipePageModel;

public class FamiliarBeaverEntry extends EntryProvider {

    public static final String ENTRY_ID = "familiar_beaver";

    public FamiliarBeaverEntry(CategoryProvider parent) {
        super(parent);
    }

    @Override
    protected void generatePages() {
        this.page("entity", () -> BookEntityPageModel.create()
                .withEntityId("occultism:beaver_familiar")
                .withText(this.context().pageText())
                .withScale(0.8f)
                .withOffset(-0.2f));
        this.pageText("A well-rounded beaver with experience in various fields, most notably at lumber mills, snack factories, and mattress stores.");

        this.page("ritual", () -> BookRitualRecipePageModel.create()
                .withRecipeId1(this.modLoc("ritual/familiar_beaver")));

        this.page("ability", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText()));
        this.pageTitle("Ability");
        this.pageText("""
                        While enabled, the Beaver Familiar will chop down nearby trees
                         when they grow from a sapling into a tree. It can only handle small trees.
                        \\
                        \\
                        **Upgrade Behaviour**
                        \\
                        Gives free snacks when shift-right-clicked with an empty hand.
                        \\
                        \\
                        **Iesnium Behaviour**
                        \\
                        While enabled, allows its owner to attack Creakings directly without needing to destroy the Creaking Heart.
                        """
        );

        this.page("effects", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText()));
        this.pageTitle("Effects");
        this.pageText("""
                        Different effects can be applied depending on the familiar''s level.
                        You can configure them using the {0}.
                        \\
                        \\
                        **Default:** {1}.
                        """,
                this.entryLink("Familiar Tablet", "familiar_rituals", "tablet"),
                this.entryLink("Beaver Harvest", "getting_started", "effects@beaver_harvest")
        );
    }

    @Override
    protected String entryName() {
        return "Beaver Familiar";
    }

    @Override
    protected String entryDescription() {
        return "Utility";
    }

    @Override
    protected GuiSprite entryBackground() {
        return EntryBackground.DEFAULT;
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(this.modLoc("textures/gui/book/familiar_beaver.png"));
    }

    @Override
    protected String entryId() {
        return ENTRY_ID;
    }
}
