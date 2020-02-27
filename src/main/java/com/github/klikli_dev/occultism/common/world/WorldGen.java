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
import com.github.klikli_dev.occultism.common.world.cave.UndergroundGroveDecorator;
import com.github.klikli_dev.occultism.common.world.cave.WorldGenSphericalCave;
import com.github.klikli_dev.occultism.registry.ItemRegistry;
import com.github.klikli_dev.occultism.util.BiomeUtil;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.AbstractChunkProvider;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.IWorldGenerator;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class WorldGen implements IWorldGenerator {
    //region Fields
    protected final WorldGenSphericalCave sphericalCaveGenerator;

    protected final List<Integer> undergroundGroveDimensionWhitelist = Arrays.asList(
            OccultismConfig.worldGen.undergroundGroveGen.dimensionWhitelist);
    protected final List<BiomeDictionary.Type> undergroundGroveBiomes = Arrays.stream(
            OccultismConfig.worldGen.undergroundGroveGen.validBiomes).map(s -> BiomeDictionary.Type.getType(s))
                                                                                .collect(Collectors.toList());
    protected final float minGroveDistanceSquared = OccultismConfig.worldGen.undergroundGroveGen.minGroveDistance *
                                                    OccultismConfig.worldGen.undergroundGroveGen.minGroveDistance;

    protected BlockPos lastUndergroundGrove = null;
    //endregion Fields

    //region Initialization
    public WorldGen() {
        this.sphericalCaveGenerator = new WorldGenSphericalCave(new UndergroundGroveDecorator(), 25, 25);
        MinecraftForge.addGrassSeed(new ItemStack(ItemRegistry.DATURA_SEEDS), 3);
    }
    //endregion Initialization

    //region Overrides
    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, ChunkGenerator iChunkGenerator,
                         AbstractChunkProvider iChunkProvider) {
        if (!this.undergroundGroveDimensionWhitelist.contains(world.provider.getDimension())) {
            return;
        }

        //Chunk offset to prevent runaway world generation
        int x = (chunkX * 16) + 8;
        int z = (chunkZ * 16) + 8;
        BlockPos height = world.getHeight(new BlockPos(x, 0, z));

        //ensure minimum distance between coves
        if (this.lastUndergroundGrove == null ||
            this.lastUndergroundGrove.distanceSq(height) >= this.minGroveDistanceSquared) {
            //check biome rarity
            if (random.nextInt(OccultismConfig.worldGen.undergroundGroveGen.groveSpawnRarity + 1) == 0) {
                BlockPos pos = new BlockPos(x, 20 + random.nextInt(20), z);
                //ensure the cove is underground and in a valid biome
                if (!world.canBlockSeeSky(pos) &&
                    BiomeUtil.containsType(world.getBiome(pos), this.undergroundGroveBiomes)) {
                    this.sphericalCaveGenerator.generate(world, random, pos);
                    this.lastUndergroundGrove = pos;
                }
            }
        }

    }

    //endregion Overrides
}
