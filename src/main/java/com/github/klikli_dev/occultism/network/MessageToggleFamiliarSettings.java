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

import com.github.klikli_dev.occultism.Occultism;
import com.github.klikli_dev.occultism.registry.OccultismCapabilities;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraftforge.fmllegacy.network.NetworkEvent;

public class MessageToggleFamiliarSettings extends MessageBase {

    //region Fields
    public boolean toggleOtherworldBird;
    public boolean toggleGreedy;
    public boolean toggleBat;
    public boolean toggleDeer;
    public boolean toggleCthulhu;
    //endregion Fields

    //region Initialization
    public MessageToggleFamiliarSettings(FriendlyByteBuf buf) {
        this.decode(buf);
    }

    public MessageToggleFamiliarSettings(boolean toggleOtherworldBird, boolean toggleGreedy, boolean toggleBat, boolean toggleDeer, boolean toggleCthulhu) {
        this.toggleOtherworldBird = toggleOtherworldBird;
        this.toggleGreedy = toggleGreedy;
        this.toggleBat = toggleBat;
        this.toggleDeer = toggleDeer;
        this.toggleCthulhu = toggleCthulhu;
    }
    //endregion Initialization

    //region Overrides

    @Override
    public void onServerReceived(MinecraftServer minecraftServer, ServerPlayer player,
                                 NetworkEvent.Context context) {

        player.getCapability(OccultismCapabilities.FAMILIAR_SETTINGS).ifPresent(cap -> {
            if (this.toggleGreedy) {
                cap.setGreedyEnabled(!cap.isGreedyEnabled());
                player.displayClientMessage(
                        new TranslatableComponent(
                                "message." + Occultism.MODID + ".familiar.greedy." +
                                        (cap.isGreedyEnabled() ? "enabled" : "disabled")), true);
            }
            if (this.toggleOtherworldBird) {
                cap.setOtherworldBirdEnabled(!cap.isOtherworldBirdEnabled());
                player.displayClientMessage(
                        new TranslatableComponent(
                                "message." + Occultism.MODID + ".familiar.otherworld_bird." +
                                        (cap.isOtherworldBirdEnabled() ? "enabled" : "disabled")), true);
            }
            if (this.toggleBat) {
                cap.setBatEnabled(!cap.isBatEnabled());
                player.displayClientMessage(
                        new TranslatableComponent(
                                "message." + Occultism.MODID + ".familiar.bat." +
                                        (cap.isBatEnabled() ? "enabled" : "disabled")), true);
            }
            if (this.toggleDeer) {
                cap.setDeerEnabled(!cap.isDeerEnabled());
                player.displayClientMessage(
                        new TranslatableComponent(
                                "message." + Occultism.MODID + ".familiar.deer." +
                                        (cap.isDeerEnabled() ? "enabled" : "disabled")), true);
            }
            if (this.toggleCthulhu) {
                cap.setCthulhuEnabled(!cap.isCthulhuEnabled());
                player.displayClientMessage(
                        new TranslatableComponent(
                                "message." + Occultism.MODID + ".familiar.cthulhu." +
                                        (cap.isCthulhuEnabled() ? "enabled" : "disabled")), true);
            }
        });
    }

    @Override
    public void encode(FriendlyByteBuf buf) {
        buf.writeBoolean(this.toggleOtherworldBird);
        buf.writeBoolean(this.toggleGreedy);
        buf.writeBoolean(this.toggleBat);
        buf.writeBoolean(this.toggleDeer);
        buf.writeBoolean(this.toggleCthulhu);
    }

    @Override
    public void decode(FriendlyByteBuf buf) {
        this.toggleOtherworldBird = buf.readBoolean();
        this.toggleGreedy = buf.readBoolean();
        this.toggleBat = buf.readBoolean();
        this.toggleDeer = buf.readBoolean();
        this.toggleCthulhu = buf.readBoolean();
    }
    //endregion Overrides
}
