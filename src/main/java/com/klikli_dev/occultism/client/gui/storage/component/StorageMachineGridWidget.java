/*
 * SPDX-FileCopyrightText: 2026 klikli-dev
 *
 * SPDX-License-Identifier: MIT
 */

package com.klikli_dev.occultism.client.gui.storage.component;

import com.klikli_dev.occultism.api.client.gui.IStorageControllerGuiContainer;
import com.klikli_dev.occultism.api.common.data.MachineReference;
import com.klikli_dev.occultism.client.gui.controls.MachineSlotWidget;
import net.minecraft.client.gui.GuiGraphicsExtractor;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

public class StorageMachineGridWidget {
    private final IStorageControllerGuiContainer parent;
    private List<MachineSlotWidget> slots = List.of();

    public StorageMachineGridWidget(IStorageControllerGuiContainer parent) {
        this.parent = parent;
    }

    public void rebuild(List<MachineReference> machinesToDisplay, int firstVisibleIndex, int rows, int columns,
                        BiFunction<Integer, Integer, Point> cellPositionResolver, int guiLeft, int guiTop) {
        List<MachineSlotWidget> rebuiltSlots = new ArrayList<>();
        int index = firstVisibleIndex;
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < columns; col++) {
                if (index >= machinesToDisplay.size()) {
                    break;
                }

                Point cellPosition = cellPositionResolver.apply(col, row);
                rebuiltSlots.add(new MachineSlotWidget(this.parent, machinesToDisplay.get(index), cellPosition.x,
                        cellPosition.y, guiLeft, guiTop));
                index++;
            }
        }

        this.slots = List.copyOf(rebuiltSlots);
    }

    public void draw(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY) {
        for (MachineSlotWidget slot : this.slots) {
            slot.drawSlot(guiGraphics, mouseX, mouseY);
        }
    }

    public void drawTooltips(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY) {
        for (MachineSlotWidget slot : this.slots) {
            if (slot.isMouseOverSlot(mouseX, mouseY)) {
                slot.drawTooltip(guiGraphics, mouseX, mouseY);
            }
        }
    }

    public MachineReference hoveredMachine(double mouseX, double mouseY) {
        for (MachineSlotWidget slot : this.slots) {
            if (slot.isMouseOverSlot(mouseX, mouseY)) {
                return slot.getMachine();
            }
        }

        return null;
    }
}
