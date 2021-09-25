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
import com.github.klikli_dev.occultism.common.job.LumberjackJob;
import com.github.klikli_dev.occultism.common.job.SpiritJob;
import com.github.klikli_dev.occultism.exceptions.ItemHandlerMissingException;
import com.github.klikli_dev.occultism.registry.OccultismSounds;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fml.network.NetworkHooks;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nullable;
import javax.swing.text.html.Option;
import java.util.Optional;
import java.util.UUID;

public abstract class SpiritEntity extends TameableEntity implements ISkinnedCreatureMixin, INamedContainerProvider {
    //region Fields
    public static final DataParameter<Integer> SKIN = EntityDataManager
                                                              .defineId(SpiritEntity.class, DataSerializers.INT);
    /**
     * The default max age in seconds.
     */
    public static final int DEFAULT_MAX_AGE = -1;//default age is unlimited.
    public static final int MAX_FILTER_SLOTS = 7;
    private static final DataParameter<Optional<BlockPos>> DEPOSIT_POSITION =
            EntityDataManager.defineId(SpiritEntity.class, DataSerializers.OPTIONAL_BLOCK_POS);
    private static final DataParameter<Optional<UUID>> DEPOSIT_ENTITY_UUID =
            EntityDataManager.defineId(SpiritEntity.class, DataSerializers.OPTIONAL_UUID);
    private static final DataParameter<Direction> DEPOSIT_FACING =
            EntityDataManager.defineId(SpiritEntity.class, DataSerializers.DIRECTION);
    private static final DataParameter<Optional<BlockPos>> EXTRACT_POSITION =
            EntityDataManager.defineId(SpiritEntity.class, DataSerializers.OPTIONAL_BLOCK_POS);
    private static final DataParameter<Direction> EXTRACT_FACING =
            EntityDataManager.defineId(SpiritEntity.class, DataSerializers.DIRECTION);
    private static final DataParameter<Optional<BlockPos>> WORK_AREA_POSITION =
            EntityDataManager.defineId(SpiritEntity.class, DataSerializers.OPTIONAL_BLOCK_POS);
    private static final DataParameter<Integer> WORK_AREA_SIZE =
            EntityDataManager.defineId(SpiritEntity.class, DataSerializers.INT);
    /**
     * The spirit age in seconds.
     */
    private static final DataParameter<Integer> SPIRIT_AGE = EntityDataManager.defineId(SpiritEntity.class,
            DataSerializers.INT);
    /**
     * The max spirit age in seconds.
     */
    private static final DataParameter<Integer> SPIRIT_MAX_AGE = EntityDataManager.defineId(SpiritEntity.class,
            DataSerializers.INT);
    /**
     * The spirit job registry name/id.
     */
    private static final DataParameter<String> JOB_ID = EntityDataManager
                                                                .defineId(SpiritEntity.class, DataSerializers.STRING);

    /**
     * The filter mode (blacklist/whitelist)
     */
    private static final DataParameter<Boolean> IS_FILTER_BLACKLIST = EntityDataManager
                                                                .defineId(SpiritEntity.class, DataSerializers.BOOLEAN);

    /**
     * The filter item list
     */
    private static final DataParameter<CompoundNBT> FILTER_ITEMS = EntityDataManager
                                                                              .defineId(SpiritEntity.class, DataSerializers.COMPOUND_TAG);

    /**
     * The filter for tags
     */
    private static final DataParameter<String> TAG_FILTER = EntityDataManager
                                                                           .defineId(SpiritEntity.class, DataSerializers.STRING);

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
    public SpiritEntity(EntityType<? extends SpiritEntity> type, World worldIn) {
        super(type, worldIn);
        this.setPersistenceRequired();
    }
    //endregion Initialization

