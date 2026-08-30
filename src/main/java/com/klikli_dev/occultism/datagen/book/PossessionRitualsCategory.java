package com.klikli_dev.occultism.datagen.book;

import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookEntryParentModel;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.occultism.datagen.OccultismBookProvider;
import com.klikli_dev.occultism.datagen.OccultismResearch;
import com.klikli_dev.occultism.datagen.book.pentacles.*;
import com.klikli_dev.occultism.datagen.book.possession_rituals.*;

public class PossessionRitualsCategory extends CategoryProvider {

    public static final String CATEGORY_ID = "possession_rituals";

    public PossessionRitualsCategory(OccultismBookProvider parent) {
        super(parent);
    }

    @Override
    protected void generateEntries() {
        var overview = this.add(new PossessionOverviewEntry(this).generate());
        overview.withCondition(this.condition().researchNodeEntryViewedOnce(OccultismResearch.PENTACLES_POSSESS_FOLIOT, OccultismResearch.entryModel(PentaclesCategory.CATEGORY_ID, PossessFoliotEntry.ENTRY_ID)));
        this.layout().entry(overview).at(-8, -1);

        var returnToRituals = this.add(new ReturnToRitualsEntry(this).generate());
        returnToRituals.withCategoryToOpen(this.modLoc("rituals"));
        returnToRituals.withParent(BookEntryParentModel.create(overview.getId()))
                .withCondition(this.condition().researchNodeEntryViewedOnce(OccultismResearch.PENTACLES_POSSESS_FOLIOT, OccultismResearch.entryModel(PentaclesCategory.CATEGORY_ID, PossessFoliotEntry.ENTRY_ID)));
        this.layout().entry(returnToRituals).leftOf(overview, 2);

        var possessEndermite = this.add(new PossessEndermiteEntry(this).generate());
        possessEndermite.withParent(BookEntryParentModel.create(overview.getId()))
                .withCondition(this.condition().researchNodeEntryViewedOnce(OccultismResearch.PENTACLES_POSSESS_FOLIOT, OccultismResearch.entryModel(PentaclesCategory.CATEGORY_ID, PossessFoliotEntry.ENTRY_ID)));
        this.layout().entry(possessEndermite).rightOf(overview, 2).above(2);
        var possessPhantom = this.add(new PossessPhantomEntry(this).generate());
        possessPhantom.withParent(BookEntryParentModel.create(overview.getId()))
                .withCondition(this.condition().researchNodeEntryViewedOnce(OccultismResearch.PENTACLES_POSSESS_FOLIOT, OccultismResearch.entryModel(PentaclesCategory.CATEGORY_ID, PossessFoliotEntry.ENTRY_ID)));
        this.layout().entry(possessPhantom).rightOf(overview, 3).above(4);
        var possessSkeleton = this.add(new PossessSkeletonEntry(this).generate());
        possessSkeleton.withParent(BookEntryParentModel.create(overview.getId()))
                .withCondition(this.condition().researchNodeEntryViewedOnce(OccultismResearch.PENTACLES_POSSESS_FOLIOT, OccultismResearch.entryModel(PentaclesCategory.CATEGORY_ID, PossessFoliotEntry.ENTRY_ID)));
        this.layout().entry(possessSkeleton).rightOf(overview, 4).above(2);
        var possessWitch = this.add(new PossessWitchEntry(this).generate());
        possessWitch.withParent(BookEntryParentModel.create(overview.getId()))
                .withCondition(this.condition().researchNodeEntryViewedOnce(OccultismResearch.PENTACLES_POSSESS_FOLIOT, OccultismResearch.entryModel(PentaclesCategory.CATEGORY_ID, PossessFoliotEntry.ENTRY_ID)));
        this.layout().entry(possessWitch).rightOf(overview, 5).above(4);
        var possessEnderman = this.add(new PossessEndermanEntry(this).generate());
        possessEnderman.withParent(BookEntryParentModel.create(overview.getId()))
                .withCondition(this.condition().researchNodeEntryViewedOnce(OccultismResearch.PENTACLES_POSSESS_DJINNI, OccultismResearch.entryModel(PentaclesCategory.CATEGORY_ID, PossessDjinniEntry.ENTRY_ID)));
        this.layout().entry(possessEnderman).rightOf(overview, 6).above(2);
        var possessBee = this.add(new PossessBeeEntry(this).generate());
        possessBee.withParent(BookEntryParentModel.create(overview.getId()))
                .withCondition(this.condition().researchNodeEntryViewedOnce(OccultismResearch.PENTACLES_POSSESS_DJINNI, OccultismResearch.entryModel(PentaclesCategory.CATEGORY_ID, PossessDjinniEntry.ENTRY_ID)));
        this.layout().entry(possessBee).rightOf(overview, 7).above(4);
        var possessGhast = this.add(new PossessGhastEntry(this).generate());
        possessGhast.withParent(BookEntryParentModel.create(overview.getId()))
                .withCondition(this.condition().researchNodeEntryViewedOnce(OccultismResearch.PENTACLES_POSSESS_DJINNI, OccultismResearch.entryModel(PentaclesCategory.CATEGORY_ID, PossessDjinniEntry.ENTRY_ID)));
        this.layout().entry(possessGhast).rightOf(overview, 8).above(2);
        var possessWeakShulker = this.add(new PossessWeakShulkerEntry(this).generate());
        possessWeakShulker.withParent(BookEntryParentModel.create(overview.getId()))
                .withCondition(this.condition().researchNodeEntryViewedOnce(OccultismResearch.PENTACLES_POSSESS_DJINNI, OccultismResearch.entryModel(PentaclesCategory.CATEGORY_ID, PossessDjinniEntry.ENTRY_ID)));
        this.layout().entry(possessWeakShulker).rightOf(overview, 9).above(4);
        var possessBlaze = this.add(new PossessBlazeEntry(this).generate());
        possessBlaze.withParent(BookEntryParentModel.create(overview.getId()))
                .withCondition(this.condition().researchNodeEntryViewedOnce(OccultismResearch.PENTACLES_POSSESS_DJINNI, OccultismResearch.entryModel(PentaclesCategory.CATEGORY_ID, PossessDjinniEntry.ENTRY_ID)));
        this.layout().entry(possessBlaze).rightOf(overview, 10).above(2);
        var possessZombiePiglin = this.add(new PossessZombiePiglinEntry(this).generate());
        possessZombiePiglin.withParent(BookEntryParentModel.create(overview.getId()))
                .withCondition(this.condition().researchNodeEntryViewedOnce(OccultismResearch.PENTACLES_POSSESS_UNBOUND_AFRIT, OccultismResearch.entryModel(PentaclesCategory.CATEGORY_ID, PossessUnboundAfritEntry.ENTRY_ID)));
        this.layout().entry(possessZombiePiglin).rightOf(overview, 11).above(4);
        var possessGuardian = this.add(new PossessGuardianEntry(this).generate());
        possessGuardian.withParent(BookEntryParentModel.create(overview.getId()))
                .withCondition(this.condition().researchNodeEntryViewedOnce(OccultismResearch.PENTACLES_POSSESS_UNBOUND_AFRIT, OccultismResearch.entryModel(PentaclesCategory.CATEGORY_ID, PossessUnboundAfritEntry.ENTRY_ID)));
        this.layout().entry(possessGuardian).rightOf(overview, 12).above(2);
        var possessWarden = this.add(new PossessWardenEntry(this).generate());
        possessWarden.withParent(BookEntryParentModel.create(overview.getId()))
                .withCondition(this.condition().researchNodeEntryViewedOnce(OccultismResearch.PENTACLES_POSSESS_AFRIT, OccultismResearch.entryModel(PentaclesCategory.CATEGORY_ID, PossessAfritEntry.ENTRY_ID)));
        this.layout().entry(possessWarden).rightOf(overview, 14).above(2);
        var possessElderGuardian = this.add(new PossessElderGuardianEntry(this).generate());
        possessElderGuardian.withParent(BookEntryParentModel.create(overview.getId()))
                .withCondition(this.condition().researchNodeEntryViewedOnce(OccultismResearch.PENTACLES_POSSESS_AFRIT, OccultismResearch.entryModel(PentaclesCategory.CATEGORY_ID, PossessAfritEntry.ENTRY_ID)));
        this.layout().entry(possessElderGuardian).rightOf(overview, 13).above(4);
        var possessHoglin = this.add(new PossessHoglinEntry(this).generate());
        possessHoglin.withParent(BookEntryParentModel.create(overview.getId()))
                .withCondition(this.condition().researchNodeEntryViewedOnce(OccultismResearch.PENTACLES_POSSESS_AFRIT, OccultismResearch.entryModel(PentaclesCategory.CATEGORY_ID, PossessAfritEntry.ENTRY_ID)));
        this.layout().entry(possessHoglin).rightOf(overview, 16).above(2);
        var possessShulker = this.add(new PossessShulkerEntry(this).generate());
        possessShulker.withParent(BookEntryParentModel.create(overview.getId()))
                .withCondition(this.condition().researchNodeEntryViewedOnce(OccultismResearch.PENTACLES_POSSESS_AFRIT, OccultismResearch.entryModel(PentaclesCategory.CATEGORY_ID, PossessAfritEntry.ENTRY_ID)));
        this.layout().entry(possessShulker).rightOf(overview, 15).above(4);
        var mercyGoat = this.add(new MercyGoatEntry(this).generate());
        mercyGoat.withParent(BookEntryParentModel.create(overview.getId()))
                .withCondition(this.condition().researchNodeEntryViewedOnce(OccultismResearch.PENTACLES_POSSESS_MARID, OccultismResearch.entryModel(PentaclesCategory.CATEGORY_ID, PossessMaridEntry.ENTRY_ID)));
        this.layout().entry(mercyGoat).rightOf(overview, 17).above(4);

        var witherSkull = this.add(new WitherSkullEntry(this).generate());
        witherSkull.withParent(BookEntryParentModel.create(overview.getId()))
                .withCondition(this.condition().researchNodeEntryViewedOnce(OccultismResearch.PENTACLES_CONTACT_WILD, OccultismResearch.entryModel(PentaclesCategory.CATEGORY_ID, ContactWildSpiritEntry.ENTRY_ID)));
        this.layout().entry(witherSkull).rightOf(overview, 2).below(2);
        var hordeIllager = this.add(new HordeIllagerEntry(this).generate());
        hordeIllager.withParent(BookEntryParentModel.create(overview.getId()))
                .withCondition(this.condition().researchNodeEntryViewedOnce(OccultismResearch.PENTACLES_CONTACT_WILD, OccultismResearch.entryModel(PentaclesCategory.CATEGORY_ID, ContactWildSpiritEntry.ENTRY_ID)));
        this.layout().entry(hordeIllager).rightOf(overview, 3).below(4);
        var hordeDesert = this.add(new HordeDesertEntry(this).generate());
        hordeDesert.withParent(BookEntryParentModel.create(overview.getId()))
                .withCondition(this.condition().researchNodeEntryViewedOnce(OccultismResearch.PENTACLES_CONTACT_WILD, OccultismResearch.entryModel(PentaclesCategory.CATEGORY_ID, ContactWildSpiritEntry.ENTRY_ID)));
        this.layout().entry(hordeDesert).rightOf(overview, 4).below(2);
        var hordeDrowned = this.add(new HordeDrownedEntry(this).generate());
        hordeDrowned.withParent(BookEntryParentModel.create(overview.getId()))
                .withCondition(this.condition().researchNodeEntryViewedOnce(OccultismResearch.PENTACLES_CONTACT_WILD, OccultismResearch.entryModel(PentaclesCategory.CATEGORY_ID, ContactWildSpiritEntry.ENTRY_ID)));
        this.layout().entry(hordeDrowned).rightOf(overview, 5).below(4);
        var hordeCreeper = this.add(new HordeCreeperEntry(this).generate());
        hordeCreeper.withParent(BookEntryParentModel.create(overview.getId()))
                .withCondition(this.condition().researchNodeEntryViewedOnce(OccultismResearch.PENTACLES_CONTACT_WILD, OccultismResearch.entryModel(PentaclesCategory.CATEGORY_ID, ContactWildSpiritEntry.ENTRY_ID)));
        this.layout().entry(hordeCreeper).rightOf(overview, 6).below(2);
        var hordeSilverfish = this.add(new HordeSilverfishEntry(this).generate());
        hordeSilverfish.withParent(BookEntryParentModel.create(overview.getId()))
                .withCondition(this.condition().researchNodeEntryViewedOnce(OccultismResearch.PENTACLES_CONTACT_WILD, OccultismResearch.entryModel(PentaclesCategory.CATEGORY_ID, ContactWildSpiritEntry.ENTRY_ID)));
        this.layout().entry(hordeSilverfish).rightOf(overview, 7).below(4);
        var possessWeakBreeze = this.add(new PossessWeakBreezeEntry(this).generate());
        possessWeakBreeze.withParent(BookEntryParentModel.create(overview.getId()))
                .withCondition(this.condition().researchNodeEntryViewedOnce(OccultismResearch.PENTACLES_CONTACT_WILD, OccultismResearch.entryModel(PentaclesCategory.CATEGORY_ID, ContactWildSpiritEntry.ENTRY_ID)));
        this.layout().entry(possessWeakBreeze).rightOf(overview, 9).below(2);
        var possessBreeze = this.add(new PossessBreezeEntry(this).generate());
        possessBreeze.withParent(BookEntryParentModel.create(possessWeakBreeze.getId()))
                .withCondition(this.condition().researchNodeEntryViewedOnce(OccultismResearch.PENTACLES_POSSESS_WEAK_BREEZE, possessWeakBreeze));
        this.layout().entry(possessBreeze).below(possessWeakBreeze, 2);
        var possessStrongBreeze = this.add(new PossessStrongBreezeEntry(this).generate());
        possessStrongBreeze.withParent(BookEntryParentModel.create(possessBreeze.getId()))
                .withCondition(this.condition().researchNodeEntryViewedOnce(OccultismResearch.PENTACLES_POSSESS_BREEZE, possessBreeze));
        this.layout().entry(possessStrongBreeze).below(possessBreeze, 2);

        var possessRandomAnimal = this.add(new PossessRandomAnimalEntry(this).generate());
        possessRandomAnimal.withParent(BookEntryParentModel.create(overview.getId()))
                .withCondition(this.condition().researchNodeEntryViewedOnce(OccultismResearch.PENTACLES_POSSESS_FOLIOT, OccultismResearch.entryModel(PentaclesCategory.CATEGORY_ID, PossessFoliotEntry.ENTRY_ID)));
        this.layout().entry(possessRandomAnimal).rightOf(overview, 13).below(2);
        var wildRandomAnimal = this.add(new WildRandomAnimalEntry(this).generate());
        wildRandomAnimal.withParent(BookEntryParentModel.create(possessRandomAnimal.getId()))
                .withCondition(this.condition().researchNodeEntryViewedOnce(OccultismResearch.PENTACLES_CONTACT_WILD, OccultismResearch.entryModel(PentaclesCategory.CATEGORY_ID, ContactWildSpiritEntry.ENTRY_ID)));
        this.layout().entry(wildRandomAnimal).below(possessRandomAnimal, 2);
    }

    @Override
    protected String categoryName() {
        return "Possession Rituals";
    }

    @Override
    protected BookIconModel categoryIcon() {
        return BookIconModel.create(this.modLoc("textures/gui/book/possession.png"));
    }

    @Override
    public String categoryId() {
        return CATEGORY_ID;
    }
}
