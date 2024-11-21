package com.klikli_dev.occultism.datagen.book;

import com.klikli_dev.modonomicon.api.datagen.CategoryEntryMap;
import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.ModonomiconProviderBase;
import com.klikli_dev.modonomicon.api.datagen.book.BookEntryModel;
import com.klikli_dev.modonomicon.api.datagen.book.BookEntryParentModel;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.condition.BookAndConditionModel;
import com.klikli_dev.modonomicon.api.datagen.book.condition.BookEntryReadConditionModel;
import com.klikli_dev.modonomicon.api.datagen.book.condition.BookModLoadedConditionModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.*;
import com.klikli_dev.occultism.datagen.book.getting_started.RitualSatchelsEntry;
import com.klikli_dev.occultism.integration.modonomicon.pages.BookBindingCraftingRecipePageModel;
import com.klikli_dev.occultism.integration.modonomicon.pages.BookRitualRecipePageModel;
import com.klikli_dev.occultism.integration.modonomicon.pages.BookSpiritFireRecipePageModel;
import com.klikli_dev.occultism.registry.OccultismBlocks;
import com.klikli_dev.occultism.registry.OccultismItems;
import com.klikli_dev.theurgy.registry.ItemRegistry;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

public class GettingStartedCategory extends CategoryProvider {

    public static final String COLOR_PURPLE = "ad03fc";
    public static final String DEMONS_DREAM = "Demon's Dream";

    public static final String CATEGORY_ID = "getting_started";

    public GettingStartedCategory(ModonomiconProviderBase parent) {
        super(parent);
    }

    @Override
    protected String[] generateEntryMap() {
        //B=brush, N=Next Steps, P=iesnium pick
        //i=intro, r=divinationRod, ç = chalk, b=bowls, g=goggles,  I=infused pick O= tier 2 otherworld materials
        //  l=lamps, M=miner, D=Dim Mineshaft
        //d=demonsDream, h=healing, f=SpiritFire, c=candle, R=ritual, a=advancedChalks, ĝ=ritualsatchels
        //e=thirdEye, ạ=books of binding, m=more ritual, s=storage
        //C=book of calling, p=grey particles, S=spirits, w=possession, x=familiars, y=summoning, z=crafting
        return new String[]{
                "__________________________________",
                "__________________________________",
                "__________________________________",
                "__________________________P___D___",
                "__________________________________",
                "______ŕ___t___B_______g_I_O_l_M___",
                "__________________________________",
                "______i___r___ç_b_______s_________",
                "__________________________________",
                "______d___f_c_____R___a___ĝ_______",
                "__________________________________",
                "______e_h_____ạ_______m___________",
                "__________________________________",
                "______________Á_C___p_S___w_x_y_z_"
        };
    }

    @Override
    protected void generateEntries() {
        var introEntry = this.add(this.makeIntroEntry(this.entryMap, 'i'));

        var demonsDreamEntry = this.add(this.makeDemonsDreamEntry(this.entryMap, 'd'));
        demonsDreamEntry.withParent(BookEntryParentModel.create(introEntry.getId()));

        var spiritFireEntry = this.add(this.makeSpiritFireEntry(this.entryMap, 'f'));
        spiritFireEntry.withParent(BookEntryParentModel.create(demonsDreamEntry.getId()));

        var healingSpiritsEntry = this.add(this.makeHealingSpiritsEntry(this.entryMap, 'h'));
        healingSpiritsEntry.withParent(BookEntryParentModel.create(demonsDreamEntry.getId()));

        var thirdEyeEntry = this.add(this.makeThirdEyeEntry(this.entryMap, 'e'));
        thirdEyeEntry.withParent(BookEntryParentModel.create(demonsDreamEntry.getId()));

        var divinationRodEntry = this.add(this.makeDivinationRodEntry(this.entryMap, 'r'));
        divinationRodEntry.withParent(BookEntryParentModel.create(spiritFireEntry.getId()));

        var theurgyDivinationRodEntry = this.add(this.makeTheurgyDivinationRodsEntry(this.entryMap, 't'));
        theurgyDivinationRodEntry
                .withParent(BookEntryParentModel.create(divinationRodEntry.getId()))
                .withCondition(
                        BookAndConditionModel.create().withChildren(
                                BookEntryReadConditionModel.create()
                                        .withEntry(divinationRodEntry.getId()),
                                BookModLoadedConditionModel.create()
                                        .withModId("theurgy")
                        )
                )
                .hideWhileLocked(true);

        var candleEntry = this.add(this.makeCandleEntry(this.entryMap, 'c'));
        candleEntry.withParent(BookEntryParentModel.create(spiritFireEntry.getId()));

        var ritualPrepChalkEntry = this.add(this.makeRitualPrepChalkEntry(this.entryMap, 'ç'));
        ritualPrepChalkEntry.withParent(BookEntryParentModel.create(candleEntry.getId()));

        var brushEntry = this.add(this.makeBrushEntry(this.entryMap, 'B'));
        brushEntry.withParent(BookEntryParentModel.create(ritualPrepChalkEntry.getId()));

        var ritualPrepBowlEntry = this.add(this.makeRitualPrepBowlEntry(this.entryMap, 'b'));
        ritualPrepBowlEntry.withParent(BookEntryParentModel.create(ritualPrepChalkEntry.getId()));

        var booksOfBinding = this.add(this.makeBooksOfBindingEntry(this.entryMap, 'ạ'));
        booksOfBinding.withParent(BookEntryParentModel.create(candleEntry.getId()));

        var booksOfBindingAutomation = this.add(this.makeBooksOfBindingAutomationEntry(this.entryMap, 'Á'));
        booksOfBindingAutomation.withParent(BookEntryParentModel.create(booksOfBinding.getId()));

        var booksOfCalling = this.add(this.makeBooksOfCallingEntry(this.entryMap, 'C'));
        booksOfCalling.withParent(BookEntryParentModel.create(booksOfBinding.getId()));

        var ritualEntry = this.add(this.makeRitualEntry(this.entryMap, 'R'));
        ritualEntry
                .withParent(BookEntryParentModel.create(ritualPrepBowlEntry.getId()))
                .withParent(BookEntryParentModel.create(booksOfBinding.getId()));

        var advancedChalksEntry = this.add(this.makeChalksEntry(this.entryMap, 'a'));
        advancedChalksEntry.withParent(BookEntryParentModel.create(ritualEntry.getId()));

        var ritualSatchelsEntry = this.add(new RitualSatchelsEntry(this).generate('ĝ'));
        ritualSatchelsEntry.withParent(BookEntryParentModel.create(advancedChalksEntry.getId()))
                .withCondition(BookEntryReadConditionModel.create().withEntry(this.modLoc("pentacles/craft_djinni")));

        var moreRitualsEntry = this.add(this.makeMoreRitualsEntry(this.entryMap, 'm'));
        moreRitualsEntry.withParent(BookEntryParentModel.create(advancedChalksEntry.getId()));

        var greyParticlesEntry = this.add(this.makeGreyParticlesEntry(this.entryMap, 'p'));
        greyParticlesEntry.withParent(BookEntryParentModel.create(ritualEntry.getId()));

        var spiritsSubcategory = this.add(this.makeSpiritsSubcategoryEntry(this.entryMap, 'S'));
        spiritsSubcategory.withParent(BookEntryParentModel.create(greyParticlesEntry.getId()));

        var otherworldGoggles = this.add(this.makeOtherworldGogglesEntry(this.entryMap, 'g'));
        otherworldGoggles.withParent(BookEntryParentModel.create(advancedChalksEntry.getId()))
                .withCondition(BookEntryReadConditionModel.create().withEntry(this.modLoc("pentacles/craft_foliot")));

        var infusedPickaxe = this.add(this.makeInfusedPickaxeEntry(this.entryMap, 'I'));
        infusedPickaxe.withParent(BookEntryParentModel.create(otherworldGoggles.getId()))
                .withCondition(BookEntryReadConditionModel.create().withEntry(this.modLoc("pentacles/craft_djinni")));

        var iesnium = this.add(this.makeIesniumEntry(this.entryMap, 'O'));
        iesnium.withParent(BookEntryParentModel.create(infusedPickaxe.getId()));

        var iesniumPickaxe = this.add(this.makeIesniumPickaxeEntry(this.entryMap, 'P'));
        iesniumPickaxe.withParent(BookEntryParentModel.create(iesnium.getId()));

        var magicLampsEntry = this.add(this.makeMagicLampsEntry(this.entryMap, 'l'));
        magicLampsEntry.withParent(BookEntryParentModel.create(iesnium.getId()));

        var spiritMinersEntry = this.add(this.makeSpiritMinersEntry(this.entryMap, 'M'));
        spiritMinersEntry.withParent(BookEntryParentModel.create(magicLampsEntry.getId()));

        var mineshaftEntry = this.add(this.makeMineshaftEntry(this.entryMap, 'D'));
        mineshaftEntry.withParent(BookEntryParentModel.create(spiritMinersEntry.getId()));

        var storageEntry = this.add(this.makeStorageEntry(this.entryMap, 's'));
        storageEntry.withParent(BookEntryParentModel.create(advancedChalksEntry.getId()))
                .withCondition(BookEntryReadConditionModel.create().withEntry(this.modLoc("pentacles/craft_djinni")));

        var possessionRitualsEntry = this.add(this.makePossessionRitualsEntry(this.entryMap, 'w'));
        possessionRitualsEntry.withParent(BookEntryParentModel.create(moreRitualsEntry.getId()))
                .withCondition(BookEntryReadConditionModel.create().withEntry(this.modLoc("pentacles/possess_foliot")));

        var familiarRitualsEntry = this.add(this.makeFamiliarRitualsEntry(this.entryMap, 'x'));
        familiarRitualsEntry.withParent(BookEntryParentModel.create(moreRitualsEntry.getId()))
                .withCondition(BookEntryReadConditionModel.create().withEntry(this.modLoc("pentacles/possess_foliot")));

        var summoningRitualsEntry = this.add(this.makeSummoningRitualsEntry(this.entryMap, 'y'));
        summoningRitualsEntry.withParent(BookEntryParentModel.create(moreRitualsEntry.getId()))
                .withCondition(BookEntryReadConditionModel.create().withEntry(this.modLoc("pentacles/summon_foliot")));

        var craftingRitualsEntry = this.add(this.makeCraftingRitualsEntry(this.entryMap, 'z'));
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

    private BookEntryModel makeIntroEntry(CategoryEntryMap entryMap, char icon) {
        this.context().entry("intro");
        this.lang().add(this.context().entryName(), "About");
        this.lang().add(this.context().entryDescription(), "About using the Dictionary of Spirits");

        this.context().page("intro");
        var intro = BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText());
        this.lang().add(this.context().pageTitle(), "About");
        this.lang().add(this.context().pageText(),
                """
                        This book aims to introduce the novice reader to the most common summoning rituals and equip them with a list of spirit names to summon.
                        The authors advise caution in the summoning of the listed entities and does not take responsibility for any harm caused.
                        """);

        this.context().page("help");
        var help = BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText());
        this.lang().add(this.context().pageTitle(), "Getting Help");
        this.lang().add(this.context().pageText(),
                """
                        If you run into any trouble while playing Occultism, please join our Discord server and ask for help.
                        \\
                        \\
                        [Join us at https://discord.gg/trE4SHRXvb](https://discord.gg/trE4SHRXvb)
                        """);

