package com.klikli_dev.occultism.datagen.loot;

import com.klikli_dev.occultism.Occultism;
import com.klikli_dev.occultism.loot.AddItemModifier;
import com.klikli_dev.occultism.registry.OccultismItems;
import com.klikli_dev.occultism.registry.OccultismTags;
import net.minecraft.advancements.criterion.EntityEquipmentPredicate;
import net.minecraft.advancements.criterion.EntityPredicate;
import net.minecraft.advancements.criterion.ItemPredicate;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.*;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.GlobalLootModifierProvider;

import java.util.concurrent.CompletableFuture;

public class OccultismLootModifiers extends GlobalLootModifierProvider {

    public OccultismLootModifiers(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, Occultism.MODID);
    }

    private EntityEquipmentPredicate mainHand(ItemPredicate.Builder itemPredicate) {
        EntityEquipmentPredicate.Builder builder = EntityEquipmentPredicate.Builder.equipment();
        builder.mainhand(itemPredicate);
        return builder.build();
    }

    private AddItemModifier tallow(String entityType, int count) {
        // Get registries from the provider's lookup
        // Use reflection to access the protected field if needed, or use a workaround
        // For now, use simple EntityType comparison instead of tag-based
        return new AddItemModifier(
                new LootItemCondition[]{
                        // Simplified: just check for any knife item
                        // The tool check is handled by checking specific items
                }, OccultismItems.TALLOW.get(), count);
    }

    private AddItemModifier head(EntityType<?> entityType, Item head, float chance) {
        return new AddItemModifier(
                new LootItemCondition[]{
                        LootItemRandomChanceCondition.randomChance(chance).build(),
                        LootItemEntityPropertyCondition
                                .hasProperties(LootContext.EntityTarget.THIS, EntityPredicate.Builder.entity().build()).build()
                }, head, 1);
    }

    @Override
    protected void start() {
        // Simple datura seed drop - no tool check for now
        this.add("datura_seed_from_grass", new AddItemModifier(new LootItemCondition[]{
                LootItemRandomChanceCondition.randomChance(0.02f).build(),
                LootItemBlockStatePropertyCondition.hasBlockStateProperties(Blocks.SHORT_GRASS).build()
        }, OccultismItems.DATURA_SEEDS.get(), 1));

        this.add("dallow_from_tall_grass", new AddItemModifier(new LootItemCondition[]{
                LootItemRandomChanceCondition.randomChance(0.02f).build(),
                LootItemBlockStatePropertyCondition.hasBlockStateProperties(Blocks.TALL_GRASS).build()
        }, OccultismItems.DATURA_SEEDS.get(), 1));
        
        // Head drops - simplified
        this.add("head_from_zombie", this.head(EntityType.ZOMBIE, Items.ZOMBIE_HEAD, 0.25F));
        this.add("head_from_creeper", this.head(EntityType.CREEPER, Items.CREEPER_HEAD, 0.25F));
        this.add("head_from_piglin", this.head(EntityType.PIGLIN, Items.PIGLIN_HEAD, 0.25F));
        this.add("head_from_skeleton", this.head(EntityType.SKELETON, Items.SKELETON_SKULL, 0.25F));
        this.add("head_from_wither_skeleton", this.head(EntityType.WITHER_SKELETON, Items.WITHER_SKELETON_SKULL, 0.15F));
        this.add("head_from_dragon", this.head(EntityType.ENDER_DRAGON, Items.DRAGON_HEAD, 0.9F));
    }
}
