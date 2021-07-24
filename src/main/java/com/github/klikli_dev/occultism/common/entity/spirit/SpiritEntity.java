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

package com.github.klikli_dev.occultism.common.entity.spirit;

import com.github.klikli_dev.occultism.api.common.data.WorkAreaSize;
import com.github.klikli_dev.occultism.common.container.spirit.SpiritContainer;
import com.github.klikli_dev.occultism.common.entity.ISkinnedCreatureMixin;
import com.github.klikli_dev.occultism.common.item.spirit.BookOfCallingItem;
import com.github.klikli_dev.occultism.common.job.SpiritJob;
import com.github.klikli_dev.occultism.exceptions.ItemHandlerMissingException;
import com.github.klikli_dev.occultism.registry.OccultismSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fmllegacy.network.NetworkHooks;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nullable;
import java.util.Optional;
import java.util.UUID;

public abstract class SpiritEntity extends TamableAnimal implements ISkinnedCreatureMixin, MenuProvider {
    //region Fields
    public static final EntityDataAccessor<Integer> SKIN = SynchedEntityData
            .defineId(SpiritEntity.class, EntityDataSerializers.INT);
    /**
     * The default max age in seconds.
     */
    public static final int DEFAULT_MAX_AGE = -1;//default age is unlimited.
    public static final int MAX_FILTER_SLOTS = 7;
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
     * The spirit job registry name/id.
     */
    private static final EntityDataAccessor<String> JOB_ID = SynchedEntityData
            .defineId(SpiritEntity.class, EntityDataSerializers.STRING);

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

    public LazyOptional<ItemStackHandler> itemStackHandler = LazyOptional.of(ItemStackHandler::new);
    public LazyOptional<ItemStackHandler> filterItemStackHandler = LazyOptional.of(() -> new ItemStackHandler(MAX_FILTER_SLOTS) {
        @Override
        protected void onContentsChanged(int slot) {
            super.onContentsChanged(slot);
            SpiritEntity.this.entityData.set(FILTER_ITEMS, this.serializeNBT());
        }
    });
    protected Optional<SpiritJob> job = Optional.empty();
    protected boolean isInitialized = false;

    //endregion Fields
    //region Initialization
    public SpiritEntity(EntityType<? extends SpiritEntity> type, Level worldIn) {
        super(type, worldIn);
        this.setPersistenceRequired();
    }
    //endregion Initialization


    @Override
    public void onSyncedDataUpdated(EntityDataAccessor<?> key) {
        super.onSyncedDataUpdated(key);

        if (key == FILTER_ITEMS) {
            //restore filter item handler from data param on client
            if (this.level.isClientSide) {
                this.filterItemStackHandler.ifPresent((handler) -> {
                    CompoundTag compound = this.entityData.get(FILTER_ITEMS);
                    if (!compound.isEmpty())
                        handler.deserializeNBT(compound);
                });
            }
        }
    }

    public <T> LazyOptional<T> getCapability(Capability<T> capability, @Nullable Direction facing) {
        if (this.isAlive() && capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return this.itemStackHandler.cast();
        }
        return super.getCapability(capability, facing);
    }


    //region Getter / Setter
    public Optional<BlockPos> getDepositPosition() {
        return this.entityData.get(DEPOSIT_POSITION);
    }

    public void setDepositPosition(BlockPos position) {
        this.entityData.set(DEPOSIT_POSITION, Optional.ofNullable(position));
        if (position != null)
            this.entityData.set(DEPOSIT_ENTITY_UUID, Optional.empty());
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
    }

    public WorkAreaSize getWorkAreaSize() {
        return WorkAreaSize.get(this.entityData.get(WORK_AREA_SIZE));
    }

    public void setWorkAreaSize(WorkAreaSize workAreaSize) {
        this.entityData.set(WORK_AREA_SIZE, workAreaSize.getValue());
    }

    public BlockPos getWorkAreaCenter() {
        return this.getWorkAreaPosition().orElse(this.blockPosition());
    }

    public Direction getDepositFacing() {
        return this.entityData.get(DEPOSIT_FACING);
    }

    public void setDepositFacing(Direction depositFacing) {
        this.entityData.set(DEPOSIT_FACING, depositFacing);
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
    public LazyOptional<ItemStackHandler> getFilterItems() {
        return this.filterItemStackHandler;
    }

    public Optional<SpiritJob> getJob() {
        return this.job;
    }

    /**
     * Cleans up old job and sets and initializes the new job.
     *
     * @param job the new job, should already be initialized
     */
    public void setJob(SpiritJob job) {
        this.removeJob();
        this.job = Optional.ofNullable(job);
        if (job != null) {
            this.job = Optional.ofNullable(job);
            this.setJobID(job.getFactoryID().toString());
        }
    }
    //endregion Getter / Setter

    //region Overrides
    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int id, Inventory playerInventory, Player player) {
        return new SpiritContainer(id, playerInventory, this);
    }

