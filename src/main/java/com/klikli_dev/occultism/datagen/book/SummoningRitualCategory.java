package com.klikli_dev.occultism.datagen.book;

import com.klikli_dev.modonomicon.api.datagen.CategoryEntryMap;
import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookEntryModel;
import com.klikli_dev.modonomicon.api.datagen.book.BookEntryParentModel;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.condition.BookEntryReadConditionModel;
import com.klikli_dev.occultism.datagen.OccultismBookProvider;
import com.klikli_dev.occultism.datagen.book.pentacles.*;
import com.klikli_dev.occultism.datagen.book.summoning_rituals.*;

public class SummoningRitualCategory extends CategoryProvider {

    public static final String CATEGORY_ID = "summoning_rituals";

    public SummoningRitualCategory(OccultismBookProvider parent) {
        super(parent);
    }

    @Override
    protected String categoryName() {
        return "Summoning Rituals";
    }

    @Override
    protected BookIconModel categoryIcon() {
        return BookIconModel.create(this.modLoc("textures/gui/book/summoning.png"));
    }

    @Override
    public String categoryId() {
        return CATEGORY_ID;
    }

    @Override
    protected String[] generateEntryMap() {
        return new String[]{
                "______________________",
                "____________h_________",
                "_______j_c_d_b_k_l____", //Specialized works (farmer, lumber, storage, time, weather)
                "______________________",
                "__9_o_________________",
                "______________________",
                "_______1_5_i__e__a____", //Tiered workers per column (crusher, smelter, crystallizer)
                "______________________", //Traders and unbound spirits
                "_______2_6_í_f_g_m____",
                "______________________",
                "_______3_7_ì_p_n______",
                "______________________",
                "_______4_8_î__________"
        };
    }

