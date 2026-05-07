/*
 * SPDX-FileCopyrightText: 2026 klikli-dev
 *
 * SPDX-License-Identifier: MIT
 */

package com.klikli_dev.occultism.client.gui.storage.component;

import com.klikli_dev.occultism.api.client.gui.IStorageControllerGuiContainer;
import com.klikli_dev.occultism.client.gui.controls.ItemSlotWidget;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.world.item.ItemStack;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

public class StorageItemGridWidget {
    private final IStorageControllerGuiContainer parent;
    private List<ItemSlotWidget> slots = List.of();

    public StorageItemGridWidget(IStorageControllerGuiContainer parent) {
        this.parent = parent;
    }

    public void rebuild(List<ItemStack> stacksToDisplay, int firstVisibleIndex, int rows, int columns,
                        BiFunction<Integer, Integer, Point> cellPositionResolver, int guiLeft, int guiTop) {
        List<ItemSlotWidget> rebuiltSlots = new ArrayList<>();
        int index = firstVisibleIndex;
        for (int row = 0; row < rows; row++) {
            if (index >= stacksToDisplay.size()) {
                break;
            }

            for (int col = 0; col < columns; col++) {
                if (index >= stacksToDisplay.size()) {
                    break;
                }

                ItemStack stack = stacksToDisplay.get(index);
                Point cellPosition = cellPositionResolver.apply(col, row);
                rebuiltSlots.add(new ItemSlotWidget(this.parent, stack, cellPosition.x, cellPosition.y, stack.getCount(),
                        guiLeft, guiTop, true));
                index++;
            }
        }

        this.slots = List.copyOf(rebuiltSlots);
    }

    public ItemStack drawAndGetHoveredStack(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY) {
        ItemStack hoveredStack = ItemStack.EMPTY;
        for (ItemSlotWidget slot : this.slots) {
            slot.drawSlot(guiGraphics, mouseX, mouseY);
            if (slot.isMouseOverSlot(mouseX, mouseY)) {
                hoveredStack = slot.getStack();
            }
        }

        return hoveredStack;
    }

    public void drawTooltips(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY) {
        for (ItemSlotWidget slot : this.slots) {
            if (slot.isMouseOverSlot(mouseX, mouseY)) {
                slot.drawTooltip(guiGraphics, mouseX, mouseY);
            }
        }
    }
}
