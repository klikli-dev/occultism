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

package com.klikli_dev.occultism.network.messages;

import com.klikli_dev.occultism.Occultism;
import com.klikli_dev.occultism.common.capability.FamiliarSettingsData;
import com.klikli_dev.occultism.network.IMessage;
import com.klikli_dev.occultism.registry.OccultismDataStorage;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.EntityType;
import org.jspecify.annotations.NonNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class MessageUpdateFamiliarSettings implements IMessage {

    public static final Identifier ID = Identifier.fromNamespaceAndPath(Occultism.MODID, "update_familiar_settings");
    public static final Type<MessageUpdateFamiliarSettings> TYPE = new Type<>(ID);
    public static final StreamCodec<RegistryFriendlyByteBuf, MessageUpdateFamiliarSettings> STREAM_CODEC = CustomPacketPayload.codec(MessageUpdateFamiliarSettings::encode, MessageUpdateFamiliarSettings::new);

    public Map<EntityType<?>, FamiliarSettingsData.FamiliarEffectSettings> settings = new HashMap<>();;

    public MessageUpdateFamiliarSettings(RegistryFriendlyByteBuf buf) {
        this.decode(buf);
    }

    public MessageUpdateFamiliarSettings(Map<EntityType<?>, FamiliarSettingsData.FamiliarEffectSettings> settings) {
        this.settings = settings;
    }

    @Override
    public void onServerReceived(MinecraftServer minecraftServer, ServerPlayer player) {
        var cap = player.getData(OccultismDataStorage.FAMILIAR_SETTINGS.get());
        for (EntityType<?> familiar : this.settings.keySet()) {
            cap.setFamiliarEnabled(familiar, this.settings.get(familiar).isEnabled());
            Map<Holder<MobEffect>, Byte> effectMap = this.settings.get(familiar).getEffectsLevelMap();
            for (Holder<MobEffect> holder : effectMap.keySet()) {
                cap.setEffectAmplifier(familiar, holder, effectMap.get(holder));
            }
        }
        FamiliarSettingsData.syncFor(player);
    }

    @Override
    public void encode(RegistryFriendlyByteBuf buf) {
        CompoundTag tag = new CompoundTag();

        for (var entry : this.settings.entrySet()) {
            EntityType<?> entityType = entry.getKey();
            FamiliarSettingsData.FamiliarEffectSettings settings = entry.getValue();
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

        buf.writeNbt(tag);
    }

    @Override
    public void decode(RegistryFriendlyByteBuf buf) {
        CompoundTag tag = buf.readNbt();
        if (tag == null)
            return;

        this.settings.clear();

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

            this.settings.put(entityType, new FamiliarSettingsData.FamiliarEffectSettings(enabled, effects));
        }
    }

    @Override
    public @NonNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
