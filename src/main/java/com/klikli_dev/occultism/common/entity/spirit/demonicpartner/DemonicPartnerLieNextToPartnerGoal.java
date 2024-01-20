package com.klikli_dev.occultism.common.entity.spirit.demonicpartner;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.BedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BedPart;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.AABB;

import javax.annotation.Nullable;

public class DemonicPartnerLieNextToPartnerGoal extends Goal {
    private final DemonicPartner entity;
    @Nullable
    private Player ownerPlayer;
    @Nullable
    private BlockPos goalPos;

    public DemonicPartnerLieNextToPartnerGoal(DemonicPartner pCat) {
        this.entity = pCat;
    }

    /**
     * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
     * method as well.
     */
    public boolean canUse() {
        if (!this.entity.isTame()) {
            return false;
        } else if (this.entity.isOrderedToSit()) {
            return false;
        } else {
            LivingEntity livingentity = this.entity.getOwner();
            if (livingentity instanceof Player) {
                this.ownerPlayer = (Player) livingentity;
                if (!livingentity.isSleeping()) {
                    return false;
                }

                if (this.entity.distanceToSqr(this.ownerPlayer) > 100.0D) {
                    return false;
                }


                BlockPos blockpos = this.ownerPlayer.blockPosition();
                BlockState blockstate = this.entity.level().getBlockState(blockpos);
                if (blockstate.is(BlockTags.BEDS)) {
                    Direction facing = blockstate.getValue(BedBlock.FACING);
                    BlockPos leftPos = blockpos.relative(facing.getClockWise());
                    BlockPos rightPos = blockpos.relative(facing.getCounterClockWise());
                    BlockState leftState = this.entity.level().getBlockState(leftPos);
                    BlockState rightState = this.entity.level().getBlockState(rightPos);

                    if (leftState.is(BlockTags.BEDS) && leftState.getValue(BedBlock.FACING) == facing) {
                        this.goalPos = this.getHeadPos(leftPos, leftState, facing);
                    } else if (rightState.is(BlockTags.BEDS) && rightState.getValue(BedBlock.FACING) == facing) {
                        this.goalPos = this.getHeadPos(rightPos, rightState, facing);
                    }
                    return this.goalPos != null && !this.spaceIsOccupied();
                }
            }

            return false;
        }
    }

    private BlockPos getHeadPos(BlockPos pos, BlockState state, Direction facing) {
        if (state.getValue(BedBlock.PART) == BedPart.FOOT) {
            return pos.relative(facing);
        }
        return pos;
    }

    private boolean spaceIsOccupied() {
        for (var partner : this.entity.level().getEntitiesOfClass(DemonicPartner.class, (new AABB(this.goalPos)).inflate(2.0D))) {
            if (partner != this.entity && (partner.isLying())) {
                return true;
            }
        }

        return false;
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    public boolean canContinueToUse() {
        return this.entity.isTame() && !this.entity.isOrderedToSit() && this.ownerPlayer != null && this.ownerPlayer.isSleeping() && this.goalPos != null && !this.spaceIsOccupied();
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void start() {
        if (this.goalPos != null) {
            this.entity.setInSittingPose(false);
            this.entity.getNavigation().moveTo(this.goalPos.getX(), this.goalPos.getY(), this.goalPos.getZ(), 1.1F);
        }

    }

    /**
     * Reset the task's internal state. Called when this task is interrupted by another one
     */
    public void stop() {
        this.entity.stopSleeping();
        this.entity.setLying(false);
        float f = this.entity.level().getTimeOfDay(1.0F);
        if (this.ownerPlayer.getSleepTimer() >= 100 && (double) f > 0.77D && (double) f < 0.8D && (double) this.entity.level().getRandom().nextFloat() < 0.7D) {
//            this.giveMorningGift();
        }

        this.entity.getNavigation().stop();
    }

    private void giveMorningGift() {
        RandomSource randomsource = this.entity.getRandom();
        BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();
        blockpos$mutableblockpos.set(this.entity.isLeashed() ? this.entity.getLeashHolder().blockPosition() : this.entity.blockPosition());
        this.entity.randomTeleport(blockpos$mutableblockpos.getX() + randomsource.nextInt(11) - 5, blockpos$mutableblockpos.getY() + randomsource.nextInt(5) - 2, blockpos$mutableblockpos.getZ() + randomsource.nextInt(11) - 5, false);
        blockpos$mutableblockpos.set(this.entity.blockPosition());
        LootTable loottable = this.entity.level().getServer().getLootData().getLootTable(BuiltInLootTables.CAT_MORNING_GIFT);
        LootParams lootparams = (new LootParams.Builder((ServerLevel) this.entity.level())).withParameter(LootContextParams.ORIGIN, this.entity.position()).withParameter(LootContextParams.THIS_ENTITY, this.entity).create(LootContextParamSets.GIFT);

        for (ItemStack itemstack : loottable.getRandomItems(lootparams)) {
            this.entity.level().addFreshEntity(new ItemEntity(this.entity.level(), (double) blockpos$mutableblockpos.getX() - (double) Mth.sin(this.entity.yBodyRot * ((float) Math.PI / 180F)), blockpos$mutableblockpos.getY(), (double) blockpos$mutableblockpos.getZ() + (double) Mth.cos(this.entity.yBodyRot * ((float) Math.PI / 180F)), itemstack));
        }

    }

    /**
     * Keep ticking a continuous task that has already been started
     */
    public void tick() {
        if (this.ownerPlayer != null && this.goalPos != null) {
            this.entity.setInSittingPose(false);
            this.entity.getNavigation().moveTo(this.goalPos.getX(), this.goalPos.getY(), this.goalPos.getZ(), 1.1F);
            if (this.entity.distanceToSqr(this.ownerPlayer) < 2.5D) {
                this.entity.startSleeping(this.goalPos);
                this.entity.setLying(true);
            } else {
                this.entity.setLying(false);
            }
        }

    }
}