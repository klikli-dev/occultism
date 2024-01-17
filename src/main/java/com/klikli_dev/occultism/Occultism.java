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

package com.klikli_dev.occultism;

import com.klikli_dev.occultism.client.render.SelectedBlockRenderer;
import com.klikli_dev.occultism.client.render.ThirdEyeEffectRenderer;
import com.klikli_dev.occultism.common.DebugHelper;
import com.klikli_dev.occultism.common.entity.familiar.*;
import com.klikli_dev.occultism.common.entity.possessed.*;
import com.klikli_dev.occultism.common.entity.spirit.*;
import com.klikli_dev.occultism.config.OccultismClientConfig;
import com.klikli_dev.occultism.config.OccultismCommonConfig;
import com.klikli_dev.occultism.config.OccultismServerConfig;
import com.klikli_dev.occultism.integration.modonomicon.PageLoaders;
import com.klikli_dev.occultism.registry.*;
import com.mojang.logging.LogUtils;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLDedicatedServerSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import org.slf4j.Logger;
import software.bernie.geckolib.GeckoLib;

@Mod(Occultism.MODID)
public class Occultism {
    public static final String MODID = "occultism";
    public static final String NAME = "Occultism";
    public static final Logger LOGGER = LogUtils.getLogger();
    public static final OccultismServerConfig SERVER_CONFIG = new OccultismServerConfig();
    public static final OccultismCommonConfig COMMON_CONFIG = new OccultismCommonConfig();
    public static final OccultismClientConfig CLIENT_CONFIG = new OccultismClientConfig();
    public static final SelectedBlockRenderer SELECTED_BLOCK_RENDERER = new SelectedBlockRenderer();
    public static final ThirdEyeEffectRenderer THIRD_EYE_EFFECT_RENDERER = new ThirdEyeEffectRenderer();
    public static final DebugHelper DEBUG = new DebugHelper();
    public static Occultism INSTANCE;

