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

package com.github.klikli_dev.occultism.common.job;

import com.github.klikli_dev.occultism.Occultism;
import com.github.klikli_dev.occultism.common.entity.spirit.SpiritEntity;
import net.minecraft.Util;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerLevel;

public class RainWeatherJob extends ChangeWeatherJob {

    //region Initialization
    public RainWeatherJob(SpiritEntity entity, int ticksToClear) {
        super(entity, ticksToClear);
    }
    //endregion Initialization

    //region Overrides
    public void changeWeather() {
        if (Occultism.SERVER_CONFIG.rituals.enableClearWeatherRitual.get()) {
            ((ServerLevel) this.entity.level).setWeatherParameters(0, 6000, true, false);
        } else {
            this.entity.getOwner().sendMessage(new TranslatableComponent("ritual.occultism.disabled"), Util.NIL_UUID);
        }
    }
    //endregion Overrides
}
