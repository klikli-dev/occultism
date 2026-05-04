// SPDX-FileCopyrightText: 2026 klikli-dev
//
// SPDX-License-Identifier: MIT

package com.klikli_dev.occultism.client.gui.widget;

import com.klikli_dev.codedefinedgui.gui.texture.GuiSprite;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.network.chat.Component;

public class SpriteButtonWidget extends AbstractWidget {
    private final GuiSprite normalSprite;
    private final GuiSprite hoverSprite;
    private final GuiSprite disabledSprite;
    private final Runnable onPress;

    public SpriteButtonWidget(int x, int y, GuiSprite normalSprite, GuiSprite hoverSprite, Component message,
                              Runnable onPress) {
        this(x, y, normalSprite, hoverSprite, normalSprite, message, onPress);
    }

    public SpriteButtonWidget(int x, int y, GuiSprite normalSprite, GuiSprite hoverSprite, GuiSprite disabledSprite,
                              Component message, Runnable onPress) {
        super(x, y, normalSprite.width(), normalSprite.height(), message);
        this.normalSprite = normalSprite;
        this.hoverSprite = hoverSprite;
        this.disabledSprite = disabledSprite;
        this.onPress = onPress;
    }

    @Override
    protected void extractWidgetRenderState(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY, float partialTicks) {
        this.currentSprite(mouseX, mouseY).extractRenderState(guiGraphics, this.getX(), this.getY(), this.getWidth(),
                this.getHeight());
    }

    @Override
    public void onClick(MouseButtonEvent event, boolean doubleClick) {
        if (!this.active) {
            return;
        }

        this.playDownSound(Minecraft.getInstance().getSoundManager());
        this.onPress.run();
    }

    @Override
    protected void updateWidgetNarration(NarrationElementOutput output) {
        this.defaultButtonNarrationText(output);
    }

    private GuiSprite currentSprite(int mouseX, int mouseY) {
        if (!this.active) {
            return this.disabledSprite;
        }

        return this.isMouseOver(mouseX, mouseY) ? this.hoverSprite : this.normalSprite;
    }
}
