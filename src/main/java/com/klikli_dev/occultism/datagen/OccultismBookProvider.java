package com.klikli_dev.occultism.datagen;

import com.klikli_dev.modonomicon.api.ModonomiconAPI;
import com.klikli_dev.modonomicon.api.datagen.CategoryEntryMap;
import com.klikli_dev.modonomicon.api.datagen.ModonomiconLanguageProvider;
import com.klikli_dev.modonomicon.api.datagen.SingleBookSubProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookCategoryModel;
import com.klikli_dev.modonomicon.api.datagen.book.BookEntryModel;
import com.klikli_dev.modonomicon.api.datagen.book.BookEntryParentModel;
import com.klikli_dev.modonomicon.api.datagen.book.BookModel;
import com.klikli_dev.modonomicon.api.datagen.book.condition.BookEntryReadConditionModel;
import com.klikli_dev.modonomicon.api.datagen.book.condition.BookOrConditionModel;
import com.klikli_dev.modonomicon.api.datagen.book.condition.BookTrueConditionModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookEntityPageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookTextPageModel;
import com.klikli_dev.occultism.Occultism;
import com.klikli_dev.occultism.datagen.book.*;
import com.klikli_dev.occultism.datagen.book.pentacles.*;
import com.klikli_dev.occultism.integration.modonomicon.pages.BookRitualRecipePageModel;
import com.klikli_dev.occultism.registry.OccultismItems;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;

public class OccultismBookProvider extends SingleBookSubProvider {

    public static final String COLOR_PURPLE = "ad03fc";

    public OccultismBookProvider(ModonomiconLanguageProvider lang) {
        super("dictionary_of_spirits", Occultism.MODID, lang);
    }

    @Override
    protected void registerDefaultMacros() {
        //currently none
    }

    @Override
    protected void generateCategories() {
        int sortNum = 1;
        var gettingStartedCategory = this.add(new GettingStartedCategory(this).generate().withSortNumber(sortNum++));
        var spiritsCategory = this.add(new SpiritsCategory(this).generate().withSortNumber(sortNum++));
        var ritualsCategory = this.add(new RitualsCategory(this).generate().withSortNumber(sortNum++));

        var pentaclesCategory = this.add(new PentaclesCategory(this).generate().withSortNumber(sortNum++));

        var summoningRitualsCategory = this.add(new SummoningRitualCategory(this).generate().withSortNumber(sortNum++));
        summoningRitualsCategory.withCondition(BookEntryReadConditionModel.create().withEntry(this.modLoc("pentacles/summon_foliot")));
        var possessionRitualsCategory = this.add(new PossessionRitualsCategory(this).generate().withSortNumber(sortNum++));
        possessionRitualsCategory.withCondition(BookEntryReadConditionModel.create().withEntry(this.modLoc("pentacles/possess_foliot")));
        var familiarRitualsCategory = this.add(new FamiliarRitualsCategory(this).generate().withSortNumber(sortNum++));
        familiarRitualsCategory.withCondition(BookEntryReadConditionModel.create().withEntry(this.modLoc("pentacles/possess_foliot")));
        var craftingRitualsCategory = this.add(new BindingRitualsCategory(this).generate().withSortNumber(sortNum++));
        craftingRitualsCategory.withCondition(BookEntryReadConditionModel.create().withEntry(this.modLoc("pentacles/craft_foliot")));

        var storageCategory = this.add(new StorageCategory(this).generate().withSortNumber(sortNum++));
        storageCategory.withCondition(BookOrConditionModel.create().withChildren(
                BookEntryReadConditionModel.create().withEntry(this.modLoc("crafting_rituals/craft_dimensional_matrix")),
                BookEntryReadConditionModel.create().withEntry(this.modLoc("getting_started/storage")),
                BookEntryReadConditionModel.create().withEntry(this.modLoc("storage/overview"))
        ));

        var introReadCondition = BookEntryReadConditionModel.create()
                .withEntry(this.modLoc("getting_started/intro"));
        spiritsCategory.withCondition(introReadCondition);
        ritualsCategory.withCondition(introReadCondition);
        pentaclesCategory.withCondition(introReadCondition);
    }

    @Override
    protected String bookName() {
        return "Dictionary of Spirits";
    }

    @Override
    protected String bookTooltip() {
        return "An introduction to the spirit world.";
    }

    @Override
    protected BookModel additionalSetup(BookModel book) {
        return super.additionalSetup(book)
                .withModel(this.modLoc("dictionary_of_spirits_icon"))
                .withGenerateBookItem(false)
                .withCustomBookItem(this.modLoc("dictionary_of_spirits"))
                .withAutoAddReadConditions(true)
                .withAllowOpenBooksWithInvalidLinks(true)
                ;
    }

    //endregion

    //endregion

}
