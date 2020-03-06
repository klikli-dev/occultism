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

import com.github.klikli_dev.occultism.api.common.data.MachineReference;
import com.github.klikli_dev.occultism.common.item.spirit.BookOfCallingItem;
import com.github.klikli_dev.occultism.common.job.ManageMachineJob;
import com.github.klikli_dev.occultism.util.ItemNBTUtil;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Hand;
import net.minecraftforge.fml.network.NetworkEvent;

public class MessageSetManagedMachine extends MessageBase {

    //region Fields
    public MachineReference managedMachine;
    //endregion Fields

    //region Initialization
    public MessageSetManagedMachine(PacketBuffer buf) {
        this.decode(buf);
    }

    public MessageSetManagedMachine(MachineReference managedMachine) {
        this.managedMachine = managedMachine;
    }
    //endregion Initialization

    //region Overrides

    @Override
    public void onServerReceived(MinecraftServer minecraftServer, ServerPlayerEntity player,
                                 NetworkEvent.Context context) {
        ItemStack stack = player.getHeldItem(Hand.MAIN_HAND);
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
                                player.container.detectAndSendChanges();
                            }
                        });
            });
        }
    }

    @Override
    public void encode(PacketBuffer buf) {
        this.managedMachine.encode(buf);
    }

    @Override
    public void decode(PacketBuffer buf) {
        this.managedMachine = MachineReference.from(buf);
    }
    //endregion Overrides
}
