/*
 * MIT License
 *
 * Copyright 2021 klikli-dev
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

package com.klikli_dev.occultism.common.blockentity;

import com.klikli_dev.occultism.Occultism;
import com.klikli_dev.occultism.common.container.DimensionalBattlefieldContainer;
import com.klikli_dev.occultism.common.entity.possessed.PossessedMob;
import com.klikli_dev.occultism.registry.*;
import com.klikli_dev.occultism.registry.OccultismTags.Entities;
import com.klikli_dev.occultism.util.ItemTransferUtil;
import com.klikli_dev.occultism.util.StorageUtil;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootParams.Builder;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.capabilities.Capabilities.Item;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.common.Tags.EntityTypes;
import net.neoforged.neoforge.common.util.FakePlayer;
import net.neoforged.neoforge.common.util.FakePlayerFactory;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.transfer.CombinedResourceHandler;
import net.neoforged.neoforge.transfer.RangedResourceHandler;
import net.neoforged.neoforge.transfer.ResourceHandler;
import net.neoforged.neoforge.transfer.item.ItemResource;
import net.neoforged.neoforge.transfer.item.ItemStacksResourceHandler;
import net.neoforged.neoforge.transfer.transaction.RootCommitJournal;
import net.neoforged.neoforge.transfer.transaction.TransactionContext;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.function.Consumer;

public class DimensionalBattlefieldBlockEntity extends NetworkedBlockEntity implements MenuProvider {

    private static final int DEFAULT_MAX_TIME = 20 * 20 * 20;
    private static final int DEFAULT_MAX_LUCK = 16;
    private static final ResourceKey<Enchantment> EVILCRAFT_UNUSING_ENCHANTMENT = ResourceKey.create(
            Registries.ENCHANTMENT, Identifier.parse("evilcraft:unusing"));
    private final float BUTCHER_HURT_CHANCE = (float) Occultism.SERVER_CONFIG.itemSettings.butcherHurtChance.getAsDouble();
    public int mobHealth;
    public int maxMobLife;
    public int hitTimer;
    public int maxHitTimer;
    public int soulValue;
    public Consumer<EntityJoinLevelEvent> entityJoinLevelEventListener;
    // Internal handlers (mirrored behavior)
    //public BattlefieldInventory inputHandler = new BattlefieldInventory(3, true);
    public BattlefieldInventory outputHandler = new BattlefieldInventory(25, false);
    // External capability-exposed handler (buffered/cached writes)
    public BufferedOutputHandler bufferedOutputHandler = new BufferedOutputHandler(this.outputHandler);
    public boolean outputDirty = false;
    public ItemStacksResourceHandler inputSoulHandler = new ItemStacksResourceHandler(1) {
        @Override
        public boolean isValid(int slot, ItemResource resource) {
            return resource.toStack().has(DataComponents.ENTITY_DATA);
        }

        @Override
        protected void onContentsChanged(int slot, ItemStack previousContents) {
            DimensionalBattlefieldBlockEntity.this.setChanged();
        }
    };
    public ItemStacksResourceHandler inputWeaponHandler = new ItemStacksResourceHandler(1) {
        @Override
        public boolean isValid(int slot, ItemResource resource) {
            return resource.toStack().has(DataComponents.ATTRIBUTE_MODIFIERS);
        }

        @Override
        protected void onContentsChanged(int slot, ItemStack previousContents) {
            DimensionalBattlefieldBlockEntity.this.setChanged();
        }
    };
    public ItemStacksResourceHandler inputFuelHandler = new ItemStacksResourceHandler(1) {
        @Override
        public boolean isValid(int slot, ItemResource resource) {
            return resource.toStack().has(OccultismDataComponents.SOUL_VALUE);
        }

        @Override
        protected void onContentsChanged(int slot, ItemStack previousContents) {
            DimensionalBattlefieldBlockEntity.this.markNetworkDirty();
            DimensionalBattlefieldBlockEntity.this.setChanged();
        }
    };
    public ResourceHandler<ItemResource> inputHandler = new CombinedResourceHandler<>(this.inputSoulHandler, this.inputWeaponHandler, this.inputFuelHandler);
    // Combined handler now uses the buffered output to propagate safety
    public ResourceHandler<ItemResource> combinedHandler = new CombinedResourceHandler<>(this.inputHandler, this.bufferedOutputHandler);
    public ResourceHandler<ItemResource> jadeWrapper = RangedResourceHandler.of(this.combinedHandler, 0, 8);
    private Holder<Enchantment> UNUSING;
    private Holder<Enchantment> SHARPNESS;
    private Holder<Enchantment> SMITE;
    private Holder<Enchantment> BANE_OF_ARTHROPODS;
    private Holder<Enchantment> IMPALING;
    private boolean cachedEnchantment = false;
    private FakePlayer cachedFakePlayer;
    private ItemStack cachedSoul;
    private ItemStack cachedWeapon;
    private LivingEntity storedLivingEntity = null;
    private LootTable storedLootTable = null;
    private int xpStored;
    private boolean wait;
    private ResourceHandler<ItemResource> handlerBelow = null;
    private BlockState cachedStateBelow = null;

    public DimensionalBattlefieldBlockEntity(BlockPos worldPos, BlockState state) {
        super(OccultismBlockEntities.DIMENSIONAL_BATTLEFIELD.get(), worldPos, state);
        this.entityJoinLevelEventListener = this::itemEntityConsumer;
    }

    private static ItemStack getStack(ResourceHandler<ItemResource> handler, int slot) {
        return handler.getResource(slot).toStack(handler.getAmountAsInt(slot));
    }

    private static void setStack(ItemStacksResourceHandler handler, int slot, ItemStack stack) {
        handler.set(slot, ItemResource.of(stack), stack.getCount());
    }

    @Override
    public void preRemoveSideEffects(BlockPos pos, BlockState state) {
        StorageUtil.dropInventoryItems(this);
        super.preRemoveSideEffects(pos, state);
    }

    @Override
    public @NotNull Component getDisplayName() {
        return Component.literal(Objects.requireNonNull(BuiltInRegistries.BLOCK_ENTITY_TYPE.getKey(this.getType())).getPath());
    }

    @Override
    public void loadAdditional(ValueInput input) {
        super.loadAdditional(input);
        this.inputSoulHandler.deserialize(input.childOrEmpty("inputSoulHandler"));
        this.inputWeaponHandler.deserialize(input.childOrEmpty("inputWeaponHandler"));
        this.inputFuelHandler.deserialize(input.childOrEmpty("inputFuelHandler"));
        this.outputHandler.deserialize(input.childOrEmpty("outputHandler"));
    }

    @Override
    protected void saveAdditional(ValueOutput output) {
        this.inputSoulHandler.serialize(output.child("inputSoulHandler"));
        this.inputWeaponHandler.serialize(output.child("inputWeaponHandler"));
        this.inputFuelHandler.serialize(output.child("inputFuelHandler"));
        this.outputHandler.serialize(output.child("outputHandler"));
        super.saveAdditional(output);
    }

    @Override
    public void loadNetwork(ValueInput input) {
        super.loadNetwork(input);
        this.mobHealth = input.getIntOr("mobHealth", 0);
        this.maxMobLife = input.getIntOr("maxMobLife", 0);
        this.hitTimer = input.getIntOr("hitTimer", 0);
        this.maxHitTimer = input.getIntOr("maxHitTimer", 0);
        this.soulValue = input.getIntOr("soulValue", 0);
    }

    @Override
    public void saveNetwork(ValueOutput output) {
        output.putInt("mobHealth", this.mobHealth);
        output.putInt("maxMobLife", this.maxMobLife);
        output.putInt("hitTimer", this.hitTimer);
        output.putInt("maxHitTimer", this.maxHitTimer);
        output.putInt("soulValue", this.soulValue);
        super.saveNetwork(output);
    }

    public void tick() {
        Level level = this.level;
        if (level == null)
            return;

        if (level.isClientSide()) {
            if (this.mobHealth > 0 && level.getGameTime() % 50 == 0) {
                level.addParticle(
                        ParticleTypes.ANGRY_VILLAGER,
                        this.worldPosition.getX() + 0.5f,
                        this.worldPosition.getY(),
                        this.worldPosition.getZ() + 0.5f,
                        0.0D, 0.0D, 0.0D
                );
            }
            return;
        }

        if (level.hasNeighborSignal(this.getBlockPos()))
            return;

        ItemStack soul = getStack(this.inputSoulHandler, 0);
        ItemStack weapon = getStack(this.inputWeaponHandler, 0);
        ItemStack fuel = getStack(this.inputFuelHandler, 0);

        if (soul.isEmpty() || weapon.isEmpty()) {
            this.mobHealth = 0;
            this.wait = true;
            return;
        }

        if (!this.cachedEnchantment)
            this.setCachedEnchantment();

        if (weapon.getDamageValue() >= weapon.getMaxDamage() - 6 &&
                this.UNUSING != null && weapon.getEnchantmentLevel(this.UNUSING) > 0) {
            this.mobHealth = 0;
            this.wait = true;
            return;
        }

        if (this.storedLivingEntity == null
                || this.cachedSoul == null
                || this.cachedWeapon == null
                || !ItemStack.isSameItemSameComponents(this.cachedSoul, soul)
                || !ItemStack.isSameItemSameComponents(this.cachedWeapon, weapon)) {
            this.setStoredLivingEntity(soul, (ServerLevel) level);
            this.setMaxMobLife();
            this.cachedSoul = soul.copy();
            this.cachedWeapon = weapon.copy();
        }

        int fuelValue = fuel.getOrDefault(OccultismDataComponents.SOUL_VALUE, 0);

        if ((fuel.isEmpty() && !soul.has(OccultismDataComponents.SOUL_VALUE))
                || fuelValue * fuel.getCount() < this.soulValue) {
            this.mobHealth = 0;
            this.wait = true;
            return;
        }

        if (this.wait) { //Reset the process, start from zero in next tick
            this.wait = false;
            this.mobHealth = this.maxMobLife;
            return;
        }

        if (--this.hitTimer > 0)
            return;

        this.hitTimer = this.maxHitTimer;
        this.mobHealth--;

        if (level.getRandom().nextFloat() < this.BUTCHER_HURT_CHANCE) {
            weapon.hurtAndBreak(1, (ServerLevel) level, null, item -> {
            });
            setStack(this.inputWeaponHandler, 0, weapon);
        }

        if (this.mobHealth <= 0) {
            int luck = fuel.getOrDefault(OccultismDataComponents.LUCK_VALUE, 1);

            if (!soul.has(OccultismDataComponents.SOUL_VALUE)) {
                fuel.shrink(1 + (this.soulValue / Math.max(fuelValue, 1)));
                setStack(this.inputFuelHandler, 0, fuel);
            }

            this.defeat(soul, luck);
            this.mobHealth = this.maxMobLife;
        }

        this.markNetworkDirty();

        if (this.outputDirty) {
            this.setChanged();
            this.outputDirty = false;
        }
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int id, @NotNull Inventory playerInventory, @NotNull Player player) {
        return new DimensionalBattlefieldContainer(id, playerInventory, this);
    }

    public void defeat(ItemStack soul, int luck) {
        if (this.level == null || this.level.isClientSide())
            return;

        if (this.level.getRandom().nextFloat() < soul.getOrDefault(OccultismDataComponents.FAIL_CHANCE, 0F))
            return;

        LivingEntity entity = this.storedLivingEntity;
        int rolls = soul.getOrDefault(OccultismDataComponents.ROLLS_PER_OPERATION, 1);
        if (entity.getType().builtInRegistryHolder().is(EntityTypes.BOSSES)) {
            rolls = 1;
        }
        if (entity instanceof PossessedMob possessedMob) {
            rolls = Math.max(1, (int) (rolls / 3F));
            EntityType<?> baseMob = possessedMob.basedMob();
            if (baseMob != null)
                entity = (LivingEntity) baseMob.create(this.level, EntitySpawnReason.MOB_SUMMONED);
        }
        if (entity == null)
            return;

        luck = Math.min(rolls, DEFAULT_MAX_LUCK); //Cap luck

        rolls = rolls + (int) (luck * luck / 100F);
        if (RandomSource.create().nextIntBetweenInclusive(0, 99) < (luck * luck) % 100)
            rolls++;

        if (this.level.getRandom().nextFloat() < soul.getOrDefault(OccultismDataComponents.CONSUME_CHANCE, 0F) / luck)
            soul.shrink(1);
        setStack(this.inputSoulHandler, 0, soul);

        ResourceHandler<ItemResource> currentHandler = this.getCurrentHandler();
        if (this.xpStored > 9) {
            int bottles = (int) (this.xpStored / 10F);
            this.xpStored -= bottles * 10;
            ItemStack bottleStack = new ItemStack(Items.EXPERIENCE_BOTTLE, bottles);
            ItemTransferUtil.insertItemStacked(currentHandler, bottleStack, false);
        }

        //TODO: Custom drops with json recipes (planned for mc 26.1)
        if (entity.getType().equals(EntityType.ENDER_DRAGON))
            ItemTransferUtil.insertItemStacked(currentHandler, Items.DRAGON_EGG.getDefaultInstance(), false);

        FakePlayer fakePlayer = this.getFakePlayer();
        if (entity.getType().builtInRegistryHolder().is(Entities.FORCE_KILL_SIMULATION)) {
            NeoForge.EVENT_BUS.addListener(this.entityJoinLevelEventListener);
            for (int i = 0; i < rolls; i++) {
                Entity clone = entity.getType().create(this.level, EntitySpawnReason.MOB_SUMMONED);
                if (clone != null) {
                    clone.snapTo(this.getBlockPos().getX(), -100.0, this.getBlockPos().getZ(), 0.0f, 0.0f);
                    clone.hurt(this.level.damageSources().playerAttack(fakePlayer), Integer.MAX_VALUE);
                }
            }
            NeoForge.EVENT_BUS.unregister(this.entityJoinLevelEventListener);
            return;
        }

        this.xpStored += entity.getExperienceReward((ServerLevel) this.level, fakePlayer);
        LootParams lootparams = this.setLootParams(entity, luck);
        if (this.storedLootTable != null) {
            for (int i = 0; i < rolls; i++) {
                ObjectArrayList<ItemStack> loot = this.storedLootTable.getRandomItems(lootparams);
                for (ItemStack itemStack : loot)
                    ItemTransferUtil.insertItemStacked(currentHandler, itemStack, false);
            }
        }
    }

    public int getRedstoneSignal() {
        ItemStack weapon = getStack(this.inputWeaponHandler, 0);
        int signalI = 0;
        int signalO = 0;
        if (!weapon.isEmpty()) {
            signalI = (int) (15 * ((float) weapon.getDamageValue() / Math.max(weapon.getMaxDamage() - 1, 1)));
            signalO += 2;
        }
        if (!this.inputSoulHandler.getResource(0).isEmpty())
            signalO += 3;
        if (!this.inputFuelHandler.getResource(0).isEmpty())
            signalO += 5;
        for (int i = 0; i < 25; i++) {
            if (!this.outputHandler.getResource(i).isEmpty())
                signalO += 2;
        }
        return Math.max(signalI, signalO / 4);
    }

    public void setStoredLivingEntity(ItemStack stack, ServerLevel level) {
        this.storedLivingEntity = null;
        if (!stack.isEmpty() && stack.has(DataComponents.ENTITY_DATA) && this.level != null) {
            // Get entity type directly from TypedEntityData - the "id" field is stripped from the NBT
            // by TypedEntityData.of() since the type is already stored in the TypedEntityData itself
            var typedEntityData = Objects.requireNonNull(stack.get(DataComponents.ENTITY_DATA));
            EntityType<?> entityType = typedEntityData.type();
            Entity tempEntity = entityType.create(this.level, EntitySpawnReason.MOB_SUMMONED);
            if (tempEntity instanceof LivingEntity livingEntity)
                this.storedLivingEntity = livingEntity;
        }
        if (this.storedLivingEntity != null) {
            if (this.storedLivingEntity instanceof PossessedMob possessed && !stack.is(OccultismItems.TRINITY_GEM_ITEM)) {
                EntityType<?> baseMob = possessed.basedMob();
                if (baseMob != null && baseMob.create(level, EntitySpawnReason.MOB_SUMMONED) instanceof LivingEntity entity) {
                    this.storedLootTable = entity.getLootTable().map(key -> level.getServer().reloadableRegistries().getLootTable(key)).orElse(null);
                    return;
                }
            }
            this.storedLootTable = this.storedLivingEntity.getLootTable().map(key -> level.getServer().reloadableRegistries().getLootTable(key)).orElse(null);
        } else {
            this.storedLootTable = null;
        }
    }

    public LootParams setLootParams(LivingEntity entity, float luck) {
        ServerLevel serverLevel = (ServerLevel) this.level;
        FakePlayer fakePlayer = this.getFakePlayer();
        ItemStack weapon = getStack(this.inputWeaponHandler, 0);

        assert serverLevel != null;
        return new Builder(serverLevel)
                .withParameter(LootContextParams.THIS_ENTITY, entity)
                .withParameter(LootContextParams.ORIGIN, Vec3.atCenterOf(this.worldPosition))
                .withParameter(LootContextParams.DAMAGE_SOURCE, fakePlayer.damageSources().generic())
                .withParameter(LootContextParams.LAST_DAMAGE_PLAYER, fakePlayer)
                .withOptionalParameter(LootContextParams.ATTACKING_ENTITY, fakePlayer)
                .withOptionalParameter(LootContextParams.DIRECT_ATTACKING_ENTITY, fakePlayer)
                .withOptionalParameter(LootContextParams.TOOL, weapon)
                .withLuck(luck)
                .create(LootContextParamSets.ENTITY);
    }

    public void setMaxMobLife() {
        if (this.storedLivingEntity == null || this.level == null)
            return;

        ItemStack soul = getStack(this.inputSoulHandler, 0);
        ItemStack weapon = getStack(this.inputWeaponHandler, 0);
        if (soul.isEmpty() || weapon.isEmpty())
            return;
        int health = DEFAULT_MAX_TIME;
        this.maxMobLife = health;
        if (soul.has(DataComponents.ENTITY_DATA)) {
            health = (int) this.storedLivingEntity.getMaxHealth();
            CompoundTag entityData = Objects.requireNonNull(soul.get(DataComponents.ENTITY_DATA)).copyTagWithoutId();
            if (entityData.contains("attributes")) {
                ListTag attrs = entityData.getListOrEmpty("attributes");
                for (int i = 0; i < attrs.size(); i++) {
                    CompoundTag attr = attrs.getCompoundOrEmpty(i);
                    if ("minecraft:generic.max_health".equals(attr.getStringOr("id", ""))) {
                        health = (int) attr.getDoubleOr("base", 0.0);
                    }
                }
            }
            this.soulValue = soul.has(OccultismDataComponents.SOUL_VALUE) ? 0 : health;
            health *= Occultism.SERVER_CONFIG.itemSettings.butcherLifeMultiplier.getAsInt();
        }

        double attackSpeed = 4.0;
        if (weapon.has(DataComponents.ATTRIBUTE_MODIFIERS)) {
            var mods = weapon.get(DataComponents.ATTRIBUTE_MODIFIERS);
            if (mods != null)
                for (var entry : mods.modifiers()) {
                    var modifier = entry.modifier();
                    if (entry.attribute().is(Objects.requireNonNull(Attributes.ATTACK_SPEED.getKey())))
                        attackSpeed += modifier.amount();
                }
        }
        double attackDamage = 1.0;
        if (weapon.has(DataComponents.ATTRIBUTE_MODIFIERS)) {
            var mods = weapon.get(DataComponents.ATTRIBUTE_MODIFIERS);
            if (mods != null)
                for (var entry : mods.modifiers()) {
                    var modifier = entry.modifier();
                    if (entry.attribute().is(Objects.requireNonNull(Attributes.ATTACK_DAMAGE.getKey())))
                        attackDamage += modifier.amount();
                }
        }
        if (weapon.isEnchanted()) {
            attackDamage += weapon.getEnchantmentLevel(this.SHARPNESS);
            if (this.storedLivingEntity.getType().builtInRegistryHolder().is(EntityTypeTags.SENSITIVE_TO_SMITE))
                attackDamage += 2.5 * weapon.getEnchantmentLevel(this.SMITE);
            if (this.storedLivingEntity.getType().builtInRegistryHolder().is(EntityTypeTags.SENSITIVE_TO_BANE_OF_ARTHROPODS))
                attackDamage += 2.5 * weapon.getEnchantmentLevel(this.BANE_OF_ARTHROPODS);
            if (this.storedLivingEntity.getType().builtInRegistryHolder().is(EntityTypeTags.SENSITIVE_TO_IMPALING))
                attackDamage += 2.5 * weapon.getEnchantmentLevel(this.IMPALING);
        }
        if (weapon.is(OccultismTags.Items.TOOLS_KNIFE_IESNIUM) &&
                (this.storedLivingEntity.getType().builtInRegistryHolder().is(Entities.HEALED_BY_DEMONS_DREAM_FRUIT)
                        || this.storedLivingEntity.getType().builtInRegistryHolder().is(EntityTypes.BOSSES))) {
            attackDamage *= 3;
        }
        if (attackSpeed == 0 || attackDamage == 0)
            return;
        int life = Math.max((int) (health / attackDamage), 1);
        int hitSpeed = (int) (40 / attackSpeed);
        this.maxMobLife = life;
        this.mobHealth = life;
        this.maxHitTimer = hitSpeed;
        this.hitTimer = hitSpeed;
    }

    @SubscribeEvent
    public void itemEntityConsumer(EntityJoinLevelEvent event) {
        Level level = event.getLevel();
        if (level.isClientSide())
            return;

        Entity entity = event.getEntity();
        if (entity.getX() != this.getBlockPos().getX() || entity.getY() != -100 || entity.getZ() != this.getBlockPos().getZ())
            return;

        if (entity instanceof ItemEntity item) {
            ResourceHandler<ItemResource> currentHandler = this.getCurrentHandler();
            ItemTransferUtil.insertItemStacked(currentHandler, item.getItem(), false);
            return;
        }
        if (entity instanceof ExperienceOrb orb) {
            this.xpStored += orb.getValue();
        }
    }

    private FakePlayer getFakePlayer() {
        if (this.cachedFakePlayer == null) {
            this.cachedFakePlayer = FakePlayerFactory.getMinecraft((ServerLevel) this.level);
        }
        return this.cachedFakePlayer;
    }

    private void setCachedEnchantment() {
        if (this.level != null) {
            this.UNUSING = this.level.registryAccess().lookupOrThrow(Registries.ENCHANTMENT).get(EVILCRAFT_UNUSING_ENCHANTMENT).orElse(null);
            this.SHARPNESS = this.level.holderOrThrow(Enchantments.SHARPNESS);
            this.SMITE = this.level.holderOrThrow(Enchantments.SMITE);
            this.BANE_OF_ARTHROPODS = this.level.holderOrThrow(Enchantments.BANE_OF_ARTHROPODS);
            this.IMPALING = this.level.holderOrThrow(Enchantments.IMPALING);
            this.cachedEnchantment = true;
        }
    }

    public void updateBelowBlock() {
        if (this.level != null) {
            var pos = this.getBlockPos().below(2);
            this.cachedStateBelow = this.level.getBlockState(pos);
            this.handlerBelow = this.level.getCapability(Item.BLOCK, pos, this.cachedStateBelow, null, Direction.UP);
        }
    }

    private ResourceHandler<ItemResource> getCurrentHandler() {
        if (this.level == null)
            return null;

        if (this.level.getBlockState(this.getBlockPos().below()).is(OccultismBlocks.DIMENSIONAL_EXTRACTOR)) {
            if (this.cachedStateBelow != this.level.getBlockState(this.getBlockPos().below(2)))
                this.updateBelowBlock();
            return this.handlerBelow == null ? this.outputHandler : this.handlerBelow;
        }

        return this.outputHandler;
    }

    // region Inner Classes
    public class BattlefieldInventory extends ItemStacksResourceHandler {
        private final boolean isInput;

        public BattlefieldInventory(int size, boolean isInput) {
            super(size);
            this.isInput = isInput;
        }

        @Override
        public boolean isValid(int slot, ItemResource resource) {
            return !this.isInput || this.mayPlace(resource.toStack(), slot);
        }

        public boolean mayPlace(ItemStack stack, int slot) {
            if (slot == 1)
                return stack.has(DataComponents.ENTITY_DATA);
            if (slot == 2)
                return stack.has(DataComponents.ATTRIBUTE_MODIFIERS);

            return stack.has(OccultismDataComponents.SOUL_VALUE);
        }

        @Override
        protected void onContentsChanged(int slot, ItemStack previousContents) {
            DimensionalBattlefieldBlockEntity.this.setChanged();
        }

        public void setStackInSlotDirect(int slot, ItemStack stack) {
            this.set(slot, ItemResource.of(stack), stack.getCount());
        }
    }

    public class BufferedOutputHandler implements ResourceHandler<ItemResource> {
        private final BattlefieldInventory internal;
        private final RootCommitJournal outputDirtyJournal = new RootCommitJournal(() -> DimensionalBattlefieldBlockEntity.this.outputDirty = true);

        public BufferedOutputHandler(BattlefieldInventory internal) {
            this.internal = internal;
        }

        @Override
        public int size() {
            return this.internal.size();
        }

        @Override
        public @NotNull ItemResource getResource(int slot) {
            return this.internal.getResource(slot);
        }

        @Override
        public long getAmountAsLong(int slot) {
            return this.internal.getAmountAsLong(slot);
        }

        @Override
        public long getCapacityAsLong(int slot, ItemResource resource) {
            return this.internal.getCapacityAsLong(slot, resource);
        }

        @Override
        public boolean isValid(int slot, ItemResource resource) {
            return this.internal.isValid(slot, resource);
        }

        @Override
        public int insert(int slot, @NotNull ItemResource resource, int amount, TransactionContext transaction) {
            int result = this.internal.insert(slot, resource, amount, transaction);
            if (result > 0) {
                this.outputDirtyJournal.updateSnapshots(transaction);
            }
            return result;
        }

        @Override
        public int extract(int slot, @NotNull ItemResource resource, int amount, TransactionContext transaction) {
            int result = this.internal.extract(slot, resource, amount, transaction);
            if (result > 0) {
                this.outputDirtyJournal.updateSnapshots(transaction);
            }
            return result;
        }
    }
    // endregion Inner Classes
}
