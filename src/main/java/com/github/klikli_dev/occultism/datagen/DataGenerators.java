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
import com.github.klikli_dev.occultism.datagen.lang.ENUSProvider;
import com.github.klikli_dev.occultism.datagen.lang.FRFRProvider;
import com.github.klikli_dev.occultism.datagen.lang.PTBRProvider;
import com.github.klikli_dev.occultism.datagen.lang.loot.OccultismBlockLoot;
import com.github.klikli_dev.occultism.datagen.lang.loot.OccultismEntityLoot;
import com.github.klikli_dev.occultism.datagen.worldgen.OccultismRegistries;
import net.minecraft.Util;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.data.registries.RegistriesDatapackGenerator;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiFunction;

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
        generator.addProvider(event.includeServer(), new CrushingRecipeProvider(generator.getPackOutput()));
        generator.addProvider(event.includeClient(), new ItemModelsGenerator(generator.getPackOutput(), event.getExistingFileHelper()));
        generator.addProvider(event.includeClient(), new StandardBlockStateProvider(generator.getPackOutput(), event.getExistingFileHelper()));


        var enUSProvider = new ENUSProvider(generator.getPackOutput());
        generator.addProvider(event.includeServer(), new OccultismBookProvider(generator.getPackOutput(), Occultism.MODID, enUSProvider));

        //Important: Lang provider (in this case enus) needs to be added after the book provider to process the texts added by the book provider
        generator.addProvider(event.includeClient(), enUSProvider);
        generator.addProvider(event.includeClient(), new FRFRProvider(generator.getPackOutput()));
        generator.addProvider(event.includeClient(), new PTBRProvider(generator.getPackOutput()));

        var holderLookup = CompletableFuture.supplyAsync(OccultismRegistries::createLookup, Util.backgroundExecutor());
        generator.addProvider(event.includeServer(), bindRegistries(RegistriesDatapackGenerator::new, holderLookup));
    }

    private static <T extends DataProvider> DataProvider.Factory<T> bindRegistries(BiFunction<PackOutput, CompletableFuture<HolderLookup.Provider>, T> datagenFactory, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        return (packOutput) -> {
            return datagenFactory.apply(packOutput, lookupProvider);
        };
    }
}