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

public class NightTimeJob extends ChangeTimeJob {

    public NightTimeJob(SpiritEntity entity, Supplier<Integer> ticksToClear) {
        super(entity, ticksToClear);
    }

    @Override
    public long getNewTime() {
        //dawn is 0, 24000, 48000, etc
        //noon is 6000
        //nightfall is 13000, 37000, 61000, etc
        //midnight is 18000

        ServerLevelData level = (ServerLevelData) this.entity.level().getLevelData();

        //calculate the time of the next nightfall
        long newTime = ((level.getDayTime() + 11000) / 24000) * 24000 + 13000;

        return newTime;
    }

    @Override
    public Component getDisabledMessage() {
        return Component.translatable("ritual.occultism.disabled");
    }

    @Override
    public boolean isEnabled() {
        return Occultism.SERVER_CONFIG.rituals.enableNightTimeRitual.get();
    }
}
