/*
 * MIT License
 *
 * Copyright 2020 klikli-dev, McJty
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
import net.minecraft.block.Blocks;
import net.minecraft.block.CropsBlock;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.loot.EntityLootTables;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Items;
import net.minecraft.loot.*;
import net.minecraft.loot.conditions.*;
import net.minecraft.loot.functions.LootingEnchantBonus;
import net.minecraft.loot.functions.SetCount;
import net.minecraftforge.fml.RegistryObject;

/**
 * Based on https://github.com/McJty/YouTubeModding14
 */
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
            this.add(OccultismEntities.POSSESSED_ENDERMITE_TYPE.get(),
                    LootTable.lootTable().withPool(
                            LootPool.lootPool().setRolls(ConstantRange.exactly(1))
                                    .add(ItemLootEntry.lootTableItem(Items.END_STONE)
                                                      .apply(SetCount.setCount(RandomValueRange.between(1.0f, 2.0F)))
                                                      .apply(LootingEnchantBonus.lootingMultiplier(
                                                              RandomValueRange.between(0.0F, 1.0F))))));

            //Guaranteed ender pearl drop for enderman
            this.add(OccultismEntities.POSSESSED_ENDERMAN_TYPE.get(),
                    LootTable.lootTable().withPool(
                            LootPool.lootPool().setRolls(ConstantRange.exactly(1))
                                    .add(ItemLootEntry.lootTableItem(Items.ENDER_PEARL)
                                                      .apply(SetCount.setCount(RandomValueRange.between(1.0f, 3.0F)))
                                                      .apply(LootingEnchantBonus.lootingMultiplier(
                                                              RandomValueRange.between(0.0F, 1.0F))))));

            //Guaranteed skeleton skull drop for skeleton
            this.add(OccultismEntities.POSSESSED_SKELETON_TYPE.get(),
                    LootTable.lootTable()
                            .withPool(LootPool.lootPool().setRolls(ConstantRange.exactly(1)).add(
                                    ItemLootEntry.lootTableItem(Items.ARROW)
                                            .apply(SetCount.setCount(RandomValueRange.between(0.0F, 2.0F)))
                                            .apply(
                                                    LootingEnchantBonus.lootingMultiplier(RandomValueRange.between(0.0F, 1.0F)))))
                            .withPool(LootPool.lootPool().setRolls(ConstantRange.exactly(1)).add(
                                    ItemLootEntry.lootTableItem(Items.SKELETON_SKULL)
                                            .apply(SetCount.setCount(RandomValueRange.between(1.0F, 1.0F)))
                                            .apply(
                                                    LootingEnchantBonus.lootingMultiplier(RandomValueRange.between(0.0F, 1.0F)))))
                            .withPool(LootPool.lootPool().setRolls(ConstantRange.exactly(1)).add(
                                    ItemLootEntry.lootTableItem(Items.BONE)
                                            .apply(SetCount.setCount(RandomValueRange.between(0.0F, 2.0F)))
                                            .apply(
                                                    LootingEnchantBonus.lootingMultiplier(RandomValueRange.between(0.0F, 1.0F))))));

            //Essence drop from wild afrit
            this.add(OccultismEntities.AFRIT_WILD_TYPE.get(),
                    LootTable.lootTable().withPool(
                            LootPool.lootPool().setRolls(ConstantRange.exactly(1))
                                    .add(ItemLootEntry.lootTableItem(OccultismItems.AFRIT_ESSENCE.get())
                                                      .apply(SetCount.setCount(RandomValueRange.between(0.7f, 1.0F)))
                                                      .apply(LootingEnchantBonus.lootingMultiplier(
                                                              RandomValueRange.between(0.0F, 1.0F))))));

            //increased wither skull drop from wild hunt
            this.add(OccultismEntities.WILD_HUNT_WITHER_SKELETON_TYPE.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(ConstantRange.exactly(1))
                     .add(ItemLootEntry.lootTableItem(Items.COAL)
                                       .apply(SetCount.setCount(RandomValueRange.between(-1.0F, 1.0F)))
                                       .apply(LootingEnchantBonus.lootingMultiplier(RandomValueRange.between(0.0F, 1.0F))))
                ).withPool(LootPool.lootPool().setRolls(ConstantRange.exactly(1))
                      .add(ItemLootEntry.lootTableItem(Items.BONE)
                                        .apply(SetCount.setCount(RandomValueRange.between(0.0F, 2.0F)))
                                        .apply(LootingEnchantBonus.lootingMultiplier(RandomValueRange.between(0.0F, 1.0F))))
                ).withPool(LootPool.lootPool().setRolls(ConstantRange.exactly(1))
                       .add(ItemLootEntry.lootTableItem(Blocks.WITHER_SKELETON_SKULL))
                                       .when(KilledByPlayer.killedByPlayer())
                                           .apply(SetCount.setCount(RandomValueRange.between(1f, 1.0F)))
                                           .apply(LootingEnchantBonus.lootingMultiplier(RandomValueRange.between(0.0F, 1.0F)))));

            //normal drop from wild hunt skeletons
            this.add(OccultismEntities.WILD_HUNT_SKELETON_TYPE.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(ConstantRange.exactly(1))
                     .add(ItemLootEntry.lootTableItem(Items.ARROW)
                                       .apply(SetCount.setCount(RandomValueRange.between(0.0F, 2.0F)))
                                       .apply(LootingEnchantBonus.lootingMultiplier(RandomValueRange.between(0.0F, 1.0F))))
                ).withPool(LootPool.lootPool().setRolls(ConstantRange.exactly(1))
                      .add(ItemLootEntry.lootTableItem(Items.BONE)
                                        .apply(SetCount.setCount(RandomValueRange.between(0.0F, 2.0F)))
                                        .apply(LootingEnchantBonus.lootingMultiplier(RandomValueRange.between(0.0F, 1.0F))))));

        }

        @Override
        protected void add(EntityType<?> type, LootTable.Builder table) {
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
                                    BlockStateProperty.hasBlockStateProperties(block).setProperties(
                                            StatePropertiesPredicate.Builder.properties()
                                                    .hasProperty(CropsBlock.AGE, 7));
                            this.add(block,
                                    createCropDrops(block, cropsBlock.getCropsItem().asItem(),
                                            cropsBlock.getSeedsItem().asItem(), lootCondition));
                        }
                        else if (settings.lootTableType == OccultismBlocks.LootTableType.DROP_SELF)
                            this.dropSelf(block);
                        else if (settings.lootTableType == OccultismBlocks.LootTableType.OTHERWORLD_BLOCK)
                            this.registerOtherworldBlockTable(block);
                    });

            this.add(OccultismBlocks.OTHERWORLD_LEAVES.get(),
                    (block) -> createLeavesDrops(block, OccultismBlocks.OTHERWORLD_SAPLING.get(),
                            DEFAULT_SAPLING_DROP_RATES));
        }

        @Override
        protected void add(Block blockIn, LootTable.Builder table) {
            StandardLootTableProvider.this.blockLootTable.put(blockIn, table);
        }
        //endregion Overrides

        //region Methods
        protected void registerOtherworldBlockTable(Block block) {
            if (block instanceof IOtherworldBlock)
                this.add(block, this.createOtherworldBlockTable(block));
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
                this.add(block,
                        this.otherWorldLeavesDroppingWithChancesAndSticks(block, coveredSapling, uncoveredSapling,
                                chances));
            else
                Occultism.LOGGER.warn("Tried to register otherworld leaves loot table for non-otherworld block {}",
                        block.getRegistryName());
        }

        protected LootTable.Builder createOtherworldBlockTable(Block block) {
            IOtherworldBlock otherworldBlock = (IOtherworldBlock) block;
            ILootCondition.IBuilder uncoveredCondition =
                    BlockStateProperty.hasBlockStateProperties(block).setProperties(
                            StatePropertiesPredicate.Builder.properties()
                                    .hasProperty(IOtherworldBlock.UNCOVERED, true));
            LootPool.Builder builder = LootPool.lootPool()
                                               .setRolls(ConstantRange.exactly(1))
                                               .add(ItemLootEntry.lootTableItem(otherworldBlock.getUncoveredBlock())
                                                                 .when(uncoveredCondition)
                                                                 .otherwise(ItemLootEntry.lootTableItem(
                                                                         otherworldBlock.getCoveredBlock()))
                                               );
            return LootTable.lootTable().withPool(builder);
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
                    BlockStateProperty.hasBlockStateProperties(forBlock).setProperties(
                            StatePropertiesPredicate.Builder.properties()
                                    .hasProperty(IOtherworldBlock.UNCOVERED, true));


            return this.droppingAlternativeWithChancesAndSticks(forBlock,
                    //Leaves entry
                    ItemLootEntry.lootTableItem(otherworldBlock.getUncoveredBlock())
                            .when(uncoveredCondition)
                            .otherwise(ItemLootEntry.lootTableItem(
                                    otherworldBlock.getCoveredBlock())),
                    //Sapling entry
                    ItemLootEntry.lootTableItem(uncoveredSapling)
                            .when(uncoveredCondition)
                            .otherwise(ItemLootEntry.lootTableItem(coveredSapling)), chances);
        }

        protected LootTable.Builder droppingAlternativeWithChancesAndSticks(Block leaves,
                                                                            LootEntry.Builder<?> leavesEntry,
                                                                            LootEntry.Builder<?> saplingEntry,
                                                                            float... chances) {
            return this.droppingAlternativeWithSilkTouchOrShears(leavesEntry,
                    applyExplosionCondition(leaves, saplingEntry)
                            .when(TableBonus.bonusLevelFlatChance(Enchantments.BLOCK_FORTUNE, chances)))
                           .withPool(LootPool.lootPool().setRolls(ConstantRange.exactly(1))
                                                .when(NOT_SILK_TOUCH_OR_SHEARS)
                                                .add(applyExplosionDecay(leaves,
                                                        ItemLootEntry.lootTableItem(Items.STICK
                                                        ).apply(
                                                                SetCount.setCount(RandomValueRange.between(1.0F, 2.0F))))
                                                                  .when(TableBonus.bonusLevelFlatChance(
                                                                          Enchantments.BLOCK_FORTUNE, 0.02F, 0.022222223F,
                                                                          0.025F, 0.033333335F, 0.1F))));
        }

        protected LootTable.Builder droppingAlternativeWithSilkTouchOrShears(LootEntry.Builder<?> mainDropEntry,
                                                                             LootEntry.Builder<?> silkTouchDropEntry) {
            return this.droppingAlternative(mainDropEntry, SILK_TOUCH_OR_SHEARS, silkTouchDropEntry);
        }

        protected LootTable.Builder droppingAlternative(LootEntry.Builder<?> mainDropEntry,
                                                        ILootCondition.IBuilder condition,
                                                        LootEntry.Builder<?> alternativeDropEntry) {
            return LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantRange.exactly(1))
                                                           .add((mainDropEntry.when(condition))
                                                                             .otherwise(alternativeDropEntry)));
        }
        //endregion Methods
    }
}
