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

package com.github.klikli_dev.occultism.registry;

import com.github.klikli_dev.occultism.Occultism;
import com.github.klikli_dev.occultism.common.jobs.SpiritJobFactory;
import com.github.klikli_dev.occultism.common.jobs.SpiritJobLumberjack;
import com.github.klikli_dev.occultism.common.jobs.SpiritJobManageMachine;
import com.github.klikli_dev.occultism.common.jobs.SpiritJobTrader;
import net.minecraft.util.ResourceLocation;

public class SpiritJobFactoryRegistry {

    //region Fields
    public static SpiritJobFactory JOB_LUMBERJACK_FACTORY = new SpiritJobFactory("lumberjack",
            SpiritJobLumberjack.class);
    public static SpiritJobFactory JOB_MANAGE_MACHINE = new SpiritJobFactory("manage_machine",
            SpiritJobManageMachine.class);
    public static SpiritJobFactory JOB_TRADE_OTHERSTONE = new SpiritJobFactory("trade_otherstone",
            SpiritJobTrader.class);
    public static SpiritJobFactory JOB_TRADE_OTHERWORLD_SAPLINGS = new SpiritJobFactory("trade_otherworld_saplings",
            SpiritJobTrader.class);

    //region Static Methods

    /**
     * Registers the given spirit job factory with forge.
     *
     * @param spiritJobFactory the factory to register.
     * @param name             the name of the factory.
     * @param <T>              the
     * @return
     */
    public static <T extends SpiritJobFactory> T registerFactory(T spiritJobFactory, String name) {
        ResourceLocation location = new ResourceLocation(Occultism.MODID, name);
        spiritJobFactory.setRegistryName(location);
        return spiritJobFactory;
    }
    //endregion Static Methods

}
