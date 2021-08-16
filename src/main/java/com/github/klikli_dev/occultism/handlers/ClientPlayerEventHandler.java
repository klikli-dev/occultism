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
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SimpleSound;
import net.minecraft.sounds.SoundEvents;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static org.lwjgl.glfw.GLFW.GLFW_PRESS;

@Mod.EventBusSubscriber(modid = Occultism.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class ClientPlayerEventHandler {
    //region Static Methods

    @SubscribeEvent
    public static void onKeyInput(final InputEvent.KeyInputEvent event) {
        Minecraft minecraft = Minecraft.getInstance();
        checkBackpackKey(event);
        checkStorageRemoteKey(event);
        checkFamiliarSettingsKeys(event);
        if (event.getAction() == GLFW_PRESS && minecraft.gameSettings.keyBindJump.isKeyDown()) {
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

        if (minecraft.screen instanceof SatchelScreen && ClientSetupEventHandler.KEY_BACKPACK.isPressed()) {
            minecraft.player.closeScreen();
        }
        //open satchel
        else if (minecraft.player != null & minecraft.screen == null &&
                ClientSetupEventHandler.KEY_BACKPACK.isPressed()) {
            if (!CuriosUtil.getBackpack(minecraft.player).isEmpty() ||
                    CuriosUtil.getFirstBackpackSlot(minecraft.player) > 0) {
                OccultismPackets.sendToServer(new MessageOpenSatchel());
                minecraft.getSoundHandler().play(SimpleSound.master(SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 0.75F, 1.0F));
            }
        }
    }

    public static void checkStorageRemoteKey(InputEvent event) {
        Minecraft minecraft = Minecraft.getInstance();

        if (minecraft.screen instanceof StorageRemoteGui && ClientSetupEventHandler.KEY_STORAGE_REMOTE.isPressed()) {
            minecraft.player.closeScreen();
        }
        //open satchel
        else if (minecraft.player != null & minecraft.screen == null &&
                ClientSetupEventHandler.KEY_STORAGE_REMOTE.isPressed()) {

            if (!CuriosUtil.getStorageRemote(minecraft.player).isEmpty() ||
                    CuriosUtil.getFirstStorageRemoteSlot(minecraft.player) > 0) {
                OccultismPackets.sendToServer(new MessageOpenStorageRemote());
                minecraft.getSoundHandler().play(SimpleSound.master(SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 0.75F, 1.0F));
            }
        }
    }

    public static void checkFamiliarSettingsKeys(InputEvent event) {
        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.player != null & minecraft.screen == null) {
            boolean familiarGreedy = ClientSetupEventHandler.KEY_FAMILIAR_GREEDY.isPressed();
            boolean familiarOtherworldBird = ClientSetupEventHandler.KEY_FAMILIAR_OTHERWORLD_BIRD.isPressed();
            boolean familiarBat = ClientSetupEventHandler.KEY_FAMILIAR_BAT.isPressed();
            boolean familiarDeer = ClientSetupEventHandler.KEY_FAMILIAR_DEER.isPressed();
            if (familiarGreedy|| familiarOtherworldBird || familiarBat || familiarDeer) {
                OccultismPackets.sendToServer(new MessageToggleFamiliarSettings(familiarOtherworldBird, familiarGreedy, familiarBat, familiarDeer));
            }
        }
    }

    //endregion Static Methods
}
