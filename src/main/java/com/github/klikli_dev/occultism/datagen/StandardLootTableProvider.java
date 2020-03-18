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

import com.github.klikli_dev.occultism.Occultism;
import com.github.klikli_dev.occultism.common.block.crops.IReplantableCrops;
import com.github.klikli_dev.occultism.common.block.otherworld.IOtherworldBlock;
import com.github.klikli_dev.occultism.registry.OccultismBlocks;
import com.github.klikli_dev.occultism.registry.OccultismEntities;
import com.github.klikli_dev.occultism.registry.OccultismItems;
import net.minecraft.advancements.criterion.StatePropertiesPredicate;
import net.minecraft.block.Block;
import net.minecraft.block.CropsBlock;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.loot.EntityLootTables;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Items;
import net.minecraft.world.storage.loot.*;
import net.minecraft.world.storage.loot.conditions.BlockStateProperty;
import net.minecraft.world.storage.loot.conditions.ILootCondition;
import net.minecraft.world.storage.loot.conditions.TableBonus;
import net.minecraft.world.storage.loot.functions.LootingEnchantBonus;
import net.minecraft.world.storage.loot.functions.SetCount;
import net.minecraftforge.fml.RegistryObject;

public class StandardLootTableProvider extends BaseLootTableProvider {

    //region Fields
    InternalBlockLootTable blockLoot = new InternalBlockLootTable();
    InternalEntityLootTable entityLoot = new InternalEntityLootTable();
    //endregion Fields

    //region Initialization
    public StandardLootTableProvider(DataGenerator dataGeneratorIn) {
        super(dataGeneratorIn);
    }
    //endregion Initialization

    //region Overrides
    @Override
    protected void addTables() {
        this.blockLoot.addTables();
        this.entityLoot.addTables();
    }
    //endregion Overrides

    private class InternalEntityLootTable extends EntityLootTables {
//region Overrides
        @Override
        protected void addTables() {
            //Guaranteed end stone drop for endermite
            this.registerLootTable(OccultismEntities.POSSESSED_ENDERMITE_TYPE.get(),
                    LootTable.builder().addLootPool(
                            LootPool.builder().rolls(ConstantRange.of(1))
                                    .addEntry(ItemLootEntry.builder(Items.END_STONE)
                                                      .acceptFunction(SetCount.builder(RandomValueRange.of(1.0f, 2.0F)))
                                                      .acceptFunction(LootingEnchantBonus.builder(
                                                              RandomValueRange.of(0.0F, 1.0F))))));

            //Guaranteed ender pearl drop for enderman
            this.registerLootTable(OccultismEntities.POSSESSED_ENDERMAN_TYPE.get(),
                    LootTable.builder().addLootPool(
                            LootPool.builder().rolls(ConstantRange.of(1))
                                    .addEntry(ItemLootEntry.builder(Items.ENDER_PEARL)
                                                      .acceptFunction(SetCount.builder(RandomValueRange.of(1.0f, 3.0F)))
                                                      .acceptFunction(LootingEnchantBonus.builder(
                                                              RandomValueRange.of(0.0F, 1.0F))))));

            //Guaranteed skeleton skull drop for skeleton
            this.registerLootTable(OccultismEntities.POSSESSED_SKELETON_TYPE.get(),
                    LootTable.builder()
                            .addLootPool(LootPool.builder().rolls(ConstantRange.of(1)).addEntry(
                                    ItemLootEntry.builder(Items.ARROW)
                                    .acceptFunction(SetCount.builder(RandomValueRange.of(0.0F, 2.0F)))
                                    .acceptFunction(LootingEnchantBonus.builder(RandomValueRange.of(0.0F, 1.0F)))))
                            .addLootPool(LootPool.builder().rolls(ConstantRange.of(1)).addEntry(
                                    ItemLootEntry.builder(Items.SKELETON_SKULL)
                                            .acceptFunction(SetCount.builder(RandomValueRange.of(1.0F, 1.0F)))
                                            .acceptFunction(LootingEnchantBonus.builder(RandomValueRange.of(0.0F, 1.0F)))))
                            .addLootPool(LootPool.builder().rolls(ConstantRange.of(1)).addEntry(
                                    ItemLootEntry.builder(Items.BONE)
                                            .acceptFunction(SetCount.builder(RandomValueRange.of(0.0F, 2.0F)))
                                            .acceptFunction(
                                                    LootingEnchantBonus.builder(RandomValueRange.of(0.0F, 1.0F))))));

            this.registerLootTable(OccultismEntities.AFRIT_WILD_TYPE.get(),
                    LootTable.builder().addLootPool(
                            LootPool.builder().rolls(ConstantRange.of(1))
                                    .addEntry(ItemLootEntry.builder(OccultismItems.AFRIT_ESSENCE.get())
                                                      .acceptFunction(SetCount.builder(RandomValueRange.of(0.7f, 1.0F)))
                                                      .acceptFunction(LootingEnchantBonus.builder(
                                                              RandomValueRange.of(0.0F, 1.0F))))));
        }

