package com.klikli_dev.occultism.datagen.book.binding_rituals;

import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.EntryBackground;
import com.klikli_dev.modonomicon.api.datagen.EntryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookSpotlightPageModel;
import com.klikli_dev.modonomicon.client.gui.book.theme.GuiSprite;
import com.klikli_dev.occultism.integration.modonomicon.pages.BookRitualRecipePageModel;
import net.minecraft.ChatFormatting;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

public class HorseArmorEntry extends EntryProvider {

    public static final String ENTRY_ID = "horse_armor";

    public HorseArmorEntry(CategoryProvider parent) {
        super(parent);
    }

    @Override
    protected void generatePages() {
        this.page("spotlight", () -> BookSpotlightPageModel.create()
                .withItem(Ingredient.of(Items.LEATHER_HORSE_ARMOR))
                .withText(this.context().pageText()));
        this.pageText("""
                        Like forging the wild armor trim, upgrading a [](item://minecraft:leather_horse_armor)
                         is a service provided by {0} and not bound any spirit to the final object.
                         You sacrifice the items and the {0} uses his power to forge that item for you.
                         Use the respective materials to obtain [](item://minecraft:iron_horse_armor),
                         [](item://minecraft:golden_horse_armor) or [](item://minecraft:diamond_horse_armor).
                        
                        """,
                this.color("Wild Spirits", ChatFormatting.DARK_PURPLE)
        );

        this.page("ritual_iron", () -> BookRitualRecipePageModel.create()
                .withRecipeId1(this.modLoc("ritual/misc_iron_horse_armor"))
        );
        this.page("ritual_golden", () -> BookRitualRecipePageModel.create()
                .withRecipeId1(this.modLoc("ritual/misc_golden_horse_armor"))
        );
        this.page("ritual_diamond", () -> BookRitualRecipePageModel.create()
                .withRecipeId1(this.modLoc("ritual/misc_diamond_horse_armor"))
        );
        //no text
    }

    @Override
    protected String entryName() {
        return "Forge Horse Armors";
    }

    @Override
    protected String entryDescription() {
        return "Defend your mount";
    }

    @Override
    protected GuiSprite entryBackground() {
        return EntryBackground.DEFAULT;
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(Items.DIAMOND_HORSE_ARMOR);
    }

    @Override
    protected String entryId() {
        return ENTRY_ID;
    }
}
