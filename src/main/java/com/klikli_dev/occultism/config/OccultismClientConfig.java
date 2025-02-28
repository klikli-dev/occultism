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
import net.neoforged.neoforge.common.ModConfigSpec.IntValue;

public class OccultismClientConfig {

    public final VisualSettings visuals;
    public final MiscSettings misc;
    public final ModConfigSpec spec;

    public OccultismClientConfig() {
        ModConfigSpec.Builder builder = new ModConfigSpec.Builder();
        this.visuals = new VisualSettings(builder);
        this.misc = new MiscSettings(builder);
        this.spec = builder.build();
    }

    public static class VisualSettings {
        public final BooleanValue showItemTagsInTooltip;
        public final BooleanValue disableDemonsDreamShaders;
        public final BooleanValue disableHolidayTheming;
        public final BooleanValue useAlternativeDivinationRodRenderer;

        public final IntValue whiteChalkGlyphColor;
        public final IntValue yellowChalkGlyphColor;
        public final IntValue purpleChalkGlyphColor;
        public final IntValue redChalkGlyphColor;
        public final IntValue lightGrayChalkGlyphColor;
        public final IntValue grayChalkGlyphColor;
        public final IntValue blackChalkGlyphColor;
        public final IntValue brownChalkGlyphColor;
        public final IntValue orangeChalkGlyphColor;
        public final IntValue limeChalkGlyphColor;
        public final IntValue greenChalkGlyphColor;
        public final IntValue cyanChalkGlyphColor;
        public final IntValue lightBlueChalkGlyphColor;
        public final IntValue blueChalkGlyphColor;
        public final IntValue magentaChalkGlyphColor;
        public final IntValue pinkChalkGlyphColor;



        public VisualSettings(ModConfigSpec.Builder builder) {
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
            ).defineInRange("whiteChalkGlyphColor", 0xffffff, 0, 0xffffff);

            this.lightGrayChalkGlyphColor = builder.comment(
                    "The integer code of the color of the light gray chalk glyph in world.",
                    "This is intended to allow people with color blindness to change the color of the glyph.",
                    "For most types of color blindness it should not be necessary to change this."
            ).defineInRange("lightGrayChalkGlyphColor", 0x9d9d97, 0, 0xffffff);

            this.grayChalkGlyphColor = builder.comment(
                    "The integer code of the color of the gray chalk glyph in world.",
                    "This is intended to allow people with color blindness to change the color of the glyph.",
                    "For most types of color blindness it should not be necessary to change this."
            ).defineInRange("grayChalkGlyphColor", 0x474f52, 0, 0xffffff);

            this.blackChalkGlyphColor = builder.comment(
                    "The integer code of the color of the black chalk glyph in world.",
                    "This is intended to allow people with color blindness to change the color of the glyph.",
                    "For most types of color blindness it should not be necessary to change this."
            ).defineInRange("blackChalkGlyphColor", 0x1d1d21, 0, 0xffffff);

            this.brownChalkGlyphColor = builder.comment(
                    "The integer code of the color of the brown chalk glyph in world.",
                    "This is intended to allow people with color blindness to change the color of the glyph.",
                    "For most types of color blindness it should not be necessary to change this."
            ).defineInRange("brownChalkGlyphColor", 0x835432, 0, 0xffffff);

            this.redChalkGlyphColor = builder.comment(
                    "The integer code of the  color of the red chalk glyph in world.",
                    "This is intended to allow people with color blindness to change the color of the glyph.",
                    "For most types of color blindness this value should be changed to a green color, we recommend 33289 (= Hex 0x008209)"
            ).defineInRange("redChalkGlyphColor", 0xcc0101,0, 0xffffff);

            this.orangeChalkGlyphColor = builder.comment(
                    "The integer code of the color of the orange chalk glyph in world.",
                    "This is intended to allow people with color blindness to change the color of the glyph.",
                    "For most types of color blindness it should not be necessary to change this."
            ).defineInRange("orangeChalkGlyphColor", 0xf9801d, 0, 0xffffff);

            this.yellowChalkGlyphColor = builder.comment(
                    "The integer code of the color of the yellow chalk glyph in world.",
                    "This is intended to allow people with color blindness to change the color of the glyph.",
                    "For most types of color blindness it should not be necessary to change this."
            ).defineInRange("yellowChalkGlyphColor", 0xf0d700, 0, 0xffffff);

