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

package com.klikli_dev.occultism.common.entity.job;

import com.klikli_dev.occultism.Occultism;
import com.klikli_dev.occultism.common.entity.spirit.SpiritEntity;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.storage.ServerLevelData;

import java.util.function.Supplier;

public class ThunderWeatherJob extends ChangeWeatherJob {

    public ThunderWeatherJob(SpiritEntity entity, Supplier<Integer> ticksToClear) {
        super(entity, ticksToClear);
    }

    public void changeWeather() {
        if (Occultism.SERVER_CONFIG.rituals.enableThunderWeatherRitual.get()) {
            ServerLevelData level = (ServerLevelData) this.entity.level().getLevelData();
            level.setClearWeatherTime(0);
            level.setRainTime(6000);
            level.setThunderTime(6000);
            level.setRaining(true);
            level.setThundering(true);
        } else {
            this.entity.getOwner().sendSystemMessage(Component.translatable("ritual.occultism.disabled"));
        }
    }

}
