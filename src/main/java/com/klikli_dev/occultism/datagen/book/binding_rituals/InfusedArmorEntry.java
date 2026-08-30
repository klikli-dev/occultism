package com.klikli_dev.occultism.datagen.book.binding_rituals;

import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.EntryBackground;
import com.klikli_dev.modonomicon.api.datagen.EntryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookTextPageModel;
import com.klikli_dev.modonomicon.client.gui.book.theme.GuiSprite;
import com.klikli_dev.occultism.integration.modonomicon.pages.BookRitualRecipePageModel;
import com.klikli_dev.occultism.registry.OccultismItems;

public class InfusedArmorEntry extends EntryProvider {

    public static final String ENTRY_ID = "craft_infused_armor";

    public InfusedArmorEntry(CategoryProvider parent) {
        super(parent);
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(OccultismItems.INFUSED_CHESTPLATE.get());
    }

    @Override
    protected String entryName() {
        return "Infused Armor";
    }

    @Override
    protected String entryDescription() {
        return "Defensive option for holding familiars";
    }

    @Override
    protected void generatePages() {
        this.page("description", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText()));
        this.pageTitle("Infused Armor");
        this.pageText("""
                        {0} are very interesting accessories, but they don''t provide any real
                         protection to the wearer. As an alternative, Silver Armor can be upgraded
                         to its Infused version using {1} and {2}.
                        \\
                        Familiar effects will be applied while the armor is equipped.
                        """,
                this.entryLink("Familiar Ring", "crafting_rituals", "craft_familiar_ring"),
                this.itemLink(OccultismItems.SPIRIT_ATTUNED_GEM),
                this.itemLink(OccultismItems.SOUL_GEM_ITEM)
        );

        this.page("ritual_helmet", () -> BookRitualRecipePageModel.create()
                .withRecipeId1(this.modLoc("ritual/craft_infused_helmet"))
        );

        this.page("ritual_chestplate", () -> BookRitualRecipePageModel.create()
                .withRecipeId1(this.modLoc("ritual/craft_infused_chestplate"))
        );

        this.page("ritual_leggings", () -> BookRitualRecipePageModel.create()
                .withRecipeId1(this.modLoc("ritual/craft_infused_leggings"))
        );

        this.page("ritual_boots", () -> BookRitualRecipePageModel.create()
                .withRecipeId1(this.modLoc("ritual/craft_infused_boots"))
        );
    }

    @Override
    protected GuiSprite entryBackground() {
        return EntryBackground.DEFAULT;
    }

    @Override
    protected String entryId() {
        return ENTRY_ID;
    }
}
