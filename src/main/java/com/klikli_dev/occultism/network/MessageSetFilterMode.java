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

package com.klikli_dev.occultism.network;

import com.klikli_dev.occultism.common.entity.spirit.SpiritEntity;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.network.NetworkEvent;

public class MessageSetFilterMode extends MessageBase {

    //region Fields
    public boolean isBlacklistFilter;
    public int entityId;
    //endregion Fields

    //region Initialization
    public MessageSetFilterMode(FriendlyByteBuf buf) {
        this.decode(buf);
    }

    public MessageSetFilterMode(boolean isBlacklistFilter, int entityId) {
        this.isBlacklistFilter = isBlacklistFilter;
        this.entityId = entityId;
    }
    //endregion Initialization

    //region Overrides

    @Override
    public void onServerReceived(MinecraftServer minecraftServer, ServerPlayer player,
                                 NetworkEvent.Context context) {

        Entity e = player.level.getEntity(this.entityId);
        if (e instanceof SpiritEntity spirit) {
            spirit.setFilterBlacklist(this.isBlacklistFilter);
        }
    }

    @Override
    public void encode(FriendlyByteBuf buf) {
        buf.writeBoolean(this.isBlacklistFilter);
        buf.writeInt(this.entityId);
    }

    @Override
    public void decode(FriendlyByteBuf buf) {
        this.isBlacklistFilter = buf.readBoolean();
        this.entityId = buf.readInt();
    }
    //endregion Overrides
}
