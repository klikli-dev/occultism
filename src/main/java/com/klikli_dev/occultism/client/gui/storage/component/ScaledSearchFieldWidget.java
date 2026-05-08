/*
 * SPDX-FileCopyrightText: 2026 klikli-dev
 *
 * SPDX-License-Identifier: MIT
 */

package com.klikli_dev.occultism.client.gui.storage.component;

import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.client.input.MouseButtonInfo;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;

public class ScaledSearchFieldWidget extends EditBox {
    private static final int CURSOR_HEIGHT = 10;
    private static final float TEXT_OFFSET_Y = 2.0F;

    private final float renderScale;
    private final int baseTextHeight;

    public ScaledSearchFieldWidget(Font font, int x, int y, int width, int height, Component message, float renderScale) {
        super(font, x, y, width, height, message);
        this.renderScale = renderScale;
        this.baseTextHeight = Math.max(9, height);
        this.setHeight(Math.max(1, Math.round(this.baseTextHeight * this.renderScale)));
    }

    @Override
    public void extractWidgetRenderState(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY, float partialTick) {
        guiGraphics.pose().pushMatrix();
        guiGraphics.pose().translate(this.getX(), this.getY());
        guiGraphics.pose().scale(this.renderScale, this.renderScale);
        guiGraphics.pose().translate(-this.getX(), -this.getY());
        guiGraphics.pose().translate(0.0F, TEXT_OFFSET_Y);
        super.extractWidgetRenderState(guiGraphics, this.scaleMouseX(mouseX), this.scaleMouseY(mouseY), partialTick);
        guiGraphics.pose().popMatrix();
    }

    @Override
    public boolean mouseClicked(MouseButtonEvent event, boolean doubleClick) {
        return super.mouseClicked(this.scaleMouseEvent(event), doubleClick);
    }

    @Override
    public boolean mouseDragged(MouseButtonEvent event, double dx, double dy) {
        return super.mouseDragged(this.scaleMouseEvent(event), dx / this.renderScale, dy / this.renderScale);
    }

    @Override
    public boolean isMouseOver(double mouseX, double mouseY) {
        return this.isActive() && mouseX >= this.getX() && mouseX < this.getX() + this.getWidth()
                && mouseY >= this.getY() && mouseY < this.getY() + this.getHeight();
    }

    @Override
    public int getInnerWidth() {
        return Math.max(1, Math.round(super.getInnerWidth() / this.renderScale));
    }

    private MouseButtonEvent scaleMouseEvent(MouseButtonEvent event) {
        return new MouseButtonEvent(this.scaleMouseX(event.x()), this.scaleMouseY(event.y()),
                new MouseButtonInfo(event.button(), event.modifiers()));
    }

    private int scaleMouseX(double mouseX) {
        return Mth.floor(this.getX() + (mouseX - this.getX()) / this.renderScale);
    }

    private int scaleMouseY(double mouseY) {
        double scaledHeight = this.baseTextHeight * this.renderScale;
        double centeredOffset = (scaledHeight - CURSOR_HEIGHT) / 2.0D;
        return Mth.floor(this.getY() + (mouseY - this.getY() - centeredOffset) / this.renderScale
                + (this.baseTextHeight - CURSOR_HEIGHT) / 2.0D);
    }
}
