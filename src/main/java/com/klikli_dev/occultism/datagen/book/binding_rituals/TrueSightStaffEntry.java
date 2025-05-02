package com.klikli_dev.occultism.datagen.book.binding_rituals;

import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.EntryBackground;
import com.klikli_dev.modonomicon.api.datagen.EntryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookSpotlightPageModel;
import com.klikli_dev.occultism.integration.modonomicon.pages.BookRitualRecipePageModel;
import com.klikli_dev.occultism.registry.OccultismItems;
import com.mojang.datafixers.util.Pair;
import net.minecraft.ChatFormatting;
import net.minecraft.world.item.crafting.Ingredient;

public class TrueSightStaffEntry extends EntryProvider {

    public static final String ENTRY_ID = "true_sight_staff";


    public TrueSightStaffEntry(CategoryProvider parent) {
        super(parent);
    }

    @Override
    protected void generatePages() {
        this.page("spotlight", () -> BookSpotlightPageModel.create()
                .withItem(Ingredient.of(OccultismItems.TRUE_SIGHT_STAFF))
                .withText(this.context().pageText()));
        this.pageText("""
                         The [](item://occultism:true_sight_staff) employs an {0} to assist the summoner in tasks of finding and interacting with the otherworld.
                         Unlike {1} in the otherworld goggles, which can only provide vision, with this staff in the off-hand or curio slot, the occultist can collect otherworld materials.
                         The divining rod's search abilities receive an upgrade, now is possible locate any block.
                         
                        """,
                this.color("Marid", ChatFormatting.DARK_PURPLE),
                this.color("Djinni", ChatFormatting.DARK_PURPLE)
        );

        this.page("ritual", () -> BookRitualRecipePageModel.create()
                .withRecipeId1(this.modLoc("ritual/craft_true_sight_staff"))
        );
        //no text
    }

    @Override
    protected String entryName() {
        return "True Sight Staff";
    }

    @Override
    protected String entryDescription() {
        return "I can see forever!";
    }

    @Override
    protected Pair<Integer, Integer> entryBackground() {
        return EntryBackground.DEFAULT;
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(OccultismItems.TRUE_SIGHT_STAFF);
    }

    @Override
    protected String entryId() {
        return ENTRY_ID;
    }
}
