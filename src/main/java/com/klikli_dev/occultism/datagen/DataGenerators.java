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

import com.klikli_dev.occultism.Occultism;
import com.klikli_dev.occultism.datagen.lang.ENUSProvider;
import com.klikli_dev.occultism.datagen.loot.OccultismBlockLoot;
import com.klikli_dev.occultism.datagen.loot.OccultismEntityLoot;
import com.klikli_dev.occultism.datagen.loot.OccultismLootModifiers;
import com.klikli_dev.occultism.datagen.tags.OccultismBiomeTagProvider;
import com.klikli_dev.occultism.datagen.tags.OccultismBlockTagProvider;
import com.klikli_dev.occultism.datagen.tags.OccultismEntityTypeTagProvider;
import com.klikli_dev.occultism.datagen.tags.OccultismItemTagProvider;
import com.klikli_dev.occultism.datagen.worldgen.OccultismRegistries;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.data.AdvancementProvider;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.List;
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
        generator.addProvider(event.includeServer(),
                new AdvancementProvider(generator.getPackOutput(), event.getLookupProvider(), event.getExistingFileHelper(), List.of(
                        new OccultismAdvancementSubProvider()
                )));


        generator.addProvider(event.includeServer(), new CrushingRecipeProvider(generator.getPackOutput()));
        generator.addProvider(event.includeServer(), new MinerRecipeProvider(generator.getPackOutput()));
        // Forge Tags
        OccultismBlockTagProvider forgeBlockProvider = new OccultismBlockTagProvider(generator.getPackOutput(), event.getLookupProvider(), event.getExistingFileHelper());
        generator.addProvider(event.includeServer(), forgeBlockProvider);
        generator.addProvider(event.includeServer(), new OccultismEntityTypeTagProvider(generator.getPackOutput(), event.getLookupProvider(), event.getExistingFileHelper()));
        generator.addProvider(event.includeServer(), new OccultismItemTagProvider(generator.getPackOutput(), event.getLookupProvider(), forgeBlockProvider.contentsGetter(), event.getExistingFileHelper()));
        generator.addProvider(event.includeServer(), new OccultismBiomeTagProvider(generator.getPackOutput(), event.getLookupProvider(), event.getExistingFileHelper()));
        generator.addProvider(event.includeClient(), new ItemModelsGenerator(generator.getPackOutput(), event.getExistingFileHelper()));
        generator.addProvider(event.includeClient(), new StandardBlockStateProvider(generator.getPackOutput(), event.getExistingFileHelper()));
        generator.addProvider(event.includeClient(), new OccultismLootModifiers(generator.getPackOutput()));

        var enUSProvider = new ENUSProvider(generator.getPackOutput());
        generator.addProvider(event.includeServer(), new OccultismBookProvider(generator.getPackOutput(), Occultism.MODID, enUSProvider));

        //Important: Lang provider (in this case enus) needs to be added after the book provider to process the texts added by the book provider
        generator.addProvider(event.includeClient(), enUSProvider);

        event.getGenerator().addProvider(event.includeServer(),
                (DataProvider.Factory<DatapackBuiltinEntriesProvider>) output ->
                        new DatapackBuiltinEntriesProvider(output, event.getLookupProvider(), OccultismRegistries.BUILDER, Set.of(Occultism.MODID)));
    }

}