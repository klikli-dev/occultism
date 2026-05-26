package com.klikli_dev.occultism.datagen.book;

import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookCategoryModel;
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
    protected String[] generateEntryMap() {
        return new String[]{
                "___________________________",
                "___________________________",
                "___<_0_n_u_w_______________",
                "___________________________",
                "_____d_____________________",
                "___________________________",
                "___________________________"
        };
    }

    @Override
    protected void generateEntries() {
        var overview = this.add(new SpiritsOverviewEntry(this).generate('0'));
        var returnToGettingStarted = this.add(new ReturnToGettingStartedEntry(this).generate('<'));
        returnToGettingStarted.withParent(BookEntryParentModel.create(overview.getId()));
        returnToGettingStarted.withCondition(BookTrueConditionModel.create());

        var essenceDecay = this.add(new EssenceDecayEntry(this).generate('d'));
        essenceDecay.withParent(BookEntryParentModel.create(overview.getId()));

        var trueNames = this.add(new TrueNamesEntry(this).generate('n'));
        trueNames.withParent(BookEntryParentModel.create(overview.getId()));

        var unboundSpirits = this.add(new UnboundSpiritsEntry(this).generate('u'));
        unboundSpirits.withParent(BookEntryParentModel.create(trueNames.getId()));

        var wildHunt = this.add(new WildHuntEntry(this).generate('w'));
        wildHunt.withParent(BookEntryParentModel.create(unboundSpirits.getId()));
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
