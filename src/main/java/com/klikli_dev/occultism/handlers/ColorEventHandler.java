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
import com.klikli_dev.occultism.registry.OccultismItems;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.FoliageColor;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;

@EventBusSubscriber(modid = Occultism.MODID)
public class ColorEventHandler {

    //region Static Methods
    @SubscribeEvent
    public static void onRegisterBlockColorHandlers(RegisterColorHandlersEvent.Block event) {
        event.getBlockColors()
                .register((state, light, pos, tintIndex) -> OccultismBlocks.CHALK_GLYPH_WHITE.get().getColor(),
                        OccultismBlocks.CHALK_GLYPH_WHITE.get(), OccultismBlocks.LARGE_CANDLE_WHITE.get());
        event.getBlockColors()
                .register((state, light, pos, tintIndex) -> OccultismBlocks.CHALK_GLYPH_YELLOW.get().getColor(),
                        OccultismBlocks.CHALK_GLYPH_YELLOW.get(), OccultismBlocks.LARGE_CANDLE_YELLOW.get());
        event.getBlockColors()
                .register((state, light, pos, tintIndex) -> OccultismBlocks.CHALK_GLYPH_PURPLE.get().getColor(),
                        OccultismBlocks.CHALK_GLYPH_PURPLE.get(), OccultismBlocks.LARGE_CANDLE_PURPLE.get());
        event.getBlockColors()
                .register((state, light, pos, tintIndex) -> OccultismBlocks.CHALK_GLYPH_RED.get().getColor(),
                        OccultismBlocks.CHALK_GLYPH_RED.get(), OccultismBlocks.LARGE_CANDLE_RED.get());
        event.getBlockColors()
                .register((state, light, pos, tintIndex) -> OccultismBlocks.CHALK_GLYPH_LIGHT_GRAY.get().getColor(),
                        OccultismBlocks.CHALK_GLYPH_LIGHT_GRAY.get(), OccultismBlocks.LARGE_CANDLE_LIGHT_GRAY.get());
        event.getBlockColors()
                .register((state, light, pos, tintIndex) -> OccultismBlocks.CHALK_GLYPH_GRAY.get().getColor(),
                        OccultismBlocks.CHALK_GLYPH_GRAY.get(), OccultismBlocks.LARGE_CANDLE_GRAY.get());
        event.getBlockColors()
                .register((state, light, pos, tintIndex) -> OccultismBlocks.CHALK_GLYPH_BLACK.get().getColor(),
                        OccultismBlocks.CHALK_GLYPH_BLACK.get(), OccultismBlocks.LARGE_CANDLE_BLACK.get());
        event.getBlockColors()
                .register((state, light, pos, tintIndex) -> OccultismBlocks.CHALK_GLYPH_BROWN.get().getColor(),
                        OccultismBlocks.CHALK_GLYPH_BROWN.get(), OccultismBlocks.LARGE_CANDLE_BROWN.get());
        event.getBlockColors()
                .register((state, light, pos, tintIndex) -> OccultismBlocks.CHALK_GLYPH_ORANGE.get().getColor(),
                        OccultismBlocks.CHALK_GLYPH_ORANGE.get(), OccultismBlocks.LARGE_CANDLE_ORANGE.get());
        event.getBlockColors()
                .register((state, light, pos, tintIndex) -> OccultismBlocks.CHALK_GLYPH_LIME.get().getColor(),
                        OccultismBlocks.CHALK_GLYPH_LIME.get(), OccultismBlocks.LARGE_CANDLE_LIME.get());
        event.getBlockColors()
                .register((state, light, pos, tintIndex) -> OccultismBlocks.CHALK_GLYPH_GREEN.get().getColor(),
                        OccultismBlocks.CHALK_GLYPH_GREEN.get(), OccultismBlocks.LARGE_CANDLE_GREEN.get());
        event.getBlockColors()
                .register((state, light, pos, tintIndex) -> OccultismBlocks.CHALK_GLYPH_CYAN.get().getColor(),
                        OccultismBlocks.CHALK_GLYPH_CYAN.get(), OccultismBlocks.LARGE_CANDLE_CYAN.get());
        event.getBlockColors()
                .register((state, light, pos, tintIndex) -> OccultismBlocks.CHALK_GLYPH_LIGHT_BLUE.get().getColor(),
                        OccultismBlocks.CHALK_GLYPH_LIGHT_BLUE.get(), OccultismBlocks.LARGE_CANDLE_LIGHT_BLUE.get());
        event.getBlockColors()
                .register((state, light, pos, tintIndex) -> OccultismBlocks.CHALK_GLYPH_BLUE.get().getColor(),
                        OccultismBlocks.CHALK_GLYPH_BLUE.get(), OccultismBlocks.LARGE_CANDLE_BLUE.get());
        event.getBlockColors()
                .register((state, light, pos, tintIndex) -> OccultismBlocks.CHALK_GLYPH_MAGENTA.get().getColor(),
                        OccultismBlocks.CHALK_GLYPH_MAGENTA.get(), OccultismBlocks.LARGE_CANDLE_MAGENTA.get());
        event.getBlockColors()
                .register((state, light, pos, tintIndex) -> OccultismBlocks.CHALK_GLYPH_PINK.get().getColor(),
                        OccultismBlocks.CHALK_GLYPH_PINK.get(), OccultismBlocks.LARGE_CANDLE_PINK.get());

        event.getBlockColors()
                .register((state, light, pos, tintIndex) -> OccultismBlocks.CHALK_GLYPH_RAINBOW.get().getColor(state),
                        OccultismBlocks.CHALK_GLYPH_RAINBOW.get());
        event.getBlockColors()
                .register((state, light, pos, tintIndex) -> OccultismBlocks.CHALK_GLYPH_VOID.get().getColor(state),
                        OccultismBlocks.CHALK_GLYPH_VOID.get());
        event.getBlockColors()
                .register((state, light, pos, tintIndex) -> (int) OccultismBlocks.SPIRIT_FIRE.get().getColor(state, tintIndex),
                        OccultismBlocks.SPIRIT_FIRE.get());

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

        event.getItemColors()
                .register((stack, tintIndex) ->
                                0xFF000000 + Occultism.CLIENT_CONFIG.visuals.blackChalkGlyphColor.get(),
                        OccultismItems.CHALK_BLACK.get(), OccultismItems.CHALK_BLACK_IMPURE.get(), OccultismBlocks.LARGE_CANDLE_BLACK.asItem());
        event.getItemColors()
                .register((stack, tintIndex) ->
                                0xFF000000 + Occultism.CLIENT_CONFIG.visuals.blueChalkGlyphColor.get(),
                        OccultismItems.CHALK_BLUE.get(), OccultismItems.CHALK_BLUE_IMPURE.get(), OccultismBlocks.LARGE_CANDLE_BLUE.asItem());
        event.getItemColors()
                .register((stack, tintIndex) ->
                                0xFF000000 + Occultism.CLIENT_CONFIG.visuals.brownChalkGlyphColor.get(),
                        OccultismItems.CHALK_BROWN.get(), OccultismItems.CHALK_BROWN_IMPURE.get(), OccultismBlocks.LARGE_CANDLE_BROWN.asItem());
        event.getItemColors()
                .register((stack, tintIndex) ->
                                0xFF000000 + Occultism.CLIENT_CONFIG.visuals.cyanChalkGlyphColor.get(),
                        OccultismItems.CHALK_CYAN.get(), OccultismItems.CHALK_CYAN_IMPURE.get(), OccultismBlocks.LARGE_CANDLE_CYAN.asItem());
        event.getItemColors()
                .register((stack, tintIndex) ->
                                0xFF000000 + Occultism.CLIENT_CONFIG.visuals.yellowChalkGlyphColor.get(),
                        OccultismItems.CHALK_YELLOW.get(), OccultismItems.CHALK_YELLOW_IMPURE.get(), OccultismBlocks.LARGE_CANDLE_YELLOW.asItem());
        event.getItemColors()
                .register((stack, tintIndex) ->
                                0xFF000000 + Occultism.CLIENT_CONFIG.visuals.grayChalkGlyphColor.get(),
                        OccultismItems.CHALK_GRAY.get(), OccultismItems.CHALK_GRAY_IMPURE.get(), OccultismBlocks.LARGE_CANDLE_GRAY.asItem());
        event.getItemColors()
                .register((stack, tintIndex) ->
                                0xFF000000 + Occultism.CLIENT_CONFIG.visuals.greenChalkGlyphColor.get(),
                        OccultismItems.CHALK_GREEN.get(), OccultismItems.CHALK_GREEN_IMPURE.get(), OccultismBlocks.LARGE_CANDLE_GREEN.asItem());
        event.getItemColors()
                .register((stack, tintIndex) ->
                                0xFF000000 + Occultism.CLIENT_CONFIG.visuals.lightBlueChalkGlyphColor.get(),
                        OccultismItems.CHALK_LIGHT_BLUE.get(), OccultismItems.CHALK_LIGHT_BLUE_IMPURE.get(), OccultismBlocks.LARGE_CANDLE_LIGHT_BLUE.asItem());
        event.getItemColors()
                .register((stack, tintIndex) ->
                                0xFF000000 + Occultism.CLIENT_CONFIG.visuals.lightGrayChalkGlyphColor.get(),
                        OccultismItems.CHALK_LIGHT_GRAY.get(), OccultismItems.CHALK_LIGHT_GRAY_IMPURE.get(), OccultismBlocks.LARGE_CANDLE_LIGHT_GRAY.asItem());
        event.getItemColors()
                .register((stack, tintIndex) ->
                                0xFF000000 + Occultism.CLIENT_CONFIG.visuals.limeChalkGlyphColor.get(),
                        OccultismItems.CHALK_LIME.get(), OccultismItems.CHALK_LIME_IMPURE.get(), OccultismBlocks.LARGE_CANDLE_LIME.asItem());
        event.getItemColors()
                .register((stack, tintIndex) ->
                                0xFF000000 + Occultism.CLIENT_CONFIG.visuals.magentaChalkGlyphColor.get(),
                        OccultismItems.CHALK_MAGENTA.get(), OccultismItems.CHALK_MAGENTA_IMPURE.get(), OccultismBlocks.LARGE_CANDLE_MAGENTA.asItem());
        event.getItemColors()
                .register((stack, tintIndex) ->
                                0xFF000000 + Occultism.CLIENT_CONFIG.visuals.orangeChalkGlyphColor.get(),
                        OccultismItems.CHALK_ORANGE.get(), OccultismItems.CHALK_ORANGE_IMPURE.get(), OccultismBlocks.LARGE_CANDLE_ORANGE.asItem());
        event.getItemColors()
                .register((stack, tintIndex) ->
                                0xFF000000 + Occultism.CLIENT_CONFIG.visuals.pinkChalkGlyphColor.get(),
                        OccultismItems.CHALK_PINK.get(), OccultismItems.CHALK_PINK_IMPURE.get(), OccultismBlocks.LARGE_CANDLE_PINK.asItem());
        event.getItemColors()
                .register((stack, tintIndex) ->
                                0xFF000000 + Occultism.CLIENT_CONFIG.visuals.purpleChalkGlyphColor.get(),
                        OccultismItems.CHALK_PURPLE.get(), OccultismItems.CHALK_PURPLE_IMPURE.get(), OccultismBlocks.LARGE_CANDLE_PURPLE.asItem());
        event.getItemColors()
                .register((stack, tintIndex) ->
                                0xFF000000 + Occultism.CLIENT_CONFIG.visuals.redChalkGlyphColor.get(),
                        OccultismItems.CHALK_RED.get(), OccultismItems.CHALK_RED_IMPURE.get(), OccultismBlocks.LARGE_CANDLE_RED.asItem());
        event.getItemColors()
                .register((stack, tintIndex) ->
                                0xFF000000 + Occultism.CLIENT_CONFIG.visuals.whiteChalkGlyphColor.get(),
                        OccultismItems.CHALK_WHITE.get(), OccultismItems.CHALK_WHITE_IMPURE.get(), OccultismBlocks.LARGE_CANDLE_WHITE.asItem());

        Occultism.LOGGER.info("Item color registration complete.");
    }
    //endregion Static Methods
}
