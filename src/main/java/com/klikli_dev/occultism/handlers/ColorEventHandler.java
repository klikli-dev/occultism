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

package com.klikli_dev.occultism.handlers;

import com.klikli_dev.occultism.Occultism;
import com.klikli_dev.occultism.common.block.otherworld.IOtherworldBlock;
import com.klikli_dev.occultism.registry.OccultismBlocks;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.FoliageColor;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;

@EventBusSubscriber(modid = Occultism.MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ColorEventHandler {

    //region Static Methods
    @SubscribeEvent
    public static void onRegisterBlockColorHandlers(RegisterColorHandlersEvent.Block event) {
        event.getBlockColors()
                .register((state, light, pos, tintIndex) -> OccultismBlocks.CHALK_GLYPH_WHITE.get().getColor(),
                        OccultismBlocks.CHALK_GLYPH_WHITE.get());
        event.getBlockColors()
                .register((state, light, pos, tintIndex) -> OccultismBlocks.CHALK_GLYPH_YELLOW.get().getColor(),
                        OccultismBlocks.CHALK_GLYPH_YELLOW.get());
        event.getBlockColors()
                .register((state, light, pos, tintIndex) -> OccultismBlocks.CHALK_GLYPH_PURPLE.get().getColor(),
                        OccultismBlocks.CHALK_GLYPH_PURPLE.get());
        event.getBlockColors()
                .register((state, light, pos, tintIndex) -> OccultismBlocks.CHALK_GLYPH_RED.get().getColor(),
                        OccultismBlocks.CHALK_GLYPH_RED.get());
        event.getBlockColors()
                .register((state, light, pos, tintIndex) -> OccultismBlocks.CHALK_GLYPH_LIGHT_GRAY.get().getColor(),
                        OccultismBlocks.CHALK_GLYPH_LIGHT_GRAY.get());
        event.getBlockColors()
                .register((state, light, pos, tintIndex) -> OccultismBlocks.CHALK_GLYPH_GRAY.get().getColor(),
                        OccultismBlocks.CHALK_GLYPH_GRAY.get());
        event.getBlockColors()
                .register((state, light, pos, tintIndex) -> OccultismBlocks.CHALK_GLYPH_BLACK.get().getColor(),
                        OccultismBlocks.CHALK_GLYPH_BLACK.get());
        event.getBlockColors()
                .register((state, light, pos, tintIndex) -> OccultismBlocks.CHALK_GLYPH_BROWN.get().getColor(),
                        OccultismBlocks.CHALK_GLYPH_BROWN.get());
        event.getBlockColors()
                .register((state, light, pos, tintIndex) -> OccultismBlocks.CHALK_GLYPH_ORANGE.get().getColor(),
                        OccultismBlocks.CHALK_GLYPH_ORANGE.get());
        event.getBlockColors()
                .register((state, light, pos, tintIndex) -> OccultismBlocks.CHALK_GLYPH_LIME.get().getColor(),
                        OccultismBlocks.CHALK_GLYPH_LIME.get());
        event.getBlockColors()
                .register((state, light, pos, tintIndex) -> OccultismBlocks.CHALK_GLYPH_GREEN.get().getColor(),
                        OccultismBlocks.CHALK_GLYPH_GREEN.get());
        event.getBlockColors()
                .register((state, light, pos, tintIndex) -> OccultismBlocks.CHALK_GLYPH_CYAN.get().getColor(),
                        OccultismBlocks.CHALK_GLYPH_CYAN.get());
        event.getBlockColors()
                .register((state, light, pos, tintIndex) -> OccultismBlocks.CHALK_GLYPH_LIGHT_BLUE.get().getColor(),
                        OccultismBlocks.CHALK_GLYPH_LIGHT_BLUE.get());
        event.getBlockColors()
                .register((state, light, pos, tintIndex) -> OccultismBlocks.CHALK_GLYPH_BLUE.get().getColor(),
                        OccultismBlocks.CHALK_GLYPH_BLUE.get());
        event.getBlockColors()
                .register((state, light, pos, tintIndex) -> OccultismBlocks.CHALK_GLYPH_MAGENTA.get().getColor(),
                        OccultismBlocks.CHALK_GLYPH_MAGENTA.get());
        event.getBlockColors()
                .register((state, light, pos, tintIndex) -> OccultismBlocks.CHALK_GLYPH_PINK.get().getColor(),
                        OccultismBlocks.CHALK_GLYPH_PINK.get());

        event.getBlockColors()
                .register((state, light, pos, tintIndex) -> OccultismBlocks.CHALK_GLYPH_RAINBOW.get().getColor(state),
                        OccultismBlocks.CHALK_GLYPH_RAINBOW.get());
        event.getBlockColors()
                .register((state, light, pos, tintIndex) -> OccultismBlocks.CHALK_GLYPH_VOID.get().getColor(state),
                        OccultismBlocks.CHALK_GLYPH_VOID.get());

        //Otherworld leaves have a colored texture, so return white tint;
        //but while covered the oak leaves need their vanilla tint
        event.getBlockColors()
                .register((state, light, pos, tintIndex) ->
                                state.getValue(IOtherworldBlock.UNCOVERED) ?
                                        0xFFFFFF : (light != null && pos != null ?
                                        BiomeColors.getAverageFoliageColor(light, pos) :
                                        FoliageColor.getDefaultColor()),
                        OccultismBlocks.OTHERWORLD_LEAVES_NATURAL.get());

        Occultism.LOGGER.info("Block color registration complete.");
    }

    @SubscribeEvent
    public static void onRegisterItemColorHandlers(RegisterColorHandlersEvent.Item event) {
        BlockColors blockColors = event.getBlockColors();
        //Otherworld leaves have a colored texture, so return white tint;
        //but while covered the oak leaves need their vanilla tint
        event.getItemColors()
                .register((stack, tintIndex) -> {
                            BlockState blockstate = ((BlockItem) stack.getItem()).getBlock().defaultBlockState();
                            return blockColors.getColor(blockstate, null, null, tintIndex);
                        }, //oak leaves color
                        OccultismBlocks.OTHERWORLD_LEAVES_NATURAL.get());

        Occultism.LOGGER.info("Item color registration complete.");
    }
    //endregion Static Methods
}
