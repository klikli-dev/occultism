package com.klikli_dev.occultism.datagen.loot;

import com.klikli_dev.occultism.registry.OccultismEntities;
import com.klikli_dev.occultism.registry.OccultismItems;
import net.minecraft.data.loot.EntityLootSubProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.EmptyLootItem;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootTableReference;
import net.minecraft.world.level.storage.loot.functions.LootingEnchantFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.functions.SmeltItemFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemEntityPropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemKilledByPlayerCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceWithLootingCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

import java.util.function.BiConsumer;

public class OccultismEntityLoot extends EntityLootSubProvider {
    public OccultismEntityLoot() {
        super(FeatureFlags.REGISTRY.allFlags());
    }

    @Override
    public void generate(BiConsumer<ResourceLocation, LootTable.Builder> consumer) {
        this.generate();
        this.map.forEach((key, entityType) -> {
            entityType.forEach(consumer::accept);
        });
    }

    @Override
    public void generate() {
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

        //Guaranteed phantom membrane drop for phantom
        this.add(OccultismEntities.POSSESSED_PHANTOM_TYPE.get(),
                LootTable.lootTable().withPool(
                        LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                                .add(LootItem.lootTableItem(Items.PHANTOM_MEMBRANE)
                                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0f, 4.0F)))
                                        .apply(LootingEnchantFunction.lootingMultiplier(
                                                UniformGenerator.between(0.0F, 1.0F))))));

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
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.67f, 1.0F)))
                        .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0F, 1.0F)))
                ).withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(Items.RIB_ARMOR_TRIM_SMITHING_TEMPLATE))
                        .when(LootItemKilledByPlayerCondition.killedByPlayer())
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.1f, 1.0F)))));

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

        this.add(OccultismEntities.POSSESSED_ELDER_GUARDIAN_TYPE.get(),
                LootTable.lootTable().withPool(
                                LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                                        .add(LootItem.lootTableItem(Items.PRISMARINE_SHARD)
                                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0f, 2.0F), false))
                                                .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0, 1)))
                                        )
                        ).withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                                .add(LootItem.lootTableItem(Items.COD)
                                        .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0, 1)))
                                        .apply(SmeltItemFunction.smelted()
                                                .when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, ENTITY_ON_FIRE)))
                                        .setWeight(3)

                                ).add(LootItem.lootTableItem(Items.PRISMARINE_CRYSTALS)
                                        .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0, 1))

                                        ).setWeight(2)
                                ).add(EmptyLootItem.emptyItem())
                        ).withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                                .add(LootItem.lootTableItem(Items.WET_SPONGE)
                                ).when(LootItemKilledByPlayerCondition.killedByPlayer())
                        )
                        .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                                .add(LootTableReference.lootTableReference(BuiltInLootTables.FISHING_FISH)
                                        .apply(SmeltItemFunction.smelted()
                                                .when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, ENTITY_ON_FIRE)))

                                ).when(LootItemKilledByPlayerCondition.killedByPlayer())
                                .when(LootItemRandomChanceWithLootingCondition.randomChanceAndLootingBoost(0.025f, 0.01f))
                        )
                        .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                                .add(EmptyLootItem.emptyItem().setWeight(8))
                                .add(LootItem.lootTableItem(Items.TIDE_ARMOR_TRIM_SMITHING_TEMPLATE))
                                .add(LootItem.lootTableItem(Items.COAST_ARMOR_TRIM_SMITHING_TEMPLATE))
                        )
                        .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                                .add(LootItem.lootTableItem(Items.NAUTILUS_SHELL)
                                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0f, 4.0F)))
                                )
                        )
                        .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                                .add(LootItem.lootTableItem(Items.HEART_OF_THE_SEA)
                                        .when(LootItemRandomChanceWithLootingCondition.randomChanceAndLootingBoost(0.4f, 0.01f))
                                )
                        )
        );

        this.add(OccultismEntities.POSSESSED_GHAST_TYPE.get(),
                LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(Items.GHAST_TEAR)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0f, 3.0F)))
                                .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0F, 1.0F)))
                        )
                ).withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(Items.GUNPOWDER)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0f, 4.0F)))
                                .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0F, 1.0F)))
                        )
                )
        );
        this.add(OccultismEntities.POSSESSED_HOGLIN_TYPE.get(),
                LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                        .add(EmptyLootItem.emptyItem().setWeight(3))
                        .add(LootItem.lootTableItem(Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE).setWeight(2))
                        .add(LootItem.lootTableItem(Items.SNOUT_ARMOR_TRIM_SMITHING_TEMPLATE).setWeight(1))
                        .add(LootItem.lootTableItem(Items.NETHERITE_SCRAP).setWeight(3))
                )
        );
        this.add(OccultismEntities.POSSESSED_SHULKER_TYPE.get(),
                LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                                .add(LootItem.lootTableItem(Items.SHULKER_SHELL)
                                )
                                .when(LootItemRandomChanceWithLootingCondition.randomChanceAndLootingBoost(0.25f, 0.1f))

                        ).withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                                .add(LootItem.lootTableItem(Items.SHULKER_SHELL))
                        )
                        .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                                .add(LootItem.lootTableItem(Items.SPIRE_ARMOR_TRIM_SMITHING_TEMPLATE)
                                        .when(LootItemRandomChanceWithLootingCondition.randomChanceAndLootingBoost(0.1f, 0.1f))
                                )
                        )
        );

        this.add(OccultismEntities.POSSESSED_WARDEN_TYPE.get(),
                LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(Items.ECHO_SHARD)
                                .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0F, 3.0F)))
                        )

                ).withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                        .add(EmptyLootItem.emptyItem().setWeight(8))
                        .add(LootItem.lootTableItem(Items.SILENCE_ARMOR_TRIM_SMITHING_TEMPLATE))
                        .add(LootItem.lootTableItem(Items.WARD_ARMOR_TRIM_SMITHING_TEMPLATE))
                ).withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                        .add(EmptyLootItem.emptyItem().setWeight(10))
                        .add(LootItem.lootTableItem(Items.DISC_FRAGMENT_5).setWeight(9))
                        .add(LootItem.lootTableItem(Items.MUSIC_DISC_OTHERSIDE))
                )
        );
        this.add(OccultismEntities.POSSESSED_WEAK_SHULKER_TYPE.get(),
                LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                                .add(LootItem.lootTableItem(Items.SHULKER_SHELL)
                                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0f, 1.0F))
                                        )
                                        .when(LootItemRandomChanceCondition.randomChance(0.1f)
                                        )
                                ).apply(LootingEnchantFunction.lootingMultiplier(ConstantValue.exactly(1)).setLimit(1).when(LootItemRandomChanceCondition.randomChance(0.1f)))
                        )
                        .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                                .add(LootItem.lootTableItem(Items.CHORUS_FRUIT)
                                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0f, 3.0F))
                                        )
                                )
                                .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(1, 3)).setLimit(6))
                        )
        );
    }


}
