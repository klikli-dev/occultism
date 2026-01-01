package com.klikli_dev.occultism.datagen.book;

import com.klikli_dev.modonomicon.api.datagen.CategoryEntryMap;
import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookEntryModel;
import com.klikli_dev.modonomicon.api.datagen.book.BookEntryParentModel;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.condition.BookEntryReadConditionModel;
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
    protected String[] generateEntryMap() {
        return new String[]{
                "___________________",
                "_______t___________",
                "___________________",
                "_______a_w_1_2_____",
                "___________________",
                "___9_0_c___s_3_f___",
                "___________________",
                "_______m_r___4_5___",
                "___________________"
        };
    }

    @Override
    protected void generateEntries() {
        //Pentacle parents
        String craftFoliotID = this.modId() + ":" + PentaclesCategory.CATEGORY_ID + "/" + CraftFoliotEntry.ENTRY_ID;
        String craftDjinniID = this.modId() + ":" + PentaclesCategory.CATEGORY_ID + "/" + CraftDjinniEntry.ENTRY_ID;
        String craftAfritID = this.modId() + ":" + PentaclesCategory.CATEGORY_ID + "/" + CraftAfritEntry.ENTRY_ID;
        String craftMaridID = this.modId() + ":" + PentaclesCategory.CATEGORY_ID + "/" + CraftMaridEntry.ENTRY_ID;
        String contactEldritchID = this.modId() + ":" + PentaclesCategory.CATEGORY_ID + "/" + ContactEldritchSpiritEntry.ENTRY_ID;

        var overview = this.add(new StorageOverviewEntry(this).generate('0'))
                .withCondition(BookEntryReadConditionModel.create().withEntry(craftDjinniID));
        var returnToCrafting = this.add(this.makeReturnToCraftingEntry(this.entryMap));
        returnToCrafting.withParent(BookEntryParentModel.create(overview.getId()));

        var storageController = this.add(new ControllerEntry(this).generate('c'));
        storageController.withParent(BookEntryParentModel.create(overview.getId()))
                .withCondition(BookEntryReadConditionModel.create().withEntry(craftDjinniID));

        var storageSystemAutomation = this.add(new AutomationEntry(this).generate('a'));
        storageSystemAutomation.withParent(BookEntryParentModel.create(storageController.getId()));
        var storageSystemAutomationTheurgy = this.add(new AutomationTheurgyEntry(this).generate('t'));
        storageSystemAutomationTheurgy.withParent(BookEntryParentModel.create(storageSystemAutomation.getId()));
        storageSystemAutomationTheurgy.withCondition(BookModLoadedConditionModel.create().withModId("theurgy"));
        var summonManageMachine = this.add(new ManageMachineEntry(this).generate('m'));
        summonManageMachine.withParent(BookEntryParentModel.create(storageController.getId()));

        var craftStableWormhole = this.add(new StableWormholeEntry(this).generate('w'));
        craftStableWormhole.withParent(BookEntryParentModel.create(storageController.getId()));
        var craftStorageRemote = this.add(new StorageRemoteEntry(this).generate('r'));
        craftStorageRemote.withParent(BookEntryParentModel.create(storageController.getId()));
        var storageStabilizer = this.add(new StabilizerEntry(this).generate('s'));
        storageStabilizer.withParent(BookEntryParentModel.create(storageController.getId()));

        var craftStabilizerTier1 = this.add(new StabilizerTier1Entry(this).generate('1'));
        craftStabilizerTier1.withParent(BookEntryParentModel.create(storageStabilizer.getId()))
                .withCondition(BookEntryReadConditionModel.create().withEntry(craftFoliotID));
        var craftStabilizerTier2 = this.add(new StabilizerTier2Entry(this).generate('2'));
        craftStabilizerTier2.withParent(BookEntryParentModel.create(craftStabilizerTier1.getId()))
                .withCondition(BookEntryReadConditionModel.create().withEntry(craftDjinniID));
        var craftStabilizerTier3 = this.add(new StabilizerTier3Entry(this).generate('3'));
        craftStabilizerTier3.withParent(BookEntryParentModel.create(craftStabilizerTier2.getId()))
                .withCondition(BookEntryReadConditionModel.create().withEntry(craftAfritID));
        var craftStabilizerTier4 = this.add(new StabilizerTier4Entry(this).generate('4'));
        craftStabilizerTier4.withParent(BookEntryParentModel.create(craftStabilizerTier3.getId()))
                .withCondition(BookEntryReadConditionModel.create().withEntry(craftMaridID));
        var craftStabilizerTier5 = this.add(new StabilizerTier5Entry(this).generate('5'));
        craftStabilizerTier5.withParent(BookEntryParentModel.create(craftStabilizerTier4.getId()))
                .withCondition(BookEntryReadConditionModel.create().withEntry(contactEldritchID));

        var stabilizedStorage = this.add(new StabilizedStorageEntry(this).generate('f'));
        stabilizedStorage.withParent(BookEntryParentModel.create(craftStabilizerTier5.getId()))
                .withCondition(BookEntryReadConditionModel.create().withEntry(contactEldritchID));
    }

    private BookEntryModel makeReturnToCraftingEntry(CategoryEntryMap entryMap) {
        this.context().entry("return_to_crafting");

        return BookEntryModel.create(this.modLoc(this.context().categoryId() + "/" + this.context().entryId()), this.context().entryName())
                .withName("Return to Binding Rituals Category")
                .withIcon(this.modLoc("textures/gui/book/infusion.png"))
                .withCategoryToOpen(this.modLoc("crafting_rituals"))
                .withEntryBackground(1, 2)
                .withLocation(entryMap.get('9'));
    }
}
