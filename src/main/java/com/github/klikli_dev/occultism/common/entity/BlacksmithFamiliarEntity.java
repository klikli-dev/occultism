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

import com.github.klikli_dev.occultism.Occultism;
import com.github.klikli_dev.occultism.common.advancement.FamiliarTrigger;
import com.github.klikli_dev.occultism.config.value.CachedInt;
import com.github.klikli_dev.occultism.registry.OccultismAdvancements;
import com.google.common.collect.ImmutableList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraftforge.common.Tags;

import javax.annotation.Nullable;
import java.util.EnumSet;

public class BlacksmithFamiliarEntity extends FamiliarEntity {

    private static final CachedInt UPGRADE_COST = Occultism.SERVER_CONFIG.spiritJobs.blacksmithFamiliarUpgradeCost;

    private static int getMaxIron() {
        return UPGRADE_COST.get() * 10;
    }

    private static final EntityDataAccessor<Boolean> EARRING = SynchedEntityData.defineId(BlacksmithFamiliarEntity.class,
            EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> MARIO_MOUSTACHE = SynchedEntityData
            .defineId(BlacksmithFamiliarEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> SQUARE_HAIR = SynchedEntityData
            .defineId(BlacksmithFamiliarEntity.class, EntityDataSerializers.BOOLEAN);

    private static final EntityDataAccessor<Byte> BARS = SynchedEntityData.defineId(BlacksmithFamiliarEntity.class,
            EntityDataSerializers.BYTE);

    private int ironCount;

    public BlacksmithFamiliarEntity(EntityType<? extends BlacksmithFamiliarEntity> type, Level level) {
        super(type, level);
    }

    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor pLevel, DifficultyInstance pDifficulty, MobSpawnType pReason, @Nullable SpawnGroupData pSpawnData, @Nullable CompoundTag pDataTag) {
        this.setEarring(this.getRandom().nextDouble() < 0.1);
        this.setMarioMoustache(this.getRandom().nextDouble() < 0.5);
        this.setSquareHair(this.getRandom().nextDouble() < 0.5);
        return super.finalizeSpawn(pLevel, pDifficulty, pReason, pSpawnData, pDataTag);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new PanicGoal(this, 1.25));
        this.goalSelector.addGoal(1, new SitGoal(this));
        this.goalSelector.addGoal(2, new UpgradeGoal(this));
        this.goalSelector.addGoal(3, new LookAtPlayerGoal(this, Player.class, 8));
        this.goalSelector.addGoal(4, new FollowOwnerGoal(this, 1, 3, 1));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(6, new FollowMobGoal(this, 1, 3, 7));
    }

    @Override
    protected InteractionResult mobInteract(Player playerIn, InteractionHand hand) {
        ItemStack stack = playerIn.getItemInHand(hand);
        Item item = stack.getItem();
        if (playerIn == this.getFamiliarOwner() && this.ironCount < getMaxIron()
                && (Tags.Items.INGOTS_IRON.contains(item) || Tags.Items.STORAGE_BLOCKS_IRON.contains(item))) {
            if (!this.level.isClientSide) {
                stack.shrink(1);
                this.changeIronCount(Tags.Items.INGOTS_IRON.contains(item) ? 1 : 9);
            }
            return InteractionResult.sidedSuccess(!this.level.isClientSide);
        }
        return super.mobInteract(playerIn, hand);
    }

    @Override
    public void setFamiliarOwner(LivingEntity owner) {
        if (this.hasEarring())
            OccultismAdvancements.FAMILIAR.trigger(owner, FamiliarTrigger.Type.RARE_VARIANT);
        super.setFamiliarOwner(owner);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(EARRING, false);
        this.entityData.define(MARIO_MOUSTACHE, false);
        this.entityData.define(SQUARE_HAIR, false);
        this.entityData.define(BARS, (byte) 0);
    }

    public boolean hasEarring() {
        return this.entityData.get(EARRING);
    }

    public boolean hasMarioMoustache() {
        return this.entityData.get(MARIO_MOUSTACHE);
    }

    public boolean hasSquareHair() {
        return this.entityData.get(SQUARE_HAIR);
    }

