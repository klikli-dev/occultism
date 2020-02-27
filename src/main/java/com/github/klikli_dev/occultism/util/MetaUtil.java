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

/**
 * Utility class to make conversion between metadata and blockstates more readable.
 * As performance impact is minimal the readability trumps here.
 */
public class MetaUtil {
    //region Fields
    /**
     * Mask for the two lower meta bits (max 4 diff. Values). No bit shifting required to access the embedded value.
     * Use: value = meta & LOWER_TWO_META_BITS_MASK;
     */
    public static final int LOWER_TWO_META_BITS_MASK = 0b0011;
    /**
     * Mask for the two higher meta bits (max 4 diff. Values). Shift resulting value to the right twice.
     * Use: value = (meta & HIGHER_TWO_META_BITS_MASK) >> 2;
     */
    public static final int HIGHER_TWO_META_BITS_MASK = 0b1100;
    //endregion Fields

    //region Static Methods

    /**
     * Gets the lower two bits from the given metadata.
     *
     * @param meta the entire metadata.
     * @return the lower two bits.
     */
    public static int getLowerTwoMetaBits(int meta) {
        return meta & LOWER_TWO_META_BITS_MASK;
    }

    /**
     * Gets the higher two bits from the given metadata.
     *
     * @param meta the entire metadata.
     * @return the higher two bits.
     */
    public static int getHigherTwoMetaBits(int meta) {
        return (meta & HIGHER_TWO_META_BITS_MASK) >> 2;
    }
    //endregion Static Methods
}
