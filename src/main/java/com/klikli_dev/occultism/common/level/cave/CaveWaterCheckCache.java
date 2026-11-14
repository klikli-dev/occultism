// SPDX-FileCopyrightText: 2026 voidsip
// SPDX-License-Identifier: MIT
package com.klikli_dev.occultism.common.level.cave;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator;
import net.minecraft.world.level.levelgen.RandomState;

import java.util.function.BooleanSupplier;

/** Reuses a deterministic water check when neighbouring chunks visit the same cave root. */
final class CaveWaterCheckCache {
    // Per random state, not per thread or per loaded chunk. Eviction only repeats the original calculation.
    private static final CaveWaterCheckCache INSTANCE = new CaveWaterCheckCache(4096);
    private final Cache<Object, Cache<Key, Boolean>> worlds = CacheBuilder.newBuilder().weakKeys().build();
    private final int maximumSize;

    CaveWaterCheckCache(int maximumSize) {
        this.maximumSize = maximumSize;
    }

    static boolean isBelowWater(LevelHeightAccessor heightAccessor, ChunkGenerator generator,
                                RandomState randomState, BlockPos rootPosition, int radius) {
        // Custom generators may have additional inputs beyond noise state and height bounds.
        if (generator.getClass() != NoiseBasedChunkGenerator.class) {
            return calculate(heightAccessor, generator, randomState, rootPosition, radius);
        }
        return INSTANCE.get(randomState, generator, rootPosition.asLong(), radius,
                heightAccessor.getMinY(), heightAccessor.getHeight(), heightAccessor.getMaxY(),
                () -> calculate(heightAccessor, generator, randomState, rootPosition, radius));
    }

    boolean get(Object randomState, Object generator, long root, int radius, int minY, int height, int maxY,
                BooleanSupplier calculation) {
        Cache<Key, Boolean> cache = this.worlds.asMap().computeIfAbsent(randomState,
                ignored -> CacheBuilder.newBuilder().maximumSize(this.maximumSize).build());
        Key key = new Key(generator, root, radius, minY, height, maxY);
        return cache.asMap().computeIfAbsent(key, ignored -> calculation.getAsBoolean());
    }

    private static boolean calculate(LevelHeightAccessor heightAccessor, ChunkGenerator generator,
                                     RandomState randomState, BlockPos rootPosition, int radius) {
        // Keep the original probe order, short circuit and noise-based height queries unchanged.
        int outerRadius = radius + 3;
        int caveTop = rootPosition.getY() + outerRadius / 2 + 2;
        int requiredFloorY = caveTop + SphericalCaveSubFeature.MIN_SOLID_BLOCKS_BELOW_WATER;
        for (int dx = -outerRadius; dx <= outerRadius; dx += outerRadius) {
            for (int dz = -outerRadius; dz <= outerRadius; dz += outerRadius) {
                int x = rootPosition.getX() + dx;
                int z = rootPosition.getZ() + dz;
                int surface = generator.getBaseHeight(x, z, Heightmap.Types.WORLD_SURFACE_WG, heightAccessor, randomState);
                int floor = generator.getBaseHeight(x, z, Heightmap.Types.OCEAN_FLOOR_WG, heightAccessor, randomState);
                if (surface > floor && floor <= requiredFloorY) {
                    return true;
                }
            }
        }
        return false;
    }

    private record Key(Object generator, long root, int radius, int minY, int height, int maxY) {
        @Override
        public boolean equals(Object object) {
            return object instanceof Key other && this.generator == other.generator && this.root == other.root
                    && this.radius == other.radius && this.minY == other.minY && this.height == other.height
                    && this.maxY == other.maxY;
        }

        @Override
        public int hashCode() {
            int hash = System.identityHashCode(this.generator);
            hash = 31 * hash + Long.hashCode(this.root);
            hash = 31 * hash + this.radius;
            hash = 31 * hash + this.minY;
            hash = 31 * hash + this.height;
            return 31 * hash + this.maxY;
        }
    }
}
