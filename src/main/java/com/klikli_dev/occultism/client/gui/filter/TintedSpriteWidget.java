// SPDX-FileCopyrightText: 2026 klikli-dev
//
// SPDX-License-Identifier: MIT

package com.klikli_dev.occultism.client.gui.filter;

import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;

class TintedSpriteWidget extends AbstractWidget {
    private final Identifier sprite;
    private final int tint;

    public TintedSpriteWidget(int x, int y, int width, int height, Identifier sprite, int tint) {
        super(x, y, width, height, Component.empty());
        this.sprite = sprite;
        this.tint = tint;
        this.active = false;
    }

    @Override
    protected void extractWidgetRenderState(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY, float partialTick) {
        guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED, this.sprite, this.getX(), this.getY(), this.getWidth(), this.getHeight(), this.tint);
    }

    @Override
    protected void updateWidgetNarration(NarrationElementOutput output) {
    }
}
