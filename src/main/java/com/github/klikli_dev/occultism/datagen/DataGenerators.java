/*
 * MIT License
 *
 * Copyright 2020 klikli-dev
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
 * associated documentation files (the "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
 * of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following
 * conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial
 * portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR
 * PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT
 * OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
 * OTHER DEALINGS IN THE SOFTWARE.
 */

package com.github.klikli_dev.occultism.datagen;

import com.github.klikli_dev.occultism.Occultism;
import com.github.klikli_dev.occultism.common.level.multichunk.MultiChunkFeatureConfig;
import com.github.klikli_dev.occultism.datagen.lang.ENUSProvider;
import com.github.klikli_dev.occultism.datagen.lang.FRFRProvider;
import com.github.klikli_dev.occultism.datagen.lang.PTBRProvider;
import com.github.klikli_dev.occultism.datagen.loot.OccultismBlockLoot;
import com.github.klikli_dev.occultism.datagen.loot.OccultismEntityLoot;
import com.github.klikli_dev.occultism.registry.OccultismBlocks;
import com.github.klikli_dev.occultism.registry.OccultismFeatures;
import com.mojang.serialization.JsonOps;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.RegistryOps;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.levelgen.GenerationStep.Decoration;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.BlobFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.trunkplacers.StraightTrunkPlacer;
import net.minecraft.world.level.levelgen.placement.HeightRangePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraftforge.common.data.JsonCodecProvider;
import net.minecraftforge.common.world.ForgeBiomeModifiers.AddFeaturesBiomeModifier;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenerators {

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        generator.addProvider(event.includeServer(),
                new LootTableProvider(generator.getPackOutput(), Set.of(), List.of(
                        new LootTableProvider.SubProviderEntry(OccultismBlockLoot::new, LootContextParamSets.BLOCK),
                        new LootTableProvider.SubProviderEntry(OccultismEntityLoot::new, LootContextParamSets.ENTITY)
                )));
        generator.addProvider(event.includeServer(), new PentacleProvider(generator));
        generator.addProvider(event.includeServer(), new OccultismAdvancementProvider(generator));
        generator.addProvider(event.includeClient(), new ItemModelsGenerator(generator, event.getExistingFileHelper()));
        generator.addProvider(event.includeClient(), new StandardBlockStateProvider(generator, event.getExistingFileHelper()));

        var enUSProvider = new ENUSProvider(generator);
        generator.addProvider(event.includeServer(), new OccultismBookProvider(generator, Occultism.MODID, enUSProvider));

        //Important: Lang provider (in this case enus) needs to be added after the book provider to process the texts added by the book provider
        generator.addProvider(event.includeClient(), enUSProvider);
        generator.addProvider(event.includeClient(), new FRFRProvider(generator));
        generator.addProvider(event.includeClient(), new PTBRProvider(generator));

        registerFeatures(event);

    }

    public static void registerFeatures(GatherDataEvent event) {
        var generator = event.getGenerator();

        var registries =   RegistryAccess.fromRegistryOfRegistries(BuiltInRegistries.REGISTRY);
        var ops = RegistryOps.create(JsonOps.INSTANCE, registries);

        var silverOreConfiguredKey = ResourceKey.create(Registries.CONFIGURED_FEATURE, new ResourceLocation(Occultism.MODID, "silver_ore"));
        var silverOreConfiguredHolder =  ops.getter(Registries.CONFIGURED_FEATURE).get().getOrThrow(silverOreConfiguredKey);
//
//        var silverOreDeepslateConfiguredKey = ResourceKey.create(Registry.CONFIGURED_FEATURE_REGISTRY, new ResourceLocation(Occultism.MODID, "silver_ore_deepslate"));
//        var silverOreDeepslateConfiguredHolder = ops.registry(Registry.CONFIGURED_FEATURE_REGISTRY).get().getOrCreateHolderOrThrow(silverOreDeepslateConfiguredKey);
//
//        var iesniumOreConfiguredKey = ResourceKey.create(Registry.CONFIGURED_FEATURE_REGISTRY, new ResourceLocation(Occultism.MODID, "iesnium_ore"));
//        var iesniumOreConfiguredHolder = ops.registry(Registry.CONFIGURED_FEATURE_REGISTRY).get().getOrCreateHolderOrThrow(iesniumOreConfiguredKey);
//
//        var undergroundGroveConfiguredKey = ResourceKey.create(Registry.CONFIGURED_FEATURE_REGISTRY, new ResourceLocation(Occultism.MODID, "underground_grove"));
//        var undergroundGroveConfiguredHolder = ops.registry(Registry.CONFIGURED_FEATURE_REGISTRY).get().getOrCreateHolderOrThrow(undergroundGroveConfiguredKey);
//
//        var otherworldTreeNaturalConfiguredKey = ResourceKey.create(Registry.CONFIGURED_FEATURE_REGISTRY, new ResourceLocation(Occultism.MODID, "otherworld_tree_natural"));
//        var otherworldTreeNaturalConfiguredHolder = ops.registry(Registry.CONFIGURED_FEATURE_REGISTRY).get().getOrCreateHolderOrThrow(otherworldTreeNaturalConfiguredKey);
//
//        var otherworldTreeConfiguredKey = ResourceKey.create(Registry.CONFIGURED_FEATURE_REGISTRY, new ResourceLocation(Occultism.MODID, "otherworld_tree"));
//        var otherworldTreeConfiguredHolder = ops.registry(Registry.CONFIGURED_FEATURE_REGISTRY).get().getOrCreateHolderOrThrow(otherworldTreeConfiguredKey);
//
        var silverOrePlacedKey = ResourceKey.create(Registries.PLACED_FEATURE, new ResourceLocation(Occultism.MODID, "silver_ore"));
        var silverOrePlacedHolder = ops.getter(Registries.PLACED_FEATURE).get().getOrThrow(silverOrePlacedKey);
//
//        var silverOreDeepslatePlacedKey = ResourceKey.create(Registry.PLACED_FEATURE_REGISTRY, new ResourceLocation(Occultism.MODID, "silver_ore_deepslate"));
//        var silverOreDeepslatePlacedHolder = ops.registry(Registry.PLACED_FEATURE_REGISTRY).get().getOrCreateHolderOrThrow(silverOreDeepslatePlacedKey);
//
//        var iesniumOrePlacedKey = ResourceKey.create(Registry.PLACED_FEATURE_REGISTRY, new ResourceLocation(Occultism.MODID, "iesnium_ore"));
//        var iesniumOrePlacedHolder = ops.registry(Registry.PLACED_FEATURE_REGISTRY).get().getOrCreateHolderOrThrow(iesniumOrePlacedKey);
//
//        var undergroundGrovePlacedKey = ResourceKey.create(Registry.PLACED_FEATURE_REGISTRY, new ResourceLocation(Occultism.MODID, "underground_grove"));
//        var undergroundGrovePlacedHolder = ops.registry(Registry.PLACED_FEATURE_REGISTRY).get().getOrCreateHolderOrThrow(undergroundGrovePlacedKey);
//
//        var otherworldTreeNaturalPlacedKey = ResourceKey.create(Registry.PLACED_FEATURE_REGISTRY, new ResourceLocation(Occultism.MODID, "otherworld_tree_natural"));
//        var otherworldTreeNaturalPlacedHolder = ops.registry(Registry.PLACED_FEATURE_REGISTRY).get().getOrCreateHolderOrThrow(otherworldTreeNaturalPlacedKey);
//
//        var otherworldTreePlacedKey = ResourceKey.create(Registry.PLACED_FEATURE_REGISTRY, new ResourceLocation(Occultism.MODID, "otherworld_tree"));
//        var otherworldTreePlacedHolder = ops.registry(Registry.PLACED_FEATURE_REGISTRY).get().getOrCreateHolderOrThrow(otherworldTreePlacedKey);


        var silverOreConfigured = new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(
                new TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES),
                OccultismBlocks.SILVER_ORE.get().defaultBlockState(), 5));
