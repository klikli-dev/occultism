package com.klikli_dev.occultism.datagen.loot;

import com.klikli_dev.occultism.registry.OccultismEntities;
import com.klikli_dev.occultism.registry.OccultismItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponents;
import net.minecraft.data.loot.EntityLootSubProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.PotionItem;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.EmptyLootItem;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.NestedLootTable;
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
        this.add(OccultismEntities.POSSESSED_SHULKER_TYPE.get(), this.shulkerLootTable());
        this.add(OccultismEntities.POSSESSED_WARDEN_TYPE.get(), this.wardenLootTable());
        this.add(OccultismEntities.POSSESSED_HOGLIN_TYPE.get(), this.hoglinLootTable());
        this.add(OccultismEntities.POSSESSED_WITCH_TYPE.get(), this.witchLootTable());
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
                                        .apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.registries, UniformGenerator.between(0.0F, 1.0F))))));

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

    public LootTable.Builder ghastLootTable(){
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
                );

    }

    public LootTable.Builder hoglinLootTable(){
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

    public LootTable.Builder shulkerLootTable(){
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
                                .add(
                                        LootItem.lootTableItem(Items.SPIRE_ARMOR_TRIM_SMITHING_TEMPLATE)
                                )
                                .when(LootItemRandomChanceCondition.randomChance(0.1F))
                );
    }

    public LootTable.Builder wardenLootTable(){
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
                );
    }

    public LootTable.Builder weakShulkerTable(){
        return LootTable.lootTable()
                .withPool(
                        LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(LootItem.lootTableItem(Items.SHULKER_SHELL)
                                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 1.0F))))
                                .when(LootItemRandomChanceCondition.randomChance(0.1F))
                ) .withPool(
                        LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(LootItem.lootTableItem(Items.CHORUS_FRUIT)
                                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F))))
                );
    }
    public LootTable.Builder huskLootTable(){
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
    public LootTable.Builder drownedLootTable(){
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
                );
    }
    public LootTable.Builder creeperLootTable(){
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
    public LootTable.Builder silverfishLootTable(){
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
    public LootTable.Builder weakBreezeTable(){
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
    public LootTable.Builder breezeTable(){
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
    public LootTable.Builder strongBreezeTable(){
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
    public LootTable.Builder evokerTable(){
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
    public LootTable.Builder witchLootTable(){
        return LootTable.lootTable()
                .withPool(
                        LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(LootItem.lootTableItem(Items.EXPERIENCE_BOTTLE).setWeight(8))
                                .add(LootItem.lootTableItem(Items.HONEY_BOTTLE).setWeight(4))
                                .add(LootItem.lootTableItem(Items.OMINOUS_BOTTLE).setWeight(2))
                                .add(LootItem.lootTableItem(Items.POTION).setWeight(1).apply(SetPotionFunction.setPotion(Potions.WATER)))
                ).apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.registries,UniformGenerator.between(0,2)));
    }
}
