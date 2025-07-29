package com.klikli_dev.occultism.datagen;

import com.klikli_dev.modonomicon.api.ModonomiconAPI;
import com.klikli_dev.modonomicon.api.datagen.BookProvider;
import com.klikli_dev.modonomicon.api.datagen.CategoryEntryMap;
import com.klikli_dev.modonomicon.api.datagen.ModonomiconLanguageProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookCategoryModel;
import com.klikli_dev.modonomicon.api.datagen.book.BookEntryModel;
import com.klikli_dev.modonomicon.api.datagen.book.BookEntryParentModel;
import com.klikli_dev.modonomicon.api.datagen.book.BookModel;
import com.klikli_dev.modonomicon.api.datagen.book.condition.BookAndConditionModel;
import com.klikli_dev.modonomicon.api.datagen.book.condition.BookEntryReadConditionModel;
import com.klikli_dev.modonomicon.api.datagen.book.condition.BookModLoadedConditionModel;
import com.klikli_dev.modonomicon.api.datagen.book.condition.BookTrueConditionModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.*;
import com.klikli_dev.occultism.datagen.book.FamiliarRitualsCategory;
import com.klikli_dev.occultism.integration.modonomicon.pages.*;
import com.klikli_dev.occultism.registry.OccultismBlocks;
import com.klikli_dev.occultism.registry.OccultismItems;
import com.klikli_dev.theurgy.registry.ItemRegistry;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Blocks;

public class OccultismBookProvider extends BookProvider {

    public static final String COLOR_PURPLE = "ad03fc";
    public static final String DEMONS_DREAM = "Demon's Dream";

    public OccultismBookProvider(PackOutput packOutput, String modid, ModonomiconLanguageProvider lang) {
        super("dictionary_of_spirits", packOutput, modid, lang);
    }

    @Override
    protected void registerDefaultMacros() {
        //currently none
    }

    @Override
    protected BookModel generateBook() {
        this.context().book("dictionary_of_spirits");
        this.lang().add(this.context().bookName(), "Dictionary of Spirits");
        this.lang().add(this.context().bookTooltip(), "An introduction to the spirit world.");

        int sortNum = 1;
        var gettingStartedCategory = this.makeGettingStartedCategory().withSortNumber(sortNum++);
        var spiritsCategory = this.makeSpiritsSubcategory().withSortNumber(sortNum++);

        var storageCategory = this.makeStorageCategory().withSortNumber(sortNum++);

        var ritualsCategory = this.makeRitualsCategory().withSortNumber(sortNum++);

        var summoningRitualsCategory = this.makeSummoningRitualsSubcategory().withSortNumber(sortNum++);
        var possessionRitualsCategory = this.makePossessionRitualsSubcategory().withSortNumber(sortNum++);
        var craftingRitualsCategory = this.makeCraftingRitualsSubcategory().withSortNumber(sortNum++);
        var familiarRitualsCategory = new FamiliarRitualsCategory(this).generate().withSortNumber(sortNum++);

        var pentaclesCategory = this.makePentaclesCategory().withSortNumber(sortNum++);

        var introReadCondition = BookEntryReadConditionModel.builder()
                .withEntry(this.modLoc("getting_started/intro")).build();

        storageCategory.withCondition(introReadCondition);
        ritualsCategory.withCondition(introReadCondition);
        pentaclesCategory.withCondition(introReadCondition);

        //https://tinyurl.com/occultism-graph

        var demoBook = BookModel.create(this.modLoc("dictionary_of_spirits"), this.context().bookName())
                .withModel(this.modLoc("dictionary_of_spirits_icon"))
                .withTooltip(this.context().bookTooltip())
                .withCategories(
                        gettingStartedCategory,
                        spiritsCategory,
                        pentaclesCategory,
                        ritualsCategory,
                        summoningRitualsCategory,
                        possessionRitualsCategory,
                        craftingRitualsCategory,
                        familiarRitualsCategory,
                        storageCategory
                )
                .withCraftingTexture(this.modLoc("textures/gui/book/crafting_textures.png"))
                .withGenerateBookItem(false)
                .withCustomBookItem(this.modLoc("dictionary_of_spirits"))
                .withAutoAddReadConditions(true)
                .withAllowOpenBooksWithInvalidLinks(true)
                ;
        return demoBook;
    }

    //region Getting Started
    private BookCategoryModel makeGettingStartedCategory() {
        this.context().category("getting_started");
        this.lang().add(this.context().categoryName(), "Getting Started");

        var entryMap = ModonomiconAPI.get().getEntryMap();
        entryMap.setMap(
                "______ŕ___t___B___________P___D___",
                "__________________________________",
                "______i___r___ç_b_____g_I_O_l_M___",
                "__________________________________",
                "______d___f_c_____R___a_s_________",
                "__________________________________",
                "______e_h_____ạ_______m___________",
                "__________________________________",
                "______________Á_C___p_S___w_x_y_z_"
        );
        //B=brush, N=Next Steps, P=iesnium pick
        //i=intro, r=divinationRod, ç = chalk, b=bowls, g=goggles,  I=infused pick O= tier 2 otherworld materials
        //  l=lamps, M=miner, D=Dim Mineshaft
        //d=demonsDream, h=healing, f=SpiritFire, c=candle, R=ritual, a=advancedChalks,
        //e=thirdEye, ạ=books of binding, m=more ritual, s=storage
        //C=book of calling, p=grey particles, S=spirits, w=possession, x=familiars, y=summoning, z=crafting

        var introEntry = this.makeIntroEntry(entryMap, 'i');

        var demonsDreamEntry = this.makeDemonsDreamEntry(entryMap, 'd');
        demonsDreamEntry.withParent(BookEntryParentModel.create(introEntry.getId()));

        var spiritFireEntry = this.makeSpiritFireEntry(entryMap, 'f');
        spiritFireEntry.withParent(BookEntryParentModel.create(demonsDreamEntry.getId()));

        var healingSpiritsEntry = this.makeHealingSpiritsEntry(entryMap, 'h');
        healingSpiritsEntry.withParent(BookEntryParentModel.create(demonsDreamEntry.getId()));

        var thirdEyeEntry = this.makeThirdEyeEntry(entryMap, 'e');
        thirdEyeEntry.withParent(BookEntryParentModel.create(demonsDreamEntry.getId()));

        var divinationRodEntry = this.makeDivinationRodEntry(entryMap, 'r');
        divinationRodEntry.withParent(BookEntryParentModel.create(spiritFireEntry.getId()));

        var theurgyDivinationRodEntry = this.makeTheurgyDivinationRodsEntry(entryMap, 't');
        theurgyDivinationRodEntry
                .withParent(BookEntryParentModel.create(divinationRodEntry.getId()))
                .withCondition(
                        BookAndConditionModel.builder().withChildren(
                                BookEntryReadConditionModel.builder()
                                        .withEntry(divinationRodEntry.getId()).build(),
                                BookModLoadedConditionModel.builder()
                                        .withModId("theurgy").build()
                        ).build()
                )
                .hideWhileLocked(true);

        var candleEntry = this.makeCandleEntry(entryMap, 'c');
        candleEntry.withParent(BookEntryParentModel.create(spiritFireEntry.getId()));

        var ritualPrepChalkEntry = this.makeRitualPrepChalkEntry(entryMap, 'ç');
        ritualPrepChalkEntry.withParent(BookEntryParentModel.create(candleEntry.getId()));

        var brushEntry = this.makeBrushEntry(entryMap, 'B');
        brushEntry.withParent(BookEntryParentModel.create(ritualPrepChalkEntry.getId()));

        var ritualPrepBowlEntry = this.makeRitualPrepBowlEntry(entryMap, 'b');
        ritualPrepBowlEntry.withParent(BookEntryParentModel.create(ritualPrepChalkEntry.getId()));

        var booksOfBinding = this.makeBooksOfBindingEntry(entryMap, 'ạ');
        booksOfBinding.withParent(BookEntryParentModel.create(candleEntry.getId()));

        var booksOfBindingAutomation = this.makeBooksOfBindingAutomationEntry(entryMap, 'Á');
        booksOfBindingAutomation.withParent(BookEntryParentModel.create(booksOfBinding.getId()));

        var booksOfCalling = this.makeBooksOfCallingEntry(entryMap, 'C');
        booksOfCalling.withParent(BookEntryParentModel.create(booksOfBinding.getId()));

        var ritualEntry = this.makeRitualEntry(entryMap, 'R');
        ritualEntry
                .withParent(BookEntryParentModel.create(ritualPrepBowlEntry.getId()))
                .withParent(BookEntryParentModel.create(booksOfBinding.getId()));

        var advancedChalksEntry = this.makeChalksEntry(entryMap, 'a');
        advancedChalksEntry.withParent(BookEntryParentModel.create(ritualEntry.getId()));

        var moreRitualsEntry = this.makeMoreRitualsEntry(entryMap, 'm');
        moreRitualsEntry.withParent(BookEntryParentModel.create(advancedChalksEntry.getId()));

        var greyParticlesEntry = this.makeGreyParticlesEntry(entryMap, 'p');
        greyParticlesEntry.withParent(BookEntryParentModel.create(ritualEntry.getId()));

        var spiritsSubcategory = this.makeSpiritsSubcategoryEntry(entryMap, 'S');
        spiritsSubcategory.withParent(BookEntryParentModel.create(greyParticlesEntry.getId()));

        var otherworldGoggles = this.makeOtherworldGogglesEntry(entryMap, 'g');
        otherworldGoggles.withParent(BookEntryParentModel.create(advancedChalksEntry.getId()));

        var infusedPickaxe = this.makeInfusedPickaxeEntry(entryMap, 'I');
        infusedPickaxe.withParent(BookEntryParentModel.create(otherworldGoggles.getId()));

        var iesnium = this.makeIesniumEntry(entryMap, 'O');
        iesnium.withParent(BookEntryParentModel.create(infusedPickaxe.getId()));

        var iesniumPickaxe = this.makeIesniumPickaxeEntry(entryMap, 'P');
        iesniumPickaxe.withParent(BookEntryParentModel.create(iesnium.getId()));

        var magicLampsEntry = this.makeMagicLampsEntry(entryMap, 'l');
        magicLampsEntry.withParent(BookEntryParentModel.create(iesnium.getId()));

        var spiritMinersEntry = this.makeSpiritMinersEntry(entryMap, 'M');
        spiritMinersEntry.withParent(BookEntryParentModel.create(magicLampsEntry.getId()));

        var mineshaftEntry = this.makeMineshaftEntry(entryMap, 'D');
        mineshaftEntry.withParent(BookEntryParentModel.create(spiritMinersEntry.getId()));

        var storageEntry = this.makeStorageEntry(entryMap, 's');
        storageEntry.withParent(BookEntryParentModel.create(advancedChalksEntry.getId()));

        var possessionRitualsEntry = this.makePossessionRitualsEntry(entryMap, 'w');
        possessionRitualsEntry.withParent(BookEntryParentModel.create(moreRitualsEntry.getId()));
        var familiarRitualsEntry = this.makeFamiliarRitualsEntry(entryMap, 'x');
        familiarRitualsEntry.withParent(BookEntryParentModel.create(moreRitualsEntry.getId()));
        var summoningRitualsEntry = this.makeSummoningRitualsEntry(entryMap, 'y');
        summoningRitualsEntry.withParent(BookEntryParentModel.create(moreRitualsEntry.getId()));
        var craftingRitualsEntry = this.makeCraftingRitualsEntry(entryMap, 'z');
        craftingRitualsEntry.withParent(BookEntryParentModel.create(moreRitualsEntry.getId()));

        return BookCategoryModel.create(this.modLoc(this.context().categoryId()), this.context().categoryName())
                .withIcon(OccultismItems.DICTIONARY_OF_SPIRITS_ICON.get())
                .withEntries(
                        introEntry,
                        demonsDreamEntry,
                        spiritFireEntry,
                        thirdEyeEntry,
                        healingSpiritsEntry,
                        divinationRodEntry,
                        theurgyDivinationRodEntry,
                        candleEntry,
                        ritualPrepChalkEntry,
                        ritualPrepBowlEntry,
                        booksOfBinding,
                        booksOfBindingAutomation,
                        ritualEntry,
                        brushEntry,
                        moreRitualsEntry,
                        greyParticlesEntry,
                        booksOfCalling,
                        spiritsSubcategory,
                        advancedChalksEntry,
                        otherworldGoggles,
                        infusedPickaxe,
                        iesnium,
                        iesniumPickaxe,
                        magicLampsEntry,
                        spiritMinersEntry,
                        mineshaftEntry,
                        storageEntry,
                        possessionRitualsEntry,
                        familiarRitualsEntry,
                        summoningRitualsEntry,
                        craftingRitualsEntry
                );
    }

    private BookEntryModel makeIntroEntry(CategoryEntryMap entryMap, char icon) {
        this.context().entry("intro");
        this.lang().add(this.context().entryName(), "About");
        this.lang().add(this.context().entryDescription(), "About using the Dictionary of Spirits");

        this.context().page("intro");
        var intro = BookTextPageModel.builder()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText())
                .build();
        this.lang().add(this.context().pageTitle(), "About");
        this.lang().add(this.context().pageText(),
                """
                        This book aims to introduce the novice reader to the most common summoning rituals and equip them with a list of spirit names to summon.
                        The authors advise caution in the summoning of the listed entities and does not take responsibility for any harm caused.
                        """);

