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
import com.klikli_dev.occultism.common.entity.ai.behaviour.*;
import com.klikli_dev.occultism.common.entity.ai.BrainUtil;
import com.klikli_dev.occultism.common.entity.ai.sensor.NearestJobItemSensor;
import com.klikli_dev.occultism.common.entity.ai.sensor.NearestTreeSensor;
import com.klikli_dev.occultism.common.entity.ai.sensor.UnreachableTreeWalkTargetSensor;
import com.klikli_dev.occultism.common.entity.spirit.SpiritEntity;
import com.klikli_dev.occultism.registry.OccultismMemoryTypes;
import com.klikli_dev.occultism.registry.OccultismSensors;
import com.klikli_dev.occultism.registry.OccultismTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.ai.ActivityData;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.behavior.LookAtTargetSink;
import net.minecraft.world.entity.ai.behavior.MoveToTargetSink;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.world.entity.schedule.Activity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.ArrayList;
import java.util.List;

public class LumberjackJob extends SpiritJob {

    protected List<Ingredient> itemsToPickUp = new ArrayList<>();

    public LumberjackJob(SpiritEntity entity) {
        super(entity);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<SensorType<? extends Sensor<SpiritEntity>>> getSensorTypes() {
        return ImmutableList.of(
                (SensorType<? extends Sensor<SpiritEntity>>) (SensorType<?>) OccultismSensors.NEAREST_TREE.get(),
                (SensorType<? extends Sensor<SpiritEntity>>) (SensorType<?>) OccultismSensors.NEAREST_JOB_ITEM.get(),
                (SensorType<? extends Sensor<SpiritEntity>>) (SensorType<?>) OccultismSensors.UNREACHABLE_TREE_WALK_TARGET.get()
        );
    }

    @Override
    public List<ActivityData<SpiritEntity>> getActivityData() {
        return List.of(
                ActivityData.create(
                        Activity.CORE,
                        0,
                        ImmutableList.of(
                                new LookAtTargetSink(8, 8),
                                new FirstApplicableBehaviour<>(
                                        new MoveToTargetSink(),
                                        new ReplantSaplingBehaviour<>(),
                                        new DepositItemsBehaviour<>(),
                                        new PickupItemBehaviour<>(),
                                        new FellTreeBehaviour<>()
                                )
                        )
                ),
                ActivityData.create(
                        Activity.IDLE,
                        0,
                        ImmutableList.of(
                                new FirstApplicableBehaviour<>(
                                        new SetWalkTargetToReplantSaplingBehaviour<>(),
                                        new SetWalkTargetToDepositBehaviour<>(),
                                        new SetWalkTargetToItemBehaviour<>(),
                                        new SetWalkTargetToTreeBehaviour<>()
                                ),
                                new HandleUnreachableTreeBehaviour<>()
                        )
                )
        );
    }


    @Override
    public void handleAdditionalBrainSetup(Brain<? extends SpiritEntity> brain) {

    }

    @Override
    public void onInit() {
        this.itemsToPickUp.add(Ingredient.of(net.minecraft.core.registries.BuiltInRegistries.ITEM.getOrThrow(ItemTags.LOGS)));
        this.itemsToPickUp.add(Ingredient.of(net.minecraft.core.registries.BuiltInRegistries.ITEM.getOrThrow(ItemTags.LEAVES)));
        this.itemsToPickUp.add(Ingredient.of(net.minecraft.core.registries.BuiltInRegistries.ITEM.getOrThrow(ItemTags.SAPLINGS)));
        this.itemsToPickUp.add(Ingredient.of(net.minecraft.core.registries.BuiltInRegistries.ITEM.getOrThrow(OccultismTags.Items.FRUITS)));
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
        BrainUtil.clearMemory(this.entity, OccultismMemoryTypes.NO_TREE_IN_WORK_AREA.get());
        BrainUtil.clearMemory(this.entity, OccultismMemoryTypes.UNREACHABLE_WALK_TARGETS.get());
        BrainUtil.clearMemory(this.entity, OccultismMemoryTypes.UNREACHABLE_TREES.get());
        BrainUtil.clearMemory(this.entity, OccultismMemoryTypes.NON_TREE_LOGS.get());
    }
}