    public Occultism(IEventBus modEventBus) {
        INSTANCE = this;
        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, SERVER_CONFIG.spec);
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, COMMON_CONFIG.spec);
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, CLIENT_CONFIG.spec);

        OccultismEffects.EFFECTS.register(modEventBus);
        OccultismRecipes.RECIPE_TYPES.register(modEventBus);
        OccultismRecipes.RECIPES.register(modEventBus);
        OccultismBlocks.BLOCKS.register(modEventBus);
        OccultismItems.ITEMS.register(modEventBus);
        OccultismCreativeModeTabs.CREATIVE_MODE_TABS.register(modEventBus);
        OccultismBlockEntities.BLOCK_ENTITIES.register(modEventBus);
        OccultismContainers.CONTAINERS.register(modEventBus);
        OccultismEntities.ENTITIES.register(modEventBus);
        OccultismSounds.SOUNDS.register(modEventBus);
        OccultismParticles.PARTICLES.register(modEventBus);
        OccultismFeatures.FEATURES.register(modEventBus);
        OccultismLootModifiers.LOOT_MODIFIERS.register(modEventBus);
        OccultismSensors.SENSORS.register(modEventBus);
        OccultismMemoryTypes.MEMORY_MODULE_TYPES.register(modEventBus);
        OccultismDataStorage.ATTACHMENT_TYPES.register(modEventBus);


        //now register the custom registries
        OccultismSpiritJobs.JOBS.register(modEventBus);
        OccultismRituals.RITUAL_FACTORIES.register(modEventBus);

        OccultismAdvancements.register();

        //register event buses
        modEventBus.addListener(OccultismCapabilities::onRegisterCapabilities);
        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(this::onEntityAttributeCreation);
        modEventBus.addListener(this::serverSetup);
        modEventBus.addListener(com.klikli_dev.theurgy.network.Networking::register);

        NeoForge.EVENT_BUS.register(this);
        NeoForge.EVENT_BUS.addListener(OccultismDataStorage::onPlayerClone);
        NeoForge.EVENT_BUS.addListener(OccultismDataStorage::onJoinWorld);

        GeckoLib.initialize(modEventBus);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        OccultismItems.registerCompostables();

        PageLoaders.onCommonSetup(event);

        //Register entity attributes on single thread

        LOGGER.info("Common setup complete.");
    }

    private void onEntityAttributeCreation(final EntityAttributeCreationEvent event) {
        event.put(OccultismEntities.FOLIOT_TYPE.get(), FoliotEntity.createAttributes().build());
        event.put(OccultismEntities.DJINNI_TYPE.get(), DjinniEntity.createAttributes().build());
        event.put(OccultismEntities.AFRIT_TYPE.get(), AfritEntity.createAttributes().build());
        event.put(OccultismEntities.AFRIT_WILD_TYPE.get(), AfritWildEntity.createAttributes().build());
        event.put(OccultismEntities.MARID_TYPE.get(), MaridEntity.createAttributes().build());
        event.put(OccultismEntities.POSSESSED_ENDERMITE_TYPE.get(), PossessedEndermiteEntity.createAttributes().build());
        event.put(OccultismEntities.POSSESSED_SKELETON_TYPE.get(), PossessedSkeletonEntity.createAttributes().build());
        event.put(OccultismEntities.POSSESSED_ENDERMAN_TYPE.get(), PossessedEndermanEntity.createAttributes().build());
        event.put(OccultismEntities.POSSESSED_GHAST_TYPE.get(), PossessedGhastEntity.createAttributes().build());
        event.put(OccultismEntities.POSSESSED_PHANTOM_TYPE.get(), PossessedPhantomEntity.createAttributes().build());
        event.put(OccultismEntities.POSSESSED_WEAK_SHULKER_TYPE.get(), PossessedWeakShulkerEntity.createAttributes().build());
        event.put(OccultismEntities.POSSESSED_SHULKER_TYPE.get(), PossessedShulkerEntity.createAttributes().build());
        event.put(OccultismEntities.POSSESSED_ELDER_GUARDIAN_TYPE.get(), PossessedElderGuardianEntity.createAttributes().build());
        event.put(OccultismEntities.WILD_HUNT_SKELETON_TYPE.get(), WildHuntSkeletonEntity.createAttributes().build());
        event.put(OccultismEntities.WILD_HUNT_WITHER_SKELETON_TYPE.get(), WildHuntWitherSkeletonEntity.createAttributes().build());
        event.put(OccultismEntities.OTHERWORLD_BIRD_TYPE.get(), OtherworldBirdEntity.createAttributes().build());
        event.put(OccultismEntities.GREEDY_FAMILIAR_TYPE.get(), GreedyFamiliarEntity.createAttributes().build());
        event.put(OccultismEntities.BAT_FAMILIAR_TYPE.get(), BatFamiliarEntity.createAttributes().build());
        event.put(OccultismEntities.DEER_FAMILIAR_TYPE.get(), DeerFamiliarEntity.createAttributes().build());
        event.put(OccultismEntities.CTHULHU_FAMILIAR_TYPE.get(), CthulhuFamiliarEntity.createAttributes().build());
        event.put(OccultismEntities.DEVIL_FAMILIAR_TYPE.get(), DevilFamiliarEntity.createAttributes().build());
        event.put(OccultismEntities.DRAGON_FAMILIAR_TYPE.get(), DragonFamiliarEntity.createAttributes().build());
        event.put(OccultismEntities.BLACKSMITH_FAMILIAR_TYPE.get(), BlacksmithFamiliarEntity.createAttributes().build());
        event.put(OccultismEntities.GUARDIAN_FAMILIAR_TYPE.get(), GuardianFamiliarEntity.createAttributes().build());
        event.put(OccultismEntities.HEADLESS_FAMILIAR_TYPE.get(), HeadlessFamiliarEntity.createAttributes().build());
        event.put(OccultismEntities.CHIMERA_FAMILIAR_TYPE.get(), ChimeraFamiliarEntity.createAttributes().build());
        event.put(OccultismEntities.GOAT_FAMILIAR_TYPE.get(), FamiliarEntity.createAttributes().build());
        event.put(OccultismEntities.SHUB_NIGGURATH_FAMILIAR_TYPE.get(), FamiliarEntity.createAttributes().build());
        event.put(OccultismEntities.BEHOLDER_FAMILIAR_TYPE.get(), FamiliarEntity.createAttributes().build());
        event.put(OccultismEntities.FAIRY_FAMILIAR_TYPE.get(), FairyFamiliarEntity.createAttributes().build());
        event.put(OccultismEntities.MUMMY_FAMILIAR_TYPE.get(), MummyFamiliarEntity.createAttributes().build());
        event.put(OccultismEntities.BEAVER_FAMILIAR_TYPE.get(), BeaverFamiliarEntity.createAttributes().build());
        event.put(OccultismEntities.SHUB_NIGGURATH_SPAWN_TYPE.get(), ShubNiggurathSpawnEntity.createAttributes().build());
    }

    private void serverSetup(final FMLDedicatedServerSetupEvent event) {
        LOGGER.info("Dedicated server setup complete.");
    }
}
