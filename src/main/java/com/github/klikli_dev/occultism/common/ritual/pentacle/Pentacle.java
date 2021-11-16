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

package com.github.klikli_dev.occultism.common.ritual.pentacle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.github.klikli_dev.occultism.common.block.ChalkGlyphBlock;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tags.ITag;
import net.minecraft.tags.TagCollectionManager;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.common.util.TriPredicate;
import net.minecraftforge.registries.ForgeRegistries;
import vazkii.patchouli.api.IMultiblock;
import vazkii.patchouli.api.IStateMatcher;
import vazkii.patchouli.api.PatchouliAPI;
import vazkii.patchouli.api.PatchouliAPI.IPatchouliAPI;

public class Pentacle {
    private final ResourceLocation rl;
    private final List<String> pattern;
    private final Map<Character, JsonElement> mappings;
    private final IMultiblock matcher;

    public Pentacle(ResourceLocation rl, List<String> pattern, Map<Character, JsonElement> mappings) {
        this.rl = rl;
        this.pattern = pattern;
        this.mappings = mappings;
        for (String r1 : pattern) {
            for (String r2 : pattern) {
                if (r1.length() != r2.length())
                    throw new IllegalArgumentException("Pentacle pattern can not have rows with different lengths");
            }
        }
        for (String row : pattern) {
            for (char c : row.toCharArray()) {
                if (c != ' ' && !mappings.containsKey(c))
                    throw new IllegalArgumentException("Pentacle mappings is missing " + c);
            }
        }
        IPatchouliAPI api = PatchouliAPI.get();
        String[][] multiPattern = new String[1][pattern.size()];
        for (int i = 0; i < pattern.size(); i++)
            multiPattern[0][i] = pattern.get(pattern.size() - 1 - i);
        List<Object> multiMappings = new ArrayList<>();
        for (Entry<Character, JsonElement> entry : mappings.entrySet()) {
            multiMappings.add(entry.getKey());
            multiMappings.add(parseStateMatcher(entry.getValue()));
        }
        // Space == whatever
        multiMappings.add(' ');
        multiMappings.add(api.anyMatcher());

        ResourceLocation multiRL = new ResourceLocation(rl.getNamespace(), "pentacle." + rl.getPath());
        this.matcher = api.makeMultiblock(multiPattern, multiMappings.toArray());
        this.matcher.setId(multiRL);
        try {
            PatchouliAPI.get().registerMultiblock(multiRL, this.matcher);
        } catch (IllegalArgumentException e) { // Patchouli weirdness
        }
    }

    public static Pentacle fromJson(ResourceLocation rl, JsonObject json) {
        JsonArray jsonPattern = JSONUtils.getAsJsonArray(json, "pattern");
        JsonObject jsonMapping = JSONUtils.getAsJsonObject(json, "mapping");
        List<String> pattern = new ArrayList<>();
        Map<Character, JsonElement> mappings = new HashMap<>();
        for (int i = 0; i < jsonPattern.size(); i++)
            pattern.add(JSONUtils.convertToString(jsonPattern.get(i), "row"));

        for (Entry<String, JsonElement> entry : jsonMapping.entrySet()) {
            if (entry.getKey().length() != 1)
                throw new JsonSyntaxException("Mapping key needs to be only 1 character");
            char key = entry.getKey().charAt(0);
            mappings.put(key, entry.getValue());
        }
        return new Pentacle(rl, pattern, mappings);
    }

