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
import com.github.klikli_dev.occultism.common.entity.spirits.EntitySpirit;
import com.github.klikli_dev.occultism.common.item.ItemBookOfCallingActive;
import com.github.klikli_dev.occultism.common.jobs.SpiritJobManageMachine;
import com.github.klikli_dev.occultism.util.ItemNBTUtil;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Hand;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageSetManagedMachine extends MessageBase<MessageSetManagedMachine> {

    //region Fields
    public MachineReference managedMachine;
    //endregion Fields

    //region Initialization
    public MessageSetManagedMachine() {
    }

    public MessageSetManagedMachine(MachineReference managedMachine) {
        this.managedMachine = managedMachine;
    }
    //endregion Initialization

    //region Overrides
    @Override
    public void onClientReceived(Minecraft minecraft, MessageSetManagedMachine message, PlayerEntity player,
                                 MessageContext context) {

    }

    @Override
    public void onServerReceived(MinecraftServer minecraftServer, MessageSetManagedMachine message,
                                 ServerPlayerEntity player, MessageContext context) {
        ItemStack stack = player.getHeldItem(Hand.MAIN_HAND);
        if (stack.getItem() instanceof ItemBookOfCallingActive) {
            EntitySpirit spirit = ItemNBTUtil.getSpiritEntity(stack);
            if (spirit != null) {
                if (spirit.getJob() instanceof SpiritJobManageMachine) {
                    SpiritJobManageMachine job = (SpiritJobManageMachine) spirit.getJob();
                    if (job.getManagedMachine() != null) {
                        //we only set the "client trusted" properties
                        //to link a machine the book of calling serverside logic is required
                        job.getManagedMachine().customName = message.managedMachine.customName;
                        job.getManagedMachine().extractFacing = message.managedMachine.extractFacing;
                        job.getManagedMachine().insertFacing = message.managedMachine.insertFacing;
                        ItemNBTUtil.updateItemNBTFromEntity(stack, spirit);
                        player.inventoryContainer.detectAndSendChanges();
                    }
                }
            }
        }
    }

    @Override
    public void fromBytes(ByteBuf byteBuf) {
        this.managedMachine = MachineReference.fromBytes(byteBuf);
    }

    @Override
    public void toBytes(ByteBuf byteBuf) {
        this.managedMachine.toBytes(byteBuf);
    }
    //endregion Overrides
}
