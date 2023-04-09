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

package com.github.klikli_dev.occultism.handlers;

import com.github.klikli_dev.occultism.Occultism;
import com.github.klikli_dev.occultism.client.gui.DimensionalMineshaftScreen;
import com.github.klikli_dev.occultism.client.gui.spirit.SpiritGui;
import com.github.klikli_dev.occultism.client.gui.spirit.SpiritTransporterGui;
import com.github.klikli_dev.occultism.client.gui.storage.SatchelScreen;
import com.github.klikli_dev.occultism.client.gui.storage.StableWormholeGui;
import com.github.klikli_dev.occultism.client.gui.storage.StorageControllerGui;
import com.github.klikli_dev.occultism.client.gui.storage.StorageRemoteGui;
import com.github.klikli_dev.occultism.client.itemproperties.*;
import com.github.klikli_dev.occultism.client.keybindings.BackpackKeyConflictContext;
import com.github.klikli_dev.occultism.client.keybindings.StorageRemoteKeyConflictContext;
import com.github.klikli_dev.occultism.client.model.entity.*;
import com.github.klikli_dev.occultism.client.render.ThirdEyeEffectRenderer;
import com.github.klikli_dev.occultism.client.render.blockentity.SacrificialBowlRenderer;
import com.github.klikli_dev.occultism.client.render.blockentity.StorageControllerGeoRenderer;
import com.github.klikli_dev.occultism.client.render.entity.*;
import com.github.klikli_dev.occultism.client.render.entity.MummyFamiliarRenderer.KapowModel;
import com.github.klikli_dev.occultism.common.capability.FamiliarSettingsCapability;
import com.github.klikli_dev.occultism.common.container.spirit.SpiritContainer;
import com.github.klikli_dev.occultism.integration.modonomicon.PageRenderers;
import com.github.klikli_dev.occultism.registry.*;
import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.registries.ForgeRegistries;
import org.lwjgl.glfw.GLFW;

import java.util.HashMap;
import java.util.Map;

