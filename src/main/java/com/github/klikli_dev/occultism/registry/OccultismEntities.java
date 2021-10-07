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

package com.github.klikli_dev.occultism.registry;

import com.github.klikli_dev.occultism.Occultism;
import com.github.klikli_dev.occultism.common.entity.*;
import com.github.klikli_dev.occultism.common.entity.possessed.PossessedEndermanEntity;
import com.github.klikli_dev.occultism.common.entity.possessed.PossessedEndermiteEntity;
import com.github.klikli_dev.occultism.common.entity.possessed.PossessedSkeletonEntity;
import com.github.klikli_dev.occultism.common.entity.spirit.*;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.common.util.NonNullLazy;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import static com.github.klikli_dev.occultism.util.StaticUtil.modLoc;

public class OccultismEntities {
    //region Fields
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES,
            Occultism.MODID);

    public static final NonNullLazy<EntityType<FoliotEntity>> FOLIOT_TYPE =
            NonNullLazy.of(() -> EntityType.Builder.of(FoliotEntity::new, MobCategory.CREATURE)
                    .sized(0.6f, 0.9f)
                    .clientTrackingRange(8)
                    .build(modLoc("foliot").toString()));
    public static final NonNullLazy<EntityType<DjinniEntity>> DJINNI_TYPE =
            NonNullLazy.of(() -> EntityType.Builder.of(DjinniEntity::new, MobCategory.CREATURE)
                    .sized(0.6f, 0.9f)
                    .clientTrackingRange(8)
                    .build(modLoc("djinni").toString()));
    public static final NonNullLazy<EntityType<AfritEntity>> AFRIT_TYPE =
            NonNullLazy.of(() -> EntityType.Builder.of(AfritEntity::new, MobCategory.CREATURE)
                    .sized(1.2f, 1.8f)
                    .clientTrackingRange(8)
                    .build(modLoc("afrit").toString()));
    public static final NonNullLazy<EntityType<AfritWildEntity>> AFRIT_WILD_TYPE =
            NonNullLazy.of(() -> EntityType.Builder.of(AfritWildEntity::new, MobCategory.CREATURE)
                    .sized(1.2f, 1.8f)
                    .clientTrackingRange(8)
                    .build(modLoc("afrit_wild").toString()));
    public static final NonNullLazy<EntityType<MaridEntity>> MARID_TYPE =
            NonNullLazy.of(() -> EntityType.Builder.of(MaridEntity::new, MobCategory.CREATURE)
                    .sized(1.2f, 1.8f)
                    .clientTrackingRange(8)
                    .build(modLoc("marid").toString()));

    public static final NonNullLazy<EntityType<PossessedEndermiteEntity>> POSSESSED_ENDERMITE_TYPE =
            NonNullLazy.of(() -> EntityType.Builder.of(PossessedEndermiteEntity::new, MobCategory.MONSTER)
                    .sized(0.4F, 0.3F)
                    .clientTrackingRange(8)
                    .build(modLoc("possessed_endermite").toString()));
    public static final NonNullLazy<EntityType<PossessedSkeletonEntity>> POSSESSED_SKELETON_TYPE =
            NonNullLazy.of(() -> EntityType.Builder.of(PossessedSkeletonEntity::new, MobCategory.MONSTER)
                    .sized(0.6F, 1.99F)
                    .clientTrackingRange(8)
                    .build(modLoc("possessed_skeleton").toString()));
    public static final NonNullLazy<EntityType<PossessedEndermanEntity>> POSSESSED_ENDERMAN_TYPE =
            NonNullLazy.of(() -> EntityType.Builder.of(PossessedEndermanEntity::new, MobCategory.MONSTER)
                    .sized(0.6F, 2.9F)
                    .clientTrackingRange(8)
                    .build(modLoc("possessed_endermite").toString()));
    public static final NonNullLazy<EntityType<WildHuntSkeletonEntity>> WILD_HUNT_SKELETON_TYPE =
            NonNullLazy.of(() -> EntityType.Builder.of(WildHuntSkeletonEntity::new, MobCategory.MONSTER)
                    .sized(0.6F, 2.9F)
                    .clientTrackingRange(8)
                    .build(modLoc("wild_hunt_skeleton").toString()));
    public static final NonNullLazy<EntityType<WildHuntWitherSkeletonEntity>> WILD_HUNT_WITHER_SKELETON_TYPE =
            NonNullLazy.of(() -> EntityType.Builder.of(WildHuntWitherSkeletonEntity::new, MobCategory.MONSTER)
                    .sized(0.6F, 2.9F)
                    .clientTrackingRange(8)
                    .build(modLoc("wild_hunt_wither_skeleton").toString()));
    public static final NonNullLazy<EntityType<OtherworldBirdEntity>> OTHERWORLD_BIRD_TYPE =
            NonNullLazy.of(() -> EntityType.Builder.of(OtherworldBirdEntity::new, MobCategory.CREATURE)
                    .sized(0.5F, 0.9F)
                    .clientTrackingRange(8)
                    .build(modLoc("otherworld_bird").toString()));
    public static final NonNullLazy<EntityType<GreedyFamiliarEntity>> GREEDY_FAMILIAR_TYPE =
            NonNullLazy.of(() -> EntityType.Builder.of(GreedyFamiliarEntity::new, MobCategory.CREATURE)
                    .sized(0.5F, 0.9F)
                    .clientTrackingRange(8)
                    .build(modLoc("greedy_familiar").toString()));
    public static final NonNullLazy<EntityType<BatFamiliarEntity>> BAT_FAMILIAR_TYPE =
            NonNullLazy.of(() -> EntityType.Builder.of(BatFamiliarEntity::new, MobCategory.CREATURE)
                    .sized(0.5F, 0.9F)
                    .clientTrackingRange(8)
                    .build(modLoc("bat_familiar").toString()));
    public static final NonNullLazy<EntityType<DeerFamiliarEntity>> DEER_FAMILIAR_TYPE =
            NonNullLazy.of(() -> EntityType.Builder.of(DeerFamiliarEntity::new, MobCategory.CREATURE)
                    .sized(0.6F, 1.0F)
                    .clientTrackingRange(8)
                    .build(modLoc("deer_familiar").toString()));

    public static final NonNullLazy<EntityType<CthulhuFamiliarEntity>> CTHULHU_FAMILIAR_TYPE =
            NonNullLazy.of(() -> EntityType.Builder.of(CthulhuFamiliarEntity::new, MobCategory.CREATURE)
                    .sized(0.6F, 1.0F)
                    .clientTrackingRange(8)
                    .build(modLoc("cthulhu_familiar").toString()));

    public static final NonNullLazy<EntityType<DevilFamiliarEntity>> DEVIL_FAMILIAR_TYPE =
            NonNullLazy.of(() -> EntityType.Builder.of(DevilFamiliarEntity::new, MobCategory.CREATURE)
                    .sized(0.8F, 1.0F)
                    .clientTrackingRange(8)
                    .build(modLoc("devil_familiar").toString()));
    public static final NonNullLazy<EntityType<DragonFamiliarEntity>> DRAGON_FAMILIAR_TYPE =
            NonNullLazy.of(() -> EntityType.Builder.of(DragonFamiliarEntity::new, MobCategory.CREATURE)
                    .sized(1F, 0.8F)
                    .clientTrackingRange(8)
                    .build(modLoc("dragon_familiar").toString()));

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
    //endregion Fields
}
