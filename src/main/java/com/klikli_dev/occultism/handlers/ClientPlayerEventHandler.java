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

package com.klikli_dev.occultism.handlers;

import com.klikli_dev.occultism.Occultism;
import com.klikli_dev.occultism.client.gui.satchel.SatchelScreen;
import com.klikli_dev.occultism.client.gui.storage.StorageRemoteGui;
import com.klikli_dev.occultism.network.Networking;
import com.klikli_dev.occultism.network.messages.MessageDoubleJump;
import com.klikli_dev.occultism.network.messages.MessageOpenSatchel;
import com.klikli_dev.occultism.network.messages.MessageOpenStorageRemote;
import com.klikli_dev.occultism.network.messages.MessageToggleFamiliarSettings;
import com.klikli_dev.occultism.registry.OccultismBlocks;
import com.klikli_dev.occultism.registry.OccultismSounds;
import com.klikli_dev.occultism.util.CuriosUtil;
import com.klikli_dev.occultism.util.MovementUtil;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.InputEvent;
import net.neoforged.neoforge.event.PlayLevelSoundEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import static org.lwjgl.glfw.GLFW.GLFW_PRESS;

@EventBusSubscriber(modid = Occultism.MODID, bus = EventBusSubscriber.Bus.GAME, value = Dist.CLIENT)
public class ClientPlayerEventHandler {
    //region Static Methods

    @SubscribeEvent
    public static void onPlaySoundAt(PlayLevelSoundEvent.AtPosition event) {
        //handle spirit fire sound disable config
        if (event.getLevel().isClientSide &&
                Occultism.CLIENT_CONFIG.misc.disableSpiritFireSuccessSound.get() &&
                event.getSound().value() == OccultismSounds.START_RITUAL.get() &&
                event.getLevel().getBlockState(BlockPos.containing(event.getPosition())).getBlock() == OccultismBlocks.SPIRIT_FIRE.get()) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void onKeyInput(final InputEvent.Key event) {
        Minecraft minecraft = Minecraft.getInstance();
        checkBackpackKey(event);
        checkStorageRemoteKey(event);
        checkFamiliarSettingsKeys(event);
        if (event.getAction() == GLFW_PRESS && minecraft.options.keyJump.isDown()) {
            if (minecraft.player != null && MovementUtil.doubleJump(minecraft.player)) {
                Networking.sendToServer(new MessageDoubleJump());
            }
        }
    }

    @SubscribeEvent
    public static void onMouseInput(final InputEvent.MouseButton.Key event) {
        //handle mouse button bindings for storage keys
        checkBackpackKey(event);
        checkStorageRemoteKey(event);
        checkFamiliarSettingsKeys(event);
    }

    public static void checkBackpackKey(InputEvent event) {
        Minecraft minecraft = Minecraft.getInstance();

        if (minecraft.screen instanceof SatchelScreen && ClientSetupEventHandler.KEY_BACKPACK.consumeClick()) {
            minecraft.player.closeContainer();
        }
        //open satchel
        else if (minecraft.player != null & minecraft.screen == null &&
                ClientSetupEventHandler.KEY_BACKPACK.consumeClick()) {
            if (!CuriosUtil.getBackpack(minecraft.player).isEmpty() ||
                    CuriosUtil.getFirstBackpackSlot(minecraft.player) > 0) {
                Networking.sendToServer(new MessageOpenSatchel());
                minecraft.getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.ARMOR_EQUIP_LEATHER.value(), 0.75F, 1.0F));
            }
        }
    }

    public static void checkStorageRemoteKey(InputEvent event) {
        Minecraft minecraft = Minecraft.getInstance();

        if (minecraft.screen instanceof StorageRemoteGui && ClientSetupEventHandler.KEY_STORAGE_REMOTE.consumeClick()) {
            minecraft.player.closeContainer();
        }
        //open satchel
        else if (minecraft.player != null & minecraft.screen == null &&
                ClientSetupEventHandler.KEY_STORAGE_REMOTE.consumeClick()) {

            if (!CuriosUtil.getStorageRemoteCurio(minecraft.player).isEmpty() ||
                    CuriosUtil.getFirstStorageRemoteSlot(minecraft.player) > 0) {
                Networking.sendToServer(new MessageOpenStorageRemote());
                minecraft.getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.ARMOR_EQUIP_DIAMOND.value(), 0.75F, 1.0F));
            }
        }
    }

    public static void checkFamiliarSettingsKeys(InputEvent event) {
        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.player != null & minecraft.screen == null) {
            boolean familiarKeyPressed = false;
            Map<EntityType<?>, Boolean> familiarsPressed = new HashMap<>();

            for (Entry<EntityType<?>, KeyMapping> entry : ClientSetupEventHandler.keysFamiliars.entrySet()) {
                boolean isPressed = entry.getValue().consumeClick();
                if (isPressed)
                    familiarKeyPressed = true;
                familiarsPressed.put(entry.getKey(), isPressed);
            }
            if (familiarKeyPressed) {
                Networking.sendToServer(new MessageToggleFamiliarSettings(familiarsPressed));
            }
        }
    }

    //endregion Static Methods
}
