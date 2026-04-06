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

import java.util.function.Supplier;

public class DayTimeJob extends ChangeTimeJob {
    
    protected static final int TIME_DAWN = 0;

    public DayTimeJob(SpiritEntity entity, Supplier<Integer> ticksToClear) {
        super(entity, ticksToClear);
    }

    @Override
    protected long getNewTime() {
        var server = this.entity.level().getServer();
        var clockHolder = server.registryAccess().lookupOrThrow(net.minecraft.core.registries.Registries.WORLD_CLOCK).getOrThrow(net.minecraft.world.clock.WorldClocks.OVERWORLD);
        long currentTime = server.clockManager().getTotalTicks(clockHolder);
        return getNearestDayTime(currentTime, TIME_DAWN);
    }

    @Override
    public Component getDisabledMessage() {
        return Component.translatable("ritual.occultism.disabled");
    }

    @Override
    public boolean isEnabled() {
        return Occultism.SERVER_CONFIG.rituals.enableDayTimeRitual.get();
    }
}
