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

package com.github.klikli_dev.occultism.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class OccultismConfig extends ConfigBase {

    //region Fields
    public final StorageSettings storageSettings;
    public final ForgeConfigSpec spec;
    //endregion Fields

    //region Initialization
    public OccultismConfig() {
        ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();
        this.storageSettings = new StorageSettings(this, builder);
        this.spec = builder.build();
    }
    //endregion Initialization

    public class StorageSettings extends ConfigCategoryBase {
        //region Fields
        public final CachedValue<Integer> stabilizerTier1Slots;
        public final CachedValue<Integer> stabilizerTier2Slots;
        public final CachedValue<Integer> stabilizerTier3Slots;
        public final CachedValue<Integer> stabilizerTier4Slots;
        public CachedValue<Integer> controllerBaseSlots;
        //endregion Fields

        //region Initialization
        public StorageSettings(IConfigCache parent, ForgeConfigSpec.Builder builder) {
            super(parent, builder);
            builder.comment("Storage Settings").push("storage");
            stabilizerTier1Slots = CachedValue.wrap(this,
                    builder.comment("The amount of slots the storage stabilizer tier 1 provides.")
                            .define("stabilizerTier1Slots", 128));
            stabilizerTier2Slots = CachedValue.wrap(this,
                    builder.comment("The amount of slots the storage stabilizer tier 2 provides.")
                            .define("stabilizerTier2Slots", 256));
            stabilizerTier3Slots = CachedValue.wrap(this,
                    builder.comment("The amount of slots the storage stabilizer tier 3 provides.")
                            .define("stabilizerTier3Slots", 512));
            stabilizerTier4Slots = CachedValue.wrap(this,
                    builder.comment("The amount of slots the storage stabilizer tier 4 provides.")
                            .define("stabilizerTier4Slots", 1024));
            controllerBaseSlots = CachedValue.wrap(this,
                    builder.comment("The amount of slots the storage actuator provides.")
                            .define("controllerBaseSlots", 128));
            builder.pop();
        }
        //endregion Initialization
    }
}
