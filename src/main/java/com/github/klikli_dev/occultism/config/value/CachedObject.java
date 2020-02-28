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

public class CachedObject<T> implements ICachedValue {
    protected ForgeConfigSpec.ConfigValue<T> configValue;
    protected T cachedValue;

    protected CachedObject(IConfigCache cache, ForgeConfigSpec.ConfigValue<T> configValue){
        this.configValue = configValue;
        cache.cache(this);
    }

    public static <T> CachedObject<T> wrap(IConfigCache config, ForgeConfigSpec.ConfigValue<T> configValue) {
        return new CachedObject<>(config, configValue);
    }

    public T get() {
        if (this.cachedValue == null) {
            cachedValue = configValue.get();
        }
        return cachedValue;
    }

    public void set(T value) {
        configValue.set(value);
        cachedValue = value;
    }

    public void clear() {
        this.cachedValue = null;
    }
}
