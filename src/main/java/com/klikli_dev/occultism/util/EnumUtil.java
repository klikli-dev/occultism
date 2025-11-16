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

package com.klikli_dev.occultism.util;

import com.klikli_dev.occultism.Occultism;
import net.minecraft.core.Direction;

public class EnumUtil {

    //region Static Methods

    /**
     * Cycles through EnumFacing values.
     *
     * @param currentValue the current value.
     * @return the next value.
     */
    public static Direction nextFacing(Direction currentValue) {
        return Direction.values()[(currentValue.ordinal() + 1) % Direction.values().length];
    }

    public static int getConfiguredColor(int i) {
        return switch (i) {
            case 1 -> Occultism.CLIENT_CONFIG.visuals.lightGrayChalkGlyphColor.get();
            case 2 -> Occultism.CLIENT_CONFIG.visuals.grayChalkGlyphColor.get();
            case 3 -> Occultism.CLIENT_CONFIG.visuals.blackChalkGlyphColor.get();
            case 4 -> Occultism.CLIENT_CONFIG.visuals.brownChalkGlyphColor.get();
            case 5 -> Occultism.CLIENT_CONFIG.visuals.redChalkGlyphColor.get();
            case 6 -> Occultism.CLIENT_CONFIG.visuals.orangeChalkGlyphColor.get();
            case 7 -> Occultism.CLIENT_CONFIG.visuals.yellowChalkGlyphColor.get();
            case 8 -> Occultism.CLIENT_CONFIG.visuals.limeChalkGlyphColor.get();
            case 9 -> Occultism.CLIENT_CONFIG.visuals.greenChalkGlyphColor.get();
            case 10 -> Occultism.CLIENT_CONFIG.visuals.cyanChalkGlyphColor.get();
            case 11 -> Occultism.CLIENT_CONFIG.visuals.lightBlueChalkGlyphColor.get();
            case 12 -> Occultism.CLIENT_CONFIG.visuals.blueChalkGlyphColor.get();
            case 13 -> Occultism.CLIENT_CONFIG.visuals.purpleChalkGlyphColor.get();
            case 14 -> Occultism.CLIENT_CONFIG.visuals.magentaChalkGlyphColor.get();
            case 15 -> Occultism.CLIENT_CONFIG.visuals.pinkChalkGlyphColor.get();
            default -> Occultism.CLIENT_CONFIG.visuals.whiteChalkGlyphColor.get();
        };
    }
    //endregion Static Methods
}
