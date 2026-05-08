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

    public static final int SLOT_SIZE = 18;
    public static final int OUTPUT_SLOT_VISUAL_OFFSET = -5;
    public static final int CRAFTING_GRID_SLOT_VISUAL_OFFSET = -1;
    public static final int ORDER_SLOT_BACKGROUND_OFFSET = -5;
    public static final int ORDER_SLOT_BACKGROUND_SIZE = 28;
    public static final int ORDER_SLOT_OVERLAY_OFFSET = 2;

    public int playerInventoryX(int column) {
        return this.playerInventoryLeft + column * SLOT_SIZE;
    }

    public int playerInventoryY(int row) {
        return this.playerInventoryTop + row * SLOT_SIZE;
    }

    public int hotbarX(int column) {
        return this.hotbarLeft + column * SLOT_SIZE;
    }

    public int hotbarY() {
        return this.hotbarTop;
    }

    public int craftingGridX(int column) {
        return this.craftingGridLeft + column * SLOT_SIZE;
    }

    public int craftingGridY(int row) {
        return this.craftingGridTop + row * SLOT_SIZE;
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

    public int craftingArrowX() {
        return this.craftingOutputLeft - 35;
    }

    public int craftingArrowY() {
        return this.craftingOutputTop + 1;
    }

    public int clearRecipeButtonX() {
        return this.craftingGridLeft + 56;
    }

    public int clearRecipeButtonY() {
        return this.craftingGridTop - 1;
    }

    public int storageTypesLabelX() {
        return this.craftingOutputLeft + SLOT_SIZE / 2;
    }

    public int storageTypesLabelY() {
        return 47;
    }

    public int orderSlotBackgroundX() {
        return this.orderSlotLeft + ORDER_SLOT_BACKGROUND_OFFSET;
    }

    public int orderSlotBackgroundY() {
        return this.orderSlotTop + ORDER_SLOT_BACKGROUND_OFFSET;
    }
}