        @Override
        protected void registerLootTable(EntityType<?> type, LootTable.Builder table) {
            StandardLootTableProvider.this.entityLootTable.put(type, table);
        }
//endregion Overrides
    }

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
                        else if (settings.lootTableType == OccultismBlocks.LootTableType.REPLANTABLE_CROP) {
                            IReplantableCrops cropsBlock = (IReplantableCrops) block;
                            ILootCondition.IBuilder lootCondition =
                                    BlockStateProperty.builder(block).fromProperties(
                                            StatePropertiesPredicate.Builder.newBuilder()
                                                    .withIntProp(CropsBlock.AGE, 7));
                            this.registerLootTable(block,
                                    droppingAndBonusWhen(block, cropsBlock.getCropsItem().asItem(),
                                            cropsBlock.getSeedsItem().asItem(), lootCondition));
                        }
                        else if (settings.lootTableType == OccultismBlocks.LootTableType.DROP_SELF)
                            this.registerDropSelfLootTable(block);
                        else if (settings.lootTableType == OccultismBlocks.LootTableType.OTHERWORLD_BLOCK)
                            this.registerOtherworldBlockTable(block);
                    });

            this.registerLootTable(OccultismBlocks.OTHERWORLD_LEAVES.get(),
                    (block) -> droppingWithChancesAndSticks(block, OccultismBlocks.OTHERWORLD_SAPLING.get(),
                            DEFAULT_SAPLING_DROP_RATES));
        }

        @Override
        protected void registerLootTable(Block blockIn, LootTable.Builder table) {
            StandardLootTableProvider.this.blockLootTable.put(blockIn, table);
        }
        //endregion Overrides

        //region Methods
        protected void registerOtherworldBlockTable(Block block) {
            if (block instanceof IOtherworldBlock)
                this.registerLootTable(block, this.createOtherworldBlockTable(block));
            else
                Occultism.LOGGER.warn("Tried to register otherworld block loot table for non-otherworld block {}",
                        block.getRegistryName());
        }

        /**
         * Does not work currently, leads to no sapling drop.
         */
        @Deprecated
        protected void registerOtherworldLeavesTable(Block block, Block coveredSapling,
                                                     Block uncoveredSapling, float... chances) {
            if (block instanceof IOtherworldBlock)
                this.registerLootTable(block,
                        this.otherWorldLeavesDroppingWithChancesAndSticks(block, coveredSapling, uncoveredSapling,
                                chances));
            else
                Occultism.LOGGER.warn("Tried to register otherworld leaves loot table for non-otherworld block {}",
                        block.getRegistryName());
        }

        protected LootTable.Builder createOtherworldBlockTable(Block block) {
            IOtherworldBlock otherworldBlock = (IOtherworldBlock) block;
            ILootCondition.IBuilder uncoveredCondition =
                    BlockStateProperty.builder(block).fromProperties(
                            StatePropertiesPredicate.Builder.newBuilder()
                                    .withBoolProp(IOtherworldBlock.UNCOVERED, true));
            LootPool.Builder builder = LootPool.builder()
                                               .rolls(ConstantRange.of(1))
                                               .addEntry(ItemLootEntry.builder(otherworldBlock.getUncoveredBlock())
                                                                 .acceptCondition(uncoveredCondition)
                                                                 .alternatively(ItemLootEntry.builder(
                                                                         otherworldBlock.getCoveredBlock()))
                                               );
            return LootTable.builder().addLootPool(builder);
        }

        /**
         * Does not work currently, leads to no sapling drop
         */
        @Deprecated
        protected LootTable.Builder otherWorldLeavesDroppingWithChancesAndSticks(Block forBlock, Block coveredSapling,
                                                                                 Block uncoveredSapling,
                                                                                 float... chances) {
            IOtherworldBlock otherworldBlock = (IOtherworldBlock) forBlock;
            ILootCondition.IBuilder uncoveredCondition =
                    BlockStateProperty.builder(forBlock).fromProperties(
                            StatePropertiesPredicate.Builder.newBuilder()
                                    .withBoolProp(IOtherworldBlock.UNCOVERED, true));


            return this.droppingAlternativeWithChancesAndSticks(forBlock,
                    //Leaves entry
                    ItemLootEntry.builder(otherworldBlock.getUncoveredBlock())
                            .acceptCondition(uncoveredCondition)
                            .alternatively(ItemLootEntry.builder(
                                    otherworldBlock.getCoveredBlock())),
                    //Sapling entry
                    ItemLootEntry.builder(uncoveredSapling)
                            .acceptCondition(uncoveredCondition)
                            .alternatively(ItemLootEntry.builder(coveredSapling)), chances);
        }

        protected LootTable.Builder droppingAlternativeWithChancesAndSticks(Block leaves,
                                                                            LootEntry.Builder<?> leavesEntry,
                                                                            LootEntry.Builder<?> saplingEntry,
                                                                            float... chances) {
            return this.droppingAlternativeWithSilkTouchOrShears(leavesEntry,
                    withSurvivesExplosion(leaves, saplingEntry)
                            .acceptCondition(TableBonus.builder(Enchantments.FORTUNE, chances)))
                           .addLootPool(LootPool.builder().rolls(ConstantRange.of(1))
                                                .acceptCondition(NOT_SILK_TOUCH_OR_SHEARS)
                                                .addEntry(withExplosionDecay(leaves,
                                                        ItemLootEntry.builder(Items.STICK
                                                        ).acceptFunction(
                                                                SetCount.builder(RandomValueRange.of(1.0F, 2.0F))))
                                                                  .acceptCondition(TableBonus.builder(
                                                                          Enchantments.FORTUNE, 0.02F, 0.022222223F,
                                                                          0.025F, 0.033333335F, 0.1F))));
        }

        protected LootTable.Builder droppingAlternativeWithSilkTouchOrShears(LootEntry.Builder<?> mainDropEntry,
                                                                             LootEntry.Builder<?> silkTouchDropEntry) {
            return this.droppingAlternative(mainDropEntry, SILK_TOUCH_OR_SHEARS, silkTouchDropEntry);
        }

        protected LootTable.Builder droppingAlternative(LootEntry.Builder<?> mainDropEntry,
                                                        ILootCondition.IBuilder condition,
                                                        LootEntry.Builder<?> alternativeDropEntry) {
            return LootTable.builder().addLootPool(LootPool.builder().rolls(ConstantRange.of(1))
                                                           .addEntry((mainDropEntry.acceptCondition(condition))
                                                                             .alternatively(alternativeDropEntry)));
        }
        //endregion Methods
    }
}
