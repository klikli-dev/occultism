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
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.*;
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
import net.minecraft.util.DamageSource;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fml.network.NetworkHooks;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nullable;
import java.util.Optional;

public abstract class SpiritEntity extends TameableEntity implements ISkinnedCreatureMixin, INamedContainerProvider {
    //region Fields
    public static final DataParameter<Integer> SKIN = EntityDataManager
                                                              .createKey(SpiritEntity.class, DataSerializers.VARINT);
    /**
     * The default max age in seconds.
     */
    public static final int DEFAULT_MAX_AGE = -1;//default age is unlimited.
    private static final DataParameter<Optional<BlockPos>> DEPOSIT_POSITION = EntityDataManager
                                                                                      .createKey(SpiritEntity.class,
                                                                                              DataSerializers.OPTIONAL_BLOCK_POS);
    private static final DataParameter<Direction> DEPOSIT_FACING = EntityDataManager.createKey(SpiritEntity.class,
            DataSerializers.DIRECTION);
    private static final DataParameter<Optional<BlockPos>> WORK_AREA_POSITION = EntityDataManager
                                                                                        .createKey(SpiritEntity.class,
                                                                                                DataSerializers.OPTIONAL_BLOCK_POS);
    private static final DataParameter<Integer> WORK_AREA_SIZE = EntityDataManager.createKey(SpiritEntity.class,
            DataSerializers.VARINT);
    /**
     * The spirit age in seconds.
     */
    private static final DataParameter<Integer> SPIRIT_AGE = EntityDataManager.createKey(SpiritEntity.class,
            DataSerializers.VARINT);
    /**
     * The max spirit age in seconds.
     */
    private static final DataParameter<Integer> SPIRIT_MAX_AGE = EntityDataManager.createKey(SpiritEntity.class,
            DataSerializers.VARINT);
    /**
     * The spirit job registry name/id.
     */
    private static final DataParameter<String> JOB_ID = EntityDataManager
                                                                .createKey(SpiritEntity.class, DataSerializers.STRING);
    public LazyOptional<ItemStackHandler> itemStackHandler = LazyOptional.of(ItemStackHandler::new);
    protected Optional<SpiritJob> job = Optional.empty();
    protected boolean isInitialized = false;

    //endregion Fields
    //region Initialization
    public SpiritEntity(EntityType<? extends SpiritEntity> type, World worldIn) {
        super(type, worldIn);
        this.enablePersistence();
    }
    //endregion Initialization

    //region Getter / Setter
    public Optional<BlockPos> getDepositPosition() {
        return this.dataManager.get(DEPOSIT_POSITION);
    }

    public void setDepositPosition(BlockPos position) {
        this.dataManager.set(DEPOSIT_POSITION, Optional.ofNullable(position));
    }

    public Optional<BlockPos> getWorkAreaPosition() {
        return this.dataManager.get(WORK_AREA_POSITION);
    }

    public void setWorkAreaPosition(BlockPos position) {
        this.dataManager.set(WORK_AREA_POSITION, Optional.ofNullable(position));
    }

    public WorkAreaSize getWorkAreaSize() {
        return WorkAreaSize.get(this.dataManager.get(WORK_AREA_SIZE));
    }

    public void setWorkAreaSize(WorkAreaSize workAreaSize) {
        this.dataManager.set(WORK_AREA_SIZE, workAreaSize.getValue());
    }

    public BlockPos getWorkAreaCenter() {
        return this.getWorkAreaPosition().orElse(this.getPosition());
    }

    public Direction getDepositFacing() {
        return this.dataManager.get(DEPOSIT_FACING);
    }

    public void setDepositFacing(Direction depositFacing) {
        this.dataManager.set(DEPOSIT_FACING, depositFacing);
    }

    /**
     * @return the spirit age in seconds.
     */
    public int getSpiritAge() {
        return this.dataManager.get(SPIRIT_AGE);
    }

    /**
     * Sets the spirit age.
     *
     * @param seconds the spirit age in seconds.
     */
    public void setSpiritAge(int seconds) {
        this.dataManager.set(SPIRIT_AGE, seconds);
    }

    /**
     * @return the spirit max age in seconds.
     */
    public int getSpiritMaxAge() {
        return this.dataManager.get(SPIRIT_MAX_AGE);
    }

    /**
     * Sets the spirit max age.
     *
     * @param seconds the spirit max age in seconds.
     */
    public void setSpiritMaxAge(int seconds) {
        this.dataManager.set(SPIRIT_MAX_AGE, seconds);
    }

