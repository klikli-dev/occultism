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
import com.github.klikli_dev.occultism.network.MessageDoubleJump;
import com.github.klikli_dev.occultism.network.MessageOpenSatchel;
import com.github.klikli_dev.occultism.network.OccultismPackets;
import com.github.klikli_dev.occultism.util.CuriosUtil;
import com.github.klikli_dev.occultism.util.MovementUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SimpleSound;
import net.minecraft.util.SoundEvents;
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
        if (event.getAction() == GLFW_PRESS && minecraft.gameSettings.keyBindJump.isKeyDown()) {
            if (minecraft.player != null && MovementUtil.doubleJump(minecraft.player)) {
                OccultismPackets.sendToServer(new MessageDoubleJump());
            }
        }
    }

    @SubscribeEvent
    public static void onMouseInput(final InputEvent.MouseInputEvent event) {
        checkBackpackKey(event);
    }

    public static void checkBackpackKey(InputEvent event) {
        Minecraft minecraft = Minecraft.getInstance();

        //check backpack keydown here instead of on key input
        //  because mouse buttons don't properly fire key inputs (
        if (minecraft.currentScreen instanceof SatchelScreen && ClientSetupEventHandler.KEY_BACKPACK.isPressed()) {
            minecraft.player.closeScreen();
        }
        //open satchel
        else if (minecraft.player != null & minecraft.currentScreen == null &&
                 ClientSetupEventHandler.KEY_BACKPACK.isPressed()) {
            if (!CuriosUtil.getBackpack(minecraft.player).isEmpty() ||
                CuriosUtil.getFirstBackpackSlot(minecraft.player) > 0) {
                OccultismPackets.sendToServer(new MessageOpenSatchel());
                minecraft.getSoundHandler().play(SimpleSound.master(SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 0.75F, 1.0F));
            }
        }
    }

    //endregion Static Methods
}
