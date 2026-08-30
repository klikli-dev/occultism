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

public class FamiliarFairyEntry extends EntryProvider {

    public static final String ENTRY_ID = "familiar_fairy";

    public FamiliarFairyEntry(CategoryProvider parent) {
        super(parent);
    }

    @Override
    protected void generatePages() {
        this.page("entity", () -> BookEntityPageModel.create()
                .withEntityId("occultism:fairy_familiar")
                .withText(this.context().pageText())
                .withScale(0.9f)
                .withOffset(-0.3f));
        this.pageText("A little magic, a lot of glitter.");

        this.page("ritual", () -> BookRitualRecipePageModel.create()
                .withRecipeId1(this.modLoc("ritual/familiar_fairy")));

        this.page("ability", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText()));
        this.pageTitle("Ability");
        this.pageText("""
                        The Fairy familiar **keeps other familiars from dying** (with cooldown),
                         helps out other familiars with **beneficial effects** as needed
                         (speed, resistance, fire resistance, water breathing, slow falling).
                        \\
                        \\
                        When upgraded by a Blacksmith Familiar, you can give a {0} to transform in a {1}.
                        """,
                this.itemLink(Items.GLASS_BOTTLE),
                this.itemLink(Items.DRAGON_BREATH)
        );

        this.page("attacks", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText()));
        this.pageTitle("Attacks");
        this.pageText("""
                        Whenever the Fairy attacks, the enemies life force is drained and transferred to nearby familiars.
                        an additional effect will be applied to the target. Possibilities depend on the familiar level.
                        \\
                        **Default:**
                         - Slowness
                        \\
                        \\
                        **Upgraded:**
                         - Glowing
                         - Levitation
                        \\
                        \\
                        **Iesnium:**
                         - Poison
                         - Wither
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
                        **Upgraded:** Regeneration.
                        \\
                        \\
                        **Iesnium:** {1}.
                        """,
                this.entryLink("Familiar Tablet", "familiar_rituals", "tablet"),
                this.entryLink("Fairy Blessing", "getting_started", "effects@fairy_bless")
        );

        this.page("curio", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText()));
        this.pageTitle("Equipping");
        this.pageText("""
                        When captured in a Familiar Ring or any Infused Equipment it will heal other nearby familiars owned by the wearer.
                        """
        );
    }

    @Override
    protected String entryName() {
        return "Fairy Familiar";
    }

    @Override
    protected String entryDescription() {
        return "Support | Attack | Conversion";
    }

    @Override
    protected GuiSprite entryBackground() {
        return EntryBackground.DEFAULT;
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(this.modLoc("textures/gui/book/familiar_fairy.png"));
    }

    @Override
    protected String entryId() {
        return ENTRY_ID;
    }
}
