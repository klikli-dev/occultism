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

package com.github.klikli_dev.occultism.common.entity.spirits;

import com.github.klikli_dev.occultism.Occultism;
import com.github.klikli_dev.occultism.common.entity.ISkinnedCreatureMixin;
import com.github.klikli_dev.occultism.common.item.ItemBookOfCallingActive;
import com.github.klikli_dev.occultism.common.jobs.SpiritJob;
import com.github.klikli_dev.occultism.common.jobs.SpiritJobFactory;
import com.github.klikli_dev.occultism.handler.GuiHandler;
import com.github.klikli_dev.occultism.network.MessageParticle;
import com.github.klikli_dev.occultism.registry.SoundRegistry;
import com.google.common.base.Optional;
import net.minecraft.entity.*;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.ai.goal.HurtByTargetGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.inventory.ContainerHorseChest;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;

import javax.annotation.Nullable;

public abstract class EntitySpirit extends TameableEntity implements ISkinnedCreatureMixin {
    //region Fields
    public static final DataParameter<Integer> SKIN = EntityDataManager
                                                              .createKey(EntitySpirit.class, DataSerializers.VARINT);
    /**
     * The default max age in seconds.
     */
    public static final int DEFAULT_MAX_AGE = -1;//default age is unlimited.
    private static final DataParameter<Optional<BlockPos>> DEPOSIT_POSITION = EntityDataManager
                                                                                      .createKey(EntitySpirit.class,
                                                                                              DataSerializers.OPTIONAL_BLOCK_POS);
    private static final DataParameter<Direction> DEPOSIT_FACING = EntityDataManager.createKey(EntitySpirit.class,
            DataSerializers.FACING);
    private static final DataParameter<Optional<BlockPos>> WORK_AREA_POSITION = EntityDataManager
                                                                                        .createKey(EntitySpirit.class,
                                                                                                DataSerializers.OPTIONAL_BLOCK_POS);
    private static final DataParameter<Integer> WORK_AREA_SIZE = EntityDataManager.createKey(EntitySpirit.class,
            DataSerializers.VARINT);
    /**
     * The spirit age in seconds.
     */
    private static final DataParameter<Integer> SPIRIT_AGE = EntityDataManager.createKey(EntitySpirit.class,
            DataSerializers.VARINT);
    /**
     * The max spirit age in seconds.
     */
    private static final DataParameter<Integer> SPIRIT_MAX_AGE = EntityDataManager.createKey(EntitySpirit.class,
            DataSerializers.VARINT);
    /**
     * The spirit job registry name/id.
     */
    private static final DataParameter<String> JOB_ID = EntityDataManager
                                                                .createKey(EntitySpirit.class, DataSerializers.STRING);


    public ContainerHorseChest inventory;
    protected SpiritJob job;
    protected boolean isInitialized = false;
    //endregion Fields

    //region Initialization
    public EntitySpirit(World worldIn) {
        super(worldIn);
        this.enablePersistence();
        this.setupInventory();
    }
    //endregion Initialization

    //region Getter / Setter

    public BlockPos getDepositPosition() {
        return this.dataManager.get(DEPOSIT_POSITION).orNull();
    }

    public void setDepositPosition(BlockPos position) {
        this.dataManager.set(DEPOSIT_POSITION, Optional.fromNullable(position));
    }

    public BlockPos getWorkAreaPosition() {
        return this.dataManager.get(WORK_AREA_POSITION).orNull();
    }

    public void setWorkAreaPosition(BlockPos position) {
        this.dataManager.set(WORK_AREA_POSITION, Optional.fromNullable(position));
    }

    public WorkAreaSize getWorkAreaSize() {
        return WorkAreaSize.get(this.dataManager.get(WORK_AREA_SIZE));
    }

    public void setWorkAreaSize(WorkAreaSize workAreaSize) {
        this.dataManager.set(WORK_AREA_SIZE, workAreaSize.getValue());
    }

