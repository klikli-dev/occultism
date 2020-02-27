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

import com.github.klikli_dev.occultism.api.client.gui.IStorageControllerGui;
import com.github.klikli_dev.occultism.api.common.data.GlobalBlockPos;
import com.github.klikli_dev.occultism.api.common.data.MachineReference;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * This message sends the stacks in the currently opened storage controller.
 */
public class MessageUpdateLinkedMachines extends MessageBase<MessageUpdateLinkedMachines> {

    //region Fields
    private List<MachineReference> linkedMachines;
    //endregion Fields

    //region Initialization
    public MessageUpdateLinkedMachines() {

    }

    public MessageUpdateLinkedMachines(List<MachineReference> linkedMachines) {
        this.linkedMachines = linkedMachines;
    }

    public MessageUpdateLinkedMachines(Map<GlobalBlockPos, MachineReference> linkedMachines) {
        this.linkedMachines = new ArrayList<>(linkedMachines.values());
    }
    //endregion Initialization

    //region Overrides
    @Override
    public void onClientReceived(Minecraft minecraft, MessageUpdateLinkedMachines message, PlayerEntity player,
                                 MessageContext context) {
        IStorageControllerGui gui = (IStorageControllerGui) Minecraft.getMinecraft().currentScreen;
        if (gui != null) {
            gui.setLinkedMachines(message.linkedMachines);
        }

    }

    @Override
    public void onServerReceived(MinecraftServer minecraftServer, MessageUpdateLinkedMachines message,
                                 ServerPlayerEntity player, MessageContext context) {

    }


    @Override
    public void fromBytes(ByteBuf byteBuf) {
        int linkedMachinesSize = byteBuf.readInt();
        this.linkedMachines = new ArrayList<>(linkedMachinesSize);

        for (int i = 0; i < linkedMachinesSize; i++) {
            MachineReference machineReference = MachineReference.fromBytes(byteBuf);
            this.linkedMachines.add(machineReference);
        }
    }

    @Override
    public void toBytes(ByteBuf byteBuf) {
        byteBuf.writeInt(this.linkedMachines.size());
        for (MachineReference machineReference : this.linkedMachines) {
            machineReference.toBytes(byteBuf);
        }
    }
    //endregion Overrides
}
