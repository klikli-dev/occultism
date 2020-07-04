/*
 * MIT License
 *
 * Copyright 2020 klikli-dev, Florian "Sangar" Nücke
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

package com.github.klikli_dev.occultism.client.divination;

import com.github.klikli_dev.occultism.util.Math3DUtil;
import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Based on https://github.com/MightyPirates/Scannable
 */
public class ScanManager {
    //region Fields
    public static final ScanManager instance = new ScanManager();
    public static final int SCAN_DURATION_TICKS = 40;
    public static final int SCAN_RADIUS_BLOCKS = 96;
    List<BlockPos> results = new ArrayList<>();
    private Scanner scanner;
    private int scanningTicks = -1;
    //endregion Fields

    //region Methods
    public void beginScan(PlayerEntity player, Block target) {
        this.cancelScan();

        this.scanner = new Scanner(target);
        this.scanner.initialize(player, player.getPositionVector(), SCAN_RADIUS_BLOCKS, SCAN_DURATION_TICKS);
    }

    public void updateScan(PlayerEntity player, boolean forceFinish) {
        final int remainingTicks = SCAN_DURATION_TICKS - this.scanningTicks;

        if (remainingTicks <= 0 || this.scanner == null) {
            return;
        }

        //if we are not forcing we simply tick once
        if (!forceFinish) {
            this.scanner.scan(result -> this.results.add(result));
            this.scanningTicks++;
            return;
        }

        //when forcing we scan through all remaining tikcs at once
        for (int i = 0; i < remainingTicks; i++) {
            this.scanner.scan(result -> this.results.add(result));
            this.scanningTicks++;
        }
    }

    public BlockPos finishScan(PlayerEntity player) {
        this.updateScan(player, true);

        Vec3d scanCenter = player.getPositionVector();
        this.results
                .sort(Comparator.comparing(result -> scanCenter.squareDistanceTo(Math3DUtil.center(result))));
        BlockPos result = !this.results.isEmpty() ? this.results.get(0) : null;
        this.cancelScan();
        return result;
    }

    public void cancelScan() {
        this.scanner = null;
        this.results.clear();
        this.scanningTicks = -1;
    }

    //endregion Methods
}
