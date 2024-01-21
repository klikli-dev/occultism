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
import com.klikli_dev.occultism.common.entity.spirit.*;
import com.klikli_dev.occultism.common.entity.spirit.demonicpartner.husband.DemonicHusband;
import com.klikli_dev.occultism.common.entity.spirit.demonicpartner.wife.DemonicWife;
import com.klikli_dev.occultism.util.StaticUtil;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.util.NonNullLazy;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class OccultismEntities {

    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES,
            Occultism.MODID);

    public static final NonNullLazy<EntityType<FoliotEntity>> FOLIOT_TYPE =
            NonNullLazy.of(() -> EntityType.Builder.of(FoliotEntity::new, MobCategory.CREATURE)
                    .sized(0.6f, 0.7f)
                    .clientTrackingRange(8)
                    .build(StaticUtil.modLoc("foliot").toString()));
    public static final NonNullLazy<EntityType<DjinniEntity>> DJINNI_TYPE =
            NonNullLazy.of(() -> EntityType.Builder.of(DjinniEntity::new, MobCategory.CREATURE)
                    .sized(0.6f, 0.8f)
                    .clientTrackingRange(8)
                    .build(StaticUtil.modLoc("djinni").toString()));
    public static final NonNullLazy<EntityType<AfritEntity>> AFRIT_TYPE =
            NonNullLazy.of(() -> EntityType.Builder.of((EntityType<AfritEntity> t, Level l) -> new AfritEntity(t, l), MobCategory.CREATURE)
                    .fireImmune()
                    .sized(1.2f, 2.4f)
                    .clientTrackingRange(8)
                    .build(StaticUtil.modLoc("afrit").toString()));
    public static final NonNullLazy<EntityType<AfritWildEntity>> AFRIT_WILD_TYPE =
            NonNullLazy.of(() -> EntityType.Builder.of((EntityType<AfritWildEntity> t, Level l) -> new AfritWildEntity(t, l), MobCategory.CREATURE)
                    .fireImmune()
                    .sized(1.2f, 2.4f)
                    .clientTrackingRange(8)
                    .build(StaticUtil.modLoc("afrit_wild").toString()));
    public static final NonNullLazy<EntityType<MaridEntity>> MARID_TYPE =
            NonNullLazy.of(() -> EntityType.Builder.of(MaridEntity::new, MobCategory.CREATURE)
                    .sized(1.2f, 2.4f)
                    .clientTrackingRange(8)
                    .build(StaticUtil.modLoc("marid").toString()));

    public static final NonNullLazy<EntityType<PossessedEndermiteEntity>> POSSESSED_ENDERMITE_TYPE =
            NonNullLazy.of(() -> EntityType.Builder.of(PossessedEndermiteEntity::new, MobCategory.MONSTER)
                    .sized(0.4F, 0.3F)
                    .clientTrackingRange(8)
                    .build(StaticUtil.modLoc("possessed_endermite").toString()));
    public static final NonNullLazy<EntityType<PossessedSkeletonEntity>> POSSESSED_SKELETON_TYPE =
            NonNullLazy.of(() -> EntityType.Builder.of(PossessedSkeletonEntity::new, MobCategory.MONSTER)
                    .sized(0.6F, 1.99F)
                    .clientTrackingRange(8)
                    .build(StaticUtil.modLoc("possessed_skeleton").toString()));
    public static final NonNullLazy<EntityType<PossessedEndermanEntity>> POSSESSED_ENDERMAN_TYPE =
            NonNullLazy.of(() -> EntityType.Builder.of(PossessedEndermanEntity::new, MobCategory.MONSTER)
                    .sized(0.6F, 2.9F)
                    .clientTrackingRange(8)
                    .build(StaticUtil.modLoc("possessed_enderman").toString()));
    public static final NonNullLazy<EntityType<PossessedGhastEntity>> POSSESSED_GHAST_TYPE =
            NonNullLazy.of(() -> EntityType.Builder.of(PossessedGhastEntity::new, MobCategory.MONSTER)
                    .fireImmune()
                    .sized(4.0F, 4.0F)
                    .clientTrackingRange(10)
                    .build(StaticUtil.modLoc("possessed_ghast").toString()));
    public static final NonNullLazy<EntityType<PossessedPhantomEntity>> POSSESSED_PHANTOM_TYPE =
            NonNullLazy.of(() -> EntityType.Builder.of(PossessedPhantomEntity::new, MobCategory.MONSTER)
                    .fireImmune()
                    .sized(0.9F, 0.5F)
                    .clientTrackingRange(16)
                    .build(StaticUtil.modLoc("possessed_phantom").toString()));
    public static final NonNullLazy<EntityType<PossessedWeakShulkerEntity>> POSSESSED_WEAK_SHULKER_TYPE =
            NonNullLazy.of(() -> EntityType.Builder.of(PossessedWeakShulkerEntity::new, MobCategory.MONSTER)
                    .fireImmune()
                    .sized(1F, 1F)
                    .clientTrackingRange(8)
                    .build(StaticUtil.modLoc("possessed_weak_shulker").toString()));
    public static final NonNullLazy<EntityType<PossessedShulkerEntity>> POSSESSED_SHULKER_TYPE =
            NonNullLazy.of(() -> EntityType.Builder.of(PossessedShulkerEntity::new, MobCategory.MONSTER)
                    .fireImmune()
                    .sized(1F, 1F)
                    .clientTrackingRange(8)
                    .build(StaticUtil.modLoc("possessed_shulker").toString()));
    public static final NonNullLazy<EntityType<PossessedElderGuardianEntity>> POSSESSED_ELDER_GUARDIAN_TYPE =
            NonNullLazy.of(() -> EntityType.Builder.of(PossessedElderGuardianEntity::new, MobCategory.MONSTER)
                    .fireImmune()
                    .sized(2F, 2F)
                    .clientTrackingRange(24)
                    .build(StaticUtil.modLoc("possessed_elder_guardian").toString()));

    public static final NonNullLazy<EntityType<PossessedWardenEntity>> POSSESSED_WARDEN_TYPE =
            NonNullLazy.of(() -> EntityType.Builder.of(PossessedWardenEntity::new, MobCategory.MONSTER)
                    .fireImmune()
                    .sized(1.8F, 5.8F)
                    .clientTrackingRange(48)
                    .build(StaticUtil.modLoc("possessed_warden").toString()));

    public static final NonNullLazy<EntityType<PossessedHoglinEntity>> POSSESSED_HOGLIN_TYPE =
            NonNullLazy.of(() -> EntityType.Builder.of(PossessedHoglinEntity::new, MobCategory.MONSTER)
                    .fireImmune()
                    .sized(1.88F, 1.88F)
                    .clientTrackingRange(24)
                    .build(StaticUtil.modLoc("possessed_hoglin").toString()));
    public static final NonNullLazy<EntityType<WildHuntSkeletonEntity>> WILD_HUNT_SKELETON_TYPE =
            NonNullLazy.of(() -> EntityType.Builder.of(WildHuntSkeletonEntity::new, MobCategory.MONSTER)
                    .sized(0.6F, 2.9F)
                    .clientTrackingRange(8)
                    .build(StaticUtil.modLoc("wild_hunt_skeleton").toString()));
    public static final NonNullLazy<EntityType<WildHuntWitherSkeletonEntity>> WILD_HUNT_WITHER_SKELETON_TYPE =
            NonNullLazy.of(() -> EntityType.Builder.of(WildHuntWitherSkeletonEntity::new, MobCategory.MONSTER)
                    .sized(0.6F, 2.9F)
                    .clientTrackingRange(8)
                    .build(StaticUtil.modLoc("wild_hunt_wither_skeleton").toString()));
    public static final NonNullLazy<EntityType<OtherworldBirdEntity>> OTHERWORLD_BIRD_TYPE =
            NonNullLazy.of(() -> EntityType.Builder.of(OtherworldBirdEntity::new, MobCategory.CREATURE)
                    .sized(0.5F, 0.9F)
                    .clientTrackingRange(8)
                    .build(StaticUtil.modLoc("otherworld_bird").toString()));
    public static final NonNullLazy<EntityType<GreedyFamiliarEntity>> GREEDY_FAMILIAR_TYPE =
            NonNullLazy.of(() -> EntityType.Builder.of(GreedyFamiliarEntity::new, MobCategory.CREATURE)
                    .sized(0.5F, 0.9F)
                    .clientTrackingRange(8)
                    .build(StaticUtil.modLoc("greedy_familiar").toString()));
    public static final NonNullLazy<EntityType<BatFamiliarEntity>> BAT_FAMILIAR_TYPE =
            NonNullLazy.of(() -> EntityType.Builder.of(BatFamiliarEntity::new, MobCategory.CREATURE)
                    .sized(0.5F, 0.9F)
                    .clientTrackingRange(8)
                    .build(StaticUtil.modLoc("bat_familiar").toString()));
    public static final NonNullLazy<EntityType<DeerFamiliarEntity>> DEER_FAMILIAR_TYPE =
            NonNullLazy.of(() -> EntityType.Builder.of(DeerFamiliarEntity::new, MobCategory.CREATURE)
                    .sized(0.6F, 1.0F)
                    .clientTrackingRange(8)
                    .build(StaticUtil.modLoc("deer_familiar").toString()));

    public static final NonNullLazy<EntityType<CthulhuFamiliarEntity>> CTHULHU_FAMILIAR_TYPE =
            NonNullLazy.of(() -> EntityType.Builder.of(CthulhuFamiliarEntity::new, MobCategory.CREATURE)
                    .sized(0.6F, 1.0F)
                    .clientTrackingRange(8)
                    .build(StaticUtil.modLoc("cthulhu_familiar").toString()));

    public static final NonNullLazy<EntityType<DevilFamiliarEntity>> DEVIL_FAMILIAR_TYPE =
            NonNullLazy.of(() -> EntityType.Builder.of(DevilFamiliarEntity::new, MobCategory.CREATURE)
                    .sized(0.8F, 1.8F)
                    .fireImmune()
                    .clientTrackingRange(8)
                    .build(StaticUtil.modLoc("devil_familiar").toString()));
    public static final NonNullLazy<EntityType<DragonFamiliarEntity>> DRAGON_FAMILIAR_TYPE =
            NonNullLazy.of(() -> EntityType.Builder.of(DragonFamiliarEntity::new, MobCategory.CREATURE)
                    .sized(1F, 0.8F)
                    .clientTrackingRange(8)
                    .fireImmune()
                    .build(StaticUtil.modLoc("dragon_familiar").toString()));
    public static final NonNullLazy<EntityType<BlacksmithFamiliarEntity>> BLACKSMITH_FAMILIAR_TYPE =
            NonNullLazy.of(() -> EntityType.Builder.of(BlacksmithFamiliarEntity::new, MobCategory.CREATURE)
                    .sized(0.65F, 1F)
                    .clientTrackingRange(8)
                    .build(StaticUtil.modLoc("blacksmith_familiar").toString()));
    public static final NonNullLazy<EntityType<GuardianFamiliarEntity>> GUARDIAN_FAMILIAR_TYPE =
            NonNullLazy.of(() -> EntityType.Builder.of(GuardianFamiliarEntity::new, MobCategory.CREATURE)
                    .sized(0.8F, 1.5F)
                    .clientTrackingRange(8)
                    .fireImmune()
                    .build(StaticUtil.modLoc("guardian_familiar").toString()));

    public static final NonNullLazy<EntityType<HeadlessFamiliarEntity>> HEADLESS_FAMILIAR_TYPE =
            NonNullLazy.of(() -> EntityType.Builder.of(HeadlessFamiliarEntity::new, MobCategory.CREATURE)
                    .sized(0.7F, 1.1F)
                    .clientTrackingRange(8)
                    .build(StaticUtil.modLoc("headless_familiar").toString()));

    public static final NonNullLazy<EntityType<ChimeraFamiliarEntity>> CHIMERA_FAMILIAR_TYPE =
            NonNullLazy.of(() -> EntityType.Builder.of(ChimeraFamiliarEntity::new, MobCategory.CREATURE)
                    .sized(0.85F, 1.05F)
                    .clientTrackingRange(8)
                    .build(StaticUtil.modLoc("chimera_familiar").toString()));
    public static final NonNullLazy<EntityType<GoatFamiliarEntity>> GOAT_FAMILIAR_TYPE =
            NonNullLazy.of(() -> EntityType.Builder.<GoatFamiliarEntity>of(GoatFamiliarEntity::new, MobCategory.CREATURE)
                    .sized(0.7F, 0.8F)
                    .clientTrackingRange(8)
                    .build(StaticUtil.modLoc("goat_familiar").toString()));
    public static final NonNullLazy<EntityType<ShubNiggurathFamiliarEntity>> SHUB_NIGGURATH_FAMILIAR_TYPE =
            NonNullLazy.of(() -> EntityType.Builder.<ShubNiggurathFamiliarEntity>of(ShubNiggurathFamiliarEntity::new, MobCategory.CREATURE)
                    .sized(0.7F, 0.8F)
                    .clientTrackingRange(8)
                    .build(StaticUtil.modLoc("shub_niggurath_familiar").toString()));
    public static final NonNullLazy<EntityType<BeholderFamiliarEntity>> BEHOLDER_FAMILIAR_TYPE =
            NonNullLazy.of(() -> EntityType.Builder.of(BeholderFamiliarEntity::new, MobCategory.CREATURE)
                    .sized(1.6F, 1.5F)
                    .clientTrackingRange(8)
                    .build(StaticUtil.modLoc("beholder_familiar").toString()));

    public static final NonNullLazy<EntityType<FairyFamiliarEntity>> FAIRY_FAMILIAR_TYPE =
            NonNullLazy.of(() -> EntityType.Builder.of(FairyFamiliarEntity::new, MobCategory.CREATURE)
                    .sized(0.6F, 1F)
                    .clientTrackingRange(8)
                    .fireImmune()
                    .build(StaticUtil.modLoc("fairy_familiar").toString()));

    public static final NonNullLazy<EntityType<MummyFamiliarEntity>> MUMMY_FAMILIAR_TYPE =
            NonNullLazy.of(() -> EntityType.Builder.of(MummyFamiliarEntity::new, MobCategory.CREATURE)
                    .sized(0.6F, 1.2F)
                    .clientTrackingRange(8)
                    .build(StaticUtil.modLoc("mummy_familiar").toString()));

    public static final NonNullLazy<EntityType<BeaverFamiliarEntity>> BEAVER_FAMILIAR_TYPE =
            NonNullLazy.of(() -> EntityType.Builder.of(BeaverFamiliarEntity::new, MobCategory.CREATURE)
                    .sized(0.7F, 0.6F)
                    .clientTrackingRange(8)
                    .build(StaticUtil.modLoc("beaver_familiar").toString()));

    public static final NonNullLazy<EntityType<ThrownSwordEntity>> THROWN_SWORD_TYPE =
            NonNullLazy.of(() -> EntityType.Builder.of(ThrownSwordEntity::new, MobCategory.MISC)
                    .sized(0.5F, 0.5F)
                    .clientTrackingRange(8)
                    .build(StaticUtil.modLoc("thrown_sword").toString()));
    public static final NonNullLazy<EntityType<ShubNiggurathSpawnEntity>> SHUB_NIGGURATH_SPAWN_TYPE =
            NonNullLazy.of(() -> EntityType.Builder.<ShubNiggurathSpawnEntity>of(ShubNiggurathSpawnEntity::new, MobCategory.CREATURE)
                    .sized(0.6F, 0.6F)
                    .clientTrackingRange(8)
                    .build(StaticUtil.modLoc("shub_niggurath_spawn").toString()));


    public static final RegistryObject<EntityType<FoliotEntity>> FOLIOT = ENTITIES.register("foliot", FOLIOT_TYPE::get);
    public static final RegistryObject<EntityType<DjinniEntity>> DJINNI = ENTITIES.register("djinni", DJINNI_TYPE::get);
    public static final RegistryObject<EntityType<AfritEntity>> AFRIT = ENTITIES.register("afrit", AFRIT_TYPE::get);
    public static final RegistryObject<EntityType<AfritWildEntity>> AFRIT_WILD = ENTITIES.register("afrit_wild", AFRIT_WILD_TYPE::get);
    public static final RegistryObject<EntityType<MaridEntity>> MARID = ENTITIES.register("marid", MARID_TYPE::get);


    public static final RegistryObject<EntityType<PossessedEndermiteEntity>> POSSESSED_ENDERMITE =
            ENTITIES.register("possessed_endermite", POSSESSED_ENDERMITE_TYPE::get);
    public static final RegistryObject<EntityType<PossessedSkeletonEntity>> POSSESSED_SKELETON =
            ENTITIES.register("possessed_skeleton", POSSESSED_SKELETON_TYPE::get);
    public static final RegistryObject<EntityType<PossessedEndermanEntity>> POSSESSED_ENDERMAN =
            ENTITIES.register("possessed_enderman", POSSESSED_ENDERMAN_TYPE::get);
    public static final RegistryObject<EntityType<PossessedGhastEntity>> POSSESSED_GHAST =
            ENTITIES.register("possessed_ghast", POSSESSED_GHAST_TYPE::get);
    public static final RegistryObject<EntityType<PossessedPhantomEntity>> POSSESSED_PHANTOM =
            ENTITIES.register("possessed_phantom", POSSESSED_PHANTOM_TYPE::get);
    public static final RegistryObject<EntityType<PossessedWeakShulkerEntity>> POSSESSED_WEAK_SHULKER =
            ENTITIES.register("possessed_weak_shulker", POSSESSED_WEAK_SHULKER_TYPE::get);
    public static final RegistryObject<EntityType<PossessedShulkerEntity>> POSSESSED_SHULKER =
            ENTITIES.register("possessed_shulker", POSSESSED_SHULKER_TYPE::get);
    public static final RegistryObject<EntityType<PossessedElderGuardianEntity>> POSSESSED_ELDER_GUARDIAN =
            ENTITIES.register("possessed_elder_guardian", POSSESSED_ELDER_GUARDIAN_TYPE::get);

    public static final RegistryObject<EntityType<PossessedWardenEntity>> POSSESSED_WARDEN =
            ENTITIES.register("possessed_warden", POSSESSED_WARDEN_TYPE::get);

    public static final RegistryObject<EntityType<PossessedHoglinEntity>> POSSESSED_HOGLIN =
            ENTITIES.register("possessed_hoglin", POSSESSED_HOGLIN_TYPE::get);
    public static final RegistryObject<EntityType<WildHuntSkeletonEntity>> WILD_HUNT_SKELETON =
            ENTITIES.register("wild_hunt_skeleton", WILD_HUNT_SKELETON_TYPE::get);
    public static final RegistryObject<EntityType<WildHuntWitherSkeletonEntity>> WILD_HUNT_WITHER_SKELETON =
            ENTITIES.register("wild_hunt_wither_skeleton", WILD_HUNT_WITHER_SKELETON_TYPE::get);
    public static final RegistryObject<EntityType<OtherworldBirdEntity>> OTHERWORLD_BIRD =
            ENTITIES.register("otherworld_bird", OTHERWORLD_BIRD_TYPE::get);
    public static final RegistryObject<EntityType<GreedyFamiliarEntity>> GREEDY_FAMILIAR =
            ENTITIES.register("greedy_familiar", GREEDY_FAMILIAR_TYPE::get);
    public static final RegistryObject<EntityType<BatFamiliarEntity>> BAT_FAMILIAR =
            ENTITIES.register("bat_familiar", BAT_FAMILIAR_TYPE::get);
    public static final RegistryObject<EntityType<DeerFamiliarEntity>> DEER_FAMILIAR =
            ENTITIES.register("deer_familiar", DEER_FAMILIAR_TYPE::get);
    public static final RegistryObject<EntityType<CthulhuFamiliarEntity>> CTHULHU_FAMILIAR =
            ENTITIES.register("cthulhu_familiar", CTHULHU_FAMILIAR_TYPE::get);
    public static final RegistryObject<EntityType<DevilFamiliarEntity>> DEVIL_FAMILIAR =
            ENTITIES.register("devil_familiar", DEVIL_FAMILIAR_TYPE::get);
    public static final RegistryObject<EntityType<DragonFamiliarEntity>> DRAGON_FAMILIAR =
            ENTITIES.register("dragon_familiar", DRAGON_FAMILIAR_TYPE::get);
    public static final RegistryObject<EntityType<BlacksmithFamiliarEntity>> BLACKSMITH_FAMILIAR =
            ENTITIES.register("blacksmith_familiar", BLACKSMITH_FAMILIAR_TYPE::get);
    public static final RegistryObject<EntityType<GuardianFamiliarEntity>> GUARDIAN_FAMILIAR =
            ENTITIES.register("guardian_familiar", GUARDIAN_FAMILIAR_TYPE::get);
    public static final RegistryObject<EntityType<HeadlessFamiliarEntity>> HEADLESS_FAMILIAR =
            ENTITIES.register("headless_familiar", HEADLESS_FAMILIAR_TYPE::get);
    public static final RegistryObject<EntityType<ChimeraFamiliarEntity>> CHIMERA_FAMILIAR =
            ENTITIES.register("chimera_familiar", CHIMERA_FAMILIAR_TYPE::get);
    public static final RegistryObject<EntityType<GoatFamiliarEntity>> GOAT_FAMILIAR =
            ENTITIES.register("goat_familiar", GOAT_FAMILIAR_TYPE::get);
    public static final RegistryObject<EntityType<ShubNiggurathFamiliarEntity>> SHUB_NIGGURATH_FAMILIAR =
            ENTITIES.register("shub_niggurath_familiar", SHUB_NIGGURATH_FAMILIAR_TYPE::get);
    public static final RegistryObject<EntityType<BeholderFamiliarEntity>> BEHOLDER_FAMILIAR =
            ENTITIES.register("beholder_familiar", BEHOLDER_FAMILIAR_TYPE::get);
    public static final RegistryObject<EntityType<FairyFamiliarEntity>> FAIRY_FAMILIAR =
            ENTITIES.register("fairy_familiar", FAIRY_FAMILIAR_TYPE::get);
    public static final RegistryObject<EntityType<MummyFamiliarEntity>> MUMMY_FAMILIAR =
            ENTITIES.register("mummy_familiar", MUMMY_FAMILIAR_TYPE::get);
    public static final RegistryObject<EntityType<BeaverFamiliarEntity>> BEAVER_FAMILIAR =
            ENTITIES.register("beaver_familiar", BEAVER_FAMILIAR_TYPE::get);
    public static final RegistryObject<EntityType<ThrownSwordEntity>> THROWN_SWORD =
            ENTITIES.register("thrown_sword", THROWN_SWORD_TYPE::get);
    public static final RegistryObject<EntityType<ShubNiggurathSpawnEntity>> SHUB_NIGGURATH_SPAWN =
            ENTITIES.register("shub_niggurath_spawn", SHUB_NIGGURATH_SPAWN_TYPE::get);

    public static final RegistryObject<EntityType<DemonicWife>> DEMONIC_WIFE = ENTITIES.register(DemonicWife.ID.getPath(), DemonicWife.ENTITY_TYPE::get);
    public static final RegistryObject<EntityType<DemonicHusband>> DEMONIC_HUSBAND = ENTITIES.register(DemonicHusband.ID.getPath(), DemonicHusband.ENTITY_TYPE::get);
}
