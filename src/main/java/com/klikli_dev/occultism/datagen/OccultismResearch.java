package com.klikli_dev.occultism.datagen;

import com.klikli_dev.modonomicon.api.datagen.book.BookEntryModel;
import com.klikli_dev.modonomicon.api.datagen.research.ResearchNodeRef;
import com.klikli_dev.modonomicon.api.datagen.research.SingleResearchSubProvider;
import com.klikli_dev.occultism.datagen.book.*;
import com.klikli_dev.occultism.datagen.book.binding_rituals.CraftingOverviewEntry;
import com.klikli_dev.occultism.datagen.book.binding_rituals.RepairEntry;
import com.klikli_dev.occultism.datagen.book.binding_rituals.StorageSystemEntry;
import com.klikli_dev.occultism.datagen.book.familiar_rituals.FamiliarsRitualsOverviewEntry;
import com.klikli_dev.occultism.datagen.book.familiar_rituals.ResurrectFamiliarEntry;
import com.klikli_dev.occultism.datagen.book.getting_started.DivinationRodEntry;
import com.klikli_dev.occultism.datagen.book.getting_started.IntroEntry;
import com.klikli_dev.occultism.datagen.book.pentacles.*;
import com.klikli_dev.occultism.datagen.book.possession_rituals.PossessBreezeEntry;
import com.klikli_dev.occultism.datagen.book.possession_rituals.PossessWeakBreezeEntry;
import com.klikli_dev.occultism.datagen.book.possession_rituals.PossessionOverviewEntry;
import com.klikli_dev.occultism.datagen.book.rituals.ItemUseEntry;
import com.klikli_dev.occultism.datagen.book.rituals.SacrificeEntry;
import com.klikli_dev.occultism.datagen.book.storage_system.StorageOverviewEntry;
import com.klikli_dev.occultism.datagen.book.summoning_rituals.SummoningOverviewEntry;
import net.minecraft.resources.Identifier;

/**
 * Research bundle for the Dictionary of Spirits book.
 * <p>
 * Declares one fact + hook + node per entry that is used as a read-gate elsewhere in the book.
 * The hook trigger type is {@code modonomicon:entry_viewed_once} and the target is the entry's
 * {@link Identifier}. When the entry is viewed for the first time the hook grants the fact, which
 * completes the node, which in turn satisfies the {@code BookResearchNodeUnlockedConditionModel}
 * that replaced the old {@code BookEntryReadConditionModel}.
 */
public class OccultismResearch extends SingleResearchSubProvider {

    //region Getting Started
    public static final ResearchNodeRef GETTING_STARTED_INTRO = node("research/entry_viewed/getting_started/intro");
    public static final ResearchNodeRef GETTING_STARTED_DIVINATION_ROD = node("research/entry_viewed/getting_started/divination_rod");
    //endregion

