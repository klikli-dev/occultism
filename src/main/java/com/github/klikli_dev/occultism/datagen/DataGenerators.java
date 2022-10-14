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
import com.github.klikli_dev.occultism.registry.OccultismBlocks;
import com.github.klikli_dev.occultism.registry.OccultismFeatures;
import com.mojang.serialization.JsonOps;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.RegistryOps;
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
import net.minecraftforge.common.data.JsonCodecProvider;
import net.minecraftforge.common.world.ForgeBiomeModifiers.AddFeaturesBiomeModifier;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;
import java.util.Map;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenerators {

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        generator.addProvider(event.includeServer(), new StandardLootTableProvider(generator));
        generator.addProvider(event.includeServer(), new PentacleProvider(generator));
        generator.addProvider(event.includeServer(), new OccultismAdvancementProvider(generator));
        generator.addProvider(event.includeClient(), new ItemModelsGenerator(generator, event.getExistingFileHelper()));
        generator.addProvider(event.includeClient(), new StandardBlockStateProvider(generator, event.getExistingFileHelper()));
        generator.addProvider(event.includeClient(), new ENUSProvider(generator));
        generator.addProvider(event.includeClient(), new FRFRProvider(generator));
        generator.addProvider(event.includeServer(), new BookGenerator(generator, Occultism.MODID));

        registerFeatures(event);

    }

    public static void registerFeatures(GatherDataEvent event) {
        var generator = event.getGenerator();

        var registries = RegistryAccess.builtinCopy();
        var ops = RegistryOps.create(JsonOps.INSTANCE, registries);

        Holder<ConfiguredFeature<?, ?>> silverOreConfigured = Holder.direct(new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(
                new TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES),
                OccultismBlocks.SILVER_ORE.get().defaultBlockState(), 5)));
        Holder<ConfiguredFeature<?, ?>> silverOreDeepslateConfigured = Holder.direct(new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(
                new TagMatchTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES),
                OccultismBlocks.SILVER_ORE_DEEPSLATE.get().defaultBlockState(), 10)));
        Holder<ConfiguredFeature<?, ?>> iesniumOreConfigured = Holder.direct(
                new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(
                        new TagMatchTest(BlockTags.BASE_STONE_NETHER),
                        OccultismBlocks.IESNIUM_ORE_NATURAL.get().defaultBlockState(), 3)));


        Holder<ConfiguredFeature<?, ?>> otherworldTreeNaturalConfigured = Holder.direct(
                new ConfiguredFeature<>(Feature.TREE,
                        new TreeConfiguration.TreeConfigurationBuilder(
                                BlockStateProvider.simple(OccultismBlocks.OTHERWORLD_LOG_NATURAL.get().defaultBlockState()),
                                new StraightTrunkPlacer(4, 2, 0),
                                BlockStateProvider.simple(OccultismBlocks.OTHERWORLD_LEAVES_NATURAL.get().defaultBlockState()),
                                new BlobFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0), 3),
                                new TwoLayersFeatureSize(1, 0, 1)).ignoreVines().build()));

        Holder<ConfiguredFeature<?, ?>> otherworldTreeConfigured = Holder.direct(
                new ConfiguredFeature<>(Feature.TREE,
                        new TreeConfiguration.TreeConfigurationBuilder(
                                BlockStateProvider.simple(OccultismBlocks.OTHERWORLD_LOG.get().defaultBlockState()),
                                new StraightTrunkPlacer(4, 2, 0),
                                BlockStateProvider.simple(OccultismBlocks.OTHERWORLD_LEAVES.get().defaultBlockState()),
                                new BlobFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0), 3),
                                new TwoLayersFeatureSize(1, 0, 1)).ignoreVines().build()));

        var silverOrePlaced = Holder.direct(new PlacedFeature(silverOreConfigured,
                OccultismFeatures.commonOrePlacement(7, HeightRangePlacement.triangle(VerticalAnchor.absolute(50), VerticalAnchor.absolute(200)))));
        var silverOreDeepslatePlaced = Holder.direct(new PlacedFeature(silverOreDeepslateConfigured,
                OccultismFeatures.commonOrePlacement(10, HeightRangePlacement.triangle(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(50)))));
        var iesniumOrePlaced = Holder.direct(
                new PlacedFeature(iesniumOreConfigured,
                        OccultismFeatures.commonOrePlacement(3, HeightRangePlacement.triangle(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(50)))));


        var otherworldTreeNaturalPlaced = Holder.direct(new PlacedFeature(otherworldTreeNaturalConfigured, List.of(
                PlacementUtils.filteredByBlockSurvival(OccultismBlocks.OTHERWORLD_SAPLING_NATURAL.get()))));
        var otherworldTreePlaced = Holder.direct(new PlacedFeature(otherworldTreeConfigured, List.of(
                PlacementUtils.filteredByBlockSurvival(OccultismBlocks.OTHERWORLD_SAPLING.get()))));


        Holder<ConfiguredFeature<?, ?>> undergroundGroveConfigured = Holder.direct(
                new ConfiguredFeature<>(OccultismFeatures.UNDERGROUND_GROVE_FEATURE.get(),
                        new MultiChunkFeatureConfig(
                                7,
                                400,
                                25,
                                60,
                                14653667,
                                0.6f,
                                0.1f,
                                0.3f,
                                0.1f,
                                otherworldTreeNaturalPlaced)));
        var undergroundGrovePlaced = Holder.direct(new PlacedFeature(undergroundGroveConfigured, List.of()));


        //biome modifiers
        var addSilverOre = new AddFeaturesBiomeModifier(
                new HolderSet.Named<>(ops.registry(Registry.BIOME_REGISTRY).get(), BiomeTags.IS_OVERWORLD),
                HolderSet.direct(silverOrePlaced), Decoration.UNDERGROUND_ORES);
        var addDeepslateSilverOre = new AddFeaturesBiomeModifier(
                new HolderSet.Named<>(ops.registry(Registry.BIOME_REGISTRY).get(), BiomeTags.IS_OVERWORLD),
                HolderSet.direct(silverOreDeepslatePlaced), Decoration.UNDERGROUND_ORES);
        var addIesniumOre = new AddFeaturesBiomeModifier(
                new HolderSet.Named<>(ops.registry(Registry.BIOME_REGISTRY).get(), BiomeTags.IS_NETHER),
                HolderSet.direct(iesniumOrePlaced), Decoration.UNDERGROUND_ORES);
        var addUndergroundGrove = new AddFeaturesBiomeModifier(
                new HolderSet.Named<>(ops.registry(Registry.BIOME_REGISTRY).get(), BiomeTags.IS_OVERWORLD),
                HolderSet.direct(undergroundGrovePlaced), Decoration.UNDERGROUND_ORES);
        var addOtherworldTreeNatural = new AddFeaturesBiomeModifier(
                new HolderSet.Named<>(ops.registry(Registry.BIOME_REGISTRY).get(), BiomeTags.IS_OVERWORLD),
                HolderSet.direct(otherworldTreeNaturalPlaced), Decoration.UNDERGROUND_ORES);
        var addOtherworldTree = new AddFeaturesBiomeModifier(
                new HolderSet.Named<>(ops.registry(Registry.BIOME_REGISTRY).get(), BiomeTags.IS_OVERWORLD),
                HolderSet.direct(otherworldTreePlaced), Decoration.UNDERGROUND_ORES);

        generator.addProvider(event.includeServer(), JsonCodecProvider.forDatapackRegistry(
                generator, event.getExistingFileHelper(), Occultism.MODID, ops, Registry.CONFIGURED_FEATURE_REGISTRY,
                Map.of(
                        new ResourceLocation(Occultism.MODID, "silver_ore"), silverOreConfigured.get(),
                        new ResourceLocation(Occultism.MODID, "silver_ore_deepslate"), silverOreDeepslateConfigured.get(),
                        new ResourceLocation(Occultism.MODID, "iesnium_ore"), iesniumOreConfigured.get(),
                        new ResourceLocation(Occultism.MODID, "underground_grove"), undergroundGroveConfigured.get(),
                        new ResourceLocation(Occultism.MODID, "otherworld_tree_natural"), otherworldTreeNaturalConfigured.get(),
                        new ResourceLocation(Occultism.MODID, "otherworld_tree"), otherworldTreeConfigured.get()

                )));

        generator.addProvider(event.includeServer(), JsonCodecProvider.forDatapackRegistry(
                generator, event.getExistingFileHelper(), Occultism.MODID, ops, Registry.PLACED_FEATURE_REGISTRY,
                Map.of(
                        new ResourceLocation(Occultism.MODID, "silver_ore"), silverOrePlaced.get(),
                        new ResourceLocation(Occultism.MODID, "silver_ore_deepslate"), silverOreDeepslatePlaced.get(),
                        new ResourceLocation(Occultism.MODID, "iesnium_ore"), iesniumOrePlaced.get(),
                        new ResourceLocation(Occultism.MODID, "underground_grove"), undergroundGrovePlaced.get(),
                        new ResourceLocation(Occultism.MODID, "otherworld_tree_natural"), otherworldTreeNaturalPlaced.get(),
                        new ResourceLocation(Occultism.MODID, "otherworld_tree"), otherworldTreePlaced.get()
                )));

        generator.addProvider(event.includeServer(), JsonCodecProvider.forDatapackRegistry(
                generator, event.getExistingFileHelper(), Occultism.MODID, ops, ForgeRegistries.Keys.BIOME_MODIFIERS,
                Map.of(
                        new ResourceLocation(Occultism.MODID, "add_silver_ore"), addSilverOre,
                        new ResourceLocation(Occultism.MODID, "add_deepslate_silver_ore"), addDeepslateSilverOre,
                        new ResourceLocation(Occultism.MODID, "add_iesnium_ore"), addIesniumOre,
                        new ResourceLocation(Occultism.MODID, "add_underground_grove"), addUndergroundGrove,
                        new ResourceLocation(Occultism.MODID, "add_otherworld_tree_natural"), addOtherworldTreeNatural,
                        new ResourceLocation(Occultism.MODID, "add_otherworld_tree"), addOtherworldTree
                )));

    }
}