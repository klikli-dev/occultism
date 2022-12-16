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

import com.github.klikli_dev.occultism.common.entity.spirit.SpiritEntity;
import com.github.klikli_dev.occultism.registry.OccultismSpiritJobs;
import com.google.common.collect.ImmutableList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraftforge.common.util.INBTSerializable;
import net.tslat.smartbrainlib.api.core.BrainActivityGroup;
import net.tslat.smartbrainlib.api.core.sensor.ExtendedSensor;

import java.util.List;

public abstract class SpiritJob implements INBTSerializable<CompoundTag> {
    public SpiritEntity entity;
    public ResourceLocation factoryId;

    public SpiritJob(SpiritEntity entity) {
        this.entity = entity;
    }

    public static SpiritJob from(SpiritEntity entity, CompoundTag nbt) {
        SpiritJobFactory factory = OccultismSpiritJobs.REGISTRY
                .get().getValue(new ResourceLocation(nbt.getString("factoryId")));
        SpiritJob job = factory.create(entity);
        job.deserializeNBT(nbt);
        return job;
    }

    public ResourceLocation getFactoryID() {
        return this.factoryId;
    }

    public void setFactoryId(ResourceLocation factoryId) {
        this.factoryId = factoryId;
    }

    @Override
    public CompoundTag serializeNBT() {
        return this.writeJobToNBT(new CompoundTag());
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        this.readJobFromNBT(nbt);
    }


    /**
     * Sets up the job, e.g. AI Tasks
     */
    public abstract void init();

    public List<ExtendedSensor<SpiritEntity>> getSensors() {
        return ImmutableList.of();
    }

    public BrainActivityGroup<SpiritEntity> getCoreTasks() {
        return BrainActivityGroup.empty();
    }

    public BrainActivityGroup<SpiritEntity> getIdleTasks() {
        return BrainActivityGroup.empty();
    }

    /**
     * Cleans up the job, e.g. AI Tasks.
     */
    public abstract void cleanup();

    /**
     * updates the job.
     */
    public void update() {

    }

    /**
     * Writes job data to NBT.
     *
     * @param compound the compound to write to.
     * @return the written to compound.
     */
    public CompoundTag writeJobToNBT(CompoundTag compound) {
        compound.putString("factoryId", this.getFactoryID().toString());
        return compound;
    }

    /**
     * Reads job data from NBT and initializes the job
     *
     * @param compound the NBT to read from.
     */
    public void readJobFromNBT(CompoundTag compound) {
    }

    /**
     * Determines if the spirit can pick up the given item stack while on this job.
     *
     * @param entity the item entity to pick up
     * @return true to pick up.
     */
    public boolean canPickupItem(ItemEntity entity) {
        return false;
    }

    public EntityDimensions getDimensions(Pose pPose, EntityDimensions original) {
        return original;
    }
}
