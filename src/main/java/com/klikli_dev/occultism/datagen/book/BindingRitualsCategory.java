package com.klikli_dev.occultism.datagen.book;

import com.klikli_dev.modonomicon.api.datagen.CategoryEntryMap;
import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookEntryModel;
import com.klikli_dev.modonomicon.api.datagen.book.BookEntryParentModel;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.condition.BookTrueConditionModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookCraftingRecipePageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookSpotlightPageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookTextPageModel;
import com.klikli_dev.occultism.datagen.OccultismBookProvider;
import com.klikli_dev.occultism.datagen.book.binding_rituals.ApprenticeRitualSatchelEntry;
import com.klikli_dev.occultism.datagen.book.binding_rituals.ArtisanalRitualSatchelEntry;
import com.klikli_dev.occultism.integration.modonomicon.pages.BookRitualRecipePageModel;
import com.klikli_dev.occultism.registry.OccultismBlocks;
import com.klikli_dev.occultism.registry.OccultismItems;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

public class BindingRitualsCategory extends CategoryProvider {
    public static final String CATEGORY_ID = "crafting_rituals";

    public BindingRitualsCategory(OccultismBookProvider parent) {
        super(parent);
    }

    @Override
    protected String[] generateEntryMap() {
        return new String[]{
                "___________________________",
                "_______b_e_x_p_q___________",
                "___________________________",
                "_______d_h_c__w____________",
                "___________________________",
                "___9_0_____________________",
                "___________________________",
                "_______f_z_a__g_ĝ_ğ________",
                "___________________________",
                "___________n_m_o___________",
                "___________________________",
                "___________i_j_k_l_________",
                "___________________________"
        };
    }

