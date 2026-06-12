package com.klikli_dev.occultism.datagen;

import com.klikli_dev.modonomicon.api.datagen.SingleBookSubProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookModel;
import com.klikli_dev.modonomicon.api.datagen.book.condition.BookOrConditionModel;
import com.klikli_dev.occultism.Occultism;
import com.klikli_dev.occultism.datagen.book.*;

public class OccultismBookProvider extends SingleBookSubProvider {

    public static final String COLOR_PURPLE = "ad03fc";

    public OccultismBookProvider() {
        super("dictionary_of_spirits", Occultism.MODID);
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
        summoningRitualsCategory.withCondition(OccultismResearch.PENTACLES_SUMMON_FOLIOT);
        var possessionRitualsCategory = this.add(new PossessionRitualsCategory(this).generate().withSortNumber(sortNum++));
        possessionRitualsCategory.withCondition(OccultismResearch.PENTACLES_POSSESS_FOLIOT);
        var familiarRitualsCategory = this.add(new FamiliarRitualsCategory(this).generate().withSortNumber(sortNum++));
        familiarRitualsCategory.withCondition(OccultismResearch.PENTACLES_POSSESS_FOLIOT);
        var craftingRitualsCategory = this.add(new BindingRitualsCategory(this).generate().withSortNumber(sortNum++));
        craftingRitualsCategory.withCondition(OccultismResearch.PENTACLES_CRAFT_FOLIOT);

        var storageCategory = this.add(new StorageCategory(this).generate().withSortNumber(sortNum++));
        storageCategory.withCondition(BookOrConditionModel.create().withChildren(
                this.condition().researchNodeUnlocked(OccultismResearch.BINDING_STORAGE_SYSTEM),
                this.condition().researchNodeUnlocked(OccultismResearch.STORAGE_OVERVIEW)
        ));

        spiritsCategory.withCondition(OccultismResearch.GETTING_STARTED_INTRO);
        ritualsCategory.withCondition(OccultismResearch.GETTING_STARTED_INTRO);
        pentaclesCategory.withCondition(OccultismResearch.GETTING_STARTED_INTRO);
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
                .withGenerateEntryHierarchyResearch(true)
                .withAllowOpenBooksWithInvalidLinks(true)
                ;
    }

    //endregion

    //endregion

}
