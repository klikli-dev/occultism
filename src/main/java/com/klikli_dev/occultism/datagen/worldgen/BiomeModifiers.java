package com.klikli_dev.occultism.datagen.worldgen;

import com.klikli_dev.occultism.Occultism;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.levelgen.GenerationStep.Decoration;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.common.world.BiomeModifiers.AddFeaturesBiomeModifier;
import net.neoforged.neoforge.registries.NeoForgeRegistries.Keys;

public class BiomeModifiers {

    public static final ResourceKey<BiomeModifier> ADD_ORE_SILVER = ResourceKey.create(Keys.BIOME_MODIFIERS, Identifier.fromNamespaceAndPath(Occultism.MODID, "add_ore_silver"));

    public static final ResourceKey<BiomeModifier> ADD_ORE_SILVER_DEEPSLATE = ResourceKey.create(Keys.BIOME_MODIFIERS, Identifier.fromNamespaceAndPath(Occultism.MODID, "add_ore_silver_deepslate"));

    public static final ResourceKey<BiomeModifier> ADD_ORE_IESNIUM = ResourceKey.create(Keys.BIOME_MODIFIERS, Identifier.fromNamespaceAndPath(Occultism.MODID, "add_ore_iesnium"));

    public static final ResourceKey<BiomeModifier> ADD_TREE_OTHERWORLD = ResourceKey.create(Keys.BIOME_MODIFIERS, Identifier.fromNamespaceAndPath(Occultism.MODID, "add_tree_otherworld"));

    public static final ResourceKey<BiomeModifier> ADD_TREE_OTHERWORLD_NATURAL = ResourceKey.create(Keys.BIOME_MODIFIERS, Identifier.fromNamespaceAndPath(Occultism.MODID, "add_tree_otherworld_natural"));

    public static final ResourceKey<BiomeModifier> ADD_GROVE_UNDERGROUND = ResourceKey.create(Keys.BIOME_MODIFIERS, Identifier.fromNamespaceAndPath(Occultism.MODID, "add_grove_underground"));

    public static void bootstrap(BootstrapContext<BiomeModifier> context) {
        var placedFeatures = context.lookup(Registries.PLACED_FEATURE);
        var biomes = context.lookup(Registries.BIOME);

        context.register(ADD_ORE_SILVER, new AddFeaturesBiomeModifier(
                biomes.getOrThrow(BiomeTags.IS_OVERWORLD),
                HolderSet.direct(placedFeatures.getOrThrow(PlacedFeatures.ORE_SILVER)),
                Decoration.UNDERGROUND_ORES));

        context.register(ADD_ORE_SILVER_DEEPSLATE, new AddFeaturesBiomeModifier(
                biomes.getOrThrow(BiomeTags.IS_OVERWORLD),
                HolderSet.direct(placedFeatures.getOrThrow(PlacedFeatures.ORE_SILVER_DEEPSLATE)),
                Decoration.UNDERGROUND_ORES));

        context.register(ADD_ORE_IESNIUM, new AddFeaturesBiomeModifier(
                biomes.getOrThrow(BiomeTags.IS_NETHER),
                HolderSet.direct(placedFeatures.getOrThrow(PlacedFeatures.ORE_IESNIUM)),
                Decoration.UNDERGROUND_ORES));

        context.register(ADD_TREE_OTHERWORLD_NATURAL, new AddFeaturesBiomeModifier(
                biomes.getOrThrow(BiomeTags.STRONGHOLD_BIASED_TO),
                HolderSet.direct(placedFeatures.getOrThrow(PlacedFeatures.TREE_OTHERWORLD_NATURAL)),
                Decoration.VEGETAL_DECORATION));

        context.register(ADD_TREE_OTHERWORLD, new AddFeaturesBiomeModifier(
                biomes.getOrThrow(BiomeTags.STRONGHOLD_BIASED_TO),
                HolderSet.direct(placedFeatures.getOrThrow(PlacedFeatures.TREE_OTHERWORLD)),
                Decoration.VEGETAL_DECORATION));

        context.register(ADD_GROVE_UNDERGROUND, new AddFeaturesBiomeModifier(
                biomes.getOrThrow(BiomeTags.IS_OVERWORLD),
                HolderSet.direct(placedFeatures.getOrThrow(PlacedFeatures.GROVE_UNDERGROUND)),
                Decoration.UNDERGROUND_STRUCTURES));
    }
}
