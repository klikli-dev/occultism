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

package com.klikli_dev.occultism.common.capability;

import com.google.common.collect.ImmutableList;
import com.klikli_dev.occultism.common.data.FamiliarEffects;
import com.klikli_dev.occultism.common.entity.familiar.IFamiliar;
import com.klikli_dev.occultism.network.Networking;
import com.klikli_dev.occultism.network.messages.MessageSyncFamiliarSettings;
import com.klikli_dev.occultism.registry.OccultismDataStorage;
import com.klikli_dev.occultism.registry.OccultismEntities;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.neoforged.neoforge.common.util.ValueIOSerializable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;

public class FamiliarSettingsData  implements ValueIOSerializable {

    private static ImmutableList<EntityType<? extends IFamiliar>> familiars = null;

    private final Map<EntityType<?>, FamiliarEffectSettings> familiarEnabled;

    public FamiliarSettingsData() {
        this.familiarEnabled = new HashMap<>();
        for (EntityType<?> familiar : getFamiliars()) {
            ImmutableList<FamiliarEffects.FamiliarEffectDefinition> effects = FamiliarEffects.effectMap().get(familiar);
            Map<Holder<MobEffect>, Byte> map;
            if (effects != null && !effects.isEmpty()) {
                map = new HashMap<>(effects.size());
                for (var e : effects) {
                    map.put(e.effect(), e.iesniumValue());
                }
            } else {
                map = Map.of();
            }
            this.familiarEnabled.put(familiar, new FamiliarEffectSettings(true, map));
        }
    }

