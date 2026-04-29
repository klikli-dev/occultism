package com.klikli_dev.occultism.datagen.book.binding_rituals;

import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.EntryBackground;
import com.klikli_dev.modonomicon.api.datagen.EntryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookSpotlightPageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookTextPageModel;
import com.klikli_dev.occultism.integration.modonomicon.pages.BookRitualRecipePageModel;
import com.klikli_dev.occultism.registry.OccultismItems;
import com.klikli_dev.modonomicon.client.gui.book.theme.GuiSprite;
import net.minecraft.ChatFormatting;
import net.minecraft.world.item.crafting.Ingredient;

public class KnowledgeTabletEntry extends EntryProvider {

    public static final String ENTRY_ID = "knowledge_tablet";


    public KnowledgeTabletEntry(CategoryProvider parent) {
        super(parent);
    }

    @Override
    protected void generatePages() {
        this.page("spotlight", () -> BookSpotlightPageModel.create()
                .withItem(Ingredient.of(OccultismItems.KNOWLEDGE_TABLET))
                .withText(this.context().pageText()));
        this.pageText("""
                        Knowledge Tablet is an item infused by a {0}. The spirit can hold a enormous
                         quantity of experience points. Keeping safe and allowing giving XP to other players.
                        """,
                this.color("Foliot", ChatFormatting.DARK_PURPLE)
        );

        this.page("ritual", () -> BookRitualRecipePageModel.create()
                .withRecipeId1(this.modLoc("ritual/craft_knowledge_tablet"))
        );
        //no text

        this.page("use", () -> BookTextPageModel.create()
                .withTitle(this.context.pageTitle())
                .withText(this.context().pageText()));
        this.pageTitle("Usage");
        this.pageText("""
                        This item use is very simple: \\
                        {0} will storage all of your experience points. \\
                        {1} receive all stored points. \\
                        \\
                        NOTE: Sometimes, with big values, you can lost a small quantity of point due numerical approximations.
                        """,
                this.color("Right-Click", ChatFormatting.GREEN),
                this.color("Shift-Right-Click", ChatFormatting.GREEN)
        );
    }

    @Override
    protected String entryName() {
        return "Knowledge Tablet";
    }

    @Override
    protected String entryDescription() {
        return "XP storage item";
    }

    @Override
    protected GuiSprite entryBackground() {
        return EntryBackground.DEFAULT;
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(OccultismItems.KNOWLEDGE_TABLET);
    }

    @Override
    protected String entryId() {
        return ENTRY_ID;
    }
}
