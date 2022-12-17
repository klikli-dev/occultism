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
import com.github.klikli_dev.occultism.common.entity.ai.behaviour.FellTreeBehaviour;
import com.github.klikli_dev.occultism.common.entity.ai.behaviour.SetWalkToTreeTargetBehaviour;
import com.github.klikli_dev.occultism.common.entity.ai.sensor.NearestTreeSensor;
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
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.tslat.smartbrainlib.api.core.BrainActivityGroup;
import net.tslat.smartbrainlib.api.core.behaviour.custom.move.MoveToWalkTarget;
import net.tslat.smartbrainlib.api.core.sensor.ExtendedSensor;
import net.tslat.smartbrainlib.util.BrainUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class LumberjackJob extends SpiritJob {

    protected EntityDimensions lumberJackDimensions;
    protected List<IItemStackComparator> itemsToPickUp = new ArrayList<>();

    public LumberjackJob(SpiritEntity entity) {
        super(entity);
        this.lumberJackDimensions = EntityDimensions.scalable(0.9f, 0.9f);
    }

    @Override
    public List<ExtendedSensor<SpiritEntity>> getSensors() {
        //TODO: use and handle UnreachableTargetSensor?
        return ImmutableList.of(new NearestTreeSensor<>());
    }

    @Override
    @SuppressWarnings("unchecked")
    public BrainActivityGroup<SpiritEntity> getCoreTasks() {
        //TODO: when idle we probably should walk to center of work area

        //TODO: pickup behaviour
        //TODO: replant sapling behaviour
        //TODO: deposit behaviour
        return BrainActivityGroup.coreTasks(
                new MoveToWalkTarget<>(),
                new FellTreeBehaviour<>()
        );
    }

    @Override
    @SuppressWarnings("unchecked")
    public BrainActivityGroup<SpiritEntity> getIdleTasks() {
        return BrainActivityGroup.idleTasks(
                new SetWalkToTreeTargetBehaviour<>()
        );
    }


    @Override
    public void handleAdditionalBrainSetup(Brain<SpiritEntity> brain) {

    }

    @Override
    public void init() {
        this.itemsToPickUp.add(new ItemTagComparator(ItemTags.LOGS));
        this.itemsToPickUp.add(new ItemTagComparator(ItemTags.LEAVES));
        this.itemsToPickUp.add(new ItemTagComparator(ItemTags.SAPLINGS));
        this.itemsToPickUp.add(new ItemTagComparator(OccultismTags.FRUITS));
        this.itemsToPickUp.add(new ItemStackComparator(new ItemStack(Items.STICK), false));
    }

    @Override
    public void cleanup() {

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
}
