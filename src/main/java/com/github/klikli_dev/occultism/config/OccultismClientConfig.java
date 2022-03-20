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
import net.minecraftforge.common.ForgeConfigSpec.BooleanValue;
import net.minecraftforge.common.ForgeConfigSpec.IntValue;

public class OccultismClientConfig {

    public final VisualSettings visuals;
    public final MiscSettings misc;
    public final ForgeConfigSpec spec;

    public OccultismClientConfig() {
        ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();
        this.visuals = new VisualSettings(builder);
        this.misc = new MiscSettings(builder);
        this.spec = builder.build();
    }

    public static class VisualSettings {
        public final BooleanValue disableDemonsDreamShaders;
        public final BooleanValue disableHolidayTheming;

        public VisualSettings(ForgeConfigSpec.Builder builder) {
            builder.comment("Visual Settings").push("visual");

            this.disableDemonsDreamShaders = builder.comment("Disables the headache- and possibly seizure-inducing visual effects of Demon's Dream.")
                    .define("disableDemonsDreamShaders", false);
            this.disableHolidayTheming = builder.comment("Disables holiday themed visual content such as familiar skins.")
                    .define("disableHolidayTheming", false);
            builder.pop();
        }
    }

    public static class MiscSettings {
        public final BooleanValue syncJeiSearch;
        public final BooleanValue divinationRodHighlightAllResults;
        public final IntValue divinationRodScanRange;

        public MiscSettings(ForgeConfigSpec.Builder builder) {
            builder.comment("Misc Settings").push("misc");

            this.syncJeiSearch = builder.comment("Sync JEI search in storage actuator.")
                    .define("syncJeiSearch", false);
            this.divinationRodHighlightAllResults = builder.comment("If true, divination rod will render all matching blocks with an outline. Keep disabled if it causes lag.")
                    .define("divinationRodHighlightAllResults", false);
            this.divinationRodScanRange = builder.comment("The scan range in blocks for the divination rod. Too high might cause lags")
                    .defineInRange("divinationRodScanRange", 96, 1, Integer.MAX_VALUE);
            builder.pop();
        }
    }
}
