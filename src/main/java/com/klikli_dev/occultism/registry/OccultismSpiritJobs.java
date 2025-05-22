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
import com.klikli_dev.occultism.client.entities.SpiritJobClient;
import com.klikli_dev.occultism.common.entity.job.*;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class OccultismSpiritJobs {

    public static final ResourceKey<Registry<SpiritJobFactory>> JOBS_KEY = ResourceKey.createRegistryKey(ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "spirit_job_factories"));
    public static DeferredRegister<SpiritJobFactory> JOBS = DeferredRegister.create(JOBS_KEY, Occultism.MODID);

    public static final Registry<SpiritJobFactory> REGISTRY = JOBS.makeRegistry((builder) -> {
    });

    public static final DeferredHolder<SpiritJobFactory, SpiritJobFactory> LUMBERJACK = JOBS.register("lumberjack",
            () -> new SpiritJobFactory(LumberjackJob::new, SpiritJobClient.create("lumberjack")));
    public static final DeferredHolder<SpiritJobFactory, SpiritJobFactory> MANAGE_MACHINE = JOBS.register("manage_machine",
            () -> new SpiritJobFactory(ManageMachineJob::new, SpiritJobClient.create("machine_manager")));
    public static final DeferredHolder<SpiritJobFactory, SpiritJobFactory> TRANSPORT_ITEMS = JOBS.register("transport_items",
            () -> new SpiritJobFactory(TransportItemsJob::new, SpiritJobClient.create("transporter")));
    public static final DeferredHolder<SpiritJobFactory, SpiritJobFactory> CLEANER = JOBS.register("cleaner",
            () -> new SpiritJobFactory(CleanerJob::new, SpiritJobClient.create("janitor")));

    //Trade jobs
    public static final DeferredHolder<SpiritJobFactory, SpiritJobFactory> TRADE_OTHERSTONE_T1 = JOBS.register("trade_otherstone_t1",
            () -> new SpiritJobFactory((entity) -> {
                TraderJob job = new TraderJob(entity, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "spirit_trade/stone_to_otherstone"));
                job.setTimeToConvert(15);
                job.setMaxTradesPerRound(4);
                return job;
            }, SpiritJobClient.create("otherstone_trader")));
    public static final DeferredHolder<SpiritJobFactory, SpiritJobFactory> TRADE_OTHERWORLD_SAPLINGS_T2 = JOBS.register("trade_otherworld_saplings_t1",
            () -> new SpiritJobFactory((entity) -> {
                TraderJob job = new TraderJob(entity, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "spirit_trade/otherworld_sapling"));
                job.setTimeToConvert(20);
                job.setMaxTradesPerRound(1);
                return job;
            }, SpiritJobClient.create("sapling_trader")));

    //Crushing jobs
    public static final DeferredHolder<SpiritJobFactory, SpiritJobFactory> CRUSH_TIER1 = JOBS.register("crush_tier1",
            () -> new SpiritJobFactory((entity) -> new CrusherJob(entity,
                    () -> Occultism.SERVER_CONFIG.spiritJobs.tier1CrusherTimeMultiplier.get().floatValue(),
                    () -> Occultism.SERVER_CONFIG.spiritJobs.tier1CrusherOutputMultiplier.get().floatValue(),
                    () -> 1
            ), SpiritJobClient.create("crusher")));
    public static final DeferredHolder<SpiritJobFactory, SpiritJobFactory> CRUSH_TIER2 = JOBS.register("crush_tier2",
            () -> new SpiritJobFactory((entity) -> new CrusherJob(entity,
                    () -> Occultism.SERVER_CONFIG.spiritJobs.tier2CrusherTimeMultiplier.get().floatValue(),
                    () -> Occultism.SERVER_CONFIG.spiritJobs.tier2CrusherOutputMultiplier.get().floatValue(),
                    () -> 2
            ), SpiritJobClient.create("crusher")));
    public static final DeferredHolder<SpiritJobFactory, SpiritJobFactory> CRUSH_TIER3 = JOBS.register("crush_tier3",
            () -> new SpiritJobFactory((entity) -> new CrusherJob(entity,
                    () -> Occultism.SERVER_CONFIG.spiritJobs.tier3CrusherTimeMultiplier.get().floatValue(),
                    () -> Occultism.SERVER_CONFIG.spiritJobs.tier3CrusherOutputMultiplier.get().floatValue(),
                    () -> 3
            ), SpiritJobClient.create("crusher")));
    public static final DeferredHolder<SpiritJobFactory, SpiritJobFactory> CRUSH_TIER4 = JOBS.register("crush_tier4",
            () -> new SpiritJobFactory((entity) -> new CrusherJob(entity,
                    () -> Occultism.SERVER_CONFIG.spiritJobs.tier4CrusherTimeMultiplier.get().floatValue(),
                    () -> Occultism.SERVER_CONFIG.spiritJobs.tier4CrusherOutputMultiplier.get().floatValue(),
                    () -> 4
            ), SpiritJobClient.create("crusher")));
    //Smelting Jobs
    public static final DeferredHolder<SpiritJobFactory, SpiritJobFactory> SMELT_TIER1 = JOBS.register("smelt_tier1",
            () -> new SpiritJobFactory((entity) -> new SmelterJob(entity,
                    () -> Occultism.SERVER_CONFIG.spiritJobs.tier1SmelterTimeMultiplier.get().floatValue()
            ), SpiritJobClient.create("smelter")));
    public static final DeferredHolder<SpiritJobFactory, SpiritJobFactory> SMELT_TIER2 = JOBS.register("smelt_tier2",
            () -> new SpiritJobFactory((entity) -> new SmelterJob(entity,
                    () -> Occultism.SERVER_CONFIG.spiritJobs.tier2SmelterTimeMultiplier.get().floatValue()
            ), SpiritJobClient.create("smelter")));
    public static final DeferredHolder<SpiritJobFactory, SpiritJobFactory> SMELT_TIER3 = JOBS.register("smelt_tier3",
            () -> new SpiritJobFactory((entity) -> new SmelterJob(entity,
                    () -> Occultism.SERVER_CONFIG.spiritJobs.tier3SmelterTimeMultiplier.get().floatValue()
            ), SpiritJobClient.create("smelter")));
    public static final DeferredHolder<SpiritJobFactory, SpiritJobFactory> SMELT_TIER4 = JOBS.register("smelt_tier4",
            () -> new SpiritJobFactory((entity) -> new SmelterJob(entity,
                    () -> Occultism.SERVER_CONFIG.spiritJobs.tier4SmelterTimeMultiplier.get().floatValue()
            ), SpiritJobClient.create("smelter")));

    //Weather Jobs
    public static final DeferredHolder<SpiritJobFactory, SpiritJobFactory> CLEAR_WEATHER = JOBS.register("clear_weather",
            () -> new SpiritJobFactory((entity) -> new ClearWeatherJob(entity, Occultism.SERVER_CONFIG.spiritJobs.clearWeatherTimeToCast::getAsInt), SpiritJobClient.create()));
    public static final DeferredHolder<SpiritJobFactory, SpiritJobFactory> RAIN_WEATHER = JOBS.register("rain_weather",
            () -> new SpiritJobFactory((entity) -> new RainWeatherJob(entity, Occultism.SERVER_CONFIG.spiritJobs.rainTimeToCast::getAsInt), SpiritJobClient.create()));
    public static final DeferredHolder<SpiritJobFactory, SpiritJobFactory> THUNDER_WEATHER = JOBS.register("thunder_weather",
            () -> new SpiritJobFactory((entity) -> new ThunderWeatherJob(entity, Occultism.SERVER_CONFIG.spiritJobs.thunderTimeToCast::getAsInt), SpiritJobClient.create()));

    //Time Jobs
    public static final DeferredHolder<SpiritJobFactory, SpiritJobFactory> DAY_TIME = JOBS.register("day_time",
            () -> new SpiritJobFactory((entity) -> new DayTimeJob(entity, Occultism.SERVER_CONFIG.spiritJobs.dayTimeToCast::getAsInt), SpiritJobClient.create()));
    public static final DeferredHolder<SpiritJobFactory, SpiritJobFactory> NIGHT_TIME = JOBS.register("night_time",
            () -> new SpiritJobFactory((entity) -> new NightTimeJob(entity, Occultism.SERVER_CONFIG.spiritJobs.nightTimeToCast::getAsInt), SpiritJobClient.create()));

}