    //region Pentacles
    public static final ResearchNodeRef PENTACLES_OVERVIEW = node("research/entry_viewed/pentacles/overview");
    public static final ResearchNodeRef PENTACLES_SUMMON_FOLIOT = node("research/entry_viewed/pentacles/summon_foliot");
    public static final ResearchNodeRef PENTACLES_POSSESS_FOLIOT = node("research/entry_viewed/pentacles/possess_foliot");
    public static final ResearchNodeRef PENTACLES_CRAFT_FOLIOT = node("research/entry_viewed/pentacles/craft_foliot");
    public static final ResearchNodeRef PENTACLES_SUMMON_DJINNI = node("research/entry_viewed/pentacles/summon_djinni");
    public static final ResearchNodeRef PENTACLES_POSSESS_DJINNI = node("research/entry_viewed/pentacles/possess_djinni");
    public static final ResearchNodeRef PENTACLES_CRAFT_DJINNI = node("research/entry_viewed/pentacles/craft_djinni");
    public static final ResearchNodeRef PENTACLES_SUMMON_UNBOUND_AFRIT = node("research/entry_viewed/pentacles/summon_unbound_afrit");
    public static final ResearchNodeRef PENTACLES_POSSESS_UNBOUND_AFRIT = node("research/entry_viewed/pentacles/possess_unbound_afrit");
    public static final ResearchNodeRef PENTACLES_SUMMON_AFRIT = node("research/entry_viewed/pentacles/summon_afrit");
    public static final ResearchNodeRef PENTACLES_POSSESS_AFRIT = node("research/entry_viewed/pentacles/possess_afrit");
    public static final ResearchNodeRef PENTACLES_CRAFT_AFRIT = node("research/entry_viewed/pentacles/craft_afrit");
    public static final ResearchNodeRef PENTACLES_SUMMON_UNBOUND_MARID = node("research/entry_viewed/pentacles/summon_unbound_marid");
    public static final ResearchNodeRef PENTACLES_POSSESS_MARID = node("research/entry_viewed/pentacles/possess_marid");
    public static final ResearchNodeRef PENTACLES_CRAFT_MARID = node("research/entry_viewed/pentacles/craft_marid");
    public static final ResearchNodeRef PENTACLES_SUMMON_MARID = node("research/entry_viewed/pentacles/summon_marid");
    public static final ResearchNodeRef PENTACLES_CONTACT_WILD = node("research/entry_viewed/pentacles/contact_wild_spirit");
    public static final ResearchNodeRef PENTACLES_CONTACT_ELDRITCH = node("research/entry_viewed/pentacles/contact_eldritch_spirit");
    public static final ResearchNodeRef PENTACLES_DIDACTICS = node("research/entry_viewed/pentacles/didactics");
    public static final ResearchNodeRef PENTACLES_WHITE_CHALK = node("research/entry_viewed/pentacles/white_chalk");
    public static final ResearchNodeRef PENTACLES_LIGHT_GRAY_CHALK = node("research/entry_viewed/pentacles/light_gray_chalk");
    public static final ResearchNodeRef PENTACLES_YELLOW_CHALK = node("research/entry_viewed/pentacles/yellow_chalk");
    public static final ResearchNodeRef PENTACLES_PURPLE_CHALK = node("research/entry_viewed/pentacles/purple_chalk");
    public static final ResearchNodeRef PENTACLES_LIME_CHALK = node("research/entry_viewed/pentacles/lime_chalk");
    public static final ResearchNodeRef PENTACLES_GREEN_CHALK = node("research/entry_viewed/pentacles/green_chalk");
    public static final ResearchNodeRef PENTACLES_LIGHT_BLUE_CHALK = node("research/entry_viewed/pentacles/light_blue_chalk");
    public static final ResearchNodeRef PENTACLES_ORANGE_CHALK = node("research/entry_viewed/pentacles/orange_chalk");
    public static final ResearchNodeRef PENTACLES_GRAY_CHALK = node("research/entry_viewed/pentacles/gray_chalk");
    public static final ResearchNodeRef PENTACLES_RED_CHALK = node("research/entry_viewed/pentacles/red_chalk");
    public static final ResearchNodeRef PENTACLES_PINK_CHALK = node("research/entry_viewed/pentacles/pink_chalk");
    public static final ResearchNodeRef PENTACLES_BLACK_CHALK = node("research/entry_viewed/pentacles/black_chalk");
    public static final ResearchNodeRef PENTACLES_BLUE_CHALK = node("research/entry_viewed/pentacles/blue_chalk");
    public static final ResearchNodeRef PENTACLES_CYAN_CHALK = node("research/entry_viewed/pentacles/cyan_chalk");
    public static final ResearchNodeRef PENTACLES_BROWN_CHALK = node("research/entry_viewed/pentacles/brown_chalk");
    public static final ResearchNodeRef PENTACLES_MAGENTA_CHALK = node("research/entry_viewed/pentacles/magenta_chalk");
    public static final ResearchNodeRef PENTACLES_RESURRECT_SPIRIT = node("research/entry_viewed/pentacles/resurrect_spirit");
    public static final ResearchNodeRef PENTACLES_POSSESS_WEAK_BREEZE = node("research/entry_viewed/pentacles/possess_weak_breeze");
    public static final ResearchNodeRef PENTACLES_POSSESS_BREEZE = node("research/entry_viewed/pentacles/possess_breeze");
    //endregion