    /**
     * @return the spirit's job id.
     */
    public String getJobID() {
        return this.dataManager.get(JOB_ID);
    }

    /**
     * Sets the spirit's job id.
     *
     * @param id the job id string.
     */
    public void setJobID(String id) {
        this.dataManager.set(JOB_ID, id);
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
    public DataParameter<Integer> getDataParameterSkin() {
        return SKIN;
    }

    @Override
    public ILivingEntityData onInitialSpawn(IWorld worldIn, DifficultyInstance difficultyIn, SpawnReason reason,
                                            @Nullable ILivingEntityData spawnDataIn, @Nullable CompoundNBT dataTag) {
        this.selectRandomSkin();
        return super.onInitialSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
    }

    @Nullable
    @Override
    public AgeableEntity createChild(AgeableEntity ageable) {
        return null;
    }

    @Override
    public void livingTick() {
        if (!this.world.isRemote) {
            if (!this.isInitialized) {
                this.isInitialized = true;
                this.init();
            }

            //every 20 ticks = 1 second, age by 1 second
            if (this.world.getGameTime() % 20 == 0 && !this.dead && this.canDieFromAge()) {
                this.setSpiritAge(this.getSpiritAge() + 1);
                if (this.getSpiritAge() > this.getSpiritMaxAge()) {
                    this.onDeath(DamageSource.GENERIC);
                    this.remove();
                }
            }
            if (!this.dead)
                this.job.ifPresent(SpiritJob::update);
        }
        this.updateArmSwingProgress();
        super.livingTick();
    }

    @Override
    public boolean attackEntityFrom(DamageSource source, float amount) {
        if (this.isInvulnerableTo(source)) {
            return false;
        }
        else {
            //copied from wolf
            Entity entity = source.getTrueSource();
            if (entity != null && !(entity instanceof PlayerEntity) && !(entity instanceof AbstractArrowEntity)) {
                amount = (amount + 1.0F) / 2.0F;
            }

            return super.attackEntityFrom(source, amount);
        }
    }

    @Override
    public boolean processInteract(PlayerEntity player, Hand hand) {
        ItemStack itemStack = player.getHeldItem(hand);
        boolean flag = !itemStack.isEmpty();
        if (itemStack.isEmpty()) {
            if (this.isTamed() && player.isShiftKeyDown()) {
                this.openGUI(player);
                return true;
            }
        }
        return super.processInteract(player, hand);
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
    protected void registerAttributes() {
        super.registerAttributes();

        this.getAttributes().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(1.0);

        //set existing attributes
        this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.30000001192092896D);
        this.getAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(50);
    }

    @Override
    public ItemStack getItemStackFromSlot(EquipmentSlotType slotIn) {
        switch (slotIn) {
            case MAINHAND:
                return this.itemStackHandler.orElseThrow(ItemHandlerMissingException::new).getStackInSlot(0);
            default:
                return ItemStack.EMPTY;
        }
    }

    @Override
    public void setItemStackToSlot(EquipmentSlotType slotIn, ItemStack stack) {
        switch (slotIn) {
            case MAINHAND:
                this.itemStackHandler.orElseThrow(ItemHandlerMissingException::new).setStackInSlot(0, stack);
        }
    }

    @Override
    public boolean attackEntityAsMob(Entity entityIn) {
        //copied from wolf
        boolean flag = entityIn.attackEntityFrom(DamageSource.causeMobDamage(this),
                (float) ((int) this.getAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getValue()));
        if (flag) {
            this.applyEnchantments(this, entityIn);
        }

        return flag;
    }

    @Override
    protected void registerData() {
        super.registerData();
        this.registerSkinDataParameter();

        this.dataManager.register(DEPOSIT_POSITION, Optional.empty());
        this.dataManager.register(DEPOSIT_FACING, Direction.UP);
        this.dataManager.register(WORK_AREA_POSITION, Optional.empty());
        this.dataManager.register(WORK_AREA_SIZE, WorkAreaSize.SMALL.getValue());
        this.dataManager.register(SPIRIT_AGE, 0);
        this.dataManager.register(SPIRIT_MAX_AGE, DEFAULT_MAX_AGE);
        this.dataManager.register(JOB_ID, "");
    }

    @Override
    public void writeAdditional(CompoundNBT compound) {
        super.writeAdditional(compound);

        //Store age
        compound.putInt("spiritAge", this.getSpiritAge());
        compound.putInt("spiritMaxAge", this.getSpiritMaxAge());

        //store work area position
        this.getWorkAreaPosition().ifPresent(pos -> compound.putLong("workAreaPosition", pos.toLong()));
        //store deposit info
        this.getDepositPosition().ifPresent(pos -> compound.putLong("depositPosition", pos.toLong()));
        compound.putInt("depositFacing", this.getDepositFacing().ordinal());
        compound.putInt("workAreaSize", this.getWorkAreaSize().getValue());

        //store current inventory
        this.itemStackHandler.ifPresent(handler -> compound.put("inventory", handler.serializeNBT()));

        //store job
        this.job.ifPresent(job -> compound.put("spiritJob", job.serializeNBT()));
    }

    @Override
    public void readAdditional(CompoundNBT compound) {
        super.readAdditional(compound);

        //read age
        if (compound.contains("spiritAge")) {
            this.setSpiritAge(compound.getInt("spiritAge"));
        }
        if (compound.contains("spiritMaxAge")) {
            this.setSpiritMaxAge(compound.getInt("spiritMaxAge"));
        }

        //read base position
        if (compound.contains("workAreaPosition")) {
            this.setWorkAreaPosition(BlockPos.fromLong(compound.getLong("workAreaPosition")));
        }

        //read deposit information
        if (compound.contains("depositPosition")) {
            this.setDepositPosition(BlockPos.fromLong(compound.getLong("depositPosition")));
            if (compound.contains("depositFacing")) {
                this.setDepositFacing(Direction.values()[compound.getInt("depositFacing")]);
            }
        }
        if (compound.contains("workAreaSize"))
            this.setWorkAreaSize(WorkAreaSize.get(compound.getInt("workAreaSize")));

        //set up inventory and read items

        if (compound.contains("inventory")) {
            this.itemStackHandler.ifPresent(handler -> handler.deserializeNBT(compound.getCompound("inventory")));
        }

        //read job
        if (compound.contains("spiritJob")) {
            SpiritJob job = SpiritJob.from(this, compound.getCompound("spiritJob"));
            this.setJob(job);
        }
    }

    @Override
    public void setTamed(boolean tamed) {
        super.setTamed(tamed);
        if (!tamed)
            this.setJob(null); //remove job if not tamed
    }

    @Override
    public void onDeath(DamageSource cause) {
        if (!this.world.isRemote) {
            if (this.isTamed()) {
                BookOfCallingItem.spiritDeathRegister.put(this.entityUniqueID, this.world.getGameTime());
            }

            //drop inventory on death
            this.itemStackHandler.ifPresent((handle) -> {
                for (int i = 0; i < handle.getSlots(); ++i) {
                    ItemStack itemstack = handle.getStackInSlot(i);
                    if (!itemstack.isEmpty()) {
                        this.entityDropItem(itemstack, 0.0F);
                    }
                }
            });

            this.removeJob();

            //Death sound and particle effects
            ((ServerWorld) this.world)
                    .spawnParticle(ParticleTypes.LARGE_SMOKE, this.getPosX(), this.getPosY() + 0.5, this.getPosZ(), 1,
                            0.0, 0.0, 0.0, 0.0);
            this.world.playSound(null, this.getPosition(), OccultismSounds.START_RITUAL.get(), SoundCategory.NEUTRAL, 1,
                    1);

        }

        super.onDeath(cause);
    }

    public void remove(boolean keepData) {
        this.removeJob();
        super.remove(keepData);
    }

    //endregion Overrides

    //region Methods

    public void removeJob() {
        this.job.ifPresent(SpiritJob::cleanup);
        this.job = Optional.empty();
    }

    /**
     * @return true if the spirit has a max age and can die from age.
     */
    public boolean canDieFromAge() {
        return this.dataManager.get(SPIRIT_MAX_AGE) > -1;
    }

    public void init() {
        this.job.ifPresent(SpiritJob::init);
    }

    public boolean canPickupItem(ItemStack stack) {
        return this.job.map(job -> job.canPickupItem(stack)).orElse(false);
    }

    public void openGUI(PlayerEntity playerEntity) {
        if (!this.world.isRemote) {
            NetworkHooks.openGui((ServerPlayerEntity) playerEntity, this, (buf) -> buf.writeInt(this.getEntityId()));
        }
    }
    //endregion Methods
}
