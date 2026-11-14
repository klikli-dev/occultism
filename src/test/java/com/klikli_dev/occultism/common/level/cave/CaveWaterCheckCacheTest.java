// SPDX-License-Identifier: MIT
package com.klikli_dev.occultism.common.level.cave;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CaveWaterCheckCacheTest {
    @Test
    void neighbouringChunksReuseBothWaterAndDryDecisions() {
        var cache = new CaveWaterCheckCache(16);
        var state = new Object();
        var generator = new Object();
        var calls = new AtomicInteger();
        for (int i = 0; i < 9; i++) {
            assertFalse(cache.get(state, generator, 42, 10, -64, 384, () -> {
                calls.incrementAndGet();
                return false;
            }));
            assertTrue(cache.get(state, generator, -42, 10, -64, 384, () -> {
                calls.incrementAndGet();
                return true;
            }));
        }
        assertEquals(2, calls.get());
    }

    @Test
    void separatesNoiseStateGeneratorRootRadiusAndHeightBounds() {
        var cache = new CaveWaterCheckCache(16);
        var state = new EqualObject();
        var generator = new EqualObject();
        assertTrue(cache.get(state, generator, 42, 10, -64, 384, () -> true));
        assertFalse(cache.get(new EqualObject(), generator, 42, 10, -64, 384, () -> false));
        assertFalse(cache.get(state, new EqualObject(), 42, 10, -64, 384, () -> false));
        assertFalse(cache.get(state, generator, 43, 10, -64, 384, () -> false));
        assertFalse(cache.get(state, generator, 42, 11, -64, 384, () -> false));
        assertFalse(cache.get(state, generator, 42, 10, 0, 384, () -> false));
        assertFalse(cache.get(state, generator, 42, 10, -64, 256, () -> false));
    }

    @Test
    void concurrentChunkWorkersCalculateOneRootOnlyOnce() throws Exception {
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
                    assertTrue(start.await(10, TimeUnit.SECONDS));
                    return cache.get(state, generator, 42, 10, -64, 384, () -> {
                        calls.incrementAndGet();
                        return true;
                    });
                }));
            }
            assertTrue(ready.await(10, TimeUnit.SECONDS));
            start.countDown();
            for (var result : results) {
                assertTrue(result.get(10, TimeUnit.SECONDS));
            }
            assertEquals(1, calls.get());
        } finally {
            start.countDown();
            pool.shutdownNow();
            assertTrue(pool.awaitTermination(10, TimeUnit.SECONDS));
        }
    }

    @Test
    void failedCalculationIsNotCachedOrWrapped() {
        var cache = new CaveWaterCheckCache(16);
        var state = new Object();
        var generator = new Object();
        var failure = new IllegalStateException("height lookup failed");
        assertSame(failure, assertThrows(IllegalStateException.class,
                () -> cache.get(state, generator, 42, 10, -64, 384, () -> { throw failure; })));
        assertTrue(cache.get(state, generator, 42, 10, -64, 384, () -> true));
    }

    @Test
    void evictedRootsAreRecomputedWithoutChangingTheResult() {
        var cache = new CaveWaterCheckCache(1);
        var state = new Object();
        var generator = new Object();
        var calls = new AtomicInteger();
        for (long root : new long[]{1, 2, 1}) {
            assertTrue(cache.get(state, generator, root, 10, -64, 384, () -> {
                calls.incrementAndGet();
                return true;
            }));
        }
        assertEquals(3, calls.get());
    }

    private static final class EqualObject {
        @Override public boolean equals(Object other) { return other instanceof EqualObject; }
        @Override public int hashCode() { return 0; }
    }
}
