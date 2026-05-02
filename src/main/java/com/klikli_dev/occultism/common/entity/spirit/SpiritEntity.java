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

import com.klikli_dev.occultism.api.common.data.WorkAreaSize;
import com.klikli_dev.occultism.common.container.spirit.SpiritContainer;
import com.klikli_dev.occultism.common.item.filter.EntityItemFilter;
import com.klikli_dev.occultism.common.entity.IFilterConfigurable;
import com.klikli_dev.occultism.common.entity.ai.BrainUtil;
import com.klikli_dev.occultism.common.entity.job.SpiritJob;
import com.klikli_dev.occultism.common.item.spirit.BookOfCallingItem;
import com.klikli_dev.occultism.registry.OccultismMemoryTypes;
import com.klikli_dev.occultism.registry.OccultismSounds;
import com.klikli_dev.occultism.registry.OccultismTags.Items;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.TagParser;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.ProblemReporter;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.Brain.Packed;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.arrow.AbstractArrow;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.storage.TagValueInput;
import net.minecraft.world.level.storage.TagValueOutput;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.neoforged.neoforge.transfer.item.ItemResource;
import net.neoforged.neoforge.transfer.item.ItemStacksResourceHandler;
import net.neoforged.neoforge.transfer.transaction.Transaction;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.Optional;

public abstract class SpiritEntity extends TamableAnimal implements ISkinnedCreatureMixin, MenuProvider, IFilterConfigurable {
    public static final EntityDataAccessor<Integer> SKIN = SynchedEntityData
            .defineId(SpiritEntity.class, EntityDataSerializers.INT);
    /**
     * The default max age in seconds.
     */
    public static final int DEFAULT_MAX_AGE = -1;//default age is unlimited.
    public static final int LEGACY_MAX_FILTER_SLOTS = 14;
    /**
     * The spirit job registry name/id.
     */
    protected static final EntityDataAccessor<String> JOB_ID = SynchedEntityData
            .defineId(SpiritEntity.class, EntityDataSerializers.STRING);
    private static final EntityDataAccessor<Optional<BlockPos>> DEPOSIT_POSITION =
            SynchedEntityData.defineId(SpiritEntity.class, EntityDataSerializers.OPTIONAL_BLOCK_POS);
    private static final EntityDataAccessor<Optional<EntityReference<LivingEntity>>> DEPOSIT_ENTITY_UUID =
            SynchedEntityData.defineId(SpiritEntity.class, EntityDataSerializers.OPTIONAL_LIVING_ENTITY_REFERENCE);
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
    private static final EntityDataAccessor<String> FILTER_ITEM = SynchedEntityData
            .defineId(SpiritEntity.class, EntityDataSerializers.STRING);

    public ItemStacksResourceHandler inventory;
    public ItemStacksResourceHandler filterItemStackHandler = new ItemStacksResourceHandler(1) {
        @Override
        protected void onContentsChanged(int slot, @NotNull ItemStack previousContents) {
            super.onContentsChanged(slot, previousContents);
            TagValueOutput tagOutput = TagValueOutput.createWithContext(ProblemReporter.DISCARDING, SpiritEntity.this.level().registryAccess());
            this.serialize(tagOutput);
            SpiritEntity.this.entityData.set(FILTER_ITEM, tagOutput.buildResult().toString());
        }
    };

    //initialized in getter, because super constructor already accesses it
    protected Optional<SpiritJob> job;
    protected boolean isInitialized = false;

    public SpiritEntity(EntityType<? extends SpiritEntity> type, Level worldIn) {
        this(type, worldIn, new ItemStacksResourceHandler(1));
    }

