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

package com.github.klikli_dev.occultism.common.capability;

import com.github.klikli_dev.occultism.registry.OccultismCapabilities;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.INBT;
import net.minecraft.core.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class FamiliarSettingsCapability {

    //region Fields
    private boolean greedyEnabled = true;
    private boolean otherworldBirdEnabled = true;
    private boolean batEnabled = true;
    //endregion Fields

    //region Initialization
    public FamiliarSettingsCapability() {
    }
    //endregion Initialization

    //region Getter / Setter

    //endregion Getter / Setter

    //region Methods

    /**
     * Clones the settings from an existing settings instance into this instance
     * @param settings the existing settings instance.
     */
    public void clone(FamiliarSettingsCapability settings) {
        this.greedyEnabled = settings.greedyEnabled;
        this.otherworldBirdEnabled = settings.otherworldBirdEnabled;
        this.batEnabled = settings.batEnabled;
    }

    public CompoundTag write(CompoundTag compound) {
        compound.putBoolean("greedyEnabled", this.greedyEnabled);
        compound.putBoolean("otherworldBirdEnabled", this.otherworldBirdEnabled);
        compound.putBoolean("batEnabled", this.batEnabled);
        return compound;
    }

    public CompoundTag read(CompoundTag compound) {
        this.greedyEnabled = compound.getBoolean("greedyEnabled");
        this.otherworldBirdEnabled = compound.getBoolean("otherworldBirdEnabled");
        this.batEnabled = compound.getBoolean("batEnabled");
        return compound;
    }

    public boolean isGreedyEnabled() {
        return this.greedyEnabled;
    }

    public void setGreedyEnabled(boolean greedyEnabled) {
        this.greedyEnabled = greedyEnabled;
    }

    public boolean isOtherworldBirdEnabled() {
        return this.otherworldBirdEnabled;
    }

    public void setOtherworldBirdEnabled(boolean otherworldBirdEnabled) {
        this.otherworldBirdEnabled = otherworldBirdEnabled;
    }

    public boolean isBatEnabled() {
        return this.batEnabled;
    }

    public void setBatEnabled(boolean batEnabled) {
        this.batEnabled = batEnabled;
    }
    //endregion Methods

    public static class Storage implements Capability.IStorage<FamiliarSettingsCapability> {
        //region Overrides
        @Override
        public INBT writeNBT(Capability<FamiliarSettingsCapability> capability, FamiliarSettingsCapability instance,
                             Direction facing) {
            return instance.write(new CompoundTag());
        }

        @Override
        public void readNBT(Capability<FamiliarSettingsCapability> capability, FamiliarSettingsCapability instance, Direction side,
                            INBT nbt) {
            instance.read((CompoundTag) nbt);
        }
        //endregion Overrides
    }

    public static class Dispatcher implements ICapabilitySerializable<CompoundTag> {

        //region Fields
        private final LazyOptional<FamiliarSettingsCapability> familiarSettingsCapability = LazyOptional.of(
                FamiliarSettingsCapability::new);
        //endregion Fields

        //region Overrides
        @Nonnull
        @Override
        public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
            if (cap == OccultismCapabilities.FAMILIAR_SETTINGS) {
                return this.familiarSettingsCapability.cast();
            }
            return LazyOptional.empty();
        }

        @Override
        public CompoundTag serializeNBT() {
            CompoundTag nbt = new CompoundTag();
            this.familiarSettingsCapability.ifPresent(capability -> {
                capability.write(nbt);
            });
            return nbt;
        }

        @Override
        public void deserializeNBT(CompoundTag nbt) {
            this.familiarSettingsCapability.ifPresent(capability -> capability.read(nbt));
        }
        //endregion Overrides

    }
}
