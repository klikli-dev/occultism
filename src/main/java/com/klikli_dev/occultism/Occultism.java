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

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import com.klikli_dev.occultism.client.render.SelectedBlockRenderer;
import com.klikli_dev.occultism.client.render.ThirdEyeEffectRenderer;
import com.klikli_dev.occultism.common.DebugHelper;
import com.klikli_dev.occultism.common.entity.familiar.*;
import com.klikli_dev.occultism.common.entity.possessed.*;
import com.klikli_dev.occultism.common.entity.possessed.horde.*;
import com.klikli_dev.occultism.common.entity.spirit.*;
import com.klikli_dev.occultism.common.entity.spirit.demonicpartner.husband.DemonicHusband;
import com.klikli_dev.occultism.common.entity.spirit.demonicpartner.wife.DemonicWife;
import com.klikli_dev.occultism.config.OccultismClientConfig;
import com.klikli_dev.occultism.config.OccultismCommonConfig;
import com.klikli_dev.occultism.config.OccultismServerConfig;
import com.klikli_dev.occultism.config.OccultismStartupConfig;
import com.klikli_dev.occultism.handlers.ClientSetupEventHandler;
import com.klikli_dev.occultism.integration.modonomicon.PageLoaders;
import com.klikli_dev.occultism.network.Networking;
import com.klikli_dev.occultism.registry.*;
import com.klikli_dev.theurgy.Theurgy;
import com.klikli_dev.theurgy.registry.DataComponentRegistry;
import com.klikli_dev.theurgy.registry.ParticleRegistry;
import com.klikli_dev.theurgy.registry.TheurgyRegistries;
import com.mojang.logging.LogUtils;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Slime;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLDedicatedServerSetupEvent;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import org.slf4j.Logger;
import software.bernie.geckolib.GeckoLib;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

@Mod(Occultism.MODID)
public class Occultism {
    public static final String MODID = "occultism";
    public static final String NAME = "Occultism";
    public static final Logger LOGGER = LogUtils.getLogger();
    public static final OccultismServerConfig SERVER_CONFIG = new OccultismServerConfig();
    public static final OccultismCommonConfig COMMON_CONFIG = new OccultismCommonConfig();
    public static final OccultismClientConfig CLIENT_CONFIG = new OccultismClientConfig();
    public static final OccultismStartupConfig STARTUP_CONFIG = new OccultismStartupConfig();
    public static final SelectedBlockRenderer SELECTED_BLOCK_RENDERER = new SelectedBlockRenderer();
    public static final ThirdEyeEffectRenderer THIRD_EYE_EFFECT_RENDERER = new ThirdEyeEffectRenderer();
    public static final DebugHelper DEBUG = new DebugHelper();
    public static Occultism INSTANCE;

    public Occultism(IEventBus modEventBus, ModContainer modContainer) {
        INSTANCE = this;
        modContainer.registerConfig(ModConfig.Type.SERVER, SERVER_CONFIG.spec);
        modContainer.registerConfig(ModConfig.Type.COMMON, COMMON_CONFIG.spec);
        modContainer.registerConfig(ModConfig.Type.CLIENT, CLIENT_CONFIG.spec);
        modContainer.registerConfig(ModConfig.Type.STARTUP, STARTUP_CONFIG.spec);

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
        OccultismAdvancements.TRIGGER_TYPES.register(modEventBus);
        OccultismDataComponents.DATA_COMPONENTS.register(modEventBus);
        OccultismRecipeResults.RECIPE_RESULT_TYPES.register(modEventBus);
        OccultismConditionCodecs.CONDITION_CODECS.register(modEventBus);

        //now register the custom registries
        OccultismSpiritJobs.JOBS.register(modEventBus);
        OccultismRituals.RITUAL_FACTORIES.register(modEventBus);

        //register event buses
        modEventBus.addListener(OccultismRegistries::onRegisterRegistries);
        modEventBus.addListener(EventPriority.HIGHEST, OccultismCapabilities::onRegisterCapabilities);
        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(this::onEntityAttributeCreation);
        modEventBus.addListener(this::serverSetup);
        modEventBus.addListener(Networking::register);

        NeoForge.EVENT_BUS.addListener(OccultismDataStorage::onPlayerClone);
        NeoForge.EVENT_BUS.addListener(OccultismDataStorage::onJoinWorld);

        if (FMLEnvironment.dist == Dist.CLIENT) {
            modEventBus.addListener(ClientSetupEventHandler::onRegisterMenuScreens);
            modEventBus.addListener(ClientSetupEventHandler::onRegisterClientExtensions);
            ClientSetupEventHandler.registerConfigScreen(modContainer);
        }

    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        PageLoaders.onCommonSetup(event);

        event.enqueueWork(() -> {
            BlockEntityType.CAMPFIRE.validBlocks = Stream.concat(
                    BlockEntityType.CAMPFIRE.validBlocks.stream(),
                    Stream.of(OccultismBlocks.SPIRIT_CAMPFIRE.get())
            ).collect(ImmutableSet.toImmutableSet());
        });

        //Register entity attributes on single thread

        LOGGER.info("Common setup complete.");
    }

