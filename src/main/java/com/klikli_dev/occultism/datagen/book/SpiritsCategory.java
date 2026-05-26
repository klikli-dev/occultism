package com.klikli_dev.occultism.datagen.book;

import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookEntryParentModel;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.condition.BookTrueConditionModel;
import com.klikli_dev.occultism.datagen.OccultismBookProvider;
import com.klikli_dev.occultism.datagen.book.spirits.*;

public class SpiritsCategory extends CategoryProvider {

    public static final String CATEGORY_ID = "spirits";

    public SpiritsCategory(OccultismBookProvider parent) {
        super(parent);
    }

    @Override
    protected void generateEntries() {
        var overview = this.add(new SpiritsOverviewEntry(this).generate());
        this.layout().entry(overview).at(-8, -1);

        var returnToGettingStarted = this.add(new ReturnToGettingStartedEntry(this).generate());
        returnToGettingStarted.withParent(BookEntryParentModel.create(overview.getId()));
        returnToGettingStarted.withCondition(BookTrueConditionModel.create());
        this.layout().entry(returnToGettingStarted).leftOf(overview, 2);

        var essenceDecay = this.add(new EssenceDecayEntry(this).generate());
        essenceDecay.withParent(BookEntryParentModel.create(overview.getId()));
        this.layout().entry(essenceDecay).below(overview, 2);

        var trueNames = this.add(new TrueNamesEntry(this).generate());
        trueNames.withParent(BookEntryParentModel.create(overview.getId()));
        this.layout().entry(trueNames).rightOf(overview, 2);

        var unboundSpirits = this.add(new UnboundSpiritsEntry(this).generate());
        unboundSpirits.withParent(BookEntryParentModel.create(trueNames.getId()));
        this.layout().entry(unboundSpirits).rightOf(trueNames, 2);

        var wildHunt = this.add(new WildHuntEntry(this).generate());
        wildHunt.withParent(BookEntryParentModel.create(unboundSpirits.getId()));
        this.layout().entry(wildHunt).rightOf(unboundSpirits, 2);
    }

    @Override
    protected String categoryName() {
        return "Spirits";
    }

    @Override
    protected BookIconModel categoryIcon() {
        return BookIconModel.create(this.modLoc("textures/gui/book/spirits.png"));
    }

    @Override
    public String categoryId() {
        return CATEGORY_ID;
    }
}
