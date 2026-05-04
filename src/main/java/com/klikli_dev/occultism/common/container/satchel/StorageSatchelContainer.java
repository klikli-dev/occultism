package com.klikli_dev.occultism.common.container.satchel;

import com.klikli_dev.occultism.registry.OccultismContainers;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;

public class StorageSatchelContainer extends AbstractSatchelContainer {
    public static final int SATCHEL_SIZE = 13 * 9;
    private static final int SATCHEL_ROW_LENGTH = 17;
    private static final int SATCHEL_FULL_ROWS = 6;
    private static final int SATCHEL_LAST_ROW_OFFSET = 1;
    private static final int SATCHEL_LEFT = 8;
    private static final int SATCHEL_TOP = 18;
    private static final int PLAYER_INVENTORY_LEFT = 77;
    private static final int PLAYER_INVENTORY_TOP = 161;
    private static final int HOTBAR_LEFT = 77;
    private static final int HOTBAR_TOP = 219;

    public StorageSatchelContainer(int id, Inventory playerInventory, Container satchelInventory, int selectedSlot) {
        super(OccultismContainers.SATCHEL.get(), id, playerInventory, satchelInventory, selectedSlot);
    }

    public static StorageSatchelContainer createClientContainer(int id, Inventory playerInventory, FriendlyByteBuf buffer) {
        final int selectedSlot = buffer.readVarInt();
        return new StorageSatchelContainer(id, playerInventory, new SimpleContainer(SATCHEL_SIZE), selectedSlot);
    }

    @Override
    protected void setupPlayerInventorySlots() {
        int hotbarSlots = 9;

        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 9; j++)
                this.addSlot(new Slot(this.playerInventory, j + i * 9 + hotbarSlots, PLAYER_INVENTORY_LEFT + j * 18,
                        PLAYER_INVENTORY_TOP + i * 18));
    }

    @Override
    protected void setupPlayerHotbar() {
        for (int i = 0; i < 9; i++) {
            this.addSlot(new Slot(this.playerInventory, i, HOTBAR_LEFT + i * 18, HOTBAR_TOP));
        }
    }

    @Override
    protected void setupSatchelSlots() {
        for (int slotIndex = 0; slotIndex < SATCHEL_SIZE; slotIndex++) {
            int row;
            int column;
            if (slotIndex < SATCHEL_ROW_LENGTH * SATCHEL_FULL_ROWS) {
                row = slotIndex / SATCHEL_ROW_LENGTH;
                column = slotIndex % SATCHEL_ROW_LENGTH;
            } else {
                row = SATCHEL_FULL_ROWS;
                column = slotIndex - SATCHEL_ROW_LENGTH * SATCHEL_FULL_ROWS + SATCHEL_LAST_ROW_OFFSET;
            }

            this.addSlot(new StorageSatchelSlot(this.satchelInventory, slotIndex,
                    SATCHEL_LEFT + column * 18,
                    SATCHEL_TOP + row * 18));
        }
    }
}