    @Override
    public void onSyncedDataUpdated(DataParameter<?> key) {
        super.onSyncedDataUpdated(key);

        if(key == FILTER_ITEMS){
            //restore filter item handler from data param on client
            if(this.level.isClientSide){
                this.filterItemStackHandler.ifPresent((handler) -> {
                    CompoundNBT compound = this.entityData.get(FILTER_ITEMS);
                    if(!compound.isEmpty())
                        handler.deserializeNBT(compound);
                });
            }
        }
        //if work area changes we clear the cached ignore list for trees
        //this allows players to manually reset that list if e.g. they tore down a wooden building and real trees grow there now.

        if(key.getId() == WORK_AREA_POSITION.getId() || key.getId() == WORK_AREA_SIZE.getId()){
            if(!this.level.isClientSide){
                this.job.map(j -> (LumberjackJob) j).ifPresent(j -> {
                    j.getIgnoredTrees().clear();
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
        if(position != null)
            this.entityData.set(DEPOSIT_ENTITY_UUID, Optional.empty());
    }

    public Optional<UUID> getDepositEntityUUID() {
        return this.entityData.get(DEPOSIT_ENTITY_UUID);
    }

    public void setDepositEntityUUID(UUID uuid) {
        this.entityData.set(DEPOSIT_ENTITY_UUID, Optional.ofNullable(uuid));
        if(uuid != null)
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
    public String getTagFilter(){
        return this.entityData.get(TAG_FILTER);
    }

    /**
     * Sets the tag filter string
     */
    public void setTagFilter(String tagFilter){
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
    public Container createMenu(int id, PlayerInventory playerInventory, PlayerEntity player) {
        return new SpiritContainer(id, playerInventory, this);
    }

    @Override
    public LivingEntity getEntity() {
        return this;
    }

    @Override
    public DataParameter<Integer> getDataParameterSkin() {
        return SKIN;
    }


    @Override
    public ILivingEntityData finalizeSpawn(IServerWorld worldIn, DifficultyInstance difficultyIn, SpawnReason reason,
                                            @Nullable ILivingEntityData spawnDataIn, @Nullable CompoundNBT dataTag) {
        this.selectRandomSkin();
        return super.finalizeSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
    }

    @Nullable
    @Override
    public AgeableEntity getBreedOffspring(ServerWorld serverWorld, AgeableEntity ageable) {
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
                    this.remove();
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
        }
        else {
            //copied from wolf
            Entity entity = source.getEntity();
            if (entity != null && !(entity instanceof PlayerEntity) && !(entity instanceof AbstractArrowEntity)) {
                amount = (amount + 1.0F) / 2.0F;
            }

            return super.hurt(source, amount);
        }
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new SwimGoal(this));
        this.goalSelector.addGoal(5, new MeleeAttackGoal(this, 1.0D, true));
        this.goalSelector.addGoal(9, new LookAtGoal(this, PlayerEntity.class, 8.0F, 1));
        this.goalSelector.addGoal(10, new LookRandomlyGoal(this));
        this.targetSelector.addGoal(3, new HurtByTargetGoal(this));
    }

    @Override
    public ItemStack getItemBySlot(EquipmentSlotType slotIn) {
        switch (slotIn) {
            case MAINHAND:
                return this.itemStackHandler.orElseThrow(ItemHandlerMissingException::new).getStackInSlot(0);
            default:
                return ItemStack.EMPTY;
        }
    }

    @Override
    public void setItemSlot(EquipmentSlotType slotIn, ItemStack stack) {
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
        this.entityData.define(FILTER_ITEMS, new CompoundNBT());
        this.entityData.define(TAG_FILTER, "");
    }


    @Override
    public void addAdditionalSaveData(CompoundNBT compound) {
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
    public void readAdditionalSaveData(CompoundNBT compound) {
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

        if(compound.contains("isFilterBlacklist")){
            this.setFilterBlacklist(compound.getBoolean("isFilterBlacklist"));
        }

        if(compound.contains("filterItems")){
            this.filterItemStackHandler.ifPresent(handler -> handler.deserializeNBT(compound.getCompound("filterItems")));
        }

        if(compound.contains("tagFilter")){
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
            ((ServerWorld) this.level)
                    .sendParticles(ParticleTypes.LARGE_SMOKE, this.getX(), this.getY() + 0.5, this.getZ(), 1,
                            0.0, 0.0, 0.0, 0.0);
            this.level.playSound(null, this.blockPosition(), OccultismSounds.START_RITUAL.get(), SoundCategory.NEUTRAL, 1,
                    1);

        }

        super.die(cause);
    }

    public void remove(boolean keepData) {
        this.removeJob();
        super.remove(keepData);
    }

    @Override
    public ActionResultType interactAt(PlayerEntity player, Vector3d vec, Hand hand) {
        ItemStack itemStack = player.getItemInHand(hand);

        if (itemStack.isEmpty()) {
            if (this.isTame() && player.isShiftKeyDown()) {
                this.openGUI(player);
                return ActionResultType.SUCCESS;
            }
        }
        return super.interactAt(player, vec, hand);
    }
    //endregion Overrides

    //region Static Methods
    public static AttributeModifierMap.MutableAttribute registerAttributes() {
        return TameableEntity.createLivingAttributes()
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

    public void openGUI(PlayerEntity playerEntity) {
        if (!this.level.isClientSide) {
            INamedContainerProvider containerProvider = this;

            SpiritJob currentJob = this.job.orElse(null);
            if(currentJob instanceof INamedContainerProvider)
                containerProvider = (INamedContainerProvider) currentJob;

            NetworkHooks.openGui((ServerPlayerEntity) playerEntity, containerProvider, (buf) -> buf.writeInt(this.getId()));
        }
    }
    //endregion Methods
}