    public static IStateMatcher parseStateMatcher(JsonElement matcher) {
        if (matcher.isJsonObject()) {
            JsonObject jsonObject = matcher.getAsJsonObject();
            Block display = null;
            if (jsonObject.has("display")) {
                ResourceLocation displayRL = new ResourceLocation(JSONUtils.getAsString(jsonObject, "display"));
                display = ForgeRegistries.BLOCKS.getValue(displayRL);
                if (display == null)
                    throw new JsonSyntaxException("Invalid display" + displayRL);
            }
            if (jsonObject.has("block")) {
                ResourceLocation blockRL = new ResourceLocation(JSONUtils.getAsString(jsonObject, "block"));
                Block block = ForgeRegistries.BLOCKS.getValue(blockRL);
                if (block == null)
                    throw new JsonSyntaxException("Invalid block " + blockRL);

                if (display != null) {
                    return OM(PatchouliAPI.get().predicateMatcher(display, s -> s.getBlock() == block), block);
                } else {
                    return OM(PatchouliAPI.get().looseBlockMatcher(block), block);
                }
            } else if (jsonObject.has("tag")) {
                ResourceLocation tagRL = new ResourceLocation(JSONUtils.getAsString(jsonObject, "tag"));
                ITag<Block> tag = TagCollectionManager.getInstance().getBlocks().getTag(tagRL);
                if (tag == null)
                    throw new JsonSyntaxException("Invalid tag " + tagRL);
                if (display == null)
                    throw new JsonSyntaxException("No display set for tag " + tagRL);
                return OM(PatchouliAPI.get().predicateMatcher(display, s -> tag.contains(s.getBlock())));
            } else if (display != null) {
                return OM(PatchouliAPI.get().displayOnlyMatcher(display));
            }
        }

        // if it's a primitive we assume it's a block
        ResourceLocation blockRL = new ResourceLocation(matcher.getAsString());
        Block block = ForgeRegistries.BLOCKS.getValue(blockRL);
        if (block == null)
            throw new JsonSyntaxException("Invalid block " + blockRL);
        return OM(PatchouliAPI.get().looseBlockMatcher(block), block);
    }

    public static Pentacle decode(ResourceLocation key, PacketBuffer buffer) {
        List<String> pattern = new ArrayList<>();
        Map<Character, JsonElement> mappings = new HashMap<>();
        int size = buffer.readInt();
        for (int i = 0; i < size; i++)
            pattern.add(buffer.readUtf());
        size = buffer.readInt();
        JsonParser parser = new JsonParser();
        for (int i = 0; i < size; i++)
            mappings.put(buffer.readChar(), parser.parse(buffer.readUtf()));
        return new Pentacle(key, pattern, mappings);
    }

    public String getTranslationKey() {
        return Util.makeDescriptionId("pentacle", this.rl);
    }

    public boolean validate(World world, BlockPos pos) {
        return this.matcher.validate(world, pos) != null;
    }

    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        JsonArray jsonPattern = new JsonArray();
        for (String row : this.pattern)
            jsonPattern.add(row);
        json.add("pattern", jsonPattern);
        JsonObject jsonMapping = new JsonObject();
        for (Entry<Character, JsonElement> entry : this.mappings.entrySet())
            jsonMapping.add(String.valueOf(entry.getKey()), entry.getValue());
        json.add("mapping", jsonMapping);
        return json;
    }

    public void encode(PacketBuffer buffer) {
        buffer.writeInt(this.pattern.size());
        for (String row : this.pattern)
            buffer.writeUtf(row);
        buffer.writeInt(this.mappings.size());
        for (Entry<Character, JsonElement> entry : this.mappings.entrySet()) {
            buffer.writeChar(entry.getKey());
            buffer.writeUtf(entry.getValue().toString());
        }
    }

    private static OccultismMatcher OM(IStateMatcher matcher) {
        return new OccultismMatcher(matcher);
    }

    private static OccultismMatcher OM(IStateMatcher matcher, Block block) {
        return new OccultismMatcher(matcher, block);
    }

    // Matcher wrapper to handle the case of cycling through the different glyphs
    private static class OccultismMatcher implements IStateMatcher {

        private IStateMatcher matcher;
        private Block block;

        private OccultismMatcher(IStateMatcher matcher, Block block) {
            this.matcher = matcher;
            this.block = block;
        }

        private OccultismMatcher(IStateMatcher matcher) {
            this(matcher, null);
        }

        @Override
        public BlockState getDisplayedState(int ticks) {
            if (block instanceof ChalkGlyphBlock) {
                return block.defaultBlockState().setValue(ChalkGlyphBlock.SIGN, ticks / 20 % ChalkGlyphBlock.MAX_SIGN);
            }

            return matcher.getDisplayedState(ticks);
        }

        @Override
        public TriPredicate<IBlockReader, BlockPos, BlockState> getStatePredicate() {
            return matcher.getStatePredicate();
        }

    }
}