    public static List<EntityType<? extends IFamiliar>> getFamiliars() {
        if (familiars == null)
            familiars = ImmutableList.of(
                    OccultismEntities.GREEDY_FAMILIAR_TYPE.get(),
                    OccultismEntities.DRIKWING_FAMILIAR_TYPE.get(),
                    OccultismEntities.WINGNIS_FAMILIAR_TYPE.get(),
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

    public static void syncFor(ServerPlayer player) {
        player.getData(OccultismDataStorage.FAMILIAR_SETTINGS).sync(player);
    }

    /**
     * Clones the settings from an existing settings instance into this instance
     *
     * @param settings the existing settings instance.
     */
    public void clone(FamiliarSettingsData settings) {
        for (Entry<EntityType<?>, FamiliarEffectSettings> entry : settings.familiarEnabled.entrySet())
            this.familiarEnabled.put(entry.getKey(), entry.getValue());
    }

    public void setFamiliarEnabled(EntityType<?> familiar, boolean b) {
        FamiliarEffectSettings settings = this.familiarEnabled.get(familiar);
        settings.setEnabled(b);
        this.familiarEnabled.put(familiar, settings);
    }

    public boolean isFamiliarEnabled(EntityType<?> familiar) {
        return familiar != null && this.familiarEnabled.get(familiar).isEnabled();
    }

    public void setEffectAmplifier(EntityType<?> familiar, Holder<MobEffect> effectHolder, byte power) {
        FamiliarEffectSettings settings = this.familiarEnabled.get(familiar);
        settings.setEffectsAmplifier(effectHolder, power);
        this.familiarEnabled.put(familiar, settings);
    }

    public int getEffectAmplifier(EntityType<?> familiar, Holder<MobEffect> effectHolder) {
        if (familiar == null || effectHolder == null)
            return -1;
        return this.familiarEnabled.get(familiar).getEffectsLevelMap().get(effectHolder);
    }

    public void sync(ServerPlayer player) {
        Networking.sendTo(player, new MessageSyncFamiliarSettings(this, player.registryAccess()));
    }

    public Map<EntityType<?>, FamiliarEffectSettings> getMap() {
        return this.familiarEnabled;
    }

    public CompoundTag serializeNBT(Provider provider) {
        CompoundTag tag = new CompoundTag();

        for (var entry : this.familiarEnabled.entrySet()) {
            EntityType<?> entityType = entry.getKey();
            FamiliarEffectSettings settings = entry.getValue();
            Identifier entityId = BuiltInRegistries.ENTITY_TYPE.getKey(entityType);

            CompoundTag familiarTag = new CompoundTag();
            familiarTag.putBoolean("enabled", settings.isEnabled());

            CompoundTag effectsTag = new CompoundTag();
            for (var effectEntry : settings.getEffectsLevelMap().entrySet()) {
                Identifier effectId = effectEntry.getKey().unwrapKey()
                        .orElseThrow().identifier();

                effectsTag.putByte(effectId.toString(), effectEntry.getValue());
            }

            familiarTag.put("effects", effectsTag);

            tag.put(entityId.toString(), familiarTag);
        }
        return tag;
    }

    public void deserializeNBT(Provider provider, CompoundTag tag) {
        this.familiarEnabled.clear();

        for (String entityKey : tag.keySet()) {
            Identifier entityId = Identifier.parse(entityKey);

            EntityType<?> entityType = BuiltInRegistries.ENTITY_TYPE.getValue(entityId);

            CompoundTag familiarTag = tag.getCompoundOrEmpty(entityKey);

            boolean enabled = familiarTag.getBooleanOr("enabled", true);

            Map<Holder<MobEffect>, Byte> effects = new HashMap<>();

            CompoundTag effectsTag = familiarTag.getCompoundOrEmpty("effects");

            for (String effectKey : effectsTag.keySet()) {
                Identifier effectId = Identifier.parse(effectKey);

                Optional<Holder.Reference<MobEffect>> holder =
                        BuiltInRegistries.MOB_EFFECT.get(ResourceKey.create(Registries.MOB_EFFECT, effectId));

                holder.ifPresent(effect ->
                        effects.put(effect, effectsTag.getByteOr(effectKey, (byte) 0)));
            }

            this.familiarEnabled.put(entityType, new FamiliarEffectSettings(enabled, effects));
        }
    }

    @Override
    public void serialize(ValueOutput output) {
        for (var entry : this.familiarEnabled.entrySet()) {
            Identifier entityId = BuiltInRegistries.ENTITY_TYPE.getKey(entry.getKey());
            FamiliarEffectSettings settings = entry.getValue();

            ValueOutput familiar = output.child(entityId.toString());

            familiar.putBoolean("enabled", settings.isEnabled());

            ValueOutput effects = familiar.child("effects");

            for (var effect : settings.getEffectsLevelMap().entrySet()) {
                Identifier effectId = effect.getKey().unwrapKey().orElseThrow().identifier();
                effects.putByte(effectId.toString(), effect.getValue());
            }
        }
    }

    @Override
    public void deserialize(ValueInput input) {
        this.familiarEnabled.clear();

        for (EntityType<?> familiar : getFamiliars()) {
            Identifier entityId = BuiltInRegistries.ENTITY_TYPE.getKey(familiar);

            Optional<ValueInput> familiarInput = input.child(entityId.toString());

            if (familiarInput.isEmpty()) {
                continue;
            }

            ValueInput familiarTag = familiarInput.get();

            boolean enabled = familiarTag.getBooleanOr("enabled", true);

            Map<Holder<MobEffect>, Byte> effects = new HashMap<>();

            ValueInput effectsInput = familiarTag.childOrEmpty("effects");

            ImmutableList<FamiliarEffects.FamiliarEffectDefinition> definitions =
                    FamiliarEffects.effectMap().get(familiar);

            if (definitions != null) {
                for (var definition : definitions) {
                    Identifier effectId = definition.effect()
                            .unwrapKey()
                            .orElseThrow()
                            .identifier();

                    byte amplifier = effectsInput.getByteOr(
                            effectId.toString(),
                            definition.iesniumValue());

                    effects.put(definition.effect(), amplifier);
                }
            }

            this.familiarEnabled.put(
                    familiar,
                    new FamiliarEffectSettings(enabled, effects));
        }
    }

    public static class FamiliarEffectSettings {
        boolean enabled;
        Map<Holder<MobEffect>, Byte> effectsLevelMap;

        public FamiliarEffectSettings(boolean enabled, Map<Holder<MobEffect>, Byte> effectsLevelMap) {
            this.enabled = enabled;
            this.effectsLevelMap = effectsLevelMap;
        }

        public void setEnabled(boolean bool) {
            this.enabled = bool;
        }

        public boolean isEnabled() {
            return this.enabled;
        }

        public void setEffectsAmplifier(Holder<MobEffect> effectHolder, Byte value) {
            this.effectsLevelMap.put(effectHolder, value);
        }

        public Map<Holder<MobEffect>, Byte> getEffectsLevelMap() {
            return this.effectsLevelMap;
        }
    }
}
