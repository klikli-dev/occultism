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

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiLabel;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

import java.lang.reflect.Field;
import java.util.List;

public class GuiLabelNoShadow extends GuiLabel {
    //region Fields
    protected static Field labelsField;

    static {
        //TODOUPDATE: update obfuscated name
        labelsField = ReflectionHelper.findField(GuiLabel.class, "labels", "bjf");
        labelsField.setAccessible(true);
    }

    protected List<String> labels;
    //endregion Fields

    //region Initialization
    public GuiLabelNoShadow(FontRenderer fontRendererObj, int labelId, int xIn, int yIn, int widthIn, int heightIn,
                            int colorIn) {
        super(fontRendererObj, labelId, xIn, yIn, widthIn, heightIn, colorIn);
        try {
            this.labels = (List<String>) labelsField.get(this);
        } catch (Exception ignored) {
        }
    }
    //endregion Initialization

    //region Overrides
    @Override
    public void drawCenteredString(FontRenderer fontRendererIn, String text, int x, int y, int color) {
        fontRendererIn.drawString(text, (float) (x - fontRendererIn.getStringWidth(text) / 2), (float) y, color, false);
    }

    @Override
    public void drawString(FontRenderer fontRendererIn, String text, int x, int y, int color) {
        fontRendererIn.drawString(text, (float) x, (float) y, color, false);
    }
    //endregion Overrides

    //region Methods
    public void addLineTranslated(String message) {
        this.labels.add(message);
    }
    //endregion Methods
}
