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
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.fml.common.registry.GameRegistry;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public abstract class SpiritJob implements INBTSerializable<CompoundNBT> {

    //region Fields
    public SpiritEntity entity;
    public ResourceLocation factoryId;

    //endregion Fields
    //region Initialization
    public SpiritJob(SpiritEntity entity) {
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

    //region Overrides
    @Override
    public CompoundNBT serializeNBT() {
        return this.writeJobToNBT(new CompoundNBT());
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        this.readJobFromNBT(nbt);
    }
    //endregion Overrides

    public static SpiritJob from(CompoundNBT nbt){
        //TODO: enable once spirit job factory and registry are ready
//        SpiritJobFactory factory = GameRegistry.findRegistry(SpiritJobFactory.class).getValue(new ResourceLocation(nbt.getString("factoryId")));
//        SpiritJob job = factory.create(this);
//        job.deserializeNBT(nbt);
//        return job;
        throw new NotImplementedException();
    }

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
     * @param stack the stack to pick up
     * @return true to pick up.
     */
    public boolean canPickupItem(ItemStack stack) {
        return false;
    }
    //endregion Methods
}
