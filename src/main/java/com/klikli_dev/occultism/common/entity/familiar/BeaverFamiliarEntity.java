/*
 * MIT License
 *
 * Copyright 2022 vemerion
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
import com.klikli_dev.occultism.common.advancement.FamiliarTrigger;
import com.klikli_dev.occultism.registry.OccultismAdvancements;
import com.klikli_dev.occultism.registry.OccultismEffects;
import com.klikli_dev.occultism.registry.OccultismItems;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.ai.navigation.WaterBoundPathNavigation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.pathfinder.PathType;
import net.neoforged.neoforge.common.NeoForgeMod;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.items.ItemHandlerHelper;

import javax.annotation.Nullable;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

public class BeaverFamiliarEntity extends FamiliarEntity {

    protected static final int SNACK_INTERVAL = 20;
    private final WaterBoundPathNavigation waterNavigator;
    private final GroundPathNavigation groundNavigator;
    protected long lastSnackTime;
    private BlockPos treeTarget;

    public BeaverFamiliarEntity(EntityType<? extends BeaverFamiliarEntity> type, Level level) {
        super(type, level);
        this.setPathfindingMalus(PathType.WATER, 0);
        this.waterNavigator = new WaterBoundPathNavigation(this, level);
        this.groundNavigator = new GroundPathNavigation(this, level);
        this.moveControl = new CthulhuFamiliarEntity.MoveController(this);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return FamiliarEntity.createMobAttributes().add(NeoForgeMod.SWIM_SPEED, 1f);
    }

    @Override
    public void updateSwimming() {
        if (!this.level().isClientSide) {
            if (this.isInWater()) {
                this.navigation = this.waterNavigator;
                this.setSwimming(true);
            } else {
                this.navigation = this.groundNavigator;
                this.setSwimming(false);
            }
        }
    }

    @Override
    public void setFamiliarOwner(LivingEntity owner) {
        if (this.hasBigTail())
            OccultismAdvancements.FAMILIAR.get().trigger(owner, FamiliarTrigger.Type.RARE_VARIANT);
        super.setFamiliarOwner(owner);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FamiliarPanicGoal(this, 1.25));
        this.goalSelector.addGoal(1, new SitGoal(this));
        this.goalSelector.addGoal(2, new LookAtPlayerGoal(this, Player.class, 8));
        this.goalSelector.addGoal(4, new ChopTreeGoal(this));
        this.goalSelector.addGoal(5, new CthulhuFamiliarEntity.FollowOwnerWaterGoal(this, 1, 3, 1));
        this.goalSelector.addGoal(7, new RandomStrollGoal(this, 1));
        this.goalSelector.addGoal(8, new FollowMobGoal(this, 1, 3, 7));
    }

    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor pLevel, DifficultyInstance pDifficulty, MobSpawnType pReason, @Nullable SpawnGroupData pSpawnData) {
        this.setBigTail(this.getRandom().nextDouble() < 0.1);
        this.setEars(this.getRandom().nextBoolean());
        this.setWhiskers(this.getRandom().nextBoolean());
        return super.finalizeSpawn(pLevel, pDifficulty, pReason, pSpawnData);
    }

    @Override
    public boolean canDrownInFluidType(FluidType type) {
        return false;
    }

    @Override
    public InteractionResult mobInteract(Player pPlayer, InteractionHand pHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pHand);
        if (this.getOwner() == pPlayer) {

            if (!pPlayer.isShiftKeyDown() && itemstack.isEmpty()) {
                if (!this.hasBlacksmithUpgrade()) {
                    pPlayer.displayClientMessage(Component.translatable("dialog.occultism.beaver.no_upgrade"), true);
                } else if (this.level().getGameTime() > this.lastSnackTime + SNACK_INTERVAL) {
                    this.lastSnackTime = this.level().getGameTime();
                    ItemHandlerHelper.giveItemToPlayer(pPlayer, new ItemStack(OccultismItems.BEAVER_NUGGET.get()));
                } else {
                    pPlayer.displayClientMessage(Component.translatable("dialog.occultism.beaver.snack_on_cooldown"), true);
                }
                //even if we don't give a snack we return success, otherwise we make the familiar change sitting position
                return InteractionResult.sidedSuccess(this.level().isClientSide);
            }

        }
        return super.mobInteract(pPlayer, pHand);
    }

    public boolean hasWhiskers() {
        return this.hasVariant(0);
    }

    private void setWhiskers(boolean b) {
        this.setVariant(0, b);
    }

    public boolean hasEars() {
        return this.hasVariant(1);
    }

    private void setEars(boolean b) {
        this.setVariant(1, b);
    }

    public boolean hasBigTail() {
        return this.hasVariant(2);
    }

    private void setBigTail(boolean b) {
        this.setVariant(2, b);
    }

    @Override
    public Iterable<MobEffectInstance> getFamiliarEffects() {
        return ImmutableList.of(new MobEffectInstance(OccultismEffects.BEAVER_HARVEST, 300, 0, false,false));
    }

    public void setTreeTarget(BlockPos pos) {
        this.treeTarget = pos;
    }

    @Override
    public boolean canBlacksmithUpgrade() {
        return true;
    }

    private static class ChopTreeGoal extends Goal {

        private final BeaverFamiliarEntity beaver;

        private ChopTreeGoal(BeaverFamiliarEntity beaver) {
            this.beaver = beaver;
            this.setFlags(EnumSet.of(Goal.Flag.JUMP, Goal.Flag.MOVE));
        }

        @Override
        public boolean canUse() {
            return !this.beaver.isSitting() && this.beaver.isEffectEnabled(this.beaver.getFamiliarOwner()) && this.beaver.treeTarget != null
                    && this.beaver.level().getBlockState(this.beaver.treeTarget).is(BlockTags.LOGS);
        }

        @Override
        public void start() {
            if (this.beaver.treeTarget == null)
                return;

            this.beaver.getNavigation().moveTo(this.beaver.treeTarget.getX(), this.beaver.treeTarget.getY(), this.beaver.treeTarget.getZ(),
                    1);
        }

        @Override
        public void tick() {
            if (this.beaver.treeTarget == null)
                return;

            if (this.beaver.distanceToSqr(this.beaver.treeTarget.getX(), this.beaver.treeTarget.getY(), this.beaver.treeTarget.getZ()) > 4)
                this.beaver.getNavigation().moveTo(this.beaver.treeTarget.getX(), this.beaver.treeTarget.getY(),
                        this.beaver.treeTarget.getZ(), 1);
            else {
                LinkedList<BlockPos> positions = new LinkedList<>();
                Set<BlockPos> harvesting = new HashSet<>();

                positions.add(this.beaver.treeTarget);

                while (!positions.isEmpty() && harvesting.size() < 15) {
                    BlockPos pos = positions.pop();

                    harvesting.add(pos);
                    for (BlockPos p : BlockPos.withinManhattan(pos, 1, 1, 1)) {
                        if (!harvesting.contains(p) && this.beaver.level().getBlockState(p).is(BlockTags.LOGS)) {
                            positions.add(p.immutable());
                            harvesting.add(pos);
                        }

                    }
                }

                if (!positions.isEmpty()) {
                    this.beaver.treeTarget = null;
                    return;
                }

                for (BlockPos p : harvesting)
                    this.beaver.level().destroyBlock(p, true);
                this.beaver.treeTarget = null;
                OccultismAdvancements.FAMILIAR.get().trigger(this.beaver.getFamiliarOwner(), FamiliarTrigger.Type.BEAVER_WOODCHOP);
            }
        }
    }
}
