// SPDX-FileCopyrightText: 2024 klikli-dev
//
// SPDX-License-Identifier: MIT

package com.klikli_dev.occultism.registry;

import com.klikli_dev.occultism.Occultism;
import com.klikli_dev.occultism.crafting.recipe.result.RecipeResultType;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.registries.NewRegistryEvent;
import net.neoforged.neoforge.registries.RegistryBuilder;

public class OccultismRegistries {
    public static final Registry<RecipeResultType<?>> RECIPE_RESULT_TYPES = new RegistryBuilder<>(Keys.RECIPE_RESULT_TYPES).sync(true).create();


    public static void onRegisterRegistries(NewRegistryEvent event) {
        event.register(RECIPE_RESULT_TYPES);
    }

    public static final class Keys {
        public static final ResourceKey<Registry<RecipeResultType<?>>> RECIPE_RESULT_TYPES = ResourceKey.createRegistryKey(ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "recipe_result_type"));
    }
}
