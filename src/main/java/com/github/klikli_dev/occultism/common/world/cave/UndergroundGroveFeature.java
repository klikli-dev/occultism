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

package com.github.klikli_dev.occultism.common.world.cave;

import com.github.klikli_dev.occultism.Occultism;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationSettings;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.NoFeatureConfig;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class UndergroundGroveFeature extends SphericalCaveFeature {

    protected final List<DimensionType> undergroundGroveDimensionWhitelist =
            Occultism.CONFIG.worldGen.undergroundGroveGen.dimensionTypeWhitelist.get().stream().map(s -> DimensionType.byName(new ResourceLocation(s))).collect(
                    Collectors.toList());
    protected final float minGroveDistanceSquared = Occultism.CONFIG.worldGen.undergroundGroveGen.minGroveDistance.get() *
                                                    Occultism.CONFIG.worldGen.undergroundGroveGen.minGroveDistance.get();

    protected BlockPos lastUndergroundGrove = null;

    //region Initialization

    /**
     * @param radius                the radius of the cave.
     * @param maxRandomRadiusOffset the maximum random offset for the radius
     */
    public UndergroundGroveFeature(int radius, int maxRandomRadiusOffset) {
        super(new UndergroundGroveDecorator(), radius, maxRandomRadiusOffset);
    }

    /**
     * @param radius                     the radius of the cave.
     * @param maxRandomRadiusOffset      the maximum random offset for the radius
     * @param additionalSpheres          the amount of additional spheres to add on to the main sphere.
     * @param maxRandomAdditionalSpheres the maximum amount of random additional sphere to add to the main sphere.
     */
    public UndergroundGroveFeature(int radius, int maxRandomRadiusOffset,
                                   int additionalSpheres, int maxRandomAdditionalSpheres) {
        super(new UndergroundGroveDecorator(), radius, maxRandomRadiusOffset, additionalSpheres, maxRandomAdditionalSpheres);
    }
    //endregion Initialization

    //region Overrides

    @Override
    public boolean place(IWorld world, ChunkGenerator<? extends GenerationSettings> generator, Random rand,
                         BlockPos pos, NoFeatureConfig config) {
        if (!this.undergroundGroveDimensionWhitelist.contains(world.getDimension().getType())) {
            return false;
        }

        BlockPos height = world.getHeight(Heightmap.Type.WORLD_SURFACE, new BlockPos(pos.getX(), 0, pos.getY()));

        //ensure minimum distance between coves
        if (this.lastUndergroundGrove == null ||
            this.lastUndergroundGrove.distanceSq(height) >= this.minGroveDistanceSquared) {
            //check biome rarity
            if (rand.nextInt(Occultism.CONFIG.worldGen.undergroundGroveGen.groveSpawnRarity.get() + 1) == 0) {
                BlockPos generatorPosition = new BlockPos(pos.getX(), 20 + rand.nextInt(20), + pos.getY());
                //ensure the cove is underground and in a valid biome
                if (!world.canBlockSeeSky(generatorPosition)) {
                    this.lastUndergroundGrove = generatorPosition;
                    return super.place(world, generator, rand, pos, config);
                }
            }
        }
        return false;

    }

    //endregion Overrides
}
