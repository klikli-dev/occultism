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

public class FamiliarCthulhuEntry extends EntryProvider {

    public static final String ENTRY_ID = "familiar_cthulhu";

    public FamiliarCthulhuEntry(CategoryProvider parent) {
        super(parent);
    }

    @Override
    protected void generatePages() {
        this.page("entity", () -> BookEntityPageModel.create()
                .withEntityId("occultism:cthulhu_familiar")
                .withText(this.context().pageText())
                .withScale(0.8f)
                .withOffset(-0.3f));
        this.pageText("Provides General Coolness");

        this.page("ritual", () -> BookRitualRecipePageModel.create()
                .withRecipeId1(this.modLoc("ritual/familiar_cthulhu")));

        this.page("ability", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText()));
        this.pageTitle("Ability");
        this.pageText("""
                        Give a {0} to transform in a {1}, the efficiency of this conversion depends on Cthulhu''s level.
                        \\
                        \\
                        **Upgrade Behaviour**
                        \\
                         When upgraded by a Blacksmith Familiar, it will act as a mobile light source.
                        """,
                this.itemLink(Items.LAPIS_LAZULI),
                this.itemLink(Items.PRISMARINE_SHARD)
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
                        **Default:** Water Breathing.
                        \\
                        \\
                        **Upgraded:** Dolphin Grace.
                        \\
                        \\
                        **Iesnium:** Conduit Power and {1}.
                        """,
                this.entryLink("Familiar Tablet", "familiar_rituals", "tablet"),
                this.entryLink("Lord of the Aquatic Depths", "getting_started", "effects@aquatic_lord")
        );
    }

    @Override
    protected String entryName() {
        return "Cthulhu Familiar";
    }

    @Override
    protected String entryDescription() {
        return "Utility | Conversion | Influence";
    }

    @Override
    protected GuiSprite entryBackground() {
        return EntryBackground.DEFAULT;
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(this.modLoc("textures/gui/book/familiar_cthulhu.png"));
    }

    @Override
    protected String entryId() {
        return ENTRY_ID;
    }
}
