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

import com.github.klikli_dev.occultism.registry.OccultismEffects;
import com.google.common.collect.ImmutableList;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;
import net.minecraftforge.common.Tags;

public class DragonFamiliarEntity extends FamiliarEntity {

    private static final int GREEDY_INCREMENT = 20 * 60 * 5;

    private static final DataParameter<Boolean> FEZ = EntityDataManager.createKey(DragonFamiliarEntity.class,
            DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> EARS = EntityDataManager.createKey(DragonFamiliarEntity.class,
            DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> ARMS = EntityDataManager.createKey(DragonFamiliarEntity.class,
            DataSerializers.BOOLEAN);

    private float colorOffset;
    private int greedyTimer;

    public DragonFamiliarEntity(EntityType<? extends DragonFamiliarEntity> type, World worldIn) {
        super(type, worldIn);
        colorOffset = this.getRNG().nextFloat() * 2;
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
        this.goalSelector.addGoal(1, new SitGoal(this));
        this.goalSelector.addGoal(2, new DevilFamiliarEntity.AttackGoal(this));
        this.goalSelector.addGoal(3, new GreedyFamiliarEntity.FindItemGoal(this) {
            @Override
            public boolean shouldExecute() {
                return super.shouldExecute()
                        && getPassengers().stream().anyMatch(e -> e instanceof GreedyFamiliarEntity);
            }
        });
    }
    
    @Override
    public void swing(Hand handIn, boolean updateSelf) {
        super.swing(handIn, updateSelf);
        this.swingProgressInt = -20 + 6;
    }
    
    public float getAttackProgress(float partialTicks) {
        return MathHelper.lerp((this.swingProgressInt + (20 - 6) + partialTicks) / 20, 0, 1);
    }

    @Override
    public void tick() {
        super.tick();
        if (greedyTimer > 0)
            greedyTimer--;
    }

    @Override
    protected ActionResultType getEntityInteractionResult(PlayerEntity playerIn, Hand hand) {
        ItemStack stack = playerIn.getHeldItem(hand);
        if (stack.getItem().isIn(Tags.Items.NUGGETS_GOLD)) {
            greedyTimer += GREEDY_INCREMENT;
            if (isServerWorld())
                stack.shrink(1);
            else
                world.addParticle(ParticleTypes.HEART, this.getPosX(), this.getPosY() + 1, this.getPosZ(), 0, 0, 0);
            return ActionResultType.func_233537_a_(!isServerWorld());
        }
        return super.getEntityInteractionResult(playerIn, hand);
    }

    @Override
    protected void registerData() {
        super.registerData();
        this.dataManager.register(FEZ, false);
        this.dataManager.register(EARS, false);
        this.dataManager.register(ARMS, false);
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
        compound.putInt("greedyTimer", this.greedyTimer);
    }

    @Override
    public void readAdditional(CompoundNBT compound) {
        super.readAdditional(compound);
        this.setFez(compound.getBoolean("hasFez"));
        this.setEars(compound.getBoolean("hasEars"));
        this.setArms(compound.getBoolean("hasArms"));
        this.greedyTimer = compound.getInt("greedyTimer");
    }

    @Override
    public Iterable<EffectInstance> getFamiliarEffects() {
        return ImmutableList.of(new EffectInstance(OccultismEffects.DRAGON_GREED.get(), 300,
                this.greedyTimer > 0 ? 1 : 0, false, false));
    }

    public float getEyeColorR(float partialTicks) {
        return Math.abs(MathHelper.sin((ticksExisted + partialTicks + 5) / 20 + colorOffset)) * 0.8f + 0.2f;
    }

    public float getEyeColorG(float partialTicks) {
        return Math.abs(MathHelper.sin((ticksExisted + partialTicks + 10) / 30 + colorOffset)) * 0.8f + 0.2f;
    }

    public float getEyeColorB(float partialTicks) {
        return Math.abs(MathHelper.sin((ticksExisted + partialTicks) / 40 + colorOffset)) * 0.8f + 0.2f;
    }
}
