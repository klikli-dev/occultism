package com.klikli_dev.occultism.datagen.book;

import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookEntryParentModel;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.condition.BookEntryReadConditionModel;
import com.klikli_dev.occultism.datagen.OccultismBookProvider;
import com.klikli_dev.occultism.datagen.book.familiar_rituals.*;
import com.klikli_dev.occultism.datagen.book.pentacles.*;

public class FamiliarRitualsCategory extends CategoryProvider {

    public static final String CATEGORY_ID = "familiar_rituals";

    public FamiliarRitualsCategory(OccultismBookProvider parent) {
        super(parent);
    }

    @Override
    public String[] generateEntryMap() {
        return new String[]{
                "_____e__J_O__Y__G__________",
                "___________________________",
                "_____a_X_S_L_M_T___________",
                "___________________________",
                "___r_o____________Z________",
                "___________________________",
                "_____H_W_K_N_Q_P___________",
                "___________________________",
                "________R_U_V_I____________"
        };
    }

    @Override
    protected void generateEntries() {
        String possessFoliotID = this.modId() + ":" + PentaclesCategory.CATEGORY_ID + "/" + PossessFoliotEntry.ENTRY_ID;
        String possessDjinniID = this.modId() + ":" + PentaclesCategory.CATEGORY_ID + "/" + PossessDjinniEntry.ENTRY_ID;
        String summonDjinniID = this.modId() + ":" + PentaclesCategory.CATEGORY_ID + "/" + SummonDjinniEntry.ENTRY_ID;
        String possessAfritID = this.modId() + ":" + PentaclesCategory.CATEGORY_ID + "/" + PossessAfritEntry.ENTRY_ID;
        String possessMaridID = this.modId() + ":" + PentaclesCategory.CATEGORY_ID + "/" + PossessMaridEntry.ENTRY_ID;
        String resurrectionID = this.modId() + ":" + PentaclesCategory.CATEGORY_ID + "/" + ResurrectSpiritEntry.ENTRY_ID;

        var overview = this.add(new FamiliarsRitualsOverviewEntry(this).generate('o'));
        overview.withCondition(BookEntryReadConditionModel.create().withEntry(possessFoliotID));
        var returnToRituals = this.add(new ReturnToRitualsEntry(this).generate('r'));
        returnToRituals.withCategoryToOpen(this.modLoc("rituals"));
        returnToRituals.withParent(BookEntryParentModel.create(overview.getId()))
                .withCondition(BookEntryReadConditionModel.create().withEntry(possessFoliotID));

        var resurrection = this.add(new ResurrectFamiliarEntry(this).generate('a'));
        resurrection.withParent(BookEntryParentModel.create(overview.getId()))
                .withCondition(BookEntryReadConditionModel.create().withEntry(resurrectionID));
        var resurrectAllay = this.add(new ResurrectAllayEntry(this).generate('H'));
        resurrectAllay.withParent(BookEntryParentModel.create(overview.getId()))
                .withCondition(BookEntryReadConditionModel.create().withEntry(resurrectionID));
        var resurrectionGreat = this.add(new GreatResurrectionEntry(this).generate('e'));
        resurrectionGreat.withParent(BookEntryParentModel.create(resurrection.getId()))
                .withCondition(BookEntryReadConditionModel.create().withEntry(resurrectionID));

        var familiarBat = this.add(new FamiliarBatEntry(this).generate('I'));
        familiarBat.withParent(BookEntryParentModel.create(overview.getId()))
                .withCondition(BookEntryReadConditionModel.create().withEntry(possessDjinniID));
        var familiarBeaver = this.add(new FamiliarBeaverEntry(this).generate('J'));
        familiarBeaver.withParent(BookEntryParentModel.create(overview.getId()))
                .withCondition(BookEntryReadConditionModel.create().withEntry(possessFoliotID));
        var familiarBeholder = this.add(new FamiliarBeholderEntry(this).generate('K'));
        familiarBeholder.withParent(BookEntryParentModel.create(overview.getId()))
                .withCondition(BookEntryReadConditionModel.create().withEntry(possessDjinniID));
        var familiarBlacksmith = this.add(new FamiliarBlacksmithEntry(this).generate('L'));
        familiarBlacksmith.withParent(BookEntryParentModel.create(overview.getId()))
                .withCondition(BookEntryReadConditionModel.create().withEntry(possessFoliotID));
        var familiarChimera = this.add(new FamiliarChimeraEntry(this).generate('M'));
        familiarChimera.withParent(BookEntryParentModel.create(overview.getId()))
                .withCondition(BookEntryReadConditionModel.create().withEntry(possessDjinniID));
        var familiarCthulhu = this.add(new FamiliarCthulhuEntry(this).generate('N'));
        familiarCthulhu.withParent(BookEntryParentModel.create(overview.getId()))
                .withCondition(BookEntryReadConditionModel.create().withEntry(possessDjinniID));
        var familiarDeer = this.add(new FamiliarDeerEntry(this).generate('O'));
        familiarDeer.withParent(BookEntryParentModel.create(overview.getId()))
                .withCondition(BookEntryReadConditionModel.create().withEntry(possessFoliotID));
        var familiarDevil = this.add(new FamiliarDevilEntry(this).generate('P'));
        familiarDevil.withParent(BookEntryParentModel.create(overview.getId()))
                .withCondition(BookEntryReadConditionModel.create().withEntry(possessDjinniID));
        var familiarDragon = this.add(new FamiliarDragonEntry(this).generate('Q'));
        familiarDragon.withParent(BookEntryParentModel.create(overview.getId()))
                .withCondition(BookEntryReadConditionModel.create().withEntry(possessDjinniID));
        var familiarFairy = this.add(new FamiliarFairyEntry(this).generate('R'));
        familiarFairy.withParent(BookEntryParentModel.create(overview.getId()))
                .withCondition(BookEntryReadConditionModel.create().withEntry(possessDjinniID));
        var familiarGreedy = this.add(new FamiliarGreedyEntry(this).generate('S'));
        familiarGreedy.withParent(BookEntryParentModel.create(overview.getId()))
                .withCondition(BookEntryReadConditionModel.create().withEntry(possessFoliotID));
        var familiarGuardian = this.add(new FamiliarGuardianEntry(this).generate('T'));
        familiarGuardian.withParent(BookEntryParentModel.create(overview.getId()))
                .withCondition(BookEntryReadConditionModel.create().withEntry(possessAfritID));
        var familiarHeadlessRatman = this.add(new FamiliarHeadlessRatmanEntry(this).generate('U'));
        familiarHeadlessRatman.withParent(BookEntryParentModel.create(overview.getId()))
                .withCondition(BookEntryReadConditionModel.create().withEntry(possessDjinniID));
        var familiarMummy = this.add(new FamiliarMummyEntry(this).generate('V'));
        familiarMummy.withParent(BookEntryParentModel.create(overview.getId()))
                .withCondition(BookEntryReadConditionModel.create().withEntry(possessDjinniID));
        var familiarOtherworldBird = this.add(new FamiliarOtherworldBirdEntry(this).generate('W'));
        familiarOtherworldBird.withParent(BookEntryParentModel.create(overview.getId()))
                .withCondition(BookEntryReadConditionModel.create().withEntry(possessDjinniID));
        var familiarParrot = this.add(new FamiliarParrotEntry(this).generate('X'));
        familiarParrot.withParent(BookEntryParentModel.create(overview.getId()))
                .withCondition(BookEntryReadConditionModel.create().withEntry(possessFoliotID));
        var familiarShubNiggurath = this.add(new FamiliarShubNiggurathEntry(this).generate('Y'));
        familiarShubNiggurath.withParent(BookEntryParentModel.create(familiarChimera.getId()));

        var demonicPartner = this.add(new DemonicPartnerEntry(this).generate('Z'));
        demonicPartner.withParent(BookEntryParentModel.create(overview.getId()))
                .withCondition(BookEntryReadConditionModel.create().withEntry(summonDjinniID));
        var iesniumGolem = this.add(new IesniumGolemEntry(this).generate('G'));
        iesniumGolem.withParent(BookEntryParentModel.create(overview.getId()))
                .withCondition(BookEntryReadConditionModel.create().withEntry(possessMaridID));
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
