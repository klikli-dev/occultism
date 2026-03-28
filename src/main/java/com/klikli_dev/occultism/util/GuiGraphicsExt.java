/*
 * MIT License
 *
 * Copyright 2023 klikli-dev
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

package com.klikli_dev.occultism.util;

import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.Font;
import net.minecraft.network.chat.Component;
import net.minecraft.util.FormattedCharSequence;
import org.jetbrains.annotations.Nullable;

/**
 * Helper class for GuiGraphicsExtractor operations.
 * Note: In 26.1, the rendering API changed significantly. 
 * For custom rendering, use the native GuiGraphicsExtractor methods directly.
 */
public class GuiGraphicsExt {

    /**
     * Render text using Component - delegates to GuiGraphicsExtractor.
     */
    public static void drawString(GuiGraphicsExtractor guiGraphics, Font font, @Nullable Component component, int x, int y, int color, boolean drawShadow) {
        if (component == null) {
            return;
        }
        guiGraphics.drawString(font, component, x, y, color, drawShadow);
    }

    /**
     * Render text using FormattedCharSequence - delegates to GuiGraphicsExtractor.
     */
    public static void drawString(GuiGraphicsExtractor guiGraphics, Font font, FormattedCharSequence text, int x, int y, int color, boolean drawShadow) {
        guiGraphics.drawString(font, text, x, y, color, drawShadow);
    }
}