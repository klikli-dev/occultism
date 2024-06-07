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

package com.klikli_dev.occultism.datagen;

import com.google.common.collect.Sets;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.klikli_dev.occultism.Occultism;
import com.mojang.datafixers.util.Pair;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class CrushingRecipeProvider implements DataProvider {
    protected final PackOutput.PathProvider recipePathProvider;

    public CrushingRecipeProvider(PackOutput packOutput) {
        this.recipePathProvider = packOutput.createPathProvider(PackOutput.Target.DATA_PACK, "recipes/crushing");
    }

    public CompletableFuture<?> run(CachedOutput pOutput) {
        Set<ResourceLocation> set = Sets.newHashSet();
        List<CompletableFuture<?>> list = new ArrayList<>();
        this.buildRecipes((recipe) -> {
            if (!set.add(recipe.getFirst())) {
                throw new IllegalStateException("Duplicate recipe " + recipe.getFirst());
            } else {
                list.add(DataProvider.saveStable(pOutput, recipe.getSecond(), this.recipePathProvider.json(recipe.getFirst())));
            }
        });
        return CompletableFuture.allOf(list.toArray(CompletableFuture[]::new));
    }


    protected void buildRecipes(Consumer<Pair<ResourceLocation, JsonObject>> recipeConsumer) {
        this.buildCrushingRecipesForMetal("aluminum", recipeConsumer);
        this.buildCrushingRecipesForMetal("signalum", recipeConsumer);
        this.buildCrushingRecipesForMetal("uranium", recipeConsumer);
        this.buildCrushingRecipesForMetal("graphite", recipeConsumer);
        this.buildCrushingRecipesForMetal("azure_silver", recipeConsumer);
        this.buildCrushingRecipesForMetal("zinc", recipeConsumer);
        this.buildCrushingRecipesForMetal("lumium", recipeConsumer);
        this.buildCrushingRecipesForMetal("osmium", recipeConsumer);
        this.buildCrushingRecipesForMetal("nickel", recipeConsumer);
        this.buildCrushingRecipesForMetal("lead", recipeConsumer);
        this.buildCrushingRecipesForMetal("allthemodium", recipeConsumer);
        this.buildCrushingRecipesForMetal("bronze", recipeConsumer);
        this.buildCrushingRecipesForMetal("cobalt", recipeConsumer);
        this.buildCrushingRecipesForMetal("unobtainium", recipeConsumer);
        this.buildCrushingRecipesForMetal("tungsten", recipeConsumer);
        this.buildCrushingRecipesForMetal("iridium", recipeConsumer);
        this.buildCrushingRecipesForMetal("steel", recipeConsumer);
        this.buildCrushingRecipesForMetal("enderium", recipeConsumer);
        this.buildCrushingRecipesForMetal("electrum", recipeConsumer);
        this.buildCrushingRecipesForMetal("constantan", recipeConsumer);
        this.buildCrushingRecipesForMetal("tin", recipeConsumer);
        this.buildCrushingRecipesForMetal("netherite", recipeConsumer);
        this.buildCrushingRecipesForMetal("brass", recipeConsumer);
        this.buildCrushingRecipesForMetal("crimson_iron", recipeConsumer);
        this.buildCrushingRecipesForMetal("platinum", recipeConsumer);
        this.buildCrushingRecipesForMetal("invar", recipeConsumer);
        this.buildCrushingRecipesForMetal("vibranium", recipeConsumer);
        this.buildCrushingRecipesForMetal("silver", recipeConsumer);
        this.buildCrushingRecipesForMetal("copper", recipeConsumer);
        this.buildCrushingRecipesForMetal("pewter", recipeConsumer);
        this.buildCrushingRecipesForMetal("mithril", recipeConsumer);
        this.buildCrushingRecipesForMetal("gold", recipeConsumer);
        this.buildCrushingRecipesForMetal("quicksilver", recipeConsumer);
        this.buildCrushingRecipesForMetal("iron", recipeConsumer);
        this.buildCrushingRecipesForMetal("iesnium", recipeConsumer);


        this.buildCrushingRecipeForGem("diamond", recipeConsumer);
        this.buildCrushingRecipeForGem("emerald", recipeConsumer);
        this.buildCrushingRecipeForGem("lapis", recipeConsumer);
        this.buildCrushingRecipeForGem("quartz", recipeConsumer);
        this.buildCrushingRecipeForGem("coal", recipeConsumer);
        this.buildCrushingRecipeForGem("redstone", recipeConsumer);
        this.buildCrushingRecipeForGem("apatite", recipeConsumer);
        this.buildCrushingRecipeForGem("sulfur", recipeConsumer);
        this.buildCrushingRecipeForGem("fluorite", recipeConsumer);
        this.buildCrushingRecipeForGem("cinnabar", recipeConsumer);
        this.buildCrushingRecipeForGem("amber", recipeConsumer);
        this.buildCrushingRecipeForGem("certus_quartz", recipeConsumer);
        this.buildCrushingRecipeForGem("charged_certus_quartz", recipeConsumer);
        this.buildCrushingRecipeForGem("peridot", recipeConsumer);
        this.buildCrushingRecipeForGem("ruby", recipeConsumer);
        this.buildCrushingRecipeForGem("sapphire", recipeConsumer);
        this.buildCrushingRecipeForGem("topaz", recipeConsumer);
        this.buildCrushingRecipeForGem("arcane_crystal", recipeConsumer);
    }

    protected void buildCrushingRecipeForGem(String gem, Consumer<Pair<ResourceLocation, JsonObject>> recipeConsumer) {
        var gemDustId = new ResourceLocation(Occultism.MODID, gem + "_dust");
        var gemDustRecipe = this.buildCrushingRecipe("c:ores/" + gem, "c:dusts/" + gem, 4, 200, false);
        recipeConsumer.accept(new Pair<>(gemDustId, gemDustRecipe));

        var gemDustFromGemId = new ResourceLocation(Occultism.MODID, gem + "_dust_from_gem");
        var gemDustFromGemRecipe = this.buildCrushingRecipe("c:gems/" + gem, "c:dusts/" + gem, 1, 200, true);
        recipeConsumer.accept(new Pair<>(gemDustFromGemId, gemDustFromGemRecipe));
    }

    protected void buildCrushingRecipesForMetal(String metal, Consumer<Pair<ResourceLocation, JsonObject>> recipeConsumer) {
        var metalDustRecipeId = new ResourceLocation(Occultism.MODID, metal + "_dust");
        var metalDustRecipe = this.buildCrushingRecipe("c:ores/" + metal, "c:dusts/" + metal, 2, 200, false);
        recipeConsumer.accept(new Pair<>(metalDustRecipeId, metalDustRecipe));

        var metalDustFromRawRecipeId = new ResourceLocation(Occultism.MODID, metal + "_dust_from_raw");
        var metalDustFromRawRecipe = this.buildCrushingRecipe("c:raw_materials/" + metal, "c:dusts/" + metal, 2, 200, false);
        recipeConsumer.accept(new Pair<>(metalDustFromRawRecipeId, metalDustFromRawRecipe));

        var metalDustFromRawBlockRecipeId = new ResourceLocation(Occultism.MODID, metal + "_dust_from_raw_block");
        var metalDustFromRawBlockRecipe = this.buildCrushingRecipe("c:storage_blocks/raw_" + metal, "c:dusts/" + metal, 18, 1600, false);
        recipeConsumer.accept(new Pair<>(metalDustFromRawBlockRecipeId, metalDustFromRawBlockRecipe));

        var metalDustFromIngotRecipeId = new ResourceLocation(Occultism.MODID, metal + "_dust_from_ingot");
        var metalDustFromIngotRecipe = this.buildCrushingRecipe("c:ingots/" + metal, "c:dusts/" + metal, 1, 200, true);
        recipeConsumer.accept(new Pair<>(metalDustFromIngotRecipeId, metalDustFromIngotRecipe));
    }

    public JsonObject buildCrushingRecipe(String inputTag, String outputTag, int count, int crushingTime, boolean ignoreCrushingMultiplier) {
        var recipe = new JsonObject();
        recipe.addProperty("type", "occultism:crushing");
        var conditions = this.buildCrushingCondition(inputTag, outputTag);
        recipe.add("neoforge:conditions", conditions);
        var ingredient = new JsonObject();
        ingredient.addProperty("tag", inputTag);
        recipe.add("ingredient", ingredient);
        var result = new JsonObject();
        result.addProperty("tag", outputTag);
        result.addProperty("count", count);
        recipe.add("result", result);
        recipe.addProperty("crushing_time", crushingTime);
        recipe.addProperty("ignore_crushing_multiplier", ignoreCrushingMultiplier);
        return recipe;
    }

    public JsonArray buildCrushingCondition(String inputTag, String outputTag) {
        var conditions = new JsonArray();
        //multiple conditions on the root level array are treated as AND by forge
        var condition = new JsonObject();
        condition.addProperty("type", "neoforge:not");
        var value = new JsonObject();
        value.addProperty("type", "neoforge:tag_empty");
        value.addProperty("tag", inputTag);
        condition.add("value", value);
        conditions.add(condition);
        condition = new JsonObject();
        condition.addProperty("type", "neoforge:not");
        value = new JsonObject();
        value.addProperty("type", "neoforge:tag_empty");
        value.addProperty("tag", outputTag);
        condition.add("value", value);
        conditions.add(condition);
        return conditions;
    }

    @Override
    public String getName() {
        return "Crushing Recipes";
    }
}
