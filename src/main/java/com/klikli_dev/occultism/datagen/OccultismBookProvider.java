package com.klikli_dev.occultism.datagen;

import com.klikli_dev.modonomicon.api.ModonomiconAPI;
import com.klikli_dev.modonomicon.api.datagen.CategoryEntryMap;
import com.klikli_dev.modonomicon.api.datagen.EntryBackground;
import com.klikli_dev.modonomicon.api.datagen.ModonomiconLanguageProvider;
import com.klikli_dev.modonomicon.api.datagen.SingleBookSubProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookCategoryModel;
import com.klikli_dev.modonomicon.api.datagen.book.BookEntryModel;
import com.klikli_dev.modonomicon.api.datagen.book.BookEntryParentModel;
import com.klikli_dev.modonomicon.api.datagen.book.BookModel;
import com.klikli_dev.modonomicon.api.datagen.book.condition.BookEntryReadConditionModel;
import com.klikli_dev.modonomicon.api.datagen.book.condition.BookOrConditionModel;
import com.klikli_dev.modonomicon.api.datagen.book.condition.BookTrueConditionModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookEntityPageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookTextPageModel;
import com.klikli_dev.occultism.Occultism;
import com.klikli_dev.occultism.datagen.book.*;
import com.klikli_dev.occultism.datagen.book.pentacles.*;
import com.klikli_dev.occultism.integration.modonomicon.pages.BookRitualRecipePageModel;
import com.klikli_dev.occultism.registry.OccultismItems;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;

public class OccultismBookProvider extends SingleBookSubProvider {

    public static final String COLOR_PURPLE = "ad03fc";

    public OccultismBookProvider(ModonomiconLanguageProvider lang) {
        super("dictionary_of_spirits", Occultism.MODID, lang);
    }

    @Override
    protected void registerDefaultMacros() {
        //currently none
    }

    @Override
    protected void generateCategories() {
        int sortNum = 1;
        var gettingStartedCategory = this.add(new GettingStartedCategory(this).generate().withSortNumber(sortNum++));
        var spiritsCategory = this.add(this.makeSpiritsSubcategory().withSortNumber(sortNum++));
        var ritualsCategory = this.add(this.makeRitualsCategory().withSortNumber(sortNum++));

        var pentaclesCategory = this.add(new PentaclesCategory(this).generate().withSortNumber(sortNum++));

        var summoningRitualsCategory = this.add(new SummoningRitualCategory(this).generate().withSortNumber(sortNum++));
        summoningRitualsCategory.withCondition(BookEntryReadConditionModel.create().withEntry(this.modLoc("pentacles/summon_foliot")));
        var possessionRitualsCategory = this.add(this.makePossessionRitualsSubcategory().withSortNumber(sortNum++));
        possessionRitualsCategory.withCondition(BookEntryReadConditionModel.create().withEntry(this.modLoc("pentacles/possess_foliot")));
        var familiarRitualsCategory = this.add(new FamiliarRitualsCategory(this).generate().withSortNumber(sortNum++));
        familiarRitualsCategory.withCondition(BookEntryReadConditionModel.create().withEntry(this.modLoc("pentacles/possess_foliot")));
        var craftingRitualsCategory = this.add(new BindingRitualsCategory(this).generate().withSortNumber(sortNum++));
        craftingRitualsCategory.withCondition(BookEntryReadConditionModel.create().withEntry(this.modLoc("pentacles/craft_foliot")));

        var storageCategory = this.add(new StorageCategory(this).generate().withSortNumber(sortNum++));
        storageCategory.withCondition(BookOrConditionModel.create().withChildren(
                BookEntryReadConditionModel.create().withEntry(this.modLoc("crafting_rituals/craft_dimensional_matrix")),
                BookEntryReadConditionModel.create().withEntry(this.modLoc("getting_started/storage")),
                BookEntryReadConditionModel.create().withEntry(this.modLoc("storage/overview"))
        ));

        var introReadCondition = BookEntryReadConditionModel.create()
                .withEntry(this.modLoc("getting_started/intro"));
        spiritsCategory.withCondition(introReadCondition);
        ritualsCategory.withCondition(introReadCondition);
        pentaclesCategory.withCondition(introReadCondition);
    }

    @Override
    protected String bookName() {
        return "Dictionary of Spirits";
    }

    @Override
    protected String bookTooltip() {
        return "An introduction to the spirit world.";
    }

    @Override
    protected BookModel additionalSetup(BookModel book) {
        return super.additionalSetup(book)
                .withModel(this.modLoc("dictionary_of_spirits_icon"))
                .withGenerateBookItem(false)
                .withCustomBookItem(this.modLoc("dictionary_of_spirits"))
                .withAutoAddReadConditions(true)
                .withAllowOpenBooksWithInvalidLinks(true)
                ;
    }

    private BookEntryModel makeReturnToRitualsEntry(CategoryEntryMap entryMap, char icon) {
        this.context().entry("return_to_rituals");

        return BookEntryModel.create(this.modLoc(this.context().categoryId() + "/" + this.context().entryId()), this.context().entryName())
                .withIcon(this.modLoc("textures/gui/book/robe.png"))
                .withCategoryToOpen(this.modLoc("rituals"))
                .withEntryBackground(EntryBackground.CIRCLE_GRAY)
                .withLocation(entryMap.get(icon));
    }

    private BookCategoryModel makeSpiritsSubcategory() {
        this.context().category("spirits");
        this.lang().add(this.context().categoryName(), "Spirits");
        var entryMap = ModonomiconAPI.get().getEntryMap();
        entryMap.setMap(
                "___________________________",
                "___________________________",
                "___<_0_n_u_w_______________",
                "___________________________",
                "_____d_____________________",
                "___________________________",
                "___________________________"
        );

        var overview = this.makeSpiritsOverviewEntry(entryMap, '0');
        var returnToGettingStarted = this.makeReturnToGettingStartedEntry(entryMap, '<');
        returnToGettingStarted.withParent(BookEntryParentModel.create(overview.getId()));
        returnToGettingStarted.withCondition(BookTrueConditionModel.create());

        var essenceDecay = this.makeEssenceDecayEntry(entryMap, 'd');
        essenceDecay.withParent(BookEntryParentModel.create(overview.getId()));

        var trueNames = this.makeTrueNamesEntry(entryMap, 'n');
        trueNames.withParent(BookEntryParentModel.create(overview.getId()));

        var unboundSpirits = this.makeUnboundSpiritsEntry(entryMap, 'u');
        unboundSpirits.withParent(BookEntryParentModel.create(trueNames.getId()));

        var wildHunt = this.makeWildHuntEntry(entryMap, 'w');
        wildHunt.withParent(BookEntryParentModel.create(unboundSpirits.getId()));

        return BookCategoryModel.create(this.modLoc(this.context().categoryId()), this.context().categoryName())
                .withIcon(this.modLoc("textures/gui/book/spirits.png"))
                .withShowCategoryButton(true)
                .withEntries(
                        overview,
                        returnToGettingStarted,
                        trueNames,
                        essenceDecay,
                        unboundSpirits,
                        wildHunt
                );
    }

    private BookEntryModel makeReturnToGettingStartedEntry(CategoryEntryMap entryMap, char icon) {
        this.context().entry("return_to_getting_started");
        this.lang().add(this.context().entryName(), "Return to getting started");

        return BookEntryModel.create(this.modLoc(this.context().categoryId() + "/" + this.context().entryId()), this.context().entryName())
                .withIcon(OccultismItems.DICTIONARY_OF_SPIRITS_ICON.get())
                .withCategoryToOpen(this.modLoc("getting_started"))
                .withEntryBackground(EntryBackground.CIRCLE_GRAY)
                .withLocation(entryMap.get(icon));
    }

    private BookEntryModel makeSpiritsOverviewEntry(CategoryEntryMap entryMap, char icon) {
        this.context().entry("overview");
        this.lang().add(this.context().entryName(), "On Spirits");
        this.lang().add(this.context().entryDescription(), "An overview of the supernatural");

        this.context().page("intro");
        var intro = BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText());
        this.lang().add(this.context().pageTitle(), "On Spirits");
        this.lang().add(this.context().pageText(),
                """
                        [#](%1$s)Spirit[#](), commonly referred to also as [#](%1$s)Demon[#](), is a general term for a variety of supernatural entities usually residing in [#](%1$s)The Other Place[#](), a plane of existence entirely separate from our own.
                        """.formatted(COLOR_PURPLE));

