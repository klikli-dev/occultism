package com.klikli_dev.occultism.datagen.loot;

import com.klikli_dev.occultism.registry.OccultismEntities;
import com.klikli_dev.occultism.registry.OccultismItems;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.loot.EntityLootSubProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.LootTable.Builder;
import net.minecraft.world.level.storage.loot.entries.EmptyLootItem;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.EnchantedCountIncreaseFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.functions.SetPotionFunction;
import net.minecraft.world.level.storage.loot.functions.SmeltItemFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemKilledByPlayerCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceWithEnchantedBonusCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

import java.util.function.BiConsumer;

public class OccultismEntityLoot extends EntityLootSubProvider {
    public OccultismEntityLoot(Provider pRegistries) {
        super(FeatureFlags.REGISTRY.allFlags(), pRegistries);
    }

    @Override
    public void generate(BiConsumer<ResourceKey<LootTable>, Builder> pGenerator) {
        this.generate();
        this.map.forEach((key, entityType) -> {
            entityType.forEach(pGenerator::accept);
        });
    }


    @Override
    public void generate() {
        this.add(OccultismEntities.POSSESSED_SHULKER_TYPE.get(), this.shulkerLootTable());
        this.add(OccultismEntities.POSSESSED_WARDEN_TYPE.get(), this.wardenLootTable());
        this.add(OccultismEntities.POSSESSED_HOGLIN_TYPE.get(), this.hoglinLootTable());
        this.add(OccultismEntities.POSSESSED_WITCH_TYPE.get(), this.witchLootTable());
        this.add(OccultismEntities.POSSESSED_BLAZE_TYPE.get(), this.blazeLootTable());
        this.add(OccultismEntities.POSSESSED_WEAK_SHULKER_TYPE.get(), this.weakShulkerTable());
        this.add(OccultismEntities.POSSESSED_GHAST_TYPE.get(), this.ghastLootTable());
        this.add(OccultismEntities.POSSESSED_ELDER_GUARDIAN_TYPE.get(), this.elderGuardianLootTable());
        this.add(OccultismEntities.WILD_HORDE_HUSK_TYPE.get(), this.huskLootTable());
        this.add(OccultismEntities.WILD_HORDE_DROWNED_TYPE.get(), this.drownedLootTable());
        this.add(OccultismEntities.WILD_HORDE_CREEPER_TYPE.get(), this.creeperLootTable());
        this.add(OccultismEntities.WILD_HORDE_SILVERFISH_TYPE.get(), this.silverfishLootTable());
        this.add(OccultismEntities.POSSESSED_WEAK_BREEZE_TYPE.get(), this.weakBreezeTable());
        this.add(OccultismEntities.POSSESSED_BREEZE_TYPE.get(), this.breezeTable());
        this.add(OccultismEntities.POSSESSED_STRONG_BREEZE_TYPE.get(), this.strongBreezeTable());
        this.add(OccultismEntities.POSSESSED_EVOKER_TYPE.get(), this.evokerTable());
        this.add(OccultismEntities.POSSESSED_GUARDIAN_TYPE.get(), this.guardianLootTable());
        this.add(OccultismEntities.POSSESSED_ENDERMITE_TYPE.get(),
                LootTable.lootTable().withPool(
                                LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                                        .add(LootItem.lootTableItem(Items.END_STONE).setWeight(99)
                                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0f, 2.0F)))
                                                .apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.registries, UniformGenerator.between(0.0F, 1.0F))))
                                        .add(LootItem.lootTableItem(Items.END_STONE_BRICKS).setWeight(1)
                                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0f, 2.0F)))
                                                .apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.registries, UniformGenerator.between(0.0F, 1.0F)))))
                        .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                                .add(LootItem.lootTableItem(Items.FERMENTED_SPIDER_EYE).setWeight(2)
                                        .apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.registries, UniformGenerator.between(0.0F, 1.0F))))
                                .add(LootItem.lootTableItem(Items.SPIDER_EYE).setWeight(2)
                                        .apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.registries, UniformGenerator.between(0.0F, 1.0F))))
                                .add(LootItem.lootTableItem(Items.ENDER_EYE).setWeight(1)
                                        .apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.registries, UniformGenerator.between(0.0F, 1.0F))))
                                .when(LootItemRandomChanceCondition.randomChance(ConstantValue.exactly(0.25F)))));

        //Guaranteed ender pearl drop for enderman
        this.add(OccultismEntities.POSSESSED_ENDERMAN_TYPE.get(),
                LootTable.lootTable().withPool(
                                LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                                        .add(LootItem.lootTableItem(Items.ENDER_PEARL)
                                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0f, 3.0F)))
                                                .apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.registries,
                                                        UniformGenerator.between(0.0F, 1.0F)))))
                        .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                                .add(LootItem.lootTableItem(Items.EYE_ARMOR_TRIM_SMITHING_TEMPLATE))
                                .when(LootItemRandomChanceCondition.randomChance(0.1F))));

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
                LootTable.lootTable()
                        .withPool(
                                LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                                        .add(LootItem.lootTableItem(Items.PHANTOM_MEMBRANE)
                                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0f, 4.0F)))
                                                .apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.registries, UniformGenerator.between(0.5F, 2.0F)))))
                        .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                                .add(LootItem.lootTableItem(Items.WIND_CHARGE)
                                        .apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.registries, UniformGenerator.between(0.0F, 1.0F))))
                                .when(LootItemRandomChanceCondition.randomChance(ConstantValue.exactly(0.05F))))
        );

        //Essence drop from wild afrit
        this.add(OccultismEntities.AFRIT_WILD_TYPE.get(),
                LootTable.lootTable().withPool(
                        LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                                .add(LootItem.lootTableItem(OccultismItems.AFRIT_ESSENCE.get())
                                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.7f, 1.0F)))
                                        .apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.registries, UniformGenerator.between(0.0F, 1.0F))))));

        //Essence drop from unbound marid
        this.add(OccultismEntities.MARID_UNBOUND_TYPE.get(),
                LootTable.lootTable().withPool(
                        LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                                .add(LootItem.lootTableItem(OccultismItems.MARID_ESSENCE.get())
                                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.9f, 1.0F)))
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
                        .add(LootItem.lootTableItem(Items.WITHER_ROSE)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 3.0F)))
                                .apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.registries, UniformGenerator.between(0.0F, 1.0F)))
                                .when(LootItemRandomChanceCondition.randomChance(0.3F)))
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

        this.add(OccultismEntities.GOAT_OF_MERCY_TYPE.get(),
                LootTable.lootTable().withPool(
                        LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                                .add(LootItem.lootTableItem(OccultismItems.CRUELTY_ESSENCE)
                                        .apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.registries, UniformGenerator.between(0.0F, 1.0F))))));

        this.add(OccultismEntities.POSSESSED_ZOMBIE_PIGLIN_TYPE.get(),
                LootTable.lootTable().withPool(
                                LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                                        .add(LootItem.lootTableItem(OccultismItems.DEMONIC_MEAT)
                                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 4.0F)))
                                                .apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.registries, UniformGenerator.between(0.0F, 1.0F)))))
                        .withPool(
                                LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                                        .add(LootItem.lootTableItem(OccultismItems.TALLOW)
                                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F)))
                                                .apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.registries, UniformGenerator.between(0.0F, 1.0F))))
                                        .when(LootItemRandomChanceCondition.randomChance(ConstantValue.exactly(0.8F))))
                        .withPool(
                                LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                                        .add(LootItem.lootTableItem(Items.PORKCHOP)
                                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 4.0F)))
                                                .apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.registries, UniformGenerator.between(0.0F, 1.0F))))
                                        .add(LootItem.lootTableItem(Items.ROTTEN_FLESH)
                                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 4.0F)))
                                                .apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.registries, UniformGenerator.between(0.0F, 1.0F))))
                                        .when(LootItemRandomChanceCondition.randomChance(ConstantValue.exactly(0.33F)))));

        this.add(OccultismEntities.POSSESSED_BEE_TYPE.get(),
                LootTable.lootTable().withPool(
                        LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                                .add(LootItem.lootTableItem(OccultismItems.CURSED_HONEY)
                                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.5F, 1.0F)))
                                        .apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.registries, UniformGenerator.between(0.3F, 1.0F))))));
    }

    /**
     * Copied from VanillaEntityLoot and modified
     *
     * @return
     */
    public Builder elderGuardianLootTable() {
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
                                .add(LootItem.lootTableItem(Items.COD).setWeight(2)
                                        .apply(SmeltItemFunction.smelted().when(this.shouldSmeltLoot())))
                                .add(LootItem.lootTableItem(Items.SALMON).setWeight(2)
                                        .apply(SmeltItemFunction.smelted().when(this.shouldSmeltLoot())))
                                .add(LootItem.lootTableItem(Items.TROPICAL_FISH))
                                .add(LootItem.lootTableItem(Items.PUFFERFISH))
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

    public Builder ghastLootTable() {
        return LootTable.lootTable()
                .withPool(
                        LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(
                                        LootItem.lootTableItem(Items.GHAST_TEAR)
                                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F))
                                                )
                                )
                )
                .withPool(
                        LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(
                                        LootItem.lootTableItem(Items.GUNPOWDER)
                                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 4.0F))
                                                )
                                )
                )
                .withPool(
                        LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(LootItem.lootTableItem(Items.MUSIC_DISC_TEARS))
                                .add(LootItem.lootTableItem(Items.DRIED_GHAST))
                                .when(LootItemRandomChanceWithEnchantedBonusCondition
                                        .randomChanceAndLootingBoost(registries, 0.2F, 0.05F))
                );

    }

    public Builder hoglinLootTable() {
        return LootTable.lootTable()
                .withPool(
                        LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(LootItem.lootTableItem(Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE).setWeight(2))
                                .add(LootItem.lootTableItem(Items.SNOUT_ARMOR_TRIM_SMITHING_TEMPLATE).setWeight(1))
                                .add(LootItem.lootTableItem(Items.NETHERITE_SCRAP).setWeight(3))
                                .add(LootItem.lootTableItem(Items.NETHER_BRICK).setWeight(1))
                                .add(LootItem.lootTableItem(Items.PIGLIN_BANNER_PATTERN).setWeight(1))
                                .add(LootItem.lootTableItem(Items.MUSIC_DISC_PIGSTEP).setWeight(1))
                );
    }

    public Builder shulkerLootTable() {
        return LootTable.lootTable()
                .withPool(
                        LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(
                                        LootItem.lootTableItem(Items.SHULKER_SHELL)
                                )
                                .when(LootItemRandomChanceWithEnchantedBonusCondition.randomChanceAndLootingBoost(this.registries, 0.25F, 0.1F))
                )
                .withPool(
                        LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(
                                        LootItem.lootTableItem(Items.SHULKER_SHELL)
                                )
                )
                .withPool(
                        LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(LootItem.lootTableItem(Items.SPIRE_ARMOR_TRIM_SMITHING_TEMPLATE).setWeight(1))
                                .add(LootItem.lootTableItem(Items.CHORUS_FLOWER).setWeight(2))
                                .add(LootItem.lootTableItem(Items.CHORUS_FRUIT).setWeight(4))
                                .when(LootItemRandomChanceCondition.randomChance(0.1F))
                );
    }

    public Builder wardenLootTable() {
        return LootTable.lootTable()
                .withPool(
                        LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(
                                        LootItem.lootTableItem(Items.ECHO_SHARD)
                                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(6.0F, 9.0F))
                                                )
                                )
                )
                .withPool(
                        LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(EmptyLootItem.emptyItem().setWeight(8))
                                .add(LootItem.lootTableItem(Items.SILENCE_ARMOR_TRIM_SMITHING_TEMPLATE))
                                .add(LootItem.lootTableItem(Items.WARD_ARMOR_TRIM_SMITHING_TEMPLATE))
                )
                .withPool(
                        LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(EmptyLootItem.emptyItem().setWeight(10))
                                .add(LootItem.lootTableItem(Items.DISC_FRAGMENT_5).setWeight(9))
                                .add(LootItem.lootTableItem(Items.MUSIC_DISC_OTHERSIDE))
                )
                .withPool(
                        LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(EmptyLootItem.emptyItem().setWeight(1))
                                .add(LootItem.lootTableItem(Items.SCULK).setWeight(9))
                                .add(LootItem.lootTableItem(Items.SCULK_VEIN).setWeight(15))
                                .add(LootItem.lootTableItem(Items.SCULK_CATALYST).setWeight(3))
                                .add(LootItem.lootTableItem(Items.SCULK_SHRIEKER).setWeight(6))
                                .add(LootItem.lootTableItem(Items.SCULK_SENSOR).setWeight(12))
                );
    }

    public Builder weakShulkerTable() {
        return LootTable.lootTable()
                .withPool(
                        LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(LootItem.lootTableItem(Items.SHULKER_SHELL)
                                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 1.0F))))
                                .when(LootItemRandomChanceCondition.randomChance(0.1F))
                ).withPool(
                        LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(LootItem.lootTableItem(Items.CHORUS_FRUIT)
                                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F))))
                );
    }

    public Builder huskLootTable() {
        return LootTable.lootTable()
                .withPool(
                        LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(EmptyLootItem.emptyItem().setWeight(3))
                                .add(LootItem.lootTableItem(Items.DUNE_ARMOR_TRIM_SMITHING_TEMPLATE).setWeight(1))
                )
                .withPool(
                        LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(LootItem.lootTableItem(Items.ARCHER_POTTERY_SHERD).setWeight(1))
                                .add(LootItem.lootTableItem(Items.MINER_POTTERY_SHERD).setWeight(1))
                                .add(LootItem.lootTableItem(Items.PRIZE_POTTERY_SHERD).setWeight(1))
                                .add(LootItem.lootTableItem(Items.SKULL_POTTERY_SHERD).setWeight(1))
                                .add(LootItem.lootTableItem(Items.ARMS_UP_POTTERY_SHERD).setWeight(1))
                                .add(LootItem.lootTableItem(Items.BREWER_POTTERY_SHERD).setWeight(1))
                );
    }

    public Builder drownedLootTable() {
        return LootTable.lootTable()
                .withPool(
                        LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(EmptyLootItem.emptyItem().setWeight(2))
                                .add(LootItem.lootTableItem(Items.SNIFFER_EGG).setWeight(2))
                                .add(LootItem.lootTableItem(Items.TURTLE_EGG).setWeight(3))
                                .add(LootItem.lootTableItem(Items.TRIDENT).setWeight(3))
                )
                .withPool(
                        LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(LootItem.lootTableItem(Items.ANGLER_POTTERY_SHERD).setWeight(1))
                                .add(LootItem.lootTableItem(Items.SHELTER_POTTERY_SHERD).setWeight(1))
                                .add(LootItem.lootTableItem(Items.SNORT_POTTERY_SHERD).setWeight(1))
                                .add(LootItem.lootTableItem(Items.BLADE_POTTERY_SHERD).setWeight(1))
                                .add(LootItem.lootTableItem(Items.EXPLORER_POTTERY_SHERD).setWeight(1))
                                .add(LootItem.lootTableItem(Items.MOURNER_POTTERY_SHERD).setWeight(1))
                                .add(LootItem.lootTableItem(Items.PLENTY_POTTERY_SHERD).setWeight(1))
                )
                .withPool(
                        LootPool.lootPool()
                                .setRolls(UniformGenerator.between(1.0F, 2.0F))
                                .add(EmptyLootItem.emptyItem().setWeight(2))
                                .add(LootItem.lootTableItem(Items.COPPER_INGOT).setWeight(3))
                                .add(LootItem.lootTableItem(Items.PRISMARINE_SHARD).setWeight(6))
                                .add(LootItem.lootTableItem(Items.PRISMARINE_CRYSTALS))
                                .apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.registries, ConstantValue.exactly(1.0F)))
                );
    }

    public Builder creeperLootTable() {
        return LootTable.lootTable()
                .withPool(
                        LootPool.lootPool()
                                .setRolls(UniformGenerator.between(1.0F, 3.0F))
                                .add(LootItem.lootTableItem(Items.MUSIC_DISC_13).setWeight(1))
                                .add(LootItem.lootTableItem(Items.MUSIC_DISC_CAT).setWeight(1))
                                .add(LootItem.lootTableItem(Items.MUSIC_DISC_BLOCKS).setWeight(1))
                                .add(LootItem.lootTableItem(Items.MUSIC_DISC_CHIRP).setWeight(1))
                                .add(LootItem.lootTableItem(Items.MUSIC_DISC_FAR).setWeight(1))
                                .add(LootItem.lootTableItem(Items.MUSIC_DISC_MALL).setWeight(1))
                                .add(LootItem.lootTableItem(Items.MUSIC_DISC_MELLOHI).setWeight(1))
                                .add(LootItem.lootTableItem(Items.MUSIC_DISC_STAL).setWeight(1))
                                .add(LootItem.lootTableItem(Items.MUSIC_DISC_STRAD).setWeight(1))
                                .add(LootItem.lootTableItem(Items.MUSIC_DISC_WARD).setWeight(1))
                                .add(LootItem.lootTableItem(Items.MUSIC_DISC_11).setWeight(1))
                                .add(LootItem.lootTableItem(Items.MUSIC_DISC_WAIT).setWeight(1))
                );
    }

    public Builder silverfishLootTable() {
        return LootTable.lootTable()
                .withPool(
                        LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(EmptyLootItem.emptyItem().setWeight(4))
                                .add(LootItem.lootTableItem(Items.MUSIC_DISC_RELIC).setWeight(1))
                )
                .withPool(
                        LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(EmptyLootItem.emptyItem().setWeight(4))
                                .add(LootItem.lootTableItem(Items.HOST_ARMOR_TRIM_SMITHING_TEMPLATE).setWeight(1))
                                .add(LootItem.lootTableItem(Items.RAISER_ARMOR_TRIM_SMITHING_TEMPLATE).setWeight(1))
                                .add(LootItem.lootTableItem(Items.SHAPER_ARMOR_TRIM_SMITHING_TEMPLATE).setWeight(1))
                                .add(LootItem.lootTableItem(Items.WAYFINDER_ARMOR_TRIM_SMITHING_TEMPLATE).setWeight(1))
                )
                .withPool(
                        LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(LootItem.lootTableItem(Items.BURN_POTTERY_SHERD).setWeight(1))
                                .add(LootItem.lootTableItem(Items.DANGER_POTTERY_SHERD).setWeight(1))
                                .add(LootItem.lootTableItem(Items.FRIEND_POTTERY_SHERD).setWeight(1))
                                .add(LootItem.lootTableItem(Items.HEART_POTTERY_SHERD).setWeight(1))
                                .add(LootItem.lootTableItem(Items.HEARTBREAK_POTTERY_SHERD).setWeight(1))
                                .add(LootItem.lootTableItem(Items.HOWL_POTTERY_SHERD).setWeight(1))
                                .add(LootItem.lootTableItem(Items.SHEAF_POTTERY_SHERD).setWeight(1))
                );
    }

    public Builder weakBreezeTable() {
        return LootTable.lootTable()
                .withPool(
                        LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(LootItem.lootTableItem(Items.TRIAL_KEY).setWeight(1))
                )
                .withPool(
                        LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(LootItem.lootTableItem(Items.OMINOUS_BOTTLE).setWeight(1))
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.5F, 2.0F)))
                )
                .withPool(
                        LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(EmptyLootItem.emptyItem().setWeight(2))
                                .add(LootItem.lootTableItem(Items.GUSTER_POTTERY_SHERD).setWeight(1))
                                .add(LootItem.lootTableItem(Items.SCRAPE_POTTERY_SHERD).setWeight(1))
                )
                .withPool(
                        LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(EmptyLootItem.emptyItem().setWeight(7))
                                .add(LootItem.lootTableItem(Items.MUSIC_DISC_CREATOR_MUSIC_BOX).setWeight(1))
                );
    }

    public Builder breezeTable() {
        return LootTable.lootTable()
                .withPool(
                        LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(LootItem.lootTableItem(Items.OMINOUS_TRIAL_KEY).setWeight(1))
                )
                .withPool(
                        LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(LootItem.lootTableItem(Items.BREEZE_ROD).setWeight(1))
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F)))
                )
                .withPool(
                        LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(EmptyLootItem.emptyItem().setWeight(8))
                                .add(LootItem.lootTableItem(Items.BOLT_ARMOR_TRIM_SMITHING_TEMPLATE).setWeight(4))
                                .add(LootItem.lootTableItem(Items.GUSTER_BANNER_PATTERN).setWeight(2))
                                .add(LootItem.lootTableItem(Items.MUSIC_DISC_PRECIPICE).setWeight(1))
                );
    }

    public Builder strongBreezeTable() {
        return LootTable.lootTable()
                .withPool(
                        LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(LootItem.lootTableItem(Items.HEAVY_CORE).setWeight(1))
                )
                .withPool(
                        LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(EmptyLootItem.emptyItem().setWeight(5))
                                .add(LootItem.lootTableItem(Items.FLOW_POTTERY_SHERD).setWeight(4))
                                .add(LootItem.lootTableItem(Items.FLOW_ARMOR_TRIM_SMITHING_TEMPLATE).setWeight(3))
                                .add(LootItem.lootTableItem(Items.FLOW_BANNER_PATTERN).setWeight(2))
                                .add(LootItem.lootTableItem(Items.MUSIC_DISC_CREATOR).setWeight(1))
                );
    }

    public Builder evokerTable() {
        return LootTable.lootTable()
                .withPool(
                        LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(LootItem.lootTableItem(Items.TOTEM_OF_UNDYING).setWeight(1))
                )
                .withPool(
                        LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(EmptyLootItem.emptyItem().setWeight(2))
                                .add(LootItem.lootTableItem(Items.VEX_ARMOR_TRIM_SMITHING_TEMPLATE).setWeight(1))
                                .add(LootItem.lootTableItem(Items.SENTRY_ARMOR_TRIM_SMITHING_TEMPLATE).setWeight(1))
                )
                .withPool(
                        LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(LootItem.lootTableItem(Items.OMINOUS_BOTTLE).setWeight(1))
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.5F, 2.0F)))
                );
    }

    public Builder witchLootTable() {
        return LootTable.lootTable()
                .withPool(
                        LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(LootItem.lootTableItem(Items.EXPERIENCE_BOTTLE).setWeight(8))
                                .add(LootItem.lootTableItem(Items.HONEY_BOTTLE).setWeight(4))
                                .add(LootItem.lootTableItem(Items.OMINOUS_BOTTLE).setWeight(2))
                                .add(LootItem.lootTableItem(Items.POTION).setWeight(1).apply(SetPotionFunction.setPotion(Potions.WATER)))
                ).apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.registries, UniformGenerator.between(0, 2)));
    }

    public Builder blazeLootTable() {
        return LootTable.lootTable()
                .withPool(
                        LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(LootItem.lootTableItem(Items.BLAZE_ROD))
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(2, 6)))
                                .apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.registries, UniformGenerator.between(0, 2)))
                )
                .withPool(
                        LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(LootItem.lootTableItem(Items.BLAZE_POWDER))
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(0, 13))))
                .withPool(
                        LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(LootItem.lootTableItem(Items.NETHER_WART).setWeight(30))
                                .add(LootItem.lootTableItem(Items.CRIMSON_FUNGUS).setWeight(15))
                                .add(LootItem.lootTableItem(Items.WARPED_FUNGUS).setWeight(15))
                                .add(LootItem.lootTableItem(Items.RED_MUSHROOM).setWeight(10))
                                .add(LootItem.lootTableItem(Items.BROWN_MUSHROOM).setWeight(10))
                                .add(LootItem.lootTableItem(Items.CRIMSON_ROOTS).setWeight(5))
                                .add(LootItem.lootTableItem(Items.WARPED_ROOTS).setWeight(5))
                                .add(LootItem.lootTableItem(Items.WEEPING_VINES).setWeight(5))
                                .add(LootItem.lootTableItem(Items.TWISTING_VINES).setWeight(5))
                                .apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.registries, UniformGenerator.between(1, 3)))
                )
                .withPool(
                        LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(EmptyLootItem.emptyItem().setWeight(10))
                                .add(LootItem.lootTableItem(Items.NETHERRACK).setWeight(45))
                                .add(LootItem.lootTableItem(Items.NETHER_QUARTZ_ORE).setWeight(25))
                                .add(LootItem.lootTableItem(Items.CRIMSON_NYLIUM).setWeight(9))
                                .add(LootItem.lootTableItem(Items.WARPED_NYLIUM).setWeight(9))
                                .add(LootItem.lootTableItem(Items.NETHER_WART_BLOCK).setWeight(1))
                                .add(LootItem.lootTableItem(Items.WARPED_WART_BLOCK).setWeight(1))
                                .apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.registries, UniformGenerator.between(0, 1)))
                )
                .withPool(
                        LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(EmptyLootItem.emptyItem().setWeight(25))
                                .add(LootItem.lootTableItem(Items.SOUL_SAND).setWeight(15))
                                .add(LootItem.lootTableItem(Items.SOUL_SOIL).setWeight(15))
                                .add(LootItem.lootTableItem(Items.BASALT).setWeight(15))
                                .add(LootItem.lootTableItem(Items.BLACKSTONE).setWeight(15))
                                .add(LootItem.lootTableItem(Items.GRAVEL).setWeight(9))
                                .add(LootItem.lootTableItem(Items.BONE_BLOCK).setWeight(5))
                                .add(LootItem.lootTableItem(Items.GILDED_BLACKSTONE).setWeight(1))
                                .apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.registries, UniformGenerator.between(0, 1)))
                )
                .withPool(
                        LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(EmptyLootItem.emptyItem().setWeight(50))
                                .add(LootItem.lootTableItem(Items.GLOWSTONE_DUST).setWeight(25).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 6))))
                                .add(LootItem.lootTableItem(Items.MAGMA_BLOCK).setWeight(15))
                                .add(LootItem.lootTableItem(Items.GLOWSTONE).setWeight(9))
                                .add(LootItem.lootTableItem(Items.SHROOMLIGHT).setWeight(1))
                                .apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.registries, UniformGenerator.between(0, 1)))
                )
                .withPool(
                        LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(EmptyLootItem.emptyItem().setWeight(75))
                                .add(LootItem.lootTableItem(Items.OBSIDIAN).setWeight(20))
                                .add(LootItem.lootTableItem(Items.CRYING_OBSIDIAN).setWeight(4))
                                .add(LootItem.lootTableItem(Items.ANCIENT_DEBRIS).setWeight(1))
                                .apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.registries, ConstantValue.exactly(1)))
                );
    }

    public Builder guardianLootTable() {
        return LootTable.lootTable()
                .withPool(
                        LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(LootItem.lootTableItem(Items.SEA_PICKLE).setWeight(9))
                                .add(LootItem.lootTableItem(Items.KELP).setWeight(1))
                                .when(LootItemRandomChanceWithEnchantedBonusCondition.randomChanceAndLootingBoost(this.registries, 0.8F, 0.1F))
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 2.0F)))
                )
                .withPool(
                        LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(LootItem.lootTableItem(Items.TUBE_CORAL_BLOCK))
                                .add(LootItem.lootTableItem(Items.BRAIN_CORAL_BLOCK))
                                .add(LootItem.lootTableItem(Items.BUBBLE_CORAL_BLOCK))
                                .add(LootItem.lootTableItem(Items.FIRE_CORAL_BLOCK))
                                .add(LootItem.lootTableItem(Items.HORN_CORAL_BLOCK))
                )
                .withPool(
                        LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(LootItem.lootTableItem(Items.TUBE_CORAL))
                                .add(LootItem.lootTableItem(Items.BRAIN_CORAL))
                                .add(LootItem.lootTableItem(Items.BUBBLE_CORAL))
                                .add(LootItem.lootTableItem(Items.FIRE_CORAL))
                                .add(LootItem.lootTableItem(Items.HORN_CORAL))
                                .apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.registries, UniformGenerator.between(0, 1)))
                )
                .withPool(
                        LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(LootItem.lootTableItem(Items.TUBE_CORAL_FAN))
                                .add(LootItem.lootTableItem(Items.BRAIN_CORAL_FAN))
                                .add(LootItem.lootTableItem(Items.BUBBLE_CORAL_FAN))
                                .add(LootItem.lootTableItem(Items.FIRE_CORAL_FAN))
                                .add(LootItem.lootTableItem(Items.HORN_CORAL_FAN))
                                .apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.registries, ConstantValue.exactly(1)))
                )
                .withPool(
                        LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(
                                        LootItem.lootTableItem(Items.PRISMARINE_SHARD)
                                                .setWeight(2)
                                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 2.0F)))
                                                .apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.registries, UniformGenerator.between(0.0F, 1.0F)))
                                )
                                .add(
                                        LootItem.lootTableItem(Items.PRISMARINE_CRYSTALS)
                                                .apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.registries, UniformGenerator.between(0.0F, 1.0F)))
                                )
                                .add(EmptyLootItem.emptyItem().setWeight(3))
                );
    }
}
