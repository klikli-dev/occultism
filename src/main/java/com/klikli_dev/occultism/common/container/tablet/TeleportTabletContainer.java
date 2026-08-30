package com.klikli_dev.occultism.common.container.tablet;

import com.klikli_dev.occultism.registry.OccultismContainers;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;

public class TeleportTabletContainer extends AbstractTabletContainer {
    public static final int TABLET_SIZE = 9;
    private static final int TABLET_LEFT = 11;
    private static final int TABLET_TOP = 23;
    private static final int TABLET_RADIUS = 18*4;
    private static final int PLAYER_INVENTORY_LEFT = 11;
    private static final int PLAYER_INVENTORY_TOP = 166;
    private static final int HOTBAR_LEFT = 11;
    private static final int HOTBAR_TOP = 224;

    public TeleportTabletContainer(int id, Inventory playerInventory, Container tabletInventory, int selectedSlot) {
        super(OccultismContainers.TELEPORT_TABLET.get(), id, playerInventory, tabletInventory, selectedSlot);
    }

    public static TeleportTabletContainer createClientContainer(int id, Inventory playerInventory, FriendlyByteBuf buffer) {
        final int selectedSlot = buffer.readVarInt();
        return new TeleportTabletContainer(id, playerInventory, new SimpleContainer(TABLET_SIZE), selectedSlot);
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
    protected void setupTabletSlots() {
        int centerX = 4 * 18;
        int centerY = 2 * 18;

        this.addSlot(new TeleportTabletSlot(this.tabletInventory, 0,
                TABLET_LEFT + centerX,
                TABLET_TOP + centerY));

        for (int slotIndex = 1; slotIndex < TABLET_SIZE; slotIndex++) {
            double angle = 2 * Math.PI * (slotIndex-1) / (TABLET_SIZE - 1) - Math.PI/2;
            int x = centerX + (int) Math.round(TABLET_RADIUS * Math.cos(angle));
            int y = centerY + (int) Math.round(TABLET_RADIUS * Math.sin(angle));

            this.addSlot(new TeleportTabletSlot(this.tabletInventory, slotIndex,
                    TABLET_LEFT + x,
                    TABLET_TOP + y));
        }
    }
}
