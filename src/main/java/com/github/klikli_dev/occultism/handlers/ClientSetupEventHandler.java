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
import com.github.klikli_dev.occultism.client.render.entity.*;
import com.github.klikli_dev.occultism.client.render.tile.SacrificialBowlRenderer;
import com.github.klikli_dev.occultism.client.render.tile.StorageControllerRenderer;
import com.github.klikli_dev.occultism.common.capability.FamiliarSettingsCapability;
import com.github.klikli_dev.occultism.common.container.spirit.SpiritContainer;
import com.github.klikli_dev.occultism.registry.*;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.client.util.InputMappings;
import net.minecraft.entity.EntityType;
import net.minecraft.item.ItemModelsProperties;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import org.lwjgl.glfw.GLFW;

import java.util.HashMap;
import java.util.Map;

@Mod.EventBusSubscriber(modid = Occultism.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientSetupEventHandler {

    //region Fields
    public static final KeyBinding KEY_BACKPACK =
            new KeyBinding("key.occultism.backpack", BackpackKeyConflictContext.INSTANCE,
                    InputMappings.Type.KEYSYM.getOrCreate(GLFW.GLFW_KEY_B), "key.occultism.category");

    public static final KeyBinding KEY_STORAGE_REMOTE =
            new KeyBinding("key.occultism.storage_remote", StorageRemoteKeyConflictContext.INSTANCE,
                    InputMappings.Type.KEYSYM.getOrCreate(GLFW.GLFW_KEY_N), "key.occultism.category");

    public static Map<EntityType<?>, KeyBinding> keysFamiliars;

    //endregion Fields

    //region Static Methods
    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        //Register client side event handlers
        MinecraftForge.EVENT_BUS.register(Occultism.SELECTED_BLOCK_RENDERER);
        MinecraftForge.EVENT_BUS.register(Occultism.THIRD_EYE_EFFECT_RENDERER);

        //keybindings
        event.enqueueWork(() -> {
            ClientRegistry.registerKeyBinding(KEY_BACKPACK);
            ClientRegistry.registerKeyBinding(KEY_STORAGE_REMOTE);

            keysFamiliars = new HashMap<>();
            for (EntityType<?> familiar : FamiliarSettingsCapability.getFamiliars()) {
                KeyBinding kb = new KeyBinding("key.occultism.familiar." + familiar.getRegistryName().getPath(), KeyConflictContext.IN_GAME,
                        InputMappings.Type.KEYSYM.getOrCreate(-1), "key.occultism.category");
                keysFamiliars.put(familiar, kb);
                ClientRegistry.registerKeyBinding(kb);
            }
        });

        //Register Entity Renderers
        RenderingRegistry.registerEntityRenderingHandler(OccultismEntities.FOLIOT.get(), FoliotRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(OccultismEntities.DJINNI.get(), DjinniRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(OccultismEntities.AFRIT.get(), AfritRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(OccultismEntities.AFRIT_WILD.get(), AfritRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(OccultismEntities.MARID.get(), MaridRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(OccultismEntities.GREEDY_FAMILIAR.get(), GreedyFamiliarRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(OccultismEntities.BAT_FAMILIAR.get(), BatFamiliarRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(OccultismEntities.DEER_FAMILIAR.get(), DeerFamiliarRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(OccultismEntities.CTHULHU_FAMILIAR.get(), CthulhuFamiliarRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(OccultismEntities.DEVIL_FAMILIAR.get(), DevilFamiliarRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(OccultismEntities.DRAGON_FAMILIAR.get(), DragonFamiliarRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(OccultismEntities.BLACKSMITH_FAMILIAR.get(), BlacksmithFamiliarRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(OccultismEntities.GUARDIAN_FAMILIAR.get(), GuardianFamiliarRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(OccultismEntities.HEADLESS_FAMILIAR.get(), HeadlessFamiliarRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(OccultismEntities.CHIMERA_FAMILIAR.get(), ChimeraFamiliarRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(OccultismEntities.GOAT_FAMILIAR.get(), GoatFamiliarRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(OccultismEntities.SHUB_NIGGURATH_FAMILIAR.get(), ShubNiggurathFamiliarRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(OccultismEntities.BEHOLDER_FAMILIAR.get(), BeholderFamiliarRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(OccultismEntities.FAIRY_FAMILIAR.get(), FairyFamiliarRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(OccultismEntities.MUMMY_FAMILIAR.get(), MummyFamiliarRenderer::new);
        
        RenderingRegistry.registerEntityRenderingHandler(OccultismEntities.THROWN_SWORD.get(), m -> new DragonRendering.ThrownSwordRenderer(m, Minecraft.getInstance().getItemRenderer()));
        RenderingRegistry.registerEntityRenderingHandler(OccultismEntities.SHUB_NIGGURATH_SPAWN.get(), ShubNiggurathSpawnRenderer::new);
        RenderingRegistry
                .registerEntityRenderingHandler(OccultismEntities.POSSESSED_ENDERMITE.get(), EndermiteRenderer::new);
        RenderingRegistry
                .registerEntityRenderingHandler(OccultismEntities.POSSESSED_SKELETON.get(), SkeletonRenderer::new);
        RenderingRegistry
                .registerEntityRenderingHandler(OccultismEntities.POSSESSED_ENDERMAN.get(), EndermanRenderer::new);
        RenderingRegistry
                .registerEntityRenderingHandler(OccultismEntities.POSSESSED_GHAST.get(), GhastRenderer::new);
        RenderingRegistry
                .registerEntityRenderingHandler(OccultismEntities.WILD_HUNT_SKELETON.get(), SkeletonRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(OccultismEntities.WILD_HUNT_WITHER_SKELETON.get(),
                WitherSkeletonRenderer::new);
        RenderingRegistry
                .registerEntityRenderingHandler(OccultismEntities.OTHERWORLD_BIRD.get(), OtherworldBirdRenderer::new);

        //Register Tile Entity Renderers
        ClientRegistry.bindTileEntityRenderer(OccultismTiles.STORAGE_CONTROLLER.get(), StorageControllerRenderer::new);
        ClientRegistry.bindTileEntityRenderer(OccultismTiles.SACRIFICIAL_BOWL.get(), SacrificialBowlRenderer::new);
        ClientRegistry
                .bindTileEntityRenderer(OccultismTiles.GOLDEN_SACRIFICIAL_BOWL.get(), SacrificialBowlRenderer::new);

        //Setup block render layers
        RenderTypeLookup.setRenderLayer(OccultismBlocks.CHALK_GLYPH_WHITE.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(OccultismBlocks.CHALK_GLYPH_GOLD.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(OccultismBlocks.CHALK_GLYPH_PURPLE.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(OccultismBlocks.CHALK_GLYPH_RED.get(), RenderType.cutout());

        RenderTypeLookup.setRenderLayer(OccultismBlocks.STABLE_WORMHOLE.get(), RenderType.translucent());
        RenderTypeLookup.setRenderLayer(OccultismBlocks.SPIRIT_ATTUNED_CRYSTAL.get(), RenderType.translucent());
        RenderTypeLookup.setRenderLayer(OccultismBlocks.DATURA.get(), RenderType.cutoutMipped());
        RenderTypeLookup.setRenderLayer(OccultismBlocks.SPIRIT_FIRE.get(), RenderType.cutoutMipped());
        RenderTypeLookup.setRenderLayer(OccultismBlocks.OTHERWORLD_SAPLING.get(), RenderType.cutoutMipped());
        RenderTypeLookup.setRenderLayer(OccultismBlocks.OTHERWORLD_SAPLING_NATURAL.get(), RenderType.cutoutMipped());
        RenderTypeLookup.setRenderLayer(OccultismBlocks.OTHERWORLD_LEAVES.get(), RenderType.cutoutMipped());
        RenderTypeLookup.setRenderLayer(OccultismBlocks.OTHERWORLD_LEAVES_NATURAL.get(), RenderType.cutoutMipped());

        registerItemModelProperties(event);

        //Not safe to call during parallel load, so register to run threadsafe.
        event.enqueueWork(() -> {
            //Register screen factories
            ScreenManager.register(OccultismContainers.STORAGE_CONTROLLER.get(), StorageControllerGui::new);
            ScreenManager.register(OccultismContainers.STABLE_WORMHOLE.get(), StableWormholeGui::new);
            ScreenManager.register(OccultismContainers.STORAGE_REMOTE.get(), StorageRemoteGui::new);
            ScreenManager.register(OccultismContainers.SPIRIT.get(), SpiritGui<SpiritContainer>::new);
            ScreenManager.register(OccultismContainers.SPIRIT_TRANSPORTER.get(), SpiritTransporterGui::new);
            ScreenManager.register(OccultismContainers.OTHERWORLD_MINER.get(), DimensionalMineshaftScreen::new);
            ScreenManager.register(OccultismContainers.SATCHEL.get(), SatchelScreen::new);
            Occultism.LOGGER.debug("Registered Screen Containers");
        });

        Occultism.LOGGER.info("Client setup complete.");
    }

    public static void registerItemModelProperties(FMLClientSetupEvent event) {

        //Not safe to call during parallel load, so register to run threadsafe
        event.enqueueWork(() -> {
            //Register item model properties
            ItemModelsProperties.register(OccultismItems.GUIDE_BOOK.get(),
                    new ResourceLocation("completion"), new GuideBookItemPropertyGetter());
            ItemModelsProperties.register(OccultismItems.SOUL_GEM_ITEM.get(),
                    new ResourceLocation(Occultism.MODID, "has_entity"), new SoulGemItemPropertyGetter());
            ItemModelsProperties.register(OccultismItems.DIVINATION_ROD.get(),
                    new ResourceLocation(Occultism.MODID, "distance"), new DivinationRodItemPropertyGetter());
            ItemModelsProperties.register(OccultismItems.OTHERWORLD_SAPLING_NATURAL.get(),
                    new ResourceLocation(Occultism.MODID, "simulated"), new OtherworldBlockItemPropertyGetter());
            ItemModelsProperties.register(OccultismItems.STORAGE_REMOTE.get(),
                    new ResourceLocation(Occultism.MODID, "linked"), new StorageRemoteItemPropertyGetter());
            ItemModelsProperties.register(OccultismItems.STABLE_WORMHOLE.get(),
                    new ResourceLocation(Occultism.MODID, "linked"), new StableWormholeBlockItemPropertyGetter());

            Occultism.LOGGER.debug("Registered Item Properties");
        });
    }
    //endregion Static Methods
}
