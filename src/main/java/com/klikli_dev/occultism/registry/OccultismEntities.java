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
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.common.util.NonNullLazy;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class OccultismEntities {

    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(BuiltInRegistries.ENTITY_TYPE,
            Occultism.MODID);

    public static final NonNullLazy<EntityType<FoliotEntity>> FOLIOT_TYPE =
            NonNullLazy.of(() -> EntityType.Builder.of(FoliotEntity::new, MobCategory.CREATURE)
                    .sized(0.6f, 1.1f)
                    .clientTrackingRange(8)
                    .build(new ResourceLocation(Occultism.MODID, "foliot").toString()));
    public static final NonNullLazy<EntityType<DjinniEntity>> DJINNI_TYPE =
            NonNullLazy.of(() -> EntityType.Builder.of(DjinniEntity::new, MobCategory.CREATURE)
                    .sized(0.6f, 1.1f)
                    .clientTrackingRange(8)
                    .build(new ResourceLocation(Occultism.MODID, "djinni").toString()));
    public static final NonNullLazy<EntityType<AfritEntity>> AFRIT_TYPE =
            NonNullLazy.of(() -> EntityType.Builder.of((EntityType<AfritEntity> t, Level l) -> new AfritEntity(t, l), MobCategory.CREATURE)
                    .fireImmune()
                    .sized(1.2f, 2.4f)
                    .clientTrackingRange(8)
                    .build(new ResourceLocation(Occultism.MODID, "afrit").toString()));
    public static final NonNullLazy<EntityType<AfritWildEntity>> AFRIT_WILD_TYPE =
            NonNullLazy.of(() -> EntityType.Builder.of((EntityType<AfritWildEntity> t, Level l) -> new AfritWildEntity(t, l), MobCategory.CREATURE)
                    .fireImmune()
                    .sized(1.2f, 2.4f)
                    .clientTrackingRange(8)
                    .build(new ResourceLocation(Occultism.MODID, "afrit_wild").toString()));
    public static final NonNullLazy<EntityType<MaridEntity>> MARID_TYPE =
            NonNullLazy.of(() -> EntityType.Builder.of(MaridEntity::new, MobCategory.CREATURE)
                    .sized(1.2f, 2.4f)
                    .clientTrackingRange(8)
                    .build(new ResourceLocation(Occultism.MODID, "marid").toString()));

    public static final NonNullLazy<EntityType<PossessedEndermiteEntity>> POSSESSED_ENDERMITE_TYPE =
            NonNullLazy.of(() -> EntityType.Builder.of(PossessedEndermiteEntity::new, MobCategory.MONSTER)
                    .sized(0.4F, 0.3F)
                    .clientTrackingRange(8)
                    .build(new ResourceLocation(Occultism.MODID, "possessed_endermite").toString()));
    public static final NonNullLazy<EntityType<PossessedSkeletonEntity>> POSSESSED_SKELETON_TYPE =
            NonNullLazy.of(() -> EntityType.Builder.of(PossessedSkeletonEntity::new, MobCategory.MONSTER)
                    .sized(0.6F, 1.99F)
                    .clientTrackingRange(8)
                    .build(new ResourceLocation(Occultism.MODID, "possessed_skeleton").toString()));
    public static final NonNullLazy<EntityType<PossessedEndermanEntity>> POSSESSED_ENDERMAN_TYPE =
            NonNullLazy.of(() -> EntityType.Builder.of(PossessedEndermanEntity::new, MobCategory.MONSTER)
                    .sized(0.6F, 2.9F)
                    .clientTrackingRange(8)
                    .build(new ResourceLocation(Occultism.MODID, "possessed_enderman").toString()));
    public static final NonNullLazy<EntityType<PossessedGhastEntity>> POSSESSED_GHAST_TYPE =
            NonNullLazy.of(() -> EntityType.Builder.of(PossessedGhastEntity::new, MobCategory.MONSTER)
                    .fireImmune()
                    .sized(4.0F, 4.0F)
                    .clientTrackingRange(10)
                    .build(new ResourceLocation(Occultism.MODID, "possessed_ghast").toString()));
    public static final NonNullLazy<EntityType<PossessedPhantomEntity>> POSSESSED_PHANTOM_TYPE =
            NonNullLazy.of(() -> EntityType.Builder.of(PossessedPhantomEntity::new, MobCategory.MONSTER)
                    .fireImmune()
                    .sized(0.9F, 0.5F)
                    .clientTrackingRange(16)
                    .build(new ResourceLocation(Occultism.MODID, "possessed_phantom").toString()));
    public static final NonNullLazy<EntityType<PossessedWeakShulkerEntity>> POSSESSED_WEAK_SHULKER_TYPE =
            NonNullLazy.of(() -> EntityType.Builder.of(PossessedWeakShulkerEntity::new, MobCategory.MONSTER)
                    .fireImmune()
                    .sized(1F, 1F)
                    .clientTrackingRange(8)
                    .build(new ResourceLocation(Occultism.MODID, "possessed_weak_shulker").toString()));
    public static final NonNullLazy<EntityType<PossessedShulkerEntity>> POSSESSED_SHULKER_TYPE =
            NonNullLazy.of(() -> EntityType.Builder.of(PossessedShulkerEntity::new, MobCategory.MONSTER)
                    .fireImmune()
                    .sized(1F, 1F)
                    .clientTrackingRange(8)
                    .build(new ResourceLocation(Occultism.MODID, "possessed_shulker").toString()));
    public static final NonNullLazy<EntityType<PossessedElderGuardianEntity>> POSSESSED_ELDER_GUARDIAN_TYPE =
            NonNullLazy.of(() -> EntityType.Builder.of(PossessedElderGuardianEntity::new, MobCategory.MONSTER)
                    .fireImmune()
                    .sized(2F, 2F)
                    .clientTrackingRange(24)
                    .build(new ResourceLocation(Occultism.MODID, "possessed_elder_guardian").toString()));
    public static final NonNullLazy<EntityType<WildHuntSkeletonEntity>> WILD_HUNT_SKELETON_TYPE =
            NonNullLazy.of(() -> EntityType.Builder.of(WildHuntSkeletonEntity::new, MobCategory.MONSTER)
                    .sized(0.6F, 2.9F)
                    .clientTrackingRange(8)
                    .build(new ResourceLocation(Occultism.MODID, "wild_hunt_skeleton").toString()));
    public static final NonNullLazy<EntityType<WildHuntWitherSkeletonEntity>> WILD_HUNT_WITHER_SKELETON_TYPE =
            NonNullLazy.of(() -> EntityType.Builder.of(WildHuntWitherSkeletonEntity::new, MobCategory.MONSTER)
                    .sized(0.6F, 2.9F)
                    .clientTrackingRange(8)
                    .build(new ResourceLocation(Occultism.MODID, "wild_hunt_wither_skeleton").toString()));
    public static final NonNullLazy<EntityType<OtherworldBirdEntity>> OTHERWORLD_BIRD_TYPE =
            NonNullLazy.of(() -> EntityType.Builder.of(OtherworldBirdEntity::new, MobCategory.CREATURE)
                    .sized(0.5F, 0.9F)
                    .clientTrackingRange(8)
                    .build(new ResourceLocation(Occultism.MODID, "otherworld_bird").toString()));
    public static final NonNullLazy<EntityType<GreedyFamiliarEntity>> GREEDY_FAMILIAR_TYPE =
            NonNullLazy.of(() -> EntityType.Builder.of(GreedyFamiliarEntity::new, MobCategory.CREATURE)
                    .sized(0.5F, 0.9F)
                    .clientTrackingRange(8)
                    .build(new ResourceLocation(Occultism.MODID, "greedy_familiar").toString()));
    public static final NonNullLazy<EntityType<BatFamiliarEntity>> BAT_FAMILIAR_TYPE =
            NonNullLazy.of(() -> EntityType.Builder.of(BatFamiliarEntity::new, MobCategory.CREATURE)
                    .sized(0.5F, 0.9F)
                    .clientTrackingRange(8)
                    .build(new ResourceLocation(Occultism.MODID, "bat_familiar").toString()));
    public static final NonNullLazy<EntityType<DeerFamiliarEntity>> DEER_FAMILIAR_TYPE =
            NonNullLazy.of(() -> EntityType.Builder.of(DeerFamiliarEntity::new, MobCategory.CREATURE)
                    .sized(0.6F, 1.0F)
                    .clientTrackingRange(8)
                    .build(new ResourceLocation(Occultism.MODID, "deer_familiar").toString()));

    public static final NonNullLazy<EntityType<CthulhuFamiliarEntity>> CTHULHU_FAMILIAR_TYPE =
            NonNullLazy.of(() -> EntityType.Builder.of(CthulhuFamiliarEntity::new, MobCategory.CREATURE)
                    .sized(0.6F, 1.0F)
                    .clientTrackingRange(8)
                    .build(new ResourceLocation(Occultism.MODID, "cthulhu_familiar").toString()));

    public static final NonNullLazy<EntityType<DevilFamiliarEntity>> DEVIL_FAMILIAR_TYPE =
            NonNullLazy.of(() -> EntityType.Builder.of(DevilFamiliarEntity::new, MobCategory.CREATURE)
                    .sized(0.8F, 1.5F)
                    .fireImmune()
                    .clientTrackingRange(8)
                    .build(new ResourceLocation(Occultism.MODID, "devil_familiar").toString()));
    public static final NonNullLazy<EntityType<DragonFamiliarEntity>> DRAGON_FAMILIAR_TYPE =
            NonNullLazy.of(() -> EntityType.Builder.of(DragonFamiliarEntity::new, MobCategory.CREATURE)
                    .sized(1F, 0.8F)
                    .clientTrackingRange(8)
                    .fireImmune()
                    .build(new ResourceLocation(Occultism.MODID, "dragon_familiar").toString()));
    public static final NonNullLazy<EntityType<BlacksmithFamiliarEntity>> BLACKSMITH_FAMILIAR_TYPE =
            NonNullLazy.of(() -> EntityType.Builder.of(BlacksmithFamiliarEntity::new, MobCategory.CREATURE)
                    .sized(0.65F, 1F)
                    .clientTrackingRange(8)
                    .build(new ResourceLocation(Occultism.MODID, "blacksmith_familiar").toString()));
    public static final NonNullLazy<EntityType<GuardianFamiliarEntity>> GUARDIAN_FAMILIAR_TYPE =
            NonNullLazy.of(() -> EntityType.Builder.of(GuardianFamiliarEntity::new, MobCategory.CREATURE)
                    .sized(0.8F, 1.5F)
                    .clientTrackingRange(8)
                    .fireImmune()
                    .build(new ResourceLocation(Occultism.MODID, "guardian_familiar").toString()));

    public static final NonNullLazy<EntityType<HeadlessFamiliarEntity>> HEADLESS_FAMILIAR_TYPE =
            NonNullLazy.of(() -> EntityType.Builder.of(HeadlessFamiliarEntity::new, MobCategory.CREATURE)
                    .sized(0.7F, 1.1F)
                    .clientTrackingRange(8)
                    .build(new ResourceLocation(Occultism.MODID, "headless_familiar").toString()));

    public static final NonNullLazy<EntityType<ChimeraFamiliarEntity>> CHIMERA_FAMILIAR_TYPE =
            NonNullLazy.of(() -> EntityType.Builder.of(ChimeraFamiliarEntity::new, MobCategory.CREATURE)
                    .sized(0.85F, 1.05F)
                    .clientTrackingRange(8)
                    .build(new ResourceLocation(Occultism.MODID, "chimera_familiar").toString()));
    public static final NonNullLazy<EntityType<GoatFamiliarEntity>> GOAT_FAMILIAR_TYPE =
            NonNullLazy.of(() -> EntityType.Builder.<GoatFamiliarEntity>of(GoatFamiliarEntity::new, MobCategory.CREATURE)
                    .sized(0.7F, 0.8F)
                    .clientTrackingRange(8)
                    .build(new ResourceLocation(Occultism.MODID, "goat_familiar").toString()));
    public static final NonNullLazy<EntityType<ShubNiggurathFamiliarEntity>> SHUB_NIGGURATH_FAMILIAR_TYPE =
            NonNullLazy.of(() -> EntityType.Builder.<ShubNiggurathFamiliarEntity>of(ShubNiggurathFamiliarEntity::new, MobCategory.CREATURE)
                    .sized(0.7F, 0.8F)
                    .clientTrackingRange(8)
                    .build(new ResourceLocation(Occultism.MODID, "shub_niggurath_familiar").toString()));
    public static final NonNullLazy<EntityType<BeholderFamiliarEntity>> BEHOLDER_FAMILIAR_TYPE =
            NonNullLazy.of(() -> EntityType.Builder.of(BeholderFamiliarEntity::new, MobCategory.CREATURE)
                    .sized(1.6F, 1F)
                    .clientTrackingRange(8)
                    .build(new ResourceLocation(Occultism.MODID, "beholder_familiar").toString()));

    public static final NonNullLazy<EntityType<FairyFamiliarEntity>> FAIRY_FAMILIAR_TYPE =
            NonNullLazy.of(() -> EntityType.Builder.of(FairyFamiliarEntity::new, MobCategory.CREATURE)
                    .sized(0.6F, 1F)
                    .clientTrackingRange(8)
                    .fireImmune()
                    .build(new ResourceLocation(Occultism.MODID, "fairy_familiar").toString()));

    public static final NonNullLazy<EntityType<MummyFamiliarEntity>> MUMMY_FAMILIAR_TYPE =
            NonNullLazy.of(() -> EntityType.Builder.of(MummyFamiliarEntity::new, MobCategory.CREATURE)
                    .sized(0.6F, 1.2F)
                    .clientTrackingRange(8)
                    .build(new ResourceLocation(Occultism.MODID, "mummy_familiar").toString()));

    public static final NonNullLazy<EntityType<BeaverFamiliarEntity>> BEAVER_FAMILIAR_TYPE =
            NonNullLazy.of(() -> EntityType.Builder.of(BeaverFamiliarEntity::new, MobCategory.CREATURE)
                    .sized(0.7F, 0.6F)
                    .clientTrackingRange(8)
                    .build(new ResourceLocation(Occultism.MODID, "beaver_familiar").toString()));

    public static final NonNullLazy<EntityType<ThrownSwordEntity>> THROWN_SWORD_TYPE =
            NonNullLazy.of(() -> EntityType.Builder.of(ThrownSwordEntity::new, MobCategory.MISC)
                    .sized(0.5F, 0.5F)
                    .clientTrackingRange(8)
                    .build(new ResourceLocation(Occultism.MODID, "thrown_sword").toString()));
    public static final NonNullLazy<EntityType<ShubNiggurathSpawnEntity>> SHUB_NIGGURATH_SPAWN_TYPE =
            NonNullLazy.of(() -> EntityType.Builder.<ShubNiggurathSpawnEntity>of(ShubNiggurathSpawnEntity::new, MobCategory.CREATURE)
                    .sized(0.6F, 0.6F)
                    .clientTrackingRange(8)
                    .build(new ResourceLocation(Occultism.MODID, "shub_niggurath_spawn").toString()));

    public static final Supplier<EntityType<FoliotEntity>> FOLIOT = ENTITIES.register("foliot", FOLIOT_TYPE::get);
    public static final Supplier<EntityType<DjinniEntity>> DJINNI = ENTITIES.register("djinni", DJINNI_TYPE::get);
    public static final Supplier<EntityType<AfritEntity>> AFRIT = ENTITIES.register("afrit", AFRIT_TYPE::get);
    public static final Supplier<EntityType<AfritWildEntity>> AFRIT_WILD = ENTITIES.register("afrit_wild", AFRIT_WILD_TYPE::get);
    public static final Supplier<EntityType<MaridEntity>> MARID = ENTITIES.register("marid", MARID_TYPE::get);


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
    public static final Supplier<EntityType<WildHuntWitherSkeletonEntity>> WILD_HUNT_WITHER_SKELETON =
            ENTITIES.register("wild_hunt_wither_skeleton", WILD_HUNT_WITHER_SKELETON_TYPE::get);
    public static final Supplier<EntityType<OtherworldBirdEntity>> OTHERWORLD_BIRD =
            ENTITIES.register("otherworld_bird", OTHERWORLD_BIRD_TYPE::get);
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

}
