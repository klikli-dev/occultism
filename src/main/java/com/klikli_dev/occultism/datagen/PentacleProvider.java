/*
 * MIT License
 *
 * Copyright 2021 vemerion
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

package com.klikli_dev.occultism.datagen;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.klikli_dev.modonomicon.data.MultiblockDataManager;
import com.klikli_dev.occultism.Occultism;
import com.klikli_dev.occultism.registry.OccultismBlocks;
import com.klikli_dev.occultism.registry.OccultismTags;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.Tags;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

public class PentacleProvider implements DataProvider {

    private static final Logger LOGGER = LogManager.getLogger();

    private final Map<String, JsonElement> toSerialize = new HashMap<>();
    private final DataGenerator generator;

    public PentacleProvider(DataGenerator generator) {
        this.generator = generator;
    }

    private void start() {
        //Summon
        this.addPentacle("summon_foliot",
                this.createPattern(
                        "_________",
                        "___WWW___",
                        "__W_W_W__",
                        "_W_1W1_W_",
                        "_WWW0WWW_",
                        "_W_1W1_W_",
                        "__W_W_W__",
                        "___WWW___",
                        "_________"),
                new MappingBuilder().bowl().whiteChalk().candle().ground().build());
        this.addPentacle("summon_djinni",
                this.createPattern(
                        "_____________",
                        "____LLLLL____",
                        "___L_L1L_L___",
                        "__L2_WWW_2L__",
                        "_L__W_S_W__L_",
                        "_LLW_1S1_WLL_",
                        "_L1WSS0SSW1L_",
                        "_LLW_1S1_WLL_",
                        "_L__W_S_W__L_",
                        "__L2_WWW_2L__",
                        "___L_L1L_L___",
                        "____LLLLL____",
                        "_____________"),
                new MappingBuilder().bowl().whiteChalk().lightGrayChalk().limeChalk().candle().skeleton().ground().build());
        this.addPentacle("summon_unbound_afrit",
                this.createPattern(
                        "_________________",
                        "______OOOOO______",
                        "____OO__2__OO____",
                        "___O__LLLLL__O___",
                        "__O__L_L1L_L__O__",
                        "__O_L2_WWW_2L_O__",
                        "_O_L__W_G_W__L_O_",
                        "_O_LLW_1G1_WLL_O_",
                        "_O2L1WGG0GGW1L2O_",
                        "_O_LLW_1G1_WLL_O_",
                        "_O_L__W_G_W__L_O_",
                        "__O_L2_WWW_2L_O__",
                        "__O__L_L1L_L__O__",
                        "___O__LLLLL__O___",
                        "____OO__2__OO____",
                        "______OOOOO______",
                        "_________________"),
                new MappingBuilder().bowl().whiteChalk().grayChalk().limeChalk().orangeChalk().candle().skeleton().crystal().ground().build());
        this.addPentacle("summon_afrit",
                this.createPattern(
                        "_________________",
                        "______OOOOO______",
                        "____OOR_2_ROO____",
                        "___OR_LLLLL_RO___",
                        "__OR3L_L1L_L3RO__",
                        "__O_L2_WWW_2L_O__",
                        "_ORL__W_G_W__LRO_",
                        "_O_LLW_1G1_WLL_O_",
                        "_O2L1WGG0GGW1L2O_",
                        "_O_LLW_1G1_WLL_O_",
                        "_ORL__W_G_W__LRO_",
                        "__O_L2_WWW_2L_O__",
                        "__OR3L_L1L_L3RO__",
                        "___OR_LLLLL_RO___",
                        "____OOR_2_ROO____",
                        "______OOOOO______",
                        "_________________"),
                new MappingBuilder().bowl().whiteChalk().grayChalk().limeChalk().orangeChalk().redChalk().candle().skeleton().crystal().ground().build());
        this.addPentacle("summon_unbound_marid",
                this.createPattern(
                        "_________________",
                        "______OOOOO______",
                        "__4_OOR_2_ROO_4__",
                        "___OR_LLLLL_RO___",
                        "__OR3L_L1L_L3RO__",
                        "__O_L2_WWW_2L_O__",
                        "_ORL__W_K_W__LRO_",
                        "_O_LLW_1K1_WLL_O_",
                        "_O2L1WKK0KKW1L2O_",
                        "_O_LLW_1K1_WLL_O_",
                        "_ORL__W_K_W__LRO_",
                        "__O_L2_WWW_2L_O__",
                        "__OR3L_L1L_L3RO__",
                        "___OR_LLLLL_RO___",
                        "__4_OOR_2_ROO_4__",
                        "______OOOOO______",
                        "_________________"),
                new MappingBuilder().bowl().whiteChalk().blackChalk().limeChalk().orangeChalk().redChalk().candle().skeleton().crystal().wither().ground().build());
        this.addPentacle("summon_marid",
                this.createPattern(
                        "_____________________",
                        "_______UUUUUUU_______",
                        "_____UU__U3U__UU_____",
                        "____U___OOOOO___U____",
                        "___U4_OOR_2_ROO_4U___",
                        "__U__OR_LLLLL_RO__U__",
                        "__U_OR3L_L1L_L3RO_U__",
                        "_U__O_L2_WWW_2L_O__U_",
                        "_U_ORL__W_K_W__LRO_U_",
                        "_UUO_LLW_1K1_WLL_OUU_",
                        "_U3O2L1WKK0KKW1L2O3U_",
                        "_UUO_LLW_1K1_WLL_OUU_",
                        "_U_ORL__W_K_W__LRO_U_",
                        "_U__O_L2_WWW_2L_O__U_",
                        "__U_OR3L_L1L_L3RO_U__",
                        "__U__OR_LLLLL_RO__U__",
                        "___U4_OOR_2_ROO_4U___",
                        "____U___OOOOO___U____",
                        "_____UU__U3U__UU_____",
                        "_______UUUUUUU_______",
                        "_____________________"),
                new MappingBuilder().bowl().whiteChalk().blackChalk().limeChalk().orangeChalk().redChalk().blueChalk().candle().skeleton().crystal().wither().ground().build());
        //Possess
        this.addPentacle("possess_foliot",
                this.createPattern(
                        "_________",
                        "____W____",
                        "___W1W___",
                        "__W_Y_W__",
                        "_W1Y0Y1W_",
                        "__W_Y_W__",
                        "___W1W___",
                        "____W____",
                        "_________"),
                new MappingBuilder().bowl().whiteChalk().yellowChalk().candle().ground().build());
        this.addPentacle("possess_djinni",
                this.createPattern(
                        "_____________",
                        "______L______",
                        "_____L2L_____",
                        "____Y_W_Y____",
                        "___Y1S1S1Y___",
                        "__L_S_Y_S_L__",
                        "_L2W1Y0Y1W2L_",
                        "__L_S_Y_S_L__",
                        "___Y1S1S1Y___",
                        "____Y_W_Y____",
                        "_____L2L_____",
                        "______L______",
                        "_____________"),
                new MappingBuilder().bowl().whiteChalk().lightGrayChalk().yellowChalk().limeChalk().candle().skeleton().ground().build());
        this.addPentacle("possess_unbound_afrit",
                this.createPattern(
                        "_________________",
                        "________Y________",
                        "_______Y3Y_______",
                        "________L________",
                        "_____O_L2L_O_____",
                        "____O2Y_W_Y2O____",
                        "_____Y1G1G1Y_____",
                        "__Y_L_G_Y_G_L_Y__",
                        "_Y3L2W1Y0Y1W2L3Y_",
                        "__Y_L_G_Y_G_L_Y__",
                        "_____Y1G1G1Y_____",
                        "____O2Y_W_Y2O____",
                        "_____O_L2L_O_____",
                        "________L________",
                        "_______Y3Y_______",
                        "________Y________",
                        "_________________"),
                new MappingBuilder().bowl().whiteChalk().grayChalk().yellowChalk().limeChalk().orangeChalk().candle().skeleton().crystal().ground().build());
        this.addPentacle("possess_afrit",
                this.createPattern(
                        "_________________",
                        "________Y________",
                        "_______Y3Y_______",
                        "______R_L_R______",
                        "_____O_L2L_O_____",
                        "____O2Y_W_Y2O____",
                        "___R_Y1G1G1Y_R___",
                        "__Y_L_G_Y_G_L_Y__",
                        "_Y3L2W1Y0Y1W2L3Y_",
                        "__Y_L_G_Y_G_L_Y__",
                        "___R_Y1G1G1Y_R___",
                        "____O2Y_W_Y2O____",
                        "_____O_L2L_O_____",
                        "______R_L_R______",
                        "_______Y3Y_______",
                        "________Y________",
                        "_________________"),
                new MappingBuilder().bowl().whiteChalk().grayChalk().yellowChalk().limeChalk().orangeChalk().redChalk().candle().skeleton().crystal().ground().build());
        this.addPentacle("possess_marid",
                this.createPattern(
                        "_____________________",
                        "__________U__________",
                        "_________Y4Y_________",
                        "________U_Y_U________",
                        "_______Y_Y3Y_Y_______",
                        "______U_R_L_R_U______",
                        "_____U3O_L2L_O3U_____",
                        "____Y_O2Y_W_Y2O_Y____",
                        "___U_R_Y1K1K1Y_R_U___",
                        "__Y_Y_L_K_Y_K_L_Y_Y__",
                        "_U4Y3L2W1Y0Y1W2L3Y4U_",
                        "__Y_Y_L_K_Y_K_L_Y_Y__",
                        "___U_R_Y1K1K1Y_R_U___",
                        "____Y_O2Y_W_Y2O_Y____",
                        "_____U3O_L2L_O3U_____",
                        "______U_R_L_R_U______",
                        "_______Y_Y3Y_Y_______",
                        "________U_Y_U________",
                        "_________Y4Y_________",
                        "__________U__________",
                        "_____________________"),
                new MappingBuilder().bowl().whiteChalk().blackChalk().yellowChalk().limeChalk().orangeChalk().redChalk().blueChalk().candle().skeleton().crystal().wither().ground().build());
        //Craft
        this.addPentacle("craft_foliot",
                this.createPattern(
                        "_________",
                        "__WXXXW__",
                        "_WW1_1WW_",
                        "_X1W_W1X_",
                        "_X__0__X_",
                        "_X1W_W1X_",
                        "_WW1_1WW_",
                        "__WXXXW__",
                        "_________"),
                new MappingBuilder().bowl().whiteChalk().purpleChalk().candle().ground().build());
        this.addPentacle("craft_djinni",
                this.createPattern(
                        "_____________",
                        "__XLXLXLXLX__",
                        "_XL_2___2_LX_",
                        "_L__WXXXW__L_",
                        "_X2WS1_1SW2X_",
                        "_L_X1S_S1X_L_",
                        "_X_X__0__X_X_",
                        "_L_X1S_S1X_L_",
                        "_X2WS1_1SW2X_",
                        "_L__WXXXW__L_",
                        "_XL_2___2_LX_",
                        "__XLXLXLXLX__",
                        "_____________"),
                new MappingBuilder().bowl().whiteChalk().lightGrayChalk().purpleChalk().limeChalk().candle().skeleton().ground().build());
        this.addPentacle("craft_afrit",
                this.createPattern(
                        "_________________",
                        "__RXOXOXRXOXOXR__",
                        "_RX____3_3____XR_",
                        "_X__XLXLXLXLX__X_",
                        "_O_XL_2___2_LX_O_",
                        "_X_L__WXXXW__L_X_",
                        "_O_X2WG1_1GW2X_O_",
                        "_X3L_X1G_G1X_L3X_",
                        "_R_X_X__0__X_X_R_",
                        "_X3L_X1G_G1X_L3X_",
                        "_O_X2WG1_1GW2X_O_",
                        "_X_L__WXXXW__L_X_",
                        "_O_XL_2___2_LX_O_",
                        "_X__XLXLXLXLX__X_",
                        "_RX____3_3____XR_",
                        "__RXOXOXRXOXOXR__",
                        "_________________"),
                new MappingBuilder().bowl().whiteChalk().grayChalk().purpleChalk().limeChalk().orangeChalk().redChalk().candle().skeleton().crystal().ground().build());
        this.addPentacle("craft_marid",
                this.createPattern(
                        "_____________________",
                        "__XXUUUXUUUUUXUUUXX__",
                        "_XX_______4_______XX_",
                        "_X__RXOXOXRXOXOXR__X_",
                        "_U_RX____3_3____XR_U_",
                        "_U_X__XLXLXLXLX__X_U_",
                        "_U_O_XL_2___2_LX_O_U_",
                        "_X_X_L__WXXXW__L_X_X_",
                        "_U_O_X2WK1_1KW2X_O_U_",
                        "_U_X3L_X1K_K1X_L3X_U_",
                        "_U4R_X_X__0__X_X_R4U_",
                        "_U_X3L_X1K_K1X_L3X_U_",
                        "_U_O_X2WK1_1KW2X_O_U_",
                        "_X_X_L__WXXXW__L_X_X_",
                        "_U_O_XL_2___2_LX_O_U_",
                        "_U_X__XLXLXLXLX__X_U_",
                        "_U_RX____3_3____XR_U_",
                        "_X__RXOXOXRXOXOXR__X_",
                        "_XX_______4_______XX_",
                        "__XXUUUXUUUUUXUUUXX__",
                        "_____________________"),
                new MappingBuilder().bowl().whiteChalk().blackChalk().purpleChalk().limeChalk().orangeChalk().redChalk().blueChalk().candle().skeleton().crystal().wither().ground().build());
        //Others
        this.addPentacle("resurrect_spirit",
                this.createPattern(
                        "___________",
                        "_____W_____",
                        "__W_____W__",
                        "___________",
                        "____WWW____",
                        "_W__W0W__W_",
                        "____WWW____",
                        "___________",
                        "__W_____W__",
                        "_____W_____",
                        "___________"),
                new MappingBuilder().bowl().whiteChalk().ground().build());
       this.addPentacle("contact_wild_spirit",
                this.createPattern(
                        "_____________",
                        "______P______",
                        "__A_AE_EA_A__",
                        "____PE_EP____",
                        "__APA_P_APA__",
                        "__EE_____EE__",
                        "_P__P_0_P__P_",
                        "__EE_____EE__",
                        "__APA_P_APA__",
                        "____PE_EP____",
                        "__A_AE_EA_A__",
                        "______P______",
                        "_____________"),
                new MappingBuilder().bowl().pinkChalk().greenChalk().lightBlueChalk().ground().build());
        this.addPentacle("contact_eldritch_spirit",
                this.createPattern(
                        "_____________________",
                        "__________C__________",
                        "_________CeC_________",
                        "_________CMC_________",
                        "____BB__CMMMC__BB____",
                        "____BlBBCM_MCBBfB____",
                        "_____B_B_____B_B_____",
                        "_____BB_______BB_____",
                        "____CC_________CC____",
                        "__CCMM_________MMCC__",
                        "_CkMM_____0_____MMgC_",
                        "__CCMM_________MMCC__",
                        "____CC_________CC____",
                        "_____BB_______BB_____",
                        "_____B_B_____B_B_____",
                        "____BjBBCM_MCBBhB____",
                        "____BB__CMMMC__BB____",
                        "_________CMC_________",
                        "_________CiC_________",
                        "__________C__________",
                        "_____________________"),
                new MappingBuilder().bowl().magentaChalk().brownChalk().cyanChalk().eldritch().ground().build());
        this.addPentacle("debug",
                this.createPattern(
                        "  Y1Y  ",
                        " Y X Y ",
                        "Y  X  Y",
                        "1XX0XX1",
                        "Y  X  Y",
                        " Y X Y ",
                        "  Y1Y  "),
                new MappingBuilder().bowl().yellowChalk().purpleChalk().candle().skeleton().eldritch().ground().build());
    }

    private List<String> createPattern(String... rows) {
        List<String> pattern = new ArrayList<>();
        for (String row : rows) {
            pattern.add(row.replace(" ", "_"));
        }
        return pattern;
    }

    private void addPentacle(String name, List<String> pattern, Map<Character, JsonElement> mappings) {
        this.addPentacle(ResourceLocation.fromNamespaceAndPath(Occultism.MODID, name), pattern, mappings);
    }

    private void addPentacle(ResourceLocation rl, List<String> pattern, Map<Character, JsonElement> mappings) {
        JsonObject json = new JsonObject();

        json.addProperty("type", "modonomicon:dense");

        JsonArray outerPattern = new JsonArray();
        JsonArray innerPattern = new JsonArray();
        for (String row : pattern)
            innerPattern.add(row);
        outerPattern.add(innerPattern); //modonomicon multiblocks may have multiple layers, but we use only one

        JsonArray ground = new JsonArray();
        for (int i = 0; i < pattern.size(); i++) {
            var row = "";
            for (int j = 0; j < pattern.get(i).length(); j++) {
                //create a checkerbord, alternatively adding "*" and "+" to the row
                if ((i + j) % 2 == 0)
                    row += "*";
                else
                    row += "+";
            }
            ground.add(row);
        }
        outerPattern.add(ground);

        json.add("pattern", outerPattern);

        JsonObject jsonMapping = new JsonObject();
        for (Entry<Character, JsonElement> entry : mappings.entrySet())
            jsonMapping.add(String.valueOf(entry.getKey()), entry.getValue());
        json.add("mapping", jsonMapping);

        this.toSerialize.put(rl.getPath(), json);
    }

    @Override
    public CompletableFuture<?> run(CachedOutput cache) {
        List<CompletableFuture<?>> futures = new ArrayList<>();

        Path folder = this.generator.getPackOutput().getOutputFolder();

        this.start();

        this.toSerialize.forEach((name, json) -> {
            Path path = folder.resolve("data/" + Occultism.MODID + "/" + MultiblockDataManager.FOLDER + "/" + name + ".json");
            futures.add(DataProvider.saveStable(cache, json, path));
        });

        return CompletableFuture.allOf(futures.toArray(CompletableFuture[]::new));
    }

    @Override
    public String getName() {
        return "Pentacles: " + Occultism.MODID;
    }

    private static class MappingBuilder {
        private final Map<Character, JsonElement> mappings = new HashMap<>();

        public MappingBuilder() {
            this.ground(); //always map ground
        }

        private MappingBuilder element(char c, JsonElement e) {
            this.mappings.put(c, e);
            return this;
        }

        private Map<Character, JsonElement> build() {
            return this.mappings;
        }

        private MappingBuilder block(char c, Supplier<? extends Block> b) {

            JsonObject json = new JsonObject();
            json.addProperty("type", "modonomicon:block");
            json.addProperty("block", BuiltInRegistries.BLOCK.getKey(b.get()).toString());
            return this.element(c, json);
        }

        private MappingBuilder blockDisplay(char c, Supplier<? extends Block> b, Supplier<? extends Block> display) {
            JsonObject json = new JsonObject();
            json.addProperty("type", "modonomicon:block");
            json.addProperty("block", BuiltInRegistries.BLOCK.getKey(b.get()).toString());
            json.addProperty("display", BuiltInRegistries.BLOCK.getKey(display.get()).toString());
            return this.element(c, json);
        }
        private MappingBuilder tagDisplay(char c, TagKey<Block> tag, Supplier<? extends Block> display) {
            JsonObject json = new JsonObject();
            json.addProperty("type", "modonomicon:tag");
            json.addProperty("tag", "#" + tag.location());
            json.addProperty("display", BuiltInRegistries.BLOCK.getKey(display.get()).toString());
            return this.element(c, json);
        }

        private MappingBuilder display(char c, Supplier<? extends Block> display) {
            JsonObject json = new JsonObject();
            json.addProperty("type", "modonomicon:display");
            json.addProperty("display", BuiltInRegistries.BLOCK.getKey(display.get()).toString());
            return this.element(c, json);
        }

        private MappingBuilder tag(char c, TagKey<Block> tag) {
            JsonObject json = new JsonObject();
            json.addProperty("type", "modonomicon:tag");
            json.addProperty("tag", "#" + tag.location());
            return this.element(c, json);
        }

        private MappingBuilder bowl() {
            return this.tagDisplay('0', OccultismTags.Blocks.CENTER_SACRIFICIAL_BOWL, OccultismBlocks.GOLDEN_SACRIFICIAL_BOWL);
        }
        private MappingBuilder candle() {
            return this.tag('1', OccultismTags.Blocks.CANDLES);
        }
        private MappingBuilder skeleton() {
            return this.block('2', () -> Blocks.SKELETON_SKULL);
        }
        private MappingBuilder crystal() {
            return this.block('3', OccultismBlocks.SPIRIT_ATTUNED_CRYSTAL);
        }
        private MappingBuilder wither() {
            return this.block('4', () -> Blocks.WITHER_SKELETON_SKULL);
        }

        private MappingBuilder whiteChalk() {
            return this.tagDisplay('W', OccultismTags.Blocks.FOUNDATION_GLYPHS_ANY, OccultismBlocks.CHALK_GLYPH_WHITE);
        }
        private MappingBuilder lightGrayChalk() {
            return this.tagDisplay('S', OccultismTags.Blocks.FOUNDATION_GLYPHS_NO_WHITE, OccultismBlocks.CHALK_GLYPH_LIGHT_GRAY);
        }
        private MappingBuilder grayChalk() {
            return this.tagDisplay('G', OccultismTags.Blocks.FOUNDATION_GLYPHS_DARK, OccultismBlocks.CHALK_GLYPH_GRAY);
        }
        private MappingBuilder blackChalk() {
            return this.tagDisplay('K', OccultismTags.Blocks.GLYPHS_BLACK, OccultismBlocks.CHALK_GLYPH_BLACK);
        }
        private MappingBuilder brownChalk() {
            return this.tagDisplay('B', OccultismTags.Blocks.GLYPHS_BROWN, OccultismBlocks.CHALK_GLYPH_BROWN);
        }
        private MappingBuilder redChalk() {
            return this.tagDisplay('R', OccultismTags.Blocks.GLYPHS_RED, OccultismBlocks.CHALK_GLYPH_RED);
        }
        private MappingBuilder orangeChalk() {
            return this.tagDisplay('O', OccultismTags.Blocks.GLYPHS_ORANGE, OccultismBlocks.CHALK_GLYPH_ORANGE);
        }
        private MappingBuilder yellowChalk() {
            return this.tagDisplay('Y', OccultismTags.Blocks.GLYPHS_YELLOW, OccultismBlocks.CHALK_GLYPH_YELLOW);
        }
        private MappingBuilder limeChalk() {
            return this.tagDisplay('L', OccultismTags.Blocks.GLYPHS_LIME, OccultismBlocks.CHALK_GLYPH_LIME);
        }
        private MappingBuilder greenChalk() {
            return this.tagDisplay('E', OccultismTags.Blocks.GLYPHS_GREEN, OccultismBlocks.CHALK_GLYPH_GREEN);
        }
        private MappingBuilder cyanChalk() {
            return this.tagDisplay('C', OccultismTags.Blocks.GLYPHS_CYAN, OccultismBlocks.CHALK_GLYPH_CYAN);
        }
        private MappingBuilder lightBlueChalk() {
            return this.tagDisplay('A', OccultismTags.Blocks.GLYPHS_LIGHT_BLUE, OccultismBlocks.CHALK_GLYPH_LIGHT_BLUE);
        }
        private MappingBuilder blueChalk() {
            return this.tagDisplay('U', OccultismTags.Blocks.GLYPHS_BLUE, OccultismBlocks.CHALK_GLYPH_BLUE);
        }
        private MappingBuilder purpleChalk() {
            return this.tagDisplay('X', OccultismTags.Blocks.GLYPHS_PURPLE, OccultismBlocks.CHALK_GLYPH_PURPLE);
        }
        private MappingBuilder magentaChalk() {
            return this.tagDisplay('M', OccultismTags.Blocks.GLYPHS_MAGENTA, OccultismBlocks.CHALK_GLYPH_MAGENTA);
        }
        private MappingBuilder pinkChalk() {
            return this.tagDisplay('P', OccultismTags.Blocks.GLYPHS_PINK, OccultismBlocks.CHALK_GLYPH_PINK);
        }

        private MappingBuilder eldritch(){
            return this.block('e', () -> Blocks.LODESTONE)
                    .block('f', () -> Blocks.END_ROD)
                    .block('g', () ->Blocks.AMETHYST_CLUSTER)
                    .tag('h', Tags.Blocks.GLASS_PANES)
                    .block('i', () -> Blocks.BEACON)
                    .block('j', () -> Blocks.LIGHTNING_ROD)
                    .block('k', () -> Blocks.ENCHANTING_TABLE)
                    .block('l', () -> Blocks.IRON_BARS);
        }

        private MappingBuilder ground() {
            return this.display('*', OccultismBlocks.OTHERSTONE).display('+', () -> Blocks.STONE);
        }


    }

}
