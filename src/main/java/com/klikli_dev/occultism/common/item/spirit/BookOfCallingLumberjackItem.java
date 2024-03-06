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

package com.klikli_dev.occultism.common.item.spirit;

import com.klikli_dev.occultism.common.entity.job.LumberjackJob;
import com.klikli_dev.occultism.common.item.spirit.calling.ItemMode;
import com.klikli_dev.occultism.common.item.spirit.calling.ItemModes;
import net.minecraft.world.item.ItemStack;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BookOfCallingLumberjackItem extends BookOfCallingItem {

    public BookOfCallingLumberjackItem(Properties properties, String translationKeyBase) {
        super(properties, translationKeyBase, spirit -> spirit.getJob().orElse(null) instanceof LumberjackJob);
    }

    @Override
    public List<ItemMode> getItemModes() {
        return Arrays.asList(ItemModes.SET_BASE, ItemModes.SET_DEPOSIT);
    }
}
