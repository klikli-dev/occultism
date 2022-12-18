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

package com.github.klikli_dev.occultism.common.entity.job;

import com.github.klikli_dev.occultism.api.common.container.IItemStackComparator;
import com.github.klikli_dev.occultism.common.entity.ai.behaviour.*;
import com.github.klikli_dev.occultism.common.entity.ai.sensor.NearestJobItemSensor;
import com.github.klikli_dev.occultism.common.entity.ai.sensor.NearestTreeSensor;
import com.github.klikli_dev.occultism.common.entity.ai.sensor.UnreachableWalkTargetSensor;
import com.github.klikli_dev.occultism.common.entity.spirit.SpiritEntity;
import com.github.klikli_dev.occultism.common.misc.ItemStackComparator;
import com.github.klikli_dev.occultism.common.misc.ItemTagComparator;
import com.github.klikli_dev.occultism.registry.OccultismMemoryTypes;
import com.github.klikli_dev.occultism.registry.OccultismTags;
import com.google.common.collect.ImmutableList;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.behavior.LookAtTargetSink;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.tslat.smartbrainlib.api.core.BrainActivityGroup;
import net.tslat.smartbrainlib.api.core.behaviour.FirstApplicableBehaviour;
import net.tslat.smartbrainlib.api.core.behaviour.custom.move.MoveToWalkTarget;
import net.tslat.smartbrainlib.api.core.sensor.ExtendedSensor;
import net.tslat.smartbrainlib.util.BrainUtils;

import java.util.ArrayList;
import java.util.List;

public class LumberjackJob extends SpiritJob {

    protected EntityDimensions lumberJackDimensions;
    protected List<IItemStackComparator> itemsToPickUp = new ArrayList<>();

    public LumberjackJob(SpiritEntity entity) {
        super(entity);
        this.lumberJackDimensions = EntityDimensions.scalable(0.8f, 0.8f);
    }

    @Override
    public List<ExtendedSensor<SpiritEntity>> getSensors() {
        return ImmutableList.of(
                new NearestTreeSensor<>(),
                new NearestJobItemSensor<>(),
                new UnreachableWalkTargetSensor<>()
        );
    }

    @Override
    @SuppressWarnings("unchecked")
    public BrainActivityGroup<SpiritEntity> getCoreTasks() {
        //TODO: when idle we probably should walk to center of work area

        //priority is handled by the set target behaviours mostly
        //replant, deposit, pickup and fell tree should be gated by distance to tree/item/block/lastfelledtree anyway
        //TODO: check if that works, or if we need firstApplicable or something here too -> for close quarters work

        return BrainActivityGroup.coreTasks(
                new LookAtTargetSink(8, 8),
                new FirstApplicableBehaviour<>(
                        new MoveToWalkTarget<>(),
                        new ReplantSaplingBehaviour<>(),
                        new DepositItemsBehaviour<>(),
                        new PickupItemBehaviour<>(),
                        new FellTreeBehaviour<>()
                )
        );
    }

    @Override
    @SuppressWarnings("unchecked")
    public BrainActivityGroup<SpiritEntity> getIdleTasks() {

        //prefer replanting saplings over depositing
        //prefer depositing over picking up job items
        //prefer job items to pick up, over trees
        return BrainActivityGroup.idleTasks(
                new FirstApplicableBehaviour<>(
                        new SetWalkTargetToReplantSaplingBehaviour<>(),
                        new SetWalkTargetToDepositBehaviour<>(),
                        new SetWalkTargetToItemBehaviour<>(),
                        new SetWalkTargetToTreeBehaviour<>()
                ),
                new HandleUnreachableTreeBehaviour<>()
        );
    }


    @Override
    public void handleAdditionalBrainSetup(Brain<SpiritEntity> brain) {

    }

    @Override
    public void init() {
        this.entity.refreshDimensions(); //will apply getDimensions()
        this.itemsToPickUp.add(new ItemTagComparator(ItemTags.LOGS));
        this.itemsToPickUp.add(new ItemTagComparator(ItemTags.LEAVES));
        this.itemsToPickUp.add(new ItemTagComparator(ItemTags.SAPLINGS));
        this.itemsToPickUp.add(new ItemTagComparator(OccultismTags.FRUITS));
        this.itemsToPickUp.add(new ItemStackComparator(new ItemStack(Items.STICK), false));
    }

    @Override
    public void cleanup() {
        this.entity.refreshDimensions();
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
        return this.lumberJackDimensions;
    }

    @Override
    public void onChangeWorkArea() {
        BrainUtils.clearMemory(this.entity, OccultismMemoryTypes.NO_TREE_IN_WORK_AREA.get());
        BrainUtils.clearMemory(this.entity, OccultismMemoryTypes.UNREACHABLE_WALK_TARGETS.get());
        BrainUtils.clearMemory(this.entity, OccultismMemoryTypes.UNREACHABLE_TREES.get());
        BrainUtils.clearMemory(this.entity, OccultismMemoryTypes.NON_TREE_LOGS.get());
    }
}
