package com.klikli_dev.occultism.registry;

import com.klikli_dev.occultism.Occultism;
import com.klikli_dev.occultism.common.misc.OutputIngredient;
import com.klikli_dev.occultism.common.misc.WeightedOutputIngredient;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.neoforged.neoforge.common.crafting.IngredientType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public class OccultismIngredientTypes {
    public static final DeferredRegister<IngredientType<?>> INGREDIENT_TYPES =
            DeferredRegister.create(NeoForgeRegistries.INGREDIENT_TYPES, Occultism.MODID);

    public static final DeferredHolder<IngredientType<?>,IngredientType<OutputIngredient>> OUTPUT_INGREDIENT = INGREDIENT_TYPES.register("output_ingredient", () -> new IngredientType<>(OutputIngredient.CODEC,OutputIngredient.CODEC_NONEMPTY));

}
