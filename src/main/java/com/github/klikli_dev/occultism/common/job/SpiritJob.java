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

import com.github.klikli_dev.occultism.common.entity.spirit.SpiritEntity;
import com.github.klikli_dev.occultism.registry.OccultismSpiritJobs;
import net.minecraft.entity.EntitySize;
import net.minecraft.entity.Pose;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.INBTSerializable;

public abstract class SpiritJob implements INBTSerializable<CompoundNBT> {

    public SpiritEntity entity;
    public ResourceLocation factoryId;

    public SpiritJob(SpiritEntity entity) {
        this.entity = entity;
    }

    public static SpiritJob from(SpiritEntity entity, CompoundNBT nbt) {
        SpiritJobFactory factory = OccultismSpiritJobs.REGISTRY
                .getValue(new ResourceLocation(nbt.getString("factoryId")));
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
    public CompoundNBT serializeNBT() {
        return this.writeJobToNBT(new CompoundNBT());
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        this.readJobFromNBT(nbt);
    }

    /**
     * Sets up the job, e.g. AI Tasks
     */
    public abstract void init();

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
    public CompoundNBT writeJobToNBT(CompoundNBT compound) {
        compound.putString("factoryId", this.getFactoryID().toString());
        return compound;
    }

    /**
     * Reads job data from NBT and initializes the job
     *
     * @param compound the NBT to read from.
     */
    public void readJobFromNBT(CompoundNBT compound) {
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

    public EntitySize getDimensions(Pose pPose, EntitySize original){
        return original;
    }
}
