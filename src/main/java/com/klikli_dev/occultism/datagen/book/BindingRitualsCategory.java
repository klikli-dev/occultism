package com.klikli_dev.occultism.datagen.book;

import com.klikli_dev.modonomicon.api.datagen.CategoryEntryMap;
import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookEntryModel;
import com.klikli_dev.modonomicon.api.datagen.book.BookEntryParentModel;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.condition.BookEntryReadConditionModel;
import com.klikli_dev.occultism.datagen.OccultismBookProvider;
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
    protected String[] generateEntryMap() {
        return new String[]{
                "___________________________",
                "_______e_x_p_q_r_t_Ť_______",
                "___________________________",
                "_____b___ᑬ_______u_c_h_____",
                "___________________________",
                "_____d_______________é_____",
                "___________________________",
                "_______f_F_Ċ_K___A_G_______",
                "___________________________",
                "_9_0___________________y___",
                "___________________________",
                "_____z_g_ĝ_Y_ã_Ж___Č_______",
                "___________________________",
                "_____a_ģ_ğ___________w_____",
                "___________________________",
                "_______m_o_ĥ_H_B_v_s_ç_____",
                "___________________________",
                "_______n___i_j_k_l_á_______",
                "___________________________"
        };
    }

    @Override
    protected void generateEntries() {
        //Pentacle parents ID
        String craftFoliotID = this.modId() + ":" + PentaclesCategory.CATEGORY_ID + "/" + CraftFoliotEntry.ENTRY_ID;
        String craftDjinniID = this.modId() + ":" + PentaclesCategory.CATEGORY_ID + "/" + CraftDjinniEntry.ENTRY_ID;
        String craftAfritID = this.modId() + ":" + PentaclesCategory.CATEGORY_ID + "/" + CraftAfritEntry.ENTRY_ID;
        String craftMaridID = this.modId() + ":" + PentaclesCategory.CATEGORY_ID + "/" + CraftMaridEntry.ENTRY_ID;
        String contactWildID = this.modId() + ":" + PentaclesCategory.CATEGORY_ID + "/" + ContactWildSpiritEntry.ENTRY_ID;
        String contactEldritchID = this.modId() + ":" + PentaclesCategory.CATEGORY_ID + "/" + ContactEldritchSpiritEntry.ENTRY_ID;

        //Basic Entries
        var overview = this.add(new CraftingOverviewEntry(this).generate('0'));
        overview.withCondition(BookEntryReadConditionModel.create().withEntry(craftFoliotID));
        var returnToRituals = this.add(this.makeReturnToRitualsEntry(this.entryMap));
        returnToRituals.withParent(BookEntryParentModel.create(overview.getId()))
                .withCondition(BookEntryReadConditionModel.create().withEntry(craftFoliotID));

        var craftInfusedPickaxe = this.add(new InfusedPickaxeEntry(this).generate('d'));
        craftInfusedPickaxe.withParent(BookEntryParentModel.create(overview.getId()))
                .withCondition(BookEntryReadConditionModel.create().withEntry(craftDjinniID));
        var craftOtherworldGoggles = this.add(new OtherworldGogglesEntry(this).generate('f'));
        craftOtherworldGoggles.withParent(BookEntryParentModel.create(overview.getId()))
                .withCondition(BookEntryReadConditionModel.create().withEntry(craftFoliotID));

        //Dimensional Mining Entries
        var craftDimensionalMineshaft = this.add(new DimensionalMineshaftEntry(this).generate('b'));
        craftDimensionalMineshaft.withParent(BookEntryParentModel.create(craftInfusedPickaxe.getId()))
                .withCondition(BookEntryReadConditionModel.create().withEntry(craftDjinniID));
        var craftFoliotMiner = this.add(new MinerFoliotEntry(this).generate('e'));
        craftFoliotMiner.withParent(BookEntryParentModel.create(craftDimensionalMineshaft.getId()).withLineReversed(true))
                .withCondition(BookEntryReadConditionModel.create().withEntry(craftDjinniID));
        var craftDjinniMiner = this.add(new MinerDjinniEntry(this).generate('x'));
        craftDjinniMiner.withParent(BookEntryParentModel.create(craftFoliotMiner.getId()))
                .withCondition(BookEntryReadConditionModel.create().withEntry(craftDjinniID));
        var craftAfritMiner = this.add(new MinerAfritEntry(this).generate('p'));
        craftAfritMiner.withParent(BookEntryParentModel.create(craftDjinniMiner.getId()))
                .withCondition(BookEntryReadConditionModel.create().withEntry(craftAfritID));
        var craftMaridMiner = this.add(new MinerMaridEntry(this).generate('q'));
        craftMaridMiner.withParent(BookEntryParentModel.create(craftAfritMiner.getId()))
                .withCondition(BookEntryReadConditionModel.create().withEntry(craftMaridID));
        var craftAncientMiner = this.add(new MinerAncientEntry(this).generate('r'));
        craftAncientMiner.withParent(BookEntryParentModel.create(craftMaridMiner.getId()))
                .withCondition(BookEntryReadConditionModel.create().withEntry(contactEldritchID));

        //Storage Entries
            //Start
        var craftStorageSystem = this.add(new StorageSystemEntry(this).generate('z'));
        craftStorageSystem.withParent(BookEntryParentModel.create(overview.getId()))
                .withCondition(BookEntryReadConditionModel.create().withEntry(craftFoliotID));
        var craftDimensionalMatrix = this.add(new DimensionalMatrixEntry(this).generate('a'));
        craftDimensionalMatrix.withParent(BookEntryParentModel.create(craftStorageSystem.getId()))
                .withCondition(BookEntryReadConditionModel.create().withEntry(craftDjinniID));
        var craftStorageControllerBase = this.add(new StorageControllerBaseEntry(this).generate('n'));
        craftStorageControllerBase.withParent(BookEntryParentModel.create(craftDimensionalMatrix.getId()).withLineReversed(true))
                .withCondition(BookEntryReadConditionModel.create().withEntry(craftDjinniID));
            //Stabilizer
        var craftStabilizerTier1 = this.add(new StabilizerTier1Entry(this).generate('i'));
        craftStabilizerTier1.withParent(BookEntryParentModel.create(craftStorageControllerBase.getId()))
                .withCondition(BookEntryReadConditionModel.create().withEntry(craftDjinniID));
        var craftStabilizerTier2 = this.add(new StabilizerTier2Entry(this).generate('j'));
        craftStabilizerTier2.withParent(BookEntryParentModel.create(craftStabilizerTier1.getId()))
                .withCondition(BookEntryReadConditionModel.create().withEntry(craftDjinniID));
        var craftStabilizerTier3 = this.add(new StabilizerTier3Entry(this).generate('k'));
        craftStabilizerTier3.withParent(BookEntryParentModel.create(craftStabilizerTier2.getId()))
                .withCondition(BookEntryReadConditionModel.create().withEntry(craftAfritID));
        var craftStabilizerTier4 = this.add(new StabilizerTier4Entry(this).generate('l'));
        craftStabilizerTier4.withParent(BookEntryParentModel.create(craftStabilizerTier3.getId()))
                .withCondition(BookEntryReadConditionModel.create().withEntry(craftMaridID));
        var craftStabilizerTier5 = this.add(new StabilizerTier5Entry(this).generate('á'));
        craftStabilizerTier5.withParent(BookEntryParentModel.create(craftStabilizerTier4.getId()))
                .withCondition(BookEntryReadConditionModel.create().withEntry(contactEldritchID));
            //Distance Access
        var craftStableWormhole = this.add(new StableWormholeEntry(this).generate('m'));
        craftStableWormhole.withParent(BookEntryParentModel.create(craftStorageControllerBase.getId()).withLineReversed(true))
                .withCondition(BookEntryReadConditionModel.create().withEntry(craftDjinniID));
        var craftStorageRemote = this.add(new StorageRemoteEntry(this).generate('o'));
        craftStorageRemote.withParent(BookEntryParentModel.create(craftStorageControllerBase.getId()))
                .withCondition(BookEntryReadConditionModel.create().withEntry(craftDjinniID));
            //Final
        var craftStabilizedStorage = this.add(new StabilizedStorageEntry(this).generate('ç'));
        craftStabilizedStorage.withParent(BookEntryParentModel.create(craftStabilizerTier5.getId()))
                .withCondition(BookEntryReadConditionModel.create().withEntry(contactEldritchID));
            //Satchels
        var craftSatchel = this.add(new BackpackSatchelEntry(this).generate('g'));
        craftSatchel.withParent(BookEntryParentModel.create(craftStorageSystem.getId()))
                .withCondition(BookEntryReadConditionModel.create().withEntry(craftFoliotID));
        var craftEnderSatchel = this.add(new EnderSatchelEntry(this).generate('ģ'));
        craftEnderSatchel.withParent(BookEntryParentModel.create(craftSatchel.getId()))
                .withCondition(BookEntryReadConditionModel.create().withEntry(craftDjinniID));
        var apprenticeRitualSatchel = this.add(new ApprenticeRitualSatchelEntry(this).generate('ĝ'));
        apprenticeRitualSatchel.withParent(craftSatchel)
                .withCondition(BookEntryReadConditionModel.create().withEntry(craftFoliotID));
        var artisanalRitualSatchel = this.add(new ArtisanalRitualSatchelEntry(this).generate('ğ'));
        artisanalRitualSatchel.withParent(apprenticeRitualSatchel)
                .withCondition(BookEntryReadConditionModel.create().withEntry(craftAfritID));

        var craftFragileSoulGem = this.add(new FragileSoulGemEntry(this).generate('é'));
        craftFragileSoulGem.withParent(BookEntryParentModel.create(overview.getId()))
                .withCondition(BookEntryReadConditionModel.create().withEntry(craftFoliotID));
        var craftSoulGem = this.add(new SoulGemEntry(this).generate('h'));
        craftSoulGem.withParent(BookEntryParentModel.create(craftFragileSoulGem.getId()))
                .withCondition(BookEntryReadConditionModel.create().withEntry(craftDjinniID));
        var craftFamiliarRing = this.add(new FamiliarRingEntry(this).generate('c'));
        craftFamiliarRing.withParent(BookEntryParentModel.create(craftSoulGem.getId()))
                .withCondition(BookEntryReadConditionModel.create().withEntry(craftDjinniID));
        var craftTrinityGem = this.add(new TrinityGemEntry(this).generate('Ť'));
        craftTrinityGem.withParent(BookEntryParentModel.create(craftSoulGem.getId()).withLineReversed(true))
                .withCondition(BookEntryReadConditionModel.create().withEntry(contactEldritchID));

        var craftKnowledgeTablet = this.add(new KnowledgeTabletEntry(this).generate('ã'));
        craftKnowledgeTablet.withParent(BookEntryParentModel.create(overview.getId()))
                .withCondition(BookEntryReadConditionModel.create().withEntry(craftFoliotID));
        var craftVitalityCompass = this.add(new VitalityCompassEntry(this).generate('Ж'));
        craftVitalityCompass.withParent(BookEntryParentModel.create(overview.getId()))
                .withCondition(BookEntryReadConditionModel.create().withEntry(craftFoliotID));
        var craftEntityWormhole = this.add(new EntityWormholeEntry(this).generate('ᑬ'));
        craftEntityWormhole.withParent(BookEntryParentModel.create(craftInfusedPickaxe.getId()))
                .withCondition(BookEntryReadConditionModel.create().withEntry(craftDjinniID));

        var craftIesniumSacrificialBowl = this.add(new IesniumSacrificialBowlEntry(this).generate('u'));
        craftIesniumSacrificialBowl.withParent(BookEntryParentModel.create(craftInfusedPickaxe.getId()))
                .withCondition(BookEntryReadConditionModel.create().withEntry(craftAfritID));
        var craftIesniumAnvil = this.add(new IesniumAnvilEntry(this).generate('A'));
        craftIesniumAnvil.withParent(BookEntryParentModel.create(craftInfusedPickaxe.getId()))
                .withCondition(BookEntryReadConditionModel.create().withEntry(craftMaridID));
        var trueSightStaff = this.add(new TrueSightStaffEntry(this).generate('F'));
        trueSightStaff.withParent(craftOtherworldGoggles).withParent(craftInfusedPickaxe)
                .withCondition(BookEntryReadConditionModel.create().withEntry(craftMaridID));

        var craftWildTrim = this.add(new WildTrimEntry(this).generate('w'));
        craftWildTrim.withParent(BookEntryParentModel.create(overview.getId()))
                .withCondition(BookEntryReadConditionModel.create().withEntry(contactWildID));
        var craftAmethyst = this.add(new BuddingAmethystEntry(this).generate('v'));
        craftAmethyst.withParent(BookEntryParentModel.create(craftWildTrim.getId()))
                .withCondition(BookEntryReadConditionModel.create().withEntry(contactWildID));
        var craftDeepslate = this.add(new ReinforcedDeepslateEntry(this).generate('s'));
        craftDeepslate.withParent(BookEntryParentModel.create(craftWildTrim.getId()))
                .withCondition(BookEntryReadConditionModel.create().withEntry(contactWildID));
        var craftBeeNest = this.add(new BeeNestEntry(this).generate('B'));
        craftBeeNest.withParent(BookEntryParentModel.create(craftWildTrim.getId()))
                .withCondition(BookEntryReadConditionModel.create().withEntry(contactWildID));
        var craftBell = this.add(new BellEntry(this).generate('H'));
        craftBell.withParent(BookEntryParentModel.create(craftWildTrim.getId()))
                .withCondition(BookEntryReadConditionModel.create().withEntry(contactWildID));
        var craftEldritchChalice = this.add(new EldritchChaliceEntry(this).generate('t'));
        craftEldritchChalice.withParent(BookEntryParentModel.create(craftIesniumSacrificialBowl.getId()))
                .withCondition(BookEntryReadConditionModel.create().withEntry(contactEldritchID));


        var craftMasterChalks = this.add(new MasterChalksEntry(this).generate('Č'));
        craftMasterChalks.withParent(BookEntryParentModel.create(overview.getId()))
                .withCondition(BookEntryReadConditionModel.create().withEntry(contactEldritchID));
        var repairRituals = this.add(new RepairEntry(this).generate('y'));
        repairRituals.withParent(BookEntryParentModel.create(overview.getId()))
                .withCondition(BookEntryReadConditionModel.create().withEntry(craftDjinniID));
    }

    private BookEntryModel makeReturnToRitualsEntry(CategoryEntryMap entryMap) {
        this.context().entry("return_to_rituals");

        return BookEntryModel.create(this.modLoc(this.context().categoryId() + "/" + this.context().entryId()), this.context().entryName())
                .withName("Return to Rituals Category")
                .withIcon(this.modLoc("textures/gui/book/robe.png"))
                .withCategoryToOpen(this.modLoc("rituals"))
                .withEntryBackground(1, 2)
                .withLocation(entryMap.get('9'));
    }
}
