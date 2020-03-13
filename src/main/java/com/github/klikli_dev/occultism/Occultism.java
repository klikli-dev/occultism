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
import com.github.klikli_dev.occultism.client.gui.spirit.SpiritGui;
import com.github.klikli_dev.occultism.client.gui.storage.StableWormholeGui;
import com.github.klikli_dev.occultism.client.gui.storage.StorageControllerGui;
import com.github.klikli_dev.occultism.client.gui.storage.StorageRemoteGui;
import com.github.klikli_dev.occultism.client.render.SelectedBlockRenderer;
import com.github.klikli_dev.occultism.client.render.ThirdEyeEffectRenderer;
import com.github.klikli_dev.occultism.client.render.entity.FoliotRenderer;
import com.github.klikli_dev.occultism.client.render.tile.SacrificialBowlRenderer;
import com.github.klikli_dev.occultism.client.render.tile.StorageControllerRenderer;
import com.github.klikli_dev.occultism.common.OccultismItemGroup;
import com.github.klikli_dev.occultism.common.world.WorldGenHandler;
import com.github.klikli_dev.occultism.config.OccultismConfig;
import com.github.klikli_dev.occultism.network.OccultismPackets;
import com.github.klikli_dev.occultism.registry.*;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.client.model.obj.OBJLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
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
    public static final ThirdEyeEffectRenderer THIRD_EYE_EFFECT_RENDERER = new ThirdEyeEffectRenderer();
    public static Occultism INSTANCE;
    //endregion Fields

    //region Initialization
    public Occultism() {
        INSTANCE = this;
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, CONFIG.spec);
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        OccultismEffects.EFFECTS.register(modEventBus);
        OccultismRecipes.RECIPES.register(modEventBus);
        OccultismBlocks.BLOCKS.register(modEventBus);
        OccultismItems.ITEMS.register(modEventBus);
        OccultismTiles.TILES.register(modEventBus);
        OccultismContainers.CONTAINERS.register(modEventBus);
        OccultismEntities.ENTITIES.register(modEventBus);
        OccultismSounds.SOUNDS.register(modEventBus);
        OccultismParticles.PARTICLES.register(modEventBus);
        OccultismBiomeFeatures.FEATURES.register(modEventBus);

        //register event buses
        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(this::clientSetup);
        modEventBus.addListener(this::serverSetup);
        modEventBus.addListener(this::onModConfigEvent);

        MinecraftForge.EVENT_BUS.register(this);
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

        WorldGenHandler.setupOreGeneration();
        //TODO: re enable grove generation after reworking it to fit new generation
        WorldGenHandler.setupUndergroundGroveGeneration();

        OccultismAPI.commonSetup();

        LOGGER.info("Common setup complete.");
    }

    private void serverSetup(final FMLDedicatedServerSetupEvent event) {
        LOGGER.info("Dedicated server setup complete.");
    }

    private void clientSetup(final FMLClientSetupEvent event) {
        //Register client side event handlers
        MinecraftForge.EVENT_BUS.register(SELECTED_BLOCK_RENDERER);
        MinecraftForge.EVENT_BUS.register(THIRD_EYE_EFFECT_RENDERER);


        //Register Entity Renderers
        RenderingRegistry.registerEntityRenderingHandler(OccultismEntities.FOLIOT.get(), FoliotRenderer::new);

        //Register Tile Entity Renderers
        ClientRegistry.bindTileEntityRenderer(OccultismTiles.STORAGE_CONTROLLER.get(), StorageControllerRenderer::new);
        ClientRegistry.bindTileEntityRenderer(OccultismTiles.SACRIFICIAL_BOWL.get(), SacrificialBowlRenderer::new);
        ClientRegistry
                .bindTileEntityRenderer(OccultismTiles.GOLDEN_SACRIFICIAL_BOWL.get(), SacrificialBowlRenderer::new);

        //Setup block render layers
        RenderTypeLookup.setRenderLayer(OccultismBlocks.CHALK_GLYPH_WHITE.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(OccultismBlocks.CHALK_GLYPH_GOLD.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(OccultismBlocks.CHALK_GLYPH_PURPLE.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(OccultismBlocks.CHALK_GLYPH_RED.get(), RenderType.getCutout());

        RenderTypeLookup.setRenderLayer(OccultismBlocks.STABLE_WORMHOLE.get(), RenderType.getTranslucent());
        RenderTypeLookup.setRenderLayer(OccultismBlocks.SPIRIT_ATTUNED_CRYSTAL.get(), RenderType.getTranslucent());
        RenderTypeLookup.setRenderLayer(OccultismBlocks.DATURA.get(), RenderType.getCutoutMipped());
        RenderTypeLookup.setRenderLayer(OccultismBlocks.SPIRIT_FIRE.get(), RenderType.getCutoutMipped());
        RenderTypeLookup.setRenderLayer(OccultismBlocks.OTHERWORLD_SAPLING.get(), RenderType.getCutoutMipped());
        RenderTypeLookup.setRenderLayer(OccultismBlocks.OTHERWORLD_SAPLING_NATURAL.get(), RenderType.getCutoutMipped());
        RenderTypeLookup.setRenderLayer(OccultismBlocks.OTHERWORLD_LEAVES.get(), RenderType.getCutoutMipped());
        RenderTypeLookup.setRenderLayer(OccultismBlocks.OTHERWORLD_LEAVES_NATURAL.get(), RenderType.getCutoutMipped());

        //Not safe to call during parallel load, so register just after.
        DeferredWorkQueue.runLater(() -> {
            //Register screen factories
            ScreenManager.registerFactory(OccultismContainers.STORAGE_CONTROLLER.get(), StorageControllerGui::new);
            ScreenManager.registerFactory(OccultismContainers.STABLE_WORMHOLE.get(), StableWormholeGui::new);
            ScreenManager.registerFactory(OccultismContainers.STORAGE_REMOTE.get(), StorageRemoteGui::new);
            ScreenManager.registerFactory(OccultismContainers.SPIRIT.get(), SpiritGui::new);
            LOGGER.debug("Registered Screen Containers");
        });

        LOGGER.info("Client setup complete.");
    }
    //endregion Methods
}
