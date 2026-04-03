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

package com.klikli_dev.occultism.handlers;

import com.klikli_dev.occultism.Occultism;
import com.klikli_dev.occultism.client.gui.DimensionalBattlefieldScreen;
import com.klikli_dev.occultism.client.gui.DimensionalMineshaftScreen;
import com.klikli_dev.occultism.client.gui.satchel.RitualSatchelScreen;
import com.klikli_dev.occultism.client.gui.satchel.SatchelScreen;
import com.klikli_dev.occultism.client.gui.spirit.SpiritGui;
import com.klikli_dev.occultism.client.gui.spirit.SpiritTransporterGui;
import com.klikli_dev.occultism.client.gui.storage.StableWormholeGui;
import com.klikli_dev.occultism.client.gui.storage.StorageControllerGui;
import com.klikli_dev.occultism.client.gui.storage.StorageControllerGuiBase;
import com.klikli_dev.occultism.client.gui.storage.StorageRemoteGui;
import com.klikli_dev.occultism.client.itemproperties.*;
import com.klikli_dev.occultism.client.keybindings.BackpackKeyConflictContext;
import com.klikli_dev.occultism.client.keybindings.EnderBagKeyConflictContext;
import com.klikli_dev.occultism.client.keybindings.StorageRemoteKeyConflictContext;
import com.klikli_dev.occultism.client.model.entity.*;
import com.klikli_dev.occultism.client.render.GoldenSacrificialBowlHUD;
import com.klikli_dev.occultism.client.render.blockentity.EntityWormholeRenderer;
import com.klikli_dev.occultism.client.render.blockentity.GoldenSacrificialBowlRenderer;
import com.klikli_dev.occultism.client.render.blockentity.SacrificialBowlRenderer;
import com.klikli_dev.occultism.client.render.blockentity.StorageControllerGeoRenderer;
import com.klikli_dev.occultism.client.render.entity.*;
import com.klikli_dev.occultism.common.capability.FamiliarSettingsData;
import com.klikli_dev.occultism.common.container.spirit.SpiritContainer;
import com.klikli_dev.occultism.common.effect.DoubleJumpEffect;
import com.klikli_dev.occultism.common.effect.ThirdEyeEffect;
import com.klikli_dev.occultism.common.entity.spirit.demonicpartner.husband.DemonicHusbandRenderer;
import com.klikli_dev.occultism.common.entity.spirit.demonicpartner.wife.DemonicWifeRenderer;
import com.klikli_dev.occultism.integration.modonomicon.PageRenderers;
import com.klikli_dev.occultism.registry.*;
import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.blockentity.HangingSignRenderer;
import net.minecraft.client.renderer.blockentity.StandingSignRenderer;
import net.minecraft.client.renderer.entity.*;
// TODO: Port to 26.1 item property system - ItemProperties was removed
// import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EntityType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.*;
import net.neoforged.neoforge.client.extensions.common.IClientMobEffectExtensions;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.neoforged.neoforge.client.gui.VanillaGuiLayers;
import net.minecraft.client.renderer.RenderPipelines;
import net.neoforged.neoforge.client.settings.KeyConflictContext;
import net.neoforged.neoforge.common.NeoForge;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.glfw.GLFW;

import java.util.HashMap;
import java.util.Map;

@EventBusSubscriber(modid = Occultism.MODID, value = Dist.CLIENT)
public class ClientSetupEventHandler {
    private static final Identifier HAS_ENTITY_ITEM_MODEL_PROPERTY = Identifier.fromNamespaceAndPath(Occultism.MODID, "has_entity");
    private static final Identifier LINKED_ITEM_MODEL_PROPERTY = Identifier.fromNamespaceAndPath(Occultism.MODID, "linked");

    // Register a custom key category for this mod
    private static final KeyMapping.Category OCCULTISM_KEY_CATEGORY = KeyMapping.Category.register(Identifier.fromNamespaceAndPath(Occultism.MODID, "category"));

