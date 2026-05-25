package com.klikli_dev.occultism.datagen.book;

import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.ModonomiconProviderBase;
import com.klikli_dev.modonomicon.api.datagen.book.BookEntryParentModel;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.condition.BookAndConditionModel;
import com.klikli_dev.modonomicon.api.datagen.book.condition.BookEntryReadConditionModel;
import com.klikli_dev.modonomicon.api.datagen.book.condition.BookModLoadedConditionModel;
import com.klikli_dev.occultism.datagen.book.getting_started.*;
import com.klikli_dev.occultism.registry.OccultismItems;

public class GettingStartedCategory extends CategoryProvider {

    public static final String CATEGORY_ID = "getting_started";

    public GettingStartedCategory(ModonomiconProviderBase parent) {
        super(parent);
    }

    @Override
    protected String[] generateEntryMap() {
        return new String[]{
                "__________________________________",
                "__________________________________",
                "__________________________________",
                "__________________________P_D_____",
                "__________________________________",
                "______ŕ___t___B_____l_g_I_O_M_____",
                "__________________________________",
                "______i___r___ç_b_______s_________",
                "__________________________________",
                "______d___f_c_____R___a___ĝ_______",
                "__________________________________",
                "______e_h_____ạ_______m___________",
                "__________________________________",
                "______________Á_É_C_p_S___w_x_y_z_"
        };
    }

