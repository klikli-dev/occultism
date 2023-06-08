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

package com.klikli_dev.occultism.common.entity.ai.goal;

import net.minecraft.world.entity.ai.goal.Goal;

public abstract class PausableGoal extends Goal {

    //region Fields
    protected long lastPaused;
    protected long pauseDuration;
    //endregion Fields

    //region Getter / Setter
    public boolean isPaused() {

        long currentTime = System.currentTimeMillis();
        return this.lastPaused + this.pauseDuration > currentTime;
    }
    //endregion Getter / Setter

    //region Overrides
    @Override
    public boolean canUse() {
        return !this.isPaused();
    }

    @Override
    public boolean canContinueToUse() {
        return super.canContinueToUse() && !this.isPaused();
    }
    //endregion Overrides

    //region Methods
    public void pause(long i) {
        this.pauseDuration = i;
        this.lastPaused = System.currentTimeMillis();
    }

    public void unpause() {
        this.pauseDuration = 0;
    }
    //endregion Methods
}
