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
        this.addPentacle("craft_afrit",
                this.createPattern(
                        "     N     ",
                        "    GGG    ",
                        "   GCPCG   ",
                        "  G WCW G  ",
                        " GCWW WWCG ",
                        "ZGPC 0 CPGZ",
                        " GCWW WWCG ",
                        "  G WCW G  ",
                        "   GCPCG   ",
                        "    GGG    ",
                        "     Z     "),
                new MappingBuilder().bowl().whiteChalk().goldChalk().purpleChalk().redChalk().candle().crystal().skeleton().wither().ground().build());
        this.addPentacle("craft_djinni",
                this.createPattern(
                        "         ",
                        " C WGW C ",
                        "  P W P  ",
                        " W SWS W ",
                        " GWW0WWG ",
                        " W SWS W ",
                        "  P W P  ",
                        " C WGW C ",
                        "         "),
                new MappingBuilder().bowl().whiteChalk().goldChalk().purpleChalk().candle().crystal().ground().build());
        this.addPentacle("craft_foliot",
                this.createPattern(
                        "  WSW  ",
                        " G   G ",
                        "W  W  W",
                        "C W0W C",
                        "W  W  W",
                        " G   G ",
                        "  WSW  "),
                new MappingBuilder().bowl().whiteChalk().goldChalk().candle().crystal().ground().build());
        this.addPentacle("craft_marid",
                this.createPattern(
                        "       Z       ",
                        "      RRR      ",
                        "     RCWCR     ",
                        "    R  W  R    ",
                        "   RGSWNWSGR   ",
                        "  R SGW WGS R  ",
                        " RC WW   WW CR ",
                        "ZRWWN  0  NWWRZ",
                        " RC WW   WW CR ",
                        "  R SGW WGS R  ",
                        "   RGSWNWSGR   ",
                        "    R  W  R    ",
                        "     RCWCR     ",
                        "      RRR      ",
                        "       Z       "),
                new MappingBuilder().bowl().whiteChalk().goldChalk().redChalk().candle().crystal().skeleton().wither().ground().build());
        this.addPentacle("debug",
                this.createPattern(
                        "  GCG  ",
                        " G P G ",
                        "G  P  G",
                        "CPP0PPC",
                        "G  P  G",
                        " G P G ",
                        "  GCG  "),
                new MappingBuilder().bowl().goldChalk().purpleChalk().candle().skeleton().ground().build());
        this.addPentacle("possess_afrit",
                this.createPattern(
                        "           ",
                        "    PRP    ",
                        "   GCWCG   ",
                        "  G WNW G  ",
                        " PCWP PWCP ",
                        " RWZ 0 ZWR ",
                        " PCWP PWCP ",
                        "  G WNW G  ",
                        "   GCWCG   ",
                        "    PRP    ",
                        "           "),
                new MappingBuilder().bowl().whiteChalk().goldChalk().purpleChalk().redChalk().candle().skeleton().wither().ground().build());
        this.addPentacle("possess_djinni",
                this.createPattern(
                        "   GPG   ",
                        "  GC CG  ",
                        " GZW WZG ",
                        "GCWP PWCG",
                        "P   0   P",
                        "GCWP PWCG",
                        " GZW WZG ",
                        "  GC CG  ",
                        "   GPG   "),
                new MappingBuilder().bowl().whiteChalk().goldChalk().purpleChalk().candle().skeleton().ground().build());
        this.addPentacle("possess_foliot",
                this.createPattern(
                        "   GGG   ",
                        "  GC CG  ",
                        " GW   WG ",
                        "GC W W CG",
                        "G   0   G",
                        "GC W W CG",
                        " GW   WG ",
                        "  GC CG  ",
                        "   GGG   "),
                new MappingBuilder().bowl().whiteChalk().goldChalk().candle().ground().build());
        this.addPentacle("summon_afrit",
                this.createPattern(
                        "           ",
                        "    PRP    ",
                        "   WCWCW   ",
                        "  W WNW W  ",
                        " PCWP PWCP ",
                        " RWZ 0 ZWR ",
                        " PCWP PWCP ",
                        "  W WNW W  ",
                        "   WCWCW   ",
                        "    PRP    ",
                        "           "),
                new MappingBuilder().bowl().whiteChalk().purpleChalk().redChalk().candle().skeleton().wither().ground().build());
        this.addPentacle("summon_djinni",
                this.createPattern(
                        "   C C   ",
                        "   PPP   ",
                        "  W Z W  ",
                        "CP W W PC",
                        " PZ 0 ZP ",
                        "CP W W PC",
                        "  W Z W  ",
                        "   PPP   ",
                        "   C C   "),
                new MappingBuilder().bowl().whiteChalk().purpleChalk().candle().skeleton().ground().build());
        this.addPentacle("summon_marid",
                this.createPattern(
                        "       Z       ",
                        "      RRR      ",
                        "     RCWCR     ",
                        "    R  W  R    ",
                        "   RG WNW GR   ",
                        "  R  GW WG  R  ",
                        " RC WW   WW CR ",
                        "ZRWWN  0  NWWRZ",
                        " RC WW   WW CR ",
                        "  R  GW WG  R  ",
                        "   RG WNW GR   ",
                        "    R  W  R    ",
                        "     RCWCR     ",
                        "      RRR      ",
                        "       Z       "),
                new MappingBuilder().bowl().whiteChalk().goldChalk().redChalk().candle().skeleton().wither().ground().build());
        this.addPentacle("summon_foliot",
                this.createPattern(
                        "         ",
                        "   WCW   ",
                        "  W W W  ",
                        " W  W  W ",
                        " CWW0WWC ",
                        " W  W  W ",
                        "  W W W  ",
                        "   WCW   ",
                        "         "),
                new MappingBuilder().bowl().whiteChalk().candle().ground().build());
        this.addPentacle("summon_wild_afrit",
                this.createPattern(
                        "           ",
                        "    PPP    ",
                        "   WCWCW   ",
                        "  W WNW W  ",
                        " PCWP PWCP ",
                        " PWZ 0 ZWP ",
                        " PCWP PWCP ",
                        "  W WNW W  ",
                        "   WCWCW   ",
                        "    PPP    ",
                        "           "),
                new MappingBuilder().bowl().whiteChalk().purpleChalk().candle().skeleton().wither().ground().build());
        this.addPentacle("summon_wild_greater_spirit",
                this.createPattern(
                        "           ",
                        "    PPP    ",
                        "   W W W   ",
                        "  W WZW W  ",
                        " P WP PW P ",
                        " PWZ 0 ZWP ",
                        " P WP PW P ",
                        "  W WZW W  ",
                        "   W W W   ",
                        "    PPP    ",
                        "           "),
                new MappingBuilder().bowl().whiteChalk().purpleChalk().skeleton().ground().build());
    }

    private List<String> createPattern(String... rows) {
        List<String> pattern = new ArrayList<>();
        for (String row : rows) {
            pattern.add(row.replace(" ", "_"));
        }
        return pattern;
    }

    private void addPentacle(String name, List<String> pattern, Map<Character, JsonElement> mappings) {
        this.addPentacle(new ResourceLocation(Occultism.MODID, name), pattern, mappings);
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
            return this.block('0', OccultismBlocks.GOLDEN_SACRIFICIAL_BOWL);
        }

        private MappingBuilder candle() {
            return this.tag('C', OccultismTags.CANDLES);
        }

        private MappingBuilder whiteChalk() {
            return this.block('W', OccultismBlocks.CHALK_GLYPH_WHITE);
        }

        private MappingBuilder goldChalk() {
            return this.block('G', OccultismBlocks.CHALK_GLYPH_GOLD);
        }

        private MappingBuilder purpleChalk() {
            return this.block('P', OccultismBlocks.CHALK_GLYPH_PURPLE);
        }

        private MappingBuilder redChalk() {
            return this.block('R', OccultismBlocks.CHALK_GLYPH_RED);
        }

        private MappingBuilder crystal() {
            return this.block('S', OccultismBlocks.SPIRIT_ATTUNED_CRYSTAL);
        }

        private MappingBuilder skeleton() {
            return this.block('Z', () -> Blocks.SKELETON_SKULL);
        }

        private MappingBuilder ground() {
            return this.display('*', OccultismBlocks.OTHERSTONE).display('+', () -> Blocks.STONE);
        }

        private MappingBuilder wither() {
            return this.block('N', () -> Blocks.WITHER_SKELETON_SKULL);
        }
    }

}
