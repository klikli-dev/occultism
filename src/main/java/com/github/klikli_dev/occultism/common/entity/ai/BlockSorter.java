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

package com.github.klikli_dev.occultism.common.entity.ai;

import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;

import java.util.Comparator;

public class BlockSorter implements Comparator<BlockPos> {
    //region Fields
    private final Entity entity;
    //endregion Fields

    //region Initialization
    public BlockSorter(Entity entity) {
        this.entity = entity;
    }
    //endregion Initialization

    //region Overrides
    @Override
    public int compare(BlockPos a, BlockPos b) {
        double distanceA = this.getDistance(a);
        double distanceB = this.getDistance(b);
        return Double.compare(distanceA, distanceB);
    }
    //endregion Overrides

    //region Methods

    /**
     * Gets the distance of the entity to the given position at eye level.
     *
     * @param pos the position to get the distance to.
     * @return the distance at eye level.
     */
    private double getDistance(BlockPos pos) {
        double deltaX = this.entity.getPosX() - (pos.getX() + 0.5);
        double deltaY = this.entity.getPosY() + this.entity.getEyeHeight() - (pos.getY() + 0.5);
        double deltaZ = this.entity.getPosZ() - (pos.getZ() + 0.5);
        return deltaX * deltaX + deltaY * deltaY + deltaZ * deltaZ;
    }
    //endregion Methods
}
//endregion Subtypes