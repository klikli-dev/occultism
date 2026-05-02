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
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class OccultismSpiritJobs {

    public static final ResourceKey<Registry<SpiritJobFactory>> JOBS_KEY = ResourceKey.createRegistryKey(Identifier.fromNamespaceAndPath(Occultism.MODID, "spirit_job_factories"));
    public static DeferredRegister<SpiritJobFactory> JOBS = DeferredRegister.create(JOBS_KEY, Occultism.MODID);

    public static final Registry<SpiritJobFactory> REGISTRY = JOBS.makeRegistry((builder) -> {
    });

    public static final DeferredHolder<SpiritJobFactory, SpiritJobFactory> LUMBERJACK = JOBS.register("lumberjack",
            () -> new SpiritJobFactory(LumberjackJob::new, SpiritJobClient.create("lumberjack")));
    public static final DeferredHolder<SpiritJobFactory, SpiritJobFactory> FARMER = JOBS.register("farmer",
            () -> new SpiritJobFactory(FarmerJob::new, SpiritJobClient.create("farmer")));
    public static final DeferredHolder<SpiritJobFactory, SpiritJobFactory> MANAGE_MACHINE = JOBS.register("manage_machine",
            () -> new SpiritJobFactory(ManageMachineJob::new, SpiritJobClient.create("machine_manager")));
    public static final DeferredHolder<SpiritJobFactory, SpiritJobFactory> TRANSPORT_ITEMS = JOBS.register("transport_items",
            () -> new SpiritJobFactory(TransportItemsJob::new, SpiritJobClient.create("transporter")));
    public static final DeferredHolder<SpiritJobFactory, SpiritJobFactory> CLEANER = JOBS.register("cleaner",
            () -> new SpiritJobFactory(CleanerJob::new, SpiritJobClient.create("janitor")));

    //Trade jobs
    public static final DeferredHolder<SpiritJobFactory, SpiritJobFactory> TRADE_OTHERSTONE = JOBS.register("trader_otherstone",
            () -> new SpiritJobFactory((entity) -> new TraderJob(entity,
                    Occultism.SERVER_CONFIG.spiritJobs.traderOtherstone.operationTimer::getAsInt,
                    Occultism.SERVER_CONFIG.spiritJobs.traderOtherstone.operationCount::getAsInt
            ), SpiritJobClient.create("otherstone_trader")));
    public static final DeferredHolder<SpiritJobFactory, SpiritJobFactory> TRADE_OTHERROCK = JOBS.register("trader_otherrock",
            () -> new SpiritJobFactory((entity) -> new TraderJob(entity,
                    Occultism.SERVER_CONFIG.spiritJobs.traderOtherrock.operationTimer::getAsInt,
                    Occultism.SERVER_CONFIG.spiritJobs.traderOtherrock.operationCount::getAsInt
            ), SpiritJobClient.create("otherrock_trader")));
    public static final DeferredHolder<SpiritJobFactory, SpiritJobFactory> TRADE_OTHERWORLD_SAPLINGS = JOBS.register("trader_otherworld_saplings",
            () -> new SpiritJobFactory((entity) -> new TraderJob(entity,
                    Occultism.SERVER_CONFIG.spiritJobs.traderSapling.operationTimer::getAsInt,
                    Occultism.SERVER_CONFIG.spiritJobs.traderSapling.operationCount::getAsInt
            ), SpiritJobClient.create("sapling_trader")));
    public static final DeferredHolder<SpiritJobFactory, SpiritJobFactory> TRADE_GAMBLER = JOBS.register("gambler",
            () -> new SpiritJobFactory((entity) -> new TraderJob(entity,
                    Occultism.SERVER_CONFIG.spiritJobs.traderGem.operationTimer::getAsInt,
                    Occultism.SERVER_CONFIG.spiritJobs.traderGem.operationCount::getAsInt
            ), SpiritJobClient.create("gambler")));

    //Crushing jobs
    public static final DeferredHolder<SpiritJobFactory, SpiritJobFactory> CRUSH_TIER1 = JOBS.register("crush_tier1",
            () -> new SpiritJobFactory((entity) -> new CrusherJob(entity,
                    () -> Occultism.SERVER_CONFIG.spiritJobs.crusherFoliot.timeMultiplier.get().floatValue(),
                    () -> Occultism.SERVER_CONFIG.spiritJobs.crusherFoliot.outputMultiplier.get().floatValue(),
                    Occultism.SERVER_CONFIG.spiritJobs.crusherFoliot.operationCount::getAsInt,
                    Occultism.SERVER_CONFIG.spiritJobs.crusherFoliot.tier::getAsInt
            ), SpiritJobClient.create("crusher")));
    public static final DeferredHolder<SpiritJobFactory, SpiritJobFactory> CRUSH_TIER2 = JOBS.register("crush_tier2",
            () -> new SpiritJobFactory((entity) -> new CrusherJob(entity,
                    () -> Occultism.SERVER_CONFIG.spiritJobs.crusherDjinni.timeMultiplier.get().floatValue(),
                    () -> Occultism.SERVER_CONFIG.spiritJobs.crusherDjinni.outputMultiplier.get().floatValue(),
                    Occultism.SERVER_CONFIG.spiritJobs.crusherDjinni.operationCount::getAsInt,
                    Occultism.SERVER_CONFIG.spiritJobs.crusherDjinni.tier::getAsInt
            ), SpiritJobClient.create("crusher")));
    public static final DeferredHolder<SpiritJobFactory, SpiritJobFactory> CRUSH_TIER3 = JOBS.register("crush_tier3",
            () -> new SpiritJobFactory((entity) -> new CrusherJob(entity,
                    () -> Occultism.SERVER_CONFIG.spiritJobs.crusherAfrit.timeMultiplier.get().floatValue(),
                    () -> Occultism.SERVER_CONFIG.spiritJobs.crusherAfrit.outputMultiplier.get().floatValue(),
                    Occultism.SERVER_CONFIG.spiritJobs.crusherAfrit.operationCount::getAsInt,
                    Occultism.SERVER_CONFIG.spiritJobs.crusherAfrit.tier::getAsInt
            ), SpiritJobClient.create("crusher")));
    public static final DeferredHolder<SpiritJobFactory, SpiritJobFactory> CRUSH_TIER4 = JOBS.register("crush_tier4",
            () -> new SpiritJobFactory((entity) -> new CrusherJob(entity,
                    () -> Occultism.SERVER_CONFIG.spiritJobs.crusherMarid.timeMultiplier.get().floatValue(),
                    () -> Occultism.SERVER_CONFIG.spiritJobs.crusherMarid.outputMultiplier.get().floatValue(),
                    Occultism.SERVER_CONFIG.spiritJobs.crusherMarid.operationCount::getAsInt,
                    Occultism.SERVER_CONFIG.spiritJobs.crusherMarid.tier::getAsInt
            ), SpiritJobClient.create("crusher")));
    //Smelting Jobs
    public static final DeferredHolder<SpiritJobFactory, SpiritJobFactory> SMELT_TIER1 = JOBS.register("smelt_tier1",
            () -> new SpiritJobFactory((entity) -> new SmelterJob(entity,
                    () -> Occultism.SERVER_CONFIG.spiritJobs.smelterFoliot.timeMultiplier.get().floatValue(),
                    Occultism.SERVER_CONFIG.spiritJobs.smelterFoliot.operationCount::getAsInt
            ), SpiritJobClient.create("smelter")));
    public static final DeferredHolder<SpiritJobFactory, SpiritJobFactory> SMELT_TIER2 = JOBS.register("smelt_tier2",
            () -> new SpiritJobFactory((entity) -> new SmelterJob(entity,
                    () -> Occultism.SERVER_CONFIG.spiritJobs.smelterDjinni.timeMultiplier.get().floatValue(),
                    Occultism.SERVER_CONFIG.spiritJobs.smelterDjinni.operationCount::getAsInt
            ), SpiritJobClient.create("smelter")));
    public static final DeferredHolder<SpiritJobFactory, SpiritJobFactory> SMELT_TIER3 = JOBS.register("smelt_tier3",
            () -> new SpiritJobFactory((entity) -> new SmelterJob(entity,
                    () -> Occultism.SERVER_CONFIG.spiritJobs.smelterAfrit.timeMultiplier.get().floatValue(),
                    Occultism.SERVER_CONFIG.spiritJobs.smelterAfrit.operationCount::getAsInt
            ), SpiritJobClient.create("smelter")));
    public static final DeferredHolder<SpiritJobFactory, SpiritJobFactory> SMELT_TIER4 = JOBS.register("smelt_tier4",
            () -> new SpiritJobFactory((entity) -> new SmelterJob(entity,
                    () -> Occultism.SERVER_CONFIG.spiritJobs.smelterMarid.timeMultiplier.get().floatValue(),
                    Occultism.SERVER_CONFIG.spiritJobs.smelterMarid.operationCount::getAsInt
            ), SpiritJobClient.create("smelter")));
    //Crystallize jobs
    public static final DeferredHolder<SpiritJobFactory, SpiritJobFactory> CRYSTAL_TIER1 = JOBS.register("crystal_tier1",
            () -> new SpiritJobFactory((entity) -> new CrystallizerJob(entity,
                    () -> Occultism.SERVER_CONFIG.spiritJobs.crystallizerFoliot.timeMultiplier.get().floatValue(),
                    () -> Occultism.SERVER_CONFIG.spiritJobs.crystallizerFoliot.outputMultiplier.get().floatValue(),
                    Occultism.SERVER_CONFIG.spiritJobs.crystallizerFoliot.operationCount::getAsInt,
                    Occultism.SERVER_CONFIG.spiritJobs.crystallizerFoliot.tier::getAsInt
            ), SpiritJobClient.create("crystallizer")));
    public static final DeferredHolder<SpiritJobFactory, SpiritJobFactory> CRYSTAL_TIER2 = JOBS.register("crystal_tier2",
            () -> new SpiritJobFactory((entity) -> new CrystallizerJob(entity,
                    () -> Occultism.SERVER_CONFIG.spiritJobs.crystallizerDjinni.timeMultiplier.get().floatValue(),
                    () -> Occultism.SERVER_CONFIG.spiritJobs.crystallizerDjinni.outputMultiplier.get().floatValue(),
                    Occultism.SERVER_CONFIG.spiritJobs.crystallizerDjinni.operationCount::getAsInt,
                    Occultism.SERVER_CONFIG.spiritJobs.crystallizerDjinni.tier::getAsInt
            ), SpiritJobClient.create("crystallizer")));
    public static final DeferredHolder<SpiritJobFactory, SpiritJobFactory> CRYSTAL_TIER3 = JOBS.register("crystal_tier3",
            () -> new SpiritJobFactory((entity) -> new CrystallizerJob(entity,
                    () -> Occultism.SERVER_CONFIG.spiritJobs.crystallizerAfrit.timeMultiplier.get().floatValue(),
                    () -> Occultism.SERVER_CONFIG.spiritJobs.crystallizerAfrit.outputMultiplier.get().floatValue(),
                    Occultism.SERVER_CONFIG.spiritJobs.crystallizerAfrit.operationCount::getAsInt,
                    Occultism.SERVER_CONFIG.spiritJobs.crystallizerAfrit.tier::getAsInt
            ), SpiritJobClient.create("crystallizer")));
    public static final DeferredHolder<SpiritJobFactory, SpiritJobFactory> CRYSTAL_TIER4 = JOBS.register("crystal_tier4",
            () -> new SpiritJobFactory((entity) -> new CrystallizerJob(entity,
                    () -> Occultism.SERVER_CONFIG.spiritJobs.crystallizerMarid.timeMultiplier.get().floatValue(),
                    () -> Occultism.SERVER_CONFIG.spiritJobs.crystallizerMarid.outputMultiplier.get().floatValue(),
                    Occultism.SERVER_CONFIG.spiritJobs.crystallizerMarid.operationCount::getAsInt,
                    Occultism.SERVER_CONFIG.spiritJobs.crystallizerMarid.tier::getAsInt
            ), SpiritJobClient.create("crystallizer")));

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
