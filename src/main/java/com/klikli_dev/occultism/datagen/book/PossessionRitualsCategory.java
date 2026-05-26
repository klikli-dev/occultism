package com.klikli_dev.occultism.datagen.book;

import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookEntryParentModel;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.condition.BookEntryReadConditionModel;
import com.klikli_dev.occultism.datagen.OccultismBookProvider;
import com.klikli_dev.occultism.datagen.book.pentacles.ContactWildSpiritEntry;
import com.klikli_dev.occultism.datagen.book.pentacles.PossessAfritEntry;
import com.klikli_dev.occultism.datagen.book.pentacles.PossessDjinniEntry;
import com.klikli_dev.occultism.datagen.book.pentacles.PossessFoliotEntry;
import com.klikli_dev.occultism.datagen.book.pentacles.PossessMaridEntry;
import com.klikli_dev.occultism.datagen.book.pentacles.PossessUnboundAfritEntry;
import com.klikli_dev.occultism.datagen.book.possession_rituals.*;

public class PossessionRitualsCategory extends CategoryProvider {

    public static final String CATEGORY_ID = "possession_rituals";

    public PossessionRitualsCategory(OccultismBookProvider parent) {
        super(parent);
    }

    @Override
    protected String[] generateEntryMap() {
        return new String[]{
                "________I_A_B_J_P_L_K_C____",
                "___________________________",
                "_______D_G_E_F_Q_R_M_N_____",
                "___________________________",
                "___r_o_____________________",
                "___________________________",
                "_______H_W_Y__S___p_a______",
                "___________________________",
                "________V_X_Z_T___d_b______",
                "___________________________",
                "______________U____________"
        };
    }

