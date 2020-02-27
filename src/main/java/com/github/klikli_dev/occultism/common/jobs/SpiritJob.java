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

package com.github.klikli_dev.occultism.common.jobs;

import com.github.klikli_dev.occultism.common.entity.spirits.EntitySpirit;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;

public abstract class SpiritJob {

    //region Fields
    public EntitySpirit entity;
    public ResourceLocation factoryId;

    //endregion Fields
    //region Initialization
    public SpiritJob(EntitySpirit entity) {
        this.entity = entity;
    }
    //endregion Initialization

    //region Getter / Setter
    public ResourceLocation getFactoryID() {
        return this.factoryId;
    }

    public void setFactoryId(ResourceLocation factoryId) {
        this.factoryId = factoryId;
    }
    //endregion Getter / Setter


    //region Methods

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
    public NBTTagCompound writeJobToNBT(NBTTagCompound compound) {
        return compound;
    }

    /**
     * Reads job data from NBT and initializes the job
     *
     * @param compound the NBT to read from.
     */
    public void readJobFromNBT(NBTTagCompound compound) {
    }

    /**
     * Determines if the spirit can pick up the given item stack while on this job.
     *
     * @param stack the stack to pick up
     * @return true to pick up.
     */
    public boolean canPickupItem(ItemStack stack) {
        return false;
    }
    //endregion Methods
}
