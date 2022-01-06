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

package com.github.klikli_dev.occultism.common.entity;

import java.util.EnumSet;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

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
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.potion.EffectInstance;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;

public class BeaverFamiliarEntity extends FamiliarEntity {

    private BlockPos treeTarget;

    public BeaverFamiliarEntity(EntityType<? extends BeaverFamiliarEntity> type, World worldIn) {
        super(type, worldIn);
    }

    @Override
    public ILivingEntityData finalizeSpawn(IServerWorld worldIn, DifficultyInstance difficultyIn, SpawnReason reason,
            ILivingEntityData spawnDataIn, CompoundNBT dataTag) {
        this.setBigTail(this.getRandom().nextDouble() < 0.1);
        this.setEars(this.getRandom().nextBoolean());
        this.setWhiskers(this.getRandom().nextBoolean());
        return super.finalizeSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
    }

    @Override
    public void setFamiliarOwner(LivingEntity owner) {
        if (this.hasBigTail())
            OccultismAdvancements.FAMILIAR.trigger(owner, FamiliarTrigger.Type.RARE_VARIANT);
        super.setFamiliarOwner(owner);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new PanicGoal(this, 1.25));
        this.goalSelector.addGoal(0, new SwimGoal(this));
        this.goalSelector.addGoal(1, new SitGoal(this));
        this.goalSelector.addGoal(2, new LookAtGoal(this, PlayerEntity.class, 8));
        this.goalSelector.addGoal(4, new ChopTreeGoal(this));
        this.goalSelector.addGoal(5, new FollowOwnerGoal(this, 1, 3, 1));
        this.goalSelector.addGoal(7, new RandomWalkingGoal(this, 1));
        this.goalSelector.addGoal(8, new FollowMobGoal(this, 1, 3, 7));
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
    public Iterable<EffectInstance> getFamiliarEffects() {
        return ImmutableList.of(new EffectInstance(OccultismEffects.BEAVER_HARVEST.get(), 300));
    }

    public void setTreeTarget(BlockPos pos) {
        this.treeTarget = pos;
    }

    private static class ChopTreeGoal extends Goal {

        private BeaverFamiliarEntity beaver;

        private ChopTreeGoal(BeaverFamiliarEntity beaver) {
            this.beaver = beaver;
            this.setFlags(EnumSet.of(Goal.Flag.JUMP, Goal.Flag.MOVE));
        }

        @Override
        public boolean canUse() {
            return !beaver.isSitting() && beaver.isEffectEnabled(beaver.getFamiliarOwner()) && beaver.treeTarget != null
                    && beaver.level.getBlockState(beaver.treeTarget).is(BlockTags.LOGS);
        }

        @Override
        public void start() {
            if (beaver.treeTarget == null)
                return;

            beaver.getNavigation().moveTo(beaver.treeTarget.getX(), beaver.treeTarget.getY(), beaver.treeTarget.getZ(),
                    1);
        }

        @Override
        public void tick() {
            if (beaver.treeTarget == null)
                return;

            if (beaver.distanceToSqr(beaver.treeTarget.getX(), beaver.treeTarget.getY(), beaver.treeTarget.getZ()) > 4)
                beaver.getNavigation().moveTo(beaver.treeTarget.getX(), beaver.treeTarget.getY(),
                        beaver.treeTarget.getZ(), 1);
            else {
                LinkedList<BlockPos> positions = new LinkedList<>();
                Set<BlockPos> harvesting = new HashSet<>();

                positions.add(beaver.treeTarget);

                while (!positions.isEmpty() && harvesting.size() < 15) {
                    BlockPos pos = positions.pop();

                    harvesting.add(pos);
                    for (BlockPos p : BlockPos.withinManhattan(pos, 1, 1, 1)) {
                        if (!harvesting.contains(p) && beaver.level.getBlockState(p).is(BlockTags.LOGS)) {
                            positions.add(p.immutable());
                            harvesting.add(pos);
                        }

                    }
                }

                if (!positions.isEmpty()) {
                    beaver.treeTarget = null;
                    return;
                }

                for (BlockPos p : harvesting)
                    beaver.level.destroyBlock(p, true);
                beaver.treeTarget = null;
            }
        }
    }
}
