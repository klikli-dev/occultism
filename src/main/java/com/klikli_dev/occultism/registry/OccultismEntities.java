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

package com.klikli_dev.occultism.registry;

import com.klikli_dev.occultism.Occultism;
import com.klikli_dev.occultism.common.entity.familiar.*;
import com.klikli_dev.occultism.common.entity.possessed.*;
import com.klikli_dev.occultism.common.entity.possessed.horde.*;
import com.klikli_dev.occultism.common.entity.spirit.*;
import com.klikli_dev.occultism.common.entity.spirit.demonicpartner.husband.DemonicHusband;
import com.klikli_dev.occultism.common.entity.spirit.demonicpartner.wife.DemonicWife;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.common.util.Lazy;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class OccultismEntities {

    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(BuiltInRegistries.ENTITY_TYPE,
            Occultism.MODID);

    public static final Lazy<EntityType<FoliotEntity>> FOLIOT_TYPE =
            Lazy.of(() -> EntityType.Builder.of(FoliotEntity::new, MobCategory.CREATURE)
                    .sized(0.5f, 1.2f)
                    .clientTrackingRange(8)
                    .build(ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "foliot").toString()));
    public static final Lazy<EntityType<DjinniEntity>> DJINNI_TYPE =
            Lazy.of(() -> EntityType.Builder.of(DjinniEntity::new, MobCategory.CREATURE)
                    .sized(1.0f, 2.4f)
                    .clientTrackingRange(8)
                    .build(ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "djinni").toString()));
    public static final Lazy<EntityType<AfritEntity>> AFRIT_TYPE =
            Lazy.of(() -> EntityType.Builder.of((EntityType<AfritEntity> t, Level l) -> new AfritEntity(t, l), MobCategory.CREATURE)
                    .fireImmune()
                    .sized(1.0f, 2.4f)
                    .clientTrackingRange(8)
                    .build(ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "afrit").toString()));
    public static final Lazy<EntityType<AfritWildEntity>> AFRIT_WILD_TYPE =
            Lazy.of(() -> EntityType.Builder.of((EntityType<AfritWildEntity> t, Level l) -> new AfritWildEntity(t, l), MobCategory.CREATURE)
                    .fireImmune()
                    .sized(1.0f, 2.4f)
                    .clientTrackingRange(8)
                    .build(ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "afrit_wild").toString()));
    public static final Lazy<EntityType<MaridEntity>> MARID_TYPE =
            Lazy.of(() -> EntityType.Builder.of(MaridEntity::new, MobCategory.CREATURE)
                    .sized(1.0f, 2.4f)
                    .clientTrackingRange(8)
                    .build(ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "marid").toString()));

    public static final Lazy<EntityType<MaridUnboundEntity>> MARID_UNBOUND_TYPE =
            Lazy.of(() -> EntityType.Builder.of((EntityType<MaridUnboundEntity> t, Level l) -> new MaridUnboundEntity(t, l), MobCategory.CREATURE)
                    .sized(1.0f, 2.4f)
                    .clientTrackingRange(16)
                    .build(ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "marid_unbound").toString()));

    public static final Lazy<EntityType<PossessedEndermiteEntity>> POSSESSED_ENDERMITE_TYPE =
            Lazy.of(() -> EntityType.Builder.of(PossessedEndermiteEntity::new, MobCategory.MONSTER)
                    .sized(0.4F, 0.3F)
                    .clientTrackingRange(8)
                    .build(ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "possessed_endermite").toString()));
    public static final Lazy<EntityType<PossessedSkeletonEntity>> POSSESSED_SKELETON_TYPE =
            Lazy.of(() -> EntityType.Builder.of(PossessedSkeletonEntity::new, MobCategory.MONSTER)
                    .sized(0.6F, 1.99F)
                    .clientTrackingRange(8)
                    .build(ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "possessed_skeleton").toString()));
    public static final Lazy<EntityType<PossessedEndermanEntity>> POSSESSED_ENDERMAN_TYPE =
            Lazy.of(() -> EntityType.Builder.of(PossessedEndermanEntity::new, MobCategory.MONSTER)
                    .sized(0.6F, 2.9F)
                    .clientTrackingRange(8)
                    .build(ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "possessed_enderman").toString()));
    public static final Lazy<EntityType<PossessedGhastEntity>> POSSESSED_GHAST_TYPE =
            Lazy.of(() -> EntityType.Builder.of(PossessedGhastEntity::new, MobCategory.MONSTER)
                    .fireImmune()
                    .sized(4.0F, 4.0F)
                    .clientTrackingRange(10)
                    .build(ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "possessed_ghast").toString()));
    public static final Lazy<EntityType<PossessedPhantomEntity>> POSSESSED_PHANTOM_TYPE =
            Lazy.of(() -> EntityType.Builder.of(PossessedPhantomEntity::new, MobCategory.MONSTER)
                    .fireImmune()
                    .sized(0.9F, 0.5F)
                    .clientTrackingRange(16)
                    .build(ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "possessed_phantom").toString()));
    public static final Lazy<EntityType<PossessedWeakShulkerEntity>> POSSESSED_WEAK_SHULKER_TYPE =
            Lazy.of(() -> EntityType.Builder.of(PossessedWeakShulkerEntity::new, MobCategory.MONSTER)
                    .fireImmune()
                    .sized(1F, 1F)
                    .clientTrackingRange(8)
                    .build(ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "possessed_weak_shulker").toString()));
    public static final Lazy<EntityType<PossessedShulkerEntity>> POSSESSED_SHULKER_TYPE =
            Lazy.of(() -> EntityType.Builder.of(PossessedShulkerEntity::new, MobCategory.MONSTER)
                    .fireImmune()
                    .sized(1F, 1F)
                    .clientTrackingRange(8)
                    .build(ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "possessed_shulker").toString()));
    public static final Lazy<EntityType<PossessedElderGuardianEntity>> POSSESSED_ELDER_GUARDIAN_TYPE =
            Lazy.of(() -> EntityType.Builder.of(PossessedElderGuardianEntity::new, MobCategory.MONSTER)
                    .fireImmune()
                    .sized(2F, 2F)
                    .clientTrackingRange(24)
                    .build(ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "possessed_elder_guardian").toString()));
    public static final Lazy<EntityType<PossessedWardenEntity>> POSSESSED_WARDEN_TYPE =
            Lazy.of(() -> EntityType.Builder.of(PossessedWardenEntity::new, MobCategory.MONSTER)
                    .sized(0.9F, 2.9F)
                    .fireImmune()
                    .clientTrackingRange(48)
                    .build(ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "possessed_warden").toString()));
    public static final Lazy<EntityType<PossessedHoglinEntity>> POSSESSED_HOGLIN_TYPE =
            Lazy.of(() -> EntityType.Builder.of(PossessedHoglinEntity::new, MobCategory.MONSTER)
                    .sized(1.88F, 1.88F)
                    .fireImmune()
                    .clientTrackingRange(24)
                    .build(ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "possessed_hoglin").toString()));
    public static final Lazy<EntityType<PossessedWitchEntity>> POSSESSED_WITCH_TYPE =
            Lazy.of(() -> EntityType.Builder.of(PossessedWitchEntity::new, MobCategory.MONSTER)
                    .sized(0.9F, 1.95F)
                    .fireImmune()
                    .clientTrackingRange(16)
                    .build(ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "possessed_witch").toString()));
    public static final Lazy<EntityType<PossessedZombiePiglinEntity>> POSSESSED_ZOMBIE_PIGLIN_TYPE =
            Lazy.of(() -> EntityType.Builder.of(PossessedZombiePiglinEntity::new, MobCategory.MONSTER)
                    .sized(0.6F, 1.95F)
                    .fireImmune()
                    .clientTrackingRange(32)
                    .build(ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "possessed_zombie_piglin").toString()));
    public static final Lazy<EntityType<PossessedBeeEntity>> POSSESSED_BEE_TYPE =
            Lazy.of(() -> EntityType.Builder.of(PossessedBeeEntity::new, MobCategory.MONSTER)
                    .sized(0.7F, 0.6F)
                    .clientTrackingRange(48)
                    .build(ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "possessed_bee").toString()));
    public static final Lazy<EntityType<GoatOfMercyEntity>> GOAT_OF_MERCY_TYPE =
            Lazy.of(() -> EntityType.Builder.of(GoatOfMercyEntity::new, MobCategory.CREATURE)
                    .sized(0.9F, 1.3F)
                    .build(ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "mercy_goat").toString()));

    public static final Lazy<EntityType<WildHuntSkeletonEntity>> WILD_HUNT_SKELETON_TYPE =
            Lazy.of(() -> EntityType.Builder.of(WildHuntSkeletonEntity::new, MobCategory.MONSTER)
                    .sized(0.6F, 2.9F)
                    .clientTrackingRange(8)
                    .build(ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "wild_hunt_skeleton").toString()));
    public static final Lazy<EntityType<WildHuntWitherSkeletonEntity>> WILD_HUNT_WITHER_SKELETON_TYPE =
            Lazy.of(() -> EntityType.Builder.of(WildHuntWitherSkeletonEntity::new, MobCategory.MONSTER)
                    .sized(0.6F, 2.9F)
                    .clientTrackingRange(8)
                    .build(ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "wild_hunt_wither_skeleton").toString()));
    public static final Lazy<EntityType<OtherworldBirdEntity>> OTHERWORLD_BIRD_TYPE =
            Lazy.of(() -> EntityType.Builder.of(OtherworldBirdEntity::new, MobCategory.CREATURE)
                    .sized(0.5F, 0.9F)
                    .clientTrackingRange(8)
                    .build(ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "otherworld_bird").toString()));
    public static final Lazy<EntityType<WildHordeHuskEntity>> WILD_HORDE_HUSK_TYPE =
            Lazy.of(() -> EntityType.Builder.of(WildHordeHuskEntity::new, MobCategory.MONSTER)
                    .sized(0.6F, 1.95F)
                    .clientTrackingRange(8)
                    .build(ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "wild_horde_husk").toString()));
    public static final Lazy<EntityType<WildHordeDrownedEntity>> WILD_HORDE_DROWNED_TYPE =
            Lazy.of(() -> EntityType.Builder.of(WildHordeDrownedEntity::new, MobCategory.MONSTER)
                    .sized(0.6F, 1.95F)
                    .clientTrackingRange(8)
                    .build(ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "wild_horde_drowned").toString()));
    public static final Lazy<EntityType<WildHordeCreeperEntity>> WILD_HORDE_CREEPER_TYPE =
            Lazy.of(() -> EntityType.Builder.of(WildHordeCreeperEntity::new, MobCategory.MONSTER)
                    .sized(0.6F, 1.7F)
                    .clientTrackingRange(8)
                    .build(ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "wild_horde_creeper").toString()));
    public static final Lazy<EntityType<WildHordeSilverfishEntity>> WILD_HORDE_SILVERFISH_TYPE =
            Lazy.of(() -> EntityType.Builder.of(WildHordeSilverfishEntity::new, MobCategory.MONSTER)
                    .sized(0.4F, 0.3F)
                    .clientTrackingRange(16)
                    .build(ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "wild_horde_silverfish").toString()));
    public static final Lazy<EntityType<PossessedWeakBreezeEntity>> POSSESSED_WEAK_BREEZE_TYPE =
            Lazy.of(() -> EntityType.Builder.of(PossessedWeakBreezeEntity::new, MobCategory.MONSTER)
                    .sized(0.6F, 1.77F)
                    .clientTrackingRange(32)
                    .build(ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "possessed_weak_breeze").toString()));
    public static final Lazy<EntityType<PossessedBreezeEntity>> POSSESSED_BREEZE_TYPE =
            Lazy.of(() -> EntityType.Builder.of(PossessedBreezeEntity::new, MobCategory.MONSTER)
                    .fireImmune()
                    .sized(0.6F, 1.77F)
                    .clientTrackingRange(32)
                    .build(ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "possessed_breeze").toString()));
    public static final Lazy<EntityType<PossessedStrongBreezeEntity>> POSSESSED_STRONG_BREEZE_TYPE =
            Lazy.of(() -> EntityType.Builder.of(PossessedStrongBreezeEntity::new, MobCategory.MONSTER)
                    .fireImmune()
                    .sized(0.6F, 1.77F)
                    .clientTrackingRange(32)
                    .build(ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "possessed_strong_breeze").toString()));
    public static final Lazy<EntityType<WildZombieEntity>> WILD_ZOMBIE_TYPE =
            Lazy.of(() -> EntityType.Builder.of(WildZombieEntity::new, MobCategory.MONSTER)
                    .sized(0.6F, 1.95F)
                    .clientTrackingRange(32)
                    .build(ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "wild_zombie").toString()));
    public static final Lazy<EntityType<WildSkeletonEntity>> WILD_SKELETON_TYPE =
            Lazy.of(() -> EntityType.Builder.of(WildSkeletonEntity::new, MobCategory.MONSTER)
                    .sized(0.6F, 1.99F)
                    .clientTrackingRange(32)
                    .build(ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "wild_skeleton").toString()));
    public static final Lazy<EntityType<WildSilverfishEntity>> WILD_SILVERFISH_TYPE =
            Lazy.of(() -> EntityType.Builder.of(WildSilverfishEntity::new, MobCategory.MONSTER)
                    .sized(0.4F, 0.3F)
                    .clientTrackingRange(32)
                    .build(ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "wild_silverfish").toString()));
    public static final Lazy<EntityType<WildSpiderEntity>> WILD_SPIDER_TYPE =
            Lazy.of(() -> EntityType.Builder.of(WildSpiderEntity::new, MobCategory.MONSTER)
                    .sized(0.9F, 1.4F)
                    .clientTrackingRange(32)
                    .build(ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "wild_spider").toString()));
    public static final Lazy<EntityType<WildBoggedEntity>> WILD_BOGGED_TYPE =
            Lazy.of(() -> EntityType.Builder.of(WildBoggedEntity::new, MobCategory.MONSTER)
                    .sized(0.6F, 1.99F)
                    .clientTrackingRange(32)
                    .build(ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "wild_bogged").toString()));
    public static final Lazy<EntityType<WildSlimeEntity>> WILD_SLIME_TYPE =
            Lazy.of(() -> EntityType.Builder.of(WildSlimeEntity::new, MobCategory.MONSTER)
                    .sized(0.5F, 0.5F)
                    .clientTrackingRange(32)
                    .build(ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "wild_slime").toString()));
    public static final Lazy<EntityType<WildHuskEntity>> WILD_HUSK_TYPE =
            Lazy.of(() -> EntityType.Builder.of(WildHuskEntity::new, MobCategory.MONSTER)
                    .sized(0.6F, 1.95F)
                    .clientTrackingRange(32)
                    .build(ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "wild_husk").toString()));
    public static final Lazy<EntityType<WildStrayEntity>> WILD_STRAY_TYPE =
            Lazy.of(() -> EntityType.Builder.of(WildStrayEntity::new, MobCategory.MONSTER)
                    .sized(0.6F, 1.99F)
                    .clientTrackingRange(32)
                    .build(ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "wild_stray").toString()));
    public static final Lazy<EntityType<WildCaveSpiderEntity>> WILD_CAVE_SPIDER_TYPE =
            Lazy.of(() -> EntityType.Builder.of(WildCaveSpiderEntity::new, MobCategory.MONSTER)
                    .sized(0.5F, 0.7F)
                    .clientTrackingRange(32)
                    .build(ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "wild_cave_spider").toString()));
    public static final Lazy<EntityType<PossessedEvokerEntity>> POSSESSED_EVOKER_TYPE =
            Lazy.of(() -> EntityType.Builder.of(PossessedEvokerEntity::new, MobCategory.MONSTER)
                    .sized(0.6F, 1.95F)
                    .clientTrackingRange(32)
                    .build(ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "possessed_evoker").toString()));
    public static final Lazy<EntityType<GreedyFamiliarEntity>> GREEDY_FAMILIAR_TYPE =
            Lazy.of(() -> EntityType.Builder.of(GreedyFamiliarEntity::new, MobCategory.CREATURE)
                    .sized(0.5F, 0.9F)
                    .clientTrackingRange(8)
                    .build(ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "greedy_familiar").toString()));
    public static final Lazy<EntityType<BatFamiliarEntity>> BAT_FAMILIAR_TYPE =
            Lazy.of(() -> EntityType.Builder.of(BatFamiliarEntity::new, MobCategory.CREATURE)
                    .sized(0.5F, 0.9F)
                    .clientTrackingRange(8)
                    .build(ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "bat_familiar").toString()));
    public static final Lazy<EntityType<DeerFamiliarEntity>> DEER_FAMILIAR_TYPE =
            Lazy.of(() -> EntityType.Builder.of(DeerFamiliarEntity::new, MobCategory.CREATURE)
                    .sized(0.6F, 1.0F)
                    .clientTrackingRange(8)
                    .build(ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "deer_familiar").toString()));

    public static final Lazy<EntityType<CthulhuFamiliarEntity>> CTHULHU_FAMILIAR_TYPE =
            Lazy.of(() -> EntityType.Builder.of(CthulhuFamiliarEntity::new, MobCategory.CREATURE)
                    .sized(0.6F, 1.0F)
                    .clientTrackingRange(8)
                    .build(ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "cthulhu_familiar").toString()));

    public static final Lazy<EntityType<DevilFamiliarEntity>> DEVIL_FAMILIAR_TYPE =
            Lazy.of(() -> EntityType.Builder.of(DevilFamiliarEntity::new, MobCategory.CREATURE)
                    .sized(0.8F, 1.8F)
                    .fireImmune()
                    .clientTrackingRange(8)
                    .build(ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "devil_familiar").toString()));
    public static final Lazy<EntityType<DragonFamiliarEntity>> DRAGON_FAMILIAR_TYPE =
            Lazy.of(() -> EntityType.Builder.of(DragonFamiliarEntity::new, MobCategory.CREATURE)
                    .sized(1F, 0.8F)
                    .clientTrackingRange(8)
                    .fireImmune()
                    .build(ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "dragon_familiar").toString()));
    public static final Lazy<EntityType<BlacksmithFamiliarEntity>> BLACKSMITH_FAMILIAR_TYPE =
            Lazy.of(() -> EntityType.Builder.of(BlacksmithFamiliarEntity::new, MobCategory.CREATURE)
                    .sized(0.65F, 1F)
                    .clientTrackingRange(8)
                    .build(ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "blacksmith_familiar").toString()));
    public static final Lazy<EntityType<GuardianFamiliarEntity>> GUARDIAN_FAMILIAR_TYPE =
            Lazy.of(() -> EntityType.Builder.of(GuardianFamiliarEntity::new, MobCategory.CREATURE)
                    .sized(0.8F, 1.5F)
                    .clientTrackingRange(8)
                    .fireImmune()
                    .build(ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "guardian_familiar").toString()));

    public static final Lazy<EntityType<HeadlessFamiliarEntity>> HEADLESS_FAMILIAR_TYPE =
            Lazy.of(() -> EntityType.Builder.of(HeadlessFamiliarEntity::new, MobCategory.CREATURE)
                    .sized(0.7F, 1.1F)
                    .clientTrackingRange(8)
                    .build(ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "headless_familiar").toString()));

    public static final Lazy<EntityType<ChimeraFamiliarEntity>> CHIMERA_FAMILIAR_TYPE =
            Lazy.of(() -> EntityType.Builder.of(ChimeraFamiliarEntity::new, MobCategory.CREATURE)
                    .sized(0.85F, 1.05F)
                    .clientTrackingRange(8)
                    .build(ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "chimera_familiar").toString()));
    public static final Lazy<EntityType<GoatFamiliarEntity>> GOAT_FAMILIAR_TYPE =
            Lazy.of(() -> EntityType.Builder.<GoatFamiliarEntity>of(GoatFamiliarEntity::new, MobCategory.CREATURE)
                    .sized(0.7F, 0.8F)
                    .clientTrackingRange(8)
                    .build(ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "goat_familiar").toString()));
    public static final Lazy<EntityType<ShubNiggurathFamiliarEntity>> SHUB_NIGGURATH_FAMILIAR_TYPE =
            Lazy.of(() -> EntityType.Builder.<ShubNiggurathFamiliarEntity>of(ShubNiggurathFamiliarEntity::new, MobCategory.CREATURE)
                    .sized(0.7F, 0.8F)
                    .clientTrackingRange(8)
                    .build(ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "shub_niggurath_familiar").toString()));
    public static final Lazy<EntityType<BeholderFamiliarEntity>> BEHOLDER_FAMILIAR_TYPE =
            Lazy.of(() -> EntityType.Builder.of(BeholderFamiliarEntity::new, MobCategory.CREATURE)
                    .sized(1.6F, 1.5F)
                    .clientTrackingRange(8)
                    .build(ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "beholder_familiar").toString()));

    public static final Lazy<EntityType<FairyFamiliarEntity>> FAIRY_FAMILIAR_TYPE =
            Lazy.of(() -> EntityType.Builder.of(FairyFamiliarEntity::new, MobCategory.CREATURE)
                    .sized(0.6F, 1F)
                    .clientTrackingRange(8)
                    .fireImmune()
                    .build(ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "fairy_familiar").toString()));

    public static final Lazy<EntityType<MummyFamiliarEntity>> MUMMY_FAMILIAR_TYPE =
            Lazy.of(() -> EntityType.Builder.of(MummyFamiliarEntity::new, MobCategory.CREATURE)
                    .sized(0.6F, 1.2F)
                    .clientTrackingRange(8)
                    .build(ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "mummy_familiar").toString()));

    public static final Lazy<EntityType<BeaverFamiliarEntity>> BEAVER_FAMILIAR_TYPE =
            Lazy.of(() -> EntityType.Builder.of(BeaverFamiliarEntity::new, MobCategory.CREATURE)
                    .sized(0.7F, 0.6F)
                    .clientTrackingRange(8)
                    .build(ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "beaver_familiar").toString()));

    public static final Lazy<EntityType<ThrownSwordEntity>> THROWN_SWORD_TYPE =
            Lazy.of(() -> EntityType.Builder.of(ThrownSwordEntity::new, MobCategory.MISC)
                    .sized(0.5F, 0.5F)
                    .clientTrackingRange(8)
                    .build(ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "thrown_sword").toString()));
    public static final Lazy<EntityType<ShubNiggurathSpawnEntity>> SHUB_NIGGURATH_SPAWN_TYPE =
            Lazy.of(() -> EntityType.Builder.<ShubNiggurathSpawnEntity>of(ShubNiggurathSpawnEntity::new, MobCategory.CREATURE)
                    .sized(0.6F, 0.6F)
                    .clientTrackingRange(8)
                    .build(ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "shub_niggurath_spawn").toString()));

    public static final Lazy<EntityType<IesniumGolemEntity>> IESNIUM_GOLEM_TYPE =
            Lazy.of(() -> EntityType.Builder.of(IesniumGolemEntity::new, MobCategory.MISC)
                    .sized(1.4F, 2.7F)
                    .clientTrackingRange(24)
                    .build(ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "iesnium_golem").toString()));

    public static final Supplier<EntityType<FoliotEntity>> FOLIOT = ENTITIES.register("foliot", FOLIOT_TYPE::get);
    public static final Supplier<EntityType<DjinniEntity>> DJINNI = ENTITIES.register("djinni", DJINNI_TYPE::get);
    public static final Supplier<EntityType<AfritEntity>> AFRIT = ENTITIES.register("afrit", AFRIT_TYPE::get);
    public static final Supplier<EntityType<AfritWildEntity>> AFRIT_WILD = ENTITIES.register("afrit_wild", AFRIT_WILD_TYPE::get);
    public static final Supplier<EntityType<MaridEntity>> MARID = ENTITIES.register("marid", MARID_TYPE::get);
    public static final Supplier<EntityType<MaridUnboundEntity>> MARID_UNBOUND = ENTITIES.register("marid_unbound", MARID_UNBOUND_TYPE::get);


    public static final Supplier<EntityType<PossessedEndermiteEntity>> POSSESSED_ENDERMITE =
            ENTITIES.register("possessed_endermite", POSSESSED_ENDERMITE_TYPE::get);
    public static final Supplier<EntityType<PossessedSkeletonEntity>> POSSESSED_SKELETON =
            ENTITIES.register("possessed_skeleton", POSSESSED_SKELETON_TYPE::get);
    public static final Supplier<EntityType<PossessedEndermanEntity>> POSSESSED_ENDERMAN =
            ENTITIES.register("possessed_enderman", POSSESSED_ENDERMAN_TYPE::get);
    public static final Supplier<EntityType<PossessedGhastEntity>> POSSESSED_GHAST =
            ENTITIES.register("possessed_ghast", POSSESSED_GHAST_TYPE::get);
    public static final Supplier<EntityType<PossessedPhantomEntity>> POSSESSED_PHANTOM =
            ENTITIES.register("possessed_phantom", POSSESSED_PHANTOM_TYPE::get);
    public static final Supplier<EntityType<PossessedWeakShulkerEntity>> POSSESSED_WEAK_SHULKER =
            ENTITIES.register("possessed_weak_shulker", POSSESSED_WEAK_SHULKER_TYPE::get);
    public static final Supplier<EntityType<PossessedShulkerEntity>> POSSESSED_SHULKER =
            ENTITIES.register("possessed_shulker", POSSESSED_SHULKER_TYPE::get);
    public static final Supplier<EntityType<PossessedElderGuardianEntity>> POSSESSED_ELDER_GUARDIAN =
            ENTITIES.register("possessed_elder_guardian", POSSESSED_ELDER_GUARDIAN_TYPE::get);
    public static final Supplier<EntityType<WildHuntSkeletonEntity>> WILD_HUNT_SKELETON =
            ENTITIES.register("wild_hunt_skeleton", WILD_HUNT_SKELETON_TYPE::get);
    public static final Supplier<EntityType<PossessedWardenEntity>> POSSESSED_WARDEN =
            ENTITIES.register("possessed_warden", POSSESSED_WARDEN_TYPE::get);
    public static final Supplier<EntityType<PossessedHoglinEntity>> POSSESSED_HOGLIN =
            ENTITIES.register("possessed_hoglin", POSSESSED_HOGLIN_TYPE::get);
    public static final Supplier<EntityType<PossessedWitchEntity>> POSSESSED_WITCH =
            ENTITIES.register("possessed_witch", POSSESSED_WITCH_TYPE::get);
    public static final Supplier<EntityType<PossessedZombiePiglinEntity>> POSSESSED_ZOMBIE_PIGLIN =
            ENTITIES.register("possessed_zombie_piglin", POSSESSED_ZOMBIE_PIGLIN_TYPE::get);
    public static final Supplier<EntityType<PossessedBeeEntity>> POSSESSED_BEE =
            ENTITIES.register("possessed_bee", POSSESSED_BEE_TYPE::get);
    public static final Supplier<EntityType<GoatOfMercyEntity>> GOAT_OF_MERCY =
            ENTITIES.register("mercy_goat", GOAT_OF_MERCY_TYPE::get);
    public static final Supplier<EntityType<WildHuntWitherSkeletonEntity>> WILD_HUNT_WITHER_SKELETON =
            ENTITIES.register("wild_hunt_wither_skeleton", WILD_HUNT_WITHER_SKELETON_TYPE::get);
    public static final Supplier<EntityType<OtherworldBirdEntity>> OTHERWORLD_BIRD =
            ENTITIES.register("otherworld_bird", OTHERWORLD_BIRD_TYPE::get);
    public static final Supplier<EntityType<WildHordeHuskEntity>> WILD_HORDE_HUSK =
            ENTITIES.register("wild_horde_husk", WILD_HORDE_HUSK_TYPE::get);
    public static final Supplier<EntityType<WildHordeDrownedEntity>> WILD_HORDE_DROWNED =
            ENTITIES.register("wild_horde_drowned", WILD_HORDE_DROWNED_TYPE::get);
    public static final Supplier<EntityType<WildHordeCreeperEntity>> WILD_HORDE_CREEPER =
            ENTITIES.register("wild_horde_creeper", WILD_HORDE_CREEPER_TYPE::get);
    public static final Supplier<EntityType<WildHordeSilverfishEntity>> WILD_HORDE_SILVERFISH =
            ENTITIES.register("wild_horde_silverfish", WILD_HORDE_SILVERFISH_TYPE::get);
    public static final Supplier<EntityType<PossessedWeakBreezeEntity>> POSSESSED_WEAK_BREEZE =
            ENTITIES.register("possessed_weak_breeze", POSSESSED_WEAK_BREEZE_TYPE::get);
    public static final Supplier<EntityType<PossessedBreezeEntity>> POSSESSED_BREEZE =
            ENTITIES.register("possessed_breeze", POSSESSED_BREEZE_TYPE::get);
    public static final Supplier<EntityType<PossessedStrongBreezeEntity>> POSSESSED_STRONG_BREEZE =
            ENTITIES.register("possessed_strong_breeze", POSSESSED_STRONG_BREEZE_TYPE::get);
    public static final Supplier<EntityType<WildZombieEntity>> WILD_ZOMBIE =
            ENTITIES.register("wild_zombie", WILD_ZOMBIE_TYPE::get);
    public static final Supplier<EntityType<WildSkeletonEntity>> WILD_SKELETON =
            ENTITIES.register("wild_skeleton", WILD_SKELETON_TYPE::get);
    public static final Supplier<EntityType<WildSilverfishEntity>> WILD_SILVERFISH =
            ENTITIES.register("wild_silverfish", WILD_SILVERFISH_TYPE::get);
    public static final Supplier<EntityType<WildSpiderEntity>> WILD_SPIDER =
            ENTITIES.register("wild_spider", WILD_SPIDER_TYPE::get);
    public static final Supplier<EntityType<WildBoggedEntity>> WILD_BOGGED =
            ENTITIES.register("wild_bogged", WILD_BOGGED_TYPE::get);
    public static final Supplier<EntityType<WildSlimeEntity>> WILD_SLIME =
            ENTITIES.register("wild_slime", WILD_SLIME_TYPE::get);
    public static final Supplier<EntityType<WildHuskEntity>> WILD_HUSK =
            ENTITIES.register("wild_husk", WILD_HUSK_TYPE::get);
    public static final Supplier<EntityType<WildStrayEntity>> WILD_STRAY =
            ENTITIES.register("wild_stray", WILD_STRAY_TYPE::get);
    public static final Supplier<EntityType<WildCaveSpiderEntity>> WILD_CAVE_SPIDER =
            ENTITIES.register("wild_cave_spider", WILD_CAVE_SPIDER_TYPE::get);
    public static final Supplier<EntityType<PossessedEvokerEntity>> POSSESSED_EVOKER =
            ENTITIES.register("possessed_evoker", POSSESSED_EVOKER_TYPE::get);
    public static final Supplier<EntityType<GreedyFamiliarEntity>> GREEDY_FAMILIAR =
            ENTITIES.register("greedy_familiar", GREEDY_FAMILIAR_TYPE::get);
    public static final Supplier<EntityType<BatFamiliarEntity>> BAT_FAMILIAR =
            ENTITIES.register("bat_familiar", BAT_FAMILIAR_TYPE::get);
    public static final Supplier<EntityType<DeerFamiliarEntity>> DEER_FAMILIAR =
            ENTITIES.register("deer_familiar", DEER_FAMILIAR_TYPE::get);
    public static final Supplier<EntityType<CthulhuFamiliarEntity>> CTHULHU_FAMILIAR =
            ENTITIES.register("cthulhu_familiar", CTHULHU_FAMILIAR_TYPE::get);
    public static final Supplier<EntityType<DevilFamiliarEntity>> DEVIL_FAMILIAR =
            ENTITIES.register("devil_familiar", DEVIL_FAMILIAR_TYPE::get);
    public static final Supplier<EntityType<DragonFamiliarEntity>> DRAGON_FAMILIAR =
            ENTITIES.register("dragon_familiar", DRAGON_FAMILIAR_TYPE::get);
    public static final Supplier<EntityType<BlacksmithFamiliarEntity>> BLACKSMITH_FAMILIAR =
            ENTITIES.register("blacksmith_familiar", BLACKSMITH_FAMILIAR_TYPE::get);
    public static final Supplier<EntityType<GuardianFamiliarEntity>> GUARDIAN_FAMILIAR =
            ENTITIES.register("guardian_familiar", GUARDIAN_FAMILIAR_TYPE::get);
    public static final Supplier<EntityType<HeadlessFamiliarEntity>> HEADLESS_FAMILIAR =
            ENTITIES.register("headless_familiar", HEADLESS_FAMILIAR_TYPE::get);
    public static final Supplier<EntityType<ChimeraFamiliarEntity>> CHIMERA_FAMILIAR =
            ENTITIES.register("chimera_familiar", CHIMERA_FAMILIAR_TYPE::get);
    public static final Supplier<EntityType<GoatFamiliarEntity>> GOAT_FAMILIAR =
            ENTITIES.register("goat_familiar", GOAT_FAMILIAR_TYPE::get);
    public static final Supplier<EntityType<ShubNiggurathFamiliarEntity>> SHUB_NIGGURATH_FAMILIAR =
            ENTITIES.register("shub_niggurath_familiar", SHUB_NIGGURATH_FAMILIAR_TYPE::get);
    public static final Supplier<EntityType<BeholderFamiliarEntity>> BEHOLDER_FAMILIAR =
            ENTITIES.register("beholder_familiar", BEHOLDER_FAMILIAR_TYPE::get);
    public static final Supplier<EntityType<FairyFamiliarEntity>> FAIRY_FAMILIAR =
            ENTITIES.register("fairy_familiar", FAIRY_FAMILIAR_TYPE::get);
    public static final Supplier<EntityType<MummyFamiliarEntity>> MUMMY_FAMILIAR =
            ENTITIES.register("mummy_familiar", MUMMY_FAMILIAR_TYPE::get);
    public static final Supplier<EntityType<BeaverFamiliarEntity>> BEAVER_FAMILIAR =
            ENTITIES.register("beaver_familiar", BEAVER_FAMILIAR_TYPE::get);
    public static final Supplier<EntityType<ThrownSwordEntity>> THROWN_SWORD =
            ENTITIES.register("thrown_sword", THROWN_SWORD_TYPE::get);
    public static final Supplier<EntityType<ShubNiggurathSpawnEntity>> SHUB_NIGGURATH_SPAWN =
            ENTITIES.register("shub_niggurath_spawn", SHUB_NIGGURATH_SPAWN_TYPE::get);
    public static final Supplier<EntityType<IesniumGolemEntity>> IESNIUM_GOLEM =
            ENTITIES.register("iesnium_golem", IESNIUM_GOLEM_TYPE::get);

    public static final Supplier<EntityType<DemonicWife>> DEMONIC_WIFE = ENTITIES.register(DemonicWife.ID.getPath(), DemonicWife.ENTITY_TYPE::get);
    public static final Supplier<EntityType<DemonicHusband>> DEMONIC_HUSBAND = ENTITIES.register(DemonicHusband.ID.getPath(), DemonicHusband.ENTITY_TYPE::get);
}