    public BlockPos getWorkAreaCenter() {
        BlockPos basePos = this.getWorkAreaPosition();
        if (basePos == null) {
            return this.getPosition();
        }
        else {
            return basePos;
        }
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
     * @param age the spirit age in seconds.
     */
    public void setSpiritAge(int age) {
        this.dataManager.set(SPIRIT_AGE, age);
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
     * @param maxAge the spirit max age in seconds.
     */
    public void setSpiritMaxAge(int maxAge) {
        this.dataManager.set(SPIRIT_MAX_AGE, maxAge);
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

    public SpiritJob getJob() {
        return this.job;
    }

    /**
     * Cleans up old job and sets and initializes the new job.
     *
     * @param job the new job, should already be initialized
     */
    public void setJob(SpiritJob job) {
        if (this.job != null)
            this.job.cleanup();
        this.job = job;
        if (this.job != null)
            this.setJobID(this.job.getFactoryID().toString());
    }
    //endregion Getter / Setter

    //region Overrides
    @Override
    public DataParameter<Integer> getDataParameterSkin() {
        return SKIN;
    }

    @Override
    protected void initEntityAI() {
        this.tasks.addTask(1, new SwimGoal(this));
        this.tasks.addTask(5, new MeleeAttackGoal(this, 1.0D, true));
        this.tasks.addTask(9, new EntityAIWatchClosest2(this, PlayerEntity.class, 8.0F, 1));
        this.tasks.addTask(10, new LookRandomlyGoal(this));
        this.targetTasks.addTask(3, new HurtByTargetGoal(this, true));
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        //register attack damage because it does not exist on creatures
        this.getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(1.0);

        //set existing attributes
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.30000001192092896D);
        this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(50);
    }

    @Override
    public ItemStack getItemStackFromSlot(EquipmentSlotType slotIn) {
        if (slotIn == EquipmentSlotType.MAINHAND) {
            return this.inventory.getStackInSlot(0);
        }
        return super.getItemStackFromSlot(slotIn);
    }

    @Override
    public void setItemStackToSlot(EquipmentSlotType slotIn, ItemStack stack) {
        if (slotIn == EquipmentSlotType.MAINHAND) {
            this.inventory.setInventorySlotContents(0, stack);
        }
        else {
            super.getItemStackFromSlot(slotIn);
        }
    }

    @Nullable
    @Override
    public ILivingEntityData onInitialSpawn(DifficultyInstance difficulty, @Nullable ILivingEntityData livingData) {
        this.selectRandomSkin();
        return super.onInitialSpawn(difficulty, livingData);
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.registerSkinDataParameter();

        this.dataManager.register(DEPOSIT_POSITION, Optional.absent());
        this.dataManager.register(DEPOSIT_FACING, Direction.UP);
        this.dataManager.register(WORK_AREA_POSITION, Optional.absent());
        this.dataManager.register(WORK_AREA_SIZE, WorkAreaSize.SMALL.getValue());
        this.dataManager.register(SPIRIT_AGE, 0);
        this.dataManager.register(SPIRIT_MAX_AGE, DEFAULT_MAX_AGE);
        this.dataManager.register(JOB_ID, "");
    }

    @Override
    public void writeEntityToNBT(CompoundNBT compound) {
        super.writeEntityToNBT(compound);

        //Store age
        compound.setInteger("spiritAge", this.getSpiritAge());
        compound.setInteger("spiritMaxAge", this.getSpiritMaxAge());

        //store work area position
        BlockPos workAreaPosition = this.getWorkAreaPosition();
        if (workAreaPosition != null) {
            compound.setLong("workAreaPosition", workAreaPosition.toLong());
        }

        //store deposit info
        BlockPos depositPosition = this.getDepositPosition();
        if (depositPosition != null) {
            compound.setLong("depositPosition", depositPosition.toLong());
            compound.setInteger("depositFacing", this.getDepositFacing().ordinal());
        }

        compound.setInteger("workAreaSize", this.getWorkAreaSize().getValue());

        //store current inventory
        if (this.inventory != null) {
            ListNBT nbttaglist = new ListNBT();
            for (int i = 0; i < this.inventory.getSizeInventory(); ++i) {
                ItemStack itemstack = this.inventory.getStackInSlot(i);
                if (!itemstack.isEmpty()) {
                    CompoundNBT itemCompound = new CompoundNBT();
                    itemCompound.setByte("slot", (byte) i);
                    itemstack.writeToNBT(itemCompound);
                    nbttaglist.appendTag(itemCompound);
                }
            }
            compound.setTag("items", nbttaglist);
        }

        //store job
        if (this.hasJob()) {
            CompoundNBT jobCompound = new CompoundNBT();
            jobCompound.setString("factoryId", this.job.getFactoryID().toString());
            this.job.writeJobToNBT(jobCompound);
            compound.setTag("spiritJob", jobCompound);
        }
    }

    @Override
    public void readEntityFromNBT(CompoundNBT compound) {
        super.readEntityFromNBT(compound);

        //read age
        if (compound.hasKey("spiritAge")) {
            this.setSpiritAge(compound.getInteger("spiritAge"));
        }
        if (compound.hasKey("spiritMaxAge")) {
            this.setSpiritMaxAge(compound.getInteger("spiritMaxAge"));
        }

        //read base position
        if (compound.hasKey("workAreaPosition")) {
            this.setWorkAreaPosition(BlockPos.fromLong(compound.getLong("workAreaPosition")));
        }

        //read deposit information
        if (compound.hasKey("depositPosition")) {
            this.setDepositPosition(BlockPos.fromLong(compound.getLong("depositPosition")));
            if (compound.hasKey("depositFacing")) {
                this.setDepositFacing(Direction.values()[compound.getInteger("depositFacing")]);
            }
        }

        if (compound.hasKey("workAreaSize"))
            this.setWorkAreaSize(WorkAreaSize.get(compound.getInteger("workAreaSize")));

        //set up inventory and read items
        this.setupInventory();
        ListNBT nbttaglist = compound.getTagList("items", 10);
        for (int i = 0; i < nbttaglist.tagCount(); ++i) {
            CompoundNBT itemCompound = nbttaglist.getCompoundTagAt(i);
            int j = itemCompound.getByte("slot") & 255;
            this.inventory.setInventorySlotContents(j, new ItemStack(itemCompound));
        }

        //read job
        if (compound.hasKey("spiritJob")) {
            CompoundNBT jobCompound = compound.getCompoundTag("spiritJob");
            SpiritJobFactory factory = GameRegistry.findRegistry(SpiritJobFactory.class)
                                               .getValue(new ResourceLocation(jobCompound.getString("factoryId")));
            SpiritJob job = factory.create(this);
            if (job != null) {
                job.readJobFromNBT(jobCompound);
                this.setJob(job);
            }
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
                ItemBookOfCallingActive.spiritDeathRegister.put(this.entityUniqueID, this.world.getTotalWorldTime());
            }

            //drop inventory on death
            if (this.inventory != null) {
                for (int i = 0; i < this.inventory.getSizeInventory(); ++i) {
                    ItemStack itemstack = this.inventory.getStackInSlot(i);
                    if (!itemstack.isEmpty()) {
                        this.entityDropItem(itemstack, 0.0F);
                    }
                }
            }

            //Death sound and particle effects
            Occultism.network.sendToDimension(
                    new MessageParticle(EnumParticleTypes.SMOKE_LARGE, this.posX, this.posY + 0.5, this.posZ),
                    this.world.provider.getDimension());
            this.world.playSound(null, this.getPosition(), SoundRegistry.START_RITUAL, SoundCategory.BLOCKS, 1, 1);

        }

        super.onDeath(cause);
    }

    @Override
    public void setDead() {
        if (this.job != null)
            this.job.cleanup();
        super.setDead();
    }

    @Override
    public boolean attackEntityAsMob(Entity entityIn) {
        //copied from wolf
        boolean flag = entityIn.attackEntityFrom(DamageSource.causeMobDamage(this),
                (float) ((int) this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue()));
        if (flag) {
            this.applyEnchantments(this, entityIn);
        }

        return flag;
    }

    @Nullable
    @Override
    public AgeableEntity createChild(AgeableEntity ageable) {
        return null;
    }

    @Override
    public void onLivingUpdate() {

        if (!this.world.isRemote) {
            if (!this.isInitialized) {
                this.isInitialized = true;
                this.init();
            }

            //every 20 ticks = 1 second, age by 1 second
            if (this.world.getTotalWorldTime() % 20 == 0 && !this.isDead && this.canDieFromAge()) {
                this.setSpiritAge(this.getSpiritAge() + 1);
                if (this.getSpiritAge() > this.getSpiritMaxAge()) {
                    this.onDeath(DamageSource.GENERIC);
                    this.setDead();
                }
            }
            if (this.job != null && !this.isDead)
                this.job.update();
        }
        super.onLivingUpdate();
    }

    @Override
    public boolean attackEntityFrom(DamageSource source, float amount) {
        if (this.isEntityInvulnerable(source)) {
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
            if (this.isTamed() && player.isSneaking()) {
                this.openGUI(player);
                return true;
            }
        }
        return super.processInteract(player, hand);
    }

    //endregion Overrides

    //region Methods

    /**
     * @return true if the spirit has a max age and can die from age.
     */
    public boolean canDieFromAge() {
        return this.dataManager.get(SPIRIT_MAX_AGE) > -1;
    }

    public void init() {
        if (this.job != null)
            this.job.init();
    }

    public boolean hasJob() {
        return this.job != null;
    }

    public boolean canPickupItem(ItemStack stack) {
        if (this.hasJob())
            return this.job.canPickupItem(stack);
        return false;
    }

    public void openGUI(PlayerEntity playerEntity) {
        if (!this.world.isRemote) {
            playerEntity
                    .openGui(Occultism.instance, GuiHandler.GuiID.SPIRIT.ordinal(), this.world, this.getEntityId(), 0,
                            0);
        }
    }

    protected void setupInventory() {
        this.inventory = new ContainerHorseChest("spiritInventory", 1);
        this.inventory.setCustomName(this.getName());
    }
    //endregion Methods
}
