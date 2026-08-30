package com.klikli_dev.occultism.datagen.book.familiar_rituals;

import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.EntryBackground;
import com.klikli_dev.modonomicon.api.datagen.EntryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookEntityPageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookTextPageModel;
import com.klikli_dev.modonomicon.client.gui.book.theme.GuiSprite;
import com.klikli_dev.occultism.integration.modonomicon.pages.BookRitualRecipePageModel;
import net.minecraft.world.item.Items;

public class FamiliarDragonEntry extends EntryProvider {

    public static final String ENTRY_ID = "familiar_dragon";

    public FamiliarDragonEntry(CategoryProvider parent) {
        super(parent);
    }

    @Override
    protected void generatePages() {
        this.page("entity", () -> BookEntityPageModel.create()
                .withEntityId("occultism:dragon_familiar")
                .withText(this.context().pageText())
                .withScale(0.7f)
                .withOffset(-0.3f));
        this.pageText("Loves Gold Nuggets... and Sticks.");

        this.page("ritual", () -> BookRitualRecipePageModel.create()
                .withRecipeId1(this.modLoc("ritual/familiar_dragon")));

        this.page("ability", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText()));
        this.pageTitle("Ability");
        this.pageText("""
                        The Dragon Familiar will collect sticks from the ground and bring them to you.
                        \\
                        \\
                        Greedy familiars can ride on dragon familiars, combining their powers.
                        \\
                        \\
                        When upgraded by a Blacksmith Familiar, it will throw swords at nearby enemies.
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
                        \\
                        \\
                        **Iesnium:** Hero of the Village.
                        \\
                        \\
                        You can give it {2} to increase its effects max level for 5 minutes.
                        """,
                this.entryLink("Familiar Tablet", "familiar_rituals", "tablet"),
                this.entryLink("Dragon's Greed", "getting_started", "effects@dragon_greed"),
                this.itemLink(Items.GOLD_NUGGET)
        );

        this.page("immunity", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText()));
        this.pageTitle("Immunities");
        this.pageText("""
                        While active, it provides additional defenses for its owner, granting different immunities depending on its level.
                        \\
                        \\
                        **Iesnium:** Bad/Raid/Trial Omen effects.
                        """
        );

        this.page("curio", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText()));
        this.pageTitle("Equipping");
        this.pageText("""
                        When captured in a Familiar Ring or any Infused Equipment it will continue throwing swords at nearby monsters.
                        """
        );
    }

    @Override
    protected String entryName() {
        return "Dragon Familiar";
    }

    @Override
    protected String entryDescription() {
        return "Utility | Attack";
    }

    @Override
    protected GuiSprite entryBackground() {
        return EntryBackground.DEFAULT;
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(this.modLoc("textures/gui/book/familiar_dragon.png"));
    }

    @Override
    protected String entryId() {
        return ENTRY_ID;
    }
}
