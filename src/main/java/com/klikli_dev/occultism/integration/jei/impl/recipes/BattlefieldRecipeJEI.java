package com.klikli_dev.occultism.integration.jei.impl.recipes;

import com.klikli_dev.occultism.Occultism;
import com.klikli_dev.occultism.common.entity.possessed.PossessedMob;
import com.klikli_dev.occultism.registry.OccultismDataComponents;
import com.klikli_dev.occultism.registry.OccultismItems;
import com.klikli_dev.occultism.registry.OccultismTags;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.item.component.TypedEntityData;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.util.FakePlayer;
import net.neoforged.neoforge.common.util.FakePlayerFactory;
import net.neoforged.neoforge.server.ServerLifecycleHooks;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


// Based on https://github.com/CyclopsMC/EvilCraft-Compat/blob/master-26/src/main/java/org/cyclops/evilcraftcompat/modcompat/jei/spiritfurnace/SpiritFurnaceRecipeJEI.java
public class BattlefieldRecipeJEI {

    private final ItemStack inputItem;
    private final ItemStack spawnEgg;
    private final List<ItemStack> outputItems;
    private final List<ItemStack> soulItems;
    private final EntityType<?> entityType;
    private static List<BattlefieldRecipeJEI> cachedRecipes = null;

    public BattlefieldRecipeJEI(ItemStack inputItem, ItemStack egg, List<ItemStack> outputItems, List<ItemStack> soulItems, EntityType<?> entityType) {
        this.inputItem = inputItem;
        this.spawnEgg = egg;
        this.outputItems = outputItems;
        this.soulItems = soulItems;
        this.entityType = entityType;
    }

    public static BattlefieldRecipeJEI create(EntityType<?> entityType, LivingEntity entity, ServerLevel level, List<ItemStack> soul) {
        return new BattlefieldRecipeJEI(
                getGem(entityType, level),
                getEgg(entityType),
                getMobDrops(entityType, entity, level),
                getSoulStack(entity, soul),
                entityType
        );
    }

    public static void reloadCache() {
        cachedRecipes = null;
        generateServerRecipes();
    }

    public static ItemStack getGem(EntityType<?> entityType, ServerLevel level) {
        Entity entity = entityType.create(level, EntitySpawnReason.MOB_SUMMONED);
        ItemStack stack = new ItemStack(OccultismItems.SOUL_GEM_ITEM.get());
        if (entity instanceof LivingEntity) {
            if (entity instanceof PossessedMob || entity.is(OccultismTags.Entities.SOUL_GEM_DENY_LIST)) {
                stack = new ItemStack(OccultismItems.TRINITY_GEM_ITEM.get());
                if (entity.is(OccultismTags.Entities.TRINITY_GEM_DENY_LIST)) {
                    if (entity.is(OccultismTags.Entities.SOUL_SHATTERED_DENY_LIST)) {
                        stack = ItemStack.EMPTY;
                    } else {
                        stack = new ItemStack(OccultismItems.SOUL_SHATTERED_ITEM.get());
                    }
                }
            }
            if (!stack.isEmpty())
                stack.set(DataComponents.ENTITY_DATA, TypedEntityData.of(entityType, new CompoundTag()));
        }
        return stack;
    }

    public static ItemStack getEgg(EntityType<?> entityType) {
        ItemStack stack = ItemStack.EMPTY;
        if (SpawnEggItem.byId(entityType).isPresent()) {
            stack = SpawnEggItem.byId(entityType).get().value().getDefaultInstance();
        }
        return stack;
    }

    public static List<ItemStack> getMobDrops(EntityType<?> entityType, LivingEntity entity, ServerLevel level) {
        List<ItemStack> items = new ArrayList<>();

        FakePlayer killerEntity = FakePlayerFactory.getMinecraft(level);
        LootParams lootParams = new LootParams.Builder(level)
                .withParameter(LootContextParams.THIS_ENTITY, entity)
                .withParameter(LootContextParams.ORIGIN, Vec3.ZERO)
                .withParameter(LootContextParams.DAMAGE_SOURCE, killerEntity.damageSources().playerAttack(killerEntity))
                .withParameter(LootContextParams.LAST_DAMAGE_PLAYER, killerEntity)
                .withParameter(LootContextParams.ATTACKING_ENTITY, killerEntity)
                .withParameter(LootContextParams.DIRECT_ATTACKING_ENTITY, killerEntity)
                .create(LootContextParamSets.ENTITY);

        ResourceKey<LootTable> customLoot = ResourceKey.create(Registries.LOOT_TABLE,
                Identifier.fromNamespaceAndPath(Occultism.MODID, "battlefield/"
                        + BuiltInRegistries.ENTITY_TYPE.getKey(entityType).toString().replace(":","/")));
        LootTable lootTable = level.getServer().reloadableRegistries().getLootTable(customLoot);
        if (lootTable == LootTable.EMPTY && entity.getLootTable().isPresent()) {
            lootTable = level.getServer().reloadableRegistries().getLootTable(entity.getLootTable().get());
        }

        LootContext context = new LootContext.Builder(lootParams).create(Optional.empty());
        for (LootPool pool : lootTable.pools) {
            for (LootPoolEntryContainer entryContainer : pool.entries) {
                entryContainer.expand(context, entry -> entry.createItemStack(item -> {
                    item.setCount(1);
                    if (!item.isEmpty()) {
                        items.add(item.copy());
                    }
                }, context));
            }
        }
        if (entity instanceof LivingEntity living &&  living.getExperienceReward(level, killerEntity) > 0)
           items.add(new ItemStack(Items.EXPERIENCE_BOTTLE));

        return items;
    }

