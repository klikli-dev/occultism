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

package com.klikli_dev.occultism.registry;

import com.klikli_dev.occultism.common.capability.DoubleJumpCapability;
import com.klikli_dev.occultism.common.capability.FamiliarSettingsCapability;
import com.klikli_dev.occultism.util.StaticUtil;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;

public class OccultismCapabilities {

    public static final ResourceLocation DOUBLE_JUMP_ID = StaticUtil.modLoc("double_jump");
    public static final ResourceLocation FAMILIAR_SETTINGS_ID = StaticUtil.modLoc("familiar_settings");

    public static Capability<DoubleJumpCapability> DOUBLE_JUMP = CapabilityManager.get(new CapabilityToken<>() {
    });
    public static Capability<FamiliarSettingsCapability> FAMILIAR_SETTINGS = CapabilityManager.get(new CapabilityToken<>() {
    });


    public static void onRegisterCapabilities(final RegisterCapabilitiesEvent event) {
        event.register(DoubleJumpCapability.class);
        event.register(FamiliarSettingsCapability.class);
    }

    public static void onPlayerClone(final PlayerEvent.Clone event) {
        //only handle respawn after death -> not portal transfers
        if (event.isWasDeath()) {
            event.getOriginal().reviveCaps();
            //copy capability to new player instance
            event.getEntity().getCapability(OccultismCapabilities.FAMILIAR_SETTINGS).ifPresent(newCap -> {
                        event.getOriginal().getCapability(OccultismCapabilities.FAMILIAR_SETTINGS).ifPresent(newCap::clone);
                    }
            );
        }
    }

    public static void onJoinWorld(final EntityJoinLevelEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            FamiliarSettingsCapability.syncFor(player);
        }
    }
}
