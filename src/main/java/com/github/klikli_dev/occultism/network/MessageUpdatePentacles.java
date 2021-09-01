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

package com.github.klikli_dev.occultism.network;

import com.github.klikli_dev.occultism.common.ritual.pentacle.Pentacle;

import com.github.klikli_dev.occultism.common.ritual.pentacle.PentacleManager;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.fml.DistExecutor.SafeRunnable;
import net.minecraftforge.fmllegacy.network.NetworkEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class MessageUpdatePentacles extends MessageBase {

    private Map<ResourceLocation, Pentacle> pentacles;

    public MessageUpdatePentacles(FriendlyByteBuf buf) {
        this.decode(buf);
    }

    public MessageUpdatePentacles(Map<ResourceLocation, Pentacle> pentacles) {
        this.pentacles = pentacles;
    }

    @Override
    public void encode(FriendlyByteBuf buffer) {
        buffer.writeInt(this.pentacles.size());
        for (Entry<ResourceLocation, Pentacle> entry : pentacles.entrySet()) {
            Pentacle pentacle = entry.getValue();
            buffer.writeResourceLocation(entry.getKey());
            pentacle.toNetwork(buffer);
        }
    }

    @Override
    public void decode(final FriendlyByteBuf buffer) {
        this.pentacles = new HashMap<>();
        int size = buffer.readInt();
        for (int i = 0; i < size; i++) {
            ResourceLocation key = buffer.readResourceLocation();
            Pentacle pentacle = Pentacle.fromNetwork(key, buffer);
            this.pentacles.put(key, pentacle);
        }
    }

    @Override
    public void onClientReceived(Minecraft minecraft, Player player, NetworkEvent.Context context) {
        UpdatePentacles.update(this.pentacles).run();
    }

    private static class UpdatePentacles {
        private static SafeRunnable update(Map<ResourceLocation, Pentacle> pentacles) {
            return new SafeRunnable() {
                private static final long serialVersionUID = 1L;

                @Override
                public void run() {
                    PentacleManager.getInstance().setPentacles(pentacles);
                    //TODO: Patchouli
                    //PatchouliAPI.get().reloadBookContents();
                }
            };
        }
    }
}