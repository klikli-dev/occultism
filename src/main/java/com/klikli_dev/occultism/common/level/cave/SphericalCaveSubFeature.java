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

package com.klikli_dev.occultism.common.level.cave;

import com.klikli_dev.occultism.common.level.multichunk.IMultiChunkSubFeature;
import com.klikli_dev.occultism.common.level.multichunk.MultiChunkFeature;
import com.klikli_dev.occultism.common.level.multichunk.MultiChunkFeatureConfig;
import com.klikli_dev.occultism.registry.OccultismTags;
import com.klikli_dev.occultism.util.Math3DUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.phys.AABB;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SphericalCaveSubFeature implements IMultiChunkSubFeature {

    public static Set<BlockPos> sphericalCaves = new HashSet<>();
    protected ICaveDecorator caveDecorator;
    protected int radius;
    protected int maxRandomRadiusOffset;
    protected int additionalSpheres;
    protected int maxRandomAdditionalSpheres;

    /**
     * @param caveDecorator         the decorator for the generated cave.
     * @param radius                the radius of the cave.
     * @param maxRandomRadiusOffset the maximum random offset for the radius
     */
    public SphericalCaveSubFeature(ICaveDecorator caveDecorator, int radius, int maxRandomRadiusOffset) {
        this(caveDecorator, radius, maxRandomRadiusOffset, 3, 2);
    }

    /**
     * @param caveDecorator              the decorator for the generated cave.
     * @param radius                     the radius of the cave.
     * @param maxRandomRadiusOffset      the maximum random offset for the radius
     * @param additionalSpheres          the amount of additional spheres to add on to the main sphere.
     * @param maxRandomAdditionalSpheres the maximum amount of random additional sphere to add to the main sphere.
     */
    public SphericalCaveSubFeature(ICaveDecorator caveDecorator, int radius, int maxRandomRadiusOffset,
                                   int additionalSpheres, int maxRandomAdditionalSpheres) {
        this.caveDecorator = caveDecorator;
        this.radius = radius;
        this.maxRandomRadiusOffset = maxRandomRadiusOffset;
        this.additionalSpheres = additionalSpheres;
        this.maxRandomAdditionalSpheres = maxRandomAdditionalSpheres;
    }

    @Override
    public boolean place(WorldGenLevel reader, ChunkGenerator generator, RandomSource rand, BlockPos rootPosition,
                         AABB bounds, MultiChunkFeatureConfig config) {
        //can never generate in daylight
        if (reader.canSeeSkyFromBelowWater(rootPosition))
            return false;

        //Store a list of spherical caves for easy access during development, or future command access.
        sphericalCaves.add(rootPosition);

        ChunkPos rootChunk = new ChunkPos(rootPosition);

        //Seed with root chunk position
        var seed = MultiChunkFeature.getLargeFeatureWithSaltSeed(reader.getSeed(), rootChunk.x, rootChunk.z, config.featureSeedSalt);
        rand.setSeed(seed);

        List<Sphere> spheres = new ArrayList<>();
        int radiusBase = this.radius + rand.nextInt(this.maxRandomRadiusOffset);
        int radius = (int) (radiusBase * 0.2F) + rand.nextInt(8);
        spheres.add(this.generateSphere(reader, rand, rootPosition, radius, bounds));
        for (int i = 0; i < this.additionalSpheres + rand.nextInt(this.maxRandomAdditionalSpheres); i++) {
            Direction direction = Direction.Plane.HORIZONTAL.getRandomDirection(rand);
            spheres.add(this.generateSphere(reader, rand, rootPosition.relative(direction, radius - 2),
                    2 * (int) (radius / 3F) + rand.nextInt(8), bounds));
        }
        for (Sphere sphere : spheres) {
            this.hollowOutSphere(reader, rand, sphere.center, sphere.radius - 2, bounds);
            this.decorateSphere(reader, generator, rand, sphere.center, sphere.radius + 2, bounds, config);
        }
        spheres.clear();
        return true;
    }

    protected Sphere generateSphere(WorldGenLevel level, RandomSource rand, BlockPos position, int radius, AABB bounds) {
        return new Sphere(position, radius);
    }

    protected void hollowOutSphere(WorldGenLevel reader, RandomSource rand, BlockPos center, int radius, AABB bounds) {
        int j = radius;
        int k = radius / 2;
        int l = radius;
        float f = (float) (j + k + l) * 0.333F + 0.5F;
        BlockPos min = Math3DUtil.clamp(center.offset(-j, -k, -l), bounds);
        BlockPos max = Math3DUtil.clamp(center.offset(j, k, l), bounds);

        BlockPos.betweenClosed(min, max).forEach(blockPos -> {
            if (blockPos.distSqr(center) <= (double) (f * f * Mth.clamp(rand.nextFloat(), 0.75F, 1.0F))) {
                BlockState currentState = reader.getBlockState(blockPos);
                this.setBlockSafely(reader, blockPos, currentState, Blocks.CAVE_AIR.defaultBlockState(), 2);
            }
        });
    }

    protected void decorateSphere(WorldGenLevel reader, ChunkGenerator generator, RandomSource rand,
                                  BlockPos center, int radius, AABB bounds, MultiChunkFeatureConfig config) {
        int j = radius;
        //int k = radius / 2;
        int k = radius / 2;
        int l = radius;
        CaveDecoratordata data = new CaveDecoratordata();
        float f = (float) (j + k + l) * 0.333F + 0.5F;
        BlockPos min = Math3DUtil.clamp(center.offset(-j, -k, -l), bounds);
        BlockPos max = Math3DUtil.clamp(center.offset(j, k, l), bounds);
        BlockPos.betweenClosed(min, max).forEach(blockPos -> {
            if (blockPos.distSqr(center) <= (double) (f * f)) {
                this.caveDecorator.fill(reader, generator, rand, blockPos.immutable(), data, config);
            }
        });

        this.caveDecorator.finalPass(reader, generator, rand, data, config);
    }

    protected void setBlockSafely(WorldGenLevel level, BlockPos pPos, BlockState pCurrentState, BlockState pNewState, int pFlags) {
        if (pCurrentState.hasBlockEntity() || pCurrentState.getBlock() == Blocks.BEDROCK || pCurrentState.is(OccultismTags.Blocks.WORLDGEN_BLACKLIST)) {
            return;
        }
        level.setBlock(pPos, pNewState, pFlags);
    }

    public class Sphere {

        public BlockPos center;
        public int radius;

        public Sphere(BlockPos center, int radius) {
            this.center = center;
            this.radius = radius;
        }

    }
}
