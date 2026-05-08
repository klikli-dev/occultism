/*
 * SPDX-FileCopyrightText: 2026 klikli-dev
 *
 * SPDX-License-Identifier: MIT
 */

package com.klikli_dev.occultism.client.gui;

import com.klikli_dev.occultism.Occultism;
import com.klikli_dev.occultism.common.blockentity.DimensionalMineshaftBlockEntity;
import com.klikli_dev.occultism.common.container.DimensionalMineshaftContainer;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class DimensionalMineshaftScreen extends AbstractDimensionalMachineScreen<DimensionalMineshaftContainer> {
    private static final int OUTPUT_SLOT_COUNT = 9;
    private static final int MACHINE_SLOT_COUNT = OUTPUT_SLOT_COUNT + 1;

    public final DimensionalMineshaftBlockEntity otherworldMiner;

    public DimensionalMineshaftScreen(DimensionalMineshaftContainer screenContainer, Inventory inv,
                                      Component titleIn) {
        super(screenContainer, inv, titleIn, 176, 166, DimensionalMineshaftLayouts.create());
        this.otherworldMiner = screenContainer.otherworldMiner;
    }

    @Override
    protected int machineSlotCount() {
        return MACHINE_SLOT_COUNT;
    }

    @Override
    protected String machineSlotNodePath(int slotIndex) {
        if (slotIndex < OUTPUT_SLOT_COUNT) {
            return "frame.machine.output.slot_" + slotIndex;
        }

        return slotIndex == OUTPUT_SLOT_COUNT ? "frame.machine.input" : null;
    }

    @Override
    protected void renderDynamicContents(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY, float partialTicks) {
        int miningTime = this.otherworldMiner.miningTime;
        int progress = this.otherworldMiner.maxMiningTime > 0
                ? (int) (18 * (1.0F - (float) miningTime / this.otherworldMiner.maxMiningTime))
                : 0;
        if (progress > 0 && miningTime > 0) {
            this.renderSpriteAtNode(guiGraphics, "frame.progress.fill",
                    OccultismGuiSprites.OTHERWORLD_MINER_PROGRESS_FILL, progress + 1, 4);
        }

        if (this.otherworldMiner.inputHandler.getStackInSlot(0).isEmpty()) {
            this.renderSpriteAtNode(guiGraphics, "frame.machine.input",
                    OccultismGuiSprites.OTHERWORLD_MINER_INPUT_SLOT_HINT);
        }
    }
}