    public static final KeyMapping KEY_BACKPACK = new KeyMapping("key.occultism.backpack", InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_B, OCCULTISM_KEY_CATEGORY);
    public static final KeyMapping KEY_ENDER_BAG = new KeyMapping("key.occultism.ender_bag", InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_V, OCCULTISM_KEY_CATEGORY);
    public static final KeyMapping KEY_STORAGE_REMOTE = new KeyMapping("key.occultism.storage_remote", InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_N, OCCULTISM_KEY_CATEGORY);

    public static Map<EntityType<?>, KeyMapping> keysFamiliars;

    public static void registerConfigScreen(ModContainer modContainer) {
        modContainer.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
    }

    @SubscribeEvent
    public static void onRegisterEntityRendererLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        //Register Entity Layers
        event.registerLayerDefinition(OccultismModelLayers.FAMILIAR_BAT, BatFamiliarModel::createBodyLayer);
        event.registerLayerDefinition(OccultismModelLayers.FAMILIAR_DEER, DeerFamiliarModel::createBodyLayer);
        event.registerLayerDefinition(OccultismModelLayers.FAMILIAR_GREEDY, GreedyFamiliarModel::createBodyLayer);
        event.registerLayerDefinition(OccultismModelLayers.FAMILIAR_CTHULHU, CthulhuFamiliarModel::createBodyLayer);
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
        event.registerLayerDefinition(OccultismModelLayers.KAPOW, MummyFamiliarRenderer.KapowModel::createBodyLayer);
    }

    @SubscribeEvent
    public static void onRegisterEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        //Register Entity Renderers
        event.registerEntityRenderer(OccultismEntities.FOLIOT.get(), FoliotRenderer::new);
        event.registerEntityRenderer(OccultismEntities.DJINNI.get(), DjinniRenderer::new);
        event.registerEntityRenderer(OccultismEntities.AFRIT.get(), AfritRenderer::new);
        event.registerEntityRenderer(OccultismEntities.AFRIT_WILD.get(), AfritWildRenderer::new);
        event.registerEntityRenderer(OccultismEntities.MARID.get(), MaridRenderer::new);
        event.registerEntityRenderer(OccultismEntities.MARID_UNBOUND.get(), MaridUnboundRenderer::new);
        event.registerEntityRenderer(OccultismEntities.WONDERING_TRADER.get(), WonderingTraderRenderer::new);
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
        event.registerEntityRenderer(OccultismEntities.IESNIUM_GOLEM.get(), IesniumGolemRenderer::new);
        event.registerEntityRenderer(OccultismEntities.POSSESSED_ENDERMITE.get(), EndermiteRenderer::new);
        event.registerEntityRenderer(OccultismEntities.POSSESSED_SKELETON.get(), SkeletonRenderer::new);
        event.registerEntityRenderer(OccultismEntities.POSSESSED_ENDERMAN.get(), EndermanRenderer::new);
        event.registerEntityRenderer(OccultismEntities.POSSESSED_GHAST.get(), GhastRenderer::new);
        event.registerEntityRenderer(OccultismEntities.POSSESSED_PHANTOM.get(), PhantomRenderer::new);
        event.registerEntityRenderer(OccultismEntities.POSSESSED_WEAK_SHULKER.get(), ShulkerRenderer::new);
        event.registerEntityRenderer(OccultismEntities.POSSESSED_SHULKER.get(), ShulkerRenderer::new);
        event.registerEntityRenderer(OccultismEntities.POSSESSED_ELDER_GUARDIAN.get(), ElderGuardianRenderer::new);
        event.registerEntityRenderer(OccultismEntities.POSSESSED_WARDEN.get(), WardenRenderer::new);
        event.registerEntityRenderer(OccultismEntities.POSSESSED_HOGLIN.get(), HoglinRenderer::new);
        event.registerEntityRenderer(OccultismEntities.POSSESSED_WITCH.get(), WitchRenderer::new);
        event.registerEntityRenderer(OccultismEntities.POSSESSED_BLAZE.get(), BlazeRenderer::new);
        event.registerEntityRenderer(OccultismEntities.POSSESSED_ZOMBIE_PIGLIN.get(), PossessedZombiePiglinRenderer::new);
        event.registerEntityRenderer(OccultismEntities.POSSESSED_GUARDIAN.get(), GuardianRenderer::new);
        event.registerEntityRenderer(OccultismEntities.POSSESSED_BEE.get(), BeeRenderer::new);
        event.registerEntityRenderer(OccultismEntities.GOAT_OF_MERCY.get(), GoatRenderer::new);
        event.registerEntityRenderer(OccultismEntities.WILD_HUNT_SKELETON.get(), SkeletonRenderer::new);
        event.registerEntityRenderer(OccultismEntities.WILD_HUNT_WITHER_SKELETON.get(), WitherSkeletonRenderer::new);
        event.registerEntityRenderer(OccultismEntities.OTHERWORLD_BIRD.get(), OtherworldBirdRenderer::new);

        event.registerEntityRenderer(OccultismEntities.WILD_HORDE_HUSK.get(), HuskRenderer::new);
        event.registerEntityRenderer(OccultismEntities.WILD_HORDE_DROWNED.get(), DrownedRenderer::new);
        event.registerEntityRenderer(OccultismEntities.WILD_HORDE_CREEPER.get(), CreeperRenderer::new);
        event.registerEntityRenderer(OccultismEntities.WILD_HORDE_SILVERFISH.get(), SilverfishRenderer::new);
        event.registerEntityRenderer(OccultismEntities.POSSESSED_WEAK_BREEZE.get(), BreezeRenderer::new);
        event.registerEntityRenderer(OccultismEntities.POSSESSED_BREEZE.get(), BreezeRenderer::new);
        event.registerEntityRenderer(OccultismEntities.POSSESSED_STRONG_BREEZE.get(), BreezeRenderer::new);
        event.registerEntityRenderer(OccultismEntities.POSSESSED_EVOKER.get(), EvokerRenderer::new);

        event.registerEntityRenderer(OccultismEntities.WILD_ZOMBIE.get(), ZombieRenderer::new);
        event.registerEntityRenderer(OccultismEntities.WILD_SKELETON.get(), SkeletonRenderer::new);
        event.registerEntityRenderer(OccultismEntities.WILD_SILVERFISH.get(), SilverfishRenderer::new);
        event.registerEntityRenderer(OccultismEntities.WILD_SPIDER.get(), SpiderRenderer::new);
        event.registerEntityRenderer(OccultismEntities.WILD_BOGGED.get(), BoggedRenderer::new);
        event.registerEntityRenderer(OccultismEntities.WILD_SLIME.get(), SlimeRenderer::new);
        event.registerEntityRenderer(OccultismEntities.WILD_HUSK.get(), HuskRenderer::new);
        event.registerEntityRenderer(OccultismEntities.WILD_STRAY.get(), StrayRenderer::new);
        event.registerEntityRenderer(OccultismEntities.WILD_CAVE_SPIDER.get(), CaveSpiderRenderer::new);

        event.registerEntityRenderer(OccultismEntities.DEMONIC_WIFE.get(), DemonicWifeRenderer::new);
        event.registerEntityRenderer(OccultismEntities.DEMONIC_HUSBAND.get(), DemonicHusbandRenderer::new);


    }

    @SubscribeEvent
    public static void onRegisterKeyMappings(RegisterKeyMappingsEvent event) {
        event.register(KEY_BACKPACK);
        event.register(KEY_ENDER_BAG);
        event.register(KEY_STORAGE_REMOTE);

        keysFamiliars = new HashMap<>();
        for (EntityType<?> familiar : FamiliarSettingsData.getFamiliars()) {
            KeyMapping kb = new KeyMapping("key.occultism.familiar." + BuiltInRegistries.ENTITY_TYPE.getKey(familiar).getPath(), InputConstants.Type.KEYSYM, -1, OCCULTISM_KEY_CATEGORY);
            keysFamiliars.put(familiar, kb);
            event.register(kb);
        }
    }

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        TooltipHandler.registerNamespaceToListenTo(Occultism.MODID);

        //Register client side event handlers
        NeoForge.EVENT_BUS.register(Occultism.SELECTED_BLOCK_RENDERER);
        NeoForge.EVENT_BUS.register(Occultism.THIRD_EYE_EFFECT_RENDERER);
        NeoForge.EVENT_BUS.addListener((ScreenEvent.MouseButtonPressed.Pre e) -> StorageControllerGuiBase.onScreenMouseClickedPre(e));

        //Register Tile Entity Renderers
        BlockEntityRenderers.register(OccultismBlockEntities.STORAGE_CONTROLLER.get(), StorageControllerGeoRenderer::new);
        BlockEntityRenderers.register(OccultismBlockEntities.SACRIFICIAL_BOWL.get(), SacrificialBowlRenderer::new);
        BlockEntityRenderers.register(OccultismBlockEntities.GOLDEN_SACRIFICIAL_BOWL.get(), GoldenSacrificialBowlRenderer::new);
        // Use vanilla sign renderers for custom sign types
        BlockEntityRenderers.register(OccultismBlockEntities.OTHERPLANKS_SIGN.get(), StandingSignRenderer::new);
        BlockEntityRenderers.register(OccultismBlockEntities.OTHERPLANKS_HANGING_SIGN.get(), HangingSignRenderer::new);
        BlockEntityRenderers.register(OccultismBlockEntities.ENTITY_WORMHOLE.get(), EntityWormholeRenderer::new);

        PageRenderers.onClientSetup(event);

        Occultism.LOGGER.debug("Registered Overlays");

        Occultism.LOGGER.info("Client setup complete.");
    }

    public static void onRegisterMenuScreens(RegisterMenuScreensEvent event) {
        event.register(OccultismContainers.STORAGE_CONTROLLER.get(), StorageControllerGui::new);
        event.register(OccultismContainers.STABLE_WORMHOLE.get(), StableWormholeGui::new);
        event.register(OccultismContainers.STORAGE_REMOTE.get(), StorageRemoteGui::new);
        event.register(OccultismContainers.SPIRIT.get(), (SpiritContainer menu, net.minecraft.world.entity.player.Inventory inv, net.minecraft.network.chat.Component title) -> new SpiritGui<>(menu, inv, title));
        event.register(OccultismContainers.SPIRIT_TRANSPORTER.get(), SpiritTransporterGui::new);
        event.register(OccultismContainers.OTHERWORLD_MINER.get(), DimensionalMineshaftScreen::new);
        event.register(OccultismContainers.OTHERWORLD_BUTCHER.get(), DimensionalBattlefieldScreen::new);
        event.register(OccultismContainers.SATCHEL.get(), SatchelScreen::new);
        event.register(OccultismContainers.RITUAL_SATCHEL_T1.get(), RitualSatchelScreen::new);
        event.register(OccultismContainers.RITUAL_SATCHEL_T2.get(), RitualSatchelScreen::new);
    }

    public static void onRegisterClientExtensions(RegisterClientExtensionsEvent event) {
        event.registerMobEffect(new IClientMobEffectExtensions() {
            @Override
            public boolean renderGuiIcon(@NotNull MobEffectInstance instance, @NotNull Gui gui, @NotNull GuiGraphicsExtractor guiGraphics, int x, int y, float z, float alpha) {
                guiGraphics.blit(RenderPipelines.GUI_TEXTURED, ThirdEyeEffect.ICON, x + 3, y + 3, 0.0F, 0.0F, 18, 18, 256, 256);
                return true;
            }

        }, OccultismEffects.THIRD_EYE.get());

        event.registerMobEffect( new IClientMobEffectExtensions() {
            @Override
            public boolean renderInventoryIcon(@NotNull MobEffectInstance instance, @NotNull AbstractContainerScreen<?> screen, @NotNull GuiGraphicsExtractor guiGraphics, int x, int y, int blitOffset) {
                guiGraphics.blit(RenderPipelines.GUI_TEXTURED, DoubleJumpEffect.ICON, x + 6, y + 7, 0.0F, 0.0F, 18, 18, 256, 256);
                return false;
            }

            @Override
            public boolean renderGuiIcon(@NotNull MobEffectInstance instance, @NotNull Gui gui, @NotNull GuiGraphicsExtractor guiGraphics, int x, int y, float z, float alpha) {
                guiGraphics.blit(RenderPipelines.GUI_TEXTURED, DoubleJumpEffect.ICON, x + 3, y + 3, 0.0F, 0.0F, 18, 18, 256, 256);
                return false;
            }
        }, OccultismEffects.DOUBLE_JUMP.get());
    }

    public static void onRegisterConditionalItemModelProperties(RegisterConditionalItemModelPropertyEvent event) {
        event.register(HAS_ENTITY_ITEM_MODEL_PROPERTY, SoulGemItemPropertyGetter.MAP_CODEC);
        event.register(LINKED_ITEM_MODEL_PROPERTY, StorageRemoteItemPropertyGetter.MAP_CODEC);
    }

    public static void registerItemModelProperties(FMLClientSetupEvent event) {
        // TODO: Port remaining 26.1 item model properties.
        // Boolean item properties now use RegisterConditionalItemModelPropertyEvent.
        // Range/select properties for divination rod, true sight staff, and vitality compass still need migration.
        //
        // event.enqueueWork(() -> {
        //     ItemProperties.register(OccultismItems.FRAGILE_SOUL_GEM_ITEM.get(),
        //             Identifier.fromNamespaceAndPath(Occultism.MODID, "has_entity"), new SoulGemItemPropertyGetter());
        //     ItemProperties.register(OccultismItems.SOUL_GEM_ITEM.get(),
        //             Identifier.fromNamespaceAndPath(Occultism.MODID, "has_entity"), new SoulGemItemPropertyGetter());
        //     ItemProperties.register(OccultismItems.TRINITY_GEM_ITEM.get(),
        //             Identifier.fromNamespaceAndPath(Occultism.MODID, "has_entity"), new SoulGemItemPropertyGetter());
        //     ItemProperties.register(OccultismItems.DIVINATION_ROD.get(),
        //             Identifier.fromNamespaceAndPath(Occultism.MODID, "distance"), new DivinationRodItemPropertyGetter());
        //     ItemProperties.register(OccultismItems.TRUE_SIGHT_STAFF.get(),
        //             Identifier.fromNamespaceAndPath(Occultism.MODID, "distance"), new DivinationRodItemPropertyGetter());
        //     //ItemProperties.register(OccultismBlocks.OTHERWORLD_SAPLING_NATURAL.asItem(),
        //     //        Identifier.fromNamespaceAndPath(Occultism.MODID, "simulated"), new OtherworldBlockItemPropertyGetter());
        //     ItemProperties.register(OccultismItems.STORAGE_REMOTE.get(),
        //             Identifier.fromNamespaceAndPath(Occultism.MODID, "linked"), new StorageRemoteItemPropertyGetter());
        //     ItemProperties.register(OccultismItems.STABLE_WORMHOLE.get(),
        //             Identifier.fromNamespaceAndPath(Occultism.MODID, "linked"), new StableWormholeBlockItemPropertyGetter());
        //     ItemProperties.register(OccultismItems.STABLE_WORMHOLE_DARK.get(),
        //             Identifier.fromNamespaceAndPath(Occultism.MODID, "linked"), new StableWormholeBlockItemPropertyGetter());
        //     ItemProperties.register(OccultismItems.VITALITY_COMPASS.get(),
        //             Identifier.fromNamespaceAndPath(Occultism.MODID, "angle"), new VitalityCompassItemPropertyGetter());
        //
        //     Occultism.LOGGER.debug("Registered Item Properties");
        // });
    }

    @SubscribeEvent
    public static void onRegisterGuiOverlays(RegisterGuiLayersEvent event) {
        // TODO (1.21 -> 26.1): The GUI overlay/render pipeline API changed in Minecraft 26.1.
        // The original overlay logic using RenderSystem and PoseStack is no longer valid.
        // Disable the overlays until they are ported to the new GuiGraphicsExtractor / RenderPipeline system.
        // event.registerAboveAll(Identifier.fromNamespaceAndPath(Occultism.MODID, "third_eye"), (guiGraphics, partialTick) -> {
        //     if (Occultism.THIRD_EYE_EFFECT_RENDERER.gogglesActiveLastTick || Occultism.THIRD_EYE_EFFECT_RENDERER.thirdEyeActiveLastTick) {
        //         // Port overlay rendering to GuiGraphicsExtractor / RenderPipelines
        //     }
        // });
        // event.registerAbove(VanillaGuiLayers.HOTBAR, Identifier.fromNamespaceAndPath("occultism", "golden_sacrificial_bow_hud"), GoldenSacrificialBowlHUD.get());
    }
}