    @Override
    protected void generateEntries() {
        String possessFoliotID = this.modId() + ":" + PentaclesCategory.CATEGORY_ID + "/" + PossessFoliotEntry.ENTRY_ID;
        String possessDjinniID = this.modId() + ":" + PentaclesCategory.CATEGORY_ID + "/" + PossessDjinniEntry.ENTRY_ID;
        String possessUnboundAfritID = this.modId() + ":" + PentaclesCategory.CATEGORY_ID + "/" + PossessUnboundAfritEntry.ENTRY_ID;
        String possessAfritID = this.modId() + ":" + PentaclesCategory.CATEGORY_ID + "/" + PossessAfritEntry.ENTRY_ID;
        String possessMaridID = this.modId() + ":" + PentaclesCategory.CATEGORY_ID + "/" + PossessMaridEntry.ENTRY_ID;
        String possessWildID = this.modId() + ":" + PentaclesCategory.CATEGORY_ID + "/" + ContactWildSpiritEntry.ENTRY_ID;

        var overview = this.add(new PossessionOverviewEntry(this).generate('o'));
        overview.withCondition(BookEntryReadConditionModel.create().withEntry(possessFoliotID));

        var returnToRituals = this.add(new ReturnToRitualsEntry(this).generate('r'));
        returnToRituals.withCategoryToOpen(this.modLoc("rituals"));
        returnToRituals.withParent(BookEntryParentModel.create(overview.getId()))
                .withCondition(BookEntryReadConditionModel.create().withEntry(possessFoliotID));

        var possessEndermite = this.add(new PossessEndermiteEntry(this).generate('D'));
        possessEndermite.withParent(BookEntryParentModel.create(overview.getId()))
                .withCondition(BookEntryReadConditionModel.create().withEntry(possessFoliotID));
        var possessPhantom = this.add(new PossessPhantomEntry(this).generate('I'));
        possessPhantom.withParent(BookEntryParentModel.create(overview.getId()))
                .withCondition(BookEntryReadConditionModel.create().withEntry(possessFoliotID));
        var possessSkeleton = this.add(new PossessSkeletonEntry(this).generate('G'));
        possessSkeleton.withParent(BookEntryParentModel.create(overview.getId()))
                .withCondition(BookEntryReadConditionModel.create().withEntry(possessFoliotID));
        var possessWitch = this.add(new PossessWitchEntry(this).generate('A'));
        possessWitch.withParent(BookEntryParentModel.create(overview.getId()))
                .withCondition(BookEntryReadConditionModel.create().withEntry(possessFoliotID));
        var possessEnderman = this.add(new PossessEndermanEntry(this).generate('E'));
        possessEnderman.withParent(BookEntryParentModel.create(overview.getId()))
                .withCondition(BookEntryReadConditionModel.create().withEntry(possessDjinniID));
        var possessBee = this.add(new PossessBeeEntry(this).generate('B'));
        possessBee.withParent(BookEntryParentModel.create(overview.getId()))
                .withCondition(BookEntryReadConditionModel.create().withEntry(possessDjinniID));
        var possessGhast = this.add(new PossessGhastEntry(this).generate('F'));
        possessGhast.withParent(BookEntryParentModel.create(overview.getId()))
                .withCondition(BookEntryReadConditionModel.create().withEntry(possessDjinniID));
        var possessWeakShulker = this.add(new PossessWeakShulkerEntry(this).generate('J'));
        possessWeakShulker.withParent(BookEntryParentModel.create(overview.getId()))
                .withCondition(BookEntryReadConditionModel.create().withEntry(possessDjinniID));
        var possessBlaze = this.add(new PossessBlazeEntry(this).generate('Q'));
        possessBlaze.withParent(BookEntryParentModel.create(overview.getId()))
                .withCondition(BookEntryReadConditionModel.create().withEntry(possessDjinniID));
        var possessZombiePiglin = this.add(new PossessZombiePiglinEntry(this).generate('P'));
        possessZombiePiglin.withParent(BookEntryParentModel.create(overview.getId()))
                .withCondition(BookEntryReadConditionModel.create().withEntry(possessUnboundAfritID));
        var possessGuardian = this.add(new PossessGuardianEntry(this).generate('R'));
        possessGuardian.withParent(BookEntryParentModel.create(overview.getId()))
                .withCondition(BookEntryReadConditionModel.create().withEntry(possessUnboundAfritID));
        var possessWarden = this.add(new PossessWardenEntry(this).generate('M'));
        possessWarden.withParent(BookEntryParentModel.create(overview.getId()))
                .withCondition(BookEntryReadConditionModel.create().withEntry(possessAfritID));
        var possessElderGuardian = this.add(new PossessElderGuardianEntry(this).generate('L'));
        possessElderGuardian.withParent(BookEntryParentModel.create(overview.getId()))
                .withCondition(BookEntryReadConditionModel.create().withEntry(possessAfritID));
        var possessHoglin = this.add(new PossessHoglinEntry(this).generate('N'));
        possessHoglin.withParent(BookEntryParentModel.create(overview.getId()))
                .withCondition(BookEntryReadConditionModel.create().withEntry(possessAfritID));
        var possessShulker = this.add(new PossessShulkerEntry(this).generate('K'));
        possessShulker.withParent(BookEntryParentModel.create(overview.getId()))
                .withCondition(BookEntryReadConditionModel.create().withEntry(possessAfritID));
        var mercyGoat = this.add(new MercyGoatEntry(this).generate('C'));
        mercyGoat.withParent(BookEntryParentModel.create(overview.getId()))
                .withCondition(BookEntryReadConditionModel.create().withEntry(possessMaridID));

        var witherSkull = this.add(new WitherSkullEntry(this).generate('H'));
        witherSkull.withParent(BookEntryParentModel.create(overview.getId()))
                .withCondition(BookEntryReadConditionModel.create().withEntry(possessWildID));
        var hordeIllager = this.add(new HordeIllagerEntry(this).generate('V'));
        hordeIllager.withParent(BookEntryParentModel.create(overview.getId()))
                .withCondition(BookEntryReadConditionModel.create().withEntry(possessWildID));
        var hordeDesert = this.add(new HordeDesertEntry(this).generate('W'));
        hordeDesert.withParent(BookEntryParentModel.create(overview.getId()))
                .withCondition(BookEntryReadConditionModel.create().withEntry(possessWildID));
        var hordeDrowned = this.add(new HordeDrownedEntry(this).generate('X'));
        hordeDrowned.withParent(BookEntryParentModel.create(overview.getId()))
                .withCondition(BookEntryReadConditionModel.create().withEntry(possessWildID));
        var hordeCreeper = this.add(new HordeCreeperEntry(this).generate('Y'));
        hordeCreeper.withParent(BookEntryParentModel.create(overview.getId()))
                .withCondition(BookEntryReadConditionModel.create().withEntry(possessWildID));
        var hordeSilverfish = this.add(new HordeSilverfishEntry(this).generate('Z'));
        hordeSilverfish.withParent(BookEntryParentModel.create(overview.getId()))
                .withCondition(BookEntryReadConditionModel.create().withEntry(possessWildID));
        var possessWeakBreeze = this.add(new PossessWeakBreezeEntry(this).generate('S'));
        possessWeakBreeze.withParent(BookEntryParentModel.create(overview.getId()))
                .withCondition(BookEntryReadConditionModel.create().withEntry(possessWildID));
        var possessBreeze = this.add(new PossessBreezeEntry(this).generate('T'));
        possessBreeze.withParent(BookEntryParentModel.create(possessWeakBreeze.getId()))
                .withCondition(BookEntryReadConditionModel.create().withEntry(possessWeakBreeze.getId()));
        var possessStrongBreeze = this.add(new PossessStrongBreezeEntry(this).generate('U'));
        possessStrongBreeze.withParent(BookEntryParentModel.create(possessBreeze.getId()))
                .withCondition(BookEntryReadConditionModel.create().withEntry(possessBreeze.getId()));

        var possessUnboundParrot = this.add(new PossessUnboundParrotEntry(this).generate('p'));
        possessUnboundParrot.withParent(BookEntryParentModel.create(overview.getId()))
                .withCondition(BookEntryReadConditionModel.create().withEntry(possessFoliotID));
        var possessUnboundOtherworldBird = this.add(new PossessUnboundOtherworldBirdEntry(this).generate('d'));
        possessUnboundOtherworldBird.withParent(BookEntryParentModel.create(possessUnboundParrot.getId()))
                .withCondition(BookEntryReadConditionModel.create().withEntry(possessDjinniID));

        var possessRandomAnimal = this.add(new PossessRandomAnimalEntry(this).generate('a'));
        possessRandomAnimal.withParent(BookEntryParentModel.create(overview.getId()))
                .withCondition(BookEntryReadConditionModel.create().withEntry(possessFoliotID));
        var wildRandomAnimal = this.add(new WildRandomAnimalEntry(this).generate('b'));
        wildRandomAnimal.withParent(BookEntryParentModel.create(possessRandomAnimal.getId()))
                .withCondition(BookEntryReadConditionModel.create().withEntry(possessWildID));
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
