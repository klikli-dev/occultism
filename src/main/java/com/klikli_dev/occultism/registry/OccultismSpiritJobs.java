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

package com.klikli_dev.occultism.registry;

import com.klikli_dev.occultism.Occultism;
import  com.klikli_dev.occultism.common.entity.job.*;
import com.klikli_dev.occultism.common.entity.job.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

import static com.klikli_dev.occultism.util.StaticUtil.modLoc;

public class OccultismSpiritJobs {
    //region Fields
    public static DeferredRegister<SpiritJobFactory> JOBS = DeferredRegister.create(new ResourceLocation(Occultism.MODID, "spirit_job_factory"), Occultism.MODID);

    public static final Supplier<IForgeRegistry<SpiritJobFactory>> REGISTRY = JOBS.makeRegistry(() ->
            new RegistryBuilder<SpiritJobFactory>().disableSaving().setMaxID(Integer.MAX_VALUE - 1));

    public static final RegistryObject<SpiritJobFactory> LUMBERJACK = JOBS.register("lumberjack",
            () -> new SpiritJobFactory(LumberjackJob::new));
    public static final RegistryObject<SpiritJobFactory> MANAGE_MACHINE = JOBS.register("manage_machine",
            () -> new SpiritJobFactory(ManageMachineJob::new));
    public static final RegistryObject<SpiritJobFactory> TRANSPORT_ITEMS = JOBS.register("transport_items",
            () -> new SpiritJobFactory(TransportItemsJob::new));
    public static final RegistryObject<SpiritJobFactory> CLEANER = JOBS.register("cleaner",
            () -> new SpiritJobFactory(CleanerJob::new));

    //Trade jobs
    public static final RegistryObject<SpiritJobFactory> TRADE_OTHERSTONE_T1 = JOBS.register("trade_otherstone_t1",
            () -> new SpiritJobFactory((entity) -> {
                TraderJob job = new TraderJob(entity, modLoc("spirit_trade/stone_to_otherstone"));
                job.setTimeToConvert(15);
                job.setMaxTradesPerRound(4);
                return job;
            }));
    public static final RegistryObject<SpiritJobFactory> TRADE_OTHERWORLD_SAPLINGS_T2 = JOBS.register("trade_otherworld_saplings_t1",
            () -> new SpiritJobFactory((entity) -> {
                TraderJob job = new TraderJob(entity, modLoc("spirit_trade/otherworld_sapling"));
                job.setTimeToConvert(20);
                job.setMaxTradesPerRound(1);
                return job;
            }));

    //Crushing jobs
    public static final RegistryObject<SpiritJobFactory> CRUSH_TIER1 = JOBS.register("crush_tier1",
            () -> new SpiritJobFactory((entity) -> new CrusherJob(entity,
                    () -> Occultism.SERVER_CONFIG.spiritJobs.tier1CrusherTimeMultiplier.get().floatValue(),
                    () -> Occultism.SERVER_CONFIG.spiritJobs.tier1CrusherOutputMultiplier.get().floatValue(),
                    () -> 1
            )));
    public static final RegistryObject<SpiritJobFactory> CRUSH_TIER2 = JOBS.register("crush_tier2",
            () -> new SpiritJobFactory((entity) -> new CrusherJob(entity,
                    () -> Occultism.SERVER_CONFIG.spiritJobs.tier2CrusherTimeMultiplier.get().floatValue(),
                    () -> Occultism.SERVER_CONFIG.spiritJobs.tier2CrusherOutputMultiplier.get().floatValue(),
                    () -> 2
            )));
    public static final RegistryObject<SpiritJobFactory> CRUSH_TIER3 = JOBS.register("crush_tier3",
            () -> new SpiritJobFactory((entity) -> new CrusherJob(entity,
                    () -> Occultism.SERVER_CONFIG.spiritJobs.tier3CrusherTimeMultiplier.get().floatValue(),
                    () -> Occultism.SERVER_CONFIG.spiritJobs.tier3CrusherOutputMultiplier.get().floatValue(),
                    () -> 3
            )));
    public static final RegistryObject<SpiritJobFactory> CRUSH_TIER4 = JOBS.register("crush_tier4",
            () -> new SpiritJobFactory((entity) -> new CrusherJob(entity,
                    () -> Occultism.SERVER_CONFIG.spiritJobs.tier4CrusherTimeMultiplier.get().floatValue(),
                    () -> Occultism.SERVER_CONFIG.spiritJobs.tier4CrusherOutputMultiplier.get().floatValue(),
                    () -> 4
            )));

    //Weather Jobs
    public static final RegistryObject<SpiritJobFactory> CLEAR_WEATHER = JOBS.register("clear_weather",
            () -> new SpiritJobFactory((entity) -> new ClearWeatherJob(entity, 20 * 15)));
    public static final RegistryObject<SpiritJobFactory> RAIN_WEATHER = JOBS.register("rain_weather",
            () -> new SpiritJobFactory((entity) -> new RainWeatherJob(entity, 20 * 30)));
    public static final RegistryObject<SpiritJobFactory> THUNDER_WEATHER = JOBS.register("thunder_weather",
            () -> new SpiritJobFactory((entity) -> new ThunderWeatherJob(entity, 20 * 60)));

    //Time Jobs
    public static final RegistryObject<SpiritJobFactory> DAY_TIME = JOBS.register("day_time",
            () -> new SpiritJobFactory((entity) -> new DayTimeJob(entity, 20 * 5)));
    public static final RegistryObject<SpiritJobFactory> NIGHT_TIME = JOBS.register("night_time",
            () -> new SpiritJobFactory((entity) -> new NightTimeJob(entity, 20 * 5)));

    //endregion Fields
}
