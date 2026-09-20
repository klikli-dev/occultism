package com.klikli_dev.occultism.datagen.loot;

import com.klikli_dev.occultism.Occultism;
import com.klikli_dev.occultism.common.block.crops.IReplantableCrops;
import com.klikli_dev.occultism.common.block.otherworld.IOtherworldBlock;
import com.klikli_dev.occultism.registry.OccultismBlocks;
import com.klikli_dev.occultism.registry.OccultismBlocks.BlockDataGenSettings;
import com.klikli_dev.occultism.registry.OccultismBlocks.LootTableType;
import com.klikli_dev.occultism.registry.OccultismDataComponents;
import com.klikli_dev.occultism.registry.OccultismItems;
import net.minecraft.advancements.predicates.StatePropertiesPredicate.Builder;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.CopyComponentsFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.AnyOfCondition;
import net.minecraft.world.level.storage.loot.predicates.BonusLevelTableCondition;
import net.minecraft.world.level.storage.loot.predicates.MatchBlock;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.providers.number.floats.ContextFloatProviders;
import net.minecraft.world.level.storage.loot.providers.number.ints.ContextIntProviders;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class OccultismBlockLoot extends BlockLootSubProvider {
    private final Map<ResourceKey<LootTable>, LootTable.Builder> tables = new HashMap<>();

    //x2 vanilla rate
    protected static final float[] DEFAULT_SAPLING_DROP_RATES = new float[]{0.05F, 0.0625F, 0.083333336F, 0.1F};
    protected static final float[] INCREASED_SAPLING_DROP_RATES = new float[]{0.1F, 0.2F, 0.3F, 0.4F};

    public OccultismBlockLoot(LootTableSubProvider.Context context) {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags(), context);
    }

    @Override
    public void run() {
        this.generate();
        this.tables.forEach(this.output::accept);
    }

    @Override
    protected void add(Block block, LootTable.Builder builder) {
        super.add(block, builder);
        this.tables.put(block.getLootTable().orElseThrow(), builder);
    }

    protected void generate() {
        OccultismBlocks.BLOCKS.getEntries().stream()
                .map(DeferredHolder::get)
                .forEach(block -> {
                    BlockDataGenSettings settings = OccultismBlocks.BLOCK_DATA_GEN_SETTINGS
                            .get(BuiltInRegistries.BLOCK.getKey(block));
                    if (settings == null) {
                        Occultism.LOGGER.warn("No block data-gen settings for block {}. Skipping loot table generation.",
                                BuiltInRegistries.BLOCK.getKey(block));
                        return;
                    }

                    if (settings.lootTableType == LootTableType.EMPTY)
                        this.registerDropNothingLootTable(block);
                    else if (settings.lootTableType == LootTableType.REPLANTABLE_CROP) {
                        IReplantableCrops cropsBlock = (IReplantableCrops) block;
                        LootItemCondition.Builder lootCondition =
                                MatchBlock.blockMatches(this.blocks, block, 
                                        Builder.properties()
                                                .hasProperty(CropBlock.AGE, 7));
                        this.add(block,
                                this.createCropDrops(block, cropsBlock.getCropsItem().asItem(),
                                        cropsBlock.getSeedsItem().asItem(), lootCondition));
                    } else if (settings.lootTableType == LootTableType.DROP_SELF) {
                        if (block.asItem() != Items.AIR) {
                            this.dropSelf(block);
                        } else {
                            Occultism.LOGGER.warn("Block {} has DROP_SELF loot type but its item is AIR. Skipping.",
                                    BuiltInRegistries.BLOCK.getKey(block));
                        }
                    } else if (settings.lootTableType == LootTableType.OTHERWORLD_BLOCK)
                        this.registerOtherworldBlockTable(block);
                });

        this.add(OccultismBlocks.OTHERWORLD_LEAVES.get(),
                (block) -> this.createLeavesDrops(block, OccultismBlocks.OTHERWORLD_SAPLING.get(), DEFAULT_SAPLING_DROP_RATES)
                        .withPool(LootPool.lootPool().setRolls(ContextIntProviders.exactly(1))
                                .when(this.doesNotHaveShearsOrSilkTouch())
                                .add((this.applyExplosionCondition(block, LootItem.lootTableItem(OccultismItems.PITAYA.asItem())))
                                        .when(BonusLevelTableCondition.bonusLevelFlatChance(this.enchantments.getOrThrow(Enchantments.FORTUNE),
                                                INCREASED_SAPLING_DROP_RATES)))));

        this.add(OccultismBlocks.OTHERWORLD_LEAVES_NATURAL.get(),
                (block) -> this.createOtherworldLeavesDrops(block, Blocks.OAK_SAPLING, OccultismBlocks.OTHERWORLD_SAPLING_NATURAL.get(),
                        Items.APPLE, OccultismItems.PITAYA.asItem(), INCREASED_SAPLING_DROP_RATES));

        this.add(OccultismBlocks.OTHERGLASS_NATURAL.get(),
                (block) -> this.createOtherworldBlockTable(block, OccultismItems.CRUSHED_END_STONE.get(), block));

        this.add(OccultismBlocks.SILVER_ORE.get(), this.createOreDrop(OccultismBlocks.SILVER_ORE.get(), OccultismItems.RAW_SILVER.get()));
        this.add(OccultismBlocks.SILVER_ORE_DEEPSLATE.get(), this.createOreDrop(OccultismBlocks.SILVER_ORE_DEEPSLATE.get(), OccultismItems.RAW_SILVER.get()));
        this.add(OccultismBlocks.IESNIUM_ORE.get(), this.createOreDrop(OccultismBlocks.IESNIUM_ORE.get(), OccultismItems.RAW_IESNIUM.get()));
        this.add(OccultismBlocks.IESNIUM_ORE_NATURAL.get(),
                (block) -> this.createCoveredOreDrop(block, OccultismItems.RAW_IESNIUM.get()));

        this.dropSelfWithComponents(OccultismBlocks.STORAGE_CONTROLLER.get(),
                OccultismDataComponents.STORAGE_CONTROLLER_CONTENTS.get(),
                OccultismDataComponents.SORT_DIRECTION.get(),
                OccultismDataComponents.SORT_TYPE.get(),
                OccultismDataComponents.CRAFTING_MATRIX.get(),
                OccultismDataComponents.ORDER_STACK.get()
        );
        this.dropSelfWithComponents(OccultismBlocks.STORAGE_CONTROLLER_STABILIZED.get(),
                OccultismDataComponents.STORAGE_CONTROLLER_CONTENTS.get(),
                OccultismDataComponents.SORT_DIRECTION.get(),
                OccultismDataComponents.SORT_TYPE.get(),
                OccultismDataComponents.CRAFTING_MATRIX.get(),
                OccultismDataComponents.ORDER_STACK.get()
        );
        this.dropSelfWithComponents(OccultismBlocks.STORAGE_CONTROLLER_DARK.get(),
                OccultismDataComponents.STORAGE_CONTROLLER_CONTENTS.get(),
                OccultismDataComponents.SORT_DIRECTION.get(),
                OccultismDataComponents.SORT_TYPE.get(),
                OccultismDataComponents.CRAFTING_MATRIX.get(),
                OccultismDataComponents.ORDER_STACK.get()
        );
        this.dropSelfWithComponents(OccultismBlocks.STORAGE_CONTROLLER_STABILIZED_DARK.get(),
                OccultismDataComponents.STORAGE_CONTROLLER_CONTENTS.get(),
                OccultismDataComponents.SORT_DIRECTION.get(),
                OccultismDataComponents.SORT_TYPE.get(),
                OccultismDataComponents.CRAFTING_MATRIX.get(),
                OccultismDataComponents.ORDER_STACK.get()
        );

        this.dropSelfWithComponents(OccultismBlocks.STABLE_WORMHOLE.get(),
                OccultismDataComponents.LINKED_STORAGE_CONTROLLER.get(),
                OccultismDataComponents.SORT_DIRECTION.get(),
                OccultismDataComponents.SORT_TYPE.get(),
                OccultismDataComponents.CRAFTING_MATRIX.get(),
                OccultismDataComponents.ORDER_STACK.get()
        );
        this.dropSelfWithComponents(OccultismBlocks.STABLE_WORMHOLE_DARK.get(),
                OccultismDataComponents.LINKED_STORAGE_CONTROLLER.get(),
                OccultismDataComponents.SORT_DIRECTION.get(),
                OccultismDataComponents.SORT_TYPE.get(),
                OccultismDataComponents.CRAFTING_MATRIX.get(),
                OccultismDataComponents.ORDER_STACK.get()
        );

        this.add(OccultismBlocks.OTHERSTONE_SLAB.get(), block -> this.createSlabItemTable(OccultismBlocks.OTHERSTONE_SLAB.get()));
        this.add(OccultismBlocks.OTHERCOBBLESTONE_SLAB.get(), block -> this.createSlabItemTable(OccultismBlocks.OTHERCOBBLESTONE_SLAB.get()));
        this.add(OccultismBlocks.POLISHED_OTHERSTONE_SLAB.get(), block -> this.createSlabItemTable(OccultismBlocks.POLISHED_OTHERSTONE_SLAB.get()));
        this.add(OccultismBlocks.OTHERSTONE_BRICKS_SLAB.get(), block -> this.createSlabItemTable(OccultismBlocks.OTHERSTONE_BRICKS_SLAB.get()));
        this.add(OccultismBlocks.OTHERROCK_SLAB.get(), block -> this.createSlabItemTable(OccultismBlocks.OTHERROCK_SLAB.get()));
        this.add(OccultismBlocks.OTHERCOBBLEROCK_SLAB.get(), block -> this.createSlabItemTable(OccultismBlocks.OTHERCOBBLEROCK_SLAB.get()));
        this.add(OccultismBlocks.POLISHED_OTHERROCK_SLAB.get(), block -> this.createSlabItemTable(OccultismBlocks.POLISHED_OTHERROCK_SLAB.get()));
        this.add(OccultismBlocks.OTHERROCK_BRICKS_SLAB.get(), block -> this.createSlabItemTable(OccultismBlocks.OTHERROCK_BRICKS_SLAB.get()));
        this.add(OccultismBlocks.OTHERPLANKS_SLAB.get(), block -> this.createSlabItemTable(OccultismBlocks.OTHERPLANKS_SLAB.get()));
        this.add(OccultismBlocks.OTHERPLANKS_DOOR.get(), block -> this.createDoorTable(OccultismBlocks.OTHERPLANKS_DOOR.get()));
        this.add(OccultismBlocks.OTHERPLANKS_SIGN.get(), item -> this.createSingleItemTable(OccultismItems.OTHERPLANKS_SIGN));
        this.add(OccultismBlocks.OTHERPLANKS_WALL_SIGN.get(), item -> this.createSingleItemTable(OccultismItems.OTHERPLANKS_SIGN));
        this.add(OccultismBlocks.OTHERPLANKS_HANGING_SIGN.get(), item -> this.createSingleItemTable(OccultismItems.OTHERPLANKS_HANGING_SIGN));
        this.add(OccultismBlocks.OTHERPLANKS_WALL_HANGING_SIGN.get(), item -> this.createSingleItemTable(OccultismItems.OTHERPLANKS_HANGING_SIGN));
        this.add(OccultismBlocks.POTTED_OTHERFLOWER.get(), this.createPotFlowerItemTable(OccultismBlocks.OTHERFLOWER.get()));
        this.add(OccultismBlocks.OTHERSTONE.get(), block -> this.createSingleItemTableWithSilkTouch(block, OccultismBlocks.OTHERCOBBLESTONE.asItem()));
        this.add(OccultismBlocks.OTHERROCK.get(), block -> this.createSingleItemTableWithSilkTouch(block, OccultismBlocks.OTHERCOBBLEROCK.asItem()));
        this.add(OccultismBlocks.SKELETON_SKULL_DUMMY.get(), block -> noDrop());
        this.add(OccultismBlocks.WITHER_SKELETON_SKULL_DUMMY.get(), block -> noDrop());

        this.add(OccultismBlocks.SILVER_CUT_SLAB.get(), block -> createSlabItemTable(OccultismBlocks.SILVER_CUT_SLAB.get()));
        this.add(OccultismBlocks.SILVER_DOOR.get(), block -> createDoorTable(OccultismBlocks.SILVER_DOOR.get()));

    }

    protected void registerOtherworldBlockTable(Block block) {
        if (block instanceof IOtherworldBlock)
            this.add(block, this.createOtherworldBlockTable(block));
        else
            Occultism.LOGGER.warn("Tried to register otherworld block loot table for non-otherworld block {}",
                    BuiltInRegistries.BLOCK.getKey(block));
    }

    protected LootTable.Builder createOtherworldBlockTable(Block block) {
        IOtherworldBlock otherworldBlock = (IOtherworldBlock) block;
        return this.createOtherworldBlockTable(block, otherworldBlock.getCoveredBlock(), otherworldBlock.getUncoveredBlock());
    }

    protected LootTable.Builder createOtherworldBlockTable(Block block, ItemLike coveredDrop, ItemLike uncoveredDrop) {
        LootPool.Builder builder = LootPool.lootPool()
                .setRolls(ContextIntProviders.exactly(1))
                .add(LootItem.lootTableItem(uncoveredDrop)
                        .when(this.uncoveredCondition(block))
                        .otherwise(LootItem.lootTableItem(coveredDrop))
                );
        return LootTable.lootTable().withPool(builder);
    }

    protected LootTable.Builder createOtherworldLeavesDrops(Block leavesBlock, Block coveredSapling,
                                                            Block uncoveredSapling, Item coveredFruit,
                                                            Item uncoveredFruit,
                                                            float... chances) {
        var saplingLootItem = LootItem.lootTableItem(uncoveredSapling)
                .when(this.uncoveredCondition(leavesBlock)).otherwise(LootItem.lootTableItem(coveredSapling));
        var coveredLeaves = leavesBlock instanceof IOtherworldBlock ? ((IOtherworldBlock) leavesBlock).getCoveredBlock() : Blocks.AIR;
        var fruitLootItem = LootItem.lootTableItem(uncoveredFruit)
                .when(this.uncoveredCondition(leavesBlock)).otherwise(LootItem.lootTableItem(coveredFruit));

        return LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ContextIntProviders.exactly(1))
                        .add(LootItem.lootTableItem(leavesBlock)
                                .when(this.uncoveredCondition(leavesBlock).and(this.hasShearsOrSilkTouch()))))
                .withPool(LootPool.lootPool()
                        .setRolls(ContextIntProviders.exactly(1))
                        .add(LootItem.lootTableItem(coveredLeaves)
                                .when(this.coveredCondition(leavesBlock).and(this.hasShearsOrSilkTouch()))))
                .withPool(LootPool.lootPool()
                        .setRolls(ContextIntProviders.exactly(1))
                        .when(this.doesNotHaveShearsOrSilkTouch())
                        .add(this.applyExplosionCondition(leavesBlock, saplingLootItem)
                                .when(BonusLevelTableCondition.bonusLevelFlatChance(this.enchantments.getOrThrow(Enchantments.FORTUNE), chances))))
                .withPool(LootPool.lootPool().setRolls(ContextIntProviders.exactly(1))
                        .when(this.doesNotHaveShearsOrSilkTouch())
                        .add((this.applyExplosionCondition(leavesBlock, fruitLootItem))
                                .when(BonusLevelTableCondition.bonusLevelFlatChance(this.enchantments.getOrThrow(Enchantments.FORTUNE),
                                        DEFAULT_SAPLING_DROP_RATES))))
                .withPool(LootPool.lootPool()
                        .setRolls(ContextIntProviders.exactly(1))
                        .when(this.doesNotHaveShearsOrSilkTouch())
                        .add(this.applyExplosionDecay(leavesBlock, LootItem.lootTableItem(Items.STICK)
                                        .apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 2))))
                                .when(BonusLevelTableCondition.bonusLevelFlatChance(this.enchantments.getOrThrow(Enchantments.FORTUNE), 0.02F, 0.022222223F, 0.025F, 0.033333335F, 0.1F))
                        ));
    }

    protected LootTable.Builder createCoveredOreDrop(Block block, Item item) {
        var coveredBlock = block instanceof IOtherworldBlock ? ((IOtherworldBlock) block).getCoveredBlock() : Blocks.AIR;
        var uncoveredBlock = block instanceof IOtherworldBlock ? ((IOtherworldBlock) block).getUncoveredBlock() : Blocks.AIR;

        return LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ContextIntProviders.exactly(1))
                        .add(LootItem.lootTableItem(coveredBlock))
                        .when(this.coveredCondition(block))
                )
                .withPool(LootPool.lootPool()
                        .setRolls(ContextIntProviders.exactly(1))
                        .add(LootItem.lootTableItem(uncoveredBlock)
                                .when(this.uncoveredCondition(block).and(() -> this.hasSilkTouch().value())))
                )
                .withPool(LootPool.lootPool()
                        .setRolls(ContextIntProviders.exactly(1))
                        .add(LootItem.lootTableItem(item)
                                .apply(ApplyBonusCount.addOreBonusCount(this.enchantments.getOrThrow(Enchantments.FORTUNE)))
                                .when(this.uncoveredCondition(block).and(this.doesNotHaveSilkTouch())))
                );
    }

    public void registerDropNothingLootTable(Block block) {
//       this.add(block, noDrop());
    }

    protected final void dropSelfWithComponents(Block pBlock, DataComponentType<?>... pIncludes) {
        this.dropSelfWithComponents(pBlock, this.copyComponents(pIncludes));
    }

    protected void dropSelfWithComponents(Block pBlock, CopyComponentsFunction.Builder data) {
        this.add(pBlock, this.createSelfWithComponentsDrop(pBlock, data));
    }

    protected LootTable.Builder createSelfWithComponentsDrop(Block pBlock, CopyComponentsFunction.Builder data) {
        return LootTable.lootTable()
                .withPool(
                        this.applyExplosionCondition(
                                pBlock,
                                LootPool.lootPool()
                                        .setRolls(ContextIntProviders.exactly(1))
                                        .add(
                                                LootItem.lootTableItem(pBlock)
                                                        .apply(
                                                                data
                                                        )
                                        )
                        )
                );
    }

    protected CopyComponentsFunction.Builder copyComponents(DataComponentType<?>... pIncludes) {
        var builder = CopyComponentsFunction.copyComponentsFromBlockEntity(LootContextParams.BLOCK_ENTITY);
        for (var include : pIncludes) {
            builder.include(include);
        }
        return builder;
    }

    private LootItemCondition.Builder hasShearsOrSilkTouch() {
        return new AnyOfCondition.Builder().or(this.hasShears()).or(this.hasSilkTouch());
    }

    private LootItemCondition.Builder doesNotHaveShearsOrSilkTouch() {
        return this.hasShearsOrSilkTouch().invert();
    }

    private LootItemCondition.Builder uncoveredCondition(Block block) {
        return MatchBlock.blockMatches(this.blocks, block,
                Builder.properties()
                        .hasProperty(IOtherworldBlock.UNCOVERED, true));
    }

    private LootItemCondition.Builder coveredCondition(Block block) {
        return MatchBlock.blockMatches(this.blocks, block,
                Builder.properties()
                        .hasProperty(IOtherworldBlock.UNCOVERED, false));
    }

}
