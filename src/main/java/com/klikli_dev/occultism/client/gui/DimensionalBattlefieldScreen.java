/*
 * SPDX-FileCopyrightText: 2026 klikli-dev
 *
 * SPDX-License-Identifier: MIT
 */

package com.klikli_dev.occultism.client.gui;

import com.klikli_dev.occultism.Occultism;
import com.klikli_dev.occultism.common.blockentity.DimensionalBattlefieldBlockEntity;
import com.klikli_dev.occultism.common.container.DimensionalBattlefieldContainer;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.neoforged.neoforge.transfer.item.ItemStacksResourceHandler;

public class DimensionalBattlefieldScreen extends AbstractDimensionalMachineScreen<DimensionalBattlefieldContainer> {
    private static final int IMAGE_WIDTH = 176;
    private static final int IMAGE_HEIGHT = 202;
    private static final int OUTPUT_SLOT_COUNT = 25;
    private static final int MACHINE_SLOT_COUNT = OUTPUT_SLOT_COUNT + 3;

    public final DimensionalBattlefieldBlockEntity otherworldButcher;

    public DimensionalBattlefieldScreen(DimensionalBattlefieldContainer screenContainer, Inventory inv,
                                        Component titleIn) {
        super(screenContainer, inv, titleIn, IMAGE_WIDTH, IMAGE_HEIGHT, DimensionalBattlefieldLayouts.create());
        this.otherworldButcher = screenContainer.otherworldButcher;
    }

    private static boolean isEmpty(ItemStacksResourceHandler handler) {
        return handler.getResource(0).isEmpty();
    }

    protected int machineSlotCount() {
        return MACHINE_SLOT_COUNT;
    }

    @Override
    protected String machineSlotNodePath(int slotIndex) {
        if (slotIndex < OUTPUT_SLOT_COUNT) {
            return "frame.machine.output.slot_" + slotIndex;
        }

        return switch (slotIndex) {
            case OUTPUT_SLOT_COUNT -> "frame.machine.input_soul";
            case OUTPUT_SLOT_COUNT + 1 -> "frame.machine.input_fuel";
            case OUTPUT_SLOT_COUNT + 2 -> "frame.machine.input_weapon";
            default -> null;
        };
    }

    @Override
    protected void renderDynamicContents(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY, float partialTicks) {
        int mobHealth = this.otherworldButcher.mobHealth;
        int progress = this.otherworldButcher.maxMobLife > 0 ?
                (int) (34 * (1.0F - (float) mobHealth / this.otherworldButcher.maxMobLife)) : 0;
        if (progress > 0 && mobHealth > 0) {
            this.renderSpriteAtNode(guiGraphics, "frame.progress.fill",
                    OccultismGuiSprites.OTHERWORLD_BUTCHER_PROGRESS_FILL, progress + 1, 4);
        }

        if (isEmpty(this.otherworldButcher.inputSoulHandler)) {
            this.renderSpriteAtNode(guiGraphics, "frame.machine.input_soul",
                    OccultismGuiSprites.OTHERWORLD_BUTCHER_SOUL_SLOT_HINT);
        }
        if (isEmpty(this.otherworldButcher.inputWeaponHandler)) {
            this.renderSpriteAtNode(guiGraphics, "frame.machine.input_weapon",
                    OccultismGuiSprites.OTHERWORLD_BUTCHER_WEAPON_SLOT_HINT);
        }
        if (isEmpty(this.otherworldButcher.inputFuelHandler)) {
            this.renderSpriteAtNode(guiGraphics, "frame.machine.input_fuel",
                    OccultismGuiSprites.OTHERWORLD_BUTCHER_FUEL_SLOT_HINT);
        }
    }
}
