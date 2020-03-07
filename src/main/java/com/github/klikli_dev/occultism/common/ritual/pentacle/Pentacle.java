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

import com.github.klikli_dev.occultism.registry.OccultismBlocks;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistryEntry;
import vazkii.patchouli.api.IMultiblock;
import vazkii.patchouli.api.PatchouliAPI;
import vazkii.patchouli.common.multiblock.StateMatcher;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

public abstract class Pentacle extends ForgeRegistryEntry<Pentacle> {
    //region Fields

    protected PatchouliAPI.IPatchouliAPI api = PatchouliAPI.instance;
    protected IMultiblock blockMatcher;
    protected List<Object> mapping = new ArrayList<>();
    //endregion Fields

    //region Initialization
    public Pentacle() {
    }
    //endregion Initialization

    //region Getter / Setter

    /**
     * Gets the multiblock block matcher for the pentacle.
     *
     * @return the multiblack.
     */
    public IMultiblock getBlockMatcher() {
        return this.blockMatcher;
    }
    //endregion Getter / Setter

    //region Methods

    /**
     * registers the multiblock with patchouli_books.
     */
    public void registerMultiblock(ResourceLocation id) {
        this.setupMapping();
        this.blockMatcher = this.api.registerMultiblock(id, this.setupMultiblock());
    }

    protected void setupMapping() {
        this.mapping.addAll(Arrays.asList(
                '0', StateMatcher.fromBlockLoose(OccultismBlocks.GOLDEN_SACRIFICIAL_BOWL.get()),
                'W', StateMatcher.fromBlockLoose(OccultismBlocks.CHALK_GLYPH_WHITE.get()),
                'G', StateMatcher.fromBlockLoose(OccultismBlocks.CHALK_GLYPH_GOLD.get()),
                'P', StateMatcher.fromBlockLoose(OccultismBlocks.CHALK_GLYPH_PURPLE.get()),
                'R', StateMatcher.fromBlockLoose(OccultismBlocks.CHALK_GLYPH_RED.get()),
                ' ', this.api.anyMatcher())
        );
    }

    /**
     * set up the multi block in this method.
     * Example at
     * https://github.com/Vazkii/Patchouli/blob/1.14-final/src/main/java/vazkii/patchouli/common/multiblock/MultiblockRegistry.java
     *
     * @return the finished multiblock.
     */
    protected abstract IMultiblock setupMultiblock();
    //endregion Methods
}
