package com.github.klikli_dev.occultism.datagen.worldgen;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.world.level.biome.Biome;
import net.minecraftforge.registries.ForgeRegistries;

public class OccultismRegistries {
    public static final RegistrySetBuilder BUILDER = (new RegistrySetBuilder())
            .add(Registries.CONFIGURED_FEATURE, ConfiguredFeatures::bootstrap)
            .add(Registries.PLACED_FEATURE, PlacedFeatures::bootstrap)
            .add(Registries.BIOME, OccultismRegistries::bootstrapBiomes)
            .add(ForgeRegistries.Keys.BIOME_MODIFIERS, BiomeModifiers::bootstrap);

    public static void bootstrapBiomes(BootstapContext<Biome> context) {
        //doesn't need to do anything, just gives us acccess to a biome registry with empty tag lookup in our other boopstrap contexts
    }
}
