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
import com.github.klikli_dev.occultism.client.gui.storage.SatchelScreen;
import com.github.klikli_dev.occultism.client.gui.storage.StorageRemoteGui;
import com.github.klikli_dev.occultism.network.*;
import com.github.klikli_dev.occultism.util.CuriosUtil;
import com.github.klikli_dev.occultism.util.MovementUtil;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import javax.swing.text.JTextComponent;

import static org.lwjgl.glfw.GLFW.GLFW_PRESS;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

@Mod.EventBusSubscriber(modid = Occultism.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class ClientPlayerEventHandler {
    //region Static Methods

    @SubscribeEvent
    public static void onKeyInput(final InputEvent.KeyInputEvent event) {
        Minecraft minecraft = Minecraft.getInstance();
        checkBackpackKey(event);
        checkStorageRemoteKey(event);
        checkFamiliarSettingsKeys(event);
        if (event.getAction() == GLFW_PRESS && minecraft.options.keyJump.isDown()) {
            if (minecraft.player != null && MovementUtil.doubleJump(minecraft.player)) {
                OccultismPackets.sendToServer(new MessageDoubleJump());
            }
        }
    }

    @SubscribeEvent
    public static void onMouseInput(final InputEvent.MouseInputEvent event) {
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
                OccultismPackets.sendToServer(new MessageOpenSatchel());
                minecraft.getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.ARMOR_EQUIP_LEATHER, 0.75F, 1.0F));
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
                OccultismPackets.sendToServer(new MessageOpenStorageRemote());
                minecraft.getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.ARMOR_EQUIP_DIAMOND, 0.75F, 1.0F));
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
                OccultismPackets.sendToServer(new MessageToggleFamiliarSettings(familiarsPressed));
            }
        }
    }

    //endregion Static Methods
}
