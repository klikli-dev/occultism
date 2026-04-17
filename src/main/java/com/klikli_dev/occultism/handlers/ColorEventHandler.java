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
import com.klikli_dev.occultism.common.block.RainbowGlyphBlock;
import com.klikli_dev.occultism.common.block.SpiritFireBlock;
import com.klikli_dev.occultism.common.block.VoidGlyphBlock;
import com.klikli_dev.occultism.common.block.otherworld.IOtherworldBlock;
import com.klikli_dev.occultism.registry.OccultismBlocks;
import net.minecraft.client.color.block.BlockTintSource;
import net.minecraft.world.level.GrassColor;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent.BlockTintSources;

import java.util.List;

public class ColorEventHandler {

    //region Static Methods
    public static void onRegisterBlockColorHandlers(BlockTintSources event) {
        // Chalk glyphs and candles with fixed colors derived from block instance
        event.register(List.of(state -> opaque(OccultismBlocks.CHALK_GLYPH_WHITE.get().getColor())),
                OccultismBlocks.CHALK_GLYPH_WHITE.get(), OccultismBlocks.LARGE_CANDLE_WHITE.get());
        event.register(List.of(state -> opaque(OccultismBlocks.CHALK_GLYPH_YELLOW.get().getColor())),
                OccultismBlocks.CHALK_GLYPH_YELLOW.get(), OccultismBlocks.LARGE_CANDLE_YELLOW.get());
        event.register(List.of(state -> opaque(OccultismBlocks.CHALK_GLYPH_PURPLE.get().getColor())),
                OccultismBlocks.CHALK_GLYPH_PURPLE.get(), OccultismBlocks.LARGE_CANDLE_PURPLE.get());
        event.register(List.of(state -> opaque(OccultismBlocks.CHALK_GLYPH_RED.get().getColor())),
                OccultismBlocks.CHALK_GLYPH_RED.get(), OccultismBlocks.LARGE_CANDLE_RED.get());
        event.register(List.of(state -> opaque(OccultismBlocks.CHALK_GLYPH_LIGHT_GRAY.get().getColor())),
                OccultismBlocks.CHALK_GLYPH_LIGHT_GRAY.get(), OccultismBlocks.LARGE_CANDLE_LIGHT_GRAY.get());
        event.register(List.of(state -> opaque(OccultismBlocks.CHALK_GLYPH_GRAY.get().getColor())),
                OccultismBlocks.CHALK_GLYPH_GRAY.get(), OccultismBlocks.LARGE_CANDLE_GRAY.get());
        event.register(List.of(state -> opaque(OccultismBlocks.CHALK_GLYPH_BLACK.get().getColor())),
                OccultismBlocks.CHALK_GLYPH_BLACK.get(), OccultismBlocks.LARGE_CANDLE_BLACK.get());
        event.register(List.of(state -> opaque(OccultismBlocks.CHALK_GLYPH_BROWN.get().getColor())),
                OccultismBlocks.CHALK_GLYPH_BROWN.get(), OccultismBlocks.LARGE_CANDLE_BROWN.get());
        event.register(List.of(state -> opaque(OccultismBlocks.CHALK_GLYPH_ORANGE.get().getColor())),
                OccultismBlocks.CHALK_GLYPH_ORANGE.get(), OccultismBlocks.LARGE_CANDLE_ORANGE.get());
        event.register(List.of(state -> opaque(OccultismBlocks.CHALK_GLYPH_LIME.get().getColor())),
                OccultismBlocks.CHALK_GLYPH_LIME.get(), OccultismBlocks.LARGE_CANDLE_LIME.get());
        event.register(List.of(state -> opaque(OccultismBlocks.CHALK_GLYPH_GREEN.get().getColor())),
                OccultismBlocks.CHALK_GLYPH_GREEN.get(), OccultismBlocks.LARGE_CANDLE_GREEN.get());
        event.register(List.of(state -> opaque(OccultismBlocks.CHALK_GLYPH_CYAN.get().getColor())),
                OccultismBlocks.CHALK_GLYPH_CYAN.get(), OccultismBlocks.LARGE_CANDLE_CYAN.get());
        event.register(List.of(state -> opaque(OccultismBlocks.CHALK_GLYPH_LIGHT_BLUE.get().getColor())),
                OccultismBlocks.CHALK_GLYPH_LIGHT_BLUE.get(), OccultismBlocks.LARGE_CANDLE_LIGHT_BLUE.get());
        event.register(List.of(state -> opaque(OccultismBlocks.CHALK_GLYPH_BLUE.get().getColor())),
                OccultismBlocks.CHALK_GLYPH_BLUE.get(), OccultismBlocks.LARGE_CANDLE_BLUE.get());
        event.register(List.of(state -> opaque(OccultismBlocks.CHALK_GLYPH_MAGENTA.get().getColor())),
                OccultismBlocks.CHALK_GLYPH_MAGENTA.get(), OccultismBlocks.LARGE_CANDLE_MAGENTA.get());
        event.register(List.of(state -> opaque(OccultismBlocks.CHALK_GLYPH_PINK.get().getColor())),
                OccultismBlocks.CHALK_GLYPH_PINK.get(), OccultismBlocks.LARGE_CANDLE_PINK.get());

        // Rainbow glyph: color is state-dependent (cycles through colors)
        event.register(List.of(state -> opaque(((RainbowGlyphBlock) state.getBlock()).getColor(state))),
                OccultismBlocks.CHALK_GLYPH_RAINBOW.get());

        // Void glyph: color is state-dependent
        event.register(List.of(state -> opaque(((VoidGlyphBlock) state.getBlock()).getColor(state))),
                OccultismBlocks.CHALK_GLYPH_VOID.get());

        // Spirit fire: color is state-dependent and uses two tint layers in the model
        event.register(List.of(
                        state -> opaque(((SpiritFireBlock) state.getBlock()).getColor(state, 0)),
                        state -> opaque(((SpiritFireBlock) state.getBlock()).getColor(state, 1))
                ),
                OccultismBlocks.SPIRIT_FIRE.get());

        // Otherworld leaves: white tint when uncovered (has own texture), biome foliage color when covered
        event.register(List.of(state -> state.getValue(IOtherworldBlock.UNCOVERED)
                ? opaque(0xFFFFFF)
                : opaque(GrassColor.getDefaultColor())), OccultismBlocks.OTHERWORLD_LEAVES_NATURAL.get());

        Occultism.LOGGER.info("Block color registration complete.");
    }

    // TODO: Port to 26.1 data-driven item tint system (ItemTintSource)
    // RegisterColorHandlersEvent.Item was removed in 26.1. Item colors are now data-driven via RegisterColorHandlersEvent.ItemTintSources.
    //endregion Static Methods

    private static int opaque(int color) {
        return (color & 0xFF000000) == 0 ? color | 0xFF000000 : color;
    }
}
