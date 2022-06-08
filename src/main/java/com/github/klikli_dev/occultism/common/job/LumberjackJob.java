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

package com.github.klikli_dev.occultism.common.job;

import com.github.klikli_dev.occultism.api.common.container.IItemStackComparator;
import com.github.klikli_dev.occultism.common.entity.ai.DepositItemsGoal;
import com.github.klikli_dev.occultism.common.entity.ai.FellTreesGoal;
import com.github.klikli_dev.occultism.common.entity.ai.PickupItemsGoal;
import com.github.klikli_dev.occultism.common.entity.ai.ReplantSaplingGoal;
import com.github.klikli_dev.occultism.common.entity.spirit.SpiritEntity;
import com.github.klikli_dev.occultism.common.misc.ItemStackComparator;
import com.github.klikli_dev.occultism.common.misc.ItemTagComparator;
import com.github.klikli_dev.occultism.registry.OccultismTags;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.LongTag;
import net.minecraft.nbt.Tag;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class LumberjackJob extends SpiritJob {

    protected EntityDimensions lumberJackDimensions;
    protected ReplantSaplingGoal replantSaplingGoal;
    protected PickupItemsGoal pickupItemsGoal;
    protected FellTreesGoal fellTreesGoal;
    protected DepositItemsGoal depositItemsGoal;
    protected List<IItemStackComparator> itemsToPickUp = new ArrayList<>();
    private Set<BlockPos> ignoredTrees = new HashSet<>();
    private BlockPos lastFelledTree = null;


    public LumberjackJob(SpiritEntity entity) {
        super(entity);
        this.lumberJackDimensions = EntityDimensions.scalable(0.9f, 0.9f);
    }

    @Override
    public void init() {
        this.entity.goalSelector.addGoal(0, this.pickupItemsGoal = new PickupItemsGoal(this.entity, 4f, 10));
        this.entity.goalSelector.addGoal(2, this.fellTreesGoal = new FellTreesGoal(this.entity));
        this.entity.goalSelector.addGoal(3, this.replantSaplingGoal = new ReplantSaplingGoal(this.entity));
        this.entity.goalSelector.addGoal(4, this.depositItemsGoal = new DepositItemsGoal(this.entity));

        this.itemsToPickUp.add(new ItemTagComparator(ItemTags.LOGS));
        this.itemsToPickUp.add(new ItemTagComparator(ItemTags.LEAVES));
        this.itemsToPickUp.add(new ItemTagComparator(ItemTags.SAPLINGS));
        this.itemsToPickUp.add(new ItemTagComparator(OccultismTags.FRUITS));
        this.itemsToPickUp.add(new ItemStackComparator(new ItemStack(Items.STICK), false));
    }

    @Override
    public void cleanup() {
        this.entity.targetSelector.removeGoal(this.replantSaplingGoal);
        this.entity.targetSelector.removeGoal(this.pickupItemsGoal);
        this.entity.goalSelector.removeGoal(this.fellTreesGoal);
        this.entity.goalSelector.removeGoal(this.depositItemsGoal);
    }

    @Override
    public boolean canPickupItem(ItemEntity entity) {

        ItemStack stack = entity.getItem();
        for (IItemStackComparator comparator : this.itemsToPickUp) {
            if (comparator.matches(stack))
                return true;
        }
        return false;
    }

    @Override
    public EntityDimensions getDimensions(Pose pPose, EntityDimensions original) {
        return this.fellTreesGoal != null ? this.fellTreesGoal.shouldUseLumberjackDimensions() ? this.lumberJackDimensions : original : null;
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag compound = super.serializeNBT();
        ListTag list = new ListTag();
        for (BlockPos ignoredTree : this.ignoredTrees) {
            list.add(LongTag.valueOf(ignoredTree.asLong()));
        }
        compound.put("ignoredTrees", list);
        return compound;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        super.deserializeNBT(nbt);

        this.ignoredTrees = new HashSet<>();
        if (nbt.contains("ignoredTrees")) {
            ListTag list = nbt.getList("ignoredTrees", Tag.TAG_LIST);
            for (int i = 0; i < list.size(); i++) {
                this.ignoredTrees.add(BlockPos.of(((LongTag) list.get(i)).getAsLong()));
            }
        }
    }

    public Set<BlockPos> getIgnoredTrees() {
        return this.ignoredTrees;
    }

    public void setIgnoredTrees(Set<BlockPos> ignoredTrees) {
        this.ignoredTrees = ignoredTrees;
    }

    public BlockPos getLastFelledTree() {
        return this.lastFelledTree;
    }

    public void setLastFelledTree(BlockPos lastFelledTree) {
        this.lastFelledTree = lastFelledTree;
    }

}
