/*
 * SPDX-FileCopyrightText: 2026 klikli-dev
 *
 * SPDX-License-Identifier: MIT
 */

package com.klikli_dev.occultism.client.gui.widget;

import com.klikli_dev.codedefinedgui.api.widget.GuiTextWidget;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.network.chat.Component;

import java.util.function.IntSupplier;
import java.util.function.Supplier;

public class VerticallyCenteredTextWidget extends GuiTextWidget {
    private final float yOffset;

    public VerticallyCenteredTextWidget(int x, int y, float yOffset, Supplier<Component> textSupplier,
                                        IntSupplier colorSupplier, boolean shadow) {
        super(x, y, textSupplier, colorSupplier, shadow);
        this.yOffset = yOffset;
    }

    @Override
    protected void extractWidgetRenderState(GuiGraphicsExtractor graphics, int mouseX, int mouseY,
                                            float partialTick) {
        graphics.pose().pushMatrix();
        graphics.pose().translate(0.0F, this.yOffset);
        super.extractWidgetRenderState(graphics, mouseX, mouseY, partialTick);
        graphics.pose().popMatrix();
    }
}
