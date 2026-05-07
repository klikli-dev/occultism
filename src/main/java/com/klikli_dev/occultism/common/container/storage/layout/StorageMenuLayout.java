/*
 * SPDX-FileCopyrightText: 2026 klikli-dev
 *
 * SPDX-License-Identifier: MIT
 */

package com.klikli_dev.occultism.common.container.storage.layout;

public record StorageMenuLayout(
        int playerInventoryLeft,
        int playerInventoryTop,
        int hotbarLeft,
        int hotbarTop,
        int craftingGridLeft,
        int craftingGridTop,
        int craftingOutputLeft,
        int craftingOutputTop,
        int orderSlotLeft,
        int orderSlotTop) {

    public int playerInventoryX(int column) {
        return this.playerInventoryLeft + column * 18;
    }

    public int playerInventoryY(int row) {
        return this.playerInventoryTop + row * 18;
    }

    public int hotbarX(int column) {
        return this.hotbarLeft + column * 18;
    }

    public int hotbarY() {
        return this.hotbarTop;
    }

    public int craftingGridX(int column) {
        return this.craftingGridLeft + column * 18;
    }

    public int craftingGridY(int row) {
        return this.craftingGridTop + row * 18;
    }

    public int craftingOutputX() {
        return this.craftingOutputLeft;
    }

    public int craftingOutputY() {
        return this.craftingOutputTop;
    }

    public int orderSlotX() {
        return this.orderSlotLeft;
    }

    public int orderSlotY() {
        return this.orderSlotTop;
    }
}