        this.context().page("help");
        var help = BookTextPageModel.builder()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText())
                .build();
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
        var intro = BookTextPageModel.builder()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText())
                .build();
        this.lang().add(this.context().pageTitle(), "The Otherworld");
        this.lang().add(this.context().pageText(),
                """
                        Hidden from mere human eyes exists another plane of existence, another *dimension* if you will, the [#](%1$s)Otherworld[#]().
                        This world is populated with entities often referred to as [#](%1$s)Demons[#]().
                        """.formatted(COLOR_PURPLE));

        this.context().page("intro2");
        var intro2 = BookTextPageModel.builder()
                .withText(this.context().pageText())
                .build();
        this.lang().add(this.context().pageText(),
                """
                        These Demons possess a wide variety of powers and useful skills, and for centuries magicians have sought to summon them for their own gain.
                        The first step on the journey to successfully summoning such an Entity is to learn how to interact with the Otherworld.
                        """);

        this.context().page("spotlight");
        var spotlight = BookSpotlightPageModel.builder()
                .withItem(Ingredient.of(OccultismItems.DATURA.get()))
                .withText(this.context().pageText())
                .build();
        this.lang().add(this.context().pageText(),
                """
                        %1$s is a herb that gives humans the [#](%2$s)Third Eye[#](),
                        allowing them to see where the [#](%2$s)Otherworld[#]() intersects with our own.
                        Seeds can be found **by breaking grass**.
                        **Consuming** the grown fruit activates the ability *with a certain chance*.
                        """.formatted(DEMONS_DREAM, COLOR_PURPLE));

        this.context().page("harvest_effect");
        var harvestEffect = BookTextPageModel.builder()
                .withText(this.context().pageText())
                .build();
        this.lang().add(this.context().pageText(),
                """
                        An additional side effect of %1$s, is **the ability to interact with [#](%2$s)Otherworld[#]() materials**.
                        This is unique to %1$s, other ways to obtain [#](%2$s)Third Eye[#]() do not yield this ability.
                        While under the effect of %1$s you are able to **harvest** Otherstone as well as Otherworld trees.
                         """.formatted(DEMONS_DREAM, COLOR_PURPLE));

        this.context().page("datura_screenshot");
        var datureScreenshot = BookImagePageModel.builder()
                .withImages(this.modLoc("textures/gui/book/datura_effect.png"))
                .build();
        //no text

        this.context().page("note_on_spirit_fire");
        var spiritFire = BookTextPageModel.builder()
                .withText(this.context().pageText())
                .build();
        this.lang().add(this.context().pageText(),
                """
                        **Hint**: The otherworld materials you obtain by harvesting under the effects of[#](%2$s)Third Eye[#]() **can be obtained more easily using [](item://occultism:spirit_fire)**. Proceed with the next entry in this book to learn more about spirit fire.
                         """.formatted(DEMONS_DREAM, COLOR_PURPLE));

        this.context().page("spotlight2");
        var spotlight2 = BookSpotlightPageModel.builder()
                .withItem(Ingredient.of(OccultismItems.DEMONS_DREAM_ESSENCE.get()))
                .withText(this.context().pageText())
                .build();
        this.lang().add(this.context().pageText(),
                """
                    Multiple Demon's Dream fruits or seeds can be compressed into an essence that is much more potent. It *guarantees* the [#](%2$s)Third Eye[#]() and provides it for a longer amount of time, but comes with a lot of (positive and negative) side effects.
                        """.formatted(DEMONS_DREAM, COLOR_PURPLE)
        );

        this.context().page("recipe_essence");
        var recipeEssence = BookCraftingRecipePageModel.builder()
                .withRecipeId1(this.modLoc("crafting/demons_dream_essence_from_fruit"))
                .withRecipeId2(this.modLoc("crafting/demons_dream_essence_from_seeds"))
                .build();
        //no text

        this.context().page("spotlight3");
        var spotlight3 = BookSpotlightPageModel.builder()
                .withItem(Ingredient.of(OccultismItems.DEMONS_DREAM_ESSENCE.get()))
                .withText(this.context().pageText())
                .build();
        this.lang().add(this.context().pageText(),
                """
                    The essence can be purified in spirit fire (more on that later!) to obtain a version free from all negative side effects, while retaining the positive.
                        """.formatted(DEMONS_DREAM, COLOR_PURPLE)
        );

        this.context().page("recipe_essence_pure");
        var recipeEssencePure = BookSpiritFireRecipePageModel.builder()
                .withRecipeId1(this.modLoc("spirit_fire/otherworld_essence"))
                .build();
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
        var spotlight = BookSpotlightPageModel.builder()
                .withItem(Ingredient.of(OccultismItems.SPIRIT_FIRE.get()))
                .withText(this.context().pageText())
                .build();
        this.lang().add(this.context().pageText(),
                """
                        [#](%1$s)Spiritfire[#]() is a special type of fire that exists mostly in [#](%1$s)The Other Place[#]()
                        and does not harm living beings. Its special properties allow to use it to purify and convert
                        certain materials by burning them, without consuming them.
                        """.formatted(COLOR_PURPLE));

        this.context().page("spirit_fire_screenshot");
        var spiritFireScreenshot = BookImagePageModel.builder()
                .withImages(this.modLoc("textures/gui/book/spiritfire_instructions.png"))
                .withText(this.context().pageText())
                .build();
        this.lang().add(this.context().pageText(),
                """
                        Throw [](item://occultism:datura) to the ground and light it on fire with [](item://minecraft:flint_and_steel).
                         """);

        this.context().page("main_uses");
        var mainUses = BookTextPageModel.builder()
                .withText(this.context().pageText())
                .build();
        this.lang().add(this.context().pageText(),
                """
                        The main uses of [](item://occultism:spirit_fire) are to convert [](item://minecraft:diamond) into [](item://occultism:spirit_attuned_gem),
                        to get basic ingredients such as [](item://occultism:otherstone) and [Otherworld Saplings](item://occultism:otherworld_sapling_natural),
                        and to purify impure chalks.
                         """);

        this.context().page("otherstone_recipe");
        var otherstoneRecipe = BookSpiritFireRecipePageModel.builder()
                .withRecipeId1(this.modLoc("spirit_fire/otherstone"))
                .withText(this.context().pageText())
                .build();
        this.lang().add(this.context().pageText(),
                """
                        An easier way to obtain [](item://occultism:otherstone) than via divination.
                              """);

        this.context().page("otherworld_sapling_natural_recipe");
        var otherworldSaplingNaturalRecipe = BookSpiritFireRecipePageModel.builder()
                .withRecipeId1(this.modLoc("spirit_fire/otherworld_sapling_natural"))
                .withText(this.context().pageText())
                .build();
        this.lang().add(this.context().pageText(),
                """
                        An easier way to obtain [Otherworld Saplings](item://occultism:otherworld_sapling_natural) than via divination.
                              """);

        this.context().page("otherworld_ashes_recipe");
        var otherworldAshesRecipe = BookSpiritFireRecipePageModel.builder()
                .withRecipeId1(this.modLoc("spirit_fire/otherworld_ashes"))
                .build();
        //no text

        this.context().page("gem_recipe");
        var gemRecipe = BookSpiritFireRecipePageModel.builder()
                .withRecipeId1(this.modLoc("spirit_fire/spirit_attuned_gem"))
                .build();
        //no text


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
                        gemRecipe
                );
    }

    private BookEntryModel makeHealingSpiritsEntry(CategoryEntryMap entryMap, char icon) {
        this.context().entry("healing_spirits");
        this.lang().add(this.context().entryName(), "Healing Spirits");
        this.lang().add(this.context().entryDescription(), "Fix up your spirit!");

        this.context().page("spotlight");
        var spotlight = BookSpotlightPageModel.builder()
                .withItem(Ingredient.of(OccultismItems.DATURA.get()))
                .withText(this.context().pageText())
                .build();
        this.lang().add(this.context().pageText(),
                """
                        Right-click a spirit with [](item://occultism:datura) to heal it. 
                        \\
                        \\
                        This will work on **Familiars**, **Summoned Spirits** and also **Possessed Mobs**.
                        """);

        this.context().page("spotlight2");
        var spotlight2 = BookSpotlightPageModel.builder()
                .withItem(Ingredient.of(OccultismItems.DEMONS_DREAM_ESSENCE.get()))
                .withText(this.context().pageText())
                .build();
        this.lang().add(this.context().pageText(),
                """
                        When compressing Demon's Dream fruits or seeds into essence, a much stronger instant healing effect can be achieved. This comes at the cost of efficiency: Feeding 9 fruits to a spirit in succession will heal it more than feeding it 9 fruits worth of essence.
                        """);

        this.context().page("spotlight3");
        var spotlight3 = BookSpotlightPageModel.builder()
                .withItem(Ingredient.of(OccultismItems.OTHERWORLD_ESSENCE.get()))
                .withText(this.context().pageText())
                .build();
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
        var about = BookTextPageModel.builder()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText())
                .build();
        this.lang().add(this.context().pageTitle(), "Third Eye");
        this.lang().add(this.context().pageText(),
                """
                        The ability to see beyond the physical world is referred to as [#](%1$s)Third Eye[#]().
                        Humans do not possess such an ability to see [#](%1$s)beyond the veil[#](),
                        however with certain substances and contraptions the knowledgeable summoner can work around this limitation.
                         """.formatted(COLOR_PURPLE));

        this.context().page("how_to_obtain");
        var howToObtain = BookTextPageModel.builder()
                .withText(this.context().pageText())
                .build();
        this.lang().add(this.context().pageText(),
                """
                        The most comfortable, and most *expensive*, way to obtain this ability, is to wear spectacles
                        infused with spirits, that *lend* their sight to the wearer.
                        A slightly more nauseating, but **very affordable** alternative is the consumption of certain herbs,
                        [%1$s](entry://occultism:dictionary_of_spirits/getting_started/demons_dream) most prominent among them.
                         """.formatted(DEMONS_DREAM));

        this.context().page("otherworld_goggles");
        var otherworldGoggles = BookSpotlightPageModel.builder()
                .withItem(Ingredient.of(OccultismItems.OTHERWORLD_GOGGLES.get()))
                .withText(this.context().pageText())
                .build();
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
        var intro = BookTextPageModel.builder()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText())
                .build();
        this.lang().add(this.context().pageTitle(), "Divination");
        this.lang().add(this.context().pageText(),
                """
                        To make it easier to get started, the materials obtained by divination now also have crafting recipes.
                        **If you want the full experience, skip the following recipe page and move on to the
                        [divination instructions](entry://occultism:dictionary_of_spirits/getting_started/divination_rod@divination_instructions).**
                                """);

        this.context().page("otherstone_recipe");
        var otherstoneRecipe = BookSpiritFireRecipePageModel.builder()
                .withRecipeId1(this.modLoc("spirit_fire/otherstone"))
                .build();
        //no text

        this.context().page("otherworld_sapling_natural_recipe");
        var otherworldSaplingNaturalRecipe = BookSpiritFireRecipePageModel.builder()
                .withRecipeId1(this.modLoc("spirit_fire/otherworld_sapling_natural"))
                .withText(this.context().pageText())
                .build();
        this.lang().add(this.context().pageText(),
                """
                        **Beware**: the tree growing from the sapling will look like a normal oak tree.
                        You need to activate the [Third Eye](entry://occultism:dictionary_of_spirits/getting_started/demons_dream)
                        to harvest the Otherworld Logs and Leaves.
                                """);

        this.context().page("divination_rod");
        var divinationRod = BookSpotlightPageModel.builder()
                .withItem(Ingredient.of(OccultismItems.DIVINATION_ROD.get()))
                .withText(this.context().pageText())
                .withAnchor("divination_instructions")
                .build();
        this.lang().add(this.context().pageText(),
                """
                        Otherworld materials play an important role in interacting with spirits.
                        As they are rare and not visible to the naked eye, finding them requires special tools.
                        The divination rod allows to find Otherworld materials based on their similarities to materials common to our world.
                                 """);

        this.context().page("spirit_attuned_gem_recipe");
        var spiritAttunedGemRecipe = BookSpiritFireRecipePageModel.builder()
                .withRecipeId1(this.modLoc("spirit_fire/spirit_attuned_gem"))
                .build();

        this.context().page("divination_rod_recipe");
        var divinationRodRecipe = BookCraftingRecipePageModel.builder()
                .withRecipeId1(this.modLoc("crafting/divination_rod"))
                .build();

        this.context().page("about_divination_rod");
        var aboutDivinationRod = BookTextPageModel.builder()
                .withText(this.context().pageText())
                .build();
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
        var howToUse = BookTextPageModel.builder()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText())
                .build();
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
        var howToUse2 = BookTextPageModel.builder()
                .withText(this.context().pageText())
                .build();
        this.lang().add(this.context().pageText(),
                """
                        After the animation finishes, the closest **found block will be highlighted
                        with white lines and can be seen through other blocks**.
                        Additionally you can watch the crystals for hints: a white crystal indicates no target blocks found,
                        a fully purple block means the found block is nearby. Mixes between white and purple show
                        that the target is rather far away.""");

        this.context().page("how_to_use3");
        var howToUse3 = BookTextPageModel.builder()
                .withText(this.context().pageText())
                .build();
        this.lang().add(this.context().pageText(),
                """
                        [#](%1$s)Right-clicking[#]() without holding after a successful search will show the last found target block again.
                        \\
                        \\
                        If the mod *"Theurgy"* is installed the rod will not highlight the target block, but instead send a particle effect in the direction of the target block.
                        """.formatted(COLOR_PURPLE));

        this.context().page("divination_rod_screenshots");
        var divinationRodScreenshots = BookImagePageModel.builder()
                .withImages(
                        this.modLoc("textures/gui/book/rod_far.png"),
                        this.modLoc("textures/gui/book/rod_mid.png"),
                        this.modLoc("textures/gui/book/rod_near.png")
                )
                .withText(this.context().pageText())
                .build();
        this.lang().add(this.context().pageText(),
                """
                        White means nothing was found.
                        The more purple you see, the closer you are.
                        """);

        this.context().page("troubleshooting");
        var troubleshooting = BookTextPageModel.builder()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText())
                .build();
        this.lang().add(this.context().pageTitle(), "Troubleshooting");
        this.lang().add(this.context().pageText(),
                """
                        If the rod does not create highlighted blocks for you, you can try to:
                        - install theurgy, then a particle effect will be used instead
                        - Open occultism-client.toml in your instance's /config folder and set useAlternativeDivinationRodRenderer = true
                        """);


        this.context().page("otherworld_groves");
        var otherworldGroves = BookTextPageModel.builder()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText())
                .build();
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
        var otherworldGroves2 = BookTextPageModel.builder()
                .withText(this.context().pageText())
                .build();
        this.lang().add(this.context().pageText(),
                """
                        **Hint:** In the Overworld, look **down**.
                          """);

        this.context().page("otherworld_trees");
        var otherworldTrees = BookTextPageModel.builder()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText())
                .build();
        this.lang().add(this.context().pageTitle(), "Otherworld Trees");
        this.lang().add(this.context().pageText(),
                """
                        Otherworld trees grow naturally in Otherworld Groves. To the naked eye they appear as oak trees,
                        but to the Third Eye they reveal their true nature.   \s
                        **Important:** Otherworld Saplings can only be obtained by breaking the leaves manually, naturally only oak saplings drop.
                         """);

        this.context().page("otherworld_trees_2");
        var otherworldTrees2 = BookTextPageModel.builder()
                .withText(this.context().pageText())
                .build();
        this.lang().add(this.context().pageText(),
                """
                        Trees grown from Stable Otherworld Saplings as obtained from spirit traders do not have that limitation.
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
                        otherworldTrees2);
    }


    private BookEntryModel makeTheurgyDivinationRodsEntry(CategoryEntryMap entryMap, char icon) {
        this.context().entry("theurgy_divination_rod");

        this.lang().add(this.context().entryName(), "More Divination Rods");
        this.lang().add(this.context().entryDescription(), "Finding other ores and resources.");

        this.context().page("intro");
        var intro = BookSpotlightPageModel.builder()
                .withItem(Ingredient.of(ItemRegistry.DIVINATION_ROD_T1.get()))
                .withText(this.context().pageText())
                .build();

        this.lang().add(this.context().pageText(),
                """
                        While the [](item://occultism:divination_rod) is a great tool for finding [#](%1$s)Otherworld Materials[#](), it would be useful to have a way to find *all other* ores and resources as well.
                        \\
                        \\
                        This is where the Theurgy Divination Rod comes in.
                                """.formatted(COLOR_PURPLE));

        this.context().page("recipe_rod");
        var recipeRod = BookCraftingRecipePageModel.builder()
                .withRecipeId1("theurgy:crafting/shaped/divination_rod_t1")
                .build();

        this.context().page("more_info");
        var moreInfo = BookTextPageModel.builder()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText())
                .build();

        this.lang().add(this.context().pageTitle(), "More Information");
        this.lang().add(this.context().pageText(),
                """
                        To find out more about the Theurgy Divination Rod, check out *"The Hermetica"*, the Guidebook for Theurgy.
                        [This Entry](entry://theurgy:the_hermetica/getting_started/about_divination_rods) has more information about the Theurgy Divination Rod.
                        """);

        this.context().page("recipe_hermetica");
        var recipeHermetica = BookCraftingRecipePageModel.builder()
                .withRecipeId1("theurgy:crafting/shapeless/the_hermetica")
                .build();

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
        var intro = BookSpotlightPageModel.builder()
                .withItem(Ingredient.of(OccultismBlocks.CANDLE_WHITE.get()))
                .withText(this.context().pageText())
                .build();
        this.lang().add(this.context().pageText(),
                """
                        Candles provide stability to rituals and are an important part of almost all pentacles.
                        **Candles also act like bookshelves for enchantment purposes.**
                        \\
                        \\
                        Candles from Minecraft and other Mods may be used in place of Occultism candles.
                            """);

        this.context().page("tallow");
        var tallow = BookSpotlightPageModel.builder()
                .withItem(Ingredient.of(OccultismItems.TALLOW.get()))
                .withText(this.context().pageText())
                .build();
        this.lang().add(this.context().pageText(),
                """
                        Key ingredient for candles. Kill large animals like pigs, cows or sheep with a [](item://occultism:butcher_knife)
                        to harvest [](item://occultism:tallow).
                            """);

        this.context().page("cleaver_recipe");
        var cleaverRecipe = BookCraftingRecipePageModel.builder()
                .withRecipeId1(this.modLoc("crafting/butcher_knife"))
                .build();
        //no text

        this.context().page("candle_recipe");
        var candleRecipe = BookCraftingRecipePageModel.builder()
                .withRecipeId1(this.modLoc("crafting/candle"))
                .build();
        //no text

        return BookEntryModel.create(this.modLoc(this.context().categoryId() + "/" + this.context().entryId()), this.context().entryName())
                .withDescription(this.context().entryDescription())
                .withIcon(OccultismBlocks.CANDLE_WHITE.get())
                .withLocation(entryMap.get(icon))
                .withPages(
                        intro,
                        tallow,
                        cleaverRecipe,
                        candleRecipe
                );
    }

    private BookEntryModel makeRitualPrepChalkEntry(CategoryEntryMap entryMap, char icon) {
        this.context().entry("ritual_prep_chalk");
        this.lang().add(this.context().entryName(), "Ritual Preparations: Chalks");
        this.lang().add(this.context().entryDescription(), "Signs to find them, Signs to bring them all, and in the darkness bind them.");

        this.context().page("intro");
        var intro = BookTextPageModel.builder()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText())
                .build();
        this.lang().add(this.context().pageTitle(), "Ritual Preparations: Chalks");
        this.lang().add(this.context().pageText(),
                """
                        To summon spirits from the [#](%1$s)Other Place[#]() in *relative* safety,
                        you need to draw a fitting pentacle using chalk to contain their powers.
                         """.formatted(COLOR_PURPLE));

        this.context().page("white_chalk");
        var whiteChalkSpotlight = BookSpotlightPageModel.builder()
                .withItem(Ingredient.of(OccultismItems.CHALK_WHITE.get()))
                .withText(this.context().pageText())
                .withAnchor("white_chalk")
                .build();
        this.lang().add(this.context().pageText(),
                """
                        White chalk is used to draw the most basic pentacles, such as for our first ritual.
                        \\
                        \\
                        More powerful summonings require appropriate more advanced chalk, see [Chalks](entry://occultism:dictionary_of_spirits/getting_started/chalks) for more information.
                            """);

        this.context().page("burnt_otherstone_recipe");
        var burntOtherstoneRecipe = BookSmeltingRecipePageModel.builder()
                .withRecipeId1(this.modLoc("smelting/burnt_otherstone"))
                .build();
        //no text

        this.context().page("otherworld_ashes_recipe");
        var otherworldAshesRecipe = BookSpiritFireRecipePageModel.builder()
                .withRecipeId1(this.modLoc("spirit_fire/otherworld_ashes"))
                .build();
        //no text
        this.context().page("impure_white_chalk_recipe");
        var impureWhiteChalkRecipe = BookCraftingRecipePageModel.builder()
                .withRecipeId1(this.modLoc("crafting/chalk_white_impure"))
                .build();
        //no text

        this.context().page("white_chalk_recipe");
        var whiteChalkRecipe = BookSpiritFireRecipePageModel.builder()
                .withRecipeId1(this.modLoc("spirit_fire/chalk_white"))
                .build();
        //no text

        this.context().page("usage");
        var usage = BookTextPageModel.builder()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText())
                .build();
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
        var sacrificialBowlSpotlight = BookSpotlightPageModel.builder()
                .withItem(Ingredient.of(OccultismBlocks.SACRIFICIAL_BOWL.get()))
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText())
                .build();
        this.lang().add(this.context().pageTitle(), "Ritual Preparations: Sacrificial Bowls");
        this.lang().add(this.context().pageText(),
                """
                        These bowls are used to place the items we will sacrifice as part of a ritual and you will need a handful of them.
                        Note: Their exact placement in the ritual does not matter - just keep them within 8 blocks horizontally of the pentacle center!
                             """);

        this.context().page("sacrificial_bowl_recipe");
        var sacrificialBowlRecipe = BookCraftingRecipePageModel.builder()
                .withRecipeId1(this.modLoc("crafting/sacrificial_bowl"))
                .build();
        //no text


        this.context().page("golden_sacrificial_bowl");
        var goldenSacrificialBowlSpotlight = BookSpotlightPageModel.builder()
                .withItem(Ingredient.of(OccultismBlocks.GOLDEN_SACRIFICIAL_BOWL.get()))
                .withText(this.context().pageText())
                .build();
        this.lang().add(this.context().pageText(),
                """
                        Once everything has been set up and you are ready to start, this special sacrificial bowl is used to activate the ritual by [#](%1$s)right-clicking[#]() it with the activation item,
                        usually a [Book of Binding](entry://getting_started/books_of_binding).
                             """.formatted(COLOR_PURPLE));

        this.context().page("golden_sacrificial_bowl_recipe");
        var goldenSacrificialBowlRecipe = BookCraftingRecipePageModel.builder()
                .withRecipeId1(this.modLoc("crafting/golden_sacrificial_bowl"))
                .build();
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
        var intro = BookTextPageModel.builder()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText())
                .build();
        this.lang().add(this.context().pageTitle(), "Books of Binding");
        this.lang().add(this.context().pageText(),
                """
                        To call forth a spirit, a [#](%1$s)Book of Binding[#]() must be used in the ritual.
                        There is a type of book corresponding to each type (or tier) of spirit.
                        To identify a spirit to summon, it's name must be written in the [#](%1$s)Book of Binding[#](), resulting in a [#](%1$s)Bound Book of Binding[#]() that can be used in the ritual.
                         """.formatted(COLOR_PURPLE));

        this.context().page("intro2");
        var intro2 = BookTextPageModel.builder()
                .withText(this.context().pageText())
                .build();
        this.lang().add(this.context().pageText(),
                """
                        **Note:** *The spirit names are eye candy only*, that means they are not relevant for the recipe. As long as you have the right spirit type in your book of binding it can be used.
                         """.formatted(COLOR_PURPLE));

        this.context().page("purified_ink_recipe");
        var purifiedInkRecipe = BookSpiritFireRecipePageModel.builder()
                .withRecipeId1(this.modLoc("spirit_fire/purified_ink"))
                .withText(this.context().pageText())
                .build();
        this.lang().add(this.context().pageText(),
                """
                        In order to craft [#](%1$s)Books of Binding[#]() to summon spirits, you need purified ink. Simply drop any black dye into [](item://occultism:spirit_fire) to purify it.
                            """.formatted(COLOR_PURPLE));

        this.context().page("awakened_feather_recipe");
        var awakenedFeatherRecipe = BookSpiritFireRecipePageModel.builder()
                .withRecipeId1(this.modLoc("spirit_fire/awakened_feather"))
                .withText(this.context().pageText())
                .build();
        this.lang().add(this.context().pageText(),
                """
                        In order to craft [#](%1$s)Books of Binding[#]() to summon spirits, you also need awakened feather. Simply drop any feather into [](item://occultism:spirit_fire) to awakened it.
                            """.formatted(COLOR_PURPLE));

        this.context().page("taboo_book_recipe");
        var tabooBookRecipe = BookSpiritFireRecipePageModel.builder()
                .withRecipeId1(this.modLoc("spirit_fire/taboo_book"))
                .withText(this.context().pageText())
                .build();
        this.lang().add(this.context().pageText(),
                """
                        Lastly you need taboo book to craft [#](%1$s)Books of Binding[#]() to summon spirits. Simply drop a book into [](item://occultism:spirit_fire) to get it.
                        """.formatted(COLOR_PURPLE));

        this.context().page("book_of_binding_foliot_recipe");
        var bookOfBindingFoliotRecipe = BookCraftingRecipePageModel.builder()
                .withRecipeId1(this.modLoc("crafting/book_of_binding_foliot"))
                .withText(this.context().pageText())
                .build();
        this.lang().add(this.context().pageText(),
                """
                        Craft a book of binding that will be used to call forth a [#](%1$s)Foliot[#]() spirit.
                           """.formatted(COLOR_PURPLE));

        this.context().page("book_of_binding_bound_foliot_recipe");
        var bookOfBindingBoundFoliotRecipe = BookBindingCraftingRecipePageModel.builder()
                .withRecipeId1()
                .withUnboundBook(new ItemStack(OccultismItems.BOOK_OF_BINDING_FOLIOT.get()))
                .withText(this.context().pageText()).build();
        this.lang().add(this.context().pageText(),
                """
                        Add the name of the spirit to summon to your book of binding by crafting it with the Dictionary of Spirits. The Dictionary will not be used up.
                           """);

        this.context().page("book_of_binding_djinni_recipe");
        var bookOfBindingDjinniRecipe = BookCraftingRecipePageModel.builder()
                .withRecipeId1(this.modLoc("crafting/book_of_binding_djinni")).build();

        this.context().page("book_of_binding_bound_djinni_recipe");
        var bookOfBoundBindingDjinniRecipe = BookBindingCraftingRecipePageModel.builder()
                .withRecipeId1()
                .withUnboundBook(new ItemStack(OccultismItems.BOOK_OF_BINDING_DJINNI.get())).build();
        //no text

        this.context().page("book_of_binding_afrit_recipe");
        var bookOfBindingAfritRecipe = BookCraftingRecipePageModel.builder()
                .withRecipeId1(this.modLoc("crafting/book_of_binding_afrit")).build();
        //no text

        this.context().page("book_of_binding_bound_afrit_recipe");
        var bookOfBoundBindingAfritRecipe = BookBindingCraftingRecipePageModel.builder()
                .withRecipeId1()
                .withUnboundBook(new ItemStack(OccultismItems.BOOK_OF_BINDING_AFRIT.get())).build();
        //no text

        this.context().page("book_of_binding_marid_recipe");
        var bookOfBindingMaridRecipe = BookCraftingRecipePageModel.builder()
                .withRecipeId1(this.modLoc("crafting/book_of_binding_marid")).build();
        //no text

        this.context().page("book_of_binding_bound_marid_recipe");
        var bookOfBoundBindingMaridRecipe = BookBindingCraftingRecipePageModel.builder()
                .withRecipeId1()
                .withUnboundBook(new ItemStack(OccultismItems.BOOK_OF_BINDING_MARID.get())).build();
        //no text

        this.context().page("book_of_binding_empty");
        var alternativeBooks = BookSpiritFireRecipePageModel.builder()
                .withRecipeId1(this.modLoc("spirit_fire/book_of_binding_empty"))
                .withText(this.context().pageText())
                .build();
        this.lang().add(this.context().pageText(),
                """
                         Alternatively, you can directly use the Binding Book: Empty instead of the previous three items. There are two ways to obtain this book. Place this book in the center of dyes to get specific book of binding.
                        """.formatted(COLOR_PURPLE));

        this.context().page("book_of_binding_empty_recipe");
        var bookOfBindingEmptyRecipe = BookCraftingRecipePageModel.builder()
                .withRecipeId2(this.modLoc("crafting/book_of_binding_empty"))
                .build();
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
        var intro = BookTextPageModel.builder()
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
        var solution = BookTextPageModel.builder()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText());
        this.lang().add(this.context().pageTitle(), "The Solution");
        this.lang().add(this.context().pageText(),
                """
                        1. Put a dictionary of spirits into an anvil and give it a name. This will be the name of all spirits summoned in the future.
                        2. Use this dictionary to configure crafting patterns (if your automation mod requires it).
                        """
        );

        this.context().page("solution2");
        var solution2 = BookTextPageModel.builder()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText());
        this.lang().add(this.context().pageTitle(), "The Solution");
        this.lang().add(this.context().pageText(),
                """
                        3. Use this dictionary to craft the Bound Books of Binding in the automation system. As usual, the dictionary will not be used up.
                        4. All crafted books will now have the same name and will be recognized by your automation system.
                        """
        );

        return BookEntryModel.create(this.modLoc(this.context().categoryId() + "/" + this.context().entryId()), this.context().entryName())
                .withDescription(this.context().entryDescription())
                .withIcon(Items.CRAFTING_TABLE)
                .withLocation(entryMap.get(icon))
                .withPages(
                        intro.build(),
                        solution.build(),
                        solution2.build()
                );
    }

    private BookEntryModel makeBooksOfCallingEntry(CategoryEntryMap entryMap, char icon) {
        this.context().entry("books_of_calling");
        this.lang().add(this.context().entryName(), "Books of Calling");
        this.lang().add(this.context().entryDescription(), "Telling your spirits what to do");

        this.context().page("intro");
        var intro = BookTextPageModel.builder()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText())
                .build();
        this.lang().add(this.context().pageTitle(), "Books of Calling");
        this.lang().add(this.context().pageText(),
                """
                        Books of Calling allow to control a summoned spirit, and to store it to prevent essence decay or move it more easily. 
                        \\
                        \\
                        Only spirits that require precise instructions - such as a work area or drop-off storage - come with a book of calling.
                        """);

        this.context().page("usage");
        var usage = BookTextPageModel.builder()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText())
                .build();
        this.lang().add(this.context().pageTitle(), "Usage");
        this.lang().add(this.context().pageText(),
                """
                        - [#](%1$s)Right-click[#]() air to open the configuration screen
                        - [#](%1$s)Shift-right-click[#]() a block to apply the action selected in the configuration screen
                        - [#](%1$s)Shift-right-click[#]() a spirit to capture it (must be of the same type)
                        - [#](%1$s)Right-click[#]() with a book with a captured spirit to release it
                        """.formatted(COLOR_PURPLE));

        this.context().page("obtaining");
        var obtaining = BookTextPageModel.builder()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText())
                .build();
        this.lang().add(this.context().pageTitle(), "How to obtain Books of Calling");
        this.lang().add(this.context().pageText(),
                """
                        If a summoned spirit supports the use of a Book of Calling, the summoning ritual will automatically spawn a book in the world alongside the spirit.
                        \\
                        \\
                        If you **lose the book**, there are also crafting recipes that just provide the book (without summoning a spirit).
                        """.formatted(COLOR_PURPLE));

        this.context().page("obtaining2");
        var obtaining2 = BookTextPageModel.builder()
                .withText(this.context().pageText())
                .build();
        this.lang().add(this.context().pageText(),
                """
                        The recipes can be found in this book or via JEI.
                        \\
                        \\
                        [#](%1$s)Shift-right-click[#]() the spirit with the crafted book to assign it.
                        """.formatted(COLOR_PURPLE));

        this.context().page("storage");
        var storage = BookTextPageModel.builder()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText())
                .build();
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
        this.lang.add(this.context().entryName(), "First Ritual");
        this.lang.add(this.context().entryDescription(), "We're actually getting started now!");

        this.context().page("intro");
        var intro = BookTextPageModel.builder()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText())
                .build();
        this.lang.add(this.context().pageTitle(), "The Ritual (tm)");
        this.lang.add(this.context().pageText(),
                """
                        These pages will walk the gentle reader through the process of the [first ritual](entry://summoning_rituals/summon_crusher_t1) step by step.
                        \\
                        We **start** by placing the [](item://occultism:golden_sacrificial_bowl) and drawing the appropriate pentacle, [Aviar's Circle](entry://pentacles/summon_foliot) as seen on the left around it.
                          """.formatted(COLOR_PURPLE));

        this.context().page("multiblock");
        var multiblock = BookMultiblockPageModel.builder()
                .withMultiblockId(this.modLoc("summon_foliot"))
                .withText(this.context().pageText())
                .build();
        this.lang.add(this.context().pageText(),
                """
                        Only the color and location of the chalk marks is relevant, not the glyph/sign.
                          """.formatted(COLOR_PURPLE));

        this.context().page("bowl_text");
        var bowlText = BookTextPageModel.builder()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText())
                .build();
        this.lang.add(this.context().pageTitle(), "Sacrificial Bowls");
        this.lang.add(this.context().pageText(),
                """
                        Next, place *at least* 4 [Sacrificial Bowls](item://occultism:sacrificial_bowl) close to the pentacle.
                        \\
                        \\
                        They must be placed **anywhere** within 8 blocks of the central [](item://occultism:golden_sacrificial_bowl). **The exact location does not matter.**
                          """.formatted(COLOR_PURPLE));

        this.context().page("bowl_placement");
        var bowlPlacementImage = BookImagePageModel.builder()
                .withImages(this.modLoc("textures/gui/book/bowl_placement.png"))
                .withBorder(true)
                .withText(this.context().pageText())
                .build();
        this.lang.add(this.context().pageText(),
                """
                        Possible locations for the sacrificial bowls.
                          """.formatted(COLOR_PURPLE));

        this.context().page("ritual_text");
        var ritualText = BookTextPageModel.builder()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText())
                .build();
        this.lang.add(this.context().pageTitle(), "Placing Ingredients");
        this.lang.add(this.context().pageText(),
                """
                        Now it is time to place the ingredients you see on the next page in the (regular, not golden) sacrificial bowls. The ingredients will be consumed from the bowls as the ritual progresses.
                          """.formatted(COLOR_PURPLE));

        this.context().page("ritual_recipe");
        var ritualRecipe = BookRitualRecipePageModel.builder()
                .withRecipeId1(this.modLoc("ritual/summon_foliot_crusher"))
                .build();
        //no text

        this.context().page("pentacle_link_hint");
        var pentacleLinkHint = BookTextPageModel.builder()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText())
                .build();
        this.lang.add(this.context().pageTitle(), "A Note about Ritual Recipes");
        this.lang.add(this.context().pageText(),
                """
                        Ritual recipe pages, such as the previous pageshow not only the ingredients, but also the pentacle that you need to draw with chalk in order to use the ritual.
                        \\
                        \\
                        **To show the pentacle, click the blue link** at the center top of the ritual page. You can then even preview it in-world.
                          """.formatted(COLOR_PURPLE));

        this.context().page("start_ritual");
        var startRitualText = BookTextPageModel.builder()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText())
                .build();
        this.lang.add(this.context().pageTitle(), "Let there be ... spirits!");
        this.lang.add(this.context().pageText(),
                """
                        Finally, [#](%1$s)right-click[#]() the [](item://occultism:golden_sacrificial_bowl) with the **bound** book of binding you created before and wait until the crusher spawns.
                        \\
                        \\
                        Now all that remains is to drop appropriate ores near the crusher and wait for it to turn it into dust.
                          """.formatted(COLOR_PURPLE));

        this.context().page("automation");
        var automationText = BookTextPageModel.builder()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText())
                .build();
        this.lang.add(this.context().pageTitle(), "Automatic Rituals");
        this.lang.add(this.context().pageText(),
                """
                        Instead of right-clicking the golden sacrificial bowl with the final ingredient, you can also use a Hopper or any type of pipe to insert the item into the bowl. The ritual will start automatically.\\
                        Note that any rituals that summon tamed animals or familiars will summon them untamed instead.
                          """.formatted(COLOR_PURPLE));

        this.context().page("redstone");
        var redstoneText = BookTextPageModel.builder()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText())
                .build();
        this.lang.add(this.context().pageTitle(), "Redstone");
        this.lang.add(this.context().pageText(),
                """
                        Depending on the ritual state the golden bowl will emit a different redstone level:
                        - **0** if no ritual is active
                        - **1** if the ritual is active, but waiting for a sacrifice
                        - **2** if the ritual is active, but waiting for an item to be used
                        - **4** if the ritual is active and running
                        """.formatted(COLOR_PURPLE));


        return BookEntryModel.create(this.modLoc(this.context().categoryId() + "/" + this.context().entryId()), this.context().entryName())
                .withDescription(this.context().entryDescription())
                .withIcon(OccultismItems.PENTACLE.get())
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
                        redstoneText
                );
    }

    private BookEntryModel makeBrushEntry(CategoryEntryMap entryMap, char icon) {
        this.context().entry("brush");
        this.lang().add(this.context().entryName(), "Brush");
        this.lang().add(this.context().entryDescription(), "Cleaning up!");

        this.context().page("intro");
        var intro = BookTextPageModel.builder()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText())
                .build();
        this.lang().add(this.context().pageTitle(), "Next Steps");
        this.lang().add(this.context().pageText(),
                """
                        Chalk is a pain to clean up, by [#](%1$s)right-clicking[#]() with a brush you can remove it from the world much more easily.
                          """.formatted(COLOR_PURPLE));

        this.context().page("brushRecipe");
        var brushRecipe = BookCraftingRecipePageModel.builder()
                .withRecipeId1(this.modLoc("crafting/brush"))
                .build();
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
        var text = BookTextPageModel.builder()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText())
                .build();
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
        var intro = BookTextPageModel.builder()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText())
                .build();
        this.lang().add(this.context().pageTitle(), "More Chalks");
        this.lang().add(this.context().pageText(),
                """
                        For more advanced rituals the basic [White Chalk](entry://occultism:dictionary_of_spirits/getting_started/ritual_prep_chalk) is not sufficient. Instead chalks made from more arcane materials are required.
                        """);

        this.context().page("impure_gold_chalk_recipe");
        var impureGoldChalkRecipe = BookCraftingRecipePageModel.builder()
                .withRecipeId1(this.modLoc("crafting/chalk_gold_impure"))
                .build();
        //no text

        this.context().page("gold_chalk_recipe");
        var goldChalkRecipe = BookSpiritFireRecipePageModel.builder()
                .withRecipeId1(this.modLoc("spirit_fire/chalk_gold"))
                .build();
        //no text

        this.context().page("impure_purple_chalk_recipe");
        var impurePurpleChalkRecipe = BookCraftingRecipePageModel.builder()
                .withRecipeId1(this.modLoc("crafting/chalk_purple_impure"))
                .withText(this.context().pageText())
                .build();
        this.lang().add(this.context().pageText(),
                """
                        You do not need to visit the [#](%1$s)The End[#]() to obtain Endstone. You can summon a [Possessed Endermite](entry://possession_rituals/possess_endermite) which has a high chance to drop it.
                             """.formatted(COLOR_PURPLE));

        this.context().page("purple_chalk_recipe");
        var purpleChalkRecipe = BookSpiritFireRecipePageModel.builder()
                .withRecipeId1(this.modLoc("spirit_fire/chalk_purple"))
                .build();
        //no text

        this.context().page("impure_red_chalk_recipe");
        var impureRedChalkRecipe = BookCraftingRecipePageModel.builder()
                .withRecipeId1(this.modLoc("crafting/chalk_red_impure"))
                .build();
        //no text

        this.context().page("red_chalk_recipe");
        var redChalkRecipe = BookSpiritFireRecipePageModel.builder()
                .withRecipeId1(this.modLoc("spirit_fire/chalk_red"))
                .build();
        //no text

        this.context().page("afrit_essence");
        var afritEssenceSpotlight = BookSpotlightPageModel.builder()
                .withItem(Ingredient.of(OccultismItems.AFRIT_ESSENCE.get()))
                .withText(this.context().pageText())
                .build();
        this.lang().add(this.context().pageText(),
                """
                        To obtain the essence of an [#](%1$s)Afrit[#]() for [](item://occultism:chalk_red) you need to [summon and kill an Unbound Afrit](entry://summoning_rituals/afrit_essence).
                        """.formatted(COLOR_PURPLE));

        return BookEntryModel.create(this.modLoc(this.context().categoryId() + "/" + this.context().entryId()), this.context().entryName())
                .withDescription(this.context().entryDescription())
                .withIcon(OccultismItems.CHALK_GOLD.get())
                .withLocation(entryMap.get(icon))
                .withPages(
                        intro,
                        impureGoldChalkRecipe,
                        goldChalkRecipe,
                        impurePurpleChalkRecipe,
                        purpleChalkRecipe,
                        impureRedChalkRecipe,
                        redChalkRecipe,
                        afritEssenceSpotlight
                );
    }

    private BookEntryModel makeOtherworldGogglesEntry(CategoryEntryMap entryMap, char icon) {
        this.context().entry("otherworld_goggles");
        this.lang().add(this.context().entryName(), "Otherworld Goggles");
        this.lang().add(this.context().entryDescription(), "Say no to drugs!");

        this.context().page("spotlight");
        var spotlight = BookSpotlightPageModel.builder()
                .withItem(Ingredient.of(OccultismItems.OTHERWORLD_GOGGLES.get()))
                .withText(this.context().pageText())
                .build();
        this.lang().add(this.context().pageText(),
                """
                        The [](item://occultism:otherworld_goggles) are what advanced summoners use to see the [#](%1$s)Otherworld[#](), to avoid the negative side effects of [](entry://occultism:dictionary_of_spirits/getting_started/demons_dream).
                        \\
                        \\
                        Making your first pair of these is seen by many as a rite of passage.
                        """.formatted(COLOR_PURPLE));

        this.context().page("crafting");
        var crafting = BookTextPageModel.builder()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText())
                .build();
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
        var spotlight = BookSpotlightPageModel.builder()
                .withItem(Ingredient.of(OccultismItems.INFUSED_PICKAXE.get()))
                .withText(this.context().pageText())
                .build();
        this.lang().add(this.context().pageText(),
                """
                        Beyond [](item://occultism:otherworld_log) and [](item://occultism:otherstone) there are also otherworld materials that require special tools to harvest. 
                        \\
                        \\
                        This pickaxe is rather brittle, but it will do the job.
                        """);

        this.context().page("gem_recipe");
        var gemRecipe = BookSpiritFireRecipePageModel.builder()
                .withRecipeId1(this.modLoc("spirit_fire/spirit_attuned_gem"))
                .withText(this.context().pageText())
                .build();
        this.lang().add(this.context().pageText(),
                """
                        These gems, when infused with a spirit, can be used to interact with Otherword materials and are the key to crafting the pickaxe.
                        """);

        this.context().page("head_recipe");
        var headRecipe = BookCraftingRecipePageModel.builder()
                .withRecipeId1(this.modLoc("crafting/spirit_attuned_pickaxe_head"))
                .build();
        //no text

        this.context().page("crafting");
        var crafting = BookTextPageModel.builder()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText())
                .build();
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
        var spotlight = BookSpotlightPageModel.builder()
                .withItem(Ingredient.of(OccultismBlocks.IESNIUM_ORE.get()))
                .withText(this.context().pageText())
                .build();
        this.lang().add(this.context().pageText(),
                """
                        This is a rare metal that, to the naked eye, looks like [](item://minecraft:netherrack) and cannot be mined with a regular pickaxe.
                        \\
                        \\
                        When mined with the correct tools, it can be used to craft powerful items (you will learn more about that later).
                             """.formatted(COLOR_PURPLE));

        this.context().page("where");
        var where = BookTextPageModel.builder()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText())
                .build();
        this.lang().add(this.context().pageTitle(), "Where to find it");
        this.lang().add(this.context().pageText(),
                """
                        Like Netherrack, Iesnium can be found in the Nether. In order to **see** it, you need to wear [Otherworld Goggles](entry://getting_started/otherworld_goggles).
                        \\
                        \\
                        To make searching for it simpler, attune a [Divination Rod](entry://getting_started/divination_rod) to it and righ-click and hold in the nether until it highlights a nearby block, which will hold the ore.
                             """.formatted(COLOR_PURPLE));

        this.context().page("how");
        var how = BookTextPageModel.builder()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText())
                .build();
        this.lang().add(this.context().pageTitle(), "How to mine it");
        this.lang().add(this.context().pageText(),
                """
                        Iesnium can only be mined with the [Infused Pickaxe](entry://getting_started/infused_pickaxe) or an [](item://occultism:iesnium_pickaxe) (about which you will learn later).
                        \\
                        \\
                        After identifying a block that holds Iesnium, you can mine it with the pickaxe you created in the previous step.
                             """.formatted(COLOR_PURPLE));

        this.context().page("processing");
        var processing = BookTextPageModel.builder()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText())
                .build();
        this.lang().add(this.context().pageTitle(), "Processing");
        this.lang().add(this.context().pageText(),
                """
                        Iesnium Ore, after mining, can be smelted directly into ingots, or placed down. When placed, it will not turn back into it's netherrack form. Consequently it can also be mined with any pickaxe then. This visible form of the Ore, when mined, will drop [](item://occultism:raw_iesnium).
                             """.formatted(COLOR_PURPLE));

        this.context().page("uses");
        var uses = BookTextPageModel.builder()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText())
                .build();
        this.lang().add(this.context().pageTitle(), "Uses");
        this.lang().add(this.context().pageText(),
                """
                        Iesnium can be used to craft an improved pickaxe, spirit lamps, and other powerful items. Follow the progress in this book to learn more about it.
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
                        uses
                );
    }

    private BookEntryModel makeIesniumPickaxeEntry(CategoryEntryMap entryMap, char icon) {
        this.context().entry("iesnium_pickaxe");
        this.lang().add(this.context().entryName(), "Iesnium Pickaxe");
        this.lang().add(this.context().entryDescription(), "A more durable otherworld-appropriate pickaxe");

        this.context().page("spotlight");
        var spotlight = BookSpotlightPageModel.builder()
                .withItem(Ingredient.of(OccultismItems.IESNIUM_PICKAXE.get()))
                .withText(this.context().pageText())
                .build();
        this.lang().add(this.context().pageText(),
                """
                        Like the [Infused Pickaxe](entry://getting_started/infused_pickaxe), this pickaxe can be used to mine Tier 2 Otherworld Materials such as [](item://occultism:iesnium_ore). As it is made from metal, instead of brittle [](item://occultism:spirit_attuned_gem), it is very durable and can be used for a long time.
                             """.formatted(COLOR_PURPLE));

        this.context().page("crafting");
        var crafting = BookCraftingRecipePageModel.builder()
                .withRecipeId1(this.modLoc("crafting/iesnium_pickaxe"))
                .build();
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
        var spotlight = BookSpotlightPageModel.builder()
                .withItem(Ingredient.of(OccultismItems.MAGIC_LAMP_EMPTY.get()))
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText())
                .build();
        this.lang().add(this.context().pageTitle(), "Magic Lamps");
        this.lang().add(this.context().pageText(),
                """
                        Magic Lamps can be used to keep spirits safe from [#](%1$s)Essence Decay[#](), while still having access to some of their powers. Most commonly they are used to access a [#](%1$s)Mining Dimension[#]() and act as (*lag free*) [#](%1$s)Void Miners[#]().
                             """.formatted(COLOR_PURPLE));

        this.context().page("crafting");
        var crafting = BookCraftingRecipePageModel.builder()
                .withRecipeId1(this.modLoc("crafting/magic_lamp_empty"))
                .build();
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
        var spotlight = BookSpotlightPageModel.builder()
                .withItem(Ingredient.of(OccultismItems.MINER_FOLIOT_UNSPECIALIZED.get()))
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText())
                .build();
        this.lang().add(this.context().pageTitle(), "Spirit Miners");
        this.lang().add(this.context().pageText(),
                """
                        By summoning a spirit into a Magic Lamp and placing it in a [Dimensional Mineshaft (see next step)](entry://getting_started/mineshaft) it can be made to mine for you in a [#](%1$s)Mining Dimension[#](). This is a great way to get resources without having to go mining in the overworld (or other dimesions) yourself.
                             """.formatted(COLOR_PURPLE));

        this.context().page("crafting");
        var crafting = BookTextPageModel.builder()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText())
                .build();
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
        var spotlight = BookSpotlightPageModel.builder()
                .withItem(Ingredient.of(OccultismBlocks.DIMENSIONAL_MINESHAFT.get()))
                .withText(this.context().pageText())
                .build();
        this.lang().add(this.context().pageText(),
                """
                        This block acts as a portal, for spirits only, to the [#](%1$s)Mining Dimension[#](). Place a Magic Lamp with a Miner Spirit in it, to make it mine for you.
                             """.formatted(COLOR_PURPLE));

        this.context().page("crafting");
        var crafting = BookTextPageModel.builder()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText())
                .build();
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
        var intro = BookTextPageModel.builder()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText())
                .build();
        this.lang().add(this.context().pageTitle(), "Possession Rituals");
        this.lang().add(this.context().pageText(),
                """
                        Possessed mobs are controlled by spirits, allowing the summoner to determine some of their properties. They usually have **high drop rates** for rare drops, but are generally harder to kill.
                        \\
                        \\
                        You probably will want to start by summoning a [Possessed Endermite](entry://possession_rituals/possess_endermite) to get [](item://minecraft:end_stone) to craft [Advanced Chalks](entry://getting_started/chalks).
                             """.formatted(COLOR_PURPLE));

        this.context().page("more");
        var more = BookTextPageModel.builder()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText())
                .build();
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
        var intro = BookTextPageModel.builder()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText())
                .build();
        this.lang().add(this.context().pageTitle(), "Familiar Rituals");
        this.lang().add(this.context().pageText(),
                """
                        Familiars provide a variety of bonus effects, such as feather falling, water breathing, jump boosts and more, and may also assist you in combat.
                        \\
                        \\
                        Store them in a [Familiar Ring](entry://crafting_rituals/craft_familiar_ring) to equip them as a curio.
                             """.formatted(COLOR_PURPLE));

        this.context().page("more");
        var more = BookTextPageModel.builder()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText())
                .build();
        this.lang().add(this.context().pageTitle(), "More Information");
        this.lang().add(this.context().pageText(),
                """
                        To find more about Familiars, see the [Familiar Rituals](category://familiar_rituals) Category.
                             """.formatted(COLOR_PURPLE));

        return BookEntryModel.create(this.modLoc(this.context().categoryId() + "/" + this.context().entryId()), this.context().entryName())
                .withDescription(this.context().entryDescription())
                .withIcon(this.modLoc("textures/gui/book/parrot.png"))
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
        var intro = BookTextPageModel.builder()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText())
                .build();
        this.lang().add(this.context().pageTitle(), "Summoning Rituals");
        this.lang().add(this.context().pageText(),
                """
                        Summoning Rituals allow you to summon spirits to work for you. Unlike familiars, they are not personally bound to you, meaning they will not follow you around, but they will perform various work tasks for you. In fact the first ritual you performed, the [Foliot Crusher](entry://getting_started/first_ritual), was a summoning ritual.
                             """.formatted(COLOR_PURPLE));

        this.context().page("more");
        var more = BookTextPageModel.builder()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText())
                .build();
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
        var intro = BookTextPageModel.builder()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText())
                .build();
        this.lang().add(this.context().pageTitle(), "Infusion Rituals");
        this.lang().add(this.context().pageText(),
                """
                        Infusion rituals are all about crafting powerful items, by binding ("infusing") spirits into objects.The spirits will provide special functionality to the items.
                             """.formatted(COLOR_PURPLE));

        this.context().page("more");
        var more = BookTextPageModel.builder()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText())
                .build();
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

    //endregion

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
        returnToGettingStarted.withCondition(BookTrueConditionModel.builder().build());

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
                .withEntryBackground(1, 2)
                .withLocation(entryMap.get(icon));
    }

    private BookEntryModel makeSpiritsOverviewEntry(CategoryEntryMap entryMap, char icon) {
        this.context().entry("overview");
        this.lang().add(this.context().entryName(), "On Spirits");
        this.lang().add(this.context().entryDescription(), "An overview of the supernatural");

        this.context().page("intro");
        var intro = BookTextPageModel.builder()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText())
                .build();
        this.lang().add(this.context().pageTitle(), "On Spirits");
        this.lang().add(this.context().pageText(),
                """
                        [#](%1$s)Spirit[#](), commonly referred to also as [#](%1$s)Demon[#](), is a general term for a variety of supernatural entities usually residing in [#](%1$s)The Other Place[#](), a plane of existence entirely separate from our own.
                        """.formatted(COLOR_PURPLE));

        this.context().page("shapes");
        var shapes = BookTextPageModel.builder()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText())
                .build();
        this.lang().add(this.context().pageTitle(), "Shapes");
        this.lang().add(this.context().pageText(),
                """
                        When in our world Spirits can take a variety of forms, by morphing their essence into [#](%1$s)Chosen Forms[#](). Alternatively, they can inhabit objects or even living beings.
                          """.formatted(COLOR_PURPLE));

        this.context().page("tiers");
        var tiers = BookTextPageModel.builder()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText())
                .build();
        this.lang().add(this.context().pageTitle(), "Types of Spirits");
        this.lang().add(this.context().pageText(),
                """
                        There are four major "ranks" of spirits identified by researchers, but there are a myriad spirits below and in between these ranks, and some great entities of terrible power, referred to only as [#](%1$s)Greater Spirits[#](), that are beyond classification.
                         """.formatted(COLOR_PURPLE));

        this.context().page("foliot");
        var foliot = BookTextPageModel.builder()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText())
                .build();
        this.lang().add(this.context().pageTitle(), "Foliot");
        this.lang().add(this.context().pageText(),
                """
                        The lowest identified class of spirit. Equipped with some intelligence and a modicum of power they are most often used for manual labor or minor artifacts.
                         """.formatted(COLOR_PURPLE));

        this.context().page("djinni");
        var djinni = BookTextPageModel.builder()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText())
                .build();
        this.lang().add(this.context().pageTitle(), "Djinni");
        this.lang().add(this.context().pageText(),
                """
                        The most commonly summoned class. There is a great variety of Djinni, differing both in intelligence and power. Djinni can be used for a variety of task, ranging from higher artifacts over possession of living beings to carrying out tasks in their Chosen Form.
                         """.formatted(COLOR_PURPLE));

        this.context().page("afrit");
        var afrit = BookTextPageModel.builder()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText())
                .build();
        this.lang().add(this.context().pageTitle(), "Afrit");
        this.lang().add(this.context().pageText(),
                """
                        Even more powerful than Djinni, Afrit are used for the creation of major artifacts and the possession of powerful beings.
                         """.formatted(COLOR_PURPLE));


        this.context().page("marid");
        var marid = BookTextPageModel.builder()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText())
                .build();
        this.lang().add(this.context().pageTitle(), "Marid");
        this.lang().add(this.context().pageText(),
                """
                        The strongest identified class of spirits. Due to their power and vast intellect attempting a summoning is extremely dangerous and usually only carried out by the most experienced summoners, and even then usually in groups.
                         """.formatted(COLOR_PURPLE));

        this.context().page("greater_spirits");
        var greaterSpirits = BookTextPageModel.builder()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText())
                .build();
        this.lang().add(this.context().pageTitle(), "Greater Spirits");
        this.lang().add(this.context().pageText(),
                """
                        Spirits of power so great it is beyond measure. No summons have been attempted in living memory, and records of summonings in ancient times are mostly considered apocryphal.
                         """.formatted(COLOR_PURPLE));


        return BookEntryModel.create(this.modLoc(this.context().categoryId() + "/" + this.context().entryId()), this.context().entryName())
                .withDescription(this.context().entryDescription())
                .withIcon(this.modLoc("textures/gui/book/spirits.png"))
                .withLocation(entryMap.get(icon))
                .withEntryBackground(0, 1)
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
        var intro = BookTextPageModel.builder()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText())
                .build();
        this.lang().add(this.context().pageTitle(), "Essence Decay");
        this.lang().add(this.context().pageText(),
                """
                        When residing in our plane of existence, spirits experience [#](%1$s)Essence Decay[#](), the slow rot of their "body". The more powerful the spirit, the slower the decay, but only the most powerful can stop it entirely. Once fully decayed they are returned to [#](%1$s)The Other Place[#]() and can only be re-summoned once fully recovered.
                        """.formatted(COLOR_PURPLE));

        this.context().page("countermeasures");
        var countermeasures = BookTextPageModel.builder()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText())
                .build();
        this.lang().add(this.context().pageTitle(), "Countermeasures");
        this.lang().add(this.context().pageText(),
                """
                        The summoner can slow or even stop essence decay by binding the spirit into an object, or summoning it into a living being. Additionally the pentacle used can influence the effects of essence decay to a degree.
                        """.formatted(COLOR_PURPLE));


        this.context().page("affected_spirits");
        var affectedSpirits = BookTextPageModel.builder()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText())
                .build();
        this.lang().add(this.context().pageTitle(), "Affected Spirits");
        this.lang().add(this.context().pageText(),
                """
                        Only tier 1 spirits are affected by essence decay, by default. All higher tiers are immune and will not despawn. Modpacks may modify this behaviour.
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
        var intro = BookTextPageModel.builder()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText())
                .build();
        this.lang().add(this.context().pageTitle(), "True Names");
        this.lang().add(this.context().pageText(),
                """
                        To summon a spirit the magician needs to know their [#](%1$s)True Name[#](). By calling the true naming during the summoning ritual the Spirit is drawn forth from [#](%1$s)The Other Place[#]() and forced to do the summoners bidding.
                        \\
                        \\
                        *It should be noted, that it does not matter which spirit name is used in summoning, only the spirit tier is relevant.*
                         """.formatted(COLOR_PURPLE));

        this.context().page("finding_names");
        var findingNames = BookTextPageModel.builder()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText())
                .build();
        this.lang().add(this.context().pageTitle(), "Finding Names");
        this.lang().add(this.context().pageText(),
                """
                        In ancient summoners had to research and experiment to find [#](%1$s)True Names[#](). Some spirits can be convinced to share their knowledge of true names of other demons, either by promising a swift return to [#](%1$s)The Other Place[#](), or by more ... *persuasive* measures.
                        """.formatted(COLOR_PURPLE));

        this.context().page("using_names");
        var usingNames = BookTextPageModel.builder()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText())
                .build();
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
        var intro = BookTextPageModel.builder()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText())
                .build();
        this.lang().add(this.context().pageTitle(), "Unbound Spirits");
        this.lang().add(this.context().pageText(),
                """
                        Generally spirits are summoned [#](%1$s)bound[#](), which refers to any condition that keeps them under control of the summoner. A side effect of binding spells is that part of the spirit remains in [#](%1$s)The Other Place[#](), robbing them of large portions of the power, but at the same time also protecting their essence from foreign access in this world.
                        """.formatted(COLOR_PURPLE));

        this.context().page("unbound");
        var unbound = BookTextPageModel.builder()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText())
                .build();
        this.lang().add(this.context().pageTitle(), "Forego the Leash");
        this.lang().add(this.context().pageText(),
                """
                        In order to access a spirit's essence, or unleash it's full destructive power, it needs to be summoned [#](%1$s)unbound[#](). Unbound summonings use pentacles that are intentionally incomplete or unstable, allowing to call on the spirit, but not putting any constraints on it.
                        """.formatted(COLOR_PURPLE));

        this.context().page("unbound2");
        var unbound2 = BookTextPageModel.builder()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText())
                .build();
        this.lang().add(this.context().pageTitle(), "Beware!");
        this.lang().add(this.context().pageText(),
                """
                        The lack of restraints when summoning spirits unbound makes these rituals incredibly dangerous, but you may find that the rewards are worth the risk - and often there is no way around them to achieve certain results.
                        """.formatted(COLOR_PURPLE));

        this.context().page("essence");
        var essence = BookTextPageModel.builder()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText())
                .build();
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
        var intro = BookTextPageModel.builder()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText())
                .build();
        this.lang().add(this.context().pageTitle(), "The Wild Hunt");
        this.lang().add(this.context().pageText(),
                """
                        A group of legendary Greater Spirits, usually appearing in the form of wither skeletons, with their skeleton minions. The Greater Spirits are bound to their minions in such fashion that they are virtually invulnerable until their minions have been sent back to [#](%1$s)The Other Place[#]().
                        """.formatted(COLOR_PURPLE));

        this.context().page("wither_skull");
        var witherSkull = BookTextPageModel.builder()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText())
                .build();
        this.lang().add(this.context().pageTitle(), "Wither Skeleton Skulls");
        this.lang().add(this.context().pageText(),
                """
                        While it is incredibly dangerous to call on the Wild Hunt, some summoners have been known to do so for quick access to the rare wither skeleton skulls they are known to leave behind. Summoning the wild hunt is described in detail on the page on obtaining [Wither Skeleton Skulls](entry://summoning_rituals/wither_skull).
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

    private BookCategoryModel makePentaclesCategory() {
        this.context().category("pentacles");

        var entryMap = ModonomiconAPI.get().getEntryMap();
        entryMap.setMap(
                "____________________",
                "__p_a_b_c_d_e_f_____", //paraphernalia, summon foliot, summon djinni, summon wild afrit, summon afrit, summon marid, summon wild greater spirit
                "____________________",
                "__o___g_h_i_________", //overview, possess foliot, possess djinni, possess afrit
                "____________________",
                "__u_j_k_l_m_________", //uses of chalks, craft foliot, craft djinni, craft afrit, craft marid
                "____________________"
        );

        var overview = this.makePentaclesOverviewEntry(entryMap, 'o');
        var paraphernalia = this.makeParaphernaliaEntry(entryMap, 'p');
        paraphernalia.withParent(BookEntryParentModel.create(overview.getId()));
        var chalkUses = this.makeChalkUsesEntry(entryMap, 'u');
        chalkUses.withParent(BookEntryParentModel.create(overview.getId()));

        var summonFoliot = this.makeSummonFoliotEntry(entryMap, 'a');
        summonFoliot.withParent(BookEntryParentModel.create(overview.getId()));
        var summonDjinni = this.makeSummonDjinniEntry(entryMap, 'b');
        summonDjinni.withParent(BookEntryParentModel.create(summonFoliot.getId()));
        var summonWildAfrit = this.makeSummonWildAfritEntry(entryMap, 'c');
        summonWildAfrit.withParent(BookEntryParentModel.create(summonDjinni.getId()));
        var summonAfrit = this.makeSummonAfritEntry(entryMap, 'd');
        summonAfrit.withParent(BookEntryParentModel.create(summonWildAfrit.getId()));
        var summonMarid = this.makeSummonMaridEntry(entryMap, 'e');
        summonMarid.withParent(BookEntryParentModel.create(summonAfrit.getId()));
        var summonWildGreaterSpirit = this.makeSummonWildGreaterSpiritEntry(entryMap, 'f');
        summonWildGreaterSpirit.withParent(BookEntryParentModel.create(summonMarid.getId()));

        var possessFoliot = this.makePossessFoliotEntry(entryMap, 'g');
        possessFoliot.withParent(BookEntryParentModel.create(overview.getId()));
        var possessDjinni = this.makePossessDjinniEntry(entryMap, 'h');
        possessDjinni.withParent(BookEntryParentModel.create(possessFoliot.getId()));
        var possessAfrit = this.makePossessAfritEntry(entryMap, 'i');
        possessAfrit.withParent(BookEntryParentModel.create(possessDjinni.getId()));

        var craftFoliot = this.makeCraftFoliotEntry(entryMap, 'j');
        craftFoliot.withParent(BookEntryParentModel.create(overview.getId()));
        var craftDjinni = this.makeCraftDjinniEntry(entryMap, 'k');
        craftDjinni.withParent(BookEntryParentModel.create(craftFoliot.getId()));
        var craftAfrit = this.makeCraftAfritEntry(entryMap, 'l');
        craftAfrit.withParent(BookEntryParentModel.create(craftDjinni.getId()));
        var craftMarid = this.makeCraftMaridEntry(entryMap, 'm');
        craftMarid.withParent(BookEntryParentModel.create(craftAfrit.getId()));

        //add true condition to all entries to enable them by default
        overview.withCondition(BookTrueConditionModel.builder().build());
        paraphernalia.withCondition(BookTrueConditionModel.builder().build());
        chalkUses.withCondition(BookTrueConditionModel.builder().build());
        summonFoliot.withCondition(BookTrueConditionModel.builder().build());
        summonDjinni.withCondition(BookTrueConditionModel.builder().build());
        summonWildAfrit.withCondition(BookTrueConditionModel.builder().build());
        summonAfrit.withCondition(BookTrueConditionModel.builder().build());
        summonMarid.withCondition(BookTrueConditionModel.builder().build());
        summonWildGreaterSpirit.withCondition(BookTrueConditionModel.builder().build());
        possessFoliot.withCondition(BookTrueConditionModel.builder().build());
        possessDjinni.withCondition(BookTrueConditionModel.builder().build());
        possessAfrit.withCondition(BookTrueConditionModel.builder().build());
        craftFoliot.withCondition(BookTrueConditionModel.builder().build());
        craftDjinni.withCondition(BookTrueConditionModel.builder().build());
        craftAfrit.withCondition(BookTrueConditionModel.builder().build());
        craftMarid.withCondition(BookTrueConditionModel.builder().build());

        return BookCategoryModel.create(this.modLoc(this.context().categoryId()), this.context().categoryName())
                .withIcon(OccultismItems.PENTACLE.get())
                .withEntries(
                        overview,
                        paraphernalia,
                        chalkUses,

                        summonFoliot,
                        summonDjinni,
                        summonWildAfrit,
                        summonAfrit,
                        summonMarid,
                        summonWildGreaterSpirit,

                        possessFoliot,
                        possessDjinni,
                        possessAfrit,

                        craftFoliot,
                        craftDjinni,
                        craftAfrit,
                        craftMarid
                );
    }

    private BookEntryModel makePentaclesOverviewEntry(CategoryEntryMap entryMap, char icon) {
        this.context().entry("pentacles_overview");

        this.context().page("intro1");
        var intro1 = BookTextPageModel.builder()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText())
                .build();

        this.context().page("intro2");
        var intro2 = BookTextPageModel.builder()
                .withText(this.context().pageText())
                .build();

        this.context().page("intro3");
        var intro3 = BookTextPageModel.builder()
                .withText(this.context().pageText())
                .build();

        this.context().page("intro4");
        var intro4 = BookTextPageModel.builder()
                .withText(this.context().pageText())
                .build();

        //exact copy found in first ritual entry
        this.context().page("bowl_placement");
        var bowlPlacementImage = BookImagePageModel.builder()
                .withImages(this.modLoc("textures/gui/book/bowl_placement.png"))
                .withBorder(true)
                .build();

        //exact copy found in first ritual entry
        this.context().page("bowl_text");
        var bowlText = BookTextPageModel.builder()
                .withText(this.context().pageText())
                .build();

        this.context().page("summoning_pentacles");
        var summoningPentacles = BookTextPageModel.builder()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText())
                .build();

        this.context().page("infusion_pentacles");
        var infusionPentacles = BookTextPageModel.builder()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText())
                .build();

        this.context().page("possession_pentacles");
        var possessionPentacles = BookTextPageModel.builder()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText())
                .build();

        return BookEntryModel.create(this.modLoc(this.context().categoryId() + "/" + this.context().entryId()), this.context().entryName())
                .withIcon(OccultismBlocks.SPIRIT_ATTUNED_CRYSTAL.get())
                .withLocation(entryMap.get(icon))
                .withEntryBackground(0, 1)
                .withPages(
                        intro1,
                        intro2,
                        intro3,
                        intro4,
                        bowlPlacementImage,
                        bowlText,
                        summoningPentacles,
                        infusionPentacles,
                        possessionPentacles
                );
    }

    private BookEntryModel makeParaphernaliaEntry(CategoryEntryMap entryMap, char icon) {
        this.context().entry("paraphernalia");

        this.context().page("intro");
        var intro = BookTextPageModel.builder()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText())
                .build();

        this.context().page("candle");
        var candle = BookSpotlightPageModel.builder()
                .withText(this.context().pageText())
                .withItem(Ingredient.of(OccultismBlocks.CANDLE_WHITE.get()))
                .build();

        this.context().page("crystal");
        var crystal = BookSpotlightPageModel.builder()
                .withText(this.context().pageText())
                .withItem(Ingredient.of(OccultismBlocks.SPIRIT_ATTUNED_CRYSTAL.get()))
                .build();

        this.context().page("gem_recipe");
        var gemRecipe = BookSpiritFireRecipePageModel.builder()
                .withRecipeId1(this.modLoc("spirit_fire/spirit_attuned_gem"))
                .build();

        this.context().page("crystal_recipe");
        var crystalRecipe = BookCraftingRecipePageModel.builder()
                .withRecipeId1(this.modLoc("crafting/spirit_attuned_crystal"))
                .build();

        this.context().page("skeleton_skull");
        var skeletonSkull = BookSpotlightPageModel.builder()
                .withText(this.context().pageText())
                .withItem(Ingredient.of(Blocks.SKELETON_SKULL))
                .build();

        return BookEntryModel.create(this.modLoc(this.context().categoryId() + "/" + this.context().entryId()), this.context().entryName())
                .withIcon(Blocks.SKELETON_SKULL)
                .withLocation(entryMap.get(icon))
                .withPages(
                        intro,
                        candle,
                        crystal,
                        gemRecipe,
                        crystalRecipe,
                        skeletonSkull
                );
    }

    private BookEntryModel makeChalkUsesEntry(CategoryEntryMap entryMap, char icon) {
        this.context().entry("chalk_uses");

        this.context().page("intro");
        var intro = BookTextPageModel.builder()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText())
                .build();

        this.context().page("intro2");
        var intro2 = BookTextPageModel.builder()
                .withText(this.context().pageText())
                .build();

        this.context().page("white_chalk");
        var whiteChalk = BookSpotlightPageModel.builder()
                .withText(this.context().pageText())
                .withItem(Ingredient.of(OccultismItems.CHALK_WHITE.get()))
                .build();

        this.context().page("white_chalk_uses");
        var whiteChalkUses = BookTextPageModel.builder()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText())
                .build();

        this.context().page("white_chalk_uses2");
        var whiteChalkUses2 = BookTextPageModel.builder()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText())
                .build();

        this.context().page("golden_chalk");
        var goldChalk = BookSpotlightPageModel.builder()
                .withText(this.context().pageText())
                .withItem(Ingredient.of(OccultismItems.CHALK_GOLD.get()))
                .build();

        this.context().page("golden_chalk_uses");
        var goldChalkUses = BookTextPageModel.builder()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText())
                .build();

        this.context().page("purple_chalk");
        var purpleChalk = BookSpotlightPageModel.builder()
                .withText(this.context().pageText())
                .withItem(Ingredient.of(OccultismItems.CHALK_PURPLE.get()))
                .build();

        this.context().page("purple_chalk_uses");
        var purpleChalkUses = BookTextPageModel.builder()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText())
                .build();

        this.context().page("red_chalk");
        var redChalk = BookSpotlightPageModel.builder()
                .withText(this.context().pageText())
                .withItem(Ingredient.of(OccultismItems.CHALK_RED.get()))
                .build();

        this.context().page("red_chalk_uses");
        var redChalkUses = BookTextPageModel.builder()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText())
                .build();

        return BookEntryModel.create(this.modLoc(this.context().categoryId() + "/" + this.context().entryId()), this.context().entryName())
                .withIcon(OccultismItems.CHALK_PURPLE.get())
                .withLocation(entryMap.get(icon))
                .withPages(
                        intro,
                        intro2,
                        whiteChalk,
                        whiteChalkUses,
                        whiteChalkUses2,
                        goldChalk,
                        goldChalkUses,
                        purpleChalk,
                        purpleChalkUses,
                        redChalk,
                        redChalkUses
                );
    }

    private BookEntryModel makeSummonFoliotEntry(CategoryEntryMap entryMap, char icon) {
        this.context().entry("summon_foliot");

        this.context().page("intro");
        var intro = BookTextPageModel.builder()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText())
                .build();

        this.context().page("multiblock");
        var multiblock = BookMultiblockPageModel.builder()
                .withMultiblockId(this.modLoc("summon_foliot"))
                .build();

        this.context().page("uses");
        var uses = BookTextPageModel.builder()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText())
                .build();

        return BookEntryModel.create(this.modLoc(this.context().categoryId() + "/" + this.context().entryId()), this.context().entryName())
                .withIcon(OccultismItems.PENTACLE.get())
                .withLocation(entryMap.get(icon))
                .withPages(
                        intro,
                        multiblock,
                        uses
                );
    }

    private BookEntryModel makePossessFoliotEntry(CategoryEntryMap entryMap, char icon) {
        this.context().entry("possess_foliot");

        this.context().page("intro");
        var intro = BookTextPageModel.builder()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText())
                .build();

        this.context().page("multiblock");
        var multiblock = BookMultiblockPageModel.builder()
                .withMultiblockId(this.modLoc("possess_foliot"))
                .build();

        this.context().page("uses");
        var uses = BookTextPageModel.builder()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText())
                .build();

        return BookEntryModel.create(this.modLoc(this.context().categoryId() + "/" + this.context().entryId()), this.context().entryName())
                .withIcon(OccultismItems.PENTACLE.get())
                .withLocation(entryMap.get(icon))
                .withPages(
                        intro,
                        multiblock,
                        uses
                );
    }

    private BookEntryModel makeCraftFoliotEntry(CategoryEntryMap entryMap, char icon) {
        this.context().entry("craft_foliot");

        this.context().page("intro");
        var intro = BookTextPageModel.builder()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText())
                .build();

        this.context().page("multiblock");
        var multiblock = BookMultiblockPageModel.builder()
                .withMultiblockId(this.modLoc("craft_foliot"))
                .build();

        this.context().page("uses");
        var uses = BookTextPageModel.builder()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText())
                .build();

        return BookEntryModel.create(this.modLoc(this.context().categoryId() + "/" + this.context().entryId()), this.context().entryName())
                .withIcon(OccultismItems.PENTACLE.get())
                .withLocation(entryMap.get(icon))
                .withPages(
                        intro,
                        multiblock,
                        uses
                );
    }

    private BookEntryModel makeSummonDjinniEntry(CategoryEntryMap entryMap, char icon) {
        this.context().entry("summon_djinni");

        this.context().page("intro");
        var intro = BookTextPageModel.builder()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText())
                .build();

        this.context().page("multiblock");
        var multiblock = BookMultiblockPageModel.builder()
                .withMultiblockId(this.modLoc("summon_djinni"))
                .build();

        this.context().page("uses");
        var uses = BookTextPageModel.builder()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText())
                .build();

        return BookEntryModel.create(this.modLoc(this.context().categoryId() + "/" + this.context().entryId()), this.context().entryName())
                .withIcon(OccultismItems.PENTACLE.get())
                .withLocation(entryMap.get(icon))
                .withPages(
                        intro,
                        multiblock,
                        uses
                );
    }

    private BookEntryModel makePossessDjinniEntry(CategoryEntryMap entryMap, char icon) {
        this.context().entry("possess_djinni");

        this.context().page("intro");
        var intro = BookTextPageModel.builder()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText())
                .build();

        this.context().page("multiblock");
        var multiblock = BookMultiblockPageModel.builder()
                .withMultiblockId(this.modLoc("possess_djinni"))
                .build();

        this.context().page("uses");
        var uses = BookTextPageModel.builder()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText())
                .build();

        this.context().page("uses2");
        var uses2 = BookTextPageModel.builder()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText())
                .build();

        return BookEntryModel.create(this.modLoc(this.context().categoryId() + "/" + this.context().entryId()), this.context().entryName())
                .withIcon(OccultismItems.PENTACLE.get())
                .withLocation(entryMap.get(icon))
                .withPages(
                        intro,
                        multiblock,
                        uses,
                        uses2
                );
    }

    private BookEntryModel makeCraftDjinniEntry(CategoryEntryMap entryMap, char icon) {
        this.context().entry("craft_djinni");

        this.context().page("intro");
        var intro = BookTextPageModel.builder()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText())
                .build();

        this.context().page("multiblock");
        var multiblock = BookMultiblockPageModel.builder()
                .withMultiblockId(this.modLoc("craft_djinni"))
                .build();

        this.context().page("uses");
        var uses = BookTextPageModel.builder()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText())
                .build();

        return BookEntryModel.create(this.modLoc(this.context().categoryId() + "/" + this.context().entryId()), this.context().entryName())
                .withIcon(OccultismItems.PENTACLE.get())
                .withLocation(entryMap.get(icon))
                .withPages(
                        intro,
                        multiblock,
                        uses
                );
    }

    private BookEntryModel makeSummonAfritEntry(CategoryEntryMap entryMap, char icon) {
        this.context().entry("summon_afrit");

        this.context().page("intro");
        var intro = BookTextPageModel.builder()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText())
                .build();

        this.context().page("multiblock");
        var multiblock = BookMultiblockPageModel.builder()
                .withMultiblockId(this.modLoc("summon_afrit"))
                .build();

        this.context().page("uses");
        var uses = BookTextPageModel.builder()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText())
                .build();

        return BookEntryModel.create(this.modLoc(this.context().categoryId() + "/" + this.context().entryId()), this.context().entryName())
                .withIcon(OccultismItems.PENTACLE.get())
                .withLocation(entryMap.get(icon))
                .withPages(
                        intro,
                        multiblock,
                        uses
                );
    }

    private BookEntryModel makeSummonWildAfritEntry(CategoryEntryMap entryMap, char icon) {
        this.context().entry("summon_wild_afrit");

        this.context().page("intro");
        var intro = BookTextPageModel.builder()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText())
                .build();

        this.context().page("multiblock");
        var multiblock = BookMultiblockPageModel.builder()
                .withMultiblockId(this.modLoc("summon_wild_afrit"))
                .build();

        this.context().page("uses");
        var uses = BookTextPageModel.builder()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText())
                .build();

        return BookEntryModel.create(this.modLoc(this.context().categoryId() + "/" + this.context().entryId()), this.context().entryName())
                .withIcon(OccultismItems.PENTACLE.get())
                .withLocation(entryMap.get(icon))
                .withPages(
                        intro,
                        multiblock,
                        uses
                );
    }

    private BookEntryModel makePossessAfritEntry(CategoryEntryMap entryMap, char icon) {
        this.context().entry("possess_afrit");

        this.context().page("intro");
        var intro = BookTextPageModel.builder()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText())
                .build();

        this.context().page("multiblock");
        var multiblock = BookMultiblockPageModel.builder()
                .withMultiblockId(this.modLoc("possess_afrit"))
                .build();

        this.context().page("uses");
        var uses = BookTextPageModel.builder()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText())
                .build();

        return BookEntryModel.create(this.modLoc(this.context().categoryId() + "/" + this.context().entryId()), this.context().entryName())
                .withIcon(OccultismItems.PENTACLE.get())
                .withLocation(entryMap.get(icon))
                .withPages(
                        intro,
                        multiblock,
                        uses
                );
    }

    private BookEntryModel makeCraftAfritEntry(CategoryEntryMap entryMap, char icon) {
        this.context().entry("craft_afrit");

        this.context().page("intro");
        var intro = BookTextPageModel.builder()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText())
                .build();

        this.context().page("multiblock");
        var multiblock = BookMultiblockPageModel.builder()
                .withMultiblockId(this.modLoc("craft_afrit"))
                .build();

        this.context().page("uses");
        var uses = BookTextPageModel.builder()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText())
                .build();

        return BookEntryModel.create(this.modLoc(this.context().categoryId() + "/" + this.context().entryId()), this.context().entryName())
                .withIcon(OccultismItems.PENTACLE.get())
                .withLocation(entryMap.get(icon))
                .withPages(
                        intro,
                        multiblock,
                        uses
                );
    }

    private BookEntryModel makeSummonMaridEntry(CategoryEntryMap entryMap, char icon) {
        this.context().entry("summon_marid");

        this.context().page("intro");
        var intro = BookTextPageModel.builder()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText())
                .build();

        this.context().page("multiblock");
        var multiblock = BookMultiblockPageModel.builder()
                .withMultiblockId(this.modLoc("summon_marid"))
                .build();

        this.context().page("uses");
        var uses = BookTextPageModel.builder()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText())
                .build();

        return BookEntryModel.create(this.modLoc(this.context().categoryId() + "/" + this.context().entryId()), this.context().entryName())
                .withIcon(OccultismItems.PENTACLE.get())
                .withLocation(entryMap.get(icon))
                .withPages(
                        intro,
                        multiblock,
                        uses
                );
    }

    private BookEntryModel makeCraftMaridEntry(CategoryEntryMap entryMap, char icon) {
        this.context().entry("craft_marid");

        this.context().page("intro");
        var intro = BookTextPageModel.builder()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText())
                .build();

        this.context().page("multiblock");
        var multiblock = BookMultiblockPageModel.builder()
                .withMultiblockId(this.modLoc("craft_marid"))
                .build();

        this.context().page("uses");
        var uses = BookTextPageModel.builder()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText())
                .build();

        return BookEntryModel.create(this.modLoc(this.context().categoryId() + "/" + this.context().entryId()), this.context().entryName())
                .withIcon(OccultismItems.PENTACLE.get())
                .withLocation(entryMap.get(icon))
                .withPages(
                        intro,
                        multiblock,
                        uses
                );
    }

    private BookEntryModel makeSummonWildGreaterSpiritEntry(CategoryEntryMap entryMap, char icon) {
        this.context().entry("summon_wild_greater_spirit");

        this.context().page("intro");
        var intro = BookTextPageModel.builder()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText())
                .build();

        this.context().page("multiblock");
        var multiblock = BookMultiblockPageModel.builder()
                .withMultiblockId(this.modLoc("summon_wild_greater_spirit"))
                .build();

        this.context().page("uses");
        var uses = BookTextPageModel.builder()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText())
                .build();

        return BookEntryModel.create(this.modLoc(this.context().categoryId() + "/" + this.context().entryId()), this.context().entryName())
                .withIcon(OccultismItems.PENTACLE.get())
                .withLocation(entryMap.get(icon))
                .withPages(
                        intro,
                        multiblock,
                        uses
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
        summoning.withParent(BookEntryParentModel.create(sacrifice.getId()));
        var possession = this.makePossessionRitualsSubcategoryEntry(entryMap, 'p');
        possession.withParent(BookEntryParentModel.create(sacrifice.getId()));
        var crafting = this.makeCraftingRitualsSubcategoryEntry(entryMap, 'c');
        crafting.withParent(BookEntryParentModel.create(sacrifice.getId()));
        var familiars = this.makeFamiliarRitualsSubcategoryEntry(entryMap, 'f');
        familiars.withParent(BookEntryParentModel.create(sacrifice.getId()));

        //enable all entries by default
        itemUse.withCondition(BookTrueConditionModel.builder().build());
        sacrifice.withCondition(BookTrueConditionModel.builder().build());
        summoning.withCondition(BookTrueConditionModel.builder().build());
        possession.withCondition(BookTrueConditionModel.builder().build());
        crafting.withCondition(BookTrueConditionModel.builder().build());
        familiars.withCondition(BookTrueConditionModel.builder().build());

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
        var intro = BookTextPageModel.builder()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText())
                .build();

        this.context().page("steps");
        var steps = BookTextPageModel.builder()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText())
                .build();

        this.context().page("additional_requirements");
        var additional_requirements = BookTextPageModel.builder()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText())
                .build();

        return BookEntryModel.create(this.modLoc(this.context().categoryId() + "/" + this.context().entryId()), this.context().entryName())
                .withIcon(this.modLoc("textures/gui/book/robe.png"))
                .withLocation(entryMap.get(icon))
                .withEntryBackground(0, 1)
                .withPages(
                        intro,
                        steps,
                        additional_requirements
                );
    }

    private BookEntryModel makeSacrificeEntry(CategoryEntryMap entryMap, char icon) {
        this.context().entry("sacrifice");

        this.context().page("intro");
        var intro = BookTextPageModel.builder()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText())
                .build();

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
        var intro = BookTextPageModel.builder()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText())
                .build();

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
                .withEntryBackground(1, 1) //silver background and wavey entry shape
                .withLocation(entryMap.get(icon));
    }

    private BookEntryModel makePossessionRitualsSubcategoryEntry(CategoryEntryMap entryMap, char icon) {
        this.context().entry("possession_rituals");

        return BookEntryModel.create(this.modLoc(this.context().categoryId() + "/" + this.context().entryId()), this.context().entryName())
                .withIcon(this.modLoc("textures/gui/book/possession.png"))
                .withCategoryToOpen(this.modLoc("possession_rituals"))
                .withEntryBackground(1, 1) //silver background and wavey entry shape
                .withLocation(entryMap.get(icon));
    }

    private BookEntryModel makeCraftingRitualsSubcategoryEntry(CategoryEntryMap entryMap, char icon) {
        this.context().entry("crafting_rituals");

        return BookEntryModel.create(this.modLoc(this.context().categoryId() + "/" + this.context().entryId()), this.context().entryName())
                .withIcon(this.modLoc("textures/gui/book/infusion.png"))
                .withCategoryToOpen(this.modLoc("crafting_rituals"))
                .withEntryBackground(1, 1) //silver background and wavey entry shape
                .withLocation(entryMap.get(icon));
    }

    private BookEntryModel makeFamiliarRitualsSubcategoryEntry(CategoryEntryMap entryMap, char icon) {
        this.context().entry("familiar_rituals");

        return BookEntryModel.create(this.modLoc(this.context().categoryId() + "/" + this.context().entryId()), this.context().entryName())
                .withIcon(this.modLoc("textures/gui/book/parrot.png"))
                .withCategoryToOpen(this.modLoc("familiar_rituals"))
                .withEntryBackground(1, 1) //silver background and wavey entry shape
                .withLocation(entryMap.get(icon));
    }

    private BookEntryModel makeReturnToRitualsEntry(CategoryEntryMap entryMap, char icon) {
        this.context().entry("return_to_rituals");

        return BookEntryModel.create(this.modLoc(this.context().categoryId() + "/" + this.context().entryId()), this.context().entryName())
                .withIcon(this.modLoc("textures/gui/book/robe.png"))
                .withCategoryToOpen(this.modLoc("rituals"))
                .withEntryBackground(1, 2)
                .withLocation(entryMap.get(icon));
    }
    //endregion

    //region Summoning Rituals
    private BookCategoryModel makeSummoningRitualsSubcategory() {
        this.context().category("summoning_rituals");

        var entryMap = ModonomiconAPI.get().getEntryMap();
        entryMap.setMap(
                "___________b___l______",
                "______________________",
                "_________c_d_h_k______",
                "______________________",
                "___r_o________________",
                "______________________",
                "_________1_e_i_a_m___",
                "______________________",
                "_________2_f_j________",
                "______________________",
                "_________3_g__________",
                "______________________",
                "_________4____________"
        );

        var overview = this.makeSummoningRitualsOverviewEntry(entryMap, 'o');
        var returnToRituals = this.makeReturnToRitualsEntry(entryMap, 'r');
        returnToRituals.withParent(BookEntryParentModel.create(overview.getId()));
        returnToRituals.withCondition(BookTrueConditionModel.builder().build());

        var summonT1Crusher = this.makeSummonCrusherT1Entry(entryMap, '1');
        summonT1Crusher.withParent(BookEntryParentModel.create(overview.getId()));
        var summonT2Crusher = this.makeSummonCrusherT2Entry(entryMap, '2');
        summonT2Crusher.withParent(BookEntryParentModel.create(summonT1Crusher.getId()));
        var summonT3Crusher = this.makeSummonCrusherT3Entry(entryMap, '3');
        summonT3Crusher.withParent(BookEntryParentModel.create(summonT2Crusher.getId()));
        var summonT4Crusher = this.makeSummonCrusherT4Entry(entryMap, '4');
        summonT4Crusher.withParent(BookEntryParentModel.create(summonT3Crusher.getId()));

        var summonLumberjack = this.makeSummonLumberjackEntry(entryMap, 'c');
        summonLumberjack.withParent(BookEntryParentModel.create(overview.getId()));

        var summonTransportItems = this.makeSummonTransportItemsEntry(entryMap, 'd');
        summonTransportItems.withParent(BookEntryParentModel.create(overview.getId()));
        var summonCleaner = this.makeSummonCleanerEntry(entryMap, 'b');
        summonCleaner.withParent(BookEntryParentModel.create(summonTransportItems.getId()));
        var summonManageMachine = this.makeSummonManageMachineEntry(entryMap, 'h');
        summonManageMachine.withParent(BookEntryParentModel.create(summonTransportItems.getId()));

        var tradeSpirits = this.makeTradeSpiritsEntry(entryMap, 'e');
        tradeSpirits.withParent(BookEntryParentModel.create(overview.getId()));
        var summonOtherworldSaplingTrader = this.makeSummonOtherworldSaplingTraderEntry(entryMap, 'f');
        summonOtherworldSaplingTrader.withParent(BookEntryParentModel.create(tradeSpirits.getId()));
        var summonOtherstoneTrader = this.makeSummonOtherstoneTraderEntry(entryMap, 'g');
        summonOtherstoneTrader.withParent(BookEntryParentModel.create(summonOtherworldSaplingTrader.getId()));

        var summonWildParrot = this.makeSummonWildParrotEntry(entryMap, 'i');
        summonWildParrot.withParent(BookEntryParentModel.create(overview.getId()));
        var summonWildOtherworldBird = this.makeSummonWildOtherworldBirdEntry(entryMap, 'j');
        summonWildOtherworldBird.withParent(BookEntryParentModel.create(summonWildParrot.getId()));

        var weatherMagic = this.makeWeatherMagicEntry(entryMap, 'k');
        weatherMagic.withParent(BookEntryParentModel.create(overview.getId()));
        var timeMagic = this.makeTimeMagicEntry(entryMap, 'l');
        timeMagic.withParent(BookEntryParentModel.create(weatherMagic.getId()));

        var afritEssence = this.makeAfritEssenceEntry(entryMap, 'a');
        afritEssence.withParent(BookEntryParentModel.create(overview.getId()));

        var witherSkull = this.makeWitherSkullEntry(entryMap, 'm');
        witherSkull.withParent(BookEntryParentModel.create(overview.getId()));

        //add true condition to all entries to enable them by default
        overview.withCondition(BookTrueConditionModel.builder().build());
        summonT1Crusher.withCondition(BookTrueConditionModel.builder().build());
        summonT2Crusher.withCondition(BookTrueConditionModel.builder().build());
        summonT3Crusher.withCondition(BookTrueConditionModel.builder().build());
        summonT4Crusher.withCondition(BookTrueConditionModel.builder().build());
        summonLumberjack.withCondition(BookTrueConditionModel.builder().build());
        summonTransportItems.withCondition(BookTrueConditionModel.builder().build());
        summonCleaner.withCondition(BookTrueConditionModel.builder().build());
        summonManageMachine.withCondition(BookTrueConditionModel.builder().build());
        tradeSpirits.withCondition(BookTrueConditionModel.builder().build());
        summonOtherworldSaplingTrader.withCondition(BookTrueConditionModel.builder().build());
        summonOtherstoneTrader.withCondition(BookTrueConditionModel.builder().build());
        summonWildParrot.withCondition(BookTrueConditionModel.builder().build());
        summonWildOtherworldBird.withCondition(BookTrueConditionModel.builder().build());
        weatherMagic.withCondition(BookTrueConditionModel.builder().build());
        timeMagic.withCondition(BookTrueConditionModel.builder().build());
        afritEssence.withCondition(BookTrueConditionModel.builder().build());
        witherSkull.withCondition(BookTrueConditionModel.builder().build());

        return BookCategoryModel.create(this.modLoc(this.context().categoryId()), this.context().categoryName())
                .withIcon(this.modLoc("textures/gui/book/summoning.png"))
                .withShowCategoryButton(true)
                .withEntries(
                        overview,
                        returnToRituals,
                        afritEssence,
                        summonCleaner,
                        summonT1Crusher,
                        summonT2Crusher,
                        summonT3Crusher,
                        summonT4Crusher,
                        summonLumberjack,
                        summonManageMachine,
                        summonTransportItems,
                        tradeSpirits,
                        summonOtherstoneTrader,
                        summonOtherworldSaplingTrader,
                        summonWildOtherworldBird,
                        summonWildParrot,
                        timeMagic,
                        weatherMagic,
                        witherSkull,
                        afritEssence
                );
    }

    private BookEntryModel makeSummoningRitualsOverviewEntry(CategoryEntryMap entryMap, char icon) {
        this.context().entry("overview");

        this.context().page("intro");
        var intro = BookTextPageModel.builder()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText())
                .build();

        return BookEntryModel.create(this.modLoc(this.context().categoryId() + "/" + this.context().entryId()), this.context().entryName())
                .withIcon(this.modLoc("textures/gui/book/summoning.png"))
                .withLocation(entryMap.get(icon))
                .withEntryBackground(0, 1)
                .withPages(
                        intro
                );
    }

    private BookEntryModel makeAfritEssenceEntry(CategoryEntryMap entryMap, char icon) {
        this.context().entry("afrit_essence");

        this.context().page("intro");
        var intro = BookTextPageModel.builder()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText())
                .build();

        this.context().page("ritual");
        var ritual = BookRitualRecipePageModel.builder()
                .withRecipeId1(this.modLoc("ritual/summon_wild_afrit"))
                .build();

        return BookEntryModel.create(this.modLoc(this.context().categoryId() + "/" + this.context().entryId()), this.context().entryName())
                .withIcon(OccultismItems.AFRIT_ESSENCE.get())
                .withLocation(entryMap.get(icon))
                .withPages(
                        intro,
                        ritual
                );
    }

    private BookEntryModel makeSummonCrusherT1Entry(CategoryEntryMap entryMap, char icon) {
        this.context().entry("summon_crusher_t1");
        this.lang.add(this.context().entryName(), "Summon Foliot Crusher");

        this.context().page("about_crushers");
        var aboutCrushers = BookTextPageModel.builder()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText())
                .build();
        this.lang.add(this.context().pageTitle(), "Crusher Spirits");
        this.lang.add(this.context().pageText(),
                """
                        Crusher spirits are summoned to crush ores into dusts, effectively multiplying the metal output. They will pick up appropriate ores and drop the resulting dusts into the world. A purple particle effect and a crushing sound indicate the crusher is at work.
                          """);

        this.context().page("automation");
        var automation = BookTextPageModel.builder()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText())
                .build();
        this.lang.add(this.context().pageTitle(), "Automation");
        this.lang.add(this.context().pageText(),
                """
                        To ease automation, try summoning a [Transporter Spirit](entry://occultism:dictionary_of_spirits/summoning_rituals/summon_transport_items)
                        to place items from chests in the crusher's inventory, and a [Janitor Spirit](entry://occultism:dictionary_of_spirits/summoning_rituals/summon_cleaner) to collect the processed items.
                         """);

        this.context().page("intro");
        var intro = BookTextPageModel.builder()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText())
                .build();
        this.lang.add(this.context().pageTitle(), "Foliot Crusher");
        this.lang.add(this.context().pageText(),
                """
                        The foliot crusher is the most basic crusher spirit.
                        \\
                        \\
                        It will crush **one** ore into **two** corresponding dusts.
                         """);

        this.context().page("ritual");
        var ritual = BookRitualRecipePageModel.builder()
                .withRecipeId1(this.modLoc("ritual/summon_foliot_crusher"))
                .build();
        //no text

        return BookEntryModel.create(this.modLoc(this.context().categoryId() + "/" + this.context().entryId()), this.context().entryName())
                .withIcon(OccultismItems.COPPER_DUST.get())
                .withLocation(entryMap.get(icon))
                .withPages(
                        aboutCrushers,
                        automation,
                        intro,
                        ritual
                );
    }

    private BookEntryModel makeSummonCrusherT2Entry(CategoryEntryMap entryMap, char icon) {
        this.context().entry("summon_crusher_t2");

        this.context().page("intro");
        var intro = BookTextPageModel.builder()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText())
                .build();

        this.context().page("ritual");
        var ritual = BookRitualRecipePageModel.builder()
                .withRecipeId1(this.modLoc("ritual/summon_djinni_crusher"))
                .build();

        return BookEntryModel.create(this.modLoc(this.context().categoryId() + "/" + this.context().entryId()), this.context().entryName())
                .withIcon(OccultismItems.IRON_DUST.get())
                .withLocation(entryMap.get(icon))
                .withPages(
                        intro,
                        ritual
                );
    }

    private BookEntryModel makeSummonCrusherT3Entry(CategoryEntryMap entryMap, char icon) {
        this.context().entry("summon_crusher_t3");

        this.context().page("intro");
        var intro = BookTextPageModel.builder()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText())
                .build();

        this.context().page("ritual");
        var ritual = BookRitualRecipePageModel.builder()
                .withRecipeId1(this.modLoc("ritual/summon_afrit_crusher"))
                .build();

        return BookEntryModel.create(this.modLoc(this.context().categoryId() + "/" + this.context().entryId()), this.context().entryName())
                .withIcon(OccultismItems.SILVER_DUST.get())
                .withLocation(entryMap.get(icon))
                .withPages(
                        intro,
                        ritual
                );
    }

    private BookEntryModel makeSummonCrusherT4Entry(CategoryEntryMap entryMap, char icon) {
        this.context().entry("summon_crusher_t4");

        this.context().page("intro");
        var intro = BookTextPageModel.builder()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText())
                .build();

        this.context().page("ritual");
        var ritual = BookRitualRecipePageModel.builder()
                .withRecipeId1(this.modLoc("ritual/summon_marid_crusher"))
                .build();

        return BookEntryModel.create(this.modLoc(this.context().categoryId() + "/" + this.context().entryId()), this.context().entryName())
                .withIcon(OccultismItems.GOLD_DUST.get())
                .withLocation(entryMap.get(icon))
                .withPages(
                        intro,
                        ritual
                );
    }

    private BookEntryModel makeSummonLumberjackEntry(CategoryEntryMap entryMap, char icon) {
        this.context().entry("summon_lumberjack");
        this.lang.add(this.context().entryName(), "Summon Foliot Lumberjack");

        this.context().page("intro");
        var intro = BookTextPageModel.builder()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText())
                .build();
        this.lang.add(this.context().pageTitle(), "Foliot Lumberjack");
        this.lang.add(this.context().pageText(),
                """
                        The lumberjack will harvest trees in it's working area. If a deposit location is set it will collect the dropped items into the specified chest, and re-plant saplings.
                          """);

        this.context().page("prerequisites");
        var prerequisites = BookTextPageModel.builder()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText())
                .build();
        this.lang.add(this.context().pageTitle(), "Prerequisites");
        this.lang.add(this.context().pageText(),
                """
                        Summoning the lumberjack requires a [Stable Otherworld Sapling](item://occultism:otherworld_sapling). You can obtain it by summoning an [Otherworld Sapling Trader](entry://summoning_rituals/summon_otherworld_sapling_trader). 
                          """
        );


        this.context().page("ritual");
        var ritual = BookRitualRecipePageModel.builder()
                .withRecipeId1(this.modLoc("ritual/summon_foliot_lumberjack"))
                .build();
        //no text

        this.context().page("book_of_calling");
        var bookOfCalling = BookCraftingRecipePageModel.builder()
                .withRecipeId1(this.modLoc("crafting/book_of_calling_foliot_lumberjack"))
                .withText(this.context().pageText())
                .build();
        this.lang.add(this.context().pageText(),
                """
                        If you lose the book of calling, you can craft a new one.
                        [#](%1$s)Shift-right-click[#]() the spirit with the crafted book to assign it.
                        """.formatted(COLOR_PURPLE));

        this.context().page("usage");
        var usage = BookTextPageModel.builder()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText())
                .build();
        this.lang.add(this.context().pageTitle(), "Usage");
        this.lang.add(this.context().pageText(),
                """
                        Use the book of calling to set the work area and deposit location of the lumberjack.
                        \\
                        \\
                        See [Books of Calling](entry://getting_started/books_of_calling) for more information.
                           """);

        this.context().page("usage2");
        var usage2 = BookTextPageModel.builder()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText())
                .build();
        this.lang.add(this.context().pageTitle(), "Lazy Lumberjack?");
        this.lang.add(this.context().pageText(),
                """
                        The spirit might pause for a few minutes after clearing his work area, even if trees have regrown since. This is a performance-saving measure and not a bug, he will continue on his own.
                        \\
                        \\
                        Set the work area again to make him continue work immediately.
                          """);

        return BookEntryModel.create(this.modLoc(this.context().categoryId() + "/" + this.context().entryId()), this.context().entryName())
                .withIcon(OccultismItems.BRUSH.get())
                .withIcon(Items.IRON_AXE)
                .withLocation(entryMap.get(icon))
                .withPages(
                        intro,
                        prerequisites,
                        ritual,
                        bookOfCalling,
                        usage,
                        usage2
                );
    }

    private BookEntryModel makeSummonTransportItemsEntry(CategoryEntryMap entryMap, char icon) {
        this.context().entry("summon_transport_items");
        this.lang.add(this.context().entryName(), "Summon Foliot Transporter");

        this.context().page("intro");
        var intro = BookTextPageModel.builder()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText())
                .build();
        this.lang.add(this.context().pageTitle(), "Foliot Transporter");
        this.lang.add(this.context().pageText(),
                """
                        The transporter is useful in that you don't need a train of hoppers transporting stuff, and can use any inventory to take from and deposit.
                        \\
                        \\
                        To make it take from an inventory simply sneak and interact with it's book of calling on the inventory you want.
                               """);

        this.context().page("intro2");
        var intro2 = BookTextPageModel.builder()
                .withText(this.context().pageText())
                .build();
        this.lang.add(this.context().pageText(),
                """
                        You can also dictate which inventory it deposits to in the same way.
                        \\
                        The transporter will move all items it can access from one inventory to another, including machines. It can also deposit into the inventories of other spirits. By setting the extract and insert side they can be used to automate various transport tasks.
                           """);

        this.context().page("spirit_inventories");
        var spirit_inventories = BookTextPageModel.builder()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText())
                .build();
        this.lang.add(this.context().pageTitle(), "Spirit Inventories");
        this.lang.add(this.context().pageText(),
                """
                        The Transporter can also interact with the inventories of other spirits. This is especially useful to automatically supply a [Crusher spirit](entry://summoning_rituals/summon_crusher_t1) with items to crush.
                           """);

        this.context().page("item_filters");
        var itemFilters = BookTextPageModel.builder()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText())
                .build();
        this.lang.add(this.context().pageTitle(), "Item Filters");
        this.lang.add(this.context().pageText(),
                """
                        By default the Transporter is in "Whitelist" mode and will not move anything. Shift-click the transporter to open the config UI. You can then add items to the filter list to make it move only those items, or set it to "Blacklist" to move everything *except* the filtered items. You can also enter a tag in the text field below to filter by tag.
                           """);

        this.context().page("ritual");
        var ritual = BookRitualRecipePageModel.builder()
                .withRecipeId1(this.modLoc("ritual/summon_foliot_transport_items"))
                .build();
        //no text

        this.context().page("book_of_calling");
        var bookOfCalling = BookCraftingRecipePageModel.builder()
                .withRecipeId1(this.modLoc("crafting/book_of_calling_foliot_transport_items"))
                .withText(this.context().pageText())
                .build();
        this.lang.add(this.context().pageText(),
                """
                        If you lose the book of calling, you can craft a new one.
                        [#](%1$s)Shift-right-click[#]() the spirit with the crafted book to assign it.
                        """.formatted(COLOR_PURPLE));

        return BookEntryModel.create(this.modLoc(this.context().categoryId() + "/" + this.context().entryId()), this.context().entryName())
                .withIcon(Items.MINECART)
                .withLocation(entryMap.get(icon))
                .withPages(
                        intro,
                        intro2,
                        spirit_inventories,
                        itemFilters,
                        ritual,
                        bookOfCalling
                );
    }

    private BookEntryModel makeSummonCleanerEntry(CategoryEntryMap entryMap, char icon) {
        this.context().entry("summon_cleaner");
        this.lang.add(this.context().entryName(), "Summon Foliot Janitor");

        this.context().page("intro");
        var intro = BookTextPageModel.builder()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText())
                .build();
        this.lang.add(this.context().pageTitle(), "Foliot Janitor");
        this.lang.add(this.context().pageText(),
                """
                        The janitor will pick up dropped items and deposit them into a target inventory. You can configure an allow/block list to specify which items to pick up or ignore. **Warning**: By default it is set to "allow" mode, so it will only pick up items you specify in the allow list.
                        You can use tags to handle whole groups of items.
                          """);

        this.context().page("intro2");
        var intro2 = BookTextPageModel.builder()
                .withText(this.context().pageText())
                .build();
        this.lang.add(this.context().pageText(),
                """
                        To bind the janitor to an inventory simply sneak and interact with the janitor book of calling on that inventory. You can also interact with a block while holding the janitor book of calling to have it deposit items there. You can also have it wander around a select area by pulling up that interface. To configure an allow/block list sneak and interact with the janitor.
                          """);

        this.context().page("tip");
        var tip = BookTextPageModel.builder()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText())
                .build();
        this.lang.add(this.context().pageTitle(), "Pro tip");
        this.lang.add(this.context().pageText(),
                """
                        The Janitor will pick up crushed items from a [Crusher spirit](entry://summoning_rituals/summon_crusher_t1) and deposit them into a chest.
                        \\
                        \\
                        Combine that with a [Transporter Spirit](entry://summoning_rituals/summon_transport_items) to automate the whole process.
                           """);

        this.context().page("ritual");
        var ritual = BookRitualRecipePageModel.builder()
                .withRecipeId1(this.modLoc("ritual/summon_foliot_cleaner"))
                .build();
        //no text

        this.context().page("book_of_calling");
        var bookOfCalling = BookCraftingRecipePageModel.builder()
                .withRecipeId1(this.modLoc("crafting/book_of_calling_foliot_cleaner"))
                .withText(this.context().pageText())
                .build();
        this.lang.add(this.context().pageText(),
                """
                        If you lose the book of calling, you can craft a new one.
                        [#](%1$s)Shift-right-click[#]() the spirit with the crafted book to assign it.
                        """.formatted(COLOR_PURPLE));

        return BookEntryModel.create(this.modLoc(this.context().categoryId() + "/" + this.context().entryId()), this.context().entryName())
                .withIcon(OccultismItems.BRUSH.get())
                .withLocation(entryMap.get(icon))
                .withPages(
                        intro,
                        intro2,
                        tip,
                        ritual,
                        bookOfCalling
                );
    }

    private BookEntryModel makeSummonManageMachineEntry(CategoryEntryMap entryMap, char icon) {
        this.context().entry("summon_manage_machine");

        this.context().page("intro");
        var intro = BookTextPageModel.builder()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText())
                .build();

        this.context().page("tutorial");
        var tutorial = BookTextPageModel.builder()
                .withText(this.context().pageText())
                .build();

        this.context().page("tutorial2");
        var tutorial2 = BookTextPageModel.builder()
                .withText(this.context().pageText())
                .build();

        this.context().page("ritual");
        var ritual = BookRitualRecipePageModel.builder()
                .withRecipeId1(this.modLoc("ritual/summon_djinni_manage_machine"))
                .build();

        this.context().page("book_of_calling");
        var bookOfCalling = BookCraftingRecipePageModel.builder()
                .withRecipeId1(this.modLoc("crafting/book_of_calling_djinni_manage_machine"))
                .withText(this.context().pageText())
                .build();

        return BookEntryModel.create(this.modLoc(this.context().categoryId() + "/" + this.context().entryId()), this.context().entryName())
                .withIcon(Items.LEVER)
                .withLocation(entryMap.get(icon))
                .withPages(
                        intro,
                        tutorial,
                        tutorial2,
                        ritual,
                        bookOfCalling
                );
    }

    private BookEntryModel makeTradeSpiritsEntry(CategoryEntryMap entryMap, char icon) {
        this.context().entry("trade_spirits");

        this.context().page("intro");
        var intro = BookTextPageModel.builder()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText())
                .build();

        this.context().page("intro2");
        var intro2 = BookTextPageModel.builder()
                .withText(this.context().pageText())
                .build();

        return BookEntryModel.create(this.modLoc(this.context().categoryId() + "/" + this.context().entryId()), this.context().entryName())
                .withIcon(this.modLoc("textures/gui/book/cash.png"))
                .withLocation(entryMap.get(icon))
                .withPages(
                        intro,
                        intro2
                );
    }

    private BookEntryModel makeSummonOtherstoneTraderEntry(CategoryEntryMap entryMap, char icon) {
        this.context().entry("summon_otherstone_trader");

        this.context().page("intro");
        var intro = BookTextPageModel.builder()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText())
                .build();

        this.context().page("trade");
        var trade = BookSpiritTradeRecipePageModel.builder()
                .withRecipeId1(this.modLoc("spirit_trade/stone_to_otherstone"))
                .build();

        this.context().page("ritual");
        var ritual = BookRitualRecipePageModel.builder()
                .withRecipeId1(this.modLoc("ritual/summon_foliot_otherstone_trader"))
                .build();

        return BookEntryModel.create(this.modLoc(this.context().categoryId() + "/" + this.context().entryId()), this.context().entryName())
                .withIcon(OccultismBlocks.OTHERSTONE.get())
                .withLocation(entryMap.get(icon))
                .withPages(
                        intro,
                        trade,
                        ritual
                );
    }

    private BookEntryModel makeSummonOtherworldSaplingTraderEntry(CategoryEntryMap entryMap, char icon) {
        this.context().entry("summon_otherworld_sapling_trader");

        this.context().page("intro");
        var intro = BookTextPageModel.builder()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText())
                .build();

        this.context().page("trade");
        var trade = BookSpiritTradeRecipePageModel.builder()
                .withRecipeId1(this.modLoc("spirit_trade/otherworld_sapling"))
                .withText(this.context().pageText())
                .build();
        this.lang().add(this.context.pageText(), """
                To trade, drop an your offered item next to the trader, he will pick it up and drop the exchanged item.
                """);

        this.context().page("ritual");
        var ritual = BookRitualRecipePageModel.builder()
                .withRecipeId1(this.modLoc("ritual/summon_foliot_sapling_trader"))
                .build();

        return BookEntryModel.create(this.modLoc(this.context().categoryId() + "/" + this.context().entryId()), this.context().entryName())
                .withIcon(OccultismBlocks.OTHERWORLD_SAPLING.get())
                .withLocation(entryMap.get(icon))
                .withPages(
                        intro,
                        trade,
                        ritual
                );
    }

    private BookEntryModel makeSummonWildParrotEntry(CategoryEntryMap entryMap, char icon) {
        this.context().entry("summon_wild_parrot");

        this.context().page("entity");
        var entity = BookEntityPageModel.builder()
                .withEntityId("minecraft:parrot")
                .withText(this.context().pageText())
                .build();

        this.context().page("ritual");
        var ritual = BookRitualRecipePageModel.builder()
                .withRecipeId1(this.modLoc("ritual/summon_wild_parrot"))
                .build();

        this.context().page("description");
        var description = BookTextPageModel.builder()
                .withText(this.context().pageText())
                .build();

        this.context().page("description2");
        var description2 = BookTextPageModel.builder()
                .withText(this.context().pageText())
                .build();


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

    private BookEntryModel makeSummonWildOtherworldBirdEntry(CategoryEntryMap entryMap, char icon) {
        this.context().entry("summon_wild_otherworld_bird");

        this.context().page("entity");
        var entity = BookEntityPageModel.builder()
                .withEntityId("occultism:otherworld_bird")
                .withText(this.context().pageText())
                .build();

        this.context().page("ritual");
        var ritual = BookRitualRecipePageModel.builder()
                .withRecipeId1(this.modLoc("ritual/summon_wild_otherworld_bird"))
                .build();

        this.context().page("description");
        var description = BookTextPageModel.builder()
                .withText(this.context().pageText())
                .build();

        return BookEntryModel.create(this.modLoc(this.context().categoryId() + "/" + this.context().entryId()), this.context().entryName())
                .withIcon(this.modLoc("textures/gui/book/otherworld_bird.png"))
                .withLocation(entryMap.get(icon))
                .withPages(
                        entity,
                        ritual,
                        description
                );
    }

    private BookEntryModel makeWeatherMagicEntry(CategoryEntryMap entryMap, char icon) {
        this.context().entry("weather_magic");

        this.context().page("intro");
        var intro = BookTextPageModel.builder()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText())
                .build();

        this.context().page("ritual_clear");
        var ritualClear = BookRitualRecipePageModel.builder()
                .withRecipeId1(this.modLoc("ritual/summon_djinni_clear_weather"))
                .withAnchor("clear")
                .build();

        this.context().page("ritual_rain");
        var ritualRain = BookRitualRecipePageModel.builder()
                .withRecipeId1(this.modLoc("ritual/summon_afrit_rain_weather"))
                .withAnchor("rain")
                .build();

        this.context().page("ritual_thunder");
        var ritualThunder = BookRitualRecipePageModel.builder()
                .withRecipeId1(this.modLoc("ritual/summon_afrit_thunder_weather"))
                .withAnchor("thunder")
                .build();

        return BookEntryModel.create(this.modLoc(this.context().categoryId() + "/" + this.context().entryId()), this.context().entryName())
                .withIcon(Items.WHEAT)
                .withLocation(entryMap.get(icon))
                .withPages(
                        intro,
                        ritualClear,
                        ritualRain,
                        ritualThunder
                );
    }

    private BookEntryModel makeTimeMagicEntry(CategoryEntryMap entryMap, char icon) {
        this.context().entry("time_magic");

        this.context().page("intro");
        var intro = BookTextPageModel.builder()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText())
                .build();

        this.context().page("ritual_day");
        var ritualDay = BookRitualRecipePageModel.builder()
                .withRecipeId1(this.modLoc("ritual/summon_djinni_day_time"))
                .withAnchor("day")
                .build();

        this.context().page("ritual_night");
        var ritualNight = BookRitualRecipePageModel.builder()
                .withRecipeId1(this.modLoc("ritual/summon_djinni_night_time"))
                .withAnchor("night")
                .build();

        return BookEntryModel.create(this.modLoc(this.context().categoryId() + "/" + this.context().entryId()), this.context().entryName())
                .withIcon(Items.CLOCK)
                .withLocation(entryMap.get(icon))
                .withPages(
                        intro,
                        ritualDay,
                        ritualNight
                );
    }

    private BookEntryModel makeWitherSkullEntry(CategoryEntryMap entryMap, char icon) {
        this.context().entry("wither_skull");

        this.context().page("intro");
        var intro = BookTextPageModel.builder()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText())
                .build();

        this.context().page("ritual");
        var ritual = BookRitualRecipePageModel.builder()
                .withRecipeId1(this.modLoc("ritual/summon_wild_hunt"))
                .build();

        return BookEntryModel.create(this.modLoc(this.context().categoryId() + "/" + this.context().entryId()), this.context().entryName())
                .withIcon(Items.WITHER_SKELETON_SKULL)
                .withLocation(entryMap.get(icon))
                .withPages(
                        intro,
                        ritual
                );
    }
    //endregion

    //region Crafting Rituals
    private BookCategoryModel makeCraftingRitualsSubcategory() {
        this.context().category("crafting_rituals");

        var entryMap = ModonomiconAPI.get().getEntryMap();
        entryMap.setMap(
                "___________________________",
                "_______b_e_x_p_q___________",
                "___________________________",
                "_______d_h_c_______________",
                "___________________________",
                "___9_0_____________________",
                "___________________________",
                "_______f_z_a__g____________",
                "___________________________",
                "___________n_m_o___________",
                "___________________________",
                "___________i_j_k_l_________",
                "___________________________"
        );

        var overview = this.makeCraftingRitualsOverviewEntry(entryMap, '0');
        var returnToRituals = this.makeReturnToRitualsEntry(entryMap, '9');
        returnToRituals.withParent(BookEntryParentModel.create(overview.getId()));
        returnToRituals.withCondition(BookTrueConditionModel.builder().build());

        var craftInfusedPickaxe = this.makeCraftInfusedPickaxeEntry(entryMap, 'd');
        craftInfusedPickaxe.withParent(BookEntryParentModel.create(overview.getId()));
        var craftDimensionalMineshaft = this.makeCraftDimensionalMineshaftEntry(entryMap, 'b');
        craftDimensionalMineshaft.withParent(BookEntryParentModel.create(craftInfusedPickaxe.getId()));
        var craftFoliotMiner = this.makeCraftFoliotMinerEntry(entryMap, 'e');
        craftFoliotMiner.withParent(BookEntryParentModel.create(craftDimensionalMineshaft.getId()));
        var craftDjinniMiner = this.makeCraftDjinniMinerEntry(entryMap, 'x');
        craftDjinniMiner.withParent(BookEntryParentModel.create(craftFoliotMiner.getId()));
        var craftAfritMiner = this.makeCraftAfritMinerEntry(entryMap, 'p');
        craftAfritMiner.withParent(BookEntryParentModel.create(craftDjinniMiner.getId()));
        var craftMaridMiner = this.makeCraftMaridMinerEntry(entryMap, 'q');
        craftMaridMiner.withParent(BookEntryParentModel.create(craftAfritMiner.getId()));

        var craftStorageSystem = this.makeCraftStorageSystemEntry(entryMap, 'z');
        craftStorageSystem.withParent(BookEntryParentModel.create(overview.getId()));

        var craftDimensionalMatrix = this.makeCraftDimensionalMatrixEntry(entryMap, 'a');
        craftDimensionalMatrix.withParent(BookEntryParentModel.create(craftStorageSystem.getId()));
        var craftStorageControllerBase = this.makeCraftStorageControllerBaseEntry(entryMap, 'n');
        craftStorageControllerBase.withParent(BookEntryParentModel.create(craftDimensionalMatrix.getId()));
        var craftStabilizerTier1 = this.makeCraftStabilizerTier1Entry(entryMap, 'i');
        craftStabilizerTier1.withParent(BookEntryParentModel.create(craftStorageControllerBase.getId()));
        var craftStabilizerTier2 = this.makeCraftStabilizerTier2Entry(entryMap, 'j');
        craftStabilizerTier2.withParent(BookEntryParentModel.create(craftStabilizerTier1.getId()));
        var craftStabilizerTier3 = this.makeCraftStabilizerTier3Entry(entryMap, 'k');
        craftStabilizerTier3.withParent(BookEntryParentModel.create(craftStabilizerTier2.getId()));
        var craftStabilizerTier4 = this.makeCraftStabilizerTier4Entry(entryMap, 'l');
        craftStabilizerTier4.withParent(BookEntryParentModel.create(craftStabilizerTier3.getId()));

        var craftStableWormhole = this.makeCraftStableWormholeEntry(entryMap, 'm');
        craftStableWormhole.withParent(BookEntryParentModel.create(craftStorageControllerBase.getId()));
        var craftStorageRemote = this.makeCraftStorageRemoteEntry(entryMap, 'o');
        craftStorageRemote.withParent(BookEntryParentModel.create(craftStableWormhole.getId()));

        var craftOtherworldGoggles = this.makeCraftOtherworldGogglesEntry(entryMap, 'f');
        craftOtherworldGoggles.withParent(BookEntryParentModel.create(overview.getId()));

        var craftSatchel = this.makeCraftSatchelEntry(entryMap, 'g');
        craftSatchel.withParent(BookEntryParentModel.create(overview.getId()));

        var craftSoulGem = this.makeCraftSoulGemEntry(entryMap, 'h');
        craftSoulGem.withParent(BookEntryParentModel.create(overview.getId()));
        var craftFamiliarRing = this.makeCraftFamiliarRingEntry(entryMap, 'c');
        craftFamiliarRing.withParent(BookEntryParentModel.create(craftSoulGem.getId()));

        //add true condition to all entries to enable them by default
        overview.withCondition(BookTrueConditionModel.builder().build());
        craftInfusedPickaxe.withCondition(BookTrueConditionModel.builder().build());
        craftDimensionalMineshaft.withCondition(BookTrueConditionModel.builder().build());
        craftFoliotMiner.withCondition(BookTrueConditionModel.builder().build());
        craftDjinniMiner.withCondition(BookTrueConditionModel.builder().build());
        craftAfritMiner.withCondition(BookTrueConditionModel.builder().build());
        craftMaridMiner.withCondition(BookTrueConditionModel.builder().build());
        craftStorageSystem.withCondition(BookTrueConditionModel.builder().build());
        craftDimensionalMatrix.withCondition(BookTrueConditionModel.builder().build());
        craftStorageControllerBase.withCondition(BookTrueConditionModel.builder().build());
        craftStabilizerTier1.withCondition(BookTrueConditionModel.builder().build());
        craftStabilizerTier2.withCondition(BookTrueConditionModel.builder().build());
        craftStabilizerTier3.withCondition(BookTrueConditionModel.builder().build());
        craftStabilizerTier4.withCondition(BookTrueConditionModel.builder().build());
        craftStableWormhole.withCondition(BookTrueConditionModel.builder().build());
        craftStorageRemote.withCondition(BookTrueConditionModel.builder().build());
        craftOtherworldGoggles.withCondition(BookTrueConditionModel.builder().build());
        craftSatchel.withCondition(BookTrueConditionModel.builder().build());
        craftSoulGem.withCondition(BookTrueConditionModel.builder().build());
        craftFamiliarRing.withCondition(BookTrueConditionModel.builder().build());

        return BookCategoryModel.create(this.modLoc(this.context().categoryId()), this.context().categoryName())
                .withIcon(this.modLoc("textures/gui/book/infusion.png"))
                .withShowCategoryButton(true)
                .withEntries(
                        overview,
                        returnToRituals,
                        craftStorageSystem,
                        craftDimensionalMatrix,
                        craftDimensionalMineshaft,
                        craftInfusedPickaxe,
                        craftFoliotMiner,
                        craftDjinniMiner,
                        craftAfritMiner,
                        craftMaridMiner,
                        craftOtherworldGoggles,
                        craftSatchel,
                        craftSoulGem,
                        craftFamiliarRing,
                        craftStabilizerTier1,
                        craftStabilizerTier2,
                        craftStabilizerTier3,
                        craftStabilizerTier4,
                        craftStableWormhole,
                        craftStorageControllerBase,
                        craftStorageRemote
                );
    }

    private BookEntryModel makeCraftingRitualsOverviewEntry(CategoryEntryMap entryMap, char icon) {
        this.context().entry("overview");

        this.context().page("intro");
        var intro = BookTextPageModel.builder()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText())
                .build();

        return BookEntryModel.create(this.modLoc(this.context().categoryId() + "/" + this.context().entryId()), this.context().entryName())
                .withIcon(this.modLoc("textures/gui/book/infusion.png"))
                .withLocation(entryMap.get(icon))
                .withEntryBackground(0, 1)
                .withPages(
                        intro
                );
    }

    private BookEntryModel makeCraftStorageSystemEntry(CategoryEntryMap entryMap, char icon) {
        this.context().entry("craft_storage_system");

        this.context().page("spotlight");
        var spotlight = BookSpotlightPageModel.builder()
                .withItem(Ingredient.of(OccultismBlocks.STORAGE_CONTROLLER.get()))
                .withText(this.context().pageText())
                .build();

        return BookEntryModel.create(this.modLoc(this.context().categoryId() + "/" + this.context().entryId()), this.context().entryName())
                .withIcon(Items.CHEST)
                .withLocation(entryMap.get(icon))
                .withPages(
                        spotlight
                );
    }

    private BookEntryModel makeCraftDimensionalMatrixEntry(CategoryEntryMap entryMap, char icon) {
        this.context().entry("craft_dimensional_matrix");

        this.context().page("spotlight");
        var spotlight = BookSpotlightPageModel.builder()
                .withItem(Ingredient.of(OccultismItems.DIMENSIONAL_MATRIX.get()))
                .withText(this.context().pageText())
                .build();

        this.context().page("ritual");
        var ritual = BookRitualRecipePageModel.builder()
                .withRecipeId1(this.modLoc("ritual/craft_dimensional_matrix"))
                .build();

        return BookEntryModel.create(this.modLoc(this.context().categoryId() + "/" + this.context().entryId()), this.context().entryName())
                .withIcon(OccultismItems.DIMENSIONAL_MATRIX.get())
                .withLocation(entryMap.get(icon))
                .withPages(
                        spotlight,
                        ritual
                );
    }

    private BookEntryModel makeCraftDimensionalMineshaftEntry(CategoryEntryMap entryMap, char icon) {
        this.context().entry("craft_dimensional_mineshaft");

        this.context().page("spotlight");
        var spotlight = BookSpotlightPageModel.builder()
                .withItem(Ingredient.of(OccultismBlocks.DIMENSIONAL_MINESHAFT.get()))
                .withText(this.context().pageText())
                .build();

        this.context().page("ritual");
        var ritual = BookRitualRecipePageModel.builder()
                .withRecipeId1(this.modLoc("ritual/craft_dimensional_mineshaft"))
                .build();

        this.context().page("description");
        var description = BookTextPageModel.builder()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText())
                .build();

        return BookEntryModel.create(this.modLoc(this.context().categoryId() + "/" + this.context().entryId()), this.context().entryName())
                .withIcon(OccultismBlocks.DIMENSIONAL_MINESHAFT.get())
                .withLocation(entryMap.get(icon))
                .withPages(
                        spotlight,
                        ritual,
                        description
                );
    }

    private BookEntryModel makeCraftInfusedPickaxeEntry(CategoryEntryMap entryMap, char icon) {
        this.context().entry("craft_infused_pickaxe");

        this.context().page("spotlight");
        var spotlight = BookSpotlightPageModel.builder()
                .withItem(Ingredient.of(OccultismItems.INFUSED_PICKAXE.get()))
                .withText(this.context().pageText())
                .build();

        this.context().page("ritual");
        var ritual = BookRitualRecipePageModel.builder()
                .withRecipeId1(this.modLoc("ritual/craft_infused_pickaxe"))
                .build();

        return BookEntryModel.create(this.modLoc(this.context().categoryId() + "/" + this.context().entryId()), this.context().entryName())
                .withIcon(OccultismItems.INFUSED_PICKAXE.get())
                .withLocation(entryMap.get(icon))
                .withPages(
                        spotlight,
                        ritual
                );
    }

    private BookEntryModel makeCraftStorageControllerBaseEntry(CategoryEntryMap entryMap, char icon) {
        this.context().entry("craft_storage_controller_base");

        this.context().page("spotlight");
        var spotlight = BookSpotlightPageModel.builder()
                .withItem(Ingredient.of(OccultismBlocks.STORAGE_CONTROLLER_BASE.get()))
                .withText(this.context().pageText())
                .build();

        this.context().page("ritual");
        var ritual = BookRitualRecipePageModel.builder()
                .withRecipeId1(this.modLoc("ritual/craft_storage_controller_base"))
                .build();

        return BookEntryModel.create(this.modLoc(this.context().categoryId() + "/" + this.context().entryId()), this.context().entryName())
                .withIcon(OccultismBlocks.STORAGE_CONTROLLER_BASE.get())
                .withLocation(entryMap.get(icon))
                .withPages(
                        spotlight,
                        ritual
                );
    }

    private BookEntryModel makeCraftStabilizerTier1Entry(CategoryEntryMap entryMap, char icon) {
        this.context().entry("craft_stabilizer_tier1");

        this.context().page("spotlight");
        var spotlight = BookSpotlightPageModel.builder()
                .withItem(Ingredient.of(OccultismBlocks.STORAGE_STABILIZER_TIER1.get()))
                .withText(this.context().pageText())
                .build();

        this.context().page("ritual");
        var ritual = BookRitualRecipePageModel.builder()
                .withRecipeId1(this.modLoc("ritual/craft_stabilizer_tier1"))
                .build();

        return BookEntryModel.create(this.modLoc(this.context().categoryId() + "/" + this.context().entryId()), this.context().entryName())
                .withIcon(OccultismBlocks.STORAGE_STABILIZER_TIER1.get())
                .withLocation(entryMap.get(icon))
                .withPages(
                        spotlight,
                        ritual
                );
    }

    private BookEntryModel makeCraftStabilizerTier2Entry(CategoryEntryMap entryMap, char icon) {
        this.context().entry("craft_stabilizer_tier2");

        this.context().page("spotlight");
        var spotlight = BookSpotlightPageModel.builder()
                .withItem(Ingredient.of(OccultismBlocks.STORAGE_STABILIZER_TIER2.get()))
                .withText(this.context().pageText())
                .build();

        this.context().page("ritual");
        var ritual = BookRitualRecipePageModel.builder()
                .withRecipeId1(this.modLoc("ritual/craft_stabilizer_tier2"))
                .build();

        return BookEntryModel.create(this.modLoc(this.context().categoryId() + "/" + this.context().entryId()), this.context().entryName())
                .withIcon(OccultismBlocks.STORAGE_STABILIZER_TIER2.get())
                .withLocation(entryMap.get(icon))
                .withPages(
                        spotlight,
                        ritual
                );
    }

    private BookEntryModel makeCraftStabilizerTier3Entry(CategoryEntryMap entryMap, char icon) {
        this.context().entry("craft_stabilizer_tier3");

        this.context().page("spotlight");
        var spotlight = BookSpotlightPageModel.builder()
                .withItem(Ingredient.of(OccultismBlocks.STORAGE_STABILIZER_TIER3.get()))
                .withText(this.context().pageText())
                .build();

        this.context().page("ritual");
        var ritual = BookRitualRecipePageModel.builder()
                .withRecipeId1(this.modLoc("ritual/craft_stabilizer_tier3"))
                .build();

        return BookEntryModel.create(this.modLoc(this.context().categoryId() + "/" + this.context().entryId()), this.context().entryName())
                .withIcon(OccultismBlocks.STORAGE_STABILIZER_TIER3.get())
                .withLocation(entryMap.get(icon))
                .withPages(
                        spotlight,
                        ritual
                );
    }

    private BookEntryModel makeCraftStabilizerTier4Entry(CategoryEntryMap entryMap, char icon) {
        this.context().entry("craft_stabilizer_tier4");

        this.context().page("spotlight");
        var spotlight = BookSpotlightPageModel.builder()
                .withItem(Ingredient.of(OccultismBlocks.STORAGE_STABILIZER_TIER4.get()))
                .withText(this.context().pageText())
                .build();

        this.context().page("ritual");
        var ritual = BookRitualRecipePageModel.builder()
                .withRecipeId1(this.modLoc("ritual/craft_stabilizer_tier4"))
                .build();

        return BookEntryModel.create(this.modLoc(this.context().categoryId() + "/" + this.context().entryId()), this.context().entryName())
                .withIcon(OccultismBlocks.STORAGE_STABILIZER_TIER4.get())
                .withLocation(entryMap.get(icon))
                .withPages(
                        spotlight,
                        ritual
                );
    }

    private BookEntryModel makeCraftStableWormholeEntry(CategoryEntryMap entryMap, char icon) {
        this.context().entry("craft_stable_wormhole");

        this.context().page("spotlight");
        var spotlight = BookSpotlightPageModel.builder()
                .withItem(Ingredient.of(OccultismBlocks.STABLE_WORMHOLE.get()))
                .withText(this.context().pageText())
                .build();

        this.context().page("ritual");
        var ritual = BookRitualRecipePageModel.builder()
                .withRecipeId1(this.modLoc("ritual/craft_stable_wormhole"))
                .build();

        return BookEntryModel.create(this.modLoc(this.context().categoryId() + "/" + this.context().entryId()), this.context().entryName())
                .withIcon(OccultismBlocks.STABLE_WORMHOLE.get())
                .withLocation(entryMap.get(icon))
                .withPages(
                        spotlight,
                        ritual
                );
    }

    private BookEntryModel makeCraftStorageRemoteEntry(CategoryEntryMap entryMap, char icon) {
        this.context().entry("craft_storage_remote");

        this.context().page("spotlight");
        var spotlight = BookSpotlightPageModel.builder()
                .withItem(Ingredient.of(OccultismItems.STORAGE_REMOTE.get()))
                .withText(this.context().pageText())
                .build();

        this.context().page("ritual");
        var ritual = BookRitualRecipePageModel.builder()
                .withRecipeId1(this.modLoc("ritual/craft_storage_remote"))
                .build();

        return BookEntryModel.create(this.modLoc(this.context().categoryId() + "/" + this.context().entryId()), this.context().entryName())
                .withIcon(OccultismItems.STORAGE_REMOTE.get())
                .withLocation(entryMap.get(icon))
                .withPages(
                        spotlight,
                        ritual
                );
    }

    private BookEntryModel makeCraftFoliotMinerEntry(CategoryEntryMap entryMap, char icon) {
        this.context().entry("craft_foliot_miner");

        this.context().page("intro");
        var intro = BookTextPageModel.builder()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText())
                .build();

        this.context().page("magic_lamp");
        var lamp = BookTextPageModel.builder()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText())
                .build();

        this.context().page("magic_lamp_recipe");
        var lampRecipe = BookCraftingRecipePageModel.builder()
                .withRecipeId1(this.modLoc("crafting/magic_lamp_empty"))
                .build();

        this.context().page("spotlight");
        var spotlight = BookSpotlightPageModel.builder()
                .withItem(Ingredient.of(OccultismItems.MINER_FOLIOT_UNSPECIALIZED.get()))
                .withText(this.context().pageText())
                .build();

        this.context().page("ritual");
        var ritual = BookRitualRecipePageModel.builder()
                .withRecipeId1(this.modLoc("ritual/craft_miner_foliot_unspecialized"))
                .build();

        return BookEntryModel.create(this.modLoc(this.context().categoryId() + "/" + this.context().entryId()), this.context().entryName())
                .withIcon(OccultismItems.MINER_FOLIOT_UNSPECIALIZED.get())
                .withLocation(entryMap.get(icon))
                .withPages(
                        intro,
                        lamp,
                        lampRecipe,
                        spotlight,
                        ritual
                );
    }

    private BookEntryModel makeCraftDjinniMinerEntry(CategoryEntryMap entryMap, char icon) {
        this.context().entry("craft_djinni_miner");

        this.context().page("spotlight");
        var spotlight = BookSpotlightPageModel.builder()
                .withItem(Ingredient.of(OccultismItems.MINER_DJINNI_ORES.get()))
                .withText(this.context().pageText())
                .build();

        this.context().page("ritual");
        var ritual = BookRitualRecipePageModel.builder()
                .withRecipeId1(this.modLoc("ritual/craft_miner_djinni_ores"))
                .build();

        return BookEntryModel.create(this.modLoc(this.context().categoryId() + "/" + this.context().entryId()), this.context().entryName())
                .withIcon(OccultismItems.MINER_DJINNI_ORES.get())
                .withLocation(entryMap.get(icon))
                .withPages(
                        spotlight,
                        ritual
                );
    }

    private BookEntryModel makeCraftAfritMinerEntry(CategoryEntryMap entryMap, char icon) {
        this.context().entry("craft_afrit_miner");

        this.context().page("spotlight");
        var spotlight = BookSpotlightPageModel.builder()
                .withItem(Ingredient.of(OccultismItems.MINER_AFRIT_DEEPS.get()))
                .withText(this.context().pageText())
                .build();

        this.context().page("ritual");
        var ritual = BookRitualRecipePageModel.builder()
                .withRecipeId1(this.modLoc("ritual/craft_miner_afrit_deeps"))
                .build();

        return BookEntryModel.create(this.modLoc(this.context().categoryId() + "/" + this.context().entryId()), this.context().entryName())
                .withIcon(OccultismItems.MINER_AFRIT_DEEPS.get())
                .withLocation(entryMap.get(icon))
                .withPages(
                        spotlight,
                        ritual
                );
    }

    private BookEntryModel makeCraftMaridMinerEntry(CategoryEntryMap entryMap, char icon) {
        this.context().entry("craft_marid_miner");

        this.context().page("spotlight");
        var spotlight = BookSpotlightPageModel.builder()
                .withItem(Ingredient.of(OccultismItems.MINER_MARID_MASTER.get()))
                .withText(this.context().pageText())
                .build();

        this.context().page("ritual");
        var ritual = BookRitualRecipePageModel.builder()
                .withRecipeId1(this.modLoc("ritual/craft_miner_marid_master"))
                .build();

        return BookEntryModel.create(this.modLoc(this.context().categoryId() + "/" + this.context().entryId()), this.context().entryName())
                .withIcon(OccultismItems.MINER_MARID_MASTER.get())
                .withLocation(entryMap.get(icon))
                .withPages(
                        spotlight,
                        ritual
                );
    }

    private BookEntryModel makeCraftSatchelEntry(CategoryEntryMap entryMap, char icon) {
        this.context().entry("craft_satchel");

        this.context().page("spotlight");
        var spotlight = BookSpotlightPageModel.builder()
                .withItem(Ingredient.of(OccultismItems.SATCHEL.get()))
                .withText(this.context().pageText())
                .build();

        this.context().page("ritual");
        var ritual = BookRitualRecipePageModel.builder()
                .withRecipeId1(this.modLoc("ritual/craft_satchel"))
                .build();

        return BookEntryModel.create(this.modLoc(this.context().categoryId() + "/" + this.context().entryId()), this.context().entryName())
                .withIcon(OccultismItems.SATCHEL.get())
                .withLocation(entryMap.get(icon))
                .withPages(
                        spotlight,
                        ritual
                );
    }

    private BookEntryModel makeCraftSoulGemEntry(CategoryEntryMap entryMap, char icon) {
        this.context().entry("craft_soul_gem");

        this.context().page("spotlight");
        var spotlight = BookSpotlightPageModel.builder()
                .withItem(Ingredient.of(OccultismItems.SOUL_GEM_ITEM.get()))
                .withText(this.context().pageText())
                .build();

        this.context().page("usage");
        var usage = BookTextPageModel.builder()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText())
                .build();

        this.context().page("ritual");
        var ritual = BookRitualRecipePageModel.builder()
                .withRecipeId1(this.modLoc("ritual/craft_soul_gem"))
                .build();

        return BookEntryModel.create(this.modLoc(this.context().categoryId() + "/" + this.context().entryId()), this.context().entryName())
                .withIcon(OccultismItems.SOUL_GEM_ITEM.get())
                .withLocation(entryMap.get(icon))
                .withPages(
                        spotlight,
                        usage,
                        ritual
                );
    }

    private BookEntryModel makeCraftFamiliarRingEntry(CategoryEntryMap entryMap, char icon) {
        this.context().entry("craft_familiar_ring");

        this.context().page("spotlight");
        var spotlight = BookSpotlightPageModel.builder()
                .withItem(Ingredient.of(OccultismItems.FAMILIAR_RING.get()))
                .withText(this.context().pageText())
                .build();

        this.context().page("usage");
        var usage = BookTextPageModel.builder()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText())
                .build();

        this.context().page("ritual");
        var ritual = BookRitualRecipePageModel.builder()
                .withRecipeId1(this.modLoc("ritual/craft_familiar_ring"))
                .build();

        return BookEntryModel.create(this.modLoc(this.context().categoryId() + "/" + this.context().entryId()), this.context().entryName())
                .withIcon(OccultismItems.FAMILIAR_RING.get())
                .withLocation(entryMap.get(icon))
                .withPages(
                        spotlight,
                        usage,
                        ritual
                );
    }

    private BookEntryModel makeCraftOtherworldGogglesEntry(CategoryEntryMap entryMap, char icon) {
        this.context().entry("craft_otherworld_goggles");

        this.context().page("goggles_spotlight");
        var gogglesSpotlight = BookSpotlightPageModel.builder()
                .withItem(Ingredient.of(OccultismItems.OTHERWORLD_GOGGLES.get()))
                .withText(this.context().pageText())
                .build();

        this.context().page("goggles_more");
        var gogglesMore = BookTextPageModel.builder()
                .withText(this.context().pageText())
                .build();

        this.context().page("lenses_spotlight");
        var lensesSpotlight = BookSpotlightPageModel.builder()
                .withItem(Ingredient.of(OccultismItems.LENSES.get()))
                .withText(this.context().pageText())
                .build();

        this.context().page("lenses_more");
        var lensesMore = BookTextPageModel.builder()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText())
                .build();

        this.context().page("lenses_recipe");
        var lensesRecipe = BookCraftingRecipePageModel.builder()
                .withRecipeId1(this.modLoc("crafting/lenses"))
                .build();

        this.context().page("ritual");
        var ritual = BookRitualRecipePageModel.builder()
                .withRecipeId1(this.modLoc("ritual/craft_infused_lenses"))
                .build();

        this.context().page("goggles_recipe");
        var gogglesRecipe = BookCraftingRecipePageModel.builder()
                .withRecipeId1(this.modLoc("crafting/goggles"))
                .build();

        return BookEntryModel.create(this.modLoc(this.context().categoryId() + "/" + this.context().entryId()), this.context().entryName())
                .withIcon(OccultismItems.OTHERWORLD_GOGGLES.get())
                .withLocation(entryMap.get(icon))
                .withPages(
                        gogglesSpotlight,
                        gogglesMore,
                        lensesSpotlight,
                        lensesMore,
                        lensesRecipe,
                        ritual,
                        gogglesRecipe
                );
    }
    //endregion

    //region Possession Rituals
    private BookCategoryModel makePossessionRitualsSubcategory() {
        this.context().category("possession_rituals");

        var entryMap = ModonomiconAPI.get().getEntryMap();
        entryMap.setMap(
                "___________________________",
                "___________________________",
                "___________________________",
                "_______D_E_A_I_L_M_________",
                "___r_o_____________________",
                "_______F_G_H_J_K_N_________",
                "___________________________",
                "___________________________",
                "___________________________"
        );

        var overview = this.makePossessionRitualsOverviewEntry(entryMap, 'o');
        var returnToRituals = this.makeReturnToRitualsEntry(entryMap, 'r');
        returnToRituals.withParent(BookEntryParentModel.create(overview.getId()));
        returnToRituals.withCondition(BookTrueConditionModel.builder().build());

        var possessEndermite = this.makePossessEndermiteEntry(entryMap, 'D');
        possessEndermite.withParent(BookEntryParentModel.create(overview.getId()));
        var possessEnderman = this.makePossessEndermanEntry(entryMap, 'E');
        possessEnderman.withParent(BookEntryParentModel.create(overview.getId()));
        var possessGhast = this.makePossessGhastEntry(entryMap, 'F');
        possessGhast.withParent(BookEntryParentModel.create(overview.getId()));
        var possessSkeleton = this.makePossessSkeletonEntry(entryMap, 'G');
        possessSkeleton.withParent(BookEntryParentModel.create(overview.getId()));
        var possessPhantom = this.makePossessPhantomEntry(entryMap, 'I');
        possessPhantom.withParent(BookEntryParentModel.create(overview.getId()));
        var possessWeakShulker = this.makePossessWeakShulkerEntry(entryMap, 'J');
        possessWeakShulker.withParent(BookEntryParentModel.create(overview.getId()));
        var possessShulker = this.makePossessShulkerEntry(entryMap, 'K');
        possessShulker.withParent(BookEntryParentModel.create(overview.getId()));
        var possessElderGuardian = this.makePossessElderGuardianEntry(entryMap, 'L');
        possessElderGuardian.withParent(BookEntryParentModel.create(overview.getId()));
        var possessWarden = this.makePossessWardenEntry(entryMap, 'M');
        possessWarden.withParent(BookEntryParentModel.create(overview.getId()));
        var possessHoglin = this.makePossessHoglinEntry(entryMap, 'N');
        possessHoglin.withParent(BookEntryParentModel.create(overview.getId()));

        this.context().category("summoning_rituals"); //re-use the entries from the summoning rituals category
        var possessWitherSkeleton = this.makeWitherSkullEntry(entryMap, 'H');
        possessWitherSkeleton.withParent(BookEntryParentModel.create(overview.getId()));
        var afritEssence = this.makeAfritEssenceEntry(entryMap, 'A');
        afritEssence.withParent(BookEntryParentModel.create(overview.getId()));
        this.context().category("possession_rituals");

        //add true condition to all entries to enable them by default
        overview.withCondition(BookTrueConditionModel.builder().build());
        possessEnderman.withCondition(BookTrueConditionModel.builder().build());
        possessEndermite.withCondition(BookTrueConditionModel.builder().build());
        possessGhast.withCondition(BookTrueConditionModel.builder().build());
        possessSkeleton.withCondition(BookTrueConditionModel.builder().build());
        possessPhantom.withCondition(BookTrueConditionModel.builder().build());
        possessWeakShulker.withCondition(BookTrueConditionModel.builder().build());
        possessShulker.withCondition(BookTrueConditionModel.builder().build());
        possessElderGuardian.withCondition(BookTrueConditionModel.builder().build());
        possessWarden.withCondition(BookTrueConditionModel.builder().build());
        possessHoglin.withCondition(BookTrueConditionModel.builder().build());
        possessWitherSkeleton.withCondition(BookTrueConditionModel.builder().build());
        afritEssence.withCondition(BookTrueConditionModel.builder().build());

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
                        possessWitherSkeleton,
                        afritEssence
                );
    }

    private BookEntryModel makePossessionRitualsOverviewEntry(CategoryEntryMap entryMap, char icon) {
        this.context().entry("overview");

        this.context().page("intro");
        var intro = BookTextPageModel.builder()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText())
                .build();

        return BookEntryModel.create(this.modLoc(this.context().categoryId() + "/" + this.context().entryId()), this.context().entryName())
                .withIcon(this.modLoc("textures/gui/book/possession.png"))
                .withLocation(entryMap.get(icon))
                .withEntryBackground(0, 1)
                .withPages(
                        intro
                );
    }

    private BookEntryModel makePossessEndermanEntry(CategoryEntryMap entryMap, char icon) {
        this.context().entry("possess_enderman");

        this.context().page("entity");
        var entity = BookEntityPageModel.builder()
                .withEntityId("occultism:possessed_enderman")
                .withText(this.context().pageText())
                .build();

        this.context().page("ritual");
        var ritual = BookRitualRecipePageModel.builder()
                .withRecipeId1(this.modLoc("ritual/possess_enderman"))
                .build();

        this.context().page("description");
        var description = BookTextPageModel.builder()
                .withText(this.context().pageText())
                .build();

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
        var entity = BookEntityPageModel.builder()
                .withEntityId("occultism:possessed_endermite")
                .withText(this.context().pageText())
                .build();

        this.context().page("ritual");
        var ritual = BookRitualRecipePageModel.builder()
                .withRecipeId1(this.modLoc("ritual/possess_endermite"))
                .build();

        this.context().page("description");
        var description = BookTextPageModel.builder()
                .withText(this.context().pageText())
                .build();

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
        this.lang.add(this.context().entryName(), "Possessed Ghast");

        this.context().page("entity");
        var entity = BookEntityPageModel.builder()
                .withEntityId("occultism:possessed_ghast")
                .withScale(0.5f)
                .withText(this.context().pageText())
                .build();
        this.lang.add(this.context().pageText(),
                """
                        **Drops**: 1-3x [](item://minecraft:ghast_tear)
                                """);

        this.context().page("ritual");
        var ritual = BookRitualRecipePageModel.builder()
                .withRecipeId1(this.modLoc("ritual/possess_ghast"))
                .build();
        //no text

        this.context().page("description");
        var description = BookTextPageModel.builder()
                .withText(this.context().pageText())
                .build();
        this.lang.add(this.context().pageText(),
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
        var entity = BookEntityPageModel.builder()
                .withEntityId("occultism:possessed_skeleton")
                .withText(this.context().pageText())
                .build();

        this.context().page("ritual");
        var ritual = BookRitualRecipePageModel.builder()
                .withRecipeId1(this.modLoc("ritual/possess_skeleton"))
                .build();

        this.context().page("description");
        var description = BookTextPageModel.builder()
                .withText(this.context().pageText())
                .build();

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
        this.lang.add(this.context().entryName(), "Possessed Phantom");

        this.context().page("entity");
        var entity = BookEntityPageModel.builder()
                .withEntityId("occultism:possessed_phantom")
                .withScale(0.5f)
                .withText(this.context().pageText())
                .build();
        this.lang.add(this.context().pageText(),
                """
                        **Drops**: 1-4x [](item://minecraft:phantom_membrane)
                                """);

        this.context().page("ritual");
        var ritual = BookRitualRecipePageModel.builder()
                .withRecipeId1(this.modLoc("ritual/possess_phantom"))
                .build();

        this.context().page("description");
        var description = BookTextPageModel.builder()
                .withText(this.context().pageText())
                .build();
        this.lang.add(this.context().pageText(),
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
        this.lang.add(this.context().entryName(), "Possessed Weak Shulker");

        this.context().page("entity");
        var entity = BookEntityPageModel.builder()
                .withEntityId("occultism:possessed_weak_shulker")
                .withScale(0.5f)
                .withText(this.context().pageText())
                .build();
        this.lang.add(this.context().pageText(),
                """
                        **Drops**: 1-3x [](item://minecraft:chorus_fruit);
                        and as 10% to drop a [](item://minecraft:shulker_shell);
                                """);

        this.context().page("ritual");
        var ritual = BookRitualRecipePageModel.builder()
                .withRecipeId1(this.modLoc("ritual/possess_weak_shulker"))
                .build();

        this.context().page("description");
        var description = BookTextPageModel.builder()
                .withText(this.context().pageText())
                .build();
        this.lang.add(this.context().pageText(),
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

    private BookEntryModel makePossessShulkerEntry(CategoryEntryMap entryMap, char icon) {
        this.context().entry("possess_shulker");
        this.lang.add(this.context().entryName(), "Possessed Shulker");

        this.context().page("entity");
        var entity = BookEntityPageModel.builder()
                .withEntityId("occultism:possessed_shulker")
                .withScale(0.5f)
                .withText(this.context().pageText())
                .build();
        this.lang.add(this.context().pageText(),
                """
                        **Drops**: 1-2x [](item://minecraft:shulker_shell);
                                """);

        this.context().page("ritual");
        var ritual = BookRitualRecipePageModel.builder()
                .withRecipeId1(this.modLoc("ritual/possess_shulker"))
                .build();

        this.context().page("description");
        var description = BookTextPageModel.builder()
                .withText(this.context().pageText())
                .build();
        this.lang.add(this.context().pageText(),
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
        this.lang.add(this.context().entryName(), "Possessed Elder Guardian");

        this.context().page("entity");
        var entity = BookEntityPageModel.builder()
                .withEntityId("occultism:possessed_elder_guardian")
                .withScale(0.5f)
                .withText(this.context().pageText())
                .build();
        this.lang.add(this.context().pageText(),
                """
                        **Drops**: 2-4x [](item://minecraft:nautilus_shell);
                        and as 40% to drop a [](item://minecraft:heart_of_the_sea);
                        Also commom Elder Guardian loot;
                                """);

        this.context().page("ritual");
        var ritual = BookRitualRecipePageModel.builder()
                .withRecipeId1(this.modLoc("ritual/possess_elder_guardian"))
                .build();

        this.context().page("description");
        var description = BookTextPageModel.builder()
                .withText(this.context().pageText())
                .build();
        this.lang.add(this.context().pageText(),
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
        this.lang.add(this.context().entryName(), "Possessed Warden");

        this.context().page("entity");
        var entity = BookEntityPageModel.builder()
                .withEntityId("occultism:possessed_warden")
                .withScale(1f)
                .withText(this.context().pageText())
                .build();
        this.lang.add(this.context().pageText(),
                """
                        **Drops**: 1x [](item://minecraft:echo_shard)
                        and items related to ancient city;
                                """);

        this.context().page("ritual");
        var ritual = BookRitualRecipePageModel.builder()
                .withRecipeId1(this.modLoc("ritual/possess_warden"))
                .build();

        this.context().page("description");
        var description = BookTextPageModel.builder()
                .withText(this.context().pageText())
                .build();
        this.lang.add(this.context().pageText(),
                """
                        In this ritual a [#](%1$s)Warden[#]() is spawned using the life energy of a [#](%1$s)Axolotl[#]() and immediately possessed by the summoned [#](%1$s)Djinni[#](). The [#](%1$s)Possessed Warden[#]() will always drop at least one [](item://minecraft:echo_shard) when killed and as a chance to drop [](item://minecraft:disc_fragment_5), [](item://minecraft:music_disc_otherside), [](item://minecraft:silence_armor_trim_smithing_template), [](item://minecraft:ward_armor_trim_smithing_template). If you try to escape, this possessed Warden will go to the floor like a normal warden.
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
        this.lang.add(this.context().entryName(), "Possessed Hoglin");

        this.context().page("entity");
        var entity = BookEntityPageModel.builder()
                .withEntityId("occultism:possessed_hoglin")
                .withScale(0.7f)
                .withText(this.context().pageText())
                .build();
        this.lang.add(this.context().pageText(),
                """
                        **Drops**: Can drop: [](item://minecraft:netherite_upgrade_smithing_template),
                        return back [](item://minecraft:netherite_scrap) or nothing;
                      """);

        this.context().page("ritual");
        var ritual = BookRitualRecipePageModel.builder()
                .withRecipeId1(this.modLoc("ritual/possess_hoglin"))
                .build();

        this.context().page("description");
        var description = BookTextPageModel.builder()
                .withText(this.context().pageText())
                .build();
        this.lang.add(this.context().pageText(),
                """
                        In this ritual a [#](%1$s)Hoglin[#]() is spawned using the life energy of a [#](%1$s)Pig[#]() and immediately possessed by the summoned [#](%1$s)Afrit[#](). The [#](%1$s)Possessed Hoglin[#]() can drop a [](item://minecraft:netherite_upgrade_smithing_template), [](item://minecraft:snout_armor_trim_smithing_template), return back [](item://minecraft:netherite_scrap) or nothing when killed. You need to kill this mob before the transformation to a Zoglin if you don't want to perform the ritual in the nether.
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

    //endregion

    private BookCategoryModel makeStorageCategory() {
        this.context().category("storage");

        var entryMap = ModonomiconAPI.get().getEntryMap();
        entryMap.setMap(
                "___________________________",
                "_________w_r_______________",
                "___________________________",
                "_____0_c___s_1_2_3_4________",
                "___________________________",
                "_________d_________________",
                "___________________________"
        );

        var overview = this.makeStorageOverviewEntry(entryMap, '0');

        this.context().category("storage");
        var storageController = this.makeStorageControllerEntry(entryMap, 'c');
        storageController.withParent(BookEntryParentModel.create(overview.getId()));

        var storageStabilizer = this.makeStorageStabilizerEntry(entryMap, 's');
        storageStabilizer.withParent(BookEntryParentModel.create(storageController.getId()));

        this.context().category("crafting_rituals"); //re-use existing entries
        var craftStabilizerTier1 = this.makeCraftStabilizerTier1Entry(entryMap, '1');
        craftStabilizerTier1.withParent(BookEntryParentModel.create(storageStabilizer.getId()));
        var craftStabilizerTier2 = this.makeCraftStabilizerTier2Entry(entryMap, '2');
        craftStabilizerTier2.withParent(BookEntryParentModel.create(
                new ResourceLocation(
                        craftStabilizerTier1.getId().getNamespace(),
                        "storage/" + craftStabilizerTier1.getId().getPath()
                )
        ));
        var craftStabilizerTier3 = this.makeCraftStabilizerTier3Entry(entryMap, '3');
        craftStabilizerTier3.withParent(BookEntryParentModel.create(
                new ResourceLocation(
                        craftStabilizerTier2.getId().getNamespace(),
                        "storage/" + craftStabilizerTier2.getId().getPath()
                )
        ));
        var craftStabilizerTier4 = this.makeCraftStabilizerTier4Entry(entryMap, '4');
        craftStabilizerTier4.withParent(BookEntryParentModel.create(
                new ResourceLocation(
                        craftStabilizerTier3.getId().getNamespace(),
                        "storage/" + craftStabilizerTier3.getId().getPath()
                )
        ));

        var craftStableWormhole = this.makeCraftStableWormholeEntry(entryMap, 'w');
        craftStableWormhole.withParent(BookEntryParentModel.create(storageController.getId()));
        var craftStorageRemote = this.makeCraftStorageRemoteEntry(entryMap, 'r');
        craftStorageRemote.withParent(BookEntryParentModel.create(
                new ResourceLocation(
                        craftStableWormhole.getId().getNamespace(),
                        "storage/" + craftStableWormhole.getId().getPath()
                )
        ));

        this.context().category("summoning_rituals"); //re-use existing entries
        var summonManageMachine = this.makeSummonManageMachineEntry(entryMap, 'd');
        summonManageMachine.withParent(BookEntryParentModel.create(storageController.getId()));

        this.context().category("storage");

        return BookCategoryModel.create(this.modLoc(this.context().categoryId()), this.context().categoryName())
                .withIcon(Items.CHEST)
                .withEntries(
                        overview,
                        storageController,
                        storageStabilizer,
                        craftStabilizerTier1,
                        craftStabilizerTier2,
                        craftStabilizerTier3,
                        craftStabilizerTier4,
                        craftStableWormhole,
                        craftStorageRemote,
                        summonManageMachine
                );
    }

    private BookEntryModel makeStorageOverviewEntry(CategoryEntryMap entryMap, char icon) {
        this.context().entry("overview");

        this.context().page("intro");
        var intro = BookTextPageModel.builder()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText())
                .build();

        this.context().page("intro2");
        var intro2 = BookTextPageModel.builder()
                .withText(this.context().pageText())
                .build();


        return BookEntryModel.create(this.modLoc(this.context().categoryId() + "/" + this.context().entryId()), this.context().entryName())
                .withIcon(Items.CHEST)
                .withLocation(entryMap.get(icon))
                .withEntryBackground(0, 1)
                .withPages(
                        intro,
                        intro2
                );
    }

    private BookEntryModel makeStorageControllerEntry(CategoryEntryMap entryMap, char icon) {
        this.context().entry("storage_controller");

        this.context().page("intro");
        var intro = BookSpotlightPageModel.builder()
                .withItem(Ingredient.of(OccultismBlocks.STORAGE_CONTROLLER.get()))
                .withText(this.context().pageText())
                .build();

        this.context().page("usage");
        var usage = BookTextPageModel.builder()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText())
                .build();

        this.context().page("safety");
        var safety = BookTextPageModel.builder()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText())
                .build();

        this.context().page("size");
        var size = BookTextPageModel.builder()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText())
                .build();

        this.context().page("unique_items");
        var uniqueItems = BookTextPageModel.builder()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText())
                .build();

        this.context().page("config");
        var config = BookTextPageModel.builder()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText())
                .build();

        this.context().page("mods");
        var mods = BookTextPageModel.builder()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText())
                .build();

        this.context().page("matrix_ritual");
        var matrixRitual = BookRitualRecipePageModel.builder()
                .withRecipeId1(this.modLoc("ritual/craft_dimensional_matrix"))
                .build();

        this.context().page("base_ritual");
        var baseRitual = BookRitualRecipePageModel.builder()
                .withRecipeId1(this.modLoc("ritual/craft_storage_controller_base"))
                .build();

        this.context().page("recipe");
        var recipe = BookCraftingRecipePageModel.builder()
                .withRecipeId1(this.modLoc("crafting/storage_controller"))
                .withText(this.context().pageText())
                .build();

        return BookEntryModel.create(this.modLoc(this.context().categoryId() + "/" + this.context().entryId()), this.context().entryName())
                .withIcon(OccultismBlocks.STORAGE_CONTROLLER.get())
                .withLocation(entryMap.get(icon))
                .withPages(
                        intro,
                        usage,
                        safety,
                        size,
                        uniqueItems,
                        config,
                        mods,
                        matrixRitual,
                        baseRitual,
                        recipe
                );
    }

    private BookEntryModel makeStorageStabilizerEntry(CategoryEntryMap entryMap, char icon) {
        this.context().entry("storage_stabilizer");

        this.context().page("spotlight");
        var spotlight = BookSpotlightPageModel.builder()
                .withItem(Ingredient.of(OccultismBlocks.STORAGE_STABILIZER_TIER1.get()))
                .withText(this.context().pageText())
                .build();

        this.context().page("upgrade");
        var upgrade = BookTextPageModel.builder()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText())
                .build();

        this.context().page("build_instructions");
        var buildInstructions = BookTextPageModel.builder()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText())
                .build();

        this.context().page("demo");
        var demo = BookMultiblockPageModel.builder()
                .withMultiblockId(this.modLoc("storage_stabilizer_demo"))
                .withMultiblockName(this.context().pageTitle())
                .build();

        return BookEntryModel.create(this.modLoc(this.context().categoryId() + "/" + this.context().entryId()), this.context().entryName())
                .withIcon(OccultismBlocks.STORAGE_CONTROLLER.get())
                .withLocation(entryMap.get(icon))
                .withPages(
                        spotlight,
                        upgrade,
                        buildInstructions,
                        demo
                );
    }
}