    private void setEarring(boolean b) {
        this.entityData.set(EARRING, b);
    }

    private void setMarioMoustache(boolean b) {
        this.entityData.set(MARIO_MOUSTACHE, b);
    }

    private void setSquareHair(boolean b) {
        this.entityData.set(SQUARE_HAIR, b);
    }

    private void setIronCount(int count) {
        this.ironCount = count;
        this.entityData.set(BARS, (byte) Math.min(10, (this.ironCount / UPGRADE_COST.get())));
    }

    private void changeIronCount(int delta) {
        this.setIronCount(this.ironCount + delta);
    }

    public byte getBars() {
        return this.entityData.get(BARS);
    }

    @Override
    public Iterable<MobEffectInstance> getFamiliarEffects() {
        return ImmutableList.of();
    }

    @Override
    protected void dropEquipment() {
        int blockCount = this.ironCount / 9;
        int barCount = this.ironCount % 9;
        this.spawnAtLocation(new ItemStack(Items.IRON_INGOT, barCount));
        this.spawnAtLocation(new ItemStack(Items.IRON_BLOCK, blockCount));
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putBoolean("hasEarring", this.hasEarring());
        compound.putBoolean("hasMarioMoustache", this.hasMarioMoustache());
        compound.putBoolean("hasSquareHair", this.hasSquareHair());
        compound.putInt("ironCount", this.ironCount);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.setEarring(compound.getBoolean("hasEarring"));
        this.setMarioMoustache(compound.getBoolean("hasMarioMoustache"));
        this.setSquareHair(compound.getBoolean("hasSquareHair"));
        this.setIronCount(compound.getInt("ironCount"));
    }

    private static class UpgradeGoal extends Goal {

        private static final CachedInt MAX_COOLDOWN = Occultism.SERVER_CONFIG.spiritJobs.blacksmithFamiliarUpgradeCooldown;

        private final BlacksmithFamiliarEntity blacksmith;
        private IFamiliar target;
        private int cooldown = MAX_COOLDOWN.get();

        public UpgradeGoal(BlacksmithFamiliarEntity blacksmith) {
            this.blacksmith = blacksmith;
            this.setFlags(EnumSet.of(Goal.Flag.JUMP, Goal.Flag.MOVE));
        }

        @Override
        public boolean canUse() {
            this.target = this.findTarget();
            return this.blacksmith.ironCount >= UPGRADE_COST.get() && this.target != null && this.cooldown-- < 0;
        }

        @Override
        public boolean canContinueToUse() {
            return this.target != null;
        }

        public void start() {
            this.blacksmith.getNavigation().moveTo(this.target.getFamiliarEntity(), 0.7);
        }

        public void stop() {
            this.blacksmith.getNavigation().stop();
            this.cooldown = MAX_COOLDOWN.get();
            this.target = null;
        }

        @Override
        public void tick() {
            if (this.target == null)
                return;

            if (!this.blacksmith.isPathFinding())
                this.blacksmith.getNavigation().moveTo(this.target.getFamiliarEntity(), 0.7);

            if (this.blacksmith.distanceToSqr(this.target.getFamiliarEntity()) < 3) {
                if (this.target.canBlacksmithUpgrade()) {
                    this.target.blacksmithUpgrade();
                    this.blacksmith.changeIronCount(-UPGRADE_COST.get());
                    OccultismAdvancements.FAMILIAR.trigger(this.blacksmith.getFamiliarOwner(),
                            FamiliarTrigger.Type.BLACKSMITH_UPGRADE);
                }
                this.target = null;
            }
        }

        private IFamiliar findTarget() {
            for (LivingEntity e : this.blacksmith.level.getEntitiesOfClass(LivingEntity.class,
                    this.blacksmith.getBoundingBox().inflate(4), this::familiarPred)) {
                return (IFamiliar) e;
            }
            return null;
        }

        private boolean familiarPred(Entity e) {
            if (!(e instanceof IFamiliar))
                return false;
            IFamiliar familiar = (IFamiliar) e;
            LivingEntity owner = familiar.getFamiliarOwner();
            return familiar.canBlacksmithUpgrade() && owner != null && owner == this.blacksmith.getFamiliarOwner();
        }
    }
}
