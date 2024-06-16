package com.klikli_dev.occultism.datagen.book;

import com.klikli_dev.modonomicon.api.datagen.CategoryEntryMap;
import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookEntryModel;
import com.klikli_dev.modonomicon.api.datagen.book.BookEntryParentModel;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.condition.BookTrueConditionModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookEntityPageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookTextPageModel;
import com.klikli_dev.occultism.datagen.OccultismBookProvider;
import com.klikli_dev.occultism.datagen.book.familiarrituals.DemonicPartnerEntry;
import com.klikli_dev.occultism.datagen.book.familiarrituals.ResurrectionEntry;
import com.klikli_dev.occultism.integration.modonomicon.pages.BookRitualRecipePageModel;

public class FamiliarRitualsCategory extends CategoryProvider {

    public static final String CATEGORY_ID = "familiar_rituals";

    public FamiliarRitualsCategory(OccultismBookProvider parent) {
        super(parent);
    }

    @Override
    public String[] generateEntryMap() {
        return new String[]{
                "________R_T_V_X____________",
                "___________________________",
                "_____a_Q_S_U_W_Z___________",
                "___________________________",
                "___r_o_____________________",
                "___________________________",
                "_______I_K_M_O_Y___________",
                "___________________________",
                "________J_L_N_P____________"
        };
    }

