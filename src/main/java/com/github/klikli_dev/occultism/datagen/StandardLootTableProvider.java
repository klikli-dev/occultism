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
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.loot.EntityLoot;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.LootingEnchantFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.BonusLevelTableCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemKilledByPlayerCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.minecraftforge.registries.RegistryObject;

/**
 * Based on https://github.com/McJty/YouTubeModding14
 */
public class StandardLootTableProvider extends BaseLootTableProvider {

    //Copied from BlockLootTables
    protected static final float[] INCREASED_SAPLING_DROP_RATES = new float[]{0.1F, 0.2F, 0.3F, 0.4F};

    InternalBlockLootTable blockLoot = new InternalBlockLootTable();
    InternalEntityLootTable entityLoot = new InternalEntityLootTable();

    public StandardLootTableProvider(DataGenerator dataGeneratorIn) {
        super(dataGeneratorIn);
    }

    @Override
    protected void addTables() {
        this.blockLoot.addTables();
        this.entityLoot.addTables();
    }

    private class InternalEntityLootTable extends EntityLoot {
        //region Overrides
        @Override
        protected void addTables() {
            //Guaranteed end stone drop for endermite
            this.add(OccultismEntities.POSSESSED_ENDERMITE_TYPE.get(),
                    LootTable.lootTable().withPool(
                            LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                                    .add(LootItem.lootTableItem(Items.END_STONE)
                                            .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0f, 2.0F)))
                                            .apply(LootingEnchantFunction.lootingMultiplier(
                                                    UniformGenerator.between(0.0F, 1.0F))))));

            //Guaranteed ender pearl drop for enderman
            this.add(OccultismEntities.POSSESSED_ENDERMAN_TYPE.get(),
                    LootTable.lootTable().withPool(
                            LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                                    .add(LootItem.lootTableItem(Items.ENDER_PEARL)
                                            .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0f, 3.0F)))
                                            .apply(LootingEnchantFunction.lootingMultiplier(
                                                    UniformGenerator.between(0.0F, 1.0F))))));

            //Guaranteed skeleton skull drop for skeleton
            this.add(OccultismEntities.POSSESSED_SKELETON_TYPE.get(),
                    LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1)).add(
                                    LootItem.lootTableItem(Items.ARROW)
                                            .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 2.0F)))
                                            .apply(
                                                    LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0F, 1.0F)))))
                            .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1)).add(
                                    LootItem.lootTableItem(Items.SKELETON_SKULL)
                                            .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 1.0F)))
                                            .apply(
                                                    LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0F, 1.0F)))))
                            .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1)).add(
                                    LootItem.lootTableItem(Items.BONE)
                                            .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 2.0F)))
                                            .apply(
                                                    LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0F, 1.0F))))));

            //Essence drop from wild afrit
            this.add(OccultismEntities.AFRIT_WILD_TYPE.get(),
                    LootTable.lootTable().withPool(
                            LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                                    .add(LootItem.lootTableItem(OccultismItems.AFRIT_ESSENCE.get())
                                            .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.7f, 1.0F)))
                                            .apply(LootingEnchantFunction.lootingMultiplier(
                                                    UniformGenerator.between(0.0F, 1.0F))))));

            //increased wither skull drop from wild hunt
            this.add(OccultismEntities.WILD_HUNT_WITHER_SKELETON_TYPE.get(), LootTable.lootTable()
                    .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                            .add(LootItem.lootTableItem(Items.COAL)
                                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(-1.0F, 1.0F)))
                                    .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0F, 1.0F))))
                    ).withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                            .add(LootItem.lootTableItem(Items.BONE)
                                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 2.0F)))
                                    .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0F, 1.0F))))
                    ).withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                            .add(LootItem.lootTableItem(Blocks.WITHER_SKELETON_SKULL))
                            .when(LootItemKilledByPlayerCondition.killedByPlayer())
                            .apply(SetItemCountFunction.setCount(UniformGenerator.between(1f, 1.0F)))
                            .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0F, 1.0F)))));

            //normal drop from wild hunt skeletons
            this.add(OccultismEntities.WILD_HUNT_SKELETON_TYPE.get(), LootTable.lootTable()
                    .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                            .add(LootItem.lootTableItem(Items.ARROW)
                                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 2.0F)))
                                    .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0F, 1.0F))))
                    ).withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                            .add(LootItem.lootTableItem(Items.BONE)
                                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 2.0F)))
                                    .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0F, 1.0F))))));

        }

        @Override
        protected void add(EntityType<?> type, LootTable.Builder table) {
            StandardLootTableProvider.this.entityLootTable.put(type, table);
        }
        //endregion Overrides
    }

    private class InternalBlockLootTable extends StandardBlockLootTables {
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
                            LootItemCondition.Builder lootCondition =
                                    LootItemBlockStatePropertyCondition.hasBlockStateProperties(block).setProperties(
                                            StatePropertiesPredicate.Builder.properties()
                                                    .hasProperty(CropBlock.AGE, 7));
                            this.add(block,
                                    createCropDrops(block, cropsBlock.getCropsItem().asItem(),
                                            cropsBlock.getSeedsItem().asItem(), lootCondition));
                        } else if (settings.lootTableType == OccultismBlocks.LootTableType.DROP_SELF)
                            this.dropSelf(block);
                        else if (settings.lootTableType == OccultismBlocks.LootTableType.OTHERWORLD_BLOCK)
                            this.registerOtherworldBlockTable(block);
                    });

            this.add(OccultismBlocks.OTHERWORLD_LEAVES.get(),
                    (block) -> createLeavesDrops(block, OccultismBlocks.OTHERWORLD_SAPLING.get(),
                            DEFAULT_SAPLING_DROP_RATES));

            this.add(OccultismBlocks.OTHERWORLD_LEAVES_NATURAL.get(),
                    (block) -> this.createOtherworldLeavesDrops(block, Blocks.OAK_SAPLING, OccultismBlocks.OTHERWORLD_SAPLING_NATURAL.get(),
                            INCREASED_SAPLING_DROP_RATES));

            this.add(OccultismBlocks.SILVER_ORE.get(), createOreDrop(OccultismBlocks.SILVER_ORE.get(), OccultismItems.RAW_SILVER.get()));
            this.add(OccultismBlocks.SILVER_ORE_DEEPSLATE.get(), createOreDrop(OccultismBlocks.SILVER_ORE_DEEPSLATE.get(), OccultismItems.RAW_SILVER.get()));
            this.add(OccultismBlocks.IESNIUM_ORE.get(), createOreDrop(OccultismBlocks.IESNIUM_ORE.get(), OccultismItems.RAW_IESNIUM.get()));
        }

        @Override
        protected void add(Block blockIn, LootTable.Builder table) {
            StandardLootTableProvider.this.blockLootTable.put(blockIn, table);
        }

        protected void registerOtherworldBlockTable(Block block) {
            if (block instanceof IOtherworldBlock)
                this.add(block, this.createOtherworldBlockTable(block));
            else
                Occultism.LOGGER.warn("Tried to register otherworld block loot table for non-otherworld block {}",
                        block.getRegistryName());
        }

        protected LootTable.Builder createOtherworldBlockTable(Block block) {
            IOtherworldBlock otherworldBlock = (IOtherworldBlock) block;
            return this.createOtherworldBlockTable(block, otherworldBlock.getCoveredBlock(), otherworldBlock.getUncoveredBlock());
        }

        protected LootTable.Builder createOtherworldBlockTable(Block block, ItemLike coveredDrop, ItemLike uncoveredDrop) {
            LootItemCondition.Builder uncoveredCondition =
                    LootItemBlockStatePropertyCondition.hasBlockStateProperties(block).setProperties(
                            StatePropertiesPredicate.Builder.properties()
                                    .hasProperty(IOtherworldBlock.UNCOVERED, true));
            LootPool.Builder builder = LootPool.lootPool()
                    .setRolls(ConstantValue.exactly(1))
                    .add(LootItem.lootTableItem(uncoveredDrop)
                            .when(uncoveredCondition)
                            .otherwise(LootItem.lootTableItem(coveredDrop))
                    );
            return LootTable.lootTable().withPool(builder);
        }

        protected LootTable.Builder createOtherworldLeavesDrops(Block leavesBlock, Block coveredSapling,
                                                                Block uncoveredSapling,
                                                                float... chances) {
            LootItemCondition.Builder uncoveredCondition =
                    LootItemBlockStatePropertyCondition.hasBlockStateProperties(leavesBlock).setProperties(
                            StatePropertiesPredicate.Builder.properties()
                                    .hasProperty(IOtherworldBlock.UNCOVERED, true));

            var saplingLootItem = LootItem.lootTableItem(uncoveredSapling)
                    .when(uncoveredCondition)
                    .otherwise(LootItem.lootTableItem(coveredSapling));

            return createSilkTouchOrShearsDispatchTable(leavesBlock,
                    applyExplosionCondition(leavesBlock, saplingLootItem)
                            .when(BonusLevelTableCondition.bonusLevelFlatChance(Enchantments.BLOCK_FORTUNE, chances)))
                    .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F))
                            .when(NOT_SILK_TOUCH_OR_SHEARS)
                            .add(applyExplosionDecay(leavesBlock, LootItem.lootTableItem(Items.STICK)
                                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F))))
                                    .when(BonusLevelTableCondition.bonusLevelFlatChance(Enchantments.BLOCK_FORTUNE, 0.02F, 0.022222223F, 0.025F, 0.033333335F, 0.1F))));
        }
    }
}
