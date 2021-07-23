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

package com.github.klikli_dev.occultism.handlers;

import com.github.klikli_dev.occultism.Occultism;
import com.github.klikli_dev.occultism.common.capability.DoubleJumpCapability;
import com.github.klikli_dev.occultism.common.capability.FamiliarSettingsCapability;
import com.github.klikli_dev.occultism.network.MessageSetJumps;
import com.github.klikli_dev.occultism.network.OccultismPackets;
import com.github.klikli_dev.occultism.registry.OccultismCapabilities;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.Player;
import net.minecraft.entity.player.ServerPlayer;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Occultism.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class CapabilityEventHandler {
    //region Static Methods
    @SubscribeEvent
    public static void onPlayerTick(final TickEvent.PlayerTickEvent evt) {
        if (evt.phase == TickEvent.Phase.END) {
            //Reset the double jump capability
            if (evt.player.isOnGround()) {
                evt.player.getCapability(OccultismCapabilities.DOUBLE_JUMP).ifPresent( cap -> cap.setJumps(0));
            }
        }
    }

    @SubscribeEvent
    public static void onEntityJoinWorld(final EntityJoinWorldEvent evt) {
        if (evt.getEntity() instanceof ServerPlayer) {
            ServerPlayer player = (ServerPlayer) evt.getEntity();
            int jumps = player.getCapability(OccultismCapabilities.DOUBLE_JUMP).map(DoubleJumpCapability::getJumps).orElse(0);
            if (jumps > 0) {
                OccultismPackets.sendTo(player, new MessageSetJumps(jumps));
            }
        }
    }

    @SubscribeEvent
    public static void onEntityConstructing(final AttachCapabilitiesEvent<Entity> evt) {
        if (evt.getObject() instanceof Player) {
            if (!evt.getObject().getCapability(OccultismCapabilities.DOUBLE_JUMP).isPresent()) {
                evt.addCapability(OccultismCapabilities.DOUBLE_JUMP_ID, new DoubleJumpCapability.Dispatcher());
            }
            if (!evt.getObject().getCapability(OccultismCapabilities.FAMILIAR_SETTINGS).isPresent()) {
                evt.addCapability(OccultismCapabilities.FAMILIAR_SETTINGS_ID, new FamiliarSettingsCapability.Dispatcher());
            }
        }
    }
    //endregion Static Methods
}
