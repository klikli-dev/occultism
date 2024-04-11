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
import com.klikli_dev.occultism.common.entity.spirit.*;
import com.klikli_dev.occultism.common.entity.spirit.demonicpartner.husband.DemonicHusband;
import com.klikli_dev.occultism.common.entity.spirit.demonicpartner.wife.DemonicWife;
import com.klikli_dev.occultism.config.OccultismClientConfig;
import com.klikli_dev.occultism.config.OccultismCommonConfig;
import com.klikli_dev.occultism.config.OccultismServerConfig;
import com.klikli_dev.occultism.integration.modonomicon.PageLoaders;
import com.klikli_dev.occultism.network.OccultismPackets;
import com.klikli_dev.occultism.registry.*;
import com.mojang.logging.LogUtils;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLDedicatedServerSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;
import software.bernie.geckolib.GeckoLib;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotTypeMessage;
import top.theillusivec4.curios.api.SlotTypePreset;

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
    public static final SelectedBlockRenderer SELECTED_BLOCK_RENDERER = new SelectedBlockRenderer();
    public static final ThirdEyeEffectRenderer THIRD_EYE_EFFECT_RENDERER = new ThirdEyeEffectRenderer();
    public static final DebugHelper DEBUG = new DebugHelper();
    public static Occultism INSTANCE;

    public Occultism() {
        INSTANCE = this;
        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, SERVER_CONFIG.spec);
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, COMMON_CONFIG.spec);
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, CLIENT_CONFIG.spec);
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        OccultismEffects.EFFECTS.register(modEventBus);
        OccultismRecipes.RECIPE_TYPES.register(modEventBus);
        OccultismRecipes.RECIPES.register(modEventBus);
        OccultismBlocks.BLOCKS.register(modEventBus);
        OccultismItems.ITEMS.register(modEventBus);
        OccultismCreativeModeTabs.CREATIVE_MODE_TABS.register(modEventBus);
        OccultismTiles.TILES.register(modEventBus);
        OccultismContainers.CONTAINERS.register(modEventBus);
        OccultismEntities.ENTITIES.register(modEventBus);
        OccultismSounds.SOUNDS.register(modEventBus);
        OccultismParticles.PARTICLES.register(modEventBus);
        OccultismFeatures.FEATURES.register(modEventBus);
        OccultismLootModifiers.LOOT_MODIFIERS.register(modEventBus);
        OccultismSensors.SENSORS.register(modEventBus);
        OccultismMemoryTypes.MEMORY_MODULE_TYPES.register(modEventBus);


        //now register the custom registries
        OccultismSpiritJobs.JOBS.register(modEventBus);
        OccultismRituals.RITUAL_FACTORIES.register(modEventBus);

        OccultismAdvancements.register();

        //register event buses
        modEventBus.addListener(OccultismCapabilities::onRegisterCapabilities);
        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(this::onEntityAttributeCreation);
        modEventBus.addListener(this::serverSetup);
        modEventBus.addListener(this::enqueueIMC);

        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.addListener(OccultismCapabilities::onPlayerClone);
        MinecraftForge.EVENT_BUS.addListener(OccultismCapabilities::onJoinWorld);

        GeckoLib.initialize();
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        OccultismPackets.registerMessages();

        event.enqueueWork(OccultismItems::registerCompostables);

        event.enqueueWork(() -> {
            BlockEntityType.CAMPFIRE.validBlocks = Stream.concat(
                    BlockEntityType.CAMPFIRE.validBlocks.stream(),
                    Stream.of(OccultismBlocks.SPIRIT_CAMPFIRE.get())
            ).collect(ImmutableSet.toImmutableSet());
        });

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
        event.put(OccultismEntities.POSSESSED_WARDEN_TYPE.get(), PossessedWardenEntity.createAttributes().build());
        event.put(OccultismEntities.POSSESSED_HOGLIN_TYPE.get(), PossessedHoglinEntity.createAttributes().build());
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

        event.put(OccultismEntities.DEMONIC_WIFE.get(), DemonicWife.createAttributes().build());
        event.put(OccultismEntities.DEMONIC_HUSBAND.get(), DemonicHusband.createAttributes().build());
    }

    private void serverSetup(final FMLDedicatedServerSetupEvent event) {
        LOGGER.info("Dedicated server setup complete.");
    }

    private void enqueueIMC(final InterModEnqueueEvent event) {
        InterModComms.sendTo(CuriosApi.MODID, SlotTypeMessage.REGISTER_TYPE, () -> SlotTypePreset.BELT.getMessageBuilder().build());
        InterModComms.sendTo(CuriosApi.MODID, SlotTypeMessage.REGISTER_TYPE, () -> SlotTypePreset.HEAD.getMessageBuilder().build());
        InterModComms.sendTo(CuriosApi.MODID, SlotTypeMessage.REGISTER_TYPE, () -> SlotTypePreset.RING.getMessageBuilder().build());
        InterModComms.sendTo(CuriosApi.MODID, SlotTypeMessage.REGISTER_TYPE, () -> SlotTypePreset.HANDS.getMessageBuilder().build());
    }
}
