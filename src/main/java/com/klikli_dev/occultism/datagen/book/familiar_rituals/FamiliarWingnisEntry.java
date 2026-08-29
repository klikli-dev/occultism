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

public class FamiliarWingnisEntry extends EntryProvider {

    public static final String ENTRY_ID = "familiar_wingnis";

    public FamiliarWingnisEntry(CategoryProvider parent) {
        super(parent);
    }

    @Override
    protected void generatePages() {
        this.page("entity", () -> BookEntityPageModel.create()
                .withEntityId("occultism:wingnis")
                .withText(this.context().pageText())
                .withScale(1.2f)
                .withOffset(-0.4f));
        this.pageText("Definitely not a Phoenix.");

        this.page("ritual", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText()));
        this.pageTitle("Transformation");
        this.pageText("""
                        The {0} is not summoned directly. First, summon a {1}, give it a {2} and bring it to a {3},
                         then click the drikwing with {4}. Make sure it dies to fire so you can obtain the {5}.
                        """,
                this.color("Wingnis", ChatFormatting.DARK_PURPLE),
                this.entryLink("Drikwing Familiar", "familiar_rituals", "familiar_drikwing"),
                this.itemLink(Items.TOTEM_OF_UNDYING),
                this.color("Nether Biome", ChatFormatting.DARK_PURPLE),
                this.entryLink("Flaming Paste", "crafting_rituals", "craft_flaming_paste"),
                this.color("Wingnis Familiar", ChatFormatting.DARK_PURPLE)
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
                        **Default:** {1}, Jump Boost, Slow Falling and Glowing.
                        \\
                        \\
                        **Upgraded:** {2}.
                        """,
                this.entryLink("Familiar Tablet", "familiar_rituals", "tablet"),
                this.entryLink("Multi Jump", "getting_started", "effects@double_jump"),
                this.entryLink("Fire Wings", "getting_started", "effects@fire_wing")
        );

        this.page("immunity", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText()));
        this.pageTitle("Immunities");
        this.pageText("""
                        While active, it provides additional defenses for its owner, granting different immunities depending on its level.
                        \\
                        \\
                        **Default:** Fall damage (also cancels Farmland Trample) and Levitation effect.
                        """
        );

        this.page("ability", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText()));
        this.pageTitle("Ability");
        this.pageText("""
                        When an Iesnium Wingnis owner cheats death, whether through a {0} abilities,
                         a {1}, or any similar methods, they will receive several benefits:
                         Full Heal, Strength, Speed, Fire Resistance, Resistance, Regeneration, and Saturation.
                        """,
                this.entryLink("Guardian Familiar", "familiar_rituals", "familiar_guardian"),
                this.itemLink(Items.TOTEM_OF_UNDYING)
        );
    }

    @Override
    protected String entryName() {
        return "Drikwing Familiar";
    }

    @Override
    protected String entryDescription() {
        return "Movement | Defense";
    }

    @Override
    protected GuiSprite entryBackground() {
        return EntryBackground.DEFAULT;
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(this.modLoc("textures/gui/book/wingnis.png"));
    }

    @Override
    protected String entryId() {
        return ENTRY_ID;
    }
}
