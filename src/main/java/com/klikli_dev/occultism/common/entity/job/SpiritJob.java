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
import com.klikli_dev.occultism.common.entity.ai.BrainUtil;
import com.klikli_dev.occultism.common.entity.spirit.SpiritEntity;
import com.klikli_dev.occultism.registry.OccultismMemoryTypes;
import com.klikli_dev.occultism.registry.OccultismSpiritJobs;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.ai.ActivityData;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.List;

public abstract class SpiritJob {
    public SpiritEntity entity;
    public Identifier factoryId;

    public SpiritJob(SpiritEntity entity) {
        this.entity = entity;
    }

    public static SpiritJob from(SpiritEntity entity, CompoundTag nbt) {
        SpiritJobFactory factory = OccultismSpiritJobs.REGISTRY
                .get(Identifier.parse(nbt.getStringOr("factoryId", ""))).orElseThrow().value();
        SpiritJob job = factory.create(entity);
        job.readJobFromNBT(nbt, entity.level().registryAccess());
        return job;
    }

    public Identifier getFactoryID() {
        return this.factoryId;
    }

    public void setFactoryId(Identifier factoryId) {
        this.factoryId = factoryId;
    }


    /**
     * Sets up the job, e.g. AI Tasks
     */
    public final void init() {
        BrainUtil.setMemory(this.entity, OccultismMemoryTypes.WORK_AREA_CENTER.get(), this.entity.getWorkAreaCenter());
        BrainUtil.setMemory(this.entity, OccultismMemoryTypes.WORK_AREA_SIZE.get(), this.entity.getWorkAreaSize().getValue());
        BrainUtil.setMemory(this.entity, OccultismMemoryTypes.DEPOSIT_POSITION.get(), this.entity.getDepositPosition().orElse(null));
        BrainUtil.setMemory(this.entity, OccultismMemoryTypes.DEPOSIT_FACING.get(), this.entity.getDepositFacing());
        this.onInit();
    }

    protected abstract void onInit();

    public List<MemoryModuleType<?>> getMemoryTypes() {
        return OccultismMemoryTypes.all();
    }

    public List<SensorType<? extends Sensor<SpiritEntity>>> getSensorTypes() {
        return ImmutableList.of();
    }

    public List<ActivityData<SpiritEntity>> getActivityData() {
        return ImmutableList.of();
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
