package com.klikli_dev.occultism.datagen.book.familiar_rituals;

import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.EntryBackground;
import com.klikli_dev.modonomicon.api.datagen.EntryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookEntityPageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookTextPageModel;
import com.klikli_dev.modonomicon.client.gui.book.theme.GuiSprite;
import com.klikli_dev.occultism.integration.modonomicon.pages.BookRitualRecipePageModel;

public class FamiliarGuardianEntry extends EntryProvider {

    public static final String ENTRY_ID = "familiar_guardian";

    public FamiliarGuardianEntry(CategoryProvider parent) {
        super(parent);
    }

    @Override
    protected void generatePages() {
        this.page("entity", () -> BookEntityPageModel.create()
                .withEntityId("occultism:guardian_familiar{for_book:true}")
                .withText(this.context().pageText())
                .withScale(0.9f)
                .withOffset(-0.6f));
        this.pageText("I'd rather die than lose my life.");

        this.page("ritual", () -> BookRitualRecipePageModel.create()
                .withRecipeId1(this.modLoc("ritual/familiar_guardian")));

        this.page("ability", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText()));
        this.pageTitle("Ability");
        this.pageText("""
                        The guardian familiar sacrifices a limb everytime it's master
                         is about to die and thus **prevents the death**.
                         Once the guardian dies, the player is no longer protected.
                        \\
                        \\
                        When summoned, the guardian spawns with a **random amount of limbs**,
                         there is no guarantee that a complete guardian is summoned.
                        \\
                        \\
                        Regains one limb for each Blacksmith Familiar upgrade it receives.
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
                        **Upgraded:** Health Boost (level is equal to the number of limbs).
                        \\
                        \\
                        **Iesnium:** Resistance (level is equal to half the number of limbs).
                        """,
                this.entryLink("Familiar Tablet", "familiar_rituals", "tablet")
        );

        this.page("curio", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText()));
        this.pageTitle("Equipping");
        this.pageText("""
                        When captured in a Familiar Ring or any Infused Equipment
                         the death protection no longer costs limbs. Instead, the
                         wearer receives a cooldown effect whose duration varies
                         depending on the number of limbs and the Guardian's Familiar level.
                        """
        );
    }

    @Override
    protected String entryName() {
        return "Guardian Familiar";
    }

    @Override
    protected String entryDescription() {
        return "Defense";
    }

    @Override
    protected GuiSprite entryBackground() {
        return EntryBackground.DEFAULT;
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(this.modLoc("textures/gui/book/familiar_guardian.png"));
    }

    @Override
    protected String entryId() {
        return ENTRY_ID;
    }
}