    @Override
    protected void generateEntries() {
        //Pentacle parents
        String summonFoliotID = this.modId() + ":" + PentaclesCategory.CATEGORY_ID + "/" + SummonFoliotEntry.ENTRY_ID;
        String summonDjinniID = this.modId() + ":" + PentaclesCategory.CATEGORY_ID + "/" + SummonDjinniEntry.ENTRY_ID;
        String summonUnboundAfritID = this.modId() + ":" + PentaclesCategory.CATEGORY_ID + "/" + SummonUnboundAfritEntry.ENTRY_ID;
        String summonAfritID = this.modId() + ":" + PentaclesCategory.CATEGORY_ID + "/" + SummonAfritEntry.ENTRY_ID;
        String summonUnboundMaridID = this.modId() + ":" + PentaclesCategory.CATEGORY_ID + "/" + SummonUnboundMaridEntry.ENTRY_ID;
        String summonMaridID = this.modId() + ":" + PentaclesCategory.CATEGORY_ID + "/" + SummonMaridEntry.ENTRY_ID;

        var overview = this.add(new SummoningOverviewEntry(this).generate('o'));
        overview.withCondition(BookEntryReadConditionModel.create().withEntry(summonFoliotID));
        var returnToRituals = this.add(this.makeReturnToRitualsEntry(this.entryMap));
        returnToRituals.withParent(BookEntryParentModel.create(overview.getId()))
                .withCondition(BookEntryReadConditionModel.create().withEntry(summonFoliotID));

        var summonT1Crusher = this.add(new CrusherFoliotEntry(this).generate('1'));
        summonT1Crusher.withParent(BookEntryParentModel.create(overview.getId()))
                .withCondition(BookEntryReadConditionModel.create().withEntry(summonFoliotID));
        var summonT2Crusher = this.add(new CrusherDjinniEntry(this).generate('2'));
        summonT2Crusher.withParent(BookEntryParentModel.create(summonT1Crusher.getId()))
                .withCondition(BookEntryReadConditionModel.create().withEntry(summonDjinniID));
        var summonT3Crusher = this.add(new CrusherAfritEntry(this).generate('3'));
        summonT3Crusher.withParent(BookEntryParentModel.create(summonT2Crusher.getId()))
                .withCondition(BookEntryReadConditionModel.create().withEntry(summonAfritID));
        var summonT4Crusher = this.add(new CrusherMaridEntry(this).generate('4'));
        summonT4Crusher.withParent(BookEntryParentModel.create(summonT3Crusher.getId()))
                .withCondition(BookEntryReadConditionModel.create().withEntry(summonMaridID));

        var summonT1Smelter = this.add(new SmelterFoliotEntry(this).generate('5'));
        summonT1Smelter.withParent(BookEntryParentModel.create(overview.getId()))
                .withCondition(BookEntryReadConditionModel.create().withEntry(summonFoliotID));
        var summonT2Smelter = this.add(new SmelterDjinniEntry(this).generate('6'));
        summonT2Smelter.withParent(BookEntryParentModel.create(summonT1Smelter.getId()))
                .withCondition(BookEntryReadConditionModel.create().withEntry(summonDjinniID));
        var summonT3Smelter = this.add(new SmelterAfritEntry(this).generate('7'));
        summonT3Smelter.withParent(BookEntryParentModel.create(summonT2Smelter.getId()))
                .withCondition(BookEntryReadConditionModel.create().withEntry(summonAfritID));
        var summonT4Smelter = this.add(new SmelterMaridEntry(this).generate('8'));
        summonT4Smelter.withParent(BookEntryParentModel.create(summonT3Smelter.getId()))
                .withCondition(BookEntryReadConditionModel.create().withEntry(summonMaridID));

        var summonT1Crystallizer = this.add(new CrystallizerFoliotEntry(this).generate('i'));
        summonT1Crystallizer.withParent(BookEntryParentModel.create(overview.getId()))
                .withCondition(BookEntryReadConditionModel.create().withEntry(summonFoliotID));
        var summonT2Crystallizer = this.add(new CrystallizerDjinniEntry(this).generate('í'));
        summonT2Crystallizer.withParent(BookEntryParentModel.create(summonT1Crystallizer.getId()))
                .withCondition(BookEntryReadConditionModel.create().withEntry(summonDjinniID));
        var summonT3Crystallizer = this.add(new CrystallizerAfritEntry(this).generate('ì'));
        summonT3Crystallizer.withParent(BookEntryParentModel.create(summonT2Crystallizer.getId()))
                .withCondition(BookEntryReadConditionModel.create().withEntry(summonAfritID));
        var summonT4Crystallizer = this.add(new CrystallizerMaridEntry(this).generate('î'));
        summonT4Crystallizer.withParent(BookEntryParentModel.create(summonT3Crystallizer.getId()))
                .withCondition(BookEntryReadConditionModel.create().withEntry(summonMaridID));

        var summonLumberjack = this.add(new LumberjackEntry(this).generate('c'));
        summonLumberjack.withParent(BookEntryParentModel.create(overview.getId()))
                .withCondition(BookEntryReadConditionModel.create().withEntry(summonFoliotID));
        var summonFarmer = this.add(new FarmerEntry(this).generate('j'));
        summonFarmer.withParent(BookEntryParentModel.create(overview.getId()))
                .withCondition(BookEntryReadConditionModel.create().withEntry(summonFoliotID));

        var summonTransportItems = this.add(new TransporterEntry(this).generate('d'));
        summonTransportItems.withParent(BookEntryParentModel.create(overview.getId()))
                .withCondition(BookEntryReadConditionModel.create().withEntry(summonFoliotID));
        var summonCleaner = this.add(new CleanerEntry(this).generate('b'));
        summonCleaner.withParent(BookEntryParentModel.create(overview.getId()))
                .withCondition(BookEntryReadConditionModel.create().withEntry(summonFoliotID));
        var summonManageMachine = this.add(new ManageMachineEntry(this).generate('h'));
        summonManageMachine.withParent(BookEntryParentModel.create(overview.getId()))
                .withCondition(BookEntryReadConditionModel.create().withEntry(summonDjinniID));

        var tradeSpirits = this.add(new TraderSpiritsEntry(this).generate('e'));
        tradeSpirits.withParent(BookEntryParentModel.create(overview.getId()))
                .withCondition(BookEntryReadConditionModel.create().withEntry(summonFoliotID));
        var summonOtherworldSaplingTrader = this.add(new TraderSaplingEntry(this).generate('f'));
        summonOtherworldSaplingTrader.withParent(BookEntryParentModel.create(tradeSpirits.getId()).withLineReversed(true))
                .withCondition(BookEntryReadConditionModel.create().withEntry(summonFoliotID));
        var summonOtherstoneTrader = this.add(new TraderOtherstoneEntry(this).generate('g'));
        summonOtherstoneTrader.withParent(BookEntryParentModel.create(tradeSpirits.getId()).withLineReversed(true))
                .withCondition(BookEntryReadConditionModel.create().withEntry(summonFoliotID));
        var summonOtherrockTrader = this.add(new TraderOtherrockEntry(this).generate('n'));
        summonOtherrockTrader.withParent(BookEntryParentModel.create(tradeSpirits.getId()).withLineReversed(true))
                .withCondition(BookEntryReadConditionModel.create().withEntry(summonFoliotID));
        var summonGambler = this.add(new TraderGemsEntry(this).generate('p'));
        summonGambler.withParent(BookEntryParentModel.create(tradeSpirits.getId()).withLineReversed(true))
                .withCondition(BookEntryReadConditionModel.create().withEntry(summonDjinniID));

        var weatherMagic = this.add(new MagicWeatherEntry(this).generate('k'));
        weatherMagic.withParent(BookEntryParentModel.create(overview.getId()))
                .withCondition(BookEntryReadConditionModel.create().withEntry(summonDjinniID));
        var timeMagic = this.add(new MagicTimeEntry(this).generate('l'));
        timeMagic.withParent(BookEntryParentModel.create(overview.getId()))
                .withCondition(BookEntryReadConditionModel.create().withEntry(summonDjinniID));

        var afritEssence = this.add(new EssenceAfritEntry(this).generate('a'));
        afritEssence.withParent(BookEntryParentModel.create(overview.getId()))
                .withCondition(BookEntryReadConditionModel.create().withEntry(summonUnboundAfritID));
        var maridEssence = this.add(new EssenceMaridEntry(this).generate('m'));
        maridEssence.withParent(BookEntryParentModel.create(afritEssence.getId()))
                .withCondition(BookEntryReadConditionModel.create().withEntry(summonUnboundMaridID));

    }

    private BookEntryModel makeReturnToRitualsEntry(CategoryEntryMap entryMap) {
        this.context().entry("return_to_rituals");
        this.lang().add(this.context().entryName(), "Return to Rituals Category");

        return BookEntryModel.create(this.modLoc(this.context().categoryId() + "/" + this.context().entryId()), this.context().entryName())
                .withIcon(this.modLoc("textures/gui/book/robe.png"))
                .withCategoryToOpen(this.modLoc("rituals"))
                .withEntryBackground(1, 2)
                .withLocation(entryMap.get('9'));
    }

}
