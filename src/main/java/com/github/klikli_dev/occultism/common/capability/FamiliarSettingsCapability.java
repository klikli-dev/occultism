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
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class FamiliarSettingsCapability implements INBTSerializable<CompoundTag> {

    //region Fields
    private boolean greedyEnabled = true;
    private boolean otherworldBirdEnabled = true;
    private boolean batEnabled = true;
    private boolean deerEnabled = true;
    private boolean cthulhuEnabled = true;
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
     *
     * @param settings the existing settings instance.
     */
    public void clone(FamiliarSettingsCapability settings) {
        this.greedyEnabled = settings.greedyEnabled;
        this.otherworldBirdEnabled = settings.otherworldBirdEnabled;
        this.batEnabled = settings.batEnabled;
        this.deerEnabled = settings.deerEnabled;
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

    public boolean isDeerEnabled() {
        return this.deerEnabled;
    }

    public void setDeerEnabled(boolean deerEnabled) {
        this.deerEnabled = deerEnabled;
    }

    public boolean isCthulhuEnabled(){
        return this.cthulhuEnabled;
    }

    public void setCthulhuEnabled(boolean cthulhuEnabled){
        this.cthulhuEnabled = cthulhuEnabled;
    }
    //endregion Methods

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag compound = new CompoundTag();
        compound.putBoolean("greedyEnabled", this.greedyEnabled);
        compound.putBoolean("otherworldBirdEnabled", this.otherworldBirdEnabled);
        compound.putBoolean("batEnabled", this.batEnabled);
        compound.putBoolean("deerEnabled", this.deerEnabled);
        compound.putBoolean("cthulhuEnabled", this.cthulhuEnabled);
        return compound;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        this.greedyEnabled = nbt.getBoolean("greedyEnabled");
        this.otherworldBirdEnabled = nbt.getBoolean("otherworldBirdEnabled");
        this.batEnabled = nbt.getBoolean("batEnabled");
        this.deerEnabled = nbt.getBoolean("deerEnabled");
        this.cthulhuEnabled = nbt.getBoolean("cthulhuEnabled");
    }
    //endregion Methods


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
            return this.familiarSettingsCapability.map(FamiliarSettingsCapability::serializeNBT).orElse(new CompoundTag());
        }

        @Override
        public void deserializeNBT(CompoundTag nbt) {
            this.familiarSettingsCapability.ifPresent(capability -> capability.deserializeNBT(nbt));
        }
        //endregion Overrides

    }
}
