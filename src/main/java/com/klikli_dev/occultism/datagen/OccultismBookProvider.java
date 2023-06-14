package com.klikli_dev.occultism.datagen;

import com.klikli_dev.modonomicon.api.ModonomiconAPI;
import com.klikli_dev.modonomicon.api.datagen.BookLangHelper;
import com.klikli_dev.modonomicon.api.datagen.BookProvider;
import com.klikli_dev.modonomicon.api.datagen.EntryLocationHelper;
import com.klikli_dev.modonomicon.api.datagen.book.BookCategoryModel;
import com.klikli_dev.modonomicon.api.datagen.book.BookEntryModel;
import com.klikli_dev.modonomicon.api.datagen.book.BookEntryParentModel;
import com.klikli_dev.modonomicon.api.datagen.book.BookModel;
import com.klikli_dev.modonomicon.api.datagen.book.condition.BookAndConditionModel;
import com.klikli_dev.modonomicon.api.datagen.book.condition.BookEntryReadConditionModel;
import com.klikli_dev.modonomicon.api.datagen.book.condition.BookModLoadedConditionModel;
import com.klikli_dev.modonomicon.api.datagen.book.condition.BookTrueConditionModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.*;
import com.klikli_dev.occultism.integration.modonomicon.pages.BookRitualRecipePageModel;
import com.klikli_dev.occultism.integration.modonomicon.pages.BookSpiritFireRecipePageModel;
import com.klikli_dev.occultism.integration.modonomicon.pages.BookSpiritTradeRecipePageModel;
import com.klikli_dev.occultism.registry.OccultismBlocks;
import com.klikli_dev.occultism.registry.OccultismItems;
import com.klikli_dev.theurgy.registry.ItemRegistry;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.data.LanguageProvider;
import net.minecraftforge.registries.ForgeRegistries;

public class OccultismBookProvider extends BookProvider {

    public static final String COLOR_PURPLE = "ad03fc";
    public static final String DEMONS_DREAM = "Demon's Dream";

    public OccultismBookProvider(PackOutput packOutput, String modid, LanguageProvider lang, LanguageProvider... translations) {
        super(packOutput, modid, lang, translations);
    }

    @Override
    protected void generate() {
        var dictionaryOfSpirits = this.makeDictionaryOfSpirits();
        this.add(dictionaryOfSpirits);
    }

    private BookModel makeDictionaryOfSpirits() {
        var helper = ModonomiconAPI.get().getLangHelper(this.modid);
        helper.book("dictionary_of_spirits");
        this.lang().add(helper.bookName(), "Dictionary of Spirits");
        this.lang().add(helper.bookTooltip(), "An introduction to the spirit world.");

        int sortNum = 1;
        var gettingStartedCategory = this.makeGettingStartedCategory(helper).withSortNumber(sortNum++);
        var spiritsCategory = this.makeSpiritsSubcategory(helper).withSortNumber(sortNum++);

        var storageCategory = this.makeStorageCategory(helper).withSortNumber(sortNum++);

        var ritualsCategory = this.makeRitualsCategory(helper).withSortNumber(sortNum++);

        var summoningRitualsCategory = this.makeSummoningRitualsSubcategory(helper).withSortNumber(sortNum++);
        var possessionRitualsCategory = this.makePossessionRitualsSubcategory(helper).withSortNumber(sortNum++);
        var craftingRitualsCategory = this.makeCraftingRitualsSubcategory(helper).withSortNumber(sortNum++);
        var familiarRitualsCategory = this.makeFamiliarRitualsSubcategory(helper).withSortNumber(sortNum++);

        var pentaclesCategory = this.makePentaclesCategory(helper).withSortNumber(sortNum++);

        var introReadCondition = BookEntryReadConditionModel.builder()
                .withEntry(this.modLoc("getting_started/intro")).build();

        storageCategory.withCondition(introReadCondition);
        ritualsCategory.withCondition(introReadCondition);
        pentaclesCategory.withCondition(introReadCondition);

        //https://tinyurl.com/occultism-graph

        var demoBook = BookModel.create(this.modLoc("dictionary_of_spirits"), helper.bookName())
                .withModel(this.modLoc("dictionary_of_spirits_icon"))
                .withTooltip(helper.bookTooltip())
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
                .withAutoAddReadConditions(true);
        return demoBook;
    }

    //region Getting Started
    private BookCategoryModel makeGettingStartedCategory(BookLangHelper helper) {
        helper.category("getting_started");
        this.lang().add(helper.categoryName(), "Getting Started");

        var entryHelper = ModonomiconAPI.get().getEntryLocationHelper();
        entryHelper.setMap(
                "__________t___B___________P___D___",
                "__________________________________",
                "______i___r___ç_b_____g_I_O_l_M___",
                "__________________________________",
                "______d___f_c_____R___a_s_________",
                "__________________________________",
                "______e_h_____ạ_______m___________",
                "__________________________________",
                "________________C___p_S___w_x_y_z_"
        );
        //B=brush, N=Next Steps, P=iesnium pick
        //i=intro, r=divinationRod, ç = chalk, b=bowls, g=goggles,  I=infused pick O= tier 2 otherworld materials
        //  l=lamps, M=miner, D=Dim Mineshaft
        //d=demonsDream, h=healing, f=SpiritFire, c=candle, R=ritual, a=advancedChalks,
        //e=thirdEye, ạ=books of binding, m=more ritual, s=storage
        //C=book of calling, p=grey particles, S=spirits, w=possession, x=familiars, y=summoning, z=crafting

        var introEntry = this.makeIntroEntry(helper, entryHelper, 'i');

        var demonsDreamEntry = this.makeDemonsDreamEntry(helper, entryHelper, 'd');
        demonsDreamEntry.withParent(BookEntryParentModel.builder().withEntryId(introEntry.id).build());

        var spiritFireEntry = this.makeSpiritFireEntry(helper, entryHelper, 'f');
        spiritFireEntry.withParent(BookEntryParentModel.builder().withEntryId(demonsDreamEntry.id).build());

        var healingSpiritsEntry = this.makeHealingSpiritsEntry(helper, entryHelper, 'h');
        healingSpiritsEntry.withParent(BookEntryParentModel.builder().withEntryId(demonsDreamEntry.id).build());

        var thirdEyeEntry = this.makeThirdEyeEntry(helper, entryHelper, 'e');
        thirdEyeEntry.withParent(BookEntryParentModel.builder().withEntryId(demonsDreamEntry.id).build());

        var divinationRodEntry = this.makeDivinationRodEntry(helper, entryHelper, 'r');
        divinationRodEntry.withParent(BookEntryParentModel.builder().withEntryId(spiritFireEntry.id).build());

        var theurgyDivinationRodEntry = this.makeTheurgyDivinationRodsEntry(helper, entryHelper, 't');
        theurgyDivinationRodEntry
                .withParent(BookEntryParentModel.builder()
                        .withEntryId(divinationRodEntry.id)
                        .build())
                .withCondition(
                        BookAndConditionModel.builder().withChildren(
                                BookEntryReadConditionModel.builder()
                                        .withEntry(divinationRodEntry.id).build(),
                                BookModLoadedConditionModel.builder()
                                        .withModId("theurgy").build()
                        ).build()
                )
                .hideWhileLocked(true);

        var candleEntry = this.makeCandleEntry(helper, entryHelper, 'c');
        candleEntry.withParent(BookEntryParentModel.builder().withEntryId(spiritFireEntry.id).build());

        var ritualPrepChalkEntry = this.makeRitualPrepChalkEntry(helper, entryHelper, 'ç');
        ritualPrepChalkEntry.withParent(BookEntryParentModel.builder().withEntryId(candleEntry.id).build());

        var brushEntry = this.makeBrushEntry(helper, entryHelper, 'B');
        brushEntry.withParent(BookEntryParentModel.builder().withEntryId(ritualPrepChalkEntry.id).build());

        var ritualPrepBowlEntry = this.makeRitualPrepBowlEntry(helper, entryHelper, 'b');
        ritualPrepBowlEntry.withParent(BookEntryParentModel.builder().withEntryId(ritualPrepChalkEntry.id).build());

        var booksOfBinding = this.makeBooksOfBindingEntry(helper, entryHelper, 'ạ');
        booksOfBinding.withParent(BookEntryParentModel.builder().withEntryId(candleEntry.id).build());

        var booksOfCalling = this.makeBooksOfCallingEntry(helper, entryHelper, 'C');
        booksOfCalling.withParent(BookEntryParentModel.builder().withEntryId(booksOfBinding.id).build());

        var ritualEntry = this.makeRitualEntry(helper, entryHelper, 'R');
        ritualEntry
                .withParent(BookEntryParentModel.builder().withEntryId(ritualPrepBowlEntry.id).build())
                .withParent(BookEntryParentModel.builder().withEntryId(booksOfBinding.id).build());

        var advancedChalksEntry = this.makeChalksEntry(helper, entryHelper, 'a');
        advancedChalksEntry.withParent(BookEntryParentModel.builder().withEntryId(ritualEntry.id).build());

        var moreRitualsEntry = this.makeMoreRitualsEntry(helper, entryHelper, 'm');
        moreRitualsEntry.withParent(BookEntryParentModel.builder().withEntryId(advancedChalksEntry.id).build());

        var greyParticlesEntry = this.makeGreyParticlesEntry(helper, entryHelper, 'p');
        greyParticlesEntry.withParent(BookEntryParentModel.builder().withEntryId(ritualEntry.id).build());

        var spiritsSubcategory = this.makeSpiritsSubcategoryEntry(helper, entryHelper, 'S');
        spiritsSubcategory.withParent(BookEntryParentModel.builder().withEntryId(greyParticlesEntry.id).build());

        var otherworldGoggles = this.makeOtherworldGogglesEntry(helper, entryHelper, 'g');
        otherworldGoggles.withParent(BookEntryParentModel.builder().withEntryId(advancedChalksEntry.id).build());

        var infusedPickaxe = this.makeInfusedPickaxeEntry(helper, entryHelper, 'I');
        infusedPickaxe.withParent(BookEntryParentModel.builder().withEntryId(otherworldGoggles.id).build());

        var iesnium = this.makeIesniumEntry(helper, entryHelper, 'O');
        iesnium.withParent(BookEntryParentModel.builder().withEntryId(infusedPickaxe.id).build());

        var iesniumPickaxe = this.makeIesniumPickaxeEntry(helper, entryHelper, 'P');
        iesniumPickaxe.withParent(BookEntryParentModel.builder().withEntryId(iesnium.id).build());

        var magicLampsEntry = this.makeMagicLampsEntry(helper, entryHelper, 'l');
        magicLampsEntry.withParent(BookEntryParentModel.builder().withEntryId(iesnium.id).build());

        var spiritMinersEntry = this.makeSpiritMinersEntry(helper, entryHelper, 'M');
        spiritMinersEntry.withParent(BookEntryParentModel.builder().withEntryId(magicLampsEntry.id).build());

        var mineshaftEntry = this.makeMineshaftEntry(helper, entryHelper, 'D');
        mineshaftEntry.withParent(BookEntryParentModel.builder().withEntryId(spiritMinersEntry.id).build());

        var storageEntry = this.makeStorageEntry(helper, entryHelper, 's');
        storageEntry.withParent(BookEntryParentModel.builder().withEntryId(advancedChalksEntry.id).build());

        var possessionRitualsEntry = this.makePossessionRitualsEntry(helper, entryHelper, 'w');
        possessionRitualsEntry.withParent(BookEntryParentModel.builder().withEntryId(moreRitualsEntry.id).build());
        var familiarRitualsEntry = this.makeFamiliarRitualsEntry(helper, entryHelper, 'x');
        familiarRitualsEntry.withParent(BookEntryParentModel.builder().withEntryId(moreRitualsEntry.id).build());
        var summoningRitualsEntry = this.makeSummoningRitualsEntry(helper, entryHelper, 'y');
        summoningRitualsEntry.withParent(BookEntryParentModel.builder().withEntryId(moreRitualsEntry.id).build());
        var craftingRitualsEntry = this.makeCraftingRitualsEntry(helper, entryHelper, 'z');
        craftingRitualsEntry.withParent(BookEntryParentModel.builder().withEntryId(moreRitualsEntry.id).build());

        return BookCategoryModel.create(this.modLoc(helper.category), helper.categoryName())
                .withIcon(OccultismItems.DICTIONARY_OF_SPIRITS_ICON.getId().toString())
                .withEntries(
                        introEntry.build(),
                        demonsDreamEntry.build(),
                        spiritFireEntry.build(),
                        thirdEyeEntry.build(),
                        healingSpiritsEntry.build(),
                        divinationRodEntry.build(),
                        theurgyDivinationRodEntry.build(),
                        candleEntry.build(),
                        ritualPrepChalkEntry.build(),
                        ritualPrepBowlEntry.build(),
                        booksOfBinding.build(),
                        ritualEntry.build(),
                        brushEntry.build(),
                        moreRitualsEntry.build(),
                        greyParticlesEntry.build(),
                        booksOfCalling.build(),
                        spiritsSubcategory.build(),
                        advancedChalksEntry.build(),
                        otherworldGoggles.build(),
                        infusedPickaxe.build(),
                        iesnium.build(),
                        iesniumPickaxe.build(),
                        magicLampsEntry.build(),
                        spiritMinersEntry.build(),
                        mineshaftEntry.build(),
                        storageEntry.build(),
                        possessionRitualsEntry.build(),
                        familiarRitualsEntry.build(),
                        summoningRitualsEntry.build(),
                        craftingRitualsEntry.build()
                );
    }

    private BookEntryModel.Builder makeIntroEntry(BookLangHelper helper, EntryLocationHelper entryHelper, char icon) {
        helper.entry("intro");
        this.lang().add(helper.entryName(), "About");
        this.lang().add(helper.entryDescription(), "About using the Dictionary of Spirits");

        helper.page("intro");
        var intro = BookTextPageModel.builder()
                .withTitle(helper.pageTitle())
                .withText(helper.pageText())
                .build();
        this.lang().add(helper.pageTitle(), "About");
        this.lang().add(helper.pageText(),
                """
                        This book aims to introduce the novice reader to the most common summoning rituals and equip them with a list of spirit names to summon.
                        The authors advise caution in the summoning of the listed entities and does not take responsibility for any harm caused.
                        """);

        helper.page("help");
        var help = BookTextPageModel.builder()
                .withTitle(helper.pageTitle())
                .withText(helper.pageText())
                .build();
        this.lang().add(helper.pageTitle(), "Getting Help");
        this.lang().add(helper.pageText(),
                """
                        If you run into any trouble while playing Occultism, please join our Discord server and ask for help.
                        \\
                        \\
                        [Join us at https://invite.gg/klikli](https://invite.gg/klikli)
                        """);

        return BookEntryModel.builder()
                .withId(this.modLoc(helper.category + "/" + helper.entry))
                .withName(helper.entryName())
                .withDescription(helper.entryDescription())
                .withIcon(OccultismItems.DICTIONARY_OF_SPIRITS_ICON.getId().toString())
                .withLocation(entryHelper.get(icon))
                .withEntryBackground(0, 1)
                .withPages(
                        intro,
                        help
                );
    }

    private BookEntryModel.Builder makeDemonsDreamEntry(BookLangHelper helper, EntryLocationHelper entryHelper, char icon) {
        helper.entry("demons_dream");
        this.lang().add(helper.entryName(), "Lifting the Veil");
        this.lang().add(helper.entryDescription(), "Learn about the Otherworld and the Third Eye.");

        helper.page("intro");
        var intro = BookTextPageModel.builder()
                .withTitle(helper.pageTitle())
                .withText(helper.pageText())
                .build();
        this.lang().add(helper.pageTitle(), "The Otherworld");
        this.lang().add(helper.pageText(),
                """
                        Hidden from mere human eyes exists another plane of existence, another *dimension* if you will, the [#](%1$s)Otherworld[#]().
                        This world is populated with entities often referred to as [#](%1$s)Demons[#]().
                        """.formatted(COLOR_PURPLE));

        helper.page("intro2");
        var intro2 = BookTextPageModel.builder()
                .withText(helper.pageText())
                .build();
        this.lang().add(helper.pageText(),
                """
                        These Demons possess a wide variety of powers and useful skills, and for centuries magicians have sought to summon them for their own gain.
                        The first step on the journey to successfully summoning such an Entity is to learn how to interact with the Otherworld.
                        """);

        helper.page("spotlight");
        var spotlight = BookSpotlightPageModel.builder()
                .withItem(Ingredient.of(OccultismItems.DATURA.get()))
                .withText(helper.pageText())
                .build();
        this.lang().add(helper.pageText(),
                """
                        %1$s is a herb that gives humans the [#](%2$s)Third Eye[#](),
                        allowing them to see where the [#](%2$s)Otherworld[#]() intersects with our own.
                        Seeds can be found **by breaking grass**.
                        **Consuming** the grown fruit activates the ability.
                        """.formatted(DEMONS_DREAM, COLOR_PURPLE));

        helper.page("harvest_effect");
        var harvestEffect = BookTextPageModel.builder()
                .withText(helper.pageText())
                .build();
        this.lang().add(helper.pageText(),
                """
                        An additional side effect of %1$s, is **the ability to interact with [#](%2$s)Otherworld[#]() materials**.
                        This is unique to %1$s, other ways to obtain [#](%2$s)Third Eye[#]() do not yield this ability.
                        While under the effect of %1$s you are able to **harvest** Otherstone as well as Otherworld trees.
                         """.formatted(DEMONS_DREAM, COLOR_PURPLE));

        helper.page("datura_screenshot");
        var datureScreenshot = BookImagePageModel.builder()
                .withImages(this.modLoc("textures/gui/book/datura_effect.png"))
                .build();
        //no text

        helper.page("note_on_spirit_fire");
        var spiritFire = BookTextPageModel.builder()
                .withText(helper.pageText())
                .build();
        this.lang().add(helper.pageText(),
                """
                        **Hint**: The otherworld materials you obtain by harvesting under the effects of[#](%2$s)Third Eye[#]() **can be obtained more easily using [](item://occultism:spirit_fire)**. Proceed with the next entry in this book to learn more about spirit fire.
                         """.formatted(DEMONS_DREAM, COLOR_PURPLE));

        return BookEntryModel.builder()
                .withId(this.modLoc(helper.category + "/" + helper.entry))
                .withName(helper.entryName())
                .withDescription(helper.entryDescription())
                .withIcon(OccultismItems.DATURA.getId().toString())
                .withLocation(entryHelper.get(icon))
                .withPages(
                        intro,
                        intro2,
                        spotlight,
                        harvestEffect,
                        datureScreenshot,
                        spiritFire);
    }

    private BookEntryModel.Builder makeSpiritFireEntry(BookLangHelper helper, EntryLocationHelper entryHelper, char icon) {
        helper.entry("spirit_fire");
        this.lang().add(helper.entryName(), "It burns!");
        this.lang().add(helper.entryDescription(), "Or does it?");

        helper.page("spotlight");
        var spotlight = BookSpotlightPageModel.builder()
                .withItem(Ingredient.of(OccultismItems.SPIRIT_FIRE.get()))
                .withText(helper.pageText())
                .build();
        this.lang().add(helper.pageText(),
                """
                        [#](%1$s)Spiritfire[#]() is a special type of fire that exists mostly in [#](%1$s)The Other Place[#]()
                        and does not harm living beings. Its special properties allow to use it to purify and convert
                        certain materials by burning them, without consuming them.
                        """.formatted(COLOR_PURPLE));

        helper.page("spirit_fire_screenshot");
        var spiritFireScreenshot = BookImagePageModel.builder()
                .withImages(this.modLoc("textures/gui/book/spiritfire_instructions.png"))
                .withText(helper.pageText())
                .build();
        this.lang().add(helper.pageText(),
                """
                        Throw [](item://occultism:datura) to the ground and light it on fire with [](item://minecraft:flint_and_steel).
                         """);

        helper.page("main_uses");
        var mainUses = BookTextPageModel.builder()
                .withText(helper.pageText())
                .build();
        this.lang().add(helper.pageText(),
                """
                        The main uses of [](item://occultism:spirit_fire) are to convert [](item://minecraft:diamond) into [](item://occultism:spirit_attuned_gem),
                        to get basic ingredients such as [](item://occultism:otherstone) and [Otherworld Saplings](item://occultism:otherworld_sapling_natural),
                        and to purify impure chalks.
                         """);

        helper.page("otherstone_recipe");
        var otherstoneRecipe = BookSpiritFireRecipePageModel.builder()
                .withRecipeId1(this.modLoc("spirit_fire/otherstone"))
                .withText(helper.pageText())
                .build();
        this.lang().add(helper.pageText(),
                """
                        An easier way to obtain [](item://occultism:otherstone) than via divination.
                              """);

        helper.page("otherworld_sapling_natural_recipe");
        var otherworldSaplingNaturalRecipe = BookSpiritFireRecipePageModel.builder()
                .withRecipeId1(this.modLoc("spirit_fire/otherworld_sapling_natural"))
                .withText(helper.pageText())
                .build();
        this.lang().add(helper.pageText(),
                """
                        An easier way to obtain [Otherworld Saplings](item://occultism:otherworld_sapling_natural) than via divination.
                              """);

        helper.page("otherworld_ashes_recipe");
        var otherworldAshesRecipe = BookSpiritFireRecipePageModel.builder()
                .withRecipeId1(this.modLoc("spirit_fire/otherworld_ashes"))
                .build();
        //no text

        helper.page("gem_recipe");
        var gemRecipe = BookSpiritFireRecipePageModel.builder()
                .withRecipeId1(this.modLoc("spirit_fire/spirit_attuned_gem"))
                .build();
        //no text


        return BookEntryModel.builder()
                .withId(this.modLoc(helper.category + "/" + helper.entry))
                .withName(helper.entryName())
                .withDescription(helper.entryDescription())
                .withIcon(OccultismItems.SPIRIT_FIRE.getId().toString())
                .withLocation(entryHelper.get(icon))
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

    private BookEntryModel.Builder makeHealingSpiritsEntry(BookLangHelper helper, EntryLocationHelper entryHelper, char icon) {
        helper.entry("healing_spirits");
        this.lang().add(helper.entryName(), "Healing Spirits");
        this.lang().add(helper.entryDescription(), "Fix up your spirit!");

        helper.page("spotlight");
        var spotlight = BookSpotlightPageModel.builder()
                .withItem(Ingredient.of(OccultismItems.DATURA.get()))
                .withText(helper.pageText())
                .build();
        this.lang().add(helper.pageText(),
                """
                        Right-click a spirit with [](item://occultism:datura) to heal it. 
                        \\
                        \\
                        This will work on **Familiars**, **Summoned Spirits** and also **Possessed Mobs**.
                        """);

        return BookEntryModel.builder()
                .withId(this.modLoc(helper.category + "/" + helper.entry))
                .withName(helper.entryName())
                .withDescription(helper.entryDescription())
                .withIcon(ForgeRegistries.ITEMS.getKey(Items.SPLASH_POTION).toString())
                .withLocation(entryHelper.get(icon))
                .withPages(
                        spotlight
                );
    }

    private BookEntryModel.Builder makeThirdEyeEntry(BookLangHelper helper, EntryLocationHelper entryHelper, char icon) {
        helper.entry("third_eye");
        this.lang().add(helper.entryName(), "The Third Eye");
        this.lang().add(helper.entryDescription(), "Do you see now?");

        helper.page("about");
        var about = BookTextPageModel.builder()
                .withTitle(helper.pageTitle())
                .withText(helper.pageText())
                .build();
        this.lang().add(helper.pageTitle(), "Third Eye");
        this.lang().add(helper.pageText(),
                """
                        The ability to see beyond the physical world is referred to as [#](%1$s)Third Eye[#]().
                        Humans do not possess such an ability to see [#](%1$s)beyond the veil[#](),
                        however with certain substances and contraptions the knowledgeable summoner can work around this limitation.
                         """.formatted(COLOR_PURPLE));

        helper.page("how_to_obtain");
        var howToObtain = BookTextPageModel.builder()
                .withText(helper.pageText())
                .build();
        this.lang().add(helper.pageText(),
                """
                        The most comfortable, and most *expensive*, way to obtain this ability, is to wear spectacles
                        infused with spirits, that *lend* their sight to the wearer.
                        A slightly more nauseating, but **very affordable** alternative is the consumption of certain herbs,
                        [%1$s](entry://occultism:dictionary_of_spirits/getting_started/demons_dream) most prominent among them.
                         """.formatted(DEMONS_DREAM));

        helper.page("otherworld_goggles");
        var otherworldGoggles = BookSpotlightPageModel.builder()
                .withItem(Ingredient.of(OccultismItems.OTHERWORLD_GOGGLES.get()))
                .withText(helper.pageText())
                .build();
        this.lang().add(helper.pageText(),
                """
                        [These goggles](entry://occultism:dictionary_of_spirits/crafting_rituals/craft_otherworld_goggles) allow to see even more hidden Otherworld blocks,
                        however they do not allow harvesting those materials.
                        Low-tier materials can be harvested by consuming [%1$s](entry://occultism:dictionary_of_spirits/getting_started/demons_dream),
                        but more valuable materials require special tools.
                                """.formatted(DEMONS_DREAM));

        return BookEntryModel.builder()
                .withId(this.modLoc(helper.category + "/" + helper.entry))
                .withName(helper.entryName())
                .withDescription(helper.entryDescription())
                .withIcon(this.modLoc("textures/mob_effect/third_eye.png").toString())
                .withLocation(entryHelper.get(icon))
                .withPages(about, howToObtain, otherworldGoggles);
    }

    private BookEntryModel.Builder makeDivinationRodEntry(BookLangHelper helper, EntryLocationHelper entryHelper, char icon) {
        helper.entry("divination_rod");
        this.lang().add(helper.entryName(), "Divination Rod");
        this.lang().add(helper.entryDescription(), "Obtaining otherworld materials");

        helper.page("intro");
        var intro = BookTextPageModel.builder()
                .withTitle(helper.pageTitle())
                .withText(helper.pageText())
                .build();
        this.lang().add(helper.pageTitle(), "Divination");
        this.lang().add(helper.pageText(),
                """
                        To make it easier to get started, the materials obtained by divination now also have crafting recipes.
                        **If you want the full experience, skip the following recipe page and move on to the
                        [divination instructions](entry://occultism:dictionary_of_spirits/getting_started/divination_rod@divination_instructions).**
                                """);

        helper.page("otherstone_recipe");
        var otherstoneRecipe = BookSpiritFireRecipePageModel.builder()
                .withRecipeId1(this.modLoc("spirit_fire/otherstone"))
                .build();
        //no text

        helper.page("otherworld_sapling_natural_recipe");
        var otherworldSaplingNaturalRecipe = BookSpiritFireRecipePageModel.builder()
                .withRecipeId1(this.modLoc("spirit_fire/otherworld_sapling_natural"))
                .withText(helper.pageText())
                .build();
        this.lang().add(helper.pageText(),
                """
                        **Beware**: the tree growing from the sapling will look like a normal oak tree.
                        You need to activate the [Third Eye](entry://occultism:dictionary_of_spirits/getting_started/demons_dream)
                        to harvest the Otherworld Logs and Leaves.
                                """);

        helper.page("divination_rod");
        var divinationRod = BookSpotlightPageModel.builder()
                .withItem(Ingredient.of(OccultismItems.DIVINATION_ROD.get()))
                .withText(helper.pageText())
                .withAnchor("divination_instructions")
                .build();
        this.lang().add(helper.pageText(),
                """
                        Otherworld materials play an important role in interacting with spirits.
                        As they are rare and not visible to the naked eye, finding them requires special tools.
                        The divination rod allows to find Otherworld materials based on their similarities to materials common to our world.
                                 """);

        helper.page("spirit_attuned_gem_recipe");
        var spiritAttunedGemRecipe = BookSpiritFireRecipePageModel.builder()
                .withRecipeId1(this.modLoc("spirit_fire/spirit_attuned_gem"))
                .build();

        helper.page("divination_rod_recipe");
        var divinationRodRecipe = BookCraftingRecipePageModel.builder()
                .withRecipeId1(this.modLoc("crafting/divination_rod"))
                .build();

        helper.page("about_divination_rod");
        var aboutDivinationRod = BookTextPageModel.builder()
                .withText(helper.pageText())
                .build();
        this.lang().add(helper.pageText(),
                """
                        The divination rod uses a spirit attuned gem attached to a wooden rod.
                        The gem resonates with the chosen material, and this movement is amplified by the wooden rod,
                        allowing to detect nearby Otherworld materials.   \s
                           \s
                           \s
                        The rod works by detecting resonance between real world and Otherworld materials.
                        Attuned the rod to a real world material, and it will find the corresponding Otherworld block.
                                 """);

        helper.page("how_to_use");
        var howToUse = BookTextPageModel.builder()
                .withTitle(helper.pageTitle())
                .withText(helper.pageText())
                .build();
        this.lang().add(helper.pageTitle(), "Use of the Rod");
        this.lang().add(helper.pageText(),
                """
                        [#](%1$s)Shift-right-click[#]() a block to attune the rod to the corresponding Otherworld block.
                        - [](item://minecraft:andesite): [](item://occultism:otherstone)
                        - [](item://minecraft:oak_wood):  [](item://occultism:otherworld_log)
                        - [](item://minecraft:oak_leaves): [](item://occultism:otherworld_leaves)
                        - [](item://minecraft:netherrack): [](item://occultism:iesnium_ore)

                        Then [#](%1$s)right-click[#]() and hold until the rod animation finishes.""".formatted(COLOR_PURPLE));

        helper.page("how_to_use2");
        var howToUse2 = BookTextPageModel.builder()
                .withText(helper.pageText())
                .build();
        this.lang().add(helper.pageText(),
                """
                        After the animation finishes, the closest **found block will be highlighted
                        with white lines and can be seen through other blocks**.
                        Additionally you can watch the crystals for hints: a white crystal indicates no target blocks found,
                        a fully purple block means the found block is nearby. Mixes between white and purple show
                        that the target is rather far away.""");

        helper.page("how_to_use3");
        var howToUse3 = BookTextPageModel.builder()
                .withText(helper.pageText())
                .build();
        this.lang().add(helper.pageText(),
                """
                        [#](%1$s)Right-clicking[#]() without holding after a successful search will show the last found target block again.
                        \\
                        \\
                        If the mod *"Theurgy"* is installed the rod will not highlight the target block, but instead send a particle effect in the direction of the target block.
                        """.formatted(COLOR_PURPLE));

        helper.page("divination_rod_screenshots");
        var divinationRodScreenshots = BookImagePageModel.builder()
                .withImages(
                        this.modLoc("textures/gui/book/rod_far.png"),
                        this.modLoc("textures/gui/book/rod_mid.png"),
                        this.modLoc("textures/gui/book/rod_near.png")
                )
                .withText(helper.pageText())
                .build();
        this.lang().add(helper.pageText(),
                """
                        White means nothing was found.
                        The more purple you see, the closer you are.
                        """);

        helper.page("otherworld_groves");
        var otherworldGroves = BookTextPageModel.builder()
                .withTitle(helper.pageTitle())
                .withText(helper.pageText())
                .build();
        this.lang().add(helper.pageTitle(), "Otherworld Groves");
        this.lang().add(helper.pageText(),
                """
                        Otherworld Groves are lush, overgrown caves, with [#](%1$s)Otherworld Trees[#](),
                        and walls of [](item://occultism:otherstone), and represent the fastest way to get everything one
                        needs to get set up as a summoner.
                        To find them, attune your divination rod to Otherworld leaves
                        or logs, as unlike Otherstone, they only spawn in these groves.
                         """.formatted(COLOR_PURPLE));

        helper.page("otherworld_groves_2");
        var otherworldGroves2 = BookTextPageModel.builder()
                .withText(helper.pageText())
                .build();
        this.lang().add(helper.pageText(),
                """
                        **Hint:** In the Overworld, look **down**.
                          """);

        helper.page("otherworld_trees");
        var otherworldTrees = BookTextPageModel.builder()
                .withTitle(helper.pageTitle())
                .withText(helper.pageText())
                .build();
        this.lang().add(helper.pageTitle(), "Otherworld Trees");
        this.lang().add(helper.pageText(),
                """
                        Otherworld trees grow naturally in Otherworld Groves. To the naked eye they appear as oak trees,
                        but to the Third Eye they reveal their true nature.   \s
                        **Important:** Otherworld Saplings can only be obtained by breaking the leaves manually, naturally only oak saplings drop.
                         """);

        helper.page("otherworld_trees_2");
        var otherworldTrees2 = BookTextPageModel.builder()
                .withText(helper.pageText())
                .build();
        this.lang().add(helper.pageText(),
                """
                        Trees grown from Stable Otherworld Saplings as obtained from spirit traders do not have that limitation.
                         """);

        return BookEntryModel.builder()
                .withId(this.modLoc(helper.category + "/" + helper.entry))
                .withName(helper.entryName())
                .withDescription(helper.entryDescription())
                .withIcon(OccultismItems.DIVINATION_ROD.getId().toString())
                .withLocation(entryHelper.get(icon))
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
                        otherworldGroves,
                        otherworldGroves2,
                        otherworldTrees,
                        otherworldTrees2);
    }

