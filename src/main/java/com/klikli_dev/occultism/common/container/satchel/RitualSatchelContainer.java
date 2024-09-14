package com.klikli_dev.occultism.common.container.satchel;

import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;

import javax.annotation.Nullable;

public abstract class RitualSatchelContainer extends AbstractSatchelContainer {
    public static final int SATCHEL_SIZE = 4 * 9;

    public RitualSatchelContainer(@Nullable MenuType<?> menuType, int id, Inventory playerInventory, Container satchelInventory, int selectedSlot) {
        super(menuType, id, playerInventory, satchelInventory, selectedSlot);
    }

    @Override
    protected void setupPlayerInventorySlots() {
        int playerInventoryTop = 84;
        int playerInventoryLeft = 8;
        int hotbarSlots = 9;

        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 9; j++)
                this.addSlot(new Slot(this.playerInventory, j + i * 9 + hotbarSlots, playerInventoryLeft + j * 18,
                        playerInventoryTop + i * 18));
    }

    @Override
    protected void setupPlayerHotbar() {
        int hotbarTop = 142;
        int hotbarLeft = 8;
        for (int i = 0; i < 9; i++) {
            this.addSlot(new Slot(this.playerInventory, i, hotbarLeft + i * 18, hotbarTop));
        }
    }

    @Override
    protected void setupSatchelSlots() {
        //8x 8y for satchel
        int height = 4;
        int width = 9;
        int x = 8;
        int y = 8;

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                this.addSlot(new RitualSatchelSlot(this.satchelInventory, j + i * width, x + j * 18, y + i * 18));
            }
        }
    }
}