    @Override
    protected void generateEntries() {
        var overview = this.add(this.makeCraftingRitualsOverviewEntry(this.entryMap, '0'));
        var returnToRituals = this.add(this.makeReturnToRitualsEntry(this.entryMap, '9'));
        returnToRituals.withParent(BookEntryParentModel.create(overview.getId()));

        var craftInfusedPickaxe = this.add(this.makeCraftInfusedPickaxeEntry(this.entryMap, 'd'));
        craftInfusedPickaxe.withParent(BookEntryParentModel.create(overview.getId()));
        var craftDimensionalMineshaft = this.add(this.makeCraftDimensionalMineshaftEntry(this.entryMap, 'b'));
        craftDimensionalMineshaft.withParent(BookEntryParentModel.create(craftInfusedPickaxe.getId()));
        var craftFoliotMiner = this.add(this.makeCraftFoliotMinerEntry(this.entryMap, 'e'));
        craftFoliotMiner.withParent(BookEntryParentModel.create(craftDimensionalMineshaft.getId()));
        var craftDjinniMiner = this.add(this.makeCraftDjinniMinerEntry(this.entryMap, 'x'));
        craftDjinniMiner.withParent(BookEntryParentModel.create(craftFoliotMiner.getId()));
        var craftAfritMiner = this.add(this.makeCraftAfritMinerEntry(this.entryMap, 'p'));
        craftAfritMiner.withParent(BookEntryParentModel.create(craftDjinniMiner.getId()));
        var craftMaridMiner = this.add(this.makeCraftMaridMinerEntry(this.entryMap, 'q'));
        craftMaridMiner.withParent(BookEntryParentModel.create(craftAfritMiner.getId()));

        var craftStorageSystem = this.add(this.makeCraftStorageSystemEntry(this.entryMap, 'z'));
        craftStorageSystem.withParent(BookEntryParentModel.create(overview.getId()));

        var craftDimensionalMatrix = this.add(this.makeCraftDimensionalMatrixEntry(this.entryMap, 'a'));
        craftDimensionalMatrix.withParent(BookEntryParentModel.create(craftStorageSystem.getId()));
        var craftStorageControllerBase = this.add(this.makeCraftStorageControllerBaseEntry(this.entryMap, 'n'));
        craftStorageControllerBase.withParent(BookEntryParentModel.create(craftDimensionalMatrix.getId()));
        var craftStabilizerTier1 = this.add(this.makeCraftStabilizerTier1Entry(this.entryMap, 'i'));
        craftStabilizerTier1.withParent(BookEntryParentModel.create(craftStorageControllerBase.getId()));
        var craftStabilizerTier2 = this.add(this.makeCraftStabilizerTier2Entry(this.entryMap, 'j'));
        craftStabilizerTier2.withParent(BookEntryParentModel.create(craftStabilizerTier1.getId()));
        var craftStabilizerTier3 = this.add(this.makeCraftStabilizerTier3Entry(this.entryMap, 'k'));
        craftStabilizerTier3.withParent(BookEntryParentModel.create(craftStabilizerTier2.getId()));
        var craftStabilizerTier4 = this.add(this.makeCraftStabilizerTier4Entry(this.entryMap, 'l'));
        craftStabilizerTier4.withParent(BookEntryParentModel.create(craftStabilizerTier3.getId()));

        var craftStableWormhole = this.add(this.makeCraftStableWormholeEntry(this.entryMap, 'm'));
        craftStableWormhole.withParent(BookEntryParentModel.create(craftStorageControllerBase.getId()));
        var craftStorageRemote = this.add(this.makeCraftStorageRemoteEntry(this.entryMap, 'o'));
        craftStorageRemote.withParent(BookEntryParentModel.create(craftStableWormhole.getId()));

        var craftOtherworldGoggles = this.add(this.makeCraftOtherworldGogglesEntry(this.entryMap, 'f'));
        craftOtherworldGoggles.withParent(BookEntryParentModel.create(overview.getId()));

        var craftSatchel = this.add(this.makeCraftSatchelEntry(this.entryMap, 'g'));
        craftSatchel.withParent(BookEntryParentModel.create(overview.getId()));

        var apprenticeRitualSatchel = this.add(new ApprenticeRitualSatchelEntry(this).generate('ĝ'));
        apprenticeRitualSatchel.withParent(craftSatchel);

        var artisanalRitualSatchel = this.add(new ArtisanalRitualSatchelEntry(this).generate('ğ'));
        artisanalRitualSatchel.withParent(apprenticeRitualSatchel);

        var craftSoulGem = this.add(this.makeCraftSoulGemEntry(this.entryMap, 'h'));
        craftSoulGem.withParent(BookEntryParentModel.create(overview.getId()));
        var craftFamiliarRing = this.add(this.makeCraftFamiliarRingEntry(this.entryMap, 'c'));
        craftFamiliarRing.withParent(BookEntryParentModel.create(craftSoulGem.getId()));

        var craftWildTrim = this.add(this.makeCraftWildTrimEntry(this.entryMap, 'w'));
        craftWildTrim.withParent(BookEntryParentModel.create(overview.getId()));

        //Note: by default entries get an "entry read" condition for their parent entry, but we want to show all of these right away
        this.category.getEntries().forEach(entry -> entry.withCondition(BookTrueConditionModel.create()));
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

    private BookEntryModel makeReturnToRitualsEntry(CategoryEntryMap entryMap, char icon) {
        this.context().entry("return_to_rituals");

        return BookEntryModel.create(this.modLoc(this.context().categoryId() + "/" + this.context().entryId()), this.context().entryName())
                .withIcon(this.modLoc("textures/gui/book/robe.png"))
                .withCategoryToOpen(this.modLoc("rituals"))
                .withEntryBackground(1, 2)
                .withLocation(entryMap.get(icon));
    }

    private BookEntryModel makeCraftingRitualsOverviewEntry(CategoryEntryMap entryMap, char icon) {
        this.context().entry("overview");

        this.context().page("intro");
        var intro = BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText());

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
        var spotlight = BookSpotlightPageModel.create()
                .withItem(Ingredient.of(OccultismBlocks.STORAGE_CONTROLLER.get()))
                .withText(this.context().pageText());

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
        var spotlight = BookSpotlightPageModel.create()
                .withItem(Ingredient.of(OccultismItems.DIMENSIONAL_MATRIX.get()))
                .withText(this.context().pageText());

        this.context().page("ritual");
        var ritual = BookRitualRecipePageModel.create()
                .withRecipeId1(this.modLoc("ritual/craft_dimensional_matrix"));

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
        var spotlight = BookSpotlightPageModel.create()
                .withItem(Ingredient.of(OccultismBlocks.DIMENSIONAL_MINESHAFT.get()))
                .withText(this.context().pageText());

        this.context().page("ritual");
        var ritual = BookRitualRecipePageModel.create()
                .withRecipeId1(this.modLoc("ritual/craft_dimensional_mineshaft"));

        this.context().page("description");
        var description = BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText());

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
        var spotlight = BookSpotlightPageModel.create()
                .withItem(Ingredient.of(OccultismItems.INFUSED_PICKAXE.get()))
                .withText(this.context().pageText());

