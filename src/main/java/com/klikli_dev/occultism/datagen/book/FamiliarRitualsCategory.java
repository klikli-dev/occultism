package com.klikli_dev.occultism.datagen.book;

import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookEntryParentModel;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.occultism.datagen.OccultismResearch;
import com.klikli_dev.occultism.datagen.OccultismBookProvider;
import com.klikli_dev.occultism.datagen.book.familiar_rituals.*;
import com.klikli_dev.occultism.datagen.book.pentacles.*;

public class FamiliarRitualsCategory extends CategoryProvider {

    public static final String CATEGORY_ID = "familiar_rituals";

    public FamiliarRitualsCategory(OccultismBookProvider parent) {
        super(parent);
    }

    @Override
    protected void generateEntries() {

        var overview = this.add(new FamiliarsRitualsOverviewEntry(this).generate());
        overview.withCondition(this.condition().researchNodeEntryViewedOnce(OccultismResearch.PENTACLES_POSSESS_FOLIOT, OccultismResearch.entryModel(PentaclesCategory.CATEGORY_ID, PossessFoliotEntry.ENTRY_ID)));
        this.layout().entry(overview).at(-8, 0);
        var returnToRituals = this.add(new ReturnToRitualsEntry(this).generate());
        returnToRituals.withCategoryToOpen(this.modLoc("rituals"));
        returnToRituals.withParent(BookEntryParentModel.create(overview.getId()))
                .withCondition(this.condition().researchNodeEntryViewedOnce(OccultismResearch.PENTACLES_POSSESS_FOLIOT, OccultismResearch.entryModel(PentaclesCategory.CATEGORY_ID, PossessFoliotEntry.ENTRY_ID)));
        this.layout().entry(returnToRituals).leftOf(overview, 2);

        var resurrection = this.add(new ResurrectFamiliarEntry(this).generate());
        resurrection.withParent(BookEntryParentModel.create(overview.getId()))
                .withCondition(this.condition().researchNodeEntryViewedOnce(OccultismResearch.PENTACLES_RESURRECT_SPIRIT, OccultismResearch.entryModel(PentaclesCategory.CATEGORY_ID, ResurrectSpiritEntry.ENTRY_ID)));
        this.layout().entry(resurrection).above(overview, 2);
        var resurrectAllay = this.add(new ResurrectAllayEntry(this).generate());
        resurrectAllay.withParent(BookEntryParentModel.create(overview.getId()))
                .withCondition(this.condition().researchNodeEntryViewedOnce(OccultismResearch.PENTACLES_RESURRECT_SPIRIT, OccultismResearch.entryModel(PentaclesCategory.CATEGORY_ID, ResurrectSpiritEntry.ENTRY_ID)));
        this.layout().entry(resurrectAllay).below(overview, 2);
        var resurrectionGreat = this.add(new GreatResurrectionEntry(this).generate());
        resurrectionGreat.withParent(BookEntryParentModel.create(resurrection.getId()))
                .withCondition(this.condition().researchNodeEntryViewedOnce(OccultismResearch.PENTACLES_RESURRECT_SPIRIT, OccultismResearch.entryModel(PentaclesCategory.CATEGORY_ID, ResurrectSpiritEntry.ENTRY_ID)));
        this.layout().entry(resurrectionGreat).above(resurrection, 2);

        var familiarBat = this.add(new FamiliarBatEntry(this).generate());
        familiarBat.withParent(BookEntryParentModel.create(overview.getId()))
                .withCondition(this.condition().researchNodeEntryViewedOnce(OccultismResearch.PENTACLES_POSSESS_DJINNI, OccultismResearch.entryModel(PentaclesCategory.CATEGORY_ID, PossessDjinniEntry.ENTRY_ID)));
        this.layout().entry(familiarBat).rightOf(overview, 9).below(4);
        var familiarBeaver = this.add(new FamiliarBeaverEntry(this).generate());
        familiarBeaver.withParent(BookEntryParentModel.create(overview.getId()))
                .withCondition(this.condition().researchNodeEntryViewedOnce(OccultismResearch.PENTACLES_POSSESS_FOLIOT, OccultismResearch.entryModel(PentaclesCategory.CATEGORY_ID, PossessFoliotEntry.ENTRY_ID)));
        this.layout().entry(familiarBeaver).rightOf(overview, 3).above(4);
        var familiarBeholder = this.add(new FamiliarBeholderEntry(this).generate());
        familiarBeholder.withParent(BookEntryParentModel.create(overview.getId()))
                .withCondition(this.condition().researchNodeEntryViewedOnce(OccultismResearch.PENTACLES_POSSESS_DJINNI, OccultismResearch.entryModel(PentaclesCategory.CATEGORY_ID, PossessDjinniEntry.ENTRY_ID)));
        this.layout().entry(familiarBeholder).rightOf(overview, 4).below(2);
        var familiarBlacksmith = this.add(new FamiliarBlacksmithEntry(this).generate());
        familiarBlacksmith.withParent(BookEntryParentModel.create(overview.getId()))
                .withCondition(this.condition().researchNodeEntryViewedOnce(OccultismResearch.PENTACLES_POSSESS_FOLIOT, OccultismResearch.entryModel(PentaclesCategory.CATEGORY_ID, PossessFoliotEntry.ENTRY_ID)));
        this.layout().entry(familiarBlacksmith).rightOf(overview, 6).above(2);
        var familiarChimera = this.add(new FamiliarChimeraEntry(this).generate());
        familiarChimera.withParent(BookEntryParentModel.create(overview.getId()))
                .withCondition(this.condition().researchNodeEntryViewedOnce(OccultismResearch.PENTACLES_POSSESS_DJINNI, OccultismResearch.entryModel(PentaclesCategory.CATEGORY_ID, PossessDjinniEntry.ENTRY_ID)));
        this.layout().entry(familiarChimera).rightOf(overview, 8).above(2);
        var familiarCthulhu = this.add(new FamiliarCthulhuEntry(this).generate());
        familiarCthulhu.withParent(BookEntryParentModel.create(overview.getId()))
                .withCondition(this.condition().researchNodeEntryViewedOnce(OccultismResearch.PENTACLES_POSSESS_DJINNI, OccultismResearch.entryModel(PentaclesCategory.CATEGORY_ID, PossessDjinniEntry.ENTRY_ID)));
        this.layout().entry(familiarCthulhu).rightOf(overview, 6).below(2);
        var familiarDeer = this.add(new FamiliarDeerEntry(this).generate());
        familiarDeer.withParent(BookEntryParentModel.create(overview.getId()))
                .withCondition(this.condition().researchNodeEntryViewedOnce(OccultismResearch.PENTACLES_POSSESS_FOLIOT, OccultismResearch.entryModel(PentaclesCategory.CATEGORY_ID, PossessFoliotEntry.ENTRY_ID)));
        this.layout().entry(familiarDeer).rightOf(familiarBeaver, 2);
        var familiarDevil = this.add(new FamiliarDevilEntry(this).generate());
        familiarDevil.withParent(BookEntryParentModel.create(overview.getId()))
                .withCondition(this.condition().researchNodeEntryViewedOnce(OccultismResearch.PENTACLES_POSSESS_DJINNI, OccultismResearch.entryModel(PentaclesCategory.CATEGORY_ID, PossessDjinniEntry.ENTRY_ID)));
        this.layout().entry(familiarDevil).rightOf(overview, 10).below(2);
        var familiarDragon = this.add(new FamiliarDragonEntry(this).generate());
        familiarDragon.withParent(BookEntryParentModel.create(overview.getId()))
                .withCondition(this.condition().researchNodeEntryViewedOnce(OccultismResearch.PENTACLES_POSSESS_DJINNI, OccultismResearch.entryModel(PentaclesCategory.CATEGORY_ID, PossessDjinniEntry.ENTRY_ID)));
        this.layout().entry(familiarDragon).rightOf(overview, 8).below(2);
        var familiarFairy = this.add(new FamiliarFairyEntry(this).generate());
        familiarFairy.withParent(BookEntryParentModel.create(overview.getId()))
                .withCondition(this.condition().researchNodeEntryViewedOnce(OccultismResearch.PENTACLES_POSSESS_DJINNI, OccultismResearch.entryModel(PentaclesCategory.CATEGORY_ID, PossessDjinniEntry.ENTRY_ID)));
        this.layout().entry(familiarFairy).rightOf(overview, 3).below(4);
        var familiarGreedy = this.add(new FamiliarGreedyEntry(this).generate());
        familiarGreedy.withParent(BookEntryParentModel.create(overview.getId()))
                .withCondition(this.condition().researchNodeEntryViewedOnce(OccultismResearch.PENTACLES_POSSESS_FOLIOT, OccultismResearch.entryModel(PentaclesCategory.CATEGORY_ID, PossessFoliotEntry.ENTRY_ID)));
        this.layout().entry(familiarGreedy).rightOf(overview, 4).above(2);
        var familiarGuardian = this.add(new FamiliarGuardianEntry(this).generate());
        familiarGuardian.withParent(BookEntryParentModel.create(overview.getId()))
                .withCondition(this.condition().researchNodeEntryViewedOnce(OccultismResearch.PENTACLES_POSSESS_AFRIT, OccultismResearch.entryModel(PentaclesCategory.CATEGORY_ID, PossessAfritEntry.ENTRY_ID)));
        this.layout().entry(familiarGuardian).rightOf(overview, 10).above(2);
        var familiarHeadlessRatman = this.add(new FamiliarHeadlessRatmanEntry(this).generate());
        familiarHeadlessRatman.withParent(BookEntryParentModel.create(overview.getId()))
                .withCondition(this.condition().researchNodeEntryViewedOnce(OccultismResearch.PENTACLES_POSSESS_DJINNI, OccultismResearch.entryModel(PentaclesCategory.CATEGORY_ID, PossessDjinniEntry.ENTRY_ID)));
        this.layout().entry(familiarHeadlessRatman).rightOf(overview, 5).below(4);
        var familiarMummy = this.add(new FamiliarMummyEntry(this).generate());
        familiarMummy.withParent(BookEntryParentModel.create(overview.getId()))
                .withCondition(this.condition().researchNodeEntryViewedOnce(OccultismResearch.PENTACLES_POSSESS_DJINNI, OccultismResearch.entryModel(PentaclesCategory.CATEGORY_ID, PossessDjinniEntry.ENTRY_ID)));
        this.layout().entry(familiarMummy).rightOf(overview, 7).below(4);
        var familiarOtherworldBird = this.add(new FamiliarOtherworldBirdEntry(this).generate());
        familiarOtherworldBird.withParent(BookEntryParentModel.create(overview.getId()))
                .withCondition(this.condition().researchNodeEntryViewedOnce(OccultismResearch.PENTACLES_POSSESS_DJINNI, OccultismResearch.entryModel(PentaclesCategory.CATEGORY_ID, PossessDjinniEntry.ENTRY_ID)));
        this.layout().entry(familiarOtherworldBird).rightOf(overview, 2).below(2);
        var familiarParrot = this.add(new FamiliarParrotEntry(this).generate());
        familiarParrot.withParent(BookEntryParentModel.create(overview.getId()))
                .withCondition(this.condition().researchNodeEntryViewedOnce(OccultismResearch.PENTACLES_POSSESS_FOLIOT, OccultismResearch.entryModel(PentaclesCategory.CATEGORY_ID, PossessFoliotEntry.ENTRY_ID)));
        this.layout().entry(familiarParrot).rightOf(overview, 2).above(2);
        var familiarShubNiggurath = this.add(new FamiliarShubNiggurathEntry(this).generate());
        familiarShubNiggurath.withParent(BookEntryParentModel.create(familiarChimera.getId()));
        this.layout().entry(familiarShubNiggurath).above(familiarChimera, 2);

        var demonicPartner = this.add(new DemonicPartnerEntry(this).generate());
        demonicPartner.withParent(BookEntryParentModel.create(overview.getId()))
                .withCondition(this.condition().researchNodeEntryViewedOnce(OccultismResearch.PENTACLES_SUMMON_DJINNI, OccultismResearch.entryModel(PentaclesCategory.CATEGORY_ID, SummonDjinniEntry.ENTRY_ID)));
        this.layout().entry(demonicPartner).rightOf(overview, 13);
        var iesniumGolem = this.add(new IesniumGolemEntry(this).generate());
        iesniumGolem.withParent(BookEntryParentModel.create(overview.getId()))
                .withCondition(this.condition().researchNodeEntryViewedOnce(OccultismResearch.PENTACLES_POSSESS_MARID, OccultismResearch.entryModel(PentaclesCategory.CATEGORY_ID, PossessMaridEntry.ENTRY_ID)));
        this.layout().entry(iesniumGolem).rightOf(overview, 11).above(4);
    }

    @Override
    protected String categoryName() {
        return "Familiar Rituals";
    }

    @Override
    protected BookIconModel categoryIcon() {
        return BookIconModel.create(this.modLoc("textures/gui/book/familiar.png"));
    }

    @Override
    public String categoryId() {
        return CATEGORY_ID;
    }
}