    private BookEntryModel.Builder makeTheurgyDivinationRodsEntry(BookLangHelper helper, EntryLocationHelper entryHelper, char icon) {
        helper.entry("theurgy_divination_rod");

        this.lang().add(helper.entryName(), "More Divination Rods");
        this.lang().add(helper.entryDescription(), "Finding other ores and resources.");

        helper.page("intro");
        var intro = BookSpotlightPageModel.builder()
                .withItem(Ingredient.of(ItemRegistry.DIVINATION_ROD_T1.get()))
                .withText(helper.pageText())
                .build();

        this.lang().add(helper.pageText(),
                """
                        While the [](item://occultism:divination_rod) is a great tool for finding [#](%1$s)Otherworld Materials[#](), it would be useful to have a way to find *all other* ores and resources as well.
                        \\
                        \\
                        This is where the Theurgy Divination Rod comes in.
                                """.formatted(COLOR_PURPLE));

        helper.page("recipe_rod");
        var recipeRod = BookCraftingRecipePageModel.builder()
                .withRecipeId1("theurgy:crafting/shaped/divination_rod_t1")
                .build();

        helper.page("more_info");
        var moreInfo = BookTextPageModel.builder()
                .withTitle(helper.pageTitle())
                .withText(helper.pageText())
                .build();

        this.lang().add(helper.pageTitle(), "More Information");
        this.lang().add(helper.pageText(),
                """
                        To find out more about the Theurgy Divination Rod, check out *"The Hermetica"*, the Guidebook for Theurgy.
                        [This Entry](entry://theurgy:the_hermetica/getting_started/divination_rod) has more information about the Theurgy Divination Rod.
                        """);

        helper.page("recipe_hermetica");
        var recipeHermetica = BookCraftingRecipePageModel.builder()
                .withRecipeId1("theurgy:crafting/shapeless/the_hermetica")
                .build();


        return BookEntryModel.builder()
                .withId(this.modLoc(helper.category + "/" + helper.entry))
                .withName(helper.entryName())
                .withDescription(helper.entryDescription())
                .withIcon(ItemRegistry.DIVINATION_ROD_T1.get())
                .withLocation(entryHelper.get(icon))
                .withPages(
                        intro,
                        recipeRod,
                        moreInfo,
                        recipeHermetica
                );
    }

    private BookEntryModel.Builder makeCandleEntry(BookLangHelper helper, EntryLocationHelper entryHelper, char icon) {
        helper.entry("candle");
        this.lang().add(helper.entryName(), "Candles");
        this.lang().add(helper.entryDescription(), "Let there be light!");

        helper.page("intro");
        var intro = BookSpotlightPageModel.builder()
                .withItem(Ingredient.of(OccultismBlocks.CANDLE_WHITE.get()))
                .withText(helper.pageText())
                .build();
        this.lang().add(helper.pageText(),
                """
                        Candles provide stability to rituals and are an important part of almost all pentacles.
                        **Candles also act like bookshelves for enchantment purposes.**
                        \\
                        \\
                        Candles from Minecraft and other Mods may be used in place of Occultism candles.
                            """);

        helper.page("tallow");
        var tallow = BookSpotlightPageModel.builder()
                .withItem(Ingredient.of(OccultismItems.TALLOW.get()))
                .withText(helper.pageText())
                .build();
        this.lang().add(helper.pageText(),
                """
                        Key ingredient for candles. Kill large animals like pigs, cows or sheep with a [](item://occultism:butcher_knife)
                        to harvest [](item://occultism:tallow).
                            """);

        helper.page("cleaver_recipe");
        var cleaverRecipe = BookCraftingRecipePageModel.builder()
                .withRecipeId1(this.modLoc("crafting/butcher_knife"))
                .build();
        //no text

        helper.page("candle_recipe");
        var candleRecipe = BookCraftingRecipePageModel.builder()
                .withRecipeId1(this.modLoc("crafting/candle"))
                .build();
        //no text

        return BookEntryModel.builder()
                .withId(this.modLoc(helper.category + "/" + helper.entry))
                .withName(helper.entryName())
                .withDescription(helper.entryDescription())
                .withIcon(OccultismBlocks.CANDLE_WHITE.getId().toString())
                .withLocation(entryHelper.get(icon))
                .withPages(
                        intro,
                        tallow,
                        cleaverRecipe,
                        candleRecipe
                );
    }

