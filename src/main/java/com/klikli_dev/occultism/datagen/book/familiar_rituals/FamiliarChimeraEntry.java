package com.klikli_dev.occultism.datagen.book.familiar_rituals;

import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.EntryBackground;
import com.klikli_dev.modonomicon.api.datagen.EntryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookEntityPageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookTextPageModel;
import com.klikli_dev.modonomicon.client.gui.book.theme.GuiSprite;
import com.klikli_dev.occultism.integration.modonomicon.pages.BookRitualRecipePageModel;

public class FamiliarChimeraEntry extends EntryProvider {

    public static final String ENTRY_ID = "familiar_chimera";

    public FamiliarChimeraEntry(CategoryProvider parent) {
        super(parent);
    }

    @Override
    protected void generatePages() {
        this.page("entity", () -> BookEntityPageModel.create()
                .withEntityId("occultism:chimera_familiar")
                .withText(this.context().pageText())
                .withScale(1.5f)
                .withOffset(-0.6f));
        this.pageText("Not related to Shou Tucker in the slightest.");

        this.page("ritual", () -> BookRitualRecipePageModel.create()
                .withRecipeId1(this.modLoc("ritual/familiar_chimera")));

        this.page("description", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText()));
        this.pageTitle("Description");
        this.pageText("""
                        The chimera familiar can be fed (any) meat to grow,
                         when growing it will gain damage and speed.
                         Once it has grown big enough, players can ride it.
                         \\
                         \\
                         **Warning:** It will gradually shrink if it has not yet received the Blacksmith Upgrade.
                        """
        );

        this.page("attacks", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText()));
        this.pageTitle("Attacks");
        this.pageText("""
                        Whenever the Chimera attacks, an additional ability will be triggered.
                        Depending on the type of attack.
                        \\
                        **Default:**
                         - LION: set on fire.
                         - SNAKE: apply poison.
                         - GOAT: great knockback.
                        \\
                        \\
                        **Upgraded:** .
                         - PAW: stun the enemy;
                        \\
                        \\
                        **Iesnium:**
                        \\
                        Can apply two of the abilities listed above per hit.
                        """
        );

        this.page("goat", () -> BookEntityPageModel.create()
                .withEntityId("occultism:goat_familiar")
                .withText(this.context().pageText())
                .withScale(1.0f)
                .withOffset(-0.4f));
        this.pageText("When you feed the Chimera a [](item://minecraft:golden_apple), the [#](ad03fc)Goat[#]() will detach and become a separate familiar.");

        this.page("bell", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText()));
        this.pageTitle("Goat Bell");
        this.pageText("""
                        When **upgraded** by a Blacksmith Familiar, the Goat Familiar will get a warning bell.
                         When you hit the familiar it will ring the bell and attract enemies in a large radius.
                        """
        );
    }

    @Override
    protected String entryName() {
        return "Chimera Familiar";
    }

    @Override
    protected String entryDescription() {
        return "Movement | Attack | Support";
    }

    @Override
    protected GuiSprite entryBackground() {
        return EntryBackground.DEFAULT;
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(this.modLoc("textures/gui/book/familiar_chimera.png"));
    }

    @Override
    protected String entryId() {
        return ENTRY_ID;
    }
}
