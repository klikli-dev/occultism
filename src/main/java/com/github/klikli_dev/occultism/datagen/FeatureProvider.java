package com.github.klikli_dev.occultism.datagen;

import com.github.klikli_dev.occultism.Occultism;
import com.github.klikli_dev.occultism.registry.OccultismFeatures;
import com.google.gson.JsonElement;
import com.mojang.serialization.JsonOps;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.resources.RegistryOps;
import net.minecraft.server.packs.PackType;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep.Decoration;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.ForgeBiomeModifiers.AddFeaturesBiomeModifier;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.io.IOException;
import java.nio.file.Path;

public class FeatureProvider implements DataProvider {

    DataGenerator dataGenerator;
    RegistryOps<JsonElement> ops;
    String directory;
    CachedOutput cache;

    public FeatureProvider(DataGenerator dataGeneratorIn) {
        this.dataGenerator = dataGeneratorIn;
        this.ops = RegistryOps.create(JsonOps.INSTANCE, RegistryAccess.BUILTIN.get());
        directory = PackType.SERVER_DATA.getDirectory();
    }

    public void placedFeature(String name, PlacedFeature feature) {
        final Path outputFolder = this.dataGenerator.getOutputFolder();
        final String featurePathString = String.join("/", directory, Occultism.MODID,
                Registry.PLACED_FEATURE_REGISTRY.location().getPath(), name + ".json");

        final Path featurePath = outputFolder.resolve(featurePathString);

        PlacedFeature.DIRECT_CODEC.encodeStart(ops, feature)
                .resultOrPartial(msg -> Occultism.LOGGER.error("Failed to encode placed feature {}: {}", featurePathString, msg)) // Log error on encode failure.
                .ifPresent(json -> // Output to file on encode success.
                {
                    try {
                        DataProvider.saveStable(cache, json, featurePath);
                    } catch (
                            IOException e) // The throws can't deal with this exception, because we're inside the ifPresent.
                    {
                        Occultism.LOGGER.error("Failed to save placed feature" + featurePathString, e);
                    }
                });
    }

    public void configuredFeature(String name, ConfiguredFeature<?, ?> feature) {
        final Path outputFolder = this.dataGenerator.getOutputFolder();
        final String featurePathString = String.join("/", directory, Occultism.MODID,
                Registry.CONFIGURED_FEATURE_REGISTRY.location().getPath(), name + ".json");

        final Path featurePath = outputFolder.resolve(featurePathString);

        ConfiguredFeature.DIRECT_CODEC.encodeStart(ops, feature)
                .resultOrPartial(msg -> Occultism.LOGGER.error("Failed to encode configured feature {}: {}", featurePathString, msg)) // Log error on encode failure.
                .ifPresent(json -> // Output to file on encode success.
                {
                    try {
                        DataProvider.saveStable(cache, json, featurePath);
                    } catch (
                            IOException e) // The throws can't deal with this exception, because we're inside the ifPresent.
                    {
                        Occultism.LOGGER.error("Failed to save configured feature" + featurePathString, e);
                    }
                });
    }

    public void addOreToBiomes(String name, RegistryObject<PlacedFeature> feature, HolderSet<Biome> biomes) {
        final BiomeModifier modifier = new AddFeaturesBiomeModifier(
                biomes,
                HolderSet.direct(feature.getHolder().orElseThrow()), Decoration.UNDERGROUND_ORES);


        final Path outputFolder = this.dataGenerator.getOutputFolder();
        final String modifierPathString = String.join("/", directory, Occultism.MODID,
                ForgeRegistries.Keys.BIOME_MODIFIERS.location().getPath(), name + ".json");

        final Path modifierPath = outputFolder.resolve(modifierPathString);

        BiomeModifier.DIRECT_CODEC.encodeStart(ops, modifier)
                .resultOrPartial(msg -> Occultism.LOGGER.error("Failed to encode biome modifier {}: {}", modifierPathString, msg)) // Log error on encode failure.
                .ifPresent(json -> // Output to file on encode success.
                {
                    try {
                        DataProvider.saveStable(cache, json, modifierPath);
                    } catch (
                            IOException e) // The throws can't deal with this exception, because we're inside the ifPresent.
                    {
                        Occultism.LOGGER.error("Failed to save biome modifier" + modifierPathString, e);
                    }
                });

    }

    @Override
    public void run(final CachedOutput cache) throws IOException {
        this.cache = cache;

        this.configuredFeature("silver_ore", OccultismFeatures.SILVER_ORE_CONFIGURED.get());
        this.placedFeature("silver_ore", OccultismFeatures.SILVER_ORE_PLACED.get());
        this.addOreToBiomes("add_silver_ore", OccultismFeatures.SILVER_ORE_PLACED,
                new HolderSet.Named<>(ops.registry(Registry.BIOME_REGISTRY).get(), BiomeTags.IS_OVERWORLD));

        //TODO: register other features
        //TODO: register the ore biome modifiers
        //TODO: register the biome modifier for undergourund grove
        //TODO: replace our own biome mod with forge builtin
    }

    @Override
    public String getName() {
        return Occultism.MODID + ":biome_modifiers";
    }
}
