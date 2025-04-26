package com.klikli_dev.occultism.datagen.book;

import com.klikli_dev.modonomicon.api.datagen.book.BookEntryParentModel;
import com.klikli_dev.modonomicon.api.datagen.book.condition.BookAdvancementConditionModel;
import com.klikli_dev.modonomicon.api.datagen.book.condition.BookAndConditionModel;
import com.klikli_dev.modonomicon.api.datagen.book.condition.BookEntryReadConditionModel;
import com.klikli_dev.occultism.datagen.OccultismBookProvider;
import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
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
    public String[] generateEntryMap() {
        return new String[]{
                "_______W__S__G_____K_____",
                "_________________________",
                "____t__a__b__c__d__u__e__",
                "_r_______________________",
                "____Y__f__g__h__i_____j__",
                "_s_______________________",
                "____X__l__m_____n_____o__",
                "_________________________",
                "__________L__O__R_____U__",
                "_________________________",
                "_____________E________C__",
                "_______k_____A__p__q__B__",
                "_____________P________M__"
        };
    }

    @Override
    protected void generateEntries(){
        //Introduction
        var overviewEntry = this.add(new PentaclesOverviewEntry(this).generate( 'r'));

        var paraphernaliaEntry = this.add(new ParaphernaliaEntry(this).generate('s'));
        paraphernaliaEntry.withParent(BookEntryParentModel.create(overviewEntry.getId()).withLineReversed(true));

        var didacticsEntry = this.add(new DidacticsEntry(this).generate('t'));
        didacticsEntry.withParent(BookEntryParentModel.create(overviewEntry.getId()).withLineReversed(true));

        //Tier Foliot
        var whiteChalkEntry = this.add(new WhiteChalkEntry(this).generate('W'));
        whiteChalkEntry.withParent(BookEntryParentModel.create(didacticsEntry.getId()).withLineReversed(true))
                .withCondition(BookEntryReadConditionModel.create().withEntry(didacticsEntry.getId()));

        var summonFoliot = this.add(new SummonFoliotEntry(this).generate('a'));
        summonFoliot.withParent(BookEntryParentModel.create(didacticsEntry.getId()))
                .withCondition(BookEntryReadConditionModel.create().withEntry(whiteChalkEntry.getId()));
        //TODO: enable advancement condition once modonomicon supports skipping them
//                .withCondition(BookAdvancementConditionModel.create().withAdvancementId("occultism:chalks/white"));

        var lightGrayChalkEntry = this.add(new LightGrayChalkEntry(this).generate('S'));
        lightGrayChalkEntry.withParent(BookEntryParentModel.create(whiteChalkEntry.getId()))
                .withCondition(BookEntryReadConditionModel.create().withEntry(summonFoliot.getId()));

        var yellowChalkEntry = this.add(new YellowChalkEntry(this).generate('Y'));
        yellowChalkEntry
                .withParents(
                        BookEntryParentModel.create(didacticsEntry.getId()),
                        BookEntryParentModel.create(whiteChalkEntry.getId()).withLineEnabled(false))
                .withCondition(BookEntryReadConditionModel.create().withEntry(summonFoliot.getId()));

        var possessFoliot = this.add(new PossessFoliotEntry(this).generate('f'));
        possessFoliot.withParent(BookEntryParentModel.create(yellowChalkEntry.getId()))
                .withCondition(BookEntryReadConditionModel.create().withEntry(yellowChalkEntry.getId()));
                //TODO: enable advancement condition once modonomicon supports skipping them
//                .withCondition(BookAdvancementConditionModel.create().withAdvancementId("occultism:chalks/yellow"));

        var purpleChalkEntry = this.add(new PurpleChalkEntry(this).generate('X'));
        purpleChalkEntry.withParent(BookEntryParentModel.create(yellowChalkEntry.getId()))
                .withCondition(BookEntryReadConditionModel.create().withEntry(possessFoliot.getId()));

        var craftFoliot = this.add(new CraftFoliotEntry(this).generate('l'));
        craftFoliot.withParent(BookEntryParentModel.create(purpleChalkEntry.getId()))
                .withCondition(BookEntryReadConditionModel.create().withEntry(purpleChalkEntry.getId()));
        //TODO: enable advancement condition once modonomicon supports skipping them
//                .withCondition(BookAdvancementConditionModel.create().withAdvancementId("occultism:chalks/purple"));

        var limeChalkEntry = this.add(new LimeChalkEntry(this).generate('L'));
        limeChalkEntry.withParent(BookEntryParentModel.create(purpleChalkEntry.getId()).withLineReversed(true))
                .withCondition(BookEntryReadConditionModel.create().withEntry(craftFoliot.getId()));

        var greenChalkEntry = this.add(new GreenChalkEntry(this).generate('E'));
        greenChalkEntry.withParent(BookEntryParentModel.create(craftFoliot.getId()).withLineEnabled(false));

        var resurrectSpirit = this.add(new ResurrectSpiritEntry(this).generate('k'));
        resurrectSpirit
                .withParent(BookEntryParentModel.create(possessFoliot.getId()).withLineEnabled(false))
                .withCondition(BookEntryReadConditionModel.create().withEntry(possessFoliot.getId()));

        //Tier Djinni
        var summonDjinni = this.add(new SummonDjinniEntry(this).generate('b'));
        summonDjinni.withParent(BookEntryParentModel.create(summonFoliot.getId()))
                .withCondition(
                        this.condition().and(
                                this.condition().entryRead(lightGrayChalkEntry),
                                this.condition().entryRead(limeChalkEntry)
                        ));
        //TODO: enable advancement condition once modonomicon supports skipping them
//                        BookAndConditionModel.create().withChildren(
//                        BookAdvancementConditionModel.create().withAdvancementId("occultism:chalks/light_gray"),
//                        BookAdvancementConditionModel.create().withAdvancementId("occultism:chalks/lime"))
//                    );

        var lightBlueChalkEntry = this.add(new LightBlueChalkEntry(this).generate('A'));
        lightBlueChalkEntry.withParent(BookEntryParentModel.create(summonDjinni.getId()).withLineEnabled(false))
                .withCondition(BookEntryReadConditionModel.create().withEntry(summonDjinni.getId()));

        var possessDjinni = this.add(new PossessDjinniEntry(this).generate('g'));
        possessDjinni.withParent(BookEntryParentModel.create(possessFoliot.getId()))
                .withCondition(
                        this.condition().and(
                                this.condition().entryRead(lightGrayChalkEntry),
                                this.condition().entryRead(limeChalkEntry)
                        ));
        //TODO: enable advancement condition once modonomicon supports skipping them
//                .withCondition(BookAndConditionModel.create().withChildren(
//                        BookAdvancementConditionModel.create().withAdvancementId("occultism:chalks/light_gray"),
//                        BookAdvancementConditionModel.create().withAdvancementId("occultism:chalks/lime")));

        var orangeChalkEntry = this.add(new OrangeChalkEntry(this).generate('O'));
        orangeChalkEntry.withParent(BookEntryParentModel.create(limeChalkEntry.getId()))
                .withCondition(BookEntryReadConditionModel.create().withEntry(possessDjinni.getId()));

        var craftDjinni = this.add(new CraftDjinniEntry(this).generate('m'));
        craftDjinni.withParent(BookEntryParentModel.create(craftFoliot.getId()))
                .withCondition(
                        this.condition().and(
                                this.condition().entryRead(lightGrayChalkEntry),
                                this.condition().entryRead(limeChalkEntry)
                        ));
        //TODO: enable advancement condition once modonomicon supports skipping them
//                .withCondition(BookAndConditionModel.create().withChildren(
//                        BookAdvancementConditionModel.create().withAdvancementId("occultism:chalks/light_gray"),
//                        BookAdvancementConditionModel.create().withAdvancementId("occultism:chalks/lime")));

        var grayChalkEntry = this.add(new GrayChalkEntry(this).generate('G'));
        grayChalkEntry.withParent(BookEntryParentModel.create(lightGrayChalkEntry.getId()))
                .withCondition(BookEntryReadConditionModel.create().withEntry(craftDjinni.getId()));

        //Tier Unbound Afrit
        var summonUnboundAfrit = this.add(new SummonUnboundAfritEntry(this).generate('c'));
        summonUnboundAfrit.withParent(BookEntryParentModel.create(summonDjinni.getId()))
                .withCondition(
                        this.condition().and(
                                this.condition().entryRead(grayChalkEntry),
                                this.condition().entryRead(orangeChalkEntry)
                        ));
        //TODO: enable advancement condition once modonomicon supports skipping them
//                .withCondition(BookAndConditionModel.create().withChildren(
//                        BookAdvancementConditionModel.create().withAdvancementId("occultism:chalks/gray"),
//                        BookAdvancementConditionModel.create().withAdvancementId("occultism:chalks/orange")));

        var redChalkEntry = this.add(new RedChalkEntry(this).generate('R'));
        redChalkEntry.withParent(BookEntryParentModel.create(orangeChalkEntry.getId()))
                .withCondition(BookEntryReadConditionModel.create().withEntry(summonUnboundAfrit.getId()));

        var possessUnboundAfrit = this.add(new PossessUnboundAfritEntry(this).generate('h'));
        possessUnboundAfrit.withParent(BookEntryParentModel.create(possessDjinni.getId()))
                .withCondition(
                        this.condition().and(
                                this.condition().entryRead(grayChalkEntry),
                                this.condition().entryRead(orangeChalkEntry)
                        ));
        //TODO: enable advancement condition once modonomicon supports skipping them
//                .withCondition(BookAndConditionModel.create().withChildren(
//                        BookAdvancementConditionModel.create().withAdvancementId("occultism:chalks/gray"),
//                        BookAdvancementConditionModel.create().withAdvancementId("occultism:chalks/orange")));

        var pinkChalkEntry = this.add(new PinkChalkEntry(this).generate('P'));
        pinkChalkEntry.withParent(BookEntryParentModel.create(possessUnboundAfrit.getId()).withLineEnabled(false));

        var contactWildSpirit = this.add(new ContactWildSpiritEntry(this).generate('p'));
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
        //TODO: enable advancement condition once modonomicon supports skipping them

//                .withCondition(BookAndConditionModel.create().withChildren(
//                        BookAdvancementConditionModel.create().withAdvancementId("occultism:chalks/pink"),
//                        BookAdvancementConditionModel.create().withAdvancementId("occultism:chalks/green"),
//                        BookAdvancementConditionModel.create().withAdvancementId("occultism:chalks/light_blue")));

        //Tier Afrit
        var summonAfrit = this.add(new SummonAfritEntry(this).generate('d'));
        summonAfrit.withParent(BookEntryParentModel.create(summonUnboundAfrit.getId()))
                .withCondition(this.condition().entryRead(redChalkEntry));
        //TODO: enable advancement condition once modonomicon supports skipping them
//                .withCondition(BookAdvancementConditionModel.create().withAdvancementId("occultism:chalks/red"));

        var possessAfrit = this.add(new PossessAfritEntry(this).generate('i'));
        possessAfrit.withParent(BookEntryParentModel.create(possessUnboundAfrit.getId()))
                .withCondition(this.condition().entryRead(redChalkEntry));
        //TODO: enable advancement condition once modonomicon supports skipping them
//                .withCondition(BookAdvancementConditionModel.create().withAdvancementId("occultism:chalks/red"));

        var craftAfrit = this.add(new CraftAfritEntry(this).generate('n'));
        craftAfrit.withParent(BookEntryParentModel.create(craftDjinni.getId()))
                .withCondition(this.condition().entryRead(redChalkEntry));
        //TODO: enable advancement condition once modonomicon supports skipping them
//                .withCondition(BookAdvancementConditionModel.create().withAdvancementId("occultism:chalks/red"));

        var blackChalkEntry = this.add(new BlackChalkEntry(this).generate('K'));
        blackChalkEntry.withParent(BookEntryParentModel.create(grayChalkEntry.getId()))
                .withCondition(BookEntryReadConditionModel.create().withEntry(craftAfrit.getId()));

        //Tier Marid
        var summonUnboundMarid = this.add(new SummonUnboundMaridEntry(this).generate('u'));
        summonUnboundMarid.withParent(BookEntryParentModel.create(summonAfrit.getId()))
                .withCondition(this.condition().entryRead(blackChalkEntry));
        //TODO: enable advancement condition once modonomicon supports skipping them
//                .withCondition(BookAdvancementConditionModel.create().withAdvancementId("occultism:chalks/black"));

        var blueChalkEntry = this.add(new BlueChalkEntry(this).generate('U'));
        blueChalkEntry.withParent(BookEntryParentModel.create(redChalkEntry.getId()))
                .withCondition(BookEntryReadConditionModel.create().withEntry(summonUnboundMarid.getId()));

        var summonMarid = this.add(new SummonMaridEntry(this).generate('e'));
        summonMarid.withParent(BookEntryParentModel.create(summonUnboundMarid.getId()))
                .withCondition(this.condition().entryRead(blueChalkEntry));
        //TODO: enable advancement condition once modonomicon supports skipping them
//                .withCondition(BookAdvancementConditionModel.create().withAdvancementId("occultism:chalks/blue"));

        var cyanChalkEntry = this.add(new CyanChalkEntry(this).generate('C'));
        cyanChalkEntry.withParent(BookEntryParentModel.create(summonMarid.getId()).withLineEnabled(false));

        var possessMarid = this.add(new PossessMaridEntry(this).generate('j'));
        possessMarid.withParent(BookEntryParentModel.create(possessAfrit.getId()))
                .withCondition(this.condition().entryRead(blueChalkEntry));
        //TODO: enable advancement condition once modonomicon supports skipping them
//                .withCondition(BookAdvancementConditionModel.create().withAdvancementId("occultism:chalks/blue"));

        var brownChalkEntry = this.add(new BrownChalkEntry(this).generate('B'));
        brownChalkEntry.withParent(BookEntryParentModel.create(possessMarid.getId()).withLineEnabled(false));

        var craftMarid = this.add(new CraftMaridEntry(this).generate('o'));
        craftMarid.withParent(BookEntryParentModel.create(craftAfrit.getId()))
                .withCondition(this.condition().entryRead(blueChalkEntry));
        //TODO: enable advancement condition once modonomicon supports skipping them
//                .withCondition(BookAdvancementConditionModel.create().withAdvancementId("occultism:chalks/blue"));

        var magentaChalkEntry = this.add(new MagentaChalkEntry(this).generate('M'));
        magentaChalkEntry.withParent(BookEntryParentModel.create(craftMarid.getId()).withLineEnabled(false));

        var contactEldritch = this.add(new ContactEldritchSpiritEntry(this).generate('q'));
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
        //TODO: enable advancement condition once modonomicon supports skipping them
//                .withCondition(BookAndConditionModel.create().withChildren(
//                        BookAdvancementConditionModel.create().withAdvancementId("occultism:chalks/magenta"),
//                        BookAdvancementConditionModel.create().withAdvancementId("occultism:chalks/brown"),
//                        BookAdvancementConditionModel.create().withAdvancementId("occultism:chalks/cyan")));
    }
}
