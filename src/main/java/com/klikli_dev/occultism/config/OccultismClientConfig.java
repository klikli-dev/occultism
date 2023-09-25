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
        public final BooleanValue showItemTagsInTooltip;
        public final BooleanValue disableDemonsDreamShaders;
        public final BooleanValue disableHolidayTheming;
        public final BooleanValue useAlternativeDivinationRodRenderer;

        public final ForgeConfigSpec.ConfigValue<Integer> whiteChalkGlyphColor;
        public final ForgeConfigSpec.ConfigValue<Integer> goldenChalkGlyphColor;
        public final ForgeConfigSpec.ConfigValue<Integer> purpleChalkGlyphColor;
        public final ForgeConfigSpec.ConfigValue<Integer> redChalkGlyphColor;


        public VisualSettings(ForgeConfigSpec.Builder builder) {
            builder.comment("Visual Settings").push("visual");
            this.showItemTagsInTooltip = builder.comment("Shows all tags an item has in the tooltip on hover if advanced tooltips (F3+H) are enabled.")
                    .define("showItemTagsInTooltip", false);
            this.disableDemonsDreamShaders = builder.comment("Disables the headache- and possibly seizure-inducing visual effects of Demon's Dream.")
                    .define("disableDemonsDreamShaders", false);
            this.disableHolidayTheming = builder.comment("Disables holiday themed visual content such as familiar skins.")
                    .define("disableHolidayTheming", false);
            this.useAlternativeDivinationRodRenderer = builder.comment(
                            "When true the old divination rod selected block renderer will be used.",
                            "May work for some people that do not see selected block outlines when using the divination rod.")
                    .define("useAlternativeDivinationRodRenderer", false);

            this.whiteChalkGlyphColor = builder.comment(
                    "The integer code of the color of the white chalk glyph in world.",
                    "This is intended to allow people with color blindness to change the color of the glyph.",
                    "For most types of color blindness it should not be necessary to change this."
            ).define("whiteChalkGlyphColor", 0xffffff);

            this.goldenChalkGlyphColor = builder.comment(
                    "The integer code of the color of the golden chalk glyph in world.",
                    "This is intended to allow people with color blindness to change the color of the glyph.",
                    "For most types of color blindness it should not be necessary to change this."
            ).define("goldenChalkGlyphColor", 0xf0d700);


            this.purpleChalkGlyphColor = builder.comment(
                    "The integer code of the color of the purple chalk glyph in world.",
                    "This is intended to allow people with color blindness to change the color of the glyph.",
                    "For most types of color blindness it should not be necessary to change this."
            ).define("purpleChalkGlyphColor", 0x9c0393);

            this.redChalkGlyphColor = builder.comment(
                    "The integer code of the  color of the red chalk glyph in world.",
                    "This is intended to allow people with color blindness to change the color of the glyph.",
                    "For most types of color blindness this value should be changed to a green color, we recommend 33289 (= Hex 0x008209)"
            ).define("redChalkGlyphColor", 0xcc0101);

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
            this.divinationRodHighlightAllResults = builder.comment(
                            "If true, divination rod will render all matching blocks with an outline. Disable if it causes lag.",
                            "This setting will be unused, if Theurgy is installed alongside, as Occultism will use Theurgy's divination rod result rendering instead.")
                    .define("divinationRodHighlightAllResults", false);
            this.divinationRodScanRange = builder.comment("The scan range in blocks for the divination rod. Too high might cause lags")
                    .defineInRange("divinationRodScanRange", 129, 1, Integer.MAX_VALUE);
            builder.pop();
        }
    }
}
