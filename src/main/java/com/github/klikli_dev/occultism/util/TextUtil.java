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

package com.github.klikli_dev.occultism.util;

import net.minecraft.util.text.TextFormatting;

public class TextUtil {
    //region Static Methods

    /**
     * Formats the given spirit name in bold and gold.
     *
     * @param name the name to format.
     * @return the formatted name.
     */
    public static String formatDemonName(String name) {
        return TextFormatting.GOLD.toString() + TextFormatting.BOLD.toString() + name + TextFormatting.RESET.toString();
    }

    /**
     * Formats the given number for human friendly display.
     * Rounds high numbers.
     *
     * @param number the number to format.
     * @return a formatted string for the number.
     */
    public static String formatLargeNumber(int number) {
        if (number < Math.pow(10, 3)) {
            return number + "";
        }
        else if (number < Math.pow(10, 6)) {
            int rounded = Math.round(number / 1000.0F);
            return rounded + "K";
        }
        else if (number < Math.pow(10, 9)) {
            int rounded = Math.round(number / (float) Math.pow(10, 6));
            return rounded + "M";
        }
        else if (number < Math.pow(10, 12)) {
            int rounded = Math.round(number / (float) Math.pow(10, 9));
            return rounded + "B";
        }
        return Integer.toString(number);
    }
    //endregion Static Methods
}
