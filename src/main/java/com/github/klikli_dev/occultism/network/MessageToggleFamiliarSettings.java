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

package com.github.klikli_dev.occultism.network;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.github.klikli_dev.occultism.Occultism;
import com.github.klikli_dev.occultism.common.capability.FamiliarSettingsCapability;
import com.github.klikli_dev.occultism.registry.OccultismCapabilities;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.fml.network.NetworkEvent;

public class MessageToggleFamiliarSettings extends MessageBase {

    //region Fields
    public Map<EntityType<?>, Boolean> familiarsPressed;
    //endregion Fields

    //region Initialization
    public MessageToggleFamiliarSettings(PacketBuffer buf) {
        this.decode(buf);
    }

    public MessageToggleFamiliarSettings(Map<EntityType<?>, Boolean> familiarsPressed) {
        this.familiarsPressed = familiarsPressed;
    }
    //endregion Initialization

    //region Overrides

    @Override
    public void onServerReceived(MinecraftServer minecraftServer, ServerPlayerEntity player,
                                 NetworkEvent.Context context) {

        player.getCapability(OccultismCapabilities.FAMILIAR_SETTINGS).ifPresent(cap -> {
            for (Entry<EntityType<?>, Boolean> toggle : familiarsPressed.entrySet()) {
                if (toggle.getValue()) {
                    cap.setFamiliarEnabled(toggle.getKey(), !cap.isFamiliarEnabled(toggle.getKey()));
                    player.displayClientMessage(
                            new TranslationTextComponent(
                                    "message." + Occultism.MODID + ".familiar." + toggle.getKey().getRegistryName().getPath() +
                                            (cap.isFamiliarEnabled(toggle.getKey()) ? ".enabled" : ".disabled")), true);
                }
            }
        });
    }

    @Override
    public void encode(PacketBuffer buf) {
        for (EntityType<?> familiar : FamiliarSettingsCapability.getFamiliars())
            buf.writeBoolean(this.familiarsPressed.get(familiar));
    }

    @Override
    public void decode(PacketBuffer buf) {
        this.familiarsPressed = new HashMap<>();
        for (EntityType<?> familiar : FamiliarSettingsCapability.getFamiliars())
            this.familiarsPressed.put(familiar, buf.readBoolean());
    }
    //endregion Overrides
}
