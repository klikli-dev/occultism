package com.github.klikli_dev.occultism.datagen;

import com.github.klikli_dev.occultism.Occultism;
import com.google.common.collect.Sets;
import com.google.gson.*;
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

public class CrushingRecipeProvider implements DataProvider {

    private static final Gson GSON = (new GsonBuilder()).setPrettyPrinting().create();
    protected final DataGenerator generator;

    public CrushingRecipeProvider(DataGenerator generator) {
        this.generator = generator;
    }

    @Override
    public void run(HashCache pOutput) throws IOException {
        Set<ResourceLocation> set = Sets.newHashSet();
        Path path = this.generator.getOutputFolder();
        this.buildRecipes((recipe) -> {
            if (!set.add(recipe.getFirst())) {
                throw new IllegalStateException("Duplicate recipe " + recipe.getFirst());
            } else {
                saveRecipe(pOutput, recipe.getSecond(), path.resolve("data/" + recipe.getFirst().getNamespace() + "/recipes/" + recipe.getFirst().getPath() + ".json"));
            }
        });
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

    protected void buildRecipes(Consumer<Pair<ResourceLocation, JsonObject>> recipeConsumer) {
        this.buildCrushingRecipesForMetal("aluminum", recipeConsumer);
    }

    protected void buildCrushingRecipesForMetal(String metal, Consumer<Pair<ResourceLocation, JsonObject>> recipeConsumer) {
        var metalDustRecipeId = new ResourceLocation(Occultism.MODID, metal + "_dust");
        var metalDustRecipe = this.buildCrushingRecipe("forge:ores/" + metal, "forge:dusts/" + metal, 2, 200, false);
        recipeConsumer.accept(new Pair<>(metalDustRecipeId, metalDustRecipe));

        var metalDustFromRawRecipeId = new ResourceLocation(Occultism.MODID, metal + "_dust_from_raw");
        var metalDustFromRawRecipe = this.buildCrushingRecipe("forge:raw_materials/" + metal, "forge:dusts/" + metal, 2, 200, false);
        recipeConsumer.accept(new Pair<>(metalDustFromRawRecipeId, metalDustFromRawRecipe));

        var metalDustFromIngotRecipeId = new ResourceLocation(Occultism.MODID, metal + "_dust_from_ingot");
        var metalDustFromIngotRecipe = this.buildCrushingRecipe("forge:ingots/" + metal, "forge:dusts/" + metal, 1, 200, true);
        recipeConsumer.accept(new Pair<>(metalDustFromIngotRecipeId, metalDustFromIngotRecipe));
    }

    public JsonObject buildCrushingRecipe(String inputTag, String outputTag, int count, int crushingTime, boolean ignoreCrushingMultiplier) {
        var recipe = new JsonObject();
        recipe.addProperty("type", "occultism:crushing");
        var conditions = this.buildCrushingCondition(inputTag, outputTag);
        recipe.add("conditions", conditions);
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
        condition.addProperty("type", "forge:not");
        var value = new JsonObject();
        value.addProperty("type", "forge:tag_empty");
        value.addProperty("tag", inputTag);
        condition.add("value", value);
        conditions.add(condition);
        condition = new JsonObject();
        condition.addProperty("type", "forge:not");
        value = new JsonObject();
        value.addProperty("type", "forge:tag_empty");
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