//
//        var silverOreDeepslateConfigured = new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(
//                new TagMatchTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES),
//                OccultismBlocks.SILVER_ORE_DEEPSLATE.get().defaultBlockState(), 10));
//        var iesniumOreConfigured = new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(
//                new TagMatchTest(BlockTags.BASE_STONE_NETHER),
//                OccultismBlocks.IESNIUM_ORE_NATURAL.get().defaultBlockState(), 3));
//
//
//        var otherworldTreeNaturalConfigured = new ConfiguredFeature<>(Feature.TREE,
//                new TreeConfiguration.TreeConfigurationBuilder(
//                        BlockStateProvider.simple(OccultismBlocks.OTHERWORLD_LOG_NATURAL.get().defaultBlockState()),
//                        new StraightTrunkPlacer(4, 2, 0),
//                        BlockStateProvider.simple(OccultismBlocks.OTHERWORLD_LEAVES_NATURAL.get().defaultBlockState()),
//                        new BlobFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0), 3),
//                        new TwoLayersFeatureSize(1, 0, 1)).ignoreVines().build());
//
//        var otherworldTreeConfigured = new ConfiguredFeature<>(Feature.TREE,
//                new TreeConfiguration.TreeConfigurationBuilder(
//                        BlockStateProvider.simple(OccultismBlocks.OTHERWORLD_LOG.get().defaultBlockState()),
//                        new StraightTrunkPlacer(4, 2, 0),
//                        BlockStateProvider.simple(OccultismBlocks.OTHERWORLD_LEAVES.get().defaultBlockState()),
//                        new BlobFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0), 3),
//                        new TwoLayersFeatureSize(1, 0, 1)).ignoreVines().build());


        var silverOrePlaced = new PlacedFeature(silverOreConfiguredHolder,
                OccultismFeatures.commonOrePlacement(7, HeightRangePlacement.triangle(VerticalAnchor.absolute(50), VerticalAnchor.absolute(200))));
