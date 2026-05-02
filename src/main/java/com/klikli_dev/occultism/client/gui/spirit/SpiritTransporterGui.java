/*
 * SPDX-FileCopyrightText: 2026 klikli-dev
 *
 * SPDX-License-Identifier: MIT
 */

package com.klikli_dev.occultism.client.gui.spirit;

import com.klikli_dev.codedefinedgui.gui.core.GuiHost;
import com.klikli_dev.codedefinedgui.gui.core.GuiRootWidget;
import com.klikli_dev.codedefinedgui.gui.texture.GuiSprites;
import com.klikli_dev.codedefinedgui.gui.widget.FrameWidget;
import com.klikli_dev.codedefinedgui.gui.widget.GuiBackgroundWidget;
import com.klikli_dev.codedefinedgui.gui.widget.GuiSpriteWidget;
import com.klikli_dev.codedefinedgui.gui.widget.HorizontalSeparatorWidget;
import com.klikli_dev.codedefinedgui.gui.widget.VerticalSeparatorWidget;
import com.klikli_dev.occultism.Occultism;
import com.klikli_dev.occultism.common.container.spirit.SpiritTransporterContainer;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SpiritTransporterGui extends SpiritGui<SpiritTransporterContainer> implements GuiHost {
    protected static final int ENTITY_INVENTORY_SLOT_INDEX = 36;
    protected static final int INVENTORY_SLOT_LEFT = 152;
    protected static final int INVENTORY_SLOT_TOP = 54;
    protected static final int INVENTORY_SLOT_SIZE = 18;
    protected static final int FILTER_SLOT_LEFT = 152;
    protected static final int FILTER_SLOT_TOP = 84;
    protected static final String TRANSLATION_KEY_BASE = "gui." + Occultism.MODID + ".spirit.transporter";

    protected final GuiRootWidget root;
    protected final List<Component> tooltip = new ArrayList<>();

    public SpiritTransporterGui(SpiritTransporterContainer container,
                                Inventory playerInventory,
                                Component titleIn) {
        super(container, playerInventory, titleIn, 176, 220);
        this.root = new GuiRootWidget(this);
    }

    @Override
    public void init() {
        super.init();

        this.addRenderableWidget(this.root);
        this.root.clearChildren();
        this.root.addChild(new GuiBackgroundWidget(this));
        this.root.addChild(new FrameWidget(this.guiX(7), this.guiY(7), this.imageWidth - 14, 96, 0xFF101010));
        this.root.addChild(new FrameWidget(this.guiX(7), this.guiY(131), this.imageWidth - 14, 76, 0xFF101010));
        this.root.addChild(new VerticalSeparatorWidget(this.guiX(61), this.guiY(8), 94, 0xFF101010));
        this.root.addChild(new HorizontalSeparatorWidget(this.guiX(8), this.guiY(102), this.imageWidth - 16, 0xFF101010));
        this.root.addChild(new FrameWidget(this.guiX(143), this.guiY(44), 26, 58, 0xFF101010));

        for (Slot slot : this.menu.slots) {
            this.root.addChild(new GuiSpriteWidget(this.guiX(slot.x - 1), this.guiY(slot.y - 1), GuiSprites.INVENTORY_SLOT));
        }

        this.root.addChild(new FrameWidget(this.guiX(FILTER_SLOT_LEFT - 2), this.guiY(FILTER_SLOT_TOP - 2), 22, 22, 0xFF592424));
        this.root.syncWithHost();
    }

    @Override
    protected void extractBackground(GuiGraphicsExtractor guiGraphics) {
    }

    @Override
    public <T extends AbstractWidget> T addGuiWidget(T widget) {
        return this.addRenderableWidget(widget);
    }

    @Override
    public void removeGuiWidget(AbstractWidget widget) {
        this.removeWidget(widget);
    }

    @Override
    public int leftPos() {
        return this.leftPos;
    }

    @Override
    public int topPos() {
        return this.topPos;
    }

    @Override
    public int width() {
        return this.width;
    }

    @Override
    public int height() {
        return this.height;
    }

    @Override
    public int imageWidth() {
        return this.imageWidth;
    }

    @Override
    public int imageHeight() {
        return this.imageHeight;
    }

    @Override
    public void extractRenderState(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY, float partialTicks) {
        super.extractRenderState(guiGraphics, mouseX, mouseY, partialTicks);
        this.renderFg(guiGraphics, mouseX, mouseY);
    }

    protected void renderFg(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY) {
        this.tooltip.clear();

        if (!this.spirit.isInventorySlotActive()) {
            guiGraphics.fillGradient(this.leftPos + INVENTORY_SLOT_LEFT, this.topPos + INVENTORY_SLOT_TOP,
                    this.leftPos + INVENTORY_SLOT_LEFT + INVENTORY_SLOT_SIZE - 2,
                    this.topPos + INVENTORY_SLOT_TOP + INVENTORY_SLOT_SIZE - 2, 0xAA555555, 0xAA555555);
        }

        if (this.isPointInFilterSlot(mouseX, mouseY)) {
            this.tooltip.add(Component.translatable(TRANSLATION_KEY_BASE + ".filter_item"));
            if (!this.menu.getSlot(this.menu.getFilterSlotIndex()).hasItem()) {
                this.tooltip.add(Component.translatable(TRANSLATION_KEY_BASE + ".filter_item.empty")
                        .withStyle(ChatFormatting.GRAY));
            }
        }

        if (this.isPointInInventorySlot(mouseX, mouseY)) {
            if (!this.spirit.isInventorySlotActive()) {
                this.tooltip.add(Component.translatable(TRANSLATION_KEY_BASE + ".inventory_slot.disabled")
                        .withStyle(ChatFormatting.GRAY));
            } else if (!this.container.getSlot(ENTITY_INVENTORY_SLOT_INDEX).hasItem()) {
                this.tooltip.add(Component.translatable(TRANSLATION_KEY_BASE + ".inventory_slot.block_only")
                        .withStyle(ChatFormatting.GRAY));
            }
        }

        if (!this.tooltip.isEmpty()) {
            guiGraphics.setTooltipForNextFrame(this.font, this.tooltip, Optional.empty(), mouseX, mouseY);
        }
    }

    protected boolean isPointInInventorySlot(double mouseX, double mouseY) {
        return this.isHovering(INVENTORY_SLOT_LEFT, INVENTORY_SLOT_TOP, INVENTORY_SLOT_SIZE, INVENTORY_SLOT_SIZE,
                mouseX, mouseY);
    }

    protected boolean isPointInFilterSlot(double mouseX, double mouseY) {
        return this.isHovering(FILTER_SLOT_LEFT, FILTER_SLOT_TOP, INVENTORY_SLOT_SIZE, INVENTORY_SLOT_SIZE,
                mouseX, mouseY);
    }
}
