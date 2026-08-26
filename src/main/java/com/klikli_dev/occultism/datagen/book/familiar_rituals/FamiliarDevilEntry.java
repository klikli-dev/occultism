package com.klikli_dev.occultism.datagen.book.familiar_rituals;

import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.EntryBackground;
import com.klikli_dev.modonomicon.api.datagen.EntryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookEntityPageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookTextPageModel;
import com.klikli_dev.modonomicon.client.gui.book.theme.GuiSprite;
import com.klikli_dev.occultism.integration.modonomicon.pages.BookRitualRecipePageModel;
import com.klikli_dev.occultism.registry.OccultismItems;
import net.minecraft.world.item.Items;

public class FamiliarDevilEntry extends EntryProvider {

    public static final String ENTRY_ID = "familiar_devil";

    public FamiliarDevilEntry(CategoryProvider parent) {
        super(parent);
    }

    @Override
    protected void generatePages() {
        this.page("entity", () -> BookEntityPageModel.create()
                .withEntityId("occultism:devil_familiar")
                .withText(this.context().pageText())
                .withScale(1.4f)
                .withOffset(-0.3f));
        this.pageText("I have an offer you can't refuse.");

        this.page("ritual", () -> BookRitualRecipePageModel.create()
                .withRecipeId1(this.modLoc("ritual/familiar_devil")));

        this.page("ability", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText()));
        this.pageTitle("Ability");
        this.pageText("""
                        While enabled, the Devil Familiar attacks and burns nearby enemies.
                        \\
                        \\
                        When upgraded by a Blacksmith Familiar, you can give a {0} or {1} to obtain the enchanted version.
                        \\
                        **Warning:** This conversion has a five-minute cooldown if the Devil does not have the Iesnium Upgrade.
                        """,
                this.itemLink(Items.GOLDEN_APPLE),
                this.itemLink(OccultismItems.PITAYA_GOLDEN)
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
                        **Default:** Fire Resistance.
                        \\
                        \\
                        **Iesnium:** {1}.
                        """,
                this.entryLink("Familiar Tablet", "familiar_rituals", "tablet"),
                this.entryLink("Emperor of the Nether", "getting_started", "effects@nether_emperor")
        );

        this.page("curio", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText()));
        this.pageTitle("Equipping");
        this.pageText("""
                        When captured in a Familiar Ring or any Infused Equipment it can still setting enemies on fire.
                        """
        );
    }

    @Override
    protected String entryName() {
        return "Devil Familiar";
    }

    @Override
    protected String entryDescription() {
        return "Attack | Defense | Conversion | Influence";
    }

    @Override
    protected GuiSprite entryBackground() {
        return EntryBackground.DEFAULT;
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(this.modLoc("textures/gui/book/familiar_devil.png"));
    }

    @Override
    protected String entryId() {
        return ENTRY_ID;
    }
}
