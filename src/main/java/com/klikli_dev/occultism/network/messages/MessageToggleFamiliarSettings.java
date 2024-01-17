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
import com.klikli_dev.occultism.registry.OccultismCapabilities;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntityType;
import net.neoforged.neoforge.registries.ForgeRegistries;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class MessageToggleFamiliarSettings implements IMessage {

    public static final ResourceLocation ID = new ResourceLocation(Occultism.MODID, "toggle_familiar_settings");

    public Map<EntityType<?>, Boolean> familiarsPressed;

    public MessageToggleFamiliarSettings(FriendlyByteBuf buf) {
        this.decode(buf);
    }

    public MessageToggleFamiliarSettings(Map<EntityType<?>, Boolean> familiarsPressed) {
        this.familiarsPressed = familiarsPressed;
    }

    @Override
    public void onServerReceived(MinecraftServer minecraftServer, ServerPlayer player) {
        player.getCapability(OccultismCapabilities.FAMILIAR_SETTINGS).ifPresent(cap -> {
            for (Entry<EntityType<?>, Boolean> toggle : this.familiarsPressed.entrySet()) {
                if (toggle.getValue()) {
                    cap.setFamiliarEnabled(toggle.getKey(), !cap.isFamiliarEnabled(toggle.getKey()));
                    player.displayClientMessage(
                            Component.translatable(
                                    "message." + Occultism.MODID + ".familiar." + ForgeRegistries.ENTITY_TYPES.getKey(toggle.getKey()).getPath() +
                                            (cap.isFamiliarEnabled(toggle.getKey()) ? ".enabled" : ".disabled")), true);
                }
            }
        });
        FamiliarSettingsData.syncFor(player);
    }

    @Override
    public void encode(FriendlyByteBuf buf) {
        for (EntityType<?> familiar : FamiliarSettingsData.getFamiliars())
            buf.writeBoolean(this.familiarsPressed.get(familiar));
    }

    @Override
    public void decode(FriendlyByteBuf buf) {
        this.familiarsPressed = new HashMap<>();
        for (EntityType<?> familiar : FamiliarSettingsData.getFamiliars())
            this.familiarsPressed.put(familiar, buf.readBoolean());
    }

    @Override
    public ResourceLocation id() {
        return ID;
    }
}
