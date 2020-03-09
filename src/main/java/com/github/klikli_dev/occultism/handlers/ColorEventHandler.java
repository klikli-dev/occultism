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

package com.github.klikli_dev.occultism.handlers;

import com.github.klikli_dev.occultism.Occultism;
import com.github.klikli_dev.occultism.common.block.otherworld.IOtherworldBlock;
import com.github.klikli_dev.occultism.common.block.otherworld.OtherworldLeavesNaturalBlock;
import com.github.klikli_dev.occultism.registry.OccultismBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.item.BlockItem;
import net.minecraft.world.FoliageColors;
import net.minecraft.world.biome.BiomeColors;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Occultism.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ColorEventHandler {

    //region Static Methods
    @SubscribeEvent
    public static void onRegisterBlockColors(ColorHandlerEvent.Block event) {
        event.getBlockColors()
                .register((state, light, pos, tintIndex) -> OccultismBlocks.CHALK_GLYPH_WHITE.get().getColor(),
                        OccultismBlocks.CHALK_GLYPH_WHITE.get());
        event.getBlockColors()
                .register((state, light, pos, tintIndex) -> OccultismBlocks.CHALK_GLYPH_GOLD.get().getColor(),
                        OccultismBlocks.CHALK_GLYPH_GOLD.get());
        event.getBlockColors()
                .register((state, light, pos, tintIndex) -> OccultismBlocks.CHALK_GLYPH_PURPLE.get().getColor(),
                        OccultismBlocks.CHALK_GLYPH_PURPLE.get());
        event.getBlockColors()
                .register((state, light, pos, tintIndex) -> OccultismBlocks.CHALK_GLYPH_RED.get().getColor(),
                        OccultismBlocks.CHALK_GLYPH_RED.get());

        //Otherworld leaves shows in oak leaves color unless uncovered.
        event.getBlockColors()
                .register((state, light, pos, tintIndex) -> OtherworldLeavesNaturalBlock.COLOR,
                        OccultismBlocks.OTHERWORLD_LEAVES.get());
        event.getBlockColors()
                .register((state, light, pos, tintIndex) -> state.get(
                        IOtherworldBlock.UNCOVERED) ? OtherworldLeavesNaturalBlock.COLOR :
                                                                    (light != null && pos != null ? BiomeColors
                                                                                                            .getFoliageColor(
                                                                                                                    light,
                                                                                                                    pos) : FoliageColors
                                                                                                                                   .getDefault()),
                        OccultismBlocks.OTHERWORLD_LEAVES_NATURAL.get());

        Occultism.LOGGER.info("Block color registration complete.");
    }

    @SubscribeEvent
    public static void onRegisterItemColors(ColorHandlerEvent.Item event) {
        BlockColors blockColors = event.getBlockColors();
        event.getItemColors()
                .register((stack, tintIndex) -> OtherworldLeavesNaturalBlock.COLOR,
                        OccultismBlocks.OTHERWORLD_LEAVES.get());
        
        event.getItemColors()
                .register((stack, tintIndex) -> {
                            BlockState blockstate = ((BlockItem) stack.getItem()).getBlock().getDefaultState();
                            return blockColors.getColor(blockstate, null, null, tintIndex);
                        }, //oak leaves color
                        OccultismBlocks.OTHERWORLD_LEAVES_NATURAL.get());

        Occultism.LOGGER.info("Item color registration complete.");
    }
    //endregion Static Methods
}
