// SPDX-FileCopyrightText: 2026 klikli-dev
//
// SPDX-License-Identifier: MIT

package com.klikli_dev.occultism.gametest;

import com.klikli_dev.occultism.common.level.cave.SphericalCaveSubFeature;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.biome.MultiNoiseBiomeSource;
import net.minecraft.world.level.biome.MultiNoiseBiomeSourceParameterLists;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;
import net.minecraft.world.level.levelgen.RandomState;

import java.util.function.BiPredicate;

/**
 * Regression tests for <a href="https://github.com/klikli-dev/occultism/issues/1022">#1022</a>: underground groves
 * must not generate below oceans (or other bodies of water), where they break through the floor and flood.
 * <p>
 * The game test level is a superflat world without water, so these tests build a vanilla overworld noise generator
 * with a fixed seed and run the grove placement check against real ocean and land terrain.
 */
public class UndergroundGroveGameTests {

    private static final long SEED = 1022L;
    /**
     * Smallest radius the grove can roll, results in the smallest cave top offset and thus the most lenient check.
     */
    private static final int RADIUS = 8;
    private static final int SEARCH_STEP = 256;
    private static final int SEARCH_RANGE = 16384;

    private static Terrain createOverworldTerrain(GameTestHelper helper) {
        var registries = helper.getLevel().registryAccess();
        var settings = registries.lookupOrThrow(Registries.NOISE_SETTINGS).getOrThrow(NoiseGeneratorSettings.OVERWORLD);
        var parameters = registries.lookupOrThrow(Registries.MULTI_NOISE_BIOME_SOURCE_PARAMETER_LIST)
                .getOrThrow(MultiNoiseBiomeSourceParameterLists.OVERWORLD);

        var generator = new NoiseBasedChunkGenerator(MultiNoiseBiomeSource.createFromPreset(parameters), settings);
        var randomState = RandomState.create(settings.value(), registries.lookupOrThrow(Registries.NOISE), SEED);
        var noiseSettings = settings.value().noiseSettings();
        var heightAccessor = LevelHeightAccessor.create(noiseSettings.minY(), noiseSettings.height());
        return new Terrain(generator, randomState, heightAccessor);
    }

    private static int outerRadius() {
        return RADIUS + 3;
    }

    /**
     * The highest root y at which a cave of {@link #RADIUS} is still rejected below a water floor at the given height.
     */
    private static int highestRejectedRootY(int floor) {
        return floor - outerRadius() / 2 - 2 - SphericalCaveSubFeature.MIN_SOLID_BLOCKS_BELOW_WATER;
    }

    /**
     * Searches outward from the origin for a column where the given predicate holds for all positions sampled by the
     * grove check. The predicate receives the surface height (with fluids) and the floor height (without fluids).
     */
    private static BlockPos findColumn(GameTestHelper helper, Terrain terrain, BiPredicate<Integer, Integer> predicate, String description) {
        for (int ring = 0; ring * SEARCH_STEP <= SEARCH_RANGE; ring++) {
            for (int i = -ring; i <= ring; i++) {
                for (int j = -ring; j <= ring; j++) {
                    if (Math.max(Math.abs(i), Math.abs(j)) != ring)
                        continue;

                    int x = i * SEARCH_STEP;
                    int z = j * SEARCH_STEP;
                    if (matchesFootprint(terrain, x, z, predicate))
                        return new BlockPos(x, 0, z);
                }
            }
        }
        helper.fail("Could not find a column with " + description + " for seed " + SEED);
        return null;
    }

    private static boolean matchesFootprint(Terrain terrain, int x, int z, BiPredicate<Integer, Integer> predicate) {
        int outer = outerRadius();
        //check the center first, it is the cheapest way to discard most candidates.
        if (!predicate.test(terrain.surface(x, z), terrain.floor(x, z)))
            return false;

        for (int dx = -outer; dx <= outer; dx += outer) {
            for (int dz = -outer; dz <= outer; dz += outer) {
                if (!predicate.test(terrain.surface(x + dx, z + dz), terrain.floor(x + dx, z + dz)))
                    return false;
            }
        }
        return true;
    }

    private static int minFloor(Terrain terrain, BlockPos column) {
        int outer = outerRadius();
        int min = Integer.MAX_VALUE;
        for (int dx = -outer; dx <= outer; dx += outer) {
            for (int dz = -outer; dz <= outer; dz += outer) {
                min = Math.min(min, terrain.floor(column.getX() + dx, column.getZ() + dz));
            }
        }
        return min;
    }

