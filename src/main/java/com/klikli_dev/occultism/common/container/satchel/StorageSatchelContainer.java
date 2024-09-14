package com.klikli_dev.occultism.common.container.satchel;

import com.klikli_dev.occultism.registry.OccultismContainers;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;

public class StorageSatchelContainer extends AbstractSatchelContainer {
    public static final int SATCHEL_SIZE = 13 * 9;

    public StorageSatchelContainer(int id, Inventory playerInventory, Container satchelInventory, int selectedSlot) {
        super(OccultismContainers.SATCHEL.get(), id, playerInventory, satchelInventory, selectedSlot);
    }

    public static StorageSatchelContainer createClientContainer(int id, Inventory playerInventory, FriendlyByteBuf buffer) {
        final int selectedSlot = buffer.readVarInt();
        return new StorageSatchelContainer(id, playerInventory, new SimpleContainer(SATCHEL_SIZE), selectedSlot);
    }

    @Override
    protected void setupPlayerInventorySlots() {
        int playerInventoryTop = 174;
        int playerInventoryLeft = 44;
        int hotbarSlots = 9;

        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 9; j++)
                this.addSlot(new Slot(this.playerInventory, j + i * 9 + hotbarSlots, playerInventoryLeft + j * 18,
                        playerInventoryTop + i * 18));
    }

    @Override
    protected void setupPlayerHotbar() {
        int hotbarTop = 232;
        int hotbarLeft = 44;
        for (int i = 0; i < 9; i++) {
            this.addSlot(new Slot(this.playerInventory, i, hotbarLeft + i * 18, hotbarTop));
        }
    }

    @Override
    protected void setupSatchelSlots() {
        //8x 8y for satchel
        int height = 9;
        int width = 13;
        int x = 8;
        int y = 8;

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                this.addSlot(new StorageSatchelSlot(this.satchelInventory, j + i * width, x + j * 18, y + i * 18));
            }
        }
    }
}