//        var silverOreDeepslatePlaced = new PlacedFeature(silverOreDeepslateConfiguredHolder,
//                OccultismFeatures.commonOrePlacement(10, HeightRangePlacement.triangle(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(50))));
//        var iesniumOrePlaced =
//                new PlacedFeature(iesniumOreConfiguredHolder,
//                        OccultismFeatures.commonOrePlacement(3, HeightRangePlacement.triangle(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(50))));
//
//        var otherworldTreeNaturalPlaced = new PlacedFeature(otherworldTreeNaturalConfiguredHolder, List.of(
//                PlacementUtils.filteredByBlockSurvival(OccultismBlocks.OTHERWORLD_SAPLING_NATURAL.get())));
//        var otherworldTreePlaced = new PlacedFeature(otherworldTreeConfiguredHolder, List.of(
//                PlacementUtils.filteredByBlockSurvival(OccultismBlocks.OTHERWORLD_SAPLING.get())));

//
//        var undergroundGroveConfigured = new ConfiguredFeature<>(OccultismFeatures.UNDERGROUND_GROVE_FEATURE.get(),
//                new MultiChunkFeatureConfig(
//                        7,
//                        400,
//                        25,
//                        60,
//                        14653667,
//                        0.6f,
//                        0.1f,
//                        0.3f,
//                        0.1f,
//                        otherworldTreeNaturalPlacedHolder));
//        var undergroundGrovePlaced = new PlacedFeature(undergroundGroveConfiguredHolder, List.of());


        //biome modifiers
        var addSilverOre = new AddFeaturesBiomeModifier(
                ops.getter(Registries.BIOME).get().getOrThrow( BiomeTags.IS_OVERWORLD),
                HolderSet.direct(silverOrePlacedHolder), Decoration.UNDERGROUND_ORES);
