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
import com.github.klikli_dev.occultism.client.gui.DimensionalMineshaftScreen;
import com.github.klikli_dev.occultism.client.gui.spirit.SpiritGui;
import com.github.klikli_dev.occultism.client.gui.spirit.SpiritTransporterGui;
import com.github.klikli_dev.occultism.client.gui.storage.StableWormholeGui;
import com.github.klikli_dev.occultism.client.gui.storage.StorageControllerGui;
import com.github.klikli_dev.occultism.client.gui.storage.StorageRemoteGui;
import com.github.klikli_dev.occultism.client.render.SelectedBlockRenderer;
import com.github.klikli_dev.occultism.client.render.ThirdEyeEffectRenderer;
import com.github.klikli_dev.occultism.client.render.entity.AfritRenderer;
import com.github.klikli_dev.occultism.client.render.entity.DjinniRenderer;
import com.github.klikli_dev.occultism.client.render.entity.FoliotRenderer;
import com.github.klikli_dev.occultism.client.render.entity.OtherworldBirdRenderer;
import com.github.klikli_dev.occultism.client.render.tile.SacrificialBowlRenderer;
import com.github.klikli_dev.occultism.client.render.tile.StorageControllerRenderer;
import com.github.klikli_dev.occultism.common.DebugHelper;
import com.github.klikli_dev.occultism.common.OccultismItemGroup;
import com.github.klikli_dev.occultism.common.container.spirit.SpiritContainer;
import com.github.klikli_dev.occultism.common.item.otherworld.OtherworldBlockItem;
import com.github.klikli_dev.occultism.common.item.storage.StableWormholeBlockItem;
import com.github.klikli_dev.occultism.common.item.storage.StorageRemoteItem;
import com.github.klikli_dev.occultism.common.item.tool.DivinationRodItem;
import com.github.klikli_dev.occultism.common.item.tool.GuideBookItem;
import com.github.klikli_dev.occultism.common.item.tool.SoulGemItem;
import com.github.klikli_dev.occultism.common.world.WorldGenHandler;
import com.github.klikli_dev.occultism.config.OccultismConfig;
import com.github.klikli_dev.occultism.network.OccultismPackets;
import com.github.klikli_dev.occultism.registry.*;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.client.renderer.entity.EndermanRenderer;
import net.minecraft.client.renderer.entity.EndermiteRenderer;
import net.minecraft.client.renderer.entity.SkeletonRenderer;
import net.minecraft.client.renderer.entity.WitherSkeletonRenderer;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemModelsProperties;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
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
import vazkii.patchouli.api.PatchouliAPI;

import static com.github.klikli_dev.occultism.util.StaticUtil.modLoc;

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
    public static final DebugHelper DEBUG = new DebugHelper();
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
        modEventBus.addListener(OccultismCapabilities::commonSetup);
        modEventBus.addListener(this::commonSetup);
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

        WorldGenHandler.registerConfiguredFeatures();

        OccultismAPI.commonSetup();

        //Register multiblocks
        OccultismRituals.PENTACLE_REGISTRY.getValues().forEach(pentacle -> {
            ResourceLocation multiBlockId = modLoc("pentacle." + pentacle.getRegistryName().getPath());
            if (PatchouliAPI.instance.getMultiblock(multiBlockId) == null)
                pentacle.registerMultiblock(multiBlockId);
        });

        //Register entity attributes on single thread
        event.enqueueWork(OccultismEntities::registerEntityAttributes);

        LOGGER.info("Common setup complete.");
    }

    private void serverSetup(final FMLDedicatedServerSetupEvent event) {
        LOGGER.info("Dedicated server setup complete.");
    }
    //endregion Methods
}
