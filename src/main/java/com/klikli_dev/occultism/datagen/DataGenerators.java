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
import net.minecraft.advancements.Advancement;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.MultiRegistryBootstrap;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.advancements.AdvancementProvider;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.predicates.LootPredicates;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent.Client;

import java.util.List;
import java.util.Set;

@EventBusSubscriber()
public class DataGenerators {

    @SubscribeEvent
    public static void gatherData(Client event) {
        DataGenerator generator = event.getGenerator();

        // Register world registry objects (biomes, features, enchantments) and datapack entries
        event.createWorldRegistryObjects(OccultismRegistries.WORLD_BUILDER);

        // Register all reloadable registries (advancements, recipes, loot tables) in one call
        event.createReloadableRegistryObjects(
                new RegistrySetBuilder()
                        .add(Registries.PREDICATE, LootPredicates::bootstrap)
                        .add(Registries.ADVANCEMENT, new AdvancementProvider(List.of(
                                OccultismAdvancementSubProvider::new
                        )))
                        .add(Registries.LOOT_TABLE, new OccultismLootTableProvider(Set.of(), List.of(
                                new LootTableProvider.SubProviderEntry(OccultismBlockLoot::new, LootContextParamSets.BLOCK),
                                new LootTableProvider.SubProviderEntry(OccultismEntityLoot::new, LootContextParamSets.ENTITY)
                        )))
                        .add(new MultiRegistryBootstrap() {
                            @Override
                            public Set<ResourceKey<? extends Registry<?>>> requestedRegistries() {
                                return Set.of(Registries.RECIPE, Registries.ADVANCEMENT);
                            }

                            @Override
                            public void run(MultiRegistryBootstrap.BootstrapGetter registries) {
                                new OccultismRecipeProvider(registries.get(Registries.RECIPE), registries.get(Registries.ADVANCEMENT)).run();
                            }
                        }),
                Set.of(Occultism.MODID)
        );

        //Fetch the lookup only after registering world and reloadable entries, otherwise
        //it resolves to the vanilla registries and datapack references (e.g. enchantments) fail validation.
        var lookup = event.getReloadableLookupProvider();

        generator.addProvider(true, new PentacleProvider(generator));

        OccultismBlockTagProvider forgeBlockProvider = new OccultismBlockTagProvider(generator.getPackOutput(), lookup);
        generator.addProvider(true, forgeBlockProvider);
        generator.addProvider(true, new OccultismEntityTypeTagProvider(generator.getPackOutput(), lookup));
        generator.addProvider(true, new OccultismItemTagProvider(generator.getPackOutput(), lookup, forgeBlockProvider.contentsGetter()));
        generator.addProvider(true, new OccultismBiomeTagProvider(generator.getPackOutput(), lookup));
        generator.addProvider(true, new OccultismEnchantmentTagProvider(generator.getPackOutput(), lookup));
        generator.addProvider(true, new OccultismModelProvider(generator.getPackOutput()));
        generator.addProvider(true, new OccultismLootModifiers(generator.getPackOutput(), lookup));

        var langCache = new LanguageProviderCache("en_us");
        var researchCache = new ResearchCache();

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
