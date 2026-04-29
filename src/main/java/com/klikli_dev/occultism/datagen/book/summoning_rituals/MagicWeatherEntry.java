package com.klikli_dev.occultism.datagen.book.summoning_rituals;

import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.EntryBackground;
import com.klikli_dev.modonomicon.api.datagen.EntryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.condition.BookEntryReadConditionModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookTextPageModel;
import com.klikli_dev.occultism.datagen.book.PentaclesCategory;
import com.klikli_dev.occultism.datagen.book.pentacles.SummonAfritEntry;
import com.klikli_dev.occultism.integration.modonomicon.pages.BookRitualRecipePageModel;
import com.klikli_dev.modonomicon.client.gui.book.theme.GuiSprite;
import net.minecraft.world.item.Items;

public class MagicWeatherEntry extends EntryProvider {

    public static final String ENTRY_ID = "weather_magic";

    public MagicWeatherEntry(CategoryProvider parent) {
        super(parent);
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(Items.WHEAT);
    }

    @Override
    protected String entryName() {
        return "Weather Magic";
    }

    @Override
    protected String entryDescription() {
        return "My name is vic(ky) (Season Finale)";
    }

    @Override
    protected void generatePages() {
        this.page("intro", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText()));
        this.pageTitle("Weather Magic");
        this.pageText("""
                    Weather magic is especially useful for farmers and others depending
                    on specific weather. Summons spirits to modify the weather.
                    Different types of weather modification require different spirits.
                    \\
                    \\
                    Weather spirits will only modify the weather once and then vanish.
                    """
        );

        this.page("ritual_clear", () -> BookRitualRecipePageModel.create()
                    .withRecipeId1(this.modLoc("ritual/summon_djinni_clear_weather"))
                    .withAnchor("clear"));
        //no text

        this.page("ritual_rain", () -> BookRitualRecipePageModel.create()
                    .withRecipeId1(this.modLoc("ritual/summon_afrit_rain_weather"))
                    .withAnchor("rain")
                    .withCondition(BookEntryReadConditionModel.create().withEntry(
                            this.modId() + ":" + PentaclesCategory.CATEGORY_ID + "/" + SummonAfritEntry.ENTRY_ID
                    ))
        );
        //no text

        this.page("ritual_thunder", () -> BookRitualRecipePageModel.create()
                    .withRecipeId1(this.modLoc("ritual/summon_afrit_thunder_weather"))
                    .withAnchor("thunder")
                    .withCondition(BookEntryReadConditionModel.create().withEntry(
                        this.modId() + ":" + PentaclesCategory.CATEGORY_ID + "/" + SummonAfritEntry.ENTRY_ID
                    ))
        );
        //no text

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
