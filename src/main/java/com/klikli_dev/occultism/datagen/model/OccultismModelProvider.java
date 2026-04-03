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

package com.klikli_dev.occultism.datagen.model;

import com.klikli_dev.occultism.Occultism;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.core.Holder;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;

import java.util.stream.Stream;

public class OccultismModelProvider extends ModelProvider {
    private final OccultismBlockModelSubProvider blockModels = new OccultismBlockModelSubProvider();
    private final OccultismItemModelSubProvider itemModels = new OccultismItemModelSubProvider();

    public OccultismModelProvider(PackOutput packOutput) {
        super(packOutput, Occultism.MODID);
    }

    @Override
    public @NotNull String getName() {
        return "Model Definitions - " + this.modId;
    }

    @Override
    protected void registerModels(@NotNull BlockModelGenerators blockModels, @NotNull ItemModelGenerators itemModels) {
        this.blockModels.registerModels(blockModels, itemModels);
        this.itemModels.registerModels(blockModels, itemModels);
    }

    @Override
    protected Stream<? extends Holder<Block>> getKnownBlocks() {
        return this.blockModels.getKnownBlocks().map(Block::builtInRegistryHolder);
    }

    @Override
    protected Stream<? extends Holder<Item>> getKnownItems() {
        return this.itemModels.getKnownItems().map(Item::builtInRegistryHolder);
    }
}
