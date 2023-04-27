/*
 * MIT License
 *
 * Copyright 2023 klikli-dev
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

package com.github.klikli_dev.occultism.datagen;

import com.github.klikli_dev.occultism.Occultism;
import com.google.common.collect.Sets;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.mojang.datafixers.util.Pair;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.HashCache;
import net.minecraft.resources.ResourceLocation;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;

public class MinerRecipeProvider implements DataProvider {
    private static final Gson GSON = (new GsonBuilder()).setPrettyPrinting().create();
    protected final DataGenerator generator;

    public MinerRecipeProvider(DataGenerator generator) {
        this.generator = generator;
    }

    private static void saveRecipe(HashCache pCache, JsonObject pRecipeJson, Path pPath) {
        try {
            String s = GSON.toJson(pRecipeJson);
            String s1 = SHA1.hashUnencodedChars(s).toString();
            if (!Objects.equals(pCache.getHash(pPath), s1) || !Files.exists(pPath)) {
                Files.createDirectories(pPath.getParent());
                BufferedWriter bufferedwriter = Files.newBufferedWriter(pPath);

                try {
                    bufferedwriter.write(s);
                } catch (Throwable throwable1) {
                    if (bufferedwriter != null) {
                        try {
                            bufferedwriter.close();
                        } catch (Throwable throwable) {
                            throwable1.addSuppressed(throwable);
                        }
                    }

                    throw throwable1;
                }

                if (bufferedwriter != null) {
                    bufferedwriter.close();
                }
            }

            pCache.putNew(pPath, s1);
        } catch (IOException ioexception) {
            Occultism.LOGGER.error("Couldn't save recipe {}", pPath, ioexception);
        }
    }

    @Override
    public void run(HashCache pOutput) throws IOException {
        Set<ResourceLocation> set = Sets.newHashSet();
        Path path = this.generator.getOutputFolder();
        this.buildRecipes((recipe) -> {
            if (!set.add(recipe.getFirst())) {
                throw new IllegalStateException("Duplicate recipe " + recipe.getFirst());
            } else {
                saveRecipe(pOutput, recipe.getSecond(), path.resolve("data/" + recipe.getFirst().getNamespace() + "/recipes/miner/" + recipe.getFirst().getPath() + ".json"));
            }
        });
    }

    protected ResourceLocation modLoc(String path) {
        return new ResourceLocation(Occultism.MODID, path);
    }

    protected ResourceLocation loc(String namespace, String path) {
        return new ResourceLocation(namespace, path);
    }

    protected void buildRecipes(Consumer<Pair<ResourceLocation, JsonObject>> recipes) {
        this.buildForbiddenArcanusRecipes(recipes);
    }

    protected void buildForbiddenArcanusRecipes(Consumer<Pair<ResourceLocation, JsonObject>> recipes) {
        recipes.accept(this.buildMinerRecipe(
                this.modLoc("deeps/deepslate_arcane_crystal"),
                this.modLoc("miners/deeps"),
                this.loc("forge", "ores/arcane_crystal"),
                200));

        recipes.accept(this.buildMinerRecipe(
                this.modLoc("master/stella_arcanum"),
                this.modLoc("miners/master"),
                this.loc("forge", "ores/stella_arcanum"),
                100));

        recipes.accept(this.buildMinerRecipe(
                this.modLoc("ores/arcane_crystal"),
                this.modLoc("miners/ores"),
                this.loc("forge", "ores/arcane_crystal"),
                200));

        recipes.accept(this.buildMinerRecipe(
                this.modLoc("ores/xpetrified"),
                this.modLoc("miners/ores"),
                this.loc("forge", "ores/xpetrified_ore"),
                200));
    }

    protected Pair<ResourceLocation, JsonObject> buildMinerRecipe(ResourceLocation name, ResourceLocation minerTag, ResourceLocation outputTag, int weight) {
        var recipe = this.buildMinerRecipeJson(minerTag.toString(), outputTag.toString(), weight);
        return new Pair<>(name, recipe);
    }

    public JsonObject buildMinerRecipeJson(String minerTag, String outputTag, int weight) {
        var recipe = new JsonObject();
        recipe.addProperty("type", "occultism:miner");
        var conditions = this.buildMinerRecipeConditionJson(outputTag);
        recipe.add("conditions", conditions);
        var ingredient = new JsonObject();
        ingredient.addProperty("tag", minerTag);
        recipe.add("ingredient", ingredient);
        var result = new JsonObject();
        result.addProperty("tag", outputTag);
        recipe.add("result", result);
        recipe.addProperty("weight", weight);
        return recipe;
    }

    public JsonArray buildMinerRecipeConditionJson(String outputTag) {
        var conditions = new JsonArray();
        //multiple conditions on the root level array are treated as AND by forge, but we only have one
        var condition = new JsonObject();
        condition.addProperty("type", "forge:not");
        var value = new JsonObject();
        value.addProperty("type", "forge:tag_empty");
        value.addProperty("tag", outputTag);
        condition.add("value", value);
        conditions.add(condition);
        return conditions;
    }

    @Override
    public String getName() {
        return "Miner Recipes";
    }
}
