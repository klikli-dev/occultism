package com.klikli_dev.occultism.datagen.book.familiar_rituals;

import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.EntryBackground;
import com.klikli_dev.modonomicon.api.datagen.EntryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookEntityPageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookTextPageModel;
import com.klikli_dev.modonomicon.client.gui.book.theme.GuiSprite;
import com.klikli_dev.occultism.integration.modonomicon.pages.BookRitualRecipePageModel;

public class FamiliarBatEntry extends EntryProvider {

    public static final String ENTRY_ID = "familiar_bat";

    public FamiliarBatEntry(CategoryProvider parent) {
        super(parent);
    }

    @Override
    protected void generatePages() {
        this.page("entity", () -> BookEntityPageModel.create()
                .withEntityId("occultism:bat_familiar")
                .withText(this.context().pageText())
                .withScale(0.8f)
                .withOffset(-0.2f));
        this.pageText("This cute little bat definitely won't turn you into a vampire.");

        this.page("ritual", () -> BookRitualRecipePageModel.create()
                .withRecipeId1(this.modLoc("ritual/familiar_bat")));

        this.page("ability", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText()));
        this.pageTitle("Ability");
        this.pageText("""
                        While enabled, the Bat Familiar will hunt and devour ordinary bats,
                         sharing part of its meal with its owner.
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
                        **Default:** Night Vision.
                        \\
                        \\
                        **Upgraded:** {1}.
                        \\
                        \\
                        **Iesnium:** {2}.
                        """,
                this.entryLink("Familiar Tablet", "familiar_rituals", "tablet"),
                this.entryLink("Life-Steal", "getting_started", "effects@life_steal"),
                this.entryLink("Bat-Flight", "getting_started", "effects@bat_flight")
        );
    }

    @Override
    protected String entryName() {
        return "Bat Familiar";
    }

    @Override
    protected String entryDescription() {
        return "Utility | Support | Movement";
    }

    @Override
    protected GuiSprite entryBackground() {
        return EntryBackground.DEFAULT;
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(this.modLoc("textures/gui/book/bat_familiar.png"));
    }

    @Override
    protected String entryId() {
        return ENTRY_ID;
    }
}
