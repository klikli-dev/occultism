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

import com.github.klikli_dev.occultism.client.gui.spirit.SpiritGui;
import com.github.klikli_dev.occultism.client.gui.storage.StorageControllerGui;
import com.github.klikli_dev.occultism.client.gui.storage.StorageRemoteGui;
import com.github.klikli_dev.occultism.client.render.OccultismRenderType;
import com.github.klikli_dev.occultism.client.render.SelectedBlockRenderer;
import com.github.klikli_dev.occultism.client.render.entity.FoliotRenderer;
import com.github.klikli_dev.occultism.client.render.tile.StorageControllerRenderer;
import com.github.klikli_dev.occultism.common.OccultismItemGroup;
import com.github.klikli_dev.occultism.config.OccultismConfig;
import com.github.klikli_dev.occultism.crafting.recipe.SpiritTrade;
import com.github.klikli_dev.occultism.network.OccultismPackets;
import com.github.klikli_dev.occultism.registry.*;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.DeferredWorkQueue;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLDedicatedServerSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(Occultism.MODID)
public class Occultism {
    //region Fields
    public static final String MODID = "occultism";
    public static final String NAME = "Occultism";
    public static final ItemGroup ITEM_GROUP = new OccultismItemGroup();
    public static final Logger LOGGER = LogManager.getLogger(MODID);
    public static final OccultismConfig CONFIG = new OccultismConfig();
    public static final SelectedBlockRenderer SELECTED_BLOCK_RENDERER = new SelectedBlockRenderer();
    public static Occultism INSTANCE;
    //endregion Fields

    //region Initialization
    public Occultism() {
        INSTANCE = this;
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, CONFIG.spec);

        OccultismRecipes.RECIPES.register(FMLJavaModLoadingContext.get().getModEventBus());
        OccultismBlocks.BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
        OccultismItems.ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
        OccultismTiles.TILES.register(FMLJavaModLoadingContext.get().getModEventBus());
        OccultismContainers.CONTAINERS.register(FMLJavaModLoadingContext.get().getModEventBus());
        OccultismEntities.ENTITIES.register(FMLJavaModLoadingContext.get().getModEventBus());
        OccultismSounds.SOUNDS.register(FMLJavaModLoadingContext.get().getModEventBus());
        OccultismParticles.PARTICLES.register(FMLJavaModLoadingContext.get().getModEventBus());

        OccultismSpiritJobs.JOBS.register(FMLJavaModLoadingContext.get().getModEventBus());
        OccultismRituals.PENTACLES.register(FMLJavaModLoadingContext.get().getModEventBus());
        OccultismRituals.RITUALS.register(FMLJavaModLoadingContext.get().getModEventBus());

        //register event buses
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::commonSetup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::clientSetup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::serverSetup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::onModConfigEvent);

        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(SELECTED_BLOCK_RENDERER);
    }

    //endregion Initialization
    //region Methods
    public void onModConfigEvent(final ModConfig.ModConfigEvent event) {
        if (event.getConfig().getSpec() == CONFIG.spec) {
            //Clear the config cache on reload.
            CONFIG.clear();
        }
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        OccultismPackets.registerMessages();
        LOGGER.info("Common setup complete.");
    }

    private void serverSetup(final FMLDedicatedServerSetupEvent event) {
        LOGGER.info("Dedicated server setup complete.");
    }

    private void clientSetup(final FMLClientSetupEvent event) {
        //Register Entity Renderers
        RenderingRegistry.registerEntityRenderingHandler(OccultismEntities.FOLIOT.get(), FoliotRenderer::new);

        //Register Tile Entity Renderers
        ClientRegistry.bindTileEntityRenderer(OccultismTiles.STORAGE_CONTROLLER.get(), StorageControllerRenderer::new);

        //Setup block render layers
        RenderTypeLookup.setRenderLayer(OccultismBlocks.CHALK_GLYPH_WHITE.get(), RenderType.getCutoutMipped());
        RenderTypeLookup.setRenderLayer(OccultismBlocks.CHALK_GLYPH_GOLD.get(), RenderType.getCutoutMipped());
        RenderTypeLookup.setRenderLayer(OccultismBlocks.CHALK_GLYPH_PURPLE.get(), RenderType.getCutoutMipped());
        RenderTypeLookup.setRenderLayer(OccultismBlocks.CHALK_GLYPH_RED.get(), RenderType.getCutoutMipped());
        RenderTypeLookup.setRenderLayer(OccultismBlocks.STABLE_WORMHOLE.get(), RenderType.getTranslucent());

        //Not safe to call during parallel load, so register just after.
        DeferredWorkQueue.runLater(() -> {
            //Register screen factories
            ScreenManager.registerFactory(OccultismContainers.STORAGE_CONTROLLER.get(), StorageControllerGui::new);
            ScreenManager.registerFactory(OccultismContainers.STORAGE_REMOTE.get(), StorageRemoteGui::new);
            ScreenManager.registerFactory(OccultismContainers.SPIRIT.get(), SpiritGui::new);
            LOGGER.debug("Registered Screen Containers");
        });

        LOGGER.info("Client setup complete.");
    }
    //endregion Methods
}