@Mod.EventBusSubscriber(modid = Occultism.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientSetupEventHandler {

    public static final KeyMapping KEY_BACKPACK =
            new KeyMapping("key.occultism.backpack", BackpackKeyConflictContext.INSTANCE,
                    InputConstants.Type.KEYSYM.getOrCreate(GLFW.GLFW_KEY_B), "key.occultism.category");

    public static final KeyMapping KEY_STORAGE_REMOTE =
            new KeyMapping("key.occultism.storage_remote", StorageRemoteKeyConflictContext.INSTANCE,
                    InputConstants.Type.KEYSYM.getOrCreate(GLFW.GLFW_KEY_N), "key.occultism.category");

    public static Map<EntityType<?>, KeyMapping> keysFamiliars;

    @SubscribeEvent
    public static void onRegisterEntityRendererLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        //Register Entity Layers
        event.registerLayerDefinition(OccultismModelLayers.AFRIT, AfritModel::createBodyLayer);
        event.registerLayerDefinition(OccultismModelLayers.AFRIT_WILD, AfritWildModel::createBodyLayer);
        event.registerLayerDefinition(OccultismModelLayers.FAMILIAR_BAT, BatFamiliarModel::createBodyLayer);
        event.registerLayerDefinition(OccultismModelLayers.FAMILIAR_DEER, DeerFamiliarModel::createBodyLayer);
        event.registerLayerDefinition(OccultismModelLayers.FAMILIAR_GREEDY, GreedyFamiliarModel::createBodyLayer);
        event.registerLayerDefinition(OccultismModelLayers.FAMILIAR_CTHULHU, CthulhuFamiliarModel::createBodyLayer);
        event.registerLayerDefinition(OccultismModelLayers.FAMILIAR_DEVIL, DevilFamiliarModel::createBodyLayer);
        event.registerLayerDefinition(OccultismModelLayers.FAMILIAR_DRAGON, DragonFamiliarModel::createBodyLayer);
        event.registerLayerDefinition(OccultismModelLayers.FAMILIAR_BLACKSMITH, BlacksmithFamiliarModel::createBodyLayer);
        event.registerLayerDefinition(OccultismModelLayers.FAMILIAR_GUARDIAN, GuardianFamiliarModel::createBodyLayer);
        event.registerLayerDefinition(OccultismModelLayers.FAMILIAR_HEADLESS, HeadlessFamiliarModel::createBodyLayer);
        event.registerLayerDefinition(OccultismModelLayers.FAMILIAR_CHIMERA, ChimeraFamiliarModel::createBodyLayer);
        event.registerLayerDefinition(OccultismModelLayers.FAMILIAR_GOAT, GoatFamiliarModel::createBodyLayer);
        event.registerLayerDefinition(OccultismModelLayers.FAMILIAR_SHUB_NIGGURATH, ShubNiggurathFamiliarModel::createBodyLayer);
        event.registerLayerDefinition(OccultismModelLayers.FAMILIAR_SHUB_NIGGURATH_SPAWN, ShubNiggurathSpawnModel::createBodyLayer);
        event.registerLayerDefinition(OccultismModelLayers.FAMILIAR_BEHOLDER, BeholderFamiliarModel::createBodyLayer);
        event.registerLayerDefinition(OccultismModelLayers.FAMILIAR_FAIRY, FairyFamiliarModel::createBodyLayer);
        event.registerLayerDefinition(OccultismModelLayers.FAMILIAR_MUMMY, MummyFamiliarModel::createBodyLayer);
        event.registerLayerDefinition(OccultismModelLayers.FAMILIAR_BEAVER, BeaverFamiliarModel::createBodyLayer);
        event.registerLayerDefinition(OccultismModelLayers.DJINNI, DjinniModel::createBodyLayer);
        event.registerLayerDefinition(OccultismModelLayers.FOLIOT, FoliotModel::createBodyLayer);
        event.registerLayerDefinition(OccultismModelLayers.MARID, MaridModel::createBodyLayer);
        event.registerLayerDefinition(OccultismModelLayers.KAPOW, KapowModel::createBodyLayer);
    }

    @SubscribeEvent
    public static void onRegisterEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        //Register Entity Renderers
        event.registerEntityRenderer(OccultismEntities.FOLIOT.get(), FoliotRenderer::new);
        event.registerEntityRenderer(OccultismEntities.DJINNI.get(), DjinniRenderer::new);
        event.registerEntityRenderer(OccultismEntities.AFRIT.get(), AfritRenderer::new);
        event.registerEntityRenderer(OccultismEntities.AFRIT_WILD.get(), AfritWildRenderer::new);
        event.registerEntityRenderer(OccultismEntities.MARID.get(), MaridRenderer::new);
        event.registerEntityRenderer(OccultismEntities.GREEDY_FAMILIAR.get(), GreedyFamiliarRenderer::new);
        event.registerEntityRenderer(OccultismEntities.BAT_FAMILIAR.get(), BatFamiliarRenderer::new);
        event.registerEntityRenderer(OccultismEntities.DEER_FAMILIAR.get(), DeerFamiliarRenderer::new);
        event.registerEntityRenderer(OccultismEntities.CTHULHU_FAMILIAR.get(), CthulhuFamiliarRenderer::new);
        event.registerEntityRenderer(OccultismEntities.DEVIL_FAMILIAR.get(), DevilFamiliarRenderer::new);
        event.registerEntityRenderer(OccultismEntities.DRAGON_FAMILIAR.get(), DragonFamiliarRenderer::new);
        event.registerEntityRenderer(OccultismEntities.BLACKSMITH_FAMILIAR.get(), BlacksmithFamiliarRenderer::new);
        event.registerEntityRenderer(OccultismEntities.GUARDIAN_FAMILIAR.get(), GuardianFamiliarRenderer::new);
        event.registerEntityRenderer(OccultismEntities.HEADLESS_FAMILIAR.get(), HeadlessFamiliarRenderer::new);
        event.registerEntityRenderer(OccultismEntities.THROWN_SWORD.get(), DragonRendering.ThrownSwordRenderer::new);
        event.registerEntityRenderer(OccultismEntities.CHIMERA_FAMILIAR.get(), ChimeraFamiliarRenderer::new);
        event.registerEntityRenderer(OccultismEntities.SHUB_NIGGURATH_FAMILIAR.get(), ShubNiggurathFamiliarRenderer::new);
        event.registerEntityRenderer(OccultismEntities.BEHOLDER_FAMILIAR.get(), BeholderFamiliarRenderer::new);
        event.registerEntityRenderer(OccultismEntities.FAIRY_FAMILIAR.get(), FairyFamiliarRenderer::new);
        event.registerEntityRenderer(OccultismEntities.MUMMY_FAMILIAR.get(), MummyFamiliarRenderer::new);
        event.registerEntityRenderer(OccultismEntities.BEAVER_FAMILIAR.get(), BeaverFamiliarRenderer::new);
        event.registerEntityRenderer(OccultismEntities.GOAT_FAMILIAR.get(), GoatFamiliarRenderer::new);
        event.registerEntityRenderer(OccultismEntities.SHUB_NIGGURATH_SPAWN.get(), ShubNiggurathSpawnRenderer::new);
        event.registerEntityRenderer(OccultismEntities.POSSESSED_ENDERMITE.get(), EndermiteRenderer::new);
        event.registerEntityRenderer(OccultismEntities.POSSESSED_SKELETON.get(), SkeletonRenderer::new);
        event.registerEntityRenderer(OccultismEntities.POSSESSED_ENDERMAN.get(), EndermanRenderer::new);
        event.registerEntityRenderer(OccultismEntities.POSSESSED_GHAST.get(), GhastRenderer::new);
        event.registerEntityRenderer(OccultismEntities.WILD_HUNT_SKELETON.get(), SkeletonRenderer::new);
        event.registerEntityRenderer(OccultismEntities.WILD_HUNT_WITHER_SKELETON.get(),
                WitherSkeletonRenderer::new);
        event.registerEntityRenderer(OccultismEntities.OTHERWORLD_BIRD.get(), OtherworldBirdRenderer::new);
    }

    @SubscribeEvent
    public static void onRegisterKeyMappings(RegisterKeyMappingsEvent event){
        event.register(KEY_BACKPACK);
        event.register(KEY_STORAGE_REMOTE);

        keysFamiliars = new HashMap<>();
        for (EntityType<?> familiar : FamiliarSettingsCapability.getFamiliars()) {
            KeyMapping kb = new KeyMapping("key.occultism.familiar." + ForgeRegistries.ENTITY_TYPES.getKey(familiar).getPath(), KeyConflictContext.IN_GAME,
                    InputConstants.Type.KEYSYM.getOrCreate(-1), "key.occultism.category");
            keysFamiliars.put(familiar, kb);
            event.register(kb);
        }
    }

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        //Register client side event handlers
        MinecraftForge.EVENT_BUS.register(Occultism.SELECTED_BLOCK_RENDERER);
        MinecraftForge.EVENT_BUS.register(Occultism.THIRD_EYE_EFFECT_RENDERER);

        //Register Tile Entity Renderers
        BlockEntityRenderers.register(OccultismTiles.STORAGE_CONTROLLER.get(), StorageControllerGeoRenderer::new);
        BlockEntityRenderers.register(OccultismTiles.SACRIFICIAL_BOWL.get(), SacrificialBowlRenderer::new);
        BlockEntityRenderers.register(OccultismTiles.GOLDEN_SACRIFICIAL_BOWL.get(), SacrificialBowlRenderer::new);

        registerItemModelProperties(event);

        //Not safe to call during parallel load, so register to run threadsafe.
        event.enqueueWork(() -> {
            //Register screen factories
            MenuScreens.register(OccultismContainers.STORAGE_CONTROLLER.get(), StorageControllerGui::new);
            MenuScreens.register(OccultismContainers.STABLE_WORMHOLE.get(), StableWormholeGui::new);
            MenuScreens.register(OccultismContainers.STORAGE_REMOTE.get(), StorageRemoteGui::new);
            MenuScreens.register(OccultismContainers.SPIRIT.get(), SpiritGui<SpiritContainer>::new);
            MenuScreens.register(OccultismContainers.SPIRIT_TRANSPORTER.get(), SpiritTransporterGui::new);
            MenuScreens.register(OccultismContainers.OTHERWORLD_MINER.get(), DimensionalMineshaftScreen::new);
            MenuScreens.register(OccultismContainers.SATCHEL.get(), SatchelScreen::new);
            Occultism.LOGGER.debug("Registered Screen Containers");
        });

        PageRenderers.onClientSetup(event);

        Occultism.LOGGER.debug("Registered Overlays");

        Occultism.LOGGER.info("Client setup complete.");
    }

    public static void registerItemModelProperties(FMLClientSetupEvent event) {

        //Not safe to call during parallel load, so register to run threadsafe
        event.enqueueWork(() -> {
            //Register item model properties

            ItemProperties.register(OccultismItems.SOUL_GEM_ITEM.get(),
                    new ResourceLocation(Occultism.MODID, "has_entity"), new SoulGemItemPropertyGetter());
            ItemProperties.register(OccultismItems.DIVINATION_ROD.get(),
                    new ResourceLocation(Occultism.MODID, "distance"), new DivinationRodItemPropertyGetter());
            ItemProperties.register(OccultismItems.OTHERWORLD_SAPLING_NATURAL.get(),
                    new ResourceLocation(Occultism.MODID, "simulated"), new OtherworldBlockItemPropertyGetter());
            ItemProperties.register(OccultismItems.STORAGE_REMOTE.get(),
                    new ResourceLocation(Occultism.MODID, "linked"), new StorageRemoteItemPropertyGetter());
            ItemProperties.register(OccultismItems.STABLE_WORMHOLE.get(),
                    new ResourceLocation(Occultism.MODID, "linked"), new StableWormholeBlockItemPropertyGetter());

            Occultism.LOGGER.debug("Registered Item Properties");
        });
    }
    @SubscribeEvent
    public static void onRegisterGuiOverlays(RegisterGuiOverlaysEvent event){
        event.registerAboveAll("third_eye", (gui, poseStack, partialTick, screenWidth, screenHeight) -> {
            if(  Occultism.THIRD_EYE_EFFECT_RENDERER.gogglesActiveLastTick || Occultism.THIRD_EYE_EFFECT_RENDERER.thirdEyeActiveLastTick){
                gui.setupOverlayRenderState(true, false, ThirdEyeEffectRenderer.THIRD_EYE_TEXTURE);
                Occultism.THIRD_EYE_EFFECT_RENDERER.renderOverlay(poseStack);
            }
        });
    }
}
