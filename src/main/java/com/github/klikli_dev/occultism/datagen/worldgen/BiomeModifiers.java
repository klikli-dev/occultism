package com.github.klikli_dev.occultism.datagen.worldgen;

import com.github.klikli_dev.occultism.Occultism;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.ForgeBiomeModifiers;
import net.minecraftforge.registries.ForgeRegistries;

public class BiomeModifiers {

    public static final ResourceKey<BiomeModifier> ADD_ORE_SILVER = ResourceKey.create(ForgeRegistries.Keys.BIOME_MODIFIERS, new ResourceLocation(Occultism.MODID, "add_ore_silver"));

    public static final ResourceKey<BiomeModifier> ADD_ORE_SILVER_DEEPSLATE = ResourceKey.create(ForgeRegistries.Keys.BIOME_MODIFIERS, new ResourceLocation(Occultism.MODID, "add_ore_silver_deepslate"));

    public static final ResourceKey<BiomeModifier> ADD_ORE_IESNIUM = ResourceKey.create(ForgeRegistries.Keys.BIOME_MODIFIERS, new ResourceLocation(Occultism.MODID, "add_ore_iesnium"));

    public static final ResourceKey<BiomeModifier> ADD_TREE_OTHERWORLD = ResourceKey.create(ForgeRegistries.Keys.BIOME_MODIFIERS, new ResourceLocation(Occultism.MODID, "add_tree_otherworld"));

    public static final ResourceKey<BiomeModifier> ADD_TREE_OTHERWORLD_NATURAL = ResourceKey.create(ForgeRegistries.Keys.BIOME_MODIFIERS, new ResourceLocation(Occultism.MODID, "add_tree_otherworld_natural"));

    public static final ResourceKey<BiomeModifier> ADD_GROVE_UNDERGROUND = ResourceKey.create(ForgeRegistries.Keys.BIOME_MODIFIERS, new ResourceLocation(Occultism.MODID, "add_grove_underground"));

    public static void bootstrap(BootstapContext<BiomeModifier> context) {
        var placedFeatures = context.lookup(Registries.PLACED_FEATURE);
        var biomes = context.lookup(Registries.BIOME);

        context.register(ADD_ORE_SILVER, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(BiomeTags.IS_OVERWORLD),
                HolderSet.direct(placedFeatures.getOrThrow(PlacedFeatures.ORE_SILVER)),
                GenerationStep.Decoration.UNDERGROUND_ORES));

        context.register(ADD_ORE_SILVER_DEEPSLATE, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(BiomeTags.IS_OVERWORLD),
                HolderSet.direct(placedFeatures.getOrThrow(PlacedFeatures.ORE_SILVER_DEEPSLATE)),
                GenerationStep.Decoration.UNDERGROUND_ORES));

        context.register(ADD_ORE_IESNIUM, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(BiomeTags.IS_NETHER),
                HolderSet.direct(placedFeatures.getOrThrow(PlacedFeatures.ORE_IESNIUM)),
                GenerationStep.Decoration.UNDERGROUND_ORES));

        context.register(ADD_TREE_OTHERWORLD_NATURAL, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(BiomeTags.IS_OVERWORLD),
                HolderSet.direct(placedFeatures.getOrThrow(PlacedFeatures.TREE_OTHERWORLD_NATURAL)),
                GenerationStep.Decoration.VEGETAL_DECORATION));

        context.register(ADD_TREE_OTHERWORLD, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(BiomeTags.IS_OVERWORLD),
                HolderSet.direct(placedFeatures.getOrThrow(PlacedFeatures.TREE_OTHERWORLD)),
                GenerationStep.Decoration.VEGETAL_DECORATION));

        context.register(ADD_GROVE_UNDERGROUND, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(BiomeTags.IS_OVERWORLD),
                HolderSet.direct(placedFeatures.getOrThrow(PlacedFeatures.GROVE_UNDERGROUND)),
                GenerationStep.Decoration.UNDERGROUND_STRUCTURES));
    }
}
