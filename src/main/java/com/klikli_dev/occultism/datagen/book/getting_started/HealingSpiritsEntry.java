package com.klikli_dev.occultism.datagen.book.getting_started;

import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.EntryBackground;
import com.klikli_dev.modonomicon.api.datagen.EntryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookSpotlightPageModel;
import com.klikli_dev.modonomicon.client.gui.book.theme.GuiSprite;
import com.klikli_dev.occultism.registry.OccultismItems;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

public class HealingSpiritsEntry extends EntryProvider {

    public static final String ENTRY_ID = "healing_spirits";

    public HealingSpiritsEntry(CategoryProvider parent) {
        super(parent);
    }

    @Override
    protected void generatePages() {
        this.page("spotlight", () -> BookSpotlightPageModel.create()
                .withItem(Ingredient.of(OccultismItems.DATURA.get()))
                .withText(this.context().pageText()));
        this.pageText("Right-click a spirit with [](item://occultism:datura) to heal it.\n\\\n\\\nThis will work on **Familiars**, **Summoned Spirits** and also **Possessed Mobs**.\n");

        this.page("spotlight2", () -> BookSpotlightPageModel.create()
                .withItem(Ingredient.of(OccultismItems.DEMONS_DREAM_ESSENCE.get()))
                .withText(this.context().pageText()));
        this.pageText("When compressing Demon's Dream fruits or seeds into essence, a much stronger instant healing effect can be achieved. This comes at the cost of efficiency: Feeding 9 fruits to a spirit in succession will heal it more than feeding it 9 fruits worth of essence.\n");

        this.page("spotlight3", () -> BookSpotlightPageModel.create()
                .withItem(Ingredient.of(OccultismItems.OTHERWORLD_ESSENCE.get()))
                .withText(this.context().pageText()));
        this.pageText("Purifying the Demon's Dream Essence will yield a version that heals even more, negating the efficiency loss.\n");
    }

    @Override
    protected String entryName() {
        return "Healing Spirits";
    }

    @Override
    protected String entryDescription() {
        return "Fix up your spirit!";
    }

    @Override
    protected GuiSprite entryBackground() {
        return EntryBackground.DEFAULT;
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(Items.SPLASH_POTION);
    }

    @Override
    protected String entryId() {
        return ENTRY_ID;
    }
}
