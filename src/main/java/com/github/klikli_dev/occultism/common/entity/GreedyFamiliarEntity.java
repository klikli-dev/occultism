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

import java.util.EnumSet;
import java.util.List;
import java.util.Optional;

import com.github.klikli_dev.occultism.common.advancement.FamiliarTrigger;
import com.github.klikli_dev.occultism.registry.OccultismAdvancements;
import com.google.common.collect.ImmutableList;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.FollowMobGoal;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.MoveToBlockGoal;
import net.minecraft.entity.ai.goal.PanicGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.WaterAvoidingRandomWalkingGoal;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.items.wrapper.PlayerMainInvWrapper;

public class GreedyFamiliarEntity extends FamiliarEntity {

    private static final DataParameter<Optional<BlockPos>> TARGET_BLOCK = EntityDataManager
            .defineId(GreedyFamiliarEntity.class, DataSerializers.OPTIONAL_BLOCK_POS);

    private float earRotZ, earRotZ0, earRotX, earRotX0;

    public GreedyFamiliarEntity(EntityType<? extends GreedyFamiliarEntity> type, World worldIn) {
        super(type, worldIn);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new PanicGoal(this, 1.25));
        this.goalSelector.addGoal(0, new SwimGoal(this));
        this.goalSelector.addGoal(1, new SitGoal(this));
        this.goalSelector.addGoal(2, new FindBlockGoal(this));
        this.goalSelector.addGoal(3, new RideDragonGoal(this));
        this.goalSelector.addGoal(4, new FindItemGoal(this));
        this.goalSelector.addGoal(4, new LookAtGoal(this, PlayerEntity.class, 8));
        this.goalSelector.addGoal(5, new FollowOwnerGoal(this, 1, 3, 1));
        this.goalSelector.addGoal(6, new WaterAvoidingRandomWalkingGoal(this, 1.0D));
        this.goalSelector.addGoal(7, new FollowMobGoal(this, 1, 3, 7));
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(TARGET_BLOCK, Optional.empty());
    }

    public Optional<BlockPos> getTargetBlock() {
        return this.entityData.get(TARGET_BLOCK);
    }

    private void setTargetBlock(Optional<BlockPos> pos) {
        this.entityData.set(TARGET_BLOCK, pos);
    }

    @Override
    public Iterable<EffectInstance> getFamiliarEffects() {
        return ImmutableList.of();
    }

    @Override
    public void curioTick(LivingEntity wearer) {
        if (!(wearer instanceof PlayerEntity))
            return;

        if (this.isEffectEnabled())
            for (ItemEntity e : wearer.level.getEntitiesOfClass(ItemEntity.class, wearer.getBoundingBox().inflate(5),
                    e -> e.isAlive())) {
                ItemStack stack = e.getItem();

                boolean isStackDemagnetized = stack.hasTag() && stack.getTag().getBoolean("PreventRemoteMovement");
                boolean isEntityDemagnetized = e.getPersistentData().getBoolean("PreventRemoteMovement");

                if (!isStackDemagnetized && !isEntityDemagnetized) {
                    e.playerTouch((PlayerEntity) wearer);
                }
            }
    }

    @Override
    public void tick() {
        super.tick();

        // Ears point to target block pos
        this.earRotX0 = this.earRotX;
        this.earRotZ0 = this.earRotZ;
        if (level.isClientSide) {
            Optional<BlockPos> targetBlock = getTargetBlock();
            if (targetBlock.isPresent()) {
                Vector3d p = Vector3d.atCenterOf(targetBlock.get());

                float endZRot = -0.5f;
                if (Math.abs(p.y - this.getEyeY()) > 1)
                    endZRot = p.y > this.getEyeY() ? 0 : -2f;
                this.earRotZ = MathHelper.lerp(0.1f, this.earRotZ, endZRot);

                Vector3d look = this.getViewVector(1.0F);
                Vector3d toTarget = p.vectorTo(this.position()).normalize();
                toTarget = new Vector3d(toTarget.x, 0.0D, toTarget.z);
                float endXRot = toTarget.dot(look) < 0 ? 1 : -1;
                this.earRotX = MathHelper.lerp(0.1f, this.earRotX, endXRot);
            } else {
                this.earRotX = MathHelper.lerp(0.1f, this.earRotX, 0);
                this.earRotZ = MathHelper.lerp(0.1f, this.earRotZ, -0.5f);
            }
        }
    }

    public float getEarRotZ(float partialTicks) {
        return MathHelper.lerp(partialTicks, this.earRotZ0, this.earRotZ);
    }

    public float getEarRotX(float partialTicks) {
        return MathHelper.lerp(partialTicks, this.earRotX0, this.earRotX);
    }

    @Override
    public boolean canBlacksmithUpgrade() {
        return !hasBlacksmithUpgrade();
    }

    @Override
    protected ActionResultType mobInteract(PlayerEntity playerIn, Hand hand) {
        ItemStack stack = playerIn.getItemInHand(hand);
        if (hasBlacksmithUpgrade() && !getOffhandItem().isEmpty()) {
            ItemHandlerHelper.giveItemToPlayer(playerIn, getOffhandItem());
            this.setItemInHand(Hand.OFF_HAND, ItemStack.EMPTY);
            return ActionResultType.sidedSuccess(this.level.isClientSide);
        } else if (hasBlacksmithUpgrade() && stack.getItem() instanceof BlockItem) {
            this.setItemInHand(Hand.OFF_HAND, new ItemStack(stack.getItem()));
            stack.shrink(1);
            return ActionResultType.sidedSuccess(this.level.isClientSide);
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
            return this.getNearbyItem() != null && this.entity.getFamiliarOwner() instanceof PlayerEntity;
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
                if (item.distanceToSqr(this.entity) < 4 && owner instanceof PlayerEntity) {
                    item.playerTouch(((PlayerEntity) owner));
                    if (this.entity instanceof GreedyFamiliarEntity)
                        OccultismAdvancements.FAMILIAR.trigger(owner, FamiliarTrigger.Type.GREEDY_ITEM);
                    else if (this.entity instanceof DragonFamiliarEntity)
                        OccultismAdvancements.FAMILIAR.trigger(owner, FamiliarTrigger.Type.DRAGON_RIDE);
                }
            }
        }

        private ItemEntity getNearbyItem() {
            LivingEntity owner = this.entity.getFamiliarOwner();
            if (!(owner instanceof PlayerEntity))
                return null;

            PlayerEntity player = (PlayerEntity) owner;
            IItemHandler inv = new PlayerMainInvWrapper(player.inventory);

            for (ItemEntity item : this.entity.level.getEntitiesOfClass(ItemEntity.class,
                    this.entity.getBoundingBox().inflate(RANGE), e -> e.isAlive())) {
                ItemStack stack = item.getItem();

                boolean isStackDemagnetized = stack.hasTag() && stack.getTag().getBoolean("PreventRemoteMovement");
                boolean isEntityDemagnetized = item.getPersistentData().getBoolean("PreventRemoteMovement");

                if ((!isStackDemagnetized && !isEntityDemagnetized)
                        && ItemHandlerHelper.insertItemStacked(inv, stack, true).getCount() != stack.getCount())
                    return item;
            }
            return null;
        }

    }

    private static class RideDragonGoal extends Goal {

        private final GreedyFamiliarEntity greedy;
        private DragonFamiliarEntity dragon;

        private RideDragonGoal(GreedyFamiliarEntity greedy) {
            this.greedy = greedy;
            this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.JUMP));
        }

        @Override
        public boolean canUse() {
            if (this.greedy.getVehicle() instanceof DragonFamiliarEntity)
                return true;

            DragonFamiliarEntity dragon = this.findDragon();
            if (dragon != null) {
                this.dragon = dragon;
                this.greedy.getNavigation().moveTo(dragon, 1);
                return true;
            }
            return false;
        }

        @Override
        public boolean canContinueToUse() {
            return this.greedy.getVehicle() instanceof DragonFamiliarEntity
                    || (this.greedy.isPathFinding() && this.dragon != null);
        }

        @Override
        public void stop() {
            this.dragon = null;
        }

        @Override
        public void tick() {
            if (this.dragon != null && this.greedy.distanceToSqr(this.dragon) < 5) {
                this.greedy.startRiding(this.dragon);
            }
        }

        private DragonFamiliarEntity findDragon() {
            LivingEntity owner = this.greedy.getOwner();
            if (owner == null)
                return null;

            List<DragonFamiliarEntity> dragons = this.greedy.level.getEntitiesOfClass(DragonFamiliarEntity.class,
                    this.greedy.getBoundingBox().inflate(5),
                    e -> e.getFamiliarOwner() == owner && !e.isVehicle() && !e.isSitting());
            if (dragons.isEmpty())
                return null;
            return dragons.get(0);
        }
    }

    private static class FindBlockGoal extends MoveToBlockGoal {

        GreedyFamiliarEntity greedy;

        public FindBlockGoal(GreedyFamiliarEntity greedy) {
            super(greedy, 1, 10, 5);
            this.greedy = greedy;
        }

        @Override
        public boolean canUse() {
            return super.canUse() && !mob.getOffhandItem().isEmpty();
        }

        @Override
        public boolean canContinueToUse() {
            return super.canContinueToUse() && !mob.getOffhandItem().isEmpty();
        }

        @Override
        protected boolean isValidTarget(IWorldReader pLevel, BlockPos pPos) {
            ItemStack offhand = mob.getOffhandItem();
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