    private BookEntryModel.Builder makeRitualPrepChalkEntry(BookLangHelper helper, EntryLocationHelper entryHelper, char icon) {
        helper.entry("ritual_prep_chalk");
        this.lang().add(helper.entryName(), "Ritual Preparations: Chalks");
        this.lang().add(helper.entryDescription(), "Signs to find them, Signs to bring them all, and in the darkness bind them.");

        helper.page("intro");
        var intro = BookTextPageModel.builder()
                .withTitle(helper.pageTitle())
                .withText(helper.pageText())
                .build();
        this.lang().add(helper.pageTitle(), "Ritual Preparations: Chalks");
        this.lang().add(helper.pageText(),
                """
                        To summon spirits from the [#](%1$s)Other Place[#]() in *relative* safety,
                        you need to draw a fitting pentacle using chalk to contain their powers.
                         """.formatted(COLOR_PURPLE));

        helper.page("white_chalk");
        var whiteChalkSpotlight = BookSpotlightPageModel.builder()
                .withItem(Ingredient.of(OccultismItems.CHALK_WHITE.get()))
                .withText(helper.pageText())
                .withAnchor("white_chalk")
                .build();
        this.lang().add(helper.pageText(),
                """
                        White chalk is used to draw the most basic pentacles, such as for our first ritual.
                        \\
                        \\
                        More powerful summonings require appropriate more advanced chalk, see [Chalks](entry://occultism:dictionary_of_spirits/getting_started/chalks) for more information.
                            """);

        helper.page("burnt_otherstone_recipe");
        var burntOtherstoneRecipe = BookSmeltingRecipePageModel.builder()
                .withRecipeId1(this.modLoc("smelting/burnt_otherstone"))
                .build();
        //no text

        helper.page("otherworld_ashes_recipe");
        var otherworldAshesRecipe = BookSpiritFireRecipePageModel.builder()
                .withRecipeId1(this.modLoc("spirit_fire/otherworld_ashes"))
                .build();
        //no text
        helper.page("impure_white_chalk_recipe");
        var impureWhiteChalkRecipe = BookCraftingRecipePageModel.builder()
                .withRecipeId1(this.modLoc("crafting/chalk_white_impure"))
                .build();
        //no text

        helper.page("white_chalk_recipe");
        var whiteChalkRecipe = BookSpiritFireRecipePageModel.builder()
                .withRecipeId1(this.modLoc("spirit_fire/chalk_white"))
                .build();
        //no text

        helper.page("usage");
        var usage = BookTextPageModel.builder()
                .withTitle(helper.pageTitle())
                .withText(helper.pageText())
                .build();
        this.lang().add(helper.pageTitle(), "Usage");
        this.lang().add(helper.pageText(),
                """
                        Right-click on a block with the chalk to draw a single glyph. For decorative purposes you can repeatedly click a block to cycle through glyphs. The shown glyph does not matter for the ritual, only the color.
                         """.formatted(COLOR_PURPLE));

        return BookEntryModel.builder()
                .withId(this.modLoc(helper.category + "/" + helper.entry))
                .withName(helper.entryName())
                .withDescription(helper.entryDescription())
                .withIcon(OccultismItems.CHALK_WHITE.getId().toString())
                .withLocation(entryHelper.get(icon))
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

    private BookEntryModel.Builder makeRitualPrepBowlEntry(BookLangHelper helper, EntryLocationHelper entryHelper, char icon) {
        helper.entry("ritual_prep_bowl");
        this.lang().add(helper.entryName(), "Ritual Preparations: Sacrificial Bowls");
        this.lang().add(helper.entryDescription(), "There is no power without sacrifice.");

        helper.page("sacrificial_bowl");
        var sacrificialBowlSpotlight = BookSpotlightPageModel.builder()
                .withItem(Ingredient.of(OccultismBlocks.SACRIFICIAL_BOWL.get()))
                .withTitle(helper.pageTitle())
                .withText(helper.pageText())
                .build();
        this.lang().add(helper.pageTitle(), "Ritual Preparations: Sacrificial Bowls");
        this.lang().add(helper.pageText(),
                """
                        These bowls are used to place the items we will sacrifice as part of a ritual and you will need a handful of them.
                        Note: Their exact placement in the ritual does not matter - just keep them within 8 blocks horizontally of the pentacle center!
                             """);

        helper.page("sacrificial_bowl_recipe");
        var sacrificialBowlRecipe = BookCraftingRecipePageModel.builder()
                .withRecipeId1(this.modLoc("crafting/sacrificial_bowl"))
                .build();
        //no text


        helper.page("golden_sacrificial_bowl");
        var goldenSacrificialBowlSpotlight = BookSpotlightPageModel.builder()
                .withItem(Ingredient.of(OccultismBlocks.GOLDEN_SACRIFICIAL_BOWL.get()))
                .withText(helper.pageText())
                .build();
        this.lang().add(helper.pageText(),
                """
                        Once everything has been set up and you are ready to start, this special sacrificial bowl is used to activate the ritual by [#](%1$s)right-clicking[#]() it with the activation item,
                        usually a [Book of Binding](entry://getting_started/books_of_binding).
                             """.formatted(COLOR_PURPLE));

        helper.page("golden_sacrificial_bowl_recipe");
        var goldenSacrificialBowlRecipe = BookCraftingRecipePageModel.builder()
                .withRecipeId1(this.modLoc("crafting/golden_sacrificial_bowl"))
                .build();
        //no text

        return BookEntryModel.builder()
                .withId(this.modLoc(helper.category + "/" + helper.entry))
                .withName(helper.entryName())
                .withDescription(helper.entryDescription())
                .withIcon(OccultismBlocks.GOLDEN_SACRIFICIAL_BOWL.getId().toString())
                .withLocation(entryHelper.get(icon))
                .withPages(
                        sacrificialBowlSpotlight,
                        sacrificialBowlRecipe,
                        goldenSacrificialBowlSpotlight,
                        goldenSacrificialBowlRecipe
                );
    }

    private BookEntryModel.Builder makeBooksOfBindingEntry(BookLangHelper helper, EntryLocationHelper entryHelper, char icon) {
        helper.entry("books_of_binding");
        this.lang().add(helper.entryName(), "Books of Binding");
        this.lang().add(helper.entryDescription(), "Or how to identify your spirit");

        helper.page("intro");
        var intro = BookTextPageModel.builder()
                .withTitle(helper.pageTitle())
                .withText(helper.pageText())
                .build();
        this.lang().add(helper.pageTitle(), "Books of Binding");
        this.lang().add(helper.pageText(),
                """
                        To call forth a spirit, a [#](%1$s)Book of Binding[#]() must be used in the ritual.
                        There is a type of book corresponding to each type (or tier) of spirit.
                        To identify a spirit to summon, it's name must be written in the [#](%1$s)Book of Binding[#](), resulting in a [#](%1$s)Bound Book of Binding[#]() that can be used in the ritual.
                         """.formatted(COLOR_PURPLE));

        helper.page("intro2");
        var intro2 = BookTextPageModel.builder()
                .withText(helper.pageText())
                .build();
        this.lang().add(helper.pageText(),
                """
                        **Note:** *The spirit names are eye candy only*, that means they are not relevant for the recipe. As long as you have the right spirit type in your book of binding it can be used.
                         """.formatted(COLOR_PURPLE));

        helper.page("purified_ink_recipe");
        var purifiedInkRecipe = BookSpiritFireRecipePageModel.builder()
                .withRecipeId1(this.modLoc("spirit_fire/purified_ink"))
                .withText(helper.pageText())
                .build();
        this.lang().add(helper.pageText(),
                """
                        In order to craft [#](%1$s)Books of Binding[#]() to summon spirits, you need purified ink. Simply drop any black dye into [](item://occultism:spirit_fire) to purify it.
                            """.formatted(COLOR_PURPLE));

        helper.page("book_of_binding_foliot_recipe");
        var bookOfBindingFoliotRecipe = BookCraftingRecipePageModel.builder()
                .withRecipeId1(this.modLoc("crafting/book_of_binding_foliot"))
                .withText(helper.pageText())
                .build();
        this.lang().add(helper.pageText(),
                """
                        Craft a book of binding that will be used to call forth a [#](%1$s)Foliot[#]() spirit.
                           """.formatted(COLOR_PURPLE));

        helper.page("book_of_binding_bound_foliot_recipe");
        var bookOfBindingBoundFoliotRecipe = BookCraftingRecipePageModel.builder()
                .withRecipeId1(this.modLoc("crafting/book_of_binding_bound_foliot"))
                .withText(helper.pageText())
                .build();
        this.lang().add(helper.pageText(),
                """
                        Add the name of the spirit to summon to your book of binding by crafting it with the Dictionary of Spirits. The Dictionary will not be used up.
                           """);

        helper.page("book_of_binding_djinni_recipe");
        var bookOfBindingDjinniRecipe = BookCraftingRecipePageModel.builder()
                .withRecipeId1(this.modLoc("crafting/book_of_binding_djinni"))
                .withRecipeId2(this.modLoc("crafting/book_of_binding_bound_djinni"))
                .build();
        //no text

        helper.page("book_of_binding_afrit_recipe");
        var bookOfBindingAfritRecipe = BookCraftingRecipePageModel.builder()
                .withRecipeId1(this.modLoc("crafting/book_of_binding_afrit"))
                .withRecipeId2(this.modLoc("crafting/book_of_binding_bound_afrit"))
                .build();
        //no text

        helper.page("book_of_binding_marid_recipe");
        var bookOfBindingMaritRecipe = BookCraftingRecipePageModel.builder()
                .withRecipeId1(this.modLoc("crafting/book_of_binding_marid"))
                .withRecipeId2(this.modLoc("crafting/book_of_binding_bound_marid"))
                .build();
        //no text

        return BookEntryModel.builder()
                .withId(this.modLoc(helper.category + "/" + helper.entry))
                .withName(helper.entryName())
                .withDescription(helper.entryDescription())
                .withIcon(OccultismItems.BOOK_OF_BINDING_BOUND_FOLIOT.getId().toString())
                .withLocation(entryHelper.get(icon))
                .withPages(
                        intro,
                        intro2,
                        purifiedInkRecipe,
                        bookOfBindingFoliotRecipe,
                        bookOfBindingBoundFoliotRecipe,
                        bookOfBindingDjinniRecipe,
                        bookOfBindingAfritRecipe,
                        bookOfBindingMaritRecipe
                );
    }

    private BookEntryModel.Builder makeBooksOfCallingEntry(BookLangHelper helper, EntryLocationHelper entryHelper, char icon) {
        helper.entry("books_of_calling");
        this.lang().add(helper.entryName(), "Books of Calling");
        this.lang().add(helper.entryDescription(), "Telling your spirits what to do");

        helper.page("intro");
        var intro = BookTextPageModel.builder()
                .withTitle(helper.pageTitle())
                .withText(helper.pageText())
                .build();
        this.lang().add(helper.pageTitle(), "Books of Calling");
        this.lang().add(helper.pageText(),
                """
                        Books of Calling allow to control a summoned spirit, and to store it to prevent essence decay or move it more easily. 
                        \\
                        \\
                        Only spirits that require precise instructions - such as a work area or drop-off storage - come with a book of calling.
                        """);

        helper.page("usage");
        var usage = BookTextPageModel.builder()
                .withTitle(helper.pageTitle())
                .withText(helper.pageText())
                .build();
        this.lang().add(helper.pageTitle(), "Usage");
        this.lang().add(helper.pageText(),
                """
                        - [#](%1$s)Right-click[#]() air to open the configuration screen
                        - [#](%1$s)Shift-right-click[#]() a block to apply the action selected in the configuration screen
                        - [#](%1$s)Shift-right-click[#]() a spirit to capture it (must be of the same type)
                        - [#](%1$s)Right-click[#]() with a book with a captured spirit to release it
                        """.formatted(COLOR_PURPLE));

        helper.page("obtaining");
        var obtaining = BookTextPageModel.builder()
                .withTitle(helper.pageTitle())
                .withText(helper.pageText())
                .build();
        this.lang().add(helper.pageTitle(), "How to obtain Books of Calling");
        this.lang().add(helper.pageText(),
                """
                        If a summoned spirit supports the use of a Book of Calling, the summoning ritual will automatically spawn a book in the world alongside the spirit.
                        \\
                        \\
                        If you **lose the book**, there are also crafting recipes that just provide the book (without summoning a spirit).
                        """.formatted(COLOR_PURPLE));

        helper.page("obtaining2");
        var obtaining2 = BookTextPageModel.builder()
                .withText(helper.pageText())
                .build();
        this.lang().add(helper.pageText(),
                """
                        The recipes can be found in this book or via JEI.
                        \\
                        \\
                        [#](%1$s)Shift-right-click[#]() the spirit with the crafted book to assign it.
                        """.formatted(COLOR_PURPLE));

        helper.page("storage");
        var storage = BookTextPageModel.builder()
                .withTitle(helper.pageTitle())
                .withText(helper.pageText())
                .build();
        this.lang().add(helper.pageTitle(), "Storing Spirits");
        this.lang().add(helper.pageText(),
                """
                        To store spirits that do not have a fitting book of calling, you can use a [Soul Gem](entry://crafting_rituals/craft_soul_gem).
                        Soul gems are much more versatile and allow to store almost all types of entities even animals and monsters, but not players or bosses.
                        """);

        return BookEntryModel.builder()
                .withId(this.modLoc(helper.category + "/" + helper.entry))
                .withName(helper.entryName())
                .withDescription(helper.entryDescription())
                .withIcon(OccultismItems.BOOK_OF_CALLING_DJINNI_MANAGE_MACHINE.getId().toString())
                .withLocation(entryHelper.get(icon))
                .withPages(
                        intro,
                        usage,
                        obtaining,
                        obtaining2,
                        storage
                );
    }

    private BookEntryModel.Builder makeRitualEntry(BookLangHelper helper, EntryLocationHelper entryHelper, char icon) {
        helper.entry("first_ritual");
        this.lang.add(helper.entryName(), "First Ritual");
        this.lang.add(helper.entryDescription(), "We're actually getting started now!");

        helper.page("intro");
        var intro = BookTextPageModel.builder()
                .withTitle(helper.pageTitle())
                .withText(helper.pageText())
                .build();
        this.lang.add(helper.pageTitle(), "The Ritual (tm)");
        this.lang.add(helper.pageText(),
                """
                        These pages will walk the gentle reader through the process of the [first ritual](entry://summoning_rituals/summon_crusher_t1) step by step.
                        \\
                        We **start** by placing the [](item://occultism:golden_sacrificial_bowl) and drawing the appropriate pentacle, [Aviar's Circle](entry://pentacles/summon_foliot) as seen on the left around it.
                          """.formatted(COLOR_PURPLE));

        helper.page("multiblock");
        var multiblock = BookMultiblockPageModel.builder()
                .withMultiblockId(this.modLoc("summon_foliot"))
                .withText(helper.pageText())
                .build();
        this.lang.add(helper.pageText(),
                """
                        Only the color and location of the chalk marks is relevant, not the glyph/sign.
                          """.formatted(COLOR_PURPLE));

        helper.page("bowl_text");
        var bowlText = BookTextPageModel.builder()
                .withTitle(helper.pageTitle())
                .withText(helper.pageText())
                .build();
        this.lang.add(helper.pageTitle(), "Sacrificial Bowls");
        this.lang.add(helper.pageText(),
                """
                        Next, place *at least* 4 [Sacrificial Bowls](item://occultism:sacrificial_bowl) close to the pentacle.
                        \\
                        \\
                        They must be placed **anywhere** within 8 blocks of the central [](item://occultism:golden_sacrificial_bowl). **The exact location does not matter.**
                          """.formatted(COLOR_PURPLE));

        helper.page("bowl_placement");
        var bowlPlacementImage = BookImagePageModel.builder()
                .withImages(this.modLoc("textures/gui/book/bowl_placement.png"))
                .withBorder(true)
                .withText(helper.pageText())
                .build();
        this.lang.add(helper.pageText(),
                """
                        Possible locations for the sacrificial bowls.
                          """.formatted(COLOR_PURPLE));

        helper.page("ritual_text");
        var ritualText = BookTextPageModel.builder()
                .withTitle(helper.pageTitle())
                .withText(helper.pageText())
                .build();
        this.lang.add(helper.pageTitle(), "Placing Ingredients");
        this.lang.add(helper.pageText(),
                """
                        Now it is time to place the ingredients you see on the next page in the (regular, not golden) sacrificial bowls. The ingredients will be consumed from the bowls as the ritual progresses.
                          """.formatted(COLOR_PURPLE));

        helper.page("ritual_recipe");
        var ritualRecipe = BookRitualRecipePageModel.builder()
                .withRecipeId1(this.modLoc("ritual/summon_foliot_crusher"))
                .build();
        //no text

        helper.page("pentacle_link_hint");
        var pentacleLinkHint = BookTextPageModel.builder()
                .withTitle(helper.pageTitle())
                .withText(helper.pageText())
                .build();
        this.lang.add(helper.pageTitle(), "A Note about Ritual Recipes");
        this.lang.add(helper.pageText(),
                """
                        Ritual recipe pages, such as the previous pageshow not only the ingredients, but also the pentacle that you need to draw with chalk in order to use the ritual.
                        \\
                        \\
                        **To show the pentacle, click the blue link** at the center top of the ritual page. You can then even preview it in-world.
                          """.formatted(COLOR_PURPLE));

        helper.page("start_ritual");
        var startRitualText = BookTextPageModel.builder()
                .withTitle(helper.pageTitle())
                .withText(helper.pageText())
                .build();
        this.lang.add(helper.pageTitle(), "Let there be ... spirits!");
        this.lang.add(helper.pageText(),
                """
                        Finally, [#](%1$s)right-click[#]() the [](item://occultism:golden_sacrificial_bowl) with the **bound** book of binding you created before and wait until the crusher spawns.
                        \\
                        \\
                        Now all that remains is to drop appropriate ores near the crusher and wait for it to turn it into dust.
                          """.formatted(COLOR_PURPLE));


        return BookEntryModel.builder()
                .withId(this.modLoc(helper.category + "/" + helper.entry))
                .withName(helper.entryName())
                .withDescription(helper.entryDescription())
                .withIcon(OccultismItems.PENTACLE.getId().toString())
                .withLocation(entryHelper.get(icon))
                .withPages(
                        intro,
                        multiblock,
                        bowlText,
                        bowlPlacementImage,
                        ritualText,
                        ritualRecipe,
                        pentacleLinkHint,
                        startRitualText
                );
    }

    private BookEntryModel.Builder makeBrushEntry(BookLangHelper helper, EntryLocationHelper entryHelper, char icon) {
        helper.entry("brush");
        this.lang().add(helper.entryName(), "Brush");
        this.lang().add(helper.entryDescription(), "Cleaning up!");

        helper.page("intro");
        var intro = BookTextPageModel.builder()
                .withTitle(helper.pageTitle())
                .withText(helper.pageText())
                .build();
        this.lang().add(helper.pageTitle(), "Next Steps");
        this.lang().add(helper.pageText(),
                """
                        Chalk is a pain to clean up, by [#](%1$s)right-clicking[#]() with a brush you can remove it from the world much more easily.
                          """.formatted(COLOR_PURPLE));

        helper.page("brushRecipe");
        var brushRecipe = BookCraftingRecipePageModel.builder()
                .withRecipeId1(this.modLoc("crafting/brush"))
                .build();
        //no text

        return BookEntryModel.builder()
                .withId(this.modLoc(helper.category + "/" + helper.entry))
                .withName(helper.entryName())
                .withDescription(helper.entryDescription())
                .withIcon(OccultismItems.BRUSH.getId().toString())
                .withLocation(entryHelper.get(icon))
                .withPages(
                        intro,
                        brushRecipe
                );
    }

    private BookEntryModel.Builder makeMoreRitualsEntry(BookLangHelper helper, EntryLocationHelper entryHelper, char icon) {
        helper.entry("more_rituals");
        this.lang().add(helper.entryName(), "More Rituals");
        this.lang().add(helper.entryDescription(), "Ready for new challenges?");

        return BookEntryModel.builder()
                .withId(this.modLoc(helper.category + "/" + helper.entry))
                .withName(helper.entryName())
                .withDescription(helper.entryDescription())
                .withIcon("occultism:textures/gui/book/robe.png")
                .withLocation(entryHelper.get(icon))
                .withEntryBackground(1, 1) //silver background and wavey entry shape
                .withCategoryToOpen(this.modLoc("rituals"));
    }

    private BookEntryModel.Builder makeGreyParticlesEntry(BookLangHelper helper, EntryLocationHelper entryHelper, char icon) {
        helper.entry("grey_particles");
        this.lang().add(helper.entryName(), "Grey particles?");
        this.lang().add(helper.entryDescription(), "What to do when a ritual seems stuck!");

        helper.page("text");
        var text = BookTextPageModel.builder()
                .withTitle(helper.pageTitle())
                .withText(helper.pageText())
                .build();
        this.lang().add(helper.pageTitle(), "Ritual stuck?");
        this.lang().add(helper.pageText(),
                """
                        If a ritual appears stuck - no items being consumed - you should see grey particles around the [](item://occultism:golden_sacrificial_bowl). If this is the case the ritual requires you to either [use a specific item](entry://rituals/item_use) or [sacrifice a specific mob](entry://rituals/sacrifice).
                        \\
                        \\
                        Find the ritual in the [Rituals](category://rituals) category and check for instructions.
                          """);

        return BookEntryModel.builder()
                .withId(this.modLoc(helper.category + "/" + helper.entry))
                .withName(helper.entryName())
                .withDescription(helper.entryDescription())
                .withIcon(ForgeRegistries.ITEMS.getKey(Items.GRAY_DYE).toString())
                .withLocation(entryHelper.get(icon))
                .withPages(text);
    }

    private BookEntryModel.Builder makeSpiritsSubcategoryEntry(BookLangHelper helper, EntryLocationHelper entryHelper, char icon) {
        helper.entry("spirits");
        this.lang().add(helper.entryName(), "About Spirits");
        this.lang().add(helper.entryDescription(), "Learn more about Spirits.");

        return BookEntryModel.builder()
                .withId(this.modLoc(helper.category + "/" + helper.entry))
                .withName(helper.entryName())
                .withIcon("occultism:textures/gui/book/spirits.png")
                .withCategoryToOpen(this.modLoc("spirits"))
                .withEntryBackground(1, 1) //silver background and wavey entry shape
                .withLocation(entryHelper.get(icon));
    }

    private BookEntryModel.Builder makeChalksEntry(BookLangHelper helper, EntryLocationHelper entryHelper, char icon) {
        helper.entry("chalks");
        this.lang().add(helper.entryName(), "More Chalks");
        this.lang().add(helper.entryDescription(), "Better chalks for better rituals!");

        helper.page("intro");
        var intro = BookTextPageModel.builder()
                .withTitle(helper.pageTitle())
                .withText(helper.pageText())
                .build();
        this.lang().add(helper.pageTitle(), "More Chalks");
        this.lang().add(helper.pageText(),
                """
                        For more advanced rituals the basic [White Chalk](entry://occultism:dictionary_of_spirits/getting_started/ritual_prep_chalk) is not sufficient. Instead chalks made from more arcane materials are required.
                        """);

        helper.page("impure_gold_chalk_recipe");
        var impureGoldChalkRecipe = BookCraftingRecipePageModel.builder()
                .withRecipeId1(this.modLoc("crafting/chalk_gold_impure"))
                .build();
        //no text

        helper.page("gold_chalk_recipe");
        var goldChalkRecipe = BookSpiritFireRecipePageModel.builder()
                .withRecipeId1(this.modLoc("spirit_fire/chalk_gold"))
                .build();
        //no text

        helper.page("impure_purple_chalk_recipe");
        var impurePurpleChalkRecipe = BookCraftingRecipePageModel.builder()
                .withRecipeId1(this.modLoc("crafting/chalk_purple_impure"))
                .withText(helper.pageText())
                .build();
        this.lang().add(helper.pageText(),
                """
                        You do not need to visit the [#](%1$s)The End[#]() to obtain Endstone. You can summon a [Possessed Endermite](entry://possession_rituals/possess_endermite) which has a high chance to drop it.
                             """.formatted(COLOR_PURPLE));

        helper.page("purple_chalk_recipe");
        var purpleChalkRecipe = BookSpiritFireRecipePageModel.builder()
                .withRecipeId1(this.modLoc("spirit_fire/chalk_purple"))
                .build();
        //no text

        helper.page("impure_red_chalk_recipe");
        var impureRedChalkRecipe = BookCraftingRecipePageModel.builder()
                .withRecipeId1(this.modLoc("crafting/chalk_red_impure"))
                .build();
        //no text

        helper.page("red_chalk_recipe");
        var redChalkRecipe = BookSpiritFireRecipePageModel.builder()
                .withRecipeId1(this.modLoc("spirit_fire/chalk_red"))
                .build();
        //no text

        helper.page("afrit_essence");
        var afritEssenceSpotlight = BookSpotlightPageModel.builder()
                .withItem(Ingredient.of(OccultismItems.AFRIT_ESSENCE.get()))
                .withText(helper.pageText())
                .build();
        this.lang().add(helper.pageText(),
                """
                        To obtain the essence of an [#](%1$s)Afrit[#]() for [](item://occultism:chalk_red) you need to [summon and kill an Unbound Afrit](entry://summoning_rituals/afrit_essence).
                        """.formatted(COLOR_PURPLE));

        return BookEntryModel.builder()
                .withId(this.modLoc(helper.category + "/" + helper.entry))
                .withName(helper.entryName())
                .withDescription(helper.entryDescription())
                .withIcon(OccultismItems.CHALK_GOLD.getId().toString())
                .withLocation(entryHelper.get(icon))
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

    private BookEntryModel.Builder makeOtherworldGogglesEntry(BookLangHelper helper, EntryLocationHelper entryHelper, char icon) {
        helper.entry("otherworld_goggles");
        this.lang().add(helper.entryName(), "Otherworld Goggles");
        this.lang().add(helper.entryDescription(), "Say no to drugs!");

        helper.page("spotlight");
        var spotlight = BookSpotlightPageModel.builder()
                .withItem(Ingredient.of(OccultismItems.OTHERWORLD_GOGGLES.get()))
                .withText(helper.pageText())
                .build();
        this.lang().add(helper.pageText(),
                """
                        The [](item://occultism:otherworld_goggles) are what advanced summoners use to see the [#](%1$s)Otherworld[#](), to avoid the negative side effects of [](entry://occultism:dictionary_of_spirits/getting_started/demons_dream).
                        \\
                        \\
                        Making your first pair of these is seen by many as a rite of passage.
                        """.formatted(COLOR_PURPLE));

        helper.page("crafting");
        var crafting = BookTextPageModel.builder()
                .withTitle(helper.pageTitle())
                .withText(helper.pageText())
                .build();
        this.lang().add(helper.pageTitle(), "Crafting Goggles");
        this.lang().add(helper.pageText(),
                """
                        Crafting these goggles is a multi-step process described in detail in the Entry about [Crafting Otherworld Goggles](entry://crafting_rituals/craft_otherworld_goggles).
                        """.formatted(COLOR_PURPLE));

        return BookEntryModel.builder()
                .withId(this.modLoc(helper.category + "/" + helper.entry))
                .withName(helper.entryName())
                .withDescription(helper.entryDescription())
                .withIcon(OccultismItems.OTHERWORLD_GOGGLES.getId().toString())
                .withLocation(entryHelper.get(icon))
                .withPages(
                        spotlight,
                        crafting
                );
    }

    private BookEntryModel.Builder makeInfusedPickaxeEntry(BookLangHelper helper, EntryLocationHelper entryHelper, char icon) {
        helper.entry("infused_pickaxe");
        this.lang().add(helper.entryName(), "Infused Pickaxe");
        this.lang().add(helper.entryDescription(), "Tackling Otherworld Ores");

        helper.page("spotlight");
        var spotlight = BookSpotlightPageModel.builder()
                .withItem(Ingredient.of(OccultismItems.INFUSED_PICKAXE.get()))
                .withText(helper.pageText())
                .build();
        this.lang().add(helper.pageText(),
                """
                        Beyond [](item://occultism:otherworld_log) and [](item://occultism:otherstone) there are also otherworld materials that require special tools to harvest. 
                        \\
                        \\
                        This pickaxe is rather brittle, but it will do the job.
                        """);

        helper.page("gem_recipe");
        var gemRecipe = BookSpiritFireRecipePageModel.builder()
                .withRecipeId1(this.modLoc("spirit_fire/spirit_attuned_gem"))
                .withText(helper.pageText())
                .build();
        this.lang().add(helper.pageText(),
                """
                        These gems, when infused with a spirit, can be used to interact with Otherword materials and are the key to crafting the pickaxe.
                        """);

        helper.page("head_recipe");
        var headRecipe = BookCraftingRecipePageModel.builder()
                .withRecipeId1(this.modLoc("crafting/spirit_attuned_pickaxe_head"))
                .build();
        //no text

        helper.page("crafting");
        var crafting = BookTextPageModel.builder()
                .withTitle(helper.pageTitle())
                .withText(helper.pageText())
                .build();
        this.lang().add(helper.pageTitle(), "Crafting");
        this.lang().add(helper.pageText(),
                """
                        After preparing the raw materials, the pickaxe needs to be infused with a spirit.
                        \\
                        \\
                        Follow the instructions at [Craft Infuse Pickaxe](entry://crafting_rituals/craft_infused_pickaxe)
                        """.formatted(COLOR_PURPLE));

        return BookEntryModel.builder()
                .withId(this.modLoc(helper.category + "/" + helper.entry))
                .withName(helper.entryName())
                .withDescription(helper.entryDescription())
                .withIcon(OccultismItems.INFUSED_PICKAXE.getId().toString())
                .withLocation(entryHelper.get(icon))
                .withPages(
                        spotlight,
                        gemRecipe,
                        headRecipe,
                        crafting
                );
    }

    private BookEntryModel.Builder makeIesniumEntry(BookLangHelper helper, EntryLocationHelper entryHelper, char icon) {
        helper.entry("iesnium");
        this.lang().add(helper.entryName(), "Iesnium Ore");
        this.lang().add(helper.entryDescription(), "Myterious metals ...");

        helper.page("spotlight");
        var spotlight = BookSpotlightPageModel.builder()
                .withItem(Ingredient.of(OccultismBlocks.IESNIUM_ORE.get()))
                .withText(helper.pageText())
                .build();
        this.lang().add(helper.pageText(),
                """
                        This is a rare metal that, to the naked eye, looks like [](item://minecraft:netherrack) and cannot be mined with a regular pickaxe.
                        \\
                        \\
                        When mined with the correct tools, it can be used to craft powerful items (you will learn more about that later).
                             """.formatted(COLOR_PURPLE));

        helper.page("where");
        var where = BookTextPageModel.builder()
                .withTitle(helper.pageTitle())
                .withText(helper.pageText())
                .build();
        this.lang().add(helper.pageTitle(), "Where to find it");
        this.lang().add(helper.pageText(),
                """
                        Like Netherrack, Iesnium can be found in the Nether. In order to **see** it, you need to wear [Otherworld Goggles](entry://getting_started/otherworld_goggles).
                        \\
                        \\
                        To make searching for it simpler, attune a [Divination Rod](entry://getting_started/divination_rod) to it and righ-click and hold in the nether until it highlights a nearby block, which will hold the ore.
                             """.formatted(COLOR_PURPLE));

        helper.page("how");
        var how = BookTextPageModel.builder()
                .withTitle(helper.pageTitle())
                .withText(helper.pageText())
                .build();
        this.lang().add(helper.pageTitle(), "How to mine it");
        this.lang().add(helper.pageText(),
                """
                        Iesnium can only be mined with the [Infused Pickaxe](entry://getting_started/infused_pickaxe) or an [](item://occultism:iesnium_pickaxe) (about which you will learn later).
                        \\
                        \\
                        After identifying a block that holds Iesnium, you can mine it with the pickaxe you created in the previous step.
                             """.formatted(COLOR_PURPLE));

        helper.page("processing");
        var processing = BookTextPageModel.builder()
                .withTitle(helper.pageTitle())
                .withText(helper.pageText())
                .build();
        this.lang().add(helper.pageTitle(), "Processing");
        this.lang().add(helper.pageText(),
                """
                        Iesnium Ore, after mining, can be smelted directly into ingots, or placed down. When placed, it will not turn back into it's netherrack form. Consequently it can also be mined with any pickaxe then. This visible form of the Ore, when mined, will drop [](item://occultism:raw_iesnium).
                             """.formatted(COLOR_PURPLE));

        helper.page("uses");
        var uses = BookTextPageModel.builder()
                .withTitle(helper.pageTitle())
                .withText(helper.pageText())
                .build();
        this.lang().add(helper.pageTitle(), "Uses");
        this.lang().add(helper.pageText(),
                """
                        Iesnium can be used to craft an improved pickaxe, spirit lamps, and other powerful items. Follow the progress in this book to learn more about it.
                             """.formatted(COLOR_PURPLE));

        return BookEntryModel.builder()
                .withId(this.modLoc(helper.category + "/" + helper.entry))
                .withName(helper.entryName())
                .withDescription(helper.entryDescription())
                .withIcon(OccultismBlocks.IESNIUM_ORE.getId().toString())
                .withLocation(entryHelper.get(icon))
                .withPages(
                        spotlight,
                        where,
                        how,
                        processing,
                        uses
                );
    }

    private BookEntryModel.Builder makeIesniumPickaxeEntry(BookLangHelper helper, EntryLocationHelper entryHelper, char icon) {
        helper.entry("iesnium_pickaxe");
        this.lang().add(helper.entryName(), "Iesnium Pickaxe");
        this.lang().add(helper.entryDescription(), "A more durable otherworld-appropriate pickaxe");

        helper.page("spotlight");
        var spotlight = BookSpotlightPageModel.builder()
                .withItem(Ingredient.of(OccultismItems.IESNIUM_PICKAXE.get()))
                .withText(helper.pageText())
                .build();
        this.lang().add(helper.pageText(),
                """
                        Like the [Infused Pickaxe](entry://getting_started/infused_pickaxe), this pickaxe can be used to mine Tier 2 Otherworld Materials such as [](item://occultism:iesnium_ore). As it is made from metal, instead of brittle [](item://occultism:spirit_attuned_gem), it is very durable and can be used for a long time.
                             """.formatted(COLOR_PURPLE));

        helper.page("crafting");
        var crafting = BookCraftingRecipePageModel.builder()
                .withRecipeId1(this.modLoc("crafting/iesnium_pickaxe"))
                .build();
        //no text

        return BookEntryModel.builder()
                .withId(this.modLoc(helper.category + "/" + helper.entry))
                .withName(helper.entryName())
                .withDescription(helper.entryDescription())
                .withIcon(OccultismItems.IESNIUM_PICKAXE.getId().toString())
                .withLocation(entryHelper.get(icon))
                .withPages(
                        spotlight,
                        crafting
                );
    }

    private BookEntryModel.Builder makeMagicLampsEntry(BookLangHelper helper, EntryLocationHelper entryHelper, char icon) {
        helper.entry("magic_lamps");
        this.lang().add(helper.entryName(), "Magic Lamps");
        this.lang().add(helper.entryDescription(), "Three wishes? Close, but not quite..");

        helper.page("spotlight");
        var spotlight = BookSpotlightPageModel.builder()
                .withItem(Ingredient.of(OccultismItems.MAGIC_LAMP_EMPTY.get()))
                .withTitle(helper.pageTitle())
                .withText(helper.pageText())
                .build();
        this.lang().add(helper.pageTitle(), "Magic Lamps");
        this.lang().add(helper.pageText(),
                """
                        Magic Lamps can be used to keep spirits safe from [#](%1$s)Essence Decay[#](), while still having access to some of their powers. Most commonly they are used to access a [#](%1$s)Mining Dimension[#]() and act as (*lag free*) [#](%1$s)Void Miners[#]().
                             """.formatted(COLOR_PURPLE));

        helper.page("crafting");
        var crafting = BookCraftingRecipePageModel.builder()
                .withRecipeId1(this.modLoc("crafting/magic_lamp_empty"))
                .build();
        //no text

        return BookEntryModel.builder()
                .withId(this.modLoc(helper.category + "/" + helper.entry))
                .withName(helper.entryName())
                .withDescription(helper.entryDescription())
                .withIcon(OccultismItems.MAGIC_LAMP_EMPTY.getId().toString())
                .withLocation(entryHelper.get(icon))
                .withPages(
                        spotlight,
                        crafting
                );
    }

    private BookEntryModel.Builder makeSpiritMinersEntry(BookLangHelper helper, EntryLocationHelper entryHelper, char icon) {
        helper.entry("spirit_miners");
        this.lang().add(helper.entryName(), "Spirit Miners");
        this.lang().add(helper.entryDescription(), "It's Free Real Estate (-> Resources)");

        helper.page("spotlight");
        var spotlight = BookSpotlightPageModel.builder()
                .withItem(Ingredient.of(OccultismItems.MINER_FOLIOT_UNSPECIALIZED.get()))
                .withTitle(helper.pageTitle())
                .withText(helper.pageText())
                .build();
        this.lang().add(helper.pageTitle(), "Spirit Miners");
        this.lang().add(helper.pageText(),
                """
                        By summoning a spirit into a Magic Lamp and placing it in a [Dimensional Mineshaft (see next step)](entry://getting_started/mineshaft) it can be made to mine for you in a [#](%1$s)Mining Dimension[#](). This is a great way to get resources without having to go mining in the overworld (or other dimesions) yourself.
                             """.formatted(COLOR_PURPLE));

        helper.page("crafting");
        var crafting = BookTextPageModel.builder()
                .withTitle(helper.pageTitle())
                .withText(helper.pageText())
                .build();
        this.lang().add(helper.pageTitle(), "Crafting");
        this.lang().add(helper.pageText(),
                """
                        See [Foliot Miner](entry://crafting_rituals/craft_foliot_miner) and the subsequent entries for information on how to craft spirit miners.
                             """.formatted(COLOR_PURPLE));

        return BookEntryModel.builder()
                .withId(this.modLoc(helper.category + "/" + helper.entry))
                .withName(helper.entryName())
                .withDescription(helper.entryDescription())
                .withIcon(OccultismItems.MINER_FOLIOT_UNSPECIALIZED.getId().toString())
                .withLocation(entryHelper.get(icon))
                .withPages(
                        spotlight,
                        crafting
                );
    }

    private BookEntryModel.Builder makeMineshaftEntry(BookLangHelper helper, EntryLocationHelper entryHelper, char icon) {
        helper.entry("mineshaft");
        this.lang().add(helper.entryName(), "Dimensional Mineshaft");
        this.lang().add(helper.entryDescription(), "Ethically questionable, but very profitable");

        helper.page("spotlight");
        var spotlight = BookSpotlightPageModel.builder()
                .withItem(Ingredient.of(OccultismBlocks.DIMENSIONAL_MINESHAFT.get()))
                .withText(helper.pageText())
                .build();
        this.lang().add(helper.pageText(),
                """
                        This block acts as a portal, for spirits only, to the [#](%1$s)Mining Dimension[#](). Place a Magic Lamp with a Miner Spirit in it, to make it mine for you.
                             """.formatted(COLOR_PURPLE));

        helper.page("crafting");
        var crafting = BookTextPageModel.builder()
                .withTitle(helper.pageTitle())
                .withText(helper.pageText())
                .build();
        this.lang().add(helper.pageTitle(), "Crafting");
        this.lang().add(helper.pageText(),
                """
                        See [Dimensional Mineshaft](entry://crafting_rituals/craft_dimensional_mineshaft) in the [Binding Rituals](category://crafting_rituals) Category.
                             """.formatted(COLOR_PURPLE));

        return BookEntryModel.builder()
                .withId(this.modLoc(helper.category + "/" + helper.entry))
                .withName(helper.entryName())
                .withDescription(helper.entryDescription())
                .withIcon(OccultismBlocks.DIMENSIONAL_MINESHAFT.getId().toString())
                .withLocation(entryHelper.get(icon))
                .withPages(
                        spotlight,
                        crafting
                );
    }

    private BookEntryModel.Builder makeStorageEntry(BookLangHelper helper, EntryLocationHelper entryHelper, char icon) {
        helper.entry("storage");
        this.lang().add(helper.entryName(), "Magic Storage");
        this.lang().add(helper.entryDescription(), "Looking for much much much more storage? Look no further!");

        return BookEntryModel.builder()
                .withId(this.modLoc(helper.category + "/" + helper.entry))
                .withName(helper.entryName())
                .withDescription(helper.entryDescription())
                .withIcon(OccultismBlocks.STORAGE_CONTROLLER.getId().toString())
                .withLocation(entryHelper.get(icon))
                .withEntryBackground(1, 1) //silver background and wavey entry shape
                .withCategoryToOpen(this.modLoc("storage"));
    }

    private BookEntryModel.Builder makePossessionRitualsEntry(BookLangHelper helper, EntryLocationHelper entryHelper, char icon) {
        helper.entry("possession_rituals");
        this.lang().add(helper.entryName(), "Possession Rituals");
        this.lang().add(helper.entryDescription(), "A different way to get rare drops ...");

        helper.page("intro");
        var intro = BookTextPageModel.builder()
                .withTitle(helper.pageTitle())
                .withText(helper.pageText())
                .build();
        this.lang().add(helper.pageTitle(), "Possession Rituals");
        this.lang().add(helper.pageText(),
                """
                        Possessed mobs are controlled by spirits, allowing the summoner to determine some of their properties. They usually have **high drop rates** for rare drops, but are generally harder to kill.
                        \\
                        \\
                        You probably will want to start by summoning a [Possessed Endermite](entry://possession_rituals/possess_endermite) to get [](item://minecraft:end_stone) to craft [Advanced Chalks](entry://getting_started/chalks).
                             """.formatted(COLOR_PURPLE));

        helper.page("more");
        var more = BookTextPageModel.builder()
                .withTitle(helper.pageTitle())
                .withText(helper.pageText())
                .build();
        this.lang().add(helper.pageTitle(), "More Information");
        this.lang().add(helper.pageText(),
                """
                        To find out more about Possession Rituals, see the [Possession Rituals](category://possession_rituals) Category.
                             """.formatted(COLOR_PURPLE));

        return BookEntryModel.builder()
                .withId(this.modLoc(helper.category + "/" + helper.entry))
                .withName(helper.entryName())
                .withDescription(helper.entryDescription())
                .withIcon("occultism:textures/gui/book/possession.png")
                .withLocation(entryHelper.get(icon))
                .withEntryBackground(1, 1) //silver background and wavey entry shape
                .withPages(
                        intro,
                        more
                );
    }

    private BookEntryModel.Builder makeFamiliarRitualsEntry(BookLangHelper helper, EntryLocationHelper entryHelper, char icon) {
        helper.entry("familiar_rituals");
        this.lang().add(helper.entryName(), "Familiar Rituals");
        this.lang().add(helper.entryDescription(), "Personal helpers that provide buffs or fight for you");

        helper.page("intro");
        var intro = BookTextPageModel.builder()
                .withTitle(helper.pageTitle())
                .withText(helper.pageText())
                .build();
        this.lang().add(helper.pageTitle(), "Familiar Rituals");
        this.lang().add(helper.pageText(),
                """
                        Familiars provide a variety of bonus effects, such as feather falling, water breathing, jump boosts and more, and may also assist you in combat.
                        \\
                        \\
                        Store them in a [Familiar Ring](entry://crafting_rituals/craft_familiar_ring) to equip them as a curio.
                             """.formatted(COLOR_PURPLE));

        helper.page("more");
        var more = BookTextPageModel.builder()
                .withTitle(helper.pageTitle())
                .withText(helper.pageText())
                .build();
        this.lang().add(helper.pageTitle(), "More Information");
        this.lang().add(helper.pageText(),
                """
                        To find more about Familiars, see the [Familiar Rituals](category://familiar_rituals) Category.
                             """.formatted(COLOR_PURPLE));

        return BookEntryModel.builder()
                .withId(this.modLoc(helper.category + "/" + helper.entry))
                .withName(helper.entryName())
                .withDescription(helper.entryDescription())
                .withIcon("occultism:textures/gui/book/parrot.png")
                .withLocation(entryHelper.get(icon))
                .withEntryBackground(1, 1) //silver background and wavey entry shape
                .withPages(
                        intro,
                        more
                );
    }

    private BookEntryModel.Builder makeSummoningRitualsEntry(BookLangHelper helper, EntryLocationHelper entryHelper, char icon) {
        helper.entry("summoning_rituals");
        this.lang().add(helper.entryName(), "Summoning Rituals");
        this.lang().add(helper.entryDescription(), "Spirit helpers for your daily work life");

        helper.page("intro");
        var intro = BookTextPageModel.builder()
                .withTitle(helper.pageTitle())
                .withText(helper.pageText())
                .build();
        this.lang().add(helper.pageTitle(), "Summoning Rituals");
        this.lang().add(helper.pageText(),
                """
                        Summoning Rituals allow you to summon spirits to work for you. Unlike familiars, they are not personally bound to you, meaning they will not follow you around, but they will perform various work tasks for you. In fact the first ritual you performed, the [Foliot Crusher](entry://getting_started/first_ritual), was a summoning ritual.
                             """.formatted(COLOR_PURPLE));

        helper.page("more");
        var more = BookTextPageModel.builder()
                .withTitle(helper.pageTitle())
                .withText(helper.pageText())
                .build();
        this.lang().add(helper.pageTitle(), "More Information");
        this.lang().add(helper.pageText(),
                """
                        To find more about Summoning Rituals, see the [Summoning Rituals](category://summoning_rituals) Category.
                             """.formatted(COLOR_PURPLE));

        return BookEntryModel.builder()
                .withId(this.modLoc(helper.category + "/" + helper.entry))
                .withName(helper.entryName())
                .withDescription(helper.entryDescription())
                .withIcon("occultism:textures/gui/book/summoning.png")
                .withLocation(entryHelper.get(icon))
                .withEntryBackground(1, 1) //silver background and wavey entry shape
                .withPages(
                        intro,
                        more
                );
    }

    private BookEntryModel.Builder makeCraftingRitualsEntry(BookLangHelper helper, EntryLocationHelper entryHelper, char icon) {
        helper.entry("crafting_rituals");
        this.lang().add(helper.entryName(), "Infusion Rituals");
        this.lang().add(helper.entryDescription(), "Infuse spirits into items to create powerful tools");

        helper.page("intro");
        var intro = BookTextPageModel.builder()
                .withTitle(helper.pageTitle())
                .withText(helper.pageText())
                .build();
        this.lang().add(helper.pageTitle(), "Infusion Rituals");
        this.lang().add(helper.pageText(),
                """
                        Infusion rituals are all about crafting powerful items, by binding ("infusing") spirits into objects.The spirits will provide special functionality to the items.
                             """.formatted(COLOR_PURPLE));

        helper.page("more");
        var more = BookTextPageModel.builder()
                .withTitle(helper.pageTitle())
                .withText(helper.pageText())
                .build();
        this.lang().add(helper.pageTitle(), "More Information");
        this.lang().add(helper.pageText(),
                """
                        To find more about Infusing items, see the [Infusion Rituals](category://crafting_rituals) Category.
                             """.formatted(COLOR_PURPLE));

        return BookEntryModel.builder()
                .withId(this.modLoc(helper.category + "/" + helper.entry))
                .withName(helper.entryName())
                .withDescription(helper.entryDescription())
                .withIcon("occultism:textures/gui/book/infusion.png")
                .withLocation(entryHelper.get(icon))
                .withEntryBackground(1, 1) //silver background and wavey entry shape
                .withPages(
                        intro,
                        more
                );
    }

    //endregion

    private BookCategoryModel makeSpiritsSubcategory(BookLangHelper helper) {
        helper.category("spirits");
        this.lang().add(helper.categoryName(), "Spirits");
        var entryHelper = ModonomiconAPI.get().getEntryLocationHelper();
        entryHelper.setMap(
                "___________________________",
                "___________________________",
                "___<_0_n_u_w_______________",
                "___________________________",
                "_____d_____________________",
                "___________________________",
                "___________________________"
        );

        var overview = this.makeSpiritsOverviewEntry(helper, entryHelper, '0');
        var returnToGettingStarted = this.makeReturnToGettingStartedEntry(helper, entryHelper, '<');
        returnToGettingStarted.withParent(BookEntryParentModel.builder().withEntryId(overview.id).build());
        returnToGettingStarted.withCondition(BookTrueConditionModel.builder().build());

        var essenceDecay = this.makeEssenceDecayEntry(helper, entryHelper, 'd');
        essenceDecay.withParent(BookEntryParentModel.builder().withEntryId(overview.id).build());

        var trueNames = this.makeTrueNamesEntry(helper, entryHelper, 'n');
        trueNames.withParent(BookEntryParentModel.builder().withEntryId(overview.id).build());

        var unboundSpirits = this.makeUnboundSpiritsEntry(helper, entryHelper, 'u');
        unboundSpirits.withParent(BookEntryParentModel.builder().withEntryId(trueNames.id).build());

        var wildHunt = this.makeWildHuntEntry(helper, entryHelper, 'w');
        wildHunt.withParent(BookEntryParentModel.builder().withEntryId(unboundSpirits.id).build());

        return BookCategoryModel.create(this.modLoc(helper.category), helper.categoryName())
                .withIcon("occultism:textures/gui/book/spirits.png")
                .withShowCategoryButton(false)
                .withEntries(
                        overview.build(),
                        returnToGettingStarted.build(),
                        trueNames.build(),
                        essenceDecay.build(),
                        unboundSpirits.build(),
                        wildHunt.build()
                );
    }

    private BookEntryModel.Builder makeReturnToGettingStartedEntry(BookLangHelper helper, EntryLocationHelper entryHelper, char icon) {
        helper.entry("return_to_getting_started");
        this.lang().add(helper.entryName(), "Return to getting started");

        return BookEntryModel.builder()
                .withId(this.modLoc(helper.category + "/" + helper.entry))
                .withName(helper.entryName())
                .withIcon(OccultismItems.DICTIONARY_OF_SPIRITS_ICON.getId().toString())
                .withCategoryToOpen(this.modLoc("getting_started"))
                .withEntryBackground(1, 2)
                .withLocation(entryHelper.get(icon));
    }

    private BookEntryModel.Builder makeSpiritsOverviewEntry(BookLangHelper helper, EntryLocationHelper entryHelper, char icon) {
        helper.entry("overview");
        this.lang().add(helper.entryName(), "On Spirits");

        helper.page("intro");
        var intro = BookTextPageModel.builder()
                .withTitle(helper.pageTitle())
                .withText(helper.pageText())
                .build();
        this.lang().add(helper.pageTitle(), "On Spirits");
        this.lang().add(helper.pageText(),
                """
                        [#](%1$s)Spirit[#](), commonly referred to also as [#](%1$s)Demon[#](), is a general term for a variety of supernatural entities usually residing in [#](%1$s)The Other Place[#](), a plane of existence entirely separate from our own.
                        """.formatted(COLOR_PURPLE));

        helper.page("shapes");
        var shapes = BookTextPageModel.builder()
                .withTitle(helper.pageTitle())
                .withText(helper.pageText())
                .build();
        this.lang().add(helper.pageTitle(), "Shapes");
        this.lang().add(helper.pageText(),
                """
                        When in our world Spirits can take a variety of forms, by morphing their essence into [#](%1$s)Chosen Forms[#](). Alternatively, they can inhabit objects or even living beings.
                          """.formatted(COLOR_PURPLE));

        helper.page("tiers");
        var tiers = BookTextPageModel.builder()
                .withTitle(helper.pageTitle())
                .withText(helper.pageText())
                .build();
        this.lang().add(helper.pageTitle(), "Types of Spirits");
        this.lang().add(helper.pageText(),
                """
                        There are four major "ranks" of spirits identified by researchers, but there are a myriad spirits below and in between these ranks, and some great entities of terrible power, referred to only as [#](%1$s)Greater Spirits[#](), that are beyond classification.
                         """.formatted(COLOR_PURPLE));

        helper.page("foliot");
        var foliot = BookTextPageModel.builder()
                .withTitle(helper.pageTitle())
                .withText(helper.pageText())
                .build();
        this.lang().add(helper.pageTitle(), "Foliot");
        this.lang().add(helper.pageText(),
                """
                        The lowest identified class of spirit. Equipped with some intelligence and a modicum of power they are most often used for manual labor or minor artifacts.
                         """.formatted(COLOR_PURPLE));

        helper.page("djinni");
        var djinni = BookTextPageModel.builder()
                .withTitle(helper.pageTitle())
                .withText(helper.pageText())
                .build();
        this.lang().add(helper.pageTitle(), "Djinni");
        this.lang().add(helper.pageText(),
                """
                        The most commonly summoned class. There is a great variety of Djinni, differing both in intelligence and power. Djinni can be used for a variety of task, ranging from higher artifacts over possession of living beings to carrying out tasks in their Chosen Form.
                         """.formatted(COLOR_PURPLE));

        helper.page("afrit");
        var afrit = BookTextPageModel.builder()
                .withTitle(helper.pageTitle())
                .withText(helper.pageText())
                .build();
        this.lang().add(helper.pageTitle(), "Afrit");
        this.lang().add(helper.pageText(),
                """
                        Even more powerful than Djinni, Afrit are used for the creation of major artifacts and the possession of powerful beings.
                         """.formatted(COLOR_PURPLE));


        helper.page("marid");
        var marid = BookTextPageModel.builder()
                .withTitle(helper.pageTitle())
                .withText(helper.pageText())
                .build();
        this.lang().add(helper.pageTitle(), "Marid");
        this.lang().add(helper.pageText(),
                """
                        The strongest identified class of spirits. Due to their power and vast intellect attempting a summoning is extremely dangerous and usually only carried out by the most experienced summoners, and even then usually in groups.
                         """.formatted(COLOR_PURPLE));

        helper.page("greater_spirits");
        var greaterSpirits = BookTextPageModel.builder()
                .withTitle(helper.pageTitle())
                .withText(helper.pageText())
                .build();
        this.lang().add(helper.pageTitle(), "Greater Spirits");
        this.lang().add(helper.pageText(),
                """
                        Spirits of power so great it is beyond measure. No summons have been attempted in living memory, and records of summonings in ancient times are mostly considered apocryphal.
                         """.formatted(COLOR_PURPLE));


        return BookEntryModel.builder()
                .withId(this.modLoc(helper.category + "/" + helper.entry))
                .withName(helper.entryName())
                .withDescription(helper.entryDescription())
                .withIcon("occultism:textures/gui/book/spirits.png")
                .withLocation(entryHelper.get(icon))
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

    private BookEntryModel.Builder makeEssenceDecayEntry(BookLangHelper helper, EntryLocationHelper entryHelper, char icon) {
        helper.entry("essence_decay");
        this.lang().add(helper.entryName(), "Essence Decay");
        this.lang().add(helper.entryDescription(), "Even the immortal are not immune to time.");

        helper.page("intro");
        var intro = BookTextPageModel.builder()
                .withTitle(helper.pageTitle())
                .withText(helper.pageText())
                .build();
        this.lang().add(helper.pageTitle(), "Essence Decay");
        this.lang().add(helper.pageText(),
                """
                        When residing in our plane of existence, spirits experience [#](%1$s)Essence Decay[#](), the slow rot of their "body". The more powerful the spirit, the slower the decay, but only the most powerful can stop it entirely. Once fully decayed they are returned to [#](%1$s)The Other Place[#]() and can only be re-summoned once fully recovered.
                        """.formatted(COLOR_PURPLE));

        helper.page("countermeasures");
        var countermeasures = BookTextPageModel.builder()
                .withTitle(helper.pageTitle())
                .withText(helper.pageText())
                .build();
        this.lang().add(helper.pageTitle(), "Countermeasures");
        this.lang().add(helper.pageText(),
                """
                        The summoner can slow or even stop essence decay by binding the spirit into an object, or summoning it into a living being. Additionally the pentacle used can influence the effects of essence decay to a degree.
                        """.formatted(COLOR_PURPLE));


        helper.page("affected_spirits");
        var affectedSpirits = BookTextPageModel.builder()
                .withTitle(helper.pageTitle())
                .withText(helper.pageText())
                .build();
        this.lang().add(helper.pageTitle(), "Affected Spirits");
        this.lang().add(helper.pageText(),
                """
                        Only tier 1 spirits are affected by essence decay, by default. All higher tiers are immune and will not despawn. Modpacks may modify this behaviour.
                              """.formatted(COLOR_PURPLE));

        return BookEntryModel.builder()
                .withId(this.modLoc(helper.category + "/" + helper.entry))
                .withName(helper.entryName())
                .withDescription(helper.entryDescription())
                .withIcon(ForgeRegistries.ITEMS.getKey(Items.ROTTEN_FLESH).toString())
                .withLocation(entryHelper.get(icon))
                .withPages(
                        intro,
                        countermeasures,
                        affectedSpirits
                );
    }

    private BookEntryModel.Builder makeTrueNamesEntry(BookLangHelper helper, EntryLocationHelper entryHelper, char icon) {
        helper.entry("true_names");
        this.lang().add(helper.entryName(), "True Names");
        this.lang().add(helper.entryDescription(), "How to call spirits.");

        helper.page("intro");
        var intro = BookTextPageModel.builder()
                .withTitle(helper.pageTitle())
                .withText(helper.pageText())
                .build();
        this.lang().add(helper.pageTitle(), "True Names");
        this.lang().add(helper.pageText(),
                """
                        To summon a spirit the magician needs to know their [#](%1$s)True Name[#](). By calling the true naming during the summoning ritual the Spirit is drawn forth from [#](%1$s)The Other Place[#]() and forced to do the summoners bidding.
                        \\
                        \\
                        *It should be noted, that it does not matter which spirit name is used in summoning, only the spirit tier is relevant.*
                         """.formatted(COLOR_PURPLE));

        helper.page("finding_names");
        var findingNames = BookTextPageModel.builder()
                .withTitle(helper.pageTitle())
                .withText(helper.pageText())
                .build();
        this.lang().add(helper.pageTitle(), "Finding Names");
        this.lang().add(helper.pageText(),
                """
                        In ancient summoners had to research and experiment to find [#](%1$s)True Names[#](). Some spirits can be convinced to share their knowledge of true names of other demons, either by promising a swift return to [#](%1$s)The Other Place[#](), or by more ... *persuasive* measures.
                        """.formatted(COLOR_PURPLE));

        helper.page("using_names");
        var usingNames = BookTextPageModel.builder()
                .withTitle(helper.pageTitle())
                .withText(helper.pageText())
                .build();
        this.lang().add(helper.pageTitle(), "Using Names to Summon a Spirit");
        this.lang().add(helper.pageText(),
                """
                        For your convenience, in this work you will find the known names of spirits of all 4 ranks, as well as some beyond that. To summon a spirit, copy their name from this book into the appropriate book of binding, then use this bound book of binding to activate a ritual.
                         """.formatted(COLOR_PURPLE));

        return BookEntryModel.builder()
                .withId(this.modLoc(helper.category + "/" + helper.entry))
                .withName(helper.entryName())
                .withDescription(helper.entryDescription())
                .withIcon(ForgeRegistries.ITEMS.getKey(Items.WRITABLE_BOOK).toString())
                .withLocation(entryHelper.get(icon))
                .withPages(
                        intro,
                        findingNames,
                        usingNames
                );
    }

    private BookEntryModel.Builder makeUnboundSpiritsEntry(BookLangHelper helper, EntryLocationHelper entryHelper, char icon) {
        helper.entry("unbound_spirits");
        this.lang().add(helper.entryName(), "Unbound Spirits");
        this.lang().add(helper.entryDescription(), "Try not to lose your spirits!");

        helper.page("intro");
        var intro = BookTextPageModel.builder()
                .withTitle(helper.pageTitle())
                .withText(helper.pageText())
                .build();
        this.lang().add(helper.pageTitle(), "Unbound Spirits");
        this.lang().add(helper.pageText(),
                """
                        Generally spirits are summoned [#](%1$s)bound[#](), which refers to any condition that keeps them under control of the summoner. A side effect of binding spells is that part of the spirit remains in [#](%1$s)The Other Place[#](), robbing them of large portions of the power, but at the same time also protecting their essence from foreign access in this world.
                        """.formatted(COLOR_PURPLE));

        helper.page("unbound");
        var unbound = BookTextPageModel.builder()
                .withTitle(helper.pageTitle())
                .withText(helper.pageText())
                .build();
        this.lang().add(helper.pageTitle(), "Forego the Leash");
        this.lang().add(helper.pageText(),
                """
                        In order to access a spirit's essence, or unleash it's full destructive power, it needs to be summoned [#](%1$s)unbound[#](). Unbound summonings use pentacles that are intentionally incomplete or unstable, allowing to call on the spirit, but not putting any constraints on it.
                        """.formatted(COLOR_PURPLE));

        helper.page("unbound2");
        var unbound2 = BookTextPageModel.builder()
                .withTitle(helper.pageTitle())
                .withText(helper.pageText())
                .build();
        this.lang().add(helper.pageTitle(), "Beware!");
        this.lang().add(helper.pageText(),
                """
                        The lack of restraints when summoning spirits unbound makes these rituals incredibly dangerous, but you may find that the rewards are worth the risk - and often there is no way around them to achieve certain results.
                        """.formatted(COLOR_PURPLE));

        helper.page("essence");
        var essence = BookTextPageModel.builder()
                .withTitle(helper.pageTitle())
                .withText(helper.pageText())
                .build();
        this.lang().add(helper.pageTitle(), "Spirit Essence");
        this.lang().add(helper.pageText(),
                """
                        Unbound summonings are the only way to obtain [Afrit Essence](entry://summoning_rituals/afrit_essence), a powerful substance required for crafting [](item://occultism:chalk_red) which is used for the most powerful binding pentacles.
                        """.formatted(COLOR_PURPLE));

        return BookEntryModel.builder()
                .withId(this.modLoc(helper.category + "/" + helper.entry))
                .withName(helper.entryName())
                .withDescription(helper.entryDescription())
                .withIcon("occultism:textures/gui/book/unbound_spirits.png")
                .withLocation(entryHelper.get(icon))
                .withPages(
                        intro,
                        unbound,
                        unbound2,
                        essence
                );
    }

    private BookEntryModel.Builder makeWildHuntEntry(BookLangHelper helper, EntryLocationHelper entryHelper, char icon) {
        helper.entry("wild_hunt");
        this.lang().add(helper.entryName(), "The Wild Hunt");
        this.lang().add(helper.entryDescription(), "You better watch out, you better not cry ...");
        helper.page("intro");
        var intro = BookTextPageModel.builder()
                .withTitle(helper.pageTitle())
                .withText(helper.pageText())
                .build();
        this.lang().add(helper.pageTitle(), "The Wild Hunt");
        this.lang().add(helper.pageText(),
                """
                        A group of legendary Greater Spirits, usually appearing in the form of wither skeletons, with their skeleton minions. The Greater Spirits are bound to their minions in such fashion that they are virtually invulnerable until their minions have been sent back to [#](%1$s)The Other Place[#]().
                        """.formatted(COLOR_PURPLE));

        helper.page("wither_skull");
        var witherSkull = BookTextPageModel.builder()
                .withTitle(helper.pageTitle())
                .withText(helper.pageText())
                .build();
        this.lang().add(helper.pageTitle(), "Wither Skeleton Skulls");
        this.lang().add(helper.pageText(),
                """
                        While it is incredibly dangerous to call on the Wild Hunt, some summoners have been known to do so for quick access to the rare wither skeleton skulls they are known to leave behind. Summoning the wild hunt is described in detail on the page on obtaining [Wither Skeleton Skulls](entry://summoning_rituals/wither_skull).
                        """.formatted(COLOR_PURPLE));

        return BookEntryModel.builder()
                .withId(this.modLoc(helper.category + "/" + helper.entry))
                .withName(helper.entryName())
                .withDescription(helper.entryDescription())
                .withIcon("occultism:textures/gui/book/wild_hunt.png")
                .withLocation(entryHelper.get(icon))
                .withPages(
                        intro,
                        witherSkull
                );
    }

    //endregion

    private BookCategoryModel makePentaclesCategory(BookLangHelper helper) {
        helper.category("pentacles");

        var entryHelper = ModonomiconAPI.get().getEntryLocationHelper();
        entryHelper.setMap(
                "____________________",
                "__p_a_b_c_d_e_f_____", //paraphernalia, summon foliot, summon djinni, summon wild afrit, summon afrit, summon marid, summon wild greater spirit
                "____________________",
                "__o___g_h_i_________", //overview, possess foliot, possess djinni, possess afrit
                "____________________",
                "__u_j_k_l_m_________", //uses of chalks, craft foliot, craft djinni, craft afrit, craft marid
                "____________________"
        );

        var overview = this.makePentaclesOverviewEntry(helper, entryHelper, 'o');
        var paraphernalia = this.makeParaphernaliaEntry(helper, entryHelper, 'p');
        paraphernalia.withParent(BookEntryParentModel.builder().withEntryId(overview.id).build());
        var chalkUses = this.makeChalkUsesEntry(helper, entryHelper, 'u');
        chalkUses.withParent(BookEntryParentModel.builder().withEntryId(overview.id).build());

        var summonFoliot = this.makeSummonFoliotEntry(helper, entryHelper, 'a');
        summonFoliot.withParent(BookEntryParentModel.builder().withEntryId(overview.id).build());
        var summonDjinni = this.makeSummonDjinniEntry(helper, entryHelper, 'b');
        summonDjinni.withParent(BookEntryParentModel.builder().withEntryId(summonFoliot.id).build());
        var summonWildAfrit = this.makeSummonWildAfritEntry(helper, entryHelper, 'c');
        summonWildAfrit.withParent(BookEntryParentModel.builder().withEntryId(summonDjinni.id).build());
        var summonAfrit = this.makeSummonAfritEntry(helper, entryHelper, 'd');
        summonAfrit.withParent(BookEntryParentModel.builder().withEntryId(summonWildAfrit.id).build());
        var summonMarid = this.makeSummonMaridEntry(helper, entryHelper, 'e');
        summonMarid.withParent(BookEntryParentModel.builder().withEntryId(summonAfrit.id).build());
        var summonWildGreaterSpirit = this.makeSummonWildGreaterSpiritEntry(helper, entryHelper, 'f');
        summonWildGreaterSpirit.withParent(BookEntryParentModel.builder().withEntryId(summonMarid.id).build());

        var possessFoliot = this.makePossessFoliotEntry(helper, entryHelper, 'g');
        possessFoliot.withParent(BookEntryParentModel.builder().withEntryId(overview.id).build());
        var possessDjinni = this.makePossessDjinniEntry(helper, entryHelper, 'h');
        possessDjinni.withParent(BookEntryParentModel.builder().withEntryId(possessFoliot.id).build());
        var possessAfrit = this.makePossessAfritEntry(helper, entryHelper, 'i');
        possessAfrit.withParent(BookEntryParentModel.builder().withEntryId(possessDjinni.id).build());

        var craftFoliot = this.makeCraftFoliotEntry(helper, entryHelper, 'j');
        craftFoliot.withParent(BookEntryParentModel.builder().withEntryId(overview.id).build());
        var craftDjinni = this.makeCraftDjinniEntry(helper, entryHelper, 'k');
        craftDjinni.withParent(BookEntryParentModel.builder().withEntryId(craftFoliot.id).build());
        var craftAfrit = this.makeCraftAfritEntry(helper, entryHelper, 'l');
        craftAfrit.withParent(BookEntryParentModel.builder().withEntryId(craftDjinni.id).build());
        var craftMarid = this.makeCraftMaridEntry(helper, entryHelper, 'm');
        craftMarid.withParent(BookEntryParentModel.builder().withEntryId(craftAfrit.id).build());

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

        return BookCategoryModel.create(this.modLoc(helper.category), helper.categoryName())
                .withIcon(OccultismItems.PENTACLE.getId().toString())
                .withEntries(
                        overview.build(),
                        paraphernalia.build(),
                        chalkUses.build(),

                        summonFoliot.build(),
                        summonDjinni.build(),
                        summonWildAfrit.build(),
                        summonAfrit.build(),
                        summonMarid.build(),
                        summonWildGreaterSpirit.build(),

                        possessFoliot.build(),
                        possessDjinni.build(),
                        possessAfrit.build(),

                        craftFoliot.build(),
                        craftDjinni.build(),
                        craftAfrit.build(),
                        craftMarid.build()
                );
    }

    private BookEntryModel.Builder makePentaclesOverviewEntry(BookLangHelper helper, EntryLocationHelper entryHelper, char icon) {
        helper.entry("pentacles_overview");

        helper.page("intro1");
        var intro1 = BookTextPageModel.builder()
                .withTitle(helper.pageTitle())
                .withText(helper.pageText())
                .build();

        helper.page("intro2");
        var intro2 = BookTextPageModel.builder()
                .withText(helper.pageText())
                .build();

        helper.page("intro3");
        var intro3 = BookTextPageModel.builder()
                .withText(helper.pageText())
                .build();

        helper.page("intro4");
        var intro4 = BookTextPageModel.builder()
                .withText(helper.pageText())
                .build();

        //exact copy found in first ritual entry
        helper.page("bowl_placement");
        var bowlPlacementImage = BookImagePageModel.builder()
                .withImages(this.modLoc("textures/gui/book/bowl_placement.png"))
                .withBorder(true)
                .build();

        //exact copy found in first ritual entry
        helper.page("bowl_text");
        var bowlText = BookTextPageModel.builder()
                .withText(helper.pageText())
                .build();

        helper.page("summoning_pentacles");
        var summoningPentacles = BookTextPageModel.builder()
                .withTitle(helper.pageTitle())
                .withText(helper.pageText())
                .build();

        helper.page("infusion_pentacles");
        var infusionPentacles = BookTextPageModel.builder()
                .withTitle(helper.pageTitle())
                .withText(helper.pageText())
                .build();

        helper.page("possession_pentacles");
        var possessionPentacles = BookTextPageModel.builder()
                .withTitle(helper.pageTitle())
                .withText(helper.pageText())
                .build();

        return BookEntryModel.builder()
                .withId(this.modLoc(helper.category + "/" + helper.entry))
                .withName(helper.entryName())
                .withIcon(OccultismBlocks.SPIRIT_ATTUNED_CRYSTAL.getId().toString())
                .withLocation(entryHelper.get(icon))
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

    private BookEntryModel.Builder makeParaphernaliaEntry(BookLangHelper helper, EntryLocationHelper entryHelper, char icon) {
        helper.entry("paraphernalia");

        helper.page("intro");
        var intro = BookTextPageModel.builder()
                .withTitle(helper.pageTitle())
                .withText(helper.pageText())
                .build();

        helper.page("candle");
        var candle = BookSpotlightPageModel.builder()
                .withText(helper.pageText())
                .withItem(Ingredient.of(OccultismBlocks.CANDLE_WHITE.get()))
                .build();

        helper.page("crystal");
        var crystal = BookSpotlightPageModel.builder()
                .withText(helper.pageText())
                .withItem(Ingredient.of(OccultismBlocks.SPIRIT_ATTUNED_CRYSTAL.get()))
                .build();

        helper.page("gem_recipe");
        var gemRecipe = BookSpiritFireRecipePageModel.builder()
                .withRecipeId1(this.modLoc("spirit_fire/spirit_attuned_gem"))
                .build();

        helper.page("crystal_recipe");
        var crystalRecipe = BookCraftingRecipePageModel.builder()
                .withRecipeId1(this.modLoc("crafting/spirit_attuned_crystal"))
                .build();

        helper.page("skeleton_skull");
        var skeletonSkull = BookSpotlightPageModel.builder()
                .withText(helper.pageText())
                .withItem(Ingredient.of(Blocks.SKELETON_SKULL))
                .build();

        return BookEntryModel.builder()
                .withId(this.modLoc(helper.category + "/" + helper.entry))
                .withName(helper.entryName())
                .withIcon(ForgeRegistries.BLOCKS.getKey(Blocks.SKELETON_SKULL).toString())
                .withLocation(entryHelper.get(icon))
                .withPages(
                        intro,
                        candle,
                        crystal,
                        gemRecipe,
                        crystalRecipe,
                        skeletonSkull
                );
    }

    private BookEntryModel.Builder makeChalkUsesEntry(BookLangHelper helper, EntryLocationHelper entryHelper, char icon) {
        helper.entry("chalk_uses");

        helper.page("intro");
        var intro = BookTextPageModel.builder()
                .withTitle(helper.pageTitle())
                .withText(helper.pageText())
                .build();

        helper.page("intro2");
        var intro2 = BookTextPageModel.builder()
                .withText(helper.pageText())
                .build();

        helper.page("white_chalk");
        var whiteChalk = BookSpotlightPageModel.builder()
                .withText(helper.pageText())
                .withItem(Ingredient.of(OccultismItems.CHALK_WHITE.get()))
                .build();

        helper.page("white_chalk_uses");
        var whiteChalkUses = BookTextPageModel.builder()
                .withTitle(helper.pageTitle())
                .withText(helper.pageText())
                .build();

        helper.page("white_chalk_uses2");
        var whiteChalkUses2 = BookTextPageModel.builder()
                .withTitle(helper.pageTitle())
                .withText(helper.pageText())
                .build();

        helper.page("golden_chalk");
        var goldChalk = BookSpotlightPageModel.builder()
                .withText(helper.pageText())
                .withItem(Ingredient.of(OccultismItems.CHALK_GOLD.get()))
                .build();

        helper.page("golden_chalk_uses");
        var goldChalkUses = BookTextPageModel.builder()
                .withTitle(helper.pageTitle())
                .withText(helper.pageText())
                .build();

        helper.page("purple_chalk");
        var purpleChalk = BookSpotlightPageModel.builder()
                .withText(helper.pageText())
                .withItem(Ingredient.of(OccultismItems.CHALK_PURPLE.get()))
                .build();

        helper.page("purple_chalk_uses");
        var purpleChalkUses = BookTextPageModel.builder()
                .withTitle(helper.pageTitle())
                .withText(helper.pageText())
                .build();

        helper.page("red_chalk");
        var redChalk = BookSpotlightPageModel.builder()
                .withText(helper.pageText())
                .withItem(Ingredient.of(OccultismItems.CHALK_RED.get()))
                .build();

        helper.page("red_chalk_uses");
        var redChalkUses = BookTextPageModel.builder()
                .withTitle(helper.pageTitle())
                .withText(helper.pageText())
                .build();

        return BookEntryModel.builder()
                .withId(this.modLoc(helper.category + "/" + helper.entry))
                .withName(helper.entryName())
                .withIcon(OccultismItems.CHALK_PURPLE.getId().toString())
                .withLocation(entryHelper.get(icon))
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

    private BookEntryModel.Builder makeSummonFoliotEntry(BookLangHelper helper, EntryLocationHelper entryHelper, char icon) {
        helper.entry("summon_foliot");

        helper.page("intro");
        var intro = BookTextPageModel.builder()
                .withTitle(helper.pageTitle())
                .withText(helper.pageText())
                .build();

        helper.page("multiblock");
        var multiblock = BookMultiblockPageModel.builder()
                .withMultiblockId(this.modLoc("summon_foliot"))
                .build();

        helper.page("uses");
        var uses = BookTextPageModel.builder()
                .withTitle(helper.pageTitle())
                .withText(helper.pageText())
                .build();

        return BookEntryModel.builder()
                .withId(this.modLoc(helper.category + "/" + helper.entry))
                .withName(helper.entryName())
                .withIcon(OccultismItems.PENTACLE.getId().toString())
                .withLocation(entryHelper.get(icon))
                .withPages(
                        intro,
                        multiblock,
                        uses
                );
    }

    private BookEntryModel.Builder makePossessFoliotEntry(BookLangHelper helper, EntryLocationHelper entryHelper, char icon) {
        helper.entry("possess_foliot");

        helper.page("intro");
        var intro = BookTextPageModel.builder()
                .withTitle(helper.pageTitle())
                .withText(helper.pageText())
                .build();

        helper.page("multiblock");
        var multiblock = BookMultiblockPageModel.builder()
                .withMultiblockId(this.modLoc("possess_foliot"))
                .build();

        helper.page("uses");
        var uses = BookTextPageModel.builder()
                .withTitle(helper.pageTitle())
                .withText(helper.pageText())
                .build();

        return BookEntryModel.builder()
                .withId(this.modLoc(helper.category + "/" + helper.entry))
                .withName(helper.entryName())
                .withIcon(OccultismItems.PENTACLE.getId().toString())
                .withLocation(entryHelper.get(icon))
                .withPages(
                        intro,
                        multiblock,
                        uses
                );
    }

    private BookEntryModel.Builder makeCraftFoliotEntry(BookLangHelper helper, EntryLocationHelper entryHelper, char icon) {
        helper.entry("craft_foliot");

        helper.page("intro");
        var intro = BookTextPageModel.builder()
                .withTitle(helper.pageTitle())
                .withText(helper.pageText())
                .build();

        helper.page("multiblock");
        var multiblock = BookMultiblockPageModel.builder()
                .withMultiblockId(this.modLoc("craft_foliot"))
                .build();

        helper.page("uses");
        var uses = BookTextPageModel.builder()
                .withTitle(helper.pageTitle())
                .withText(helper.pageText())
                .build();

        return BookEntryModel.builder()
                .withId(this.modLoc(helper.category + "/" + helper.entry))
                .withName(helper.entryName())
                .withIcon(OccultismItems.PENTACLE.getId().toString())
                .withLocation(entryHelper.get(icon))
                .withPages(
                        intro,
                        multiblock,
                        uses
                );
    }

    private BookEntryModel.Builder makeSummonDjinniEntry(BookLangHelper helper, EntryLocationHelper entryHelper, char icon) {
        helper.entry("summon_djinni");

        helper.page("intro");
        var intro = BookTextPageModel.builder()
                .withTitle(helper.pageTitle())
                .withText(helper.pageText())
                .build();

        helper.page("multiblock");
        var multiblock = BookMultiblockPageModel.builder()
                .withMultiblockId(this.modLoc("summon_djinni"))
                .build();

        helper.page("uses");
        var uses = BookTextPageModel.builder()
                .withTitle(helper.pageTitle())
                .withText(helper.pageText())
                .build();

        return BookEntryModel.builder()
                .withId(this.modLoc(helper.category + "/" + helper.entry))
                .withName(helper.entryName())
                .withIcon(OccultismItems.PENTACLE.getId().toString())
                .withLocation(entryHelper.get(icon))
                .withPages(
                        intro,
                        multiblock,
                        uses
                );
    }

    private BookEntryModel.Builder makePossessDjinniEntry(BookLangHelper helper, EntryLocationHelper entryHelper, char icon) {
        helper.entry("possess_djinni");

        helper.page("intro");
        var intro = BookTextPageModel.builder()
                .withTitle(helper.pageTitle())
                .withText(helper.pageText())
                .build();

        helper.page("multiblock");
        var multiblock = BookMultiblockPageModel.builder()
                .withMultiblockId(this.modLoc("possess_djinni"))
                .build();

        helper.page("uses");
        var uses = BookTextPageModel.builder()
                .withTitle(helper.pageTitle())
                .withText(helper.pageText())
                .build();

        helper.page("uses2");
        var uses2 = BookTextPageModel.builder()
                .withTitle(helper.pageTitle())
                .withText(helper.pageText())
                .build();

        return BookEntryModel.builder()
                .withId(this.modLoc(helper.category + "/" + helper.entry))
                .withName(helper.entryName())
                .withIcon(OccultismItems.PENTACLE.getId().toString())
                .withLocation(entryHelper.get(icon))
                .withPages(
                        intro,
                        multiblock,
                        uses,
                        uses2
                );
    }

    private BookEntryModel.Builder makeCraftDjinniEntry(BookLangHelper helper, EntryLocationHelper entryHelper, char icon) {
        helper.entry("craft_djinni");

        helper.page("intro");
        var intro = BookTextPageModel.builder()
                .withTitle(helper.pageTitle())
                .withText(helper.pageText())
                .build();

        helper.page("multiblock");
        var multiblock = BookMultiblockPageModel.builder()
                .withMultiblockId(this.modLoc("craft_djinni"))
                .build();

        helper.page("uses");
        var uses = BookTextPageModel.builder()
                .withTitle(helper.pageTitle())
                .withText(helper.pageText())
                .build();

        return BookEntryModel.builder()
                .withId(this.modLoc(helper.category + "/" + helper.entry))
                .withName(helper.entryName())
                .withIcon(OccultismItems.PENTACLE.getId().toString())
                .withLocation(entryHelper.get(icon))
                .withPages(
                        intro,
                        multiblock,
                        uses
                );
    }

    private BookEntryModel.Builder makeSummonAfritEntry(BookLangHelper helper, EntryLocationHelper entryHelper, char icon) {
        helper.entry("summon_afrit");

        helper.page("intro");
        var intro = BookTextPageModel.builder()
                .withTitle(helper.pageTitle())
                .withText(helper.pageText())
                .build();

        helper.page("multiblock");
        var multiblock = BookMultiblockPageModel.builder()
                .withMultiblockId(this.modLoc("summon_afrit"))
                .build();

        helper.page("uses");
        var uses = BookTextPageModel.builder()
                .withTitle(helper.pageTitle())
                .withText(helper.pageText())
                .build();

        return BookEntryModel.builder()
                .withId(this.modLoc(helper.category + "/" + helper.entry))
                .withName(helper.entryName())
                .withIcon(OccultismItems.PENTACLE.getId().toString())
                .withLocation(entryHelper.get(icon))
                .withPages(
                        intro,
                        multiblock,
                        uses
                );
    }

    private BookEntryModel.Builder makeSummonWildAfritEntry(BookLangHelper helper, EntryLocationHelper entryHelper, char icon) {
        helper.entry("summon_wild_afrit");

        helper.page("intro");
        var intro = BookTextPageModel.builder()
                .withTitle(helper.pageTitle())
                .withText(helper.pageText())
                .build();

        helper.page("multiblock");
        var multiblock = BookMultiblockPageModel.builder()
                .withMultiblockId(this.modLoc("summon_wild_afrit"))
                .build();

        helper.page("uses");
        var uses = BookTextPageModel.builder()
                .withTitle(helper.pageTitle())
                .withText(helper.pageText())
                .build();

        return BookEntryModel.builder()
                .withId(this.modLoc(helper.category + "/" + helper.entry))
                .withName(helper.entryName())
                .withIcon(OccultismItems.PENTACLE.getId().toString())
                .withLocation(entryHelper.get(icon))
                .withPages(
                        intro,
                        multiblock,
                        uses
                );
    }

    private BookEntryModel.Builder makePossessAfritEntry(BookLangHelper helper, EntryLocationHelper entryHelper, char icon) {
        helper.entry("possess_afrit");

        helper.page("intro");
        var intro = BookTextPageModel.builder()
                .withTitle(helper.pageTitle())
                .withText(helper.pageText())
                .build();

        helper.page("multiblock");
        var multiblock = BookMultiblockPageModel.builder()
                .withMultiblockId(this.modLoc("possess_afrit"))
                .build();

        helper.page("uses");
        var uses = BookTextPageModel.builder()
                .withTitle(helper.pageTitle())
                .withText(helper.pageText())
                .build();

        return BookEntryModel.builder()
                .withId(this.modLoc(helper.category + "/" + helper.entry))
                .withName(helper.entryName())
                .withIcon(OccultismItems.PENTACLE.getId().toString())
                .withLocation(entryHelper.get(icon))
                .withPages(
                        intro,
                        multiblock,
                        uses
                );
    }

    private BookEntryModel.Builder makeCraftAfritEntry(BookLangHelper helper, EntryLocationHelper entryHelper, char icon) {
        helper.entry("craft_afrit");

        helper.page("intro");
        var intro = BookTextPageModel.builder()
                .withTitle(helper.pageTitle())
                .withText(helper.pageText())
                .build();

        helper.page("multiblock");
        var multiblock = BookMultiblockPageModel.builder()
                .withMultiblockId(this.modLoc("craft_afrit"))
                .build();

        helper.page("uses");
        var uses = BookTextPageModel.builder()
                .withTitle(helper.pageTitle())
                .withText(helper.pageText())
                .build();

        return BookEntryModel.builder()
                .withId(this.modLoc(helper.category + "/" + helper.entry))
                .withName(helper.entryName())
                .withIcon(OccultismItems.PENTACLE.getId().toString())
                .withLocation(entryHelper.get(icon))
                .withPages(
                        intro,
                        multiblock,
                        uses
                );
    }

    private BookEntryModel.Builder makeSummonMaridEntry(BookLangHelper helper, EntryLocationHelper entryHelper, char icon) {
        helper.entry("summon_marid");

        helper.page("intro");
        var intro = BookTextPageModel.builder()
                .withTitle(helper.pageTitle())
                .withText(helper.pageText())
                .build();

        helper.page("multiblock");
        var multiblock = BookMultiblockPageModel.builder()
                .withMultiblockId(this.modLoc("summon_marid"))
                .build();

        helper.page("uses");
        var uses = BookTextPageModel.builder()
                .withTitle(helper.pageTitle())
                .withText(helper.pageText())
                .build();

        return BookEntryModel.builder()
                .withId(this.modLoc(helper.category + "/" + helper.entry))
                .withName(helper.entryName())
                .withIcon(OccultismItems.PENTACLE.getId().toString())
                .withLocation(entryHelper.get(icon))
                .withPages(
                        intro,
                        multiblock,
                        uses
                );
    }

    private BookEntryModel.Builder makeCraftMaridEntry(BookLangHelper helper, EntryLocationHelper entryHelper, char icon) {
        helper.entry("craft_marid");

        helper.page("intro");
        var intro = BookTextPageModel.builder()
                .withTitle(helper.pageTitle())
                .withText(helper.pageText())
                .build();

        helper.page("multiblock");
        var multiblock = BookMultiblockPageModel.builder()
                .withMultiblockId(this.modLoc("craft_marid"))
                .build();

        helper.page("uses");
        var uses = BookTextPageModel.builder()
                .withTitle(helper.pageTitle())
                .withText(helper.pageText())
                .build();

        return BookEntryModel.builder()
                .withId(this.modLoc(helper.category + "/" + helper.entry))
                .withName(helper.entryName())
                .withIcon(OccultismItems.PENTACLE.getId().toString())
                .withLocation(entryHelper.get(icon))
                .withPages(
                        intro,
                        multiblock,
                        uses
                );
    }

    private BookEntryModel.Builder makeSummonWildGreaterSpiritEntry(BookLangHelper helper, EntryLocationHelper entryHelper, char icon) {
        helper.entry("summon_wild_greater_spirit");

        helper.page("intro");
        var intro = BookTextPageModel.builder()
                .withTitle(helper.pageTitle())
                .withText(helper.pageText())
                .build();

        helper.page("multiblock");
        var multiblock = BookMultiblockPageModel.builder()
                .withMultiblockId(this.modLoc("summon_wild_greater_spirit"))
                .build();

        helper.page("uses");
        var uses = BookTextPageModel.builder()
                .withTitle(helper.pageTitle())
                .withText(helper.pageText())
                .build();

        return BookEntryModel.builder()
                .withId(this.modLoc(helper.category + "/" + helper.entry))
                .withName(helper.entryName())
                .withIcon(OccultismItems.PENTACLE.getId().toString())
                .withLocation(entryHelper.get(icon))
                .withPages(
                        intro,
                        multiblock,
                        uses
                );
    }
    //endregion

    private BookCategoryModel makeRitualsCategory(BookLangHelper helper) {
        helper.category("rituals");

        var entryHelper = ModonomiconAPI.get().getEntryLocationHelper();
        entryHelper.setMap(
                "___________________",
                "______________p_s__",
                "___________________",
                "________o_i_k______",
                "___________________",
                "______________c_f__",
                "___________________"
        );

        var ritualOverview = this.makeRitualOverviewEntry(helper, entryHelper, 'o');
        var itemUse = this.makeItemUseEntry(helper, entryHelper, 'i');
        itemUse.withParent(BookEntryParentModel.builder().withEntryId(ritualOverview.id).build());
        var sacrifice = this.makeSacrificeEntry(helper, entryHelper, 'k');
        sacrifice.withParent(BookEntryParentModel.builder().withEntryId(itemUse.id).build());

        var summoning = this.makeSummoningRitualsSubcategoryEntry(helper, entryHelper, 's');
        summoning.withParent(BookEntryParentModel.builder().withEntryId(sacrifice.id).build());
        var possession = this.makePossessionRitualsSubcategoryEntry(helper, entryHelper, 'p');
        possession.withParent(BookEntryParentModel.builder().withEntryId(sacrifice.id).build());
        var crafting = this.makeCraftingRitualsSubcategoryEntry(helper, entryHelper, 'c');
        crafting.withParent(BookEntryParentModel.builder().withEntryId(sacrifice.id).build());
        var familiars = this.makeFamiliarRitualsSubcategoryEntry(helper, entryHelper, 'f');
        familiars.withParent(BookEntryParentModel.builder().withEntryId(sacrifice.id).build());

        //enable all entries by default
        itemUse.withCondition(BookTrueConditionModel.builder().build());
        sacrifice.withCondition(BookTrueConditionModel.builder().build());
        summoning.withCondition(BookTrueConditionModel.builder().build());
        possession.withCondition(BookTrueConditionModel.builder().build());
        crafting.withCondition(BookTrueConditionModel.builder().build());
        familiars.withCondition(BookTrueConditionModel.builder().build());

        return BookCategoryModel.create(this.modLoc(helper.category), helper.categoryName())
                .withIcon("occultism:textures/gui/book/robe.png")
                .withEntries(
                        ritualOverview.build(),
                        itemUse.build(),
                        sacrifice.build(),
                        summoning.build(),
                        possession.build(),
                        crafting.build(),
                        familiars.build()
                );
    }

    private BookEntryModel.Builder makeRitualOverviewEntry(BookLangHelper helper, EntryLocationHelper entryHelper, char icon) {
        helper.entry("overview");

        helper.page("intro");
        var intro = BookTextPageModel.builder()
                .withTitle(helper.pageTitle())
                .withText(helper.pageText())
                .build();

        helper.page("steps");
        var steps = BookTextPageModel.builder()
                .withTitle(helper.pageTitle())
                .withText(helper.pageText())
                .build();

        helper.page("additional_requirements");
        var additional_requirements = BookTextPageModel.builder()
                .withTitle(helper.pageTitle())
                .withText(helper.pageText())
                .build();

        return BookEntryModel.builder()
                .withId(this.modLoc(helper.category + "/" + helper.entry))
                .withName(helper.entryName())
                .withIcon("occultism:textures/gui/book/robe.png")
                .withLocation(entryHelper.get(icon))
                .withEntryBackground(0, 1)
                .withPages(
                        intro,
                        steps,
                        additional_requirements
                );
    }

    private BookEntryModel.Builder makeSacrificeEntry(BookLangHelper helper, EntryLocationHelper entryHelper, char icon) {
        helper.entry("sacrifice");

        helper.page("intro");
        var intro = BookTextPageModel.builder()
                .withTitle(helper.pageTitle())
                .withText(helper.pageText())
                .build();

        return BookEntryModel.builder()
                .withId(this.modLoc(helper.category + "/" + helper.entry))
                .withName(helper.entryName())
                .withIcon(ForgeRegistries.ITEMS.getKey(Items.IRON_SWORD).toString())
                .withLocation(entryHelper.get(icon))
                .withPages(
                        intro
                );
    }

    private BookEntryModel.Builder makeItemUseEntry(BookLangHelper helper, EntryLocationHelper entryHelper, char icon) {
        helper.entry("item_use");

        helper.page("intro");
        var intro = BookTextPageModel.builder()
                .withTitle(helper.pageTitle())
                .withText(helper.pageText())
                .build();

        return BookEntryModel.builder()
                .withId(this.modLoc(helper.category + "/" + helper.entry))
                .withName(helper.entryName())
                .withIcon(ForgeRegistries.ITEMS.getKey(Items.FLINT_AND_STEEL).toString())
                .withLocation(entryHelper.get(icon))
                .withPages(
                        intro
                );
    }

    private BookEntryModel.Builder makeSummoningRitualsSubcategoryEntry(BookLangHelper helper, EntryLocationHelper entryHelper, char icon) {
        helper.entry("summoning_rituals");

        return BookEntryModel.builder()
                .withId(this.modLoc(helper.category + "/" + helper.entry))
                .withName(helper.entryName())
                .withIcon("occultism:textures/gui/book/summoning.png")
                .withCategoryToOpen(this.modLoc("summoning_rituals"))
                .withEntryBackground(1, 1) //silver background and wavey entry shape
                .withLocation(entryHelper.get(icon));
    }

    private BookEntryModel.Builder makePossessionRitualsSubcategoryEntry(BookLangHelper helper, EntryLocationHelper entryHelper, char icon) {
        helper.entry("possession_rituals");

        return BookEntryModel.builder()
                .withId(this.modLoc(helper.category + "/" + helper.entry))
                .withName(helper.entryName())
                .withIcon("occultism:textures/gui/book/possession.png")
                .withCategoryToOpen(this.modLoc("possession_rituals"))
                .withEntryBackground(1, 1) //silver background and wavey entry shape
                .withLocation(entryHelper.get(icon));
    }

    private BookEntryModel.Builder makeCraftingRitualsSubcategoryEntry(BookLangHelper helper, EntryLocationHelper entryHelper, char icon) {
        helper.entry("crafting_rituals");

        return BookEntryModel.builder()
                .withId(this.modLoc(helper.category + "/" + helper.entry))
                .withName(helper.entryName())
                .withIcon("occultism:textures/gui/book/infusion.png")
                .withCategoryToOpen(this.modLoc("crafting_rituals"))
                .withEntryBackground(1, 1) //silver background and wavey entry shape
                .withLocation(entryHelper.get(icon));
    }

    private BookEntryModel.Builder makeFamiliarRitualsSubcategoryEntry(BookLangHelper helper, EntryLocationHelper entryHelper, char icon) {
        helper.entry("familiar_rituals");

        return BookEntryModel.builder()
                .withId(this.modLoc(helper.category + "/" + helper.entry))
                .withName(helper.entryName())
                .withIcon("occultism:textures/gui/book/parrot.png")
                .withCategoryToOpen(this.modLoc("familiar_rituals"))
                .withEntryBackground(1, 1) //silver background and wavey entry shape
                .withLocation(entryHelper.get(icon));
    }

    private BookEntryModel.Builder makeReturnToRitualsEntry(BookLangHelper helper, EntryLocationHelper entryHelper, char icon) {
        helper.entry("return_to_rituals");

        return BookEntryModel.builder()
                .withId(this.modLoc(helper.category + "/" + helper.entry))
                .withName(helper.entryName())
                .withIcon("occultism:textures/gui/book/robe.png")
                .withCategoryToOpen(this.modLoc("rituals"))
                .withEntryBackground(1, 2)
                .withLocation(entryHelper.get(icon));
    }
    //endregion

    //region Summoning Rituals
    private BookCategoryModel makeSummoningRitualsSubcategory(BookLangHelper helper) {
        helper.category("summoning_rituals");

        var entryHelper = ModonomiconAPI.get().getEntryLocationHelper();
        entryHelper.setMap(
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

        var overview = this.makeSummoningRitualsOverviewEntry(helper, entryHelper, 'o');
        var returnToRituals = this.makeReturnToRitualsEntry(helper, entryHelper, 'r');
        returnToRituals.withParent(BookEntryParentModel.builder().withEntryId(overview.id).build());
        returnToRituals.withCondition(BookTrueConditionModel.builder().build());

        var summonT1Crusher = this.makeSummonCrusherT1Entry(helper, entryHelper, '1');
        summonT1Crusher.withParent(BookEntryParentModel.builder().withEntryId(overview.id).build());
        var summonT2Crusher = this.makeSummonCrusherT2Entry(helper, entryHelper, '2');
        summonT2Crusher.withParent(BookEntryParentModel.builder().withEntryId(summonT1Crusher.id).build());
        var summonT3Crusher = this.makeSummonCrusherT3Entry(helper, entryHelper, '3');
        summonT3Crusher.withParent(BookEntryParentModel.builder().withEntryId(summonT2Crusher.id).build());
        var summonT4Crusher = this.makeSummonCrusherT4Entry(helper, entryHelper, '4');
        summonT4Crusher.withParent(BookEntryParentModel.builder().withEntryId(summonT3Crusher.id).build());

        var summonLumberjack = this.makeSummonLumberjackEntry(helper, entryHelper, 'c');
        summonLumberjack.withParent(BookEntryParentModel.builder().withEntryId(overview.id).build());

        var summonTransportItems = this.makeSummonTransportItemsEntry(helper, entryHelper, 'd');
        summonTransportItems.withParent(BookEntryParentModel.builder().withEntryId(overview.id).build());
        var summonCleaner = this.makeSummonCleanerEntry(helper, entryHelper, 'b');
        summonCleaner.withParent(BookEntryParentModel.builder().withEntryId(summonTransportItems.id).build());
        var summonManageMachine = this.makeSummonManageMachineEntry(helper, entryHelper, 'h');
        summonManageMachine.withParent(BookEntryParentModel.builder().withEntryId(summonTransportItems.id).build());

        var tradeSpirits = this.makeTradeSpiritsEntry(helper, entryHelper, 'e');
        tradeSpirits.withParent(BookEntryParentModel.builder().withEntryId(overview.id).build());
        var summonOtherworldSaplingTrader = this.makeSummonOtherworldSaplingTraderEntry(helper, entryHelper, 'f');
        summonOtherworldSaplingTrader.withParent(BookEntryParentModel.builder().withEntryId(tradeSpirits.id).build());
        var summonOtherstoneTrader = this.makeSummonOtherstoneTraderEntry(helper, entryHelper, 'g');
        summonOtherstoneTrader.withParent(BookEntryParentModel.builder().withEntryId(summonOtherworldSaplingTrader.id).build());

        var summonWildParrot = this.makeSummonWildParrotEntry(helper, entryHelper, 'i');
        summonWildParrot.withParent(BookEntryParentModel.builder().withEntryId(overview.id).build());
        var summonWildOtherworldBird = this.makeSummonWildOtherworldBirdEntry(helper, entryHelper, 'j');
        summonWildOtherworldBird.withParent(BookEntryParentModel.builder().withEntryId(summonWildParrot.id).build());

        var weatherMagic = this.makeWeatherMagicEntry(helper, entryHelper, 'k');
        weatherMagic.withParent(BookEntryParentModel.builder().withEntryId(overview.id).build());
        var timeMagic = this.makeTimeMagicEntry(helper, entryHelper, 'l');
        timeMagic.withParent(BookEntryParentModel.builder().withEntryId(weatherMagic.id).build());

        var afritEssence = this.makeAfritEssenceEntry(helper, entryHelper, 'a');
        afritEssence.withParent(BookEntryParentModel.builder().withEntryId(overview.id).build());

        var witherSkull = this.makeWitherSkullEntry(helper, entryHelper, 'm');
        witherSkull.withParent(BookEntryParentModel.builder().withEntryId(overview.id).build());

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

        return BookCategoryModel.create(this.modLoc(helper.category), helper.categoryName())
                .withIcon("occultism:textures/gui/book/summoning.png")
                .withShowCategoryButton(false)
                .withEntries(
                        overview.build(),
                        returnToRituals.build(),
                        afritEssence.build(),
                        summonCleaner.build(),
                        summonT1Crusher.build(),
                        summonT2Crusher.build(),
                        summonT3Crusher.build(),
                        summonT4Crusher.build(),
                        summonLumberjack.build(),
                        summonManageMachine.build(),
                        summonTransportItems.build(),
                        tradeSpirits.build(),
                        summonOtherstoneTrader.build(),
                        summonOtherworldSaplingTrader.build(),
                        summonWildOtherworldBird.build(),
                        summonWildParrot.build(),
                        timeMagic.build(),
                        weatherMagic.build(),
                        witherSkull.build(),
                        afritEssence.build()
                );
    }

    private BookEntryModel.Builder makeSummoningRitualsOverviewEntry(BookLangHelper helper, EntryLocationHelper entryHelper, char icon) {
        helper.entry("overview");

        helper.page("intro");
        var intro = BookTextPageModel.builder()
                .withTitle(helper.pageTitle())
                .withText(helper.pageText())
                .build();

        return BookEntryModel.builder()
                .withId(this.modLoc(helper.category + "/" + helper.entry))
                .withName(helper.entryName())
                .withIcon("occultism:textures/gui/book/summoning.png")
                .withLocation(entryHelper.get(icon))
                .withEntryBackground(0, 1)
                .withPages(
                        intro
                );
    }

    private BookEntryModel.Builder makeAfritEssenceEntry(BookLangHelper helper, EntryLocationHelper entryHelper, char icon) {
        helper.entry("afrit_essence");

        helper.page("intro");
        var intro = BookTextPageModel.builder()
                .withTitle(helper.pageTitle())
                .withText(helper.pageText())
                .build();

        helper.page("ritual");
        var ritual = BookRitualRecipePageModel.builder()
                .withRecipeId1(this.modLoc("ritual/summon_wild_afrit"))
                .build();

        return BookEntryModel.builder()
                .withId(this.modLoc(helper.category + "/" + helper.entry))
                .withName(helper.entryName())
                .withIcon(OccultismItems.AFRIT_ESSENCE.getId().toString())
                .withLocation(entryHelper.get(icon))
                .withPages(
                        intro,
                        ritual
                );
    }

    private BookEntryModel.Builder makeSummonCrusherT1Entry(BookLangHelper helper, EntryLocationHelper entryHelper, char icon) {
        helper.entry("summon_crusher_t1");
        this.lang.add(helper.entryName(), "Summon Foliot Crusher");

        helper.page("about_crushers");
        var aboutCrushers = BookTextPageModel.builder()
                .withTitle(helper.pageTitle())
                .withText(helper.pageText())
                .build();
        this.lang.add(helper.pageTitle(), "Crusher Spirits");
        this.lang.add(helper.pageText(),
                """
                        Crusher spirits are summoned to crush ores into dusts, effectively multiplying the metal output. They will pick up appropriate ores and drop the resulting dusts into the world. A purple particle effect and a crushing sound indicate the crusher is at work.
                          """);

        helper.page("automation");
        var automation = BookTextPageModel.builder()
                .withTitle(helper.pageTitle())
                .withText(helper.pageText())
                .build();
        this.lang.add(helper.pageTitle(), "Automation");
        this.lang.add(helper.pageText(),
                """
                        To ease automation, try summoning a [Transporter Spirit](entry://occultism:dictionary_of_spirits/summoning_rituals/summon_transport_items)
                        to place items from chests in the crusher's inventory, and a [Janitor Spirit](entry://occultism:dictionary_of_spirits/summoning_rituals/summon_cleaner) to collect the processed items.
                         """);

        helper.page("intro");
        var intro = BookTextPageModel.builder()
                .withTitle(helper.pageTitle())
                .withText(helper.pageText())
                .build();
        this.lang.add(helper.pageTitle(), "Foliot Crusher");
        this.lang.add(helper.pageText(),
                """
                        The foliot crusher is the most basic crusher spirit.
                        \\
                        \\
                        It will crush **one** ore into **two** corresponding dusts.
                         """);

        helper.page("ritual");
        var ritual = BookRitualRecipePageModel.builder()
                .withRecipeId1(this.modLoc("ritual/summon_foliot_crusher"))
                .build();
        //no text

        return BookEntryModel.builder()
                .withId(this.modLoc(helper.category + "/" + helper.entry))
                .withName(helper.entryName())
                .withIcon(OccultismItems.COPPER_DUST.getId().toString())
                .withLocation(entryHelper.get(icon))
                .withPages(
                        aboutCrushers,
                        automation,
                        intro,
                        ritual
                );
    }

    private BookEntryModel.Builder makeSummonCrusherT2Entry(BookLangHelper helper, EntryLocationHelper entryHelper, char icon) {
        helper.entry("summon_crusher_t2");

        helper.page("intro");
        var intro = BookTextPageModel.builder()
                .withTitle(helper.pageTitle())
                .withText(helper.pageText())
                .build();

        helper.page("ritual");
        var ritual = BookRitualRecipePageModel.builder()
                .withRecipeId1(this.modLoc("ritual/summon_djinni_crusher"))
                .build();

        return BookEntryModel.builder()
                .withId(this.modLoc(helper.category + "/" + helper.entry))
                .withName(helper.entryName())
                .withIcon(OccultismItems.IRON_DUST.getId().toString())
                .withLocation(entryHelper.get(icon))
                .withPages(
                        intro,
                        ritual
                );
    }

    private BookEntryModel.Builder makeSummonCrusherT3Entry(BookLangHelper helper, EntryLocationHelper entryHelper, char icon) {
        helper.entry("summon_crusher_t3");

        helper.page("intro");
        var intro = BookTextPageModel.builder()
                .withTitle(helper.pageTitle())
                .withText(helper.pageText())
                .build();

        helper.page("ritual");
        var ritual = BookRitualRecipePageModel.builder()
                .withRecipeId1(this.modLoc("ritual/summon_afrit_crusher"))
                .build();

        return BookEntryModel.builder()
                .withId(this.modLoc(helper.category + "/" + helper.entry))
                .withName(helper.entryName())
                .withIcon(OccultismItems.SILVER_DUST.getId().toString())
                .withLocation(entryHelper.get(icon))
                .withPages(
                        intro,
                        ritual
                );
    }

    private BookEntryModel.Builder makeSummonCrusherT4Entry(BookLangHelper helper, EntryLocationHelper entryHelper, char icon) {
        helper.entry("summon_crusher_t4");

        helper.page("intro");
        var intro = BookTextPageModel.builder()
                .withTitle(helper.pageTitle())
                .withText(helper.pageText())
                .build();

        helper.page("ritual");
        var ritual = BookRitualRecipePageModel.builder()
                .withRecipeId1(this.modLoc("ritual/summon_marid_crusher"))
                .build();

        return BookEntryModel.builder()
                .withId(this.modLoc(helper.category + "/" + helper.entry))
                .withName(helper.entryName())
                .withIcon(OccultismItems.GOLD_DUST.getId().toString())
                .withLocation(entryHelper.get(icon))
                .withPages(
                        intro,
                        ritual
                );
    }

    private BookEntryModel.Builder makeSummonLumberjackEntry(BookLangHelper helper, EntryLocationHelper entryHelper, char icon) {
        helper.entry("summon_lumberjack");
        this.lang.add(helper.entryName(), "Summon Foliot Lumberjack");

        helper.page("intro");
        var intro = BookTextPageModel.builder()
                .withTitle(helper.pageTitle())
                .withText(helper.pageText())
                .build();
        this.lang.add(helper.pageTitle(), "Foliot Lumberjack");
        this.lang.add(helper.pageText(),
                """
                        The lumberjack will harvest trees in it's working area. If a deposit location is set it will collect the dropped items into the specified chest, and re-plant saplings.
                          """);

        helper.page("ritual");
        var ritual = BookRitualRecipePageModel.builder()
                .withRecipeId1(this.modLoc("ritual/summon_foliot_lumberjack"))
                .build();
        //no text

        helper.page("book_of_calling");
        var bookOfCalling = BookCraftingRecipePageModel.builder()
                .withRecipeId1(this.modLoc("crafting/book_of_calling_foliot_lumberjack"))
                .withText(helper.pageText())
                .build();
        this.lang.add(helper.pageText(),
                """
                        If you lose the book of calling, you can craft a new one.
                        [#](%1$s)Shift-right-click[#]() the spirit with the crafted book to assign it.
                        """.formatted(COLOR_PURPLE));

        helper.page("usage");
        var usage = BookTextPageModel.builder()
                .withTitle(helper.pageTitle())
                .withText(helper.pageText())
                .build();
        this.lang.add(helper.pageTitle(), "Usage");
        this.lang.add(helper.pageText(),
                """
                        Use the book of calling to set the work area and deposit location of the lumberjack.
                        \\
                        \\
                        See [Books of Calling](entry://getting_started/books_of_calling) for more information.
                           """);

        helper.page("usage2");
        var usage2 = BookTextPageModel.builder()
                .withTitle(helper.pageTitle())
                .withText(helper.pageText())
                .build();
        this.lang.add(helper.pageTitle(), "Lazy Lumberjack?");
        this.lang.add(helper.pageText(),
                """
                        The spirit might pause for a few minutes after clearing his work area, even if trees have regrown since. This is a performance-saving measure and not a bug, he will continue on his own.
                        \\
                        \\
                        Set the work area again to make him continue work immediately.
                          """);

        return BookEntryModel.builder()
                .withId(this.modLoc(helper.category + "/" + helper.entry))
                .withName(helper.entryName())
                .withIcon(OccultismItems.BRUSH.getId().toString())
                .withIcon(ForgeRegistries.ITEMS.getKey(Items.IRON_AXE).toString())
                .withLocation(entryHelper.get(icon))
                .withPages(
                        intro,
                        ritual,
                        bookOfCalling,
                        usage,
                        usage2
                );
    }

    private BookEntryModel.Builder makeSummonTransportItemsEntry(BookLangHelper helper, EntryLocationHelper entryHelper, char icon) {
        helper.entry("summon_transport_items");
        this.lang.add(helper.entryName(), "Summon Foliot Transporter");

        helper.page("intro");
        var intro = BookTextPageModel.builder()
                .withTitle(helper.pageTitle())
                .withText(helper.pageText())
                .build();
        this.lang.add(helper.pageTitle(), "Foliot Transporter");
        this.lang.add(helper.pageText(),
                """
                        The transporter is useful in that you don't need a train of hoppers transporting stuff, and can use any inventory to take from and deposit.
                        \\
                        \\
                        To make it take from an inventory simply sneak and interact with it's book of calling on the inventory you want.
                               """);

        helper.page("intro2");
        var intro2 = BookTextPageModel.builder()
                .withText(helper.pageText())
                .build();
        this.lang.add(helper.pageText(),
                """
                        You can also dictate which inventory it deposits to in the same way.
                        \\
                        The transporter will move all items it can access from one inventory to another, including machines. It can also deposit into the inventories of other spirits. By setting the extract and insert side they can be used to automate various transport tasks.
                           """);

        helper.page("spirit_inventories");
        var spirit_inventories = BookTextPageModel.builder()
                .withTitle(helper.pageTitle())
                .withText(helper.pageText())
                .build();
        this.lang.add(helper.pageTitle(), "Spirit Inventories");
        this.lang.add(helper.pageText(),
                """
                        The Transporter can also interact with the inventories of other spirits. This is especially useful to automatically supply a [Crusher spirit](entry://summoning_rituals/summon_crusher_t1) with items to crush.
                           """);

        helper.page("item_filters");
        var itemFilters = BookTextPageModel.builder()
                .withTitle(helper.pageTitle())
                .withText(helper.pageText())
                .build();
        this.lang.add(helper.pageTitle(), "Item Filters");
        this.lang.add(helper.pageText(),
                """
                        By default the Transporter is in "Blacklist" mode and will not move anything. Shift-click the transporter to open the config UI. You can then add items to the filter list to make it move only those items, or set it to "Whitelist" to move everything *except* the filtered items. You can also enter a tag in the text field below to filter by tag.
                           """);

        helper.page("ritual");
        var ritual = BookRitualRecipePageModel.builder()
                .withRecipeId1(this.modLoc("ritual/summon_foliot_transport_items"))
                .build();
        //no text

        helper.page("book_of_calling");
        var bookOfCalling = BookCraftingRecipePageModel.builder()
                .withRecipeId1(this.modLoc("crafting/book_of_calling_foliot_transport_items"))
                .withText(helper.pageText())
                .build();
        this.lang.add(helper.pageText(),
                """
                        If you lose the book of calling, you can craft a new one.
                        [#](%1$s)Shift-right-click[#]() the spirit with the crafted book to assign it.
                        """.formatted(COLOR_PURPLE));

        return BookEntryModel.builder()
                .withId(this.modLoc(helper.category + "/" + helper.entry))
                .withName(helper.entryName())
                .withIcon(ForgeRegistries.ITEMS.getKey(Items.MINECART).toString())
                .withLocation(entryHelper.get(icon))
                .withPages(
                        intro,
                        intro2,
                        spirit_inventories,
                        itemFilters,
                        ritual,
                        bookOfCalling
                );
    }

    private BookEntryModel.Builder makeSummonCleanerEntry(BookLangHelper helper, EntryLocationHelper entryHelper, char icon) {
        helper.entry("summon_cleaner");
        this.lang.add(helper.entryName(), "Summon Foliot Janitor");

        helper.page("intro");
        var intro = BookTextPageModel.builder()
                .withTitle(helper.pageTitle())
                .withText(helper.pageText())
                .build();
        this.lang.add(helper.pageTitle(), "Foliot Janitor");
        this.lang.add(helper.pageText(),
                """
                        The janitor will pick up dropped items and deposit them into a target inventory. You can configure an allow/block list to specify which items to pick up or ignore. **Warning**: By default it is set to "allow" mode, so it will only pick up items you specify in the allow list.
                        You can use tags to handle whole groups of items.
                          """);

        helper.page("intro2");
        var intro2 = BookTextPageModel.builder()
                .withText(helper.pageText())
                .build();
        this.lang.add(helper.pageText(),
                """
                        To bind the janitor to an inventory simply sneak and interact with the janitor book of calling on that inventory. You can also interact with a block while holding the janitor book of calling to have it deposit items there. You can also have it wander around a select area by pulling up that interface. To configure an allow/block list sneak and interact with the janitor.
                          """);

        helper.page("tip");
        var tip = BookTextPageModel.builder()
                .withTitle(helper.pageTitle())
                .withText(helper.pageText())
                .build();
        this.lang.add(helper.pageTitle(), "Pro tip");
        this.lang.add(helper.pageText(),
                """
                        The Janitor will pick up crushed items from a [Crusher spirit](entry://summoning_rituals/summon_crusher_t1) and deposit them into a chest.
                        \\
                        \\
                        Combine that with a [Transporter Spirit](entry://summoning_rituals/summon_transport_items) to automate the whole process.
                           """);

        helper.page("ritual");
        var ritual = BookRitualRecipePageModel.builder()
                .withRecipeId1(this.modLoc("ritual/summon_foliot_cleaner"))
                .build();
        //no text

        helper.page("book_of_calling");
        var bookOfCalling = BookCraftingRecipePageModel.builder()
                .withRecipeId1(this.modLoc("crafting/book_of_calling_foliot_cleaner"))
                .withText(helper.pageText())
                .build();
        this.lang.add(helper.pageText(),
                """
                        If you lose the book of calling, you can craft a new one.
                        [#](%1$s)Shift-right-click[#]() the spirit with the crafted book to assign it.
                        """.formatted(COLOR_PURPLE));

        return BookEntryModel.builder()
                .withId(this.modLoc(helper.category + "/" + helper.entry))
                .withName(helper.entryName())
                .withIcon(OccultismItems.BRUSH.getId().toString())
                .withLocation(entryHelper.get(icon))
                .withPages(
                        intro,
                        intro2,
                        tip,
                        ritual,
                        bookOfCalling
                );
    }

    private BookEntryModel.Builder makeSummonManageMachineEntry(BookLangHelper helper, EntryLocationHelper entryHelper, char icon) {
        helper.entry("summon_manage_machine");

        helper.page("intro");
        var intro = BookTextPageModel.builder()
                .withTitle(helper.pageTitle())
                .withText(helper.pageText())
                .build();

        helper.page("tutorial");
        var tutorial = BookTextPageModel.builder()
                .withText(helper.pageText())
                .build();

        helper.page("tutorial2");
        var tutorial2 = BookTextPageModel.builder()
                .withText(helper.pageText())
                .build();

        helper.page("ritual");
        var ritual = BookRitualRecipePageModel.builder()
                .withRecipeId1(this.modLoc("ritual/summon_djinni_manage_machine"))
                .build();

        helper.page("book_of_calling");
        var bookOfCalling = BookCraftingRecipePageModel.builder()
                .withRecipeId1(this.modLoc("crafting/book_of_calling_djinni_manage_machine"))
                .withText(helper.pageText())
                .build();

        return BookEntryModel.builder()
                .withId(this.modLoc(helper.category + "/" + helper.entry))
                .withName(helper.entryName())
                .withIcon(ForgeRegistries.ITEMS.getKey(Items.LEVER).toString())
                .withLocation(entryHelper.get(icon))
                .withPages(
                        intro,
                        tutorial,
                        tutorial2,
                        ritual,
                        bookOfCalling
                );
    }

    private BookEntryModel.Builder makeTradeSpiritsEntry(BookLangHelper helper, EntryLocationHelper entryHelper, char icon) {
        helper.entry("trade_spirits");

        helper.page("intro");
        var intro = BookTextPageModel.builder()
                .withTitle(helper.pageTitle())
                .withText(helper.pageText())
                .build();

        helper.page("intro2");
        var intro2 = BookTextPageModel.builder()
                .withText(helper.pageText())
                .build();

        return BookEntryModel.builder()
                .withId(this.modLoc(helper.category + "/" + helper.entry))
                .withName(helper.entryName())
                .withIcon("occultism:textures/gui/book/cash.png")
                .withLocation(entryHelper.get(icon))
                .withPages(
                        intro,
                        intro2
                );
    }

    private BookEntryModel.Builder makeSummonOtherstoneTraderEntry(BookLangHelper helper, EntryLocationHelper entryHelper, char icon) {
        helper.entry("summon_otherstone_trader");

        helper.page("intro");
        var intro = BookTextPageModel.builder()
                .withTitle(helper.pageTitle())
                .withText(helper.pageText())
                .build();

        helper.page("trade");
        var trade = BookSpiritTradeRecipePageModel.builder()
                .withRecipeId1(this.modLoc("spirit_trade/stone_to_otherstone"))
                .build();

        helper.page("ritual");
        var ritual = BookRitualRecipePageModel.builder()
                .withRecipeId1(this.modLoc("ritual/summon_foliot_otherstone_trader"))
                .build();

        return BookEntryModel.builder()
                .withId(this.modLoc(helper.category + "/" + helper.entry))
                .withName(helper.entryName())
                .withIcon(OccultismBlocks.OTHERSTONE.getId().toString())
                .withLocation(entryHelper.get(icon))
                .withPages(
                        intro,
                        trade,
                        ritual
                );
    }

    private BookEntryModel.Builder makeSummonOtherworldSaplingTraderEntry(BookLangHelper helper, EntryLocationHelper entryHelper, char icon) {
        helper.entry("summon_otherworld_sapling_trader");

        helper.page("intro");
        var intro = BookTextPageModel.builder()
                .withTitle(helper.pageTitle())
                .withText(helper.pageText())
                .build();

        helper.page("trade");
        var trade = BookSpiritTradeRecipePageModel.builder()
                .withRecipeId1(this.modLoc("spirit_trade/otherworld_sapling"))
                .build();

        helper.page("ritual");
        var ritual = BookRitualRecipePageModel.builder()
                .withRecipeId1(this.modLoc("ritual/summon_foliot_sapling_trader"))
                .build();

        return BookEntryModel.builder()
                .withId(this.modLoc(helper.category + "/" + helper.entry))
                .withName(helper.entryName())
                .withIcon(OccultismBlocks.OTHERWORLD_SAPLING.getId().toString())
                .withLocation(entryHelper.get(icon))
                .withPages(
                        intro,
                        trade,
                        ritual
                );
    }

    private BookEntryModel.Builder makeSummonWildParrotEntry(BookLangHelper helper, EntryLocationHelper entryHelper, char icon) {
        helper.entry("summon_wild_parrot");

        helper.page("entity");
        var entity = BookEntityPageModel.builder()
                .withEntityId("minecraft:parrot")
                .withText(helper.pageText())
                .build();

        helper.page("ritual");
        var ritual = BookRitualRecipePageModel.builder()
                .withRecipeId1(this.modLoc("ritual/summon_wild_parrot"))
                .build();

        helper.page("description");
        var description = BookTextPageModel.builder()
                .withText(helper.pageText())
                .build();

        helper.page("description2");
        var description2 = BookTextPageModel.builder()
                .withText(helper.pageText())
                .build();


        return BookEntryModel.builder()
                .withId(this.modLoc(helper.category + "/" + helper.entry))
                .withName(helper.entryName())
                .withIcon("occultism:textures/gui/book/parrot.png")
                .withLocation(entryHelper.get(icon))
                .withPages(
                        entity,
                        ritual,
                        description,
                        description2
                );
    }

    private BookEntryModel.Builder makeSummonWildOtherworldBirdEntry(BookLangHelper helper, EntryLocationHelper entryHelper, char icon) {
        helper.entry("summon_wild_otherworld_bird");

        helper.page("entity");
        var entity = BookEntityPageModel.builder()
                .withEntityId("occultism:otherworld_bird")
                .withText(helper.pageText())
                .build();

        helper.page("ritual");
        var ritual = BookRitualRecipePageModel.builder()
                .withRecipeId1(this.modLoc("ritual/summon_wild_otherworld_bird"))
                .build();

        helper.page("description");
        var description = BookTextPageModel.builder()
                .withText(helper.pageText())
                .build();

        return BookEntryModel.builder()
                .withId(this.modLoc(helper.category + "/" + helper.entry))
                .withName(helper.entryName())
                .withIcon("occultism:textures/gui/book/otherworld_bird.png")
                .withLocation(entryHelper.get(icon))
                .withPages(
                        entity,
                        ritual,
                        description
                );
    }

    private BookEntryModel.Builder makeWeatherMagicEntry(BookLangHelper helper, EntryLocationHelper entryHelper, char icon) {
        helper.entry("weather_magic");

        helper.page("intro");
        var intro = BookTextPageModel.builder()
                .withTitle(helper.pageTitle())
                .withText(helper.pageText())
                .build();

        helper.page("ritual_clear");
        var ritualClear = BookRitualRecipePageModel.builder()
                .withRecipeId1(this.modLoc("ritual/summon_djinni_clear_weather"))
                .withAnchor("clear")
                .build();

        helper.page("ritual_rain");
        var ritualRain = BookRitualRecipePageModel.builder()
                .withRecipeId1(this.modLoc("ritual/summon_afrit_rain_weather"))
                .withAnchor("rain")
                .build();

        helper.page("ritual_thunder");
        var ritualThunder = BookRitualRecipePageModel.builder()
                .withRecipeId1(this.modLoc("ritual/summon_afrit_thunder_weather"))
                .withAnchor("thunder")
                .build();

        return BookEntryModel.builder()
                .withId(this.modLoc(helper.category + "/" + helper.entry))
                .withName(helper.entryName())
                .withIcon(ForgeRegistries.ITEMS.getKey(Items.WHEAT).toString())
                .withLocation(entryHelper.get(icon))
                .withPages(
                        intro,
                        ritualClear,
                        ritualRain,
                        ritualThunder
                );
    }

    private BookEntryModel.Builder makeTimeMagicEntry(BookLangHelper helper, EntryLocationHelper entryHelper, char icon) {
        helper.entry("time_magic");

        helper.page("intro");
        var intro = BookTextPageModel.builder()
                .withTitle(helper.pageTitle())
                .withText(helper.pageText())
                .build();

        helper.page("ritual_day");
        var ritualDay = BookRitualRecipePageModel.builder()
                .withRecipeId1(this.modLoc("ritual/summon_djinni_day_time"))
                .withAnchor("day")
                .build();

        helper.page("ritual_night");
        var ritualNight = BookRitualRecipePageModel.builder()
                .withRecipeId1(this.modLoc("ritual/summon_djinni_night_time"))
                .withAnchor("night")
                .build();

        return BookEntryModel.builder()
                .withId(this.modLoc(helper.category + "/" + helper.entry))
                .withName(helper.entryName())
                .withIcon(ForgeRegistries.ITEMS.getKey(Items.CLOCK).toString())
                .withLocation(entryHelper.get(icon))
                .withPages(
                        intro,
                        ritualDay,
                        ritualNight
                );
    }

    private BookEntryModel.Builder makeWitherSkullEntry(BookLangHelper helper, EntryLocationHelper entryHelper, char icon) {
        helper.entry("wither_skull");

        helper.page("intro");
        var intro = BookTextPageModel.builder()
                .withTitle(helper.pageTitle())
                .withText(helper.pageText())
                .build();

        helper.page("ritual");
        var ritual = BookRitualRecipePageModel.builder()
                .withRecipeId1(this.modLoc("ritual/summon_wild_hunt"))
                .build();

        return BookEntryModel.builder()
                .withId(this.modLoc(helper.category + "/" + helper.entry))
                .withName(helper.entryName())
                .withIcon(ForgeRegistries.ITEMS.getKey(Items.WITHER_SKELETON_SKULL).toString())
                .withLocation(entryHelper.get(icon))
                .withPages(
                        intro,
                        ritual
                );
    }
    //endregion

    //region Crafting Rituals
    private BookCategoryModel makeCraftingRitualsSubcategory(BookLangHelper helper) {
        helper.category("crafting_rituals");

        var entryHelper = ModonomiconAPI.get().getEntryLocationHelper();
        entryHelper.setMap(
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

        var overview = this.makeCraftingRitualsOverviewEntry(helper, entryHelper, '0');
        var returnToRituals = this.makeReturnToRitualsEntry(helper, entryHelper, '9');
        returnToRituals.withParent(BookEntryParentModel.builder().withEntryId(overview.id).build());
        returnToRituals.withCondition(BookTrueConditionModel.builder().build());

        var craftInfusedPickaxe = this.makeCraftInfusedPickaxeEntry(helper, entryHelper, 'd');
        craftInfusedPickaxe.withParent(BookEntryParentModel.builder().withEntryId(overview.id).build());
        var craftDimensionalMineshaft = this.makeCraftDimensionalMineshaftEntry(helper, entryHelper, 'b');
        craftDimensionalMineshaft.withParent(BookEntryParentModel.builder().withEntryId(craftInfusedPickaxe.id).build());
        var craftFoliotMiner = this.makeCraftFoliotMinerEntry(helper, entryHelper, 'e');
        craftFoliotMiner.withParent(BookEntryParentModel.builder().withEntryId(craftDimensionalMineshaft.id).build());
        var craftDjinniMiner = this.makeCraftDjinniMinerEntry(helper, entryHelper, 'x');
        craftDjinniMiner.withParent(BookEntryParentModel.builder().withEntryId(craftFoliotMiner.id).build());
        var craftAfritMiner = this.makeCraftAfritMinerEntry(helper, entryHelper, 'p');
        craftAfritMiner.withParent(BookEntryParentModel.builder().withEntryId(craftDjinniMiner.id).build());
        var craftMaridMiner = this.makeCraftMaridMinerEntry(helper, entryHelper, 'q');
        craftMaridMiner.withParent(BookEntryParentModel.builder().withEntryId(craftAfritMiner.id).build());

        var craftStorageSystem = this.makeCraftStorageSystemEntry(helper, entryHelper, 'z');
        craftStorageSystem.withParent(BookEntryParentModel.builder().withEntryId(overview.id).build());

        var craftDimensionalMatrix = this.makeCraftDimensionalMatrixEntry(helper, entryHelper, 'a');
        craftDimensionalMatrix.withParent(BookEntryParentModel.builder().withEntryId(craftStorageSystem.id).build());
        var craftStorageControllerBase = this.makeCraftStorageControllerBaseEntry(helper, entryHelper, 'n');
        craftStorageControllerBase.withParent(BookEntryParentModel.builder().withEntryId(craftDimensionalMatrix.id).build());
        var craftStabilizerTier1 = this.makeCraftStabilizerTier1Entry(helper, entryHelper, 'i');
        craftStabilizerTier1.withParent(BookEntryParentModel.builder().withEntryId(craftStorageControllerBase.id).build());
        var craftStabilizerTier2 = this.makeCraftStabilizerTier2Entry(helper, entryHelper, 'j');
        craftStabilizerTier2.withParent(BookEntryParentModel.builder().withEntryId(craftStabilizerTier1.id).build());
        var craftStabilizerTier3 = this.makeCraftStabilizerTier3Entry(helper, entryHelper, 'k');
        craftStabilizerTier3.withParent(BookEntryParentModel.builder().withEntryId(craftStabilizerTier2.id).build());
        var craftStabilizerTier4 = this.makeCraftStabilizerTier4Entry(helper, entryHelper, 'l');
        craftStabilizerTier4.withParent(BookEntryParentModel.builder().withEntryId(craftStabilizerTier3.id).build());

        var craftStableWormhole = this.makeCraftStableWormholeEntry(helper, entryHelper, 'm');
        craftStableWormhole.withParent(BookEntryParentModel.builder().withEntryId(craftStorageControllerBase.id).build());
        var craftStorageRemote = this.makeCraftStorageRemoteEntry(helper, entryHelper, 'o');
        craftStorageRemote.withParent(BookEntryParentModel.builder().withEntryId(craftStableWormhole.id).build());

        var craftOtherworldGoggles = this.makeCraftOtherworldGogglesEntry(helper, entryHelper, 'f');
        craftOtherworldGoggles.withParent(BookEntryParentModel.builder().withEntryId(overview.id).build());

        var craftSatchel = this.makeCraftSatchelEntry(helper, entryHelper, 'g');
        craftSatchel.withParent(BookEntryParentModel.builder().withEntryId(overview.id).build());

        var craftSoulGem = this.makeCraftSoulGemEntry(helper, entryHelper, 'h');
        craftSoulGem.withParent(BookEntryParentModel.builder().withEntryId(overview.id).build());
        var craftFamiliarRing = this.makeCraftFamiliarRingEntry(helper, entryHelper, 'c');
        craftFamiliarRing.withParent(BookEntryParentModel.builder().withEntryId(craftSoulGem.id).build());

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

        return BookCategoryModel.create(this.modLoc(helper.category), helper.categoryName())
                .withIcon("occultism:textures/gui/book/infusion.png")
                .withShowCategoryButton(false)
                .withEntries(
                        overview.build(),
                        returnToRituals.build(),
                        craftStorageSystem.build(),
                        craftDimensionalMatrix.build(),
                        craftDimensionalMineshaft.build(),
                        craftInfusedPickaxe.build(),
                        craftFoliotMiner.build(),
                        craftDjinniMiner.build(),
                        craftAfritMiner.build(),
                        craftMaridMiner.build(),
                        craftOtherworldGoggles.build(),
                        craftSatchel.build(),
                        craftSoulGem.build(),
                        craftFamiliarRing.build(),
                        craftStabilizerTier1.build(),
                        craftStabilizerTier2.build(),
                        craftStabilizerTier3.build(),
                        craftStabilizerTier4.build(),
                        craftStableWormhole.build(),
                        craftStorageControllerBase.build(),
                        craftStorageRemote.build()
                );
    }

    private BookEntryModel.Builder makeCraftingRitualsOverviewEntry(BookLangHelper helper, EntryLocationHelper entryHelper, char icon) {
        helper.entry("overview");

        helper.page("intro");
        var intro = BookTextPageModel.builder()
                .withTitle(helper.pageTitle())
                .withText(helper.pageText())
                .build();

        return BookEntryModel.builder()
                .withId(this.modLoc(helper.category + "/" + helper.entry))
                .withName(helper.entryName())
                .withIcon("occultism:textures/gui/book/infusion.png")
                .withLocation(entryHelper.get(icon))
                .withEntryBackground(0, 1)
                .withPages(
                        intro
                );
    }

    private BookEntryModel.Builder makeCraftStorageSystemEntry(BookLangHelper helper, EntryLocationHelper entryHelper, char icon) {
        helper.entry("craft_storage_system");

        helper.page("spotlight");
        var spotlight = BookSpotlightPageModel.builder()
                .withItem(Ingredient.of(OccultismBlocks.STORAGE_CONTROLLER.get()))
                .withText(helper.pageText())
                .build();

        return BookEntryModel.builder()
                .withId(this.modLoc(helper.category + "/" + helper.entry))
                .withName(helper.entryName())
                .withIcon("minecraft:chest")
                .withLocation(entryHelper.get(icon))
                .withPages(
                        spotlight
                );
    }

    private BookEntryModel.Builder makeCraftDimensionalMatrixEntry(BookLangHelper helper, EntryLocationHelper entryHelper, char icon) {
        helper.entry("craft_dimensional_matrix");

        helper.page("spotlight");
        var spotlight = BookSpotlightPageModel.builder()
                .withItem(Ingredient.of(OccultismItems.DIMENSIONAL_MATRIX.get()))
                .withText(helper.pageText())
                .build();

        helper.page("ritual");
        var ritual = BookRitualRecipePageModel.builder()
                .withRecipeId1(this.modLoc("ritual/craft_dimensional_matrix"))
                .build();

        return BookEntryModel.builder()
                .withId(this.modLoc(helper.category + "/" + helper.entry))
                .withName(helper.entryName())
                .withIcon(OccultismItems.DIMENSIONAL_MATRIX.getId().toString())
                .withLocation(entryHelper.get(icon))
                .withPages(
                        spotlight,
                        ritual
                );
    }

    private BookEntryModel.Builder makeCraftDimensionalMineshaftEntry(BookLangHelper helper, EntryLocationHelper entryHelper, char icon) {
        helper.entry("craft_dimensional_mineshaft");

        helper.page("spotlight");
        var spotlight = BookSpotlightPageModel.builder()
                .withItem(Ingredient.of(OccultismBlocks.DIMENSIONAL_MINESHAFT.get()))
                .withText(helper.pageText())
                .build();

        helper.page("ritual");
        var ritual = BookRitualRecipePageModel.builder()
                .withRecipeId1(this.modLoc("ritual/craft_dimensional_mineshaft"))
                .build();

        helper.page("description");
        var description = BookTextPageModel.builder()
                .withTitle(helper.pageTitle())
                .withText(helper.pageText())
                .build();

        return BookEntryModel.builder()
                .withId(this.modLoc(helper.category + "/" + helper.entry))
                .withName(helper.entryName())
                .withIcon(OccultismBlocks.DIMENSIONAL_MINESHAFT.getId().toString())
                .withLocation(entryHelper.get(icon))
                .withPages(
                        spotlight,
                        ritual,
                        description
                );
    }

    private BookEntryModel.Builder makeCraftInfusedPickaxeEntry(BookLangHelper helper, EntryLocationHelper entryHelper, char icon) {
        helper.entry("craft_infused_pickaxe");

        helper.page("spotlight");
        var spotlight = BookSpotlightPageModel.builder()
                .withItem(Ingredient.of(OccultismItems.INFUSED_PICKAXE.get()))
                .withText(helper.pageText())
                .build();

        helper.page("ritual");
        var ritual = BookRitualRecipePageModel.builder()
                .withRecipeId1(this.modLoc("ritual/craft_infused_pickaxe"))
                .build();

        return BookEntryModel.builder()
                .withId(this.modLoc(helper.category + "/" + helper.entry))
                .withName(helper.entryName())
                .withIcon(OccultismItems.INFUSED_PICKAXE.getId().toString())
                .withLocation(entryHelper.get(icon))
                .withPages(
                        spotlight,
                        ritual
                );
    }

    private BookEntryModel.Builder makeCraftStorageControllerBaseEntry(BookLangHelper helper, EntryLocationHelper entryHelper, char icon) {
        helper.entry("craft_storage_controller_base");

        helper.page("spotlight");
        var spotlight = BookSpotlightPageModel.builder()
                .withItem(Ingredient.of(OccultismBlocks.STORAGE_CONTROLLER_BASE.get()))
                .withText(helper.pageText())
                .build();

        helper.page("ritual");
        var ritual = BookRitualRecipePageModel.builder()
                .withRecipeId1(this.modLoc("ritual/craft_storage_controller_base"))
                .build();

        return BookEntryModel.builder()
                .withId(this.modLoc(helper.category + "/" + helper.entry))
                .withName(helper.entryName())
                .withIcon(OccultismBlocks.STORAGE_CONTROLLER_BASE.getId().toString())
                .withLocation(entryHelper.get(icon))
                .withPages(
                        spotlight,
                        ritual
                );
    }

    private BookEntryModel.Builder makeCraftStabilizerTier1Entry(BookLangHelper helper, EntryLocationHelper entryHelper, char icon) {
        helper.entry("craft_stabilizer_tier1");

        helper.page("spotlight");
        var spotlight = BookSpotlightPageModel.builder()
                .withItem(Ingredient.of(OccultismBlocks.STORAGE_STABILIZER_TIER1.get()))
                .withText(helper.pageText())
                .build();

        helper.page("ritual");
        var ritual = BookRitualRecipePageModel.builder()
                .withRecipeId1(this.modLoc("ritual/craft_stabilizer_tier1"))
                .build();

        return BookEntryModel.builder()
                .withId(this.modLoc(helper.category + "/" + helper.entry))
                .withName(helper.entryName())
                .withIcon(OccultismBlocks.STORAGE_STABILIZER_TIER1.getId().toString())
                .withLocation(entryHelper.get(icon))
                .withPages(
                        spotlight,
                        ritual
                );
    }

    private BookEntryModel.Builder makeCraftStabilizerTier2Entry(BookLangHelper helper, EntryLocationHelper entryHelper, char icon) {
        helper.entry("craft_stabilizer_tier2");

        helper.page("spotlight");
        var spotlight = BookSpotlightPageModel.builder()
                .withItem(Ingredient.of(OccultismBlocks.STORAGE_STABILIZER_TIER2.get()))
                .withText(helper.pageText())
                .build();

        helper.page("ritual");
        var ritual = BookRitualRecipePageModel.builder()
                .withRecipeId1(this.modLoc("ritual/craft_stabilizer_tier2"))
                .build();

        return BookEntryModel.builder()
                .withId(this.modLoc(helper.category + "/" + helper.entry))
                .withName(helper.entryName())
                .withIcon(OccultismBlocks.STORAGE_STABILIZER_TIER2.getId().toString())
                .withLocation(entryHelper.get(icon))
                .withPages(
                        spotlight,
                        ritual
                );
    }

    private BookEntryModel.Builder makeCraftStabilizerTier3Entry(BookLangHelper helper, EntryLocationHelper entryHelper, char icon) {
        helper.entry("craft_stabilizer_tier3");

        helper.page("spotlight");
        var spotlight = BookSpotlightPageModel.builder()
                .withItem(Ingredient.of(OccultismBlocks.STORAGE_STABILIZER_TIER3.get()))
                .withText(helper.pageText())
                .build();

        helper.page("ritual");
        var ritual = BookRitualRecipePageModel.builder()
                .withRecipeId1(this.modLoc("ritual/craft_stabilizer_tier3"))
                .build();

        return BookEntryModel.builder()
                .withId(this.modLoc(helper.category + "/" + helper.entry))
                .withName(helper.entryName())
                .withIcon(OccultismBlocks.STORAGE_STABILIZER_TIER3.getId().toString())
                .withLocation(entryHelper.get(icon))
                .withPages(
                        spotlight,
                        ritual
                );
    }

    private BookEntryModel.Builder makeCraftStabilizerTier4Entry(BookLangHelper helper, EntryLocationHelper entryHelper, char icon) {
        helper.entry("craft_stabilizer_tier4");

        helper.page("spotlight");
        var spotlight = BookSpotlightPageModel.builder()
                .withItem(Ingredient.of(OccultismBlocks.STORAGE_STABILIZER_TIER4.get()))
                .withText(helper.pageText())
                .build();

        helper.page("ritual");
        var ritual = BookRitualRecipePageModel.builder()
                .withRecipeId1(this.modLoc("ritual/craft_stabilizer_tier4"))
                .build();

        return BookEntryModel.builder()
                .withId(this.modLoc(helper.category + "/" + helper.entry))
                .withName(helper.entryName())
                .withIcon(OccultismBlocks.STORAGE_STABILIZER_TIER4.getId().toString())
                .withLocation(entryHelper.get(icon))
                .withPages(
                        spotlight,
                        ritual
                );
    }

    private BookEntryModel.Builder makeCraftStableWormholeEntry(BookLangHelper helper, EntryLocationHelper entryHelper, char icon) {
        helper.entry("craft_stable_wormhole");

        helper.page("spotlight");
        var spotlight = BookSpotlightPageModel.builder()
                .withItem(Ingredient.of(OccultismBlocks.STABLE_WORMHOLE.get()))
                .withText(helper.pageText())
                .build();

        helper.page("ritual");
        var ritual = BookRitualRecipePageModel.builder()
                .withRecipeId1(this.modLoc("ritual/craft_stable_wormhole"))
                .build();

        return BookEntryModel.builder()
                .withId(this.modLoc(helper.category + "/" + helper.entry))
                .withName(helper.entryName())
                .withIcon(OccultismBlocks.STABLE_WORMHOLE.getId().toString())
                .withLocation(entryHelper.get(icon))
                .withPages(
                        spotlight,
                        ritual
                );
    }

    private BookEntryModel.Builder makeCraftStorageRemoteEntry(BookLangHelper helper, EntryLocationHelper entryHelper, char icon) {
        helper.entry("craft_storage_remote");

        helper.page("spotlight");
        var spotlight = BookSpotlightPageModel.builder()
                .withItem(Ingredient.of(OccultismItems.STORAGE_REMOTE.get()))
                .withText(helper.pageText())
                .build();

        helper.page("ritual");
        var ritual = BookRitualRecipePageModel.builder()
                .withRecipeId1(this.modLoc("ritual/craft_storage_remote"))
                .build();

        return BookEntryModel.builder()
                .withId(this.modLoc(helper.category + "/" + helper.entry))
                .withName(helper.entryName())
                .withIcon(OccultismItems.STORAGE_REMOTE.getId().toString())
                .withLocation(entryHelper.get(icon))
                .withPages(
                        spotlight,
                        ritual
                );
    }

    private BookEntryModel.Builder makeCraftFoliotMinerEntry(BookLangHelper helper, EntryLocationHelper entryHelper, char icon) {
        helper.entry("craft_foliot_miner");

        helper.page("intro");
        var intro = BookTextPageModel.builder()
                .withTitle(helper.pageTitle())
                .withText(helper.pageText())
                .build();

        helper.page("magic_lamp");
        var lamp = BookTextPageModel.builder()
                .withTitle(helper.pageTitle())
                .withText(helper.pageText())
                .build();

        helper.page("magic_lamp_recipe");
        var lampRecipe = BookCraftingRecipePageModel.builder()
                .withRecipeId1(this.modLoc("crafting/magic_lamp_empty"))
                .build();

        helper.page("spotlight");
        var spotlight = BookSpotlightPageModel.builder()
                .withItem(Ingredient.of(OccultismItems.MINER_FOLIOT_UNSPECIALIZED.get()))
                .withText(helper.pageText())
                .build();

        helper.page("ritual");
        var ritual = BookRitualRecipePageModel.builder()
                .withRecipeId1(this.modLoc("ritual/craft_miner_foliot_unspecialized"))
                .build();

        return BookEntryModel.builder()
                .withId(this.modLoc(helper.category + "/" + helper.entry))
                .withName(helper.entryName())
                .withIcon(OccultismItems.MINER_FOLIOT_UNSPECIALIZED.getId().toString())
                .withLocation(entryHelper.get(icon))
                .withPages(
                        intro,
                        lamp,
                        lampRecipe,
                        spotlight,
                        ritual
                );
    }

    private BookEntryModel.Builder makeCraftDjinniMinerEntry(BookLangHelper helper, EntryLocationHelper entryHelper, char icon) {
        helper.entry("craft_djinni_miner");

        helper.page("spotlight");
        var spotlight = BookSpotlightPageModel.builder()
                .withItem(Ingredient.of(OccultismItems.MINER_DJINNI_ORES.get()))
                .withText(helper.pageText())
                .build();

        helper.page("ritual");
        var ritual = BookRitualRecipePageModel.builder()
                .withRecipeId1(this.modLoc("ritual/craft_miner_djinni_ores"))
                .build();

        return BookEntryModel.builder()
                .withId(this.modLoc(helper.category + "/" + helper.entry))
                .withName(helper.entryName())
                .withIcon(OccultismItems.MINER_DJINNI_ORES.getId().toString())
                .withLocation(entryHelper.get(icon))
                .withPages(
                        spotlight,
                        ritual
                );
    }

    private BookEntryModel.Builder makeCraftAfritMinerEntry(BookLangHelper helper, EntryLocationHelper entryHelper, char icon) {
        helper.entry("craft_afrit_miner");

        helper.page("spotlight");
        var spotlight = BookSpotlightPageModel.builder()
                .withItem(Ingredient.of(OccultismItems.MINER_AFRIT_DEEPS.get()))
                .withText(helper.pageText())
                .build();

        helper.page("ritual");
        var ritual = BookRitualRecipePageModel.builder()
                .withRecipeId1(this.modLoc("ritual/craft_miner_afrit_deeps"))
                .build();

        return BookEntryModel.builder()
                .withId(this.modLoc(helper.category + "/" + helper.entry))
                .withName(helper.entryName())
                .withIcon(OccultismItems.MINER_AFRIT_DEEPS.getId().toString())
                .withLocation(entryHelper.get(icon))
                .withPages(
                        spotlight,
                        ritual
                );
    }

    private BookEntryModel.Builder makeCraftMaridMinerEntry(BookLangHelper helper, EntryLocationHelper entryHelper, char icon) {
        helper.entry("craft_marid_miner");

        helper.page("spotlight");
        var spotlight = BookSpotlightPageModel.builder()
                .withItem(Ingredient.of(OccultismItems.MINER_MARID_MASTER.get()))
                .withText(helper.pageText())
                .build();

        helper.page("ritual");
        var ritual = BookRitualRecipePageModel.builder()
                .withRecipeId1(this.modLoc("ritual/craft_miner_marid_master"))
                .build();

        return BookEntryModel.builder()
                .withId(this.modLoc(helper.category + "/" + helper.entry))
                .withName(helper.entryName())
                .withIcon(OccultismItems.MINER_MARID_MASTER.getId().toString())
                .withLocation(entryHelper.get(icon))
                .withPages(
                        spotlight,
                        ritual
                );
    }

    private BookEntryModel.Builder makeCraftSatchelEntry(BookLangHelper helper, EntryLocationHelper entryHelper, char icon) {
        helper.entry("craft_satchel");

        helper.page("spotlight");
        var spotlight = BookSpotlightPageModel.builder()
                .withItem(Ingredient.of(OccultismItems.SATCHEL.get()))
                .withText(helper.pageText())
                .build();

        helper.page("ritual");
        var ritual = BookRitualRecipePageModel.builder()
                .withRecipeId1(this.modLoc("ritual/craft_satchel"))
                .build();

        return BookEntryModel.builder()
                .withId(this.modLoc(helper.category + "/" + helper.entry))
                .withName(helper.entryName())
                .withIcon(OccultismItems.SATCHEL.getId().toString())
                .withLocation(entryHelper.get(icon))
                .withPages(
                        spotlight,
                        ritual
                );
    }

    private BookEntryModel.Builder makeCraftSoulGemEntry(BookLangHelper helper, EntryLocationHelper entryHelper, char icon) {
        helper.entry("craft_soul_gem");

        helper.page("spotlight");
        var spotlight = BookSpotlightPageModel.builder()
                .withItem(Ingredient.of(OccultismItems.SOUL_GEM_ITEM.get()))
                .withText(helper.pageText())
                .build();

        helper.page("usage");
        var usage = BookTextPageModel.builder()
                .withTitle(helper.pageTitle())
                .withText(helper.pageText())
                .build();

        helper.page("ritual");
        var ritual = BookRitualRecipePageModel.builder()
                .withRecipeId1(this.modLoc("ritual/craft_soul_gem"))
                .build();

        return BookEntryModel.builder()
                .withId(this.modLoc(helper.category + "/" + helper.entry))
                .withName(helper.entryName())
                .withIcon(OccultismItems.SOUL_GEM_ITEM.getId().toString())
                .withLocation(entryHelper.get(icon))
                .withPages(
                        spotlight,
                        usage,
                        ritual
                );
    }

    private BookEntryModel.Builder makeCraftFamiliarRingEntry(BookLangHelper helper, EntryLocationHelper entryHelper, char icon) {
        helper.entry("craft_familiar_ring");

        helper.page("spotlight");
        var spotlight = BookSpotlightPageModel.builder()
                .withItem(Ingredient.of(OccultismItems.FAMILIAR_RING.get()))
                .withText(helper.pageText())
                .build();

        helper.page("usage");
        var usage = BookTextPageModel.builder()
                .withTitle(helper.pageTitle())
                .withText(helper.pageText())
                .build();

        helper.page("ritual");
        var ritual = BookRitualRecipePageModel.builder()
                .withRecipeId1(this.modLoc("ritual/craft_familiar_ring"))
                .build();

        return BookEntryModel.builder()
                .withId(this.modLoc(helper.category + "/" + helper.entry))
                .withName(helper.entryName())
                .withIcon(OccultismItems.FAMILIAR_RING.getId().toString())
                .withLocation(entryHelper.get(icon))
                .withPages(
                        spotlight,
                        usage,
                        ritual
                );
    }

    private BookEntryModel.Builder makeCraftOtherworldGogglesEntry(BookLangHelper helper, EntryLocationHelper entryHelper, char icon) {
        helper.entry("craft_otherworld_goggles");

        helper.page("goggles_spotlight");
        var gogglesSpotlight = BookSpotlightPageModel.builder()
                .withItem(Ingredient.of(OccultismItems.OTHERWORLD_GOGGLES.get()))
                .withText(helper.pageText())
                .build();

        helper.page("goggles_more");
        var gogglesMore = BookTextPageModel.builder()
                .withText(helper.pageText())
                .build();

        helper.page("lenses_spotlight");
        var lensesSpotlight = BookSpotlightPageModel.builder()
                .withItem(Ingredient.of(OccultismItems.LENSES.get()))
                .withText(helper.pageText())
                .build();

        helper.page("lenses_more");
        var lensesMore = BookTextPageModel.builder()
                .withTitle(helper.pageTitle())
                .withText(helper.pageText())
                .build();

        helper.page("lenses_recipe");
        var lensesRecipe = BookCraftingRecipePageModel.builder()
                .withRecipeId1(this.modLoc("crafting/lenses"))
                .build();

        helper.page("ritual");
        var ritual = BookRitualRecipePageModel.builder()
                .withRecipeId1(this.modLoc("ritual/craft_infused_lenses"))
                .build();

        helper.page("goggles_recipe");
        var gogglesRecipe = BookCraftingRecipePageModel.builder()
                .withRecipeId1(this.modLoc("crafting/goggles"))
                .build();

        return BookEntryModel.builder()
                .withId(this.modLoc(helper.category + "/" + helper.entry))
                .withName(helper.entryName())
                .withIcon(OccultismItems.OTHERWORLD_GOGGLES.getId().toString())
                .withLocation(entryHelper.get(icon))
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
    private BookCategoryModel makePossessionRitualsSubcategory(BookLangHelper helper) {
        helper.category("possession_rituals");

        var entryHelper = ModonomiconAPI.get().getEntryLocationHelper();
        entryHelper.setMap(
                "___________________________",
                "___________________________",
                "___________________________",
                "_______D_E_A_______________",
                "___r_o_____________________",
                "_______F_G_H_______________",
                "___________________________",
                "___________________________",
                "___________________________"
        );

        var overview = this.makePossessionRitualsOverviewEntry(helper, entryHelper, 'o');
        var returnToRituals = this.makeReturnToRitualsEntry(helper, entryHelper, 'r');
        returnToRituals.withParent(BookEntryParentModel.builder().withEntryId(overview.id).build());
        returnToRituals.withCondition(BookTrueConditionModel.builder().build());

        var possessEndermite = this.makePossessEndermiteEntry(helper, entryHelper, 'D');
        possessEndermite.withParent(BookEntryParentModel.builder().withEntryId(overview.id).build());
        var possessEnderman = this.makePossessEndermanEntry(helper, entryHelper, 'E');
        possessEnderman.withParent(BookEntryParentModel.builder().withEntryId(overview.id).build());
        var possessGhast = this.makePossessGhastEntry(helper, entryHelper, 'F');
        possessGhast.withParent(BookEntryParentModel.builder().withEntryId(overview.id).build());
        var possessSkeleton = this.makePossessSkeletonEntry(helper, entryHelper, 'G');
        possessSkeleton.withParent(BookEntryParentModel.builder().withEntryId(overview.id).build());

        helper.category("summoning_rituals"); //re-use the entries from the summoning rituals category
        var possessWitherSkeleton = this.makeWitherSkullEntry(helper, entryHelper, 'H');
        possessWitherSkeleton.withParent(BookEntryParentModel.builder().withEntryId(overview.id).build());
        var afritEssence = this.makeAfritEssenceEntry(helper, entryHelper, 'A');
        afritEssence.withParent(BookEntryParentModel.builder().withEntryId(overview.id).build());
        helper.category("possession_rituals");

        //add true condition to all entries to enable them by default
        overview.withCondition(BookTrueConditionModel.builder().build());
        possessEnderman.withCondition(BookTrueConditionModel.builder().build());
        possessEndermite.withCondition(BookTrueConditionModel.builder().build());
        possessGhast.withCondition(BookTrueConditionModel.builder().build());
        possessSkeleton.withCondition(BookTrueConditionModel.builder().build());
        possessWitherSkeleton.withCondition(BookTrueConditionModel.builder().build());
        afritEssence.withCondition(BookTrueConditionModel.builder().build());

        return BookCategoryModel.create(this.modLoc(helper.category), helper.categoryName())
                .withIcon("occultism:textures/gui/book/possession.png")
                .withShowCategoryButton(false)
                .withEntries(
                        overview.build(),
                        returnToRituals.build(),
                        possessEnderman.build(),
                        possessEndermite.build(),
                        possessGhast.build(),
                        possessSkeleton.build(),
                        possessWitherSkeleton.build(),
                        afritEssence.build()
                );
    }

    private BookEntryModel.Builder makePossessionRitualsOverviewEntry(BookLangHelper helper, EntryLocationHelper entryHelper, char icon) {
        helper.entry("overview");

        helper.page("intro");
        var intro = BookTextPageModel.builder()
                .withTitle(helper.pageTitle())
                .withText(helper.pageText())
                .build();

        return BookEntryModel.builder()
                .withId(this.modLoc(helper.category + "/" + helper.entry))
                .withName(helper.entryName())
                .withIcon("occultism:textures/gui/book/possession.png")
                .withLocation(entryHelper.get(icon))
                .withEntryBackground(0, 1)
                .withPages(
                        intro
                );
    }

    private BookEntryModel.Builder makePossessEndermanEntry(BookLangHelper helper, EntryLocationHelper entryHelper, char icon) {
        helper.entry("possess_enderman");

        helper.page("entity");
        var entity = BookEntityPageModel.builder()
                .withEntityId("occultism:possessed_enderman")
                .withText(helper.pageText())
                .build();

        helper.page("ritual");
        var ritual = BookRitualRecipePageModel.builder()
                .withRecipeId1(this.modLoc("ritual/possess_enderman"))
                .build();

        helper.page("description");
        var description = BookTextPageModel.builder()
                .withText(helper.pageText())
                .build();

        return BookEntryModel.builder()
                .withId(this.modLoc(helper.category + "/" + helper.entry))
                .withName(helper.entryName())
                .withIcon(ForgeRegistries.ITEMS.getKey(Items.ENDER_PEARL).toString())
                .withLocation(entryHelper.get(icon))
                .withPages(
                        entity,
                        ritual,
                        description
                );
    }

    private BookEntryModel.Builder makePossessEndermiteEntry(BookLangHelper helper, EntryLocationHelper entryHelper, char icon) {
        helper.entry("possess_endermite");

        helper.page("entity");
        var entity = BookEntityPageModel.builder()
                .withEntityId("occultism:possessed_endermite")
                .withText(helper.pageText())
                .build();

        helper.page("ritual");
        var ritual = BookRitualRecipePageModel.builder()
                .withRecipeId1(this.modLoc("ritual/possess_endermite"))
                .build();

        helper.page("description");
        var description = BookTextPageModel.builder()
                .withText(helper.pageText())
                .build();

        return BookEntryModel.builder()
                .withId(this.modLoc(helper.category + "/" + helper.entry))
                .withName(helper.entryName())
                .withIcon(ForgeRegistries.BLOCKS.getKey(Blocks.END_STONE).toString())
                .withLocation(entryHelper.get(icon))
                .withPages(
                        entity,
                        ritual,
                        description
                );
    }

    private BookEntryModel.Builder makePossessGhastEntry(BookLangHelper helper, EntryLocationHelper entryHelper, char icon) {
        helper.entry("possess_ghast");
        this.lang.add(helper.entryName(), "Possessed Ghast");

        helper.page("entity");
        var entity = BookEntityPageModel.builder()
                .withEntityId("occultism:possessed_ghast")
                .withScale(0.5f)
                .withText(helper.pageText())
                .build();
        this.lang.add(helper.pageText(),
                """
                        **Drops**: 1-3x [](item://minecraft:ghast_tear)
                                """);

        helper.page("ritual");
        var ritual = BookRitualRecipePageModel.builder()
                .withRecipeId1(this.modLoc("ritual/possess_ghast"))
                .build();
        //no text

        helper.page("description");
        var description = BookTextPageModel.builder()
                .withText(helper.pageText())
                .build();
        this.lang.add(helper.pageText(),
                """
                        In this ritual a [#](%1$s)Ghast[#]() is spawned using the life energy of a [#](%1$s)Cow[#]() and immediately possessed by the summoned [#](%1$s)Djinni[#](). The [#](%1$s)Possessed Ghast[#]() will always drop at least one [](item://minecraft:ghast_tear) when killed.
                        """.formatted(COLOR_PURPLE));

        return BookEntryModel.builder()
                .withId(this.modLoc(helper.category + "/" + helper.entry))
                .withName(helper.entryName())
                .withIcon(ForgeRegistries.ITEMS.getKey(Items.GHAST_TEAR).toString())
                .withLocation(entryHelper.get(icon))
                .withPages(
                        entity,
                        ritual,
                        description
                );
    }

    private BookEntryModel.Builder makePossessSkeletonEntry(BookLangHelper helper, EntryLocationHelper entryHelper, char icon) {
        helper.entry("possess_skeleton");

        helper.page("entity");
        var entity = BookEntityPageModel.builder()
                .withEntityId("occultism:possessed_skeleton")
                .withText(helper.pageText())
                .build();

        helper.page("ritual");
        var ritual = BookRitualRecipePageModel.builder()
                .withRecipeId1(this.modLoc("ritual/possess_skeleton"))
                .build();

        helper.page("description");
        var description = BookTextPageModel.builder()
                .withText(helper.pageText())
                .build();

        return BookEntryModel.builder()
                .withId(this.modLoc(helper.category + "/" + helper.entry))
                .withName(helper.entryName())
                .withIcon(ForgeRegistries.ITEMS.getKey(Items.SKELETON_SKULL).toString())
                .withLocation(entryHelper.get(icon))
                .withPages(
                        entity,
                        ritual,
                        description
                );
    }
    //endregion

    //region Familiar Rituals
    private BookCategoryModel makeFamiliarRitualsSubcategory(BookLangHelper helper) {
        helper.category("familiar_rituals");

        var entryHelper = ModonomiconAPI.get().getEntryLocationHelper();
        entryHelper.setMap(
                "________R_T_V_X____________",
                "___________________________",
                "_______Q_S_U_W_____________",
                "___________________________",
                "___r_o_________Y___________",
                "___________________________",
                "_______I_K_M_O_____________",
                "___________________________",
                "________J_L_N_P____________"
        );

        var overview = this.makeFamiliarsRitualsOverviewEntry(helper, entryHelper, 'o');
        var returnToRituals = this.makeReturnToRitualsEntry(helper, entryHelper, 'r');
        returnToRituals.withParent(BookEntryParentModel.builder().withEntryId(overview.id).build());
        returnToRituals.withCondition(BookTrueConditionModel.builder().build());

        var familiarBat = this.makeFamiliarBatEntry(helper, entryHelper, 'I');
        familiarBat.withParent(BookEntryParentModel.builder().withEntryId(overview.id).build());
        var familiarBeaver = this.makeFamiliarBeaverEntry(helper, entryHelper, 'J');
        familiarBeaver.withParent(BookEntryParentModel.builder().withEntryId(overview.id).build());
        var familiarBeholder = this.makeFamiliarBeholderEntry(helper, entryHelper, 'K');
        familiarBeholder.withParent(BookEntryParentModel.builder().withEntryId(overview.id).build());
        var familiarBlacksmith = this.makeFamiliarBlacksmithEntry(helper, entryHelper, 'L');
        familiarBlacksmith.withParent(BookEntryParentModel.builder().withEntryId(overview.id).build());
        var familiarChimera = this.makeFamiliarChimeraEntry(helper, entryHelper, 'M');
        familiarChimera.withParent(BookEntryParentModel.builder().withEntryId(overview.id).build());
        var familiarCthulhu = this.makeFamiliarCthulhuEntry(helper, entryHelper, 'N');
        familiarCthulhu.withParent(BookEntryParentModel.builder().withEntryId(overview.id).build());
        var familiarDeer = this.makeFamiliarDeerEntry(helper, entryHelper, 'O');
        familiarDeer.withParent(BookEntryParentModel.builder().withEntryId(overview.id).build());
        var familiarDevil = this.makeFamiliarDevilEntry(helper, entryHelper, 'P');
        familiarDevil.withParent(BookEntryParentModel.builder().withEntryId(overview.id).build());
        var familiarDragon = this.makeFamiliarDragonEntry(helper, entryHelper, 'Q');
        familiarDragon.withParent(BookEntryParentModel.builder().withEntryId(overview.id).build());
        var familiarFairy = this.makeFamiliarFairyEntry(helper, entryHelper, 'R');
        familiarFairy.withParent(BookEntryParentModel.builder().withEntryId(overview.id).build());
        var familiarGreedy = this.makeFamiliarGreedyEntry(helper, entryHelper, 'S');
        familiarGreedy.withParent(BookEntryParentModel.builder().withEntryId(overview.id).build());
        var familiarGuardian = this.makeFamiliarGuardianEntry(helper, entryHelper, 'T');
        familiarGuardian.withParent(BookEntryParentModel.builder().withEntryId(overview.id).build());
        var familiarHeadlessRatman = this.makeFamiliarHeadlessRatmanEntry(helper, entryHelper, 'U');
        familiarHeadlessRatman.withParent(BookEntryParentModel.builder().withEntryId(overview.id).build());
        var familiarMummy = this.makeFamiliarMummyEntry(helper, entryHelper, 'V');
        familiarMummy.withParent(BookEntryParentModel.builder().withEntryId(overview.id).build());
        var familiarOtherworldBird = this.makeFamiliarOtherworldBirdEntry(helper, entryHelper, 'W');
        familiarOtherworldBird.withParent(BookEntryParentModel.builder().withEntryId(overview.id).build());
        var familiarParrot = this.makeFamiliarParrotEntry(helper, entryHelper, 'X');
        familiarParrot.withParent(BookEntryParentModel.builder().withEntryId(overview.id).build());
        var familiarShubNiggurath = this.makeFamiliarShubNiggurathEntry(helper, entryHelper, 'Y');
        familiarShubNiggurath.withParent(BookEntryParentModel.builder().withEntryId(overview.id).build());

        //add true condition to all entries to enable them by default
        overview.withCondition(BookTrueConditionModel.builder().build());
        familiarBat.withCondition(BookTrueConditionModel.builder().build());
        familiarBeaver.withCondition(BookTrueConditionModel.builder().build());
        familiarBeholder.withCondition(BookTrueConditionModel.builder().build());
        familiarBlacksmith.withCondition(BookTrueConditionModel.builder().build());
        familiarChimera.withCondition(BookTrueConditionModel.builder().build());
        familiarCthulhu.withCondition(BookTrueConditionModel.builder().build());
        familiarDeer.withCondition(BookTrueConditionModel.builder().build());
        familiarDevil.withCondition(BookTrueConditionModel.builder().build());
        familiarDragon.withCondition(BookTrueConditionModel.builder().build());
        familiarFairy.withCondition(BookTrueConditionModel.builder().build());
        familiarGreedy.withCondition(BookTrueConditionModel.builder().build());
        familiarGuardian.withCondition(BookTrueConditionModel.builder().build());
        familiarHeadlessRatman.withCondition(BookTrueConditionModel.builder().build());
        familiarMummy.withCondition(BookTrueConditionModel.builder().build());
        familiarOtherworldBird.withCondition(BookTrueConditionModel.builder().build());
        familiarParrot.withCondition(BookTrueConditionModel.builder().build());
        familiarShubNiggurath.withCondition(BookTrueConditionModel.builder().build());

        return BookCategoryModel.create(this.modLoc(helper.category), helper.categoryName())
                .withIcon("occultism:textures/gui/book/parrot.png")
                .withShowCategoryButton(false)
                .withEntries(
                        overview.build(),
                        returnToRituals.build(),
                        familiarBat.build(),
                        familiarBeaver.build(),
                        familiarBeholder.build(),
                        familiarBlacksmith.build(),
                        familiarChimera.build(),
                        familiarCthulhu.build(),
                        familiarDeer.build(),
                        familiarDevil.build(),
                        familiarDragon.build(),
                        familiarFairy.build(),
                        familiarGreedy.build(),
                        familiarGuardian.build(),
                        familiarHeadlessRatman.build(),
                        familiarMummy.build(),
                        familiarOtherworldBird.build(),
                        familiarParrot.build(),
                        familiarShubNiggurath.build()
                );
    }

    private BookEntryModel.Builder makeFamiliarsRitualsOverviewEntry(BookLangHelper helper, EntryLocationHelper entryHelper, char icon) {
        helper.entry("overview");

        helper.page("intro");
        var intro = BookTextPageModel.builder()
                .withTitle(helper.pageTitle())
                .withText(helper.pageText())
                .build();

        helper.page("ring");
        var ring = BookTextPageModel.builder()
                .withTitle(helper.pageTitle())
                .withText(helper.pageText())
                .build();

        helper.page("trading");
        var trading = BookTextPageModel.builder()
                .withTitle(helper.pageTitle())
                .withText(helper.pageText())
                .build();

        return BookEntryModel.builder()
                .withId(this.modLoc(helper.category + "/" + helper.entry))
                .withName(helper.entryName())
                .withIcon("occultism:textures/gui/book/parrot.png")
                .withLocation(entryHelper.get(icon))
                .withEntryBackground(0, 1)
                .withPages(
                        intro,
                        ring,
                        trading
                );
    }

    private BookEntryModel.Builder makeFamiliarBatEntry(BookLangHelper helper, EntryLocationHelper entryHelper, char icon) {
        helper.entry("familiar_bat");

        helper.page("entity");
        var entity = BookEntityPageModel.builder()
                .withEntityId("occultism:bat_familiar")
                .withText(helper.pageText())
                .withScale(0.7f)
                .withOffset(0.3f)
                .build();

        helper.page("ritual");
        var ritual = BookRitualRecipePageModel.builder()
                .withRecipeId1(this.modLoc("ritual/familiar_bat"))
                .build();

        helper.page("description");
        var description = BookTextPageModel.builder()
                .withTitle(helper.pageTitle())
                .withText(helper.pageText())
                .build();

        return BookEntryModel.builder()
                .withId(this.modLoc(helper.category + "/" + helper.entry))
                .withName(helper.entryName())
                .withIcon("occultism:textures/gui/book/bat_familiar.png")
                .withLocation(entryHelper.get(icon))
                .withPages(
                        entity,
                        ritual,
                        description
                );
    }

    private BookEntryModel.Builder makeFamiliarBeaverEntry(BookLangHelper helper, EntryLocationHelper entryHelper, char icon) {
        helper.entry("familiar_beaver");

        helper.page("entity");
        var entity = BookEntityPageModel.builder()
                .withEntityId("occultism:beaver_familiar")
                .withText(helper.pageText())
                .build();

        helper.page("ritual");
        var ritual = BookRitualRecipePageModel.builder()
                .withRecipeId1(this.modLoc("ritual/familiar_beaver"))
                .build();

        helper.page("description");
        var description = BookTextPageModel.builder()
                .withTitle(helper.pageTitle())
                .withText(helper.pageText())
                .build();

        return BookEntryModel.builder()
                .withId(this.modLoc(helper.category + "/" + helper.entry))
                .withName(helper.entryName())
                .withIcon("occultism:textures/gui/book/familiar_beaver.png")
                .withLocation(entryHelper.get(icon))
                .withPages(
                        entity,
                        ritual,
                        description
                );
    }

    private BookEntryModel.Builder makeFamiliarBeholderEntry(BookLangHelper helper, EntryLocationHelper entryHelper, char icon) {
        helper.entry("familiar_beholder");

        helper.page("entity");
        var entity = BookEntityPageModel.builder()
                .withEntityId("occultism:beholder_familiar")
                .withText(helper.pageText())
                .withScale(0.7f)
                .withOffset(0.3f)
                .build();

        helper.page("ritual");
        var ritual = BookRitualRecipePageModel.builder()
                .withRecipeId1(this.modLoc("ritual/familiar_beholder"))
                .build();

        helper.page("description");
        var description = BookTextPageModel.builder()
                .withTitle(helper.pageTitle())
                .withText(helper.pageText())
                .build();

        return BookEntryModel.builder()
                .withId(this.modLoc(helper.category + "/" + helper.entry))
                .withName(helper.entryName())
                .withIcon("occultism:textures/gui/book/familiar_beholder.png")
                .withLocation(entryHelper.get(icon))
                .withPages(
                        entity,
                        ritual,
                        description
                );
    }

    private BookEntryModel.Builder makeFamiliarBlacksmithEntry(BookLangHelper helper, EntryLocationHelper entryHelper, char icon) {
        helper.entry("familiar_blacksmith");

        helper.page("entity");
        var entity = BookEntityPageModel.builder()
                .withEntityId("occultism:blacksmith_familiar")
                .withText(helper.pageText())
                .build();

        helper.page("ritual");
        var ritual = BookRitualRecipePageModel.builder()
                .withRecipeId1(this.modLoc("ritual/familiar_blacksmith"))
                .build();

        helper.page("description");
        var description = BookTextPageModel.builder()
                .withTitle(helper.pageTitle())
                .withText(helper.pageText())
                .build();

        helper.page("description2");
        var description2 = BookTextPageModel.builder()
                .withTitle(helper.pageTitle())
                .withText(helper.pageText())
                .build();

        return BookEntryModel.builder()
                .withId(this.modLoc(helper.category + "/" + helper.entry))
                .withName(helper.entryName())
                .withIcon("occultism:textures/gui/book/familiar_blacksmith.png")
                .withLocation(entryHelper.get(icon))
                .withPages(
                        entity,
                        ritual,
                        description,
                        description2
                );
    }

    private BookEntryModel.Builder makeFamiliarChimeraEntry(BookLangHelper helper, EntryLocationHelper entryHelper, char icon) {
        helper.entry("familiar_chimera");

        helper.page("entity");
        var entity = BookEntityPageModel.builder()
                .withEntityId("occultism:chimera_familiar")
                .withText(helper.pageText())
                .withScale(0.7f)
                .withOffset(0.3f)
                .build();

        helper.page("ritual");
        var ritual = BookRitualRecipePageModel.builder()
                .withRecipeId1(this.modLoc("ritual/familiar_chimera"))
                .build();

        helper.page("description");
        var description = BookTextPageModel.builder()
                .withTitle(helper.pageTitle())
                .withText(helper.pageText())
                .build();

        helper.page("description2");
        var description2 = BookTextPageModel.builder()
                .withText(helper.pageText())
                .build();

        return BookEntryModel.builder()
                .withId(this.modLoc(helper.category + "/" + helper.entry))
                .withName(helper.entryName())
                .withIcon("occultism:textures/gui/book/familiar_chimera.png")
                .withLocation(entryHelper.get(icon))
                .withPages(
                        entity,
                        ritual,
                        description,
                        description2
                );
    }

    private BookEntryModel.Builder makeFamiliarCthulhuEntry(BookLangHelper helper, EntryLocationHelper entryHelper, char icon) {
        helper.entry("familiar_cthulhu");

        helper.page("entity");
        var entity = BookEntityPageModel.builder()
                .withEntityId("occultism:cthulhu_familiar")
                .withText(helper.pageText())
                .withScale(0.5f)
                .withOffset(0.3f)
                .build();

        helper.page("ritual");
        var ritual = BookRitualRecipePageModel.builder()
                .withRecipeId1(this.modLoc("ritual/familiar_cthulhu"))
                .build();

        helper.page("description");
        var description = BookTextPageModel.builder()
                .withTitle(helper.pageTitle())
                .withText(helper.pageText())
                .build();

        return BookEntryModel.builder()
                .withId(this.modLoc(helper.category + "/" + helper.entry))
                .withName(helper.entryName())
                .withIcon("occultism:textures/gui/book/familiar_cthulhu.png")
                .withLocation(entryHelper.get(icon))
                .withPages(
                        entity,
                        ritual,
                        description
                );
    }

    private BookEntryModel.Builder makeFamiliarDeerEntry(BookLangHelper helper, EntryLocationHelper entryHelper, char icon) {
        helper.entry("familiar_deer");

        helper.page("entity");
        var entity = BookEntityPageModel.builder()
                .withEntityId("occultism:deer_familiar")
                .withText(helper.pageText())
                .withScale(0.7f)
                .withOffset(0.3f)
                .build();

        helper.page("ritual");
        var ritual = BookRitualRecipePageModel.builder()
                .withRecipeId1(this.modLoc("ritual/familiar_deer"))
                .build();

        helper.page("description");
        var description = BookTextPageModel.builder()
                .withTitle(helper.pageTitle())
                .withText(helper.pageText())
                .build();

        return BookEntryModel.builder()
                .withId(this.modLoc(helper.category + "/" + helper.entry))
                .withName(helper.entryName())
                .withIcon("occultism:textures/gui/book/familiar_deer.png")
                .withLocation(entryHelper.get(icon))
                .withPages(
                        entity,
                        ritual,
                        description
                );
    }

    private BookEntryModel.Builder makeFamiliarDevilEntry(BookLangHelper helper, EntryLocationHelper entryHelper, char icon) {
        helper.entry("familiar_devil");

        helper.page("entity");
        var entity = BookEntityPageModel.builder()
                .withEntityId("occultism:devil_familiar")
                .withText(helper.pageText())
                .withScale(0.5f)
                .withOffset(0.3f)
                .build();

        helper.page("ritual");
        var ritual = BookRitualRecipePageModel.builder()
                .withRecipeId1(this.modLoc("ritual/familiar_devil"))
                .build();

        helper.page("description");
        var description = BookTextPageModel.builder()
                .withTitle(helper.pageTitle())
                .withText(helper.pageText())
                .build();

        return BookEntryModel.builder()
                .withId(this.modLoc(helper.category + "/" + helper.entry))
                .withName(helper.entryName())
                .withIcon("occultism:textures/gui/book/familiar_devil.png")
                .withLocation(entryHelper.get(icon))
                .withPages(
                        entity,
                        ritual,
                        description
                );
    }

    private BookEntryModel.Builder makeFamiliarDragonEntry(BookLangHelper helper, EntryLocationHelper entryHelper, char icon) {
        helper.entry("familiar_dragon");

        helper.page("entity");
        var entity = BookEntityPageModel.builder()
                .withEntityId("occultism:dragon_familiar")
                .withText(helper.pageText())
                .withScale(0.7f)
                .withOffset(0.3f)
                .build();

        helper.page("ritual");
        var ritual = BookRitualRecipePageModel.builder()
                .withRecipeId1(this.modLoc("ritual/familiar_dragon"))
                .build();

        helper.page("description");
        var description = BookTextPageModel.builder()
                .withTitle(helper.pageTitle())
                .withText(helper.pageText())
                .build();

        return BookEntryModel.builder()
                .withId(this.modLoc(helper.category + "/" + helper.entry))
                .withName(helper.entryName())
                .withIcon("occultism:textures/gui/book/familiar_dragon.png")
                .withLocation(entryHelper.get(icon))
                .withPages(
                        entity,
                        ritual,
                        description
                );
    }

    private BookEntryModel.Builder makeFamiliarFairyEntry(BookLangHelper helper, EntryLocationHelper entryHelper, char icon) {
        helper.entry("familiar_fairy");

        helper.page("entity");
        var entity = BookEntityPageModel.builder()
                .withEntityId("occultism:fairy_familiar")
                .withText(helper.pageText())
                .withScale(0.8f)
                .withOffset(0.3f)
                .build();

        helper.page("ritual");
        var ritual = BookRitualRecipePageModel.builder()
                .withRecipeId1(this.modLoc("ritual/familiar_fairy"))
                .build();

        helper.page("description");
        var description = BookTextPageModel.builder()
                .withTitle(helper.pageTitle())
                .withText(helper.pageText())
                .build();

        return BookEntryModel.builder()
                .withId(this.modLoc(helper.category + "/" + helper.entry))
                .withName(helper.entryName())
                .withIcon("occultism:textures/gui/book/familiar_fairy.png")
                .withLocation(entryHelper.get(icon))
                .withPages(
                        entity,
                        ritual,
                        description
                );
    }

    private BookEntryModel.Builder makeFamiliarGreedyEntry(BookLangHelper helper, EntryLocationHelper entryHelper, char icon) {
        helper.entry("familiar_greedy");

        helper.page("entity");
        var entity = BookEntityPageModel.builder()
                .withEntityId("occultism:greedy_familiar")
                .withText(helper.pageText())
                .build();

        helper.page("ritual");
        var ritual = BookRitualRecipePageModel.builder()
                .withRecipeId1(this.modLoc("ritual/familiar_greedy"))
                .build();

        helper.page("description");
        var description = BookTextPageModel.builder()
                .withTitle(helper.pageTitle())
                .withText(helper.pageText())
                .build();

        return BookEntryModel.builder()
                .withId(this.modLoc(helper.category + "/" + helper.entry))
                .withName(helper.entryName())
                .withIcon("occultism:textures/gui/book/familiar_greedy.png")
                .withLocation(entryHelper.get(icon))
                .withPages(
                        entity,
                        ritual,
                        description
                );
    }

    private BookEntryModel.Builder makeFamiliarGuardianEntry(BookLangHelper helper, EntryLocationHelper entryHelper, char icon) {
        helper.entry("familiar_guardian");

        helper.page("entity");
        var entity = BookEntityPageModel.builder()
                .withEntityId("occultism:guardian_familiar{for_book:true}")
                .withText(helper.pageText())
                .build();

        helper.page("ritual");
        var ritual = BookRitualRecipePageModel.builder()
                .withRecipeId1(this.modLoc("ritual/familiar_guardian"))
                .build();

        helper.page("description");
        var description = BookTextPageModel.builder()
                .withTitle(helper.pageTitle())
                .withText(helper.pageText())
                .build();

        helper.page("description2");
        var description2 = BookTextPageModel.builder()
                .withText(helper.pageText())
                .build();

        return BookEntryModel.builder()
                .withId(this.modLoc(helper.category + "/" + helper.entry))
                .withName(helper.entryName())
                .withIcon("occultism:textures/gui/book/familiar_guardian.png")
                .withLocation(entryHelper.get(icon))
                .withPages(
                        entity,
                        ritual,
                        description,
                        description2
                );
    }

    private BookEntryModel.Builder makeFamiliarHeadlessRatmanEntry(BookLangHelper helper, EntryLocationHelper entryHelper, char icon) {
        helper.entry("familiar_headless");

        helper.page("entity");
        var entity = BookEntityPageModel.builder()
                .withEntityId("occultism:headless_familiar")
                .withScale(0.7f)
                .withText(helper.pageText())
                .build();

        helper.page("ritual");
        var ritual = BookRitualRecipePageModel.builder()
                .withRecipeId1(this.modLoc("ritual/familiar_headless"))
                .build();

        helper.page("description");
        var description = BookTextPageModel.builder()
                .withTitle(helper.pageTitle())
                .withText(helper.pageText())
                .build();

        return BookEntryModel.builder()
                .withId(this.modLoc(helper.category + "/" + helper.entry))
                .withName(helper.entryName())
                .withIcon("occultism:textures/gui/book/familiar_headless_ratman.png")
                .withLocation(entryHelper.get(icon))
                .withPages(
                        entity,
                        ritual,
                        description
                );
    }

    private BookEntryModel.Builder makeFamiliarMummyEntry(BookLangHelper helper, EntryLocationHelper entryHelper, char icon) {
        helper.entry("familiar_mummy");

        helper.page("entity");
        var entity = BookEntityPageModel.builder()
                .withEntityId("occultism:mummy_familiar")
                .withText(helper.pageText())
                .build();

        helper.page("ritual");
        var ritual = BookRitualRecipePageModel.builder()
                .withRecipeId1(this.modLoc("ritual/familiar_mummy"))
                .build();

        helper.page("description");
        var description = BookTextPageModel.builder()
                .withTitle(helper.pageTitle())
                .withText(helper.pageText())
                .build();

        return BookEntryModel.builder()
                .withId(this.modLoc(helper.category + "/" + helper.entry))
                .withName(helper.entryName())
                .withIcon("occultism:textures/gui/book/familiar_mummy.png")
                .withLocation(entryHelper.get(icon))
                .withPages(
                        entity,
                        ritual,
                        description
                );
    }

    private BookEntryModel.Builder makeFamiliarOtherworldBirdEntry(BookLangHelper helper, EntryLocationHelper entryHelper, char icon) {
        helper.entry("familiar_otherworld_bird");

        helper.page("entity");
        var entity = BookEntityPageModel.builder()
                .withEntityId("occultism:otherworld_bird")
                .withText(helper.pageText())
                .build();

        helper.page("ritual");
        var ritual = BookRitualRecipePageModel.builder()
                .withRecipeId1(this.modLoc("ritual/familiar_otherworld_bird"))
                .build();

        helper.page("description");
        var description = BookTextPageModel.builder()
                .withTitle(helper.pageTitle())
                .withText(helper.pageText())
                .build();

        helper.page("description2");
        var description2 = BookTextPageModel.builder()
                .withText(helper.pageText())
                .build();

        return BookEntryModel.builder()
                .withId(this.modLoc(helper.category + "/" + helper.entry))
                .withName(helper.entryName())
                .withIcon("occultism:textures/gui/book/otherworld_bird.png")
                .withLocation(entryHelper.get(icon))
                .withPages(
                        entity,
                        ritual,
                        description,
                        description2
                );
    }

    private BookEntryModel.Builder makeFamiliarParrotEntry(BookLangHelper helper, EntryLocationHelper entryHelper, char icon) {
        helper.entry("familiar_parrot");

        helper.page("entity");
        var entity = BookEntityPageModel.builder()
                .withEntityId("minecraft:parrot")
                .withText(helper.pageText())
                .build();

        helper.page("ritual");
        var ritual = BookRitualRecipePageModel.builder()
                .withRecipeId1(this.modLoc("ritual/familiar_parrot"))
                .build();

        helper.page("description");
        var description = BookTextPageModel.builder()
                .withTitle(helper.pageTitle())
                .withText(helper.pageText())
                .build();

        helper.page("description2");
        var description2 = BookTextPageModel.builder()
                .withText(helper.pageText())
                .build();

        return BookEntryModel.builder()
                .withId(this.modLoc(helper.category + "/" + helper.entry))
                .withName(helper.entryName())
                .withIcon("occultism:textures/gui/book/parrot.png")
                .withLocation(entryHelper.get(icon))
                .withPages(
                        entity,
                        ritual,
                        description,
                        description2
                );
    }

    private BookEntryModel.Builder makeFamiliarShubNiggurathEntry(BookLangHelper helper, EntryLocationHelper entryHelper, char icon) {
        helper.entry("familiar_shub_niggurath");

        helper.page("entity");
        var entity = BookEntityPageModel.builder()
                .withEntityId("occultism:shub_niggurath_familiar")
                .withText(helper.pageText())
                .build();

        helper.page("ritual");
        var ritual = BookTextPageModel.builder()
                .withTitle(helper.pageTitle())
                .withText(helper.pageText())
                .build();

        helper.page("description");
        var description = BookTextPageModel.builder()
                .withTitle(helper.pageTitle())
                .withText(helper.pageText())
                .build();

        return BookEntryModel.builder()
                .withId(this.modLoc(helper.category + "/" + helper.entry))
                .withName(helper.entryName())
                .withIcon("occultism:textures/gui/book/familiar_shub_niggurath.png")
                .withLocation(entryHelper.get(icon))
                .withPages(
                        entity,
                        ritual,
                        description
                );
    }
    //endregion

    private BookCategoryModel makeStorageCategory(BookLangHelper helper) {
        helper.category("storage");

        var entryHelper = ModonomiconAPI.get().getEntryLocationHelper();
        entryHelper.setMap(
                "___________________________",
                "_________w_r_______________",
                "___________________________",
                "_____0_c___s_1_2_3_4________",
                "___________________________",
                "_________d_________________",
                "___________________________"
        );

        var overview = this.makeStorageOverviewEntry(helper, entryHelper, '0');

        helper.category("storage");
        var storageController = this.makeStorageControllerEntry(helper, entryHelper, 'c');
        storageController.withParent(BookEntryParentModel.builder().withEntryId(overview.id).build());

        var storageStabilizer = this.makeStorageStabilizerEntry(helper, entryHelper, 's');
        storageStabilizer.withParent(BookEntryParentModel.builder().withEntryId(storageController.id).build());

        helper.category("crafting_rituals"); //re-use existing entries
        var craftStabilizerTier1 = this.makeCraftStabilizerTier1Entry(helper, entryHelper, '1');
        craftStabilizerTier1.withParent(BookEntryParentModel.builder().withEntryId(storageStabilizer.id).build());
        var craftStabilizerTier2 = this.makeCraftStabilizerTier2Entry(helper, entryHelper, '2');
        craftStabilizerTier2.withParent(BookEntryParentModel.builder().withEntryId(
                new ResourceLocation(
                        craftStabilizerTier1.id.getNamespace(),
                        "storage/" + craftStabilizerTier1.id.getPath()
                )
        ).build());
        var craftStabilizerTier3 = this.makeCraftStabilizerTier3Entry(helper, entryHelper, '3');
        craftStabilizerTier3.withParent(BookEntryParentModel.builder().withEntryId(
                new ResourceLocation(
                        craftStabilizerTier2.id.getNamespace(),
                        "storage/" + craftStabilizerTier2.id.getPath()
                )
        ).build());
        var craftStabilizerTier4 = this.makeCraftStabilizerTier4Entry(helper, entryHelper, '4');
        craftStabilizerTier4.withParent(BookEntryParentModel.builder().withEntryId(
                new ResourceLocation(
                        craftStabilizerTier3.id.getNamespace(),
                        "storage/" + craftStabilizerTier3.id.getPath()
                )
        ).build());

        var craftStableWormhole = this.makeCraftStableWormholeEntry(helper, entryHelper, 'w');
        craftStableWormhole.withParent(BookEntryParentModel.builder().withEntryId(storageController.id).build());
        var craftStorageRemote = this.makeCraftStorageRemoteEntry(helper, entryHelper, 'r');
        craftStorageRemote.withParent(BookEntryParentModel.builder().withEntryId(
                new ResourceLocation(
                        craftStableWormhole.id.getNamespace(),
                        "storage/" + craftStableWormhole.id.getPath()
                )
        ).build());

        helper.category("summoning_rituals"); //re-use existing entries
        var summonManageMachine = this.makeSummonManageMachineEntry(helper, entryHelper, 'd');
        summonManageMachine.withParent(BookEntryParentModel.builder().withEntryId(storageController.id).build());

        helper.category("storage");

        return BookCategoryModel.create(this.modLoc(helper.category), helper.categoryName())
                .withIcon("minecraft:chest")
                .withEntries(
                        overview.build(),
                        storageController.build(),
                        storageStabilizer.build(),
                        craftStabilizerTier1.build(),
                        craftStabilizerTier2.build(),
                        craftStabilizerTier3.build(),
                        craftStabilizerTier4.build(),
                        craftStableWormhole.build(),
                        craftStorageRemote.build(),
                        summonManageMachine.build()
                );
    }

    private BookEntryModel.Builder makeStorageOverviewEntry(BookLangHelper helper, EntryLocationHelper entryHelper, char icon) {
        helper.entry("overview");

        helper.page("intro");
        var intro = BookTextPageModel.builder()
                .withTitle(helper.pageTitle())
                .withText(helper.pageText())
                .build();

        helper.page("intro2");
        var intro2 = BookTextPageModel.builder()
                .withText(helper.pageText())
                .build();


        return BookEntryModel.builder()
                .withId(this.modLoc(helper.category + "/" + helper.entry))
                .withName(helper.entryName())
                .withIcon("minecraft:chest")
                .withLocation(entryHelper.get(icon))
                .withEntryBackground(0, 1)
                .withPages(
                        intro,
                        intro2
                );
    }

    private BookEntryModel.Builder makeStorageControllerEntry(BookLangHelper helper, EntryLocationHelper entryHelper, char icon) {
        helper.entry("storage_controller");

        helper.page("intro");
        var intro = BookSpotlightPageModel.builder()
                .withItem(Ingredient.of(OccultismBlocks.STORAGE_CONTROLLER.get()))
                .withText(helper.pageText())
                .build();

        helper.page("usage");
        var usage = BookTextPageModel.builder()
                .withTitle(helper.pageTitle())
                .withText(helper.pageText())
                .build();

        helper.page("safety");
        var safety = BookTextPageModel.builder()
                .withTitle(helper.pageTitle())
                .withText(helper.pageText())
                .build();

        helper.page("size");
        var size = BookTextPageModel.builder()
                .withTitle(helper.pageTitle())
                .withText(helper.pageText())
                .build();

        helper.page("unique_items");
        var uniqueItems = BookTextPageModel.builder()
                .withTitle(helper.pageTitle())
                .withText(helper.pageText())
                .build();

        helper.page("config");
        var config = BookTextPageModel.builder()
                .withTitle(helper.pageTitle())
                .withText(helper.pageText())
                .build();

        helper.page("mods");
        var mods = BookTextPageModel.builder()
                .withTitle(helper.pageTitle())
                .withText(helper.pageText())
                .build();

        helper.page("matrix_ritual");
        var matrixRitual = BookRitualRecipePageModel.builder()
                .withRecipeId1(this.modLoc("ritual/craft_dimensional_matrix"))
                .build();

        helper.page("base_ritual");
        var baseRitual = BookRitualRecipePageModel.builder()
                .withRecipeId1(this.modLoc("ritual/craft_storage_controller_base"))
                .build();

        helper.page("recipe");
        var recipe = BookCraftingRecipePageModel.builder()
                .withRecipeId1(this.modLoc("crafting/storage_controller"))
                .withText(helper.pageText())
                .build();

        return BookEntryModel.builder()
                .withId(this.modLoc(helper.category + "/" + helper.entry))
                .withName(helper.entryName())
                .withIcon(OccultismBlocks.STORAGE_CONTROLLER.getId().toString())
                .withLocation(entryHelper.get(icon))
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

    private BookEntryModel.Builder makeStorageStabilizerEntry(BookLangHelper helper, EntryLocationHelper entryHelper, char icon) {
        helper.entry("storage_stabilizer");

        helper.page("spotlight");
        var spotlight = BookSpotlightPageModel.builder()
                .withItem(Ingredient.of(OccultismBlocks.STORAGE_STABILIZER_TIER1.get()))
                .withText(helper.pageText())
                .build();

        helper.page("upgrade");
        var upgrade = BookTextPageModel.builder()
                .withTitle(helper.pageTitle())
                .withText(helper.pageText())
                .build();

        helper.page("build_instructions");
        var buildInstructions = BookTextPageModel.builder()
                .withTitle(helper.pageTitle())
                .withText(helper.pageText())
                .build();

        helper.page("demo");
        var demo = BookMultiblockPageModel.builder()
                .withMultiblockId(this.modLoc("storage_stabilizer_demo"))
                .withMultiblockName(helper.pageTitle())
                .build();

        return BookEntryModel.builder()
                .withId(this.modLoc(helper.category + "/" + helper.entry))
                .withName(helper.entryName())
                .withIcon(OccultismBlocks.STORAGE_CONTROLLER.getId().toString())
                .withLocation(entryHelper.get(icon))
                .withPages(
                        spotlight,
                        upgrade,
                        buildInstructions,
                        demo
                );
    }
}