    @Override
    public LivingEntity getEntity() {
        return (LivingEntity) this;
    }

    @Override
    public EntityDataAccessor<Integer> getDataParameterSkin() {
        return SKIN;
    }


    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor worldIn, DifficultyInstance difficultyIn, MobSpawnType reason,
                                        @Nullable SpawnGroupData spawnDataIn, @Nullable CompoundTag dataTag) {
        this.selectRandomSkin();
        return super.finalizeSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel serverLevel, AgeableMob ageable) {
        return null;
    }

    @Override
    public void aiStep() {
        if (!this.level.isClientSide) {
            if (!this.isInitialized) {
                this.isInitialized = true;
                this.init();
            }

            //every 20 ticks = 1 second, age by 1 second
            if (this.level.getGameTime() % 20 == 0 && !this.dead && this.canDieFromAge()) {
                this.setSpiritAge(this.getSpiritAge() + 1);
                if (this.getSpiritAge() > this.getSpiritMaxAge()) {
                    this.die(DamageSource.GENERIC);
                    this.remove(false);
                }
            }
            if (!this.dead)
                this.job.ifPresent(SpiritJob::update);
        }
        this.updateSwingTime();
        super.aiStep();
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
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(5, new MeleeAttackGoal(this, 1.0D, true));
        this.goalSelector.addGoal(9, new LookAtPlayerGoal(this, Player.class, 8.0F, 1));
        this.goalSelector.addGoal(10, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(3, new HurtByTargetGoal(this));
    }

    @Override
    public ItemStack getItemBySlot(EquipmentSlot slotIn) {
        switch (slotIn) {
            case MAINHAND:
                return this.itemStackHandler.orElseThrow(ItemHandlerMissingException::new).getItem(0);
            default:
                return ItemStack.EMPTY;
        }
    }

    @Override
    public void setItemSlot(EquipmentSlot slotIn, ItemStack stack) {
        switch (slotIn) {
            case MAINHAND:
                this.itemStackHandler.orElseThrow(ItemHandlerMissingException::new).setStackInSlot(0, stack);
        }
    }

    @Override
    public boolean doHurtTarget(Entity entityIn) {
        //copied from wolf
        boolean flag = entityIn.hurt(DamageSource.mobAttack(this),
                (float) ((int) this.getAttributeValue(Attributes.ATTACK_DAMAGE)));
        if (flag) {
            this.doEnchantDamageEffects(this, entityIn);
        }

        return flag;
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.registerSkinDataParameter();
        this.entityData.define(DEPOSIT_POSITION, Optional.empty());
        this.entityData.define(DEPOSIT_ENTITY_UUID, Optional.empty());
        this.entityData.define(DEPOSIT_FACING, Direction.UP);
        this.entityData.define(EXTRACT_POSITION, Optional.empty());
        this.entityData.define(EXTRACT_FACING, Direction.DOWN);
        this.entityData.define(WORK_AREA_POSITION, Optional.empty());
        this.entityData.define(WORK_AREA_SIZE, WorkAreaSize.SMALL.getValue());
        this.entityData.define(SPIRIT_AGE, 0);
        this.entityData.define(SPIRIT_MAX_AGE, DEFAULT_MAX_AGE);
        this.entityData.define(JOB_ID, "");
        this.entityData.define(IS_FILTER_BLACKLIST, false);
        this.entityData.define(FILTER_ITEMS, new CompoundTag());
        this.entityData.define(TAG_FILTER, "");
    }


    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);

        //Store age
        compound.putInt("spiritAge", this.getSpiritAge());
        compound.putInt("spiritMaxAge", this.getSpiritMaxAge());

        //store work area position
        this.getWorkAreaPosition().ifPresent(pos -> compound.putLong("workAreaPosition", pos.asLong()));
        compound.putInt("workAreaSize", this.getWorkAreaSize().getValue());

        //store deposit info
        this.getDepositPosition().ifPresent(pos -> compound.putLong("depositPosition", pos.asLong()));
        this.getDepositEntityUUID().ifPresent(uuid -> compound.putUUID("depositEntityUUID", uuid));
        compound.putInt("depositFacing", this.getDepositFacing().ordinal());

        //store extract info
        this.getExtractPosition().ifPresent(pos -> compound.putLong("extractPosition", pos.asLong()));
        compound.putInt("extractFacing", this.getExtractFacing().ordinal());

        //store current inventory
        this.itemStackHandler.ifPresent(handler -> compound.put("inventory", handler.serializeNBT()));

        //store job
        this.job.ifPresent(job -> compound.put("spiritJob", job.serializeNBT()));

        compound.putBoolean("isFilterBlacklist", this.isFilterBlacklist());
        this.filterItemStackHandler.ifPresent(handler -> compound.put("filterItems", handler.serializeNBT()));

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
            this.itemStackHandler.ifPresent(handler -> handler.deserializeNBT(compound.getCompound("inventory")));
        }

        //read job
        if (compound.contains("spiritJob")) {
            SpiritJob job = SpiritJob.from(this, compound.getCompound("spiritJob"));
            this.setJob(job);
        }

        if (compound.contains("isFilterBlacklist")) {
            this.setFilterBlacklist(compound.getBoolean("isFilterBlacklist"));
        }

        if (compound.contains("filterItems")) {
            this.filterItemStackHandler.ifPresent(handler -> handler.deserializeNBT(compound.getCompound("filterItems")));
        }

        if (compound.contains("tagFilter")) {
            this.setTagFilter(compound.getString("tagFilter"));
        }
    }

    @Override
    public void setTame(boolean tamed) {
        super.setTame(tamed);
        if (!tamed)
            this.setJob(null); //remove job if not tamed
    }

    @Override
    protected void dropEquipment() {
        super.dropEquipment();
        this.itemStackHandler.ifPresent((handle) -> {
            for (int i = 0; i < handle.getSlots(); ++i) {
                ItemStack itemstack = handle.getStackInSlot(i);
                if (!itemstack.isEmpty()) {
                    this.spawnAtLocation(itemstack, 0.0F);
                }
            }
        });

    }

    @Override
    public void die(DamageSource cause) {
        if (!this.level.isClientSide) {
            if (this.isTame()) {
                BookOfCallingItem.spiritDeathRegister.put(this.uuid, this.level.getGameTime());
            }

            this.removeJob();

            //Death sound and particle effects
            ((ServerLevel) this.level)
                    .sendParticles(ParticleTypes.LARGE_SMOKE, this.getX(), this.getY() + 0.5, this.getZ(), 1,
                            0.0, 0.0, 0.0, 0.0);
            this.level.playSound(null, this.blockPosition(), OccultismSounds.START_RITUAL.get(), SoundSource.NEUTRAL, 1,
                    1);

        }

        super.die(cause);
    }

    public void remove(boolean keepData) {
        this.removeJob();
        super.remove(keepData);
    }

    @Override
    public InteractionResult interactAt(Player player, Vec3 vec, InteractionHand hand) {
        ItemStack itemStack = player.getItemInHand(hand);

        if (itemStack.isEmpty()) {
            if (this.isTame() && player.isShiftKeyDown()) {
                this.openGUI(player);
                return InteractionResult.SUCCESS;
            }
        }
        return super.interactAt(player, vec, hand);
    }
    //endregion Overrides

    //region Static Methods
    public static AttributeSupplier.Builder createLivingAttributes() {
        return TamableAnimal.createLivingAttributes()
                .add(Attributes.ATTACK_DAMAGE, 1.0)
                .add(Attributes.ATTACK_SPEED, 4.0)
                .add(Attributes.MOVEMENT_SPEED, 0.30000001192092896)
                .add(Attributes.FOLLOW_RANGE, 50.0);
    }
    //endregion Static Methods

    //region Methods

    public void removeJob() {
        this.job.ifPresent(SpiritJob::cleanup);
        this.job = Optional.empty();
    }

    /**
     * @return true if the spirit has a max age and can die from age.
     */
    public boolean canDieFromAge() {
        return this.entityData.get(SPIRIT_MAX_AGE) > -1;
    }

    public void init() {
        this.job.ifPresent(SpiritJob::init);
    }

    public boolean canPickupItem(ItemEntity entity) {
        return this.job.map(job -> job.canPickupItem(entity)).orElse(false);
    }

    public void openGUI(Player playerEntity) {
        if (!this.level.isClientSide) {
            MenuProvider menuProvider = this;

            SpiritJob currentJob = this.job.orElse(null);
            if (currentJob instanceof MenuProvider)
                menuProvider = (MenuProvider) currentJob;

            NetworkHooks.openGui((ServerPlayer) playerEntity, menuProvider, (buf) -> buf.writeInt(this.getId()));
        }
    }
    //endregion Methods
}
