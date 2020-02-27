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

package com.github.klikli_dev.occultism;

import com.github.klikli_dev.occultism.api.OccultismAPI;
import com.github.klikli_dev.occultism.common.OccultismTab;
import com.github.klikli_dev.occultism.common.world.OreGen;
import com.github.klikli_dev.occultism.common.world.WorldGen;
import com.github.klikli_dev.occultism.proxy.CommonProxy;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.common.registry.GameRegistry;
import org.apache.logging.log4j.Logger;

@Mod(Occultism.MODID)
public class Occultism {
    //region Fields
    public static final String MODID = "occultism";
    public static final String NAME = "Occultism";
    public static final ItemGroup CREATIVE_TAB = new OccultismTab();
    @SidedProxy(clientSide = "com.github.klikli_dev.occultism.proxy.ClientProxy", serverSide = "com.github.klikli_dev.occultism.proxy.ServerProxy")
    public static CommonProxy proxy;
    public static SimpleNetworkWrapper network = new SimpleNetworkWrapper(MODID);
    @Mod.Instance
    public static Occultism instance;
    public static Logger logger;
    //endregion Fields

    //region Methods
    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        logger = event.getModLog();
        proxy.preInit(event);
        GameRegistry.registerWorldGenerator(new WorldGen(), 0);
        GameRegistry.registerWorldGenerator(new OreGen(), 0);
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        logger.info("Initializing {} ({}-{})", NAME, MODID, VERSION);
        proxy.init(event);
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent e) {
        proxy.postInit(e);
        OccultismAPI.postInit();
    }
    //endregion Methods
}
