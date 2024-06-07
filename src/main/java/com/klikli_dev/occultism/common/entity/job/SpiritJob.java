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
import com.klikli_dev.occultism.common.entity.spirit.SpiritEntity;
import com.klikli_dev.occultism.registry.OccultismMemoryTypes;
import com.klikli_dev.occultism.registry.OccultismSpiritJobs;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.common.util.INBTSerializable;
import net.tslat.smartbrainlib.api.core.BrainActivityGroup;
import net.tslat.smartbrainlib.api.core.sensor.ExtendedSensor;
import net.tslat.smartbrainlib.util.BrainUtils;

import java.util.List;

public abstract class SpiritJob implements INBTSerializable<CompoundTag> {
    public SpiritEntity entity;
    public ResourceLocation factoryId;

    public SpiritJob(SpiritEntity entity) {
        this.entity = entity;
    }

    public static SpiritJob from(SpiritEntity entity, CompoundTag nbt) {
        SpiritJobFactory factory = OccultismSpiritJobs.REGISTRY
                .get(new ResourceLocation(nbt.getString("factoryId")));
        SpiritJob job = factory.create(entity);
        job.deserializeNBT(entity.level().registryAccess(), nbt);
        return job;
    }

    public ResourceLocation getFactoryID() {
        return this.factoryId;
    }

    public void setFactoryId(ResourceLocation factoryId) {
        this.factoryId = factoryId;
    }

    @Override
    public CompoundTag serializeNBT(HolderLookup.Provider provider) {
        return this.writeJobToNBT(new CompoundTag(), provider);
    }

    @Override
    public void deserializeNBT(HolderLookup.Provider provider, CompoundTag nbt) {
        this.readJobFromNBT(nbt, provider);
    }


    /**
     * Sets up the job, e.g. AI Tasks
     */
    public final void init() {
        this.entity.remakeBrain();
        BrainUtils.setMemory(this.entity, OccultismMemoryTypes.WORK_AREA_CENTER.get(), this.entity.getWorkAreaCenter());
        BrainUtils.setMemory(this.entity, OccultismMemoryTypes.WORK_AREA_SIZE.get(), this.entity.getWorkAreaSize().getValue());
        BrainUtils.setMemory(this.entity, OccultismMemoryTypes.DEPOSIT_POSITION.get(), this.entity.getDepositPosition().orElse(null));
        BrainUtils.setMemory(this.entity, OccultismMemoryTypes.DEPOSIT_FACING.get(), this.entity.getDepositFacing());
        this.onInit();
    }

    protected abstract void onInit();

    public List<ExtendedSensor<SpiritEntity>> getSensors() {
        return ImmutableList.of();
    }

    public BrainActivityGroup<SpiritEntity> getCoreTasks() {
        return BrainActivityGroup.empty();
    }

    public BrainActivityGroup<SpiritEntity> getIdleTasks() {
        return BrainActivityGroup.empty();
    }

    public void handleAdditionalBrainSetup(Brain<? extends SpiritEntity> brain) {

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
    public CompoundTag writeJobToNBT(CompoundTag compound, HolderLookup.Provider provider
    ) {
        compound.putString("factoryId", this.getFactoryID().toString());
        return compound;
    }

    /**
     * Reads job data from NBT and initializes the job
     *
     * @param compound the NBT to read from.
     */
    public void readJobFromNBT(CompoundTag compound, HolderLookup.Provider provider
    ) {
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

    public List<Ingredient> getItemsToPickUp(){
        return List.of();
    }

    public EntityDimensions getDimensions(Pose pPose, EntityDimensions original) {
        return original;
    }

    public void onChangeWorkArea() {

    }
}