    @Override
    protected void generateEntries() {
        var overview = this.add(this.makeFamiliarsRitualsOverviewEntry(this.entryMap, 'o'));
        var returnToRituals = this.add(this.makeReturnToRitualsEntry(this.entryMap, 'r'));
        returnToRituals.withParent(BookEntryParentModel.create(overview.getId()));
        returnToRituals.withCondition(BookTrueConditionModel.create());

        var resurrection = new ResurrectionEntry(this).generate('a');
        resurrection.withParent(BookEntryParentModel.create(overview.getId()));

        var familiarBat = this.add(this.makeFamiliarBatEntry(this.entryMap, 'I'));
        familiarBat.withParent(BookEntryParentModel.create(overview.getId()));
        var familiarBeaver = this.add(this.makeFamiliarBeaverEntry(this.entryMap, 'J'));
        familiarBeaver.withParent(BookEntryParentModel.create(overview.getId()));
        var familiarBeholder = this.add(this.makeFamiliarBeholderEntry(this.entryMap, 'K'));
        familiarBeholder.withParent(BookEntryParentModel.create(overview.getId()));
        var familiarBlacksmith = this.add(this.makeFamiliarBlacksmithEntry(this.entryMap, 'L'));
        familiarBlacksmith.withParent(BookEntryParentModel.create(overview.getId()));
        var familiarChimera = this.add(this.makeFamiliarChimeraEntry(this.entryMap, 'M'));
        familiarChimera.withParent(BookEntryParentModel.create(overview.getId()));
        var familiarCthulhu = this.add(this.makeFamiliarCthulhuEntry(this.entryMap, 'N'));
        familiarCthulhu.withParent(BookEntryParentModel.create(overview.getId()));
        var familiarDeer = this.add(this.makeFamiliarDeerEntry(this.entryMap, 'O'));
        familiarDeer.withParent(BookEntryParentModel.create(overview.getId()));
        var familiarDevil = this.add(this.makeFamiliarDevilEntry(this.entryMap, 'P'));
        familiarDevil.withParent(BookEntryParentModel.create(overview.getId()));
        var familiarDragon = this.add(this.makeFamiliarDragonEntry(this.entryMap, 'Q'));
        familiarDragon.withParent(BookEntryParentModel.create(overview.getId()));
        var familiarFairy = this.add(this.makeFamiliarFairyEntry(this.entryMap, 'R'));
        familiarFairy.withParent(BookEntryParentModel.create(overview.getId()));
        var familiarGreedy = this.add(this.makeFamiliarGreedyEntry(this.entryMap, 'S'));
        familiarGreedy.withParent(BookEntryParentModel.create(overview.getId()));
        var familiarGuardian = this.add(this.makeFamiliarGuardianEntry(this.entryMap, 'T'));
        familiarGuardian.withParent(BookEntryParentModel.create(overview.getId()));
        var familiarHeadlessRatman = this.add(this.makeFamiliarHeadlessRatmanEntry(this.entryMap, 'U'));
        familiarHeadlessRatman.withParent(BookEntryParentModel.create(overview.getId()));
        var familiarMummy = this.add(this.makeFamiliarMummyEntry(this.entryMap, 'V'));
        familiarMummy.withParent(BookEntryParentModel.create(overview.getId()));
        var familiarOtherworldBird = this.add(this.makeFamiliarOtherworldBirdEntry(this.entryMap, 'W'));
        familiarOtherworldBird.withParent(BookEntryParentModel.create(overview.getId()));
        var familiarParrot = this.add(this.makeFamiliarParrotEntry(this.entryMap, 'X'));
        familiarParrot.withParent(BookEntryParentModel.create(overview.getId()));
        var familiarShubNiggurath = this.add(this.makeFamiliarShubNiggurathEntry(this.entryMap, 'Y'));
        familiarShubNiggurath.withParent(BookEntryParentModel.create(overview.getId()));

        //add true condition to all entries to enable them by default
        overview.withCondition(BookTrueConditionModel.create());
        familiarBat.withCondition(BookTrueConditionModel.create());
        familiarBeaver.withCondition(BookTrueConditionModel.create());
        familiarBeholder.withCondition(BookTrueConditionModel.create());
        familiarBlacksmith.withCondition(BookTrueConditionModel.create());
        familiarChimera.withCondition(BookTrueConditionModel.create());
        familiarCthulhu.withCondition(BookTrueConditionModel.create());
        familiarDeer.withCondition(BookTrueConditionModel.create());
        familiarDevil.withCondition(BookTrueConditionModel.create());
        familiarDragon.withCondition(BookTrueConditionModel.create());
        familiarFairy.withCondition(BookTrueConditionModel.create());
        familiarGreedy.withCondition(BookTrueConditionModel.create());
        familiarGuardian.withCondition(BookTrueConditionModel.create());
        familiarHeadlessRatman.withCondition(BookTrueConditionModel.create());
        familiarMummy.withCondition(BookTrueConditionModel.create());
        familiarOtherworldBird.withCondition(BookTrueConditionModel.create());
        familiarParrot.withCondition(BookTrueConditionModel.create());
        familiarShubNiggurath.withCondition(BookTrueConditionModel.create());

        var demonicPartner = new DemonicPartnerEntry(this).generate('Z');
        demonicPartner.withParent(BookEntryParentModel.create(overview.getId()));
        demonicPartner.withCondition(BookTrueConditionModel.create());
    }

    @Override
    protected String categoryName() {
        return "Familiar Rituals";
    }

    @Override
    protected BookIconModel categoryIcon() {
        return BookIconModel.create(this.modLoc("textures/gui/book/parrot.png"));
    }

    @Override
    public String categoryId() {
        return CATEGORY_ID;
    }

    private BookEntryModel makeFamiliarsRitualsOverviewEntry(CategoryEntryMap entryMap, char icon) {
        this.context().entry("overview");

        this.context().page("intro");
        var intro = BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText());

