/*
 * MIT License
 *
 * Copyright 2021 vemerion
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

package com.github.klikli_dev.occultism.common.ritual.pentacle;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.github.klikli_dev.occultism.Occultism;
import com.github.klikli_dev.occultism.network.MessageUpdatePentacles;
import com.github.klikli_dev.occultism.network.OccultismPackets;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;

import net.minecraft.client.resources.JsonReloadListener;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.profiler.IProfiler;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.network.PacketDistributor;
import net.minecraftforge.fml.server.ServerLifecycleHooks;

@EventBusSubscriber(modid = Occultism.MODID, bus = Bus.FORGE)
public class PentacleManager extends JsonReloadListener {
    private static final Gson GSON = (new GsonBuilder()).setPrettyPrinting().disableHtmlEscaping().create();
    public static final String FOLDER_NAME = Occultism.MODID + "-pentacles";

    private Map<ResourceLocation, Pentacle> pentacles;
    private static PentacleManager instance;

    public PentacleManager() {
        super(GSON, FOLDER_NAME);
        pentacles = new HashMap<>();
    }

    public static PentacleManager getInstance() {
        if (instance == null)
            instance = new PentacleManager();
        return instance;
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> objectIn, IResourceManager resourceManagerIn,
            IProfiler profilerIn) {
        for (Entry<ResourceLocation, JsonElement> entry : objectIn.entrySet()) {
            ResourceLocation key = entry.getKey();
            Pentacle pentacle = Pentacle.fromJson(key, JSONUtils.getJsonObject(entry.getValue(), "top element"));
            pentacles.put(key, pentacle);
        }

        if (ServerLifecycleHooks.getCurrentServer() != null)
            sendPentacleMessage();
    }

    private void sendPentacleMessage() {
        OccultismPackets.INSTANCE.send(PacketDistributor.ALL.noArg(), new MessageUpdatePentacles(pentacles));
    }
    
    private void sendPentacleMessage(ServerPlayerEntity player) {
        OccultismPackets.INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), new MessageUpdatePentacles(pentacles));
    }

    public static Pentacle get(ResourceLocation id) {
        return getInstance().pentacles.get(id);
    }

    public static Pentacle get(String modid, String path) {
        return getInstance().pentacles.get(new ResourceLocation(modid, path));
    }

    @SubscribeEvent
    public static void addPentacleReloadListener(AddReloadListenerEvent event) {
        event.addListener(getInstance());
    }
    
    @SubscribeEvent
    public static void syncPentacles(PlayerLoggedInEvent event) {
        getInstance().sendPentacleMessage((ServerPlayerEntity) event.getPlayer());
    }
}
