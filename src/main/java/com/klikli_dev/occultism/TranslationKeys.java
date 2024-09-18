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

package com.klikli_dev.occultism;

public class TranslationKeys {
    protected static final String ITEM = "item." + Occultism.MODID;
    public static final String BOOK_OF_CALLING_GENERIC = ITEM + ".book_of_calling";

    protected static final String JEI = "jei." + Occultism.MODID;

    public static final String JEI_CRUSHING_RECIPE_MIN_TIER = JEI + ".crushing.min_tier";
    public static final String JEI_CRUSHING_RECIPE_MAX_TIER = JEI + ".crushing.max_tier";

    public static final String MESSAGE_CONTAINER_ALREADY_OPEN = "messages." + Occultism.MODID + ".container_already_open";

    public static final String HUD_NO_PENTACLE_FOUND = "hud." + Occultism.MODID + ".no_pentacle_found";
    public static final String HUD_PENTACLE_FOUND = "hud." + Occultism.MODID + ".pentacle_found";

    public static final String RITUAL_SATCHEL_NO_PREVIEW_IN_WORLD = ITEM + ".ritual_satchel.no_preview_in_world";
    public static final String RITUAL_SATCHEL_NO_PREVIEW_BLOCK_TARGETED = ITEM + ".ritual_satchel.no_preview_block_targeted";
    public static final String RITUAL_SATCHEL_NO_VALID_ITEM_IN_SATCHEL = ITEM + ".ritual_satchel.no_valid_item_in_satchel";

    public static class Condition {
        protected static final String PREFIX = "condition." + Occultism.MODID + ".";

        public static class Ritual {
            protected static final String PREFIX = Condition.PREFIX + "ritual.";
            public static final String IS_IN_DIMENSION_TYPE_DESCRIPTION = PREFIX + "is_in_dimension_type.description";
            public static final String IS_IN_DIMENSION_TYPE_NOT_FULFILLED = PREFIX + "is_in_dimension_type.not_fulfilled";
        }
    }
}
