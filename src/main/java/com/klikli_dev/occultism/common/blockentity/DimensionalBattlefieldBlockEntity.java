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
import com.klikli_dev.occultism.registry.OccultismBlockEntities;
import com.klikli_dev.occultism.registry.OccultismBlocks;
import com.klikli_dev.occultism.registry.OccultismDataComponents;
import com.klikli_dev.occultism.registry.OccultismTags;
import com.klikli_dev.occultism.util.EntityUtil;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.LivingEntity;
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
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.util.FakePlayer;
import net.neoforged.neoforge.common.util.FakePlayerFactory;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.IItemHandlerModifiable;
import net.neoforged.neoforge.items.ItemHandlerHelper;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.neoforged.neoforge.items.wrapper.CombinedInvWrapper;
import net.neoforged.neoforge.items.wrapper.RangedWrapper;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.function.Consumer;

public class DimensionalBattlefieldBlockEntity extends NetworkedBlockEntity implements MenuProvider {

    private final float BUTCHER_HURT_CHANCE = (float) Occultism.SERVER_CONFIG.itemSettings.butcherHurtChance.getAsDouble();
    private static final int DEFAULT_MAX_TIME = 20 * 20 * 20;
    private static final ResourceKey<Enchantment> EVILCRAFT_UNUSING_ENCHANTMENT = ResourceKey.create(
                            Registries.ENCHANTMENT, ResourceLocation.parse("evilcraft:unusing"));
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
    public int mobHealth;
    public int maxMobLife;
    public int hitTimer;
    public int maxHitTimer;
    public int soulValue;
    private int xpStored;
    private IItemHandler handlerBelow = null;
    private BlockState cachedStateBelow = null;
    public Consumer<EntityJoinLevelEvent> entityJoinLevelEventListener;

    // Internal handlers (mirrored behavior)
    //public BattlefieldInventory inputHandler = new BattlefieldInventory(3, true);
    public BattlefieldInventory outputHandler = new BattlefieldInventory(25, false);

    // External capability-exposed handler (buffered/cached writes)
    public BufferedOutputHandler bufferedOutputHandler = new BufferedOutputHandler(outputHandler);

    public boolean outputDirty = false;

    public ItemStackHandler inputSoulHandler = new ItemStackHandler(1) {
        @Override
        public @NotNull ItemStack insertItem(int slot, @NotNull ItemStack stack, boolean simulate) {
            return stack.has(DataComponents.ENTITY_DATA) ? insertSoul(slot, stack, simulate) : stack;
        }
        private ItemStack insertSoul(int slot, ItemStack stack, boolean simulate) {
            return super.insertItem(slot, stack, simulate);
        }
        @Override
        protected void onContentsChanged(int slot) {
            DimensionalBattlefieldBlockEntity.this.setChanged();
        }
    };

    public ItemStackHandler inputWeaponHandler = new ItemStackHandler(1) {
        @Override
        public @NotNull ItemStack insertItem(int slot, @NotNull ItemStack stack, boolean simulate) {
            return stack.has(DataComponents.ATTRIBUTE_MODIFIERS) ? super.insertItem(slot, stack, simulate) : stack;
        }
        @Override
        protected void onContentsChanged(int slot) {
            DimensionalBattlefieldBlockEntity.this.setChanged();
        }
    };

    public ItemStackHandler inputFuelHandler = new ItemStackHandler(1) {
        @Override
        public @NotNull ItemStack insertItem(int slot, @NotNull ItemStack stack, boolean simulate) {
            return stack.has(OccultismDataComponents.SOUL_VALUE) ? super.insertItem(slot, stack, simulate) : stack;
        }
        @Override
        protected void onContentsChanged(int slot) {
            DimensionalBattlefieldBlockEntity.this.markNetworkDirty();
            DimensionalBattlefieldBlockEntity.this.setChanged();
        }
    };