    public SpiritEntity(EntityType<? extends SpiritEntity> type, Level worldIn, ItemStacksResourceHandler inventory) {
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
    protected Brain<?> makeBrain(Packed packedBrain) {
        if (this.getJob().isEmpty()) {
            return new Brain<>();
        }

        SpiritJob job = this.getJob().get();
        Brain<SpiritEntity> brain = Brain.provider(job.getMemoryTypes(), job.getSensorTypes(), entity -> job.getActivityData())
                .makeBrain(this, packedBrain);
        job.handleAdditionalBrainSetup(brain);
        return brain;
    }

    @Override
    protected void customServerAiStep(ServerLevel level) {
        this.getBrain().tick(level, this);
    }

    @Override
    public Brain<SpiritEntity> getBrain() {
        return (Brain<SpiritEntity>) super.getBrain();
    }

    @Override
    public void onSyncedDataUpdated(EntityDataAccessor<?> key) {
        super.onSyncedDataUpdated(key);

        if (key == FILTER_ITEM) {
            if (this.level().isClientSide()) {
                String compoundStr = this.entityData.get(FILTER_ITEM);
                try {
                    CompoundTag compound = compoundStr.isEmpty() ? new CompoundTag() : TagParser.parseCompoundFully(compoundStr);
                    compound.putInt("Size", 1);
                    ValueInput valueInput = TagValueInput.create(ProblemReporter.DISCARDING, this.level().registryAccess(), compound);
                    this.filterItemStackHandler.deserialize(valueInput);
                } catch (Exception e) {
                    // ignore parse errors
                }
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

        BrainUtil.setMemory(this, OccultismMemoryTypes.DEPOSIT_POSITION.get(), position);
    }

    public Optional<EntityReference<LivingEntity>> getDepositEntityUUID() {
        return this.entityData.get(DEPOSIT_ENTITY_UUID);
    }

    public void setDepositEntityUUID(EntityReference<LivingEntity> ref) {
        this.entityData.set(DEPOSIT_ENTITY_UUID, Optional.ofNullable(ref));
        if (ref != null)
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
        BrainUtil.setMemory(this, OccultismMemoryTypes.WORK_AREA_CENTER.get(), this.getWorkAreaCenter());

        this.getJob().ifPresent(SpiritJob::onChangeWorkArea);
    }

    public WorkAreaSize getWorkAreaSize() {
        return WorkAreaSize.get(this.entityData.get(WORK_AREA_SIZE));
    }

    public void setWorkAreaSize(WorkAreaSize workAreaSize) {
        this.entityData.set(WORK_AREA_SIZE, workAreaSize.ordinal()); //for the entity data set the
        BrainUtil.setMemory(this, OccultismMemoryTypes.WORK_AREA_SIZE.get(), this.getWorkAreaSize().getValue());

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
        BrainUtil.setMemory(this, OccultismMemoryTypes.DEPOSIT_FACING.get(), depositFacing);
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

    @Override
    public ItemStacksResourceHandler getFilterItems() {
        return this.filterItemStackHandler;
    }

    private void setFilterItem(ItemStack stack) {
        try (var tx = Transaction.openRoot()) {
            if (stack.isEmpty()) {
                this.filterItemStackHandler.set(0, ItemResource.EMPTY, 0);
            } else {
                this.filterItemStackHandler.set(0, ItemResource.of(stack.copyWithCount(1)), 1);
            }
            tx.commit();
        }
    }

    @Override
    public ItemStacksResourceHandler getInventory() {
        return this.inventory;
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
        } else {
            this.setJobID("");
            if (recreateBrain) {
                this.remakeBrain();
            }
        }
    }

    public void remakeBrain() {
        this.brain = this.makeBrain(Packed.EMPTY);
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
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor worldIn, DifficultyInstance difficultyIn, EntitySpawnReason reason,
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
        if (!this.level().isClientSide()) {
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
            if (this.getOwner() != null && this.getOwner().distanceTo(this.getEntity()) < 10
                    && this.getDeltaMovement().x() == 0 && this.getDeltaMovement().z() == 0)
                this.getLookControl().setLookAt(this.getOwner(), 10, this.getMaxHeadXRot());
        }
        this.updateSwingTime();
        super.aiStep();
    }

    @Override
    public boolean isInvulnerableTo(ServerLevel level, @NotNull DamageSource source) {
        return super.isInvulnerableTo(level, source) || source.is(DamageTypes.IN_WALL) || source.is(DamageTypes.FLY_INTO_WALL);
    }

    @Override
    protected void actuallyHurt(ServerLevel level, DamageSource source, float amount) {
        //copied from wolf
        Entity entity = source.getEntity();
        if (entity != null && !(entity instanceof Player) && !(entity instanceof AbstractArrow)) {
            amount = (amount + 1.0F) / 2.0F;
        }
        super.actuallyHurt(level, source, amount);
    }


    @Override
    protected void registerGoals() {
        //none, we use a brain
    }

    @Override
    public ItemStack getItemBySlot(EquipmentSlot slotIn) {
        if (slotIn == EquipmentSlot.MAINHAND) {
            return this.inventory.getResource(0).toStack(this.inventory.getAmountAsInt(0));
        }
        return ItemStack.EMPTY;
    }

    @Override
    public void setItemSlot(EquipmentSlot slotIn, ItemStack stack) {
        if (slotIn == EquipmentSlot.MAINHAND) {
            try (var tx = Transaction.openRoot()) {
                this.inventory.set(0, ItemResource.of(stack), stack.getCount());
                tx.commit();
            }
        }
    }

    @Override
    protected void defineSynchedData(Builder builder) {
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
        builder.define(FILTER_ITEM, "");
    }

    @Override
    public void addAdditionalSaveData(ValueOutput output) {
        super.addAdditionalSaveData(output);

        //Store age
        output.putInt("spiritAge", this.getSpiritAge());
        output.putInt("spiritMaxAge", this.getSpiritMaxAge());

        //store work area position
        this.getWorkAreaPosition().ifPresent(pos -> output.putLong("workAreaPosition", pos.asLong()));
        output.putInt("workAreaSize", this.getWorkAreaSize().ordinal());

        //store deposit info
        this.getDepositPosition().ifPresent(pos -> output.putLong("depositPosition", pos.asLong()));
        this.getDepositEntityUUID().ifPresent(ref -> EntityReference.store(ref, output, "depositEntityRef"));
        output.putInt("depositFacing", this.getDepositFacing().ordinal());

        //store extract info
        this.getExtractPosition().ifPresent(pos -> output.putLong("extractPosition", pos.asLong()));
        output.putInt("extractFacing", this.getExtractFacing().ordinal());

        //store current inventory
        {
            TagValueOutput inv = TagValueOutput.createWithContext(ProblemReporter.DISCARDING, this.level().registryAccess());
            this.inventory.serialize(inv);
            output.store("inventory", CompoundTag.CODEC, inv.buildResult());
        }

        //store job
        this.getJob().ifPresent(job -> output.store("spiritJob", CompoundTag.CODEC, job.writeJobToNBT(new CompoundTag(), this.level().registryAccess())));

        {
            TagValueOutput filterOut = TagValueOutput.createWithContext(ProblemReporter.DISCARDING, this.level().registryAccess());
            this.filterItemStackHandler.serialize(filterOut);
            output.store("filterItem", CompoundTag.CODEC, filterOut.buildResult());
        }
    }

    @Override
    public void readAdditionalSaveData(ValueInput input) {
        super.readAdditionalSaveData(input);

        //read age
        input.getInt("spiritAge").ifPresent(this::setSpiritAge);
        input.getInt("spiritMaxAge").ifPresent(this::setSpiritMaxAge);

        //read base position
        input.getLong("workAreaPosition").ifPresent(l -> this.setWorkAreaPosition(BlockPos.of(l)));
        input.getInt("workAreaSize").ifPresent(i -> this.setWorkAreaSize(WorkAreaSize.get(i)));

        //read deposit information
        input.getLong("depositPosition").ifPresent(l -> this.setDepositPosition(BlockPos.of(l)));
        Optional.ofNullable(EntityReference.<LivingEntity>readWithOldOwnerConversion(input, "depositEntityRef", this.level()))
                .ifPresent(this::setDepositEntityUUID);
        input.getInt("depositFacing").ifPresent(i -> this.setDepositFacing(Direction.values()[i]));

        //read extract information
        input.getLong("extractPosition").ifPresent(l -> this.setExtractPosition(BlockPos.of(l)));
        input.getInt("extractFacing").ifPresent(i -> this.setExtractFacing(Direction.values()[i]));

        //set up inventory and read items
        input.read("inventory", CompoundTag.CODEC).ifPresent(tag -> {
            ValueInput invInput = TagValueInput.create(ProblemReporter.DISCARDING, this.level().registryAccess(), tag);
            this.inventory.deserialize(invInput);
        });

        //read job
        input.read("spiritJob", CompoundTag.CODEC).ifPresent(tag -> {
            SpiritJob job = SpiritJob.from(this, tag);
            Packed packedBrain = input.read("Brain", Packed.CODEC).orElse(Packed.EMPTY);
            this.setJob(job, false);
            this.brain = this.makeBrain(packedBrain);
        });

        input.read("filterItem", CompoundTag.CODEC).ifPresent(tag -> {
            tag.putInt("Size", 1);
            ValueInput filterInput = TagValueInput.create(ProblemReporter.DISCARDING, this.level().registryAccess(), tag);
            this.filterItemStackHandler.deserialize(filterInput);
        });

        if (this.getFilterItem().isEmpty()) {
            boolean legacyBlacklist = input.getBooleanOr("isFilterBlacklist", false);
            String legacyTagFilter = input.getString("tagFilter").orElse("");
            ItemStacksResourceHandler legacyFilterItems = new ItemStacksResourceHandler(LEGACY_MAX_FILTER_SLOTS);
            input.read("filterItems", CompoundTag.CODEC).ifPresent(tag -> {
                tag.putInt("Size", LEGACY_MAX_FILTER_SLOTS);
                ValueInput filterInput = TagValueInput.create(ProblemReporter.DISCARDING, this.level().registryAccess(), tag);
                legacyFilterItems.deserialize(filterInput);
            });

            this.setFilterItem(EntityItemFilter.createLegacyFilterItem(legacyFilterItems, legacyTagFilter, legacyBlacklist));
        }
    }

    @Override
    public void setTame(boolean pTame, boolean pApplyTamingSideEffects) {
        super.setTame(pTame, pApplyTamingSideEffects);
        if (!pTame)
            this.setJob(null); //remove job if not tamed
    }

    @Override
    protected void dropEquipment(ServerLevel level) {
        super.dropEquipment(level);
        for (int i = 0; i < this.inventory.size(); ++i) {
            ItemStack itemstack = this.inventory.getResource(i).toStack(this.inventory.getAmountAsInt(i));
            if (!itemstack.isEmpty()) {
                this.spawnAtLocation(level, itemstack, 0.0F);
            }
        }

        ItemStack filterItem = this.getFilterItem();
        if (!filterItem.isEmpty()) {
            this.spawnAtLocation(level, filterItem.copy(), 0.0F);
        }
    }

    @Override
    public void die(DamageSource cause) {
        if (!this.level().isClientSide()) {
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
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack itemStack = player.getItemInHand(hand);

        if (!(itemStack.is(Items.BOOK_OF_CALLING_FOLIOT) || itemStack.is(Items.BOOK_OF_CALLING_DJINNI))) {
            if (!this.isTame())
                this.tame(player);

            if (player.isShiftKeyDown() && this.getOwner() == player) {
                this.openScreen(player);
                return InteractionResult.SUCCESS;
            }
        }

        return super.mobInteract(player, hand);
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
        if (!this.level().isClientSide()) {
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