        return BookEntryModel.create(this.modLoc(this.context().categoryId() + "/" + this.context().entryId()), this.context().entryName())
                .withDescription(this.context().entryDescription())
                .withIcon(OccultismItems.DICTIONARY_OF_SPIRITS_ICON.get())
                .withLocation(entryMap.get(icon))
                .withEntryBackground(0, 1)
                .withPages(
                        intro,
                        help
                );
    }

    private BookEntryModel makeDemonsDreamEntry(CategoryEntryMap entryMap, char icon) {
        this.context().entry("demons_dream");
        this.lang().add(this.context().entryName(), "Lifting the Veil");
        this.lang().add(this.context().entryDescription(), "Learn about the Otherworld and the Third Eye.");

        this.context().page("intro");
        var intro = BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText());
        this.lang().add(this.context().pageTitle(), "The Otherworld");
        this.lang().add(this.context().pageText(),
                """
                        Hidden from mere human eyes exists another plane of existence, another *dimension* if you will, the [#](%1$s)Otherworld[#]().
                        This world is populated with entities often referred to as [#](%1$s)Demons[#]().
                        """.formatted(COLOR_PURPLE));

        this.context().page("intro2");
        var intro2 = BookTextPageModel.create()
                .withText(this.context().pageText());
        this.lang().add(this.context().pageText(),
                """
                        These Demons possess a wide variety of powers and useful skills, and for centuries magicians have sought to summon them for their own gain.
                        The first step on the journey to successfully summoning such an Entity is to learn how to interact with the Otherworld.
                        """);

        this.context().page("spotlight");
        var spotlight = BookSpotlightPageModel.create()
                .withItem(Ingredient.of(OccultismItems.DATURA.get()))
                .withText(this.context().pageText());
        this.lang().add(this.context().pageText(),
                """
                        %1$s is a herb that gives humans the [#](%2$s)Third Eye[#](),
                        allowing them to see where the [#](%2$s)Otherworld[#]() intersects with our own.
                        Seeds can be found **by breaking grass**.
                        **Consuming** the grown fruit activates the ability *with a certain chance*.
                        """.formatted(DEMONS_DREAM, COLOR_PURPLE));

        this.context().page("harvest_effect");
        var harvestEffect = BookTextPageModel.create()
                .withText(this.context().pageText());
        this.lang().add(this.context().pageText(),
                """
                        An additional side effect of %1$s, is **the ability to interact with [#](%2$s)Otherworld[#]() materials**.
                        This is unique to %1$s, other ways to obtain [#](%2$s)Third Eye[#]() do not yield this ability.
                        While under the effect of %1$s you are able to **harvest** Otherstone as well as Otherworld trees.
                         """.formatted(DEMONS_DREAM, COLOR_PURPLE));

        this.context().page("datura_screenshot");
        var datureScreenshot = BookImagePageModel.create()
                .withImages(this.modLoc("textures/gui/book/datura_effect.png"));
        //no text

        this.context().page("note_on_spirit_fire");
        var spiritFire = BookTextPageModel.create()
                .withText(this.context().pageText());
        this.lang().add(this.context().pageText(),
                """
                        **Hint**: The otherworld materials you obtain by harvesting under the effects of[#](%2$s)Third Eye[#]() **can be obtained more easily using [](item://occultism:spirit_fire)**. Proceed with the next entry in this book to learn more about spirit fire.
                         """.formatted(DEMONS_DREAM, COLOR_PURPLE));

        this.context().page("spotlight2");
        var spotlight2 = BookSpotlightPageModel.create()
                .withItem(Ingredient.of(OccultismItems.DEMONS_DREAM_ESSENCE.get()))
                .withText(this.context().pageText());
        this.lang().add(this.context().pageText(),
                """
                        Multiple Demon's Dream fruits or seeds can be compressed into an essence that is much more potent. It *guarantees* the [#](%2$s)Third Eye[#]() and provides it for a longer amount of time, but comes with a lot of (positive and negative) side effects.
                            """.formatted(DEMONS_DREAM, COLOR_PURPLE)
        );

        this.context().page("recipe_essence");
        var recipeEssence = BookCraftingRecipePageModel.create()
                .withRecipeId1(this.modLoc("crafting/demons_dream_essence_from_fruit_or_seed"))
                .withText(this.context().pageText());
        this.lang().add(this.context().pageText(),
                """
                        Fruit and seeds can be mixed freely to create the essence.
                            """
        );

        this.context().page("spotlight3");
        var spotlight3 = BookSpotlightPageModel.create()
                .withItem(Ingredient.of(OccultismItems.DEMONS_DREAM_ESSENCE.get()))
                .withText(this.context().pageText());
        this.lang().add(this.context().pageText(),
                """
                        The essence can be purified in spirit fire (more on that later!) to obtain a version free from all negative side effects, while retaining the positive.
                            """.formatted(DEMONS_DREAM, COLOR_PURPLE)
        );

        this.context().page("recipe_essence_pure");
        var recipeEssencePure = BookSpiritFireRecipePageModel.create()
                .withRecipeId1(this.modLoc("spirit_fire/otherworld_essence"));
        //no text

        return BookEntryModel.create(this.modLoc(this.context().categoryId() + "/" + this.context().entryId()), this.context().entryName())
                .withDescription(this.context().entryDescription())
                .withIcon(OccultismItems.DATURA.get())
                .withLocation(entryMap.get(icon))
                .withPages(
                        intro,
                        intro2,
                        spotlight,
                        harvestEffect,
                        datureScreenshot,
                        spiritFire,
                        spotlight2,
                        recipeEssence,
                        spotlight3,
                        recipeEssencePure
                );
    }

    private BookEntryModel makeSpiritFireEntry(CategoryEntryMap entryMap, char icon) {
        this.context().entry("spirit_fire");
        this.lang().add(this.context().entryName(), "It burns!");
        this.lang().add(this.context().entryDescription(), "Or does it?");

        this.context().page("spotlight");
        var spotlight = BookSpotlightPageModel.create()
                .withItem(Ingredient.of(OccultismItems.SPIRIT_FIRE.get()))
                .withText(this.context().pageText());
        this.lang().add(this.context().pageText(),
                """
                        [#](%1$s)Spiritfire[#]() is a special type of fire that exists mostly in [#](%1$s)The Other Place[#]()
                        and does not harm living beings. Its special properties allow to use it to purify and convert
                        certain materials by burning them, without consuming them.
                        """.formatted(COLOR_PURPLE));

        this.context().page("spirit_fire_screenshot");
        var spiritFireScreenshot = BookImagePageModel.create()
                .withImages(this.modLoc("textures/gui/book/spiritfire_instructions.png"))
                .withText(this.context().pageText());
        this.lang().add(this.context().pageText(),
                """
                        Throw [](item://occultism:datura) to the ground and light it on fire with [](item://minecraft:flint_and_steel).
                         """);

        this.context().page("main_uses");
        var mainUses = BookTextPageModel.create()
                .withText(this.context().pageText());
        this.lang().add(this.context().pageText(),
                """
                        The main uses of [](item://occultism:spirit_fire) are to convert [](item://minecraft:diamond) into [](item://occultism:spirit_attuned_gem),
                        to get basic ingredients such as [](item://occultism:otherstone) and [Otherworld Saplings](item://occultism:otherworld_sapling_natural),
                        and to purify impure chalks.
                         """);

        this.context().page("otherstone_recipe");
        var otherstoneRecipe = BookSpiritFireRecipePageModel.create()
                .withRecipeId1(this.modLoc("spirit_fire/otherstone"))
                .withText(this.context().pageText());
        this.lang().add(this.context().pageText(),
                """
                        An easier way to obtain [](item://occultism:otherstone) than via divination.
                              """);

        this.context().page("otherworld_sapling_natural_recipe");
        var otherworldSaplingNaturalRecipe = BookSpiritFireRecipePageModel.create()
                .withRecipeId1(this.modLoc("spirit_fire/otherworld_sapling_natural"))
                .withText(this.context().pageText());
        this.lang().add(this.context().pageText(),
                """
                        An easier way to obtain [Otherworld Saplings](item://occultism:otherworld_sapling_natural) than via divination.
                              """);

        this.context().page("otherworld_ashes_recipe");
        var otherworldAshesRecipe = BookSpiritFireRecipePageModel.create()
                .withRecipeId1(this.modLoc("spirit_fire/otherworld_ashes"));
        //no text

        this.context().page("gem_recipe");
        var gemRecipe = BookSpiritFireRecipePageModel.create()
                .withRecipeId1(this.modLoc("spirit_fire/spirit_attuned_gem"));
        //no text

        this.context().page("otherflower_recipe");
        var otherflowerRecipe = BookSpiritFireRecipePageModel.create()
                .withRecipeId1(this.modLoc("spirit_fire/otherflower"))
                .withText(this.context().pageText());
        this.lang().add(this.context().pageText(),
                """
                        An easier way to clone any dye, mix this flower and the target color. You can also make ~~suspicious~~ delicious stews.
                              """);


        return BookEntryModel.create(this.modLoc(this.context().categoryId() + "/" + this.context().entryId()), this.context().entryName())
                .withDescription(this.context().entryDescription())
                .withIcon(OccultismItems.SPIRIT_FIRE.get())
                .withLocation(entryMap.get(icon))
                .withPages(
                        spotlight,
                        spiritFireScreenshot,
                        mainUses,
                        otherstoneRecipe,
                        otherworldSaplingNaturalRecipe,
                        otherworldAshesRecipe,
                        gemRecipe,
                        otherflowerRecipe
                );
    }

    private BookEntryModel makeHealingSpiritsEntry(CategoryEntryMap entryMap, char icon) {
        this.context().entry("healing_spirits");
        this.lang().add(this.context().entryName(), "Healing Spirits");
        this.lang().add(this.context().entryDescription(), "Fix up your spirit!");

        this.context().page("spotlight");
        var spotlight = BookSpotlightPageModel.create()
                .withItem(Ingredient.of(OccultismItems.DATURA.get()))
                .withText(this.context().pageText());
        this.lang().add(this.context().pageText(),
                """
                        Right-click a spirit with [](item://occultism:datura) to heal it. 
                        \\
                        \\
                        This will work on **Familiars**, **Summoned Spirits** and also **Possessed Mobs**.
                        """);

        this.context().page("spotlight2");
        var spotlight2 = BookSpotlightPageModel.create()
                .withItem(Ingredient.of(OccultismItems.DEMONS_DREAM_ESSENCE.get()))
                .withText(this.context().pageText());
        this.lang().add(this.context().pageText(),
                """
                        When compressing Demon's Dream fruits or seeds into essence, a much stronger instant healing effect can be achieved. This comes at the cost of efficiency: Feeding 9 fruits to a spirit in succession will heal it more than feeding it 9 fruits worth of essence.
                        """);

        this.context().page("spotlight3");
        var spotlight3 = BookSpotlightPageModel.create()
                .withItem(Ingredient.of(OccultismItems.OTHERWORLD_ESSENCE.get()))
                .withText(this.context().pageText());
        this.lang().add(this.context().pageText(),
                """
                        Purifying the Demon's Dream Essence will yield a version that heals even more, negating the efficiency loss.
                        """);

        return BookEntryModel.create(this.modLoc(this.context().categoryId() + "/" + this.context().entryId()), this.context().entryName())
                .withDescription(this.context().entryDescription())
                .withIcon(Items.SPLASH_POTION)
                .withLocation(entryMap.get(icon))
                .withPages(
                        spotlight,
                        spotlight2,
                        spotlight3
                );
    }

    private BookEntryModel makeThirdEyeEntry(CategoryEntryMap entryMap, char icon) {
        this.context().entry("third_eye");
        this.lang().add(this.context().entryName(), "The Third Eye");
        this.lang().add(this.context().entryDescription(), "Do you see now?");

        this.context().page("about");
        var about = BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText());
        this.lang().add(this.context().pageTitle(), "Third Eye");
        this.lang().add(this.context().pageText(),
                """
                        The ability to see beyond the physical world is referred to as [#](%1$s)Third Eye[#]().
                        Humans do not possess such an ability to see [#](%1$s)beyond the veil[#](),
                        however with certain substances and contraptions the knowledgeable summoner can work around this limitation.
                         """.formatted(COLOR_PURPLE));

        this.context().page("how_to_obtain");
        var howToObtain = BookTextPageModel.create()
                .withText(this.context().pageText());
        this.lang().add(this.context().pageText(),
                """
                        The most comfortable, and most *expensive*, way to obtain this ability, is to wear spectacles
                        infused with spirits, that *lend* their sight to the wearer.
                        A slightly more nauseating, but **very affordable** alternative is the consumption of certain herbs,
                        [%1$s](entry://occultism:dictionary_of_spirits/getting_started/demons_dream) most prominent among them.
                         """.formatted(DEMONS_DREAM));

        this.context().page("otherworld_goggles");
        var otherworldGoggles = BookSpotlightPageModel.create()
                .withItem(Ingredient.of(OccultismItems.OTHERWORLD_GOGGLES.get()))
                .withText(this.context().pageText());
        this.lang().add(this.context().pageText(),
                """
                        [These goggles](entry://occultism:dictionary_of_spirits/crafting_rituals/craft_otherworld_goggles) allow to see even more hidden Otherworld blocks,
                        however they do not allow harvesting those materials.
                        Low-tier materials can be harvested by consuming [%1$s](entry://occultism:dictionary_of_spirits/getting_started/demons_dream),
                        but more valuable materials require special tools.
                                """.formatted(DEMONS_DREAM));

        return BookEntryModel.create(this.modLoc(this.context().categoryId() + "/" + this.context().entryId()), this.context().entryName())
                .withDescription(this.context().entryDescription())
                .withIcon(this.modLoc("textures/mob_effect/third_eye.png"))
                .withLocation(entryMap.get(icon))
                .withPages(about, howToObtain, otherworldGoggles);
    }

    private BookEntryModel makeDivinationRodEntry(CategoryEntryMap entryMap, char icon) {
        this.context().entry("divination_rod");
        this.lang().add(this.context().entryName(), "Divination Rod");
        this.lang().add(this.context().entryDescription(), "Obtaining otherworld materials");

        this.context().page("intro");
        var intro = BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText());
        this.lang().add(this.context().pageTitle(), "Divination");
        this.lang().add(this.context().pageText(),
                """
                        To make it easier to get started, the materials obtained by divination now also have crafting recipes.
                        **If you want the full experience, skip the following recipe page and move on to the
                        [divination instructions](entry://occultism:dictionary_of_spirits/getting_started/divination_rod@divination_instructions).**
                                """);

        this.context().page("otherstone_recipe");
        var otherstoneRecipe = BookSpiritFireRecipePageModel.create()
                .withRecipeId1(this.modLoc("spirit_fire/otherstone"));
        //no text

        this.context().page("otherworld_sapling_natural_recipe");
        var otherworldSaplingNaturalRecipe = BookSpiritFireRecipePageModel.create()
                .withRecipeId1(this.modLoc("spirit_fire/otherworld_sapling_natural"))
                .withText(this.context().pageText());
        this.lang().add(this.context().pageText(),
                """
                        **Beware**: the tree growing from the sapling will look like a normal oak tree.
                        You need to activate the [Third Eye](entry://occultism:dictionary_of_spirits/getting_started/demons_dream)
                        to harvest the Otherworld Logs and Leaves.
                                """);

        this.context().page("divination_rod");
        var divinationRod = BookSpotlightPageModel.create()
                .withItem(Ingredient.of(OccultismItems.DIVINATION_ROD.get()))
                .withText(this.context().pageText())
                .withAnchor("divination_instructions");
        this.lang().add(this.context().pageText(),
                """
                        Otherworld materials play an important role in interacting with spirits.
                        As they are rare and not visible to the naked eye, finding them requires special tools.
                        The divination rod allows to find Otherworld materials based on their similarities to materials common to our world.
                                 """);

        this.context().page("spirit_attuned_gem_recipe");
        var spiritAttunedGemRecipe = BookSpiritFireRecipePageModel.create()
                .withRecipeId1(this.modLoc("spirit_fire/spirit_attuned_gem"));

        this.context().page("divination_rod_recipe");
        var divinationRodRecipe = BookCraftingRecipePageModel.create()
                .withRecipeId1(this.modLoc("crafting/divination_rod"));

        this.context().page("about_divination_rod");
        var aboutDivinationRod = BookTextPageModel.create()
                .withText(this.context().pageText());
        this.lang().add(this.context().pageText(),
                """
                        The divination rod uses a spirit attuned gem attached to a wooden rod.
                        The gem resonates with the chosen material, and this movement is amplified by the wooden rod,
                        allowing to detect nearby Otherworld materials.   \s
                           \s
                           \s
                        The rod works by detecting resonance between real world and Otherworld materials.
                        Attuned the rod to a real world material, and it will find the corresponding Otherworld block.
                                 """);

        this.context().page("how_to_use");
        var howToUse = BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText());
        this.lang().add(this.context().pageTitle(), "Use of the Rod");
        this.lang().add(this.context().pageText(),
                """
                        [#](%1$s)Shift-right-click[#]() a block to attune the rod to the corresponding Otherworld block.
                        - [](item://minecraft:andesite): [](item://occultism:otherstone)
                        - [](item://minecraft:oak_wood):  [](item://occultism:otherworld_log)
                        - [](item://minecraft:oak_leaves): [](item://occultism:otherworld_leaves)
                        - [](item://minecraft:netherrack): [](item://occultism:iesnium_ore)

                        Then [#](%1$s)right-click[#]() and hold until the rod animation finishes.""".formatted(COLOR_PURPLE));

        this.context().page("how_to_use2");
        var howToUse2 = BookTextPageModel.create()
                .withText(this.context().pageText());
        this.lang().add(this.context().pageText(),
                """
                        After the animation finishes, the closest **found block will be highlighted
                        with white lines and can be seen through other blocks**.
                        Additionally you can watch the crystals for hints: a white crystal indicates no target blocks found,
                        a fully purple block means the found block is nearby. Mixes between white and purple show
                        that the target is rather far away.""");

        this.context().page("how_to_use3");
        var howToUse3 = BookTextPageModel.create()
                .withText(this.context().pageText());
        this.lang().add(this.context().pageText(),
                """
                        [#](%1$s)Right-clicking[#]() without holding after a successful search will show the last found target block again.
                        \\
                        \\
                        If the mod *"Theurgy"* is installed the rod will not highlight the target block, but instead send a particle effect in the direction of the target block.
                        """.formatted(COLOR_PURPLE));

        this.context().page("divination_rod_screenshots");
        var divinationRodScreenshots = BookImagePageModel.create()
                .withImages(
                        this.modLoc("textures/gui/book/rod_far.png"),
                        this.modLoc("textures/gui/book/rod_mid.png"),
                        this.modLoc("textures/gui/book/rod_near.png")
                )
                .withText(this.context().pageText());
        this.lang().add(this.context().pageText(),
                """
                        White means nothing was found.
                        The more purple you see, the closer you are.
                        """);

        this.context().page("troubleshooting");
        var troubleshooting = BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText());
        this.lang().add(this.context().pageTitle(), "Troubleshooting");
        this.lang().add(this.context().pageText(),
                """
                        If the rod does not create highlighted blocks for you, you can try to:
                        - If you have theurgy mod installed, then a particle effect will be used instead, set the particles to all or decreased in the video settings
                        - Open occultism-client.toml in your instance's /config folder and set useAlternativeDivinationRodRenderer = true
                        """);


        this.context().page("otherworld_groves");
        var otherworldGroves = BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText());
        this.lang().add(this.context().pageTitle(), "Otherworld Groves");
        this.lang().add(this.context().pageText(),
                """
                        Otherworld Groves are lush, overgrown caves, with [#](%1$s)Otherworld Trees[#](),
                        and walls of [](item://occultism:otherstone), and represent the fastest way to get everything one
                        needs to get set up as a summoner.
                        To find them, attune your divination rod to Otherworld leaves
                        or logs, as unlike Otherstone, they only spawn in these groves.
                         """.formatted(COLOR_PURPLE));

        this.context().page("otherworld_groves_2");
        var otherworldGroves2 = BookTextPageModel.create()
                .withText(this.context().pageText());
        this.lang().add(this.context().pageText(),
                """
                        **Hint:** In the Overworld, look **down**.
                          """);

        this.context().page("otherworld_trees");
        var otherworldTrees = BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText());
        this.lang().add(this.context().pageTitle(), "Otherworld Trees");
        this.lang().add(this.context().pageText(),
                """
                        Otherworld trees grow naturally in Otherworld Groves. To the naked eye they appear as oak trees,
                        but to the Third Eye they reveal their true nature.   \s
                        **Important:** Otherworld Saplings can only be obtained by breaking the leaves manually, naturally only oak saplings drop.
                         """);

        this.context().page("otherworld_trees_2");
        var otherworldTrees2 = BookTextPageModel.create()
                .withText(this.context().pageText());
        this.lang().add(this.context().pageText(),
                """
                        Trees grown from Stable Otherworld Saplings as obtained from spirit traders do not have that limitation.
                         """);

        this.context().page("config");
        var config = BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText());
        this.lang().add(this.context().pageTitle(), "Extra Config");
        this.lang().add(this.context().pageText(),
                """
                        An additional function of the Divination Rod is to locate any ore,
                         however this is not a default function and needs to be enabled,
                         as we recommend using the Greedy familiar or Theurgy mod for this type of divination.
                         If you want to enable this feature directly in Occultism Divination Rod, check
                         "Server Configuration > Items" and set "Divination c:ores" to "on".
                          """);

        return BookEntryModel.create(this.modLoc(this.context().categoryId() + "/" + this.context().entryId()), this.context().entryName())
                .withDescription(this.context().entryDescription())
                .withIcon(OccultismItems.DIVINATION_ROD.get())
                .withLocation(entryMap.get(icon))
                .withPages(
                        intro,
                        otherstoneRecipe,
                        otherworldSaplingNaturalRecipe,
                        divinationRod,
                        spiritAttunedGemRecipe,
                        divinationRodRecipe,
                        aboutDivinationRod,
                        howToUse,
                        howToUse2,
                        howToUse3,
                        divinationRodScreenshots,
                        troubleshooting,
                        otherworldGroves,
                        otherworldGroves2,
                        otherworldTrees,
                        otherworldTrees2,
                        config
                );
    }


    private BookEntryModel makeTheurgyDivinationRodsEntry(CategoryEntryMap entryMap, char icon) {
        this.context().entry("theurgy_divination_rod");

        this.lang().add(this.context().entryName(), "More Divination Rods");
        this.lang().add(this.context().entryDescription(), "Finding other ores and resources.");

        this.context().page("intro");
        var intro = BookSpotlightPageModel.create()
                .withItem(Ingredient.of(ItemRegistry.DIVINATION_ROD_T1.get()))
                .withText(this.context().pageText());

        this.lang().add(this.context().pageText(),
                """
                        While the [](item://occultism:divination_rod) is a great tool for finding [#](%1$s)Otherworld Materials[#](), it would be useful to have a way to find *all other* ores and resources as well.
                        \\
                        \\
                        This is where the Theurgy Divination Rod comes in.
                                """.formatted(COLOR_PURPLE));

        this.context().page("recipe_rod");
        var recipeRod = BookCraftingRecipePageModel.create()
                .withRecipeId1("theurgy:crafting/shaped/divination_rod_t1");

        this.context().page("more_info");
        var moreInfo = BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText());

        this.lang().add(this.context().pageTitle(), "More Information");
        this.lang().add(this.context().pageText(),
                """
                        To find out more about the Theurgy Divination Rod, check out *"The Hermetica"*, the Guidebook for Theurgy.
                        [This Entry](entry://theurgy:the_hermetica/getting_started/about_divination_rods) has more information about the Theurgy Divination Rod.
                        """);

        this.context().page("recipe_hermetica");
        var recipeHermetica = BookCraftingRecipePageModel.create()
                .withRecipeId1("theurgy:crafting/shapeless/the_hermetica");

        return BookEntryModel.create(this.modLoc(this.context().categoryId() + "/" + this.context().entryId()), this.context().entryName())
                .withDescription(this.context().entryDescription())
                .withIcon(ItemRegistry.DIVINATION_ROD_T1.get())
                .withLocation(entryMap.get(icon))
                .withPages(
                        intro,
                        recipeRod,
                        moreInfo,
                        recipeHermetica
                );
    }

    private BookEntryModel makeCandleEntry(CategoryEntryMap entryMap, char icon) {
        this.context().entry("candle");
        this.lang().add(this.context().entryName(), "Candles");
        this.lang().add(this.context().entryDescription(), "Let there be light!");

        this.context().page("intro");
        var intro = BookSpotlightPageModel.create()
                .withItem(Ingredient.of(OccultismBlocks.LARGE_CANDLE.get()))
                .withText(this.context().pageText());
        this.lang().add(this.context().pageText(),
                """
                        Candles provide stability to rituals and are an important part of almost all pentacles.
                        **Large Candles also act like bookshelves for enchantment purposes.**
                        \\
                        \\
                        Candles from Minecraft and other Mods may be used in place of Occultism candles.
                            """);

        this.context().page("tallow");
        var tallow = BookSpotlightPageModel.create()
                .withItem(Ingredient.of(OccultismItems.TALLOW.get()))
                .withText(this.context().pageText());
        this.lang().add(this.context().pageText(),
                """
                        Key ingredient for large candles. Kill large animals like pigs, cows or sheep with a [](item://occultism:butcher_knife)
                        to harvest [](item://occultism:tallow).
                            """);

        this.context().page("cleaver_recipe");
        var cleaverRecipe = BookCraftingRecipePageModel.create()
                .withRecipeId1(this.modLoc("crafting/butcher_knife"));
        //no text

        this.context().page("candle_recipe");
        var candleRecipe = BookCraftingRecipePageModel.create()
                .withRecipeId1(this.modLoc("crafting/large_candle"));
        //no text

        this.context().page("color_candle");
        var colorCandle = BookTextPageModel.create()
                .withText(this.context().pageText());
        this.lang().add(this.context().pageText(),
                """
                        You can use a dye and the [](item://occultism:large_candle) to mix then in shapeless craft process to get a colored large candle.
                        \\
                        Available in all the 16 minecraft dyes.
                        """);

        this.context().page("lit_candle");
        var litCandle = BookTextPageModel.create()
                .withText(this.context().pageText());
        this.lang().add(this.context().pageText(),
                """
                        Just like the candles from Minecraft, [](item://occultism:large_candle) and colored versions can be lit, turning in a great light source. 
                        \\
                        In addiction, you can use a [](item://minecraft:torch), [](item://minecraft:soul_torch), [](item://minecraft:redstone_torch) or [](item://occultism:spirit_torch) to change the type of fire.
                        \\
                        Also can be waterlogged.
                        """);

        return BookEntryModel.create(this.modLoc(this.context().categoryId() + "/" + this.context().entryId()), this.context().entryName())
                .withDescription(this.context().entryDescription())
                .withIcon(OccultismBlocks.LARGE_CANDLE.get())
                .withLocation(entryMap.get(icon))
                .withPages(
                        intro,
                        tallow,
                        cleaverRecipe,
                        candleRecipe,
                        colorCandle,
                        litCandle
                );
    }

    private BookEntryModel makeRitualPrepChalkEntry(CategoryEntryMap entryMap, char icon) {
        this.context().entry("ritual_prep_chalk");
        this.lang().add(this.context().entryName(), "Ritual Preparations: Chalks");
        this.lang().add(this.context().entryDescription(), "Signs to find them, Signs to bring them all, and in the darkness bind them.");

        this.context().page("intro");
        var intro = BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText());
        this.lang().add(this.context().pageTitle(), "Ritual Preparations: Chalks");
        this.lang().add(this.context().pageText(),
                """
                        To summon spirits from the [#](%1$s)Other Place[#]() in *relative* safety,
                        you need to draw a fitting pentacle using chalk to contain their powers.
                         """.formatted(COLOR_PURPLE));

        this.context().page("white_chalk");
        var whiteChalkSpotlight = BookSpotlightPageModel.create()
                .withItem(Ingredient.of(OccultismItems.CHALK_WHITE.get()))
                .withText(this.context().pageText())
                .withAnchor("white_chalk");
        this.lang().add(this.context().pageText(),
                """
                        White chalk is used to draw the most basic pentacles, such as for our first ritual.
                        \\
                        \\
                        More powerful summonings require appropriate more advanced chalk, see [Chalks](entry://occultism:dictionary_of_spirits/getting_started/chalks) for more information.
                            """);

        this.context().page("burnt_otherstone_recipe");
        var burntOtherstoneRecipe = BookSmeltingRecipePageModel.create()
                .withRecipeId1(this.modLoc("smelting/burnt_otherstone"));
        //no text

        this.context().page("otherworld_ashes_recipe");
        var otherworldAshesRecipe = BookSpiritFireRecipePageModel.create()
                .withRecipeId1(this.modLoc("spirit_fire/otherworld_ashes"));
        //no text
        this.context().page("impure_white_chalk_recipe");
        var impureWhiteChalkRecipe = BookCraftingRecipePageModel.create()
                .withRecipeId1(this.modLoc("crafting/chalk_white_impure"));
        //no text

        this.context().page("white_chalk_recipe");
        var whiteChalkRecipe = BookSpiritFireRecipePageModel.create()
                .withRecipeId1(this.modLoc("spirit_fire/chalk_white"));
        //no text

        this.context().page("usage");
        var usage = BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText());
        this.lang().add(this.context().pageTitle(), "Usage");
        this.lang().add(this.context().pageText(),
                """
                        Right-click on a block with the chalk to draw a single glyph. For decorative purposes you can repeatedly click a block to cycle through glyphs. The shown glyph does not matter for the ritual, only the color.
                         """.formatted(COLOR_PURPLE));

        return BookEntryModel.create(this.modLoc(this.context().categoryId() + "/" + this.context().entryId()), this.context().entryName())
                .withDescription(this.context().entryDescription())
                .withIcon(OccultismItems.CHALK_WHITE.get())
                .withLocation(entryMap.get(icon))
                .withPages(
                        intro,
                        whiteChalkSpotlight,
                        burntOtherstoneRecipe,
                        otherworldAshesRecipe,
                        impureWhiteChalkRecipe,
                        whiteChalkRecipe,
                        usage
                );
    }

    private BookEntryModel makeRitualPrepBowlEntry(CategoryEntryMap entryMap, char icon) {
        this.context().entry("ritual_prep_bowl");
        this.lang().add(this.context().entryName(), "Ritual Preparations: Sacrificial Bowls");
        this.lang().add(this.context().entryDescription(), "There is no power without sacrifice.");

        this.context().page("sacrificial_bowl");
        var sacrificialBowlSpotlight = BookSpotlightPageModel.create()
                .withItem(Ingredient.of(OccultismBlocks.SACRIFICIAL_BOWL.get()))
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText());
        this.lang().add(this.context().pageTitle(), "Ritual Preparations: Sacrificial Bowls");
        this.lang().add(this.context().pageText(),
                """
                        These bowls are used to place the items we will sacrifice as part of a ritual and you will need a handful of them.
                        Note: Their exact placement in the ritual does not matter - just keep them within 8 blocks horizontally of the pentacle center!
                             """);

        this.context().page("sacrificial_bowl_recipe");
        var sacrificialBowlRecipe = BookCraftingRecipePageModel.create()
                .withRecipeId1(this.modLoc("crafting/sacrificial_bowl"))
                .withText(this.context().pageText());
        this.lang().add(this.context().pageText(),
                """
                        You can mix a sacrificial bowl with a copper or silver ingot to create variations with the same functionality.
                        """);


        this.context().page("golden_sacrificial_bowl");
        var goldenSacrificialBowlSpotlight = BookSpotlightPageModel.create()
                .withItem(Ingredient.of(OccultismBlocks.GOLDEN_SACRIFICIAL_BOWL.get()))
                .withText(this.context().pageText());
        this.lang().add(this.context().pageText(),
                """
                        Once everything has been set up and you are ready to start, this special sacrificial bowl is used to activate the ritual by [#](%1$s)right-clicking[#]() it with the activation item,
                        usually a [Book of Binding](entry://getting_started/books_of_binding).
                             """.formatted(COLOR_PURPLE));

        this.context().page("golden_sacrificial_bowl_recipe");
        var goldenSacrificialBowlRecipe = BookCraftingRecipePageModel.create()
                .withRecipeId1(this.modLoc("crafting/golden_sacrificial_bowl"));
        //no text

        return BookEntryModel.create(this.modLoc(this.context().categoryId() + "/" + this.context().entryId()), this.context().entryName())
                .withDescription(this.context().entryDescription())
                .withIcon(OccultismBlocks.GOLDEN_SACRIFICIAL_BOWL.get())
                .withLocation(entryMap.get(icon))
                .withPages(
                        sacrificialBowlSpotlight,
                        sacrificialBowlRecipe,
                        goldenSacrificialBowlSpotlight,
                        goldenSacrificialBowlRecipe
                );
    }

    private BookEntryModel makeBooksOfBindingEntry(CategoryEntryMap entryMap, char icon) {
        this.context().entry("books_of_binding");
        this.lang().add(this.context().entryName(), "Books of Binding");
        this.lang().add(this.context().entryDescription(), "Or how to identify your spirit");

        this.context().page("intro");
        var intro = BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText());
        this.lang().add(this.context().pageTitle(), "Books of Binding");
        this.lang().add(this.context().pageText(),
                """
                        To call forth a spirit, a [#](%1$s)Book of Binding[#]() must be used in the ritual.
                        There is a type of book corresponding to each type (or tier) of spirit.
                        To identify a spirit to summon, it's name must be written in the [#](%1$s)Book of Binding[#](), resulting in a [#](%1$s)Bound Book of Binding[#]() that can be used in the ritual.
                         """.formatted(COLOR_PURPLE));

        this.context().page("intro2");
        var intro2 = BookTextPageModel.create()
                .withText(this.context().pageText());
        this.lang().add(this.context().pageText(),
                """
                        **Note:** *The spirit names are eye candy only*, that means they are not relevant for the recipe. As long as you have the right spirit type in your book of binding it can be used.
                         """.formatted(COLOR_PURPLE));

        this.context().page("purified_ink_recipe");
        var purifiedInkRecipe = BookSpiritFireRecipePageModel.create()
                .withRecipeId1(this.modLoc("spirit_fire/purified_ink"))
                .withText(this.context().pageText());
        this.lang().add(this.context().pageText(),
                """
                        In order to craft [#](%1$s)Books of Binding[#]() to summon spirits, you need purified ink. Simply drop any black dye into [](item://occultism:spirit_fire) to purify it.
                            """.formatted(COLOR_PURPLE));

        this.context().page("awakened_feather_recipe");
        var awakenedFeatherRecipe = BookSpiritFireRecipePageModel.create()
                .withRecipeId1(this.modLoc("spirit_fire/awakened_feather"))
                .withText(this.context().pageText());
        this.lang().add(this.context().pageText(),
                """
                        In order to craft [#](%1$s)Books of Binding[#]() to summon spirits, you also need awakened feather. Simply drop any feather into [](item://occultism:spirit_fire) to awakened it.
                            """.formatted(COLOR_PURPLE));

        this.context().page("taboo_book_recipe");
        var tabooBookRecipe = BookSpiritFireRecipePageModel.create()
                .withRecipeId1(this.modLoc("spirit_fire/taboo_book"))
                .withText(this.context().pageText());
        this.lang().add(this.context().pageText(),
                """
                        Lastly you need taboo book to craft [#](%1$s)Books of Binding[#]() to summon spirits. Simply drop a book into [](item://occultism:spirit_fire) to get it.
                        """.formatted(COLOR_PURPLE));

        this.context().page("book_of_binding_foliot_recipe");
        var bookOfBindingFoliotRecipe = BookCraftingRecipePageModel.create()
                .withRecipeId1(this.modLoc("crafting/book_of_binding_foliot"))
                .withText(this.context().pageText());
        this.lang().add(this.context().pageText(),
                """
                        Craft a book of binding that will be used to call forth a [#](%1$s)Foliot[#]() spirit.
                           """.formatted(COLOR_PURPLE));

        this.context().page("book_of_binding_bound_foliot_recipe");
        var bookOfBindingBoundFoliotRecipe = BookBindingCraftingRecipePageModel.create()
                .withRecipeId1()
                .withUnboundBook(OccultismItems.BOOK_OF_BINDING_FOLIOT.toStack())
                .withText(this.context().pageText());
        this.lang().add(this.context().pageText(),
                """
                        Add the name of the spirit to summon to your book of binding by crafting it with the Dictionary of Spirits. The Dictionary will not be used up.
                           """);

        this.context().page("book_of_binding_djinni_recipe");
        var bookOfBindingDjinniRecipe = BookCraftingRecipePageModel.create()
                .withRecipeId1(this.modLoc("crafting/book_of_binding_djinni"));

        this.context().page("book_of_binding_bound_djinni_recipe");
        var bookOfBoundBindingDjinniRecipe = BookBindingCraftingRecipePageModel.create()
                .withRecipeId1()
                .withUnboundBook(OccultismItems.BOOK_OF_BINDING_DJINNI.toStack());
        //no text

        this.context().page("book_of_binding_afrit_recipe");
        var bookOfBindingAfritRecipe = BookCraftingRecipePageModel.create()
                .withRecipeId1(this.modLoc("crafting/book_of_binding_afrit"));
        //no text

        this.context().page("book_of_binding_bound_afrit_recipe");
        var bookOfBoundBindingAfritRecipe = BookBindingCraftingRecipePageModel.create()
                .withRecipeId1()
                .withUnboundBook(OccultismItems.BOOK_OF_BINDING_AFRIT.toStack());
        //no text

        this.context().page("book_of_binding_marid_recipe");
        var bookOfBindingMaridRecipe = BookCraftingRecipePageModel.create()
                .withRecipeId1(this.modLoc("crafting/book_of_binding_marid"));
        //no text

        this.context().page("book_of_binding_bound_marid_recipe");
        var bookOfBoundBindingMaridRecipe = BookBindingCraftingRecipePageModel.create()
                .withRecipeId1()
                .withUnboundBook(OccultismItems.BOOK_OF_BINDING_MARID.toStack());
        //no text

        this.context().page("book_of_binding_empty");
        var alternativeBooks = BookSpiritFireRecipePageModel.create()
                .withRecipeId1(this.modLoc("spirit_fire/book_of_binding_empty"))
                .withText(this.context().pageText());
        this.lang().add(this.context().pageText(),
                """
                         Alternatively, you can directly use the Binding Book: Empty instead of the previous three items. There are two ways to obtain this book. Place this book in the center of dyes to get specific book of binding.
                        """.formatted(COLOR_PURPLE));

        this.context().page("book_of_binding_empty_recipe");
        var bookOfBindingEmptyRecipe = BookCraftingRecipePageModel.create()
                .withRecipeId2(this.modLoc("crafting/book_of_binding_empty"));
        //no text

        return BookEntryModel.create(this.modLoc(this.context().categoryId() + "/" + this.context().entryId()), this.context().entryName())
                .withDescription(this.context().entryDescription())
                .withIcon(OccultismItems.BOOK_OF_BINDING_BOUND_FOLIOT.get())
                .withLocation(entryMap.get(icon))
                .withPages(
                        intro,
                        intro2,
                        purifiedInkRecipe,
                        awakenedFeatherRecipe,
                        tabooBookRecipe,
                        bookOfBindingFoliotRecipe,
                        bookOfBindingBoundFoliotRecipe,
                        bookOfBindingDjinniRecipe,
                        bookOfBoundBindingDjinniRecipe,
                        bookOfBindingAfritRecipe,
                        bookOfBoundBindingAfritRecipe,
                        bookOfBindingMaridRecipe,
                        bookOfBoundBindingMaridRecipe,
                        alternativeBooks,
                        bookOfBindingEmptyRecipe
                );
    }

    private BookEntryModel makeBooksOfBindingAutomationEntry(CategoryEntryMap entryMap, char icon) {
        this.context().entry("books_of_binding_automation");
        this.lang().add(this.context().entryName(), "Books of Binding in Automation");
        this.lang().add(this.context().entryDescription(), "Tips for using books of binding in Crafting Automation such as AE2 or RS");

        this.context().page("intro");
        var intro = BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText());
        this.lang().add(this.context().pageTitle(), "The Problem");
        this.lang().add(this.context().pageText(),
                """
                        Bound Books of Binding are generated with a random spirit name. This tricks many automated crafting processes into no longer recognizing the item as the requested crafting result, because it does not expect NBT/Data Components on the item.
                        \\
                        \\
                        This leads to stuck crafting processes.
                        """
        );

        this.context().page("solution");
        var solution = BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText());
        this.lang().add(this.context().pageTitle(), "The Solution");
        this.lang().add(this.context().pageText(),
                """
                        1. Put a dictionary of spirits into an anvil and give it a name. This will be the name of all spirits summoned in the future.
                        2. Use this dictionary to configure crafting patterns (if your automation mod requires it).
                        3. Use this dictionary to craft the Bound Books of Binding in the automation system. As usual, the dictionary will not be used up.
                        4. All crafted books will now have the same name and will be recognized by your automation system.
                        """
        );

        return BookEntryModel.create(this.modLoc(this.context().categoryId() + "/" + this.context().entryId()), this.context().entryName())
                .withDescription(this.context().entryDescription())
                .withIcon(Items.CRAFTER)
                .withLocation(entryMap.get(icon))
                .withPages(
                        intro,
                        solution
                );
    }

    private BookEntryModel makeBooksOfCallingEntry(CategoryEntryMap entryMap, char icon) {
        this.context().entry("books_of_calling");
        this.lang().add(this.context().entryName(), "Books of Calling");
        this.lang().add(this.context().entryDescription(), "Telling your spirits what to do");

        this.context().page("intro");
        var intro = BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText());
        this.lang().add(this.context().pageTitle(), "Books of Calling");
        this.lang().add(this.context().pageText(),
                """
                        Books of Calling allow to control a summoned spirit, and to store it to prevent essence decay or move it more easily. 
                        \\
                        \\
                        Only spirits that require precise instructions - such as a work area or drop-off storage - come with a book of calling.
                        """);

        this.context().page("usage");
        var usage = BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText());
        this.lang().add(this.context().pageTitle(), "Usage");
        this.lang().add(this.context().pageText(),
                """
                        - [#](%1$s)Right-click[#]() air to open the configuration screen
                        - [#](%1$s)Shift-right-click[#]() a block to apply the action selected in the configuration screen
                        - [#](%1$s)Shift-right-click[#]() a spirit to capture it (must be of the same type)
                        - [#](%1$s)Right-click[#]() with a book with a captured spirit to release it
                        """.formatted(COLOR_PURPLE));

        this.context().page("obtaining");
        var obtaining = BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText());
        this.lang().add(this.context().pageTitle(), "How to obtain Books of Calling");
        this.lang().add(this.context().pageText(),
                """
                        If a summoned spirit supports the use of a Book of Calling, the summoning ritual will automatically spawn a book in the world alongside the spirit.
                        \\
                        \\
                        If you **lose the book**, there are also crafting recipes that just provide the book (without summoning a spirit).
                        """.formatted(COLOR_PURPLE));

        this.context().page("obtaining2");
        var obtaining2 = BookTextPageModel.create()
                .withText(this.context().pageText());
        this.lang().add(this.context().pageText(),
                """
                        The recipes can be found in this book or via JEI.
                        \\
                        \\
                        [#](%1$s)Shift-right-click[#]() the spirit with the crafted book to assign it.
                        """.formatted(COLOR_PURPLE));

        this.context().page("storage");
        var storage = BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText());
        this.lang().add(this.context().pageTitle(), "Storing Spirits");
        this.lang().add(this.context().pageText(),
                """
                        To store spirits that do not have a fitting book of calling, you can use a [Soul Gem](entry://crafting_rituals/craft_soul_gem).
                        Soul gems are much more versatile and allow to store almost all types of entities even animals and monsters, but not players or bosses.
                        """);

        return BookEntryModel.create(this.modLoc(this.context().categoryId() + "/" + this.context().entryId()), this.context().entryName())
                .withDescription(this.context().entryDescription())
                .withIcon(OccultismItems.BOOK_OF_CALLING_DJINNI_MANAGE_MACHINE.get())
                .withLocation(entryMap.get(icon))
                .withPages(
                        intro,
                        usage,
                        obtaining,
                        obtaining2,
                        storage
                );
    }

    private BookEntryModel makeRitualEntry(CategoryEntryMap entryMap, char icon) {
        this.context().entry("first_ritual");
        this.lang().add(this.context().entryName(), "First Ritual");
        this.lang().add(this.context().entryDescription(), "We're actually getting started now!");

        this.context().page("intro");
        var intro = BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText());
        this.lang().add(this.context().pageTitle(), "The Ritual (tm)");
        this.lang().add(this.context().pageText(),
                """
                        These pages will walk the gentle reader through the process of the [first ritual](entry://summoning_rituals/summon_crusher_t1) step by step.
                        \\
                        We **start** by placing the [](item://occultism:golden_sacrificial_bowl) and drawing the appropriate pentacle, [Aviar's Circle](entry://pentacles/summon_foliot) as seen on the left around it.
                          """.formatted(COLOR_PURPLE));

        this.context().page("multiblock");
        var multiblock = BookMultiblockPageModel.create()
                .withMultiblockId(this.modLoc("summon_foliot"))
                .withText(this.context().pageText());
        this.lang().add(this.context().pageText(),
                """
                        Only the color and location of the chalk marks is relevant, not the glyph/sign.
                          """.formatted(COLOR_PURPLE));

        this.context().page("bowl_text");
        var bowlText = BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText());
        this.lang().add(this.context().pageTitle(), "Sacrificial Bowls");
        this.lang().add(this.context().pageText(),
                """
                        Next, place *at least* 4 [Sacrificial Bowls](item://occultism:sacrificial_bowl) close to the pentacle.
                        \\
                        \\
                        They must be placed **anywhere** within 8 blocks of the central [](item://occultism:golden_sacrificial_bowl). **The exact location does not matter.**
                          """.formatted(COLOR_PURPLE));

        this.context().page("bowl_placement");
        var bowlPlacementImage = BookImagePageModel.create()
                .withImages(this.modLoc("textures/gui/book/bowl_placement.png"))
                .withBorder(true)
                .withText(this.context().pageText());
        this.lang().add(this.context().pageText(),
                """
                        Possible locations for the sacrificial bowls.
                          """.formatted(COLOR_PURPLE));

        this.context().page("ritual_text");
        var ritualText = BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText());
        this.lang().add(this.context().pageTitle(), "Placing Ingredients");
        this.lang().add(this.context().pageText(),
                """
                        Now it is time to place the ingredients you see on the next page in the (regular, not golden) sacrificial bowls. The ingredients will be consumed from the bowls as the ritual progresses.
                          """.formatted(COLOR_PURPLE));

        this.context().page("ritual_recipe");
        var ritualRecipe = BookRitualRecipePageModel.create()
                .withRecipeId1(this.modLoc("ritual/summon_foliot_crusher"));
        //no text

        this.context().page("pentacle_link_hint");
        var pentacleLinkHint = BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText());
        this.lang().add(this.context().pageTitle(), "A Note about Ritual Recipes");
        this.lang().add(this.context().pageText(),
                """
                        Ritual recipe pages, such as the previous pageshow not only the ingredients, but also the pentacle that you need to draw with chalk in order to use the ritual.
                        \\
                        \\
                        **To show the pentacle, click the blue link** at the center top of the ritual page. You can then even preview it in-world.
                          """.formatted(COLOR_PURPLE));

        this.context().page("start_ritual");
        var startRitualText = BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText());
        this.lang().add(this.context().pageTitle(), "Let there be ... spirits!");
        this.lang().add(this.context().pageText(),
                """
                        Finally, [#](%1$s)right-click[#]() the [](item://occultism:golden_sacrificial_bowl) with the **bound** book of binding you created before and wait until the crusher spawns.
                        \\
                        \\
                        Now all that remains is to drop appropriate ores near the crusher and wait for it to turn it into dust.
                          """.formatted(COLOR_PURPLE));

        this.context().page("automation");
        var automationText = BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText());
        this.lang().add(this.context().pageTitle(), "Automatic Rituals");
        this.lang().add(this.context().pageText(),
                """
                        Instead of right-clicking the golden sacrificial bowl with the final ingredient, you can also use a Hopper or any type of pipe to insert the item into the bowl. The ritual will start automatically.\\
                        Note that any rituals that summon tamed animals or familiars will summon them untamed instead.
                          """.formatted(COLOR_PURPLE));

        this.context().page("redstone");
        var redstoneText = BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText());
        this.lang().add(this.context().pageTitle(), "Redstone");
        this.lang().add(this.context().pageText(),
                """
                        Depending on the ritual state the golden bowl will emit a different redstone level:
                        - **0** if no ritual is active
                        - **1** if the ritual is active, but waiting for a sacrifice
                        - **2** if the ritual is active, but waiting for an item to be used
                        - **8** if the ritual is active and running
                        """.formatted(COLOR_PURPLE));

        this.context().page("clone_redstone");
        var cloneRedstoneText = BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText());
        this.lang().add(this.context().pageTitle(), "All sides blocked?");
        this.lang().add(this.context().pageText(),
                """
                        You can place another [](item://occultism:golden_sacrificial_bowl) in the third block below the
                         original [](item://occultism:golden_sacrificial_bowl). Every time this new bowl receives an
                         block update, it clones the actual signal strength of the original bowl.
                        """.formatted(COLOR_PURPLE));

        this.context().page("clone_placement");
        var clonePlacementImage = BookImagePageModel.create()
                .withImages(this.modLoc("textures/gui/book/redstone_clone.png"))
                .withBorder(true)
                .withText(this.context().pageText());
        this.lang().add(this.context().pageText(),
                """
                        One suggestion is to use any block that interacts with redstone and an observer.
                          """.formatted(COLOR_PURPLE));


        return BookEntryModel.create(this.modLoc(this.context().categoryId() + "/" + this.context().entryId()), this.context().entryName())
                .withDescription(this.context().entryDescription())
                .withIcon(OccultismItems.PENTACLE_SUMMON.get())
                .withLocation(entryMap.get(icon))
                .withPages(
                        intro,
                        multiblock,
                        bowlText,
                        bowlPlacementImage,
                        ritualText,
                        ritualRecipe,
                        pentacleLinkHint,
                        startRitualText,
                        automationText,
                        redstoneText,
                        cloneRedstoneText,
                        clonePlacementImage
                );
    }

    private BookEntryModel makeBrushEntry(CategoryEntryMap entryMap, char icon) {
        this.context().entry("brush");
        this.lang().add(this.context().entryName(), "Brush");
        this.lang().add(this.context().entryDescription(), "Cleaning up!");

        this.context().page("intro");
        var intro = BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText());
        this.lang().add(this.context().pageTitle(), "Next Steps");
        this.lang().add(this.context().pageText(),
                """
                        Chalk is a pain to clean up, by [#](%1$s)right-clicking[#]() with a brush you can remove it from the world much more easily.
                          """.formatted(COLOR_PURPLE));

        this.context().page("brushRecipe");
        var brushRecipe = BookCraftingRecipePageModel.create()
                .withRecipeId1(this.modLoc("crafting/brush"));
        //no text

        return BookEntryModel.create(this.modLoc(this.context().categoryId() + "/" + this.context().entryId()), this.context().entryName())
                .withDescription(this.context().entryDescription())
                .withIcon(OccultismItems.BRUSH.get())
                .withLocation(entryMap.get(icon))
                .withPages(
                        intro,
                        brushRecipe
                );
    }

    private BookEntryModel makeMoreRitualsEntry(CategoryEntryMap entryMap, char icon) {
        this.context().entry("more_rituals");
        this.lang().add(this.context().entryName(), "More Rituals");
        this.lang().add(this.context().entryDescription(), "Ready for new challenges?");

        return BookEntryModel.create(this.modLoc(this.context().categoryId() + "/" + this.context().entryId()), this.context().entryName())
                .withDescription(this.context().entryDescription())
                .withIcon(this.modLoc("textures/gui/book/robe.png"))
                .withLocation(entryMap.get(icon))
                .withEntryBackground(1, 1) //silver background and wavey entry shape
                .withCategoryToOpen(this.modLoc("rituals"));
    }

    private BookEntryModel makeGreyParticlesEntry(CategoryEntryMap entryMap, char icon) {
        this.context().entry("grey_particles");
        this.lang().add(this.context().entryName(), "Grey particles?");
        this.lang().add(this.context().entryDescription(), "What to do when a ritual seems stuck!");

        this.context().page("text");
        var text = BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText());
        this.lang().add(this.context().pageTitle(), "Ritual stuck?");
        this.lang().add(this.context().pageText(),
                """
                        If a ritual appears stuck - no items being consumed - you should see grey particles around the [](item://occultism:golden_sacrificial_bowl). If this is the case the ritual requires you to either [use a specific item](entry://rituals/item_use) or [sacrifice a specific mob](entry://rituals/sacrifice).
                        \\
                        \\
                        Find the ritual in the [Rituals](category://rituals) category and check for instructions.
                          """);

        return BookEntryModel.create(this.modLoc(this.context().categoryId() + "/" + this.context().entryId()), this.context().entryName())
                .withDescription(this.context().entryDescription())
                .withIcon(Items.GRAY_DYE)
                .withLocation(entryMap.get(icon))
                .withPages(text);
    }

    private BookEntryModel makeSpiritsSubcategoryEntry(CategoryEntryMap entryMap, char icon) {
        this.context().entry("spirits");
        this.lang().add(this.context().entryName(), "About Spirits");
        this.lang().add(this.context().entryDescription(), "Learn more about Spirits.");

        return BookEntryModel.create(this.modLoc(this.context().categoryId() + "/" + this.context().entryId()), this.context().entryName())
                .withIcon(this.modLoc("textures/gui/book/spirits.png"))
                .withCategoryToOpen(this.modLoc("spirits"))
                .withEntryBackground(1, 1) //silver background and wavey entry shape
                .withLocation(entryMap.get(icon));
    }

    private BookEntryModel makeChalksEntry(CategoryEntryMap entryMap, char icon) {
        this.context().entry("chalks");
        this.lang().add(this.context().entryName(), "More Chalks");
        this.lang().add(this.context().entryDescription(), "Better chalks for better rituals!");

        this.context().page("intro");
        var intro = BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText());
        this.lang().add(this.context().pageTitle(), "More Chalks");
        this.lang().add(this.context().pageText(),
                """
                        For more advanced rituals the basic [White Chalk](entry://occultism:dictionary_of_spirits/getting_started/ritual_prep_chalk) is not sufficient. Instead chalks made from more arcane materials are required.
                        """);

        this.context().page("more");
        var more = BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText());
        this.lang().add(this.context().pageTitle(), "Pentacle Category");
        this.lang().add(this.context().pageText(),
                """
                       Follow the progression in [Pentacle page](category://pentacles) to get the 16 chalks and do all pentacles,
                       """);

        //no text
        return BookEntryModel.create(this.modLoc(this.context().categoryId() + "/" + this.context().entryId()), this.context().entryName())
                .withDescription(this.context().entryDescription())
                .withIcon(OccultismItems.CHALK_YELLOW.get())
                .withLocation(entryMap.get(icon))
                .withPages(
                        intro,
                        more
                );
    }

    private BookEntryModel makeOtherworldGogglesEntry(CategoryEntryMap entryMap, char icon) {
        this.context().entry("otherworld_goggles");
        this.lang().add(this.context().entryName(), "Otherworld Goggles");
        this.lang().add(this.context().entryDescription(), "Say no to drugs!");

        this.context().page("spotlight");
        var spotlight = BookSpotlightPageModel.create()
                .withItem(Ingredient.of(OccultismItems.OTHERWORLD_GOGGLES.get()))
                .withText(this.context().pageText());
        this.lang().add(this.context().pageText(),
                """
                        The [](item://occultism:otherworld_goggles) are what advanced summoners use to see the [#](%1$s)Otherworld[#](), to avoid the negative side effects of [](entry://occultism:dictionary_of_spirits/getting_started/demons_dream).
                        \\
                        \\
                        Making your first pair of these is seen by many as a rite of passage.
                        """.formatted(COLOR_PURPLE));

        this.context().page("crafting");
        var crafting = BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText());
        this.lang().add(this.context().pageTitle(), "Crafting Goggles");
        this.lang().add(this.context().pageText(),
                """
                        Crafting these goggles is a multi-step process described in detail in the Entry about [Crafting Otherworld Goggles](entry://crafting_rituals/craft_otherworld_goggles).
                        """.formatted(COLOR_PURPLE));

        return BookEntryModel.create(this.modLoc(this.context().categoryId() + "/" + this.context().entryId()), this.context().entryName())
                .withDescription(this.context().entryDescription())
                .withIcon(OccultismItems.OTHERWORLD_GOGGLES.get())
                .withLocation(entryMap.get(icon))
                .withPages(
                        spotlight,
                        crafting
                );
    }

    private BookEntryModel makeInfusedPickaxeEntry(CategoryEntryMap entryMap, char icon) {
        this.context().entry("infused_pickaxe");
        this.lang().add(this.context().entryName(), "Infused Pickaxe");
        this.lang().add(this.context().entryDescription(), "Tackling Otherworld Ores");

        this.context().page("spotlight");
        var spotlight = BookSpotlightPageModel.create()
                .withItem(Ingredient.of(OccultismItems.INFUSED_PICKAXE.get()))
                .withText(this.context().pageText());
        this.lang().add(this.context().pageText(),
                """
                        Beyond [](item://occultism:otherworld_log) and [](item://occultism:otherstone) there are also otherworld materials that require special tools to harvest. 
                        \\
                        \\
                        This pickaxe is rather brittle, but it will do the job.
                        """);

        this.context().page("gem_recipe");
        var gemRecipe = BookSpiritFireRecipePageModel.create()
                .withRecipeId1(this.modLoc("spirit_fire/spirit_attuned_gem"))
                .withText(this.context().pageText());
        this.lang().add(this.context().pageText(),
                """
                        These gems, when infused with a spirit, can be used to interact with Otherword materials and are the key to crafting the pickaxe.
                        """);

        this.context().page("head_recipe");
        var headRecipe = BookCraftingRecipePageModel.create()
                .withRecipeId1(this.modLoc("crafting/spirit_attuned_pickaxe_head"));
        //no text

        this.context().page("crafting");
        var crafting = BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText());
        this.lang().add(this.context().pageTitle(), "Crafting");
        this.lang().add(this.context().pageText(),
                """
                        After preparing the raw materials, the pickaxe needs to be infused with a spirit.
                        \\
                        \\
                        Follow the instructions at [Craft Infuse Pickaxe](entry://crafting_rituals/craft_infused_pickaxe)
                        """.formatted(COLOR_PURPLE));

        return BookEntryModel.create(this.modLoc(this.context().categoryId() + "/" + this.context().entryId()), this.context().entryName())
                .withDescription(this.context().entryDescription())
                .withIcon(OccultismItems.INFUSED_PICKAXE.get())
                .withLocation(entryMap.get(icon))
                .withPages(
                        spotlight,
                        gemRecipe,
                        headRecipe,
                        crafting
                );
    }

    private BookEntryModel makeIesniumEntry(CategoryEntryMap entryMap, char icon) {
        this.context().entry("iesnium");
        this.lang().add(this.context().entryName(), "Iesnium Ore");
        this.lang().add(this.context().entryDescription(), "Myterious metals ...");

        this.context().page("spotlight");
        var spotlight = BookSpotlightPageModel.create()
                .withItem(Ingredient.of(OccultismBlocks.IESNIUM_ORE.get()))
                .withText(this.context().pageText());
        this.lang().add(this.context().pageText(),
                """
                        This is a rare metal that, to the naked eye, looks like [](item://minecraft:netherrack) and cannot be mined with a regular pickaxe.
                        \\
                        \\
                        When mined with the correct tools, it can be used to craft powerful items (you will learn more about that later).
                             """.formatted(COLOR_PURPLE));

        this.context().page("where");
        var where = BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText());
        this.lang().add(this.context().pageTitle(), "Where to find it");
        this.lang().add(this.context().pageText(),
                """
                        Like Netherrack, Iesnium can be found in the Nether. In order to **see** it, you need to wear [Otherworld Goggles](entry://getting_started/otherworld_goggles).
                        \\
                        \\
                        To make searching for it simpler, attune a [Divination Rod](entry://getting_started/divination_rod) to it and righ-click and hold in the nether until it highlights a nearby block, which will hold the ore.
                             """.formatted(COLOR_PURPLE));

        this.context().page("how");
        var how = BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText());
        this.lang().add(this.context().pageTitle(), "How to mine it");
        this.lang().add(this.context().pageText(),
                """
                        Iesnium can only be mined with the [Infused Pickaxe](entry://getting_started/infused_pickaxe) or an [](item://occultism:iesnium_pickaxe) (about which you will learn later).
                        \\
                        \\
                        After identifying a block that holds Iesnium, you can mine it with the pickaxe you created in the previous step.
                             """.formatted(COLOR_PURPLE));

        this.context().page("processing");
        var processing = BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText());
        this.lang().add(this.context().pageTitle(), "Processing");
        this.lang().add(this.context().pageText(),
                """
                        Iesnium Ore, after mining, can be smelted directly into ingots, or placed down. When placed, it will not turn back into it's netherrack form. Consequently it can also be mined with any pickaxe then. This visible form of the Ore, when mined, will drop [](item://occultism:raw_iesnium).
                             """.formatted(COLOR_PURPLE));

        this.context().page("uses");
        var uses = BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText());
        this.lang().add(this.context().pageTitle(), "Uses");
        this.lang().add(this.context().pageText(),
                """
                        Iesnium can be used to craft an improved pickaxe, spirit lamps, and other powerful items. Follow the progress in this book to learn more about it.
                             """.formatted(COLOR_PURPLE));

        this.context().page("otherglass");
        var otherglass = BookCraftingRecipePageModel.create()
                .withRecipeId1(this.modLoc("crafting/otherglass"))
                .withText(this.context().pageText());
        this.lang().add(this.context().pageText(),
                """
                One of the uses of iesnium is the creation of Otherglass, this block hides from common eyes and is revealed only to those who see the other world. To collect this you need an infused or iesnium pickaxe.
                 """.formatted(COLOR_PURPLE));


        return BookEntryModel.create(this.modLoc(this.context().categoryId() + "/" + this.context().entryId()), this.context().entryName())
                .withDescription(this.context().entryDescription())
                .withIcon(OccultismBlocks.IESNIUM_ORE.get())
                .withLocation(entryMap.get(icon))
                .withPages(
                        spotlight,
                        where,
                        how,
                        processing,
                        uses,
                        otherglass
                );
    }

    private BookEntryModel makeIesniumPickaxeEntry(CategoryEntryMap entryMap, char icon) {
        this.context().entry("iesnium_pickaxe");
        this.lang().add(this.context().entryName(), "Iesnium Pickaxe");
        this.lang().add(this.context().entryDescription(), "A more durable otherworld-appropriate pickaxe");

        this.context().page("spotlight");
        var spotlight = BookSpotlightPageModel.create()
                .withItem(Ingredient.of(OccultismItems.IESNIUM_PICKAXE.get()))
                .withText(this.context().pageText());
        this.lang().add(this.context().pageText(),
                """
                        Like the [Infused Pickaxe](entry://getting_started/infused_pickaxe), this pickaxe can be used to mine Tier 2 Otherworld Materials such as [](item://occultism:iesnium_ore). As it is made from metal, instead of brittle [](item://occultism:spirit_attuned_gem), it is very durable and can be used for a long time.
                             """.formatted(COLOR_PURPLE));

        this.context().page("crafting");
        var crafting = BookCraftingRecipePageModel.create()
                .withRecipeId1(this.modLoc("crafting/iesnium_pickaxe"));
        //no text

        return BookEntryModel.create(this.modLoc(this.context().categoryId() + "/" + this.context().entryId()), this.context().entryName())
                .withDescription(this.context().entryDescription())
                .withIcon(OccultismItems.IESNIUM_PICKAXE.get())
                .withLocation(entryMap.get(icon))
                .withPages(
                        spotlight,
                        crafting
                );
    }

    private BookEntryModel makeMagicLampsEntry(CategoryEntryMap entryMap, char icon) {
        this.context().entry("magic_lamps");
        this.lang().add(this.context().entryName(), "Magic Lamps");
        this.lang().add(this.context().entryDescription(), "Three wishes? Close, but not quite..");

        this.context().page("spotlight");
        var spotlight = BookSpotlightPageModel.create()
                .withItem(Ingredient.of(OccultismItems.MAGIC_LAMP_EMPTY.get()))
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText());
        this.lang().add(this.context().pageTitle(), "Magic Lamps");
        this.lang().add(this.context().pageText(),
                """
                        Magic Lamps can be used to keep spirits safe from [#](%1$s)Essence Decay[#](), while still having access to some of their powers. Most commonly they are used to access a [#](%1$s)Mining Dimension[#]() and act as (*lag free*) [#](%1$s)Void Miners[#]().
                             """.formatted(COLOR_PURPLE));

        this.context().page("crafting");
        var crafting = BookCraftingRecipePageModel.create()
                .withRecipeId1(this.modLoc("crafting/magic_lamp_empty"));
        //no text

        return BookEntryModel.create(this.modLoc(this.context().categoryId() + "/" + this.context().entryId()), this.context().entryName())
                .withDescription(this.context().entryDescription())
                .withIcon(OccultismItems.MAGIC_LAMP_EMPTY.get())
                .withLocation(entryMap.get(icon))
                .withPages(
                        spotlight,
                        crafting
                );
    }

    private BookEntryModel makeSpiritMinersEntry(CategoryEntryMap entryMap, char icon) {
        this.context().entry("spirit_miners");
        this.lang().add(this.context().entryName(), "Spirit Miners");
        this.lang().add(this.context().entryDescription(), "It's Free Real Estate (-> Resources)");

        this.context().page("spotlight");
        var spotlight = BookSpotlightPageModel.create()
                .withItem(Ingredient.of(OccultismItems.MINER_FOLIOT_UNSPECIALIZED.get()))
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText());
        this.lang().add(this.context().pageTitle(), "Spirit Miners");
        this.lang().add(this.context().pageText(),
                """
                        By summoning a spirit into a Magic Lamp and placing it in a [Dimensional Mineshaft (see next step)](entry://getting_started/mineshaft) it can be made to mine for you in a [#](%1$s)Mining Dimension[#](). This is a great way to get resources without having to go mining in the overworld (or other dimesions) yourself.
                             """.formatted(COLOR_PURPLE));

        this.context().page("crafting");
        var crafting = BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText());
        this.lang().add(this.context().pageTitle(), "Crafting");
        this.lang().add(this.context().pageText(),
                """
                        See [Foliot Miner](entry://crafting_rituals/craft_foliot_miner) and the subsequent entries for information on how to craft spirit miners.
                             """.formatted(COLOR_PURPLE));

        return BookEntryModel.create(this.modLoc(this.context().categoryId() + "/" + this.context().entryId()), this.context().entryName())
                .withDescription(this.context().entryDescription())
                .withIcon(OccultismItems.MINER_FOLIOT_UNSPECIALIZED.get())
                .withLocation(entryMap.get(icon))
                .withPages(
                        spotlight,
                        crafting
                );
    }

    private BookEntryModel makeMineshaftEntry(CategoryEntryMap entryMap, char icon) {
        this.context().entry("mineshaft");
        this.lang().add(this.context().entryName(), "Dimensional Mineshaft");
        this.lang().add(this.context().entryDescription(), "Ethically questionable, but very profitable");

        this.context().page("spotlight");
        var spotlight = BookSpotlightPageModel.create()
                .withItem(Ingredient.of(OccultismBlocks.DIMENSIONAL_MINESHAFT.get()))
                .withText(this.context().pageText());
        this.lang().add(this.context().pageText(),
                """
                        This block acts as a portal, for spirits only, to the [#](%1$s)Mining Dimension[#](). Place a Magic Lamp with a Miner Spirit in it, to make it mine for you.
                             """.formatted(COLOR_PURPLE));

        this.context().page("crafting");
        var crafting = BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText());
        this.lang().add(this.context().pageTitle(), "Crafting");
        this.lang().add(this.context().pageText(),
                """
                        See [Dimensional Mineshaft](entry://crafting_rituals/craft_dimensional_mineshaft) in the [Binding Rituals](category://crafting_rituals) Category.
                             """.formatted(COLOR_PURPLE));

        return BookEntryModel.create(this.modLoc(this.context().categoryId() + "/" + this.context().entryId()), this.context().entryName())
                .withDescription(this.context().entryDescription())
                .withIcon(OccultismBlocks.DIMENSIONAL_MINESHAFT.get())
                .withLocation(entryMap.get(icon))
                .withPages(
                        spotlight,
                        crafting
                );
    }

    private BookEntryModel makeStorageEntry(CategoryEntryMap entryMap, char icon) {
        this.context().entry("storage");
        this.lang().add(this.context().entryName(), "Magic Storage");
        this.lang().add(this.context().entryDescription(), "Looking for much much much more storage? Look no further!");

        return BookEntryModel.create(this.modLoc(this.context().categoryId() + "/" + this.context().entryId()), this.context().entryName())
                .withDescription(this.context().entryDescription())
                .withIcon(OccultismBlocks.STORAGE_CONTROLLER.get())
                .withLocation(entryMap.get(icon))
                .withEntryBackground(1, 1) //silver background and wavey entry shape
                .withCategoryToOpen(this.modLoc("storage"));
    }

    private BookEntryModel makePossessionRitualsEntry(CategoryEntryMap entryMap, char icon) {
        this.context().entry("possession_rituals");
        this.lang().add(this.context().entryName(), "Possession Rituals");
        this.lang().add(this.context().entryDescription(), "A different way to get rare drops ...");

        this.context().page("intro");
        var intro = BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText());
        this.lang().add(this.context().pageTitle(), "Possession Rituals");
        this.lang().add(this.context().pageText(),
                """
                        Possessed mobs are controlled by spirits, allowing the summoner to determine some of their properties. They usually have **high drop rates** for rare drops, but are generally harder to kill.
                        \\
                        \\
                        You probably will want to start by summoning a [Possessed Endermite](entry://possession_rituals/possess_endermite) to get [](item://minecraft:end_stone) to craft [Advanced Chalks](entry://getting_started/chalks).
                             """.formatted(COLOR_PURPLE));

        this.context().page("more");
        var more = BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText());
        this.lang().add(this.context().pageTitle(), "More Information");
        this.lang().add(this.context().pageText(),
                """
                        To find out more about Possession Rituals, see the [Possession Rituals](category://possession_rituals) Category.
                             """.formatted(COLOR_PURPLE));

        return BookEntryModel.create(this.modLoc(this.context().categoryId() + "/" + this.context().entryId()), this.context().entryName())
                .withDescription(this.context().entryDescription())
                .withIcon(this.modLoc("textures/gui/book/possession.png"))
                .withLocation(entryMap.get(icon))
                .withEntryBackground(1, 1) //silver background and wavey entry shape
                .withPages(
                        intro,
                        more
                );
    }

    private BookEntryModel makeFamiliarRitualsEntry(CategoryEntryMap entryMap, char icon) {
        this.context().entry("familiar_rituals");
        this.lang().add(this.context().entryName(), "Familiar Rituals");
        this.lang().add(this.context().entryDescription(), "Personal helpers that provide buffs or fight for you");

        this.context().page("intro");
        var intro = BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText());
        this.lang().add(this.context().pageTitle(), "Familiar Rituals");
        this.lang().add(this.context().pageText(),
                """
                        Familiars provide a variety of bonus effects, such as feather falling, water breathing, jump boosts and more, and may also assist you in combat.
                        \\
                        \\
                        Store them in a [Familiar Ring](entry://crafting_rituals/craft_familiar_ring) to equip them as a curio.
                             """.formatted(COLOR_PURPLE));

        this.context().page("more");
        var more = BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText());
        this.lang().add(this.context().pageTitle(), "More Information");
        this.lang().add(this.context().pageText(),
                """
                        To find more about Familiars, see the [Familiar Rituals](category://familiar_rituals) Category.
                             """.formatted(COLOR_PURPLE));

        return BookEntryModel.create(this.modLoc(this.context().categoryId() + "/" + this.context().entryId()), this.context().entryName())
                .withDescription(this.context().entryDescription())
                .withIcon(this.modLoc("textures/gui/book/familiar.png"))
                .withLocation(entryMap.get(icon))
                .withEntryBackground(1, 1) //silver background and wavey entry shape
                .withPages(
                        intro,
                        more
                );
    }

    private BookEntryModel makeSummoningRitualsEntry(CategoryEntryMap entryMap, char icon) {
        this.context().entry("summoning_rituals");
        this.lang().add(this.context().entryName(), "Summoning Rituals");
        this.lang().add(this.context().entryDescription(), "Spirit helpers for your daily work life");

        this.context().page("intro");
        var intro = BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText());
        this.lang().add(this.context().pageTitle(), "Summoning Rituals");
        this.lang().add(this.context().pageText(),
                """
                        Summoning Rituals allow you to summon spirits to work for you. Unlike familiars, they are not personally bound to you, meaning they will not follow you around, but they will perform various work tasks for you. In fact the first ritual you performed, the [Foliot Crusher](entry://getting_started/first_ritual), was a summoning ritual.
                             """.formatted(COLOR_PURPLE));

        this.context().page("more");
        var more = BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText());
        this.lang().add(this.context().pageTitle(), "More Information");
        this.lang().add(this.context().pageText(),
                """
                        To find more about Summoning Rituals, see the [Summoning Rituals](category://summoning_rituals) Category.
                             """.formatted(COLOR_PURPLE));

        return BookEntryModel.create(this.modLoc(this.context().categoryId() + "/" + this.context().entryId()), this.context().entryName())
                .withDescription(this.context().entryDescription())
                .withIcon(this.modLoc("textures/gui/book/summoning.png"))
                .withLocation(entryMap.get(icon))
                .withEntryBackground(1, 1) //silver background and wavey entry shape
                .withPages(
                        intro,
                        more
                );
    }

    private BookEntryModel makeCraftingRitualsEntry(CategoryEntryMap entryMap, char icon) {
        this.context().entry("crafting_rituals");
        this.lang().add(this.context().entryName(), "Infusion Rituals");
        this.lang().add(this.context().entryDescription(), "Infuse spirits into items to create powerful tools");

        this.context().page("intro");
        var intro = BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText());
        this.lang().add(this.context().pageTitle(), "Infusion Rituals");
        this.lang().add(this.context().pageText(),
                """
                        Infusion rituals are all about crafting powerful items, by binding ("infusing") spirits into objects.The spirits will provide special functionality to the items.
                             """.formatted(COLOR_PURPLE));

        this.context().page("more");
        var more = BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText());
        this.lang().add(this.context().pageTitle(), "More Information");
        this.lang().add(this.context().pageText(),
                """
                        To find more about Infusing items, see the [Infusion Rituals](category://crafting_rituals) Category.
                             """.formatted(COLOR_PURPLE));

        return BookEntryModel.create(this.modLoc(this.context().categoryId() + "/" + this.context().entryId()), this.context().entryName())
                .withDescription(this.context().entryDescription())
                .withIcon(this.modLoc("textures/gui/book/infusion.png"))
                .withLocation(entryMap.get(icon))
                .withEntryBackground(1, 1) //silver background and wavey entry shape
                .withPages(
                        intro,
                        more
                );
    }
}