    @Override
    protected void generateEntries() {
        var introEntry = this.add(new IntroEntry(this).generate('i'));

        var demonsDreamEntry = this.add(new DemonsDreamEntry(this).generate('d'));
        demonsDreamEntry.withParent(BookEntryParentModel.create(introEntry.getId()));

        var spiritFireEntry = this.add(new SpiritFireEntry(this).generate('f'));
        spiritFireEntry.withParent(BookEntryParentModel.create(demonsDreamEntry.getId()));

        var healingSpiritsEntry = this.add(new HealingSpiritsEntry(this).generate('h'));
        healingSpiritsEntry.withParent(BookEntryParentModel.create(demonsDreamEntry.getId()));

        var thirdEyeEntry = this.add(new ThirdEyeEntry(this).generate('e'));
        thirdEyeEntry.withParent(BookEntryParentModel.create(demonsDreamEntry.getId()));

        var divinationRodEntry = this.add(new DivinationRodEntry(this).generate('r'));
        divinationRodEntry.withParent(BookEntryParentModel.create(spiritFireEntry.getId()));

        var theurgyDivinationRodEntry = this.add(new TheurgyDivinationRodsEntry(this).generate('t'));
        theurgyDivinationRodEntry
                .withParent(BookEntryParentModel.create(divinationRodEntry.getId()))
                .withCondition(
                        BookAndConditionModel.create().withChildren(
                                BookEntryReadConditionModel.create().withEntry(divinationRodEntry.getId()),
                                BookModLoadedConditionModel.create().withModId("theurgy")
                        )
                )
                .hideWhileLocked(true);

        var candleEntry = this.add(new CandleEntry(this).generate('c'));
        candleEntry.withParent(BookEntryParentModel.create(spiritFireEntry.getId()));

        var ritualPrepChalkEntry = this.add(new RitualPrepChalkEntry(this).generate('ç'));
        ritualPrepChalkEntry.withParent(BookEntryParentModel.create(candleEntry.getId()));

        var brushEntry = this.add(new BrushEntry(this).generate('B'));
        brushEntry.withParent(BookEntryParentModel.create(ritualPrepChalkEntry.getId()));

        var ritualPrepBowlEntry = this.add(new RitualPrepBowlEntry(this).generate('b'));
        ritualPrepBowlEntry.withParent(BookEntryParentModel.create(ritualPrepChalkEntry.getId()));

        var booksOfBinding = this.add(new BooksOfBindingEntry(this).generate('ạ'));
        booksOfBinding.withParent(BookEntryParentModel.create(candleEntry.getId()));

        var booksOfBindingAutomation = this.add(new BooksOfBindingAutomationEntry(this).generate('Á'));
        booksOfBindingAutomation.withParent(BookEntryParentModel.create(booksOfBinding.getId()));

        var bookshelfBinding = this.add(new BookshelfBindingEntry(this).generate('É'));
        bookshelfBinding.withParent(BookEntryParentModel.create(booksOfBinding.getId()));

        var booksOfCalling = this.add(new BooksOfCallingEntry(this).generate('C'));
        booksOfCalling.withParent(BookEntryParentModel.create(booksOfBinding.getId()));

        var ritualEntry = this.add(new FirstRitualEntry(this).generate('R'));
        ritualEntry
                .withParent(BookEntryParentModel.create(ritualPrepBowlEntry.getId()))
                .withParent(BookEntryParentModel.create(booksOfBinding.getId()));

        var advancedChalksEntry = this.add(new ChalksEntry(this).generate('a'));
        advancedChalksEntry.withParent(BookEntryParentModel.create(ritualEntry.getId()));

        var ritualSatchelsEntry = this.add(new RitualSatchelsEntry(this).generate('ĝ'));
        ritualSatchelsEntry.withParent(BookEntryParentModel.create(advancedChalksEntry.getId()))
                .withCondition(BookEntryReadConditionModel.create().withEntry(this.modLoc("pentacles/craft_djinni")));

        var moreRitualsEntry = this.add(new MoreRitualsEntry(this).generate('m'));
        moreRitualsEntry.withCategoryToOpen(this.modLoc("rituals"));
        moreRitualsEntry.withParent(BookEntryParentModel.create(advancedChalksEntry.getId()));

        var greyParticlesEntry = this.add(new GreyParticlesEntry(this).generate('p'));
        greyParticlesEntry.withParent(BookEntryParentModel.create(ritualEntry.getId()));

        var spiritsSubcategory = this.add(new SpiritsEntry(this).generate('S'));
        spiritsSubcategory.withCategoryToOpen(this.modLoc("spirits"));
        spiritsSubcategory.withParent(BookEntryParentModel.create(greyParticlesEntry.getId()));

        var otherworldGoggles = this.add(new OtherworldGogglesEntry(this).generate('g'));
        otherworldGoggles.withParent(BookEntryParentModel.create(advancedChalksEntry.getId()))
                .withCondition(BookEntryReadConditionModel.create().withEntry(this.modLoc("pentacles/craft_foliot")));

        var infusedPickaxe = this.add(new InfusedPickaxeEntry(this).generate('I'));
        infusedPickaxe.withParent(BookEntryParentModel.create(otherworldGoggles.getId()))
                .withCondition(BookEntryReadConditionModel.create().withEntry(this.modLoc("pentacles/craft_djinni")));

        var iesnium = this.add(new IesniumEntry(this).generate('O'));
        iesnium.withParent(BookEntryParentModel.create(infusedPickaxe.getId()));

        var iesniumPickaxe = this.add(new IesniumPickaxeEntry(this).generate('P'));
        iesniumPickaxe.withParent(BookEntryParentModel.create(iesnium.getId()));

        var magicLampsEntry = this.add(new MagicLampsEntry(this).generate('l'));
        magicLampsEntry.withParent(BookEntryParentModel.create(ritualEntry.getId()));

        var spiritMinersEntry = this.add(new SpiritMinersEntry(this).generate('M'));
        spiritMinersEntry.withParent(BookEntryParentModel.create(iesnium.getId()));

        var mineshaftEntry = this.add(new MineshaftEntry(this).generate('D'));
        mineshaftEntry.withParent(BookEntryParentModel.create(spiritMinersEntry.getId()));

        var storageEntry = this.add(new StorageEntry(this).generate('s'));
        storageEntry.withCategoryToOpen(this.modLoc("storage"));
        storageEntry.withParent(BookEntryParentModel.create(advancedChalksEntry.getId()))
                .withCondition(BookEntryReadConditionModel.create().withEntry(this.modLoc("pentacles/craft_djinni")));

        var possessionRitualsEntry = this.add(new PossessionRitualsEntry(this).generate('w'));
        possessionRitualsEntry.withParent(BookEntryParentModel.create(moreRitualsEntry.getId()))
                .withCondition(BookEntryReadConditionModel.create().withEntry(this.modLoc("pentacles/possess_foliot")));

        var familiarRitualsEntry = this.add(new FamiliarRitualsEntry(this).generate('x'));
        familiarRitualsEntry.withParent(BookEntryParentModel.create(moreRitualsEntry.getId()))
                .withCondition(BookEntryReadConditionModel.create().withEntry(this.modLoc("pentacles/possess_foliot")));

        var summoningRitualsEntry = this.add(new SummoningRitualsEntry(this).generate('y'));
        summoningRitualsEntry.withParent(BookEntryParentModel.create(moreRitualsEntry.getId()))
                .withCondition(BookEntryReadConditionModel.create().withEntry(this.modLoc("pentacles/summon_foliot")));

        var craftingRitualsEntry = this.add(new CraftingRitualsEntry(this).generate('z'));
        craftingRitualsEntry.withParent(BookEntryParentModel.create(moreRitualsEntry.getId()))
                .withCondition(BookEntryReadConditionModel.create().withEntry(this.modLoc("pentacles/craft_foliot")));
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
