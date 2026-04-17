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
import com.klikli_dev.occultism.crafting.recipe.OccultismRecipeManagerClient;
import com.klikli_dev.occultism.client.gui.storage.StorageControllerGui;
import com.klikli_dev.occultism.client.gui.storage.StorageRemoteGui;
import com.klikli_dev.occultism.network.Networking;
import com.klikli_dev.occultism.network.messages.*;
import com.klikli_dev.occultism.registry.OccultismBlocks;
import com.klikli_dev.occultism.registry.OccultismSounds;
import com.klikli_dev.occultism.util.CuriosUtil;
import com.klikli_dev.occultism.util.MovementUtil;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.ChatScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.inventory.ChestMenu;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientPlayerNetworkEvent;
import net.neoforged.neoforge.client.event.InputEvent;
import net.neoforged.neoforge.client.event.RecipesReceivedEvent;
import net.neoforged.neoforge.client.event.ScreenEvent;
import net.neoforged.neoforge.event.PlayLevelSoundEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import static org.lwjgl.glfw.GLFW.GLFW_PRESS;

@EventBusSubscriber(modid = Occultism.MODID, value = Dist.CLIENT)
public class ClientPlayerEventHandler {
    //region Static Methods

    @SubscribeEvent
    public static void onPlaySoundAt(PlayLevelSoundEvent.AtPosition event) {
        //handle spirit fire sound disable config
        if (event.getLevel().isClientSide() &&
                Occultism.CLIENT_CONFIG.misc.disableSpiritFireSuccessSound.get() &&
                event.getSound() != null &&
                event.getSound().value() == OccultismSounds.START_RITUAL.get() &&
                event.getLevel().getBlockState(BlockPos.containing(event.getPosition())).getBlock() == OccultismBlocks.SPIRIT_FIRE.get()) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void onKeyInput(final InputEvent.Key event) {
        Minecraft minecraft = Minecraft.getInstance();
        checkBackpackKey();
        checkEnderBagKey();
        checkStorageRemoteKey();
        checkFamiliarSettingsKeys();
        if (event.getAction() == GLFW_PRESS && minecraft.options.keyJump.isDown()) {
            if (minecraft.player != null && MovementUtil.doubleJump(minecraft.player)) {
                Networking.sendToServer(new MessageDoubleJump());
            }
        }
    }

    @SubscribeEvent
    public static void onMouseInput(final InputEvent.MouseButton.Key event) {
        //handle mouse button bindings for storage keys
        checkBackpackKey();
        checkEnderBagKey();
        checkStorageRemoteKey();
        checkFamiliarSettingsKeys();
    }

    public static void checkBackpackKey() {
        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.player != null & minecraft.screen == null && ClientSetupEventHandler.KEY_BACKPACK.consumeClick()
                && (!CuriosUtil.getBackpack(minecraft.player).isEmpty() || CuriosUtil.getFirstBackpackSlot(minecraft.player) > 0)) {
            Networking.sendToServer(new MessageOpenSatchel());
            minecraft.getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.ARMOR_EQUIP_LEATHER.value(), 0.75F, 1.0F));
        }
    }

    public static void checkEnderBagKey() {
        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.player != null & minecraft.screen == null && ClientSetupEventHandler.KEY_ENDER_BAG.consumeClick()
                && (!CuriosUtil.getEnderSatchel(minecraft.player).isEmpty() || CuriosUtil.getFirstEnderSatchelSlot(minecraft.player) > 0)) {
            Networking.sendToServer(new MessageOpenEnderSatchel());
            minecraft.getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.ENDER_CHEST_OPEN, 0.75F, 1.0F));
        }
    }

    public static void checkStorageRemoteKey() {
        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.player != null & minecraft.screen == null && ClientSetupEventHandler.KEY_STORAGE_REMOTE.consumeClick()
                && (!CuriosUtil.getStorageRemoteCurio(minecraft.player).isEmpty() || CuriosUtil.getFirstStorageRemoteSlot(minecraft.player) > 0)) {
            Networking.sendToServer(new MessageOpenStorageRemote());
            minecraft.getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.ARMOR_EQUIP_DIAMOND.value(), 0.75F, 1.0F));
        }
    }

    public static void checkFamiliarSettingsKeys() {
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
    @SubscribeEvent
    public static void onScreenKey(ScreenEvent.KeyPressed.Post event) {
        Screen screen = event.getScreen();
        if (screen instanceof ChatScreen)
            return;

        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.player == null)
            return;

        if (event.getKeyCode() == ClientSetupEventHandler.KEY_ENDER_BAG.getKey().getValue()
                && screen instanceof AbstractContainerScreen<?> containerScreen
                && containerScreen.getMenu() instanceof ChestMenu
                && screen.getTitle().getString().contains(Component.translatable("block.minecraft.ender_chest").getString())) {

            minecraft.player.closeContainer();
            minecraft.getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.ENDER_CHEST_CLOSE, 0.75F, 1.0F));
            event.setCanceled(true);
        }

        if (event.getKeyCode() == ClientSetupEventHandler.KEY_BACKPACK.getKey().getValue()
                && minecraft.screen instanceof SatchelScreen) {
            minecraft.player.closeContainer();
            event.setCanceled(true);
        }

        if (event.getKeyCode() == ClientSetupEventHandler.KEY_STORAGE_REMOTE.getKey().getValue()
                && (minecraft.screen instanceof StorageRemoteGui || minecraft.screen instanceof StorageControllerGui)) {
            minecraft.player.closeContainer();
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void onRecipesReceived(RecipesReceivedEvent event) {
        OccultismRecipeManagerClient.onRecipesReceived(event);
    }

    @SubscribeEvent
    public static void onClientLogout(ClientPlayerNetworkEvent.LoggingOut event) {
        OccultismRecipeManagerClient.onClientLogout(event);
    }
    //endregion Static Methods
}
