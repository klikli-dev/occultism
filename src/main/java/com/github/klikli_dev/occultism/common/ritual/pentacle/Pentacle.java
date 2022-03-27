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

import com.github.klikli_dev.occultism.Occultism;
import com.github.klikli_dev.occultism.common.block.ChalkGlyphBlock;
import com.google.gson.*;
import com.mojang.datafixers.util.Pair;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.Tag;
import net.minecraft.tags.TagKey;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.registries.ForgeRegistries;
import vazkii.patchouli.api.IMultiblock;
import vazkii.patchouli.api.IMultiblock.SimulateResult;
import vazkii.patchouli.api.IStateMatcher;
import vazkii.patchouli.api.PatchouliAPI;
import vazkii.patchouli.api.TriPredicate;

import java.util.*;
import java.util.Map.Entry;

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

        var api = PatchouliAPI.get();
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
            if(PatchouliAPI.get().getMultiblock(multiRL) == null)
                PatchouliAPI.get().registerMultiblock(multiRL, this.matcher);
        } catch (IllegalArgumentException e) {
            //should never happen, only thrown if multiblock registered twice
            Occultism.LOGGER.warn(e);
        }
    }

    public static Pentacle fromJson(ResourceLocation rl, JsonObject json) {
        JsonArray jsonPattern = GsonHelper.getAsJsonArray(json, "pattern");
        JsonObject jsonMapping = GsonHelper.getAsJsonObject(json, "mapping");
        List<String> pattern = new ArrayList<>();
        Map<Character, JsonElement> mappings = new HashMap<>();
        for (int i = 0; i < jsonPattern.size(); i++)
            pattern.add(GsonHelper.convertToString(jsonPattern.get(i), "row"));

        for (Entry<String, JsonElement> entry : jsonMapping.entrySet()) {
            if (entry.getKey().length() != 1)
                throw new JsonSyntaxException("Mapping key needs to be only 1 character");
            char key = entry.getKey().charAt(0);
            mappings.put(key, entry.getValue());
        }
        return new Pentacle(rl, pattern, mappings);
    }

    public static Pentacle fromNetwork(ResourceLocation key, FriendlyByteBuf buffer) {
        List<String> pattern = new ArrayList<>();
        Map<Character, JsonElement> mappings = new HashMap<>();
        int size = buffer.readInt();
        for (int i = 0; i < size; i++)
            pattern.add(buffer.readUtf());
        size = buffer.readInt();
        for (int i = 0; i < size; i++)
            mappings.put(buffer.readChar(), JsonParser.parseString(buffer.readUtf()));
        return new Pentacle(key, pattern, mappings);
    }
    public static IStateMatcher parseStateMatcher(JsonElement matcher) {
        if (matcher.isJsonObject()) {
            JsonObject jsonObject = matcher.getAsJsonObject();
            Block display = null;
            if (jsonObject.has("display")) {
                ResourceLocation displayRL = new ResourceLocation(GsonHelper.getAsString(jsonObject, "display"));
                display = ForgeRegistries.BLOCKS.getValue(displayRL);
                if (display == null)
                    throw new JsonSyntaxException("Invalid display" + displayRL);
            }
            if (jsonObject.has("block")) {
                ResourceLocation blockRL = new ResourceLocation(GsonHelper.getAsString(jsonObject, "block"));
                Block block = ForgeRegistries.BLOCKS.getValue(blockRL);
                if (block == null)
                    throw new JsonSyntaxException("Invalid block " + blockRL);

                if (display != null) {
                    return OM(PatchouliAPI.get().predicateMatcher(display, s -> s.getBlock() == block), block);
                } else {
                    return OM(PatchouliAPI.get().looseBlockMatcher(block), block);
                }
            } else if (jsonObject.has("tag")) {
                ResourceLocation tagRL = new ResourceLocation(GsonHelper.getAsString(jsonObject, "tag"));
                TagKey<Block> tag = TagKey.create(Registry.BLOCK_REGISTRY, tagRL);
                return OM(PatchouliAPI.get().predicateMatcher(display, s -> s.is(tag)));

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

    private static OccultismMatcher OM(IStateMatcher matcher) {
        return new OccultismMatcher(matcher);
    }

    private static OccultismMatcher OM(IStateMatcher matcher, Block block) {
        return new OccultismMatcher(matcher, block);
    }

    public String getDescriptionId() {
        return Util.makeDescriptionId("pentacle", this.rl);
    }

    public boolean validate(Level level, BlockPos pos) {
        return this.matcher.validate(level, pos) != null;
    }

    // Return the positions that are wrong
    public Map<BlockPos, Block> getDifference(Level level, BlockPos pos) {
        Map<BlockPos, Block> minDifference = new HashMap<>();
        int minDiffSize = Integer.MAX_VALUE;

        Map<BlockPos, Block> difference;
        for (Rotation rot : Rotation.values()) {
            difference = new HashMap<>();
            Pair<BlockPos, Collection<SimulateResult>> sim = this.matcher.simulate(level, pos, rot, false);

            for (SimulateResult result : sim.getSecond()) {
                if (!result.test(level, rot)) {
                    difference.put(result.getWorldPosition(), result.getStateMatcher().getDisplayedState(0).getBlock());
                }
            }

            if (difference.size() < minDiffSize) {
                minDifference = difference;
                minDiffSize = difference.size();
            }
        }

        return minDifference;
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

    public void toNetwork(FriendlyByteBuf buffer) {
        buffer.writeInt(this.pattern.size());
        for (String row : this.pattern)
            buffer.writeUtf(row);
        buffer.writeInt(this.mappings.size());
        for (Entry<Character, JsonElement> entry : this.mappings.entrySet()) {
            buffer.writeChar(entry.getKey());
            buffer.writeUtf(entry.getValue().toString());
        }
    }

    // Matcher wrapper to handle the case of cycling through the different glyphs
    private static class OccultismMatcher implements IStateMatcher {

        private final IStateMatcher matcher;
        private final Block block;

        private OccultismMatcher(IStateMatcher matcher, Block block) {
            this.matcher = matcher;
            this.block = block;
        }

        private OccultismMatcher(IStateMatcher matcher) {
            this(matcher, null);
        }

        @Override
        public BlockState getDisplayedState(long ticks) {
            if (this.block instanceof ChalkGlyphBlock) {
                return this.block.defaultBlockState().setValue(ChalkGlyphBlock.SIGN, (int)(ticks / 20 % ChalkGlyphBlock.MAX_SIGN));
            }

            return this.matcher.getDisplayedState(ticks);
        }

        @Override
        public TriPredicate<BlockGetter, BlockPos, BlockState> getStatePredicate() {
            return this.matcher.getStatePredicate();
        }

    }
}
