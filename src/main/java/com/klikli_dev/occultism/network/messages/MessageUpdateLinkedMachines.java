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
import com.klikli_dev.occultism.api.client.gui.IStorageControllerGui;
import com.klikli_dev.occultism.api.common.data.GlobalBlockPos;
import com.klikli_dev.occultism.api.common.data.MachineReference;
import com.klikli_dev.occultism.network.IMessage;
import net.minecraft.client.Minecraft;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * This message sends the stacks in the currently opened storage controller.
 */
public class MessageUpdateLinkedMachines implements IMessage {

    public static final ResourceLocation ID = new ResourceLocation(Occultism.MODID, "update_linked_machines");
    public static final Type<MessageUpdateLinkedMachines> TYPE = new Type<>(ID);
    public static final StreamCodec<RegistryFriendlyByteBuf, MessageUpdateLinkedMachines> STREAM_CODEC = CustomPacketPayload.codec(MessageUpdateLinkedMachines::encode, MessageUpdateLinkedMachines::new);

    private List<MachineReference> linkedMachines;

    public MessageUpdateLinkedMachines(RegistryFriendlyByteBuf buf) {
        this.decode(buf);
    }

    public MessageUpdateLinkedMachines(List<MachineReference> linkedMachines) {
        this.linkedMachines = linkedMachines;
    }

    public MessageUpdateLinkedMachines(Map<GlobalBlockPos, MachineReference> linkedMachines) {
        this.linkedMachines = new ArrayList<>(linkedMachines.values());
    }

    @Override
    public void onClientReceived(Minecraft minecraft, Player player) {
        if (minecraft.screen instanceof IStorageControllerGui gui) {
            if (gui != null) {
                gui.setLinkedMachines(this.linkedMachines);
            }
        }
    }

    @Override
    public void encode(RegistryFriendlyByteBuf buf) {
        buf.writeInt(this.linkedMachines.size());
        for (MachineReference machineReference : this.linkedMachines) {
            MachineReference.STREAM_CODEC.encode(buf, machineReference);
        }
    }

    @Override
    public void decode(RegistryFriendlyByteBuf buf) {
        int linkedMachinesSize = buf.readInt();
        this.linkedMachines = new ArrayList<>(linkedMachinesSize);

        for (int i = 0; i < linkedMachinesSize; i++) {
            MachineReference machineReference = MachineReference.STREAM_CODEC.decode(buf);
            this.linkedMachines.add(machineReference);
        }
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
