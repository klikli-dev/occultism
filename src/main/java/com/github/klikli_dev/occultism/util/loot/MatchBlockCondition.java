/*
 *
 * Copyright (c) 2016, David Quintana
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * * Redistributions of source code must retain the above copyright notice, this
 *   list of conditions and the following disclaimer.
 *
 * * Redistributions in binary form must reproduce the above copyright notice,
 *   this list of conditions and the following disclaimer in the documentation
 *   and/or other materials provided with the distribution.
 *
 * * Neither the name of project nor the names of its
 *   contributors may be used to endorse or promote products derived from
 *   this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package com.github.klikli_dev.occultism.util.loot;

import com.google.common.collect.Lists;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.Tag;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.Serializer;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;

public class MatchBlockCondition implements LootItemCondition {
    public static LootItemConditionType BLOCK_TAG_CONDITION;

    @Nullable
    final List<Block> blockList;
    @Nullable
    final Tag.Named<Block> blockTag;

    public MatchBlockCondition(@Nullable List<Block> blockList, @Nullable Tag.Named<Block> blockTag) {
        this.blockList = blockList;
        this.blockTag = blockTag;
    }

    @Override
    public boolean test(LootContext lootContext) {
        BlockState state = lootContext.getParam(LootContextParams.BLOCK_STATE);
        if (state == null)
            return false;
        if (this.blockTag != null)
            return this.blockTag.contains(state.getBlock());
        if (this.blockList != null)
            return this.blockList.contains(state.getBlock());
        return false;
    }

    @Override
    public LootItemConditionType getType() {
        return BLOCK_TAG_CONDITION;
    }

    public static class LootSerializer implements Serializer<MatchBlockCondition> {
        @Override
        public void serialize(JsonObject json, MatchBlockCondition value, JsonSerializationContext context) {
            if (value.blockTag != null)
                json.addProperty("tag", value.blockTag.getName().toString());
        }

        @Override
        public MatchBlockCondition deserialize(JsonObject json, JsonDeserializationContext context) {
            if (json.has("tag")) {
                ResourceLocation tagName = new ResourceLocation(GsonHelper.getAsString(json, "tag"));
                return new MatchBlockCondition(null, BlockTags.createOptional(tagName));
            } else if (json.has("blocks")) {
                List<Block> blockNames = Lists.newArrayList();
                for (JsonElement e : GsonHelper.getAsJsonArray(json, "blocks")) {
                    ResourceLocation blockName = new ResourceLocation(e.getAsString());
                    blockNames.add(ForgeRegistries.BLOCKS.getValue(blockName));
                }
                return new MatchBlockCondition(blockNames, null);
            } else if (json.has("block")) {
                ResourceLocation blockName = new ResourceLocation(GsonHelper.getAsString(json, "block"));
                return new MatchBlockCondition(Collections.singletonList(ForgeRegistries.BLOCKS.getValue(blockName)), null);
            }
            throw new RuntimeException("match_block must have one of 'tag', 'block' or 'blocks' key");
        }
    }
}