    public CombinedInvWrapper inputHandler = new CombinedInvWrapper(this.inputSoulHandler, this.inputWeaponHandler, this.inputFuelHandler);
    // Combined handler now uses the buffered output to propagate safety
    public CombinedInvWrapper combinedHandler = new CombinedInvWrapper(this.inputHandler, this.bufferedOutputHandler);
    public RangedWrapper jadeWrapper = new RangedWrapper(this.combinedHandler, 0, 8);

    public DimensionalBattlefieldBlockEntity(BlockPos worldPos, BlockState state) {
        super(OccultismBlockEntities.DIMENSIONAL_BATTLEFIELD.get(), worldPos, state);
        this.entityJoinLevelEventListener = this::itemEntityConsumer;
    }

    @Override
    public @NotNull Component getDisplayName() {
        return Component.literal(Objects.requireNonNull(BuiltInRegistries.BLOCK_ENTITY_TYPE.getKey(this.getType())).getPath());
    }

    @Override
    public void loadAdditional(CompoundTag compound, HolderLookup.Provider provider) {
        super.loadAdditional(compound, provider);
        this.inputSoulHandler.deserializeNBT(provider, compound.getCompound("inputSoulHandler"));
        this.inputWeaponHandler.deserializeNBT(provider, compound.getCompound("inputWeaponHandler"));
        this.inputFuelHandler.deserializeNBT(provider, compound.getCompound("inputFuelHandler"));
        this.outputHandler.deserializeNBT(provider, compound.getCompound("outputHandler"));
    }

    @Override
    protected void saveAdditional(CompoundTag compound, HolderLookup.Provider provider) {
        compound.put("inputSoulHandler", this.inputSoulHandler.serializeNBT(provider));
        compound.put("inputWeaponHandler", this.inputWeaponHandler.serializeNBT(provider));
        compound.put("inputFuelHandler", this.inputFuelHandler.serializeNBT(provider));
        compound.put("outputHandler", this.outputHandler.serializeNBT(provider));
        super.saveAdditional(compound, provider);
    }

    @Override
    public void loadNetwork(CompoundTag compound, HolderLookup.Provider provider) {
        super.loadNetwork(compound, provider);
        this.mobHealth = compound.getInt("mobHealth");
        this.maxMobLife = compound.getInt("maxMobLife");
        this.hitTimer = compound.getInt("hitTimer");
        this.maxHitTimer = compound.getInt("maxHitTimer");
        this.soulValue = compound.getInt("soulValue");
    }

    @Override
    public CompoundTag saveNetwork(CompoundTag compound, HolderLookup.Provider provider) {
        compound.putInt("mobHealth", this.mobHealth);
        compound.putInt("maxMobLife", this.maxMobLife);
        compound.putInt("hitTimer", this.hitTimer);
        compound.putInt("maxHitTimer", this.maxHitTimer);
        compound.putInt("soulValue", this.soulValue);
        return super.saveNetwork(compound, provider);
    }

