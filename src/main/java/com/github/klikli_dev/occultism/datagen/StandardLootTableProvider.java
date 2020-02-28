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

import com.github.klikli_dev.occultism.registry.OccultismBlocks;
import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.fml.RegistryObject;

public class StandardLootTableProvider extends BaseLootTableProvider {

    //region Initialization
    public StandardLootTableProvider(DataGenerator dataGeneratorIn) {
        super(dataGeneratorIn);
    }
    //endregion Initialization

    //region Overrides
    @Override
    protected void addTables() {
        OccultismBlocks.BLOCKS.getEntries().stream()
                .map(RegistryObject::get)
                .forEach(block -> {
                    //if the block should not drop anything (like glyph) generate empty table.
                    if (OccultismBlocks.requiresEmptyLootTable(block))
                        lootTables.put(block, empty(block.getRegistryName().getPath(), block));
                    //if the block does not have a special table, just drop itself.
                    else if (!OccultismBlocks.requiresCustomLootTable(block))
                        lootTables.put(block, basic(block.getRegistryName().getPath(), block));
                });

        //All custom/"special" loot tables can be generated here
        lootTables.put(OccultismBlocks.STABLE_WORMHOLE.get(),
                withTileNBT(OccultismBlocks.STABLE_WORMHOLE.get().getRegistryName().getPath(),
                        OccultismBlocks.STABLE_WORMHOLE.get()));
    }
    //endregion Overrides
}
