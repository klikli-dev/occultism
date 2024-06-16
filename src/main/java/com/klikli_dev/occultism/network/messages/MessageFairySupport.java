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

package com.klikli_dev.occultism.network.messages;

import com.klikli_dev.occultism.Occultism;
import com.klikli_dev.occultism.common.entity.familiar.FairyFamiliarEntity;
import com.klikli_dev.occultism.network.IMessage;
import net.minecraft.client.Minecraft;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3f;

public class MessageFairySupport implements IMessage {
    public static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "fairy_support");
    public static final Type<MessageFairySupport> TYPE = new Type<>(ID);
    public static final StreamCodec<RegistryFriendlyByteBuf, MessageFairySupport> STREAM_CODEC = CustomPacketPayload.codec(MessageFairySupport::encode, MessageFairySupport::new);
    private int fairyId;
    private int targetId;

    public MessageFairySupport(int fairyId, int targetId) {
        this.fairyId = fairyId;
        this.targetId = targetId;
    }

    public MessageFairySupport(RegistryFriendlyByteBuf buf) {
        this.decode(buf);
    }

    @Override
    public void encode(RegistryFriendlyByteBuf buf) {
        buf.writeInt(this.fairyId);
        buf.writeInt(this.targetId);
    }

    @Override
    public void decode(RegistryFriendlyByteBuf buf) {
        this.fairyId = buf.readInt();
        this.targetId = buf.readInt();
    }

    @Override
    public void onClientReceived(Minecraft minecraft, Player player) {
        Entity fairy = minecraft.level.getEntity(this.fairyId);
        if (fairy instanceof FairyFamiliarEntity)
            ((FairyFamiliarEntity) fairy).startSupportAnimation();
        Entity target = minecraft.level.getEntity(this.targetId);
        if (target != null) {
            for (int i = 0; i < 30; i++) {
                Vec3 pos = new Vec3(target.getRandomX(1), target.getRandomY(), target.getRandomZ(1));
                minecraft.level.addParticle(new DustParticleOptions(new Vector3f(0.9f, 0.9f, 0.5f), 1), pos.x, pos.y, pos.z, 0, 0,
                        0);
            }
        }
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
