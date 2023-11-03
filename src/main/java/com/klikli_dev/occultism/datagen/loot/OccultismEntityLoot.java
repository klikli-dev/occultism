package com.klikli_dev.occultism.datagen.loot;

import com.klikli_dev.occultism.registry.OccultismEntities;
import com.klikli_dev.occultism.registry.OccultismItems;
import net.minecraft.data.loot.EntityLootSubProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.LootingEnchantFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemKilledByPlayerCondition;
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
}
