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

import com.github.klikli_dev.occultism.common.entity.IFamiliar;
import com.github.klikli_dev.occultism.registry.OccultismCapabilities;
import com.github.klikli_dev.occultism.registry.OccultismEntities;
import com.google.common.collect.ImmutableList;

import net.minecraft.entity.EntityType;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class FamiliarSettingsCapability {

    //region Fields
    private static ImmutableList<EntityType<? extends IFamiliar>> familiars = null;
    
    private final Map<EntityType<?>, Boolean> familiarEnabled;
    //endregion Fields

    //region Initialization
    public FamiliarSettingsCapability() {
        familiarEnabled = new HashMap<>();
        for (EntityType<?> familiar : getFamiliars())
            familiarEnabled.put(familiar, true);
    }
    //endregion Initialization

    //region Getter / Setter

    //endregion Getter / Setter

    //region Methods
    
    public static List<EntityType<? extends IFamiliar>> getFamiliars() {
        if (familiars == null)
            familiars = ImmutableList.of(OccultismEntities.GREEDY_FAMILIAR_TYPE.get(), OccultismEntities.OTHERWORLD_BIRD_TYPE.get(), OccultismEntities.BAT_FAMILIAR_TYPE.get(), OccultismEntities.DEER_FAMILIAR_TYPE.get(), OccultismEntities.CTHULHU_FAMILIAR_TYPE.get(), OccultismEntities.DEVIL_FAMILIAR_TYPE.get());
        return familiars;
    }

    /**
     * Clones the settings from an existing settings instance into this instance
     * @param settings the existing settings instance.
     */
    public void clone(FamiliarSettingsCapability settings) {
        for (Entry<EntityType<?>, Boolean> entry : settings.familiarEnabled.entrySet())
            familiarEnabled.put(entry.getKey(), entry.getValue());
    }

    public CompoundNBT write(CompoundNBT compound) {
        for (Entry<EntityType<?>, Boolean> entry : familiarEnabled.entrySet())
            compound.putBoolean(entry.getKey().getRegistryName().getPath(), entry.getValue());
        return compound;
    }

    public CompoundNBT read(CompoundNBT compound) {
        for (EntityType<?> familiar : getFamiliars())
            if (compound.contains(familiar.getRegistryName().getPath()))
                familiarEnabled.put(familiar, compound.getBoolean(familiar.getRegistryName().getPath()));
        return compound;
    }
    
    public void setFamiliarEnabled(EntityType<?> familiar, boolean b) {
        familiarEnabled.put(familiar, b);
    }
    
    public boolean isFamiliarEnabled(EntityType<?> familiar) {
        return familiarEnabled.get(familiar);
    }

    //endregion Methods

    public static class Storage implements Capability.IStorage<FamiliarSettingsCapability> {
        //region Overrides
        @Override
        public INBT writeNBT(Capability<FamiliarSettingsCapability> capability, FamiliarSettingsCapability instance,
                             Direction facing) {
            return instance.write(new CompoundNBT());
        }

        @Override
        public void readNBT(Capability<FamiliarSettingsCapability> capability, FamiliarSettingsCapability instance, Direction side,
                            INBT nbt) {
            instance.read((CompoundNBT) nbt);
        }
        //endregion Overrides
    }

    public static class Dispatcher implements ICapabilitySerializable<CompoundNBT> {

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
        public CompoundNBT serializeNBT() {
            CompoundNBT nbt = new CompoundNBT();
            this.familiarSettingsCapability.ifPresent(capability -> {
                capability.write(nbt);
            });
            return nbt;
        }

        @Override
        public void deserializeNBT(CompoundNBT nbt) {
            this.familiarSettingsCapability.ifPresent(capability -> capability.read(nbt));
        }
        //endregion Overrides

    }
}
