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

package com.github.klikli_dev.occultism.common.data;

import net.minecraft.util.StringRepresentable;

import java.util.HashMap;
import java.util.Map;

public enum ChalkGlyphType implements StringRepresentable {

    /**
     * The white chalk.
     */
    WHITE(0),
    /**
     * The red chalk.
     */
    RED(1),
    /**
     * The golden chalk.
     */
    GOLD(2),
    /**
     * The purple chalk.
     */
    PURPLE(3);

    //region Fields
    private static final Map<Integer, ChalkGlyphType> lookup = new HashMap<Integer, ChalkGlyphType>();

    static {
        for (ChalkGlyphType chalkGlyphType : ChalkGlyphType.values()) {
            lookup.put(chalkGlyphType.getValue(), chalkGlyphType);
        }
    }

    private final int value;
    //endregion Fields

    //region Initialization
    ChalkGlyphType(int value) {
        this.value = value;
    }
    //endregion Initialization

    //region Static Methods
    public static ChalkGlyphType get(int value) {
        return lookup.get(value);
    }
    //endregion Getter / Setter

    //region Getter / Setter
    public int getValue() {
        return this.value;
    }

    @Override
    public String getSerializedName() {
        return this.name().toLowerCase();
    }

    //endregion Static Methods
}