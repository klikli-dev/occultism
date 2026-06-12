package com.klikli_dev.occultism.datagen.book.getting_started;

import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.EntryBackground;
import com.klikli_dev.modonomicon.api.datagen.EntryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookSpotlightPageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookTextPageModel;
import com.klikli_dev.modonomicon.client.gui.book.theme.GuiSprite;
import com.klikli_dev.occultism.registry.OccultismItems;
import net.minecraft.world.item.crafting.Ingredient;

public class ThirdEyeEntry extends EntryProvider {

    public static final String ENTRY_ID = "third_eye";

    public ThirdEyeEntry(CategoryProvider parent) {
        super(parent);
    }

    @Override
    protected void generatePages() {
        this.page("about", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText()));
        this.pageTitle("Third Eye");
        this.pageText("The ability to see beyond the physical world is referred to as [#](ad03fc)Third Eye[#]().\nHumans do not possess such an ability to see [#](ad03fc)beyond the veil[#](),\nhowever with certain substances and contraptions the knowledgeable summoner can work around this limitation.\n");

        this.page("how_to_obtain", () -> BookTextPageModel.create()
                .withText(this.context().pageText()));
        this.pageText("The most comfortable, and most *expensive*, way to obtain this ability, is to wear spectacles\ninfused with spirits, that *lend* their sight to the wearer.\nA slightly more nauseating, but **very affordable** alternative is the consumption of certain herbs,\n[Demon's Dream](entry://occultism:dictionary_of_spirits/getting_started/demons_dream) most prominent among them.\n");

        this.page("otherworld_goggles", () -> BookSpotlightPageModel.create()
                .withItem(Ingredient.of(OccultismItems.OTHERWORLD_GOGGLES.get()))
                .withText(this.context().pageText()));
        this.pageText("[These goggles](entry://occultism:dictionary_of_spirits/crafting_rituals/craft_otherworld_goggles) allow to see even more hidden Otherworld blocks,\nhowever they do not allow harvesting those materials.\nLow-tier materials can be harvested by consuming [Demon's Dream](entry://occultism:dictionary_of_spirits/getting_started/demons_dream),\nbut more valuable materials require special tools.\n");
    }

    @Override
    protected String entryName() {
        return "The Third Eye";
    }

    @Override
    protected String entryDescription() {
        return "Do you see now?";
    }

    @Override
    protected GuiSprite entryBackground() {
        return EntryBackground.DEFAULT;
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(this.modLoc("textures/mob_effect/third_eye.png"));
    }

    @Override
    protected String entryId() {
        return ENTRY_ID;
    }
}
