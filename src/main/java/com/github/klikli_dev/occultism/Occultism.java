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

import com.github.klikli_dev.occultism.common.OccultismBlocks;
import com.github.klikli_dev.occultism.common.OccultismItemGroup;
import com.github.klikli_dev.occultism.common.OccultismItems;
import com.github.klikli_dev.occultism.common.OccultismSounds;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLDedicatedServerSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(Occultism.MODID)
public class Occultism {
    //region Fields
    public static final String MODID = "occultism";
    public static final String NAME = "Occultism";
    public static final ItemGroup ITEM_GROUP = new OccultismItemGroup();
    public static final Logger logger = LogManager.getLogger(MODID);
    //endregion Fields

    //region Initialization
    public Occultism() {
        OccultismItems.ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
        OccultismBlocks.BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
        OccultismSounds.SOUNDS.register(FMLJavaModLoadingContext.get().getModEventBus());

        //register event buses
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::commonSetup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::clientSetup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::serverSetup);

        MinecraftForge.EVENT_BUS.register(this);
    }

    //endregion Initialization
    //region Methods

    private void commonSetup(final FMLCommonSetupEvent event) {
        logger.info("Common setup complete.");
    }

    private void serverSetup(final FMLDedicatedServerSetupEvent event) {
        logger.info("Dedicated server setup complete.");
    }

    private void clientSetup(final FMLClientSetupEvent event) {
        //Setup block render layers
        RenderTypeLookup.setRenderLayer(OccultismBlocks.CHALK_GLYPH_WHITE.get(), RenderType.getCutoutMipped());
        RenderTypeLookup.setRenderLayer(OccultismBlocks.CHALK_GLYPH_GOLD.get(), RenderType.getCutoutMipped());
        RenderTypeLookup.setRenderLayer(OccultismBlocks.CHALK_GLYPH_PURPLE.get(), RenderType.getCutoutMipped());
        RenderTypeLookup.setRenderLayer(OccultismBlocks.CHALK_GLYPH_RED.get(), RenderType.getCutoutMipped());
        logger.info("Client setup complete.");
    }
    //endregion Methods
}
