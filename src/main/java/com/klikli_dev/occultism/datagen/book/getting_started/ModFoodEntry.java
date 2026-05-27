package com.klikli_dev.occultism.datagen.book.getting_started;

import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.EntryBackground;
import com.klikli_dev.modonomicon.api.datagen.EntryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookSpotlightPageModel;
import com.klikli_dev.modonomicon.client.gui.book.theme.GuiSprite;
import com.klikli_dev.occultism.registry.OccultismItems;
import net.minecraft.world.item.crafting.Ingredient;

public class ModFoodEntry extends EntryProvider {

    public static final String ENTRY_ID = "mod_food";

    public ModFoodEntry(CategoryProvider parent) {
        super(parent);
    }

    @Override
    protected void generatePages() {
        this.page("spotlight_cursed_honey", () -> BookSpotlightPageModel.create()
                .withItem(Ingredient.of(OccultismItems.CURSED_HONEY.get()))
                .withText(this.context().pageText()));
        this.pageText("Cursed honey is dropped by Possessed Bees. Eating it grants instant health and absorption effects.\n");

        this.page("spotlight_beaver_nugget", () -> BookSpotlightPageModel.create()
                .withItem(Ingredient.of(OccultismItems.BEAVER_NUGGET.get()))
                .withText(this.context().pageText()));
        this.pageText("Beaver nuggets are dropped by Beaver Familiars. Eating them grants mining fatigue but significantly increases mining speed.\n");

        this.page("spotlight_sweet_honey_heart", () -> BookSpotlightPageModel.create()
                .withItem(Ingredient.of(OccultismItems.SWEET_HONEY_HEART.get()))
                .withText(this.context().pageText()));
        this.pageText("Sweet honey heart is crafted from cursed honey and other ingredients. Provides strong healing effects.\n");

        this.page("spotlight_demonic_meat", () -> BookSpotlightPageModel.create()
                .withItem(Ingredient.of(OccultismItems.DEMONIC_MEAT.get()))
                .withText(this.context().pageText()));
        this.pageText("Demonic meat is dropped by Possessed Zombified Piglins. Eating it grants strength and fire resistance.\n");
    }

    @Override
    protected String entryName() {
        return "Mod Food";
    }

    @Override
    protected String entryDescription() {
        return "Special food from mod entities";
    }

    @Override
    protected GuiSprite entryBackground() {
        return EntryBackground.DEFAULT;
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(OccultismItems.CURSED_HONEY.get());
    }

    @Override
    protected String entryId() {
        return ENTRY_ID;
    }
}
