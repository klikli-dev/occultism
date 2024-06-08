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

package com.klikli_dev.occultism.network.messages;

import com.klikli_dev.occultism.Occultism;
import com.klikli_dev.occultism.common.item.tool.DivinationRodItem;
import com.klikli_dev.occultism.network.IMessage;
import com.klikli_dev.occultism.registry.OccultismDataComponents;
import net.minecraft.core.BlockPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;

public class MessageSetDivinationResult implements IMessage {

    public static final ResourceLocation ID = new ResourceLocation(Occultism.MODID, "set_divination_result");
    public static final Type<MessageSetDivinationResult> TYPE = new Type<>(ID);
    public static final StreamCodec<RegistryFriendlyByteBuf, MessageSetDivinationResult> STREAM_CODEC = CustomPacketPayload.codec(MessageSetDivinationResult::encode, MessageSetDivinationResult::new);
    public BlockPos pos;
    public byte distance;

    public MessageSetDivinationResult(RegistryFriendlyByteBuf buf) {
        this.decode(buf);
    }

    public MessageSetDivinationResult(BlockPos pos, float distance) {
        this.pos = pos;
        this.distance = (byte) Math.min(256, distance);
    }


    @Override
    public void onServerReceived(MinecraftServer minecraftServer, ServerPlayer player) {
        ItemStack stack = player.getItemInHand(InteractionHand.MAIN_HAND);
        if (stack.getItem() instanceof DivinationRodItem) {
            stack.set(OccultismDataComponents.DIVINATION_DISTANCE, (float) this.distance);
            if (this.pos != null) {
                stack.set(OccultismDataComponents.DIVINATION_POS, this.pos);
            }
            player.inventoryMenu.broadcastChanges();
        }
    }

    @Override
    public void encode(RegistryFriendlyByteBuf buf) {
        buf.writeBoolean(this.pos != null);
        if (this.pos != null) {
            buf.writeBlockPos(this.pos);
        }
        buf.writeByte(this.distance);
    }

    @Override
    public void decode(RegistryFriendlyByteBuf buf) {
        if (buf.readBoolean()) {
            this.pos = buf.readBlockPos();
        }
        this.distance = buf.readByte();
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