    //region Binding Rituals
    public static final ResearchNodeRef BINDING_OVERVIEW = node("research/entry_viewed/binding_rituals/crafting_rituals_overview");
    public static final ResearchNodeRef BINDING_STORAGE_SYSTEM = node("research/entry_viewed/binding_rituals/craft_storage_system");
    public static final ResearchNodeRef BINDING_REPAIR = node("research/entry_viewed/binding_rituals/repair");
    //endregion

    //region Summoning Rituals
    public static final ResearchNodeRef SUMMONING_OVERVIEW = node("research/entry_viewed/summoning_rituals/summoning_rituals_overview");
    //endregion

    //region Possession Rituals
    public static final ResearchNodeRef POSSESSION_OVERVIEW = node("research/entry_viewed/possession_rituals/possession_rituals_overview");
    public static final ResearchNodeRef POSSESSION_POSSESS_WILD = node("research/entry_viewed/possession_rituals/possess_wild_spirit");
    //endregion

    //region Familiar Rituals
    public static final ResearchNodeRef FAMILIAR_OVERVIEW = node("research/entry_viewed/familiar_rituals/familiars_rituals_overview");
    public static final ResearchNodeRef FAMILIAR_RESURRECTION = node("research/entry_viewed/familiar_rituals/resurrect_spirit");
    //endregion

    //region Storage
    public static final ResearchNodeRef STORAGE_OVERVIEW = node("research/entry_viewed/storage/overview");
    //endregion

    //region Rituals
    public static final ResearchNodeRef RITUALS_ITEM_USE = node("research/entry_viewed/rituals/item_use");
    public static final ResearchNodeRef RITUALS_SACRIFICE = node("research/entry_viewed/rituals/sacrifice");
    //endregion

    public OccultismResearch(String modId) {
        super("dictionary_of_spirits", modId);
    }

    /**
     * Creates a minimal BookEntryModel with just the correct name for tooltip purposes.
     * Used for cross-category researchNodeEntryViewedOnce calls where the entry model
     * is not available as a local variable.
     */
    public static BookEntryModel entryModel(String categoryId, String entryId) {
        return BookEntryModel.create(
                Identifier.fromNamespaceAndPath("occultism", categoryId + "/" + entryId),
                "book.occultism.dictionary_of_spirits." + categoryId + "." + entryId + ".name"
        );
    }