    public void tick() {
        Level level = this.level;
        if (level == null)
            return;

        if (level.isClientSide) {
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

        ItemStack soul = inputSoulHandler.getStackInSlot(0);
        ItemStack weapon = inputWeaponHandler.getStackInSlot(0);
        ItemStack fuel = inputFuelHandler.getStackInSlot(0);

        if (soul.isEmpty() || weapon.isEmpty()) {
            this.mobHealth = 0;
            return;
        }

        if (!cachedEnchantment)
            this.setCachedEnchantment();

        if (weapon.getDamageValue() >= weapon.getMaxDamage() - 6 &&
                UNUSING != null && weapon.getEnchantmentLevel(UNUSING) > 0) {
            this.mobHealth = 0;
            return;
        }

        if (storedLivingEntity == null || this.cachedSoul != soul || this.cachedWeapon != weapon) {
                setStoredLivingEntity(soul, (ServerLevel) level);
                setMaxMobLife();
                this.cachedSoul = soul;
                this.cachedWeapon = weapon;
        }

        int fuelValue = fuel.getOrDefault(OccultismDataComponents.SOUL_VALUE, 0);

        if ((fuel.isEmpty() && !soul.has(OccultismDataComponents.SOUL_VALUE))
                || fuelValue * fuel.getCount() < this.soulValue) {
            this.mobHealth = 0;
            return;
        }

        if (--hitTimer > 0)
            return;

        hitTimer = maxHitTimer;
        mobHealth--;

        if (level.random.nextFloat() < BUTCHER_HURT_CHANCE) {
            weapon.hurtAndBreak(1, (ServerLevel) level, null, item -> {});
        }

        if (mobHealth <= 0) {
            int luck = fuel.getOrDefault(OccultismDataComponents.LUCK_VALUE, 1);

            if (!soul.has(OccultismDataComponents.SOUL_VALUE))
                fuel.shrink(1 + (soulValue / Math.max(fuelValue, 1)));

            defeat(soul, luck);
            mobHealth = maxMobLife;
        }

        this.markNetworkDirty();
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
        if (entity.getType().is(Tags.EntityTypes.BOSSES)) {
            rolls = 1;
        }
        if (entity instanceof PossessedMob possessedMob) {
            rolls = Math.max(1, (int) (rolls/3F) );
            EntityType<?> baseMob = possessedMob.basedMob();
            if (baseMob != null)
                entity = (LivingEntity) baseMob.create(this.level);
        }
        if (entity == null)
            return;

        rolls = rolls + (int) (luck*luck/100F);
        if (RandomSource.create().nextIntBetweenInclusive(0, 99) < (luck*luck) % 100)
            rolls++;

        if (this.level.getRandom().nextFloat() < soul.getOrDefault(OccultismDataComponents.CONSUME_CHANCE, 0F)/luck)
            soul.shrink(1);

        IItemHandler currentHandler = this.getCurrentHandler();
        if (this.xpStored > 9) {
            int bottles = (int) (this.xpStored/10F);
            this.xpStored -= bottles*10;
            ItemStack bottleStack = new ItemStack(Items.EXPERIENCE_BOTTLE, bottles);
            ItemHandlerHelper.insertItemStacked(currentHandler, bottleStack, false);
        }

        FakePlayer fakePlayer = this.getFakePlayer();
        if (entity.getType().is(OccultismTags.Entities.FORCE_KILL_SIMULATION)) {
            NeoForge.EVENT_BUS.addListener(this.entityJoinLevelEventListener);
            for (int i = 0; i < rolls; i++) {
                Entity clone = entity.getType().create(this.level);
                if (clone != null) {
                    clone.moveTo(this.getBlockPos().getX(), -100, this.getBlockPos().getZ());
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
                    ItemHandlerHelper.insertItemStacked(currentHandler, itemStack, false);
            }
        }
    }

    public int getRedstoneSignal() {
        ItemStack weapon = this.inputWeaponHandler.getStackInSlot(0);
        int signalI = 0;
        int signalO = 0;
        if (!weapon.isEmpty()) {
            signalI = (int) (15 * ((float) weapon.getDamageValue() / Math.max(weapon.getMaxDamage() - 1, 1)));
            signalO += 2;
        }
        if (!this.inputSoulHandler.getStackInSlot(0).isEmpty())
            signalO += 3;
        if (!this.inputFuelHandler.getStackInSlot(0).isEmpty())
            signalO += 5;
        for (int i = 0; i < 25; i++) {
            if (!this.outputHandler.getStackInSlot(i).isEmpty())
                signalO += 2;
        }
        return Math.max(signalI, signalO/4);
    }

    public void setStoredLivingEntity(ItemStack stack, ServerLevel level) {
        if (!stack.isEmpty() && stack.has(DataComponents.ENTITY_DATA) && this.level != null) {
            CompoundTag entityData = Objects.requireNonNull(stack.get(DataComponents.ENTITY_DATA)).copyTag();
            this.storedLivingEntity = (LivingEntity) EntityUtil.entityTypeFromNbt(entityData).create(this.level);
        } else {
            this.storedLivingEntity = null;
        }
        if (this.storedLivingEntity != null) {
            this.storedLootTable = level.getServer().reloadableRegistries().getLootTable(this.storedLivingEntity.getLootTable());
        } else {
            this.storedLootTable = null;
        }
    }

    public LootParams setLootParams(LivingEntity entity, float luck) {
        ServerLevel serverLevel = (ServerLevel) this.level;
        FakePlayer fakePlayer = this.getFakePlayer();
        ItemStack weapon = this.inputWeaponHandler.getStackInSlot(0);

        assert serverLevel != null;
        return new LootParams.Builder(serverLevel)
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

        ItemStack soul = this.inputSoulHandler.getStackInSlot(0);
        ItemStack weapon = this.inputWeaponHandler.getStackInSlot(0);
        if (soul.isEmpty() || weapon.isEmpty())
            return;
        int health = DEFAULT_MAX_TIME;
        this.maxMobLife = health;
        if (soul.has(DataComponents.ENTITY_DATA)) {
            health = (int) this.storedLivingEntity.getMaxHealth();
            CompoundTag entityData = Objects.requireNonNull(soul.get(DataComponents.ENTITY_DATA)).copyTag();
            if (entityData.contains("attributes")) {
                ListTag attrs = entityData.getList("attributes", Tag.TAG_COMPOUND);
                for (int i = 0; i < attrs.size(); i++) {
                    CompoundTag attr = attrs.getCompound(i);
                    if ("minecraft:generic.max_health".equals(attr.getString("id"))) {
                        health = (int) attr.getDouble("base");
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
            attackDamage += weapon.getEnchantmentLevel(SHARPNESS);
            if (this.storedLivingEntity.getType().is(EntityTypeTags.SENSITIVE_TO_SMITE))
                attackDamage += 2.5*weapon.getEnchantmentLevel(SMITE);
            if (this.storedLivingEntity.getType().is(EntityTypeTags.SENSITIVE_TO_BANE_OF_ARTHROPODS))
                attackDamage += 2.5*weapon.getEnchantmentLevel(BANE_OF_ARTHROPODS);
            if (this.storedLivingEntity.getType().is(EntityTypeTags.SENSITIVE_TO_IMPALING))
                attackDamage += 2.5*weapon.getEnchantmentLevel(IMPALING);
        }
        if (weapon.is(OccultismTags.Items.TOOLS_KNIFE_IESNIUM) &&
                (this.storedLivingEntity.getType().is(OccultismTags.Entities.HEALED_BY_DEMONS_DREAM_FRUIT)
                || this.storedLivingEntity.getType().is(Tags.EntityTypes.BOSSES))) {
            attackDamage *= 3;
        }
        if (attackSpeed == 0 || attackDamage == 0)
            return;
        int life =  Math.max((int) (health / attackDamage), 1);
        int hitSpeed =  (int) (40 / attackSpeed);
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
            IItemHandler currentHandler = this.getCurrentHandler();
            ItemHandlerHelper.insertItemStacked(currentHandler, item.getItem(), false);
            return;
        }
        if (entity instanceof ExperienceOrb orb) {
            this.xpStored += orb.getValue();
        }
    }

    private FakePlayer getFakePlayer() {
        if (cachedFakePlayer == null) {
            cachedFakePlayer = FakePlayerFactory.getMinecraft((ServerLevel) this.level);
        }
        return cachedFakePlayer;
    }

    private void setCachedEnchantment() {
        if (this.level != null) {
            UNUSING = this.level.registryAccess().lookupOrThrow(Registries.ENCHANTMENT).get(EVILCRAFT_UNUSING_ENCHANTMENT).orElse(null);
            SHARPNESS = this.level.holderOrThrow(Enchantments.SHARPNESS);
            SMITE = this.level.holderOrThrow(Enchantments.SMITE);
            BANE_OF_ARTHROPODS = this.level.holderOrThrow(Enchantments.BANE_OF_ARTHROPODS);
            IMPALING = this.level.holderOrThrow(Enchantments.IMPALING);
            cachedEnchantment = true;
        }
    }

    public void updateBelowBlock() {
        if (this.level != null) {
            this.cachedStateBelow = this.level.getBlockState(this.getBlockPos().below(2));
            this.handlerBelow = this.level.getCapability(Capabilities.ItemHandler.BLOCK,
                    this.getBlockPos().below(2), this.cachedStateBelow, null, Direction.UP);
        }
    }

    private IItemHandler getCurrentHandler() {
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
    public class BattlefieldInventory extends ItemStackHandler {
        private boolean isInput;
        public boolean suppressWrites = false;

        public BattlefieldInventory(int size, boolean isInput) {
            super(size);
            this.isInput = isInput;
        }

        @Override
        public @NotNull ItemStack insertItem(int slot, @NotNull ItemStack stack, boolean simulate) {
            if (this.isInput) {
                return mayPlace(stack, slot) ? super.insertItem(slot, stack, simulate) : stack;
            }
            return super.insertItem(slot, stack, simulate);
        }

        public boolean mayPlace(ItemStack stack, int slot) {
            if (slot == 1)
                return stack.has(DataComponents.ENTITY_DATA);
            if (slot == 2)
                return stack.has(DataComponents.ATTRIBUTE_MODIFIERS);

            return stack.has(OccultismDataComponents.SOUL_VALUE);
        }

        @Override
        protected void onContentsChanged(int slot) {
            if (!this.suppressWrites) {
                DimensionalBattlefieldBlockEntity.this.setChanged();
            }
        }

        public void setStackInSlotDirect(int slot, ItemStack stack) {
            this.stacks.set(slot, stack);
        }
    }

    public class BufferedOutputHandler implements IItemHandler, IItemHandlerModifiable {
        private final DimensionalBattlefieldBlockEntity.BattlefieldInventory internal;

        public BufferedOutputHandler(DimensionalBattlefieldBlockEntity.BattlefieldInventory internal) {
            this.internal = internal;
        }

        @Override
        public int getSlots() {
            return internal.getSlots();
        }

        @Override
        public @NotNull ItemStack getStackInSlot(int slot) {
            return internal.getStackInSlot(slot);
        }

        @Override
        public @NotNull ItemStack insertItem(int slot, @NotNull ItemStack stack, boolean simulate) {
            internal.suppressWrites = true;
            try {
                ItemStack result = internal.insertItem(slot, stack, simulate);
                // If items were accepted (result count < stack count), mark dirty
                if (!simulate && (result.isEmpty() || result.getCount() < stack.getCount())) {
                    DimensionalBattlefieldBlockEntity.this.outputDirty = true;
                }
                return result;
            } finally {
                internal.suppressWrites = false;
            }
        }

        @Override
        public @NotNull ItemStack extractItem(int slot, int amount, boolean simulate) {
            internal.suppressWrites = true;
            try {
                ItemStack result = internal.extractItem(slot, amount, simulate);
                if (!simulate && !result.isEmpty()) {
                    DimensionalBattlefieldBlockEntity.this.outputDirty = true;
                }
                return result;
            } finally {
                internal.suppressWrites = false;
            }
        }

        @Override
        public int getSlotLimit(int slot) {
            return internal.getSlotLimit(slot);
        }

        @Override
        public boolean isItemValid(int slot, @NotNull ItemStack stack) {
            return internal.isItemValid(slot, stack);
        }

        @Override
        public void setStackInSlot(int slot, @NotNull ItemStack stack) {
            internal.suppressWrites = true;
            try {
                internal.setStackInSlot(slot, stack);
                DimensionalBattlefieldBlockEntity.this.outputDirty = true;
            } finally {
                internal.suppressWrites = false;
            }
        }
    }
    // endregion Inner Classes
}
