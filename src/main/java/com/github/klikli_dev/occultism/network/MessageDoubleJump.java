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

package com.github.klikli_dev.occultism.network;

import com.github.klikli_dev.occultism.util.MovementUtil;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.fml.network.NetworkEvent;

public class MessageDoubleJump extends MessageBase {

    //region Initialization
    public MessageDoubleJump() {

    }

    public MessageDoubleJump(PacketBuffer buf) {
        this.decode(buf);
    }
    //endregion Initialization


    //region Overrides
    @Override
    public void onServerReceived(MinecraftServer minecraftServer, ServerPlayerEntity player,
                                 NetworkEvent.Context context) {
        if (MovementUtil.doubleJump(player)) {
            //Show cloud on jump.
            player.getLevel()
                    .sendParticles(ParticleTypes.CLOUD, player.position().x, player.position().y,
                            player.position().z, 5, 0, 0, 0, 0.01F);
        }
    }

    @Override
    public void encode(PacketBuffer buf) {

    }

    @Override
    public void decode(PacketBuffer buf) {

    }
    //endregion Overrides
}
