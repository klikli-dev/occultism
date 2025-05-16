/*
 * MIT License
 *
 * Copyright 2020 klikli-dev
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

package com.klikli_dev.occultism.common.entity.spirit;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.klikli_dev.occultism.api.common.data.WorkAreaSize;
import com.klikli_dev.occultism.common.container.spirit.SpiritContainer;
import com.klikli_dev.occultism.common.entity.job.SpiritJob;
import com.klikli_dev.occultism.common.item.spirit.BookOfCallingItem;
import com.klikli_dev.occultism.registry.OccultismMemoryTypes;
import com.klikli_dev.occultism.registry.OccultismSounds;
import com.klikli_dev.occultism.registry.OccultismTags;
import com.mojang.serialization.Dynamic;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.tslat.smartbrainlib.api.SmartBrainOwner;
import net.tslat.smartbrainlib.api.core.BrainActivityGroup;
import net.tslat.smartbrainlib.api.core.SmartBrain;
import net.tslat.smartbrainlib.api.core.SmartBrainProvider;
import net.tslat.smartbrainlib.api.core.sensor.ExtendedSensor;
import net.tslat.smartbrainlib.util.BrainUtils;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public abstract class SpiritEntity extends TamableAnimal implements ISkinnedCreatureMixin, MenuProvider, SmartBrainOwner<SpiritEntity> {
    public static final EntityDataAccessor<Integer> SKIN = SynchedEntityData
            .defineId(SpiritEntity.class, EntityDataSerializers.INT);
    /**
     * The default max age in seconds.
     */
    public static final int DEFAULT_MAX_AGE = -1;//default age is unlimited.
    public static final int MAX_FILTER_SLOTS = 14;
    /**
     * The spirit job registry name/id.
     */
    protected static final EntityDataAccessor<String> JOB_ID = SynchedEntityData
            .defineId(SpiritEntity.class, EntityDataSerializers.STRING);
    private static final EntityDataAccessor<Optional<BlockPos>> DEPOSIT_POSITION =
            SynchedEntityData.defineId(SpiritEntity.class, EntityDataSerializers.OPTIONAL_BLOCK_POS);
    private static final EntityDataAccessor<Optional<UUID>> DEPOSIT_ENTITY_UUID =
            SynchedEntityData.defineId(SpiritEntity.class, EntityDataSerializers.OPTIONAL_UUID);
    private static final EntityDataAccessor<Direction> DEPOSIT_FACING =
            SynchedEntityData.defineId(SpiritEntity.class, EntityDataSerializers.DIRECTION);
    private static final EntityDataAccessor<Optional<BlockPos>> EXTRACT_POSITION =
            SynchedEntityData.defineId(SpiritEntity.class, EntityDataSerializers.OPTIONAL_BLOCK_POS);
    private static final EntityDataAccessor<Direction> EXTRACT_FACING =
            SynchedEntityData.defineId(SpiritEntity.class, EntityDataSerializers.DIRECTION);
    private static final EntityDataAccessor<Optional<BlockPos>> WORK_AREA_POSITION =
            SynchedEntityData.defineId(SpiritEntity.class, EntityDataSerializers.OPTIONAL_BLOCK_POS);
    private static final EntityDataAccessor<Integer> WORK_AREA_SIZE =
            SynchedEntityData.defineId(SpiritEntity.class, EntityDataSerializers.INT);
    /**
     * The spirit age in seconds.
     */
    private static final EntityDataAccessor<Integer> SPIRIT_AGE = SynchedEntityData.defineId(SpiritEntity.class,
            EntityDataSerializers.INT);
    /**
     * The max spirit age in seconds.
     */
    private static final EntityDataAccessor<Integer> SPIRIT_MAX_AGE = SynchedEntityData.defineId(SpiritEntity.class,
            EntityDataSerializers.INT);
    /**
     * The filter mode (blacklist/whitelist)
     */
    private static final EntityDataAccessor<Boolean> IS_FILTER_BLACKLIST = SynchedEntityData
            .defineId(SpiritEntity.class, EntityDataSerializers.BOOLEAN);

    /**
     * The filter item list
     */
    private static final EntityDataAccessor<CompoundTag> FILTER_ITEMS = SynchedEntityData
            .defineId(SpiritEntity.class, EntityDataSerializers.COMPOUND_TAG);

    /**
     * The filter for tags
     */
    private static final EntityDataAccessor<String> TAG_FILTER = SynchedEntityData
            .defineId(SpiritEntity.class, EntityDataSerializers.STRING);

    public ItemStackHandler inventory;
    public ItemStackHandler filterItemStackHandler = new ItemStackHandler(MAX_FILTER_SLOTS) {
        @Override
        protected void onContentsChanged(int slot) {
            super.onContentsChanged(slot);
            SpiritEntity.this.entityData.set(FILTER_ITEMS, this.serializeNBT(SpiritEntity.this.level().registryAccess()));
        }
    };

    //initialized in getter, because super constructor already accesses it
    protected Optional<SpiritJob> job;
    protected boolean isInitialized = false;

    public SpiritEntity(EntityType<? extends SpiritEntity> type, Level worldIn) {
        this(type, worldIn, new ItemStackHandler(1));
    }

    public SpiritEntity(EntityType<? extends SpiritEntity> type, Level worldIn, ItemStackHandler inventory) {
        super(type, worldIn);
        this.inventory = inventory;
        this.setPersistenceRequired();
    }

    public static AttributeSupplier.Builder createAttributes() {
        return TamableAnimal.createLivingAttributes()
                .add(Attributes.ATTACK_DAMAGE, 1.0)
                .add(Attributes.ATTACK_SPEED, 4.0)
                .add(Attributes.MOVEMENT_SPEED, 0.30000001192092896)
                .add(Attributes.FOLLOW_RANGE, 50.0);
    }

    public boolean isInitialized() {
        return this.isInitialized;
    }

    @Override
    protected Brain.Provider<?> brainProvider() {
        //memories are dynamic due to the job system.
        return new SmartBrainProvider<>(this, true);
    }

    @Override
    protected void customServerAiStep() {
        this.tickBrain(this);
    }

    @Override
    public void handleAdditionalBrainSetup(SmartBrain<? extends SpiritEntity> brain) {
        //we might want to init brain vars that come from spirit vars here, but as this happens before entity is in the world, we are missing fallback data such as entity position that some of our spirit vars (work area center) use
        this.getJob().ifPresent(job -> job.handleAdditionalBrainSetup(brain));
    }

    @Override
    public List<ExtendedSensor<SpiritEntity>> getSensors() {
        return this.getJob().isPresent() ? this.getJob().get().getSensors() : ImmutableList.of();
    }

    @Override
    public BrainActivityGroup<SpiritEntity> getCoreTasks() {
        return this.getJob().isPresent() ? this.getJob().get().getCoreTasks() : BrainActivityGroup.empty();
    }

    @Override
    public BrainActivityGroup<SpiritEntity> getIdleTasks() {
        return this.getJob().isPresent() ? this.getJob().get().getIdleTasks() : BrainActivityGroup.empty();
    }

    @Override
    public void onSyncedDataUpdated(EntityDataAccessor<?> key) {
        super.onSyncedDataUpdated(key);

        if (key == FILTER_ITEMS) {
            //restore filter item handler from data param on client
            if (this.level().isClientSide) {
                CompoundTag compound = this.entityData.get(FILTER_ITEMS);
                if (!compound.isEmpty())
                    this.filterItemStackHandler.deserializeNBT(this.level().registryAccess(), compound);
            }
        }

        //TODO job
    }

    public Optional<BlockPos> getDepositPosition() {
        return this.entityData.get(DEPOSIT_POSITION);
    }

    public void setDepositPosition(BlockPos position) {
        this.entityData.set(DEPOSIT_POSITION, Optional.ofNullable(position));
        if (position != null)
            this.entityData.set(DEPOSIT_ENTITY_UUID, Optional.empty());

        BrainUtils.setMemory(this, OccultismMemoryTypes.DEPOSIT_POSITION.get(), position);
    }

    public Optional<UUID> getDepositEntityUUID() {
        return this.entityData.get(DEPOSIT_ENTITY_UUID);
    }

    public void setDepositEntityUUID(UUID uuid) {
        this.entityData.set(DEPOSIT_ENTITY_UUID, Optional.ofNullable(uuid));
        if (uuid != null)
            this.entityData.set(DEPOSIT_POSITION, Optional.empty());
    }

    public Optional<BlockPos> getExtractPosition() {
        return this.entityData.get(EXTRACT_POSITION);
    }

    public void setExtractPosition(BlockPos position) {
        this.entityData.set(EXTRACT_POSITION, Optional.ofNullable(position));
    }

    public Optional<BlockPos> getWorkAreaPosition() {
        return this.entityData.get(WORK_AREA_POSITION);
    }

    public void setWorkAreaPosition(BlockPos position) {
        this.entityData.set(WORK_AREA_POSITION, Optional.ofNullable(position));
        BrainUtils.setMemory(this, OccultismMemoryTypes.WORK_AREA_CENTER.get(), this.getWorkAreaCenter());

        this.getJob().ifPresent(SpiritJob::onChangeWorkArea);
    }

    public WorkAreaSize getWorkAreaSize() {
        return WorkAreaSize.get(this.entityData.get(WORK_AREA_SIZE));
    }

    public void setWorkAreaSize(WorkAreaSize workAreaSize) {
        this.entityData.set(WORK_AREA_SIZE, workAreaSize.ordinal()); //for the entity data set the
        BrainUtils.setMemory(this, OccultismMemoryTypes.WORK_AREA_SIZE.get(), this.getWorkAreaSize().getValue());

        this.getJob().ifPresent(SpiritJob::onChangeWorkArea);
    }

    public BlockPos getWorkAreaCenter() {
        return this.getWorkAreaPosition().orElse(this.blockPosition());
    }

    public Direction getDepositFacing() {
        return this.entityData.get(DEPOSIT_FACING);
    }

    public void setDepositFacing(Direction depositFacing) {
        this.entityData.set(DEPOSIT_FACING, depositFacing);
        BrainUtils.setMemory(this, OccultismMemoryTypes.DEPOSIT_FACING.get(), depositFacing);
    }

    public Direction getExtractFacing() {
        return this.entityData.get(EXTRACT_FACING);
    }

    public void setExtractFacing(Direction extractFacing) {
        this.entityData.set(EXTRACT_FACING, extractFacing);
    }

    /**
     * @return the spirit age in seconds.
     */
    public int getSpiritAge() {
        return this.entityData.get(SPIRIT_AGE);
    }

    /**
     * Sets the spirit age.
     *
     * @param seconds the spirit age in seconds.
     */
    public void setSpiritAge(int seconds) {
        this.entityData.set(SPIRIT_AGE, seconds);
    }

    /**
     * @return the spirit max age in seconds.
     */
    public int getSpiritMaxAge() {
        return this.entityData.get(SPIRIT_MAX_AGE);
    }

    /**
     * Sets the spirit max age.
     *
     * @param seconds the spirit max age in seconds.
     */
    public void setSpiritMaxAge(int seconds) {
        this.entityData.set(SPIRIT_MAX_AGE, seconds);
    }

    /**
     * @return the spirit's job id.
     */
    public String getJobID() {
        return this.entityData.get(JOB_ID);
    }

    /**
     * Sets the spirit's job id.
     *
     * @param id the job id string.
     */
    public void setJobID(String id) {
        this.entityData.set(JOB_ID, id);
    }

    /**
     * @return the filter mode
     */
    public boolean isFilterBlacklist() {
        return this.entityData.get(IS_FILTER_BLACKLIST);
    }

    /**
     * Sets the filter mode
     *
     * @param isFilterBlacklist the filter mode
     */
    public void setFilterBlacklist(boolean isFilterBlacklist) {
        this.entityData.set(IS_FILTER_BLACKLIST, isFilterBlacklist);
    }

    /**
     * Gets the tag filter string
     */
    public String getTagFilter() {
        return this.entityData.get(TAG_FILTER);
    }

    /**
     * Sets the tag filter string
     */
    public void setTagFilter(String tagFilter) {
        this.entityData.set(TAG_FILTER, tagFilter);
    }

    /**
     * @return the filter mode
     */
    public ItemStackHandler getFilterItems() {
        return this.filterItemStackHandler;
    }

    public Optional<SpiritJob> getJob() {
        //super constructor already accesses it so it is null, that is why we init it here
        if (this.job == null)
            this.job = Optional.empty();
        return this.job;
    }

    /**
     * Cleans up old job and sets and initializes the new job.
     * Will not recreate brain.
     *
     * @param job the new job, should already be initialized
     */
    public void setJob(SpiritJob job) {
        this.setJob(job, true);
    }

    /**
     * Cleans up old job and sets and initializes the new job.
     *
     * @param job           the new job, should already be initialized
     * @param recreateBrain if true, the brain will be re-created (which will force tasks to be set up again)
     */
    public void setJob(SpiritJob job, boolean recreateBrain) {
        this.removeJob();
        this.job = Optional.ofNullable(job);
        if (job != null) {
            this.job = Optional.ofNullable(job);
            this.setJobID(job.getFactoryID().toString());

            if (recreateBrain) {
                this.remakeBrain();
            }
        }
    }

    public void remakeBrain() {
        NbtOps nbtops = NbtOps.INSTANCE;
        this.brain = this.makeBrain(new Dynamic<>(nbtops, nbtops.createMap(ImmutableMap.of(nbtops.createString("memories"), nbtops.emptyMap()))));
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int id, Inventory playerInventory, Player player) {
        return new SpiritContainer(id, playerInventory, this);
    }

    @Override
    public LivingEntity getEntity() {
        return this;
    }

    @Override
    public EntityDataAccessor<Integer> getDataParameterSkin() {
        return SKIN;
    }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor worldIn, DifficultyInstance difficultyIn, MobSpawnType reason,
                                        @Nullable SpawnGroupData spawnDataIn) {
        this.selectRandomSkin();
        return super.finalizeSpawn(worldIn, difficultyIn, reason, spawnDataIn);
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel serverLevel, AgeableMob ageable) {
        return null;
    }

    @Override
    public boolean shouldTryTeleportToOwner() {
        return false;
    }

    @Override
    public void aiStep() {
        if (!this.level().isClientSide) {
            if (!this.isInitialized) {
                this.init();
            }

            //every 20 ticks = 1 second, age by 1 second
            if (this.level().getGameTime() % 20 == 0 && !this.dead && this.canDieFromAge()) {
                this.setSpiritAge(this.getSpiritAge() + 1);
                if (this.getSpiritAge() > this.getSpiritMaxAge()) {
                    this.die(this.damageSources().generic());
                    this.remove(RemovalReason.DISCARDED);
                }
            }
            if (!this.dead)
                this.getJob().ifPresent(SpiritJob::update);
        }
        this.updateSwingTime();
        super.aiStep();
    }

    @Override
    public boolean isInvulnerableTo(@NotNull DamageSource source) {
        return super.isInvulnerableTo(source) || source.is(DamageTypes.IN_WALL) || source.is(DamageTypes.FLY_INTO_WALL);
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (this.isInvulnerableTo(source)) {
            return false;
        } else {
            //copied from wolf
            Entity entity = source.getEntity();
            if (entity != null && !(entity instanceof Player) && !(entity instanceof AbstractArrow)) {
                amount = (amount + 1.0F) / 2.0F;
            }

            return super.hurt(source, amount);
        }
    }


    @Override
    protected void registerGoals() {
        //none, we use a brain
    }

    @Override
    public ItemStack getItemBySlot(EquipmentSlot slotIn) {
        if (slotIn == EquipmentSlot.MAINHAND) {
            return this.inventory.getStackInSlot(0);
        }
        return ItemStack.EMPTY;
    }

    @Override
    public void setItemSlot(EquipmentSlot slotIn, ItemStack stack) {
        if (slotIn == EquipmentSlot.MAINHAND) {
            this.inventory.setStackInSlot(0, stack);
        }
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        this.registerSkinDataParameter(builder);
        builder.define(DEPOSIT_POSITION, Optional.empty());
        builder.define(DEPOSIT_ENTITY_UUID, Optional.empty());
        builder.define(DEPOSIT_FACING, Direction.UP);
        builder.define(EXTRACT_POSITION, Optional.empty());
        builder.define(EXTRACT_FACING, Direction.DOWN);
        builder.define(WORK_AREA_POSITION, Optional.empty());
        builder.define(WORK_AREA_SIZE, WorkAreaSize.SMALL.ordinal());
        builder.define(SPIRIT_AGE, 0);
        builder.define(SPIRIT_MAX_AGE, DEFAULT_MAX_AGE);
        builder.define(JOB_ID, "");
        builder.define(IS_FILTER_BLACKLIST, false);
        builder.define(FILTER_ITEMS, new CompoundTag());
        builder.define(TAG_FILTER, "");
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);

        //Store age
        compound.putInt("spiritAge", this.getSpiritAge());
        compound.putInt("spiritMaxAge", this.getSpiritMaxAge());

        //store work area position
        this.getWorkAreaPosition().ifPresent(pos -> compound.putLong("workAreaPosition", pos.asLong()));
        compound.putInt("workAreaSize", this.getWorkAreaSize().ordinal());

        //store deposit info
        this.getDepositPosition().ifPresent(pos -> compound.putLong("depositPosition", pos.asLong()));
        this.getDepositEntityUUID().ifPresent(uuid -> compound.putUUID("depositEntityUUID", uuid));
        compound.putInt("depositFacing", this.getDepositFacing().ordinal());

        //store extract info
        this.getExtractPosition().ifPresent(pos -> compound.putLong("extractPosition", pos.asLong()));
        compound.putInt("extractFacing", this.getExtractFacing().ordinal());

        //store current inventory
        compound.put("inventory", this.inventory.serializeNBT(this.level().registryAccess()));

        //store job
        this.getJob().ifPresent(job -> compound.put("spiritJob", job.serializeNBT(this.level().registryAccess())));

        compound.putBoolean("isFilterBlacklist", this.isFilterBlacklist());
        compound.put("filterItems", this.filterItemStackHandler.serializeNBT(this.level().registryAccess()));

        compound.putString("tagFilter", this.getTagFilter());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);

        //read age
        if (compound.contains("spiritAge")) {
            this.setSpiritAge(compound.getInt("spiritAge"));
        }
        if (compound.contains("spiritMaxAge")) {
            this.setSpiritMaxAge(compound.getInt("spiritMaxAge"));
        }

        //read base position
        if (compound.contains("workAreaPosition")) {
            this.setWorkAreaPosition(BlockPos.of(compound.getLong("workAreaPosition")));
        }
        if (compound.contains("workAreaSize")) {
            this.setWorkAreaSize(WorkAreaSize.get(compound.getInt("workAreaSize")));
        }

        //read deposit information
        if (compound.contains("depositPosition")) {
            this.setDepositPosition(BlockPos.of(compound.getLong("depositPosition")));
        }
        if (compound.contains("depositEntityUUID")) {
            this.setDepositEntityUUID(compound.getUUID("depositEntityUUID"));
        }
        if (compound.contains("depositFacing")) {
            this.setDepositFacing(Direction.values()[compound.getInt("depositFacing")]);
        }

        //read extract information
        if (compound.contains("extractPosition")) {
            this.setExtractPosition(BlockPos.of(compound.getLong("extractPosition")));
        }
        if (compound.contains("extractFacing")) {
            this.setExtractFacing(Direction.values()[compound.getInt("extractFacing")]);
        }

        //set up inventory and read items

        if (compound.contains("inventory")) {
            this.inventory.deserializeNBT(this.level().registryAccess(), compound.getCompound("inventory"));
        }

        //read job
        if (compound.contains("spiritJob")) {
            SpiritJob job = SpiritJob.from(this, compound.getCompound("spiritJob"));
            var containsBrain = compound.contains("Brain", 10);
            this.setJob(job, !containsBrain);
            if (compound.contains("Brain", Tag.TAG_COMPOUND)) {
                this.brain = this.makeBrain(new Dynamic<>(NbtOps.INSTANCE, compound.get("Brain")));
            }
        }

        if (compound.contains("isFilterBlacklist")) {
            this.setFilterBlacklist(compound.getBoolean("isFilterBlacklist"));
        }

        if (compound.contains("filterItems")) {
            compound.getCompound("filterItems").putInt("Size", MAX_FILTER_SLOTS); //override legacy filter size
            this.filterItemStackHandler.deserializeNBT(this.level().registryAccess(), compound.getCompound("filterItems"));
        }

        if (compound.contains("tagFilter")) {
            this.setTagFilter(compound.getString("tagFilter"));
        }
    }

    @Override
    public void setTame(boolean pTame, boolean pApplyTamingSideEffects) {
        super.setTame(pTame, pApplyTamingSideEffects);
        if (!pTame)
            this.setJob(null); //remove job if not tamed
    }

    @Override
    protected void dropEquipment() {
        super.dropEquipment();
        for (int i = 0; i < this.inventory.getSlots(); ++i) {
            ItemStack itemstack = this.inventory.getStackInSlot(i);
            if (!itemstack.isEmpty()) {
                this.spawnAtLocation(itemstack, 0.0F);
            }
        }
    }

    @Override
    public void die(DamageSource cause) {
        if (!this.level().isClientSide) {
            if (this.isTame()) {
                BookOfCallingItem.spiritDeathRegister.put(this.uuid, this.level().getGameTime());
            }

            this.removeJob();

            //Death sound and particle effects
            ((ServerLevel) this.level())
                    .sendParticles(ParticleTypes.LARGE_SMOKE, this.getX(), this.getY() + 0.5, this.getZ(), 1,
                            0.0, 0.0, 0.0, 0.0);
            this.level().playSound(null, this.blockPosition(), OccultismSounds.START_RITUAL.get(), SoundSource.NEUTRAL, 1,
                    1);

        }

        super.die(cause);
    }

    @Override
    public void remove(RemovalReason reason) {
        this.removeJob();
        super.remove(reason);
    }

    @Override
    public InteractionResult interactAt(Player player, Vec3 vec, InteractionHand hand) {
      ItemStack itemStack = player.getItemInHand(hand);

      if (!(itemStack.is(OccultismTags.Items.BOOK_OF_CALLING_FOLIOT) || itemStack.is(OccultismTags.Items.BOOK_OF_CALLING_DJINNI))) {
        if (!this.isTame())
            this.tame(player);

        if (player.isShiftKeyDown() && this.getOwner() == player) {
            this.openScreen(player);
            return InteractionResult.SUCCESS;
        }
      }
        
      return super.interactAt(player, vec, hand);
    }

    @Override
    public EntityDimensions getDefaultDimensions(Pose pPose) {
        return this.getJob().map(job -> job.getDimensions(pPose, super.getDefaultDimensions(pPose))).orElse(super.getDefaultDimensions(pPose));
    }

    public void removeJob() {
        this.getJob().ifPresent(SpiritJob::cleanup);
        this.job = Optional.empty();
    }

    /**
     * @return true if the spirit has a max age and can die from age.
     */
    public boolean canDieFromAge() {
        return this.entityData.get(SPIRIT_MAX_AGE) > -1;
    }

    public void init() {
        this.isInitialized = true;
        this.getJob().ifPresent(SpiritJob::init);
    }

    public boolean canPickupItem(ItemEntity entity) {
        return this.getJob().map(job -> job.canPickupItem(entity)).orElse(false);
    }

    public void openScreen(Player playerEntity) {
        if (!this.level().isClientSide) {
            MenuProvider menuProvider = this;

            SpiritJob currentJob = this.getJob().orElse(null);
            if (currentJob instanceof MenuProvider)
                menuProvider = (MenuProvider) currentJob;

            if (playerEntity instanceof ServerPlayer serverPlayer) {
                serverPlayer.openMenu(menuProvider, (buf) -> buf.writeInt(this.getId()));
            }
        }
    }
}