    @Override
    protected void generateResearch() {
        //region Getting Started
        this.node(GETTING_STARTED_INTRO, this.ingress()
                .onEntryViewedOnce(this.modLoc(GettingStartedCategory.CATEGORY_ID + "/" + IntroEntry.ENTRY_ID))
                .declareFact("occultism/research/entry_viewed/getting_started/intro"));
        this.node(GETTING_STARTED_DIVINATION_ROD, this.ingress()
                .onEntryViewedOnce(this.modLoc(GettingStartedCategory.CATEGORY_ID + "/" + DivinationRodEntry.ENTRY_ID))
                .declareFact("occultism/research/entry_viewed/getting_started/divination_rod"));
        //endregion

        //region Pentacles
        this.node(PENTACLES_OVERVIEW, this.ingress()
                .onEntryViewedOnce(this.modLoc(PentaclesCategory.CATEGORY_ID + "/" + PentaclesOverviewEntry.ENTRY_ID))
                .declareFact("occultism/research/entry_viewed/pentacles/overview"));
        this.node(PENTACLES_SUMMON_FOLIOT, this.ingress()
                .onEntryViewedOnce(this.modLoc(PentaclesCategory.CATEGORY_ID + "/" + SummonFoliotEntry.ENTRY_ID))
                .declareFact("occultism/research/entry_viewed/pentacles/summon_foliot"));
        this.node(PENTACLES_POSSESS_FOLIOT, this.ingress()
                .onEntryViewedOnce(this.modLoc(PentaclesCategory.CATEGORY_ID + "/" + PossessFoliotEntry.ENTRY_ID))
                .declareFact("occultism/research/entry_viewed/pentacles/possess_foliot"));
        this.node(PENTACLES_CRAFT_FOLIOT, this.ingress()
                .onEntryViewedOnce(this.modLoc(PentaclesCategory.CATEGORY_ID + "/" + CraftFoliotEntry.ENTRY_ID))
                .declareFact("occultism/research/entry_viewed/pentacles/craft_foliot"));
        this.node(PENTACLES_SUMMON_DJINNI, this.ingress()
                .onEntryViewedOnce(this.modLoc(PentaclesCategory.CATEGORY_ID + "/" + SummonDjinniEntry.ENTRY_ID))
                .declareFact("occultism/research/entry_viewed/pentacles/summon_djinni"));
        this.node(PENTACLES_POSSESS_DJINNI, this.ingress()
                .onEntryViewedOnce(this.modLoc(PentaclesCategory.CATEGORY_ID + "/" + PossessDjinniEntry.ENTRY_ID))
                .declareFact("occultism/research/entry_viewed/pentacles/possess_djinni"));
        this.node(PENTACLES_CRAFT_DJINNI, this.ingress()
                .onEntryViewedOnce(this.modLoc(PentaclesCategory.CATEGORY_ID + "/" + CraftDjinniEntry.ENTRY_ID))
                .declareFact("occultism/research/entry_viewed/pentacles/craft_djinni"));
        this.node(PENTACLES_SUMMON_UNBOUND_AFRIT, this.ingress()
                .onEntryViewedOnce(this.modLoc(PentaclesCategory.CATEGORY_ID + "/" + SummonUnboundAfritEntry.ENTRY_ID))
                .declareFact("occultism/research/entry_viewed/pentacles/summon_unbound_afrit"));
        this.node(PENTACLES_POSSESS_UNBOUND_AFRIT, this.ingress()
                .onEntryViewedOnce(this.modLoc(PentaclesCategory.CATEGORY_ID + "/" + PossessUnboundAfritEntry.ENTRY_ID))
                .declareFact("occultism/research/entry_viewed/pentacles/possess_unbound_afrit"));
        this.node(PENTACLES_SUMMON_AFRIT, this.ingress()
                .onEntryViewedOnce(this.modLoc(PentaclesCategory.CATEGORY_ID + "/" + SummonAfritEntry.ENTRY_ID))
                .declareFact("occultism/research/entry_viewed/pentacles/summon_afrit"));
        this.node(PENTACLES_POSSESS_AFRIT, this.ingress()
                .onEntryViewedOnce(this.modLoc(PentaclesCategory.CATEGORY_ID + "/" + PossessAfritEntry.ENTRY_ID))
                .declareFact("occultism/research/entry_viewed/pentacles/possess_afrit"));
        this.node(PENTACLES_CRAFT_AFRIT, this.ingress()
                .onEntryViewedOnce(this.modLoc(PentaclesCategory.CATEGORY_ID + "/" + CraftAfritEntry.ENTRY_ID))
                .declareFact("occultism/research/entry_viewed/pentacles/craft_afrit"));
        this.node(PENTACLES_SUMMON_UNBOUND_MARID, this.ingress()
                .onEntryViewedOnce(this.modLoc(PentaclesCategory.CATEGORY_ID + "/" + SummonUnboundMaridEntry.ENTRY_ID))
                .declareFact("occultism/research/entry_viewed/pentacles/summon_unbound_marid"));
        this.node(PENTACLES_POSSESS_MARID, this.ingress()
                .onEntryViewedOnce(this.modLoc(PentaclesCategory.CATEGORY_ID + "/" + PossessMaridEntry.ENTRY_ID))
                .declareFact("occultism/research/entry_viewed/pentacles/possess_marid"));
        this.node(PENTACLES_CRAFT_MARID, this.ingress()
                .onEntryViewedOnce(this.modLoc(PentaclesCategory.CATEGORY_ID + "/" + CraftMaridEntry.ENTRY_ID))
                .declareFact("occultism/research/entry_viewed/pentacles/craft_marid"));
        this.node(PENTACLES_SUMMON_MARID, this.ingress()
                .onEntryViewedOnce(this.modLoc(PentaclesCategory.CATEGORY_ID + "/" + SummonMaridEntry.ENTRY_ID))
                .declareFact("occultism/research/entry_viewed/pentacles/summon_marid"));
        this.node(PENTACLES_CONTACT_WILD, this.ingress()
                .onEntryViewedOnce(this.modLoc(PentaclesCategory.CATEGORY_ID + "/" + ContactWildSpiritEntry.ENTRY_ID))
                .declareFact("occultism/research/entry_viewed/pentacles/contact_wild_spirit"));
        this.node(PENTACLES_CONTACT_ELDRITCH, this.ingress()
                .onEntryViewedOnce(this.modLoc(PentaclesCategory.CATEGORY_ID + "/" + ContactEldritchSpiritEntry.ENTRY_ID))
                .declareFact("occultism/research/entry_viewed/pentacles/contact_eldritch_spirit"));
        this.node(PENTACLES_DIDACTICS, this.ingress()
                .onEntryViewedOnce(this.modLoc(PentaclesCategory.CATEGORY_ID + "/" + DidacticsEntry.ENTRY_ID))
                .declareFact("occultism/research/entry_viewed/pentacles/didactics"));
        this.node(PENTACLES_WHITE_CHALK, this.ingress()
                .onEntryViewedOnce(this.modLoc(PentaclesCategory.CATEGORY_ID + "/" + WhiteChalkEntry.ENTRY_ID))
                .declareFact("occultism/research/entry_viewed/pentacles/white_chalk"));
        this.node(PENTACLES_LIGHT_GRAY_CHALK, this.ingress()
                .onEntryViewedOnce(this.modLoc(PentaclesCategory.CATEGORY_ID + "/" + LightGrayChalkEntry.ENTRY_ID))
                .declareFact("occultism/research/entry_viewed/pentacles/light_gray_chalk"));
        this.node(PENTACLES_YELLOW_CHALK, this.ingress()
                .onEntryViewedOnce(this.modLoc(PentaclesCategory.CATEGORY_ID + "/" + YellowChalkEntry.ENTRY_ID))
                .declareFact("occultism/research/entry_viewed/pentacles/yellow_chalk"));
        this.node(PENTACLES_PURPLE_CHALK, this.ingress()
                .onEntryViewedOnce(this.modLoc(PentaclesCategory.CATEGORY_ID + "/" + PurpleChalkEntry.ENTRY_ID))
                .declareFact("occultism/research/entry_viewed/pentacles/purple_chalk"));
        this.node(PENTACLES_LIME_CHALK, this.ingress()
                .onEntryViewedOnce(this.modLoc(PentaclesCategory.CATEGORY_ID + "/" + LimeChalkEntry.ENTRY_ID))
                .declareFact("occultism/research/entry_viewed/pentacles/lime_chalk"));
        this.node(PENTACLES_GREEN_CHALK, this.ingress()
                .onEntryViewedOnce(this.modLoc(PentaclesCategory.CATEGORY_ID + "/" + GreenChalkEntry.ENTRY_ID))
                .declareFact("occultism/research/entry_viewed/pentacles/green_chalk"));
        this.node(PENTACLES_LIGHT_BLUE_CHALK, this.ingress()
                .onEntryViewedOnce(this.modLoc(PentaclesCategory.CATEGORY_ID + "/" + LightBlueChalkEntry.ENTRY_ID))
                .declareFact("occultism/research/entry_viewed/pentacles/light_blue_chalk"));
        this.node(PENTACLES_ORANGE_CHALK, this.ingress()
                .onEntryViewedOnce(this.modLoc(PentaclesCategory.CATEGORY_ID + "/" + OrangeChalkEntry.ENTRY_ID))
                .declareFact("occultism/research/entry_viewed/pentacles/orange_chalk"));
        this.node(PENTACLES_GRAY_CHALK, this.ingress()
                .onEntryViewedOnce(this.modLoc(PentaclesCategory.CATEGORY_ID + "/" + GrayChalkEntry.ENTRY_ID))
                .declareFact("occultism/research/entry_viewed/pentacles/gray_chalk"));
        this.node(PENTACLES_RED_CHALK, this.ingress()
                .onEntryViewedOnce(this.modLoc(PentaclesCategory.CATEGORY_ID + "/" + RedChalkEntry.ENTRY_ID))
                .declareFact("occultism/research/entry_viewed/pentacles/red_chalk"));
        this.node(PENTACLES_PINK_CHALK, this.ingress()
                .onEntryViewedOnce(this.modLoc(PentaclesCategory.CATEGORY_ID + "/" + PinkChalkEntry.ENTRY_ID))
                .declareFact("occultism/research/entry_viewed/pentacles/pink_chalk"));
        this.node(PENTACLES_BLACK_CHALK, this.ingress()
                .onEntryViewedOnce(this.modLoc(PentaclesCategory.CATEGORY_ID + "/" + BlackChalkEntry.ENTRY_ID))
                .declareFact("occultism/research/entry_viewed/pentacles/black_chalk"));
        this.node(PENTACLES_BLUE_CHALK, this.ingress()
                .onEntryViewedOnce(this.modLoc(PentaclesCategory.CATEGORY_ID + "/" + BlueChalkEntry.ENTRY_ID))
                .declareFact("occultism/research/entry_viewed/pentacles/blue_chalk"));
        this.node(PENTACLES_CYAN_CHALK, this.ingress()
                .onEntryViewedOnce(this.modLoc(PentaclesCategory.CATEGORY_ID + "/" + CyanChalkEntry.ENTRY_ID))
                .declareFact("occultism/research/entry_viewed/pentacles/cyan_chalk"));
        this.node(PENTACLES_BROWN_CHALK, this.ingress()
                .onEntryViewedOnce(this.modLoc(PentaclesCategory.CATEGORY_ID + "/" + BrownChalkEntry.ENTRY_ID))
                .declareFact("occultism/research/entry_viewed/pentacles/brown_chalk"));
        this.node(PENTACLES_MAGENTA_CHALK, this.ingress()
                .onEntryViewedOnce(this.modLoc(PentaclesCategory.CATEGORY_ID + "/" + MagentaChalkEntry.ENTRY_ID))
                .declareFact("occultism/research/entry_viewed/pentacles/magenta_chalk"));
        this.node(PENTACLES_RESURRECT_SPIRIT, this.ingress()
                .onEntryViewedOnce(this.modLoc(PentaclesCategory.CATEGORY_ID + "/" + ResurrectSpiritEntry.ENTRY_ID))
                .declareFact("occultism/research/entry_viewed/pentacles/resurrect_spirit"));
        this.node(PENTACLES_POSSESS_WEAK_BREEZE, this.ingress()
                .onEntryViewedOnce(this.modLoc(PossessionRitualsCategory.CATEGORY_ID + "/" + PossessWeakBreezeEntry.ENTRY_ID))
                .declareFact("occultism/research/entry_viewed/pentacles/possess_weak_breeze"));
        this.node(PENTACLES_POSSESS_BREEZE, this.ingress()
                .onEntryViewedOnce(this.modLoc(PossessionRitualsCategory.CATEGORY_ID + "/" + PossessBreezeEntry.ENTRY_ID))
                .declareFact("occultism/research/entry_viewed/pentacles/possess_breeze"));
        //endregion

        //region Binding Rituals
        this.node(BINDING_OVERVIEW, this.ingress()
                .onEntryViewedOnce(this.modLoc(BindingRitualsCategory.CATEGORY_ID + "/" + CraftingOverviewEntry.ENTRY_ID))
                .declareFact("occultism/research/entry_viewed/binding_rituals/crafting_rituals_overview"));
        this.node(BINDING_STORAGE_SYSTEM, this.ingress()
                .onEntryViewedOnce(this.modLoc(BindingRitualsCategory.CATEGORY_ID + "/" + StorageSystemEntry.ENTRY_ID))
                .declareFact("occultism/research/entry_viewed/binding_rituals/craft_storage_system"));
        this.node(BINDING_REPAIR, this.ingress()
                .onEntryViewedOnce(this.modLoc(BindingRitualsCategory.CATEGORY_ID + "/" + RepairEntry.ENTRY_ID))
                .declareFact("occultism/research/entry_viewed/binding_rituals/repair"));
        //endregion

        //region Summoning Rituals
        this.node(SUMMONING_OVERVIEW, this.ingress()
                .onEntryViewedOnce(this.modLoc(SummoningRitualCategory.CATEGORY_ID + "/" + SummoningOverviewEntry.ENTRY_ID))
                .declareFact("occultism/research/entry_viewed/summoning_rituals/summoning_rituals_overview"));
        //endregion

        //region Possession Rituals
        this.node(POSSESSION_OVERVIEW, this.ingress()
                .onEntryViewedOnce(this.modLoc(PossessionRitualsCategory.CATEGORY_ID + "/" + PossessionOverviewEntry.ENTRY_ID))
                .declareFact("occultism/research/entry_viewed/possession_rituals/possession_rituals_overview"));
        this.node(POSSESSION_POSSESS_WILD, this.ingress()
                .onEntryViewedOnce(this.modLoc(PossessionRitualsCategory.CATEGORY_ID + "/possess_wild_spirit"))
                .declareFact("occultism/research/entry_viewed/possession_rituals/possess_wild_spirit"));
        //endregion

        //region Familiar Rituals
        this.node(FAMILIAR_OVERVIEW, this.ingress()
                .onEntryViewedOnce(this.modLoc(FamiliarRitualsCategory.CATEGORY_ID + "/" + FamiliarsRitualsOverviewEntry.ENTRY_ID))
                .declareFact("occultism/research/entry_viewed/familiar_rituals/familiars_rituals_overview"));
        this.node(FAMILIAR_RESURRECTION, this.ingress()
                .onEntryViewedOnce(this.modLoc(FamiliarRitualsCategory.CATEGORY_ID + "/" + ResurrectFamiliarEntry.ENTRY_ID))
                .declareFact("occultism/research/entry_viewed/familiar_rituals/resurrect_spirit"));
        //endregion

        //region Storage
        this.node(STORAGE_OVERVIEW, this.ingress()
                .onEntryViewedOnce(this.modLoc(StorageCategory.CATEGORY_ID + "/" + StorageOverviewEntry.ENTRY_ID))
                .declareFact("occultism/research/entry_viewed/storage/overview"));
        //endregion

        //region Rituals
        this.node(RITUALS_ITEM_USE, this.ingress()
                .onEntryViewedOnce(this.modLoc(RitualsCategory.CATEGORY_ID + "/" + ItemUseEntry.ENTRY_ID))
                .declareFact("occultism/research/entry_viewed/rituals/item_use"));
        this.node(RITUALS_SACRIFICE, this.ingress()
                .onEntryViewedOnce(this.modLoc(RitualsCategory.CATEGORY_ID + "/" + SacrificeEntry.ENTRY_ID))
                .declareFact("occultism/research/entry_viewed/rituals/sacrifice"));
        //endregion

        //region Node names
        this.researchNodeName(GETTING_STARTED_INTRO, "About");
        this.researchNodeName(GETTING_STARTED_DIVINATION_ROD, "Divination Rod");

        this.researchNodeName(PENTACLES_OVERVIEW, "On Pentacles");
        this.researchNodeName(PENTACLES_SUMMON_FOLIOT, "Aviar's Circle");
        this.researchNodeName(PENTACLES_POSSESS_FOLIOT, "Hedyrin's Lure");
        this.researchNodeName(PENTACLES_CRAFT_FOLIOT, "Eziveus' Spectral Compulsion");
        this.researchNodeName(PENTACLES_SUMMON_DJINNI, "Ophyx' Calling");
        this.researchNodeName(PENTACLES_POSSESS_DJINNI, "Ihagan's Enthrallment");
        this.researchNodeName(PENTACLES_CRAFT_DJINNI, "Strigeor's Higher Binding");
        this.researchNodeName(PENTACLES_SUMMON_UNBOUND_AFRIT, "Kandar's Open Conjure");
        this.researchNodeName(PENTACLES_POSSESS_UNBOUND_AFRIT, "Odus' Open Convocation");
        this.researchNodeName(PENTACLES_SUMMON_AFRIT, "Abras' Conjure");
        this.researchNodeName(PENTACLES_POSSESS_AFRIT, "Posuc's Convocation");
        this.researchNodeName(PENTACLES_CRAFT_AFRIT, "Sevira's Permanent Confinement");
        this.researchNodeName(PENTACLES_SUMMON_UNBOUND_MARID, "Tibira's Attraction");
        this.researchNodeName(PENTACLES_POSSESS_MARID, "Xeovrenth Adjure");
        this.researchNodeName(PENTACLES_CRAFT_MARID, "Uphyxes Inverted Tower");
        this.researchNodeName(PENTACLES_SUMMON_MARID, "Fatma's Incentivized Attraction");
        this.researchNodeName(PENTACLES_CONTACT_WILD, "Osorin's Unbound Calling");
        this.researchNodeName(PENTACLES_CONTACT_ELDRITCH, "Ronaza's Contact");
        this.researchNodeName(PENTACLES_DIDACTICS, "Reading this Section");
        this.researchNodeName(PENTACLES_WHITE_CHALK, "The Most Basic Chalk");
        this.researchNodeName(PENTACLES_LIGHT_GRAY_CHALK, "Decent Foundation Chalk");
        this.researchNodeName(PENTACLES_YELLOW_CHALK, "The Chalk of Possession");
        this.researchNodeName(PENTACLES_PURPLE_CHALK, "The Chalk of Infusion");
        this.researchNodeName(PENTACLES_LIME_CHALK, "The Knowledge Chalk");
        this.researchNodeName(PENTACLES_GREEN_CHALK, "The Plant Chalk");
        this.researchNodeName(PENTACLES_LIGHT_BLUE_CHALK, "The Glacial Chalk");
        this.researchNodeName(PENTACLES_ORANGE_CHALK, "The Tangy Chalk");
        this.researchNodeName(PENTACLES_GRAY_CHALK, "Improved Foundation Chalk");
        this.researchNodeName(PENTACLES_RED_CHALK, "The Afrit Control Chalk");
        this.researchNodeName(PENTACLES_PINK_CHALK, "The Meat Chalk");
        this.researchNodeName(PENTACLES_BLACK_CHALK, "Perfect Foundation Chalk");
        this.researchNodeName(PENTACLES_BLUE_CHALK, "The Chalk of The Seven Seas");
        this.researchNodeName(PENTACLES_CYAN_CHALK, "The Chalk From Ancients");
        this.researchNodeName(PENTACLES_BROWN_CHALK, "The Cruelty Chalk");
        this.researchNodeName(PENTACLES_MAGENTA_CHALK, "The Dragon Chalk");
        this.researchNodeName(PENTACLES_RESURRECT_SPIRIT, "Susje's Simple Circle");
        this.researchNodeName(PENTACLES_POSSESS_WEAK_BREEZE, "The first key");
        this.researchNodeName(PENTACLES_POSSESS_BREEZE, "In the chamber");

        this.researchNodeName(BINDING_OVERVIEW, "Binding Rituals");
        this.researchNodeName(BINDING_STORAGE_SYSTEM, "Magic Storage");
        this.researchNodeName(BINDING_REPAIR, "Repair Rituals");

        this.researchNodeName(SUMMONING_OVERVIEW, "Summoning Rituals");

        this.researchNodeName(POSSESSION_OVERVIEW, "Possession Rituals");
        this.researchNodeName(POSSESSION_POSSESS_WILD, "Possess Wild Spirit");

        this.researchNodeName(FAMILIAR_OVERVIEW, "Familiar Rituals");
        this.researchNodeName(FAMILIAR_RESURRECTION, "Resurrecting Familiars");

        this.researchNodeName(STORAGE_OVERVIEW, "Magic Storage");

        this.researchNodeName(RITUALS_ITEM_USE, "Item Use");
        this.researchNodeName(RITUALS_SACRIFICE, "Sacrifices");
        //endregion
    }

    /**
     * Creates a research node ref for a given entry path.
     */
    static ResearchNodeRef node(String path) {
        return ResearchNodeRef.of(Identifier.fromNamespaceAndPath("occultism", path));
    }
}
