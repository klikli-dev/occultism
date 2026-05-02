package com.klikli_dev.occultism.datagen.book.binding_rituals;

import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.EntryBackground;
import com.klikli_dev.modonomicon.api.datagen.EntryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookSpotlightPageModel;
import com.klikli_dev.modonomicon.client.gui.book.theme.GuiSprite;
import com.klikli_dev.occultism.integration.modonomicon.pages.BookRitualRecipePageModel;
import com.klikli_dev.occultism.registry.OccultismBlocks;
import net.minecraft.ChatFormatting;
import net.minecraft.world.item.crafting.Ingredient;

public class SpiritGrindstoneEntry extends EntryProvider {

    public static final String ENTRY_ID = "spirit_grindstone";

    public SpiritGrindstoneEntry(CategoryProvider parent) {
        super(parent);
    }

    @Override
    protected void generatePages() {
        this.page("spotlight", () -> BookSpotlightPageModel.create()
                .withItem(Ingredient.of(OccultismBlocks.SPIRIT_GRINDSTONE))
                .withText(this.context().pageText()));
        this.pageText("""
                        The [](item://occultism:spirit_grindstone) is a {0} infusion.
                        This grindstone has some differences:
                        1. Remove only curses from enchanted items;
                        2. The returned XP is 100%% of removed curses (instead 50%%-100%%);
                        3. When combining two items, the enchantments of the top one will be maintained;
                        4. The repair rate gets an extra bonus, sum of durability values plus 20%%
                         (instead of just 5%% of maximum durability), so repairing at the right time will be rewarding;
                        """,
                this.color("Djinni", ChatFormatting.DARK_PURPLE)
        );

        this.page("ritual", () -> BookRitualRecipePageModel.create()
                .withRecipeId1(this.modLoc("ritual/craft_spirit_grindstone"))
        );
        //no text
    }

    @Override
    protected String entryName() {
        return "Spirit Grindstone";
    }

    @Override
    protected String entryDescription() {
        return "Purify the curses";
    }

    @Override
    protected GuiSprite entryBackground() {
        return EntryBackground.DEFAULT;
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(OccultismBlocks.SPIRIT_GRINDSTONE);
    }

    @Override
    protected String entryId() {
        return ENTRY_ID;
    }
}
