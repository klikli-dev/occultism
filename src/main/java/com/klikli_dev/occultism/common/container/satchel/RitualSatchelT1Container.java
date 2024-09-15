package com.klikli_dev.occultism.common.container.satchel;

import com.klikli_dev.occultism.registry.OccultismContainers;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;

public class RitualSatchelT1Container extends RitualSatchelContainer {
    public RitualSatchelT1Container(int id, Inventory playerInventory, Container satchelInventory, int selectedSlot) {
        super(OccultismContainers.RITUAL_SATCHEL_T1.get(), id, playerInventory, satchelInventory, selectedSlot);
    }

    public static RitualSatchelT1Container createClientContainer(int id, Inventory playerInventory, FriendlyByteBuf buffer) {
        final int selectedSlot = buffer.readVarInt();
        return new RitualSatchelT1Container(id, playerInventory, new SimpleContainer(RitualSatchelContainer.SATCHEL_SIZE), selectedSlot);
    }
}
