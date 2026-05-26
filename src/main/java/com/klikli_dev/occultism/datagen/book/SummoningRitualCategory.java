package com.klikli_dev.occultism.datagen.book;

import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
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
    protected void generateEntries() {
        //Pentacle parents
        String summonFoliotID = this.modId() + ":" + PentaclesCategory.CATEGORY_ID + "/" + SummonFoliotEntry.ENTRY_ID;
        String summonDjinniID = this.modId() + ":" + PentaclesCategory.CATEGORY_ID + "/" + SummonDjinniEntry.ENTRY_ID;
        String summonUnboundAfritID = this.modId() + ":" + PentaclesCategory.CATEGORY_ID + "/" + SummonUnboundAfritEntry.ENTRY_ID;
        String summonAfritID = this.modId() + ":" + PentaclesCategory.CATEGORY_ID + "/" + SummonAfritEntry.ENTRY_ID;
        String summonUnboundMaridID = this.modId() + ":" + PentaclesCategory.CATEGORY_ID + "/" + SummonUnboundMaridEntry.ENTRY_ID;
        String summonMaridID = this.modId() + ":" + PentaclesCategory.CATEGORY_ID + "/" + SummonMaridEntry.ENTRY_ID;

        var overview = this.add(new SummoningOverviewEntry(this).generate());
        overview.withCondition(BookEntryReadConditionModel.create().withEntry(summonFoliotID));
        this.layout().entry(overview).at(-7, -3);
        var returnToRituals = this.add(new ReturnToRitualsEntry(this).generate());
        returnToRituals.withCategoryToOpen(this.modLoc("rituals"));
        returnToRituals.withParent(BookEntryParentModel.create(overview.getId()))
                .withCondition(BookEntryReadConditionModel.create().withEntry(summonFoliotID));
        this.layout().entry(returnToRituals).leftOf(overview, 2);

        var summonT1Crusher = this.add(new CrusherFoliotEntry(this).generate());
        summonT1Crusher.withParent(BookEntryParentModel.create(overview.getId()))
                .withCondition(BookEntryReadConditionModel.create().withEntry(summonFoliotID));
        this.layout().entry(summonT1Crusher).rightOf(overview, 3).below(2);
        var summonT2Crusher = this.add(new CrusherDjinniEntry(this).generate());
        summonT2Crusher.withParent(BookEntryParentModel.create(summonT1Crusher.getId()))
                .withCondition(BookEntryReadConditionModel.create().withEntry(summonDjinniID));
        this.layout().entry(summonT2Crusher).below(summonT1Crusher, 2);
        var summonT3Crusher = this.add(new CrusherAfritEntry(this).generate());
        summonT3Crusher.withParent(BookEntryParentModel.create(summonT2Crusher.getId()))
                .withCondition(BookEntryReadConditionModel.create().withEntry(summonAfritID));
        this.layout().entry(summonT3Crusher).below(summonT2Crusher, 2);
        var summonT4Crusher = this.add(new CrusherMaridEntry(this).generate());
        summonT4Crusher.withParent(BookEntryParentModel.create(summonT3Crusher.getId()))
                .withCondition(BookEntryReadConditionModel.create().withEntry(summonMaridID));
        this.layout().entry(summonT4Crusher).below(summonT3Crusher, 2);

        var summonT1Smelter = this.add(new SmelterFoliotEntry(this).generate());
        summonT1Smelter.withParent(BookEntryParentModel.create(overview.getId()))
                .withCondition(BookEntryReadConditionModel.create().withEntry(summonFoliotID));
        this.layout().entry(summonT1Smelter).rightOf(overview, 5).below(2);
        var summonT2Smelter = this.add(new SmelterDjinniEntry(this).generate());
        summonT2Smelter.withParent(BookEntryParentModel.create(summonT1Smelter.getId()))
                .withCondition(BookEntryReadConditionModel.create().withEntry(summonDjinniID));
        this.layout().entry(summonT2Smelter).below(summonT1Smelter, 2);
        var summonT3Smelter = this.add(new SmelterAfritEntry(this).generate());
        summonT3Smelter.withParent(BookEntryParentModel.create(summonT2Smelter.getId()))
                .withCondition(BookEntryReadConditionModel.create().withEntry(summonAfritID));
        this.layout().entry(summonT3Smelter).below(summonT2Smelter, 2);
        var summonT4Smelter = this.add(new SmelterMaridEntry(this).generate());
        summonT4Smelter.withParent(BookEntryParentModel.create(summonT3Smelter.getId()))
                .withCondition(BookEntryReadConditionModel.create().withEntry(summonMaridID));
        this.layout().entry(summonT4Smelter).below(summonT3Smelter, 2);

        var summonT1Crystallizer = this.add(new CrystallizerFoliotEntry(this).generate());
        summonT1Crystallizer.withParent(BookEntryParentModel.create(overview.getId()))
                .withCondition(BookEntryReadConditionModel.create().withEntry(summonFoliotID));
        this.layout().entry(summonT1Crystallizer).rightOf(overview, 7).below(2);
        var summonT2Crystallizer = this.add(new CrystallizerDjinniEntry(this).generate());
        summonT2Crystallizer.withParent(BookEntryParentModel.create(summonT1Crystallizer.getId()))
                .withCondition(BookEntryReadConditionModel.create().withEntry(summonDjinniID));
        this.layout().entry(summonT2Crystallizer).below(summonT1Crystallizer, 2);
        var summonT3Crystallizer = this.add(new CrystallizerAfritEntry(this).generate());
        summonT3Crystallizer.withParent(BookEntryParentModel.create(summonT2Crystallizer.getId()))
                .withCondition(BookEntryReadConditionModel.create().withEntry(summonAfritID));
        this.layout().entry(summonT3Crystallizer).below(summonT2Crystallizer, 2);
        var summonT4Crystallizer = this.add(new CrystallizerMaridEntry(this).generate());
        summonT4Crystallizer.withParent(BookEntryParentModel.create(summonT3Crystallizer.getId()))
                .withCondition(BookEntryReadConditionModel.create().withEntry(summonMaridID));
        this.layout().entry(summonT4Crystallizer).below(summonT3Crystallizer, 2);

        var summonLumberjack = this.add(new LumberjackEntry(this).generate());
        summonLumberjack.withParent(BookEntryParentModel.create(overview.getId()))
                .withCondition(BookEntryReadConditionModel.create().withEntry(summonFoliotID));
        this.layout().entry(summonLumberjack).rightOf(overview, 5).above(2);
        var summonFarmer = this.add(new FarmerEntry(this).generate());
        summonFarmer.withParent(BookEntryParentModel.create(overview.getId()))
                .withCondition(BookEntryReadConditionModel.create().withEntry(summonFoliotID));
        this.layout().entry(summonFarmer).rightOf(overview, 3).above(2);

        var summonTransportItems = this.add(new TransporterEntry(this).generate());
        summonTransportItems.withParent(BookEntryParentModel.create(overview.getId()))
                .withCondition(BookEntryReadConditionModel.create().withEntry(summonFoliotID));
        this.layout().entry(summonTransportItems).rightOf(overview, 7).above(2);
        var transporterFilters = this.add(new TransporterFiltersEntry(this).generate());
        transporterFilters.withParent(BookEntryParentModel.create(summonTransportItems.getId()).withLineReversed(true))
                .withCondition(BookEntryReadConditionModel.create().withEntry(summonFoliotID));
        this.layout().entry(transporterFilters).above(summonTransportItems, 2);
        var summonCleaner = this.add(new CleanerEntry(this).generate());
        summonCleaner.withParent(BookEntryParentModel.create(overview.getId()))
                .withCondition(BookEntryReadConditionModel.create().withEntry(summonFoliotID));
        this.layout().entry(summonCleaner).rightOf(overview, 9).above(2);
        var summonManageMachine = this.add(new ManageMachineEntry(this).generate());
        summonManageMachine.withParent(BookEntryParentModel.create(overview.getId()))
                .withCondition(BookEntryReadConditionModel.create().withEntry(summonDjinniID));
        this.layout().entry(summonManageMachine).rightOf(overview, 8).above(3);

        var tradeSpirits = this.add(new TraderSpiritsEntry(this).generate());
        tradeSpirits.withParent(BookEntryParentModel.create(overview.getId()))
                .withCondition(BookEntryReadConditionModel.create().withEntry(summonFoliotID));
        this.layout().entry(tradeSpirits).rightOf(overview, 10).below(2);
        var summonOtherworldSaplingTrader = this.add(new TraderSaplingEntry(this).generate());
        summonOtherworldSaplingTrader.withParent(BookEntryParentModel.create(tradeSpirits.getId()).withLineReversed(true))
                .withCondition(BookEntryReadConditionModel.create().withEntry(summonFoliotID));
        this.layout().entry(summonOtherworldSaplingTrader).leftOf(tradeSpirits, 1).below(2);
        var summonOtherstoneTrader = this.add(new TraderOtherstoneEntry(this).generate());
        summonOtherstoneTrader.withParent(BookEntryParentModel.create(tradeSpirits.getId()).withLineReversed(true))
                .withCondition(BookEntryReadConditionModel.create().withEntry(summonFoliotID));
        this.layout().entry(summonOtherstoneTrader).rightOf(tradeSpirits, 1).below(2);
        var summonOtherrockTrader = this.add(new TraderOtherrockEntry(this).generate());
        summonOtherrockTrader.withParent(BookEntryParentModel.create(tradeSpirits.getId()).withLineReversed(true))
                .withCondition(BookEntryReadConditionModel.create().withEntry(summonFoliotID));
        this.layout().entry(summonOtherrockTrader).rightOf(tradeSpirits, 1).below(4);
        var summonGambler = this.add(new TraderGemsEntry(this).generate());
        summonGambler.withParent(BookEntryParentModel.create(tradeSpirits.getId()).withLineReversed(true))
                .withCondition(BookEntryReadConditionModel.create().withEntry(summonDjinniID));
        this.layout().entry(summonGambler).leftOf(tradeSpirits, 1).below(4);
        var summonWondering = this.add(new TraderWonderingEntry(this).generate());
        summonWondering.withParent(BookEntryParentModel.create(tradeSpirits.getId()))
                .withCondition(BookEntryReadConditionModel.create().withEntry(summonDjinniID));
        this.layout().entry(summonWondering).below(tradeSpirits, 6);

        var weatherMagic = this.add(new MagicWeatherEntry(this).generate());
        weatherMagic.withParent(BookEntryParentModel.create(overview.getId()))
                .withCondition(BookEntryReadConditionModel.create().withEntry(summonDjinniID));
        this.layout().entry(weatherMagic).rightOf(overview, 11).above(2);
        var timeMagic = this.add(new MagicTimeEntry(this).generate());
        timeMagic.withParent(BookEntryParentModel.create(overview.getId()))
                .withCondition(BookEntryReadConditionModel.create().withEntry(summonDjinniID));
        this.layout().entry(timeMagic).rightOf(overview, 13).above(2);

        var afritEssence = this.add(new EssenceAfritEntry(this).generate());
        afritEssence.withParent(BookEntryParentModel.create(overview.getId()))
                .withCondition(BookEntryReadConditionModel.create().withEntry(summonUnboundAfritID));
        this.layout().entry(afritEssence).rightOf(overview, 13).below(2);
        var maridEssence = this.add(new EssenceMaridEntry(this).generate());
        maridEssence.withParent(BookEntryParentModel.create(afritEssence.getId()))
                .withCondition(BookEntryReadConditionModel.create().withEntry(summonUnboundMaridID));
        this.layout().entry(maridEssence).below(afritEssence, 2);

    }

}
