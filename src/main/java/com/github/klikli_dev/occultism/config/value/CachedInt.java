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

package com.github.klikli_dev.occultism.config.value;

import com.github.klikli_dev.occultism.config.IConfigCache;
import net.minecraftforge.common.ForgeConfigSpec;

public class CachedInt extends CachedPrimitive<Integer> {
    //region Fields
    protected int cachedValue;
    //endregion Fields

    //region Initialization
    protected CachedInt(IConfigCache cache,
                        ForgeConfigSpec.ConfigValue<Integer> configValue) {
        super(cache, configValue);
    }
    //endregion Initialization

    //region Static Methods
    public static CachedInt cache(IConfigCache cache, ForgeConfigSpec.ConfigValue<Integer> internal) {
        return new CachedInt(cache, internal);
    }
    //endregion Static Methods

    //region Methods
    public int get() {
        if (!this.cacheAvailable) {
            //If we don't have a cached value or need to resolve it again, get it from the actual ConfigValue
            this.cachedValue = this.configValue.get();
            this.cacheAvailable = true;
        }
        return this.cachedValue;
    }

    public void set(int value) {
        this.configValue.set(value);
        this.cachedValue = value;
        this.cacheAvailable = true;
    }
    //endregion Methods
}
