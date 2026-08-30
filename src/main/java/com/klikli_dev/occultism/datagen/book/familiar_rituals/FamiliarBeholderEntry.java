package com.klikli_dev.occultism.datagen.book.familiar_rituals;

import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.EntryBackground;
import com.klikli_dev.modonomicon.api.datagen.EntryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookEntityPageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookTextPageModel;
import com.klikli_dev.modonomicon.client.gui.book.theme.GuiSprite;
import com.klikli_dev.occultism.integration.modonomicon.pages.BookRitualRecipePageModel;

public class FamiliarBeholderEntry extends EntryProvider {

    public static final String ENTRY_ID = "familiar_beholder";

    public FamiliarBeholderEntry(CategoryProvider parent) {
        super(parent);
    }

    @Override
    protected void generatePages() {
        this.page("entity", () -> BookEntityPageModel.create()
                .withEntityId("occultism:beholder_familiar")
                .withText(this.context().pageText())
                .withScale(1.5f)
                .withOffset(-0.1f));
        this.pageText("Large Aberration, Lawful Evil, CR 13.\\\n STR 16. DEX 14. CON 18. INT 17. WIS 15. CHA 17.");

        this.page("ritual", () -> BookRitualRecipePageModel.create()
                .withRecipeId1(this.modLoc("ritual/familiar_beholder")));

        this.page("ability", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText()));
        this.pageTitle("Ability");
        this.pageText("""
                        While enabled, the Beholder Familiar highlights nearby entities with a glow effect, and shoots laser rays at enemies.
                        \\
                         It **eats** (poor) **Shub Niggurath babies** to gain temporary damage and speed.
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
                        **Iesnium:** {1}.
                        """,
                this.entryLink("Familiar Tablet", "familiar_rituals", "tablet"),
                this.entryLink("Herald of Aberrations", "getting_started", "effects@herald_aberrations")
        );

        this.page("immunity", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText()));
        this.pageTitle("Immunities");
        this.pageText("""
                        While active, it provides additional defenses for its owner, granting different immunities depending on its level.
                        \\
                        \\
                        **Upgraded:** Blindness effect.
                        \\
                        \\
                        **Iesnium:** Darkness effect.
                        """
        );

        this.page("curio", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText()));
        this.pageTitle("Equipping");
        this.pageText("""
                        When captured in a Familiar Ring or any Infused Equipment it can still apply glow.
                        """
        );
    }

    @Override
    protected String entryName() {
        return "Beholder Familiar";
    }

    @Override
    protected String entryDescription() {
        return "Attack | Defense | Influence";
    }

    @Override
    protected GuiSprite entryBackground() {
        return EntryBackground.DEFAULT;
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(this.modLoc("textures/gui/book/familiar_beholder.png"));
    }

    @Override
    protected String entryId() {
        return ENTRY_ID;
    }
}