        this.context().page("ring");
        var ring = BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText());

        this.context().page("trading");
        var trading = BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText());

        return BookEntryModel.create(this.modLoc(this.context().categoryId() + "/" + this.context().entryId()), this.context().entryName())
                .withIcon(this.modLoc("textures/gui/book/parrot.png"))
                .withLocation(entryMap.get(icon))
                .withEntryBackground(0, 1)
                .withPages(
                        intro,
                        ring,
                        trading
                );
    }

    private BookEntryModel makeFamiliarBatEntry(CategoryEntryMap entryMap, char icon) {
        this.context().entry("familiar_bat");

        this.context().page("entity");
        var entity = BookEntityPageModel.create()
                .withEntityId("occultism:bat_familiar")
                .withText(this.context().pageText())
                .withScale(0.7f)
                .withOffset(0.3f);

        this.context().page("ritual");
        var ritual = BookRitualRecipePageModel.create()
                .withRecipeId1(this.modLoc("ritual/familiar_bat"));

        this.context().page("description");
        var description = BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText());

        return BookEntryModel.create(this.modLoc(this.context().categoryId() + "/" + this.context().entryId()), this.context().entryName())
                .withIcon(this.modLoc("textures/gui/book/bat_familiar.png"))
                .withLocation(entryMap.get(icon))
                .withPages(
                        entity,
                        ritual,
                        description
                );
    }

    private BookEntryModel makeFamiliarBeaverEntry(CategoryEntryMap entryMap, char icon) {
        this.context().entry("familiar_beaver");

        this.context().page("entity");
        var entity = BookEntityPageModel.create()
                .withEntityId("occultism:beaver_familiar")
                .withText(this.context().pageText());

        this.context().page("ritual");
        var ritual = BookRitualRecipePageModel.create()
                .withRecipeId1(this.modLoc("ritual/familiar_beaver"));

        this.context().page("description");
        var description = BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText());

        return BookEntryModel.create(this.modLoc(this.context().categoryId() + "/" + this.context().entryId()), this.context().entryName())
                .withIcon(this.modLoc("textures/gui/book/familiar_beaver.png"))
                .withLocation(entryMap.get(icon))
                .withPages(
                        entity,
                        ritual,
                        description
                );
    }

    private BookEntryModel makeFamiliarBeholderEntry(CategoryEntryMap entryMap, char icon) {
        this.context().entry("familiar_beholder");

        this.context().page("entity");
        var entity = BookEntityPageModel.create()
                .withEntityId("occultism:beholder_familiar")
                .withText(this.context().pageText())
                .withScale(0.7f)
                .withOffset(0.3f);

        this.context().page("ritual");
        var ritual = BookRitualRecipePageModel.create()
                .withRecipeId1(this.modLoc("ritual/familiar_beholder"));

        this.context().page("description");
        var description = BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText());

        return BookEntryModel.create(this.modLoc(this.context().categoryId() + "/" + this.context().entryId()), this.context().entryName())
                .withIcon(this.modLoc("textures/gui/book/familiar_beholder.png"))
                .withLocation(entryMap.get(icon))
                .withPages(
                        entity,
                        ritual,
                        description
                );
    }

    private BookEntryModel makeFamiliarBlacksmithEntry(CategoryEntryMap entryMap, char icon) {
        this.context().entry("familiar_blacksmith");

        this.context().page("entity");
        var entity = BookEntityPageModel.create()
                .withEntityId("occultism:blacksmith_familiar")
                .withText(this.context().pageText());

        this.context().page("ritual");
        var ritual = BookRitualRecipePageModel.create()
                .withRecipeId1(this.modLoc("ritual/familiar_blacksmith"));

        this.context().page("description");
        var description = BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText());

        this.context().page("description2");
        var description2 = BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText());

        return BookEntryModel.create(this.modLoc(this.context().categoryId() + "/" + this.context().entryId()), this.context().entryName())
                .withIcon(this.modLoc("textures/gui/book/familiar_blacksmith.png"))
                .withLocation(entryMap.get(icon))
                .withPages(
                        entity,
                        ritual,
                        description,
                        description2
                );
    }

    private BookEntryModel makeFamiliarChimeraEntry(CategoryEntryMap entryMap, char icon) {
        this.context().entry("familiar_chimera");

        this.context().page("entity");
        var entity = BookEntityPageModel.create()
                .withEntityId("occultism:chimera_familiar")
                .withText(this.context().pageText())
                .withScale(0.7f)
                .withOffset(0.3f);

        this.context().page("ritual");
        var ritual = BookRitualRecipePageModel.create()
                .withRecipeId1(this.modLoc("ritual/familiar_chimera"));

        this.context().page("description");
        var description = BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText());

        this.context().page("description2");
        var description2 = BookTextPageModel.create()
                .withText(this.context().pageText());

        return BookEntryModel.create(this.modLoc(this.context().categoryId() + "/" + this.context().entryId()), this.context().entryName())
                .withIcon(this.modLoc("textures/gui/book/familiar_chimera.png"))
                .withLocation(entryMap.get(icon))
                .withPages(
                        entity,
                        ritual,
                        description,
                        description2
                );
    }

    private BookEntryModel makeFamiliarCthulhuEntry(CategoryEntryMap entryMap, char icon) {
        this.context().entry("familiar_cthulhu");

        this.context().page("entity");
        var entity = BookEntityPageModel.create()
                .withEntityId("occultism:cthulhu_familiar")
                .withText(this.context().pageText())
                .withScale(0.5f)
                .withOffset(0.3f);

        this.context().page("ritual");
        var ritual = BookRitualRecipePageModel.create()
                .withRecipeId1(this.modLoc("ritual/familiar_cthulhu"));

        this.context().page("description");
        var description = BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText());

        return BookEntryModel.create(this.modLoc(this.context().categoryId() + "/" + this.context().entryId()), this.context().entryName())
                .withIcon(this.modLoc("textures/gui/book/familiar_cthulhu.png"))
                .withLocation(entryMap.get(icon))
                .withPages(
                        entity,
                        ritual,
                        description
                );
    }

    private BookEntryModel makeFamiliarDeerEntry(CategoryEntryMap entryMap, char icon) {
        this.context().entry("familiar_deer");

        this.context().page("entity");
        var entity = BookEntityPageModel.create()
                .withEntityId("occultism:deer_familiar")
                .withText(this.context().pageText())
                .withScale(0.7f)
                .withOffset(0.3f);

        this.context().page("ritual");
        var ritual = BookRitualRecipePageModel.create()
                .withRecipeId1(this.modLoc("ritual/familiar_deer"));

        this.context().page("description");
        var description = BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText());

        return BookEntryModel.create(this.modLoc(this.context().categoryId() + "/" + this.context().entryId()), this.context().entryName())
                .withIcon(this.modLoc("textures/gui/book/familiar_deer.png"))
                .withLocation(entryMap.get(icon))
                .withPages(
                        entity,
                        ritual,
                        description
                );
    }

    private BookEntryModel makeFamiliarDevilEntry(CategoryEntryMap entryMap, char icon) {
        this.context().entry("familiar_devil");

        this.context().page("entity");
        var entity = BookEntityPageModel.create()
                .withEntityId("occultism:devil_familiar")
                .withText(this.context().pageText())
                .withScale(0.5f)
                .withOffset(0.3f);

        this.context().page("ritual");
        var ritual = BookRitualRecipePageModel.create()
                .withRecipeId1(this.modLoc("ritual/familiar_devil"));

        this.context().page("description");
        var description = BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText());

        return BookEntryModel.create(this.modLoc(this.context().categoryId() + "/" + this.context().entryId()), this.context().entryName())
                .withIcon(this.modLoc("textures/gui/book/familiar_devil.png"))
                .withLocation(entryMap.get(icon))
                .withPages(
                        entity,
                        ritual,
                        description
                );
    }

    private BookEntryModel makeFamiliarDragonEntry(CategoryEntryMap entryMap, char icon) {
        this.context().entry("familiar_dragon");

        this.context().page("entity");
        var entity = BookEntityPageModel.create()
                .withEntityId("occultism:dragon_familiar")
                .withText(this.context().pageText())
                .withScale(0.7f)
                .withOffset(0.3f);

        this.context().page("ritual");
        var ritual = BookRitualRecipePageModel.create()
                .withRecipeId1(this.modLoc("ritual/familiar_dragon"));

        this.context().page("description");
        var description = BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText());

        return BookEntryModel.create(this.modLoc(this.context().categoryId() + "/" + this.context().entryId()), this.context().entryName())
                .withIcon(this.modLoc("textures/gui/book/familiar_dragon.png"))
                .withLocation(entryMap.get(icon))
                .withPages(
                        entity,
                        ritual,
                        description
                );
    }

    private BookEntryModel makeFamiliarFairyEntry(CategoryEntryMap entryMap, char icon) {
        this.context().entry("familiar_fairy");

        this.context().page("entity");
        var entity = BookEntityPageModel.create()
                .withEntityId("occultism:fairy_familiar")
                .withText(this.context().pageText())
                .withScale(0.8f)
                .withOffset(0.3f);

        this.context().page("ritual");
        var ritual = BookRitualRecipePageModel.create()
                .withRecipeId1(this.modLoc("ritual/familiar_fairy"));

        this.context().page("description");
        var description = BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText());

        return BookEntryModel.create(this.modLoc(this.context().categoryId() + "/" + this.context().entryId()), this.context().entryName())
                .withIcon(this.modLoc("textures/gui/book/familiar_fairy.png"))
                .withLocation(entryMap.get(icon))
                .withPages(
                        entity,
                        ritual,
                        description
                );
    }

    private BookEntryModel makeFamiliarGreedyEntry(CategoryEntryMap entryMap, char icon) {
        this.context().entry("familiar_greedy");

        this.context().page("entity");
        var entity = BookEntityPageModel.create()
                .withEntityId("occultism:greedy_familiar")
                .withText(this.context().pageText());

        this.context().page("ritual");
        var ritual = BookRitualRecipePageModel.create()
                .withRecipeId1(this.modLoc("ritual/familiar_greedy"));

        this.context().page("description");
        var description = BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText());

        return BookEntryModel.create(this.modLoc(this.context().categoryId() + "/" + this.context().entryId()), this.context().entryName())
                .withIcon(this.modLoc("textures/gui/book/familiar_greedy.png"))
                .withLocation(entryMap.get(icon))
                .withPages(
                        entity,
                        ritual,
                        description
                );
    }

    private BookEntryModel makeFamiliarGuardianEntry(CategoryEntryMap entryMap, char icon) {
        this.context().entry("familiar_guardian");

        this.context().page("entity");
        var entity = BookEntityPageModel.create()
                .withEntityId("occultism:guardian_familiar{for_book:true}")
                .withText(this.context().pageText());

        this.context().page("ritual");
        var ritual = BookRitualRecipePageModel.create()
                .withRecipeId1(this.modLoc("ritual/familiar_guardian"));

        this.context().page("description");
        var description = BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText());

        this.context().page("description2");
        var description2 = BookTextPageModel.create()
                .withText(this.context().pageText());

        return BookEntryModel.create(this.modLoc(this.context().categoryId() + "/" + this.context().entryId()), this.context().entryName())
                .withIcon(this.modLoc("textures/gui/book/familiar_guardian.png"))
                .withLocation(entryMap.get(icon))
                .withPages(
                        entity,
                        ritual,
                        description,
                        description2
                );
    }

    private BookEntryModel makeFamiliarHeadlessRatmanEntry(CategoryEntryMap entryMap, char icon) {
        this.context().entry("familiar_headless");

        this.context().page("entity");
        var entity = BookEntityPageModel.create()
                .withEntityId("occultism:headless_familiar")
                .withScale(0.7f)
                .withText(this.context().pageText());

        this.context().page("ritual");
        var ritual = BookRitualRecipePageModel.create()
                .withRecipeId1(this.modLoc("ritual/familiar_headless"));

        this.context().page("description");
        var description = BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText());

        return BookEntryModel.create(this.modLoc(this.context().categoryId() + "/" + this.context().entryId()), this.context().entryName())
                .withIcon(this.modLoc("textures/gui/book/familiar_headless_ratman.png"))
                .withLocation(entryMap.get(icon))
                .withPages(
                        entity,
                        ritual,
                        description
                );
    }

    private BookEntryModel makeFamiliarMummyEntry(CategoryEntryMap entryMap, char icon) {
        this.context().entry("familiar_mummy");

        this.context().page("entity");
        var entity = BookEntityPageModel.create()
                .withEntityId("occultism:mummy_familiar")
                .withText(this.context().pageText());

        this.context().page("ritual");
        var ritual = BookRitualRecipePageModel.create()
                .withRecipeId1(this.modLoc("ritual/familiar_mummy"));

        this.context().page("description");
        var description = BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText());

        return BookEntryModel.create(this.modLoc(this.context().categoryId() + "/" + this.context().entryId()), this.context().entryName())
                .withIcon(this.modLoc("textures/gui/book/familiar_mummy.png"))
                .withLocation(entryMap.get(icon))
                .withPages(
                        entity,
                        ritual,
                        description
                );
    }

    private BookEntryModel makeFamiliarOtherworldBirdEntry(CategoryEntryMap entryMap, char icon) {
        this.context().entry("familiar_otherworld_bird");

        this.context().page("entity");
        var entity = BookEntityPageModel.create()
                .withEntityId("occultism:otherworld_bird")
                .withText(this.context().pageText());

        this.context().page("ritual");
        var ritual = BookRitualRecipePageModel.create()
                .withRecipeId1(this.modLoc("ritual/familiar_otherworld_bird"));

        this.context().page("description");
        var description = BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText());

        this.context().page("description2");
        var description2 = BookTextPageModel.create()
                .withText(this.context().pageText());

        return BookEntryModel.create(this.modLoc(this.context().categoryId() + "/" + this.context().entryId()), this.context().entryName())
                .withIcon(this.modLoc("textures/gui/book/otherworld_bird.png"))
                .withLocation(entryMap.get(icon))
                .withPages(
                        entity,
                        ritual,
                        description,
                        description2
                );
    }

    private BookEntryModel makeFamiliarParrotEntry(CategoryEntryMap entryMap, char icon) {
        this.context().entry("familiar_parrot");

        this.context().page("entity");
        var entity = BookEntityPageModel.create()
                .withEntityId("minecraft:parrot")
                .withText(this.context().pageText());

        this.context().page("ritual");
        var ritual = BookRitualRecipePageModel.create()
                .withRecipeId1(this.modLoc("ritual/familiar_parrot"));

        this.context().page("description");
        var description = BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
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

    private BookEntryModel makeFamiliarShubNiggurathEntry(CategoryEntryMap entryMap, char icon) {
        this.context().entry("familiar_shub_niggurath");

        this.context().page("entity");
        var entity = BookEntityPageModel.create()
                .withEntityId("occultism:shub_niggurath_familiar")
                .withText(this.context().pageText());

        this.context().page("ritual");
        var ritual = BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText());

        this.context().page("description");
        var description = BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText());

        return BookEntryModel.create(this.modLoc(this.context().categoryId() + "/" + this.context().entryId()), this.context().entryName())
                .withIcon(this.modLoc("textures/gui/book/familiar_shub_niggurath.png"))
                .withLocation(entryMap.get(icon))
                .withPages(
                        entity,
                        ritual,
                        description
                );
    }

    private BookEntryModel makeReturnToRitualsEntry(CategoryEntryMap entryMap, char icon) {
        this.context().entry("return_to_rituals");

        return BookEntryModel.create(this.modLoc(this.context().categoryId() + "/" + this.context().entryId()), this.context().entryName())
                .withIcon(this.modLoc("textures/gui/book/robe.png"))
                .withCategoryToOpen(this.modLoc("rituals"))
                .withEntryBackground(1, 2)
                .withLocation(entryMap.get(icon));
    }
}