    public static List<ItemStack> getSoulStack(LivingEntity entity, List<ItemStack> soulItems) {
        List<ItemStack> soulStacks = soulItems.stream().map(ItemStack::copy).toList();
        for (ItemStack stack : soulStacks) {
            int soulValue = stack.getOrDefault(OccultismDataComponents.SOUL_VALUE, 0);
            stack.setCount(1 + ((int) entity.getMaxHealth() / Math.max(soulValue, 1)));
        }
        return soulStacks;
    }

    public ItemStack getInputItem() {
        return inputItem;
    }

    public ItemStack getSpawnEgg() {
        return spawnEgg;
    }

    public List<ItemStack> getOutputItems() {
        return outputItems;
    }

    public List<ItemStack> getSoulItems() {
        return soulItems;
    }

    public EntityType<?> getEntityType() {
        return entityType;
    }

    public static List<BattlefieldRecipeJEI> getCachedRecipes() {
        return cachedRecipes;
    }

    public static void encode(BattlefieldRecipeJEI recipe, RegistryFriendlyByteBuf output) {
        ItemStack.OPTIONAL_STREAM_CODEC.encode(output, recipe.getInputItem());
        ItemStack.OPTIONAL_STREAM_CODEC.encode(output, recipe.getSpawnEgg());
        output.writeInt(recipe.outputItems.size());
        for (ItemStack outputItem : recipe.getOutputItems()) {
            ItemStack.OPTIONAL_STREAM_CODEC.encode(output, outputItem);
        }
        output.writeInt(recipe.soulItems.size());
        for (ItemStack soulItem : recipe.getSoulItems()) {
            ItemStack.OPTIONAL_STREAM_CODEC.encode(output, soulItem);
        }
        EntityType.STREAM_CODEC.encode(output, recipe.getEntityType());
    }

    public static BattlefieldRecipeJEI decode(RegistryFriendlyByteBuf input) {
        ItemStack inputItem = ItemStack.OPTIONAL_STREAM_CODEC.decode(input);
        ItemStack spawnEgg = ItemStack.OPTIONAL_STREAM_CODEC.decode(input);
        List<ItemStack> outputItems = new ArrayList<>();
        int outputItemsCount = input.readInt();
        for (int i = 0; i < outputItemsCount; ++i) {
            outputItems.add(ItemStack.OPTIONAL_STREAM_CODEC.decode(input));
        }
        List<ItemStack> soulItems = new ArrayList<>();
        int soulItemsCount = input.readInt();
        for (int i = 0; i < soulItemsCount; ++i) {
            soulItems.add(ItemStack.OPTIONAL_STREAM_CODEC.decode(input));
        }
        EntityType<?> entityType = EntityType.STREAM_CODEC.decode(input);
        return new BattlefieldRecipeJEI(inputItem, spawnEgg, outputItems, soulItems, entityType);
    }

    public static List<BattlefieldRecipeJEI> generateServerRecipes() {
        if (cachedRecipes != null) {
            return cachedRecipes;
        }

        ServerLevel level = ServerLifecycleHooks.getCurrentServer().overworld();
        List<BattlefieldRecipeJEI> recipes = new ArrayList<>();
        List<ItemStack> soul = new ArrayList<>();
        for (Item item : BuiltInRegistries.ITEM) {
            if (item.getDefaultInstance().has(OccultismDataComponents.SOUL_VALUE))
                soul.add(item.getDefaultInstance());
        }
        for (EntityType<?> entityType : BuiltInRegistries.ENTITY_TYPE) {
            if (entityType == EntityTypes.PLAYER)
                continue;
            try {
                Entity entity = entityType.create(level, EntitySpawnReason.SPAWN_ITEM_USE);
                if (entity instanceof LivingEntity livingEntity) {
                    BattlefieldRecipeJEI recipeJEI = BattlefieldRecipeJEI.create(entityType, livingEntity, level, soul);
                    if (!recipeJEI.getOutputItems().isEmpty())
                        recipes.add(recipeJEI);
                }
            } catch (Throwable e) {
                // Ignore errors during entity creation
            }
        }
        cachedRecipes = recipes;
        return recipes;
    }
}