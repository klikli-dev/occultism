// SPDX-FileCopyrightText: 2026 voidsip
// SPDX-License-Identifier: MIT

package com.klikli_dev.occultism.gametest;

import com.klikli_dev.occultism.common.level.cave.CaveWaterCheckCache;
import net.minecraft.gametest.framework.GameTestHelper;

import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicInteger;

public class CaveWaterCheckCacheGameTests {

    public static void neighbouringChunksReuseWaterAndDryDecisions(GameTestHelper helper) {
        var cache = new CaveWaterCheckCache(16);
        var state = new Object();
        var generator = new Object();
        var calls = new AtomicInteger();
        for (int i = 0; i < 9; i++) {
            helper.assertTrue(!cache.get(state, generator, 42, 10, -64, 384, 319, () -> {
                calls.incrementAndGet();
                return false;
            }), "Dry root at 42 was not reported as dry");
            helper.assertTrue(cache.get(state, generator, -42, 10, -64, 384, 319, () -> {
                calls.incrementAndGet();
                return true;
            }), "Water root at -42 was not reported as water");
        }
        helper.assertTrue(calls.get() == 2, "Expected 2 calculations but got " + calls.get());
        helper.succeed();
    }

    public static void separatesNoiseStateGeneratorRootRadiusAndHeightBounds(GameTestHelper helper) {
        var cache = new CaveWaterCheckCache(16);
        var state = new EqualObject();
        var generator = new EqualObject();
        helper.assertTrue(cache.get(state, generator, 42, 10, -64, 384, 319, () -> true), "Initial water root was not reported as water");
        helper.assertTrue(!cache.get(new EqualObject(), generator, 42, 10, -64, 384, 319, () -> false), "Equal noise state shared a cache entry");
        helper.assertTrue(!cache.get(state, new EqualObject(), 42, 10, -64, 384, 319, () -> false), "Equal generator shared a cache entry");
        helper.assertTrue(!cache.get(state, generator, 43, 10, -64, 384, 319, () -> false), "Different root shared a cache entry");
        helper.assertTrue(!cache.get(state, generator, 42, 11, -64, 384, 319, () -> false), "Different radius shared a cache entry");
        helper.assertTrue(!cache.get(state, generator, 42, 10, 0, 384, 319, () -> false), "Different min Y shared a cache entry");
        helper.assertTrue(!cache.get(state, generator, 42, 10, -64, 256, 191, () -> false), "Different height bounds shared a cache entry");
        helper.succeed();
    }

    public static void separatesAnOverriddenUpperLimitFromNominalHeight(GameTestHelper helper) {
        var cache = new CaveWaterCheckCache(16);
        var state = new Object();
        var generator = new Object();
        helper.assertTrue(cache.get(state, generator, 42, 10, -64, 384, 319, () -> true), "Initial water root was not reported as water");
        helper.assertTrue(!cache.get(state, generator, 42, 10, -64, 384, -49, () -> false), "Overridden max Y shared a cache entry with nominal height");
        helper.succeed();
    }

    public static void concurrentChunkWorkersCalculateOneRootOnlyOnce(GameTestHelper helper) {
        var cache = new CaveWaterCheckCache(16);
        var state = new Object();
        var generator = new Object();
        var calls = new AtomicInteger();
        var ready = new CountDownLatch(8);
        var start = new CountDownLatch(1);
        var pool = Executors.newFixedThreadPool(8);
        try {
            var results = new ArrayList<Future<Boolean>>();
            for (int i = 0; i < 8; i++) {
                results.add(pool.submit(() -> {
                    ready.countDown();
                    start.await(10, TimeUnit.SECONDS);
                    return cache.get(state, generator, 42, 10, -64, 384, 319, () -> {
                        calls.incrementAndGet();
                        return true;
                    });
                }));
            }
            helper.assertTrue(ready.await(10, TimeUnit.SECONDS), "Chunk workers did not start");
            start.countDown();
            for (var result : results) {
                helper.assertTrue(result.get(10, TimeUnit.SECONDS), "Concurrent chunk worker did not receive water");
            }
            helper.assertTrue(calls.get() == 1, "Expected 1 calculation but got " + calls.get());
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            helper.fail("Concurrent chunk workers failed: " + e);
        } finally {
            start.countDown();
            pool.shutdownNow();
        }
        helper.succeed();
    }

    public static void failedCalculationIsNotCachedOrWrapped(GameTestHelper helper) {
        var cache = new CaveWaterCheckCache(16);
        var state = new Object();
        var generator = new Object();
        var failure = new IllegalStateException("height lookup failed");
        IllegalStateException thrown = null;
        try {
            cache.get(state, generator, 42, 10, -64, 384, 319, () -> {
                throw failure;
            });
        } catch (IllegalStateException e) {
            thrown = e;
        }
        helper.assertTrue(thrown == failure, "Failed calculation was not rethrown as the original exception");
        helper.assertTrue(cache.get(state, generator, 42, 10, -64, 384, 319, () -> true), "Failed calculation was cached");
        helper.succeed();
    }

    public static void evictedRootsAreRecomputedWithoutChangingTheResult(GameTestHelper helper) {
        var cache = new CaveWaterCheckCache(1);
        var state = new Object();
        var generator = new Object();
        var calls = new AtomicInteger();
        for (long root : new long[]{1, 2, 1}) {
            helper.assertTrue(cache.get(state, generator, root, 10, -64, 384, 319, () -> {
                calls.incrementAndGet();
                return true;
            }), "Root " + root + " was not reported as water");
        }
        helper.assertTrue(calls.get() == 3, "Expected 3 calculations but got " + calls.get());
        helper.succeed();
    }

    private static final class EqualObject {
        @Override
        public boolean equals(Object other) {
            return other instanceof EqualObject;
        }

        @Override
        public int hashCode() {
            return 0;
        }
    }
}