//        var addDeepslateSilverOre = new AddFeaturesBiomeModifier(
//                new HolderSet.Named<>(ops.registry(Registry.BIOME_REGISTRY).get(), BiomeTags.IS_OVERWORLD),
//                HolderSet.direct(silverOreDeepslatePlacedHolder), Decoration.UNDERGROUND_ORES);
//        var addIesniumOre = new AddFeaturesBiomeModifier(
//                new HolderSet.Named<>(ops.registry(Registry.BIOME_REGISTRY).get(), BiomeTags.IS_NETHER),
//                HolderSet.direct(iesniumOrePlacedHolder), Decoration.UNDERGROUND_ORES);
//        var addUndergroundGrove = new AddFeaturesBiomeModifier(
//                new HolderSet.Named<>(ops.registry(Registry.BIOME_REGISTRY).get(), BiomeTags.IS_OVERWORLD),
//                HolderSet.direct(undergroundGrovePlacedHolder), Decoration.UNDERGROUND_ORES);
//        var addOtherworldTreeNatural = new AddFeaturesBiomeModifier(
//                new HolderSet.Named<>(ops.registry(Registry.BIOME_REGISTRY).get(), BiomeTags.IS_OVERWORLD),
//                HolderSet.direct(otherworldTreeNaturalPlacedHolder), Decoration.UNDERGROUND_ORES);
//        var addOtherworldTree = new AddFeaturesBiomeModifier(
//                new HolderSet.Named<>(ops.registry(Registry.BIOME_REGISTRY).get(), BiomeTags.IS_OVERWORLD),
//                HolderSet.direct(otherworldTreePlacedHolder), Decoration.UNDERGROUND_ORES);

        generator.addProvider(event.includeServer(), JsonCodecProvider.forDatapackRegistry(
                generator, event.getExistingFileHelper(), Occultism.MODID, ops, Registries.CONFIGURED_FEATURE,
                Map.of(
                        silverOreConfiguredKey.location(), silverOreConfigured
//                        silverOreDeepslateConfiguredKey.location(), silverOreDeepslateConfigured,
//                        iesniumOreConfiguredKey.location(), iesniumOreConfigured,
//                        undergroundGroveConfiguredKey.location(), undergroundGroveConfigured,
//                        otherworldTreeNaturalConfiguredKey.location(), otherworldTreeNaturalConfigured,
//                        otherworldTreeConfiguredKey.location(), otherworldTreeConfigured
                )));

        generator.addProvider(event.includeServer(), JsonCodecProvider.forDatapackRegistry(
                generator, event.getExistingFileHelper(), Occultism.MODID, ops, Registries.PLACED_FEATURE,
                Map.of(
                        silverOrePlacedKey.location(), silverOrePlaced
//                        silverOreDeepslatePlacedKey.location(), silverOreDeepslatePlaced,
//                        iesniumOrePlacedKey.location(), iesniumOrePlaced,
//                        otherworldTreeNaturalPlacedKey.location(), otherworldTreeNaturalPlaced,
//                        otherworldTreePlacedKey.location(), otherworldTreePlaced,
//                        undergroundGrovePlacedKey.location(), undergroundGrovePlaced
                )));

        generator.addProvider(event.includeServer(), JsonCodecProvider.forDatapackRegistry(
                generator, event.getExistingFileHelper(), Occultism.MODID, ops, ForgeRegistries.Keys.BIOME_MODIFIERS,
                Map.of(
                        new ResourceLocation(Occultism.MODID, "add_silver_ore"), addSilverOre
//                        new ResourceLocation(Occultism.MODID, "add_deepslate_silver_ore"), addDeepslateSilverOre,
//                        new ResourceLocation(Occultism.MODID, "add_iesnium_ore"), addIesniumOre,
//                        new ResourceLocation(Occultism.MODID, "add_underground_grove"), addUndergroundGrove,
//                        new ResourceLocation(Occultism.MODID, "add_otherworld_tree_natural"), addOtherworldTreeNatural,
//                        new ResourceLocation(Occultism.MODID, "add_otherworld_tree"), addOtherworldTree
                )));

    }
}