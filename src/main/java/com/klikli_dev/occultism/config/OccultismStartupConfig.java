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

package com.klikli_dev.occultism.config;

import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.neoforge.common.ModConfigSpec.BooleanValue;
import net.neoforged.neoforge.common.ModConfigSpec.ConfigValue;
import net.neoforged.neoforge.common.ModConfigSpec.DoubleValue;

import java.util.ArrayList;
import java.util.List;

public class OccultismStartupConfig {

    public final RitualSettings rituals;

    public final ModConfigSpec spec;
    public OccultismStartupConfig() {
        ModConfigSpec.Builder builder = new ModConfigSpec.Builder();
        this.rituals = new RitualSettings(builder);
        this.spec = builder.build();
    }

    public static class RitualSettings {
        public final ConfigValue<List<? extends String>> possibleSpiritNames;
        public final DoubleValue usePossibleSpiritNamesChance;


        public RitualSettings(ModConfigSpec.Builder builder) {
            builder.comment("Ritual Settings").push("rituals");

            this.possibleSpiritNames =
                    builder.comment("By default spirit names are generated randomly. " +
                                    "This list can be used as an additional source of spirit names, or even a full replacement, depending on the configuration of \"usePossibleSpiritNamesChance\".")
                            .defineList("possibleSpiritNames", new ArrayList<>(), () -> "A Name", (e) -> e instanceof String);

            this.usePossibleSpiritNamesChance =
                    builder.comment(
                                    "0.0 (default) to only use random names.",
                                    "1.0 to only use the names in \"possibleSpiritNames\"",
                                    "0.1-0.9 to use a mix of both, the higher the value the higher the chance of using a name from this list instead of a random name.",
                                    "Will be ignored if \"possibleSpiritNames\" is empty.")
                            .defineInRange("usePossibleSpiritNamesChance", 0.0, 0.0, 1.0);

            builder.pop();
        }
    }
}
