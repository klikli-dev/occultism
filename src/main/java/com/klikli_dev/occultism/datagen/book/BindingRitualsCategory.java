package com.klikli_dev.occultism.datagen.book;

import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookEntryParentModel;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.occultism.datagen.OccultismBookProvider;
import com.klikli_dev.occultism.datagen.OccultismResearch;
import com.klikli_dev.occultism.datagen.book.binding_rituals.*;
import com.klikli_dev.occultism.datagen.book.pentacles.*;

public class BindingRitualsCategory extends CategoryProvider {
    public static final String CATEGORY_ID = "crafting_rituals";

    public BindingRitualsCategory(OccultismBookProvider parent) {
        super(parent);
    }

    @Override
    protected String categoryName() {
        return "Binding Rituals";
    }

    @Override
    protected BookIconModel categoryIcon() {
        return BookIconModel.create(this.modLoc("textures/gui/book/infusion.png"));
    }

    @Override
    public String categoryId() {
        return CATEGORY_ID;
    }

    @Override
    protected void generateEntries() {
        //Basic Entries
        var overview = this.add(new CraftingOverviewEntry(this).generate());
        overview.withCondition(this.condition().researchNodeEntryViewedOnce(OccultismResearch.PENTACLES_CRAFT_FOLIOT, OccultismResearch.entryModel(PentaclesCategory.CATEGORY_ID, CraftFoliotEntry.ENTRY_ID)));
        this.layout().entry(overview).at(-10, 0);
        var returnToRituals = this.add(new ReturnToRitualsEntry(this).generate());
        returnToRituals.withCategoryToOpen(this.modLoc("rituals"));
        returnToRituals.withParent(BookEntryParentModel.create(overview.getId()))
                .withCondition(this.condition().researchNodeEntryViewedOnce(OccultismResearch.PENTACLES_CRAFT_FOLIOT, OccultismResearch.entryModel(PentaclesCategory.CATEGORY_ID, CraftFoliotEntry.ENTRY_ID)));
        this.layout().entry(returnToRituals).leftOf(overview, 2);

        var craftFragileSoulGem = this.add(new FragileSoulGemEntry(this).generate());
        craftFragileSoulGem.withParent(BookEntryParentModel.create(overview.getId()))
                .withCondition(this.condition().researchNodeEntryViewedOnce(OccultismResearch.PENTACLES_CRAFT_FOLIOT, OccultismResearch.entryModel(PentaclesCategory.CATEGORY_ID, CraftFoliotEntry.ENTRY_ID)));
        this.layout().entry(craftFragileSoulGem).rightOf(overview, 2).above(2);
        var craftSoulGem = this.add(new SoulGemEntry(this).generate());
        craftSoulGem.withParent(BookEntryParentModel.create(craftFragileSoulGem.getId()))
                .withCondition(this.condition().researchNodeEntryViewedOnce(OccultismResearch.PENTACLES_CRAFT_DJINNI, OccultismResearch.entryModel(PentaclesCategory.CATEGORY_ID, CraftDjinniEntry.ENTRY_ID)));
        this.layout().entry(craftSoulGem).above(craftFragileSoulGem, 2);
        var craftFamiliarRing = this.add(new FamiliarRingEntry(this).generate());
        craftFamiliarRing.withParent(BookEntryParentModel.create(craftSoulGem.getId()))
                .withCondition(this.condition().researchNodeEntryViewedOnce(OccultismResearch.PENTACLES_CRAFT_DJINNI, OccultismResearch.entryModel(PentaclesCategory.CATEGORY_ID, CraftDjinniEntry.ENTRY_ID)));
        this.layout().entry(craftFamiliarRing).rightOf(craftSoulGem, 2).above(2);
        var craftTrinityGem = this.add(new TrinityGemEntry(this).generate());
        craftTrinityGem.withParent(BookEntryParentModel.create(craftSoulGem.getId()))
                .withCondition(this.condition().researchNodeEntryViewedOnce(OccultismResearch.PENTACLES_CONTACT_ELDRITCH, OccultismResearch.entryModel(PentaclesCategory.CATEGORY_ID, ContactEldritchSpiritEntry.ENTRY_ID)));
        this.layout().entry(craftTrinityGem).above(craftSoulGem, 2);
        var craftFamiliarGlove = this.add(new FamiliarGloveEntry(this).generate());
        craftFamiliarGlove.withParent(craftFamiliarRing).withParent(BookEntryParentModel.create(craftTrinityGem.getId()).withLineReversed(true))
                .withCondition(this.condition().researchNodeEntryViewedOnce(OccultismResearch.PENTACLES_CONTACT_ELDRITCH, OccultismResearch.entryModel(PentaclesCategory.CATEGORY_ID, CraftDjinniEntry.ENTRY_ID)));
        this.layout().entry(craftFamiliarGlove).rightOf(craftTrinityGem, 2).above(2);

        var craftInfusedPickaxe = this.add(new InfusedToolsEntry(this).generate());
        craftInfusedPickaxe.withParent(BookEntryParentModel.create(craftSoulGem.getId()))
                .withCondition(this.condition().researchNodeEntryViewedOnce(OccultismResearch.PENTACLES_CRAFT_DJINNI, OccultismResearch.entryModel(PentaclesCategory.CATEGORY_ID, CraftDjinniEntry.ENTRY_ID)));
        this.layout().entry(craftInfusedPickaxe).rightOf(craftSoulGem, 4);
        var craftInfusedArmor = this.add(new InfusedArmorEntry(this).generate());
        craftInfusedArmor.withParent(BookEntryParentModel.create(craftSoulGem.getId()))
                .withCondition(this.condition().researchNodeEntryViewedOnce(OccultismResearch.PENTACLES_CRAFT_DJINNI, OccultismResearch.entryModel(PentaclesCategory.CATEGORY_ID, CraftDjinniEntry.ENTRY_ID)));
        this.layout().entry(craftInfusedArmor).rightOf(craftSoulGem, 2).below(2);
        var craftOtherworldGoggles = this.add(new OtherworldGogglesEntry(this).generate());
        craftOtherworldGoggles.withParent(BookEntryParentModel.create(overview.getId()))
                .withCondition(this.condition().researchNodeEntryViewedOnce(OccultismResearch.PENTACLES_CRAFT_FOLIOT, OccultismResearch.entryModel(PentaclesCategory.CATEGORY_ID, CraftFoliotEntry.ENTRY_ID)));
        this.layout().entry(craftOtherworldGoggles).rightOf(overview, 6).above(2);

        //Dimensional Mining Entries
        var craftDimensionalMineshaft = this.add(new DimensionalMineshaftEntry(this).generate());
        craftDimensionalMineshaft.withParent(BookEntryParentModel.create(craftInfusedPickaxe.getId()))
                .withCondition(this.condition().researchNodeEntryViewedOnce(OccultismResearch.PENTACLES_CRAFT_DJINNI, OccultismResearch.entryModel(PentaclesCategory.CATEGORY_ID, CraftDjinniEntry.ENTRY_ID)));
        this.layout().entry(craftDimensionalMineshaft).above(craftInfusedPickaxe, 2).rightOf(2);
        var craftFoliotMiner = this.add(new MinerFoliotEntry(this).generate());
        craftFoliotMiner.withParent(BookEntryParentModel.create(craftDimensionalMineshaft.getId()))
                .withCondition(this.condition().researchNodeEntryViewedOnce(OccultismResearch.PENTACLES_CRAFT_DJINNI, OccultismResearch.entryModel(PentaclesCategory.CATEGORY_ID, CraftDjinniEntry.ENTRY_ID)));
        this.layout().entry(craftFoliotMiner).above(craftDimensionalMineshaft, 2).leftOf(2);
        var craftDjinniMiner = this.add(new MinerDjinniEntry(this).generate());
        craftDjinniMiner.withParent(BookEntryParentModel.create(craftFoliotMiner.getId()))
                .withCondition(this.condition().researchNodeEntryViewedOnce(OccultismResearch.PENTACLES_CRAFT_DJINNI, OccultismResearch.entryModel(PentaclesCategory.CATEGORY_ID, CraftDjinniEntry.ENTRY_ID)));
        this.layout().entry(craftDjinniMiner).rightOf(craftFoliotMiner, 2);
        var craftAfritMiner = this.add(new MinerAfritEntry(this).generate());
        craftAfritMiner.withParent(BookEntryParentModel.create(craftDjinniMiner.getId()))
                .withCondition(this.condition().researchNodeEntryViewedOnce(OccultismResearch.PENTACLES_CRAFT_AFRIT, OccultismResearch.entryModel(PentaclesCategory.CATEGORY_ID, CraftAfritEntry.ENTRY_ID)));
        this.layout().entry(craftAfritMiner).rightOf(craftDjinniMiner, 2);
        var craftMaridMiner = this.add(new MinerMaridEntry(this).generate());
        craftMaridMiner.withParent(BookEntryParentModel.create(craftAfritMiner.getId()))
                .withCondition(this.condition().researchNodeEntryViewedOnce(OccultismResearch.PENTACLES_CRAFT_MARID, OccultismResearch.entryModel(PentaclesCategory.CATEGORY_ID, CraftMaridEntry.ENTRY_ID)));
        this.layout().entry(craftMaridMiner).rightOf(craftAfritMiner, 2);
        var craftAncientMiner = this.add(new MinerAncientEntry(this).generate());
        craftAncientMiner.withParent(BookEntryParentModel.create(craftMaridMiner.getId()))
                .withCondition(this.condition().researchNodeEntryViewedOnce(OccultismResearch.PENTACLES_CONTACT_ELDRITCH, OccultismResearch.entryModel(PentaclesCategory.CATEGORY_ID, ContactEldritchSpiritEntry.ENTRY_ID)));
        this.layout().entry(craftAncientMiner).rightOf(craftMaridMiner, 2);
        //Dimensional Battlefield
        var craftDimensionalBattlefield = this.add(new DimensionalBattlefieldEntry(this).generate());
        craftDimensionalBattlefield.withParent(BookEntryParentModel.create(craftInfusedPickaxe.getId()))
                .withCondition(this.condition().researchNodeEntryViewedOnce(OccultismResearch.PENTACLES_CRAFT_AFRIT, OccultismResearch.entryModel(PentaclesCategory.CATEGORY_ID, CraftAfritEntry.ENTRY_ID)));
        this.layout().entry(craftDimensionalBattlefield).rightOf(craftInfusedPickaxe, 4).above(2);
        //Dimensional Extractor
        var craftDimensionalExtractor = this.add(new DimensionalExtractorEntry(this).generate());
        craftDimensionalExtractor.withParent(BookEntryParentModel.create(craftInfusedPickaxe.getId()))
                .withCondition(this.condition().researchNodeEntryViewedOnce(OccultismResearch.PENTACLES_CRAFT_DJINNI, OccultismResearch.entryModel(PentaclesCategory.CATEGORY_ID, CraftDjinniEntry.ENTRY_ID)));
        this.layout().entry(craftDimensionalExtractor).rightOf(craftInfusedPickaxe, 6).above(2);

        //Storage Entries
        //Start
        var craftStorageSystem = this.add(new StorageSystemEntry(this).generate());
        craftStorageSystem.withParent(BookEntryParentModel.create(overview.getId()))
                .withCondition(this.condition().researchNodeEntryViewedOnce(OccultismResearch.PENTACLES_CRAFT_FOLIOT, OccultismResearch.entryModel(PentaclesCategory.CATEGORY_ID, CraftFoliotEntry.ENTRY_ID)));
        this.layout().entry(craftStorageSystem).rightOf(overview, 2).below(2);
        var craftDimensionalMatrix = this.add(new DimensionalMatrixEntry(this).generate());
        craftDimensionalMatrix.withParent(BookEntryParentModel.create(craftStorageSystem.getId()))
                .withCondition(this.condition().researchNodeEntryViewedOnce(OccultismResearch.PENTACLES_CRAFT_DJINNI, OccultismResearch.entryModel(PentaclesCategory.CATEGORY_ID, CraftDjinniEntry.ENTRY_ID)));
        this.layout().entry(craftDimensionalMatrix).below(craftStorageSystem, 2);
        var craftStorageControllerBase = this.add(new StorageControllerBaseEntry(this).generate());
        craftStorageControllerBase.withParent(BookEntryParentModel.create(craftDimensionalMatrix.getId()).withLineReversed(true))
                .withCondition(this.condition().researchNodeEntryViewedOnce(OccultismResearch.PENTACLES_CRAFT_DJINNI, OccultismResearch.entryModel(PentaclesCategory.CATEGORY_ID, CraftDjinniEntry.ENTRY_ID)));
        this.layout().entry(craftStorageControllerBase).rightOf(craftDimensionalMatrix, 2).below(4);
        //Stabilizer
        var craftStabilizerTier1 = this.add(new StabilizerTier1Entry(this).generate());
        craftStabilizerTier1.withParent(BookEntryParentModel.create(craftStorageControllerBase.getId()))
                .withCondition(this.condition().researchNodeEntryViewedOnce(OccultismResearch.PENTACLES_CRAFT_DJINNI, OccultismResearch.entryModel(PentaclesCategory.CATEGORY_ID, CraftDjinniEntry.ENTRY_ID)));
        this.layout().entry(craftStabilizerTier1).rightOf(craftStorageControllerBase, 4);
        var craftStabilizerTier2 = this.add(new StabilizerTier2Entry(this).generate());
        craftStabilizerTier2.withParent(BookEntryParentModel.create(craftStabilizerTier1.getId()))
                .withCondition(this.condition().researchNodeEntryViewedOnce(OccultismResearch.PENTACLES_CRAFT_DJINNI, OccultismResearch.entryModel(PentaclesCategory.CATEGORY_ID, CraftDjinniEntry.ENTRY_ID)));
        this.layout().entry(craftStabilizerTier2).rightOf(craftStabilizerTier1, 2);
        var craftStabilizerTier3 = this.add(new StabilizerTier3Entry(this).generate());
        craftStabilizerTier3.withParent(BookEntryParentModel.create(craftStabilizerTier2.getId()))
                .withCondition(this.condition().researchNodeEntryViewedOnce(OccultismResearch.PENTACLES_CRAFT_AFRIT, OccultismResearch.entryModel(PentaclesCategory.CATEGORY_ID, CraftAfritEntry.ENTRY_ID)));
        this.layout().entry(craftStabilizerTier3).rightOf(craftStabilizerTier2, 2);
        var craftStabilizerTier4 = this.add(new StabilizerTier4Entry(this).generate());
        craftStabilizerTier4.withParent(BookEntryParentModel.create(craftStabilizerTier3.getId()))
                .withCondition(this.condition().researchNodeEntryViewedOnce(OccultismResearch.PENTACLES_CRAFT_MARID, OccultismResearch.entryModel(PentaclesCategory.CATEGORY_ID, CraftMaridEntry.ENTRY_ID)));
        this.layout().entry(craftStabilizerTier4).rightOf(craftStabilizerTier3, 2);
        var craftStabilizerTier5 = this.add(new StabilizerTier5Entry(this).generate());
        craftStabilizerTier5.withParent(BookEntryParentModel.create(craftStabilizerTier4.getId()))
                .withCondition(this.condition().researchNodeEntryViewedOnce(OccultismResearch.PENTACLES_CONTACT_ELDRITCH, OccultismResearch.entryModel(PentaclesCategory.CATEGORY_ID, ContactEldritchSpiritEntry.ENTRY_ID)));
        this.layout().entry(craftStabilizerTier5).rightOf(craftStabilizerTier4, 2);
        //Distance Access
        var craftStableWormhole = this.add(new StableWormholeEntry(this).generate());
        craftStableWormhole.withParent(BookEntryParentModel.create(craftStorageControllerBase.getId()).withLineReversed(true))
                .withCondition(this.condition().researchNodeEntryViewedOnce(OccultismResearch.PENTACLES_CRAFT_DJINNI, OccultismResearch.entryModel(PentaclesCategory.CATEGORY_ID, CraftDjinniEntry.ENTRY_ID)));
        this.layout().entry(craftStableWormhole).above(craftStorageControllerBase, 2);
        var craftStorageRemote = this.add(new StorageRemoteEntry(this).generate());
        craftStorageRemote.withParent(BookEntryParentModel.create(craftStorageControllerBase.getId()))
                .withCondition(this.condition().researchNodeEntryViewedOnce(OccultismResearch.PENTACLES_CRAFT_DJINNI, OccultismResearch.entryModel(PentaclesCategory.CATEGORY_ID, CraftDjinniEntry.ENTRY_ID)));
        this.layout().entry(craftStorageRemote).rightOf(craftStableWormhole, 2);
        //Final
        var craftStabilizedStorage = this.add(new StabilizedStorageEntry(this).generate());
        craftStabilizedStorage.withParent(BookEntryParentModel.create(craftStabilizerTier5.getId()))
                .withCondition(this.condition().researchNodeEntryViewedOnce(OccultismResearch.PENTACLES_CONTACT_ELDRITCH, OccultismResearch.entryModel(PentaclesCategory.CATEGORY_ID, ContactEldritchSpiritEntry.ENTRY_ID)));
        this.layout().entry(craftStabilizedStorage).rightOf(craftStabilizerTier5, 2).above(2);
        //Satchels
        var craftSatchel = this.add(new BackpackSatchelEntry(this).generate());
        craftSatchel.withParent(BookEntryParentModel.create(craftStorageSystem.getId()))
                .withCondition(this.condition().researchNodeEntryViewedOnce(OccultismResearch.PENTACLES_CRAFT_FOLIOT, OccultismResearch.entryModel(PentaclesCategory.CATEGORY_ID, CraftFoliotEntry.ENTRY_ID)));
        this.layout().entry(craftSatchel).rightOf(craftStorageSystem, 2);
        var craftEnderSatchel = this.add(new EnderSatchelEntry(this).generate());
        craftEnderSatchel.withParent(BookEntryParentModel.create(craftSatchel.getId()))
                .withCondition(this.condition().researchNodeEntryViewedOnce(OccultismResearch.PENTACLES_CRAFT_DJINNI, OccultismResearch.entryModel(PentaclesCategory.CATEGORY_ID, CraftDjinniEntry.ENTRY_ID)));
        this.layout().entry(craftEnderSatchel).below(craftSatchel, 2);
        var apprenticeRitualSatchel = this.add(new ApprenticeRitualSatchelEntry(this).generate());
        apprenticeRitualSatchel.withParent(craftSatchel)
                .withCondition(this.condition().researchNodeEntryViewedOnce(OccultismResearch.PENTACLES_CRAFT_FOLIOT, OccultismResearch.entryModel(PentaclesCategory.CATEGORY_ID, CraftFoliotEntry.ENTRY_ID)));
        this.layout().entry(apprenticeRitualSatchel).rightOf(craftSatchel, 2);
        var artisanalRitualSatchel = this.add(new ArtisanalRitualSatchelEntry(this).generate());
        artisanalRitualSatchel.withParent(apprenticeRitualSatchel)
                .withCondition(this.condition().researchNodeEntryViewedOnce(OccultismResearch.PENTACLES_CRAFT_AFRIT, OccultismResearch.entryModel(PentaclesCategory.CATEGORY_ID, CraftAfritEntry.ENTRY_ID)));
        this.layout().entry(artisanalRitualSatchel).below(apprenticeRitualSatchel, 2);

        //Crafting Materials
        //Dusts
        var craftResearchDust = this.add(new ResearchDustEntry(this).generate());
        craftResearchDust.withParent(BookEntryParentModel.create(overview.getId()))
                .withCondition(this.condition().researchNodeEntryViewedOnce(OccultismResearch.PENTACLES_CRAFT_FOLIOT, OccultismResearch.entryModel(PentaclesCategory.CATEGORY_ID, CraftFoliotEntry.ENTRY_ID)));
        this.layout().entry(craftResearchDust).rightOf(overview, 8).below(2);
        var craftWithertiteDust = this.add(new WithertiteDustEntry(this).generate());
        craftWithertiteDust.withParent(BookEntryParentModel.create(craftResearchDust.getId()))
                .withCondition(this.condition().researchNodeEntryViewedOnce(OccultismResearch.PENTACLES_CRAFT_AFRIT, OccultismResearch.entryModel(PentaclesCategory.CATEGORY_ID, CraftAfritEntry.ENTRY_ID)));
        this.layout().entry(craftWithertiteDust).below(craftResearchDust, 2);
        var craftDragonystDust = this.add(new DragonystDustEntry(this).generate());
        craftDragonystDust.withParent(BookEntryParentModel.create(craftWithertiteDust.getId()))
                .withCondition(this.condition().researchNodeEntryViewedOnce(OccultismResearch.PENTACLES_CRAFT_MARID, OccultismResearch.entryModel(PentaclesCategory.CATEGORY_ID, CraftMaridEntry.ENTRY_ID)));
        this.layout().entry(craftDragonystDust).below(craftWithertiteDust, 2);
        //Pastes
        var craftNaturePaste = this.add(new NaturePasteEntry(this).generate());
        craftNaturePaste.withParent(BookEntryParentModel.create(overview.getId()))
                .withCondition(this.condition().researchNodeEntryViewedOnce(OccultismResearch.PENTACLES_CRAFT_FOLIOT, OccultismResearch.entryModel(PentaclesCategory.CATEGORY_ID, CraftFoliotEntry.ENTRY_ID)));
        this.layout().entry(craftNaturePaste).rightOf(overview, 10).below(2);
        var craftGrayPaste = this.add(new GrayPasteEntry(this).generate());
        craftGrayPaste.withParent(BookEntryParentModel.create(craftNaturePaste.getId()))
                .withCondition(this.condition().researchNodeEntryViewedOnce(OccultismResearch.PENTACLES_CRAFT_DJINNI, OccultismResearch.entryModel(PentaclesCategory.CATEGORY_ID, CraftDjinniEntry.ENTRY_ID)));
        this.layout().entry(craftGrayPaste).below(craftNaturePaste, 2);
        var craftFlamingPaste = this.add(new FlamingPasteEntry(this).generate());
        craftFlamingPaste.withParent(BookEntryParentModel.create(craftGrayPaste.getId()))
                .withCondition(this.condition().researchNodeEntryViewedOnce(OccultismResearch.PENTACLES_CRAFT_AFRIT, OccultismResearch.entryModel(PentaclesCategory.CATEGORY_ID, CraftAfritEntry.ENTRY_ID)));
        this.layout().entry(craftFlamingPaste).below(craftGrayPaste, 2);

        var craftKnowledgeTablet = this.add(new KnowledgeTabletEntry(this).generate());
        craftKnowledgeTablet.withParent(BookEntryParentModel.create(overview.getId()))
                .withCondition(this.condition().researchNodeEntryViewedOnce(OccultismResearch.PENTACLES_CRAFT_FOLIOT, OccultismResearch.entryModel(PentaclesCategory.CATEGORY_ID, CraftFoliotEntry.ENTRY_ID)));
        this.layout().entry(craftKnowledgeTablet).rightOf(overview, 10).above(2);
        var craftVitalityCompass = this.add(new VitalityCompassEntry(this).generate());
        craftVitalityCompass.withParent(BookEntryParentModel.create(overview.getId()))
                .withCondition(this.condition().researchNodeEntryViewedOnce(OccultismResearch.PENTACLES_CRAFT_FOLIOT, OccultismResearch.entryModel(PentaclesCategory.CATEGORY_ID, CraftFoliotEntry.ENTRY_ID)));
        this.layout().entry(craftVitalityCompass).rightOf(craftKnowledgeTablet, 2);
        var craftEntityWormhole = this.add(new EntityWormholeEntry(this).generate());
        craftEntityWormhole.withParent(BookEntryParentModel.create(craftInfusedPickaxe.getId()))
                .withCondition(this.condition().researchNodeEntryViewedOnce(OccultismResearch.PENTACLES_CRAFT_DJINNI, OccultismResearch.entryModel(PentaclesCategory.CATEGORY_ID, CraftDjinniEntry.ENTRY_ID)));
        this.layout().entry(craftEntityWormhole).rightOf(craftInfusedPickaxe, 8).above(2);
        var craftWormholeTablet = this.add(new WormholeTabletEntry(this).generate());
        craftWormholeTablet.withParent(BookEntryParentModel.create(craftEntityWormhole.getId()))
                .withCondition(this.condition().researchNodeEntryViewedOnce(OccultismResearch.PENTACLES_CRAFT_DJINNI, OccultismResearch.entryModel(PentaclesCategory.CATEGORY_ID, CraftDjinniEntry.ENTRY_ID)));
        this.layout().entry(craftWormholeTablet).rightOf(craftEntityWormhole, 2);
        var craftSpiritGrindStone = this.add(new SpiritGrindstoneEntry(this).generate());
        craftSpiritGrindStone.withParent(BookEntryParentModel.create(overview.getId()))
                .withCondition(this.condition().researchNodeEntryViewedOnce(OccultismResearch.PENTACLES_CRAFT_DJINNI, OccultismResearch.entryModel(PentaclesCategory.CATEGORY_ID, CraftDjinniEntry.ENTRY_ID)));
        this.layout().entry(craftSpiritGrindStone).rightOf(overview, 16).below(2);

        var craftIesniumSacrificialBowl = this.add(new IesniumSacrificialBowlEntry(this).generate());
        craftIesniumSacrificialBowl.withParent(BookEntryParentModel.create(craftInfusedPickaxe.getId()))
                .withCondition(this.condition().researchNodeEntryViewedOnce(OccultismResearch.PENTACLES_CRAFT_AFRIT, OccultismResearch.entryModel(PentaclesCategory.CATEGORY_ID, CraftAfritEntry.ENTRY_ID)));
        this.layout().entry(craftIesniumSacrificialBowl).rightOf(craftInfusedPickaxe, 12).above(2);
        var craftIesniumAnvil = this.add(new IesniumAnvilEntry(this).generate());
        craftIesniumAnvil.withParent(BookEntryParentModel.create(craftInfusedPickaxe.getId()))
                .withCondition(this.condition().researchNodeEntryViewedOnce(OccultismResearch.PENTACLES_CRAFT_MARID, OccultismResearch.entryModel(PentaclesCategory.CATEGORY_ID, CraftMaridEntry.ENTRY_ID)));
        this.layout().entry(craftIesniumAnvil).rightOf(craftInfusedPickaxe, 10).below(2);
        var craftIesniumButcherKnife = this.add(new IesniumButcherKnifeEntry(this).generate());
        craftIesniumButcherKnife.withParent(BookEntryParentModel.create(craftInfusedPickaxe.getId()))
                .withCondition(this.condition().researchNodeEntryViewedOnce(OccultismResearch.PENTACLES_CRAFT_AFRIT, OccultismResearch.entryModel(PentaclesCategory.CATEGORY_ID, CraftAfritEntry.ENTRY_ID)));
        this.layout().entry(craftIesniumButcherKnife).rightOf(craftInfusedPickaxe, 8).below(2);
        var trueSightStaff = this.add(new TrueSightStaffEntry(this).generate());
        trueSightStaff.withParent(craftOtherworldGoggles).withParent(craftInfusedPickaxe)
                .withCondition(this.condition().researchNodeEntryViewedOnce(OccultismResearch.PENTACLES_CRAFT_MARID, OccultismResearch.entryModel(PentaclesCategory.CATEGORY_ID, CraftMaridEntry.ENTRY_ID)));
        this.layout().entry(trueSightStaff).rightOf(craftOtherworldGoggles, 2);

        var craftWildTrim = this.add(new WildTrimEntry(this).generate());
        craftWildTrim.withParent(BookEntryParentModel.create(overview.getId()))
                .withCondition(this.condition().researchNodeEntryViewedOnce(OccultismResearch.PENTACLES_CONTACT_WILD, OccultismResearch.entryModel(PentaclesCategory.CATEGORY_ID, ContactWildSpiritEntry.ENTRY_ID)));
        this.layout().entry(craftWildTrim).rightOf(overview, 18).below(4);
        var craftAmethyst = this.add(new BuddingAmethystEntry(this).generate());
        craftAmethyst.withParent(BookEntryParentModel.create(craftWildTrim.getId()))
                .withCondition(this.condition().researchNodeEntryViewedOnce(OccultismResearch.PENTACLES_CONTACT_WILD, OccultismResearch.entryModel(PentaclesCategory.CATEGORY_ID, ContactWildSpiritEntry.ENTRY_ID)));
        this.layout().entry(craftAmethyst).leftOf(craftWildTrim, 4).below(2);
        var craftDeepslate = this.add(new ReinforcedDeepslateEntry(this).generate());
        craftDeepslate.withParent(BookEntryParentModel.create(craftWildTrim.getId()))
                .withCondition(this.condition().researchNodeEntryViewedOnce(OccultismResearch.PENTACLES_CONTACT_WILD, OccultismResearch.entryModel(PentaclesCategory.CATEGORY_ID, ContactWildSpiritEntry.ENTRY_ID)));
        this.layout().entry(craftDeepslate).leftOf(craftWildTrim, 2).below(2);
        var craftBeeNest = this.add(new BeeNestEntry(this).generate());
        craftBeeNest.withParent(BookEntryParentModel.create(craftWildTrim.getId()))
                .withCondition(this.condition().researchNodeEntryViewedOnce(OccultismResearch.PENTACLES_CONTACT_WILD, OccultismResearch.entryModel(PentaclesCategory.CATEGORY_ID, ContactWildSpiritEntry.ENTRY_ID)));
        this.layout().entry(craftBeeNest).leftOf(craftWildTrim, 6).below(2);
        var craftBell = this.add(new BellEntry(this).generate());
        craftBell.withParent(BookEntryParentModel.create(craftWildTrim.getId()))
                .withCondition(this.condition().researchNodeEntryViewedOnce(OccultismResearch.PENTACLES_CONTACT_WILD, OccultismResearch.entryModel(PentaclesCategory.CATEGORY_ID, ContactWildSpiritEntry.ENTRY_ID)));
        this.layout().entry(craftBell).leftOf(craftWildTrim, 4).above(2);
        var craftHorseArmor = this.add(new AnimalArmorEntry(this).generate());
        craftHorseArmor.withParent(BookEntryParentModel.create(craftWildTrim.getId()))
                .withCondition(this.condition().researchNodeEntryViewedOnce(OccultismResearch.PENTACLES_CONTACT_WILD, OccultismResearch.entryModel(PentaclesCategory.CATEGORY_ID, ContactWildSpiritEntry.ENTRY_ID)));
        this.layout().entry(craftHorseArmor).leftOf(craftWildTrim, 6).above(2);
        var craftEldritchChalice = this.add(new EldritchChaliceEntry(this).generate());
        craftEldritchChalice.withParent(BookEntryParentModel.create(craftIesniumSacrificialBowl.getId()).withLineReversed(true))
                .withCondition(this.condition().researchNodeEntryViewedOnce(OccultismResearch.PENTACLES_CONTACT_ELDRITCH, OccultismResearch.entryModel(PentaclesCategory.CATEGORY_ID, ContactEldritchSpiritEntry.ENTRY_ID)));
        this.layout().entry(craftEldritchChalice).leftOf(craftIesniumSacrificialBowl, 2).above(2);

        var craftMasterChalks = this.add(new MasterChalksEntry(this).generate());
        craftMasterChalks.withParent(BookEntryParentModel.create(overview.getId()))
                .withCondition(this.condition().researchNodeEntryViewedOnce(OccultismResearch.PENTACLES_CONTACT_ELDRITCH, OccultismResearch.entryModel(PentaclesCategory.CATEGORY_ID, ContactEldritchSpiritEntry.ENTRY_ID)));
        this.layout().entry(craftMasterChalks).rightOf(overview, 18).above(2);
        var repairRituals = this.add(new RepairEntry(this).generate());
        repairRituals.withParent(BookEntryParentModel.create(overview.getId()))
                .withCondition(this.condition().researchNodeEntryViewedOnce(OccultismResearch.PENTACLES_CRAFT_DJINNI, OccultismResearch.entryModel(PentaclesCategory.CATEGORY_ID, CraftDjinniEntry.ENTRY_ID)));
        this.layout().entry(repairRituals).rightOf(overview, 20);
        var unbreakableRituals = this.add(new UnbreakableEntry(this).generate());
        unbreakableRituals.withParent(BookEntryParentModel.create(repairRituals.getId()))
                .withCondition(this.condition().researchNodeEntryViewedOnce(OccultismResearch.PENTACLES_CONTACT_ELDRITCH, OccultismResearch.entryModel(PentaclesCategory.CATEGORY_ID, ContactEldritchSpiritEntry.ENTRY_ID)));
        this.layout().entry(unbreakableRituals).rightOf(repairRituals, 2);
    }

}
