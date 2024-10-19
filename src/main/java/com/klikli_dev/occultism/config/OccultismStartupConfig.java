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

    public final DimensionalMineshaftSettings dimensionalMineshaft;
    public final RitualSettings rituals;

    public final ModConfigSpec spec;
    public OccultismStartupConfig() {
        ModConfigSpec.Builder builder = new ModConfigSpec.Builder();
        this.dimensionalMineshaft = new DimensionalMineshaftSettings(builder);
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

    public static class DimensionalMineshaftSettings {
        public final MinerSpiritSettings minerFoliotUnspecialized;
        public final MinerSpiritSettings minerDjinniOres;
        public final MinerSpiritSettings minerAfritDeeps;
        public final MinerSpiritSettings minerMaridMaster;

        public DimensionalMineshaftSettings(ModConfigSpec.Builder builder) {
            builder.comment("Dimensional Mineshaft Settings").push("dimensional_mineshaft");

            this.minerFoliotUnspecialized =
                    new MinerSpiritSettings("miner_foliot_unspecialized", builder, 400, 1, 1000);

            this.minerDjinniOres =
                    new MinerSpiritSettings("miner_djinni_ores", builder, 300, 1, 400);

            this.minerAfritDeeps =
                    new MinerSpiritSettings("miner_afrit_deeps", builder, 200, 1, 800);

            this.minerMaridMaster =
                    new MinerSpiritSettings("miner_marid_master", builder, 100, 1, 1600);

            builder.pop();
        }

        public static class MinerSpiritSettings {
            public final ModConfigSpec.IntValue maxMiningTime;
            public final ModConfigSpec.IntValue rollsPerOperation;
            public final ModConfigSpec.IntValue durability;

            public MinerSpiritSettings(String oreName, ModConfigSpec.Builder builder,
                                       int maxMiningTime, int rollsPerOperation, int durability) {
                builder.comment("Miner Spirit Settings").push(oreName);

                this.maxMiningTime =
                        builder.comment("The amount of time it takes the spirit to perform one mining operation.")
                                .defineInRange("maxMiningTime", maxMiningTime, 0, Integer.MAX_VALUE);
                this.rollsPerOperation =
                        builder.comment("The amount of blocks the spirit will obtain per mining operation")
                                .defineInRange("rollsPerOperation", rollsPerOperation, 0, Integer.MAX_VALUE);
                this.durability =
                        builder.comment("The amount of mining operations the spirit can perform before breaking.")
                                .defineInRange("durability", durability, 0, Integer.MAX_VALUE);

                builder.pop();
            }
        }

    }
}
