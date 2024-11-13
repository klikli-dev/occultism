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

package com.klikli_dev.occultism.common.entity.familiar;

import com.google.common.collect.ImmutableList;
import com.klikli_dev.occultism.Occultism;
import com.klikli_dev.occultism.common.advancement.FamiliarTrigger;
import com.klikli_dev.occultism.registry.OccultismAdvancements;
import com.klikli_dev.occultism.registry.OccultismEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemHandlerHelper;
import net.neoforged.neoforge.items.wrapper.PlayerMainInvWrapper;
import java.util.EnumSet;
import java.util.List;
import java.util.Optional;

public class GreedyFamiliarEntity extends FamiliarEntity {

    private static final EntityDataAccessor<Optional<BlockPos>> TARGET_BLOCK = SynchedEntityData
            .defineId(GreedyFamiliarEntity.class, EntityDataSerializers.OPTIONAL_BLOCK_POS);

    private float earRotZ, earRotZ0, earRotX, earRotX0, peekRot, peekRot0, monsterRot, monsterRot0;
    private int monsterAnimTimer;

    public GreedyFamiliarEntity(EntityType<? extends GreedyFamiliarEntity> type, Level worldIn) {
        super(type, worldIn);
        this.monsterAnimTimer = this.getRandom().nextInt(100);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FamiliarPanicGoal(this, 1.25));
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new SitGoal(this));
        this.goalSelector.addGoal(2, new FindBlockGoal(this));
        this.goalSelector.addGoal(3, new RideFamiliarGoal<>(this, OccultismEntities.DRAGON_FAMILIAR.get()));
        this.goalSelector.addGoal(4, new FindItemGoal(this));
        this.goalSelector.addGoal(4, new LookAtPlayerGoal(this, Player.class, 8));
        this.goalSelector.addGoal(5, new FollowOwnerGoal(this, 1, 3, 1));
        this.goalSelector.addGoal(6, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(7, new FollowMobGoal(this, 1, 3, 7));
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(TARGET_BLOCK, Optional.empty());
    }

    public Optional<BlockPos> getTargetBlock() {
        return this.entityData.get(TARGET_BLOCK);
    }

    private void setTargetBlock(Optional<BlockPos> pos) {
        this.entityData.set(TARGET_BLOCK, pos);
    }

    @Override
    public Iterable<MobEffectInstance> getFamiliarEffects() {
        return ImmutableList.of();
    }

    @Override
    public void curioTick(LivingEntity wearer) {
        if (!(wearer instanceof Player))
            return;

        if (this.isEffectEnabled(wearer))
            for (ItemEntity e : wearer.level().getEntitiesOfClass(ItemEntity.class, wearer.getBoundingBox().inflate(5), Entity::isAlive)) {
                ItemStack stack = e.getItem();

                boolean isStackDemagnetized = false;//TODO: Find what the updated convention is for stack.hasTag() && stack.getTag().getBoolean("PreventRemoteMovement");
                boolean isEntityDemagnetized = e.getPersistentData().getBoolean("PreventRemoteMovement");

                if (!isStackDemagnetized && !isEntityDemagnetized) {
                    e.playerTouch((Player) wearer);
                }
            }
    }

    @Override
    public void tick() {
        super.tick();

        // Ears point to target block pos
        this.earRotX0 = this.earRotX;
        this.earRotZ0 = this.earRotZ;
        if (this.level().isClientSide) {
            Optional<BlockPos> targetBlock = this.getTargetBlock();
            if (targetBlock.isPresent()) {
                Vec3 p = Vec3.atCenterOf(targetBlock.get());

                float endZRot = -0.5f;
                if (Math.abs(p.y - this.getEyeY()) > 1)
                    endZRot = p.y > this.getEyeY() ? 0 : -2f;
                this.earRotZ = Mth.lerp(0.1f, this.earRotZ, endZRot);

                Vec3 look = this.getViewVector(1.0F);
                Vec3 toTarget = p.vectorTo(this.position()).normalize();
                toTarget = new Vec3(toTarget.x, 0.0D, toTarget.z);
                float endXRot = toTarget.dot(look) < 0 ? 1 : -1;
                this.earRotX = Mth.lerp(0.1f, this.earRotX, endXRot);
            } else {
                this.earRotX = Mth.lerp(0.1f, this.earRotX, 0);
                this.earRotZ = Mth.lerp(0.1f, this.earRotZ, -0.5f);
            }

            this.monsterAnimTimer++;
            this.peekRot0 = this.peekRot;
            this.monsterRot0 = this.monsterRot;
            if (this.monsterAnimTimer % 300 < 200) {
                float peekTimer = this.monsterAnimTimer % 300 % 200;
                if (peekTimer > 30 && peekTimer < 50)
                    this.monsterRot = Mth.lerp(0.3f, this.monsterRot, this.toRad(37));
                else if (peekTimer > 50 && peekTimer < 70)
                    this.monsterRot = Mth.lerp(0.3f, this.monsterRot, this.toRad(-37));
                else if (peekTimer > 70)
                    this.monsterRot = Mth.lerp(0.3f, this.monsterRot, 0);

                if (peekTimer < 100)
                    this.peekRot = Mth.lerp(0.1f, this.peekRot, this.toRad(46));
                else
                    this.peekRot = Mth.lerp(0.1f, this.peekRot, 0);
            } else {
                this.peekRot = 0;
                this.monsterRot = 0;
            }
        }
    }

    private float toRad(float deg) {
        return (float) Math.toRadians(deg);
    }

    public float getLidRot(float partialTicks) {
        return Mth.lerp(partialTicks, this.peekRot0, this.peekRot);
    }

    public float getMonsterRot(float partialTicks) {
        return Mth.lerp(partialTicks, this.monsterRot0, this.monsterRot);
    }

    public float getEarRotZ(float partialTicks) {
        return Mth.lerp(partialTicks, this.earRotZ0, this.earRotZ);
    }

    public float getEarRotX(float partialTicks) {
        return Mth.lerp(partialTicks, this.earRotX0, this.earRotX);
    }

    @Override
    public boolean canBlacksmithUpgrade() {
        return !this.hasBlacksmithUpgrade();
    }

    @Override
    protected InteractionResult mobInteract(Player playerIn, InteractionHand hand) {
        ItemStack stack = playerIn.getItemInHand(hand);
        if (this.hasBlacksmithUpgrade() && !this.getOffhandItem().isEmpty()) {
            ItemHandlerHelper.giveItemToPlayer(playerIn, this.getOffhandItem());
            this.setItemInHand(InteractionHand.OFF_HAND, ItemStack.EMPTY);
            return InteractionResult.sidedSuccess(this.level().isClientSide);
        } else if (this.hasBlacksmithUpgrade() && stack.getItem() instanceof BlockItem) {
            this.setItemInHand(InteractionHand.OFF_HAND, new ItemStack(stack.getItem()));
            stack.shrink(1);
            return InteractionResult.sidedSuccess(this.level().isClientSide);
        }

        return super.mobInteract(playerIn, hand);
    }


    public static class FindItemGoal extends Goal {

        private static final double RANGE = 12;

        private final FamiliarEntity entity;

        public FindItemGoal(FamiliarEntity entity) {
            this.entity = entity;
            this.setFlags(EnumSet.of(Goal.Flag.MOVE));
        }

        @Override
        public boolean canUse() {
            return this.getNearbyItem() != null && this.entity.getFamiliarOwner() instanceof Player;
        }

        @Override
        public void start() {
            ItemEntity item = this.getNearbyItem();
            if (item != null)
                this.entity.getNavigation().moveTo(item, 1.2);
        }

        @Override
        public void tick() {
            ItemEntity item = this.getNearbyItem();
            if (item != null) {
                this.entity.getNavigation().moveTo(item, 1.2);
                LivingEntity owner = this.entity.getFamiliarOwner();
                if (item.distanceToSqr(this.entity) < 4 && owner instanceof Player player) {
                    item.playerTouch(player);
                    OccultismAdvancements.FAMILIAR.get().trigger(owner, FamiliarTrigger.Type.GREEDY_ITEM);
                }
            }
        }

        private ItemEntity getNearbyItem() {
            LivingEntity owner = this.entity.getFamiliarOwner();
            if (!(owner instanceof Player player))
                return null;

            IItemHandler inv = new PlayerMainInvWrapper(player.getInventory());

            for (ItemEntity item : this.entity.level().getEntitiesOfClass(ItemEntity.class,
                    this.entity.getBoundingBox().inflate(RANGE), e -> e.isAlive())) {
                ItemStack stack = item.getItem();

                boolean isStackDemagnetized = false;//TODO: Find what the updated convention is for stack.hasTag() && stack.getTag().getBoolean("PreventRemoteMovement");
                boolean isEntityDemagnetized = item.getPersistentData().getBoolean("PreventRemoteMovement");

                if ((!isStackDemagnetized && !isEntityDemagnetized)
                        && ItemHandlerHelper.insertItemStacked(inv, stack, true).getCount() != stack.getCount())
                    return item;
            }
            return null;
        }

    }

    public static class RideFamiliarGoal<T extends FamiliarEntity> extends Goal {

        private final FamiliarEntity rider;
        private final EntityType<T> type;
        private FamiliarEntity mount;

        public RideFamiliarGoal(FamiliarEntity rider, EntityType<T> type) {
            this.rider = rider;
            this.type = type;
            this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.JUMP));
        }

        @Override
        public boolean canUse() {
            if (this.rider.getVehicle() != null && this.rider.getVehicle().getType() == this.type)
                return true;

            FamiliarEntity mount = this.findMount();
            if (mount != null) {
                this.mount = mount;
                this.rider.getNavigation().moveTo(mount, 1);
                return true;
            }
            return false;
        }

        @Override
        public boolean canContinueToUse() {
            return (this.rider.getVehicle() != null && this.rider.getVehicle().getType() == this.type)
                    || (this.rider.isPathFinding() && this.mount != null);
        }

        @Override
        public void stop() {
            this.rider.stopRiding();
            this.mount = null;
        }

        @Override
        public void tick() {
            if (this.mount != null && this.rider.distanceToSqr(this.mount) < 5 && !this.mount.hasPassenger(this.rider)) {
                this.rider.startRiding(this.mount);
                this.mount.getNavigation().stop();

                if (this.rider.getType() == OccultismEntities.SHUB_NIGGURATH_FAMILIAR.get() && this.type == OccultismEntities.CTHULHU_FAMILIAR.get())
                    OccultismAdvancements.FAMILIAR.get().trigger(this.rider.getFamiliarOwner(), FamiliarTrigger.Type.SHUB_CTHULHU_FRIENDS);
            }
        }

        private FamiliarEntity findMount() {
            LivingEntity owner = this.rider.getOwner();
            if (owner == null)
                return null;

            List<T> mounts = this.rider.level().getEntities(this.type, this.rider.getBoundingBox().inflate(5),
                    e -> e.getFamiliarOwner() == owner && !e.isVehicle() && !e.isSitting());
            if (mounts.isEmpty())
                return null;
            return mounts.get(0);
        }
    }

    private static class FindBlockGoal extends MoveToBlockGoal {

        GreedyFamiliarEntity greedy;

        public FindBlockGoal(GreedyFamiliarEntity greedy) {
            super(greedy, 1, Occultism.SERVER_CONFIG.spiritJobs.greedySearchRange.getAsInt(), Occultism.SERVER_CONFIG.spiritJobs.greedyVerticalSearchRange.getAsInt());
            this.greedy = greedy;
        }

        @Override
        public boolean canUse() {
            return super.canUse() && !this.mob.getOffhandItem().isEmpty();
        }

        @Override
        public boolean canContinueToUse() {
            return super.canContinueToUse() && !this.mob.getOffhandItem().isEmpty();
        }

        @Override
        protected boolean isValidTarget(LevelReader pLevel, BlockPos pPos) {
            ItemStack offhand = this.mob.getOffhandItem();
            return pLevel.getBlockState(pPos).getBlock() == Block.byItem(offhand.getItem());
        }

        @Override
        public void start() {
            super.start();
            this.greedy.setTargetBlock(Optional.of(this.blockPos));
        }

        @Override
        public void stop() {
            super.stop();
            this.greedy.setTargetBlock(Optional.empty());
        }

        @Override
        protected BlockPos getMoveToTarget() {
            return this.blockPos;
        }

    }

}
