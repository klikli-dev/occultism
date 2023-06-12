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

import com.klikli_dev.occultism.registry.OccultismCapabilities;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.NetworkEvent;

public class MessageSetJumps extends MessageBase {

    public int jumps;

    public MessageSetJumps(FriendlyByteBuf buf) {
        this.decode(buf);
    }

    public MessageSetJumps(int jumps) {
        this.jumps = jumps;
    }

    @Override
    public void onClientReceived(Minecraft minecraft, Player player, NetworkEvent.Context context) {
        if (!player.onGround()) {
            player.getCapability(OccultismCapabilities.DOUBLE_JUMP).ifPresent(cap -> cap.setJumps(this.jumps));
        }
    }

    @Override
    public void encode(FriendlyByteBuf buf) {
        buf.writeInt(this.jumps);
    }

    @Override
    public void decode(FriendlyByteBuf buf) {
        this.jumps = buf.readInt();
    }

}
