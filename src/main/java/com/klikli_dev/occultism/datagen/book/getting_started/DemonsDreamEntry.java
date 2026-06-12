package com.klikli_dev.occultism.datagen.book.getting_started;

import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.EntryBackground;
import com.klikli_dev.modonomicon.api.datagen.EntryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookCraftingRecipePageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookImagePageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookSpotlightPageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookTextPageModel;
import com.klikli_dev.modonomicon.client.gui.book.theme.GuiSprite;
import com.klikli_dev.occultism.integration.modonomicon.pages.BookSpiritFireRecipePageModel;
import com.klikli_dev.occultism.registry.OccultismItems;
import net.minecraft.world.item.crafting.Ingredient;

public class DemonsDreamEntry extends EntryProvider {

    public static final String ENTRY_ID = "demons_dream";

    public DemonsDreamEntry(CategoryProvider parent) {
        super(parent);
    }

    @Override
    protected void generatePages() {
        this.page("intro", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText()));
        this.pageTitle("The Otherworld");
        this.pageText("Hidden from mere human eyes exists another plane of existence, another *dimension* if you will, the [#](ad03fc)Otherworld[#]().\nThis world is populated with entities often referred to as [#](ad03fc)Demons[#]().\n");

        this.page("intro2", () -> BookTextPageModel.create()
                .withText(this.context().pageText()));
        this.pageText("These Demons possess a wide variety of powers and useful skills, and for centuries magicians have sought to summon them for their own gain.\nThe first step on the journey to successfully summoning such an Entity is to learn how to interact with the Otherworld.\n");

        this.page("spotlight", () -> BookSpotlightPageModel.create()
                .withItem(Ingredient.of(OccultismItems.DATURA.get()))
                .withText(this.context().pageText()));
        this.pageText("Demon's Dream is a herb that gives humans the [#](ad03fc)Third Eye[#](),\nallowing them to see where the [#](ad03fc)Otherworld[#]() intersects with our own.\nSeeds can be found **by breaking grass**.\n**Consuming** the grown fruit activates the ability *with a certain chance*.\n");

        this.page("harvest_effect", () -> BookTextPageModel.create()
                .withText(this.context().pageText()));
        this.pageText("An additional side effect of Demon's Dream, is **the ability to interact with [#](ad03fc)Otherworld[#]() materials**.\nThis is unique to Demon's Dream, other ways to obtain [#](ad03fc)Third Eye[#]() do not yield this ability.\nWhile under the effect of Demon's Dream you are able to **harvest** Otherstone as well as Otherworld trees.\n");

        this.page("datura_screenshot", () -> BookImagePageModel.create()
                .withImages(this.modLoc("textures/gui/book/datura_effect.png")));

        this.page("note_on_spirit_fire", () -> BookTextPageModel.create()
                .withText(this.context().pageText()));
        this.pageText("**Hint**: The otherworld materials you obtain by harvesting under the effects of[#](ad03fc)Third Eye[#]() **can be obtained more easily using [](item://occultism:spirit_fire)**. Proceed with the next entry in this book to learn more about spirit fire.\n");

        this.page("spotlight2", () -> BookSpotlightPageModel.create()
                .withItem(Ingredient.of(OccultismItems.DEMONS_DREAM_ESSENCE.get()))
                .withText(this.context().pageText()));
        this.pageText("Multiple Demon's Dream fruits or seeds can be compressed into an essence that is much more potent. It *guarantees* the [#](ad03fc)Third Eye[#]() and provides it for a longer amount of time, but comes with a lot of (positive and negative) side effects.\n");

        this.page("recipe_essence", () -> BookCraftingRecipePageModel.create()
                .withRecipeId1(this.modLoc("crafting/demons_dream_essence_from_fruit_or_seed"))
                .withText(this.context().pageText()));
        this.pageText("Fruit and seeds can be mixed freely to create the essence.\n");

        this.page("spotlight3", () -> BookSpotlightPageModel.create()
                .withItem(Ingredient.of(OccultismItems.DEMONS_DREAM_ESSENCE.get()))
                .withText(this.context().pageText()));
        this.pageText("The essence can be purified in spirit fire (more on that later!) to obtain a version free from all negative side effects, while retaining the positive.\n");

        this.page("recipe_essence_pure", () -> BookSpiritFireRecipePageModel.create()
                .withRecipeId1(this.modLoc("spirit_fire/otherworld_essence")));
    }

    @Override
    protected String entryName() {
        return "Lifting the Veil";
    }

    @Override
    protected String entryDescription() {
        return "Learn about the Otherworld and the Third Eye.";
    }

    @Override
    protected GuiSprite entryBackground() {
        return EntryBackground.DEFAULT;
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(OccultismItems.DATURA.get());
    }

    @Override
    protected String entryId() {
        return ENTRY_ID;
    }
}
