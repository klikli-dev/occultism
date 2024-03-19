package com.klikli_dev.occultism.api.common.datagen.recipe;

import com.klikli_dev.occultism.datagen.recipes.JsonRecipeProvider;
import net.minecraft.data.PackOutput;

public abstract class CrushingRecipeProvider extends JsonRecipeProvider {

    public CrushingRecipeProvider(PackOutput packOutput, String modid, String recipeSubPath) {
        super(packOutput, modid, recipeSubPath);
    }


}
