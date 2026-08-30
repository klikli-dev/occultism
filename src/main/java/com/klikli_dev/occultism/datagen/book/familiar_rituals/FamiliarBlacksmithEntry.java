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

public class FamiliarBlacksmithEntry extends EntryProvider {

    public static final String ENTRY_ID = "familiar_blacksmith";

    public FamiliarBlacksmithEntry(CategoryProvider parent) {
        super(parent);
    }

    @Override
    protected void generatePages() {
        this.page("entity", () -> BookEntityPageModel.create()
                .withEntityId("occultism:blacksmith_familiar")
                .withText(this.context().pageText())
                .withScale(1.1f)
                .withOffset(-0.4f));
        this.pageText("Strike while the iron is hot.");

        this.page("ritual", () -> BookRitualRecipePageModel.create()
                .withRecipeId1(this.modLoc("ritual/familiar_blacksmith")));

        this.page("ability", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText()));
        this.pageTitle("Ability");
        this.pageText("""
                        While enabled, whenever the player picks up stone or cobblestone, there is a chance
                         (33%% by default, server config) for the Blacksmith Familiar to repair their equipment a little bit.
                        \\
                        \\
                        **Upgrade Behaviour**:
                        \\
                        Slowly repairs **one** equipment in the owner's inventory (each ~6 seconds, server config).
                        \\
                        \\
                        **Iesnium Behaviour**:
                        \\
                        Slowly repairs **all** equipment in the owner's inventory (each ~6 seconds, server config).
                        """
        );

        this.page("upgrading", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText()));
        this.pageTitle("Upgrading Familiars");
        this.pageText("""
                        To upgrade familiars the blacksmith needs to be given iron ingots or blocks by {0} it
                         (requires 18 ingots by default, server config).
                        \\
                        An iron ingot will appear on the blacksmith''s anvil for each upgrade he has enough materials to perform.
                        \\
                        Be patient after upgrading a familiar, as there is a cooldown before another one can be upgraded.
                        """,
                this.color("right-clicking", ChatFormatting.DARK_PURPLE)
        );

        this.page("upgraded", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText()));
        this.pageTitle("Upgraded Familiars");
        this.pageText("""
                        When the blacksmith upgrades a familiar, a message appears in the chat,
                         an anvil sound is emitted, and a star appears as **suffix** of the familiar's name.
                        \\
                        Upgraded familiars has better stats (+3 damage bonus and +20 max health)
                         and provide additional benefits described in their entries.
                        """,
                this.color("right-clicking", ChatFormatting.DARK_PURPLE)
        );

        this.page("advanced_anvil", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText()));
        this.pageTitle("Better Anvil");
        this.pageText("""
                        After upgrading a Blacksmith Familiar, you can give him an {0},
                         allowing him to perform advanced upgrades on other **upgraded** familiars.
                        \\
                        \\
                        References to Iesnium familiars refer to this final upgrade.
                        """,
                this.entryLink("Iesnium Anvil", "crafting_rituals", "craft_iesnium_anvil")
        );

        this.page("iesnium", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText()));
        this.pageTitle("Iesnium Familiars");
        this.pageText("""
                        The Iesnium Upgrade works like a regular upgrade, but consumes Iesnium instead of iron.
                         (requires 32 ingots by default, server config).
                        \\
                        Other differences include the name star appearing as **prefix** of the name
                         and a higher stats bonus (+9 extra damage and +50 max health).
                        """
        );

        this.page("curio", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText()));
        this.pageTitle("Equipping");
        this.pageText("""
                        When captured in a Familiar Ring or any Infused Equipment it can still repair the user's equipment.
                        """
        );
    }

    @Override
    protected String entryName() {
        return "Blacksmith Familiar";
    }

    @Override
    protected String entryDescription() {
        return "Support";
    }

    @Override
    protected GuiSprite entryBackground() {
        return EntryBackground.DEFAULT;
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(this.modLoc("textures/gui/book/familiar_blacksmith.png"));
    }

    @Override
    protected String entryId() {
        return ENTRY_ID;
    }
}