        this.context().page("ritual");
        var ritual = BookRitualRecipePageModel.create()
                .withRecipeId1(this.modLoc("ritual/craft_infused_pickaxe"));

        return BookEntryModel.create(this.modLoc(this.context().categoryId() + "/" + this.context().entryId()), this.context().entryName())
                .withIcon(OccultismItems.INFUSED_PICKAXE.get())
                .withLocation(entryMap.get(icon))
                .withPages(
                        spotlight,
                        ritual
                );
    }

    public BookEntryModel makeCraftStorageControllerBaseEntry(CategoryEntryMap entryMap, char icon) {
        this.context().entry("craft_storage_controller_base");

        this.context().page("spotlight");
        var spotlight = BookSpotlightPageModel.create()
                .withItem(Ingredient.of(OccultismBlocks.STORAGE_CONTROLLER_BASE.get()))
                .withText(this.context().pageText());

        this.context().page("ritual");
        var ritual = BookRitualRecipePageModel.create()
                .withRecipeId1(this.modLoc("ritual/craft_storage_controller_base"));

        return BookEntryModel.create(this.modLoc(this.context().categoryId() + "/" + this.context().entryId()), this.context().entryName())
                .withIcon(OccultismBlocks.STORAGE_CONTROLLER_BASE.get())
                .withLocation(entryMap.get(icon))
                .withPages(
                        spotlight,
                        ritual
                );
    }

    public BookEntryModel makeCraftStabilizerTier1Entry(CategoryEntryMap entryMap, char icon) {
        this.context().entry("craft_stabilizer_tier1");

        this.context().page("spotlight");
        var spotlight = BookSpotlightPageModel.create()
                .withItem(Ingredient.of(OccultismBlocks.STORAGE_STABILIZER_TIER1.get()))
                .withText(this.context().pageText());

        this.context().page("ritual");
        var ritual = BookRitualRecipePageModel.create()
                .withRecipeId1(this.modLoc("ritual/craft_stabilizer_tier1"));

        return BookEntryModel.create(this.modLoc(this.context().categoryId() + "/" + this.context().entryId()), this.context().entryName())
                .withIcon(OccultismBlocks.STORAGE_STABILIZER_TIER1.get())
                .withLocation(entryMap.get(icon))
                .withPages(
                        spotlight,
                        ritual
                );
    }

    public BookEntryModel makeCraftStabilizerTier2Entry(CategoryEntryMap entryMap, char icon) {
        this.context().entry("craft_stabilizer_tier2");

        this.context().page("spotlight");
        var spotlight = BookSpotlightPageModel.create()
                .withItem(Ingredient.of(OccultismBlocks.STORAGE_STABILIZER_TIER2.get()))
                .withText(this.context().pageText());

        this.context().page("ritual");
        var ritual = BookRitualRecipePageModel.create()
                .withRecipeId1(this.modLoc("ritual/craft_stabilizer_tier2"));

        return BookEntryModel.create(this.modLoc(this.context().categoryId() + "/" + this.context().entryId()), this.context().entryName())
                .withIcon(OccultismBlocks.STORAGE_STABILIZER_TIER2.get())
                .withLocation(entryMap.get(icon))
                .withPages(
                        spotlight,
                        ritual
                );
    }

    public BookEntryModel makeCraftStabilizerTier3Entry(CategoryEntryMap entryMap, char icon) {
        this.context().entry("craft_stabilizer_tier3");

        this.context().page("spotlight");
        var spotlight = BookSpotlightPageModel.create()
                .withItem(Ingredient.of(OccultismBlocks.STORAGE_STABILIZER_TIER3.get()))
                .withText(this.context().pageText());

        this.context().page("ritual");
        var ritual = BookRitualRecipePageModel.create()
                .withRecipeId1(this.modLoc("ritual/craft_stabilizer_tier3"));

        return BookEntryModel.create(this.modLoc(this.context().categoryId() + "/" + this.context().entryId()), this.context().entryName())
                .withIcon(OccultismBlocks.STORAGE_STABILIZER_TIER3.get())
                .withLocation(entryMap.get(icon))
                .withPages(
                        spotlight,
                        ritual
                );
    }

    public BookEntryModel makeCraftStabilizerTier4Entry(CategoryEntryMap entryMap, char icon) {
        this.context().entry("craft_stabilizer_tier4");

        this.context().page("spotlight");
        var spotlight = BookSpotlightPageModel.create()
                .withItem(Ingredient.of(OccultismBlocks.STORAGE_STABILIZER_TIER4.get()))
                .withText(this.context().pageText());

        this.context().page("ritual");
        var ritual = BookRitualRecipePageModel.create()
                .withRecipeId1(this.modLoc("ritual/craft_stabilizer_tier4"));

        return BookEntryModel.create(this.modLoc(this.context().categoryId() + "/" + this.context().entryId()), this.context().entryName())
                .withIcon(OccultismBlocks.STORAGE_STABILIZER_TIER4.get())
                .withLocation(entryMap.get(icon))
                .withPages(
                        spotlight,
                        ritual
                );
    }

    public BookEntryModel makeCraftStableWormholeEntry(CategoryEntryMap entryMap, char icon) {
        this.context().entry("craft_stable_wormhole");

        this.context().page("spotlight");
        var spotlight = BookSpotlightPageModel.create()
                .withItem(Ingredient.of(OccultismBlocks.STABLE_WORMHOLE.get()))
                .withText(this.context().pageText());

        this.context().page("ritual");
        var ritual = BookRitualRecipePageModel.create()
                .withRecipeId1(this.modLoc("ritual/craft_stable_wormhole"));

        return BookEntryModel.create(this.modLoc(this.context().categoryId() + "/" + this.context().entryId()), this.context().entryName())
                .withIcon(OccultismBlocks.STABLE_WORMHOLE.get())
                .withLocation(entryMap.get(icon))
                .withPages(
                        spotlight,
                        ritual
                );
    }

    public BookEntryModel makeCraftStorageRemoteEntry(CategoryEntryMap entryMap, char icon) {
        this.context().entry("craft_storage_remote");

        this.context().page("spotlight");
        var spotlight = BookSpotlightPageModel.create()
                .withItem(Ingredient.of(OccultismItems.STORAGE_REMOTE.get()))
                .withText(this.context().pageText());

        this.context().page("ritual");
        var ritual = BookRitualRecipePageModel.create()
                .withRecipeId1(this.modLoc("ritual/craft_storage_remote"));

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
        var intro = BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText());

        this.context().page("magic_lamp");
        var lamp = BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText());

        this.context().page("magic_lamp_recipe");
        var lampRecipe = BookCraftingRecipePageModel.create()
                .withRecipeId1(this.modLoc("crafting/magic_lamp_empty"));

        this.context().page("spotlight");
        var spotlight = BookSpotlightPageModel.create()
                .withItem(Ingredient.of(OccultismItems.MINER_FOLIOT_UNSPECIALIZED.get()))
                .withText(this.context().pageText());

        this.context().page("ritual");
        var ritual = BookRitualRecipePageModel.create()
                .withRecipeId1(this.modLoc("ritual/craft_miner_foliot_unspecialized"));

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
        var spotlight = BookSpotlightPageModel.create()
                .withItem(Ingredient.of(OccultismItems.MINER_DJINNI_ORES.get()))
                .withText(this.context().pageText());

        this.context().page("ritual");
        var ritual = BookRitualRecipePageModel.create()
                .withRecipeId1(this.modLoc("ritual/craft_miner_djinni_ores"));

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
        var spotlight = BookSpotlightPageModel.create()
                .withItem(Ingredient.of(OccultismItems.MINER_AFRIT_DEEPS.get()))
                .withText(this.context().pageText());

        this.context().page("ritual");
        var ritual = BookRitualRecipePageModel.create()
                .withRecipeId1(this.modLoc("ritual/craft_miner_afrit_deeps"));

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
        var spotlight = BookSpotlightPageModel.create()
                .withItem(Ingredient.of(OccultismItems.MINER_MARID_MASTER.get()))
                .withText(this.context().pageText());

        this.context().page("ritual");
        var ritual = BookRitualRecipePageModel.create()
                .withRecipeId1(this.modLoc("ritual/craft_miner_marid_master"));

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
        var spotlight = BookSpotlightPageModel.create()
                .withItem(Ingredient.of(OccultismItems.SATCHEL.get()))
                .withText(this.context().pageText());

        this.context().page("ritual");
        var ritual = BookRitualRecipePageModel.create()
                .withRecipeId1(this.modLoc("ritual/craft_satchel"));

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
        var spotlight = BookSpotlightPageModel.create()
                .withItem(Ingredient.of(OccultismItems.SOUL_GEM_ITEM.get()))
                .withText(this.context().pageText());

        this.context().page("usage");
        var usage = BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText());

        this.context().page("ritual");
        var ritual = BookRitualRecipePageModel.create()
                .withRecipeId1(this.modLoc("ritual/craft_soul_gem"));

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
        var spotlight = BookSpotlightPageModel.create()
                .withItem(Ingredient.of(OccultismItems.FAMILIAR_RING.get()))
                .withText(this.context().pageText());

        this.context().page("usage");
        var usage = BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText());

        this.context().page("ritual");
        var ritual = BookRitualRecipePageModel.create()
                .withRecipeId1(this.modLoc("ritual/craft_familiar_ring"));

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
        var gogglesSpotlight = BookSpotlightPageModel.create()
                .withItem(Ingredient.of(OccultismItems.OTHERWORLD_GOGGLES.get()))
                .withText(this.context().pageText());

        this.context().page("goggles_more");
        var gogglesMore = BookTextPageModel.create()
                .withText(this.context().pageText());

        this.context().page("lenses_spotlight");
        var lensesSpotlight = BookSpotlightPageModel.create()
                .withItem(Ingredient.of(OccultismItems.LENSES.get()))
                .withText(this.context().pageText());

        this.context().page("lenses_more");
        var lensesMore = BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText());

        this.context().page("lenses_recipe");
        var lensesRecipe = BookCraftingRecipePageModel.create()
                .withRecipeId1(this.modLoc("crafting/lenses"));

        this.context().page("ritual");
        var ritual = BookRitualRecipePageModel.create()
                .withRecipeId1(this.modLoc("ritual/craft_infused_lenses"));

        this.context().page("goggles_recipe");
        var gogglesRecipe = BookCraftingRecipePageModel.create()
                .withRecipeId1(this.modLoc("crafting/goggles"));

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

    private BookEntryModel makeCraftWildTrimEntry(CategoryEntryMap entryMap, char icon) {
        this.context().entry("craft_wild_trim");

        this.context().page("spotlight");
        var spotlight = BookSpotlightPageModel.create()
                .withItem(Ingredient.of(Items.WILD_ARMOR_TRIM_SMITHING_TEMPLATE))
                .withText(this.context().pageText());

        this.context().page("ritual");
        var ritual = BookRitualRecipePageModel.create()
                .withRecipeId1(this.modLoc("ritual/craft_wild_trim"));

        return BookEntryModel.create(this.modLoc(this.context().categoryId() + "/" + this.context().entryId()), this.context().entryName())
                .withIcon(Items.WILD_ARMOR_TRIM_SMITHING_TEMPLATE)
                .withLocation(entryMap.get(icon))
                .withPages(
                        spotlight,
                        ritual
                );
    }
}
