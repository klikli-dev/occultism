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

package com.klikli_dev.occultism.api.common.data;

import java.util.HashMap;
import java.util.Map;

public enum OtherworldBlockTier {
    NONE(0),
    ONE(1),
    TWO(2);

    private static final Map<Integer, OtherworldBlockTier> lookup = new HashMap<Integer, OtherworldBlockTier>();

    static {
        for (OtherworldBlockTier tier : OtherworldBlockTier.values()) {
            lookup.put(tier.getLevel(), tier);
        }
    }

    private final int level;

    OtherworldBlockTier(int level) {
        this.level = level;
    }

    //region Static Methods
    public static OtherworldBlockTier get(int level) {
        return lookup.get(level);
    }
    //endregion Getter / Setter

    public static OtherworldBlockTier max(OtherworldBlockTier a, OtherworldBlockTier b) {
        if (a.getLevel() > b.getLevel())
            return a;
        return b;
    }

    //region Getter / Setter
    public int getLevel() {
        return this.level;
    }
    //endregion Static Methods
}
