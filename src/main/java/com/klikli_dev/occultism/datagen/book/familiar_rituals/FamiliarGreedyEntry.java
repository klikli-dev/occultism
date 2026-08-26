package com.klikli_dev.occultism.datagen.book.familiar_rituals;

import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.EntryBackground;
import com.klikli_dev.modonomicon.api.datagen.EntryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookEntityPageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookTextPageModel;
import com.klikli_dev.modonomicon.client.gui.book.theme.GuiSprite;
import com.klikli_dev.occultism.integration.modonomicon.pages.BookRitualRecipePageModel;
import net.minecraft.ChatFormatting;

public class FamiliarGreedyEntry extends EntryProvider {

    public static final String ENTRY_ID = "familiar_greedy";

    public FamiliarGreedyEntry(CategoryProvider parent) {
        super(parent);
    }

    @Override
    protected void generatePages() {
        this.page("entity", () -> BookEntityPageModel.create()
                .withEntityId("occultism:greedy_familiar")
                .withText(this.context().pageText())
                .withScale(1.1f)
                .withOffset(-0.4f));
        this.pageText("I'll become a great dragon rider, just like Jonas Peralvilho.");

        this.page("ritual", () -> BookRitualRecipePageModel.create()
                .withRecipeId1(this.modLoc("ritual/familiar_greedy")));

        this.page("ability", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText()));
        this.pageTitle("Ability");
        this.pageText("""
                        The Greedy Familiar is a Foliot that will pick up nearby items for it''s master.
                         {0} with empty hand to open a screen that can set a {1}.
                        \\
                        \\
                        When upgraded by a Blacksmith Familiar, it can find blocks for its master.
                         {2} it with a block to tell it what to look for.
                        """,
                this.color("Shift-right-click", ChatFormatting.DARK_PURPLE),
                this.entryLink("Spirit Filter", "summoning_rituals", "transporter_filters"),
                this.color("Right-click", ChatFormatting.DARK_PURPLE)
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
                this.entryLink("Greedy Harvest", "getting_started", "effects@greedy_harvest")
        );

        this.page("immunity", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText()));
        this.pageTitle("Immunities");
        this.pageText("""
                        While active, it provides additional defenses for its owner, granting different immunities depending on its level.
                        \\
                        \\
                        **Iesnium:** Mining Fatigue effect.
                        """
        );

        this.page("curio", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText()));
        this.pageTitle("Equipping");
        this.pageText("""
                        When captured in a Familiar Ring or any Infused Equipment it increased the pick-up range of the wearer.
                        """
        );
    }

    @Override
    protected String entryName() {
        return "Greedy Familiar";
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
        return BookIconModel.create(this.modLoc("textures/gui/book/familiar_greedy.png"));
    }

    @Override
    protected String entryId() {
        return ENTRY_ID;
    }
}
