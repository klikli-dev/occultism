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

import com.klikli_dev.occultism.Occultism;

import java.util.HashMap;
import java.util.Map;

public enum WorkAreaSize {
    SMALL(16, "small"),
    MEDIUM(32, "medium"),
    LARGE(64, "large");

    private static final Map<Integer, WorkAreaSize> lookup = new HashMap<Integer, WorkAreaSize>();
    private static final String TRANSLATION_KEY_BASE = "enum." + Occultism.MODID + ".work_area_size";

    static {
        for (WorkAreaSize workAreaSize : WorkAreaSize.values()) {
            lookup.put(workAreaSize.getValue(), workAreaSize);
        }
    }

    private final int value;
    private final String translationKey;

    WorkAreaSize(int value, String translationKey) {
        this.value = value;
        this.translationKey = TRANSLATION_KEY_BASE + "." + translationKey;
    }

    //region Static Methods
    public static WorkAreaSize get(int value) {
        return lookup.get(value);
    }

    //region Getter / Setter
    public int getValue() {
        return this.value;
    }
    //endregion Getter / Setter

    public String getDescriptionId() {
        return this.translationKey;
    }
    //endregion Static Methods

    public WorkAreaSize next() {
        return values()[(this.ordinal() + 1) % values().length];
    }

    public boolean equals(int value) {
        return this.value == value;
    }
}