            this.limeChalkGlyphColor = builder.comment(
                    "The integer code of the color of the lime chalk glyph in world.",
                    "This is intended to allow people with color blindness to change the color of the glyph.",
                    "For most types of color blindness it should not be necessary to change this."
            ).defineInRange("limeChalkGlyphColor", 0x80c71f, 0, 0xffffff);

            this.greenChalkGlyphColor = builder.comment(
                    "The integer code of the color of the green chalk glyph in world.",
                    "This is intended to allow people with color blindness to change the color of the glyph.",
                    "For most types of color blindness it should not be necessary to change this."
            ).defineInRange("greenChalkGlyphColor", 0x5e7c16, 0, 0xffffff);

            this.cyanChalkGlyphColor = builder.comment(
                    "The integer code of the color of the cyan chalk glyph in world.",
                    "This is intended to allow people with color blindness to change the color of the glyph.",
                    "For most types of color blindness it should not be necessary to change this."
            ).defineInRange("cyanChalkGlyphColor", 0x169c9c, 0, 0xffffff);

            this.lightBlueChalkGlyphColor = builder.comment(
                    "The integer code of the color of the light blue chalk glyph in world.",
                    "This is intended to allow people with color blindness to change the color of the glyph.",
                    "For most types of color blindness it should not be necessary to change this."
            ).defineInRange("lightBlueChalkGlyphColor", 0x80abe7, 0, 0xffffff);

            this.blueChalkGlyphColor = builder.comment(
                    "The integer code of the color of the blue chalk glyph in world.",
                    "This is intended to allow people with color blindness to change the color of the glyph.",
                    "For most types of color blindness it should not be necessary to change this."
            ).defineInRange("blueChalkGlyphColor", 0x3c44aa, 0, 0xffffff);

            this.purpleChalkGlyphColor = builder.comment(
                    "The integer code of the color of the purple chalk glyph in world.",
                    "This is intended to allow people with color blindness to change the color of the glyph.",
                    "For most types of color blindness it should not be necessary to change this."
            ).defineInRange("purpleChalkGlyphColor", 0x9c0393,0, 0xffffff);

            this.magentaChalkGlyphColor = builder.comment(
                    "The integer code of the color of the magenta chalk glyph in world.",
                    "This is intended to allow people with color blindness to change the color of the glyph.",
                    "For most types of color blindness it should not be necessary to change this."
            ).defineInRange("magentaChalkGlyphColor", 0xc74ebd, 0, 0xffffff);

            this.pinkChalkGlyphColor = builder.comment(
                    "The integer code of the color of the pink chalk glyph in world.",
                    "This is intended to allow people with color blindness to change the color of the glyph.",
                    "For most types of color blindness it should not be necessary to change this."
            ).defineInRange("pinkChalkGlyphColor", 0xf38baa, 0, 0xffffff);
            builder.pop();
        }
    }

    public static class MiscSettings {
        public final BooleanValue syncJeiSearch;
        public final BooleanValue divinationRodHighlightAllResults;
        public final IntValue divinationRodScanRange;
        public final BooleanValue disableSpiritFireSuccessSound;
        public final IntValue pentagramInBowlInfoCount;
        public final IntValue pentagramInBowlInfoTicks;

        public MiscSettings(ModConfigSpec.Builder builder) {
            builder.comment("Misc Settings").push("misc");

            this.syncJeiSearch = builder.comment("Sync JEI search in storage actuator.")
                    .define("syncJeiSearch", false);
            this.divinationRodHighlightAllResults = builder.comment(
                            "If true, divination rod will render all matching blocks with an outline. Disable if it causes lag.",
                            "This setting will be unused, if Theurgy is installed alongside, as Occultism will use Theurgy's divination rod result rendering instead.")
                    .define("divinationRodHighlightAllResults", false);
            this.divinationRodScanRange = builder.comment("The scan range in blocks for the divination rod. Too high might cause lags")
                    .defineInRange("divinationRodScanRange", 129, 1, Integer.MAX_VALUE);
            this.disableSpiritFireSuccessSound = builder.comment(
                            "Disables the sound played when a spirit fire successfully crafted an item."
                    )
                    .define("disableSpiritFireSuccessSound", false);
            this.pentagramInBowlInfoCount = builder.comment(
                    "How many pentagrams to show at one time on the bowl information"
            ).defineInRange("pentagramInBowlInfoCount", 3, 1, Integer.MAX_VALUE);
            this.pentagramInBowlInfoTicks = builder.comment(
                    "How many ticks between each page for the pentagram information on a bowl"
            ).defineInRange("pentagramInBowlInfoTicks", 40, 1, Integer.MAX_VALUE);
            builder.pop();
        }
    }
}
