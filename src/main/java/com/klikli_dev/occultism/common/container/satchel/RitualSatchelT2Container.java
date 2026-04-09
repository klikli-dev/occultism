package com.klikli_dev.occultism.common.container.satchel;

import com.klikli_dev.occultism.registry.OccultismContainers;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;

import java.util.UUID;

public class RitualSatchelT2Container extends RitualSatchelContainer {
    public RitualSatchelT2Container(int id, Inventory playerInventory, Container satchelInventory, int selectedSlot, UUID satchelUUID) {
        super(OccultismContainers.RITUAL_SATCHEL_T2.get(), id, playerInventory, satchelInventory, selectedSlot, satchelUUID);
    }

    public static RitualSatchelT2Container createClientContainer(int id, Inventory playerInventory, FriendlyByteBuf buffer) {
        final int selectedSlot = buffer.readVarInt();
        final UUID satchelUUID = buffer.readUUID();
        return new RitualSatchelT2Container(id, playerInventory, new SimpleContainer(RitualSatchelContainer.SATCHEL_SIZE), selectedSlot, satchelUUID);
    }
}
