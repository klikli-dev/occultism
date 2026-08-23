package com.klikli_dev.occultism.datagen.book;

import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.ModonomiconProviderBase;
import com.klikli_dev.modonomicon.api.datagen.book.BookEntryParentModel;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.condition.BookAndConditionModel;
import com.klikli_dev.modonomicon.api.datagen.book.condition.BookModLoadedConditionModel;
import com.klikli_dev.occultism.datagen.OccultismResearch;
import com.klikli_dev.occultism.datagen.book.getting_started.*;
import com.klikli_dev.occultism.datagen.book.pentacles.CraftDjinniEntry;
import com.klikli_dev.occultism.datagen.book.pentacles.CraftFoliotEntry;
import com.klikli_dev.occultism.datagen.book.pentacles.PossessFoliotEntry;
import com.klikli_dev.occultism.datagen.book.pentacles.SummonFoliotEntry;
import com.klikli_dev.occultism.registry.OccultismItems;

public class GettingStartedCategory extends CategoryProvider {

    public static final String CATEGORY_ID = "getting_started";

    public GettingStartedCategory(ModonomiconProviderBase parent) {
        super(parent);
    }

    @Override
    protected void generateEntries() {
        var introEntry = this.add(new IntroEntry(this).generate());
        this.layout().entry(introEntry).at(-11, 0);

        var demonsDreamEntry = this.add(new DemonsDreamEntry(this).generate());
        demonsDreamEntry.withParent(BookEntryParentModel.create(introEntry.getId()));
        this.layout().entry(demonsDreamEntry).below(introEntry, 2);

        var spiritFireEntry = this.add(new SpiritFireEntry(this).generate());
        spiritFireEntry.withParent(BookEntryParentModel.create(demonsDreamEntry.getId()));
        this.layout().entry(spiritFireEntry).rightOf(demonsDreamEntry, 4);

        var healingSpiritsEntry = this.add(new HealingSpiritsEntry(this).generate());
        healingSpiritsEntry.withParent(BookEntryParentModel.create(demonsDreamEntry.getId()));
        this.layout().entry(healingSpiritsEntry).rightOf(demonsDreamEntry, 2).below(2);

        var thirdEyeEntry = this.add(new ThirdEyeEntry(this).generate());
        thirdEyeEntry.withParent(BookEntryParentModel.create(demonsDreamEntry.getId()));
        this.layout().entry(thirdEyeEntry).below(demonsDreamEntry, 2);

        var divinationRodEntry = this.add(new DivinationRodEntry(this).generate());
        divinationRodEntry.withParent(BookEntryParentModel.create(spiritFireEntry.getId()));
        this.layout().entry(divinationRodEntry).above(spiritFireEntry, 2);

        var theurgyDivinationRodEntry = this.add(new TheurgyDivinationRodsEntry(this).generate());
        theurgyDivinationRodEntry
                .withParent(BookEntryParentModel.create(divinationRodEntry.getId()))
                .withCondition(
                        BookAndConditionModel.create().withChildren(
                                this.condition().researchNodeEntryViewedOnce(OccultismResearch.GETTING_STARTED_DIVINATION_ROD, divinationRodEntry),
                                BookModLoadedConditionModel.create().withModId("theurgy")
                        )
                )
                .hideWhileLocked(true);
        this.layout().entry(theurgyDivinationRodEntry).above(divinationRodEntry, 2);

        var candleEntry = this.add(new CandleEntry(this).generate());
        candleEntry.withParent(BookEntryParentModel.create(spiritFireEntry.getId()));
        this.layout().entry(candleEntry).rightOf(spiritFireEntry, 2);

        var ritualPrepChalkEntry = this.add(new RitualPrepChalkEntry(this).generate());
        ritualPrepChalkEntry.withParent(BookEntryParentModel.create(candleEntry.getId()));
        this.layout().entry(ritualPrepChalkEntry).rightOf(candleEntry, 2).above(2);

        var brushEntry = this.add(new BrushEntry(this).generate());
        brushEntry.withParent(BookEntryParentModel.create(ritualPrepChalkEntry.getId()));
        this.layout().entry(brushEntry).above(ritualPrepChalkEntry, 2);

        var ritualPrepBowlEntry = this.add(new RitualPrepBowlEntry(this).generate());
        ritualPrepBowlEntry.withParent(BookEntryParentModel.create(ritualPrepChalkEntry.getId()));
        this.layout().entry(ritualPrepBowlEntry).rightOf(ritualPrepChalkEntry, 2);

        var booksOfBinding = this.add(new BooksOfBindingEntry(this).generate());
        booksOfBinding.withParent(BookEntryParentModel.create(candleEntry.getId()));
        this.layout().entry(booksOfBinding).rightOf(candleEntry, 2).below(2);

        var booksOfBindingAutomation = this.add(new BooksOfBindingAutomationEntry(this).generate());
        booksOfBindingAutomation.withParent(BookEntryParentModel.create(booksOfBinding.getId()));
        this.layout().entry(booksOfBindingAutomation).below(booksOfBinding, 2);

        var bookshelfBinding = this.add(new BookshelfBindingEntry(this).generate());
        this.layout().entry(bookshelfBinding).rightOf(booksOfBinding, 2).below(2);
        bookshelfBinding.withParent(BookEntryParentModel.create(booksOfBinding.getId()));

        var booksOfCalling = this.add(new BooksOfCallingEntry(this).generate());
        this.layout().entry(booksOfCalling).rightOf(bookshelfBinding, 2);
        booksOfCalling.withParent(BookEntryParentModel.create(booksOfBinding.getId()));

        var ritualEntry = this.add(new FirstRitualEntry(this).generate());
        this.layout().entry(ritualEntry).rightOf(ritualPrepBowlEntry, 2).below(2);
        ritualEntry
                .withParent(BookEntryParentModel.create(ritualPrepBowlEntry.getId()))
                .withParent(BookEntryParentModel.create(booksOfBinding.getId()));

        var advancedChalksEntry = this.add(new ChalksEntry(this).generate());
        this.layout().entry(advancedChalksEntry).rightOf(ritualEntry, 4);
        advancedChalksEntry.withParent(BookEntryParentModel.create(ritualEntry.getId()));

        var ritualSatchelsEntry = this.add(new RitualSatchelsEntry(this).generate());
        this.layout().entry(ritualSatchelsEntry).rightOf(advancedChalksEntry, 4);
        ritualSatchelsEntry.withParent(BookEntryParentModel.create(advancedChalksEntry.getId()))
                .withCondition(this.condition().researchNodeEntryViewedOnce(OccultismResearch.PENTACLES_CRAFT_DJINNI, OccultismResearch.entryModel(PentaclesCategory.CATEGORY_ID, CraftDjinniEntry.ENTRY_ID)));

        var moreRitualsEntry = this.add(new MoreRitualsEntry(this).generate());
        this.layout().entry(moreRitualsEntry).below(advancedChalksEntry, 2);
        moreRitualsEntry.withCategoryToOpen(this.modLoc("rituals"));
        moreRitualsEntry.withParent(BookEntryParentModel.create(advancedChalksEntry.getId()));

        var greyParticlesEntry = this.add(new GreyParticlesEntry(this).generate());
        this.layout().entry(greyParticlesEntry).rightOf(ritualEntry, 2).below(4);
        greyParticlesEntry.withParent(BookEntryParentModel.create(ritualEntry.getId()));

        var spiritsSubcategory = this.add(new SpiritsEntry(this).generate());
        this.layout().entry(spiritsSubcategory).rightOf(greyParticlesEntry, 2);
        spiritsSubcategory.withCategoryToOpen(this.modLoc("spirits"));
        spiritsSubcategory.withParent(BookEntryParentModel.create(greyParticlesEntry.getId()));

        var otherworldGoggles = this.add(new OtherworldGogglesEntry(this).generate());
        this.layout().entry(otherworldGoggles).rightOf(advancedChalksEntry, 0).above(4);
        otherworldGoggles.withParent(BookEntryParentModel.create(advancedChalksEntry.getId()))
                .withCondition(this.condition().researchNodeEntryViewedOnce(OccultismResearch.PENTACLES_CRAFT_FOLIOT, OccultismResearch.entryModel(PentaclesCategory.CATEGORY_ID, CraftFoliotEntry.ENTRY_ID)));

        var infusedPickaxe = this.add(new InfusedPickaxeEntry(this).generate());
        this.layout().entry(infusedPickaxe).rightOf(otherworldGoggles, 2);
        infusedPickaxe.withParent(BookEntryParentModel.create(otherworldGoggles.getId()))
                .withCondition(this.condition().researchNodeEntryViewedOnce(OccultismResearch.PENTACLES_CRAFT_DJINNI, OccultismResearch.entryModel(PentaclesCategory.CATEGORY_ID, CraftDjinniEntry.ENTRY_ID)));

        var iesnium = this.add(new IesniumEntry(this).generate());
        this.layout().entry(iesnium).rightOf(infusedPickaxe, 2);
        iesnium.withParent(BookEntryParentModel.create(infusedPickaxe.getId()));

        var iesniumPickaxe = this.add(new IesniumPickaxeEntry(this).generate());
        this.layout().entry(iesniumPickaxe).above(iesnium, 2);
        iesniumPickaxe.withParent(BookEntryParentModel.create(iesnium.getId()));

        var magicLampsEntry = this.add(new MagicLampsEntry(this).generate());
        this.layout().entry(magicLampsEntry).rightOf(ritualEntry, 2).above(4);
        magicLampsEntry.withParent(BookEntryParentModel.create(ritualEntry.getId()));

        var silverEntry = this.add(new SilverEntry(this).generate());
        this.layout().entry(silverEntry).below(iesnium, 2);
        silverEntry.withParent(BookEntryParentModel.create(iesnium.getId()));

        var spiritMinersEntry = this.add(new SpiritMinersEntry(this).generate());
        this.layout().entry(spiritMinersEntry).rightOf(iesnium, 2);
        spiritMinersEntry.withParent(BookEntryParentModel.create(iesnium.getId()));

        var mineshaftEntry = this.add(new MineshaftEntry(this).generate());
        this.layout().entry(mineshaftEntry).above(spiritMinersEntry, 2);
        mineshaftEntry.withParent(BookEntryParentModel.create(spiritMinersEntry.getId()));

        var spiritAttunedCrystal = this.add(new SpiritAttunedCrystalEntry(this).generate());
        this.layout().entry(spiritAttunedCrystal).rightOf(spiritMinersEntry, 2);
        spiritAttunedCrystal.withParent(BookEntryParentModel.create(spiritMinersEntry.getId()));

        var otherworldWoodEntry = this.add(new OtherworldWoodEntry(this).generate());
        this.layout().entry(otherworldWoodEntry).below(thirdEyeEntry, 2);
        otherworldWoodEntry.withParent(BookEntryParentModel.create(thirdEyeEntry.getId()));
        otherworldWoodEntry.withParent(BookEntryParentModel.create(spiritFireEntry.getId()));

        var otherstoneBlocksEntry = this.add(new OtherstoneBlocksEntry(this).generate());
        this.layout().entry(otherstoneBlocksEntry).above(infusedPickaxe, 2);
        otherstoneBlocksEntry.withParent(BookEntryParentModel.create(infusedPickaxe.getId()));

        var daturaEntry = this.add(new DaturaEntry(this).generate());
        this.layout().entry(daturaEntry).below(healingSpiritsEntry, 2);
        daturaEntry.withParent(BookEntryParentModel.create(demonsDreamEntry.getId()));

        var pitayaEntry = this.add(new PitayaEntry(this).generate());
        this.layout().entry(pitayaEntry).rightOf(daturaEntry, 2);
        pitayaEntry.withParent(BookEntryParentModel.create(daturaEntry.getId()));

        var modFoodEntry = this.add(new ModFoodEntry(this).generate());
        this.layout().entry(modFoodEntry).rightOf(pitayaEntry, 2);
        modFoodEntry.withParent(BookEntryParentModel.create(pitayaEntry.getId()));

        var storageEntry = this.add(new StorageEntry(this).generate());
        this.layout().entry(storageEntry).rightOf(advancedChalksEntry, 2).above(2);
        storageEntry.withCategoryToOpen(this.modLoc("storage"));
        storageEntry.withParent(BookEntryParentModel.create(advancedChalksEntry.getId()))
                .withCondition(this.condition().researchNodeEntryViewedOnce(OccultismResearch.PENTACLES_CRAFT_DJINNI, OccultismResearch.entryModel(PentaclesCategory.CATEGORY_ID, CraftDjinniEntry.ENTRY_ID)));

        var possessionRitualsEntry = this.add(new PossessionRitualsEntry(this).generate());
        this.layout().entry(possessionRitualsEntry).rightOf(moreRitualsEntry, 4).below(2);
        possessionRitualsEntry.withParent(BookEntryParentModel.create(moreRitualsEntry.getId()))
                .withCondition(this.condition().researchNodeEntryViewedOnce(OccultismResearch.PENTACLES_POSSESS_FOLIOT, OccultismResearch.entryModel(PentaclesCategory.CATEGORY_ID, PossessFoliotEntry.ENTRY_ID)));

        var familiarRitualsEntry = this.add(new FamiliarRitualsEntry(this).generate());
        this.layout().entry(familiarRitualsEntry).rightOf(moreRitualsEntry, 6).below(2);
        familiarRitualsEntry.withParent(BookEntryParentModel.create(moreRitualsEntry.getId()))
                .withCondition(this.condition().researchNodeEntryViewedOnce(OccultismResearch.PENTACLES_POSSESS_FOLIOT, OccultismResearch.entryModel(PentaclesCategory.CATEGORY_ID, PossessFoliotEntry.ENTRY_ID)));

        var summoningRitualsEntry = this.add(new SummoningRitualsEntry(this).generate());
        this.layout().entry(summoningRitualsEntry).rightOf(moreRitualsEntry, 8).below(2);
        summoningRitualsEntry.withParent(BookEntryParentModel.create(moreRitualsEntry.getId()))
                .withCondition(this.condition().researchNodeEntryViewedOnce(OccultismResearch.PENTACLES_SUMMON_FOLIOT, OccultismResearch.entryModel(PentaclesCategory.CATEGORY_ID, SummonFoliotEntry.ENTRY_ID)));

        var craftingRitualsEntry = this.add(new CraftingRitualsEntry(this).generate());
        this.layout().entry(craftingRitualsEntry).rightOf(moreRitualsEntry, 10).below(2);
        craftingRitualsEntry.withParent(BookEntryParentModel.create(moreRitualsEntry.getId()))
                .withCondition(this.condition().researchNodeEntryViewedOnce(OccultismResearch.PENTACLES_CRAFT_FOLIOT, OccultismResearch.entryModel(PentaclesCategory.CATEGORY_ID, CraftFoliotEntry.ENTRY_ID)));
    }

    @Override
    protected String categoryName() {
        return "Getting Started";
    }

    @Override
    protected BookIconModel categoryIcon() {
        return BookIconModel.create(OccultismItems.DICTIONARY_OF_SPIRITS_ICON);
    }

    @Override
    public String categoryId() {
        return CATEGORY_ID;
    }
}
