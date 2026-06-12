package com.klikli_dev.occultism.datagen.book;

import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookEntryParentModel;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.occultism.datagen.OccultismResearch;
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
    protected void generateEntries() {
        var overview = this.add(new RitualOverviewEntry(this).generate());
        this.layout().entry(overview).at(-1, 0);

        var itemUse = this.add(new ItemUseEntry(this).generate());
        itemUse.withParent(BookEntryParentModel.create(overview.getId()));
        this.layout().entry(itemUse).rightOf(overview, 2);

        var sacrifice = this.add(new SacrificeEntry(this).generate());
        sacrifice.withParent(BookEntryParentModel.create(itemUse.getId()));
        this.layout().entry(sacrifice).rightOf(itemUse, 2);

        var summoning = this.add(new SummoningRitualsSubcategoryEntry(this).generate());
        summoning.withParent(BookEntryParentModel.create(sacrifice.getId()));
        summoning.withCategoryToOpen(this.modLoc("summoning_rituals")).withCondition(OccultismResearch.PENTACLES_SUMMON_FOLIOT);
        this.layout().entry(summoning).rightOf(sacrifice, 4).above(2);

        var possession = this.add(new PossessionRitualsSubcategoryEntry(this).generate());
        possession.withParent(BookEntryParentModel.create(sacrifice.getId()));
        possession.withCategoryToOpen(this.modLoc("possession_rituals")).withCondition(OccultismResearch.PENTACLES_POSSESS_FOLIOT);
        this.layout().entry(possession).rightOf(sacrifice, 2).above(2);

        var crafting = this.add(new CraftingRitualsSubcategoryEntry(this).generate());
        crafting.withParent(BookEntryParentModel.create(sacrifice.getId()));
        crafting.withCategoryToOpen(this.modLoc("crafting_rituals")).withCondition(OccultismResearch.PENTACLES_CRAFT_FOLIOT);
        this.layout().entry(crafting).rightOf(sacrifice, 2).below(2);

        var familiars = this.add(new FamiliarRitualsSubcategoryEntry(this).generate());
        familiars.withParent(BookEntryParentModel.create(sacrifice.getId()));
        familiars.withCategoryToOpen(this.modLoc("familiar_rituals")).withCondition(OccultismResearch.PENTACLES_POSSESS_FOLIOT);
        this.layout().entry(familiars).rightOf(sacrifice, 4).below(2);

        itemUse.withCondition(OccultismResearch.PENTACLES_SUMMON_FOLIOT);
        sacrifice.withCondition(OccultismResearch.PENTACLES_SUMMON_FOLIOT);
    }
}
