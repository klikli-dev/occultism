/*
 * MIT License
 *
 * Copyright 2021 vemerion
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

package com.github.klikli_dev.occultism.common.entity;

import java.util.Collections;
import java.util.EnumSet;
import java.util.List;

import com.github.klikli_dev.occultism.common.advancement.FamiliarTrigger;
import com.github.klikli_dev.occultism.registry.OccultismAdvancements;
import com.github.klikli_dev.occultism.registry.OccultismEffects;
import com.google.common.collect.ImmutableList;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.goal.FollowMobGoal;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.PanicGoal;
import net.minecraft.entity.ai.goal.RandomWalkingGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;
import net.minecraftforge.common.Tags;
import net.minecraftforge.items.ItemHandlerHelper;

public class DragonFamiliarEntity extends FamiliarEntity {

    private static final int GREEDY_INCREMENT = 20 * 60 * 5;

    public static final int MAX_PET_TIMER = 20 * 2;

    private static final DataParameter<Boolean> FEZ = EntityDataManager.createKey(DragonFamiliarEntity.class,
            DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> EARS = EntityDataManager.createKey(DragonFamiliarEntity.class,
            DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> ARMS = EntityDataManager.createKey(DragonFamiliarEntity.class,
            DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> STICK = EntityDataManager.createKey(DragonFamiliarEntity.class,
            DataSerializers.BOOLEAN);

    private final float colorOffset;
    private int greedyTimer;
    private int flyingTimer, flyingTimer0, wingspan, wingspan0;
    private int petTimer;

    public DragonFamiliarEntity(EntityType<? extends DragonFamiliarEntity> type, World worldIn) {
        super(type, worldIn);
        this.colorOffset = this.getRNG().nextFloat() * 2;
        this.petTimer = MAX_PET_TIMER;
    }

    @Override
    public void setFamiliarOwner(LivingEntity owner) {
        if (this.hasFez())
            OccultismAdvancements.FAMILIAR.trigger(owner, FamiliarTrigger.Type.RARE_VARIANT);
        super.setFamiliarOwner(owner);
    }

    @Override
    public ILivingEntityData onInitialSpawn(IServerWorld worldIn, DifficultyInstance difficultyIn, SpawnReason reason,
            ILivingEntityData spawnDataIn, CompoundNBT dataTag) {
        this.setFez(this.getRNG().nextDouble() < 0.1);
        this.setEars(this.getRNG().nextDouble() < 0.5);
        this.setArms(this.getRNG().nextDouble() < 0.5);
        return super.onInitialSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new PanicGoal(this, 1.25));
        this.goalSelector.addGoal(0, new SwimGoal(this));
        this.goalSelector.addGoal(1, new SitGoal(this));
        this.goalSelector.addGoal(2, new LookAtGoal(this, PlayerEntity.class, 8));
        this.goalSelector.addGoal(3, new GreedyFamiliarEntity.FindItemGoal(this) {
            @Override
            public boolean shouldExecute() {
                return super.shouldExecute() && DragonFamiliarEntity.this.getPassengers().stream()
                        .anyMatch(e -> e instanceof GreedyFamiliarEntity);
            }
        });
        this.goalSelector.addGoal(4, new FetchGoal(this));
        this.goalSelector.addGoal(5, new FollowOwnerGoal(this, 1, 3, 1));
        this.goalSelector.addGoal(6, new DevilFamiliarEntity.AttackGoal(this));
        this.goalSelector.addGoal(7, new RandomWalkingGoal(this, 1));
        this.goalSelector.addGoal(8, new FollowMobGoal(this, 1, 3, 7));
    }

    @Override
    public void swing(Hand handIn, boolean updateSelf) {
        super.swing(handIn, updateSelf);
        this.swingProgressInt = -20 + 6;
    }

    @Override
    public boolean onLivingFall(float fallDistance, float damageMultiplier) {
        return false;
    }

    public float getAttackProgress(float partialTicks) {
        return MathHelper.lerp((this.swingProgressInt + (20 - 6) + partialTicks) / 20, 0, 1);
    }

    public int getPetTimer() {
        return petTimer;
    }

    @Override
    public void tick() {
        super.tick();
        if (this.greedyTimer > 0)
            this.greedyTimer--;

        if (!this.isOnGround()) {
            Vector3d motion = this.getMotion();
            if (motion.y < 0) {
                motion = motion.mul(1, 0.5, 1);
                this.setMotion(motion);
            }
        }

        if (!this.isServerWorld()) {
            this.flyingTimer0 = this.flyingTimer;
            this.wingspan0 = this.wingspan;
            if (this.isOnGround()) {
                this.wingspan -= 5;
                if (this.wingspan < 0)
                    this.wingspan = 0;

            } else {
                this.flyingTimer++;
                this.wingspan += 5;
                if (this.wingspan > 30)
                    this.wingspan = 30;
            }
            if (this.petTimer < MAX_PET_TIMER)
                this.petTimer++;
        }
    }

    public float getFlyingTimer(float partialTicks) {
        return MathHelper.lerp(partialTicks, this.flyingTimer0, this.flyingTimer);
    }

    public float getWingspan(float partialTicks) {
        return MathHelper.lerp(partialTicks, this.wingspan0, this.wingspan);
    }

    @Override
    protected ActionResultType getEntityInteractionResult(PlayerEntity playerIn, Hand hand) {
        ItemStack stack = playerIn.getHeldItem(hand);
        if (hasStick()) {
            ItemHandlerHelper.giveItemToPlayer(playerIn, new ItemStack(Items.STICK));
            setStick(false);
            return ActionResultType.func_233537_a_(!this.isServerWorld());
        } else if (stack.getItem().isIn(Tags.Items.NUGGETS_GOLD)) {
            OccultismAdvancements.FAMILIAR.trigger(this.getFamiliarOwner(), FamiliarTrigger.Type.DRAGON_NUGGET);
            this.greedyTimer += GREEDY_INCREMENT;
            if (this.isServerWorld())
                stack.shrink(1);
            else
                this.world.addParticle(ParticleTypes.HEART, this.getPosX(), this.getPosY() + 1, this.getPosZ(), 0, 0,
                        0);
            return ActionResultType.func_233537_a_(!this.isServerWorld());
        } else if (stack.isEmpty() && playerIn.isSneaking()) {
            this.petTimer = 0;
            OccultismAdvancements.FAMILIAR.trigger(playerIn, FamiliarTrigger.Type.DRAGON_PET);
            return ActionResultType.func_233537_a_(!this.isServerWorld());
        }
        return super.getEntityInteractionResult(playerIn, hand);
    }

    @Override
    protected void registerData() {
        super.registerData();
        this.dataManager.register(FEZ, false);
        this.dataManager.register(EARS, false);
        this.dataManager.register(ARMS, false);
        this.dataManager.register(STICK, false);
    }

    public boolean hasFez() {
        return this.dataManager.get(FEZ);
    }

    private void setFez(boolean b) {
        this.dataManager.set(FEZ, b);
    }

    public boolean hasEars() {
        return this.dataManager.get(EARS);
    }

    private void setEars(boolean b) {
        this.dataManager.set(EARS, b);
    }

    public boolean hasArms() {
        return this.dataManager.get(ARMS);
    }

    private void setArms(boolean b) {
        this.dataManager.set(ARMS, b);
    }

    public boolean hasStick() {
        return this.dataManager.get(STICK);
    }

    private void setStick(boolean b) {
        this.dataManager.set(STICK, b);
    }

    @Override
    public double getMountedYOffset() {
        return super.getMountedYOffset() * 0.4f;
    }

    @Override
    public void writeAdditional(CompoundNBT compound) {
        super.writeAdditional(compound);
        compound.putBoolean("hasFez", this.hasFez());
        compound.putBoolean("hasEars", this.hasEars());
        compound.putBoolean("hasArms", this.hasArms());
        compound.putBoolean("hasStick", this.hasStick());
        compound.putInt("greedyTimer", this.greedyTimer);
    }

    @Override
    public void readAdditional(CompoundNBT compound) {
        super.readAdditional(compound);
        this.setFez(compound.getBoolean("hasFez"));
        this.setEars(compound.getBoolean("hasEars"));
        this.setArms(compound.getBoolean("hasArms"));
        this.setStick(compound.getBoolean("hasStick"));
        this.greedyTimer = compound.getInt("greedyTimer");
    }

    @Override
    public Iterable<EffectInstance> getFamiliarEffects() {
        if (this.isEffectEnabled()) {
            return ImmutableList.of(new EffectInstance(OccultismEffects.DRAGON_GREED.get(), 300,
                    this.greedyTimer > 0 ? 1 : 0, false, false));
        }
        return Collections.emptyList();
    }

    public float getEyeColorR(float partialTicks) {
        return Math.abs(MathHelper.sin((this.ticksExisted + partialTicks + 5) / 20 + this.colorOffset)) * 0.8f + 0.2f;
    }

    public float getEyeColorG(float partialTicks) {
        return Math.abs(MathHelper.sin((this.ticksExisted + partialTicks + 10) / 30 + this.colorOffset)) * 0.8f + 0.2f;
    }

    public float getEyeColorB(float partialTicks) {
        return Math.abs(MathHelper.sin((this.ticksExisted + partialTicks) / 40 + this.colorOffset)) * 0.8f + 0.2f;
    }

    private static class FetchGoal extends Goal {

        private DragonFamiliarEntity dragon;
        private ItemEntity stick;

        public FetchGoal(DragonFamiliarEntity dragon) {
            this.dragon = dragon;
            this.setMutexFlags(EnumSet.of(Goal.Flag.JUMP, Goal.Flag.MOVE));
        }

        @Override
        public boolean shouldExecute() {
            stick = findStick();
            return stick != null && !dragon.hasStick();
        }

        @Override
        public boolean shouldContinueExecuting() {
            return stick != null && !dragon.hasStick();
        }

        public void startExecuting() {
            dragon.getNavigator().tryMoveToEntityLiving(stick, 1.2);
        }

        public void resetTask() {
            dragon.getNavigator().clearPath();
            stick = null;
        }

        @Override
        public void tick() {
            if (stick == null || !stick.isAlive()) {
                stick = findStick();
                if (stick == null)
                    return;
            }
            dragon.getNavigator().tryMoveToEntityLiving(stick, 1.2);

            if (stick.getDistanceSq(dragon) < 3) {
                dragon.setStick(true);
                OccultismAdvancements.FAMILIAR.trigger(dragon.getFamiliarOwner(), FamiliarTrigger.Type.DRAGON_FETCH);
                stick.getItem().shrink(1);
                stick = null;
            }
        }

        private ItemEntity findStick() {
            List<ItemEntity> sticks = dragon.world.getEntitiesWithinAABB(ItemEntity.class,
                    dragon.getBoundingBox().grow(8), e -> e.getItem().getItem() == Items.STICK && e.isAlive());
            return sticks.isEmpty() ? null : sticks.get(0);
        }

    }
}
