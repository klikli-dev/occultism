package com.klikli_dev.occultism.datagen.book;

import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookEntryParentModel;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.condition.BookEntryReadConditionModel;
import com.klikli_dev.occultism.datagen.OccultismBookProvider;
import com.klikli_dev.occultism.datagen.book.rituals.*;

public class RitualsCategory extends CategoryProvider {

    public static final String CATEGORY_ID = "rituals";

    public RitualsCategory(OccultismBookProvider parent) {
        super(parent);
    }

    @Override
    protected String categoryName() {
        return "Rituals";
    }

    @Override
    protected BookIconModel categoryIcon() {
        return BookIconModel.create(this.modLoc("textures/gui/book/robe.png"));
    }

    @Override
    public String categoryId() {
        return CATEGORY_ID;
    }

    @Override
    protected String[] generateEntryMap() {
        return new String[]{
                "___________________",
                "______________p_s__",
                "___________________",
                "________o_i_k______",
                "___________________",
                "______________c_f__",
                "___________________"
        };
    }

    @Override
    protected void generateEntries() {
        var overview = this.add(new RitualOverviewEntry(this).generate('o'));
        var itemUse = this.add(new ItemUseEntry(this).generate('i'));
        itemUse.withParent(BookEntryParentModel.create(overview.getId()));
        var sacrifice = this.add(new SacrificeEntry(this).generate('k'));
        sacrifice.withParent(BookEntryParentModel.create(itemUse.getId()));

        var summoning = this.add(new SummoningRitualsSubcategoryEntry(this).generate('s'));
        summoning.withParent(BookEntryParentModel.create(sacrifice.getId()));
        summoning.withCategoryToOpen(this.modLoc("summoning_rituals")).withCondition(BookEntryReadConditionModel.create().withEntry(this.modLoc("pentacles/summon_foliot")));
        var possession = this.add(new PossessionRitualsSubcategoryEntry(this).generate('p'));
        possession.withParent(BookEntryParentModel.create(sacrifice.getId()));
        possession.withCategoryToOpen(this.modLoc("possession_rituals")).withCondition(BookEntryReadConditionModel.create().withEntry(this.modLoc("pentacles/possess_foliot")));
        var crafting = this.add(new CraftingRitualsSubcategoryEntry(this).generate('c'));
        crafting.withParent(BookEntryParentModel.create(sacrifice.getId()));
        crafting.withCategoryToOpen(this.modLoc("crafting_rituals")).withCondition(BookEntryReadConditionModel.create().withEntry(this.modLoc("pentacles/craft_foliot")));
        var familiars = this.add(new FamiliarRitualsSubcategoryEntry(this).generate('f'));
        familiars.withParent(BookEntryParentModel.create(sacrifice.getId()));
        familiars.withCategoryToOpen(this.modLoc("familiar_rituals")).withCondition(BookEntryReadConditionModel.create().withEntry(this.modLoc("pentacles/possess_foliot")));

        itemUse.withCondition(BookEntryReadConditionModel.create().withEntry(this.modLoc("pentacles/summon_foliot")));
        sacrifice.withCondition(BookEntryReadConditionModel.create().withEntry(this.modLoc("pentacles/summon_foliot")));
    }
}
