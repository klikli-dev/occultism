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

package com.github.klikli_dev.occultism.common.ritual.pentacle;

import com.github.klikli_dev.occultism.common.block.CandleBlock;
import com.github.klikli_dev.occultism.registry.OccultismBlocks;
import net.minecraft.block.Blocks;
import vazkii.patchouli.api.IMultiblock;
import vazkii.patchouli.common.multiblock.StateMatcher;

import java.util.Arrays;

public class PentacleCraftDjinni extends Pentacle {

    //region Fields
    private final String[][] pattern = new String[][]{
            {
                    "C WGW C",
                    " P W P ",
                    "W SWS W",
                    "GWW0WWG",
                    "W SWS W",
                    " P W P ",
                    "C WGW C"
            }
    };

    //endregion Fields

    //region Overrides


    @Override
    protected void setupMapping() {
        super.setupMapping();
        this.mapping.addAll(Arrays.asList(
                //TODO: enable once spirit attuned crystal is ready and remove quartz block mapping
                //            'S',  StateMatcher.fromBlockLoose(OccultismBlocks.SPIRIT_ATTUNED_CRYSTAL.get()),
                'S',  StateMatcher.fromBlockLoose(Blocks.QUARTZ_BLOCK),
                'C', StateMatcher.fromPredicate(OccultismBlocks.CANDLE_WHITE.get(),
                        b -> b.getBlock() instanceof CandleBlock)
        ));
    }

    @Override
    protected IMultiblock setupMultiblock() {
        return this.api.makeMultiblock(this.pattern, this.mapping.toArray()).setSymmetrical(true);
    }
    //endregion Overrides
}