        this.context().page("shapes");
        var shapes = BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText());
        this.lang().add(this.context().pageTitle(), "Shapes");
        this.lang().add(this.context().pageText(),
                """
                        When in our world Spirits can take a variety of forms, by morphing their essence into [#](%1$s)Chosen Forms[#](). Alternatively, they can inhabit objects or even living beings.
                        """.formatted(COLOR_PURPLE));

        this.context().page("tiers");
        var tiers = BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText());
        this.lang().add(this.context().pageTitle(), "Types of Spirits");
        this.lang().add(this.context().pageText(),
                """
                        There are four major "ranks" of spirits identified by researchers, but there are a myriad spirits below and in between these ranks, and some great entities of terrible power, referred to only as [#](%1$s)Greater Spirits[#](), that are beyond classification.
                        """.formatted(COLOR_PURPLE));

        this.context().page("foliot");
        var foliot = BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText());
        this.lang().add(this.context().pageTitle(), "Foliot");
        this.lang().add(this.context().pageText(),
                """
                        The lowest identified class of spirit. Equipped with some intelligence and a modicum of power they are most often used for manual labor or minor artifacts.
                        """.formatted(COLOR_PURPLE));

        this.context().page("djinni");
        var djinni = BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText());
        this.lang().add(this.context().pageTitle(), "Djinni");
        this.lang().add(this.context().pageText(),
                """
                        The most commonly summoned class. There is a great variety of Djinni, differing both in intelligence and power. Djinni can be used for a variety of task, ranging from higher artifacts over possession of living beings to carrying out tasks in their Chosen Form.
                        """.formatted(COLOR_PURPLE));

        this.context().page("afrit");
        var afrit = BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText());
        this.lang().add(this.context().pageTitle(), "Afrit");
        this.lang().add(this.context().pageText(),
                """
                        Even more powerful than Djinni, Afrit are used for the creation of major artifacts and the possession of powerful beings.
                        """.formatted(COLOR_PURPLE));


        this.context().page("marid");
        var marid = BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText());
        this.lang().add(this.context().pageTitle(), "Marid");
        this.lang().add(this.context().pageText(),
                """
                        The strongest identified class of spirits. Due to their power and vast intellect attempting a summoning is extremely dangerous and usually only carried out by the most experienced summoners, and even then usually in groups.
                        """.formatted(COLOR_PURPLE));

        this.context().page("greater_spirits");
        var greaterSpirits = BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText());
        this.lang().add(this.context().pageTitle(), "Greater Spirits");
        this.lang().add(this.context().pageText(),
                """
                        Spirits of power so great it is beyond measure. No summons have been attempted in living memory, and records of summonings in ancient times are mostly considered apocryphal.
                        """.formatted(COLOR_PURPLE));


        return BookEntryModel.create(this.modLoc(this.context().categoryId() + "/" + this.context().entryId()), this.context().entryName())
                .withDescription(this.context().entryDescription())
                .withIcon(this.modLoc("textures/gui/book/spirits.png"))
                .withLocation(entryMap.get(icon))
                .withEntryBackground(EntryBackground.STAR_GOLD)
                .withPages(
                        intro,
                        shapes,
                        tiers,
                        foliot,
                        djinni,
                        afrit,
                        marid,
                        greaterSpirits
                );
    }

    private BookEntryModel makeEssenceDecayEntry(CategoryEntryMap entryMap, char icon) {
        this.context().entry("essence_decay");
        this.lang().add(this.context().entryName(), "Essence Decay");
        this.lang().add(this.context().entryDescription(), "Even the immortal are not immune to time.");

        this.context().page("intro");
        var intro = BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText());
        this.lang().add(this.context().pageTitle(), "Essence Decay");
        this.lang().add(this.context().pageText(),
                """
                        When residing in our plane of existence, spirits experience [#](%1$s)Essence Decay[#](), the slow rot of their "body". The more powerful the spirit, the slower the decay, but only the most powerful can stop it entirely. Once fully decayed they are returned to [#](%1$s)The Other Place[#]() and can only be re-summoned once fully recovered.
                        """.formatted(COLOR_PURPLE));

        this.context().page("countermeasures");
        var countermeasures = BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText());
        this.lang().add(this.context().pageTitle(), "Countermeasures");
        this.lang().add(this.context().pageText(),
                """
                        The summoner can slow or even stop essence decay by binding the spirit into an object, or summoning it into a living being. Additionally the pentacle used can influence the effects of essence decay to a degree.
                        """.formatted(COLOR_PURPLE));


        this.context().page("affected_spirits");
        var affectedSpirits = BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText());
        this.lang().add(this.context().pageTitle(), "Affected Spirits");
        this.lang().add(this.context().pageText(),
                """
                        Only trader, time and weather spirits are affected by essence decay, by default. All others are immune and will not despawn. Modpacks may modify this behaviour.
                        """.formatted(COLOR_PURPLE));

        return BookEntryModel.create(this.modLoc(this.context().categoryId() + "/" + this.context().entryId()), this.context().entryName())
                .withDescription(this.context().entryDescription())
                .withIcon(Items.ROTTEN_FLESH)
                .withLocation(entryMap.get(icon))
                .withPages(
                        intro,
                        countermeasures,
                        affectedSpirits
                );
    }

    private BookEntryModel makeTrueNamesEntry(CategoryEntryMap entryMap, char icon) {
        this.context().entry("true_names");
        this.lang().add(this.context().entryName(), "True Names");
        this.lang().add(this.context().entryDescription(), "How to call spirits.");

        this.context().page("intro");
        var intro = BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText());
        this.lang().add(this.context().pageTitle(), "True Names");
        this.lang().add(this.context().pageText(),
                """
                        To summon a spirit the magician needs to know their [#](%1$s)True Name[#](). By calling the true naming during the summoning ritual the Spirit is drawn forth from [#](%1$s)The Other Place[#]() and forced to do the summoners bidding.
                        \\
                        \\
                        *It should be noted, that it does not matter which spirit name is used in summoning, only the spirit tier is relevant.*
                        """.formatted(COLOR_PURPLE));

        this.context().page("finding_names");
        var findingNames = BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText());
        this.lang().add(this.context().pageTitle(), "Finding Names");
        this.lang().add(this.context().pageText(),
                """
                        In ancient summoners had to research and experiment to find [#](%1$s)True Names[#](). Some spirits can be convinced to share their knowledge of true names of other demons, either by promising a swift return to [#](%1$s)The Other Place[#](), or by more ... *persuasive* measures.
                        """.formatted(COLOR_PURPLE));

        this.context().page("using_names");
        var usingNames = BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText());
        this.lang().add(this.context().pageTitle(), "Using Names to Summon a Spirit");
        this.lang().add(this.context().pageText(),
                """
                        For your convenience, in this work you will find the known names of spirits of all 4 ranks, as well as some beyond that. To summon a spirit, copy their name from this book into the appropriate book of binding, then use this bound book of binding to activate a ritual.
                        """.formatted(COLOR_PURPLE));

        return BookEntryModel.create(this.modLoc(this.context().categoryId() + "/" + this.context().entryId()), this.context().entryName())
                .withDescription(this.context().entryDescription())
                .withIcon(Items.WRITABLE_BOOK)
                .withLocation(entryMap.get(icon))
                .withPages(
                        intro,
                        findingNames,
                        usingNames
                );
    }

    private BookEntryModel makeUnboundSpiritsEntry(CategoryEntryMap entryMap, char icon) {
        this.context().entry("unbound_spirits");
        this.lang().add(this.context().entryName(), "Unbound Spirits");
        this.lang().add(this.context().entryDescription(), "Try not to lose your spirits!");

        this.context().page("intro");
        var intro = BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText());
        this.lang().add(this.context().pageTitle(), "Unbound Spirits");
        this.lang().add(this.context().pageText(),
                """
                        Generally spirits are summoned [#](%1$s)bound[#](), which refers to any condition that keeps them under control of the summoner. A side effect of binding spells is that part of the spirit remains in [#](%1$s)The Other Place[#](), robbing them of large portions of the power, but at the same time also protecting their essence from foreign access in this world.
                        """.formatted(COLOR_PURPLE));

        this.context().page("unbound");
        var unbound = BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText());
        this.lang().add(this.context().pageTitle(), "Forego the Leash");
        this.lang().add(this.context().pageText(),
                """
                        In order to access a spirit's essence, or unleash it's full destructive power, it needs to be summoned [#](%1$s)unbound[#](). Unbound summonings use pentacles that are intentionally incomplete or unstable, allowing to call on the spirit, but not putting any constraints on it.
                        """.formatted(COLOR_PURPLE));

        this.context().page("unbound2");
        var unbound2 = BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText());
        this.lang().add(this.context().pageTitle(), "Beware!");
        this.lang().add(this.context().pageText(),
                """
                        The lack of restraints when summoning spirits unbound makes these rituals incredibly dangerous, but you may find that the rewards are worth the risk - and often there is no way around them to achieve certain results.
                        """.formatted(COLOR_PURPLE));

        this.context().page("essence");
        var essence = BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText());
        this.lang().add(this.context().pageTitle(), "Spirit Essence");
        this.lang().add(this.context().pageText(),
                """
                        Unbound summonings are the only way to obtain [Afrit Essence](entry://summoning_rituals/afrit_essence), a powerful substance required for crafting [](item://occultism:chalk_red) which is used for the most powerful binding pentacles.
                        """.formatted(COLOR_PURPLE));

        return BookEntryModel.create(this.modLoc(this.context().categoryId() + "/" + this.context().entryId()), this.context().entryName())
                .withDescription(this.context().entryDescription())
                .withIcon(this.modLoc("textures/gui/book/unbound_spirits.png"))
                .withLocation(entryMap.get(icon))
                .withPages(
                        intro,
                        unbound,
                        unbound2,
                        essence
                );
    }

    private BookEntryModel makeWildHuntEntry(CategoryEntryMap entryMap, char icon) {
        this.context().entry("wild_hunt");
        this.lang().add(this.context().entryName(), "The Wild Hunt");
        this.lang().add(this.context().entryDescription(), "You better watch out, you better not cry ...");
        this.context().page("intro");
        var intro = BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText());
        this.lang().add(this.context().pageTitle(), "The Wild Hunt");
        this.lang().add(this.context().pageText(),
                """
                        A group of legendary Greater Spirits, usually appearing in the form of wither skeletons, with their skeleton minions. The Greater Spirits are bound to their minions in such fashion that they are virtually invulnerable until their minions have been sent back to [#](%1$s)The Other Place[#]().
                        """.formatted(COLOR_PURPLE));

        this.context().page("wither_skull");
        var witherSkull = BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText());
        this.lang().add(this.context().pageTitle(), "Wither Skeleton Skulls");
        this.lang().add(this.context().pageText(),
                """
                        While it is incredibly dangerous to call on the Wild Hunt, some summoners have been known to do so for quick access to the rare wither skeleton skulls they are known to leave behind. Summoning the wild hunt is described in detail on the page on obtaining [Wither Skeleton Skulls](entry://possession_rituals/wither_skull).
                        """.formatted(COLOR_PURPLE));

        return BookEntryModel.create(this.modLoc(this.context().categoryId() + "/" + this.context().entryId()), this.context().entryName())
                .withDescription(this.context().entryDescription())
                .withIcon(this.modLoc("textures/gui/book/wild_hunt.png"))
                .withLocation(entryMap.get(icon))
                .withPages(
                        intro,
                        witherSkull
                );
    }
    //endregion

    private BookCategoryModel makeRitualsCategory() {
        this.context().category("rituals");

        var entryMap = ModonomiconAPI.get().getEntryMap();
        entryMap.setMap(
                "___________________",
                "______________p_s__",
                "___________________",
                "________o_i_k______",
                "___________________",
                "______________c_f__",
                "___________________"
        );

        var ritualOverview = this.makeRitualOverviewEntry(entryMap, 'o');
        var itemUse = this.makeItemUseEntry(entryMap, 'i');
        itemUse.withParent(BookEntryParentModel.create(ritualOverview.getId()));
        var sacrifice = this.makeSacrificeEntry(entryMap, 'k');
        sacrifice.withParent(BookEntryParentModel.create(itemUse.getId()));

        var summoning = this.makeSummoningRitualsSubcategoryEntry(entryMap, 's');
        summoning.withParent(BookEntryParentModel.create(sacrifice.getId()))
                .withCondition(BookEntryReadConditionModel.create().withEntry(this.modLoc("pentacles/summon_foliot")));
        var possession = this.makePossessionRitualsSubcategoryEntry(entryMap, 'p');
        possession.withParent(BookEntryParentModel.create(sacrifice.getId()))
                .withCondition(BookEntryReadConditionModel.create().withEntry(this.modLoc("pentacles/possess_foliot")));
        var crafting = this.makeCraftingRitualsSubcategoryEntry(entryMap, 'c');
        crafting.withParent(BookEntryParentModel.create(sacrifice.getId()))
                .withCondition(BookEntryReadConditionModel.create().withEntry(this.modLoc("pentacles/craft_foliot")));
        var familiars = this.makeFamiliarRitualsSubcategoryEntry(entryMap, 'f');
        familiars.withParent(BookEntryParentModel.create(sacrifice.getId()))
                .withCondition(BookEntryReadConditionModel.create().withEntry(this.modLoc("pentacles/possess_foliot")));

        //enable all entries by default
        itemUse.withCondition(BookTrueConditionModel.create());
        sacrifice.withCondition(BookTrueConditionModel.create());

        return BookCategoryModel.create(this.modLoc(this.context().categoryId()), this.context().categoryName())
                .withIcon(this.modLoc("textures/gui/book/robe.png"))
                .withEntries(
                        ritualOverview,
                        itemUse,
                        sacrifice,
                        summoning,
                        possession,
                        crafting,
                        familiars
                );
    }

    private BookEntryModel makeRitualOverviewEntry(CategoryEntryMap entryMap, char icon) {
        this.context().entry("overview");

        this.context().page("intro");
        var intro = BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText());

        this.context().page("steps");
        var steps = BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText());

        this.context().page("additional_requirements");
        var additional_requirements = BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText());

        return BookEntryModel.create(this.modLoc(this.context().categoryId() + "/" + this.context().entryId()), this.context().entryName())
                .withIcon(this.modLoc("textures/gui/book/robe.png"))
                .withLocation(entryMap.get(icon))
                .withEntryBackground(EntryBackground.STAR_GOLD)
                .withPages(
                        intro,
                        steps,
                        additional_requirements
                );
    }

    private BookEntryModel makeSacrificeEntry(CategoryEntryMap entryMap, char icon) {
        this.context().entry("sacrifice");

        this.context().page("intro");
        var intro = BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText());

        return BookEntryModel.create(this.modLoc(this.context().categoryId() + "/" + this.context().entryId()), this.context().entryName())
                .withIcon(Items.IRON_SWORD)
                .withLocation(entryMap.get(icon))
                .withPages(
                        intro
                );
    }

    private BookEntryModel makeItemUseEntry(CategoryEntryMap entryMap, char icon) {
        this.context().entry("item_use");

        this.context().page("intro");
        var intro = BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText());

        return BookEntryModel.create(this.modLoc(this.context().categoryId() + "/" + this.context().entryId()), this.context().entryName())
                .withIcon(Items.FLINT_AND_STEEL)
                .withLocation(entryMap.get(icon))
                .withPages(
                        intro
                );
    }

    private BookEntryModel makeSummoningRitualsSubcategoryEntry(CategoryEntryMap entryMap, char icon) {
        this.context().entry("summoning_rituals");

        return BookEntryModel.create(this.modLoc(this.context().categoryId() + "/" + this.context().entryId()), this.context().entryName())
                .withIcon(this.modLoc("textures/gui/book/summoning.png"))
                .withCategoryToOpen(this.modLoc("summoning_rituals"))
                .withEntryBackground(EntryBackground.STAR_GRAY) //silver background and wavey entry shape
                .withLocation(entryMap.get(icon));
    }

    private BookEntryModel makePossessionRitualsSubcategoryEntry(CategoryEntryMap entryMap, char icon) {
        this.context().entry("possession_rituals");

        return BookEntryModel.create(this.modLoc(this.context().categoryId() + "/" + this.context().entryId()), this.context().entryName())
                .withIcon(this.modLoc("textures/gui/book/possession.png"))
                .withCategoryToOpen(this.modLoc("possession_rituals"))
                .withEntryBackground(EntryBackground.STAR_GRAY) //silver background and wavey entry shape
                .withLocation(entryMap.get(icon));
    }

    private BookEntryModel makeCraftingRitualsSubcategoryEntry(CategoryEntryMap entryMap, char icon) {
        this.context().entry("crafting_rituals");

        return BookEntryModel.create(this.modLoc(this.context().categoryId() + "/" + this.context().entryId()), this.context().entryName())
                .withIcon(this.modLoc("textures/gui/book/infusion.png"))
                .withCategoryToOpen(this.modLoc("crafting_rituals"))
                .withEntryBackground(EntryBackground.STAR_GRAY) //silver background and wavey entry shape
                .withLocation(entryMap.get(icon));
    }

    private BookEntryModel makeFamiliarRitualsSubcategoryEntry(CategoryEntryMap entryMap, char icon) {
        this.context().entry("familiar_rituals");

        return BookEntryModel.create(this.modLoc(this.context().categoryId() + "/" + this.context().entryId()), this.context().entryName())
                .withIcon(this.modLoc("textures/gui/book/familiar.png"))
                .withCategoryToOpen(this.modLoc("familiar_rituals"))
                .withEntryBackground(EntryBackground.STAR_GRAY) //silver background and wavey entry shape
                .withLocation(entryMap.get(icon));
    }


    //endregion

    //region Possession Rituals
    private BookCategoryModel makePossessionRitualsSubcategory() {
        this.context().category("possession_rituals");

        var entryMap = ModonomiconAPI.get().getEntryMap();
        entryMap.setMap(
                "________I_A_B_J_P_L_K_C____", //The Places follow the tier progression
                "___________________________",
                "_______D_G_E_F_Q_R_M_N_____",
                "___________________________",
                "___r_o_____________________",
                "___________________________",
                "_______H_W_Y__S___p_a______", //Down part is wild
                "___________________________",
                "________V_X_Z_T___d_b______",
                "___________________________",
                "______________U____________"
        );

        String possessFoliotID = this.modId() + ":" + PentaclesCategory.CATEGORY_ID + "/" + PossessFoliotEntry.ENTRY_ID;
        String possessDjinniID = this.modId() + ":" + PentaclesCategory.CATEGORY_ID + "/" + PossessDjinniEntry.ENTRY_ID;
        String possessUnboundAfritID = this.modId() + ":" + PentaclesCategory.CATEGORY_ID + "/" + PossessUnboundAfritEntry.ENTRY_ID;
        String possessAfritID = this.modId() + ":" + PentaclesCategory.CATEGORY_ID + "/" + PossessAfritEntry.ENTRY_ID;
        String possessMaridID = this.modId() + ":" + PentaclesCategory.CATEGORY_ID + "/" + PossessMaridEntry.ENTRY_ID;
        String possessWildID = this.modId() + ":" + PentaclesCategory.CATEGORY_ID + "/" + ContactWildSpiritEntry.ENTRY_ID;

        var overview = this.makePossessionRitualsOverviewEntry(entryMap, 'o');
        overview.withCondition(BookEntryReadConditionModel.create().withEntry(possessFoliotID));
        var returnToRituals = this.makeReturnToRitualsEntry(entryMap, 'r');
        returnToRituals.withParent(BookEntryParentModel.create(overview.getId()))
                .withCondition(BookEntryReadConditionModel.create().withEntry(possessFoliotID));

        var possessEndermite = this.makePossessEndermiteEntry(entryMap, 'D');
        possessEndermite.withParent(BookEntryParentModel.create(overview.getId()))
                .withCondition(BookEntryReadConditionModel.create().withEntry(possessFoliotID));
        var possessPhantom = this.makePossessPhantomEntry(entryMap, 'I');
        possessPhantom.withParent(BookEntryParentModel.create(overview.getId()))
                .withCondition(BookEntryReadConditionModel.create().withEntry(possessFoliotID));
        var possessSkeleton = this.makePossessSkeletonEntry(entryMap, 'G');
        possessSkeleton.withParent(BookEntryParentModel.create(overview.getId()))
                .withCondition(BookEntryReadConditionModel.create().withEntry(possessFoliotID));
        var possessWitch = this.makePossessWitchEntry(entryMap, 'A');
        possessWitch.withParent(BookEntryParentModel.create(overview.getId()))
                .withCondition(BookEntryReadConditionModel.create().withEntry(possessFoliotID));
        var possessEnderman = this.makePossessEndermanEntry(entryMap, 'E');
        possessEnderman.withParent(BookEntryParentModel.create(overview.getId()))
                .withCondition(BookEntryReadConditionModel.create().withEntry(possessDjinniID));
        var possessBee = this.makePossessBeeEntry(entryMap, 'B');
        possessBee.withParent(BookEntryParentModel.create(overview.getId()))
                .withCondition(BookEntryReadConditionModel.create().withEntry(possessDjinniID));
        var possessGhast = this.makePossessGhastEntry(entryMap, 'F');
        possessGhast.withParent(BookEntryParentModel.create(overview.getId()))
                .withCondition(BookEntryReadConditionModel.create().withEntry(possessDjinniID));
        var possessWeakShulker = this.makePossessWeakShulkerEntry(entryMap, 'J');
        possessWeakShulker.withParent(BookEntryParentModel.create(overview.getId()))
                .withCondition(BookEntryReadConditionModel.create().withEntry(possessDjinniID));
        var possessBlaze = this.makePossessBlazeEntry(entryMap, 'Q');
        possessBlaze.withParent(BookEntryParentModel.create(overview.getId()))
                .withCondition(BookEntryReadConditionModel.create().withEntry(possessDjinniID));
        var possessZombiePiglin = this.makePossessZombiePiglinEntry(entryMap, 'P');
        possessZombiePiglin.withParent(BookEntryParentModel.create(overview.getId()))
                .withCondition(BookEntryReadConditionModel.create().withEntry(possessUnboundAfritID));
        var possessGuardian = this.makePossessGuardianEntry(entryMap, 'R');
        possessGuardian.withParent(BookEntryParentModel.create(overview.getId()))
                .withCondition(BookEntryReadConditionModel.create().withEntry(possessUnboundAfritID));
        var possessWarden = this.makePossessWardenEntry(entryMap, 'M');
        possessWarden.withParent(BookEntryParentModel.create(overview.getId()))
                .withCondition(BookEntryReadConditionModel.create().withEntry(possessAfritID));
        var possessElderGuardian = this.makePossessElderGuardianEntry(entryMap, 'L');
        possessElderGuardian.withParent(BookEntryParentModel.create(overview.getId()))
                .withCondition(BookEntryReadConditionModel.create().withEntry(possessAfritID));
        var possessHoglin = this.makePossessHoglinEntry(entryMap, 'N');
        possessHoglin.withParent(BookEntryParentModel.create(overview.getId()))
                .withCondition(BookEntryReadConditionModel.create().withEntry(possessAfritID));
        var possessShulker = this.makePossessShulkerEntry(entryMap, 'K');
        possessShulker.withParent(BookEntryParentModel.create(overview.getId()))
                .withCondition(BookEntryReadConditionModel.create().withEntry(possessAfritID));
        var mercyGoat = this.makeMercyGoatEntry(entryMap, 'C');
        mercyGoat.withParent(BookEntryParentModel.create(overview.getId()))
                .withCondition(BookEntryReadConditionModel.create().withEntry(possessMaridID));

        var possessWitherSkeleton = this.makeWitherSkullEntry(entryMap, 'H');
        possessWitherSkeleton.withParent(BookEntryParentModel.create(overview.getId()))
                .withCondition(BookEntryReadConditionModel.create().withEntry(possessWildID));
        var hordeIllager = this.makeHordeIllagerEntry(entryMap, 'V');
        hordeIllager.withParent(BookEntryParentModel.create(overview.getId()))
                .withCondition(BookEntryReadConditionModel.create().withEntry(possessWildID));
        var hordeDesert = this.makeHordeDesertEntry(entryMap, 'W');
        hordeDesert.withParent(BookEntryParentModel.create(overview.getId()))
                .withCondition(BookEntryReadConditionModel.create().withEntry(possessWildID));
        var hordeDrowned = this.makeHordeDrownedEntry(entryMap, 'X');
        hordeDrowned.withParent(BookEntryParentModel.create(overview.getId()))
                .withCondition(BookEntryReadConditionModel.create().withEntry(possessWildID));
        var hordeCreeper = this.makeHordeCreeperEntry(entryMap, 'Y');
        hordeCreeper.withParent(BookEntryParentModel.create(overview.getId()))
                .withCondition(BookEntryReadConditionModel.create().withEntry(possessWildID));
        var hordeSilverfish = this.makeHordeSilverfishEntry(entryMap, 'Z');
        hordeSilverfish.withParent(BookEntryParentModel.create(overview.getId()))
                .withCondition(BookEntryReadConditionModel.create().withEntry(possessWildID));
        var possessWeakBreeze = this.makePossessWeakBreezeEntry(entryMap, 'S');
        possessWeakBreeze.withParent(BookEntryParentModel.create(overview.getId()))
                .withCondition(BookEntryReadConditionModel.create().withEntry(possessWildID));
        var possessBreeze = this.makePossessBreezeEntry(entryMap, 'T');
        possessBreeze.withParent(BookEntryParentModel.create(possessWeakBreeze.getId()))
                .withCondition(BookEntryReadConditionModel.create().withEntry(possessWeakBreeze.getId()));
        var possessStrongBreeze = this.makePossessStrongBreezeEntry(entryMap, 'U');
        possessStrongBreeze.withParent(BookEntryParentModel.create(possessBreeze.getId()))
                .withCondition(BookEntryReadConditionModel.create().withEntry(possessBreeze.getId()));

        var possessUnboundParrot = this.makePossessUnboundParrotEntry(entryMap, 'p');
        possessUnboundParrot.withParent(BookEntryParentModel.create(overview.getId()))
                .withCondition(BookEntryReadConditionModel.create().withEntry(possessFoliotID));
        var possessUnboundOtherworldBird = this.makePossessUnboundOtherworldBirdEntry(entryMap, 'd');
        possessUnboundOtherworldBird.withParent(BookEntryParentModel.create(possessUnboundParrot.getId()))
                .withCondition(BookEntryReadConditionModel.create().withEntry(possessDjinniID));

        var possessRandomAnimal = this.makePossessRandomAnimalEntry(entryMap, 'a');
        possessRandomAnimal.withParent(BookEntryParentModel.create(overview.getId()))
                .withCondition(BookEntryReadConditionModel.create().withEntry(possessFoliotID));

        var wildRandomAnimal = this.makeWildRandomAnimalEntry(entryMap, 'b');
        wildRandomAnimal.withParent(BookEntryParentModel.create(possessRandomAnimal.getId()))
                .withCondition(BookEntryReadConditionModel.create().withEntry(possessWildID));

        this.context().category("possession_rituals");

        return BookCategoryModel.create(this.modLoc(this.context().categoryId()), this.context().categoryName())
                .withIcon(this.modLoc("textures/gui/book/possession.png"))
                .withShowCategoryButton(true)
                .withEntries(
                        overview,
                        returnToRituals,
                        possessEnderman,
                        possessEndermite,
                        possessGhast,
                        possessSkeleton,
                        possessPhantom,
                        possessWeakShulker,
                        possessShulker,
                        possessElderGuardian,
                        possessWarden,
                        possessHoglin,
                        possessWitch,
                        possessBlaze,
                        possessWitherSkeleton,
                        hordeDesert,
                        hordeDrowned,
                        hordeCreeper,
                        hordeSilverfish,
                        hordeIllager,
                        possessWeakBreeze,
                        possessBreeze,
                        possessStrongBreeze,
                        mercyGoat,
                        possessZombiePiglin,
                        possessGuardian,
                        possessBee,
                        possessUnboundOtherworldBird,
                        possessUnboundParrot,
                        possessRandomAnimal,
                        wildRandomAnimal
                );
    }

    private BookEntryModel makePossessionRitualsOverviewEntry(CategoryEntryMap entryMap, char icon) {
        this.context().entry("overview");

        this.context().page("intro");
        var intro = BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText());

        return BookEntryModel.create(this.modLoc(this.context().categoryId() + "/" + this.context().entryId()), this.context().entryName())
                .withIcon(this.modLoc("textures/gui/book/possession.png"))
                .withLocation(entryMap.get(icon))
                .withEntryBackground(EntryBackground.STAR_GOLD)
                .withPages(
                        intro
                );
    }

    private BookEntryModel makeWitherSkullEntry(CategoryEntryMap entryMap, char icon) {
        this.context().entry("wither_skull");

        this.context().page("intro");
        var intro = BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText());

        this.context().page("ritual");
        var ritual = BookRitualRecipePageModel.create()
                .withRecipeId1(this.modLoc("ritual/wild_hunt"));

        return BookEntryModel.create(this.modLoc(this.context().categoryId() + "/" + this.context().entryId()), this.context().entryName())
                .withIcon(Items.WITHER_SKELETON_SKULL)
                .withLocation(entryMap.get(icon))
                .withPages(
                        intro,
                        ritual
                );
    }

    private BookEntryModel makePossessEndermanEntry(CategoryEntryMap entryMap, char icon) {
        this.context().entry("possess_enderman");

        this.context().page("entity");
        var entity = BookEntityPageModel.create()
                .withEntityId("occultism:possessed_enderman")
                .withText(this.context().pageText());

        this.context().page("ritual");
        var ritual = BookRitualRecipePageModel.create()
                .withRecipeId1(this.modLoc("ritual/possess_enderman"));

        this.context().page("description");
        var description = BookTextPageModel.create()
                .withText(this.context().pageText());

        return BookEntryModel.create(this.modLoc(this.context().categoryId() + "/" + this.context().entryId()), this.context().entryName())
                .withIcon(Items.ENDER_PEARL)
                .withLocation(entryMap.get(icon))
                .withPages(
                        entity,
                        ritual,
                        description
                );
    }

    private BookEntryModel makePossessEndermiteEntry(CategoryEntryMap entryMap, char icon) {
        this.context().entry("possess_endermite");

        this.context().page("entity");
        var entity = BookEntityPageModel.create()
                .withEntityId("occultism:possessed_endermite")
                .withText(this.context().pageText());

        this.context().page("ritual");
        var ritual = BookRitualRecipePageModel.create()
                .withRecipeId1(this.modLoc("ritual/possess_endermite"));

        this.context().page("description");
        var description = BookTextPageModel.create()
                .withText(this.context().pageText());

        return BookEntryModel.create(this.modLoc(this.context().categoryId() + "/" + this.context().entryId()), this.context().entryName())
                .withIcon(Blocks.END_STONE)
                .withLocation(entryMap.get(icon))
                .withPages(
                        entity,
                        ritual,
                        description
                );
    }

    private BookEntryModel makePossessGhastEntry(CategoryEntryMap entryMap, char icon) {
        this.context().entry("possess_ghast");
        this.lang().add(this.context().entryName(), "Possessed Ghast");

        this.context().page("entity");
        var entity = BookEntityPageModel.create()
                .withEntityId("occultism:possessed_ghast")
                .withScale(0.5f)
                .withText(this.context().pageText());
        this.lang().add(this.context().pageText(),
                """
                        **Drops**: 1-3x [](item://minecraft:ghast_tear) and
                        1-4x [](item://minecraft:gunpowder)
                        """);

        this.context().page("ritual");
        var ritual = BookRitualRecipePageModel.create()
                .withRecipeId1(this.modLoc("ritual/possess_ghast"));
        //no text

        this.context().page("description");
        var description = BookTextPageModel.create()
                .withText(this.context().pageText());
        this.lang().add(this.context().pageText(),
                """
                        In this ritual a [#](%1$s)Ghast[#]() is spawned using the life energy of a [#](%1$s)Cow[#]() and immediately possessed by the summoned [#](%1$s)Djinni[#](). The [#](%1$s)Possessed Ghast[#]() will always drop at least one [](item://minecraft:ghast_tear) when killed.
                        """.formatted(COLOR_PURPLE));

        return BookEntryModel.create(this.modLoc(this.context().categoryId() + "/" + this.context().entryId()), this.context().entryName())
                .withIcon(Items.GHAST_TEAR)
                .withLocation(entryMap.get(icon))
                .withPages(
                        entity,
                        ritual,
                        description
                );
    }

    private BookEntryModel makePossessSkeletonEntry(CategoryEntryMap entryMap, char icon) {
        this.context().entry("possess_skeleton");

        this.context().page("entity");
        var entity = BookEntityPageModel.create()
                .withEntityId("occultism:possessed_skeleton")
                .withText(this.context().pageText());

        this.context().page("ritual");
        var ritual = BookRitualRecipePageModel.create()
                .withRecipeId1(this.modLoc("ritual/possess_skeleton"));

        this.context().page("description");
        var description = BookTextPageModel.create()
                .withText(this.context().pageText());

        return BookEntryModel.create(this.modLoc(this.context().categoryId() + "/" + this.context().entryId()), this.context().entryName())
                .withIcon(Items.SKELETON_SKULL)
                .withLocation(entryMap.get(icon))
                .withPages(
                        entity,
                        ritual,
                        description
                );
    }

    private BookEntryModel makePossessPhantomEntry(CategoryEntryMap entryMap, char icon) {
        this.context().entry("possess_phantom");
        this.lang().add(this.context().entryName(), "Possessed Phantom");

        this.context().page("entity");
        var entity = BookEntityPageModel.create()
                .withEntityId("occultism:possessed_phantom")
                .withScale(0.5f)
                .withText(this.context().pageText());
        this.lang().add(this.context().pageText(),
                """
                        **Drops**: 1-4x [](item://minecraft:phantom_membrane)
                        and has 5%% chance to drop a [](item://minecraft:wind_charge)
                        """);

        this.context().page("ritual");
        var ritual = BookRitualRecipePageModel.create()
                .withRecipeId1(this.modLoc("ritual/possess_phantom"));

        this.context().page("description");
        var description = BookTextPageModel.create()
                .withText(this.context().pageText());
        this.lang().add(this.context().pageText(),
                """
                        In this ritual a [#](%1$s)Phantom[#]() is spawned using the life energy of a [#](%1$s)Flying Passive Mob[#]() and immediately possessed by the summoned [#](%1$s)Foliot[#](). The [#](%1$s)Possessed Phantom[#]() will always drop at least one [](item://minecraft:phantom_membrane) when killed. Using this ritual is easy to trap the phantom and you can has comfy sleep.
                        """.formatted(COLOR_PURPLE));

        return BookEntryModel.create(this.modLoc(this.context().categoryId() + "/" + this.context().entryId()), this.context().entryName())
                .withIcon(Items.PHANTOM_MEMBRANE)
                .withLocation(entryMap.get(icon))
                .withPages(
                        entity,
                        ritual,
                        description
                );
    }

    private BookEntryModel makePossessWeakShulkerEntry(CategoryEntryMap entryMap, char icon) {
        this.context().entry("possess_weak_shulker");
        this.lang().add(this.context().entryName(), "Possessed Weak Shulker");

        this.context().page("entity");
        var entity = BookEntityPageModel.create()
                .withEntityId("occultism:possessed_weak_shulker")
                .withScale(0.5f)
                .withText(this.context().pageText());
        this.lang().add(this.context().pageText(),
                """
                        **Drops**: 1-3x [](item://minecraft:chorus_fruit)
                        and as 10%% to drop a [](item://minecraft:shulker_shell);
                        """);

        this.context().page("ritual");
        var ritual = BookRitualRecipePageModel.create()
                .withRecipeId1(this.modLoc("ritual/possess_weak_shulker"));

        this.context().page("description");
        var description = BookTextPageModel.create()
                .withText(this.context().pageText());
        this.lang().add(this.context().pageText(),
                """
                        In this ritual a [#](%1$s)Shulker[#]() is spawned using the life energy of a [#](%1$s)Cube Mob[#]() and immediately possessed by the summoned [#](%1$s)Djinni[#](). The [#](%1$s)Possessed Weak Shulker[#]() will always drop at least one [](item://minecraft:chorus_fruit) when killed and as a chance to drop [](item://minecraft:shulker_shell). You can use vanilla shulker multiplication to get normal shulkers with more chance to drop their shells.
                        """.formatted(COLOR_PURPLE));

        return BookEntryModel.create(this.modLoc(this.context().categoryId() + "/" + this.context().entryId()), this.context().entryName())
                .withIcon(Items.CHORUS_FRUIT)
                .withLocation(entryMap.get(icon))
                .withPages(
                        entity,
                        ritual,
                        description
                );
    }

    private BookEntryModel makePossessBlazeEntry(CategoryEntryMap entryMap, char icon) {
        this.context().entry("possess_blaze");
        this.lang().add(this.context().entryName(), "Possessed Blaze");

        this.context().page("entity");
        var entity = BookEntityPageModel.create()
                .withEntityId("occultism:possessed_blaze")
                .withScale(1f)
                .withText(this.context().pageText());
        this.lang().add(this.context().pageText(),
                """
                        **Drops**: 2-6x [](item://minecraft:blaze_rod), 0-13x [](item://minecraft:blaze_powder)
                        and nether-related items (check next page);
                        """);

        this.context().page("ritual");
        var ritual = BookRitualRecipePageModel.create()
                .withRecipeId1(this.modLoc("ritual/possess_blaze"));

        this.context().page("description");
        var description = BookTextPageModel.create()
                .withText(this.context().pageText());
        this.lang().add(this.context().pageText(),
                """
                        In this ritual a [#](%1$s)Blaze[#]() is spawned with energy of [#](%1$s)The Nether[#]() and immediately 
                        possessed by the summoned [#](%1$s)Djinni[#](). The [#](%1$s)Possessed Blaze[#]() is immune to water and snowball! 
                        \\                        
                        Extra Drops:
                        \\
                        Always one of [](item://minecraft:nether_wart), [](item://minecraft:crimson_fungus), [](item://minecraft:warped_fungus), 
                        [](item://minecraft:red_mushroom), [](item://minecraft:brown_mushroom), [](item://minecraft:crimson_roots), 
                        [](item://minecraft:warped_roots), [](item://minecraft:weeping_vines), [](item://minecraft:twisting_vines);                         
                        """.formatted(COLOR_PURPLE));

        this.context().page("description2");
        var description2 = BookTextPageModel.create()
                .withText(this.context().pageText());
        this.lang().add(this.context().pageText(),
                """
                        Usually one of [](item://minecraft:netherrack), [](item://minecraft:nether_quartz_ore), [](item://minecraft:crimson_nylium),
                        [](item://minecraft:warped_nylium), [](item://minecraft:nether_wart_block), [](item://minecraft:warped_wart_block);
                        \\
                        Generally one of [](item://minecraft:soul_sand), [](item://minecraft:soul_soil), [](item://minecraft:basalt), 
                        [](item://minecraft:blackstone), [](item://minecraft:gravel), [](item://minecraft:bone_block), [](item://minecraft:gilded_blackstone);
                        Sometimes one of [](item://minecraft:glowstone_dust), [](item://minecraft:magma_block), [](item://minecraft:glowstone), [](item://minecraft:shroomlight);
                        \\
                        Occasionally one of [](item://minecraft:obsidian), [](item://minecraft:crying_obsidian), [](item://minecraft:ancient_debris);
                        """.formatted(COLOR_PURPLE));

        return BookEntryModel.create(this.modLoc(this.context().categoryId() + "/" + this.context().entryId()), this.context().entryName())
                .withIcon(Items.BLAZE_ROD)
                .withLocation(entryMap.get(icon))
                .withPages(
                        entity,
                        ritual,
                        description,
                        description2
                );
    }

    private BookEntryModel makePossessShulkerEntry(CategoryEntryMap entryMap, char icon) {
        this.context().entry("possess_shulker");
        this.lang().add(this.context().entryName(), "Possessed Shulker");

        this.context().page("entity");
        var entity = BookEntityPageModel.create()
                .withEntityId("occultism:possessed_shulker")
                .withScale(0.5f)
                .withText(this.context().pageText());
        this.lang().add(this.context().pageText(),
                """
                        **Drops**: 1-2x [](item://minecraft:shulker_shell) and
                        as 10%% chance to drop a [](item://minecraft:chorus_flower)
                         or [](item://minecraft:spire_armor_trim_smithing_template);
                        """);

        this.context().page("ritual");
        var ritual = BookRitualRecipePageModel.create()
                .withRecipeId1(this.modLoc("ritual/possess_shulker"));

        this.context().page("description");
        var description = BookTextPageModel.create()
                .withText(this.context().pageText());
        this.lang().add(this.context().pageText(),
                """
                        In this ritual a [#](%1$s)Shulker[#]() is spawned using the life energy of a [#](%1$s)Cube Mob[#]() and immediately possessed by the summoned [#](%1$s)Afrit[#](). The [#](%1$s)Possessed Shulker[#]() will always drop at least one [](item://minecraft:shulker_shell) when killed. You can use vanilla shulker multiplication to get normal shulkers but their have less chance to drop shells.
                        """.formatted(COLOR_PURPLE));

        return BookEntryModel.create(this.modLoc(this.context().categoryId() + "/" + this.context().entryId()), this.context().entryName())
                .withIcon(Items.SHULKER_SHELL)
                .withLocation(entryMap.get(icon))
                .withPages(
                        entity,
                        ritual,
                        description
                );
    }

    private BookEntryModel makePossessElderGuardianEntry(CategoryEntryMap entryMap, char icon) {
        this.context().entry("possess_elder_guardian");
        this.lang().add(this.context().entryName(), "Possessed Elder Guardian");

        this.context().page("entity");
        var entity = BookEntityPageModel.create()
                .withEntityId("occultism:possessed_elder_guardian")
                .withScale(0.7f)
                .withText(this.context().pageText());
        this.lang().add(this.context().pageText(),
                """
                        **Drops**: 2-4x [](item://minecraft:nautilus_shell)
                        and as 40%% to drop a [](item://minecraft:heart_of_the_sea)
                        Also common Elder Guardian loot;
                        """);

        this.context().page("ritual");
        var ritual = BookRitualRecipePageModel.create()
                .withRecipeId1(this.modLoc("ritual/possess_elder_guardian"));

        this.context().page("description");
        var description = BookTextPageModel.create()
                .withText(this.context().pageText());
        this.lang().add(this.context().pageText(),
                """
                        In this ritual a [#](%1$s)Elder Guardian[#]() is spawned using the life energy of a [#](%1$s)Fish[#]() and immediately possessed by the summoned [#](%1$s)Afrit[#](). The [#](%1$s)Possessed Elder Guardian[#]() will always drop at least one [](item://minecraft:nautilus_shell), having a chance to drop [](item://minecraft:heart_of_the_sea) and a lot of things that normal Elder Guardian drops.
                        """.formatted(COLOR_PURPLE));

        return BookEntryModel.create(this.modLoc(this.context().categoryId() + "/" + this.context().entryId()), this.context().entryName())
                .withIcon(Items.HEART_OF_THE_SEA)
                .withLocation(entryMap.get(icon))
                .withPages(
                        entity,
                        ritual,
                        description
                );
    }

    private BookEntryModel makePossessWardenEntry(CategoryEntryMap entryMap, char icon) {
        this.context().entry("possess_warden");
        this.lang().add(this.context().entryName(), "Possessed Warden");

        this.context().page("entity");
        var entity = BookEntityPageModel.create()
                .withEntityId("occultism:possessed_warden")
                .withScale(1f)
                .withText(this.context().pageText());
        this.lang().add(this.context().pageText(),
                """
                        **Drops**: 6-9x [](item://minecraft:echo_shard)
                        and items related to ancient city;
                        """);

        this.context().page("ritual");
        var ritual = BookRitualRecipePageModel.create()
                .withRecipeId1(this.modLoc("ritual/possess_warden"));

        this.context().page("description");
        var description = BookTextPageModel.create()
                .withText(this.context().pageText());
        this.lang().add(this.context().pageText(),
                """
                        In this ritual a [#](%1$s)Warden[#]() is spawned using the life energy of a [#](%1$s)Axolotl[#]() and immediately possessed by the summoned [#](%1$s)Afrit[#](). The [#](%1$s)Possessed Warden[#]() will always drop at least six [](item://minecraft:echo_shard) when killed and as a chance to drop [](item://minecraft:disc_fragment_5), [](item://minecraft:music_disc_otherside), [](item://minecraft:silence_armor_trim_smithing_template), [](item://minecraft:ward_armor_trim_smithing_template). If you try to escape, this possessed Warden will go to the floor like a normal warden.
                        """.formatted(COLOR_PURPLE));

        return BookEntryModel.create(this.modLoc(this.context().categoryId() + "/" + this.context().entryId()), this.context().entryName())
                .withIcon(Items.ECHO_SHARD)
                .withLocation(entryMap.get(icon))
                .withPages(
                        entity,
                        ritual,
                        description
                );
    }

    private BookEntryModel makePossessHoglinEntry(CategoryEntryMap entryMap, char icon) {
        this.context().entry("possess_hoglin");
        this.lang().add(this.context().entryName(), "Possessed Hoglin");

        this.context().page("entity");
        var entity = BookEntityPageModel.create()
                .withEntityId("occultism:possessed_hoglin")
                .withScale(0.7f)
                .withText(this.context().pageText());
        this.lang().add(this.context().pageText(),
                """
                          **Drops**: Can drop: [](item://minecraft:netherite_upgrade_smithing_template),
                          return back [](item://minecraft:netherite_scrap) or other things (See next page);
                        """);

        this.context().page("ritual");
        var ritual = BookRitualRecipePageModel.create()
                .withRecipeId1(this.modLoc("ritual/possess_hoglin"));

        this.context().page("description");
        var description = BookTextPageModel.create()
                .withText(this.context().pageText());
        this.lang().add(this.context().pageText(),
                """
                        In this ritual a [#](%1$s)Hoglin[#]() is spawned using the life energy of a [#](%1$s)Pig[#]() and immediately possessed by the summoned [#](%1$s)Afrit[#](). The [#](%1$s)Possessed Hoglin[#]() can drop a [](item://minecraft:netherite_upgrade_smithing_template), [](item://minecraft:snout_armor_trim_smithing_template), [](item://minecraft:music_disc_pigstep), [](item://minecraft:piglin_banner_pattern), [](item://minecraft:nether_brick) or return back [](item://minecraft:netherite_scrap). You need to kill this mob before the transformation to a Zoglin if you don't want to perform the ritual in the nether.
                        """.formatted(COLOR_PURPLE));

        return BookEntryModel.create(this.modLoc(this.context().categoryId() + "/" + this.context().entryId()), this.context().entryName())
                .withIcon(Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE)
                .withLocation(entryMap.get(icon))
                .withPages(
                        entity,
                        ritual,
                        description
                );
    }

    private BookEntryModel makePossessWitchEntry(CategoryEntryMap entryMap, char icon) {
        this.context().entry("possess_witch");
        this.lang().add(this.context().entryName(), "Possessed Witch");

        this.context().page("entity");
        var entity = BookEntityPageModel.create()
                .withEntityId("occultism:possessed_witch")
                .withScale(0.4f).withOffset(0.8f)
                .withText(this.context().pageText());
        this.lang().add(this.context().pageText(),
                """
                          **Drops**: Can drop: [](item://minecraft:experience_bottle) or other bottles (See next page);
                        """);

        this.context().page("ritual");
        var ritual = BookRitualRecipePageModel.create()
                .withRecipeId1(this.modLoc("ritual/possess_witch"));

        this.context().page("description");
        var description = BookTextPageModel.create()
                .withText(this.context().pageText());
        this.lang().add(this.context().pageText(),
                """
                        In this ritual a [#](%1$s)Witch[#]() is spawned using the rage energy from the [#](%1$s)Cat[#]() death. The [#](%1$s)Possessed Witch[#]() can drop a [](item://minecraft:experience_bottle), [](item://minecraft:honey_bottle), [](item://minecraft:ominous_bottle) or a simple water bottle.
                        """.formatted(COLOR_PURPLE));

        return BookEntryModel.create(this.modLoc(this.context().categoryId() + "/" + this.context().entryId()), this.context().entryName())
                .withIcon(Items.EXPERIENCE_BOTTLE)
                .withLocation(entryMap.get(icon))
                .withPages(
                        entity,
                        ritual,
                        description
                );
    }

    private BookEntryModel makeMercyGoatEntry(CategoryEntryMap entryMap, char icon) {
        this.context().entry("possess_goat");
        this.lang().add(this.context().entryName(), "Goat of Mercy");

        this.context().page("entity");
        var entity = BookEntityPageModel.create()
                .withEntityId("occultism:mercy_goat")
                .withScale(0.7f)
                .withText(this.context().pageText());
        this.lang().add(this.context().pageText(),
                """
                          **Drops**: [](item://occultism:cruelty_essence);
                        """);

        this.context().page("ritual");
        var ritual = BookRitualRecipePageModel.create()
                .withRecipeId1(this.modLoc("ritual/possess_goat"));

        this.context().page("description");
        var description = BookTextPageModel.create()
                .withText(this.context().pageText());
        this.lang().add(this.context().pageText(),
                """
                        In this ritual, a [#](%1$s)Goat of Mercy[#]() is summoned to be sacrificed. This is the only way to obtain the [](item://occultism:cruelty_essence).
                         Be sure of your actions, because they will forever mark your history.
                        """.formatted(COLOR_PURPLE));

        return BookEntryModel.create(this.modLoc(this.context().categoryId() + "/" + this.context().entryId()), this.context().entryName())
                .withIcon(OccultismItems.CRUELTY_ESSENCE)
                .withLocation(entryMap.get(icon))
                .withPages(
                        entity,
                        ritual,
                        description
                );
    }

    private BookEntryModel makePossessZombiePiglinEntry(CategoryEntryMap entryMap, char icon) {
        this.context().entry("possess_zombie_piglin");
        this.lang().add(this.context().entryName(), "Possessed Zombified Piglin");

        this.context().page("entity");
        var entity = BookEntityPageModel.create()
                .withEntityId("occultism:possessed_zombie_piglin")
                .withScale(0.7f)
                .withText(this.context().pageText());
        this.lang().add(this.context().pageText(),
                """
                          **Drops**: 1-4x [](item://occultism:demonic_meat) and
                          other body parts;
                        """);

        this.context().page("ritual");
        var ritual = BookRitualRecipePageModel.create()
                .withRecipeId1(this.modLoc("ritual/possess_zombie_piglin"));

        this.context().page("description");
        var description = BookTextPageModel.create()
                .withText(this.context().pageText());
        this.lang().add(this.context().pageText(),
                """
                        In this ritual an [#](%1$s)afrit[#]() will possess an [#](%1$s)Old Zombified Piglin[#](),
                         unifying the energies of the [#](%1$s)nether[#](), the power of the [#](%1$s)afrit[#](),
                          the material [#](%1$s)pork[#]() and the concept of the color [#](%1$s)pink[#]().
                         This is the only known method to obtain [](item://occultism:demonic_meat), its properties
                          prevent cooking but grant fire resistance to whoever consumes it.
                        """.formatted(COLOR_PURPLE));

        return BookEntryModel.create(this.modLoc(this.context().categoryId() + "/" + this.context().entryId()), this.context().entryName())
                .withIcon(OccultismItems.DEMONIC_MEAT)
                .withLocation(entryMap.get(icon))
                .withPages(
                        entity,
                        ritual,
                        description
                );
    }

    private BookEntryModel makePossessGuardianEntry(CategoryEntryMap entryMap, char icon) {
        this.context().entry("possess_guardian");
        this.lang().add(this.context().entryName(), "Possessed Guardian");

        this.context().page("entity");
        var entity = BookEntityPageModel.create()
                .withEntityId("occultism:possessed_guardian")
                .withScale(0.6f)
                .withText(this.context().pageText());
        this.lang().add(this.context().pageText(),
                """
                          **Drops**: Every coral, prismarine and some sea plants;
                        """);

        this.context().page("ritual");
        var ritual = BookRitualRecipePageModel.create()
                .withRecipeId1(this.modLoc("ritual/possess_guardian"));

        this.context().page("description");
        var description = BookTextPageModel.create()
                .withText(this.context().pageText());
        this.lang().add(this.context().pageText(),
                """
                        In this ritual an [#](%1$s)afrit[#]() will possess a [#](%1$s)Guardian[#](), 
                        harvesting energy from warm seas, corals are infused into the guardian's internal structure.\\ 
                        \\
                         Drops:
                          + [](item://minecraft:sea_pickle) or [](item://minecraft:kelp);
                          + [](item://minecraft:tube_coral), [](item://minecraft:brain_coral), [](item://minecraft:bubble_coral),
                          [](item://minecraft:fire_coral), [](item://minecraft:horn_coral) (all also in block and fan version)
                          + [](item://minecraft:prismarine_shard), [](item://minecraft:prismarine_crystals);
                        """.formatted(COLOR_PURPLE));

        return BookEntryModel.create(this.modLoc(this.context().categoryId() + "/" + this.context().entryId()), this.context().entryName())
                .withIcon(Items.TUBE_CORAL)
                .withLocation(entryMap.get(icon))
                .withPages(
                        entity,
                        ritual,
                        description
                );
    }

    private BookEntryModel makePossessBeeEntry(CategoryEntryMap entryMap, char icon) {
        this.context().entry("possess_bee");
        this.lang().add(this.context().entryName(), "Possessed Bee");

        this.context().page("entity");
        var entity = BookEntityPageModel.create()
                .withEntityId("occultism:possessed_bee")
                .withScale(1.0f)
                .withText(this.context().pageText());
        this.lang().add(this.context().pageText(),
                """
                          **Drops**: [](item://occultism:cursed_honey);
                        """);

        this.context().page("ritual");
        var ritual = BookRitualRecipePageModel.create()
                .withRecipeId1(this.modLoc("ritual/possess_bee"));

        this.context().page("description");
        var description = BookTextPageModel.create()
                .withText(this.context().pageText());
        this.lang().add(this.context().pageText(),
                """
                        In this ritual an [#](%1$s)djinni[#]() will possess an [#](%1$s)Bee[#](), Be careful,
                         a bee invoked by this way does not lose its stinger, always poison the target,
                         attacks faster and can summon other bees when it takes damage.
                         This is the only known method to obtain [](item://occultism:cursed_honey), eating will grants
                         a short regeneration buff.\\
                         \\
                         If this bee enters a hive, the djinni will return to [#](%1$s)The Other Place[#]().
                        """.formatted(COLOR_PURPLE));

        return BookEntryModel.create(this.modLoc(this.context().categoryId() + "/" + this.context().entryId()), this.context().entryName())
                .withIcon(OccultismItems.CURSED_HONEY)
                .withLocation(entryMap.get(icon))
                .withPages(
                        entity,
                        ritual,
                        description
                );
    }

    private BookEntryModel makeHordeDesertEntry(CategoryEntryMap entryMap, char icon) {
        this.context().entry("horde_desert");
        this.lang().add(this.context().entryName(), "Wild Horde Desert");

        this.context().page("husk");
        var husk = BookEntityPageModel.create()
                .withEntityId("occultism:wild_horde_husk")
                .withScale(1f)
                .withText(this.context().pageText());
        this.lang().add(this.context().pageText(),
                """
                        **Drops**: Items related to desert trials (See next page);
                        """);

        this.context().page("ritual");
        var ritual = BookRitualRecipePageModel.create()
                .withRecipeId1(this.modLoc("ritual/wild_husk"));

        this.context().page("parched");
        var parched = BookEntityPageModel.create()
                .withEntityId("occultism:wild_horde_parched")
                .withScale(1f)
                .withText(this.context().pageText());
        this.lang().add(this.context().pageText(),
                """
                        **Drops**: Items related to desert trials (See next page);
                        """);

        this.context().page("ritual2");
        var ritual2 = BookRitualRecipePageModel.create()
                .withRecipeId1(this.modLoc("ritual/wild_parched"));

        this.context().page("description");
        var description = BookTextPageModel.create()
                .withText(this.context().pageText());
        this.lang().add(this.context().pageText(),
                """
                        Husks and Parcheds summoned by any of this rituals can drop: [](item://minecraft:dune_armor_trim_smithing_template), [](item://minecraft:archer_pottery_sherd), [](item://minecraft:miner_pottery_sherd), [](item://minecraft:prize_pottery_sherd), [](item://minecraft:skull_pottery_sherd), [](item://minecraft:arms_up_pottery_sherd), [](item://minecraft:brewer_pottery_sherd).
                        """.formatted(COLOR_PURPLE));

        return BookEntryModel.create(this.modLoc(this.context().categoryId() + "/" + this.context().entryId()), this.context().entryName())
                .withIcon(Items.DUNE_ARMOR_TRIM_SMITHING_TEMPLATE)
                .withLocation(entryMap.get(icon))
                .withPages(
                        husk,
                        ritual,
                        parched,
                        ritual2,
                        description
                );
    }

    private BookEntryModel makeHordeDrownedEntry(CategoryEntryMap entryMap, char icon) {
        this.context().entry("horde_drowned");
        this.lang().add(this.context().entryName(), "Wild Horde Drowned");

        this.context().page("entity");
        var entity = BookEntityPageModel.create()
                .withEntityId("occultism:wild_horde_drowned")
                .withScale(1f)
                .withText(this.context().pageText());
        this.lang().add(this.context().pageText(),
                """
                        **Drops**: Items related to ocean trials (See next page);
                        """);

        this.context().page("ritual");
        var ritual = BookRitualRecipePageModel.create()
                .withRecipeId1(this.modLoc("ritual/wild_drowned"));

        this.context().page("description");
        var description = BookTextPageModel.create()
                .withText(this.context().pageText());
        this.lang().add(this.context().pageText(),
                """
                        Drowned summoned by this way can drop: [](item://minecraft:sniffer_egg), [](item://minecraft:turtle_egg), [](item://minecraft:trident), [](item://minecraft:angler_pottery_sherd), [](item://minecraft:shelter_pottery_sherd), [](item://minecraft:snort_pottery_sherd), [](item://minecraft:blade_pottery_sherd), [](item://minecraft:explorer_pottery_sherd), [](item://minecraft:mourner_pottery_sherd), [](item://minecraft:plenty_pottery_sherd).
                        """.formatted(COLOR_PURPLE));

        return BookEntryModel.create(this.modLoc(this.context().categoryId() + "/" + this.context().entryId()), this.context().entryName())
                .withIcon(Items.SNIFFER_EGG)
                .withLocation(entryMap.get(icon))
                .withPages(
                        entity,
                        ritual,
                        description
                );
    }

    private BookEntryModel makeHordeCreeperEntry(CategoryEntryMap entryMap, char icon) {
        this.context().entry("horde_creeper");
        this.lang().add(this.context().entryName(), "Wild Horde Creeper");

        this.context().page("entity");
        var entity = BookEntityPageModel.create()
                .withEntityId("occultism:wild_horde_creeper")
                .withScale(0.8f)
                .withText(this.context().pageText());
        this.lang().add(this.context().pageText(),
                """
                        **Drops**: Discs that the normal creeper drops when killed by Skeleton (See next page);
                        """);

        this.context().page("ritual");
        var ritual = BookRitualRecipePageModel.create()
                .withRecipeId1(this.modLoc("ritual/wild_creeper"));

        this.context().page("description");
        var description = BookTextPageModel.create()
                .withText(this.context().pageText());
        this.lang().add(this.context().pageText(),
                """
                        Creeper summoned in this ritual are CHARGED and will drop 1-3 of these discs: [](item://minecraft:music_disc_13), [](item://minecraft:music_disc_cat), [](item://minecraft:music_disc_blocks), [](item://minecraft:music_disc_chirp), [](item://minecraft:music_disc_far), [](item://minecraft:music_disc_mall), [](item://minecraft:music_disc_mellohi), [](item://minecraft:music_disc_stal), [](item://minecraft:music_disc_strad), [](item://minecraft:music_disc_ward), [](item://minecraft:music_disc_11), [](item://minecraft:music_disc_wait).
                        """.formatted(COLOR_PURPLE));

        return BookEntryModel.create(this.modLoc(this.context().categoryId() + "/" + this.context().entryId()), this.context().entryName())
                .withIcon(Items.MUSIC_DISC_CAT)
                .withLocation(entryMap.get(icon))
                .withPages(
                        entity,
                        ritual,
                        description
                );
    }

    private BookEntryModel makeHordeSilverfishEntry(CategoryEntryMap entryMap, char icon) {
        this.context().entry("horde_silverfish");
        this.lang().add(this.context().entryName(), "Wild Horde Silverfish");

        this.context().page("entity");
        var entity = BookEntityPageModel.create()
                .withEntityId("occultism:wild_horde_silverfish")
                .withScale(1f)
                .withText(this.context().pageText());
        this.lang().add(this.context().pageText(),
                """
                        **Drops**: Items related to ruins trials (See next page);
                        """);

        this.context().page("ritual");
        var ritual = BookRitualRecipePageModel.create()
                .withRecipeId1(this.modLoc("ritual/wild_silverfish"));

        this.context().page("description");
        var description = BookTextPageModel.create()
                .withText(this.context().pageText());
        this.lang().add(this.context().pageText(),
                """
                        Silverfish summoned by this way can drop: [](item://minecraft:music_disc_relic), [](item://minecraft:host_armor_trim_smithing_template), [](item://minecraft:raiser_armor_trim_smithing_template), [](item://minecraft:shaper_armor_trim_smithing_template), [](item://minecraft:wayfinder_armor_trim_smithing_template), [](item://minecraft:burn_pottery_sherd), [](item://minecraft:danger_pottery_sherd), [](item://minecraft:friend_pottery_sherd), [](item://minecraft:heart_pottery_sherd), [](item://minecraft:heartbreak_pottery_sherd), [](item://minecraft:howl_pottery_sherd), [](item://minecraft:sheaf_pottery_sherd).
                        """.formatted(COLOR_PURPLE));

        return BookEntryModel.create(this.modLoc(this.context().categoryId() + "/" + this.context().entryId()), this.context().entryName())
                .withIcon(Items.MUSIC_DISC_RELIC)
                .withLocation(entryMap.get(icon))
                .withPages(
                        entity,
                        ritual,
                        description
                );
    }

    private BookEntryModel makeHordeIllagerEntry(CategoryEntryMap entryMap, char icon) {
        this.context().entry("horde_illager");
        this.lang().add(this.context().entryName(), "Wild Illager Invasion");

        this.context().page("entity");
        var entity = BookEntityPageModel.create()
                .withEntityId("occultism:possessed_evoker")
                .withScale(0.7f)
                .withText(this.context().pageText());
        this.lang().add(this.context().pageText(),
                """
                          **Drops**: [](item://minecraft:totem_of_undying)
                        """);

        this.context().page("ritual");
        var ritual = BookRitualRecipePageModel.create()
                .withRecipeId1(this.modLoc("ritual/wild_horde_illager"));

        this.context().page("description");
        var description = BookTextPageModel.create()
                .withText(this.context().pageText());
        this.lang().add(this.context().pageText(),
                """
                        Summon a Wild Evoker and his henchmen to get [](item://minecraft:totem_of_undying), [](item://minecraft:vex_armor_trim_smithing_template) and [](item://minecraft:sentry_armor_trim_smithing_template).
                        """.formatted(COLOR_PURPLE));

        return BookEntryModel.create(this.modLoc(this.context().categoryId() + "/" + this.context().entryId()), this.context().entryName())
                .withIcon(Items.TOTEM_OF_UNDYING)
                .withLocation(entryMap.get(icon))
                .withPages(
                        entity,
                        ritual,
                        description
                );
    }

    private BookEntryModel makePossessWeakBreezeEntry(CategoryEntryMap entryMap, char icon) {
        this.context().entry("possess_weak_breeze");
        this.lang().add(this.context().entryName(), "The first key");

        this.context().page("entity");
        var entity = BookEntityPageModel.create()
                .withEntityId("occultism:possessed_weak_breeze")
                .withScale(0.5f)
                .withText(this.context().pageText());
        this.lang().add(this.context().pageText(),
                """
                          **Drops**: 1x [](item://minecraft:trial_key) and can drop other things (See next page);
                        """);

        this.context().page("ritual");
        var ritual = BookRitualRecipePageModel.create()
                .withRecipeId1(this.modLoc("ritual/wild_weak_breeze"));

        this.context().page("description");
        var description = BookTextPageModel.create()
                .withText(this.context().pageText());
        this.lang().add(this.context().pageText(),
                """
                        [](item://minecraft:breeze_rod) cannot be obtained from Wild Weak Breeze rods due to their fragile nature, but this version of Breeze hides some treasures and has a chance to drop: [](item://minecraft:guster_pottery_sherd), [](item://minecraft:scrape_pottery_sherd), [](item://minecraft:music_disc_creator_music_box) and [](item://minecraft:ominous_bottle).
                        """.formatted(COLOR_PURPLE));

        return BookEntryModel.create(this.modLoc(this.context().categoryId() + "/" + this.context().entryId()), this.context().entryName())
                .withIcon(Items.TRIAL_KEY)
                .withLocation(entryMap.get(icon))
                .withPages(
                        entity,
                        ritual,
                        description
                );
    }

    private BookEntryModel makePossessBreezeEntry(CategoryEntryMap entryMap, char icon) {
        this.context().entry("possess_breeze");
        this.lang().add(this.context().entryName(), "In the chamber");

        this.context().page("entity");
        var entity = BookEntityPageModel.create()
                .withEntityId("occultism:possessed_breeze")
                .withScale(1.0f)
                .withText(this.context().pageText());
        this.lang().add(this.context().pageText(),
                """
                          **Drops**: 1x [](item://minecraft:ominous_trial_key) and can drop other things (See next page);
                        """);

        this.context().page("ritual");
        var ritual = BookRitualRecipePageModel.create()
                .withRecipeId1(this.modLoc("ritual/wild_breeze"));

        this.context().page("description");
        var description = BookTextPageModel.create()
                .withText(this.context().pageText());
        this.lang().add(this.context().pageText(),
                """
                        The Wild Breeze has intrinsic Ominous Essence causing a drop of [](item://minecraft:ominous_trial_key). The [](item://minecraft:breeze_rod) from this enemy can survive after the battle and the extra loot is: [](item://minecraft:bolt_armor_trim_smithing_template), [](item://minecraft:guster_banner_pattern) and [](item://minecraft:music_disc_precipice).
                        """.formatted(COLOR_PURPLE));

        return BookEntryModel.create(this.modLoc(this.context().categoryId() + "/" + this.context().entryId()), this.context().entryName())
                .withIcon(Items.OMINOUS_TRIAL_KEY)
                .withLocation(entryMap.get(icon))
                .withPages(
                        entity,
                        ritual,
                        description
                );
    }

    private BookEntryModel makePossessStrongBreezeEntry(CategoryEntryMap entryMap, char icon) {
        this.context().entry("possess_strong_breeze");
        this.lang().add(this.context().entryName(), "Glorious Vault");

        this.context().page("entity");
        var entity = BookEntityPageModel.create()
                .withEntityId("occultism:possessed_strong_breeze")
                .withScale(1.0f)
                .withText(this.context().pageText());
        this.lang().add(this.context().pageText(),
                """
                          **Drops**: 1x [](item://minecraft:heavy_core) and can drop other things (See next page);
                        """);

        this.context().page("ritual");
        var ritual = BookRitualRecipePageModel.create()
                .withRecipeId1(this.modLoc("ritual/wild_strong_breeze"));

        this.context().page("description");
        var description = BookTextPageModel.create()
                .withText(this.context().pageText());
        this.lang().add(this.context().pageText(),
                """
                        The Wild Strong Breeze is 'Flow-Forged', granting a powerful version of the regular Breeze. This is the final target to obtain a [](item://minecraft:heavy_core) and as a bonus, you can get: [](item://minecraft:flow_armor_trim_smithing_template), [](item://minecraft:flow_banner_pattern), [](item://minecraft:flow_pottery_sherd) and [](item://minecraft:music_disc_creator).
                        """.formatted(COLOR_PURPLE));

        return BookEntryModel.create(this.modLoc(this.context().categoryId() + "/" + this.context().entryId()), this.context().entryName())
                .withIcon(Items.HEAVY_CORE)
                .withLocation(entryMap.get(icon))
                .withPages(
                        entity,
                        ritual,
                        description
                );
    }

    private BookEntryModel makePossessUnboundParrotEntry(CategoryEntryMap entryMap, char icon) {
        this.context().entry("possess_unbound_parrot");

        this.context().page("entity");
        var entity = BookEntityPageModel.create()
                .withEntityId("minecraft:parrot")
                .withText(this.context().pageText());

        this.context().page("ritual");
        var ritual = BookRitualRecipePageModel.create()
                .withRecipeId1(this.modLoc("ritual/possess_unbound_parrot"));

        this.context().page("description");
        var description = BookTextPageModel.create()
                .withText(this.context().pageText());

        this.context().page("description2");
        var description2 = BookTextPageModel.create()
                .withText(this.context().pageText());


        return BookEntryModel.create(this.modLoc(this.context().categoryId() + "/" + this.context().entryId()), this.context().entryName())
                .withIcon(this.modLoc("textures/gui/book/parrot.png"))
                .withLocation(entryMap.get(icon))
                .withPages(
                        entity,
                        ritual,
                        description,
                        description2
                );
    }

    private BookEntryModel makePossessRandomAnimalEntry(CategoryEntryMap entryMap, char icon) {
        this.context().entry("possess_random_animal");
        this.add(this.context().entryName(), "Possessed Random Animal");

        this.context().page("description");
        var description = BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText());
        this.add(this.context().pageTitle(), "Summon Random Animal");
        this.add(this.context().pageText(),
                """
                        In this type of ritual, a [#](%1$s)Spirit[#]() is summoned **as an untamed creature** to take the shape of a random animal.
                        Different rituals can be performed, each with their own respective animals, check in the ritual dummy or in the recipe output the possibilities.
                        \\
                        The animal can be interacted with as it's natural counterpart, including taming, breeding and loot.
                        """.formatted(COLOR_PURPLE));

        this.context().page("ritual_common");
        var ritualCommon = BookRitualRecipePageModel.create()
                .withRecipeId1(this.modLoc("ritual/possess_random_animal_common"));
        //no text

        this.context().page("ritual_water");
        var ritualWater = BookRitualRecipePageModel.create()
                .withRecipeId1(this.modLoc("ritual/possess_random_animal_water"));
        //no text

        this.context().page("ritual_small");
        var ritualSmall = BookRitualRecipePageModel.create()
                .withRecipeId1(this.modLoc("ritual/possess_random_animal_small"));
        //no text

        this.context().page("ritual_rideable");
        var ritualRideable = BookRitualRecipePageModel.create()
                .withRecipeId1(this.modLoc("ritual/possess_random_animal_rideable"));
        //no text

        this.context().page("ritual_villager");
        var ritualVillager = BookRitualRecipePageModel.create()
                .withRecipeId1(this.modLoc("ritual/possess_villager"));
        //no text

        this.context().page("ritual_special");
        var ritualSpecial = BookRitualRecipePageModel.create()
                .withRecipeId1(this.modLoc("ritual/possess_random_animal_special"));
        //no text


        return BookEntryModel.create(this.modLoc(this.context().categoryId() + "/" + this.context().entryId()), this.context().entryName())
                .withIcon(OccultismItems.MYSTERIOUS_EGG_ICON)
                .withLocation(entryMap.get(icon))
                .withPages(
                        description,
                        ritualCommon,
                        ritualWater,
                        ritualSmall,
                        ritualRideable,
                        ritualVillager,
                        ritualSpecial
                );
    }

    private BookEntryModel makeWildRandomAnimalEntry(CategoryEntryMap entryMap, char icon) {
        this.context().entry("wild_random_animal");
        this.add(this.context().entryName(), "Group of Random Animal");

        this.context().page("description");
        var description = BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText());
        this.add(this.context().pageTitle(), "Summon Group of Random Animal");
        this.add(this.context().pageText(),
                """
                        You have learned how to attract groups of random animals by changing
                         the pentacle to [#](%1$s)Osorin's Wild Calling[#]() and using a
                         [](item://occultism:spirit_attuned_gem) instead of a bound book of binding.
                        """.formatted(COLOR_PURPLE));

        this.context().page("ritual_common");
        var ritualCommon = BookRitualRecipePageModel.create()
                .withRecipeId1(this.modLoc("ritual/wild_random_animal_common"));
        //no text

        this.context().page("ritual_water");
        var ritualWater = BookRitualRecipePageModel.create()
                .withRecipeId1(this.modLoc("ritual/wild_random_animal_water"));
        //no text

        this.context().page("ritual_small");
        var ritualSmall = BookRitualRecipePageModel.create()
                .withRecipeId1(this.modLoc("ritual/wild_random_animal_small"));
        //no text

        this.context().page("ritual_rideable");
        var ritualRideable = BookRitualRecipePageModel.create()
                .withRecipeId1(this.modLoc("ritual/wild_random_animal_rideable"));
        //no text

        this.context().page("ritual_villager");
        var ritualVillager = BookRitualRecipePageModel.create()
                .withRecipeId1(this.modLoc("ritual/wild_villager"));
        //no text

        this.context().page("ritual_special");
        var ritualSpecial = BookRitualRecipePageModel.create()
                .withRecipeId1(this.modLoc("ritual/wild_random_animal_special"));
        //no text


        return BookEntryModel.create(this.modLoc(this.context().categoryId() + "/" + this.context().entryId()), this.context().entryName())
                .withIcon(OccultismItems.MYSTERIOUS_EGG_ICON)
                .withLocation(entryMap.get(icon))
                .withPages(
                        description,
                        ritualCommon,
                        ritualWater,
                        ritualSmall,
                        ritualRideable,
                        ritualVillager,
                        ritualSpecial
                );
    }

    private BookEntryModel makePossessUnboundOtherworldBirdEntry(CategoryEntryMap entryMap, char icon) {
        this.context().entry("possess_unbound_otherworld_bird");

        this.context().page("entity");
        var entity = BookEntityPageModel.create()
                .withEntityId("occultism:otherworld_bird")
                .withText(this.context().pageText());

        this.context().page("ritual");
        var ritual = BookRitualRecipePageModel.create()
                .withRecipeId1(this.modLoc("ritual/possess_unbound_otherworld_bird"));

        this.context().page("description");
        var description = BookTextPageModel.create()
                .withText(this.context().pageText());

        return BookEntryModel.create(this.modLoc(this.context().categoryId() + "/" + this.context().entryId()), this.context().entryName())
                .withIcon(this.modLoc("textures/gui/book/otherworld_bird.png"))
                .withLocation(entryMap.get(icon))
                .withPages(
                        entity,
                        ritual,
                        description
                );
    }
    //endregion
}
