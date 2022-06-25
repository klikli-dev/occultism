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
import com.github.klikli_dev.occultism.registry.OccultismBlocks;
import com.github.klikli_dev.occultism.registry.OccultismFeatures;
import com.mojang.serialization.JsonOps;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.RegistryOps;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.levelgen.GenerationStep.Decoration;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.placement.HeightRangePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;
import net.minecraftforge.common.data.JsonCodecProvider;
import net.minecraftforge.common.world.ForgeBiomeModifiers.AddFeaturesBiomeModifier;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

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
        //generator.addProvider(event.includeClient(), new ENUSProvider(generator));

        registerFeatures(event);

    }

    public static void registerFeatures(GatherDataEvent event) {
        var generator = event.getGenerator();

        var registries = RegistryAccess.builtinCopy();
        var ops = RegistryOps.create(JsonOps.INSTANCE, registries);

        Holder<ConfiguredFeature<?, ?>> silverOreConfigured = Holder.direct(new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(
                new TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES),
                OccultismBlocks.SILVER_ORE.get().defaultBlockState(), 7)));
        Holder<ConfiguredFeature<?, ?>> silverOreDeepslateConfigured = Holder.direct(new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(
                new TagMatchTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES),
                OccultismBlocks.SILVER_ORE_DEEPSLATE.get().defaultBlockState(), 7)));

        generator.addProvider(event.includeServer(), JsonCodecProvider.forDatapackRegistry(
                generator, event.getExistingFileHelper(), Occultism.MODID, ops, Registry.CONFIGURED_FEATURE_REGISTRY,
                Map.of(
                        new ResourceLocation(Occultism.MODID, "silver_ore"), silverOreConfigured.get(),
                        new ResourceLocation(Occultism.MODID, "silver_ore_deepslate"), silverOreDeepslateConfigured.get()

                )));

        var silverOrePlaced = Holder.direct(new PlacedFeature(silverOreConfigured,
                OccultismFeatures.commonOrePlacement(3, HeightRangePlacement.triangle(VerticalAnchor.absolute(50), VerticalAnchor.absolute(200)))));
        var silverOreDeepslatePlaced = Holder.direct(new PlacedFeature(silverOreDeepslateConfigured,
                OccultismFeatures.commonOrePlacement(3, HeightRangePlacement.triangle(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(50)))));



        generator.addProvider(event.includeServer(), JsonCodecProvider.forDatapackRegistry(
                generator, event.getExistingFileHelper(), Occultism.MODID, ops, Registry.PLACED_FEATURE_REGISTRY,
                Map.of(
                        new ResourceLocation(Occultism.MODID, "silver_ore"), silverOrePlaced.get(),
                        new ResourceLocation(Occultism.MODID, "silver_ore_deepslate"), silverOreDeepslatePlaced.get()
                )));

        var addSilverOre = new AddFeaturesBiomeModifier(
                new HolderSet.Named<>(ops.registry(Registry.BIOME_REGISTRY).get(), BiomeTags.IS_OVERWORLD),
                HolderSet.direct(silverOrePlaced), Decoration.UNDERGROUND_ORES);
        var addDeepslateSilverOre = new AddFeaturesBiomeModifier(
                new HolderSet.Named<>(ops.registry(Registry.BIOME_REGISTRY).get(), BiomeTags.IS_OVERWORLD),
                HolderSet.direct(silverOreDeepslatePlaced), Decoration.UNDERGROUND_ORES);

        generator.addProvider(event.includeServer(), JsonCodecProvider.forDatapackRegistry(
                generator, event.getExistingFileHelper(), Occultism.MODID, ops, ForgeRegistries.Keys.BIOME_MODIFIERS,
                Map.of(
                        new ResourceLocation(Occultism.MODID, "add_silver_ore"), addSilverOre,
                        new ResourceLocation(Occultism.MODID, "add_deepslate_silver_ore"), addDeepslateSilverOre
                )));

        //TODO: add all other features
    }
}