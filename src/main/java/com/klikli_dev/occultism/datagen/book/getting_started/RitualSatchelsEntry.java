package com.klikli_dev.occultism.datagen.book.getting_started;

import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.EntryBackground;
import com.klikli_dev.modonomicon.api.datagen.EntryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookSpotlightPageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookTextPageModel;
import com.klikli_dev.occultism.datagen.book.BindingRitualsCategory;
import com.klikli_dev.occultism.datagen.book.binding_rituals.ApprenticeRitualSatchelEntry;
import com.klikli_dev.occultism.integration.modonomicon.pages.BookRitualRecipePageModel;
import com.klikli_dev.occultism.registry.OccultismItems;
import com.mojang.datafixers.util.Pair;
import net.minecraft.ChatFormatting;
import net.minecraft.world.item.crafting.Ingredient;

public class RitualSatchelsEntry extends EntryProvider {

    public static final String ENTRY_ID = "ritual_satchels";


    public RitualSatchelsEntry(CategoryProvider parent) {
        super(parent);
    }

    @Override
    protected void generatePages() {

        this.page("spotlight", () -> BookSpotlightPageModel.create()
                .withItem(Ingredient.of(OccultismItems.RITUAL_SATCHEL_T1.get()))
                .withText(this.context().pageText()));
        this.pageText("""
                        Ritual satchels are bags that can hold items needed to create pentacles for rituals.\\
                        More importantly, they can automatically place the right items for a pentacle, removing the need to manually place chalks, candles, crystals, skulls and other items needed for rituals.\\\\
                        The Apprentice Satchel places pentacle blocks one by one.\\
                        The improved Artisanal Satchel places all pentacle blocks in a single action.\\
                        """
        );

        this.page("more", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText()));
        this.pageTitle("Obtaining a Satchel");
        this.pageText("""
                        Visit the entry about the {0} or the {1} in the {2} to learn how to enchant a satchel and use it for rituals.
                        """,
                this.entryLink("Apprentice Satchel", BindingRitualsCategory.CATEGORY_ID, ApprenticeRitualSatchelEntry.ENTRY_ID),
                this.entryLink("Artisanal Satchel", BindingRitualsCategory.CATEGORY_ID, ApprenticeRitualSatchelEntry.ENTRY_ID),
                this.categoryLink("Binding Rituals Category", BindingRitualsCategory.CATEGORY_ID)
        );

    }

    @Override
    protected String entryName() {
        return "Ritual Satchels";
    }

    @Override
    protected String entryDescription() {
        return "Easier pentacle drawing with a Ritual Satchel";
    }

    @Override
    protected Pair<Integer, Integer> entryBackground() {
        return EntryBackground.DEFAULT;
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(OccultismItems.RITUAL_SATCHEL_T1.get());
    }

    @Override
    protected String entryId() {
        return ENTRY_ID;
    }
}
