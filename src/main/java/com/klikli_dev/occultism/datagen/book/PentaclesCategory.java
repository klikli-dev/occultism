package com.klikli_dev.occultism.datagen.book;

import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookEntryParentModel;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.condition.BookEntryReadConditionModel;
import com.klikli_dev.occultism.datagen.OccultismBookProvider;
import com.klikli_dev.occultism.datagen.book.pentacles.*;

public class PentaclesCategory extends CategoryProvider {
    public static final String CATEGORY_ID = "pentacles";

    public PentaclesCategory(OccultismBookProvider parent) {
        super(parent);
    }

    @Override
    protected String categoryName() {
        return "Pentacles";
    }

    @Override
    protected BookIconModel categoryIcon() {
        return BookIconModel.create(this.modLoc("textures/gui/book/pentacle.png"));
    }

    @Override
    public String categoryId() {
        return CATEGORY_ID;
    }

    @Override
    protected void generateEntries() {
        //Introduction
        var overviewEntry = this.add(new PentaclesOverviewEntry(this).generate());
        this.layout().entry(overviewEntry).at(-11, -3);

        var paraphernaliaEntry = this.add(new ParaphernaliaEntry(this).generate());
        paraphernaliaEntry.withParent(BookEntryParentModel.create(overviewEntry.getId()).withLineReversed(true))
                .withCondition(BookEntryReadConditionModel.create().withEntry(overviewEntry.getId()));
        this.layout().entry(paraphernaliaEntry).below(overviewEntry, 2);

        var didacticsEntry = this.add(new DidacticsEntry(this).generate());
        didacticsEntry.withParent(BookEntryParentModel.create(overviewEntry.getId()).withLineReversed(true))
                .withCondition(BookEntryReadConditionModel.create().withEntry(overviewEntry.getId()));
        this.layout().entry(didacticsEntry).rightOf(overviewEntry, 3).above(1);

        //Tier Foliot
        var whiteChalkEntry = this.add(new WhiteChalkEntry(this).generate());
        whiteChalkEntry.withParent(BookEntryParentModel.create(didacticsEntry.getId()).withLineReversed(true))
                .withCondition(BookEntryReadConditionModel.create().withEntry(didacticsEntry.getId()));
        this.layout().entry(whiteChalkEntry).rightOf(didacticsEntry, 3).above(2);

        var summonFoliot = this.add(new SummonFoliotEntry(this).generate());
        summonFoliot.withParent(BookEntryParentModel.create(didacticsEntry.getId()))
                .withCondition(BookEntryReadConditionModel.create().withEntry(whiteChalkEntry.getId()));
        this.layout().entry(summonFoliot).rightOf(didacticsEntry, 3);

        var lightGrayChalkEntry = this.add(new LightGrayChalkEntry(this).generate());
        lightGrayChalkEntry.withParent(BookEntryParentModel.create(whiteChalkEntry.getId()))
                .withCondition(BookEntryReadConditionModel.create().withEntry(summonFoliot.getId()));
        this.layout().entry(lightGrayChalkEntry).rightOf(whiteChalkEntry, 3);

        var yellowChalkEntry = this.add(new YellowChalkEntry(this).generate());
        yellowChalkEntry.withParents(BookEntryParentModel.create(didacticsEntry.getId()))
                .withCondition(BookEntryReadConditionModel.create().withEntry(summonFoliot.getId()));
        this.layout().entry(yellowChalkEntry).below(didacticsEntry, 2);

        var possessFoliot = this.add(new PossessFoliotEntry(this).generate());
        possessFoliot.withParent(BookEntryParentModel.create(yellowChalkEntry.getId()))
                .withCondition(BookEntryReadConditionModel.create().withEntry(yellowChalkEntry.getId()));
        this.layout().entry(possessFoliot).rightOf(yellowChalkEntry, 3);

        var purpleChalkEntry = this.add(new PurpleChalkEntry(this).generate());
        purpleChalkEntry.withParent(BookEntryParentModel.create(yellowChalkEntry.getId()))
                .withCondition(BookEntryReadConditionModel.create().withEntry(possessFoliot.getId()));
        this.layout().entry(purpleChalkEntry).below(yellowChalkEntry, 2);

        var craftFoliot = this.add(new CraftFoliotEntry(this).generate());
        craftFoliot.withParent(BookEntryParentModel.create(purpleChalkEntry.getId()))
                .withCondition(BookEntryReadConditionModel.create().withEntry(purpleChalkEntry.getId()));
        this.layout().entry(craftFoliot).rightOf(purpleChalkEntry, 3);

        var limeChalkEntry = this.add(new LimeChalkEntry(this).generate());
        limeChalkEntry.withParent(BookEntryParentModel.create(purpleChalkEntry.getId()).withLineReversed(true))
                .withCondition(BookEntryReadConditionModel.create().withEntry(craftFoliot.getId()));
        this.layout().entry(limeChalkEntry).rightOf(purpleChalkEntry, 6).below(2);

        var greenChalkEntry = this.add(new GreenChalkEntry(this).generate());
        greenChalkEntry.withParent(BookEntryParentModel.create(craftFoliot.getId()).withLineEnabled(false))
                .withCondition(BookEntryReadConditionModel.create().withEntry(craftFoliot.getId()));
        this.layout().entry(greenChalkEntry).rightOf(craftFoliot, 6).below(4);

        var resurrectSpirit = this.add(new ResurrectSpiritEntry(this).generate());
        resurrectSpirit.withParent(BookEntryParentModel.create(possessFoliot.getId()).withLineEnabled(false))
                .withCondition(BookEntryReadConditionModel.create().withEntry(possessFoliot.getId()));
        this.layout().entry(resurrectSpirit).below(possessFoliot, 7);

        //Tier Djinni
        var summonDjinni = this.add(new SummonDjinniEntry(this).generate());
        summonDjinni.withParent(BookEntryParentModel.create(summonFoliot.getId()))
                .withCondition(
                        this.condition().and(
                                this.condition().entryRead(lightGrayChalkEntry),
                                this.condition().entryRead(limeChalkEntry)
                        ));

        this.layout().entry(summonDjinni).rightOf(summonFoliot, 3);
        var lightBlueChalkEntry = this.add(new LightBlueChalkEntry(this).generate());
        lightBlueChalkEntry.withParent(BookEntryParentModel.create(summonDjinni.getId()).withLineEnabled(false))
                .withCondition(BookEntryReadConditionModel.create().withEntry(summonDjinni.getId()));

        this.layout().entry(lightBlueChalkEntry).rightOf(summonDjinni, 3).below(9);
        var possessDjinni = this.add(new PossessDjinniEntry(this).generate());
        possessDjinni.withParent(BookEntryParentModel.create(possessFoliot.getId()))
                .withCondition(
                        this.condition().and(
                                this.condition().entryRead(lightGrayChalkEntry),
                                this.condition().entryRead(limeChalkEntry)
                        ));

        this.layout().entry(possessDjinni).rightOf(possessFoliot, 3);
        var orangeChalkEntry = this.add(new OrangeChalkEntry(this).generate());
        orangeChalkEntry.withParent(BookEntryParentModel.create(limeChalkEntry.getId()))
                .withCondition(BookEntryReadConditionModel.create().withEntry(possessDjinni.getId()));

        this.layout().entry(orangeChalkEntry).rightOf(limeChalkEntry, 3);
        var craftDjinni = this.add(new CraftDjinniEntry(this).generate());
        craftDjinni.withParent(BookEntryParentModel.create(craftFoliot.getId()))
                .withCondition(
                        this.condition().and(
                                this.condition().entryRead(lightGrayChalkEntry),
                                this.condition().entryRead(limeChalkEntry)
                        ));

        this.layout().entry(craftDjinni).rightOf(craftFoliot, 3);
        var grayChalkEntry = this.add(new GrayChalkEntry(this).generate());
        grayChalkEntry.withParent(BookEntryParentModel.create(lightGrayChalkEntry.getId()))
                .withCondition(BookEntryReadConditionModel.create().withEntry(craftDjinni.getId()));

        //Tier Unbound Afrit
        this.layout().entry(grayChalkEntry).rightOf(lightGrayChalkEntry, 3);
        var summonUnboundAfrit = this.add(new SummonUnboundAfritEntry(this).generate());
        summonUnboundAfrit.withParent(BookEntryParentModel.create(summonDjinni.getId()))
                .withCondition(
                        this.condition().and(
                                this.condition().entryRead(grayChalkEntry),
                                this.condition().entryRead(orangeChalkEntry)
                        ));

        this.layout().entry(summonUnboundAfrit).rightOf(summonDjinni, 3);
        var redChalkEntry = this.add(new RedChalkEntry(this).generate());
        redChalkEntry.withParent(BookEntryParentModel.create(orangeChalkEntry.getId()))
                .withCondition(BookEntryReadConditionModel.create().withEntry(summonUnboundAfrit.getId()));

        this.layout().entry(redChalkEntry).rightOf(orangeChalkEntry, 3);
        var possessUnboundAfrit = this.add(new PossessUnboundAfritEntry(this).generate());
        possessUnboundAfrit.withParent(BookEntryParentModel.create(possessDjinni.getId()))
                .withCondition(
                        this.condition().and(
                                this.condition().entryRead(grayChalkEntry),
                                this.condition().entryRead(orangeChalkEntry)
                        ));

        this.layout().entry(possessUnboundAfrit).rightOf(possessDjinni, 3);
        var pinkChalkEntry = this.add(new PinkChalkEntry(this).generate());
        pinkChalkEntry.withParent(BookEntryParentModel.create(possessUnboundAfrit.getId()).withLineEnabled(false))
                .withCondition(BookEntryReadConditionModel.create().withEntry(possessUnboundAfrit.getId()));

        this.layout().entry(pinkChalkEntry).below(possessUnboundAfrit, 8);
        var contactWildSpirit = this.add(new ContactWildSpiritEntry(this).generate());
        contactWildSpirit
                .withParents(
                        BookEntryParentModel.create(pinkChalkEntry.getId()),
                        BookEntryParentModel.create(greenChalkEntry.getId()),
                        BookEntryParentModel.create(lightBlueChalkEntry.getId()))
                .withCondition(
                        this.condition().and(
                                this.condition().entryRead(pinkChalkEntry),
                                this.condition().entryRead(greenChalkEntry),
                                this.condition().entryRead(lightBlueChalkEntry)
                        ));

        //Tier Afrit
        this.layout().entry(contactWildSpirit).rightOf(pinkChalkEntry, 3).above(1);
        var summonAfrit = this.add(new SummonAfritEntry(this).generate());
        summonAfrit.withParent(BookEntryParentModel.create(summonUnboundAfrit.getId()))
                .withCondition(this.condition().entryRead(redChalkEntry));

        this.layout().entry(summonAfrit).rightOf(summonUnboundAfrit, 3);
        var possessAfrit = this.add(new PossessAfritEntry(this).generate());
        possessAfrit.withParent(BookEntryParentModel.create(possessUnboundAfrit.getId()))
                .withCondition(this.condition().entryRead(redChalkEntry));

        this.layout().entry(possessAfrit).rightOf(possessUnboundAfrit, 3);
        var craftAfrit = this.add(new CraftAfritEntry(this).generate());
        craftAfrit.withParent(BookEntryParentModel.create(craftDjinni.getId()))
                .withCondition(this.condition().entryRead(redChalkEntry));

        this.layout().entry(craftAfrit).rightOf(craftDjinni, 6);
        var blackChalkEntry = this.add(new BlackChalkEntry(this).generate());
        blackChalkEntry.withParent(BookEntryParentModel.create(grayChalkEntry.getId()))
                .withCondition(BookEntryReadConditionModel.create().withEntry(craftAfrit.getId()));

        //Tier Marid
        this.layout().entry(blackChalkEntry).rightOf(grayChalkEntry, 6);
        var summonUnboundMarid = this.add(new SummonUnboundMaridEntry(this).generate());
        summonUnboundMarid.withParent(BookEntryParentModel.create(summonAfrit.getId()))
                .withCondition(this.condition().entryRead(blackChalkEntry));

        this.layout().entry(summonUnboundMarid).rightOf(summonAfrit, 3);
        var blueChalkEntry = this.add(new BlueChalkEntry(this).generate());
        blueChalkEntry.withParent(BookEntryParentModel.create(redChalkEntry.getId()))
                .withCondition(BookEntryReadConditionModel.create().withEntry(summonUnboundMarid.getId()));

        this.layout().entry(blueChalkEntry).rightOf(redChalkEntry, 6);
        var summonMarid = this.add(new SummonMaridEntry(this).generate());
        summonMarid.withParent(BookEntryParentModel.create(summonUnboundMarid.getId()))
                .withCondition(this.condition().entryRead(blueChalkEntry));

        this.layout().entry(summonMarid).rightOf(summonUnboundMarid, 3);
        var cyanChalkEntry = this.add(new CyanChalkEntry(this).generate());
        cyanChalkEntry.withParent(BookEntryParentModel.create(summonMarid.getId()).withLineEnabled(false))
                .withCondition(BookEntryReadConditionModel.create().withEntry(summonMarid.getId()));

        this.layout().entry(cyanChalkEntry).below(summonMarid, 8);
        var possessMarid = this.add(new PossessMaridEntry(this).generate());
        possessMarid.withParent(BookEntryParentModel.create(possessAfrit.getId()))
                .withCondition(this.condition().entryRead(blueChalkEntry));

        this.layout().entry(possessMarid).rightOf(possessAfrit, 6);
        var brownChalkEntry = this.add(new BrownChalkEntry(this).generate());
        brownChalkEntry.withParent(BookEntryParentModel.create(possessMarid.getId()).withLineEnabled(false))
                .withCondition(BookEntryReadConditionModel.create().withEntry(possessMarid.getId()));

        this.layout().entry(brownChalkEntry).below(possessMarid, 7);
        var craftMarid = this.add(new CraftMaridEntry(this).generate());
        craftMarid.withParent(BookEntryParentModel.create(craftAfrit.getId()))
                .withCondition(this.condition().entryRead(blueChalkEntry));

        this.layout().entry(craftMarid).rightOf(craftAfrit, 6);
        var magentaChalkEntry = this.add(new MagentaChalkEntry(this).generate());
        magentaChalkEntry.withParent(BookEntryParentModel.create(craftMarid.getId()).withLineEnabled(false))
                .withCondition(BookEntryReadConditionModel.create().withEntry(craftMarid.getId()));

        this.layout().entry(magentaChalkEntry).below(craftMarid, 6);
        var contactEldritch = this.add(new ContactEldritchSpiritEntry(this).generate());
        contactEldritch
                .withParents(
                        BookEntryParentModel.create(magentaChalkEntry.getId()),
                        BookEntryParentModel.create(brownChalkEntry.getId()),
                        BookEntryParentModel.create(cyanChalkEntry.getId()),
                        BookEntryParentModel.create(contactWildSpirit.getId()))
                .withCondition(this.condition().and(
                        this.condition().entryRead(magentaChalkEntry),
                        this.condition().entryRead(brownChalkEntry),
                        this.condition().entryRead(cyanChalkEntry),
                        this.condition().entryRead(contactWildSpirit)
                ));
        this.layout().entry(contactEldritch).leftOf(magentaChalkEntry, 3).above(1);
    }
}
