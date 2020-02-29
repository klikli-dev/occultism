/*
 * MIT License
 *
 * Copyright 2020 klikli-dev
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
 * associated documentation files (the "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
 * of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following
 * conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial
 * portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR
 * PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT
 * OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
 * OTHER DEALINGS IN THE SOFTWARE.
 */

package com.github.klikli_dev.occultism.datagen;

import com.github.klikli_dev.occultism.registry.OccultismBlocks;
import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.storage.loot.ConstantRange;
import net.minecraft.world.storage.loot.ItemLootEntry;
import net.minecraft.world.storage.loot.LootPool;
import net.minecraft.world.storage.loot.LootTable;
import net.minecraft.world.storage.loot.conditions.SurvivesExplosion;
import net.minecraft.world.storage.loot.functions.CopyNbt;
import net.minecraftforge.fml.RegistryObject;

public class StandardLootTableProvider extends BaseLootTableProvider {

    //region Fields
    InternalBlockLootTable lootTable = new InternalBlockLootTable();
    //endregion Fields

    //region Initialization
    public StandardLootTableProvider(DataGenerator dataGeneratorIn) {
        super(dataGeneratorIn);
    }
    //endregion Initialization

    //region Overrides
    @Override
    protected void addTables() {
        this.lootTable.addTables();
    }
    //endregion Overrides

    private class InternalBlockLootTable extends StandardBlockLootTables {
        //region Overrides
        @Override
        protected void addTables() {
            //Handle the non custom tables
            OccultismBlocks.BLOCKS.getEntries().stream()
                    .map(RegistryObject::get)
                    .forEach(block -> {
                        OccultismBlocks.BlockDataGenSettings settings = OccultismBlocks.BLOCK_DATA_GEN_SETTINGS
                                                                                .get(block.getRegistryName());
                        if (settings.lootTableType == OccultismBlocks.LootTableType.EMPTY)
                            this.registerDropNothingLootTable(block);
                        else if (settings.lootTableType == OccultismBlocks.LootTableType.DROP_SELF)
                            this.registerDropSelfLootTable(block);
                    });

            this.registerLootTable(OccultismBlocks.STORAGE_CONTROLLER.get(),
                    this.storageController(OccultismBlocks.STORAGE_CONTROLLER.get()));
            this.registerDropWithNBTLootTable(OccultismBlocks.STABLE_WORMHOLE.get());
        }

        @Override
        protected void registerLootTable(Block blockIn, LootTable.Builder table) {
            StandardLootTableProvider.this.lootTables.put(blockIn, table);
        }
        //endregion Overrides

//region Methods
        protected LootTable.Builder storageController(Block block) {
            LootPool.Builder builder = LootPool.builder()
                       .rolls(ConstantRange.of(1))
                       .addEntry(ItemLootEntry.builder(block)
                                         .acceptFunction(
                                                 CopyNbt.builder(CopyNbt.Source.BLOCK_ENTITY)
                                                         .replaceOperation("items","BlockEntityTag.items")
                                                         .replaceOperation("sortDirection","BlockEntityTag.sortDirection")
                                                         .replaceOperation("sortType","BlockEntityTag.sortType")
                                                         .replaceOperation("maxSlots","BlockEntityTag.maxSlots")
                                                         .replaceOperation("matrix","BlockEntityTag.matrix")
                                                         .replaceOperation("orderStack","BlockEntityTag.orderStack")
                                                         .replaceOperation("linkedMachines","BlockEntityTag.linkedMachines")

                                         )


                       )
                       .acceptCondition(SurvivesExplosion.builder());
            return LootTable.builder().addLootPool(builder);
        }
//endregion Methods
    }
}
