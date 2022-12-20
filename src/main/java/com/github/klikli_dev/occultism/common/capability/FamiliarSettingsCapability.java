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

import com.github.klikli_dev.occultism.common.entity.familiar.IFamiliar;
import com.github.klikli_dev.occultism.registry.OccultismCapabilities;
import com.github.klikli_dev.occultism.registry.OccultismEntities;
import com.google.common.collect.ImmutableList;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class FamiliarSettingsCapability implements INBTSerializable<CompoundTag> {

    private static ImmutableList<EntityType<? extends IFamiliar>> familiars = null;

    private final Map<EntityType<?>, Boolean> familiarEnabled;

    public FamiliarSettingsCapability() {
        this.familiarEnabled = new HashMap<>();
        for (EntityType<?> familiar : getFamiliars())
            this.familiarEnabled.put(familiar, true);
    }

    public static List<EntityType<? extends IFamiliar>> getFamiliars() {
        if (familiars == null)
            familiars = ImmutableList.of(
                    OccultismEntities.GREEDY_FAMILIAR_TYPE.get(),
                    OccultismEntities.OTHERWORLD_BIRD_TYPE.get(),
                    OccultismEntities.BAT_FAMILIAR_TYPE.get(),
                    OccultismEntities.DEER_FAMILIAR_TYPE.get(),
                    OccultismEntities.CTHULHU_FAMILIAR_TYPE.get(),
                    OccultismEntities.DEVIL_FAMILIAR_TYPE.get(),
                    OccultismEntities.DRAGON_FAMILIAR_TYPE.get(),
                    OccultismEntities.BLACKSMITH_FAMILIAR_TYPE.get(),
                    OccultismEntities.GUARDIAN_FAMILIAR_TYPE.get(),
                    OccultismEntities.HEADLESS_FAMILIAR_TYPE.get(),
                    OccultismEntities.CHIMERA_FAMILIAR_TYPE.get(),
                    OccultismEntities.GOAT_FAMILIAR_TYPE.get(),
                    OccultismEntities.SHUB_NIGGURATH_FAMILIAR_TYPE.get(),
                    OccultismEntities.BEHOLDER_FAMILIAR_TYPE.get(),
                    OccultismEntities.FAIRY_FAMILIAR_TYPE.get(),
                    OccultismEntities.MUMMY_FAMILIAR_TYPE.get(),
                    OccultismEntities.BEAVER_FAMILIAR_TYPE.get()
            );
        return familiars;
    }

    /**
     * Clones the settings from an existing settings instance into this instance
     *
     * @param settings the existing settings instance.
     */
    public void clone(FamiliarSettingsCapability settings) {
        for (Entry<EntityType<?>, Boolean> entry : settings.familiarEnabled.entrySet())
            this.familiarEnabled.put(entry.getKey(), entry.getValue());
    }

    public void setFamiliarEnabled(EntityType<?> familiar, boolean b) {
        this.familiarEnabled.put(familiar, b);
    }

    public boolean isFamiliarEnabled(EntityType<?> familiar) {
        return this.familiarEnabled.get(familiar);
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag compound = new CompoundTag();
        for (Entry<EntityType<?>, Boolean> entry : this.familiarEnabled.entrySet())
            compound.putBoolean(entry.getKey().getRegistryName().getPath(), entry.getValue());
        return compound;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        for (EntityType<?> familiar : getFamiliars())
            if (nbt.contains(familiar.getRegistryName().getPath()))
                this.familiarEnabled.put(familiar, nbt.getBoolean(familiar.getRegistryName().getPath()));
    }

    public static class Dispatcher implements ICapabilitySerializable<CompoundTag> {

        private final LazyOptional<FamiliarSettingsCapability> familiarSettingsCapability = LazyOptional.of(
                FamiliarSettingsCapability::new);

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
    }
}
