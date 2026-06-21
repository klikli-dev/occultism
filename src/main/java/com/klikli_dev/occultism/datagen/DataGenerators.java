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

package com.klikli_dev.occultism.datagen;

import com.klikli_dev.modonomicon.api.datagen.LanguageProviderCache;
import com.klikli_dev.modonomicon.api.datagen.NeoBookProvider;
import com.klikli_dev.modonomicon.api.datagen.NeoResearchProvider;
import com.klikli_dev.modonomicon.api.datagen.research.ResearchCache;
import com.klikli_dev.occultism.Occultism;
import com.klikli_dev.occultism.datagen.lang.ENUSProvider;
import com.klikli_dev.occultism.datagen.loot.OccultismBlockLoot;
import com.klikli_dev.occultism.datagen.loot.OccultismEntityLoot;
import com.klikli_dev.occultism.datagen.loot.OccultismLootModifiers;
import com.klikli_dev.occultism.datagen.loot.OccultismLootTableProvider;
import com.klikli_dev.occultism.datagen.model.OccultismModelProvider;
import com.klikli_dev.occultism.datagen.recipe.OccultismRecipeProvider;
import com.klikli_dev.occultism.datagen.tags.*;
import com.klikli_dev.occultism.datagen.worldgen.OccultismRegistries;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.advancements.AdvancementProvider;
import net.minecraft.data.loot.LootTableProvider.SubProviderEntry;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.RecipeProvider.Runner;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.neoforged.neoforge.data.event.GatherDataEvent.Client;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

@EventBusSubscriber()
public class DataGenerators {

    @SubscribeEvent
    public static void gatherData(Client event) {
        DataGenerator generator = event.getGenerator();

        //Used for enchantment
        DatapackBuiltinEntriesProvider datapackProvider =
                new DatapackBuiltinEntriesProvider(
                        generator.getPackOutput(),
                        event.getLookupProvider(),
                        OccultismRegistries.BUILDER,
                        Set.of(Occultism.MODID)
                );
        generator.addProvider(true, datapackProvider);
        CompletableFuture<Provider> lookup = datapackProvider.getRegistryProvider();

        generator.addProvider(true,
                new OccultismLootTableProvider(generator.getPackOutput(), Set.of(), List.of(
                        new SubProviderEntry(OccultismBlockLoot::new, LootContextParamSets.BLOCK),
                        new SubProviderEntry(OccultismEntityLoot::new, LootContextParamSets.ENTITY)
                ), event.getLookupProvider()));
        generator.addProvider(true, new PentacleProvider(generator));
        generator.addProvider(true,
                new AdvancementProvider(generator.getPackOutput(), event.getLookupProvider(), List.of(
                        new OccultismAdvancementSubProvider()
                )));


        OccultismBlockTagProvider forgeBlockProvider = new OccultismBlockTagProvider(generator.getPackOutput(), event.getLookupProvider());
        generator.addProvider(true, forgeBlockProvider);
        generator.addProvider(true, new OccultismEntityTypeTagProvider(generator.getPackOutput(), event.getLookupProvider()));
        generator.addProvider(true, new OccultismItemTagProvider(generator.getPackOutput(), event.getLookupProvider(), forgeBlockProvider.contentsGetter()));
        generator.addProvider(true, new OccultismBiomeTagProvider(generator.getPackOutput(), event.getLookupProvider()));
        generator.addProvider(true, new OccultismEnchantmentTagProvider(generator.getPackOutput(), lookup));
        generator.addProvider(true, new OccultismModelProvider(generator.getPackOutput()));
        generator.addProvider(true, new OccultismLootModifiers(generator.getPackOutput(), event.getLookupProvider()));

        var langCache = new LanguageProviderCache("en_us");
        var researchCache = new ResearchCache();

        // Generate recipes using RecipeProvider.Runner - the standard way in 26.1
        // RecipeProvider.Runner is an abstract runner that must be subclassed to provide the concrete provider.
        generator.addProvider(true, new Runner(generator.getPackOutput(), event.getLookupProvider()) {
            @Override
            protected RecipeProvider createRecipeProvider(Provider registries, RecipeOutput output) {
                return OccultismRecipeProvider.create(registries, output);
            }

            @Override
            public String getName() {
                return "Occultism Recipe Provider Runner";
            }
        });

        generator.addProvider(true, NeoBookProvider.of(event, langCache, researchCache,
                new OccultismBookProvider()
        ));
        generator.addProvider(true, NeoResearchProvider.of(event, langCache, researchCache,
                new OccultismResearch(Occultism.MODID)
        ));

        //Important: Lang provider (in this case enus) needs to be added after the book provider to process the texts added by the book provider
        generator.addProvider(true, new ENUSProvider(generator.getPackOutput(), langCache));
    }

}
