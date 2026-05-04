package com.klikli_dev.occultism.common.container.satchel;

import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;

import javax.annotation.Nullable;

public abstract class RitualSatchelContainer extends AbstractSatchelContainer {
    public static final int SATCHEL_SIZE = 4 * 9;
    private static final int SATCHEL_LEFT = 11;
    private static final int SATCHEL_TOP = 18;
    private static final int PLAYER_INVENTORY_LEFT = 11;
    private static final int PLAYER_INVENTORY_TOP = 106;
    private static final int HOTBAR_LEFT = 11;
    private static final int HOTBAR_TOP = 164;

    public RitualSatchelContainer(@Nullable MenuType<?> menuType, int id, Inventory playerInventory, Container satchelInventory, int selectedSlot) {
        super(menuType, id, playerInventory, satchelInventory, selectedSlot);
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
        int height = 4;
        int width = 9;

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                this.addSlot(new RitualSatchelSlot(this.satchelInventory, j + i * width, SATCHEL_LEFT + j * 18,
                        SATCHEL_TOP + i * 18));
            }
        }
    }
}
