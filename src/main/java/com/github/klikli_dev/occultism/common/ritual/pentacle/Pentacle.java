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

import com.github.klikli_dev.occultism.api.common.data.ChalkGlyphType;
import com.github.klikli_dev.occultism.common.block.BlockChalkGlyph;
import com.github.klikli_dev.occultism.registry.BlockRegistry;
import com.github.klikli_dev.occultism.registry.RitualRegistry;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.IForgeRegistryEntry;
import vazkii.patchouli.api.IMultiblock;
import vazkii.patchouli.api.PatchouliAPI;

public abstract class Pentacle extends IForgeRegistryEntry.Impl<Pentacle> {
    //region Fields
    public static final IBlockState GLYPH_GOLD = BlockRegistry.CHALK_GLYPH.getDefaultState()
                                                         .withProperty(BlockChalkGlyph.TYPE, ChalkGlyphType.GOLD);
    public static final IBlockState GLYPH_RED = BlockRegistry.CHALK_GLYPH.getDefaultState()
                                                        .withProperty(BlockChalkGlyph.TYPE, ChalkGlyphType.RED);
    public static final IBlockState GLYPH_WHITE = BlockRegistry.CHALK_GLYPH.getDefaultState()
                                                          .withProperty(BlockChalkGlyph.TYPE, ChalkGlyphType.WHITE);
    public static final IBlockState GLYPH_PURPLE = BlockRegistry.CHALK_GLYPH.getDefaultState()
                                                           .withProperty(BlockChalkGlyph.TYPE, ChalkGlyphType.PURPLE);

    protected PatchouliAPI.IPatchouliAPI api = PatchouliAPI.instance;
    private IMultiblock blockMatcher;
    //endregion Fields

    //region Initialization
    public Pentacle(String name) {
        RitualRegistry.registerPentacle(this, name);
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
    public void registerMultiblock() {
        ResourceLocation multiblockResourceLocation = new ResourceLocation(this.getRegistryName().getNamespace(),
                "pentacle." + this.getRegistryName().getPath());
        this.blockMatcher = this.api.registerMultiblock(multiblockResourceLocation, this.setupMultiblock());
    }

    /**
     * set up the multi block in this method.
     * Example at
     * https://github.com/Vazkii/Patchouli/blob/1.12.2-final/src/main/java/vazkii/patchouli/common/multiblock/MultiblockRegistry.java
     *
     * @return the finished multiblock.
     */
    protected abstract IMultiblock setupMultiblock();
    //endregion Methods
}
