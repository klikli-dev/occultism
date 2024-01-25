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

package com.klikli_dev.occultism.common.entity.job;

import com.google.common.collect.ImmutableList;
import com.klikli_dev.occultism.api.common.container.IItemStackComparator;
import com.klikli_dev.occultism.common.entity.ai.behaviour.*;
import com.klikli_dev.occultism.common.entity.ai.sensor.NearestJobItemSensor;
import com.klikli_dev.occultism.common.entity.ai.sensor.NearestTreeSensor;
import com.klikli_dev.occultism.common.entity.ai.sensor.UnreachableTreeWalkTargetSensor;
import com.klikli_dev.occultism.common.entity.spirit.SpiritEntity;
import com.klikli_dev.occultism.common.misc.ItemStackComparator;
import com.klikli_dev.occultism.common.misc.ItemTagComparator;
import com.klikli_dev.occultism.registry.OccultismMemoryTypes;
import com.klikli_dev.occultism.registry.OccultismTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.behavior.LookAtTargetSink;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.tslat.smartbrainlib.api.core.BrainActivityGroup;
import net.tslat.smartbrainlib.api.core.behaviour.FirstApplicableBehaviour;
import net.tslat.smartbrainlib.api.core.behaviour.custom.move.MoveToWalkTarget;
import net.tslat.smartbrainlib.api.core.sensor.ExtendedSensor;
import net.tslat.smartbrainlib.util.BrainUtils;

import java.util.ArrayList;
import java.util.List;

public class LumberjackJob extends SpiritJob {

    protected List<Ingredient> itemsToPickUp = new ArrayList<>();

    public LumberjackJob(SpiritEntity entity) {
        super(entity);
    }

    @Override
    public List<ExtendedSensor<SpiritEntity>> getSensors() {
        return ImmutableList.of(
                new NearestTreeSensor<>(),
                new NearestJobItemSensor<>(),
                new UnreachableTreeWalkTargetSensor<>()
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
    public void handleAdditionalBrainSetup(Brain<? extends SpiritEntity> brain) {

    }

    @Override
    public void onInit() {
        this.itemsToPickUp.add(Ingredient.of(ItemTags.LOGS));
        this.itemsToPickUp.add(Ingredient.of(ItemTags.LEAVES));
        this.itemsToPickUp.add(Ingredient.of(ItemTags.SAPLINGS));
        this.itemsToPickUp.add(Ingredient.of(OccultismTags.FRUITS));
        this.itemsToPickUp.add(Ingredient.of(Items.STICK));
    }

    @Override
    public void cleanup() {
    }

    @Override
    public boolean canPickupItem(ItemEntity entity) {
        ItemStack stack = entity.getItem();
        return !stack.isEmpty() && this.itemsToPickUp.stream().anyMatch(i -> i.test(stack));
    }

    @Override
    public List<Ingredient> getItemsToPickUp() {
        return this.itemsToPickUp;
    }

    @Override
    public void onChangeWorkArea() {
        BrainUtils.clearMemory(this.entity, OccultismMemoryTypes.NO_TREE_IN_WORK_AREA.get());
        BrainUtils.clearMemory(this.entity, OccultismMemoryTypes.UNREACHABLE_WALK_TARGETS.get());
        BrainUtils.clearMemory(this.entity, OccultismMemoryTypes.UNREACHABLE_TREES.get());
        BrainUtils.clearMemory(this.entity, OccultismMemoryTypes.NON_TREE_LOGS.get());
    }
}
