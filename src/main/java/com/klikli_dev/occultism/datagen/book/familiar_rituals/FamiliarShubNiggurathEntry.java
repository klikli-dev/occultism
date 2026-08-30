package com.klikli_dev.occultism.datagen.book.familiar_rituals;

import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.EntryBackground;
import com.klikli_dev.modonomicon.api.datagen.EntryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookEntityPageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookTextPageModel;
import com.klikli_dev.modonomicon.client.gui.book.theme.GuiSprite;
import net.minecraft.ChatFormatting;
import net.minecraft.world.item.Items;

public class FamiliarShubNiggurathEntry extends EntryProvider {

    public static final String ENTRY_ID = "familiar_shub_niggurath";

    public FamiliarShubNiggurathEntry(CategoryProvider parent) {
        super(parent);
    }

    @Override
    protected void generatePages() {
        this.page("entity", () -> BookEntityPageModel.create()
                .withEntityId("occultism:shub_niggurath_familiar")
                .withText(this.context().pageText())
                .withScale(1.0f)
                .withOffset(-0.4f));
        this.pageText("The Black Goat of the Woods with a Thousand Young");

        this.page("ritual", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText()));
        this.pageTitle("Transformation");
        this.pageText("""
                        The {0} is not summoned directly. First, summon a {1} and feed it a {2} to detach the {3}.
                        \\
                        Bring the goat to a {4}. Then click the goat with {5}, {6} and {7} to obtain the {8}.
                        """,
                this.color("Shub Niggurath", ChatFormatting.DARK_PURPLE),
                this.entryLink("Chimera Familiar", "familiar_rituals", "familiar_chimera"),
                this.itemLink(Items.GOLDEN_APPLE),
                this.entryLink("Goat Familiar", "familiar_rituals", "familiar_chimera@goat"),
                this.color("Forest Biome", ChatFormatting.DARK_PURPLE),
                this.itemLink(Items.DYE.black()),
                this.itemLink(Items.FLINT),
                this.itemLink(Items.ENDER_EYE),
                this.color("Shub Niggurath Familiar", ChatFormatting.DARK_PURPLE)
        );

        this.page("ability", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText()));
        this.pageTitle("Attack");
        this.pageText("""
                        Spawns small versions of itself to fight for you, if it isn't sitting.
                        The cooldown of this ability depends on the Shub Niggurath Familiar level.
                        \\
                        \\
                        **Default:** 10 seconds.
                        \\
                        \\
                        **Upgraded:** 5 seconds.
                        \\
                        \\
                        **Iesnium:** 1 second.
                        """
        );

        this.page("bell", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText()));
        this.pageTitle("Goat Bell");
        this.pageText("""
                        When **upgraded** by a Blacksmith Familiar, the Shub Niggurath Familiar will get a warning bell.
                         When you hit the familiar it will ring the bell and attract enemies in a large radius.
                        \\
                        \\
                         *If the goat used in the transformation already has a bell, it will be retained.*
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
                this.entryLink("Whisperer of the Forest", "getting_started", "effects@forest_whisperer")
        );
    }

    @Override
    protected String entryName() {
        return "Shub Niggurath Familiar";
    }

    @Override
    protected String entryDescription() {
        return "Attack | Support | Influence";
    }

    @Override
    protected GuiSprite entryBackground() {
        return EntryBackground.DEFAULT;
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(this.modLoc("textures/gui/book/familiar_shub_niggurath.png"));
    }

    @Override
    protected String entryId() {
        return ENTRY_ID;
    }
}
