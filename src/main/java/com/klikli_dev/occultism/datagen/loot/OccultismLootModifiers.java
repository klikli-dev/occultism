package com.klikli_dev.occultism.datagen.loot;

import com.klikli_dev.occultism.Occultism;
import com.klikli_dev.occultism.loot.AddItemModifier;
import com.klikli_dev.occultism.registry.OccultismEntities;
import com.klikli_dev.occultism.registry.OccultismItems;
import com.klikli_dev.occultism.registry.OccultismTags;
import net.minecraft.advancements.predicates.entity.EntityEquipmentPredicate;
import net.minecraft.advancements.predicates.entity.EntityPredicate;
import net.minecraft.advancements.predicates.ItemPredicate.Builder;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.storage.loot.LootContext.EntityTarget;
import net.minecraft.world.level.storage.loot.predicates.*;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.GlobalLootModifierProvider;

import java.util.concurrent.CompletableFuture;

public class OccultismLootModifiers extends GlobalLootModifierProvider {

    public OccultismLootModifiers(PackOutput output, CompletableFuture<Provider> registries) {
        super(output, registries, Occultism.MODID);
    }

    private EntityEquipmentPredicate mainHand(Builder itemPredicate) {
        EntityEquipmentPredicate.Builder builder = EntityEquipmentPredicate.Builder.equipment();
        builder.mainhand(itemPredicate);
        return builder.build();
    }

    private AddItemModifier seed(Block block) {
        var itemRegistry = this.registries.lookupOrThrow(Registries.ITEM);
        return new AddItemModifier(new LootItemCondition[]{
                LootItemRandomChanceCondition.randomChance(0.02f).build(),
                LootItemBlockStatePropertyCondition.hasBlockStateProperties(block).build(),
                InvertedLootItemCondition.invert(
                        MatchTool.toolMatches(Builder.item().of(itemRegistry, Tags.Items.TOOLS_SHEAR))
                ).build()
        }, OccultismItems.DATURA_SEEDS.get(), 1);
    }

    private AddItemModifier tallow(String entityType, int count) {
        var itemRegistry = this.registries.lookupOrThrow(Registries.ITEM);
        var entityTypeRegistry = this.registries.lookupOrThrow(Registries.ENTITY_TYPE);
        return new AddItemModifier(
                new LootItemCondition[]{
                        LootItemEntityPropertyCondition
                                .hasProperties(EntityTarget.ATTACKER,
                                        EntityPredicate.Builder.entity()
                                                .equipment(this.mainHand(Builder.item().of(itemRegistry,
                                                        OccultismTags.Items.TOOLS_KNIFE)))).build(),
                        LootItemEntityPropertyCondition
                                .hasProperties(EntityTarget.THIS,
                                        EntityPredicate.Builder.entity()
                                                .of(entityTypeRegistry, OccultismTags.makeEntityTypeTag(Identifier.fromNamespaceAndPath("c", entityType))).build()).build()
                }, OccultismItems.TALLOW.get(), count);
    }

    private AddItemModifier head(EntityType<?> entityType, Item head, float chance) {
        var itemRegistry = this.registries.lookupOrThrow(Registries.ITEM);
        var entityTypeRegistry = this.registries.lookupOrThrow(Registries.ENTITY_TYPE);
        return new AddItemModifier(
                new LootItemCondition[]{
                        LootItemEntityPropertyCondition
                                .hasProperties(EntityTarget.ATTACKER,
                                        EntityPredicate.Builder.entity()
                                                .equipment(this.mainHand(Builder.item().of(itemRegistry,
                                                        OccultismTags.Items.TOOLS_KNIFE_IESNIUM)))).build(),
                        LootItemRandomChanceCondition.randomChance(chance).build(),
                        LootItemEntityPropertyCondition
                                .hasProperties(EntityTarget.THIS,
                                        EntityPredicate.Builder.entity()
                                                .of(entityTypeRegistry, entityType)
                                                .build()).build()
                }, head, 1);
    }

    @Override
    protected void start() {
        this.add("datura_seed_from_short_grass", this.seed(Blocks.SHORT_GRASS));
        this.add("datura_seed_from_tall_grass", this.seed(Blocks.TALL_GRASS));
        this.add("datura_seed_from_short_dry_grass", this.seed(Blocks.SHORT_DRY_GRASS));
        this.add("datura_seed_from_tall_dry_grass", this.seed(Blocks.TALL_DRY_GRASS));
        this.add("datura_seed_from_fern", this.seed(Blocks.FERN));
        this.add("datura_seed_from_large_fern", this.seed(Blocks.LARGE_FERN));
        this.add("datura_seed_from_bush", this.seed(Blocks.BUSH));
        this.add("datura_seed_from_firefly_bush", this.seed(Blocks.FIREFLY_BUSH));

        this.add("tallow_from_cows", this.tallow("cows", 4));
        this.add("tallow_from_donkeys", this.tallow("donkeys", 3));
        this.add("tallow_from_goats", this.tallow("goats", 2));
        this.add("tallow_from_hoglins", this.tallow("hoglins", 4));
        this.add("tallow_from_horses", this.tallow("horses", 3));
        this.add("tallow_from_llamas", this.tallow("llamas", 3));
        this.add("tallow_from_mules", this.tallow("mules", 3));
        this.add("tallow_from_pandas", this.tallow("pandas", 3));
        this.add("tallow_from_pigs", this.tallow("pigs", 2));
        this.add("tallow_from_sheep", this.tallow("sheep", 2));

        // Head drops - simplified
        this.add("head_from_zombie", this.head(EntityTypes.ZOMBIE, Items.ZOMBIE_HEAD, 0.25F));
        this.add("head_from_creeper", this.head(EntityTypes.CREEPER, Items.CREEPER_HEAD, 0.25F));
        this.add("head_from_piglin", this.head(EntityTypes.PIGLIN, Items.PIGLIN_HEAD, 0.25F));
        this.add("head_from_skeleton", this.head(EntityTypes.SKELETON, Items.SKELETON_SKULL, 0.25F));
        this.add("head_from_wither_skeleton", this.head(EntityTypes.WITHER_SKELETON, Items.WITHER_SKELETON_SKULL, 0.15F));
        this.add("head_from_dragon", this.head(EntityTypes.ENDER_DRAGON, Items.DRAGON_HEAD, 0.9F));

        this.add("echo_dust_from_warden", this.head(EntityTypes.WARDEN, OccultismItems.ECHO_DUST.get(), 1F));
        this.add("echo_dust_from_possessed_warden", this.head(OccultismEntities.POSSESSED_WARDEN_TYPE.get(), OccultismItems.ECHO_DUST.get(), 1F));
    }
}
