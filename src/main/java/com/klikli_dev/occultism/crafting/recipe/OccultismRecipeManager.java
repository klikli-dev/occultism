// SPDX-FileCopyrightText: 2024 klikli-dev
//
// SPDX-License-Identifier: MIT

package com.klikli_dev.occultism.crafting.recipe;

import com.klikli_dev.occultism.registry.OccultismRecipes;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.item.crafting.RecipeMap;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.common.util.Lazy;
import net.neoforged.neoforge.event.OnDatapackSyncEvent;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class OccultismRecipeManager {
    private static final OccultismRecipeManager INSTANCE = new OccultismRecipeManager();
    private static final Lazy<List<RecipeType<?>>> SYNCED_RECIPE_TYPES = Lazy.of(() -> List.of(
            OccultismRecipes.SPIRIT_TRADE_TYPE.get(),
            OccultismRecipes.SPIRIT_FIRE_TYPE.get(),
            OccultismRecipes.CRUSHING_TYPE.get(),
            OccultismRecipes.CRYSTALLIZE_TYPE.get(),
            OccultismRecipes.MINER_TYPE.get(),
            OccultismRecipes.RITUAL_TYPE.get()
    ));

    private final Map<RecipeType<?>, Collection<RecipeHolder<?>>> clientRecipeCache = new ConcurrentHashMap<>();
    private final Map<RecipeType<?>, Map<ResourceKey<Recipe<?>>, RecipeHolder<?>>> clientRecipeByKeyCache = new ConcurrentHashMap<>();
    private volatile long recipeGeneration;

    private OccultismRecipeManager() {
    }

    public static OccultismRecipeManager get() {
        return INSTANCE;
    }

    public static OccultismRecipeManager getInstance() {
        return INSTANCE;
    }

    private List<RecipeType<?>> syncedRecipeTypes() {
        return SYNCED_RECIPE_TYPES.get();
    }

    public long getRecipeGeneration() {
        return this.recipeGeneration;
    }

    public <C extends RecipeInput, T extends Recipe<C>> Collection<RecipeHolder<T>> getRecipesByType(RecipeType<T> type, Level level) {
        if (level == null) {
            return List.of();
        }

        if (level.isClientSide()) {
            //noinspection unchecked
            return (Collection<RecipeHolder<T>>) (Collection<?>) this.clientRecipeCache.getOrDefault(type, List.of());
        }

        return level.getServer().getRecipeManager().recipeMap().byType(type);
    }

    public <C extends RecipeInput, T extends Recipe<C>> Optional<RecipeHolder<T>> getRecipeFor(RecipeType<T> type, C input, Level level) {
        return this.getRecipeFor(type, input, level, null);
    }

    public <C extends RecipeInput, T extends Recipe<C>> Optional<RecipeHolder<T>> getRecipeFor(RecipeType<T> type, C input, Level level, ResourceKey<Recipe<?>> lastRecipe) {
        if (level == null) {
            return Optional.empty();
        }

        if (lastRecipe != null) {
            var cachedRecipe = this.getRecipeByKey(type, lastRecipe, level);
            if (cachedRecipe.isPresent() && cachedRecipe.get().value().matches(input, level)) {
                return cachedRecipe;
            }
        }

        if (level.isClientSide()) {
            return this.getRecipesByType(type, level).stream()
                    .filter(recipe -> recipe.value().matches(input, level))
                    .findFirst();
        }

        return level.getServer().getRecipeManager().getRecipeFor(type, input, level);
    }

    @SuppressWarnings("unchecked")
    public <T extends Recipe<?>> Optional<RecipeHolder<T>> getRecipeByKey(RecipeType<T> type, ResourceKey<Recipe<?>> key, Level level) {
        if (key == null || level == null) {
            return Optional.empty();
        }

        if (level.isClientSide()) {
            var typeMap = this.clientRecipeByKeyCache.get(type);
            if (typeMap != null) {
                return Optional.ofNullable((RecipeHolder<T>) typeMap.get(key));
            }
            return Optional.empty();
        }

        return level.getServer().getRecipeManager().byKey(key)
                .filter(recipe -> recipe.value().getType() == type)
                .map(recipe -> (RecipeHolder<T>) recipe);
    }

    public void onDatapackSync(OnDatapackSyncEvent event) {
        this.recipeGeneration++;
        this.syncedRecipeTypes().forEach(event::sendRecipes);
    }

    void clearClientCache() {
        this.recipeGeneration++;
        this.clientRecipeCache.clear();
        this.clientRecipeByKeyCache.clear();
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    void storeClientRecipesUnchecked(RecipeMap recipeMap, RecipeType<?> type) {
        this.storeClientRecipes(recipeMap, (RecipeType) type);
    }

    private <I extends RecipeInput, T extends Recipe<I>> void storeClientRecipes(RecipeMap recipeMap, RecipeType<T> type) {
        this.storeClientRecipes(type, recipeMap.byType(type));
    }

    private <T extends Recipe<?>> void storeClientRecipes(RecipeType<T> type, Iterable<RecipeHolder<T>> recipes) {
        var recipeList = new ArrayList<RecipeHolder<?>>();
        var recipesByKey = new ConcurrentHashMap<ResourceKey<Recipe<?>>, RecipeHolder<?>>();

        for (var recipe : recipes) {
            recipeList.add(recipe);
            recipesByKey.put(recipe.id(), recipe);
        }

        this.clientRecipeCache.put(type, List.copyOf(recipeList));
        this.clientRecipeByKeyCache.put(type, recipesByKey);
    }
}