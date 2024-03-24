package com.klikli_dev.occultism.datagen.recipes;

import com.klikli_dev.occultism.Occultism;
import com.klikli_dev.occultism.registry.OccultismTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.concurrent.CompletableFuture;

public class OccultismRecipeProvider extends RecipeProvider {
    public OccultismRecipeProvider(PackOutput p_248933_, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(p_248933_, lookupProvider);
    }

    @Override
    protected void buildRecipes(RecipeOutput pRecipeOutput) {
        crushingRecipes(pRecipeOutput);

    }

    private void crushingRecipes(RecipeOutput pRecipeOutput) {
        CrushingRecipeBuilder.crushingRecipe(Ingredient.of(OccultismTags.Items.DATURA_CROP),Ingredient.of(OccultismTags.Items.DATURA_SEEDS), 200)
                .unlockedBy("has_datura", has(OccultismTags.Items.DATURA_CROP))
                .setAllowEmpty(false)
                .setOutputAmount(2)
                .save(pRecipeOutput, new ResourceLocation(Occultism.MODID, "crushing/datura"));
    }
}
