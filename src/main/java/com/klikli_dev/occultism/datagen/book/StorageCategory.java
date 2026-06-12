package com.klikli_dev.occultism.datagen.book;

import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookEntryParentModel;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.occultism.datagen.OccultismResearch;
import com.klikli_dev.modonomicon.api.datagen.book.condition.BookModLoadedConditionModel;
import com.klikli_dev.occultism.datagen.OccultismBookProvider;
import com.klikli_dev.occultism.datagen.book.binding_rituals.*;
import com.klikli_dev.occultism.datagen.book.pentacles.*;
import com.klikli_dev.occultism.datagen.book.storage_system.*;
import com.klikli_dev.occultism.datagen.book.summoning_rituals.ManageMachineEntry;
import net.minecraft.world.item.Items;

public class StorageCategory extends CategoryProvider {

    public static final String CATEGORY_ID = "storage";

    public StorageCategory(OccultismBookProvider parent) {
        super(parent);
    }

    @Override
    protected String categoryName() {
        return "Magic Storage";
    }

    @Override
    protected BookIconModel categoryIcon() {
        return BookIconModel.create(Items.CHEST);
    }

    @Override
    public String categoryId() {
        return CATEGORY_ID;
    }

    @Override
    protected void generateEntries() {
        var overview = this.add(new StorageOverviewEntry(this).generate())
                .withCondition(OccultismResearch.PENTACLES_CRAFT_DJINNI);
        this.layout().entry(overview).at(-4, 1);

        var returnToCrafting = this.add(new ReturnToCraftingEntry(this).generate());
        returnToCrafting.withCategoryToOpen(this.modLoc("crafting_rituals"));
        returnToCrafting.withParent(BookEntryParentModel.create(overview.getId()));
        this.layout().entry(returnToCrafting).leftOf(overview, 2);

        var storageController = this.add(new ControllerEntry(this).generate());
        storageController.withParent(BookEntryParentModel.create(overview.getId()))
                .withCondition(OccultismResearch.PENTACLES_CRAFT_DJINNI);
        this.layout().entry(storageController).rightOf(overview, 2);

        var storageSystemAutomation = this.add(new AutomationEntry(this).generate());
        storageSystemAutomation.withParent(BookEntryParentModel.create(storageController.getId()));
        this.layout().entry(storageSystemAutomation).above(storageController, 2);

        var storageSystemAutomationTheurgy = this.add(new AutomationTheurgyEntry(this).generate());
        storageSystemAutomationTheurgy.withParent(BookEntryParentModel.create(storageSystemAutomation.getId()));
        storageSystemAutomationTheurgy.withCondition(BookModLoadedConditionModel.create().withModId("theurgy"));
        this.layout().entry(storageSystemAutomationTheurgy).above(storageSystemAutomation, 2);

        var summonManageMachine = this.add(new ManageMachineEntry(this).generate());
        summonManageMachine.withParent(BookEntryParentModel.create(storageController.getId()));
        this.layout().entry(summonManageMachine).below(storageController, 2);

        var craftStableWormhole = this.add(new StableWormholeEntry(this).generate());
        craftStableWormhole.withParent(BookEntryParentModel.create(storageController.getId()));
        this.layout().entry(craftStableWormhole).rightOf(storageController, 2).above(2);

        var craftStorageRemote = this.add(new StorageRemoteEntry(this).generate());
        craftStorageRemote.withParent(BookEntryParentModel.create(storageController.getId()));
        this.layout().entry(craftStorageRemote).rightOf(storageController, 2).below(2);

        var storageStabilizer = this.add(new StabilizerEntry(this).generate());
        storageStabilizer.withParent(BookEntryParentModel.create(storageController.getId()));
        this.layout().entry(storageStabilizer).rightOf(storageController, 4);

        var craftStabilizerTier1 = this.add(new StabilizerTier1Entry(this).generate());
        craftStabilizerTier1.withParent(BookEntryParentModel.create(storageStabilizer.getId()))
                .withCondition(OccultismResearch.PENTACLES_CRAFT_FOLIOT);
        this.layout().entry(craftStabilizerTier1).above(storageStabilizer, 2);

        var craftStabilizerTier2 = this.add(new StabilizerTier2Entry(this).generate());
        craftStabilizerTier2.withParent(BookEntryParentModel.create(craftStabilizerTier1.getId()))
                .withCondition(OccultismResearch.PENTACLES_CRAFT_DJINNI);
        this.layout().entry(craftStabilizerTier2).rightOf(craftStabilizerTier1, 2);

        var craftStabilizerTier3 = this.add(new StabilizerTier3Entry(this).generate());
        craftStabilizerTier3.withParent(BookEntryParentModel.create(craftStabilizerTier2.getId()))
                .withCondition(OccultismResearch.PENTACLES_CRAFT_AFRIT);
        this.layout().entry(craftStabilizerTier3).below(craftStabilizerTier2, 2);

        var craftStabilizerTier4 = this.add(new StabilizerTier4Entry(this).generate());
        craftStabilizerTier4.withParent(BookEntryParentModel.create(craftStabilizerTier3.getId()))
                .withCondition(OccultismResearch.PENTACLES_CRAFT_MARID);
        this.layout().entry(craftStabilizerTier4).below(craftStabilizerTier3, 2);

        var craftStabilizerTier5 = this.add(new StabilizerTier5Entry(this).generate());
        craftStabilizerTier5.withParent(BookEntryParentModel.create(craftStabilizerTier4.getId()))
                .withCondition(OccultismResearch.PENTACLES_CONTACT_ELDRITCH);
        this.layout().entry(craftStabilizerTier5).rightOf(craftStabilizerTier4, 2);

        var stabilizedStorage = this.add(new StabilizedStorageEntry(this).generate());
        stabilizedStorage.withParent(BookEntryParentModel.create(craftStabilizerTier5.getId()))
                .withCondition(OccultismResearch.PENTACLES_CONTACT_ELDRITCH);
        this.layout().entry(stabilizedStorage).above(craftStabilizerTier5, 2);
    }

}