    private static BlockPos findDeepOcean(GameTestHelper helper, Terrain terrain) {
        //at least 10 blocks of water everywhere in the cave footprint
        return findColumn(helper, terrain, (surface, floor) -> surface - floor >= 10, "deep water");
    }

    /**
     * A grove whose top would be right below the ocean floor is rejected, this is the case reported in the issue.
     */
    public static void groveDirectlyBelowOceanFloorIsRejected(GameTestHelper helper) {
        var terrain = createOverworldTerrain(helper);
        var column = findDeepOcean(helper, terrain);
        var root = column.atY(minFloor(terrain, column) - 4);

        helper.assertTrue(terrain.isBelowWater(root),
                "Grove at " + root + " directly below the ocean floor was not rejected");
        helper.succeed();
    }

    /**
     * Verifies the exact threshold: with {@link SphericalCaveSubFeature#MIN_SOLID_BLOCKS_BELOW_WATER} blocks of
     * ground between cave and water the grove is rejected, with one more block it is allowed.
     * This ensures groves are not blanket-banned in ocean biomes, they just need to be deep enough.
     */
    public static void groveBelowOceanRespectsMinimumGroundThickness(GameTestHelper helper) {
        var terrain = createOverworldTerrain(helper);
        var column = findDeepOcean(helper, terrain);
        int rejectedY = highestRejectedRootY(minFloor(terrain, column));

        var rejected = column.atY(rejectedY);
        helper.assertTrue(terrain.isBelowWater(rejected),
                "Grove at " + rejected + " with too little ground below the ocean was not rejected");

        var allowed = column.atY(rejectedY - 1);
        helper.assertFalse(terrain.isBelowWater(allowed),
                "Grove at " + allowed + " with enough ground below the ocean was rejected");
        helper.succeed();
    }

    /**
     * A grove that only partially extends below water (e.g. at a coast) is rejected as well.
     */
    public static void groveAtCoastIsRejected(GameTestHelper helper) {
        var terrain = createOverworldTerrain(helper);
        var ocean = findDeepOcean(helper, terrain);

        //walk from the ocean in a straight line until the center of the cave is on dry land, the footprint still touches water
        int outer = outerRadius();
        for (int dx = 1; dx <= SEARCH_RANGE; dx++) {
            int x = ocean.getX() + dx;
            int z = ocean.getZ();
            boolean centerIsDry = terrain.surface(x, z) == terrain.floor(x, z);
            boolean westIsWater = terrain.surface(x - outer, z) > terrain.floor(x - outer, z);
            if (centerIsDry && westIsWater) {
                int waterFloor = terrain.floor(x - outer, z);
                var root = new BlockPos(x, highestRejectedRootY(waterFloor), z);
                helper.assertTrue(terrain.isBelowWater(root),
                        "Grove at " + root + " with its center on land but partially below water was not rejected");
                helper.succeed();
                return;
            }
        }
        helper.fail("Could not find a coast near " + ocean + " for seed " + SEED);
    }

    /**
     * Groves below dry land are not affected.
     */
    public static void groveBelowLandIsAllowed(GameTestHelper helper) {
        var terrain = createOverworldTerrain(helper);
        //no fluids anywhere in the footprint, and the surface well above sea level
        var column = findColumn(helper, terrain, (surface, floor) -> surface.equals(floor) && floor > 70, "dry land");

        for (int y = 9; y <= 48; y++) {
            var root = column.atY(y);
            helper.assertFalse(terrain.isBelowWater(root), "Grove at " + root + " below dry land was rejected");
        }
        helper.succeed();
    }

    private record Terrain(NoiseBasedChunkGenerator generator, RandomState randomState, LevelHeightAccessor heightAccessor) {
        int surface(int x, int z) {
            return this.generator.getBaseHeight(x, z, Heightmap.Types.WORLD_SURFACE_WG, this.heightAccessor, this.randomState);
        }

        int floor(int x, int z) {
            return this.generator.getBaseHeight(x, z, Heightmap.Types.OCEAN_FLOOR_WG, this.heightAccessor, this.randomState);
        }

        boolean isBelowWater(BlockPos root) {
            return SphericalCaveSubFeature.isBelowWater(this.heightAccessor, this.generator, this.randomState, root, RADIUS);
        }
    }
}
