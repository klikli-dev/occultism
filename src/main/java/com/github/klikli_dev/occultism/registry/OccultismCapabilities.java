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

package com.github.klikli_dev.occultism.registry;

import com.github.klikli_dev.occultism.common.capability.DoubleJumpCapability;
import com.github.klikli_dev.occultism.common.capability.FamiliarSettingsCapability;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.InteractionResult;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

import static com.github.klikli_dev.occultism.util.StaticUtil.modLoc;

public class OccultismCapabilities {

    //region Fields
    public static final ResourceLocation DOUBLE_JUMP_ID = modLoc("double_jump");
    public static final ResourceLocation FAMILIAR_SETTINGS_ID = modLoc("familiar_settings");

    @CapabilityInject(DoubleJumpCapability.class)
    public static Capability<DoubleJumpCapability> DOUBLE_JUMP;

    @CapabilityInject(FamiliarSettingsCapability.class)
    public static Capability<FamiliarSettingsCapability> FAMILIAR_SETTINGS;
    //endregion Fields

    //region Static Methods
    public static void commonSetup(final FMLCommonSetupEvent event) {
        CapabilityManager.INSTANCE
                .register(DoubleJumpCapability.class, new DoubleJumpCapability.Storage(), DoubleJumpCapability::new);
        CapabilityManager.INSTANCE
                .register(FamiliarSettingsCapability.class, new FamiliarSettingsCapability.Storage(), FamiliarSettingsCapability::new);
    }

    public static void onPlayerClone(final PlayerEvent.Clone event) {
        //only handle respawn after death -> not portal transfers
        if(event.isWasDeath()){
            //copy capability to new player instance
            event.getPlayer().getCapability(OccultismCapabilities.FAMILIAR_SETTINGS).ifPresent( newCap -> {
                        event.getOriginal().getCapability(OccultismCapabilities.FAMILIAR_SETTINGS).ifPresent( oldCap -> {
                            newCap.clone(oldCap);
                        });
                    }
            );
        }
    }
    //endregion Static Methods
}
