package com.klikli_dev.occultism.datagen;

import com.klikli_dev.modonomicon.api.datagen.research.ResearchNodeRef;
import com.klikli_dev.modonomicon.api.datagen.research.SingleResearchSubProvider;
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

    @Override
    protected void generateResearch() {
        //region Getting Started
        this.node(GETTING_STARTED_INTRO, this.ingress()
                .onEntryViewedOnce(this.modLoc("getting_started/intro"))
                .declareFact("occultism/research/entry_viewed/getting_started/intro"));
        this.node(GETTING_STARTED_DIVINATION_ROD, this.ingress()
                .onEntryViewedOnce(this.modLoc("getting_started/divination_rod"))
                .declareFact("occultism/research/entry_viewed/getting_started/divination_rod"));
        //endregion

        //region Pentacles
        this.node(PENTACLES_OVERVIEW, this.ingress()
                .onEntryViewedOnce(this.modLoc("pentacles/overview"))
                .declareFact("occultism/research/entry_viewed/pentacles/overview"));
        this.node(PENTACLES_SUMMON_FOLIOT, this.ingress()
                .onEntryViewedOnce(this.modLoc("pentacles/summon_foliot"))
                .declareFact("occultism/research/entry_viewed/pentacles/summon_foliot"));
        this.node(PENTACLES_POSSESS_FOLIOT, this.ingress()
                .onEntryViewedOnce(this.modLoc("pentacles/possess_foliot"))
                .declareFact("occultism/research/entry_viewed/pentacles/possess_foliot"));
        this.node(PENTACLES_CRAFT_FOLIOT, this.ingress()
                .onEntryViewedOnce(this.modLoc("pentacles/craft_foliot"))
                .declareFact("occultism/research/entry_viewed/pentacles/craft_foliot"));
        this.node(PENTACLES_SUMMON_DJINNI, this.ingress()
                .onEntryViewedOnce(this.modLoc("pentacles/summon_djinni"))
                .declareFact("occultism/research/entry_viewed/pentacles/summon_djinni"));
        this.node(PENTACLES_POSSESS_DJINNI, this.ingress()
                .onEntryViewedOnce(this.modLoc("pentacles/possess_djinni"))
                .declareFact("occultism/research/entry_viewed/pentacles/possess_djinni"));
        this.node(PENTACLES_CRAFT_DJINNI, this.ingress()
                .onEntryViewedOnce(this.modLoc("pentacles/craft_djinni"))
                .declareFact("occultism/research/entry_viewed/pentacles/craft_djinni"));
        this.node(PENTACLES_SUMMON_UNBOUND_AFRIT, this.ingress()
                .onEntryViewedOnce(this.modLoc("pentacles/summon_unbound_afrit"))
                .declareFact("occultism/research/entry_viewed/pentacles/summon_unbound_afrit"));
        this.node(PENTACLES_POSSESS_UNBOUND_AFRIT, this.ingress()
                .onEntryViewedOnce(this.modLoc("pentacles/possess_unbound_afrit"))
                .declareFact("occultism/research/entry_viewed/pentacles/possess_unbound_afrit"));
        this.node(PENTACLES_SUMMON_AFRIT, this.ingress()
                .onEntryViewedOnce(this.modLoc("pentacles/summon_afrit"))
                .declareFact("occultism/research/entry_viewed/pentacles/summon_afrit"));
        this.node(PENTACLES_POSSESS_AFRIT, this.ingress()
                .onEntryViewedOnce(this.modLoc("pentacles/possess_afrit"))
                .declareFact("occultism/research/entry_viewed/pentacles/possess_afrit"));
        this.node(PENTACLES_CRAFT_AFRIT, this.ingress()
                .onEntryViewedOnce(this.modLoc("pentacles/craft_afrit"))
                .declareFact("occultism/research/entry_viewed/pentacles/craft_afrit"));
        this.node(PENTACLES_SUMMON_UNBOUND_MARID, this.ingress()
                .onEntryViewedOnce(this.modLoc("pentacles/summon_unbound_marid"))
                .declareFact("occultism/research/entry_viewed/pentacles/summon_unbound_marid"));
        this.node(PENTACLES_POSSESS_MARID, this.ingress()
                .onEntryViewedOnce(this.modLoc("pentacles/possess_marid"))
                .declareFact("occultism/research/entry_viewed/pentacles/possess_marid"));
        this.node(PENTACLES_CRAFT_MARID, this.ingress()
                .onEntryViewedOnce(this.modLoc("pentacles/craft_marid"))
                .declareFact("occultism/research/entry_viewed/pentacles/craft_marid"));
        this.node(PENTACLES_SUMMON_MARID, this.ingress()
                .onEntryViewedOnce(this.modLoc("pentacles/summon_marid"))
                .declareFact("occultism/research/entry_viewed/pentacles/summon_marid"));
        this.node(PENTACLES_CONTACT_WILD, this.ingress()
                .onEntryViewedOnce(this.modLoc("pentacles/contact_wild_spirit"))
                .declareFact("occultism/research/entry_viewed/pentacles/contact_wild_spirit"));
        this.node(PENTACLES_CONTACT_ELDRITCH, this.ingress()
                .onEntryViewedOnce(this.modLoc("pentacles/contact_eldritch_spirit"))
                .declareFact("occultism/research/entry_viewed/pentacles/contact_eldritch_spirit"));
        this.node(PENTACLES_DIDACTICS, this.ingress()
                .onEntryViewedOnce(this.modLoc("pentacles/didactics"))
                .declareFact("occultism/research/entry_viewed/pentacles/didactics"));
        this.node(PENTACLES_WHITE_CHALK, this.ingress()
                .onEntryViewedOnce(this.modLoc("pentacles/white_chalk"))
                .declareFact("occultism/research/entry_viewed/pentacles/white_chalk"));
        this.node(PENTACLES_LIGHT_GRAY_CHALK, this.ingress()
                .onEntryViewedOnce(this.modLoc("pentacles/light_gray_chalk"))
                .declareFact("occultism/research/entry_viewed/pentacles/light_gray_chalk"));
        this.node(PENTACLES_YELLOW_CHALK, this.ingress()
                .onEntryViewedOnce(this.modLoc("pentacles/yellow_chalk"))
                .declareFact("occultism/research/entry_viewed/pentacles/yellow_chalk"));
        this.node(PENTACLES_PURPLE_CHALK, this.ingress()
                .onEntryViewedOnce(this.modLoc("pentacles/purple_chalk"))
                .declareFact("occultism/research/entry_viewed/pentacles/purple_chalk"));
        this.node(PENTACLES_LIME_CHALK, this.ingress()
                .onEntryViewedOnce(this.modLoc("pentacles/lime_chalk"))
                .declareFact("occultism/research/entry_viewed/pentacles/lime_chalk"));
        this.node(PENTACLES_GREEN_CHALK, this.ingress()
                .onEntryViewedOnce(this.modLoc("pentacles/green_chalk"))
                .declareFact("occultism/research/entry_viewed/pentacles/green_chalk"));
        this.node(PENTACLES_LIGHT_BLUE_CHALK, this.ingress()
                .onEntryViewedOnce(this.modLoc("pentacles/light_blue_chalk"))
                .declareFact("occultism/research/entry_viewed/pentacles/light_blue_chalk"));
        this.node(PENTACLES_ORANGE_CHALK, this.ingress()
                .onEntryViewedOnce(this.modLoc("pentacles/orange_chalk"))
                .declareFact("occultism/research/entry_viewed/pentacles/orange_chalk"));
        this.node(PENTACLES_GRAY_CHALK, this.ingress()
                .onEntryViewedOnce(this.modLoc("pentacles/gray_chalk"))
                .declareFact("occultism/research/entry_viewed/pentacles/gray_chalk"));
        this.node(PENTACLES_RED_CHALK, this.ingress()
                .onEntryViewedOnce(this.modLoc("pentacles/red_chalk"))
                .declareFact("occultism/research/entry_viewed/pentacles/red_chalk"));
        this.node(PENTACLES_PINK_CHALK, this.ingress()
                .onEntryViewedOnce(this.modLoc("pentacles/pink_chalk"))
                .declareFact("occultism/research/entry_viewed/pentacles/pink_chalk"));
        this.node(PENTACLES_BLACK_CHALK, this.ingress()
                .onEntryViewedOnce(this.modLoc("pentacles/black_chalk"))
                .declareFact("occultism/research/entry_viewed/pentacles/black_chalk"));
        this.node(PENTACLES_BLUE_CHALK, this.ingress()
                .onEntryViewedOnce(this.modLoc("pentacles/blue_chalk"))
                .declareFact("occultism/research/entry_viewed/pentacles/blue_chalk"));
        this.node(PENTACLES_CYAN_CHALK, this.ingress()
                .onEntryViewedOnce(this.modLoc("pentacles/cyan_chalk"))
                .declareFact("occultism/research/entry_viewed/pentacles/cyan_chalk"));
        this.node(PENTACLES_BROWN_CHALK, this.ingress()
                .onEntryViewedOnce(this.modLoc("pentacles/brown_chalk"))
                .declareFact("occultism/research/entry_viewed/pentacles/brown_chalk"));
        this.node(PENTACLES_MAGENTA_CHALK, this.ingress()
                .onEntryViewedOnce(this.modLoc("pentacles/magenta_chalk"))
                .declareFact("occultism/research/entry_viewed/pentacles/magenta_chalk"));
        this.node(PENTACLES_RESURRECT_SPIRIT, this.ingress()
                .onEntryViewedOnce(this.modLoc("pentacles/resurrect_spirit"))
                .declareFact("occultism/research/entry_viewed/pentacles/resurrect_spirit"));
        this.node(PENTACLES_POSSESS_WEAK_BREEZE, this.ingress()
                .onEntryViewedOnce(this.modLoc("pentacles/possess_weak_breeze"))
                .declareFact("occultism/research/entry_viewed/pentacles/possess_weak_breeze"));
        this.node(PENTACLES_POSSESS_BREEZE, this.ingress()
                .onEntryViewedOnce(this.modLoc("pentacles/possess_breeze"))
                .declareFact("occultism/research/entry_viewed/pentacles/possess_breeze"));
        //endregion

        //region Binding Rituals
        this.node(BINDING_OVERVIEW, this.ingress()
                .onEntryViewedOnce(this.modLoc("binding_rituals/crafting_rituals_overview"))
                .declareFact("occultism/research/entry_viewed/binding_rituals/crafting_rituals_overview"));
        this.node(BINDING_STORAGE_SYSTEM, this.ingress()
                .onEntryViewedOnce(this.modLoc("binding_rituals/craft_storage_system"))
                .declareFact("occultism/research/entry_viewed/binding_rituals/craft_storage_system"));
        this.node(BINDING_REPAIR, this.ingress()
                .onEntryViewedOnce(this.modLoc("binding_rituals/repair"))
                .declareFact("occultism/research/entry_viewed/binding_rituals/repair"));
        //endregion

        //region Summoning Rituals
        this.node(SUMMONING_OVERVIEW, this.ingress()
                .onEntryViewedOnce(this.modLoc("summoning_rituals/summoning_rituals_overview"))
                .declareFact("occultism/research/entry_viewed/summoning_rituals/summoning_rituals_overview"));
        //endregion

        //region Possession Rituals
        this.node(POSSESSION_OVERVIEW, this.ingress()
                .onEntryViewedOnce(this.modLoc("possession_rituals/possession_rituals_overview"))
                .declareFact("occultism/research/entry_viewed/possession_rituals/possession_rituals_overview"));
        this.node(POSSESSION_POSSESS_WILD, this.ingress()
                .onEntryViewedOnce(this.modLoc("possession_rituals/possess_wild_spirit"))
                .declareFact("occultism/research/entry_viewed/possession_rituals/possess_wild_spirit"));
        //endregion

        //region Familiar Rituals
        this.node(FAMILIAR_OVERVIEW, this.ingress()
                .onEntryViewedOnce(this.modLoc("familiar_rituals/familiars_rituals_overview"))
                .declareFact("occultism/research/entry_viewed/familiar_rituals/familiars_rituals_overview"));
        this.node(FAMILIAR_RESURRECTION, this.ingress()
                .onEntryViewedOnce(this.modLoc("familiar_rituals/resurrect_spirit"))
                .declareFact("occultism/research/entry_viewed/familiar_rituals/resurrect_spirit"));
        //endregion

        //region Storage
        this.node(STORAGE_OVERVIEW, this.ingress()
                .onEntryViewedOnce(this.modLoc("storage/overview"))
                .declareFact("occultism/research/entry_viewed/storage/overview"));
        //endregion

        //region Rituals
        this.node(RITUALS_ITEM_USE, this.ingress()
                .onEntryViewedOnce(this.modLoc("rituals/item_use"))
                .declareFact("occultism/research/entry_viewed/rituals/item_use"));
        this.node(RITUALS_SACRIFICE, this.ingress()
                .onEntryViewedOnce(this.modLoc("rituals/sacrifice"))
                .declareFact("occultism/research/entry_viewed/rituals/sacrifice"));
        //endregion
    }

    /**
     * Creates a research node ref for a given entry path.
     */
    static ResearchNodeRef node(String path) {
        return ResearchNodeRef.of(Identifier.fromNamespaceAndPath("occultism", path));
    }
}
