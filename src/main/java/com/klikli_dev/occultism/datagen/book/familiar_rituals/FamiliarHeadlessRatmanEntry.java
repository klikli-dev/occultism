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

public class FamiliarHeadlessRatmanEntry extends EntryProvider {

    public static final String ENTRY_ID = "familiar_headless";

    public FamiliarHeadlessRatmanEntry(CategoryProvider parent) {
        super(parent);
    }

    @Override
    protected void generatePages() {
        this.page("entity", () -> BookEntityPageModel.create()
                .withEntityId("occultism:headless_familiar")
                .withText(this.context().pageText())
                .withScale(0.8f)
                .withOffset(-0.3f));
        this.pageText("You can't live in fear.");

        this.page("ritual", () -> BookRitualRecipePageModel.create()
                .withRecipeId1(this.modLoc("ritual/familiar_headless")));

        this.page("ability", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText()));
        this.pageTitle("Ability");
        this.pageText("""
                        While enabled, The Headless Ratman Familiar steals the heads of nearby slain monsters.
                         It then grants its master double damage against that type of creature.
                        \\
                        When upgraded by a Blacksmith Familiar, it applies weakness to nearby mobs of the same type as the head it stole.
                        """
        );

        this.page("description", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText()));
        this.pageTitle("Lonely Rat");
        this.pageText("""
                        If the ratman drops **below 50%% health** it dies, but can then be rebuilt by their master.
                         This can be done by providing:
                         - 2x {0}
                         - 1x {1}
                         - 2x {2}
                         - 1x {3}
                        \\
                        \\
                        Once reconstructed, a Fairy Familiar can revive the rider.
                        """,
                this.itemLink(Items.WHEAT),
                this.itemLink(Items.HAY_BLOCK),
                this.itemLink(Items.STICK),
                this.itemLink(Items.CARVED_PUMPKIN)
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
                        **Upgraded:** {1}.
                        \\
                        \\
                        **Iesnium:** Strength.
                        """,
                this.entryLink("Familiar Tablet", "familiar_rituals", "tablet"),
                this.entryLink("Pumpkin Head", "getting_started", "effects@pumpkin_head")
        );

        this.page("immunity", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText()));
        this.pageTitle("Immunities");
        this.pageText("""
                        While active, it provides additional defenses for its owner, granting different immunities depending on its level.
                        \\
                        \\
                        **Iesnium:** Weakness effect.
                        """
        );
    }

    @Override
    protected String entryName() {
        return "Headless Ratman Familiar";
    }

    @Override
    protected String entryDescription() {
        return "Attack | Defense";
    }

    @Override
    protected GuiSprite entryBackground() {
        return EntryBackground.DEFAULT;
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(this.modLoc("textures/gui/book/familiar_headless_ratman.png"));
    }

    @Override
    protected String entryId() {
        return ENTRY_ID;
    }
}
