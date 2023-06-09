/*
 * MIT License
 *
 * Copyright 2020 klikli-dev
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
 * associated documentation files (the "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
 * of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following
 * conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial
 * portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR
 * PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT
 * OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
 * OTHER DEALINGS IN THE SOFTWARE.
 */

package com.klikli_dev.occultism.client.gui.controls;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;

import java.util.ArrayList;
import java.util.List;

public class LabelWidget extends AbstractWidget {

    public List<String> lines = new ArrayList<>();
    public boolean centered = false;
    public boolean rightAligned = false;
    public int width = 0;
    public int margin = 2;
    public boolean shadow = false;

    public LabelWidget(int xIn, int yIn) {
        this(xIn, yIn, false);
    }

    public LabelWidget(int xIn, int yIn, boolean centered) {
        this(xIn, yIn, centered, -1, -1);
    }

    public LabelWidget(int xIn, int yIn, boolean centered, int width, int height) {
        this(xIn, yIn, centered, width, height, 2);
    }

    public LabelWidget(int xIn, int yIn, boolean centered, int width, int height, int margin) {
        this(xIn, yIn, centered, width, height, margin, 16777215); //color is default color from widget
    }

    public LabelWidget(int xIn, int yIn, boolean centered, int width, int height, int margin, int color) {
        super(xIn, yIn, width, height, Component.literal(""));
        this.centered = centered;
        this.width = width;
        this.margin = margin;
        this.packedFGColor = color;
        //active false prevents click intercepts and other unwanted interactions
        this.active = false;
    }

    @Override
    public void renderWidget(GuiGraphics guiGraphics, int p_268034_, int p_268009_, float p_268085_) {
        if (this.visible) {
            Minecraft minecraft = Minecraft.getInstance();
            Font fontrenderer = minecraft.font;
            RenderSystem.clearColor(1.0F, 1.0F, 1.0F, this.alpha);
            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();

            if (this.centered && this.width <= 0 && this.lines.size() > 0)
                this.width = fontrenderer.width(this.lines.get(0));

            int color = this.getFGColor();
            for (int i = 0; i < this.lines.size(); i++) {
                int top = this.getY() + i * (fontrenderer.lineHeight + this.margin);
                if (this.centered) {
                    this.drawCenteredLabelString(guiGraphics, fontrenderer, this.lines.get(i), this.getX(), top, color);
                } else if (this.rightAligned) {
                    this.drawRightAlignedLabelString(guiGraphics, fontrenderer, this.lines.get(i), this.getX(), top, color);
                } else {
                    this.drawLabelString(guiGraphics, fontrenderer, this.lines.get(i), this.getX(), top, color);
                }
            }
        }
    }

    public void drawCenteredLabelString(GuiGraphics guiGraphics, Font font, String text, int x, int y, int color) {
        if (this.shadow) {
            guiGraphics.drawString(font, text, (float) (x - font.width(text) / 2), (float) y, color, true);
        } else {
            guiGraphics.drawString(font, text, (float) (x - font.width(text) / 2), (float) y, color, false);
        }
    }

    public void drawRightAlignedLabelString(GuiGraphics guiGraphics, Font font, String text,
                                            int x, int y,
                                            int color) {
        if (this.shadow) {
            guiGraphics.drawString(font, text, (float) (x - font.width(text)), (float) y, color, true);
        } else {
            guiGraphics.drawString(font, text, (float) (x - font.width(text)), (float) y, color, false);
        }

    }

    public void drawLabelString(GuiGraphics guiGraphics, Font font, String text, int x, int y, int color) {
        if (this.shadow) {
            guiGraphics.drawString(font, text, x, y, color, true);
        } else {
            guiGraphics.drawString(font, text, x, y, color, false);
        }
    }

    public LabelWidget alignRight(boolean align) {
        this.rightAligned = align;
        if (this.rightAligned)
            this.centered = false;
        return this;
    }

    public void addLine(String string, boolean translate) {
        if (translate)
            this.addLine(I18n.get(string));
        else
            this.addLine(string);
    }

    public void addLine(String string) {
        this.lines.add(string);
    }

    public void addLine(Component component) {
        this.lines.add(component.getString());
    }

    @Override
    protected void updateWidgetNarration(NarrationElementOutput pNarrationElementOutput) {

    }

}
