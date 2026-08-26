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
import net.minecraft.world.item.Items;

public class FamiliarDrikwingEntry extends EntryProvider {

    public static final String ENTRY_ID = "familiar_drikwing";

    public FamiliarDrikwingEntry(CategoryProvider parent) {
        super(parent);
    }

    @Override
    protected void generatePages() {
        this.page("entity", () -> BookEntityPageModel.create()
                .withEntityId("occultism:drikwing")
                .withText(this.context().pageText())
                .withScale(1.2f)
                .withOffset(-0.4f));
        this.pageText("Aka Otherworld Bird");

        this.page("ritual", () -> BookRitualRecipePageModel.create()
                .withRecipeId1(this.modLoc("ritual/familiar_drikwing")));

        this.page("description", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText()));
        this.pageTitle("Description");
        this.pageText("""
                        {0} are a subclass of {1} that are known to be amicable towards humans.
                         They usually take the shape of a dark blue and purple parrot.
                         Drikwing Familiar will provide their owner with limited flight abilities when nearby.
                        """,
                this.color("Drikwings", ChatFormatting.DARK_PURPLE),
                this.color("Djinni", ChatFormatting.DARK_PURPLE)
        );

        this.page("description2", () -> BookTextPageModel.create()
                .withText(this.context().pageText()));
        this.pageText("""
                        To obtain the parrot for the sacrifice, consider summoning them using either the {0}.
                        \\
                        \\
                        **Hint:** If you use mods that protect pets from death, use a hopper to start the ritual!
                        """,
                this.entryLink("Parrot Ritual", "familiar_rituals", "familiar_parrot")
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
                        **Default:** {1}, Jump Boost and Slow Falling.
                        """,
                this.entryLink("Familiar Tablet", "familiar_rituals", "tablet"),
                this.entryLink("Multi Jump", "getting_started", "effects@double_jump")
        );

        this.page("immunity", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText()));
        this.pageTitle("Immunities");
        this.pageText("""
                        While active, it provides additional defenses for its owner, granting different immunities depending on its level.
                        \\
                        \\
                        **Upgraded:** Fall damage (and cancels Farmland Trample).
                        """
        );

        this.page("ability", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText()));
        this.pageTitle("Ability");
        this.pageText("""
                        By default, you cannot use Multi Jump while gliding with an {0},
                         but this restriction can be overcome by having a Drikiwing Familiar with the Iesnium Upgrade.
                        """,
                this.itemLink(Items.ELYTRA)
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
        return BookIconModel.create(this.modLoc("textures/gui/book/drikwing.png"));
    }

    @Override
    protected String entryId() {
        return ENTRY_ID;
    }
}
