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

import com.github.klikli_dev.occultism.common.entity.BeholderFamiliarEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.NetworkEvent.Context;

import java.util.ArrayList;
import java.util.List;

public class MessageBeholderAttack extends MessageBase {

    private int beholderId;
    private List<Integer> targetIds;

    public MessageBeholderAttack(int beholderId, List<Integer> targetIds) {
        this.beholderId = beholderId;
        this.targetIds = targetIds;
    }

    public MessageBeholderAttack(FriendlyByteBuf buf) {
        this.decode(buf);
    }

    @Override
    public void encode(FriendlyByteBuf buf) {
        buf.writeInt(this.beholderId);
        buf.writeInt(this.targetIds.size());
        for (int id : this.targetIds)
            buf.writeInt(id);
    }

    @Override
    public void decode(FriendlyByteBuf buf) {
        this.beholderId = buf.readInt();
        this.targetIds = new ArrayList<>();
        int length = buf.readInt();
        for (int i = 0; i < length; i++)
            this.targetIds.add(buf.readInt());
    }

    @Override
    public void onClientReceived(Minecraft minecraft, Player player, Context context) {
        Entity entity = minecraft.level.getEntity(this.beholderId);
        if (entity instanceof BeholderFamiliarEntity beholder)
            beholder.shootRay(this.targetIds);
    }

}
