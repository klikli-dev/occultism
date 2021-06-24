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

package com.github.klikli_dev.occultism.client.gui.controls;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

import java.util.ArrayList;
import java.util.List;

public class LabelWidget extends Widget {
    //region Fields
    public List<String> lines = new ArrayList<>();
    public boolean centered = false;
    public boolean rightAligned = false;
    public int width = 0;
    public int margin = 2;
    public boolean shadow = false;
    //endregion Fields

    //region Initialization
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
        super(xIn, yIn, width, height, new StringTextComponent(""));
        this.centered = centered;
        this.width = width;
        this.margin = margin;
        this.packedFGColor = color;
        //active false prevents click intercepts and other unwanted interactions
        this.active = false;
    }
    //endregion Initialization

    //region Overrides
    @Override
    public void render(MatrixStack stack, int mouseX, int mouseY, float partialTicks) {
        if (this.visible) {
            Minecraft minecraft = Minecraft.getInstance();
            FontRenderer fontrenderer = minecraft.fontRenderer;
            RenderSystem.color4f(1.0F, 1.0F, 1.0F, this.alpha);
            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();

            if (this.centered && this.width <= 0 && this.lines.size() > 0)
                this.width = fontrenderer.getStringWidth(this.lines.get(0));

            int color = this.getFGColor();
            for (int i = 0; i < this.lines.size(); i++) {
                int top = this.y + i * (fontrenderer.FONT_HEIGHT + this.margin);
                if (this.centered) {
                    this.drawCenteredLabelString(stack, fontrenderer, this.lines.get(i), this.x, top, color);
                }
                else if (this.rightAligned) {
                    this.drawRightAlignedLabelString(stack, fontrenderer, this.lines.get(i), this.x, top, color);
                }
                else {
                    this.drawLabelString(stack, fontrenderer, this.lines.get(i), this.x, top, color);
                }
            }
        }
    }

    public void drawCenteredLabelString(MatrixStack stack, FontRenderer fontRenderer, String text, int x, int y, int color) {
        if (this.shadow) {
            fontRenderer
                    .drawStringWithShadow(stack, text, (float) (x - fontRenderer.getStringWidth(text) / 2), (float) y, color);
        }
        else {
            fontRenderer.drawString(stack, text, (float) (x - fontRenderer.getStringWidth(text) / 2), (float) y, color);
        }
    }

    public void drawRightAlignedLabelString(MatrixStack stack, FontRenderer fontRenderer, String text,
                                       int x, int y,
                                       int color) {
        if (this.shadow) {
            fontRenderer.drawStringWithShadow(stack, text, (float) (x - fontRenderer.getStringWidth(text)), (float) y, color);
        }
        else {
            fontRenderer.drawString(stack, text, (float) (x - fontRenderer.getStringWidth(text)), (float) y, color);
        }

    }

    public void drawLabelString(MatrixStack stack, FontRenderer fontRenderer, String text, int x, int y, int color) {
        if (this.shadow) {
            fontRenderer.drawStringWithShadow(stack, text, x, y, color);
        }
        else {
            fontRenderer.drawString(stack, text, x, y, color);
        }
    }
    //endregion Overrides

    //region Methods
    public LabelWidget alignRight(boolean align) {
        this.rightAligned = align;
        if (this.rightAligned)
            this.centered = false;
        return this;
    }

    public void addLine(String string, boolean translate) {
        if (translate)
            this.addLine(I18n.format(string));
        else
            this.addLine(string);
    }

    public void addLine(String string) {
        this.lines.add(string);
    }

    public void addLine(ITextComponent component) {
        this.lines.add(component.getString());
    }
    //endregion Methods
}
