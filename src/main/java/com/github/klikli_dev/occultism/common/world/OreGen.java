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

package com.github.klikli_dev.occultism.common.world;

import com.github.klikli_dev.occultism.OccultismConfig;
import com.github.klikli_dev.occultism.registry.BlockRegistry;
import com.github.klikli_dev.occultism.registry.ItemRegistry;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.AbstractChunkProvider;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeature;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.IWorldGenerator;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class OreGen implements IWorldGenerator {
    //region Fields
    private final Feature otherstoneOre = new OreFeature(BlockRegistry.OTHERSTONE_ORE.getDefaultState(),
            OccultismConfig.worldGen.oreGen.otherstoneOreSize);
    private final List<Integer> oreGenDimensionWhiteList = Arrays.asList(
            OccultismConfig.worldGen.oreGen.dimensionWhitelist);
    //endregion Fields

    //region Initialization
    public OreGen() {
        MinecraftForge.addGrassSeed(new ItemStack(ItemRegistry.DATURA_SEEDS), 3);
    }
    //endregion Initialization

    //region Overrides
    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, ChunkGenerator iChunkGenerator,
                         AbstractChunkProvider iChunkProvider) {
        if (this.oreGenDimensionWhiteList.contains(world.provider.getDimension())) {
            this.generateOre(world, random, this.otherstoneOre, chunkX, chunkZ,
                    OccultismConfig.worldGen.oreGen.otherstoneOreChance,
                    OccultismConfig.worldGen.oreGen.otherstoneOreMin, OccultismConfig.worldGen.oreGen.otherstoneOreMax);
        }
    }

    //endregion Overrides

    //region Methods
    private void generateOre(World world, Random rand, Feature gen, int chunkX, int chunkZ, int chance,
                             int minHeight, int maxHeight) {
        for (int i = 0; i < chance; i++)
            gen.generate(world, rand,
                    new BlockPos(chunkX * 16 + rand.nextInt(16), rand.nextInt(maxHeight - minHeight) + minHeight,
                            chunkZ * 16 + rand.nextInt(16)));
    }
    //endregion Methods

}
