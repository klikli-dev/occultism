package com.klikli_dev.occultism.datagen.book.binding_rituals;

import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.EntryBackground;
import com.klikli_dev.modonomicon.api.datagen.EntryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookSpotlightPageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookTextPageModel;
import com.klikli_dev.modonomicon.client.gui.book.theme.GuiSprite;
import com.klikli_dev.occultism.integration.modonomicon.pages.BookRitualRecipePageModel;
import com.klikli_dev.occultism.registry.OccultismItems;
import net.minecraft.ChatFormatting;
import net.minecraft.world.item.crafting.Ingredient;

public class WormholeTabletEntry extends EntryProvider {

    public static final String ENTRY_ID = "wormhole_tablet";

    public WormholeTabletEntry(CategoryProvider parent) {
        super(parent);
    }

    @Override
    protected void generatePages() {
        this.page("spotlight", () -> BookSpotlightPageModel.create()
                .withItem(Ingredient.of(OccultismItems.WORMHOLE_TABLET.asItem()))
                .withText(this.context().pageText()));
        this.pageText("""
                        Portal blocks are really cool, but they aren't always practical, such as when you need to get back home.
                        \\
                        Because of this, a pocket-sized version of the teleporters was developed.
                        \\
                        With the added benefit of a small internal inventory that can hold up to 9 compasses.
                        """
        );

        this.page("ritual", () -> BookRitualRecipePageModel.create()
                .withRecipeId1(this.modLoc("ritual/craft_wormhole_tablet"))
        );
        //no text

        this.page("uses", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText()));
        this.pageTitle("Using");
        this.pageText("""
                        - {0} will open a menu where you can store your compasses. The center slot is the selected destination;
                        - {1} to teleport;
                        - {2} while holding the item, change the destination;
                        """,
                this.color("Shift-Right-Click", ChatFormatting.DARK_PURPLE),
                this.color("Right-Click", ChatFormatting.DARK_PURPLE),
                this.color("Shift-Mouse Scroll", ChatFormatting.DARK_PURPLE)
        );

        this.page("back", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText()));
        this.pageTitle("Returning");
        this.pageText("""
                        If this device has a compass named {0} inside, it will save your current position before teleporting.
                        """,
                this.color("BACK", ChatFormatting.DARK_PURPLE)
        );
    }

    @Override
    protected String entryName() {
        return "Wormhole Tablet";
    }

    @Override
    protected String entryDescription() {
        return "Portable fast travel";
    }

    @Override
    protected GuiSprite entryBackground() {
        return EntryBackground.DEFAULT;
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(OccultismItems.WORMHOLE_TABLET.get());
    }

    @Override
    protected String entryId() {
        return ENTRY_ID;
    }
}
