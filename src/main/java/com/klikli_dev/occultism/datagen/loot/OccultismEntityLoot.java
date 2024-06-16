package com.klikli_dev.occultism.datagen.loot;

import com.klikli_dev.occultism.registry.OccultismEntities;
import com.klikli_dev.occultism.registry.OccultismItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.EntityLootSubProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.EmptyLootItem;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.NestedLootTable;
import net.minecraft.world.level.storage.loot.functions.EnchantedCountIncreaseFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.functions.SmeltItemFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemKilledByPlayerCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceWithEnchantedBonusCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

import java.util.function.BiConsumer;

public class OccultismEntityLoot extends EntityLootSubProvider {
    public OccultismEntityLoot(HolderLookup.Provider pRegistries) {
        super(FeatureFlags.REGISTRY.allFlags(), pRegistries);
    }

    @Override
    public void generate(BiConsumer<ResourceKey<LootTable>, LootTable.Builder> pGenerator) {
        this.generate();
        this.map.forEach((key, entityType) -> {
            entityType.forEach(pGenerator::accept);
        });
    }


    @Override
    public void generate() {
        this.add(OccultismEntities.POSSESSED_ELDER_GUARDIAN_TYPE.get(), this.elderGuardianLootTable());
        this.add(OccultismEntities.POSSESSED_ENDERMITE_TYPE.get(),
                LootTable.lootTable().withPool(
                        LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                                .add(LootItem.lootTableItem(Items.END_STONE)
                                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0f, 2.0F)))
                                        .apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.registries, UniformGenerator.between(0.0F, 1.0F))))));

        //Guaranteed ender pearl drop for enderman
        this.add(OccultismEntities.POSSESSED_ENDERMAN_TYPE.get(),
                LootTable.lootTable().withPool(
                                LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                                        .add(LootItem.lootTableItem(Items.ENDER_PEARL)
                                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0f, 3.0F)))
                                                .apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.registries,
                                                        UniformGenerator.between(0.0F, 1.0F)))))
                        .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                                .add(LootItem.lootTableItem(Items.EYE_ARMOR_TRIM_SMITHING_TEMPLATE)
                                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.1f, 1.0F))))));

        //Guaranteed skeleton skull drop for skeleton
        this.add(OccultismEntities.POSSESSED_SKELETON_TYPE.get(),
                LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1)).add(
                                LootItem.lootTableItem(Items.ARROW)
                                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 2.0F)))
                                        .apply(
                                                EnchantedCountIncreaseFunction.lootingMultiplier(this.registries, UniformGenerator.between(0.0F, 1.0F)))))
                        .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1)).add(
                                LootItem.lootTableItem(Items.SKELETON_SKULL)
                                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 1.0F)))
                                        .apply(
                                                EnchantedCountIncreaseFunction.lootingMultiplier(this.registries, UniformGenerator.between(0.0F, 1.0F)))))
                        .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1)).add(
                                LootItem.lootTableItem(Items.BONE)
                                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 2.0F)))
                                        .apply(
                                                EnchantedCountIncreaseFunction.lootingMultiplier(this.registries, UniformGenerator.between(0.0F, 1.0F))))));

        //Guaranteed phantom membrane drop for phantom
        this.add(OccultismEntities.POSSESSED_PHANTOM_TYPE.get(),
                LootTable.lootTable().withPool(
                        LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                                .add(LootItem.lootTableItem(Items.PHANTOM_MEMBRANE)
                                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0f, 4.0F)))
                                        .apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.registries, UniformGenerator.between(0.0F, 1.0F))))));

        //Essence drop from wild afrit
        this.add(OccultismEntities.AFRIT_WILD_TYPE.get(),
                LootTable.lootTable().withPool(
                        LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                                .add(LootItem.lootTableItem(OccultismItems.AFRIT_ESSENCE.get())
                                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.7f, 1.0F)))
                                        .apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.registries, UniformGenerator.between(0.0F, 1.0F))))));

        //increased wither skull drop from wild hunt
        this.add(OccultismEntities.WILD_HUNT_WITHER_SKELETON_TYPE.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(Items.COAL)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(-1.0F, 1.0F)))
                                .apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.registries, UniformGenerator.between(0.0F, 1.0F))))
                ).withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(Items.BONE)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 2.0F)))
                                .apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.registries, UniformGenerator.between(0.0F, 1.0F))))
                ).withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(Blocks.WITHER_SKELETON_SKULL))
                        .when(LootItemKilledByPlayerCondition.killedByPlayer())
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.67f, 1.0F)))
                        .apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.registries, UniformGenerator.between(0.0F, 1.0F)))
                ).withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(Items.RIB_ARMOR_TRIM_SMITHING_TEMPLATE))
                        .when(LootItemKilledByPlayerCondition.killedByPlayer())
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.1f, 1.0F)))));

        //normal drop from wild hunt skeletons
        this.add(OccultismEntities.WILD_HUNT_SKELETON_TYPE.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(Items.ARROW)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 2.0F)))
                                .apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.registries, UniformGenerator.between(0.0F, 1.0F))))
                ).withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(Items.BONE)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 2.0F)))
                                .apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.registries, UniformGenerator.between(0.0F, 1.0F))))));
    }

    /**
     * Copied from VanillaEntityLoot and modified
     *
     * @return
     */
    public LootTable.Builder elderGuardianLootTable() {
        return LootTable.lootTable()
                .withPool(
                        LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(
                                        LootItem.lootTableItem(Items.PRISMARINE_SHARD)
                                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 2.0F)))
                                                .apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.registries, UniformGenerator.between(0.0F, 1.0F)))
                                )
                )
                .withPool(
                        LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(
                                        LootItem.lootTableItem(Items.COD)
                                                .setWeight(3)
                                                .apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.registries, UniformGenerator.between(0.0F, 1.0F)))
                                                .apply(
                                                        SmeltItemFunction.smelted().when(this.shouldSmeltLoot())
                                                )
                                )
                                .add(
                                        LootItem.lootTableItem(Items.PRISMARINE_CRYSTALS)
                                                .setWeight(2)
                                                .apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.registries, UniformGenerator.between(0.0F, 1.0F)))
                                )
                                .add(EmptyLootItem.emptyItem())
                )
                .withPool(
                        LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(LootItem.lootTableItem(Blocks.WET_SPONGE))
                                .when(LootItemKilledByPlayerCondition.killedByPlayer())
                )
                .withPool(
                        LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(
                                        NestedLootTable.lootTableReference(BuiltInLootTables.FISHING_FISH)
                                                .apply(
                                                        SmeltItemFunction.smelted().when(this.shouldSmeltLoot())
                                                )
                                )
                                .when(LootItemKilledByPlayerCondition.killedByPlayer())
                                .when(LootItemRandomChanceWithEnchantedBonusCondition.randomChanceAndLootingBoost(this.registries, 0.025F, 0.01F))
                )

                .withPool(
                        LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(EmptyLootItem.emptyItem().setWeight(8))
                                .add(LootItem.lootTableItem(Items.TIDE_ARMOR_TRIM_SMITHING_TEMPLATE).setWeight(1))
                                .add(LootItem.lootTableItem(Items.COAST_ARMOR_TRIM_SMITHING_TEMPLATE).setWeight(1))
                )
                .withPool(
                        LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(
                                        LootItem.lootTableItem(Items.NAUTILUS_SHELL)
                                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 4.0F))
                                                )
                                )
                )
                .withPool(
                        LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(
                                        LootItem.lootTableItem(Items.HEART_OF_THE_SEA)
                                                .when(LootItemRandomChanceWithEnchantedBonusCondition.randomChanceAndLootingBoost(this.registries, 0.4F, 0.1F))
                                )
                );
    }
}
