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
import com.klikli_dev.occultism.api.common.data.MachineReference;
import com.klikli_dev.occultism.common.entity.job.ManageMachineJob;
import com.klikli_dev.occultism.common.item.spirit.BookOfCallingItem;
import com.klikli_dev.occultism.network.IMessage;
import com.klikli_dev.occultism.util.ItemNBTUtil;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;

public class MessageSetManagedMachine implements IMessage {
    public static final ResourceLocation ID = new ResourceLocation(Occultism.MODID, "set_managed_machine");
    public static final Type<MessageSetManagedMachine> TYPE = new Type<>(ID);
    public static final StreamCodec<RegistryFriendlyByteBuf, MessageSetManagedMachine> STREAM_CODEC = CustomPacketPayload.codec(MessageSetManagedMachine::encode, MessageSetManagedMachine::new);

    public MachineReference managedMachine;

    public MessageSetManagedMachine(RegistryFriendlyByteBuf buf) {
        this.decode(buf);
    }

    public MessageSetManagedMachine(MachineReference managedMachine) {
        this.managedMachine = managedMachine;
    }

    @Override
    public void onServerReceived(MinecraftServer minecraftServer, ServerPlayer player) {
        ItemStack stack = player.getItemInHand(InteractionHand.MAIN_HAND);
        if (stack.getItem() instanceof BookOfCallingItem) {
            ItemNBTUtil.getSpiritEntity(stack).ifPresent(spirit -> {
                spirit.getJob().filter(ManageMachineJob.class::isInstance).map(ManageMachineJob.class::cast)
                        .ifPresent(job -> {
                            if (job.getManagedMachine() != null) {
                                //we only set the "client trusted" properties
                                //to link a machine the book of calling serverside logic is required
                                job.getManagedMachine().customName = this.managedMachine.customName;
                                job.getManagedMachine().extractFacing = this.managedMachine.extractFacing;
                                job.getManagedMachine().insertFacing = this.managedMachine.insertFacing;
                                ItemNBTUtil.updateItemNBTFromEntity(stack, spirit);
                                player.inventoryMenu.broadcastChanges();
                            }
                        });
            });
        }
    }

    @Override
    public void encode(RegistryFriendlyByteBuf buf) {
        MachineReference.STREAM_CODEC.encode(buf, this.managedMachine);
    }

    @Override
    public void decode(RegistryFriendlyByteBuf buf) {
        this.managedMachine = MachineReference.STREAM_CODEC.decode(buf);
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