    private void onEntityAttributeCreation(final EntityAttributeCreationEvent event) {
        event.put(OccultismEntities.FOLIOT_TYPE.get(), FoliotEntity.createAttributes().build());
        event.put(OccultismEntities.DJINNI_TYPE.get(), DjinniEntity.createAttributes().build());
        event.put(OccultismEntities.AFRIT_TYPE.get(), AfritEntity.createAttributes().build());
        event.put(OccultismEntities.AFRIT_WILD_TYPE.get(), AfritWildEntity.createAttributes().build());
        event.put(OccultismEntities.MARID_TYPE.get(), MaridEntity.createAttributes().build());
        event.put(OccultismEntities.MARID_UNBOUND_TYPE.get(), MaridUnboundEntity.createAttributes().build());
        event.put(OccultismEntities.POSSESSED_ENDERMITE_TYPE.get(), PossessedEndermiteEntity.createAttributes().build());
        event.put(OccultismEntities.POSSESSED_SKELETON_TYPE.get(), PossessedSkeletonEntity.createAttributes().build());
        event.put(OccultismEntities.POSSESSED_ENDERMAN_TYPE.get(), PossessedEndermanEntity.createAttributes().build());
        event.put(OccultismEntities.POSSESSED_GHAST_TYPE.get(), PossessedGhastEntity.createAttributes().build());
        event.put(OccultismEntities.POSSESSED_PHANTOM_TYPE.get(), PossessedPhantomEntity.createAttributes().build());
        event.put(OccultismEntities.POSSESSED_WEAK_SHULKER_TYPE.get(), PossessedWeakShulkerEntity.createAttributes().build());
        event.put(OccultismEntities.POSSESSED_SHULKER_TYPE.get(), PossessedShulkerEntity.createAttributes().build());
        event.put(OccultismEntities.POSSESSED_ELDER_GUARDIAN_TYPE.get(), PossessedElderGuardianEntity.createAttributes().build());
        event.put(OccultismEntities.POSSESSED_WARDEN_TYPE.get(), PossessedWardenEntity.createAttributes().build());
        event.put(OccultismEntities.POSSESSED_HOGLIN_TYPE.get(), PossessedHoglinEntity.createAttributes().build());
        event.put(OccultismEntities.POSSESSED_WITCH_TYPE.get(), PossessedWitchEntity.createAttributes().build());
        event.put(OccultismEntities.POSSESSED_ZOMBIE_PIGLIN_TYPE.get(), PossessedZombiePiglinEntity.createAttributes().build());
        event.put(OccultismEntities.POSSESSED_BEE_TYPE.get(), PossessedBeeEntity.createAttributes().build());
        event.put(OccultismEntities.GOAT_OF_MERCY_TYPE.get(), GoatOfMercyEntity.createAttributes().build());
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
        event.put(OccultismEntities.IESNIUM_GOLEM_TYPE.get(), IesniumGolemEntity.createAttributes().build());

        event.put(OccultismEntities.WILD_HORDE_HUSK_TYPE.get(), WildHordeHuskEntity.createAttributes().build());
        event.put(OccultismEntities.WILD_HORDE_DROWNED_TYPE.get(), WildHordeDrownedEntity.createAttributes().build());
        event.put(OccultismEntities.WILD_HORDE_CREEPER_TYPE.get(), WildHordeCreeperEntity.createAttributes().build());
        event.put(OccultismEntities.WILD_HORDE_SILVERFISH_TYPE.get(), WildHordeSilverfishEntity.createAttributes().build());
        event.put(OccultismEntities.POSSESSED_WEAK_BREEZE_TYPE.get(), PossessedWeakBreezeEntity.createAttributes().build());
        event.put(OccultismEntities.POSSESSED_BREEZE_TYPE.get(), PossessedBreezeEntity.createAttributes().build());
        event.put(OccultismEntities.POSSESSED_STRONG_BREEZE_TYPE.get(), PossessedStrongBreezeEntity.createAttributes().build());
        event.put(OccultismEntities.POSSESSED_EVOKER_TYPE.get(), PossessedEvokerEntity.createAttributes().build());
        event.put(OccultismEntities.WILD_ZOMBIE_TYPE.get(), WildZombieEntity.createAttributes().build());
        event.put(OccultismEntities.WILD_SKELETON_TYPE.get(), WildSkeletonEntity.createAttributes().build());
        event.put(OccultismEntities.WILD_SILVERFISH.get(), WildSilverfishEntity.createAttributes().build());
        event.put(OccultismEntities.WILD_SPIDER_TYPE.get(), WildSpiderEntity.createAttributes().build());
        event.put(OccultismEntities.WILD_BOGGED_TYPE.get(), WildBoggedEntity.createAttributes().build());
        event.put(OccultismEntities.WILD_SLIME_TYPE.get(), WildSlimeEntity.createAttributes().build());
        event.put(OccultismEntities.WILD_HUSK_TYPE.get(), WildHuskEntity.createAttributes().build());
        event.put(OccultismEntities.WILD_STRAY_TYPE.get(), WildStrayEntity.createAttributes().build());
        event.put(OccultismEntities.WILD_CAVE_SPIDER_TYPE.get(), WildCaveSpiderEntity.createAttributes().build());


        event.put(OccultismEntities.DEMONIC_WIFE.get(), DemonicWife.createAttributes().build());
        event.put(OccultismEntities.DEMONIC_HUSBAND.get(), DemonicHusband.createAttributes().build());
    }

    private void serverSetup(final FMLDedicatedServerSetupEvent event) {
        LOGGER.info("Dedicated server setup complete.");
    }
}
