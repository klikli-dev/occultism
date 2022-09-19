/*
 * MIT License
 *
 * Copyright 2020 klikli-dev, McJty
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
import net.minecraft.data.DataGenerator;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;

/**
 * Based on https://github.com/McJty/YouTubeModding14
 */
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenerators {

    //region Static Methods
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        if (event.includeServer()) {
            generator.addProvider(new StandardLootTableProvider(generator));
            generator.addProvider(new PentacleProvider(generator));
            generator.addProvider(new OccultismAdvancementProvider(generator));
            generator.addProvider(new BookGenerator(generator, Occultism.MODID));
        }
        if (event.includeClient()) {
            generator.addProvider(new ItemModelsGenerator(generator, event.getExistingFileHelper()));
            generator.addProvider(new StandardBlockStateProvider(generator, event.getExistingFileHelper()));
            generator.addProvider(new ENUSProvider(generator));
            generator.addProvider(new FRFRProvider(generator));
        }
    }
    //endregion Static Methods
}